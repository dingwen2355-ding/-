import ol from '../ol/ol'
import '../ol/ol.css'
import $ from 'jquery'
import Measure from '../ol/measure'

function createMapLayer(mapConfig) {
  // mapConfig.mapServeUrl = mapConfig.mapServeUrl ? mapConfig.mapServeUrl + '/' : null
  // 给8位字符串文件名补0
  function zeroFill(num, len, radix) {
    var str = num.toString(radix || 10)
    while (str.length < len) {
      str = '0' + str
    }
    return str
  }
  function LoadGaodeArcGISTile() {
    // 离线高德加载
    // ol.source.XYZ添加瓦片地图的层
    return new ol.layer.Tile({
      source: new ol.source.XYZ({
        tileUrlFunction: function (coordinate) {
          var x = 'C' + zeroFill(coordinate[1], 8, 16)
          var y = 'R' + zeroFill(-coordinate[2] - 1, 8, 16)
          var z = 'L' + zeroFill(coordinate[0], 2, 10)
          return (mapConfig.mapServeUrl || 'http://10.45.157.179:8008/') + z.toUpperCase() + '/' + y.toUpperCase() + '/' + x.toUpperCase() + '.png'// 这里可以修改地图路径
        },
        projection: 'EPSG:3857'
      }),
      zIndex: -1
    })
  }
  function loadBaiduArcGISTile() {
    // 离线百度服务
    var projection = ol.proj.get('EPSG:3857')
    console.log(projection)
    var origin = [-33554432.0, 33554432.0000001]
    var fullExtent = [-33554432.0, -33554431.999999903, 33554432.0, 33554432.0000001]
    var resolutions = [262144.0, 131072.0, 65536.0, 32768.0, 16384.0, 8192.0, 4096.0, 2048.0, 1024.0, 512.0, 256.0, 128.0, 64.0, 32.0, 16.0, 8.0, 4.0, 2.0, 1.0]
    var tileGrid = new ol.tilegrid.TileGrid({
      tileSize: 256,
      origin: origin,
      extent: fullExtent,
      resolutions: resolutions
    })
    return new ol.layer.Tile({
      source: new ol.source.TileWMS({
        // url: "http://localhost:8888/geowebcache/service/wms",
        url: mapConfig.mapServeUrl || 'http://10.45.157.223:8888/geowebcache/service/wms',
        params: { 'LAYERS': 'shanghai_bd', format: 'image/png', SRS: 'EPSG:3857' },
        tileGrid: tileGrid
      }),
      zIndex: -1
    })
  }
  // eslint-disable-next-line no-unused-vars
  function loadOSM() {
    var basicLayer = new ol.layer.Tile({
      source: new ol.source.OSM(),
      zIndex: -1
    })
    return basicLayer
  }

  function loadWMTS() {
    var projection = ol.proj.get('EPSG:4326')
    var projectionExtent = projection.getExtent()
    var size = ol.extent.getWidth(projectionExtent) / 256
    var resolutions = []
    var matrixIds = []
    for (var z = 0; z < 19; ++z) {
      resolutions[z] = size / Math.pow(2, z)
      matrixIds[z] = z
    }
    var wtmsSource = new ol.source.WMTS({
      url: mapConfig.mapServeUrl || 'http://10.45.157.179:8008/',
      projection: projection,
      style: 'default',
      format: 'image/png',
      // matrixSet: 'EPSG:4326',
      // layer: "SXTDTSL10_20",
      // layer: 'TDTSL',
      tileGrid: new ol.tilegrid.WMTS({
        origin: ol.extent.getTopLeft(projectionExtent),
        resolutions: resolutions,
        matrixIds: matrixIds
      })
    })
    mapLayer = new ol.layer.Tile({
      source: wtmsSource,
      zIndex: -1
    })
    return mapLayer
  }

  function loadTMS() {
    return new ol.layer.Tile({
      source: new ol.source.XYZ({
        url: mapConfig.mapServeUrl + '{z}/{x}/{-y}.png',
        projection: 'EPSG:3857'
      }),
      zIndex: -1
    })
  }

  function loadImageLayer() {
    let extent, projection
    if (mapConfig.units && mapConfig.units === 'pixels') {
      extent = mapConfig.bounds
      projection = new ol.proj.Projection({
        code: 'xkcd-image',
        units: 'pixels',
        extent: extent
      })
    } else {
      let bounds1 = ol.proj.transform([mapConfig.bounds[0], mapConfig.bounds[1]], 'EPSG:4326', 'EPSG:3857')
      let bounds2 = ol.proj.transform([mapConfig.bounds[2], mapConfig.bounds[3]], 'EPSG:4326', 'EPSG:3857')
      projection = ol.proj.get('EPSG:3857')
      extent = bounds1.concat(bounds2)
    }
    return new ol.layer.Image({
      source: new ol.source.ImageStatic({
        url: mapConfig.imageUrl,// 这里添加静态图片的地址
        projection: projection,
        imageExtent: extent
      })
    })
  }

  let mapLayer = LoadGaodeArcGISTile()
  if (mapConfig.mapType) {
    if (mapConfig.mapType.indexOf('baidu') > -1) {
      mapLayer = loadBaiduArcGISTile()
    } else if (mapConfig.mapType.indexOf('wmts') > -1) {
      mapLayer = loadWMTS()
    } else if (mapConfig.mapType.indexOf('tms') > -1) {
      mapLayer = loadTMS()
    } else if (mapConfig.mapType.indexOf('image') > -1) {
      mapLayer = loadImageLayer()
    }
  }
  return mapLayer
}

