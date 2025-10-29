import coordtransform from './coordtransform'
import ZMap from './znvmapnew/znvMapShikongyun'
import store from '../../store'

let ZnvMap = {}
let map // 地图对象

let layerMarks = {}
let eventLayerMarks = {}
let Util = new ZMap.Util()
let eventCircle

ZnvMap.start = function (mapId, opts) {
  layerMarks = {}
  return new ZMap.Map(mapId, opts).then((res) => {
    map = res
    map._addEditControl()
    map._onEditEnd((feature) => {
      let showMarkers = []
      for (let key in layerMarks) {
        layerMarks[key]._removeFromMap(map.map)
        if (Util.isPointInPolygon(layerMarks[key], feature)){
          showMarkers.push(layerMarks[key])
        }
      }
      ZnvMap.showMarkers(showMarkers)
      store.dispatch('map/setCircleContainPoints', showMarkers)
    })
    map._on('click', (e) => {})
  })
}

ZnvMap.draw = function (mode) {
  map._onBtnCtrlActive(mode)
}

// 加载事件点位
ZnvMap.loadEventPointLayer = function(points, opts) {
  let markers = []
  points.forEach(e => {
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    let marker = new ZMap.Marker(map, e)
    eventLayerMarks[e.id] = marker
    markers.push(marker)
  })
  ZnvMap.showMarkers(markers)
}

// 加载点位
ZnvMap.loadPointLayer = function (points, opts) {
  let defParams = {
    isHidden: false
  }
  defParams = Object.assign({}, defParams, opts)
  let markers = []
  points.forEach((e) => {
    if (!(e.position.lng && e.position.lat)) {
      // 坐标有问题定位到青岛市中心
      e.position.lng = 120.422167
      e.position.lat = 36.117018
      // console.log('坐标有误', e)
      // return
    }
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    let marker = new ZMap.Marker(map, e)
    marker._on('click', (v) => {
      ZnvMap.clickEvent(marker)
    })
    layerMarks[e.id] = marker
    if (!defParams.isHidden) {
      markers.push(marker)
    }
  })
  ZnvMap.showMarkers(markers)
}

ZnvMap.clickEvent = function (marker) {
  ZnvMap.showMarkers([marker])
  ZnvMap.gotoPoint([marker.opts.position.lng, marker.opts.position.lat])
  ZnvMap.showPointDetail(marker.opts)
}
ZnvMap.showPointDetail = function (marker) {
  store.dispatch('map/setWindowInfoDetail', {
    data: marker.extData || marker.info,
    random: new Date().getTime()
  })
}

ZnvMap.showMarkers = function (markers) {
  markers.forEach((e) => {
    e._addToMap(map.map)
  })
}

ZnvMap.hideMarkers = function (markers) {
  markers.forEach((e) => {
    e._removeFromMap(map.map)
  })
}

ZnvMap.hideAllMarkers = function (markers) {
  for (let key in layerMarks) {
    layerMarks[key]._removeFromMap(map.map)
  }
}

ZnvMap.showMarkersByType = function (type) {
  let markers = []
  for (let key in layerMarks) {
    if (layerMarks[key].opts.extData.type === type) {
      markers.push(layerMarks[key])
    }
  }
  ZnvMap.showMarkers(markers)
}

ZnvMap.hideMarkersByType = function (type) {
  let markers = []
  for (let key in layerMarks) {
    if (layerMarks[key].opts.extData.type === type) {
      markers.push(layerMarks[key])
    }
  }
  ZnvMap.hideMarkers(markers)
}

ZnvMap.getLayerByTitles = function (titles) {
  let markers = []
  for (let key in layerMarks) {
    let flag = false
    flag = titles.some(e => {
      if (!(layerMarks[key].opts.title && layerMarks[key].opts.title.includes(e))) {
        return true
      }
    })
    if (!flag) {
      markers.push(layerMarks[key])
    }
  }
  return markers
}

ZnvMap.loadEventCircle = function (opts) {
  opts.center = ZnvMap.zbTransform(opts.center)
  if (eventCircle) {
    eventCircle._remove(map.map)
    eventCircle = null
  }
  eventCircle = new ZMap.Circle(opts, map.map)
  ZnvMap.showEventCircleContainMarker()
}

ZnvMap.showEventCircleContainMarker = function () {
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  ZnvMap.hideAllMarkers()
  let showMarkers = eventCircle._contain(markers)
  store.dispatch('map/setCircleContainPoints', showMarkers)
  ZnvMap.showMarkers(showMarkers)
}

// 根据过滤条件显示圈内点位
ZnvMap.showCircleContainPointByKey = function (filterList, type = 'or') {
  ZnvMap.hideAllMarkers()
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
    filterList[0].value.forEach((e) => {
      markerList.forEach((m) => {
        let extData = m.opts.extData || m.info
        if (extData.type === e) {
          showMarkers.push(m)
        }
      })
    })
  }
  ZnvMap.showMarkers(showMarkers)
}

ZnvMap.gotoPoint = function (lnglat, zoom = 15) {
  map._flyTo({
    center: lnglat,
    zoom: zoom
  })
}

ZnvMap.zbTransform = function (lnglat, csysType = 1) {
  // 坐标转换
  let point
  let param = {}
  if (Array.isArray(lnglat)) {
    param.lng = lnglat[0]
    param.lat = lnglat[1]
  } else {
    param.lng = lnglat.gpsX || lnglat.gpsx || lnglat.lng
    param.lat = lnglat.gpsY || lnglat.gpsy || lnglat.lat
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

export default ZnvMap
