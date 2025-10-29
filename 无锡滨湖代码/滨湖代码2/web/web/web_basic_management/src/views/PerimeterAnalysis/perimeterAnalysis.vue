<template>
  <div class="perimeterAnalysis">
    <div class="main-container">
      <div class="tools-container">
        <el-form :inline="true" :model="searchForm" ref="form">
          <el-form-item label="事件名称:">
            <el-select v-model="searchForm.id" @change="changeEvent" @clear="clearEvent" filterable placeholder="请选择" style="width: 175px" clearable>
              <el-option
                v-for="item in eventList"
                :key="'event-'+item.id"
                :label="item.eventTitle"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="类别:">
            <el-select filterable v-model="searchForm.kind" style="width: 140px;" placeholder="请选择类别" clearable>
              <el-option v-for="item in kindList" :value="item.id" :label="item.name" :key="item.id"></el-option>
            </el-select>
          </el-form-item>-->
<!--          <el-form-item label="事件状态:">
            <el-select filterable v-model="searchForm.id" style="width: 140px;" placeholder="请输入事件状态" clearable>
              <el-option v-for="item in sourceList" :value="item.id" :label="item.dictName" :key="item.id"></el-option>
            </el-select>
          </el-form-item>-->
<!--          <el-form-item label="事件编号:">
            &lt;!&ndash; <el-input clearable v-model="searchForm.area" type="number" min="0" placeholder="请输入面积"></el-input> &ndash;&gt;
            <el-input clearable v-model="searchForm.statusIds" style="width: 140px" placeholder="请输入事件编号"></el-input>
          </el-form-item>-->
<!--          <el-form-item style="float: right">
            <el-button icon="el-icon-search" type="primary" style="margin-left: 10px" @click="onSubmit">查询</el-button>
            <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
          </el-form-item>-->
        </el-form>
      </div>
      <div class="contentBody">
        <div class="znvmap" id="map-container"></div>
        <div class="map-hover-06">
          <div class="list-item" :class="{'active':item.active}" v-for="(item,index) in iconList" :key="index" @click="checkStatus(item)">
            <span class="l-circle" :class="item.color"></span>
            <span class="l-label">{{ item.name}}</span>
          </div>
        </div>
        <div class="map-hover-05">
          <div class="m-tools">
            <i class="m-icon el-icon-aim" @click="goCenter"></i>
          </div>
          <div class="m-tools">
            <i class="m-icon el-icon-zoom-in" @click="zoomIn"></i>
            <span class="m-split"></span>
            <i class="m-icon el-icon-zoom-out" @click="zoomOut"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import ZnvVideo from '@/components/znvVideo/Video'
