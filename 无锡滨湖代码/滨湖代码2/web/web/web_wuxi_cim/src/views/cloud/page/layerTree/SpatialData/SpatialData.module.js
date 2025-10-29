// 空间数据 公共业务 采用 vue.mixins
/*
	1. 公共数据
		1.1 数据叠加配置-默认config
		1.2 点-默认option
		1.3 线-默认option
		1.4 面-默认option

		1.5 当前叠加序列
	
	2. 公共接口
		2.1 获取 数据详情 不同回调
		2.2 获取 数据叠加配置 不同回调
		2.3 marker 操作 不同模式
		2.4 polyline 操作 不同模式
		2.5 polygon 操作 不同模式

*/
import { get } from '@/utils/fetch';
import { Message } from 'element-ui'

/* 数据叠加配置-默认config */
const defaultDataConfig = {
	titleField: "feature_name",
	heightField: "model_z",
	fontSize: 16,
	fontColor: "#FFFFFF", // 字体颜色
	fontOutlineColor: "#0D9BA0", // 字体轮廓线颜色
	textBackgroundColor: "#333333", // 文本背景颜色
	showLine: true,
	lineColor: "#CCCCCC",
	lineSize: [2, 32],
	lineOffset: [60, -50],
	textRange: [10, 60000],
	markerRange: [10, 60000],
	imageIndex: 0,
	occlusionCull: true,
	displayMode: 2,
	// -----------------------------
	thickness: 20,
	polyLineColor: "#F1E507", // 线颜色 <color>
	polyLineShape: 1, // <shap> 样式 0：直线， 1：曲线
	depthTest: false,
	// -----------------------------
	polygonColor: "#F1E507",
	polygonStyle: 10,
	polygoHeight: 10,
	enableHeightField: false,
	// -----------------------------
	color: "#FF66CC", // del
	num: 8, // del
	height: 5, // del
	style: 0, // del
	occlusion: 1, // del
}

/* 点-默认option */
const defalutOption_Point = {
	id: "",
	// userData:"",
	displayMode: 1, // 显示模式
	coordinate: [],
	text: "Marker",
	fontSize: 16, // 字体大小
	fontColor: "#ffffff", // 字体颜色
	fontOutlineColor: "", // 字体轮廓线颜色
	textBackgroundColor: "#333333", // 文本背景颜色
	textRange: [10, 60000],
	range: [10, 60000],
	useTextAnimation: false,
	imagePath: "",
	fixedSize: true,
	imageSize: [32, 32], // [width, height],
	showLine: true, // 是否显示垂直牵引线
	lineColor: "#CCCCCC",
	lineSize: [2, 32], // [width, height],
	lineOffset: [60, -50], // [width, height],
	occlusionCull: true,
	// ---------------------
	coordinateType: 0, // 坐标系类型，取值0(Projection), 1(WGS84)，如果不设置此参数，默认为0。
}

/* 线-默认option */
const defalutOption_Line = {
	id: "",
	color: "#F1E507", // 线颜色 
	thickness: 20, // 线宽
	shape: 1, // <shap> 样式 0：直线， 1：曲线
	depthTest: false, // 深度检测
	coordinates: [[], []],
	// ---------------------
	style: 4, // 折线样式 0: 箭头样式1 2:流动线 3:光流 4:固定线条 5:贴地模式
	intensity: 0.5, // 亮度
	flowRate: 0.5, // 流速
	range: [10, 60000],
	coordinateType: 0, // 坐标系类型，取值0(Projection), 1(WGS84)，如果不设置此参数，默认为0。
}

/* 面-默认option */
const defalutOption_Plane = {
	id: "",
	depthTest: false, // 深度检测
	color: "#F1E507",
	style: 10, // 折线样式 0:单色 1:圆点 5:波纹 7:旋转箭头 10:贴地模式
	coordinates: [[], []],
	// ---------------------
	intensity: 0.5, // 亮度
	range: [10, 60000],
	frameThickness: 0,
	coordinateType: 0, // 坐标系类型，取值0(Projection), 1(WGS84)，如果不设置此参数，默认为0。
}

