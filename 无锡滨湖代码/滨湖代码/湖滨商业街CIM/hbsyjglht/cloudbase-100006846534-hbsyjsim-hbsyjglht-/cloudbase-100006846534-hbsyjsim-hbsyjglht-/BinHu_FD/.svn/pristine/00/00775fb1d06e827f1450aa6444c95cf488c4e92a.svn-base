<template>
    <j-modal
      :title="title"
      :width="800"
      :visible="visible"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @ok="handleOk"
      @cancel="handleCancel"
      cancelText="关闭">
      
      <a-spin :spinning="confirmLoading">
        <a-form-model ref="form" :model="model" :rules="validatorRules">
        
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="placeType"
            label="场所类型">
            <j-dict-select-tag v-model="model.placeType" dictCode="PLACE_TYPE"/>
           
          </a-form-model-item>
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="placeName"
            label="场所名称">
            <a-input v-model="model.placeName" placeholder="请输入场所名称"  />
          </a-form-model-item>
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="billing"
            label="计费标准（元/分钟）">
            <a-input-number v-model="model.billing" placeholder="请输入计费标准"  />
          </a-form-model-item>
         <!--  <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="state"
            label="场所状态">
            <a-input v-model="model.state" placeholder="请输入场所状态"  />
          </a-form-model-item> -->
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="description"
            label="场所描述">
            <a-input v-model="model.description" placeholder="请输入场所描述"  />
          </a-form-model-item>
          <a-form-model-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            prop="imgUrl"
            label="场所图片">

            <a-input v-model="model.imgUrl" />
          </a-form-model-item>
        </a-form-model>
      </a-spin>
    </j-modal>
  </template>
  
  <script>
    import { httpAction } from '@/api/manage'
    import pick from 'lodash.pick'
    import moment from "moment"
  
    export default {
      name: "ReservePlaceModal",
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
          validatorRules:{
            placeType:[{ required: true, message: '请选择场所类型!' }],
            placeName:[{ required: true, message: '请输入场所名称!' }],
            billing:[{ required: true, message: '请输入计费标准!' }],
          },
          url: {
            add: "/reserve/reservePlace/add",
            edit: "/reserve/reservePlace/edit",
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
         
          this.model = Object.assign({}, record);
          this.visible = true;
        
  
        },
        close () {
          this.$emit('close');
          this.visible = false;
        },
        handleOk () {
          const that = this;
          // 触发表单验证
          this.$refs.form.validate((valid) => {
          if (valid) {
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
              let formData = Object.assign({},this.model);
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