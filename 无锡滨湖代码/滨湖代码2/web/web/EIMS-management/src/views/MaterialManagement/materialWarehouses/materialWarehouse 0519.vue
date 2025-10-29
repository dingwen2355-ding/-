<template>
  <div class="mater">
    <el-tabs v-model="type" @tab-click="handleClick">
      <el-tab-pane label="按仓库统计" name="1">
        <div class="mater_tree">
          <div style="display: flex">
            <el-input style="margin: 10px; width: calc(40%)" placeholder="关键字" v-model="filterText"> </el-input>
            <el-select
              @change="checkType"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterType"
              multiple
              collapse-tags
              placeholder="救援类型"
            >
              <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              @change="checkType"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterManage"
              clearable
              placeholder="管理类型"
            >
              <el-option v-for="item in manageList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              @change="checkType"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterLevel"
              clearable
              placeholder="仓库等级"
            >
              <el-option v-for="item in levelList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
          </div>
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
      </el-tab-pane>
      <el-tab-pane label="按仓库统计(明细)" name="3">
        <div class="mater_tree">
          <div style="display: flex">
            <el-input style="margin: 10px; width: calc(40%)" placeholder="关键字" v-model="filterText2"> </el-input>
            <el-select
              @change="checkType2"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterType2"
              multiple
              collapse-tags
              placeholder="救援类型"
            >
              <el-option v-for="item in typeList2" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              @change="checkType2"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterManage2"
              clearable
              placeholder="管理类型"
            >
              <el-option v-for="item in manageList2" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              @change="checkType2"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterLevel2"
              clearable
              placeholder="仓库等级"
            >
              <el-option v-for="item in levelList2" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
          </div>
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
      </el-tab-pane>
      <el-tab-pane label="按区县统计" name="2" v-if="wareHouseLevel.indexOf(userLevel) === -1">
        <div class="mater_tree" v-loading="loading">
          <div style="display: flex">
            <el-select
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterType4"
              multiple
              collapse-tags
              placeholder="救援类型"
            >
              <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterManage4"
              clearable
              placeholder="管理类型"
            >
              <el-option v-for="item in manageList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterLevel4"
              clearable
              placeholder="仓库等级"
            >
              <el-option v-for="item in levelList" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
          </div>
          <div class="main_tree">
            <div class="mater_tree_item" v-for="(item, index) in tableData" :key="index">
              <el-checkbox :true-label="item.id" :false-label="null" @change="itemChange(item.id, $event)">{{
                item.label
              }}</el-checkbox>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="物资储备统计" name="4" v-if="wareHouseLevel.indexOf(userLevel) === -1">
        <div class="mater_tree">
          <div style="display: flex">
            <el-input style="margin: 10px; width: calc(40%)" placeholder="关键字" v-model="filterText3"> </el-input>
            <el-select
              @change="checkType3"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterType3"
              multiple
              collapse-tags
              placeholder="救援类型"
            >
              <el-option v-for="item in typeList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              @change="checkType3"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterManage3"
              clearable
              placeholder="管理类型"
            >
              <el-option v-for="item in manageList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
            <el-select
              @change="checkType3"
              style="margin: 10px 5px 0px 0px; width: calc(40%)"
              v-model="filterLevel3"
              clearable
              placeholder="仓库等级"
            >
              <el-option v-for="item in levelList3" :value="item.id" :label="item.name" :key="item.id"> </el-option>
            </el-select>
          </div>
          <div class="main_tree">
            <el-tree
              :data="tableData"
              show-checkbox
              v-loading="loading"
              node-key="id"
              :filter-node-method="filterNode3"
              :props="defaultProps"
              @check-change="handleCheckChange"
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
      </el-tab-pane>
    </el-tabs>
    <div class="mater_body">
      <div class="mater_exel">
        <div>统计周期</div>
        <div class="mater_radio">
          <el-radio-group :disabled="type === '4'" v-model="radio" @change="handleTime">
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
      </div>
      <div class="mater_img_title">
        <div class="mater_text" v-if="urlExel && !dataLoading">已统计完成，请下载Excel台账</div>
        <div class="mater_text" v-if="dataLoading">正在统计，请稍后……</div>
        <div class="mater_img">
          <img
            v-if="urlExel == undefined || urlExel == null || urlExel == ''"
            src="../../assets/ExcelJingtai.png"
            alt=""
          />
          <img v-else src="../../assets/ExcelT.gif" alt="" />
        </div>
        <el-button
          type="primary"
          @click="viewExelFile"
          class="exel"
          :disabled="urlExel == undefined || urlExel == null || urlExel == ''"
        >
          Excel统计表预览
        </el-button>
        <el-button
          type="primary"
          @click="handleExelFile"
          class="exel"
          :disabled="urlExel == undefined || urlExel == null || urlExel == ''"
        >
          Excel统计表下载
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import moment from 'moment'
import Axios from 'axios'
export default {
  data() {
    return {
      type: '1',
      radio: 1,
      loading: true,
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: [configs.wareHouseLevel, '400'],
      datetimerange: [],
      tableData: [],
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
      filterLevel: '',
      filterLevel2: '',
      filterLevel3: '',
      filterLevel4: '',
      filterManage: '',
      filterManage2: '',
      filterManage3: '',
      filterManage4: '',
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
      showTag: false,
      dataLoading: false
    }
  },
  watch: {
    filterText(val) {
      this.checkType()
    },
    filterText2(val) {
      this.$refs.tree2.filter()
    },
    filterText3(val) {
      this.$refs.tree3.filter()
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
      // console.log(val, event)
      if (val === event) {
        this.regionCheckedId.push(val)
      } else {
        this.regionCheckedId.splice(this.regionCheckedId.indexOf(val), 1)
      }

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

      let typeKey4 = this.filterType4 === '' ? true : data.helpTypes.indexOf(this.filterType4) !== -1
      let manageKey4 = this.filterManage4 === '' ? true : data.beManagedType.indexOf(this.filterManage4) !== -1
      let levelKey4 = this.filterLevel4 === '' ? true : data.storageLevel.indexOf(this.filterLevel4) !== -1
      if (typeKey4 && manageKey4 && levelKey4) {
        this.tempList.push(data)
      }
      return textKey && typeKey && manageKey && levelKey
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
      return textKey && typeKey && manageKey && levelKey
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
      return textKey && typeKey && manageKey && levelKey
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
    handleCheckChange(data, checked, indeterminate) {},
    handleCheckChange2(data, checked, indeterminate) {},
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
        this.$refs.tree.filter()
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
        })
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
        '/gtw/EIMS-management/warehouse/stuffExcelExport?stockId=' +
        stockId +
        '&type=1&beginTime=' +
        this.beginTime +
        '&endTime=' +
        this.endTime +
        '&helpTypes=' +
        helpTypes
      Axios.get(url).then((res) => {
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
        '/gtw/EIMS-management/warehouse/stuffExcelExport2?stockId=' +
        stockId +
        '&beginTime=' +
        this.beginTime +
        '&endTime=' +
        this.endTime +
        '&helpTypes=' +
        helpTypes
      Axios.get(url).then((res) => {
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
      })
    },
    viewExelFile() {
      let routeData = this.$router.resolve({
        path: '/fileView',
        query: {
          fileUrl: this.urlExel
        }
      })
      window.open(routeData.href, '_blank')
    },
    handleExelFile() {
      window.location.href = this.urlExel
    },
    checkType() {
      this.loading = true
      setTimeout(() => {
        this.$refs.tree.filter()
        this.loading = false
      }, 100)
    },
    checkType2() {
      this.loading = true
      setTimeout(() => {
        this.$refs.tree2.filter()
        this.loading = false
      }, 100)
    },
    checkType3() {
      this.loading = true
      setTimeout(() => {
        this.$refs.tree3.filter()
        this.loading = false
      }, 100)
    }
  }
}
</script>
<style lang="scss">
/* @import url(); 引入css类 */
.mater {
  display: flex;
  position: relative;
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
  }
  .mater_exel {
    width: 100%;
    height: 40px;
    line-height: 40px;
    display: flex;
    padding-left: 40px;
    .mater_radio {
      margin-left: 22px;
      // display: flex;
    }
    .num {
      width: 68px;
      height: 36px;
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
    top: 255px;
    left: 60%;
    margin-left: -180px;
    text-align: center;
    .mater_text {
      color: #000;
      font-size: 22px;
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
    width: 406px;
    height: 299px;

    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
