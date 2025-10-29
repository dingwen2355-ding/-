<template>
  <div v-if="dialogVisible">
    <el-dialog
      title="预案审核"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form ref="formLabel" :rules="rules" :model="form" label-width="110px">
          <el-form-item label="审核状态" prop="status" v-if="isAuditBack">
            <el-radio v-model="form.status" label="2">通过</el-radio>
            <el-radio v-model="form.status" label="3">退回</el-radio>
          </el-form-item>
          <el-form-item label="备注" prop="des">
            <el-input type="textarea" :rows="6" clearable v-model="form.des" placeholder="请输入审核意见"></el-input>
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
import Axios from '@/utils/request'
export default {
  name: 'audit',
  props: ['dialogVisible', 'formInfo', 'isAuditBack'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
      this.planId = this.formInfo
      // console.log('this.planId', this.planId)
    }
  },
  data() {
    return {
      rules: {
        status: [{ required: true, message: '请选择审核状态', trigger: 'blur' }],
        des: [{ required: true, message: '请输入审核意见', trigger: 'blur' }]
      },
      loading: false,
      form: {
        status: '2',
        des: ''
      },
      planId: ''
    }
  },
  methods: {
    reset() {
      this.loading = false
      this.form = {
        status: '2',
        des: ''
      }
    },
    handleClose() {
      this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '提交保存',
        cancelButtonText: '关闭窗口',
        type: 'warning'
      })
        .then(() => {
          this.handleOk()
        })
        .catch((e) => {
          if (e === 'cancel') {
            this.reset()
            this.$emit('update:dialogVisible', false)
          }
        })
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
      let params = {
        id: this.planId,
        audit: '',
        remark: this.form.des
      }
      if (this.isAuditBack) {
        params.audit = this.form.status
        params.auditId = localStorage.getItem('userId')
      } else {
        params.audit = '3'
        params.auditId = localStorage.getItem('userId')
      }
      // console.log('审核参数：', params)
      let url = '/binhuapis/audit-plan'
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('审核成功')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error('审核失败')
        }
      })
    }
  }
}
</script>
<style scoped>
</style>
