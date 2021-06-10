// pages/subordinatePage/reputation/reputation.js
let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    isShowRecord: false,
    userScore: 10,
    records:[]
  },

  showSummary: function(){
    this.setData({
      isShowRecord: false
    })
  },

  showRecord: function(){
    this.setData({
      isShowRecord: true
    })
    wx.request({
      url: 'https://www.vozl.cn/assist/profile/getAllItemByPhoneNumber',
      method: 'POST',
      data: {
        phoneNumber: this.data.userInfo.phoneNum
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        console.log(res)
        if(res.statusCode == 200){
          this.setData({
            records: res.data
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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let userInfo = app.globalData.userInfo
    if(userInfo){
      this.setData({
        userInfo: userInfo
      })
    }
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
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  }
})