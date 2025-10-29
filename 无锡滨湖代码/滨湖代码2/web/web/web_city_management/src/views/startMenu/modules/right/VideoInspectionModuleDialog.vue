<template>
  <el-dialog
    :title="'融合视频'"
    :visible.sync="dialogVisible"
    :modal="false"
    :width="showBig ? '100%' : '2000rem'"
    class="VideoInspectionModuleDialog"
    :style="{ marginTop: showBig ? '-15vh' : '0', marginBottom: showBig ? '-50rem' : '', height: showBig ? '1245rem !important' : '' }"
    :append-to-body="true"
    :before-close="handleClose"
  >
  <div style="margin-top: -25rem;">
  <img class="imgDiv" src="../../../../assets/global/组109.png" @click="changeBig" />
    <div class="VideoInspectionModule-warp" :style="{ width: showBig ? 'auto' : '1900rem', height: showBig ? '970rem' : '730rem' }">
      <!--        <module-view>-->
      <div class="video-content">
        <div class="video-content-item">
          <div class="item-head" @mouseover="video[0].iconShow = true" @mouseleave="video[0].iconShow = false">
            <module-title :title="title[0]" :fontSize="24"></module-title>
            <i
              class="search-icon el-icon-search icon-botton"
              v-show="video[0].iconShow"
              @click="queryVideo(0, [video[0].deviceKind])"
            ></i>
          </div>
          <div class="item-content" @mouseenter="mouseenterHandle(video[0])" @mouseleave="mouseleaveHandle(video[0])">
            <znv-video
              v-if="video[0].type === 'hls' || video[0].type === 'rtsp' || video[0].type === 'flv' ||video[0].type === 'iframe'"
              :src="video[0].src"
              :type="video[0].type"
              :vId="video[0].vId"
              :playerType="video[0].playerType"
              :key="video[0].refreshTime"
              :ref="video[0].vId"
              :tgt_session_id="video[0].tgt_session_id"
              :cameraId="video[0].type === 'iframe'?video[0].device_id:''"
            >
            </znv-video>
            <!--            <video-->
            <!--              v-else-if="video[0].type === 'gaoda'"-->
            <!--              controls-->
            <!--              autoplay-->
            <!--              :muted="video[0].isMuted"-->
            <!--              :id="'MobileIndividualVideo_' + video[0].remoteId + '_inspectionModuleDialog'"-->
            <!--            ></video>-->
            <!-- <GDIndividualVideo v-else-if="video[0].type === 'gaoda'" :gdData="video[0]" :postfix="gdronghe"/>
            <GGIndividualVideo v-else-if="video[0].type === 'second'" :gdData="video[0]" :preDom="'ronghe'"/> -->
            <video v-else-if="video[0].type === 'wex'" id="video2" :name="video[0].meetvideo" autoplay controls></video>
            <div v-else class="no-video">请配置数据</div>
            <!--            <transition name="fade">-->
            <!--              <div class="opt-box" v-show="video[0].soltShow">-->
            <!--                <div class="video-button" @click="refreshVideo(video[0], 0)">刷新</div>-->
            <!--                <div class="video-button" @click="setMuted(video[0])">{{ video[0].isMuted ? '放音' : '静音' }}</div>-->
            <!--                <div class="video-button" @click="setFullscreen(video[0])">全屏</div>-->
            <!--              </div>-->
            <!--            </transition>-->
          </div>
        </div>
        <div class="video-content-item">
          <div class="item-head" @mouseover="video[1].iconShow = true" @mouseleave="video[1].iconShow = false">
            <module-title :title="title[1]" :fontSize="24"></module-title>
            <i
              class="search-icon el-icon-search icon-botton"
              v-show="video[1].iconShow"
              @click="queryVideo(1, [video[1].deviceKind])"
            ></i>
          </div>
          <div class="item-content" @mouseenter="mouseenterHandle(video[1])" @mouseleave="mouseleaveHandle(video[1])">
            <znv-video
              v-if="video[1].type === 'hls' || video[1].type === 'rtsp' || video[1].type === 'flv'||video[1].type === 'iframe'"
              :src="video[1].src"
              :type="video[1].type"
              :vId="video[1].vId"
              :playerType="video[1].playerType"
              :key="video[1].refreshTime"
              :ref="video[1].vId"
              :tgt_session_id="video[1].tgt_session_id"
              :cameraId="video[1].type === 'iframe'?video[1].device_id:''"
            >
            </znv-video>
            <!--            <video-->
            <!--              v-else-if="video[1].type === 'gaoda'"-->
            <!--              controls-->
            <!--              autoplay-->
            <!--              :muted="video[1].isMuted"-->
            <!--              :id="'MobileIndividualVideo_' + video[1].remoteId + '_inspectionModuleDialog'"-->
            <!--            ></video>-->
            <!-- <GDIndividualVideo v-else-if="video[1].type === 'gaoda'" :gdData="video[1]" :postfix="gdronghe"/>
            <GGIndividualVideo v-else-if="video[1].type === 'second'" :gdData="video[1]" :preDom="'ronghe'"/> -->
            <video v-else-if="video[1].type === 'wex'" id="video3" :name="video[1].meetvideo" autoplay controls></video>
            <div v-else class="no-video">请配置数据</div>
            <!--            <transition name="fade">-->
            <!--              <div class="opt-box" v-show="video[1].soltShow">-->
            <!--                <div class="video-button" @click="refreshVideo(video[1], 1)">刷新</div>-->
            <!--                <div class="video-button" @click="setMuted(video[1])">{{ video[1].isMuted ? '放音' : '静音' }}</div>-->
            <!--                <div class="video-button" @click="setFullscreen(video[1])">全屏</div>-->
            <!--              </div>-->
            <!--            </transition>-->
          </div>
        </div>
        <div class="video-content-item">
          <div class="item-video">
            <div class="video-warp">
              <div class="item-head" @mouseover="video[2].iconShow = true" @mouseleave="video[2].iconShow = false">
                <module-title :title="title[2]" :fontSize="24"></module-title>
                <i
                  class="search-icon el-icon-search icon-botton"
                  v-show="video[2].iconShow"
                  @click="queryVideo(2, [video[2].deviceKind])"
                ></i>
              </div>
              <div
                class="item-content"
                @mouseenter="mouseenterHandle(video[2])"
                @mouseleave="mouseleaveHandle(video[2])"
              >
                <znv-video
                  v-if="video[2].type === 'hls' || video[2].type === 'rtsp' || video[2].type === 'flv'||video[2].type === 'iframe'"
                  :src="video[2].src"
                  :type="video[2].type"
                  :vId="video[2].vId"
                  :playerType="video[2].playerType"
                  :key="video[2].refreshTime"
                  :ref="video[2].vId"
                  :tgt_session_id="video[2].tgt_session_id"
                  :cameraId="video[2].type === 'iframe'?video[2].device_id:''"
                ></znv-video>
                <!--                <video-->
                <!--                  v-else-if="video[2].type === 'gaoda'"-->
                <!--                  controls-->
                <!--                  autoplay-->
                <!--                  :muted="video[2].isMuted"-->
                <!--                  :id="'MobileIndividualVideo_' + video[2].remoteId + '_inspectionModuleDialog'"-->
                <!--                ></video>-->
                <!-- <GDIndividualVideo v-else-if="video[2].type === 'gaoda'" :gdData="video[2]" :postfix="gdronghe"/>
                <GGIndividualVideo v-else-if="video[2].type === 'second'" :gdData="video[2]" :preDom="'ronghe'"/> -->
                <video v-else-if="video[2].type === 'wex'" id="video4" :name="video[2].meetvideo" autoplay controls></video>
                <div v-else class="no-video">请配置数据</div>
                <!--                <transition name="fade">-->
                <!--                  <div class="opt-box" v-show="video[2].soltShow">-->
                <!--                    <div class="video-button" @click="refreshVideo(video[2], 2)">刷新</div>-->
                <!--                    <div class="video-button" @click="setMuted(video[2])">{{ video[2].isMuted ? '放音' : '静音' }}</div>-->
                <!--                    <div class="video-button" @click="setFullscreen(video[2])">全屏</div>-->
                <!--                  </div>-->
                <!--                </transition>-->
              </div>
            </div>
            <div class="video-warp">
              <div class="item-head" @mouseover="video[4].iconShow = true" @mouseleave="video[4].iconShow = false">
                <module-title :title="title[4]" :fontSize="24"></module-title>
                <i
                  class="search-icon el-icon-search icon-botton"
                  v-show="video[4].iconShow"
                  @click="queryVideo(4, [video[4].deviceKind])"
                ></i>
              </div>
              <div
                class="item-content"
                @mouseenter="mouseenterHandle(video[4])"
                @mouseleave="mouseleaveHandle(video[4])"
              >
                <znv-video
                  v-if="video[4].type === 'hls' || video[4].type === 'rtsp' || video[4].type === 'flv'||video[4].type === 'iframe'"
                  :src="video[4].src"
                  :type="video[4].type"
                  :vId="video[4].vId"
                  :playerType="video[4].playerType"
                  :key="video[4].refreshTime"
                  :ref="video[4].vId"
                  :tgt_session_id="video[4].tgt_session_id"
                  :cameraId="video[4].type === 'iframe'?video[4].device_id:''"
                ></znv-video>
                <!--                <video-->
                <!--                  v-else-if="video[4].type === 'gaoda'"-->
                <!--                  controls-->
                <!--                  autoplay-->
                <!--                  :muted="video[4].isMuted"-->
                <!--                  :id="'MobileIndividualVideo_' + video[4].remoteId + '_inspectionModuleDialog'"-->
                <!--                ></video>-->
                <!-- <GDIndividualVideo v-else-if="video[4].type === 'gaoda'" :gdData="video[4]" :postfix="gdronghe"/>
                <GGIndividualVideo v-else-if="video[4].type === 'second'" :gdData="video[4]" :preDom="'ronghe'"/> -->
                <video v-else-if="video[4].type === 'wex'" id="video6" :name="video[4].meetvideo" autoplay controls></video>
                <div v-else class="no-video">请配置数据</div>
                <!--                <transition name="fade">-->
                <!--                  <div class="opt-box" v-show="video[4].soltShow">-->
                <!--                    <div class="video-button" @click="refreshVideo(video[4], 4)">刷新</div>-->
                <!--                    <div class="video-button" @click="setMuted(video[4])">{{ video[4].isMuted ? '放音' : '静音' }}</div>-->
                <!--                    <div class="video-button" @click="setFullscreen(video[4])">全屏</div>-->
                <!--                  </div>-->
                <!--                </transition>-->
              </div>
            </div>
          </div>
          <div class="item-video">
            <div class="video-warp">
              <div class="item-head" @mouseover="video[3].iconShow = true" @mouseleave="video[3].iconShow = false">
                <module-title :title="title[3]" :fontSize="24"></module-title>
                <i
                  class="search-icon el-icon-search icon-botton"
                  v-show="video[3].iconShow"
                  @click="queryVideo(3, [video[3].deviceKind])"
                ></i>
              </div>
              <div
                class="item-content"
                @mouseenter="mouseenterHandle(video[3])"
                @mouseleave="mouseleaveHandle(video[3])"
              >
                <znv-video
                  v-if="video[3].type === 'hls' || video[3].type === 'rtsp' || video[3].type === 'flv'||video[3].type === 'iframe'"
                  :src="video[3].src"
                  :type="video[3].type"
                  :vId="video[3].vId"
                  :playerType="video[3].playerType"
                  :key="video[3].refreshTime"
                  :ref="video[3].vId"
                  :tgt_session_id="video[3].tgt_session_id"
                  :cameraId="video[3].type === 'iframe'?video[3].device_id:''"
                ></znv-video>
                <!--                <video-->
                <!--                  v-else-if="video[3].type === 'gaoda'"-->
                <!--                  controls-->
                <!--                  autoplay-->
                <!--                  :muted="video[3].isMuted"-->
                <!--                  :id="'MobileIndividualVideo_' + video[3].remoteId + '_inspectionModuleDialog'"-->
                <!--                ></video>-->
                <!-- <GDIndividualVideo v-else-if="video[3].type === 'gaoda'" :gdData="video[3]" :postfix="gdronghe"/>
                <GGIndividualVideo v-else-if="video[3].type === 'second'" :gdData="video[3]" :preDom="'ronghe'"/> -->
                <video v-else-if="video[3].type === 'wex'" id="video5" :name="video[3].meetvideo" autoplay controls></video>
                <div v-else class="no-video">请配置数据</div>
                <!--                <transition name="fade">-->
                <!--                  <div class="opt-box" v-show="video[3].soltShow">-->
                <!--                    <div class="video-button" @click="refreshVideo(video[3], 3)">刷新</div>-->
                <!--                    <div class="video-button" @click="setMuted(video[3])">{{ video[3].isMuted ? '放音' : '静音' }}</div>-->
                <!--                    <div class="video-button" @click="setFullscreen(video[3])">全屏</div>-->
                <!--                  </div>-->
                <!--                </transition>-->
              </div>
            </div>
            <div class="video-warp">
              <div class="item-head" @mouseover="video[5].iconShow = true" @mouseleave="video[5].iconShow = false">
                <module-title :title="title[5]" :fontSize="24"></module-title>
                <i
                  class="search-icon el-icon-search icon-botton"
                  v-show="video[5].iconShow"
                  @click="queryVideo(5, [video[5].deviceKind])"
                ></i>
              </div>
              <div
                class="item-content"
                @mouseenter="mouseenterHandle(video[5])"
                @mouseleave="mouseleaveHandle(video[5])"
              >
                <znv-video
                  v-if="video[5].type === 'hls' || video[5].type === 'rtsp' || video[5].type === 'flv'||video[5].type === 'iframe'"
                  :src="video[5].src"
                  :type="video[5].type"
                  :vId="video[5].vId"
                  :playerType="video[5].playerType"
                  :key="video[5].refreshTime"
                  :ref="video[5].vId"
                  :tgt_session_id="video[5].tgt_session_id"
                  :cameraId="video[5].type === 'iframe'?video[5].device_id:''"
                ></znv-video>
                <!--                <video-->
                <!--                  v-else-if="video[5].type === 'gaoda'"-->
                <!--                  controls-->
                <!--                  autoplay-->
                <!--                  :muted="video[5].isMuted"-->
                <!--                  :id="'MobileIndividualVideo_' + video[5].remoteId + '_inspectionModuleDialog'"-->
                <!--                ></video>-->
                <!-- <GDIndividualVideo v-else-if="video[5].type === 'gaoda'" :gdData="video[5]" :postfix="gdronghe"/>
                <GGIndividualVideo v-else-if="video[5].type === 'second'" :gdData="video[5]" :preDom="'ronghe'"/> -->
                <video v-else-if="video[5].type === 'wex'" id="video7" :name="video[5].meetvideo" autoplay controls></video>
                <div v-else class="no-video">请配置数据</div>
                <!--                <transition name="fade">-->
                <!--                  <div class="opt-box" v-show="video[5].soltShow">-->
                <!--                    <div class="video-button" @click="refreshVideo(video[5], 5)">刷新</div>-->
                <!--                    <div class="video-button" @click="setMuted(video[5])">{{ video[5].isMuted ? '放音' : '静音' }}</div>-->
                <!--                    <div class="video-button" @click="setFullscreen(video[5])">全屏</div>-->
                <!--                  </div>-->
                <!--                </transition>-->
              </div>
            </div>
          </div>
        </div>
      </div>
      <!--        </module-view>-->
    </div>
  </div>
  </el-dialog>
