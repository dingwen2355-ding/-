<template>
  <div class="Right2 module">
    <SimilarThings />
    <!-- <Right1 :info="info" />
    <right2To1 />-->
    <RightNew1 />
  </div>
</template>

<script>
// import SmallTitle from '@/components/SmallTitle.vue'
import SimilarThings from '.././left/SimilarThings.vue'
// import nearThing from '.././left/nearThing.vue'
// import EventHandling from '.././left/EventHandling.vue'
import Right1 from './Right1.vue'
import Axios from '@/utils/request'
import right2To1 from './Right2To1'
import RightNew1 from './RightNew1'
export default {
  name: 'Right2',
  components: { Right1, SimilarThings, right2To1, RightNew1 },
  data() {
    return {
      info: {}
    }
  },
  mounted() {
    this.getEventOverview()
  },
  activated() {
    this.getEventOverview()
  },
  methods: {
    // 事件概述
    getEventOverview() {
      let url = '/apis/manage/queryEventInfo'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.info = res.data.data.length > 0 ? res.data.data[0] : {}
          this.info.urgency = this.info.urgency || '重大'
        }
      })
    }
  }
}
</script>

<style lang="scss">
.Right2 {
  width: 2840rem;
  // height: 896rem;
  position: absolute;
  top: 430rem;
  //right: 1554rem;
  right: 80rem;
}
</style>
