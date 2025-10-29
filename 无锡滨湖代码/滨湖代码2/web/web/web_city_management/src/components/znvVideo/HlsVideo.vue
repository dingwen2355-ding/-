<template>
  <div class="box-video">
    <video :id="vId" class="znv-video" muted autoplay controls></video>
  </div>
</template>

<script>
function isAppleMobileDevice() {
  return /iphone|ipod|ipad|Macintosh/i.test(navigator.userAgent.toLowerCase())
}

export default {
  name: 'HlsVideo',
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
      if (isAppleMobileDevice()) {
        this.initIosVideo()
      } else {
        this.dispose()
        this.initVideo()
      }
    }
  },
  mounted() {
    if (isAppleMobileDevice()) {
      this.initIosVideo()
    } else {
      this.initVideo()
    }
  },
  methods: {
    initIosVideo() {
      var video = document.getElementById(this.vId)
      video.src = this.src
    },
    initVideo() {
      if (Hls.isSupported() && this.src) {
        var video = document.getElementById(this.vId)
        var hls = new Hls()
        // bind them together
        hls.attachMedia(video)
        // MEDIA_ATTACHED event is fired by hls object once MediaSource is ready
        hls.on(Hls.Events.MEDIA_ATTACHED, () => {
          console.log('video and hls.js are now bound together !')
          hls.loadSource(this.src)
        })
        hls.on(Hls.Events.MANIFEST_PARSED, function (event, data) {
          console.log('manifest loaded, found ' + data.levels.length + ' quality level')
        })
        hls.on(Hls.Events.ERROR, function (event, data) {
          if (data.fatal) {
            switch (data.type) {
              case Hls.ErrorTypes.MEDIA_ERROR:
                console.log('fatal media error encountered, try to recover')
                hls.recoverMediaError()
                break
              case Hls.ErrorTypes.NETWORK_ERROR:
                console.error('fatal network error encountered', data)
                // All retries and media options have been exhausted.
                // Immediately trying to restart loading could cause loop loading.
                // Consider modifying loading policies to best fit your asset and network
                // conditions (manifestLoadPolicy, playlistLoadPolicy, fragLoadPolicy).
                break
              default:
                // cannot recover
                hls.destroy()
                break
            }
          }
        })
        this.hls = hls
      }
    },
    dispose() {
      if (this.hls) {
        this.hls.destroy()
        this.hls = null
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
