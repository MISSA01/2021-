// pages/subordinatePage/contactUs/contactUs.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    qrCodeUrl: "https://www.vozl.cn/assist/img/others/groupCode.jpg"
  },

  saveQRImage:function(event){
    let url = event.currentTarget.dataset.imgurl
    console.log(url)
    wx.getSetting({
      success: (res) => {
        if (!res.authSetting['scope.writePhotosAlbum']) {
          wx.authorize({
            scope: 'scope.writePhotosAlbum',
            success:()=> {
              wx.getImageInfo({
                src: event.currentTarget.dataset.imgurl,
                success: function (sres) {
                wx.saveImageToPhotosAlbum({
                  filePath: sres.path,
                  success: function (fres) {
                    wx.showToast({
                      title: '保存成功！',
                      icon: 'success',
                      duration: 1000
                    })
                  }
                })
                }
              })
            },
            fail: (res) =>{
              console.log(res);
            }
          })
        }else{
          wx.getImageInfo({
            src: event.currentTarget.dataset.imgurl,
            success: function (sres) {
            wx.saveImageToPhotosAlbum({
              filePath: sres.path,
              success: function (fres) {
                wx.showToast({
                  title: '保存成功！',
                  icon: 'success',
                  duration: 1000
                })
              }
            })
            }
          })
        }
      },
      fail: (res) =>{
        console.log(res);
      }
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