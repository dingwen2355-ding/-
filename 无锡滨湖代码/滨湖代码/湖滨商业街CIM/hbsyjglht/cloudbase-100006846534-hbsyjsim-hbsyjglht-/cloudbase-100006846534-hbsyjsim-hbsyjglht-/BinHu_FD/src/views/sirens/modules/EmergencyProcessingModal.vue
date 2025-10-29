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
        <a-form-model-item label="值班人员" required prop="username">
          <j-select-user-by-dep v-model="model.username" :multi="false"></j-select-user-by-dep>
        </a-form-model-item>
        <a-form-model-item label="分配预案" required prop="plan">
          <j-dict-select-tag placeholder="请选择预案" dictCode="td_pre_plan,title,id" v-model="model.plan" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'

export default {
  name: 'PrePlanModal',
  components: { JDictSelectTag, JSelectUserByDep },
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
        plan: [{ required: true, message: '请选择预案', trigger: 'change' }],
        username: [{ required: true, message: '请选择值班人员', trigger: 'change' }],
      },
      url: {
        add: '/alarm/emergencyProcessing/add',
        edit: '/alarm/emergencyProcessing/edit',
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
