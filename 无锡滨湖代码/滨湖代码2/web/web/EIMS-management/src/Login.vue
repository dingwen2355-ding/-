<template>
  <div id="login-page" class="login-page">
    <div class="title-div">
      <img src="./assets/login/logo.png" alt="" />
      <span class="title">{{ title }}</span>
    </div>
    <div class="demo-ruleForm login-container">
      <div class="form-title">
        <span>请登录</span>
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
        <el-form-item prop="checkPass">
          <el-input
            id="yanzheng"
            type="text"
            v-model="ruleForm.yzm"
            placeholder="请输入验证码"
            @keyup.enter.native="login"
            style="width: 293px; margin-right: 49px"
          ></el-input>
          <div class="code" @click="refreshCode">
            <s-identify :identifyCode="identifyCode"></s-identify>
          </div>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rememberPassword" @change="rememberPasswordChange">记住密码</el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click.native.prevent="login">登录 </el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- <div class="login-erweima">
      <div class="item">
        <span>手机app</span>
        <img src="http://39.75.167.141:8090/app/files/qrcode/appIcon.png" alt="" srcset="" />
      </div>
      <div class="item">
        <span>物资装备组</span>
        <img src="http://39.75.167.141:8090/app/files/qrcode/supplieIcon.png" alt="" srcset="" />
      </div>
    </div> -->
  </div>
</template>

<script>
import $ from 'jquery'
import { Encrypt } from './utils/crypto'
import axios from 'axios'
import Identify from './Identify'

