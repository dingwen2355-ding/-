import area from './area'
import areaname from './areaname'

const minemap = window.minemap || {}
const turf = window.turf

class Map {
  constructor(mapId, opts) {
    opts = Object.assign(
      {
        zoom: 12,
        minZoom: 1,
        maxZoom: 17,
        center: [120.422167, 36.117018]
      },
      opts
    )
    // 创建地图
    let map = new minemap.Map({
      container: mapId,
      style: '//2.22.131.158:21009/service/solu/style/id/222530', // 底图样式https://service.minedata.cn/map/solu/style/11001  现场环境：2.22.131.158:21009/service/solu/style/id/222530
      center: opts.center, // 地图中心点
      zoom: opts.zoom, // 地图默认缩放等级
      pitch: 0, // 地图俯仰角度
      maxZoom: opts.maxZoom, // 地图最大缩放等级
      minZoom: opts.minZoom, // 地图最小缩放等级
      projection: 'LATLON' // 互联网：MERCATOR 现场：LATLON'
    })
    this.map = map
    this.clusterData = {
      type: 'FeatureCollection',
      features: []
    }
    const promise = new Promise((resolve, reject) => {
      map.on('load', () => {
        resolve(this)
      })
    })
    return promise
  }

  _on(event, handler) {
    this.map.on(event, handler)
  }

  _addEditControl() {
    this._delEditControl()
    if (this.map) {
      this.edit = new minemap.edit.init(this.map, {
        boxSelect: true /* 是否支持拉框选择 */,
        touchEnabled: true /* 是否支持手指触屏 */,
        displayControlsDefault: true /* 是否启用编辑控件 */,
        showButtons: false /* 是否启用默认控件按钮 */,
        drawEnabled: false,
        userStyles: {
          inactive: {
            fillColor: '#55B1F3',
            fillOpacity: 0.5,
            fillOutlineColor: '#55B1F3',
            fillOutlineWidth: 2,
            lineColor: '#55B1F3',
            lineWidth: 2,
            circleColor: '#55B1F3',
            circleRadius: 4,
            circleBorderColor: '#ffffff',
            circleBorderRadius: 2
          },
          active: {
            fillColor: '#F05668',
            fillOpacity: 0.1,
            fillOutlineColor: '#F05668',
            fillOutlineWidth: 2,
            lineColor: '#F05668',
            lineWidth: 2,
            circleColor: '#F05668',
            circleRadius: 6,
            circleBorderColor: '#ffffff',
            circleBorderRadius: 2
          },
          static: {
            fillColor: '#404040',
            fillOpacity: 0.1,
            fillOutlineColor: '#404040',
            fillOutlineWidth: 2,
            lineColor: '#404040',
            lineWidth: 2,
            circleColor: '#404040',
            circleRadius: 4
          }
        }
      })
    }
  }

  _enableOrDisableDraw(isEdit) {
    if (isEdit) {
      this.edit.enableDraw()
    } else {
      this.edit.disableDraw()
    }
  }

  _delEditControl() {
    if (this.map && this.edit) {
      this.edit.dispose()
      this.edit = null
    }
  }

  _onBtnCtrlActive(mode) {
    if (this.map && mode) {
      this.edit.onBtnCtrlActive(mode)
    }
  }

  _onEditEnd(handler) {
    // 绑定编辑操作记录新增事件
    this.map.on('edit.record.create', (e) => {
      console.log('eee:', e.record)
      // record结果说明如下：
      // {
      //     type:0,/*操作类型：0-无、1-删除、2-修改、3-新增、4-替换*/
      //     action:0,/*更新操作行为：0-无、1-图形移动、2-更改图形形状点、3-更改properties*/
      //     features:[],/*本次操作后的features*/
      //     prevFeatures[] /*本次操作前的features*/
      // }
      let record = e.record
      if (record.type === 1 || record.type === 2 || record.type === 3) {
        handler(record.features[0], record.type)
      }
    })
  }

  _flyTo(opts) {
    this.map.setCenter(opts.center)
    this.map.setZoom(opts.zoom)
  }
}

