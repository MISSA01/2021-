// pages/subordinatePage/register/register.js
let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{
      phoneNum: "",
      studentId: "",
      dormNum: 7,
      gender: 2
    },
    genders:[
      "女",
      "男",
      "保密"
    ],
    dormNums:["1号楼","2号楼","3号楼","4号楼","5号楼","6号楼","保密"],
    isRegister: true,
    canUse: false
  },

  phoneNumInput: function(event){
    let key = "userInfo.phoneNum"
    let phoneNum = event.detail.value
    this.setData({
      [key]: phoneNum
    })
  },

  phoneNumBlur: function(){
    let phoneNum = this.data.userInfo.phoneNum
    if(phoneNum.length == 0){
      wx.showToast({     
        title: '手机号码不能为空',
        duration: 2000,
        icon:'none'
      })
      return false
    }
    if (!(/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(parseInt(phoneNum)))) {
      wx.showToast({     
        title: '手机号码有误',
        duration: 1500,
        icon:'none'
      })
      return false
    }
    return true
  },

  studentIdInput: function(event){
    let key = "userInfo.studentId"
    this.setData({
      [key]: event.detail.value
    })
  },

  studentIdBlur: function(){
    let studentId = this.data.userInfo.studentId
    if (studentId.length == 0) {
      wx.showToast({     
        title: '学号不能为空',
        duration: 2000,
        icon:'none'
      })
      return false
    }
    if (!(/^\d{11,15}$/.test(parseFloat(studentId)))) {
      wx.showToast({     
        title: '学号有误',
        duration: 2000,
        icon:'none'
      })
      return false
    }
    return true
  },

  genderChange: function(event){
    let key = "userInfo.gender"
    this.setData({
      [key]: event.detail.value
    })
  },

  dormNumChange: function(event){
    let key = "userInfo.dormNum"
    this.setData({
      [key]: parseInt(event.detail.value) + 1
    })
  },

  onRegitser: function(){
    if(this.phoneNumBlur() && this.studentIdBlur()){
      wx.getUserProfile({
        lang: "zh_CN",
        desc: "展示个人信息",
        success: (getres) => {
          let comeback = true
          let tempInfo = getres.userInfo
          let currentInfo = this.data.userInfo
          tempInfo.phoneNum = currentInfo.phoneNum
          tempInfo.studentId = currentInfo.studentId
          tempInfo.dormNum = currentInfo.dormNum
          tempInfo.gender = parseInt(currentInfo.gender)
          new Promise((resolve, reject)=>{
            wx.downloadFile({
              url: tempInfo.avatarUrl,
              success: (downloadres) => {
                wx.getFileSystemManager().readFile({
                  filePath: downloadres.tempFilePath,
                  encoding: 'base64',
                  success: res => {
                    resolve('data:image/png;base64,' + res.data)
                  }
                })
              }
            })
          }).then( imgdata => {
            wx.login({
              success: (loginres) => {
                wx.request({
                  url: 'https://www.vozl.cn/assist/entrance/register',
                  method: 'POST',
                  data: {
                    code: loginres.code,
                    phoneNumber: tempInfo.phoneNum,
                    userName: tempInfo.nickName,
                    studentId: tempInfo.studentId,
                    dormNum: tempInfo.dormNum,
                    gender: tempInfo.gender,
                    avatar: imgdata
                  },
                  header: {'content-type': 'application/x-www-form-urlencoded'},
                  success: (requestres) => {
                    console.log(requestres)
                    if(requestres.data == ""){
                      wx.showToast({
                        title: '该手机号已被注册！',
                        icon: 'none',
                        duration: 1500
                      })
                      comeback = false
                      return
                    }
                    if(requestres.statusCode == 200){
                      app.globalData.userInfo = requestres.data
                      app.globalData.userInfo.avatar = app.globalData.urlStart + app.globalData.userInfo.avatar
                      if(!app.globalData.socketConnected){
                        app.useWebSocket(app.globalData.userInfo.phoneNum)
                      }
                      wx.showToast({
                        title: '注册成功！',
                        icon: 'none',
                        duration: 1000
                      })
                    }else{
                      wx.showToast({
                        title: '抱歉，请求失败！',
                        icon: 'none',
                        duration: 1500
                      })
                      comeback = false
                    }
                    if(comeback){
                      let secessionExpiration = Date.parse(new Date()) + 7*24*60*60*1000
                      wx.setStorageSync('userInfo', app.globalData.userInfo)
                      wx.setStorageSync('userInfo_expiration', secessionExpiration)
                      wx.redirectTo({
                        url: '/pages/subordinatePage/greenhandGuide/greenhandGuide',
                      })
                    }       
                  },fail: (requestres) => {console.log(requestres)}
                })      
              },fail: (loginresres) => {console.log(loginresres)}
            })
          })
        },
        fail: (res) => {
          console.log(res)
          wx.showModal({
            title: '授权提示',
            content: '小程序需要您的微信授权才能使用哦~',
            showCancel: false,
            confirmText: '确定'
          })
        }
      })        
    }
  },

  onLogin: function(){
    wx.login({
      success: (loginres) => {
        wx.request({
          url: 'https://www.vozl.cn/assist/entrance/login',
          method: 'POST',
          data: {
            code: loginres.code
          },
          header: {'content-type': 'application/x-www-form-urlencoded'},
          success: (requestres) => {
            console.log(requestres)
            if(requestres.statusCode == 200){
              app.globalData.userInfo = requestres.data
              app.globalData.userInfo.avatar = app.globalData.urlStart + app.globalData.userInfo.avatar
              if(! app.globalData.socketConnected){
                app.useWebSocket(app.globalData.userInfo.phoneNum)
              }
              let secessionExpiration = Date.parse(new Date()) + 7*24*60*60*1000
              wx.setStorageSync('userInfo', app.globalData.userInfo)
              wx.setStorageSync('userInfo_expiration', secessionExpiration)
              app.getNewChatNum(app.globalData.userInfo.phoneNum)
              wx.switchTab({
                url: '/pages/index/index',
              })
            }else{
              wx.showToast({
                title: '抱歉，请求失败！',
                icon: 'none',
                duration: 1500
              })
            }
          },fail: (requestres) => {console.log(requestres)}
        })      
      },fail: (loginresres) => {console.log(loginresres)}
    })
  },

  cancelRegiste: function(){
    wx.navigateBack({
      delta: 0
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let flag = options.isRegister
    if(typeof flag != 'undefined'){
      this.setData({
        isRegister: (flag == 'true')
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

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  }
})