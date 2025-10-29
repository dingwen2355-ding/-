<template>
  <div>
    <div class="info_body">
      <div class="addOrganizationalModule">
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
                    <li style="width: 100px">{{ item.name }}</li>
                    <li v-if="isCode == 1">{{ user.name }}</li>
                    <li v-else>
                      <el-select
                        v-model="user.name"
                        placeholder="请选择"
                        filterable
                        :filter-method="filterMethod"
                        @change="changeLeader(user)"
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
                    <li class="delete-btn">
                      <i class="el-icon-delete-solid" @click="deleteItem(item.userList, user)"></i>
                    </li>
                    <!-- <li>{{ user.phone }}</li> -->
                  </ul>
                </div>
              </div>
              <div class="add-btn" v-if="isCode != 1" @click="openQueryUserDialog">
                <i class="el-icon-plus"></i> 新增
              </div>
            </div>
            <div class="item item2">
              <div class="command-set">
                <i class="iconfont iconzhgl_title"></i>
                <span>事件相关区属单位（{{ cityUnitArr.length }}个）</span>
              </div>
              <div class="list-content">
                <ul v-for="(item, index) in cityUnitArr" :key="'city' + index">
                  <li class="num" style="margin-left: 10px">{{ index + 1 }}</li>
                  <li :title="item.name" style="width: 100px">{{ item.name }}</li>
                  <li v-if="isCode != 1">
                    <el-select v-model="item.user.id" placeholder="请选择" filterable>
                      <el-option :key="''" :label="'无'" :value="''"></el-option>
                      <el-option
                        v-for="(leader, lIndex) in item.memberList"
                        :key="lIndex"
                        :label="leader.leader"
                        :value="leader.personId"
                      ></el-option>
                    </el-select>
                  </li>
                  <li v-else>{{ item.user.leader }}</li>
                  <li class="delete-btn">
                    <i class="el-icon-delete-solid" @click="deleteItem(cityUnitArr, item)"></i>
                  </li>
                </ul>
              </div>
              <div class="add-btn" @click="showAddDialog('cityUnit')" v-if="isCode != 1">
                <i class="el-icon-plus"></i> 新增
              </div>
            </div>
            <div class="item item3">
              <div class="command-set">
                <i class="iconfont iconzhgl_title"></i>
                <span>事件相关属地单位（{{ colonyUnitArr.length }}个）</span>
              </div>
              <div class="list-content">
                <ul v-for="(item, index) in colonyUnitArr" :key="index">
                  <li class="num" style="margin-left: 10px">{{ index + 1 }}</li>
                  <li :title="item.name" style="width: 100px">{{ item.name }}</li>
                  <li v-if="isCode == 1">{{ item.user.id }}</li>
                  <li v-else>
                    <el-select v-model="item.user.id" placeholder="请选择" filterable>
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
              <div class="add-btn" @click="showAddDialog('colonyUnit')" v-if="isCode != 1">
                <i class="el-icon-plus"></i> 新增
              </div>
            </div>
            <div class="item item4">
              <div class="command-set">
                <i class="iconfont iconzhgl_title"></i>
                <span>保供企业（{{ enterpriseArr.length }}个）</span>
              </div>
              <div class="list-content">
                <ul v-for="(item, index) in enterpriseArr" :key="index">
                  <li class="num" style="margin-left: 10px">{{ index + 1 }}</li>
                  <li :title="item.name" style="width: 100px">{{ item.name }}</li>
                  <li v-if="isCode == 1">{{ item.user.id }}</li>
                  <li v-else>
                    <el-select v-model="item.user.id" placeholder="请选择" filterable>
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
                    <i class="el-icon-delete-solid" @click="deleteItem(enterpriseArr, item)"></i>
                  </li>
                </ul>
              </div>
              <div class="add-btn" @click="showAddDialog('enterprise')" v-if="isCode != 1">
                <i class="el-icon-plus"></i> 新增
              </div>
            </div>
          </div>
        </div>
        <div class="addDialog" v-if="dialogVisible1">
          <div class="add-dialog-content">
            <div class="add-dialog-title">
              <div class="title-content">
                <i class="iconfont iconzhgl_title"></i>
                <span>新增</span>
                <i class="iconfont iconzhgl_title"></i>
              </div>
              <div class="close-icon" @click="closeAddDialog">
                <i class="el-icon-close"></i>
              </div>
            </div>
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
                <div class="QueryUserDialog-item" style="width: 52%">
                  <div class="QueryUserDialog-item-bg"></div>
                  <div class="item-content">
                    <div class="item-content-title">{{ transferTitles[0] }}</div>
                    <div class="item-search-warp">
                      <el-input
                        placeholder="请输入单位名称"
                        prefix-icon="el-icon-search"
                        v-model="searchUnitText"
                      ></el-input>
                    </div>
                    <div class="item-content-list">
                      <el-tree
                        :expand-on-click-node="false"
                        :check-strictly="true"
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
                <div class="QueryUserDialog-item" style="width: 12%">
                  <div class="item-btn-warp">
                    <div class="i-btn" @click="removeUsers()">
                      <i class="el-icon-arrow-left"></i>
                    </div>
                    <div class="i-btn" @click="addUsers()">
                      <i class="el-icon-arrow-right"></i>
                    </div>
                  </div>
                </div>
                <div class="QueryUserDialog-item" style="width: 36%">
                  <div class="QueryUserDialog-item-bg"></div>
                  <div class="item-content">
                    <div class="item-content-title">{{ transferTitles[1] }}</div>
                    <div class="item-content-list">
                      <div class="list-item" v-for="(item, i4) in eventSelectList" :key="i4">
                        <el-checkbox v-model="item.checked"></el-checkbox>
                        <span
                          :title="item.name"
                          class="name"
                          style="max-width: 75%"
                          @click="selectUserList(item)"
                        >
                          {{
                          item.name
                          }}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="colony-warp" style="width: 2100px" v-show="dialogType === 'colonyUnit'">
                <div class="vx-content">
                  <div class="vx-content-item" style="width: 20%">
                    <div class="vx-title">
                      <i class="iconfont iconzhgl_title"></i>
                      <span>滨湖区</span>
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
                      <ul
                        class="vx-list-ul"
                        style="display: flex"
                        v-for="(item, index) in selectUnitList"
                        :key="index"
                      >
                        <li style="width: 650px">
                          <i class="iconfont iconzhihui_person"></i>
                          {{ item.name }}
                        </li>
                        <li style="width: 170px">
                          <i
                            class="el-icon-delete-solid"
                            @click="deleteItem(selectUnitList, item, 1)"
                          ></i>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
              <div class="btn">
                <div class="item item_btn1" @click="closeAddDialog">取 消</div>
                <div class="item item_btn2" @click="submitDialog">提 交</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Axios from '@/utils/request'
