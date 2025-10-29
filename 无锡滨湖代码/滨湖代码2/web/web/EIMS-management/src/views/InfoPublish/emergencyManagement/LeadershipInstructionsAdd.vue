<template>
  <el-dialog
    title="新增领导批示"
    :visible.sync="dialogVisible"
    width="720px"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose">
    <el-form :inline="true" ref='form' :rules="rules" :model="form" class="LeadershipInstructionsAdd" label-width="100px">
      <el-form-item label="指示时间" prop="instructionsTime">
        <el-date-picker
          v-model="form.instructionsTime"
          type="datetime"
          value-format='yyyy-MM-dd HH:mm:ss'
          placeholder="选择指示时间"
          clearable>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="指示内容" prop="content" class="whole-line">
        <el-input clearable v-model="form.content" type="textarea" :rows="2" placeholder="请输入指示内容"></el-input>
      </el-form-item>
      <el-form-item label="指示文件" prop="attachment" class="whole-line">
        <el-upload ref="upload" action="/" :multiple="false" :file-list="fileList" :show-file-list="true" :on-change="handleChange" :on-remove="handleRemove" :auto-upload="false" >
          <el-button slot="trigger" size="small" type="primary" :disabled="!!fileList.length">上传文档</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
// 新增领导批示
import axios from "axios";
export default {
  name: "LeadershipInstructionsAdd",
  components: {},
  data() {
    return {
      form: {
        eventId: '',
        content: '',
        instructionsTime: ''
      },
      rules: {
        instructionsTime: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入后提交', trigger: 'blur'}
        ]
      },
      fileList: [],
      file: null
    }
  },
  props: ['dialogVisible', 'rowInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    rowInfo(v) {
      if (v) this.form.eventId = v.id
    }
  },
  mounted() {
    if (this.rowInfo) this.form.eventId = this.rowInfo.id
  },
  methods: {
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
    },
    handleRemove(file, fileList) {
      this.file = file
      this.fileList = fileList
    },
    handleClose(done) {
      this.reset()
      this.$emit("update:dialogVisible", false)
    },
    reset() {
      this.$refs.form.resetFields()
      this.form = {
        eventId: '',
        content: '',
        instructionsTime: ''
      }
      this.$refs.upload.clearFiles()
      this.file = null
      this.fileList = []
    },
    handleOk() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let formData = new FormData()
          formData.append('data', JSON.stringify(this.form))
          if (this.file && this.fileList.length) {
            this.file = this.fileList[0]
            // this.$refs.upload.clearFiles()
            const types = this.file.name.split('.')[1]
            const fileType = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw', 'csv', 'doc', 'docx'].some(item => item === types)
            if (!fileType) {
              this.$message('格式错误！请重新选择')
              return
            }
            formData.append('file', this.file.raw)
          }
          let url = '/gtw/EIMS-management/event/saveEventInstructions'
          axios.post(url, formData).then(r => {
            if (r.data.code === 200 || r.data.code === 201) {
              this.$message.success('操作成功')
              this.$emit('success')
              this.handleClose()
            } else {
              this.$message.error(r.data.message)
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style lang="scss">
.LeadershipInstructionsAdd.el-form--inline {
  .el-select {
    display: block;
  }
  .el-form-item__content {
    width: 225px;
  }

  .el-date-editor {
    width: 225px;
  }

  .whole-line {
    width: 100%;

    .el-form-item__content {
      width: 564px;
    }
  }

}
</style>
