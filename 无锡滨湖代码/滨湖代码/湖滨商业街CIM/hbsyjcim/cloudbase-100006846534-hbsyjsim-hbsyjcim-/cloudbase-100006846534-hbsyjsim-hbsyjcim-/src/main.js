import Vue from "vue";
import App from "./App.vue";

import router from "@/router";

// 导入 store
import store from "@/store";

// 导入echarts
// import echarts from '@/utils/echarts';
import * as echarts from 'echarts';

// 导入ElementUI
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import { findComponentUpward } from "@/utils/tool/findComponentUpward";
import { findComponentDownward } from "@/utils/tool/findComponentDownward";
import { Percentages } from '@/utils/tool/Percentage';
Vue.mixin({
  methods: {
    findComponentUpward,
    findComponentDownward,
    Percentages,
  },
});

Vue.use(ElementUI);

// 挂载到实例中
Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false;

import "./styles/base.min.css";

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
