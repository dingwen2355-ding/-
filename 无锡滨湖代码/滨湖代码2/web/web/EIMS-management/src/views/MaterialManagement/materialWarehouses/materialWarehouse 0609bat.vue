<template>
  <div class="mater">
    <div v-loading="loading" style="margin-top: 20px; display: flex">
      <el-select
        @change="selectMain"
        style="margin: 10px 5px 0px 0px; width: 149px"
        v-model="type"
        placeholder="统计类型"
      >
        <el-option value="1" label="按仓库统计"></el-option>
        <el-option value="3" label="按仓库统计(明细)"></el-option>
        <el-option value="2" label="按区县统计" v-if="wareHouseLevel.indexOf(userLevel) === -1"></el-option>
        <el-option value="4" label="物资储备统计" v-if="wareHouseLevel.indexOf(userLevel) === -1"></el-option>
        <el-option value="5" label="物资统计(横排)" v-if="wareHouseLevel.indexOf(userLevel) === -1"></el-option>
      </el-select>

      <!--按仓库统计-->
      <div v-if="type === '1'" style="display: flex">
        <el-input style="margin: 10px; width: 149px" placeholder="仓库名称" v-model="filterText"> </el-input>
        <el-select
          @change="checkType"
          style="margin: 10px 5px 0px 0px; width: 149px"
          v-model="filterType"
          multiple
          collapse-tags
          placeholder="救援类型"
        >
          <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
        </el-select>
        <el-select
          @change="checkType"
          style="margin: 10px 5px 0px 0px; width: 149px"
          v-model="filterManage"
          clearable
          placeholder="管理类型"
        >
          <el-option v-for="item in manageList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
        </el-select>
        <el-select
          @change="checkType"
          style="margin: 10px 5px 0px 0px; width: 149px"
          v-model="filterLevel"
          clearable
          placeholder="仓库等级"
        >
          <el-option v-for="item in levelList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
        </el-select>
        <el-select
          @change="checkType"
          style="margin: 10px 5px 0px 0px; width: 149px"
          v-model="stockType"
          clearable
          placeholder="仓库类型"
        >
          <el-option v-for="item in stockTypeList" :value="item.name" :label="item.name" :key="item.id"> </el-option>
        </el-select>
      </div>

      <!--按仓库统计-->
      <div v-show="type === '1'" style="margin-top: 10px; font-weight: 600">
        <el-popover
          v-show="type === '1'"
          placement="bottom-start"
          title="请选择仓库"
          width="80%"
          trigger="manual"
          style="margin-top: 10px; font-weight: 600"
          popper-class="materialWarehousePopper"
          v-model="treeVisible"
        >
          <el-checkbox v-model="checked" @change="handleCheckAllChange">按仓库统计</el-checkbox>
          <div class="mater_tree" v-show="type === '1'">
            <div class="main_tree">
              <el-tree
                :data="tableData"
                show-checkbox
                v-loading="loading"
                node-key="id"
                :filter-node-method="filterNode"
                :props="defaultProps"
                @check-change="handleCheckChange"
                ref="tree"
              >
                <span class="span-ellipsis" slot-scope="{ node, data }">
                  <span :title="node.label">{{
                    node.label.length > 30 ? node.label.substring(0, 30) + '...' : node.label
                  }}</span>
                </span>
              </el-tree>
            </div>
          </div>
          <el-button slot="reference" @click="treeVisible = !treeVisible">选择仓库/区县</el-button>
        </el-popover>
      </div>

      <!--按仓库统计(明细)-->
      <div v-if="type === '3'" style="display: flex">
        <div style="display: flex">
          <el-input style="margin: 10px; width: 149px" placeholder="仓库名称" v-model="filterText2"> </el-input>
          <el-select
            @change="checkType2"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterType2"
            multiple
            collapse-tags
            placeholder="救援类型"
          >
            <el-option v-for="item in typeList2" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType2"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterManage2"
            clearable
            placeholder="管理类型"
          >
            <el-option v-for="item in manageList2" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType2"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterLevel2"
            clearable
            placeholder="仓库等级"
          >
            <el-option v-for="item in levelList2" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="stockType2"
            clearable
            placeholder="仓库类型"
          >
            <el-option v-for="item in stockTypeList" :value="item.name" :label="item.name" :key="item.id"> </el-option>
          </el-select>
        </div>
      </div>
      <!--按仓库统计(明细)-->
      <div v-show="type === '3'" style="margin-top: 10px; font-weight: 600">
        <el-popover
          placement="bottom-start"
          title="请选择仓库"
          width="80%"
          trigger="manual"
          style="margin-top: 10px; font-weight: 600"
          popper-class="materialWarehousePopper"
          v-model="treeVisible3"
        >
          <el-checkbox v-model="checked2" @change="handleCheckAllChange2">按仓库统计(明细)</el-checkbox>
          <div class="mater_tree" v-show="type === '3'">
            <div class="main_tree">
              <el-tree
                :data="tableData"
                show-checkbox
                node-key="id"
                v-loading="loading"
                check-on-click-node
                :filter-node-method="filterNode2"
                :props="defaultProps"
                @check-change="handleCheckChange2"
                ref="tree2"
              >
                <span class="span-ellipsis" slot-scope="{ node, data }">
                  <span :title="node.label">{{
                    node.label.length > 9 ? node.label.substring(0, 9) + '...' : node.label
                  }}</span>
                </span>
              </el-tree>
            </div>
          </div>
          <el-button slot="reference" @click="treeVisible3 = !treeVisible3">选择仓库/区县</el-button>
        </el-popover>
      </div>

      <!--按区县统计-->
      <div v-if="type === '2'" style="display: flex">
        <div style="display: flex">
          <el-select
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterType4"
            multiple
            collapse-tags
            placeholder="救援类型"
          >
            <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterManage4"
            clearable
            placeholder="管理类型"
          >
            <el-option v-for="item in manageList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterLevel4"
            clearable
            placeholder="仓库等级"
          >
            <el-option v-for="item in levelList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="stockType4"
            clearable
            placeholder="仓库类型"
          >
            <el-option v-for="item in stockTypeList" :value="item.name" :label="item.name" :key="item.id"> </el-option>
          </el-select>
        </div>
      </div>
      <!--按区县统计-->
      <div v-show="type === '2'" style="margin-top: 10px; font-weight: 600">
        <el-popover
          v-show="type === '2'"
          placement="bottom-start"
          title="请选择区县"
          width="80%"
          trigger="manual"
          style="margin-top: 10px; font-weight: 600"
          popper-class="materialWarehousePopper"
          v-model="treeVisible2"
        >
          <div class="mater_tree" v-loading="loading">
            <div class="main_tree" v-if="type === '2'" v-show="false">
              <el-tree
                :data="tableData"
                show-checkbox
                v-loading="loading"
                node-key="id"
                :filter-node-method="filterNode4"
                :props="defaultProps"
                @check-change="handleCheckChange4"
                ref="tree4"
              >
                <span class="span-ellipsis" slot-scope="{ node, data }">
                  <span :title="node.label">{{
                    node.label.length > 30 ? node.label.substring(0, 30) + '...' : node.label
                  }}</span>
                </span>
              </el-tree>
            </div>
            <div class="main_tree">
              <el-checkbox v-model="checked3" @change="handleCheckAllChange3">全选</el-checkbox>
              <div class="mater_tree_item" v-for="(item, index) in tableData" :key="index">
                <el-checkbox
                  v-model="checkObj[item.id]"
                  :true-label="item.id"
                  :false-label="null"
                  @change="itemChange(item.id, $event)"
                  >{{ item.label }}</el-checkbox
                >
              </div>
            </div>
          </div>
          <el-button slot="reference" @click="treeVisible2 = !treeVisible2">选择仓库/区县</el-button>
        </el-popover>
      </div>

      <!--物资协议统计-->
      <div v-if="type === '4'" style="display: flex">
        <div style="display: flex">
          <el-input style="margin: 10px; width: 149px" placeholder="仓库名称" v-model="filterText3"> </el-input>
          <el-select
            @change="checkType3"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterType3"
            multiple
            collapse-tags
            placeholder="救援类型"
          >
            <el-option v-for="item in typeList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType3"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterManage3"
            clearable
            placeholder="管理类型"
          >
            <el-option v-for="item in manageList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType3"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterLevel3"
            clearable
            placeholder="仓库等级"
          >
            <el-option v-for="item in levelList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType3"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="stockType3"
            clearable
            placeholder="仓库类型"
          >
            <el-option v-for="item in stockTypeList" :value="item.name" :label="item.name" :key="item.id"> </el-option>
          </el-select>
        </div>
      </div>
      <!--物资协议统计-->
      <div v-show="type === '4'" style="margin-top: 10px; font-weight: 600">
        <el-popover
          placement="bottom-start"
          title="请选择仓库"
          width="80%"
          trigger="manual"
          popper-class="materialWarehousePopper"
          v-model="treeVisible4"
        >
          <el-checkbox v-model="checked4" @change="handleCheckAllChange4">物资协议统计</el-checkbox>
          <div class="mater_tree" v-show="type === '4'">
            <div class="main_tree">
              <el-tree
                :data="tableData"
                show-checkbox
                v-loading="loading"
                node-key="id"
                :filter-node-method="filterNode3"
                :props="defaultProps"
                @check-change="handleCheckChange3"
                ref="tree3"
              >
                <span class="span-ellipsis" slot-scope="{ node, data }">
                  <span :title="node.label">{{
                    node.label.length > 9 ? node.label.substring(0, 9) + '...' : node.label
                  }}</span>
                </span>
              </el-tree>
            </div>
          </div>
          <el-button slot="reference" @click="treeVisible4 = !treeVisible4">选择仓库/区县</el-button>
        </el-popover>
      </div>

      <!--物资统计(横排)-->
      <div v-if="type === '5'" style="display: flex">
        <div style="display: flex">
          <el-input style="margin: 10px; width: 149px" placeholder="仓库名称" v-model="filterText5"> </el-input>
          <el-select
            @change="checkType5"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterType5"
            multiple
            collapse-tags
            placeholder="救援类型"
          >
            <el-option v-for="item in typeList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType5"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterManage5"
            clearable
            placeholder="管理类型"
          >
            <el-option v-for="item in manageList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType5"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="filterLevel5"
            clearable
            placeholder="仓库等级"
          >
            <el-option v-for="item in levelList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
          </el-select>
          <el-select
            @change="checkType5"
            style="margin: 10px 5px 0px 0px; width: 149px"
            v-model="stockType5"
            clearable
            placeholder="仓库类型"
          >
            <el-option v-for="item in stockTypeList" :value="item.name" :label="item.name" :key="item.id"> </el-option>
          </el-select>
        </div>
      </div>
      <!--物资统计(横排)-->
      <div v-show="type === '5'" style="margin-top: 10px; font-weight: 600">
        <el-popover
          placement="bottom-start"
          title="请选择仓库"
          width="80%"
          trigger="manual"
          popper-class="materialWarehousePopper"
          v-model="treeVisible5"
        >
          <el-checkbox v-model="checked5" @change="handleCheckAllChange5">物资统计(横排)</el-checkbox>
          <div class="mater_tree" v-show="type === '5'">
            <div class="main_tree">
              <el-tree
                :data="tableData"
                show-checkbox
                v-loading="loading"
                node-key="id"
                :filter-node-method="filterNode5"
                :props="defaultProps"
                @check-change="handleCheckChange5"
                ref="tree5"
              >
                <span class="span-ellipsis" slot-scope="{ node, data }">
                  <span :title="node.label">{{
                    node.label.length > 9 ? node.label.substring(0, 9) + '...' : node.label
                  }}</span>
                </span>
              </el-tree>
            </div>
          </div>
          <el-button slot="reference" @click="treeVisible5 = !treeVisible5">选择仓库/区县</el-button>
        </el-popover>
      </div>
    </div>
    <div class="mater_body">
      <div class="mater_exel">
        <div>统计周期</div>
        <div class="mater_radio">
          <el-radio-group :disabled="type === '4' || type === '5'" v-model="radio" @change="handleTime">
            <el-radio :label="1">近1个月</el-radio>
            <el-radio :label="2">近3个月</el-radio>
            <el-radio :label="3">近6个月</el-radio>
            <el-radio :label="4">近1年</el-radio>
            <el-radio :label="5">自定义</el-radio>
          </el-radio-group>
          <el-date-picker
            style="margin-left: 10px"
            v-model="datetimerange"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            v-if="radio === 5"
          >
          </el-date-picker>
        </div>
        <el-button class="num" @click="handleTong">统计</el-button>
        <el-button
          type="success"
          @click="handleExelFile"
          class="exel"
          :disabled="urlExel == undefined || urlExel == null || urlExel == ''"
        >
          Excel统计表下载
        </el-button>
        <!-- <el-button type="primary" @click="viewExelFile('newPage')"
          class="exel" :disabled="urlExel == undefined || urlExel == null || urlExel == ''">
          Excel在新页面打开
        </el-button> -->
      </div>
      <div class="mater_img_title">
        <div class="mater_text_wait" v-if="dataLoading">正在统计，请稍候……</div>
        <div class="mater_text" v-if="urlExel && !dataLoading">(正在预览，请下载Excel台账)</div>
        <div class="mater_img" v-if="dataLoading" v-loading="dataLoading"></div>
        <iframe
          v-if="urlExel !== undefined && urlExel !== null && urlExel !== '' && !dataLoading"
          height="770px"
          width="1550px"
          :src="excelViewUrl"
          style="border: none"
        >
          <p>您的浏览器不支持,请尝试使用Chrome浏览器</p>
        </iframe>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import Axios from 'axios'
