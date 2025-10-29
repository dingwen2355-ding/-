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
          prop="reserveDate"
          label="预约日期">
         
          <a-date-picker v-model="model.reserveDate" valueFormat="YYYY-MM-DD" format="YYYY-MM-DD"  disabled ></a-date-picker>
        </a-form-model-item>
        <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="checkedList"
          label="预约时段">
         
            <a-select v-model="model.checkedList"   mode="multiple" >             
                <a-select-option v-for="item in periodList" :key="item.periodId" :value="item.periodId" :periodList="item">{{item.beginTime+' - '+item.endTime}}</a-select-option>
            </a-select>
        </a-form-model-item>

     <!--    <a-form-model-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          prop="minTotal"
          label="预约总时长/分">
          <a-input-number v-model="model.minTotal" />
        </a-form-model-item> -->
		
      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
    import { getAction,httpAction } from '../../../api/manage';
export default {
name:'reserveModal',
data(){
    return {
        title:"操作",
        visible: false,
        model: {},
        periodList:[],
        checkedList:[],
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
        checkedList: [{ required: true, message: '请选择预约时段!' }],
        reserveDate:[{ required: true, message: '请选择预约日期!' }],
        },
        url: {
          add: "/reserve/reservePeriod/add",
          edit: "/reserve/reservePeriod/edit",
        },
      }},
      methods: {
      add () {
        this.edit({});
      },
      show (record,reserveDate) {
        getAction('/reserve/recordPeriod/placeReservedPeriod',{placeId:record.id,reserveDate:reserveDate}).then(res=>{
            if(res.success){
                //this.$set(record,'periodList',res.result)
                this.periodList=res.result
            }else{
                this.$message.error(res.message)
            }
        }).finally(()=>{
          //  this.$set(record,'listLoading',false)
        })
        this.model = Object.assign({}, record);
        this.model.reserveDate=reserveDate
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

            let formData ={} //Object.assign({},this.model);
            //时间格式化
            formData.reservePlace=this.model.id
            formData.reserveDate=this.model.reserveDate
            console.log('打印日志');
            formData.periodList=this.periodList.filter(item=>{
            
                return that.model.checkedList.includes(item.periodId) 
            })
            console.log(formData)
            httpAction('/reserve/reserveRecord/reserve',formData,'post').then((res)=>{
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

<style>

</style>