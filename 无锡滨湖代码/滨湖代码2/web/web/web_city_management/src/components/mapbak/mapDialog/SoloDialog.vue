<template>
  <!-- <el-dialog
    :title="infomation.precinct !== '510113' ? (infomation.deviceName ? infomation.deviceName : infomation.unit) : infomation.unit"
    :visible.sync="dialogVisible"
    :modal="false"
    width="500rem"
    height="900rem"
    class="SoloDialog"
    :close-on-click-modal="false"
    :before-close="handleClose"
  > -->
  <div class="SoloDialog">
    <div style="font-size: 18rem;color: #fff;">名称：{{ infomation.precinct !== '510113' ? (infomation.deviceName ? infomation.deviceName : infomation.unit) : infomation.unit }}</div>
    <div class="video-container" v-if="infomation.precinctAll === '510116'">
    <!-- 青白江区 -->
    <div class="box-item" style="height: 100rem;margin-bottom: 0;padding-top: 12rem;color: #fff;font-size: 30rem;margin-top: 22%;margin-left: 25%;" v-if="infomation.precinct === '510113'">
        <div>
          姓名：{{ infomation.deviceName }}
        </div>
        <div class="action-info">
          <div class="action-btn" @click="playTalk()" style="width: 200rem;margin-top: 30rem;margin-left: 2%;">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            发起视频通话
          </div>
          <!-- <div class="action-btn" @click="trackView">查看实时轨迹</div> -->
        </div>
      </div>
      <!-- 双流区 -->
      <div class="box-item" style="height: 100rem;margin-bottom: 0;padding-top: 12rem;color: #fff;font-size: 30rem;margin-top: 22%;margin-left: 25%;" v-else-if="infomation.precinct === '510116'">
        <div>
          姓名：{{ infomation.deviceName }}
        </div>
        <div class="action-info">
          <div class="action-btn" @click="playCall()" style="width: 200rem;margin-top: 30rem;margin-left: 2%;">
            <img src="../../../assets/global/video.png" class="img" alt="" />
            发起视频呼叫
          </div>
          <!-- <div class="action-btn" @click="trackView">查看实时轨迹</div> -->
        </div>
      </div>
      <div class="box-item" v-else>
        <video v-if="infomation.precinct === '510106' || infomation.precinct === '510104' || infomation.precinct === '510187' || infomation.precinct === '510105' ||
     infomation.precinct === '510121' || infomation.precinct === '510129' || infomation.precinct === '510181' || infomation.precinct === '510183' ||
      infomation.precinct === '510186' || infomation.precinct === '510117' || infomation.precinct === '510185' || infomation.precinct === '510108' ||
      infomation.precinct === '510131' || infomation.precinct === '510107' || infomation.precinct === '510182'" id="video1" autoplay controls></video>
        <div v-else id="cmsv6flash"></div>
        <div class="action-info" v-if="infomation.precinct === '510106' || infomation.precinct === '510104' || infomation.precinct === '510187' || infomation.precinct === '510105' ||
     infomation.precinct === '510121' || infomation.precinct === '510129' || infomation.precinct === '510181' || infomation.precinct === '510183' || infomation.precinct === '510117' || infomation.precinct === '510185' || infomation.precinct === '510108' ||
      infomation.precinct === '510131' || infomation.precinct === '510107' || infomation.precinct === '510182'">
          <!-- <div class="action-btn" @click="callOn()">
            <i class="iconfont iconmap_layer_yuyin"></i> 语音通话
          </div> -->
          <div class="action-btn" v-show="!isCloseVideo1" @click="callOn()">
            <img src="../../../assets/global/video.png" class="img" alt="" />
            视频播放
          </div>
          <div class="action-btn" v-show="isCloseVideo1" @click="videoOff()">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            挂断电话
          </div>
          <!-- <div class="action-btn" @click="trackView">查看实时轨迹</div> -->
        </div>
        <div class="action-info" v-else>
          <div class="action-btn" v-show="!isCloseVideo" @click="playVideo()">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            视频播放
          </div>
          <div class="action-btn" v-show="isCloseVideo" @click="stopVideo()">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            视频停止
          </div>
          <div class="action-btn t-red" v-show="isCloseCall" @click="stopMonitor()">
            <i class="iconfont iconzhihui_dianhua_guaduan"></i> 停止对讲
          </div>
          <div class="action-btn" v-show="!isCloseCall" @click="startMonitor()">
            <i class="iconfont iconmap_layer_yuyin"></i> 开始对讲
          </div>
          <!-- <div class="action-btn" @click="trackView">查看实时轨迹</div> -->
        </div>
      </div>
    </div>
    <div class="video-container" v-else>
      <div class="box-item">
        <video v-if="infomation.precinct === '510106' || infomation.precinct === '510104' || infomation.precinct === '510187' || infomation.precinct === '510105' ||
     infomation.precinct === '510121' || infomation.precinct === '510129' || infomation.precinct === '510181' || infomation.precinct === '510183' ||
      infomation.precinct === '510186' || infomation.precinct === '510117' || infomation.precinct === '510116' || infomation.precinct === '510185' || infomation.precinct === '510108' ||
      infomation.precinct === '510131' || infomation.precinct === '510107' || infomation.precinct === '510182'" id="video1" autoplay controls></video>
        <div v-else id="cmsv6flash"></div>
        <div class="action-info" v-if="infomation.precinct === '510106' || infomation.precinct === '510104' || infomation.precinct === '510187' || infomation.precinct === '510105' ||
     infomation.precinct === '510121' || infomation.precinct === '510129' || infomation.precinct === '510181' || infomation.precinct === '510116' ||
     infomation.precinct === '510183' || infomation.precinct === '510117' || infomation.precinct === '510185' || infomation.precinct === '510108' ||
      infomation.precinct === '510131' || infomation.precinct === '510107' || infomation.precinct === '510182'">
          <!-- <div class="action-btn" @click="callOn()">
            <i class="iconfont iconmap_layer_yuyin"></i> 语音通话
          </div> -->
          <div class="action-btn" v-show="!isCloseVideo1" @click="callOn()">
            <img src="../../../assets/global/video.png" class="img" alt="" />
            视频播放
          </div>
          <div class="action-btn" v-show="isCloseVideo1" @click="videoOff()">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            挂断电话
          </div>
          <!-- <div class="action-btn" @click="trackView">查看实时轨迹</div> -->
        </div>
        <div class="action-info" v-else>
          <div class="action-btn" v-show="!isCloseVideo" @click="playVideo()">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            视频播放
          </div>
          <div class="action-btn" v-show="isCloseVideo" @click="stopVideo()">
            <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
            <img src="../../../assets/global/video.png" class="img" alt="" />
            视频停止
          </div>
          <div class="action-btn t-red" v-show="isCloseCall" @click="stopMonitor()">
            <i class="iconfont iconzhihui_dianhua_guaduan"></i> 停止对讲
          </div>
          <div class="action-btn" v-show="!isCloseCall" @click="startMonitor()">
            <i class="iconfont iconmap_layer_yuyin"></i> 开始对讲
          </div>
          <!-- <div class="action-btn" @click="trackView">查看实时轨迹</div> -->
        </div>
      </div>
    </div>
  </div>
  <!-- </el-dialog> -->
