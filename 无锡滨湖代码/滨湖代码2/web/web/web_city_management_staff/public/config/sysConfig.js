const sysConfig = { // eslint-disable-line no-unused-vars
  websocket: {
    // ip: '10.134.20.248', /*  大数据股份政务网环境 */
    // ip: '180.167.126.155', /* 互联网测试环境 */
    ip: '10.89.1.92', /* 大数据中心政务云环境 */
    port: '8083'
    // port: '8001'
  },
  defaultPrecinct: {
    'precinctId': '',
    'precinctName': '',
    'eventType': ''
  },
  // rtsp视频socket url
  // rtspWebSocketUrl: 'ws:127.0.0.1:8060/ws',
  rtspWebSocketUrl: 'ws:10.45.157.205:8060/ws',
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
  }]
}
