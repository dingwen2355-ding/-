import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/common.scss'
import './styles/large-screen.scss'
import './utils/directives'
import 'vue2-org-tree/dist/style.css'
import './assets/styles/index.scss'
// import sysConfig from './utils/sysConfig'
import store from './store'
import './utils/drag'
import Vue2OrgTree from 'vue2-org-tree'
// window.sysConfig = sysConfig

Vue.config.productionTip = false
ElementUI.Dialog.props.closeOnClickModal.default = false
Vue.use(ElementUI)
Vue.use(Vue2OrgTree)
new Vue({
  router,
  store,
  render: h => h(App),
  data: {
    Bus: new Vue() // 消息事件总线
  }
}).$mount('#app')
