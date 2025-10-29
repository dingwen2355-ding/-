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
          label="电子税票号码">
          <a-input placeholder="请输入电子税票号码" v-decorator.trim="['dzsphm', validatorRules.dzsphm]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="征收项目">
          <a-input placeholder="请输入征收项目" v-decorator.trim="['zsxm', validatorRules.zsxm]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="计税依据">
          <a-input placeholder="请输入计税依据" v-decorator.trim="['jsyj', validatorRules.jsyj]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="税率">
          <a-input placeholder="请输入税率" v-decorator.trim="['sl', validatorRules.sl]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实缴金额">
          <a-input placeholder="请输入实缴金额" v-decorator.trim="['sjje', validatorRules.sjje]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="开具日期">
          <j-date  style="width: 100%"  placeholder="请选择开具日期"  :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'kjrq', validatorRules.kjrq]"  />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import {addTaxInfo, editTaxInfo} from '@/api/api'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: "TaxModal",
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
          dzsphm: {rules: [{required: true, message: '请输入电子税票号码!'}]},
          zsxm: {rules: [{required: true, message: '请输入征收项目!'}]},
          jsyj: {rules: [{required: true, message: '请输入计税依据!'}]},
          sl: {rules: [{required: true, message: '请输入税率!'}]},
          sjje: {rules: [{required: true, message: '请输入实缴金额!'}]},
          kjrq: {rules: [{required: true, message: '请选择开具日期!'}]},
        },
      }
    },
    created() {
    },
    methods: {
      add(qyid) {
        this.qyid = qyid;
        this.edit({});
      },
      edit(record) {
        if (record.id) {
          this.qyid = record.qyid;
          this.visibleCheck = (record.status == 1) ? true : false;
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.qyid = this.qyid;
        this.model.status = this.status;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'dzsphm','zsxm','jsyj','sl','sjje','kjrq'))
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
              obj = addTaxInfo(formData);
            } else {
              obj = editTaxInfo(formData);
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