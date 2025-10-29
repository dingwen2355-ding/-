import '../baidu/MakerClusterer.js'
import $ from 'jquery'
function baiduMap() {
  let baiduMap = {}
  // wgs84、google坐标转百度坐标
  baiduMap.Coordinate = {
    WGS84: 1,
    GOOGLE: 3,
    BAIDU: 5
  }
  baiduMap.Constants = {
    ZOOM_CHANGED: 'zoomchange',
    ZOOM_END: 'zoomend',
    DRAG_END: 'dragend',
    CLICK: 'click', // 单击事件
    DBLCLICK: 'dblclick', // 双击事件
    RIGHTCLICK: 'rightclick',
    LOADED: 'tilesloaded', // 地图加载完成
    MOUSEMOVE: 'mousemove', // 鼠标在地图区域移动过程中触发此事件
    MOUSEOVER: 'mouseover',
    MOUSEOUT: 'mouseout'
  }
  baiduMap.MapStyles = {
    normal: "",
    dark: "",
    blue: ""
  }
  baiduMap.SymbolShapeType = { // 线上的样式
    BMap_Symbol_SHAPE_CIRCLE: 'BMap_Symbol_SHAPE_CIRCLE', // 圆形，默认半径为1px
    BMap_Symbol_SHAPE_RECTANGLE: 'BMap_Symbol_SHAPE_RECTANGLE', // 矩形，默认宽度4px、高度2px
    BMap_Symbol_SHAPE_RHOMBUS: 'BMap_Symbol_SHAPE_RHOMBUS', // 菱形，默认外接圆半径10px
    BMap_Symbol_SHAPE_STAR: 'BMap_Symbol_SHAPE_STAR', // 五角星，五角星外接圆半径为10px
    BMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW: 'BMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW', // 箭头方向向下的闭合箭头
    BMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW: 'BMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW',
    BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW: 'BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW',
    BMap_Symbol_SHAPE_FORWARD_OPEN_ARROW: 'BMap_Symbol_SHAPE_FORWARD_OPEN_ARROW',
    BMap_Symbol_SHAPE_POINT: 'BMap_Symbol_SHAPE_POINT',
    BMap_Symbol_SHAPE_PLANE: 'BMap_Symbol_SHAPE_PLANE',
    BMap_Symbol_SHAPE_CAMERA: 'BMap_Symbol_SHAPE_CAMERA', // 预设的照相机形状
    BMap_Symbol_SHAPE_WARNING: 'BMap_Symbol_SHAPE_WARNING', // 预设的警告符号
    BMap_Symbol_SHAPE_SMILE: 'BMap_Symbol_SHAPE_SMILE', // 预设的笑脸形状
    BMap_Symbol_SHAPE_CLOCK: 'BMap_Symbol_SHAPE_CLOCK' // 预设的钟表形
  }

  /*
    Map类
   */
  baiduMap.Map = function (mapDiv, opts) {
    // dragging 是否支持拖拽
    // doubleClickZoom 是否支持双击缩放
    // scrollWheelZoom 是否支持滚轮缩放
    let defOpts = {
      zoom: 12,
      minZoom: 3,
      maxZoom: 20,
      enableMapClick: false, // 是否开启底图可点功能，默认启用
      enableAutoResize: true, // 是否自动适应地图容器变化，默认启用
      dragging: true,
      doubleClickZoom: true,
      scrollWheelZoom: true
    }
    defOpts = $.extend({}, defOpts, opts)
    let mapOpts = {
      minZoom: defOpts.minZoom,
      maxZoom: defOpts.maxZoom,
      enableMapClick: defOpts.enableMapClick, // 是否开启底图可点功能，默认启用
      enableAutoResize: defOpts.enableAutoResize // 是否自动适应地图容器变化，默认启用
    }
    let map = new BMap.Map(mapDiv, mapOpts)
    if (opts.center) {
      map.centerAndZoom(new BMap.Point(opts.center.lng, opts.center.lat), defOpts.zoom);
    }
    if (defOpts.dragging) {
      map.enableDragging()
    } else {
      map.disableDragging()
    }
    if (defOpts.doubleClickZoom) {
      map.enableDoubleClickZoom()
    } else {
      map.disableDoubleClickZoom()
    }
    if (defOpts.scrollWheelZoom) {
      map.enableScrollWheelZoom()
    } else {
      map.disableScrollWheelZoom()
    }
    // map.enableScrollWheelZoom(true)
    map = $.extend(map, this);
    map.protoSelf = map;
    return map;
  }
  baiduMap.Map.prototype = {
    _clear: function () {
      this.clearOverlays();
    },
    setStatus: function () {

    },
    // 设置地图中心点和缩放级别
    _centerAndZoom: function (point, zoom) {
      this.centerAndZoom(new BMap.Point(point.lng || point[0], point.lat || point[1]), zoom);
    },
    _center: function (point) {
      this.setCenter(new BMap.Point(point.lng || point[0], point.lat || point[1]));
    },
    // 设置地图当前显示级别
    _zoom: function (zoom) {
      this.setZoom(zoom);
    },
    _getCenter: function () {
      let pt = this.getCenter()
      // let cpt = {lng: pt.lng, lat: pt.lat}
      return pt;
    },
    _getZoom: function() {
      return this.getZoom();
    },
    // 获取地图当前可视范围
    _getView: function () {
      let bds = this.getBounds();
      var northeast = [bds.getNorthEast().lng, bds.getNorthEast().lat];
      var southwest = [bds.getSouthWest().lng, bds.getSouthWest().lat];
      // let ext = {'northeast': northeast, 'southwest': southwest}
      // return ext
      return new baiduMap.Bounds(northeast.concat(southwest))
    },
    /*
     *设置地图当前屏幕可视范围
     */
    _setView: function (points) {
      let bPoints = [];
      if (points.length) {
        // 自设置视野
        for (let i = 0; i < points.length; i++) {
          let pointA = points[i]
          let bPoint = new BMap.Point(pointA.lng, pointA.lat)
          bPoints.push(bPoint)
        }
        this.setViewport(bPoints)
      } else {
        // 通过图形获取视野
        bPoints = bPoints.concat(points.getPath());
        this.setViewport(bPoints);
      }
    },
    _setBounds: function (bounds) { // 设置边界，拖拽后会自动恢复到该区域。
      let bds;
      if (bounds.southwest && bounds.northeast) { // 自定义bounds
        let sw = new BMap.Point(bounds.southwest[0], bounds.southwest[1])
        let ne = new BMap.Point(bounds.northeast[0], bounds.northeast[1])
        bds = new BMap.Bounds(sw, ne)
      } else { // 根据图形要素获取边界
        bds = bounds.getBounds();
      }
      try {
        let result = BMapLib.AreaRestriction.setBounds(this, bds);
        console.log(result)
      } catch (e) {
        console.log(e)
      }
    },
    // 地图控件的添加，eg:导航控件，比例尺控件等
    _addControl: function(control) {
      this.addControl(control);
    },
    // 从地图移除控件
    _removeControl: function(control) {
      this.removeControl(control);
    },
    // 向地图添加覆盖要素，eg: marker , polyline,polygon等
    _addOverlay: function(overlay) {
      this.addOverlay(overlay);
    },
    // 从地图移除覆盖要素
    _removeOverlay: function (overlay) {
      this.removeOverlay(overlay);
    },
    // 获取两点间的距离
    _getDistance: function (pointA, pointB) {
      let pA = new BMap.Point(pointA.lng, pointA.lat)
      let pB = new BMap.Point(pointB.lng, pointB.lat)
      return this.getDistance(pA, pB).toFixed(2) + '米'
    },
    // 启用地图拖拽
    _enableDragging: function() {
      this.enableDragging();
    },
    // 禁用地图拖拽
    _disableDragging: function() {
      this.disableDragging();
    },
    // 启用地图双击放大
    _enableDoubleClickZoom: function () {
      this.enableDoubleClickZoom();
    },
    // 禁用地图双击放大
    _disableDoubleClickZoom: function () {
      this.disableDoubleClickZoom()
    },
    // 启用滚轮缩放
    _enableScrollWheelZoom: function () {
      this.enableScrollWheelZoom()
    },
    // 禁用滚轮缩放
    _disableScrollWheelZoom() {
      this.disableScrollWheelZoom()
    },
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        if (e.point) { // 单击，双击事件
          let point = new baiduMap.Point(e.point.lng, e.point.lat);
          handler(point)
        } else { // 地图加载完成事件
          handler(e)
        }
      })
    },
    _on: function (event, handler) {
      this.addEventListener(event, function (e) {
        if (e.point) { // 单击，双击事件
          let point = new baiduMap.Point(e.point.lng, e.point.lat);
          handler(point)
        } else { // 地图加载完成事件
          handler(e)
        }
      })
    },
    // 注销地图事件
    _removeEventListener: function (event, handler) {
      this.removeEventListener(event, function (e) {
        handler(e)
      });
    },
    _destroy: function () {
      return this.destroy()
    }
  }

  /*
   Bounds类
   */

  baiduMap.Bounds = function (mapExtent) {
    // let lnglat = [mapExtent[0], mapExtent[1]]
    // let lnglat01 = [mapExtent[2], mapExtent[3]]
    let lnglat = new baiduMap.Point(mapExtent[0], mapExtent[1]) // 左下点
    let lnglat01 = new baiduMap.Point(mapExtent[2], mapExtent[3]) // 右上点
    let bs = new BMap.Bounds(lnglat,lnglat01)
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

  baiduMap.Bounds.prototype = {
    getNorthEast: function () {
      return new baiduMap.Point(this.elng, this.nlat)
    },
    getSouthWest: function () {
      return new baiduMap.Point(this.wlng, this.slat)
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

  /*
   Marker类
   */
  baiduMap.Marker = function (map, opts) {
    this.id = opts.id // marker唯一标志ID
    this.type = opts.type
    this.layerName = opts.layerName // 图层名称
    this.icon = opts.icon
    this.extData = opts.extData
    this.opts = opts;
    opts.map = map;
    // this.map = map // ??? 设置后会添加到地图上
    let markerOpts = {
      icon: 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png',
      // title: '', // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      size: new BMap.Size(27, 27),
      position: {lng: '', lat: ''},
      extData: {}
    }
    markerOpts = $.extend({}, markerOpts, opts)
    if (opts.position) {
      markerOpts.position = new BMap.Point(opts.position.lng, opts.position.lat)
    }
    if (opts.size) {
      markerOpts.size = new BMap.Size(opts.size.width, opts.size.height)
    }
    this.extData = markerOpts.extData
    markerOpts.icon = new BMap.Icon(markerOpts.icon, markerOpts.size, {
      anchor: new BMap.Size(opts.size.width / 2, opts.size.height),// 图标中央下端的尖角位置。
      // imageOffset: new BMap.Size(0, 0 - opts.size.height) // 设置图片偏移
    });
    let marker = new BMap.Marker(markerOpts.position, markerOpts);
    this.protoSelf = marker
    return ($.extend(marker, this))
  }
  baiduMap.Marker.prototype = {
    _enableDragging: function() {
      this.enableDragging();
    },
    _disableDragging: function() {
      this.disableDragging();
    },
    _show: function () {
      this.show()
      this.visible = true
    },
    _hide: function () {
      this.hide()
      this.visible = false
    },
    _getPosition: function () {
      return this.getPosition()
    },
    _setPosition: function (pos) {
      let pt = new BMap.Point(pos.lng, pos.lat);
      this.setPosition(pt)
    },
    _setLabel: function (opt) {
      // label, opt
      let label = opt.content
      let bLable;
      if (opt) {
        if (opt.offset) {
          opt.offset = new BMap.Size(opt.offset.x, opt.offset.y)
        }
        bLable = new BMap.Label(label, opt)
      } else {
        bLable = new BMap.Label(label)
      }
      this.setLabel(bLable)
    },
    _getIcon: function () {
      let icon = this.getIcon().imageUrl
      return icon
    },
    _setIcon: function (iconOpts) {
      let opts = {};
      if (typeof iconOpts == "string") {
        opts.icon = iconOpts;
      } else {
        if (iconOpts.iconSrc) {
          iconOpts.icon = iconOpts.iconSrc;
        }
        opts = $.extend({}, opts, iconOpts);
      }
      let size = this.getIcon().size;
      if (opts && opts.size) {
        size = new BMap.Size(opts.size.width, opts.size.height)
      }
      let bIcon = new BMap.Icon(opts.icon, size, {});
      this.setIcon(bIcon)
    },
    _setMap: function (map) {
      if (map) {
        map.addOverlay(this)
      } else {
        this.opts.map.removeOverlay(this)
      }
    },
    _add: function (map) {
      this.opts.map.addOverlay(this)
    },
    _remove: function () {
      this.opts.map.removeOverlay(this)
      this.isAdded = false
      this.visible = false
    },
    // 添加事件监听函数
    //  event: "click"
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        if (e.point) {
          let pt = new baiduMap.Point(e.point.lng, e.point.lat)
          handler(pt);
        } else {
          handler(e)
        }
      })
    },
    _infoTipShow: function () {
      // 空方法，兼容openalyer写的
    },
    _infoTipClose: function () {
      // 空方法，兼容openalyer写的
    }
  }

  // 信息窗体
  baiduMap.InfoWindow = function (content, opts) {
    let infoWindowOptions = {
      isCustom: true,
      enableAutoPan: true,
      enableCloseOnClick: opts.enableCloseOnClick
    }
    infoWindowOptions = $.extend({}, infoWindowOptions, opts)
    if (opts.lnglat) {
      infoWindowOptions.position = new BMap.Point(opts.lnglat.lng, opts.lnglat.lat)
    }
    if (opts.offset) {
      infoWindowOptions.offset = new BMap.Size(offset.x, offset.y)
    }
    // let size = opts.size
    // if (size) {
    //   infoWindowOptions.size = new AMap.Size(size.width, size.height)
    // }
    let inforWindow = new BMap.InfoWindow(content, infoWindowOptions)
    return ($.extend(inforWindow, this))
  }
  baiduMap.InfoWindow.prototype = {
    _open: function (map, marker) {
      marker.openInfoWindow(this)
    },
    _close: function (marker) {
      marker.closeInfoWindow();
    }
  }

  // 地图控件---工具条、方向盘
  baiduMap.Controls = {}
  baiduMap.Controls.ToolBar = function (map, opts) {
    let toolBarOptions = {
      offset: new BMap.Size(150, 5),
      type: BMAP_NAVIGATION_CONTROL_LARGE, // BMAP_NAVIGATION_CONTROL_LARGE,BMAP_NAVIGATION_CONTROL_SMALL,BMAP_NAVIGATION_CONTROL_PAN,BMAP_NAVIGATION_CONTROL_ZOOM
      anchor: BMAP_ANCHOR_TOP_LEFT // BMAP_ANCHOR_TOP_RIGHT,BMAP_ANCHOR_BOTTOM_LEFT,BMAP_ANCHOR_BOTTOM_RIGHT
    }
    toolBarOptions = $.extend({}, toolBarOptions, opts)
    let tool = new BMap.NavigationControl(opts);
    return tool
  }
  baiduMap.Controls.ToolBar.prototype = {
    hide() {
      // this.hide()
    },
    show() {
      // this.show()
    }
  }
  // 地图控件---鹰眼
  baiduMap.Controls.OverView = function (map, opts) {
    let overViewOptions = {
      isOpen: true
      // anchor: BMAP_ANCHOR_BOTTOM_RIGHT
    }
    overViewOptions = $.extend({}, overViewOptions, opts)
    var tool = new BMap.OverviewMapControl(overViewOptions);
    return tool
  }
  baiduMap.Controls.OverView.prototype = {
    hide() {
      // this.hide()
    },
    show() {
      // this.show()
    }
  }
