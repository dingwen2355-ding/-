import $ from 'jquery'

let webSocket = new WebSocket('ws://localhost:9002')
let loginRspData
let fullDuplexSingleCall
let halfDuplexSingleCall
let normalGroupCall
let dynamicGroupCall
let temporaryGroupCall
let fullDuplexSingleComingCall
let halfDuplexSingleComingCall
let normalGroupComingCall
// let loginInfo = {"msgCode": 10001, "data": {"userId": "zj", "password": "111111", "enterpriseId": "caltta4567", "serverIp": "10.194.23.22", "localIp": "10.194.26.70"}};

function getUserIP(onNewIP) { //  onNewIp - your listener function for new IPs
  // compatibility for firefox and chrome
  let myPeerConnection = window.RTCPeerConnection || window.mozRTCPeerConnection || window.webkitRTCPeerConnection
  // eslint-disable-next-line new-cap
  let pc = new myPeerConnection({
    iceServers: []
  })
  let noop = function() {}
  let localIPs = {}
  let ipRegex = /([0-9]{1,3}(\.[0-9]{1,3}){3}|[a-f0-9]{1,4}(:[a-f0-9]{1,4}){7})/g
  function iterateIP(ip) {
    if (!localIPs[ip]) onNewIP(ip)
    localIPs[ip] = true
  }
  // create a bogus data channel
  pc.createDataChannel('')
  // create offer and set local description
  pc.createOffer().then(function(sdp) {
    sdp.sdp.split('\n').forEach(function(line) {
      if (line.indexOf('candidate') < 0) return
      line.match(ipRegex).forEach(iterateIP)
    })
    pc.setLocalDescription(sdp, noop, noop)
  }).catch(function(reason) {
    // An error occurred, so handle the failure to connect
  })
  // sten for candidate events
  pc.onicecandidate = function(ice) {
    if (!ice || !ice.candidate || !ice.candidate.candidate || !ice.candidate.candidate.match(ipRegex)) return
    ice.candidate.candidate.match(ipRegex).forEach(iterateIP)
  }
}

// 连接错误事件
webSocket.onerror = function(event) {
  console.log('Connection error')
}

// 连接打开事件
webSocket.onopen = function(event) {
  console.log('Connection established')
  loginReq()
}

// 连接消息事件
webSocket.onmessage = function(event) {
  onMessage(event)
}

// 连接关闭事件
webSocket.onclose = function(event) {
  console.log('Connection close')
}

function onMessage(event) {
  console.log('new message: ' + event.data)

  // 解析消息
  let msg = JSON.parse(event.data)
  switch (msg.msgCode) {
    case 12101:
    {
      fullDuplexSingleComingCall = msg.data
      singleComingCallReq('fullDuplexSingleComingCall')
      break
    }
    case 12102:
    {
      halfDuplexSingleComingCall = msg.data
      singleComingCallReq('halfDuplexSingleComingCall')
      break
    }
    case 20001:
    {
      loginRsp(msg.data)
      break
    }
    case 20002:
    {
      logoutRsp(msg.data)
      break
    }

    case 21101: // 呼叫应答
    case 21102:
    case 21103:
    case 21104:
    case 21105:
    {
      callRsp(msg)
      break
    }

    case 21303: // 设置当前呼叫应答
    {
      // {"msgCode":21303,"data":{"result":0,"channelId":0}}
      break
    }
    case 21304: // 设置播放声音应答
    {
      // {"msgCode":21304,"data":{"result":0,"operType":1,"channelId":0}}
      break
    }
    case 21305: // 操作话权应答
    {
      // {"msgCode":21305,"data":{"result":0,"operType":0,"channelId":0}}
      break
    }
    case 21306: // 设置本地录音应答
    {
      // {"msgCode":21306,"data":{"result":0,"operType":1,"channelId":0,"tmpGroupNumber":-1}}
      break
    }
    case 21307: // 查询本地录音应答
    {
      procLocalAudioRecordRsp(msg.data)
      break
    }
    case 21308: // 静音应答
    {
      // {"msgCode":21308,"data":{"result":0,"operType":1}}
      // {"msgCode":21308,"data":{"result":0,"operType":0}}
      break
    }
    case 21309: // 禁言应答
    {
      // {"msgCode":21309,"data":{"result":0,"operType":1}}
      // {"msgCode":21309,"data":{"result":0,"operType":0}}
      break
    }

    case 30004: // 常规组呼来呼通知
    {
      normalGroupComingCallNtfy(msg.data)
      break
    }
    case 30005: // 呼叫状态通知
    {
      callStatusNotify(msg.data)
      break
    }
    case 30006: // 呼叫话权通知
    {
      callFloorNotify(msg.data)
      break
    }

    case 30101: // 消息登录状态通知
    {
      msgLoginStatusNotify(msg.data)
      break
    }
    case 30102: // 文本消息通知
    {
      comingTextMsgNotify(msg.data)
      break
    }
    case 30103: // 文件消息通知
    {
      comingFileMsgNotify(msg.data)
      break
    }
  }
}

