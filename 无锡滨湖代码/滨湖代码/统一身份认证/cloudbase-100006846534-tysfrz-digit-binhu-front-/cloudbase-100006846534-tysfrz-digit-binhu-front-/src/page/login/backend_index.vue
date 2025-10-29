<template>
  <div class="login-container" ref="login" @keyup.enter.native="handleLogin">
    <top-color v-show="false"></top-color>
    <div class="system-title">
      {{ websitConfig.sysName ? websitConfig.sysName : website.title }}
    </div>
    <div class="login-weaper animated bounceInDown">
      <div class="login-border">
        <div class="login-main">
          <div class="login-title">
            登录
            <!-- <top-lang></top-lang> -->
          </div>
          <userLogin
            v-if="activeName === 'user'"
            @getChildData="getChildData"
          ></userLogin>
          <codeLogin v-else-if="activeName === 'code'"></codeLogin>
          <thirdLogin v-else-if="activeName === 'third'"></thirdLogin>
          <!-- <div class="login-menu">
            <a href="#" @click.stop="activeName = 'user'">{{
              $t("login.userLogin")
            }}</a> -->
          <!--<a href="#" @click.stop="activeName='code'">{{ $t('login.phoneLogin') }}</a>-->
          <!-- <a href="#" @click.stop="activeName = 'third'">{{
              $t("login.thirdLogin")
            }}</a>
          </div> -->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import userLogin from "./userlogin";
import codeLogin from "./codelogin";
import thirdLogin from "./thirdlogin";
import { mapGetters } from "vuex";
import { dateFormat } from "@/util/date";
import { validatenull } from "@/util/validate";
import topLang from "@/page/index/top/top-lang";
import topColor from "@/page/index/top/top-color";
import { getQueryString, getTopUrl } from "@/util/util";
export default {
  name: "login",
  components: {
    userLogin,
    codeLogin,
    thirdLogin,
    topLang,
    topColor,
  },
  data() {
    return {
      time: "",
      activeName: "user",
      socialForm: {
        tenantId: "000000",
        source: "",
        code: "",
        state: "",
      },
      gapTime: "",
      beforeUnloadTime: "",
      captchaInfo: {},
    };
  },
  watch: {
    $route() {
      this.handleLogin();
    },
  },
  created() {
    this.handleLogin();
    this.getTime();
  },
  mounted() {
    window.addEventListener("beforeunload", () => this.beforeunloadHandler());
    window.addEventListener("unload", () => this.unloadHandler());
  },
  computed: {
    ...mapGetters(["website", "tagWel", "websitConfig"]),
  },
  props: [],
  methods: {
    getChildData(data) {
      this.captchaInfo = data;
    },
    beforeunloadHandler() {
      this.beforeUnloadTime = new Date().getTime();
    },
    unloadHandler() {
      this.gapTime = new Date().getTime() - this.beforeUnloadTime;
      alert(this.gapTime);
      if (this.gapTime <= 5) {
        //判断是窗口关闭还是刷新，小于5代表关闭，否则就是刷新。
        console.log("关闭了浏览器");
      } else {
        sessionStorage.setItem("captchaInfo", JSON.stringify(this.captchaInfo));
      }
    },
    getTime() {
      setInterval(() => {
        this.time = dateFormat(new Date());
      }, 1000);
    },
    handleLogin() {
      const topUrl = getTopUrl();
      const redirectUrl = "/oauth/redirect/";
      this.socialForm.source = getQueryString("source");
      this.socialForm.code = getQueryString("code");
      this.socialForm.state = getQueryString("state");
      if (
        validatenull(this.socialForm.source) &&
        topUrl.includes(redirectUrl)
      ) {
        let source = topUrl.split("?")[0];
        source = source.split(redirectUrl)[1];
        this.socialForm.source = source;
      }
      if (
        !validatenull(this.socialForm.source) &&
        !validatenull(this.socialForm.code) &&
        !validatenull(this.socialForm.state)
      ) {
        const loading = this.$loading({
          lock: true,
          text: "第三方系统登录中,请稍后。。。",
          spinner: "el-icon-loading",
        });
        this.$store
          .dispatch("LoginBySocial", this.socialForm)
          .then(() => {
            window.location.href = topUrl.split(redirectUrl)[0];
            this.$router.push({ path: this.tagWel.value });
            loading.close();
          })
          .catch(() => {
            loading.close();
          });
      }
    },
  },
  destroyed() {
    window.removeEventListener("beforeunload", () =>
      this.beforeunloadHandler()
    );
    window.removeEventListener("unload", () => this.unloadHandler());
  },
};
</script>

<style lang="scss">
@import "@/styles/login.scss";
</style>
