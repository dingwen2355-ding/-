import $ from 'jquery'
import gaodeMap from './znvMapGaode'

function cityGisMap() {
  let Map = CityGis
  let cityGisMap = {}
  let loaded = 0
  cityGisMap.Constants = {
    ZOOM_CHANGED: 'zoom',
    ZOOM_END: 'zoom-end',
    DRAG_END: 'mouse-drag-end',
    MOUSEMOVE: 'mouse-move',
    // CLICK: 'onClick', // graphic点击事件
    CLICK: 'click', // map点击事件click, graphic点击事件onClick
    DBLCLICK: 'dbl-click', // 双击事件
    RIGHTCLICK: 'contextmenu',
    LOADED: 'load', // 地图加载事件
    MOUSEOVER: 'onMouseOver', // graphic
    MOUSEOUT: 'onMouseOut' // graphic
  }
  cityGisMap.loaded = function (loadedFn) {
    if (loaded === 0) {
      setTimeout(() => {
        cityGisMap.loaded(loadedFn)
      }, 200)
      return
    }
    loadedFn()
  }
  cityGisMap.Map = function (mapDiv, opts, mapType, mapConfig) {
    let map = new Map.Bridge(mapDiv, {
      url: opts.mapServeUrl,
      // center: (opts.center && opts.center.lng) ? Point(opts.center.lng, opts.center.lat) : Point(118.6589, 32.1474),
      // zoom: opts.zoom,
      // logo: false,
      // slider: false,
      // minZoom: Number(opts.minZoom),
      // maxZoom: Number(opts.maxZoom)
      onReady: function () {
        loaded = 1
      }
    })
    map = $.extend(map, this)
    map.protoSelf = map
    return map
  }

  /**
   map接口
   */
  cityGisMap.Map.prototype = {
    _clear: function () {
      this.protoSelf.clear()
    },
    _centerAndZoom: function (point) {
      this.protoSelf.Invoke({
        'ActionName': 'goToPosition',
        'Parameters': {
          // positon: {
          //   x: 112.121766,
          //   y: 32.009595,
          //   z: 10
          // },
          positon: {
            x: Number(point.lng),
            y: Number(point.lat)
          },
          hasImg: false
        }
      })
    }
  }

  window.cityGisMap = cityGisMap
  return cityGisMap
}

export default cityGisMap
