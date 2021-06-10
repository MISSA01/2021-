// pages/chooseTaskType/chooseTaskType.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    types:[
      {
        title: "食堂带饭",
        taskType: 0,
        iconUrl: "/image/canteen.png"
      },
      {
        title: "外卖代取",
        taskType: 1,
        iconUrl: "/image/errand.png"
      },
      {
        title: "快递代取",
        taskType: 2,
        iconUrl: "/image/expressage.png"
      },
      {
        title: "组队",
        taskType: 3,
        iconUrl: "/image/team.png"
      }
    ]
  },

  goback(){
    wx.navigateBack({
      delta: 0,
    })
  },

  openTypeForm(event){
    let index = event.currentTarget.dataset.index
    let temp = this.data.types[index]
    let url = "/pages/subordinatePage/taskForm/taskForm?taskType=" + temp.taskType
    wx.redirectTo({
      url
    })
    console.log(this.data.types[index])
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