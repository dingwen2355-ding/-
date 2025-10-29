<template>
  <div v-if="dialogVisible">
    <el-dialog
      title="新增关联系统"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form ref="formLabel" :rules="rules" :model="form" label-width="110px" style="margin-right: 35px">
          <el-form-item label="系统名称" prop="systemName">
            <el-input clearable v-model="form.systemName" placeholder="请输入名称" style="width: 610px"></el-input>
          </el-form-item>
          <el-form-item label="管理单位" prop="systemUnit">
            <el-input clearable v-model="form.systemUnit" placeholder="请输入管理单位" style="width: 610px"></el-input>
          </el-form-item>
          <el-form-item label="网络环境" prop="systemEnvironment">
            <el-input
              clearable
              v-model="form.systemEnvironment"
              placeholder="请输入网络环境"
              style="width: 610px"
            ></el-input>
          </el-form-item>
          <el-form-item label="网址" prop="systemUrl">
            <el-input clearable v-model="form.systemUrl" placeholder="请输入网址" style="width: 610px"></el-input>
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
import Axios from 'axios'
export default {
  name: 'addSystemDialog',
  props: ['dialogVisible', 'formInfo', 'planId', 'planPreId'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
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
        systemName: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        systemUnit: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        systemEnvironment: [{ required: true, message: '请输入名称', trigger: 'blur' }]
        // systemUrl: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      },
      form: {
        planNumId: '',
        systemName: '',
        systemUnit: '',
        systemEnvironment: '',
        systemUrl: ''
      },
      loading: false
    }
  },
  methods: {
    reset() {
      this.loading = false
      this.form = {
        planNumId: '',
        systemName: '',
        systemUnit: '',
        systemEnvironment: '',
        systemUrl: ''
      }
    },
    // 取 消
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
    // 确 定
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
      // console.log('this.form.planNumId', this.planId)
      let params = { planNumId: this.planId }
      if (this.planId) {
        this.form.planNumId = this.planId
      }
      // console.log('this.form', this.form)
      let url = ''
      if (this.planPreId) {
        url = '/gtw/EIMS-management/update-plan-num-pre'
        params.id = this.planPreId
        params.addPlanNumSystemBeans = []
        params.addPlanNumSystemBeans.push(this.form)
      } else {
        url = '/gtw/EIMS-management/add-plan-num-pre'
        params.planNumSystemBeans = []
        params.planNumSystemBeans.push(this.form)
      }
      // console.log('参数', params)
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          //   if (this.planPreId) {
          //     this.$message.success('修改成功！')
          //   } else {
          this.$message.success('新增关联系统成功！')
          //   }
          this.$emit('success', this.planId)
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          //   if (this.planPreId) {
          //     this.$message.success('修改失败！')
          //   } else {
          this.$message.success('新增关联系统失败！')
          //   }
        }
      })
    }
  }
}
</script>
<style scoped>
</style>
