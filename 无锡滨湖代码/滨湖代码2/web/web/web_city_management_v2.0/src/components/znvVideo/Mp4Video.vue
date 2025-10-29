<template>
  <div class="box-video">
    <video :id="vId" class="znv-video" muted autoplay controls :src="src"></video>
  </div>
</template>

<script>
export default {
  name: 'FlvVideo',
  data() {
    return {}
  },
  props: {
    vId: {
      type: String,
      required: true,
      default: ''
    },
    src: {
      type: String,
      required: true,
      default: ''
    }
  }
}
</script>

<style>
@import url(./viedo.css);
</style>
