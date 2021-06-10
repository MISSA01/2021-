// pages/order/order.js
const util = require("../../utils/util")
let windowWidth = wx.getSystemInfoSync().windowWidth
let app = getApp()
let leftList = ["全部"].concat(app.globalData.taskTypes)

Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo:app.globalData.userInfo,
    imageString: "",
    currentTop: 0,
    currentLeft: 0,
    labelIsActive: false,
    topListItems:[
      "全部",
      "我接取的",
      "我发布的"
    ],
    leftListItems: leftList,
    taskTypes: app.globalData.taskTypes,
    orderInfos:[
    ],
    height: 0,
    innerHeight: 0,
    orderScrollTop: 0,
    startScroll: 0, 
    touchDown: 0,
    isloading: false
  },

  selectTop: function (event){
    let top = event.target.dataset.index
    if(top != this.data.currentTop){
      this.setData({
        currentTop: top
      })
      this.getCurrentSelectedOrders()
    }
  },

  selectLeft: function (event){
    let left = event.target.dataset.index
    if(left == 5){
      wx.showToast({
        title: '任务种类待添加...',
        icon: 'none',
        duration: 1500
      })
      return
    }
    if(left != this.data.currentLeft){
      this.setData({
        currentLeft: left
      })
      this.getCurrentSelectedOrders()
    }
  },

  navigateToMyTask: function(event){
    if(this.data.userInfo){
      let url = "/pages/subordinatePage/myTask/myTask"
      wx.navigateTo({
        url
      })
    }else{
      wx.showToast({
        title: '请先登录/注册！',
        icon: 'none',
        duration: 1000
      })
    }
  },

  delOrderItem: function(event){
    let index = event.detail
    this.data.orderInfos.splice(index, 1)
    this.setData({
      orderInfos: this.data.orderInfos
    })
  },

  touchStart: function(event) {
    this.setData({
      touchDown: event.touches[0].clientY
    })
    wx.createSelectorQuery().select('.task-list-inner').boundingClientRect(rect => {
      this.setData({
        innerHeight: rect.height
      })
    }).exec()

    wx.createSelectorQuery().select('.task-list').fields({
      scrollOffset: true,
      size: true
    }, rect => {
      this.setData({
        startScroll: rect.scrollTop,
        height: rect.height
      })
    }).exec()
  },

  touchEnd: function(event) {
    let currentY = event.changedTouches[0].clientY;
    let {startScroll, innerHeight, height, touchDown} = this.data;
    // if (currentY > touchDown && currentY- touchDown > 20 && startScroll == 0) {
    //   console.log("触摸开头")
    // }else 
    if(currentY < touchDown && touchDown - currentY >= 40 && (startScroll +  
      height >= (innerHeight + 8) || startScroll +  
      height >= (innerHeight - 8)) && innerHeight > height) {
      console.log("触摸底部")
      this.setData({
        isloading: true
      })
      setTimeout(()=>{
        this.setData({
          isloading: false
        })
      }, 800)
    }
  },

  changeItem: function(orderId, flag){
    for(let i = 0; i < this.data.orderInfos.length; i++){
      if(this.data.orderInfos[i].orderId == orderId){
        this.data.orderInfos[i].hasNew = flag
        break
      }
    }
    this.setData({
      orderInfos: this.data.orderInfos
    })
  },

  getNewChatOfList: function(callback = function(){}){
    for(let i = 0; i < this.data.orderInfos.length; i++){
      if(!(this.data.orderInfos[i].finishTime == "" || !this.data.orderInfos[i].finishTime)){
        return
      }
      new Promise((resolve) => {
        let anotherPhoneNum = 
          (this.data.userInfo.phoneNum == this.data.orderInfos[i].phoneNum ? 
            this.data.orderInfos[i].task.phoneNum : this.data.orderInfos[i].phoneNum)
        wx.request({
          url: 'https://www.vozl.cn/assist/chat/getNewChatNumOfOne',
          method: 'POST',
          data: {
            fromUserPhoneNum: anotherPhoneNum,
            toUserPhoneNum: this.data.userInfo.phoneNum,
            orderId: parseInt(this.data.orderInfos[i].orderId)
          },
          header: {'content-type': 'application/x-www-form-urlencoded'},
          success: (res) => {
            if(res.statusCode == 200){
              resolve(res.data)
              callback()
            }else{
              resolve(0)
            }
          },
          fail: (res) => {
            console.log(res)
            this.setData({
              isloading: false
            })
            resolve(0)
          }
        }) 
      }).then((num) => {
        if(num > 0){
          this.data.orderInfos[i].hasNew = true
          this.setData({
            orderInfos: this.data.orderInfos
          })
        }
      })
    }
  },

  getAllOrders: function(type){
    let url = "https://www.vozl.cn/assist/orders/"
    let data = {
      phoneNum: this.data.userInfo.phoneNum
    }
    if(type == -1){
      url += "getPeoAllOrdersByPhoneNum"    
    }else{
      url += "getPeoOrdersByPhoneNum"
      data.taskType = type
    }
    this.getOrder(url, data)
  },

  getOrdersReceive: function(type){
    let url = "https://www.vozl.cn/assist/orders/"
    let data = {
      phoneNum: this.data.userInfo.phoneNum
    }
    if(type == -1){
      url += "getAllOrdersByPhoneNum"    
    }else{
      url += "getOrdersByPhoneNum"
      data.taskType = type
    }
    this.getOrder(url, data)
  },
  
  getOrdersPublish: function(type){
    let url = "https://www.vozl.cn/assist/orders/"
    let data = {
      phoneNum: this.data.userInfo.phoneNum
    }
    if(type == -1){
      url += "getAllPublishOrdersByPhoneNum"    
    }else{
      url += "getPublishOrdersByPhoneNum"
      data.taskType = type
    }
    this.getOrder(url, data)
  },

  getOrder(url, data, callback = function(){}){
    // this.setData({
    //   isloading: true
    // })
    wx.request({
      url: url,
      method: 'GET',
      data: data,
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.statusCode == 200){
          this.setData({
            orderInfos: []
          })
          this.setData({
            orderInfos: util.deepClone(res.data),
            refresherTriggered: false,
            isloading: false
          })
          this.getNewChatOfList(() => {
            // this.setData({
            //   isloading: false
            // })
          })
        }
        callback()
      },
      fail: (res) => {
        console.log(res)
        this.setData({
          isloading: false
        })
      }
    })
  },

  getCurrentSelectedOrders: function(){
    if(this.data.userInfo){
      this.setData({
        isloading: true
      })
      let top = this.data.currentTop
      let left = this.data.currentLeft
      if(top == 0){
        this.getAllOrders(left - 1)
      }else if(top == 1){
        this.getOrdersReceive(left - 1)
      }else{
        this.getOrdersPublish(left - 1)
      }
    }
  },

  touchScreen: function(){
    wx.createSelectorQuery().select('.task-list').fields({
      scrollOffset: true,
      size: true
    }, rect => {
      this.setData({
        orderScrollTop: rect.scrollTop
      })
      wx.setStorageSync('orderScrollTop', rect.scrollTop)
    }).exec()
  },

  refreshPulling: function(){
    this.getCurrentSelectedOrders()
    this.setData({
      refresherTriggered: false
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    app.globalData.orderPage = this
    wx.setStorageSync('orderScrollTop', 0)
    this.setData({
      userInfo: app.globalData.userInfo
    })
    let query = wx.createSelectorQuery()
    query.select('.task-list').boundingClientRect(rect=>{
      this.setData({
        height: rect.height
      })
    }).exec()
  },


  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      userInfo: app.globalData.userInfo
    })
    if(this.data.userInfo){
      this.getCurrentSelectedOrders()
      let orderScrollTop = wx.getStorageSync('orderScrollTop')
      if(orderScrollTop){
        this.setData({
          orderScrollTop: orderScrollTop
        })
      }
    }
    app.changeRedpoint(app.globalData.msgList.length != 0)
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        currentTab: 3
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  emptyFunc: function(){

  }
})