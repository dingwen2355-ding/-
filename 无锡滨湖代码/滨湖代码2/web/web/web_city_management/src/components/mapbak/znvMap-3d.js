import coordtransform from './coordtransform'
import ZMap from './znvmap-3d/znvMapSuperMap'
import store from '../../store'

let ZnvMap = {}
let map // 地图对象
let markerClusterLayer
let eventLayerMarks = {} // 事件点位信息
let layerMarks = {}
let eventCircle
let draw
let baimoLayers = null
let jingmoLayers = null
let weixingLayers = null

ZnvMap.start = function (mapId, opts) {
  layerMarks = {}
  return new ZMap.Map(mapId, opts).then(res => {
    markerClusterLayer = new ZMap.MarkerClusterer([], { pixelRange: 50, minClusterSize: 5 })
    map = res
    map._on('LEFT_CLICK', (data) => {
      if (data.length) {
      } else {
        ZnvMap.clickEvent(layerMarks[data._id])
      }
    })
    // map._openSCP('http://10.14.216.12:8290/iserver/services/3D-test1/rest/realspace', (res) => {
    //   for (var layer of res) {
    //     changeLayerShow(layer, false)
    //     if (['dzbm1', 'dzbm2', 'dzbm3', 'dzbm4', 'dzbm5'].includes(layer._name)) {
    //       baimoLayers.push(layer)
    //     } else if (layer._imageryProvider) {
    //       weixingLayers.push(layer)
    //     } else {
    //       jingmoLayers.push(layer)
    //     }
    //   }
    // })
  })
}

ZnvMap.gotoPoint = function (lnglat) {
  map._flyTo({
    center: [...lnglat, 10000]
  })
}

// function changeLayerShow(layer, show) {
//   if (layer._isS3MB) {
//     layer.visible = show
//   } else {
//     layer.show = show
//   }
// }

// 显示隐藏白膜
// 达州
// ZnvMap.showBaimoLayers = function (isShow) {
//   baimoLayers.forEach(e => {
//     changeLayerShow(e, isShow)
//   })
// }
// 无锡
// ZnvMap.showBaimoLayers = function (isShow) {
//   if (isShow) {
//     baimoLayers = map._addCesium3DTileset(sysConfig.whiteUrl)
//   } else {
//     map._removeCesium3DTileset(baimoLayers)
//   }
// }
ZnvMap.showBaimoLayers = function (isShow) {
  if (isShow) {
    baimoLayers = map._addCesium3DTileset(sysConfig.whiteUrl)
    baimoLayers.style = new Cesium.Cesium3DTileStyle({
      color: {
        conditions: [
          ['${Elevation} >= 200', "color('#D33038')"], // red
          ['${Elevation} >= 150', "color('#2747E0')"], // blue
          ['${Elevation} >= 100', "color('#D33B7D')"], // pink
          ['${Elevation} >= 60', "color('#FF9742')"], // orange
          ['${Elevation} >= 30', 'rgba(252, 230, 200, 1)'],
          ['${Elevation} >= 20', 'rgba(248, 176, 87, 1)'],
          ['${Elevation} >= 10', 'rgba(198, 106, 11, 1)'],
          ['true', 'rgba(127, 59, 8, 1)']
        ]
      }
    })
  } else {
    map._removeCesium3DTileset(baimoLayers)
  }
}

// 显示隐藏精模
// 无锡
ZnvMap.showJingmoLayers = function (isShow) {
  if (jingmoLayers) {
    if (isShow) {
      jingmoLayers.visible = true
    } else {
      jingmoLayers.visible = false
    }
  } else {
    map._openSCP('http://218.94.111.14:20003/iserver/services/3D-wxjm_test/rest/realspace', (res) => {
      jingmoLayers = res[0]
    })
  }
}

// 显示隐藏卫星
// 达州
// ZnvMap.showWeixingLayers = function (isShow) {
//   weixingLayers.forEach(e => {
//     changeLayerShow(e, isShow)
//   })
// }
// 无锡
ZnvMap.showWeixingLayers = function (isShow) {
  if (isShow) {
    weixingLayers = map._addWeixing()
  } else {
    map._removeWeixing(weixingLayers)
  }
}

// 加载事件动效
ZnvMap.loadEventCircle = function (opts) {
  opts.center = ZnvMap.zbTransform(opts.center)
  eventCircle = new ZMap.Circle(opts)
  eventCircle._addToMap()
  ZnvMap.showEventCircleContainMarker()
}

ZnvMap.changeEventCircleRadiu = function (r,nameList) {
  eventCircle._changeRadiu(r)
  ZnvMap.showEventCircleContainMarkerByFilter(nameList)
}

