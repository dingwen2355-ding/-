import coordtransform from './coordtransform'
import ZMapAPI from './znvmap/lib/index'
import store from '../../vuex'

let sysConfig = configs
let commandZnvMap = {}
let ZMap // 地图服务
let map // 地图对象
let polygonMap = {}
let layerMarks = {} // 点位信息
let heatMap = {}
let tool // 鼠标工具
let geometry = [] // 绘制图形
let restLayer = {} // 城管套件图层
// let cluster
// 打开对应的信息框
let infowin = null
let animationLayer = {} // 动画点位信息
// let oldClickMarker = null
sysConfig.map.mapIp = process.env.VUE_APP_BUILD_ENV === 'production' ? 'http://59.206.203.34/' : 'http://www.sdmap.gov.cn/'
commandZnvMap.start = function (mapId, opts) {
  layerMarks = {}
  let mapPluginName = sysConfig.map.mapType
  // openlayerMap:openlayer地图
  // superMap: 超图
  // gaodeMap: 高德地图
  // baiduMap: 百度地图
  // arcGisMap: arcgis地图
  console.log(process.env)
  return new ZMapAPI({
    mapName: mapPluginName,
    mapServeUrl: process.env.NODE_ENV === 'development' ? '' : sysConfig.map.mapUrl,
    apiUrl: process.env.NODE_ENV === 'development' ? '' : sysConfig.map.apiUrl
  }).then(e => {
    ZMap = e
    map = new ZMap.Map(mapId, opts)
    map.setDefaultCursor("pointer")
    // restLayer[map.baseLayer.name] = map.baseLayer
    // commandZnvMap.addLayer({
    //   name: '影像地图',
    //   id: 'yixiangditu',
    //   url: sysConfig.map.mapIp + 'tileservice/SDRasterPubMap',
    //   layerIndex: 1,
    //   isHidden: true
    // })
    // commandZnvMap.addLayer({
    //   name: '影像地图标注',
    //   id: 'yixiangditubiaozhu',
    //   url: sysConfig.map.mapIp + 'tileservice/SDRasterPubMapDJ',
    //   layerIndex: 2,
    //   isHidden: true
    // })
    tool = new ZMap.MapLib.MouseTool(map)
    // cluster = new ZMap.MapLib.MarkerClusterer(map, [])
  })
}

// 关闭MouseTool插件 清空画布
// commandZnvMap.clearDrawOverlays = function () {
//   tool && tool._clearDrawOverlays()
// }
// 使用MouseTool插件画点
// commandZnvMap.drawPoint = function (callback) {
//   let znvMap = this
//   tool._drawPoint(data => {
//     let gps = commandZnvMap.zbTransform2({
//       lng: data.getPosition().lng,
//       lat: data.getPosition().lat
//     })
//     callback.call(znvMap, {
//       lng: gps[1],
//       lat: gps[0]
//     })
//   })
// }
commandZnvMap.zbTransform2 = function (param) { // 高德转84
  let point
  param.lng = param.gpsX || param.gpsx || param.lng
  param.lat = param.gpsY || param.gpsy || param.lat
  point = coordtransform.gcj02towgs84(parseFloat(param.lng), parseFloat(param.lat))
  return point
}

commandZnvMap.changeMap = function (id) {
  if (id === 'yixiangditu') {
    restLayer['ditu'].setVisibility(false)
    restLayer['yixiangditu'].setVisibility(true)
    restLayer['yixiangditubiaozhu'].setVisibility(true)
  } else {
    restLayer['ditu'].setVisibility(true)
    restLayer['yixiangditu'].setVisibility(false)
    restLayer['yixiangditubiaozhu'].setVisibility(false)
  }
}

commandZnvMap.addLayer = function (opts) {
  let layer = new window.SuperMap.Layer.WMTS({
    name: opts.name,
    url: opts.url,
    layer: 'cva',
    style: 'default',
    matrixSet: 'c',
    format: 'tiles',
    opacity: 1,
    requestEncoding: 'KVP'
  })
  map._addOverlay(layer)
  map._setLayerIndex(layer, opts.layerIndex)
  restLayer[opts.id] = layer
  if (opts.isHidden) {
    layer.setVisibility(false)
  }
}

