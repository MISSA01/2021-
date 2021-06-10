// pages/myProfile/myProfile.js
let app = getApp()

Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: app.globalData.userInfo,
    isTicking: false,
    operates:[
      {
        title:"个人详情",
        imgUrl:"/image/myProfile/user_detail.png",
        navigateUrl:"/pages/subordinatePage/userDetail/userDetail"
      },
      {
        title:"信誉积分",
        imgUrl:"/image/myProfile/reputation.png",
        navigateUrl:"/pages/subordinatePage/reputation/reputation"
      },
      {
        title:"我的任务",
        imgUrl:"/image/myProfile/setting.png",
        navigateUrl:"/pages/subordinatePage/myTask/myTask"
      },
      {
        title:"设置",
        imgUrl:"/image/myProfile/setting.png",
        navigateUrl:"/pages/subordinatePage/mySetting/mySetting"
      },
      {
        title:"新手引导",
        imgUrl:"/image/myProfile/about_us.png",
        navigateUrl:"/pages/subordinatePage/greenhandGuide/greenhandGuide"
      }
    ]
  },

  navigateToPage: function(event){
    let index = event.currentTarget.dataset.index
    if(this.data.userInfo || index == "4" || index == "3"){
      let url = this.data.operates[index].navigateUrl
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

  tickJournal: function(){
    this.setData({
      isTicking: true
    })
    setTimeout(()=>{
      this.setData({
        isTicking: false
      })
    }, 1000)
  },

  registerOrLogin: function(){
    app.loginOrRegister()
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      userInfo: app.globalData.userInfo,
      showCustomer: app.globalData.showCustomer
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
      userInfo: app.globalData.userInfo
    })
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      if(app.globalData.showCustomer){
        this.getTabBar().setData({
          currentTab: 4
        })
      }else{
        this.getTabBar().setData({
          currentTab: 3
        })
      }
    }
  },

  emptyFunc: function(){

  }
})