// 设置全局变量（origAPI: 封装前/__g, sealAPI: 封装后）
// window.sealAPI = new SealAirCityAPI(vm);

const defaultOption = {
	id: "",
	videoURL: "",
	location: [],
	rotation: [-90, 0, 0], // 旋转
	fov: 90, //可视角度
	aspectRatio: 1.77, //纵横比
	distance: 200, //距离
	depthCulling: true, //是否背面剔除
	frustumColor: Color.Red, //投影线框颜色
};


class VideoProjection {
	constructor() { }

	/**
		 * 添加一个或多个Beam对象
		 * @param {object | array} data 数据结构，支持对象或数组，对于每一个对象支持以下属性：
		 * id (string) 字符串类型的ID
		 * groupId (string) 可选，Group分组
		 * userData (string) 可选，用户自定义数据
		 * videoURL (string) 视频地址，支持本地文件和网络文件，同时支持rtsp实时视频流协议，注意：mp4文件视频只支持H264/AVC编码格式的
		 * location (array) 位置坐标：[X,Y,Z]，数组元素类型：(number)，取值范围：[任意数值]
		 * rotation (array) 旋转：[Pitch,Yaw,Roll]，数组元素类型：(number)，取值范围：[任意数值]
		 * fov (number) 垂直夹角，取值范围：[0~160]，单位：度
		 * aspectRatio (number) 纵横比，取值范围：[0.1~10.0]
		 * distance (number) 距离，取值范围：[0~1000000.0]，单位：米
		 * depthCulling (boolean) 是否背面剔除，默认值：false
		 * frustumVisible (boolean) 是否显示投影线框，默认值：false
		 * frustumColor (Color) 投影线框颜色，支持四种格式，取值示例
		 * texturePath (string) 自定义投影蒙版图片路径，可以是本地路径，也支持网络路径，
		 * @returns 可选的回调函数
		 */
	add = function (data) {
		return new Promise((resolve, reject) => {
			// --------------------------- 补全参数
			window.origAPI.videoProjection.add({ ...defaultOption, ...data }, () => {
				resolve()
			})
		})
	}

	/** 
	 * 修改一个或多个VideoProjection对象
		* @param {object | array} data 数据结构，请参考add方法
	 * @returns 可选的回调函数
	*/
	update = function (data) {
		return new Promise((resolve, reject) => {
			window.origAPI.videoProjection.update({ ...defaultOption, ...data }, () => {
				resolve()
			})
		})
	}

	/** 
	 * 删除一个或多个VideoProjection对象
	 * @param {string | array} ids 要删除的VideoProjection对象的ID或者ID数组（可以删除一个或者多个）
	 * @returns 可选的回调函数
	*/
	delete = function (ids) {
		return new Promise((resolve, reject) => {
			window.origAPI.videoProjection.delete(ids, () => {
				resolve()
			})
		})
	}

	/** 
	 * 自动定位到合适的观察距离
	 * @param {string | array} ids VideoProjection对象的ID或者ID数组
	 * @param {number} distance 可选参数，观察点距离目标点（被拍摄物体）的距离，取值范围：[0.01~任意正数]，如果设置为0或者不设置，系统自动计算
	 * @param {number} flyTime 可选参数，相机飞行的时间，取值范围：[0~任意正数]，单位：秒，默认值2秒
	 * @returns 可选的回调函数
	*/
	focus = function (ids, distance, flyTime) {
		return new Promise((resolve, reject) => {
			window.origAPI.videoProjection.focus(ids, distance, flyTime, () => {
				resolve()
			})
		})
	}

	/** 
	 * 根据ID获取VideoProjection的详细信息
	 * @param {string | array} ids 要获取的VideoProjection对象ID或者ID数组（可以获取一个或者多个）
	 * @returns 可选的回调函数
	*/
	get = function (ids) {
		return new Promise((resolve, reject) => {
			window.origAPI.videoProjection.get(ids, () => {
				resolve()
			})
		})
	}

	/** 
	 * 隐藏VideoProjection
	 * @param {string | array} ids VideoProjection对象的ID或者ID数组
	 * @returns 可选的回调函数
	*/
	hide = function (ids) {
		return new Promise((resolve, reject) => {
			window.origAPI.videoProjection.hide(ids, () => {
				resolve()
			})
		})
	}

	/** 
	 * 显示VideoProjection
	 * @param {string | array} ids VideoProjection对象的ID或者ID数组
	 * @returns 可选的回调函数
	*/
	show = function (ids) {
		return new Promise((resolve, reject) => {
			window.origAPI.videoProjection.show(ids, () => {
				resolve()
			})
		})
	}

}