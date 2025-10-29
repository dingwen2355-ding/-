// 襄阳的那个单兵
import $ from 'jquery'

// let server = 'http://10.190.162.5:8080'
let token = ''
// let account = 'zhzx2'
// let account = 'zhzx'
// let password = ''
let remoteId = ''
let uniqueId = ''
let videoController
/**
 * 入口js
 */
// 参数
window.token = ''
window.server = ''
window.account = ''
window.password = ''
window.name = ''
var heartTime

/**
 * 初始化登录
 */
export function doLogin() {
  // var name = account
  // password = sha256_digest(password)
  var param = {
    accUsername: window.name,
    accPassword: sha256_digest(window.password)
  }
  var loginServer = _util.getApiCoreServer('/data/system/authManage/loginTo')
  _util._loadJson(loginServer, param, function (data) {
    if (data.code === 0) {
      window.token = data.data
      token = data.data
      console.log('登录成功')
      _dispUtils.connectToServer()
      // 启动心跳保持
      if (heartTime) {
        clearInterval(heartTime)
      }
      setInterval(keepHeart, 15000)

      // 通讯录树图加载
      // window._core.init()
      // window._sub.init()
      // window._video.init()

      // 加载账号信息
      var accParam = {
        validToken: window.token
      }
      var accDetailUrl = _util.getApiCoreServer('/data/system/accountManage/getAccountDetail')
      _util._loadJson(accDetailUrl, accParam, function (result) {
        if (result.code == 0) {
          window.account = result.data
        }
      })
    } else {
      console.warn('登录失败，' + data.message)
    }
  })
}

/**
 * 登出
 */
export function doLogout() {
  var logOutServer = _util.getApiCoreServer('/data/system/authManage/logout')
  if (!window.token) {
    console.warn('账号未登录，无法进行登出操作！')
    return
  }
  var param = {
    'token': window.token
  }
  _util._loadJson(logOutServer, param, function (data) {
    if (data.code == 0) {
      console.log('登出成功')
      if (heartTime) {
        clearInterval(heartTime)
      }
    }
  })
}

/**
 * 心跳动作
 */
function keepHeart() {
  var heartUrl = _util.getApiCoreServer('/data/system/authManage/heartbeat')
  _util._loadJson(heartUrl, null, function (data) {
    if (data.code != 0) {
      console.log('账号心跳保持失败，' + data.message)
    }
  })
}

/**
 * 设置remoteId
 */
function setRomoteId(id, uId) {
  remoteId = id
  uniqueId = uId
}

export function makeVideoCall(remoteId, domId, type) {
  setRomoteId(remoteId, domId)
  // type = type || 'makeCall'
  type = type || 'makeVideoCall'
  var caller = window.account.mainTel
  var callee = remoteId
  if (!callee) {
    console.warn('请选择发起择呼叫对象！')
  }

  var param = {
    'caller': caller,
    'callee': callee
  }
  var url = window._util.getApiDispServer('/api/call/' + type)
  window._util._loadJson(url, param, function (data) {
    console.log('呼叫结果', data)
  })
  setTimeout(() => {
    initVideo(remoteId, domId)
  }, 2000)
}

export function makeCall(remoteId, type) {
  setRomoteId(remoteId)
  type = type || 'makeCall'
  // type = type || 'makeVideoCall'
  _dispUtils.makeCall(type)
}

function initVideo(remoteId, domId) {
  var url = window.server + '/scooper-video/conf/data'
  var param = {}
  // 从接口获取视频的参数信息
  $.getJSON(url, param, function (data) {
    // 创建视频对象
    var conf = data
    // 参数
    var videoOpts = {
      // 初始化时的界面显示的分屏树
      // windows: 4,
      windows: 1,
      // 共有哪几种分屏
      windowsArr: [1, 4, 9, 16],
      // 总的窗口数
      windowsNum: 1,
      conf: {
        user: conf['video.username'],
        passwd: conf['video.password'],
        // videoServer ip
        ip: conf['video.ip'],
        // videoServer port
        port: conf['video.port'],
        // janus地址
        janusUrl: conf['video.janus.url']
      },
      // 码流类型，默认0
      streamType: conf['video.stream'],
      // 是否截屏
      capImage: conf['video.cap.image'] === 'true',
      // 截屏默认保存的地址
      videoCapImagePath: conf['video.cap.image.path'],

      // remoteId
      remoteId: remoteId,
      uniqueId: domId
    }
    // 轮巡的时间，单位秒
    conf['video.poll.time'] && (videoOpts.pollInterval = conf['video.poll.time'])

    // videoController = new scooper.video($('.video-area'), videoOpts)
    videoController = new scooper.video($('.' + domId), videoOpts)

    videoListenEvent(remoteId)
    // play(remoteId)
  })
}

