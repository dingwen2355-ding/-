<template>
  <div class="MapInfoWindow" style="height: 99vh">
    <div class="info-window-content">
      <div class="window-content">
        <div class="yi-dong-dan-bin" v-if="mapInfoWindowType === '移动单兵'">
          <div class="line-info">
            <div class="key">部门：</div>
            <div class="value">{{ popWindowData.unit }}</div>
          </div>
          <div class="line-info">
            <div class="key">名称：</div>
            <div class="value">{{ popWindowData.device_name }}</div>
          </div>
          <div class="line-info">
            <div class="key">ID：</div>
            <div class="value">{{ popWindowData.device_id }}</div>
          </div>
          <div class="video-item">
            <div class="gg-individual">
              <div class="mobile-video-warp">
                <div class="video-canves video gd">
                  <div :class="'GGIndividual_' + 'map' + '_' + remoteId" class="gg-video"></div>
                </div>
              </div>
            </div>
          </div>
          <div class="action-info">
            <div class="action-btn t-red" v-show="isCloseCall" @click="callOverAudioCall(popWindowData.device_id)">
              <i class="iconfont iconzhihui_dianhua_guaduan"></i> 挂断
            </div>
            <div class="action-btn" v-show="!isCloseCall" @click="audioCall(popWindowData.device_id)">
              <i class="iconfont iconmap_layer_yuyin"></i> 语音呼叫
            </div>
            <!-- <div class="action-btn" @click="login">
              <i class="iconfont iconmap_layer_yuyin"></i> 登录
            </div> -->
            <div
              class="action-btn"
              style="float: left"
              v-show="!ismobileCall"
              @click="showMobileIndividualVideoDialog(popWindowData.device_id, false)"
            >
              <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
              <img src="../../../assets/global/video.png" class="img" alt="" />
              视频通话
            </div>
            <div
              class="action-btn t-red"
              style="float: left"
              v-show="ismobileCall"
              @click="delMobileIndividualVideoDialog(popWindowData.device_id)"
            >
              <!--              <i class="iconfont iconmap_layer_shipin"></i>-->
              <img src="../../../assets/global/video.png" class="img" alt="" />
              视频挂断
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import mobileIndividualVoice from '@/utils/mobileIndividual/mobileIndividualVoice'
import * as GGIndividual from '@/utils/GGIndividual/GGIndividual'
import qs from 'qs'

export default {
  data() {
    return {
      mapInfoWindowWidth: 331,
      mapInfoWindowHeight: 223,
      mapInfoWindowVisible: false,
      mapInfoWindowTitle: '移动单兵',
      mapInfoWindowType: '移动单兵', //
      popWindowData: {}, // 填充数据
      isCloseCall: false,
      ismobileCall: false,
      remoteId: '',
      domName: ''
    }
  },
  mounted() {
    let location = window.location.href.split('?')[1]
    if (location) {
      let data = qs.parse(location)
      this.mapInfoWindowVisible = true
      this.mapInfoWindowWidth = data.mapInfoWindowWidth || 331
      this.mapInfoWindowHeight = data.mapInfoWindowHeight || 223
      this.mapInfoWindowTitle = data.mapInfoWindowTitle || '移动单兵'
      this.mapInfoWindowType = data.mapInfoWindowType || '移动单兵'
      this.popWindowData = data
      if (this.mapInfoWindowType === '移动单兵') {
        // GGIndividual.doLogin()
      }
      // this.$root.Bus.$emit('openMapInfoWindowAfter')
    }
    window.onload = function () {
      // let location = window.location.href.split('?')[1]
      // let data = qs.parse(location)
      window.server = sysConfig.xujianUrl.server
      window.name = sysConfig.xujianUrl.account
      window.password = sysConfig.xujianUrl.password
      GGIndividual.doLogin()
    }
    // this.$root.Bus.$on('openMapInfoWindow', (data) =>
    // })
  },
  methods: {
    closeMapInfoWindow: function () {
      this.$root.Bus.$emit('closeMapInfoWindowAfter')
    },
    showMobileIndividualVideoDialog: function (remoteId, isVideoCall) {
      if (this.popWindowData.subtype === 'gaoda') {
        let data = {
          remoteId: remoteId,
          isVideoCall: isVideoCall,
          individualType: this.popWindowData.individual_type,
          type: 'solo',
          subtype: 'gaoda'
        }
        // this.$root.Bus.$emit('showMobileIndividualVideoDialog', data)
        this.$root.Bus.$emit('openVideoWindow', data)
      } else {
        // this.$root.Bus.$emit('showSecondMobileIndividualVideoDialog', data)
        // this.$root.Bus.$emit('openVideoWindow', data)
        this.ismobileCall = true
        this.remoteId = remoteId
        let domName = 'GGIndividual_' + 'map' + '_' + remoteId
        this.domName = domName
        this.$nextTick(() => {
          GGIndividual.makeVideoCall(remoteId, domName)
        })
      }
    },
    audioCall: function (remoteId) {
      this.isCloseCall = true
      if (this.popWindowData.subtype === 'gaoda') {
        mobileIndividualVoice.callReq(11101, this.popWindowData.echart)
      } else {
        GGIndividual.makeCall(remoteId)
      }
    },
    callOverAudioCall: function (remoteId) {
      this.isCloseCall = false
      if (this.popWindowData.subtype === 'gaoda') {
        // if (this.popWindowData.individual_type === '2') {
        mobileIndividualVoice.hangup()
        // }
      } else {
        GGIndividual.hungup(remoteId)
      }
    },
    delMobileIndividualVideoDialog: function (remoteId) {
      this.ismobileCall = false
      if (this.popWindowData.subtype === 'gaoda') {
        // if (this.popWindowData.individual_type === '2') {
        mobileIndividualVoice.hangup()
        // }
      } else {
        GGIndividual.hungup(remoteId)
      }
    }
  },
  beforeDestroy() {
    GGIndividual.stop(this.remoteId, this.domName)
  }
}
</script>

