<template>
  <div class="video">
    <hik-video :vId="vId" :cameraIndexCodes="cameraIndexCodes" :hikLayer="hikLayer" v-if="type === 'hik'" :isShow="isShow"></hik-video>
    <znv-video :vId="vId" :src="src" :type="type" v-else></znv-video>
  </div>
</template>

<script>
/**
 * 流媒体播放器2.0
 * @module 组件存放位置
 * @desc 同时支持rtsp、flv和hls三种流媒体，相比1.0去掉的rtmp的支持，同时使用video.js统一UI和API,代码更加精简，更容易集成以及使用。支持hik（海康平台对接视频）
 * @author 李志伟0049003294
 * @date 2021年05月20日10:22:43
 * @param {String} [src]    - 流媒体链接 必传
 * @param {String} [type]    - 流媒体类型 必传
 */

import ZnvVideo from './ZnvVideo'
import HikVideo from './HikVideo'
export default {
  name: 'Video',
  components: {
    ZnvVideo,
    HikVideo
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

<style lang="scss">
.video{
  height: 100%;
}
</style>