commandZnvMap.removeLayerById = function (id) {
  map._removeLayer(restLayer[id])
}

commandZnvMap.gotoMapPoint = function (params) {
  let defParams = {
    lng: 118.0851364,
    lat: 36.1680603,
    zoom: 12,
    csysType: 1
  }
  defParams = Object.assign({}, defParams, params)
  let point
  point = commandZnvMap.zbTransform(defParams)
  if (point[0] === 0 || point[1] === 0) {
    return
  }
  map._centerAndZoom(point, defParams.zoom)
}

// 加载事件点位
commandZnvMap.loadEventPointLayer = function (points, opts) {
  points.forEach(e => {
    let lnglat = commandZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    e.layerName = 'eventLayer'
    let marker = new ZMap.Marker(map, e, true)
    // layerMarks[e.id] = marker
    marker._add(map)
  })
}

// 加载点位
commandZnvMap.loadPointLayer = function (points, opts) {
  let markers = []
  points.forEach(e => {
    if (!(e.position.lng && e.position.lat)) {
      console.log('坐标有误', e)
      return
    }
    let lnglat = commandZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    e.layerName = 'pointLayer'
    let marker = new ZMap.Marker(map, e, true)
    marker._add(map)
    marker._hide()
    marker._addEventListener('click', function (v) {
      commandZnvMap.clickEvent(v)
    })
    if (opts.showLabel && e.label) {
      marker._setLabel({ content: e.label, offset: { x: 0, y: -20 } })
    }
    layerMarks[e.id] = marker
    if (!opts.isHidden || e.visible) {
      markers.push(marker)
    }
  })
  // cluster._addMarkers(markers)
  commandZnvMap.showMarkers(markers)
}
// 关闭信息框
commandZnvMap.closeInfoWin = function () {
  if (infowin) {
    try {
      infowin.hide()
      infowin.destroy()
    } catch (e) {
    }
  }
}
// 打开信息框
commandZnvMap.openInfoWin = function (marker) {
  commandZnvMap.closeInfoWin()
  let data = marker.extData || marker.info
  let popup = new window.SuperMap.Popup(
    'popwin-' + data.class,
    new window.SuperMap.LonLat(marker.position.lng, marker.position.lat),
    new window.SuperMap.Size(300, 160),
    `
    <div class='box'>
      <div class='title'>${data.industryName}</div>
      <div class='list'>
        <div class='item'>
          <span class='key'>企业地址：</span>
          <span class='value'>${data.address}</span>
        </div>
        <div class='item'>
          <span class='key'>所属行业：</span>
          <span class='value'>${data.industryType}</span>
        </div>
        <div class='item'>
          <span class='key'>风险等级：</span>
          <span class='value'>${data.subType}</span>
        </div>
      </div>
    </div>
    `,
    true,
    null
  )
  // infowin = popup
  // map.addPopup(popup)
  // let infoWindow = new ZMap.InfoWindow({
  //   content: info.join('') //使用默认信息窗体框样式，显示信息内容
  // })
  infowin = popup
}
// 点击-撒点
commandZnvMap.clickEvent = function (marker, animation) {
  console.log(marker, '233333333');
  store.dispatch('map/setMapClickCommand', {
    data: marker.De,
    random: new Date().getTime()
  })
}
commandZnvMap.showPointDetail = function (marker) {
  store.dispatch('map/setWindowInfoDetail', {
    data: marker.extData || marker.info,
    random: new Date().getTime()
  })
}
// 查看二级水库并定位
commandZnvMap.setMapCenterByReservoirName = function (reservoirName) {
  for (let key in layerMarks) {
    let info = layerMarks[key].extData || layerMarks[key].info
    if (info.reservoir === reservoirName && info.type.includes('水库')) {
      commandZnvMap.gotoMapPoint({
        lng: info.gpsx,
        lat: info.gpsy,
        zoom: 14
      })
      return info
    }
  }
}
// 隐藏所有点位图标
commandZnvMap.hideAllLayer = function () {
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    marker._hide()
  }
  // cluster._clearMarkers()
}
// 显示所有点位图标
commandZnvMap.showAllLayer = function () {
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    marker._show()
    // cluster._addMarker(marker)
  }
}
// 显示所有点位图标
commandZnvMap.showAnimationLayer = function () {
  for (let key in animationLayer) {
    let marker = animationLayer[key]
    marker._show()
    // cluster._addMarker(marker)
  }
}
// 根据id显示点位
commandZnvMap.showLayerById = function (id) {
  let marker = layerMarks[id]
  if (marker) {
    marker._show()
    // cluster._addMarker(marker)
  }
  return marker
}
// 根据id获取点位
commandZnvMap.getLayerById = function (id) {
  let marker = layerMarks[id]
  return marker
}
commandZnvMap.showMarkers = function (markers) {
  markers.forEach(e => {
    e._show()
  })
}
// 根据过滤条件展示点位
commandZnvMap.showPointByKey = function (filterList) {
  commandZnvMap.hideAllLayer()
  let showMarkers = []
  let markerList = []
  let markerIdList = []
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    markerList.push(marker)
  }
  filterList.forEach(e => {
    markerList.forEach(m => {
      let extData = m.extData || m.info
      let flag = false
      if (Array.isArray(extData[e.key])) {
        extData[e.key].some(e1 => {
          if (e.value.includes(e1)) {
            flag = true
            return true
          }
        })
      } else {
        flag = e.value.includes(extData[e.key])
      }
      if (flag && !markerIdList.includes(m.id)) {
        showMarkers.push(m)
        markerIdList.push(m.id)
      }
    })
  })
  // cluster._addMarkers(showMarkers)
  commandZnvMap.showMarkers(showMarkers)
  return showMarkers
}
// 根据过滤条件获取点位
commandZnvMap.getPointByKey = function (filterList) {
  commandZnvMap.hideAllLayer()
  let showMarkers = []
  let markerList = []
  let markerIdList = []
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    markerList.push(marker)
  }
  filterList.forEach(e => {
    markerList.forEach(m => {
      let extData = m.extData || m.info
      let flag = false
      if (Array.isArray(extData[e.key])) {
        extData[e.key].some(e1 => {
          if (e.value.includes(e1)) {
            flag = true
            return true
          }
        })
      } else {
        flag = e.value.includes(extData[e.key])
      }
      if (flag && !markerIdList.includes(m.id)) {
        showMarkers.push(m)
        markerIdList.push(m.id)
      }
    })
  })
  return showMarkers
}
// 根据title查找点位
commandZnvMap.getLayerByTitle = function (title) {
  commandZnvMap.hideAllLayer()
  let markers = []
  for (let key in layerMarks) {
    if (layerMarks[key].id.includes(title)) {
      markers.push(layerMarks[key])
    }
  }
  if (markers.length > 0) {
    markers.forEach(item => {
        let position = {
          lng: item.De.gpsx,
          lat: item.De.gpsy
        }
        item.position = position
    })
  }
  commandZnvMap.loadPointLayer(markers, { isHidden: false })
  return markers
}
// 刻画区域轮廓
commandZnvMap.regionalContour = function (id, path, opts, callback) {
  let lnglatArr = []

  for (let i = 0; i < path.length; i++) {
    let lnglat = path[i]
    if (lnglat.length !== 2) {
      continue
    }
    let lnglan = commandZnvMap.zbTransform({
      lng: lnglat[0],
      lat: lnglat[1]
    }, 3)
    lnglatArr.push(lnglan)
  }

  opts.path = lnglatArr
  let polygon = new ZMap.Polygon(map, opts)
  polygonMap[id] = polygon
  if (opts.showLabel) {
    let centerlnglan = commandZnvMap.zbTransform({
      lng: opts.center[0],
      lat: opts.center[1]
    }, 3)
    let text = new ZMap.Text(map, {
      position: {
        lng: centerlnglan[0],
        lat: centerlnglan[1]
      },
      text: opts.label
    })
    polygonMap[id + '-label'] = text
    text._hide()
  }
  return polygon
}

