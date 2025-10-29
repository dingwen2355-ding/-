<template>
  <a-drawer
    :title="title"
    :maskClosable="true"
    :width="drawerWidth"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible"
    style="height: 100%;overflow: auto;padding-bottom: 53px;">

    <template slot="title">
      <div style="width: 100%;">
        <span>{{ title }}</span>
        <span style="display:inline-block;width:calc(100% - 51px);padding-right:10px;text-align: right">
          <a-button @click="toggleScreen" icon="appstore" style="height:20px;width:20px;border:0px"></a-button>
        </span>
      </div>

    </template>

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="企业名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input placeholder="请输入企业名称" v-decorator.trim="[ 'jgmc', validatorRules.jgmc]" />
        </a-form-item>
          <a-form-item label="统一社会信用代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入统一社会信用代码"  v-decorator.trim="[ 'tyshxydm', validatorRules.tyshxydm]" />
          </a-form-item>
        <a-form-item label="注册号" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入注册号" v-decorator.trim="[ 'zch']" />
        </a-form-item>
        <a-form-item label="组织机构代码" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入组织机构代码" v-decorator.trim="[ 'zzjgdm']" />
        </a-form-item>
        <a-form-item label="纳税人识别号" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入纳税人识别号" v-decorator.trim="[ 'nsrsbh', validatorRules.nsrsbh]" />
        </a-form-item>
        <a-form-item label="纳税人资质" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-select showSearch  placeholder="请选择纳税人资质" style="width: 100%" v-decorator="['bsrzz']">
            <a-select-option value="一般纳税人">一般纳税人</a-select-option>
            <a-select-option value="小规模纳税人">小规模纳税人</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="注册地址" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入注册地址" v-decorator.trim="[ 'zcdz', validatorRules.zcdz]" />
        </a-form-item>
        <a-form-item label="法人代表" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入法人代表" v-decorator.trim="[ 'fddbrxm', validatorRules.fddbrxm]" />
        </a-form-item>
        <a-form-item label="成立日期" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <j-date  style="width: 100%" :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'clrq', validatorRules.clrq]"  />
        </a-form-item>
        <a-form-item label="经营(营业)起始日期" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <j-date  style="width: 100%" :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'jyqsrq', validatorRules.jyqsrq]"  />
        </a-form-item>
        <a-form-item label="经营(营业)截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <j-date  style="width: 100%" :showTime="false" dateFormat="YYYY-MM-DD" v-decorator="[ 'jyjzrq']"  />
        </a-form-item>
        <a-form-item label="企业类型" :labelCol="labelCol" :wrapperCol="wrapperCol" >
            <a-select showSearch  placeholder="请选择企业类型" style="width: 100%" v-decorator="['jglx']">
              <a-select-option value="工商个体户">工商个体户</a-select-option>
              <a-select-option value="工商企业">工商企业</a-select-option>
            </a-select>
        </a-form-item>
        <a-form-item label="注册资本" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入注册资本" v-decorator.trim="[ 'zczb', validatorRules.zczb]" />
        </a-form-item>
        <a-form-item label="登记机关" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入登记机关" v-decorator.trim="[ 'frdjgljg', validatorRules.frdjgljg]" />
        </a-form-item>
        <a-form-item label="人员规模" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入人员规模" v-decorator.trim="['zcdz']" />
        </a-form-item>
        <a-form-item label="单位公积金账号" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入单位公积金账号" v-decorator.trim="[ 'dwgjjzh']" />
        </a-form-item>
        <a-form-item label="股票类别" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入股票类别" v-decorator.trim="['ssbk']" />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="经营范围">
          <a-textarea :rows="5" placeholder="请输入经营范围" v-decorator="[ 'zzhywfw', validatorRules.description ]" />
        </a-form-item>
        <a-form-item label="所属行业" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-select showSearch  placeholder="请选择所属行业" optionFilterProp="children" :filterOption="filterOption" style="width: 100%" v-decorator="['sshy']" >
            <a-select-option value="休闲娱乐">休闲娱乐</a-select-option>
            <a-select-option value="其他类型">其他类型</a-select-option>
            <a-select-option value="科技服务">科技服务</a-select-option>
            <a-select-option value="零售批发">零售批发</a-select-option>
            <a-select-option value="餐饮住宿">餐饮住宿</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所属街道" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-input placeholder="请输入所属街道" v-decorator.trim="[ 'ssjd']" />
        </a-form-item>
        <a-form-item label="企业状态" :labelCol="labelCol" :wrapperCol="wrapperCol" >
          <a-select showSearch  placeholder="请选择企业状态" optionFilterProp="children" :filterOption="filterOption" style="width: 100%" v-decorator="['qyzt']" >
            <a-select-option value="吊销，已注销">吊销，已注销</a-select-option>
            <a-select-option value="吊销，未注销">吊销，未注销</a-select-option>
            <a-select-option value="存续(在营、开业、在册)">存续(在营、开业、在册)</a-select-option>
            <a-select-option value="撤销">撤销</a-select-option>
            <a-select-option value="注销">注销</a-select-option>
            <a-select-option value="迁出">迁出</a-select-option>
          </a-select>
        </a-form-item>


      </a-form>
    </a-spin>
    <depart-window ref="departWindow" @ok="modalFormOk"></depart-window>

    <div class="drawer-bootom-button" v-show="!disableSubmit">
      <a-popconfirm title="确定放弃编辑？" @confirm="handleCancel" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="confirmLoading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
  import pick from 'lodash.pick'
  import moment from 'moment'
  import Vue from 'vue'
  // 引入搜索部门弹出框的组件
  import departWindow from '../../system/modules/DepartWindow'
  import JSelectPosition from '@/components/jeecgbiz/JSelectPosition'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { getAction } from '@/api/manage'
  import {addCompany,editCompany} from '@/api/api'
  import { disabledAuthFilter } from "@/utils/authFilter"
  import {duplicateCheck } from '@/api/api'
  import JImageUpload from '../../../components/jeecg/JImageUpload'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JDate from '@/components/jeecg/JDate'

  export default {
    name: "CompanyModal",
    components: {
      JDate,
      JImageUpload,
      departWindow,
      JSelectDepart,
      JSelectPosition
    },
    data () {
      return {
        carTypeOptions:[],
        carUseOptions:[],
        departDisabled: false, //是否是我的部门调用该页面
        // roleDisabled: false, //是否是角色维护调用该页面
        modalWidth:800,
        drawerWidth:700,
        modaltoggleFlag:true,
        confirmDirty: false,
        selectedDepartKeys:[], //保存用户选择部门id
        checkedDepartKeys:[],
        checkedDepartNames:[], // 保存部门的名称 =>title
        checkedDepartNameString:"", // 保存部门的名称 =>title
        resultDepartOptions:[],
        userId:"", //保存用户id
        disableSubmit:false,
        // userDepartModel:{userId:'',departIdList:[]}, // 保存SysUserDepart的用户部门中间表数据需要的对象
        dateFormat:"YYYY-MM-DD",
        validatorRules:{
          jgmc:{
            rules: [{
              required: true, message: '请输入企业名称!'
            }]
          },
          tyshxydm:{
            rules: [{
              required: true, message: '请输入统一社会信用代码!'
            }]
          },
          nsrsbh:{
            rules: [{
              required: true,
              message: '请输入纳税人识别号'
            }],
          },
          zcdz:{
            rules: [{
              required: true,
              message: '请输入注册地址'
            }],
          },
          fddbrxm:{
            rules: [{
              required: true,
              message: '请输入法人代表'
            }],
          },
          clrq:{
            rules: [{
              required: true,
              message: '请选择成立日期'
            }],
          },
          jyqsrq:{
            rules: [{
              required: true,
              message: '请选择经营(营业)起始日期'
            }],
          },
          jyjzrq:{
            rules: [{
              required: true,
              message: '请选择经营(营业)截止日期'
            }],
          },
          zczb:{
            rules: [{
              required: true,
              message: '请输入注册资本'
            }],
          },
          frdjgljg:{
            rules: [{
              required: true,
              message: '请输入登记机关'
            }],
          }
        },
        departIdShow:false,
        departIds:[], //负责部门id
        title:"操作",
        visible: false,
        model: {},
        roleList:[],
        selectedRole:[],
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        uploadLoading:false,
        confirmLoading: false,
        headers:{},
        form:this.$form.createForm(this),
        picUrl: "",
        url: {
        //   // fileUpload: window._CONFIG['domianURL']+"/sys/common/upload",
        //   userWithDepart: "/sys/user/userDepartList", // 引入为指定用户查看部门信息需要的url
        //   userId:"/sys/user/generateUserId", // 引入生成添加用户情况下的url
        //   syncUserByUserName:"/process/extActProcess/doSyncUserByUserName",//同步用户到工作流
            queryTenantList: '/sys/tenant/queryList'
        //   getCarTypeDict: 'sys/dict/getDictItems/cartype',
        //   getCarUseDict: 'sys/dict/getDictItems/caruse'
         },
        identity:"1",
        fileList:[],
        tenantList: [],
        currentTenant:[]
      }
    },
    created () {
      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = {"X-Access-Token":token}
      //this.initTenantList()
    //  this.queryAllDict();

    },
    computed:{

    },
    methods: {
      // queryAllDict (){
      //   // getAction(this.url.getCarTypeDict).then((res) => {
      //   //   this.carTypeOptions = res.result
      //   // })
      //   // getAction(this.url.getCarUseDict).then((res) => {
      //   //   this.carUseOptions = res.result
      //   // })
      // },
      filterOption(input, option) {
        return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      },
      isDisabledAuth(code){
        return disabledAuthFilter(code);
      },
      initTenantList(){
        getAction(this.url.queryTenantList).then(res=>{
          if(res.success){
            this.tenantList = res.result
          }
        })
      },
      //窗口最大化切换
      toggleScreen(){
        if(this.modaltoggleFlag){
          this.modalWidth = window.innerWidth;
        }else{
          this.modalWidth = 800;
        }
        this.modaltoggleFlag = !this.modaltoggleFlag;
      },

      refresh () {
        this.selectedDepartKeys=[];
        this.checkedDepartKeys=[];
        this.checkedDepartNames=[];
        this.checkedDepartNameString = "";
        this.userId=""
        this.resultDepartOptions=[];
        this.departId=[];
        this.departIdShow=false;
        this.currentTenant = []
      },
      add () {
        //this.picUrl = "";
        this.refresh();
        this.edit({activitiSync:'1'});
      },
      edit (record) {
        this.resetScreenSize(); // 调用此方法,根据屏幕宽度自适应调整抽屉的宽度
        let that = this;
        //that.initialRoleList();
        that.form.resetFields();
        if(record.hasOwnProperty("id")){
          //that.loadUserRoles(record.id);
          setTimeout(() => {
            this.fileList = record.avatar;
          }, 5)
        }
        that.userId = record.id;
        that.visible = true;
        that.model = Object.assign({}, record);
        that.$nextTick(() => {
          that.form.setFieldsValue(pick(this.model,'qywz','qyjs','tyshxydm','zch','zzjgdm','zcdz','fddbrxm','clrq','jglx','zczb','dwgjjzh','frdjgljg','rygm','ssbk','zzhywfw','sshy','ssjd','jgmc','qyzt','nsrsbh','bsrzz','jyqsrq','jyjzrq'))
        });
      },
      loadCheckedDeparts(){
        let that = this;
        if(!that.userId){return}
        getAction(that.url.userWithDepart,{userId:that.userId}).then((res)=>{
          that.checkedDepartNames = [];
          if(res.success){
            var depart=[];
            var departId=[];
            for (let i = 0; i < res.result.length; i++) {
              that.checkedDepartNames.push(res.result[i].title);
              this.checkedDepartNameString = this.checkedDepartNames.join(",");
              that.checkedDepartKeys.push(res.result[i].key);
              //新增负责部门选择下拉框
              depart.push({
                key:res.result[i].key,
                title:res.result[i].title
              })
              departId.push(res.result[i].key)
            }
            that.resultDepartOptions=depart;
            //判断部门id是否存在，不存在择直接默认当前所在部门
            if(this.model.departIds){
              this.departIds=this.model.departIds.split(",");
            }else{
              this.departIds=departId;
            }
            //  that.userDepartModel.departIdList = that.checkedDepartKeys
          }else{
            console.log(res.message);
          }
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.disableSubmit = false;
        this.selectedRole = [];
        this.departIds=[];
        /*this.userDepartModel = {userId:'',departIdList:[]};
        this.checkedDepartNames = [];
        this.checkedDepartNameString='';
        this.checkedDepartKeys = [];
        this.selectedDepartKeys = [];
        this.resultDepartOptions=[];
        this.departIdShow=false;
        this.identity="1";
        this.fileList=[];*/
      },
      moment,
      handleSubmit () {

        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          //debugger
          if (!err) {
            that.confirmLoading = true;
            let formData = Object.assign(this.model, values);
            formData.selectedroles = this.selectedRole.length>0?this.selectedRole.join(","):'';
            let obj;
            if(!this.model.id){
              formData.id = this.userId;
              obj=addCompany(formData);
            }else{
              obj=editCompany(formData);
            }
            obj.then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.checkedDepartNames = [];
              //that.userDepartModel.departIdList = {userId:'',departIdList:[]};
              that.close();
            })

          }
        })
      },
      handleCancel () {
        this.close()
      },
      validateToNextPassword  (rule, value, callback) {
        const form = this.form;
        const confirmpassword=form.getFieldValue('confirmpassword');

        if (value && confirmpassword && value !== confirmpassword) {
          callback('两次输入的密码不一样！');
        }
        if (value && this.confirmDirty) {
          form.validateFields(['confirm'], { force: true })
        }
        callback();
      },
      compareToFirstPassword  (rule, value, callback) {
        const form = this.form;
        if (value && value !== form.getFieldValue('password')) {
          callback('两次输入的密码不一样！');
        } else {
          callback()
        }
      },
      validatePhone(rule, value, callback){
        if(!value){
          callback()
        }else{
          //update-begin-- for：[05] 手机号不支持199号码段--------------------
          if(new RegExp(/^1[3|4|5|7|8|9][0-9]\d{8}$/).test(value)){
            //update-end-- for：[05] 手机号不支持199号码段--------------------

            var params = {
              tableName: 'sys_user',
              fieldName: 'phone',
              fieldVal: value,
              dataId: this.userId
            };
            duplicateCheck(params).then((res) => {
              if (res.success) {
                callback()
              } else {
                callback("手机号已存在!")
              }
            })
          }else{
            callback("请输入正确格式的手机号码!");
          }
        }
      },
      validateEmail(rule, value, callback){
        if(!value){
          callback()
        }else{
          if(new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/).test(value)){
            var params = {
              tableName: 'sys_user',
              fieldName: 'email',
              fieldVal: value,
              dataId: this.userId
            };
            duplicateCheck(params).then((res) => {
              console.log(res)
              if (res.success) {
                callback()
              } else {
                callback("邮箱已存在!")
              }
            })
          }else{
            callback("请输入正确格式的邮箱!")
          }
        }
      },
      validateUsername(rule, value, callback){
        var params = {
          tableName: 'sys_user',
          fieldName: 'username',
          fieldVal: value,
          dataId: this.userId
        };
        duplicateCheck(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("用户名已存在!")
          }
        })
      },
      validateWorkNo(rule, value, callback){
        var params = {
          tableName: 'sys_user',
          fieldName: 'work_no',
          fieldVal: value,
          dataId: this.userId
        };
        duplicateCheck(params).then((res) => {
          if (res.success) {
            callback()
          } else {
            callback("工号已存在!")
          }
        })
      },
      handleConfirmBlur  (e) {
        const value = e.target.value;
        this.confirmDirty = this.confirmDirty || !!value
      },

      normFile  (e) {
        console.log('Upload event:', e);
        if (Array.isArray(e)) {
          return e
        }
        return e && e.fileList
      },
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      handleChange (info) {
        this.picUrl = "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true;
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          console.log(response);
          if(response.success){
            this.model.avatar = response.message;
            this.picUrl = "Has no pic url yet";
          }else{
            this.$message.warning(response.message);
          }
        }
      },
      // 搜索用户对应的部门API
      onSearch(){
        this.$refs.departWindow.add(this.checkedDepartKeys,this.userId);
      },

      // 获取用户对应部门弹出框提交给返回的数据
      modalFormOk (formData) {
        this.checkedDepartNames = [];
        this.selectedDepartKeys = [];
        this.checkedDepartNameString = '';
        this.userId = formData.userId;
        //this.userDepartModel.userId = formData.userId;
        this.departIds=[];
        this.resultDepartOptions=[];
        var depart=[];
        for (let i = 0; i < formData.departIdList.length; i++) {
          this.selectedDepartKeys.push(formData.departIdList[i].key);
          this.checkedDepartNames.push(formData.departIdList[i].title);
          this.checkedDepartNameString = this.checkedDepartNames.join(",");
          //新增部门选择，如果上面部门选择后不为空直接付给负责部门
          depart.push({
            key:formData.departIdList[i].key,
            title:formData.departIdList[i].title
          })
          this.departIds.push(formData.departIdList[i].key)
        }
        this.resultDepartOptions=depart;
        this.userDepartModel.departIdList = this.selectedDepartKeys;
        this.checkedDepartKeys = this.selectedDepartKeys  //更新当前的选择keys
      },
      // 根据屏幕变化,设置抽屉尺寸
      resetScreenSize(){
        let screenWidth = document.body.clientWidth;
        if(screenWidth < 500){
          this.drawerWidth = screenWidth;
        }else{
          this.drawerWidth = 700;
        }
      },
      identityChange(e){
        if(e.target.value==="1"){
          this.departIdShow=false;
        }else{
          this.departIdShow=true;
        }
      }
    }
  }
</script>

<style scoped>
  .avatar-uploader > .ant-upload {
    width:104px;
    height:104px;
  }
  .ant-upload-select-picture-card i {
    font-size: 49px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }

  .ant-table-tbody .ant-table-row td{
    padding-top:10px;
    padding-bottom:10px;
  }

  .drawer-bootom-button {
    position: absolute;
    bottom: -8px;
    width: 100%;
    border-top: 1px solid #e8e8e8;
    padding: 10px 16px;
    text-align: right;
    left: 0;
    background: #fff;
    border-radius: 0 0 2px 2px;
  }
</style>