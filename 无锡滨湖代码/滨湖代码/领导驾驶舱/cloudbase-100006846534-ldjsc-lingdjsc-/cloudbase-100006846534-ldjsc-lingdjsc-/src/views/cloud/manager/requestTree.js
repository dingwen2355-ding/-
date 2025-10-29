import axios from 'axios';
import store from "../../../services/store.provider.js"

const newAxios = axios.create({
    baseURL: cim_main,
    transformRequest: [
        function (data) {
            let ret = ""
            for (let it in data) {
                ret += encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&"
            }
            return ret
        }
    ]
})


newAxios.interceptors.request.use((config) => {
    if (store.state.login.account.token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
        config.headers.Authorization = store.state.login.account.token;
    }
    return {
        ...config
    }
}, (err) => Promise.reject(err))


newAxios.interceptors.response.use((res) => {
    // console.log(res, "响应拦截 ！！！")
    return res.data;
}, (err) => Promise.reject(err))
export default newAxios;