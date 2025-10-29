<template>
  <div class="MapViewAdd">
    <div class="znvmap" id="map-container-statis"></div>
    <div class="option-box">
      <el-button type="primary" @click="getTableDta(rowObj)">查看轨迹</el-button>
      <el-button type="primary" @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
import ZnvMap from './map/znvMap'
// import ZnvMap from '@/components/map/znvMap'
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'MapViewAdd',
  components: {},
  props: ['formInfo', 'isCode'],
  data() {
    return {
      mapShow: false,
      mapReady: false,
      clickGps: [],
      dialogVisible: true,
      polyline1: null,
      plineList: [],
      rowObj: {},
      time: []
    }
  },
  watch: {
    mapShow: {
      handler(v) {
        if (v) {
          this.$el.style.zIndex = 9999
        } else {
          this.$el.style.zIndex = -1
        }
      }
    }
  },
  mounted() {
    // this.initMap()
    this.$root.eventBus.$on('clickMapPline', (point) => {
      this.mapShow = true
      if (point) {
        // this.plineList = point.split(';')
        this.rowObj = point.rows
        this.time = point.time
        this.$nextTick(() => {
          this.initMap()
        })
      }
    })
  },
  methods: {
    getTableDta(point) {
      console.log(point, 'pointpoint')
      // this.listLoading = true
      let param = {}

      if (point.name) {
        param.name = point.name
      }
      if (this.time && this.time.length > 1) {
        param.beginTime = this.time[0]
        param.endTime = this.time[1]
      }
      let url = '/binhuapis/clock/queryClockInfoList?' + qs.stringify(param)
      axios.get(url).then((res) => {
        this.listLoading = false
        if (res.data.code === 200) {
          this.plineList = res.data.data || []
          this.handleDetailRoad()
        }
      })
    },
    handleClose(done) {
      this.$emit('update:dialogVisible', false)
    },
    initMap() {
      // 初始化地图
      ZnvMap.start('map-container-statis', {
        zoom: 13,
        minZoom: 10,
        maxZoom: 17,
        center: [120.422167, 36.117018]
      })
      // this.mapReady = true
    },
    handleDetailRoad() {
      console.log(this.plineList, '======777777777=======')
      if (this.polyline1) {
        this.polyline1._remove()
      }
      // let arr = []
      if (this.plineList && this.plineList.length > 0) {
        let path = this.plineList.map((v) => {
          return {
            lng: v.gpsx,
            lat: v.gpsy
          }
        })
        console.log(path, 'pathpathpath')
        this.polyline1 = ZnvMap.drawLine1(path, {
          strokeWeight: 6, // 线条宽度，默认为 1
          strokeColor: '#0091ea' // 线条颜色
          // lineJoin: 'round', // 折线拐点连接处样式
          // lineCap: 'round'
        })
      } else {
        this.$message.warning('无历史轨迹')
      }
    },
    cancel() {
      this.mapShow = false
    }
  }
}
</script>

<style lang="scss">
.MapViewAdd {
  width: 1700px;
  height: 1000px;
  position: absolute;
  top: 700px;
  left: 0;
  overflow: hidden;
  flex-grow: 1;
  z-index: -1;
  .znvmap {
    width: 100%;
    height: 600px;
    position: relative;
  }

  .option-box {
    position: absolute;
    right: 20px;
    top: 20px;
  }
}
</style>
