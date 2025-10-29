import request from '@/utils/request'
import qs from 'qs'
export function getPrecinctList (data) {
  // let url = '/cdapis/precinct/queryPrecinctList?precinctId=' + data.precinctId
  // if (data.precinctKind) url += '&precinctKind=' + data.precinctKind
  let url = '/mock/getPrecinctList.json'
  return request({
    url: url,
    method: 'get'
  })
}

// 视频巡检
export function queryCameraListGroupByKind (data) {
  // let url = '/mock/pointList.json'
  let url = '/cdapis/device/queryCameraListGroupByKind'
  return request({
    url: url,
    method: 'get'
  })
}
// 查询固定视频点位
export function getDeviceFixedVideo (data) {
  // let url = '/mock/pointList.json'
  let url = '/cdapis/dahua/getDeviceList'
  return request({
    url: url,
    method: 'get'
  })
}
// 查询城市部件
export function queryCSBJList (data) {
  // let url = '/mock/pointList.json'
  let url = '/cdapis/cityFirmware/query'
  return request({
    url: url,
    method: 'get'
  })
}
// 查询单兵
export function queryIndividualList (data) {
  // let url = '/mock/pointList.json'
  let url = '/cdapis/queryDevice/queryIndividualList?precinctId=510100'
  return request({
    url: url,
    method: 'get'
  })
}
// 查询车辆撒点信息
export function getCar (data) {
  // let url = '/mock/pointList.json'
  let url = '/cdapis/syncCar/query-condition?type=' + data
  return request({
    url: url,
    method: 'get'
  })
}

// 查询避难场所、应急专家、学校、信息
export function getEmergencyCoordinates (data) {
  // let url = '/mock/pointList.json'
  let url = '/binhuapis/siteManage/selectBasicShelter'
  if (data && data != '') {
    url = url + `?isAll=${data}`
  }
  return request({
    url: url,
    method: 'get'
  })
}

// 查询 应急专家
export function getEmergencyExpert(data) {
  // let url = '/mock/pointList.json'
  let url = '/binhuapis/basicExpert/getBasicExpertList?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
  if (data && data != '') {
    url = url + `?isAll=${data}`
  }
  return request({
    url: url,
    method: 'get'
  })
}

// 应急物资仓库撒点
export function getEmergencySupplies (data) {
  // let url = '/mock/pointList.json'
  let url = '/binhuapis/resourceStuff/query?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
  if (data && data != '') {
    url = url + `?isAll=${data}`
  }
  return request({
    url: url,
    method: 'get'
  })
}

// 救援队伍
export function queryRescueTeamList (data) {
  let url = '/binhuapis/teamInfo/queryTeamInfoList?helpType=999'
  return request({
    url: url,
    method: 'get'
  })
}
// 救援物资
export function queryReliefMaterialsList (data) {
  let url = '/cdapis/stockInfo/queryStockInfoList?helpType=999'
  return request({
    url: url,
    method: 'get'
  })
}
// 视频资源
export function queryVideoResourcesList (data) {
  let url = '/cdapis/device/queryDeviceInfoList'
  return request({
    url: url,
    method: 'get'
  })
}

// 应急力量
export function getEmergencyType (data) {
  let url = '/cdapis/teamInfo/emergencyPowerDictList'
  return request({
    url: url,
    method: 'get'
  })
}

// 应急力量
export function getEmergencyPower (data) {
  let url = '/cdapis/teamInfo/emergencyPower'
  return request({
    url: url,
    method: 'get'
  })
}

// 企业
export function getCompany (data) {
  let url = '/auth/focusBasic/queryCompanyList'
  return request({
    url: url,
    method: 'get'
  })
}

// 人员密集点 旅游景点
export function getFocusBasicInfo (data) {
  let url = '/auth/focusBasic/queryList'
  return request({
    url: url,
    method: 'get'
  })
}

// 水库撒点
export function queryReservoirList (data) {
  if (data) {
    data = qs.stringify(data, {
      encode: false
    })
  } else {
    data = ''
  }
  return request({
    url: '/cdapis/reservoir/queryReservoirList',
    method: 'get'
  })
}

// 救援车队
export function getCarTeam (data) {
  let url = '/cdapis/teamInfo/carTeam'
  return request({
    url: url,
    method: 'get'
  })
}

// 抢修队伍
export function getEmergencyFloodStuff (data) {
  let url = '/cdapis/teamInfo/emergencyFloodStuff'
  return request({
    url: url,
    method: 'get'
  })
}

