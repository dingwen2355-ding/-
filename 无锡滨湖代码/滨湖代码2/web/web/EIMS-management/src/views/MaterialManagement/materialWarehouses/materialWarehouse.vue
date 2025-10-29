<template>
  <div class="mater">
    <div v-loading="loading" style="margin-top: 20px; display: flex">
      <el-select
        @change="selectMain"
        style="margin: 10px 5px 0px 0px; width: 149px"
        v-model="type"
        placeholder="统计类型"
      >
        <el-option value="1" label="出入库汇总统计"></el-option>
        <el-option value="2" label="出入库明细统计"></el-option>
        <el-option value="3" label="出入库区县统计"></el-option>
        <el-option value="4" label="物资储备统计"></el-option>
        <el-option value="5" label="物资明细统计"></el-option>
        <el-option value="6" label="物资救援类型统计"></el-option>
        <el-option value="7" label="物资管理类型统计"></el-option>
        <el-option value="8" label="物资类型统计"></el-option>
        <el-option value="9" label="物资名录分类统计"></el-option>
      </el-select>

      <el-select
        style="margin: 10px 5px 0px 0px; width: 149px"
        v-model="filterType"
        multiple
        collapse-tags
        value-key="id"
        placeholder="救援类型"
      >
        <el-option v-for="item in typeList" :value="item" :label="item.name" :key="item.id"> </el-option>
      </el-select>

      <el-select
        v-if="type === '3'"
        style="margin: 10px 5px 0px 0px; width: 149px"
        v-model="filterType2"
        multiple
        collapse-tags
        placeholder="选择区县"
      >
        <el-option label="全部" value="全部"></el-option>
        <div v-if="!filterType2.includes('全部')">
          <el-option v-for="item in regionList" :value="item.id" :label="item.name" :key="item.id"></el-option>
        </div>
      </el-select>

      <el-button
        v-if="type !== '3'"
        style="margin: 10px 5px 0px 0px; height: 40px"
        plain
        :type="materialCheck.length === 0 ? 'info' : 'success'"
        @click="showMaterial"
      >
        {{ materialCheck.length === 0 ? '物资名录选择' : '物资名录(已选择)' }}</el-button
      >

      <el-button
        v-if="type !== '3'"
        style="margin: 10px 5px 0px 0px; height: 40px"
        plain
        :type="stockCheck.length === 0 ? 'info' : 'success'"
        @click="showStock"
        >{{ stockCheck.length === 0 ? '物资仓库选择' : '物资仓库(已选择)' }}
      </el-button>
    </div>

    <!--物资名录弹窗-->
    <el-dialog
      class="materialDialog"
      title="物资名录选择"
      :visible.sync="materialDialogVisable"
      top="40px"
      width="1800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      @close="materialDialogClose"
    >
      <MaterialList :isCheck="1" :helpTypeList="helpTypeIdList" @success="checkMaterial" @close="materialDialogClose" />
    </el-dialog>

    <!--仓库管理弹窗-->
    <el-dialog
      class="materialDialog"
      title="仓库选择"
      :visible.sync="stockDialogVisable"
      top="15px"
      width="1800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      @close="stockDialogClose"
    >
      <StockList :isCheck="1" :helpTypeList="helpTypeNameList" @success="checkStock" @close="stockDialogClose" />
    </el-dialog>

    <!--统计-->
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
          height="760px"
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
import MaterialList from '../storage/materialList/MaterialList.vue'
import StockList from '../storage/storageManage/StorageManage.vue'
// import router from "../../router";
export default {
  components: {
    MaterialList,
    StockList
  },
  data() {
    return {
      type: '1',
      radio: 1,
      loading: false,
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      datetimerange: [],
      tableData: [],
      regionList: [],
      checkedId: [],
      typeList: [],
      filterType: [],
      filterType2: [],
      beginTime: '',
      endTime: '',
      urlExel: '',
      newarr: [],
      newCheckedId: [],
      regionCheckedId: [],
      showTag: false,
      dataLoading: false,
      materialDialogVisable: false,
      materialCheck: [],
      stockDialogVisable: false,
      stockCheck: [],
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
  watch: {},
  computed: {
    helpTypeIdList() {
      return this.filterType.map((item) => {
        return item.id
      })
    },
    helpTypeNameList() {
      return this.filterType.map((item) => {
        return item.name
      })
    }
  },
  mounted() {
    this.getHelpTypeList()
    this.getRegion()
  },
  methods: {
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
    getHelpTypeList() {
      let url = '/gtw/EIMS-management/teamInfo/emergencyPowerDictList'
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.typeList = data
        this.typeList2 = data
        this.typeList3 = data
      })
    },
    getRegion() {
      let url = '/gtw/EIMS-management/stockInfoData/getRegion'
      Axios.get(url).then((r) => {
        this.regionList = r.data.data.data
        // console.log(this.regionList)
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
      if (this.stockCheck.length === 0 && this.type !== '3') {
        this.$message.error('请先选择仓库')
        return
      }
      if (this.filterType2.length === 0 && this.type === '3') {
        this.$message.error('请先选择区县')
        return
      }

      if (this.radio === 5) {
        this.beginTime = this.datetimerange[0].slice(0, 10)
        this.endTime = this.datetimerange[1].slice(0, 10)
      } else if (this.radio === 1) {
        this.beginTime = moment(new Date()).subtract(1, 'months').format('YYYY-MM-DD')
        this.endTime = moment(new Date()).format('YYYY-MM-DD')
      }
      switch (this.type) {
        case '1':
          this.getAxiosList(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '2':
          this.getAxiosList2(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '3':
          this.getStockByRegion(this.filterType2, this.helpTypeIdList, this.materialCheck.join())
          break
        case '4':
          this.getAxiosList4(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '5':
          this.getAxiosList5(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '6':
          this.getAxiosList6(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '7':
          this.getAxiosList7(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '8':
          this.getAxiosList8(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
        case '9':
          this.getAxiosList9(this.stockCheck.join(), this.helpTypeIdList, this.materialCheck.join())
          break
      }
    },
    getStockByRegion(regionList, helpTypeIdList, stuffList) {
      let url = ''
      if (regionList.includes('全部')) {
        url = '/gtw/EIMS-management/warehouse/getStockIdByArea'
      } else {
        url = `/gtw/EIMS-management/warehouse/getStockIdByArea?regionId=${regionList.join()}`
      }
      Axios.get(url).then((r) => {
        let stockIds = r.data.data
        this.getAxiosList(stockIds, helpTypeIdList, stuffList)
      })
    },
    getAxiosList(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
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
        data: params,
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
      })
    },
    getAxiosList2(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
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
        data: params,
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
      })
    },
    getAxiosList4(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
      }
      this.dataLoading = true
      let url =
        '/gtw/EIMS-management/resourceAgreement/stuffReverseExcel?stockIds=' + stockId + '&helpTypes=' + helpTypes
      Axios.post(url, params).then((r) => {
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
      })
    },
    getAxiosList5(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
      }
      this.dataLoading = true
      let url = '/gtw/EIMS-management/warehouse/stuffExcelExport3?helpTypes=' + helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: params,
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
      })
    },
    getAxiosList6(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
      }
      this.dataLoading = true
      let url = '/gtw/EIMS-management/resourceAgreement/downloadForhelpTypes?helpTypes=' + helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: params,
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
      })
    },
    getAxiosList7(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
      }
      this.dataLoading = true
      let url = '/gtw/EIMS-management/resourceAgreement/downloadForManageTypes?helpTypes=' + helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: params,
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
      })
    },
    getAxiosList8(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
      }
      this.dataLoading = true
      let url = '/gtw/EIMS-management/resourceAgreement/downloadForTypes?helpTypes=' + helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: params,
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
      })
    },
    getAxiosList9(stockId, helpTypes, stuffIds) {
      if (!helpTypes) {
        helpTypes = ''
      } else {
        helpTypes = helpTypes.join(',')
      }
      let params = {
        stockIds: stockId,
        stuffIds: stuffIds
      }
      this.dataLoading = true
      let url = '/gtw/EIMS-management/resourceAgreement/downloadForDirectories?helpTypes=' + helpTypes
      Axios({
        method: 'POST',
        url: url,
        data: params,
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
      this.filterType = '1'
      this.filterType = []
    },
    showMaterial() {
      this.materialDialogVisable = true
    },
    materialDialogClose() {
      this.materialDialogVisable = false
    },
    checkMaterial(v) {
      this.materialCheck = v
    },
    showStock() {
      // console.log(this.filterType)
      this.stockDialogVisable = true
    },
    stockDialogClose() {
      this.stockDialogVisable = false
    },
    checkStock(v) {
      this.stockCheck = v
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
