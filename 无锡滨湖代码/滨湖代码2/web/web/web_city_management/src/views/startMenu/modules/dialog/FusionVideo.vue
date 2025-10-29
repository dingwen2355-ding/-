<template>
  <div>
    <el-dialog
      title="融合视频"
      :visible.sync="$store.getters.fusionVideo"
      :modal="false"
      height="2601rem"
      width="9378rem"
      class="FusionVideo default-dialog-class large-screen"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="dd-title-back">
        <div class="dd-title-back-s"></div>
      </div>
      <div class="dd-back-area top-right-area"></div>
      <div class="dd-back-angle top-left-angle"></div>
      <div class="dd-back-angle top-right-angle"></div>
      <div class="dd-back-angle bottom-left-angle"></div>
      <div class="dd-back-angle bottom-right-angle"></div>
      <div class="fusionVideo-"></div>
      <div class="video">
        <div class="danbing">
          <div class="leader">
            <img src="../../../../assets/global/title.png" alt/>
            单兵
          </div>
          <znv-video
            :src="opaCent[0].url"
            :fontSize="16"
            :type="opaCent[0].type"
            class="videoHight"
            style="width: 3004rem;height: 2088rem;"
          ></znv-video>
        </div>
        <div class="shipinhuishang">
          <div class="leader">
            <img src="../../../../assets/global/title.png" alt/>
            视频会商
          </div>
          <div class="videoItem">
            <znv-video
              :src="opaCent[1].url"
              :fontSize="16"
              :type="opaCent[1].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
            <znv-video
              :src="opaCent[2].url"
              :fontSize="16"
              :type="opaCent[2].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
            <znv-video
              :src="opaCent[3].url"
              :fontSize="16"
              :type="opaCent[3].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
            <znv-video
              :src="opaCent[4].url"
              :fontSize="16"
              :type="opaCent[4].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
          </div>
        </div>
        <div class="chengshizhiy">
          <div class="leader">
            <img src="../../../../assets/global/title.png" alt/>
            城市之眼
          </div>
          <div class="videoItem">
            <znv-video
              :src="opaCent[5].url"
              :fontSize="16"
              :type="opaCent[5].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
            <znv-video
              :src="opaCent[6].url"
              :fontSize="16"
              :type="opaCent[6].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
          </div>
        </div>
        <div class="wurenji">
          <div class="leader">
            <img src="../../../../assets/global/title.png" alt/>
            无人机
          </div>
          <div class="videoItem">
            <znv-video
              :src="opaCent[7].url"
              :fontSize="16"
              :type="opaCent[7].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
            <znv-video
              :src="opaCent[8].url"
              :fontSize="16"
              :type="opaCent[8].type"
              class="videoHight"
              style="width: 1492rem;height: 1038rem;"
            ></znv-video>
          </div>
        </div>
      </div>
      <!-- <znv-video
      :src="opaCent[0].url"
      :fontSize="16"
      :type="opaCent[0].type"
      class="videoHight"
      style="width: 3004rem;height: 2008rem;position: absolute;top: 12.8%;left: -11.5%;"
    ></znv-video>
    <znv-video
      :src="opaCent[0].url"
      :fontSize="16"
      :type="opaCent[0].type"
      class="videoHight"
      style="width: 3038rem;height: 2100rem;position: absolute;top: 12.8%;left: 25.6%;"
    ></znv-video>
    <znv-video
      :src="opaCent[0].url"
      :fontSize="16"
      :type="opaCent[0].type"
      class="videoHight"
      style="width: 1500rem;height: 1042rem;position: absolute;top: 12.8%;left: 62.8%;"
    ></znv-video>
    <znv-video
      :src="opaCent[0].url"
      :fontSize="16"
      :type="opaCent[0].type"
      class="videoHight"
      style="width: 1500rem;height: 1042rem;position: absolute;top: 12.8%;left: 81.4%;"
    ></znv-video>
    <znv-video
      :src="opaCent[0].url"
      :fontSize="16"
      :type="opaCent[0].type"
      class="videoHight"
      style="width: 1500rem;height: 1042rem;position: absolute;top: 51.4%;left: 62.8%;"
    ></znv-video>
    <znv-video
      :src="opaCent[0].url"
      :fontSize="16"
      :type="opaCent[0].type"
      class="videoHight"
      style="width: 1500rem;height: 1042rem;position: absolute;top: 51.4%;left: 81.4%;"
      ></znv-video>-->
    </el-dialog>
  </div>
