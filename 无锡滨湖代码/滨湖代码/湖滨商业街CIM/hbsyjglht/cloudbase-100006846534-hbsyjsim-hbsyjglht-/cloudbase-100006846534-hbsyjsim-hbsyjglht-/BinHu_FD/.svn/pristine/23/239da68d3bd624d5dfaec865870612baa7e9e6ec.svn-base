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
          prop="beginTime"
          label="预约起始时间">
          <div style="display:flex;    align-items: center;font-size: 16px;">
            <a-select v-model="beginH" style="width:60px" size="small" :showArrow="false" show-search :options="dict.hours">             
            </a-select>时
            <a-select v-model="beginM" style="width:60px" size="small" :showArrow="false" show-search :options="dict.minutes">             
            </a-select>分
          </div>
          <!-- <a-time-picker v-model="model.beginTime"></a-time-picker> -->
          <!-- <a-input placeholder="请输入预约起始时间" v-decorator="['beginTime', validatorRules.beginTime ]" /> -->
        </a-form-model-item>
        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="endTime"
          label="预约截止时间">
          <div style="display:flex;    align-items: center;font-size: 16px;">
            <a-select v-model="endH" style="width:60px" size="small" :showArrow="false" show-search :options="dict.hours">             
            </a-select>时
            <a-select v-model="endM" style="width:60px" size="small" :showArrow="false" show-search :options="dict.minutes">             
            </a-select>分
          </div>
         <!--  <a-time-picker v-model="model.endTime"></a-time-picker> -->
         <!--  <a-input placeholder="请输入预约截止时间" v-decorator="['endTime', validatorRules.endTime ]" /> -->
        </a-form-model-item>
        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="minTotal"
          label="预约总时长/分">
          <a-input-number v-model="model.minTotal" />
        </a-form-model-item>
		
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
import { get } from 'lodash'
  import pick from 'lodash.pick'
  import moment from "moment"

  export default {
    name: "ReservePeriodModal",
    data () {
      const hours=[]
for(let i=0;i<24;i++){ 
  if(i<9) i='0'+i 
  hours.push({label:String(i),value:String(i)})}
const minutes=[]
for(let i=0;i<60;i++){ 
  if(i<9) i='0'+i 
 minutes.push({label:String(i),value:String(i)})}
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
        beginTime: [{ required: true, message: '请输入预约起始时间!' }],
        endTime:[{ required: true, message: '请输入预约截止时间!' }],
        },
        dict:{hours,minutes},
        url: {
          add: "/reserve/reservePeriod/add",
          edit: "/reserve/reservePeriod/edit",
        },
      }

    },
    computed:{
      beginH:{
        get(){
          if(!this.model.beginTime) return '00'
          return this.model.beginTime.split(':')[0]
        },
        set(val){
          this.$set(this.model,'beginTime',val+':'+this.beginM)
         
        }
      },
      beginM:{
        get(){
          if(!this.model.beginTime) return '00'
          return this.model.beginTime.split(':')[1]
        },
        set(val){
          this.$set(this.model,'beginTime',this.beginH+':'+val)
         
        }
      },
      endH:{
        get(){
          if(!this.model.endTime) return '00'
          return this.model.endTime.split(':')[0]
        },
        set(val){
          this.$set(this.model,'endTime',val+':'+this.endM)
        }
      },
      endM:{
        get(){
          if(!this.model.endTime) return '00'
          return this.model.endTime.split(':')[1]
        },
        set(val){
          this.$set(this.model,'endTime',this.endH+':'+val)       
        }
      }
    },
    watch:{
      'model.beginTime'(){
        this.computedMinTotal()
      },
      'model.endTime'(){
        this.computedMinTotal()
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
      computedMinTotal(){
        this.$set(this.model,'minTotal',(this.endH-this.beginH)*60+Number(this.endM)-Number(this.beginM)) 
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