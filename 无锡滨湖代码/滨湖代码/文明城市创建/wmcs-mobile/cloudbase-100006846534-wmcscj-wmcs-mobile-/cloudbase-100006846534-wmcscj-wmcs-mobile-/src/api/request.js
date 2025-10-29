const qs = require("qs")
// 全局请求封装


// 需要修改token，和根据实际修改请求头
export default (url, method, data, params) => {
	// 获取本地token
	// 根据请求方法或URL来判断是否添加请求头
	let header = {
		'content-type': "application/json",
		"Authorization": "Bearer " + uni.getStorageSync("access_token")
	}
	return new Promise((resolve, reject) => {
		if (params) {
			url = url + '?' + qs.stringify(params)
		}
		uni.request({
			url: url,
			method: method,
			header: header,
			data: data,
			success(response) {
				const res = response
				// 根据返回的状态码做出对应的操作
				//获取成功
				if (res.data.code == 200) {
					resolve(res.data);
				} else if (res.data.code == '500') {
					//获取失败
					// uni.clearStorageSync()
					uni.showToast({
						title: res.data.msg,
						icon: 'none',
					})
				} else if (res.data.code == '401') {
					//获取失败
					// uni.clearStorageSync()
					uni.showToast({
						title: res.data.msg,
						icon: 'none',
						success() {
							uni.navigateTo({
								url: "/pages/login/login"
							})
						}
					})
				}
			},
			fail(err) {
				reject(err);
				console.log(err)
			},
			complete() {

			}
		});
	}).catch((e) => { });
};