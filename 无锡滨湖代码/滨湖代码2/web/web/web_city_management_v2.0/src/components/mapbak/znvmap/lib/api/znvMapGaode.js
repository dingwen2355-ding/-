import $ from 'jquery'
import axios from '@/utils/request';
function gaodeMap(aMapUIMap) {
  let AMap = window.AMap
  let gaodeMap = {}
  let SimpleMarker = aMapUIMap.SimpleMarker
  gaodeMap.Constants = {
    ZOOM_CHANGED: 'zoomchange',
    ZOOM_END: 'zoomend',
    DRAG_END: 'dragend',
    CLICK: 'click', // 单击事件
    DBLCLICK: 'dblclick', // 双击事件
    RIGHTCLICK: 'rightclick',
    LOADED: 'complete', // 地图加载完成
    MOUSEMOVE: 'mousemove', // 鼠标在地图区域移动过程中触发此事件
    MOUSEOVER: 'mouseover',
    MOUSEOUT: 'mouseout'
  }
  // 提供wgs84、百度坐标与高德坐标的转换
  gaodeMap.Coordinate = {
    WGS84: 'gps',
    // GOOGLE: 3,
    BAIDU: 'baidu'
  }
  gaodeMap.MapStyles = {
    normal: 'amap://styles/normal',
    dark: 'amap://styles/dark',
    blue: 'amap://styles/blue'
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
    let defOpts = {
      isHotspot: false,
      expandZoomRange: true,
      zooms: [3, 20],
      // center: [116.39, 39.9],
      zoom: 12
    }
    defOpts = $.extend({}, defOpts, opts)
    if (opts && opts.maxZoom) {
      defOpts.zooms[1] = defOpts.maxZoom
    }
    if (opts && opts.minZoom) {
      defOpts.zooms[0] = defOpts.minZoom
    }
    if (opts && opts.center) {
      defOpts.center = [opts.center.lng, opts.center.lat]
    }
    let protoMap = new window.AMap.Map(mapDiv, defOpts)
    let map = $.extend(protoMap, this)
    map.protoSelf = protoMap
    // map.setMapStyle('blue_night');
    map.on('complete', function () {
      // 隐藏高德logo
      $('.amap-logo').css('z-index', '-1')
      $('.amap-copyright').css('z-index', '-1')
      $('#tagmask').css('display', 'none')
      if (opts && opts.mapComplete) {
        opts.mapComplete()
      }
    })
    return map
  }

  /**
   map接口
   */
  gaodeMap.Map.prototype = {
    _transformPointToOL(v) {
      return v
    },
    _clear: function () {
      this.protoSelf.clearMap()
    },
    _centerAndZoom: function (point, zoom) {
      if (point[0] < -180 || point[0] > 180 || point[1] < -85 || point[1] > 85) {
        return false
      }
      this.protoSelf.setZoomAndCenter(zoom, point)
    },
    _center: function (point) {
      let pt = new AMap.LngLat(point.lng, point.lat)
      this.setCenter(pt)
    },
    // _setBounds: function (polygon) {
    //   let bounds = polygon.getBounds()
    //   this.setBounds(bounds)
    // },
    /*
     *设置地图当前屏幕可视范围
     */
    _setView: function (polygon) {
      let bounds = polygon.getBounds()
      this.setBounds(bounds)
    },
    _getCenter: function () {
      return this.getCenter()
    },
    _getZoom: function () {
      return this.getZoom()
    },
    _getView: function () {
      // var bds = this.getBounds()
      // var northeast = [bds['northeast'].lng, bds['northeast'].lat]
      // var southwest = [bds['southwest'].lng, bds['southwest'].lat]
      // return { 'northeast': northeast, 'southwest': southwest }
      // let ext = {'northeast': northeast, 'southwest': southwest}
      // return ext
      return this.getBounds()
    },
    _zoom: function (zoom) {
      this.setZoom(zoom)
    },
    _addOverlay: function (overlays) { // 将Maker添加到地图中
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
      let mouseEvents = ['mousemove', 'click', 'dblclick', 'rightclick']
      if (mouseEvents.indexOf(event) >= 0) {
        this.on(event, function (e) {
          let point = new gaodeMap.Point(e.lnglat.lng, e.lnglat.lat)
          handler(point)
        })
      } else {
        // 其他事件，例如complete
        this.on(event, function (e) {
          handler(e)
        })
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
    }
  }

  // id,name可穿空字符串
  gaodeMap.Marker = function (map, opts) {
    this.map = map
    this.id = opts.id
    this.layerName = opts.layerName
    this.type = opts.type
    this.extData = opts.extData
    this.icon = opts.icon || 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'
    let markerOpts = {
      icon: 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png',
      title: '', // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      size: new AMap.Size(27, 27),
      // position: {lng: '', lat: ''},
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
    return ($.extend(marker, this))
  }

  gaodeMap.Marker.prototype = {
    _getPosition: function () {
      return this.getPosition()
    },
    _setPosition: function (pos) {
      if (pos) {
        let pt = new AMap.LngLat(pos.lng || pos[0], pos.lat || pos[1])
        this.setPosition(pt)
      }
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
      let icon = this.getIcon()
      if (this.getIcon().F) {
        icon = this.getIcon().F.image
      }
      return icon
    },
    _setIcon: function (iconOpts) {
      if (typeof iconOpts == 'string') {
        this.setIcon(iconOpts) // setIcon(content:String/Icon)
      } else {
        if (iconOpts.iconSrc) {
          iconOpts.icon = iconOpts.iconSrc
          let icon = new AMap.Icon(iconOpts.icon,
            new AMap.Size(iconOpts.size.iconOpts, iconOpts.size.height),
            new AMap.Pixel(iconOpts.offset.x, iconOpts.offset.y))
          this.setIcon(icon)
        }
      }
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
      let m = this
      this.on(event, function (e) {
        handler(m, e)
      })
    },
    _setAnimation: function (animation) {
      this.setAnimation('AMAP_' + animation)
    },
    _infoTipShow: function () {
      // 空方法，兼容openalyer写的
    },
    _infoTipClose: function () {
      // 空方法，兼容openalyer写的
    }
  }

  gaodeMap.MassMarks = function (map, data, opts) {
    let massMarks = new AMap.MassMarks(data, opts)
    massMarks.setMap(map)
    this.map = map
    return ($.extend(massMarks, this))
  }

  // 右键菜单 marker可以是map，也可以是marker
  gaodeMap.ContextMenu = function (marker) {
    this.marker = marker
    return ($.extend(new AMap.ContextMenu(), this))
  }

  gaodeMap.ContextMenu.prototype = {
    _addItem: function (itemname, handler, index) {
      this.addItem(itemname, handler, index)
    },
    _open: function (map, e) {
      let position = e.lnglat || e // 点击Marker，取值e.lnglat；点击map，取值e
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
    return ($.extend(new AMap.LngLat(lng, lat), this))
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
    return ($.extend(new AMap.InfoWindow(infoWindowOptions), this))
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
    _measureDistance: function (opt) {
      let map = this.map
      let mouseTool = this.mouseTool
      if (mouseTool) {
        // 清除地图上前一次的绘制信息
        mouseTool.close(true)
      }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          // 鼠标工具插件添加draw事件监听
          // AMap.event.addListenerOnce(mouseTool, 'draw', function callback (e) {
          AMap.event.addListener(mouseTool, 'draw', function callback(e) {
            mouseTool.close()
          })

          me.mouseTool = mouseTool
        }
        mouseTool.rule() // 调用鼠标工具的距离量测功能
      })
    },
    // 测面
    _measureArea: function () {
      let map = this.map
      let mouseTool = this.mouseTool
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          // 鼠标工具插件添加draw事件监听
          // AMap.event.addListenerOnce(mouseTool, 'draw', function callback (e) {
          AMap.event.addListener(mouseTool, 'draw', function callback(e) {
            mouseTool.close()
          })
          me.mouseTool = mouseTool
        }
        mouseTool.measureArea() // 调用鼠标工具的面积量测功能
      })
    },
    _clearDrawOverlays: function (geo) {
      if (this.mouseTool) {
        this.mouseTool.close(true)
      }
      if (geo) {
        geo.forEach((item) => {
          this.map.removeOverlay(item)
        })
      }
    },
    // 画圈
    _drawCircle: function (cb, opts) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#F6A21C',
        fillColor: '#F6A21C',
        fillOpacity: 0.3,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      rectOptions = $.extend({}, rectOptions, opts)
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          let obj = e.obj
          let center = obj.getCenter()
          let path = gaodeMap.gmUtil.getLatLonByDis(obj.getRadius() / 1000, center.lat, center.lng)
          obj.flatCoordinates = [path.lng, path.lat]
          obj.znvCoordinates = [[center.lat, center.lng], [path.lng, path.lat]]
          cb(obj)
          mouseTool.close()
        })
        mouseTool.circle(rectOptions)
      })
    },
    // 多边形
    // enableCalculate 是否开启面积测量
    _drawPolygon: function (cb, enableCalculate, opts) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#008BCA',
        fillColor: '#00AEFF',
        fillOpacity: 0.2,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      rectOptions = $.extend({}, rectOptions, opts)
      let me = this
      if (mouseTool) {
        // 清除地图上前一次的绘制信息
        mouseTool.close(true)
      }
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
          obj.znvCoordinates = me.toFlatCoordinates(path)
          cb(obj)
          mouseTool.close()
        })
        if (enableCalculate) {
          // 测面积
          mouseTool.measureArea(rectOptions)
        } else {
          // 绘制多边形
          mouseTool.polygon(rectOptions)
        }
      })
    },
    // 绘制线
    _drawLine: function (cb, opts) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#008BCA',
        fillColor: '#00AEFF',
        fillOpacity: 0.2,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      rectOptions = $.extend({}, rectOptions, opts)
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
          obj.znvCoordinates = me.toFlatCoordinates(path)
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
        // lines.push(item.lng, item.lat)
        lines.push([item.lng, item.lat])
      }
      return lines
    },
    // 画点
    _drawPoint: function (cb, opts) {
      let map = this.map
      let mouseTool = this.mouseTool
      // let rectOptions = {
      //   strokeColor: '#008BCA',
      //   fillColor: '#00AEFF',
      //   fillOpacity: 0.2,
      //   strokeOpacity: 1,
      //   strokeWeight: 2
      // }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          mouseTool.close()
          let obj = e.obj
          let path = [obj.getPosition()]
          let lines = []
          for (let i = 0; i < path.length; i++) {
            let item = path[i]
            lines.push([item.lng, item.lat])
          }
          obj.linePoints = lines
          obj.znvCoordinates = lines
          cb(obj)
        })
        let icon = new AMap.Icon({
          image: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACcAAAAZCAYAAACy0zfoAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyRpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYxIDY0LjE0MDk0OSwgMjAxMC8xMi8wNy0xMDo1NzowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNS4xIE1hY2ludG9zaCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoxREY5RkI2RkE1RTkxMUUwODI2REU5NkQ5QTFGNjdFMSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDoxREY5RkI3MEE1RTkxMUUwODI2REU5NkQ5QTFGNjdFMSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjFERjlGQjZEQTVFOTExRTA4MjZERTk2RDlBMUY2N0UxIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjFERjlGQjZFQTVFOTExRTA4MjZERTk2RDlBMUY2N0UxIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+kU8h2AAAA3dJREFUeNrMl0toFEEQhqtnZh9unm5MxEQRNQ8IHnwe9JJAQLx5NBc9COJB8aSgN4+KOejdo6go4knwIhIJPlEUUS9RYkxissnmsTGZ3Z2dGf9aK2HYTLK7SUZs+NM9zHbn66qu6hrlui4VtpdN+w10tdBmqBUKQ3PQF2hKlG+Hht9RUM3wAWtDdwo6FtHdtpDuksID/83YRJatXuremIXQXGqIAm1qwnFjrOHStvtJtqm6MU7ShjigaJSeRIHdyklzTpHRO0WxWo0lTe4vfXoTlev8F3Hl0PTta46Gqrg5yk0nKffxIzsSE70QGHJnVExh2A/BZYHAAO4rxvZY9TTWxI11krem1K9sBA0ckC+A3DowDsX284DWAN6C9srTdqYl2dlH38uCQwblVhhxoq7F0YnsM64XWHg/ZBnTV728nq6yNndLSsBWojDndnoE1BwHXUx2xd6TrZ/eV7RiGUqyNOlDcYBNzuSEWY7KHVZ4WokQ+qA0HAOcoIle1Ob5OAt4OAI+c3kr/j0P/WGM5Q5K4tH6l8FwoC7n3aVmtaxLTy818EAffctLRVL+C4+WQ8jWFfEBf/5zlL9UIdFaHy3TuTyW/sPpRUShUzxIKUaHGPBVqEG4FuDqWMgy1xK6aV4WETRUBiTuf5N3B9ZWgpnCblFp/HDVAlFOPsA+kCmIOyUpKl+FaEMpwEFkqmR9CtqbR2vm5DaVHLNh6cMSx0lwH21QcqKjXhNqkLY2KMQsu5Il5rhpeFvvPevfXc1Yl5/TAsd2BjtDhgcp43TrehBwWv+AVqLeK6cEsB1Iq5XKzLv+VSKO2d8As6CTeNz1sr+3Ya05Km9gbDS7Ca6c0qUI3c1ztlHC4BzOsQU9y8ZBK75+zPlOHknOXTxticPiyXfcInwNiFcShScOhXDHqBGhUGPnuO347YTVcmTX3JCxtLDKYMPrgXoA/L7DwN/ZYzVMxKOQmEcegT17wCmPP9hpB2HcHRbmhud9wTIMm/wD1cmC4zj+/XMXFlM1QvkVqYNrKyiQkJgHGxnCXQtBIcT7w0Pq83KwkQjAnAdzhwSjgzPyQd7JKgiAh4RqzKJdCwWC2zYKmiX1+exv/gBALkSdZW2xEEr/B8WsCLuSst+ZNBp+UcJgUoJUCWN+H63tkvGveVGkW+h9v73ar8k3BIlBOgkkurPwIMAGoCSg1/fYeDAAAAAElFTkSuQmCC',
          size: new AMap.Size(39, 25),
          imageSize: new AMap.Size(39, 25)
        })
        // let icon = new AMap.Icon({
        //   size: new AMap.Size(0, 0),
        //   imageSize: new AMap.Size(0, 0)
        // })
        mouseTool.marker({
          offset: new AMap.Pixel(-10, -25),
          icon: icon
        })
      })
    },
    // 画框
    _drawRectangle: function (cb, opts) {
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
      rectOptions = $.extend({}, rectOptions, opts)
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
          obj.znvCoordinates = me.toFlatCoordinates(path)
          cb(obj)
          mouseTool.close()
          // ZnvMap.batchDeal();
        })
        mouseTool.rectangle(rectOptions)
      })
    },
    // 圈选
    _selectCircle: function (markers, cb, opts) {
      let map = this.map
      let mouseTool = this.mouseTool
      let rectOptions = {
        strokeColor: '#F6A21C',
        fillColor: '#F6A21C',
        fillOpacity: 0.3,
        strokeOpacity: 1,
        strokeWeight: 2
      }
      if (opts) {
        rectOptions = $.extend({}, rectOptions, opts)
      }
      let me = this
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function callback(e) {
          let obj = e.obj
          mouseTool.close()
          let resultMarkers = []
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (obj.contains(pt)) {
              resultMarkers.push(markers[i])
            }
          }
          map.remove(obj)
          cb(resultMarkers)
        })
        mouseTool.circle(rectOptions)
      })
    },
    // 框选
    _selectRectangle: function (markers, cb, opts) {
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
      if (opts) {
        rectOptions = $.extend({}, rectOptions, opts)
      }
      map.plugin(['AMap.MouseTool'], function () {
        if (!mouseTool) {
          mouseTool = new AMap.MouseTool(map)
          me.mouseTool = mouseTool
        }
        AMap.event.addListenerOnce(mouseTool, 'draw', function (e) {
          let obj = e.obj
          mouseTool.close()
          let resultMarkers = []
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (obj.contains(pt)) {
              resultMarkers.push(markers[i])
            }
          }
          map.remove(obj)
          cb(resultMarkers)
        })
        mouseTool.rectangle(rectOptions)
      })
    },
    // 多边形选
    _selectPolygon: function (markers, cb, opts) {
      console.log('_selectPolygon方法没实现')
    },
    // 关闭工具
    _closeTool: function () {
      if (this.mouseTool) {
        this.mouseTool.close()
        this.mouseTool = null
      }
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
      clustererMarkerMap[pmarkerMap.getId()] = pmarkerMap
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
      this.clustererMarkerMap[marker.getId()] = marker
    },
    _deleteFromMarkerMap: function (marker) {
      delete this.clustererMarkerMap[marker.getId()]
    },
    _isMarkerExisted: function (marker) {
      if (this.clustererMarkerMap[marker.getId()]) {
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
    /*
     *动态向聚合添加Marker
     */
    _addMarker: function (marker) {
      if (!this._isMarkerExisted(marker)) {
        this.cluster.addMarker(marker)
        this._addToMarkerMap(marker)
      }
    },
    /*
     *动态从聚合移除Marker
     */
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
    setMinClusterSize: function (minmumClusterSize, isRedraw) {
      this.cluster.setMinClusterSize(minmumClusterSize, isRedraw)
    }
  }
  // 轨迹
  gaodeMap.Polyline = function (map, lineArr, opts) {
    let PolylineOpts = {
      map: map,
      path: lineArr,
      strokeColor: '#00A', // 线颜色
      strokeOpacity: 1, // 线透明度
      strokeWeight: 3, // 线宽
      strokeStyle: 'solid' // 线样式
    }
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
    }
    let lnglatarr = []
    for (let i = 0; i < lineArr.length; i++) {
      let lnglat = lineArr[i]
      lnglatarr.push([lnglat.lng, lnglat.lat])
    }
    PolylineOpts.path = lnglatarr
    let polyline = new AMap.Polyline(PolylineOpts)
    if (PolylineOpts.isFresh) {
      let bounds = polyline.getBounds && polyline.getBounds()
      let center = bounds && bounds.getCenter && bounds.getCenter()
      if (center) {
        map._center(center)
      }
    }
    this.map = map
    this.extData = PolylineOpts.extData
    // this.protoSelf = polyline
    return ($.extend(polyline, this))
  }
  gaodeMap.Polyline.prototype = {
    _hide: function () {
      this.hide()
    },
    _show: function () {
      this.show()
    },
    _setPath: function (lineArr) {
      this.setPath(lineArr)
    },
    _remove: function () {
      this.setMap(null)
    },
    _addEventListener: function (event, handler) {
      let m = this
      this.on(event, function (e) {
        // handler(e.type, e.target, e.lnglat, e.pixel)
        handler(m, e)
      })
    },
    _getPath: function () {
      let bPoints = this.getPath()
      let pts = []
      for (let i = 0; i < bPoints.length; i++) {
        pts.push({ lng: bPoints[i].lng, lat: bPoints[i].lat })
      }
      return pts
    }
  }

  // 轨迹
  gaodeMap.TrackPolyline = function (map, lineArr, opts) {
    let PolylineOpts = {
      map: map,
      path: lineArr,
      showDir: true,
      strokeColor: '#00A', // 线颜色
      strokeWeight: 3 // 线宽
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
    map.setFitView()
    const mapBegin = 'data:image/png;base64,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'
    const mapFinish = 'data:image/png;base64,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'
    let startPoint = new AMap.Marker({
      icon: new AMap.Icon({
        size: opts.iconSize || new AMap.Size(28, 32),
        image: opts.startIcon || mapBegin,
        imageSize: opts.iconSize || new AMap.Size(28, 32)
      }),
      position: lnglatarr[0],
      offset: opts.iconOffset || new AMap.Pixel(-14, -16)
    })
    let endPoint = new AMap.Marker({
      icon: new AMap.Icon({
        size: opts.iconSize || new AMap.Size(28, 32),
        image: opts.startIcon || mapFinish,
        imageSize: opts.iconSize || new AMap.Size(28, 32)
      }),
      position: lnglatarr[lnglatarr.length - 1],
      offset: opts.iconOffset || new AMap.Pixel(-14, -16)
    })
    startPoint.setMap(map)
    endPoint.setMap(map)
    map.addOverlay(polyline)
    this.startPoint = startPoint
    this.endPoint = endPoint
    this.lineArr = lnglatarr
    this.map = map
    this.protoSelf = polyline
  }
  gaodeMap.TrackPolyline.prototype = {
    _setPath: function (lineArr) {
      this.setPath(lineArr)
    },
    _remove: function () {
      this.protoSelf.setMap(null)
      this.startPoint.setMap(null)
      this.endPoint.setMap(null)
    }
  }

  // 轨迹回放
  gaodeMap.TrackReplay = function (map, lineArr, opts) {
    let track = new gaodeMap.TrackPolyline(map, lineArr, opts)
    let marker = new AMap.Marker({
      map: map,
      position: [lineArr[0].lng, lineArr[0].lat],
      icon: 'https://webapi.amap.com/images/car.png',
      offset: new AMap.Pixel(-26, -13),
      autoRotation: true,
      angle: -90
    })
    let passedPolyline = new AMap.Polyline({
      map: map,
      strokeColor: '#AF5', // 线颜色
      // strokeOpacity: 1,     //线透明度
      strokeWeight: 6 // 线宽
      // strokeStyle: "solid"  //线样式
    })
    marker.on('moving', function (e) {
      passedPolyline.setPath(e.passedPath)
    })
    map.setFitView()
    this.track = track
    this.moveMarker = marker
    this.passedPolyline = passedPolyline
  }
  gaodeMap.TrackReplay.prototype = {
    _remove: function () {
      this.track.setMap(null)
      this.moveMarker.setMap(null)
      this.passedPolyline.setMap(null)
    },
    _startMove: function (time) {
      this.moveMarker.moveAlong(this.track.lineArr, time)
    },
    _pauseMove: function (time) {
      this.moveMarker.pauseMove()
    },
    _resumeMove: function (time) {
      this.moveMarker.resumeMove()
    },
    _stopMove: function (time) {
      this.moveMarker.stopMove()
    }
  }

  // 多边形
  gaodeMap.Polygon = function (map, opt) {
    let lineArr = opt.path
    let defOpts = {
      map: map,
      path: [],
      strokeColor: '#0010ff', // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: 'solid', // 线样式
      fillColor: '#0010ff',
      fillOpacity: 0.3,
      isFresh: true
    }
    if (opt) {
      defOpts = $.extend({}, defOpts, opt)
    }

    let lnglatarr = []
    for (let i = 0; i < lineArr.length; i++) {
      let lnglat = lineArr[i]
      lnglat.lng && lnglat.lat && lnglatarr.push(new AMap.LngLat(lnglat.lng, lnglat.lat))
      lnglat[0] && lnglat[1] && lnglatarr.push(new AMap.LngLat(lnglat[0], lnglat[1]))
    }
    defOpts.path = lnglatarr
    let polygon = new AMap.Polygon(defOpts)
    // 如果配置了区域，点击树的时候，将地图所放置区域范围
    // let bounds = polygon.getBounds()
    // map.setBounds(bounds)
    this.map = map
    // map.addOverlay(polygon)
    this.opts = defOpts
    this.extData = defOpts.extData
    // this.protoSelf = polygon
    return ($.extend(polygon, this))
  }
  gaodeMap.Polygon.prototype = {
    _hide: function () {
      this.hide()
    },
    _show: function () {
      this.show()
    },
    _remove: function () {
      this.setMap(null)
    },
    _contains: function (lng, lat) {
      return this.contains(new gaodeMap.Point(lng, lat))
    },
    _addEventListener: function (event, handler) {
      // this.on(event, handler)
      let m = this
      this.on(event, function (e) {
        // handler(e.type, e.target, e.lnglat, e.pixel)
        handler(m, e)
      })
    },
    _setOptions: function (opts) {
      this.setOptions(opts)
    },
    _getCenter: function () {
      let center = this.getBounds().getCenter()
      return [center.lng, center.lat]
    }
  }

  gaodeMap.ImageLayer = function (opts) {
    var imageLayer = new AMap.ImageLayer({
      url: opts.imageUrl,
      bounds: new AMap.Bounds(
        [opts.bounds[0], opts.bounds[1]],
        [opts.bounds[2], opts.bounds[3]]
      )
    });
    return ($.extend(imageLayer, this))
  }

  gaodeMap.ImageLayer.prototype = {
    _setVisible: function (visible) {
      if (visible) {
        this.show()
      } else {
        this.hide()
      }
    },
    _setZIndex: function (index) {
      this.setzIndex(index)
    }
  }

  gaodeMap.Bounds = function (mapExtent) {
    // let lnglat = [mapExtent[0], mapExtent[1]]
    // let lnglat01 = [mapExtent[2], mapExtent[3]]
    let lnglat = new AMap.LngLat(mapExtent[0], mapExtent[1]) // 左下点
    let lnglat01 = new AMap.LngLat(mapExtent[2], mapExtent[3]) // 右上点
    let bs = new AMap.Bounds(lnglat, lnglat01)
    if (lnglat.lng > lnglat01.lng) {
      this.wlng = lnglat01.lng
      this.elng = lnglat.lng
    } else {
      this.wlng = lnglat.lng
      this.elng = lnglat01.lng
    }
    if (lnglat.lat > lnglat01.lat) {
      this.slat = lnglat01.lat
      this.nlat = lnglat.lat
    } else {
      this.slat = lnglat.lat
      this.nlat = lnglat01.lat
    }
    this.northeast = [this.elng, this.nlat]
    this.southwest = [this.wlng, this.slat]
    return ($.extend(bs, this))
  }

  gaodeMap.Bounds.prototype = {
    getNorthEast: function () {
      return new gaodeMap.Point(this.elng, this.nlat)
    },
    getSouthWest: function () {
      return new gaodeMap.Point(this.wlng, this.slat)
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

  gaodeMap.PathSimplifier = function (map) {
    // eslint-disable-next-line no-undef
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

      // 设置数据
      pathSimplifierIns.setData([{
        name: '路线0',
        points: [{
          name: '点a',
          lnglat: [116.405289, 39.904987]
        }, {
          name: '点b',
          lnglat: [113.964458, 40.54664]
        }, {
          name: '点c',
          lnglat: [111.47836, 41.135964]
        }, {
          name: '点d',
          lnglat: [108.949297, 41.670904]
        }, {
          name: '点e',
          lnglat: [106.380111, 42.149509]
        }, {
          name: '点f',
          lnglat: [103.774185, 42.56996]
        }, {
          name: '点g',
          lnglat: [101.135432, 42.930601]
        }, {
          name: '点h',
          lnglat: [98.46826, 43.229964]
        }, {
          name: '点i',
          lnglat: [95.777529, 43.466798]
        }, {
          name: '点j',
          lnglat: [93.068486, 43.64009]
        }, {
          name: '点k',
          lnglat: [90.34669, 43.749086]
        }, {
          name: '点l',
          lnglat: [87.61792, 43.793308]
        }]
      }])

      // 选中路线0
      pathSimplifierIns.setSelectedPathIndex(0)

      pathSimplifierIns.on('pointClick', function (e, info) {
        console.log('Click: ' + info.pathData.points[info.pointIndex].name)
      })
    })
  }
  // 圆形
  gaodeMap.Circle = function (map, opt) {
    let center = opt.center
    let defOpts = {
      // center: [121.48175477165995, 31.28390596169205],
      map: map,
      radius: 10000, // 半径 单位米
      strokeColor: '#FF33FF',
      strokeOpacity: 1,
      fillOpacity: 0.4,
      // 线样式还支持 'dashed'
      fillColor: '#1791fc',
      zIndex: 50
    }
    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }
    defOpts.center = new AMap.LngLat(center.lng, center.lat)
    let circle = new AMap.Circle(defOpts)
    this.map = map
    this.protoSelf = circle
    this.opts = defOpts
    this.extData = defOpts.extData
    return ($.extend(circle, this))
  }
  gaodeMap.Circle.prototype = {
    // _getCenter: function () {
    //   return this.getCenter();
    // },
    // _getRadius: function () {
    //   return this.getRadius();
    // },
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
    _addEventListener: function (event, handler) {
      // this.on(event, handler)
      this.on(event, function (e) {
        handler(e.type, e.target, e.lnglat, e.pixel)
      })
    },
    _setOptions: function (opts) {
      this.setOptions(opts)
    }
  }
  // 矩形
  gaodeMap.Rectangle = function (map, opt) {
    let bounds = opt.bounds
    let ne = new AMap.LngLat(bounds.northeast[0], bounds.northeast[1])
    let sw = new AMap.LngLat(bounds.southwest[0], bounds.southwest[1])
    let rectangleBounds = new AMap.Bounds(sw, ne)
    let defOpts = {
      map: map,
      strokeColor: '#FF33FF',
      strokeOpacity: 1,
      fillOpacity: 0.4,
      fillColor: '#1791fc'
      // zIndex: 50
    }
    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }
    defOpts.bounds = rectangleBounds
    let rectangle = new AMap.Rectangle(defOpts)
    this.map = map
    this.protoSelf = rectangle
    return ($.extend(rectangle, this))
  }
  gaodeMap.Rectangle.prototype = {
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
    }
  }

  // 椭圆
  gaodeMap.Ellipse = function (map, center, opt) {
    //  此处计算为粗略计算
    // 纬度： 1°≈111234.705  Y
    // 经度： 1°≈111234.705  X
    const latConst = 111234.705
    const lonConst = Math.cos(center.lat) * 2 * Math.PI * 6371000 / 360

    let radiusX = 1000
    let radiusY = 2000
    if (opt && opt.x && opt.y) {
      radiusX = opt.x * lonConst
      radiusY = opt.y * latConst
    }
    let defOpts = {
      // center: [121.48175477165995, 31.2839059616925],
      map: map,
      radius: [radiusX, radiusY], // 半径 单位米
      strokeColor: '#FF33FF',
      strokeOpacity: 1,
      fillOpacity: 0.4,
      fillColor: '#1791fc',
      zIndex: 50
    }
    if (opt) {
      defOpts = $.extend(defOpts, opt)
    }
    defOpts.center = new AMap.LngLat(center.lng, center.lat)
    let ellipse = new AMap.Ellipse(defOpts)
    this.map = map
    this.protoSelf = ellipse
    return ($.extend(ellipse, this))
  }
  gaodeMap.Ellipse.prototype = {
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
    _contains: function () {

    }
  }

  // 文本标记
  gaodeMap.Text = function (map, opts) {
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
        'box-shadow': '0 2rem 6rem 0 rgba(114, 124, 245, .5)',
        'text-align': 'center',
        'font-size': '20rem',
        'color': 'blue'
      }
    }
    defOpts = $.extend(defOpts, opts)
    defOpts.position = [defOpts.position.lng, defOpts.position.lat]
    let text = new AMap.Text(defOpts)
    this.protoSelf = text
    return ($.extend(text, this))
  }
  gaodeMap.Text.prototype = {
    _show: function () {
      this.show()
    },
    _hide: function () {
      this.hide()
    },
    _addEventListener: function (event, handler) {
      this.on(event, handler)
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

  // gaodeMap.Heatmap = function (opts) {
  //   // 高德地图此功能需要支持canvas的浏览器
  //   let opt = {
  //     url: '',
  //     blur: 10,
  //     radius: 10,
  //     field: ''
  //   }
  //   opt = $.extend({}, opt, opts)
  //   var vector
  //   opt.map.plugin(['AMap.Heatmap'], function () {
  //     vector = new AMap.Heatmap(opt.map, {
  //       radius: 25, // 给定半径
  //       opacity: [0, 0.8],
  //       blur: opt.blur
  //       /*,
  //       gradient:{
  //           0.5: 'blue',
  //           0.65: 'rgb(117,211,248)',
  //           0.7: 'rgb(0, 255, 0)',
  //           0.9: '#ffea00',
  //           1.0: 'red'
  //       }
  //        */
  //     })
  //     vector.setDataSet({
  //       data: opt.dataSource,
  //       max: 100
  //     })
  //   })
  //   return ($.extend(vector, this))
  // }

  gaodeMap.Heatmap = function (opt, map) {
    // 计算热力数据
    let heatmapdata = []
    if (opt.dataSource) {
      //   直接接收数据 必须为下面样式的
      //   let temp = {
      //     'lng': item.geometry.coordinates[0],
      //     'lat': item.geometry.coordinates[1],
      //     'count': item.geometry.alarm
      //   }
      heatmapdata = opt.dataSource
      var heatmapOverlay
      var cfg = {
        radius: opt.radius,
        opacity: [0, 0.8], // 热力图透明度数组，取值范围[0,1] 默认：[0,1]
      }
      let gradientObj = {}
      if (opt.gradient && opt.gradient.length > 0) {
        let step = 0.6 / (opt.gradient.length - 1).toFixed(2)
        let gkey = 0
        for (let k = 0; k < opt.gradient.length - 1; k++) {
          gkey = Number((gkey + step).toFixed(2))
          gradientObj[gkey] = opt.gradient[k]
        }
        gradientObj[1] = opt.gradient[opt.gradient.length - 1]
      } else if (opt.gradient) {
        gradientObj = opt.gradient
      } else {
        gradientObj = {
          0.5: 'blue',
          0.65: 'rgb(117,211,248)',
          0.7: 'rgb(0, 255, 0)',
          0.9: '#ffea00',
          1.0: 'red'
        }
      }
      cfg.gradient = gradientObj
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
    } else if (opt.url) {
      // 调用请求获取数据
      axios.get(opt.url).then((res) => {
        let resdata = res.data.features
        if (resdata && resdata.length > 0) {
          res.data.features.forEach(function (item) {
            let temp = {
              'lng': item.geometry.coordinates[0],
              'lat': item.geometry.coordinates[1],
              'count': item.geometry.alarm
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
      }).catch((e) => {
      })
    }
  }
  gaodeMap.Heatmap.prototype = {
    toggleShow: function () {
      if (this.protoSelf) {
        this.protoSelf.show()
      }
    },
    toggleHide: function () {
      if (this.protoSelf) {
        this.protoSelf.hide()
      }
    }
  }
  // 地图控件---工具条、方向盘
  gaodeMap.Controls = {}
  gaodeMap.Controls.ToolBar = function (map, opts) {
    let toolBarOptions = {
      offset: new AMap.Pixel(10, 10),
      position: 'LT',
      ruler: true,
      noIpLocate: false,
      locate: false,
      liteStyle: false,
      direction: true,
      autoPosition: false,
      useNative: false
    }
    toolBarOptions = $.extend({}, toolBarOptions, opts)
    var tool = new AMap.ToolBar(toolBarOptions)
    return tool
  }
  gaodeMap.Controls.ToolBar.prototype = {
    hide() {
      this.hide()
    },
    show() {
      this.show()
    }
  }
  gaodeMap.Controls.Zoom = function (map, opts) {
    let toolBarOptions = {
      position: 'lt'
    }
    toolBarOptions = $.extend({}, toolBarOptions, opts)
    // eslint-disable-next-line no-undef
    AMapUI.loadUI(['control/BasicControl'], (BasicControl) => {
      this.zoom = new BasicControl.Zoom(toolBarOptions)
      this._add(map)
    })
  }
  gaodeMap.Controls.Zoom.prototype = {
    _add(map) {
      this.zoom.addTo(map)
    },
    _remove: function (map) {
      this.zoom.removeFrom(map)
    },
    _zoomIn: function () {
      this.zoom.zoomIn()
    },
    _zoomOut: function () {
      this.zoom.zoomOut()
    }
  }
  gaodeMap.Controls.LayerSwitcher = function (map, opts) {
    let toolBarOptions = {
      position: 'rt'
    }
    toolBarOptions = $.extend({}, toolBarOptions, opts)
    // eslint-disable-next-line no-undef
    AMapUI.loadUI(['control/BasicControl'], (BasicControl) => {
      this.layerSwitcher = new BasicControl.LayerSwitcher(toolBarOptions)
      this._add(map)
    })
  }
  gaodeMap.Controls.LayerSwitcher.prototype = {
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
  gaodeMap.Controls.Traffic = function (map, opts) {
    let toolBarOptions = {
      position: 'lb'
    }
    toolBarOptions = $.extend({}, toolBarOptions, opts)
    // eslint-disable-next-line no-undef
    AMapUI.loadUI(['control/BasicControl'], (BasicControl) => {
      this.traffic = new BasicControl.Traffic(toolBarOptions)
      this._add(map)
    })
  }
  gaodeMap.Controls.Traffic.prototype = {
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
  // 地图控件---鹰眼
  gaodeMap.Controls.OverView = function (map, opts) {
    let overViewOptions = {}
    overViewOptions = $.extend({}, overViewOptions, opts)
    var tool = new AMap.OverView(overViewOptions)
    tool.open()
    return tool
  }
  gaodeMap.Controls.OverView.prototype = {
    hide() {
      this.hide()
    },
    show() {
      this.show()
    }
  }
  // 地图控件---比例尺
  gaodeMap.Controls.Scale = function (map, opts) {
    let scaleOptions = {}
    scaleOptions = $.extend({}, scaleOptions, opts)
    var tool = new AMap.Scale(scaleOptions)
    return tool
  }
  gaodeMap.Controls.Scale.prototype = {
    hide() {
      this.hide()
    },
    show() {
      this.show()
    }
  }
  // 地图控件---地图类型
  gaodeMap.Controls.MapType = function (map, opts) {
    let mapTypeOptions = {}
    mapTypeOptions = $.extend({}, mapTypeOptions, opts)
    var tool = new AMap.MapType(mapTypeOptions)
    return tool
  }
  gaodeMap.Controls.MapType.prototype = {
    hide() {
      this.hide()
    },
    show() {
      this.show()
    }
  }
  // 导航服务
  gaodeMap.NavigationServer = function (map, opts) {
    this.map = map
    let trafficType = 1 // 1:驾车，2：货车，3：步行，4：骑行
    if (opts && opts.trafficType) {
      trafficType = opts.trafficType
    }
    let defOption = {
      // AMap.DrivingPolicy.LEAST_TIME 最快捷，AMap.DrivingPolicy.LEAST_FEE 最经济
      // AMap.DrivingPolicy.LEAST_DISTANCE 最短距离，AMap.DrivingPolicy.REAL_TRAFFIC 考虑实时路况
      policy: AMap.DrivingPolicy.LEAST_TIME,
      ferry: 0, // 默认为0，表示可以使用轮渡，为1的时候表示不可以使用轮渡
      map: map,
      showTraffic: true
      // panel: , // 结果列表的HTML容器id或容器元素，提供此参数后，结果列表将在此容器中进行展示。可选
    }
    if (trafficType == 2) {
      return $.extend(new AMap.TruckDriving(defOption), this)
    } else if (trafficType == 3) {
      return $.extend(new AMap.Transfer(defOption), this)
    } else if (trafficType == 4) {
      return $.extend(new AMap.Walking(defOption), this)
    } else if (trafficType == 5) {
      return $.extend(new AMap.Riding(defOption), this)
    } else {
      return $.extend(new AMap.Driving(defOption), this)
    }
  }
  gaodeMap.NavigationServer.prototype = {
    // 起始点，途经点为经纬度
    searchLatLon(startPt, endPt, routParas, callback) {
      let sPt = new AMap.LngLat(startPt.lng, startPt.lat)
      let ePt = new AMap.LngLat(endPt.lng, endPt.lat)
      let passPts = []
      if (routParas && routParas.waypoints && routParas.waypoints.length > 0) {
        routParas.waypoints.forEach((item) => {
          let pt = new AMap.LngLat(item.lng, item.lat)
          passPts.push(pt)
        })
      }
      this.search(sPt, ePt, { waypoints: passPts }, function (status, result) {
        if (status === 'complete') {
          if (result.routes && result.routes.length) {
            // 绘制第一条路线，也可以按需求绘制其它几条路线
            // drawRoute(result.routes[0], this.map)
            callback(status, result)
          }
        } else {
          callback(status, result)
        }
      })
    },
    // 起始点，途经点为文字
    searchName(pts, callback) {
      this.search(pts, function (status, result) {
        if (status === 'complete') {
          if (result.routes && result.routes.length) {
            // 绘制第一条路线，也可以按需求绘制其它几条路线
            // drawRoute(result.routes[0], this.map)
            callback(status, result)
          }
        } else {
          callback(status, result)
        }
      })
    }
  }

  function parseRouteToPath(route) {
    var path = []
    for (var i = 0, l = route.steps.length; i < l; i++) {
      var step = route.steps[i]

      for (var j = 0, n = step.path.length; j < n; j++) {
        path.push(step.path[j])
      }
    }
    return path
  }
  // eslint-disable-next-line no-unused-vars
  function drawRoute(route, map) {
    var path = parseRouteToPath(route)
    var startMarker = new AMap.Marker({
      position: path[0],
      icon: 'https://webapi.amap.com/theme/v1.3/markers/n/start.png',
      map: map
    })
    var endMarker = new AMap.Marker({
      position: path[path.length - 1],
      icon: 'https://webapi.amap.com/theme/v1.3/markers/n/end.png',
      map: map
    })
    var routeLine = new AMap.Polyline({
      path: path,
      isOutline: true,
      outlineColor: '#ffeeee',
      borderWeight: 2,
      strokeWeight: 5,
      strokeColor: '#0091ff',
      lineJoin: 'round'
    })
    routeLine.setMap(map)
    // 调整视野达到最佳显示区域
    map.setFitView([startMarker, endMarker, routeLine])
  }

  // 坐标转换
  gaodeMap.CoordinateConvert = function () {
    // var convertor = new BMap.Convertor();
    // this.convertor = convertor;
    // return $.extend(convertor, this);
  }
  gaodeMap.CoordinateConvert.prototype = {
    // pointArray 数组{lng:lng1,lat:lat1}
    // before：需要转换的坐标类型
    // after: 转换后的坐标类型
    _translate: function (pointArray, before, handler) { // 最多10个点
      if (this) {
        let bPoints = []
        for (let i = 0; i < pointArray.length; i++) {
          let bPoint = new AMap.LngLat(pointArray[i].lng, pointArray[i].lat)
          bPoints.push(bPoint)
        }
        AMap.convertFrom(bPoints, before, function (status, result) {
          handler(result.locations)
        })
      }
    }
  }

  /*
   *图层
   */
  gaodeMap.Layer = function (map, name, opts) {
    this.name = name
    this.source = []
    return this
  }
  gaodeMap.Layer.prototype = {
    // 将图层加入地图
    _addToMap: function (map) {
      this.source.forEach((item) => {
        item._add()
      })
    },
    _addMarker: function (fea) {
      this.source.push(fea)
    },
    _removeMarker: function (fea) {
      for (let i = 0; i < this.source.length; i++) {
        let item = this.source[i]
        if (item.id == fea.id) {
          this.source.splice(i, 1)
        }
      }
    },
    _show: function () {
      this.source.forEach((item) => {
        item._show()
      })
    },
    _hide: function () {
      this.source.forEach((item) => {
        item._hide()
      })
    },
    _getMarkers: function () {
      return this.source
    }
  }

  // id,name可穿空字符串
  gaodeMap.FontIconMarker = function (map, opts) {
    function getClassnamesOfFontIcon(iconFontClass, icon) {
      return (iconFontClass || 'iconfont') + ' ' + icon
    }

    let markerOpts = {
      containerClassNames: '',
      anchor: 'top-left', // 锚点 设置点标记锚点。默认值：'top-left' 可选值：'top-left'|'top-center'|'top-right'|'middle-left'|'center'|'middle-right'|'bottom-left'|'bottom-center'|'bottom-right'
      fontIcon: '',
      title: '', // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      size: new AMap.Size(27, 27),
      position: { lng: '', lat: '' },
      showPositionPoint: false,
      iconStyle: '',
      extData: {},
      isFresh: false, // 是否刷新地图
      editabled: false,
      isCluster: true,
      getClassnamesOfFontIcon: getClassnamesOfFontIcon,
      zIndex: 100,
      offset: new AMap.Pixel(-13, -13),
      iconFontClass: 'iconfont'
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
    var iconLabel = markerOpts.iconLabel
    if (!(typeof iconLabel == 'object')) {
      iconLabel = {}
    }
    // Object.assign(iconLabel, {
    //   innerHTML: '<i class="' + markerOpts.getClassnamesOfFontIcon(markerOpts.iconFontClass, markerOpts.fontIcon) + '"></i>'
    // })
    let arr = [302, 405, 406, 407, 408, 409, 601, 602, 603];
    if (arr.indexOf(markerOpts.extData.precinctKind) >= 0 && markerOpts.extData.waitHandel > 0) {
      Object.assign(iconLabel, {
        innerHTML: '<i class="' + markerOpts.getClassnamesOfFontIcon(markerOpts.iconFontClass, markerOpts.fontIcon) + '"></i><sup class="waitHandel">' + markerOpts.extData.waitHandel + '</sup>'
      });
    } else {
      Object.assign(iconLabel, {
        innerHTML: '<i class="' + markerOpts.getClassnamesOfFontIcon(markerOpts.iconFontClass, markerOpts.fontIcon) + '"></i>'
      });
    }
    markerOpts.iconLabel = iconLabel
    if (!markerOpts.isCluster) {
      markerOpts.map = map
    }
    let label = markerOpts.cLabel
    let marker = new SimpleMarker(markerOpts)
    if (label) {
      this.markerCustomLabelDom = document.createElement('div')
      let markerCustomLabelDom = this.markerCustomLabelDom
      let domNodes = marker.domNodes
      domNodes.container.appendChild(markerCustomLabelDom)
      if (label.className) {
        this.markerCustomLabelDom.className = label.className
      }
      if (label.content) {
        // markerCustomLabelDom.innerHTML = label.content
        markerCustomLabelDom.innerHTML = "<div style='text-align: center; color: #000000;font-weight:bold;position: relative;font-size: 11rem;text-shadow: #FFFFFF 1rem 0 0, #FFFFFF 0 1rem 0, #FFFFFF -1rem 0 0, #FFFFFF 0 -1rem 0;white-space:nowrap;'>" + label.content + "</div>";
      }
      if (label.className) {
        markerCustomLabelDom.className = label.className
      }
    }
    // map.addOverlay(marker)
    this.protoSelf = marker
    this.map = map
    this.visible = true
    this.opts = markerOpts
    this.extData = markerOpts.extData
    markerOpts.preFontIcon = markerOpts.fontIcon
    return ($.extend(marker, this))
  }
  gaodeMap.FontIconMarker.prototype = {
    setFontIcon: function (fontIcon, isModifyPre) {
      var iconLabel = this.opts.iconLabel
      if (isModifyPre) {
        this.opts.preFontIcon = fontIcon
      }
      this.opts.fontIcon = fontIcon
      if (!(typeof iconLabel == 'object')) {
        iconLabel = {}
      }
      Object.assign(iconLabel, {
        innerHTML: '<i class="' + this.opts.getClassnamesOfFontIcon(this.opts.iconFontClass, fontIcon) + '"></i>'
      })
      this.setIconLabel(iconLabel)
    },
    setMarkerClasses: function (classNames) {
      this.setContainerClassNames(classNames)
    },
    addMarkerClasses: function (classNames) {
      let cClassNames = this.opts.containerClassNames
      this.setContainerClassNames(cClassNames + ' ' + classNames)
    },
    removeMarkerClasses: function (classNames) {
      let cClassNames = this.opts.containerClassNames
      let cAttr = classNames.split(/(\s)+/)
      cAttr.forEach((item) => {
        cClassNames = cClassNames.replace(item, '')
      })
      this.setContainerClassNames(cClassNames)
    },
    setMarkerOffset: function (offset) {
      let toffset = new AMap.Pixel(offset.x, offset.y)
      this.setOffset(toffset)
    },
    getMarkerOffset: function () {
      let toffset = this.getOffset()
      return {
        x: toffset.getX(),
        y: toffset.getY()
      }
    },
    _setPosition: function (lnglan) {
      let pos = new gaodeMap.Point(lnglan.lng, lnglan.lat)
      return this.setPosition(pos)
    },
    _getPosition: function () {
      return this.getPosition()
    },
    _setLabel: function (label) {
      let domNodes = this.domNodes
      let mlabel = this.markerCustomLabelDom
      if (!this.markerCustomLabelDom) {
        mlabel = this.markerCustomLabelDom = document.createElement('div')
        domNodes.container.appendChild(mlabel)
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
      this.setMap(null)
      this.isAdded = false
      this.visible = false
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.on(event, handler)
    },
    _infoTipShow: function () {
      // 空方法，兼容openalyer写的
    },
    _infoTipClose: function () {
      // 空方法，兼容openalyer写的
    }
  }

  // window.gaodeMap = gaodeMap;
  return gaodeMap
}

export default gaodeMap
