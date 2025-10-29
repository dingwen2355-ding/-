<template>
  <div class="addWorkGroupDialog" v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '新增通讯录组' : '修改通讯录组'"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form ref="formLabel" :rules="rules" :model="form" label-width="110px">
          <el-form-item label="工作组名称" prop="groupName">
            <el-input clearable v-model="form.groupName" placeholder="请输入工作组名称" style="width: 630px"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
export default {
  name: 'addWorkGroupDialog',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      // console.log('this.form', this.form)
    }
  },
  data() {
    return {
      rules: {
        groupName: [{ required: true, message: '请输入工作组名称', trigger: 'blur' }]
      },
      loading: false,
      form: {
        id: '',
        precinctId: '',
        groupName: '',
        userId: ''
      }
    }
  },
  methods: {
    handleClose() {
      this.$refs.formLabel.resetFields()
      this.$emit('update:dialogVisible', false)
      this.reset()
    },
    reset() {
      this.loading = false
      this.form = {
        id: '',
        groupName: '',
        precinctId: '',
        userId: ''
      }
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      // console.log('this.form', this.form)
      let url = ''
      let params = {
        groupName: this.form.groupName
      }
      if (this.form && this.form.id) {
        url = '/gtw/EIMS-management/update-mes-group'
        params.id = this.form.id
      } else {
        url = '/gtw/EIMS-management/add-mes-group'
        params.userId = this.form.userId
      }
      // console.log('参数', params, url)
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          if (this.form && this.form.id) {
            this.$message.success('工作组修改成功！')
          } else {
            this.$message.success('工作组新增成功！')
          }
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          if (this.form && this.form.id) {
            this.$message.success('工作组修改失败！')
          } else {
            this.$message.success('工作组新增失败！')
          }
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.addWorkGroupDialog {
  .el-dialog__body {
    height: 200px;
  }
}
</style>
