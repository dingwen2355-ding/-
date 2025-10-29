import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import { interceptor } from './utils/ajax'
import './style/e-ui-reset.scss'
import './style/common.scss'

import 'ztree/css/zTreeStyle/zTreeStyle.css'
import './style/element-variables.scss'
import './style/common-plugin.scss'
import './style/button.scss'
import './style/iconfont/iconfont.css'
import '../public/config/config'
import '@wangeditor/editor/dist/css/style.css'

Vue.config.productionTip = false
// // 挂载到vue原型链上
// Vue.prototype.axios = axios

axios.defaults.timeout = 1000 * 120
interceptor(axios)
Vue.use(ElementUI)
new Vue({
  router,
  store,
  data: {
    eventBus: new Vue() // 消息事件总线
  },
  render: h => h(App)
}).$mount('#app')

const list = ['menu', 'menuIds']
list.forEach(v => {
  if (!localStorage.getItem(v)) {
    localStorage.setItem(v, '[]')
  }
})
