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
          label="缴费年月">
          <j-date  style="width: 100%"  placeholder="请选择缴费年月"  :showTime="false" dateFormat="YYYY-MM" v-decorator="[ 'jfny', validatorRules.jfny]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="参保类别">
          <a-input placeholder="请输入参保类别" v-decorator.trim="['cblb', validatorRules.dzsphm]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="参保人数">
          <a-input placeholder="请输入参保人数" v-decorator.trim="['cbrs', validatorRules.cbrs]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="职工工资总数（元）">
          <a-input placeholder="请输入职工工资总数" v-decorator.trim="['zggzzs', validatorRules.zggzzs]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位缴纳基数（元）">
          <a-input placeholder="请输入单位缴纳基数" v-decorator.trim="['dwjnjs', validatorRules.dwjnjs]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="本月应缴金额">
          <a-input placeholder="请输入本月应缴金额" v-decorator.trim="['yjje', validatorRules.yjje]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="本月实缴金额">
          <a-input placeholder="请输入本月实缴金额" v-decorator.trim="['sjje', validatorRules.sjje]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="缴纳日期">
          <j-date  style="width: 100%"  placeholder="请选择缴纳日期"  :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'jnrq', validatorRules.jnrq]"  />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import {addSocialInsurance, editSocialInsurance} from '@/api/api'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: "SocialInsuranceModal",
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
          jfny: {rules: [{required: true, message: '请选择缴存年月!'}]},
          cblb: {rules: [{required: true, message: '请输入参保类别!'}]},
          cbrs: {rules: [{required: true, message: '请输入参保人数!'}]},
          zggzzs: {rules: [{required: true, message: '请输入职工工资总数!'}]},
          dwjnjs: {rules: [{required: true, message: '请输入单位缴纳基数!'}]},
          yjje: {rules: [{required: true, message: '请输入本月应缴金额!'}]},
          sjje: {rules: [{required: true, message: '请输入本月实缴金额!'}]},
          jnrq: {rules: [{required: true, message: '请选择缴纳日期!'}]},
        },
      }
    },
    created() {
    },
    methods: {
      add(qyid,type) {
        this.qyid = qyid;

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
        this.model.status = this.status;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'jfny','cblb','cbrs','zggzzs','dwjnjs','yjje','sjje','jnrq'))
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
              obj = addSocialInsurance(formData);
            } else {
              obj = editSocialInsurance(formData);
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