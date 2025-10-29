import "babel-polyfill";
import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import { get, post } from "./api/ajax"; // 请求
import { fRequest } from "./api/whatwgFetch";
import { fSetData } from './api/setUrlStr';
import 'whatwg-fetch'
import IP from "./api/ipconfig.js";
import pro from "./api/setAppList.js";

// 第三方插件
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "swiper/css/swiper.css";
import _ from "lodash";
import "./components/utils/ignoreError";
import * as echarts from 'echarts'
import * as filters from "./components/utils/date"; // 时间的处理方法

import '../src/assets/css/common.css'; // 公用less

Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key]);
});

Vue.prototype.$get = get;
Vue.prototype.$post = post;
Vue.prototype.$ip = {...IP};
Vue.prototype.$fRequest = fRequest;
Vue.prototype.$fSetData = fSetData;
Vue.prototype._ = _;
Vue.prototype.$echarts = echarts;
Vue.prototype.$EventBus = new Vue();

// 上线关闭Vue Devtools
// const isDebug_mode = process.env.NODE_ENV !== "production";
// Vue.config.debug = isDebug_mode;
// Vue.config.devtools = isDebug_mode;
// Vue.config.productionTip = isDebug_mode;

Vue.use(ElementUI);
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
