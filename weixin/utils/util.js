const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('-')} ${[hour, minute, second].map(formatNumber).join(':')}`
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : `0${n}`
}

const deepClone = function(initalObj) {
  let obj = {};
  obj = JSON.parse(JSON.stringify(initalObj));
  return obj;
}

const taskContentJoint = function(task){
  let content = ""
  // if(task.taskTitle.length > 0){
  //   content += ("&taskTitle^" + task.taskTitle + "|")
  // }
  if(task.taskComplement.length > 0){
    content += ("&taskComplement^" + task.taskComplement + "|")
  }
  if(task.itemCount > 0){
    content += ("&itemCount^" + task.itemCount + "|")
  }
  if(task.taskDestination.length > 0){
    content += ("&taskDestination^" + task.taskDestination + "|")
  }
  if(task.taskLabels.length > 0){
    content += ("&taskLabels^")
    for(let label of task.taskLabels){
      content += ("@" + label.labelText)
      content += ("#" + label.colorClass)
    }
    content += "|"
  }
  if(task.taskItems.length > 0){
    content += ("&taskItems^")
    for(let item of task.taskItems){
      content += ("@" + item.itemdes)
    }
    content += "|"
  }

  return content
}

const taskContentSpilt = function(taskStr){
  let taskArr = taskStr.split("|")
  let task = {}
  for(let subStr of taskArr){
    if(subStr == ""){
      continue
    }
    let attr = subStr.match(/\&(\S*)\^/)[1]
    if(attr == "taskLabels"){
      let taskLabels = []
      let tempArr = subStr.match(/\^(.*)/)[1].split("@")
      for(let i = 0; i < tempArr.length; i++){
        if(tempArr[i] == ""){
          continue
        }
        let kay = tempArr[i].split("#")
        taskLabels.push({
          labelText: kay[0],
          colorClass: kay[1]
        })
      }
      task[attr] = taskLabels
      continue
    }
    if(attr == "taskItems"){
      let taskItems = []
      let tempArr = subStr.match(/\^(.*)/)[1].split("@")
      for(let i = 0; i < tempArr.length; i++){
        if(tempArr[i] == ""){
          continue
        }
        taskItems.push({
          itemdes: tempArr[i]
        })
      }
      task[attr] = taskItems
      continue
    }
    task[attr] = subStr.match(/\^(.*)/)[1]
  }
  console.log(task)
  return task
}

const mergeJSON = function(obj1, obj2){
  let tempObj = {}
  for (var key in obj1) {
    tempObj[key] = obj1[key];
  } 
  for (var key in obj2) {
    tempObj[key] = obj2[key]; 
  } 
  return tempObj;
}

const compareVersion = function(v1, v2) {
  v1 = v1.split('.')
  v2 = v2.split('.')
  var len = Math.max(v1.length, v2.length)
 
  while (v1.length < len) {
    v1.push('0')
  }
  while (v2.length < len) {
    v2.push('0')
  }
 
  for (var i = 0; i < len; i++) {
    var num1 = parseInt(v1[i])
    var num2 = parseInt(v2[i])
 
    if (num1 > num2) {
      return 1
    } else if (num1 < num2) {
      return -1
    }
  }
  return 0
}

const sentWxNotification = function(orderId, noticeType){
  wx.request({
    url: 'https://www.vozl.cn/assist/other/weiXinNotify',
    method: 'POST',
    data: {
      orderId: parseInt(orderId),
      noticeType: parseInt(noticeType)
    },
    header: {'content-type': 'application/x-www-form-urlencoded'},
    success: (res) => {
      // console.log(res)
      if(res.statusCode == 200){
        if(res.data || res.data == "true"){
          if(!(noticeType == 1 || noticeType == 2)){
            wx.showToast({
              title: '发送成功！',
              icon: 'success',
              duration: 1500
            })
          }
        }else{
          // wx.showToast({
          //   title: '只可发送一次提醒！',
          //   icon:'none',
          //   duration: 1000
          // })
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
}

module.exports = {
  formatTime,
  deepClone,
  taskContentJoint,
  taskContentSpilt,
  mergeJSON,
  compareVersion,
  sentWxNotification
}