function startSendDataToServer() {
  webSocket.send('hello')
  return false
}

function disconnectWithServer() {
  webSocket.close()
}

// {"msgCode": 10001, "data": {"userId": "20000000009", "password": "111111", "enterpriseId": "caltta4567", "serverIp": "10.194.23.22", "localIp": "10.194.26.30"}}
function loginReq() {
  // let loginInfo = { 'msgCode': 10001, 'data': { 'userId': '20000000001', 'password': 'Root.123', 'enterpriseId': 'caltta4567', 'serverIp': '111.51.8.44', 'localIp': '192.168.1.5' } }
  let loginInfo = {"msgCode": 10001, "data": {"userId": "20000000034", "password": "Root.123", "enterpriseId": "caltta4567", "serverIp": "119.3.16.101", "localIp": "10.194.26.70"}};
  let loginStr = JSON.stringify(loginInfo)
  console.log(loginStr)
  webSocket.send(loginStr)
}

// {"msgCode":20001,"data":{"result":0,"dispatcherId":"20000000009","dispatcherName":"20000000008","radomString":"AaBbCcDd","msgServerIp":"10.194.23.22","msgServerHttpPort":5224}}
function loginRsp(rspData) {
  if (rspData.result) {
    console.log('login fail, result: ' + rspData.result)
    return
  }

  loginRspData = rspData
  console.log('login succ, randomString: ' + loginRspData.randomString)

  // 调度群组，群组来呼才能收到
  dispatchGroup('30000000329', 1)
}

// {"msgCode": 10002, "radomString":"AaBbCcDd"}
function logoutReq() {
  let logoutInfo = { 'msgCode': 10002, 'randomString': loginRspData.randomString }
  let logoutStr = JSON.stringify(logoutInfo)
  webSocket.send(logoutStr)
}

// {"msgCode": 20002, "data": {"result": 0}}
function logoutRsp(rspData) {
  console.log('logout success, result ' + rspData.result)
}

// 全双工单呼请求 {"msgCode": 11101, "randomString": "xxxxxxxx", "data": {"calledNumber": "10000001497"}}
// 半双工单呼请求 {"msgCode": 11102, "randomString": "xxxxxxxx", "data": {"calledNumber": "10000001497"}}
// 常规组呼请求 {"msgCode": 11103, "randomString": "xxxxxxxx", "data": {"calledNumber": "30000000329"}}
// 动态组呼请求 {"msgCode": 11104, "randomString": "xxxxxxxx", "data": {"calledNumber": "30000000333"}}
function callReq(callType, callNumber) {
  let callReq
  switch (callType) {
    case 11101:
    {
      callReq = { 'msgCode': 11101, 'randomString': loginRspData.randomString, 'data': { 'calledNumber': callNumber } }
      break
    }
    case 11102:
    {
      callReq = { 'msgCode': 11102, 'randomString': loginRspData.randomString, 'data': { 'calledNumber': callNumber } }
      break
    }
    case 11103:
    {
      callReq = { 'msgCode': 11103, 'randomString': loginRspData.randomString, 'data': { 'calledNumber': callNumber } }
      break
    }
    case 11104:
    {
      callReq = { 'msgCode': 11104, 'randomString': loginRspData.randomString, 'data': { 'calledNumber': callNumber } }
      break
    }
    case 11105:
    {
      break
    }
    default:
    {
      alert('unknown call request type ' + callType)
      return
    }
  }

  let callReqStr = JSON.stringify(callReq)
  webSocket.send(callReqStr)
}

