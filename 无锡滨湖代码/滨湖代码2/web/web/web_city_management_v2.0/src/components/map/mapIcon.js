export default [
  {
    name: '视频资源类',
    iconfont: 'icon-shipinziyuanlei',
    active: false,
    type: '',
    children: [
      {
        name: '移动单兵',
        iconfont: 'icon-jiuyuanduiwu',
        type: 'solo',
        num: 0,
        data: [],
        active: false,
        children: sysConfig.localVideo.yddb
      },
      {
        name: '固定视频',
        iconfont: 'icon-gudingshipin',
        type: 'video',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '布控球',
        iconfont: 'icon-bukongqiu',
        type: 'ball',
        num: 0,
        data: [],
        active: false
      },

      // {
      //   name: '政务微信',
      //   iconfont: 'icon-jiuyuanduiwu',
      //   type: 'service',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      {
        name: '无人机',
        iconfont: 'icon-wurenji',
        type: 'nopeopleair',
        num: 0,
        data: [],
        active: false,
        children: sysConfig.localVideo.wrj
      }
      // {
      //   name: '车载视频',
      //   iconfont: 'icon-wurenji',
      //   type: 'carVideo',
      //   num: 0,
      //   data: [],
      //   active: false
      // }
    ]
  },
  {
    name: '公共资源类',
    iconfont: 'icon-xiaofangjiuyuanlei',
    active: false,
    type: '',
    children: [
      // {
      //   name: '医疗机构',
      //   iconfont: 'icon-yiyuan',
      //   type: 'host',
      //   active: false
      // },
      {
        name: '学校',
        iconfont: 'icon-xuexiao',
        type: 'school',
        active: false
      },
      // {
      //   name: '气象站',
      //   iconfont: 'icon-binanchangsuo',
      //   type: 'metro',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      // {
      //   name: '水雨情监测站',
      //   iconfont: 'icon-binanchangsuo',
      //   type: 'water',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      // {
      //   name: '公交车',
      //   iconfont: 'icon-dankuang_keche',
      //   type: 'bus',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      // {
      //   name: '出租车',
      //   iconfont: 'icon-dankuang_huoche',
      //   type: 'taxi',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      {
        name: '水库',
        iconfont: 'icon-jiuyuanduiwu',
        type: 'waterThing',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '避难场所',
        iconfont: 'icon-binanchangsuo',
        type: 'shelter',
        num: 0,
        data: [],
        active: false
      }
    ]
  },
  {
    name: '公共安全类',
    iconfont: 'icon-gonggongziyuanlei',
    active: false,
    type: '',
    children: [
      {
        name: '派出所',
        iconfont: 'icon-paichusuo',
        type: 'police',
        active: false
      },
      // {
      //   name: '加气站',
      //   iconfont: 'icon-zhuanjia',
      //   type: 'gas',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      // {
      //   name: '体育场馆',
      //   iconfont: 'icon-tiyuchang',
      //   type: 'gym',
      //   active: false
      // },
      {
        name: '危化企业',
        iconfont: 'icon-tiyuchang',
        type: 'hazardous',
        active: false
      },
      {
        name: '重大危险源',
        iconfont: 'icon-dankuang_huoche',
        type: 'sentry',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '易灾点',
        iconfont: 'icon-binanchangsuo',
        type: 'easyPoint',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '易涝点',
        iconfont: 'icon-binanchangsuo',
        type: 'floodPoint',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '湖泊',
        iconfont: 'icon-binanchangsuo',
        type: 'lakePoint',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '河道',
        iconfont: 'icon-jiuyuanduiwu',
        type: 'riverRoadPoint',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '重点企业',
        iconfont: 'icon-binanchangsuo',
        type: 'KeyEnterprises',
        num: 0,
        data: [],
        active: false
      }
      // {
      //   name: '煤矿企业',
      //   iconfont: 'icon-binanchangsuo',
      //   type: 'coal',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      // {
      //   name: '危货车',
      //   iconfont: 'icon-dankuang_huoche',
      //   type: 'vehicle',
      //   num: 0,
      //   data: [],
      //   active: false
      // }
    ]
  },
  {
    name: '应急救援类',
    iconfont: 'icon-gonggonganquanlei',
    active: false,
    type: '',
    children: [
      {
        name: '消防站',
        iconfont: 'icon-zhuanjia',
        type: 'firehouse',
        num: 0,
        data: [],
        active: false
      },
      // {
      //   name: '运输力量',
      //   iconfont: 'icon-zhuanjia',
      //   type: 'firerescue',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      {
        name: '救护车',
        iconfont: 'icon-JOhuche',
        type: 'ambulance',
        num: 0,
        data: [],
        active: false
      },
      // {
      //   name: '市119指挥中心',
      //   iconfont: 'icon-zhuanjia',
      //   type: 'cityonenine',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      {
        name: '应急物资仓库',
        iconfont: 'icon-yingjizhuangbei',
        type: 'fxbox',
        num: 0,
        data: [],
        active: false
      },
      // {
      //   name: '救援队伍装备',
      //   iconfont: 'icon-zhuanjia',
      //   type: 'helpequip',
      //   num: 0,
      //   data: [],
      //   active: false
      // },
      {
        name: '市消防救援支队',
        iconfont: 'icon-zhuanjia',
        type: 'cityfire',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '应急专家',
        iconfont: 'icon-zhuanjia',
        type: 'specialist',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '应急救援队伍',
        iconfont: 'icon-zhuanjia',
        type: 'firestation',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '消防车',
        iconfont: 'icon-JOhuche',
        type: 'firetruck',
        num: 0,
        data: [],
        active: false
      }
    ]
  },
  // {
  //   name: '政府机关',
  //   iconfont: 'icon-zhengfujiguan',
  //   active: true
  // },
  // {
  //   name: '危化品车辆',
  //   iconfont: 'icon-dankuang_huoche',
  //   type: 'vehicle',
  //   num: 0,
  //   data: [],
  //   active: true
  // },
  // {
  //   name: '特殊场所类',
  //   iconfont: 'icon-jiuyuanduiwu',
  //   active: false,
  //   type: '',
  //   children: [
  //     {
  //       name: '市区机关',
  //       iconfont: 'icon-zhengfujiguan',
  //       type: 'municipal',
  //       num: 0,
  //       data: [],
  //       active: false
  //     }
  //   ]
  // },
  {
    name: '城市部件类',
    iconfont: 'icon-teshuchangsuolei',
    active: false,
    type: '',
    children: [
      {
        name: '公司企业',
        iconfont: 'icon-gongsiqiye',
        type: 'company',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '交通设施',
        iconfont: 'icon-jiaotongsheshi',
        type: 'traffic',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '旅游景点',
        iconfont: 'icon-lvyoujingdian',
        type: 'scenicSpot',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '生活服务',
        iconfont: 'icon-shegnhuofuwu',
        type: 'lifeService',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '休闲娱乐',
        iconfont: 'icon-xiuxianyule',
        type: 'recreation',
        num: 0,
        data: [],
        active: false
      },
      {
        name: '医疗保健',
        iconfont: 'icon-yiliaobaojian',
        type: 'healthcare',
        num: 0,
        data: [],
        active: false
      }
    ]
  }
]
