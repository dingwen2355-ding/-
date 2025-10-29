<template>
  <el-dialog
    title="指挥组织体系编辑"
    :visible.sync="dialogVisible1"
    :modal="false"
    width="3912rem"
    height="3070rem"
    class="OrganizationalModule default-dialog-class large-screen"
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
    <div class="historyList" v-if="false">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-select
            v-model="currentEvent1"
            placeholder="事件类型选择"
            filterable
            clearable
            @change="unitNameChange(currentEvent1)"
            popper-class="large-screen"
          >
            <el-option
              v-for="item in unitOptionsList"
              :key="item.id"
              :label="item.typeName"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button class="chaxun" type="primary" @click="onSubmit">查询</el-button>
          <el-button class="chongzhi" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="organizational-module-content">
      <div class="content_item">
        <div class="item item1">
          <div class="command-set">
            <i class="iconfont iconzhgl_title"></i>
            <span>指挥长设置</span>
          </div>
          <div class="list-content">
            <div v-for="(item, index) in leaderArr" :key="item.id">
              <ul v-for="(user, uIndex) in item.userList" :key="uIndex">
                <li class="num">{{ index + uIndex + 1 }}</li>
                <li>{{ item.name }}</li>
                <li style="width: 533rem">
                  <el-select
                    v-model="user.name"
                    placeholder="请选择"
                    filterable
                    :filter-method="filterMethod"
                    @change="changeLeader(user)"
                    popper-class="large-screen"
                  >
                    <el-option :key="''" :label="'无'" :value="''"></el-option>
                    <el-option
                      v-for="(leader, lIndex) in allLeaderList20"
                      :key="lIndex"
                      :label="leader.name"
                      :value="leader.id"
                    ></el-option>
                  </el-select>
                </li>
                <!-- <li>{{ user.phone }}</li> -->
              </ul>
            </div>
          </div>
          <div class="add-btn" @click="openQueryUserDialog()"><i class="el-icon-s-tools"></i> 设置</div>
        </div>
        <div class="item item2">
          <div class="command-set" v-if="!precinctId">
            <i class="iconfont iconzhgl_title"></i>
            <span>事件相关区属单位（{{ cityUnitArr.length }}个）</span>
          </div>
          <div class="command-set" v-else>
            <i class="iconfont iconzhgl_title"></i>
            <span>事件相关区属单位（{{ cityUnitArr.length }}个）</span>
          </div>
          <div class="list-content">
            <draggable class="wrapper" v-model="cityUnitArr">
              <transition-group>
                <ul v-for="(item, index) in cityUnitArr" :key="'city' + index">
                  <li class="num">{{ index + 1 }}</li>
                  <li style="width: 300rem" :title="item.name">{{ item.name }}</li>
                  <li style="width: 300rem">
                    <el-select v-model="item.dutyLevel" placeholder="请选择" popper-class="large-screen">
                      <el-option :key="'0'" :label="'主责'" :value="'0'"></el-option>
                      <el-option :key="'1'" :label="'协同'" :value="'1'"></el-option>
                    </el-select>
                  </li>
                  <li style="width: 300rem">
                    <el-select v-model="item.user.id" placeholder="请选择" filterable popper-class="large-screen">
                      <el-option :key="''" :label="'无'" :value="''"></el-option>
                      <el-option
                        v-for="(leader, lIndex) in item.memberList"
                        :key="lIndex"
                        :label="leader.leader"
                        :value="leader.personId"
                      ></el-option>
                    </el-select>
                  </li>
                  <li class="delete-btn">
                    <i class="el-icon-delete-solid" @click="deleteItem(cityUnitArr, item)"></i>
                  </li>
                </ul>
              </transition-group>
            </draggable>
          </div>
          <div class="add-btn" @click="showAddDialog('cityUnit')"><i class="el-icon-circle-plus"></i> 新增</div>
        </div>
        <div class="item item3">
          <div class="command-set">
            <i class="iconfont iconzhgl_title"></i>
            <span>事件相关属地单位（{{ colonyUnitArr.length }}个）</span>
          </div>
          <div class="list-content">
            <ul v-for="(item, index) in colonyUnitArr" :key="index">
              <li class="num">{{ index + 1 }}</li>
              <li :title="item.name">{{ item.name }}</li>
              <li>
                <el-select v-model="item.user.id" placeholder="请选择" filterable popper-class="large-screen">
                  <el-option :key="''" :label="'无'" :value="''"></el-option>
                  <el-option
                    v-for="(leader, lIndex) in item.memberList"
                    :key="lIndex"
                    :label="leader.leader"
                    :value="leader.personId"
                  ></el-option>
                </el-select>
              </li>
              <li class="delete-btn">
                <i class="el-icon-delete-solid" @click="deleteItem(colonyUnitArr, item)"></i>
              </li>
            </ul>
          </div>
          <div class="add-btn" @click="showAddDialog('colonyUnit')"><i class="el-icon-circle-plus"></i> 新增</div>
        </div>
      </div>
      <div class="btn">
        <el-button type="primary" @click="submitUpdate" style="margin-right: 200rem">提交</el-button>
        <el-button type="primary" @click="handleClose">取消</el-button>
      </div>
    </div>
    <el-dialog
      :title="dialogType === 'cityUnit' ? '事件相关区属单位设置' : '事件相关属地单位设置'"
      :visible.sync="dialogVisible"
      :modal="false"
      width="3912rem"
      height="3070rem"
      class="addDialog default-dialog-class large-screen"
      :append-to-body="true"
      :before-close="closeAddDialog"
    >
      <div class="dd-title-back">
        <div class="dd-title-back-s"></div>
      </div>
      <div class="dd-back-area top-right-area"></div>
      <div class="dd-back-angle top-left-angle"></div>
      <div class="dd-back-angle top-right-angle"></div>
      <div class="dd-back-angle bottom-left-angle"></div>
      <div class="dd-back-angle bottom-right-angle"></div>
      <!-- <div class="addDialog" v-if="dialogVisible"> -->
      <!-- <div class="add-dialog-content">
        <div class="add-dialog-title">
          <div class="title-content">
            <span v-if="dialogType === 'cityUnit'">事件相关区属单位设置</span>
      <span v-else>事件相关属地单位设置</span>-->

      <!-- <i class="iconfont iconzhgl_title"></i>
            <span>新增</span>
      <i class="iconfont iconzhgl_title"></i>-->

      <!-- </div>
          <div class="close-icon" @click="closeAddDialog">
            <i class="el-icon-close"></i>
          </div>
      </div>-->

      <!-- <div class="add-dialog-content"> -->
      <div class="transfer-content">
        <el-transfer
          v-model="transferValue"
          :data="transferArr"
          :titles="transferTitles"
          :props="{ key: 'id', label: 'name' }"
          :filterable="true"
          v-show="dialogType === 'enterprise'"
        ></el-transfer>
        <div class="QueryUserDialog-body" v-show="dialogType === 'cityUnit'">
          <div class="QueryUserDialog-item" style="width: 42%">
            <div class="QueryUserDialog-item-bg"></div>
            <div class="item-content">
              <div class="item-content-title">{{ transferTitles[0] }}</div>
              <div class="item-search-warp">
                <el-input placeholder="请输入单位名称" prefix-icon="el-icon-search" v-model="searchUnitText"></el-input>
              </div>
              <div class="item-content-list">
                <el-tree
                  :expand-on-click-node="false"
                  check-strictly="true"
                  show-checkbox
                  :data="transferArr"
                  node-key="id"
                  @check="handleCheckChange"
                  :filter-node-method="filterNode"
                  ref="tree"
                >
                  <div class="list-item" style="padding-left: 0" slot-scope="{ data }">
                    <span class="name" :title="data.name">{{ data.name }}</span>
                  </div>
                </el-tree>
              </div>
            </div>
          </div>
          <div class="QueryUserDialog-item" style="width: 14%">
            <div class="item-btn-warp">
              <div class="i-btn" @click="addUsers()">
                <i class="el-icon-caret-right"></i>
              </div>
              <div class="i-btn" @click="removeUsers()">
                <i class="el-icon-caret-left"></i>
              </div>
            </div>
          </div>
          <div class="QueryUserDialog-item" style="width: 36%">
            <div class="QueryUserDialog-item-bg"></div>
            <div class="item-content">
              <div class="item-content-title">{{ transferTitles[1] }}</div>
              <div class="item-content-list">
                <div class="list-item" v-for="(item, i4) in eventSelectList" :key="i4" @click="selectUserList(item)">
                  <el-checkbox v-model="item.checked"></el-checkbox>
                  <span :title="item.name" class="name" style="max-width: 75%">{{ item.name }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="colony-warp" v-show="dialogType === 'colonyUnit'">
          <div class="vx-content">
            <div class="vx-content-item">
              <div class="vx-title">
                <i class="iconfont iconzhgl_title"></i>
                <span>无锡市</span>
              </div>
              <div class="vx-list">
                <ul
                  class="vx-list-ul"
                  v-for="(item, index) in allColonyUnitList"
                  :key="index"
                  :class="{ active: item.selected }"
                >
                  <li @click="selectAreaInfo(item)">
                    <i class="iconfont iconzhihui_person"></i>
                    {{ item.name }}
                  </li>
                </ul>
              </div>
            </div>
            <div class="vx-content-item">
              <div class="vx-title">
                <i class="iconfont iconzhgl_title"></i>
                <span>镇/街道</span>
              </div>
              <div class="search-warp">
                <el-input
                  placeholder="请输入镇/街道名称"
                  prefix-icon="el-icon-search"
                  @keyup.enter.native="searchFunction()"
                  v-model="searchInput"
                ></el-input>
              </div>
              <div class="vx-list" v-if="searchInput">
                <ul
                  class="vx-list-ul"
                  v-for="(item, index) in colonyUnitSearchList"
                  :key="index"
                  :class="{ active: item.selected }"
                >
                  <li @click="selectAreatopId(item)">
                    <i class="iconfont iconzhihui_person"></i>
                    {{ item.name }}
                  </li>
                </ul>
              </div>
              <div class="vx-list" v-else>
                <ul
                  class="vx-list-ul"
                  v-for="(item, index) in colonyUnitList"
                  :key="index"
                  :class="{ active: item.selected }"
                >
                  <li @click="selectAreatopId(item)">
                    <i class="iconfont iconzhihui_person"></i>
                    {{ item.name }}
                  </li>
                </ul>
              </div>
            </div>
            <div class="vx-content-item vx-content-margin">
              <div class="vx-title">
                <i class="iconfont iconzhgl_title"></i>
                <span>已选中单位</span>
              </div>
              <div class="vx-list">
                <ul class="vx-list-ul" v-for="(item, index) in selectUnitList" :key="index">
                  <li>
                    <i class="iconfont iconzhihui_person"></i>
                    {{ item.name }}
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="btn">
          <!-- <div class="item item_btn1" @click="closeAddDialog">取消</div>
          <div class="item item_btn2" @click="submitDialog">提交</div>-->
          <el-button type="text" @click="closeAddDialog" style="margin-right: 200rem">取消</el-button>
          <el-button type="primary" @click="submitDialog">提交</el-button>
        </div>
      </div>
      <!-- </div> -->
      <!-- </div> -->
      <!-- </div> -->
    </el-dialog>
    <QueryUserDialog :eventInfoList="eventInfoList" />
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
import QueryUserDialog from './QueryUserDialog.vue'
import { mapGetters } from 'vuex'
export default {
  name: 'OrganizationalModule',
  props: ['dialogVisibleFlag', 'currentEvent', 'unitOptions', 'eventType'],
  components: { QueryUserDialog },
  data() {
    return {
      allLeaderList: [], // 所有人员列表
      allLeaderList20: [], // 所有人员列表 - 前20
      allCityUnitList: [], // 所有市属单位
      allColonyUnitList: [], // 所有属地单位 - level=1
      colonyUnitList: [], // 所有属地单位 - level=2 topId=areaCode
      colonyUnitSearchList: [], // 所有属地单位 - level=2 topId=areaCode
      selectUnitList: [], // 选中的属地单位
      allEnterpriseList: [], // 所有保供企业
      currentEvent1: '',
      eventTypeList: '',
      leaderArr: [
        {
          id: 999998,
          name: '区指挥长',
          userList: [
            {
              id: '',
              name: '',
              phone: ''
            }
          ]
        },
        {
          id: 999997,
          name: '市指挥长',
          userList: [
            {
              id: '',
              name: '',
              phone: ''
            }
          ]
        }
      ], // 指挥长设置
      cityUnitArr: [], // 事件相关区属单位设置
      colonyUnitArr: [], // 事件相关属地单位设置
      enterpriseArr: [], // 保供企业设置
      transferArr: [], //
      transferValue: [],
      transferTitles: [],
      dialogVisible: false,
      dialogVisible1: false,
      showQueryVisible: false,
      searchUnitText: '',
      dialogType: 'cityUnit',
      searchInput: '',
      // eventType: sysConfig.defaultPrecinct.eventType,
      precinctId: this.$store.getters.precinctId,
      leaderIds_999998: '',
      leaderIds_999997: '',
      eventBelongList: [],
      eventSelectList: [],
      showQueryList: null,
      eventInfoList: ''
    }
  },
  watch: {
    unitOptions(v) {
      this.unitOptionsList = v
    },
    dialogVisibleFlag(v) {
      if (v) {
        this.dialogVisible1 = true
        this.$nextTick(() => {
          this.getAllLeaderList()
          this.getAllCityUnitList()
          this.getAllColonyUnitList()
          this.getAllEnterpriseList()
          this.queryEventTypeList()
        })
        this.currentEvent1 = this.currentEvent
        this.eventInfoList = this.eventType
        if (this.eventPlanId) {
          this.getArrData3()
        }
      }
    },
    '$store.getters.precinctId': {
      handler(v) {
        if (!v) return
        this.$nextTick(() => {
          this.getArrData3()
        })
      },
      immediate: true
    },
    // searchUnitText: {
    //   handler(v) {
    //     this.$refs.tree.filter(v)
    //   },
    //   immediate: true
    // },
    eventType: {
      handler(v) {
        if (!v) return
        this.$nextTick(() => {
          this.getArrData3(v)
        })
      }
    },
    searchInput: {
      handler() {
        this.colonyUnitSearchList = []
      },
      immediate: true
    }
  },
  computed: {
    ...mapGetters(['eventPlanId'])
  },
  mounted() {
    this.$root.Bus.$on('showEventType', (data) => {
      this.getArrData3(data)
    })
    if (this.eventPlanId) {
      this.getArrData3(this.eventType)
    }
    this.$root.Bus.$on('updateLeaderAfter', () => {
      if (this.eventPlanId) {
        this.getArrData3(this.eventType)
      }
    })
  },
  methods: {
    queryEventTypeList() {
      let url = '/apis/manage/queryEventTypeList'
      Axios.get(url).then((r) => {
        this.unitOptionsList = r.data.data
      })
    },
    unitNameChange(e) {
      this.unitOptionsList.forEach((item) => {
        if (e === item.typeName) {
          this.eventTypeList = item.id
          this.eventInfoList = item.id
          this.getArrData3(this.eventTypeList)
        }
      })
    },
    handleClose() {
      this.dialogVisible1 = false
      this.$emit('update:dialogVisibleFlag', false)
    },
    handleCheckChange(data, checked) {
      this.eventBelongList = []
      this.eventBelongList = checked.checkedNodes
      console.log(data, checked)
    },
    selectUserList(type) {
      if (type.checked === true) {
        type.checked = false
      } else {
        type.checked = true
      }
      // type.checked = !type.checked
    },
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    removeUsers() {
      let delArr = this.eventSelectList.map((item) => {
        if (item.checked) return item
        else return null
      })
      delArr.forEach((item) => {
        if (item) this.eventSelectList.splice(this.eventSelectList.indexOf(item), 1)
      })
      console.log(this.eventSelectList)
      this.$refs.tree.setCheckedKeys([])
    },
    addUsers() {
      if (this.eventBelongList.length > 0) {
        for (let i = 0; i < this.eventBelongList.length; i++) {
          let member = {
            name: this.eventBelongList[i].name,
            id: this.eventBelongList[i].id,
            superId: this.eventBelongList[i].superId,
            superName: this.eventBelongList[i].superName,
            type: this.eventBelongList[i].type,
            checked: false
          }
          this.eventBelongList[i].checked = false
          this.eventSelectList.push(member)
        }
        this.eventSelectList = this.unique1(this.eventSelectList)
        this.$refs.tree.setCheckedKeys([])
      }
    },
    unique1(arr) {
      for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
          if (arr[i].id === arr[j].id) {
            arr.splice(j, 1)
            j--
          }
        }
      }
      return arr
    },
    filterMethod(query = '') {
      let arr = this.allLeaderList.filter((item) => {
        return item.name.includes(query)
      })
      if (arr.length > 20) {
        this.allLeaderList20 = arr.slice(0, 20)
      } else {
        this.allLeaderList20 = arr
      }
    },
    searchFunction: function () {
      if (!this.searchInput) return
      let searchResult = []
      for (let i = 0; i < this.colonyUnitList.length; i++) {
        let colonyUnit = this.colonyUnitList[i]
        if (colonyUnit.name.indexOf(this.searchInput) !== -1) {
          searchResult.push(colonyUnit)
        }
      }
      this.colonyUnitSearchList = searchResult
    },
    getAllLeaderList() {
      let _this = this
      let url = '/apis/communication/baseInfo?type=1,3&unitId=4236'
      Axios.get(url)
        .then((res) => {
          let data = res.data.data.list
          // console.log(res)
          _this.allLeaderList = data || []
          /* if (_this.allLeaderList.length > 20) {
            _this.allLeaderList20 = _this.allLeaderList.slice(0, 20)
          } else {
            _this.allLeaderList20 = _this.allLeaderList
          } */
          _this.allLeaderList20 = _this.allLeaderList
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getAllCityUnitList() {
      let url = '/apis/communication/unitInfoTree?type=1,2,3'
      Axios.get(url)
        .then((res) => {
          let data = res.data.data
          this.allCityUnitList = data || []
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getAllColonyUnitList() {
      let url = '/apis/communication/areaInfo?level=110,120'
      Axios.get(url)
        .then((res) => {
          let data = res.data.data || []
          for (let i = 0; i < data.length; i++) {
            data[i].selected = false
            data[i].user = { id: '' }
            data[i].memberList = []
            this.getUnitMemberList(data[i])
          }
          this.allColonyUnitList = data
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getAllEnterpriseList() {
      let url = '/apis/communication/unitInfo?type=2'
      Axios.get(url)
        .then((res) => {
          let data = res.data.data
          this.allEnterpriseList = data || []
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getArrData3(eventType) {
      // if (this.precinctId) {
      this.leaderArr = [
        {
          id: 999998,
          name: '区指挥长',
          userList: [
            {
              id: '',
              name: '',
              phone: ''
            }
          ]
        }
      ]
      // } else {
      //   this.leaderArr = [
      //     {
      //       id: 999997,
      //       name: '市指挥长',
      //       userList: [
      //         {
      //           id: '',
      //           name: '',
      //           phone: ''
      //         }
      //       ]
      //     }
      //   ]
      // }
      let url =
        '/apis/Command/communicationOrderByDepartmentByPrecinctId?linkType=2&planId=' +
        this.eventPlanId +
        '&isMax=isMax'
      this.getArrData1(url)
    },
    // getArrData (eventType) {
    //   if (!eventType) return
    //   let url = '/apis/Command/communicationOrderByDepartmentByEventType?eventType=' + eventType + '&isMax=isMax'
    //   this.getArrData1(url)
    // },
    getArrData1(url) {
      Axios.get(url)
        .then((res) => {
          let data = res.data.data
          this.cityUnitArr = []
          this.colonyUnitArr = []
          this.enterpriseArr = []
          for (let key in data) {
            // eslint-disable-next-line eqeqeq
            if (key == 1) {
              let commanderArr = data[key]
              commanderArr.forEach((commanderObj) => {
                for (let k in commanderObj) {
                  let commanderList = commanderObj[k].data
                  commanderList.forEach((item) => {
                    item.id = item.personId
                    item.nid = k
                    item.isHove = false
                    item.unitName = commanderObj[k].name
                    item.name = item.leader
                  })
                  for (let i = 0; i < this.leaderArr.length; i++) {
                    let leader = this.leaderArr[i]
                    // eslint-disable-next-line eqeqeq
                    if (k == leader.id) {
                      leader.userList = commanderList
                      break
                    }
                  }
                }
              })
              // eslint-disable-next-line eqeqeq
            } else if (key == 2) {
              let cityUnitList = []
              let cityUnitArr = data[key]
              cityUnitArr.forEach((cityUnitObj) => {
                for (let k in cityUnitObj) {
                  let cityUnit = {}
                  cityUnit.id = k
                  cityUnit.name = cityUnitObj[k].name
                  cityUnit.memberList = cityUnitObj[k].data || []
                  cityUnit.user = { id: '' }
                  cityUnit.dutyLevel = cityUnitObj[k].dutyLevel
                  if (cityUnit.memberList.length) {
                    cityUnit.user.id = cityUnit.memberList[0].personId
                  }
                  cityUnitList.push(cityUnit)
                }
              })
              this.cityUnitArr = cityUnitList
              // eslint-disable-next-line eqeqeq
            } else if (key == 3) {
              let colonyUnitList = []
              let colonyUnitArr = data[key]
              colonyUnitArr.forEach((colonyUnitObj) => {
                for (let k in colonyUnitObj) {
                  let colonyUnit = {}
                  colonyUnit.areaCode = k
                  colonyUnit.name = colonyUnitObj[k].name
                  colonyUnit.user = { id: '' }
                  colonyUnit.memberList = colonyUnitObj[k].data || []
                  if (colonyUnit.memberList.length) {
                    colonyUnit.user.id = colonyUnit.memberList[0].personId
                  }
                  colonyUnitList.push(colonyUnit)
                }
              })

              this.colonyUnitArr = colonyUnitList
              // eslint-disable-next-line eqeqeq
            } else if (key == 4) {
              let enterpriseList = []
              let enterpriseArr = data[key]
              enterpriseArr.forEach((enterpriseObj) => {
                for (let k in enterpriseObj) {
                  let enterprise = {}
                  enterprise.id = k
                  enterprise.name = enterpriseObj[k].name
                  enterprise.user = { id: '' }
                  enterprise.memberList = enterpriseObj[k].data || []
                  if (enterprise.memberList.length) {
                    enterprise.user.id = enterprise.memberList[0].personId
                  }
                  enterpriseList.push(enterprise)
                }
              })
              this.enterpriseArr = enterpriseList
            }
          }
        })
        .catch((err) => {
          console.error(err)
        })
    },
    changeLeader(user) {
      if (!user.name) {
        user.phone = ' '
        return
      }
      for (let i = 0; i < this.allLeaderList.length; i++) {
        let leader = this.allLeaderList[i]
        // eslint-disable-next-line eqeqeq
        if (user.name == leader.id) {
          user.id = leader.id
          user.phone = leader.phone
          break
        }
      }
    },
    deleteItem(array, item) {
      array.splice(array.indexOf(item), 1)
    },
    getTransferValue(arr) {
      this.transferValue = []
      arr.forEach((item) => {
        let id = item.id
        this.transferValue.push(id)
        this.eventSelectList.push(item)
      })
    },
    showAddDialog(dialogType) {
      this.dialogVisible = true
      this.dialogType = dialogType
      if (dialogType === 'cityUnit') {
        // 区属单位
        this.transferArr = this.allCityUnitList
        this.eventSelectList = []
        this.allCityUnitList.forEach((item) => {
          item.dutyLevel = '1'
          for (let i = 0; i < this.cityUnitArr.length; i++) {
            let cityUnit = this.cityUnitArr[i]
            // eslint-disable-next-line eqeqeq
            if (item.id == cityUnit.id) {
              item.dutyLevel = cityUnit.dutyLevel
              break
            }
          }
        })
        if (this.precinctId) {
          this.transferTitles = ['事件相关区属单位列表', '已选事件相关区属单位']
        } else {
          this.transferTitles = ['事件相关区属单位列表', '已选事件相关区属单位']
        }
        this.getTransferValue(this.cityUnitArr)
      } else if (dialogType === 'colonyUnit') {
        // 属地单位
        this.selectUnitList = JSON.parse(JSON.stringify(this.colonyUnitArr))
        for (let i = 0; i < this.allColonyUnitList.length; i++) {
          let item = this.allColonyUnitList[i]
          if (this.isSelected(item)) {
            item.selected = true
            this.selectAreaInfo(item)
            break
          }
        }
      } else if (dialogType === 'enterprise') {
        // 保供企业
        this.transferArr = this.allEnterpriseList
        this.transferTitles = ['保供企业列表', '已选保供企业']
        this.getTransferValue(this.enterpriseArr)
      }
    },
    closeAddDialog() {
      this.dialogVisible = false
    },

    openQueryUserDialog: function () {
      this.$root.Bus.$emit('openQueryUserDialog', this.leaderArr)
    },

    getUnitArr(array) {
      let unitArr = []
      console.log(array)
      for (let i = 0; i < array.length; i++) {
        if (this.transferValue.indexOf(array[i].id) !== -1) {
          let item = {
            user: { id: '' },
            dutyLevel: array[i].dutyLevel || '0',
            id: array[i].id,
            name: array[i].name,
            memberList: []
          }
          item.user = { id: '' }
          this.getUnitMemberList(item)
          unitArr.push(item)
        } else {
          let item = {
            user: { id: '' },
            dutyLevel: '1',
            id: array[i].id,
            name: array[i].name,
            memberList: []
          }
          item.user = { id: '' }
          this.getUnitMemberList(item)
          unitArr.push(item)
        }
      }
      return unitArr
    },

    getUnitMemberList(unit) {
      if (unit.memberList.length) return
      let unitId = unit.id || unit.areaCode
      let url = '/apis/Command/unitMember?unitId=' + unitId
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        data.forEach((item) => {
          item.personId = item.id
          item.leader = item.name
        })
        unit.memberList = data
      })
    },

    isSelected(item) {
      let isSelected = false
      for (let i = 0; i < this.colonyUnitArr.length; i++) {
        let colonyUnit = this.colonyUnitArr[i]
        // eslint-disable-next-line eqeqeq
        if (item.areaCode == colonyUnit.areaCode) {
          isSelected = true
          break
        }
      }
      return isSelected
    },
    selectAreaInfo(item) {
      if (item.data) {
        // 阻止多次请求 - 请求一次保存下来
        this.colonyUnitList = item.data
      } else {
        let url = '/apis/communication/areaInfo?level=2&topId=' + item.areaCode
        Axios.get(url)
          .then((res) => {
            let data = res.data.data || []
            for (let i = 0; i < data.length; i++) {
              data[i].selected = false
              if (this.isSelected(data[i])) {
                data[i].selected = true
              } else {
                data[i].selected = false
              }
              data[i].user = { id: '' }
              data[i].memberList = []
              this.getUnitMemberList(data[i])
            }
            item.data = data
            this.colonyUnitList = item.data
          })
          .catch((err) => {
            console.error(err)
          })
      }

      if (!this.isSelected(item)) {
        this.selectUnitList = [item]
        for (let i = 0; i < this.allColonyUnitList.length; i++) {
          let uItem = this.allColonyUnitList[i]
          uItem.selected = false
        }
        item.selected = true
      }
    },
    selectAreatopId(item) {
      // if (this.selectUnitList.length > 1) {
      //   this.selectUnitList.splice(1, 1, item)
      // } else {
      //   this.selectUnitList.push(item)
      // }
      this.selectUnitList.push(item)
      this.selectUnitList = this.unique(this.selectUnitList)
      if (!this.isSelected(item)) {
        for (let i = 0; i < this.colonyUnitList.length; i++) {
          let uItem = this.colonyUnitList[i]
          uItem.selected = false
        }
        item.selected = true
      }
    },

    unique(arr) {
      for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
          if (arr[i].areaCode === arr[j].areaCode) {
            arr.splice(j, 1)
            j--
          }
        }
      }
      return arr
    },

    submitDialog() {
      if (this.dialogType === 'cityUnit') {
        this.cityUnitArr = this.getUnitArr(this.eventSelectList)
      } else if (this.dialogType === 'enterprise') {
        this.enterpriseArr = this.getUnitArr(this.allEnterpriseList)
      } else if (this.dialogType === 'colonyUnit') {
        this.colonyUnitArr = JSON.parse(JSON.stringify(this.selectUnitList))
      }
      this.closeAddDialog()
    },
    // 确定提交
    submitUpdate() {
      // if (this.$store.getters.precinctId) {
      // eslint-disable-next-line camelcase
      this.leaderIds_999998 = this.leaderArr[0].userList.map((item) => {
        return item.id
      })
      // } else {
      //   // eslint-disable-next-line camelcase
      //   this.leaderIds_999997 = this.leaderArr[0].userList.map((item) => {
      //     return item.id
      //   })
      // }
      let cityUnitIdArr = this.cityUnitArr.map((item) => {
        return {
          unitId: item.id,
          leader: item.user.id,
          dutyLevel: item.dutyLevel
        }
      })
      let colonyUnitIdArr = this.colonyUnitArr.map((item) => {
        return {
          unitId: item.areaCode,
          leader: item.user.id
        }
      })
      let enterpriseIdArr = this.enterpriseArr.map((item) => {
        return {
          unitId: item.id,
          leader: item.user.id
        }
      })
      // if (!this.eventType && !this.eventTypeList) return
      let url = '/apis/Command/updateEventCommunicationByPrecinctId'
      let params = {
        precinctId: this.$store.getters.precinctId,
        planId: this.eventPlanId,
        linkType: 2,
        data: {
          1: {},
          2: cityUnitIdArr,
          3: colonyUnitIdArr,
          4: enterpriseIdArr
        }
      }
      // eslint-disable-next-line camelcase
      // if (!this.$store.getters.precinctId) {
      //   params.data['1'] = {
      //     999997: this.leaderIds_999997.join(',')
      //   }
      // } else {
      params.data['1'] = {
        999998: this.leaderIds_999998.join(',')
      }
      // }
      this.showSubmit(url, params)
    },
    showSubmit(url, params) {
      Axios.post(url, params)
        .then((res) => {
          if (res.data.code === 200) {
            this.$message({
              message: '提交成功！',
              type: 'success'
            })
            this.handleClose()
            this.$root.Bus.$emit('refreshIframe')
          }
        })
        .catch((err) => {
          console.error(err)
        })
    },
    // 取消提交
    cancelSubmit() {
      this.getArrData3()
    }
  },
  destroyed() {
    this.$root.Bus.$off('updateLeaderAfter')
  }
}
</script>

<style lang="scss" >
.OrganizationalModule {
  .large-screen .el-input .el-input__inner {
    min-width: 300rem;
  }
  .el-dialog {
    width: 4300rem !important;
    height: 3070rem !important;
  }
  z-index: 3;
  .el-dialog__header {
    background-color: transparent;
  }
  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 2930rem !important;
    overflow: hidden;
  }
  .organizational-module-content {
    width: 100%;
    height: 100%;
    .content_item {
      width: 100%;
      // height: 850rem;
      display: inline-flex;
      justify-content: space-around;
      align-items: center;
      margin-top: 20rem;
      .item {
        width: 30%;
        // height: 750rem;
        // padding-top: 118rem;
        position: relative;
        .list-content {
          height: 2046rem;
          width: 1120rem;
          overflow: auto;
          ul {
            // width: 1039rem;
            margin: 164rem auto;
            height: 180rem;
            line-height: 180rem;
            position: relative;
            &:hover .delete-btn {
              display: block;
            }
            .num {
              color: #0c99db;
              width: 65rem;
              font-size: 60rem;
            }
            // &:hover {
            //   background: rgba(239, 239, 239, 1);
            // }
            li {
              height: 180rem;
              line-height: 180rem;
              float: left;
              font-size: 60rem;
              margin: 10rem 10rem;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              &.delete-btn {
                position: absolute;
                right: 30rem;
                color: #0c99db;
                cursor: pointer;
                display: none;
              }
            }
          }
        }
        .command-set {
          top: 0;
          left: 0;
          right: 0;
          position: absolute;
          margin: 0 auto;
          // width: 880rem;
          height: 164rem;
          line-height: 164rem;
          font-size: 80rem;
          font-family: Microsoft YaHei;
          font-weight: normal;
          color: #ffffff;
          background: url('../../../../assets/SmallTitle/一级标题1.png') no-repeat;
          background-size: 100% 100%;
          // background-position: center center;
          padding-left: 20rem;
          span {
            margin-left: 141rem;
          }
        }
        .add-btn {
          width: 1040rem;
          height: 111rem;
          position: relative;
          // border: 2rem solid rgb(12, 153, 219);
          text-align: center;
          line-height: 111rem;
          font-size: 49rem;
          color: #ffffff;
          // background: #3f8eb8;
          // border: 2rem solid #92d9ff;
          cursor: pointer;
          // box-shadow: inset 0 0 20rem 10rem #5b8dbb;
          background: linear-gradient(-46deg, transparent 30rem, #3f8eb8 0) right,
            linear-gradient(135deg, transparent 30rem, #3f8eb8 0) left;
          background-size: 50% 100%;
          background-repeat: no-repeat;
          i {
            color: #ffffff;
          }
        }
      }
    }
    .btn {
      margin-top: 166rem;
      width: 100%;
      height: 220rem;
      // background-color: #0ff;
      display: inline-flex;
      justify-content: center;
      // border-top: 2rem solid #d6d6d6;

      .item {
        width: 100rem;
        height: 40rem;
        margin-top: 40rem;
        text-align: center;
      }
      .item_btn1 {
        font-size: 14rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #9ab1cb;
        background: #082239;
        line-height: 40rem;
        border-radius: 2rem;
        border: 1rem solid #0d365f;
        cursor: pointer;
      }
      .item_btn2 {
        background: #0d365f;
        line-height: 40rem;
        color: #fff;
        border: 1rem solid #02a7fc;
        border-radius: 2rem;
        cursor: pointer;
      }
    }
  }

  .item .el-input .el-input__inner {
    width: 300rem !important;
    color: #ffffff;
    border: none;
    padding: 0 30rem !important;
  }
}
.addDialog {
  width: 100%;
  height: 100%;
  .el-dialog {
    width: 3912rem !important;
    height: 3070rem !important;
  }
  .el-dialog__body {
    padding: 0 20rem 30rem;
    height: 2210rem !important;
    overflow: hidden;
  }
  position: absolute;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.5);
  .add-dialog-content {
    width: 1500rem;
    // height: 870rem;
    background: #05192d;
    // border-radius: 10rem;
    // margin: 238rem auto 0;
    position: relative;
    .add-dialog-title {
      width: 100%;
      height: 80rem;
      .title-content {
        width: 320rem;
        height: 100%;
        font-size: 18rem;
        font-family: Microsoft YaHei;
        background: url('../../../../assets/global/组1.png') no-repeat;
        background-size: contain;
        font-weight: 400;
        // color: rgba(51, 51, 51, 1);
        line-height: 53rem;
        padding-left: 55rem;
        i:nth-of-type(1) {
          font-size: 14rem;
          margin: 0 25rem 0 20rem;
          background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
          // box-shadow: 0rem 4rem 4rem 0rem rgba(0, 0, 0, 0.2);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
        i:nth-of-type(2) {
          font-size: 14rem;
          margin: 0 25rem 0 20rem;
          background: linear-gradient(-90deg, rgba(0, 150, 255, 1) 0%, rgba(0, 215, 254, 1) 100%);
          // box-shadow: 0rem 4rem 4rem 0rem rgba(0, 0, 0, 0.2);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }
      .close-icon {
        position: absolute;
        right: 20rem;
        top: 30rem;
        width: 48rem;
        height: 48rem;
        border-radius: 2rem;
        i {
          font-size: 24rem;
          color: rgba(182, 182, 182, 1);
        }
      }
    }
  }
  .transfer-content {
    width: 100%;
    // height: 730rem;
    display: inline-flex;
    justify-content: center;
    .colony-warp {
      width: 100%;
      height: 2210rem;
      .vx-content {
        // width: 100%;
        // height: 100%;
        .vx-content-item {
          height: 2210rem;
          width: 1120rem;
          float: left;
          // margin: 0 60rem;
          &.vx-content-margin {
            margin-left: 300rem;
          }
          .vx-title {
            width: 1040rem;
            height: 164rem;
            margin: 0 auto;
            color: #ffffff;
            background: url('../../../../assets/SmallTitle/一级标题1.png') no-repeat;
            background-size: 100% 100%;
            // background-position: center center;
            line-height: 164rem;
            padding-left: 120rem;
            text-align: left;
            font-size: 60rem;
            color: #ffffff;
          }
          .vx-input {
            margin: 32rem 0 23rem 0;
            width: 500rem;
            height: 80rem;
            input {
              border: none;
            }
          }
          .vx-list {
            width: 1038rem;
            height: 80%;
            margin: 0 auto;
            overflow-y: auto;
            overflow-x: hidden;
            &::-webkit-scrollbar {
              width: 0 !important;
            }
            .vx-list-ul {
              width: 100%;
              height: 180rem;
              line-height: 180rem;
              margin: 20rem 0;
              display: block;
              font-size: 60rem;
              &:hover {
                background: #092a4b;
                border: 1rem solid #02a7fc;
              }
              &.active {
                width: 100%;
                height: 180rem;
                background: #092a4b;
                border: 1rem solid #02a7fc;
                background: #092a4b;
                border: 1rem solid #02a7fc;
              }
              li {
                width: 100%;
                height: 180rem;
                font-size: 60rem;
                line-height: 180rem;
                margin-left: 20rem;
                // background: rgba(12, 153, 219, 0.1);
                overflow: hidden;
                cursor: pointer;
                text-overflow: ellipsis;
                white-space: nowrap;
                color: #b7e6ff;
              }
            }
          }

          .search-warp {
            margin-top: 20rem;
            margin-left: 30rem;
            width: 100%;
            .el-input__inner {
              padding-left: 160rem;
              width: 1047rem;
            }
            .el-input__icon {
              line-height: 112rem;
              font-size: 60rem;
            }
          }
        }
      }
    }

    .btn {
      position: absolute;
      bottom: 290rem;
      width: 100%;
      height: 80rem;
      // background-color: #0ff;
      display: inline-flex;
      justify-content: center;
      // border-top: 2rem solid #d6d6d6;

      .item {
        width: 100rem;
        height: 40rem;
        margin-top: 20rem;
        text-align: center;
        margin-left: 20rem;
      }
      .item_btn1 {
        font-size: 14rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #9ab1cb;
        background: #082239;
        line-height: 40rem;
        border-radius: 2rem;
        border: 1rem solid #0d365f;
        cursor: pointer;
      }
      .item_btn2 {
        background: #0d365f;
        line-height: 40rem;
        color: #fff;
        border: 1rem solid #02a7fc;
        border-radius: 2rem;
        cursor: pointer;
      }
    }
    .QueryUserDialog-body {
      position: relative;
      overflow: hidden;
      width: 100%;
      height: 2210rem;
      .el-tree {
        background: transparent;
        height: 1770rem;
        overflow: auto;
        background: linear-gradient(0deg, #1a2f52 0%, rgba(26, 47, 82, 0) 100%);
        .el-tree-node__expand-icon {
          font-size: 60rem;
        }
      }
      .el-tree-node__content {
        height: 180rem;
      }
      .el-tree-node__content:hover,
      .el-tree-node:focus > .el-tree-node__content {
        background-color: transparent;
      }
      .el-checkbox {
        font-size: 15rem;
      }

      .QueryUserDialog-item {
        position: relative;
        float: left;
        width: 19%;
        height: 2210rem;
        // overflow: hidden;
        margin-right: 20rem;
        background: linear-gradient(0deg, #1a2f52 0%, rgba(26, 47, 82, 0) 100%);
        .QueryUserDialog-item-bg {
          top: 60rem;
          left: 0;
          bottom: 0;
          // right: 48rem;
          position: absolute;
          // background: rgba(255, 255, 255, 1);
          // border: 1rem solid rgba(218, 225, 229, 1);
          // box-shadow: 0rem 2rem 19rem 1rem rgba(96, 96, 96, 0.1);
        }

        .item-btn-warp {
          top: 50%;
          left: 0;
          width: 100%;
          height: 60rem;
          margin-top: -30rem;
          position: absolute;
          text-align: center;
          margin-left: -24rem;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: space-evenly;
          .i-btn {
            display: inline-block;
            line-height: 124rem;
            cursor: pointer;
            font-size: 60rem;
            margin: 20rem 30rem;
            color: #072649;
            width: 124rem;
            height: 124rem;
            background: linear-gradient(90deg, #00a3ff 0%, #8ad4ff 100%);
            border-radius: 20rem;
            box-sizing: border-box;
            border: 1rem solid #092a4b;
          }
        }

        .item-content {
          position: relative;
          overflow: hidden;
          .item-content-title {
            height: 164rem;
            line-height: 164rem;
            font-size: 80rem;
            margin-bottom: 10rem;
            line-height: 164rem;
            margin-right: 0rem;
            padding-left: 140rem;
            // text-align: center;
            // background: linear-gradient(0deg, rgba(12, 153, 219, 0.2) 0%, rgba(255, 255, 255, 0) 100%);
            color: #ffffff;
            background: url('../../../../assets/SmallTitle/一级标题1.png') no-repeat;
            background-size: 100% 100%;
            // background-position: center center;
            i {
              background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
              -webkit-background-clip: text;
              -webkit-text-fill-color: transparent;
            }
          }

          .item-content-list {
            position: relative;
            height: calc(100% - 80rem);
            // padding-top: 40rem;
            overflow-x: hidden;
            overflow-y: auto;

            .list-item {
              font-size: 60rem;
              font-weight: 400;
              cursor: pointer;
              color: #ffffff;

              &:before {
                content: ' ';
                display: block;
              }
              &:after {
                content: ' ';
                display: block;
                clear: both;
              }

              &.active {
                background: #092a4b;
                box-sizing: border-box;
                border: 1rem solid #02a7fc;
              }

              .el-checkbox {
                float: left;
                margin-left: 40rem;
                margin-right: 10rem;
                // margin-top: 10rem;
                line-height: 180rem;
              }

              .icon {
                font-size: 14rem;
                margin-left: 40rem;
                margin-right: 10rem;
                float: left;
                display: block;
                line-height: 98rem;
                color: #0c99db;
              }

              .el-checkbox + .icon {
                margin-left: 0;
              }

              .name {
                float: left;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                line-height: 180rem;
                height: 180rem;
                color: #9ab1cb;
              }
            }
          }

          .item-search-warp {
            position: relative;
            // margin: 20rem 88rem 10rem 40rem;

            .el-input {
              .el-input__inner {
                width: 100%;
                // border-radius: 40rem;
                padding-left: 120rem;
              }
              .el-input__prefix {
                margin-left: 5rem;
                margin-top: 3rem;
              }
              .el-input__icon {
                line-height: 112rem;
                font-size: 60rem;
              }
            }
          }
          .el-checkbox__inner {
            width: 46rem;
            height: 46rem;
          }
          .el-checkbox__input.is-checked .el-checkbox__inner::after {
            height: 28rem;
            left: 13rem;
            top: -6rem;
            width: 14rem;
          }
          .item-search-warp + .item-content-list {
            padding-top: 0;
            height: calc(100% - 190rem);
          }
        }
      }
    }
  }
}
</style>
