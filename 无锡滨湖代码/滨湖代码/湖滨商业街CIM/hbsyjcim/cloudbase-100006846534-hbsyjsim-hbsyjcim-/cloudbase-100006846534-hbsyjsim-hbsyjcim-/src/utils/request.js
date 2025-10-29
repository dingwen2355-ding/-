import axios from "axios";

// 创建axios实例
const service = axios.create({
  baseURL: '/', 
  timeout: 1000 * 60 * 2, // 请求超时时间
  
});

// response 拦截器
service.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    const response = error.response;
    if (!response) {
      //调不通，返回null
      return;
    }

   
    if (response.status === 401) {
       // 401:未登录;
    } else if (response.status === 403 || response.status === 405) {
      // 403:无权限访问; 405:密码到期，需要强制修改密码;
    } else if (response.status === 404) {
       // 404;
    } else if (response.status === 500 || response.status === 518) {
      // 服务端异常
    } else if (response.status === 417) {
      //登录发生异常
    }
    return Promise.reject(error);
  }
);

export default service;
