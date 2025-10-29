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
          label="汇缴年月">
          <j-date  style="width: 100%"  placeholder="请选择汇缴年月"  :showTime="false" dateFormat="YYYY-MM" v-decorator="[ 'hjny', validatorRules.hjny]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="缴存人数">
          <a-input placeholder="请输入缴存人数" v-decorator.trim="['jcrs', validatorRules.jcrs]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位性质">
          <a-input placeholder="请输入单位性质" v-decorator.trim="['dwxz', validatorRules.dwxz]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="处理类型">
          <a-input placeholder="请输入处理类型" v-decorator.trim="['cllx', validatorRules.cllx]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="缴存比例">
          <a-input placeholder="请输入缴存比例" v-decorator.trim="['jcbl', validatorRules.jcbl]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="单位汇缴年月">
          <j-date  style="width: 100%"  placeholder="请选择单位汇缴年月"  :showTime="false" dateFormat="YYYY-MM" v-decorator="[ 'dwhjny', validatorRules.dwhjny]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="确认日期">
          <j-date  style="width: 100%"  placeholder="请选择确认日期"  :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'qrrq', validatorRules.qrrq]"  />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实缴金额">
          <a-input placeholder="请输入实缴金额" v-decorator.trim="['sjje', validatorRules.sjje]"/>
        </a-form-item>


      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import {addAccumulationFund, editAccumulationFund} from '@/api/api'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: "AccumulationFund",
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
          hjny: {rules: [{required: true, message: '请选择汇缴年月!'}]},
          jcrs: {rules: [{required: true, message: '请输入缴存人数!'}]},
          dwxz: {rules: [{required: true, message: '请输入单位性质!'}]},
          cllx: {rules: [{required: true, message: '请输入处理类型!'}]},
          jcbl: {rules: [{required: true, message: '请输入缴存比例!'}]},
          dwhjny: {rules: [{required: true, message: '请输入单位汇缴年月!'}]},
          sjje: {rules: [{required: true, message: '请输入实缴金额!'}]},
          qrrq: {rules: [{required: true, message: '请选择确认日期!'}]},
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
          this.visibleCheck = (record.status == 1) ? true : false;
        }
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.model.qyid = this.qyid;
        this.model.status = this.status;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model, 'qyid','jcrs','sjje','qrrq','cllx','jcbl','hjny','dwxz','dwhjny'))
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
              obj = addAccumulationFund(formData);
            } else {
              obj = editAccumulationFund(formData);
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