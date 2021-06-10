// index.js
const app = getApp()

Page({
  data: {
    currentSiwper: 0,
    userInfo: {},
    notice:[
      {
        title: "",
        content: ""
      }
    ],
    ongoingTasks:[],
    taskTypeImgs: [
      "/image/canteen.png",
      "/image/errand.png",
      "/image/expressage.png",
      "/image/team.png"
    ],
    waitingTask: 0,
    completingTask: 0
  },

  swiperChange(event) {
    this.setData({
      currentSiwper: event.detail.current
    })
  },

  navigateToMyTask:function(event){
    if(this.data.showCustomer){
      if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
        app.loginOrRegister()
        return
      }
      let index = event.currentTarget.dataset.index
      console.log(index)
      let url = "/pages/subordinatePage/myTask/myTask?index=" + index
      wx.navigateTo({
        url
      })
    }
  },

  navigateToTaskDetail:function(event){
    let index = event.currentTarget.dataset.index
    let order = this.data.ongoingTasks[index]
    let url = "/pages/subordinatePage/taskDetail/taskDetail?taskId=" + order.taskId + "&orderId=" + order.orderId
    wx.navigateTo({
      url
    })
  },

  myClear: function(){
    wx.removeStorageSync('userInfo')
    wx.clearStorageSync()
  },

  onLoad() {
    app.getShowCustomer().then((flag) => {
      this.setData({
        showCustomer: flag
      })
    })
    wx.request({
      url: 'https://www.vozl.cn/assist/other/getLatelyAnnounce',
      method: 'POST',
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        this.setData({
          notice: res.data
        })
      },
      fail: (res) => {console.log(res)}
    })
  },

  onShow() {
    this.setData({
      isLoading: true
    })
    let userInfo = wx.getStorageSync('userInfo')
    if (userInfo){
      this.setData({
        userInfo: userInfo
      })
      let ongoingTasks = []
      wx.request({
        url: 'https://www.vozl.cn/assist/orders/getAllOrderingByPhoneNum',
        method: 'GET',
        data:{
          phoneNum: userInfo.phoneNum
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          if(res.statusCode == 200){
            ongoingTasks = ongoingTasks.concat(res.data)
            wx.request({
              url: 'https://www.vozl.cn/assist/orders/getAllPublishOrderingByPhoneNum',
              method: 'GET',
              data:{
                phoneNum: userInfo.phoneNum
              },
              header: {'content-type': 'application/x-www-form-urlencoded'},
              success: (innerres) => {
                if(innerres.statusCode == 200){
                  ongoingTasks = ongoingTasks.concat(innerres.data)
                  for(let i = 0; i < ongoingTasks.length; i++){
                    ongoingTasks[i].isMyTakeTask = (ongoingTasks[i].phoneNum == userInfo.phoneNum)
                  }
                  this.setData({
                    ongoingTasks: ongoingTasks,
                    isLoading: false
                  })
                }else{
                  wx.showToast({
                    title: '抱歉，请求失败！',
                    icon: 'none',
                    duration: 1500
                  })
                  this.setData({
                    isLoading: false
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
              isLoading: false
            })
          }
        },
        fail: (res) => {console.log(res)}
      })
      wx.request({
        url: 'https://www.vozl.cn/assist/task/getNotPickUpTaskByPhoneNum',
        method: 'GET',
        data:{
          phoneNum: userInfo.phoneNum
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          this.setData({
            waitingTask: res.data.length
          })
        },
        fail: (res) => {
          console.log(res)
          this.setData({
            isLoading: false
          })
        }
      })
    }
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        currentTab: 0 
      })
    }
  },

  emptyFun: function(){}
})
