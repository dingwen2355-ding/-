import coordtransform from '../coordtransform'
import ZMapAPI from './znvmap/lib/index'
import store from '../../store'

let ZnvMap = {}
let ZMap // 地图服务
let map // 地图对象
let polygonMap = {}
let eventLayerMarks = {} // 事件点位信息
let layerMarks = {} // 点位信息
let circleMarks = {}
let heatMap = {}
let tool // 鼠标工具
let geometry = [] // 绘制图形
let restLayer = {} // 城管套件图层
let utfGridlayer = [] // 城管套件数据图层
let control
let cluster
// 打开对应的信息框
let infowin = null

ZnvMap.start = function(mapId, opts) {
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
    mapServeUrl: process.env.VUE_APP_BUILD_ENV === 'development' ? '' : sysConfig.map.mapUrl,
    apiUrl: process.env.VUE_APP_BUILD_ENV === 'development' ? '' : sysConfig.map.apiUrl
  }).then(e => {
    ZMap = e
    map = new ZMap.Map(mapId, opts, () => {
      restLayer[map.baseLayer.name] = map.baseLayer
    })
    tool = new ZMap.MapLib.MouseTool(map)
    cluster = new ZMap.MapLib.MarkerClusterer(map, [])
  })
}

ZnvMap.changeMap = function(id) {
  if (id === 'yixiangditu') {
    restLayer.ditu.setVisibility(false)
    restLayer.yixiangditu.setVisibility(true)
  } else {
    restLayer.ditu.setVisibility(true)
    restLayer.yixiangditu.setVisibility(false)
  }
}

ZnvMap.addLayer = function(opts) {
  let url = opts.url
  if (process.env.VUE_APP_BUILD_ENV === 'development') {
    url = url.replace('http://10.214.111.221:8090', 'http://36.153.138.103:28090')
  } else {
    url = url.replace('http://10.214.111.221:8090', location.protocol + '//' + location.hostname + ':8096/superMap')
  }
  opts.url = url
  let layer = new window.SuperMap.Layer.TiledDynamicRESTLayer(
    opts.name,
    url,
    {
      transparent: true, // 透明
      cacheEnabled: false
    },
    { maxResolution: 'auto' }
  )
  layer.events.on({
    layerInitialized: function(evt) {
      map._addOverlay(layer)
      map._setLayerIndex(layer, 1)
      restLayer[opts.id] = layer
      if (opts.isHidden) {
        layer.setVisibility(false)
      }
      if (opts.type !== 'TiledDynamicRESTLayer') {
        addUTFGrid(layer, opts)
      }
    }
  })
}
function formatLayerName(url) {
  let urlsplit1 = url.split('/')
  let urlsplit2 = urlsplit1[urlsplit1.length - 1].split('@')
  return urlsplit2[0]
}

function addUTFGrid(layer, opts) {
  let utfGrid = new window.SuperMap.Layer.UTFGrid(opts.name, opts.url,
    {
      layerName: formatLayerName(opts.url) + '@12.18.128.249_dlxx', /* 根据图层url最后的名称替换 ZDQYSP */
      utfTileSize: 256,
      pixcell: 16,
      isUseCache: true
    },
    {
      utfgridResolution: 16
    }
  )
  utfGrid.maxExtent = layer.maxExtent
  map._addOverlay(utfGrid)
  map._setLayerIndex(utfGrid, 1)
  utfGridlayer[opts.name] = utfGrid
  if (!control) {
    control = new window.SuperMap.Control.UTFGrid({
      layers: [utfGrid],
      callback: (data) => {
        console.log(data)
        store.dispatch('map/setUTFGridInfoDetail', data)
      },
      handlerMode: 'click'
    })
    map.addControl(control)
  } else {
    control.layers.push(utfGrid)
  }
}

ZnvMap.removeLayerById = function(id) {
  for (let key in layerMarks) {
    if (key.includes(id)) {
      map._removeLayer(layerMarks[key])
    }
  }
}

