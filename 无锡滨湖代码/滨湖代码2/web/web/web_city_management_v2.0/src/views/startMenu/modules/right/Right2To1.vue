<template>
  <div class="Right2To1 module">
    <SmallTitle :titleList="['无人机']" size="big" />
    <div class="content">
      <div class="video" v-for="item in videos" :key="item.deviceId">
<!--        <ZnvVideo :src="item.url" :fontSize="16" :type="item.type" class="videoHeight"></ZnvVideo>-->
        <img class="img" :src="item.url">
      </div>
    </div>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import ZnvVideo from "@/components/znvVideo/Video.vue";
export default {
  name: 'Right2To1',
  components: {
    ZnvVideo,
    SmallTitle
  },
  data() {
    return {
      videos: [
        {
          url: require('../../../../assets/建设.png'),
          type: 'mp4',
          deviceId: '0000'
        },
        {
          url: require('../../../../assets/建设.png'),
          type: 'mp4',
          deviceId: '0000'
        },
        {
          url: require('../../../../assets/建设.png'),
          type: 'mp4',
          deviceId: '0000'
        },
      ]
    }
  },
  computed: {
    showEmpty() {
      // this.leadershipList.length
      return this.eventList.length === 0 && !this.precinct
    }
  },
  mounted() {
  },
  methods: {

  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
    this.timer1 && clearInterval(this.timer1)
  }
}
</script>
<style lang="scss">
.Right2To1 {
  width: 2840rem;
  position: absolute;
  top: 0;
  left: 0;

  .content {
    display: flex;
    flex-wrap: nowrap;
    overflow-x: scroll;
    height: 700rem;
    padding: 52rem 88rem;
    position: relative;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;

    .video {

      .img{
        margin-left: 40rem;
        width: 840rem;
        height: 570rem;
      }
      .videoHeight {
        margin-left: 40rem;
        width: 840rem;
        height: 570rem;
      }
    }
  }
}
</style>