commandZnvMap.showRegionalContour = function () {
  for (let key in polygonMap) {
    if (!key.includes('-label')) {
      polygonMap[key]._show()
    }
  }
}

commandZnvMap.hideRegionalContour = function () {
  for (let key in polygonMap) {
    polygonMap[key]._hide()
  }
}

commandZnvMap.showRegionalContourById = function (id) {
  polygonMap[id]._show()
  polygonMap[id + '-label']._show()
}

commandZnvMap.zoomIn = function () {
  let zoom = map._getZoom()
  if (zoom <= 20) {
    map._zoom(zoom + 1)
  }
}

commandZnvMap.zoomOut = function () {
  let zoom = map._getZoom()
  if (zoom >= 1) {
    map._zoom(zoom - 1)
  }
}

commandZnvMap.selectRect = function (pns, callback) {
  let pnsCopy = pns.filter(t => !(t.gpsx == '' || t.gpsy == '')).map(t => {
    let a = coordtransform.gcj02towgs84(Number(t.gpsx), Number(t.gpsy))
    t.lng = a[0]
    t.lat = a[1]
    return t
  })
  tool._selectRectangleCompareLngLat(pnsCopy, function (rePns) {
    callback(rePns)
  })
}
commandZnvMap.selectPolygon = function (pns, callback) {
  let pnsCopy = pns.filter(t => !(t.gpsx == '' || t.gpsy == '')).map(t => {
    let a = coordtransform.gcj02towgs84(Number(t.gpsx), Number(t.gpsy))
    t.lng = a[0]
    t.lat = a[1]
    return t
  })
  tool._selectPolygonCompareLngLat(pnsCopy, function (rePns) {
    callback(rePns)
  })
}

