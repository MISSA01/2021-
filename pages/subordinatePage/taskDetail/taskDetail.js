// pages/subordinatePage/taskDetail/taskDetail.js
const util = require("../../../utils/util")
let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentUser: app.globalData.userInfo,
    publishUser: {},
    taskTypes: app.globalData.taskTypes,
    orderStates: app.globalData.orderStates,
    taskStateLabels: app.globalData.taskStateLabels,
    canPickup: true,
    fromPage: "",
    taskInfo:{
      taskId: 0,
      taskTitle: "",
      phoneNum: "",
      taskState: 0,
      taskType: 0,
      taskReward: 0,
      itemCount: 1,
      taskContent: "",
      taskGetPlace: "",
      taskDestination: "",
      taskInTime: "",
      taskOutTime: ""
    },
    orderInfo:{
      orderId:"",
      phoneNum:"",
      receiveTime:"",
      finishTime:"",
    }
  },

  completeTask: function(){
    wx.showModal({
      title: '提示',
      content: '确定完成吗？任务完成后将无法再发送消息！',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: 'https://www.vozl.cn/assist/orders/finishOrderByOrderId',
            method: 'POST',
            data: {
              orderId: this.data.orderInfo.orderId
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              console.log(res)
              if(res.statusCode == 200){
                wx.showToast({
                  title: '任务完成！',
                  icon: 'success',
                  duration: 1500,
                  success: () => {
                    setTimeout(() => {
                      wx.navigateBack({
                        delta: 0
                      })
                    }, 1500)
                  }
                })
                wx.getSetting({
                  withSubscriptions: true,
                  success: (settingRes) => {
                    console.log(this.data.orderInfo.orderId)
                    let itemSettings = settingRes.subscriptionsSetting.itemSettings
                    if(!itemSettings || itemSettings['uphQtIan7LzJKseba1CS-hyuujK6Gn9H9CQxUNxqOZ8'] == "accept"){
                      util.sentWxNotification(this.data.orderInfo.orderId, 2)
                    }
                  }
                })
                let key = "orderInfo.finishTime"
                this.setData({
                  [key]: util.formatTime(new Date())
                })
              }else{
                wx.showToast({
                  title: '抱歉，请求失败！',
                  icon: 'none',
                  duration: 1500
                })
              }
            },
            fail: (res) => {console.log(res)}
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  giveupTask: function(){
    console.log(this.data.orderInfo.orderId)
    console.log(this.data.orderInfo)
    wx.showModal({
      title: '提示',
      content: '确定放弃吗？任务放弃后将删除相关订单',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: 'https://www.vozl.cn/assist/orders/giveUpOrderByOrderId',
            method: 'POST',
            data: {
              orderId: parseInt(this.data.orderInfo.orderId)
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              console.log(res)
              if(res.statusCode == 200){
                wx.showToast({
                  title: '放弃任务！即将退出当前窗口！',
                  icon: 'none',
                  success: () => {
                    setTimeout(() => {
                      wx.navigateBack({
                        delta: 0,
                      })
                    }, 1500)            
                  }
                })
              }else{
                wx.showToast({
                  title: '抱歉，请求失败！',
                  icon: 'none',
                  duration: 1500
                })
              }
            },
            fail: (res) => {console.log(res)}
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  modifyTask: function(){
    let url = '/pages/subordinatePage/taskForm/taskForm?isModify=true&taskId=' + 
      this.data.taskInfo.taskId
    wx.navigateTo({
      url
    })
  },

  cancelTask: function(){
    wx.showModal({
      title: '提示',
      content: '确定取消任务吗？',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: 'https://www.vozl.cn/assist/task/giveUpTaskByTaskId',
            method: 'POST',
            data: {
              taskId: parseInt(this.data.taskInfo.taskId)
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              if(res.statusCode == 200){
                if(res.data == "success"){
                  wx.showToast({
                    title: '取消成功！',
                    icon: 'success',
                    duration: 1500,
                    success: () => {
                      setTimeout(() => {
                        wx.navigateBack({
                          delta: 0,
                        })
                      }, 1500)
                    }
                  })
                }else{
                  wx.showToast({
                    title: '取消失败，请稍后再试！',
                    icon: 'none',
                    duration: 1500
                  })
                }
              }else{
                wx.showToast({
                  title: '抱歉，请求失败！',
                  icon: 'success',
                  duration: 1500,
                })
              }
            },
            fail: (res) => {console.log(res)}
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })    
  },

  finishTask: function(){
    wx.request({
      url: 'https://www.vozl.cn/assist/task/finishTaskByTaskId',
      method: 'POST',
      data: {
        taskId: this.data.taskInfo.taskId
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.statusCode == 200){
          wx.showToast({
            title: '目标完成',
            icon: 'success',
            duration: 1500,
            success: ()=>{
              setTimeout(() => {
                wx.navigateBack({
                  delta: 0
                })
              }, 1500)
            }
          })
        }else{
          wx.showToast({
            title: '抱歉，请求失败！',
            icon: 'none',
            duration: 1500
          })
        }
      },
      fail: (res) => {console.log(res)}
    })
  },

  pickupTask: function(){
    wx.showModal({
      title: '提示',
      content: '确定接取吗？',
      success: (modalres) => {
        if (modalres.confirm) {
          wx.request({
            url: 'https://www.vozl.cn/assist/task/pickupTaskByTaskId',
            method: 'POST',
            data: {
              phoneNum: this.data.currentUser.phoneNum,
              taskId: this.data.taskInfo.taskId
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (requestres) => {
              if(requestres.statusCode == 200){
                if(requestres.data == "outTimeError"){
                  wx.showToast({
                    title: '已超接取时限！',
                    icon: 'error',
                    duration: 1500,
                    success: () => {
                      setTimeout(()=>{
                        wx.navigateBack({
                          delta: 0,
                        })
                      },1500)
                    }
                  })
                }else if(requestres.data == "outPeoNumError"){
                  wx.showToast({
                    title: '抱歉，已被接取！',
                    duration: 1500,
                    success: () => {
                      setTimeout(()=>{
                        wx.navigateBack({
                          delta: 0,
                        })
                      },1500)
                    }
                  })
                }else if(requestres.data == "duplicateError"){
                  wx.showToast({
                    title: '不能重复接取！',
                    icon: 'none',
                    duration: 1500,
                    success: () => {
                      setTimeout(()=>{
                        wx.navigateBack({
                          delta: 0,
                        })
                      },1500)
                    }
                  })
                }else{
                  wx.getSetting({
                    withSubscriptions: true,
                    success: (settingRes) => {
                      let itemSettings = settingRes.subscriptionsSetting.itemSettings
                      // if(!itemSettings || itemSettings['J-HnU4hnf9gxOb5opr8KGkLiAQ2G8lGKBJX10CA8qkE'] == "accept"){
                      //   util.sentWxNotification(requestres.data, 1)
                      // }
                      if(!itemSettings || itemSettings['UlzegeugM-sxDzl_g-8S7XXdtm1kQwEJ4ikk2l9izK4'] == "accept"){
                        util.sentWxNotification(requestres.data, 1)
                      }
                    }
                  })
                  wx.showToast({
                    title: '接取成功！',
                    icon: 'success',
                    duration: 1500,
                    success: () => {
                      setTimeout(()=>{
                        wx.navigateBack({
                          delta: 0,
                        })
                      },1500)
                    }
                  })
                }
              }else{
                wx.showToast({
                  title: '抱歉，请求失败！',
                  icon: 'none',
                  duration: 1500
                })
              }
              // console.log(this.data.currentUser.phoneNum + " pickup " +
              //   this.data.publishUser.phoneNum)
            },
            fail: (res) => {console.log(res)}
          })
        } else if (modalres.cancel) {
          console.log('用户点击取消')
        }
      }
    })    
  },

  gotoChat(){
    let currentUser = app.globalData.userInfo.phoneNum
    let order = this.data.orderInfo
    let task = this.data.taskInfo
    // console.log(order)
    if(order.phoneNum && order.phoneNum.length > 0){
      let isMyTask = (currentUser == order.phoneNum ? false : true)
      let toPhoneNum = (currentUser == order.phoneNum ? task.phoneNum : order.phoneNum)
      let url = '/pages/subordinatePage/chat/chat?toPhoneNum=' + toPhoneNum + 
        "&isCompleted=" + !(order.finishTime == "" || !order.finishTime) + "&orderId=" + order.orderId + '&isMyTask=' + isMyTask
      wx.navigateTo({url})
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
      app.loginOrRegister()
      return
    }
    this.setData({
      fromPage: options.fromPage,
      currentUser: app.globalData.userInfo,
      showCustomer: app.globalData.showCustomer
    })
    if(!this.data.showCustomer){
      wx.setNavigationBarTitle({
        title: '目标详情',
      })
    }
    this.setData({
      taskId: options.taskId,
      orderId: options.orderId
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
    this.setData({
      currentUser: app.globalData.userInfo
    })
    let taskId = this.data.taskId
    let orderId = this.data.orderId
    let url = 'https://www.vozl.cn/assist/'
    let data = {}
    // console.log("task: " + taskId)
    // console.log("order: " + orderId)
    if(orderId && orderId != "-1"){
      url += 'orders/getOrdersByOrderId'
      data.orderId = parseInt(orderId)
    }else{
      url += 'task/getTaskByTaskId'
      data.taskId = parseInt(taskId)
    }
    wx.request({
      url: 'https://www.vozl.cn/assist/task/judgePickUpTaskOrNot',
      method: 'GET',
      data: {
        phoneNum: this.data.currentUser.phoneNum,
        taskId: parseInt(taskId)
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.statusCode == 200){
          if(res.data){
            this.setData({
              canPickup: false
            })
          }else{
            this.setData({
              canPickup: true
            })
          }
          if(this.data.canPickup){
            this.setData({
              ['taskInfo.taskState']: 0,
              orderInfo: {}
            })
          }
        }
      },
      fail: (res) => {console.log(res)}
    })
    new Promise((resolve) => {
      wx.request({
        url: url,
        method: 'GET',
        data: data,
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          let task = {}
          if(res.statusCode == 200){
            if(res.data != ""){
              if(orderId && orderId != "-1"){
                let orderInfo = {
                  orderId:res.data.orderId,
                  phoneNum:res.data.phoneNum,
                  receiveTime:res.data.receiveTime,
                  finishTime:res.data.finishTime,
                }
                task = util.mergeJSON(res.data.task, JSON.parse(res.data.task.taskContent))
                this.setData({
                  taskInfo: task,
                  orderInfo: orderInfo
                })
              }else{
                task = util.mergeJSON(res.data, JSON.parse(res.data.taskContent))
                this.setData({
                  taskInfo: task
                })
              }
              // console.log(task)
              resolve(task.phoneNum)
            }
          }else{
            wx.showToast({
              title: '抱歉，请求失败！',
              icon: 'none',
              duration: 1500
            })
          }
        },
        fail: (res) => {console.log(res)}
      })
    }).then((phoneNum = "") => {
      if(phoneNum != "" && (!this.data.publishUser.phoneNum)){
        wx.request({
          url: 'https://www.vozl.cn/assist/profile/getUserDetails',
          method: 'POST',
          data: {
            phoneNumber: phoneNum
          },
          header: {'content-type': 'application/x-www-form-urlencoded'},
          success: (res) => {
            // console.log(res.data)
            res.data.avatar = app.globalData.urlStart + res.data.avatar
            this.setData({
              publishUser: res.data
            })
          },
          fail: (res) => {console.log(res)}
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  }
})