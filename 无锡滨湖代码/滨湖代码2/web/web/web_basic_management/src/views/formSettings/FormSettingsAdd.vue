<template>
  <el-dialog
    :title="isCode === 0 ? '新增' : isCode === 1 ? '编辑' : '查看'"
    :visible.sync="dialogVisible"
    width="600px"
    :modal-append-to-body="false"
    :append-to-body="true"
    :before-close="handleClose"
    class="NoticeAnnouncementAdd"
  >
    <div class="info_body">
      <el-form :inline="true" ref="form" :rules="rules" :model="form" label-width="110px">
        <el-form-item label="系统名称" prop="name">
          <el-input clearable v-model="form.name" placeholder="请输入系统名称" :disabled="isCode === 2"></el-input>
        </el-form-item>
        <el-form-item label="系统地址" prop="address">
          <el-input clearable v-model="form.address" placeholder="请输入系统地址" :disabled="isCode === 2"></el-input>
        </el-form-item>
        <el-form-item label="参数键名值">
          <el-input clearable v-model="form.params" placeholder="请输入参数键名" :disabled="isCode === 2"></el-input>
        </el-form-item>
        <el-form-item label="系统来源" prop="source">
          <el-input clearable v-model="form.source" placeholder="请输入系统来源" :disabled="isCode === 2"></el-input>
        </el-form-item>
        <el-form-item label="图片地址" prop="image">
          <el-input clearable v-model="form.image" placeholder="请输入图片地址" :disabled="isCode === 2"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input clearable v-model="form.remark" placeholder="请输入备注" :disabled="isCode === 2"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose()">取 消</el-button>
      <el-button type="primary" @click="handleOk" v-if="isCode !== 2">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'DangerTroubleshootingChecklistAdd',
  components: {},
  props: ['dialogVisible', 'formInfo', 'isCode'],
  data() {
    return {
      form: {
        name: '',
        address: '',
        params: '',
        source: '',
        image: '',
        remark: '',
        id: ''
      },
      rules: {
        name: [{ required: true, message: '请输入', trigger: 'blur' }]
      }
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        if (this.formInfo) {
          let obj = JSON.parse(JSON.stringify(this.formInfo))
          this.form.name = obj.name
          this.form.address = obj.address
          this.form.params = obj.params
          this.form.source = obj.source
          this.form.image = obj.image
          this.form.remark = obj.remark
          this.form.id = obj.id
        }
      }
    }
  },
  methods: {
    handleClose(done) {
      this.reset()
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.form = {
        name: '',
        address: '',
        params: '',
        source: '',
        image: '',
        remark: '',
        id: ''
      }
    },
    handleOk() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let params = {
            name: this.form.name,
            address: this.form.address,
            params: this.form.params,
            source: this.form.source,
            image: this.form.image,
            remark: this.form.remark
          }

          let url = ''
          if (this.formInfo) {
            params.id = this.form.id
            url = '/auth/third-platform/update'
            axios.put(url, params).then((r) => {
              if (r.data.code === 200) {
                this.$notify({
                  title: '成功',
                  message: '操作成功',
                  type: 'success',
                  duration: 1000
                })
                this.$emit('success')
                this.reset()
                this.$emit('update:dialogVisible', false)
              } else {
                this.$notify.error({
                  title: '错误',
                  message: r.data.message,
                  duration: 1000
                })
              }
            })
          } else {
            url = '/auth/third-platform/add'
            axios.post(url, params).then((r) => {
              if (r.data.code === 200) {
                this.$notify({
                  title: '成功',
                  message: '操作成功',
                  type: 'success',
                  duration: 1000
                })
                this.$emit('success')
                this.reset()
                this.$emit('update:dialogVisible', false)
              } else {
                this.$notify.error({
                  title: '错误',
                  message: r.data.message,
                  duration: 1000
                })
              }
            })
          }
        } else {
          this.$notify({
            title: '警告',
            message: '请检查表单的数据合法性！！',
            type: 'warning',
            duration: 1000
          })
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
.NoticeAnnouncementAdd {
  .el-form--inline {
    .el-select {
      display: block;
    }
    .el-form-item__content {
      width: 400px;
    }
    .whole-line {
      width: 100%;

      .el-form-item__content {
        width: 585px;
      }
    }
  }
}
</style>
