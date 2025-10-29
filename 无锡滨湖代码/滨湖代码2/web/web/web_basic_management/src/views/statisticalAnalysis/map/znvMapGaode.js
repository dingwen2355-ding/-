/* eslint-disable */
import createScripts from '@/utils/createScripts'
import axios from '@/utils/request'
// import { getConfigResourcePowers } from '../../../public/config/mapFilterConfig'
// 用于修改地图颜色（自定义修改时 注释，并在index.html添加）
createScripts([`http://webapi.amap.com/maps?v=1.4.15&key=${configs.map.key1}&plugin=AMap.CircleEditor`])

function gaodeMap() {
  let AMap = window.AMap
  let gaodeMap = {}
  gaodeMap.Constants = {
    ZOOM_CHANGED: 'zoomchange',
    ZOOM_END: 'zoomend',
    DRAG_END: 'dragend'
  }
  // 组件加载好
  gaodeMap.loaded = function (loadedFn) {
    AMap = window.AMap
    if (!window.AMap) {
      setTimeout(() => {
        gaodeMap.loaded(loadedFn)
      }, 200)
      return
    }
    loadedFn()
  }
  /**
   map类
   */
  gaodeMap.Map = function (mapDiv, opts) {
    console.log(mapDiv, opts, 'mapDiv, opts')
    // let layer = opts.mapShow
    //   ? []
    //   : [
    //       //只显示默认图层的时候，layers可以缺省
    //       new AMap.TileLayer.Satellite(), // 路网
    //       new AMap.TileLayer.RoadNet() //高德默认标准图层
    //     ]
    let defOpts = {
      isHotspot: false,
      expandZoomRange: true,
      zooms: [3, 20],
      viewMode: '2D' // 地图模式
    }

    defOpts = $.extend({}, defOpts, opts)
    if (opts.maxZoom) {
      defOpts.zooms[1] = defOpts.maxZoom
    }

    if (opts.minZoom) {
      defOpts.zooms[0] = defOpts.minZoom
    }
    console.log(defOpts, '----')
    let protoMap = new window.AMap.Map(mapDiv, defOpts)
    let map = $.extend(protoMap, this)
    map.protoSelf = protoMap
    /*var styleName = 'amap://styles/blue'
    map.setMapStyle(styleName)*/
    map.on('complete', function () {
      // 隐藏高德logo
      $('.amap-logo').css('z-index', '-1')
      $('.amap-copyright').css('z-index', '-1')
      $('#tagmask').css('display', 'none')
      if (opts.mapComplete) {
        opts.mapComplete()
      }
    })
    return map
  }

  /**
   map接口
   */
  gaodeMap.Map.prototype = {
    _centerAndZoom: function (point, zoom) {
      this.protoSelf.setZoomAndCenter(zoom, point)
    },
    _setCenter: function (point) {
      this.setCenter(point)
    },
    _setZoom: function (zoom) {
      this.setZoom(zoom)
    },
    // _setLayers: function (layers) {
    //   this.setLayers(layers)
    // },
    _addOverlay: function (overlays) {
      this.add(overlays)
    },
    _removeLayer: function (layer) {
      this.remove(layer)
    },
    _removeOverlays: function (overlays) {
      this.remove(overlays)
    },
    _removeOverlay: function (overlay) {
      this.remove([overlay])
    },
    _addControl: function (control) {
      this.addControl(control)
    },
    _removeControl: function (control) {
      this.removeControl(control)
    },
    _addTrafficeLayer: function () {
      let trafficLayer = new AMap.TileLayer.Traffic({
        zIndex: 10
      })
      trafficLayer.setMap(this)
      this._trafficLayer = trafficLayer
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
      let mouseEvents = ['mousemove', 'click', 'dblclick']
      if (mouseEvents.indexOf(event) >= 0) {
        this.on(event, function (e) {
          let point = new gaodeMap.Point(e.lnglat.lng, e.lnglat.lat)
          handler(point)
        })
      } else {
      }
    },
    _on: function (event, handler) {
      let map = this.protoSelf
      this.protoSelf.on(event, function (e) {
        if (event.indexOf('zoom') !== -1) {
          e.zoom = map.getZoom()
          handler(e)
        } else {
          handler(e)
        }
      })
    },
    _setMapStyle: function (styleName) {
      this.setMapStyle(styleName)
    },
    _destroy: function () {
      this.destroy()
    },
    // 添加卫星图层
    _Satellite: function () {
      this.Satellite = new AMap.TileLayer.Satellite()
      this.Satellite.setMap(this)
    },
    _RoadNet: function () {
      this.RoadNet = new AMap.TileLayer.RoadNet()
      this.RoadNet.setMap(this)
    },
    _removeSatelliteRoadNet: function () {
      if (this.Satellite) {
        this.Satellite.setMap(null)
      }
      if (this.RoadNet) {
        this.RoadNet.setMap(null)
      }
    }
  }

  // id,name可穿空字符串
  gaodeMap.Marker = function (map, opts) {
    let markerOpts = {
      icon: '',
      title: '', // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      size: new AMap.Size(27, 27),
      position: { lng: '', lat: '' },
      extData: {},
      isFresh: false, // 是否刷新地图
      editabled: false,
      isCluster: true,
      zIndex: 100,
      offset: new AMap.Pixel(-13, -13)
    }
    markerOpts = $.extend({}, markerOpts, opts)
    if (opts.position) {
      markerOpts.position = new gaodeMap.Point(opts.position.lng, opts.position.lat)
    }
    if (opts.size) {
      markerOpts.size = new AMap.Size(opts.size.width, opts.size.height)
    }
    if (opts.offset) {
      markerOpts.offset = new AMap.Pixel(opts.offset.x, opts.offset.y)
    }
    this.extData = markerOpts.extData

    markerOpts.icon = new AMap.Icon({
      size: markerOpts.size,
      image: markerOpts.icon,
      imageSize: markerOpts.size
    })
    if (!markerOpts.isCluster) {
      markerOpts.map = map
    }
    let marker = new AMap.Marker(markerOpts)
    this.protoSelf = marker
    this.map = map
    this.visible = true
    marker = $.extend(marker, this)
    // marker._add()
    return marker
  }

  gaodeMap.Marker.prototype = {
    _getPosition: function () {
      return this.getPosition()
    },
    _setPosition: function (lnglat) {
      return this.setPosition(new AMap.LngLat(lnglat[0], lnglat[1]))
    },
    _setLabel: function (label) {
      let opts = {
        offset: new AMap.Pixel(0, 0),
        content: label.content
      }
      opts = $.extend({}, opts, label)
      this.setLabel(opts)
    },
    _getIcon: function () {
      let icon = this.getIcon().F.image
      return icon
    },
    _setIcon: function (icon) {
      /*
      let beforeIcon = this._getIcon();
      let icon;
      let opts = {
          icon:beforeIcon.getSrc(),
          offset:beforeIcon.getOffset(),
          size:beforeIcon.getSize()
      };
      if(typeof iconOpts == "string"){
          opts.iconSrc = iconOpts;
      }
      icon =  new AMap.Icon(opts.icon,
          new AMap.Size(opts.size.width, opts.size.height), new AMap.Pixel(opts.offset.x, opts.offset.y));
          */
      this.setIcon(icon)
    },
    _setMap: function (map) {
      this.setMap(map)
    },
    _show: function () {
      this.show()
      this.visible = true
    },
    _hide: function () {
      this.hide()
      this.visible = false
    },
    _add: function () {
      this.visible = true
      if (!this.isAdded) {
        this.setMap(this.map)
        this.isAdded = true
      }
      this._show()
    },
    _remove: function () {
      this.off('click')
      this.setMap(null)
      this.isAdded = false
      this.visible = false
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.on(event, handler)
    },
    _setAnimation: function (animation) {
      this.setAnimation('AMAP_' + animation)
    },
    _infoTipShow: function () {
      // 空方法，兼容openalyer写的
    },
    _infoTipClose: function () {
      // 空方法，兼容openalyer写的
    },
    _getContentDom: function () {
      return this.getContentDom()
    },
    _getId: function () {
      return this.extData.id
    }
  }

  //文字对象
  gaodeMap.Text = function (map, opts) {
    let textOpts = {
      text: '纯文本标记',
      anchor: 'center', // 设置文本标记锚点
      draggable: false,
      cursor: 'pointer',
      angle: 0,
      style: {
        'padding': '0 1rem',
        'margin-bottom': '1rem',
        'border-radius': '.25rem',
        'background-color': 'green',
        // 'width': '20rem',
        'height': '3rem',
        'border-color': 'green',
        'border-width': 1,
        'box-shadow': '0 2px 6px 0 rgba(114, 124, 245, .5)',
        'text-align': 'center',
        'font-size': '20px',
        'color': 'white'
      },
      position: []
    }
    textOpts = $.extend({}, textOpts, opts)
    let text = new AMap.Text(textOpts)
    this.protoSelf = text
    this.map = map
    this.visible = true
    text = $.extend(text, this)
    text._add()
    return text
  }

  gaodeMap.Text.prototype = {
    _getPosition: function () {
      return this.getPosition()
    },
    _setPosition: function (lnglat) {
      return this.setPosition(new AMap.LngLat(lnglat[0], lnglat[1]))
    },
    _setLabel: function (label) {
      let opts = {
        offset: new AMap.Pixel(0, 0),
        content: label.content
      }
      opts = $.extend({}, opts, label)
      this.setLabel(opts)
    },
    _getIcon: function () {
      let icon = this.getIcon().F.image
      return icon
    },
    _setIcon: function (icon) {
      /*
      let beforeIcon = this._getIcon();
      let icon;
      let opts = {
          icon:beforeIcon.getSrc(),
          offset:beforeIcon.getOffset(),
          size:beforeIcon.getSize()
      };
      if(typeof iconOpts == "string"){
          opts.iconSrc = iconOpts;
      }
      icon =  new AMap.Icon(opts.icon,
          new AMap.Size(opts.size.width, opts.size.height), new AMap.Pixel(opts.offset.x, opts.offset.y));
          */
      this.setIcon(icon)
    },
    _setMap: function (map) {
      this.setMap(map)
    },
    _show: function () {
      this.setAnchor('center')
      this.show()
      this.visible = true
    },
    _hide: function () {
      this.hide()
      this.visible = false
    },
    _add: function () {
      this.visible = true
      if (!this.isAdded) {
        this.setMap(this.map)
        this.isAdded = true
      }
      this._show()
    },
    _remove: function () {
      this.off('click')
      this.setMap(null)
      this.isAdded = false
      this.visible = false
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.on(event, handler)
    },
    _setAnimation: function (animation) {
      this.setAnimation('AMAP_' + animation)
    },
    _infoTipShow: function () {
      // 空方法，兼容openalyer写的
    },
    _infoTipClose: function () {
      // 空方法，兼容openalyer写的
    },
    _getExtData: function () {
      return this.getExtData()
    }
  }

  gaodeMap.CircleMarker = function (map, opts) {
    let markerOpts = {
      radius: 20,
      strokeColor: '#FFF600',
      strokeWeight: 2,
      strokeOpacity: 1,
      fillColor: 'rgba(0,0,255,1)',
      fillOpacity: 0,
      zIndex: 10,
      bubble: true,
      cursor: 'pointer',
      clickable: true
    }
    markerOpts = $.extend({}, markerOpts, opts)
    this.extData = markerOpts.extData
    let marker = new AMap.CircleMarker(markerOpts)
    this.protoSelf = marker
    this.map = map
    return $.extend(marker, this)
  }

  gaodeMap.CircleMarker.prototype = {
    _getCenter: function () {
      return this.getCenter()
    },
    _setCenter: function (lnglat) {
      return this.setCenter(lnglat)
    },
    _setRadius: function (radius) {
      return this.setRadius(radius)
    },
    _show: function () {
      this.show()
    },
    _hide: function () {
      this.hide()
    },
    _add: function () {
      this.setMap(this.map)
      this._show()
    },
    _remove: function () {
      this.setMap(null)
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.on(event, handler)
    },
    _setMap: function (map) {
      this._setMap(map)
    }
  }

  gaodeMap.MassMarks = function (map, data, opts) {
    let massMarks = new AMap.MassMarks(data, opts)
    massMarks.setMap(map)
    this.map = map
    return $.extend(massMarks, this)
  }

  gaodeMap.ContextMenu = function (marker) {
    this.marker = marker
    return $.extend(new AMap.ContextMenu(), this) // new AMap.ContextMenu();
  }

  gaodeMap.ContextMenu.prototype = {
    _addItem: function (itemname, handler, index) {
      this.addItem(itemname, handler, index)
    },
    _open: function (map) {
      let position = this.marker.getPosition()
      let pt = new AMap.LngLat(position.lng, position.lat)
      this.open(map, pt)
    }
  }

  // 图标对象
  gaodeMap.Icon = function (imgUrl, size, imageOffset) {
    let opts = { size: new AMap.Size(size[0], size[1]), image: imgUrl }
    if (imageOffset) {
      opts.imageOffset = new AMap.Pixel(imageOffset[0], imageOffset[1])
    }
    return new AMap.Icon(opts)
  }

  // 点对象
  gaodeMap.Point = function (lng, lat) {
    return $.extend(new AMap.LngLat(lng, lat), this)
  }

  gaodeMap.Point.prototype = {
    _getLng: function () {
      return this.getLng()
    },
    _getLat: function () {
      return this.getLat()
    }
  }

  // 信息窗体
  gaodeMap.InfoWindow = function (content, opts) {
    let infoWindowOptions = {
      content: content,
      isCustom: true,
      autoMove: true,
      closeWhenClickMap: opts.enableCloseOnClick,
      offset: new AMap.Pixel(0, 0) //
    }
    infoWindowOptions = $.extend({}, infoWindowOptions, opts)
    if (opts.lnglat) {
      infoWindowOptions.position = new AMap.LngLat(opts.lnglat.lng, opts.lnglat.lat)
    }
    let offset = opts.offset
    if (offset) {
      infoWindowOptions.offset = new AMap.Pixel(offset.x, offset.y)
    }
    let size = opts.size
    if (size) {
      infoWindowOptions.size = new AMap.Size(size.width, size.height)
    }
    return $.extend(new AMap.InfoWindow(infoWindowOptions), this)
  }

  gaodeMap.InfoWindow.prototype = {
    _open: function (map) {
      this.open(map, this.getPosition())
    },
    _close: function () {
      this.close()
    }
  }

  gaodeMap.MapLib = {}

  gaodeMap.MapLib.MouseTool = function (map) {
    this.map = map
    this.mouseTool = null
  }

  gaodeMap.MapLib.MouseTool.prototype = {
    // 测距
    _measurDistance: function () {
      let map = this.map
      let mouseTool = this.mouseTool
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          // 鼠标工具插件添加draw事件监听
          AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
            mouseTool.close()
          })

          me.mouseTool = mouseTool
        }
        mouseTool.rule() // 调用鼠标工具的距离量测功能
      })
    },
    // 测面
    _measurArea: function () {
      let map = this.map
      let mouseTool = this.mouseTool
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          // 鼠标工具插件添加draw事件监听
          AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
            mouseTool.close()
          })

          me.mouseTool = mouseTool
        }
        mouseTool.measureArea() // 调用鼠标工具的面积量测功能
      })
    },
    _clearDrawOverlays: function () {
      if (this.mouseTool) {
        this.mouseTool.close(true)
      }
    },
    // 圈选
    _drawCircle: function (cb) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#F6A21C',
        fillColor: '#F6A21C',
        fillOpacity: 0.3,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          // let _overlay = new gaodeMap.Overlay(e.obj);
          let obj = e.obj
          let center = obj.getCenter()
          let path = gaodeMap.gmUtil.getLatLonByDis(obj.getRadius() / 1000, center.lat, center.lng)
          obj.flatCoordinates = [path.lng, path.lat]
          cb(obj)
          mouseTool.close()
          // ZnvMap.batchDeal();
        })
        mouseTool.circle(rectOptions)
      })
    },
    // 多边形
    _drawPolygon: function (cb) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#008BCA',
        fillColor: '#00AEFF',
        fillOpacity: 0.2,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          // let _overlay = new gaodeMap.Overlay(e.obj);
          let obj = e.obj
          let path = obj.getPath()
          obj.flatCoordinates = me.toFlatCoordinates(path)
          cb(obj)
          mouseTool.close()
          // ZnvMap.batchDeal();
        })
        mouseTool.polygon(rectOptions)
      })
    },
    // 线选
    _drawLine: function (cb) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#008BCA',
        fillColor: '#00AEFF',
        fillOpacity: 0.2,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          mouseTool.close()
          // let _overlay = new gaodeMap.Overlay(e.obj);
          let obj = e.obj
          let path = obj.getPath()
          let lines = []
          for (let i = 0; i < path.length; i++) {
            let item = path[i]
            lines.push([item.lng, item.lat])
          }
          obj.linePoints = lines
          obj.flatCoordinates = me.toFlatCoordinates(path)
          cb(obj)
          // ZnvMap.batchDeal();
        })
        mouseTool.polyline(rectOptions)
      })
    },
    toFlatCoordinates(path) {
      let lines = []
      for (let i = 0; i < path.length; i++) {
        let item = path[i]
        lines.push(item.lng, item.lat)
      }
      return lines
    },
    // 点选
    _drawPoint: function (cb) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#008BCA',
        fillColor: '#00AEFF',
        fillOpacity: 0.2,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          mouseTool.close()
          // let _overlay = new gaodeMap.Overlay(e.obj);
          let obj = e.obj
          let path = [obj.getPosition()]
          let lines = []
          for (let i = 0; i < path.length; i++) {
            let item = path[i]
            lines.push([item.lng, item.lat])
          }
          obj.linePoints = lines
          cb(obj)
          // ZnvMap.batchDeal();
        })
        let icon = new AMap.Icon({
          image: './static/img/icon/circle.png',
          size: new AMap.Size(10, 10),
          imageSize: new AMap.Size(10, 10)
        })
        mouseTool.marker({
          offset: new AMap.Pixel(-5, -5),
          icon: icon
        })
        // mouseTool.point(rectOptions);
      })
    },
    // 框选
    _drawRectangle: function (cb) {
      let map = this.map
      let mouseTool = this.mouseTool
      let me = this
      let rectOptions = {
        strokeColor: '#F6A21C',
        fillColor: '#F6A21C',
        fillOpacity: 0.3,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function (e) {
          // let _overlay = new gaodeMap.Overlay(e.obj);
          let obj = e.obj
          let path = obj.getPath()
          obj.flatCoordinates = me.toFlatCoordinates(path)
          cb(obj)
          mouseTool.close()
          // ZnvMap.batchDeal();
        })
        mouseTool.rectangle(rectOptions)
      })
    }
  }

  gaodeMap.Overlay = function (overlay) {
    this._obj = overlay
  }

  gaodeMap.Overlay.prototype = {
    _containPoint: function (point) {
      let overlay = this._obj
      if (overlay.contains) {
        return overlay.contains(point)
      } else {
        return false
      }
    }
  }

  gaodeMap.MapLib.MarkerClusterer = function (map, markers, opts) {
    this.map = map
    markers = markers || []
    opts = opts || {}
    let me = this
    let clustererMarkerMap = {}
    for (let i = 0; i < markers.length; i++) {
      let pmarkerMap = markers[i]
      clustererMarkerMap[pmarkerMap._getId()] = pmarkerMap
    }
    me.clustererMarkerMap = clustererMarkerMap
    if (opts.minimumClusterSize) {
      opts.minClusterSize = opts.minimumClusterSize
    }
    map.plugin(['AMap.MarkerClusterer'], function () {
      me.cluster = new AMap.MarkerClusterer(map.protoSelf, markers, opts)
    })
  }

  gaodeMap.MapLib.MarkerClusterer.prototype = {
    _addToMarkerMap: function (marker) {
      this.clustererMarkerMap[marker._getId()] = marker
    },
    _deleteFromMarkerMap: function (marker) {
      delete this.clustererMarkerMap[marker._getId()]
    },
    _isMarkerExisted: function (marker) {
      if (this.clustererMarkerMap[marker._getId()]) {
        return true
      } else {
        return false
      }
    },
    _complete: function (callback) {
      let self = this
      let intervalId = setInterval(function () {
        if (self.cluster) {
          clearInterval(intervalId)
          callback.call(self)
        }
      }, 200)
    },
    _setMarkers: function (markers) {
      if (this.cluster) {
        this.cluster.setMarkers(markers)
      }
    },
    _addMarker: function (marker) {
      if (!this._isMarkerExisted(marker)) {
        this.cluster.addMarker(marker)
        this._addToMarkerMap(marker)
      }
    },
    _removeMarker: function (marker) {
      this.cluster.removeMarker(marker)
      this._deleteFromMarkerMap(marker)
    },
    _removeMarkers: function (markers) {
      this.cluster.removeMarkers(markers)
      for (let i = 0; i < markers.length; i++) {
        // this.cluster.removeMarker(markers[i]);
        this._deleteFromMarkerMap(markers[i])
      }
      this.cluster.refresh()
    },
    _addMarkers: function (markers) {
      let selfMarkers = []
      for (let i = 0; i < markers.length; i++) {
        if (!this._isMarkerExisted(markers[i])) {
          selfMarkers.push(markers[i])
          this._addToMarkerMap(markers[i])
        }
      }
      if (selfMarkers.length > 0) {
        this.cluster.addMarkers(selfMarkers)
      }
    },
    _clearMarkers: function () {
      this.cluster.clearMarkers()
      this.clustererMarkerMap = {}
    },
    _setMinClusterSize: function (minmumClusterSize, isRedraw) {
      this.cluster.setMinClusterSize(minmumClusterSize, isRedraw)
    }
  }

  // 地理编码与逆地理编码类，用于地址描述与坐标之间的转换
  gaodeMap.MapLib.Geocoder = function (map, opts) {
    opts = opts || {}
    map.plugin(['AMap.Geocoder'], () => {
      this.geocoder = new AMap.Geocoder(opts)
    })
  }
  gaodeMap.MapLib.Geocoder.prototype = {
    _getLocation: function (address, callback) {
      this.geocoder.getLocation(address, callback)
    }
  }

  gaodeMap.moveAlong = function (map, lineArr, opts) {
    // debugger
    this.marker = new AMap.Marker({
      map: map,
      content: `<div style=" width: 10px;
      height: 10px;
      border-radius: 50%;
      margin: 2px 6px 0 0;
      background: #00bef7;"></div>`,
      offset: new AMap.Pixel(0, 0),
      autoRotation: true,
      angle: -90
    })
    let lnglatarr = []
    for (let i = 0; i < lineArr.length; i++) {
      let lnglat = lineArr[i]
      lnglatarr.push([lnglat[0], lnglat[1]])
    }
    // 绘制轨迹
    this.polyline = new AMap.Polyline({
      map: map,
      path: lnglatarr,
      showDir: true,
      strokeColor: '#28F', //线颜色
      // strokeOpacity: 1,     //线透明度
      strokeWeight: 6 //线宽
      // strokeStyle: "solid"  //线样式
    })
    console.log(lnglatarr)
    this.passedPolyline = new AMap.Polyline({
      map: map,
      // path: lineArr,
      strokeColor: '#AF5', //线颜色
      // strokeOpacity: 1,     //线透明度
      strokeWeight: 6 //线宽
      // strokeStyle: "solid"  //线样式
    })
    // map.setFitView()
    // this.polyline.setPath(lnglatarr)
    let thef = this
    this.marker.on('moving', function (e) {
      thef.passedPolyline.setPath(e.passedPath)
    })
    this.marker.moveAlong(lnglatarr, 200)
    // return this.marker
    return $.extend(this)
  }
  gaodeMap.moveAlong.prototype = {
    _remove: function () {
      this.marker.stopMove()
      this.marker.hide()
      this.polyline.setMap(null)
      this.passedPolyline.setMap(null)
    }
  }
  // 轨迹
  gaodeMap.Polyline = function (map, lineArr, opts) {
    let PolylineOpts = {
      map: map,
      path: lineArr,
      strokeColor: 'red', // 线颜色
      // strokeOpacity: 1, // 线透明度
      strokeWeight: 3 // 线宽
      // strokeStyle: "solid"  // 线样式
    }
    let lnglatarr = []
    for (let i = 0; i < lineArr.length; i++) {
      let lnglat = lineArr[i]
      lnglatarr.push([lnglat.lng, lnglat.lat])
    }
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
    }
    PolylineOpts.path = lnglatarr
    let polyline = new AMap.Polyline(PolylineOpts)
    this.map = map
    this.protoSelf = polyline
  }
  gaodeMap.Polyline.prototype = {
    _setPath: function (lineArr) {
      this.setPath(lineArr)
    },
    _remove: function () {
      this.protoSelf.setMap(null)
    }
  }
  gaodeMap.Heatmap = function (opts) {
    // 高德地图此功能需要支持canvas的浏览器
    let opt = {
      url: '',
      blur: 10,
      radius: 10,
      field: ''
    }
    opt = $.extend({}, opt, opts)
    var vector
    opt.map.plugin(['AMap.Heatmap'], function () {
      vector = new AMap.Heatmap(opt.map, {
        radius: 25, // 给定半径
        opacity: [0, 0.8],
        blur: opt.blur
        /*,
        gradient:{
            0.5: 'blue',
            0.65: 'rgb(117,211,248)',
            0.7: 'rgb(0, 255, 0)',
            0.9: '#ffea00',
            1.0: 'red'
        }
         */
      })
      vector.setDataSet({
        data: opt.dataSource,
        max: 100
      })
    })
    return $.extend(vector, this)
  }

  // 多变形
  gaodeMap.Polygon = function (map, opt, isShow) {
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
    let lnglatarr = []
    for (let i = 0; i < opt.path.length; i++) {
      let lnglat = opt.path[i]
      lnglatarr.push(new AMap.LngLat(lnglat[0], lnglat[1]))
    }
    if (isShow) {
      var outer = [
        new AMap.LngLat(-360, 90, true),
        new AMap.LngLat(-360, -90, true),
        new AMap.LngLat(360, -90, true),
        new AMap.LngLat(360, 90, true)
      ]
      var pathArray = [outer]
      pathArray.push(lnglatarr)
      defOpts.path = pathArray
    } else {
      defOpts.path = lnglatarr
    }

    let polygon = new AMap.Polygon(defOpts)
    this.map = map
    this.protoSelf = polygon
  }
  gaodeMap.Polygon.prototype = {
    hide: function () {
      this.protoSelf.hide()
    },
    show: function () {
      this.protoSelf.show()
    },
    remove: function () {
      this.protoSelf.setMap(null)
    },
    contains: function (lng, lat) {
      return this.protoSelf.contains(new gaodeMap.Point(lng, lat))
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.protoSelf.on(event, handler)
    },
    _getExtData: function () {
      return this.protoSelf.getExtData()
    },
    _setOptions: function (fillOpacity1, fillColor1) {
      this.protoSelf.setOptions({
        fillOpacity: fillOpacity1,
        fillColor: fillColor1
      })
    }
  }
  gaodeMap.PathSimplifier = function (map, lineArr) {
    AMapUI.load(['ui/misc/PathSimplifier', 'lib/$'], function (PathSimplifier, $) {
      if (!PathSimplifier.supportCanvas) {
        console.log('当前环境不支持 Canvas！')
        return
      }
      let pathSimplifierIns = new PathSimplifier({
        zIndex: 100,
        // autoSetFitView:false,
        map: map, // 所属的地图实例

        getPath: function (pathData, pathIndex) {
          let points = pathData.points
          let lnglatList = []

          for (let i = 0, len = points.length; i < len; i++) {
            lnglatList.push(points[i].lnglat)
          }
          return lnglatList
        },
        getHoverTitle: function (pathData, pathIndex, pointIndex) {
          if (pointIndex >= 0) {
            // point
            return pathData.name + '，' + pathData.points[pointIndex].name
          }
          return pathData.name + '，点数量' + pathData.points.length
        },
        renderOptions: {
          renderAllPointsIfNumberBelow: 100 // 绘制路线节点，如不需要可设置为-1
        }
      })
      window.pathSimplifierIns = pathSimplifierIns
      let lnglatarr = []
      for (let i = 0; i < lineArr.length; i++) {
        let lnglat = lineArr[i]
        lnglatarr.push({ name: lnglat.name, lnglat: [lnglat.lng, lnglat.lat] })
      }
      // [
      //   {
      //     name: '点a',
      //     lnglat: [116.405289, 39.904987]
      //   },
      //   {
      //     name: '点b',
      //     lnglat: [113.964458, 40.54664]
      //   },
      //   {
      //     name: '点c',
      //     lnglat: [111.47836, 41.135964]
      //   },
      //   {
      //     name: '点d',
      //     lnglat: [108.949297, 41.670904]
      //   },
      //   {
      //     name: '点e',
      //     lnglat: [106.380111, 42.149509]
      //   },
      //   {
      //     name: '点f',
      //     lnglat: [103.774185, 42.56996]
      //   },
      //   {
      //     name: '点g',
      //     lnglat: [101.135432, 42.930601]
      //   },
      //   {
      //     name: '点h',
      //     lnglat: [98.46826, 43.229964]
      //   },
      //   {
      //     name: '点i',
      //     lnglat: [95.777529, 43.466798]
      //   },
      //   {
      //     name: '点j',
      //     lnglat: [93.068486, 43.64009]
      //   },
      //   {
      //     name: '点k',
      //     lnglat: [90.34669, 43.749086]
      //   },
      //   {
      //     name: '点l',
      //     lnglat: [87.61792, 43.793308]
      //   }
      // ]
      // 设置数据
      pathSimplifierIns.setData([
        {
          name: '路线0',
          points: lnglatarr
        }
      ])

      // 选中路线0
      pathSimplifierIns.setSelectedPathIndex(0)

      pathSimplifierIns.on('pointClick', function (e, info) {
        console.log('Click: ' + info.pathData.points[info.pointIndex].name)
      })
    })
  }
  // 矢量圆形标记
  gaodeMap.Circle = function (map, opt) {
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

    console.log(opt)

    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }

    let circle = new AMap.Circle(defOpts)
    this.map = map
    this.protoSelf = circle
    circle = $.extend(circle, this)
    circle._add()
    return circle
  }
  gaodeMap.Circle.prototype = {
    _getCenter: function () {
      return this.getCenter()
    },
    _getRadius: function () {
      return this.getRadius()
    },
    _show: function () {
      this.show()
    },
    _hide: function () {
      this.hide()
    },
    _add: function () {
      this.setMap(this.map)
    },
    _remove: function () {
      this.setMap(null)
    },
    _contains: function (lngLat) {
      return this.contains(lngLat)
    }
  }
  // 矢量圆形标记编辑
  gaodeMap.CircleEditor = function (map, circle) {
    map.plugin(['AMap.CircleEditor'], () => {
      this.circleEditor = new AMap.CircleEditor(map, circle)
      this.circleEditor.open()
    })
  }
  gaodeMap.CircleEditor.prototype = {
    _close: function () {
      this.circleEditor && this.circleEditor.close()
    },
    _open: function () {
      this.circleEditor && this.circleEditor.open()
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.circleEditor && this.circleEditor.on(event, handler)
    }
  }
  // 工具类
  gaodeMap.gmUtil = {
    distance: function (p0, p1) {
      return AMap.GeometryUtil.distance(p0, p1)
    },
    distanceToLine: function (p0, p1s) {
      return AMap.GeometryUtil.distanceToLine(p0, p1s)
    },
    getLatLonByDis(distance, lat1, lon1) {
      let deg2rad = (deg) => deg * (Math.PI / 180)
      let R = 6371 // Radius of the earth in km
      let tan2 = Math.pow(Math.tan(distance / (2 * R)), 2)
      let a = tan2 / (tan2 + 1)
      let b = a / (Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat1)))
      let dLong = Math.asin(Math.sqrt(b)) * 2
      let lon2 = lon1 + dLong / (Math.PI / 180)
      return { lng: lon2, lat: lat1 }
    }
  }
  // 热力图
  gaodeMap.Heatmap = function (opt, map) {
    // 计算热力数据
    let heatmapdata = []
    axios
      .get(opt.url)
      .then((res) => {
        let resdata = res.data.features
        if (resdata && resdata.length > 0) {
          res.data.features.forEach(function (item) {
            let temp = {
              lng: item.geometry.coordinates[0],
              lat: item.geometry.coordinates[1],
              count: item.geometry.alarm
            }
            heatmapdata.push(temp)
          })
        }
        var heatmapOverlay
        var cfg = {
          radius: opt.radius,
          opacity: [0, 0.8]
        }
        var self = this
        this.map = map
        if (map && !heatmapOverlay) {
          map.plugin(['AMap.Heatmap'], function () {
            heatmapOverlay = new AMap.Heatmap(map, cfg)
            self.protoSelf = heatmapOverlay
            heatmapOverlay.setDataSet({
              data: heatmapdata,
              max: 100
            })
          })
        }
      })
      .catch((e) => { })
  }
  gaodeMap.Heatmap.prototype = {
    toggleShow: function () {
      this.protoSelf.show()
    },
    toggleHide: function () {
      this.protoSelf.hide()
    }
  }
  window.gaodeMap = gaodeMap
  return gaodeMap
}

export default gaodeMap
