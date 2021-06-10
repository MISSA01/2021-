// app.js
const webSocketUrl = "wss://www.vozl.cn/assist/websocket/"
/*
wx.request({
  url: 'https://www.vozl.cn/assist/entrance/ifNewUser',
  method: 'POST',
  data: {
    code: res.code
  },
  header: {'content-type': 'application/x-www-form-urlencoded'},
  success: (res) => {
    console.log(res)
  },
  fail: (res) => {console.log(res)}
})
*/

App({
  onLaunch() {
    this.updateApply()
    wx.login({
      success: (res) => {
        wx.request({
          url: 'https://www.vozl.cn/assist/entrance/ifNewUser',
          method: 'POST',
          data: {code: res.code},
          header: {'content-type': 'application/x-www-form-urlencoded'},
          success: (ifNewUserRes) => {
            if(ifNewUserRes.statusCode == 200){
              if(ifNewUserRes.data  || ifNewUserRes.data == 'true'){
                wx.navigateTo({
                  url: '/pages/subordinatePage/register/register?isRegister=' + ifNewUserRes.data,
                })
              }else{
                let currentTimeStamp  = Date.parse(new Date()) 
                const userInfoExpiration = wx.getStorageSync('userInfo_expiration')
                const userInfo = wx.getStorageSync('userInfo')
                if(userInfo && (currentTimeStamp < userInfoExpiration)){
                  this.globalData.userInfo = userInfo
                  this.getNewChatNum(this.globalData.userInfo.phoneNum)
                  if(! this.globalData.socketConnected){
                    this.useWebSocket(userInfo.phoneNum)
                  }
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
                          if(requestres.statusCode == 200){
                            this.globalData.userInfo = requestres.data
                            this.globalData.userInfo.avatar = this.globalData.urlStart + this.globalData.userInfo.avatar
                            if(! this.globalData.socketConnected){
                              this.useWebSocket(this.globalData.userInfo.phoneNum)
                            }
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
                }else{
                  wx.navigateTo({
                    url: '/pages/subordinatePage/register/register?isRegister=' + ifNewUserRes.data,
                  })
                }
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
      }, fail: (res) => {console.log(res)}
    })

    wx.onSocketMessage((result) => {
      let msg = JSON.parse(result.data)
      console.log(msg)
      if(msg.msg){
        return
      }
      let currentPages = getCurrentPages()
      let isInChatWithPhone = false
      for(let page of currentPages){
        if(page.route == "pages/subordinatePage/chat/chat"){
          if(page.options.orderId == msg.orderId){
            if(msg.resultType == 0){
              wx.showToast({
                title: msg.error,
                icon: 'none',
                duration: 1500,
                success: () => {
                  setTimeout(() => {
                    wx.navigateBack({
                      delta: 0
                    })
                  }, 1500)
                }
              })
              return
            }else{
              this.globalData.chatPage.updateMsgList(msg)
              isInChatWithPhone = true
            }
            break
          }
        }
      }
      if(!isInChatWithPhone){
        this.globalData.msgList.push(msg)
        this.changeRedpoint(true)
        if(this.globalData.orderPage){
          this.globalData.orderPage.changeItem(msg.orderId, true)
        }
      }
    })

    wx.onSocketClose((result) => {
      console.log("socketClose")
    })

    this.globalData.windowHeight = wx.getSystemInfoSync().windowHeight
  },

  updateApply(){
    if (wx.canIUse('getUpdateManager')) {
      const updateManager = wx.getUpdateManager()
      updateManager.onCheckForUpdate(function (res) {
        if (res.hasUpdate) {
          updateManager.onUpdateReady(function () {
            wx.showModal({
              title: '更新提示',
              content: '新版本已经准备好，是否重启应用？',
              success: function (res) {
                if (res.confirm) {
                  updateManager.applyUpdate()
                }
              }
            })
          })
          updateManager.onUpdateFailed(function () {
            wx.showModal({
              title: '已经有新版本了哟~',
              content: '新版本已经上线啦~，请您删除当前小程序，重新搜索打开哟~'
            })
          })
        }
      })
    } else {
      wx.showModal({
      title: '提示',
      content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },

  getShowCustomer(){
    return new Promise(resolve => {
      wx.request({
        url: 'https://www.vozl.cn/assist/other/getRealSpareFace',
        method: 'POST',
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          // console.log(res.data)
          this.globalData.showCustomer = (res.data || res.data == 'true')
          resolve((res.data || res.data == 'true'))
        },
        fail: (res) => {console.log(res)}
      })
    })
    // return new Promise(resolve => {
    //   resolve(false)
    // })
  },

  onHide() {
    wx.closeSocket({
      code: 1000,
    })
  },

  onShow() {
    if(this.globalData.userInfo){
      this.useWebSocket(this.globalData.userInfo.phoneNum)
    }
  },

  loginOrRegister(){
    wx.login({
      success: (res) => {
        wx.request({
          url: 'https://www.vozl.cn/assist/entrance/ifNewUser',
          method: 'POST',
          data: {code: res.code},
          header: {'content-type': 'application/x-www-form-urlencoded'},
          success: (res) => {
            // console.log(res)
            if(res.statusCode == 200){
              wx.navigateTo({
                url: '/pages/subordinatePage/register/register?isRegister=' + res.data,
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
      }, fail: (res) => {console.log(res)}
    })
  },

  updateStorageUserInfo() {
    wx.setStorageSync('userInfo', this.globalData.userInfo)
  },

  useWebSocket(userPhone){
    if(userPhone.length > 0 && userPhone != ''){
      wx.connectSocket({
        url: webSocketUrl + userPhone,
      })
    }

    wx.onSocketOpen((result) => {
      console.log("websocket connect successfully")
      this.globalData.socketConnected = true
    })
  },

  sendMsgByWebSocket(message, index, callbackFail, callbackSuc = function(){}){
    let msg = {
      toPhoneNum: message.toPhoneNum,
      chatContent: message.chatContent,
      chatType: message.chatType,
      orderId: parseInt(message.orderId)
    }
    // console.log(msg)
    wx.sendSocketMessage({
      data: JSON.stringify(msg),
      success: (res) => {
        callbackSuc()
      },
      fail: (res) => {
        callbackFail(index)
      }
    })
  },

  delMsgFromList(fromPhoneNum, orderId){
    this.globalData.msgList = this.globalData.msgList.filter((item) => {
      if(item.fromPhoneNum != fromPhoneNum){
        return item
      }
    })
    if(this.globalData.msgList.length == 0){
      this.changeRedpoint(false)
    }
    wx.request({
      url: 'https://www.vozl.cn/assist/chat/updChatState',
      method: 'POST',
      data: {
        anotherUserPhoneNum: fromPhoneNum,
        phoneNum: this.globalData.userInfo.phoneNum,
        orderId: parseInt(orderId) 
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {},
      fail: (res) => {console.log(res)}
    })
  },

  changeRedpoint(flag){
    this.globalData.hasNewMsg = flag
    for(let tarbar of this.globalData.myTarbar){
      tarbar.setData({
        showRedpoint: flag
      })
    }
  },

  getNewChatNum(phoneNum){
    wx.request({
      url: 'https://www.vozl.cn/assist/chat/getAllNewChatNum',
      method: 'POST',
      data: {
        phoneNum: phoneNum
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.data > 0){
          this.changeRedpoint(true)
        }
      },
      fail: (res) => {console.log(res)}
    })
  },

  sensitiveWord(content, callback = function(){}){
    wx.request({
      url: 'https://www.vozl.cn/assist/other/ifHasSensitiveWord',
      method: 'POST',
      data: {
        content: content
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        if(res.statusCode == 200){
          if(!res.data){
            callback()
          }else{  
            wx.showToast({
              title: '包含敏感词汇！',
              icon: 'none',
              duration: 1500
            })
          }
        }
      },
      fail: (res) => {console.log(res)}
    })
  },

  globalData: {
    showCustomer: false,
    userInfo: null,
    urlStart: "https://www.vozl.cn",
    windowHeight: 0,
    currentSelected: 0,
    blendence:[
      "red",
      "orange",
      "yellow",
      "olive",
      "green",
      "cyan",
      "blue",
      "grey"
    ],
    taskTypes:[
      "食堂带饭",
      "外卖代取",
      "快递代取",
      "组队",
      "其它"
    ],
    taskStates:[
      "等待接取",
      "进行中",
      "已完成"
    ],
    orderStates:[
      "进行中",
      "已完成"
    ],
    orderStateLabels:[
      "olive",
      "grey"
    ],
    msgList: [],
    myTarbar: [],
    hasNewMsg: false
  }
})
