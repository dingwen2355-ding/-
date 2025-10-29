// let remoteId = '5383486' // 目标者Id
let wsServer = 'wss://shdb-media.intcity.com.cn:8088' // 媒体服务器地址(带端口) (拉取视频)
let mtServer = 'ws://:shdb-media.intcity.com.cn:8188' // 媒体服务器地址(带端口) (视频通话)
let dispatchId = '5382864' // 来源者Id
let mqttUserName = 'i61xy3n0'
let mqttUserPassword = 'i61xy3n0'
let mqttServer = 'wss://shdb-push.intcity.com.cn:8084/mqtt'
let mqttClicent = null
let pullCallObjs = {}
let pushVideoObj = null

let mobileIndividualVideo = {
  isDialog: false,
  suffixStr: '',
  // 视频通话
  publishLocalAudio: function(remoteId, suffixStr) {
    this.suffixStr = suffixStr || ''
    if (pullCallObjs[remoteId + this.suffixStr] || pushVideoObj || pullCallObjs[remoteId]) {
      this.closeRVideo(remoteId)
      setTimeout(() => {
        // eslint-disable-next-line no-undef
        pushVideoObj = new PublishVideo()
        pushVideoObj.init({
          success: () => {
            pushVideoObj.openLocalVideo({
              roomId: 1234,
              isAudio: true,
              isVideo: true,
              success: (userId, mediaId) => {
                // 成功后发送mqtt，通知终端进行全双工会话
                let cmd = {
                  cmd: 'call',
                  to: remoteId, // 目标者Id
                  from: dispatchId, // 来源者Id
                  name: '测试账号',
                  sessionId: '', // 会话Id由会话发起者创建
                  timestamp: new Date().getTime(), // 时间戳(单位: 秒)
                  data: {
                    calltype: 'dispatch',
                    server: mtServer, // 媒体服务器地址(带端口)
                    roomId: 1234, // 媒体房间Id
                    userId, // 媒体服务器中的用户Id,
                    mediaId, // 呼叫方的媒体Id, 同时存在userId与mediaId则为双向通话, 被叫方需要到服务器中拉取呼叫方的多媒体数据
                    audio: true,
                    video: true,
                    videoRatio: '640*480' // 被拉取方的要求上传的视频分辨率 高清 1280*720 / 标清 640*480 / 流畅 320*240
                  }
                }
                setTimeout(() => {
                  mqttClicent.publish(
                    'target/media/signal/' + remoteId,
                    JSON.stringify(cmd)
                  )
                }, 100)
              },
              videoClose: () => {
                pushVideoObj = null
              },
              error: (err) => {
                console.log('open local video fail: ' + err)
              }
            })
          },
          error: (err) => {
            console.log('init publish video fail:' + err)
          }
        })
      }, 5000)
    } else {
      // eslint-disable-next-line no-undef
      pushVideoObj = new PublishVideo()
      pushVideoObj.init({
        success: () => {
          pushVideoObj.openLocalVideo({
            roomId: 1234,
            isAudio: true,
            isVideo: true,
            success: (userId, mediaId) => {
              // 成功后发送mqtt，通知终端进行全双工会话
              let cmd = {
                cmd: 'call',
                to: remoteId, // 目标者Id
                from: dispatchId, // 来源者Id
                name: '测试账号',
                sessionId: '', // 会话Id由会话发起者创建
                timestamp: new Date().getTime(), // 时间戳(单位: 秒)
                data: {
                  calltype: 'dispatch',
                  server: mtServer, // 媒体服务器地址(带端口)
                  roomId: 1234, // 媒体房间Id
                  userId, // 媒体服务器中的用户Id,
                  mediaId, // 呼叫方的媒体Id, 同时存在userId与mediaId则为双向通话, 被叫方需要到服务器中拉取呼叫方的多媒体数据
                  audio: true,
                  video: true,
                  videoRatio: '640*480' // 被拉取方的要求上传的视频分辨率 高清 1280*720 / 标清 640*480 / 流畅 320*240
                }
              }
              setTimeout(() => {
                mqttClicent.publish(
                  'target/media/signal/' + remoteId,
                  JSON.stringify(cmd)
                )
              }, 100)
            },
            videoClose: () => {
              pushVideoObj = null
            },
            error: (err) => {
              console.log('open local video fail: ' + err)
            }
          })
        },
        error: (err) => {
          console.log('init publish video fail:' + err)
        }
      })
    }
  },
  // 终端发起
  ackPublishLocalVideo: function(remoteId) {
    // eslint-disable-next-line no-undef
    let pushVideoObj = new PublishVideo()
    pushVideoObj.init({
      success: () => {
        pushVideoObj.openLocalVideo({
          roomId: 1234,
          isAudio: true,
          isVideo: true,
          success: (userId, mediaId) => {
            // 成功后发送mqtt，通知终端进行全双工会话
            let cmd = {
              result: 'ack',
              to: remoteId, // 目标者Id
              from: dispatchId, // 来源者Id
              name: '测试账号',
              sessionId: '', // 会话Id由会话发起者创建
              timestamp: new Date().getTime(), // 时间戳(单位: 秒)
              data: {
                calltype: 'dispatch',
                server: wsServer, // 媒体服务器地址(带端口)
                roomId: 1234, // 媒体房间Id
                userId, // 媒体服务器中的用户Id,
                mediaId, // 呼叫方的媒体Id, 同时存在userId与mediaId则为双向通话, 被叫方需要到服务器中拉取呼叫方的多媒体数据
                audio: true,
                video: true,
                videoRatio: '320*240'
              }
            }
            setTimeout(() => {
              mqttClicent.publish(
                'target/media/signal/' + remoteId,
                JSON.stringify(cmd)
              )
            }, 100)
          },
          videoClose: () => {
            pushVideoObj = null
          },
          error: (err) => {
            console.log('open local video fail: ' + err)
          }
        })
      },
      error: (err) => {
        console.log('init publish video fail:' + err)
      }
    })
  },
  // 拉取视频
  sendPullVideoCmd: function (remoteId, suffixStr) {
    this.suffixStr = suffixStr || ''
    if (pushVideoObj) {
      this.closeRVideo(remoteId)
      setTimeout(() => {
        let cmd = {
          cmd: 'call',
          to: remoteId, // 目标者Id
          from: dispatchId, // 来源者Id
          name: '测试账号',
          sessionId: '', // 会话Id由会话发起者创建
          timestamp: new Date().getTime(), // 时间戳(单位: 秒)
          data: {
            calltype: 'dispatch',
            server: '', // 媒体服务器地址(带端口)
            roomId: '', // 媒体房间Id
            userId: '', // 媒体服务器中的用户Id,
            mediaId: '', // 呼叫方的媒体Id, 同时存在userId与mediaId则为双向通话, 被叫方需要到服务器中拉取呼叫方的多媒体数据
            audio: true, // 是否带音频
            video: true, // 是否带视频（注：音频视频不能同时为false）
            videoRatio: '640*480' // 被拉取方的要求上传的视频分辨率 高清 1280*720 / 标清 640*480 / 流畅 320*240
          }
        }
        setTimeout(() => {
          mqttClicent.publish(
            'target/media/signal/' + remoteId,
            JSON.stringify(cmd)
          )
        }, 100)
      }, 5000)
    } else {
      let cmd = {
        cmd: 'call',
        to: remoteId, // 目标者Id
        from: dispatchId, // 来源者Id
        name: '测试账号',
        sessionId: '', // 会话Id由会话发起者创建
        timestamp: new Date().getTime(), // 时间戳(单位: 秒)
        data: {
          calltype: 'dispatch',
          server: '', // 媒体服务器地址(带端口)
          roomId: '', // 媒体房间Id
          userId: '', // 媒体服务器中的用户Id,
          mediaId: '', // 呼叫方的媒体Id, 同时存在userId与mediaId则为双向通话, 被叫方需要到服务器中拉取呼叫方的多媒体数据
          audio: true, // 是否带音频
          video: true, // 是否带视频（注：音频视频不能同时为false）
          videoRatio: '640*480' // 被拉取方的要求上传的视频分辨率 高清 1280*720 / 标清 640*480 / 流畅 320*240
        }
      }
      setTimeout(() => {
        mqttClicent.publish(
          'target/media/signal/' + remoteId,
          JSON.stringify(cmd)
        )
      }, 100)
    }
  },
  closeRVideo: function (remoteId) {
    let cmd = {
      cmd: 'bye',
      to: remoteId,
      from: dispatchId, // 来源者Id
      sessionId: '',
      timestamp: new Date().getTime(), // 时间戳(单位: 秒)
      data: {
        calltype: 'dispatch'
      }
    }
    mqttClicent.publish(`target/media/signal/${remoteId}`, JSON.stringify(cmd))
    try {
      if (pullCallObjs[remoteId + this.suffixStr]) {
        pullCallObjs[remoteId + this.suffixStr].closeRemoteVideo()
      }
      if (pushVideoObj) {
        pushVideoObj.closeLocalVideo()
      }
    } catch (error) {
      console.log(error)
    }
  },
  loginMqtt: function () {
    if (mqttClicent) return
    let self = this
    // eslint-disable-next-line no-undef
    initLibrary(function (ok) {
      // eslint-disable-next-line no-undef
      setServerInfo(wsServer)
      console.log('init video library succeed.')
    })
    // eslint-disable-next-line no-undef
    mqttClicent = mqtt.connect(mqttServer, { username: mqttUserName, password: mqttUserPassword })
    mqttClicent.on('connect', function () {
      // 连接成功订阅主题
      mqttClicent.subscribe('target/media/signal/' + dispatchId, err => {
        console.log(err)
        if (!err) {
          console.log('成功订阅')
        }
      })
    })
    mqttClicent.on('message', function (topic, message) {
      let response = JSON.parse(message.toString())
      let data = response.data
      if (topic.indexOf('target/media/signal') > -1) {
        if (response.result === 'ack') {
          self.pullRVideo(data.roomId, data.userId, response.from, data.server)
        }
        if (response.result === 'cancel') {
          self.closeRVideo(response.from)
        }
        if (response.cmd === 'report') {
          // 拉取视频，视频上报
          self.pullRVideo(data.roomId, data.userId, response.from, data.server)
        }
        if (response.cmd === 'call') {
          self.pullRVideo(data.roomId, data.userId, response.from, data.server)
          self.ackPublishLocalVideo(response.from)
        }
      }
    })
  },
  pullRVideo: function (roomId, userId, remoteId, server) {
    let self = this
    if (server) {
      // eslint-disable-next-line no-undef
      setServerInfo(server)
    }
    // eslint-disable-next-line no-undef
    let pullCall = new SubscibeVideo()
    pullCall.init({
      success: function () {
        pullCall.openRemoteVideo({
          videoWnd: document.getElementById('MobileIndividualVideo_' + remoteId + self.suffixStr),
          roomId: roomId,
          userId: userId,
          isAudio: true,
          isVideo: true,
          success: function () {
            console.log('MobileIndividualVideo_' + remoteId + self.suffixStr + ' video open success!')
          },
          videoClose: function () {
            pullCall = null
          },
          // eslint-disable-next-line handle-callback-err
          error: function (err) {
          }
        })
      },
      error: function (err) {
        console.log('init pull video fail:' + err)
      }
    })
    pullCallObjs[remoteId + this.suffixStr] = pullCall
  }
}
export default mobileIndividualVideo
