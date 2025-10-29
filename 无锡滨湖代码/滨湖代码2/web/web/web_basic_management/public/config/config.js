const configs = {
  // eslint-disable-line no-unused-vars
  map: {
    mapType: 'gaodeMap', // gaodeMap openlayerMap gaodePliceMap
    key: 'c65721a87647f53f10a656b572c9a5ba',
    mapUrl: 'http://10.45.157.179:8008/',
    mapCsysType: 4, // 地图坐标系 csysType 坐标系类型，1表示WGS84,4表示GCJ02,3表示BD09(百度坐标系）
    contourType: 0, // 0：区域轮廓，1：网格轮廓
    showTwoLevelGridZoom: 16 // 地图超过多少级展示二级网格
  },
  hik: {
    appkey: '22009555',
    secret: '0HR20hTP1aW6n667iksk',
    ip: '172.20.20.2',
    port: 443,
    enableHTTPS: 1
  },
  title: '滨湖区城市运行管理平台',
  precinctId: '31011521',
  defaultMediaUrl: 'http://172.16.7.11:8031/files/dapeng/总结分析系统.mp4',
  precinctName: '洋泾街道',
  menuList: [
    {
      name: '事件管理',
      isShow: false,
      icon: 'el-icon-help',
      id: '930',
      path: '/drillEventEntry',
      menus: [
        {
          name: '事件汇聚',
          isShow: false,
          icon: 'el-icon-help',
          id: '902',
          path: '/drillEventEntry',
          menus: [
            {
              name: '信息接报',
              path: '/drillEventEntry'
            },
            {
              name: '事件管理',
              path: '/eventManage'
            }
          ]
        },
        {
          name: '事件处置',
          isShow: false,
          icon: 'el-icon-help',
          id: '903',
          path: '/handleEvent',
          menus: [
            {
              name: '事项受理',
              path: '/handleEvent'
            },
            {
              name: '周边分析',
              path: '/perimeterAnalysis'
            },
            {
              name: '预案响应',
              path: '/planResponse'
            },
            {
              name: '任务下发',
              path: '/taskSupplementaryEntry'
            }
          ]
        },
        {
          name: '事件总结',
          isShow: false,
          icon: 'el-icon-help',
          id: '904',
          path: '/processReproduction',
          menus: [
            {
              name: '过程再现',
              path: '/processReproduction'
            },
            {
              name: '事件评估',
              path: '/applicationCapabilityEvaluation'
            }
          ]
        },
        {
          name: '事件统计',
          isShow: false,
          icon: 'el-icon-help',
          id: '905',
          path: '/trendAnalysis',
          menus: [
            {
              name: '统计分析',
              path: '/trendAnalysis'
            }
          ]
        }
      ]
    },
    {
      name: '应急物资',
      isShow: false,
      icon: 'el-icon-s-promotion',
      id: '931',
      path: '/rescueTeams',
      menus: [
        {
          name: '队伍力量',
          isShow: false,
          icon: 'el-icon-s-promotion',
          id: '906',
          path: '/rescueTeams',
          menus: [
            {
              name: '救援队伍',
              path: '/rescueTeams'
            },
            {
              name: '应急专家',
              path: '/emergencyExpertManagement'
            }
          ]
        },
        {
          name: '物资力量',
          isShow: false,
          icon: 'el-icon-s-promotion',
          id: '907',
          path: '/materialManage',
          menus: [
            {
              name: '物资管理',
              path: '/materialManage'
            },
            {
              name: '出入库记录',
              path: '/materialUse'
            }/*,
            {
              name: '物资统计',
              path: '/storageOutIn'
            } */
          ]
        },
        {
          name: '避难场所管理',
          isShow: false,
          icon: 'el-icon-s-promotion',
          id: '908',
          path: '/siteManage',
          menus: [
            {
              name: '场所管理',
              path: '/siteManage'
            },
            {
              name: '场所分布',
              path: '/distributionPlace'
            }
          ]
        },
        {
          name: '风险管理',
          isShow: false,
          icon: 'el-icon-s-promotion',
          id: '909',
          path: '/riskDanger',
          menus: [
            {
              name: '风险隐患',
              path: '/riskDanger'
            },
            {
              name: '防护目标',
              path: '/protectiveTarget'
            }
          ]
        },
        {
          name: '硬件管理',
          isShow: false,
          icon: 'el-icon-s-promotion',
          id: '910',
          path: '/VideoMonitoring',
          menus: [
            {
              name: '视频监控',
              path: '/VideoMonitoring'
            }
          ]
        },
        // {
        //   name: '指挥体系',
        //   isShow: false,
        //   icon: 'el-icon-s-promotion',
        //   id: '911',
        //   path: '/siteManage',
        //   menus: [
        //     {
        //       name: '通信群组',
        //       path: '/siteManage'
        //     }
        //   ]
        // },
        {
          name: '应急知识库',
          isShow: false,
          icon: 'el-icon-s-promotion',
          id: '912',
          path: '/standardSpecification',
          menus: [
            {
              name: '标准规范',
              path: '/standardSpecification'
            },
            {
              name: '法律法规',
              path: '/lawsAndRegulations'
            },
            {
              name: '历史事件',
              path: '/historicalEvent'
            }
          ]
        }
      ]
    },
    {
      name: '数字预案',
      isShow: false,
      icon: 'el-icon-edit-outline',
      id: '932',
      path: '/EmergencyPlanNew',
      menus: [
        {
          name: '预案管理',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '913',
          path: '/EmergencyPlanNew',
          menus: [
            {
              name: '我的预案',
              path: '/EmergencyPlanNew'
            }
          ]
        },
        {
          name: '预案数字化',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '914',
          path: '/myPlanIn',
          menus: [
            {
              name: '预案指挥体系',
              path: '/myPlanIn'
            },
            {
              name: '预案拆解',
              path: '/planDisassembly'
            }
          ]
        }
      ]
    },
    {
      name: '事项管理',
      isShow: false,
      icon: 'el-icon-news',
      id: '933',
      path: '/consultationNotice',
      menus: [
        // {
        //   name: '字典管理',
        //   isShow: false,
        //   icon: 'el-icon-edit-outline',
        //   id: '340',
        //   path: '/dictionaryConfiguration',
        //   menus: [
        //     {
        //       name: '字典项配置',
        //       path: '/dictionaryConfiguration'
        //     }
        //   ]
        // },
        {
          name: '通知管理',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '915',
          path: '/consultationNotice',
          menus: [
            {
              name: '资讯管理',
              path: '/consultationNotice'
            },
            {
              name: '通知管理',
              path: '/notificationManager'
            },
            {
              name: '活动管理',
              path: '/activityManagement'
            }
          ]
        },
        {
          name: '任务待办',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '916',
          path: '/myTasks',
          menus: [
            {
              name: '我的任务',
              path: '/myTasks'
            } /*,
            {
              name: '预案拆解',
              path: '/drillEventEntry'
            } */
          ]
        },
        {
          name: '业务库管理',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '917',
          path: '/lawsAndRegulationsDatabase',
          menus: [
            {
              name: '法律法规库',
              path: '/lawsAndRegulationsDatabase'
            },
            {
              name: '应急预案库',
              path: '/emergencyPlanLibrary'
            },
            {
              name: '文档资料库',
              path: '/documentDatabase'
            }
          ]
        },
        {
          name: '综合查询',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '918',
          path: '/classicCaseQuery',
          menus: [
            // {
            //   name: '知识库查询',
            //   path: '/difficultPain'
            // },
            {
              name: '经典案例查询',
              path: '/classicCaseQuery'
            }
          ]
        },
        {
          name: '值班管理配置',
          isShow: false,
          icon: 'el-icon-edit-outline',
          id: '917',
          path: '/',
          menus: [
            {
              name: '值班人员管理',
              path: '/dutyPeople'
            },
            {
              name: '值班模板管理',
              path: '/dutyTemplate'
            },
            {
              name: '值班角色管理',
              path: '/dutyRole'
            },
            {
              name: '人员排班管理',
              path: '/dutyManage'
            }
          ]
        }
      ]
    },
    {
      name: '系统管理',
      isShow: false,
      icon: 'el-icon-setting',
      id: '934',
      path: '/area',
      menus: [
        {
          name: '组织管理',
          isShow: false,
          icon: 'el-icon-setting',
          id: '919',
          path: '/area'
        },
        {
          name: '用户管理',
          isShow: false,
          icon: 'el-icon-setting',
          id: '920',
          path: '/user'
        },
        {
          name: '角色管理',
          isShow: false,
          icon: 'el-icon-setting',
          id: '921',
          path: '/role'
        },
        {
          name: '菜单管理',
          isShow: false,
          icon: 'el-icon-setting',
          id: '922',
          path: '/module'
        },

        {
          name: '字典管理',
          isShow: false,
          icon: 'el-icon-setting',
          id: '923',
          path: '/dictionaries'
        },
        // {
        //   name: '参数设置',
        //   isShow: false,
        //   icon: 'el-icon-setting',
        //   id: '900',
        //   path: '/parameterSetting'
        // },
        {
          name: '通知公告',
          isShow: false,
          icon: 'el-icon-setting',
          id: '924',
          path: '/noticeAnnouncement'
        },
        // {
        //   name: '操作日志',
        //   isShow: false,
        //   icon: 'el-icon-setting',
        //   id: '925',
        //   path: '/operationLog'
        // },
        {
          name: '日志管理',
          isShow: false,
          icon: 'el-icon-setting',
          id: '926',
          path: '/loginLog'
        }
        // {
        //   name: '表单设置',
        //   isShow: false,
        //   icon: 'el-icon-setting',
        //   id: '927',
        //   path: '/formSettings'
        // }
      ]
    }
  ],
  // 统一登录开关。true关闭统一登录
  superControl: true
}