<style lang="scss" scoped>
.MapInfoWindow {
  // z-index: 1111;
  background: rgba(4, 12, 28, 0.8);
  border: 1rem solid #28feff;
  padding: 10rem 15rem;

  .info-window-content {
    position: relative;
    height: 100%;

    .window-header {
      position: relative;
      height: 50rem;
      color: #3df8f8;

      .window-close {
        float: right;
        cursor: pointer;

        i {
          font-size: 24rem;
        }
      }
    }

    .window-content {
      position: relative;
      margin-top: 10rem;
      height: 100%;
      overflow-x: hidden;
      overflow-y: auto;

      .line-info {
        position: relative;
        // line-height: 4.5;
        font-size: 28rem;
        margin-bottom: 10rem;

        .key {
          color: #3df8f8;
          float: left;
          text-align: right;
        }

        .value {
          float: left;
          color: #feffff;
          text-align: left;
        }

        &:before {
          content: ' ';
          display: block;
        }

        &:after {
          content: ' ';
          display: block;
          clear: both;
        }
      }
      .video-item {
        height: 75vh;
        width: 97vw;
        .gg-individual {
          height: 100%;
          width: 100%;

          .mobile-video-warp {
            position: relative;
            width: 100%;
            height: 100%;

            .video {
              width: 100%;
              height: 100%;
              object-fit: fill;
              min-width: 400rem;

              .gg-video {
                width: 100%;
                height: 100%;
              }
            }
          }

          .video-box {
            width: 100%;
            height: 100%;
          }
        }
      }

      .action-info {
        position: relative;
        text-align: center;

        .action-btn {
          width: 185rem;
          height: 50rem;
          background: rgba(4, 12, 28, 0.4);
          color: #3df8f8;
          border: 1rem solid #3df8f8;
          border-radius: 4rem;
          font-size: 24rem;
          line-height: 45rem;
          float: right;
          cursor: pointer;
          margin-right: 10rem;

          i {
            font-size: 16rem;
          }

          img {
            width: 28rem;
            margin-right: 10rem;
            vertical-align: middle;
          }
        }

        .t-red {
          color: red;
        }
      }

      .yi-dong-dan-bin {
        .line-info {
          .key {
            width: 120rem;
          }

          .value {
            width: calc(100% - 120rem);
          }
        }
      }

      .gu-ding-shi-pin {
        position: relative;
        height: 90%;
      }
    }
  }
}
</style>
