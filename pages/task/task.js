// pages/task/task.js
const util = require("../../utils/util")
let app = getApp()
let taskTypes = app.globalData.taskTypes

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentRank: 0,
    currentPageIndex: 0,
    pagePerCount: 12,
    typePannel: false,
    labelIsActive: false,
    height: 0,
    innerHeight: 0,
    scrollTop: 0,
    startScroll: 0, 
    touchDown: 0,
    isloading: false,
    taskScrollTop: 0,
    taskRank:[
      "全部",
      "推荐"
    ],
    taskTypes:taskTypes,
    choosedTaskTypes:[
      {
        type:0,
        isSelected: false
      },{
        type:1,
        isSelected: false
      },
      {
        type:2,
        isSelected: false
      },{
        type:3,
        isSelected: false
      }
    ],
    taskLeftList:[],
    taskRightList:[]
  },

  changeRank: function(event){
    let index = event.currentTarget.dataset.index
    this.setData({
      currentRank: index
    })
    if(index == 0){
      this.getTaskWithTypes()
    }else{
      this.getRecommendTask()
    }
  },

  selectTaskType: function (event){
    let index = event.target.dataset.index
    let temp = this.data.choosedTaskTypes[index]
    temp.isSelected = !temp.isSelected
    let key = "choosedTaskTypes[" + index + "]"
    this.setData({
      [key]: temp
    })
  },

  confirmTypes: function(){
    this.setData({
      currentPageIndex: 0
    })
    this.getTaskWithTypes()
  },

  resetType: function(){
    // console.log("ebter")
    for(let i = 0; i < this.data.choosedTaskTypes.length; i++){
      this.data.choosedTaskTypes[i].isSelected = false
    }
    this.setData({
      choosedTaskTypes: this.data.choosedTaskTypes,
      labelIsActive: false
    })
  },

  togglePannel: function(){
    let flag = !this.data.typePannel
    this.setData({
      typePannel: flag,
      labelIsActive: false 
    })
  },

  closePannel: function(){
    this.setData({
      typePannel: false,
      labelIsActive: this.taskTypeIsActive()  
    })
  },

  getTaskWithTypes: function(callback = function(){}, 
    page = this.data.currentPageIndex + 1, num = this.data.pagePerCount){
    let types = []
    for(let i = 0; i < this.data.choosedTaskTypes.length; i++){
      if(this.data.choosedTaskTypes[i].isSelected){
        types.push(i)
      }
    }
    if(types.length == 0){
      types = [0, 1, 2, 3, 4]
    }
    wx.request({
      url: 'https://www.vozl.cn/assist/task/getSelectTaskForPage',
      method: 'GET',
      data: {
        "types[]": JSON.stringify(types) ,
        page: page,
        num: num
      },
      success: (res) => {
        // console.log(res.data)
        if(res.statusCode == 200){
          let newLeftTaskList = []
          let newRightTaskList = []
          for(let i = 0; i < res.data.length; i++){
            let taskExtent =  JSON.parse(res.data[i].taskContent)
            if(i % 2 == 0){
              newLeftTaskList.push(util.mergeJSON(res.data[i], taskExtent))
            }else{
              newRightTaskList.push(util.mergeJSON(res.data[i], taskExtent))
            }
          }
          this.setData({
            taskLeftList: newLeftTaskList,
            taskRightList: newRightTaskList
          })
          callback()
        }else{
          wx.showToast({
            title: '很抱歉，请求失败！',
            icon: 'none',
            duration: 1500
          })
        }
      },
      fail: (res) => {console.log(res)},
      complete: () => {
        this.setData({
          refresherTriggered: false
        })
      }
    })
    this.closePannel()
  },

  getRecommendTask: function(){
    wx.request({
      url: 'https://www.vozl.cn/assist/task/getCommendTask',
      method: 'GET',
      data:{
        phoneNumber: app.globalData.userInfo.phoneNum
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        console.log(res.data)
        if(res.statusCode == 200){
          let newLeftTaskList = []
          let newRightTaskList = []
          for(let i = 0; i < res.data.length; i++){
            let tempObj = res.data[i]
            let taskExtent = JSON.parse(tempObj.taskContent)
            if(i % 2 == 0){
              newLeftTaskList.push(util.mergeJSON(tempObj, taskExtent))
            }else{
              newRightTaskList.push(util.mergeJSON(tempObj, taskExtent))
            }
          }
          this.setData({
            taskLeftList: newLeftTaskList,
            taskRightList: newRightTaskList
          })
        }else{
          wx.showToast({
            title: '很抱歉，请求失败！',
            icon: 'none',
            duration: 1500
          })
        }
      },
      fail: (res) => {console.log(res)},
      complete: () => {
        this.setData({
          refresherTriggered: false
        })
      }
    })
  },

  taskTypeIsActive: function(){
    let flags = this.data.choosedTaskTypes  
    return flags[0].isSelected || flags[1].isSelected || flags[2].isSelected || flags[3].isSelected
  },

  // navigateTaskDetail: function(){
  //   let url = '/pages/subordinatePage/taskDetail/taskDetail?fromPage=task'
  //   wx.navigateTo({
  //     url
  //   })
  // },

  touchStart: function(event) {
    this.setData({
      touchDown: event.touches[0].clientY
    })
    wx.createSelectorQuery().select('.task-showarea').boundingClientRect(rect => {
      this.setData({
        innerHeight: rect.height
      })
    }).exec()

    wx.createSelectorQuery().select('.task-scroll-view').fields({
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
    let currentY = event.changedTouches[0].clientY
    let {startScroll, innerHeight, height, touchDown} = this.data
    // if (currentY > touchDown && currentY- touchDown > 20 && startScroll == 0) {
    //   console.log("触摸开头")
    // }else 
    if(this.data.currentRank == 1){
      return
    }
    if(currentY < touchDown && touchDown - currentY >= 40 && (startScroll +  
      height >= (innerHeight + 8) || startScroll +  
      height >= (innerHeight - 8)) && innerHeight > height) {
      // console.log("触摸底部")
      this.setData({
        isloading: true,
        currentPageIndex: this.data.currentPageIndex + 1
      })
      let types = []
      for(let i = 0; i < this.data.choosedTaskTypes.length; i++){
        if(this.data.choosedTaskTypes[i].isSelected){
          types.push(i)
        }
      }
      if(types.length == 0){
        types = [0, 1, 2, 3, 4]
      }
      wx.request({
        url: 'https://www.vozl.cn/assist/task/getSelectTaskForPage',
        method: 'GET',
        data: {
          "types[]": JSON.stringify(types) ,
          page: this.data.currentPageIndex + 1,
          num: this.data.pagePerCount
        },
        success: (res) => {
          // console.log(res.data)
          if(res.statusCode == 200){
            if(res.data.length == 0){
              this.setData({
                isloading: false
              })
              wx.showToast({
                title: '没有更多任务了！',
                icon: 'none',
                duration: 1500
              })
              return
            }
            let newLeftTaskList = []
            let newRightTaskList = []
            for(let i = 0; i < res.data.length; i++){
              let taskExtent = JSON.parse(res.data[i].taskContent)
              if(this.data.taskRightList.length > this.data.taskLeftList.length){
                if(i % 2 == 0){
                  newRightTaskList.push(util.mergeJSON(res.data[i], taskExtent))
                }else{
                  newLeftTaskList.push(util.mergeJSON(res.data[i], taskExtent))
                }
              }else{
                if(i % 2 == 0){
                  newLeftTaskList.push(util.mergeJSON(res.data[i], taskExtent))
                }else{
                  newRightTaskList.push(util.mergeJSON(res.data[i], taskExtent))
                }
              }
            }
            this.data.taskLeftList = this.data.taskLeftList.concat(newLeftTaskList)
            this.data.taskRightList = 
              this.data.taskRightList.concat(newRightTaskList)
            setTimeout(() => {
              this.setData({
                isloading: false
              })
            }, 800)
            this.setData({
              taskLeftList: this.data.taskLeftList,
              taskRightList: this.data.taskRightList
            })
          }else{
            wx.showToast({
              title: '很抱歉，请求失败！',
              icon: 'none',
              duration: 1500
            })
          }
        },
        fail: (res) => {console.log(res)}
      })
    }
  },

  touchScreen: function(){
    wx.createSelectorQuery().select('.task-scroll-view').fields({
      scrollOffset: true,
      size: true
    }, rect => {
      // console.log(rect.scrollTop)
      this.setData({
        taskScrollTop: rect.scrollTop
      })
      wx.setStorageSync('taskScrollTop', rect.scrollTop)
    }).exec()
  },

  refreshPulling: function(){
    wx.setStorageSync('taskScrollTop', 0)
    this.setData({
      taskScrollTop: 0
    })
    if(this.data.currentRank == 0){
      this.setData({
        currentPageIndex: 0
      })
      this.getTaskWithTypes(() => {
        let taskScrollTop = wx.getStorageSync('taskScrollTop')
        if(taskScrollTop){
          this.setData({
            taskScrollTop: taskScrollTop,
            refresherTriggered: false
          })
        }
      })
    }else{
      this.getRecommendTask()
    }
  },

  getCurrentTask: function(event){
    console.log(event.detail.index)
    console.log(event.detail.direction)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      showCustomer: app.globalData.showCustomer
    })
    if(!this.data.showCustomer){
      wx.setNavigationBarTitle({
        title: '目标',
      })
    }
    wx.setStorageSync('taskScrollTop', 0)
    let query = wx.createSelectorQuery()
    query.select('.task-scroll-view').boundingClientRect(rect=>{
      this.setData({
        height: rect.height,
        currentRank: 0
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
    if(this.data.currentRank == 0){
      this.getTaskWithTypes(() => {
        let taskScrollTop = wx.getStorageSync('taskScrollTop')
        if(taskScrollTop){
          this.setData({
            taskScrollTop: taskScrollTop
          })
        }
      }, 1, ((this.data.currentPageIndex + 1) * this.data.pagePerCount))
    }else{
      this.getRecommendTask()
    }
    
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        currentTab: 1
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