export default {
  name: 'addOrganizationalModule',
  props: ['planId', 'isCode'],
  data() {
    return {
      linkType: 2,
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
        }
      ], // 指挥长设置
      searchInput: '',
      searchUnitText: '',
      dialogType: 'cityUnit',
      dialogVisible1: false,
      cityUnitArr: [], // 事件相关市属单位设置
      colonyUnitArr: [], // 事件相关属地单位设置
      enterpriseArr: [], // 保供企业设置
      transferArr: [], //
      transferValue: [],
      transferTitles: [],
      allColonyUnitList: [],
      allLeaderList: [], // 所有人员列表
      allLeaderList20: [], // 所有人员列表 - 前20
      leaderIds_999998: '',
      eventBelongList: [],
      eventSelectList: [],
      colonyUnitList: [],
      selectUnitList: []
    }
  },
  watch: {
    searchInput: {
      handler() {
        this.colonyUnitSearchList = []
      },
      immediate: true
    },
    searchUnitText: {
      handler(v) {
        if (!this.$refs.tree) return
        this.$refs.tree.filter(v)
      },
      immediate: true
    },
    planId(val) {
      if (!val) return
      this.getArrData3(val)
    }
  },
  mounted() {
    if (this.planId) {
      this.getArrData3(this.planId)
    }
    // 更新指挥体系
    this.$root.eventBus.$on('updateEventPlanLeaderAfter', (id = null) => {
      if (id) {
        this.linkType = 1
        this.planId = id
      } else {
        this.getArrData3(this.planId)
      }
    })
    this.$nextTick(() => {
      this.getAllLeaderList()
      this.getAllCityUnitList()
      this.getAllColonyUnitList()
      this.getAllEnterpriseList()
    })
  },
  methods: {
    deleteItem(array, item, e) {
      array.splice(array.indexOf(item), 1)
      if (e) {
        this.selectJudgeList = JSON.parse(JSON.stringify(array))
      }
    },
    selectAreatopId(item) {
      this.selectUnitList.push(item)
      this.selectUnitList = this.unique(this.selectUnitList)
      if (!this.isSelected(item)) {
        for (let i = 0; i < this.colonyUnitList.length; i++) {
          let uItem = this.colonyUnitList[i]
          uItem.selected = false
        }
        item.selected = true
      }
      this.selectJudgeList = JSON.parse(JSON.stringify(this.selectUnitList))
    },
    selectAreaInfo(item) {
      // if (item.data) {
      //   // 阻止多次请求 - 请求一次保存下来
      //   this.colonyUnitList = item.data
      // } else {
      let url = '/cdapis/communication/queryPrecinctList?upPrecinctId=' + item.areaCode
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
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
          if (this.selectJudgeList.length > 0) {
            let list = this.selectJudgeList.filter((r) => r.areaCode === item.areaCode)
            if (list.length > 0) {
              this.selectUnitList = this.selectJudgeList
            } else {
              this.selectUnitList = this.selectJudgeList
              this.selectUnitList.push(item)
            }
          }
        })
        .catch((err) => {
          console.error(err)
        })
      // }

      if (!this.isSelected(item)) {
        this.selectUnitList = [item]
        for (let i = 0; i < this.allColonyUnitList.length; i++) {
          let uItem = this.allColonyUnitList[i]
          uItem.selected = false
        }
        item.selected = true
      }
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
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    submitDialog() {
      debugger
      if (this.dialogType === 'cityUnit') {
        this.cityUnitArr = this.getUnitArr(this.eventSelectList)
      } else if (this.dialogType === 'enterprise') {
        this.enterpriseArr = this.getUnitArr(this.allEnterpriseList, 'prise')
      } else if (this.dialogType === 'colonyUnit') {
        this.colonyUnitArr = JSON.parse(JSON.stringify(this.selectUnitList))
      }
      this.closeAddDialog()
    },
    getUnitArr(array, v) {
      let unitArr = []
      console.log(array)
      for (let i = 0; i < array.length; i++) {
        if (v) {
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
          }
        } else {
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
      }
      return unitArr
    },
    handleCheckChange(data, checked) {
      this.eventBelongList = []
      this.eventBelongList = checked.checkedNodes
      console.log(data, checked)
    },
    showAddDialog(dialogType) {
      this.dialogVisible1 = true
      this.dialogType = dialogType
      if (dialogType === 'cityUnit') {
        // 区属单位
        debugger
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
        this.selectJudgeList = []
        this.selectUnitList = JSON.parse(JSON.stringify(this.colonyUnitArr))
        this.selectJudgeList = JSON.parse(JSON.stringify(this.selectUnitList))
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
      this.dialogVisible1 = false
    },
    openQueryUserDialog() {
      this.$root.eventBus.$emit('configOrganization', this.leaderArr)
    },
    getOrganizationInfo() {
      this.leaderIds_999998 = this.leaderArr[0].userList.map((item) => {
        return item.id
      })
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
      let params = {
        precinctId: 510100,
        planId: this.planId,
        linkType: '2',
        data: {
          1: {},
          2: cityUnitIdArr,
          3: colonyUnitIdArr,
          4: enterpriseIdArr
        }
      }
      params.data['1'] = {
        999998: this.leaderIds_999998.join(',')
      }
      let url = '/cdapis/Command/updateEventCommunicationByPrecinctId'
      return { params, url }
    },
    changeLeader(user) {
      if (!user.name) {
        user.phone = ' '
        return
      }
      for (let i = 0; i < this.allLeaderList.length; i++) {
        let leader = this.allLeaderList[i]
        if (user.name === leader.id) {
          user.id = leader.id
          user.phone = leader.phone
          break
        }
      }
    },
    getAllLeaderList() {
      let url = '/cdapis/communication/baseInfo?type=1,3'
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
        .then((res) => {
          let data = res.data.data.list
          this.allLeaderList = data || []
          if (this.allLeaderList.length > 20) {
            this.allLeaderList20 = this.allLeaderList.slice(0, 20)
          } else {
            this.allLeaderList20 = this.allLeaderList
          }
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getAllCityUnitList() {
      let url = '/cdapis/communication/unitInfoTree?type=1,2,3'
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
        .then((res) => {
          let data = res.data.data
          this.allCityUnitList = data || []
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getAllColonyUnitList() {
      let url = '/cdapis/communication/queryPrecinctList?precinctKind=110,120'
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
        .then((res) => {
          let data = res.data.data || []
          for (let i = 0; i < data.length; i++) {
            data[i].selected = false
            data[i].user = { id: '' }
            data[i].memberList = []
            // this.getUnitMemberList(data[i])
          }
          this.allColonyUnitList = data
        })
        .catch((err) => {
          console.error(err)
        })
    },
    getAllEnterpriseList() {
      let url = '/cdapis/communication/unitInfo?type=2'
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
        .then((res) => {
          let data = res.data.data
          this.allEnterpriseList = data || []
        })
        .catch((err) => {
          console.error(err)
        })
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
    getArrData3(planId) {
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
      let url =
        '/cdapis/Command/communicationOrderByDepartmentByPrecinctId?linkType=' +
        this.linkType +
        '&planId=' +
        planId +
        '&isMax=isMax'
      this.getArrData1(url)
    },
    getArrData1(url) {
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      })
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
                  cityUnit.user = { id: '', leader: '' }
                  cityUnit.dutyLevel = cityUnitObj[k].dutyLevel
                  // eslint-disable-next-line no-unused-expressions, no-sequences
                  if (cityUnit.memberList.length) {
                    ; (cityUnit.user.id = cityUnit.memberList[0].personId),
                      (cityUnit.user.leader = cityUnit.memberList[0].leader)
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
                  colonyUnit.user = { id: '', leader: '' }
                  colonyUnit.memberList = colonyUnitObj[k].data || []
                  // eslint-disable-next-line no-unused-expressions, no-sequences
                  if (colonyUnit.memberList.length) {
                    ; (colonyUnit.user.id = colonyUnit.memberList[0].personId),
                      (colonyUnit.user.leader = colonyUnit.memberList[0].leader)
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
                  enterprise.user = { id: '', leader: '' }
                  enterprise.memberList = enterpriseObj[k].data || []
                  // eslint-disable-next-line no-unused-expressions, no-sequences
                  if (enterprise.memberList.length) {
                    ; (enterprise.user.id = enterprise.memberList[0].personId),
                      (enterprise.user.leader = enterprise.memberList[0].leader)
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
    getTransferValue(arr) {
      this.transferValue = []
      arr.forEach((item) => {
        let id = item.id
        this.transferValue.push(id)
        this.eventSelectList.push(item)
      })
    },
    getUnitMemberList(unit) {
      if (unit.memberList.length) return
      let unitId = unit.id || unit.areaCode
      let url = '/cdapis/Command/unitMember?unitId=' + unitId
      Axios({
        method: 'get',
        url: url,
        headers: {
          Authorization: '12CBD9B708D887A41AFAB97DAC46AAC6'
        }
      }).then((res) => {
        let data = res.data.data || []
        data.forEach((item) => {
          item.personId = item.id
          item.leader = item.name
        })
        unit.memberList = data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.addOrganizationalModule {
  width: 100%;
  height: 500px;
  position: relative;
  z-index: 1000;
  .organizational-module-content {
    width: 100%;
    height: 100%;
    .content_item {
      width: 100%;
      display: inline-flex;
      justify-content: space-around;
      .item {
        width: 25%;
        padding-top: 45px;
        height: 450px;
        position: relative;
        .list-content {
          height: 400px;
          width: 100%;
          overflow: auto;
          ul {
            // width: 10%;
            // margin: 0 auto;
            height: 35px;
            line-height: 35px;
            position: relative;
            display: flex;
            &:hover .delete-btn {
              display: block;
            }
            .num {
              color: #0052d9;
              // width: 40px;
            }
            &:hover {
              background: rgba(239, 239, 239, 1);
            }
            li {
              height: 35px;
              line-height: 35px;
              float: left;
              margin: 0 10px 0 0;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              &.delete-btn {
                position: absolute;
                right: 10px;
                color: #0052d9;
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
          height: 45px;
          line-height: 45px;
          background: linear-gradient(0deg, rgba(0, 82, 217, 0.2) 0%, rgba(0, 82, 217, 0) 100%);
          font-size: 16px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(51, 51, 51, 1);
          i {
            font-size: 18px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, #0052d9 0%, #0052d9 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
        }
        .add-btn {
          width: 75%;
          height: 50px;
          background: rgba(255, 255, 255, 1);
          border: 2px solid #0052d9;
          border-radius: 8px;
          text-align: center;
          line-height: 50px;
          font-size: 16px;
          margin: -5px auto;
          cursor: pointer;
          i {
            color: #0052d9;
          }
        }
        &.item1 {
          // text-align: center;
          &::after {
            content: '';
            position: absolute;
            width: 1.5px;
            height: 500px;
            top: 10px;
            right: -4px;
            background: linear-gradient(
              0deg,
              rgba(0, 168, 255, 0) 0%,
              rgba(0, 82, 217, 100) 49%,
              rgba(0, 168, 255, 0) 100%
            );
          }
        }
        &.item2 {
          // background-color: red;
          &::after {
            content: '';
            position: absolute;
            width: 1.5px;
            height: 500px;
            top: 10px;
            right: -4px;
            background: linear-gradient(
              0deg,
              rgba(0, 168, 255, 0) 0%,
              rgba(0, 82, 217, 100) 49%,
              rgba(0, 168, 255, 0) 100%
            );
          }
        }
        &.item3 {
          // background-color: green;
          &::after {
            content: '';
            position: absolute;
            width: 1.5px;
            height: 500px;
            top: 10px;
            right: -4px;
            background: linear-gradient(
              0deg,
              rgba(0, 168, 255, 0) 0%,
              rgba(0, 82, 217, 100) 49%,
              rgba(0, 168, 255, 0) 100%
            );
          }
        }
        &.item4 {
          // background-color: green;
        }
      }
    }
    .btn {
      width: 100%;
      height: 90px;
      // background-color: #0ff;
      display: inline-flex;
      justify-content: center;
      border-top: 2px solid #d6d6d6;

      .item {
        width: 168px;
        height: 50px;
        margin-top: 30px;
        text-align: center;
      }
      .item_btn1 {
        font-size: 24px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: rgba(0, 82, 217, 100);
        line-height: 58px;
        cursor: pointer;
      }
      .item_btn2 {
        background: linear-gradient(-90deg, #0052d9 0%, #0052d9 100%);
        border-radius: 8px;
        line-height: 58px;
        color: #fff;
        cursor: pointer;
      }
    }
  }
  .addDialog {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 100000;
    background: rgba(0, 0, 0, 0.5);
    .add-dialog-content {
      width: 72%;
      height: 75%;
      background: white;
      border-radius: 10px;
      margin: 6% auto 0;
      position: relative;
      .add-dialog-title {
        width: 100%;
        height: 100px;
        border-bottom: 4px solid #d9e1e5;
        display: inline-flex;
        justify-content: center;
        align-items: center;
        .title-content {
          width: 156px;
          height: 50%;
          font-size: 30px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: rgba(51, 51, 51, 1);
          line-height: 35px;
          i:nth-of-type(1) {
            font-size: 30px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
          i:nth-of-type(2) {
            font-size: 30px;
            margin: 0 25px 0 20px;
            background: linear-gradient(-90deg, rgba(0, 150, 255, 1) 0%, rgba(0, 215, 254, 1) 100%);
            // box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
        }
        .close-icon {
          position: absolute;
          right: 38px;
          top: 38px;
          width: 48px;
          height: 48px;
          border-radius: 2px;
          i {
            font-size: 30px;
            color: rgba(182, 182, 182, 1);
          }
        }
      }
      .transfer-content {
        width: 100%;
        height: 650px;
        display: inline-flex;
        justify-content: center;
        ::v-deep .el-transfer-panel {
          width: 500px;
        }
        ::v-deep .el-transfer-panel__body {
          height: 500px;
        }
        ::v-deep .el-transfer-panel__list.is-filterable {
          height: 80%;
        }
        .vx-content {
          width: 100%;
          height: 100%;
          .vx-content-item {
            height: 100%;
            width: 32%;
            float: left;
            margin: 0 2% 0 0;
            &.vx-content-margin {
              margin-left: 8%;
            }
            .vx-title {
              // width: 500px;
              height: 118px;
              background: linear-gradient(0deg, rgba(12, 153, 219, 0.2) 0%, rgba(255, 255, 255, 0) 100%);
              margin: 0 auto;
              line-height: 118px;
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
            .vx-input {
              margin: 32px 0 23px 0;
              width: 500px;
              height: 80px;
              input {
                border: none;
              }
            }
            .vx-list {
              // width: 500px;
              height: 60%;
              margin: 0 auto;
              overflow-y: auto;
              overflow-x: hidden;
              &::-webkit-scrollbar {
                width: 0 !important;
              }
              .vx-list-ul {
                width: 100%;
                height: 30px;
                margin: 5px 0;
                display: block;
                &:hover {
                  background-image: url('../../../../assets/global/choice_list.png');
                  background-size: 100% 100%;
                }
                &.active {
                  background-image: url('../../../../assets/global/choice_list.png');
                  background-size: 100% 100%;
                }
                li {
                  width: 100%;
                  height: 30px;
                  font-size: 15px;
                  line-height: 30px;
                  // background: rgba(12, 153, 219, 0.1);
                  overflow: hidden;
                  cursor: pointer;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  i {
                    font-size: 15px;
                    line-height: 30px;
                    margin: 0 23px 0 46px;
                    color: #0c99db;
                  }
                }
              }
            }

            .search-warp {
              margin-top: 20px;

              .el-input__inner {
                padding-left: 60px;
              }
            }
          }
        }
        .btn {
          position: absolute;
          bottom: 0;
          width: 100%;
          height: 60px;
          // background-color: #0ff;
          display: inline-flex;
          justify-content: center;
          border-top: 2px solid #d6d6d6;

          .item {
            width: 125px;
            height: 40px;
            margin-top: 10px;
            text-align: center;
          }
          .item_btn1 {
            font-size: 18px;
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
        .el-button {
          font-size: 24px;
          line-height: 50px;
          padding: 0 40px;
        }
        .el-tree {
          background: transparent;
          .el-tree-node__expand-icon {
            font-size: 30px;
          }
        }
        .el-tree-node__content {
          height: 30px;
        }
        .el-tree-node__content:hover,
        .el-tree-node:focus > .el-tree-node__content {
          background-color: transparent;
        }
      }
    }
  }
  .item .el-input .el-input__inner {
    width: 200px;
    color: #5b5b5b;
    border: none;
  }
  .QueryUserDialog-body {
    position: relative;
    width: 100%;
    height: calc(100% - 80px);
    overflow: hidden;

    .QueryUserDialog-item {
      position: relative;
      float: left;
      width: 19%;
      height: 100%;
      overflow: hidden;

      .QueryUserDialog-item-bg {
        top: 80px;
        left: 0;
        bottom: 0;
        right: 48px;
        position: absolute;
        background: rgba(255, 255, 255, 1);
        border: 1px solid rgba(218, 225, 229, 1);
        box-shadow: 0px 2px 19px 1px rgba(96, 96, 96, 0.1);
      }

      .item-btn-warp {
        top: 50%;
        left: 0;
        width: 100%;
        height: 60px;
        margin-top: -30px;
        position: absolute;
        text-align: center;
        margin-left: -24px;

        .i-btn {
          display: inline-block;
          line-height: 60px;
          height: 60px;
          width: 60px;
          cursor: pointer;
          font-size: 24px;
          margin: 0 30px;
          background-color: #0c99db;
          color: #fff;
          border-radius: 10px;
        }
      }

      .item-content {
        position: relative;
        height: 100%;
        overflow: hidden;

        .item-content-title {
          height: 80px;
          margin-bottom: 10px;
          line-height: 80px;
          margin-right: 48px;
          text-align: center;
          background: linear-gradient(0deg, rgba(12, 153, 219, 0.2) 0%, rgba(255, 255, 255, 0) 100%);

          i {
            background: linear-gradient(-90deg, rgba(0, 215, 254, 1) 0%, rgba(0, 150, 255, 1) 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
          }
        }

        .item-content-list {
          position: relative;
          height: calc(100% - 80px);
          // padding-top: 40px;
          overflow-x: hidden;
          overflow-y: auto;
          width: 80%;

          .list-item {
            font-size: 15px;
            font-weight: 400;
            cursor: pointer;
            color: #5b5b5b;

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
              background-image: url('../../../../assets/global/choice_list.png');
              background-size: 100% 100%;
            }

            .el-checkbox {
              float: left;
              margin-left: 40px;
              margin-right: 10px;
              margin-top: 8px;
            }

            .icon {
              font-size: 15px;
              margin-left: 40px;
              margin-right: 10px;
              float: left;
              display: block;
              line-height: 30px;
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
              line-height: 30px;
              width: 140px;
              height: 30px;
              // max-width: 82%;
              padding-left: 10px;
              // max-width: calc(100% - 148px);
            }
          }
        }

        .item-search-warp {
          position: relative;
          margin: 20px 88px 10px 40px;

          .el-input {
            .el-input__inner {
              width: 100%;
              border-radius: 40px;
              padding-left: 55px;
            }
            .el-input__prefix {
              margin-left: 5px;
              margin-top: 3px;
            }
          }
        }

        .item-search-warp + .item-content-list {
          padding-top: 0;
          height: calc(100% - 190px);
        }
      }
    }
  }
}
</style>
