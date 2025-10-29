<template>
  <j-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" v-bind="layout" :rules="validatorRules">
        <a-form-model-item label="预案名称" required prop="title">
          <a-input placeholder="请输入预案名称" v-model="model.title" />
        </a-form-model-item>
        <a-form-model-item label="详细预案" required prop="content">
          <a-input placeholder="请输入预案内容" type="textarea" v-model="model.content" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction } from '@/api/manage'

export default {
  name: 'PrePlanModal',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {},
      layout: {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
      },
      confirmLoading: false,
      validatorRules: {
        title: [{ required: true, message: '请输入预案名称' }],
        content: [{ required: true, message: '请输入预案内容' }],
      },
      url: {
        add: '/alarm/prePlan/add',
        edit: '/alarm/prePlan/edit',
      },
    }
  },
  created() {},
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          httpAction(httpurl, this.model, method)
            .then((res) => {
              if (res.success) {
                that.$message.success(res.message)
                that.$emit('ok')
              } else {
                that.$message.error(res.message)
              }
            })
            .finally(() => {
              that.confirmLoading = false
              that.close()
            })
        } else {
          return false
        }
      })
    },
    handleCancel() {
      this.close()
    },
  },
}
</script>

<style lang="less" scoped></style>
