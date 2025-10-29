<template>
  <div class="right3 module">
    <SmallTitle :titleList="['视频监控']" style="cursor: pointer" @click="openVideoManage" />
    <div class="rongheVideo" @click="clickTitle">
      <span class="circle"></span>融合视频
    </div>
    <div class="content">
      <div class="noImg">
        <div class="publicVideo" v-for="item in opaCent" :key="item.deviceId">
          <znv-video :src="item.url" :fontSize="16" :type="item.type" class="videoHight"></znv-video>
        </div>
      </div>
    </div>
    <!-- <VideoInspectionModuleDialog :dialogVisibleFlag.sync="showvideoVisible" :showvideoList="showvideoList" /> -->
    <video-manage v-drag :dialogVisibleFlag.sync="videoManageVisible"></video-manage>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import Axios from '@/utils/request'
import { getdeviceId, getHlsUrl, getFlvUrl } from '@/api/info'
import ZnvVideo from '@/components/znvVideo/Video.vue'
import VideoManage from "@/components/VideoManage/VideoManage";
// import VideoInspectionModuleDialog from './VideoInspectionModuleDialog.vue'
export default {
  name: 'SimilarThings',
  components: { SmallTitle, ZnvVideo, VideoManage },
  data() {
    return {
      title: ['领导指示', '指令'],
      activeIndex: 0,
      leadershipList: [],
      showLeadersVisible: false,
      showLookVisible: false,
      showvideoVisible: false,
      showLeadersList: [],
      videoVisible: false,
      videoManageVisible: false,
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
      showvideoList: [],
      groupList: [],
      modelList: [],
      opaCent: sysConfig.localVideo.spjk
      // opaCent: [ // 视频监控
      //   {
      //     deviceId: 1,
      //     url: '',
      //     type: 'mp4'
      //   }, {
      //     deviceId: 2,
      //     url: '',
      //     type: 'mp4'
      //   }, {
      //     deviceId: 3,
      //     url: '',
      //     type: 'mp4'
      //   }, {
      //     deviceId: 4,
      //     url: '',
      //     type: 'mp4'
      //   }
      // ]
    }
  },
  mounted() {
    // setTimeout(() => {
    //   
    // }, 1000 * 5)
    this.$nextTick(() => {
      this.getdeviceId()
    })
  },
  methods: {
    openVideoManage() {
      this.videoManageVisible = true
    },
    getdeviceId() {
      getdeviceId().then((res) => {
        let deviceList = res.data.data.slice(0, 4) || []
        this.opaCent = deviceList.map((e) => {
          e.url = ''
          e.type = ''
          return e
        })
        this.opaCent.forEach((item) => {
          getFlvUrl({
            deviceId: item.deviceId
          }).then((res) => {
            item.url = res.src
            item.type = res.type
          })
        })
      })
    },
    clickTitle() {
      // this.showvideoVisible = true
      this.$store.dispatch('view/setFusionVideo', true)
      console.log(this.$store.getters.fusionVideo);
      // this.showvideoList = []
    },
    handleClose() {
      this.showvideoVisible = false
      // this.showvideoList = []
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
      this.clearOprator()
    }
  }
}
</script>

<style lang="scss" scoped>
.right3 {
  width: 1676rem;
  // height: 850rem;
  position: absolute;
  top: 430rem;
  right: 80rem;
  .videoDialog1 {
    ::v-deep.el-dialog {
      width: 9378rem !important;
    }
  }
  .rongheVideo {
    width: 320rem;
    height: 140rem;
    border: 1rem solid rgba(17, 155, 248, 0.5);
    font-size: 60rem;
    font-family: SourceHanSansCN;
    font-weight: 500;
    color: #ffffff;
    background: linear-gradient(45deg, #13a1f1 0%, #ffffff 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    position: absolute;
    line-height: 140rem;
    cursor: pointer;
    right: 20rem;
    top: 60rem;
    .circle {
      display: inline-block;
      width: 18rem;
      height: 18rem;
      background: #ffffff;
      border-radius: 50%;
      margin: 8rem 12rem;
    }
  }
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
  .content {
    margin: 0 0 0;
    padding-top: 20rem;
    position: relative;
    height: 3076rem;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;
    .noImg {
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-evenly;
      align-items: center;
      .publicVideo {
        width: 98%;
        height: 692rem;
        margin-bottom: 45rem;
        float: left;
        .videoHight {
          width: 100%;
          height: 100%;
        }
      }
      .publicVideo:last-of-type {
        margin-bottom: 0;
      }
      .publicVideo:nth-of-type(2n-1) {
        margin-right: 5rem;
      }
    }
    .leader {
      width: 500rem;
      height: 32rem;
      display: flex;
      .leaderView {
        width: 146.5rem;
        height: 35rem;
        font-size: 18rem;
        font-weight: 500;
        background-image: url('../../../../assets/left/选中.png');
        background-repeat: no-repeat;
        background-size: 100% 100%;
        padding-left: 36rem;
        padding-top: 4rem;
        margin-right: 250rem;
      }
      .add1 {
        width: 26rem;
        height: 25rem;
        position: absolute;
        line-height: 20rem;
        border: 2rem solid #4da0d1;
        right: 1.9%;
        top: 5rem;
        font-size: 27rem;
        color: #4da0d1;
        cursor: pointer;
      }
      ul {
        display: flex;
        width: 590rem;
        li {
          font-size: 18rem;
          font-family: SourceHanSansCN;
          font-weight: 500;
          color: #42a1e4;
          line-height: 30rem;
          margin: 0 20rem;
          cursor: pointer;
        }
        li.active {
          font-size: 21rem;
          font-family: SourceHanSansCN;
          font-weight: 500;
          color: #ffffff;
          border-bottom: 2rem solid #ffffff;
          border-radius: 1rem;
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
