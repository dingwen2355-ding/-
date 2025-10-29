<template>
  <div v-if="dialogVisible">
    <el-dialog
      title="编辑科室"
      :visible.sync="dialogVisible"
      width="500px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-form ref="formLabel" :rules="rules" :model="form" label-width="110px">
          <el-form-item prop="precinctName" label="责任科室">
            <el-cascader
              ref="cascader"
              :options="options"
              :props="props"
              placeholder="请选择责任科室"
              clearable
              collapse-tags
              v-model="form.precinctName"
              :show-all-levels="false"
            ></el-cascader>
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
  props: ['showEditedDialog', 'planId'],
  watch: {
    showEditedDialog(v) {
      // console.log(v)
      this.dialogVisible = v
      // console.log('this.planId', this.planId)
    }
  },
  data() {
    return {
      dialogVisible: false,
      rules: {
        precinctName: [{ required: true, message: '请选择责任科室', trigger: 'blur' }]
      },
      form: {},
      props: {
        emitPath: false,
        label: 'precinctName',
        value: 'precinctId',
        children: 'precinctSon'
      },
      options: []
    }
  },
  mounted() {
    this.getPrecinctTree()
  },
  methods: {
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].precinctSon.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].precinctSon = undefined
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].precinctSon)
        }
      }
      return data
    },
    getPrecinctTree() {
      let url = `/binhuapis/getCommitteeVilageTreeNew`
      Axios.get(url).then(res => {
        let datas = this.getTreeData(res.data.data)
        this.options = JSON.parse(JSON.stringify(datas))
      })
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
            this.form = {}
            this.$emit('update:showEditedDialog', false)
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
      let params = {
        id: this.planId
      }
      console.log(this.form.precinctName)
      if (this.form.precinctName) {
        params.precinctId = this.form.precinctName
      }
      // console.log('审核参数：', params)
      let url = '/binhuapis/update-plan-pre'
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('编辑成功')
          this.$emit('success')
          this.$emit('update:showEditedDialog', false)
          this.form = {}
        } else {
          this.$message.error('编辑失败')
        }
      })
    }
  }
}
</script>
<style scoped>
</style>
