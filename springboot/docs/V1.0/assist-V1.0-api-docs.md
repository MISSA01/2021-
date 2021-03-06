> This documentation is generated by [JApiDocs](https://japidocs.agilestudio.cn/).
---
# 聊天模块
## getMaxChatNum

*作者: May*

**请求URL**

/chat/getMaxChatNum `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
fromUserPhoneNum|string|是|发送人手机号码
toUserPhoneNum|string|是|接收人手机号码
orderId|int|是|订单ID

**返回结果**

```json
int{}
```
## addSingleUnreadChat

*作者: May*

**请求URL**

/chat/addSingleUnreadChat `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
chatId|int|否|聊天消息的自增id
fromPhoneNum|string|是|消息发送人手机号
toPhoneNum|string|是|消息接收人手机号
sentTime|date|否|发送时间
chatContent|string|是|消息内容
chatState|int|否|消息状态（1-已读，0-未读）
chatType|int|是|消息类型（1-文字，0-图片，2是二维码）
orderId|int|是|订单id

**返回结果**

```json
{}
```
## getAllChat

*作者: May*

**请求URL**

/chat/getAllChat `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum1|string|是|其中一个人的手机号码
phoneNum2|string|是|另一个人的手机号码
orderId|int|是|订单ID

**返回结果**

```json
[{
	"chatId":"int //聊天消息的自增id",
	"fromPhoneNum":"string //消息发送人手机号",
	"toPhoneNum":"string //消息接收人手机号",
	"sentTime":"date //发送时间",
	"chatContent":"string //消息内容",
	"chatState":"int //消息状态（1-已读，0-未读）",
	"chatType":"int //消息类型（1-文字，0-图片，2是二维码）",
	"orderId":"int //订单id"
}]
```
## getAllChatOfPage

*作者: May*

**请求URL**

/chat/getAllChatOfPage `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum1|string|是|其中一个人的手机号码
phoneNum2|string|是|另一个人的手机号码
pageNum|int|是|页码（从第1页开始）
pageSize|int|是|每页数
orderId|int|是|订单ID

**返回结果**

```json
[{
	"chatId":"int //聊天消息的自增id",
	"fromPhoneNum":"string //消息发送人手机号",
	"toPhoneNum":"string //消息接收人手机号",
	"sentTime":"date //发送时间",
	"chatContent":"string //消息内容",
	"chatState":"int //消息状态（1-已读，0-未读）",
	"chatType":"int //消息类型（1-文字，0-图片，2是二维码）",
	"orderId":"int //订单id"
}]
```
## getAllIndividualChat

*作者: May*

**请求URL**

/chat/getAllIndividualChat `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
fromUserPhoneNum|string|是|发送人手机号码
toUserPhoneNum|string|是|接收人手机号码
orderId|int|是|订单ID

**返回结果**

```json
[{
	"chatId":"int //聊天消息的自增id",
	"fromPhoneNum":"string //消息发送人手机号",
	"toPhoneNum":"string //消息接收人手机号",
	"sentTime":"date //发送时间",
	"chatContent":"string //消息内容",
	"chatState":"int //消息状态（1-已读，0-未读）",
	"chatType":"int //消息类型（1-文字，0-图片，2是二维码）",
	"orderId":"int //订单id"
}]
```
## getNewChatNumOfOne

*作者: May*

**请求URL**

/chat/getNewChatNumOfOne `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
fromUserPhoneNum|string|是|新消息发送人手机号码
toUserPhoneNum|string|是|新消息接收人手机号码
orderId|int|是|订单ID

**返回结果**

```json
int{}
```
## getAllNewChatNum

*作者: May*

**请求URL**

/chat/getAllNewChatNum `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|是|手机号

**返回结果**

```json
int{}
```
## updChatState

*作者: May*

**请求URL**

/chat/updChatState `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|是|该用户手机号
anotherUserPhoneNum|string|是|另一个用户手机号
orderId|int|是|订单ID

**返回结果**

```json
{}
```
# 登录注册模块
## ifNewUser

*作者: May*

**请求URL**

/entrance/ifNewUser `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
code|string|是|微信code码

**返回结果**

```json
{}
```
## login

*作者: May*

**请求URL**

/entrance/login `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
code|string|是|登录用户的微信code码

**返回结果**

```json
{
	"phoneNum":"string //电话号码",
	"userName":"string //用户名",
	"studentId":"string //学号",
	"dormNum":"int //宿舍号",
	"gender":"int //性别（1-男，0-女）",
	"userScore":"int //信誉分（满分10）",
	"avatar":"string //头像URL"
}
```
## register

*作者: May*

**请求URL**

/entrance/register `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|手机号
userName|string|是|用户名
studentId|string|是|学号
dormNum|int|否|宿舍号
gender|int|否|性别（1-男，0-女，2-未知）
avatar|string|是|头像图片的Base64字节码
code|string|是|微信code

**返回结果**

```json
{
	"phoneNum":"string //电话号码",
	"userName":"string //用户名",
	"studentId":"string //学号",
	"dormNum":"int //宿舍号",
	"gender":"int //性别（1-男，0-女）",
	"userScore":"int //信誉分（满分10）",
	"avatar":"string //头像URL"
}
```
# 订单接口
## getAllPublishOrderingByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getAllPublishOrderingByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getPublishOrderingByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getPublishOrderingByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getAllPublishOrderedByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getAllPublishOrderedByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getPublishOrderedByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getPublishOrderedByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getAllOrderingByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getAllOrderingByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getOrderingByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getOrderingByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getAllOrderedByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getAllOrderedByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getOrderedByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getOrderedByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## finishOrderByOrderId

**

**请求URL**

/orders/finishOrderByOrderId `POST` 


**返回结果**

```json
string{}
```
## getOrdersByOrderId

*作者: MISSA*

**请求URL**

/orders/getOrdersByOrderId `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
orderId|int|否|订单Id

**返回结果**

```json
{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}
```
## getAllOrdersByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getAllOrdersByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|接取人的手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getAllPublishOrdersByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getAllPublishOrdersByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|发布者手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getPublishOrdersByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getPublishOrdersByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|发布者手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getOrdersByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getOrdersByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|接取者的手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getPeoAllOrdersByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getPeoAllOrdersByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## getPeoOrdersByPhoneNum

*作者: MISSA*

**请求URL**

/orders/getPeoOrdersByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|用户手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"orderId":"int //自增主键",
	"taskId":"int //对应任务ID",
	"phoneNum":"string //接任务的人的电话",
	"receiveTime":"date //接受时间",
	"finishTime":"date //完成时间",
	"task":{
		"taskId":"int //自增主键",
		"phoneNum":"string //任务发起者电话",
		"taskTitle":"string //任务标题",
		"taskType":"int //任务类型",
		"taskReward":"float //任务奖励",
		"taskContent":"string //任务内容",
		"taskInTime":"date //任务发布时间",
		"taskOutTime":"date //任务截止时间",
		"taskState":"int //任务状态",
		"taskPeoNum":"int //任务人数",
		"taskAvaPeoNum":"int //任务人数"
	}
}]
```
## giveUpOrderByOrderId

*作者: MISSA*

**请求URL**

/orders/giveUpOrderByOrderId `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
orderId|int|否|订单ID

