const menuList = [
	{
		name: "平台概览", path: "/home", index: "home", flag: "show", hide: true,
	},
	// {
	// 	name: "CIM+应用",
	// 	index: "cim+",
	// 	flag: "show",
	// 	child: [
	// 		{
	// 			name: "智慧社区",
	// 			path: "/cimPlusApplication/smartCommunity",
	// 			index: "smartCommunity",
	// 		},
	// 		{
	// 			name: "城市体检",
	// 			path: "/cimPlusApplication/physicalExaminationPlatform",
	// 			index: "physicalExaminationPlatform",
	// 		},
	// 		{
	// 			name: "BIM报建",
	// 			path: "/cimPlusApplication/applicationPlatform",
	// 			index: "applicationPlatform",
	// 		},
	// 		{
	// 			name: "城市更新",
	// 			path: "/cimPlusApplication/updatePlatform",
	// 			index: "updatePlatform",
	// 		},
	// 		{
	// 			name: "智慧市政",
	// 			path: "/cimPlusApplication/smartMunicipal",
	// 			index: "smartMunicipal",
	// 		},
	// 		{
	// 			name: "智慧消防",
	// 			path: "/cimPlusApplication/fireControl",
	// 			index: "fireControl",
	// 		},
	// 	],
	// },
	{ name: "开发中心", path: "/cimDevelopmentCenter1", index: "cimDevelopmentCenter1", flag: "show" },
	// {
	// 	id: '07b7878d-568a-4123-ae94-8fae1e851ac0',
	// 	name: "CIM一张图",
	// 	path: "http://cim-platform.g-bim.cn/cims-dev-platform/",
	// 	index: "cimDemoplatform",
	// 	flag: "show",
	// },
	{
		name: "全息展示中心",
		path: "http://cim-platform.g-bim.cn/cim_web_quanxizhanshi/#/resource",
		index: "holographicDisplayCenter",
		flag: "show",
		code: 'qxzszxpz'
	},
	{
		name: "一张图",
		path: "http://cim-platform.g-bim.cn/cims-dev-platform/",
		projectPath: "R:/CIM一张图-new/CIM一张图/演示平台.acp", // 实例所在的工程路径
		index: "cimDemoplatform",
		flag: "show",
		hide: true,
	},
	{
		name: "资源服务中心",
		index: "resourceCenter",
		path: "/resourceCenter",
		flag: "show",
	},
	{
		name: "平台运行驾驶舱", path: "/flightDeck", index: "flightDeck", flag: "show"
	},
	{
		name: "源数据管理中心",
		path: "/dourcedatacenter/management",
		index: "dourcedatacenter",
		flag: "login",
		code: 'ysjglzx'
	},
	/* {
		name: "数据融合中心",
		path: "/addressenginetool",
		index: "datafusionCenter",
		flag: "login",
		children: [
			{
				name: "地名地址引擎工具",
				index: "addressenginetool",
				path: "/addressenginetool",
			},
			{
				name: "建筑网格",
				index: "buildingGrid",
				path: "/buildingGrid",
			},
			{
				name: "待融合数据",
				index: "datafusionData",
				path: "/datafusionData",
			},
			{
				name: "网格",
				index: "commonGrid",
				path: "/commonGrid",
			},
			{
				name: "编码成果",
				index: "modelEncoding",
				path: "/modelEncoding",
			},
		],
	}, */
	{
		name: "数据融合中心",
		index: "DataFusionCenter",
		flag: "login",
		code: 'sjrhzx',
		child: [
			{
				name: "数据融合",
				path: "/fusion/fusion",
				index: "fusion",
				code: 'sjrh',
				children: [
					{
						name: "手动融合",
						path: "/fusion/fusion",
						code: 'sdrh',
					},
					{
						name: "坐标系转换",
						path: "/fusion/coordinateTransform",
						code: 'zbxzh',
					},
					{
						name: "地名地址解析",
						path: "/fusion/addressAnalysis",
						code: 'dmdzjx',
					},
					{
						name: "关联绑定",
						path: "/fusion/coordinateAnalysis",
						code: 'glbd',
					},
					/* {
						name: "网格融合",
						path: "/fusion/gridFusion",
					}, */
					{
						name: "三维视频融合",
						path: "/fusion/videoFusion",
						code: 'swsprh',
					},
					/* {
						name: "业务数据质检",
						path: "/fusion/dataCheck",
					}, */
				],
			},
			{
				name: "轻量化",
				path: "/LTWT/3D/3dtiles",
				index: "LTWT",
				code: 'qlh',
				children: [
					{
						name: "轻量化三维数据",
						index: "3D",
						code: 'qlh',
						children: [
							{
								name: "轻量化3dtiles",
								path: "/LTWT/3D/3dtiles",
								code: 'qlh',
							},
							// {
							// 	name: "轻量化i3s1.6",
							// 	path: "/LTWT/3D/i3s1.6",
							// },
							// {
							// 	name: "轻量化i3s1.7",
							// 	path: "/LTWT/3D/i3s1.7",
							// },
							{
								name: "轻量化3dt",
								path: "/LTWT/3D/3dt",
								code: 'qlh',
							},
						]
					},
				]
			}
		]
	},
	{
		name: "资源管理中心",
		index: "RMC",
		flag: "login",
		code: 'zyglzx',
		child: [
			{
				name: '基础数据管理',
				path: '/RMC/basicDataMGT/basicData',
				index: 'baseMGT',
				code: 'jcsjgl'
			},
			{
				name: '模型管理',
				path: '/RMC/modelMGT',
				// path: '/RMC/modelMGT', // TODO: 改版后的路由地址是这样的
				index: 'modelMGT',
				code: 'mxgl'
			}
		]
	},
	{
		name: "场景创建中心",
		path: "/sence/sceneServe",
		index: "sceneCreationCenter",
		flag: "login",
		code: 'cjcjzx',
		children: [
			{
				name: "场景组装",
				index: "sence",
				code: 'cjzz',
				children: [
					{
						name: "高渲染场景服务",
						path: "/sence/sceneServe",
						code: 'gxrcjfw'
					},
					// {
					// 	name: "WebGL场景服务",
					// 	path: "/sence/webglSceneServer",
					// },
				],
			},
		]
	},
	{
		name: "服务管理中心",
		index: "SMC",
		flag: "login",
		code: 'fwglzx',
		child: [
			{
				name: '数据服务管理',
				path: '/SMC/spaceService',
				index: 'space',
				code: 'sjfwgl'
			},
			{
				name: '场景服务管理',
				path: '/SMC/sceneService',
				index: 'scene',
				code: 'cjfwgl'
			}
		]
	},
	{
		name: "运营管理中心",
		path: "/safety/user",
		index: "operationManagementCenter",
		flag: "login",
		code: 'yyglzx',
		children: [
			{
				name: "安全管理",
				index: "safety",
				code: 'aqgl',
				children: [
					{
						name: "用户管理",
						path: "/safety/user",
						code: 'yhgl',
					},
					{
						name: "角色管理",
						path: "/safety/role",
						code: 'jsgl',
					},
					{
						name: "数据权限管理",
						path: "/safety/dataAuthority",
						code: 'sjqxgl',
					},
					{
						name: "服务授权查询",
						path: "/safety/serviceAuthorizationQuery",
						code: 'fwsqcx',
					},
				]
			},
			{
				name: "审批管理",
				index: "apply",
				code: 'spgl',
				children: [
					{
						name: "待审批",
						path: "/apply/pending",
						code: 'dsp',
					},
					{
						name: "审批记录",
						path: "/apply/record",
						code: 'spjl',
					},
				]
			},
			{
				name: "服务运行监控",
				index: "servicemonitor",
				code: 'fwyxjk',
				children: [
					{
						name: "概览",
						path: "/servicemonitor/overview",
						code: 'fwgl',
					},
					{
						name: "服务状态监控",
						path: "/servicemonitor/operation",
						code: 'fwztjk',
					},
					{
						name: "服务调用监控",
						path: "/servicemonitor/usage",
						code: 'fwdyjk',
					},
					{
						name: "当前请求列表",
						path: "/servicemonitor/currReqList",
						code: 'dqqqlb',
					},
				]
			},
			{
				name: "平台运行监控",
				index: "monitor",
				code: 'ptyxjk',
				children: [
					{
						name: "概览",
						path: "/monitor/overview",
						code: 'ptgl',
					},
					{
						name: "主机",
						path: "/monitor/host",
						code: 'zj',
					},
					{
						name: "系统运维检测",
						path: "/monitor/sysOperInsp",
						code: 'xtywjc',
					},
					{
						name: "黑名单",
						path: "/monitor/blackList",
						code: 'hmd',
					},
					{
						name: "当前在线用户",
						path: "/monitor/currOnlineUser",
						code: 'dqzxyh',
					},
					{
						name: "用户在线时长",
						path: "/monitor/userOnlineDuration",
						code: 'yhzxsc',
					},
					{
						name: "访问规则告警",
						path: "/monitor/accessRuleAlarm",
						code: 'fwgzgj',
					},
				]
			},
			{
				name: "日志管理",
				index: "log",
				code: 'rzgl',
				children: [
					{
						name: "服务访问日志",
						path: "/log/serviceAccess",
						code: 'fwfwrz',
					},
					{
						name: "实例预约日志",
						path: "/log/instanceAppointment",
						code: 'slyyrz',
					},
					{
						name: "运维日志",
						path: "/log/O&M",
						code: 'ywrz',
					},
					{
						name: "异常告警日志",
						path: "/log/abnormalLogAlarm",
						code: 'ycgjrz',
					},
					{
						name: "系统异常日志",
						path: "/log/sysAlarmLog",
						code: 'xtycrz',
					},
				]
			},
			{
				name: "系统配置",
				index: "CFG",
				code: 'xtpz',
				children: [
					// {
					// 	name: "Cloud服务管理",
					// 	children: [
					// 		{
					// 			name: "渲染服务",
					// 			path: "/CFG/cloudRanderMGT",
					// 		},
					// 		{
					// 			name: "实例管理",
					// 			path: "/CFG/cloudInstanceMGT",
					// 		},
					// 	]
					// },
					{
						name: "服务引擎配置",
						path: "/CFG/serviceEngine",
						code: 'fwyqpz',
					},
					{
						name: "告警规则",
						path: "/CFG/alarmRules",
						code: 'gjgz',
					},
					{
						name: "元数据管理",
						path: "/CFG/metadataManagement",
						code: 'ysjgl',
					},
					{
						name: "标签管理",
						path: "/CFG/tagManagement",
						code: 'bqgl',
					},
					{
						name: "系统映射",
						path: "/CFG/systemMapping",
						code: 'xtys',
					},
					{
						name: "网格配置",
						path: "/CFG/gridSetting",
						code: 'wgpz',
					},
					{
						name: "坐标系配置",
						path: "/CFG/coordinateSetting",
						code: 'zbxpz',
					},
					{
						name: "全息展示中心配置",
						path: "/CFG/holographicDisplayCenter",
						code: 'qxzszxpz',
					},
					{
						name: "CIM等级管理",
						path: "/CFG/cimGradeManage",
						code: 'cimdjgl',
					},
					{
						name: "区域管理",
						path: "/CFG/districtManage",
						code: 'cimqygl',
					},
					{
						name: "风格样式",
						path: "/CFG/styleSheet",
						code: 'style',
					},
				]
			},
		],
	},
];