ZnvMap.gotoMapPoint = function(params) {
  let defParams = {
    lng: 119.0092888,
    lat: 31.692719,
    zoom: 15,
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
ZnvMap.loadEventPointLayer = function(points, opts) {
  points.forEach(e => {
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    e.layerName = 'eventLayer'
    let marker = new ZMap.Marker(map, e, true)
    eventLayerMarks[e.id] = marker
    marker._add(map)
  })
}

// 加载点位
ZnvMap.loadPointLayer = function(points, opts) {
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
    let marker = new ZMap.Marker(map, e)
    marker._addEventListener('click', function(e) {
      ZnvMap.clickEvent(e)
    })
    layerMarks[e.id] = marker
    if (!opts.isHidden) {
      markers.push(marker)
    }
  })
  cluster._addMarkers(markers)
}
// 关闭信息框
function closeInfoWin() {
  if (infowin) {
    try {
      infowin.hide()
      infowin.destroy()
    } catch (e) {}
  }
}
// 点击-撒点
ZnvMap.clickEvent = function(marker) {
  if (marker.children && marker.children.length > 0) {
    return
  }
  closeInfoWin()
  // let size = new window.SuperMap.Size(0, 48)
  // let offset = new window.SuperMap.Pixel(0, 0)
  // let icon = new window.SuperMap.Icon('', size, offset)
  // let popup = new window.SuperMap.Popup.FramedCloud(
  //   'popwin',
  //   new window.SuperMap.LonLat(marker.geometry.x, marker.geometry.y),
  //   null,
  //   '当前点位',
  //   icon,
  //   true
  // )
  // infowin = popup
  // map.addPopup(popup)
  // map._center([marker.geometry.x, marker.geometry.y])
  ZnvMap.showPointDetail(marker)
}
ZnvMap.showPointDetail = function(marker) {
  let sameGpsLayerMarks = []
  let layers = cluster.clustererMarkerMap
  for (let e in layers) {
    if (layers[e].geometry.x.toFixed(3) === marker.geometry.x.toFixed(3) && layers[e].geometry.y.toFixed(3) === marker.geometry.y.toFixed(3)) {
      sameGpsLayerMarks.push(layers[e])
    }
  }
  store.dispatch('map/setWindowInfoDetail', {
    data: marker.info,
    random: new Date().getTime()
  })
  // if (sameGpsLayerMarks.length > 1) {
  //   store.dispatch('map/setSameGpsAlarmList', {
  //     data: sameGpsLayerMarks,
  //     random: new Date().getTime()
  //   })
  // } else {
  //   store.dispatch('map/setWindowInfoDetail', {
  //     data: marker.info,
  //     random: new Date().getTime()
  //   })
  // }
}
// 隐藏所有点位图标
ZnvMap.hideAllLayer = function() {
  // for (let key in layerMarks) {
  //   let marker = layerMarks[key]
  //   marker._hide()
  // }
  cluster._clearMarkers()
}
// 显示所有点位图标
ZnvMap.showAllLayer = function() {
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    // marker._show()
    cluster._addMarker(marker)
  }
}
// 根据id显示点位
ZnvMap.showLayerById = function(id) {
  let marker = layerMarks[id]
  if (marker) {
    cluster._addMarker(marker)
  }
  return marker
}
// 根据过滤条件展示点位
ZnvMap.showPointByKey = function(filterList) {
  ZnvMap.hideAllLayer()
  let showMarkers = []
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    showMarkers.push(marker)
  }
  filterList.forEach(e => {
    showMarkers = showMarkers.filter(m => {
      let extData = m.extData || m.info
      return e.value.includes(extData[e.key])
    })
  })
  cluster._addMarkers(showMarkers)
  store.dispatch('map/setCurrentShowPoint', showMarkers)
}
// 根据title查找点位
ZnvMap.getLayerByTitle = function(title) {
  let markers = []
  for (let key in layerMarks) {
    if (layerMarks[key].title && layerMarks[key].title.includes(title)) {
      markers.push(layerMarks[key])
    }
  }
  return markers
}
// 根据id查找点位
ZnvMap.getLayerById = function(id) {
  return layerMarks[id]
}
// 刻画区域轮廓
ZnvMap.regionalContour = function(id, pathStr, opts, callback) {
  let path = pathStr.split(';')
  let lnglatArr = []

  for (let i = 0; i < path.length; i++) {
    let item = path[i]
    let lnglat = item.split(',')
    if (lnglat.length !== 2) {
      continue
    }
    let lnglan = ZnvMap.zbTransform({
      lng: lnglat[0],
      lat: lnglat[1]
    })
    lnglatArr.push(lnglan)
  }

  opts.path = lnglatArr
  let polygon = new ZMap.Polygon(map, opts)
  polygonMap[id] = polygon
  return polygon
}

