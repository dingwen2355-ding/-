<template>
    <div class="znv-video" @mouseenter="soltShow = true" @mouseleave="soltShow = false">
      <div v-if="type === 'hls'" class="hls-container">
        <video v-if="playerType === 'videoJs'" :id="vId" style="width: 100%; height:100%;" class="video-js vjs-default-skin vjs-big-play-centered video"></video>
        <video v-else muted :id="vId" class="video" autoplay controls></video>
      </div>
      <video v-if="type === 'rtsp'" :id="vId" controls autoplay muted class="video">
        <source :src="src" type="application/x-rtsp" />
      </video>
      <div v-if="type === 'rtmp'" class="hls-container small-window-rtmp">
        <video v-if="playerType === 'videoJs'" :id="vId" style="width: 100%; height:100%;" class="video-js vjs-default-skin vjs-big-play-centered video">
          <source :src="src" type="rtmp/flv">
        </video>
        <video v-else :id="vId" class="video" autoplay controls muted></video>
      </div>
      <video v-if="type === 'mp4'" :src="src" :id="vId" controls autoplay muted loop class="video"></video>
      <transition name="fade">
        <div class="opt-box" v-show="$scopedSlots.$hasNormal && soltShow">
          <slot></slot>
        </div>
      </transition>
    </div>
</template>

<script>
/**
* 流媒体播放器
* @module 组件存放位置
* @desc 同时支持rtsp、rtmp和hls三种流媒体，hls、rtmp支持VideoJsPlayer和EZUIPlayer两种解码库
* @author 李志伟0049003294
* @date 2019年11月23日10:22:43
* @param {String} [src]    - 流媒体链接 必传
* @param {String} [vId] - 播放器ID 必传
* @param {String} [type]    - 流媒体类型 必传
* @param {String} [playerType] - 针对HLS、RTMP类型的流媒体指定解码库 非必传 EZUIPlayer videoJs
*/
import videojs from 'video.js'
import 'videojs-flash'
import 'video.js/dist/video-js.css'
import language from './video/hlsVideoLanguage.js'
import './video/streamedian/streamedian.min.js'
import EZUIKit from './video/EZUIKit-JavaScript-master/ezuikit.js'
if (!window.EZUIPlayer) {
  window.EZUIPlayer = EZUIKit.EZUIPlayer
}
videojs.addLanguage('zh-CN', {
  ...language.language,
  'Picture-in-Picture': '画中画'
})