// 全双工单呼应答 {"msgCode":21101,"data":{"result":0,"channelId":0,"calledNumber":"10000001497"}}
// 半双工单呼应答 {"msgCode":21102,"data":{"result":0,"channelId":0,"calledNumber":"10000001497"}}
// 常规组呼应答 {"msgCode":21103,"data":{"result":0,"channelId":0,"calledNumber":"30000000329"}}
// 动态组呼应答 {"msgCode":21104,"data":{"result":0,"channelId":1,"calledNumber":"30000000333"}}
function callRsp(msg) {
  switch (msg.msgCode) {
    case 21101:
    {
      if (msg.data.result) {
        alert('full duplex single call fail, result ' + msg.data.result)
      } else {
        console.log('connecting ...')
        fullDuplexSingleCall = { 'channelId': msg.data.channelId, 'calledNumber': msg.data.calledNumber, 'callStatus': 32 }
      }
      break
    }
    case 21102:
    {
      if (msg.data.result) {
        alert('half duplex single call fail, result ' + msg.data.result)
      } else {
        console.log('connecting ...')
        halfDuplexSingleCall = { 'channelId': msg.data.channelId, 'calledNumber': msg.data.calledNumber, 'callStatus': 32 }
      }
      break
    }
    case 21103:
    {
      if (msg.data.result) {
        alert('normal group call fail, result ' + msg.data.result)
      } else {
        console.log('connecting ...')
        normalGroupCall = { 'channelId': msg.data.channelId, 'calledNumber': msg.data.calledNumber, 'callStatus': 32 }
      }
      break
    }
    case 21104:
    {
      if (msg.data.result) {
        alert('dynamic group call fail, result ' + msg.data.result)
      } else {
        console.log('connecting ...')
        dynamicGroupCall = { 'channelId': msg.data.channelId, 'calledNumber': msg.data.calledNumber, 'callStatus': 32 }
      }
      break
    }
    case 21105:
    {
      if (msg.data.result) {
        alert('temporary group call fail, result ' + msg.data.result)
      } else {
        console.log('connecting ...')
        temporaryGroupCall = { 'channelId': msg.data.channelId, 'calledNumber': msg.data.calledNumber, 'callStatus': 32 }
      }
      break
    }
    default:
    {
      alert('unknown call response type ' + msg.msgCode)
    }
  }
}

// 全双工单呼来呼请求 {"msgCode": 12101, "data": {"channelId": 1, "callStatus": 31, "calleeNumber": "20000001936"}}
// 半双工单呼来呼请求 {"msgCode": 12102, "data": {"channelId": 1, "callStatus": 31, "calleeNumber": "20000001936"}}
function singleComingCallReq(callName) {
  console.log('coming call ...')
}

// 全双工单呼来呼应答 {"msgCode": 22101, "randomString": "xxxxxxxx", "data": {"result": 200, "channelId": 1}}  //200 接听， 603 拒绝
// 半双工单呼来呼应答 {"msgCode": 22102, "randomString": "xxxxxxxx", "data": {"result": 200, "channelId": 1}} //200 接听， 603 拒绝
function singleComingCallRsp(msgcode, result) {
  let rsp
  let callName
  // eslint-disable-next-line eqeqeq
  if (msgcode == 22101) {
    rsp = { 'msgCode': msgcode, 'randomString': loginRspData.randomString, 'data': { 'result': result, 'channelId': fullDuplexSingleComingCall.channelId } }
    callName = 'fullDuplexSingleComingCall'
  } else {
    rsp = { 'msgCode': msgcode, 'randomString': loginRspData.randomString, 'data': { 'result': result, 'channelId': halfDuplexSingleComingCall.channelId } }
    callName = 'halfDuplexSingleComingCall'
  }
  let rspStr = JSON.stringify(rsp)
  webSocket.send(rspStr)
}

// 结束呼叫请求: {"msgCode": 11301, "randomString": "xxxxxxxx", "data": {"channelId": 0, "cause": 2}}
function hangup(channelId) {
  let terminateCallReq = { 'msgCode': 11301, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId, 'cause': 2 } }
  let terminateCallStr = JSON.stringify(terminateCallReq)
  webSocket.send(terminateCallStr)
}

// 调度群组请求 {"msgCode": 11302, "randomString": "xxxxxxxx", "data": {"operType": 0, "groupNumber": "30000000329"}} //0 取消调度， 1 调度
function dispatchGroup(groupNumber, operType) {
  let dispatchGroupReq = { 'msgCode': 11302, 'randomString': loginRspData.randomString, 'data': { 'operType': operType, 'groupNumber': groupNumber } }
  let dispatchGroupStr = JSON.stringify(dispatchGroupReq)
  webSocket.send(dispatchGroupStr)
}

// 设置当前呼叫请求 {"msgCode": 11303, "randomString": "xxxxxxxx", "data": {"channelId": 0}}
function setCurrentCall(channelId) {
  let setCurrentCallReq = { 'msgCode': 11303, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId } }
  let setCurrentCallStr = JSON.stringify(setCurrentCallReq)
  webSocket.send(setCurrentCallStr)
}