ZnvMap.addCircle = function(type, opts) {
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

// ZnvMap.addCircle = function (type, opts) {
//   let defParams = {
//     center: { lng: 118.6589, lat: 32.1474 },
//     radius: 1000, // 单位米
//     strokeColor: '#FF0000',
//     fillColor: '#FFFF00',
//     strokeOpacity: 0.4
//   }
//   defParams = Object.assign({}, defParams, opts)
//   let lnglan = ZnvMap.zbTransform({
//     lng: defParams.center.lng,
//     lat: defParams.center.lat
//   })
//   defParams.center.lng = lnglan[0]
//   defParams.center.lat = lnglan[1]
//   circleMarks[type] = new ZMap.Circle(map, defParams)
//   // map._addOverlay(circleMarks[type], false) // 第二个参数仅支持openlayer
//   store.dispatch('map/setCurrentCircle', {
//     geoType: 'circle',
//     center: {
//       gpsX: defParams.center.lng,
//       gpsY: defParams.center.lat
//     },
//     radius: defParams.radius,
//     outlinePoints: ''
//   })
// }

ZnvMap.removeAllCircle = function() {
  for (let key in circleMarks) {
    let circle = circleMarks[key]
    circle._remove()
  }
  circleMarks = {}
}

ZnvMap.circleShow = function(type) {
  circleMarks[type]._show()
}

ZnvMap.circleHide = function(type) {
  circleMarks[type]._hide()
}

// ZnvMap.showCircleContainPoints = function(type) {
//   ZnvMap.hideAllLayer()
//   let markers = []
//   for (let key in layerMarks) {
//     let marker = layerMarks[key]
//     if (marker.type === 'event' || circleMarks[type]._contains(marker)) {
//       markers.push(marker)
//     }
//   }
//   cluster._addMarkers(markers)
// }

ZnvMap.showCircleContainPoints = function (type) {
  ZnvMap.hideAllLayer()
  let markers = []
  console.log(Object.keys(layerMarks).length)
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    let info = marker.extData || marker.info
    if (info.type === 'event' || circleMarks[type]._contains(marker)) {
      markers.push(marker)
    }
  }
  setCircleContainPoints(
    [circleMarks[type].geometry.getCentroid().x, circleMarks[type].geometry.getCentroid().y],
    markers
  )
  cluster._addMarkers(markers)
}

