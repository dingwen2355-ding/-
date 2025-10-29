<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="scrapDia"
      :title="title"
      :visible.sync="dialogVisible"
      width="700px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-form
          :inline="true"
          ref="formData1"
          :rules="rules1"
          class="storage-form"
          :model="formData1"
          label-width="110px"
          style="margin-top: 10px"
        >
          <el-form-item label="报废事由" prop="distributeInfo">
            <el-input type="textarea" :rows="2" v-model="formData1.distributeInfo" style="width: 340px"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'receiveDialog',
  props: ['receiveDialog', 'receiveDialogData'],
  components: {},
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      title: '接收信息',
      formData1: {
        eventIds: '',
        distributeInfo: ''
      },
      rules1: {},
      receive: ''
    }
  },
  watch: {
    receiveDialog(v) {
      this.dialogVisible = v
      if (v) {
        this.formData1.eventIds = JSON.parse(JSON.stringify(this.receiveDialogData)).eventId
      }
    }
  },
  methods: {
    // 确定按钮
    handleOk() {
      let url1 = '/binhuapis/event/dump'
      let params1 = {
        taskIds: this.receiveDialogData.taskId,
        eventIds: this.formData1.eventIds,
        distributeInfo: this.formData1.distributeInfo
      }
      axios.post(url1, qs.stringify(params1)).then((r) => {
        if (r.data.code === 200) {
          this.$emit('success')
          this.handleClose()
        } else {
          this.$notify({
            title: '警告',
            message: r.data.message,
            type: 'warning'
          })
        }
      })
    },
    handleClose() {
      this.formData1 = {
        eventIds: '',
        distributeInfo: ''
      }
      this.$emit('update:receiveDialogData', {})
      this.$emit('update:receiveDialog', false)
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss" scoped>
.scrapDia {
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 150px;
    display: flex;
    .leftCard {
      width: 10%;
      height: 100%;
      .steps {
        height: 500px;
      }
    }
    .rightCard {
      overflow: auto;
      overflow-x: hidden;
      width: 97%;
      height: 100%;
      margin-left: 10px;
    }
  }
}
</style>
