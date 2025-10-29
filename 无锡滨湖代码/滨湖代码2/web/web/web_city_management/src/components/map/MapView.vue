<template>
  <div class="map-view">
    <div class="znvmap" id="map-container"></div>
    <MapFilter />
    <MapTool />
    <RescueDailog :dialogVisibleFlag.sync="RescueVisible" :infomation="RescueData" />
    <ShelterDailog :dialogVisibleFlag.sync="shelterVisible" :infomation="shelterData" />
    <specialDailog :dialogVisibleFlag.sync="specialListVisible" :infomation="specialListData" />
    <EmergencyDailog :dialogVisibleFlag.sync="emergencyVisible" :infomation="emergencyData" />
  </div>
</template>

<script>
import ZnvMap from '@/components/map/znvMap'
import axios from '@/utils/request'
import MapFilter from './MapFilter'
import MapTool from './MapTool'
import RescueDailog from './mapDialog/RescueDailog.vue'
import ShelterDailog from './mapDialog/ShelterDailog.vue'
import specialDailog from './mapDialog/specialDailog.vue'
import EmergencyDailog from './mapDialog/EmergencyDailog.vue'
export default {
  components: { MapFilter, MapTool, RescueDailog, ShelterDailog, specialDailog, EmergencyDailog },
  data() {
    return {
      RescueData: {},
      RescueVisible: false,
      shelterData: {},
      specialListData:{},
      shelterVisible: false,
      RescueDatspecialListDataa: {},
      specialListVisible: false,
      emergencyData: {},
      emergencyVisible: false
    }
  },
  mounted() {
    setTimeout(() => {
      this.initMap()
    }, 1000)
  },
  watch: {
    '$store.getters.windowInfoDetail': {
      handler(v) {
        this.showWindowInfoDetail(v.data)
      }
    }
  },
  methods: {
    initMap() {
      // 初始化地图
      ZnvMap.start('map-container', {
        zoom: 15,
        minZoom: 1,
        maxZoom: 20,
        center: [120.310846, 31.58658]
      }).then(() => {
        setTimeout(() => {
          this.loadEventCircle()
        }, 1000)
        // 加载点位
        this.loadMapPoints()
      })
    },
    loadEventCircle() {
      let data = ''
      let event = this.$store.getters.eventInfo
      if (event && event.gpsx && event.gpsy) {
        data = [event.gpsx, event.gpsy]
      } else {
        data = [120.310846, 31.58658]
      }
      ZnvMap.gotoPoint(data)
      ZnvMap.loadEventPointLayer(
        [
          {
            id: 'event-marker',
            type: 'event',
            icon: require('../../assets/mapIcon/定位.png'),
            position: { lng: data[0], lat: data[1], height: 100 },
            size: { width: 42, height: 48 },
            offset: { x: -21, y: -48 },
            extData: {}
          }
        ],
        { isHidden: false }
      )
      ZnvMap.loadEventCircle({
        id: 'event_circle',
        center: data,
        radiu: 500,
        height: 20
      })
    },
    loadMapPoints() {
      // 摄像头
      this.loadFixedVideoPoint({ isHidden: true })
      // 城市部件
      this.queryCSBJListPoint({ isHidden: true })
      // 单兵
      this.queryIndividualListPoint({ isHidden: true })
      // 应急救援队伍
      this.queryRescuePoint({ isHidden: true })
      // 应急专家
      this.loadSpecialPoint({ isHidden: true })
      // 避难场所
      this.loadShelterCoordinatesPoint({ isHidden: true })
      // 应急物资仓库
      this.loadEmergencyCoordinatesPoint({ isHidden: true })
    },

    // 固定视频
    loadFixedVideoPoint(opts) {
      axios.get('/apis/dahua/getDeviceList').then(res => {
        let pointList = this.fixVideoPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    fixVideoPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/固定视频_blue_hover.png')
        e.type = 'video'
        e.clickType = '视频监控'
        let temp = {
          id: '视频监控_' + index,
          icon: iconInfo,
          title: e.deviceName,
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },

    // 城市部件
    queryCSBJListPoint(opts) {
      axios.get('/apis/cityFirmware/query').then(res => {
        let pointList = this.queryCSBJListProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryCSBJListProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require(`../../assets/mapIcon/point_${e.parentType}@2x.png`)
        let typeMap = {
          公司企业: 'company',
          交通设施: 'traffic',
          旅游景点: 'scenicSpot',
          生活服务: 'lifeService',
          休闲娱乐: 'recreation',
          医疗保健: 'healthcare'
        }
        e.type = typeMap[e.parentType]
        e.clickType = e.parentType
        let temp = {
          id: e.parentType + '_' + index,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },

    // 单兵
    queryIndividualListPoint(opts) {
      let dataList = [
        {
          timeStamp: 1687837858000,
          deviceKind: 666,
          alarmState: 0,
          onofflineState: 0,
          precinctId: '510100',
          fault: 0,
          deviceState: 0,
          deviceId: '7',
          deviceName: '移动单兵',
          gpsx: '120.269476',
          gpsy: '31.529424'
        }
      ]
      let pointList = this.queryIndividualListProcess(dataList)
      ZnvMap.loadPointLayer(pointList, opts)
    },
    queryIndividualListProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/单兵_blue_hover.png')
        e.type = 'solo'
        e.clickType = '单兵'
        let temp = {
          id: '单兵_' + index,
          icon: iconInfo,
          title: e.deviceName,
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },

    // 应急救援队伍
    queryRescuePoint(opts) {
      axios.get('/community/teamInfo/queryRescueTeamInfo?isAll=1').then(res => {
        let pointList = this.queryRescuePointProcess(res.data.data.teamList)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryRescuePointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/救援队伍_blue_hover.png')
        e.type = 'firestation'
        e.clickType = '应急救援队伍'
        let temp = {
          id: '应急救援队伍_' + index,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.LONGITUDE, lat: e.LATITUDE, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },

    // 应急专家
    loadSpecialPoint(opts) {
      axios.get('/community/basicExpert/getBasicExpertList?isAll=1').then(res => {
        let pointList = this.SpecialProcess(res.data.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    SpecialProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/专家_blue_hover.png')
        e.type = 'specialist'
        e.clickType = '应急专家'
        let temp = {
          id: '应急专家_' + index,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },

    // 避难场所
    loadShelterCoordinatesPoint(opts) {
      axios.get('/community/siteManage/selectBasicShelter?isAll=1').then(res => {
        let pointList = this.ShelterCoordinatesProcess(res.data.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    ShelterCoordinatesProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/避难场所_blue_hover.png')
        e.type = 'shelter'
        e.clickType = '避难场所'
        let temp = {
          id: '避难场所_' + index,
          icon: iconInfo,
          title: e.shelterName,
          position: { lng: e.shelterLongitude, lat: e.shelterLatitude, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },

    // 应急物资仓库
    loadEmergencyCoordinatesPoint(opts) {
      axios.get('/community/resourceStuff/query?isAll=1').then(res => {
        let pointList = this.EmergencySuppliesPointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    EmergencySuppliesPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/应急物资_blue_hover.png')
        e.type = 'fxbox'
        e.clickType = '应急物资'
        let temp = {
          id: '应急物资_' + index,
          icon: iconInfo,
          title: e.stockName,
          position: { lng: e.longitude, lat: e.latitude, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },
    showWindowInfoDetail(v) {
      if (v.clickType === '视频监控') {
        this.$root.Bus.$emit('openVideoWindow', v)
      } else if (v.clickType === '单兵') {
        this.$root.Bus.$emit('openVideoWindow', v)
      } else if (v.clickType === '应急救援队伍') {
        this.RescueVisible = true
        this.RescueData = v
      } else if (v.clickType === '避难场所') {
        this.shelterVisible = true
        this.shelterData = v
      } else if (v.clickType === '应急专家') {
        this.specialListVisible = true
        this.specialListData = v
      } else if (v.clickType === '应急物资') {
        this.emergencyVisible = true
        this.emergencyData = v
      }
    }
  }
}
</script>

<style lang="scss">
.map-view {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
  flex-grow: 1;
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
  @keyframes twinkle {
    0% {
      transform: scale(0.7);
      transform-origin: center bottom;
      opacity: 0;
    }
    50% {
      transform: scale(2);
      transform-origin: center bottom;
      opacity: 1;
    }
    100% {
      transform: scale(0.7);
      transform-origin: center bottom;
      opacity: 0;
    }
  }
}
</style>