**返回结果**

```json
string{}
```
# 其他杂项模块
## getLatelyAnnounce

*作者: May*

**请求URL**

/other/getLatelyAnnounce `POST` 


**返回结果**

```json
[{
	"id":"int",
	"title":"string",
	"content":"string",
	"announceDate":"date",
	"managerId":"string"
}]
```
## ifHasSensitiveWord

*作者: May*

**请求URL**

/other/ifHasSensitiveWord `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
content|string|否|待测试文本内容

**返回结果**

```json
{}
```
## weiXinNotify

*作者: May*

**请求URL**

/other/weiXinNotify `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
orderId|int|是|订单id
noticeType|int|是|1-任务确认通知，2-任务完成通知，3-任务代付款通知

**返回结果**

```json
{}
```
## getManualPhotos

*作者: May*

**请求URL**

/other/getManualPhotos `POST` 


**返回结果**

```json
string[]
```
## getManualSparePhotos

*作者: May*

**请求URL**

/other/getManualSparePhotos `POST` 


**返回结果**

```json
string[]
```
## getRealFace

*作者: May*

**请求URL**

/other/getRealFace `POST` 


**返回结果**

```json
{}
```
## getRealSpareFace

*作者: May*

**请求URL**

/other/getRealSpareFace `POST` 


**返回结果**

```json
{}
```
# 测试模块
## toChat

*作者: May*

**请求URL**

/test/chat `GET` `POST` 