function play(remoteId, uniqueId) {
  if (!remoteId) {
    console.warn('请输入视频ID')
    return
  }

  // if (videoController.isPlaying(uniqueId) != -1) {
  //   console.warn('该视频正在播放！')
  //   return
  // }
  // videoController.playByOrderExpandWindow(remoteId, remoteId, { name: remoteId, uniqueId: uniqueId })
  videoController.playByOrder(remoteId, remoteId, { name: remoteId, uniqueId: uniqueId })
}

export function stop(remoteId, uniqueId) {
  // var videoObj = videoController.getInChoiceVideo()

  // if (!videoObj) {
  //   console.warn('请选择正在播放的视频窗口！')
  //   return
  // }

  var index = videoController.isPlaying(uniqueId)
  videoController.close(index)
}

export function closeAll() {
  videoController.closeAll()
}

export function hungup(remoteId) {

  var param = {
    'tel': remoteId
  }
  var url = window._util.getApiDispServer('/api/call/hungup')
  window._util._loadJson(url, param, function (data) {
    console.log('挂断结果', data)
    // window._video.doClose(data.data.tel)
  })
}

function videoListenEvent(remoteId) {
  // 初始化完成
  videoController.addListener('initsucc', function (e) {
    console.log('initsucc')
    play(remoteId, uniqueId)
  })
  // 播放后的返回事件通知
  videoController.addListener('afterclose', function (e) {
    console.log('afterclose')
  })

  // 分屏的返回事件通知
  videoController.addListener('screenchange', function (e) {
    console.log('screenchange')
    // $('.menu-screen>li').removeClass('on-sel')
    // $('.menu-screen>li[mode=' + e.windowNums + ']').addClass('on-sel')
  })
}

/**
 * 工具类
 */
var _util = {
  /**
   * 获取通讯录访问url
   * @param url
   * @returns {string}
   */
  getApiCoreServer: function (url) {
    return window.server + '/scooper-core-rest' + url
  },

  /**
   * 获取调度访问url
   * @param url
   * @returns {string}
   */
  getApiDispServer: function (url) {
    return window.server + '/dispatch-web' + url
  },

  /**
   * 获取cometd后台服务地址
   * @returns {string}
   */
  getCometdServer: function () {
    return window.server + '/scooper-msg-queue/cometd'
  },

  /**
   * ajax加载方法
   * @param url      访问地址路径
   * @param param    携带参数
   * @param backSuccfn  请求成功回调方法
   * @param async      是否异步加载，默认true
   * @param method    请求方式,默认'POST'方式
   */
  _loadJson: function (url, param, backSuccfn, async, method) {
    method = (method || 'POST')
    async = (async == undefined ? true : async)

    if (param && !param.token) {
      param['token'] = window.token
    } else if (!param) {
      param = { token: window.token }
    }

    $.ajax({
      'method': method,
      'url': url,
      'data': param,
      'async': async
    }).fail(function (jqXHR, sts) {
      console.error('加载数据失败：' + sts + ', ' + url)
    }).done(function (ret) {
      if (!ret || ret.code != 0) {
        console.error('加载数据失败：' + JSON.stringify(ret) + ', ' + url)
      }
      if (backSuccfn) backSuccfn(ret)
    })
  }
}

window._util = _util

/**
 * 调度相关
 * @type {{}}
 * @private
 */
var _dispUtils = {
  /**
   * 获取选择人员的号码（操作号码）
   */
  getSelMemTel: function () {
    console.log('获取人员号码remoteId：', remoteId)
    return remoteId
  },
  /**
   * 发起呼叫
   * @param type  makeVideoCall 视频呼叫
   */
  makeCall: function (type) {
    type = type || 'makeCall'
    var caller = window.account.mainTel
    var callee = _dispUtils.getSelMemTel()
    if (!callee) {
      console.warn('请选择发起择呼叫对象！')
    }

    var param = {
      'caller': caller,
      'callee': callee
    }
    var url = window._util.getApiDispServer('/api/call/' + type)
    window._util._loadJson(url, param, function (data) {
      console.log('呼叫结果', data)
    })
  },
  makeAudioCall: function () {
    _dispUtils.makeCall()
  },
  /**
   * 发起视频呼叫
   */
  makeVideoCall: function () {
    _dispUtils.makeCall('makeVideoCall')
  },
  /**
         * 与调度服务器建立连接
         * @param cb 连接成功回调函数
         */
  connectToServer: function() {
    var url = window._util.getApiDispServer('/api/conn/connectionByToken')
    window._util._loadJson(url, {}, function(ret) {
      if (ret.code == 0) {
        console.log('调度服务连接成功', ret.data)
      }
    })
  },
  /**
   * 挂断
   */
  hungup: function () {
    var tel = _dispUtils.getSelMemTel()
    if (!tel) {
      console.warn('请选择挂断对象！')
    }

    var param = {
      'tel': tel
    }
    var url = window._util.getApiDispServer('/api/call/hungup')
    window._util._loadJson(url, param, function (data) {
      console.log('挂断结果', data)
      window._video.doClose(data.data.tel)
    })
  }
}
window._dispUtils = _dispUtils
