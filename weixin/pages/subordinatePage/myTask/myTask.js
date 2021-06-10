// pages/subordinatePage/myTask/myTask.js
let app = getApp()
let taskTypes = app.globalData.taskTypes

Page({

  /**
   * 页面的初始数据
   */
  data: {
    height: 0,
    innerHeight: 0,
    scrollTop: 0,
    startScroll: 0, 
    touchDown: 0,
    isloading: false,
    currentActive: 0,
    buttonText:[
      "进行中",
      "已完成",
      "待接取",
    ],
    taskTypes:app.globalData.taskTypes,
    taskStates:app.globalData.taskStates,
    showList:[
    ]
  },

  changeActive: function(event){
    let index = event.currentTarget.dataset.index
    if(index != this.data.currentActive){
      this.getOrderOrTask(index,
        () => {
          this.setData({
            currentActive: index
          })
        }
      )
    }
  },

  touchStart: function(event) {
    this.setData({
      touchDown: event.touches[0].clientY
    })
    wx.createSelectorQuery().select('.task-list').boundingClientRect(rect => {
      this.setData({
        innerHeight: rect.height
      })
    }).exec()

    wx.createSelectorQuery().select('.task-list-outer').fields({
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

  touchScreen: function(){
    wx.createSelectorQuery().select('.task-list-outer').fields({
      scrollOffset: true,
      size: true
    }, rect => {
      this.setData({
        scrollTop: rect.scrollTop
      })
      wx.setStorageSync('scrollTop', rect.scrollTop)
    }).exec()
  },

  refreshPulling: function(){
    this.getOrderOrTask(this.data.currentActive, () => {
      let scrollTop = wx.getStorageSync('scrollTop')
      if(scrollTop){
        this.setData({
          scrollTop: scrollTop
        })
      }
      this.setData({
        refresherTriggered: false
      })
    })
  },

  getOrderOrTask: function(index, success = function(){}, fail = function(){
      wx.showToast({
        title: '很抱歉，获取失败，请稍后再试',
        icon: 'none',
        duration: 1500
      })
    }){
    this.setData({
      isloading: true
    })
    let showList = []
    let phoneNum = app.globalData.userInfo.phoneNum
    let url = "https://www.vozl.cn/assist/"
    if(index != 2){
      let anotherUrl = "https://www.vozl.cn/assist/"
      if(index == 0){
        url += "orders/getAllOrderingByPhoneNum"
        anotherUrl += "orders/getAllPublishOrderingByPhoneNum"
      }else if(index == 1){
        url += "/orders/getAllOrderedByPhoneNum"
        anotherUrl += "/orders/getAllPublishOrderedByPhoneNum"
      }
      wx.request({
        url: url,
        method: 'GET',
        data:{
          phoneNum: phoneNum
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          if(res.statusCode == 200){
            showList = showList.concat(res.data)
            wx.request({
              url: anotherUrl,
              method: 'GET',
              data:{
                phoneNum: phoneNum
              },
              header: {'content-type': 'application/x-www-form-urlencoded'},
              success: (innerres) => {
                if(innerres.statusCode == 200){
                  showList = showList.concat(innerres.data)
                  let currentList = []
                  for(let i = 0; i < showList.length; i++){
                    let item = showList[i]
                    let tempObj = {}
                    if(item.orderId){
                      tempObj.orderInfo = {}
                      tempObj.orderInfo.orderId = item.orderId
                      tempObj.orderInfo.phoneNum = item.phoneNum
                      tempObj.orderInfo.receiveTime = item.receiveTime
                      tempObj.orderInfo.finishTime = item.finishTime
                      tempObj.orderInfo.taskId = item.taskId
                      tempObj.orderInfo.isMyTakeTask = (item.phoneNum == phoneNum)
                      tempObj.taskInfo = item.task
                    }
                    currentList.push(tempObj)
                  }
                  success()
                  this.setData({
                    showList: currentList,
                    isloading: false
                  })
                }else{
                  wx.showToast({
                    title: '抱歉，请求失败！',
                    icon: 'none',
                    duration: 1500
                  })
                  this.setData({
                    isloading: false
                  })
                }
              },
              fail: (innerres) => {console.log(innerres)}
            })
          }else{
            wx.showToast({
              title: '抱歉，请求失败！',
              icon: 'none',
              duration: 1500
            })
            this.setData({
              isloading: false
            })
          }
        },
        fail: (res) => {console.log(res)}
      })
    }else if(index == 2){
      url += "task/getNotPickUpTaskByPhoneNum"
      wx.request({
        url: url,
        method: 'GET',
        data: {
          phoneNum: phoneNum,
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          if(res.statusCode == 200){
            let currentList = []
            for(let item of res.data){
              let tempObj = {}
              if(item.orderId){
                tempObj.orderInfo = {}
                tempObj.orderInfo.orderId = item.orderId
                tempObj.orderInfo.phoneNum = item.phoneNum
                tempObj.orderInfo.receiveTime = item.receiveTime
                tempObj.orderInfo.finishTime = item.finishTime
                tempObj.orderInfo.taskId = item.taskId
                tempObj.taskInfo = item.task
              }else{
                tempObj.taskInfo = item
              }
              currentList.push(tempObj)
            }
            this.setData({
              showList: currentList,
              isloading: false
            })
            success()
          }else{
            fail()
            this.setData({
              isloading: false
            })
          }
        },
        fail: (res) => {
          fail()
          this.setData({
            isloading: false
          })
        }
      })
    }


    // if(index == 0){
    //   url += "orders/getAllOrdersByPhoneNum"
    // }else if(index == 1){
    //   url += "orders/getAllPublishOrdersByPhoneNum"
    // }else{
    //   url += "task/getNotPickUpTaskByPhoneNum"
    // }
    // wx.request({
    //   url: url,
    //   method: 'GET',
    //   data: {
    //     phoneNum: app.globalData.userInfo.phoneNum,
    //   },
    //   header: {'content-type': 'application/x-www-form-urlencoded'},
    //   success: (res) => {
    //     if(res.statusCode == 200){
    //       let currentList = []
    //       for(let item of res.data){
    //         if(item.taskState == 1){
    //           continue
    //         }
    //         let tempObj = {}
    //         if(item.orderId){
    //           tempObj.orderInfo = {}
    //           tempObj.orderInfo.orderId = item.orderId
    //           tempObj.orderInfo.phoneNum = item.phoneNum
    //           tempObj.orderInfo.receiveTime = item.receiveTime
    //           tempObj.orderInfo.finishTime = item.finishTime
    //           tempObj.orderInfo.taskId = item.taskId
    //           tempObj.taskInfo = item.task
    //         }else{
    //           tempObj.taskInfo = item
    //         }
    //         currentList.push(tempObj)
    //       }
    //       this.setData({
    //         showList: currentList
    //       })
    //       success()
    //     }else{
    //       fail()
    //     }
    //   },
    //   fail: (res) => {
    //     fail()
    //   }
    // })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
      app.loginOrRegister()
      return
    }
    wx.setStorageSync('scrollTop', 0)
    let index = options.index ? options.index : 0
    this.setData({
      currentActive: index
    })
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
    this.getOrderOrTask(this.data.currentActive, () => {
      if(this.data.showList.length > 0){
        let query = wx.createSelectorQuery()
        query.select('.task-list-outer').boundingClientRect(rect=>{
          this.setData({
            height: rect.height
          })
        }).exec()
      }
    })
    let scrollTop = wx.getStorageSync('scrollTop')
    if(scrollTop){
      this.setData({
        scrollTop: scrollTop
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  }
})