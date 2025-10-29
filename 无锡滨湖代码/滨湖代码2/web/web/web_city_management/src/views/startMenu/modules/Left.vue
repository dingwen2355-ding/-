<template>
  <div class="Left" :class="{'active': active}">
     <EventHandling :info="info"/>
     <EventProfile :info="info"/>
     <SimilarThings />
     <nearThing />
     <left2To1 />
     <right1 :info="info" />
  </div>
</template>

<script>
import left2To1 from './left/left2To1.vue'
import SimilarThings from './left/SimilarThings.vue'
import nearThing from './left/nearThing.vue'
import EventHandling from './left/EventHandling.vue'
import EventProfile from './left/EventProfile.vue'
import Axios from '@/utils/request'
import right1 from './right/Right1.vue'
export default {
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
  components: {
    right1,
    left2To1,
    nearThing,
    SimilarThings,
    EventHandling,
    EventProfile
  },
  props: ['active'],
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
.Left {
  position: absolute;
  left: -1133rem;;
  top: 0;
  width: 4260rem;
  height: 100%;
  background-image: linear-gradient(to right, rgba(22,33,56, 1), rgba(22,33,56, 1) 65%, rgba(22,33,56, 0));
  transition: 1s;
  &.active{
    left: 0;
    z-index: 1;
  }
  .leftpic {
    width: 100%;
    height: 97%;
    background: url('../../../assets/dialog/左.png') no-repeat;
    background-size: 100% 100%;
    position: absolute;
    // top: 67rem;
    margin-left: 27rem;
    background-position-y: 97rem;
  }
}
</style>
