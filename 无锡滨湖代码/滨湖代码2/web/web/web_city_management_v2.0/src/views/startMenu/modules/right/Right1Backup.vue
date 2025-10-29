<template>
  <div class="Right1 module">
    <SmallTitle :titleList="['事件动态']" size="big" />
    <div class="content">
      <div class="content-1" v-for="(item, i) in event" :key="i">
        <div class="con" style="margin-left: 85rem"><span>任务标题：</span> {{ item.taskTitle }}</div>
        <div class="con" style="margin-left: 85rem"><span>下发时间：</span> {{ item.time }}</div>
        <div class="con" style="margin-left: 85rem"><span>任务类型：</span> {{ item.nodeName }}</div>
        <div
          :class="['con', i === 0 ? '' : 'arrowRight', item.status === '1' ? 'finish' : 'undeal']"
          style="padding-left: 85rem; margin-left: 0"
        >
          <span>处置反馈：</span> {{ item.status === '0' ? '未处置' : '已处置' }}
        </div>
        <div class="con" style="margin-left: 85rem"><span>反馈部门：</span> {{ item.department }}</div>
        <div class="con" v-if="item.feedback" style="margin-left: 85rem">
          <span>反馈时间：</span> {{ item.dealTime }}
        </div>
        <div class="con" v-if="item.feedback" style="margin-left: 85rem">
          <span>反馈内容：</span> {{ item.feedback }}
        </div>
        <div class="con" v-if="item.feedback" style="margin-left: 85rem">
          <span>反馈内容：</span> {{ item.feedback }}
        </div>
        <div class="con" v-if="item.filePicture || item.fileVideo || item.fileVoice" style="margin-left: 85rem">
          <span>处置附件：</span>
          <div style="display: inline-block">
            <ExtraFileSegments :images="item.filePicture" :videos="item.fileVideo" :doucs="item.fileVoice" />
          </div>
        </div>

        <!-- <div class="con" style="margin-left: 85rem">{{ ToText(item.description) }}</div> -->
        <!-- <div class="more moreDown" v-if="!item.isShowAll && item.isRoll && item.setEvent" @click="toDown(item)">
          展开>>
        </div>
        <div class="more moreUp" v-if="!item.isShowAll && !item.isRoll && item.setEvent" @click="toUp(item)">
          收起{{ geshi }}
        </div> -->
      </div>
      <!-- <div class="weixin" @click="handleVideo">
        <img class="command-icon" src="@/assets/left/视频会商.png" alt="" />
        <span style="margin: 14rem 78rem 0">视频会商>></span>
      </div> -->
    </div>
    <VideoConferencingDialog
      :dialogVisibleFlag.sync="videoVisible"
      :meetingMini="meetingMini"
      :groupList="groupList"
      :allList="allList"
      :modelList="modelList"
    />
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import { queryFlow } from '@/api/info'
import axios from '@/utils/request'
import VideoConferencingDialog from '@/components/VideoConferencingDialogTest.vue'
import ExtraFileSegments from './ExtraFileSegments'

