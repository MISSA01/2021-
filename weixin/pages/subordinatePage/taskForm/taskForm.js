// pages/taskForm/taskForm.js
let util = require('../../../utils/util.js')
let now = util.formatTime(new Date(Date.parse(new Date()) + 30*60*1000))
let currentDate = now.substring(0,10)
let currentTime = now.substring(11,16)
let startDate = util.formatTime(new Date()).substring(0,10)
let startTime = util.formatTime(new Date()).substring(11,16)
let app = getApp()
let taskTypes = [].concat(app.globalData.taskTypes)
let blendence = [].concat(app.globalData.blendence)

Page({
  /**
   * 页面的初始数据
   */
  data: {
    isModify: false,
    blendence: blendence,
    taskTypes: taskTypes,
    currentDate: currentDate,
    currentTime: currentTime,
    startDate: startDate,
    startTime: startTime,
    currentLabelContent: "",
    commendLabel:[
      {
        labelText: "标签",
        colorClass: "yellow"
      }
    ],
    clcLength: 0,
    currentColor: 0,
    showAddLabel: false,
    showTaskPreview: false,
    taskRecommendReward: 0,
    task: {
      taskTitle: "",
      taskComplement: "",
      taskReward: 0,
      taskType: 0,
      itemCount: 0,
      taskPeoNum: 1,
      taskGetPlace: "",
      taskDestination: "",
      taskOutTime: currentDate + ' ' + currentTime,
      taskLabels: [],
      taskItems: []
    }
  },

  titleInput: function(event){
    let str = this.regulateInput(event.detail.value)
    this.setData({
      ['task.taskTitle']: str
    })
  },

  contentInput: function(event){
    let str = this.regulateInput(event.detail.value)
    this.setData({
      ['task.taskComplement']: str
    })
  },

  destinationInput: function(event){
    let str = this.regulateInput(event.detail.value)
    this.setData({
      ['task.taskDestination']: str
    })
  },

  getPlaceInput: function(event){
    let str = this.regulateInput(event.detail.value)
    this.setData({
      ['task.taskGetPlace']: str
    })
  },

  rewardFocus: function(event){
    if(this.data.task.taskReward == "0" || this.data.task.taskReward == 0){
      this.setData({
        ['task.taskReward']: ""
      })
    }
  },

  rewardInput: function(event){
    let price = event.detail.value
    if(parseFloat(price) >= 100){
      price = 100
    }
    this.setData({
      ['task.taskReward']: price
    })
  },

  countFocus: function(event){
    if(event.currentTarget.dataset.attr == "taskPeoNum"){
      this.setData({
        ['task.taskPeoNum']: ""
      })
    }else{ 
      if(this.data.task.itemCount == "0" || this.data.task.itemCount == 0){
        this.setData({
          ['task.itemCount']: ""
        })
      }

    }
  },

  countInput: function(event){
    let attr = event.currentTarget.dataset.attr
    let count = parseInt(event.detail.value)
    if(attr == "taskPeoNum"){
      this.setData({
        ['task.taskPeoNum']: count
      })
    }else{     
      if(count >= 4){
        count = 4
      }
      let len = this.data.task.taskItems.length
      if(len > count){
        this.data.task.taskItems.splice(count, len - count)
      }else{
        for(let i = len; i < count; i++){
          let temp = {
            itemdes: ""
          }
          this.data.task.taskItems.splice(i, 0, temp)
        }
      }
      this.setData({
        ['task.itemCount']: count,
        ['task.taskItems']: this.data.task.taskItems
      })
    }
  },

  weightInput: function(event){
    let weight = event.detail.value
    let key = "task.taskItems[" + event.currentTarget.dataset.index + "].estimateWeight"
    this.setData({
      [key]: weight
    })
  },

  concentRecommend: function(){
    this.setData({
      ['task.taskReward']: this.data.taskRecommendReward
    })
  },

  taskTypeChange: function(event){
    if(parseInt(event.detail.value) == 4){
      wx.showToast({
        title: '暂时还不支持其他类型！',
        icon: 'none',
        duration: 2000
      })
      return
    }
    this.setData({
      ['task.taskType']: parseInt(event.detail.value),
      ['task.taskItems']: [],
      ['task.itemCount']: 0
    })
    if(this.data.task.taskType == 0){
      this.setData({
        ['task.taskGetPlace']: "食堂"
      })
    }else if(this.data.task.taskType == 1){
      this.setData({
        ['task.taskGetPlace']: "校门口"
      })
    }else if(this.data.task.taskType == 2){
      this.setData({
        ['task.taskGetPlace']: ""
      })
    }
  },

  taskDateChange: function(event){
    let date = event.detail.value
    this.setData({
      currentDate: date,
      ['task.taskOutTime']: date + ' ' + this.data.currentTime
    })
  },

  taskTimeChange: function(event){
    let time = event.detail.value
    this.setData({
      currentTime: time,
      ['task.taskOutTime']: this.data.currentDate + ' ' + time
    })
  },

  delLabel: function(event){
    let index = event.currentTarget.dataset.index
    this.data.task.taskLabels.splice(index, 1)
    this.setData({
      ['task.taskLabels']: this.data.task.taskLabels
    })
  },

  addLabel: function(event){
    let len = this.data.task.taskLabels.length
    if(len >= 3){
      wx.showToast({
        title: '最多3个标签哦！',
        icon: 'none',
        duration: 1000
      })
      return
    }
    let insertItem = {
      labelText: "",
      colorClass: ""
    }
    if(event.currentTarget.dataset.flag){
      let index = event.currentTarget.dataset.index
      insertItem = this.data.commendLabel[index]
    }else{
      insertItem.labelText = this.data.currentLabelContent
      insertItem.colorClass = this.data.blendence[this.data.currentColor]
      if(insertItem.labelText.length == 0){
        this.setData({
          showAddLabel: false
        })
        return;
      }
    }
    this.data.task.taskLabels.splice(len, 0, insertItem)
    this.setData({
      currentLabelContent: "",
      currentColor: 0,
      clcLength: 0,
      showAddLabel: false,
      ['task.taskLabels']: this.data.task.taskLabels
    })
  },

  closeAddLabel: function(){
    this.setData({
      showAddLabel: false
    })
  },

  openAddLabel: function(){
    this.setData({
      showAddLabel: true
    })
  },

  closeTaskPreview: function(){
    this.setData({
      showTaskPreview: false
    })
  },

  openTaskPreview: function(){
    this.setData({
      showTaskPreview: true
    })
  },

  chooseColor: function(event){
    let index = event.currentTarget.dataset.index
    this.setData({
      currentColor: index
    })
  },

  inputLabelContent: function(event){
    let content = this.regulateInput(event.detail.value)
    this.setData({
      currentLabelContent: content,
      clcLength: content.length
    })
  },

  addObjItem: function(event){
    let len = this.data.task.taskItems.length
    if(len >= 4){
      wx.showToast({
        title: '物品数量最多为4！',
        icon: 'none',
        duration: 1000
      })
      return
    }
    let temp = {
      itemdes: ""
    }
    this.data.task.taskItems.push(temp)
    this.setData({
      ['task.taskItems']: this.data.task.taskItems,
      ['task.itemCount']: len + 1
    })
  },

  delObjItem: function(event){
    let index = event.currentTarget.dataset.index
    let len = this.data.task.taskItems.length
    this.data.task.taskItems.splice(index, 1)
    this.setData({
      ['task.taskItems']: this.data.task.taskItems,
      ['task.itemCount']: len - 1
    })
  },

  inputItemContent: function(event){
    let index = event.currentTarget.dataset.index
    let content = event.detail.value
    this.data.task.taskItems[index].itemdes = content
    this.setData({
      ['task.taskItems']: this.data.task.taskItems
    })
  },

  recommendPrice: function(){
    let data = this.data
    let taskContent = this.getTaskContent()
    wx.request({
      url: 'https://www.vozl.cn/assist/task/getCommendPrice',
      method: 'POST',
      data: {
        phoneNum: app.globalData.userInfo.phoneNum,
        taskTitle: data.task.taskTitle,
        taskType: parseInt(data.task.taskType),
        taskReward: parseFloat(data.task.taskReward),
        taskPeoNum: parseInt(data.task.taskPeoNum),
        taskAvaPeoNum: parseInt(data.task.taskPeoNum),
        taskTime: data.currentDate + " " + data.currentTime,
        taskContent: taskContent,
        taskState: 0
      },
      header: {'content-type': 'application/x-www-form-urlencoded'},
      success: (res) => {
        console.log(res.data)
        if(res.statusCode == 200){
          if(res.data == ""){
            this.setData({
              taskRecommendReward: "0"
            })
          }else{
            this.setData({
              taskRecommendReward: res.data
            })
          }
        }
      },
      fail: (res) => {console.log(res)}
    })   
  },

  titleBlur: function(){
    if(this.data.task.taskTitle.length == 0){
      wx.showToast({
        title: '请输入任务标题！',
        icon: 'none',
        duration: 2000
      })
      return false
    }
    return true
  },

  destinationBlur: function(){
    if(this.data.task.taskDestination.length == 0 && this.data.task.taskType != 3){
      wx.showToast({
        title: '请输入目的地！',
        icon: 'none',
        duration: 2000
      })
      return false
    }
    return true
  },

  getPlaceBlur: function(){
    if(this.data.task.taskGetPlace.length == 0 && this.data.task.taskType != 3){
      wx.showToast({
        title: '请输入取物地！',
        icon: 'none',
        duration: 2000
      })
      return false
    }
    return true
  },

  countBlur: function(){
    let len = this.data.task.taskItems.length
    this.setData({
      ['task.itemCount']: len
    })
  },

  priceBlur: function(){
    let price = this.data.task.taskReward
    if((price == 0 || (price.toString() == "")) && this.data.task.taskType != 3){
      wx.showToast({
        title: '请输入任务金额！',
        icon: 'none',
        duration: 2000
      })
      this.setData({
        ['task.taskReward']: 0
      })
      return false
    }else if(parseFloat(price) < 0.1){
      wx.showToast({
        title: '抱歉！金额不能低于0.1元！',
        icon: 'none',
        duration: 1500,
        complete: () => {
          this.setData({
            ['task.taskReward']: 0.1
          })
        }
      })
      return false
    }
    return true
  },

  regulateInput: function(str){
    return str.replace(new RegExp("[\\#,\\&,\\@,\\^,\\|]", "gm"), "")
  },

  getJudge: function(itemSettings){
      // if(util.compareVersion(wx.getSystemInfoSync().SDKVersion, "2.8.2") >= 0){
      //   return !(itemSettings['LyvxYEDSJcb4b73qWMh4d54zjZaL7FTcy_u-yE1t0I0'] == 'accept' && itemSettings['J-HnU4hnf9gxOb5opr8KGkLiAQ2G8lGKBJX10CA8qkE'] == 'accept' && itemSettings['uphQtIan7LzJKseba1CS-hyuujK6Gn9H9CQxUNxqOZ8'] == 'accept')
      // }else{
      //   return (itemSettings['LyvxYEDSJcb4b73qWMh4d54zjZaL7FTcy_u-yE1t0I0'] != "accept")
      // }
    if(util.compareVersion(wx.getSystemInfoSync().SDKVersion, "2.8.2") >= 0){
      return !(itemSettings['4AVLwPz-os3QtyxwXW3LvSWoG9_Rww_PtlzCcbPCrCE'] == 'accept' && itemSettings['UlzegeugM-sxDzl_g-8S7XXdtm1kQwEJ4ikk2l9izK4'] == 'accept' && itemSettings['e3fREFFqLa3aNuVKjGYQN0NJX-5bPDwRhLBQXhcSiMg'] == 'accept')
    }else{
      return (itemSettings['4AVLwPz-os3QtyxwXW3LvSWoG9_Rww_PtlzCcbPCrCE'] != "accept")
    }
  },

  announceTask: function(){
    let data = this.data

    if(this.data.showCustomer){
      if(!this.titleBlur() || !this.destinationBlur() || !this.getPlaceBlur()
        || !this.priceBlur()){
        return
      }
    }else{
      if(!this.titleBlur()){
        return
      }
    }

    if(data.task.taskType == 2){
      for(let item of data.task.taskItems){
        if(!item.estimateWeight || item.estimateWeight == "" 
          || item.estimateWeight.length == 0){
          wx.showToast({
            title: '预估重量不能为空！',
            icon:'none',
            duration: 1500
          })
          return
        }
      }
    }
    let taskContent = this.getTaskContent()
    let task = {
      phoneNum: app.globalData.userInfo.phoneNum,
      taskTitle: data.task.taskTitle,
      taskType: parseInt(data.task.taskType),
      taskReward: parseFloat(data.task.taskReward),
      taskPeoNum: parseInt(data.task.taskPeoNum),
      taskAvaPeoNum: parseInt(data.task.taskPeoNum),
      taskTime: data.currentDate + " " + data.currentTime,
      taskContent: taskContent,
      taskState: 0
    }

    let year = parseInt(task.taskTime.substring(0,4))
    let month = parseInt(task.taskTime.substring(5,7))
    let day = parseInt(task.taskTime.substring(8,10))
    let hour = parseInt(task.taskTime.substring(11,13))
    let minute = parseInt(task.taskTime.substring(14,16))

    let timeLimit = Date.parse(new Date(year, month-1, day, hour, minute, 0))
    let nowTime = Date.parse(new Date())

    if(timeLimit < nowTime){
      wx.showToast({
        title: '接取时限不能小于当前时间！',
        icon: 'none',
        duration: 1000
      })
      return
    }

    new Promise((resolve) => {
      wx.getSetting({
        withSubscriptions: true,
        success: (res) => {
          let itemSettings = res.subscriptionsSetting.itemSettings
          if(!itemSettings || this.getJudge(itemSettings)){
            // let tempIds = ["LyvxYEDSJcb4b73qWMh4d54zjZaL7FTcy_u-yE1t0I0"]
            let tempIds = ["4AVLwPz-os3QtyxwXW3LvSWoG9_Rww_PtlzCcbPCrCE"]
            if(util.compareVersion(wx.getSystemInfoSync().SDKVersion, "2.8.2") >= 0){
              // tempIds = tempIds.concat([
              //   "J-HnU4hnf9gxOb5opr8KGkLiAQ2G8lGKBJX10CA8qkE",
              //   "uphQtIan7LzJKseba1CS-hyuujK6Gn9H9CQxUNxqOZ8"
              // ])
              tempIds = tempIds.concat([
                "UlzegeugM-sxDzl_g-8S7XXdtm1kQwEJ4ikk2l9izK4",
                "e3fREFFqLa3aNuVKjGYQN0NJX-5bPDwRhLBQXhcSiMg"
              ])
            }
            if(this.data.showCustomer){
              wx.requestSubscribeMessage({
                tmplIds: tempIds,
                success:(res) => {
                  // if(res["LyvxYEDSJcb4b73qWMh4d54zjZaL7FTcy_u-yE1t0I0"] == "accept" ||
                  //   res["J-HnU4hnf9gxOb5opr8KGkLiAQ2G8lGKBJX10CA8qkE"] == "accept" ||
                  //   res["uphQtIan7LzJKseba1CS-hyuujK6Gn9H9CQxUNxqOZ8"] == "accept"){
                  //   wx.showToast({
                  //     title: "订阅成功！",
                  //     duration: 1500,
                  //     icon: "success"
                  //   })
                  // }
                  if(res["e3fREFFqLa3aNuVKjGYQN0NJX-5bPDwRhLBQXhcSiMg"] == "accept" ||
                    res["UlzegeugM-sxDzl_g-8S7XXdtm1kQwEJ4ikk2l9izK4"] == "accept" ||
                    res["4AVLwPz-os3QtyxwXW3LvSWoG9_Rww_PtlzCcbPCrCE"] == "accept"){
                    wx.showToast({
                      title: "订阅成功！",
                      duration: 1500,
                      icon: "success"
                    })
                  }
                  resolve(0)
                },
                fail:(res) => {
                  console.log(res)
                  if (res.errCode === 20004) {
                    wx.showModal({
                      title: "温馨提示",
                      content: `您已拒绝授权，将无法在微信中收到相关通知！是否跳转到授权界面？`,
                      showCancel: false,
                      success: () => {
                        if (res.confirm) {
                          wx.openSetting({
                            withSubscriptions: true
                          })
                        }
                        resolve(0)
                      }
                    })
                  }
                }
              })
            }else{
              resolve(0)
            }
          }else{
            resolve(0)
          }
        }
      })  
    }).then((num) => {
      data.canAnnounce = true
      if(data.canAnnounce){
        data.canAnnounce = false
        app.sensitiveWord(JSON.stringify(task), () => {
          wx.request({
            url: 'https://www.vozl.cn/assist/task/addOneTask',
            method: 'POST',
            data: {
              phoneNum: app.globalData.userInfo.phoneNum,
              taskTitle: data.task.taskTitle,
              taskType: parseInt(data.task.taskType),
              taskReward: parseFloat(data.task.taskReward),
              taskPeoNum: parseInt(data.task.taskPeoNum),
              taskAvaPeoNum: parseInt(data.task.taskPeoNum),
              taskTime: data.currentDate + " " + data.currentTime,
              taskContent: taskContent,
              taskState: 0
            },
            header: {'content-type': 'application/x-www-form-urlencoded'},
            success: (res) => {
              console.log(res)
              if(res.statusCode == 200){
                let title = '发布成功！'
                if(!this.data.showCustomer){
                  title = '新增成功！'
                }
                data.canAnnounce = true
                wx.showToast({
                  title: title,
                  icon: 'success',
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
                data.canAnnounce = true
              }
            },
            fail: (res) => {
              console.log(res)
              data.canAnnounce = true
            }
          })
        })
      }
    })
  },

  getTaskContent: function(){
    let data = this.data
    let tempList = [].concat(this.data.task.taskLabels)
    for(let i of this.data.task.taskItems){
      if(i.itemdes == ""){
        continue
      }
      let flag = true
      for(let l of this.data.task.taskLabels){
        if(l.labelText == i.itemdes){
          flag = false
          break
        }
      }
      if(flag){
        let label = {}
        label.labelText = i.itemdes
        label.colorClass = blendence[Math.floor(Math.random()*8)]
        tempList.push(label)
      }
    }

    let newTask = {
      taskComplement: data.task.taskComplement,
      taskGetPlace: data.task.taskGetPlace,
      taskDestination: data.task.taskDestination,
      taskLabels: tempList,
      taskItems: data.task.taskItems ,
      itemCount: data.task.itemCount
    }

    return JSON.stringify(newTask)
  },

  cancelModify: function(){
    wx.navigateBack({
      delta: 0,
    })
  },

  confirmModify: function(){
    let data = this.data
    if(this.data.showCustomer){
      if(!this.titleBlur() || !this.destinationBlur() || !this.getPlaceBlur()
        || !this.priceBlur()){
        return
      }
    }else{
      if(!this.titleBlur()){
        return
      }
    }
    let taskContent = this.getTaskContent()
    // console.log(taskContent)
    let task = {
      taskId: parseInt(data.task.taskId),
      phoneNum: app.globalData.userInfo.phoneNum,
      taskTitle: data.task.taskTitle,
      taskType: parseInt(data.task.taskType),
      taskReward: parseFloat(data.task.taskReward),
      taskPeoNum: parseInt(data.task.taskPeoNum),
      taskAvaPeoNum: parseInt(data.task.taskPeoNum),
      taskContent: taskContent,
      taskState: 0
    }
    app.sensitiveWord(JSON.stringify(task), () => {  
      wx.request({
        url: 'https://www.vozl.cn/assist/task/updTaskByTaskId',
        method: 'POST',
        data: {
          taskId: parseInt(data.task.taskId),
          phoneNum: app.globalData.userInfo.phoneNum,
          taskTitle: data.task.taskTitle,
          taskType: parseInt(data.task.taskType),
          taskReward: parseFloat(data.task.taskReward),
          taskPeoNum: parseInt(data.task.taskPeoNum),
          taskAvaPeoNum: parseInt(data.task.taskPeoNum),
          taskContent: taskContent,
          taskState: 0
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          console.log(res)
          if(res.statusCode == 200){
            if(res.data == "outTimeError"){
              wx.showToast({
                title: '该任务已超时限！',
                icon: 'none',
                duration: 1500,
                success: () => {
                  setTimeout(()=>{
                    wx.navigateBack({
                      delta: 2,
                    })
                  },1500)
                }
              })
            }else if(res.data == "success"){
              wx.showToast({
                title: '修改成功！',
                icon: 'success',
                duration: 1500,
                success: () => {
                  setTimeout(()=>{
                    wx.navigateBack({
                      delta: 2,
                    })
                  },1500)
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
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    now = util.formatTime(new Date(Date.parse(new Date()) + 30*60*1000))
    currentDate = now.substring(0,10)
    currentTime = now.substring(11,16)
    startDate = util.formatTime(new Date()).substring(0,10)
    startTime = util.formatTime(new Date()).substring(11,16)
    this.setData({
      currentDate: currentDate,
      currentTime: currentTime,
      startDate: startDate,
      startTime: startTime,
    })
    let isModify = options.isModify ? (options.isModify == "true") : false
    let taskType = options.taskType ? options.taskType : 0
    this.setData({
      showCustomer: app.globalData.showCustomer
    })
    if(!this.data.showCustomer){
      wx.setNavigationBarTitle({
        title: "编辑目标"
      })
    }
    if(taskType == 0){
      this.setData({
        ['task.taskGetPlace']: "食堂"
      })
    }else if(taskType == 1){
      this.setData({
        ['task.taskGetPlace']: "校门口"
      })
    }else if(taskType == 2){
      this.setData({
        ['task.taskGetPlace']: "乐收"
      })
    }
    if(isModify){
      let taskId = options.taskId
      wx.request({
        url: 'https://www.vozl.cn/assist/task/getTaskByTaskId',
        method: 'GET',
        data: {
          taskId: parseInt(taskId)
        },
        header: {'content-type': 'application/x-www-form-urlencoded'},
        success: (res) => {
          console.log(res.data)
          if(res.statusCode == 200){
            let task = util.mergeJSON(res.data, JSON.parse(res.data.taskContent))
            this.setData({
              ['task.taskId']: task.taskId,
              ['task.taskTitle']: task.taskTitle,
              ['task.taskComplement']: task.taskComplement,
              ['task.taskReward']: task.taskReward,
              ['task.taskType']: task.taskType,
              ['task.itemCount']: task.itemCount,
              ['task.taskPeoNum']: task.taskPeoNum,
              ['task.taskGetPlace']: task.taskGetPlace,
              ['task.taskDestination']: task.taskDestination,
              ['task.taskOutTime']: task.taskOutTime,
              ['task.taskLabels']: task.taskLabels,
              ['task.taskItems']: task.taskItems
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
    }
    this.setData({
      isModify: isModify,
      ['task.taskType']: taskType
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
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },
  
  emptyFunc: function(){

  }
})