const SpatialDataModule = {
	data() {
		return {
			defaultDataConfig,
			defalutOption_Point,
			defalutOption_Line,
			defalutOption_Plane,
			markerIcons: [
				{
					id: 1,
					name: "3d",
					url: require('@/assets/cloud/marker_icons/3d.png')
				},
				{
					id: 2,
					name: "administration",
					url: require('@/assets/cloud/marker_icons/administration.png')
				},
				{
					id: 3,
					name: "air_fixwing",
					url: require('@/assets/cloud/marker_icons/air_fixwing.png')
				},
				{
					id: 4,
					name: "above_ground",
					url: require('@/assets/cloud/marker_icons/above_ground.png')
				},
				{
					id: 5,
					name: "school",
					url: require('@/assets/cloud/marker_icons/school.png')
				},
				{
					id: 6,
					name: "emergencyphone",
					url: require('@/assets/cloud/marker_icons/emergencyphone.png')
				},
				{
					id: 7,
					name: "elevator",
					url: require('@/assets/cloud/marker_icons/elevator.png')
				},
				{
					id: 8,
					name: "flag-export",
					url: require('@/assets/cloud/marker_icons/flag-export.png')
				},
				{
					id: 9,
					name: "helicopter",
					url: require('@/assets/cloud/marker_icons/helicopter.png')
				},
				{
					id: 10,
					name: "group-2",
					url: require('@/assets/cloud/marker_icons/group-2.png')
				},
				{
					id: 11,
					name: "ambulance",
					url: require('@/assets/cloud/marker_icons/ambulance.png')
				},
				{
					id: 12,
					name: "fireexstinguisher",
					url: require('@/assets/cloud/marker_icons/fireexstinguisher.png')
				},
				{
					id: 13,
					name: "ferry.png",
					url: require('@/assets/cloud/marker_icons/ferry.png')
				},
				{
					id: 14,
					name: "disability",
					url: require('@/assets/cloud/marker_icons/disability.png')
				},
				{
					id: 16,
					name: "ferriswheel",
					url: require('@/assets/cloud/marker_icons/ferriswheel.png')
				},
			],
			currRow: null,
			currRowId: "",
		}
	},
	methods: {
		/* 获取 数据类型 */
		getFeatType(featureType) {
			switch (featureType) {
				case "Point":
				case "PointZM":
				case "MultiPoint":
					return "点"; // 点 point
				case "LineString":
				case "LinearRing":
				case "MultiLineString":
					return "线"; // 线 line
				case "Polygon":
				case "MultiPolygon":
					return "面"; // 面 plane
				default:
					return "点";
			}
		},
		fusionMethodJudge(fusionMethod) {
			// batchUpdate(指定字段),singleUpdate （赋坐标）,featureUpdate（要素绑定）,fileUpdate(图文关联), null 
			if (!Boolean(fusionMethod) || fusionMethod == 'batchUpdate' || fusionMethod == 'singleUpdate' || fusionMethod == 'featureUpdate') {
				return true
			}
			return false
		},
		/* 获取 数据配置 */
		async getDataConfig(row) {
			let res = await get(`${cim_main}fusion/getGridBindField`, { tableName: row.tableName })
			return res.message;
		},
		// markerManger() { },
		// polyLineManger() { },
		// polygonManger() { },
		markerHelperOne(item, dataConfig, focused = false) {
			if (!item) return;
			let isHeightField = false;

			let coordinate = [item.model_x, item.model_y]
			let model_z = item[dataConfig.heightField]

			if (!coordinate[0] || !coordinate[1]) {
				coordinate = [item.modelX, item.modelY]
			}

			if (!coordinate[0] || !coordinate[1]) {
				return Message.warning("数据坐标无效！");
			}

			if (isNaN(model_z)) {
				isHeightField = true;
				coordinate.push(5);
			} else coordinate.push(Number(model_z));
			if (!item.model_x) {
				coordinate = [item.modelX, item.modelY, item.modelZ]
			}

			let markerOption = {
				...this.defalutOption_Point,
				id: `mk-${item.id || item.grid_id}`,
				text: item[dataConfig.titleField] || item.name || "标题缺失",
				coordinate,
				fontColor: dataConfig.fontColor,
				fontOutlineColor: dataConfig.fontOutlineColor,
				textBackgroundColor: dataConfig.textBackgroundColor,
				textRange: dataConfig.textRange,
				range: dataConfig.markerRange,
				showLine: dataConfig.showLine,
				lineColor: dataConfig.lineColor,
				lineSize: dataConfig.lineSize,
				lineOffset: dataConfig.lineOffset,
				imagePath: this.markerIcons[dataConfig.imageIndex].url,
			}

			if (isHeightField) Message.error("字段 " + this.configInfo.heightField + " 值非有效数, 设置高度默认值: 5")
			this.$nextTick(() => {
				window.origAPI.marker.delete(markerOption.id, () => {
					window.origAPI.marker.add(markerOption, () => {
						if (this.currRowId != markerOption.id) {
							window.origAPI.marker.focus(markerOption.id)
							this.currRowId = markerOption.id;
						}
						if (focused) window.origAPI.marker.focus(markerOption.id)
					})
				})
			})
		},
		polyLineHelperOne(item, dataConfig, focused = false) {
			if (!item) return;

			let coordArr = item.th_geom.split("((")[1].split("))")[0].split(",")

			let coordinates = coordArr.map(str => {
				let arr = str.split(" ");
				return arr.map(it => Number(it));
			})

			let polyLineOption = {
				...this.defalutOption_Line,
				id: `pline-${item.id || item.grid_id}`,
				coordinates,
				thickness: dataConfig.thickness,
				shap: dataConfig.polyLineShape,
				color: dataConfig.polyLineColor,
				depthTest: dataConfig.depthTest,
			}
			this.$nextTick(() => {
				window.origAPI.polyline.delete(polyLineOption.id, () => {
					window.origAPI.polyline.add(polyLineOption, () => {
						if (this.currRowId != polyLineOption.id) {
							window.origAPI.polyline.focus(polyLineOption.id)
							this.currRowId = polyLineOption.id;
						}
						if (focused) window.origAPI.polyline.focus(polyLineOption.id)
					})
				})
			})
		},
		polygonHelperOne(item, dataConfig, focused = false) {
			if (!item) return;
			let isHeightField = false;

			let coordArr = item.th_geom.split("(((")[1].split(")))")[0].split(",")
			let coordinates = coordArr.map(str => {
				let arr = str.split(" ");
				if (dataConfig.enableHeightField) {
					let model_z = item[dataConfig.heightField]
					if (isNaN(model_z)) {
						arr[2] = 5;
						isHeightField = true;
					} else arr[2] = Number(model_z);
				} else {
					arr[2] = Number(dataConfig.polygoHeight);
				}
				return arr.map(it => Number(it));
			})

			if (isHeightField) Message.error("字段 " + dataConfig.heightField + " 值非有效数, 设置高度默认值: 5")

			let polygonOption = {
				...this.defalutOption_Plane,
				id: `plgon-${item.id || item.grid_id}`,
				coordinates,
				color: dataConfig.polygonColor,
				depthTest: dataConfig.depthTest,
				style: dataConfig.polygonStyle,
			}

			this.$nextTick(() => {
				window.origAPI.polygon.delete(polygonOption.id, () => {
					window.origAPI.polygon.add(polygonOption, () => {
						if (this.currRowId != polygonOption.id) {
							window.origAPI.polygon.focus(polygonOption.id)
							this.currRowId = polygonOption.id;
						}
						if (focused) window.origAPI.polygon.focus(polygonOption.id)
					})
				})
			})
		},
	}
}

export default SpatialDataModule