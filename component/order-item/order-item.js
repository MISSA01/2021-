// component/order-item.js
let app = getApp()

Component({
  /**
   * 组件的属性列表
   */
  options: {
    addGlobalClass: true
  },
  properties: {
    orderInfo:{
      type:Object,
      value:{
        orderId:"",
        phoneNum:"",
        receiveTime:"",
        finishTime:"",
        taskId:"",
        task:{
          taskId:0,
          phoneNum:"",
          taskTitle:"",
          taskType:0,
          taskReward:0,
          taskContent:"",
          taskInTime:"",
          taskOutTime:"",
          taskState:1,
          taskPeoNum:""
        }
      }
    },
    index:{
      type: Number
    },
    hasNew:{
      type: Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    taskTypes: app.globalData.taskTypes,
    orderStates: app.globalData.orderStates,
    currentUser: app.globalData.userInfo,
    anotherUser: {},
    taskTypeImgs: [
      "/image/canteen.png",
      "/image/errand.png",
      "/image/expressage.png",
      "/image/team.png"
    ]
  },

  lifetimes: {
    attached: function() {
      this.setData({
        currentUser: app.globalData.userInfo
      })
      let data = this.data
      let anotherPhoneNum = 
        (data.currentUser.phoneNum == data.orderInfo.phoneNum ? 
          data.orderInfo.task.phoneNum : data.orderInfo.phoneNum)
      wx.request({
        url: 'https://www.vozl.cn/assist/profile/getUserDetails',
        method: 'POST',
        data: {
          phoneNumber: anotherPhoneNum
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          if(res.statusCode == 200){
            let tempObj = res.data
            tempObj.avatar = app.globalData.urlStart + tempObj.avatar
            this.setData({
              anotherUser: tempObj
            })
          }
        },
        fail: (res) => {console.log(res)}
      })
    },
  },
  /**
   * 组件的方法列表
   */
  methods: {
    gotoChat(){
      if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
        app.loginOrRegister()
        return
      }
      let currentUser = app.globalData.userInfo.phoneNum
      let order = this.data.orderInfo
      let isMyTask = (currentUser == order.phoneNum ? false : true)
      let toPhoneNum = (currentUser == order.phoneNum ? order.task.phoneNum : order.phoneNum)
      let url = '/pages/subordinatePage/chat/chat?toPhoneNum=' + toPhoneNum + 
        "&isCompleted=" + !(this.data.orderInfo.finishTime == "" || !this.data.orderInfo.finishTime) + "&orderId="
        + this.data.orderInfo.orderId + '&isMyTask=' + isMyTask
      this.setData({
        hasNew: false
      })
      wx.navigateTo({url})
    },

    navigateTaskDetail: function(){
      if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
        app.loginOrRegister()
        return
      }
      let url = '/pages/subordinatePage/taskDetail/taskDetail?fromPage=order&orderId=' + this.data.orderInfo.orderId + '&taskId=' + this.data.orderInfo.task.taskId
      wx.navigateTo({
        url
      })
    },
    
    delOrderItem: function(){
      wx.showModal({
        title: '提示',
        content: '确定删除吗？',
        success: (res) => {
          if (res.confirm) {
            this.triggerEvent("delOrderItem", this.data.index)
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    },

    emptyFunc(){

    }
  }
})
