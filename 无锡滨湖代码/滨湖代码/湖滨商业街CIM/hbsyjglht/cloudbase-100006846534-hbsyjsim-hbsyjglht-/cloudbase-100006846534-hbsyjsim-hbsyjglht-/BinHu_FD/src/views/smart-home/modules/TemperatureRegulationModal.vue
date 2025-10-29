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
        <a-form-model-item label="温度" required prop="val">
          <a-input-number :default-value="15" v-model="model.val" :min="15" :max="35" />
        </a-form-model-item>
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction } from '@/api/manage'

export default {
  name: 'TemperatureRegulationModal',
  data() {
    return {
      title: '操作',
      visible: false,
      model: {
        sn: 'BC00D076508230F3',
        cmd: 'control',
        data: '',
        room: '',
        val: '',
      },
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
        temp: [{ required: true, message: '请调节合适的温度' }],
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
      this.model.data = record.function
      this.model.room = record.room
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
          console.log(this.model)
          httpAction('/device/smartHome/kpi', this.model, 'post')
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
