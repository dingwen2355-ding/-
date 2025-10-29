import request from '@/utils/request'
var Socket = function () {
  this.type = null
  this.deviceIds = null
  this.deviceKinds = null
  this.precintId = null
  this.callbacks = {}

  var ip = sysConfig.websocket.ip
  var port = sysConfig.websocket.port
  var sessionId = null
  var hasSubscribe = false

  var that = this
  var websocket = new WebSocket('ws://' + ip + ':' + port + '/websocket')
  websocket.onopen = function (event) {
    console.log('Info: WebSocket connection opened.')
  }
  websocket.onclose = function (event) {
    console.log('Info: WebSocket closed.')
  }
  websocket.onmessage = function (event) {
    var jsono = JSON.parse(event.data)
    if (!jsono) {
      return
    }
    if (jsono.type === 'session') {
      sessionId = jsono.sessionid
      // 获取了sessionId执行过subscribe函数给subscribe_types，device_ids赋值后在订阅
      if (hasSubscribe) {
        request({
          url: '/apis/subscribe/device?' + 'sessionId=' + sessionId + '&isSubscribe=1' + '&subscribeTypes=' + that.type + '&deviceKinds=' + that.deviceKinds + '&deviceIds=' + that.deviceIds + '&precinctId=' + that.precintId,
          method: 'get'
        }).then(res => {
          hasSubscribe = false
        })
      }
      return
    }
    if (jsono.type in that.callbacks) {
      var data = JSON.parse(jsono.data)
      that.callbacks[jsono.type](data)
    }
  }

  Socket.prototype.subscribe = function (type, deviceIds, precintId, deviceKinds) {
    this.type = type
    this.deviceIds = deviceIds
    this.deviceKinds = deviceKinds == null ? '' : deviceKinds
    this.precintId = precintId == null ? '' : precintId
    hasSubscribe = true
    if (sessionId) {
      request({
        url: '/apis/subscribe/device?' + 'sessionId=' + sessionId + '&isSubscribe=1' + '&subscribeTypes=' + this.type + '&deviceKinds=' + this.deviceKinds + '&deviceIds=' + this.deviceIds + '&precinctId=' + this.precintId,
        method: 'get'
      }).then(res => {
        hasSubscribe = false
      })
    }
  }
  Socket.prototype.addCallback = function (type, func) {
    this.callbacks[type] = func
  }
  Socket.prototype.delCallback = function (type) {
    delete this.callbacks[type]
  }
}

export default new Socket()
