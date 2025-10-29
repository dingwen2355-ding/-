import coordtransform from './coordtransform'
import ZMap from './znvmapnew/znvMapShikongyun'
import store from '../../store'

let ZnvMap = {}
let map // 地图对象

let layerMarks = {}
let eventLayerMarks = {}
let Util = new ZMap.Util()
let eventCircle
let mapIconMap = {
  定位: '/files/mapIcon/定位.png',
  固定视频: '/files/mapIcon/固定视频_red_hover.png',
  公司企业: '/files/mapIcon/point_公司企业@2x.png',
  交通设施: '/files/mapIcon/point_交通设施@2x.png',
  旅游景点: '/files/mapIcon/point_旅游景点@2x.png',
  生活服务: '/files/mapIcon/point_生活服务@2x.png',
  休闲娱乐: '/files/mapIcon/point_休闲娱乐@2x.png',
  医疗保健: '/files/mapIcon/point_医疗保健@2x.png',
  单兵: '/files/mapIcon/单兵_blue_hover.png',
  救援队伍: '/files/mapIcon/救援队伍_red_hover.png',
  避难场所: '/files/mapIcon/避难场所_red_hover.png',
  应急物资: '/files/mapIcon/应急物资_blue_hover.png',
  专家: '/files/mapIcon/专家_red_hover.png',
  易涝点: '/files/mapIcon/point_易涝点@2x.png',
  河道: '/files/mapIcon/point_河道@2x.png',
  湖泊: '/files/mapIcon/point_湖泊@2x.png',
  背景: '/files/mapIcon/矩形1.png'
}
let circleFeatures = {}
ZnvMap.start = function (mapId, opts) {
  layerMarks = {}
  return new ZMap.Map(mapId, opts).then((res) => {
    map = res
    for (let k in mapIconMap) {
      map.map.loadImage(mapIconMap[k], function (error, image) {
        if (error) throw error
        // 添加自定义图标
        map.map.addImage(k, image)
      })
    }
    Util.createEventIcon(map.map)
    map.cluster = new ZMap.Cluster(map.map, {})
    map._addEditControl()
    map._onEditEnd((feature, type) => {
      let circleContainPoints = store.getters.circleContainPoints
      let circleIds = circleContainPoints.map(t => t.opts.id)
      if (type == 1) {
        let showMarkers = circleFeatures[feature.id]
        ZnvMap.hideMarkers(showMarkers)
        delete circleFeatures[feature.id]
      }
      if (type == 3) {
        let showMarkers = []
        // ZnvMap.hideAllMarkers()
        for (let key in layerMarks) {
          // layerMarks[key]._removeFromMap(map.map)
          if (Util.isPointInPolygon(layerMarks[key], feature)) {
            if (!circleIds.includes(layerMarks[key].opts.id)) {
              showMarkers.push(layerMarks[key])
            }
          }
        }
        circleFeatures[feature.id] = showMarkers
        ZnvMap.showMarkers(showMarkers)
        // store.dispatch('map/setEventCircleContainPoints', showMarkers)
      }
      if (type == 2) {
        let hideMarkers = circleFeatures[feature.id]
        ZnvMap.hideMarkers(hideMarkers)
        let showMarkers = []
        // ZnvMap.hideAllMarkers()
        for (let key in layerMarks) {
          // layerMarks[key]._removeFromMap(map.map)
          if (Util.isPointInPolygon(layerMarks[key], feature)) {
            if (!circleIds.includes(layerMarks[key].opts.id)) {
              showMarkers.push(layerMarks[key])
            }
          }
        }
        circleFeatures[feature.id] = showMarkers
        ZnvMap.showMarkers(showMarkers)
        // store.dispatch('map/setEventCircleContainPoints', showMarkers)
      }
    })
    map._on('mousemove', function (e) {
      var features = map.map.queryRenderedFeatures(e.point, { layers: ['unclustered-points'] })
      var feature = features.length > 0 ? features[0] : null
      // 如果开启显示详细信息功能
      // 将features数组详情信息存入properties.children中
      var source = map.map.getSource('data-point')
      features = features.map(function (f) {
        if (feature.properties.cluster_id && feature.properties.point_count) {
          /*  获取下一层级的children
                 * @param cluster_id属性值
                 * @param callback 回调函数 (`(error, features) => {}`)
                */
          // source.getClusterChildren(feature.properties.cluster_id, function (err,res) {
          //     /* res 返回下一层级的children信息 */
          // });

          /*  获取当前聚合点的所有children信息
                 * @param cluster_id属性值
                 * @param limit 返回数量限制，默认为10，
                 * @param offset 分页显示起始值，默认为0
                 * @param callback 回调函数 (`(error, features) => {}`)
                 */
          source.getClusterLeaves(feature.properties.cluster_id, feature.properties.point_count, 0, function (err, res) {
            f.properties.children = res
          })
        }
        return f
      })
      var dataInfo = features.map(function (f) {
        return {
          opts: {
            extData: JSON.parse(f.properties.extData),
            icon: f.properties.icon,
            id: f.properties.id,
            offset: JSON.parse(f.properties.offset),
            position: JSON.parse(f.properties.position),
            size: f.properties.size,
            title: f.properties.title
          }
        }
      })
      document.getElementById('map-container').title = dataInfo[0] ? dataInfo[0].opts.title : ''
    })
    map._on('click', function (e) {
      var features = map.map.queryRenderedFeatures(e.point, { layers: ['unclustered-points'] })
      if (features.length === 0) {
        return
      }
      if (features.length > 1) {
        console.log('features:', features)
        store.dispatch('map/setMutiPoints', features)
        return
      }
      var feature = features.length > 0 ? features[0] : null
      // 如果开启显示详细信息功能
      // 将features数组详情信息存入properties.children中
      var source = map.map.getSource('data-point')
      features = features.map(function (f) {
        if (feature.properties.cluster_id && feature.properties.point_count) {
          /*  获取下一层级的children
                 * @param cluster_id属性值
                 * @param callback 回调函数 (`(error, features) => {}`)
                */
          // source.getClusterChildren(feature.properties.cluster_id, function (err,res) {
          //     /* res 返回下一层级的children信息 */
          // });

          /*  获取当前聚合点的所有children信息
                 * @param cluster_id属性值
                 * @param limit 返回数量限制，默认为10，
                 * @param offset 分页显示起始值，默认为0
                 * @param callback 回调函数 (`(error, features) => {}`)
                 */
          source.getClusterLeaves(feature.properties.cluster_id, feature.properties.point_count, 0, function (err, res) {
            f.properties.children = res
          })
        }
        return f
      })
      var dataInfo = features.map(function (f) {
        return {
          opts: {
            extData: JSON.parse(f.properties.extData),
            icon: f.properties.icon,
            id: f.properties.id,
            offset: JSON.parse(f.properties.offset),
            position: JSON.parse(f.properties.position),
            size: f.properties.size,
            title: f.properties.title
          }
        }
      })

      ZnvMap.clickEvent(dataInfo[0])
    })
  })
}

