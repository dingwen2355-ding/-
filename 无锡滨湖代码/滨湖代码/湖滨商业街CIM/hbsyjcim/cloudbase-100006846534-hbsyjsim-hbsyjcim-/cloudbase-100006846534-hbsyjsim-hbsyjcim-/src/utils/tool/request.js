import axios from "axios";

export const requestNew = (options) => {
  return new Promise((resolve, reject) => {
    const headerJSON = {
      "Content-Type": "application/json",
      //authorization: getUserToken(),
    };
    var parameter = options.data || {};
    var method = options.method || "get";

    //统一所有请求
    axiosRequest(options.url, parameter, method, headerJSON)
      .then(function (response) {
        //直接返回对象
        // var result = response.data.data || response.data;
        var result = response.data;

        resolve(result);
      })
      .catch(function (err) {
        var msg = "接口报错了";
        if (
          err.response != undefined &&
          err.response.data != undefined &&
          (err.response.data.msg != undefined ||
            err.response.data.message != undefined)
        ) {
          if (err.response.data.msg != undefined) {
            msg = err.response.data.msg;
          } else if (err.response.data.message != undefined) {
            msg = err.response.data.message;
          }
        }
        console.log('msg', msg);
        console.log('err', err);
     
        reject(err);
      });
  });
};

export const axiosRequest = (url, params, method, headerJSON) => {
  // url地址
  // url = 'http://cmsapi.api.dev.happy8000.com' + url //测试
  url = '/api' + url
  // url = 'http://2.21.138.71:8081' + url

  if (method == "get") {
    return axiosGet(url, params, headerJSON);
  }
  if (method == "post") {
    return axiosPost(url, params, headerJSON);
  }
  if (method == "put") {
    return axiosPut(url, params, headerJSON);
  }
  if (method == "delete") {
    return axiosDelete(url, params, headerJSON);
  }
};

//get请求并能传递请求头 : 请求头与参数一起传递
export const axiosGet = (url, params, headerJSON) => {
  return axios.get(url, {
    params: params,
    headers: headerJSON,
  });
};
//post请求并能传递请求头 : 请求头在第三个参数中传递
export const axiosPost = (url, params, headerJSON) => {
  return axios.post(url, params, {
    headers: headerJSON,
  });
};

export const axiosPut = (url, params, headerJSON) => {
  return axios.put(url, params, {
    headers: headerJSON,
  });
};

export const axiosDelete = (url, params, headerJSON) => {
  return axios.delete(url, {
    params: params,
    headers: headerJSON,
  });
};

export default requestNew;
