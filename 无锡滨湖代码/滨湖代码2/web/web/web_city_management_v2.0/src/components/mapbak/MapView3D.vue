<template>
  <div class="map-view">
    <div class="znvmap" id="map-container"></div>
    <!-- <MapFilter /> -->
    <!-- <el-slider
      v-show="rangeShow"
      @change="eventRadiuChange"
      v-model="eventRadiu"
      :max="20000"
      style="position: absolute;left: 25%; top: 170rem; width: 400rem"
    ></el-slider> -->
    <carDailog :dialogVisibleFlag.sync="carVisible" :infomation="carData" />
    <ShelterDailog :dialogVisibleFlag.sync="shelterVisible" :infomation="shelterData" />
    <schoolDialog :dialogVisibleFlag.sync="schoolVisible" :infomation="schoolData" />
    <specialDailog :dialogVisibleFlag.sync="specialListVisible" :infomation="specialListData" />
    <EmergencyDailog :dialogVisibleFlag.sync="emergencyVisible" :infomation="emergencyData" />
    <CoalMineDailog :dialogVisibleFlag.sync="CoalMineVisible" :infomation="CoalMineData" />
    <EnterpriseDailog :dialogVisibleFlag.sync="EnterpriseVisible" :infomation="EnterpriseData" />
    <queryHazardDailog :dialogVisibleFlag.sync="queryHazardVisible" :infomation="queryHazardData" />
    <MedicalInstitutionDailog :dialogVisibleFlag.sync="MedicalInstitutionVisible" :infomation="MedicalInstitutionData" />
    <ObservationStationDailog :dialogVisibleFlag.sync="ObservationStationVisible" :infomation="ObservationStationData" />
    <ProneDisasterDailog :dialogVisibleFlag.sync="ProneDisasterVisible" :infomation="ProneDisasterData" />
    <RescueDailog :dialogVisibleFlag.sync="RescueVisible" :infomation="RescueData" />
    <ReservoirDailog :dialogVisibleFlag.sync="ReservoirVisible" :infomation="ReservoirData" />
    <TransportDailog :dialogVisibleFlag.sync="TransportVisible" :infomation="TransportData" />
    <WaterloggingDailog :dialogVisibleFlag.sync="WaterloggingVisible" :infomation="WaterloggingData" />
    <WeatherStationDailog :dialogVisibleFlag.sync="WeatherStationVisible" :infomation="WeatherStationData" />
  </div>
</template>

