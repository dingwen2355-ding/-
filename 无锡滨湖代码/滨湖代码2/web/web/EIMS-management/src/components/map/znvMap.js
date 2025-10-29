import coordtransform from './coordtransform'
import ZMapAPI from './znvmap/lib/index'
import store from '../../store'
import mapIcon from './mapIcon'

let sysConfig = configs
let ZnvMap = {}
let ZMap // 地图服务
let map // 地图对象
let polygonMap = {}
let polylineMap = {}
let layerMarks = {} // 点位信息
let circleMarks = {}
let heatMap = {}
let tool // 鼠标工具
let geometry = [] // 绘制图形
let restLayer = {} // 城管套件图层
// let cluster
// 打开对应的信息框
let infowin = null
let animationLayer = {} // 动画点位信息
let oldClickMarker = null
sysConfig.map.mapIp = process.env.VUE_APP_BUILD_ENV === 'production' ? 'http://59.206.203.34/' : 'http://www.sdmap.gov.cn/'
ZnvMap.start = function (mapId, opts) {
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
    // restLayer[map.baseLayer.name] = map.baseLayer
    // ZnvMap.addLayer({
    //   name: '影像地图',
    //   id: 'yixiangditu',
    //   url: sysConfig.map.mapIp + 'tileservice/SDRasterPubMap',
    //   layerIndex: 1,
    //   isHidden: true
    // })
    // ZnvMap.addLayer({
    //   name: '影像地图标注',
    //   id: 'yixiangditubiaozhu',
    //   url: sysConfig.map.mapIp + 'tileservice/SDRasterPubMapDJ',
    //   layerIndex: 2,
    //   isHidden: true
    // })
    tool = new ZMap.MapLib.MouseTool(map)
    map._addEventListener('click', function (v) {
      store.dispatch('map/setMapClickGps', v.lng + ',' + v.lat)
    })
    // cluster = new ZMap.MapLib.MarkerClusterer(map, [])
  })
}

// 关闭MouseTool插件 清空画布
ZnvMap.clearDrawOverlays = function () {
  tool && tool._clearDrawOverlays()
}
// 使用MouseTool插件画点
ZnvMap.drawPoint = function (callback) {
  let znvMap = this
  tool._drawPoint(data => {
    let gps = ZnvMap.zbTransform2({
      lng: data.getPosition().lng,
      lat: data.getPosition().lat
    })
    callback.call(znvMap, {
      lng: gps[1],
      lat: gps[0]
    })
  })
}
ZnvMap.zbTransform2 = function (param) { // 高德转84
  let point
  param.lng = param.gpsX || param.gpsx || param.lng
  param.lat = param.gpsY || param.gpsy || param.lat
  point = coordtransform.gcj02towgs84(parseFloat(param.lng), parseFloat(param.lat))
  return point
}

ZnvMap.changeMap = function (id) {
  if (id === 'yixiangditu') {
    restLayer.ditu.setVisibility(false)
    restLayer.yixiangditu.setVisibility(true)
    restLayer.yixiangditubiaozhu.setVisibility(true)
  } else {
    restLayer.ditu.setVisibility(true)
    restLayer.yixiangditu.setVisibility(false)
    restLayer.yixiangditubiaozhu.setVisibility(false)
  }
}