// 设置播放声音请求 {"msgCode": 11304, "randomString": "xxxxxxxx", "data": {"channelId": 0, "operType": 1}}
function setPlaySound(channelId, operType) {
  let setPlaySoundReq = { 'msgCode': 11304, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId, 'operType': operType } }
  let setPlaySoundStr = JSON.stringify(setPlaySoundReq)
  webSocket.send(setPlaySoundStr)
}

// 操作话权请求 {"msgCode": 11305, "randomString": "xxxxxxxx", "data": {"channelId": 0, "operType": 1}}
function operateFloor(input, channelId) {
  let operateFloorReq
  if (input.value == '释放话权') {
    operateFloorReq = { 'msgCode': 11305, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId, 'operType': 0 } }
    input.value = '申请话权'
  } else { // 申请话权
    operateFloorReq = { 'msgCode': 11305, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId, 'operType': 1 } }
    input.value = '释放话权'
  }
  let operateFloorStr = JSON.stringify(operateFloorReq)
  webSocket.send(operateFloorStr)
}

// 设置本地录音请求 {"msgCode": 11306, "randomString": "xxxxxxxx", "data": {"channelId": 0, "operType": 1, "tmpGroupNumber": 123}}
function setLocalAudioRecord(input, channelId, tmpGroupNumber) {
  let setLocalAudioRecordReq
  // eslint-disable-next-line eqeqeq
  if (input.value == '开始录音') {
    setLocalAudioRecordReq = { 'msgCode': 11306, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId, 'operType': 1, 'tmpGroupNumber': tmpGroupNumber } }
    input.value = '结束录音'
  } else { // 结束录音
    setLocalAudioRecordReq = { 'msgCode': 11306, 'randomString': loginRspData.randomString, 'data': { 'channelId': channelId, 'operType': 0, 'tmpGroupNumber': tmpGroupNumber } }
    input.value = '开始录音'
  }
  let setLocalAudioRecordStr = JSON.stringify(setLocalAudioRecordReq)
  webSocket.send(setLocalAudioRecordStr)
}

// 查询本地录音请求 {"msgCode": 11307, "randomString": "xxxxxxxx", "data": {"startTime": 1594456229960, "stopTime": 1594456339960, "number": "10000001497", "pageInfo": {"pageIndex": 1, "numPerPage": 50, "totalPage": 0, "totalRecord": 0}}}
function qryLocalAudioRecord() {
  let d = new Date()
  let endTime = parseInt(d.getTime() / 1000) // 出去毫秒数
  let date = d.getDate()
  d.setDate(date - 1)
  let startTime = parseInt(d.getTime() / 1000)
  let qryLocalAudioRecordReq = { 'msgCode': 11307, 'randomString': loginRspData.randomString, 'data': { 'startTime': startTime, 'stopTime': endTime, 'number': '', 'pageInfo': { 'pageIndex': 1, 'numPerPage': 50, 'totalPage': 0, 'totalRecord': 0 } } }
  let qryLocalAudioRecordStr = JSON.stringify(qryLocalAudioRecordReq)
  webSocket.send(qryLocalAudioRecordStr)
}

/*
查询本地录音应答
    {
  "msgCode":21307,
  "data":
  {
    "result":0,
    "pageInfo":{"pageIndex":1,"numPerPage":50,"totalPage":1,"totalRecord":4},
    "recordList":
    [
      {"recordIndex":6,"calleeNumber":"19630002295","calledNumber":"30000000329","callType":3,"startTime":"1597458627","recordLength":4,"fileFullName":"E:\\SVN\\DacWebsocketServer\\DacWebsocketServer\\RecordFiles\\2020815103027.mp3"},
      {"recordIndex":5,"calleeNumber":"19630002295","calledNumber":"30000000329","callType":3,"startTime":"1597458610","recordLength":7,"fileFullName":"E:\\SVN\\DacWebsocketServer\\DacWebsocketServer\\RecordFiles\\2020815103010.mp3"},
      {"recordIndex":4,"calleeNumber":"20000000021","calledNumber":"10000001497","callType":2,"startTime":"1597454304","recordLength":10,"fileFullName":"E:\\SVN\\DacWebsocketServer\\DacWebsocketServer\\RecordFiles\\202081591824.mp3"},
      {"recordIndex":3,"calleeNumber":"20000000021","calledNumber":"30000000329","callType":3,"startTime":"1597453958","recordLength":12,"fileFullName":"E:\\SVN\\DacWebsocketServer\\DacWebsocketServer\\RecordFiles\\202081591238.mp3"}
    ]
  }
}
*/
function procLocalAudioRecordRsp(msgData) {
  // eslint-disable-next-line eqeqeq
  if (msgData.result != 0) {
    console.log('query fail, result ' + msgData.result)

    // eslint-disable-next-line eqeqeq
  } else if (msgData.recordList.length == 0) {
    console.log('no local audio record data')
  }

  // let recordTable = '<table>'
  // recordTable += '<tr>'
  // recordTable += '<th>索引</th>'
  // recordTable += '<th>主叫号码</th>'
  // recordTable += '<th>被叫号码</th>'
  // recordTable += '<th>呼叫类型</th>'
  // recordTable += '<th>开始时间</th>'
  // recordTable += '<th>录音时长</th>'
  // recordTable += '<th>文件连接</th>'
  // recordTable += '</tr>'
  // for (let index = 0; index < msgData.recordList.length; index++) {
  //   recordTable += '<tr>'
  //   recordTable += '<td>' + msgData.recordList[index].recordIndex + '</td>'
  //   recordTable += '<td>' + msgData.recordList[index].calleeNumber + '</td>'
  //   recordTable += '<td>' + msgData.recordList[index].calledNumber + '</td>'
  //   recordTable += '<td>' + msgData.recordList[index].callType + '</td>'
  //   recordTable += '<td>' + msgData.recordList[index].startTime + '</td>'
  //   recordTable += '<td>' + msgData.recordList[index].recordLength + '</td>'
  //   recordTable += '<td>' + msgData.recordList[index].fileFullName + '</td>'
  //   recordTable += '</tr>'
  // }
  // recordTable += '</table>'
  // $('#localAudioRecordResult').html(recordTable)
}

