<template>
  <el-dialog
    title="修改密码"
    :visible.sync="dialogPasswordVisible"
    :modal-append-to-body="false"
    :center="dialogCenter"
    :before-close="close"
    :close-on-click-modal="false"
    width="750px"
  >
    <el-form :model="pform" status-icon :rules="pwdrules" ref="pform" label-width="120px">
      <!-- <el-form-item label="旧密码" prop="oldpassword">
        <el-input v-model="pform.oldpassword" placeholder="旧密码" auto-complete="off" type="password"></el-input>
      </el-form-item>-->
      <el-form-item label="新密码" prop="password">
        <el-input v-model="pform.password" placeholder="新密码" auto-complete="off" type="password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input v-model="pform.checkPass" placeholder="再次输入新密码" type="password" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="savePwd">确定</el-button>
      <el-button type="primary" plain @click="close">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { Encrypt } from '../../utils/crypto'
import axios from '@/utils/request'
export default {
  props: ['dialogPasswordVisible'],
  data() {
    var self = this
    const passwordCheck = (rule, value, callback) => {
      var passwordReg =
        /^(((?=.*[0-9])(?=.*[a-zA-Z])|(?=.*[0-9])(?=.*[^\s0-9a-zA-Z])|(?=.*[a-zA-Z])(?=.*[^\s0-9a-zA-Z]))[^\s]+)$/
      if (!passwordReg.test(value)) {
        callback(new Error('密码至少含有字母、数字、特殊字符中的两种'))
      } else {
        callback()
      }
    }

    var validatePass = function (rule, value, callback) {
      self.$refs.pform.validateField('checkPass')
      callback()
    }
    var validatePass2 = function (rule, value, callback) {
      if (value !== self.pform.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      dialogCenter: false,
      pform: { password: '', checkPass: '' }, // 重置密码
      pwdrules: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码为至少8位数', trigger: 'blur' },
          { validator: passwordCheck, trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    dialogPasswordVisible(v) {
      if (v) {
        this.pform = { password: '', checkPass: '' }
      }
    }
  },
  methods: {
    savePwd() {
      this.$refs['pform'].validate((valid) => {
        if (valid) {
          var url =
            '/auth/user/changePassword?type=reset&newPassword=' +
            Encrypt(this.pform.password) +
            '&userName=' +
            sessionStorage.getItem('userName') +
            '&userId=' +
            sessionStorage.getItem('userId')
          axios.put(url).then((r) => {
            if (r.data.result === 0) {
              this.close()
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 1000
              })
            } else {
              this.$notify.error({
                title: '错误',
                message: '修改失败',
                duration: 1000
              })
            }
          })
        }
      })
    },
    close() {
      this.$emit('update:dialogPasswordVisible', false)
    }
  }
}
</script>

<style></style>