<script>
import ZnvMap from '@/components/map/znvMap-3d'
// import { getIconByName } from './mapIcon'
import qs from 'qs'
import {
  getDeviceFixedVideo,
  getCar,
  getEmergencyCoordinates,
  getEmergencySupplies,
  queryCoalMine,
  queryEnterprise,
  queryHazard,
  queryMedicalInstitution,
  queryObservationStation,
  queryProneDisaster,
  queryRescue,
  queryReservoir,
  queryTransport,
  queryWaterlogging,
  queryWeatherStation,
  queryIndividualList,
  queryCSBJList
} from '@/api/info'
import mapIcon from './mapIconPic'
import carDailog from './mapDialog/carDialog.vue'
import ShelterDailog from './mapDialog/ShelterDailog.vue'
import EmergencyDailog from './mapDialog/EmergencyDailog.vue'
import specialDailog from './mapDialog/specialDailog.vue'
import schoolDialog from './mapDialog/schoolDialog.vue'
import CoalMineDailog from './mapDialog/CoalMineDailog.vue'
import EnterpriseDailog from './mapDialog/EnterpriseDailog.vue'
import queryHazardDailog from './mapDialog/queryHazardDailog.vue'
import MedicalInstitutionDailog from './mapDialog/MedicalInstitutionDailog.vue'
import ProneDisasterDailog from './mapDialog/ProneDisasterDailog.vue'
import RescueDailog from './mapDialog/RescueDailog.vue'
import ReservoirDailog from './mapDialog/ReservoirDailog.vue'
import TransportDailog from './mapDialog/TransportDailog.vue'
import WaterloggingDailog from './mapDialog/WaterloggingDailog.vue'
import WeatherStationDailog from './mapDialog/WeatherStationDailog.vue'
import ObservationStationDailog from './mapDialog/ObservationStationDailog.vue'
import iconInfo from "../../assets/mapIcon/应急物资_blue_hover.png";
import {getEmergencyExpert} from "../../api/info";
// import iconInfo from '../../assets/mapIcon/单兵_blue_hover.png'
export default {
  components: {
    carDailog,
    ShelterDailog,
    EmergencyDailog,
    specialDailog,
    schoolDialog,
    CoalMineDailog,
    EnterpriseDailog,
    queryHazardDailog,
    MedicalInstitutionDailog,
    ObservationStationDailog,
    ProneDisasterDailog,
    RescueDailog,
    ReservoirDailog,
    TransportDailog,
    WaterloggingDailog,
    WeatherStationDailog
  },
  data() {
    return {
      eventRadiu: 500,
      center: {},
      carVisible: false,
      carData: {},
      shelterVisible: false,
      shelterData: {},
      specialListVisible: false,
      specialListData: {},
      emergencyVisible: false,
      emergencyData: {},
      schoolVisible: false,
      schoolData: {},
      CoalMineVisible: false,
      CoalMineData: {},
      EnterpriseVisible: false,
      EnterpriseData: {},
      queryHazardVisible: false,
      queryHazardData: {},
      MedicalInstitutionVisible: false,
      MedicalInstitutionData: {},
      ObservationStationVisible: false,
      ObservationStationData: {},
      ProneDisasterVisible: false,
      ProneDisasterData: {},
      RescueVisible: false,
      RescueData: {},
      ReservoirVisible: false,
      ReservoirData: {},
      TransportVisible: false,
      TransportData: {},
      WaterloggingVisible: false,
      WaterloggingData: {},
      WeatherStationVisible: false,
      WeatherStationData: {},
      eventList: {},
      serviceTimer: null,
      nameList: [],
      abTimer: null,
      taxiTimer: null,
      xfTimer: null,
      radiusList: '',
      rangeShow: true,
      sourceTransList:{
        '救援队伍':[],
        '救援专家':[],
        '避难场所':[],
        '物资装备':[]
      }
    }
  },
  mounted() {
    this.$root.Bus.$on('changeMarkerType', (v) => {
      this.nameList = v
      ZnvMap.showCircleContainPointByKey([
        {
          key: 'type',
          value: v
        }
      ])
    })
    this.$root.Bus.$on('setRangeShow', data => {
      this.rangeShow = data
    })
    this.$root.Bus.$on('setClear', (v) => {
      ZnvMap.showEventCircleContainMarker()
      setTimeout(() => {
        let data = ''
        let event = this.$store.getters.eventInfo
        if (event && event.gpsx && event.gpsy) {
          data = [event.gpsx, event.gpsy]
        } else {
          data = [120.27542136266311, 31.531931670765953]
        }
        ZnvMap.gotoPoint(data)
      }, 1000)
    })
    // setTimeout(() => {
    //   this.initMap()
    // }, 100)

    this.$root.Bus.$on('eventRadiu', (data) => {
        if(data!=''){
          this.eventRadiu = data * 1000
          this.eventRadiuChange(this.eventRadiu)
          let markers = ZnvMap.listEventCircleContainMarker()
          let trans = {
            '救援队伍':[],
            '救援专家':[],
            '避难场所':[],
            '物资装备':[]
          }
          markers.forEach(t=>{
            if(t.opts.id.indexOf('应急救援队伍')> -1){
              trans.救援队伍.push(t.opts.extData)
            }
            if(t.opts.id.indexOf('应急专家')> -1){
              trans.救援专家.push(t.opts.extData)
            }
            if(t.opts.id.indexOf('避难场所')> -1){
              trans.避难场所.push(t.opts.extData)
            }
            if(t.opts.id.indexOf('应急物资')> -1){
              trans.物资装备.push(t.opts.extData)
            }
          })
          this.$root.Bus.$emit('eventInfos', trans)
        }else{
          this.eventRadiu = 500
          this.eventRadiuChange(this.eventRadiu)
          this.$root.Bus.$emit('eventInfos', this.sourceTransList)
        }
    })
    // 应急救援队伍
    let p1 = this.queryRescuePointEvent()
    // 应急专家
    let p2 = this.loadSpecialPointEvent()
    // 避难场所
    let p3 = this.loadShelterCoordinatesPointEvent()
    // 应急物资仓库
    let p4 = this.loadEmergencyCoordinatesPointEvent()
    Promise.all([p1,p2,p3,p4]).then(()=>{
      this.$root.Bus.$emit('eventInfos', this.sourceTransList)
    })
  },
  watch: {
    '$store.getters.windowInfoDetail': {
      handler(v) {
        this.showWindowInfoDetail(v.data)
      }
    },
    '$store.getters.eventInfo': {
      handler() {
        this.initMap()
      },
      // immediate: true,
      deep: true
    }
  },
  methods: {
    initMap() {
      let data = ''
      let event = this.$store.getters.eventInfo
      if (event && event.gpsx && event.gpsy) {
        data = [event.gpsx, event.gpsy, 20000]
      } else {
        data = [120.27542136266311, 31.531931670765953, 20000]
      }
      // 初始化地图
      ZnvMap.start('map-container', {
        center: data
      }).then(() => {
        // 加载事件点位
        // this.loadEventPoints()
        // 加载点位
        this.loadMapPoints()
      })
    },
    loadEventPoints() {
      let data = []
      let event = this.$store.getters.eventInfo
      if (event && event.gpsx && event.gpsy) {
        data.push(event)
      } else {
        let obj = {
          eventId: '25',
          gpsx: 120.27542136266311,
          gpsy: 31.531931670765953
        }
        data.push(obj)
      }
      let pointList = this.eventPointProcess(data)
      ZnvMap.loadEventPointLayer(pointList, { isHidden: false })
      setTimeout(() => {
        this.loadEventCircle()
      }, 3000)
    },
    eventPointProcess(data) {
      return data.map(e => {
        let temp = {
          id: e.eventId,
          type: 'event',
          icon: require('../../assets/mapIcon/定位.png'),
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 42, height: 48 },
          offset: { x: -21, y: -48 },
          extData: e
        }
        return temp
      })
    },
    loadEventCircle() {
      let data = ''
      let event = this.$store.getters.eventInfo
      if (event && event.gpsx && event.gpsy) {
        data = [event.gpsx, event.gpsy]
      } else {
        data = [120.27542136266311, 31.531931670765953]
      }
      ZnvMap.loadEventCircle({
        center: data,
        radiu: this.eventRadiu,
        height: 20
      })
    },
    eventRadiuChange(v) {
      this.radiusList = v
      ZnvMap.changeEventCircleRadiu(v,this.nameList)
    },
    loadMapPoints() {
      // 摄像头
      this.loadFixedVideoPoint({ isHidden: true })

      // 城市部件
      this.queryCSBJListPoint({ isHidden: true })
      // // 消防车
      // this.loadFiretruckPoint({ isHidden: true })
      // this.xfTimer = setInterval(() => {
      //   ZnvMap.removeLayerById(this.nameList)
      //   if (this.nameList.includes('firetruck')) {
      //     this.loadFiretruckPoint({ isHidden: false })
      //     setTimeout(() => {
      //       ZnvMap.showCircleContainPointByKey([
      //         {
      //           key: 'type',
      //           value: this.nameList
      //         }
      //       ])
      //     }, 1000)
      //   } else {
      //     this.loadFiretruckPoint({ isHidden: true })
      //   }
      // }, 60 * 2000)
      // // 公交车
      // this.loadBusPoint({ isHidden: true })
      // this.serviceTimer = setInterval(() => {
      //   ZnvMap.removeLayerById(this.nameList)
      //   if (this.nameList.includes('bus')) {
      //     this.loadBusPoint({ isHidden: false })
      //     setTimeout(() => {
      //       ZnvMap.showCircleContainPointByKey([
      //         {
      //           key: 'type',
      //           value: this.nameList
      //         }
      //       ])
      //     }, 1000)
      //   } else {
      //     this.loadBusPoint({ isHidden: true })
      //   }
      // }, 60 * 1000)
      // // 出租车
      // this.loadTaxiPoint({ isHidden: true })
      // this.taxiTimer = setInterval(() => {
      //   ZnvMap.removeLayerById(this.nameList)
      //   if (this.nameList.includes('taxi')) {
      //     this.loadTaxiPoint({ isHidden: false })
      //     setTimeout(() => {
      //       ZnvMap.showCircleContainPointByKey([
      //         {
      //           key: 'type',
      //           value: this.nameList
      //         }
      //       ])
      //     }, 1000)
      //   } else {
      //     this.loadTaxiPoint({ isHidden: true })
      //   }
      // }, 60 * 1000)
      // // 救护车
      // this.loadAmbulancePoint({ isHidden: true })
      // this.abTimer = setInterval(() => {
      //   ZnvMap.removeLayerById(this.nameList)
      //   if (this.nameList.includes('ambulance')) {
      //     this.loadAmbulancePoint({ isHidden: false })
      //     setTimeout(() => {
      //       ZnvMap.showCircleContainPointByKey([
      //         {
      //           key: 'type',
      //           value: this.nameList
      //         }
      //       ])
      //     }, 1000)
      //   } else {
      //     this.loadAmbulancePoint({ isHidden: true })
      //   }
      // }, 60 * 1000)
      // // 学校
      // this.loadSchoolPoint({ isHidden: true })
      // // 煤矿企业
      // this.queryCoalMinePoint({ isHidden: true })
      // // 重点企业
      // this.queryEnterprisePoint({ isHidden: true })
      // // 重大危险源
      // this.queryHazardPoint({ isHidden: true })
      // // 医疗机构
      // this.queryMedicalInstitutionPoint({ isHidden: true })
      // // 水雨情监测站
      // this.queryObservationStationPoint({ isHidden: true })
      // // 易灾点
      // this.queryProneDisasterPoint({ isHidden: true })
      // // 水库
      // this.queryReservoirPoint({ isHidden: true })
      // // 运输力量
      // this.queryTransportPoint({ isHidden: true })
      // // 内涝点
      // this.queryWaterloggingPoint({ isHidden: true })
      // // 气象站
      // this.queryWeatherStationPoint({ isHidden: true })
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
      // 加载事件点位
      this.loadEventPoints()
    },
    // 城市部件
    queryCSBJListPoint(opts) {
      queryCSBJList().then(res => {
        let pointList = this.queryCSBJListProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryCSBJListProcess(data) {
      return data.map((e, index) => {
        let iconInfo = mapIcon[e.parentType]
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
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 单兵
    queryIndividualListPoint(opts) {
      /*queryIndividualList().then(res => {
        let pointList = this.queryIndividualListProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })*/
      let dataList = [
        {
          "timeStamp": 1687837858000,
          "deviceKind": 666,
          "alarmState": 0,
          "onofflineState": 0,
          "precinctId": "510100",
          "fault": 0,
          "deviceState": 0,
          "deviceId": "7",
          "deviceName": "移动单兵",
          "gpsx": "120.269476",
          "gpsy": "31.529424"
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
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 避难场所
    loadShelterCoordinatesPoint(opts) {
      getEmergencyCoordinates('1').then(res => {
        let pointList = this.ShelterCoordinatesProcess(res.data.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    // 避难场所
    loadShelterCoordinatesPointEvent() {
      return getEmergencyCoordinates('0').then(res => {
        this.sourceTransList.避难场所 = res.data.data.data
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
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 应急专家
    loadSpecialPoint(opts) {
      getEmergencyExpert('1').then(res => {
        let pointList = this.SpecialProcess(res.data.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    // 应急专家
    loadSpecialPointEvent() {
      return getEmergencyExpert("0").then(res => {
        this.sourceTransList.救援专家 = res.data.data.data
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
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 学校
    loadSchoolPoint(opts) {
      getEmergencyCoordinates('学校').then(res => {
        let pointList = this.SchoolProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    SchoolProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/学校_blue_hover.png')
        e.type = 'school'
        e.clickType = '学校'
        let temp = {
          id: '学校_' + index,
          icon: iconInfo,
          title: e.XXMC,
          position: { lng: e.GPSX, lat: e.GPSY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 应急物资仓库
    loadEmergencyCoordinatesPoint(opts) {
      getEmergencySupplies('1').then(res => {
        let pointList = this.EmergencySuppliesPointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    // 应急物资仓库
    loadEmergencyCoordinatesPointEvent() {
      return getEmergencySupplies('0').then(res => {
        this.sourceTransList.物资装备 = res.data.data.list
      })
    },
    EmergencySuppliesPointProcess(data){
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/应急物资_blue_hover.png')
        e.type = 'fxbox'
        e.clickType = '应急物资'
        let temp = {
          id: '应急物资_' + index,
          icon: iconInfo,
          title: e.stockName,
          position: { lng: e.longitude, lat: e.latitude, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    EmergencyCoordinatesPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/应急物资_blue_hover.png')
        e.type = 'fxbox'
        e.clickType = '应急物资'
        let temp = {
          id: '应急物资_' + index,
          icon: iconInfo,
          title: e.CBKMC,
          position: { lng: e.GPSX, lat: e.GPSY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 固定视频
    loadFixedVideoPoint(opts) {
      getDeviceFixedVideo().then(res => {
        let pointList = this.fixVideoPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    fixVideoPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = mapIcon[e.deviceTypeName] || mapIcon['固定视频']
        e.type = 'video'
        e.clickType = '视频监控'
        let temp = {
          id: '视频监控_' + index,
          icon: iconInfo,
          title: e.deviceName,
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    loadAmbulancePoint(opts) {
      getCar('救护车').then(res => {
        let pointList = this.AmbulancePointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    AmbulancePointProcess(data) {
      return data.map((e, index) => {
        if (e.gpsx && e.gpsy) {
          let iconInfo = require('../../assets/mapIcon/救护车.png')
          e.type = 'ambulance'
          e.clickType = '救护车'
          let temp = {
            id: '救护车_' + index,
            icon: iconInfo,
            title: e.dept,
            position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
            size: { width: 31, height: 34 },
            offset: { x: -10, y: -24 },
            extData: e
          }
          return temp
        }
      })
    },
    loadTaxiPoint(opts) {
      getCar('出租车').then(res => {
        let pointList = this.TaxiPointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    TaxiPointProcess(data) {
      return data.map((e, index) => {
        if (e.gpsx && e.gpsy) {
          let iconInfo = require('../../assets/mapIcon/taxi.png')
          e.type = 'taxi'
          e.clickType = '出租车'
          let temp = {
            id: '出租车_' + index,
            icon: iconInfo,
            title: e.dept,
            position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
            size: { width: 31, height: 34 },
            offset: { x: -10, y: -24 },
            extData: e
          }
          return temp
        }
      })
    },
    loadBusPoint(opts) {
      getCar('公交车').then(res => {
        let pointList = this.loadBusProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    loadBusProcess(data) {
      return data.map((e, index) => {
        if (e.gpsx && e.gpsy) {
          let iconInfo = require('../../assets/mapIcon/bus.png')
          e.type = 'bus'
          e.clickType = '公交车'
          let temp = {
            id: '公交车_' + index,
            icon: iconInfo,
            title: e.dept,
            position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
            size: { width: 31, height: 34 },
            offset: { x: -10, y: -24 },
            extData: e
          }
          return temp
        }
      })
    },
    loadFiretruckPoint(opts) {
      getCar('消防车').then(res => {
        let pointList = this.FiretruckPointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    FiretruckPointProcess(data) {
      return data.map((e, index) => {
        if (e.gpsx && e.gpsy) {
          let iconInfo = require('../../assets/mapIcon/xf.png')
          e.type = 'firetruck'
          e.clickType = '消防车'
          let temp = {
            id: '消防车_' + index,
            icon: iconInfo,
            title: e.dept,
            position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
            size: { width: 31, height: 34 },
            offset: { x: -10, y: -24 },
            extData: e
          }
          return temp
        }
      })
    },
    // 煤矿企业
    queryCoalMinePoint(opts) {
      queryCoalMine().then(res => {
        let pointList = this.queryCoalMinePointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryCoalMinePointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/煤矿企业.png')
        e.type = 'coal'
        e.clickType = '煤矿企业'
        let temp = {
          id: '煤矿企业_' + index,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 重点企业
    queryEnterprisePoint(opts) {
      queryEnterprise().then(res => {
        let pointList = this.queryEnterprisePointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryEnterprisePointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/重点企业.png')
        e.type = 'KeyEnterprises'
        e.clickType = '重点企业'
        let temp = {
          id: '重点企业_' + index,
          icon: iconInfo,
          title: e.enterpriseName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 重大危险源
    queryHazardPoint(opts) {
      queryHazard().then(res => {
        let pointList = this.queryHazardPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryHazardPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/社会治理大联动_red_hover.png')
        e.type = 'sentry'
        e.clickType = '重大危险源'
        let temp = {
          id: '重大危险源_' + index,
          icon: iconInfo,
          title: e.unitName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 医疗机构
    queryMedicalInstitutionPoint(opts) {
      queryMedicalInstitution().then(res => {
        let pointList = this.queryMedicalInstitutionPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryMedicalInstitutionPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/医院_blue_hover.png')
        e.type = 'host'
        e.clickType = '医疗机构'
        let temp = {
          id: '医疗机构_' + index,
          icon: iconInfo,
          title: e.institutionName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 水雨情监测站
    queryObservationStationPoint(opts) {
      queryObservationStation().then(res => {
        let pointList = this.queryObservationStationPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryObservationStationPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/水位监测_blue_hover.png')
        e.type = 'water'
        e.clickType = '水雨情监测站'
        let temp = {
          id: '水雨情监测站_' + index,
          icon: iconInfo,
          title: e.stationName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 易灾点
    queryProneDisasterPoint(opts) {
      queryProneDisaster().then(res => {
        let pointList = this.queryProneDisasterPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryProneDisasterPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/防台防汛_red_hover.png')
        e.type = 'easyPoint'
        e.clickType = '易灾点'
        let temp = {
          id: '易灾点_' + index,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 应急救援队伍
    queryRescuePoint(opts) {
      queryRescue('1').then(res => {
        let pointList = this.queryRescuePointProcess(res.data.data.teamList)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    // 应急救援队伍
    queryRescuePointEvent() {
      return queryRescue('0').then(res => {
        this.sourceTransList.救援队伍 = res.data.data.teamList
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
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 水库
    queryReservoirPoint(opts) {
      queryReservoir().then(res => {
        let pointList = this.queryReservoirPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryReservoirPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/积水风险_blue_hover.png')
        e.type = 'waterThing'
        e.clickType = '水库'
        let temp = {
          id: '水库_' + index,
          icon: iconInfo,
          title: e.resrevoirName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 运输力量
    queryTransportPoint(opts) {
      queryTransport().then(res => {
        let pointList = this.queryTransportPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryTransportPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/道路_blue_hover.png')
        e.type = 'firerescue'
        e.clickType = '运输力量'
        let temp = {
          id: '运输力量_' + index,
          icon: iconInfo,
          title: e.unitName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 内涝点
    queryWaterloggingPoint(opts) {
      queryWaterlogging().then(res => {
        let pointList = this.queryWaterloggingPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryWaterloggingPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/雨量监测_blue_hover.png')
        e.type = 'inPoint'
        e.clickType = '内涝点'
        let temp = {
          id: '内涝点_' + index,
          icon: iconInfo,
          title: e.unitName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    // 气象站
    queryWeatherStationPoint(opts) {
      queryWeatherStation().then(res => {
        let pointList = this.queryWeatherStationPointProcess(res.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryWeatherStationPointProcess(data) {
      return data.map((e, index) => {
        let iconInfo = require('../../assets/mapIcon/空气监测_blue_hover.png')
        e.type = 'metro'
        e.clickType = '气象站'
        let temp = {
          id: '气象站_' + index,
          icon: iconInfo,
          title: e.stationName,
          position: { lng: e.gpsX, lat: e.gpsY, height: 100 },
          size: { width: 31, height: 34 },
          offset: { x: -10, y: -24 },
          extData: e
        }
        return temp
      })
    },
    showWindowInfoDetail(v) {
      this.carVisible = false
      if (v.clickType === '视频监控') {
        this.$root.Bus.$emit('openVideoWindow', v)
      } else if (v.type === 'firetruck' || v.type === 'ambulance' || v.type === 'taxi' || v.type === 'bus') {
        this.carVisible = true
        this.carData = v
      } else if (v.clickType === '避难场所') {
        this.shelterVisible = true
        this.shelterData = v
      } else if (v.clickType === '应急专家') {
        this.specialListVisible = true
        this.specialListData = v
      } else if (v.clickType === '应急物资') {
        this.emergencyVisible = true
        this.emergencyData = v
      } else if (v.clickType === '学校') {
        this.schoolVisible = true
        this.schoolData = v
      } else if (v.clickType === '煤矿企业') {
        this.CoalMineVisible = true
        this.CoalMineData = v
      } else if (v.clickType === '重点企业') {
        this.EnterpriseVisible = true
        this.EnterpriseData = v
      } else if (v.clickType === '重大危险源') {
        this.queryHazardVisible = true
        this.queryHazardData = v
      } else if (v.clickType === '医疗机构') {
        this.MedicalInstitutionVisible = true
        this.MedicalInstitutionData = v
      } else if (v.clickType === '水雨情监测站') {
        this.ObservationStationVisible = true
        this.ObservationStationData = v
      } else if (v.clickType === '易灾点') {
        this.ProneDisasterVisible = true
        this.ProneDisasterData = v
      } else if (v.clickType === '应急救援队伍') {
        this.RescueVisible = true
        this.RescueData = v
      } else if (v.clickType === '水库') {
        this.ReservoirVisible = true
        this.ReservoirData = v
      } else if (v.clickType === '运输力量') {
        this.TransportVisible = true
        this.TransportData = v
      } else if (v.clickType === '内涝点') {
        this.WaterloggingVisible = true
        this.WaterloggingData = v
      } else if (v.clickType === '气象站') {
        this.WeatherStationVisible = true
        this.WeatherStationData = v
      } else if (v.clickType === '单兵') {
        /*console.log(v)
        let strWindowFeatures = 'top=450,left=1500,width=600,height=500'
        let second = {
          device_id: v.deviceId,
          device_name: v.deviceName,
          unit: v.unit,
          subtype: 'second',
          precinct: v.precinctId,
          onofflineState: v.onofflineState,
          precinctAll: v.precinctAll
        }
        window.open('/MapInfoWindow?' + qs.stringify(second), v.deviceId, strWindowFeatures)*/
        this.$root.Bus.$emit('openVideoWindow', v)
      }
    }
  },
  beforeDestroy() {
    this.serviceTimer && clearInterval(this.serviceTimer)
    this.abTimer && clearInterval(this.abTimer)
    this.taxiTimer && clearInterval(this.taxiTimer)
    this.xfTimer && clearInterval(this.xfTimer)
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
    background: #343434;
    .cesium-widget-credits {
      display: none;
    }
  }
  .opts-box{
    position: absolute;
    top: 20rem;
    right: 20rem;
  }
}
</style>