// 呼叫静音请求 {"msgCode": 11308, "randomString": "xxxxxxxx", "data": {"operType": 1}}
function silence(input) {
  let slienceReq
  // eslint-disable-next-line eqeqeq
  if (input.value == '开始静音') {
    slienceReq = { 'msgCode': 11308, 'randomString': loginRspData.randomString, 'data': { 'operType': 1 } }
    input.value = '取消静音'
  } else { // 取消静音
    slienceReq = { 'msgCode': 11308, 'randomString': loginRspData.randomString, 'data': { 'operType': 0 } }
    input.value = '开始静音'
  }

  let slienceStr = JSON.stringify(slienceReq)
  webSocket.send(slienceStr)
}

// 呼叫禁言请求 {"msgCode": 11309, "randomString": "xxxxxxxx", "data": {"operType": 1}}
function mute(input) {
  let muteReq
  // eslint-disable-next-line eqeqeq
  if (input.value == '开始禁言') {
    muteReq = { 'msgCode': 11309, 'randomString': loginRspData.randomString, 'data': { 'operType': 1 } }
    input.value = '取消禁言'
  } else { // 取消禁言
    muteReq = { 'msgCode': 11309, 'randomString': loginRspData.randomString, 'data': { 'operType': 0 } }
    input.value = '开始禁言'
  }
  let muteStr = JSON.stringify(muteReq)
  webSocket.send(muteStr)
}

// 呼叫失败提示
function alertCallFail(cause) {
  switch (cause) {
    case 7:
    {
      alert('Undefined reason!')
      break
    }
    case 9:
    {
      alert('Call timeout, user do not answer!')
      break
    }
    case 30:
    {
      alert("The called party doesn't exist!")
      break
    }
    case 39:
    {
      alert('The user is busy!')
      break
    }
    case 41:
    {
      alert('The called party refused to answer!')
      break
    }
  }
}
// 发送文本消息请求 {"msgCode": 11501, "randomString": "xxxxxxxx", "data": {"msgId": "1594456229670", "target": {"type": 1, "number": "20000001936", "name": "目标名称", "systemId": "2"}, "content": "文本消息内容"}}
function sendTextMsg() {
  let d = new Date()
  let msgId = d.getTime().toString()
  let content = document.getElementById('testMsgInput').value
  let sendTextMsgReq = { 'msgCode': 11501, 'randomString': loginRspData.randomString, 'data': { 'msgId': msgId, 'target': { 'type': 1, 'number': '10000001497', 'name': '19620000010', 'systemId': '2' }, 'content': content } }
  // let sendTextMsgReq = {"msgCode": 11501, "randomString": loginRspData.randomString, "data": {"msgId": msgId, "target": {"type": 2, "number": "30000000329", "name": "liwq", "systemId": "2"}, "content": content}};
  let sendTextMsgStr = JSON.stringify(sendTextMsgReq)
  webSocket.send(sendTextMsgStr)
}

// 打开文件选择对话框
function openDialog() {
  document.getElementById('fileMsg_selectWnd').click()
}

