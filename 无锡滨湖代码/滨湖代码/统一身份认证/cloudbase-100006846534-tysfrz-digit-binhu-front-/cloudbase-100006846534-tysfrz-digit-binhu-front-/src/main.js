import Vue from 'vue';
import axios from './router/axios';
import VueAxios from 'vue-axios';
import App from './App';
import router from './router/router';
import './permission'; // 权限
import './error'; // 日志
import './cache';//页面缓存
import store from './store';
import {loadStyle} from './util/util'
import * as urls from '@/config/env';
import Element from 'element-ui';
import {
  iconfontUrl,
  iconfontVersion
} from '@/config/env';
import i18n from './lang'; // Internationalization
import './styles/common.scss';
import basicBlock from './components/basic-block/main';
import basicContainer from './components/basic-container/main';
import thirdRegister from './components/third-register/main';
import flowDesign from './components/flow-design/main';
import avueUeditor from 'avue-plugin-ueditor';
import website from '@/config/website';
import crudCommon from '@/mixins/crud';
// 业务组件
import tenantPackage from './views/system/tenantpackage';
import echarts from 'echarts'
Vue.prototype.$echarts = echarts;
Vue.use(echarts)

// 注册全局crud驱动
window.$crudCommon = crudCommon;
// 加载Vue拓展
Vue.use(router);
Vue.use(VueAxios, axios);
Vue.use(Element, {
  i18n: (key, value) => i18n.t(key, value)
});
Vue.use(window.AVUE, {
  size: 'small',
  tableSize: 'small',
  calcHeight: 65,
  i18n: (key, value) => i18n.t(key, value)
});
//注册echarts
Vue.component('e-chart', r => require.ensure([], () => {
  // 引入 ECharts 主模块
  // var echarts = require('echarts/lib/echarts')
  // 引入柱状图
  require('echarts/lib/chart/map')
  require('echarts/lib/chart/line')
  require('echarts/lib/chart/bar')
  require('echarts/lib/chart/pie')
  require('echarts/lib/chart/gauge')
  require('echarts/lib/chart/candlestick')
  require('echarts/lib/chart/custom')//引入自定义


  // 引入提示框和标题组件
  require('echarts/lib/component/tooltip')
  require('echarts/lib/component/title')
  //引入markline
  require('echarts/lib/component/markLine')
  // 引入图例
  require('echarts/lib/component/legend')
  require('echarts/lib/component/visualMap')
  // 引入vue包装组件
  r(require('./components/echarts/echarts.vue'))
}, 'echarts'))
// 注册全局容器
Vue.component('basicContainer', basicContainer);
Vue.component('basicBlock', basicBlock);
Vue.component('thirdRegister', thirdRegister);
Vue.component('avueUeditor', avueUeditor);
Vue.component('flowDesign', flowDesign);
Vue.component('tenantPackage', tenantPackage);
// 加载相关url地址
Object.keys(urls).forEach(key => {
  Vue.prototype[key] = urls[key];
});
// 加载NutFlow
Vue.use(window.WfDesignBase);
// 加载website
Vue.prototype.website = website;
// 动态加载阿里云字体库
iconfontVersion.forEach(ele => {
  loadStyle(iconfontUrl.replace('$key', ele));
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app');
