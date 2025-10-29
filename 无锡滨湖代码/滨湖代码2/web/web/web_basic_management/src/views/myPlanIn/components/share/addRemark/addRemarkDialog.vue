<template>
  <div class="add-remark-dialog" v-if="dialogVisible">
    <el-dialog
      title="新增反馈信息"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-form :inline="true" :rules="rules" :model="form" ref="formLabel" label-width="110px">
          <el-form-item label="反馈内容" prop="remarkContent" class="whole-line">
            <el-input
              type="textarea"
              :rows="6"
              clearable
              v-model="form.remarkContent"
              placeholder="请输入反馈内容"
            ></el-input>
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
  name: 'addRemarkDialog',
  props: ['dialogVisible', 'planId'],
  watch: {
    dialogVisible(v) {
      console.log(v)
      this.dialogVisible = v
      this.belongPlanId = this.planId
      console.log('this.belongPlanId', this.belongPlanId)
    }
  },
  data() {
    return {
      rules: {
        remarkContent: [{ required: true, message: '请输入反馈内容', trigger: 'blur' }]
      },
      form: {
        remarkContent: ''
      },
      belongPlanId: ''
    }
  },
  methods: {
    reset() {
      this.form = {
        remarkContent: ''
      }
    },
    handleClose() {
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      let params = {
        planNumId: this.belongPlanId, // 预案id
        content: this.form.remarkContent, // 评论内容
        operatorId: localStorage.getItem('userId') // 当前评论用户id
      }
      console.log('提交反馈参数：', params)
      Axios.post('/binhuapis/add-plan-comment', params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('反馈成功')
          this.$emit('addSuccess', this.belongPlanId)
          this.$emit('update:dialogVisible', false)
          this.reset()
        } else {
          this.$message.error('反馈失败')
        }
      })
    }
  }
}
</script>
<style>
</style>