**返回结果**

```json
string{}
```
# 个人信息模块
## getUserDetails

*作者: May*

**请求URL**

/profile/getUserDetails `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号

**返回结果**

```json
{
	"phoneNum":"string //电话号码",
	"userName":"string //用户名",
	"studentId":"string //学号",
	"dormNum":"int //宿舍号",
	"gender":"int //性别（1-男，0-女）",
	"userScore":"int //信誉分（满分10）",
	"avatar":"string //头像URL"
}
```
## getUserDetailsByCode

*作者: May*

**请求URL**

/profile/getUserDetailsByCode `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
code|string|是|微信code

**返回结果**

```json
{
	"phoneNum":"string //电话号码",
	"userName":"string //用户名",
	"studentId":"string //学号",
	"dormNum":"int //宿舍号",
	"gender":"int //性别（1-男，0-女）",
	"userScore":"int //信誉分（满分10）",
	"avatar":"string //头像URL"
}
```
## deductScore

*作者: May*

**请求URL**

/profile/deductScore `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|被扣除用户的手机号
creditContent|string|是|被扣除原因描述
creditScore|int|是|扣除的分值

**返回结果**

```json
{}
```
## getAllItemByPhoneNumber

*作者: May*

**请求URL**

/profile/getAllItemByPhoneNumber `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号

**返回结果**

```json
[{
	"creditId":"int //扣分记录ID",
	"phoneNum":"string //扣分用户的手机号",
	"creditContent":"string //扣分行为描述",
	"creditDate":"date //扣分时间",
	"creditScore":"int //扣分分值"
}]
```
## getAllItemByPhoneNumber

*作者: May*

**请求URL**

/profile/getAllItemByPhoneNumberOfPage `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号
pageNum|int|是|页数，从第1页开始
pageSize|int|是|每页大小

**返回结果**

```json
[{
	"creditId":"int //扣分记录ID",
	"phoneNum":"string //扣分用户的手机号",
	"creditContent":"string //扣分行为描述",
	"creditDate":"date //扣分时间",
	"creditScore":"int //扣分分值"
}]
```
## updAvatar

*作者: May*

**请求URL**

/profile/updAvatar `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号
imgCode|string|是|新的头像图形code

**返回结果**

```json
{}
```
## updStudentId

*作者: May*

**请求URL**

/profile/updStudentId `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号
studentId|string|是|新的学号

**返回结果**

```json
{}
```
## updDormNum

*作者: May*

**请求URL**

/profile/updDormNum `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号
dormNum|int|是|新的宿舍楼号

**返回结果**

```json
{}
```
## updGender

*作者: May*

**请求URL**

/profile/updGender `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号
gender|int|是|新的性别（1-男性，0-女性，3-保密）

**返回结果**

```json
{}
```
## updUser

*作者: May*

**请求URL**

/profile/updUser `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|手机号
userName|string|是|用户名
studentId|string|是|用户学号
dormNum|int|是|宿舍号
gender|int|是|性别（1-男，0-女）

**返回结果**

```json
{}
```
## getAvatarByPhoneNumber

*作者: May*

**请求URL**

/profile/getAvatarByPhoneNumber `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号

**返回结果**

```json
string{}
```
## getNameByPhoneNumber

*作者: May*

**请求URL**

/profile/getNameByPhoneNumber `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号

**返回结果**

```json
string{}
```
## getScoreByPhoneNumber

*作者: May*

**请求URL**

/profile/getScoreByPhoneNumber `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号

**返回结果**

```json
int{}
```
## addSingleAdvice

*作者: May*

**请求URL**

/profile/addSingleAdvice `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|是|用户手机号
content|string|是|反馈内容

**返回结果**

```json
{}
```
## updRewardCode

*作者: May*

**请求URL**

/profile/updRewardCode `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|是|用户手机号
imgCode|string|是|新的收款码图形code

**返回结果**

```json
string{}
```
## getRewardCodeByPhoneNumber

*作者: May*

**请求URL**

/profile/getRewardCodeByPhoneNumber `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNumber|string|否|用户手机号

**返回结果**

```json
string{}
```
# 任务接口
## getAllTask

*作者: MISSA*

**请求URL**

/task/getAllTask `GET` 


**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## getAllTaskForPage

*作者: MISSA*

**请求URL**

/task/getAllTaskForPage `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
page|int|否|第几页
num|int|否|多少个

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## getTask

*作者: MISSA*

**请求URL**

/task/getTask `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskType|int|否|任务类型

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## addOneTask

