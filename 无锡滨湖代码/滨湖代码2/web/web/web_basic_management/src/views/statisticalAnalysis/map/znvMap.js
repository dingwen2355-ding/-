import coordtransform from './coordtransform'
import './style/znvmap.scss'
import store from '../../../store'

let ZnvMap = {
  closeFilter: true
}
let ZMap // 地图服务
let map // 地图对象
let markerCluster // 聚合类markerClusterlet
let markerCluster1 // 聚合类markerCluster
let layerMarks = {} // 点位信息
let layerMarks1 = {} // 点位信息
let textMarks = [] // 点位信息
let polygonMap = {}
// let currentClickMarker = null // 点位点击效果
let resourcePowerMarker = []
let Geocoder
let labelTimeout
let mapPluginName = ''
ZnvMap.start = function (mapId, opts) {
  markerCluster = null
  layerMarks = {}
  let pluginPromise
  mapPluginName = configs.map.mapType1
  if (mapPluginName === 'gaodeMap') {
    pluginPromise = import('./znvMapGaode.js')
  }
  // else if (mapPluginName === 'openlayerMap') {
  //   pluginPromise = import('./znv-openlayer-map.js')
  // } else if (mapPluginName === 'greenMap') {
  //   pluginPromise = import('./znv-green-gis-map.js')
  // }
  opts = opts || {}
  if (pluginPromise) {
    pluginPromise.then(function (znvMap) {
      ZMap = znvMap.default()
      ZnvMap.ZMap = ZMap
      ZnvMap.loadMap(opts, mapId)
    })
  }
}
ZnvMap.destroy = function () {
  ZnvMap.isMapComplete = 0
  ZnvMap.map._destroy()
}
// 加载地图
ZnvMap.loadMap = function (opts, mapId) {
  let defOpts = {
    maxZoom: 20,
    minZoom: 13,
    center: [106.5966, 29.5869], // [118.6589, 32.1474], // [121.5022888, 31.2029285]  重庆[106.5966,29.5869]
    zoom: 13
  }
  defOpts = Object.assign({}, defOpts, opts)
  ZMap.loaded(function () {
    map = new ZMap.Map(mapId, defOpts)
    ZnvMap.map = map
    ZnvMap.isMapComplete = 1
  })
}

ZnvMap.zbTransform = function (param) {
  // 坐标转换
  let point
  param.lng = param.gpsX || param.gpsx || param.lng
  param.lat = param.gpsY || param.gpsy || param.lat
  let csysType = configs.map.mapCsysType
  if (
    param.gpsType === -1 ||
    (csysType === 4 && (param.csysType === 4 || param.gpsType === 4)) ||
    (csysType === 1 && (param.csysType === 1 || param.gpsType === 1))
  ) {
    // 是高德坐标
    point = [parseFloat(param.lng), parseFloat(param.lat)]
  } else {
    if (csysType === 4) {
      point = coordtransform.wgs84togcj02(parseFloat(param.lng), parseFloat(param.lat))
    } else {
      point = coordtransform.gcj02towgs84(parseFloat(param.lng), parseFloat(param.lat))
    }
  }
  return point
}

ZnvMap.onclick = function (f) {
  ZnvMap.map._addEventListener('click', function (e) {
    f(e)
  })
}

// 历史轨迹
ZnvMap.drawLine1 = function (lineArr, opts) {
  let path = []
  lineArr.forEach((v) => {
    path.push(ZnvMap.zbTransform(v))
  })
  console.log(lineArr)
  let polyline = new ZMap.moveAlong(map, path, opts)
  console.log(polyline)
  // new ZMap.Polyline(map, path, opts)
  return polyline
}
window.ZnvMap = ZnvMap

export default ZnvMap
