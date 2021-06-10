// pages/subordinatePage/userDetail/userDetail.js
const utils = require('../../../utils/util')
let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    modifyStuId: false,
    userInfo: {},
    genders:[
      "女",
      "男",
      "保密"
    ],
    dormNums:["1号楼","2号楼","3号楼","4号楼","5号楼","6号楼","保密"],
    hasQRCode: false
  },

  genderChange: function(event){
    let key = "userInfo." + event.target.dataset.attr
    this.setData({
      [key]: parseInt(event.detail.value)
    })
    this.updateUserInfo()
  },

  dormNumChange: function(event){
    let key = "userInfo." + event.target.dataset.attr
    this.setData({
      [key]: parseInt(event.detail.value) + 1
    })
    this.updateUserInfo()
  },

  inputChange: function(event){
    let key = "userInfo.studentId"
    this.setData({
      [key]: event.detail.value
    })
  },

  openChangeStuId: function(){
    this.setData({
      modifyStuId: true
    })
  },

  closeChangeStuId: function(){
    this.setData({
      modifyStuId: false
    })
  },

  confirmChange: function(){
    this.closeChangeStuId()
    this.updateUserInfo()
  },

  updateUserInfo: function(){
    let tempObj = this.data.userInfo
    wx.request({
      url: 'https://www.vozl.cn/assist/profile/updUser',
      method: 'POST',
      data: {
        phoneNumber: tempObj.phoneNum,
        studentId: tempObj.studentId,
        userName: tempObj.userName,
        dormNum: tempObj.dormNum,
        gender: parseInt(tempObj.gender) ,        
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.statusCode == 200){
          console.log(res)
          app.globalData.userInfo = utils.deepClone(this.data.userInfo)
          app.updateStorageUserInfo()
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

  uploadQRCode: function(){
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album'],
      success: (res) => {       
        const tempFilePaths = res.tempFilePaths
        wx.setStorageSync('tempFilePaths', tempFilePaths)
        new Promise((resolve, reject) => {  
          wx.getFileSystemManager().readFile({
            filePath: tempFilePaths[0],
            encoding: 'base64',
            success: res => {
              resolve('data:image/png;base64,' + res.data + this.data.userInfo.phoneNum)
            },
            fail: (res) => {
              console.log(res)
            }
          })
        }).then((imgContent) => {
          wx.request({
            url: 'https://www.vozl.cn/assist/profile/updRewardCode',
            method: 'POST',
            data: {
              phoneNumber: this.data.userInfo.phoneNum,
              imgCode: imgContent
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              if(res.statusCode == 200){
                wx.showToast({
                  title: '上传成功！',
                  icon: 'success',
                  duration: 1500,
                  success: () => {
                    this.setData({
                      hasQRCode: true
                    })
                    if(this.data.fromPage == 'chat'){
                      setTimeout(() => {
                        wx.navigateBack({
                          delta: 0
                        })
                      }, 1500)
                    }
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
      fail: (res) =>{console.log("取消")}
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let fromPage = options.fromPage
    if(fromPage){
      this.data.fromPage = fromPage
    }
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
    wx.request({
      url: 'https://www.vozl.cn/assist/profile/getRewardCodeByPhoneNumber',
      method: 'POST',
      data: {
        phoneNumber: this.data.userInfo.phoneNum
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.statusCode == 200){
          if(res.data){
            this.setData({
              hasQRCode: true
            })
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
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  emptyFunc: function(){
    
  }
})