// 安置场所
export function getShelterInfo (data) {
  let url = '/cdapis/teamInfo/shelterInfo'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询煤矿企业
export function queryCoalMine (data) {
  let url = '/cdapis/center/queryCoalMine'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询重点企业
export function queryEnterprise (data) {
  let url = '/cdapis/center/queryEnterprise'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询重大危险源
export function queryHazard (data) {
  let url = '/cdapis/center/queryHazard'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询医疗机构
export function queryMedicalInstitution (data) {
  let url = '/cdapis/center/queryMedicalInstitution'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询水雨情监测站
export function queryObservationStation (data) {
  let url = '/cdapis/center/queryObservationStation'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询易灾点
export function queryProneDisaster (data) {
  let url = '/cdapis/center/queryProneDisaster'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询应急救援队伍
export function queryRescue (data) {
  let url = '/binhuapis/teamInfo/queryRescueTeamInfo?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
  if (data && data != '') {
    url = url + `?isAll=${data}`
  }
  return request({
    url: url,
    method: 'get'
  })
}

// 查询水库
export function queryReservoir (data) {
  let url = '/cdapis/center/queryReservoir'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询运输力量
export function queryTransport (data) {
  let url = '/cdapis/center/queryTransport'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询内涝点
export function queryWaterlogging (data) {
  let url = '/cdapis/center/queryWaterlogging'
  return request({
    url: url,
    method: 'get'
  })
}

// 查询气象站
export function queryWeatherStation (data) {
  let url = '/cdapis/center/queryWeatherStation'
  return request({
    url: url,
    method: 'get'
  })
}

// 行政区域轮廓
export function queryDistrict (data) {
  return request({
    url: '/mock/district.json',
    method: 'get'
  })
}

// 河道
export function queryRiver (data) {
  return request({
    url: '/mock/queryRiver.json',
    method: 'get'
  })
}

// 查询各区域站点位置【本地数据】
export function getAreaSite (data) {
  return request({
    url: '/mock/areaSite.json',
    method: 'get'
  })
}

// 河流监测数据
export function queryWaterDataList (data) {
  if (data) {
    data = qs.stringify(data, {
      encode: false
    })
  } else {
    data = ''
  }
  return request({
    url: '/cdapis/device/queryRiverMonitorInfo?' + data,
    method: 'get'
  })
}

// 固定视频
export function getdeviceId(data) {
  let url = '/cdapis/fusion/getVideoListByEventId'
  return request({
    url: url,
    method: 'get'
  })
}

export function getFlvUrl(data) {
  let type = data.type || 'HLS'
  // let type = data.type || 'FLV_HTTP'
  // let url = '/cdapis/dahua/getVideoUrl?videoType=FLV_HTTP'
  let url = '/cdapis/dahua/getVideoUrl?videoType=' + type
  if (data.deviceId) {
    url += ('&deviceId=' + data.deviceId)
  }
  let typeObj = {
    FLV_HTTP: 'flv',
    HLS: 'hls',
    RTSP: 'rtsp'
  }
  return new Promise((resolve, reject) => {
    request({
      url: url,
      method: 'get'
    }).then(res => {
      resolve({
        src: res.data.data.url,
        type: typeObj[type]
      })
    }).catch(() => {
      resolve({
        src: '',
        type: typeObj[type]
      })
    })
  })
}
// 城市之眼
export function getCityEyeUrl(data) {
  // let type = data.type || 'http-flv'
  let type = data.type || 'hls'
  let delay = 0
  if (type === 'hls') {
    delay = 2000
  } else {
    delay = 0
  }
  // let url = '/cdapis/dahua/getVideoUrl?videoType=FLV_HTTP'
  let url = '/cdapis/dahua/getCityEyesVideoUrl?protocol=' + type
  if (data.deviceId) {
    url += ('&deviceId=' + data.deviceId)
  }
  let typeObj = {
    'http-flv': 'flv',
    hls: 'hls',
    RTSP: 'rtsp'
  }
  return new Promise((resolve, reject) => {
    request({
      url: url,
      method: 'get'
    }).then(res => {
      setTimeout(() => {
        // if (res && res.data && res.data.data && res.data.data.data) {
        resolve({
          src: res.data.data.data ? res.data.data.data.send_uri : '',
          tgt_session_id: res.data.data.data ? res.data.data.data.tgt_session_id : '',
          type: res.data.data.data ? typeObj[type] : ''
        })
        // } else {
        //   reject('接口报错')
        // }
      }, delay)
    }).catch(() => {
      resolve({
        src: '',
        // type: typeObj[type]
        type: ''
      })
    })
  })
}

// 事件处置
export function queryFlow(data) {
  // let url = '/cdapis/eventDeal/queryFlow'
  // let url = '/cdapis/eventDeal/getEventDeal'
  let url = '/cdapis/eventJoint/dealMessage'
  return request({
    url: url,
    method: 'post',
    data: data
  })
}

export function getHlsUrl(data) {
  let url = '/cdapis/hik/getHlsUrl?'
  if (data.deviceId) {
    url += ('&indexCode=' + data.deviceId)
  }
  return new Promise((resolve, reject) => {
    request({
      url: url,
      method: 'get'
    }).then(res => {
      resolve({
        src: res.data.data,
        type: 'hls'
      })
    }).catch(() => {
      resolve({
        src: '',
        type: 'hls'
      })
    })
  })
}
