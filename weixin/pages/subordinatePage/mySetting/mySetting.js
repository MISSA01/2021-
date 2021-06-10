// pages/subordinatePage/mySetting/mySetting.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    settings:[
      {
        settingName: "意见反馈",
        settingUrl: "/pages/subordinatePage/feedback/feedback"
      },
      {
        settingName: "联系我们",
        settingUrl: "/pages/subordinatePage/contactUs/contactUs"
      }
    ]
  },

  navigateToSubSetting: function(event){
    let index = event.currentTarget.dataset.index
    let url = this.data.settings[index].settingUrl
    wx.navigateTo({
      url
    })
  }
})