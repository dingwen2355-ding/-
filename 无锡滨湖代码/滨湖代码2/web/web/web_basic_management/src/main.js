import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from '@/utils/request.js'
import './style/e-ui-reset.scss'
import './style/common.scss'
import './common/style/iconfont/iconfont.css'
import * as echarts from 'echarts'
import '@wangeditor/editor/dist/css/style.css'
Vue.prototype.$echarts = echarts
// import VueAMap from 'vue-amap';
// Vue.use(VueAMap);

// VueAMap.initAMapApiLoader({
//   // 高德key
//   key: '8faa3e559c8e10b89e6f8071fc3ef802', // 自己到官网申请，我随便写的
//   // 插件集合 （插件按需引入）
//   plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor', 'AMap.MarkerClusterer'],
//   v: '1.4.15', // 我也不知道为什么要写这个，不写项目会报错，而且我随便写的，跟我下载的版本对应不了
//   uiVersion: '1.0.11' // ui版本号，也是需要写，不配置不加载,

// })

ElementUI.Dialog.props.closeOnClickModal.default = false
Vue.config.productionTip = false
// 挂载到vue原型链上
Vue.prototype.axios = axios
Vue.use(ElementUI)
new Vue({
  router,
  store,
  data: {
    eventBus: new Vue() // 消息事件总线
  },
  render: h => h(App)
}).$mount('#app')