export default {
  name: 'ZnvVideo',
  data() {
    return {
      soltShow: false
    }
  },
  props: {
    src: {
      type: String,
      required: true,
      default: ''
    },
    vId: {
      type: String,
      required: true,
      default: ''
    },
    type: {
      type: String,
      required: true,
      default: ''
    },
    playerType: {
      type: String,
      required: false,
      default: 'EZUIPlayer'
    }
  },
  mounted () {
    if (this.type === 'hls') {
      this.initHls()
    } else if (this.type === 'rtsp') {
      this.initRtsp()
    } else if (this.type === 'rtmp') {
      this.initRtmp()
    }
  },
  watch: {
    src () {
      this.initZnvVideo()
      if (this.type === 'hls') {
        if (this.playerType === 'videoJs') {
          this.player.src({
            src: this.src,
            type: 'application/x-mpegURL',
            withCredentials: false
          })
        } else {
          this.$nextTick(() => {
            $('#' + this.vId).attr('src', this.src)
            this.player = new window.EZUIPlayer(this.vId)
            this.player.play()
            this.player.znvVideoSrcType = 'hls'
            this.player.znvVideoPlayerType = 'EZUIPlayer'
          })
        }
      } else if (this.type === 'rtsp') {
        this.initRtsp()
      } else if (this.type === 'rtmp') {
        if (this.playerType === 'videoJs') {
          this.player.src({
            src: this.src,
            type: 'rtmp/flv'
          })
        } else {
          this.$nextTick(() => {
            $('#' + this.vId).attr('src', this.src)
            this.player = new window.EZUIPlayer(this.vId)
            this.player.play()
            this.player.znvVideoSrcType = 'rtmp'
            this.player.znvVideoPlayerType = 'EZUIPlayer'
          })
        }
      }
    }
  },
  methods: {
    // 切换视频流时调用，初始化播放器
    initZnvVideo() {
      if (this.player) {
        switch (this.player.znvVideoSrcType) {
          case 'hls' :
            if (this.player.znvVideoPlayerType === 'EZUIPlayer') {
              this.player.stop()
              $('#' + this.vId).attr('src', '')
            }
            break
          case 'rtsp' :
            this.destroyRtsp()
            break
          case 'rtmp' :
            if (this.player.znvVideoPlayerType === 'EZUIPlayer') {
              this.player.stop()
              $('#' + this.vId).replaceWith(`<video id="${this.vId}" class="video" autoplay controls muted></video>`)
            }
            break
        }
      }
    },
    // 初始化HLS视频流
    initHls () {
      if (!this.src) {
        return
      }
      if (this.playerType === 'videoJs') {
        this.player = videojs(this.vId, {
          autoplay: true,
          controls: true,
          muted: true,
          language: 'zh-CN',
          techOrder: ['html5']
        })
        this.player.src({
          src: this.src,
          type: 'application/x-mpegURL',
          withCredentials: false
        })
        this.player.znvVideoSrcType = 'hls'
        this.player.znvVideoPlayerType = 'videoJs'
      } else {
        this.$nextTick(() => {
          $('#' + this.vId).attr('src', this.src)
          this.player = new window.EZUIPlayer(this.vId)
          this.player.play()
          this.player.znvVideoSrcType = 'hls'
          this.player.znvVideoPlayerType = 'EZUIPlayer'
        })
      }
    },
    // 初始化RTSP视频流
    initRtsp () {
      this.$nextTick(() => {
        if (window.Streamedian && this.src) {
          let errHandler = function (err) {
            console.log(err.message)
          }
          let playerOptions = {
            socket: sysConfig.rtspWebSocketUrl,
            redirectNativeMediaErrors: true,
            bufferDuration: 30,
            errorHandler: errHandler
          }
          this.player = window.Streamedian.player(
            this.vId,
            playerOptions
          )
          this.player.znvVideoSrcType = 'rtsp'
          this.player.znvVideoPlayerType = 'streamedian'
        }
      })
    },
    // 初始化RTMP视频流
    initRtmp () {
      if (!this.src) {
        return
      }
      if (this.playerType === 'videoJs') {
        this.$nextTick(() => {
          this.player = videojs(this.vId, {
            autoplay: true,
            controls: true,
            muted: true,
            language: 'zh-CN',
            poster: require('../assets/mapFilter/map_layer_ring@2x.png'),
            controlBar: {
              fullscreenToggle: true, // 全屏按钮，默认为true
              pictureInPictureToggle: false, // 画中画按钮，默认为true
              volumePanel: true, // 声音面板
              currentTimeDisplay: false, // 当前播放时间
              timeDivider: false, // 时间分割线
              durationDisplay: false, // 总时间
              remainingTimeDisplay: false // 剩余时间，
            }
          })
          this.player.src({
            src: this.src,
            type: 'rtmp/flv'
          })
          this.player.znvVideoSrcType = 'rtmp'
          this.player.znvVideoPlayerType = 'videoJs'
        })
      } else {
        this.$nextTick(() => {
          $('#' + this.vId).attr('src', this.src)
          this.player = new window.EZUIPlayer(this.vId)
          this.player.play()
          this.player.znvVideoSrcType = 'rtmp'
          this.player.znvVideoPlayerType = 'EZUIPlayer'
        })
      }
    },
    // 销毁HLS视频流以及对应播放器实例
    destroyHls () {
      if (this.player && this.playerType === 'videoJs') {
        this.player.dispose()
      } else {
        this.player.stop()
        $('#' + this.vId).attr('src', '')
      }
      this.player = null
    },
    // 销毁RTSP视频流以及对应播放器实例
    destroyRtsp () {
      if (this.player) {
        this.player.destroy()
        this.player = null
      }
    },
    // 销毁RTMP视频流以及对应播放器实例
    destroyRtmp () {
      if (this.player && this.playerType === 'videoJs') {
        this.player.dispose()
      } else {
        this.player.stop()
        $('#' + this.vId).replaceWith(`<video id="${this.vId}" class="video" autoplay controls muted></video>`)
      }
      this.player = null
    }
  },
  destroyed () {
    if (this.type === 'hls') {
      this.destroyHls()
    } else if (this.type === 'rtsp') {
      this.destroyRtsp()
    } else if (this.type === 'rtmp') {
      this.destroyRtmp()
    }
  }
}
</script>

<style scoped lang="scss">
.znv-video{
  position: relative;
  width: 100%;
  height: 100%;
  overflow: hidden;
  border: 2px solid rgba(61,135,243,1);
  box-shadow: 0px 8px 10px 0px rgba(0,13,42,0.3);

  .hls-container{
    width: 100%;
    height: 100%;
  }
  .small-window-rtmp {
    width: 200%;
    height: 200%;
    transform: scale(.5);
    transform-origin: left top;
  }
  video{
    width: 100%;
    height: 100%;
    object-fit: fill;
  }
  .opt-box{
    position: absolute;
    width: 100%;
    height: 20%;
    left: 0;
    top: 0;
    background-image: linear-gradient(rgba(0,0,0,.9), rgba(0,0,0,.2));
    padding: 0 5%;
  }
}
.fade-leave-active {
  transition: all .8s ease;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  transform: translateY(-100%);
}
</style>