class Polygon {
  constructor(map, opts) {
    let colors = [['320211004', 'rgba(50, 180, 255, 1)', '蠡湖街道'],
    ['320211003', 'rgba(0, 240, 255, 1)', '蠡园街道'],
    ['320211006', 'rgba(160, 150, 255, 1)', '河埒街道'],
    ['320211001', 'rgba(167, 109, 255, 1)', '胡埭镇'],
    ['320211101', 'rgba(59, 255, 184, 1)', '马山街道'],
    ['320211009', 'rgba(255, 100, 235, 1)', '荣巷街道'],
    ['320211002', 'rgba(117, 255, 62, 1)', '雪浪街道']]
    map.addSource('fillSource', {
      type: 'geojson',
      data: area
    })
    map.addSource('nameSource', {
      type: 'geojson',
      data: areaname
    })
    map.addLayer({
      id: 'lineLayer',
      type: 'line',
      source: 'fillSource',
      layout: {
        'line-join': 'round',
        'line-cap': 'round',
        'border-visibility': 'visible' // 是否开启线边框
      },
      paint: {
        'line-width': 3,
        'line-color': {
          type: 'categorical',
          property: 'kind',
          stops: [[1, 'rgba(255,255,255,1)'], [2, 'rgba(255,255,255,1)']],
          default: 'rgba(255,255,255,1)'
        },
        'line-border-width': 2, // 设置线边框宽度
        'line-border-opacity': 1, // 设置线边框透明度
        'line-border-color': {
          stops: [[1, 'rgba(255,255,255,1)'], [2, 'rgba(255,255,255,0)']]
        } // 设置线边框颜色
      },
      minzoom: 7,
      maxzoom: 17.5
    })
    map.addLayer({
      id: 'symbolLayer',
      type: 'symbol',
      source: 'nameSource',
      layout: {
        'text-field': '{name}',
        'icon-image': '背景',
        'text-offset': [-0.5, 0],
        'icon-allow-overlap': true,
        'text-allow-overlap': true,
        'icon-size': 0.5  // 设置图标大小为原始大小的 1.5 倍
      },
      paint: {
        'text-color': 'rgba(255,255,255,1)'
      },
      minzoom: 7,
      maxzoom: 17.5
    })

    this.opts = opts
    return this
  }
}

class Marker {
  constructor(map, opts) {
    var el = document.createElement('div')
    el.id = 'marker'
    el.style['background-image'] = `url(${opts.icon || 'https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png'})`
    el.style['background-size'] = 'cover'
    el.style.width = opts.size.width * 5 + 'rem'
    el.style.height = opts.size.height * 5 + 'rem'
    el.title = opts.title

    // Marker构造函数接收两个参数，一个为自定义的DOM元素，一个是Object参数，其中包括偏移量等
    // offset参数为标注点相对于其左上角偏移像素大小
    // 调用setLngLat方法指定Marker的坐标位置
    var Marker = new minemap.Marker(el, { offset: [opts.offset.x, opts.offset.y] }).setLngLat([
      opts.position.lng,
      opts.position.lat
    ])
    this.Marker = Marker
    this.el = el
    this.opts = opts
    return this
  }

  _getPosition() {
    return [this.opts.position.lng, this.opts.position.lat]
  }

  _addToMap(map) {
    this.Marker.addTo(map)
  }

  _removeFromMap() {
    this.Marker.remove()
  }

  _on(event, handler) {
    this.el.addEventListener(event, function (e) {
      handler(this.opts)
      e.stopPropagation()
    })
  }
}

class MarkerDynamic {
  constructor(map, opts) {
    var jsonData = {
      type: 'FeatureCollection',
      features: [
        {
          type: 'Feature',
          geometry: {
            type: 'Point',
            coordinates: [opts.position.lng, opts.position.lat]
          },
          properties: opts
        }
      ]
    }
    map.addSource('pointSource', {
      type: 'geojson',
      data: jsonData
    })
    map.addLayer({
      id: 'event-marker',
      type: 'symbol',
      source: 'pointSource',
      layout: {
        visibility: 'visible',
        'icon-image': 'pulsing-dot',
        'icon-allow-overlap': true, // 图标允许压盖
        'text-allow-overlap': true // 图标覆盖文字允许压盖
      },
      paint: {
      },
      minzoom: 7,
      maxzoom: 17
    })
    this.opts = opts
    return this
  }
}

