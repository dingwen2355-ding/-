
// function arcGisMap(esri, dojo, arcgisapi) {
import $ from "jquery";
function arcGisMap(mapServeUrl, dojo,
                   Measure,
                   ClusterLayer,
                   Map, PictureMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol, SimpleMarkerSymbol,
                   TileInfo, TiledMapServiceLayer, FeatureLayer, GraphicsLayer, ArcGISTiledMapServiceLayer,
                   Point, Circle, webMercatorUtils, SpatialReference, ClassBreaksRenderer, Measurement, Draw,
                   HeatmapRenderer, InfoWindowBase, InfoWindow, Scalebar, OverviewMap, Graphic, dom, units, Font,
                   TextSymbol, Color, Polyline, Extent, Polygon, geometryEngine, InfoTemplate, GeometryService, Menu, MenuItem) {
  let AMap = Map;
  let drawVector;
  let menu;
  let tempGraphicLayer; // 测距等临时绘制图层
  var arcGisMap = {};
  arcGisMap.Constants = {
    ZOOM_CHANGED: "zoom",
    ZOOM_END: "zoom-end",
    DRAG_END: "mouse-drag-end",
    MOUSEMOVE: "mouse-move",
    // CLICK: 'onClick', // graphic点击事件
    CLICK: 'click', // map点击事件click, graphic点击事件onClick
    DBLCLICK: 'dbl-click', // 双击事件
    RIGHTCLICK: 'contextmenu',
    LOADED: 'load', // 地图加载事件
    MOUSEOVER: 'onMouseOver', // graphic
    MOUSEOUT: 'onMouseOut' // graphic
  };

  arcGisMap.Map = function (mapDiv, opts, mapType, mapConfig) {
    var map = Map(mapDiv, {
      // center: (opts.center && opts.center.lng) ? Point(opts.center.lng, opts.center.lat) : Point(118.6589, 32.1474),
      zoom: opts.zoom,
      logo: false,
      slider: false,
      minZoom: Number(opts.minZoom),
      maxZoom: Number(opts.maxZoom)
    })
    // let layer = getLayer(mapServeUrl || "http://10.45.157.179:8008", dojo, TiledMapServiceLayer, SpatialReference, Point, TileInfo, Extent); // 现有的ArcGIS瓦片
    // let basicUrl = "https://services.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer";
    // var layer = new esri.layers.ArcGISTiledMapServiceLayer(basicUrl);
    let layer = createMapLayer(Object.assign({
      mapServeUrl: mapServeUrl,
      mapType: mapType
    }, mapConfig), dojo, TiledMapServiceLayer, SpatialReference, Point, TileInfo, Extent)
    this.spatialReference = layer.spatialReference;
    map.spatialReference = layer.spatialReference
    map.addLayer(layer);
    this.protoSelf = map;
    // 新建一个画图工具
    this.mouseTool = new Draw(map, {showTooltips: false});
    // 添加graphiclayer，存储设备
    tempGraphicLayer = new GraphicsLayer({"id": "tempGraphicLayer"});
    map.addLayer(tempGraphicLayer);
    map.centerAt(new Point(opts.center.lng, opts.center.lat, layer.spatialReference))
    // dojo.connect(map.graphics, "onMouseOver", function(evt) {
    //   var g = evt.graphic;
    //   map.infoWindow.setContent(g.title);
    //   map.infoWindow.setTitle(g.title);
    //   map.infoWindow.show(evt.screenPoint, map.getInfoWindowAnchor(evt.screenPoint));
    // });
    // dojo.connect(map.graphics, "onMouseOut", function(evt) {
    //   var g = evt.graphic;
    //   map.infoWindow.setContent(g.title);
    //   map.infoWindow.setTitle(g.title);
    //   map.infoWindow.hide();
    // });
  };

  /**
   map接口
   */
  arcGisMap.Map.prototype = {
    _setView: function () {
      console.log("_setView方法没有实现")
    },
    _getCenter: function () {
      // this.protoSelf.extent.getCenterPoint()
      var lat = this.protoSelf.extent.getCenter().getLatitude();
      var lng = this.protoSelf.extent.getCenter().getLongitude();
      return {lng: lng, lat: lat}
    },
    _getView: function () {
      let mapExtent = this.protoSelf.extent;
      return new arcGisMap.Bounds(mapExtent)
    },
    _getZoom: function () {
      return this.protoSelf.getZoom()
    },
    setStatus: function () {

    },
    _centerAndZoom: function (point, zoom) {
      point[0] = parseFloat(point[0]);
      point[1] = parseFloat(point[1]);
      this.protoSelf.setZoom(zoom);
      this._center(point);
    },
    _center: function (point) {
      point[0] = parseFloat(point[0]);
      point[1] = parseFloat(point[1]);
      this.protoSelf.centerAt(new arcGisMap.Point(point[0], point[1], this.spatialReference));
    },
    _zoom: function (zoom) {
      // this.getView().setZoom(zoom - 3);
      this.protoSelf.setZoom(zoom);
    },
    getZoom: function () {
      return this.protoSelf.getZoom();
    },
    getBounds: function () {
      var mapExtent = this.protoSelf.geographicExtent;
      return new arcGisMap.Bounds(mapExtent);
    },
    _addOverlay: function (layer) { // 此方法是添加图层
      this.protoSelf.graphics.add(layer.protoSelf);
    },
    _addLayer: function (layer) {
      this.protoSelf.addLayer(layer);
    },
    _removeLayer: function (layer) {
      this.protoSelf.removeLayer(layer); // 此方法是移除图层
    },
    _removeOverlays: function (overlays) {
      overlays.forEach((item) => {
        this.protoSelf.removeLayer(item);
      })
    },
    _removeOverlay: function (overlay, isLayer) {
      if (isLayer) {
        this.protoSelf.removeLayer(overlay);
      } else {
        this.protoSelf.graphics.remove(overlay.protoSelf);
      }
    },
    _clearOverlays: function () {
      this.protoSelf.removeLayers();
    },
    _addControl: function (control) {
      control.show();
    },
    _removeControl: function (control) {
      control.hide();
    },
    _addTrafficeLayer: function () {
      /*var trafficLayer = new AMap.TileLayer.Traffic({
        zIndex: 10
      });
      trafficLayer.setMap(this);
      this._trafficLayer = trafficLayer;*/
    },
    _removeTrafficeLayer: function () {
      if (this._trafficLayer) {
        this._trafficLayer.setMap(null);
      }
    },
    _getAllOverlays: function (type) {
      return this.protoSelf.getLayers();
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      let self = this;
      this.protoSelf.on(event, function (e) {
        let mapPoint = e.mapPoint;
        // handler(self.webMercatorToGeographic(mapPoint));
        // 返回参数统一
        let pt = self.webMercatorToGeographic(mapPoint)
        let znvPt = new arcGisMap.Point(pt.x, pt.y, pt.spatialReference)
        handler(znvPt)
      });
    },
    webMercatorToGeographic: function (geo) {
      // if (geo.spatialReference && geo.spatialReference.wkid == '3857') {
      if (geo.spatialReference && geo.spatialReference.wkid != '4326' && geo.spatialReference.wkid != '4490') {
        // geo = esri.geometry.webMercatorUtils.webMercatorToGeographic(geo);
        geo = webMercatorUtils.webMercatorToGeographic(geo);
      }
      return geo;
    },
    geographicToWebMercator: function (latlng) {
      // var flatXY = esri.geometry.webMercatorUtils.geographicToWebMercator(latlng);
      var flatXY = webMercatorUtils.geographicToWebMercator(latlng);
      return {lng: flatXY.x, lat: flatXY.y}
    },
    _on: function (event, handler) {
      if (!this.eventHandlerMap) {
        this.eventHandlerMap = {};
      }
      this.eventHandlerMap[event] = handler;
      this.protoSelf.on(event, function (e) {
        handler(e);
      });
    },
    addExLayer: function (graphic) { // 添加marker,线，多边形，圆等
      this.protoSelf.graphics.add(graphic);
    },
    removeExLayer: function (graphic) { // 添加marker,线，多边形，圆等
      this.protoSelf.graphics.remove(graphic);
    },
    // destroy: function () {
    //   return this.destroy();
    // },
    _destroy: function () {
      return this.protoSelf.destroy();
    },
    _setMapStyle: function (styleName) {

    }
    // 区域定位，临时这样用下
    // 南京 118.792819,32.053766
    // 孝感 113.910588,30.933866
    // 银川106.227256,38.486691
    // 重庆 106.69037,25.54006
    // setCity: function (areaName) {
    //   var p = new arcGisMap.Point(113.910588, 30.933866);
    //   this._center(p);
    // }
  };

  arcGisMap.Bounds = function (mapExtent) {
    this.wlng = mapExtent.xmin;
    this.elng = mapExtent.xmax;
    this.slat = mapExtent.ymin;
    this.nlat = mapExtent.ymax;
    this.northeast = [this.elng, this.nlat]
    this.southwest = [this.wlng, this.slat]
    this.protoSelf = mapExtent;
  };

  arcGisMap.Bounds.prototype = {
    getNorthEast: function () {
      return new arcGisMap.Point(this.elng, this.nlat);
    },
    getSouthWest: function () {
      return new arcGisMap.Point(this.wlng, this.slat);
    },
    containsLngLat: function (position) {
      let res = false;
      let lng = position.getLng ? position.getLng() : position[0]
      let lat = position.getLat ? position.getLat() : position[1]
      if (this.elng >= lng && this.wlng <= lng && this.slat <= lat && this.nlat >= lat) {
        res = true;
      }
      // res = this.protoSelf.contains(position);
      return res;
    }
  };

  arcGisMap.Marker = function (map, opts) {
    // id, name, pt, opts, extData, size
    this.extData = opts.extData;
    this.position = new arcGisMap.Point(opts.position.lng, opts.position.lat, map.protoSelf.spatialReference);
    let defOpts = {
      "url": "",
      "height": 20,
      "width": 20,
      'xoffset': 0,
      'yoffset': 0,
      positioning: 'top-left'
    }
    defOpts.url = opts.icon;
    defOpts.width = opts.size.width;
    defOpts.height = opts.size.height;
    defOpts.offset = opts.offset;
    defOpts.position = this.position;
    defOpts.map = map;
    this.markerDom = document.createElement("div");
    this.iconDom = document.createElement("div");
    this.labelDom = document.createElement("div"); // 设备标注
    if (opts.icon) {
      var img = document.createElement("img");
      img.src = opts.icon;
      img.style.cursor = "pointer";
      img.width = defOpts.width;
      img.height = defOpts.height;
      this.iconDom.appendChild(img)
      this.markerImgDom = img;
    }
    this.markerDom.appendChild(this.iconDom);
    this.markerDom.appendChild(this.labelDom);
    defOpts.element = this.markerDom;
    let marker = new arcGisMap.ElementLayer(defOpts)
    // let symbol = new esri.symbol.PictureMarkerSymbol(defOpts);
    // let marker = new esri.Graphic(this.position, symbol, null, null);
    this.opts = defOpts;
    var name = "";
    if (opts.extData) {
      name = opts.extData.mtitle; // 标注字段
      if (opts.extData.deviceName) {
        name = opts.extData.deviceName;
      } else if (opts.extData.buildingName) {
        name = opts.extData.buildingName;
      } else if (opts.extData.name) {
        name = opts.extData.name; // precinctName;
      } else if (opts.extData.companyTypeName) {
        name = opts.extData.companyTypeName;
      }
    }

    this.title = name;
    if (name) {
      this.iconDom.title = name;
    }
    this.map = map;
    this.protoSelf = marker;
    marker.map = map;
    marker.position = this.position;
  };

  arcGisMap.Marker.prototype = {
    _getPosition: function () {
      return this.position;
    },
    _setPosition: function (lnglan) {
      this.position = new arcGisMap.Point(lnglan.lng, lnglan.lat);
      this.protoSelf.setPosition(this.position);
    },
    _getIcon: function () {
      let imgDom = this.markerImgDom;
      return imgDom && imgDom.src;
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
      let imgDom = this.markerImgDom;
      imgDom && (imgDom.src = opts.icon);
    },
    setzIndex: function (zIndex) {
      this.protoSelf.setzIndex(zIndex);
    },
    _setLabel: function (labelOpts) {
      this.labelDom.innerHTML = labelOpts.content;
    },
    _setMap: function (map) {
      if (map) {
        this._add();
        this.map = map;
      } else {
        this._remove();
      }
    },
    _show: function () {
      this.protoSelf.show();
      this.visible = true;
    },
    _hide: function () {
      this.protoSelf.hide();
      this.visible = false;
    },
    _add: function () {
      this.visible = true;
      if (!this.isAdded) {
        this.protoSelf.add();
        this.isAdded = true;
      }
      this._show();
    },
    _remove: function () {
      //  this._hide();
      this.isAdded = false;
      this.visible = false;
      this.protoSelf.remove();
    },
    _getMap: function () {
      return this.map;
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      var m = this;
      if (this.iconDom.attachEvent) {
        this.imgdiv.attachEvent(event, function () {
          handler && handler(m);
        });
      } else {
        this.iconDom.addEventListener(event, function () {
          handler && handler(m);
        });
      }
    },
    _infoTipShow: function () {

    },
    _infoTipClose: function () {

    }
  };

  /*arcGisMap.Marker = function (map, opts) {
    //  参数标准化
    var defOption = {
      type: "picture-marker",
      url: '../../map/images/defaultPoint.png',
      width: 32,
      height: 32
    }
    var options = $.extend({}, defOption, opts)
    if (opts.size) {
      options.width = opts.size.width
      options.height = opts.size.height
    }
    let iconUrl = opts.icon || (opts.extData ? opts.extData.icon : null)
    if (iconUrl) {
      options.url = iconUrl
    }
    this.id = opts.id // 必传
    this.map = map
    this.extData = opts.extData;
    this.layerName = opts.layerName // 图层名称
    this.title = opts.title // Marker的提示信息
    this.position = new arcGisMap.Point(opts.position.lng, opts.position.lat, map.protoSelf.spatialReference);
    var flatPos = map.webMercatorToGeographic(this.position)
    this.x = flatPos.lng;
    this.y = flatPos.lat;
    this.attributes = {"imageUrl": options.url, "extData": this.extData} // 聚合使用此处信息，必填。
    var pictureSymbol = new PictureMarkerSymbol(options.url, options.width, options.height);
    this.symbol = pictureSymbol;
    var graphic = new Graphic(this.position, pictureSymbol);
    return $.extend(graphic, this)
  }
  arcGisMap.Marker.prototype = {
    _add() {
      this.map.protoSelf.graphics.add(this);
    },
    _remove: function () {
      this.map.protoSelf.graphics.remove(this);
    },
    _setAngle(angle) {
      this.symbol.setAngle(angle)
    },
    _setSize(size) {
      this.symbol.setHeight(size.height)
      this.symbol.setWidth(size.width)
    },
    _setIcon(url) {
      this.symbol.setUrl(url);
      this.map.protoSelf.graphics.redraw(); // 重新绘制，刷新功能
    },
    _getIcon: function () {
      return this.symbol.url
    },
    _getPosition: function () {
      return this.position
    },
    _setPosition: function (lnglan) {
      var geom = new arcGisMap.Point(lnglan.lng, lnglan.lat);
      this.setGeometry(geom);
      this.position = geom;
      // 标注修改
      this.map.protoSelf.graphics.remove(this.label);
      this._setLabel(this.labelOption)
    },
    // setzIndex: function (zIndex) {
    //   this.style.graphicZIndex = zIndex
    // },
    _setLabel: function (label) {
      if (label == undefined) {
        label = []
      }
      var title = label ? (label.content ? label.content : this.title) : this.title
      var color = label ? (label.fontColor ? label.fontColor : "#800000") : "#800000"
      var fontsize = label ? (label.fontSize ? label.fontSize + "pt" : "10pt") : "10pt"
      // var offsetX = label ? (label.offset ? label.offset.x : 0) : 0
      // var offsetY = label ? (label.offset ? label.offset.y : -35) : -35
      var offsetX = 0
      var offsetY = -35
      var textsymbol = new TextSymbol(title)
      textsymbol.setColor(new dojo.Color(color))
      textsymbol.setFont(new Font(fontsize)) // setFont设置文本大小
      textsymbol.setOffset(offsetX, offsetY); // 设置偏移方向
      var graphic = new Graphic(this.position, textsymbol);
      this.labelOption = label
      this.label = graphic
      this.attributes.label = graphic // 聚合使用此处信息
      this.lableSymbol = textsymbol
      this.map.protoSelf.graphics.add(graphic);
    },
    _show: function () {
      this.show()
      if (this.label) {
        this.label.show();
      }
    },
    _hide: function () {
      this.hide()
      if (this.label) {
        this.label.hide();
      }
    },
    _addEventListener: function (event, handler) {
      if (event == 'click') event = 'onClick' // graphic onClick
      dojo.connect(this.map.protoSelf.graphics, event, function (e) {
        var graphic = e.graphic
        handler(graphic)
      })
    }
  }*/
var currentLocation;
  // 右键菜单
  arcGisMap.ContextMenu = function (obj) {
    this.marker = obj;
    this.map = obj.protoSelf;
    if (this.map && this.map.container) {
      menu = new Menu({
        onOpen: function (box) {
          currentLocation = getMapPointFromMenuPosition(box, this.map);
        }
      });
      menu.startup();
      menu.bindDomNode(this.map.container);
    } else {
      var map = this.marker.map.protoSelf;
      menu = new Menu();
      menu.startup();
      map.graphics.on("mouse-over", function(evt) {
        menu.bindDomNode(evt.graphic.getDojoShape().getNode());
      });
      map.graphics.on("mouse-out", function(evt) {
        menu.unBindDomNode(evt.graphic.getDojoShape().getNode());
      });
    }

    return ($.extend(menu, this));
  };
  // 确定弹窗的位置
  function getMapPointFromMenuPosition(box, map) {
    var x = box.x, y = box.y;
    switch (box.corner) {
      case "TR":
        x += box.w;
        break;
      case "BL":
        y += box.h;
        break;
      case "BR":
        x += box.w;
        y += box.h;
        break;
    }
    var screenPoint = new Point(x - map.position.x, y - map.position.y);
    return map.toMap(screenPoint);
  }
  arcGisMap.ContextMenu.prototype = {
    _addItem: function (itemname, handler, index) {
      var item = new MenuItem({
        label: itemname,
        onClick: function (e) {
          handler(e)
        }
      })
      this.addChild(item);
    },
    _open: function (map) {
    }
  };

// 图标对象
  arcGisMap.Icon = function (imgUrl, size, imageOffset) {
    /*var opts = {size: new AMap.Size(size[0], size[1]), image: imgUrl};
    if (imageOffset) {
      opts.imageOffset = new AMap.Pixel(imageOffset[0], imageOffset[1]);
    }
    return new AMap.Icon(opts);*/
  };

// 点对象
  arcGisMap.Point = function (lng, lat, spatialReference) {
    this.lng = lng;
    this.lat = lat;
    let point;
    if (spatialReference && spatialReference.wkid !== 102100) {
      point = new Point(lng, lat, spatialReference);
    } else {
      point = new Point(lng, lat);
    }
    return ($.extend(point, this));
  };

  arcGisMap.Point.prototype = {
    _getLng: function () {
      return this.lng;
    },
    _getLat: function () {
      return this.lat;
    },
    getLng: function () {
      return this.lng;
    },
    getLat: function () {
      return this.lat;
    },
    _getLnglat: function () {
      return [this.lng, this.lat];
    }
  };

  // 地图上加线 -- ol.layer.Vector
  arcGisMap.Polyline = function (map, lineArr, opts) {
    let PolylineOpts = {
      strokeWeight: 3,
      color: "#ff0000"
    };
    if (opts) {
      PolylineOpts = $.extend({}, PolylineOpts, opts)
    }
    let polyline = new Polyline();
    let symbol = new SimpleLineSymbol(PolylineOpts.strokeStyle, hexToRgba(PolylineOpts.strokeColor, PolylineOpts.strokeOpacity), PolylineOpts.strokeWeight)
    let clineArr = [];
    for (let i = 0; i < lineArr.length; i++) {
      let item = lineArr[i];
      let pt = new arcGisMap.Point(item.lng, item.lat);
      clineArr.push(pt);
    }
    polyline.addPath(clineArr);
    let polylineGraphic = new Graphic(polyline, symbol);
    // map.addExLayer(polylineGraphic);
    this.map = map;
    this.protoSelf = polylineGraphic;
  };

  arcGisMap.Polyline.prototype = {
    hide: function () {
      this.protoSelf.hide();
    },
    show: function () {
      this.protoSelf.show();
    },
    remove: function () {
      this.map.removeExLayer(this.protoSelf);
    }
  };

  function hexToRgba(val, opacity) { // HEX十六进制颜色值转换为RGB(A)颜色值
    var a, b, c;
    if ((/^#/g).test(val)) {
      a = val.slice(1, 3);
      b = val.slice(3, 5);
      c = val.slice(5, 7);
      return [parseInt(a, 16), parseInt(b, 16), parseInt(c, 16), opacity];
    } else {
      return "";
    }
  }

  arcGisMap.Polygon = function (map, opt) {
    var defOpts = {
      path: [],
      strokeColor: "#0010ff", // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: "solid", // 线样式
      fillColor: "#0010ff",
      fillOpacity: 0.2
    };
    Object.assign(defOpts, opt);
    let newPath = [];
    defOpts.path.forEach((item) => {
      item.lng && newPath.push([item.lng, item.lat])
      item[0] && newPath.push([item[0], item[1]])
    })
    defOpts.path = newPath;
    let fillColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
    let strokeColor = new Color(hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity));
    let lsymbol = new SimpleLineSymbol(defOpts.strokeStyle, strokeColor, defOpts.strokeWeight)
    let symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, lsymbol, fillColor);
    // 多边形此处注意一定要是[坐标数组]
    var polygon = new Polygon([defOpts.path]);
    polygon.addRing([defOpts.path]);
    let polygonGraphic = new Graphic(polygon, symbol);
    // map.protoSelf.graphics.add(polygonGraphic); // 通过外边加入
    this.map = map;
    this.extData = defOpts.extData;
    this.opts = defOpts;
    this.feature = polygon;
    this.polygon = polygon;
    this.protoSelf = polygonGraphic;
    if (opt.isFresh) { // 自动调整多边形到地图中心
    }
    return $.extend(polygon, this)
  };
  arcGisMap.Polygon.prototype = {
    _hide: function () {
      this.protoSelf.hide();
    },
    _show: function () {
      this.protoSelf.show();
    },
    hide: function () {
      this.protoSelf.hide();
    },
    show: function () {
      this.protoSelf.show();
    },
    _remove: function () {
      this.map.removeExLayer(this.protoSelf);
    },
    _contains: function (lng, lat) {
      this.polygon.contains(arcGisMap.Point(lng, lat))
    },
    _setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts);
      let oldSymbol = this.protoSelf.symbol;
      let newColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
      let newSymbol = oldSymbol.setColor(newColor);
      this.protoSelf.setSymbol(newSymbol)
    },
    on: function (event, handler) {
      let feature = this.feature;
      feature.addEventListener(event, function () {
        handler();
      })
    },
    _addEventListener: function (event, handler) {
      if (event) {
        let eventName = event;
        if (event === "click") {
          eventName = "onClick"
          if (this.map.protoSelf.graphics.onClick.length == 0) {
            // 事件加在图层上，需要确认只加一次
            dojo.connect(this.map.protoSelf.graphics, eventName, function (e) {
              console.log(e)
              handler();
            });
          }
        } else {
          // if (this.map.protoSelf.graphics.onMouseMove.length == 0) {
            dojo.connect(this.map.protoSelf.graphics, eventName, function (e) {
              handler();
            });
          // }
        }
      }
    }
  };

  arcGisMap.Circle = function (map, opt) {
    let defOpts = {
      radius: 100,
      radiusUnit: units.METERS, // KILOMETERS
      strokeColor: '#FF33FF',
      strokeOpacity: 0,
      fillOpacity: 0.4,
      fillColor: '#1791fc',
      zIndex: 50,
      geodesic: true
    }
    Object.assign(defOpts, opt);
    defOpts.center = [defOpts.center.lng, defOpts.center.lat]
    let circle = new Circle(defOpts)
    let fillColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
    let strokeColor = new Color(hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity));
    let lsymbol = new SimpleLineSymbol(defOpts.strokeStyle || SimpleLineSymbol.STYLE_SOLID, strokeColor, defOpts.strokeWeight || 3)
    let symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, lsymbol, fillColor);
    let circleGraphic = new Graphic(circle, symbol);
    // map.addExLayer(polygonGraphic); // 通过外边加入
    this.map = map;
    this.extData = defOpts.extData;
    this.opts = defOpts;
    this.feature = circle;
    this.protoSelf = circleGraphic;
  };
  arcGisMap.Circle.prototype = {
    hide: function () {
      this.protoSelf.hide();
    },
    show: function () {
      this.protoSelf.show();
    },
    remove: function () {
      this.map.removeExLayer(this.protoSelf);
    },
    contains: function (lng, lat) {
      this.polygon.contains(arcGisMap.Point(lng, lat))
    },
    _setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts);
      let oldSymbol = this.protoSelf.symbol;
      let newColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
      let newSymbol = oldSymbol.setColor(newColor);
      this.protoSelf.setSymbol(newSymbol)
    },
    on: function (event, handler) {
      let feature = this.feature;
      feature.addEventListener(event, function () {
        handler();
      })
    },
    _addEventListener: function (event, handler) {
      if (event) {
        let eventName = event;
        if (event === "click") {
          eventName = "onClick"
          if (this.map.protoSelf.graphics.onClick.length == 0) {
            // 事件加在图层上，需要确认只加一次
            dojo.connect(this.map.protoSelf.graphics, eventName, function (e) {
              console.log(e)
              handler();
            });
          }
        } else {
          // if (this.map.protoSelf.graphics.onMouseMove.length == 0) {
          dojo.connect(this.map.protoSelf.graphics, eventName, function (e) {
            handler();
          });
          // }
        }
      }
    }
  };

  arcGisMap.Rectangle = function (map, opt) {
    var defOpts = {
      strokeColor: "#0010ff", // 线颜色
      strokeOpacity: 0.75, // 线透明度
      strokeWeight: 1, // 线宽
      strokeStyle: "solid", // 线样式
      fillColor: "#0010ff",
      fillOpacity: 0.2
    };
    Object.assign(defOpts, opt);
    let bounds = opt.bounds;
    defOpts.path = [
      [bounds.southwest[0], bounds.northeast[1]],
      [bounds.southwest[0], bounds.southwest[1]],
      [bounds.northeast[0], bounds.southwest[1]],
      [bounds.northeast[0], bounds.northeast[1]],
      [bounds.southwest[0], bounds.northeast[1]]
    ]

    let fillColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
    let strokeColor = new Color(hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity));
    let lsymbol = new SimpleLineSymbol(defOpts.strokeStyle || SimpleFillSymbol.STYLE_SOLID, strokeColor, defOpts.strokeWeight || 3)
    let symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, lsymbol, fillColor);
    // 多边形此处注意一定要是[坐标数组]
    var polygon = new Polygon([defOpts.path]);
    polygon.addRing([defOpts.path]);
    let polygonGraphic = new Graphic(polygon, symbol);
    // map.addExLayer(polygonGraphic); // 通过外边加入
    this.map = map;
    this.extData = defOpts.extData;
    this.opts = defOpts;
    this.feature = polygon;
    this.rectangle = polygon;
    this.protoSelf = polygonGraphic;
    return this;
  };
  arcGisMap.Rectangle.prototype = {
    hide: function () {
      this.protoSelf.hide();
    },
    show: function () {
      this.protoSelf.show();
    },
    remove: function () {
      this.map.removeExLayer(this.protoSelf);
    },
    contains: function (lng, lat) {
      this.polygon.contains(arcGisMap.Point(lng, lat))
    },
    setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts);
      let feature = this.feature;
      feature.style_.setFill(new ol.style.Fill({
        color: hexToRgba(defOpts.fillColor, opts.fillOpacity)
      }));
      feature.style_.setStroke(new ol.style.Stroke({
        color: hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity),
        width: defOpts.strokeWeight
      }))
      feature.setStyle(feature.style_);
    },
    on: function (event, handler) {
      let feature = this.feature;
      feature.addEventListener(event, function () {
        handler();
      })
    }
  };

  arcGisMap.InfoWindow = function (content, opts) {
    var map = opts.map.protoSelf;
    if (!map) {
      return
    }
    this.map = map
    var pos = new arcGisMap.Point(opts.lnglat.lng, opts.lnglat.lat);
    this.position = pos;
    var title = opts.title || "信息"
    var infoWindow = new InfoTemplate();
    infoWindow.setTitle(title);
    infoWindow.setContent(content)
    return $.extend(infoWindow, this);
  };

  arcGisMap.InfoWindow.prototype = {
    _open: function (map, marker) {
      marker.setInfoTemplate(this);
      // this.map.infoWindow.show();
    },
    _close: function () {
      this.map.infoWindow.hide();
    },
    _stopPropagation: function () {

    }
  };

  /*
 *图层
 */
  arcGisMap.Layer = function (map, name, opts) {
    var glayer = new GraphicsLayer();
    this.map = map;
    this.name = name
    glayer.id = name
    this.source = []
    return $.extend(glayer, this);
  }
  arcGisMap.Layer.prototype = {
    // 将图层加入地图
    _addToMap: function (map) {
      this.map.protoSelf.addLayer(this);
    },
    _addMarker: function (fea) {
      this.add(fea)
    },
    _removeMarker: function (fea) {
      this.remove(fea)
    },
    _show: function () {
      this.show();
    },
    _hide: function () {
      this.hide()
    },
    _getMarkers: function () {
      return this.graphics
    }
  }

  arcGisMap.FontIconMarker = function (map, opts) {
    let markerOpts = {
      map: map,
      containerClassNames: "",
      anchor: 'top-left', // 锚点 设置点标记锚点。默认值：'top-left' 可选值：'top-left'|'top-center'|'top-right'|'middle-left'|'center'|'middle-right'|'bottom-left'|'bottom-center'|'bottom-right'
      fontIcon: '',
      label: '',
      title: "", // 鼠标滑过点标记时的文字提示，不设置则鼠标滑过点标无文字提示
      position: {lng: "", lat: ""},
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
      url: "",
      height: 20,
      width: 20,
      xoffset: 0,
      yoffset: 0,
      positioning: 'top-left'
    };
    markerOpts = $.extend({}, markerOpts, opts);
    if (opts.position) {
      markerOpts.position = new arcGisMap.Point(opts.position.lng, opts.position.lat, map.protoSelf.spatialReference)
    }
    let offset = [0, 0];
    if (opts.offset) {
      offset[0] = opts.offset.x;
      offset[1] = opts.offset.y;
    }
    var iconLabel = markerOpts.iconLabel;
    if (!(typeof iconLabel == 'object')) {
      iconLabel = {};
    }
    markerOpts.iconLabel = iconLabel;
    if (!markerOpts.isCluster) {
      markerOpts.map = map;
    }
    this.markerDom = document.createElement("div");
    let markerDom = this.markerDom;
    markerDom.className = markerOpts.getContainerClassNames(markerOpts.containerClassNames);
    markerDom.title = markerOpts.title;
    this.markerIconDom = document.createElement("div");
    let markerIconDom = this.markerIconDom;
    this.markerLabelDom = document.createElement("div");
    let markerLabelDom = this.markerLabelDom;
    this.markerFontIconDom = document.createElement("i");
    let markerFontIconDom = this.markerFontIconDom;
    markerDom.appendChild(markerIconDom)
    markerDom.appendChild(markerLabelDom)
    markerLabelDom.appendChild(markerFontIconDom);
    let label = markerOpts.cLabel;
    if (label) {
      this.markerCustomLabelDom = document.createElement("div");
      let markerCustomLabelDom = this.markerCustomLabelDom;
      markerDom.appendChild(markerCustomLabelDom);
      if (label.content) {
        markerCustomLabelDom.innerHTML = label.content;
      }
      if (label.className) {
        markerCustomLabelDom.className = label.className;
      }
    }
    markerIconDom.className = "amap-simple-marker-icon";
    markerLabelDom.className = "amap-simple-marker-label";
    markerFontIconDom.className = markerOpts.getClassnamesOfFontIcon(markerOpts.fontIcon);
    markerOpts.element = markerDom;
    var marker = new arcGisMap.ElementLayer(markerOpts)
    markerOpts.preFontIcon = markerOpts.fontIcon;
    this.protoSelf = marker;
    this.map = map;
    this.visible = false;
    this.opts = markerOpts;
    this.extData = markerOpts.extData;
    if (markerOpts.map) {
      this.visible = true;
      // map.addOverlay(marker);
      this.map = map;
    }
    this.protoSelf = marker;
    marker.map = map;
    // marker.position = this.position;
    // return ($.extend(marker, this));
  };
  arcGisMap.FontIconMarker.prototype = {
    setFontIcon: function (fontIcon, isModifyPre) {
      if (isModifyPre) {
        this.opts.preFontIcon = fontIcon;
      }
      this.opts.fontIcon = fontIcon;
      this.markerFontIconDom.className = this.opts.getClassnamesOfFontIcon(fontIcon);
    },
    setMarkerClasses: function (classNames) {
      this.markerDom.className = this.opts.getContainerClassNames(classNames);
      this.opts.containerClassNames = classNames;
    },
    addMarkerClasses: function (classNames) {
      let cClassNames = this.opts.containerClassNames;
      cClassNames = cClassNames + " " + classNames;
      this.opts.containerClassNames = cClassNames;
      this.markerDom.className = this.opts.getContainerClassNames(cClassNames);
    },
    removeMarkerClasses: function (classNames) {
      let cClassNames = this.opts.containerClassNames;
      let cAttr = classNames.split(/(\s)+/);
      cAttr.forEach((item) => {
        cClassNames = cClassNames.replace(item, "")
      })
      this.opts.containerClassNames = cClassNames;
      this.markerDom.className = this.opts.getContainerClassNames(cClassNames);
    },
    setMarkerOffset: function (offset) {
      this.protoSelf.setOffset(offset);
    },
    getMarkerOffset: function () {
      let toffset = this.protoSelf.getOffset();
      return toffset;
    },
    _getPosition: function () {
      return this.opts.position;
    },
    _setPosition: function (lnglan) {
      this.position = new arcGisMap.Point(lnglan[0], lnglan[1]);
      this.protoSelf.setPosition(this.position);
    },
    setzIndex: function (zIndex) {
      $(this.markerDom).parent().css("z-index", zIndex);
      // this.setElement(imgdiv);
    },
    _setLabel: function (label) {
      let mlabel = this.markerCustomLabelDom;
      if (!this.markerCustomLabelDom) {
        mlabel = this.markerCustomLabelDom = document.createElement("div");
        this.markerDom.appendChild(mlabel);
      }
      if (label.content) {
        mlabel.innerHTML = label.content;
      }
      if (label.className) {
        mlabel.className = label.className;
      }
      this.opts.cLabel = label;
    },
    _setMap: function (map) {
      if (map) {
        this._add();
        this.map = map;
      } else {
        this._remove();
      }
    },
    _show: function () {
      this.protoSelf.show();
      this.visible = true;
    },
    _hide: function () {
      this.protoSelf.hide();
      this.visible = false;
    },
    _add: function () {
      this.visible = true;
      if (!this.isAdded) {
        this.protoSelf.add();
        this.isAdded = true;
      }
      this._show();
    },
    _remove: function () {
      //  this._hide();
      this.isAdded = false;
      this.visible = false;
      this.protoSelf.remove();
    },
    _getMap: function () {
      return this.getMap();
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      var m = this;
      if (this.markerDom.attachEvent) {
        this.markerDom.attachEvent(event, function () {
          handler(m);
        });
      } else {
        this.markerDom.addEventListener(event, function () {
          handler(m);
        });
      }
    },
    _infoTipShow: function () {
      $(this.textdiv).css("display", "block")
    },
    _infoTipClose: function () {
      $(this.textdiv).css("display", "none")
    }
  };

  function getClassnamesOfFontIcon(icon) {
    return "iconfont " + icon;
  }

  function getContainerClassNames(classNames) {
    return "amap-simple-marker " + classNames;
  }

  function popclose(map) {
    var element = document.getElementsByClassName("ol-sanjiao");
    $(element).remove();
  }

  arcGisMap.MapLib = {};

  arcGisMap.MapLib.MouseTool = function (map) {
    this.map = map;
    this.simpleLineSymbol = new SimpleLineSymbol("solid", Color.fromRgb('rgba(0,139,202,1)'), 2)
    let fillColor = new Color([0, 174, 255, 0.2]);

    this.simpleFillSymbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, this.simpleLineSymbol, fillColor);
    map.mouseTool.setFillSymbol(this.simpleFillSymbol);
    map.mouseTool.setLineSymbol(this.simpleLineSymbol);
    this.mouseTool = map.mouseTool;
    // 距离面积测量
    var options = {
      map: map.protoSelf,
      geometryServiceUrl: ((this.map.protoSelf.spatialReference.wkid == "3857" || (this.map.protoSelf.spatialReference.wkid == "102100") || this.map.protoSelf.spatialReference.wkid == "4326")) ? '' : 'https://utility.arcgisonline.com/ArcGIS/rest/services/Geometry/GeometryServer'
    };
    if (typeof Measure !== 'undefined') this.measureTool = new Measure(options);
  };

  arcGisMap.MapLib.MouseTool.prototype = {
    toCommonGeo: function (geo) { // 转换为统一几何图形
      let fcd = [];
      let geo01 = this.map.webMercatorToGeographic(geo);
      if (geo01.type == "extent") {
        fcd.push([geo01.xmin, geo01.ymin]);
        fcd.push([geo01.xmin, geo01.ymax]);
        fcd.push([geo01.xmax, geo01.ymin]);
        fcd.push([geo01.xmax, geo01.ymax]);
      } else {
        let points = geo01.rings || geo01.paths || [geo01];
        points = points[0];
        for (let i = 0; i < points.length; i++) {
          let item = points[i];
          fcd.push(item);
        }
      }
      geo01.znvCoordinates = fcd;
      return geo01;
    },
    // 测距
    _measureDistance: function () {
      this.measureTool && this.measureTool.measureDistance();
    },
    // 测面
    _measureArea: function () {
      this.measureTool && this.measureTool.measureArea();
    },
    _clearDrawOverlays: function () {
      if (this.drawVector) {
        this.map.removeExLayer(this.drawVector);
        this.drawVector = undefined;
      }
      if (this.pointMarker) {
        this.pointMarker._remove();
        this.pointMarker = false;
      }
    },
    // 圈选
    _drawCircle: function (cb) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.CIRCLE);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleFillSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        cb(geo01);
      })
    },
    // 多边形
    _drawPolygon: function (cb) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.POLYGON);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleFillSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        cb(geo01);
      })
    },
    // 点
    _drawPoint: function (cb) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.POINT);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        cb(geo01);
        me.mouseTool.deactivate();
        let opts = {
          icon: "/static/map/images/defaultPoint.png",
          size: {width: 32, height: 32},
          position: {lng: geo01.x, lat: geo01.y},
          isFresh: false, // 是否刷新地图
          offset: {x: -5, y: -5}
        };
        let marker = new arcGisMap.Marker(map, opts)
        marker._add();
        me.pointMarker = marker;
        event.remove();
      })
    },
    // 线
    _drawLine: function (cb) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.POLYLINE);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleLineSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        cb(geo01);
      })
    },
    // 矩形
    _drawRectangle: function (cb) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.EXTENT);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleFillSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        cb(geo01);
      })
    },
    // 圈选要素
    _selectCircle: function (markers, cb, opts) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.CIRCLE);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleFillSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        let resultMarkers = [];
        for (let i = 0; i < markers.length; i++) {
          let pt = markers[i]._getPosition()
          let isContained = geometryEngine.contains(geo01, pt)
          if (isContained) {
            resultMarkers.push(markers[i])
          }
        }
        map.removeExLayer(graphic);
        cb(resultMarkers);
      })
    },
    _selectRectangle: function (markers, cb, opts) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.EXTENT);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleFillSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        // cb(geo01);
        let resultMarkers = [];
        for (let i = 0; i < markers.length; i++) {
          let pt = markers[i]._getPosition()
          let isContained = geometryEngine.contains(geo01, pt)
          if (isContained) {
            resultMarkers.push(markers[i])
          }
        }
        map.removeExLayer(graphic);
        cb(resultMarkers);
      })
    },
    // 多边形选择
    _selectPolygon: function (markers, cb, opts) {
      var map = this.map;
      let me = this;
      me.mouseTool.activate(Draw.POLYGON);
      let event = me.mouseTool.on('draw-complete', function (evt) {
        let geo = evt.geometry;
        var graphic = new Graphic(geo, me.simpleFillSymbol);
        me.drawVector = graphic;
        map.addExLayer(graphic);
        me.mouseTool.deactivate()
        let geo01 = me.toCommonGeo(geo);
        event.remove();
        let resultMarkers = [];
        for (let i = 0; i < markers.length; i++) {
          let pt = markers[i]._getPosition()
          let isContained = geometryEngine.contains(geo01, pt)
          if (isContained) {
            resultMarkers.push(markers[i])
          }
        }
        map.removeExLayer(graphic);
        cb(resultMarkers);
      })
    }
  };

  arcGisMap.Overlay = function (overlay) {
    this._obj = overlay;
  };

  arcGisMap.Overlay.prototype = {
    _containPoint: function (point) {
      var overlay = this._obj;
      if (overlay.contains) {
        return overlay.contains(point);
      } else {
        return false;
      }
    }
  };

  arcGisMap.MapLib.MarkerClusterer = function (map, markers, opts) {
    if (typeof ClusterLayer === 'undefined') return false
    // this.markers = markers;
    var clusterLayer = new ClusterLayer({
      "data": markers,
      "distance": 100,
      "id": "clusters",
      "labelColor": "#fff",
      "labelOffset": 10,
      "resolution": map.protoSelf.extent.getWidth() / map.width,
      "singleColor": "#888",
      // "singleTemplate": "",
      "showSingles": false,
      "spatialReference": map.protoSelf.spatialReference
    });
    var defaultSym = new SimpleMarkerSymbol().setSize(4);
    var renderer = new ClassBreaksRenderer(defaultSym, "clusterCount");
    var levelSelf = new PictureMarkerSymbol('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACcElEQVRYR8WWvW/TUBTF73VjW2wsSI1T1KVS7TCBWNoJNgQNDIiPFRYWkEAIISo+CpWKhEAgdWJBjHyIpS1ITHSiDAixNM9Dl4rmJRL/APFrc5CjpKLEeXZcW/Xqd8/5veN3rx/THj+8x/6UGmAUXjFQcMINWCbLdRb1NJtJDDCCQ2OtYOsKmI8y0bEoMxAtM/DdsIZebvDqWhKgRABOUL4GYJqZhpOIAtRg5jlpVefj1scCOE3vDTFdiBOKfA96K21xUVerBSg2vY/MdDKVeacIoE91W5zqp9EXwFHuAwLP7MZ8u5YxI03/YZRWJEBp051CixczMe+IsIFKreAv/a8ZCeCo8gIBlSwBiHlRmtXTsQAl5U4A/DVT824KjMma6a/8q92TQKnpzoH5Ti4AwOOa7U9rAZzAC3c/kQcAEa1IS0zqAZruL2IeyQUA2JC2fzAuAeRi3hGVltjx2XvOQDHwvvSb9RmA/ZSWOKxPIMsB1EPMr6VVvaTvghyG0LYh6La0xRMtQPifV4pkBnH3ShjGGVlYXdAChC+doDxPhKuZQoA/S7t6InYStgH+jI/TkPGNQPuzgmht4Xhjn7+cCCBcVFTeLIPuZgIAPJW2fytKS3sfcJT3jkDndgXB9F6a4nw/jdgbUUl5zwG6ngaCmV7UTHFDVxsL0D4TaWaD5hIS2wVRxMWme5aJnxHTqDYN0DoIN+u2/yFJaokS6AoNY+yAsWm+ItBUpDjTUqugLjd47XcS83DNQABd0ZLy7gH0aEeUTPdrpphNatxdlwqg3aaBd4RB7XsjmCp1S/wY1Dx1AmmMUrdhlmYDD6K8zUP9v7bdyyHm8GrKAAAAAElFTkSuQmCC', 32, 32).setOffset(0, 0);
    var level1 = new PictureMarkerSymbol('data:image/png;base64,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', 53, 52).setOffset(0, 15);
    var level2 = new PictureMarkerSymbol('data:image/png;base64,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', 56, 55).setOffset(0, 15);
    var level3 = new PictureMarkerSymbol('data:image/png;base64,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', 66, 65).setOffset(0, 15);
    var level4 = new PictureMarkerSymbol('data:image/png;base64,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', 78, 77).setOffset(0, 15);
    var level5 = new PictureMarkerSymbol('data:image/png;base64,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', 90, 89).setOffset(0, 15);
    renderer.addBreak(0, 1, levelSelf);
    renderer.addBreak(1, 10, level1);
    renderer.addBreak(10, 100, level2);
    renderer.addBreak(100, 1000, level3);
    renderer.addBreak(1000, 10000, level4);
    renderer.addBreak(10000, 100000, level5);
    clusterLayer.setRenderer(renderer);
    map.protoSelf.addLayer(clusterLayer);
    this.clustererMarkerMap = this.clustererMarkerMap || []
    if (markers && markers.length > 0) {
      markers.forEach((item) => {
        this.clustererMarkerMap[item.id] = item
      })
    }
    dojo.connect(clusterLayer, "onClick", function(evt) {
      // var g = evt.graphic;
      console.log("clusterLayer  onClick")
    });
    return $.extend(clusterLayer, this)
  };
  arcGisMap.MapLib.MarkerClusterer.prototype = {
   _addToMarkerMap: function (marker) {
     this.clustererMarkerMap = this.clustererMarkerMap || []
     this.clustererMarkerMap[marker.id] = marker
    },
    _deleteFromMarkerMap: function (marker) {
      delete this.clustererMarkerMap[marker.id]
    },
    _isMarkerExisted: function (marker) {
      return this.clustererMarkerMap[marker.id];
    },
    _setMarkers: function (markers) {
    },
    _addMarker: function (marker) {
      if (!this._isMarkerExisted(marker)) {
        this._addToMarkerMap(marker)
        this.refresh();
      }
    },
    _removeMarker: function (marker) {
      if (this._isMarkerExisted(marker)) {
        this._deleteFromMarkerMap(marker)
        this.refresh();
      }
    },
    _addMarkers: function (markers) {
     markers.forEach((item) => {
       if (!this._isMarkerExisted(item)) {
         this._addToMarkerMap(item)
       }
     })
      this.refresh();
    },
    _removeMarkers: function (markers) {
      markers.forEach((item) => {
        if (this._isMarkerExisted(item)) {
          this._deleteFromMarkerMap(item)
        }
      })
      this.refresh();
    },
    _clearMarkers: function () {
      this.clear()
      this.clustererMarkerMap = {}
    },
    _complete: function (callback) {
      callback.call(this)
    }
  };
  // 工具类
  arcGisMap.gmUtil = {
    distance: function (p0, p1) {
      let lat1 = p0[1];
      let lon1 = p0[0];
      let lat2 = p1[1];
      let lon2 = p1[0];
      let deg2rad = (deg) => deg * (Math.PI / 180);
      let R = 6371000; // Radius of the earth in km
      let dLat = deg2rad(lat2 - lat1); // deg2rad below
      let dLon = deg2rad(lon2 - lon1);
      let a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
      let c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      return R * c;
    },
    distanceToLine: function (p0, p1s) {
      let mdistance = -1;
      let gm = arcGisMap.gmUtil;
      for (let i = 0; i < p1s.length - 1; i++) {
        let p1 = p1s[i];
        let p2 = p1s[i + 1];
        let d1 = gm.distance(p1, p2);
        let d2 = gm.distance(p0, p2);
        let d3 = gm.distance(p0, p1);
        let d12 = Math.pow(d1, 2);
        let d22 = Math.pow(d2, 2);
        let d32 = Math.pow(d3, 2);
        if (d2 == 0 || d3 == 0) {
          mdistance = 0
          break;
        }
        let p = (d1 + d2 + d3) / 2;
        let s = Math.sqrt(p * (p - d1) * (p - d2) * (p - d3));
        let h = 2 * s / d1;
        if ((d12 + d22) < d32) {
          h = d2;
        } else if ((d12 + d32) < d22) {
          h = d3;
        }
        if (mdistance > h || mdistance == -1) {
          mdistance = h;
        }
      }
      return mdistance;
    }
  }

  arcGisMap.Controls = {}
  // 地图控件---比例尺
  arcGisMap.Controls.Scale = function (map, opts) {
    var scalebar = new Scalebar({
      map: map.protoSelf,
      attachTo: "bottom-left",
      scalebarUnit: "metric"
    });
    return scalebar
  }
  arcGisMap.Controls.Scale.prototype = {
    hide() {
      this.hide()
    },
    show() {
      this.show()
    }
  }
  // 地图控件---鹰眼
  arcGisMap.Controls.OverView = function (map, opts) {
    var overview = new OverviewMap({
      map: map.protoSelf,
      color: "#D84E13",
      opacity: 0.40,
      expandFactor: 0.4,
      width: 310,
      height: 150,
      visible: true,
      attachTo: "bottom-right" // top-left, top-right, bottom-right, bottom-left
    });
    overview.startup();
    return overview
  }
  arcGisMap.Controls.OverView.prototype = {
    hide() {
      this.hide()
    },
    show() {
      this.show()
    }
  }

  let layersContainer

  arcGisMap.ElementLayer = function (opts) {
    // 加载时地图坐标
    var map = opts.map.protoSelf;
    this.spatialReference = opts.map.spatialReference;
    // 图层对象
    var layerElement;
    // 地图拖拽事件
    this._panEvt = null;
    this._panEndEvt = null;
    // 地图所缩放事件
    this._zoomStartEvt = null;
    this._zoomEndEvt = null;
    // 图层DIV
    this.layerElement = document.createElement("div");
    layerElement = this.layerElement;
    layerElement.className = "arc-custom-layer";
    layerElement.style.position = "absolute";
    if (opts.element) {
      layerElement.appendChild(opts.element);
    } else if (opts.content) {
      layerElement.innerHTML = opts.content
    }
    this.position = opts.position;
    this.opts = opts;
    this.layerOffset = opts.offset;
    if (!layersContainer) {
      layersContainer = document.createElement("div");
      document.getElementById(map.id + "_root").appendChild(layersContainer);
      layersContainer.className = "arc-custom-layers";
    }
    this.layerContainer = layersContainer;
    this.protoMap = map;
  }

  arcGisMap.ElementLayer.prototype = {
    remove: function () {
      if (this.isAdded) {
        if (this._panEndEvt) {
          this._panEndEvt.remove();
        }
        if (this._panEvt) {
          this._panEvt.remove();
        }
        if (this._zoomEndEvt) {
          this._zoomEndEvt.remove();
        }
        if (this._zoomStartEvt) {
          this._zoomStartEvt.remove();
        }
        this._panEndEvt = null;
        this._panEvt = null;
        this._zoomEndEvt = null;
        this._zoomStartEvt = null;
        this.layerContainer.removeChild(this.layerElement);
      }
      this.visible = false;
      this.isAdded = false;
    },
    add: function () {
      if (!this.isAdded) {
        this.layerContainer.appendChild(this.layerElement);
        this.freshPosition();
        this.bindDefualtEvents();
      }
      this.isAdded = true;
      this.show();
    },
    show: function () {
      this.layerElement.style.display = 'block';
      this.freshPosition();
      this.visible = true;
    },
    hide: function () {
      this.visible = false;
      this.layerElement.style.display = 'none';
    },
    freshPosition: function () { // 定位
      let map = this.protoMap
      let layerElement = this.layerElement;
      var p = map.toScreen(this.position);
      // var p = map.toScreen(this.position, this.spatialReference);
      this.left = p.x;
      this.top = p.y;
      let offset = this.layerOffset;
      if (offset) {
        this.left += offset.x;
        this.top += offset.y;
      }
      layerElement.style.top = this.top + "px";
      layerElement.style.left = this.left + "px";
    },
    setzIndex: function (zIndex) {
      this.layerElement.style.zIndex = zIndex;
    },
    setPosition(point) {
      this.position = point;
      this.freshPosition();
    },
    bindDefualtEvents: function () {
      let layerElement = this.layerElement;
      let map = this.protoMap;
      // 定义地图缩放事件
      this._zoomStartEvt = map.on("zoom-start", (obj) => {
        layerElement.style.visibility = "hidden";
        this.freshPosition();
      });
      this._zoomEndEvt = map.on("zoom-end", () => {
        layerElement.style.visibility = "visible";
        this.freshPosition();
      });
      // 移动量
      let movX = 0;
      let movY = 0;
      // 变化量
      let tempX = 0;
      let tempY = 0;
      // 定义地图拖拽事件
      this._panEvt = map.on("pan", (evt) => {
        var point = evt.delta;
        movX = point.x - tempX;
        movY = point.y - tempY;
        tempX = point.x;
        tempY = point.y;
        this.top += movY;
        this.left += movX;
        layerElement.style.top = this.top + "px";
        layerElement.style.left = this.left + "px";
      });
      this._panEndEvt = map.on("pan-end", function (evt) {
        tempX = 0;
        tempY = 0;
        console.log(1);
      });
    }
  }

  // 热力图
  arcGisMap.Heatmap = function (opt, map) {
    var layerDefinition = {
      "geometryType": "esriGeometryPoint",
      "fields": [{
        "name": "count", // 字段名称xxx，渲染字段
        "type": "esriFieldTypeInteger" // 字段数据类型
      }
      ]
    };
    var featureCollection = {
      "layerDefinition": layerDefinition,
      "featureSet": {
        "features": [],
        "geometryType": "esriGeometryPoint",
        "spatialReference": map.spatialReference
      }
    };
    var heatMapLayer = new FeatureLayer(featureCollection, {
      mode: FeatureLayer.MODE_SNAPSHOT,
      outFields: ["*"],
      opacity: 1,
      spatialReference: map.spatialReference
    });
    heatMapLayer['id'] = "heatMap";
    map.protoSelf.addLayer(heatMapLayer);
    this.protoSelf = heatMapLayer;
    var heatmapRenderer = new HeatmapRenderer({
      // colorStops: [ // 根据实际需要设置colorStops
      //   {ratio: 0, color: "rgba(0, 0, 255,0)"},
      //   {ratio: 0.5, color: "rgb(0, 0, 255)"},
      //   {ratio: 0.7, color: "rgb(0, 255, 0)"},
      //   {ratio: 0.9, color: "rgb(255, 0, 0)"}],
      colorStops: [ // 根据实际需要设置colorStops
        {ratio: 0, color: "rgba(0, 0, 255,0)"},
        {ratio: 0.3, color: "rgb(0, 0, 255)"},
        {ratio: 0.5, color: "rgb(0, 255, 0)"},
        {ratio: 0.7, color: "rgb(255, 255, 0)"},
        {ratio: 0.9, color: "rgb(255, 0, 0)"}],
      blurRadius: 8,
      maxPixelIntensity: 100,
      minPixelIntensity: 0
    });
    // 计算热力数据
    let heatmapdata = []
    if (opt.dataSource) {
      for (var i = 0; i < opt.dataSource.length; i++) {
        let item = opt.dataSource[i];
        let ptGeo = arcGisMap.Point(item.lng, item.lat, map.spatialReference)
        var point = {}
        if (map.spatialReference.wkid == 3857) { // 坐标系********************************
          point = webMercatorUtils.geographicToWebMercator(ptGeo);
        }
        // g不需要定义symbol
        var g = new Graphic(point, null, {count: item.count}, null);
        heatMapLayer.add(g);
      }
      heatMapLayer.setRenderer(heatmapRenderer);
    }
  }
  arcGisMap.Heatmap.prototype = {
    toggleShow: function () {
      this.protoSelf.show()
    },
    toggleHide: function () {
      this.protoSelf.hide()
    }
  }

  /*
   *扇形
   */
  arcGisMap.Sector = function (map, opt) {
    let defOpts = {
      radius: 100,
      // radiusUnit: esri.units.METERS, // KILOMETERS
      strokeColor: '#FF33FF',
      strokeOpacity: 0,
      fillOpacity: 0.4,
      fillColor: '#1791fc',
      strokeWeight: 1,
      zIndex: 50,
      geodesic: true
    }
    Object.assign(defOpts, opt);
    let fillColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
    let strokeColor = new Color(hexToRgba(defOpts.strokeColor, defOpts.strokeOpacity));
    let lsymbol = new SimpleLineSymbol(defOpts.strokeStyle || SimpleFillSymbol.STYLE_SOLID, strokeColor, defOpts.strokeWeight)
    let symbol = new SimpleFillSymbol(SimpleFillSymbol.STYLE_SOLID, lsymbol, fillColor);
    let radius = opt.radius / 111000 || 1 // 半径单位度
    let sides = 256
    let centerAngle = opt.angle || 120
    let rotate = opt.rotate || 0
    // let startAngle = 90 + rotate;
    let startAngle = 90 - rotate;
    let endAngle = startAngle - centerAngle;
    let sectorPoints = getPoints([defOpts.center.lng, defOpts.center.lat], radius, startAngle, endAngle, sides);
    // 多边形此处注意一定要是[坐标数组]

    var polygon = new Polygon([sectorPoints]);
    // polygon.addRing([sectorPoints]);
    let polygonGraphic = new Graphic(polygon, symbol);
    // map.addExLayer(polygonGraphic); // 通过外边加入
    this.map = map;
    this.extData = defOpts.extData;
    this.opts = defOpts;
    this.protoSelf = polygonGraphic;
    if (opt.isFresh) { // 自动调整多边形到地图中心
    }
  }
  arcGisMap.Sector.prototype = {
    _hide: function () {
      this.protoSelf.hide();
    },
    _show: function () {
      this.protoSelf.show();
    },
    _remove: function () {
      this.map.removeExLayer(this.protoSelf);
    },
    _contains: function (lng, lat) {
      this.polygon.contains(arcGisMap.Point(lng, lat))
    },
    _setOptions: function (opts) {
      let defOpts = Object.assign(this.opts, opts);
      let oldSymbol = this.protoSelf.symbol;
      let newColor = new Color(hexToRgba(defOpts.fillColor, defOpts.fillOpacity));
      let newSymbol = oldSymbol.setColor(newColor);
      this.protoSelf.setSymbol(newSymbol)
    },
    on: function (event, handler) {
      let feature = this.feature;
      feature.addEventListener(event, function () {
        handler();
      })
    },
    _addEventListener: function (event, handler) {
      if (event) {
        let eventName = event;
        if (event === "click") {
          eventName = "onClick"
          if (this.map.protoSelf.graphics.onClick.length == 0) {
            // 事件加在图层上，需要确认只加一次
            dojo.connect(this.map.protoSelf.graphics, eventName, function (e) {
              console.log(e)
              handler();
            });
          }
        } else {
          // if (this.map.protoSelf.graphics.onMouseMove.length == 0) {
          dojo.connect(this.map.protoSelf.graphics, eventName, function (e) {
            handler();
          });
          // }
        }
      }
    }
  }

  function getPoints(center, radius, startAngle, endAngle, pointNum) {
    pointNum = pointNum | 256;
    var sin, cos, x, y, angle;
    var points = [];
    points[0] = center;
    for (var i = 1; i <= pointNum; i++) {
      angle = startAngle + (endAngle - startAngle) * i / pointNum;
      sin = Math.sin(angle * Math.PI / 180);
      cos = Math.cos(angle * Math.PI / 180);
      x = center[0] + radius * sin;
      y = center[1] + radius * cos;
      points[i] = [x, y];
    }
    points[points.length] = points[0];
    return points;
  }

  function getAngle4(x1, y1, x2, y2) {
    if (x1 == x2 && y1 == y2) {
      return -1;
    }
    var x = Math.abs(x1 - x2);
    var y = Math.abs(y2 - y1);
    var z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    var cos = y / z;
    var radina = Math.acos(cos); // 用反三角函数求弧度
    var angle = Math.floor(180 / (Math.PI / radina)); // 将弧度转换成角度

    if (x2 >= x1 && y2 < y1) { // 在第2象限
      angle = 180 - angle;
    } else if (x2 < x1 && y2 <= y1) {
      angle = 180 + angle;
    } else if (x2 <= x1 && y2 > y1) {
      angle = 363 - angle;
    }
    return angle;
  };

  window.arcGisMap = arcGisMap;
  return arcGisMap;
}