function openlayerMap(mapServeUrl) {
  // let selectSingleClick; // 用来选择矢量要素
  let drawVector = []
  let clusterLayer
  let openlayerMap = {}
  let map
  let markersLayer = {}
  if (mapServeUrl && mapServeUrl.lastIndexOf('/') !== (mapServeUrl.length - 1) && mapServeUrl.indexOf('?') === -1) {
    mapServeUrl += '/'
  }
  // 事件参数
  openlayerMap.Constants = {
    ZOOM_CHANGED: 'zoomchange',
    ZOOM_END: 'moveend',
    DRAG_END: 'dragend', // dragend   pointerdrag
    CLICK: 'click', // 单击事件 singleclick
    DBLCLICK: 'dblclick', // 双击事件
    RIGHTCLICK: 'contextmenu', // 'rightclick',
    LOADED: 'tilesloaded', // 地图加载完成
    MOUSEMOVE: 'pointermove', // 鼠标在地图区域移动过程中触发此事
    MOUSEOVER: 'mouseover',
    MOUSEOUT: 'mouseout'
  }
  // 坐标系参数
  // 提供wgs84球面坐标与平面坐标的转换
  openlayerMap.Coordinate = {
    WGS84: 'EPSG:4326', // wgs84 经纬度
    WGS84_flat: 'EPSG:3857' // wgs84 平面坐标系
  }
  /**
   * map类
   * mapType:不传，默认高德。"baidu"：百度离线。
   */
  openlayerMap.Map = function (mapDiv, opts, mapType) {
    let mapOpt = {
      mapServeUrl: mapServeUrl,
      mapType: mapType || opts.mapType
    }
    if (opts.imageUrl || opts.imageUrl === '') mapOpt.imageUrl = opts.imageUrl
    if (opts.bounds) mapOpt.bounds = opts.bounds
    if (opts.units) mapOpt.units = opts.units
    let mapLayer = createMapLayer(mapOpt)
    this.mapLayer = mapLayer
    clusterLayer = new ol.layer.Vector({
      source: new ol.source.Vector({
        features: []
      }),
      zIndex: 1
    })
    let optParma = {
      center: { lng: 118.6589, lat: 32.1474 },
      zoom: 12,
      minZoom: 3,
      maxZoom: 20
    }
    opts = Object.assign({}, optParma, opts)
    if (opts && opts.center) {
      optParma.center = [opts.center.lng, opts.center.lat]
    }
    // console.log(ol.extent.getCenter(opts.bounds))
    let view = {
      // 'EPSG:4326'  :wgs84的球体坐标。经纬度表示
      // 'EPSG:3857'  :wgs84的平面坐标。千米、米的形式
      // center: ol.proj.transform(optParma.center, 'EPSG:4326', 'EPSG:3857'), // [118.6589, 32.1474]
      // center: ol.extent.getCenter(extent),
      zoom: opts.zoom,
      minZoom: Number(opts.minZoom),
      maxZoom: Number(opts.maxZoom)
    }
    if (opts.bounds && (opts.imageUrl || opts.imageUrl === '')) {
      // view.projection = ol.proj.get('EPSG:3857')
      // view.center = ol.proj.transform(optParma.center, 'EPSG:4326', 'EPSG:3857')
      let projection = ol.proj.get('EPSG:3857')
      if (opts.units && opts.units === 'pixels') {
        let extent = opts.bounds
        projection = new ol.proj.Projection({
          code: 'xkcd-image',
          units: 'pixels',
          extent: extent
        })
        view.center = optParma.center
        // view.center = ol.extent.getCenter(opts.bounds)
      } else {
        projection = ol.proj.get('EPSG:3857')
        view.center = ol.proj.transform(optParma.center, 'EPSG:4326', 'EPSG:3857')
      }
      view.projection = projection
    } else {
      view.center = ol.proj.transform(optParma.center, 'EPSG:4326', 'EPSG:3857')
    }
    map = new ol.Map({
      controls: ol.control.defaults({
        attribution: false,
        zoom: false,
        rotate: false
      }),
      layers: [
        mapLayer, clusterLayer
      ],
      view: new ol.View(view),
      target: mapDiv
    })
    map.opts = opts
    // 选择Feature，当前没用到此方法
    // selectSingleClick = new ol.interaction.Select();
    // map.addInteraction(selectSingleClick);
    // mouseover ,mouseout   Feature
    let moveFeature
    map.on('pointermove', function (e) {
      var pixel = map.getEventPixel(e.originalEvent)
      var feature = map.forEachFeatureAtPixel(pixel, function (feature) {
        return feature
      })
      if (moveFeature != feature) {
        if (feature) {
          let listeners = feature.listeners_
          listeners.mouseover && listeners.mouseover.forEach((item) => {
            item()
          })
        }
        if (moveFeature) {
          let listeners = moveFeature.listeners_
          listeners.mouseout && listeners.mouseout.forEach((item) => {
            item()
          })
        }
      }
      moveFeature = feature
    })
    // 点击Feature
    map.on('click', function (e) {
      var pixel = map.getEventPixel(e.originalEvent)
      var feature = map.forEachFeatureAtPixel(pixel, function (feature) {
        return feature
      })
      if (feature) {
        let listeners = feature.listeners_
        listeners.click && listeners.click.forEach((item) => {
          item()
        })
        if (!listeners.click && feature.values_ && feature.values_.features && feature.values_.features.length > 0) {
          // 解决聚合点不出发点击事件bug
          let fMListeners = feature.values_.features[0].listeners_
          fMListeners.click && fMListeners.click.forEach((item) => {
            item()
          })
        }
      }
    })
    // 右键菜单
    map.on('contextmenu', function (e) {
      var pixel = map.getEventPixel(e.originalEvent)
      var feature = map.forEachFeatureAtPixel(pixel, function (feature) {
        return feature
      })
      if (feature) {
        let listeners = feature.listeners_
        listeners.contextmenu && listeners.contextmenu.forEach((item) => {
          item()
        })
      }
    })
    map = $.extend(map, this)
    // document.querySelector('.ol-control').style.display = 'none'
    return map
  }
  /**
   map接口
   */
  openlayerMap.Map.prototype = {
    lnglatToPixel: function (lnglat, zoom) {
      let olCoordinate = [lnglat.lng, lnglat.lat]
      let platCoordinate
      if (!map.opts.units || map.opts.units !== 'pixels') {
        platCoordinate = ol.proj.transform(olCoordinate, 'EPSG:4326', 'EPSG:3857')
      }
      return this.getPixelFromCoordinate(platCoordinate)
    },
    pixelToLngLat: function (pixel,zoom) {
      let olPixel = [pixel.x, pixel.y]
      let coordinate = this.getCoordinateFromPixel(olPixel)
      if (map.opts.units && map.opts.units === 'pixels') {
        return coordinate
      } else {
        return ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326')
      }
    },
    setEngineUrl: function (mapConfig) {
      let mapLayer = createMapLayer(mapConfig)
      this.addLayer(mapLayer)
      this.removeLayer(this.mapLayer)
      this.mapLayer = mapLayer
    },
    setStatus: function () {

    },
    _centerAndZoom: function (point, zoom) {
      if ((!this.opts.units || this.opts.units !== 'pixels') && (point[0] < -180 || point[0] > 180 || point[1] < -85 || point[1] > 85)) {
        return false
      }
      if (!Array.isArray(point)) {
        point = point.getFirstCoordinate()
      }
      point[0] = parseFloat(point[0])
      point[1] = parseFloat(point[1])
      let center
      if (this.opts.units && this.opts.units === 'pixels') {
        center = point
      } else {
        center = ol.proj.transform(point, 'EPSG:4326', 'EPSG:3857')
      }
      this.getView().animate({
        center: center,
        duration: 500,
        zoom: zoom
      })
    },
    _center: function (point) {
      // if (!Array.isArray(point)) {
      //   point = point.getFirstCoordinate()
      // }
      // point[0] = parseFloat(point[0])
      // point[1] = parseFloat(point[1])
      point[0] = parseFloat(point.lng || point[0])
      point[1] = parseFloat(point.lat || point[1])
      let cPt = [point[0], point[1]]
      let center
      if (this.opts.units && this.opts.units === 'pixels') {
        center = point
      } else {
        center = ol.proj.transform(cPt, 'EPSG:4326', 'EPSG:3857')
      }
      this.getView().animate({
        center: center,
        duration: 500
      })
    },
    _zoom: function (zoom) {
      this.getView().animate({
        zoom: zoom,
        duration: 500
      })
    },
    _getCenter: function () {
      // 直接获取的坐标是平面坐标
      let center = this.getView().getCenter()
      let lnglat
      if (this.opts.units && this.opts.units === 'pixels') {
        lnglat = center
      } else {
        lnglat = ol.proj.transform([center[0], center[1]], 'EPSG:3857', 'EPSG:4326')
      }
      return { lng: lnglat[0], lat: lnglat[1] }
    },
    _getZoom: function () {
      return this.getView().getZoom()
    },
    // 获取地图可视坐标范围
    _getView: function () {
      let mapExtent = this.getView().calculateExtent(this.getSize())
      return new openlayerMap.Bounds(mapExtent)
    },

    /*
     *图层有vector，overlay两种
     *addOverlay：默认true。添加Overlay
     */
    _addOverlay: function (layer, addOverlay) {
      if (addOverlay) {
        this.addOverlay(layer)
      } else {
        this.addLayer(layer)
      }
    },
    /*
     *设置地图当前屏幕可视范围
     */
    _setView: function(layer) {
      let ext = layer.getSource().getExtent()
      map.getView().fit(ext, map.getSize(), {
        constrainResolution: false,
        earest: false
      })
    },
    _removeOverlays: function (overlays) {
      overlays.forEach((v) => {
        this.removeOverlay(v)
      })
    },
    _removeOverlay: function (overlay, isOverlay) {
      if (isOverlay) {
        this.removeOverlay(overlay)
      } else {
        this.removeLayer(overlay)
      }
    },
    _clearOverlays: function () {
      let overlayers = this.getOverlays()
      for (let i = 0; i < overlayers.a.length; i++) {
        this.removeOverlay(overlayers.a[i])
      }
    },
    _addControl: function (control) {
      // let newControl = ol.control.defaults().extend([
      //   control
      // ])
      // this.addControl(newControl)
      this.addControl(control)
    },
    _removeControl: function (control) {
      this.removeControl(control)
    },
    _addTrafficeLayer: function () {
      /* let trafficLayer = new AMap.TileLayer.Traffic({
        zIndex: 10
      })
      trafficLayer.setMap(this)
      this._trafficLayer = trafficLayer */
    },
    _removeTrafficeLayer: function () {
      if (this._trafficLayer) {
        this._trafficLayer.setMap(null)
      }
    },
    _getAllOverlays: function (type) {
      return this.getAllOverlays(type)
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      let mouseEvents = ['pointermove', 'click', 'dblclick', 'rightclick', 'singleclick', 'mouseup']
      if (mouseEvents.indexOf(event) >= 0) {
        this.on(event, function (e) {
          // e.coordinate平面坐标
          let pt
          if (map.opts.units && map.opts.units === 'pixels') {
            pt = [e.coordinate[0], e.coordinate[1]]
          } else {
            pt = ol.proj.transform([e.coordinate[0], e.coordinate[1]], 'EPSG:3857', 'EPSG:4326')
          }
          let point = new openlayerMap.Point(pt[0], pt[1])
          handler(point)
        })
      } else if (event.indexOf('moveend') !== -1) {
        this.on(event, function (e) {
          if (!this.eventHandlerMap) {
            this.eventHandlerMap = {}
          }
          let data = {}
          this.eventHandlerMap[event] = handler
          data.zoom = map._getZoom()
          if (data.zoom === map.prevZoom) {
            let handler1 = this.eventHandlerMap[openlayerMap.Constants.DRAG_END]
            if (handler1) {
              handler1(event)
            }
          } else {
            handler(data)
          }
          map.prevZoom = data.zoom
        })
      } else if (event.indexOf('contextmenu') !== -1) {
        this.on(event, function (e) {
          // var coordinate = map.getEventCoordinate(e);
          console.log(e)
          e.preventDefault()
          handler(e.coordinate)
        })
      } else if (event == 'complete') {
        this.on('postrender', handler)
        setTimeout(() => {
          this.un('postrender', handler)
        }, 30)
      } else {
        this.on(event, handler)
      }
    },
    _on: function (event, handler) {
      let map = this
      if (!this.eventHandlerMap) {
        this.eventHandlerMap = {}
      }
      this.eventHandlerMap[event] = handler
      this.on(event, function (e) {
        if (event.indexOf('moveend') !== -1) {
          e.zoom = map._getZoom()
          if (e.zoom === map.prevZoom) {
            let handler1 = this.eventHandlerMap[openlayerMap.Constants.DRAG_END]
            if (handler1) {
              handler1(e)
            }
          } else {
            handler(e)
          }
          map.prevZoom = e.zoom
        } else {
          handler(e)
        }
      })
    },
    _destroy: function () {
      // return this.destroy()
    },
    destroy: function () {
      // return this.destroy()
    },
    _setMapStyle: function (styleName) {

    },
    _transformPoint (v) {
      if (map.opts.units && map.opts.units === 'pixels') {
        return [v[0], v[1]]
      } else {
        return ol.proj.transform([v[0], v[1]], 'EPSG:3857', 'EPSG:4326')
      }
    },
    _updateSize(opts) {
      return this.updateSize()
    },
    // 编辑要素
    _editFeatures: function (layer, callback) {
      var modify = new ol.interaction.Modify({ source: layer.getSource() })
      modify.on('modifyend', function () {
        // 编辑结束时获取坐标点
        var features = layer.getSource().getFeatures()
        var geometry = features[0].getGeometry()
        var points
        var lnglat = []
        points = geometry.getCoordinates()[0]
        for (var i = 0; i < points.length; i++) {
          let pt
          if (map.opts.units && map.opts.units === 'pixels') {
            pt = points[i]
          } else {
            pt = ol.proj.transform(points[i], 'EPSG:3857', 'EPSG:4326')
          }
          lnglat.push(pt)
        }
        // console.log(lnglat);
        callback(lnglat)
      })
      map.addInteraction(modify)
    },
    _transformPointToOL(v) {
      return ol.proj.transform([v[0], v[1]], 'EPSG:4326', 'EPSG:3857')
    }
  }

  openlayerMap.Layer = function (map, name, opts) {
    this.name = name
    let iconSrc = 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
    if (opts && opts.icon) {
      iconSrc = opts.icon
    }
    let style = new ol.style.Style({
      image: new ol.style.Icon({
        src: iconSrc
      })
    })
    let source = new ol.source.Vector({ wrapX: false })
    let dVector = new ol.layer.Vector({
      source: source,
      style: style
    })
    // map.addLayer(dVector)
    return ($.extend(dVector, this))
  }
  openlayerMap.Layer.prototype = {
    // 将图层加入地图
    _addToMap: function (map) {
      map.addLayer(this)
    },
    _addMarker: function (fea) {
      this.getSource().addFeature(fea)
    },
    _removeMarker: function (fea) {
      this.getSource().removeFeature(fea)
    },
    _show: function () {
      this.setVisible(true)
    },
    _hide: function () {
      this.setVisible(false)
    },
    _getMarkers: function () {
      return this.getSource().getFeatures()
    }
  }

  openlayerMap.Bounds = function (mapExtent) {
    let lnglat, lnglat01
    if (map.opts.units && map.opts.units === 'pixels') {
      lnglat = [mapExtent[0], mapExtent[1]]
      lnglat01 = [mapExtent[2], mapExtent[3]]
    } else {
      lnglat = ol.proj.transform([mapExtent[0], mapExtent[1]], 'EPSG:3857', 'EPSG:4326')
      lnglat01 = ol.proj.transform([mapExtent[2], mapExtent[3]], 'EPSG:3857', 'EPSG:4326')
    }
    if (lnglat[0] > lnglat01[0]) {
      this.wlng = lnglat01[0]
      this.elng = lnglat[0]
    } else {
      this.wlng = lnglat[0]
      this.elng = lnglat01[0]
    }
    if (lnglat[1] > lnglat01[1]) {
      this.slat = lnglat01[1]
      this.nlat = lnglat[1]
    } else {
      this.slat = lnglat[1]
      this.nlat = lnglat01[1]
    }
    this.northeast = [this.elng, this.nlat]
    this.southwest = [this.wlng, this.slat]
  }

  openlayerMap.Bounds.prototype = {
    getNorthEast: function () {
      return new openlayerMap.Point(this.elng, this.nlat)
    },
    getSouthWest: function () {
      return new openlayerMap.Point(this.wlng, this.slat)
    },
    containsLngLat: function (position) {
      let res = false
      let lng = position.getLng ? position.getLng() : position[0]
      let lat = position.getLat ? position.getLat() : position[1]
      if (this.elng >= lng && this.wlng <= lng && this.slat <= lat && this.nlat >= lat) {
        res = true
      }
      return res
    }
  }

  openlayerMap.ImageLayer = function (opts) {
    let extent, projection
    if (map.opts.units && map.opts.units === 'pixels') {
      extent = opts.bounds
      projection = new ol.proj.Projection({
        code: 'xkcd-image',
        units: 'pixels',
        extent: extent
      })
    } else {
      let bounds1 = ol.proj.transform([opts.bounds[0], opts.bounds[1]], 'EPSG:4326', 'EPSG:3857')
      let bounds2 = ol.proj.transform([opts.bounds[2], opts.bounds[3]], 'EPSG:4326', 'EPSG:3857')
      projection = ol.proj.get('EPSG:3857')
      extent = bounds1.concat(bounds2)
    }
    let imageLayer = new ol.layer.Image({
      source: new ol.source.ImageStatic({
        url: opts.imageUrl,// 这里添加静态图片的地址
        projection: projection,
        imageExtent: extent
      })
    })
    return ($.extend(imageLayer, this))
  }

  openlayerMap.ImageLayer.prototype = {
    _setVisible: function (visible) {
      this.setVisible(visible)
    },
    _setZIndex: function (index) {
      this.setZIndex(index)
    }
  }

// Marker 使用feature替代overlay
  openlayerMap.OverlayMarker = function (map, opts) {
    this.id = opts.id
    this.type = opts.type
    this.iconSrc = opts.icon
    this.extData = opts.extData || {}
    this.extData.size = opts.size
    if (opts.position) {
      this.position = new openlayerMap.Point(opts.position.lng, opts.position.lat)
    }
    this.visible = true
    this.imgdiv = document.createElement('div')
    this.textdiv = document.createElement('div') // 设备标注
    if (opts.time) {
      let timediv = document.createElement('div')
      let correct = document.createTextNode(opts.time)
      timediv.appendChild(correct)
    }
    if (opts.icon) {
      let img = document.createElement('img')
      img.src = opts.icon
      this.imgdiv.style.height = opts.size.height
      this.imgdiv.style.width = opts.size.width
      this.imgdiv.style.cursor = 'pointer'
      img.width = opts.size.width
      img.height = opts.size.height
      img.className = opts['class'] || ''
      this.imgdiv.appendChild(img)
      let name = '' // 标注字段
      if (this.extData.deviceName) {
        name = this.extData.deviceName
      } else if (this.extData.buildingName) {
        name = this.extData.buildingName
      } else if (this.extData) {
        name = this.extData // precinctName;
      } else if (this.extData.companyTypeName) {
        name = this.extData.companyTypeName
      }
      this.textdiv.innerHTML = name
      this.textdiv.style.position = 'absolute'
      this.textdiv.style.display = 'none'
      this.textdiv.style.border = '1px solid gray'
      this.textdiv.style.background = 'white'
      this.textdiv.style.fontSize = '14px'
      this.textdiv.style.lineHeight = '18px'
      this.imgdiv.appendChild(this.textdiv)
    }
    let offset = [0, 0]
    if (opts.offset) {
      offset[0] = opts.offset.x
      offset[1] = opts.offset.y
    }
    let markerOpts = {
      // position: this.position.getFirstCoordinate(),
      positioning: 'center-center',
      element: this.imgdiv,
      stopEvent: false,
      insertFirst: false,
      offset: offset
    }
    if (this.position) {
      markerOpts.position = this.position.getFirstCoordinate()
    }
    let marker = new ol.Overlay(markerOpts)
    this.map = map
    this.protoSelf = marker
    // marker.setMap(map);
    // map.addOverlay(marker);
    marker.map = map
    marker.position = this.position
    marker = $.extend(marker, this)
    markersLayer[marker.extData.id] = marker
    return marker
  }

  openlayerMap.OverlayMarker.prototype = {
    _getPosition: function () {
      // return this.getPosition()
      let pos = flatToGeo(this.getPosition()[0], this.getPosition()[1])
      return pos
    },
    _setPosition: function (lnglan) {
      let runPos = new openlayerMap.Point(lnglan.lng || lnglan[0], lnglan.lat || lnglan[1])
      // let runPos = ol.proj.transform([lnglan[0], lnglan[1]], 'EPSG:4326', 'EPSG:3857')
      return this.setPosition(runPos.getFirstCoordinate())
    },
    _getIcon: function () {
      let icon = this.getIcon()
      if (typeof icon === 'object') {
        icon = icon.image
      }
      return icon
    },
    // _setIcon: function (iconOpts) {
    //   let imgdiv = this.getElement()
    //   let opts = {}
    //   if (typeof iconOpts === 'string') {
    //     opts.icon = iconOpts
    //   } else {
    //     if (iconOpts.iconSrc) {
    //       iconOpts.icon = iconOpts.iconSrc
    //     }
    //     opts = $.extend({}, opts, iconOpts)
    //   }
    //   imgdiv.src = opts.icon
    //   this.setElement(imgdiv)
    // },
    _setIcon: function (iconOpts) {
      let imgdiv = this.getElement()
      let opts = {}
      if (typeof iconOpts == 'string') {
        opts.icon = iconOpts
      } else {
        if (iconOpts.iconSrc) {
          iconOpts.icon = iconOpts.iconSrc
        }
        opts = $.extend({}, opts, iconOpts)
      }
      $('img', imgdiv).attr('src', opts.icon)
      this.setElement(imgdiv)
    },
    _setIconSize: function (width, height) { // 点图标尺寸放大
      let imgdiv = this.getElement()
      $('img', imgdiv).attr('width', width)
      $('img', imgdiv).attr('height', height)
    },
    setzIndex: function (zIndex) {
      let imgdiv = this.getElement()
      imgdiv.parentNode.style.zIndex = zIndex
      this.setElement(imgdiv)
    },
    _setBigIcon: function (icon) {
      let imgdiv = this.getElement()
      imgdiv.style.position = 'relative'
      imgdiv.style.top = '-10px'
      imgdiv.style.width = '30px'
      imgdiv.style.height = '50px'
      // this.setElement(imgdiv);
      if (imgdiv) {
        imgdiv.style.top = '-56px'
        imgdiv.style.fontSize = '18px'
      }
    },
    /*_setLabel: function (label) {
      let imgdiv = this.getElement('div')
      let of = label.offset
      let left = (of && of.x) || 0
      let top = (of && of.y) || 0
      let labelContent = `<div class="amap-marker-label" style="left: ${left}px;top: ${top}px">${label.content}</div>`
      $(imgdiv).append(labelContent)
    },*/
    _setLabel: function (label) {
      let imgdiv = $(this.getElement('div'))
      let of = label.offset
      let left = (of && of.x) || 0
      let top = (of && of.y) || 0

      let labelDOM = imgdiv.find('.amap-marker-label')

      if(labelDOM.length > 0) {
        labelDOM.html(label.content)
      }else{
        let labelContent = `<div class="amap-marker-label" style=" position: absolute; left: ${left}px;top: ${top}px">${label.content}</div>`
        imgdiv.append(labelContent)
      }
    },
    _setLabel2: function (label) {
      // let text = new ol.Overlay({
      //     position: this._getPosition(),//ol.proj.fromLonLat(this._getPosition()),
      //     element: document.createElement("div")
      // });
      // //map.addOverlay(text); //添加
      // text.getElement().innerText = label.content;  //替换文字
      // map.addOverlay(text);
      let imgdiv = this.getElement()
      let label2 = "<div style='top: -24px; text-align: center; color: #fff; position: relative;font-size: 11px;'>" + label + '</div>'
      imgdiv.appendChild(label2)
    },
    _setMap: function (map) {
      this.setMap(map)
    },
    _show: function () {
      // this.setMap(this.map);
      this.getElement().style.display = 'block'
      this.visible = true
    },
    _hide: function () {
      // this.setMap(null);
      this.getElement().style.display = 'none'
      this.visible = false
    },
    _add: function () {
      this.visible = true
      if (!this.isAdded) {
        this.map.addOverlay(this)
        this.isAdded = true
      }
      this._show()
    },
    _remove: function () {
      //  this._hide();
      this.isAdded = false
      this.visible = false
      this.map.removeOverlay(this)
    },
    _getMap: function () {
      return this.getMap()
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      if (event === openlayerMap.Constants.RIGHTCLICK) { // 通过contextmenu触发Marker右键
        event = 'contextmenu'
        event.preventDefault()
      }
      let m = this
      if (this.imgdiv.attachEvent) {
        this.imgdiv.attachEvent(event, function () {
          handler && handler(m)
        })
      } else {
        this.imgdiv.addEventListener(event, function () {
          handler && handler(m)
        })
      }
    },
    _setAnimation: function (animate) {
      this.getElement().className = ''
      if (animate !== 'ANIMATION_NONE') this.getElement().classList.add(animate)
    },
    _infoTipShow: function () {
      this.textdiv.style.display = 'block'
    },
    _infoTipClose: function () {
      this.textdiv.style.display = 'none'
    }
  }

  openlayerMap.Text = function (map, opts) {
    let defOpts = {
      map: map,
      text: '纯文本标记',
      anchor: 'center', // 设置文本标记锚点
      draggable: false,
      cursor: 'pointer',
      angle: 0,
      style: {
        'padding': '.75rem 1.25rem',
        'margin-bottom': '1rem',
        'border-radius': '.25rem',
        'background-color': 'white',
        'width': '15rem',
        'border-width': 0,
        'box-shadow': '0 2px 6px 0 rgba(114, 124, 245, .5)',
        'text-align': 'center',
        'font-size': '20px',
        'color': 'blue'
      }
    }
    if (opts) {
      defOpts = $.extend(defOpts, opts)
      // let text = new ol.style.Text(defOpts.text, opts)
      let text = new ol.style.Text(defOpts)
      this.protoSelf = text
      return ($.extend(text, this))
    }
  }
  openlayerMap.Text.prototype = {
    // 设置label的样式
    _setStyle: function (opt) {
      this.setStyle(opt)
    },
    _show: function () {
      // this.show()
    },
    _hide: function () {
      // this.hide()
    },
    // Label添加事件
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        // handler(e.type, e.target)
      })
    }
  }

  openlayerMap.ContextMenu = function (element, menuId) {
    let menuElement
    let menuOverlay
    this.marker = element

    menuElement = document.createElement('div')
    menuElement.className = 'contextmenu'
    menuElement.id = menuId || 'contextmenu_container'
    let menuUl = document.createElement('ul')
    menuUl.className = 'menuul'
    menuElement.appendChild(menuUl)
    menuOverlay = new ol.Overlay({
      element: menuElement,
      offset: [15, 0],
      positioning: 'center-left'
    })
    this.menuUl = menuUl
    map.addOverlay(menuOverlay)
    menuOverlay = $.extend(menuOverlay, this)
    return menuOverlay
  }

  openlayerMap.ContextMenu.prototype = {
    _addItem: function (itemname, handler, index) {
      let self = this
      let li = document.createElement('li')
      li.className = 'menuitem'
      // let a = document.createElement('a')
      // a.setAttribute('href', '#')
      // a.innerHTML = itemname
      // li.appendChild(a)
      li.innerHTML = itemname
      li.onclick = function (e) {
        self.setPosition(undefined)
        handler(e)
      }
      this.menuUl.appendChild(li)
    },
    _open: function (map, e) {
      let position = e
      if (!e) {
        let pt = this.marker._getPosition()
        if (map.opts.units && map.opts.units === 'pixels') {
          position = [pt.lng, pt.lat]
        } else {
          position = ol.proj.transform([pt.lng, pt.lat], 'EPSG:4326', 'EPSG:3857')
        }
      }
      this.setPosition(position)
    },
    _close: function () {
      this.setPosition(undefined)
    }
  }

  // 点对象
  openlayerMap.Point = function (lng, lat) {
    // return ($.extend(ol.proj.transform([lng,lat], 'EPSG:4326', 'EPSG:3857'),this));
    let lnglat
    if (map.opts.units && map.opts.units === 'pixels') {
      lnglat = [parseFloat(lng), parseFloat(lat)]
    } else {
      lnglat = ol.proj.transform([parseFloat(lng), parseFloat(lat)], 'EPSG:4326', 'EPSG:3857')
    }
    this.lng = lng
    this.lat = lat
    return ($.extend(new ol.geom.Point(lnglat), this))
  }

  openlayerMap.Point.prototype = {
    _getLng: function () {
      return this.lng
    },
    _getLat: function () {
      return this.lat
    },
    getLng: function () {
      return this.lng
    },
    getLat: function () {
      return this.lat
    },
    _getLnglat: function () {
      return [this.lng, this.lat]
    }
  }

  // 矢量圆形标记
  openlayerMap.Circle = function (map, opt) {
    let defOpts = {
      // center: [121.48175477165995, 31.28390596169205],
      radius: 100, // 半径
      strokeColor: '#FF33FF',
      strokeOpacity: 0,
      fillOpacity: 0.4,
      // 线样式还支持 'dashed'
      fillColor: '#1791fc',
      zIndex: 50
    }
    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }
    let layer = new ol.layer.Vector({
      source: new ol.source.Vector()
    })
    defOpts.center = [defOpts.center.lng, defOpts.center.lat]
    let center
    if (map.opts.units && map.opts.units === 'pixels') {
      center = defOpts.center
    } else {
      center = ol.proj.transform(defOpts.center, 'EPSG:4326', 'EPSG:3857')
    }
    let circle = new ol.geom.Circle(center, defOpts.radius)
    let feature = new ol.Feature({
      geometry: circle
    })
    // let featureStyle = new ol.style.Style({
    //   fill: new ol.style.Fill({
    //     color: hexToRgba(defOpts.fillColor, defOpts.fillOpacity)
    //   }),
    //   stroke: new ol.style.Stroke({
    //     color: defOpts.strokeColor,
    //     width: defOpts.strokeWeight,
    //     lineDash: defOpts.strokeDasharray ? [10, 10] : []
    //   })
    // })
    defOpts.fill = new ol.style.Fill({
      color: hexToRgba(defOpts.fillColor, defOpts.fillOpacity)
    })
    defOpts.stroke = new ol.style.Stroke({
      color: defOpts.strokeColor,
      width: defOpts.strokeWeight,
      lineDash: defOpts.strokeDasharray ? [10, 10] : []
    })
    feature.setStyle(new ol.style.Style(defOpts))
    layer.getSource().addFeature(feature)
    this.circle = circle
    this.feature = feature
    this.opts = defOpts
    this.extData = defOpts.extData
    // layer._add(map)
    return $.extend(layer, this)
  }
  openlayerMap.Circle.prototype = {
    _getCenter: function () {
    },
    _getRadius: function () {
    },
    _show: function () {
    },
    _hide: function () {
    },
    _add: function (map) {
      map.addLayer(this)
    },
    _remove: function (map) {
      this.setSource(null)
    },
    _contains: function (lngLat) {
      let point = ol.proj.transform(lngLat, 'EPSG:4326', 'EPSG:3857')
      return this.getSource().getFeatures()[0].getGeometry().intersectsCoordinate(point)
    },
    _setView: function () {

    },
    _addEventListener: function (event, handler) {
      let feature = this.feature
      feature.addEventListener(event, function () {
        handler()
      })
    },
    _setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts)
      let feature = this.feature
      feature.style_.setFill(new ol.style.Fill({
        color: hexToRgba(defOpts.fillColor, opts.fillOpacity)
      }))
      feature.style_.setStroke(new ol.style.Stroke({
        color: hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity),
        width: defOpts.strokeWeight
      }))
      feature.setStyle(feature.style_)
    }
  }

  // 矢量圆形标记编辑
  openlayerMap.CircleEditor = function (map, layer) {
    let modify = new ol.interaction.Modify({
      source: layer.getSource()
    })
    map.addInteraction(modify)
    return $.extend(modify, this)
  }
  openlayerMap.CircleEditor.prototype = {
    _close: function () {
      map.removeInteraction(this)
    },
    _open: function () {
      map.addInteraction(this)
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      if (event === 'move') this.on('modifyend', handler)
    }
  }

  // 地图上加线 -- ol.layer.Vector
  openlayerMap.Polyline = function (map, lineArr, opts) {
    let PolylineOpts = {
      stroke: new ol.style.Stroke({
        width: opts.strokeWeight || 3,
        color: opts.strokeColor || [255, 0, 0, 1],
        lineDash: opts.strokeDash || null
      })
    }
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
    }
    let clineArr = []
    for (let i = 0; i < lineArr.length; i++) {
      let item = lineArr[i]
      clineArr.push([item.lng, item.lat])
    }
    let line = new ol.geom.LineString(clineArr)
    if (!map.opts.units || map.opts.units !== 'pixels') {
      line.applyTransform(ol.proj.getTransform('EPSG:4326', 'EPSG:3857'))
    }
    let feature = new ol.Feature({
      geometry: line
    })
    feature.setStyle(new ol.style.Style(PolylineOpts))
    let source = new ol.source.Vector({
      features: [feature]
    })
    let layer = new ol.layer.Vector({
      source: source
    })
    map.addLayer(layer)
    if (PolylineOpts.isDrawArrow) { // 是否绘制箭头
      // 绘制箭头
      let geometry = feature.getGeometry()
      geometry.forEachSegment(function (start, end) {
        if (!(end[0] == start[0] && end[1] == start[1])) { // 起始点不加箭头
          let x1 = (end[0] + start[0]) / 2
          let y1 = (end[1] + start[1]) / 2
          let newLonLat = [(x1 + end[0]) / 2, (y1 + end[1]) / 2]
          let dx = end[0] - start[0]
          let dy = end[1] - start[1]
          let rotation = Math.atan2(dy, dx)
          let arrowStyle = new ol.style.Style({
            image: new ol.style.Icon({
              src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAMCAYAAABfnvydAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8mlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoTWFjaW50b3NoKSIgeG1wOkNyZWF0ZURhdGU9IjIwMTktMDgtMjBUMTA6MjM6NDUrMDg6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDE5LTA4LTI5VDE5OjU3OjQyKzA4OjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDE5LTA4LTI5VDE5OjU3OjQyKzA4OjAwIiBkYzpmb3JtYXQ9ImltYWdlL3BuZyIgcGhvdG9zaG9wOkNvbG9yTW9kZT0iMyIgcGhvdG9zaG9wOklDQ1Byb2ZpbGU9InNSR0IgSUVDNjE5NjYtMi4xIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjlhZWY5MmViLTExM2MtNDE4Yy05NjEwLWQ0MzY1YTRiN2ZhOSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo3NDhlOTRlNi0xMzUyLTQyMzQtODAwNy01N2EwMGQ4YjU2YjkiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo3NDhlOTRlNi0xMzUyLTQyMzQtODAwNy01N2EwMGQ4YjU2YjkiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjc0OGU5NGU2LTEzNTItNDIzNC04MDA3LTU3YTAwZDhiNTZiOSIgc3RFdnQ6d2hlbj0iMjAxOS0wOC0yMFQxMDoyMzo0NSswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKE1hY2ludG9zaCkiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjlhZWY5MmViLTExM2MtNDE4Yy05NjEwLWQ0MzY1YTRiN2ZhOSIgc3RFdnQ6d2hlbj0iMjAxOS0wOC0yOVQxOTo1Nzo0MiswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKE1hY2ludG9zaCkiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+90waCAAAAJ9JREFUGJVdzz0OgUEQh/EfIRqFE0iEI2gUohKdE+gcwAEkNMRxxBF0FBIXUEhUClFpfLyaXVnvk2wyk/nvM5lCtlBGG3t8RKYZKGKOFTqh/6OIGnpYYpgPlHBBhi7quOKIRzSU8Qp1AzP0U0MhhCIDNLGJhltYkdJKDdVgiTyxTq+4537vME0N72S4xShc9jNUQn3AOB3GwDm8CU65db4RBR2YgmZR6wAAAABJRU5ErkJggg==',
              anchor: [0.75, 0.5],
              rotateWithView: true,
              rotation: -rotation
            })
          })
          let arrowFeature = new ol.Feature({
            geometry: new ol.geom.Point([newLonLat[0], newLonLat[1]])
          })
          arrowFeature.setStyle(arrowStyle)
          layer.getSource().addFeature(arrowFeature)
        }
      })
    }
    if (PolylineOpts.isFresh) {
      let ext = layer.getSource().getExtent()
      map.getView().fit(ext, map.getSize(), {
        constrainResolution: false,
        earest: false
      })
    }
    // 绘制起始，结束点
    /* let start = ol.proj.transform([lineArr[0].lng, lineArr[0].lat], 'EPSG:4326', 'EPSG:3857');
    let end = ol.proj.transform([lineArr[lineArr.length - 1].lng, lineArr[lineArr.length - 1].lat], 'EPSG:4326', 'EPSG:3857');
    let startStyle = new ol.style.Style({
      image: new ol.style.Icon({
        src: './static/map/images/icon/map-begin.png',
        anchor: [0.75, 0.75]
      })
    });
    let startFeature = new ol.Feature({
      geometry: new ol.geom.Point([start[0], start[1]])
    });
    startFeature.setStyle(startStyle);
    layer.getSource().addFeature(startFeature);
    let endFeature = new ol.Feature({
      geometry: new ol.geom.Point([end[0], end[1]])
    });
    endFeature.setStyle(new ol.style.Style({
      image: new ol.style.Icon({
        src: './static/map/images/icon/map-finish.png',
        anchor: [0.5, 0.75]
      })
    }));
    layer.getSource().addFeature(endFeature); */
    // 将这条线显示到当前屏幕
    // let ext = layer.getSource().getExtent()
    // map.getView().fit(ext, map.getSize(), {
    //   constrainResolution: false,
    //   earest: false
    // })
    this.extData = PolylineOpts.extData
    this.polyline = line
    this.feature = feature
    return $.extend(layer, this)
  }

  openlayerMap.Polyline.prototype = {
    _remove: function () {
      // let fFeatures = this.getSource().getFeatures();
      // fFeatures.setGeometry(null);
      this.setSource(null)
    },
    _setPath: function (path) {
      let fFeatures = this.getSource().getFeatures()
      if (path) {
        fFeatures.setGeometry(new ol.geom.LineString(path))
      }
    },
    _getPath() {
      let bPoints = this.polyline.getCoordinates()
      let pts = []
      for (let i = 0; i < bPoints.length; i++) {
        let pt
        if (map.opts.units && map.opts.units === 'pixels') {
          pt = [bPoints[i][0], bPoints[i][1]]
        } else {
          pt = ol.proj.transform([bPoints[i][0], bPoints[i][1]], 'EPSG:3857', 'EPSG:4326')
        }
        pts.push({ lng: pt[0], lat: pt[1] })
        // pts.push([bPoints[i][0], bPoints[i][1]])
      }
      return pts
    },
    _addEventListener: function (event, handler) {
      let m = this
      let feature = this.feature
      feature.addEventListener(event, function () {
        handler(m)
      })
    }
  }

  // 地图上加轨迹
  openlayerMap.TrackPolyline = function (map, lineArr, opts) {
    let self = this
    this.animating = false
    let hideStartEndPoint = false // 是否显示起止点
    let hideArrow = false // 线上箭头是否显示
    let PolylineOpts = {
      stroke: new ol.style.Stroke({
        width: opts.strokeWeight || 3,
        color: opts.strokeColor || [255, 0, 0, 1]
      })
    }
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
      hideStartEndPoint = opts.hideStartEndPoint ? opts.hideStartEndPoint : false
      hideArrow = opts.hideArrow ? opts.hideArrow : false
    }
    let clineArr = []
    for (let i = 0; i < lineArr.length; i++) {
      let item = lineArr[i]
      clineArr.push([item.lng, item.lat])
    }
    let line = new ol.geom.LineString(clineArr)
    if (!map.opts.units || map.opts.units !== 'pixels') {
      line.applyTransform(ol.proj.getTransform('EPSG:4326', 'EPSG:3857'))
    }
    this.route = line
    this.routeCoords = clineArr
    let feature = new ol.Feature({
      geometry: line
    })
    this.aaastyles = {
      'geoMarker': new ol.style.Style({
        image: new ol.style.Icon({
          src: opts.icon || 'data:image/png;base64,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'
        })
      })
    }
    let geoMarkerLnglat
    if (map.opts.units && map.opts.units === 'pixels') {
      geoMarkerLnglat = [parseFloat(lineArr[0].lng), parseFloat(lineArr[0].lat)]
    } else {
      geoMarkerLnglat = ol.proj.transform([parseFloat(lineArr[0].lng), parseFloat(lineArr[0].lat)], 'EPSG:4326', 'EPSG:3857')
    }
    this.geoMarker = new ol.Feature({
      type: 'geoMarker',
      geometry: new ol.geom.Point(geoMarkerLnglat)
    })
    feature.setStyle(new ol.style.Style(PolylineOpts))
    let source = new ol.source.Vector({
      features: [feature, this.geoMarker]
    })
    let layer = new ol.layer.Vector({
      source: source,
      style: function(feature) {
        // hide geoMarker if animation is active
        // if (self.animating && feature.get('type') === 'geoMarker') {
        //   return null
        // }
        return self.aaastyles[feature.get('type')]
      }
    })
    map.addLayer(layer)
    // 绘制箭头
    let geometry = feature.getGeometry()
    geometry.forEachSegment(function (start, end) {
      if (!(end[0] == start[0] && end[1] == start[1])) { // 起始点不加箭头
        let x1 = (end[0] + start[0]) / 2
        let y1 = (end[1] + start[1]) / 2
        let newLonLat = [(x1 + end[0]) / 2, (y1 + end[1]) / 2]
        let dx = end[0] - start[0]
        let dy = end[1] - start[1]
        let rotation = Math.atan2(dy, dx)
        let arrowStyle = new ol.style.Style({
          image: new ol.style.Icon({
            src: opts.arrowIcon || 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAAMCAYAAABfnvydAAAACXBIWXMAAAsTAAALEwEAmpwYAAAF8mlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS42LWMxNDUgNzkuMTYzNDk5LCAyMDE4LzA4LzEzLTE2OjQwOjIyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RFdnQ9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZUV2ZW50IyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgMjAxOSAoTWFjaW50b3NoKSIgeG1wOkNyZWF0ZURhdGU9IjIwMTktMDgtMjBUMTA6MjM6NDUrMDg6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDE5LTA4LTI5VDE5OjU3OjQyKzA4OjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDE5LTA4LTI5VDE5OjU3OjQyKzA4OjAwIiBkYzpmb3JtYXQ9ImltYWdlL3BuZyIgcGhvdG9zaG9wOkNvbG9yTW9kZT0iMyIgcGhvdG9zaG9wOklDQ1Byb2ZpbGU9InNSR0IgSUVDNjE5NjYtMi4xIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjlhZWY5MmViLTExM2MtNDE4Yy05NjEwLWQ0MzY1YTRiN2ZhOSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo3NDhlOTRlNi0xMzUyLTQyMzQtODAwNy01N2EwMGQ4YjU2YjkiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo3NDhlOTRlNi0xMzUyLTQyMzQtODAwNy01N2EwMGQ4YjU2YjkiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjc0OGU5NGU2LTEzNTItNDIzNC04MDA3LTU3YTAwZDhiNTZiOSIgc3RFdnQ6d2hlbj0iMjAxOS0wOC0yMFQxMDoyMzo0NSswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKE1hY2ludG9zaCkiLz4gPHJkZjpsaSBzdEV2dDphY3Rpb249InNhdmVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOjlhZWY5MmViLTExM2MtNDE4Yy05NjEwLWQ0MzY1YTRiN2ZhOSIgc3RFdnQ6d2hlbj0iMjAxOS0wOC0yOVQxOTo1Nzo0MiswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTkgKE1hY2ludG9zaCkiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+90waCAAAAJ9JREFUGJVdzz0OgUEQh/EfIRqFE0iEI2gUohKdE+gcwAEkNMRxxBF0FBIXUEhUClFpfLyaXVnvk2wyk/nvM5lCtlBGG3t8RKYZKGKOFTqh/6OIGnpYYpgPlHBBhi7quOKIRzSU8Qp1AzP0U0MhhCIDNLGJhltYkdJKDdVgiTyxTq+4537vME0N72S4xShc9jNUQn3AOB3GwDm8CU65db4RBR2YgmZR6wAAAABJRU5ErkJggg==',
            anchor: [0.75, 0.5],
            rotateWithView: true,
            rotation: -rotation
          })
        })
        let arrowFeature = new ol.Feature({
          geometry: new ol.geom.Point([newLonLat[0], newLonLat[1]])
        })
        arrowFeature.setStyle(arrowStyle)
        if (!hideArrow) {
          layer.getSource().addFeature(arrowFeature)
        }
      }
    })
    // 绘制起始，结束点
    let start, end
    if (map.opts.units && map.opts.units === 'pixels') {
      start = [lineArr[0].lng, lineArr[0].lat]
      end = [lineArr[lineArr.length - 1].lng, lineArr[lineArr.length - 1].lat]
    } else {
      start = ol.proj.transform([lineArr[0].lng, lineArr[0].lat], 'EPSG:4326', 'EPSG:3857')
      end = ol.proj.transform([lineArr[lineArr.length - 1].lng, lineArr[lineArr.length - 1].lat], 'EPSG:4326', 'EPSG:3857')
    }
    const mapBegin = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAtCAYAAADcMyneAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyppVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTMyIDc5LjE1OTI4NCwgMjAxNi8wNC8xOS0xMzoxMzo0MCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUuNSAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo5NEY1ODdBQ0RFQzExMUVBOTY4M0JDNjQ3MTU2QjcwRSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo5NEY1ODdBRERFQzExMUVBOTY4M0JDNjQ3MTU2QjcwRSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjNFQjAwOTdGREVDMTExRUE5NjgzQkM2NDcxNTZCNzBFIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjNFQjAwOTgwREVDMTExRUE5NjgzQkM2NDcxNTZCNzBFIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+yDuyIQAADcJJREFU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'
    const mapFinish = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACgAAAAtCAYAAADcMyneAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyppVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTMyIDc5LjE1OTI4NCwgMjAxNi8wNC8xOS0xMzoxMzo0MCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUuNSAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo5NEY1ODdCMERFQzExMUVBOTY4M0JDNjQ3MTU2QjcwRSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo5NEY1ODdCMURFQzExMUVBOTY4M0JDNjQ3MTU2QjcwRSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjk0RjU4N0FFREVDMTExRUE5NjgzQkM2NDcxNTZCNzBFIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjk0RjU4N0FGREVDMTExRUE5NjgzQkM2NDcxNTZCNzBFIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+PHMpGgAADgZJREFU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'
    let startStyle = new ol.style.Style({
      image: new ol.style.Icon(opts.startPointStyle || {
        src: mapBegin,
        anchor: opts.anchor || [0.5, 0.75]
      })
    })
    let startFeature = new ol.Feature({
      geometry: new ol.geom.Point([start[0], start[1]])
    })
    startFeature.setStyle(startStyle)
    let endFeature = new ol.Feature({
      geometry: new ol.geom.Point([end[0], end[1]])
    })
    endFeature.setStyle(new ol.style.Style({
      image: new ol.style.Icon(opts.endPointStyle || {
        src: mapFinish,
        anchor: opts.anchor || [0.5, 0.75]
      })
    }))
    if (!hideStartEndPoint) {
      layer.getSource().addFeature(startFeature)
      layer.getSource().addFeature(endFeature)
    }
    // 将这条线显示到当前屏幕
    let ext = layer.getSource().getExtent()
    map.getView().fit(ext, map.getSize(), {
      constrainResolution: false,
      earest: false
    })
    return $.extend(this, layer)
  }

  openlayerMap.TrackPolyline.prototype = {
    _remove: function () {
      // let fFeatures = this.getSource().getFeatures();
      // fFeatures.setGeometry(null);
      this.setSource(null)
      this.stopAnimation()
    },
    _setPath: function (path) {
      let fFeatures = this.getSource().getFeatures()
      if (path) {
        fFeatures.setGeometry(new ol.geom.LineString(path))
      }
    },
    stopAnimation(ended) {
      this.animating = false
      var coord = ended ? this.routeCoords[this.routeCoords.length - 1] : this.routeCoords[0]
      var geometry = this.geoMarker.getGeometry()
      geometry.setCoordinates(coord)
      map.un('postcompose')
      this.un('postrender')
    },
    startAnimation(speed, callback) {
      speed = speed || 1
      this.totaltime = formatLength(this.route.getCoordinates()) / 100 / speed * 1000000// 假设速度为100m/s
      if (this.animating) {
        this.stopAnimation(false)
      } else {
        this.animating = true
        this.now = new Date().getTime()
        this.geoMarker.setStyle(null)
        map.on('postcompose', (event) => {
          let frameState = event.frameState
          if (this.animating) {
            let elapsedTime = frameState.time - this.now
            let currentPoint = this.route.getCoordinateAt(elapsedTime / this.totaltime)// 这里用到了LineString 对象中的getCoordinateAt的方法,来获取当前时间对应的点坐标
            let scale = parseFloat(elapsedTime) / parseFloat(this.totaltime)
            if (scale >= 1) {
              this.stopAnimation(true)
              callback(false)
              return
            } else {
              currentPoint = new ol.geom.Point(this.route.getCoordinateAt(scale))
              // let feature = new ol.Feature(currentPoint)
              // vectorContext.drawFeature(feature, this.aaastyles.geoMarker)
              this.geoMarker.getGeometry().setCoordinates(currentPoint.flatCoordinates)
            }
          }
          map.render()
        })
        map.render()
      }
    }
  }

  // 热力图
  openlayerMap.Heatmap = function (opts, map) {
    let opt = {
      url: '',
      blur: 10,
      radius: 10,
      field: 'alarm', // 热力分析字段
      gradient: ['#00f', '#0ff', '#0f0', '#ff0', '#f00']
    }
    opt = $.extend({}, opt, opts)
    let heatmapSource = {}
    if (!opt.url && opt.dataSource) { // 通过dataSource取值
      let features = []
      opt.dataSource.forEach((obj) => {
        let flatPt
        if (map.opts.units && map.opts.units === 'pixels') {
          flatPt = [obj.lng, obj.lat]
        } else {
          flatPt = ol.proj.transform([obj.lng, obj.lat], 'EPSG:4326', 'EPSG:3857')
        }
        let fea = {
          'geometry':{
            'alarm':obj.count,
            'coordinates':[flatPt[0], flatPt[1]],
            'type':'Point'
          },
          'type':'Feature'
        }
        features.push(fea)
      })
      let geojsonObject = {
        'features': features,
        'type':'FeatureCollection'
      }
      let heatmapFeaturs = (new ol.format.GeoJSON()).readFeatures(JSON.stringify(geojsonObject))
      heatmapSource = new ol.source.Vector({
        features: heatmapFeaturs
      })
    } else { // 通过url取值
      heatmapSource = new ol.source.Vector({
        url: opt.url, // 数据源为json数据
        format: new ol.format.GeoJSON()
      })
    }
    let vector = new ol.layer.Heatmap({
      source: heatmapSource,
      blur: opt.blur,
      radius: opt.radius,
      gradient: opt.gradient
    })
    vector.getSource().on('addfeature', function (event) {
      let magnitude = event.feature.get(opt.field)
      event.feature.set('weight', magnitude)
    })
    map.addLayer(vector)
    return ($.extend(vector, this))
  }
  openlayerMap.Heatmap.prototype = {
    toggleShow: function () {
      this.setVisible(true)
    },
    toggleHide: function () {
      this.setVisible(false)
    }
  }

  // 地图上加面 ol.layer.Vector 没有设置透明度
  // openlayerMap.Polygon = function (map, opt) {
  //   let defOpts = {
  //     fill: new ol.style.Fill({
  //       color: 'rgba(0,30,255,0.2)'
  //     }),
  //     stroke: new ol.style.Stroke({
  //       color: 'rgba(0,30,255,0.2)',
  //       width: 2
  //     })
  //   }
  //
  //   // 多边形此处注意一定要是[坐标数组]
  //   let polygon = new ol.geom.Polygon([opt.path])
  //   polygon.applyTransform(ol.proj.getTransform('EPSG:4326', 'EPSG:3857'))
  //   // 多边形要素类
  //
  //   let feature = new ol.Feature({
  //     geometry: polygon
  //   })
  //   feature.setStyle(new ol.style.Style(defOpts))
  //   let source = new ol.source.Vector()
  //   source.addFeature(feature)
  //
  //   let vector = new ol.layer.Vector({
  //     source: source
  //   })
  //   // this.map = map;
  //   // if (map.protoSelf) {
  //   //   map.removeLayer(map.protoSelf);
  //   // }
  //   // map.protoSelf = vector;
  //   this.protoSelf = vector
  //   map.addLayer(vector)
  //   // 如果配置了区域，点击树的时候，将地图所放置区域范围
  //   let ext = vector.getSource().getExtent()
  //   map.getView().fit(ext, map.getSize(), {
  //     constrainResolution: false,
  //     earest: false
  //   })
  //   return $.extend(vector, this)
  // }

  openlayerMap.Polygon = function (map, opt) {
    var defOpts = {
      path: [],
      strokeColor: '#0010ff', // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: 'solid', // 线样式
      fillColor: '#0010ff',
      fillOpacity: 0.3,
      isFresh: false
    }
    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }
    // 多边形此处注意一定要是[坐标数组]
    let oPath = []
    for (let i = 0; i < defOpts.path.length; i++) {
      if (map.opts.units && map.opts.units === 'pixels') {
        if (defOpts.path[i].lng || defOpts.path[i].lng == 0) oPath.push([defOpts.path[i].lng, defOpts.path[i].lat])
        if (defOpts.path[i][0] || defOpts.path[i][0] == 0) oPath.push([defOpts.path[i][0], defOpts.path[i][1]])
      } else {
        defOpts.path[i].lng && defOpts.path[i].lat && oPath.push([defOpts.path[i].lng, defOpts.path[i].lat])
        defOpts.path[i][0] && defOpts.path[i][1] && oPath.push([defOpts.path[i][0], defOpts.path[i][1]])
      }
    }
    defOpts.path = oPath
    defOpts.fill = new ol.style.Fill({
      color: hexToRgba(defOpts.fillColor, defOpts.fillOpacity)
    })
    defOpts.stroke = new ol.style.Stroke({
      color: defOpts.strokeColor,
      width: defOpts.strokeWeight,
      lineDash: defOpts.strokeDasharray ? [10, 10] : []
    })
    if (opt.isShowLabel && opt.label) {
      defOpts.text = new ol.style.Text({
        text: opt.label,
        font: '12px Calibri, 黑体',
        fill: new ol.style.Fill({
          color: 'black'
        })
        // stroke: new ol.style.Stroke({color: outlineColor, width: outlineWidth})
      })
    }
    let polygon = new ol.geom.Polygon([defOpts.path])
    if (!map.opts.units || map.opts.units !== 'pixels') {
      polygon.applyTransform(ol.proj.getTransform('EPSG:4326', 'EPSG:3857'))
    }
    var feature = new ol.Feature({
      geometry: polygon
    })
    feature.setStyle(new ol.style.Style(defOpts))
    var source = new ol.source.Vector()
    source.addFeature(feature)
    let layer = new ol.layer.Vector({
      source: source,
      zIndex: 0
    })
    if (map) {
      map.addLayer(layer)
      layer.map = map
    }
    layer.polygon = polygon
    this.feature = feature
    this.opts = defOpts
    this.extData = defOpts.extData
    // map.addLayer(layer)
    // 如果配置了区域，点击树的时候，将地图所放置区域范围
    if (defOpts.isFresh) {
      let ext = layer.getSource().getExtent()
      map.getView().fit(ext, map.getSize(), {
        constrainResolution: false,
        earest: false
      })
    }
    layer = $.extend(layer, this)
    return layer
  }

  openlayerMap.Polygon.prototype = {
    hide: function () {
      this.setVisible(false)
    },
    _hide: function () {
      this.setVisible(false)
    },
    show: function () {
      this.setVisible(true)
    },
    _add: function () {
      this.map.addLayer(this)
    },
    _show: function () {
      this.setVisible(true)
    },
    remove: function () {
      this.setSource(null)
    },
    _remove: function () {
      this.setSource(null)
    },
    contains: function (lng, lat) {
      this.getExtent().containsCoordinate([lng, lat])
    },
    _contains: function (lng, lat) {
      this.getExtent().containsCoordinate(ol.proj.transform([lng, lat], 'EPSG:4326', 'EPSG:3857'))
    },
    _addEventListener: function (event, handler) {
      let m = this
      let feature = this.feature
      feature.addEventListener(event, function () {
        handler(m)
      })
    },
    _setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts)
      let feature = this.feature
      feature.style_.setFill(new ol.style.Fill({
        color: hexToRgba(defOpts.fillColor, opts.fillOpacity)
      }))
      feature.style_.setStroke(new ol.style.Stroke({
        color: hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity),
        width: defOpts.strokeWeight
      }))
      feature.setStyle(feature.style_)
    },
    _getCenter: function () {
      let center = ol.extent.getCenter(this.polygon.getExtent())
      return this.map._transformPoint(center)
    },
    _fitView: function () {
      let ext = this.getSource().getExtent()
      map.getView().fit(ext, map.getSize(), {
        constrainResolution: false,
        earest: false
      })
    }
  }

  // 椭圆
  openlayerMap.Ellipse = function (map, center, opt) {
    let defOpts = {
      map: map,
      path: [],
      strokeColor: '#0010ff', // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: 'solid', // 线样式
      fillColor: '#0010ff',
      fillOpacity: 0.2
    }
    if (opt) {
      defOpts = $.extend({}, defOpts, opt)
    }
    // 构造椭圆
    var x = opt.x || 0.1
    var y = opt.y || 0.2
    let lnglatarr = []
    var angle
    var dot
    var tangent = x / y
    for (var i = 0; i < 36; i++) {
      angle = (2 * Math.PI / 36) * i
      dot = [center.lng + Math.sin(angle) * y * tangent, center.lat + Math.cos(angle) * y]
      lnglatarr.push(dot)
    }
    let ellipse = new ol.geom.Polygon([lnglatarr])
    if (!map.opts.units || map.opts.units !== 'pixels') {
      ellipse.applyTransform(ol.proj.getTransform('EPSG:4326', 'EPSG:3857'))
    }
    let layerStyle = new ol.style.Style({
      fill: new ol.style.Fill({
        color: hexToRgba(defOpts.fillColor, defOpts.fillOpacity)
      }),
      stroke: new ol.style.Stroke({
        color: defOpts.strokeColor,
        width: defOpts.strokeWeight,
        lineDash: defOpts.strokeDasharray ? [10, 10] : []
      })
    })
    var feature = new ol.Feature({
      geometry: ellipse
    })
    var source = new ol.source.Vector()
    source.addFeature(feature)
    let layer = new ol.layer.Vector({
      source: source,
      style: layerStyle
    })
    layer.ellipse = ellipse
    layer = $.extend(layer, this)
    // layer._add(map)
    return layer
  }
  openlayerMap.Ellipse.prototype = {
    hide: function () {
      this.setVisible(false)
    },
    _hide: function () {
      this.setVisible(false)
    },
    show: function () {
      this.setVisible(true)
    },
    _add: function (map) {
      map.addLayer(this)
    },
    _show: function () {
      this.setVisible(true)
    },
    remove: function () {
      this.setSource(null)
    },
    _remove: function () {
      this.setSource(null)
    },
    _contains: function (pt) {
      let extent = this.ellipse.getExtent()
      let point = ol.proj.transform([pt.lng, pt.lat], 'EPSG:4326', 'EPSG:3857')
      let isContain = ol.extent.containsXY(extent, point[0], point[1])
      return isContain
    }
  }

  // 矩形
  openlayerMap.Rectangle = function (map, opt) {
    var defOpts = {
      // path: [],
      strokeColor: '#0010ff', // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: 'solid', // 线样式
      fillColor: '#0010ff',
      fillOpacity: 0.3
    }
    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }
    // 多边形此处注意一定要是[坐标数组]
    // let oPath = [];
    let bounds = opt.bounds
    let oPath = [
      [bounds.southwest[0], bounds.northeast[1]],
      [bounds.southwest[0], bounds.southwest[1]],
      [bounds.northeast[0], bounds.southwest[1]],
      [bounds.northeast[0], bounds.northeast[1]]
    ]
    // for (let i = 0; i < defOpts.path.length; i++) {
    //   oPath.push([defOpts.path[i].lng, defOpts.path[i].lat])
    // }
    defOpts.path = oPath

    let layerStyle = new ol.style.Style({
      fill: new ol.style.Fill({
        color: hexToRgba(defOpts.fillColor, defOpts.fillOpacity)
      }),
      stroke: new ol.style.Stroke({
        color: defOpts.strokeColor,
        width: defOpts.strokeWeight,
        lineDash: defOpts.strokeDasharray ? [10, 10] : []
      })
    })
    let polygon = new ol.geom.Polygon([defOpts.path])
    if (!map.opts.units || map.opts.units !== 'pixels') {
      polygon.applyTransform(ol.proj.getTransform('EPSG:4326', 'EPSG:3857'))
    }
    var feature = new ol.Feature({
      geometry: polygon
    })
    var source = new ol.source.Vector()
    source.addFeature(feature)
    let layer = new ol.layer.Vector({
      source: source,
      style: layerStyle
    })
    layer.polygon = polygon
    layer = $.extend(layer, this)
    // layer._add(map)
    return layer
  }
  openlayerMap.Rectangle.prototype = {
    hide: function () {
      this.setVisible(false)
    },
    _hide: function () {
      this.setVisible(false)
    },
    show: function () {
      this.setVisible(true)
    },
    _add: function (map) {
      map.addLayer(this)
    },
    _show: function () {
      this.setVisible(true)
    },
    remove: function () {
      this.setSource(null)
    },
    _remove: function () {
      this.setSource(null)
    },
    contains: function (lng, lat) {
      this.getExtent().containsCoordinate([lng, lat])
    },
    _contains: function (lng, lat) {
      this.getExtent().containsCoordinate(ol.proj.transform([lng, lat], 'EPSG:4326', 'EPSG:3857'))
    }
  }

  openlayerMap.InfoWindow = function (content, opts) {
    // 先清除pop弹出要素
    popclose('')
    let info = document.createElement('div')
    // info.classList.add('ol-sanjiao')
    // info.style.position = 'relative'
    // info.style.zIndex = 1000
    $(info).addClass('ol-sanjiao').css({
      position: 'relative',
      'z-index': 1000
    })
    // info.appendChild(content)
    $(info).append(content)
    let pos = new openlayerMap.Point(opts.lnglat.lng, opts.lnglat.lat) // 经纬度坐标需要坐标转换
    if (opts.lnglat.lng > 1000) { // 非经纬度坐标
      pos = new ol.geom.Point([opts.lnglat.lng, opts.lnglat.lat])
    }

    let infowindow = new ol.Overlay({
      id: 'pop0',
      element: info,
      autoPan: true,
      stopEvent: false,
      autoPanAnimation: {
        duration: 0
      }
    })
    return $.extend(infowindow, this, { pos: pos.getFirstCoordinate() })
  }

  function popclose (map) {
    let element = document.getElementsByClassName('ol-sanjiao')
    // element.remove()
    $(element).remove()
  }

  openlayerMap.InfoWindow.prototype = {
    _open: function (map) {
      map.addOverlay(this)
      this.setPosition(this.pos)
      let el = this.getElement()
      el.parentNode.style.zIndex = 1000
    },
    _close: function () {
      // let map = this.getMap();
      // map.removeOverlay(this);
      this.setMap(null)
    },
    _stopPropagation: function () {

    }
  }

  openlayerMap.MapLib = {}

  openlayerMap.MapLib.MouseTool = function (map) {
    this.map = map
  }

  openlayerMap.MapLib.MouseTool.prototype = {
    toCommonGeo: function (evt) { // 转换为统一几何图形
      let geome = evt.feature.getGeometry()
      geome.contains = function (position) {
        return this.intersectsCoordinate(position.flatCoordinates || position.B || position)
      }
      let path = geome.flatCoordinates || geome.B
      let znvCoordinates = []
      for (let i = 0; i < path.length; i = i + 2) {
        let item = this.map._transformPoint([path[i], path[i + 1]])
        znvCoordinates.push(item)
      }
      geome.znvCoordinates = znvCoordinates
      return geome
    },
    // 测距
    _measureDistance: function (opts) {
      let map = this.map
      opts = opts || {}
      // map.removeLayer(drawVector)
      // 定义矢量数据源
      let source = new ol.source.Vector()
      // 定义矢量图层
      let dVector = new ol.layer.Vector({
        source: source,
        style: new ol.style.Style({
          fill: new ol.style.Fill({
            color: opts.fillColor || 'rgba(255,255,255,0.2)'
          }),
          stroke: new ol.style.Stroke({
            color: opts.strokeColor || '#1178cb',
            width: 2
          }),
          image: new ol.style.Circle({
            radius: 5,
            fill: new ol.style.Fill({
              color: opts.fillColor || 'rgba(17,120,203,0.5)'
            })
          })
        })
      })
      drawVector.push(dVector)
      map.addLayer(dVector)
      Measure(map, source, 'LineString', opts)
    },
    // 测面
    _measureArea: function (opts) {
      opts = opts || {}
      // 定义矢量数据源
      let source = new ol.source.Vector()
      // 定义矢量图层
      let dVector = new ol.layer.Vector({
        source: source,
        style: new ol.style.Style({
          fill: new ol.style.Fill({
            color: opts.fillColor || 'rgba(17,120,203,0.3)'
          }),
          stroke: new ol.style.Stroke({
            color: opts.strokeColor || '#1178cb',
            width: opts.strokeWeight || 2
          })
        })
      })
      // 将矢量图层添加到地图中
      map.addLayer(dVector)
      drawVector.push(dVector)
      Measure(map, source, 'Polygon', opts)
    },
    _clearDrawOverlays: function () {
      drawVector.forEach((item) => {
        map.removeLayer(item)
      })
      let doms = document.querySelectorAll('.ol-tooltip')
      doms.forEach((dom) => {
        dom.parentElement.removeChild(dom)
      })
      if (this.pointMarker) {
        this.pointMarker._remove()
        this.pointMarker = false
      }
    },
    // 绘制圈
    _drawCircle: function (cb) {
      let map = this.map
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      drawVector.push(dVector)
      map.addLayer(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Circle',
        style: style,
        stopClick: true,
        freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', (evt) => {
        let geo = this.toCommonGeo(evt)
        cb(geo)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 多边形
    _drawPolygon: function (cb) {
      let map = this.map
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      drawVector.push(dVector)
      map.addLayer(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Polygon',
        style: style
        // stopClick: true,
        // freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', (evt) => {
        let geo = this.toCommonGeo(evt)
        cb(geo)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 点
    _drawPoint: function (cb) {
      let map = this.map
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      drawVector.push(dVector)
      map._addOverlay(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Point',
        style: style
        // stopClick: true,
        // freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', (evt) => {
        let geo = this.toCommonGeo(evt)
        cb(geo)
        map.removeInteraction(me.mouseTool)
        // dVector.setSource(null)
      })
    },
    // 线
    _drawLine: function (cb,opt) {
      let map = this.map
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: (opt && opt.strokeColor) ? opt.strokeColor : 'rgba(0,139,202,1)',
          width: (opt && opt.strokeWeight) ? opt.strokeWeight : 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      drawVector.push(dVector)
      map.addLayer(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'LineString',
        style: style
        // stopClick: true,
        // freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', (evt) => {
        /* let gm = evt.feature.getGeometry()
         let path = gm.getCoordinates()
         let lines = []
         for (let i = 0; i < path.length; i++) {
           let item = path[i]
           item = ol.proj.transform([item[0], item[1]], 'EPSG:3857', 'EPSG:4326')
           lines.push(item)
         }
         gm.linePoints = lines */
        let geo = this.toCommonGeo(evt)
        cb(geo)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 绘制矩形框
    _drawRectangle: function (cb) {
      let map = this.map
      let geometryFunction = ol.interaction.Draw.createBox()
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      map.addLayer(dVector)
      drawVector.push(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Circle',
        style: style,
        geometryFunction: geometryFunction,
        stopClick: true,
        freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', (evt) => {
        let geo = this.toCommonGeo(evt)
        cb(geo)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 圈选要素
    _selectCircle: function (markers, cb, opts) {
      let map = this.map
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      map.addLayer(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Circle',
        style: style,
        stopClick: true,
        freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', function (evt) {
        let resultMarkers = []
        let extent = evt.feature.getGeometry().getExtent()
        for (let i = 0; i < markers.length; i++) {
          let pt = markers[i]._getPosition()
          let flatPt
          if (map.opts.units && map.opts.units === 'pixels') {
            flatPt = [pt.lng, pt.lat]
          } else {
            flatPt = ol.proj.transform([pt.lng, pt.lat], 'EPSG:4326', 'EPSG:3857')
          }
          if (ol.extent.containsXY(extent, flatPt[0], flatPt[1])) {
            resultMarkers.push(markers[i])
          }
        }
        cb(resultMarkers)
        map.removeLayer(dVector)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 框选
    _selectRectangle: function (markers, cb, opts) {
      let map = this.map
      let geometryFunction = ol.interaction.Draw.createBox()
      let me = this
      let style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      let source = new ol.source.Vector({ wrapX: false })
      let dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      map.addLayer(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Circle',
        style: style,
        geometryFunction: geometryFunction,
        stopClick: true,
        freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', function (evt) {
        // cb(evt.feature.getGeometry(), evt.target.a[1])
        let resultMarkers = []
        let extent = evt.feature.getGeometry().getExtent()
        for (let i = 0; i < markers.length; i++) {
          let pt = markers[i]._getPosition()
          let flatPt
          if (map.opts.units && map.opts.units === 'pixels') {
            flatPt = [pt.lng, pt.lat]
          } else {
            flatPt = ol.proj.transform([pt.lng, pt.lat], 'EPSG:4326', 'EPSG:3857')
          }
          if (ol.extent.containsXY(extent, flatPt[0], flatPt[1])) {
            resultMarkers.push(markers[i])
          }
        }
        cb(resultMarkers)
        map.removeLayer(dVector)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 多边形选
    _selectPolygon: function(markers, cb, opts) {
      // console.log('_selectPolygon方法没实现')
      const map = this.map
      const me = this
      const style = new ol.style.Style({
        fill: new ol.style.Fill({
          color: 'rgba(0,174,255,0.2)'
        }),
        stroke: new ol.style.Stroke({
          color: 'rgba(0,139,202,1)',
          width: 2
        }),
        image: new ol.style.Circle({
          radius: 5,
          fill: new ol.style.Fill({
            color: 'rgba(0,139,202,.8)'
          })
        })
      })
      const source = new ol.source.Vector({ wrapX: false })
      const dVector = new ol.layer.Vector({
        source: source,
        style: style
      })
      map.addLayer(dVector)
      me.mouseTool = new ol.interaction.Draw({
        source: source,
        type: 'Polygon',
        style: style
        // stopClick: true,
        // freehand: true
      })
      map.addInteraction(me.mouseTool)
      me.mouseTool.on('drawend', function(evt) {
        const resultMarkers = []
        const extent = evt.feature.getGeometry().getExtent()
        for (let i = 0; i < markers.length; i++) {
          const pt = markers[i]._getPosition()
          let flatPt
          if (map.opts.units && map.opts.units === 'pixels') {
            flatPt = [pt.lng, pt.lat]
          } else {
            flatPt = ol.proj.transform([pt.lng, pt.lat], 'EPSG:4326', 'EPSG:3857')
          }
          if (ol.extent.containsXY(extent, flatPt[0], flatPt[1])) {
            resultMarkers.push(markers[i])
          }
        }
        cb(resultMarkers)
        map.removeLayer(dVector)
        map.removeInteraction(me.mouseTool)
      })
    },
    // 关闭工具
    _closeTool: function () {
      this.map.removeInteraction(this.mouseTool)
    }
  }

  // 地理编码与逆地理编码类，用于地址描述与坐标之间的转换
  openlayerMap.MapLib.Geocoder = function (map, opts) {
    opts = opts || {}
  }
  openlayerMap.MapLib.Geocoder.prototype = {
    _getLocation: function (address, callback) {
    }
  }

  openlayerMap.Overlay = function (overlay) {
    this._obj = overlay
  }

  openlayerMap.Overlay.prototype = {
    _containPoint: function (point) {
      let overlay = this._obj
      if (overlay.contains) {
        return overlay.contains(point)
      } else {
        return false
      }
    }
  }

  /*  function getClusters (markers, opts) {
      let features = new Array(markers.length)
      for (let i = 0; i < markers.length; ++i) {
        let geo = new ol.geom.Point([markers[i]._getPosition().lng, markers[i]._getPosition().lat]); // 平面坐标直接使用
        if (markers[i]._getPosition().lng < 200) { // 经纬度坐标需要转换到平面坐标
          geo = new ol.geom.Point(ol.proj.transform([markers[i]._getPosition().lng, markers[i]._getPosition().lat]), 'EPSG:4326', 'EPSG:3857')
        }
        features[i] = new ol.Feature({
          geometry: geo
        })
        features[i].setStyle(new ol.style.Style({
          image: new ol.style.Icon({
            src: markers[i].iconSrc
          })
        }));
      }
      let source = new ol.source.Vector({
        features: features
      })

      let clusterSource = new ol.source.Cluster({
        distance: opts.gridSize || 100,
        source: source
      })
      let styleCache = {}
      let clusters = new ol.layer.Vector({
        source: clusterSource,
        style: function(feature) { // 聚合样式
          var size = feature.get('features').length
          var style = styleCache[size];
          let clusterImgSrc = '../../map/images/m1.png'
          if (size == 1) {
            clusterImgSrc = '' // 显示marker自身的图标
            return feature.values_.features[0].style_;
          } else if (size < 10) {
            clusterImgSrc = '../../map/images/m1.png'
          } else if (size < 50) {
            clusterImgSrc = '../../map/images/m2.png'
          } else if (size < 100) {
            clusterImgSrc = '../../map/images/m3.png'
          } else {
            clusterImgSrc = '../../map/images/m4.png'
          }
          if (!style) {
            style = new ol.style.Style({
              image: clusterImgSrc ? new ol.style.Icon({
                        src: clusterImgSrc
                      }) : '',
              text: size > 1 ? new ol.style.Text({
                text: size.toString(),
                fill: new ol.style.Fill({
                  color: '#333'
                })
              }) : ''
            })
            styleCache[size] = style;
          }
          return style;
        }
        // style: function (feature) {
        //   let size = feature.get('features').length
        //   let style = styleCache[size]
        //   if (map._getZoom() > 17) {
        //     feature.get('features').forEach((v) => {
        //       markersLayer[v.getProperties().id]._setMap(map)
        //     })
        //     return false
        //   }
        //   if (size === 1) {
        //     markersLayer[feature.get('features')[0].getProperties().id]._setMap(map)
        //   } else {
        //     feature.get('features').forEach((v) => {
        //       markersLayer[v.getProperties().id]._setMap(null)
        //     })
        //   }
        //   if (!style && size !== 1) {
        //     let src = size <= 10 ? require('./images/m1.png')
        //       : size <= 100 ? require('./images/m2.png')
        //         : size <= 1000 ? require('./images/m3.png')
        //           : size <= 10000 ? require('./images/m4.png') : require('./images/m5.png')
        //     style = new ol.style.Style({
        //       image: new ol.style.Icon({
        //         src: src
        //       }),
        //       text: new ol.style.Text({
        //         text: size === 1 ? '' : size.toString(),
        //         fill: new ol.style.Fill({
        //           color: '#000'
        //         })
        //       })
        //     })
        //     styleCache[size] = style
        //   }
        //   return style
        // }
      })
      clusters.clusterSource = clusterSource
      clusters.opts = opts
      return clusters
    } */

  /*
   *聚合图层
   */
  openlayerMap.MapLib.MarkerClusterer = function (map, markers, opts) {
    this.map = map
    markers = markers || []
    opts = opts || {}
    let clusters = getClusters(markers, opts)
    map._addOverlay(clusters)
    clusters.markers = markers
    clusters.visible = true
    return $.extend(clusters, this)
  }
  openlayerMap.MapLib.MarkerClusterer.prototype = {
    _addMarker: function (marker) {
      this.clusterSource.getSource().addFeature(marker)
    },
    _removeMarker: function (marker) {
      this.clusterSource.getSource().removeFeature(marker)
    },
    _clearMarkers: function () {
      this.clusterSource.getSource().clear() // 移除聚合标注数据源中的所有要素
    },
    _complete: function (callback) {
      callback.call(this)
    },
    _addMarkers: function (markers) {
      if (markers.length > 0) {
        let features = new Array(markers.length)
        for (let i = 0; i < markers.length; ++i) {
          let point
          if (map.opts.units && map.opts.units === 'pixels') {
            point = [parseFloat(markers[i].extData.lnglat[0]), parseFloat(markers[i].extData.lnglat[1])]
          } else {
            point = ol.proj.transform([parseFloat(markers[i].extData.lnglat[0]), parseFloat(markers[i].extData.lnglat[1])], 'EPSG:4326', 'EPSG:3857')
          }
          features[i] = new ol.Feature({
            geometry: new ol.geom.Point(point),
            icon: markers[i].extData.icon,
            size: markers[i].extData.size,
            id: markers[i].extData.id
          })
        }
        this.clusterSource.getSource().addFeatures(features)
        this.setSource(this.clusterSource)
      }
    },
    _removeMarkers: function (removeMarkers) {
      for (let i = 0; i < removeMarkers.length; i++) {
        let currentFeatures = this.clusterSource.getSource().getFeatures()
        for (let j = 0; j < currentFeatures.length; j++) {
          if (currentFeatures[j].getProperties().id === removeMarkers[i].extData.id) {
            this.clusterSource.getSource().removeFeature(currentFeatures[j])
            this.setSource(this.clusterSource)
          }
        }
      }
    },
    _hide() {
      this.visible = false
      map._removeOverlay(this)
    },
    _show() {
      this.visible = true
      map._addOverlay(this)
    }
  }
  // 工具类
  openlayerMap.gmUtil = {
    distance: function (p0, p1) {
      let lat1 = p0[1]
      let lon1 = p0[0]
      let lat2 = p1[1]
      let lon2 = p1[0]
      let deg2rad = (deg) => deg * (Math.PI / 180)
      let R = 6371000 // Radius of the earth in km
      let dLat = deg2rad(lat2 - lat1) // deg2rad below
      let dLon = deg2rad(lon2 - lon1)
      let a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)
      let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
      return R * c
    },
    distanceToLine: function (p0, p1s) {
      let mdistance = -1
      let gm = openlayerMap.gmUtil
      for (let i = 0; i < p1s.length - 1; i++) {
        let p1 = p1s[i]
        let p2 = p1s[i + 1]
        let d1 = gm.distance(p1, p2)
        let d2 = gm.distance(p0, p2)
        let d3 = gm.distance(p0, p1)
        let d12 = Math.pow(d1, 2)
        let d22 = Math.pow(d2, 2)
        let d32 = Math.pow(d3, 2)
        if (d2 == 0 || d3 == 0) {
          mdistance = 0
          break
        }
        let p = (d1 + d2 + d3) / 2
        let s = Math.sqrt(p * (p - d1) * (p - d2) * (p - d3))
        let h = 2 * s / d1
        if ((d12 + d22) < d32) {
          h = d2
        } else if ((d12 + d32) < d22) {
          h = d3
        }
        if (mdistance > h || mdistance == -1) {
          mdistance = h
        }
      }
      return mdistance
    }
  }
// 地图控件--缩放
  openlayerMap.Controls = {}
  openlayerMap.Controls.ToolBar = function (map, opts) {
    this.map = map
    var zoomSlider = new ol.control.ZoomSlider()
    return zoomSlider
  }
  openlayerMap.Controls.ToolBar.prototype = {
    hide() {
      // this.hide()
    },
    show() {
      // this.show()
    }
  }
  // 地图控件---比例尺
  openlayerMap.Controls.Scale = function (map, opts) {
    var tool = new ol.control.ScaleLine()
    return tool
  }
  openlayerMap.Controls.Scale.prototype = {
    hide() {
      // this.hide()
    },
    show() {
      // this.show()
    }
  }
  // 地图控件---鹰眼
  openlayerMap.Controls.OverView = function (map, opts) {
    var tool = new ol.control.OverviewMap(
      {
        collapseLabel: '\u00BB', // 鹰眼展开时功能按钮上的标识
        label: '\u00AB', // 鹰眼折叠时功能按钮上的标识
        collapsed: false// 初始为展开显示方式
      }
    )
    return tool
  }
  openlayerMap.Controls.OverView.prototype = {
    hide() {
      // this.hide()
    },
    show() {
      // this.show()
    }
  }
  // 基础控件
  openlayerMap.Controls.Zoom = function (map, opts) {
    let zoomBtns = document.querySelectorAll('.ol-viewport button.ol-zoom')
    if (zoomBtns.length > 0) {
      zoomBtns.forEach((zoomBtn) => {
        zoomBtn.parentNode.remove()
      })
    }
    let defOpts = {
      zoomInTipLabel: '放大',
      zoomOutTipLabel: '缩小'
    }
    if (opts && opts.position) {
      let position = opts.position.toLowerCase()
      defOpts.className = 'ol-zoom ol-zoom-' + position
    } else {
      defOpts.className = 'ol-zoom'
    }
    if (opts && opts.theme) {
      defOpts.className = opts.theme + ' ' + defOpts.className
    }
    this.zoom = new ol.control.Zoom(defOpts)
    map._addControl(this.zoom)
  }
  openlayerMap.Controls.Zoom.prototype = {
    _add: function(map) {
      map._addControl(this.zoom)
    },
    _remove: function (map) {
      map._removeControl(this.zoom)
    },
    _zoomIn: function () {
      this.zoom && this.zoom.zoomByDelta_(1)
    },
    _zoomOut: function () {
      this.zoom && this.zoom.zoomByDelta_(-1)
    }
  }
  openlayerMap.Controls.LayerSwitcher = function (map, opts) {
    let toolBarOptions = {
      position: 'rt'
    }
    $.extend(toolBarOptions, opts)
  }
  openlayerMap.Controls.LayerSwitcher.prototype = {
    _add(map) {
      map.addControl(this.layerSwitcher)
    },
    _remove: function (map) {
      map.removeControl(this.layerSwitcher)
    },
    _showLayersPanel: function () {
      this.layerSwitcher.showLayersPanel()
    },
    _hideLayersPanel: function () {
      this.layerSwitcher.hideLayersPanel()
    },
    _addEventListener: function (event, handle) {
      let interval = setInterval(() => {
        if (this.layerSwitcher) {
          this.layerSwitcher.on(event, handle)
          clearInterval(interval)
          interval = null
        }
      }, 100)
    }
  }
  openlayerMap.Controls.Traffic = function (map, opts) {
    let toolBarOptions = {
      position: 'lb'
    }
    $.extend(toolBarOptions, opts)
  }
  openlayerMap.Controls.Traffic.prototype = {
    _add(map) {
      map._addControl(this.traffic)
    },
    _remove: function (map) {
      map._removeControl(this.traffic)
    },
    _open: function () {
      this.traffic.open()
    },
    _close: function () {
      this.traffic.close()
    },
    _showButton: function () {
      this.traffic.showButton()
    },
    _hideButton: function () {
      this.traffic.hideButton()
    }
  }
  // 全屏显示
  openlayerMap.Controls.FullScreen = function (map, opts) {
    this.map = map
    this.fullScreen = new ol.control.FullScreen()
    map._addControl(this.fullScreen)
  }
  openlayerMap.Controls.FullScreen.prototype = {
    hide() {
      map._removeControl(this.fullScreen)
    },
    show() {
      map._addControl(this.fullScreen)
    }
  }
  /*
   *此功能待完善----------
   */
  openlayerMap.GeometryTool = function (map) {
    this.map = map
    return this
  }
  openlayerMap.GeometryTool.prototype = {
    _untion: function (polygon1, polygon2) {
      let extent1 = polygon1.polygon.getExtent()
      let extent2 = polygon2.polygon.getExtent()
      ol.extent.extend(extent1, extent2)
      let pgon = ol.geom.Polygon.fromExtent(extent1)
      if (!map.opts.units || map.opts.units !== 'pixels') {
        pgon.applyTransform(ol.proj.getTransform('EPSG:3857', 'EPSG:4326'))
      }
      let arry = pgon.getCoordinates()
      let result = []
      arry[0].forEach((item) => {
        let it = { lng: item[0], lat: item[1] }
        result.push(it)
      })
      return result
    }
  }

  // 坐标转换
  openlayerMap.CoordinateConvert = function () {
    return this
  }
  openlayerMap.CoordinateConvert.prototype = {
    // pointArray 数组{lng:lng1,lat:lat1}
    // before：需要转换的坐标类型
    // after: 转换后的坐标类型
    _translate: function (pointArray, before, after) {
      if (this) {
        let result = []
        for (let i = 0; i < pointArray.length; i++) {
          let item = pointArray[i]
          // item = ol.proj.transform([item.lng, item.lat], 'EPSG:3857', 'EPSG:4326');
          if (map.opts.units && map.opts.units === 'pixels') {
            item = [item.lng, item.lat]
          } else {
            item = ol.proj.transform([item.lng, item.lat], before, after)
          }
          result.push(item)
        }
        return result
      }
    }
  }

  //  创建feature类型的Marker
  openlayerMap.FeatureMarker = function (map, opts) {
    this.id = opts.id
    this.layerName = opts.layerName
    this.type = opts.type
    this.map = map
    this.extData = opts.extData
    this.icon = opts.icon || 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
    this.position = opts.position
    let point
    if (map.opts.units && map.opts.units === 'pixels') {
      point = [opts.position.lng, opts.position.lat]
    } else {
      point = ol.proj.transform([opts.position.lng, opts.position.lat], 'EPSG:4326', 'EPSG:3857')
    }
    let geo = new ol.geom.Point(point)
    let fea = new ol.Feature({
      geometry: geo,
      name: opts.title || '',
      zIndex: 999
    })
    // 矢量标注样式设置函数，设置image为图标ol.style.Icon
    function createLabelStyle(feature) {
      return new ol.style.Style({
        image: new ol.style.Icon({
          src: opts.icon || 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
        }),
        zIndex: 999
      })
    }
    fea.setStyle(createLabelStyle(fea))
    fea.setId(opts.id)
    fea = $.extend(fea, this)
    return fea
  }
  openlayerMap.FeatureMarker.prototype = {
    // 加入数据源，将在地图显示
    _add: function () {
      clusterLayer.getSource().addFeature(this)
      clusterLayer.getSource().refresh()
    },
    _setMap: function (map) {
      if (map) {
        clusterLayer.getSource().addFeature(this)
        clusterLayer.getSource().refresh()
      } else {
        // 从地图移除
        clusterLayer.getSource().removeFeature(this)
        clusterLayer.getSource().refresh()
      }
    },
    // 从数据源移除，将从地图删除
    _remove: function () {
      clusterLayer.getSource().removeFeature(this)
      clusterLayer.getSource().refresh()
    },
    _getPosition: function () {
      if (this.getGeometry().getType() == 'Point') {
        let ptFlat = this.getGeometry().getFirstCoordinate()
        return flatToGeo(ptFlat[0], ptFlat[1])
      }
    },
    _setPosition: function (lnglan) {
      // let runPos = new openlayerMap.Point(lnglan.lng, lnglan.lat)
      let runPos
      if (map.opts.units && map.opts.units === 'pixels') {
        runPos = lnglan.lng ? [lnglan.lng, lnglan.lat] : [lnglan[0], lnglan[1]]
      } else {
        runPos = lnglan.lng ? ol.proj.transform([lnglan.lng, lnglan.lat], 'EPSG:4326', 'EPSG:3857')
          : ol.proj.transform([lnglan[0], lnglan[1]], 'EPSG:4326', 'EPSG:3857')
      }
      if (this.getGeometry().getType() == 'Point') {
        this.getGeometry().setCoordinates(runPos)
      }
    },
    _setIcon: function (url) {
      if (url) {
        this.icon = url
      }
      let style = this.getStyle()
      let newImage = new ol.style.Icon({
        src: url || 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
      })
      style.setImage(newImage)
      this.setStyle(style)
    },
    _setIconSize: function (width, height) {
      console.log('功能未实现')
    },
    _getIcon: function () {
      return this.icon
    },
    _hide: function () {
      // this.setStyle(null)
      this.setGeometry(null)
    },
    _show: function () {
      let pt
      if (map.opts.units && map.opts.units === 'pixels') {
        pt = [this.position.lng, this.position.lat]
      } else {
        pt = ol.proj.transform([this.position.lng, this.position.lat], 'EPSG:4326', 'EPSG:3857')
      }
      let geo = new ol.geom.Point(pt)
      this.setGeometry(geo)
      // this.setStyle(new ol.style.Style({
      //   image: new ol.style.Icon({
      //     src: this.icon || "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png"
      //   })
      // }));
    },
    _getMap: function () {
      return this.map
    },
    // _addEventListener: function (event, handler) {
    //   if (event === 'click') {
    //     event = 'select'
    //     selectSingleClick.on(event, function(e) { // select交互类方面鼠标选择
    //       if (e.selected.length > 0) {
    //         handler(e.selected[0], e.target);
    //       }
    //     });
    //   } else if (event.indexOf('contextmenu') !== -1) {
    //     this.on(event, function (e) {
    //       // e.preventDefault();
    //       handler(e.coordinate)
    //     })
    //   } else {
    //     this.on(event, function (e) {
    //       handler(e)
    //     })
    //   }
    // },
    _addEventListener: function(event, handler) {
      this.addEventListener(event, e => {
        handler(this)
      })
    },
    _setLabel: function (label) {
      function createLabelStyle(feature) {
        return new ol.style.Style({
          image: new ol.style.Icon({
            src: feature.icon || 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
          }),
          text: new ol.style.Text({
            textAlign: 'center', // 位置
            textBaseline: 'middle', // 基准线
            font: 'normal 14px 微软雅黑', // 文字样式
            text: label.content, // feature.get('name'), // 文本内容
            // fill: new ol.style.Fill({ // 文本填充样式（即文字颜色)
            //   color: '#000'
            // }),
            stroke: new ol.style.Stroke({
              color: '#0033CC',
              width: 1
            }),
            offsetY: 25
          })
        })
      }
      this.setStyle(createLabelStyle(this))
    }
  }

  openlayerMap.Marker = function (map, opts, isOverlay) {
    if (!isOverlay) {
      // feature
      return new openlayerMap.FeatureMarker(map, opts)
    } else {
      // overlay
      return new openlayerMap.OverlayMarker(map, opts)
    }
  }

  /*
 * 扇形
 * angle:圆心角
 * center：旋转角，从平面直角坐标系x轴正轴方向逆时针旋转的角度
 * origin：圆心
 * radius：半径，单位米
 */
  openlayerMap.Sector = function (map, opts) {
    let defOpts = {
      radius: 100, // 半径
      strokeColor: '#FF33FF',
      strokeOpacity: 0,
      fillOpacity: 0.4,
      fillColor: '#1791fc',
      zIndex: 50
    }
    if (opts) {
      defOpts = $.extend(defOpts, opts)
    }
    defOpts.center = [defOpts.center.lng, defOpts.center.lat]
    if (!map.opts.units || map.opts.units !== 'pixels') {
      defOpts.center = ol.proj.transform(defOpts.center, 'EPSG:4326', 'EPSG:3857')
    }

    let centerAngle = opts.angle || 120
    let rotate = opts.rotate || 0
    var points = createSector(defOpts.center, defOpts.radius, 256, centerAngle, rotate)
    let layer = new ol.layer.Vector({
      source: new ol.source.Vector()
    })
    var feature = new ol.Feature({
      geometry: points
    })
    defOpts.fill = new ol.style.Fill({
      color: hexToRgba(defOpts.fillColor, defOpts.fillOpacity)
    })
    defOpts.stroke = new ol.style.Stroke({
      color: defOpts.strokeColor,
      width: defOpts.strokeWeight,
      lineDash: defOpts.strokeDasharray ? [10, 10] : []
    })
    feature.setStyle(new ol.style.Style(defOpts))
    layer.getSource().addFeature(feature)
    this.feature = feature
    this.opts = defOpts
    this.extData = defOpts.extData
    // layer._add(map)
    return $.extend(layer, this)
  }
  openlayerMap.Sector.prototype = {
    hide: function () {
      this.setVisible(false)
    },
    _hide: function () {
      this.setVisible(false)
    },
    show: function () {
      this.setVisible(true)
    },
    _add: function (map) {
      map.addLayer(this)
    },
    _show: function () {
      this.setVisible(true)
    },
    remove: function () {
      this.setSource(null)
    },
    _remove: function () {
      this.setSource(null)
    },
    contains: function (lng, lat) {
      let geoExtent = this.feature.getGeometry()
      let flatPt
      if (map.opts.units && map.opts.units === 'pixels') {
        flatPt = [lng, lat]
      } else {
        flatPt = ol.proj.transform([lng, lat], 'EPSG:4326', 'EPSG:3857')
      }
      return geoExtent.intersectsCoordinate(flatPt)
    },
    _contains: function (lng, lat) {
      let geoExtent = this.feature.getGeometry()
      let flatPt
      if (map.opts.units && map.opts.units === 'pixels') {
        flatPt = [lng, lat]
      } else {
        flatPt = ol.proj.transform([lng, lat], 'EPSG:4326', 'EPSG:3857')
      }
      return geoExtent.intersectsCoordinate(flatPt)
    },
    _addEventListener: function (event, handler) {
      let feature = this.feature
      feature.addEventListener(event, function () {
        handler()
      })
    },
    _setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts)
      let feature = this.feature
      feature.style_.setFill(new ol.style.Fill({
        color: hexToRgba(defOpts.fillColor, opts.fillOpacity)
      }))
      feature.style_.setStroke(new ol.style.Stroke({
        color: hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity),
        width: defOpts.strokeWeight
      }))
      feature.setStyle(feature.style_)
    }
  }

  function getClassnamesOfFontIcon(iconFontClass, icon) {
    return (iconFontClass || 'iconfont') + ' ' + icon
  }

  function getContainerClassNames(classNames) {
    return 'amap-simple-marker ' + classNames
  }

  // id,name可穿空字符串
  openlayerMap.FontIconMarker = function (map, opts) {
    let markerOpts = {
      containerClassNames: '',
      anchor: 'top-left', // 锚点 设置点标记锚点。默认值：'top-left' 可选值：'top-left'|'top-center'|'top-right'|'middle-left'|'center'|'middle-right'|'bottom-left'|'bottom-center'|'bottom-right'
      fontIcon: '',
      label: '',
      title: '', // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      position: { lng: '', lat: '' },
      showPositionPoint: false,
      iconStyle: '',
      extData: {},
      isFresh: false, // 是否刷新地图
      editabled: false,
      isCluster: true,
      getClassnamesOfFontIcon: getClassnamesOfFontIcon,
      getContainerClassNames: getContainerClassNames,
      zIndex: 100,
      offset: [0, 0],
      iconFontClass: 'iconfont'
    }
    markerOpts = $.extend({}, markerOpts, opts)
    if (opts.position) {
      markerOpts.position = new openlayerMap.Point(opts.position.lng, opts.position.lat)
    }
    let offset = [0, 0]
    if (opts.offset) {
      offset[0] = opts.offset.x
      offset[1] = opts.offset.y
    }
    var iconLabel = markerOpts.iconLabel
    if (!(typeof iconLabel == 'object')) {
      iconLabel = {}
    }
    markerOpts.iconLabel = iconLabel
    if (!markerOpts.isCluster) {
      markerOpts.map = map
    }
    this.markerDom = document.createElement('div')
    let markerDom = this.markerDom
    markerDom.className = markerOpts.getContainerClassNames(markerOpts.containerClassNames)
    markerDom.title = markerOpts.title
    this.markerIconDom = document.createElement('div')
    let markerIconDom = this.markerIconDom
    this.markerLabelDom = document.createElement('div')
    let markerLabelDom = this.markerLabelDom
    this.markerFontIconDom = document.createElement('i')
    let markerFontIconDom = this.markerFontIconDom
    markerDom.appendChild(markerIconDom)
    markerDom.appendChild(markerLabelDom)
    markerLabelDom.appendChild(markerFontIconDom)
    let label = markerOpts.cLabel
    if (label) {
      this.markerCustomLabelDom = document.createElement('div')
      let markerCustomLabelDom = this.markerCustomLabelDom
      markerDom.appendChild(markerCustomLabelDom)
      if (label.content) {
        // markerCustomLabelDom.innerHTML = label.content
        markerCustomLabelDom.innerHTML =  "<div style='text-align: center; color: #000000;font-weight:bold;position: relative;font-size: 11px;text-shadow: #FFFFFF 1px 0 0, #FFFFFF 0 1px 0, #FFFFFF -1px 0 0, #FFFFFF 0 -1px 0;white-space:nowrap;'>" + label.content + "</div>";
      }
      if (label.className) {
        markerCustomLabelDom.className = label.className
      }
    }
    markerIconDom.className = 'amap-simple-marker-icon'
    markerLabelDom.className = 'amap-simple-marker-label'
    markerFontIconDom.className = markerOpts.getClassnamesOfFontIcon(markerOpts.iconFontClass, markerOpts.fontIcon)
    var marker = new ol.Overlay({
      position: markerOpts.position.getFirstCoordinate(),
      positioning: markerOpts.anchor,
      element: markerDom,
      stopEvent: true,
      insertFirst: false,
      offset: offset
    })
    markerOpts.preFontIcon = markerOpts.fontIcon
    this.protoSelf = marker
    this.map = map
    this.visible = false
    this.opts = markerOpts
    this.extData = markerOpts.extData
    if (markerOpts.map) {
      this.visible = true
      map.addOverlay(marker)
    }
    // marker.setMap(map);
    // map.addOverlay(marker);
    return ($.extend(marker, this))
  }
  openlayerMap.FontIconMarker.prototype = {
    setFontIcon: function (fontIcon, isModifyPre) {
      if (isModifyPre) {
        this.opts.preFontIcon = fontIcon
      }
      this.opts.fontIcon = fontIcon
      this.markerFontIconDom.className = this.opts.getClassnamesOfFontIcon(this.opts.iconFontClass, fontIcon)
    },
    setMarkerClasses: function (classNames) {
      this.markerDom.className = this.opts.getContainerClassNames(classNames)
      this.opts.containerClassNames = classNames
    },
    addMarkerClasses: function (classNames) {
      let cClassNames = this.opts.containerClassNames
      cClassNames = cClassNames + ' ' + classNames
      this.opts.containerClassNames = cClassNames
      this.markerDom.className = this.opts.getContainerClassNames(cClassNames)
    },
    removeMarkerClasses: function (classNames) {
      let cClassNames = this.opts.containerClassNames
      let cAttr = classNames.split(/(\s)+/)
      cAttr.forEach((item) => {
        cClassNames = cClassNames.replace(item, '')
      })
      this.opts.containerClassNames = cClassNames
      this.markerDom.className = this.opts.getContainerClassNames(cClassNames)
    },
    setMarkerBgIconClasses: function (classNames) {
      this.markerIconDom.className = classNames
    },
    removeMarkerBgIconClasses: function () {
      this.markerIconDom.className = 'amap-simple-marker-icon'
    },
    setMarkerOffset: function (offset) {
      let toffset = [offset.x, offset.y]
      try {
        this.setOffset(toffset)
      } catch (e) {
        console.log(e)
      }
    },
    getMarkerOffset: function () {
      let toffset = this.getOffset()
      return {
        x: toffset[0],
        y: toffset[1]
      }
    },
    _getPosition: function () {
      return this.opts.position
    },
    _setPosition: function (lnglan) {
      this.opts.position = new openlayerMap.Point(lnglan[0], lnglan[1])
      let runPos
      if (map.opts.units && map.opts.units === 'pixels') {
        runPos = [lnglan[0], lnglan[1]]
      } else {
        runPos = ol.proj.transform([lnglan[0], lnglan[1]], 'EPSG:4326', 'EPSG:3857')
      }
      return this.setPosition(runPos)
    },
    setzIndex: function (zIndex) {
      $(this.markerDom).parent().css('z-index', zIndex)
      // this.setElement(imgdiv);
    },
    _setLabel: function (label) {
      let mlabel = this.markerCustomLabelDom
      if (!this.markerCustomLabelDom) {
        mlabel = this.markerCustomLabelDom = document.createElement('div')
        this.markerDom.appendChild(mlabel)
      }
      if (label.content) {
        mlabel.innerHTML = label.content
      }
      if (label.className) {
        mlabel.className = label.className
      }
      this.opts.cLabel = label
    },
    _setMap: function (map) {
      if (map) {
        map.addOverlay(this)
        this.map = map
      } else {
        this.setMap(null)
      }
    },
    _show: function () {
      // this.setMap(this.map);
      $(this.getElement()).show()
      this.visible = true
    },
    _hide: function () {
      // this.setMap(null);
      $(this.getElement()).hide()
      this.visible = false
    },
    _add: function () {
      this.visible = true
      if (!this.isAdded) {
        this.map.addOverlay(this)
        this.isAdded = true
      }
      this._show()
    },
    _remove: function () {
      //  this._hide();
      this.isAdded = false
      this.visible = false
      this.map.removeOverlay(this)
    },
    _getMap: function () {
      return this.getMap()
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      var m = this
      if (this.markerDom.attachEvent) {
        this.markerDom.attachEvent(event, function () {
          handler(m)
        })
      } else {
        this.markerDom.addEventListener(event, function () {
          handler(m)
        })
      }
    },
    _infoTipShow: function () {
      $(this.textdiv).css('display', 'block')
    },
    _infoTipClose: function () {
      $(this.textdiv).css('display', 'none')
    }
  }

  // 圆心    半径  边数  弧度  方向角
  function createSector(origin, radius, sides, r, angel) {
    var rR = r * Math.PI / (180 * sides)
    var rotatedAngle, x, y
    var points = []
    for (var i = 0; i < sides; ++i) {
      // rotatedAngle = rR * i
      rotatedAngle = rR * i + angel * Math.PI / 180
      x = origin[0] + (radius * Math.cos(rotatedAngle))
      y = origin[1] + (radius * Math.sin(rotatedAngle))
      points.push([x, y])
    }
    points.push(origin)// 将原心放入
    var ring = new ol.geom.LinearRing(points)
    ring.rotate(parseFloat(angel), origin)
    // ring.rotate(Math.PI - ((angel - r / 2) / 180) * Math.PI, origin);

    var geo = new ol.geom.Polygon([points])
    var a = ring.A
    geo.A = a
    geo.origin = origin
    geo.radius = radius
    geo.r = r
    geo.angel = angel
    geo.sides = sides
    geo.polygonType = 'Curve'
    return geo
  }

  function flatToGeo(lng, lat) {
    let geoPt
    if (map.opts.units && map.opts.units === 'pixels') {
      geoPt = [lng, lat]
    } else {
      geoPt = ol.proj.transform([lng, lat], 'EPSG:3857', 'EPSG:4326')
    }
    return { lng: geoPt[0], lat: geoPt[1] }
  }
  // eslint-disable-next-line no-unused-vars
  function geoToFlat(lng, lat) {
    let flatPt
    if (map.opts.units && map.opts.units === 'pixels') {
      flatPt = [lng, lat]
    } else {
      flatPt = ol.proj.transform([lng, lat], 'EPSG:4326', 'EPSG:3857')
    }
    return { lng: flatPt[0], lat: flatPt[1] }
  }
  // eslint-disable-next-line no-unused-vars
  function getClusters (markers, opts) {
    let source = new ol.source.Vector({
      features: markers
    })
    let clusterSource = new ol.source.Cluster({
      distance: opts.gridSize || 100,
      source: source
    })
    let styleCache = {}
    let clusters = new ol.layer.Vector({
      source: clusterSource,
      style: function(feature) { // 聚合样式
        var size = feature.get('features').length
        var style = styleCache[size]
        let clusterImgSrc = 'data:image/png;base64,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'
        if (size == 1) {
          clusterImgSrc = '' // 显示marker自身的图标
          return feature.values_.features[0].style_
        } else if (size < 10) {
          clusterImgSrc = 'data:image/png;base64,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'
        } else if (size < 100) {
          clusterImgSrc = 'data:image/png;base64,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'
        } else if (size < 1000) {
          clusterImgSrc = 'data:image/png;base64,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'
        } else if (size < 10000) {
          clusterImgSrc = 'data:image/png;base64,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'
        } else {
          clusterImgSrc = 'data:image/png;base64,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'
        }
        if (!style) {
          style = new ol.style.Style({
            image: clusterImgSrc ? new ol.style.Icon({
              src: clusterImgSrc
            }) : '',
            text: size > 1 ? new ol.style.Text({
              text: size.toString(),
              fill: new ol.style.Fill({
                color: '#333'
              })
            }) : ''
          })
          styleCache[size] = style
        }
        return style
      }
    })
    clusters.clusterSource = clusterSource
    clusters.opts = opts
    return clusters
  }
  function setClusterLayer(markers, opts) {
    let source = new ol.source.Vector({
      features: markers
    })
    let clusterSource = new ol.source.Cluster({
      distance: opts.gridSize || 100,
      source: source
    })
    let styleCache = {}
    let clusterStyle = function(feature) { // 聚合样式
      var size = feature.get('features').length
      var style = styleCache[size]
      let clusterImgSrc = 'data:image/png;base64,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'
      if (size == 1) {
        clusterImgSrc = '' // 显示marker自身的图标
        return feature.values_.features[0].style_
      } else if (size < 10) {
        clusterImgSrc = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADUAAAA0CAYAAAAqunDVAAAAAXNSR0IArs4c6QAAAAZiS0dEAP8A/wD/oL2nkwAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB9kEGAgqEZIAg5UAAAs7SURBVGjevZpbjF1ndcd//31mxmOPx/bEcWKcQBOapKkTBTBY3KFKC6nacBHhgbZSURHwQOAFzhY8cBEXCcE5AokHUFUEqHmIkCo1KomQgkqlJqFABCgxIcVuSNI4xOPY4xmP537O/vOw197nm+PbmRtbGp05l/3tb31rff/1X//1ic2+2gbIAAENoIEN0gh2gdQBjF2+ln8Feda7v6kNTWH9d7cK6omU70eBUaShMCRDysKAeJoIA8EWUoHdRepiryAt0NTKRtdV6/JEU6VRIKQxYGcYAbaRynFt18+Ij2oD4cK/gxXgLLBUL1r/Am6qUT2DMqSd2DvCG5Ux6ZjVhDthVBGfqQ7N0kAnXnTy/TLSHE3NrzUsteZwaxXbkXYDWYRSFV6VR4w9B8wjdS46kbbB3oa0A3t7b0aJceVYBdJpmuoMapjW5B3YA2wHivr+0jsd7AWkeewhpB3AbuwJpJ3ASEyUCLkF7LPAGWAWaR4Q9k5gWwBNtUDVYs1gz5Fnl/WaBjRoGGkiJuz63nIVp2lqkbYPANcDY9gjMTGvAojV/1eT7WAvIU1jHyXPZml7HNgVIJPuyyXsKfLMG/NUqxgG9vWBgIFFYBq4CrgtjOnWAHAhxKshwqwarzfxIWASeDy8vhdpuA9MloHTlzJs6LL7SNqWAEHpHTiJtAv79WFwAXRqqLYbETozwBzSbO01exhpHHssvNEFijB6BftKpLdiP4/0ZKDqFckCDSM1gM76jJKgqXMJ2nXJs0navh771vhVN0JR4aVZ4Cns4wmI7Ad2R0gejZxk7CHg5cANATyKkBPwUuyrgEfCe/vieVM01dlo+BGbcwf2InArcF0fWBRhxDFgHPsWpFuBlwH767Asr0bkouPAs0hHgCexh4GbkK6OsZ0k6CNIJ7EhzxYvl7e0Rji/DumVtevtDDiH9BD2PqR/Cq+MJPunl2TTPZXCt7SI/TjSPRGSrw3jHVGwAjxMnp0bBNa1jsT75xEuID0NHMO+A+kdsZlTD2YRcivYy/G87UkuKhKEHAbOYt+D9CRwGJiIUP0lTZ3YGkbRC8WbsWeBBeBupOsiV1VgMoI0A/wceAz7RWA+Vn8MuBY4BBwOAzt1TpIy7P8hz75DqzgInCDPptZCldbO/VJmAa1YYSdAcQb4N+AX2Dcj3Y59YyDaCaSngIew/xt4EXg38MbgkBVtGgKep6nP/fFYehWKbd8JvDP2yALSvcBzwN3YdwE3rULS1TkKpIeB78Qi3IH99joHSt+iqf9dK5ndWOnR89xdwJuQPge8AfvfkbYl9Ob8e3rcrgKN55Fej70f6YPYbfLs2HoM2rhRPfCYQLob+GISQoOOXSHcOaT3Yf+IPFveSLGYRaVa1UfrvT6A/cUkF2nNC1sS3/uRXrU5RWLJ74zUpSmf54lLh9+7gPtWFX3rvUr07GDfRJ49s6ZoWUWTyrpmd6BYl1axFFRnhKYWL1q+lwMeAP5l0/ZnuSjDSJ8H3j/QorqOtJHgqSui7Sx4VSN+NBcP+AtgLtDsd6EluPZkuRifQGpvunhT8cWSZ642rBJ2SmJ7LU09Rdv7gmaBtCjabkT5oEh+LwbleU0w8iGk+4H3ROH3NPAk0nHsJ5AObrJBVRjfS1N/H14YA/40GP9MlDq7or57IArLHZEmikrKypJNvox0VfL+LE2tYN8GvAbpvUh/ib0bOLjpXurVYn8XBn0B+CrSR4G/xl6K8r+LtIJ9FXanlhYgy4DhpADrhqtHksdU4XhNAr9PA3/LVl1V/moVtyA1on4qkG5BOlfzy9KQsWD9dRrJEsmKpJAbCzQrmULbY9gN7IoOnUX6MyS21LByr09FNaB4/ngSRQ6t0elcsosM6MTYLrAfqYhYXwJOYU/0LQhbFIqTfcg6dEH0S+aSXRaFyoHPJQMPA+NRR7HFl4PVuw/2L+bZMGq1cqoLFHHbyLPJJFQzYFsoO1tskgvgGqCo66+mTq9Sfav9t8pT5Y2rPVfCpsJTO+K7MzGIgYPY98aCeEs8VE7yCNK28E6GNE3bo8niK8SdBn1GdJEUkxWtYgiYSdw5Hq9HYxAj3QgciyJRW+AhIf02VKrRWOgG8FjMp1r8DOlUbIkKSMjCtZ2YnCORnUkk5RFaxQT20VoKhhuBfdjf3kLk+wj2ochJDpX3Z8DeJJcZexZpuNbxpeUs4LCbDLkdez7ESsV3NwBP1J+VEvP7ge8CS7WkvHmeOkJTP0b6m6SqPkGePQdcnRg+RZ4V4anq3uWMpozdrS0vY7iDgsyWe+4A0umAcqKfdAMwjf1Aotpuxl7qIjVpFW8DroxxM+BHtIpdwN4k0R6nVQzXObQ0dLGC9KUELo29DXuyhvQyRK8F7kMaTZDmH4C7gJ9seG/1WM3XsX+DdFdo7CDNYT+ayHOK10mk8Yg2BdPoVEbNJ8BgYAdwLJSdyoMvI88ew/51osldF3rEX8Xn6QTXhnTlPvky0qeAD9d9KrsB/CwmvQe7iDnNAPPJniP0waKsfMs6aTHJUUN1bEMjDN5Jq7gV+Gek2bqFI70a+Efgduz/SJiAB/BO2g76LPA17I/VPLPkfb+jqXuxb6vTj9TAfgoYr9lPOdYieUaW1CrTSQIWcAXwDNLZeF8gvTwU1O8ndGUZOBQaxfuw7wwg0UWTc69XBfAr4AD2d5E+Gcy/6vvOAN+kVRxE2lXDOEySZyeiV+Y6JeXZXI9BtGv+NBEKarU5T0et9YokTIz9IPBW4L1JKsgCQO5B+gX2u4EPIe3H3rOqrwUnsY+FbngE+w7gPUhLCTAsYH8rmnKvTRTeDtKD2DtrrbBco7OVLK2+8nwEe2/y8Kptcwj7mrrDUYbqfyIdxv5QGFOpswJOAc8C/xpJc88qUcaeQroFeB1wfTDvIlmcWeyvxvvDtdfL18eRTgB7k0hYieIW8iwxqidQXoE9WjMMWKSpKdq+vY9cNrD/K3pNH49WD8l9C8CXaOrUBcSa0Qi1A30KlLGfBb4R4X8oWEW1GC+QZ4/S9kv6+OkZ8mzxfJberIntVBzgqGjHKG3vwn4oEm21VwrgzdjbkT6NfR9wDhiNjXsyjg6cf+XZIvbvE4K8Hfsk9veArwA3Ih2KxO945iR59iit4soEXIS9lBp0fulRKUb2mb7NvDM42I+RFmqDy1C5GftNwMNIecjIc8D/09TyJajQb8PbLwAt8uwzIfLciXRtX/dkGunntL0vTs4QYd4Fpvp1S11CzxtD2hPN6opkzkTj7TDS1dFMqx7cCMZxLDp/o/0r2Df+eFStp6JreD3SRNL2rMjzszR1hFaxN0qetIA9RZ6t9CtOl+vOj2PvSg52GHspypA/AV5ZS2eVdl4dBGnqgQFaQm/BHoujP6TiSRj0SDTL99V95F6z7xR5tnQhPTK7jPI5G/skTaajSPuA57AfRJoKbypJvNOrUsX5e4q6RkvPWJTP6QDPkWc/iDDenzTGq/1cGtQjDus4HFKCwRVUp77KUHQg3ExocC+N5tso9k/Js+MDjD0B3B57Ywk4CrwQOWlPvX96DTlHI3vpUh2RbCAqk2cL2KeCd6ULsQP7QPz/BE3dDzyCdPyiXkpD0J7G/j/sn9DUD4FnIp1cHTJdet6pJLCXMWhw/bvXsmkAu5C2rzos1YPYJWAOe3HgvlKraIRkMFrXRf2Ht8pW7DnyzIPo64M9uToKl2fd2AdTwQVV8y7HASoYGVhlKr25E3scezhZKNVdeXsSaXZQgzbW8y0nNRakcriO+b7sPoBh1eGs9BTaEtJ83XVZY0dx7b3HsoldeXAu9tqL0f7pJAx70Gs5PLQc2sjJAIPyEMg6WqR/AP8TaUdhTpMSAAAAAElFTkSuQmCC'
      } else if (size < 100) {
        clusterImgSrc = 'data:image/png;base64,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'
      } else if (size < 1000) {
        clusterImgSrc = 'data:image/png;base64,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'
      } else if (size < 10000) {
        clusterImgSrc = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABNCAYAAAAIPlKzAAAACXBIWXMAAAsTAAALEwEAmpwYAAABOWlDQ1BQaG90b3Nob3AgSUNDIHByb2ZpbGUAAHjarZGxSsNQFIa/G0XFoVYI4uBwJ1FQbNXBjElbiiBYq0OSrUlDldIk3NyqfQhHtw4u7j6Bk6PgoPgEvoHi1MEhSHASwW/6zs/hcOAHo2LXnYZRhkGsVbvpSNfz5ewTM0wBQCfMUrvVOgCIkzjiJwI+XxEAz5t23WnwN+bDVGlgAmx3oywEUQH6FzrVIMaAGfRTDeIOMNVJuwbiASj1cn8BSkHub0BJuZ4P4gMwe67ngzEHmEHuK4Cpo0sNUEvSkTrrnWpZtSxL2t0kiOTxKNPRIJP7cZioNFEdHXWB/D8AFvPFdtORa1XL2lvnn3E9X+b2foQAxNJjkRWEQ3X+3YWx8/tc3Bgvw+EtTE+KbPcKbjZg4brIVqtQ3oL78RfCs0/+HAmzJwAAACBjSFJNAAB6JQAAgIMAAPn/AACA6AAAUggAARVYAAA6lwAAF2/XWh+QAAAUiklEQVR42tScWWxc53XHf+fODIeLKGrfZcuWbcXNZhdN2jiw6zgtmjoF0gJugCJPfS1QoC1SFGiB5qVPfetz0Ie8FEXb1N3sLkBcJ3YTw0njxnVsx5JXWYsp0aIWcjicmXv6MP9DHl6NKA5FiukFCFGcO/d+3/876/+c77OSi6znMgzHr/vboCvuy9+p/g7UgAIw/Vs4Xgd6hnUB108PKPNzbzSePCbHbzjm+GyYq87WXAUwAjQ0hroAswSIpYn7inXo/3T109FP73ZO4HYBVxjWcLxhWCOky7CQrCKvePrdl/60EjhzvCHJK4HS8Z5hHccDyPL/K3CFAGoYFtJVS9Ll6aesqnQCzAdIXNxQq8yhK8nrpJ/uZoBY30TARpM61hw3w9zxEnALTQRLvy+BJBBLARkqbPF7qLCeF/fUgLphI44HgG399DYSwI0ELmzUuEBr6v+l4yFVAZ7JILs+c8NMTqAXn+m75niRFqXhuAeA8V7Hy4o9LGIcji8CC/rpVSR4S4EzATUZqpgAiUkGWGHPekDbsEVgURKyQi2rnlDfKwyrOT4CjMgM1GUrXYBn6bV0zzhwFWj9NABXAyYNG01GPksEjodKdh2fB+aBbrJlo8AUMKHfGxW1MtmqFjDv+DXD5hyfE6h1w8YNGxOgXvGyHiGOYVOON4E5qfKWADeqyY6EDZNkWfKmbtiCBtqWeo4DB4Htjo8bNiKvW0/xnFdiK5caRxiyqGfOAhcdv2LYFUn+hGFjFS8c6l/TZ4UWceF2AmfAqGHb5AAII51isFLS0ZI6FsBeYKdhO4Ftjo9JjQBKTcxvFMRKai0FyYuGzQNXHL8MzBp2yfFZSeeYbFzNsDIcjp4R4JnA880GrtBLt+n7S7GWBlFKshaAjuN1x/cbtgvYD+yQVC3Gvem52ckMmkhZ+bcmu7pLqnkZuABclCRe0/PHpP6FvuvJLtf199YwXteGTLlC0qY0aK8AVwqwlv6+0/EjwEHZn648ZynVLjz7AkJcV0iea0GKFL9ZSqUCiHAaTdnQc4adBi7JK4enr6VQpgjbC1zR99YGhDMzrLRNSM2KtEImYz8HLDi+DTgKHDZsAugK1PC2CLSIyRaBdgobOlKnnDmMyPA3BMBIhDdaCJJDKjS+ecPOAO/LoYxobI3w+OG4BNzcWqXO1pHk1wwL+1TTS9vArOMdw44Cdzo+FasboElCQjUWDLsIzDh+2bB5GWvXd3Ypwa85fkUOwZLnnTJsN7DH8THZrm4sTLKDXantGeBdjXmHnlEqhJl3/Now+e56gEOT2SY7twBcEkjHHT8IbFMeGpG9RY7q+AzwjmFnJZ2TwH7D9jm+BxgXyOMCoQbMGdYD5hw/J8k+KylpAocdvwPYq/sikC40rp4c1XnglGFtYDcwKkm8pjiSoVXVh3Aqkoi6wOho9U5o4qOhPuGAFN/NAO8C5wTIXYYdB/Y6vlt2cyq8dE7sk3q3BXzLsEvAB46fAt6RpB4E7nB8r+zYYgTdssGLhk07/iZw1bCm423Dej5kMnErcVzEU03gbuC4VDaMdajxfKiJVOEe4CPA/cCdsjek73nVqybb2BS4S+EI8B7wY+Ak8LY86lHgDql15KimRblbz351GGewGZlDxEmtZMdqGuxlAXYG2AP8guOPAoekpm3D2ul7OV7LBKNL7VdQTlLFew37KDANPAe8YNibslnHge0yET29Y0Hvaw4T/OZg3PHBqnojNvVGrKnjTcNOAEcitnP8A0nBgmEPOv64YXcq8m8HI5JYkxyKhDQHUzIiW2WVFC5/r+H4iGFnHX/asBe1qPfJ9hV63hngDXnQGzLDqzHbqwK3mt0bAByyFycUgpwBfuL4duBx4HMKMcoERuSOJimYk/Gfl9G/qO80gN2O75D9nHB8QnGhy/BnO1iT/X0OeMrxS4bd7/ghxXYnA7S1ADeIUl83cKtcY3L1LYUlvwh8XGxtJ9mvUUnPNPAm8KrjZwWeiyluKqS5LC/Y1WfbgAOGfUQOaa+kr5WC5XjfK8B3ZC6mgAtK0VgLcKvVUIYC7mYinezTUeDXgU8pW2jre6NAUxL5XcdfklQdcvwu4IDSpx2GbXd8n2HTspVXlMxfMOwdx98TrfQA8JDjRwVKW2CMyYG8BPy14xcqjMwtAbcZDLDLW13Uv00NYtzxNvA94HkBcQL4OPBp4GdEAmxTfNVIzwzOLlT4DeBFgfID4HVJ94OKA8Pozyl3bUliN461HVbibqbC6Z69wGOGPSrq6JLjz8vz7TDsCeAJx0/IXg20oVVbmu7pShX/Hvg7AfQw8BBwQF7128Azkto1rLhvnqoOARxKi77k+CeAfwO+Y9hngT8BPl8BZ1BFa00TlSP4muPPGfaQ478qJ/CPjs/crGa6Ws11q4DDsKbClLcc/7Jhf6Yg2BWKUKkhrNUcWOX3c8AfAn+rzOGqeLrVAahI95YDV3lRDfhzw76ssKAYtnK+BulzqeSTwJ9KddcsacMCV2yowRz8ohrwW4Z9xfEjqVjDBr/bRJR+BfhNefBNLRrnCdY2+PkjwBdl0/ZzGy6xLb+ncKi+2cAFQZkrTRvx0k8AX1VSz2ZI2g2ue4HfAX5uE/CqAVaXXjcMGxfL2oyap2KtSUlOK+WQ5RqKG5PAbzj+MFtzfQZ4QiHLtXWWPXsJrFqivBbrSaU83WhqYnGRhIcF3CWxqbNiFkKEelWDCzzs+ONbAFh46zrwiOOfNezfK2MbVEtZai+L6phSwjFgTA6uJkyKevD/obYRHkSxVjZjSkzDlNKWUyIUj+i+kxUJHHH8QeD4GtO0DXUS6X13GPZp4NlUTRt0jQM7xQpHcf2qUjwMq4tyD2qrHu0KI6kIHHTNIv0KeT3rt+OIR9shdbhHdcwLIhR/6Pi9si/bBsRdt8dH9N+3B/iM40eBU+nzw7KDNY255vidymiKRJI2EqMT5GrheL2eu4YcXyIL1Wu2Q4l0mcp/i2o9uNuwQ2JbDxp2DNhn2NuOHxe7a+sIbjdK6oKFPiri9DTwRZmdSUnYjHLfaalhI5Uo6yJBr0SxPTm4on4D7xl082gKUUx/X1Dfxm554GihiupRQ4DuXCc1tdHXNsOOOf6K8ti7ktrWxcC8LZtdJjtYkwrPyiGOVN1rwfUdj9FqNZKY07B/i6KCJmU4wyB3gcvqiDwgu8jtlrZKQBze/YTA+NCwGcOuiOMbkVQt0u868IrDyE5zRS9LkW+WioaUBGlYT6ytq3YZraQ1VlbcOyo073Z8fKulTRMdc/yA6PprMkfhCEvrh2Rhhqp9d6O5mTHPp1iFcUC0dLWNtJSkjbLc4GyGdQ2bHSDBW3ZpLtEbXHP8QwFYSC0LObCJKJxncFKz4i1XuaJZsCn7YEmFO4Zd0GCagxZlq6VP9YzuANPkqxAUSx2jWYiKod/fv9qixJf0XlWmvSx3IPV+moCTzR6VtGVVjn48FGoNspVWsX8rgat2fUc3T2XVavJAUXyOVasrtitlO8y2WGNlbgoVf3qKBEY1bpNqXhPFv6IQLiyKtbAjg65oNrZ0f10vXqzaBJZ70K6yzk7HTbgWFatFL12MP+Y0r3tqFTwiexro4QplCXmFTFJlag3tpJWwtGdhrpKvmlpUG46fdnxGou9bpJqu988CP1YttpZU0g3r0O/qHFfokVWkVLkyUtLrvGo3wEuTXOrDrawAMvwTSrFms1rLaYyJib20lXFc8vhXDXsf2KXUMuZdApcMOyfQGhGOJUFZVDpWDyGLfDW3YlVjmLrjswpoA8RCyf64YdOSSJLEjRl2h1qx3s4x4e2WtgTCe46fA46p1uop1JrTHMccH3G8kKRG0HslOkdTywaGldFK2q0k4kvVcHVLkiSyDkw5flENy7lo0qTfhfSe4z9Q4zS3O65LAeuM4y8Ydh64T/Hnkn1z/Kq0Y0I5eYC6lF6y3GAdpsqBbhjDbsVgogcRht6wWvKSu/TQ845fq2xiOyaVfYl+a8PtzlezzX7dsO8rHz2sRu5YyAXDLjq+oHy2noSmKxvuSvxzOOKOd4pgQri+ozvswazjrZR6uRL4Cfp9H6fl4qMXdwfwKfrV9ie3KFtATu1bwP/S7xTYlgjLGnBeY5w0bHvYPalrW8xJ5KtFJUzphMRFJ1GVOh4ROzAnTxsRdhM4atgZw37C9XXOh7TT5UnDvmu3W1P717fpF6Sjv6SWUyn1npwUkzORpKqQNl1keT9tjklLYLGQKpUsdwOFHqPwoqUcNHI7V/53WL+/Zti7GqCLddgHPCae64/Dw97GxP6y43/h+EnDvgDsU95ZCsAPgXfF6OxLGJjmEC1nwf6UKb3sGtbLAV9HoYklRzCmL1wy7MOwA4731BV+EHgL+J76P2JZ5hx/QIWaF4E/ki1ZkRtuRtymyX3NsOfVbvEpvTtawEYNe8mw/xHBuVtguUKPOQFLIjI8zbsDy9sZcXxRHxQpoa2JWvnQ8WkFtyR7dkwP/5Hjb8qQFlL9hmGPOP4I8A3DfleSt7ST8FaD4xQ6kLZt/gHwdeAjjj8W6Va0rxp2zvGX5fGPsdwm6/p8hn7XZhAZUU6oSWLb1Up+bIz1Ss46rhdPKwoPun3RsL1iVKcN+2elYdE0veD4TuCXgEeAvwK+6vjJxLLaeuK8DFZ6xvvA7zv+dcePOP7Lhh0ybEGf1xToPw28Jmr/AP3NK2jRWwqzukE1Va6uYR3DlkAIo9cRExp/LxXwjtJv7jurIgfaVObi9GcN+yFwDHhU9y8o8t7n+K/pWf8iNfht4PMyyrHzeZiiTr6nBTwD/KXjzwEf02Id1/tLpYkF8JxhL8jD3pMXQZnDW6FZho2mEM2kou2w/1U+Lvax15M9Cu79ihqT92vCQR+NqtLVUv1yJ/AAy13dC6LRn9Bn/2XYO6oufU4lxiMDALnZdZ5+e/6zwN8oNHpYoO1Xc+GixtEDXnf8Kdnpjyrhb8kh1BWvntbfdgnoXhrXYi4xVvdyxdbw7dm76u9Xpd93GXZvyvciCv/QsB85vsewLzr+San1YooLC+BlxVcfqCP8Zw37guP3KNkek4cuKyxOR0TkHP2e3m9pEV7RRB+n329ckxlxvbN0/JRh33T8fUnkkaC/ZNNrjr8mR1fXttCwe6ENV0RBDQQuKj87Yh9p2lzW1hcb9NtPd+vvZSoDTjv+ffq7Zr5Ev3ekk/Zyxf76a4a9rnvflD05JCA/adhRBaEk73YW+L7qox8Ydlm10J837H5JdS9VqqJt4ZTj/2TYqwL2iMDpRTqlPWUvS6KmEuARMC/QbwdZ2lE9aC+XaSKTKamNVKOlgsd++v27k6zc/9mTuP+Y/s6WJwx7ELgW3invGNQ+qpZIgacUrU/KRvZSbFYTb9ZTcfljSqF2aZzNdDhCOIIx4BXHv2nYKUnaQd0bMVsIxGv0W/nHgInkHE3zvhop2Go1BxfCuYE5+iqa8izntDKxB74jcOv0dw4WkoxvSFJ+JdE0od5NlnvYJh3/D9ml1QjQCcPupt9l3khbN11qZ+lAmP8EnlZ4cb/jh0XvB91fU6nzLHBWGjYWQX6iy+IIDl9LsaYraZhKqUowI+N60DsS+Tt1T6mVrCtEqQM/Af7B8Q/Um3uX7u2kreg1+m2o7TWEIV2p+R615ndTmbMhuzbt+LPAC/KCJ0Q8IEDC4ZVpQ1yhedXTJmNThjTHgI71+k1o8xF50Lypty5pm5FU1ejv2KuxfNbRiOKkMfqt9M8InI9Lxfez3INXl32ZXYMnbQNvOz4n6SmSd59R0v468N8yIw84flCOpptsuAPnpcKtIDkroHmKCnyo8qAGWEsiHAazKc9zCTgphnSPxD3iwUIATRn2phLqNxz/aLJRB5QCvcsazgIJmsvx07JXLQXfZ+h3TL3s+LwygvvUaRXHBBUpxJjWuGflkZtJqyKgXqS/T3ZgcL6Wjb6jChib6SQFkwGe10o3JEl3pBVdal5RQD0jRuKMBn/EsBOOTxn2r/JaN6y4pWs7/c0g+wXAawpPSnnMw4YdSDZ1qWKlSlfQSVcTPZaPLSpk1y6zvFlvXcAFeNtFbuag0LTqVySJx6Si2xKLshTSaAJzAvuCBof2YZU34NWu0xJtV+qqQj9Jf3fgLpmQorJwIWktSepb4h+nxH7E3vzw3l3NZ/5GRephgDMVYaYUMJZpFaNUOK8B7Xb8HklEMK2WTlxwOZ8O/Q1ur1Zd/c3A0wa5u7VA48qn68nmBXBNTXxatjHauSZSQJ6dVE8Z0vzN8ue1tkCEoSx0SEstMSS55WGefkfQy9rPcKfUoZ0pK+WCYwoJhu5018bi3Sk18sS3hfdvSB1jo/G8ToGYkAe2FCyH7bvG8tEfbARwkfDHSkymMKVk5cFPLQ34lFTxEP3OyEmWTxqMvHhFND7kWEK9i1Q0Dy99TfHZ+wpPSi34RAXo3MMcFf01HZ8xbNNNKZVE4p75uWA5JuTar6qYMy0G5YAY5WhGXjDsknJZv0n94LrqvDbw7pO0RxfVZWU305KyBdnn6Jz3tKvaUrYzFGjrAS5ACps0IWZhqQoUqki/EXlBk3lX9Ppecfx7HW/rvJHeOiXuqhax0HjiSI0zaQy7ZJsjZQxSMlKpjiR36Hb+oc4dGbANcuk0L5YPIPBUiI4Wi7YMblDYozLoq56mdZOy4rjCjlLqOK/3RqdBpr290lXgkto5Oa+Be9k2E7iwK2OihHJLu1cnL+mKTqceNzk98CbA5fOW6ol8rA/ourLEOPe0YPNx2N96gLvVbUfR0VMqYR5T6bCRWkODZY3TV+uS0IE54JDvjq1UzfT8JSAifhQZ0DVsQaDd8vG3G7VJLKSnK6YkDo3Knjd4uzgAdCOKrRHW1GOhUq4ZHUY9BdjB4HY2YsIbvbsuWgcWJAVNUVP1bAMTRX+rV5ny0CXA9I441bAtUnRDDhXdLOBChbqigFoCcEzviv6T7lpUZQ22Jt7T0KIEON10jl25Gb0rm33ydASqrcTljbDGM3nXMOFe6ozvqRTY2ih1XO36vwEAoW9tY5gUsLQAAAAASUVORK5CYII='
      } else {
        clusterImgSrc = 'data:image/png;base64,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'
      }
      if (!style) {
        style = new ol.style.Style({
          image: clusterImgSrc ? new ol.style.Icon({
            src: clusterImgSrc
          }) : '',
          text: size > 1 ? new ol.style.Text({
            text: size.toString(),
            fill: new ol.style.Fill({
              color: '#333'
            })
          }) : ''
        })
        styleCache[size] = style
      }
      return style
    }
    clusterLayer.setSource(clusterSource)
    clusterLayer.setStyle(clusterStyle)
    clusterLayer.clusterSource = clusterSource
    clusterLayer.opts = opts
    clusterLayer.markers = markers
  }
  // HEX十六进制颜色值转换为RGB(A)颜色值
  function hexToRgba(val, opacity) {
    var a, b, c
    if ((/^#/g).test(val)) {
      a = val.slice(1, 3)
      b = val.slice(3, 5)
      c = val.slice(5, 7)
      return 'rgba(' + parseInt(a, 16) + ',' + parseInt(b, 16) + ',' + parseInt(c, 16) + ',' + opacity + ')'
    } else {
      return ''
    }
  }
  function formatLength(coordinates) {
    let length = 0
    let sourceProj = map.getView().getProjection()
    for (let i = 0, ii = coordinates.length - 1; i < ii; ++i) {
      let c1, c2
      if (map.opts.units && map.opts.units === 'pixels') {
        c1 = coordinates[i]
        c2 = coordinates[i + 1]
      } else {
        c1 = ol.proj.transform(coordinates[i], sourceProj, 'EPSG:4326')
        c2 = ol.proj.transform(coordinates[i + 1], sourceProj, 'EPSG:4326')
      }
      length += ol.sphere.getDistance(c1, c2)
    }
    return (Math.round(length * 100) / 100 / 1000)
  }
  // window.openlayerMap = openlayerMap
  return openlayerMap
}
export default openlayerMap
