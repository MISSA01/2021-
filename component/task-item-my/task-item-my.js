// component/task-item-my/task-item-my.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    orderInfo:{
      type: Object,
      value: {
        orderId:0,
        phoneNum:"",
        receiveTime:"",
        finishTime:"",
        taskId:0
      }
    },
    taskInfo: {
      type: Object,
      value: {
        taskId:0,
        phoneNum:"",
        taskTitle:"",
        taskType:0,
        taskReward:0,
        taskContent:"",
        taskInTime:"",
        taskOutTime:"",
        taskState:0,
        taskPeoNum:1
      }
    },
    currentActive:{
      type:Number
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    taskTypeImg:[
      "/image/canteen.png",
      "/image/errand.png",
      "/image/expressage.png",
      "/image/team.png",
    ],
    taskStateImg:[
      "/image/subordinate/myTask/ongoing.png",
      "/image/subordinate/myTask/complete.png"
    ],
  },

  /**
   * 组件的方法列表
   */
  methods: {
    navigateTaskDetail: function(event){
      let taskId = this.data.taskInfo.taskId
      let orderId = "-1"   
      if(this.data.orderInfo){
        orderId = this.data.orderInfo.orderId
      }
      let url = '/pages/subordinatePage/taskDetail/taskDetail?fromPage=myTask&taskId=' + taskId + '&orderId=' + orderId
      wx.navigateTo({
        url
      })
    },
  }
})
