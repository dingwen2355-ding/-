<!--<template>-->
<!--  <div>-->
<!--    <el-dialog-->
<!--      :title="dialogName"-->
<!--      :visible.sync="diaologVisible"-->
<!--      :append-to-body="false"-->
<!--      :modal-append-to-body="false"-->
<!--      :before-close="handleClose"-->
<!--      class="videoDialog"-->
<!--      :modal="false"-->
<!--    >-->
<!--      <div style="width: 100%; height: 750rem">-->
<!--        &lt;!&ndash;        <ZnvVideo type="flv" src="url" :fontSize="46" class="videoHight"></ZnvVideo>&ndash;&gt;-->
<!--        &lt;!&ndash;        <ZnvVideo v-id="id" type="mp4" :src="url" :fontSize="46" class="videoHight"></ZnvVideo>&ndash;&gt;-->
<!--        <znv-video type="flv" :src="url" v-id="id" :fontSize="46" class="videoHight"></znv-video>-->
<!--      </div>-->
<!--    </el-dialog>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import ZnvVideo from '@/components/znvVideo/Video.vue'-->

<!--export default {-->
<!--  name: 'videoDIalog',-->
<!--  components: { ZnvVideo },-->
<!--  props: ['diaologVisible', 'dialogName'],-->
<!--  data() {-->
<!--    return {-->
<!--      // url: 'http://172.16.7.50:8070/live/32010200001310000007/0.flv?A=8139755d02e680147f8837c9f57128e4',-->
<!--      url: require('https://124.71.204.212:443/hls/bhcyzbyx3538.flv'),-->
<!--      // url: require('../../../../../public/videoAssets/固定视频0.mp4'),-->
<!--      id: 'wurenji'-->
<!--    }-->
<!--  },-->
<!--  watch: {-->
<!--    diaologVisible(v) {-->
<!--      if (v) {-->
<!--        let video = document.querySelector('video')-->
<!--        video.play()-->
<!--      }-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    handleClose() {-->
<!--      this.$emit('update:diaologVisible', false)-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style lang="scss">-->
<!--.videoDialog {-->
<!--  position: absolute;-->
<!--  top: -380rem;-->
<!--  width: 3320rem;-->
<!--  right: 3700rem;-->
<!--  bottom: -900rem;-->
<!--  left: -3200rem !important;-->
<!--  .el-dialog__header {-->
<!--    background-color: #000000;-->
<!--  }-->
<!--}-->
<!--.videoHight {-->
<!--  width: 100%;-->
<!--  height: 750rem;-->
<!--}-->
<!--.el-dialog__title {-->
<!--  font-size: 50rem;-->
<!--}-->

<!--.el-dialog__body {-->
<!--  padding: inherit;-->
<!--  width: 100%;-->
<!--  color: #606266;-->
<!--  font-size: 14rem;-->
<!--  word-break: break-all;-->
<!--}-->
<!--.el-dialog__headerbtn .el-dialog__close {-->
<!--  color: #fff;-->
<!--  font-size: 60rem;-->
<!--}-->
<!--</style>-->
