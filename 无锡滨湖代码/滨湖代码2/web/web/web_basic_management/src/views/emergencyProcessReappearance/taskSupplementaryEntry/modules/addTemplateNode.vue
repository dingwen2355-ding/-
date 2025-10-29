<template>
  <div class="addTemplateNode">
    <el-dialog
      :title="form.id && form.id!=''?'修改':'新增'"
      :visible.sync="dialogVisible"
      width="700px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form :inline="true" ref="formLabel" :rules="rules" :model="form" label-width="80px">
          <el-form-item label="节点名称" prop="nodeName">
            <el-input v-model="form.nodeName" placeholder="请输入节点名称"></el-input>
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
import request from '@/utils/request'
export default {
  name: 'addTemplateNode',
  props: ['dialogVisible', 'formInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        console.log('新增编辑查看this.form', this.form)
      }
    }
  },
  data() {
    return {
      rules: {
        nodeName: [{ required: true, message: '请填写节点名称', trigger: 'blur' }]
      },
      loading: false,
      form: {
        id: '',
        nodeName: ''
      },
      nodeList: []
    }
  },
  mounted() {
  },
  methods: {
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
    reset() {
      this.loading = false
      this.form = {
        id: '',
        nodeName: ''
      }
      // 清空表单校验，避免再次进来会出现上次校验的记录
      this.$refs.formLabel.clearValidate()
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          return false
        }
      })
    },
    submitAll() {
      if (this.form.id && this.form.id !== '') {
        this.editTemplateNode()
      } else {
        this.addTemplateNode()
      }
    },
    editTemplateNode() {
      this.loading = true
      let params = this.form
      let url = '/binhuapis/dapeng/eventNode/edit'
      request.post(url, params).then(res => {
        this.loading = false
        this.$parent.getEventList()
        if (res.data.code === 200) {
          this.$message.success('编辑成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    addTemplateNode() {
      this.loading = true
      let params = this.form
      let url = '/binhuapis/dapeng/eventNode/add'
      request.post(url, params).then(res => {
        this.loading = false
        this.$parent.getEventList()
        if (res.data.code === 200) {
          this.$message.success('新增成功！')
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(res.data.message)
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.addTemplateNode {
}
</style>
