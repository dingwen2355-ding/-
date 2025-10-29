<template>
  <div class="Center">
    <div class="center-body">
      <MapView v-show="mapType === '2D'"/>
      <Map3D v-show="mapType === '3D'"/>
      <Bottom @changeMapType="handleChangeMapType" />
      <VideoDialog />
      <FusionVideo v-if="$store.getters.fusionVideo"></FusionVideo>
    </div>
  </div>
</template>

<script>
import Bottom from './center/bottom'
import MapView from '@/components/map/MapView'
import Map3D from './map/Map3D'
// import CenterTools from './center/centerTools'
import VideoDialog from '@/components/map/mapDialog/VideoDialog'
import FusionVideo from '@/views/startMenu/modules/dialog/FusionVideo.vue'
export default {
  components: {
    MapView,
    Bottom,
    Map3D,
    VideoDialog,
    FusionVideo
  },
  data() {
    return {
      mapType: '2D'
    }
  },
  methods: {
    handleChangeMapType(v) {
      this.mapType = v
    }
  }
}
</script>

<style lang="scss">
.Center {
  position: absolute;
  position: absolute;
  top: 0;
  left: 2360rem; //原值为4260
  width: 10985rem; //原值为7185
  height: 100%;

  .center-body {
    height: 100%;
  }
}
</style>
