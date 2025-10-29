<template>
  <el-dialog
    :title="'视频会商'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1940rem"
    height="900rem"
    class="VideoConferencingDialogTest default-dialog-class large-screen"
    :append-to-body="true"
    :before-close="handleClose"
    v-dialogDrag
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div class="main-container">
      <i class="el-icon-minus" title="缩小" @click="minus"></i>
      <div class="left-container">
        <el-input placeholder="请输入会议主题" suffix-icon="el-icon-plus" v-model="conferencingName" clearable>
        </el-input>

        <el-input placeholder="请输入搜索名称" suffix-icon="el-icon-search" v-model="searchName" clearable> </el-input>

        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="区域分组" :disabled="conferencingState === 1" name="group">
            <el-collapse accordion>
              <el-checkbox-group v-model="checkedGroup" @change="handleCheckedhange">
                <el-collapse-item title="市委办" v-for="(item, index) in dataOJ.group" :key="index">
                  <template slot="title"> <span class="dot-icon"></span>{{ item.name }} </template>
                  <div class="i-item" v-for="(it, ind) in filterArr(searchName, item.children)" :key="ind">
                    <div class="item-left">
                      <el-checkbox :label="it.uris">{{ it.name }}</el-checkbox>
                    </div>
                    <div class="item-right">
<!--                      <span
                        class="idot isActive"
                        v-if="
                          it.deviceStatus.gkRegStatus === 'DEVICE_STATUS_OK' ||
                          it.deviceStatus.sipStatus === 'DEVICE_STATUS_OK'
                        "
                        >在线</span
                      >-->
                      <span class="idot">离线</span><!-- v-else-->
                      <span class="idot isActive" v-if="false && it.deviceStatus.callStatus === 'DEVICE_STATUS_OK'">已参会</span>
                      <span class="idot" v-else>未参会</span>
                    </div>
                  </div>
                </el-collapse-item>
              </el-checkbox-group>
            </el-collapse>
          </el-tab-pane>
          <el-tab-pane label="全部" :disabled="conferencingState === 1" name="all">
            <el-checkbox-group v-model="checkedAll" @change="handleCheckedhange">
              <div class="s-item" v-for="(it, ind) in filterArr(searchName, dataOJ.all)" :key="ind">
                <div class="s-item-left">
                  <el-checkbox :label="it.uris">{{ it.name }}</el-checkbox>
                </div>
                <div class="s-item-right">
<!--                  <span
                    class="s-idot isActive"
                    v-if="it.deviceStatus && (it.deviceStatus.gkRegStatus === 'DEVICE_STATUS_OK' ||
                      it.deviceStatus.sipStatus === 'DEVICE_STATUS_OK') "
                    >在线</span
                  >-->
                  <span class="s-idot" >离线</span><!--v-else-->
                  <span class="s-idot isActive" v-if="false && it.deviceStatus.callStatus === 'DEVICE_STATUS_OK'">已参会</span>
                  <span class="s-idot" v-else>未参会</span>
                </div>
              </div>
            </el-checkbox-group>
          </el-tab-pane>
          <el-tab-pane label="预案模板" :disabled="conferencingState === 1" name="model">
            <el-collapse accordion>
              <el-checkbox-group v-model="checkedModel" @change="handleCheckedhange">
                <el-collapse-item title="市委办" v-for="(item, index) in dataOJ.model" :key="index">
                  <template slot="title"> <span class="dot-icon"></span>{{ item.name }} </template>
                  <div class="i-item" v-for="(it, ind) in filterArr(searchName, item.children)" :key="ind">
                    <div class="item-left">
                      <el-checkbox :label="it.uris">{{ it.name }}</el-checkbox>
                    </div>
                    <div class="item-right">
