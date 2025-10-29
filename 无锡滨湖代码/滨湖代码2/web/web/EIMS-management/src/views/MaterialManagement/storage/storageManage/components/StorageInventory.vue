<template>
  <section class="StorageInventory">
    <el-row class="header">
      <el-form :inline="true" :model="formInline">
        <el-form-item label="仓库名称">
          <el-input clearable v-model="formInline.stockName" placeholder="请输入仓库名称"></el-input>
        </el-form-item>
        <el-form-item label="物资名称">
          <el-input clearable v-model="formInline.stuffName" placeholder="请输入物资名称"></el-input>
        </el-form-item>
        <el-form-item label="物资型号">
          <el-input clearable v-model="formInline.stuffModel" placeholder="请输入物资型号"></el-input>
        </el-form-item>
        <el-form-item label="物资品牌">
          <el-input clearable v-model="formInline.stuffBrand" placeholder="请输入物资品牌"></el-input>
        </el-form-item>
        <el-form-item label="救援类型">
          <el-select clearable v-model="formInline.helpTypeId" placeholder="救援类型">
            <el-option label="全部" value=""></el-option>
            <el-option v-for="item in helpTypeList" :value="item.id" :label="item.name" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>

      <el-button class="addBtn" type="primary" @click="addNewList">入库</el-button>
    </el-row>
    <div class="scrollArea">
      <el-radio-group v-model="showTable" @change="checkType">
        <el-radio-button label="物资库存"></el-radio-button>
        <el-radio-button label="协议储备"></el-radio-button>
      </el-radio-group>
      <el-table v-if="showTable === '物资库存'" v-loading="loading" :data="tableData" height="720" style="width: 100%">
        <el-table-column prop="stockName" label="所属仓库" width="300"> </el-table-column>
        <el-table-column prop="stuff_name" label="物资名称" width="120"> </el-table-column>
        <el-table-column prop="model" label="物资型号" width="200"> </el-table-column>
        <el-table-column prop="STUFF_NUMBER" label="库存数量" width="120"> </el-table-column>
        <el-table-column prop="STUFF_UNIT" label="计量单位" width="120"> </el-table-column>
        <el-table-column prop="brand" label="物资品牌" width="120"> </el-table-column>
        <el-table-column prop="helpName" label="救援类型" width="180"> </el-table-column>
        <el-table-column prop="MANAGER" label="负责人" width="120"> </el-table-column>
        <el-table-column prop="MANAGER_PHONE" label="联系方式" width="120"> </el-table-column>
        <el-table-column fixed="right" :key="tablekey" width="340">
          <!-- eslint-disable-next-line vue/no-unused-vars -->
          <template slot="header" slot-scope="scope">
            <div style="display: inline-block">操作</div>
          </template>
          <template slot-scope="scope">
            <el-input-number
              @change="inputChange($event, scope.row)"
              size="mini"
              v-model.number="outInNum[scope.row.stockId + scope.row.stuff_name]"
              :precision="2"
              :step="1"
              :min="0"
            >
            </el-input-number>
            <el-button @click="handle(scope.row, 'in')" type="text" size="medium">入库</el-button>
            <el-button @click="handle(scope.row, 'out')" type="text" size="medium">出库</el-button>
            <el-button @click="handleDetail(scope.row, 'in')" type="text" size="medium">详细入库</el-button>
            <el-button @click="handleDetail(scope.row, 'out')" type="text" size="medium">详细出库</el-button>
            <el-button @click="checkStuff(scope.row)" type="text" size="medium">核对</el-button>
            <el-button @click="handleDel(scope.row)" type="text" size="medium">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-table
        v-if="showTable === '协议储备'"
        v-loading="loading2"
        :data="tableData2"
        height="720"
        style="width: 100%"
      >
        <el-table-column prop="stockName" label="所属仓库" width="300"> </el-table-column>
        <el-table-column prop="stuffName" label="物资名称"> </el-table-column>
        <el-table-column prop="stuffModel" label="物资型号" width="200"> </el-table-column>
        <el-table-column prop="stuffQuantity" label="储备数量" width="120"> </el-table-column>
        <el-table-column prop="stuff_unit" label="计量单位" width="120"> </el-table-column>
        <el-table-column prop="stuffBrand" label="物资品牌" width="120"> </el-table-column>
        <el-table-column prop="helpType" label="救援类型" width="180"> </el-table-column>
        <el-table-column prop="agreementName" label="协议名称" width="120"> </el-table-column>
        <el-table-column prop="" label="协议时效" width="120">
          <template slot-scope="scope"> {{ scope.row.startTime }} - {{ scope.row.endTime }} </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="formInline.page"
      :page-size="formInline.pageSize"
      @size-change="handleSizeChange"
      :page-sizes="[50, 100, 500, 1000]"
      layout="total,sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
    <StorageInventoryOut
      :dialogVisible.sync="showOut"
      :formInfo="formInfo"
      :status="checkTo"
      @success="handleSuccess"
    />
    <StorageInventoryAdd :dialogVisible.sync="addDialogVisible" @success="handleSuccess" />
  </section>