// 选择文件变化
function selectFileChanged(fileFullPath) {
  // let url = null;
  // let fileObj = document.getElementById("fileMsg_selectWnd").files[0];
  // if (window.createObjcectURL != undefined) {
  //    url = window.createOjcectURL(fileObj);
  // } else if (window.URL != undefined) {
  //    url = window.URL.createObjectURL(fileObj);
  // } else if (window.webkitURL != undefined) {
  //    url = window.webkitURL.createObjectURL(fileObj);
  // }

  // eslint-disable-next-line eqeqeq
  if (fileFullPath.length != 0) {
    $('#fileMsg_selectRst').text(fileFullPath)
    $('#fileMsg_send').show()
  }
}

// 时间格式yyyy-MM-dd hh:mm:ss
function getFormatDateTime() {
  let d = new Date()
  let formatDateTime = ''
  formatDateTime += d.getFullYear()
  formatDateTime += '-'
  if (d.getMonth() <= 9) {
    formatDateTime += '0'
  }
  formatDateTime += (d.getMonth() + 1)
  formatDateTime += '-'
  if (d.getDate() < 10) {
    formatDateTime += '0'
  }
  formatDateTime += d.getDate()

  formatDateTime += ' '

  if (d.getHours() < 10) {
    formatDateTime += '0'
  }
  formatDateTime += d.getHours()
  formatDateTime += ':'
  if (d.getMinutes() < 10) {
    formatDateTime += '0'
  }
  formatDateTime += d.getMinutes()
  formatDateTime += ':'
  if (d.getSeconds() < 10) {
    formatDateTime += '0'
  }
  formatDateTime += d.getSeconds()

  return formatDateTime
}

// 发送文件消息请求{"msgCode": 11502, "randomString": "xxxxxxxx", "data": {"msgId": "1594456229960", "target": {"type": 1, "number": "20000001936", "name": "目标名称", "systemId": "2"}, "fileName": "dispatch_dial_call_h.png", "content": "二进制内容"}}
function sendFileMsg() {
  let d = new Date()
  let msgId = d.getTime().toString()
  let fileName = $('#fileMsg_selectRst').text()
  let index = fileName.lastIndexOf('\\')
  fileName = fileName.substr(index + 1)
  // let sendFileMsgReq = { 'msgCode': 11502, 'randomString': loginRspData.randomString, 'data': { 'msgId': msgId, 'target': { 'type': 1, 'number': '20000001497', 'name': '19620000010', 'systemId': '2' }, 'fileName': 'dispatch_dial_call_h.png', 'content': '二进制内容' } }

  let uploadUrl = 'http://' + loginRspData.msgServerIp + ':' + loginRspData.msgServerHttpPort + '/attachment'
  let fileMsgData = new FormData()
  fileMsgData.append('userid', loginRspData.dispatcherId)
  fileMsgData.append('msgid', msgId)
  let dateStr = getFormatDateTime()
  fileMsgData.append('date', dateStr)

  $.ajax({
    url: uploadUrl,
    async: true,
    type: 'post', //
    processData: false,
    contentType: 'multipart/form-data',
    // mimeType: 'multipart/form-data',
    data: fileMsgData,
    success: function(result, status, xhr) {
      alert('upload ok')
    }
  })
}

// 常规组呼来呼通知 {"msgCode":30004,"data":{"channelId":0,"callStatus":32,"groupNumber":"30000000329"}}
function normalGroupComingCallNtfy(ntfyData) {
  normalGroupComingCall = ntfyData
  $('#normalGroupComingCall_status').text('connected')
  $('#normalGroupComingCall_hangup').show()
  $('#normalGroupComingCall_setCurCall').show()
  $('#normalGroupComingCall_floor').show()
  $('#normalGroupComingCall_record').show()
}

