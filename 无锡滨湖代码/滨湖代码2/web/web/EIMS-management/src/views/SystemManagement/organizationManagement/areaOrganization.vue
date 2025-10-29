<template>
  <div class="area jq-page-style01" id="area">
    <div class="add-panel" @click="addRow()">
      <img src="@/assets/add1.png" />
      <el-button type="text"> 新增组织 </el-button>
    </div>
    <div class="search-container">
      <el-select v-model="selectAreaType" size="medium" clearable placeholder="按组织类型筛选" style="width: 20%">
        <el-option v-for="item in precinctKinds" :key="item.key" :label="item.name" :value="item.key"> </el-option>
      </el-select>
      <el-input v-model="areaName" clearable placeholder="请输入组织名称或ID搜索" style="width: 20%"></el-input>
      <el-button type="primary" @click="refreshData" icon="el-icon-search" class="btnSearch">查询</el-button>
    </div>
    <div class="jq-table-wrap">
      <el-table
        ref="singleTable"
        stripe
        height="720"
        :data="precinctData"
        highlight-current-row
        @selection-change="handleSelectionChange"
      >
        <el-table-column
          show-overflow-tooltip
          prop="precinctKind"
          label="组织类型"
          :formatter="formatterPrecinctKind"
          sortable
          width="120"
        >
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="precinctName" label="组织名称" sortable width="150">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="precinctId" label="组织ID" sortable width="200"> </el-table-column>
        <el-table-column show-overflow-tooltip prop="upPrecinctId" label="父级组织ID" sortable width="200">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="precinctAddr" sortable label="详细地址" width="200">
        </el-table-column>
        <el-table-column
          show-overflow-tooltip
          prop="gpsType"
          label="坐标类型"
          :formatter="formatterGpsType"
          sortable
          width="150"
        >
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="gpsX" sortable label="经度" width="100"> </el-table-column>
        <el-table-column show-overflow-tooltip prop="gpsY" sortable label="纬度" width="100"> </el-table-column>
        <el-table-column show-overflow-tooltip prop="gpsZ" sortable label="高度" width="100"> </el-table-column>
        <el-table-column show-overflow-tooltip prop="regionXY" sortable label="组织坐标" width="150"> </el-table-column>
        <el-table-column width="120" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="editRow(scope.row)">编辑</el-button>
            <el-button type="text" @click="deleteRow(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- <span class="jq-table-opt">共 <span
        class="jq-table-tnum">{{ dataLength }}</span> 条数据   |  最近更新时间：{{ updateTime }}</span> -->
      <el-pagination
        @current-change="handleCurrentChangePage"
        layout="prev, pager, next,jumper"
        :total="dataLength"
        :page-size="pageSize"
        :current-page.sync="currentPage"
      >
      </el-pagination>
    </div>
    <el-dialog
      :visible.sync="addDeviceFormVisible"
      :title="title"
      width="500px"
      :modal-append-to-body="false"
      :before-close="cancelRegionalForm"
      :close-on-click-modal="false"
    >
      <el-form ref="basicAttributesForm" :rules="rules" :model="basicAttributesForm" label-width="95px">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="基本属性" name="first">
            <div style="margin-left: 6px">
              <el-form-item label="新增方式 :" style="margin-left: 10px" v-if="!addOrEdit">
                <div style="float: left">
                  <el-radio label="1" v-model="addType">单个新增</el-radio>
                  <el-radio label="2" v-model="addType">连续新增</el-radio>
                </div>
              </el-form-item>
              <el-form-item label="组织ID :" v-if="addOrEdit">
                <el-input v-model="basicAttributesForm.precinct_id" :disabled="addOrEdit"></el-input>
              </el-form-item>
              <el-form-item label="父级组织 :" prop="up_precinct_name" v-if="addDeviceFormVisible && addOrEdit">
                <div class="znv-search-input-ztree" @mouseleave="leavePrec">
                  <div @click="clickPrec">
                    <el-input v-model="up_precinct_name"></el-input>
                  </div>
                  <div class="znv-form-ztree-wrap" v-show="isPrecintTreeShow">
                    <el-scrollbar class="znv-el-scrollbar-y">
                      <ul id="areaTree2" class="lib-ztree ztree ztree01"></ul>
                    </el-scrollbar>
                  </div>
                </div>
              </el-form-item>
              <el-form-item label="组织类型 :" prop="precinct_kind">
                <el-select v-model="basicAttributesForm.precinct_kind">
                  <el-option
                    v-for="item in precinctKindList"
                    :key="item.precinctKind"
                    :label="item.name"
                    :value="item.precinctKind"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="组织名称 :" prop="precinct_name">
                <el-input v-model="basicAttributesForm.precinct_name" placeholder="请输入组织名称"></el-input>
              </el-form-item>
              <el-form-item label="省/直辖市 :" prop="province">
                <el-select v-model="basicAttributesForm.province">
                  <el-option
                    v-for="item in provinceList"
                    :key="item.areaCode"
                    :label="item.areaName"
                    :value="item.areaCode"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="市区 :" prop="city">
                <el-select v-model="basicAttributesForm.city">
                  <el-option
                    v-for="item in cityList"
                    :key="item.areaCode"
                    :label="item.areaName"
                    :value="item.areaCode"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="县区 :" prop="countyArea">
                <el-select v-model="basicAttributesForm.countyArea">
                  <el-option
                    v-for="item in countyAreaList"
                    :key="item.areaCode"
                    :label="item.areaName"
                    :value="item.areaCode"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="详细地址 :" prop="detailAddress">
                <el-input v-model="basicAttributesForm.detailAddress" placeholder="请输入组织详细地址"></el-input>
              </el-form-item>
              <el-form-item label="排序号 :" prop="orderNum">
                <el-input v-model.number="basicAttributesForm.orderNum" placeholder="请输入排序号"></el-input>
              </el-form-item>
            </div>
          </el-tab-pane>
          <!-- <el-tab-pane label="扩展属性" name="second">
            <div style="margin-left: 6px;">
              <el-form-item label="坐标类型 :">
                <el-select v-model="basicAttributesForm.gpsType">
                  <el-option
                    v-for="item in coordinateList"
                    :key="item.gpsType"
                    :label="item.name"
                    :value="item.gpsType">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="组织坐标 :">
                <el-input v-model="basicAttributesForm.regionXY"></el-input>
              </el-form-item>
              <el-form-item label="经度 :" prop="gpsX">
                <el-input v-model="basicAttributesForm.gpsX"></el-input>
              </el-form-item>
              <el-form-item label="纬度 :" prop="gpsY">
                <el-input v-model="basicAttributesForm.gpsY"></el-input>
              </el-form-item>
              <el-form-item label="高度 :" prop="gpsZ">
                <el-input v-model="basicAttributesForm.gpsZ"></el-input>
              </el-form-item>
            </div>
          </el-tab-pane> -->
        </el-tabs>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button class="btnOK" type="primary" @click="saveRegional">确定</el-button>
        <el-button class="btnCancel" plain type="primary" @click="cancelRegionalForm">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import $ from 'jquery'
