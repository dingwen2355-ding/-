<template>
  <van-popup v-model="popupVisible" position="bottom" class="choose-location-popup" @close="closePopup">
    <div class="van-picker__toolbar">
      <button type="button" class="van-picker__cancel" @click="closePopup">取消</button>
      <button type="button" class="van-picker__confirm" @click="saveLocation">确认</button>
    </div>
    <div class="map-container" :id="mapId"></div>
  </van-popup>
</template>

<script>
import ZnvMap from '../common/js/znvMap'

export default {
  name: 'ChooseLocationPopup',
  props: {
    visible: Boolean,
    mapId: {
      default: 'mapContainer'
    },
    location: {
      type: Array
    }
  },
  data() {
    return {
      isInited: false,
      popupVisible: this.visible,
      currentLocation: null
    }
  },
  watch: {
    visible: {
      immediate: true,
      handler(visible) {
        this.popupVisible = visible
        if (visible && !this.isInited) {
          this.initMap()
        } else if (visible && this.location && this.location.length === 2) {
          this.setLocationMarker(this.location)
          ZnvMap.centerTo(this.location, 16)
        } else if (!visible) {
          ZnvMap.removeEventPointLayer()
          this.currentLocation = null
        }
      }
    },
    '$store.getters.mapClickGps'(gps) {
      this.setLocationMarker(gps)
    }
  },
  methods: {
    closePopup() {
      this.$emit('update:visible', false)
      this.$emit('close')
    },
    initMap() {
      // 初始化地图
      ZnvMap.start(this.mapId, {
        // 120.30431371973,31.590077071236
        center: {
          lng: 120.30242008,
          lat: 31.59091392
        },
        resizeEnable: true,
        zoom: 16,
        minZoom: 10,
        maxZoom: 18
      }).then(() => {
        this.isInited = true
        if (this.location && this.location.length === 2) {
          this.setLocationMarker(this.location)
          ZnvMap.centerTo(this.location, 16)
        }
      })
    },
    setLocationMarker(pointGps) {
      ZnvMap.removeEventPointLayer()
      ZnvMap.loadEventPointLayer({
        id: 'currentLocation',
        title: '当前点位',
        icon: require('../assets/map/location.png'),
        position: { lng: pointGps[0], lat: pointGps[1] },
        size: { width: 32, height: 32 },
        offset: { x: -16, y: -16 }
      })
      this.currentLocation = pointGps
    },
    saveLocation() {
      if (!this.currentLocation) {
        this.$notify({
          type: 'warning',
          message: '请先在地图中选择位置'
        })
        return
      }
      this.$emit('change', this.currentLocation)
      this.closePopup()
    }
  }
}
</script>

<style lang="scss" scoped>
.choose-location-popup {
  width: 100%;
  height: 70%;
}
.map-container {
  width: 100%;
  height: calc(100% - 80rem);
}
</style>
