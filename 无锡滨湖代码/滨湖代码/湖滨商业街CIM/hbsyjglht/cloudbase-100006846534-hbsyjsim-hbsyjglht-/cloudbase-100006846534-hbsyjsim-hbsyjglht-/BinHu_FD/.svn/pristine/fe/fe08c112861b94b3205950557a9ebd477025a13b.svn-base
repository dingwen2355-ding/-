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
    :ok-button-props="{ props: { disabled: disableSubmit } }"
  >
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" v-bind="layout" :rules="validatorRules">
        <a-form-model-item label="一氧化碳" required prop="co">
          <a-input-number :min="0" v-model="model.co" />
        </a-form-model-item>
        <!-- <a-form-model-item label="二氧化碳" required prop="co2">
          <a-input-number :min="0" v-model="model.co2" />
        </a-form-model-item>
        <a-form-model-item label="臭氧" required prop="o3">
          <a-input-number :min="0" v-model="model.o3" />
        </a-form-model-item>
        <a-form-model-item label="甲醛" required prop="hcho">
          <a-input-number :min="0" v-model="model.hcho" />
        </a-form-model-item>
        <a-form-model-item label="pm1" required prop="pm1">
          <a-input-number :min="0" v-model="model.pm1" />
        </a-form-model-item> -->
        <a-form-model-item label="pm10" required prop="pm10">
          <a-input-number :min="0" v-model="model.pm10" />
        </a-form-model-item>
        <a-form-model-item label="pm2.5" required prop="pm25">
          <a-input-number :min="0" v-model="model.pm25" />
        </a-form-model-item>
        <a-form-model-item label="温度" required prop="temp">
          <a-input-number :min="0" v-model="model.temp" />
        </a-form-model-item>
        <!-- <a-form-model-item label="湿度" required prop="humi">
          <a-input-number :min="0" v-model="model.humi" />
        </a-form-model-item>
        <a-form-model-item label="总挥发性有机化合物" required prop="tvoc">
          <a-input-number :min="0" v-model="model.tvoc" />
        </a-form-model-item> -->
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
import { httpAction, putAction } from '@/api/manage'

export default {
  name: 'EnvironmentModal',
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
      validatorRules: {},
      url: '/monitor/edit',
      disableSubmit: false,
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
          putAction(this.url, this.model)
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
