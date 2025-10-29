<template>
  <div v-if="dialogVisible" class="revocationDialog">
    <el-dialog
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
          :disabled="title === '详情'"
        >
          <el-form-item label="备注" prop="dealInfo">
            <el-input type="textarea" :rows="2" v-model="formData1.dealInfo" style="width: 400px"></el-input>
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
// import qs from 'qs'
export default {
  name: 'revocationDialog',
  props: ['revocationDialog', 'revocationDialogData'],
  components: {},
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      title: '撤销',
      active: '1',
      formData1: {
        dealInfo: ''
      },
      rules1: {
        dealInfo: [{ required: true, message: '请填写备注', trigger: 'blur' }]
      }
    }
  },
  watch: {
    revocationDialog(v) {
      this.dialogVisible = v
      if (v) {
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
      }
    },
    revocationDialogData(v) {
      this.tableData = JSON.parse(JSON.stringify(v))
    }
  },
  methods: {
    // 确定按钮
    handleOk() {
      this.$refs['formData1'].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('eventIds', this.tableData.eventId)
          formData.append('dealInfo', this.formData1.dealInfo)
          formData.append('taskId', this.tableData.taskId)
          let url = '/binhuapis/event/cancel'
          axios.post(url, formData).then((r) => {
            if (r.data.code === 200) {
              this.$notify({
                title: '成功',
                message: '撤销成功',
                type: 'success',
                duration: 1000
              })
              this.$emit('success')
              this.handleClose()
            } else {
              this.$notify({
                title: '警告',
                message: '撤销失败，' + r.data.message,
                type: 'warning',
                duration: 1000
              })
            }
          })
          // let params = {
          //   eventId: this.tableData.eventId,
          //   dealInfo: this.formData1.dealInfo,
          //   dealTime: this.formData1.dealTime,
          //   files: this.formData1.files
          // }
        } else {
          this.$notify({
            title: '警告',
            message: '检查表单输入内容的合法性！',
            type: 'warning',
            duration: 1000
          })
          // console.log('error submit!!')
          return false
        }
      })
    },
    handleClose() {
      this.formData1 = {
        dealInfo: ''
      }
      this.$emit('update:revocationDialogData', {})
      this.$emit('update:revocationDialog', false)
      this.$emit('close')
    },
    siteImg(r) {
      this.formData1.files = r
    }
  }
}
</script>
<style lang="scss" scoped>
.revocationDialog {
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 150px;
    display: flex;
  }
  .files {
    margin-left: 40px;
  }
}
</style>