// 根据过滤条件显示圈内点位
ZnvMap.showCircleContainPointByKey = function (filterList, type = 'or') {
  ZnvMap.hideAllLayer()
  let showMarkers = []
  let markerList = store.getters.circleContainPoints
  let markerIdList = []
  if (type === 'and') {
    markerList.forEach((m) => {
      let extData = m.extData || m.info
      let flag = false
      filterList.some((e) => {
        if (Array.isArray(extData[e.key])) {
          extData[e.key].some((e1) => {
            if (e.value.includes(e1)) {
              flag = true
              return true
            }
          })
        } else {
          flag = e.value.includes(extData[e.key])
        }
        if (!flag) {
          return true
        }
      })
      if (flag && !markerIdList.includes(m.id)) {
        showMarkers.push(m)
        markerIdList.push(m.id)
      }
    })
  } else {
    filterList.forEach((e) => {
      markerList.forEach((m) => {
        let extData = m.extData || m.info
        let flag = false
        if (Array.isArray(extData[e.key])) {
          extData[e.key].some((e1) => {
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
  }
  cluster._addMarkers(showMarkers)
  return showMarkers
}

function setCircleContainPoints(center, markers) {
  // if (center.length > 0) {
  // markers.forEach((m) => {
  //   m._addEventListener('click', function(e) {
  //     ZnvMap.clickEvent(e)
  //   })
  // })
  // } else {
  //   markers.forEach((m) => {
  //     let extData = m.extData || m.info
  //     extData.currentDistance = '-'
  //   })
  // }
  store.dispatch('map/setCircleContainPoints', markers)
}

ZnvMap.zoomIn = function() {
  let zoom = map._getZoom()
  if (zoom <= 20) {
    map._zoom(zoom + 1)
  }
}

ZnvMap.zoomOut = function() {
  let zoom = map._getZoom()
  if (zoom >= 1) {
    map._zoom(zoom - 1)
  }
}
ZnvMap.drawLine = function() {
  tool._drawLine(function(geo) {
    if (geo) {
      geometry.push(geo)
    }
  })
}

// ZnvMap.drawCircle = function() {
//   tool._drawCircle(function(geo) {
//     if (geo) {
//       geometry.push(geo)
//     }
//   })
// }
ZnvMap.drawCircle = function () {
  ZnvMap.hideAllLayer()
  let markers = []
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    markers.push(marker)
  }
  markers.forEach((m) => {
    m._addEventListener('click', function(e) {
      ZnvMap.clickEvent(e)
    })
  })
  tool._selectCircle(markers, function (showMarkers, geo) {
    setCircleContainPoints([geo.getCentroid().x, geo.getCentroid().y], showMarkers)
    cluster._addMarkers(showMarkers)
  })
}

// ZnvMap.drawPolygon = function() {
//   tool._drawPolygon(function(geo) {
//     if (geo) {
//       geometry.push(geo)
//     }
//   })
// }

// 根据title查找点位
ZnvMap.getLayerByTitles = function (titles) {
  let markers = []
  for (let key in layerMarks) {
    let flag = false
    flag = titles.some(e => {
      if (!(layerMarks[key].title && layerMarks[key].title.includes(e))) {
        return true
      }
    })
    if (!flag) {
      markers.push(layerMarks[key])
    }
  }
  return markers
}

ZnvMap.drawPolygon = function () {
  ZnvMap.hideAllLayer()
  let markers = []
  for (let key in layerMarks) {
    let marker = layerMarks[key]
    markers.push(marker)
  }
  tool._selectPolygon(markers, function (showMarkers, geo) {
    // setCircleContainPoints([geo.getCentroid().x, geo.getCentroid().y], showMarkers)
    cluster._addMarkers(showMarkers)
  })
}

ZnvMap.clearDraw = function() {
  if (geometry) {
    tool._clearDrawOverlays(geometry)
  }
  geometry = []
}

ZnvMap.addHeatMap = function(points) {
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

ZnvMap.showHeatMap = function() {
  heatMap.toggleShow(map)
}

ZnvMap.hideHeatMap = function() {
  heatMap.toggleHide()
}

ZnvMap.zbTransform = function(param) {
  // 坐标转换
  let point
  param.lng = param.gpsX || param.gpsx || param.lng
  param.lat = param.gpsY || param.gpsy || param.lat
  let csysType = sysConfig.map.mapCsysType
  if (csysType === 1) {
    point = [parseFloat(param.lng), parseFloat(param.lat)]
  } else if (csysType === 4) {
    point = coordtransform.wgs84togcj02(parseFloat(param.lng), parseFloat(param.lat))
  }
  return point
}
export default ZnvMap
