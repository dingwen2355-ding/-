import axios from "axios";
import store from "../store";
// 加载动画
import { Loading } from 'element-ui';

// 加载框
let oLoadingInstance = null;

axios.defaults.timeout = 120000; //接口请求设置超时

export function post(url, data, params) {
  store.commit('fSetRequestState', false);
  return new Promise((resolve, reject) => {
    oLoadingInstance = Loading.service({
      text: '正在疯狂请求中，请稍等！',
      lock: true,
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.6)'
    });
    axios
      .post(url, data, { params: params })
      .then(
        response => {
          store.commit('fSetRequestState', true);
          oLoadingInstance.close();
          if (response.data.message && response.data.message.indexOf('token') >= 0) {
            localStorage.removeItem('userInfo');
            window.open('http://2.21.137.200:8081/logout?logoutUrl=', '_self');
            window.close();
            reject(response)
            return;
          } else {
            resolve(response.data);
          }
        },
        err => {
          store.commit('fSetRequestState', true);
          oLoadingInstance.close();
          reject(err);
        }
      )
      .catch(error => {
        store.commit('fSetRequestState', true);
        oLoadingInstance.close();
        reject(error);
      });
  });
}
export function get(url, param) {
  store.commit('fSetRequestState', false);
  return new Promise((resolve, reject) => {
    oLoadingInstance = Loading.service({
      text: '正在疯狂请求中，请稍等！',
      lock: true,
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.6)'
    });
    // console.log(url, param)
    // console.log('axios', axios)
    axios
      .get(url, { params: param })
      .then(
        response => {
          // console.log(response)
          store.commit('fSetRequestState', true);
          oLoadingInstance.close();
          resolve(response.data);
        },
        // response => {
        //   console.log(response)
        //   store.commit('fSetRequestState', true);
        //   oLoadingInstance.close();
        //   // resolve
        //   resolve(response.data);
        //   // if (response?.data?.message && response?.data?.message.indexOf('token') >= 0) {
        //   //   // localStorage.removeItem('userInfo');
        //   //   window.open('http://2.21.137.200:8081/logout?logoutUrl=', '_self');
        //   //   window.close();
        //   //   reject(response)
        //   //   // return;
        //   // } else {
        //   //   resolve(response.data);
        //   // }
        // },
        err => {
          // 
          // console.log(err, '请求错误')
          store.commit('fSetRequestState', true);
          oLoadingInstance.close();
          reject(err);
        }
      )
      .catch(error => {
        store.commit('fSetRequestState', true);
        oLoadingInstance.close();
        reject(error);
      });
  });
}

// http request 请求拦截器
axios.interceptors.request.use(
  config => {
    let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
    if (store.state.oUserInfo.apiToken || oUserInfo.apiToken) {
      config.headers.Authorization = store.state.oUserInfo.apiToken || oUserInfo.apiToken
      config.headers.ApiToken = store.state.oUserInfo.apiToken || oUserInfo.apiToken
    }
    // config.headers.common["token"] = store.getters.get_token;
    // if (config.url.indexOf("page") != -1) {
    //   config.params = Object.assign(config.params, store.state.sys.codeObject);
    // }

    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
// http response 响应拦截器
// axios.interceptors.response.use(
//   response => {
//     // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
//     // 否则的话抛出错误
//     if (response.status === 200) {
//       return Promise.resolve(response);
//     } else {
//       return Promise.reject(response);
//     }
//   },
//   error => {
//     if (error.response.status) {
//       return Promise.reject(error.response);
//     }
//   }
// );
