/* eslint-disable */
// import '../superMap/libs/SuperMap.Include.js'
// import '../superMap/libs/SuperMap-8.1.1-14426.js'
import $ from 'jquery'

function znvSuperMap (mapServeUrl) {
  let superMap = {}
  let map
  let layer
  let layer1
  let tiandituLayer, tianMarkerLayer
  let drawVectorLayer, markerlayer, clusterLayer, featureMarkerLayer, heatMapLayer, vector, vectorCircle, textLayer
  let measureDistanceLayer, measureAreaLayer
  let style = {
    strokeColor: 'rgba(0,139,202,1)',
    strokeWidth: 2,
    pointerEvents: 'visiblePainted',
    fillColor: 'rgb(0,174,255)',
    fillOpacity: 0.3,
    pointRadius: 6
  }
  // 事件参数
  superMap.Constants = {
    ZOOM_CHANGED: 'zoomend',
    ZOOM_END: 'zoomend',
    DRAG_END: 'moveend',
    CLICK: 'click',
    DBLCLICK: 'dblclick',
    RIGHTCLICK: 'rightclick',
    LOADED: 'tilesloaded',
    MOUSEMOVE: 'mousemove',
    MOUSEOVER: 'mouseover',
    MOUSEOUT: 'mouseout'
  };
  /**
   map类
   */
  superMap.Map = function (mapDiv, opts, callback) {
    // 初始化地图
    let optParam = {
      controls: [
        // new SuperMap.Control.Navigation(),
        // new SuperMap.Control.TouchNavigation(),
        new SuperMap.Control.PinchZoom()
      ],
      allOverlays: true,
      numZoomLevels: 22
    }
    if (opts.maxZoom) {
      optParam.maxZoom = opts.maxZoom
    }
    if (opts.minZoom) {
      optParam.minZoom = opts.minZoom
    }
    if (opts.zooms) {
      optParam.maxZoom = opts.zooms[1]
      optParam.minZoom = opts.zooms[0]
    }
    map = new SuperMap.Map(mapDiv, optParam)
    tiandituLayer = new SuperMap.Layer.WMTS({
      name: "ditu",
      url: "http://t0.tianditu.gov.cn/vec_c/wmts?tk=da3a0bde71ee7e795a67bff369f33992",
      layer: "vec",
      style: "default",
      matrixSet: "c",
      format: "tiles",
      opacity: 1,
      requestEncoding: "KVP"
    });
    tianMarkerLayer = new SuperMap.Layer.WMTS({
      name: "ditubiaozhu",
      url: "http://t0.tianditu.gov.cn/cva_c/wmts?tk=da3a0bde71ee7e795a67bff369f33992",
      layer: 'cva',
      style: 'default',
      matrixSet: 'c',
      format: 'tiles',
      opacity: 1,
      requestEncoding: 'KVP'
    });
    tianMarkerLayer.layerType = "cva";
    tianMarkerLayer.isLabel = true;
    // 初始化图层
    // layer = new SuperMap.Layer.TiledDynamicRESTLayer('ditu', url, null, { maxResolution: 'auto' })
    // layer = new SuperMap.Layer.TiledDynamicRESTLayer('zmb', url, null, { maxResolution: 'auto' })
    // layer = new SuperMap.Layer.CloudLayer();
    clusterLayer = new SuperMap.Layer.ClusterLayer('Cluster')
    markerlayer = new SuperMap.Layer.Markers('markerLayer')
    vector = new SuperMap.Layer.Vector("vector");
    vectorCircle = new SuperMap.Layer.Vector("vectorCircle");
    featureMarkerLayer = new SuperMap.Layer.Vector("featureMarkerLayer", {
      renderers: ["Canvas2"]
    });
    //新建一个策略并使用在矢量要素图层(vector)上。
    var strategy = new SuperMap.Strategy.GeoText();
    strategy.style = {
      fontColor:"#FF7F00",
      fontWeight:"bolder",
      fontSize:"20px",
      fill: true,
      fillColor: "#FFFFFF",
      fillOpacity: 1,
      stroke: true,
      strokeColor:"#000"
    };
    textLayer = new SuperMap.Layer.Vector("Label",{strategies: [strategy]});
    map.addLayers([tiandituLayer, tianMarkerLayer, vector, vectorCircle, clusterLayer, markerlayer, textLayer, featureMarkerLayer])
    // 显示地图范围
    if(opts.center) {
      opts.center = [opts.center.lng,opts.center.lat];
    }
    let center = opts.center || [118.0851364, 36.1680603]
    let zoom = opts.zoom || 12
    map.setCenter(new SuperMap.LonLat(center[0], center[1]), zoom) // 上海浦东新区中心点，可换成其他中心点
    // vectorLayer 要素点击事件
    let select = new SuperMap.Control.SelectCluster(clusterLayer, {
      callbacks: {
        click: function (f) { //点击兴趣点弹出信息窗口
          //closeInfoWin();
          if (!f.isCluster) { //当点击聚散点的时候不弹出信息窗口
            //openInfoWin(f);
            // callback(f)
          }
        },
        clickout: function () {       //点击空白处关闭信息窗口
          //closeInfoWin();
        }
      }
    });
    map.addControl(select);
    select.activate();
    let select1 = new SuperMap.Control.SelectFeature(textLayer, {
      callbacks: {
        click: function (f) { //点击兴趣点弹出信息窗口
          f.layer.selectedFeatures[0] = f
          f.layer.events.listeners.featureselected[0].func.call(f.layer)
        },
        clickout: function () {       //点击空白处关闭信息窗口
        }
      }
    });
    map.addControl(select1);
    select1.activate();
    let select2 = new SuperMap.Control.SelectFeature(
      featureMarkerLayer,
      {
        callbacks: {
          over: function(currentFeature, e) {
            e.srcElement.style.cursor = 'pointer'
            map.div.title = currentFeature.data.title
          },
          out: function(currentFeature, e) {
            e.srcElement.style.cursor = 'inherit'
            map.div.title = ''
          }
        },
        onSelect: function (f, e) {
          f.layer.selectedFeatures[0] = f
          f.layer.events.listeners.featureselected[0].func.call(f.layer)
        },
      }
    );
    map.addControl(select2);
    select2.activate();
    map = $.extend(map, this)
    return map
  }
  /**
   map接口
   */
  superMap.Map.prototype = {
    _centerAndZoom: function (point, zoom) {
      this.setCenter(new SuperMap.LonLat(point[0], point[1]), zoom)
    },
    _center (point) {
      this.setCenter(point)
    },
    _getCenter() {
      let center = this.getCenter();
      return {lng: center.lon, lat: center.lat}
    },
    _zoom (zoom) {
      this.zoomTo(zoom)
    },
    _getZoom () {
      return this.getZoom()
    },
/*    _getBounds: function () {
      return map.getExtent()
       let bounds = new superMap.Bounds(map.getExtent())
        return bounds
    },*/
    _getView: function () {
      let bds = map.getExtent() // 获取当前地图的范围。
      var northeast = [bds.right, bds.top];
      var southwest = [bds.left, bds.bottom];
      let ext = {'northeast': northeast, 'southwest': southwest}
      return ext
    },
    _setView: function (layer) {
      let extent = layer.getDataExtent();
      this.zoomToExtent(extent);
    },
    _getOverlayByName: function (name) { // 此方法是根据图层名字查找图层
      return map.getLayersByName(name)
    },
    _addOverlay: function (layer) { // 此方法是添加图层
      this.addLayer(layer)
    },
    _setLayerIndex: function (layer, index) { // 此方法是设置图层顺序
      this.setLayerIndex(layer, index)
    },
    _removeLayer: function (layer) {
      this.removeLayer(layer) // 此方法是移除图层
    },
    _removeOverlays: function (overlays) {
      overlays.forEach((v) => {
        this.removeOverlay(v)
      })
    },
    // removeLayer移除要素类别。true：移除图层。false：移除覆盖物
    _removeOverlay: function (overlay, removeOverlay) {
      if(removeOverlay) {
        this.removeOverlay(overlay) // 移除覆盖物
      } else {
        this.removeLayer(overlay) // 移除图层
      }
    },
    _addControl: function (control) {
      this.addControl(control)
    },
    _removeControl: function (control) {
      this.removeControl(control)
    },
    _destroy: function () {
      return this.destroy()
    },
    _on: function (event, handler) {
      // let map = this
      this.events.on({
        event: function (e) {
          handler(e)
        }
      })
    },
    _addEventListener: function (event, handler) {
      this.events.on({
        [event]: function (e) {
          // 需要通过像素坐标获取经纬度坐标
          let px = new SuperMap.Pixel(e.xy.x,e.xy.y)
          let lonlat = this.getLonLatFromPixel(px)
          let lnglat = {lng:lonlat.lon,lat:lonlat.lat}
          handler(lnglat, e)
        }
      })
    }
  }

  superMap.OverlayMarker = function (map, opts) {
    this.type = opts.type
    this.layerName = opts.layerName // 图层名称
    this.title = opts.title // Marker的提示信息
    this.id = opts.id
    this.extData = opts.extData
    // this.extData.size = opts.size
    this.position = new SuperMap.LonLat(opts.position.lng, opts.position.lat)
    this.visible = true
    // if (!markerlayer) {
    //   markerlayer = new SuperMap.Layer.Markers('markerLayer')
    //   map.addLayer(markerlayer)
    // }
    let superSize = new SuperMap.Size(27, 27)
    if(opts.size) {
      superSize  = new SuperMap.Size(opts.size.width, opts.size.height)
    }
    let size = superSize
    let offset = new SuperMap.Pixel(-(size.w / 2), -size.h)
    let icon = new SuperMap.Icon(opts.icon, size, offset)
    let marker = new SuperMap.Marker(this.position, icon)
    marker.icon.imageDiv.title = opts.title
    // markerlayer.addMarker(marker);
    this.map = map
    this.protoSelf = marker
    // marker.setMap(map);
    // map.addOverlay(marker);
    marker.map = map
    marker.position = this.position
    marker = $.extend(marker, this)
    return marker
  }

  superMap.OverlayMarker.prototype = {
    _getPosition: function () {
      this.lng = this.getLonLat().lon
      this.lat = this.getLonLat().lat
      let point = this.getLonLat()
      return ($.extend(point, this));
      // return {lng:this.getLonLat().lon,lat:this.getLonLat().lat}
    },
    _setPosition: function (lnglan) {
      this.lonlat = {
        lon: lnglan.lng || lnglan[0],
        lat: lnglan.lat || lnglan[1]
      }
      markerlayer.addMarker(this)
    },
    _getIcon: function () {
      let icon = this.icon.url
      return icon
    },
    _setIcon: function (iconOpts) {
      let opts = {};
      if (typeof iconOpts == "string") {
        opts.icon = iconOpts;
        this.icon.url = iconOpts
      } else {
        if (iconOpts.iconSrc) {
          iconOpts.icon = iconOpts.iconSrc;
        }
        opts = $.extend({}, opts, iconOpts);
      }
      let imageDiv = this.icon.imageDiv
      if (opts.size) {
        let size = new SuperMap.Size(opts.size.width, opts.size.height)
        this.icon.size = size
        this.icon.offset = new SuperMap.Pixel(-(size.w / 2), -size.h)
        imageDiv.childNodes[0].style.width = opts.size.width + 'px'
        imageDiv.childNodes[0].style.height = opts.size.height + 'px'
      }
      if (opts.icon) {
        imageDiv.childNodes[0].src = opts.icon
      }
    },
    setzIndex: function (zIndex) {
      let imgdiv = this.icon.imageDiv
      imgdiv.parentNode.style.zIndex = zIndex
    },
    _setBigIcon: function (icon) {
      let imgdiv = this.icon.imageDiv.childNodes[0]
      imgdiv.style.width = icon.size.width + 'px'
      imgdiv.style.height = icon.size.height + 'px'
      let size = new SuperMap.Size(icon.size.width, icon.size.height)
      this.icon.size = size
      this.icon.offset = new SuperMap.Pixel(-(size.w / 2), -size.h)
    },
    _setLabel: function (label) {
      let imgdiv = this.icon.imageDiv
      let left = `calc( 50% - ${label.offset.x || '0'}px )`
      let top = Math.abs(label.offset.y) + 'px' || '35px'
      let labelContent = document.createElement('div')
      labelContent.className = 'sm-marker-label'
      labelContent.style.left = left
      labelContent.style.top = top
      labelContent.style.position = 'relative'
      labelContent.style.color = label.color || '#000'
      labelContent.style.minWidth = '120px'
      labelContent.style.fontSize = label.fontSize || '18px'
      labelContent.style.fontWeight = '700'
      labelContent.style.textAlign = label.direction || 'center'
      labelContent.style.backgroundColor = '#fff'
      labelContent.style.boxShadow = '0 2px 6px 0 rgba(114, 124, 245, .5)'
      labelContent.style.padding = '.75rem 1.25rem'
      labelContent.style.float = 'left'
      labelContent.style.transform = 'translate(-50%, 0%)'
      labelContent.innerHTML = label.content
      imgdiv.appendChild(labelContent)
    },
    _setMap: function (map) {
      if (map) {
        markerlayer.addMarker(this)
      } else {
        markerlayer.removeMarker(this)
      }
    },
    _show: function () {
      // this.setMap(this.map);
      this.icon.imageDiv.style.display = 'block'
      this.visible = true
    },
    _hide: function () {
      this.icon.imageDiv.style.display = 'none'
      this.visible = false
    },
    _add: function () {
      this.visible = true
      if (!this.isAdded) {
        markerlayer.addMarker(this)
        this.isAdded = true
      }
      this._show()
    },
    _remove: function () {
      //  this._hide();
      this.isAdded = false
      this.visible = false
      markerlayer.removeMarker(this)
    },
    _getMap: function () {
      return this.map
    },
    // 添加事件监听函数
    _addEventListener: function (event, handler) {
      this.events.on({
        [event]: function () {
          handler && handler(this)
        }
      })
    },
    _setAnimation: function (animate) {
      this.icon.imageDiv.className = ''
      if (animate !== 'ANIMATION_NONE') this.icon.imageDiv.classList.add(animate)
    },
    getId() {
      if(this.extData) {
        return this.id || this.extData.deviceId || this.extData.id
      }
    }
  }

  superMap.Marker = function (map, opts, isSuperMapMarker) {
    if (!isSuperMapMarker) {
      // feature
      return new superMap.FeatureMarker(map, opts);
    } else {
      // superMapMarker
      return new superMap.OverlayMarker(map, opts);
    }
  }

  superMap.FeatureMarker = function (map, opts) {
    let f = new SuperMap.Feature.Vector()
    this.map = map
    this.type = opts.type
    this.layerName = opts.layerName // 图层名称
    this.title = opts.title // Marker的提示信息
    this.id = opts.id
    this.icon = opts.icon || opts.extData.icon || '../../map/images/defaultPoint.png'
    this.info = opts.title // 存储数据信息
    this.position = new SuperMap.LonLat(opts.position.lng, opts.position.lat)
    this.lonlat = this.position;
    this.visible = false
    f.geometry = new SuperMap.Geometry.Point(this.position.lon, this.position.lat)
    if (opts.icon || opts.extData.icon) {
      f.style = {
        graphic: true,
        externalGraphic: opts.icon || opts.extData.icon || '../../map/images/defaultPoint.png',
        graphicWidth: opts.size.width,
        graphicHeight: opts.size.height,
        graphicTitle: this.title,
        graphicXOffset: (opts.offset && opts.offset.x) || 0,
        graphicYOffset: (opts.offset && opts.offset.y) || 0
      }
    } else {
      f.style = {
        fillColor: opts.fillColor || "#669933",
        fillOpacity: opts.fillOpacity || 0.8,
        pointRadius: opts.pointRadius || 16,
        strokeColor: opts.strokeColor || "#aaee77",
        strokeWidth: opts.strokeWidth || 3
      }
    }
    this.info = opts.extData
    this.feature = f;
    f.data = opts
    return this
  }

  superMap.FeatureMarker.prototype = {
    _getPosition: function () {
      this.lng = this.position.lon
      this.lat = this.position.lat
      let point = this.position
      return ($.extend(point, this));
      // return {lng:this.position.lon,lat:this.position.lat}
    },
    _setPosition: function (lnglan) {
      this.geometry.x = lnglan.lng || lnglan[0];
      this.geometry.y = lnglan.lat || lnglan[1];
      featureMarkerLayer.redraw();
    },
    _getIcon: function () {
      return this.icon
    },
    _setIcon: function (url) {
      if (url) {
        this.icon = url
      }
      this.style.externalGraphic = url;
      featureMarkerLayer.redraw();
    },
    setzIndex: function (zIndex) {
      this.style.graphicZIndex = zIndex
    },
    _setBigIcon: function (icon) {
      // let imgdiv = this.icon.imageDiv.childNodes[0]
      // imgdiv.style.width = icon.size.width + 'px'
      // imgdiv.style.height = icon.size.height + 'px'
      // let size = new SuperMap.Size(icon.size.width, icon.size.height)
      // this.icon.size = size
      // this.icon.offset = new SuperMap.Pixel(-(size.w / 2), -size.h)
    },
    _setLabel: function (label) {
      this.label = label;
      this.style.label = label.content;
      this.style.labelXOffset = label.offset.x || 0
      this.style.labelYOffset = label.offset.y || 0
      // this.style.labelAlign = 'cm'
      this.style.fontFamily = '微软雅黑'
      // this.style.fontOpacity
      this.style.fontWeight = 'bold'
      this.style.fontSize = 24
      this.style.fontColor = label.fontColor || "#ff0000"
    },
    _setMap: function (map) {
      // clusterLayer.destroyCluster();
    },
    _show: function () {
      this.visible = true
      this.feature.style.display = 'block'
      window.showFeatureSetTimeout && window.clearTimeout(showFeatureSetTimeout)
      window.showFeatureSetTimeout = setTimeout(() => {
        featureMarkerLayer.redraw();
      }, 100);
    },
    _hide: function () {
      this.visible = false
      this.feature.style.display = 'none'
      window.hideFeatureSetTimeout && window.clearTimeout(hideFeatureSetTimeout)
      window.hideFeatureSetTimeout = setTimeout(() => {
        featureMarkerLayer.redraw();
      }, 100);
    },
    _add: function () {
      this.visible = true
      if (window.tempFeatureMarkerList) {
        window.tempFeatureMarkerList.push(this.feature);
      } else {
        window.tempFeatureMarkerList = [this.feature]
      }
      window.addFeatureSetTimeout && window.clearTimeout(addFeatureSetTimeout)
      window.addFeatureSetTimeout = setTimeout(() => {
        featureMarkerLayer.addFeatures(window.tempFeatureMarkerList);
        window.tempFeatureMarkerList = []
      }, 100);
    },
    _remove: function () {
      this.visible = false
      if (window.tempFeatureMarkerList) {
        window.tempFeatureMarkerList.push(this.feature);
      } else {
        window.tempFeatureMarkerList = [this.feature]
      }
      window.removeFeatureSetTimeout && window.clearTimeout(removeFeatureSetTimeout)
      window.removeFeatureSetTimeout = setTimeout(() => {
        featureMarkerLayer.removeFeatures(window.tempFeatureMarkerList);
        window.tempFeatureMarkerList = []
      }, 100);
    },
    _getMap: function () {
      return this.map
    },
    _addEventListener: function(event, handler) {
      // marker事件绑定到featureMarkerLayer上
      if(event === 'click'){
        event = 'featureselected'
      }
      if(featureMarkerLayer.events.listeners.featureselected < 1) {
        featureMarkerLayer.events.on({
          [event]: function () {
            if (this.selectedFeatures && this.selectedFeatures[0])
              handler && handler(this.selectedFeatures[0])
          }
        })
      }
    },
    _setAnimation: function (animate) {
      // this.icon.imageDiv.className = ''
      // if (animate !== 'ANIMATION_NONE') this.icon.imageDiv.classList.add(animate)
    },
    getId() {
      if(this.extData) {
        return this.id || this.extData.deviceId || this.extData.id
      }
    }
  }

  superMap.MapLib = {}

  function getFeatures (data, clusterObj) {
    let ps = []
    let clustererMarkerMap = {}
    for (let i = 0; i < data.length; i++) {
      let fi = data[i]
      // clustererMarkerMap[fi.getId()] = fi
      let f = new SuperMap.Feature.Vector()
      f.geometry = new SuperMap.Geometry.Point(fi.lonlat.lon, fi.lonlat.lat)
      f.style = {
        pointRadius: 4,
        graphic: true,
        externalGraphic: fi.icon.url || fi.extData.icon || 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAACcElEQVRYR8WWvW/TUBTF73VjW2wsSI1T1KVS7TCBWNoJNgQNDIiPFRYWkEAIISo+CpWKhEAgdWJBjHyIpS1ITHSiDAixNM9Dl4rmJRL/APFrc5CjpKLEeXZcW/Xqd8/5veN3rx/THj+8x/6UGmAUXjFQcMINWCbLdRb1NJtJDDCCQ2OtYOsKmI8y0bEoMxAtM/DdsIZebvDqWhKgRABOUL4GYJqZhpOIAtRg5jlpVefj1scCOE3vDTFdiBOKfA96K21xUVerBSg2vY/MdDKVeacIoE91W5zqp9EXwFHuAwLP7MZ8u5YxI03/YZRWJEBp051CixczMe+IsIFKreAv/a8ZCeCo8gIBlSwBiHlRmtXTsQAl5U4A/DVT824KjMma6a/8q92TQKnpzoH5Ti4AwOOa7U9rAZzAC3c/kQcAEa1IS0zqAZruL2IeyQUA2JC2fzAuAeRi3hGVltjx2XvOQDHwvvSb9RmA/ZSWOKxPIMsB1EPMr6VVvaTvghyG0LYh6La0xRMtQPifV4pkBnH3ShjGGVlYXdAChC+doDxPhKuZQoA/S7t6InYStgH+jI/TkPGNQPuzgmht4Xhjn7+cCCBcVFTeLIPuZgIAPJW2fytKS3sfcJT3jkDndgXB9F6a4nw/jdgbUUl5zwG6ngaCmV7UTHFDVxsL0D4TaWaD5hIS2wVRxMWme5aJnxHTqDYN0DoIN+u2/yFJaokS6AoNY+yAsWm+ItBUpDjTUqugLjd47XcS83DNQABd0ZLy7gH0aEeUTPdrpphNatxdlwqg3aaBd4RB7XsjmCp1S/wY1Dx1AmmMUrdhlmYDD6K8zUP9v7bdyyHm8GrKAAAAAElFTkSuQmCC',
        graphicWidth: 30,
        graphicHeight: 30
      }
      f.id = fi.id;
      clustererMarkerMap[f.id] = fi
      // f.info = {
      //   // 'name': fi.deviceName
      //   'name': fi.title
      // }
      f.info = fi
      f.lonlat = fi.lonlat
      f.layerName = fi.layerName
      ps.push(f)
    }
    clusterObj.clustererMarkerMap = clustererMarkerMap
    return ps
  }

  superMap.MapLib.MarkerClusterer = function (map, markers, opts, callback) {
    // 创建聚散图层并添加layers
    // clusterLayer = new SuperMap.Layer.ClusterLayer('Cluster')
    // map.addLayer(clusterLayer)
    // 设置clusterStyles属性
    clusterLayer.clusterStyles = [
      {
        'count': 10, // 子节点小于等于10的聚散点
        // style的详情参见 SuperMap.Feature.Vector.style
        'style': {
          fontColor: '#000',
          fontSize: '12px',
          graphic: true,
          externalGraphic: 'data:image/png;base64,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', // require('./images/m1.png'),// ../../map/images/m1.png
          graphicWidth: 53,
          graphicHeight: 52,
          labelXOffset: 0,
          labelYOffset: 0
        }
      },
      {
        'count': 100, // 子节点小于等于100大于10的聚散点
        'style': {
          fontColor: '#000',
          fontSize: '12px',
          graphic: true,
          externalGraphic: 'data:image/png;base64,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', //require('./images/m2.png'),
          graphicWidth: 56,
          graphicHeight: 55,
          labelXOffset: 0,
          labelYOffset: 0
        }
      },
      {
        'count': 1000, // 子节点小于等于1000大于100的聚散点
        'style': {
          fontColor: '#000',
          fontSize: '12px',
          graphic: true,
          externalGraphic: 'data:image/png;base64,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', //  require('./images/m3.png'),
          graphicWidth: 66,
          graphicHeight: 65,
          labelXOffset: 0,
          labelYOffset: 0
        }
      },
      {
        'count': 10000, // 子节点小于等于10000大于1000的聚散点
        'style': {
          fontColor: '#000',
          fontSize: '12px',
          graphic: true,
          externalGraphic: 'data:image/png;base64,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', // require('./images/m4.png'),
          graphicWidth: 78,
          graphicHeight: 77,
          labelXOffset: 0,
          labelYOffset: 0
        }
      },
      {
        'count': 'moreThanMax', // 子节点大于10000的聚散点
        'style': {
          fontColor: '#000',
          fontSize: '12px',
          graphic: true,
          externalGraphic: 'data:image/png;base64,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', // require('./images/m5.png'),
          graphicWidth: 90,
          graphicHeight: 89,
          labelXOffset: 0,
          labelYOffset: 0
        }
      }
    ]
    // let select = new SuperMap.Control.SelectCluster(clusterLayer, {
    //   callbacks: {
    //     click: function (f) { //点击兴趣点弹出信息窗口
    //       //closeInfoWin();
    //       if (!f.isCluster) { //当点击聚散点的时候不弹出信息窗口
    //         //openInfoWin(f);
    //         callback(f.info)
    //       }
    //     },
    //     clickout: function () {       //点击空白处关闭信息窗口
    //       //closeInfoWin();
    //     }
    //   }
    // });
    // // 将控件添加到map上
    // map.addControl(select);
    // //激活控件
    // select.activate();
    // 往聚散图层中添加兴趣点
    // let clusters = getFeatures(markers, this)
    let clusters = markers
    try {
      clusterLayer.addFeatures(clusters)
    } catch (e) {
      console.log(e)
    }
    if(this.clustererMarkerMap === undefined){
      this.clustererMarkerMap = [];
    }
    if(markers && markers.length >0){
      markers.forEach((item) => {
        this.clustererMarkerMap[item.id] = item
      })
    }
    return $.extend(clusters, this)
  }

  superMap.MapLib.MarkerClusterer.prototype = {
    _addToMarkerMap: function (marker) {
      // this.clustererMarkerMap[marker.getId()] = marker
      if(!this.clustererMarkerMap) {
        this.clustererMarkerMap = []
      }
      this.clustererMarkerMap[marker.id] = marker
    },
    _deleteFromMarkerMap: function (marker) {
      // delete this.clustererMarkerMap[marker.getId()]
      delete this.clustererMarkerMap[marker.id]
    },
    _isMarkerExisted: function (marker) {
      // return !!this.clustererMarkerMap[marker.getId()];
      return !!this.clustererMarkerMap[marker.id];
    },
    _setMarkers: function (markers) {
    },
    _addMarker: function (marker) {
      if (!this._isMarkerExisted(marker)) {
        // let f = generateFea(marker)
        clusterLayer.addFeatures([marker])
        this._addToMarkerMap(marker)
      }
    },
    _removeMarker: function (marker) {
      clusterLayer.destroyCluster()
      let clusters = []
      if (this.clustererMarkerMap[marker.id]) this._deleteFromMarkerMap(marker)
      for (let i in this.clustererMarkerMap) {
        let fi = this.clustererMarkerMap[i]
        // let f = generateFea(fi)
        clusters.push(fi)
      }
      clusterLayer.addFeatures(clusters)
    },
    _addMarkers: function (markers) {
      let clusters = []
      for (let i = 0; i < markers.length; i++) {
        let fi = markers[i]
        if (!this._isMarkerExisted(fi)) {
          // let f = generateFea(fi)
          clusters.push(fi)
          this._addToMarkerMap(fi)
        }
      }
      clusterLayer.addFeatures(clusters)
    },
    _removeMarkers: function (removeMarkers) {
      clusterLayer.destroyCluster()
      let clusters = []
      removeMarkers.forEach(marker => {
        if (this.clustererMarkerMap[marker.getId()]) this._deleteFromMarkerMap(marker)
      })
      for (let i in this.clustererMarkerMap) {
        let fi = this.clustererMarkerMap[i]
        // let f = generateFea(fi)
        clusters.push(fi)
      }
      clusterLayer.addFeatures(clusters)
    },
    _clearMarkers: function () {
      clusterLayer.destroyCluster()
      this.clustererMarkerMap = {}
    },
    _complete: function (callback) {
      callback.call(this)
    }
  }
  /*
   * 聚合重新计算后，重新构造feature要素
   */
  function generateFea(marker){
    let iconUrl = '../images/defaultPoint.png'
    if(marker.icon) {
      iconUrl = marker.icon.url ? marker.icon.url : marker.icon
    }
    if(marker.extData && marker.extData.icon) {
      iconUrl = marker.extData.icon
    }
    let f = new SuperMap.Feature.Vector()
    f.geometry = new SuperMap.Geometry.Point(marker.lonlat.lon, marker.lonlat.lat)
    f.style = {
      pointRadius: 4,
      graphic: true,
      externalGraphic: iconUrl,
      graphicWidth: 30,
      graphicHeight: 30
    }
    f.info = {
      'name': marker.deviceName
    }
    if(marker.label) {
      var label = marker.label
      f.style.label = label.content;
      f.style.labelXOffset = label.offset.x || 0
      f.style.labelYOffset = label.offset.y || 0
      f.style.fontFamily = '微软雅黑'
      f.style.fontSize = 14
      f.style.fontColor = label.fontColor || "#0000ff"
    }
    return f
  }
  superMap.MapLib.MouseTool = function (map) {
    this.map = map
  }

  superMap.MapLib.MouseTool.prototype = {
    // 测距
    /*
   * opts
   * firstLabel: 起点
   * label: 公里
   * */
    _measureDistance: function (opts) {
      if (measureDistanceLayer) {
        measureDistanceLayer.removeAllFeatures()
        map.removeLayer(measureDistanceLayer)
      }
      measureDistanceLayer = new SuperMap.Layer.Vector('lineLayer')
      measureDistanceLayer.style = {
        strokeColor: 'rgba(0,139,202,1)',
        strokeWidth: 2,
        pointerEvents: 'visiblePainted',
        fillColor: 'rgba(0,174,255,0.2)'
      }
      map.addLayer(measureDistanceLayer)
      let drawLine = new SuperMap.Control.DrawFeature(measureDistanceLayer, SuperMap.Handler.Path, { multi: true })
      map.addControl(drawLine)
      drawLine.activate()
      drawLine.events.on({
        'featureadded': (drawGeometryArgs) => {
          drawLine.deactivate()
          let geometry = drawGeometryArgs.feature.geometry
          let measureParam = new SuperMap.REST.MeasureParameters(geometry) /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积 */
          let myMeasuerService = new SuperMap.REST.MeasureService(url) // 量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果

          let count = drawGeometryArgs.feature.geometry.components[0]
          for (let i = 0; i < drawGeometryArgs.feature.geometry.components[0].components.length; i++) {
            let x = count.components[i].x// 获取当前几何的界限上下左右除以2得到x轴，y同理
            let y = count.components[i].y
            let centerPoint1 = new SuperMap.Geometry.Point(x, y)
            let circleVector1 = new SuperMap.Feature.Vector(centerPoint1)
            if (i === 0) {
              circleVector1.style = {
                strokeColor: '#000',
                fillColor: '#00d7e7',
                strokeWidth: 1,
                fillOpacity: 1,
                label: (opts && opts.firstLabel) || '起点',
                fontColor: '#000',
                fontSize: '12px',
                pointRadius: 3,
                labelYOffset: '15'
              }
            }
            measureDistanceLayer.addFeatures([circleVector1])
          }
          myMeasuerService.events.on({
            'processCompleted': (measureEventArgs) => {
              let o = 0
              let DS = 0
              let distance = measureEventArgs.result.distance
              let unit = measureEventArgs.result.unit
              // let span = document.getElementById("result");
              // span.innerHTML = parseInt(distance) + "米";
              // $("#measure").attr('style', 'display:block');
              // layer.alert(span);
              for (let i = 0; i < count.components.length; i++) {
                if (i + 1 == count.components.length) {
                  o = 0
                  DS = 0
                } else {
                  let x = count.components[i].x// 获取当前几何的界限上下左右除以2得到x轴，y同理
                  let y = count.components[i].y
                  let points = [new SuperMap.Geometry.Point(x, y),
                    new SuperMap.Geometry.Point(count.components[i + 1].x, count.components[i + 1].y)]
                  let roadLine = new SuperMap.Geometry.LineString(points)
                  let measureParam = new SuperMap.REST.MeasureParameters(roadLine) /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积 */
                  let myMeasuerService = new SuperMap.REST.MeasureService(url) // 量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
                  myMeasuerService.events.on({
                    'processCompleted': (measureEventArgs) => {
                      o++
                      let distance = measureEventArgs.result.distance
                      DS += distance
                      let unit = measureEventArgs.result.unit
                      let x = count.components[o].x// 获取当前几何的界限上下左右除以2得到x轴，y同理
                      let y = count.components[o].y
                      let centerPoint1 = new SuperMap.Geometry.Point(x, y)
                      let circleVector1 = new SuperMap.Feature.Vector(centerPoint1)

                      circleVector1.style = {
                        strokeColor: '#000',
                        fillColor: '#00d7e7',
                        strokeWidth: 1,
                        fillOpacity: 1,
                        fontColor: '#000',
                        fontSize: '12px',
                        pointRadius: 3,
                        labelYOffset: '15',
                        label: (DS / 1000).toFixed(2) + (opts && opts.label) || '公里'
                      }
                      measureDistanceLayer.addFeatures([circleVector1])
                    }
                  })

                  // 对MeasureService类型进行判断和赋值，当判断出是LineString时设置MeasureMode.DISTANCE，否则是MeasureMode.AREA

                  myMeasuerService.measureMode = SuperMap.REST.MeasureMode.DISTANCE

                  myMeasuerService.processAsync(measureParam) // processAsync负责将客户端的量算参数传递到服务端。
                }
              }
            }
          })
          myMeasuerService.measureMode = SuperMap.REST.MeasureMode.DISTANCE
          myMeasuerService.processAsync(measureParam)
        }
      })
    },
    // 测面
    /*
   * opts
   * label: 平方公里
   * */
    _measureArea: function (opts) {
      if (measureAreaLayer) {
        measureAreaLayer.removeAllFeatures()
        map.removeLayer(measureAreaLayer)
      }
      measureAreaLayer = new SuperMap.Layer.Vector('areaLayer')
      measureAreaLayer.style = {
        strokeColor: 'rgba(0,139,202,1)',
        strokeWidth: 2,
        pointerEvents: 'visiblePainted',
        fillColor: 'rgba(0,174,255,0.2)'
      }
      map.addLayer(measureAreaLayer)
      let drawPolygon = new SuperMap.Control.DrawFeature(measureAreaLayer, SuperMap.Handler.Polygon)
      map.addControl(drawPolygon)
      drawPolygon.activate()
      drawPolygon.events.on({
        'featureadded': (drawGeometryArgs) => {
          drawPolygon.deactivate()
          let geometry = drawGeometryArgs.feature.geometry
          let measureParam = new SuperMap.REST.MeasureParameters(geometry) /* MeasureParameters：量算参数类。 客户端要量算的地物间的距离或某个区域的面积 */
          let myMeasuerService = new SuperMap.REST.MeasureService(url) // 量算服务类，该类负责将量算参数传递到服务端，并获取服务端返回的量算结果
          let point = geometry.getCentroid()
          myMeasuerService.events.on({
            'processCompleted': (measureEventArgs) => {
              let area = measureEventArgs.result.area
              let circleVector1 = new SuperMap.Feature.Vector(point)
              circleVector1.style = {
                strokeColor: '#000',
                fillColor: '#00d7e7',
                strokeWidth: 0,
                fillOpacity: 0,
                label: (area / 1000 / 1000).toFixed(2) + ((opts && opts.label) || '平方公里'),
                fontColor: '#000',
                fontSize: '12px',
                pointRadius: 3,
                labelYOffset: '15'
              }
              measureAreaLayer.addFeatures([circleVector1])
            }
          })
          myMeasuerService.measureMode = SuperMap.REST.MeasureMode.AREA
          myMeasuerService.processAsync(measureParam)
        }
      })
    },
    _clearDrawOverlays: function () {
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      }
      if (measureDistanceLayer) {
        measureDistanceLayer.removeAllFeatures()
      }
      if (measureAreaLayer) {
        measureAreaLayer.removeAllFeatures()
      }
    },
    // 画圈
    _drawCircle: function (cb) {
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawCircle = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.RegularPolygon, {
        handlerOptions: {
          sides: 50
        }
      })
      map.addControl(drawCircle)
      drawCircle.activate()
      drawCircle.events.on({
        'featureadded': (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawCircle.deactivate()
          // console.log(feature.geometry.intersects(new SuperMap.Geometry.Point(121.544379, 31.221517)))
          cb(feature.geometry)
        }
      })
    },
    // 多边形
    _drawPolygon: function (cb) {
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawPolygon = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Polygon)
      map.addControl(drawPolygon)
      drawPolygon.activate()
      drawPolygon.events.on({
        'featureadded': (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawPolygon.deactivate()
          // console.log(feature.geometry.intersects(new SuperMap.Geometry.Point(121.544379, 31.221517)))
          cb(feature.geometry)
        }
      })
    },
    // 点
    _drawPoint: function (cb) {
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawPoint = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Point)
      map.addControl(drawPoint)
      drawPoint.activate()
      drawPoint.events.on({
        'featureadded': (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawPoint.deactivate()
          // console.log(feature.geometry.intersects(new SuperMap.Geometry.Point(121.544379, 31.221517)))
          cb(feature.geometry)
        }
      })
    },
    // 线
    _drawLine: function (cb) {
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawLine = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Path)
      map.addControl(drawLine)
      drawLine.activate()
      drawLine.events.on({
        'featureadded': (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawLine.deactivate()
          // console.log(feature.geometry.intersects(new SuperMap.Geometry.Point(121.544379, 31.221517)))
          cb(feature.geometry)
        }
      })
    },
    // 画框
    _drawRectangle: function (cb) {
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawBox = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Box, {
        handlerOptions: {
          cursorCSS: 'crosshair'
        }
      })
      map.addControl(drawBox)
      drawBox.events.on({
        featureadded: (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawBox.deactivate()
          // console.log(feature.geometry.intersects(new SuperMap.Geometry.Point(121.544379, 31.221517)))
          cb(feature.geometry)
        }
      })
      drawBox.activate()
    },
    // 圈选要素
    _selectCircle: function (markers, cb, opts){
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawCircle = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.RegularPolygon, {
        handlerOptions: {
          sides: 50
        }
      })
      map.addControl(drawCircle)
      drawCircle.activate()
      drawCircle.events.on({
        'featureadded': (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          let bounds = feature.geometry.bounds;
          let resultMarkers = [];
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawCircle.deactivate()
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (feature.geometry.intersects(new superMap.Point(pt.lng, pt.lat))) {
              resultMarkers.push(markers[i])
            }
          }
          // map.removeLayer(drawVectorLayer)
          cb(resultMarkers, feature.geometry)
        }
      })
    },
    // 框选
    _selectRectangle: function (markers, cb, opts){
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawBox = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Box, {
        handlerOptions: {
          cursorCSS: 'crosshair'
        }
      })
      map.addControl(drawBox)
      drawBox.events.on({
        featureadded: (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          let resultMarkers = [];
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawBox.deactivate()
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (feature.geometry.intersects(new superMap.Point(pt.lng, pt.lat))) {
              resultMarkers.push(markers[i])
            }
          }
          // map.removeLayer(drawVectorLayer)
          cb(resultMarkers)
        }
      })
      drawBox.activate()
    },
    // 多边形选
    _selectPolygon: function (markers, cb, opts) {
      let map = this.map
      if (drawVectorLayer) {
        drawVectorLayer.removeAllFeatures()
      } else {
        drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
      }
      map.addLayer(drawVectorLayer)
      let drawPolygon = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Polygon)
      map.addControl(drawPolygon)
      drawPolygon.activate()
      drawPolygon.events.on({
        'featureadded': (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          let bounds = feature.geometry;
          let resultMarkers = [];
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawPolygon.deactivate()
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (feature.geometry.intersects(new superMap.Point(pt.lng, pt.lat))) {
              resultMarkers.push(markers[i])
            }
          }
          // map.removeLayer(drawVectorLayer)
          cb(resultMarkers, feature.geometry)
        }
      })
    }
  }
  /**
   *
   * Control接口
   */
  superMap.Controls = {};
  superMap.Controls.ToolBar = function (map, opts) {

  };
  superMap.Controls.ToolBar.prototype = {
    _hide() {
    },
    _show() {
    },
    _add(map) {
    },
    _remove: function (map) {
    }
  };

  // 基础控件
  superMap.Controls.Zoom = function (map) {
    //初始化简易缩放控件类
    if(map.getControlsBy('displayClass',"smControlZoom").length>=1){
      return
    }
    let zoom = new SuperMap.Control.Zoom();
    map.addControl(zoom)
    return $.extend(zoom, this)
  };
  superMap.Controls.Zoom.prototype = {
    _add: function() {
      map.addControl(this);
    },
    _remove: function () {
      this.destroy()
    },
    _zoomIn: function () {
       map.zoomIn()
    },
    _zoomOut: function () {
       map.zoomOut()
    }
  };
  superMap.Controls.LayerSwitcher = function (map,opts) {
    // if(map.getControlsBy('displayClass',"smControlLayerSwitcher").length>=1){
    //   return
    // }
    // let layerWorld = new SuperMap.Layer.TiledDynamicRESTLayer("World33", 'https://iserver.supermap.io/iserver/services/map-china400/rest/maps/China_4326', {
    //   transparent: true,
    //   cacheEnabled: true
    // });
    // layerWorld.events.on({"layerInitialized": ()=>{
    //     map.addLayer(layerWorld);
    //     map.setCenter(new SuperMap.LonLat(118, 40), 7);
    // }});
    // let LayerSwitcher=new SuperMap.Control.LayerSwitcher()
    // map.addControl(LayerSwitcher)
    // return $.extend(LayerSwitcher, this)
  };
  superMap.Controls.LayerSwitcher.prototype = {
    _add() {
      // map.addControl(this)
    },
    _remove: function (map) {
      // this.destroy()
      // this.hide()
      // map.removeControl(this)
    },
    _showLayersPanel: function () {
    },
    addData() {

    },
    _hideLayersPanel: function () {
    },
    addLayer:function(event,handle){

    }
  };
  superMap.Controls.Traffic = function (map, opts) {

  };
  superMap.Controls.Traffic.prototype = {
    _add(map) {
    },
    _remove: function (map) {
    },
    _open: function () {
    },
    _close: function () {
    },
    _showButton: function () {
    },
    _hideButton: function () {
    }
  };
  superMap.Controls.OverView = function (map, opts) {
    //初始化鹰眼控件类
    let overviewmap = new SuperMap.Control.OverviewMap();
    return overviewmap;
  };
  superMap.Controls.OverView.prototype = {};
  superMap.Controls.Scale = function (map, opts){
    let scaleline = new SuperMap.Control.ScaleLine();
    //是否使用依地量算，默认为false。推荐地图投影为EPSG:4326时设置为false；使用EPSG:900913时设置为true。为true时，比例值按照当前视图中心的水平线计算。
    scaleline.geodesic = true;
    return scaleline;
  }
  superMap.Controls.Scale.prototype = {}
  superMap.Controls.MousePosition = function (map) {
    let mp = new SuperMap.Control.MousePosition();
    map.addControl(mp)
    return $.extend(mp, this)
  };
  superMap.Controls.MousePosition.prototype = {
    _add: function() {
      map.addControl(this);
    },
    _remove: function () {
      this.destroy()
    }
  };
    /**
   *
   * polyline接口
   */
  // 地图上加线 -- ol.layer.Vector
  superMap.TrackPolyline = function (map, lineArrR, opts) {
    // superMap.Map()
    //线串几何对象
    let points2=[]
    lineArrR.map(res=>{
      points2.push(new SuperMap.Geometry.Point(res.lng, res.lat))
    })
    this.markers = new SuperMap.Layer.Markers("Markers");
    map.addLayers([this.markers]);
    let size = new SuperMap.Size(21, 25);
    let offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
    const mapBegin = 'data:image/png;base64,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'
    const mapFinish = 'data:image/png;base64,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'
    let icon = new SuperMap.Icon(mapBegin, size, offset);
    let icon2=new SuperMap.Icon(mapFinish, size, offset);
    //初始化标记覆盖物类
    let marker = new SuperMap.Marker(new SuperMap.LonLat(lineArrR[0].lng, lineArrR[0].lat), icon);
    let marker2 = new SuperMap.Marker(new SuperMap.LonLat(lineArrR[lineArrR.length-1].lng, lineArrR[lineArrR.length-1].lat), icon2);
    this.markers.addMarker(marker);
    this.markers.addMarker(marker2);
    let line1 = new SuperMap.Geometry.LineString(points2);
    let linecVector = new SuperMap.Feature.Vector(line1);
    if(opts!=undefined){
      linecVector.style = {
        strokeColor: opts.strokeColor||'#FFA200', // 线颜色
        strokeOpacity: opts.strokeOpacity||1, // 线透明度
        strokeWeight: opts.strokeWeight||3, // 线宽
        strokeStyle: opts.strokeStyle||'solid', // 线样式
        isFresh: opts.isFresh||false
      };
    }else{
      linecVector.style = {
        strokeColor: '#FFA200', // 线颜色
        strokeOpacity: 1, // 线透明度
        strokeWeight: 3, // 线宽
        strokeStyle: 'solid', // 线样式
        isFresh:false
      };
    }

    let vector = new SuperMap.Layer.Vector("vector");
    vector.addFeatures([linecVector])
    map.addLayers([vector])
    this.map = map
    return $.extend(vector,this)
  }

  superMap.TrackPolyline.prototype = {
    _remove: function () {
      map.removeLayer(this)
      this.markers.clearMarkers()
    },
    _setPath: function (lineArrR, opts) {
      this.markers.clearMarkers()
      map.removeLayer(this)
      this.removeAllFeatures();
      this.refresh();
      let size = new SuperMap.Size(21, 25);
      let offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
      const mapBegin = 'data:image/png;base64,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'
      const mapFinish = 'data:image/png;base64,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'
      let icon = new SuperMap.Icon(mapBegin, size, offset);
      let icon2=new SuperMap.Icon(mapFinish, size, offset);
      //初始化标记覆盖物类
      let marker = new SuperMap.Marker(new SuperMap.LonLat(lineArrR[0].lng, lineArrR[0].lat), icon);
      let marker2 = new SuperMap.Marker(new SuperMap.LonLat(lineArrR[lineArrR.length-1].lng, lineArrR[lineArrR.length-1].lat), icon2);
      //添加覆盖物到标记图层
      this.markers.addMarker(marker);
      this.markers.addMarker(marker2);
      let points2=lineArrR.map(res=>{
        return  new SuperMap.Geometry.Point(res.lng, res.lat)
      })
      let line1 = new SuperMap.Geometry.LineString(points2);
      let linecVector = new SuperMap.Feature.Vector(line1);
      if(opts!=undefined){
        linecVector.style = {
          strokeColor: opts.strokeColor||'#FFA200', // 线颜色
          strokeOpacity: opts.strokeOpacity||1, // 线透明度
          strokeWeight: opts.strokeWeight||3, // 线宽
          strokeStyle: opts.strokeStyle||'solid', // 线样式
          isFresh: opts.isFresh||false
        };
      }else{
        linecVector.style = {
          strokeColor: '#FFA200', // 线颜色
          strokeOpacity: 1, // 线透明度
          strokeWeight: 3, // 线宽
          strokeStyle: 'solid', // 线样式
          isFresh:false
        };
      }
      this.addFeatures([linecVector])
      map.addLayer(this)
    },
    _setDirectlineMap: function (map) {

    }
  }

  /**
   *
   * polyline接口
   */
  // 地图上加线 -- ol.layer.Vector
  superMap.Polyline = function (map, lineArrR, opts) {
    // superMap.Map()
    //线串几何对象
    let points2=[]
    lineArrR.map(res=>{
      points2.push(new SuperMap.Geometry.Point(res.lng, res.lat))
    })

    let line1 = new SuperMap.Geometry.LineString(points2);
    let linecVector = new SuperMap.Feature.Vector(line1);
    if(opts!=undefined){
      linecVector.style = {
        strokeColor: opts.strokeColor||'#FFA200', // 线颜色
        strokeOpacity: opts.strokeOpacity||1, // 线透明度
        strokeWidth: opts.strokeWeight||3, // 线宽
        strokeStyle: opts.strokeStyle||'solid', // 线样式
        isFresh: opts.isFresh||false
      };
    }else{
      linecVector.style = {
        strokeColor: '#FFA200', // 线颜色
        strokeOpacity: 1, // 线透明度
        strokeWidth: 3, // 线宽
        strokeStyle: 'solid', // 线样式
        isFresh:false
      };
    }

    vector.addFeatures([linecVector])
    this.feature = linecVector
    this.map = map
    this.visibility = true
    return this
  }

  superMap.Polyline.prototype = {
    _hide: function () {
      if (this.visibility === true) {
        vector.removeFeatures([this.feature])
        this.visibility = false
      }
    },
    _show: function () {
      if (this.visibility === false) {
        vector.addFeatures([this.feature])
        this.visibility = true
      }
    },
    _remove: function () {
      vector.removeFeatures([this.feature])
    }
  }

  /**
   *
   * Text接口
   */
  // 地图上加线 -- ol.layer.Vector
  superMap.Text = function (map, opts) {
    var geoText = new SuperMap.Geometry.GeoText(opts.position.lng, opts.position.lat, opts.text);
    var geotextFeature = new SuperMap.Feature.Vector(geoText);

    textLayer.addFeatures([geotextFeature])
    this.map = map
    this.feature = geotextFeature
    this.visibility = true
    return this
  }

  superMap.Text.prototype = {
    _hide: function () {
      if (this.visibility === true) {
        textLayer.removeFeatures([this.feature])
        this.visibility = false
      }
    },
    _show: function () {
      if (this.visibility === false) {
        textLayer.addFeatures([this.feature])
        this.visibility = true
      }
    },
    _remove: function () {
      textLayer.removeFeatures([this.feature])
    },
    _addEventListener: function (event, handler) {
      if(event === 'click'){
        event = 'featureselected'
      }
      if(textLayer.events.listeners.featureselected< 1) {
        textLayer.events.on({
          [event]: function () {
            if (this.selectedFeatures && this.selectedFeatures[0])
              handler && handler(this.selectedFeatures[0])
          }
        })
      }
    }
  }

  /**
   *
   * polygon接口
   */
  superMap.Polygon = function (map, opts) {
    // superMap.Map()
    //线串几何对象
    let points2=[]
    opts.path.map(res=>{
      res.lng && res.lat && points2.push(new SuperMap.Geometry.Point(res.lng, res.lat))
      res[0] && res[1] && points2.push(new SuperMap.Geometry.Point(res[0], res[1]))
    })

    let line1 = new SuperMap.Geometry.LinearRing(points2);
    let region = new SuperMap.Geometry.Polygon([line1]);
    let regionVector = new SuperMap.Feature.Vector(region);
    regionVector.style = {
      strokeColor: opts.strokeColor||'#FFA200', // 线颜色
      strokeOpacity: opts.strokeOpacity||1, // 线透明度
      strokeWidth: opts.strokeWidth||3, // 线宽
      strokeStyle: opts.strokeStyle||'solid', // 线样式
      isFresh: opts.isFresh||false,
      fillColor: opts.fillColor || '#C6E2FF',
      fillOpacity: opts.fillOpacity || 0.5
    };
    this.opts = opts;
    this.geometry = regionVector.geometry;
    this.extData = opts.extData;
    this.feature = regionVector
    this.visibility = true
    vector.addFeatures([regionVector])
    // map.addLayers([vector])
    return this
  };

  superMap.Polygon.prototype = {
    _hide: function () {
      if (this.visibility === true) {
        vector.removeFeatures([this.feature])
        this.visibility = false
      }
    },
    _show: function () {
      if (this.visibility === false) {
        vector.addFeatures([this.feature])
        this.visibility = true
      }
    },
    _remove: function () {
      vector.removeFeatures([this.feature])
    },
    _contains: function (lng, lat) {
      let pt = new superMap.Point(lng, lat);
      let result = this.geometry.intersects(pt);
      return result;
    }
  };

  /**
   *
   * rectangle接口
   */
  superMap.Rectangle = function (map, opts) {
    let bounds = opts.bounds;
    let oPath = [
      [bounds.southwest[0], bounds.northeast[1]],
      [bounds.southwest[0], bounds.southwest[1]],
      [bounds.northeast[0], bounds.southwest[1]],
      [bounds.northeast[0], bounds.northeast[1]]
    ]
    let points2=[]
    oPath.map(res=>{
      points2.push(new SuperMap.Geometry.Point(res[0], res[1]))
    })

    let line1 = new SuperMap.Geometry.LinearRing(points2);
    let region = new SuperMap.Geometry.Polygon([line1]);
    let regionVector = new SuperMap.Feature.Vector(region);
    regionVector.style = {
      strokeColor: opts.strokeColor||'#FFA200', // 线颜色
      strokeOpacity: opts.strokeOpacity||1, // 线透明度
      strokeWeight: opts.strokeWeight||3, // 线宽
      strokeStyle: opts.strokeStyle||'solid', // 线样式
      isFresh: opts.isFresh||false,
      fillColor: opts.fillColor || '#C6E2FF',
      fillOpacity: opts.fillOpacity || 0.5
    };

    let vector = new SuperMap.Layer.Vector("vector");
    vector.addFeatures([regionVector])
    this.geometry = regionVector.geometry;
    // map.addLayers([vector])
    return $.extend(vector, this)
  };

  superMap.Rectangle.prototype = {
    hide: function () {
      this.setVisibility(false)
    },
    _hide: function () {
      this.setVisibility(false)
    },
    show: function () {
      this.setVisibility(true)
    },
    _show: function () {
      this.setVisibility(true)
    },
    remove: function () {
      map.removeLayer(this)
    },
    _remove: function () {
      map.removeLayer(this)
    },
    _contains: function (lng, lat) {
      let pt = new superMap.Point(lng, lat);
      let result = this.geometry.intersects(pt);
      return result;
    }
  };

  /**
   *
   * circle接口
   */
  superMap.Circle = function (map, opts) {
    let origin = new SuperMap.Geometry.Point(opts.center.lng,opts.center.lat)
    let radius = opts.radius || 1 // 半径单位度
    var degree = 2*Math.PI*Math.cos(2*Math.PI/360*opts.center.lat)*6378140/360
    radius = (1/degree)*radius;
    let sides = 256
    let r = 360
    let angel = 360
    let circle = createCircle(origin, radius, sides, r, angel)
    let regionVector = new SuperMap.Feature.Vector(circle);
    regionVector.style = {
      strokeColor: opts.strokeColor||'#FFA200', // 线颜色
      strokeOpacity: opts.strokeOpacity||1, // 线透明度
      strokeWeight: opts.strokeWeight||3, // 线宽
      strokeStyle: opts.strokeStyle||'solid', // 线样式
      isFresh: opts.isFresh||false,
      fillColor: opts.fillColor || '#C6E2FF',
      fillOpacity: opts.fillOpacity || 0.5
    };

    // let vector = new SuperMap.Layer.Vector("vector");
    vectorCircle.addFeatures([regionVector])
    this.geometry = regionVector.geometry;
    this.feature = regionVector
    return $.extend(vectorCircle, this)
  };
  superMap.Circle.prototype = {
    hide: function () {
      this.setVisibility(false)
    },
    _hide: function () {
      this.setVisibility(false)
    },
    show: function () {
      this.setVisibility(true)
    },
    _show: function () {
      this.setVisibility(true)
    },
    remove: function () {
      vectorCircle.removeFeatures([this.feature])
    },
    _remove: function () {
      vectorCircle.removeFeatures([this.feature])
    },
    _contains: function (pt) {
      let result = false
      if (!pt.geometry) {
        let tempPt = new superMap.Point(pt._getPosition().lng, pt._getPosition().lat);
        result = this.geometry.intersects(tempPt);
      } else {
        result = this.geometry.intersects(pt.geometry);
      }
      return result;
    }
  };

  /*
   * 扇形
   * angle:圆心角
   * rotate：旋转角，从平面直角坐标系x轴正轴方向逆时针旋转的角度
   * center：圆心
   * radius：半径，单位米
   */
  superMap.Sector = function (map, opts) {
    let origin = new SuperMap.Geometry.Point(opts.center.lng,opts.center.lat)
    let radius = opts.radius || 1 // 半径单位度
    var degree = 2*Math.PI*Math.cos(2*Math.PI/360*opts.center.lat)*6378140/360
    radius = (1/degree)*radius;
    let sides = 256
    let centerAngle = opts.angle || 120
    let rotate = opts.rotate || 0
    let circle = createSector(origin, radius, sides, centerAngle, rotate)
    let regionVector = new SuperMap.Feature.Vector(circle);
    regionVector.style = {
      strokeColor: opts.strokeColor||'#FFA200', // 线颜色
      strokeOpacity: opts.strokeOpacity||1, // 线透明度
      strokeWeight: opts.strokeWeight||3, // 线宽
      strokeStyle: opts.strokeStyle||'solid', // 线样式
      isFresh: opts.isFresh||false,
      fillColor: opts.fillColor || '#C6E2FF',
      fillOpacity: opts.fillOpacity || 0.5
    };
    vectorCircle.addFeatures([regionVector])
    this.geometry = regionVector.geometry;
    this.feature = regionVector
    return $.extend(vectorCircle, this)
  };
  superMap.Sector.prototype = {
    hide: function () {
      this.setVisibility(false)
    },
    _hide: function () {
      this.setVisibility(false)
    },
    show: function () {
      this.setVisibility(true)
    },
    _show: function () {
      this.setVisibility(true)
    },
    remove: function () {
      vectorCircle.removeFeatures([this.feature])
    },
    _remove: function () {
      vectorCircle.removeFeatures([this.feature])
    },
    _contains: function (lng, lat) {
      let pt = new superMap.Point(lng, lat);
      let result = this.geometry.intersects(pt);
      return result;
    }
  };


  function createCircle(origin, radius, sides, r, angel) {
    var rR = r * Math.PI / (180 * sides);
    var rotatedAngle, x, y;
    var points = [];
    for (var i = 0; i < sides; ++i) {
      rotatedAngle = rR * i;
      x = origin.x + (radius * Math.cos(rotatedAngle));
      y = origin.y + (radius * Math.sin(rotatedAngle));
      points.push(new SuperMap.Geometry.Point(x, y));
    }
    rotatedAngle = r * Math.PI / 180;
    x = origin.x + (radius * Math.cos(rotatedAngle));
    y = origin.y + (radius * Math.sin(rotatedAngle));
    points.push(new SuperMap.Geometry.Point(x, y));

    var ring = new SuperMap.Geometry.LinearRing(points);
    ring.rotate(parseFloat(angel), origin);
    var geo = new SuperMap.Geometry.Collection([ring]);
    geo.origin = origin;
    geo.radius = radius;
    geo.r = r;
    geo.angel = angel;
    geo.sides = sides;
    geo.polygonType = "Curve";
    return geo;
  }

  /*
 * r: 扇形圆心角
 * angle：从平面直角坐标系x轴正轴方向逆时针旋转的角度
 */
  function createSector(origin, radius, sides, r, angel) {
    var rR = r * Math.PI / (180 * sides);
    var rotatedAngle, x, y;
    var points = [];
    for (var i = 0; i < sides; ++i) {
      rotatedAngle = rR * i;
      x = origin.x + (radius * Math.cos(rotatedAngle));
      y = origin.y + (radius * Math.sin(rotatedAngle));
      points.push(new SuperMap.Geometry.Point(x, y));
    }
    points.push(new SuperMap.Geometry.Point(origin.x, origin.y));// 将原点放入

    var ring = new SuperMap.Geometry.LinearRing(points);
    ring.rotate(parseFloat(angel), origin);
    var geo = new SuperMap.Geometry.Collection([ring]);
    geo.origin = origin;
    geo.radius = radius;
    geo.r = r;
    geo.angel = angel;
    geo.sides = sides;
    geo.polygonType = "Curve";
    return geo;
  }
  /**
   *
   * InfoWindow接口
   */
  superMap.InfoWindow = function (content, opts) {
    let infoWindowOptions = {
      content: content,
      isCustom: true,
      autoMove: true,
      xAxis:opts.xAxis||0,
      yAxis:opts.yAxis||0,
      width:opts.width||'200px',
      height:opts.height||"200px",
      color:opts.color||"#000",
      fontSize:opts.fontSize||'12px'
    }
      // let contentHTML = `<div style='width:80px; font-size: ${infoWindowOptions.fontSize};font-weight:bold ; opacity: 0.8;color: ${infoWindowOptions.color};'>` ;
      // contentHTML += content;
      // contentHTML += "</div>";
    let contentHTML = content;
      let position = new SuperMap.LonLat(infoWindowOptions.xAxis, infoWindowOptions.yAxis)
      if(opts.lnglat){
        position = new SuperMap.LonLat(opts.lnglat.lng, opts.lnglat.lat)
      }
      let popup = new SuperMap.Popup(
        "chicken",
        position,
        new SuperMap.Size(infoWindowOptions.width,infoWindowOptions.height),
        contentHTML,
        true,
      );
      popup.closeBox=true
      map.addPopup(popup);
      return $.extend(popup, this)
  };
  superMap.InfoWindow.prototype = {
    _open: function (map) {
      this.show()
      map.addPopup(this);;
    },
    _close: function () {
      this.hide()

    },
    _stopPropagation: function () {

    }
  };

  // 热力图
  superMap.Heatmap = function (opts, map) {
    this.map = map;
    opts.blur = opts.blur || 10
    opts.radius = opts.radius || 10
    opts.field = opts.field || 'alarm'
    opts.gradient = opts.gradient || ['#00f', '#0ff', '#0f0', '#ff0', '#f00']
    var heatPoints = [];
    for (var i = 0; i < opts.dataSource.length; i++) {
      let item = opts.dataSource[i]
      heatPoints[i] = new SuperMap.Feature.Vector(
        new SuperMap.Geometry.Point(item.lng, item.lat),
        {
          "value": item.count
        }
      );
    }
    if(heatMapLayer){
      heatMapLayer.removeAllFeatures()
    }
    heatMapLayer = new SuperMap.Layer.HeatMapFastLayer(
      "heatMap",
      {
        "featureWeight": "value"
      }
    );
    heatMapLayer.addFeatures(heatPoints);
    map.addLayer(heatMapLayer)
    return $.extend(heatMapLayer, this)
  }
  superMap.Heatmap.prototype = {
    toggleShow: function (map) {
      map.addLayer(this)
    },
    toggleHide: function () {
      this.map.removeLayer(this)
    }
  }
  /*
   *图层
   */
  superMap.Layer = function (map, name, opts) {
    this.name = name
    this.source = []
    return this;
  }
  superMap.Layer.prototype = {
    // 将图层加入地图
    _addToMap: function (map) {
      this.source.forEach((item) => {
        item._add();
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
    },
    _hide: function () {
    },
    _getMarkers: function () {
      return this.source;
    }
  }
  /*
   *右键菜单
   */
  superMap.ContextMenu = function (element, menuId) {
    this.element = element;// 右键菜单绑定到哪个要素上
    var mapId;
    try{
      // 右键菜单绑在地图上
      mapId = element.div.id;
    }catch (e){
      // 右键菜单绑在Marker上
    }
    var mapDiv = document.getElementById(mapId);
    // var mapDiv = document.getElementById("map-container");
    var EventUtil = {
      addHandler: function (element, type, handler) {
        if (element.addEventListener) {
          element.addEventListener(type, handler, false);
        }
        else if (element.attachEvent) {
          element.attachEvent("on" + type, handler);
        }
      },
      getEvent: function (event) {
        return event ? event : window.event;
      },
      //取消事件的默认行为
      preventDefault: function (event) {
        if (event.preventDefault) {
          event.preventDefault();
        } else {
          event.returnValue = false;
        }
      },
      stopPropagation: function (event) {
        if (event.stopPropagation) {
          event.stopPropagation();
        } else {
          event.cancelBubble = true;
        }
      }
    };
    var menuElement = document.createElement('div')
    menuElement.className = 'contextmenu'
    menuElement.style.position = 'absolute'
    menuElement.id = menuId || "contextmenu_container"
    let menuUl = document.createElement('ul')
    menuUl.className = 'menuul'
    menuElement.appendChild(menuUl)
    document.body.appendChild(menuElement);
    if(!mapDiv) {
      mapDiv = menuElement
    }
    this.mapDiv = mapDiv;
    this.menuUl = menuUl;
    this.menuElement = menuElement;
    return $.extend(EventUtil, this)
  }
  superMap.ContextMenu.prototype = {
    _addItem: function (itemname, handler, index) {
      let self = this;
      let li = document.createElement('li')
      li.className = 'menuitem'
      li.innerHTML = itemname
      li.onclick = function (e) {
        self.menuElement.style.visibility = "hidden";
        handler(e) // 回调事件
      }
      this.menuUl.appendChild(li)
      self.addHandler(this.mapDiv, "contextmenu", function (event) {
        // map右键菜单在这里调用
        event = self.getEvent(event);
        self.preventDefault(event);
        self.menuElement.style.left = event.clientX + "px";
        self.menuElement.style.top = event.clientY + "px";
        self.menuElement.style.visibility = "visible";
      });
    },
    _open: function (map, e) {
      // marker右键菜单依靠此方法调用
      var lonlat = this.element.getLonLat();
      var p = map.getPixelFromLonLat(lonlat);
      this.menuElement.style.left = p.x + "px";
      this.menuElement.style.top = p.y + 9 + "px";
      this.menuElement.style.visibility = "visible";
    },
    _close: function () {
      this.menuElement.style.visibility = "hidden";
    }
  }
/*
 *自定义边界
 */
  superMap.Bounds = function (mapExtent) {
    let bds = mapExtent // 获取当前地图的范围。
    this.nlat = bds.top
    this.slat = bds.bottom
    this.wlng = bds.left
    this.elng = bds.right
    return $.extend(bds, this)
  }
  superMap.Bounds.prototype = {
    getNorthEast: function () {
      return new superMap.Point(this.elng, this.nlat);
    },
    getSouthWest: function () {
      return new superMap.Point(this.wlng, this.slat);
    },
    containsLngLat: function (position) {
      return this.containsLonLat(position)
    }
  };
  superMap.gmUtil = {
    distance: function (p0, p1) {
      let dis = SuperMap.Util.distVincenty(new SuperMap.LonLat(p0[0], p0[1]), new SuperMap.LonLat(p1[0], p1[1]))
      return dis;
    },
    distanceToLine: function (p0, p1s) {
      let pt = new SuperMap.Geometry.Point(p0[0], p0[1])
      let line = new SuperMap.Geometry.LineString(p1s)
      let dis = line.distanceTo(pt)
      return dis;
    }
  }
  /*
   *自定义点
   */
  superMap.Point = function (lng, lat) {
    let geo = new SuperMap.Geometry.Point(lng, lat)
    this.lng = lng;
    this.lat = lat;
    return $.extend(geo, this)
  }
  superMap.Point.prototype = {
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

  window.superMap = superMap
  return superMap
}
export default znvSuperMap
