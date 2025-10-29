import 'whatwg-fetch'
import store from "../store";
// 加载动画
import { Loading } from 'element-ui';

// 加载框
let oLoadingInstance = null;

// 定义一个Fetch拦截器
const interceptor = (request) => {
    // 在请求头中添加一个自定义的Authorization字段
    let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
    if (store.state.oUserInfo.apiToken || oUserInfo.apiToken) {
        request.headers.set('apiToken', store.state.oUserInfo.apiToken || oUserInfo.apiToken);
    }

    // 返回修改后的请求对象
    return request;
};

function fSetUrl(obj) {
    let str = '?';
    for (const key in obj) {
        str += key + '=' + obj[key] + '&'
    }
    return str;
};

export function fRequest({ url, data, params, method }) {
    let sMethod = method || 'get'; // 默认post请求
    return new Promise((resolve, reject) => {
        oLoadingInstance = Loading.service({
            text: '正在疯狂请求中，请稍等！',
            lock: true,
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.6)'
        });
        let oParams = {}
        if (sMethod == 'get') {
            url = data ? url + fSetUrl(data) : url;
        } else {
            oParams = { body: JSON.stringify({ ...data }), }
        }
        if (url.substr(-1) == '&') {
            url = url.substring(0, url.length - 1)
        }
        let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
        let sToken = ''
        if (store?.state?.oUserInfo?.apiToken || oUserInfo?.apiToken) {
            sToken = store?.state?.oUserInfo?.apiToken || oUserInfo?.apiToken
        }
        // console.log(url)
        // url = url.replace('dbApi', 'api');
        fetch(url, {
            method: sMethod,
            ...oParams,
            headers: {
                'Content-Type': 'application/json',
                "Authorization": sToken,
                "ApiToken": sToken,
                // "value": sToken,
            },
            // 将拦截器函数作为参数传递给interceptor属性
            interceptor
        }).then(response => {
            response.json().then(res => {
                store.commit('fSetRequestState', true);
                oLoadingInstance.close();
                resolve(res);
                if (res.code == 401 || res.code == 403) {
                    // localStorage.removeItem('userInfo');
                    // localStorage.clear();
                    // sessionStorage.clear();
                    // this.$router.go(-this.$router.history.current.indexOf)

                    // window.location.replace('https://2.21.138.89:11511/system-web/logout')
                    // window.location.replace('http://2.22.51.26:30002/logout?logoutUrl=https://2.21.138.89:11511/api/login');
                    reject(res)
                    return;
                } else if (res.message && res.message.indexOf('token') >= 0) {
                    // localStorage.removeItem('userInfo');
                    // localStorage.clear();
                    // sessionStorage.clear();
                    // this.$router.go(-this.$router.history.current.indexOf)
                    // window.location.replace('https://2.21.138.89:11511/system-web/logout')
                    // window.location.replace('http://2.22.51.26:30002/logout?logoutUrl=https://2.21.138.89:11511/api/login');
                    reject(res)
                    return;
                } else {
                    resolve(res);
                }
            })
            // console.log()

        }).catch(err => {
            // 处理错误
            store.commit('fSetRequestState', true);
            oLoadingInstance.close();
            reject(err);
        });
    })
}