// 地图控件---比例尺
  baiduMap.Controls.Scale = function (map, opts) {
    let scaleOptions = {}
    scaleOptions = $.extend({}, scaleOptions, opts)
    var tool = new BMap.ScaleControl()
    return tool
  }
  baiduMap.Controls.Scale.prototype = {
    hide() {
      // this.hide()
    },
    show() {
      // this.show()
    }
  }
// 地图控件---地图类型
  baiduMap.Controls.MapType = function (opts) {
    let mapTypeOptions = {}
    mapTypeOptions = $.extend({}, mapTypeOptions, opts)
    var tool = new BMap.MapTypeControl()
    return tool
  }
  baiduMap.Controls.MapType.prototype = {
    hide() { },
    show() { }
  }

  // 地图点要素BMap.Point
  baiduMap.Point = function (lng, lat) {
    return ($.extend(new BMap.Point(lng, lat), this))
  }
  baiduMap.Point.prototype = {
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
    }
  }

  // 线
  baiduMap.Polyline = function (map, lineArr, opts) {
    let PolylineOpts = {
      // map: map,
      path: lineArr,
      strokeColor: '#00A', // 线颜色
      strokeOpacity: 1, // 线透明度
      strokeWeight: 3, // 线宽
      strokeStyle: "solid" // 线样式
    }
    let lnglatarr = []
    for (let i = 0; i < lineArr.length; i++) {
      let bPoint = new BMap.Point(lineArr[i].lng, lineArr[i].lat)
      lnglatarr.push(bPoint)
    }
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
    }
    if (opts.arrow) {
      var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
        scale: 0.6, // 图标缩放大小
        strokeColor: '#CC3333', // 设置矢量图标的线填充颜色
        strokeWeight: '2'// 设置线宽
      });
      var icons = new BMap.IconSequence(sy, '10', '30');
      PolylineOpts.icons = [icons]
    }
    PolylineOpts.path = lnglatarr
    let polyline = new BMap.Polyline(lnglatarr, PolylineOpts)
    // this.map = map // 指定Map后会直接被加到地图中
    this.protoSelf = polyline
    return ($.extend(polyline, this))
  }
  baiduMap.Polyline.prototype = {
    _hide: function () {
      this.hide()
    },
    _show: function () {
      this.show()
    },
    _setPath: function (lineArr) {
      let arry = [];
      for (let i = 0; i < lineArr.length; i++) {
        let bPoint = new BMap.Point(lineArr[i].lng, lineArr[i].lat);
        arry.push(bPoint);
      }
      this.setPath(arry)
    },
    _getPath: function () {
      let bPoints = this.getPath();
      let pts = [];
      for (let i = 0; i < bPoints.length; i++) {
        pts.push({lng: bPoints[i].lng, lat: bPoints[i].lat})
      }
      return pts;
    },
    _remove: function () {
      this.getMap().removeOverlay(this);
    },
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        handler(e.type, e.target, e.point, e.pixel)
      })
    }
  }

  // 轨迹线
  baiduMap.TrackPolyline = function (map, lineArr, opts) {
    //  轨迹点
    if (this.markers && this.markers.length > 0) {
      this.markers.forEach((item) => {
        map.removeOverlay(item)
      })
      this.markers = [];
    } else {
      this.markers = []
    }
    let size = new BMap.Size(57, 65)
    let offset = new BMap.Size(28, 65)
    let sIcon = new BMap.Icon("data:image/png;base64,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", size, {anchor: offset});
    let eIcon = new BMap.Icon("data:image/png;base64,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", size, {anchor: offset});
    let sPos = new BMap.Point(lineArr[0].lng, lineArr[0].lat);
    let ePos = new BMap.Point(lineArr[lineArr.length - 1].lng, lineArr[lineArr.length - 1].lat);
    let sMarker = new BMap.Marker(sPos, {icon: sIcon});
    let eMarker = new BMap.Marker(ePos, {icon: eIcon});
    this.markers.push(sMarker);
    this.markers.push(eMarker);
    map.addOverlay(sMarker)
    map.addOverlay(eMarker)
    // 线
    let PolylineOpts = {
      // map: map,
      path: lineArr,
      strokeColor: '#00A', // 线颜色
      strokeOpacity: 1, // 线透明度
      strokeWeight: 3, // 线宽
      strokeStyle: "solid" // 线样式
    }
    let lnglatarr = []
    for (let i = 0; i < lineArr.length; i++) {
      let bPoint = new BMap.Point(lineArr[i].lng, lineArr[i].lat)
      lnglatarr.push(bPoint)
    }
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
    }
    if (opts.arrow) {
      var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
        scale: 0.6, // 图标缩放大小
        strokeColor: '#CC3333', // 设置矢量图标的线填充颜色
        strokeWeight: '2'// 设置线宽
      });
      var icons = new BMap.IconSequence(sy, '10', '30');
      PolylineOpts.icons = [icons]
    }
    this.geoMarker = new BMap.Icon(opts.icon || 'data:image/png;base64,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', new BMap.Size(57,57),{anchor : new BMap.Size(28, 57)})
    PolylineOpts.path = lnglatarr
    let polyline = new BMap.Polyline(lnglatarr, PolylineOpts)
    // this.map = map // 指定Map后会直接被加到地图中
    this.lushu = new BMapLib.LuShu(map, polyline.getPath(), {
      geodesic: true,
      autoCenter: true,
      icon: this.geoMarker,
      speed: opts.speed || 4000,
      enableRotation: true
    })
    map.addOverlay(polyline)
    this.protoSelf = polyline
    return ($.extend(polyline, this))
  }
  baiduMap.TrackPolyline.prototype = {
    _setPath: function (lineArr, opts) {
      let map = this.getMap();
      //  轨迹点
      if (this.markers && this.markers.length > 0) {
        this.markers.forEach((item) => {
          map.removeOverlay(item)
        })
        this.markers = [];
      } else {
        this.markers = []
      }
      let size = new BMap.Size(50, 50)
      let offset = new BMap.Size(-(size.w / 2), -size.h)
      let sIcon = new BMap.Icon("../../map/images/map-begin.png", size, {imageOffset: offset});
      let eIcon = new BMap.Icon("../../map/images/map-finish.png", size, {imageOffset: offset});
      let sPos = new BMap.Point(lineArr[0].lng, lineArr[0].lat);
      let ePos = new BMap.Point(lineArr[lineArr.length - 1].lng, lineArr[lineArr.length - 1].lat);
      let sMarker = new BMap.Marker(sPos, {icon: sIcon});
      let eMarker = new BMap.Marker(ePos, {icon: eIcon});
      this.markers.push(sMarker);
      this.markers.push(eMarker);
      map.addOverlay(sMarker)
      map.addOverlay(eMarker)
      // 线
      let arry = [];
      for (let i = 0; i < lineArr.length; i++) {
        let bPoint = new BMap.Point(lineArr[i].lng, lineArr[i].lat);
        arry.push(bPoint);
      }
      this.setPath(arry)
    },
    _remove: function () {
      this.getMap().removeOverlay(this);
      if (this.markers && this.markers.length > 0) {
        this.markers.forEach((item) => {
          this.getMap().removeOverlay(item)
        })
        this.markers = [];
      }
    },
    _setDirectlineMap: function (map) {

    },
    stopAnimation() {
      this.lushu.stop()
    },
    startAnimation(speed, callback) {
      this.lushu.start()
    },
    pauseAnimation() {
      this.lushu.pause()
    },
  }

  /*
 * 扇形
 * angle:圆心角
 * rotate：旋转角，从平面直角坐标系x轴正轴方向逆时针旋转的角度
 * sDegree
 * eDegree 夹角从sDegree到eDegree的扇形
 * center：圆心
 * radius：半径，单位米
 */
  baiduMap.Sector = function (map, opts) {
    var origin = new BMap.Point(opts.center.lng, opts.center.lat)
    var radius = opts.radius || 1000 // 米
    var sides = 256
    var centerAngle = opts.angle || 120
    var rotate = opts.rotate || 0
    var sDegree = opts.sDegree || 0
    var eDegree = opts.eDegree || sDegree + centerAngle
    var points = createSector(origin, radius, sDegree, eDegree, {map: map})

    // 根据构成的点数组以及其他参数画多边形
    var sectorStyle = {
      strokeColor: opts.strokeColor || '#FFA200', // 线颜色
      strokeOpacity: opts.strokeOpacity || 1, // 线透明度
      strokeWeight: opts.strokeWeight || 3, // 线宽
      strokeStyle: opts.strokeStyle || 'solid', // 线样式
      isFresh: opts.isFresh || false,
      fillColor: opts.fillColor || '#C6E2FF',
      fillOpacity: opts.fillOpacity || 0.5
    };
    var polygon = new BMap.Polygon(points, sectorStyle);
    this.protoSelf = polygon
    this.opts = opts;
    this.extData = opts.extData;
    return ($.extend(polygon, this))
  };
  baiduMap.Sector.prototype = {
    _hide: function () {
      this.hide()
    },
    _show: function () {
      this.show()
    },
    _remove: function () {
      this.getMap().removeOverlay(this.protoSelf);
    },
    _contains: function (lng, lat) {
      let bounds = this.getBounds();
      let bPoint = new BMap.Point(lng, lat);
      return bounds.containsPoint(bPoint)
    }
  };

  function createSector(origin, radius, sDegree, eDegree, opts) {
    var points = []; // 创建构成多边形的点数组
    var step = ((eDegree - sDegree) / 10) || 10; // 根据扇形的总夹角确定每步夹角度数，最大为10
    points.push(origin);
    for (var i = sDegree; i < eDegree + 0.001; i += step) { // 循环获取每步的圆弧上点的坐标，存入点数组
      points.push(EOffsetBearing(origin, radius, i, opts.map));
    }
    points.push(origin);
    // if (sDegree == eDegree) {
    //   var planepoint = points[1];
    //   var myIcon = new BMap.Icon("../images/uav.png",
    //     new BMap.Size(36, 36));
    //   var marker = new BMap.Marker(planepoint, {
    //     icon: myIcon
    //   });
    //   var infoWindow = new BMap.InfoWindow("信息:", opts); // 创建信息窗口对象
    //   marker.addEventListener("click", function() {
    //     map.openInfoWindow(infoWindow, planepoint); // 开启信息窗口
    //   });
    //   map.addOverlay(marker);
    // }
    return points;
  }