ZnvMap.listEventCircleContainMarker = function(){
  let markers = []
  for (let key in layerMarks) {
    if(key.indexOf('应急救援队伍') > -1 || key.indexOf('应急专家') > -1 || key.indexOf('避难场所') > -1 || key.indexOf('应急物资') > -1 ){
      markers.push(layerMarks[key])
    }
  }
  return eventCircle._contain(markers)
}

ZnvMap.showEventCircleContainMarker = function () {
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  ZnvMap.hideAllMarkers()
  store.dispatch('map/setCircleContainPoints', eventCircle._contain(markers))
  ZnvMap.showMarkers(eventCircle._contain(markers))
}

ZnvMap.showEventCircleContainMarkerByFilter = function (filterList) {
  let markers = []
  for (let key in layerMarks) {
    let item = layerMarks[key]
    if(filterList !=null&&filterList.length > 0){
      if(filterList.includes(item.opts.extData.type)){
        markers.push(item)
      }
    }else{
      markers.push(item)
    }
  }
  ZnvMap.hideAllMarkers()
  store.dispatch('map/setCircleContainPoints', eventCircle._contain(markers))
  ZnvMap.showMarkers(eventCircle._contain(markers))
}

// 加载事件点位
ZnvMap.loadEventPointLayer = function(points, opts) {
  let markers = []
  points.forEach(e => {
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    e.layerName = 'eventLayer'
    let marker = new ZMap.Marker(e)
    eventLayerMarks[e.id] = marker
    markers.push(marker)
    // ZnvMap.showMarkers([marker])
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
      e.position.lng = 107.652523440354
      e.position.lat = 31.3852896114874
      e.position.height = 5000
      // console.log('坐标有误', e)
      // return
    }
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    let marker = new ZMap.Marker(e)
    layerMarks[e.id] = marker
    if (!defParams.isHidden) {
      markers.push(marker)
    }
  })
  ZnvMap.showMarkers(markers)
}

ZnvMap.showMarkers = function (markers) {
  markerClusterLayer._addMarkers(markers)
//   markers.forEach(e => {
//     e._addToMap()
//   })
}

ZnvMap.hideMarkers = function (markers) {
  markerClusterLayer._removeMarkers(markers)
  // markers.forEach(e => {
  //   e._removeFromMap()
  // })
}

ZnvMap.hideAllMarkers = function () {
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  ZnvMap.hideMarkers(markers)
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
ZnvMap.removeLayerById = function(id) {
  let markers = []
  if (id && id.length > 0) {
    id[0].value.forEach(item => {
      for (let key in layerMarks) {
        if (layerMarks[key].opts.extData.type === item) {
          markers.push(layerMarks[key])
        }
      }
    })
  }
  markerClusterLayer._removeMarkers(markers)
}
// ZnvMap.showMarkersByType = function (type) {
//   let markers = []
//   for (let key in layerMarks) {
//     if (layerMarks[key].opts.extData.type === type) {
//       markers.push(layerMarks[key])
//     }
//   }
//   ZnvMap.showMarkers(markers)
// }

ZnvMap.hideMarkersByType = function (type) {
  let markers = []
  for (let key in layerMarks) {
    if (layerMarks[key].opts.extData.type === type) {
      markers.push(layerMarks[key])
    }
  }
  ZnvMap.hideMarkers(markers)
}

ZnvMap.clickEvent = function (marker) {
  ZnvMap.showPointDetail(marker && marker.opts.extData)
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

ZnvMap.showPointDetail = function (info) {
  store.dispatch('map/setWindowInfoDetail', {
    data: info,
    random: new Date().getTime()
  })
}

ZnvMap.drawCircle = function () {
  if (!draw) {
    draw = new ZMap.Draw2D()
  }
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  draw._circleSelect(markers, (res) => {
    ZnvMap.hideAllMarkers()
    store.dispatch('map/setCircleContainPoints', eventCircle._contain(res))
    ZnvMap.showMarkers(res)
  })
}

ZnvMap.drawRectangle = function () {
  if (!draw) {
    draw = new ZMap.Draw2D()
  }
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  draw._rectangleSelect(markers, (res) => {
    ZnvMap.hideAllMarkers()
    ZnvMap.showMarkers(res)
  })
}

ZnvMap.drawPlane = function () {
  if (!draw) {
    draw = new ZMap.Draw2D()
  }
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  draw._planeSelect(markers, (res) => {
    ZnvMap.hideAllMarkers()
    store.dispatch('map/setCircleContainPoints', eventCircle._contain(res))
    ZnvMap.showMarkers(res)
  })
}

ZnvMap.drawClear = function () {
  if (draw) {
    draw.clear()
  }
}

ZnvMap.zbTransform = function (param, csysType = 4) {
  // 坐标转换
  let point
  if (param.length) {
    param.lng = param[0]
    param.lat = param[1]
  } else {
    param.lng = param.gpsX || param.gpsx || param.lng
    param.lat = param.gpsY || param.gpsy || param.lat
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
