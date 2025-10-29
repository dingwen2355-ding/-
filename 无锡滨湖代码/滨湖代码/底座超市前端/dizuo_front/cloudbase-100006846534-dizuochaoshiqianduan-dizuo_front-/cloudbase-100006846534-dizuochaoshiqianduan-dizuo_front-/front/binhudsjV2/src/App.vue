<template>
  <div id="app" :key="(Math.random() * 10000).toFixed(0)">
    <com-menu></com-menu>
    <!-- 中间内容区域 -->
    <div class="content" style="max-width:1920px;min-width:1400px;overflow: auto;">
      <!-- 数字底座 -->
      <router-view />
      <!-- <sub-numberbottom></sub-numberbottom> -->
    </div>
    <!-- 底部 -->
    <!-- <div class="bottom">
      Copyright ©2022 数字滨湖 . All Rights Reserved . 苏ICP备xxxxxxxx号-1 苏公网安备 xxxxxxxxxxxxx号
    </div> -->

  </div>
</template>

<script>
import { URLS_USERINFO, URLS_SZBH, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js';
import ComMenu from '@/components/page/comMenu.vue';
import axios from 'axios';
import 'whatwg-fetch'

export default {
  name: '',
  components: {
    ComMenu
  },
  props: [''],
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {},
  created() {

  },
  mounted() {
    // document.body.style.zoom = 1 / window.devicePixelRatio;
    // 请求用户信息
    this.$store.commit('fSetCode', new URL(location.href).searchParams.get('code'))
    // if (localStorage.getItem('code') && JSON.parse(localStorage.getItem('code')) === new URL(location.href).searchParams.get('code')) {
    // }else{
    //   localStorage.clear()
    //   // window.location.replace('https://2.21.138.89:11511/system-web/logout');
    //   window.location.replace('http://2.22.51.26:30002/logout?logoutUrl=https://2.21.138.89:11511/api/login');
    //   // 
    // }
    window.addEventListener("beforeunload", (e) => this.fBeforeunloadHandler(e)); // 页面关闭的时候清楚本地缓存
   
  },
  destroyed() {
    window.removeEventListener("beforeunload", (e) =>
      this.fBeforeunloadHandler(e)
    );
  },
  beforeDestroy() {

  },
  methods: {

    // 页面关闭之前，触发提示框
    fBeforeunloadHandler(e) {
      localStorage.removeItem('oNowPage');
    },
  }
}
</script>
<style lang='less' scoped>
#app {
  overflow: auto;
}

.content {
  
  margin: 0 auto;
  height: calc(100vh - 65px);
  // min-height: calc(100vh - 65px - 52px);
  overflow-y: auto;
}

.bottom {
  display: flex;
  align-items: center;
  justify-content: center;
  // margin-top: 40px;
  width: 100%;
  height: 52px;
  font-size: 12px;
  background: #5378b5;
  color: #fff;
}
.content ::-webkit-scrollbar {
  position: absolute;
  right: 0;
  top: 0;
  -webkit-appearance: none;
  width: 0px;
  height: 10px;
}
</style>