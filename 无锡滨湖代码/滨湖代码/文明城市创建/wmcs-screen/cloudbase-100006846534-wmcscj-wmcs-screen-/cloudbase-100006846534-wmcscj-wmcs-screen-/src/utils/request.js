import axios from "axios";
// request是一个axios实例,每一个实例你都可以单独定制它的baseURL,超时时间,请求头和一些其他配置项。
// const baseUrl = import.meta.env.VITE_BASE_URL
const service = axios.create({
	// baseURL: baseUrl,
	timeout: 60 * 1000,
	headers: {
		'Content-Type': 'application/json;charset=UTF-8;',
		'Accept-Language': 'zh-CN,zh;q=0.9,en;q=0.8',
		'.AspNetCore.Culture': 'c=zh-Hans|uic=zh-Hans',
	},
})

// 请求拦截器,就是说请求在到达服务器之前,你对发送到服务器的数据进行一些处理
service.interceptors.request.use(
	(config) => {
		return config
	},
	(error) => {
		Promise.reject(error)
	}
)

// 响应拦截器,后端数据真正被你的变量接收之前,进行一些预处理
service.interceptors.response.use(
	(response) => {
		return response
	},
	function (error) {
		return Promise.reject(error)
	}
)

//封装请求方法
export function axios_get(url, data) {
	return new Promise((resolve, reject) => {
		let token =  window.sessionStorage.getItem("token")
		service
			.get(url, { params: data == undefined ? {} : data.params, headers: { "Authorization": token } })
			.then(
				(response) => {
					resolve(response)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

// query参数对应POST
export function axios_post(url, params) {
	const urlObj = new FormData()
	for (var i in params) {
		urlObj.append(i, params[i])
	}

	return new Promise((resolve, reject) => {
		service
			.post(url, urlObj)
			.then(
				(response) => {
					resolve(response.data)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

// body参数对应POST
export function axios_post_json(url, params) {
	return new Promise((resolve, reject) => {
		service
			.post(url, params)
			.then(
				(response) => {
					resolve(response.data)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

export function axios_delete(url, params) {
	return new Promise((resolve, reject) => {
		service
			.delete(url, { params: params })
			.then(
				(response) => {
					resolve(response.data)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

export function axios_put(url, params, queryParams) {
	return new Promise((resolve, reject) => {
		service
			.put(url, params, { params: queryParams })
			.then(
				(response) => {
					resolve(response.data)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

export function axios_patch(url, params, queryParams) {
	const urlObj = new FormData()
	for (var i in params) {
		urlObj.append(i, params[i])
	}
	return new Promise((resolve, reject) => {
		service
			.patch(url, urlObj, { params: queryParams })
			.then(
				(response) => {
					resolve(response.data)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

export function axios_patch_json(url, params, queryParams) {
	return new Promise((resolve, reject) => {
		service
			.patch(url, params, { params: queryParams })
			.then(
				(response) => {
					resolve(response.data)
				},
				(err) => {
					reject(err)
				}
			)
			.catch((error) => {
				reject(error)
			})
	})
}

export default {
	get: function (url, params) {
		return axios_get(url, params)
	},
	post: function (url, params) {
		return axios_post(url, params)
	},
	post_json: function (url, params) {
		return axios_post_json(url, params)
	},
	delete: function (url, params) {
		return axios_delete(url, params)
	},
	input: function (url, parmas) {
		return axios_put(url, parmas)
	},
	patch: function (url, params) {
		return axios_patch(url, params)
	},
}

