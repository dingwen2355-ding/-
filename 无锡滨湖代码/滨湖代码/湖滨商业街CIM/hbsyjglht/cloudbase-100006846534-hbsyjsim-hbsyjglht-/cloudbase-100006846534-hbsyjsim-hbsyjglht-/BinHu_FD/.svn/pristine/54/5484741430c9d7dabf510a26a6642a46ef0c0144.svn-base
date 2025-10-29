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
          label="年报年度">
          <a-input placeholder="请输入年报年度" v-decorator.trim="['nbnd', validatorRules.nbnd]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="资产总额/产值(万元)">
          <a-input placeholder="请输入资产总额/产值" v-decorator.trim="['zcze', validatorRules.zcze]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="营业总收入(万元)">
          <a-input placeholder="请输入营业总收入" v-decorator.trim="['yyzsr', validatorRules.yyzsr]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="其中主营业务收入(万元)">
          <a-input placeholder="请输入其中主营业务收入" v-decorator.trim="['qzzyywsr', validatorRules.qzzyywsr]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="利润总额(万元)">
          <a-input placeholder="请输入利润总额" v-decorator.trim="['lrze', validatorRules.lrze]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="净利润(万元)">
          <a-input placeholder="请输入净利润" v-decorator.trim="['jlr', validatorRules.jlr]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="纳税总额(万元)">
          <a-input placeholder="请输入纳税总额" v-decorator.trim="['nsze', validatorRules.nsze]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="从业人数">
          <a-input placeholder="请输入从业人数" v-decorator.trim="['cyrs', validatorRules.cyrs]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="申报日期">
          <j-date  style="width: 100%"  placeholder="请选择申报日期"  :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'sbrq', validatorRules.sbrq]"  />
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import {addReport, editReport} from '@/api/api'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: "ReportModal",
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
          nbnd: {rules: [{required: true, message: '请输入年报年度!'}]},
          zcze: {rules: [{required: true, message: '请输入资产总额/产值!'}]},
          yyzsr: {rules: [{required: true, message: '请输入营业总收入!'}]},
          qzzyywsr: {rules: [{required: true, message: '请输入其中主营业务收入!'}]},
          lrze: {rules: [{required: true, message: '请输入利润总额!'}]},
          jlr: {rules: [{required: true, message: '请输入净利润!'}]},
          nsze: {rules: [{required: true, message: '请输入纳税总额!'}]},
          cyrs: {rules: [{required: true, message: '请输入从业人数!'}]},
          sbrq: {rules: [{required: true, message: '请选择申报日期!'}]},
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
          this.form.setFieldsValue(pick(this.model, 'qyid','nbnd','zcze','yyzsr','qzzyywsr','lrze','jlr','nsze','cyrs','sbrq'))
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
              obj = addReport(formData);
            } else {
              obj = editReport(formData);
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