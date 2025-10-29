<template>
  <div class="box-video">
    <div :id="'player' + vId"></div>
  </div>
</template>

<script>
function isAppleMobileDevice() {
  return /iphone|ipod|ipad|Macintosh/i.test(navigator.userAgent.toLowerCase());
}

export default {
  name: "H265Video",
  data() {
    return {};
  },
  props: {
    vId: {
      type: String,
      required: true,
      default: ""
    },
    src: {
      type: String,
      required: true,
      default: ""
    }
  },
  watch: {
    src() {
      this.dispose();
      this.initVideo();
    }
  },
  mounted() {
    this.initVideo();
  },
  methods: {
    initVideo() {
      window.STATICE_MEM_playerCount = -1;
      window.STATICE_MEM_playerIndexPtr = 0;
      if (window.new265webjs && this.src) {
        let width = this.$el.offsetWidth
        let height = this.$el.offsetHeight
        var playerObj = new window.new265webjs(this.src, {
          player: "player" + this.vId,
          width: width,
          height: height,
          token:
            "base64:QXV0aG9yOmNoYW5neWFubG9uZ3xudW1iZXJ3b2xmLEdpdGh1YjpodHRwczovL2dpdGh1Yi5jb20vbnVtYmVyd29sZixFbWFpbDpwb3JzY2hlZ3QyM0Bmb3htYWlsLmNvbSxRUTo1MzEzNjU4NzIsSG9tZVBhZ2U6aHR0cDovL3h2aWRlby52aWRlbyxEaXNjb3JkOm51bWJlcndvbGYjODY5NCx3ZWNoYXI6bnVtYmVyd29sZjExLEJlaWppbmcsV29ya0luOkJhaWR1",
          extInfo: {
            autoPlay: true
          }
        });

        playerObj.onLoadFinish = () => {
          playerObj.play();
        };
        
        playerObj.do();

        this.playerObj = playerObj;
      }
    },
    dispose() {
      if (this.playerObj) {
        this.playerObj.release();
        this.playerObj = null;
      }
    }
  },
  beforeDestroy() {
    this.dispose();
  }
};
</script>

<style>
@import url(./viedo.css);
</style>
