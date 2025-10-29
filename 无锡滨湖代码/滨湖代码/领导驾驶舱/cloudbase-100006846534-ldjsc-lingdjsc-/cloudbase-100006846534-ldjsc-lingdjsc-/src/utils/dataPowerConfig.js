// 数据权限，全部画册获取配置
export const dataPowerTypes = [
	{
		id: 1,
		name: "模型管理",
		children: [{
			name: "CIM1",
			path: "",
			topic: "A050",
			children: [{
				name: "地形模型 CIM1",
				topic: "A050B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A050B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "行政区模型 CIM1",
				topic: "A050B001",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A050B001",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "居民区模型 CIM1",
				topic: "A050B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A050B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "水系模型 CIM1",
				topic: "A050B003",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A050B003",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "交通模型 CIM1",
				topic: "A050B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A050B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		{
			name: "CIM2",
			path: "",
			topic: "A051",
			children: [{
				name: "地形模型 CIM2",
				topic: "A051B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A051B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "行政区模型 CIM2",
				topic: "A051B001",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A051B001",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型 CIM2", // 建筑外观模型
				topic: "A051B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A051B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "交通模型 CIM2",
				topic: "A051B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A051B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "水系模型 CIM2",
				topic: "A051B005",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A051B005",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "植被模型 CIM2",
				topic: "A051B006",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A051B006",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		{
			name: "CIM3",
			path: "",
			topic: "A052",
			children: [{
				name: "地形模型 CIM3",
				topic: "A052B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型 CIM3",
				topic: "A052B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "交通模型 CIM3",
				topic: "A052B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "水系模型 CIM3",
				topic: "A052B005",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B005",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "植被模型 CIM3",
				topic: "A052B006",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B006",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "管线管廊模型 CIM3",
				topic: "A052B007",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B007",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tableData: [],
			},
			{
				name: "场地模型 CIM3",
				topic: "A052B008",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B008",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "城市部件模型 CIM3",
				topic: "A052B009",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A052B009",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		{
			name: "CIM4",
			path: "",
			topic: "A053",
			children: [{
				name: "地形模型 CIM4",
				topic: "A053B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型 CIM4", // 建筑外观模型
				topic: "A053B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},

			{
				name: "交通模型 CIM4",
				topic: "A053B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "水系模型 CIM4",
				topic: "A053B005",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B005",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tableData: [],
			},
			{
				name: "植被模型 CIM4",
				topic: "A053B006",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B006",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "管线管廊模型 CIM4",
				topic: "A053B007",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B007",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "场地模型 CIM4",
				topic: "A053B008",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B008",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "市政设施模型 CIM4",
				topic: "A053B009",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B009",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "地下空间模型 CIM4",
				topic: "A053B0010",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B0010",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "地质模型 CIM4",
				topic: "A053B0011",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B0011",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "城市部件模型 CIM4",
				topic: "A053B0012",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A053B0012",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		{
			name: "CIM5",
			path: "",
			topic: "A054",
			children: [{
				name: "建筑模型 CIM5", // 建筑外观模型
				topic: "A054B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A054B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},

			{
				name: "交通模型 CIM5",
				topic: "A054B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A054B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "场地模型 CIM5",
				topic: "A054B003",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A054B003",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "市政设施模型 CIM5",
				topic: "A054B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A054B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "地下空间模型 CIM5",
				topic: "A054B005",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A054B005",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		{
			name: "CIM6",
			path: "",
			topic: "A055",
			children: [{
				name: "建筑模型 CIM6", // 建筑外观模型
				topic: "A055B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A055B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},

			{
				name: "交通模型 CIM6",
				topic: "A055B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A055B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "场地模型 CIM6",
				topic: "A055B003",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A055B003",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "市政设施模型 CIM6",
				topic: "A055B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A055B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tableData: [],
			},
			{
				name: "地下空间模型 CIM6",
				topic: "A055B005",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A055B005",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		{
			name: "CIM7",
			path: "",
			topic: "A056",
			children: [{
				name: "建筑模型暖通专业模型 CIM7",
				topic: "A056B000",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B000",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型给排水专业模型 CIM7",
				topic: "A056B001",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B001",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型电气专业模型 CIM7",
				topic: "A056B002",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B002",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型智能化专业模型 CIM7",
				topic: "A056B003",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B003",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型小市政专业模型 CIM7",
				topic: "A056B004",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B004",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "建筑模型夜景照明专业模型 CIM7",
				topic: "A056B005",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B005",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			{
				name: "其他模型 CIM7",
				topic: "A056B006",
				getUrl: `${cim_main}service/model/list`,
				getCall: "generalPostEvent",
				getParams: {
					pid: "A056B006",
					limit: 10,
					start: 0,
				},
				expand: {
					url: `${cim_server}service/3dt/list`,
					parm: {
						topic: "",
					},
					tabArgsEx: [{
						key: "name",
						label: "数据名称",
					}, {
						key: "size",
						label: "大小",
					}, {
						key: "userName",
						label: "上传人",
					}, {
						key: "createtime",
						label: "上传时间",
					}]
				},
				tabArgs: [{
					key: "name",
					label: "画册名称",
				},
				{
					key: "counts",
					label: "模型量",
				},
				// {
				// 	key: "description",
				// 	label: "描述",
				// },
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},
			],
		},
		],
	},
	// {
	//   id: 4,
	//   name: "数据融合",
	//   data: [],
	// },
	{
		id: 2,
		name: "场景组装",
		children: [{
			name: "场景组装 01",
			path: "",
			children: [{
				name: "场景服务",
				getUrl: `${cim_server}service/media/list`,
				getParams: {
					pid: "3742000", // customize
					rows: 999999,
					page: 1,
				},
				getCall: "generalPostEvent",
				tabArgs: [{
					key: "name",
					label: "数据名称",
				},
				{
					key: "remark",
					label: "场景描述",
				},
				{
					key: "cycle",
					label: "链接过期",
				},
				{
					key: "userName",
					label: "创建人",
				},
				{
					key: "createtime",
					label: "创建时间",
				},
				],
				tableData: [],
			},],
		},],
	},
]