ZnvMap.addLayer = function (opts) {
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

ZnvMap.removeLayerById = function (id) {
  map._removeLayer(restLayer[id])
}

ZnvMap.gotoMapPoint = function (params) {
  let defParams = {
    lng: 118.0851364,
    lat: 36.1680603,
    zoom: 12,
    csysType: 1
  }
  defParams = Object.assign({}, defParams, params)
  let point
  point = ZnvMap.zbTransform(defParams)
  if (point[0] === 0 || point[1] === 0) {
    return
  }
  map._centerAndZoom(point, defParams.zoom)
}

// 加载事件点位
ZnvMap.loadEventPointLayer = function (points, opts) {
  points.forEach(e => {
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    e.layerName = 'eventLayer'
    let marker = new ZMap.Marker(map, e, true)
    // layerMarks[e.id] = marker
    marker._add(map)
  })
}

// 加载点位
ZnvMap.loadPointLayer = function (points, opts) {
  let markers = []
  points.forEach(e => {
    if (!(e.position.lng && e.position.lat)) {
      console.log('坐标有误', e)
      return
    }
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    e.layerName = 'pointLayer'
    let marker = new ZMap.Marker(map, e, true)
    if (e.animation) {
      marker.icon.imageDiv.className = e.animation
      animationLayer[e.id] = marker
    }
    marker._add(map)
    marker._hide()
    marker._addEventListener('click', function (v) {
      ZnvMap.clickEvent(v)
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
  ZnvMap.showMarkers(markers)
}
// 关闭信息框
ZnvMap.closeInfoWin = function () {
  if (infowin) {
    try {
      infowin.hide()
      infowin.destroy()
    } catch (e) {
    }
  }
}
// 打开信息框
ZnvMap.openInfoWin = function (marker) {
  ZnvMap.closeInfoWin()
  let data = marker.extData || marker.info
  let popup = new window.SuperMap.Popup(
    'popwin-' + data.class,
    new window.SuperMap.LonLat(marker._getPosition().lng, marker._getPosition().lat),
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
  infowin = popup
  map.addPopup(popup)
}
// 点击-撒点
ZnvMap.clickEvent = function (marker, animation) {
  if (marker.children && marker.children.length > 0) {
    return
  }
  if (oldClickMarker) {
    oldClickMarker.icon.imageDiv.className = ''
  }
  if (animation === 'twinkle-animation' && marker.icon.imageDiv.className !== animation) {
    marker.icon.imageDiv.className = animation
    oldClickMarker = marker
  }
  ZnvMap.openInfoWin(marker)
  map._center([marker._getPosition().lng, marker._getPosition().lat])
  ZnvMap.showPointDetail(marker)
}
ZnvMap.showPointDetail = function (marker) {
  store.dispatch('map/setWindowInfoDetail', {
    data: marker.extData || marker.info,
    random: new Date().getTime()
  })
}
// 查看二级水库并定位
ZnvMap.setMapCenterByReservoirName = function (reservoirName) {
  for (let key in layerMarks) {
    let info = layerMarks[key].extData || layerMarks[key].info
    if (info.reservoir === reservoirName && info.type.includes('水库')) {
      ZnvMap.gotoMapPoint({
        lng: info.gpsx,
        lat: info.gpsy,
        zoom: 14
      })
      return info
    }
  }
}
// 隐藏所有点位图标
ZnvMap.hideAllLayer = function () {
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    marker._hide()
  }
  // cluster._clearMarkers()
}
// 显示所有点位图标
ZnvMap.showAllLayer = function () {
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    marker._show()
    // cluster._addMarker(marker)
  }
}
// 显示所有点位图标
ZnvMap.showAnimationLayer = function () {
  for (let key in animationLayer) {
    let marker = animationLayer[key]
    marker._show()
    // cluster._addMarker(marker)
  }
}
// 根据id显示点位
ZnvMap.showLayerById = function (id) {
  let marker = layerMarks[id]
  if (marker) {
    marker._show()
    // cluster._addMarker(marker)
  }
  return marker
}
// 根据id获取点位
ZnvMap.getLayerById = function (id) {
  let marker = layerMarks[id]
  return marker
}
// 根据不同时间切换雨量图标
ZnvMap.changeWeatherStationIcon = function (hour) {
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    let e = marker.extData
    let iconInfo = mapIcon['雨量无数据']
    if (e.type === '雨量监测') {
      e.subType = '雨量无数据'
      if (e[hour] > 0 && e[hour] < 10) {
        iconInfo = mapIcon['雨量小于10']
        e.subType = '雨量小于10'
      } else if (e[hour] >= 10 && e[hour] < 25) {
        iconInfo = mapIcon['雨量10到25']
        e.subType = '雨量10到25'
      } else if (e[hour] >= 25 && e[hour] < 50) {
        iconInfo = mapIcon['雨量25到50']
        e.subType = '雨量25到50'
      } else if (e[hour] >= 50 && e[hour] < 100) {
        iconInfo = mapIcon['雨量50到100']
        e.subType = '雨量50到100'
      } else if (e[hour] >= 100 && e[hour] < 250) {
        iconInfo = mapIcon['雨量100到250']
        e.subType = '雨量100到250'
      } else if (e[hour] >= 250) {
        iconInfo = mapIcon['雨量大于250']
        e.subType = '雨量大于250'
      }
      marker.icon.setUrl(iconInfo)
    }
  }
}
ZnvMap.showMarkers = function (markers) {
  markers.forEach(e => {
    e._show()
  })
}
// 根据过滤条件展示点位
ZnvMap.showPointByKey = function (filterList) {
  ZnvMap.hideAllLayer()
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
  ZnvMap.showMarkers(showMarkers)
  return showMarkers
}
// 根据过滤条件获取点位
ZnvMap.getPointByKey = function (filterList) {
  ZnvMap.hideAllLayer()
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
ZnvMap.getLayerByTitle = function (title) {
  let markers = []
  for (let key in layerMarks) {
    if (layerMarks[key].title.includes(title)) {
      markers.push(layerMarks[key])
    }
  }
  return markers
}
// 刻画区域轮廓
ZnvMap.regionalContour = function (id, path, opts, callback) {
  let lnglatArr = []

  for (let i = 0; i < path.length; i++) {
    let lnglat = path[i]
    if (lnglat.length !== 2) {
      continue
    }
    let lnglan = ZnvMap.zbTransform({
      lng: lnglat[0],
      lat: lnglat[1]
    }, 3)
    lnglatArr.push(lnglan)
  }

  opts.path = lnglatArr
  let polygon = new ZMap.Polygon(map, opts)
  polygonMap[id] = polygon
  if (opts.showLabel) {
    let centerlnglan = ZnvMap.zbTransform({
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

ZnvMap.showRegionalContour = function () {
  for (let key in polygonMap) {
    if (!key.includes('-label')) {
      polygonMap[key]._show()
    }
  }
}

ZnvMap.hideRegionalContour = function () {
  for (let key in polygonMap) {
    polygonMap[key]._hide()
  }
}

ZnvMap.showRegionalContourById = function (id) {
  polygonMap[id]._show()
  polygonMap[id + '-label']._show()
}

// 刻画河道
ZnvMap.riverLine = function (id, path, opts, callback) {
  let polyline = new ZMap.Polyline(map, path, opts)
  polylineMap[id] = polyline
  if (opts.showLabel) {
    let text = new ZMap.Text(map, {
      position: {
        lng: opts.center[0],
        lat: opts.center[1]
      },
      text: opts.label
    })
    text._addEventListener('click', function (v) {
      ZnvMap.riverClickEvent(v)
    })
    polylineMap[id + '-label'] = text
  }
  return polyline
}
ZnvMap.riverClickEvent = function (v) {
  console.log(v)
}

ZnvMap.showRiverLine = function () {
  for (let key in polylineMap) {
    polylineMap[key]._show()
  }
}

ZnvMap.hideRiverLine = function () {
  for (let key in polylineMap) {
    polylineMap[key]._hide()
  }
}

ZnvMap.addCircle = function (type, opts) {
  let defParams = {
    center: { lng: 118.6589, lat: 32.1474 },
    radius: 1000, // 单位米
    strokeColor: '#FF0000',
    fillColor: '#FFFF00',
    strokeOpacity: 0.4
  }
  defParams = Object.assign({}, defParams, opts)
  let lnglan = ZnvMap.zbTransform({
    lng: defParams.center.lng,
    lat: defParams.center.lat
  })
  defParams.center.lng = lnglan[0]
  defParams.center.lat = lnglan[1]
  circleMarks[type] = new ZMap.Circle(map, defParams)
  map._addOverlay(circleMarks[type], false) // 第二个参数仅支持openlayer
}

ZnvMap.removeAllCircle = function () {
  for (let key in circleMarks) {
    let circle = circleMarks[key]
    circle._remove()
  }
  circleMarks = {}
}

ZnvMap.circleShow = function (type) {
  circleMarks[type]._show()
}

ZnvMap.circleHide = function (type) {
  circleMarks[type]._hide()
}

ZnvMap.showCircleContainPoints = function (type, filterList) {
  ZnvMap.hideAllLayer()
  let markers = []
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    let flag = false
    if (filterList) {
      filterList.forEach(e => {
        let extData = marker.extData || marker.info
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
      })
    } else {
      flag = true
    }
    let info = marker.extData || marker.info
    if (flag && (info.type === 'event' || circleMarks[type]._contains(marker))) {
      markers.push(marker)
    }
  }
  // cluster._addMarkers(markers)
  ZnvMap.showMarkers(markers)
}

ZnvMap.zoomIn = function () {
  let zoom = map._getZoom()
  if (zoom <= 20) {
    map._zoom(zoom + 1)
  }
}

ZnvMap.zoomOut = function () {
  let zoom = map._getZoom()
  if (zoom >= 1) {
    map._zoom(zoom - 1)
  }
}
ZnvMap.drawLine = function () {
  tool._drawLine(function (geo) {
    if (geo) {
      geometry.push(geo)
    }
  })
}

ZnvMap.drawCircle = function () {
  tool._drawCircle(function (geo) {
    if (geo) {
      geometry.push(geo)
    }
  })
}

ZnvMap.selectCircle = function (pns, callback) {
  let pnsCopy = pns.filter(t => !(t.gpsx == '' || t.gpsy == '')).map(t => {
    let a = coordtransform.gcj02towgs84(Number(t.gpsx), Number(t.gpsy))
    t.lng = a[0]
    t.lat = a[1]
    return t
  })
  tool._selectCircleCompareLngLat(pnsCopy, function (rePns) {
    callback(rePns)
  })
}

ZnvMap.selectRect = function (pns, callback) {
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
ZnvMap.selectPolygon = function (pns, callback) {
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

ZnvMap.MassMarks = function (data) {
  return ZMap.MassMarks(map, data, null)
}

ZnvMap.drawPolygon = function () {
  tool._drawPolygon(function (geo) {
    if (geo) {
      geometry.push(geo)
    }
  })
}

ZnvMap.clearDraw = function () {
  if (geometry) {
    tool._clearDrawOverlays(geometry)
  }
  geometry = []
}

ZnvMap.addHeatMap = function (points) {
  // let pointList = points.map(e => {
  //   let lnglan = ZnvMap.zbTransform(e)
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

ZnvMap.showHeatMap = function () {
  heatMap.toggleShow(map)
}

ZnvMap.hideHeatMap = function () {
  heatMap.toggleHide()
}

ZnvMap.zbTransform = function (param, csysType) {
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
ZnvMap.initMarker = function (point, callback) {
  let gps = ZnvMap.zbTransform(point)
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
ZnvMap.clearMap = function () {
  map._clear()
}

ZnvMap.destroy = function (param, csysType) {
  map.destroy()
}
export default ZnvMap
