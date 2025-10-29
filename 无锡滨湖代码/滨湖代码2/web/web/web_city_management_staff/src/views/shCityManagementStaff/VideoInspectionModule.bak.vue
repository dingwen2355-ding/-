<template>
  <div class="VideoInspectionModule">
    <div class="video-tab">
      <div class="tab-item item2" :class="{'activeClass': currentTab === '0'}" @click="changeTab('0')">右侧视频配置</div>
      <div class="tab-item item2" :class="{'activeClass': currentTab === '1'}" @click="changeTab('1')">全屏视频配置</div>
    </div>
    <div class="right-content" style="justify-content: left" v-if="currentTab === '0'">
      <div class="right-content-list">
        <div class="right-title">
          <i class="iconfont iconzhgl_title"></i><span>固定视频配置</span>
        </div>
        <div class="right-list">
          <li v-for="(v,i) in videoList.fixedVideo" :key="v.device_id + i">
            <span class="num">{{i + 1}}</span>
            <span class="name">
              {{v.device_name}}
            </span>
            <span class="del">
              <i class="el-icon-delete-solid" @click="deleteItem('fixedVideo',v,i)"></i>
            </span>
          </li>
        </div>
        <div class="right-add-btn" @click="showAddDialog('fixedVideo')">
          <i class="el-icon-plus"></i>新增
        </div>
      </div>
      <div class="right-content-list">
        <div class="right-title">
          <i class="iconfont iconzhgl_title"></i><span>移动单兵配置</span>
        </div>
        <div class="right-list">
          <li v-for="(v,i) in videoList.mobileIndividual" :key="v.device_id + i">
            <span class="num">{{i + 1}}</span>
            <span class="name">
              {{v.device_name}}
            </span>
            <span class="del">
              <i class="el-icon-delete-solid" @click="deleteItem('mobileIndividual',v,i)"></i>
            </span>
          </li>
        </div>
        <div class="right-add-btn" @click="showAddDialog('mobileIndividual')">
          <i class="el-icon-plus"></i>新增
        </div>
      </div>
      <!-- <div v-if="!precinctName && !precinctId" class="right-content-list">
        <div class="right-title">
          <i class="iconfont iconzhgl_title"></i><span>视频会商配置</span>
        </div>
        <div class="right-list">
          <li v-for="(v,i) in videoList.consultationVideo" :key="v.device_id + i">
            <span class="num">{{i + 1}}</span>
            <span class="name">
              {{v.device_name}}
            </span>
            <span class="del">
              <i class="el-icon-delete-solid" @click="deleteItem('consultationVideo',v,i)"></i>
            </span>
          </li>
        </div>
        <div class="right-add-btn" @click="showAddDialog('consultationVideo')">
          <i class="el-icon-plus"></i>新增
        </div>
      </div>
      <div v-if="!precinctName && !precinctId" class="right-content-list">
        <div class="right-title">
          <i class="iconfont iconzhgl_title"></i><span>无人机视频配置</span>
        </div>
        <div class="right-list">
          <li v-for="(v,i) in videoList.UAVVideo" :key="v.device_id + i">
            <span class="num">{{i + 1}}</span>
            <span class="name">
              {{v.device_name}}
            </span>
            <span class="del">
              <i class="el-icon-delete-solid" @click="deleteItem('UAVVideo',v,i)"></i>
            </span>
          </li>
        </div>
        <div class="right-add-btn" @click="showAddDialog('UAVVideo')">
          <i class="el-icon-plus"></i>新增
        </div>
      </div>
      <div v-if="!precinctName && !precinctId" class="right-content-list">
        <div class="right-title">
          <i class="iconfont iconzhgl_title"></i><span>车载视频配置</span>
        </div>
        <div class="right-list">
          <li v-for="(v,i) in videoList.vehicleVideo" :key="v.device_id + i">
            <span class="num">{{i + 1}}</span>
            <span class="name">
              {{v.device_name}}
            </span>
            <span class="del">
              <i class="el-icon-delete-solid" @click="deleteItem('vehicleVideo',v,i)"></i>
            </span>
          </li>
        </div>
        <div class="right-add-btn" @click="showAddDialog('vehicleVideo')">
          <i class="el-icon-plus"></i>新增
        </div>
      </div> -->
    </div>

    <div class="select-content" v-if="currentTab === '1'">
      <span class="select-title">全屏视频模版选择：</span>
      <div class="select-item">
        <el-select v-model="valueTemp2" placeholder="全屏视频模版选择" style="width:100%">
          <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="video-content" v-if="currentTab === '1'">
      <div class="video-item">
        <div class="select-video">
          <el-select v-model="value1" placeholder="视频类型选择" style="width:100%" @change="changeFullType(1, $event)">
            <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
            </el-option>
          </el-select>
        </div>
        <div class="set-video">
          <div class="icon-video">
            <i class="iconfont iconvideo_play02"></i>
            <div class="video-btn full-btn">
              <!--              {{full1===''?'选择视频':full1}}-->
              <el-select class="video-btn full-btn" v-model="full1" placeholder="选择视频">
                <el-option v-for="item in fullList1" :key="item.deviceId" :label="item.deviceName"
                  :value="item.deviceId">
                </el-option>
              </el-select>
            </div>
          </div>
        </div>
      </div>
      <div class="video-item">
        <div class="select-video">
          <el-select v-model="value2" placeholder="视频类型选择" style="width:100%" @change="changeFullType(2, $event)">
            <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
            </el-option>
          </el-select>
        </div>
        <div class="set-video">
          <div class="icon-video">
            <i class="iconfont iconvideo_play02"></i>
            <div class="video-btn full-btn">
              <!--              {{full2===''?'选择视频':full2}}-->
              <el-select class="video-btn full-btn" v-model="full2" placeholder="选择视频">
                <el-option v-for="item in fullList2" :key="item.deviceId" :label="item.deviceName"
                  :value="item.deviceId">
                </el-option>
              </el-select>
            </div>
          </div>
        </div>
      </div>
      <div class="video-small-item">
        <div class="video-flex">
          <div class="select-small-video">
            <el-select v-model="value3" placeholder="视频类型选择" style="width:100%" @change="changeFullType(3, $event)">
              <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
              </el-option>
            </el-select>
          </div>
          <div class="set-video-small">
            <div class="icon-video">
              <i class="iconfont iconvideo_play02"></i>
              <div class="video-btn full-btn">
                <!--                {{full3===''?'选择视频':full3}}-->
                <el-select class="video-btn full-btn" v-model="full3" placeholder="选择视频">
                  <el-option v-for="item in fullList3" :key="item.deviceId" :label="item.deviceName"
                  :value="item.deviceId">
                </el-option>
                </el-select>
              </div>
            </div>
          </div>
        </div>
        <div class="video-flex">
          <div class="select-small-video">
            <el-select v-model="value4" placeholder="视频类型选择" style="width:100%" @change="changeFullType(4, $event)">
              <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
              </el-option>
            </el-select>
          </div>
          <div class="set-video-small">
            <div class="icon-video">
              <i class="iconfont iconvideo_play02"></i>
              <div class="video-btn full-btn">
                <!--                {{full4===''?'选择视频':full4}}-->
                <el-select class="video-btn full-btn" v-model="full4" placeholder="选择视频">
                  <el-option v-for="item in fullList4" :key="item.deviceId" :label="item.deviceName"
                  :value="item.deviceId">
                </el-option>
                </el-select>
              </div>
            </div>
          </div>
        </div>
        <div class="video-flex video-flex-item">
          <div class="select-small-video">
            <el-select v-model="value5" placeholder="视频类型选择" style="width:100%" @change="changeFullType(5, $event)">
              <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
              </el-option>
            </el-select>
          </div>
          <div class="set-video-small">
            <div class="icon-video">
              <i class="iconfont iconvideo_play02"></i>
              <div class="video-btn full-btn">
                <!--                {{full5===''?'选择视频':full5}}-->
                <el-select class="video-btn full-btn" v-model="full5" placeholder="选择视频">
                  <el-option v-for="item in fullList5" :key="item.deviceId" :label="item.deviceName"
                  :value="item.deviceId">
                </el-option>
                </el-select>
              </div>
            </div>
          </div>
        </div>
        <div class="video-flex video-flex-item">
          <div class="select-small-video">
            <el-select v-model="value6" placeholder="视频类型选择" style="width:100%" @change="changeFullType(6, $event)">
              <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
              </el-option>
            </el-select>
          </div>
          <div class="set-video-small">
            <div class="icon-video">
              <i class="iconfont iconvideo_play02"></i>
              <div class="video-btn full-btn">
                <!--                {{full6===''?'选择视频':full6}}-->
                <el-select class="video-btn full-btn" v-model="full6" placeholder="选择视频">
                  <el-option v-for="item in fullList6" :key="item.deviceId" :label="item.deviceName"
                  :value="item.deviceId">
                </el-option>
                </el-select>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="select-content" v-if="currentTab === '2'">
      <span class="select-title">群聊名称：</span>
      <div class="select-item">
        <el-input v-model="name" placeholder="请输入群聊名称" clearable autofocus="autofocus">
        </el-input>
      </div>
    </div>
    <div class="vx-content" v-if="currentTab === '2'">
      <div class="vx-content-item">
        <div class="vx-title">
          <i class="iconfont iconzhgl_title"></i><span>部门</span>
        </div>
        <div class="vx-list">
          <ul>
            <li v-for="(value, key, index) in chatAll" @click="changeDept(value,key,index)"
              :class="{'active':deptChecked===index}" :key="key" :title="key"><i
                class="iconfont iconzhihui_tongxunlu_bumen"></i> {{key}}
            </li>
          </ul>
        </div>
      </div>
      <div class="vx-content-item-1" v-if="itemOne">
        <el-transfer v-model="peopleChecked" :props="{
            key: 'account',
            label: 'name'
          }" @change="changePeople" @left-check-change="leftChange" @right-check-change="rightChange"
          :titles="['待选人员','已选人员']" :data="peopleList">
          <span slot-scope="{ option }" class="people-list"
            :class="{'isNone':deptType!==''&&option.department!==deptType}">{{ option.name }}{{option.account}}</span>
        </el-transfer>
      </div>
    </div>
    <div class="db-content" v-if="currentTab === '3'">
      <div class="db-label">主叫号码：</div>
      <div class="db-input" @click="callingNumberSettingShow = true;changeTransfer()">{{callingNumber}}</div>
    </div>
    <div class="btn">
      <div class="item item_btn1" @click="reset">取消</div>
      <div class="item item_btn2" @click="submitAll">提交</div>
    </div>
    <div class="addDialog" v-show="addDialogShow">
      <div class="add-dialog-content">
        <div class="add-dialog-title">
          <div class="title-content">
            <i class="iconfont iconzhgl_title"></i>
            <span>新增</span>
            <i class="iconfont iconzhgl_title"></i>
          </div>
          <div class="close-icon" @click="addDialogShow = false">
            <i class="el-icon-close"></i>
          </div>
        </div>
        <div class="transfer-content">
          <el-transfer v-model="value" :data="addList" :titles="titles"
            :props="{key: 'device_id',label: 'device_name'}"></el-transfer>
          <div class="btn">
            <div class="item item_btn1" @click="addDialogShow = false">取消</div>
            <div class="item item_btn2" @click="submitResult">提交</div>
          </div>
        </div>
      </div>
    </div>
    <div class="addDialog" v-show="callingNumberSettingShow">
      <div class="add-dialog-content">
        <div class="add-dialog-title">
          <div class="title-content">
            <i class="iconfont iconzhgl_title"></i>
            <span>设置</span>
            <i class="iconfont iconzhgl_title"></i>
          </div>
          <div class="close-icon" @click="callingNumberSettingShow = false">
            <i class="el-icon-close"></i>
          </div>
        </div>
        <div class="transfer-content">
          <el-transfer v-model="callingNumberList" :filterable="true" :data="videoListAll.mobileIndividual"
            @change="changeTransfer" @left-check-change="getLeftChangeCallingNumber"
            :left-default-checked="leftCheckedKeys" @right-check-change="getRightChangeCallingNumber"
            :right-default-checked="rightCheckedKeys" :titles="['设备列表','已选设备']"
            :props="{key: 'device_id',label: 'device_name'}"></el-transfer>
          <div class="btn">
            <div class="item item_btn1" @click="callingNumberSettingShow = false; getCallingNumber()">取消</div>
            <div class="item item_btn2" @click="callingNumberSettingShow = false;getChangeCallingNumber()">提交</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from '@/utils/request'
