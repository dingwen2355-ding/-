<template>
  <el-dialog :title="markerInfo.deviceName" :visible.sync="markerInfo.dialogVisible" width="40%"
    :before-close="handleClose" :modal-append-to-body="true" :append-to-body="true" class="VideoDialog">
    <div class="container" v-if="dialogVisible">
      <znv-video :src="markerInfo.url"  :type="type"></znv-video>
    </div>
  </el-dialog>
</template>

<script>
import ZnvVideo from '../../components/znvVideo/Video.vue'
export default {
  data () {
    return {
      dialogVisible: false,
      videoInfo: {},
      type: 'hls'
    }
  },
  props: ['markerInfo'],
  components: {
    ZnvVideo
  },
  watch: {
    markerInfo (v) {
      if (v.dialogVisible) {
        this.dialogVisible = true
      } else {
      }
    }
  },
  methods: {

    handleClose () {
      this.dialogVisible = false
      this.$root.eventBus.$emit('upDataVideoDialog', false)
    }
  }
}
</script>

<style lang="scss">
.VideoDialog {
  .el-dialog__body {
    padding-top: 0px;
  }
  .info-list {
    width: 100%;
    font-size: 16px;
    overflow: auto;
    display: flex;
    flex-wrap: wrap;
    line-height: 2;
    padding-bottom: 20px;
    li {
      display: flex;
      width: 50%;
      align-items: flex-start;
      .iconfont {
        color: #00efff;
        margin-right: 7px;
      }
      .key {
        min-width: 80px;
        flex-shrink: 0;
        color: #00efff;
        padding-right: 5px;
      }
      .value {
        color: #fff;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }
  .container {
    width: 100%;
    height: 400px;
  }
}
</style>
