<template>
  <section class="materialReserveLimit">
    <el-row class="materialReserveLimitHeader">
      <el-col class="materialReserveLimitHeaderRightArea">
        <el-form :inline="true">
          <el-form-item label="所属区县">
            <el-select
              v-model="formDetail.regionName"
              filterable
              placeholder="请输入所属区县"
              @change="regionNamechange"
              clearable
            >
              <el-option
                v-for="item in areaList"
                :value="item.regionName"
                :label="item.regionName"
                :key="item.regionId"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="仓库名称">
            <el-tooltip effect="dark" :disabled="disabledTooltip" content="请先输入区县名" placement="top">
              <el-select
                v-model="formDetail.stockName"
                filterable
                placeholder="请输入仓库名称"
                :disabled="disabled"
                clearable
              >
                <el-option v-for="item in stockList" :value="item.stockName" :label="item.stockName" :key="item.id">
                </el-option>
              </el-select>
            </el-tooltip>
          </el-form-item>
          <el-form-item label="物资名称">
            <el-input v-model="formDetail.stuffName" clearable placeholder="请输入物资名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button type="primary" icon="el-icon-plus" @click="increaseRecord">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
    <el-row class="znv-table-area">
      <el-table :data="tableData" border max-height="800px" :span-method="objectSpanMethod">
        <el-table-column label="序号" align="center" width="80">
          <template slot-scope="scope">
            {{ scope.row.group + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="areaName" label="所属区县" width="100" align="center"></el-table-column>
        <el-table-column show-overflow-tooltip width="250" prop="stockName" label="仓库名称" align="center">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="stuffName" label="物资名称" align="center"></el-table-column>
        <el-table-column prop="stuffUnit" label="计量单位" align="center" width="90"></el-table-column>
        <el-table-column prop="stuffModel" label="物资型号" align="center"></el-table-column>
        <el-table-column prop="stuffBrand" label="物资品牌" align="center"></el-table-column>
        <el-table-column prop="stuffLimit" label="储备限额" align="center"></el-table-column>
        <el-table-column prop="stuffFloat" label="浮动值" align="center"></el-table-column>
        <!-- <el-table-column prop="" label="预警" align="center">
          <template slot-scope="">
            <el-tooltip class="item" effect="dark" content="超出储备限额" placement="top">
              <i class="el-icon-warning"></i>
            </el-tooltip>
          </template>
        </el-table-column> -->
        <el-table-column label="操作" width="80" align="center">
          <template slot-scope="scope">
            <el-button type="text" size="medium" @click="delDutyInfo(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>

    <DetailsDialog :dialogVisible.sync="isShowAdd" :formInfo="formInfo" :isCode="isCode" @success="handleSuccess" />
  </section>
</template>
<script>
import Axios from 'axios'
import DetailsDialog from './DetailsDialog.vue'
import qs from 'qs'

export default {
  components: { DetailsDialog },
  data() {
    return {
      sortarray: ['ascending'],
      isCode: 0,
      isShowAdd: false,
      formInfo: null,
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      tableData: [],
      formDetail: {
        areaName: '', // 区县
        stockName: '', // 仓库名称
        stuffName: '', // 物资名称
        stuffUnit: '', // 计算单位
        stuffModel: '', // 物资型号
        stuffBrand: '', // 物资品牌
        stuffLimit: '', // 储备限额
        stuffFloat: '' // 浮动值
      },
      areaList: [],
      rowAndColumn: [],
      rowRoomColumn: [],
      stockList: [],
      disabled: true,
      disabledTooltip: false
    }
  },
  mounted() {
    this.querySerch()
    this.getAreaList()
  },
  methods: {
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      let self = this
      if (columnIndex === 1 || columnIndex === 0) {
        if (self.rowAndColumn[rowIndex]) {
          let rowNum = self.rowAndColumn[rowIndex]
          return {
            rowspan: rowNum,
            colspan: rowNum > 0 ? 1 : 0
          }
        }
        return {
          rowspan: 0,
          colspan: 0
        }
      }
      if (columnIndex === 2) {
        if (self.rowRoomColumn[rowIndex]) {
          let roomNum = self.rowRoomColumn[rowIndex]
          return {
            rowspan: roomNum,
            colspan: roomNum > 0 ? 1 : 0
          }
        }
        return {
          rowspan: 0,
          colspan: 0
        }
      }
    },
    getListDataForRowAndColumn(data) {
      let self = this
      self.rowAndColumn = []
      self.rowRoomColumn = []
      for (var i = 0; i < data.length; i++) {
        if (i === 0) {
          // 如果是第一条记录（即索引是0的时候），向数组中加入１
          self.rowAndColumn.push(1)
          self.pos = 0
          self.rowRoomColumn.push(1)
          self.posT = 0
          data[i].group = i
        } else {
          // data[i].typeDesc就是你从接口读取的字段信息，下同
          if (data[i].areaName === data[i - 1].areaName) {
            // 如果typeDesc相等就累加，并且push 0
            self.rowAndColumn[self.pos] += 1
            self.rowAndColumn.push(0)
            data[i].group = this.tableData[i - 1].group
            if (data[i].stockName === data[i - 1].stockName) {
              // 如果areaDesc相等就累加，并且push 0
              self.rowRoomColumn[self.posT] += 1
              self.rowRoomColumn.push(0)
            } else {
              self.rowRoomColumn.push(1)
              self.posT = i
            }
          } else {
            // 不相等push 1
            self.rowAndColumn.push(1)
            self.pos = i
            self.rowRoomColumn.push(1)
            self.posT = i
            data[i].group = this.tableData[i - 1].group + 1
          }
        }
      }
    },
    sortList(lists) {
      // lists传的是数组 分局任务性质、任务级别排序
      return lists.sort((a, b) => {
        return (b.areaName || '').localeCompare(a.areaName) || (b.stockName || '').localeCompare(a.stockName) // grapheme为字母对应的属性名
      })
    },
    getAreaList() {
      let url = `/gtw/EIMS-management/companyDict/queryRegion?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    async delDutyInfo(row) {
      const confirmResult = await this.$confirm('此操作将删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch((err) => {
        return err
      })
      if (confirmResult !== 'confirm') {
        return this.$message.info('已经取消了删除')
      } else {
        // console.log(row)
        let data = {
          ...{
            id: row.id
          }
        }
        Axios.get('/gtw/EIMS-management/resourceAgreement/deleteStuff?' + qs.stringify(data, { encode: false }))
          .then((res) => {
            // console.log(r)
            // console.log(this.selectedIsKeys)
            if (res.data.code === 200) {
              if (res.data.data == null) {
                this.querySerch()
                this.$message.success('删除成功！')
              } else {
                this.$message.warning(res.data.data)
              }
            } else {
              this.$message.warning(res.data.message)
            }
          })
          .catch((v) => {
            // console.log(v)
          })
      }
    },
    // 区县框
    regionNamechange(val) {
      this.stockList = []
      this.formDetail.stockName = ''
      if (this.formDetail.regionName.length == 0) {
        this.$message.warning('请输入区县！')
        this.disabled = true
        this.stockList = []
        this.formDetail.stockName = ''
        this.disabledTooltip = false
      } else {
        this.disabledTooltip = true
        this.disabled = false
        let params = {
          regionId: this.areaList.filter((v) => v.regionName === val)[0].regionId,
          userLevel: JSON.parse(localStorage.getItem('userLevel'))
        }
        Axios.get('/gtw/EIMS-management/stockInfo/queryAllStock?', { params }).then((res) => {
          let data = res.data.data || []
          this.stockList = data
        })
      }
    },
    // 查询
    querySerch() {
      let params = {
        areaName: this.formDetail.regionName,
        stockName: this.formDetail.stockName,
        stuffName: this.formDetail.stuffName,
        userLevel: JSON.parse(localStorage.getItem('userLevel'))
      }
      let url = '/gtw/EIMS-management/resourceAgreement/queryStuff'

      Axios.get(url, { params }).then((r) => {
        let data = r.data.data.list
        let arr = this.sortList(data)
        this.tableData = arr
        this.getListDataForRowAndColumn(arr)
      })
    },
    handleSearch() {
      this.querySerch()
    },
    increaseRecord() {
      this.isCode = 0
      this.isShowAdd = true
    },
    handleSuccess() {
      this.querySerch()
    }
  }
}
</script>

<style lang="scss">
.el-icon-warning {
  font-size: 25px;
  color: #f56c6c;
}
</style>