initTreeNode = (treedata, parm) => {
	this.findNode = (list) => {
		let nodes = [];
		list.filter((item) => {
			let _id = item.topic;
			if (!_id) {
				let arr = item.path.split("/");
				_id = arr[arr.length - 1];
			}
			let _data = {
				id: _id,
				label: item.name,
			};
			if (Object.prototype.toString.call(parm) === "[object Object]") {
				for (let key in parm) {
					_data[key] = parm[key]
				}
			}
			if (item.children) {
				_data.children = this.findNode(item.children);
			}
			nodes.push(_data);
		});
		return nodes;
	};
	return this.findNode(treedata)
}

//每个cim等级的边界
const CIM3dtBoundary = {
	CIM1: [[443881.6125, 2428671.845, 0.1], [594244.83, 2428671.845, 0.1], [594244.83, 2579035.06, 0.1], [443881.6125, 2579035.06, 0.1], [443881.6125, 2428671.845, 0.1]],
	CIM2: [[473934.2425, 2478026.595, 0.2], [564199.68, 2478026.595, 0.2], [564199.68, 2529721.625, 0.2], [473934.2425, 2529721.625, 0.2], [473934.2425, 2478026.595, 0.2]],
	CIM3: [[491942.795, 2490769.1875, 0.3], [494014.815, 2490769.1875, 0.3], [494014.815, 2493235.4525, 0.3], [491942.795, 2493235.4525, 0.3], [491942.795, 2490769.1875, 0.3]],
	CIM4: [[489908, 2489939.103125, 0.4], [495842.945, 2489939.103125, 0.4], [495842.945, 2495850.237188, 0.4], [489908, 2495850.237188, 0.4], [489908, 2489939.103125, 0.4]],
	CIM5: [[492787.09375, 2492129.75, 0.5], [492741.46875, 2492003, 0.5], [492742.25, 2491973.75, 0.5], [492767.34375, 2491951.5, 0.5], [492810.6875, 2491945.5, 0.5], [492878.53125, 2491945.25, 0.5], [492990.21875, 2491942.75, 0.5], [492989.78125, 2492026.5, 0.5], [492979.3125, 2492028.5, 0.5], [492923.34375, 2492032.5, 0.5], [492904.4375, 2492043, 0.5], [492889.0625, 2492062.75, 0.5], [492882.5, 2492075, 0.5], [492876.03125, 2492096.75, 0.5], [492848.125, 2492105, 0.5], [492787.09375, 2492129.75, 0.5]],
	CIM6: [[492793.9375, 2492116.25, 0.6], [492745.75, 2491998.25, 0.6], [492752.8125, 2491966, 0.6], [492773.625, 2491951, 0.6], [492820.6875, 2491948.5, 0.6], [492877.28125, 2491948.75, 0.6], [492915.96875, 2491947.25, 0.6], [492918.375, 2492030, 0.6], [492903.21875, 2492044.25, 0.6], [492887.625, 2492060.25, 0.6], [492881, 2492076, 0.6], [492876.28125, 2492094.75, 0.6], [492842.09375, 2492104.75, 0.6], [492841.75, 2492104.75, 0.6], [492841.75, 2492104.75, 0.6], [492793.9375, 2492116.25, 0.6]]
}

