function interceptor(Axios) {
  Axios.interceptors.request.use(
    function (request) {
      // 添加token
      request.headers.common.Authorization = localStorage.getItem("token");
      request.headers.common.token = localStorage.getItem("token");
      request.headers['Znv-Token'] = localStorage.getItem("token") // 标识自家系统调用后端接口
      // request.headers['Znv-Token'] = '12CBD9B708D887A41AFAB97DAC46AAC6' // 标识自家系统调用后端接口
      if (request.url.indexOf("userId=") === -1) {
        if (request.url.indexOf("?") === -1) {
          request.url += "?userId=" + localStorage.getItem("userId");
        } else {
          request.url += "&userId=" + localStorage.getItem("userId");
        }
      }
      request.url += ('&r=' + (new Date().getTime()))
      return request;
    },
    function (error) {
      // 对响应错误做处理
      return Promise.reject(error);
    }
  );
  Axios.interceptors.response.use(
    function (response) {
      if (response.status == 401) {
        // localStorage.clear();
        window.location.href = "/";
      }
      // 对响应数据做处理
      return response;
    },
    function (error) {
      // 对响应错误做处理
      if (error.response && error.response.status == 401) {
        // localStorage.clear();
        window.location.href = "/";
      }
      return Promise.reject(error);
    }
  );
  Axios.interceptors.request.use(
    function (config) {
      // 在发起请求请做一些业务处理
      return config;
    },
    function (error) {
      // 对请求失败做处理
      return Promise.reject(error);
    }
  );
}

export { interceptor };