// 使用数学的方法计算需要画扇形的圆弧上的点坐标
  function EOffsetBearing(point3, dist, bearing, map) {
    var lngConv = map.getDistance(point3, new BMap.Point(point3.lng + 0.1, point3.lat)) * 10; // 计算1经度与原点的距离
    var latConv = map.getDistance(point3, new BMap.Point(point3.lng, point3.lat + 0.1)) * 10; // 计算1纬度与原点的距离
    var lat = dist * Math.sin(bearing * Math.PI / 180) / latConv; // 正弦计算待获取的点的纬度与原点纬度差
    var lng = dist * Math.cos(bearing * Math.PI / 180) / lngConv; // 余弦计算待获取的点的经度与原点经度差
    return new BMap.Point(point3.lng + lng, point3.lat + lat);
  }
  // 多边形
  baiduMap.Polygon = function (map, opt) {
    let defOpts = {
      // map: map,
      path: [],
      strokeColor: '#0010ff', // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: 'solid', // 线样式
      fillColor: '#0010ff',
      fillOpacity: 0.3
    }
    if (opt) {
      defOpts = $.extend({}, defOpts, opt)
    }

    let lnglatarr = []
    for (let i = 0; i < opt.path.length; i++) {
      let bpoint = new BMap.Point(opt.path[i].lng || opt.path[i][0], opt.path[i].lat || opt.path[i][1])
      lnglatarr.push(bpoint);
    }
    // defOpts.path = lnglatarr
    let polygon = new BMap.Polygon(lnglatarr, defOpts)
    // 如果配置了区域，点击树的时候，将地图所放置区域范围
    // let bounds = polygon.getBounds()
    // map.setBounds(bounds)
    // this.map = map
    map.addOverlay(polygon)
    this.protoSelf = polygon
    this.opts = defOpts;
    this.extData = defOpts.extData;
    return ($.extend(polygon, this))
  }
  baiduMap.Polygon.prototype = {
    _hide: function () {
      this.hide()
    },
    _show: function () {
      this.show()
    },
    _add: function (map) {
      map.addOverlay(this)
    },
    _remove: function () {
      this.getMap().removeOverlay(this);
    },
    _contains: function (pt) {
      let bounds = this.getBounds();
      let bPoint = new BMap.Point(pt.lng, pt.lat);
      return bounds.containsPoint(bPoint)
    },
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        handler(e.type, e.target, e.point, e.pixel)
      })
    },
    _setOptions: function (opts) {
      if (opts) {
        if (opts.strokeColor) {
          this.setStrokeColor(opts.strokeColor)
        }
        if (opts.fillColor) {
          this.setFillColor(opts.fillColor)
        }
        if (opts.strokeOpacity) {
          this.setStrokeOpacity(opts.strokeOpacity)
        }
        if (opts.fillOpacity) {
          this.setFillOpacity(opts.fillOpacity)
        }
      }
    }
  }

  // 圆形
  baiduMap.Circle = function (map, opt) {
    let defOpts = {
      map: map,
      radius: 1000, // 半径 单位米
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
    defOpts.center = new BMap.Point(opt.center.lng, opt.center.lat)
    let circle = new BMap.Circle(defOpts.center, defOpts.radius, defOpts)
    // this.map = map
    this.protoSelf = circle
    this.opts = defOpts;
    this.extData = defOpts.extData;
    return ($.extend(circle, this))
  }
  baiduMap.Circle.prototype = {
    _getCenter: function () {
      let center = this.getCenter()
      return {lng: center.lng, lat: center.lat}
    },
    _show: function () {
      this.show()
    },
    _hide: function () {
      this.hide()
    },
    _add: function (map) {
      map.addOverlay(this)
    },
    _remove: function () {
      this.getMap().removeOverlay(this);
    },
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        handler(e.type, e.target, e.point, e.pixel)
      })
    },
    _contains: function (pt) {
      let bounds = this.getBounds();
      let bPoint = new BMap.Point(pt.lng, pt.lat);
      return bounds.containsPoint(bPoint)
    },
    _setOptions: function (opts) {
      if (opts) {
        if (opts.strokeColor) {
          this.setStrokeColor(opts.strokeColor)
        }
        if (opts.fillColor) {
          this.setFillColor(opts.fillColor)
        }
        if (opts.strokeOpacity) {
          this.setStrokeOpacity(opts.strokeOpacity)
        }
        if (opts.fillOpacity) {
          this.setFillOpacity(opts.fillOpacity)
        }
      }
    }
  }

  // 矩形
  baiduMap.Rectangle = function (map, opt) {
    let bounds = opt.bounds;
    let bPoints = [
      new BMap.Point(bounds.southwest[0], bounds.northeast[1]),
      new BMap.Point(bounds.southwest[0], bounds.southwest[1]),
      new BMap.Point(bounds.northeast[0], bounds.southwest[1]),
      new BMap.Point(bounds.northeast[0], bounds.northeast[1])
    ]
    let defOpts = {
      map: map,
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
    let rectangle = new BMap.Polygon(bPoints, defOpts)
    // this.map = map
    this.protoSelf = rectangle
    return ($.extend(rectangle, this))
  }
  baiduMap.Rectangle.prototype = {
    _show: function () {
      this.show()
    },
    _hide: function () {
      this.hide()
    },
    _add: function (map) {
      map.addOverlay(this)
    },
    _remove: function () {
      this.getMap().removeOverlay(this);
    },
    _contains: function (pt) {
      let bounds = this.getBounds();
      let bPoint = new BMap.Point(pt.lng, pt.lat);
      return bounds.containsPoint(bPoint)
    }
  }

  // 椭圆
  baiduMap.Ellipse = function (map, center, opt) {
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
    var x = opt.x || 0.1;
    var y = opt.y || 0.2;
    let lnglatarr = []
    var angle;
    var dot;
    var tangent = x / y;
    for (var i = 0; i < 36; i++) {
      angle = (2 * Math.PI / 36) * i;
      dot = new BMap.Point(center.lng + Math.sin(angle) * y * tangent, center.lat + Math.cos(angle) * y);
      lnglatarr.push(dot);
    }
    let ellipse = new BMap.Polygon(lnglatarr, defOpts)
    this.protoSelf = ellipse
    return ($.extend(ellipse, this))
  }
  baiduMap.Ellipse.prototype = {
    _hide: function () {
      this.hide()
    },
    _show: function () {
      this.show()
    },
    _add: function (map) {
      map.addOverlay(this)
    },
    _remove: function () {
      this.getMap().removeOverlay(this);
    },
    _contains: function (pt) {
      let bounds = this.getBounds();
      let bPoint = new BMap.Point(pt.lng, pt.lat);
      return bounds.containsPoint(bPoint)
    },
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        handler(e.type, e.target, e.point, e.pixel)
      })
    }
  }

  baiduMap.CurveLine = function (map, points, opt) {
    let defOptions = {
      strokeColor: "blue",
      strokeWeight: 3,
      strokeOpacity: 0.5
    }
    if (opt) {
      defOptions = $.extend({}, defOptions, opt)
    }
    let lnglatarr = []
    for (let i = 0; i < points.length; i++) {
      let bpoint = new BMap.Point(points[i].lng, points[i].lat)
      lnglatarr.push(bpoint);
    }
    var curve = new BMapLib.CurveLine(lnglatarr, defOptions); // 创建弧线对象
    this.protoSelf = curve
    return ($.extend(curve, this))
  }
  baiduMap.CurveLine.prototype = {

  }

  // 文本标记
  baiduMap.Text = function (map, opts) {
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
      let text = new BMap.Label(defOpts.text, opts)
      this.protoSelf = text;
      return ($.extend(text, this))
    }
  }
  baiduMap.Text.prototype = {
    // 设置label的样式
    _setStyle: function (opt) {
      this.setStyle(opt)
    },
    _show: function () {
      this.show()
    },
    _hide: function () {
      this.hide()
    },
    // Label添加事件
    _addEventListener: function (event, handler) {
      this.addEventListener(event, function (e) {
        handler(e.type, e.target)
      })
    }
  }

  // 热力图 基于百度开源库
  baiduMap.Heatmap = function (opts) {
    // 热力图目前只支持有canvas支持的浏览器,您所使用的浏览器不能使用热力图功能~
    let defOptions = {
      radius: opts.radius || 10, // radius 势力图的每个点的半径大小
      visible: opts.visible || true,
      map: opts.map
    }
    if (opts.gradient) {
      defOptions.gradient = opts.gradient
    }
    if (opts.opacity) { // 整体透明度，建议不设置
      defOptions.opacity = opts.opacity
    }
    let dataOption = {
      max: opts.max || 100,
      data: opts.dataSource || []
    }
    // let heatmapOverlay = new BMapLib.HeatmapOverlay(defOptions);
    let heatmapOverlay = new BMapLib.HeatmapOverlay(defOptions);
    if (opts.map) {
      defOptions.map.addOverlay(heatmapOverlay)
      // heatmapOverlay.setDataSet(defOptions)
      heatmapOverlay.setDataSet(dataOption)
      return ($.extend(heatmapOverlay, this))
    }
  }
  baiduMap.Heatmap.prototype = {
    toggleShow: function () {
      this.show()
    },
    toggleHide: function () {
      this.hide()
    }
  }

  baiduMap.MapLib = {}
  // 点聚合 基于百度开源库  MarkerClusterer.js.
  /* opts参数
    markers {Array} 要聚合的标记数组
    gridSize {Number} 聚合计算时网格的像素大小，默认60
    maxZoom {Number} 最大的聚合级别，大于该级别就不进行相应的聚合
    minClusterSize {Number} 最小的聚合数量，小于该数量的不能成为一个聚合，默认为2
    isAverangeCenter {Boolean} 聚合点的落脚位置是否是所有聚合在内点的平均值，默认为否，落脚在聚合内的第一个点
    styles {Array} 自定义聚合后的图标风格，请参考TextIconOverlay类
     */
  baiduMap.MapLib.MarkerClusterer = function (map, markers, opts) {
    this.map = map
    markers = markers || []
    opts = opts || {}
    let me = this
    if (opts.minimumClusterSize) {
      opts.minClusterSize = opts.minimumClusterSize
    }
    opts.markers = markers;
    me.cluster = new BMapLib.MarkerClusterer(map, opts)
    return me;
  }
  baiduMap.MapLib.MarkerClusterer.prototype = {
    _addMarker: function (marker) {
      this.cluster.addMarker(marker);
    },
    _addMarkers: function (markers) {
      this.cluster.addMarkers(markers)
    },
    _removeMarker: function (marker) {
      this.cluster.removeMarker(marker)
    },
    _clearMarkers: function () {
      this.cluster.clearMarkers()
    },
    _removeMarkers: function (markers) {
      this.cluster.removeMarkers(markers)
    },
    /*
       设置单个聚合的最小数量。
     */
    _setMinClusterSize: function (minmumClusterSize) {
      this.cluster.setMinClusterSize(minmumClusterSize)
    }
  }

  /*
   鼠标绘制工具 基于百度开源库 DrawingManager.js.  DistanceTool.js.
   opts = {
    distanceOptions: opt, // 测距样式
    circleOptions: opt, // 圆的样式
    polylineOptions: opt, // 线的样式
    polygonOptions: opt, // 多边形的样式
    rectangleOptions: opt
  }
   */
  baiduMap.MapLib.MouseTool = function (map) {
    this.map = map
    this.mouseTool = null
  }
  baiduMap.MapLib.MouseTool.prototype = {
    // 测距
    _measureDistance: function (opts) {
      let map = this.map
      let defOption = {
        size: new BMap.Size(30, 30)
      }
      defOption = $.extend({}, defOption, opts)
      if (opts) {
        if (opts.size) {
          defOption.size = new BMap.Size(opts.size.width, opts.size.height);
        }
        if (opts.secIcon) {
          let url = opts.secIcon;
          defOption.secIcon = new BMap.Icon(url, defOption.size, {});
        }
      }
      var distanceTool = new BMapLib.DistanceTool(map, defOption);
      this.distanceTool = distanceTool;
      this.distanceTool.open();
    },
    _measureArea: function (cb, opts) {
      let enableCalculate = true;
      let map = this.map
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        polygonOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON)
        drawingManager.open();
        if (enableCalculate) {
          drawingManager.enableCalculate();
        }
        drawingManager.addEventListener('overlaycomplete', function (e) {
          if (enableCalculate) {
            drawingManager.disableCalculate();
          }
          // cb(e.overlay)
        })
      }
    },
    _drawRectangle: function (cb, opts) {
      let map = this.map
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        rectangleOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_RECTANGLE)
        drawingManager.open();
        // this.drawingManager.enableCalculate(); // 开启测量模式
        // this.drawingManager.disableCalculate() // 关闭测量模式
        drawingManager.addEventListener('overlaycomplete', function (e) {
          cb(e.overlay)
          drawingManager.close()
        })
      }
    },
    // 框选
    _selectRectangle: function (markers, cb, opts) {
      let map = this.map
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        rectangleOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_RECTANGLE)
        drawingManager.open();
        // this.drawingManager.enableCalculate(); // 开启测量模式
        // this.drawingManager.disableCalculate() // 关闭测量模式
        drawingManager.addEventListener('overlaycomplete', function (e) {
          let resultMarkers = [];
          let bounds = e.overlay.getBounds();
          drawingManager.close()
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (bounds.containsPoint(pt)) {
              resultMarkers.push(markers[i])
            }
          }
          map.removeOverlay(e.overlay)
          cb(resultMarkers)
        })
      }
    },
    // 圈选
    _selectCircle: function (markers, cb, opts) {
      let map = this.map
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        rectangleOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_CIRCLE)
        drawingManager.open();
        drawingManager.addEventListener('overlaycomplete', function (e) {
          let resultMarkers = [];
          let bounds = e.overlay.getBounds();
          drawingManager.close()
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (bounds.containsPoint(pt)) {
              resultMarkers.push(markers[i])
            }
          }
          map.removeOverlay(e.overlay)
          cb(resultMarkers)
        })
      }
    },
    // 多边形选
    _selectPolygon: function (markers, cb, opts) {
      console.log("_selectPolygon方法没实现")
    },
    _drawLine: function (cb, opts) {
      let map = this.map
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        polylineOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_POLYLINE)
        drawingManager.open();
        drawingManager.addEventListener('overlaycomplete', function (e) {
          cb(e.overlay)
        })
      }
    },
    _drawCircle: function (cb, opts) {
      let map = this.map
      // let drawOption = {
      //   strokeColor: '#F6A21C',
      //   fillColor: '#F6A21C',
      //   fillOpacity: 0.3,
      //   strokeOpacity: 1,
      //   strokeWeight: 2,
      //   strokeStyle: 'solid' // 边线的样式，solid或dashed。
      // }
      // drawOption = $.extend({}, drawOption, opts);
      let drawOption = opts
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        circleOptions: drawOption
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_CIRCLE)
        drawingManager.open();
        drawingManager.addEventListener('overlaycomplete', function (e) {
          // cb(e.overlay.point,e.overlay.Ca)
          cb(e.overlay)
          drawingManager.close();
        })
      }
    },
    _drawPolygon: function (cb, enableCalculate, opts) {
      let map = this.map
      // let drawOption = {
      //   strokeColor: '#F6A21C',
      //   fillColor: '#F6A21C',
      //   fillOpacity: 0.3,
      //   strokeOpacity: 1,
      //   strokeWeight: 2,
      //   strokeStyle: 'solid' // 边线的样式，solid或dashed。
      // }
      // drawOption = $.extend({}, drawOption, opts);
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        polygonOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON)
        drawingManager.open();
        if (enableCalculate) {
          drawingManager.enableCalculate();
        }
        drawingManager.addEventListener('overlaycomplete', function (e) {
          if (enableCalculate) {
            drawingManager.disableCalculate();
          }
          cb(e.overlay)
        })
      }
    },
    _drawPoint: function (cb, opts) {
      let map = this.map
      if (opts) {
        if (opts.size) {
          opts.size = new BMap.Size(opts.size.width, opts.size.height)
        }
        if (opts.icon) {
          opts.icon = new BMap.Icon(opts.icon, opts.size)
        }
      }
      let defOption = {
        isOpen: false, // 是否开启绘制模式
        enableDrawingTool: false, // 是否显示工具栏
        markerOptions: opts
      }
      let drawingManager = new BMapLib.DrawingManager(map, defOption);
      if (drawingManager) {
        drawingManager.setDrawingMode(BMAP_DRAWING_MARKER)
        drawingManager.open();
        let self = this;
        drawingManager.addEventListener('overlaycomplete', function (e) {
          cb(e.overlay)
          drawingManager.close();
        })
      }
    },
    _clearDrawOverlays: function (geo) {
      // this.map.removeOverlay(geo);
      if (geo) {
        geo.forEach((item) => {
          this.map.removeOverlay(item);
        })
      }
    }
    // 关闭工具
    // _close: function () {
    //   this.drawingManager.close();
    // }
  }

  // 右键菜单
  baiduMap.ContextMenu = function (menuParent) {
    this.menuParent = menuParent
    let menu = $.extend(new BMap.ContextMenu(), this);
    this.menuParent.addContextMenu(menu)
    return menu
  }
  baiduMap.ContextMenu.prototype = {
    _addItem: function (itemname, handler, index) {
      this.addItem(new BMap.MenuItem(itemname, handler, index));
    },
    _open: function (map) {}
  }

  // 坐标转换
  baiduMap.CoordinateConvert = function () {
    var convertor = new BMap.Convertor();
    this.convertor = convertor;
    return $.extend(convertor, this);
  }
  baiduMap.CoordinateConvert.prototype = {
    // pointArray 数组{lng:lng1,lat:lat1}
    // before：需要转换的坐标类型
    // after: 转换后的坐标类型
    _translate: function (pointArray, before, handler) { // 最多10个点
      let after = 5; // 默认输出都是百度坐标系
      if (this) {
        let bPoints = [];
        for (let i = 0; i < pointArray.length; i++) {
          let bPoint = new BMap.Point(pointArray[i].lng, pointArray[i].lat)
          bPoints.push(bPoint);
        }
        // 84转百度坐标1,5
        // 谷歌坐标转百度坐标3,5
        this.translate(pointArray, before, after, function (data) {
          if (data.status === 0) {
            handler(data.points)
            // return data.points;
          }
        })
      }
    }
  }

  // POI检索服务
  baiduMap.POISearch = function (map) {
    let local = new BMap.LocalSearch(map, {
      renderOptions: {map: map}
    })
    return $.extend(local, this);
  }
  baiduMap.POISearch.prototype = {
    _searchKeywords: function (keyWords) {
      this.search(keyWords)
    }
  }

  // 驾车路线服务
  baiduMap.DriveSearch = function (map) {
    let driving = new BMap.DrivingRoute(map, {
      renderOptions: {
        map: map,
        autoViewport: true
      }
    })
    return $.extend(driving, this);
  }
  baiduMap.DriveSearch.prototype = {
    _searchPath: function (start, end) {
      let startPoint = new BMap.Point(start.lng, start.lat);
      let endPoint = new BMap.Point(end.lng, end.lat);
      this.search(startPoint, endPoint);
    }
  }

  baiduMap.getDistrictPolygon = function (name, handle, opt) {
    let defOptions = {strokeWeight: 2, strokeColor: "#ff0000"}
    if (opt) {
      defOptions = $.extend({}, defOptions, opt)
    }
    var bdary = new BMap.Boundary();
    bdary.get(name, function(rs) {
      var count = rs.boundaries.length; // 行政区域的点有多少个
      if (count === 0) {
        handle()
      }
      var pointArray = [];
      let polygonArray = [];
      for (var i = 0; i < count; i++) {
        var ply = new BMap.Polygon(rs.boundaries[i], defOptions); // 建立多边形覆盖物
        polygonArray.push(ply)
      }
      handle(polygonArray)
    });
  }
  /*
   *图层
   */
  baiduMap.Layer = function (map, name, opts) {
    this.name = name
    this.source = []
    return this;
  }
  baiduMap.Layer.prototype = {
    // 将图层加入地图
    _addToMap: function (map) {
      this.source.forEach((item) => {
        item._add(map);
      })
    },
    _addMarker: function (fea) {
      this.source.push(fea)
    },
    _removeMarker: function (fea) {
      for (let i = 0; i < this.source.length; i++) {
        let item = this.source[i]
        if (item.id == fea.id) {
          this.source.splice(i, 1);
        }
      }
    },
    _show: function () {
      this.source.forEach((item) => {
        item._show();
      })
    },
    _hide: function () {
      this.source.forEach((item) => {
        item._hide();
      })
    },
    _getMarkers: function () {
      return this.source;
    }
  }

  baiduMap.NavigationServer = function (map, opts, callback) {
    this.map = map;
    this.navigationResult = {};
    let trafficType = 1; // 1:驾车，2：步行
    if (opts && opts.trafficType) {
      trafficType = opts.trafficType;
    }
    if (trafficType == 2) {
      var walking = new BMap.WalkingRoute(map, {renderOptions: {map: map, autoViewport: true}});
      return $.extend(walking, this);
    } else {
      let policy = ["BMAP_DRIVING_POLICY_LEAST_TIME", "BMAP_DRIVING_POLICY_LEAST_DISTANCE",
        "BMAP_DRIVING_POLICY_AVOID_HIGHWAYS"]
      let options = {renderOptions: {map: map, autoViewport: true},
        policy: policy[0],
        onSearchComplete: function(results) {
        this.navigationState = driving.getStatus();
          if (driving.getStatus() == BMAP_STATUS_SUCCESS) {
            // let result = driving.getResults();
            // 获取第一条方案
            var plan = results.getPlan(0);
            // 获取方案的驾车线路
            var route = plan.getRoute(0);
            this.navigationResult = {'plan': plan, 'route': route};
          }
          callback(this.navigationState, this.navigationResult);
        }
      }
     var driving = new BMap.DrivingRoute(map, options);
     return $.extend(driving, this);
    }
  }
  baiduMap.NavigationServer.prototype = {
    // 起始点，途经点为经纬度
    searchLatLon(startPt, endPt, routParas, callback) {
      let sPt = new BMap.Point(startPt.lng, startPt.lat)
      let ePt = new BMap.Point(endPt.lng, endPt.lat)
      // driving.search(p1, p2,{waypoints:[p3,p4]});//waypoints表示途经点
      this.search(sPt, ePt)
      let resultCode = this.getStatus();
      let result = this.getResults()
      callback(resultCode, result)
    },
    // 起始点，途经点为文字
    searchName(pts, callback) {
      let start = pts[0].keyword;
      let end = pts[1].keyword
      this.search(start, end)
    }
  }

  // id,name可穿空字符串
  baiduMap.FontIconMarker = function (map, opts) {
    function getClassnamesOfFontIcon(iconFontClass, icon) {
      return (iconFontClass || 'iconfont') + ' ' + icon
    }

    function getContainerClassNames(classNames) {
      return 'amap-simple-marker ' + classNames
    }

    let markerOpts = {
      iconFontClass: 'iconfont',
      containerClassNames: '',
      fontIcon: '',
      title: '', // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      size: new BMap.Size(27, 27),
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
      // anchor: new BMap.Size(15, 30)
    }
    markerOpts = $.extend({}, markerOpts, opts)
    if (opts.position) {
      markerOpts.position = new baiduMap.Point(opts.position.lng, opts.position.lat)
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
        markerCustomLabelDom.innerHTML = label.content
      }
      if (label.className) {
        markerCustomLabelDom.className = label.className
      }
    }
    markerIconDom.className = 'amap-simple-marker-icon'
    markerLabelDom.className = 'amap-simple-marker-label'
    markerFontIconDom.className = markerOpts.getClassnamesOfFontIcon(markerOpts.iconFontClass, markerOpts.fontIcon)
    let marker = new BMapLib.RichMarker(markerDom, markerOpts.position, markerOpts)
    if (label) {
      this.markerCustomLabelDom = document.createElement('div')
      let markerCustomLabelDom = this.markerCustomLabelDom
      let domNodes = marker.domNodes
      domNodes.container.appendChild(markerCustomLabelDom)
      if (label.className) {
        this.markerCustomLabelDom.className = label.className
      }
      if (label.content) {
        markerCustomLabelDom.innerHTML = label.content
      }
      if (label.className) {
        markerCustomLabelDom.className = label.className
      }
    }
    map.addOverlay(marker)
    markerDom.parentElement.style.background = 'transparent'
    this.protoSelf = marker
    this.map = map
    this.visible = true
    this.opts = markerOpts
    this.extData = markerOpts.extData
    markerOpts.preFontIcon = markerOpts.fontIcon
    return ($.extend(marker, this))
  }
  baiduMap.FontIconMarker.prototype = {
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
      // let toffset = new AMap.Pixel(offset.x, offset.y)
      // this.setOffset(toffset)
    },
    getMarkerOffset: function () {
      // let toffset = this.getOffset()
      // return {
      //   x: toffset.getX(),
      //   y: toffset.getY()
      // }
    },
    _setPosition: function (lnglan) {
      let pos = new baiduMap.Point(lnglan.lng || lnglan[0], lnglan.lat || lnglan[1])
      return this.setPosition(pos)
    },
    _getPosition: function () {
      return this.getPosition()
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
    setMap: function (map) {
      if (map) {
        map.addOverlay(this)
      } else {
        this.map.removeOverlay(this)
      }
    },
    _setMap: function (map) {
      this.setMap(map)
    },
    _show: function () {
      // this.show()
      this.markerDom.style.display = 'block'
      this.visible = true
    },
    _hide: function () {
      this.markerDom.style.display = 'none'
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
      this.addEventListener('on' + event, handler)
    },
    _infoTipShow: function () {
      // 空方法，兼容openalyer写的
    },
    _infoTipClose: function () {
      // 空方法，兼容openalyer写的
    }
  }

  return baiduMap;
}
export default baiduMap