</template>
<script>
import axios from '@/utils/request'
import qs from 'qs'
import moment from 'moment'
export default {
  data() {
    return {
      dialogVisible: false,
      videoList: [],
      jsession: '',
      devIdno: '',
      chnNum: '',
      serverIp: '',
      serverPort: '',
      isInitFinished: false,
      isCloseCall: false,
      isCloseVideo: false,
      isCloseVideo1: false,
      showVideoMeeting: false,
      infomation: {},
      showOut: false
      // fullscreenLoading: false
    }
  },
  mounted() {
    window.addEventListener('beforeunload', () => {
      if (this.showOut && (this.infomation.deviceName || this.infomation.device_name || this.infomation.name || this.infomation['设备名称'])) {
        let v = this.infomation
        let url = '/apis/saveLog'
        let obj = {
          area: '单兵-' + (v.deviceName ? v.deviceName : (v.device_name ? v.device_name : (v.name ? v.name : (v['设备名称'] ? v['设备名称'] : '')))),
          name: sessionStorage.trueName,
          precinctId: this.$route.query.precinctId,
          state: '已退出',
          time: moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
        }
        axios.post(url, obj)
        this.showOut = false
      }
    })
    window.addEventListener('unload', (e) => this.handleClose())
    let location = window.location.href.split('?')[1]
    if (location) {
      this.showOut = true
      this.infomation = qs.parse(location)
      if (this.infomation.precinct === '510113') {
        window.global_data.param_uid1 = this.infomation.id || 65877
        window.websdk.init(function (result) {
          if (result.code === 10000) { // 连接成功
            console.log('----websdk.init success, code:{}, desc:{}', result.code, result.desc)
            window.api_demo.req_login()
          } else { // 连接失败
            console.log('----websdk.init failure, code:{}, desc:{}', result.code, result.desc)
            return true
          }
          window.websdk.websdkui.configApi.setLogLevelDebug(function (result) {
          })
          if (window.websdk.private_cache && window.websdk.private_cache.login_uid) {
            console.log('---有缓存的init---')
            window.global_data.con_id = window.websdk.private_cache.login_uid
            window.global_data.videoMeetingBaseUrl = window.websdk.private_cache.videoMeetingBaseUrl
            window.global_data.https_port = window.websdk.private_cache.https_port
          } else {
            console.log('---无缓存的init---')
          }
          window.api_demo.checkMultiScreen()
          console.log('---demo begin to register notice ---')
          window.api_demo.logonNotice()
          window.api_demo.logonSuccessNotice()
          window.api_demo.logonRemoteNotice()
          window.api_demo.logoutNotice()
          window.api_demo.emergencyAlarmNotice()
          window.api_demo.emergencyHandledNotice()
          window.api_demo.userProfileNotice()
          window.api_demo.userParamsNotice()
          window.api_demo.userStateNotice()
          window.api_demo.userGPSNotice()
          window.api_demo.userQueryGPSNotice()
          window.api_demo.callStatusNotice()
          window.api_demo.pttStatusNotice()
          window.api_demo.groupAttachInfoNotice()
          window.api_demo.enterGroupNotice()
          window.api_demo.leaveGroupNotice()
          window.api_demo.addGroupMemberNotice()
          window.api_demo.removeGroupMemberNotice()
          window.api_demo.groupMemStatusNotice()
          window.api_demo.errMsgNotice()
          window.api_demo.dynamicNotice('notice_im_modal_open')
          window.api_demo.dynamicNotice('notice_im_modal_close')
          window.api_demo.dynamicNotice('notice_video_modal_open')
          window.api_demo.dynamicNotice('notice_video_modal_close')
          window.api_demo.dynamicNotice('notice_video_call_modal_open')
          window.api_demo.dynamicNotice('notice_video_call_modal_close')
          window.api_demo.dynamicNotice('notice_voice_call_modal_open')
          window.api_demo.dynamicNotice('notice_voice_call_modal_close')
          window.api_demo.dynamicNotice('notice_group_modal_minimize')
          window.api_demo.dynamicNotice('notice_group_modal_close')
          window.api_demo.dynamicNotice('notice_create_grp_success') // 创建临时群组成功通知
          window.api_demo.dynamicNotice('notice_vmeeting_list_modal_open')
          window.api_demo.dynamicNotice('notice_vmeeting_list_modal_close')
          window.api_demo.dynamicNotice('notice_vmeeting_start_modal_open')
          window.api_demo.dynamicNotice('notice_vmeeting_start_modal_close')
        })
      } else if (this.infomation.precinct === '510106') {
      // 金牛市
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.jinniuUrl)
        } else {
          this.loadParams1(sysConfig.jinniuUrl1)
        }
      } else if (this.infomation.precinct === '510104') {
      // 锦江区
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.jinjiangUrl)
        } else {
          this.loadParams1(sysConfig.jinjiangUrl1)
        }
      } else if (this.infomation.precinct === '510187') {
        this.loadParams1(sysConfig.jinjiangUrl1)
      } else if (this.infomation.precinct === '510121') {
      // 金堂县
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.jintangUrl)
        } else {
          this.loadParams1(sysConfig.jintangUrl1)
        }
      } else if (this.infomation.precinct === '510129') {
      // 大邑县
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.dayiUrl)
        } else {
          this.loadParams1(sysConfig.dayiUrl1)
        }
      } else if (this.infomation.precinct === '510181') {
      // 都江堰市
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.dujiangyanUrl)
        } else {
          this.loadParams1(sysConfig.dujiangyanUrl1)
        }
      } else if (this.infomation.precinct === '510183') {
      // 邛崃市
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.qinlaiUrl)
        } else {
          this.loadParams1(sysConfig.qinlaiUrl1)
        }
      } else if (this.infomation.precinct === '510105') {
      // 青羊区
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.qinyangUrl)
        } else {
          this.loadParams1(sysConfig.qinyangUrl1)
        }
      } else if (this.infomation.precinct === '510185') {
      // 简阳市
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.jianyangUrl)
        } else {
          this.loadParams1(sysConfig.jianyangUrl1)
        }
      } else if (this.infomation.precinct === '510108') {
      // 成华区
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.chenghuaUrl)
        } else {
          this.loadParams1(sysConfig.chenghuaUrl1)
        }
      } else if (this.infomation.precinct === '510131') {
      // 蒲江县
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.pujiangUrl)
        } else {
          this.loadParams1(sysConfig.pujiangUrl1)
        }
      } else if (this.infomation.precinct === '510107') {
      // 武侯区
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.wuhouUrl)
        } else {
          this.loadParams1(sysConfig.wuhouUrl1)
        }
      } else if (this.infomation.precinct === '510182') {
      // 彭州市
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.pengzhouUrl)
        } else {
          this.loadParams1(sysConfig.pengzhouUrl1)
        }
      } else if (this.infomation.precinct === '510117') {
      // 郫都区
        if (this.infomation.precinctAll) {
          this.loadParams1(sysConfig.piduUrl)
        } else {
          this.loadParams1(sysConfig.jinniuUrl1)
        }
      } else if (this.infomation.precinct === '510116') {
        // 双流区
        if (this.infomation.precinctAll) {
          let name = sysConfig.shuangliuUrl.name
          let pwd = sysConfig.shuangliuUrl.ps
          let ip = sysConfig.shuangliuUrl.ip
          window.Load()
          let timer = setTimeout(() => {
            window.excuteFucOfPlugin('Login', [name, pwd, ip])
            clearTimeout(timer)
          }, 2000)
        } else {
          this.loadParams1(sysConfig.shuangliuUrl1)
        }
      } else {
        this.loadParams()
        // if (!sessionStorage.getItem('checkTrue')) {
        this.getJS()
      // }
      }
    }
  },
  methods: {
    trackView() {
      let strWindowFeatures = 'top=100,left=500,width=600,height=500'
      window.open('/trackWay?id=' + this.infomation.id, this.infomation.id + new Date(), strWindowFeatures)
    },
    playTalk() {
      window.api_demo.req_play_video()
    },
    playCall() {
      let callNO = this.infomation.id || 100102
      window.excuteFucOfPlugin('SingleCallByNo', [callNO, 1])
      window.videoCall = true
    },
    getJS() {
      // 视频插件初始化参数
      let params = {
        lang: 'zh-cn' // "en", "zh-cn", "zh-tw"
      }
      // 视频插件类型 flash、h5、auto。
      // 如果不需要调用 ttxVideoAll.switchType 方法，建议明确 指定 flash或者h5
      let playType = 'auto'
      let width = 440
      let height = 320
      this.$nextTick(() => {
        window.ttxVideoAll.init('cmsv6flash', width, height, params, playType)
        this.initFlash()
      })
      sessionStorage.setItem('checkTrue', true)
    },
    loadParams() {
      let url = sysConfig.getSessionUrl
      axios.get(url).then((res) => {
        this.jsession = res.data.jsession
        // 设备号
        this.devIdno = this.infomation.id
        // 指定第几通道
        this.chnNum = 0
        // 设置视频插件的服务器ip
        this.serverIp = sysConfig.playerUrl.ip
        // 登录服务器端口
        this.serverPort = sysConfig.playerUrl.port
      })
    },
    loadParams1(val) {
      // this.fullscreenLoading = true
      sessionStorage.getLogin = ''
      let model = { Action: 'keeplive', GongHao: val.gonghao, FenJi: val.fenji, PlatFormCode: val.id, Params: '' }
      var wsParams = {
        url: val.url,
        IsReConnect: true, // 是否重连
        reConnectTime: 40, // 重连间隔
        IsKeepAlive: true, // 是否发送心跳
        KeepAliveTime: 30, // 20秒发送1次
        KeepAliveData: JSON.stringify(model), // 心跳
        onOpen: function (e) {
          // 登录语音系统
          let models = { Action: 'Login', GongHao: val.gonghao, FenJi: val.fenji, PlatFormCode: val.id, Params: '' }
          let modelt = {
            Action: 'SetGroupRole',
            GongHao: val.gonghao,
            FenJi: val.fenji,
            PlatFormCode: val.id,
            Params: val.zuoxi
          }
          window.VoiceInterfaceObject.Login(models, modelt)
        },
        onMessage: function (e) {
          // eslint-disable-next-line no-new-func
          var func = new Function('VoiceInterfaceObject.' + e.data)
          func()
        },
        onClose: function (e) {
          console.log('通讯链路断开等待重连')
        }
      }
      // 初始化WebSocket
      window.WebSocktInterface(wsParams)
      setTimeout(() => {
        this.videoOn(1)
        // this.fullscreenLoading = false
      }, 1000)
    },
    // 视频插件是否加载完成
    initFlash() {
      if (
        typeof window.swfobject === 'undefined' ||
        window.swfobject.getObjectById('cmsv6flash') == null ||
        typeof window.swfobject.getObjectById('cmsv6flash').setWindowNum === 'undefined'
      ) {
        setTimeout(this.initFlash, 50)
      } else {
        // 设置视频插件的语言('playerType=flash'时生效)
        window.swfobject.getObjectById('cmsv6flash').setLanguage('cn.xml')
        // 先将全部窗口创建好
        window.swfobject.getObjectById('cmsv6flash').setWindowNum(36)
        // 再配置当前显示的窗口数目
        window.swfobject.getObjectById('cmsv6flash').setWindowNum(1)
        // 设置视频插件的服务器ip和登录服务器端口
        window.swfobject.getObjectById('cmsv6flash').setServerInfo(sysConfig.playerUrl.ip, sysConfig.playerUrl.port)
        // 插件初始化完成
        this.isInitFinished = true
      }
    },
    playVideo() {
      this.isCloseVideo = true
      // 视频插件初始化完成
      if (this.isInitFinished) {
        // 停止播放视频
        window.swfobject.getObjectById('cmsv6flash').stopVideo(0)
        // 设置视频窗口标题
        window.swfobject.getObjectById('cmsv6flash').setVideoInfo(0, 'vehicle1-CH1')
        // 指定窗口下标
        let winIndex = 0
        // 播放视频
        window.swfobject.getObjectById('cmsv6flash').startVideo(winIndex, this.jsession, this.devIdno, this.chnNum, 1, true)
      } else {
        alert('插件初始化未完成')
      }
    },
    stopVideo() {
      this.isCloseVideo = false
      window.swfobject.getObjectById('cmsv6flash').stopVideo(0)
    },
    // onTtxVideoMsg(index, type) {
    //   if (type === 'playRecive') {
    //     alert('开启对讲')
    //   }
    //   if (type === 'stopTalk' || type === 'talkFinish') {
    //     alert('停止对讲')
    //   }
    //   if (type === 'showDownLoadDialog') {
    //     alert('需要下载对讲工具')
    //     this.downPcmTool()
    //   }
    // },
    // downPcmTool() {
    //   if (confirm('是否下载PCM对讲工具')) {
    //     window.open(sysConfig.downPCM)
    //   }
    // },
    startMonitor() {
      this.isCloseCall = true
      // 开始对讲
      window.swfobject
        .getObjectById('cmsv6flash')
        .startTalkback(this.jsession, this.devIdno, this.chnNum, this.serverIp, this.serverPort)
    },
    stopMonitor() {
      this.isCloseCall = false
      window.swfobject.getObjectById('cmsv6flash').stopTalkback()
    },
    handleClose() {
      this.dialogVisible = false
      if (this.infomation.precinct !== '510113' && this.infomation.precinct !== '510106' && this.infomation.precinct !== '510187' && this.infomation.precinct !== '510104' && this.infomation.precinct !== '510105' &&
     this.infomation.precinct !== '510121' && this.infomation.precinct !== '510129' && this.infomation.precinct !== '510181' && this.infomation.precinct !== '510183' &&
     this.infomation.precinct !== '510185' && this.infomation.precinct !== '510108' && this.infomation.precinct !== '510131' &&
      this.infomation.precinct !== '510107' && this.infomation.precinct !== '510182' && this.infomation.precinct !== '510116' && this.infomation.precinct !== '510117') {
        let url = sysConfig.closeSessionUrl + this.jsession
        axios.get(url).then((res) => {
          console.log('已退出')
        })
      }
      if (this.infomation.precinct === '510113') {
        window.api_demo.req_logout()
      } else if (this.infomation.precinct === '510106') {
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510104') {
        // 锦江区
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510187') {
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510121') {
        // 金堂县
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510129') {
        // 大邑县
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510181') {
        // 都江堰市
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510183') {
        // 邛崃市
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510105') {
        // 青羊区
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510185') {
        // 简阳市
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510108') {
        // 成华区
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510131') {
        // 蒲江县
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510107') {
        // 武侯区
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510182') {
        // 彭州市
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else if (this.infomation.precinct === '510116') {
        // 双流区
        if (this.isCloseVideo1) {
          this.videoOff()
        }
        if (this.infomation.precinctAll) {
          window.Logout()
        }
      } else if (this.infomation.precinct === '510117') {
        // 郫都区
        if (this.isCloseVideo1) {
          this.videoOff()
        }
      } else {
        if (this.isCloseVideo) {
          this.stopVideo()
        }
        if (this.isCloseCall) {
          this.stopMonitor()
        }
      }
      this.$emit('update:dialogVisibleFlag', false)
    },
    callOn() {
      this.isCloseVideo1 = true
      let callNum = this.infomation.id || 6008
      let calls = {}
      if (this.infomation.precinct === '510106') {
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jinniuUrl1.gonghao, FenJi: sysConfig.jinniuUrl1.fenji, PlatFormCode: sysConfig.jinniuUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jinniuUrl.gonghao, FenJi: sysConfig.jinniuUrl.fenji, PlatFormCode: sysConfig.jinniuUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510104') {
        // 锦江区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jinjiangUrl1.gonghao, FenJi: sysConfig.jinjiangUrl1.fenji, PlatFormCode: sysConfig.jinjiangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jinjiangUrl.gonghao, FenJi: sysConfig.jinjiangUrl.fenji, PlatFormCode: sysConfig.jinjiangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510187') {
        calls = { Action: 'VideoCall', GongHao: sysConfig.jinjiangUrl1.gonghao, FenJi: sysConfig.jinjiangUrl1.fenji, PlatFormCode: sysConfig.jinjiangUrl1.id, Params: callNum }
      } else if (this.infomation.precinct === '510121') {
        // 金堂县
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jintangUrl1.gonghao, FenJi: sysConfig.jintangUrl1.fenji, PlatFormCode: sysConfig.jintangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jintangUrl.gonghao, FenJi: sysConfig.jintangUrl.fenji, PlatFormCode: sysConfig.jintangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510129') {
        // 大邑县
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.dayiUrl1.gonghao, FenJi: sysConfig.dayiUrl1.fenji, PlatFormCode: sysConfig.dayiUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.dayiUrl.gonghao, FenJi: sysConfig.dayiUrl.fenji, PlatFormCode: sysConfig.dayiUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510181') {
        // 都江堰市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.dujiangyanUrl1.gonghao, FenJi: sysConfig.dujiangyanUrl1.fenji, PlatFormCode: sysConfig.dujiangyanUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.dujiangyanUrl.gonghao, FenJi: sysConfig.dujiangyanUrl.fenji, PlatFormCode: sysConfig.dujiangyanUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510183') {
        // 邛崃市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.qinlaiUrl1.gonghao, FenJi: sysConfig.qinlaiUrl1.fenji, PlatFormCode: sysConfig.qinlaiUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.qinlaiUrl.gonghao, FenJi: sysConfig.qinlaiUrl.fenji, PlatFormCode: sysConfig.qinlaiUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510105') {
        // 青羊区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.qinyangUrl1.gonghao, FenJi: sysConfig.qinyangUrl1.fenji, PlatFormCode: sysConfig.qinyangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.qinyangUrl.gonghao, FenJi: sysConfig.qinyangUrl.fenji, PlatFormCode: sysConfig.qinyangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510185') {
        // 简阳市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jianyangUrl1.gonghao, FenJi: sysConfig.jianyangUrl1.fenji, PlatFormCode: sysConfig.jianyangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jianyangUrl.gonghao, FenJi: sysConfig.jianyangUrl.fenji, PlatFormCode: sysConfig.jianyangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510108') {
        // 成华区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.chenghuaUrl1.gonghao, FenJi: sysConfig.chenghuaUrl1.fenji, PlatFormCode: sysConfig.chenghuaUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.chenghuaUrl.gonghao, FenJi: sysConfig.chenghuaUrl.fenji, PlatFormCode: sysConfig.chenghuaUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510131') {
        // 蒲江县
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.pujiangUrl1.gonghao, FenJi: sysConfig.pujiangUrl1.fenji, PlatFormCode: sysConfig.pujiangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.pujiangUrl.gonghao, FenJi: sysConfig.pujiangUrl.fenji, PlatFormCode: sysConfig.pujiangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510107') {
        // 武侯区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.wuhouUrl1.gonghao, FenJi: sysConfig.wuhouUrl1.fenji, PlatFormCode: sysConfig.wuhouUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.wuhouUrl.gonghao, FenJi: sysConfig.wuhouUrl.fenji, PlatFormCode: sysConfig.wuhouUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510182') {
        // 彭州市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.pengzhouUrl1.gonghao, FenJi: sysConfig.pengzhouUrl1.fenji, PlatFormCode: sysConfig.pengzhouUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.pengzhouUrl.gonghao, FenJi: sysConfig.pengzhouUrl.fenji, PlatFormCode: sysConfig.pengzhouUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510116') {
        // 双流区
        calls = { Action: 'VideoCall', GongHao: sysConfig.shuanghuaUrl1.gonghao, FenJi: sysConfig.shuanghuaUrl1.fenji, PlatFormCode: sysConfig.shuanghuaUrl1.id, Params: callNum }
      } else if (this.infomation.precinct === '510117') {
        // 郫都区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'VideoCall', GongHao: sysConfig.jinniuUrl1.gonghao, FenJi: sysConfig.jinniuUrl1.fenji, PlatFormCode: sysConfig.jinniuUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'VideoCall', GongHao: sysConfig.piduUrl.gonghao, FenJi: sysConfig.piduUrl.fenji, PlatFormCode: sysConfig.piduUrl.id, Params: callNum }
        }
      }
      window.VoiceInterfaceObject.VideoCall(calls)
    },
    videoOn(e) {
      if (!e) {
        this.isCloseVideo1 = true
      }
      let callNum = this.infomation.id || 6008
      let calls = {}
      if (this.infomation.precinct === '510106') {
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.jinniuUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jinniuUrl1.gonghao, FenJi: sysConfig.jinniuUrl1.fenji, PlatFormCode: sysConfig.jinniuUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.jinniuUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jinniuUrl.gonghao, FenJi: sysConfig.jinniuUrl.fenji, PlatFormCode: sysConfig.jinniuUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510104') {
        // 锦江区
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.jinjiangUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jinjiangUrl1.gonghao, FenJi: sysConfig.jinjiangUrl1.fenji, PlatFormCode: sysConfig.jinjiangUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.jinjiangUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jinjiangUrl.gonghao, FenJi: sysConfig.jinjiangUrl.fenji, PlatFormCode: sysConfig.jinjiangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510187') {
        window.wrurl = sysConfig.jinjiangUrl1.rtsp
        calls = { Action: 'Rtmp', GongHao: sysConfig.jinjiangUrl1.gonghao, FenJi: sysConfig.jinjiangUrl1.fenji, PlatFormCode: sysConfig.jinjiangUrl1.id, Params: callNum }
      } else if (this.infomation.precinct === '510121') {
        // 金堂县
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.jintangUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jintangUrl1.gonghao, FenJi: sysConfig.jintangUrl1.fenji, PlatFormCode: sysConfig.jintangUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.jintangUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jintangUrl.gonghao, FenJi: sysConfig.jintangUrl.fenji, PlatFormCode: sysConfig.jintangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510129') {
        // 大邑县
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.dayiUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.dayiUrl1.gonghao, FenJi: sysConfig.dayiUrl1.fenji, PlatFormCode: sysConfig.dayiUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.dayiUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.dayiUrl.gonghao, FenJi: sysConfig.dayiUrl.fenji, PlatFormCode: sysConfig.dayiUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510181') {
        // 都江堰市
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.dujiangyanUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.dujiangyanUrl1.gonghao, FenJi: sysConfig.dujiangyanUrl1.fenji, PlatFormCode: sysConfig.dujiangyanUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.dujiangyanUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.dujiangyanUrl.gonghao, FenJi: sysConfig.dujiangyanUrl.fenji, PlatFormCode: sysConfig.dujiangyanUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510183') {
        // 邛崃市
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.qinlaiUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.qinlaiUrl1.gonghao, FenJi: sysConfig.qinlaiUrl1.fenji, PlatFormCode: sysConfig.qinlaiUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.qinlaiUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.qinlaiUrl.gonghao, FenJi: sysConfig.qinlaiUrl.fenji, PlatFormCode: sysConfig.qinlaiUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510105') {
        // 青羊区
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.qinyangUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.qinyangUrl1.gonghao, FenJi: sysConfig.qinyangUrl1.fenji, PlatFormCode: sysConfig.qinyangUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.qinyangUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.qinyangUrl.gonghao, FenJi: sysConfig.qinyangUrl.fenji, PlatFormCode: sysConfig.qinyangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510185') {
        // 简阳市
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.jianyangUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jianyangUrl1.gonghao, FenJi: sysConfig.jianyangUrl1.fenji, PlatFormCode: sysConfig.jianyangUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.jianyangUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.jianyangUrl.gonghao, FenJi: sysConfig.jianyangUrl.fenji, PlatFormCode: sysConfig.jianyangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510108') {
        // 成华区
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.chenghuaUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.chenghuaUrl1.gonghao, FenJi: sysConfig.chenghuaUrl1.fenji, PlatFormCode: sysConfig.chenghuaUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.chenghuaUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.chenghuaUrl.gonghao, FenJi: sysConfig.chenghuaUrl.fenji, PlatFormCode: sysConfig.chenghuaUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510131') {
        // 蒲江县
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.pujiangUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.pujiangUrl1.gonghao, FenJi: sysConfig.pujiangUrl1.fenji, PlatFormCode: sysConfig.pujiangUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.pujiangUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.pujiangUrl.gonghao, FenJi: sysConfig.pujiangUrl.fenji, PlatFormCode: sysConfig.pujiangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510107') {
        // 武侯区
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.wuhouUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.wuhouUrl1.gonghao, FenJi: sysConfig.wuhouUrl1.fenji, PlatFormCode: sysConfig.wuhouUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.wuhouUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.wuhouUrl.gonghao, FenJi: sysConfig.wuhouUrl.fenji, PlatFormCode: sysConfig.wuhouUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510182') {
        // 彭州市
        if (!this.infomation.precinctAll) {
          window.wrurl = sysConfig.pengzhouUrl1.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.pengzhouUrl1.gonghao, FenJi: sysConfig.pengzhouUrl1.fenji, PlatFormCode: sysConfig.pengzhouUrl1.id, Params: callNum }
        } else {
          window.wrurl = sysConfig.pengzhouUrl.rtsp
          calls = { Action: 'Rtmp', GongHao: sysConfig.pengzhouUrl.gonghao, FenJi: sysConfig.pengzhouUrl.fenji, PlatFormCode: sysConfig.pengzhouUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510116') {
        // 双流区
        window.wrurl = sysConfig.shuanghuaUrl1.rtsp
        calls = { Action: 'Rtmp', GongHao: sysConfig.shuanghuaUrl1.gonghao, FenJi: sysConfig.shuanghuaUrl1.fenji, PlatFormCode: sysConfig.shuanghuaUrl1.id, Params: callNum }
      } else if (this.infomation.precinct === '510117') {
        // 郫都区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'Rtmp', GongHao: sysConfig.jinniuUrl1.gonghao, FenJi: sysConfig.jinniuUrl1.fenji, PlatFormCode: sysConfig.jinniuUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'Rtmp', GongHao: sysConfig.piduUrl.gonghao, FenJi: sysConfig.piduUrl.fenji, PlatFormCode: sysConfig.piduUrl.id, Params: callNum }
        }
      }
      window.VoiceInterfaceObject.Rtmp(calls)
    },
    videoOff() {
      let calls = {}
      let callNum = this.infomation.id || 6008
      if (this.infomation.precinct === '510106') {
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.jinniuUrl1.gonghao, FenJi: sysConfig.jinniuUrl1.fenji, PlatFormCode: sysConfig.jinniuUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.jinniuUrl.gonghao, FenJi: sysConfig.jinniuUrl.fenji, PlatFormCode: sysConfig.jinniuUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510104') {
        // 锦江区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.jinjiangUrl1.gonghao, FenJi: sysConfig.jinjiangUrl1.fenji, PlatFormCode: sysConfig.jinjiangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.jinjiangUrl.gonghao, FenJi: sysConfig.jinjiangUrl.fenji, PlatFormCode: sysConfig.jinjiangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510187') {
        calls = { Action: 'HangUp', GongHao: sysConfig.jinjiangUrl1.gonghao, FenJi: sysConfig.jinjiangUrl1.fenji, PlatFormCode: sysConfig.jinjiangUrl1.id, Params: callNum }
      } else if (this.infomation.precinct === '510121') {
        // 金堂县
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.jintangUrl1.gonghao, FenJi: sysConfig.jintangUrl1.fenji, PlatFormCode: sysConfig.jintangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.jintangUrl.gonghao, FenJi: sysConfig.jintangUrl.fenji, PlatFormCode: sysConfig.jintangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510129') {
        // 大邑县
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.dayiUrl1.gonghao, FenJi: sysConfig.dayiUrl1.fenji, PlatFormCode: sysConfig.dayiUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.dayiUrl.gonghao, FenJi: sysConfig.dayiUrl.fenji, PlatFormCode: sysConfig.dayiUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510181') {
        // 都江堰市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.dujiangyanUrl1.gonghao, FenJi: sysConfig.dujiangyanUrl1.fenji, PlatFormCode: sysConfig.dujiangyanUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.dujiangyanUrl.gonghao, FenJi: sysConfig.dujiangyanUrl.fenji, PlatFormCode: sysConfig.dujiangyanUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510183') {
        // 邛崃市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.qinlaiUrl1.gonghao, FenJi: sysConfig.qinlaiUrl1.fenji, PlatFormCode: sysConfig.qinlaiUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.qinlaiUrl.gonghao, FenJi: sysConfig.qinlaiUrl.fenji, PlatFormCode: sysConfig.qinlaiUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510105') {
        // 青羊区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.qinyangUrl1.gonghao, FenJi: sysConfig.qinyangUrl1.fenji, PlatFormCode: sysConfig.qinyangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.qinyangUrl.gonghao, FenJi: sysConfig.qinyangUrl.fenji, PlatFormCode: sysConfig.qinyangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510185') {
        // 简阳市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.jianyangUrl1.gonghao, FenJi: sysConfig.jianyangUrl1.fenji, PlatFormCode: sysConfig.jianyangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.jianyangUrl.gonghao, FenJi: sysConfig.jianyangUrl.fenji, PlatFormCode: sysConfig.jianyangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510108') {
        // 成华区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.chenghuaUrl1.gonghao, FenJi: sysConfig.chenghuaUrl1.fenji, PlatFormCode: sysConfig.chenghuaUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.chenghuaUrl.gonghao, FenJi: sysConfig.chenghuaUrl.fenji, PlatFormCode: sysConfig.chenghuaUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510131') {
        // 蒲江县
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.pujiangUrl1.gonghao, FenJi: sysConfig.pujiangUrl1.fenji, PlatFormCode: sysConfig.pujiangUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.pujiangUrl.gonghao, FenJi: sysConfig.pujiangUrl.fenji, PlatFormCode: sysConfig.pujiangUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510107') {
        // 武侯区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.wuhouUrl1.gonghao, FenJi: sysConfig.wuhouUrl1.fenji, PlatFormCode: sysConfig.wuhouUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.wuhouUrl.gonghao, FenJi: sysConfig.wuhouUrl.fenji, PlatFormCode: sysConfig.wuhouUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510182') {
        // 彭州市
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.pengzhouUrl1.gonghao, FenJi: sysConfig.pengzhouUrl1.fenji, PlatFormCode: sysConfig.pengzhouUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.pengzhouUrl.gonghao, FenJi: sysConfig.pengzhouUrl.fenji, PlatFormCode: sysConfig.pengzhouUrl.id, Params: callNum }
        }
      } else if (this.infomation.precinct === '510116') {
        // 双流区
        calls = { Action: 'HangUp', GongHao: sysConfig.shuanghuaUrl1.gonghao, FenJi: sysConfig.shuanghuaUrl1.fenji, PlatFormCode: sysConfig.shuanghuaUrl1.id, Params: callNum }
      } else if (this.infomation.precinct === '510117') {
        // 郫都区
        if (!this.infomation.precinctAll) {
          calls = { Action: 'HangUp', GongHao: sysConfig.jinniuUrl1.gonghao, FenJi: sysConfig.jinniuUrl1.fenji, PlatFormCode: sysConfig.jinniuUrl1.id, Params: callNum }
        } else {
          calls = { Action: 'HangUp', GongHao: sysConfig.piduUrl.gonghao, FenJi: sysConfig.piduUrl.fenji, PlatFormCode: sysConfig.piduUrl.id, Params: callNum }
        }
      }
      window.VoiceInterfaceObject.HangUp(calls)
      this.isCloseVideo1 = false
    }
  },
  beforeDestroy() {
    if (this.infomation.precinct !== '510113' && this.infomation.precinct !== '510106' && this.infomation.precinct !== '510104' && this.infomation.precinct !== '510187' && this.infomation.precinct !== '510105' &&
     this.infomation.precinct !== '510121' && this.infomation.precinct !== '510129' && this.infomation.precinct !== '510181' && this.infomation.precinct !== '510183' &&
     this.infomation.precinct !== '510185' && this.infomation.precinct !== '510108' && this.infomation.precinct !== '510131' &&
      this.infomation.precinct !== '510107' && this.infomation.precinct !== '510182' && this.infomation.precinct !== '510116' && this.infomation.precinct !== '510117') {
      let url = sysConfig.closeSessionUrl + this.jsession
      axios.get(url).then((res) => {
        console.log('已退出')
      })
    }
  },
  destroyed() {
    window.removeEventListener('unload', (e) => this.handleClose())
  }
}
</script>

