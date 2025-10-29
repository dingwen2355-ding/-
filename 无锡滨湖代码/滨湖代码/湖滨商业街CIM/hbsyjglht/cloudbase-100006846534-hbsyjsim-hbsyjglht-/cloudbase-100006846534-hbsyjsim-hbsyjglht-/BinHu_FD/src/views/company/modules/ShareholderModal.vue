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
          label="股东名称">
          <a-input placeholder="请输入股东名称" v-decorator.trim="['gdmc', validatorRules.gdmc]"/>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="股东类型">
          <a-input placeholder="请输入股东类型" v-decorator.trim="['gdlx', validatorRules.gdlx]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="持股比例">
          <a-input placeholder="请输入持股比例" v-decorator.trim="['cgbl', validatorRules.cgbl]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="认缴出资额（万元）">
          <a-input placeholder="请输入认缴出资额" v-decorator.trim="['rjcz', validatorRules.rjcz]"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="认缴日期">
          <j-date  style="width: 100%"  placeholder="请选择认缴日期"  :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'rjrq', validatorRules.rjrq]"  />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实际认缴出资额（万元）">
          <a-input placeholder="请输入实际认缴出资额" v-decorator.trim="['sjrjcz']"/>
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实际认缴日期">
          <j-date  style="width: 100%" :showTime="false" placeholder="请选择实际认缴日期" dateFormat="YYYY-MM-DD" v-decorator="[ 'sjrjrq']"  />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import pick from 'lodash.pick'
  import {addShareholder, editShareholder} from '@/api/api'
  import JDate from '@/components/jeecg/JDate'


  export default {
    name: "ShareholderModal",
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
          gdmc: {rules: [{required: true, message: '请输入股东名称!'}]},
          gdlx: {rules: [{required: true, message: '请输入股东类型!'}]},
          cgbl: {rules: [{required: true, message: '请输入持股比例!'}]},
          rjcz: {rules: [{required: true, message: '请输入认缴出资额!'}]},
          rjrq: {rules: [{required: true, message: '请选择认缴日期!'}]},
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
          this.form.setFieldsValue(pick(this.model, 'gdmc', 'gdlx', 'cgbl', 'rjcz','rjrq','sjrjrq','sjrjcz'))
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
              obj = addShareholder(formData);
            } else {
              obj = editShareholder(formData);
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