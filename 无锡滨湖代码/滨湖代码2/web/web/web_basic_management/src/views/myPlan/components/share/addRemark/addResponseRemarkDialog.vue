<template>
  <div class="add-response-remark-dialog" v-if="dialogVisible">
    <el-dialog
      :title="isCode == 1 ? '新增回复反馈信息' : '新增回复信息'"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-form :inline="true" :rules="rules" :model="form" ref="formLabel" label-width="110px">
          <el-form-item label="回复内容" prop="responseContent" class="whole-line">
            <el-input
              type="textarea"
              :rows="6"
              clearable
              v-model="form.responseContent"
              placeholder="请输入回复内容"
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
  name: 'addResponseRemarkDialog',
  props: ['dialogVisible', 'planId', 'remarkId', 'responseId', 'flag', 'isCode'],
  watch: {
    dialogVisible(v) {
      console.log(v)
      this.dialogVisible = v
      this.belongPlanId = this.planId
    }
  },
  data() {
    return {
      rules: {
        responseContent: [{ required: true, message: '请输入反馈内容', trigger: 'blur' }]
      },
      form: {
        responseContent: ''
      },
      belongPlanId: ''
    }
  },
  methods: {
    reset() {
      this.form = {
        responseContent: ''
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
        respContent: this.form.responseContent, // 评论内容
        operatorId: localStorage.getItem('userId'), // 当前回复人id
        recipientId: this.responseId, // 当前被回复人id
        commentsId: this.remarkId, // 评论ID
        respType: '' // 回复类型  1： 回复预案评论  2： 回复评论的回复
      }
      // flag: '', // 标志 若为1 则respType= '1'; 若为2 respType= '2'
      if (this.flag == 1) {
        params.respType = '1'
      } else if (this.flag == 2) {
        params.respType = '2'
      }
      console.log('提交反馈参数：', params)
      Axios.post('/binhuapis/add-plan-comment_resp', params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('回复成功')
          this.$emit('addSuccess', this.remarkId)
          this.$emit('update:dialogVisible', false)
          this.reset()
        } else {
          this.$message.error('回复失败')
        }
      })
    }
  }
}
</script>
<style>
</style>
