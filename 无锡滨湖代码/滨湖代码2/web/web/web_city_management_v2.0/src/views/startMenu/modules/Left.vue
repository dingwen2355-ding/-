<template>
  <div class="Left" :class="{ active: active }">
    <!-- <DutyInformation /> -->
    <EventHandling />
    <EventToday />
    <EventProfile :info="info" />
    <nearThing />
    <left2To1 />
  </div>
</template>

<script>
import nearThing from './left/nearThing.vue'
import EventHandling from './left/EventHandling.vue'
import EventProfile from './left/EventProfile.vue'
import DutyInformation from '@/views/startMenu/modules/left/DutyInformation.vue'
import EventToday from '@/views/startMenu/modules/left/EventToday.vue'
import Axios from '@/utils/request'
import left2To1 from './left/left2To1.vue'
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
    EventToday,
    DutyInformation,
    left2To1,
    nearThing,
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
  left: 0;
  z-index: 1;
  top: 0;
  width: 4260rem;
  height: 100%;
  background-image: linear-gradient(to right, RGBA(0, 13, 64, 1), RGBA(1, 31, 101, 1) 65%, RGBA(1, 31, 101, 0));
  transition: 1s;
  /*  &.active {
    left: 0;
    z-index: 1;
  }*/
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
