<template>
  <div class="addWorkGroupMemberDialog" v-if="dialogVisible">
    <el-dialog
      title=" 新增工作组成员"
      :visible.sync="dialogVisible"
      width="850px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form ref="formLabel" :rules="rules" :model="form" label-width="130px">
          <el-form-item label="姓名" prop="personName">
            <el-input clearable v-model="form.personName" placeholder="请输入姓名" style="width: 630px"></el-input>
          </el-form-item>
          <el-form-item label="职务" prop="post">
            <el-input clearable v-model="form.post" placeholder="请输入职务" style="width: 630px"></el-input>
          </el-form-item>
          <el-form-item label="手机" prop="phoneNumber">
            <el-input clearable v-model="form.phoneNumber" placeholder="请输入手机号码" style="width: 630px"></el-input>
          </el-form-item>
          <el-form-item label="办公" prop="office">
            <el-input clearable v-model="form.office" placeholder="请输入办公" style="width: 630px"></el-input>
          </el-form-item>
          <el-form-item label="职责" prop="duty">
            <el-input clearable v-model="form.duty" placeholder="请输入职责" style="width: 630px"></el-input>
          </el-form-item>
          <el-form-item label="联络员" prop="contactPerson">
            <el-input
              clearable
              v-model="form.contactPerson"
              placeholder="请输入联络员姓名"
              style="width: 630px"
            ></el-input>
          </el-form-item>
          <el-form-item label="联络员职务" prop="contactDuty">
            <el-input
              clearable
              v-model="form.contactDuty"
              placeholder="请输入联络员职务"
              style="width: 630px"
            ></el-input>
          </el-form-item>
          <el-form-item label="联络员办公电话" prop="contactOfficePhone">
            <el-input
              clearable
              v-model="form.contactOfficePhone"
              placeholder="请输入联络员办公电话"
              style="width: 630px"
            ></el-input>
          </el-form-item>
          <el-form-item label="联络员手机电话" prop="contactCellPhone">
            <el-input
              clearable
              v-model="form.contactCellPhone"
              placeholder="请输入联络员手机电话"
              style="width: 630px"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Axios from 'axios'
export default {
  name: 'addWorkGroupMemberDialog',
  props: ['dialogVisible', 'formInfo', 'planId', 'planGroupId'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
    },
    formInfo(v) {
      if (v) {
        this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
        // console.log('this.form', this.form)
      }
    }
  },
  data() {
    const phoneCheck = (rule, value, callback) => {
      let isPhone = /^1(3|4|5|7|8)\d{9}$/ // 手机号码
      let isMob = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 座机格式
      if (!(value === null || value === undefined || value === '')) {
        if (isMob.test(value) || isPhone.test(value)) {
          callback()
        } else {
          callback(new Error('非有效电话号码！'))
        }
      } else {
        callback()
      }
    }
    return {
      rules: {
        personName: [{ required: true, message: '请输入人员姓名', trigger: 'blur' }],
        phoneNumber: [
          { required: true, message: '请输入人员电话', trigger: 'blur' },
          { validator: phoneCheck, trigger: 'blur' }
        ],
        contactOfficePhone: [
          { required: true, message: '请输入联络员办公电话', trigger: 'blur' },
          { validator: phoneCheck, trigger: 'blur' }
        ],
        contactCellPhone: [
          { required: true, message: '请输入联络员手机电话', trigger: 'blur' },
          { validator: phoneCheck, trigger: 'blur' }
        ]
      },
      loading: false,
      form: {
        groupId: '',
        planNumId: '',
        personName: '',
        post: '',
        phoneNumber: '',
        office: '',
        duty: '',
        contactCellPhone: '', // 联络员手机电话
        contactDuty: '', // 联络员职务
        contactOfficePhone: '', // 联络员办公电话
        contactPerson: '' // 联络员
      }
    }
  },
  methods: {
    handleClose() {
      this.$refs.formLabel.resetFields()
      this.$emit('update:dialogVisible', false)
      this.reset()
    },
    reset() {
      this.loading = false
      this.form = {
        groupId: '',
        planNumId: '',
        personName: '',
        post: '',
        phoneNumber: '',
        office: '',
        duty: '',
        contactCellPhone: '', // 联络员手机电话
        contactDuty: '', // 联络员职务
        contactOfficePhone: '', // 联络员办公电话
        contactPerson: '' // 联络员
      }
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      // console.log('this.form', this.form)
      if (this.planGroupId) {
        this.form.groupId = this.planGroupId
      }
      if (this.planId) {
        this.form.planNumId = this.planId
      }
      // console.log('this.form', this.form)
      let params = []
      params.push(this.form)
      let url = '/gtw/EIMS-management/add-plan-group-person'
      // console.log('参数', params)
      Axios.post(url, params).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('工作组成员信息新增成功！')
          this.$emit('success', this.planGroupId)
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.success('工作组成员信息新增失败！')
        }
      })
    }
  }
}
</script>
<style lang="scss">
</style>
