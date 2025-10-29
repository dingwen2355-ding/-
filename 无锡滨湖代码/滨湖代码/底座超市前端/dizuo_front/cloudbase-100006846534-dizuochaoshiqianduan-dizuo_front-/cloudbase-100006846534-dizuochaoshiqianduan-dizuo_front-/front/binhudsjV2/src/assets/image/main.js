import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// 第三方插件
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import { fRequest } from './api/ajax';

import * as base from '../src/api/_base.js';
import '../src/assets/css/common.css'; // 公用less

if (window) {
  window.console.log = function () {};
}

Vue.config.productionTip = false

//事件订阅
Vue.prototype.$EventBus = new Vue();

// 自定义事件
Vue.prototype.G_API = {
  fRequest:fRequest,
  ...base,
}

// 安装插件
Vue.use(ElementUI);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
