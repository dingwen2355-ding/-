import request from '@/utils/request'
var ip = sysConfig.websocket.ip
var port = sysConfig.websocket.port
var websocket = null
var sessionId = null
let Socket = {
  types: [], // 未订阅成功的
  chatIds: [],
  callbacks: {},

  subscribe: function (type, chatId) {
    if (sessionId) {
      request({
        url: '/websocketApis/subscribe?' + 'sessionId=' + sessionId + '&isSubscribe=1' + '&subscribeTypes=' + type + '&chatId=' + chatId,
        method: 'get'
      })
    } else {
      this.types.push(type)
      this.chatIds.push(chatId)
    }
  },
  addCallback: function (type, func) {
    this.callbacks[type] = func
  },
  delCallback: function (type) {
    delete this.callbacks[type]
  }
}

if (!websocket) websocket = new WebSocket('ws://' + ip + ':' + port + '/websocket')
websocket.onopen = function (event) {
  console.log('Info: WebSocket connection opened.')
}
websocket.onclose = function (event) {
  console.log('Info: WebSocket closed.')
}
websocket.onmessage = function (event) {
  var jsono = JSON.parse(event.data)
  console.log(jsono)
  if (!jsono) {
    return
  }
  if (jsono.type === 'session') {
    sessionId = jsono.sessionid
    for (let i = 0; i < Socket.types.length; i++) {
      let type = Socket.types[i]
      let chartId = Socket.chatIds[i]
      request({
        url: '/websocketApis/subscribe?' + 'sessionId=' + sessionId + '&isSubscribe=1' + '&subscribeTypes=' + type + '&chatId=' + chartId,
        method: 'get'
      })
    }
    Socket.types = []
    Socket.chatIds = []
    return
  }
  if (jsono.type in Socket.callbacks) {
     let data = JSON.parse(jsono.data)
    Socket.callbacks[jsono.type](data)
  }
}



export default Socket
