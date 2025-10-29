<template>
  <div class="Right2To1 module">
    <SmallTitle :titleList="['指挥决策']" size="big"/>
    <div class="rongheVideo" @click="handleVideo"><span class="circle"></span>视频会商</div>
    <span class="circle1" v-show="meetingMini">1</span>
    <div class="content">
      <div class="leader">
        <img src="../../../../assets/global/title.png" alt=""/>
        <ul>
          <li
            v-for="(item, index) in title"
            :key="index"
            :class="{ active: index === activeIndex }"
            @click="handleClick(index)"
          >
            {{ item }}
          </li>
        </ul>
      </div>
      <div class="pageall">
        <div v-if="leadershipList.length === 0 && !precinct" class="untitle">
          <img src="../../../../assets/global/暂无事件.png" alt=""/>
        </div>
        <div v-else class="body_content" style="padding: 0 30rem">
          <div class="content" v-for="(item, index) in eventList" :key="index">
            <div style="margin-bottom: 65rem">
              <span style="font-weight: 600">最新任务</span>
              <span class="more" @click="showTaskAssignVisiable=true">更多>></span><!--@click="more"-->
              <img class="add1" @click="showVisible2=true" src="../../../../assets/global/新增.png" alt=""/>
            </div>
            <div>
              <img
                style="width: 55rem; height: 52rem; margin-right: 30rem"
                src="../../../../assets/global/时间.png"
                alt=""
              />
              <span
                v-if="item.issueTime"
                style="color: #9ab1cb; font-size: 48rem; vertical-align: top; line-height: 70rem"
              >{{ item.issueTime }}</span
              >
            </div>
            <div class="content_s">
              <span
              ><span style="font-weight: 500">任务内容：</span>{{ item.taskContent }}</span
              >
            </div>
          </div>
        </div>
      </div>
    </div>
    <showLeadersDialog :dialogVisibleFlag.sync="showLeadersVisible"/>
    <showLookDialog :dialogVisibleFlag.sync="showLookVisible" :showLeadersList="showLeadersList"/>
    <showViewDialog :dialogVisibleFlag.sync="showViewVisible" :depart="options"/>
    <videoMeetingDialog :dialogVisibleFlag.sync="videoMeetingVisible" :videoMeetingData="videoMeetingData"/>
    <viewDetailDialog :dialogVisibleFlag.sync="showViewlist" :depart="options" :page="page"/>
    <TaskAssignDialog :dialogVisibleFlag.sync="showTaskAssignVisiable" :infomation="infomation"/>
    <VideoConferencingDialogTest :dialog-visible-flag.sync="videoVisible" :meetingMini="meetingMini" :group-list="groupList" :all-list="allList" :model-list="modelList"/>
    <NewInstructionsDialog :dialog-visible-flag.sync="showVisible2"></NewInstructionsDialog>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import Axios from '@/utils/request'
import showLeadersDialog from '../dialog/showLeadersDialog.vue'
import ZnvVideo from '@/components/znvVideo/Video.vue'
import showLookDialog from '../dialog/showLookDialog.vue'
import showViewDialog from '../dialog/showViewDialog.vue'
// import VideoConferencingDialog from '@/components/VideoConferencingDialog.vue'
// import VideoConferencingDialog from '@/components/VideoConferencingDialogTest.vue'
import videoMeetingDialog from '../dialog/videoMeetingDialog.vue'
import viewDetailDialog from '../dialog/viewDetailDialog.vue'
import TaskAssignDialog from '../dialog/TaskAssignDialog.vue'
import VideoConferencingDialogTest from "../../../../components/VideoConferencingDialogTest";
import moment from 'moment'
import NewInstructionsDialog from "../../../../components/NewInstructionsDialog";
import store from "../../../../store";

