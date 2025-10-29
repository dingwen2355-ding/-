<template>
  <el-dialog
    :title="'视频管理'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="6484rem"
    height="4133rem"
    class="video-manage—dialog default-dialog-class large-screen"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div>
      <video-manage-panel></video-manage-panel>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import moment from 'moment'
import videoManagePanel from "./videoManagePanel";
export default {
  components: {videoManagePanel},
  props: ['dialogVisibleFlag', 'infomation'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
      }
    }
  },
  data() {
    return {
      dialogVisible: false
    }
  },
  methods: {
    onSubmit() {
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.video-manage—dialog {
  ::v-deep .el-dialog{
    width: 6484rem !important;
    height: 3333rem !important;
  }
  ::v-deep .el-button{
    background: transparent !important;
  }
  .historyList{
    margin-bottom: 64rem;
  }
  .footer{
    height: 272rem;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
::v-deep .el-tree__empty-block {
  background-color: #1d3959 !important;
}
</style>
