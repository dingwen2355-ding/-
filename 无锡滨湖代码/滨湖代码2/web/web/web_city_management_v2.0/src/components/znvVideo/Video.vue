<template>
  <div class="video">
    <hik-video :vId="vId" :cameraIndexCodes="cameraIndexCodes" :hikLayer="hikLayer" v-if="type === 'hik'" :isShow="isShow"></hik-video>
    <hls-video :vId="vId" :src="src" v-else-if="type === 'hls'"></hls-video>
    <flv-video :vId="vId" :src="src" v-else-if="type === 'flv'"></flv-video>
    <vcms-video :vId="vId" :src="src" v-else-if="type === 'vcms'"></vcms-video>
    <h265-video :vId="vId" :src="src" v-else-if="type === 'h265'"></h265-video>
    <mp4-video :vId="vId" :src="src" v-else="type === 'mp4'"></mp4-video>
  </div>
</template>

<script>
/**
 * 流媒体播放器3.0
 * @module 组件存放位置
 * @desc 同时支持vcms rtsp、flv、hls、海康四种流媒体
 * @author 李志伟0049003294
 * @date 2023年05月29日10:22:43
 * @param {String} [src]    - 流媒体链接 必传
 * @param {String} [type]    - 流媒体类型 必传
 */

import HlsVideo from './HlsVideo'
import FlvVideo from './FlvVideo'
import VcmsVideo from './VcmsVideo'
import HikVideo from './HikVideo'
import H265Video from './H265Video'
import Mp4Video from './Mp4Video'
export default {
  name: 'Video',
  components: {
    HikVideo,
    HlsVideo,
    FlvVideo,
    VcmsVideo,
    H265Video,
    Mp4Video
  },
  data() {
    return {
      vId: ''
    }
  },
  props: {
    src: {
      type: String,
      required: false,
      default: '',
    },
    type: {
      type: String,
      required: true,
      default: '',
    },
    cameraIndexCodes: {
      type: String,
      required: false,
      default: ""
    },
    hikLayer: {
      type: String,
      required: false,
      default: ""
    },
    isShow: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  created() {
    this.vId = this.createUUID()
  },
  methods: {
    createUUID() {
      let d = new Date().getTime()
      if (window.performance && typeof window.performance.now === 'function') {
        d += performance.now() // use high-precision timer if available
      }
      let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(
        /[xy]/g,
        function (c) {
          let r = (d + Math.random() * 16) % 16 | 0
          d = Math.floor(d / 16)
          return (c == 'x' ? r : (r & 0x3) | 0x8).toString(16)
        }
      )
      return uuid
    }
  }
}
</script>

<style>
.video{
  height: 100%;
}
</style>