// 处理呼叫状态
function procCallStatus(ntfyData, callObject, callName) {
  // eslint-disable-next-line eqeqeq
  if (ntfyData.callStatus == 31) {
    alertCallFail(ntfyData.cause)
    // eslint-disable-next-line eqeqeq
    if (callName != 'fullDuplexSingleComingCall' && callName != 'halfDuplexSingleComingCall' && callName != 'normalGroupComingCall') {
      // $('#' + callName + '_call').show()
    }
    // $('#' + callName + '_status').hide()
    // $('#' + callName + '_status').text('')
    // $('#' + callName + '_hangup').hide()
    // $('#' + callName + '_setCurCall').hide()
    // eslint-disable-next-line eqeqeq
    if (callName != 'fullDuplexSingleCall' && callName != 'fullDuplexSingleComingCall') {
      // $('#' + callName + '_floor').hide()
    }
    // $('#' + callName + '_record').hide()
    // eslint-disable-next-line eqeqeq
  } else if (ntfyData.callStatus == 34 && callObject.callStatus != 34 && callObject.callStatus != 36) {
    callObject.callStatus = ntfyData.callStatus
    callObject.inActiveTime = ntfyData.inActiveTime
    setCurrentCall(callObject.channelId)
    setPlaySound(callObject.channelId, 1)
    // eslint-disable-next-line eqeqeq
    if (callName != 'fullDuplexSingleComingCall' && callName != 'halfDuplexSingleComingCall' && callName != 'normalGroupComingCall') {
      // $('#' + callName + '_call').hide()
    }
    // $('#' + callName + '_hangup').show()
    // $('#' + callName + '_setCurCall').show()
    // eslint-disable-next-line eqeqeq
    if (callName != 'fullDuplexSingleCall' && callName != 'fullDuplexSingleComingCall') {
      // $('#' + callName + '_floor').show()
    }
    $('#' + callName + '_record').show()
    // eslint-disable-next-line eqeqeq
  } else if (ntfyData.callStatus == 36) {
    console.log('call speaking ...')
  }
}

// 呼叫状态通知
// {"msgCode":30005,"data":{"channelId":0,"callStatus":33,"cause":0,"inActiveTime":30}}
// {"msgCode":30005,"data":{"channelId":0,"callStatus":34,"cause":0,"inActiveTime":30}}
// {"msgCode":30005,"data":{"channelId":0,"callStatus":36,"cause":0,"inActiveTime":30}}
// {"msgCode":30005,"data":{"channelId":0,"callStatus":31,"cause":0,"inActiveTime":30}}
function callStatusNotify(ntfyData) {
  // 全双工单呼
  // eslint-disable-next-line eqeqeq
  if (fullDuplexSingleCall && fullDuplexSingleCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, fullDuplexSingleCall, 'fullDuplexSingleCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      fullDuplexSingleCall = null
    }
    // eslint-disable-next-line brace-style
  }
  // 半双工单呼
  // eslint-disable-next-line eqeqeq
  else if (halfDuplexSingleCall && halfDuplexSingleCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, halfDuplexSingleCall, 'halfDuplexSingleCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      halfDuplexSingleCall = null
    }
    // eslint-disable-next-line brace-style
  }
  // 普通组呼
  // eslint-disable-next-line eqeqeq
  else if (normalGroupCall && normalGroupCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, normalGroupCall, 'normalGroupCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      // normalGroupCallCall = null
    }
    // eslint-disable-next-line brace-style
  }
  // 动态组呼
  // eslint-disable-next-line eqeqeq
  else if (dynamicGroupCall && dynamicGroupCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, dynamicGroupCall, 'dynamicGroupCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      dynamicGroupCall = null
    }
    // eslint-disable-next-line brace-style
  }
  // 临时组呼
  // eslint-disable-next-line eqeqeq
  else if (temporaryGroupCall && temporaryGroupCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, temporaryGroupCall, 'temporaryGroupCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      temporaryGroupCall = null
    }
    // eslint-disable-next-line brace-style
  }
  // 全双工单呼来呼
  // eslint-disable-next-line eqeqeq
  else if (fullDuplexSingleComingCall && fullDuplexSingleComingCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, fullDuplexSingleComingCall, 'fullDuplexSingleComingCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      fullDuplexSingleComingCall = null
    }
    // eslint-disable-next-line brace-style
  }
  // 半双工单呼来呼
  // eslint-disable-next-line eqeqeq
  else if (halfDuplexSingleComingCall && halfDuplexSingleComingCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, halfDuplexSingleComingCall, 'halfDuplexSingleComingCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      halfDuplexSingleComingCall = null
    }
  }
  // 常规组呼来呼
  // eslint-disable-next-line eqeqeq
  else if (normalGroupComingCall && normalGroupComingCall.channelId == ntfyData.channelId) {
    procCallStatus(ntfyData, normalGroupComingCall, 'normalGroupComingCall')
    // eslint-disable-next-line eqeqeq
    if (ntfyData.callStatus == 31) {
      normalGroupComingCall = null
    }
  }
}

function procFloorNotify(ntfyData, callName) {
  // eslint-disable-next-line eqeqeq
  if (ntfyData.floorType == 0) {
    // $('#' + callName + '_status').text('call idle')
  } else {
    // $('#' + callName + '_status').text(ntfyData.speakNumber + ' speaking')
  }
}