export default {
  name: 'Right1',
  components: { SmallTitle, VideoConferencingDialog, ExtraFileSegments },
  props: ['info'],
  data() {
    return {
      event: [],
      precinct: this.$route.query.eventId,
      meetingMini: false,
      geshi: '<<',
      allList: [],
      groupList: [],
      modelList: [],
      tabIndex: 1,
      timer: null,
      activeAble: false,
      queryParams: [],
      eventOther: [],
      videoMeetingVisible: false,
      videoMeetingData: {},
      judgeList: [],
      type: {},
      videoVisible: false
    }
  },
  watch: {
    '$store.getters.eventInfo': {
      handler(v) {
        this.type = v
      },
      deep: true
    },
    info: {
      handler(val) {
        this.getEventCondition(this.info.eventId)
      },
      deep: true
    }
  },
  mounted() {
    if (this.$store.getters.eventId.id === 'RE0001') {
      this.event = sysConfig.eventDemo
    } else {
      // this.getEventCondition(this.info.eventId)
    }
    this.$root.Bus.$on('globalTimeIntervalRefreshEvent', () => {
      this.getEventCondition(this.info.eventId)
    })
  },
  methods: {
    getEventCondition(eventId) {
      let url
      if (eventId !== undefined) {
        url = `/community/dapeng/eventTask/query-condition?eventId=${eventId}`
      } else {
        url = '/community/dapeng/eventTask/query-condition'
      }
      axios.get(url).then((r) => {
        this.event = r.data.data.list.map((t) => {
          return {
            id: t.id,
            time: t.issueTime,
            background: 'rgb(255, 66, 0)',
            border: '1rem solid rgb(252, 150, 114)',
            description: t.taskContent,
            ...t
          }
        })
      })
    },
    handleVideo() {
      this.videoVisible = true
    },
    sortList1() {
      let e = {
        url: '',
        title: '事件详情'
      }
      if (this.type && this.type.flowSource) {
        if (this.type.flowSource === '值班值守系统') {
          e.url =
            'http://10.1.50.123/zhrc_portal/eventlog/?riskId=' + this.$route.query.eventId + '&type=duty&height=800'
        } else {
          e.url = 'http://10.1.50.123/zhrc_portal/eventInfo/?flowNo=' + this.$route.query.eventId
        }
      } else {
        e.url = 'http://10.1.50.123/zhrc_portal/eventlog/?riskId=' + this.$route.query.eventId + '&type=duty&height=800'
      }
      this.videoMeetingVisible = true
      this.videoMeetingData = e
    },
    sortList(e) {
      this.activeAble = !e
      if (this.activeAble) {
        this.event = this.event.sort(function (a, b) {
          return a.time >= b.time ? 1 : -1
        })
      } else {
        this.event = this.event.sort(function (a, b) {
          return a.time < b.time ? 1 : -1
        })
      }
    },
    switchTab(index) {
      this.tabIndex = index
    },
    ToText(html) {
      return html
        .replace(/<(style|script|iframe)[^>]*?>[\s\S]+?<\/\1\s*>/gi, '')
        .replace(/<[^>]+?>/g, '')
        .replace(/\s+/g, ' ')
        .replace(/ /g, ' ')
        .replace(/>/g, ' ')
    },
    queryFlow() {
      // this.event = []
      let obj = { mapVo: { flowNo: this.$store.getters.eventId.id, actionType: '' } }
      queryFlow(obj).then((res) => {
        let data = res.data.data.data || []
        if (res.data.data.code === 200) {
          if (data && data['工单操作'] && data['工单操作'].rows.length > 0) {
            this.eventOther = []
            let row = data['工单操作'].rows
            this.event = this.eventThings(row)
            this.event.forEach((item) => {
              item.setEvent = 1
              if (item.content && item.content.length > 50) {
                item.description = item.content.substr(0, 50) + '......'
                item.isShowAll = false
              } else {
                item.description = item.content
                item.isShowAll = true
              }
              if (this.judgeList.length > 0) {
                this.judgeList.forEach((r) => {
                  if (item.id === r) {
                    item.description = item.content
                    item.isShowAll = false
                    item.isRoll = false
                  }
                })
              }
            })
            this.eventOther = JSON.parse(JSON.stringify(this.event))
          } else {
            this.queryFlows(this.$store.getters.eventId.id)
          }
        } else {
          if (this.eventOther.length > 0) {
            this.event = this.eventOther
          } else {
            this.queryFlows(this.$store.getters.eventId.id)
          }
        }
      })
    },
    queryFlows(eventId) {
      let url = 'apis/eventDeal/queryFlow'
      axios.get(url).then((r) => {
        if (r.data && r.data.code === 200) {
          let data = r.data.data.eventDealFlow
          if (data.length > 0) {
            let arr = data.filter((r) => r.status === '0')
            arr.sort(function (a, b) {
              return a.recordTime < b.recordTime ? 1 : -1
            })
            arr.forEach((item) => {
              item.nodeName = item.eventStatus === '1' ? '事件上报' : item.eventStatus === '2' ? '事件续报' : '事件终报'
            })
            let list = arr.map((item) => {
              return {
                id: item.id,
                nodeName: item.nodeName,
                background:
                  !item.nodeName || item.nodeName === 'null' || item.nodeName === '处置中' ? '#409eff' : '#16b866',
                color: !item.nodeName || item.nodeName === 'null' || item.nodeName === '处置中' ? '#409eff' : '#01e672',
                border: '1rem solid rgb(129 182 236)',
                time: item.recordTime.substr(5, 11),
                name: item.unitName,
                description: '【' + item.nodeName + '】' + item.dealContent,
                isShowAll: true,
                isRoll: true
              }
            })
            this.event = list
          }
        }
      })
    },
    unique(arr) {
      for (let i = 0; i < arr.length - 1; i++) {
        for (let j = i + 1; j < arr.length; j++) {
          if (arr[i] === arr[j]) {
            arr.splice(j, 1)
            j--
          }
        }
      }
      return arr
    },
    // 点击按钮展开
    toDown(e) {
      e.description = ''
      e.description = e.content
      e.isRoll = false
      this.judgeList.push(e.id)
    },
    // 点击按钮收起
    toUp(e) {
      e.description = ''
      e.description = e.content.slice(0, 50) + '......'
      e.isRoll = true
      if (this.judgeList.length > 0) {
        this.judgeList = this.judgeList.filter((item) => item !== e.id)
      }
    },
    eventThings(data) {
      let eventThings = []
      // 数据按时间从大到小展示
      data.sort(function (a, b) {
        return a.optDate < b.optDate ? 1 : -1
      })
      eventThings = data.map((item) => {
        // item.orderNodeId = item.operationType
        // if (item.operationType === '项目任务' && item.projectTaskInfo.type === 'claim') {
        //   item.orderNodeId = '任务分派'
        // } else if (item.operationType === '项目任务' && item.projectTaskInfo.type === 'done') {
        //   item.orderNodeId = '处置完成'
        // } else if (item.operationType === '项目任务' && item.projectTaskInfo.type === 'userAddLog') {
        //   item.orderNodeId = '工作记录'
        // }
        return {
          id: item.esId,
          state: item.operationType,
          orderNodeId: item.orderNodeId,
          background: !item.nodeName || item.nodeName === 'null' || item.nodeName === '处置中' ? '#409eff' : '#16b866',
          color: !item.nodeName || item.nodeName === 'null' || item.nodeName === '处置中' ? '#409eff' : '#01e672',
          border: '1rem solid rgb(129 182 236)',
          time: item.optDate.substr(5, 11),
          name: item.createUser,
          flowNo: item.flowNo,
          userId: item.userId,
          content: '【' + item.nodeName + '】' + item.operationContent,
          projectTaskInfo: item.projectTaskInfo ? item.projectTaskInfo : '',
          isShowAll: true,
          isRoll: true,
          description: ''
        }
      })
      return eventThings
    }
  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
  }
}
</script>

