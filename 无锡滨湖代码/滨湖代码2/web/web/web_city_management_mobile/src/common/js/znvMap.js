import coordtransform from './coordtransform'
import ZMapAPI from '../../libs/znvmap/lib/index'
import store from '../../store'

let ZnvMap = {}
let ZMap // 地图服务
let map // 地图对象
let eventPointLayer
ZnvMap.start = function (mapId, opts) {
  let mapPluginName = 'superMap'
  // openlayerMap:openlayer地图
  // superMap: 超图
  // gaodeMap: 高德地图
  // baiduMap: 百度地图
  // arcGisMap: arcgis地图
  return new ZMapAPI({
    mapName: mapPluginName
  }).then((e) => {
    ZMap = e
    map = new ZMap.Map(mapId, opts)

    // 地图点击操作
    // 因为移动端点击事件会和touch事件冲突，故改为通过touch自行判断是否是点击
    const mapClickHandler = function(v) {
      store.dispatch('map/setMapClickGps', [v.lng, v.lat])
    }

    // 通过touch，自行实现拖曳
    // 记录手指触摸的数量
    let touchCount = 0

    // 记录上一次触摸点的位置
    let lastTouchPos = null

    // 记录触摸点的位置和时间戳
    let touchStartPos = null
    let touchStartTime = null

    // touch start
    map._addEventListener('touchstart', function (v, event) {
      if (event.touches.length === 1) {
        // 如果只有一个触摸点，记录当前触摸点的位置
        lastTouchPos = {
          x: event.touches[0].clientX,
          y: event.touches[0].clientY
        }
        // 记录初始触摸位置和时间，用于后续判断点击操作
        touchStartPos = {
          x: event.touches[0].clientX,
          y: event.touches[0].clientY
        }
        touchStartTime = Date.now()
      } else if (event.touches.length === 2) {
        // 缩放交给地图的PinchZoomControl来实现
      }
    })

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
          e.position.lng = 120.30242008
          e.position.lat = 31.59091392
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

    // touch move
    map._addEventListener('touchmove', function (v, event) {
      if (event.touches.length === 1 && touchCount !== 2) {
        // 如果只有一个触摸点，并且之前触摸点数量不是两个，说明是拖曳地图
        let touchPos = {
          x: event.touches[0].clientX,
          y: event.touches[0].clientY
        }
        let deltaX = touchPos.x - lastTouchPos.x
        let deltaY = touchPos.y - lastTouchPos.y
        // 将deltaX和deltaY应用到地图上，实现拖曳地图的效果
        const { lng, lat } = map._getCenter()
        // 根据缩放等级和一定的基准值来计算移动距离与地图实际距离之间的比值
        const scale = 0.0014 * Math.pow(0.5, map._getZoom() - 10)
        // x方向是反向的
        map._center([lng + -deltaX * scale, lat + deltaY * scale])

        lastTouchPos = touchPos
      } else if (event.touches.length === 2) {
        // 缩放交给地图的PinchZoomControl来实现
      }
    })

    // touch end
    map._addEventListener('touchend', function (v, event) {
      if (touchStartPos) {
        // 计算触摸点的移动距离和时间
        let touchEndPos = {
          x: event.changedTouches[0].clientX,
          y: event.changedTouches[0].clientY
        }
        let touchEndTime = Date.now()
        let distanceX = touchEndPos.x - touchStartPos.x
        let distanceY = touchEndPos.y - touchStartPos.y
        let distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY)
        let duration = touchEndTime - touchStartTime

        // 计算触摸点的移动速度
        let speed = distance / duration

        if (speed < 0.1 && duration < 300) {
          // 如果移动速度过慢，则说明是点击操作
          mapClickHandler(v)
        }

        // 清空触摸点位置和时间戳，以便下一次判断
        touchStartPos = null
        touchStartTime = null
      }
      if (event.touches.length === 0) {
        // 如果没有触摸点，清空触摸点数量和上一次触摸点的位置
        touchCount = 0
        lastTouchPos = null
      } else if (event.touches.length === 1 && touchCount === 2) {
        // 如果只有一个触摸点，并且之前触摸点数量是两个，清空触摸点数量和上一次触摸点的位置
        touchCount = 0
        lastTouchPos = null
      }
    })
  })
}

ZnvMap.centerTo = function(center, zoom) {
  map._centerAndZoom(center, zoom || map._getZoom())
}

// 加载事件点位
ZnvMap.loadEventPointLayer = function (points) {
  points.forEach(e => {
    e.layerName = 'eventLayer'
    let marker = new ZMap.Marker(map, e, true)
    eventPointLayer = marker
    marker._add(map)
  })
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
// remove事件点位
ZnvMap.removeEventPointLayer = function () {
  if (eventPointLayer) {
    eventPointLayer._remove()
    eventPointLayer = null
  }
}
ZnvMap.destroy = function () {
  map._destroy()
}
export default ZnvMap
