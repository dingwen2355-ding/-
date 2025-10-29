
import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/services/store.provider';
import { post } from '@/utils/fetch.js';

Vue.use(VueRouter);
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch((err) => err)
};
const router = new VueRouter({
    mode: 'hash', base: process.env.BASE_URL, routes: [
        { path: '/', redirect: '/Rtcplayer', name: '' },
        { path: '/Rtcplayer', name: 'Rtcplayer', component: () => import('../views/cloud/Rtcplayer.vue') }
    ]
});

// scene service download：免图片验证码登录
async function getLoginInfo() {
    let formdata = new FormData();
    formdata.append("b", window.$Bus.$setRSApass(account_info.username));
    formdata.append("a", window.$Bus.$setRSApass(account_info.password));

    let res = await post(`${cim_main}freedo/user/free/login`, formdata, false);
    if (!res || !res.message || !res.message.token) return;
    store.dispatch("SET_ACCOUNT_ACTION", {
        token: res.message.token,
        uploadtoken: res.message.uploadtoken,
        userInfo: res.message.userInfo,
    });
}

// 获取url参数
function getQueryByName(url, name) {
    var reg = new RegExp('[?&]' + name + '=([^&#]+)');
    var query = url.match(reg);
    return query ? query[1] : null;
}

router.beforeEach(async (to, from, next) => {
    let token = store.getters.token;
    if (!token) {
        await getLoginInfo();
        token = store.getters.token;
    }
    let toQuery = {};
    if(!!token) {
        toQuery.eventId = getQueryByName(location.href, 'eventId')
        toQuery.token = token;
        toQuery.mediaid = getQueryByName(service_proxy_address, 'mediaId');
    }
    let query = to.query;
    if (query && query.token === toQuery.token) next();
    else {
        next({
            path: to.path,
            query: toQuery
        })
    }
});

export default router;
                