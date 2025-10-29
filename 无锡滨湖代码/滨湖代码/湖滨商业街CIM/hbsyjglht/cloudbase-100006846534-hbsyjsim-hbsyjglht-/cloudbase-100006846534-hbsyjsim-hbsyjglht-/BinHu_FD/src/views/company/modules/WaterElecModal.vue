<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="使用年月">
          <j-date  style="width: 100%"  placeholder="请选择使用年月"  :showTime="false" dateFormat="YYYY-MM" v-decorator="[ 'syyf', validatorRules.syny]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="使用量">
          <a-input placeholder="请输入使用量" v-decorator.trim="['syl', validatorRules.syl]"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import {addWaterElec, editWaterElec} from '@/api/api'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: "WaterElecModal",
    components: {
      JDate
    },
    data() {
      return {
        title: "操作",
        visible: false,
        visibleCheck: true,
        model: {},
        qyid: "",
        type:"",
        status: 1,
        labelCol: {
          xs: {span: 24},
          sm: {span: 5},
        },
        wrapperCol: {
          xs: {span: 24},
          sm: {span: 16},
        },
        confirmLoading: false,
        form: this.$form.createForm(this),
        validatorRules: {
          syny: {rules: [{required: true, message: '请选择使用年月!'}]},
          syl: {rules: [{required: true, message: '请输入使用量!'}]},
        },
      }
    },
    created() {
    },
    methods: {
      add(qyid,type) {
        this.qyid = qyid;
        this.type = type;
        this.edit({});
      },
      edit(record) {
        if (record.id) {
          this.qyid = record.qyid;
          this.type = record.type
          this.visibleCheck = (record.status == 1) ? true : false;
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.qyid = this.qyid;
        this.model.type = this.type;
        this.model.status = this.status;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'syyf','syl'))
        });
      },
      onChose(checked) {
        if (checked) {
          this.status = 1;
          this.visibleCheck = true;
        } else {
          this.status = 0;
          this.visibleCheck = false;
        }
      },
      // 确定
      handleOk() {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            values.itemText = (values.itemText || '').trim()
            values.itemValue = (values.itemValue || '').trim()
            values.description = (values.description || '').trim()
            let formData = Object.assign(this.model, values);
            formData.status = this.status;
            let obj;
            if (!this.model.id) {
              obj = addWaterElec(formData);
            } else {
              obj = editWaterElec(formData);
            }
            obj.then((res) => {
              if (res.success) {
                that.$message.success(res.message);
                that.$emit('ok');
              } else {
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
      },
      // 关闭
      handleCancel() {
        this.close();
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
    }
  }
</script>