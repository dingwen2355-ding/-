<template>
  <div class="videoMonitoring">
    <el-dialog
      title="视频配置"
      :visible.sync="dialogVisible"
      width="90%"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div>
        <div class="video-tab">
          <div class="tab-item item2" :class="{ activeClass: currentTab === '0' }" @click="changeTab('0')">
            右侧视频配置
          </div>
          <div class="tab-item item2" :class="{ activeClass: currentTab === '1' }" @click="changeTab('1')">
            全屏视频配置
          </div>
        </div>
        <div class="right-content" style="justify-content: left" v-if="currentTab === '0'">
          <div class="right-content-list">
            <div class="right-title"><i class="iconfont iconzhgl_title"></i><span>视频配置</span></div>
            <div class="right-list">
              <li v-for="(v, i) in valueList" :key="v.device_id + i">
                <span class="num">{{ i + 1 }}</span>
                <span class="name">
                  {{ v.device_name }}
                </span>
                <span class="del">
                  <i class="el-icon-delete-solid" @click="deleteItem('value', v, i)"></i>
                </span>
              </li>
            </div>
            <div class="right-add-btn" @click="showAddDialog('fixedVideo')"><i class="el-icon-plus"></i>新增</div>
          </div>
        </div>
        <div class="video-content" v-if="currentTab === '1'">
          <div class="video-item">
            <div class="select-video">
              <el-select
                v-model="value1"
                placeholder="视频类型选择"
                style="width: 100%"
                @change="changeFullType(1, $event)"
              >
                <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
                </el-option>
              </el-select>
            </div>
            <div class="set-video">
              <div class="icon-video">
                <i class="iconfont iconvideo_play02"></i>
                <div class="video-btn full-btn">
                  <!--              {{full1===''?'选择视频':full1}}-->
                  <div class="full-btn1" @click="handleFull(1, 1)" :title="full1.deviceName" v-if="value1 === 666">
                    {{ full1.deviceName ? full1.deviceName : '选择视频' }}
                  </div>
                  <div class="full-btn1" @click="handleFull(1, 2)" :title="full1.deviceName" v-else-if="value1 === 31">
                    {{ full1.deviceName ? full1.deviceName : '选择视频' }}
                  </div>
                  <el-select v-else class="video-btn full-btn" v-model="full1" filterable placeholder="选择视频">
                    <el-option
                      class="excend"
                      v-for="item in fullList1"
                      :key="item.deviceId"
                      :label="item.deviceName"
                      :value="item.deviceId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </div>
            </div>
          </div>
          <div class="video-item">
            <div class="select-video">
              <el-select
                v-model="value2"
                placeholder="视频类型选择"
                style="width: 100%"
                @change="changeFullType(2, $event)"
              >
                <el-option v-for="item in videoTemp" :key="item.deviceKind" :label="item.name" :value="item.deviceKind">
                </el-option>
              </el-select>
            </div>
            <div class="set-video">
              <div class="icon-video">
                <i class="iconfont iconvideo_play02"></i>
                <div class="video-btn full-btn">
                  <!--              {{full2===''?'选择视频':full2}}-->
                  <!-- <el-select class="video-btn full-btn" v-model="full2" filterable placeholder="选择视频">
                <el-option v-for="item in fullList2" :key="item.deviceId" :label="item.deviceName" :value="item.deviceId"> </el-option>
              </el-select> -->
                  <div class="full-btn1" @click="handleFull(2, 1)" :title="full2.deviceName" v-if="value2 === 666">
                    {{ full2.deviceName ? full2.deviceName : '选择视频' }}
                  </div>
                  <div class="full-btn1" @click="handleFull(2, 2)" :title="full2.deviceName" v-else-if="value2 === 31">
                    {{ full2.deviceName ? full2.deviceName : '选择视频' }}
                  </div>
                  <el-select v-else class="video-btn full-btn" v-model="full2" filterable placeholder="选择视频">
                    <el-option
                      class="excend"
                      v-for="item in fullList2"
                      :key="item.deviceId"
                      :label="item.deviceName"
                      :value="item.deviceId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </div>
            </div>
          </div>
          <div class="video-small-item">
            <div class="video-flex">
              <div class="select-small-video">
                <el-select
                  v-model="value3"
                  placeholder="视频类型选择"
                  style="width: 100%"
                  @change="changeFullType(3, $event)"
                >
                  <el-option
                    v-for="item in videoTemp"
                    :key="item.deviceKind"
                    :label="item.name"
                    :value="item.deviceKind"
                  >
                  </el-option>
                </el-select>
              </div>
              <div class="set-video-small">
                <div class="icon-video">
                  <i class="iconfont iconvideo_play02"></i>
                  <div class="video-btn full-btn">
                    <!--                {{full3===''?'选择视频':full3}}-->
                    <!-- <el-select class="video-btn full-btn" filterable v-model="full3" placeholder="选择视频">
                  <el-option v-for="item in fullList3" :key="item.deviceId" :label="item.deviceName" :value="item.deviceId"> </el-option>
                </el-select> -->
                    <div class="full-btn1" @click="handleFull(3, 1)" :title="full3.deviceName" v-if="value3 === 666">
                      {{ full3.deviceName ? full3.deviceName : '选择视频' }}
                    </div>
                    <div
                      class="full-btn1"
                      @click="handleFull(3, 2)"
                      :title="full3.deviceName"
                      v-else-if="value3 === 31"
                    >
                      {{ full3.deviceName ? full3.deviceName : '选择视频' }}
                    </div>
                    <el-select v-else class="video-btn full-btn" v-model="full3" filterable placeholder="选择视频">
                      <el-option
                        class="excend"
                        v-for="item in fullList3"
                        :key="item.deviceId"
                        :label="item.deviceName"
                        :value="item.deviceId"
                      >
                      </el-option>
                    </el-select>
                  </div>
                </div>
              </div>
            </div>
            <div class="video-flex">
              <div class="select-small-video">
                <el-select
                  v-model="value4"
                  placeholder="视频类型选择"
                  style="width: 100%"
                  @change="changeFullType(4, $event)"
                >
                  <el-option
                    v-for="item in videoTemp"
                    :key="item.deviceKind"
                    :label="item.name"
                    :value="item.deviceKind"
                  >
                  </el-option>
                </el-select>
              </div>
              <div class="set-video-small">
                <div class="icon-video">
                  <i class="iconfont iconvideo_play02"></i>
                  <div class="video-btn full-btn">
                    <!--                {{full4===''?'选择视频':full4}}-->
                    <!-- <el-select class="video-btn full-btn" v-model="full4" filterable placeholder="选择视频">
                  <el-option v-for="item in fullList4" :key="item.deviceId" :label="item.deviceName" :value="item.deviceId"> </el-option>
                </el-select> -->
                    <div class="full-btn1" @click="handleFull(4, 1)" :title="full4.deviceName" v-if="value4 === 666">
                      {{ full4.deviceName ? full4.deviceName : '选择视频' }}
                    </div>
                    <div
                      class="full-btn1"
                      @click="handleFull(4, 2)"
                      :title="full4.deviceName"
                      v-else-if="value4 === 31"
                    >
                      {{ full4.deviceName ? full4.deviceName : '选择视频' }}
                    </div>
                    <el-select v-else class="video-btn full-btn" v-model="full4" filterable placeholder="选择视频">
                      <el-option
                        class="excend"
                        v-for="item in fullList4"
                        :key="item.deviceId"
                        :label="item.deviceName"
                        :value="item.deviceId"
                      >
                      </el-option>
                    </el-select>
                  </div>
                </div>
              </div>
            </div>
            <div class="video-flex video-flex-item">
              <div class="select-small-video">
                <el-select
                  v-model="value5"
                  placeholder="视频类型选择"
                  style="width: 100%"
                  @change="changeFullType(5, $event)"
                >
                  <el-option
                    v-for="item in videoTemp"
                    :key="item.deviceKind"
                    :label="item.name"
                    :value="item.deviceKind"
                  >
                  </el-option>
                </el-select>
              </div>
              <div class="set-video-small">
                <div class="icon-video">
                  <i class="iconfont iconvideo_play02"></i>
                  <div class="video-btn full-btn">
                    <!--                {{full5===''?'选择视频':full5}}-->
                    <!-- <el-select class="video-btn full-btn" v-model="full5" filterable placeholder="选择视频">
                  <el-option v-for="item in fullList5" :key="item.deviceId" :label="item.deviceName" :value="item.deviceId"> </el-option>
                </el-select> -->
                    <div class="full-btn1" @click="handleFull(5, 1)" :title="full5.deviceName" v-if="value5 === 666">
                      {{ full5.deviceName ? full5.deviceName : '选择视频' }}
                    </div>
                    <div
                      class="full-btn1"
                      @click="handleFull(5, 2)"
                      :title="full5.deviceName"
                      v-else-if="value5 === 31"
                    >
                      {{ full5.deviceName ? full5.deviceName : '选择视频' }}
                    </div>
                    <el-select v-else class="video-btn full-btn" v-model="full5" filterable placeholder="选择视频">
                      <el-option
                        class="excend"
                        v-for="item in fullList5"
                        :key="item.deviceId"
                        :label="item.deviceName"
                        :value="item.deviceId"
                      >
                      </el-option>
                    </el-select>
                  </div>
                </div>
              </div>
            </div>
            <div class="video-flex video-flex-item">
              <div class="select-small-video">
                <el-select
                  v-model="value6"
                  placeholder="视频类型选择"
                  style="width: 100%"
                  @change="changeFullType(6, $event)"
                >
                  <el-option
                    v-for="item in videoTemp"
                    :key="item.deviceKind"
                    :label="item.name"
                    :value="item.deviceKind"
                  >
                  </el-option>
                </el-select>
              </div>
              <div class="set-video-small">
                <div class="icon-video">
                  <i class="iconfont iconvideo_play02"></i>
                  <div class="video-btn full-btn">
                    <!--                {{full6===''?'选择视频':full6}}-->
                    <!-- <el-select class="video-btn full-btn" v-model="full6" filterable placeholder="选择视频">
                  <el-option v-for="item in fullList6" :key="item.deviceId" :label="item.deviceName" :value="item.deviceId"> </el-option>
                </el-select> -->
                    <div class="full-btn1" @click="handleFull(6, 1)" :title="full6.deviceName" v-if="value6 === 666">
                      {{ full6.deviceName ? full6.deviceName : '选择视频' }}
                    </div>
                    <div
                      class="full-btn1"
                      @click="handleFull(6, 2)"
                      :title="full6.deviceName"
                      v-else-if="value6 === 31"
                    >
                      {{ full6.deviceName ? full6.deviceName : '选择视频' }}
                    </div>
                    <el-select v-else class="video-btn full-btn" v-model="full6" filterable placeholder="选择视频">
                      <el-option
                        class="excend"
                        v-for="item in fullList6"
                        :key="item.deviceId"
                        :label="item.deviceName"
                        :value="item.deviceId"
                      >
                      </el-option>
                    </el-select>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="btn">
          <div class="item item_btn1" @click="handleClose">取消</div>
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
              <div style="display: flex">
                <div class="leftForm">
                  <div class="topTitle">
                    可选列表<span style="float: right">共 {{ leftForm.count }} 条</span>
                  </div>
                  <div style="display: flex; margin-top: 5px">
                    <el-input placeholder="请输入搜索内容" v-model="leftForm.search" style="width: 100%"></el-input>
                    <el-button
                      style="font-size: 16px; padding: 7px 20px"
                      icon="el-icon-search"
                      @click="leftSearch"
                    ></el-button>
                  </div>
                  <div class="mainContent">
                    <el-checkbox-group v-model="leftForm.chooseList">
                      <el-checkbox
                        class="leftItem"
                        v-for="(item, index) in addList"
                        :key="index"
                        :label="item"
                        :title="item.device_name"
                      >
                        {{ item.device_name }}
                      </el-checkbox>
                    </el-checkbox-group>
                  </div>
                  <el-pagination
                    style="margin-left: -50px; margin-top: 5px"
                    @current-change="pageCheck"
                    :current-page.sync="leftForm.currentPage"
                    :page-size="leftForm.pageLen"
                    layout="prev, pager, next"
                    :total="leftForm.count"
                  >
                  </el-pagination>
                </div>
                <div class="centerBtn">
                  <span class="chooseBtn" @click="leftClick">
                    <i class="el-icon-arrow-left"></i>
                  </span>
                  <span class="chooseBtn" @click="rightClick">
                    <i class="el-icon-arrow-right"></i>
                  </span>
                </div>
                <div class="leftForm">
                  <div class="topTitle">已选列表</div>
                  <div style="display: flex; margin-top: 5px">
                    <el-input placeholder="请输入搜索内容" v-model="rightForm.search" style="width: 100%"></el-input>
                    <el-button
                      style="font-size: 16px; padding: 7px 20px"
                      icon="el-icon-search"
                      @click="rightSearch"
                    ></el-button>
                  </div>
                  <div class="mainContent">
                    <el-checkbox-group v-model="rightForm.chooseList">
                      <el-checkbox class="leftItem" v-for="(item, index) in rightForm.list" :key="index" :label="item">
                        {{ item.device_name }}
                      </el-checkbox>
                    </el-checkbox-group>
                  </div>
                </div>
              </div>
              ©
              <div class="btn">
                <div class="item item_btn1" @click="addDialogShow = false">取消</div>
                <div class="item item_btn2" @click="submitResult">提交</div>
              </div>
            </div>
          </div>
        </div>
        <div class="addDialog" v-show="addDialogRight">
          <div class="add-dialog-content" style="width: 600px">
            <div class="add-dialog-title">
              <div class="title-content">
                <i class="iconfont iconzhgl_title"></i>
                <span>新增</span>
                <i class="iconfont iconzhgl_title"></i>
              </div>
              <div class="close-icon" @click="addDialogRight = false">
                <i class="el-icon-close"></i>
              </div>
            </div>
            <div class="transfer-content">
              <div style="display: flex">
                <div class="leftForm">
                  <div class="topTitle">
                    可选列表<span style="float: right">共 {{ allForm.count }} 条</span>
                  </div>
                  <div style="display: flex; margin-top: 5px">
                    <el-input placeholder="请输入搜索内容" v-model="allForm.search" style="width: 100%"></el-input>
                    <el-button
                      style="font-size: 16px; padding: 7px 20px"
                      icon="el-icon-search"
                      @click="allSearch"
                    ></el-button>
                  </div>
                  <div class="mainContent" style="overflow-x: hidden">
                    <el-radio-group v-model="allForm.chooseList">
                      <el-radio class="leftItem" v-for="(item, index) in fullList" :key="index" :label="item">
                        {{ item.deviceName }}
                      </el-radio>
                    </el-radio-group>
                  </div>
                  <el-pagination
                    style="margin-left: -50px; margin-top: 5px"
                    @current-change="pageCheck1"
                    :current-page.sync="allForm.currentPage"
                    :page-size="allForm.pageLen"
                    layout="prev, pager, next"
                    :total="allForm.count"
                  >
                  </el-pagination>
                </div>
              </div>
              <div class="btn">
                <div class="item item_btn1" @click="addDialogRight = false">取消</div>
                <div class="item item_btn2" @click="submitRight">提交</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from '@/utils/request'