<style lang="scss">
.SoloDialog {
  position: absolute;
  width: 100%;
  height: 100%;
  .el-dialog__body {
    padding-top: 0;
  }

  .video-container {
    // position: absolute;
    // left: 800rem;
    // top: 10rem;
    z-index: 2;
    padding-left: 8rem;

    .box-item {
      width: 100%;
      // height: 360rem;
      height: 100vh;
      margin: 0 auto;
      #cmsv6flash {
        width: 99% !important;
        height: 80% !important;
        margin-bottom: 10rem;
      }
      #video1 {
        width: 99%;
        margin-top: 2%;
        height: 77%;
        margin-bottom: 10rem;
      }

      .action-info {
        position: relative;
        text-align: center;
        display: flex;
        justify-content: center;
        .action-btn:first-of-type {
          margin-left: 0;
        }

        .action-btn {
          width: 130rem;
          height: 35rem;
          background: rgba(4, 12, 28, 0.4);
          color: #3df8f8;
          border: 1rem solid #3df8f8;
          border-radius: 4rem;
          font-size: 18rem;
          line-height: 35rem;
          float: left;
          cursor: pointer;
          margin-left: 14%;

          i {
            font-size: 16rem;
          }

          img {
            width: 20rem;
            margin-right: 10rem;
            vertical-align: middle;
          }
        }

        .t-red {
          color: red;
        }
      }
    }
  }
}
</style>