<style lang="scss">
.Right1 {
  width: 2840rem;
  // height: 850rem;
  position: absolute;
  top: 820rem;
  left: 0;
  .img {
    position: absolute;
    top: 30rem;
    right: 0rem;
    width: 140rem;
    height: 140rem;
    cursor: pointer;
  }
  .untitle {
    width: 100%;
    height: 100%;
    line-height: 843rem;
    text-align: center;
    background: url('../../../../assets/right/处置进程.png') no-repeat;
    background-size: contain;
  }
  .content {
    width: 2840rem;
    height: 800rem;
    // background: url('../../../../assets/right/处置进程.png') no-repeat;
    // background-size: 98% 100%;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    display: flex;
    flex-wrap: nowrap;
    overflow-x: scroll;
    .content-1 {
      margin-bottom: 55rem;
      width: 946rem;
      height: 800rem;
      flex-shrink: 0;
      .more {
        font-size: 48rem;
        font-weight: 400;
        cursor: pointer;
        color: #00bbff;
        text-align: right;
      }

      .moreDown {
        position: absolute;
        right: 0;
        bottom: -20rem;
      }
      .circle {
        width: 40rem;
        height: 40rem;
        background: #feb021;
        border: 2rem solid #ffffff;
        border-radius: 50%;
        margin-right: 45rem;
        margin-top: 6rem;
      }
      .line {
        width: 2rem;
        height: calc(100% + 45rem);
        background: #ffffff;
        opacity: 0.2;
        position: absolute;
        left: 20rem;
        top: 20rem;
      }
      .title {
        font-size: 48rem;
        font-family: SourceHanSansCN;
        font-weight: 500;
        color: #9ab1cb;
        margin-bottom: 50rem;
      }
      .arrowRight {
        background: url('../../../../assets/right/向右箭头@2x.png') no-repeat;
        background-size: 56rem 116rem;
        margin-left: 0 !important;
      }
      .finish {
        color: #18cc30 !important;
      }
      .undeal {
        color: #ffa21f !important;
      }
      .con {
        .el-button--mini {
          padding: 10rem 20rem;
          font-size: 70rem;
        }
        span {
          font-size: 56rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #68c6f4;
        }
        font-size: 56rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ffffff;
        line-height: 100rem;
        margin-left: 30rem;
      }
    }
    .weixin {
      width: 590rem;
      height: 80rem;
      font-size: 24rem;
      font-family: SourceHanSansCN;
      font-weight: 500;
      color: #93d0ff;
      line-height: 45rem;
      text-align: center;
      cursor: pointer;
      position: absolute;
      bottom: 8rem;
      background: url('../../../../assets/left/模块按钮bg.png') no-repeat;
      background-size: contain;
      margin-left: -6rem;
      display: flex;
      img {
        margin-left: 45rem;
        margin-top: 2rem;
        width: 225rem;
        height: 70rem;
      }
    }
  }
}
</style>
