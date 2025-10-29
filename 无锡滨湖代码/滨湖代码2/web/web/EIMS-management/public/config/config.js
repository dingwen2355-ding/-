/* eslint-disable */
const configs = {
  map: {
    mapType: 'gaodeMap', // gaodeMap openlayerMap gaodePliceMap superMap
    mapIp: 'http://www.sdmap.gov.cn/',
    key: '66bb68fc7e755c2d8059eb0a8a5a8389',
    mapUrl: '',
    apiUrl: location.protocol + '//' + location.hostname + ':8090/libs/',
    mapCsysType: 1 // 地图坐标系 csysType 坐标系类型，1表示WGS84,4表示GCJ02,3表示BD09(百度坐标系）
  },
  wareHouseLevel: '300', // 仓库管理员等级配置
  // precinctId: '31011521',
  // precinctName: '洋泾街道',
  beforeTitle: '', // 淄博市 临淄区
  afterTitle: '应急管理业务系统',
  regionName: '',
  // 系统监控 --> 接口一览 http地址配置
  interfaceOverview: 'http://' + location.hostname + ':9005/swagger-ui.html#/',
  // interfaceOverview: '//172.16.7.11:9005/swagger-ui.html#/',
  menuList: [
    {
      name: '预案管理',
      isShow: false,
      icon: 'icon-guanlipingtai_zhibanguanli',
      id: '',
      path: '',
      menus: [
        {
          name: '我的预案',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '920',
          path: '/myPlan'
        },
        {
          name: '应急预案',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '921',
          path: '/emergencyPlan'
        },
        {
          name: '预案数字化流程',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '922',
          path: '/planDigitalProcess'
        }
      ]
    },
   {
      name: '物资管理',
      isShow: false,
      icon: 'icon-guanlipingtai_zhibanguanli',
      id: '',
      path: '',
      menus: [
        {
          name: '物资名录',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '914',
          path: '/materialList'
        },
        {
          name: '物资仓库管理',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '839',
          path: '/storage'
        },
        {
          name: '物资出入库管理',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '847',
          path: '/storageOutIn'
        },
        {
          name: '物资出入库明细',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '855',
          path: '/storageOutInDetail'
        },
        {
          name: '物资协议储备',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '915',
          path: '/MaterialAgreementReserve'
        },
        {
          name: '物资储备限额',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '916',
          path: '/materialReserveLimit'
        },
        {
          name: '物资库存',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '863',
          path: '/storageInventory'
        },
        {
          name: '物资仓库台账',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '864',
          path: '/materialWarehouse'
        }
      ]
    },
    {
      name: '系统管理',
      isShow: false,
      icon: 'icon-guanlipingtai_zhibanguanli',
      id: '',
      path: '',
      menus: [
        {
          name: '用户管理',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: 'qianduan-1',
          path: '/user'
        },
        {
          name: '角色管理',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: 'qianduan-2',
          path: '/role'
        },
        {
          name: '组织管理',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: 'qianduan-3',
          path: '/organizationManagement'
        },
        {
          name: '模块管理',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: 'qianduan-4',
          path: '/module'
        }
      ]
    },
    {
      name: '系统监控',
      isShow: false,
      icon: 'icon-guanlipingtai_zhibanguanli',
      id: '',
      path: '',
      menus: [
        {
          name: 'nacos配置',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '970',
          path: '/configNacos'
        },
        {
          name: '网关路由',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '971',
          path: '/gatewayRouting'
        },
        {
          name: '接口一览',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '972',
          path: '/InterfaceOverview'
        }
      ]
    },
    {
      name: '信息发布',
      isShow: false,
      icon: 'icon-guanlipingtai_zhibanguanli',
      id: '',
      path: '',
      menus: [
        {
          name: '信息模板',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '927',
          path: '/infoTemplate'
        },
        {
          name: '预警发布',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '926',
          path: '/alarmNews'
        },
        {
          name: '我的信息',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '903',
          path: '/informationRelease'
        },
        {
          name: '信息审核',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '904',
          path: '/AuditInformation'
        },
        {
          name: '发布方式',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '953',
          path: '/ReleaseWay'
        },
        {
          name: '状态巡检',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '954',
          path: '/StateInspection'
        },
        {
          name: '发布日志',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '955',
          path: '/ReleaseLog'
        },
        {
          name: '灾情信息报送',
          isShow: false,
          icon: 'icon-guanlipingtai_kanban',
          id: '956',
          path: '/DisasterInformationReporting'
        }
      ]
    }
  ]
}
