import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

App.mpType = 'app'
import API from "@/api/api"
Vue.prototype.$api = API
Vue.prototype.$resource = (url) => {
  if (url.indexOf(",") == -1) {
    return "http://2.21.137.224:8080/wechat/img/" + url
  } else if (url.indexOf(",") != -1) {
    url = url.split(",")[0]
    return "http://2.21.137.224:8080/wechat/img/" + url
  } else {
    return undefined
  }
}
const app = new Vue({
  ...App
})
app.$mount()
