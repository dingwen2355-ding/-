<template>
  <div class="Right1 module">
    <SmallTitle :titleList="['移动单兵']" size="big" />
    <div class="content">
      <div class="video" v-for="item in videos" :key="item.deviceId">
        <!--        <ZnvVideo :src="item.url" :fontSize="16" :type="item.type" class="videoHeight"></ZnvVideo>-->
        <img class="img" :src="item.url" />
      </div>
    </div>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import ZnvVideo from '@/components/znvVideo/Video.vue'
import axios from '@/utils/request';;

export default {
  name: 'Right1',
  components: { SmallTitle, ZnvVideo },
  props: ['info'],
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
  watch: {
  },
  mounted() {
  },
  methods: {
    getEventCondition() {
      let url = '/community/dapeng/eventTask/query-condition'
      // if (eventId !== undefined) {
      //   url = `/community/dapeng/eventTask/query-condition?eventId=${eventId}`
      // } else {
      //   url = '/community/dapeng/eventTask/query-condition'
      // }
      axios.get(url).then((r) => {
        this.event = r.data.data.list.map((t) => {
          return {
            // id: t.id,
            // time: t.issueTime,
            // background: 'rgb(255, 66, 0)',
            // border: '1rem solid rgb(252, 150, 114)',
            // description: t.taskContent,
            // ...t
          }
        })
      })
    },
  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
  }
}
</script>

<style lang="scss">
.Right1 {
  width: 2840rem;
  // height: 850rem;
  position: absolute;
  top: 940rem;
  left: 0;

  .content {
    width: 2840rem;
    height: 700rem;
    padding: 52rem 88rem;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    display: flex;
    flex-wrap: nowrap;
    overflow-x: scroll;

    .video {
      .img {
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