class Cluster {
  constructor(map, opts) {
    map.addSource('data-point', {
      type: 'geojson',
      data: '//2.22.131.158:21009/support/static/api/demo/js-api/zh/assets/poi_suzhou.json',
      cluster: true,
      clusterMaxZoom: 17, /* 最大聚合层级 */
      clusterRadius: 50 /* 聚合半径 */
    })

    // 添加非聚合图层
    map.addLayer({
      id: 'unclustered-points',
      type: 'symbol',
      source: 'data-point',
      filter: ['!has', 'point_count'],
      layout: {
        'icon-image': '{icon}',
        'icon-size': 0.4,
        'icon-allow-overlap': true,
        'text-allow-overlap': true
      }
    })

    // 添加聚合图层
    var outerColors = [[1000, 'rgba(253, 156, 115, 0.6)'], [100, 'rgba(241, 211, 87, 0.6)'], [0, 'rgba(181, 226, 140, 0.6)']]

    outerColors.forEach(function (color, i) {
      map.addLayer({
        id: 'point-outer-cluster-' + i,
        type: 'circle',
        source: 'data-point',
        paint: {
          'circle-color': color[1],
          'circle-radius': 20
        },
        filter: i === 0
          ? ['>=', 'point_count', color[0]]
          : ['all', ['>=', 'point_count', color[0]], ['<', 'point_count', outerColors[i - 1][0]]]
      })
    })
    var innerColors = [[1000, 'rgba(241, 128, 23, 0.6)'], [100, 'rgba(240, 194, 12, 0.6)'], [0, 'rgba(110, 204, 57, 0.6)']]

    innerColors.forEach(function (color, i) {
      map.addLayer({
        id: 'point-inner-cluster-' + i,
        type: 'circle',
        source: 'data-point',
        paint: {
          'circle-color': color[1],
          'circle-radius': 15
        },
        filter: i === 0
          ? ['>=', 'point_count', color[0]]
          : ['all', ['>=', 'point_count', color[0]], ['<', 'point_count', innerColors[i - 1][0]]]
      })
    })
    // 添加数量图层
    map.addLayer({
      id: 'cluster-count',
      type: 'symbol',
      source: 'data-point',
      layout: {
        'text-field': '{point_count}',
        'text-size': 10
      },
      paint: {
        'text-color': 'rgba(0,0,0,.75)'
      },
      filter: ['has', 'point_count']
    })
    return this
  }
}

class Circle {
  constructor(opts, map) {
    var center = opts.center
    var radius = opts.radiu / 1000
    var options = { steps: 64, units: 'kilometers', properties: { foo: 'bar' } }
    var cirlceData = turf.circle(center, radius, options)
    // 增加自定义数据源、自定义图层
    map.addSource(opts.id + 'Source', {
      type: 'geojson',
      data: cirlceData
    })

    map.addLayer({
      id: opts.id + 'Layer',
      type: 'fill',
      source: opts.id + 'Source',
      layout: {},
      paint: {
        'fill-color': 'rgb(255, 185, 181)',
        'fill-opacity': 0.2,
        'fill-outline-color': 'rgb(255, 185, 181)'
      }
    })
    this.polygonData = cirlceData
    this.opts = opts
    return this
  }

  _remove(map) {
    map.removeSource(this.opts.id + 'Source')
    map.removeLayer(this.opts.id + 'Layer')
  }

  _contain(markers) {
    let Util = new ZMap.Util()
    return markers.filter((e) => {
      return Util.isPointInPolygon(e, this.polygonData)
    })
  }
}

class Util {
  /**
   * 计算一个点是否在多边形里
   * @param {Object} pt 标注点
   * @param {Object} poly 多边形数组
   */
  isPointInPolygon(pt, poly) {
    return turf.booleanPointInPolygon(turf.point(pt._getPosition()), poly)
  }

  createEventIcon(map) {
    var size = 200

    // 定义一个CustomLayerInterface来在地图上绘制脉冲点
    var pulsingDot = {
      width: size,
      height: size,
      data: new Uint8Array(size * size * 4),

      // 在图层添加到地图时获取获取canvas context
      onAdd: function () {
        var canvas = document.createElement('canvas')
        canvas.width = this.width
        canvas.height = this.height
        this.context = canvas.getContext('2d')
      },

      // 每帧渲染时调用
      render: function () {
        var duration = 1000
        var t = (performance.now() % duration) / duration

        var radius = (size / 2) * 0.2
        var outerRadius = (size / 2) * 0.5 * t + radius
        var context = this.context

        // 画外围圆
        context.clearRect(0, 0, this.width, this.height)
        context.beginPath()
        context.arc(
          this.width / 2,
          this.height / 2,
          outerRadius,
          0,
          Math.PI * 2
        )
        context.fillStyle = 'rgba(255, 200, 200,' + (1 - t) + ')'
        context.fill()

        // 画内圈圆
        context.beginPath()
        context.arc(
          this.width / 2,
          this.height / 2,
          radius,
          0,
          Math.PI * 2
        )
        context.fillStyle = 'rgba(255, 100, 100, 1)'
        context.strokeStyle = 'white'
        context.lineWidth = 2 + 4 * (1 - t)
        context.fill()
        context.stroke()

        // 从canvas中更新图片数据
        this.data = context.getImageData(
          0,
          0,
          this.width,
          this.height
        ).data

        // 不断地重新绘制地图，导致平滑的动画点
        map.triggerRepaint()

        // 返回`true`让映射知道图像已经更新
        return true
      }
    }

    map.addImage('pulsing-dot', pulsingDot, {
      pixelRatio: 2
    })
  }
}

let ZMap = { Map, Marker, MarkerDynamic, Cluster, Circle, Util, Polygon }
export default ZMap
