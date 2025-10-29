<template>
  <section class="StorageOutInDetail">
    <el-row class="header">
      <el-form :inline="true" :model="formInline">
        <el-form-item label="所属区县">
          <el-select v-model="formInline.areaId" placeholder="所属区县">
            <el-option label="全部" value=""></el-option>
            <el-option
              v-for="item in areaList"
              :value="item.countyCode"
              :label="item.countyName"
              :key="item.countyCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库名称">
          <el-input v-model="formInline.stockName" placeholder="请输入仓库名称"></el-input>
        </el-form-item>
        <el-form-item label="物资名称">
          <el-input v-model="formInline.stuffName" placeholder="请输入物资名称"></el-input>
        </el-form-item>
        <el-form-item label="物资型号">
          <el-input v-model="formInline.stuffModel" placeholder="请输入物资型号"></el-input>
        </el-form-item>
        <el-form-item label="物资品牌">
          <el-input v-model="formInline.stuffBrand" placeholder="请输入物资品牌"></el-input>
        </el-form-item>
        <el-form-item label="救援类型">
          <el-select v-model="formInline.helpTypeId" placeholder="救援类型">
            <el-option label="全部" value=""></el-option>
            <el-option v-for="item in helpTypeList" :value="item.id" :label="item.name" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出入库类型">
          <el-select v-model="formInline.operationType" placeholder="操作类型">
            <el-option label="全部" value=""></el-option>
            <el-option label="出库" value="1"></el-option>
            <el-option label="入库" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="操作时间">
          <el-date-picker
            style="width: 455px"
            v-model="datetimerange"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            @change="datetimerangeChange"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="经办人员">
          <el-input v-model="formInline.manager" placeholder="请输入经办人员"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <div class="scrollArea">
      <el-table v-loading="loading" :data="tableData" height="720">
        <el-scrollbar>
          <el-table-column prop="area_name" label="所属县区" width="180"> </el-table-column>
          <el-table-column prop="stock_name" label="仓库名称" width="300"> </el-table-column>
          <!-- <el-table-column
          prop="unit"
          label="主管部门"
          width="200">
        </el-table-column> -->
          <el-table-column prop="stuff_name" label="物资名称" width="260"> </el-table-column>
          <el-table-column prop="stuff_model" label="物资型号" width="200"> </el-table-column>
          <el-table-column prop="stuff_operation_num" label="物资数量" width="180">
            <template slot-scope="scope">
              {{ Math.abs(scope.row.stuff_operation_num) }}
            </template>
          </el-table-column>
          <el-table-column prop="stuff_unit" label="计量单位" width="180"> </el-table-column>
          <el-table-column prop="operation_type" label="出入库类型" width="120">
            <template slot-scope="scope">
              {{ scope.row.operation_type === '1' ? '出库' : '入库' }}
            </template>
          </el-table-column>
          <el-table-column prop="stock_stuff_num" label="物资库存" width="180"> </el-table-column>
          <!-- <el-table-column
          prop="recipient_unit"
          label="操作单位"
          width="200">
        </el-table-column> -->
          <el-table-column prop="help_type" label="救援类型" width="180"> </el-table-column>
          <el-table-column prop="stuff_brand" label="物资品牌" width="200"> </el-table-column>
          <el-table-column prop="identifier" label="编号" width="200"> </el-table-column>
          <el-table-column prop="source" label="物资来源/领用单位" width="200"> </el-table-column>
          <!-- <el-table-column
          prop="stuff_create_date"
          label="生产日期"
          width="240">
        </el-table-column> -->
          <!-- <el-table-column
          prop="price"
          label="物资单价"
          width="180">
        </el-table-column> -->
          <!-- <el-table-column
          prop="life"
          label="使用年限"
          width="180">
        </el-table-column> -->
        </el-scrollbar>
        <el-table-column fixed="right" prop="operation_time" label="操作时间" width="200"> </el-table-column>
        <el-table-column fixed="right" prop="manager" label="经办人员" width="130"> </el-table-column>
        <el-table-column fixed="right" prop="" label="操作" width="130">
          <template slot-scope="scope">
            <el-button @click="delRow(scope.row)" type="text" size="medium">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="data-title">
      共<span class="blue">{{ total }}</span
      >条数据 | 最近更新时间：{{ updateTime }}
    </div>
    <el-pagination
      @current-change="handleCurrentChange"
      :current-page.sync="formInline.page"
      :page-size="formInline.pageSize"
      @size-change="handleSizeChange"
      :page-sizes="[50, 100, 500, 1000]"
      layout="sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </section>
</template>

<script>
// 物资出入库明细
import axios from 'axios'

export default {
  name: 'StorageOutInDetail',
  components: {},
  data() {
    return {
      loading: false,
      currentPage: 0,
      userId: localStorage.getItem('userId'),
      userLevel: localStorage.getItem('level'),
      wareHouseLevel: configs.wareHouseLevel,
      formInline: {
        areaId: '',
        stuffBrand: '',
        stuffModel: '',
        stockName: '',
        stuffName: '',
        helpTypeId: '',
        operationType: '',
        startTime: '',
        endTime: '',
        manager: '',
        page: 1,
        pageSize: 50
      },
      tableData: [],
      datetimerange: [],
      total: 0,
      updateTime: '',
      areaList: [],
      helpTypeList: []
    }
  },
  mounted() {
    this.getTableData()
    this.getAreaList()
    this.getHelpTypeList()
  },
  methods: {
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
    delRow(row) {
      this.$confirm('此操作将删除并回滚该条出入库数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = `/gtw/EIMS-management/warehouse/deleteOperatiionInfoById?id=${row.id}`
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
    },
    datetimerangeChange(val) {
      // console.log(val)
      if (val) {
        this.formInline.startTime = val[0]
        this.formInline.endTime = val[1]
      } else {
        this.formInline.startTime = ''
        this.formInline.endTime = ''
      }
    },
    getTableData() {
      this.tableData = []
      this.loading = true
      let url =
        '/gtw/EIMS-management/warehouse/stuffRecordDetail?userId=' + this.userId + '&userLevel=' + this.userLevel
      for (let key in this.formInline) {
        if (this.formInline[key]) url += '&' + key + '=' + this.formInline[key]
      }
      // console.log(url)
      axios
        .get(url)
        .then((res) => {
          this.loading = false
          let data = res.data.data || {}
          this.total = data.total
          this.tableData = data.list || []
          // if (this.tableData.length) this.updateTime = this.tableData[0].operation_time
          this.updateTime = this.getNowDate()
        })
        .catch(() => {
          this.loading = false
        })
    },
    getAreaList() {
      let url = `/gtw/EIMS-management/manage/county?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    getHelpTypeList() {
      let url = '/gtw/EIMS-management/teamInfo/emergencyPowerDictList'
      axios.get(url).then((res) => {
        let data = res.data.data || []
        this.helpTypeList = data
      })
    },
    getNowDate() {
      let now = new Date()
      let year = now.getFullYear()
      let mounth = now.getMonth() + 1 < 10 ? `0${now.getMonth() + 1}` : now.getMonth() + 1
      let day = now.getDate() < 10 ? `0${now.getDate()}` : now.getDate()

      return `${year}-${mounth}-${day}`
    }
  }
}
</script>

<style lang="scss">
@import '../../../../../style/base';
.StorageOutInDetail {
  .check {
    color: $--color-primary;
    border-bottom: 1px solid $--color-primary;
    padding: 5px 0;
    font-size: 20px;
    cursor: pointer;
  }

  .scrollArea {
    position: relative;
    overflow: auto;
    width: 100%;
    height: 80%;
    margin-bottom: 15px;
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
