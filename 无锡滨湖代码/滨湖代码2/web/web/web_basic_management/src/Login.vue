<template>
  <div id="login-page" class="login-page">
    <div class="title-div">
      <span class="title">{{ title }}</span>
    </div>
    <div class="demo-ruleForm login-container">
      <div class="form-title">
        <span>账户登录</span>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <div class="msg-box">
          <span>{{ msg }}</span>
        </div>
        <el-form-item prop="userName">
          <el-input type="text" v-model="ruleForm.userName" auto-complete="off" placeholder="请输入账号名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            v-model="ruleForm.password"
            auto-complete="off"
            @keyup.enter.native="login"
            placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click.native.prevent="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { Encrypt } from './utils/crypto'

export default {
  name: 'Login',
  data() {
    return {
      checked: true,
      ruleForm: { userName: '', password: '' },
      logining: false,
      rules: {
        userName: [{ required: true, message: '请输入账号名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      msg: '',
      title: configs.title
    }
  },
  methods: {
    login() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.logining = true
          let obj = JSON.parse(JSON.stringify(this.ruleForm))
          let params = {
            userName: this.ruleForm.userName,
            password: Encrypt(obj.password)
          }
          this.axios
            .post('/auth/user/login', params)
            .then((res) => {
              console.log(this.ruleForm, 'this.ruleForm')
              this.logining = false
              let { result, data, remark } = res.data
              if (result === 0) {
                data = data[0]
                let modules = data.userMessage.modules
                // sessionStorage.setItem('token', data.token)
                sessionStorage.setItem('token', data.token)
                sessionStorage.setItem('roleId', data.userMessage.roleId)
                sessionStorage.setItem('user', data.userMessage.trueName)
                sessionStorage.setItem('phone', data.userMessage.phone)
                sessionStorage.setItem('precinctTreeId', data.userMessage.precinctTreeId)
                sessionStorage.setItem('streetId', data.userMessage.streetId)
                sessionStorage.setItem('streetName', data.userMessage.streetName)
                sessionStorage.setItem('roleName', data.userMessage.roleName)
                // sessionStorage.setItem('newDepartmentId', data.userMessage.precinctTreeId)
                if (data.userMessage.departmentName) {
                  sessionStorage.setItem('departmentName', data.userMessage.departmentName)
                  sessionStorage.setItem('departmentId', data.userMessage.departmentId)
                } else {
                  sessionStorage.setItem('departmentName', '')
                  sessionStorage.setItem('departmentId', '')
                }
                sessionStorage.setItem('departmentId', data.userMessage.departmentId)
                sessionStorage.setItem('userId', data.userMessage.userId)
                sessionStorage.setItem('userName', data.userMessage.userName)
                sessionStorage.setItem('region', data.userMessage.region)
                sessionStorage.setItem('menu', JSON.stringify(modules))
                sessionStorage.setItem('level', data.userMessage.roles[0].level)
                sessionStorage.setItem('userLevel', data.userMessage.userLevel)
                sessionStorage.setItem('stockId', data.userMessage.stockId)
                let menuIds = []
                modules.forEach((v) => {
                  menuIds.push(v.id)
                })
                sessionStorage.setItem('menuIds', JSON.stringify(menuIds))
                // 为菜单设置权限
                let firstMenu // 普通角色登陆后访问的第一个菜单

                configs.menuList.forEach((e) => {
                  e.isShow = false
                  if (e.menus) {
                    let isShow = false
                    e.menus.forEach((se) => {
                      se.isShow = menuIds.includes(se.id)
                      if (se.isShow) {
                        isShow = true
                        if (!firstMenu) {
                          firstMenu = se
                        }
                      }
                    })
                    if (isShow) {
                      e.isShow = true
                    }
                  } else {
                    e.isShow = menuIds.includes(e.id)
                    if (e.isShow && !firstMenu) {
                      firstMenu = e
                    }
                  }
                })
                configs.menuList.some((item) => {
                  item.menus.some((e) => {
                    if (e.isShow) {
                      item.path = e.path
                      return true
                    }
                  })
                })
                sessionStorage.setItem('menuList', JSON.stringify(configs.menuList))
                // 根据菜单权限跳转
                if (firstMenu) {
                  let path = ''
                  let name = ''
                  let v = false
                  configs.menuList.some((e, index) => {
                    if (e.isShow) {
                      e.menus.some(item => {
                        if (item.isShow) {
                          if (item.menus) {
                            path = item.menus[0].path
                            name = item.menus[0].name
                          } else {
                            path = item.path
                          }
                          v = true
                          return true
                        }
                      })
                      return v
                    }
                  })
                  if (name === '') {
                    this.$router.push({ path: path })
                  } else {
                    this.$router.push({ path: path, query: { state: name } })
                  }
                  // console.log(path, '===========================')
                  // console.log(configs.menuList[0])
                } else {
                  this.$message.error('您无此系统权限，请联系管理员')
                }
              } else {
                this.msg = remark
              }
            })
            .catch((error) => {
              console.log(error)
            })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
#login-page {
  width: 100%;
  height: 100%;
  background: url('./assets/login/login_bg.png') no-repeat;
  background-size: 100% 100%;
  .title-div {
    width: 705px;
    height: 91px;
    position: absolute;
    top: 42px;
    left: 34px;
  }

  .title-div * {
    vertical-align: middle;
  }
  .title-img {
    width: 65px;
    height: 65px;
  }
  .title {
    font-size: 37px;
    font-family: MicrosoftYaHei-Bold;
    font-weight: bold;
    color: #fff;
    padding-left: 36px;
  }

  .login-container {
    width: 429px;
    height: 400px;
    background: url('./assets/login/loginWindowBG.png') no-repeat;
    background-size: 100% 100%;
    box-shadow: 0px 8px 8px 0px rgba(166, 167, 205, 0.32);
    position: absolute;
    top: 320px;
    right: 147px;
    .el-form-item__content {
      width: 347px;
      height: 47px;
      margin: 0 auto;
      background: rgba(247, 248, 255, 1);
      font-size: 16px;
      font-family: PingFangSC-Regular;
      font-weight: bold;
      color: rgba(193, 193, 193, 1);
      display: flex;
      vertical-align: center;
    }
    .img-div {
      width: 45px;
      height: 45px;
      background: rgba(222, 231, 255, 1);
      text-align: center;
      vertical-align: middle;
    }
    .img-item {
      width: 18px;
      height: 18px;
      vertical-align: middle;
    }
    .el-button {
      width: 100%;
      height: 100%;
      font-size: 20px;
      background: linear-gradient(0deg, rgba(43, 79, 209, 1), rgba(33, 93, 229, 1));
    }
    .el-form-item {
      margin-bottom: 28px;
    }
    .el-input__inner {
      border: 1px solid rgba(206, 215, 237, 1);
      height: 45px;
      font-size: 16px;
    }
  }

  .msg-box {
    height: 16px;
    font-size: 14px;
    font-family: PingFangSC-Regular;
    font-weight: bold;
    color: #f56c6c;
    margin: 40px 0 20px 40px;
  }

  .form-title {
    font-size: 20px;
    font-family: PingFangSC-Regular;
    font-weight: bold;
    color: #fff;
    margin-top: 41px;
    margin-left: 41px;
  }
}
</style>
