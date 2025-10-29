import { createApp } from 'vue'
import './assets/font/font.css'
import App from './App.vue'
import request from "@/utils/request"
import vue3SeamlessScroll from "vue3-seamless-scroll";
import pinia from "@/store/store"
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { axios_get, axios_post } from '@/utils/request';

// 挂载pinia
const app = createApp(App)
    .use(vue3SeamlessScroll)
app.use(pinia)
app.use(ElementPlus)
window.$axios_get = axios_get
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
// window.IP = "http://192.168.1.47:9000"
window.IP = import.meta.env.VITE_BASE_URL
function getAssets(name) {
    return new URL(`./assets/${name}`, import.meta.url).href
}
// function getResource(url) {
//     if (url.indexOf(",") == -1) {
//         return "http://106.14.27.198:9001/wmcs" + url
//     } else if (url.indexOf(",") != -1) {
//         url = url.split(",")[0]
//         return "http://106.14.27.198:9001/wmcs" + url
//     } else {
//         return undefined
//     }
// }
function getResource(url) {
    if (url.indexOf(",") == -1) {
        return "http://2.21.138.78:9001/wmcs" + url
    } else if (url.indexOf(",") != -1) {
        url = url.split(",")[0]
        return "http://2.21.138.78:9001/wmcs" + url
    } else {
        return undefined
    }
}
app.config.globalProperties.$getAssets = getAssets
app.config.globalProperties.$getResource = getResource
app.config.globalProperties.$http = request
app.mount('#app')