*作者: MISSA*

**请求URL**

/task/addOneTask `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskTime|string|否|任务持续时长，单位分钟
taskId|int|否|自增主键
phoneNum|string|是|任务发起者电话
taskTitle|string|是|任务标题
taskType|int|是|任务类型
taskReward|float|是|任务奖励
taskContent|string|是|任务内容
taskInTime|date|是|任务发布时间
taskOutTime|date|是|任务截止时间
taskState|int|是|任务状态
taskPeoNum|int|是|任务人数
taskAvaPeoNum|int|是|任务人数

**返回结果**

```json
string{}
```
## getAllTaskByPhoneNum

*作者: MISSA*

**请求URL**

/task/getAllTaskByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|发布者手机号

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## getTaskByPhoneNum

*作者: MISSA*

**请求URL**

/task/getTaskByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|发布者手机号
taskType|int|否|任务类型

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## updTaskByTaskId

*作者: MISSA*

**请求URL**

/task/updTaskByTaskId `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskId|int|否|自增主键
phoneNum|string|是|任务发起者电话
taskTitle|string|是|任务标题
taskType|int|是|任务类型
taskReward|float|是|任务奖励
taskContent|string|是|任务内容
taskInTime|date|是|任务发布时间
taskOutTime|date|是|任务截止时间
taskState|int|是|任务状态
taskPeoNum|int|是|任务人数
taskAvaPeoNum|int|是|任务人数

**返回结果**

```json
string{}
```
## pickupTaskByTaskId

*作者: MISSA*

**请求URL**

/task/pickupTaskByTaskId `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|接取者手机号
taskId|int|否|接取的任务ID

**返回结果**

```json
string{}
```
## finishTaskByTaskId

*作者: MISSA*

**请求URL**

/task/finishTaskByTaskId `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskId|int|否|要完成的任务ID

**返回结果**

```json
string{}
```
## getTaskByTaskId

*作者: MISSA*

**请求URL**

/task/getTaskByTaskId `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskId|int|否|任务Id

**返回结果**

```json
{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}
```
## getSelectTask

*作者: MISSA*

**请求URL**

/task/getSelectTask `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
types[]|string|否|任务类型的数组

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## getSelectTaskForPage

*作者: MISSA*

**请求URL**

/task/getSelectTaskForPage `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
types[]|string|否|任务类型的数组
page|int|否|第几页
num|int|否|多少个

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## getCommendPrice

*作者: MISSA*

**请求URL**

/task/getCommendPrice `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskId|int|否|自增主键
phoneNum|string|是|任务发起者电话
taskTitle|string|是|任务标题
taskType|int|是|任务类型
taskReward|float|是|任务奖励
taskContent|string|是|任务内容
taskInTime|date|是|任务发布时间
taskOutTime|date|是|任务截止时间
taskState|int|是|任务状态
taskPeoNum|int|是|任务人数
taskAvaPeoNum|int|是|任务人数

**返回结果**

```json
string{}
```
## getCommendTask

*作者: MISSA*

**请求URL**

/task/getCommendTask `GET` 


**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## getNotPickUpTaskByPhoneNum

*作者: MISSA*

**请求URL**

/task/getNotPickUpTaskByPhoneNum `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
phoneNum|string|否|发布者手机号

**返回结果**

```json
[{
	"taskId":"int //自增主键",
	"phoneNum":"string //任务发起者电话",
	"taskTitle":"string //任务标题",
	"taskType":"int //任务类型",
	"taskReward":"float //任务奖励",
	"taskContent":"string //任务内容",
	"taskInTime":"date //任务发布时间",
	"taskOutTime":"date //任务截止时间",
	"taskState":"int //任务状态",
	"taskPeoNum":"int //任务人数",
	"taskAvaPeoNum":"int //任务人数"
}]
```
## giveUpTaskByTaskId

*作者: MISSA*

**请求URL**

/task/giveUpTaskByTaskId `POST` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskId|int|否|任务Id

**返回结果**

```json
string{}
```
## judgePickUpTaskOrNot

*作者: MISSA*

**请求URL**

/task/judgePickUpTaskOrNot `GET` 

**请求参数**

参数名|类型|必须|描述
--:|:--:|:--:|:--
taskId|int|否|任务ID
phoneNum|string|否|用户手机号

**返回结果**

```json
string{}
```
