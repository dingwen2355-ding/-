<template>
  <div class="ShCityManagementStaff">
    <div class="staff-header">
      <div class="staff-title">
        <strong v-if="!precinctId">无锡一网统管-融合指挥数据管理</strong>
        <strong v-else>{{ areaNameList }}一网统管-融合指挥数据管理</strong>
      </div>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        style="padding-left: 68px;"
        background-color="rgb(15 31 49)"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="1">事件配置</el-menu-item>
        <el-menu-item index="2">全局配置</el-menu-item>
        <el-menu-item index="3">资源管理</el-menu-item>
        <!-- <el-menu-item index="4">操作日志</el-menu-item> -->
        <el-menu-item index="5">会商管理</el-menu-item>
      </el-menu>
      <div v-if="activeShow !== '10' && activeShow !== '11' && activeShow !== '12' && activeShow !== '13' && activeShow !== '14' && activeShow !== '15'">
        <div class="staff-select"  v-if="activeShow !== '1'">
        <label>事件名称</label>
        <el-select v-model="currentEvent" placeholder="请选择事件" :popper-class="'event-select'" filterable>
          <el-option v-for="item in eventList" :key="item.event_id" :label="item.event_title" :value="item.event_id">
          </el-option>
        </el-select>
        <!-- <div class="staff-btn-warp"> -->
          <!-- <div class="blue-btn" @click="resetEventConfig">重置</div> -->
          <!-- <div class="blue-btn" @click="NewEventInfo">新增</div>
        </div> -->
      </div>
      <div class="staff-select"  v-else>
        <label>事件类型</label>
        <el-select v-model="currentEvent1" placeholder="请选择事件类型" @change="unitNameChange(currentEvent1)" filterable>
          <el-option
            v-for="item in unitOptions"
              :key="item.id"
              :label="item.typeName"
              :value="item.typeName">
          </el-option>
            </el-select>
        </div>
      </div>
    </div>
    <div class="staff-content" :style="{ 'margin-top' : parseInt(activeShow) > 9 ? '0px' : '85px'}">
      <!-- <div class="iframe-warp">
        <iframe height="1080" width="3840" frameborder="0"
          :src="iframeSrc + $store.getters.eventId.id + '&serverType=1'" :key="iframeKey"></iframe>
      </div> -->
      <div class="module-warp">
        <div class="module-list">
          <ul>
            <div v-if="activeSelect">
            <!-- <li :class="{'active': activeShow === '2'}" @click="clickCurrent('2')">到场力量</li> -->
            <!-- <li :class="{'active': activeShow === '3'}" @click="clickCurrent('3')">舆情监控</li> -->
            <li :class="{'active': activeShow === '4'}" @click="clickCurrent('4')">领导指示/要求</li>
            <!-- <li :class="{'active': activeShow === '5'}" @click="clickCurrent('5')">风险预警</li> -->
            <!-- <li :class="{'active': activeShow === '6'}" @click="clickCurrent('6')">决策辅助</li> -->
            <li :class="{'active': activeShow === '7'}" @click="clickCurrent('7')">视频配置</li>
            <!-- <li :class="{'active': activeShow === '8'}" @click="clickCurrent('8')">单位/人员排序</li> -->
            <li :class="{'active': activeShow === '9'}" @click="clickCurrent('9')">应急预案</li>
            </div>
            <div v-else>
            <li v-if="layer === ''" :class="{'active': activeShow === '10'}" @click="clickCurrent('10')">部门管理</li>
            <li v-if="layer === ''" :class="{'active': activeShow === '11'}" @click="clickCurrent('11')">人员管理</li>
            <li v-if="layer === ''" :class="{'active': activeShow === '12'}" @click="clickCurrent('12')">预案管理</li>
            <li v-if="layer === ''" :class="{'active': activeShow === '1'}" @click="clickCurrent('1')">指挥体系</li>
            <li v-if="layer === '1'" :class="{'active': activeShow === '13'}" @click="clickCurrent('13')">资源管理</li>
            <!-- <li v-if="layer === '2'" :class="{'active': activeShow === '14'}" @click="clickCurrent('14')">操作日志</li> -->
            <li v-if="layer === '3'" :class="{'active': activeShow === '15'}" @click="clickCurrent('15')">会商管理</li>
            </div>
          </ul>
        </div>
        <div class="module-content">
          <OrganizationalModule v-if="activeShow === '1'" />
          <EventsModule v-if="activeShow === '2'" />
          <!-- <MonitorPublicModule v-if="activeShow === '3'" /> -->
          <LedIndicatorModule v-if="activeShow === '4'" />
          <RiskWarningModule v-if="activeShow === '5'" />
          <DecisionAidsModule v-if="activeShow === '6'" />
          <VideoInspectionModule v-if="activeShow === '7'" />
          <MemberSort v-if="activeShow === '8'" />
          <EmergencyPlan v-if="activeShow === '9'" />
          <Department v-if="activeShow === '10'" />
          <Personment v-if="activeShow === '11'" />
          <Emergencyment v-if="activeShow === '12'" />
          <resourceSearch v-if="activeShow === '13'" />
          <operationLog v-if="activeShow === '14'" />
          <videoMeeting v-if="activeShow === '15'" />
        </div>
      </div>
    </div>
    <add-led-dialog></add-led-dialog>
    <add-implementation-dialog></add-implementation-dialog>
    <add-risk-dialog></add-risk-dialog>
    <add-event-dialog></add-event-dialog>
    <AddPowerDialog />
    <query-user-dialog></query-user-dialog>
    <AddNameDialog></AddNameDialog>
  </div>