import $ from 'jquery'
import axios from '@/utils/request';

export default {
  name: 'videoMonitoring',
  props: ['dialogVisible', 'eventId'],
  data() {
    return {
      currentTab: '0',
      activeName: 'first',
      addDialogtotal: 0,
      name: '',
      valueTemp1: '',
      valueTemp2: '1',
      value1: '',
      value2: '',
      value3: '',
      value4: '',
      value5: '',
      value6: '',
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
      valueList: [],
      valueListBak: [],
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
      searchCallingText: '',
      searchCallingNumberList: [],
      callingNumberSettingShow: false,
      leftCheckedKeys: [],
      rightCheckedKeys: [],
      none: 0,
      showDatas: [],
      page: {pageNo: 1, pageSize: 20, total: 0},
      checkValue: [],
      currentDatas: [],
      sourceDatas: [],
      leftForm: {
        pageStart: 1,
        pageLen: 20,
        search: '',
        chooseList: [],
        currentPage: 1,
        count: 0
      },
      allForm: {
        pageStart: 1,
        pageLen: 20,
        search: '',
        chooseList: [],
        currentPage: 1,
        count: 0
      },
      resetForm: {
        list: []
      },
      rightForm: {
        pageStart: 1,
        pageLen: 15,
        search: '',
        list: [],
        chooseList: [],
        currentPage: 1,
        searchList: [],
        listALL: []
      },
      isSearch: false,
      showform: false,
      showTrue: false,
      addDialogRight: false,
      addDialogSolo: false,
      showRight: '',
      fullList: []
    }
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        // this.eventId = '2022060875322329123'
        this.activeName = 'first'
        this.currentTab = '0'
        this.leftForm.count = 0
        this.query(this.eventId)
        this.queryDeviceKindList()
        if (this.eventId) {
          this.queryAll()
        }
      }
    }
  },
  methods: {
    leftSearch() {
      this.leftForm.currentPage = 1
      this.showform = true
      this.queryAll()
    },
    allSearch() {
      this.allForm.currentPage = 1
      let type = this.showRight
      if (this.showType === 1) {
        this.queryFullAll(type, 666)
      } else {
        this.queryFullAll(type, 31)
      }
    },
    pageCheck(val) {
      this.leftForm.currentPage = val
      this.showform = true
      this.queryAll()
    },
    pageCheck1(val) {
      this.allForm.currentPage = val
      let type = this.showRight
      this.allForm.chooseList = []
      if (this.showType === 1) {
        this.queryFullAll(type, 666)
      } else {
        this.queryFullAll(type, 31)
      }
    },
    rightClick() {
      this.rightForm.list = this.rightForm.list.concat(this.leftForm.chooseList)
      if (this.rightForm.list.length > 0) {
        this.rightForm.list = this.unique(this.rightForm.list)
      }
      this.leftForm.chooseList = []
      this.leftForm.search = ''
      this.rightForm.search = ''
      this.showform = true
      this.rightForm.listALL = []
      this.rightForm.listALL = JSON.parse(JSON.stringify(this.rightForm.list))
      this.rightSearch()
    },
    unique(arr) {
      for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
          if (arr[i].device_id === arr[j].device_id && arr[i].device_name === arr[j].device_name) {
            arr.splice(j, 1)
            j--
          }
        }
      }
      return arr
    },
    leftClick() {
      let len = this.rightForm.chooseList.length
      this.rightForm.listALL.forEach((item, index) => {
        this.rightForm.chooseList.forEach((i, n) => {
          if (i.device_id === item.device_id && i.device_name === item.device_name) {
            this.rightForm.listALL.splice(index, len)
          }
        })
      })
      this.rightForm.chooseList = []
      this.leftForm.search = ''
      this.rightForm.search = ''
      this.rightSearch()
    },
    rightSearch() {
      this.rightForm.list = []
      if (this.rightForm.search === '') {
        this.rightForm.list = this.rightForm.listALL
      } else {
        this.rightForm.list = this.rightForm.listALL.filter((r) => r.device_name.includes(this.rightForm.search))
      }
      this.queryAll()
    },
    changeTab(v) {
      this.currentTab = v
      if (v === '1') {
        this.value1 = ''
        this.value2 = ''
        this.value3 = ''
        this.value4 = ''
        this.value5 = ''
        this.value6 = ''
        this.fullList1 = []
        this.fullList2 = []
        this.fullList3 = []
        this.fullList4 = []
        this.fullList5 = []
        this.fullList6 = []
        this.queryFullAll(0)
      }
    },
    showAddDialog(type) {
      debugger
      if (this.valueList.length > 0) {
        this.rightForm.list = this.valueList
        this.rightForm.listALL = []
        this.rightForm.listALL = JSON.parse(JSON.stringify(this.rightForm.list))
      }
      this.showAdd(type)
    },
    showAdd(type) {
      if (this.currentTab === '0') {
        this.type = type
        this.value = []
        this.addDialogShow = true
        this.addList = this.videoListAll['consultationVideo']
          .concat(this.videoListAll['mobileIndividual'])
          .concat(this.videoListAll['fixedVideo'])
        this.videoList[type].forEach((v) => {
          this.value.push(v.device_id)
        })
      } else if (this.currentTab === '1') {
        this.fullVideoType = type
        this.addDialogShow1 = true
      }
    },
    handleFull(type, i) {
      this.showRight = ''
      this.showType = ''
      this.showRight = type
      this.addDialogRight = true
      this.allForm.chooseList = []
      this.showType = i
      if (i === 1) {
        this.queryFullAll(type, 666)
      } else {
        this.queryFullAll(type, 31)
      }
    },
    submitRight() {
      if (this.showRight === 1) {
        this.full1 = this.allForm.chooseList
        console.log('二分威风威风', this.full1)
      } else if (this.showRight === 2) {
        this.full2 = this.allForm.chooseList
      } else if (this.showRight === 3) {
        this.full3 = this.allForm.chooseList
      } else if (this.showRight === 4) {
        this.full4 = this.allForm.chooseList
      } else if (this.showRight === 5) {
        this.full5 = this.allForm.chooseList
      } else if (this.showRight === 6) {
        this.full6 = this.allForm.chooseList
      }
      this.allForm.search = ''
      this.allForm.currentPage = 1
      this.addDialogRight = false
    },
    submitResult() {
      this.valueList = []
      if (this.rightForm.list.length > 0) {
        // this.rightForm.list.forEach((v) => {
        //   this.addList.forEach((x) => {
        //     if (v.device_id === x.device_id) {
        //       this.valueList.push(x)
        //     }
        //   })
        // })
        this.valueList = this.rightForm.list
      }
      console.log(this.valueList, this.rightForm.list)
      this.addDialogShow = false
      this.rightForm.list = []
      this.leftForm.currentPage = 1
      this.showform = false
      this.queryAll()
    },
    submitAll() {
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
          tmp[key].forEach((v) => {
            params[key].push(v.device_id)
          })
          params[key] = params[key].join()
        }
        let url = '/cdapis/fusion/updateEventDeviceIds?eventId=' + this.eventId
        if (this.value.length > 0) {
          url += '&deviceIds=' + this.value.join()
        } else if (this.valueList.length > 0) {
          let valueLists = []
          valueLists = this.valueList.map((item) => {
            return item.deviceId
          })
          url += '&deviceIds=' + valueLists.join()
        }
        Axios.post(url).then((res) => {
          if (res.data.code === 200) {
            this.$message.success('提交成功')
            this.query()
            this.handleClose()
            this.$root.eventBus.$emit('refreshIframe')
          } else {
            this.$message.error('提交失败')
          }
        })
      } else if (this.currentTab === '1') {
        let deviceIds =
          (this.full1.deviceId ? this.full1.deviceId : this.full1) +
          ',' +
          (this.full2.deviceId ? this.full2.deviceId : this.full2) +
          ',' +
          (this.full3.deviceId ? this.full3.deviceId : this.full3) +
          ',' +
          (this.full4.deviceId ? this.full4.deviceId : this.full4) +
          ',' +
          (this.full5.deviceId ? this.full5.deviceId : this.full5) +
          ',' +
          (this.full6.deviceId ? this.full6.deviceId : this.full6)
        let url = '/cdapis/updateFusionMax?eventId=' + this.eventId + '&type=1' + '&deviceIds=' + deviceIds
        Axios.post(url).then((res) => {
          if (res.data.code === 200) {
            this.$message.success('请求成功')
            this.queryFullAll(0)
            this.handleClose()
            this.$root.eventBus.$emit('refreshIframe')
          } else {
            this.$message.error('请求失败')
          }
        })
        let url1 = '/binhuapis/dapeng/event/edit-device?eventId=' + this.eventId + '&deviceIds=' + deviceIds
        Axios.post(url1).then((res) => {
          if (res.data.code === 200) {
            this.$message.success('请求成功')
            // this.queryFullAll(0)
            this.handleClose()
            this.$root.eventBus.$emit('refreshIframe')
          } else {
            this.$message.error('请求失败')
          }
        })
      }
    },
    deleteItem(type, v, i) {
      if (type === 'value') {
        this.valueList.splice(i, 1)
        this.value.splice(i, 1)
      } else {
        this.videoList[type].splice(i, 1)
      }
    },
    queryAll() {
      let url = '/cdapis/queryCameraInfoByType'
      url += '?pageNum=' + this.leftForm.currentPage + '&pageSize=' + this.leftForm.pageLen + '&deviceKind=666'
      // if (this.eventId) {
      //   url += '&eventId=' + this.eventId
      // }
      if (this.leftForm.search) {
        url += '&name=' + this.leftForm.search
      }
      Axios.get(url).then((res) => {
        if (res.data.data.list.length > 0) {
          for (let key in this.videoListAll) {
            this.videoListAll[key] = []
          }
          let data = res.data.data.list
          this.leftForm.count = res.data.data.count
          this.showData(data)
          if (this.showform) {
            this.showAdd('fixedVideo')
          }
        } else {
          this.leftForm.count = 0
          this.addList = []
        }
      })
    },
    showData(data) {
      data.forEach((v) => {
        v.device_name = v.deviceName
        v.device_id = v.deviceId
        if (!v.device_name) {
          v.device_name = v.device_id
        }
        // this.videoListAll.fixedVideo.push(v)
      })
      data.forEach((v) => {
        if (v.deviceKind === 6 || v.deviceKind === 666) {
          this.videoListAll.fixedVideo.push(v)
        } else if (v.deviceKind === 31) {
          // gaodadanbing
          this.videoListAll.mobileIndividual.push(v)
        } else if (v.deviceKind === 32) {
          this.videoListAll.mobileIndividual.push(v)
        } else if (v.deviceKind === 33) {
          this.videoListAll.consultationVideo.push(v)
        }
      })
    },
    query() {
      if (!this.eventId) return
      let url = '/cdapis/fusion/getVideoListByEventId?eventId=' + this.eventId
      Axios.get(url)
        .then((res) => {
          for (let key in this.videoList) {
            this.videoList[key] = []
          }
          this.value = []
          this.valueList = []
          let data = res.data.data
          data.forEach((v) => {
            v.device_name = v.deviceName
            v.device_id = v.deviceId
            if (!v.device_name) {
              v.device_name = v.device_id
            }
            // this.videoList.fixedVideo.push(v)
            // this.videoListAll.fixedVideo.push(v)
            this.valueList.push(v)
          })
          console.log(this.valueList)
          this.videoListBak = JSON.parse(JSON.stringify(this.videoList))
          this.valueListBak = JSON.parse(JSON.stringify(this.valueList))
        })
        .catch((e) => {
          for (let key in this.videoList) {
            this.videoList[key] = []
          }
          this.value = []
          this.valueList = []
        })
    },
    reset() {
      if (this.currentTab === '0') {
        this.videoList = JSON.parse(JSON.stringify(this.videoListBak))
        this.valueList = JSON.parse(JSON.stringify(this.valueListBak))
      } else if (this.currentTab === '1') {
        this.queryFullAll(0)
      } else if (this.currentTab === '2') {
        // this.queryAllChat()
      }
    },
    queryChat() {
      if (!this.eventId) return
      this.peopleChecked = []
      let url = '/cdapis/queryWxMemberByEventId?eventId=' + this.eventId
      Axios.get(url).then((res) => {
        let data = res.data.data
        this.currentChat = data.member
        this.name = data.groupName
        for (let key in this.currentChat) {
          this.currentChat[key].forEach((v) => {
            this.peopleChecked.push(v.account)
          })
        }
      })
    },
    queryAllChat() {
      let url = '/cdapis/queryWXPersonInfoExceptEventId'
      Axios.get(url).then((res) => {
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
    changeDept(value, key, index) {
      this.deptType = key
      this.deptChecked = index
      this.$nextTick(() => {
        $('.vx-content-item-1 .el-transfer-panel:first-child .people-list').parent().parent().css('cssText', '')
        $('.vx-content-item-1 .el-transfer-panel:first-child .people-list.isNone')
          .parent()
          .parent()
          .css('cssText', 'display:none!important')
      })
    },
    changePeople(c, direction, key) {
      if (direction === 'right') {
        // setTimeout(() => {
        //   this.peopleChecked = this.peopleChecked.filter(v => {
        //     return !this.exceptPeple.includes(v)
        //   })
        // })
      }
      this.$nextTick(() => {
        this.peopleChecked = this.peopleChecked.filter((v) => {
          return !this.exceptPeple.includes(v)
        })
        this.$nextTick(() => {
          $('.vx-content-item-1 .el-transfer-panel:first-child .people-list').parent().parent().css('cssText', '')
          $('.vx-content-item-1 .el-transfer-panel:first-child .people-list.isNone')
            .parent()
            .parent()
            .css('cssText', 'display:none!important')
        })
      })
    },
    leftChange(checked, change) {
      this.exceptPeple = []
      if (this.deptType) {
        checked.forEach((v) => {
          let isTrue = this.chatAll[this.deptType].some((x) => {
            return v === x.account
          })
          if (!isTrue) {
            this.exceptPeple.push(v)
          }
        })
      }
    },
    rightChange(checked, change) {
    },
    changeFullType(v, event) {
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
    queryFullAll(numType, event) {
      let url = '/cdapis/queryCameraInfoByType'
      if (event) {
        if (event !== 666) {
          if (event === 31) {
            url +=
              '?deviceKind=' +
              event +
              '&devtype=' +
              '威尔信' +
              '&pageNum=' +
              this.allForm.currentPage +
              '&pageSize=' +
              this.allForm.pageLen
          } else {
            url += '?deviceKind=' + event
          }
        } else {
          url += '?deviceKind=' + event + '&pageNum=' + this.allForm.currentPage + '&pageSize=' + this.allForm.pageLen
        }
        if (this.eventId) {
          url += '&eventId=' + this.eventId
        }
        if (this.allForm.search) url += '&name=' + this.allForm.search
      }
      Axios.get(url).then((res) => {
        for (let key in this.fullListBak) {
          this.fullListBak[key] = []
        }
        let data = []
        data = res.data.data
        if (event === 666 || event === 31) {
          this.fullList = res.data.data.list
          this.allForm.count = res.data.data.count
        } else {
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
              // eslint-disable-next-line no-case-declarations
              let tmp = []
              for (let key in this.fullListBak) {
                tmp = tmp.concat(this.fullListBak[key])
              }
              this.fullList1 = this.fullList2 = this.fullList3 = this.fullList4 = this.fullList5 = this.fullList6 = tmp
              break
          }
        }
        if (numType === 0) {
          this.queryTemp()
        }
      })
    },
    queryFullAll1(e) {
      let url = '/cdapis/queryCameraInfoByType'
      if (this.eventId) {
        url += '?eventId=' + this.eventId
      }
      Axios.get(url).then((res) => {
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
          if (e[0]) {
            if (e[0].deviceKind === 666 || e[0].deviceKind === 31) {
              this.full1 = e[0]
            } else {
              this.full1 = e[0].deviceId
            }
          }
          if (e[1]) {
            if (e[1].deviceKind === 666 || e[1].deviceKind === 31) {
              this.full2 = e[1]
            } else {
              this.full2 = e[1].deviceId
            }
          }
          if (e[2]) {
            if (e[2].deviceKind === 666 || e[2].deviceKind === 31) {
              this.full3 = e[2]
            } else {
              this.full3 = e[2].deviceId
            }
          }
          if (e[3]) {
            if (e[3].deviceKind === 666 || e[3].deviceKind === 31) {
              this.full4 = e[3]
            } else {
              this.full4 = e[3].deviceId
            }
          }
          if (e[4]) {
            if (e[4].deviceKind === 666 || e[4].deviceKind === 31) {
              this.full5 = e[4]
            } else {
              this.full5 = e[4].deviceId
            }
          }
          if (e[5]) {
            if (e[5].deviceKind === 666 || e[5].deviceKind === 31) {
              this.full6 = e[5]
            } else {
              this.full6 = e[5].deviceId
            }
          }
        }
      })
    },
    queryDeviceKindList(e) {
      let url = '/cdapis/queryDeviceKindList'
      Axios.get(url).then((res) => {
        let data = res.data.data || {}
        if (data && data.length > 0) {
          this.videoTemp = data
        }
      })
    },
    queryTemp() {
      if (!this.eventId) return
      let url = '/cdapis/fusionMax?eventId=' + this.eventId
      Axios.get(url).then((res) => {
        let data = res.data.data || {}
        this.tempData = data.deviceInfos || []
        this.tempDataBak = JSON.parse(JSON.stringify(this.tempData))
        this.queryFullAll1(this.tempData)
      })
    },
    handleClick(tab, event) {
      console.log(tab, event)
      if (tab.name === 'first') {
        this.currentTab = '0'
      } else {
        this.currentTab = '1'
      }
    },
    handleClose() {
      this.$emit('update:dialogVisible', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.videoMonitoring {
  min-width: 1450px;
  width: 100%;
  height: 100%;
  position: relative;

  .el-transfer-panel .el-transfer-panel__footer {
    height: 85px;
  }

  .video-tab {
    width: 100%;
    height: 45px;

    .tab-item {
      height: 35px;
      float: left;
      font-size: 14px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: rgba(51, 51, 51, 1);
      text-align: center;
      line-height: 35px;
      cursor: pointer;

      &.item1 {
        width: 160px;
      }

      &.item2 {
        width: 160px;
      }

      &.activeClass {
        background: rgba(12, 153, 219, 1);
        border-radius: 4px;
        color: #fff;
      }
    }
  }

  .right-content {
    width: 100%;
    height: 550px;
    display: flex;
    justify-content: space-between;

    .right-content-list {
      width: 20%;
      height: 100%;

      .right-title {
        width: 400px;
        height: 50px;
        background: linear-gradient(0deg, rgba(12, 153, 219, 0.2) 0%, rgba(255, 255, 255, 0) 100%);
        margin: 0 auto;
        line-height: 35px;
        text-align: left;

        i {
          font-size: 48px;
          margin: 0 25px 0 20px;
          background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
          // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }

      .right-list {
        width: 400px;
        height: 380px;
        margin: 0 auto;
        overflow: auto;

        li {
          margin: 10px 0;

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
        width: 400px;
        height: 50px;
        background: rgba(255, 255, 255, 1);
        border: 2px solid rgba(12, 153, 219, 1);
        border-radius: 8px;
        text-align: center;
        line-height: 50px;
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
    height: 550px;
    display: flex;
    justify-content: space-between;

    .video-item {
      width: 33%;
      height: 100%;

      .select-video {
        margin: 0 0 10px 0;
      }

      .set-video {
        height: 500px;
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
          width: 312px;
          height: 85px;
          text-align: center;

          .iconfont {
            color: #a4a4a4;
            font-size: 20px;
          }

          .video-btn {
            width: 244px;
            height: 45px;
            border-radius: 8px;
            line-height: 45px;
            color: #ffffff;
            cursor: pointer;

            &.full-btn {
              width: auto;
            }

            .full-btn1 {
              color: #b9c4cf;
              text-align: left;
              font-size: 25px;
              margin-left: 25px;
              background: rgba(12, 153, 219, 1);
              overflow: hidden;
              width: 230px;
              text-align: center;
              white-space: nowrap;
              text-overflow: ellipsis;
            }
          }
        }
      }
    }

    .video-small-item {
      width: 33%;
      height: 100%;
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;

      .video-flex {
        width: 48%;
        height: 50%;

        .select-small-video {
          margin: 0 0 10px 0;
        }

        .set-video-small {
          height: 225px;
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
            width: 150px;
            height: 68px;
            text-align: center;

            .iconfont {
              color: #a4a4a4;
              font-size: 14px;
            }

            .video-btn {
              height: 80px;
              border-radius: 8px;
              line-height: 67px;
              color: #ffffff;
              cursor: pointer;

              .full-btn1 {
                color: #b9c4cf;
                text-align: left;
                font-size: 16px;
                margin-left: 5px;
                overflow: hidden;
                width: 140px;
                height: 45px;
                line-height: 45px;
                margin-top: 13px;
                white-space: nowrap;
                text-overflow: ellipsis;
                background: rgba(12, 153, 219, 1);
              }
            }
          }
        }
      }
    }
  }

  .btn {
    width: 100%;
    height: 100px;
    // background-color: #0ff;
    display: inline-flex;
    justify-content: center;
    border-top: 2px solid #d6d6d6;

    .item {
      width: 125px;
      height: 45px;
      margin-top: 40px;
      text-align: center;
    }

    .item_btn1 {
      font-size: 20px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: rgba(12, 153, 219, 1);
      line-height: 45px;
      cursor: pointer;
    }

    .item_btn2 {
      font-size: 20px;
      background: linear-gradient(176deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
      border-radius: 8px;
      line-height: 45px;
      color: #fff;
      cursor: pointer;
    }
  }

  .addDialog {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background: rgba(0, 0, 0, 0.5);
    overflow-y: auto;

    .add-dialog-content {
      width: 1230px;
      height: 766px;
      background: white;
      margin: 105px auto 0;
      position: relative;

      .add-dialog-title {
        width: 100%;
        height: 70px;
        border-bottom: 4px solid #d9e1e5;
        display: inline-flex;
        justify-content: center;
        align-items: center;

        .title-content {
          width: 135px;
          height: 100%;
          font-size: 24px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(51, 51, 51, 1);
          line-height: 60px;

          i:nth-of-type(1) {
            font-size: 48px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }

          i:nth-of-type(2) {
            font-size: 48px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, rgba(0, 150, 255, 1) 0%, rgba(0, 215, 254, 1) 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
        }

        .close-icon {
          position: absolute;
          right: 25px;
          top: 25px;
          width: 48px;
          height: 48px;
          border-radius: 2px;
          cursor: pointer;

          i {
            font-size: 26px;
            color: rgba(182, 182, 182, 1);
          }
        }
      }
    }

    .transfer-content {
      width: 100%;
      height: 620px;

      .leftForm {
        width: 80%;
        border: 2px solid #eff2f5;
        margin-left: 20px;
        margin-right: 20px;
        height: 627px;

        .topTitle {
          background: rgba(0, 147, 255, 0.1);
          border: 1px solid #0093ff;
          padding: 12px;
          font-size: 14px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #0093ff;
        }

        .mainContent {
          display: flex;
          flex-direction: column;
          overflow: scroll;
          height: 505px;

          .leftItem {
            padding-left: 10px;
            width: 100%;
            height: 30px;
            margin-top: 10px;
            line-height: 30px;
          }
        }
      }

      .centerBtn {
        margin-top: 290px;
        width: 20%;

        .chooseBtn {
          display: inline-block;
          width: 50px;
          height: 50px;
          background: #0093ff;
          border-radius: 4px;
          font-size: 20px;
          padding: 12px;
          cursor: pointer;
          color: #fff;
          margin-left: 10px;
        }
      }

      .el-transfer {
        font-size: 14px;
        margin-left: 109px;
      }

      .el-transfer-panel__item {
        height: 44px;
      }

      .el-transfer-panel__item .el-checkbox__input {
        top: 16px;
      }

      .el-transfer-panel {
        width: 380px;
        height: 903px;
        border: 1px solid #eff2f5;
        box-shadow: 0px 0px 6px #b7b9bb;
        max-height: 100%;
      }

      .el-transfer-panel .el-transfer-panel__header {
        height: 40px;
        line-height: 40px;
        background: rgba(0, 147, 255, 0.1);
        margin: 0;
        border: 1px solid #0093ff;
        padding-left: 15px;
        /* border-bottom: 1px solid #EBEEF5; */
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
      }

      .el-transfer__buttons {
        padding: 0 80px;
      }

      .el-transfer-panel__item.el-checkbox .el-checkbox__label {
        line-height: 80px;
      }

      .el-checkbox {
        margin-right: 0px;
        font-size: 16px;
      }

      .el-transfer-panel__filter .el-input__inner {
        height: 40px;
        border-radius: 0px;
        font-size: 16px;
      }

      .el-transfer-panel__list.is-filterable {
        height: 780px;
      }

      .el-transfer-panel__item.el-checkbox:hover {
        background: #e4f4ff;
      }

      .input-with-search {
        width: 795px;
        height: 85px;
        border: 1px solid #ccc;
        position: absolute;
        top: 330px;
        left: 175px;
        background: #f5f7fa;
        z-index: 33333;

        .el-input__inner {
          border: none;
        }

        .el-input-group__append {
          padding: 0 12px;
          font-size: 20px;
          border: none;
        }
      }

      .btn {
        position: absolute;
        bottom: 0px;
        width: 100%;
        height: 70px;
        // background-color: #0ff;
        display: inline-flex;
        justify-content: center;
        border-top: 2px solid #d6d6d6;

        .item {
          width: 135px;
          height: 47px;
          margin-top: 13px;
          text-align: center;
        }

        .item_btn1 {
          font-size: 24px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(12, 153, 219, 1);
          line-height: 44px;
          cursor: pointer;
        }

        .item_btn2 {
          background: linear-gradient(176deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
          border-radius: 8px;
          line-height: 42px;
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
        background: linear-gradient(176deg, rgba(0, 168, 255, 1) 0%, rgba(0, 98, 209, 1) 100%);
        border-radius: 8px;
        line-height: 80px;
        color: #fff;
        cursor: pointer;
      }
    }
  }
}
</style>
