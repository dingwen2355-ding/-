const sysConfig = { // eslint-disable-line no-unused-vars
  superControl: true, //超级权限控制，开启可以绕过统一认证登录（需后台开关配合）
  map: {
    mapType: 'superMap', // gaodeMap openlayerMap gaodePliceMap
    key: '66bb68fc7e755c2d8059eb0a8a5a8389',
    mapUrl: 'http://10.14.216.12:8290/iserver/services/map-ugcv5-dzslzh/rest/maps/dzsl_xiu',
    apiUrl: location.protocol + '//' + location.hostname + ':8096/libs/',
    mapCsysType: 1 // 地图坐标系 csysType 坐标系类型，1表示WGS84,4表示GCJ02,3表示BD09(百度坐标系）
  },
  map3D: {
    src: 'http://2.21.137.156:8086/'
  },
  defaultPrecinct: {
    precinctId: '',
    precinctName: '成都市'
  },
  // whiteUrl: 'http://172.16.7.11:28082/baimo/3d-tiles/tileset.json',
  whiteUrl: 'http://2.21.137.156:8080/baimo/3d-tiles/tileset.json',
  playerUrl: {
    ip: '10.1.228.132',
    port: '6605',
    jsession: ''
  },
  backUrl: 'http://2.21.137.206:11510/presentation?id=44cd5979-c8cc-4745-81f9-7d766fb6cf95', // 右上角点击返回地址
  getSessionUrl: 'http://10.1.228.132:29005/StandardApiAction_login.action?account=db&password=cccc123!@%23#',
  closeSessionUrl: 'http://10.1.228.132:29005/StandardApiAction_logout.action?jsession=',
  eventDemo: [
    {
      id: 9,
      time: '04-12 18:52',
      background: 'rgb(255, 66, 0)',
      border: '1px solid rgb(252, 150, 114)',
      description: '实施清障，清理现场恢复交通。'
    },
    {
      id: 8,
      time: '04-12 17:47',
      background: 'rgb(255, 66, 0)',
      border: '1px solid rgb(252, 150, 114)',
      description: '油已抽完，开始起吊事故车。'
    },
    {
      id: 7,
      time: '04-12 16:12',
      background: 'rgb(255, 66, 0)',
      border: '1px solid rgb(252, 150, 114)',
      description: '两台油泵机到位运行。'
    },
    {
      id: 6,
      time: '04-12 15:37',
      background: 'rgb(255, 66, 0)',
      border: '1px solid rgb(252, 150, 114)',
      description: '开始卸驳载，边降温边驳载，预计完成需要两个多小时。'
    },
    {
      id: 5,
      time: '04-12 15:08',
      background: 'rgb(255, 66, 0)',
      border: '1px solid rgb(252, 150, 114)',
      description: '交通部门安排的接驳油罐车和栏板车（含泵桶）到达事故现场，同时部门持续对事故车物理降温，适时将展开卸驳载。'
    },
    {
      id: 4,
      time: '04-12 14:18',
      background: 'rgb(255, 66, 0)',
      border: '1px solid rgb(252, 150, 114)',
      description: '16吨接驳车到达事故现场。'
    },
    {
      id: 3,
      time: '04-12 13:51',
      background: 'rgb(64, 158, 255)',
      border: '1px solid rgb(129, 182, 236)',
      description: '交警负责在事故后方实行断流。'
    },
    {
      id: 2,
      time: '04-12 13:46',
      background: 'rgb(64, 158, 255)',
      border: '1px solid rgb(129, 182, 236)',
      description: '据报相关路段已有7公里拥堵，按照专家组意见采取交通管制措施；'
    },
    {
      id: 1,
      time: '04-12 13:41',
      background: 'rgb(64, 158, 255)',
      border: '1px solid rgb(129, 182, 236)',
      description: '据报交通部门安排卸泊车赶往现场；'
    },
    {
      id: 0,
      time: '04-12 09:32',
      background: 'rgb(64, 158, 255)',
      border: '1px solid rgb(129, 182, 236)',
      description: '实施清障，清理现场恢复交通。'
    }],
  extendSettings: {
    frameTracking: true,
    updateOnStart: false,
    updateOnFocus: false,
    reconnect: false
  },
  uavUrl:'https://124.71.204.212/hls/bhcyzbyx3539.flv',
  localVideo:{ //默认视频配置
    wrj: [   //无人机视频
      {
        id:'1',
        title:'无人机1',
        type:'flv',
        url:'http://124.71.204.212/hls/bhcyzbyx3538.flv'
      },{
        title:'无人机2',
        id:'8',
        type:'flv',
        url:'http://124.71.204.212/hls/bhcyzbyx3539.flv'
      },{
        title:'无人机3',
        id:'9',
        type:'mp4',
        url:'/files/media/9.mp4'
      }
    ],
    yddb:[     //移动单兵视频
      {
        id:'32021165151510000051',
        title:'移动单兵1',
        type:'HLS'
      },{
        title:'移动单兵2',
        id:'32021165151510000052',
        type:'HLS'
      }
    ],
    spjk:[   //视频监控
      {
        deviceId: 1,
        url: '/files/media/2.mp4',
        type: 'mp4'
      }, {
        deviceId: 2,
        url: '/files/media/3.mp4',
        type: 'mp4'
      }, {
        deviceId: 3,
        url: '/files/media/4.mp4',
        type: 'mp4'
      }, {
        deviceId: 4,
        url: '/files/media/5.mp4',
        type: 'mp4'
      }
    ]
  },
  topMenus:[
    {
      name: '滨湖城管',
      url: 'http://2.20.5.20:8688/bhcg/bigscreen/sso?serverName=${serverName}&code=${code}&time=${time}&account=${account}'
    },
    {
      name: '防汛防台',
      url: 'http://2.22.70.193:9600/one-map'
    },
    {
      name: '更多',
      menus: [
        {
          name: '滨湖自然规划局',
          url: 'http://2.20.41.4:18082/#/pretreatmentPage?serverName=${serverName}&code=${code}&time=${time}&account=${account}'
        }
      ]
    }
  ]
}
