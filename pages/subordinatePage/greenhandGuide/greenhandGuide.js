// pages/subordinatePage/greenhandGuide/greenhandGuide.js
let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    swiperCurrent: 0,
    swiperList: []
  },

  swiperChange: function(e) {
    let current = e.detail.current;
    this.setData({
      swiperCurrent: current
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    new Promise((resolve) => {
      wx.request({
        url: 'https://www.vozl.cn/assist/other/getManualSparePhotos',
        method: 'POST',
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          if(res.statusCode == 200){
            for(let i = 0 ; i < res.data.length; i++){
              res.data[i] = app.globalData.urlStart + res.data[i]
            }
            this.setData({
              swiperList: res.data 
            })
            resolve(res.data.length)
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
    }).then((len) => {
      // console.log(len)
      this.setData({
        swiperDots: len
      })
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

  },
})