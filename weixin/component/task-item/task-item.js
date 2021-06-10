// component/task-item/task-item.js
let app = getApp()

Component({
  /**
   * 组件的属性列表
   */
  options: {
    addGlobalClass: true
  },
  properties: {
    taskInfo:{
      type: Object,
      value: {
        taskId: 0,
        taskTitle:"",
        taskReward: 0,
        taskType: 0,
        itemCount: 0,
        taskPeoNum: 0,
        taskContent:"",
        taskDestination: "",
        taskOutTime: "",
        taskLabels:[
          {
            labelText: "",
            colorClass: ""
          }
        ]
      }
    },
    myIndex:{
      type:Number
    },
    myDirection:{
      type:String
    },
    isPreview:{
      type:Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    taskImgUrl:[
      "canteen.png",
      "errand.png",
      "expressage.png",
      "team.png",
      "other.png"
    ]
  },

  lifetimes: {
    attached: function() {
      let time = this.data.taskInfo.taskOutTime
      let key = "taskInfo.taskOutTime"
      this.setData({
        [key]: time.substring(0, 16),
        showCustomer: app.globalData.showCustomer
      })
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    navigateTaskDetail: function(){
      if(!this.data.isPreview){
        if(app.globalData.userInfo == null || app.globalData.userInfo.phoneNum == ""){
          app.loginOrRegister()
          return
        }
        let position = {
          index: this.data.myIndex,
          direction: this.data.myDirection
        }
        this.triggerEvent("myPosition", position)
        let url = '/pages/subordinatePage/taskDetail/taskDetail?fromPage=task&taskId=' 
          + this.data.taskInfo.taskId
        wx.navigateTo({
          url
        })
      }
    }
  }
})
