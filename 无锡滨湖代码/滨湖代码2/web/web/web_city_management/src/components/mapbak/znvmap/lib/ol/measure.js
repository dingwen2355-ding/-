/* eslint-disable */
import ol from './ol'
function Measure (map, source, measureType, opts) {
    let sketch
    let helpTooltipElement
    let helpTooltip
    let measureTooltipElement
    let measureTooltip
    let continuePolygonMsg = '继续点击绘制多边形'
    let continueLineMsg = '继续点击绘制线'
    let draw

    // 移除测量结果提示信息
    /*removeTags('div', 'ol-tooltip ol-tooltip-static')
    function removeTags(tagName, tagClass) { // tagName：标签；tagClass：类名
      var tagElements = document.getElementsByTagName(tagName);
      for (var m = 0; m < tagElements.length; m++) {
        if (tagElements[m].className == tagClass) {
          tagElements[m].parentNode.removeChild(tagElements[m]);// 去除元素
        }
      }
    }*/
    createMeasureTooltip()
    createHelpTooltip()
      let pointerMoveHandler = function (evt) {
      if (evt.dragging) {
        return
      }
      /** @type {string} */
      let helpMsg = '请点击开始绘制'

      if (sketch) {
        let geom = (sketch.getGeometry())
        if (geom instanceof ol.geom.Polygon) {
          helpMsg = continuePolygonMsg
        } else if (geom instanceof ol.geom.LineString) {
          helpMsg = continueLineMsg
        }
      }

      helpTooltipElement.innerHTML = helpMsg
      helpTooltip.setPosition(evt.coordinate)

      helpTooltipElement.classList.remove('ol-hidden')
    }

    map.on('pointermove', pointerMoveHandler)

    map.getViewport().addEventListener('mouseout', function () {
      helpTooltipElement.classList.add('ol-hidden')
    })

    let formatLength = function (line) {
      let length = ol.sphere.getLength(line)
      let output
      if (length > 100) {
        output = (Math.round(length / 1000 * 100) / 100) +
          ' ' + 'km'
      } else {
        output = (Math.round(length * 100) / 100) +
          ' ' + 'm'
      }
      return output
    }
    let formatArea = function (polygon) {
      let area = ol.sphere.getArea(polygon)
      let output
      if (area > 10000) {
        output = (Math.round(area / 1000000 * 100) / 100) +
          ' ' + 'km<sup>2</sup>'
      } else {
        output = (Math.round(area * 100) / 100) +
          ' ' + 'm<sup>2</sup>'
      }
      return output
    }
    function addInteraction () {
      draw = new ol.interaction.Draw({
        source: source,
        type: measureType,
        style: new ol.style.Style({
          fill: new ol.style.Fill({
            color: 'rgba(255, 255, 255, 0.2)'
          }),
          stroke: new ol.style.Stroke({
            color: opts.strokeColor || '#1178cb',
            lineDash: [10, 10],
            width: 2
          }),
          image: new ol.style.Circle({
            radius: 5,
            stroke: new ol.style.Stroke({
              color: 'rgba(0, 0, 0, 0.7)'
            }),
            fill: new ol.style.Fill({
              color: 'rgba(255, 255, 255, 0.2)'
            })
          })
        })
      })
      // 将交互绘图对象添加到地图中
      map.addInteraction(draw)
      let listener
      // 定义一个控制鼠标点击次数的变量
      let count = 0
      draw.on('drawstart',
        function (evt) {
          sketch = evt.feature
          let tooltipCoord = evt.coordinate

          listener = sketch.getGeometry().on('change', function (evt) {
            let geom = evt.target
            let output
            if (geom instanceof ol.geom.Polygon) {
              output = formatArea(geom)
              tooltipCoord = geom.getInteriorPoint().getCoordinates()
            } else if (geom instanceof ol.geom.LineString) {
              output = formatLength(geom)
              tooltipCoord = geom.getLastCoordinate()
            }
            measureTooltipElement.innerHTML = output
            measureTooltip.setPosition(tooltipCoord)
          })
        }, this)

      draw.on('drawend',
        function () {
          count = 0
          // 设置测量提示框的样式
          measureTooltipElement.className = 'ol-tooltip ol-tooltip-static'
          // 设置偏移量
          measureTooltip.setOffset([0, -7])
          // 清空绘制要素
          sketch = null
          // 清空测量提示要素
          measureTooltipElement = null
          // 绘制结束，清除继续绘制的提示信息
          if (helpTooltipElement) {
            helpTooltipElement.parentNode.removeChild(helpTooltipElement)
          }
          // 创建测量提示框
          createMeasureTooltip()
          map.removeInteraction(draw);
          // 移除事件监听
          ol.Observable.unByKey(listener)
          // helpTooltipElement.classList.add('ol-hidden')
        }, this)
    }

    function createHelpTooltip () {
      if (helpTooltipElement) {
        helpTooltipElement.parentNode.removeChild(helpTooltipElement)
      }
      helpTooltipElement = document.createElement('div')
      helpTooltipElement.className = 'ol-tooltip ol-hidden'
      helpTooltip = new ol.Overlay({
        element: helpTooltipElement,
        offset: [15, 0],
        positioning: 'center-left'
      })
      map.addOverlay(helpTooltip)
    }

    function createMeasureTooltip () {
      if (measureTooltipElement) {
        measureTooltipElement.parentNode.removeChild(measureTooltipElement)
      }
      measureTooltipElement = document.createElement('div')
      measureTooltipElement.className = 'ol-tooltip ol-tooltip-measure'
      measureTooltip = new ol.Overlay({
        element: measureTooltipElement,
        offset: [0, -15],
        positioning: 'bottom-center'
      })
      map.addOverlay(measureTooltip)
    }
    // 量测调用
    addInteraction()
  }
export default Measure
