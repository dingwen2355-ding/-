
import Vue from "vue";
import App from "./App.vue";
import AppRouters from "./routers/index.router.js";
import StoreProvider from "./services/store.provider.js";
import * as Filters from './services/filter.provider.js'

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import rem from "./utils/rem";
import "./icons";
import global_ from "./views/global";
import JSEncrypt from "jsencrypt";

import 'video.js/dist/video-js.css'

Vue.prototype.$setRSApass = function (str) {
    let pubKey = `MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3HcLWfC08gTnO28I22B58MPveRoYCFaA++ZZX12kIaEmBuTY9AQGrUG+EsQ3nBK9g10jfegBW7vEhBWdtFfGcdML5i06Kj89NtfOWD9eRSy5nOqvQtWkNZSQpX0vsZen0bfHb/ASbCK+ELYMUvLLv6KcZNvh4lwXCLbO9cxsYtwIDAQAB`;
    let encryptStr = new JSEncrypt();
    encryptStr.setPublicKey(pubKey);
    let data = encryptStr.encrypt(str.toString());
    return data;
};
Vue.prototype.GLOBAL = global_;
Vue.prototype.$Bus = window.$Bus = new Vue();


Vue.use(rem);
Vue.use(ElementUI);
ElementUI.Dialog.props.closeOnClickModal.default = false;
ElementUI.MessageBox.setDefaults({
    closeOnClickModal: false,
});
Object.keys(Filters).forEach(key => {
    Vue.filter(key, Filters[key])
})
Vue.config.productionTip = false;

new Vue({
    render: (h) => h(App),
    router: AppRouters,
    store: StoreProvider,
}).$mount("#app");
                      
                