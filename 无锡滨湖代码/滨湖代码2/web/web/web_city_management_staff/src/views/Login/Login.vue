<template>
  <div class="navi" id="navi">
    <video width="100%" autoplay loop muted id="videoPlay" src="../../assets/login/bg_v2.mp4"></video>
    <div class="loginContent">
      <div class="login_div">
       <div class="leftBox">
       <div class="leftBox_title">{{ loginTitle }}一网统管-融合指挥数据管理平台</div>
        </div>
        <el-form ref="form" :model="form" class="form" :rules="rules2" style="margin-top: 290px">
          <el-form-item prop="username" :rules="[{ required: true, message: '用户名不能为空' }]">
            <!-- <img src="../../assets/login/1.png" class="logo_img" /> -->
            <span class="icon iconfont icon-denglu_zhanghao"></span>
            <el-input type="username" placeholder="请输入用户名" v-model="form.username"></el-input>
          </el-form-item>
          <el-form-item prop="password" :rules="[{ required: true, message: '密码不能为空' }]">
            <!-- <img src="../../assets/login/2.png" class="logo_img" /> -->
            <span class="icon iconfont icon-denglu_mima"></span>
            <el-input type="password" placeholder="请输入密码" v-model="form.password"></el-input>
            <div class="el-form-item__error" v-if="loginFailed">{{ loginFailedMsg }}</div>
          </el-form-item>

          <el-form-item prop="checkPass">
            <el-input id="yanzheng" type="text" v-model="form.yzm"></el-input>
            <div class="code" @click="refreshCode">
              <s-identify :identifyCode="identifyCode"></s-identify>
            </div>
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="isRemeberPwd">记住密码</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button id="loginbtn" @click="login">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import identify from './Identify.vue'
import Axios from '@/utils/request'
export default {
  components: {
    's-identify': identify
  },
  data() {
    return {
      loginFailed: false,
      loginTitle: '成都市',
      identifyCodes: '0123456789',
      identifyCode: '',
      form: {
        username: '',
        password: '',
        yzm: ''
      },
      rules2: {
        checkPass: [{ validator: this.validatePass, trigger: 'blur' }],
        username: [{ validator: this.validateUser, trigger: 'blur' }]
      },
      isRemeberPwd: false
    }
  },
  created() {
    this.refreshCode()
    this.resize()
    if (sessionStorage.precinctId) {
      this.$router.push('/Login?precinctId=' + sessionStorage.precinctId)
      sysConfig.area.forEach(item => {
        if (item.id === sessionStorage.precinctId) {
          this.loginTitle = item.name
        }
      })
      sessionStorage.precinctId = ''
    } else {
      sysConfig.area.forEach(item => {
        if (item.id === this.$route.query.precinctId) {
          this.loginTitle = item.name
        }
      })
    }
    window.onresize = function () {
      this.resize()
    }.bind(this)
  },
  methods: {
    resize() {
      // 系统整体缩放
      // let cliWidth = document.documentElement.clientWidth || document.body.clientWidth
      // let cliHeight = document.documentElement.clientHeight || document.body.clientHeight
      // let contW = cliWidth > 1920 ? 3840 : 1920
      // let contH = cliHeight > 1080 ? 2160 : 1080
      // this.$store.dispatch('app/setRatio', contW)
      // let w = cliWidth / contW
      // let h = cliHeight / contH
      // this.$store.dispatch('view/setResize', [w, h])
      // // let top = (cliHeight - 1080) / 2
      // this.$nextTick(() => {
      //   $('#navi')
      //     .css('transform', 'scale(' + w + ',' + h + ')')
      //     .css('transformOrigin', 'top left')
      //     .width(contW)
      //     .height(contH)
      //     .css('top', 0)
      // })
      if (sessionStorage.getItem('checkTrue')) {
        sessionStorage.removeItem('checkTrue')
      }
    },
    initForm() {
      this.identifyCode = ''
      this.makeCode(self.identifyCodes, 4)
      this.$store.dispatch('user/setName', '')
      let userInfo = localStorage.userInfo
      if (localStorage.isRemeberPwd === '1') {
        if (userInfo) {
          let userParam = JSON.parse(userInfo)
          this.form.username = userParam.userName
          this.form.password = userParam.password
          this.oldUserInfo = userParam
        }
        this.isRemeberPwd = true
      }
    },
    login() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          let url = '/apis/sso/login?authType=password&loginId=' + this.form.username + '&auth=' + this.form.password
          let title = ''
          if (this.loginTitle !== '成都市') {
            sysConfig.area.forEach(item => {
              if (item.name === this.loginTitle) {
                title = item.id
              }
            })
          }
          if (title) {
            url += '&precinctId=' + title
          }
          sessionStorage.precinct = ''
          sessionStorage.name = ''
          sessionStorage.pass = ''
          Axios.post(url).then((r) => {
            if (r.data.data && r.data.data.code === 200) {
              let data = title
              sessionStorage.precinct = data
              sessionStorage.name = this.form.username
              sessionStorage.pass = this.form.password
              window.name = '大屏跳转'
              if (data) {
                if (this.$route.query.eventId) {
                  this.$router.push('/ChengDuCityManagementStaff?precinctId=' + data + '&eventId=' + this.$route.query.eventId)
                } else {
                  this.$router.push('/ChengDuCityManagementStaff?precinctId=' + data)
                }
              } else {
                if (this.$route.query.eventId) {
                  this.$router.push('/ChengDuCityManagementStaff?eventId=' + this.$route.query.eventId)
                } else {
                  this.$router.push('/ChengDuCityManagementStaff')
                }
              }
              this.$message.success('登录成功')
            } else {
              this.refreshCode()
              this.$message.warning('用户名或密码错误')
            }
          })
        }
      })
    },
    setPrecinctName(streetId, streetName, gridId, regionName) {
      localStorage.setItem('precinctName', streetName)
      localStorage.setItem('precinctId', streetId)
      localStorage.setItem('gridId', gridId)
      localStorage.setItem('regionName', regionName)
      this.$store.dispatch('app/setPrecinctName', streetName)
      this.$store.dispatch('app/setPrecinctId', streetId)
      this.$store.dispatch('app/setGridId', gridId)
      this.$store.dispatch('app/setRegionName', regionName)
    },
    download() {
      window.open('download/ZTENetviewVideoPlugin.exe')
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode(o, l) {
      for (var i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
      this.form.yzm = this.identifyCode
    },
    forgetPsd() {},
    validatePass(rule, value, callback) {
      if (this.form.yzm === '') {
        callback(new Error('请输入验证码'))
      } else if (this.form.yzm !== this.identifyCode) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    },
    validateUser(rule, value, callback) {
      if (value === '') {
        callback(new Error('用户不能为空'))
      }
      var regEn = /[`~!@#$%^&*()_+<>?:"{},./;'[\]]/im
      var regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im
      if (regEn.test(value) || regCn.test(value)) {
        callback(new Error('请不要输入特殊字符'))
      } else {
        callback()
      }
      // return rs;
    }
  }
}
</script>

<style lang="scss">
.navi {
  // display: flex;
  position: relative;
  width: 100%;
  height: 100%;
  // background: url('../../assets/login/bar_bg.png') no-repeat top left;
  top: 0 !important;
  bottom: 0 !important;
  left: 0 !important;
  right: 0 !important;
  margin: auto !important;
  #videoPlay {
    position: absolute;
    right: 0;
    bottom: 0;
    min-width: 100%;
    min-height: 100%;
    width: auto;
    height: auto;
    object-fit: fill;
    overflow: hidden;
  }
  .loginContent {
    background: url('../../assets/login/bar_bg.png') no-repeat top left;
    background-size: 100% 100%;
    width: 1300px;
    height: 1000px;
    position: absolute;
    top: 30%;
    left: 33.5%;

    .login_div {
      display: flex;
      justify-content: center;
      width: 700px;
      right: 250px;
      height: 570px;
      top: 270px;
      margin: -15px auto;
      .formtitle {
        font-size: 20px;
        margin-bottom: 10px;
      }
      .logoimgdiv {
        width: 125px;
      }
      .iconfont {
        font-size: 28px;
        left: 22px;
        top: 6px;
        position: absolute;
        background: linear-gradient(2deg, #0071ff 0%, #00b2ff 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
      }
      .logoimgdiv {
        height: 115px;
        margin: 42px auto 22px auto;
      }
      .el-input {
        font-size: 14px;

        .el-input__inner {
        width: 800px;
        padding: 0px 14px 0px 50px;
        height: 80px;
        font-size: 24px;
        color: #fff;
        background: rgba(0, 0, 0, 0.2);
        border-radius: 24px;
        -webkit-border-radius: 24px;
        -moz-border-radius: 24px;
        -ms-border-radius: 24px;
        -o-border-radius: 24px;
        border: 1px solid #3573b2;
      }
      .el-input__inner:-internal-autofill-previewed {
        -webkit-text-fill-color: #fff;
        transition: background-color 5000s ease-out 0.5s;
        -webkit-transition: background-color 5000s ease-out 0.5s;
        -moz-transition: background-color 5000s ease-out 0.5s;
        -ms-transition: background-color 5000s ease-out 0.5s;
        -o-transition: background-color 5000s ease-out 0.5s;
      }

      .el-input__inner:-internal-autofill-selected {
        -webkit-text-fill-color: #fff;
        transition: background-color 5000s ease-out 0.5s;
        -webkit-transition: background-color 5000s ease-out 0.5s;
        -moz-transition: background-color 5000s ease-out 0.5s;
        -ms-transition: background-color 5000s ease-out 0.5s;
        -o-transition: background-color 5000s ease-out 0.5s;
      }
      }
      .el-form-item {
        margin-top: 22px;
      }
      .el-form-item__content {
        line-height: 40px;
        position: relative;
        font-size: 14px;
        display: flex;
      }
      #yanzheng {
        width: 560px;
        padding: 0px 10px;
        height: 80px;
        color: #00b2ff;
      }
      #loginbtn {
        width: 800px;
        height: 62px;
        font-size: 30px;
        background: #00a1ff;
        color: #ffffff;
        font-family: Microsoft YaHei UI;
        font-weight: 400;
        color: #ffffff;
        line-height: 24px;
        text-align: center;
      }
      .download {
        margin-left: 150px;
      }
      .el-checkbox__inner {
        width: 30px;
        height: 30px;
        background: #0d245b;
        margin-bottom: 1px;
        border: 1px solid #1f5e97;
      }
      .el-checkbox__label {
        font-size: 24px;
        color: #ffffff;
        font-family: Microsoft YaHei UI;
        font-weight: 400;
      }
      .el-checkbox {
        font-size: 24px;
        color: #ffffff;
      }
      .el-checkbox__inner::after {
        height: 14px;
        left: 8px;
        top: 0px;
      }
    }
  }

  .leftBox {
    position: absolute;
    top: 155px;
    .leftBox_title {
      font-size: 50px;
      white-space: nowrap;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #ffffff;
      background: linear-gradient(0deg, #ffffff 0.146484375%, #5a86bc 99.0478515625%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
  }
}
#navi {
  font-family: 'Microsoft YaHei', 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #fff;
  text-align: left;
  position: relative;
  top: 50%;
}
</style>
