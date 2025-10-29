<template>
  <div v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '新增工作组' : '修改工作组'"
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
  name: 'AddDocKnowContent',
  props: ['dialogVisible', 'formInfo', 'isCode', 'planId'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    formInfo(v) {
      if (v) {
        this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
        // console.log('this.form', this.form)
      }
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
        groupName: '',
        planNumId: ''
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
        planNumId: ''
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
      if (this.planId) {
        this.form.planNumId = this.planId
      }
      // console.log('this.form', this.form)
      let url = ''
      if (this.form && this.form.id) {
        url = '/gtw/EIMS-management/update-plan-group'
      } else {
        url = '/gtw/EIMS-management/add-plan-group'
      }
      // console.log('参数', this.form)
      Axios.post(url, this.form).then((res) => {
        if (res.data.code == 200) {
          if (this.form && this.form.id) {
            this.$message.success('工作组修改成功！')
          } else {
            this.$message.success('工作组新增成功！')
          }
          this.$emit('success', this.planId)
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
<style scoped>
</style>
