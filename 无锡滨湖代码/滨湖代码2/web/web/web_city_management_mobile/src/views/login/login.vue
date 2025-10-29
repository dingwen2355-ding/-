<template>
  <div class="login-page">
    <van-form @submit="onSubmit">
      <van-field
        v-model="username"
        name="用户名"
        label="用户名"
        placeholder="用户名"
        :rules="[{ required: true, message: '请填写用户名' }]"
      />
      <van-field
        v-model="password"
        type="password"
        name="密码"
        label="密码"
        placeholder="密码"
        :rules="[{ required: true, message: '请填写密码' }]"
      />
      <van-button round block type="info" native-type="submit" style="margin-top: 30rem">提交</van-button>
    </van-form>
  </div>
</template>

<script>
import { Encrypt } from '../../utils/crypto'
import axios from '@/utils/request'
export default {
  name: 'login',
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    onSubmit(values) {
      axios
        .post('/auth/user/login', {
          userName: this.username,
          password: Encrypt(this.password)
        })
        .then((res) => {
          let { result, data, remark } = res.data
          if (result === 0) {
            data = data[0]
            sessionStorage.setItem('token', data.token)
            sessionStorage.setItem('roleId', data.userMessage.roleId)
            sessionStorage.setItem('user', data.userMessage.trueName)
            sessionStorage.setItem('phone', data.userMessage.phone)
            sessionStorage.setItem('precinctTreeId', data.userMessage.precinctTreeId)
            sessionStorage.setItem('streetId', data.userMessage.streetId)
            sessionStorage.setItem('streetName', data.userMessage.streetName)
            sessionStorage.setItem('roleName', data.userMessage.roleName)
            if (data.userMessage.departmentName) {
              sessionStorage.setItem('departmentName', data.userMessage.departmentName)
              sessionStorage.setItem('departmentId', data.userMessage.departmentId)
            } else {
              sessionStorage.setItem('departmentName', '')
              sessionStorage.setItem('departmentId', '')
            }
            sessionStorage.setItem('userId', data.userMessage.userId)
            sessionStorage.setItem('userName', data.userMessage.userName)
            this.$router.push('/')
          } else {
            this.$notify(remark)
          }
        })
        .catch((error) => {
          console.log(error)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  padding: 100rem 30rem 0 30rem;
}
</style>