// 呼叫话权通知 {"msgCode":30006,"data":{"channelId":0,"floorType":1,"subSystemId":1,"speakNumber":"19620000010"}}
function callFloorNotify(ntfyData) {
  // eslint-disable-next-line eqeqeq
  if (fullDuplexSingleCall && fullDuplexSingleCall.channelId == ntfyData.channelId) {
    // 全双工没有话权通知
    // eslint-disable-next-line eqeqeq
  } else if (halfDuplexSingleCall && halfDuplexSingleCall.channelId == ntfyData.channelId) {
    procFloorNotify(ntfyData, 'halfDuplexSingleCall')
    // eslint-disable-next-line eqeqeq
  } else if (normalGroupCall && normalGroupCall.channelId == ntfyData.channelId) {
    procFloorNotify(ntfyData, 'normalGroupCall')
    // eslint-disable-next-line eqeqeq
  } else if (dynamicGroupCall && dynamicGroupCall.channelId == ntfyData.channelId) {
    procFloorNotify(ntfyData, 'dynamicGroupCall')
    // eslint-disable-next-line eqeqeq
  } else if (temporaryGroupCall && temporaryGroupCall.channelId == ntfyData.channelId) {
    procFloorNotify(ntfyData, 'temporaryGroupCall')
    // eslint-disable-next-line eqeqeq
  } else if (fullDuplexSingleComingCall && fullDuplexSingleComingCall.channelId == ntfyData.channelId) {
    // 全双工没有话权通知
    // eslint-disable-next-line eqeqeq
  } else if (halfDuplexSingleComingCall && halfDuplexSingleComingCall.channelId == ntfyData.channelId) {
    procFloorNotify(ntfyData, 'halfDuplexSingleComingCall')
    // eslint-disable-next-line eqeqeq
  } else if (normalGroupComingCall && normalGroupComingCall.channelId == ntfyData.channelId) {
    procFloorNotify(ntfyData, 'normalGroupComingCall')
  }
}

// 消息登录状态通知 {"msgCode": 30101, "data": {"status": 0}} //0 未登录， 1 登录中， 2 已登录
function msgLoginStatusNotify(ntfyData) {
  // eslint-disable-next-line eqeqeq
  if (ntfyData.status == 2) {
    $('#testMsgDiv').show()
  } else {
    $('#testMsgDiv').hide()
  }
}

// 文本消息通知
// {"msgCode":30102,"data":{"source":{"type":1,"number":"10000001497","uid":"10000001497"},"content":"123"}}
// {"msgCode":30102,"data":{"source":{"type":2,"number":"30000000329","uid":"30000000329"},"content":"456"}}
function comingTextMsgNotify(ntfyData) {
  console.log('To ' + ntfyData.source.uid + ': ' + ntfyData.content)
}

// 文件消息通知
// {"msgCode":30103,"data":{"source":{"type":1,"number":"10000001497","uid":"10000001497"},"fileUrl":"http://10.194.23.22:5225/attachment?userid=10000001497&msgid=929$19620000010$100000000000178&file=未开启大内存.jpg","fileId":"","fileName":"未开启大内存.jpg","content":""}}
// {"msgCode":30103,"data":{"source":{"type":2,"number":"30000000329","uid":"30000000329"},"fileUrl":"http://10.194.23.22:5225/attachment?userid=10000001394&msgid=958$19630002295$100000000000178&file=IMG_19800106_194723.jpg","fileId":"","fileName":"IMG_19800106_194723.jpg","content":""}}
function comingFileMsgNotify(ntfyData) {
  console.log('To ' + ntfyData.source.uid + ': <a href=' + ntfyData.fileUrl + '>' + ntfyData.fileName)
}

let mobileIndividualVoice = {
  loginReq: function () {
    // getUserIP(function(localIp) {
    //   // let loginInfo = { 'msgCode': 10001, 'data': { 'userId': '20000000001', 'password': 'Root.123', 'enterpriseId': 'caltta4567', 'serverIp': '111.51.8.44', 'localIp': localIp } }
    //   let loginInfo = {"msgCode": 10001, "data": {"userId": "zj", "password": "111111", "enterpriseId": "caltta4567", "serverIp": "10.194.23.22", "localIp": "10.194.26.70"}};
    //   let loginStr = JSON.stringify(loginInfo)
    //   webSocket.send(loginStr)
    // })
    loginReq()
  },
  callReq: function (callType, callNumber) {
    callReq(callType, callNumber)
  },
  hangup: function (channelId) {
    channelId = channelId || fullDuplexSingleCall.channelId
    console.log(channelId)
    hangup(channelId)
  }
}

export default mobileIndividualVoice
