var sysConfig = { // eslint-disable-line no-unused-vars
  map: {
    mapType: 'superMap', // gaodeMap openlayerMap gaodePliceMap
    key: '66bb68fc7e755c2d8059eb0a8a5a8389',
    mapUrl: 'http://10.14.216.12:8290/iserver/services/map-ugcv5-dzslzh/rest/maps/dzsl_xiu',
    apiUrl: location.protocol + '//' + location.hostname + ':8096/libs/',
    mapCsysType: 1 // 地图坐标系 csysType 坐标系类型，1表示WGS84,4表示GCJ02,3表示BD09(百度坐标系）
  },
  cityEyeVideo: {
    url: 'http://10.1.47.58/application/gov-embedded-video',
    token: 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyIiwidXNlck5hbWUiOiLono3lkIjmjIfmjKUiLCJvcmdJZCI6IuadqOaziSIsIm9yZ05hbWUiOiIxMzIxMjcwMDcyMyIsImNyZWF0ZVRpbWUiOjE2NDY0Njg5NzY5NTgsImV4cCI6NDEwMjQxNjAwMDAwMH0.tsY9MvzlMAUbGosVQFcJ0J8tZ7epjUlAlqixRSuAjBc',
    id: '421083199802252412'
  },
  requestUrl: 'http://10.1.50.120/CoordReverse/mapTransform/hitLayerByXY',
  defaultPrecinct: {
    precinctId: '',
    precinctName: '成都市'
  },
  whiteUrl: 'http://2.21.137.156:8081/map/3d-tiles/tileset.json',
  playerUrl: {
    ip: '10.1.228.132',
    port: '6605',
    jsession: ''
  },
  // 市区 --- ip电话
  phoneUrl: {
    url: 'ws://10.1.47.199:1000',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 续简厂家
  xujianUrl: {
    server: 'http://10.14.216.182:8080',
    account: 'zhzx2',
    password: 'a0315z'
  },
  // 温江区
  wenjiangUrl: {
    server: 'http://10.40.201.175:8080',
    account: 'cs',
    password: 'abcd1234'
  },
  // 市
  wenjiangUrl1: {
    server: 'http://10.1.248.92:8080',
    account: 'csyx',
    password: 'csyx2022'
  },
  // 东部新区
  eastnewUrl: {
    server: 'http://10.190.162.5:8080',
    account: 'zhzx',
    password: 'abcd1234'
  },
  // 新津
  newjinUrl: {
    server: 'http://10.45.184.46:8080',
    account: 'xinjin',
    password: 'abcd1234'
  },
  // 新都区
  xinduUrl: {
    server: 'http://10.1.213.219:8080',
    account: 'xindu',
    password: 'abcd1234'
  },
  // 威尔信厂家
  // 青白江区 --- ip电话
  qingbaijiangUrl: {
    url: 'ws://10.1.47.199:1000',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 全屏视频配置--- 市级
  AllChengduUrl: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 郫都区(市级沿用金牛市的jinniuUrl1)
  piduUrl: {
    url: 'ws://10.41.200.7:1000',
    rtsp: 'http://10.41.200.8:8078/stream',
    id: 'Default',
    gonghao: '609001',
    fenji: '609001',
    zuoxi: '座席1组'
  },
  // 金牛市
  jinniuUrl: {
    url: 'ws://10.28.206.173:1000',
    rtsp: 'http://10.28.206.173:8078/stream',
    id: 'Default',
    gonghao: '6001',
    fenji: '6001',
    zuoxi: '座席1组'
  },
  jinniuUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 锦江区
  jinjiangUrl: {
    url: 'ws://10.1.213.204:1000',
    rtsp: 'http://10.1.213.204:8078/stream',
    id: 'Default',
    gonghao: '601001',
    fenji: '601001',
    zuoxi: '座席1组'
  },
  jinjiangUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 金堂县
  jintangUrl: {
    url: 'ws://10.1.213.32:1000',
    rtsp: 'http://10.1.213.32:8078/stream',
    id: 'Default',
    gonghao: '601001',
    fenji: '601001',
    zuoxi: '座席3组'
  },
  jintangUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 大邑县
  dayiUrl: {
    url: 'ws://10.1.213.210:1000',
    rtsp: 'http://10.1.213.210:8078/stream',
    id: 'Default',
    gonghao: '611002',
    fenji: '611002',
    zuoxi: '座席1组'
  },
  dayiUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 都江堰市
  dujiangyanUrl: {
    url: 'ws://10.1.213.215:1000',
    rtsp: 'http://10.1.213.215:8078/stream',
    id: 'Default',
    gonghao: '617001',
    fenji: '617001',
    zuoxi: '座席1组'
  },
  dujiangyanUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 邛崃市
  qinlaiUrl: {
    url: 'ws://10.1.213.208:1000',
    rtsp: 'http://10.1.213.208:8078/stream',
    id: 'Default',
    gonghao: '615001',
    fenji: '615001',
    zuoxi: '座席1组'
  },
  qinlaiUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 青羊区
  qinyangUrl: {
    url: 'ws://10.248.30.163:1000',
    rtsp: 'http://10.248.30.163:8078/stream',
    id: 'Default',
    gonghao: '8002',
    fenji: '8002',
    zuoxi: '座席1组'
  },
  qinyangUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 简阳市
  jianyangUrl: {
    url: 'ws://10.47.38.63:1000',
    rtsp: 'http://10.47.38.63:8078/stream',
    id: 'Default',
    gonghao: '620003',
    fenji: '620003',
    zuoxi: '座席1组'
  },
  jianyangUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 成华区
  chenghuaUrl: {
    url: 'ws://10.47.38.63:1000',
    rtsp: 'http://10.47.38.63:8078/stream',
    id: 'Default',
    gonghao: '620003',
    fenji: '620003',
    zuoxi: '座席1组'
  },
  chenghuaUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 蒲江县
  pujiangUrl: {
    url: 'ws://10.44.244.35:1000',
    rtsp: 'http://10.44.244.35:8078/stream',
    id: 'Default',
    gonghao: '612002',
    fenji: '612002',
    zuoxi: '座席1组'
  },
  pujiangUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 武侯区
  wuhouUrl: {
    url: 'ws://10.29.118.20:1000',
    rtsp: 'http://10.29.118.20:8078/stream',
    id: 'Default',
    gonghao: '604001',
    fenji: '604001',
    zuoxi: '座席1组'
  },
  wuhouUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 彭州市
  pengzhouUrl: {
    url: 'ws://10.1.212.30:1000',
    rtsp: 'http://10.1.212.30:8078/stream',
    id: 'Default',
    gonghao: '2000',
    fenji: '2000',
    zuoxi: '座席1组'
  },
  pengzhouUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  // 双流区
  shuangliuUrl: {
    name: '100001',
    ps: '100001',
    ip: '10.39.235.53'
  },
  shuangliuUrl1: {
    url: 'ws://10.1.47.199:1000',
    rtsp: 'http://10.1.47.40:8078/stream',
    id: 'Default',
    gonghao: '8800',
    fenji: '8800',
    zuoxi: '座席1组'
  },
  backUrl: 'http://10.1.50.137:7777/boss/release/cyzx3.0', // 右上角点击返回地址
  downPCM: 'http://10.1.228.132:29005/product/CMSV6PcmTool/CMSV6PcmTool.exe',
  getSessionUrl: 'http://10.1.228.132:29005/StandardApiAction_login.action?account=db&password=cccc123!@%23#',
  closeSessionUrl: 'http://10.1.228.132:29005/StandardApiAction_logout.action?jsession=',
  // 视频会商接口
  videoUrl: {
    src: 'http://10.1.17.12:8070/live/aE0n399B9R.flv?A=11111znvkO9Os3',
    name: '',
    type: 'flv'
  },
  // rtsp视频socket url
  rtspWebSocketUrl: 'ws://10.242.56.251:8060/ws',
  eventDemo: [{
    id: 0,
    time: '06-12 09:32',
    background: 'rgb(64, 158, 255)',
    border: '1rem solid rgb(129, 182, 236)',
    description: '市应急指挥中心接到凤凰社区人员报告：环城南路进出城涵洞已积水达50毫米，请求指挥中心组织救援处置。'
  }, {
    id: 1,
    time: '06-12 09:35',
    background: 'rgb(64, 158, 255)',
    border: '1rem solid rgb(129, 182, 236)',
    description: '指挥中心通过调度台发出救援指令：由于达州市区遭受强降雨天气，市区突降大到暴雨，西城环城南路进出城涵洞已积水达50毫米，请市政管理处防涝应急险队派出队伍进行紧急处置。'
  }, {
    id: 2,
    time: '06-12 09:35',
    background: 'rgb(64, 158, 255)',
    border: '1rem solid rgb(129, 182, 236)',
    description: '救援队长：报告指挥中心，指令已接到，我队立即出发。'
  }, {
    id: 3,
    time: '06-12 09:36',
    background: 'rgb(64, 158, 255)',
    border: '1rem solid rgb(129, 182, 236)',
    description: '指挥中心：10分钟后，救援车辆到达指定地点。'
  }, {
    id: 4,
    time: '06-12 09:46',
    background: 'rgb(255, 66, 0)',
    border: '1rem solid rgb(252, 150, 114)',
    description: '救援队长到达地点，下达指令：所有突击队员全部下车进行突击抢险。路两端设置禁行标志，放置排涝设备，经过长达10多分钟抢险，险情排除。'
  }, {
    id: 5,
    time: '06-12 09:52',
    background: 'rgb(255, 66, 0)',
    border: '1rem solid rgb(252, 150, 114)',
    description: '救援队长向指挥中心报告：报告指挥中心，环城南路险情已排除，请指示。'
  }, {
    id: 6,
    time: '06-12 09:53',
    background: 'rgb(255, 66, 0)',
    border: '1rem solid rgb(252, 150, 114)',
    description: '指挥中心：请保留部分人员和设备观察待命，其余人员返回。'
  }, {
    id: 7,
    time: '06-12 09:53',
    background: 'rgb(255, 66, 0)',
    border: '1rem solid rgb(252, 150, 114)',
    description: '救援队长：收到。'
  }, {
    id: 8,
    time: '06-12 09:54',
    background: 'rgb(255, 66, 0)',
    border: '1rem solid rgb(252, 150, 114)',
    description: '救援队长对留达人员以外人员下达指令：除留观人员外其余全部返回。'
  }, {
    id: 9,
    time: '06-12 09:55',
    background: 'rgb(255, 66, 0)',
    border: '1rem solid rgb(252, 150, 114)',
    description: '指挥中心:处置完毕。'
  }],
  area: [{
    id: '510110',
    name: '天府新区',
    showWhere: "COUTRICT_CODE='510110'",
    Dname: 'TFXQ',
    gpsx: '104.12566913250011',
    gpsy: '30.399823196000057'
  }, {
    id: '510186',
    name: '东部新区',
    showWhere: "COUTRICT_CODE='510111'",
    Dname: 'DBXQ',
    gpsx: '104.37328739300006',
    gpsy: '30.398897633500063'
  }, {
    id: '510187',
    name: '高新区',
    showWhere: "COUTRICT_CODE='510109'",
    Dname: 'GXQ',
    gpsx: '104.12566913250011',
    gpsy: '30.399823196000057'
  }, {
    id: '510104',
    name: '锦江区',
    showWhere: "COUTRICT_CODE='510104'",
    Dname: 'JJQ',
    gpsx: '104.11282708550006',
    gpsy: '30.611104563000065'
  }, {
    id: '510105',
    name: '青羊区',
    showWhere: "COUTRICT_CODE='510105'",
    Dname: 'QYQ',
    gpsx: '103.9907109615001',
    gpsy: '30.687983970000047'
  }, {
    id: '510106',
    name: '金牛区',
    showWhere: "COUTRICT_CODE='510106'",
    Dname: 'JNQ',
    gpsx: '104.04523597750004',
    gpsy: '30.737672904000057'
  }, {
    id: '510107',
    name: '武侯区',
    showWhere: "COUTRICT_CODE='510107'",
    Dname: 'WHQ',
    gpsx: '104.01859564050011',
    gpsy: '30.620836283000045'
  }, {
    id: '510108',
    name: '成华区',
    showWhere: "COUTRICT_CODE='510108'",
    Dname: 'CHQ',
    gpsx: '104.1509951370001',
    gpsy: '30.685773947000058'
  },
  // {
  //   id: '510109',
  //   name: '高新南区',
  //   showWhere: "COUTRICT_CODE='510109'",
  //   Dname: 'GXNQ',
  //   gpsx: '104.07140749750008',
  //   gpsy: '30.566792923000033'
  // },
  {
    id: '510112',
    name: '龙泉驿区',
    showWhere: "COUTRICT_CODE='510112'",
    Dname: 'LQYQ',
    gpsx: '104.29577451450008',
    gpsy: '30.594938385500047'
  }, {
    id: '510113',
    name: '青白江区',
    showWhere: "COUTRICT_CODE='510113'",
    Dname: 'QBJQ',
    gpsx: '104.32596918250005',
    gpsy: '30.78537920400003'
  }, {
    id: '510114',
    name: '新都区',
    showWhere: "COUTRICT_CODE='510114'",
    Dname: 'XDQ',
    gpsx: '104.09142923350007',
    gpsy: '30.821851754000022'
  }, {
    id: '510115',
    name: '温江区',
    showWhere: "COUTRICT_CODE='510115'",
    Dname: 'WJQ',
    gpsx: '103.81395260500007',
    gpsy: '30.75114036050009'
  }, {
    id: '510116',
    name: '双流区',
    showWhere: "COUTRICT_CODE='510116'",
    Dname: 'SLQ',
    gpsx: '103.91773336900008',
    gpsy: '30.474096092000078'
  }, {
    id: '510117',
    name: '郫都区',
    showWhere: "COUTRICT_CODE='510117'",
    Dname: 'PDQ',
    gpsx: '103.8805092860001',
    gpsy: '30.84105903000004'
  }, {
    id: '510118',
    name: '新津区',
    showWhere: "COUTRICT_CODE='510118'",
    Dname: 'XJQ',
    gpsx: '103.81870013550007',
    gpsy: '30.428320257500076'
  },
  // {
  //   id: '510119',
  //   name: '高新西区',
  //   showWhere: "name='GXXQ'",
  //   Dname: 'GXXQ',
  //   gpsx: '103.93098400100008',
  //   gpsy: '30.76433160350006'
  // },
  {
    id: '510185',
    name: '简阳市',
    showWhere: "name='JYS'",
    Dname: 'JYS',
    gpsx: '104.60033068500007',
    gpsy: '30.36882497300004'
  }, {
    id: '510181',
    name: '都江堰市',
    showWhere: "COUTRICT_CODE='510181'",
    Dname: 'DJYS',
    gpsx: '103.6063506995001',
    gpsy: '31.05883357150006'
  }, {
    id: '510182',
    name: '彭州市',
    Dname: 'PZS',
    showWhere: "COUTRICT_CODE='510182'",
    gpsx: '103.92261212650008',
    gpsy: '31.17491628300008'
  }, {
    id: '510183',
    name: '邛崃市',
    showWhere: "COUTRICT_CODE='510183'",
    Dname: 'QLS',
    gpsx: '103.41295927750006',
    gpsy: '30.38200139450004'
  }, {
    id: '510184',
    name: '崇州市',
    showWhere: "COUTRICT_CODE='510184'",
    Dname: 'CZS',
    gpsx: '103.4891647577719',
    gpsy: '30.79907503840192'
  }, {
    id: '510121',
    name: '金堂县',
    showWhere: "COUTRICT_CODE='510121'",
    Dname: 'JTX',
    gpsx: '104.61926599600005',
    gpsy: '30.719824562500055'
  }, {
    id: '510129',
    name: '大邑县',
    showWhere: "COUTRICT_CODE='510129'",
    Dname: 'DYX',
    gpsx: '103.37663492950007',
    gpsy: '30.62193942300004'
  }, {
    id: '510131',
    name: '蒲江县',
    showWhere: "COUTRICT_CODE='510131'",
    Dname: 'PJX',
    gpsx: '103.50102061750007',
    gpsy: '30.216652393000047'
  }],
  textTime: '2022-03-09',
  extendSettings: {
    frameTracking: true,
    updateOnStart: false,
    updateOnFocus: false,
    reconnect: false
  },
  layerDatas: [{ name: '消防站', group: '消防站', type: 'firehouse' },
    { name: '救援队伍装备', group: '救援队伍装备', type: 'helpequip' },
    { name: '专家', group: '专家', type: 'specialist' },
    { name: '避难场所', group: '避难场所', type: 'shelter' },
    { name: '医疗机构基本信息', group: '医院', type: 'host' },
    { name: '学校', group: '学校', type: 'school' },
    { name: '金堂县集中安置点', group: '金堂县集中安置点', type: 'centerpoint' },
    { name: '防汛抗旱物资仓库', group: '防汛抗旱物资仓库', type: 'fxbox' },
    { name: '市消防救援支队', group: '市消防救援支队', type: 'cityfire' },
    // { name: '消防救援站', group: '消防救援站', type: 'firestation' },
    { name: '市119指挥中心', group: '市119指挥中心', type: 'cityonenine' },
    // { name: '医疗站点', type: 'hostTip' },
    { name: '轨交线路', group: '轨交线路', type: 'subway' },
    { name: 'POIP_电站', group: 'POIP_电站', type: 'power' },
    { name: '加油加气站', group: '加油加气站', type: 'oil' },
    { name: '派出所', group: '派出所', type: 'police' },
    { name: '消防救援大队', group: '消防救援大队', type: 'firerescue' },
    // { name: '加气站', group: '医院', type: 'gas' },
    // { name: '体育场馆', type: 'gym' },
    { name: '危化企业', group: '危化企业', type: 'hazardous' },
    { name: '轨交站点', group: '轨交站点', type: 'metro' },
    { name: '市区政府', group: '市区政府', type: 'municipal' },
    { name: '地质灾害隐患点', group: '地质灾害隐患点', type: 'potential' },
    { name: '山洪灾害点', group: '山洪灾害点', type: 'flashflood' },
    { name: '治安岗亭', group: '部件数据', type: 'sentry' }]
}
export default sysConfig