</template>
<script>
import SmallTitle from '@/components/SmallTitle.vue'
import ZnvVideo from '@/components/znvVideo/Video.vue'
import axios from '@/utils/request'
// import VideoInspectionModuleDialog from './VideoInspectionModuleDialog.vue'
export default {
  name: 'FusionVideo',
  components: {SmallTitle, ZnvVideo},
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
      opaCent: []
    }
  },
  beforeMount() {
    // this.getConvergeVideo()
  },
  mounted() {
    this.getConvergeVideo()
    // this.getdeviceId()
  },
  methods: {
    handleClose() {
      this.$store.dispatch('view/setFusionVideo', false)
    },
    getConvergeVideo() {
      let url
      if (this.precinct !== undefined) {
        url = `/community/dapeng/event/query-condition?id=${this.precinct}`
      } else {
        url = '/community/dapeng/event/query-condition'
      }
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          let deviceIds = res.data.data.list[0].device_ids
          let url1
          let dataDevice = {
            url: '',
            type: 'mp4'
          }
          if (deviceIds !== null) {
            deviceIds.split(',').forEach(item => {
              url1 = `/apis/dahua/getVideoUrl?deviceId=${item}&videoType=HLS`
              axios.get(url1, {
                headers: {
                  Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
                }
              }).then(res1 => {
                if (res1.data.code === 200) {
                  dataDevice.url = require(res1.data.data.url)
                  this.opaCent.push(dataDevice)
                } else {
                  this.$message.error('视频流数据获取失败！')
                  console.log('视频流数据获取失败')
                }
              })
            })
          } else {
            console.log('设备id为空')
          }
          if (this.opaCent.length === 0) {
            console.log('使用默认不展示视频')
            for (let i = 0; i < 9; i++) {
              this.opaCent.push(
                {
                  deviceId: i,
                  // url: require('../../../../../public/videoAssets/固定视频01.mp4'),
                  url: '../../../../../public/videoAssets/固定视频01.mp4',
                  type: 'mp4'
                }
              )
            }
          }
        }
      })
    }
  }
}
</script>

<style lang="scss">
.FusionVideo {
  position: absolute;
  top: 488rem;
  right: 0;

  .el-dialog {
    width: 9378rem !important;
    height: 2601rem !important;
  }

  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 2208rem !important;
    overflow: hidden;
  }

  .video {
    width: 100%;
    height: 100%;
    display: flex;

    .danbing {
      width: 3004rem;
      margin-right: 50rem;
      // border: solid 1rem #efefef;
    }

    .shipinhuishang {
      width: 3034rem;
      margin-right: 50rem;

      .videoHight:nth-child(odd) {
        margin-right: 50rem;
      }
    }

    .chengshizhiy {
      width: 1492rem;
      margin-right: 50rem;
    }

    .wurenji {
      width: 1492rem;
    }

    .videoItem {
      display: flex;
      flex-wrap: wrap;

      .videoHight {
        margin-bottom: 10rem;
      }
    }

    .leader {
      width: 156rem;
      height: 68rem;
      font-size: 72rem;
      font-family: Microsoft YaHei;
      font-weight: 600;
      color: #ffffff;
      background: linear-gradient(0deg, #13a1f1 0%, #ffffff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      line-height: 78rem;
      width: 100%;
      height: 72rem;
      display: flex;
      margin-bottom: 52rem;

      img {
        width: 105rem;
        margin-right: 24rem;
      }
    }
  }
}
</style>
