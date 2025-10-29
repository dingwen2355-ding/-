<template>
  <div class="map-3d">
    <iframe :src="src" frameborder="0"></iframe>
  </div>
</template>

<script>
import ZnvMap from '@/components/map/znvMap'
export default {
  data() {
    return {
      src: sysConfig.map3D.src + this.$store.getters.eventId.id
    }
  },
  mounted() {
    window.top.addEventListener(
      'message',
      (e) => {
        // console.log(e)
        let marker = ZnvMap.getMarkersById(e.data)
        ZnvMap.showPointDetail(marker.opts)
      },
      false
    )
  }
}
</script>

<style lang="scss">
.map-3d {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
  flex-grow: 1;
  iframe {
    width: 100%;
    height: 100%;
  }
}
</style>