export default {
  name: 'Right2To1',
  components: {
    NewInstructionsDialog,
    SmallTitle,
    ZnvVideo,
    showLeadersDialog,
    showLookDialog,
    // VideoConferencingDialog,
    showViewDialog,
    videoMeetingDialog,
    viewDetailDialog,
    TaskAssignDialog,
    VideoConferencingDialogTest
  },
  data() {
    return {
      showTaskAssignVisiable: false,
      infomation: {},
      showVisible2: false,
      title: ['任务下派'],
      activeIndex: 0,
      opaCent: [{
        deviceId: 1,
        url: require('../../../../../public/videoAssets/固定视频0.mp4'),
        type: 'mp4'
      }],
      leadershipList: [],
      showLeadersVisible: false,
      showLookVisible: false,
      showLeadersList: [],
      videoVisible: false,
      timer: null,
      timer1: null,
      showViewVisible: false,
      videoMeetingVisible: false,
      showViewlist: false,
      videoMeetingData: {},
      options: [],
      meetingMini: false,
      precinct: this.$route.query.eventId,
      page: [],
      allList: [],
      groupList: [],
      modelList: [],
      eventList:[]
    }
  },
  mounted() {
    this.$root.Bus.$on('globalTimeIntervalRefreshEvent', () => {
      this.getEventCondition(this.$store.getters.eventId.id)
    })
    this.getEventCondition(this.$store.getters.eventId.id)
    this.drawBill()
    this.queryGroup()
    this.queryAll()
    this.queryModel()

    this.timer1 = setInterval(() => {
      this.queryGroup()
      this.queryAll()
      this.queryModel()
    }, 1000 * 60)
    this.departments()
    this.$root.Bus.$on('drawBill', (data) => {
      if (data) {
        this.drawBill()
      }
    })
  },
  methods: {
    getEventCondition(eventId){
      const url = `/community/dapeng/eventTask/query-condition?eventId=${eventId}`
      Axios.get(url).then((r) => {
        this.eventList = r.data.data.list.length > 0 ? [r.data.data.list[0]]:[]
      })
    },
    queryGroup() {
      let url = '/apis/huaweiGroup/queryGroupTree?type=区域分组'
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        let code = res.data.code
        if (code === 200) {
          this.groupList = res.data.data
        } else {
          console.log(res.data.message)
        }
      })
    },
    queryAll() {
      let url = '/apis/huawei/querySites?currentPage=1&numberPerPage=1000'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.allList = res.data.data.list
        } else {
          console.log(res.data.message)
        }
      })
    },
    queryModel() {
      let url = '/apis/huaweiGroup/queryGroupTree?type=预案模板'
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        let code = res.data.code
        if (code === 200) {
          this.modelList = res.data.data
        } else {
          console.log(res.data.message)
        }
      })
    },
    questionList(e) {
      e.url = 'http://10.1.50.123/zhrc_portal/eventlog/?riskId=' + e.eventIdNew + '&type=duty&height=800'
      this.videoMeetingVisible = true
      this.videoMeetingData = e
    },
    mini(e) {
      if (e === 1) {
        this.meetingMini = true
      } else {
        this.meetingMini = false
      }
    },
    click(e) {
      console.log(e)
    },
    handleClick(index) {
      this.activeIndex = index
    },
    drawBill() {
      let url = '/apis/eventJoint/queryTitle'
      Axios.get(url).then((res) => {
        let data = res.data
        if (data.code === 200) {
          let r = data.data.list
          this.page = r
        } else {
          this.$message({
            message: data.message,
            type: 'warning'
          })
        }
      })
    },
    // 组织部门
    departments() {
      let url = '/apis/eventJoint/getDepartmentAll'
      Axios.get(url).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          let r = data.data
          this.options = r
        } else {
          this.$message({
            message: data.message,
            type: 'warning'
          })
        }
      })
    },
    // 领导指示 / 要求
    getLeaderShip() {
      let url = '/apis/leaderInstruction/instructionContent'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data
          if (data.leaderInstruction && data.leaderInstruction.length > 0) {
            this.showLeadersList = data.leaderInstruction
            let leaderTrue = data.leaderInstruction.filter((item) => Number(item.status) !== 1)
            this.leadershipList = leaderTrue
          } else {
            this.leadershipList = []
          }
        }
      })
    },

    show() {
      if (this.activeIndex === 0) {
        this.showLookVisible = true
      } else {
        this.showViewVisible = true
      }
    },
    more() {
      if (this.activeIndex === 0) {
        this.showLeadersVisible = true
      } else {
        this.showViewlist = true
      }
    },
    handleVideo() {
      this.videoVisible = true
      // this.clearOprator()
    },
    clearOprator() {
      let url = '/apis/saveLog'
      let obj = {
        area: '视频会商',
        name: sessionStorage.trueName,
        precinctId: this.$route.query.precinctId,
        state: '已打开',
        time: moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      }
      Axios.post(url, obj)
    }
  },
  beforeDestroy() {
    this.timer && clearInterval(this.timer)
    this.timer1 && clearInterval(this.timer1)
  }
}
</script>
<style lang="scss" scoped>
.videoDialogRWXP {
  z-index: 10000 !important;

  ::v-deep .el-dialog {
    width: 70%;
    position: relative;

    .el-dialog__header {
      display: none;
    }
  }
}
</style>
<style lang="scss">
.Right2To1 {
  width: 100%;
  position: absolute;

  .circle1 {
    width: 15rem;
    height: 15rem;
    background: #f44336;
    border-radius: 50%;
    font-size: 12rem;
    text-align: center;
    color: #fff;
    position: absolute;
    line-height: 15rem;
    top: -3rem;
    right: 12rem;
  }

  .rongheVideo {
    width: 260rem;
    height: 96rem;
    border: 1rem solid rgba(17, 155, 248, 0.5);
    font-size: 48rem;
    font-family: SourceHanSansCN;
    font-weight: 500;
    color: #ffffff;
    background: linear-gradient(45deg, #13a1f1 0%, #ffffff 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    position: absolute;
    line-height: 96rem;
    cursor: pointer;
    right: 20rem;
    top: 12rem;

    .circle {
      display: inline-block;
      width: 18rem;
      height: 18rem;
      background: #ffffff;
      border-radius: 50%;
      margin: 8rem 12rem;
    }
  }

  .content {
    margin: 54rem 0 30rem;
    position: relative;

    .leader {
      width: 100%;
      height: 72rem;
      display: flex;

      img {
        width: 105rem;
      }

      ul {
        display: flex;
        width: 590rem;

        li {
          font-size: 60rem;
          font-family: Microsoft YaHei;
          font-weight: 600;
          color: #ffffff;
          line-height: 70rem;
          background: linear-gradient(0deg, #13a1f1 0%, #ffffff 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          margin: 0 25rem;
          cursor: pointer;
        }
      }

      span {
        font-size: 16rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ffd56f;
        margin-top: 6rem;
        cursor: pointer;
      }
    }

    .pageall {
      border: 5rem solid;
      border-top: 0;
      border-image: linear-gradient(to bottom, rgba(70, 129, 249, 0), rgba(70, 129, 249, 0.5)) 1;

      .body_content {
        font-size: 60rem;

        .more {
          font-size: 48rem;
          font-family: Source Han Sans CN;
          font-weight: 400;
          color: #ffd56f;
          position: absolute;
          right: 8%;
          top: -6rem;
        }

        img {
          width: 98rem;
          height: 88rem;
        }
      }

      .content_s {
        font-size: 48rem;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #fffefe;
        line-height: 84rem;
        height: 650rem;
        overflow: auto;
      }

      .untitle {
        width: 100%;
        height: 100%;
        margin-top: 20rem;
        text-align: center;
      }

      ul li {
        width: 685rem;
        height: 28rem;
        line-height: 28rem;
        background: #153b66;
        // opacity: 0.4;
        margin-top: 10rem;
        font-size: 14rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        cursor: pointer;
        margin-left: 10rem;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;

        span {
          display: inline-block;
          width: 6rem;
          height: 6rem;
          background: #ffffff;
          border-radius: 50%;
          margin: 0 12rem 0 29rem;
        }
      }

      .pageLi {
        color: #19a8eb;
      }

      .pageDiv {
        height: 160rem;
        overflow: auto;
      }
    }
  }
}
</style>
