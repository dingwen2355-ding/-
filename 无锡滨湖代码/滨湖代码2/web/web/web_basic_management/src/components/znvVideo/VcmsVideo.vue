<template>
  <div class="box-video">
    <video :id="vId" class="znv-video" muted autoplay controls></video>
  </div>
</template>

<script>
export default {
  name: 'VcmsVideo',
  data() {
    return {}
  },
  props: {
    vId: {
      type: String,
      required: true,
      default: ''
    },
    src: {
      type: String,
      required: true,
      default: ''
    }
  },
  watch: {
    src() {
      this.dispose()
      this.initVideo()
    }
  },
  mounted() {
    this.initVideo()
  },
  methods: {
    initVideo() {
      if (Streamedian && this.src) {
        this.playRtsp(0)
      }
    },
    initRtsp() {},
    playRtsp(streamType) {
      let self = this
      if (this.src) {
        $.ajax({
          url: '/apis/rtsp/queryUrl?cameraId=' + this.src + '&streamType=' + streamType,
          type: 'GET',
          headers: {
            Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
          },
          success: function (res) {
            let data = res.data[0]
            self.streamMark = data.streamMark
            let opt = {
              src: data.rtspUrl,
              socket: data.wsUrl,
              redirectNativeMediaErrors: true,
              bufferDuration: 10,
              statuHandler: self.stuHandler,
              isH265: true,
              isH265Url: data.wsUrl,
              streamMark: data.streamMark
            }
            var videoElement = document.getElementById(this.vId)
            var rtspPlayer = Streamedian.player(videoElement, opt)
            self.rtspPlayer = rtspPlayer
          },
          error: function () {
            console.log('H265参数请求失败')
          }
        })
      }
    },
    stuHandler(currentProxy, message) {
      if (message === 9000001) {
        this.rtspMessage = '正在请求视频'
      } else if (message === 9000002) {
        this.rtspMessage = '请求视频失败'
      } else if (message === 9000003) {
        this.rtspMessage = '正在重新拉取视频'
      } else if (message === 9000004) {
        this.rtspMessage = '正在重连视频'
      } else {
        this.rtspMessage = message.toFixed(2) + 'kbps'
      }
      console.log(this.rtspMessage)
    },
    destoryRtspStream() {
      if (this.streamMark) {
        $.ajax({
          url: 'apis/rtsp/closeStream?streamMark=' + encodeURIComponent(this.streamMark),
          type: 'GET',
          headers: {
            Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
          },
          success: function (res) {},
          error: function () {
            console.log('销毁rtsp失败')
          }
        })
      }
    },
    dispose() {
      if (this.rtspPlayer) {
        this.destoryRtspStream()
        this.rtspPlayer.destroy()
        this.rtspPlayer = null
      }
    }
  },
  beforeDestroy() {
    this.dispose()
  }
}
</script>

<style>
@import url(./viedo.css);
</style>