import Axios from 'axios'
import { checkGPSXY } from '../../../common/js/checkGPSXY.js'
import { checkNum } from '../../../common/js/checkNum.js'
// import {checkName} from '../../common/js/checkName.js'
import 'ztree'

export default {
  name: 'Area',
  props: {
    regionalTreePrecinctId: String
  },
  data() {
    return {
      addType: '1',
      organizationKind: '', // 父节点组织类型
      currentPrecinctId: '', // 当前节点的区域ID
      closeOnClickModal: false,
      allData: [], // 全部设备数据
      selectedData: [], // 关联到该区域的设备
      addPrivilege: [], // 新增权限
      removePrivilege: [], // 删除权限
      precinctData: [],
      up_precinct_name: '',
      isPrecintTreeShow: false,
      basicAttributesForm: {
        precinct_id: '',
        precinct_name: '',
        province: '',
        city: '',
        countyArea: '',
        precinct_kind: '',
        detailAddress: '',
        parent_id: '',
        gpsType: '',
        regionXY: '',
        gpsX: '',
        gpsY: '',
        gpsZ: '',
        orderNum: ''
      },
      // extendAttributesForm: {
      //   'gpsType': '',
      //   'regionXY': '',
      //   'gpsX': '',
      //   'gpsY': '',
      //   'gpsZ': ''
      // },
      provinceList: [],
      coordinateList: [], // 坐标类型
      cityList: [],
      countyAreaList: [],
      precinctKindList: [],
      addOrEdit: false,
      loading: true,
      title: '',
      activeName: 'first',
      addDeviceFormVisible: false,
      selectDeviceFormVisible: false,
      dialogCenter: true,
      upOrInsert: null,
      selectionIds: [],
      precinctKinds: [],
      rules: {
        // addType: [
        //   {required: true, message: "新增方式不能为空", trigger: "blur"}
        // ],
        precinct_kind: [{ required: true, message: '组织类型不能为空', trigger: 'change' }],
        precinct_name: [
          { required: true, message: '组织名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '长度不能超过20个字符', trigger: 'blur' }
          // {validator: checkName, message: '', trigger: 'blur'}
        ],
        province: [{ required: true, message: '省名称不能为空', trigger: 'change' }],
        city: [{ required: true, message: '市名称不能为空', trigger: 'change' }],
        countyArea: [{ required: true, message: '县名称不能为空', trigger: 'change' }],
        gpsX: [
          { required: false, message: '', trigger: 'blur' },
          { validator: checkGPSXY, trigger: 'blur' }
        ],
        gpsY: [
          { required: false, message: '', trigger: 'blur' },
          { validator: checkGPSXY, trigger: 'blur' }
        ],
        gpsZ: [
          { required: false, message: '请输入正确的高度', trigger: 'blur' },
          { validator: checkNum, trigger: 'blur' }
        ],
        detailAddress: [{ min: 0, max: 50, message: '长度不能超过50个字符', trigger: 'blur' }],
        orderNum: [
          { required: true, message: '排序号不能为空', trigger: 'blur' },
          { type: 'number', message: '请输入数字', trigger: 'blur' }
        ]
      },
      currentPage: 1,
      pageSize: 9,
      dataLength: 0,
      areaTypes: [],
      selectAreaType: '',
      areaName: '',
      date: '',
      updateTime: new Date().toLocaleString(),
      defaultValue: true,
      authorizeShow: false,
      authorizeShow1: false,
      parentAreaCode: '', // 父节点区域码
      isAreaVisible: false
    }
  },
  watch: {
    addDeviceFormVisible: function (val) {
      if (val && this.addOrEdit) {
        this.initTree()
      }
    },
    watchProvince(val, oldVal) {
      if (this.upOrInsert === 'insert') {
        if (val !== oldVal) {
          // this.basicAttributesForm.city = '';
          // this.basicAttributesForm.countyArea = '';
          this.queryRegionalBase('city')
          this.defaultValue = false
        }
      }
    },
    watchCity(val, oldVal) {
      if (this.upOrInsert === 'insert') {
        if (val !== oldVal) {
          // this.basicAttributesForm.countyArea = '';
          this.queryRegionalBase('countyArea')
        }
      }
    },
    regionalTreePrecinctId() {
      let self = this
      self.currentPage = 1
      self.$nextTick(function () {
        self.queryArea()
        self.areaName = ''
        self.selectAreaType = ''
      })
    }
  },
  computed: {
    watchProvince() {
      return this.basicAttributesForm.province
    },
    watchCity() {
      return this.basicAttributesForm.city
    },
    precinctId: function () {
      return this.$parent.precinctId
    }
  },

  mounted() {
    // this.queryPrecinctKind();
    this.queryPrecinctKindList()
    // this.queryPrecinctDict("0");
    // this.refreshData(); // this.queryArea();
    // this.queryDevice();
    // this.queryCoordinateType();
  },
  methods: {
    leavePrec: function () {
      this.isPrecintTreeShow = false
    },
    clickPrec: function () {
      this.isPrecintTreeShow = !this.isPrecintTreeShow
    },
    initTree() {
      let self = this
      let pid = self.basicAttributesForm.parent_id
      if (pid === 0) {
        self.up_precinct_name = ''
      }
      let setting = {
        view: {
          fontCss: function (treeId, treeNode) {
            return treeNode.highlight
              ? {
                  color: '#1E90FF',
                  'font-weight': 'bold'
                }
              : {
                  color: '#84868a',
                  'font-weight': 'normal'
                }
          },
          // showIcon: true,
          showLine: false,
          selectedMulti: false,
          addDiyDom: this.addDiyDom
        },
        edit: {
          enable: false
        },
        data: {
          key: {
            name: 'name',
            iconSkin: 'iconCls'
          },
          simpleData: {
            enable: true,
            idKey: 'id',
            pIdKey: 'pId',
            rootPId: 0
          }
        },
        callback: {
          onClick: this.onClick
        }
      }
      Axios.get('/gtw/EIMS-management/regional')
        .then(function (response) {
          // loading.close();
          // self.menuIsShow = true;
          self.dataIs = false
          let data = response.data.data.precinct
          let nodes = []
          let j = 0
          for (let i = 0, len = data.length; i < len; i++) {
            let item = data[i]
            nodes[j] = item
            nodes[j].pId = '' + item.upPrecinctId
            nodes[j].id = '' + item.precinctId
            nodes[j].name = item.precinctName
            j++
          }
          self.treeObj = $.fn.zTree.init($('#areaTree2'), setting, nodes)
          // 初始化选中根节点，每次新建后仍然选中当前设备树节点
          nodes = self.treeObj.getNodes()
          let cnode = self.treeObj.getNodeByParam('id', self.basicAttributesForm.precinct_id)
          if (cnode) {
            self.treeObj.hideNode(cnode)
          }
          var node = self.treeObj.getNodeByParam('id', self.basicAttributesForm.parent_id)
          if (node) {
            self.treeObj.selectNode(node)
            self.treeObj.expandNode(node, true, false, false)
            self.onClick(null, null, node)
          } else {
            let pnode = nodes[0]
            self.treeObj.selectNode(pnode)
            self.precinctId = pnode.precinctId
          }
        })
        .catch(function (error) {
          // console.log(error)
        })
    },
    addDiyDom(treeId, treeNode) {
      let spaceWidth = 10
      let switchObj = $('#' + treeNode.tId + '_switch')
      let icoObj = $('#' + treeNode.tId + '_ico')
      switchObj.remove()
      icoObj.before(switchObj)
      if (treeNode.level > 0) {
        let spaceStr = "<span style='display: inline-block;width:" + spaceWidth * treeNode.level * 2 + "px'></span>"
        switchObj.before(spaceStr)
      }
    },
    onClick: function (event, treeId, node) {
      this.up_precinct_name = node.name
      this.basicAttributesForm.parent_id = node.id
    },
    clearTreeSelect() {
      this.$emit('refreshClearTree')
      this.regionalTreePrecinctId = ''
    },
    queryDevice() {
      this.allData = []
      let self = this
      Axios.get('/gtw/EIMS-management/devices/querydevices?cmpId=1&deviceKind=0&precinctId=-1') // 0包含物联视图平台设备，获取全部没有授权的设备
        .then((res) => {
          let devices = res.data.data
          devices.forEach(function (device, index) {
            self.allData.push({
              label: device.deviceName,
              key: device.deviceId
            })
          })
        })
    },
    // 切换页码
    handleCurrentChangePage: function (val) {
      this.currentPage = val
      this.refreshData()
    },
    // 选中列表行
    handleSelectionChange: function (rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.precinctId)
      })
    },
    handleClick(tab, event) {},
    // 查询省、市、县列表
    queryRegionalBase: function (areaType) {
      let self = this
      if (!areaType) {
        let url = '/gtw/EIMS-management/regionalbase'
        Axios.get(url).then(function (res) {
          if (res.data.code === 200) {
            let data = res.data.data
            self.provinceList = data
          }
        })
      } else if (areaType === 'city' && self.basicAttributesForm.province !== '') {
        let url = '/gtw/EIMS-management/regionalbase?province=' + self.basicAttributesForm.province
        Axios.get(url).then(function (res) {
          if (res.data.code === 200) {
            let data = res.data.data
            self.cityList = data
          }
        })
      } else if (areaType === 'countyArea' && self.basicAttributesForm.city !== '') {
        let url = '/gtw/EIMS-management/regionalbase?city=' + self.basicAttributesForm.city
        Axios.get(url)
          .then(function (res) {
            if (res.data.code === 200) {
              let data = res.data.data
              self.countyAreaList = data
            }
          })
          .catch(function (error) {
            // console.log(error)
          })
      }
    },
    queryCoordinateType: function () {
      let url = '/gtw/EIMS-management/GPSTypeDict'
      Axios.get(url).then((res) => {
        let data = res.data.data
        let ss = this.strObjToArr1(data)
        this.coordinateList = ss
      })
    },
    strObjToArr1: function (data) {
      let arr = []
      for (var i in data) {
        // eslint-disable-next-line no-prototype-builtins
        if (data.hasOwnProperty(i) === true) {
          let obj = Object.create(null)
          obj.gpsType = Number(i)
          obj.name = data[i]
          arr.push(obj)
        }
      }
      return arr
    },
    // 取消新增（编辑）列表
    cancelRegionalForm: function () {
      this.addDeviceFormVisible = false
      if (this.$refs.basicAttributesForm) this.$refs.basicAttributesForm.clearValidate()
      this.basicAttributesForm = {
        precinct_id: '',
        precinct_name: '',
        province: '',
        city: '',
        countyArea: '',
        precinct_kind: '',
        detailAddress: '',
        parent_id: '',
        gpsType: '',
        regionXY: '',
        gpsX: '',
        gpsY: '',
        gpsZ: '',
        orderNum: ''
      }
      // this.extendAttributesForm = {
      //   'gpsType': '',
      //   'regionXY': '',
      //   'gpsX': '',
      //   'gpsY': '',
      //   'gpsZ': ''
      // }
      this.defaultValue = true
    },
    // 查询区域类型，加载到小区，下面得数据不要了
    queryPrecinctKindList: function () {
      let self = this
      let url = '/gtw/EIMS-management/regional/queryPrecinctKind'
      let result = []
      Axios.get(url).then(function (res) {
        if (res.status === 200) {
          let data = res.data.data
          data.forEach(function (item, index) {
            if (
              item.precinctKind == 100 ||
              item.precinctKind == 200 ||
              item.precinctKind == 201 ||
              item.precinctKind == 202 ||
              item.precinctKind == 203 ||
              item.precinctKind == 300 ||
              item.precinctKind == 301 ||
              item.precinctKind == 400 ||
              item.precinctKind == 600 ||
              item.precinctKind == 601 ||
              item.precinctKind == 210 ||
              item.precinctKind == 204 ||
              item.precinctKind == 101
            ) {
              result.push(item)
            }
          })
          self.precinctKindList = result
          self.precinctKinds = self.strObjToArr(result)
          self.precinctKinds.unshift({ key: '-1', name: '全部' })
        } else {
          // console.log(res.message)
        }
      })
    },
    strObjToArr: function (data) {
      let arr = []
      for (var i in data) {
        // eslint-disable-next-line no-prototype-builtins
        if (data.hasOwnProperty(i) === true) {
          // if (Object.prototype.hasOwnProperty.call(data, i) === true) {
          let obj = Object.create(null)
          obj.key = data[i].precinctKind
          obj.name = data[i].name
          arr.push(obj)
        }
      }
      return arr
    },
    queryArea: function () {
      let self = this
      self.precinctData = []
      let url =
        '/gtw/EIMS-management/regional?pageNum=' +
        this.currentPage +
        '&pageSize=' +
        this.pageSize +
        '&precinctId=' +
        this.regionalTreePrecinctId // 查本区域及其子区域
      Axios.get(url)
        .then(function (res) {
          if (res.data != null) {
            if (res.status === 200 || res.data.code === 200) {
              self.dataLength = res.data.data.total
              self.precinctData = res.data.data.list
              let time = new Date()
              self.updateTime = time.toLocaleString()
              //  self.updateTime = formatDate(new Date(), 'YYYY-MM-dd HH:mm');
              self.loading = false
            }
          }
        })
        .catch((err) => {
          // console.log(err)
        })
    },
    addRow: function () {
      if (this.$refs.basicAttributesForm) this.$refs.basicAttributesForm.clearValidate()
      this.title = '新增组织信息'
      this.upOrInsert = 'insert'
      this.addDeviceFormVisible = true
      this.addOrEdit = false
      this.isAreaVisible = false
      this.addType = '1' // 默认是单个新增
      this.queryRegionalBase()
      this.queryPrecinctKindList() // 获取全部组织类型
      // 当不是根节点时，组织区域应该与父节点区域保持一致，筛选组织类型
      let self = this
      if (this.regionalTreePrecinctId != '' && this.regionalTreePrecinctId != null) {
        this.isAreaVisible = true
        var url = '/gtw/EIMS-management/regional?precinctId=' + this.regionalTreePrecinctId
        Axios.get(url)
          .then(function (response) {
            self.organizationKind = response.data.data.precinct[0].precinctKind
            // let result = [];
            // let indexEqual = 8;// 到楼栋，注意这里写死了，后台更改则跟着修改
            // self.precinctKindList.forEach(
            //   function (item, index) {
            //     if (self.organizationKind == item.precinctKind) {
            //       indexEqual = index;
            //     }
            //     if (index >= indexEqual) {
            //       result.push(item);
            //     }
            //   }
            // );
            // self.precinctKindList = result;
            // 区域码与父区域保持一致
            self.parentAreaCode = response.data.data.precinct[0].areaCode
            self.basicAttributesForm.countyArea = self.parentAreaCode
            self.basicAttributesForm.city = self.parentAreaCode.slice(0, 4) + '00'
            self.basicAttributesForm.province = self.parentAreaCode.slice(0, 2) + '0000'
          })
          .catch(function (error) {
            // console.log(error)
          })
      }
    },
    editRow: function (row) {
      let self = this
      if (this.$refs.basicAttributesForm) this.$refs.basicAttributesForm.clearValidate()
      this.title = '编辑组织信息'
      this.upOrInsert = 'up'
      this.addDeviceFormVisible = true
      this.addOrEdit = true
      this.isAreaVisible = true
      // console.log('区域码：' + row.areaCode)
      this.basicAttributesForm.areaCode = row.areaCode
      this.basicAttributesForm.precinct_id = row.precinctId
      this.basicAttributesForm.precinct_name = row.precinctName
      this.basicAttributesForm.precinct_kind = row.precinctKind
      this.basicAttributesForm.detailAddress = row.precinctAddr // detailAddress;
      this.basicAttributesForm.parent_id = row.upPrecinctId
      this.basicAttributesForm.gpsX = row.gpsX
      this.basicAttributesForm.gpsY = row.gpsY
      this.basicAttributesForm.gpsZ = row.gpsZ
      this.basicAttributesForm.regionXY = row.regionXY
      this.basicAttributesForm.gpsType = row.gpsType
      this.basicAttributesForm.province =
        (row.areaCode != null && row.areaCode.length > 2 ? row.areaCode.slice(0, 2) : '') + '0000'
      Axios.get('/gtw/EIMS-management/regionalbase')
        .then(function (res) {
          if (res.data.code === 200) {
            self.provinceList = res.data.data
            Axios.get('/gtw/EIMS-management/regionalbase?province=' + self.basicAttributesForm.province).then(function (
              res
            ) {
              if (res.data.code === 200) {
                self.cityList = res.data.data
                self.basicAttributesForm.city = row.areaCode.slice(0, 4) + '00'
                Axios.get('/gtw/EIMS-management/regionalbase?city=' + self.basicAttributesForm.city).then(function (
                  res
                ) {
                  if (res.data.code === 200) {
                    self.countyAreaList = res.data.data
                    self.basicAttributesForm.countyArea = row.areaCode
                  }
                })
              }
            })
          }
        })
        .catch(function (error) {
          // console.log(error)
        })
    },
    // 保存新增（编辑）列表
    saveRegional: function () {
      let self = this
      this.addType = self.addType
      if (self.upOrInsert == 'up') {
        self.$refs.basicAttributesForm.validate(function (valid) {
          if (valid) {
            let insertRegional = {}
            // insertRegional.gpsX = Number(self.basicAttributesForm.gpsX);
            insertRegional.gpsX = self.basicAttributesForm.gpsX
            insertRegional.gpsY = self.basicAttributesForm.gpsY
            insertRegional.gpsZ = self.basicAttributesForm.gpsZ
            insertRegional.gpsType = self.basicAttributesForm.gpsType
            insertRegional.regionXY = self.basicAttributesForm.regionXY
            insertRegional.precinctName = self.basicAttributesForm.precinct_name
            insertRegional.precinctKind = Number(self.basicAttributesForm.precinct_kind)
            insertRegional.areaCode =
              self.basicAttributesForm.countyArea || self.basicAttributesForm.city || self.basicAttributesForm.province
            insertRegional.precinctAddr = self.basicAttributesForm.detailAddress
            insertRegional.upPrecinctId = self.basicAttributesForm.parent_id
            insertRegional.precinctId = self.basicAttributesForm.precinct_id
            insertRegional.orderNum = self.basicAttributesForm.orderNum
            let url = '/gtw/EIMS-management/regional'
            Axios.put(url, insertRegional)
              .then(function (res) {
                self.cancelRegionalForm()
                if (res.data.code === 201 || res.data.code === 200) {
                  self.$emit('refreshTree')
                  self.$message({
                    type: 'success',
                    showClose: true,
                    message: '编辑成功'
                  })
                } else {
                  self.$message({
                    type: 'error',
                    showClose: true,
                    message: res.data.message
                  })
                }
                // self.queryArea();
                self.refreshData()
              })
              .catch(function (error) {
                let message = ''
                if (
                  (error.response != null || error.response != undefined) &&
                  (error.response.data != null || error.response != undefined)
                ) {
                  message = error.response.data.message
                }
                self.$message({
                  type: 'error',
                  showClose: true,
                  message: '编辑失败:' + message
                })
                self.cancelRegionalForm()
                // console.log(error)
              })
          }
        })
      } else if (self.upOrInsert == 'insert') {
        self.$refs.basicAttributesForm.validate(function (valid) {
          if (valid) {
            let insertRegional = {}
            insertRegional.gpsX = self.basicAttributesForm.gpsX
            insertRegional.gpsY = self.basicAttributesForm.gpsY
            insertRegional.gpsZ = self.basicAttributesForm.gpsZ
            insertRegional.gpsType = self.basicAttributesForm.gpsType
            insertRegional.regionXY = self.basicAttributesForm.regionXY
            insertRegional.precinctName = self.basicAttributesForm.precinct_name
            insertRegional.precinctKind = Number(self.basicAttributesForm.precinct_kind)
            insertRegional.areaCode = self.defaultValue
              ? ''
              : self.basicAttributesForm.countyArea ||
                self.basicAttributesForm.city ||
                self.basicAttributesForm.province
            insertRegional.precinctAddr = self.basicAttributesForm.detailAddress
            insertRegional.upPrecinctId = self.regionalTreePrecinctId
            insertRegional.orderNum = self.basicAttributesForm.orderNum
            let url = '/gtw/EIMS-management/regional'
            Axios.post(url, insertRegional)
              .then(function (res) {
                //  self.cancelRegionalForm();
                if (res.status === 200 && res.data.code === 200) {
                  if (self.addType == 2 && self.regionalTreePrecinctId == '') {
                    self.$emit('refreshClearTree') // 如果连续新增时，第一次是在根目录下新建的，下一次也默认在根目录下新增
                    self.regionalTreePrecinctId = ''
                  } else {
                    self.$emit('refreshTree') // 更新设备树
                  }
                  self.$message({
                    type: 'success',
                    showClose: true,
                    message: '保存成功'
                  })
                  if (self.addType == '1') {
                    self.cancelRegionalForm()
                  }
                } else {
                  self.$message({
                    type: 'error',
                    showClose: true,
                    message: res.data.message
                  })
                }
                // self.queryArea();
                self.refreshData()
                // self.addRow();
              })
              .catch(function (error) {
                let message = ''
                if (
                  (error.response != null || error.response != undefined) &&
                  (error.response.data != null || error.response != undefined)
                ) {
                  message = error.response.data.message
                }
                self.$message({
                  type: 'error',
                  showClose: true,
                  message: '保存失败:' + message
                })
                self.cancelRegionalForm()
                // console.log(error)
              })
          }
        })
      }
    },
    deleteRow: function (row) {
      let self = this
      let url = '/gtw/EIMS-management/regional?id=' + row.precinctId
      self
        .$confirm('您确定要删除该组织?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        .then(function () {
          Axios.delete(url)
            .then(function (res) {
              if (res.data.code == 204 || res.data.code == 200) {
                self.$emit('refreshTree')
                self.$message({
                  type: 'success',
                  showClose: true,
                  message: '删除成功'
                })
              } else {
                self.$message({
                  type: 'error',
                  showClose: true,
                  message: res.data.message
                })
              }
              // self.queryArea();
              self.refreshData()
            })
            .catch(function (error) {
              self.$message({
                type: 'error',
                showClose: true,
                message: '删除失败 '
              })
              // console.log(error)
            })
        })
    },
    formatterPrecinctKind: function (row) {
      const index = this.precinctKinds.findIndex((precinctKind) => precinctKind.key == row.precinctKind)
      return index == -1 ? '' : this.precinctKinds[index].name
    },
    formatterGpsType(row) {
      let name = ''
      this.coordinateList.forEach(function (item, index) {
        if (item.gpsType == row.gpsType) {
          name = item.name
        }
      })
      return name
    },
    // 区域查询
    refreshData() {
      let self = this
      let precinctKind = self.selectAreaType === '-1' ? '' : self.selectAreaType
      let precinctName = self.areaName
      // 不支持特殊字符查询
      /*        if (precinctName != null && precinctName != undefined && precinctName.trim() != "") {
                let specificReg = new RegExp("[`~!#$^&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？%+_]");
                if (specificReg.test(precinctName)) {
                  self.$message({
                    type: "error",
                    showClose: true,
                    message: "不支持特殊字符查询 ",
                    onClose: function () {
                    }
                  });
                  return;
                };
              }; */
      let url =
        '/gtw/EIMS-management/regional?pageNum=' +
        self.currentPage +
        '&pageSize=' +
        self.pageSize +
        '&precinctKind=' +
        precinctKind +
        '&precinctName=' +
        precinctName.trim() +
        '&precinctId=' +
        self.regionalTreePrecinctId
      Axios.get(url)
        .then(function (res) {
          if (res.status === 200 || res.data.code === 200) {
            self.dataLength = res.data.data.total
            self.precinctData = res.data.data.list
            let time = new Date()
            self.updateTime = time.toLocaleString()
            //  self.updateTime = formatDate(new Date(), 'YYYY-MM-dd HH:mm');
          }
        })
        .catch(function (error) {
          // console.log(error)
        })
    },
    addParentOrg() {
      this.clearTreeSelect()
      this.addRow()
    }
  }
}
</script>
<style lang='scss'>
.area {
  width: 100%;
  height: 100%;
  .znv-search-input-ztree {
    position: relative;

    .znv-form-ztree-wrap {
      height: 300px;
      border: 1px solid #467fff;
      background-color: #fff;
      position: absolute;
      left: 0;
      top: 36px;
      width: 100%;
      z-index: 100;
    }
  }

  .panel {
    float: left;
    height: 100%;
    width: 100%;

    .opr-row {
      line-height: 3rem;
      height: 3rem;
      float: right;
      position: absolute;
      right: 5px;
      margin-top: -38px;

      .add-btn {
        cursor: pointer;
        font-size: 16px;

        img {
          position: relative;
          top: 2px;
        }
      }
    }

    .el-icon-search {
      margin-top: -2px;
    }
  }

  /*    .el-button--primary {
        width: 60px;
        height: 40px;
      }*/
  .btnOK {
    width: 100px;
    height: 32px;
    line-height: 8px;
  }

  .btnCancel {
    width: 102px;
    height: 34px;
    line-height: 8px;
    background: rgba(255, 255, 255, 1);
    border: 1px solid rgba(70, 127, 255, 1);
  }

  .el-transfer {
    height: 500px;

    .el-button--primary {
      width: 60px;
      height: 40px;
    }
  }

  .el-transfer-panel {
    width: 300px;
  }
}

/*.el-transfer-panel {*/
/*width: 295px;*/
/*}*/

.search-container {
  margin-bottom: 34px;

  .query-result-info {
    font-family: 'Microsoft YaHei';
    font-size: 14px;
    line-height: 50px;

    .precinct-num {
      color: #d10720;
    }
  }

  .add-panel {
    float: right;
    cursor: pointer;
    color: #467fff;
    font-size: 16px;
  }
}

.el-tooltip__popper.is-dark {
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
}
</style>