<!--                      <span
                        class="idot isActive"
                        v-if="it.deviceStatus && (it.deviceStatus.gkRegStatus === 'DEVICE_STATUS_OK' || it.deviceStatus.sipStatus === 'DEVICE_STATUS_OK')"
                        >在线</span
                      >-->
                      <span class="idot" >离线</span><!--v-else-->
                      <span class="idot isActive" v-if="false && it.deviceStatus.callStatus === 'DEVICE_STATUS_OK'">已参会</span>
                      <span class="idot" v-else>未参会</span>
                    </div>
                  </div>
                </el-collapse-item>
              </el-checkbox-group>
            </el-collapse>
          </el-tab-pane>
        </el-tabs>

        <el-button
          type="primary"
          icon="el-icon-phone"
          class="startMeeting"
          @click="startMeeting"
          v-if="conferencingState === 0"
          >开始会议</el-button
        >
        <el-button type="primary" icon="el-icon-phone" class="endMeeting" @click="endMeeting" v-else
          >结束会议</el-button
        >
      </div>
      <div class="right-container">
        <module-view :module-name="'视频会商'">
          <!-- <div class="el-icon-refresh" @click="refreshVideo" title="刷新"></div> -->
          <template>
            <div class="video-warp-content" v-show="url">
              <video id="video" autoplay controls muted preload="none" disablePictureInPicture></video>
              <!-- <div class="video-item" v-for="item in playVideoList" :key="item.device_id">
                <znv-video
                  :src="item.src"
                  :type="item.type"
                  :vId="item.vId"
                  :playerType="item.playerType"
                  :key="item.refreshTime"
                  :ref="item.vId"
                >
                  <template>
                    <div class="video-button" @click="setMuted(item)">{{ item.isMuted ? '放音' : '静音' }}</div>
                    <div class="video-button" @click="setFullscreen(item)">全屏</div>
                  </template>
                </znv-video>
              </div> -->
            </div>
            <div class="video-warp-content blackVideo" v-show="!url"></div>
          </template>
        </module-view>
      </div>