</template>

<script>
import OrganizationalModule from './OrganizationalModule'
import EventsModule from './EventsModule'
// import MonitorPublicModule from './MonitorPublicModule'
import LedIndicatorModule from './LedIndicatorModule'
import RiskWarningModule from './RiskWarningModule'
import DecisionAidsModule from './DecisionAidsModule'
import VideoInspectionModule from './VideoInspectionModule'
import Axios from '@/utils/request'
import AddLedDialog from './AddLedDialog'
import AddImplementationDialog from './AddImplementationDialog'
import AddRiskDialog from './AddRiskDialog'
import AddEventDialog from './AddEventDialog'
import AddPowerDialog from './AddPowerDialog'
import AddNameDialog from './AddNameDialog'
import MemberSort from './MemberSort'
import QueryUserDialog from './QueryUserDialog'
import websocket from '../../utils/websocket'
import EmergencyPlan from './EmergencyPlan'
import Department from './Department'
import Personment from './Personment'
import Emergencyment from './Emergencyment'
import resourceSearch from './resourceSearch'
import operationLog from './operationLog'
import videoMeeting from './videoMeeting'

export default {
  name: 'ShCityManagementStaff',
  components: {
    QueryUserDialog,
    MemberSort,
    AddEventDialog,
    AddRiskDialog,
    AddImplementationDialog,
    AddLedDialog,
    OrganizationalModule,
    EventsModule,
    // MonitorPublicModule,
    LedIndicatorModule,
    RiskWarningModule,
    DecisionAidsModule,
    VideoInspectionModule,
    AddPowerDialog,
    EmergencyPlan,
    AddNameDialog,
    Emergencyment,
    Personment,
    Department,
    resourceSearch,
    operationLog,
    videoMeeting
  },
  data () {
    return {
      eventList: [],
      currentEvent: '',
      currentEvent1: '',
      eventType: '',
      unitOptions: [],
      activeShow: '4',
      iframeSrc: 'http://10.89.1.92:8084/#/ShCityManagement?eventId=',
      // iframeSrc: 'http://127.0.0.1:8083/#/ShCityManagement?eventId=',
      iframeKey: +new Date(),
      precinctId: this.$store.getters.precinctId,
      precinctName: this.$store.getters.precinctName,
      areaNameList: '',
      showEvent: '',
      activeIndex: '1',
      layer: '0',
      activeSelect: true,
      area: sysConfig.area
    }
  },
  mounted () {
    this.$root.eventBus.$on('refreshIframe', () => {
      this.iframeKey = +new Date()
    })
    if (!this.precinctName) {
      this.getAreaInfo()
    } else {
      if (this.precinctId) {
        this.getAreaInfo()
      } else {
        this.areaNameList = ''
        this.areaNameList = this.precinctName
      }
    }
    this.$nextTick(() => {
      this.getEventList()
      this.queryUnit()
      if (window.location.href.split('?').length > 1) {
        let flag = window.sessionStorage.getItem('flag')
        if (!flag) {
          window.location.reload()
          window.sessionStorage.setItem('flag', 'true')
        }
        this.getEventLists()
      }
    })
  },
  watch: {
    currentEvent: {
      handler (v) {
        if (!v) return
        console.log(v)
        this.$nextTick(() => {
          let eventId = {
            id: v,
            timer: +new Date()
          }
          this.$store.dispatch('app/setEventId', eventId)
          this.iframeKey = +new Date()
        })
      },
      immediate: true
    },
    currentEvent1: {
      handler (v) {
        if (!v) return
        this.$nextTick(() => {
          let eventId = {
            id: this.showEvent,
            timer: +new Date()
          }
          this.$store.dispatch('app/setEventId', eventId)
          this.iframeKey = +new Date()
        })
      },
      immediate: true
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      if (key === '1') {
        this.activeShow = '4'
        this.layer = ''
        this.activeSelect = true
      } else if (key === '2') {
        this.activeShow = '10'
        this.layer = ''
        this.activeSelect = false
      } else if (key === '3') {
        this.activeShow = '13'
        this.layer = '1'
        this.activeSelect = false
      } else if (key === '4') {
        this.activeShow = '14'
        this.layer = '2'
        this.activeSelect = false
      } else if (key === '5') {
        this.activeShow = '15'
        this.layer = '3'
        this.activeSelect = false
      }
    },
    getEventLists() {
      let url = '/apis/eventDeal/eventInfo'
      let selfHref = window.location.href.split('?')
      if (selfHref.length > 1) {
        let precinect = selfHref[1].split('&')
        if (precinect.length > 1) {
          url = url + '?' + precinect[0] + '&' + precinect[1]
        } else {
          url = url + '?' + precinect[0]
        }
      } else {
        url = url + '?eventId=fb983c5b-3d67-433d-9722-ab7deef7e4c8'
      }
      Axios.get(url).then((res) => {
        if (res.data.code === 200 && res.data.data.length > 0) {
          this.currentEvent = res.data.data[0].eventId
          this.currentEvent1 = res.data.data[0].eventType
          this.showEvent = res.data.data[0].eventId
          this.unitNameChange(this.currentEvent1)
        }
      })
    },
    unitNameChange(e) {
      this.unitOptions.forEach(item => {
        if (e === item.typeName) {
          this.eventType = item.id
          sysConfig.defaultPrecinct.eventType = this.eventType
          this.$root.eventBus.$emit('showEventType', this.eventType)
        }
      })
    },
    clickCurrent (type) {
      this.activeShow = type
    },
    queryUnit() {
      const url = '/apis/manage/queryEventTypeList'
      Axios.get(url).then(r => {
        this.unitOptions = r.data.data
      })
    },
    getAreaInfo() {
      this.areaNameList = ''
      this.area.forEach(item => {
        if (item.id === this.precinctId) {
          this.areaNameList = item.name
        }
      })
    },
    getEventList () {
      let type = this.$route.query.isFalsity || ''
      let url = ''
      if (type) {
        url = '/apis/Command/eventInfoList?isFalsity=' + type
      } else {
        url = '/apis/Command/eventInfoList'
      }
      Axios.get(url).then(res => {
        let data = res.data.data
        this.eventList = data || []
      })
    },
    pushEventInfo () {
      if (!this.$store.getters.eventId.id) return
      let url =
        '/websocketApis/sync/pushEventInfo?eventId=' +
        this.$store.getters.eventId.id
      Axios.get(url).then(res => {
        let data = res.data
        console.log(data)
        if (data.code === 200) {
          this.$message({
            message: '推送成功！',
            type: 'success'
          })
        } else {
          this.$message({
            message: '推送失败！',
            type: 'warning'
          })
        }
      })
    },
    resetEventConfig () {
      if (!this.$store.getters.eventId.id) return
      let url =
        '/websocketApis/sync/resetEventConfig?eventId=' +
        this.$store.getters.eventId.id
      Axios.get(url).then(res => {
        let data = res.data
        console.log(data)
        if (data.code === 200) {
          this.$message({
            message: '重置成功！',
            type: 'success'
          })
          let eventId = {
            id: this.$store.getters.eventId.id,
            timer: +new Date()
          }
          this.$store.dispatch('app/setEventId', eventId)
          this.iframeKey = +new Date()
        } else {
          this.$message({
            message: '重置失败！',
            type: 'warning'
          })
        }
      })
    },
    NewEventInfo() {
      this.$root.eventBus.$emit('showAddNameDialog', this.$store.getters.eventId.id)
    }
  },
  created () {
    websocket.subscribe('spare')
    websocket.addCallback('spare', () => {
      let eventId = { id: this.$store.getters.eventId.id, timer: +new Date() }
      this.$store.dispatch('app/setEventId', eventId)
      this.$root.eventBus.$emit('refreshIframe')
    })
  },
  destroyed() {
    window.sessionStorage.removeItem('flag')
  }
}
</script>

