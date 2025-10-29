<template>
  <div class="box-video">
    <video :id="vId" class="znv-video" muted autoplay controls></video>
  </div>
</template>

<script>
export default {
  name: 'FlvVideo',
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
      if (flvjs.isSupported()) {
        var videoElement = document.getElementById(this.vId)
        var flvPlayer = flvjs.createPlayer({
          type: 'flv',
          url: this.src
        })
        flvPlayer.attachMediaElement(videoElement)
        flvPlayer.load()
        flvPlayer.play()
        this.flvPlayer = flvPlayer
      }
    },
    dispose() {
      if (this.flvPlayer) {
        this.flvPlayer.pause()
        this.flvPlayer.unload()
        this.flvPlayer.detachMediaElement()
        this.flvPlayer.destroy()
        this.flvPlayer = null
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
