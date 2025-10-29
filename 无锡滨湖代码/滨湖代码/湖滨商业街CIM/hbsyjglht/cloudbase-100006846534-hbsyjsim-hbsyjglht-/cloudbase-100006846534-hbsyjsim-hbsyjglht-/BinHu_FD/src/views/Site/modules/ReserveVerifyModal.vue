<template>
    <j-modal
      :title="title"
      :width="800"
      :visible="visible"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @cancel="handleCancel"
     >
      <template slot="footer">
        <a-button @click="audit('1')" type="primary">通过</a-button>
        <a-button  @click="audit('2')" type="">不通过</a-button>
       
      </template>
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
        
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="预约时长/分">
            <a-input-number disabled v-decorator="[ 'reserveTime', {}]" />
          </a-form-item>
         <!--  <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="reserveUser">
            <a-input placeholder="请输入reserveUser" v-decorator="['reserveUser', validatorRules.reserveUser ]" />
          </a-form-item> -->
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="场地名称">
            <a-input disabled placeholder="请输入预约场地" v-decorator="['reservePlace', validatorRules.reservePlace ]" />
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="场地类型">
            <j-dict-select-tag disabled placeholder="请选择类型" v-model="model.placeType" dictCode="PLACE_TYPE"/>     
           <!--  <a-input disabled placeholder="请输入预约场地" v-decorator="['reservePlace', validatorRules.reservePlace ]" /> -->
          </a-form-item>
         <!--  <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="审核人">
            <a-input placeholder="请输入审核人" v-decorator="['auditUser', {}]" />
          </a-form-item> -->
         <!--  <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="审核结果">
            <a-input placeholder="请输入审核结果" v-decorator="['status', {}]" />
          </a-form-item> -->
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="审核有效期">
            <a-input disabled placeholder="请输入审核有效期" v-decorator="['expirationDate', {}]" />
          </a-form-item>
         <!--  <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="审核时间">
            <a-input placeholder="请输入审核时间" v-decorator="['auditTime', {}]" />
          </a-form-item> -->
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="审核意见">
            <a-input placeholder="请输入审核意见" v-decorator="['auditMsg', {}]" />
          </a-form-item>
          
        </a-form>
      </a-spin>
    </j-modal>
  </template>
  
  <script>
    import { httpAction } from '@/api/manage'
    import pick from 'lodash.pick'
    import moment from "moment"
  
    export default {
      name: "ReserveRecordModal",
      data () {
        return {
          title:"操作",
          visible: false,
          model: {},
          labelCol: {
            xs: { span: 24 },
            sm: { span: 5 },
          },
          wrapperCol: {
            xs: { span: 24 },
            sm: { span: 16 },
          },
  
          confirmLoading: false,
          form: this.$form.createForm(this),
          validatorRules:{
          reserveUser:{rules: [{ required: true, message: '请输入reserveUser!' }]},
          reservePlace:{rules: [{ required: true, message: '请输入预约场地!' }]},
          },
          url: {
            audit: "/reserve/reserveRecord/audit",
            
          },
        }
      },
      created () {
      },
      methods: {
        add () {
          this.edit({});
        },
        edit (record) {
          this.form.resetFields();
          this.model = Object.assign({}, record);
          this.visible = true;
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.model,'reserveTime',/* 'reserveUser', */'reservePlace',/* 'auditUser', *//* 'status', */'expirationDate',/* 'auditTime', */'auditMsg'))
            //时间格式化
          });
  
        },
        close () {
          this.$emit('close');
          this.visible = false;
        },
        audit(auditState){
            let that=this
            that.confirmLoading = true;

            httpAction(this.url.audit,{id:this.model.id,auditState},'post').then((res)=>{
                if(res.success){
                    this.$message.success('审核成功')
                }
                else{
                    this.$message.error(res.message)
                }
            }).finally(() => {
                that.confirmLoading = false;
                that.close();
              })
        },
        handleOk () {
          const that = this;
          // 触发表单验证
          this.form.validateFields((err, values) => {
            if (!err) {
              that.confirmLoading = true;
              let httpurl = '';
              let method = '';
              if(!this.model.id){
                httpurl+=this.url.add;
                method = 'post';
              }else{
                httpurl+=this.url.edit;
                 method = 'put';
              }
              let formData = Object.assign(this.model, values);
              //时间格式化
              
              console.log(formData)
              httpAction(httpurl,formData,method).then((res)=>{
                if(res.success){
                  that.$message.success(res.message);
                  that.$emit('ok');
                }else{
                  that.$message.warning(res.message);
                }
              }).finally(() => {
                that.confirmLoading = false;
                that.close();
              })
  
  
  
            }
          })
        },
        handleCancel () {
          this.close()
        },
  
  
      }
    }
  </script>
  
  <style lang="less" scoped>
  
  </style>