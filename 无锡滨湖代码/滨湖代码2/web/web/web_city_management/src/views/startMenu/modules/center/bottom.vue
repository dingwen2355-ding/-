<template>
  <div class="centerTops">
    <ul>
      <!-- <li v-for="item in tools1" :key="item.icon" @click="handle1(item)">
        <img :src="item.src" alt="" :title="item.title" />
        <span :style="{ background: item.activeIndex === true ? 'RGBA(0, 114, 201, 1)' : '' }">{{ item.title }}</span>
      </li> -->
      <li v-if="mapType === '3D'" @click="handle2('2D')">
        <img src="../../../../assets/global/地图_遥感.png" alt="" title="3D" />
        <span style="background: RGBA(0, 114, 201, 1)">3D</span>
      </li>
      <li v-else @click="handle2('3D')">
        <img src="../../../../assets/global/地图_普通.png" alt="" title="2D" />
        <span style="background: RGBA(0, 114, 201, 1)">2D</span>
      </li>
    </ul>
  </div>
</template>

<script>
// import ZnvMap from '@/components/map/znvMap-3d'
export default {
  components: {},
  data() {
    return {
      mapType: '2D',
      tools1: [
        {
          src: require('../../../../assets/global/地图_精英.png'),
          title: '精模',
          activeIndex: false
        },
        {
          src: require('../../../../assets/global/地图_白膜.png'),
          title: '白模',
          activeIndex: false
        }
      ],
      activeIndex: 0
    }
  },
  methods: {
    hand(v) {
      this.activeIndex = v
    },
    handle2(v) {
      this.mapType = v
      this.$emit('changeMapType', v)
    },
    handle1(v) {
    }
  }
}
</script>

<style lang="scss">
.centerTops {
  margin-right: 2rem;
  position: absolute;
  bottom: 20rem;
  right: 2300rem;
  ul {
    border: 1rem solid rgba(17, 155, 248, 0.34);
    background: RGBA(0, 0, 0, 1);
    li {
      padding: 40rem;
      cursor: pointer;
      position: relative;
      img {
        width: 800rem;
      }
      span {
        position: absolute;
        display: inline-block;
        width: 175rem;
        height: 90rem;
        bottom: -15rem;
        right: 38rem;
        text-align: center;
        line-height: 90rem;
        border-radius: 4rem;
      }
    }
  }
}
</style>
