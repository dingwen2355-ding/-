<template>
  <div class="Center">
    <div class="center-body">
      <MapView v-show="mapType === '2D'" />
      <Map3D v-show="mapType === '3D'" />
      <center-tools></center-tools>
      <Bottom @changeMapType="handleChangeMapType" />
      <VideoDialog />
      <FusionVideo v-if="$store.getters.fusionVideo"></FusionVideo>
      <div class="acceptEventsOnTheSameDay">
        <span class="title">接受事件</span>
        <span class="title">{{ acceptEventsOnTheSameDayNum }}</span>
      </div>
      <!-- <div class="handlingEventsOnTheSameDay"> -->
      <div class="handlingEventsOnTheSameDayNew">
        <span class="title">处理事件</span>
        <span class="title">{{ handlingEventsOnTheSameDayNum }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import Bottom from './center/bottom'
import MapView from '@/components/map/MapView'
import Map3D from './map/Map3D'
import CenterTools from './center/centerTools'
import VideoDialog from '@/components/map/mapDialog/VideoDialog'
import FusionVideo from '@/views/startMenu/modules/dialog/FusionVideo.vue'
import axios from '@/utils/request';
import moment from 'moment'
export default {
  components: {
    MapView,
    Bottom,
    Map3D,
    VideoDialog,
    FusionVideo,
    CenterTools
  },
  data() {
    return {
      mapType: '2D',
      handlingEventsOnTheSameDayNum: 0,
      acceptEventsOnTheSameDayNum: 0,
      eventList: [
        {
          name: '12345',
          flowSource: '',
        },
        {
          name: '主动发现',
          flowSource: '主动发现',
        },
        {
          name: '城管',
          flowSource: '城管事件',
        },
        {
          name: '智能发现',
          flowSource: '告警事件',
        },
        {
          name: '文明城市创建',
          flowSource: '文明城市',
        },
        {
          name: '被动发现',
          flowSource: '基层吹哨',
        }
      ],
    }
  },
  mounted() {
    // let startTime = new Date().getFullYear() + '-' + `${new Date().getMonth() + 1}` + '-' + '01' + ' ' + '00:00:00'
    // let endTime = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
    let startTime = moment().format('YYYY-MM-DD 00:00:00')
    let endTime = moment().format('YYYY-MM-DD HH:mm:ss')
    this.getSourceDivision(startTime, endTime)
  },
  methods: {
    handleChangeMapType(v) {
      this.mapType = v
    },
    getSourceDivision(startTime, endTime) {
      let url = '/apis/eventJoint/queryEventStatisticsMap?'

      axios.get(url).then(res => {
        if (res.data.code === 200) {
          let data = res.data.data
          this.handlingEventsOnTheSameDayNum = 0
          this.acceptEventsOnTheSameDayNum = 0
          data.forEach(item => {
            // 判断item.flowSource在eventList中是否存在
            if (this.eventList.find(e => e.flowSource === item.flowSource)) {
              this.handlingEventsOnTheSameDayNum += item.endCount
              this.acceptEventsOnTheSameDayNum += item.totalCount
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
.Center {
  position: absolute;
  position: absolute;
  top: 0;
  left: 2360rem; //原值为4260
  width: 10485rem; //原值为7185
  height: 100%;

  .center-body {
    height: 100%;
  }
  .acceptEventsOnTheSameDay {
    background-image: url('../../../assets/center/当日接受事件@2x.png');
    background-size: 100% 100%;
    position: absolute;
    top: 453rem;
    left: 2389rem;
    height: 226rem;
    width: 1234rem;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding-right: 96rem;
    .title {
      font-size: 134rem;
      font-family: 'YouShe';
      font-weight: 400;
      color: #65bffc;
      // line-height: 20px;
      background: linear-gradient(180deg, #ffffff 0%, #68c8ff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      padding-left: 100rem;
    }
  }
  .handlingEventsOnTheSameDayNew {
    background-image: url('../../../assets/center/当日接受事件@2x.png');
    background-size: 100% 100%;
    position: absolute;
    top: 697rem;
    left: 2389rem;
    height: 226rem;
    width: 1234rem;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding-right: 96rem;
    .title {
      font-size: 134rem;
      font-family: 'YouShe';
      font-weight: 400;
      color: #65bffc;
      // line-height: 20px;
      background: linear-gradient(180deg, #ffffff 0%, #68c8ff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      padding-left: 100rem;
    }
  }
  .handlingEventsOnTheSameDay {
    background-image: url('../../../assets/center/当日处置事件@2x.png');
    background-size: 100% 100%;
    position: absolute;
    top: 453rem;
    right: 3289rem;
    height: 226rem;
    width: 1234rem;
    display: flex;
    align-items: center;
    .title {
      font-size: 134rem;
      font-family: 'YouShe';
      font-weight: 400;
      color: #65bffc;
      // line-height: 20px;
      background: linear-gradient(180deg, #ffffff 0%, #68c8ff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      padding-left: 100rem;
    }
  }
}
</style>
