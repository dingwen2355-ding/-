import VideoProjection from './videoProjection.class'

class VideoProjectionBIZ extends VideoProjection {
	constructor() {
		super()
	}
}

/**
 * 新增 VideoProjection
 */
VideoProjectionBIZ.prototype.add_biz = function (data) {
	let that = this
	return new Promise(async (resolve, reject) => {
		let isErr = false;
		if (!Array.isArray(data)) {
			if (!data.id || !data.videoURL) isErr = true
		} else {
			let errTarget = data.find(o => !o.id || !o.videoURL)
			if (errTarget) isErr = true
		}

		if (isErr) {
			reject({ errMsg: "参数错误: id 或 videoURL 缺失" })
			return;
		}

		await that.add(data)
		resolve()
	})
}

/**
 * 修改 VideoProjection
 */
VideoProjectionBIZ.prototype.update_biz = function (data) {
	let that = this
	return new Promise(async (resolve, reject) => {
		let isErr = false;
		if (!Array.isArray(data)) {
			if (!data.id || !data.videoURL) isErr = true
		} else {
			let errTarget = data.find(o => !o.id || !o.videoURL)
			if (errTarget) isErr = true
		}

		if (isErr) {
			reject({ errMsg: "参数错误: id 或 videoURL 缺失" })
			return;
		}

		await that.update(data)
		resolve()
	})
}

/**
 * 删除 VideoProjection
 */
VideoProjectionBIZ.prototype.delete_biz = function (ids) {
	let that = this
	return new Promise(async (resolve, reject) => {
		await that.delete(ids)
		resolve()
	})
}

export default VideoProjectionBIZ