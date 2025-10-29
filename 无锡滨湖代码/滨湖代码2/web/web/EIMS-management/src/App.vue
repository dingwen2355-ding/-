<template>
  <div id="app">
    <router-view />
    <MapCollectionPoint
      ref="MapCollectionPoint"
      @closeMap="closeMap"
      @getPoint="getPoint"
      :forbid="forbid"
      :mapShow="mapShow"
      :oriGpsInfo="oriGpsInfo"
      :areaSupport="areaSupport"
    ></MapCollectionPoint>
  </div>
</template>
<script>
import MapCollectionPoint from './components/MapCollectionPoint'

export default {
  name: 'App',
  components: { MapCollectionPoint },
  data() {
    return {
      screenWidth: 0,
      screenHeight: 0,
      forbid: false,
      mapShow: false,
      oriGpsInfo: '',
      emitEventName: '',
      areaSupport: false
    }
  },
  created() {},
  mounted() {
    this.$root.eventBus.$on('getGps', (data) => {
      this.mapShow = true
      this.oriGpsInfo = data.oriGpsInfo
      this.emitEventName = data.emitEventName
      this.forbid = data.forbid || false
      this.areaSupport = data.areaSupport || false
    })
  },
  methods: {
    closeMap() {
      this.mapShow = false
    },
    getPoint(data) {
      this.$root.eventBus.$emit('sendGps' + this.emitEventName, data)
      this.mapShow = false
    },
    changeNav: function (val) {
      this.isCollapse = val
    }
  }
}
</script>
<style lang="scss">
html,
body {
  width: 100%;
  height: 100%;
  margin: 0;
  overflow: hidden;
}
#app {
  width: 100%;
  height: 100%;
  background: #e6edf4;
  position: relative;
}
</style>