</template>

<script>
import ZnvVideo from '@/components/znvVideo/Video.vue'
import Axios from '@/utils/request'
import mobileIndividualVideo from '@/utils/mobileIndividualVideo'
import { getFlvUrl, getHlsUrl } from '@/api/info'
// import GGIndividualVideo from '../../../components/GGIndividualVideo'
// import GDIndividualVideo from '../../../components/GDIndividualVideo'

export default {
  name: 'VideoInspectionModuleDialog',
  components: { ZnvVideo },
  props: ['dialogVisibleFlag', 'showvideoList'],
  data() {
    return {
      dialogVisible: false,
      video: [
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        }
      ],
      title: ['未知', '未知', '未知', '未知', '未知', '未知'],
      index: 0,
      videoNameType: {
        固定视频: 'fixedVideo',
        单兵: 'mobileIndividual',
        无人机: 'uavVideo',
        车载视频: 'vehicleVideo',
        视频会商: 'conferencingVideo'
      },
      thisEventIdVideoList: [],
      allVideoList: [],
      deviceList: [],
      precinct: this.$route.query.precinctId,
      rtspVideo: '',
      trackTimer: null,
      showBig: false
    }
  },
  watch: {
    dialogVisibleFlag(v) {
      if (v) {
        this.dialogVisible = true
        this.showBig = false
        this.rtspVideo = this.showvideoList
        this.resetVideo()
        this.$nextTick(() => {
          if (this.$store.getters.eventId.id !== '') {
            this.getVideoList(this.$store.getters.eventId.id)
          } else {
            if (!this.$store.getters.eventId.id) {
              this.noEventIdVideoList()
            } else {
              this.getVideoList(localStorage.getItem('eventId'))
            }
          }
        })
      }
    },
    '$store.getters.eventId': {
      handler(v) {
        this.$nextTick(() => {
          if (v.id) {
            this.dialogVisible && this.getVideoList(v.id)
          } else {
            this.getVideoList(localStorage.getItem('eventId'))
          }
        })
      }
    }
  },
  mounted() {
    this.$root.Bus.$on('videoBigDialog', (row) => {
      this.refreshVideo(row, this.index)
      this.$set(this.title, this.index, row.device_kind + '-' + row.device_name)
    })
  },
  methods: {
    changeBig() {
      if (this.showBig === false) {
        window.parent.postMessage('全屏打开', '*')
      } else {
        window.parent.postMessage('全屏关闭', '*')
      }
      this.showBig = !this.showBig
    },
    handleClose() {
      this.closeDialog()
      this.$emit('update:dialogVisibleFlag', false)
    },
    noEventIdVideoList() {
      Axios.get('/apis/manage/fieldVideo').then((res) => {
        this.thisEventIdVideoList = res.data.data
        this.thisEventIdVideoList.forEach((v, i) => {
          if (v.deviceName) {
            this.$set(this.title, i, v.deviceKind + '-' + v.deviceName)
          } else {
            this.$set(this.title, i, v.deviceKind)
          }
        })
        this.classifyVideo1()
      })
    },
    getVideoList: function (eventId) {
      if (!eventId) return
      // let url = '/apis/fusionMax?eventId=' + eventId
      let url = '/apis/fusionMax'
      Axios.get(url).then((res) => {
        let data = res.data.data || {}
        console.log(data)
        this.deviceList = data.deviceInfos
        this.getVideoUrlList()
      })
    },
    getVideoUrlList() {
      let dev = []
      this.deviceList.forEach((v, i) => {
        if (v.deviceKind === 6) {
          getFlvUrl({
            deviceId: v.deviceId || v.id
          }).then((res) => {
            this.$set(this.title, i, v.deviceName)
            let playVideo = {}
            playVideo.title = v.deviceName || v.device_name || ''
            playVideo.device_id = v.deviceId || v.device_id
            playVideo.refreshTime = +new Date()
            playVideo.vId = 'VideoInspectionModuleDialog' + i
            playVideo.soltShow = false
            playVideo.iconShow = false
            playVideo.deviceKind = v.deviceKind
            playVideo.isMuted = true
            // playVideo.type = 'hls'
            // playVideo.type = 'flv'
            playVideo.type = res.type
            playVideo.src = res.src.url || res.src
            this.$set(this.video, i, playVideo)
            // if (v.deviceName) {
            //   this.$set(this.title, i, v.deviceKind + '-' + v.deviceName)
            // } else {
            //   this.$set(this.title, i, v.deviceKind)
            // }
          })
        } else if (v.deviceKind === 666 || v.deviceKind === 35 || v.deviceKind === 36 || v.deviceKind === 34) {
          getHlsUrl({
            deviceId: v.deviceId || v.id
          }).then((res) => {
            this.$set(this.title, i, v.deviceName)
            let playVideo = {}
            playVideo.title = v.deviceName || v.device_name || ''
            playVideo.device_id = v.deviceId || v.device_id
            playVideo.refreshTime = +new Date()
            playVideo.vId = 'VideoInspectionModuleDialog' + i
            playVideo.soltShow = false
            playVideo.iconShow = false
            playVideo.deviceKind = v.deviceKind
            playVideo.isMuted = true
            // playVideo.type = res.type
            playVideo.type = res.type
            // playVideo.type = 'flv'
            playVideo.src = res.src
            playVideo.tgt_session_id = res.tgt_session_id
            playVideo.deviceName = v.deviceName
            this.$set(this.video, i, playVideo)
          })
        } else if (v.deviceKind === 31) {
          // 高达单兵
          // this.$set(this.title, i, v.deviceName)
          // this.$set(this.video, i, {
          //   title: v.deviceName,
          //   remoteId: v.deviceId,
          //   type: 'gaoda'
          // })
          this.video[i].vId = v.deviceId
          this.video[i].type = 'wex'
          this.video[i].meetvideo = 'meetvideo'
          let playVideo = {}
          playVideo.title = v.deviceName
          playVideo.refreshTime = +new Date()
          playVideo.vId = v.deviceId
          playVideo.soltShow = false
          playVideo.iconShow = false
          playVideo.deviceKind = v.deviceKind
          playVideo.isMuted = true
          playVideo.type = 'wex'
          playVideo.meetvideo = 'meetvideo'
          this.$set(this.title, i, v.deviceName)
          this.$set(this.video, i, playVideo)
          dev.push(v.deviceId)
          // setTimeout(() => {
          //   mobileIndividualVideo.sendPullVideoCmd(v.deviceId, '_inspectionModuleDialog')
          // }, 100)
        } else if (v.deviceKind === 32) {
          // 啥也不是单兵
          this.$set(this.title, i, v.deviceName)
          this.$set(this.video, i, {
            title: v.deviceName,
            remoteId: v.deviceId,
            type: 'second'
          })
        } else if (v.deviceKind === 33) {
          // 视频会商
          this.$set(this.title, i, v.deviceName)
          let playVideo = {}
          playVideo.title = v.deviceName || v.device_name || ''
          playVideo.device_id = v.deviceId || v.device_id
          playVideo.refreshTime = +new Date()
          playVideo.vId = 'VideoInspectionModuleDialog' + i
          playVideo.soltShow = false
          playVideo.iconShow = false
          playVideo.deviceKind = v.deviceKind
          playVideo.isMuted = true
          playVideo.type = 'flv'
          playVideo.src = v.url
          this.$set(this.video, i, playVideo)
          // getFlvUrl({
          //   deviceId: v.deviceId || v.id,
          //   type: 'FLV_HTTP'
          // }).then((res) => {
          //   // this.allVideoList[i] = {
          //   //   name: v.deviceName,
          //   //   src: res.src.url || res.src,
          //   //   type: res.type
          //   // }
          //   // this.title[i] = v.deviceKind
          //   this.$set(this.title, i, v.deviceName)
          //   let playVideo = {}
          //   playVideo.title = v.deviceName || v.device_name || ''
          //   playVideo.device_id = v.deviceId || v.device_id
          //   playVideo.refreshTime = +new Date()
          //   playVideo.vId = 'VideoInspectionModuleDialog' + i
          //   playVideo.soltShow = false
          //   playVideo.iconShow = false
          //   playVideo.deviceKind = v.deviceKind
          //   playVideo.isMuted = true
          //   playVideo.type = 'flv'
          //   playVideo.src = res.src.url || res.src
          //   this.$set(this.video, i, playVideo)
          // })
        }
      })
      if (dev.length > 0) {
        setTimeout(() => {
          this.device31(dev)
        }, 3000)
      }
    },
    device31(dev) {
      let val = this.rtspVideo
      window.wrurl = val.rtsp
      let vid = dev.join(',')
      let Video = { Action: 'StartVideoMeeting', GongHao: val.gonghao, FenJi: val.fenji, PlatFormCode: val.id, Params: '30001' + '|' + vid }
      window.VoiceInterfaceObject.StartVideoMeeting('30001', vid, Video)
    },
    classifyVideo() {
      this.allVideoList.forEach((e, index) => {
        if (e.deviceKind === '单兵') {
          this.$set(this.video, index, {
            title: e.deviceName,
            remoteId: e.deviceId
          })
          setTimeout(() => {
            mobileIndividualVideo.sendPullVideoCmd(e.deviceId, '_inspectionModuleDialog')
          }, 100)
        } else {
          this.$set(this.video, index, this.getPlayVideoList(e, index))
        }
      })
    },
    classifyVideo1() {
      this.thisEventIdVideoList.forEach((e, index) => {
        if (e.deviceKind === '单兵') {
          this.$set(this.video, index, {
            title: e.deviceName,
            remoteId: e.deviceId
          })
          setTimeout(() => {
            mobileIndividualVideo.sendPullVideoCmd(e.deviceId, '_inspectionModuleDialog')
          }, 100)
        } else {
          this.$set(this.video, index, this.getPlayVideoList(e, index))
        }
      })
    },
    getPlayVideoList: function (video, index) {
      let playVideo = {}
      playVideo.title = video.deviceName || video.device_name || ''
      playVideo.device_id = video.deviceId || video.device_id
      playVideo.refreshTime = +new Date()
      playVideo.vId = 'VideoInspectionModuleDialog' + index
      playVideo.soltShow = false
      playVideo.iconShow = false
      playVideo.deviceKind = video.deviceKind
      playVideo.isMuted = true
      if (video.flvUrl || video.flv_url) {
        playVideo.type = 'flv'
        playVideo.src = video.flvUrl || video.flv_url
      } else if (video.hlsUrl || video.hls_url) {
        playVideo.src = video.hlsUrl || video.hls_url
        playVideo.type = 'hls'
        playVideo.playerType = 'videoJs'
      } else if (video.rtspUrl2 || video.rtsp_url2) {
        playVideo.src = video.rtspUrl2 || video.rtsp_url2
        playVideo.type = 'rtsp'
      } else if (video.rtspUrl || video.rtsp_url) {
        playVideo.src = video.rtspUrl || video.rtsp_url
        playVideo.type = 'rtsp'
      } else if (video.rtmpUrl || video.rtmp_url) {
        playVideo.src = video.rtmpUrl || video.rtmp_url
        playVideo.type = 'rtmp'
        playVideo.playerType = 'videoJs'
      }
      return playVideo
    },
    closeDialog() {
      this.video.forEach(item => {
        if (item.type === 'wex') {
          let val = this.rtspVideo
          let Video = { Action: 'ConferGetOut', GongHao: val.gonghao, FenJi: val.fenji, PlatFormCode: val.id, Params: '30001' + '|' + item.vId }
          window.VoiceInterfaceObject.ConferGetOut('30001', item.vId, Video)
        }
      })
      this.$root.Bus.$emit('closeVideoInspectionModuleDialog', {})
      // if (this.$store.getters.eventId.id !== '') {
      //   this.$router.push('/ShCityManagement?eventId=' + this.$store.getters.eventId.id)
      // } else {
      //   this.$router.push('/ShCityManagement?eventId=' + localStorage.getItem('eventId'))
      // }
      this.dialogVisible = false
      window.parent.postMessage('全屏关闭', '*')
    },
    refreshVideo: function (video, index) {
      if (video.remoteId) {
        mobileIndividualVideo.sendPullVideoCmd(video.remoteId, '_inspectionModuleDialog')
        this.video[index].device_id = video.device_id || video.deviceId
        this.submitBigVideo()
      } else {
        let url = '/apis/manage/queryCameraInfoByType?deviceId=' + video.device_id
        Axios.get(url).then((res) => {
          let data = res.data.data
          this.$set(this.video, index, this.getPlayVideoList(data[0], index))
          this.submitBigVideo()
        })
      }
    },
    setMuted(video) {
      if (video.remoteId) {
        video.isMuted = !video.isMuted
      } else {
        video.isMuted = !video.isMuted
        this.$refs[video.vId].setMuted(video.isMuted)
      }
    },
    setFullscreen(video) {
      if (video.remoteId) {
        document
          .getElementById('MobileIndividualVideo_' + video.remoteId + '_inspectionModuleDialog')
          .requestFullscreen()
      } else {
        this.$refs[video.vId].setFullscreen()
      }
    },
    mouseenterHandle(video) {
      video.soltShow = true
    },
    mouseleaveHandle(video) {
      video.soltShow = false
    },
    queryVideo(index, type) {
      this.index = index
      this.$root.Bus.$emit('openQueryVideoDialog', {
        type: type,
        emitType: this.videoNameType[type],
        where: 2
      })
    },
    submitBigVideo() {
      let deviceIds = ''
      this.video.forEach((v) => {
        deviceIds += v.device_id + ','
      })

      let url =
        '/apis/video/updateFusionMax?eventId=' + this.$store.getters.eventId.id + '&type=1' + '&deviceIds=' + deviceIds
      Axios.put(url).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('请求成功')
          this.$root.Bus.$emit('refreshStaff')
        } else {
          this.$message.error('请求失败')
        }
      })
    },
    resetVideo() {
      this.video = [
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        },
        {
          src: '',
          type: '',
          vId: '',
          meetvideo: '',
          playerType: '',
          refreshTime: '',
          soltShow: false,
          iconShow: false,
          deviceKind: ''
        }
      ]
    }
  },
  beforeDestroy() {
    // this.$root.Bus.$off('openVideoInspectionModuleDialog')
    this.$root.Bus.$off('videoBigDialog')
  }
}
</script>

