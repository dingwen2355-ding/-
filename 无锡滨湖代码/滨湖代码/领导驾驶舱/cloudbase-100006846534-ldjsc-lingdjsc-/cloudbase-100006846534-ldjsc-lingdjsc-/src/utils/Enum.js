/**
 * 服务类型
 */
export const ServerType = {
	"3D Tiles": 15,
	"Freedo WMTS": 17,
	"Freedo WETS": 18,
	"3DT": 21,
	"I3S": 23,
	"S3M": 24,
}
export const ServerTypeRe = {
	15: "3D Tiles",
	17: "Freedo WMTS",
	18: "Freedo WETS",
	21: "3DT",
	23: "I3S",
	24: "S3M",
}

/**
 * 服务发布引擎类型
 */
export const ServerEngineType = {
	"sceneservice": "Cloud引擎",
	"arcgis": "Arcgis引擎",
	"supermap": "超图引擎",
	"freedo": "飞渡引擎",
	"dts": "高渲染",
	"externalengine": "外部空间引擎",
	"nativeservice": "本地引擎",
	"geoscene": "GeoScene引擎",
}

/**
 * 元数据字段类型
 */
export const fieldTypes = {
	'2': "双精度",
	'4': "整型",
	'8': "单精度",
	'10': "布尔",
	'12': "字符串",
	'93': "日期",
}

/**
 * 元数据默认单位
 */
export const fieldUnits = [
	"米",
	"千米",
	"平方米",
	"平方千米",
]

/**
 * 字段是否必填
 */
export const requireType = {
	'0': "非必填",
	'1': "必填项",
}

/**
 * 目录树类型
 */
export const dirTreeType = {
	SourceData: 1, // 源数据管理
	ServiceData: 2, // 数据服务管理
	SceneData: 3, // 场景服务管理
	BasicData: 4, // 基础数据管理
	ModelData: 5, // 模型管理
}

/**
 * tilelayer 样式
 */
export const TileLayerStyle = [
	{ id: "default", name: "默认", styleid: 0 },
	{ id: "xRay", name: "X光", styleid: 1 },
	{ id: "pure", name: "纯色", styleid: 2 },
	{ id: "Crystal", name: "水晶体", styleid: 3 },
	{ id: "dark", name: "暗黑", styleid: 4 },
	{ id: "science", name: "科幻", styleid: 5 },
	{ id: "spreadOf", name: "扩散", styleid: 6 },
]

/**
 * 数据融合方式
 */
export const FusionMode = {
	batchUpdate: "指定字段",
	singleUpdate: "校准坐标",
	featureUpdate: "要素绑定",
	fileUpdate: "图文关联",
}

/**
 * 菜单类型
 */
export const MenuType = [
	{
		value: 1,
		name: "主菜单",
	},
	{
		value: 2,
		name: "子菜单",
	},
	{
		value: 3,
		name: "操作按钮",
	},
	{
		value: 4,
		name: "左侧菜单",
	}
]
