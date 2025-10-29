import Stomp from 'stompjs'
import SockJS from 'sockjs-client'
// import { Message } from 'element-ui'

let serverUrl = 'http://119.3.144.189:8095/smcs'
let authUserId = (Math.random() * 100000000).toFixed()
let mdsUserType = '5'
let streamType = '1'
let stompClient = null

let mobileIndividual = {
  videoInfo: null,
  videoInfos: {},
  players: {},
  videoRef: null,
  cooldownTime: 1.5 * 1000,
  pullUpFreeze: false,
  flvDelayDetectTask: null, // flv延时检测
  isFlvFirstPlay: true, // 否是flv第一次播放
  suffixStr: '',
  connect: function () {
    const socket = new SockJS(serverUrl + '/smcs-websocket', null, { timeout: 15000 })
    stompClient = Stomp.over(socket)
    stompClient.debug = null
    stompClient.heartbeat.outgoing = 5 * 1000
    stompClient.heartbeat.incoming = 5 * 1000
    stompClient.connect({
      userId: authUserId,
      type: 'video'
    }, frame => {
      console.log('连接成功\t', frame)
      this.handleWsEvent()
    }, msg => {
      console.log('连接被断开\t', msg)
      stompClient = null
      this.reconnect()
    })
  },
  disconnect: function () {
    if (stompClient !== null) {
      console.log('断开连接')
      stompClient.disconnect()
      stompClient = null
      this.handleWsEvent()
    }
  },
  reconnect: function () {
    if (stompClient) {
      this.disconnect()
      setTimeout(() => {
        this.connect()
      }, 300)
    } else {
      this.connect()
    }
  },
  handleWsEvent: function () {
    if (!stompClient) {
      this.handleTerminate()
      return
    }
    stompClient.subscribe('/user/video/notify', message => {
      console.log('>>>RESP\t', message.body)
      this.handleMessage(JSON.parse(message.body))
    })
  },
  handleTerminate: function (userId) {
    // if (!userId) return
    // 挂断后销毁对应的播放器
    this.videoInfo = null
    if (userId) {
      if (this.players[userId + this.suffixStr]) {
        this.videoInfos[userId + this.suffixStr] = null
        this.players[userId + this.suffixStr].destroy()
      }
      this.players[userId + this.suffixStr] = null
    } else {
      for (let key in this.this.videoInfos[userId + this.suffixStr]) {
        this.videoInfos[key] = null
      }
      for (let key in this.players) {
        this.players[key].destroy()
        this.players[key] = null
      }
    }
    clearInterval(this.flvDelayDetectTask)
    this.flvDelayDetectTask = null
    this.isFlvFirstPlay = true
  },
  handleMessage: function (message) {
    if (message.result && message.result.code !== 0) {
      console.log(message.result.msg)
    }
    // console.log(message.result.data)
    console.log(message)
    switch (message.cmd) {
      case 'pull_up':
        break
      case 'stream_resource':
        this.handleStreamResource(message.result.data)
        break
      case 'terminate_pull_up':
        // this.handleTerminate(message.result.data.mdsUserId)
        break
      case 'terminate_notify':
        console.log('server挂断!')
        // this.handleTerminate()
        break
      default:
        console.error('unknown cmd: ' + message.cmd + ', message.identification: ' + message.identification)
    }
  },
  handleStreamResource: function (res) {
    if (this.videoInfos && this.videoInfos[res.mdsUserId + this.suffixStr]) {
      // if (this.videoInfo) {
      // 不处理角度旋转问题 --fix
      // this.handleStreamResourceAgain(res);
      return
    }
    this.videoInfo = res
    this.videoInfos[res.mdsUserId + this.suffixStr] = res
    setTimeout(() => {
      this.initFlvVideo(res)
    }, 1000)
  },
  initFlvVideo: function (videoInfo) { // 初始化FLV视频
    const url = atob(videoInfo.streamPath)
    console.log('FLV url: ' + url)
    // eslint-disable-next-line no-undef
    this.players[videoInfo.mdsUserId + this.suffixStr] = new FlvPlayer({
      el: document.getElementById('MobileIndividual_' + videoInfo.mdsUserId + this.suffixStr),
      url: url,
      fluid: true,
      autoplay: true,
      volume: 0.6,
      videoInit: true,
      ignores: ['time'],
      rotate: { // 视频旋转按钮配置项
        innerRotate: true, // 只旋转内部video
        clockwise: true // 旋转方向是否为顺时针
      }
    })
    this.players[videoInfo.mdsUserId + this.suffixStr].on('play', () => {
      this.players[videoInfo.mdsUserId + this.suffixStr].currentTime = this.players[videoInfo.mdsUserId + this.suffixStr].buffered.end(0)
      if (this.isFlvFirstPlay) {
        // 首次播放flv时候旋转图像, 并且增加定时延迟检测
        // 不处理角度旋转问题 --fix
        /* if (this.videoInfo.rotationAngle / 90 > 0) {
            this.player.rotate(true, true, this.videoInfo.rotationAngle/90);
        } */
        this.flvDelayDetectTask = setInterval(() => {
          const maxDelay = 1.5
          if (!this.players[videoInfo.mdsUserId + this.suffixStr] || !this.players[videoInfo.mdsUserId + this.suffixStr].buffered || this.players[videoInfo.mdsUserId + this.suffixStr].buffered.end.length < 1) {
            console.log('flv播放器已无内容, 请关闭或者重新播放视频!')
          } else if (this.players[videoInfo.mdsUserId + this.suffixStr].buffered.end(0) - this.players[videoInfo.mdsUserId + this.suffixStr].currentTime > 1.5) {
            console.log('flv流延迟已超过', maxDelay, 's, 重新矫正视频!')
            this.players[videoInfo.mdsUserId + this.suffixStr].currentTime = this.players[videoInfo.mdsUserId + this.suffixStr].buffered.end(0)
          }
        }, 3000)
        this.isFlvFirstPlay = false
      }
    })
    this.players[videoInfo.mdsUserId + this.suffixStr].on('error', param => {
      console.log('flv player error: ', param)
    })
  },
  send: function (msg) {
    console.log('>>>SEND\t', msg)
    stompClient.send('/api/video', {}, JSON.stringify(msg))
  },
  pullUpCooldown: function () {
    this.pullUpFreeze = true
    setTimeout(() => {
      this.pullUpFreeze = false
    }, this.cooldownTime)
  },
  // 挂断视频
  hangup: function (userId) {
    if (!userId) return
    this.pullUpCooldown()
    this.send({
      cmd: 'terminate_pull_up',
      identification: (Math.random() * 100000000).toFixed(),
      data: {
        mdsUserId: userId,
        mdsUserType: mdsUserType,
        streamType: streamType
      }
    })
    console.log(this.players)
    this.handleTerminate(userId)
  },
  // 拉取视频
  pullUp: function (userId, suffixStr) {
    this.suffixStr = suffixStr || ''
    this.pullUpCooldown()
    this.send({
      cmd: 'pull_up',
      identification: authUserId,
      data: {
        mdsUserId: userId,
        mdsUserType: mdsUserType,
        streamType: streamType
      }
    })
  }
}

export default mobileIndividual
