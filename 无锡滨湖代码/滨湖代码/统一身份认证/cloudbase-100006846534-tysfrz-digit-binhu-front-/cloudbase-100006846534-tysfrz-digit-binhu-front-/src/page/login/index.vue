<template>
  <div>

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
import {getSsoLogoutUri} from "@/api/common";

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
    //环境配置，开发环境进入后端登录页，生产、测试进入单点登录页面
    if(process.env.NODE_ENV === 'development' ){
         this.$router.push({ path:"/backendIndex" });
    }else{
      getSsoLogoutUri().then((res) => {
         window.location.href = res.data.data;
      });
    }
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