</template>

<script>
import axios from 'axios'
import StorageInventoryOut from './StorageInventoryOut'
import StorageInventoryAdd from './StorageInventoryAdd'

export default {
  name: 'storageInventory',
  components: { StorageInventoryOut, StorageInventoryAdd },
  data() {
    return {
      showOut: false,
      addDialogVisible: false,
      loading: false,
      loading2: false,
      userId: localStorage.getItem('userId'),
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      checkTo: '',
      total: 0,
      outInNum: {},
      formInline: {
        stockName: '',
        stuffName: '',
        helpTypeId: '',
        stuffModel: '',
        stuffBrand: '',
        page: 1,
        pageSize: 50
      },
      showTable: '物资库存',
      tablekey: false,
      tableData: [],
      tableData2: [],
      updateTime: '',
      helpTypeList: [],
      formInfo: null
    }
  },
  computed: {
    stuff() {
      return encodeURI(this.formInline.stuffName)
    }
  },
  mounted() {
    // console.log(this.$route.query)
    this.$nextTick(() => {
      if (this.$route.query && this.$route.query.stockName) this.formInline.stockName = this.$route.query.stockName
      this.getTableData()
      this.getHelpTypeList()
    })
  },
  methods: {
    handleDetail(row, status) {
      this.formInfo = row
      this.showOut = true
      this.checkTo = status
    },
    checkType(v) {
      this.showTable = v
      this.formInline = {
        stockName: '',
        stuffName: '',
        helpTypeId: '',
        stuffModel: '',
        stuffBrand: '',
        page: 1,
        pageSize: 50
      }
      this.getTableData()
    },
    handle(row, status) {
      // this.formInfo = row
      // this.showOut = true
      // this.checkTo = status
      let text = status === 'in' ? '入库' : '出库'
      if (this.outInNum[row.stockId + row.stuff_name] === 0) {
        this.$message(`${text}数量不能为0`)
        return
      }
      let params = {
        stockId: row.stockId,
        stuffId: row.stuffId,
        operationNum: this.outInNum[row.stockId + row.stuff_name],
        // recipientUnit: '',
        manager: localStorage.user,
        // recipientPhone: '',
        recipientTime: this.getNowDate(),
        identifier: '',
        source: '',
        userId: localStorage.getItem('userId'),
        brand: row.brand,
        model: row.model,
        productionDay: row.stuff_create_date
      }
      if (status === 'out') {
        if (params.operationNum > row.STUFF_NUMBER) {
          this.$message.error(`出库数量不能大于${this.formInfo.STUFF_NUMBER}`)
        } else if (params.operationNum <= 0) {
          this.$message.error(`出库数量至少大于0`)
        } else {
          params.operationType = '1'
        }
      } else {
        params.operationType = '2'
      }
      this.$confirm(
        `${row.stuff_name}  ${text}：${this.outInNum[row.stockId + row.stuff_name]}${row.STUFF_UNIT}，是否确定?`,
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        let url = '/gtw/EIMS-management/stockInfoData/shipment'
        axios.post(url, params).then((r) => {
          if (r.data.code === 200) {
            this.$message.success(`${text}成功`)
            this.getTableData()
          } else {
            this.$message.error(r.data.message)
          }
        })
      })
    },
    inputChange(e, row) {
      // console.log('row', row)
      this.tablekey = !this.tablekey
    },
    checkStuff(row) {
      this.$confirm(`${row.stuff_name}的库存数量：${row.STUFF_NUMBER}${row.STUFF_UNIT}，是否完成核对?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = {
          stockId: row.stockId,
          stuffId: row.stuffId,
          brand: row.brand,
          model: row.model
        }
        let url = '/gtw/EIMS-management/warehouse/stockStuffCheck'
        axios.post(url, params).then((r) => {
          if (r.data.code === 200) {
            this.$message.success('核对完成')
          } else {
            this.$message(r.data.message)
          }
        })
      })
    },
    handleDel(row) {
      if (row.STUFF_NUMBER > 0) {
        this.$message('库存大于0时不能删除该物资数据！')
      } else {
        this.$confirm('此操作将删除并回滚该条出入库数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let url = `/gtw/EIMS-management/stockInfoData/deleteStockStuff?stockId=${row.stockId}&stuffId=${row.stuffId}&brand=${row.brand}&model=${row.model}`
            axios.get(url).then((r) => {
              if (r.data.code === 200) {
                this.$message.success('删除成功')
                this.getTableData()
              } else {
                this.$message(r.data.message)
              }
            })
          })
          .catch(() => {
            this.$message('已取消')
          })
      }
    },
    getNowDate() {
      let now = new Date()
      let year = now.getFullYear()
      let mounth = now.getMonth() + 1 < 10 ? `0${now.getMonth() + 1}` : now.getMonth() + 1
      let day = now.getDate() < 10 ? `0${now.getDate()}` : now.getDate()

      return `${year}-${mounth}-${day}`
    },
    addNewList() {
      this.addDialogVisible = true
    },
    handleSuccess() {
      this.formInline.page = 1
      this.getTableData()
    },
    onSubmit() {
      this.formInline.page = 1
      this.getTableData()
    },
    handleCurrentChange(val) {
      this.getTableData()
    },
    handleSizeChange(v) {
      this.formInline.pageSize = v
      this.formInline.page = 1
      this.getTableData()
    },
    getTableData() {
      this.tableData = []
      this.tableData2 = []
      this.loading = true
      let url = '/gtw/EIMS-management/warehouse/stuffInventory?userId=' + this.userId + '&userLevel=' + this.userLevel
      if (this.showTable === '协议储备') {
        url =
          '/gtw/EIMS-management/resourceAgreement/queryAgreementStuff?userId=' +
          this.userId +
          '&userLevel=' +
          this.userLevel
      }
      // console.log(url)
      // this.formInline.stuffName = this.stuff
      for (let key in this.formInline) {
        if (this.formInline[key] && key !== 'stuffName') url += '&' + key + '=' + this.formInline[key]
      }
      url += '&stuffName=' + this.stuff
      // console.log(url)
      axios
        .get(url)
        .then((res) => {
          this.loading = false
          let data = res.data.data || {}
          this.total = data.total
          if (this.showTable === '协议储备') {
            this.tableData2 = data.list || []
          } else {
            this.tableData = data.list || []
          }
          this.changeNumObj()
        })
        .catch(() => {
          this.loading = false
        })
    },
    changeNumObj() {
      this.tableData.forEach((item) => {
        let key = item.stockId + item.stuff_name
        this.outInNum[key] = 0
      })
    },
    getHelpTypeList() {
      let url = '/gtw/EIMS-management/teamInfo/emergencyPowerDictList'
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.helpTypeList = data
      })
    }
  }
}
</script>

<style lang="scss">
.StorageInventory {
  @import '../../../../../style/base';

  .check {
    color: $--color-primary;
    border-bottom: 1px solid $--color-primary;
    padding: 5px 0;
    font-size: 20px;
    cursor: pointer;
  }

  .addBtn {
    position: absolute;
    top: 0px;
    right: 50px;
  }

  .scrollArea {
    width: 100%;
    height: 85%;
    margin-bottom: 15px;
    overflow: scroll;
  }

  .data-title {
    float: left;
    margin-top: 20px;
  }

  .blue {
    color: $--color-primary;
  }

  .header {
    .el-input__inner {
      width: 185px;
    }
  }
}
</style>
