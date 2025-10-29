<template>
  <el-dialog
    :title="'指挥人员'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1931rem"
    class="phone—Dialog default-dialog-class large-screen"
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
    <div class="content">功能建设中...</div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import moment from 'moment'
export default {
  props: ['dialogVisibleFlag', 'infomation'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        // this.formInline = {
        //   title: '',
        //   precinctId: '',
        //   time: '',
        //   type: ''
        // }
        // this.currentPage = 1
        // this.pageSize = 16
        // this.getEventList()
        // this.queryEventTypeList()
      }
    }
  },
  data() {
    return {
      dialogVisible: false,

    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.phone—Dialog {
  ::v-deep .el-dialog {
    width: 2212rem !important;
    height: 1204rem !important;
  }
  ::v-deep .el-form--inline .el-form-item__content {
    line-height: 32rem;
  }
  .content {
    font-size: 100rem;
    text-align: center;
  }
}
</style>