import ZnvMap from '@/components/map/znvMap'
import Axios from '@/utils/request'
import mapIcon from '@/components/map/mapIcon'
export default {
  name: 'perimeterAnalysis',
  data() {
    return {
      sourceList: [],
      infomation: {},
      src: '',
      searchForm: {
        id: ''
      },
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],
      showAdd: false,
      formInfo: {},
      isCode: 0, // 0 编辑 2 新增
      list: [],
      openNum: 0,
      somebodyNum: 0,
      alarmNum: 0,
      notOpenedNum: 0,
      notUsed: 0,
      mapType: '',
      mapState: '',
      iconList: [
        {
          name: '应急救援队伍',
          type: 'firestation',
          color: 'green',
          active: false
        },
        {
          name: '应急专家',
          type: 'specialist',
          color: 'yellow',
          active: false
        },
        {
          name: '避难场所',
          type: 'shelter',
          color: 'red',
          active: false
        },
        {
          name: '应急物资',
          type: 'fxbox',
          color: 'blue',
          active: false
        }
      ],
      eventList: []
    }
  },
  mounted() {
    this.initMap()
    this.queryDataTable()
  },
  components: {
    ZnvVideo
  },
  methods: {
    changeEvent(item) {
      ZnvMap.hideAllMarkers()
      ZnvMap.showLayerByIds(['事件_' + item])
    },
    clearEvent() {
      ZnvMap.hideAllMarkers()
    },
    queryDataTable() {
      ZnvMap.hideAllMarkers()
      this.loading = true
      let url = '/binhuapis/dapeng/event/query-condition?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      Axios.get(url).then((res) => {
        this.eventList = res.data.data.list
      })
    },
    checkStatus(item) {
      this.iconList.forEach(t => {
        if (t.type === item.type) {
          t.active = !t.active
        } else {
          t.active = false
        }
      })
      let keyList = this.iconList.filter(t => t.active).map(t => t.type)
      ZnvMap.hideAllMarkers()
      ZnvMap.showPointByKey([
        {
          key: 'type',
          value: keyList
        }
      ])
      if (this.searchForm.id) {
        ZnvMap.showLayerByIds(['事件_' + this.searchForm.id])
      }
    },
    getSourceList() {
      let url = '/binhuapis/dict/dictTree?parentId=39'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.sourceList = data// .filter((item) => item.dictName === '值班值守')
        }
      })
    },
    goCenter() {
      ZnvMap.gotoPoint([120.284401, 31.527823], 12)
    },
    zoomIn() {
      ZnvMap.zoomIn()
    },
    zoomOut() {
      ZnvMap.zoomOut()
    },
    mapTypeClick(v, t) {
      ZnvMap.hideAllMarkers()
      ZnvMap.showPointByKey([
        {
          key: t,
          value: [v]
        }
      ])
    },
    initMap() {
      // 初始化地图
      ZnvMap.start('map-container', {
        center: {
          lng: 120.284401,
          lat: 31.527823
        },
        resizeEnable: true,
        zoom: 12,
        minZoom: 2
      }).then(() => {
        // 加载点位
        this.loadMapPoints()
      })
    },
    loadMapPoints() {
      // 事件
      this.queryEventPoint({ isHidden: true })
      // 应急救援队伍
      this.queryRescuePoint({ isHidden: true })
      // 应急专家
      this.loadSpecialPoint({ isHidden: true })
      // 避难场所
      this.loadShelterCoordinatesPoint({ isHidden: true })
      // 应急物资仓库
      this.loadEmergencyCoordinatesPoint({ isHidden: true })
    },
    // 事件
    queryEventPoint(opts) {
      let url = '/binhuapis/dapeng/event/query-condition?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      Axios.get(url).then((res) => {
        let pointList = this.queryEventPointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryEventPointProcess(data) {
      return data.map((e, index) => {
        e.type = 'event'
        e.clickType = 'event'
        let temp = {
          id: '事件_' + e.id,
          icon: mapIcon['事件'],
          title: e.eventTitle,
          position: { lng: e.lon, lat: e.lat, height: 100 },
          size: { width: 25, height: 25 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },
    // 避难安置
    loadShelterPoint(opts) {
      Axios.get('/binhuapis/siteManage/query').then((res) => {
        let pointList = this.ShelterProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    getMapIcon(e) {
      let iconString = e.type + '_'
      switch (e.state) {
        case '1':
          this.openNum++
          iconString = iconString + '绿'
          break
        case '2':
          this.somebodyNum++
          iconString = iconString + '黄'
          break
        case '3':
          this.alarmNum++
          iconString = iconString + '红'
          break
        case '4':
          this.notOpenedNum++
          iconString = iconString + '蓝'
          break
        case '5':
          this.notUsed++
          iconString = iconString + '灰'
          break
        default:
          break
      }
      return iconString
    },
    ShelterProcess(data) {
      return data.map((e, index) => {
        let iconInfo = mapIcon[this.getMapIcon(e)]
        e.clickType = e.type
        let temp = {
          id: e.type + '_' + e.id,
          icon: iconInfo,
          title: e.name,
          position: { lng: e.gpsx, lat: e.gpsy },
          size: { width: 42, height: 42 },
          offset: { x: -21, y: -21 },
          extData: e
        }
        return temp
      })
    },
    // 应急救援队伍
    queryRescuePoint(opts) {
      let url = '/binhuapis/teamInfo/queryRescueTeamInfo?isAll=1' + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      Axios.get(url).then((res) => {
        let pointList = this.queryRescuePointProcess(res.data.data.teamList)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    queryRescuePointProcess(data) {
      return data.map((e, index) => {
        e.type = 'firestation'
        e.clickType = '应急救援队伍'
        let temp = {
          id: '应急救援队伍_' + e.ID,
          icon: mapIcon['救援队伍'],
          title: e.TEAM_NAME,
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
      let url = '/binhuapis/basicExpert/getBasicExpertList?isAll=1&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      Axios.get(url).then((res) => {
        let pointList = this.SpecialProcess(res.data.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    SpecialProcess(data) {
      return data.map((e, index) => {
        e.type = 'specialist'
        e.clickType = '应急专家'
        let temp = {
          id: '应急专家_' + e.id,
          icon: mapIcon['专家'],
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
      Axios.get('/binhuapis/siteManage/selectBasicShelter?isAll=1').then((res) => {
        let pointList = this.ShelterCoordinatesProcess(res.data.data.data)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    ShelterCoordinatesProcess(data) {
      return data.map((e, index) => {
        e.type = 'shelter'
        e.clickType = '避难场所'
        let temp = {
          id: '避难场所_' + e.id,
          icon: mapIcon['避难场所'],
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
      Axios.get('/binhuapis/resourceStuff/query?isAll=1'+ '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")).then((res) => {
        let pointList = this.EmergencySuppliesPointProcess(res.data.data.list)
        ZnvMap.loadPointLayer(pointList, opts)
      })
    },
    EmergencySuppliesPointProcess(data) {
      return data.map((e, index) => {
        e.type = 'fxbox'
        e.clickType = '应急物资'
        let temp = {
          id: '应急物资_' + e.id,
          icon: mapIcon['应急物资'],
          title: e.stockstuffNameName,
          position: { lng: e.gpsx, lat: e.gpsy, height: 100 },
          size: { width: 30, height: 34 },
          offset: { x: -15, y: -34 },
          extData: e
        }
        return temp
      })
    },
    // 查询
    onSubmit() {
      this.mapState = ''
      this.mapType = ''
      this.queryDataTable()
    },
    // 重置
    resetSearch() {
      this.searchForm = {
        name: '',
        kind: '',
        address: '',
        area: ''
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.perimeterAnalysis {
  width: 100%;
  height: 100%;
  .main-container {
    width: 100%;
    height: 100%;
    .tools-container {
      ::v-deep .el-form {
        display: flex;
        flex-wrap: nowrap;
        align-items: center;
        .el-form-item {
          display: flex;
          margin-right: 0px;
          align-items: center;
          .el-form-item__label {
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #7f8892;
            line-height: 30px;
          }
          .el-form-item__content {
            .el-select {
              width: 250px !important;
            }
            .el-input {
              width: 250px !important;
              .el-input__inner {
                width: 250px;
                height: 45px;
                background: #ffffff;
                border: 1px solid #e9edf7;
                border-radius: 8px;
              }
            }
          }
        }
      }
    }
    .contentBody {
      position: relative;
      width: 100%;
      height: calc(100% - 60px);
      .znvmap {
        height: 100%;
        position: relative;
        background: #fff;
      }
      .map-hover-01 {
        position: absolute;
        bottom: 18px;
        left: 12px;
        width: 167px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .list-item {
          cursor: pointer;
          display: flex;
          flex-wrap: nowrap;
          align-items: center;
          justify-content: space-between;
          padding-top: 10px;
          padding-bottom: 10px;
          padding-left: 20px;
          padding-right: 20px;
          .l-circle {
            width: 13px;
            height: 13px;
            border-radius: 50%;
          }
          .l-circle.green {
            background: #76e147;
          }
          .l-circle.yellow {
            background: #ffd325;
          }
          .l-circle.red {
            background: #fd614c;
          }
          .l-circle.grey {
            background: #afbdc9;
          }
          .l-circle.blue {
            background: #1e80ff;
          }
          .l-label {
            width: 80px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #8b99b0;
            margin-left: 11px;
          }
          .l-num {
            font-size: 16px;
            font-family: Arial;
            font-weight: 400;
            color: #616161;
            text-align: right;
          }
        }
        .list-item:first-child {
          margin-top: 16px;
        }
        .list-item:last-child {
          margin-bottom: 16px;
        }
        .active {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
        .list-item:hover {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
          .l-num {
            color: #ffffff;
          }
        }
      }
      .map-hover-02 {
        position: absolute;
        top: 18px;
        left: 16px;
        width: 152px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .list-item {
          width: 100%;
          padding-left: 21px;
          padding-right: 10px;
          padding-top: 10px;
          padding-bottom: 10px;
          cursor: pointer;
          .l-icon {
            width: 24px;
            height: 20px;
            color: #8b99b0;
          }
          .l-label {
            margin-left: 10px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #8b99b0;
          }
        }
        .list-item:first-child {
          margin-top: 16px;
        }
        .list-item:last-child {
          margin-bottom: 16px;
        }
        .active {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
        .list-item:hover {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
      }
      .map-hover-03 {
        position: absolute;
        left: calc(50% - 90px);
        top: 30%;
        transform: translate(-50%, -50%);
        width: 419px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        padding-left: 30px;
        padding-top: 31px;
        .m-div-close {
          right: 20px;
          top: 20px;
          position: absolute;
          width: 11px;
          height: 11px;
        }
        .m-div-title {
          font-size: 18px;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #7f8892;
        }
        .m-div-content {
          margin-top: 19px;
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          .item-list {
            margin-bottom: 10px;
            display: flex;
            flex-direction: row;
            flex-wrap: nowrap;
            .label {
              width: 120px;
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #a1aac2;
              text-align: right;
            }
            .value {
              width: calc(100% - 120px);
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #7f8892;
              .v-circle {
                display: inline-block;
                width: 10px;
                height: 10px;
                background: #77c26f;
                border-radius: 50%;
                margin-right: 6px;
              }
              .v-circle.green {
                background: #76e147;
              }
              .v-circle.yellow {
                background: #ffd325;
              }
              .v-circle.red {
                background: #fd614c;
              }
              .v-circle.grey {
                background: #afbdc9;
              }
              .v-circle.blue {
                background: #1e80ff;
              }
            }
          }
          .item-list:last-child {
            margin-bottom: 26px;
          }
        }
      }
      .map-hover-04 {
        position: absolute;
        right: 20px;
        top: 20px;
        width: 444px;
        height: 266px;
        z-index: 99;
        background: #000000;
        .m-div-close {
          right: 20px;
          top: 0px;
          position: absolute;
          width: 11px;
          height: 11px;
          color: #ffffff;
        }
        .m-div-media {
          z-index: 299;
          margin-top: 20px;
          margin-bottom: 20px;
        }
      }
      .map-hover-05 {
        position: absolute;
        right: 19px;
        bottom: 15px;
        .m-tools {
          width: 46px;
          min-height: 46px;
          height: auto;
          background: #ffffff;
          border-radius: 23px;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          margin-bottom: 16px;
          cursor: pointer;
          .m-split {
            width: 26px;
            height: 1px;
            background: #eef0f5;
          }
          .m-icon {
            font-size: 26px;
            color: #616161;
            padding: 10px;
          }
          .m-icon:hover {
            color: rgba(97, 97, 97, 0.7);
          }
        }
      }
      .map-hover-06 {
        position: absolute;
        top: 18px;
        right: 12px;
        width: 167px;
        height: auto;
        background: #ffffff;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        .list-item {
          cursor: pointer;
          display: flex;
          flex-wrap: nowrap;
          align-items: center;
          justify-content: space-between;
          padding-top: 10px;
          padding-bottom: 10px;
          padding-left: 20px;
          padding-right: 20px;
          .l-circle {
            width: 13px;
            height: 13px;
            border-radius: 50%;
          }
          .l-circle.green {
            background: #76e147;
          }
          .l-circle.yellow {
            background: #ffd325;
          }
          .l-circle.red {
            background: #fd614c;
          }
          .l-circle.grey {
            background: #afbdc9;
          }
          .l-circle.blue {
            background: #1e80ff;
          }
          .l-label {
            width: 100px;
            font-size: 16px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #8b99b0;
            margin-left: 11px;
          }
          .l-num {
            font-size: 16px;
            font-family: Arial;
            font-weight: 400;
            color: #616161;
            text-align: right;
          }
        }
        .list-item:first-child {
          margin-top: 16px;
        }
        .list-item:last-child {
          margin-bottom: 16px;
        }
        .active {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
        }
        .list-item:hover {
          background: rgba(102, 102, 102, 0.5);
          .l-icon {
            color: #ffffff;
          }
          .l-label {
            color: #ffffff;
          }
          .l-num {
            color: #ffffff;
          }
        }
      }
    }
  }
}
</style>
