/* eslint-disable */
// import '../superMap/libs/SuperMap.Include.js'
// import '../superMap/libs/SuperMap-8.1.1-14426.js'
import $ from 'jquery'

function znvSuperMap (mapServeUrl) {
  let superMap = {}
  let map
  let layer
  let layer1
  let drawVectorLayer, markerlayer, clusterLayer, heatMapLayer, vector, vectorCircle
  let measureDistanceLayer, measureAreaLayer
  let url =  mapServeUrl || 'https://iserver.supermap.io/iserver/services/map-world/rest/maps/World'
  // let url = mapServeUrl || 'https://iserver.supermap.io/iserver/services/map-china400/rest/maps/China_4326'
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
        // new SuperMap.Control.Zoom(),
        new SuperMap.Control.Navigation()],
      allOverlays: true
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
    // map.addControl(new SuperMap.Control.MousePosition())
    // 初始化图层
    layer = new SuperMap.Layer.TiledDynamicRESTLayer('ditu', url, null, { maxResolution: 'auto' })
    // layer = new SuperMap.Layer.TiledDynamicRESTLayer('zmb', url, null, { maxResolution: 'auto' })
    // layer = new SuperMap.Layer.CloudLayer();
    clusterLayer = new SuperMap.Layer.ClusterLayer('Cluster')
    markerlayer = new SuperMap.Layer.Markers('markerLayer')
    vector = new SuperMap.Layer.Vector("vector");
    vectorCircle = new SuperMap.Layer.Vector("vectorCircle");
    heatMapLayer = new SuperMap.Layer.HeatMapFastLayer(
      "heatMap",
      {
        "featureWeight": "value"
      }
    );
    drawVectorLayer = new SuperMap.Layer.Vector('polygonLayer')
    // 监听图层信息加载完成事件
    layer.events.on({
      layerInitialized: function (evt) {
        map.addLayers([layer, vector, vectorCircle, heatMapLayer, drawVectorLayer, clusterLayer, markerlayer])
        // 显示地图范围
        if(opts.center) {
          opts.center = [opts.center.lng,opts.center.lat];
        }
        let center = opts.center || [119.039090, 31.664200]
        let zoom = opts.zoom || 8
        map.setCenter(new SuperMap.LonLat(center[0], center[1]), zoom) // 上海浦东新区中心点，可换成其他中心点
        callback && callback()
      }
    })
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
    map = $.extend(map, this)
    return map
  }
  /**
   map接口
   */
  superMap.Map.prototype = {
    _centerAndZoom: function (point, zoom) {
      if(point.length>1){
        this.setCenter(new SuperMap.LonLat(point[0], point[1]), zoom)
      }
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
          let rem = new SuperMap.Pixel(e.xy.x,e.xy.y)
          let lonlat = this.getLonLatFromPixel(rem)
          let lnglat = {lng:lonlat.lon,lat:lonlat.lat}
          handler(lnglat)
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
        imageDiv.childNodes[0].style.width = opts.size.width + 'rem'
        imageDiv.childNodes[0].style.height = opts.size.height + 'rem'
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
      imgdiv.style.width = icon.size.width + 'rem'
      imgdiv.style.height = icon.size.height + 'rem'
      let size = new SuperMap.Size(icon.size.width, icon.size.height)
      this.icon.size = size
      this.icon.offset = new SuperMap.Pixel(-(size.w / 2), -size.h)
    },
    _setLabel: function (label) {
      let imgdiv = this.icon.imageDiv
      let left = label.offset.x + 'rem' || '0'
      let top = label.offset.y + 'rem' || '35rem'
      let labelContent = document.createElement('div')
      labelContent.className = 'sm-marker-label'
      labelContent.style.left = left
      labelContent.style.top = top
      labelContent.style.position = 'relative'
      labelContent.style.color = label.color || '#000'
      labelContent.style.minWidth = '100rem'
      labelContent.style.fontSize = label.fontSize || '12rem'
      labelContent.style.textAlign = label.direction || 'center'
      labelContent.style.backgroundColor = '#fff'
      labelContent.style.boxShadow = '0 2rem 6rem 0 rgba(114, 124, 245, .5)'
      labelContent.style.padding = '.75rem 1.25rem'
      labelContent.style.float = 'left'
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
    this.visible = true
    f.geometry = new SuperMap.Geometry.Point(this.position.lon, this.position.lat)
    f.style = {
      pointRadius: 4,
      graphic: true,
      externalGraphic: opts.icon || opts.extData.icon || '../../map/images/defaultPoint.png',
      graphicWidth: opts.size.width,
      graphicHeight: opts.size.height,
      graphicTitle: this.title,
      graphicXOffset: (opts.offset && opts.offset.x) || 0,
      graphicYOffset: (opts.offset && opts.offset.y) || 0
    }
    this.info = opts.extData
    this.feature = f;
    return $.extend(f, this)
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
      clusterLayer.redraw();
    },
    _getIcon: function () {
      return this.icon
    },
    _setIcon: function (url) {
      if (url) {
        this.icon = url
      }
      this.style.externalGraphic = url;
      clusterLayer.redraw();
    },
    setzIndex: function (zIndex) {
      this.style.graphicZIndex = zIndex
    },
    _setBigIcon: function (icon) {
      // let imgdiv = this.icon.imageDiv.childNodes[0]
      // imgdiv.style.width = icon.size.width + 'rem'
      // imgdiv.style.height = icon.size.height + 'rem'
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
      // this.style.fontWeight
      this.style.fontSize = 14
      this.style.fontColor = label.fontColor || "#0000ff"
    },
    _setMap: function (map) {
      // clusterLayer.destroyCluster();
    },
    _show: function () {
      // this.style.graphic = true;
      this.style.display = 'block'
      clusterLayer.redraw();
    },
    _hide: function () {
      // this.style.graphic = false;
      this.style.display = 'none'
      clusterLayer.redraw();
    },
    _add: function () {
      try {
        clusterLayer.addFeatures(this);
        clusterLayer.redraw();
      } catch (e) {
        console.log(e)
      }
    },
    _remove: function () {
      try {
        clusterLayer.removeFeatures(this);
        clusterLayer.redraw();
      } catch (e) {
        console.log(e)
      }
    },
    _getMap: function () {
      return this.map
    },
    _addEventListener: function(event, handler) {
      // marker事件绑定到clusterLayer上
      if(event === 'click'){
        event = 'featureselected'
      }
      if(clusterLayer.events.listeners.featureselected < 1) {
        clusterLayer.events.on({
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
          fontSize: '12rem',
          graphic: true,
          externalGraphic: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABNCAYAAAAIPlKzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDYuMC1jMDAyIDExNi4xNjQ2NTUsIDIwMjEvMDEvMjYtMTU6NDE6MjAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCAyMS4yIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJGRDQ0MDc5OTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJGRDQ0MDdBOTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkZENDQwNzc5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkZENDQwNzg5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4G7SIHAAASa0lEQVR42txc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', // require('./images/m1.png'),// ../../map/images/m1.png
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
          fontSize: '12rem',
          graphic: true,
          externalGraphic: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABNCAYAAAAIPlKzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDYuMC1jMDAyIDExNi4xNjQ2NTUsIDIwMjEvMDEvMjYtMTU6NDE6MjAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCAyMS4yIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJGRDQ0MDc5OTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJGRDQ0MDdBOTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkZENDQwNzc5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkZENDQwNzg5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4G7SIHAAASa0lEQVR42txc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', //require('./images/m2.png'),
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
          fontSize: '12rem',
          graphic: true,
          externalGraphic: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABNCAYAAAAIPlKzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDYuMC1jMDAyIDExNi4xNjQ2NTUsIDIwMjEvMDEvMjYtMTU6NDE6MjAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCAyMS4yIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJGRDQ0MDc5OTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJGRDQ0MDdBOTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkZENDQwNzc5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkZENDQwNzg5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4G7SIHAAASa0lEQVR42txc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', //  require('./images/m3.png'),
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
          fontSize: '12rem',
          graphic: true,
          externalGraphic: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABNCAYAAAAIPlKzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDYuMC1jMDAyIDExNi4xNjQ2NTUsIDIwMjEvMDEvMjYtMTU6NDE6MjAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCAyMS4yIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJGRDQ0MDc5OTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJGRDQ0MDdBOTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkZENDQwNzc5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkZENDQwNzg5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4G7SIHAAASa0lEQVR42txc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', // require('./images/m4.png'),
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
          fontSize: '12rem',
          graphic: true,
          externalGraphic: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABNCAYAAAAIPlKzAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDYuMC1jMDAyIDExNi4xNjQ2NTUsIDIwMjEvMDEvMjYtMTU6NDE6MjAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCAyMS4yIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkJGRDQ0MDc5OTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkJGRDQ0MDdBOTM4MzExRUNBQ0YzQjlBRDI1NDdGRTREIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6QkZENDQwNzc5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6QkZENDQwNzg5MzgzMTFFQ0FDRjNCOUFEMjU0N0ZFNEQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4G7SIHAAASa0lEQVR42txc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', // require('./images/m5.png'),
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
                fontSize: '12rem',
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
                        fontSize: '12rem',
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
                fontSize: '12rem',
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
      }
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
      }
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
      }
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
      }
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
      }
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
      }
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
      }
      let drawBox = new SuperMap.Control.DrawFeature(drawVectorLayer, SuperMap.Handler.Box, {
        handlerOptions: {
          cursorCSS: 'crosshair'
        }
      })
      map.addControl(drawBox)
      drawBox.events.on({
        featureadded: (drawBoundsArgs) => {
          let feature = drawBoundsArgs.feature
          let bounds = feature.geometry.bounds;
          let resultMarkers = [];
          feature.style = style
          drawVectorLayer.addFeatures(feature)
          drawBox.deactivate()
          for (let i = 0; i < markers.length; i++) {
            let pt = markers[i]._getPosition()
            if (bounds.contains(pt.lng, pt.lat)) {
              resultMarkers.push(markers[i])
            }
          }
          map.removeLayer(drawVectorLayer)
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
      }
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
    const mapBegin = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADkAAABBCAYAAABraQwpAAAUZElEQVRoQ8VbB1hTd/d+M0hYCTMQFDAoYIsiCo5WqlVLta66PqGuUqSO4ipqHR2OthatVsWqdXyoIBa3qGhVWrStf6vQVtGiFZQlsgIICTPz//xucuMlBgiuL89jgyW59773fc857znnysLLebFaOY32RV9Cayd/2nPTx2Rt2bLFYtq0aa9wuVwfCwsLMYfDsQFgDUCpVqurlUplqUqlytm/f/+/8+fPVwKgAT9X4M8LJH0ctlQq7SkUCkdzOJy3ORxOLwBWZtytBrVafV2tVqfKZLLTIpHoBgCN/nvPDPhZQZLvs86ePWs7ZMiQ93k83iwWi9WdBqXValGlLEV5YyGqlRVoVNdBoWmCBZsPS7YN7HkuEPE7wpnnBhbr8aVotdpbCoViV1paWsKIESNq9Qw/NdinBUmB27lzp2V4ePhHfD5/GQBnAq5OJUOW7DLuyjOQW3cTdSo5pUGNVtvsndwAQhV5t+TYwts2AN2EfRHoMBC2XDvqPmm12kqFQhETHx//w6xZsxqfFuzTgCTfYVdWVg5xcHDYzmKxvMkFFdVn43LlUWTJrkClURoAtAVQoyWBqAX9zgIXPe3fwDBxKLrY+tFgc2QyWZS9vf1FvYzbxWp7QbJXrVrFW7Zs2Ro+n/8xAStteoALZXG4LbtKMcVkqL0ACVANAUyOo9Gih31/TPL8CB2tOhGwGoVCsWHWrFkr9+3bp2DEbJshby5Iir3ffvvNsX///oc4HM5gjVaN3yuO4JL0ABQa5XMHSIASiXNYXIztGI6xHadSP6vV6rRLly6FhoSEVJvLqjkgKYA3btwQ+/v7n2Oz2d1rVY9wpGgtcusy9TJ7vgzSALV6Zsm7r8Afi7p+DQeeE1HLzRs3bgwPDAwsMwdoWyApgCdPnnQeNWrUL2w2u1tFUxH2F36BKkXJSwOok7AWTjwXrPDbDA9rCTQazc2UlJShY8aMqWgLaFsgOYsXL7Zcu3ZtCofDGUQA7s1fApmq6qUDpGNdyLHHmh7b4WntRaR7KSoqauSuXbuaAKhbCs7WQLIBcBsbGzfw+fx5ctUj7M5dgEfK8v8ZQI1Gl5QceSLE9toHR54TGhoatlpbWy/Ug6QNRDO8LYEk/59TWlo63NXVNVmtVbH35H+Cwvo7/3OARLYkRrsKuuO7gB3gsi00ZWVlY8Ri8Tk90CfKiymQVBzGxMQIlyxZ8jebzZb8XB6rem6VJbQLks21gxbFFo7oRdWoZVBqVof4x6yELbPA5NlBrNahX10GlUVMXTgOgY5CWKM2g4feUsdDiPY8IRHaOIokof926dYHLly+XmYpPUyApmcrl8i9sbW0/L264h525C6DSqtssE92FwfC3C0ZR4338VXURjxQVzQo9XQedLMTo4zgYVlxbpFdewv3au+0GSI5FbtaOoP3wFviitrb2a4FA8BUAlXENNQZJyTQuLs4lIiLiX7AgiMtbgry6W20CJBp5120m3nINRbY8EwcfxKK4Ia8Zk3Shf8PpHUz2nAtLjjUSC7bhbMlhA5M6tnSx1xKD1M3S28QedoGIDdxJbFN1fHz8qxERESTbkiRkkK0xSMKihVwuX2pra7v6Xu117M1fbhZADouHsR1mYaBoDK5UnMPRoh9Qq5IZmKQBkuz4nsccBIuGIrf2X+zNj8Vd2S0ILRwp+VYrK80GSICSm7EpYDv6OPWDTCZbaWdnt460ckw2mSApFkUikWVpaek/bDa7U3zBCtyVp5tl1UiMTeg4B685DUNKcTxOFe97AiCxaq85heA9zygIuEIcK4qnWAx0CEaQQzAl259KT6BeVacDqk8yOtZ0/pZp9GnT8JpTMDb2/J7UzoIOHTp0Kysro0sKxSYTJBWLd+/eHeDr6/tztaIc67PDodFqWjXbFiw+RHwPSGz80McxBJ1t/HCt8hdcrUoFj22FiqZSFNbfR5O6EU58N0z2nIM+jgNxW3YDe3I3orihCJGdF+It11EoayzGf3NjcaXyV7MBEqAssHAi+AzElmLk5OS87evr+xszNpkgOQB4NTU13wiFwo//r+IEUkp2tNlNdLYNwFDXSfCw8qFizILNo9otuaoGPBYfmTVXcfJhPCoUUrztOh7/cY+k5H/kQRzOlhyFGhoE2remDdK8FlJP5XfoLdt/fjHIFqcetM0i3aoTh+T4fY5oknEh2k52d3WcAiImnDIJhVEFYJCCVSuUVLpfb4795S5FTe4OSamvdRDfhaxjmOgUdrbzA51hBrVWjXiXXNcYcK2RWX8Wu3LVw4XdEuGQBJDa++KPiIvbkb0alQkodH2BjonsEwjw/gFwrem778HThbcqJViTIBkivs7dAXO3rvglKpzOTxeMF6kCTTapkgLaKiohy3bdtWpNaqOCuzxlFdfFvtkg3XAWJ+J7zpMgZBDm+iqD4XaeUn0dnWD284D8PF8hRcqUjFOPcP4CfshUeKShwv2o+bNRlw5olhyxXChitEgH1vBDm+Di6Li6sVv2P7vY142FhkMgaNARImeWweLg+5Ag6Lo5wyZYpHUlLSI30CMoAkUrVIT0/v16dPn0uljfnYmD2zTYB0gddqWRjnPhPviN/DZek57CvYgGGuoZjoMRO/SX+i2B0kGgEWi02BLKzPBQdcOPGdIeDawZprAzaLXILuJVfKsCdvB86UJEOhUVBA6SRjCiCdlI72PwofgQ8yMjIG9e3b95oepJpmkorHvLy8UIlEsu+27A/szV/Z6siC6WCceGKEesxFL4dgnC1JwsHCHZjkOQfD3UJxrfISLFg8BDr2pwAQwCSZKTSNqFFWQ6asod6rFVVQalUgdc/TRoI/q9Kx6/4W/Cu/YxZAciM29dyIEHEI8vPzP/Dy8jpMxyUNksQjv6SkZK5YLF57peI0jj3c0uJMxtiivSLsTdU+F8sOOFS4ExelremEhWYz+ziE49fAA7sgy4S3ohgZ1A2X5mtRN1OynRllDgaxWVVM/E3sX5vE+RnUYj/Sqqzj64EfcJW6IMQ8yHpfQZYUw/NmryzG50ySUlZV9KhaLtwAgpURFDaT0SYdfVla2xMXF5YuL5YdxumS3gUnmSMP4JGxwMchlLCa4z0CVQop9eRspPzrdazE1tkjI34qfSo9RFowMqLgsPmVGNFTiZ0FLnV73s0Kjgpd1FzjyRbhWeQUPG4qgJMzTYxWjeRATILnG+T7zMNt7JiorK79xdnaO0YNUMkFaSqXSz52dnZdcKDuAsyV7n3A6pu6iDdcOo93C8bZ4PG7X/I2duTHwFQRgutdCVDaVIy5vMzJr0hHsFIIBzkPBZ/P1wAyDS73/YkGhVqBJ04Sfy84jTZpqNoM6ErSY5z0Xc3xmQyqVrnNxcVkDgEz4moMsKSlZLBaLV1wsP4Lk4p3NnI5JmUALBwsXjO/4Id4QDUNFUxkulB6Hm6UnBruOxGVpKvbkbcEjZQWme0VjdIdQ/TEJj81fukLCpuavRwqTsDlnA0hLQAFog0Ha7y55ZTEiO0egtLT0Szc3tw3GIC1ITObl5UVKJJJN6VWpSCxY27pM9FM1HizhZxeECe6R8LTuQsUYGXLZ8xxxrCgBBwp3UoyEd5qDce5TcOPRn7ha+btOJeS/VIulmxH1dx6IIMc++LEgAd/fi9W5LTMBkmOs6/ENxruPIYkn2svLK04vV0UzuZLhUEBAwKF7tTexOSe65bvIGBvqLhDwt++HMPcZ8LL11ZeBGuzN+x6/SM9QQMIlczHefQoOFsZjT942k4V+htdcTPOKwAECMmcz1AzHw/SuhhhldixaLX7sF49+zn2QmZkZ1rNnz59MyZW/e/furh9++OGftaoaLL05vpmtMpzECCBlvfRsDHUdi1CPGRBYCKlad/xhIk4+TEKTRonremnMxwX0qjj5IxK7cLRQAptkmyWd2lwWY3GkaEgsSsCVn8xNMNsuyRgCJMv4MuQxHviN27doVNGvWrGxm4iF3npQQSwA2arX6NpvNdlyZNQ1llON4POFmDn6fHBuyMFQ8FmF6kLqpegHi83/A5cqLiJTMw388puJwYQJ25n5vwskAUV3mY6okHIn5CYjN2dSMybYAelh54Nch50knUsXhcF4FUK9nkiohNEgeWavJZLIEgUAw/EDBJvwqPWWyXTI1F+1s+yoivaLhI/CDtKmMkqjI0hW5tTmIy92KAPsghHq+j5SHxxGX9wPlZEj5IAyQPpK8z+w8BxM9wrA/PwGbszeB9D/GZaJZU23w1cAkz4mI6bEacrn8J6FQ+L4eJDHpBpCU4yFrtuzs7EgfH59vs2oysDnnE91JWpSoTqrEfE/uNBuvO72JR8oqnCj6kbo5RJ5kGHykMJEqDVMlkXhQV4A78iwoNUp9ndS18CTx+Nl1RxfbLkjIS8DmnI0GSbcUgzpfrVPavj47Mdh1IGm1PvH19d0DoMHY8VATAQJy6dKlHjExMX9poLH4JDOUSv/0bsIUg2JLD4xwC8Ugl3eoNut86Ukk5JMWTYuJ7uHoaOmBQw8S0McpGFM7TW9zb0E+kJAXj43ZRK6Pe9mWGCQARTwRroakUeZ80aJFQZs2bXqgB0lNCJp1IaSMEMnW1NTsEwqF75x8uA/Hi/dSyxdTAMmJXxX2RLhkHrxtX8H1R+mIy92C+/U5FMMivhvFFnEuUV0WYoLHJNypycK1qj+o3+uaOF12Jj+97vw6utl1R2J+Ir69u14Xk/pCb5j7MCRKJ8OFvnMQ3XUuampqztnb23+glyqxdNT2mgmSlqx1alrakJDBgw+RGc2iG++hTl2rSxQmxob2Fk54UzQCEusuOFV8GLflt3SfY6R/YulGuI3DYJe3ca7kDE6XJJuswe92GINRbqNxrvQcDj84Sk0IW2OQSNWWa4Mrb6XCgeeA1NTU0KFDh5L1Hkk6dNNsAEluKJEsiUuSZa3r6upOWFtb9z1dfABJhTtanIuSrzlaiMBh81BCe02jjp6UBxFfDCeeCKWNZShtInuUJ52MK18MEd8VZY3lKG4s0Y1BGW0WMwbremLGsazTm+c5CfX19uo2NzThGVjWs94wHWVQ3QkAmJycHv/vuu8dUWiVr+a1IPKjPe+qxobn9YFtlwric+dh0wYU3T4DH4WlSUlImjB49+gpDqtRUwHiQRf5OS5Zis7y8fKNIJAolo8PPb30EhVbRXD50rJo5VTO3m2hLouT3ZIJwKjgJAQ7+xJAfcnFxWWTEomEBZGq4bGBz4sSJromJiWd5PJ77hdJk7Mj9tl2D3xfFIIn5GP+VCPeaBJVKVRQaGjr8xIkT5aZYNMUkzSYpJxSbSUlJ/cLCwpJYLBafeNHk4h/Nmmy/SIAzO3+AVd2XkRuuOHjwYNjkyZPTGSySjNpsjdfSwsdgDgjQjIyMCb179/5Oq9Wyd+duwsniw+2ai7anXTKUKkahp5MMYXC6ZCq+8v+MtGSajIyMhX379j2uB2go/swVQUtM0pmWli3FaFZWVoSfn98KLbSsw4Xx1NhQZ8danmw/zxhkaVlY8srHmOc7k9Re7T///POlv7//XgaD1KjD1AMTre0naRdE2T3y5/r165MDAgJWs1gsbkbVH/j231WoUpCt8+PZrDlTtfZmUQcLe8T2Wou3XAcRiapv3bq1MiAg4IDe1dAM0vsPs/aTtPWidiN6u0fKCmHU8syZM4OHDRv2HYfDcahokmLN7c9xvfovs8eG7QXY1yEI24O+g5uVmKzPq8+frem89cuTIS/oOg4w3aGfTbJPF9I9tPTNA2KQZpYHyo6OjPVevXr1RIBD0JiPGhPw47M3brXco+idB2tHRP1bC47aOGIi53jOxqOs8cNkc1NXV/b169eqF69evz9cDYwLU7YdaeLUFko5Pwii1RtCbBUtnZ2era9eufdy5c+eZ5Eb8VZWBlVmfQdokbdfIwhRAZ54TYgremYaCITPuhKSgoiOvfv//G4uJiukck7FFtVGvPCjAlaU5nQDNKA6XtH+/UqVMDhw8fvp7L5TqT+FyauQjXq683b7aZc1MTHT3Tybzh/Bpie62Hi6UzqYFVFy5cWDpy5EjiRwkwwh4BRwNslcH2gqQzMbXe08cpzSo/KiqqQ0xMzAahUPg6eU5gS04s9hckmN3wkrRF1gaLu87HbO9IsFls0vxe+/TTTxdt3br1oR4gzR5dB1uMQWPWzJGrcQwToMby5QsEAsubN29Gd+rUiTwOykopTsGqrNVoUDe12S65W3XE94EbEOjQk8qe9+7d2x4UFLRNLpeTzEnAmZKn2Q8RthckzSj1hAgjTmn58i9dujRiwIABa9lstnVmdSbm/r0A0qaKFruJkW7vYG2PLyG0EEChUJQkJycvCgsLI8saIkmmPAlzNHtmA2zNDJgbp/SKgdhAQ1Lavn27f2Rk5HYej9exuKEYUX/NR5aMPANE11PAks3Him7LManTRKqxrqqq+nnGjBmfHj9+XNqCPM2KP1MX/jRMtiVfUk/506dP7xAbG/uDra1tQL2qHtHXlyC1PI2KU1+BN7b2+g6+Qh8yXWvKyspa16NHj0Q9c7Q0jbNnu9hrT500h1F6QP1EnPr5+QnT0tLWuLq6jiEWcP0dslgtxfqAr2HJsURjY2Pu3r17o6Oiov4xYq9d2bOti3xWJpnHZ5YZau1Au6Ts7Ow53t7e87TQcsizsZYcPkpKSo6NHTv2q/T0dPLcqnFyoT3oU7P3vJk0JV+6zBhcUl5e3icSiWQ2+XBVVdVZJyenBQxwzOxJx95zAfisiac1F0WXGQOjAwYMsE9LS7vIZrPtVqxYEbJmzRpiz5jWjGavRXvWliyfxdY9zbGZiyQaKP/evXsfCoVCCVn0MkoEKe7PVZ7PagbaC7iZQxo6dCj59yPCZcuWlehBvnCAL0quxjeCZF0689KPeBBJEvZeKINP413byyLzHHTmJe/kRScXanjwtAc293vPs4S0dk46Runz6RbNLwHgy5KruTf8hX3uZTH5wgCYc+D/B+F6UzIOAeS/AAAAAElFTkSuQmCC'
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

  superMap.Polyline.prototype = {
    _remove: function () {
      map.removeLayer(this)
    },
    _setPath: function (lineArrR, opts) {
      map.removeLayer(this)
      this.removeAllFeatures();
      this.refresh();
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
      // let vector= new SuperMap.Layer.Vector("vector");
      this.addFeatures([linecVector])
      map.addLayer(this)
    },
    _setDirectlineMap: function (map) {

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
      strokeWeight: opts.strokeWeight||3, // 线宽
      strokeStyle: opts.strokeStyle||'solid', // 线样式
      isFresh: opts.isFresh||false,
      fillColor: opts.fillColor || '#C6E2FF',
      fillOpacity: opts.fillOpacity || 0.5
    };
    this.opts = opts;
    this.polygon = regionVector;
    this.geometry = regionVector.geometry;
    this.extData = opts.extData;
    // let vector = new SuperMap.Layer.Vector("vector");
    vector.addFeatures([regionVector])
    // map.addLayers([vector])
    return $.extend(vector, this)
  };

  superMap.Polygon.prototype = {
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
    this.opts = opts
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
      let position = pt._getPosition()
      let currentDistance = superMap.gmUtil.distance([this.opts.center.lng, this.opts.center.lat], [position.lng, position.lat])
      return (currentDistance * 1000) < this.opts.radius
    },
    _getOpts: function () {
      return this.opts
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
    let radius = opts.radius/111000 || 1 // 半径单位度
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

    let vector = new SuperMap.Layer.Vector("vector");
    vector.addFeatures([regionVector])
    // map.addLayers([vector])
    this.geometry = regionVector.geometry;
    return $.extend(vector, this)
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
      width:opts.width||'200rem',
      height:opts.height||"200rem",
      color:opts.color||"#000",
      fontSize:opts.fontSize||'12rem'
    }
      // let contentHTML = `<div style='width:80rem; font-size: ${infoWindowOptions.fontSize};font-weight:bold ; opacity: 0.8;color: ${infoWindowOptions.color};'>` ;
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
    // heatMapLayer = new SuperMap.Layer.HeatMapFastLayer(
    //   "heatMap",
    //   {
    //     "featureWeight": "value"
    //   }
    // );
    heatMapLayer.addFeatures(heatPoints);
    // map.addLayer(heatMapLayer)
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
        self.menuElement.style.left = event.clientX + "rem";
        self.menuElement.style.top = event.clientY + "rem";
        self.menuElement.style.visibility = "visible";
      });
    },
    _open: function (map, e) {
      // marker右键菜单依靠此方法调用
      var lonlat = this.element.getLonLat();
      var p = map.getPixelFromLonLat(lonlat);
      this.menuElement.style.left = p.x + "rem";
      this.menuElement.style.top = p.y + 9 + "rem";
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

  // window.superMap = superMap
  return superMap
}
export default znvSuperMap