export default {
  name: 'Login',
  components: {
    's-identify': Identify
  },
  data() {
    return {
      checked: true,
      title: configs.beforeTitle + configs.afterTitle,
      ruleForm: { userName: '', password: '', yzm: '' },
      logining: false,
      rules: {
        userName: [{ required: true, message: '请输入账号名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        checkPass: [{ validator: this.validatePass, trigger: 'blur' }]
      },
      msg: '',
      precinctName: this.$store.getters.precinctName,
      identifyCodes: '0123456789',
      identifyCode: '',
      rememberPassword: localStorage.rememberPassword === 'true'
    }
  },
  methods: {
    resize: function () {
      let contW = 1920
      let contH = 1080
      let cliWidth = document.documentElement.clientWidth || document.body.clientWidth
      let cliHeight = document.documentElement.clientHeight || document.body.clientHeight
      let w = cliWidth / contW
      let h = cliHeight / contH
      sessionStorage.transformW = w
      sessionStorage.transformH = h
      this.$store.dispatch('view/setResize', [w, h])
      $('#login-page')
        .css('transform', 'scale(' + w + ',' + h + ')')
        .css('transformOrigin', 'top left')
        .css('overflow', 'hidden')
        .width(contW)
        .height(contH)
    },
    login() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.loginRepet()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 验证后 登录信息
    loginRepet() {
      this.logining = true
      this.ruleForm.password = Encrypt(this.ruleForm.password)
      axios
        .post('/gtw/EIMS-management/login', this.ruleForm)
        .then((res) => {
          this.logining = false
          let { result, data, remark } = res.data
          if (result === 0) {
            data = data[0]
            let modules = data.userMessage.modules
            if (!modules || !Array.isArray(modules) || modules.length === 0) {
              this.$message.error('您的账号没有权限访问此系统，请联系管理员')
            } else {
              localStorage.setItem('token', data.token)
              localStorage.setItem('roleId', data.userMessage.roleId)
              localStorage.setItem('user', this.ruleForm.userName)
              localStorage.setItem('departmentId', data.userMessage.precinctTreeId)
              localStorage.setItem('streetId', data.userMessage.streetId)
              localStorage.setItem('streetName', data.userMessage.streetName)
              localStorage.setItem('roleName', data.userMessage.roleName)
              localStorage.setItem('newDepartmentId', data.userMessage.precinctTreeId)
              localStorage.setItem('departmentName', data.userMessage.departmentName)
              localStorage.setItem('userId', data.userMessage.userId)
              localStorage.setItem('region', data.userMessage.region)
              localStorage.setItem('menu', JSON.stringify(modules))
              localStorage.setItem('level', data.userMessage.roles[0].level)
              localStorage.setItem('userLevel', data.userMessage.userLevel)
              localStorage.setItem('stockId', data.userMessage.stockId)
              let menuIds = []
              modules.forEach((v) => {
                menuIds.push(v.id)
              })
              if (this.ruleForm.userName === 'admin') {
                menuIds = menuIds.concat('qianduan-1', 'qianduan-2', 'qianduan-3', 'qianduan-4')
              }
              localStorage.setItem('menuIds', JSON.stringify(menuIds))
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
              localStorage.setItem('menuList', JSON.stringify(configs.menuList))
              // 根据菜单权限跳转
              if (firstMenu) {
                this.$router.push(firstMenu.path)
              } else {
                this.$message.error('您无此系统权限，请联系管理员')
              }
            }
          } else {
            this.msg = remark
            this.refreshCode()
          }
        })
        .catch((error) => {
          console.log(error)
          this.refreshCode()
        })
        .finally((r) => {})
    },
    initForm() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
      if (this.rememberPassword) {
        this.ruleForm.userName = localStorage.getItem('userName')
        this.ruleForm.password = localStorage.getItem('password')
      }
    },
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode(o, l) {
      for (var i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
    },
    validatePass(rule, value, callback) {
      if (this.ruleForm.yzm === '') {
        callback(new Error('请输入验证码'))
      } else if (this.ruleForm.yzm !== this.identifyCode) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
    rememberPasswordChange(v) {
      if (v) {
        localStorage.setItem('userName', this.ruleForm.userName)
        localStorage.setItem('password', this.ruleForm.password)
        localStorage.setItem('rememberPassword', v)
      } else {
        localStorage.setItem('userName', '')
        localStorage.setItem('password', '')
        localStorage.setItem('rememberPassword', v)
      }
    }
  },
  mounted() {
    document.title = configs.beforeTitle + configs.afterTitle
    this.initForm()
    this.resize()
  }
}
</script>

<style lang="scss">
#login-page {
  width: 100%;
  height: 100%;
  background: url('./assets/login/login_bg.png') no-repeat;
  background-size: 100% 100%;
  &::before {
    content: '';
    background: url('./assets/login/animate.gif') no-repeat;
    background-size: 100% 100%;
    display: block;
    width: 695px;
    height: 633px;
    position: absolute;
    top: 176px;
    left: 103px;
  }

  .title-div {
    width: 652px;
    height: 91px;
    position: absolute;
    top: 147px;
    left: 1031px;
  }

  .title-div * {
    vertical-align: middle;
  }

  .title-img {
    width: 65px;
    height: 65px;
  }

  .title {
    font-size: 48px;
    font-family: Microsoft YaHei;
    font-weight: bold;
    color: #ffffff;

    background: linear-gradient(0deg, #b8d4ff 0%, #ffffff 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    padding-left: 36px;
    vertical-align: middle;
  }

  .login-container {
    width: 793px;
    height: 639px;
    background: url('./assets/login/loginWindowBG.png') no-repeat;
    background-size: 100% 100%;
    position: absolute;
    top: 244px;
    left: 997px;

    .el-form-item__content {
      width: 563px;
      height: 54px;
      margin: 0 auto;
      font-size: 16px;
      font-family: PingFangSC-Regular;
      font-weight: bold;
      color: rgba(193, 193, 193, 1);
      display: flex;
      .el-input__inner {
        background: rgba(0, 161, 255, 0.2);
        border: 1px solid #1f5e97;
        border-radius: 4px;
        color: #fff;
        height: 54px;
        font-size: 24px;
      }
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
      margin-bottom: 36px;
      &:nth-of-type(5) {
        margin-bottom: 0px;
      }
    }
  }
  .login-erweima {
    position: absolute;
    top: 880px;
    left: 1000px;
    display: flex;
    color: #fff;
    font-size: 18px;
    text-align: center;
    .item {
      width: 150px;
      margin-left: 20px;
      img {
        margin-top: 10px;
        width: 150px;
        height: 150px;
      }
    }
  }

  .msg-box {
    height: 24px;
    font-size: 24px;
    font-family: PingFangSC-Regular;
    font-weight: bold;
    color: #f56c6c;
    margin: 0px 0 20px 118px;
  }

  .form-title {
    font-size: 48px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
    margin-top: 67px;
    margin-left: 118px;
  }
  .el-form-item__error {
    font-size: 24px;
  }
  .el-checkbox__inner {
    width: 20px;
    height: 20px;
    background: #0d245b;
    border: 1px solid #1f5e97;
    border-radius: 2px;
    line-height: 24px;
  }
  .el-checkbox__input {
    line-height: 42px;
  }
  .el-checkbox__label {
    font-size: 24px;
    font-family: Microsoft YaHei UI;
    font-weight: 400;
    color: #ffffff;
    padding-left: 20px;
  }
  .el-checkbox__inner::after {
    left: 7px;
    top: 4px;
  }
}
</style>