<style lang="scss">
.ShCityManagementStaff {
  width: 100%;
  height: 100%;
  position: relative;
  .el-menu {
    top: 237px;
  }
  .el-menu-item {
    font-size: 40px;
  }
  .el-menu--horizontal>.el-menu-item {
    height: 100px;
    line-height: 100px;
  }

  .staff-header {
    width: 100%;
    position: relative;
    height: 418px;

    .staff-title {
      position: fixed;
      width: 100%;
      top: 0;
      left: 0;
      height: 238px;
      line-height: 238px;
      z-index: 999;
      overflow: hidden;
      background-image: url("../../assets/global/title_bg@2x.png");
      background-size: 100% 100%;

      strong {
        margin-left: 80px;
        font-size: 72px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: rgba(255, 255, 255, 1);
        line-height: 72px;
        text-shadow: 0px 8px 10px rgba(255, 255, 255, 0.7);

        background: linear-gradient(
          0deg,
          rgba(255, 255, 255, 1) 0.146484375%,
          rgba(90, 134, 188, 1) 99.0478515625%
        );
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
      }
    }

    .staff-select {
      top: 238px;
      height: 180px;
      text-align: center;
      padding: 50px 0;
      line-height: 80px;
      position: relative;

      label {
        margin-right: 20px;
      }

      .el-select {
        width: 1216px;
      }

      .staff-btn-warp {
        position: absolute;
        top: 50px;
        right: 20px;

        .blue-btn {
          margin: 0 34px;
        }
      }
    }
  }

  .staff-content {
    width: 100%;
    height: calc(100% - 418px);
    overflow-x: hidden;
    overflow-y: auto;

    &.staff-content::-webkit-scrollbar {
      display: none;
    }

    .iframe-warp {
      position: relative;
      padding: 60px;
      margin: 20px;
      background: rgba(236, 236, 236, 1);
      border: 2px solid rgba(214, 214, 214, 1);

      iframe {
        width: 100%;
      }
    }

    .module-warp {
      width: 100%;
      // height: 1500px;
      padding: 0 20px;
      position: relative;

      .module-list {
        width: 100%;
        height: 102px;
        border-bottom: 2px solid rgba(214, 214, 214, 1);
        // background-color: red;
        ul {
          width: 100%;
          height: 100px;

          li {
            line-height: 100px;
            float: left;
            width: 316px;
            height: 100px;
            text-align: center;
            font-size: 44px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: rgba(91, 91, 91, 1);
            cursor: pointer;
            /*background-image: url("../../assets/global/chioce_un.png");*/
            background-repeat: no-repeat;
            background-size: 100% 100%;

            &:hover {
              color: #0c99db;
            }

            &.active {
              background-image: url("../../assets/global/chioce@2x.png");
              background-repeat: no-repeat;
              background-size: 100% 100%;
              color: #ffffff;

              &:hover {
                color: #fff;
              }
            }
          }
        }
      }

      .module-content {
        width: 100%;
        position: relative;
      }
    }
  }
}
</style>
