// custom-tab-bar/index.js
const app = getApp()

Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    currentTab: '',
    backgroundColor: "#ffffff",
    selectedColor: "#d43a3c",
    showRedpoint: false,
    list: [
      {
        "pagePath": "/pages/index/index",
        "text": "首页",
        "iconPath": "/image/tabBar/home.png",
        "selectedIconPath": "/image/tabBar/home_active.png"
      },
      {
        "pagePath": "/pages/task/task",
        "text": "任务",
        "iconPath": "/image/tabBar/task.png",
        "selectedIconPath": "/image/tabBar/task_active.png"
      },
      {},
      {
        "pagePath": "/pages/order/order",
        "text": "订单",
        "iconPath": "/image/tabBar/order.png",
        "selectedIconPath": "/image/tabBar/order_active.png"
      },
      {
        "pagePath": "/pages/myProfile/myProfile",
        "text": "我的",
        "iconPath": "/image/tabBar/myProfile.png",
        "selectedIconPath": "/image/tabBar/myProfile_active.png"
      }
    ],
    anotherList:[
      {
        "pagePath": "/pages/index/index",
        "text": "首页",
        "iconPath": "/image/tabBar/home.png",
        "selectedIconPath": "/image/tabBar/home_active.png"
      },
      {
        "pagePath": "/pages/task/task",
        "text": "目标",
        "iconPath": "/image/tabBar/task.png",
        "selectedIconPath": "/image/tabBar/task_active.png"
      },
      {
        "pagePath": "/pages/subordinatePage/taskForm/taskForm",
        "text": "新增",
        "iconPath": "/image/tabBar/order.png",
        "selectedIconPath": "/image/tabBar/order_active.png"
      },
      {
        "pagePath": "/pages/myProfile/myProfile",
        "text": "我的",
        "iconPath": "/image/tabBar/myProfile.png",
        "selectedIconPath": "/image/tabBar/myProfile_active.png"
      }
    ]
  },

  attached() {
    app.getShowCustomer().then((flag) => {
      this.setData({
        showCustomer: flag
      })
    })
    this.setData({    
      currentTab: app.globalData.currentSelected
    })
    if(app.globalData.myTarbar.indexOf(this) == -1){
      app.globalData.myTarbar.push(this)
      app.changeRedpoint(app.globalData.hasNewMsg)
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    switchNav(event) {
      let index = event.target.dataset.current
      if (this.data.currentTab === index) {
        return false
      } else if(index === 2){
        return false
      }else {
        app.globalData.currentSelected = index
        let url = event.currentTarget.dataset.url
        wx.switchTab({url})
      }
    },

    test_switchNav(event) {
      let index = event.target.dataset.current
      if (this.data.currentTab === index) {
        return false
      } else if(index === 2){
        app.globalData.currentSelected = index
        wx.navigateTo({
          url: this.data.anotherList[2].pagePath,
        })
      }else {
        app.globalData.currentSelected = index
        let url = event.currentTarget.dataset.url
        wx.switchTab({url})
      }
    },

    redirectChoose(){
      if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
        app.loginOrRegister()
        return
      }
      wx.navigateTo({
        url: '/pages/subordinatePage/chooseTaskType/chooseTaskType'
      })
    },
  }
})
