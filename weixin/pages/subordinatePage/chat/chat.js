// pages/chat/chat.js
const util = require("../../../utils/util")
const app = getApp()
let windowHeight = wx.getSystemInfoSync().windowHeight
let _animation
let tapCount = 0

Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    toUser:{},
    messageList:[],
    inputValue: "",
    // isCompleted: true,
    isShowMask: false,
    showPlus: true,
    showKeyboard: false,
    showAddition: false,
    isSendImging: false,
    isPreviewImging: false,
    toView: "",
    bottom: "0px",
    showHeight: "100%",
    keyHeight: 0,
    footerHeight: 0,
    taskTypes: app.globalData.taskTypes,
    orderInfo:{
      orderId:0,
      phoneNum:"",
      receiveTime:"",
      finishTime:"",
      taskId:"",
      task:{
        taskId:0,
        phoneNum:"",
        taskTitle:"",
        taskType:0,
        taskReward:0,
        taskContent:"",
        taskInTime:"",
        taskOutTime:"",
        taskState:1,
        taskPeoNum:""
      }
    },
    animation: ''
  },

  bindKeyInput: function (event){
    let content = event.detail.value
    let flag = true
    if(content.length != 0){
      flag = false
    }
    this.setData({
      inputValue: content,
      showPlus: flag
    })
  },

  moveToBottom: function(){
    setTimeout(() => {
      this.setData({
        toView: "msg-" + (this.data.messageList.length - 1),
      })
    }, 100)
  },

  sendMessage: function(){
    let newList = this.data.messageList
    let newMsg = {
      fromPhoneNum:this.data.userInfo.phoneNum,
      toPhoneNum:this.data.toUser.phoneNum,
      chatContent:this.data.inputValue,
      orderId: this.data.orderId,
      chatState:"",
      sentTime:"",
      chatType:1
    }
    newList.push(newMsg)
    let index = this.data.messageList.length - 1
    app.sensitiveWord(JSON.stringify(newMsg), () => {
      app.sendMsgByWebSocket(newMsg, index, (index) => {
        this.showWarningImg(index)
      })
    })
    this.setData({
      inputValue: "",
      showPlus: true,
      messageList: newList,
      showAddition: false
    })
    this.tapCountChange()
    this.moveToBottom()
  },

  focus: function(event){
    this.data.keyHeight = event.detail.height;
    this.setData({
      bottom: this.data.keyHeight+"px",
      showHeight: windowHeight-this.data.keyHeight-this.data.footerHeight+"px",
      showAddition: false,
      showKeyboard: true
    })
    this.tapCountChange()
    this.moveToBottom()
  },
  
	blur: function(event){
		this.setData({
      bottom: "0px",
      showHeight: windowHeight-this.data.footerHeight+"px",
    })
    this.moveToBottom()
	},

  changeSendImg: function(flag){
    this.setData({
      isSendImging: flag
    })
  },

  changePreviewImg: function(flag){
    this.setData({
      isPreviewImging: flag
    })
  },

  closeKeyboard: function(){
    this.setData({
      showKeyboard: false
    })
  },

  taggleAddition: function(){
    this.setData({
      showAddition: !this.data.showAddition      
    })
    this.tapCountChange()
  },

  tapCountChange: function(){
    if(this.data.showAddition){
      tapCount = 1
    }else{
      tapCount = 0
    }
    this.setData({
      animation: _animation.rotate(45 * tapCount).step().export()
    })
  },

  openMask: function(){
    this.setData({
      isShowMask: true,
      showAddition: false
    })
    this.tapCountChange()
  },

  closeMask: function(){
    this.setData({
      isShowMask: false
    })
  },

  chooseImage: function(){
    wx.showActionSheet({
      itemList: ['拍照', '相册'],
      success: (res) => {
        if (!res.cancel) {
          this.chooseImageFrom(res.tapIndex)
        }
      },
      fail: (res) => {
        console.log('取消')
      }
    })
  },

  chooseImageFrom: function(tapIndex) {
    this.changeSendImg(true)
    const sourceType = ['camera', 'album']
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: [sourceType[tapIndex]],
      success: (res) => {       
        const tempFilePaths = res.tempFilePaths
        wx.setStorageSync('tempFilePaths', tempFilePaths)
        this.sendImage(tempFilePaths[0])
        this.changeSendImg(false)
      },
      fail: (res) =>{
        console.log("取消")
        this.changeSendImg(false)
      }
    })
  },

  sendImage: function(path){
    let tempChatObj = {
      fromPhoneNum:this.data.userInfo.phoneNum,
      toPhoneNum:this.data.toUser.phoneNum,
      orderId:this.data.orderId,
      chatContent: path,
      chatState: "",
      sentTime: "",
      chatType: 0
    }
    new Promise((resolve, reject) => {  
      wx.getFileSystemManager().readFile({
        filePath: path,
        encoding: 'base64',
        success: res => {
          resolve('data:image/png;base64,' + res.data)
        },
        fail: (res) => {
          console.log(res)
        }
      })
    }).then((imgContent) => {
      let len = 0
      this.getNewMessage(() => {
        len = this.data.messageList.length
        this.data.messageList.splice(len, 0, util.deepClone(tempChatObj))
        this.setData({
          messageList: this.data.messageList,
          showAddition: false
        })
        this.tapCountChange()
        tempChatObj.chatContent = imgContent
        app.sensitiveWord(JSON.stringify(tempChatObj), () => {
          app.sendMsgByWebSocket(tempChatObj, len, (index) => {
            this.showWarningImg(index)
          })
        })
        this.moveToBottom()
      })
    })
  },

  sendQRCode: function(){
    if(!this.data.isMyTask){
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
              let tempChatObj = {
                fromPhoneNum:this.data.userInfo.phoneNum,
                toPhoneNum:this.data.toUser.phoneNum,
                orderId:this.data.orderId,
                chatContent: app.globalData.urlStart + res.data,
                chatState: "",
                sentTime: "",
                chatType: 2
              }
              this.data.messageList.push(util.deepClone(tempChatObj))
              this.setData({
                messageList: this.data.messageList,
                showAddition: false
              })
              let len = this.data.messageList.length
              app.sensitiveWord(JSON.stringify(tempChatObj), () => {
                app.sendMsgByWebSocket(tempChatObj, len, () => {
                  wx.showToast({
                    title: '抱歉，发送失败，请再次发送！',
                    icon: 'none',
                    duration: 1500
                  })
                }, () => {
                  util.sentWxNotification(this.data.orderId, 3)
                })
              })
              this.tapCountChange()
              this.moveToBottom()
            }else{
              wx.showModal({
                title: '温馨提示',
                content: '您当前未上传收款码，无法发送收款提醒，是否上传收款码？',
                success: function (res) {
                  if (res.confirm) {
                    wx.navigateTo({
                      url: '/pages/subordinatePage/userDetail/userDetail?fromPage=chat',
                    })
                  }
                }
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
    }else{
      wx.showToast({
        title: '您是任务发布者，不能提起收款哦！',
        icon: 'none',
        duration: 1000
      })
    }
  },

  getOrderDetail: function(){
    wx.request({
      url: 'https://www.vozl.cn/assist/orders/getOrdersByOrderId',
      method: 'GET',
      data: {
        orderId: this.data.orderId
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        console.log(res.data)
        if(res.statusCode == 200){
          let task = util.mergeJSON(res.data.task, JSON.parse(res.data.task.taskContent))
          res.data.task = task
          this.setData({
            orderInfo: res.data,
            isShowMask: true,
            showAddition: false
          })
          this.tapCountChange()
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

  completeOrder: function(){
    wx.showModal({
      title: '提示',
      content: '确定完成吗？任务完成后将无法再发送消息！',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: 'https://www.vozl.cn/assist/orders/finishOrderByOrderId',
            method: 'POST',
            data: {
              orderId: this.data.orderId
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              console.log(res)
              if(res.statusCode == 200){
                wx.showToast({
                  title: '任务完成！',
                  icon: 'none'
                })
                this.setData({
                  isShowMask: false,
                  isCompleted: true
                })
                wx.getSetting({
                  withSubscriptions: true,
                  success: (res) => {
                    let itemSettings = res.subscriptionsSetting.itemSettings
                    if(!itemSettings || itemSettings['uphQtIan7LzJKseba1CS-hyuujK6Gn9H9CQxUNxqOZ8'] == "accept"){
                      util.sentWxNotification(this.data.orderId, 2)
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
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  giveupOrder: function(){
    wx.showModal({
      title: '提示',
      content: '确定放弃吗？任务放弃后将删除相关订单',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: 'https://www.vozl.cn/assist/orders/giveUpOrderByOrderId',
            method: 'POST',
            data: {
              orderId: parseInt(this.data.orderId)
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              console.log(res)
              if(res.statusCode == 200){
                wx.showToast({
                  title: '放弃任务！即将退出聊天窗口！',
                  icon: 'none',
                  success: () => {
                    setTimeout(() => {
                      wx.navigateBack({
                        delta: 0,
                      })
                    }, 1500)            
                  }
                })
                this.setData({
                  isShowMask: false
                })
              }
            },
            fail: (res) => {console.log(res)}
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  resendMsg: function(event){
    app.useWebSocket(this.data.userInfo.phoneNum)
    let index = event.currentTarget.dataset.index
    let item = event.currentTarget.dataset.item
    this.data.messageList.pop(index)
    this.getNewMessage(() => {
      if(item.chatType == 0){
        this.sendImage(item.chatContent)
      }else if(item.chatType == 1){
        let newMsg = {
          fromPhoneNum:item.fromPhoneNum,
          toPhoneNum:item.toPhoneNum,
          chatContent:item.chatContent,
          orderId: item.orderId,
          chatState:"",
          sentTime:"",
          chatType:1,
        }
        this.data.messageList.push(newMsg)
        let index = this.data.messageList.length - 1
        app.sendMsgByWebSocket(newMsg, index, (index) => {
          this.showWarningImg(index)
        })
      }
      this.setData({
        messageList: this.data.messageList
      })
      this.moveToBottom()
    })
  },

  adaptImageSize: function(event){
    let index = event.currentTarget.dataset.index
    let {height, width} = event.detail
    let tempObj = this.data.messageList[index]
    if(height > width){
      let newHeight = height > 510 ? 510 : height
      tempObj.height = newHeight + "rpx"
      tempObj.width = newHeight * (width / height) + "rpx"
    }else{
      let newWidth = width > 510 ? 510 : width
      tempObj.height = newWidth * (height / width) + "rpx"
      tempObj.width = newWidth + "rpx"
    }   
    this.setData({
      messageList: this.data.messageList
    })
  },

  previewChatImage: function(event) {
    this.changePreviewImg(true)
    let index = event.currentTarget.dataset.index
    let currentUrl = this.data.messageList[index].chatContent
    let imgList = []
    for(let item of this.data.messageList){
      if(item.chatType == 0 || item.chatType == 2){
        imgList.push(item.chatContent + "?time=" + Date.parse(new Date()))
      }
    }
    console.log(imgList)
    wx.previewImage({
      current: currentUrl,
      urls: imgList
    })
  },

  toViewBottomFun: function() {
    wx.createSelectorQuery().select('#viewCommunicationBody').boundingClientRect((rect) => {
      wx.pageScrollTo({
        scrollTop: rect.height,
        duration: 100 
      })
      that.setData({
        scrollTop: rect.height - this.data.scrollTop
      });
    }).exec();
  },

  getNewMessage: function(callback){
    let len = this.data.messageList.length
    wx.request({
      url: 'https://www.vozl.cn/assist/chat/getAllChat',
      method: 'POST',
      data: {
        phoneNum1: this.data.userInfo.phoneNum,
        phoneNum2: this.data.toUser.phoneNum,
        orderId: parseInt(this.data.orderId) 
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        for(let i = len; i < res.data.length; i++){
          if(res.data[i].chatType == 0){
            res.data[i].chatContent = app.globalData.urlStart
               + res.data[i].chatContent
          }
          this.data.messageList.push(res.data[i])
        }
        this.setData({
          messageList: this.data.messageList
        })
        if(callback){
          callback()
        }
      },
      fail: (res) => {console.log(res)}
    })
    // console.log(this.data.messageList)
  },

  updateMsgList: function(currentMsg){
    if(currentMsg.chatType == 0){
      currentMsg.chatContent = app.globalData.urlStart + currentMsg.chatContent
    }
    this.data.messageList.push(currentMsg)
    this.setData({
      messageList: this.data.messageList
    })
    this.moveToBottom()
  },

  showWarningImg: function(index){
    this.data.messageList[index].failSend = true
    this.setData({
      messageList: this.data.messageList
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let toPhoneNum = options.toPhoneNum
    let isCompleted = options.isCompleted == "true" ? true : false
    let isMyTask = options.isMyTask == "true" ? true : false
    this.data.orderId = options.orderId
    this.setData({
      isCompleted: isCompleted,
      isMyTask: isMyTask,
      userInfo: app.globalData.userInfo,
      orderId: options.orderId
    })
    if(!isCompleted){
      let query = wx.createSelectorQuery()
      query.select('.chat-footer').boundingClientRect(rect=>{
        this.data.footerHeight = rect.height
        this.setData({
          bottom: "0px",
          showHeight: windowHeight-this.data.footerHeight+"px"
        })
      }).exec()
    }else{
      let query = wx.createSelectorQuery()
      query.select('.chat-footer-completed').boundingClientRect(rect=>{
        this.data.footerHeight = rect.height
        this.setData({
          bottom: "0px",
          showHeight: windowHeight-this.data.footerHeight+"px"
        })
      }).exec()
    }
    wx.request({
      url: 'https://www.vozl.cn/assist/profile/getUserDetails',
      method: 'POST',
      data: {
        phoneNumber: toPhoneNum
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        let tempObj = res.data
        tempObj.avatar = app.globalData.urlStart + tempObj.avatar
        this.setData({
          userInfo: app.globalData.userInfo,
          toUser: util.deepClone(tempObj)
        })
        // console.log(this.data.userInfo)
        // console.log(this.data.toUser)
        this.getNewMessage(() => {
          if(app.globalData.orderPage){
            app.globalData.orderPage.changeItem(this.data.orderId, false)
          }
          this.moveToBottom()
        })
        wx.setNavigationBarTitle({
          title: ((isMyTask ? '接取：':'发布：') + this.data.toUser.userName) 
        })
      },
      fail: (res) => {console.log(res)}
    })
    app.globalData.chatPage = this
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.moveToBottom()
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if((!this.data.isSendImging) && (!this.data.isPreviewImging)){
      this.getNewMessage(()=>{
        this.moveToBottom()
      })
    }
    if(!this.data.isPreviewImging){
      this.moveToBottom()
    }   
    if(this.data.isPreviewImging){
      this.changePreviewImg(false)
    }
    _animation = wx.createAnimation({
      duration: 400,
      timingFunction: 'linear',
      delay: 0,
      transformOrigin: '50% 50% 0'
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    app.delMsgFromList(this.data.toUser.phoneNum, this.data.orderId)
  },

  onUnload: function(){
    app.delMsgFromList(this.data.toUser.phoneNum, this.data.orderId)
  },

  emptyFun: function(){

  }
})