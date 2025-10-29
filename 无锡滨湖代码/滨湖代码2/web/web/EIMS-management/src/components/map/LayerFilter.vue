<template>
  <div class="LayerFilter-box">
     <div class="button" v-for="(item, index) in list"
        :key="index" @click="changeShow(item)">
      <i
        class="iconfont"
        :class="[item.icon, item.isActive ? 'active' : '']"
        :title="item.name"
        v-show="item.isShow"
      ></i>
    </div>
    <div class="button" @click="currentExpandChange">
      <i
        class="iconfont"
        :class="
          currentExpand ? 'icon-ditu_yijianshouqi' : 'icon-ditu_yijianzhankai'
        "
      ></i>
    </div>
    <div class="button" @click="resetMap" title="地图还原">
      <i class="iconfont icon-ditu_ditufanhui"></i>
    </div>
  </div>
</template>

<script>
import ZnvMap from '@/components/map/znvMap'
export default {
  data() {
    return {
      list: [
        {
          name: '地图轮廓',
          key: 'districtLayer',
          isActive: true,
          isShow: true,
          icon: 'icon-ditu_ditulunkuo'
        },
        {
          name: '卫星地图',
          key: 'satelliteLayer',
          isActive: false,
          isShow: true,
          icon: 'icon-ditu_weixingmoshi'
        }
      ]
    }
  },
  computed: {
    currentExpand() {
      return (
        this.$store.getters.right_btnVisible &&
        this.$store.getters.left_btnVisible
      )
    }
  },
  mounted() {
  },
  methods: {
    changeShow(item) {
      item.isActive = !item.isActive
      if (item.name === '地图轮廓') {
        if (item.isActive) {
          ZnvMap.showRegionalContour()
        } else {
          ZnvMap.hideRegionalContour()
        }
      } else if (item.name === '河道线') {
        if (item.isActive) {
          ZnvMap.showRiverLine()
        } else {
          ZnvMap.hideRiverLine()
        }
      } else if (item.name === '卫星地图') {
        if (item.isActive) {
          ZnvMap.changeMap('yixiangditu')
        } else {
          ZnvMap.changeMap('ditu')
        }
      }
    },
    resetMap() {
      ZnvMap.showRegionalContour()
      ZnvMap.showAllLayer()
      ZnvMap.showAnimationLayer()
      ZnvMap.closeInfoWin()
      this.list.forEach(item => {
        if (item.name === '地图轮廓') {
          if (!item.isActive) {
            item.isActive = true
            ZnvMap.showRegionalContour()
          }
        } else if (item.name === '卫星地图') {
          if (item.isActive) {
            item.isActive = false
            ZnvMap.changeMap('ditu')
          }
        }
      })
      ZnvMap.gotoMapPoint({
        lng: '118.0851364',
        lat: '36.7680603',
        zoom: 10
      })
      this.$root.eventBus.$emit('resetMap')
      this.$store.dispatch('view/setLeftBtnVisible', true)
      this.$store.dispatch('view/setRightBtnVisible', true)
    },
    currentExpandChange() {
      if (this.currentExpand) {
        this.$store.dispatch('view/setLeftBtnVisible', false)
        this.$store.dispatch('view/setRightBtnVisible', false)
      } else {
        this.$store.dispatch('view/setLeftBtnVisible', true)
        this.$store.dispatch('view/setRightBtnVisible', true)
      }
    }
  }
}
</script>

<style lang="scss">
.LayerFilter-box {
  position: absolute;
  top: 15px;
  right: 330px;
  display: flex;
  flex-direction: column;
  .button {
    margin-top: 12px;
    width: 32px;
    height: 32px;
    background: #1789ff;
    border-radius: 2px;
    text-align: center;
    line-height: 32px;
    cursor: pointer;
    margin-right: 16px;
    .iconfont {
      font-size: 16px;
      color: #ffffff;
    }
    .active {
      color: #ffd929;
    }
  }
}
</style>