// import router from "../../router";
export default {
  data() {
    return {
      type: '1',
      radio: 1,
      loading: true,
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: [configs.wareHouseLevel, '400'],
      datetimerange: [],
      treeVisible: false,
      treeVisible2: false,
      treeVisible3: false,
      treeVisible4: false,
      treeVisible5: false,
      tableData: [],
      tableData2: [],
      tableData3: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      checkedId: [],
      beginTime: '',
      endTime: '',
      urlExel: '',
      newarr: [],
      newCheckedId: [],
      regionCheckedId: [],
      filterText: '',
      filterType: '',
      filterType2: '',
      filterType3: '',
      filterType4: '',
      filterType5: '',
      filterLevel: '',
      filterLevel2: '',
      filterLevel3: '',
      filterLevel4: '',
      filterLevel5: '',
      filterManage: '',
      filterManage2: '',
      filterManage3: '',
      filterManage4: '',
      filterManage5: '',
      stockType: '',
      stockType2: '',
      stockType3: '',
      stockType4: '',
      stockType5: '',
      checked: false,
      checked2: false,
      checked3: false,
      checked4: false,
      checked5: false,
      checkObj: {},
      typeList: [],
      typeList2: [],
      typeList3: [],
      manageList: [],
      manageList2: [],
      manageList3: [],
      levelList: [],
      levelList2: [],
      levelList3: [],
      tempList: [],
      filterText2: '',
      filterText3: '',
      filterText5: '',
      showTag: false,
      dataLoading: false,
      excelViewUrl: '',
      stockTypeList: [
        { id: 1, name: '物资库' },
        { id: 2, name: '装备库' },
        { id: 3, name: '其他' },
        { id: 4, name: '救援队伍' },
        { id: 5, name: '社会力量' },
        { id: 6, name: '监管企业' }
      ]
    }
  },
  watch: {
    filterText(val) {
      this.checkType()
    },
    filterText2(val) {
      this.checkType2()
    },
    filterText3(val) {
      this.checkType3()
    },
    filterText5(val) {
      this.checkType5()
    }
  },
  // 生命周期 - 创建完成（访问当前this实例）
  created() {},
  // 生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.getHelpTypeList()
    this.getStorageLevelList()
    this.getBeManagedTypeList()
    if (this.wareHouseLevel.indexOf(this.userLevel) > -1) {
      this.queryChecked()
    } else {
      this.getList()
    }
  },
  methods: {
    queryChecked() {
      if (this.wareHouseLevel.indexOf(this.userLevel) > -1) {
        let url = `/gtw/EIMS-management/warehouse/warehouseDetailById?userId=${localStorage.getItem('userId')}`
        Axios.get(url).then((r) => {
          this.tableData = r.data.data || []
          this.loading = false
        })
      } else {
        // console.log(this.userLevel)
      }
    },
    handleClick(tab, event) {
      // console.log(tab, event)
    },
    itemChange(val, event) {
      if (this.regionCheckedId.indexOf(val) === -1) {
        this.regionCheckedId.push(val)
        this.checkObj[val] = true
      } else {
        this.regionCheckedId.splice(this.regionCheckedId.indexOf(val), 1)
        this.checkObj[val] = false
      }
      this.$forceUpdate()
      // console.log(this.regionCheckedId)
    },
    filterNode(value, data) {
      var nodes = this.$refs.tree.store.nodesMap
      for (var i in nodes) {
        nodes[i].expanded = true
      }
      if (data.helpTypes === '') return false
      if (!data.helpTypes) return true
      if (data.storageLevel === '') return false
      if (!data.storageLevel) return false
      if (data.beManagedType === '') return false
      if (!data.beManagedType) return false
      let textKey = this.filterText === '' ? true : data.label.indexOf(this.filterText) !== -1
      let typeKey = this.filterType === '' ? true : data.helpTypes.indexOf(this.filterType) !== -1
      let manageKey = this.filterManage === '' ? true : data.beManagedType.indexOf(this.filterManage) !== -1
      let levelKey = this.filterLevel === '' ? true : data.storageLevel.indexOf(this.filterLevel) !== -1
      let stockType = this.stockType === '' ? true : data.stockType.indexOf(this.stockType) !== -1
      return textKey && typeKey && manageKey && levelKey && stockType
    },
    filterNode4(value, data) {
      if (data.helpTypes === '') return false
      if (!data.helpTypes) return true
      if (data.storageLevel === '') return false
      if (!data.storageLevel) return false
      if (data.beManagedType === '') return false
      if (!data.beManagedType) return false

      let typeKey4 = this.filterType4 === '' ? true : data.helpTypes.indexOf(this.filterType4) !== -1
      let manageKey4 = this.filterManage4 === '' ? true : data.beManagedType.indexOf(this.filterManage4) !== -1
      let levelKey4 = this.filterLevel4 === '' ? true : data.storageLevel.indexOf(this.filterLevel4) !== -1
      if (typeKey4 && manageKey4 && levelKey4) {
        this.tempList.push(data)
      }
      return typeKey4 && manageKey4 && levelKey4
    },
    filterNode5(value, data) {
      var nodes = this.$refs.tree5.store.nodesMap
      for (var i in nodes) {
        nodes[i].expanded = true
      }
      if (data.helpTypes === '') return false
      if (!data.helpTypes) return true
      if (data.storageLevel === '') return false
      if (!data.storageLevel) return false
      if (data.beManagedType === '') return false
      if (!data.beManagedType) return false
      let textKey = this.filterText5 === '' ? true : data.label.indexOf(this.filterText5) !== -1
      let typeKey = this.filterType5 === '' ? true : data.helpTypes.indexOf(this.filterType5) !== -1
      let manageKey = this.filterManage5 === '' ? true : data.beManagedType.indexOf(this.filterManage5) !== -1
      let levelKey = this.filterLevel5 === '' ? true : data.storageLevel.indexOf(this.filterLevel5) !== -1
      let stockType = this.stockType5 === '' ? true : data.stockType.indexOf(this.stockType5) !== -1
      return textKey && typeKey && manageKey && levelKey && stockType
    },
    filterNode2(value, data) {
      var nodes = this.$refs.tree2.store.nodesMap
      for (var i in nodes) {
        nodes[i].expanded = true
      }
      if (data.helpTypes === '') return false
      if (!data.helpTypes) return true
      if (data.storageLevel === '') return false
      if (!data.storageLevel) return false
      if (data.beManagedType === '') return false
      if (!data.beManagedType) return false
      let textKey = this.filterText2 === '' ? true : data.label.indexOf(this.filterText2) !== -1
      let typeKey = this.filterType2 === '' ? true : data.helpTypes.indexOf(this.filterType2) !== -1
      let manageKey = this.filterManage2 === '' ? true : data.beManagedType.indexOf(this.filterManage2) !== -1
      let levelKey = this.filterLevel2 === '' ? true : data.storageLevel.indexOf(this.filterLevel2) !== -1
      let stockType = this.stockType2 === '' ? true : data.stockType.indexOf(this.stockType2) !== -1
      return textKey && typeKey && manageKey && levelKey && stockType
    },
    filterNode3(value, data) {
      var nodes = this.$refs.tree3.store.nodesMap
      for (var i in nodes) {
        nodes[i].expanded = true
      }
      if (data.helpTypes === '') return false
      if (!data.helpTypes) return true
      if (data.storageLevel === '') return false
      if (!data.storageLevel) return false
      if (data.beManagedType === '') return false
      if (!data.beManagedType) return false
      let textKey = this.filterText3 === '' ? true : data.label.indexOf(this.filterText3) !== -1
      let typeKey = this.filterType3 === '' ? true : data.helpTypes.indexOf(this.filterType3) !== -1
      let manageKey = this.filterManage3 === '' ? true : data.beManagedType.indexOf(this.filterManage3) !== -1
      let levelKey = this.filterLevel3 === '' ? true : data.storageLevel.indexOf(this.filterLevel3) !== -1
      let stockType = this.stockType3 === '' ? true : data.stockType.indexOf(this.stockType3) !== -1
      return textKey && typeKey && manageKey && levelKey && stockType
    },
    handleCheckout(val) {
      if (val === true) {
        for (let v = 0; v < this.newarr.length; v++) {
          this.newarr[v].forEach((i) => {
            this.newCheckedId.push(i.id)
          })
        }
        this.$refs.tree.setCheckedKeys(this.newCheckedId)
      } else {
        this.newCheckedId = []
        this.$refs.tree.setCheckedKeys([])
      }
    },
    getHelpTypeList() {
      let url = '/gtw/EIMS-management/teamInfo/emergencyPowerDictList'
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.typeList = data
        this.typeList2 = data
        this.typeList3 = data
      })
    },
    getBeManagedTypeList() {
      let url = '/gtw/EIMS-management/stockInfoData/getBeManagedType'
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.manageList = data.data
        this.manageList2 = data.data
        this.manageList3 = data.data
      })
    },
    getStorageLevelList() {
      let url = '/gtw/EIMS-management/stockInfoData/getStorageLevel'
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.levelList = data.data
        this.levelList2 = data.data
        this.levelList3 = data.data
      })
    },
    getList() {
      this.tableData = []
      this.newarr = []
      let url = '/gtw/EIMS-management/warehouse/warehouseDetail'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.tableData = res.data.data || []
          this.newarr = this.tableData.map((v) => {
            return v.children
          })
          this.loading = false
        }
      })
    },
    handleCheckChange(data, checked, indeterminate) {
      this.checkKeys = this.$refs.tree.getCheckedKeys()
      this.checkKeys.forEach((i, n) => {
        // fix el-tree过滤Selectall,全部都选择上
        var node = this.$refs.tree.getNode(i)
        if (!node.visible && node.checked) {
          // 过滤隐藏and选中的节点。设置为false
          this.$refs.tree.setChecked(i, false)
        }
      })
    },
    handleCheckChange2(data, checked, indeterminate) {
      this.checkKeys = this.$refs.tree2.getCheckedKeys()
      this.checkKeys.forEach((i, n) => {
        // fix el-tree过滤Selectall,全部都选择上
        var node = this.$refs.tree2.getNode(i)
        if (!node.visible && node.checked) {
          // 过滤隐藏and选中的节点。设置为false
          this.$refs.tree2.setChecked(i, false)
        }
      })
    },
    handleCheckChange3(data, checked, indeterminate) {
      this.checkKeys = this.$refs.tree3.getCheckedKeys()
      this.checkKeys.forEach((i, n) => {
        // fix el-tree过滤Selectall,全部都选择上
        var node = this.$refs.tree3.getNode(i)
        if (!node.visible && node.checked) {
          // 过滤隐藏and选中的节点。设置为false
          this.$refs.tree3.setChecked(i, false)
        }
      })
    },
    handleCheckChange4(data, checked, indeterminate) {
      this.checkKeys = this.$refs.tree4.getCheckedKeys()
      this.checkKeys.forEach((i, n) => {
        // fix el-tree过滤Selectall,全部都选择上
        var node = this.$refs.tree4.getNode(i)
        if (!node.visible && node.checked) {
          // 过滤隐藏and选中的节点。设置为false
          this.$refs.tree4.setChecked(i, false)
        }
      })
    },
    handleCheckChange5(data, checked, indeterminate) {
      this.checkKeys = this.$refs.tree5.getCheckedKeys()
      this.checkKeys.forEach((i, n) => {
        // fix el-tree过滤Selectall,全部都选择上
        var node = this.$refs.tree5.getNode(i)
        if (!node.visible && node.checked) {
          // 过滤隐藏and选中的节点。设置为false
          this.$refs.tree5.setChecked(i, false)
        }
      })
    },
    handleRealCheck1() {
      let realCheckNodes = []
      let checkedNodes = this.$refs.tree.getCheckedNodes()
      checkedNodes.forEach((v) => {
        if (!v.children) {
          realCheckNodes.push(v)
        }
      })
      // console.log(realCheckNodes.length)
      return realCheckNodes
    },
    handleRealCheck2() {
      let realCheckNodes = []
      let checkedNodes = this.$refs.tree2.getCheckedNodes()
      checkedNodes.forEach((v) => {
        if (!v.children) {
          realCheckNodes.push(v)
        }
      })
      // console.log(realCheckNodes.length)
      return realCheckNodes
    },
    handleRealCheck5() {
      let realCheckNodes = []
      let checkedNodes = this.$refs.tree5.getCheckedNodes()
      checkedNodes.forEach((v) => {
        if (!v.children) {
          realCheckNodes.push(v)
        }
      })
      // console.log(realCheckNodes.length)
      return realCheckNodes
    },
    handleRealCheck3() {
      let realCheckNodes = []
      let checkedNodes = this.$refs.tree3.getCheckedNodes()
      checkedNodes.forEach((v) => {
        if (!v.children) {
          realCheckNodes.push(v)
        }
      })
      // console.log(realCheckNodes.length)
      return realCheckNodes
    },
    handleTime(v) {
      if (v === 1) {
        this.beginTime = moment(new Date()).subtract(1, 'months').format('YYYY-MM-DD')
        this.endTime = moment(new Date()).format('YYYY-MM-DD')
      } else if (v === 2) {
        this.beginTime = moment(new Date()).subtract(3, 'months').format('YYYY-MM-DD')
        this.endTime = moment(new Date()).format('YYYY-MM-DD')
      } else if (v === 3) {
        this.beginTime = moment(new Date()).subtract(6, 'months').format('YYYY-MM-DD')
        this.endTime = moment(new Date()).format('YYYY-MM-DD')
      } else if (v === 4) {
        this.beginTime = moment(new Date()).subtract(12, 'months').format('YYYY-MM-DD')
        this.endTime = moment(new Date()).format('YYYY-MM-DD')
      }
    },
    handleTong() {
      this.excelViewUrl = ''
      this.urlExel = ''
      if (this.radio === 5) {
        this.beginTime = this.datetimerange[0].slice(0, 10)
        this.endTime = this.datetimerange[1].slice(0, 10)
      } else if (this.radio === 1) {
        this.beginTime = moment(new Date()).subtract(1, 'months').format('YYYY-MM-DD')
        this.endTime = moment(new Date()).format('YYYY-MM-DD')
      }
      if (this.type === '1') {
        if (
          this.$refs.tree.getCheckedNodes() &&
          this.handleRealCheck1().length > 0 &&
          this.handleRealCheck1().length <= 5
        ) {
          let stockId = this.handleRealCheck1()
            .map((v) => v.id)
            .join(',')
          this.getAxiosList(stockId, this.filterType)
        } else if (this.$refs.tree.getCheckedNodes() && this.handleRealCheck1().length > 5) {
          let stockId = this.handleRealCheck1()
            .map((v) => v.id)
            .join(',')
          this.getAxiosList(stockId, this.filterType)
        } else if (this.$refs.tree.getCheckedNodes() && this.handleRealCheck1().length === 0) {
          this.$message({
            type: 'error',
            showClose: true,
            message: '请选择仓库！！'
          })
        }
      } else if (this.type === '2') {
        if (!this.regionCheckedId.length) {
          this.$message({
            type: 'error',
            showClose: true,
            message: '请选择至少一个区县进行统计！！'
          })
          return
        }
        this.tempList = []
        this.$refs.tree4.filter()
        let stockList = []
        this.tableData.forEach((item) => {
          this.regionCheckedId.forEach((i) => {
            if (item.id === i) {
              stockList = stockList.concat(item.children)
            }
          })
        })
        let idList = []
        this.tempList.forEach((item) => {
          stockList.forEach((i) => {
            if (item.id === i.id) {
              idList.push(item.id)
            }
          })
        })
        this.getAxiosList(idList.join(','), this.filterType4)
      } else if (this.type === '3') {
        if (this.$refs.tree2.getCheckedNodes() && this.handleRealCheck2().length > 0) {
          let stockId = this.handleRealCheck2()
            .map((v) => v.id)
            .join(',')
          this.getAxiosList2(stockId, this.filterType2)
        } else if (this.$refs.tree2.getCheckedNodes() && this.handleRealCheck2().length === 0) {
          this.$message({
            type: 'error',
            showClose: true,
            message: '请选择仓库！！'
          })
        }
      } else if (this.type === '4') {
        this.dataLoading = true
        let stockId = this.handleRealCheck3()
          .map((v) => v.id)
          .join(',')
        let helpType = this.filterType3.join(',')
        let url =
          '/gtw/EIMS-management/resourceAgreement/stuffReverseExcel?stockIds=' + stockId + '&helpTypes=' + helpType
        Axios.get(url).then((r) => {
          if (r.data.code === 200 && r.data.data !== null) {
            this.urlExel = r.data.data
            this.dataLoading = false
          } else {
            this.$message({
              type: 'error',
              showClose: true,
              message: r.data.message
            })
            this.dataLoading = false
          }
          this.viewExelFile()
          this.treeVisible = false
          this.treeVisible2 = false
          this.treeVisible3 = false
          this.treeVisible4 = false
          this.treeVisible5 = false
        })
      } else if (this.type === '5') {
        if (this.$refs.tree5.getCheckedNodes() && this.handleRealCheck5().length > 0) {
          let stockId = this.handleRealCheck5()
            .map((v) => v.id)
            .join(',')
          this.getAxiosList5(stockId, this.filterType5)
        } else if (this.$refs.tree5.getCheckedNodes() && this.handleRealCheck5().length === 0) {
          this.$message({
            type: 'error',
            showClose: true,
            message: '请选择仓库！！'
          })
        }
      }
    },
    getAxiosList(stockId, helpTypes) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      this.dataLoading = true
      let url =
        '/gtw/EIMS-management/warehouse/stuffExcelExport?type=1&beginTime=' +
        this.beginTime +
        '&endTime=' +
        this.endTime +
        '&helpTypes=' +
        helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: JSON.stringify(stockId),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.urlExel = res.data.data
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: res.data.message
          })
        }
        this.dataLoading = false
        this.viewExelFile()
        this.treeVisible = false
        this.treeVisible2 = false
        this.treeVisible3 = false
        this.treeVisible4 = false
        this.treeVisible5 = false
      })
    },
    getAxiosList2(stockId, helpTypes) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      this.dataLoading = true
      let url =
        '/gtw/EIMS-management/warehouse/stuffExcelExport2?beginTime=' +
        this.beginTime +
        '&endTime=' +
        this.endTime +
        '&helpTypes=' +
        helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: JSON.stringify(stockId),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.urlExel = res.data.data
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: res.data.message
          })
        }
        this.dataLoading = false
        this.viewExelFile()
        this.treeVisible = false
        this.treeVisible2 = false
        this.treeVisible3 = false
        this.treeVisible4 = false
        this.treeVisible5 = false
      })
    },
    getAxiosList5(stockId, helpTypes) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      this.dataLoading = true
      let url = '/gtw/EIMS-management/warehouse/stuffExcelExport3?helpTypes=' + helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: JSON.stringify(stockId),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.urlExel = res.data.data
        } else {
          this.$message({
            type: 'error',
            showClose: true,
            message: res.data.message
          })
        }
        this.dataLoading = false
        this.viewExelFile()
        this.treeVisible = false
        this.treeVisible2 = false
        this.treeVisible3 = false
        this.treeVisible4 = false
        this.treeVisible5 = false
      })
    },
    viewExelFile(key) {
      let routeData = this.$router.resolve({
        path: '/fileView',
        query: {
          fileUrl: this.urlExel
        }
      })
      this.excelViewUrl = routeData.href
      // console.log(routeData)
      if (key === 'newPage') {
        window.open(routeData.href, '_blank')
      }
    },
    handleExelFile() {
      window.location.href = this.urlExel
    },
    selectMain() {
      this.filterText = ''
      this.filterText2 = ''
      this.filterText3 = ''
      this.filterType = ''
      this.filterType2 = ''
      this.filterType3 = ''
      this.filterType4 = ''
      this.filterLevel = ''
      this.filterLevel2 = ''
      this.filterLevel3 = ''
      this.filterLevel4 = ''
      this.filterManage = ''
      this.filterManage2 = ''
      this.filterManage3 = ''
      this.filterManage4 = ''
      this.stockType = ''
      this.stockType2 = ''
      this.stockType3 = ''
      this.stockType4 = ''
      this.stockType5 = ''
      this.treeVisible = false
      this.treeVisible2 = false
      this.treeVisible3 = false
      this.treeVisible4 = false
      this.treeVisible5 = false
    },
    checkType() {
      this.loading = true
      setTimeout(() => {
        this.checked = false
        this.$refs.tree.setCheckedKeys([])
        this.$refs.tree.filter()
        this.loading = false
      }, 100)
    },
    checkType2() {
      this.loading = true
      setTimeout(() => {
        this.checked2 = false
        this.$refs.tree2.setCheckedKeys([])
        this.$refs.tree2.filter()
        this.loading = false
      }, 100)
    },
    checkType3() {
      this.loading = true
      setTimeout(() => {
        this.checked3 = false
        this.$refs.tree3.setCheckedKeys([])
        this.$refs.tree3.filter()
        this.loading = false
      }, 100)
    },
    checkType5() {
      this.loading = true
      setTimeout(() => {
        this.checked5 = false
        this.$refs.tree5.setCheckedKeys([])
        this.$refs.tree5.filter()
        this.loading = false
      }, 100)
    },
    handleCheckAllChange(v) {
      if (v) {
        this.$refs.tree.setCheckedNodes(this.tableData)
      } else {
        this.$refs.tree.setCheckedKeys([])
      }
    },
    handleCheckAllChange2(v) {
      if (v) {
        this.$refs.tree2.setCheckedNodes(this.tableData)
      } else {
        this.$refs.tree2.setCheckedKeys([])
      }
    },
    handleCheckAllChange3(v) {
      if (v) {
        this.tableData.forEach((item) => {
          this.itemChange(item.id, item.id)
        })
      } else {
        this.tableData.forEach((item) => {
          this.itemChange(item.id, null)
        })
      }
    },
    handleCheckAllChange4(v) {
      if (v) {
        this.$refs.tree3.setCheckedNodes(this.tableData)
      } else {
        this.$refs.tree3.setCheckedKeys([])
      }
    },
    handleCheckAllChange5(v) {
      if (v) {
        this.$refs.tree5.setCheckedNodes(this.tableData)
      } else {
        this.$refs.tree5.setCheckedKeys([])
      }
    }
  }
}
</script>
<style lang="scss">
/* @import url(); 引入css类 */
.materialWarehousePopper {
  width: 30%;
  height: 80%;
  top: 137px;
  overflow: scroll;
}
.mater {
  position: relative;
  overflow: hidden !important;
  .el-select__tags {
    top: 40%;
    flex-wrap: nowrap;
  }
  .mater_tree {
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid #d8d8d8;
    box-shadow: 0px 9px 10px 0px rgba(96, 107, 125, 0.1);
    border-radius: 10px;
    width: 700px;
    .main_tree {
      width: 100%;
      height: 820px;
      overflow: auto;
    }
    .el-tree-node:focus > .el-tree-node__content {
      color: #0093ff;
    }

    .mater_tree_item {
      padding: 5px 12px;
    }
  }
  .mater_body {
    width: 100%;
    height: 100%;
    margin-top: 20px;
  }
  .mater_exel {
    width: 100%;
    height: 40px;
    margin-bottom: 5px;
    line-height: 40px;
    display: flex;
    padding-left: 40px;
    .mater_radio {
      margin-left: 22px;
      // display: flex;
    }
    .num {
      width: 68px;
      height: 42px;
      background: #0093ff;
      border-radius: 6px;
      line-height: 12px;
      text-align: center;
      font-size: 16px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #fefefe;
      margin-left: 44px;
    }
  }
  .mater_img_title {
    position: absolute;
    text-align: center;
    .mater_text {
      color: #000;
      opacity: 0.5;
      font-size: 22px;
    }
    .mater_text_wait {
      position: absolute;
      top: 53%;
      left: 44%;
      color: #000;
      font-size: 22px;
      z-index: 2002;
    }
    .exel {
      width: 180px;
      height: 36px;
      // background: #0093ff;
      border-radius: 6px;
      line-height: 12px;
      text-align: center;
      font-size: 16px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #fefefe;
      margin: 0 auto;
    }
    .downloadBtn {
      width: 180px;
      height: 36px;
      border-radius: 6px;
      line-height: 36px;
      display: block;
      color: #0093ff;
      background: #e6f4ff;
      border: 1px solid #99d4ff;
      font-size: 16px;
      -webkit-appearance: none;
      text-align: center;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      outline: none;
      -webkit-transition: 0.1s;
      transition: 0.1s;
      font-weight: 500;
      -moz-user-select: none;
      -webkit-user-select: none;
      -ms-user-select: none;
      text-decoration: none;
      margin: 0 auto;

      &:hover {
        color: #fff;
        background-color: #0d9bf2;
        border-color: #0d9bf2;
      }
    }
    // a.disabled {
    //   pointer-events: none;
    //   filter: alpha(opacity=50); /*IE滤镜，透明度50%*/
    //   -moz-opacity: 0.5; /*Firefox私有，透明度50%*/
    //   opacity: 0.5; /*其他，透明度50%*/
    // }
  }
  .mater_img {
    width: 1500px;
    height: 750px;

    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
