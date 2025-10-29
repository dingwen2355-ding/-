<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="dealDialog"
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
          <el-form-item label="处置反馈" prop="dealInfo">
            <el-input type="textarea" :rows="2" v-model="formData1.dealInfo" style="width: 400px"></el-input>
          </el-form-item>

          <el-form-item label="完成时间" prop="dealTime">
            <el-date-picker
              v-model="formData1.dealTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 400px"
            ></el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div class="files">
        <span>处置附件</span>
        <AttachmentUpload @success="siteImg" :title="title" />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import AttachmentUpload from '../../components/attachmentUpload.vue'
import moment from 'moment'
import axios from '@/utils/request'
export default {
  name: 'dealDialog',
  props: ['dealDialog', 'dealDialogData'],
  components: { AttachmentUpload },
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      title: '处置上报',
      active: '1',
      formData1: {
        dealInfo: '已按要求安全且合理合规处置',
        dealTime: moment().format('yyyy-MM-DD HH:mm:ss'),
        files: []
      },
      rules1: {
        dealInfo: [{ required: true, message: '请填写处置反馈', trigger: 'blur' }],
        dealTime: [{ required: true, message: '请选择处置完成时间', trigger: 'blur' }]
      }
    }
  },
  watch: {
    dealDialog(v) {
      this.dialogVisible = v
      if (v) {
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
      }
    },
    dealDialogData(v) {
      this.tableData = JSON.parse(JSON.stringify(v))
    }
  },
  methods: {
    // 确定按钮
    handleOk() {
      this.$refs['formData1'].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('eventId', this.tableData.eventId)
          formData.append('dealInfo', this.formData1.dealInfo)
          formData.append('dealTime', this.formData1.dealTime)
          formData.append('taskId', this.tableData.taskId)
          for (var i = 0; i < this.formData1.files.length; i++) {
            formData.append('files', this.formData1.files[i].raw)
          }
          let url = '/binhuapis/event/handle'
          axios.post(url, formData).then((r) => {
            if (r.data.code === 200) {
              this.$notify({
                title: '成功',
                message: '处置成功',
                type: 'success',
                duration: 1000
              })
              this.$emit('success')
              this.handleClose()
            } else {
              this.$notify({
                title: '警告',
                message: '处置失败，' + r.data.message,
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
        dealInfo: '已按要求安全且合理合规处置',
        dealTime: moment().format('yyyy-MM-DD HH:mm:ss'),
        files: []
      }
      this.$emit('update:dealDialogData', {})
      this.$emit('update:dealDialog', false)
      this.$emit('close')
    },
    siteImg(r) {
      this.formData1.files = r
    }
  }
}
</script>
<style lang="scss" scoped>
.dealDialog {
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
