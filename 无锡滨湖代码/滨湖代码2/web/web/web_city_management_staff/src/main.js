import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import Vant from 'vant'
import 'vant/lib/index.css'
import './styles/common.scss'

import VueQuillEditor from 'vue-quill-editor'
// import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
// import 'quill/dist/quill.bubble.css'

Vue.use(VueQuillEditor)

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(Vant)

// 点击空白处-指令
Vue.directive('clickoutside', {
  bind: function (el, binding, vnode) {
    function documentHandler (e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e)
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.__vueClickOutside__ = documentHandler
    document.addEventListener('click', documentHandler)
  },
  inserted: function () { },
  update: function () { },
  componentUpdated: function () { },
  unbind: function (el, binding) {
    // 解除事件监听
    document.removeEventListener('click', el.__vueClickOutside__)
    delete el.__vueClickOutside__
  }
})

new Vue({
  router,
  store,
  render: h => h(App),
  data: {
    eventBus: new Vue() // 消息事件总线
  }
}).$mount('#app')
