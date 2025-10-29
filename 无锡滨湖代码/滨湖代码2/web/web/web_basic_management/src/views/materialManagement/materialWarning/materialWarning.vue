<template>
  <div class="materialWarning">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="避难场所名称:">
        <el-input clearable v-model="searchForm.siteName" style="width: 175px;" placeholder="请输入避难场所名称"></el-input>
      </el-form-item>
      <el-form-item label="所属社区:">
        <el-input clearable v-model="searchForm.siteUnit" style="width: 160px;" placeholder="请输入所属社区"></el-input>
        <!-- <el-select filterable v-model="searchForm.siteUnit" placeholder="请选择所属社区" clearable>
                    <el-option v-for="item in siteUnitList" :value="item.id" :label="item.name" :key="item.id"></el-option>
        </el-select>-->
      </el-form-item>
      <el-form-item label="物资名称:">
        <el-input clearable v-model="searchForm.stuffName" style="width: 160px;" placeholder="请输入物资名称"></el-input>
      </el-form-item>
      <el-form-item label="管理人员:">
        <el-input clearable v-model="searchForm.sitePerson" style="width: 160px;" placeholder="请输入管理人员"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <!-- <div class="content-top">
                <div class="content-left">
                    <el-button icon="el-icon-download" @click="inputData">导入</el-button>
                    <el-button icon="el-icon-upload2" @click="exportData">导出</el-button>
                    <el-button icon="el-icon-delete" @click="deleteData">删除</el-button>
                </div>
                <div class="content-right">
                    <el-button icon="el-icon-plus" @click="addData">新增记录</el-button>
                    <el-button type="text" icon="el-icon-search" @click="onSubmit"></el-button>
                    <el-button type="text" icon="el-icon-refresh" @click="resetSearch"></el-button>
                    <el-button type="text" icon="el-icon-s-unfold"></el-button>
                </div>
      </div>-->
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        height="800"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <!-- <el-table-column align='center' header-align='center'  type="selection" width="55"> </el-table-column> -->
        <!-- <el-table-column align='center' header-align='center'  type="index" label="序号" width="70" :index="table_index"></el-table-column> -->
        <!-- <el-table-column align='center' header-align='center'  prop="remind" label="预警信息" show-overflow-tooltip></el-table-column> -->
        <el-table-column
          align="center"
          header-align="center"
          prop="info"
          label="预警信息"
          width="400"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column align="center" header-align="center" prop="siteUnit" label="所属社区"></el-table-column>
        <el-table-column align="center" header-align="center" prop="siteName" label="场所名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffName" label="物资名称" width="110"></el-table-column>
        <el-table-column align="center" header-align="center" prop="siteAddress" label="场所地址"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffNum" label="库存" width="50"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffUnit" label="单位" width="50"></el-table-column>
        <el-table-column align="center" header-align="center" prop="sitePerson" label="管理人员" width="90"></el-table-column>
        <el-table-column align="center" header-align="center" prop="sitePhone" label="管理员手机" width="120"></el-table-column>
        <!-- <el-table-column align='center' header-align='center'  label="操作" width="180">
                <template slot-scope="scope">
                    <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium" icon="el-icon-view"></el-button>
                </template>
        </el-table-column>-->
      </el-table>

      <div class="pagination-body">
        <el-pagination
          background
          :current-page="currentPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
import request from '@/utils/request'
export default {
  name: 'materialWarning',
  data() {
    return {
      // siteUnitList:[],
      searchForm: {
        stuffName: '',
        siteName: '', // 避难场所名称
        sitePerson: '', // 管理人员
        siteUnit: '' // 所属社区
      },

      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],

      showAdd: false,
      formInfo: {},
      isCode: 0 // 0 编辑 1 查看详情 2 新增
    }
  },
  mounted() {
    this.queryDataTable()
  },
  methods: {
    // table_index(index) {
    //     return (this.currentPage - 1) * this.pageSize + index + 1
    // },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryDataTable()
    },

    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryDataTable()
    },
    queryDataTable() {
      this.loading = true
      let url = '/binhuapis/resourceWarn/query-condition?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.stuffName) { // 物资名称
        url = url + '&stuffName=' + this.searchForm.stuffName
      }
      if (this.searchForm.sitePerson) { // 管理人员
        url = url + '&sitePerson=' + this.searchForm.sitePerson
      }
      if (this.searchForm.siteName) { // 避难场所名称
        url = url + '&siteName=' + this.searchForm.siteName
      }
      if (this.searchForm.siteUnit) { // 所属社区
        url = url + '&siteUnit=' + this.searchForm.siteUnit
      }
      request.get(url).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },

    // 查询
    onSubmit() {
      this.currentPage = 1
      this.queryDataTable()
    },
    // 重置
    resetSearch() {
      this.searchForm = {
        stuffName: '',
        siteName: '', // 避难场所名称
        sitePerson: '', // 管理人员
        siteUnit: '' // 所属社区
      }
      this.currentPage = 1
      this.queryDataTable()
    }
  }
}
</script>
<style lang="scss">
.materialWarning {
  .content-top {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }
}
</style>