commandZnvMap.MassMarks = function (data) {
  return ZMap.MassMarks(map, data, null)
}

commandZnvMap.drawPolygon = function () {
  tool._drawPolygon(function (geo) {
    if (geo) {
      geometry.push(geo)
    }
  })
}

commandZnvMap.clearDraw = function () {
  if (geometry) {
    tool._clearDrawOverlays(geometry)
  }
  geometry = []
}

commandZnvMap.addHeatMap = function (points) {
  // let pointList = points.map(e => {
  //   let lnglan = commandZnvMap.zbTransform(e)
  //   e.lng = lnglan[0]
  //   e.lat = lnglan[1]
  //   return e
  // })
  let opts = {
    dataSource: points, // points {"lng":116.421969,"lat":39.913527,"count":3}数组
    map: map,
    radius: 20,
    max: 100
    // gradient: {
    //   .2:'rgb(0, 255, 255)',
    //   .5:'rgb(0, 110, 255)',
    //   .8:'rgb(100, 0, 255)'
    // }
  }
  console.log(opts)
  heatMap = new ZMap.Heatmap(opts, map)
}

commandZnvMap.showHeatMap = function () {
  heatMap.toggleShow(map)
}

commandZnvMap.hideHeatMap = function () {
  heatMap.toggleHide()
}

commandZnvMap.zbTransform = function (param, csysType) {
  // 坐标转换
  let point
  param.lng = param.gpsX || param.gpsx || param.lng
  param.lat = param.gpsY || param.gpsy || param.lat
  if (!csysType) {
    csysType = sysConfig.map.mapCsysType
  }
  if (csysType === 1) {
    point = [parseFloat(param.lng), parseFloat(param.lat)]
  } else if (csysType === 4) {
    point = coordtransform.wgs84togcj02(parseFloat(param.lng), parseFloat(param.lat))
  } else if (csysType === 3) {
    point = coordtransform.gcj02towgs84(parseFloat(param.lng), parseFloat(param.lat))
  }
  return point
}

// 加载单个点位
commandZnvMap.initMarker = function (point, callback) {
  let gps = commandZnvMap.zbTransform(point)
  // console.log(gps)
  // if (oneMarkerList[point.id]) {
  //   oneMarkerList[point.id]._show()
  // } else {
  let opt = {
    icon: './static/centerPoint.png',
    position: {
      lng: gps[0],
      lat: gps[1]
    },
    size: { width: 30, height: 30 },
    offset: { x: -15, y: -30 }
  }
  let oneMarker = new ZMap.Marker(map, opt)
  oneMarker._add()
  layerMarks[point.id] = oneMarker
  // }
}
commandZnvMap.clearMap = function () {
  map._clear()
}

commandZnvMap.destroy = function (param, csysType) {
  map.destroy()
}
export default commandZnvMap