import $ from 'jquery'

export default {
  name: 'VideoInspectionModule',
  components: {},
  data () {
    return {
      eventId: this.$store.getters.eventId.id,
      name: '',
      currentTab: '0',
      valueTemp1: '',
      valueTemp2: '1',
      value1: '',
      value2: '',
      value3: '',
      value4: '',
      value5: '',
      value6: '',
      options1: [{ value: '1', label: '模板一' }],
      options2: [{ value: '1', label: '模板一' }],
      videoList: {
        fixedVideo: [],
        mobileIndividual: [],
        consultationVideo: [],
        UAVVideo: [],
        vehicleVideo: []
      },
      addDialogShow: false,
      videoListAll: {
        fixedVideo: [],
        mobileIndividual: [],
        consultationVideo: [],
        UAVVideo: [],
        vehicleVideo: []
      },
      videoListBak: {
        fixedVideo: [],
        mobileIndividual: [],
        consultationVideo: [],
        UAVVideo: [],
        vehicleVideo: []
      },
      value: [],
      addList: [],
      titles: ['备选视频', '已选视频'],
      type: '',
      currentChat: {},
      chatAll: {},
      peopleList: [],
      deptChecked: -1,
      deptType: '',
      peopleChecked: [],
      peopleCheckedBak: [],
      deptList: [],
      exceptPeple: [],
      itemOne: true,
      videoTemp: [],
      valueSelected: '',
      addDialogShow1: false,
      fullVideoType: '',
      fullList1: [],
      fullList2: [],
      fullList3: [],
      fullList4: [],
      fullList5: [],
      fullList6: [],
      fullListBak: {
        fixedVideo: [],
        mobileIndividual: [],
        consultationVideo: [],
        UAVVideo: [],
        vehicleVideo: []
      },
      full1: '',
      full2: '',
      full3: '',
      full4: '',
      full5: '',
      full6: '',
      fullType: 0,
      tempData: [],
      tempDataBak: [],
      callingNumber: '',
      callingNumberId: '',
      callingNumberList: [],
      searchCallingText: '',
      searchCallingNumberList: [],
      callingNumberSettingShow: false,
      leftCheckedKeys: [],
      rightCheckedKeys: [],
      precinctId: this.$store.getters.precinctId,
      precinctName: this.$store.getters.precinctName,
      none: 0,
      showDatas: []
    }
  },
  watch: {
    '$store.getters.eventId': {
      handler (v) {
        this.eventId = v.id
        this.$nextTick(() => {
          this.query(v.id)
          // this.queryAllChat()
          this.queryFullAll(0)
        })
      },
      immediate: true
    },
    'callingNumber': {
      handler (v) {
        this.$nextTick(() => {
          // 提交修改主叫号码接口
        })
      },
      immediate: true
    },
    'searchCallingText': {
      handler (v) {
        if (!v) this.searchCallingNumberList = []
      },
      immediate: true
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.queryAll()
      this.queryDeviceKindList()
      // this.getCallingNumber()
    })
  },
  methods: {
    searchFunction: function () {
      if (!this.searchCallingText) return
      let searchResult = []
      for (let i = 0; i < this.fullListBak.mobileIndividual.length; i++) {
        let item = this.fullListBak.mobileIndividual[i]
        if (item.device_name.indexOf(this.searchCallingText) !== -1) {
          searchResult.push(item)
        }
      }
      this.searchCallingNumberList = searchResult
    },
    changeTab (v) {
      this.currentTab = v
    },
    showAddDialog (type) {
      this.showAdd(type)
    },
    showAdd (type) {
      if (this.currentTab === '0') {
        this.type = type
        this.value = []
        this.addDialogShow = true
        this.addList = this.videoListAll[type]
        this.videoList[type].forEach(v => {
          this.value.push(v.device_id)
        })
      } else if (this.currentTab === '1') {
        this.fullVideoType = type
        this.addDialogShow1 = true
      }
    },
    submitResult () {
      this.videoList[this.type] = []
      this.videoListAll[this.type].forEach(v => {
        if (this.value.includes(v.device_id)) {
          this.videoList[this.type].push(v)
        }
      })
      this.addDialogShow = false
    },
    submitAll () {
      if (this.currentTab === '0') {
        let tmp = {
          fixed: this.videoList.fixedVideo,
          deviceIds: this.videoList.fixedVideo,
          consultation: this.videoList.consultationVideo,
          individual: this.videoList.mobileIndividual,
          drone: this.videoList.UAVVideo,
          car: this.videoList.vehicleVideo
        }
        let params = {
          fixed: [],
          deviceIds: [],
          consultation: [],
          individual: [],
          drone: [],
          car: []
        }
        for (let key in tmp) {
          tmp[key].forEach(v => {
            params[key].push(v.device_id)
          })
          params[key] = params[key].join()
        }
        let url = '/apis/fusion/updateEventDeviceIds?eventId=' + this.eventId
        // url += '&' + qs.stringify(params)
        for (let key in params) {
          url += '&' + key + '=' + params[key]
        }
        Axios.post(url).then(res => {
          if (res.data.code === 200) {
            this.$message.success('提交成功')
            this.query()
            this.$root.eventBus.$emit('refreshIframe')
          } else {
            this.$message.error('提交失败')
          }
        })
      } else if (this.currentTab === '1') {
        let deviceIds = this.full1 + ',' + this.full2 + ',' + this.full3 + ',' + this.full4 + ',' + this.full5 + ',' + this.full6 + ','
        let url = '/apis/updateFusionMax?eventId=' + this.eventId + '&type=1' + '&deviceIds=' + deviceIds
        Axios.post(url).then(res => {
          if (res.data.code === 200) {
            this.$message.success('请求成功')
            this.queryFullAll(0)
            this.$root.eventBus.$emit('refreshIframe')
          } else {
            this.$message.error('请求失败')
          }
        })
      } else if (this.currentTab === '2') {
        let list = this.peopleChecked.join()
        if (list === '') {
          this.$message.warning('请至少选择一个人员')
          return false
        }
        let url = '/websocketApis/WX/createGroupChat?name=' + this.name + '&userList=' + this.peopleChecked.join() + '&eventId=' + this.eventId
        Axios.get(url).then(res => {
          if (res.data.errcode === 0 || res.data.errcode === '0') {
            this.$message.success('请求成功')
            this.$root.eventBus.$emit('refreshIframe')
            this.queryChat()
          } else {
            this.$message.error('请求失败')
            // this.queryAllChat()
          }
        })
      } else if (this.currentTab === '3') {
        // console.log(this.callingNumberId)
        this.setCallingNumber(this.callingNumberId)
      }
    },
    deleteItem (type, v, i) {
      this.videoList[type].splice(i, 1)
    },
    queryAll () {
      let url = '/apis/fusion/queryFixedCameraList?eventId=' + this.eventId
      // let url = '/apis/manage/queryCameraInfoByType'
      Axios.get(url).then(res => {
        for (let key in this.videoListAll) {
          this.videoListAll[key] = []
        }
        let data = res.data.data
        data.forEach(v => {
          v.device_name = v.deviceName
          v.device_id = v.deviceId
          if (!v.device_name) {
            v.device_name = v.device_id
          }
          this.videoListAll.fixedVideo.push(v)
        })
      })
    },
    query () {
      if (!this.eventId) return
      // let url = !this.precinctId && !this.precinctName ? '/apis/video/fusion?type=0&eventId=' + this.eventId
      //   : '/apis/fusion/queryFixedCameraList?type=0&eventId=' + this.eventId
      let url = '/apis/fusion/getVideoListByEventId?eventId=' + this.eventId
      Axios.get(url).then(res => {
        for (let key in this.videoList) {
          this.videoList[key] = []
        }
        let data = res.data.data
        data.forEach(v => {
          v.device_name = v.deviceName
          v.device_id = v.deviceId
          if (!v.device_name) {
            v.device_name = v.device_id
          }
          this.videoList.fixedVideo.push(v)
          // this.videoListAll.fixedVideo.push(v)
        })
        this.videoListBak = JSON.parse(JSON.stringify(this.videoList))
      }).catch(e => {
      })
    },
    reset () {
      if (this.currentTab === '0') {
        this.videoList = JSON.parse(JSON.stringify(this.videoListBak))
      } else if (this.currentTab === '1') {
        this.queryFullAll(0)
      } else if (this.currentTab === '2') {
        // this.queryAllChat()
      }
    },
    queryChat () {
      if (!this.eventId) return
      this.peopleChecked = []
      let url = '/apis/queryWxMemberByEventId?eventId=' + this.eventId
      Axios.get(url).then(res => {
        let data = res.data.data
        this.currentChat = data.member
        this.name = data.groupName
        for (let key in this.currentChat) {
          this.currentChat[key].forEach(v => {
            this.peopleChecked.push(v.account)
          })
        }
      })
    },
    queryAllChat () {
      let url = '/apis/queryWXPersonInfoExceptEventId'
      Axios.get(url).then(res => {
        let data = res.data.data
        this.chatAll = data
        this.deptList = Object.keys(this.chatAll)
        this.peopleList = []
        for (let key in this.chatAll) {
          this.peopleList = this.peopleList.concat(this.chatAll[key])
        }
        this.queryChat()
      })
    },
    changeDept (value, key, index) {
      this.deptType = key
      this.deptChecked = index
      this.$nextTick(() => {
        $('.vx-content-item-1 .el-transfer-panel:first-child .people-list').parent().parent().css('cssText', '')
        $('.vx-content-item-1 .el-transfer-panel:first-child .people-list.isNone').parent().parent().css('cssText', 'display:none!important')
      })
    },
    changePeople (c, direction, key) {
      if (direction === 'right') {
        // setTimeout(() => {
        //   this.peopleChecked = this.peopleChecked.filter(v => {
        //     return !this.exceptPeple.includes(v)
        //   })
        // })
      }
      this.$nextTick(() => {
        this.peopleChecked = this.peopleChecked.filter(v => {
          return !this.exceptPeple.includes(v)
        })
        this.$nextTick(() => {
          $('.vx-content-item-1 .el-transfer-panel:first-child .people-list').parent().parent().css('cssText', '')
          $('.vx-content-item-1 .el-transfer-panel:first-child .people-list.isNone').parent().parent().css('cssText', 'display:none!important')
        })
      })
    },
    leftChange (checked, change) {
      this.exceptPeple = []
      if (this.deptType) {
        checked.forEach(v => {
          let isTrue = this.chatAll[this.deptType].some(x => {
            return v === x.account
          })
          if (!isTrue) {
            this.exceptPeple.push(v)
          }
        })
      }
    },
    rightChange (checked, change) {
    },
    changeFullType (v, event) {
      console.log(v, event)
      switch (v) {
        case 1:
          this.full1 = ''
          this.queryFullAll(1, event)
          break
        case 2:
          this.full2 = ''
          this.queryFullAll(2, event)
          break
        case 3:
          this.full3 = ''
          this.queryFullAll(3, event)
          break
        case 4:
          this.full4 = ''
          this.queryFullAll(4, event)
          break
        case 5:
          this.full5 = ''
          this.queryFullAll(5, event)
          break
        case 6:
          this.full6 = ''
          this.queryFullAll(6, event)
          break
        default:
          break
      }
    },
    queryFullAll (numType, event) {
      let url = '/apis/queryCameraInfoByType'
      if (event) {
        url += '?deviceKind=' + event
      }
      Axios.get(url).then(res => {
        for (let key in this.fullListBak) {
          this.fullListBak[key] = []
        }
        let data = []
        data = res.data.data
        switch (numType) {
          case 1:
            this.fullList1 = data
            break
          case 2:
            this.fullList2 = data
            break
          case 3:
            this.fullList3 = data
            break
          case 4:
            this.fullList4 = data
            break
          case 5:
            this.fullList5 = data
            break
          case 6:
            this.fullList6 = data
            break
          case 0:
            let tmp = []
            for (let key in this.fullListBak) {
              tmp = tmp.concat(
                this.fullListBak[key]
              )
            }
            this.fullList1 = this.fullList2 = this.fullList3 = this.fullList4 = this.fullList5 = this.fullList6 = tmp
            break
        }
        if (numType === 0) {
          this.queryTemp()
        }
      })
    },
    queryFullAll1 (e) {
      let url = '/apis/queryCameraInfoByType'
      Axios.get(url).then(res => {
        this.showDatas = []
        this.showDatas = res.data.data
        if (e.length && this.showDatas.length) {
          this.value1 = e[0] ? e[0].deviceKind : ''
          this.value2 = e[1] ? e[1].deviceKind : ''
          this.value3 = e[2] ? e[2].deviceKind : ''
          this.value4 = e[3] ? e[3].deviceKind : ''
          this.value5 = e[4] ? e[4].deviceKind : ''
          this.value6 = e[5] ? e[5].deviceKind : ''
          this.fullList1 = this.value1 ? this.showDatas.filter((item) => item.deviceKind === this.value1) : []
          this.fullList2 = this.value2 ? this.showDatas.filter((item) => item.deviceKind === this.value2) : []
          this.fullList3 = this.value3 ? this.showDatas.filter((item) => item.deviceKind === this.value3) : []
          this.fullList4 = this.value4 ? this.showDatas.filter((item) => item.deviceKind === this.value4) : []
          this.fullList5 = this.value5 ? this.showDatas.filter((item) => item.deviceKind === this.value5) : []
          this.fullList6 = this.value6 ? this.showDatas.filter((item) => item.deviceKind === this.value6) : []
          this.full1 = e[0] ? e[0].deviceId : ''
          this.full2 = e[1] ? e[1].deviceId : ''
          this.full3 = e[2] ? e[2].deviceId : ''
          this.full4 = e[3] ? e[3].deviceId : ''
          this.full5 = e[4] ? e[4].deviceId : ''
          this.full6 = e[5] ? e[5].deviceId : ''
          console.log(this.fullList1, this.fullList2, this.fullList3)
        }
      })
    },
    queryDeviceKindList (e) {
      let url = '/apis/queryDeviceKindList'
      Axios.get(url).then(res => {
        let data = res.data.data || {}
        if (data && data.length > 0) {
          this.videoTemp = data
        }
      })
    },
    queryTemp () {
      if (!this.eventId) return
      let url = '/apis/fusionMax?eventId=' + this.eventId
      Axios.get(url).then(res => {
        let data = res.data.data || {}
        this.tempData = data.deviceInfos || []
        this.tempDataBak = JSON.parse(JSON.stringify(this.tempData))
        this.queryFullAll1(this.tempData)
      })
    },
    getCallingNumber () {
      let url = '/apis/manage/queryIndividualDefalutNum'
      Axios.get(url).then((res) => {
        let data = res.data.data || {}
        console.log('主叫号码', data)
        this.callingNumber = data.device_name || ''
        this.callingNumberList = []
        this.callingNumberList.push(data.config_value)
      })
    },
    getChangeCallingNumber () {
      let callingNumber = this.videoListAll.mobileIndividual.find(item => {
        // eslint-disable-next-line eqeqeq
        return item.device_id == this.callingNumberList[0]
      })
      this.callingNumber = callingNumber.device_name
      this.callingNumberId = callingNumber.device_id
    },
    getLeftChangeCallingNumber (val, val1) {
      // console.log(val, val1)
      this.leftCheckedKeys = val1
      if (this.leftCheckedKeys.length > 1 && this.callingNumberList.length === 0) {
        this.videoListAll.mobileIndividual.map((item, index) => {
          item.disabled = true
          this.videoListAll.mobileIndividual[0].disabled = false
        })
        // eslint-disable-next-line eqeqeq
      } else if (this.callingNumberList.length > 0) { // 穿梭框右侧有值的时候，左侧一直禁用
        this.videoListAll.mobileIndividual.map((item, index) => {
          // eslint-disable-next-line eqeqeq
          if (item.device_id != this.callingNumberList[0]) {
            item.disabled = true
          } else {
            item.disabled = false
          }
        })
      } else {
        this.videoListAll.mobileIndividual.map((item, index) => {
          item.disabled = false
        })
      }
      // console.log(this.leftCheckedKeys)
    },
    getRightChangeCallingNumber (val, val1) {
      // console.log(val, val1)
      this.rightCheckedKeys = val1
      // console.log(this.rightCheckedKeys)
    },
    changeTransfer () {
      if (this.callingNumberList.length) {
        this.videoListAll.mobileIndividual.map((item, index) => {
          // eslint-disable-next-line eqeqeq
          if (item.device_id != this.callingNumberList[0]) {
            item.disabled = true
          } else {
            item.disabled = false
          }
        })
      } else {
        this.videoListAll.mobileIndividual.map((item, index) => {
          item.disabled = false
        })
      }
    },
    setCallingNumber (deviceId) {
      let url = '/apis/manage/updateIndividualDefalutNum?deviceId=' + deviceId
      Axios.post(url).then((res) => {
        let data = res.data
        if (data.code === 200) {
          this.$message({
            message: '主叫号码设置成功！',
            type: 'success'
          })
        } else {
          this.$message({
            message: '主叫号码设置失败！',
            type: 'warning'
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
.VideoInspectionModule {
  width: 100%;
  height: 100%;
  position: relative;

  .video-tab {
    width: 100%;
    height: 200px;

    .tab-item {
      height: 80px;
      float: left;
      font-size: 44px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: rgba(51, 51, 51, 1);
      text-align: center;
      line-height: 80px;
      cursor: pointer;
      margin-top: 60px;

      &.item1 {
        width: 320px;
      }

      &.item2 {
        width: 320px;
      }

      &.activeClass {
        background: rgba(12, 153, 219, 1);
        border-radius: 4px;
        color: #fff;
      }
    }
  }

  .select-content {
    width: 100%;
    height: 80px;
    margin: 48px 0 80px 0;
    font-size: 40px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: rgba(91, 91, 91, 1);

    .select-title {
      width: 380px;
      display: block;
      float: left;
      text-align: center;
    }

    .select-item {
      float: left;
      width: 738px;
      height: 80px;
    }
  }

  .right-content {
    width: 100%;
    height: 950px;
    display: flex;
    justify-content: space-between;

    .right-content-list {
      width: 20%;
      height: 100%;

      .right-title {
        width: 654px;
        height: 118px;
        background: linear-gradient(
          0deg,
          rgba(12, 153, 219, 0.2) 0%,
          rgba(255, 255, 255, 0) 100%
        );
        margin: 0 auto;
        line-height: 118px;
        text-align: left;

        i {
          font-size: 48px;
          margin: 0 25px 0 20px;
          background: linear-gradient(
            -90deg,
            rgba(0, 215, 254, 1) 0%,
            rgba(0, 150, 255, 1) 100%
          );
          // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }

      .right-list {
        width: 654px;
        height: 600px;
        margin: 0 auto;
        overflow: auto;

        li {
          span {
            display: inline-block;
          }

          &:hover {
            .del {
              display: inline-block;
            }
          }
        }

        .num {
          color: #0c99db;
          width: 10%;
        }

        .name {
          width: 80%;
        }

        .del {
          display: none;
          color: #0c99db;
          cursor: pointer;
        }
      }

      .right-add-btn {
        width: 654px;
        height: 98px;
        background: rgba(255, 255, 255, 1);
        border: 2px solid rgba(12, 153, 219, 1);
        border-radius: 8px;
        text-align: center;
        line-height: 98px;
        margin: 64px auto;
        cursor: pointer;

        i {
          color: #0c99db;
        }
      }
    }
  }

  .video-content {
    width: 100%;
    height: 950px;
    display: flex;
    justify-content: space-between;

    .video-item {
      width: 33%;
      height: 100%;

      .select-video {
        width: 539px;
        height: 80px;
        margin: 0 0 40px 0;
      }

      .set-video {
        width: 1255px;
        height: 829px;
        background: rgba(219, 219, 219, 1);
        border: 1px solid rgba(214, 214, 214, 1);
        position: relative;

        .icon-video {
          position: absolute;
          top: 0;
          right: 0;
          bottom: 0;
          left: 0;
          margin: auto;
          width: 244px;
          height: 300px;
          text-align: center;

          .iconfont {
            color: #a4a4a4;
            font-size: 128px;
          }

          .video-btn {
            width: 244px;
            height: 80px;
            background: rgba(12, 153, 219, 1);
            border-radius: 8px;
            line-height: 80px;
            color: #ffffff;
            cursor: pointer;

            &.full-btn {
              width: auto;
            }
          }
        }
      }
    }

    .video-small-item {
      width: 30%;
      height: 100%;
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;

      .video-flex {
        width: 50%;
        height: 50%;

        .select-small-video {
          width: 418px;
          height: 80px;
          margin: 0 0 40px 0;
        }

        .set-video-small {
          width: 455px;
          height: 316px;
          background: rgba(219, 219, 219, 1);
          border: 1px solid rgba(214, 214, 214, 1);
          position: relative;

          .icon-video {
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            margin: auto;
            width: 244px;
            height: 150px;
            text-align: center;

            .iconfont {
              color: #a4a4a4;
              font-size: 57px;
            }

            .video-btn {
              width: 244px;
              height: 80px;
              background: rgba(12, 153, 219, 1);
              border-radius: 8px;
              line-height: 80px;
              color: #ffffff;
              cursor: pointer;
            }
          }
        }

        &.video-flex-item {
          margin-top: 40px;
        }
      }
    }
  }

  .vx-content {
    width: 100%;
    height: 1100px;

    .el-transfer-panel {
      height: 1080px;
    }

    .vx-content-item {
      height: 900px;
      width: 1000px;
      float: left;
      margin-right: 100px;

      &.vx-content-margin {
        margin-left: 300px;
      }

      .vx-title {
        width: 880px;
        height: 118px;
        background: linear-gradient(
          0deg,
          rgba(12, 153, 219, 0.2) 0%,
          rgba(255, 255, 255, 0) 100%
        );
        margin: 0 auto;
        line-height: 118px;
        text-align: left;

        i {
          font-size: 48px;
          margin: 0 25px 0 20px;
          background: linear-gradient(
            -90deg,
            rgba(0, 215, 254, 1) 0%,
            rgba(0, 150, 255, 1) 100%
          );
          // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }

      .vx-input {
        margin: 32px 0 23px 0;
        width: 884px;
        height: 80px;

        input {
          border: none;
        }
        .el-input .el-input__inner {
          width: 100%;
          font-size: 36px;
          font-family: Microsoft YaHei;
          color: #639ff1;
          background-color: transparent;
          padding-left: 50px;
          height: 80px;
          line-height: 80px;
          border: 2px solid rgba(214, 214, 214, 1);
          border-radius: 40px;
        }
      }

      .vx-list {
        width: 884px;
        height: 500px;
        margin: 0 auto;
        overflow: hidden;

        ul {
          width: 100%;
          height: 100%;

          li {
            width: 923px;
            height: 98px;
            font-size: 40px;
            line-height: 98px;
            cursor: pointer;

            &.active {
              background: rgba(12, 153, 219, 0.1);
            }

            :visited {
              background: green;
            }

            i {
              font-size: 50px;
              line-height: 98px;
              margin: 0 23px 0 46px;
              color: #0c99db;
            }
          }
        }
      }
    }

    .vx-content-item-1 {
      height: 900px;
      width: 2600px;
      float: left;
    }
  }

  .db-content {
    position: relative;
    margin-bottom: 40px;
    padding-left: 27px;
    height: 160px;

    .db-label {
      float: left;
      width: 260px;
      font-size: 52px;
      font-weight: bold;
    }

    .db-input {
      float: left;
      width: 1020px;
      line-height: 80px;
      font-size: 52px;
      font-weight: bold;
      cursor: pointer;
    }
  }

  .btn {
    width: 100%;
    height: 220px;
    // background-color: #0ff;
    display: inline-flex;
    justify-content: center;
    border-top: 2px solid #d6d6d6;

    .item {
      width: 168px;
      height: 80px;
      margin-top: 80px;
      text-align: center;
    }

    .item_btn1 {
      font-size: 40px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: rgba(12, 153, 219, 1);
      line-height: 80px;
      cursor: pointer;
    }

    .item_btn2 {
      background: linear-gradient(
        176deg,
        rgba(0, 168, 255, 1) 0%,
        rgba(0, 98, 209, 1) 100%
      );
      border-radius: 8px;
      line-height: 80px;
      color: #fff;
      cursor: pointer;
    }
  }
}
</style>
<style lang="scss">
.VideoInspectionModule {
  .addDialog {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background: rgba(0, 0, 0, 0.5);

    .add-dialog-content {
      width: 2174px;
      height: 1965px;
      background: white;
      border-radius: 10px;
      margin: 238px auto 0;
      position: relative;

      .add-dialog-title {
        width: 100%;
        height: 203px;
        border-bottom: 4px solid #d9e1e5;
        display: inline-flex;
        justify-content: center;
        align-items: center;

        .title-content {
          width: 320px;
          height: 100%;
          font-size: 48px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(51, 51, 51, 1);
          line-height: 203px;

          i:nth-of-type(1) {
            font-size: 48px;
            margin: 0 25px 0 20px;
            background: linear-gradient(
              -90deg,
              rgba(0, 215, 254, 1) 0%,
              rgba(0, 150, 255, 1) 100%
            );
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }

          i:nth-of-type(2) {
            font-size: 48px;
            margin: 0 25px 0 20px;
            background: linear-gradient(
              -90deg,
              rgba(0, 150, 255, 1) 0%,
              rgba(0, 215, 254, 1) 100%
            );
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
        }

        .close-icon {
          position: absolute;
          right: 73px;
          top: 78px;
          width: 48px;
          height: 48px;
          border-radius: 2px;

          i {
            font-size: 48px;
            color: rgba(182, 182, 182, 1);
          }
        }
      }
    }

    .transfer-content {
      width: 100%;
      height: 1400px;
      display: inline-flex;
      justify-content: center;

      .btn {
        position: absolute;
        bottom: 0;
        width: 100%;
        height: 280px;
        // background-color: #0ff;
        display: inline-flex;
        justify-content: center;
        border-top: 2px solid #d6d6d6;

        .item {
          width: 168px;
          height: 80px;
          margin-top: 80px;
          text-align: center;
        }

        .item_btn1 {
          font-size: 40px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(12, 153, 219, 1);
          line-height: 80px;
          cursor: pointer;
        }

        .item_btn2 {
          background: linear-gradient(
            176deg,
            rgba(0, 168, 255, 1) 0%,
            rgba(0, 98, 209, 1) 100%
          );
          border-radius: 8px;
          line-height: 80px;
          color: #fff;
          cursor: pointer;
        }
      }
    }

    .search-warp {
      margin-top: 20px;
      padding: 40px 60px;

      .el-input__inner {
        padding-left: 60px;
      }
    }

    .number-setting {
      height: 1200px;
      padding: 40px 60px;
      overflow-x: hidden;
      overflow-y: auto;

      .el-radio {
        width: 30%;
      }
    }

    .foot-btn {
      position: absolute;
      bottom: 0;
      width: 100%;
      height: 280px;
      // background-color: #0ff;
      display: inline-flex;
      justify-content: center;
      border-top: 2px solid #d6d6d6;

      .item {
        width: 168px;
        height: 80px;
        margin-top: 80px;
        text-align: center;
      }

      .item_btn1 {
        font-size: 40px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: rgba(12, 153, 219, 1);
        line-height: 80px;
        cursor: pointer;
      }

      .item_btn2 {
        background: linear-gradient(
          176deg,
          rgba(0, 168, 255, 1) 0%,
          rgba(0, 98, 209, 1) 100%
        );
        border-radius: 8px;
        line-height: 80px;
        color: #fff;
        cursor: pointer;
      }
    }
  }
}
</style>
