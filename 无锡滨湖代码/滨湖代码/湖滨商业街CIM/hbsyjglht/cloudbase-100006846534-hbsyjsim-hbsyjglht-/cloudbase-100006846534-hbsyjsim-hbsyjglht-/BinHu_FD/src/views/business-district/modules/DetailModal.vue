<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    @cancel="handleCancel"
    cancelText="关闭"
    wrapClassName="ant-modal-cust-warp"
    style="top:5%;height: 85%;overflow-y: hidden">

      <a-form :form="form">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="工单内容">
          <a-textarea :rows="5" placeholder="..." v-decorator="[ 'gdContent' ]" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="诉求目的">
          <a-textarea :rows="5" placeholder="..." v-decorator="[ 'sqmd']" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="最终处理意见">
          <a-textarea :rows="5" placeholder="..." v-decorator="[ 'zzclyj']" />
        </a-form-item>
      </a-form>
  </a-modal>
</template>

<script>
import pick from 'lodash.pick'

export default {
  name: 'DetailModal',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this),
    }
  },
  methods: {
    add() {
      this.edit({})
    },
    edit(record) {
      console.log(record)
      this.form.resetFields()
      this.model = Object.assign({}, record)
      this.visible = true

      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'gdContent', 'sqmd', 'zzclyj'))
      })

    },
    close() {
      this.$emit('close')
      this.visible = false
    },
    handleCancel() {
      this.close()
    },
  }
}
</script>

<style scoped>

</style>