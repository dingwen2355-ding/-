
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

Vue.prototype.$setRSApass = function (str) {
    let pubKey = `-----BEGIN PUBLIC KEY-----MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCorVk9wv+I4DrpiWQSA05c2mWEZFO1iQOdfRPtBrpokyIYzREZCYhCpNc/mPZ845Z7HHBGeoNfl6zhI3TTHY/oaDVLGTWVLHUzG5C0kgSDiG9wdVhZ9GAfKno9xazs38IPN6L2Haou0cEnKbYo+t3jhj2Ot8yYQu6vqYWO1RmUUQIDAQAB-----END PUBLIC KEY-----`;
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
                      
                