<style lang="scss" scoped>
  .VideoInspectionModuleDialog {
    // position: relative;
    // background: #040c1c;
    width: 100%;
    height: 100%;
    z-index: 10;
    ::v-deep .el-dialog {
      margin-bottom: 0
    }
    .imgDiv {
      position: absolute;
      top: 20rem;
      right: 60rem;
      width: 15rem;
    }
    #video2, #video3 {
        width: 99%;
        margin-top: 2%;
        height: 98%;
        margin-bottom: 10rem;
      }
      #video4, #video5, #video6, #video7 {
        width: 99%;
        margin-top: 2%;
        height: 98%;
        margin-bottom: 10rem;
      }

    .VideoInspectionModule-warp {
      width: 1900rem;
      height: 730rem;
      position: relative;

      .VideoInspectionModule-close {
        position: absolute;
        right: 20rem;
        top: 20rem;
        cursor: pointer;
        z-index: 111;
        font-size: 24rem;
        color: #02fed2;
      }

      .video-content {
        position: relative;
        height: 100%;

        .video-content-item {
          float: left;
          width: 33.3333%;
          height: 100%;
          position: relative;
          padding: 0 10rem;

          .item-head {
            height: 50rem;
            overflow: hidden;
            position: relative;
            line-height: 50rem;
          }

          .item-content {
            position: relative;
            height: calc(100% - 50rem);
            border: 1rem solid #02858c;
            overflow: hidden;

            video {
              width: 100%;
              height: 100%;
            }

            .no-video {
              position: absolute;
              left: 50%;
              top: 50%;
              transform: translate(-50%, -50%);
              font-size: 48rem;
            }

            .opt-box {
              position: absolute;
              width: 100%;
              min-height: 60rem;
              left: 0;
              bottom: 0;
              background-image: linear-gradient(rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.9));
              font-size: 24rem;
              line-height: 40rem;
              z-index: 2;

              .video-button {
                padding: 5rem 5rem;
                cursor: pointer;
                margin-left: 5rem;
                display: inline-block;
                margin-top: 5rem;
              }
            }
          }

          .item-video {
            float: left;
            width: 48%;
            height: 100%;
            margin-right: 10rem;

            .video-warp {
              width: 100%;
              height: 50%;
              position: relative;
            }
          }
        }

        .search-icon {
          font-size: 24rem;
          margin-left: 10rem;
          color: #00a6ff;
          cursor: pointer;
        }
      }
    }
  }

  .fade-leave-active {
    transition: all 0.8s ease;
  }

  .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
  {
    transform: translateY(100%);
  }
</style>
