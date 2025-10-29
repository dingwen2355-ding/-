<template>
  <div class="map-view">
    <div class="search-content">
      <span class="gps-info">点位坐标：{{ searchVal }}</span>
      <el-button type="primary" @click="submitInfo()">确 定</el-button>
    </div>
    <div class="znvmap" id="map-container1"></div>
  </div>
</template>

<script>
var minemap = window.minemap
var map
export default {
  props: ['gpsInfo'],
  data() {
    return {
      searchVal: '',
      infomation: {},
      ShelterDetailDialogVisible: false
    }
  },
  mounted() {
    this.initMap()
  },
  watch: {
    gpsInfo: {
      handler(newval) {
        this.searchVal = newval.toString().replaceAll('undefined', '')
        let point = this.searchVal.split(',')
        if (point[0].trim() && point[1].trim()) {
          this.drawPoint({
            lng: point[0].trim(),
            lat: point[1].trim()
          })
        }
      }
    }
  },
  methods: {
    submitInfo() {
      if (this.searchVal) {
        let obj = {}
        obj.value = this.searchVal
        this.$emit('gpsChoose', obj)
        this.searchVal = ''
      } else {
        this.$message.warning('请先选中点位！')
      }
    },
    initMap() {
      let self = this
      map = new minemap.Map({
        container: 'map-container1',
        // style: 'https://service.minedata.cn/map/solu/style/11001', // 家里
        style: '//2.22.131.158:21009/service/solu/style/id/222530', // 现场
        center: [120.284401, 31.527823],
        zoom: 10,
        pitch: 0,
        maxZoom: 17,
        minZoom: 3,
        // projection: 'MERCATOR', // 家里
        projection: 'LATLON' // 现场
      })

      /**
       * 地图加载完成
       * 创建地图对象后，开始加载地图资源，地图资源加载完成后触发load事件
       */
      map.on('load', function () {
        // 地图资源加载完成后触发
        // 增加自定义数据源、自定义图层
        if (self.gpsInfo) {
          self.searchVal = self.gpsInfo.replaceAll('undefined', '')
          let point = self.searchVal.split(',')
          if (point[0].trim() && point[1].trim()) {
            self.drawPoint({
              lng: point[0].trim(),
              lat: point[1].trim()
            })
          }
        }
        map.on('click', (e) => {
          var p = e.lngLat
          self.searchVal = p.lng.toFixed(6) + ',' + p.lat.toFixed(6)
          self.drawPoint(p)
        })
      })
    },
    drawPoint(lnglat) {
      if (map) {
        if (this._marker) {
          this._marker.remove()
        }
        this._marker = new minemap.Marker({
          draggable: true, // 可以在初始化的时候决定是否可以拖拽
          anchor: 'top-left', // 锚点位置(默认值"top-left")，可选值有`'center'`, `'top'`, `'bottom'`, `'left'`, `'right'`, `'top-left'`, `'top-right'`, `'bottom-left'`, and `'bottom-right'`
          color: 'red', // 默认marker标记的颜色
          rotation: 0, // marker的旋转角度，以锚点作为旋转中心点，顺时针为正
          pitchAlignment: 'map', // 倾斜对齐参数(默认值是‘auto’)，此值决定marker标记是贴在地图平面上，还是立在地图平面上，当值为`map`时，marker标记贴在地图平面上，当值为`viewport`时，marker标记立在地图平面上，当值为 `auto`时，会根据 `rotationAlignment`参数的值，自动决定.
          rotationAlignment: 'map', // 旋转对齐参数(默认值是‘auto’)，此值决定地图在旋转的时候，marker标记是否跟随旋转，当值为`map` 时，marker标记会固定在地图平面上，不会跟随地图的旋转而旋转；当值为`viewport`时，marker标记会跟随地图的旋转而旋转，保持正向面对观察者；当值为`auto`时，相当于值`viewport`.
          scale: 1 // 只有默认标记有这个参数，将默认标记放大1.5倍
        })
          .setLngLat([lnglat.lng, lnglat.lat])
          .addTo(map)
      }
    }
  },
  beforeDestroy() {
    if (map) {
      map.remove()
      map = null
    }
  }
}
</script>

<style lang="scss">
.map-view {
  width: 100%;
  height: 100%;
  #map-container1 {
    width: 100%;
    height: 500px;
  }
  .znvmap {
    height: 100%;
    position: relative;
    background: #fff;
  }

  .twinkle-animation {
    animation: twinkle 1s infinite;
  }

  .twinkle-animation-10s {
    animation: twinkle 1s 10;
  }
  .search-content {
    display: flex;
    margin-bottom: 20px;
    .gps-info {
      flex-grow: 1;
    }
  }
}
</style>