function createMapLayer(mapConfig, dojo, TiledMapServiceLayer, SpatialReference, Point, TileInfo, Extent) {
  let layer
  if (mapConfig.mapType && mapConfig.mapType.indexOf('gaode') > -1) {
    layer = getLayer(mapConfig.mapServeUrl || "http://10.45.157.179:8008", dojo, TiledMapServiceLayer, SpatialReference, Point, TileInfo, Extent); // 现有的ArcGIS瓦片
  } else {
    layer = new esri.layers.ArcGISTiledMapServiceLayer(mapConfig.mapServeUrl)
    layer.spatialReference = new esri.SpatialReference({
      wkid: mapConfig.wkid || 4326
    });
    layer.initialExtent = new esri.geometry.Extent({
      "xmin": mapConfig.initExtent.xmin, "ymin": mapConfig.initExtent.ymin, "xmax": mapConfig.initExtent.xmax, "ymax" : mapConfig.initExtent.ymax,
      spatialReference: layer.spatialReference
    });
    layer.fullExtent = new esri.geometry.Extent({
      "xmin": mapConfig.fullExtent.xmin, "ymin": mapConfig.fullExtent.ymin, "xmax": mapConfig.fullExtent.xmax, "ymax" : mapConfig.fullExtent.ymax,
      spatialReference: layer.spatialReference
    });
  }
  return layer
}
function getLayer(engineUrl, dojo, TiledMapServiceLayer, SpatialReference, Point, TileInfo, Extent) {
  dojo.declare("esri.layers.GaodeLayer", TiledMapServiceLayer, {
    engineUrl: engineUrl, // 图层类型
    constructor: function (engineUrl) {
      if (engineUrl) {
        this.engineUrl = engineUrl;
      }
      this.spatialReference = new SpatialReference({
        wkid: 3857
      });
      //  dojo.declare.safeMixin(this, args);
      this.fullExtent = new Extent(-20037508.342787, -20037508.342787, 20037508.342787, 20037508.342787, this.spatialReference);
      this.initialExtent = this.fullExtent;
      this.tileInfo = new TileInfo({
        "cols": 256,
        "rows": 256,
        "compressionQuality": 0,
        "origin": new Point(-20037508.342787, 20037508.342787, this.spatialReference),
        "spatialReference": this.spatialReference,
        "dpi": 96,
        "lods": [{"level": 0, "resolution": 156543.03392800014, "scale": 591657527.591555},
          {"level": 1, "resolution": 78271.516963999937, "scale": 295828763.79577702},
          {"level": 2, "resolution": 39135.758482000092, "scale": 147914381.89788899},
          {"level": 3, "resolution": 19567.879240999919, "scale": 73957190.948944002},
          {"level": 4, "resolution": 9783.9396204999593, "scale": 36978595.474472001},
          {"level": 5, "resolution": 4891.9698102499797, "scale": 18489297.737236001},
          {"level": 6, "resolution": 2445.9849051249898, "scale": 9244648.8686180003},
          {"level": 7, "resolution": 1222.9924525624949, "scale": 4622324.4343090001},
          {"level": 8, "resolution": 611.49622628137968, "scale": 2311162.2171550002},
          {"level": 9, "resolution": 305.74811314055756, "scale": 1155581.108577},
          {"level": 10, "resolution": 152.87405657041106, "scale": 577790.55428899999},
          {"level": 11, "resolution": 76.437028285073239, "scale": 288895.27714399999},
          {"level": 12, "resolution": 38.21851414253662, "scale": 144447.638572},
          {"level": 13, "resolution": 19.10925707126831, "scale": 72223.819285999998},
          {"level": 14, "resolution": 9.5546285356341549, "scale": 36111.909642999999},
          {"level": 15, "resolution": 4.7773142679493699, "scale": 18055.954822},
          {"level": 16, "resolution": 2.3886571339746849, "scale": 9027.9774109999998},
          {"level": 17, "resolution": 1.1943285668550503, "scale": 4513.9887049999998},
          {"level": 18, "resolution": 0.59716428355981721, "scale": 2256.994353},
          {"level": 19, "resolution": 0.298582141647617, "scale": 1128.497176}
        ]
      });
      this.loaded = true;
      this.onLoad(this);
    },
    /**
     * 根据不同的layType返回不同的图层
     * @param level
     * @param row
     * @param col
     * @returns {string}
     */
    getTileUrl: function (level, row, col) {
      var x = 'C' + this.zeroFill(col.toString(16), 8, 0);
      var y = 'R' + dojo.string.pad(row.toString(16), 8, '0');
      var z = 'L' + dojo.string.pad(level, 2, '0');
      // return 'http://10.45.148.226:8002/' + z + '/' + y + '/' + x + '.png';// 这里可以修改地图路径
      return this.engineUrl + '/' + z.toUpperCase() + '/' + y.toUpperCase() + '/' + x.toUpperCase() + '.png';// 这里可以修改地图路径;
    },
    zeroFill(num, len, radix) { // 给8位字符串文件名补0
      var str = num.toString(radix || 10);
      while (str.length < len) {
        str = "0" + str;
      }
      return str;
    }
  });
  let layer;
  layer = new esri.layers.GaodeLayer();
  return layer;
}
export default arcGisMap;