ZnvMap.draw = function (mode) {
  map._onBtnCtrlActive(mode)
}

// 加载事件点位
ZnvMap.loadEventPointLayer = function (points, opts) {
  let markers = []
  points.forEach(e => {
    let lnglat = ZnvMap.zbTransform(e.position)
    e.position.lng = lnglat[0]
    e.position.lat = lnglat[1]
    let marker = new ZMap.MarkerDynamic(map.map, e)
    eventLayerMarks[e.id] = marker
    markers.push(marker)
  })
  // ZnvMap.showMarkers(markers)
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
  // ZnvMap.gotoPoint([marker.opts.position.lng, marker.opts.position.lat])
  ZnvMap.showPointDetail(marker.opts)
}
ZnvMap.showPointDetail = function (marker) {
  store.dispatch('map/setWindowInfoDetail', {
    data: marker.extData || marker.info,
    random: new Date().getTime()
  })
}

ZnvMap.showMarkers = function (markers) {
  let ids = map.clusterData.features.map(t => t.properties.id)
  markers.forEach((e) => {
    if (!ids.includes(e.opts.id)) {
      map.clusterData.features.push({
        geometry: {
          coordinates: [e.opts.position.lng, e.opts.position.lat],
          type: 'Point'
        },
        type: 'Feature',
        properties: e.opts
      })
    }
  })
  map.map.getSource('data-point').setData(map.clusterData)
}

ZnvMap.markPoint = function (ids) {
  let _markers = []
  ids.forEach((v, i) => {
    _markers.push(layerMarks[v])
  })
  ZnvMap.showMarkers(_markers)
  if (_markers.length > 0) {
    ZnvMap.gotoPoint([_markers[0].opts.position.lng, _markers[0].opts.position.lat], 17)
  }
}

ZnvMap.hideMarkers = function (markers) {
  let newClusterFeatures = []
  let markersNeedHide = markers.map(t => (t.id || t.opts.id))
  map.clusterData.features.forEach(t => {
    if (!markersNeedHide.includes(t.properties.id)) {
      newClusterFeatures.push(t)
    }
  })
  map.clusterData = {
    type: 'FeatureCollection',
    features: newClusterFeatures
  }
  map.map.getSource('data-point').setData(map.clusterData)
}

ZnvMap.hideAllMarkers = function () {
  map.clusterData = {
    type: 'FeatureCollection',
    features: []
  }
  map.map.getSource('data-point').setData(map.clusterData)
}

ZnvMap.hideAllMarkersExceptEvent = function (eventMarkerId) {
  let newClusterFeatures = []
  map.clusterData.features.forEach(t => {
    if (eventMarkerId === t.properties.id) {
      newClusterFeatures.push(t)
    }
  })
  map.clusterData = {
    type: 'FeatureCollection',
    features: newClusterFeatures
  }
  map.map.getSource('data-point').setData(map.clusterData)
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

ZnvMap.getMarkersById = function (id) {
  return layerMarks[id]
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

ZnvMap.enableOrDisableDraw = function (isEdit) {
  map._enableOrDisableDraw(isEdit)
}

ZnvMap.showEventCircleContainMarker = function () {
  let markers = []
  for (let key in layerMarks) {
    markers.push(layerMarks[key])
  }
  ZnvMap.hideAllMarkers()
  let showMarkers = eventCircle._contain(markers)
  store.dispatch('map/setCircleContainPoints', showMarkers)
  let filterIconList = store.getters.filterIconList
  ZnvMap.showCircleContainPointByKey([
    {
      key: 'type',
      value: filterIconList
    }
  ])
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
        if (extData.type === e || extData.type === 'event-marker') {
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

ZnvMap.drawBackArea = function (opts) {
  let polygon = new ZMap.Polygon(map.map, opts)
}

export default ZnvMap
