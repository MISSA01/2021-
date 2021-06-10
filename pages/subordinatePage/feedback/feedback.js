// pages/subordinatePage/feedback/feedback.js
let app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    feedbackContent: "",
    wordCount: 0
  },

  contentInput:function(event){
    // console.log(event.detail.value)
    this.setData({
      feedbackContent: event.detail.value,
      wordCount: event.detail.value.length
    })
  },

  submitFeedback: function(event){
    app.sensitiveWord(this.data.feedbackContent, () => {
      wx.request({
        url: 'https://www.vozl.cn/assist/profile/addSingleAdvice',
        method: 'POST',
        data: {
          phoneNum: app.globalData.userInfo.phoneNum,
          content: this.data.feedbackContent
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          console.log(res)
          if(res.statusCode == 200){
            wx.showToast({
              title: "提交成功",
              icon: "success",
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
              title: '抱歉，请求失败！',
              icon: 'none',
              duration: 1500
            })
          }
        },
        fail: (res) => {console.log(res)}
      })
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  }
})