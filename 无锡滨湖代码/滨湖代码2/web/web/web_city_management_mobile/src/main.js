import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vant, { Icon, Toast } from 'vant'
import axios from '@/utils/request.js'

import 'vant/lib/index.css'
import './styles/common.scss'
import './styles/vant-reset.scss'
import AlloyFinger from 'alloyfinger'
import AlloyFingerPlugin from 'alloyfinger/vue/alloy_finger_vue'
import VueCoreVideoPlayer from 'vue-core-video-player'

Vue.config.productionTip = false
// 挂载到vue原型链上
Vue.prototype.axios = axios
Vue.use(Vant)
Vue.use(Icon)
Vue.use(Toast)
Vue.use(AlloyFingerPlugin, {
  AlloyFinger
})
Vue.use(VueCoreVideoPlayer, {
  lang: 'zh-CN'
})

new Vue({
  router,
  store,
  render: h => h(App),
  data: {
    eventBus: new Vue() // 消息事件总线
  }
}).$mount('#app')
