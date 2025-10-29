var uzmoduleNuSDK = null
var isInitNuSDK = false
var isInitNuSDKServer = false
var isLoginSDK = false
let nusdk = {}
nusdk.initNuSDK = function () {
  if (!isInitNuSDK) {
    uzmoduleNuSDK = window.api.require('nusdk')
    uzmoduleNuSDK.initSDK()
    isInitNuSDK = true
    console.log('finish initSDK!')
  }
}

nusdk.initNuSDKServer = function () {
  if (!isInitNuSDKServer) {
    var param = { ip: sysConfig.gkits.ip, port: sysConfig.gkits.port }
    uzmoduleNuSDK.initServer(param)
    isInitNuSDKServer = true
    console.log('finish initNuSDKServer!')
  }
}

nusdk.loginSDK = function loginSDK() {
  if (!isLoginSDK) {
    var param = { user: localStorage.extendUsername, password: localStorage.extendPassword }
    uzmoduleNuSDK.login(param, function (ret, err) {
      console.log('finish login return  ' + ret)
      alert(JSON.stringify(ret))
    })
    isLoginSDK = true
  }
}

nusdk.logoutSDK = function logoutSDK() {
  if (isLoginSDK) {
    uzmoduleNuSDK.logout()
    isLoginSDK = false
    console.log('finish logout')
  }
}

// 视频会商
nusdk.startVideoConference = function startVideoConference() {
  if (!isLoginSDK) {
    nusdk.loginSDK()
  }
  uzmoduleNuSDK.startVideoConference()
}

// 直播
nusdk.startLiveActivity = function startLiveActivity() {
  if (!isLoginSDK) {
    nusdk.loginSDK()
  }
  uzmoduleNuSDK.startLiveActivity()
}

// 监控
nusdk.startMonitorActivity = function startMonitorActivity() {
  if (!isLoginSDK) {
    nusdk.loginSDK()
  }
  uzmoduleNuSDK.startMonitorActivity()
}

// 拍传
nusdk.startTopicListActivity = function startTopicListActivity() {
  if (!isLoginSDK) {
    nusdk.loginSDK()
  }
  uzmoduleNuSDK.startTopicListActivity()
}

// 消息
nusdk.startMsgActivity = function startMsgActivity() {
  if (!isLoginSDK) {
    nusdk.loginSDK()
  }
  uzmoduleNuSDK.startMsgActivity()
}
export default nusdk
