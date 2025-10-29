<template>
  <section class="MaterialAgreementReserve blank">
    <el-row style="height: 100%">
      <el-col :span="24" class="right-area">
        <el-row class="header">
          <el-col :span="24">
            <el-form :inline="true" ref="formDetail" :model="formDetail" class="znv-el-form" label-width="120px">
              <el-form-item label="所属区县">
                <el-select v-model="formDetail.streetName" filterable placeholder="所属区县" clearable>
                  <el-option
                    v-for="item in areaList"
                    :value="item.regionName"
                    :label="item.regionName"
                    :key="item.regionId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="协议名称">
                <el-input clearable v-model="formDetail.agreementName" placeholder="协议名称"></el-input>
              </el-form-item>
              <el-form-item label="协议时效">
                <el-date-picker
                  v-model="datetimerange"
                  type="daterange"
                  value-format="yyyy-MM-dd"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="仓库名称">
                <el-input clearable v-model="formDetail.stockName" placeholder="仓库名称"></el-input>
              </el-form-item>
              <el-form-item label="物资名称">
                <el-input clearable v-model="formDetail.stuffName" placeholder="物资名称"></el-input>
              </el-form-item>
              <el-form-item label="物资型号">
                <el-input clearable v-model="formDetail.stuffModel" placeholder="物资型号"></el-input>
              </el-form-item>
              <el-form-item label="物资品牌">
                <el-input clearable v-model="formDetail.stuffBrand" placeholder="物资品牌"></el-input>
              </el-form-item>
              <el-form-item label=" ">
                <el-button type="primary" @click="handleSearch">查询</el-button>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
        <el-row class="znv-table-area">
          <div class="contentBody">
            <header>
              <span class="title">查询表格</span>
              <span>
                <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
              </span>
            </header>
            <el-table :data="tableData" style="width: 100%" height="625" v-loading="loading">
              <el-table-column sortable type="index" label="序号" width="70" :index="table_index"></el-table-column>
              <el-table-column show-overflow-tooltip prop="streetName" label="所属区县"></el-table-column>
              <el-table-column show-overflow-tooltip prop="stockName" label="仓库名称"></el-table-column>
              <el-table-column show-overflow-tooltip prop="agreementName" label="协议名称"></el-table-column>
              <el-table-column show-overflow-tooltip prop="timeLimit" label="协议时效"></el-table-column>
              <el-table-column show-overflow-tooltip prop="content" label="协议简介"></el-table-column>
              <el-table-column label="操作" width="180">
                <template slot-scope="scope">
                  <el-button type="text" size="medium" @click="handleEdit(scope.row, 2)">详情</el-button>
                  <el-button type="text" size="medium" @click="handleEdit(scope.row, 1)">编辑</el-button>
                  <el-button type="text" size="medium" @click="handleDelete(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              background
              :current-page.sync="currentPage"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :page-size="pageSize"
              :page-sizes="[50, 100, 500, 1000]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total"
            ></el-pagination>
          </div>
        </el-row>
      </el-col>
    </el-row>
    <MaterialAgreementReserveAdd
      :dialogVisible.sync="isShowAdd"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="handleSuccess"
    />
  </section>
</template>

<script>
import Axios from 'axios'
import MaterialAgreementReserveAdd from './MaterialAgreementReserveAdd.vue'

export default {
  name: 'MaterialAgreementReserve',
  components: { MaterialAgreementReserveAdd },
  data() {
    return {
      loading: false,
      currentPage: 1, // 当前页数
      pageSize: 50, // 每页条数
      total: 0, // 总条数
      tableData: [],
      formDetail: {
        streetName: '',
        stuffName: '',
        agreementName: '',
        stockName: '',
        stuffModel: '',
        stuffBrand: ''
      },
      datetimerange: [],
      areaList: [],
      formInfo: null,
      isShowAdd: false,
      isCode: 0
    }
  },

  mounted() {
    this.querySerch()
    this.getAreaList()
  },
  methods: {
    getAreaList() {
      let url = `/gtw/EIMS-management/companyDict/queryRegion?userLevel=${localStorage.level}&userId=${localStorage.userId}`
      Axios.get(url).then((res) => {
        let data = res.data.data || []
        this.areaList = data
      })
    },
    handleSearch() {
      this.currentPage = 1
      this.querySerch()
    },
    // 查询
    querySerch() {
      this.loading = true
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize,
        userLevel: JSON.parse(localStorage.getItem('userLevel'))
      }
      if (this.formDetail.streetName) {
        params.streetName = this.formDetail.streetName
      }
      if (this.formDetail.agreementName) {
        params.agreementName = this.formDetail.agreementName
      }
      if (this.formDetail.stuffName) {
        params.stuffName = this.formDetail.stuffName
      }
      if (this.formDetail.stockName) {
        params.stockName = this.formDetail.stockName
      }
      if (this.formDetail.stuffModel) {
        params.stuffModel = this.formDetail.stuffModel
      }
      if (this.formDetail.stuffBrand) {
        params.stuffBrand = this.formDetail.stuffBrand
      }
      if (this.datetimerange && this.datetimerange.length > 0) {
        params.startTime = this.datetimerange[0]
        params.endTime = this.datetimerange[1]
      }
      let url = '/gtw/EIMS-management/resourceAgreement/queryAgreement'
      Axios.get(url, { params }).then((r) => {
        if (r.data.code === 200) {
          // console.log(r.data.data)
          this.tableData = r.data.data.list
          this.total = r.data.data.total
          this.loading = false
        }
      })
    },

    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },

    handleSizeChange(val) {
      this.pageSize = val
      this.querySerch()
    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.querySerch()
    },
    handleAdd() {
      //   this.$root.eventBus.$emit('isCode', 2)
      this.isCode = 0
      this.isShowAdd = true
    },
    handleSuccess() {
      this.currentPage = 1
      this.querySerch()
    },
    handleEdit(row, code) {
      this.formInfo = JSON.parse(JSON.stringify(row))
      this.isCode = code
      this.isShowAdd = true
    },
    handleDelete(row) {
      this.$confirm('此操作将删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/resourceAgreement/deleteAgreement?id=' + row.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.currentPage = 1
              this.querySerch()
            } else {
              this.$message.error(res.data.message)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    }
  }
}
</script>

<style lang="scss">
@import '../../../style/base';

.MaterialAgreementReserve {
  .right-area {
    overflow: auto;
    height: 100%;
  }
}
</style>
