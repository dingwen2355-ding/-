<template>
  <div class="navi" id="navi">
    <!-- <img src="../../assets/login/loginright.png" alt="" class="loginright" /> -->
    <div class="loginleft"></div>
    <div class="loginright"></div>
    <div class="leftBox">
      <div class="leftBox_title">{{ loginTitle }}智慧蓉城运行中心</div>
    </div>
    <div class="loginContent">
      <div class="login_div">
        <el-form ref="form" :model="form" class="form" :rules="rules2" style="margin-top: 85rem">
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
import moment from 'moment'
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
    }
    window.onresize = function () {
      this.resize()
    }.bind(this)
  },
  methods: {
    resize() {
      // 系统整体缩放
      let cliWidth = document.documentElement.clientWidth || document.body.clientWidth
      let cliHeight = document.documentElement.clientHeight || document.body.clientHeight
      let contW = 3840
      let contH = 1080
      this.$store.dispatch('app/setRatio', contW)
      let w = cliWidth / contW
      let h = cliHeight / contH
      this.$store.dispatch('view/setResize', [w, h])
      let top = (cliHeight - 1080) / 2
      this.$nextTick(() => {
        $('#navi')
          // .css('transform', 'scale(' + w + ',' + h + ')')
          // .css('transformOrigin', 'top left')
          .width(contW)
          .height(contH)
          .css('top', top > 0 ? top : 0)
      })
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
          sessionStorage.accessToken = ''
          sessionStorage.userId = ''
          sessionStorage.name = ''
          sessionStorage.pass = ''
          sessionStorage.trueName = ''
          Axios.post(url).then((r) => {
            if (r.data.data && r.data.data.code === 200) {
              sessionStorage.isLogin = 1
              // let data = r.data.data.data.precinctId
              let data = title
              sessionStorage.precinct = data
              sessionStorage.name = this.form.username
              sessionStorage.pass = this.form.password
              let urls = '/apis/saveLog'
              if (r.data.data.data) {
                sessionStorage.trueName = r.data.data.data.username
              }
              let obj = {
                area: '登录',
                name: sessionStorage.name,
                precinctId: title,
                state: '登录',
                time: moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
              }
              Axios.post(urls, obj)
              if (data) {
                this.$router.push('/?precinctId=' + data)
              } else {
                this.$router.push('/')
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
  background: url('../../assets/login/login_bg.png') no-repeat;
  background-size: contain;
  .loginContent {
    background-image: url('../../assets/login/background.png');
    width: 800rem;
    height: 700rem;
    animation: myAnimationBack 2s steps(1) infinite;
    // background-size: contain;
    position: absolute;
    top: 15%;
    left: 39.5%;
    @keyframes myAnimationBack {
      0% {
        background-position: 0rem 0rem;
      }
      6.25% {
        background-position: -800rem 0rem;
      }
      12.50% {
        background-position: -1600rem 0rem;
      }
      18.75% {
        background-position: -2400rem 0rem;
      }
      25.00% {
        background-position: -3200rem 0rem;
      }
      31.25% {
        background-position: -4000rem 0rem;
      }
      37.50% {
        background-position: 0rem -700rem;
      }
      43.75% {
        background-position: -800rem -700rem;
      }
      50.00% {
        background-position: -1600rem -700rem;
      }
      56.25% {
        background-position: -2400rem -700rem;
      }
      62.50% {
        background-position: -3200rem -700rem;
      }
      68.75% {
        background-position: -4000rem -700rem;
      }
      75.00% {
        background-position: 0rem -1400rem;
      }
      81.25% {
        background-position: -800rem -1400rem;
      }
      87.50% {
        background-position: -1600rem -1400rem;
      }
      93.75% {
        background-position: -2400rem -1400rem;
      }
      100.00% {
        background-position: -3200rem -1400rem;
      }
    }

    .login_div {
      display: flex;
      justify-content: center;
      width: 700rem;
      right: 250rem;
      height: 570rem;
      top: 270rem;
      margin: -15rem auto;
      .formtitle {
        font-size: 20rem;
        margin-bottom: 10rem;
      }
      .logoimgdiv {
        width: 125rem;
      }
      .iconfont {
        font-size: 28rem;
        left: 22rem;
        top: 6rem;
        position: absolute;
        background: linear-gradient(2deg, #0071ff 0%, #00b2ff 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
      }
      .logoimgdiv {
        height: 115rem;
        margin: 42rem auto 22rem auto;
      }
      .el-input {
        font-size: 14rem;
        -webkit-input-placeholder {
          opacity: 0.5;
        }

        .el-input__inner {
          width: 563rem;
          padding: 0rem 64rem;
          height: 51rem;
          font-size: 24rem;
          border: 0;
          color: #00b2ff;
          font-weight: 400;
        }
        .el-input__inner::-webkit-input-placeholder {
          opacity: 0.5;
        }
      }
      .el-form-item {
        margin-top: 22rem;
      }
      .el-form-item__content {
        line-height: 40rem;
        position: relative;
        font-size: 14rem;
        display: flex;
      }
      #yanzheng {
        width: 293rem;
        padding: 0rem 10rem;
        height: 54rem;
        color: #00b2ff;
      }
      #loginbtn {
        width: 563rem;
        height: 46rem;
        font-size: 30rem;
        background: #00a1ff;
        color: #ffffff;
        font-family: Microsoft YaHei UI;
        font-weight: 400;
        color: #ffffff;
        line-height: 24rem;
        text-align: center;
      }
      .download {
        margin-left: 150rem;
      }
      .el-checkbox__inner {
        width: 20rem;
        height: 20rem;
        background: #0d245b;
        margin-bottom: 1rem;
        border: 1rem solid #1f5e97;
      }
      .el-checkbox__label {
        font-size: 24rem;
        color: #ffffff;
        font-family: Microsoft YaHei UI;
        font-weight: 400;
      }
      .el-checkbox {
        font-size: 24rem;
        color: #ffffff;
      }
      .el-checkbox__inner::after {
        height: 14rem;
        left: 8rem;
        top: 0rem;
      }
    }
  }
  .loginleft {
    // -webkit-backface-visibility: hidden;
    // -webkit-transform-style: preserve-3d;
    position: absolute;
    width: 300rem;
    height: 250rem;
    animation: myAnimationSync 0.72s steps(1) infinite;
    top: 785rem;
    left: 748rem;
    background-image: url('../../assets/login/loginht.png');
    @keyframes myAnimationSync {
      0% {
        background-position: 0rem 0rem;
      }
      5.88% {
        background-position: -300rem 0rem;
      }
      11.76% {
        background-position: -600rem 0rem;
      }
      17.65% {
        background-position: -900rem 0rem;
      }
      23.53% {
        background-position: -1200rem 0rem;
      }
      29.41% {
        background-position: -1500rem 0rem;
      }
      35.29% {
        background-position: -1800rem 0rem;
      }
      41.18% {
        background-position: -2100rem 0rem;
      }
      47.06% {
        background-position: -2400rem 0rem;
      }
      52.94% {
        background-position: -2700rem 0rem;
      }
      58.82% {
        background-position: -3000rem 0rem;
      }
      64.71% {
        background-position: -3300rem 0rem;
      }
      70.59% {
        background-position: -3600rem 0rem;
      }
      76.47% {
        background-position: -3900rem 0rem;
      }
      82.35% {
        background-position: -4200rem 0rem;
      }
      88.24% {
        background-position: -4500rem 0rem;
      }
      94.12% {
        background-position: 0rem -250rem;
      }
      100.00% {
        background-position: -300rem -250rem;
      }
    }
  }
  .loginright {
    background-image: url('../../assets/login/add.png');
    position: absolute;
    top: 791rem;
    left: 2816rem;
    width: 300rem;
    height: 250rem;
    animation: myAnimation 4.32s steps(1) infinite;
    @keyframes myAnimation {
      0% {
        background-position: 0rem 0rem;
      }
      1.89% {
        background-position: -300rem 0rem;
      }
      3.77% {
        background-position: -600rem 0rem;
      }
      5.66% {
        background-position: -900rem 0rem;
      }
      7.55% {
        background-position: -1200rem 0rem;
      }
      9.43% {
        background-position: -1500rem 0rem;
      }
      11.32% {
        background-position: -1800rem 0rem;
      }
      13.21% {
        background-position: -2100rem 0rem;
      }
      15.09% {
        background-position: -2400rem 0rem;
      }
      16.98% {
        background-position: -2700rem 0rem;
      }
      18.87% {
        background-position: -3000rem 0rem;
      }
      20.75% {
        background-position: -3300rem 0rem;
      }
      22.64% {
        background-position: -3600rem 0rem;
      }
      24.53% {
        background-position: -3900rem 0rem;
      }
      26.42% {
        background-position: -4200rem 0rem;
      }
      28.30% {
        background-position: -4500rem 0rem;
      }
      30.19% {
        background-position: 0rem -250rem;
      }
      32.08% {
        background-position: -300rem -250rem;
      }
      33.96% {
        background-position: -600rem -250rem;
      }
      35.85% {
        background-position: -900rem -250rem;
      }
      37.74% {
        background-position: -1200rem -250rem;
      }
      39.62% {
        background-position: -1500rem -250rem;
      }
      41.51% {
        background-position: -1800rem -250rem;
      }
      43.40% {
        background-position: -2100rem -250rem;
      }
      45.28% {
        background-position: -2400rem -250rem;
      }
      47.17% {
        background-position: -2700rem -250rem;
      }
      49.06% {
        background-position: -3000rem -250rem;
      }
      50.94% {
        background-position: -3300rem -250rem;
      }
      52.83% {
        background-position: -3600rem -250rem;
      }
      54.72% {
        background-position: -3900rem -250rem;
      }
      56.60% {
        background-position: -4200rem -250rem;
      }
      58.49% {
        background-position: -4500rem -250rem;
      }
      60.38% {
        background-position: 0rem -500rem;
      }
      62.26% {
        background-position: -300rem -500rem;
      }
      64.15% {
        background-position: -600rem -500rem;
      }
      66.04% {
        background-position: -900rem -500rem;
      }
      67.92% {
        background-position: -1200rem -500rem;
      }
      69.81% {
        background-position: -1500rem -500rem;
      }
      71.70% {
        background-position: -1800rem -500rem;
      }
      73.58% {
        background-position: -2100rem -500rem;
      }
      75.47% {
        background-position: -2400rem -500rem;
      }
      77.36% {
        background-position: -2700rem -500rem;
      }
      79.25% {
        background-position: -3000rem -500rem;
      }
      81.13% {
        background-position: -3300rem -500rem;
      }
      83.02% {
        background-position: -3600rem -500rem;
      }
      84.91% {
        background-position: -3900rem -500rem;
      }
      86.79% {
        background-position: -4200rem -500rem;
      }
      88.68% {
        background-position: -4500rem -500rem;
      }
      90.57% {
        background-position: 0rem -750rem;
      }
      92.45% {
        background-position: -300rem -750rem;
      }
      94.34% {
        background-position: -600rem -750rem;
      }
      96.23% {
        background-position: -900rem -750rem;
      }
      98.11% {
        background-position: -1200rem -750rem;
      }
      100.00% {
        background-position: -1500rem -750rem;
      }
    }
  }

  .leftBox {
    float: left;
    width: 1162rem;
    height: 265rem;
    background: url('../../assets/login/title_bg.png') no-repeat;
    background-size: contain;
    .leftBox_title {
      line-height: 60rem;
      margin: 33rem 0rem 0rem 99rem;
      font-size: 55rem;
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