<!--      <div class="tip-line">
        <span class="text">参会情况:{{ conferencingState === 1 ? siteList.map((t) => t.Name).join(',') : '' }}</span>
      </div>-->
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import ModuleView from '@/components/ModuleView'
export default {
  name: 'VideoConferencingDialog',
  props: ['dialogVisibleFlag', 'meetingMini', 'groupList', 'allList', 'modelList'],
  components: { ModuleView },
  data() {
    return {
      dialogVisible: false,
      conferencingName: '', // 会议名称
      conferencingState: 0, // 会议状态
      searchName: '',
      activeName: 'group',
      title: '',
      checked: true,
      dataOJ: {
        group: [],
        all: [],
        model: []
      },
      checkedGroup: [],
      checkedGroupCopy: [],
      checkedAll: [],
      checkedAllCopy: [],
      checkedModel: [],
      checkedModelCopy: [],
      siteTitle: '',
      currentMeetingData: [],
      currentSiteList: [],
      playVideoList: [],
      playVideoList1: '',
      url: '',
      flvSettings: {
        withCredentials: false,
        hasAudio: false
      }
    }
  },
  watch: {
    dialogVisibleFlag(v) {
      if (v) {
        this.dialogVisible = v
        if (!this.meetingMini) {
          this.dataOJ = {
            group: [],
            all: [],
            model: []
          }
          this.conferencingName = ''
          this.searchName = ''
          this.dataOJ.group = this.groupList
          this.dataOJ.all = this.allList
          this.dataOJ.model = this.modelList
          // this.queryGroup()
          // this.queryAll()
          // this.queryModel()
          this.getVideoList()
          // window.hiddenTimeout = setInterval(() => {
          //   this.getVideoList()
          // }, 60 * 1000 * 10)
        }
      }
    } /*,
    checkedGroup(nVal, oVal) {
      this.handleMeeting(nVal, oVal)
    },
    checkedAll(nVal, oVal) {
      this.handleMeeting(nVal, oVal)
    },
    checkedModel(nVal, oVal) {
      this.handleMeeting(nVal, oVal)
    } */
  },
  mounted() {
    /* this.dataOJ.group = queryGroupTree.data
    this.dataOJ.all = querySites.data.list
    this.dataOJ.model = queryGroupTree.data */
  },
  computed: {
    siteList() {
      let siteList = []
      if (this.activeName === 'all') {
        siteList = this.dataOJ.all
          .filter((t) => this.checkedAll.includes(t.uris))
          .map((t) => {
            return {
              Name: t.name,
              Uri: t.uris
            }
          })
      } else if (this.activeName === 'group') {
        let concatArr = []
        this.dataOJ.group.forEach((t) => {
          concatArr = concatArr.concat(t.children)
        })
        siteList = concatArr
          .filter((t) => this.checkedGroup.includes(t.uris))
          .map((t) => {
            return {
              Name: t.name,
              Uri: t.uris
            }
          })
      } else if (this.activeName === 'model') {
        let concatArr = []
        this.dataOJ.model.forEach((t) => {
          concatArr = concatArr.concat(t.children)
        })
        siteList = concatArr
          .filter((t) => this.checkedModel.includes(t.uris))
          .map((t) => {
            return {
              Name: t.name,
              Uri: t.uris
            }
          })
      }
      return siteList
    }
  },
  methods: {
    minus() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    },
    setFullscreen(data) {
      this.$refs[data.vId][0].setFullscreen()
    },
    setMuted(data) {
      data.isMuted = !data.isMuted
      this.$refs[data.vId][0].setMuted(data.isMuted)
    },
    refreshVideo() {
      this.getVideoList()
    },
    getVideoList() {
      let url = '/apis/queryDevice/query33DeviceList'
      if (this.$route.query.precinctId) {
        url += '?precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        let datas = res.data.data || []
        if (this.$route.query.precinctId) {
          this.url = datas[0].url
          this.loadFlv(this.url)
        } else {
          this.playVideoList1 = datas[0].url
          this.checkOnOff(datas[0].url)
        }
        // let videoUrl = {
        //   src: datas[0].url,
        //   name: '',
        //   type: 'flv'
        // }
        // let data = [
        //   {
        //     vId: 'VideoConferencing' + 0,
        //     playerType: 'videoJs',
        //     refreshTime: +new Date(),
        //     isMuted: true,
        //     iconShow: false
        //   }
        // ]
        // data[0] = { ...data[0], ...videoUrl }
        // if (this.$route.query.precinctId) {
        //   this.playVideoList = data
        // } else {
        //   this.playVideoList1 = data
        //   this.checkOnOff(data)
        // }
      })
    },
    loadFlv(url) {
      if (!url) return
      if (this.flv) {
        this.flv.destroy()
      }

      var video = document.getElementById('video')

      this.flv = new window.FlvExtend({
        element: video,
        frameTracking: sysConfig.extendSettings.frameTracking, // 追帧设置
        frameTrackingDelta: 2, // 延迟容忍度，即缓冲区末尾时间与当前播放时间的差值，大于该值会触发追帧
        updateOnStart: sysConfig.extendSettings.updateOnStart, // 点击播放按钮后实时更新视频
        updateOnFocus: sysConfig.extendSettings.updateOnFocus, // 回到前台后实时更新
        reconnect: sysConfig.extendSettings.reconnect, // 断流后重连
        reconnectInterval: 5000 // 重连间隔(ms)
      })

      this.player = this.flv.init(
        {
          type: 'flv',
          url: url,
          isLive: true,
          hasAudio: this.flvSettings.hasAudio,
          withCredentials: this.flvSettings.withCredentials
        },
        {
          enableStashBuffer: false // 是否启用IO隐藏缓冲区。如果您需要实时（最小延迟）来进行实时流播放，则设置为false
        }
      )
      this.player.play()
      this.player.onstats = (e) => {
        this.stats = JSON.stringify(e, null, 2)
      }
    },
    // 检查是否打开视频流
    checkOnOff(data) {
      let url = '/apis/huawei/checkOnOff'
      if (this.$route.query.precinctId) {
        url += '?precinctId=' + this.$route.query.precinctId
      }
      Axios.get(url).then((res) => {
        let datas = res.data.data
        if (datas === '0') {
          this.url = ''
        } else {
          this.url = data
          this.loadFlv(this.url)
        }
      })
    },
    copyData() {
      if (this.activeName === 'group') {
        this.checkedGroupCopy = this.checkedGroup
      } else if (this.activeName === 'all') {
        this.checkedAllCopy = this.checkedAll
      } else if (this.activeName === 'model') {
        this.checkedModelCopy = this.checkedModel
      }
    },
    handleMeeting(nVal, oVal) {
      if (this.conferencingState === 1) {
        if (nVal.length > oVal.length) {
          let o = this.compareArr(nVal, oVal)
          this.$confirm('从会议中添加该部门, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              this.addMeeting(o)
            })
            .catch(() => {
              if (this.activeName === 'group') {
                this.checkedGroup = this.checkedGroup.filter((t) => t !== o.uris)
              } else if (this.activeName === 'all') {
                this.checkedAll = this.checkedAll.filter((t) => t !== o.uris)
              } else if (this.activeName === 'model') {
                this.checkedModel = this.checkedModel.filter((t) => t !== o.uris)
              }
              this.copyData()
            })
        } else {
          let o = this.compareArr(oVal, nVal)
          this.$confirm('从会议中移除该部门, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              this.delMeeting(o)
            })
            .catch(() => {
              if (this.activeName === 'group') {
                this.checkedGroup.push(o.uris)
              } else if (this.activeName === 'all') {
                this.checkedAll.push(o.uris)
              } else if (this.activeName === 'model') {
                this.checkedModel.push(o.uris)
              }
              this.copyData()
            })
        }
      }
    },
    filterArr(name, arr) {
      return arr.filter((t) => t.name.indexOf(name) > -1)
    },
    compareArr(arr1, arr2) {
      let o = arr1.filter((t) => !arr2.includes(t))
      return o
    },
    addMeeting(e) {
      let url = '/apis/huawei/addSiteInScheduledConf?confInternalId=' + this.currentMeetingData.id.confInternalId
      let list = [
        {
          siteName: e.name,
          uri: e.uris
        }
      ]
      Axios.post(url, list).then((res) => {
        let code = res.data.code
        if (code === 200) {
          this.$message.success('添加成功')
          this.conferencingState = 1
          this.copyData()
        } else {
          this.$message.warning(res.data.message)
        }
      })
    },
    delMeeting(e) {
      let url =
        '/apis/huawei/delSiteInScheduledConf?confInternalId=' +
        this.currentMeetingData.id.confInternalId +
        '&siteUris=' +
        e.uris
      Axios.post(url).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('删除成功')
          this.copyData()
        }
      })
    },
    handleCheckedhange(checkList) {
      if (this.conferencingState === 0) {
        if (this.activeName === 'group') {
          this.checkedGroupCopy = checkList
        } else if (this.activeName === 'all') {
          this.checkedAllCopy = checkList
        } else if (this.activeName === 'model') {
          this.checkedModelCopy = checkList
        }
      } else {
        if (this.activeName === 'group') {
          this.handleMeeting(checkList, this.checkedGroupCopy)
        } else if (this.activeName === 'all') {
          this.handleMeeting(checkList, this.checkedAllCopy)
        } else if (this.activeName === 'model') {
          this.handleMeeting(checkList, this.checkedModelCopy)
        }
      }
    },
    handleClose() {
      if (this.conferencingState === 1) {
        this.$message.warning('会议中不能关闭')
      } else {
        this.dialogVisible = false
        window.hiddenTimeout && clearInterval(window.hiddenTimeout)
        this.$emit('update:dialogVisibleFlag', false)
      }
    },
    handleClick(tab, event) {
      console.log(tab, event)
    },
    // queryGroup() {
    //   let url = '/apis/huaweiGroup/queryGroupTree?type=区域分组'
    //   if (this.$route.query.precinctId) {
    //     url += '&precinctId=' + this.$route.query.precinctId
    //   }
    //   Axios.get(url).then((res) => {
    //     let code = res.data.code
    //     if (code === 200) {
    //       this.dataOJ.group = res.data.data
    //     } else {
    //       this.$message.warning(res.data.message)
    //     }
    //   })
    // },
    // queryAll() {
    //   let url = '/apis/huawei/querySites?currentPage=1&numberPerPage=1000'
    //   Axios.get(url).then((res) => {
    //     if (res.data.code === 200) {
    //       this.dataOJ.all = res.data.data.list
    //     } else {
    //       this.$message.warning(res.data.message)
    //     }
    //   })
    // },
    // queryModel() {
    //   let url = '/apis/huaweiGroup/queryGroupTree?type=预案模板'
    //   if (this.$route.query.precinctId) {
    //     url += '&precinctId=' + this.$route.query.precinctId
    //   }
    //   Axios.get(url).then((res) => {
    //     let code = res.data.code
    //     if (code === 200) {
    //       this.dataOJ.model = res.data.data
    //     } else {
    //       this.$message.warning(res.data.message)
    //     }
    //   })
    // },
    startMeeting() {
      if (!this.conferencingName) {
        this.$message('请输入会议名称')
        return
      }
      let url = '/apis/huawei/scheduleConf?conferenceName=' + this.conferencingName + '&minuteSpan=0'
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      if (this.siteList.length === 0) {
        this.$message('请选择至少一个会场')
        return
      }

      Axios.post(url, this.siteList).then((res) => {
        let code = res.data.code
        if (code === 200) {
          this.$message('开始会议成功')
          this.conferencingState = 1
          if (this.url === '' && !this.$route.query.precinctId) {
            this.url = this.playVideoList1
            this.loadFlv(this.url)
          }
          this.currentMeetingData = res.data.data[0]
        } else {
          this.$message(res.data.message)
        }
      })
    },
    // 会议完全结束
    deleteSite() {
      let url = '/apis/huawei/deleteScheduledConf?confInternalId=' + this.currentMeetingData.id.confInternalId
      if (this.$route.query.precinctId) {
        url += '&precinctId=' + this.$route.query.precinctId
      }
      Axios.post(url).then((res) => {
        let code = res.data.code
        if (code === 200) {
          this.$message('结束会议成功')
          this.currentMeetingData = null
          if (!this.$route.query.precinctId) {
            this.url = ''
            var video = document.getElementById('video')
            this.flv = new window.FlvExtend({
              element: video,
              frameTracking: sysConfig.extendSettings.frameTracking, // 追帧设置
              frameTrackingDelta: 2, // 延迟容忍度，即缓冲区末尾时间与当前播放时间的差值，大于该值会触发追帧
              updateOnStart: sysConfig.extendSettings.updateOnStart, // 点击播放按钮后实时更新视频
              updateOnFocus: sysConfig.extendSettings.updateOnFocus, // 回到前台后实时更新
              reconnect: sysConfig.extendSettings.reconnect, // 断流后重连
              reconnectInterval: 5000 // 重连间隔(ms)
            })
            this.player = this.flv.init(
              {
                type: 'flv',
                url: '',
                isLive: true,
                hasAudio: this.flvSettings.hasAudio,
                withCredentials: this.flvSettings.withCredentials
              },
              {
                enableStashBuffer: false // 是否启用IO隐藏缓冲区。如果您需要实时（最小延迟）来进行实时流播放，则设置为false
              }
            )
            this.player.pause()
          }
          this.conferencingState = 0
        } else {
          this.$message(res.data.message)
        }
      })
    },
    getUris() {
      let uris = []
      if (this.activeName === 'group') {
        uris = this.checkedGroup
      } else if (this.activeName === 'all') {
        uris = this.checkedAll
      } else if (this.activeName === 'model') {
        uris = this.checkedModel
      }
      return uris.join(',')
    },
    endMeeting() {
      // 结束会议
      let url = '/apis/huawei/disconnectSites?confInternalId=' + this.currentMeetingData.id.confInternalId
      let uris = this.getUris()
      url += '&uris=' + uris
      Axios.post(url).then((res) => {
        let code = res.data.code
        if (code === 200) {
          this.deleteSite()
        } else {
          this.$message(res.data.message)
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.VideoConferencingDialogTest {
  ::v-deep .el-dialog {
    margin-bottom: 0;
    width: 5259rem !important;
    height: 2601rem !important;
    .el-dialog__body {
      padding: 0 20rem;
    }
  }
  .el-dialog__body {
    padding-bottom: 0;
  }
  ::v-deep .main-container {
    position: relative;
    display: flex;
    flex-wrap: nowrap;
    .el-icon-minus {
      position: absolute;
      top: -54rem;
      color: #fbd671;
      right: 35rem;
      cursor: pointer;
    }
    .left-container {
      width: 1570rem;
      height: 2190rem;
      box-sizing: border-box;
      border: 1rem solid rgba(1, 149, 255, 0.2);

      display: flex;
      flex-direction: column;
      align-items: center;
      margin-left: 96rem;
      margin-right: 96rem;
      padding-left: 20rem;
      padding-right: 20rem;
      .title {
        font-family: PingFangSC-Semibold;
        font-size: 28rem;
        font-weight: 600;
        line-height: 26rem;
        letter-spacing: -0.018em;
        color: #ffffff;

        margin-top: 17rem;
        margin-bottom: 20rem;
      }

      .el-input {
        width: 1576rem;
        height: 124rem;
        margin-bottom: 46rem;
        .el-input__inner{
          width: 1576rem;
          height: 124rem;
        }
        .el-input__suffix{
          background: #0094FF;
        }
      }

      .el-tabs {
        width: 1570rem;
        margin-bottom: 20rem;
        .el-tabs__header {
          border: none !important;
        }
        .el-tabs__header {
          .el-tabs__nav-wrap {
            .el-tabs__nav-scroll {
              .el-tabs__nav {
                width: 1570rem;
                border: none;
                display: flex;
                justify-content: space-between;
                .el-tabs__item {
                  text-align: center;
                  height: 108rem;
                  border: none;
                  width: 452rem;
                  //background: rgba(68, 146, 255, 0.2);
                  //border: 1rem solid;
                  //border-image: linear-gradient(to top, rgba(17, 110, 168, 0.1), rgba(96, 183, 255, 0.8)) 1;
                  background-image: url("../../src/assets/left/未选中.png");
                  background-size: 100% 100%;

                  font-size: 60rem;
                  font-family: Source Han Sans CN;
                  font-weight: 500;
                  color: #FFFFFF;
                  letter-spacing: -0.018em;
                  display: flex;
                  align-items: center;
                  justify-content: center;
                }
                .el-tabs__item.is-active {
                  //background: rgba(255, 221, 111, 0.2);
                  //border: 1rem solid;
                  //border-image: linear-gradient(to top, rgba(208, 159, 24, 0), rgba(255, 214, 117, 1)) 1;
                  background-image: url("../../src/assets/left/选中.png");
                  background-size: 100% 100%;

                  font-size: 60rem;
                  font-family: Source Han Sans CN;
                  font-weight: 500;
                  color: #FFFFFF;
                  line-height: 74rem;
                  vertical-align: center;
                  letter-spacing: -0.018em;
                }
              }
            }
          }
        }

        .el-tabs__content {
          padding-top: 40rem;
          .el-tab-pane {
            overflow-x: hidden;
            overflow-y: scroll;
            height: 1288rem;
            .s-item {
              width: 370rem;
              height: 22rem;
              background: #092a4b;
              margin-top: 12rem;
              margin-bottom: 12rem;
              display: flex;
              flex-wrap: nowrap;
              align-items: center;
              justify-content: space-between;
              .s-item-left {
                font-family: SourceHanSansCN-Regular;
                font-size: 12rem;
                font-weight: normal;
                line-height: 12rem;
                letter-spacing: 0em;
                color: #9ab1cb;
                .el-checkbox {
                  .el-checkbox__label {
                    color: #9ab1cb;
                  }
                  .el-checkbox__input {
                    .el-checkbox__inner {
                      border: 1rem solid #9ab1cb !important;
                      background-color: transparent !important;
                    }
                  }
                }
                .el-checkbox.is-checked {
                  .el-checkbox__label {
                    color: #9ab1cb;
                  }
                }
              }
              .s-item-right {
                font-family: SourceHanSansCN-Regular;
                font-size: 12rem;
                font-weight: normal;
                line-height: 12rem;
                letter-spacing: 0em;
                color: #78ff7a;
                .s-idot {
                  color: #ff6b6b;
                }
                .s-idot.isActive {
                  color: #78ff7a;
                }
                .s-idot::before {
                  content: '';
                  display: inline-block;
                  width: 5rem;
                  height: 5rem;
                  background: #ff6b6b;
                  border-radius: 50%;
                  margin-right: 6rem;
                }
                .s-idot.isActive::before {
                  content: '';
                  display: inline-block;
                  width: 5rem;
                  height: 5rem;
                  background: #78ff7a;
                  border-radius: 50%;
                  margin-right: 6rem;
                }
                .s-idot:first-child {
                  margin-right: 68rem;
                }
                .s-idot:last-child {
                  margin-right: 20rem;
                }
              }
            }
            .el-collapse {
              border: none;
              overflow-x: hidden;
              overflow-y: scroll;
              height: 1288rem;
              .el-collapse-item {
                > div {
                  background-color: transparent;
                  .el-collapse-item__header {
                    background: #0d365f;
                    font-family: SourceHanSansCN-Regular;
                    font-size: 60rem;
                    height: 180rem;
                    font-weight: normal;
                    line-height: 180rem;
                    letter-spacing: -0.018em;
                    color: #b7e6ff;
                    border: none;
                    padding-left: 12rem;
                    .dot-icon {
                      display: inline-block;
                      width: 31rem;
                      height: 31rem;
                      background: #b7e6ff;
                      border-radius: 50%;
                      margin-right: 34rem;
                    }
                  }
                }
                .el-collapse-item__wrap {
                  border: none;
                  .el-collapse-item__content {
                    .i-item{
                      width: 1570rem;
                      height: 180rem;
                      display: flex;
                      flex-wrap: nowrap;
                      align-items: center;
                      justify-content: space-between;
                      .item-left {
                        font-family: SourceHanSansCN-Regular;
                        font-size: 12rem;
                        font-weight: normal;
                        line-height: 12rem;
                        letter-spacing: 0em;
                        color: #9ab1cb;
                        .el-checkbox {
                          display: flex;
                          align-items: center;
                          .el-checkbox__label {
                            margin-left: 26rem;
                            font-size: 60rem;
                            font-family: Microsoft YaHei;
                            font-weight: 400;
                            color: #FFFFFF;
                            line-height: 180rem;
                          }
                          .el-checkbox__input {
                            margin-left: 74rem;
                            .el-checkbox__inner {
                              width: 46rem;
                              height: 46rem;
                              border: 1rem solid #9ab1cb;
                              background-color: transparent;
                            }
                            .el-checkbox__inner:after{
                              width: 17rem;
                              height: 25rem;
                            }
                          }
                        }
                        .el-checkbox.is-checked {
                          .el-checkbox__label {
                            color: #9ab1cb;
                          }
                        }
                      }
                      .item-right {
                        font-family: SourceHanSansCN-Regular;
                        font-size: 12rem;
                        font-weight: normal;
                        line-height: 12rem;
                        letter-spacing: 0em;
                        .idot {
                          color: #F11700;
                        }
                        .idot.isActive {
                          color: #73FDFF;
                        }
                        .idot::before {
                          content: '';
                          display: inline-block;
                          width: 31rem;
                          height: 31rem;
                          background: #F11700;
                          border-radius: 50%;
                          margin-right: 6rem;
                        }
                        .idot.isActive::before {
                          content: '';
                          display: inline-block;
                          width: 31rem;
                          height: 31rem;
                          background: #73FDFF;
                          border-radius: 50%;
                          margin-right: 6rem;
                        }
                        .idot:first-child {
                          margin-right: 68rem;
                          font-size: 60rem;
                          font-family: Microsoft YaHei;
                          font-weight: 400;
                          color: #FFFFFF;
                          line-height: 180rem;
                        }
                        .idot:last-child {
                          margin-right: 20rem;
                          font-size: 60rem;
                          font-family: Microsoft YaHei;
                          font-weight: 400;
                          color: #FFFFFF;
                          line-height: 180rem;
                        }
                      }
                    }
                    .i-item:nth-child(odd) {
                      background: #092a4b;
                    }
                    .i-item:nth-child(even){

                    }
                  }
                }
              }
            }
          }
        }
      }

      .el-button.startMeeting {
        border: none;
        margin-top: 102rem;
        width: 1438rem;
        height: 111rem;
        font-size: 49rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #FFFFFF;
        line-height: 12rem;
        letter-spacing: 0em;
        color: #ffffff;
        background: linear-gradient(-46deg, transparent 30rem, #3f8eb8 0) right,linear-gradient(135deg, transparent 30rem, #3f8eb8 0) left;
        background-size: 50% 100%;
        background-repeat: no-repeat;
      }
      .el-button.endMeeting {
        border: none;
        margin-top: 102rem;
        width: 1438rem;
        height: 111rem;
        font-size: 49rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #FFFFFF;
        line-height: 12rem;
        letter-spacing: 0em;
        color: #ffffff;
        background: linear-gradient(-46deg, transparent 30rem, #3f8eb8 0) right,linear-gradient(135deg, transparent 30rem, #3f8eb8 0) left;
        background-size: 50% 100%;
        background-repeat: no-repeat;
      }
    }
    .right-container {
      margin-left: 194rem;
      margin-right: 200rem;
      width: 3196rem;
      height: 2190rem;
      background: rgba(37, 164, 255, 0.02);
      box-sizing: border-box;
      border: 1rem solid rgba(1, 149, 255, 0.2);
      position: relative;
      .el-icon-refresh {
        cursor: pointer;
        position: absolute;
        top: -40rem;
        left: 725rem;
        z-index: 2;
      }

      .video-warp-content {
        position: relative;
        height: 100%;
        #video {
          width: 100%;
          height: 100%;
        }

        .video-item {
          // width: calc(50% - 15rem);
          height: 100%;

          // &:first-child {
          //   float: left;
          // }

          // &:last-child {
          //   float: right;
          // }

          .video-button {
            padding: 5rem 5rem;
            cursor: pointer;
            margin-left: 5rem;
            display: inline-block;
            margin-top: 5rem;
          }
        }
      }
      .blackVideo {
        background: #092a4b;
      }
    }
    .tip-line {
      position: absolute;
      bottom: -55rem;
      left: 445rem;
      .text {
        font-family: PingFangSC-Semibold;
        font-size: 16rem;
        font-weight: 600;
        line-height: 25rem;
        letter-spacing: -0.018000000000000002em;
        color: #ffffff;
      }
    }
  }
}
</style>
