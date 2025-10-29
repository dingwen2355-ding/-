<template>
  <div class="map-3d">
    <iframe :src="src" frameborder="0"></iframe>
  </div>
</template>

<script>
export default {
  data() {
    return {
      src: sysConfig.map3D.src
    }
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
