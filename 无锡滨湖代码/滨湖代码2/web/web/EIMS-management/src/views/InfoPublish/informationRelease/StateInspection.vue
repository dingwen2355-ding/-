<template>
  <div class="StateInspection">
    <div class="headerTop">
      <label>发布方式</label>
      <el-input v-model="searchReleaseWay" placeholder="请输入发布方式" clearable></el-input>
      <label style="margin-left: 20px">巡检时间</label>
      <el-date-picker
        v-model="searchReleaseTime"
        type="datetimerange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd HH:mm:ss"
        :default-time="['00:00:00', '23:59:59']"
      ></el-date-picker>
      <el-button class="searchBtn" @click="querySearch()">查询</el-button>
    </div>

    <div class="contentBody">
      <div class="content-top">
        <div class="top-title">查询表格</div>
      </div>

      <el-table :data="tableData" border style="width: 100%" height="690" v-loading="loading">
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="relName" label="发布方式"></el-table-column>
        <el-table-column prop="reviewTime" label="巡检时间"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <div style="color: #85ce61" v-if="scope.row.status === '0'">正常</div>
            <div style="color: red" v-else>异常</div>
          </template>
        </el-table-column>
        <el-table-column prop="typeName" label="通知方式"></el-table-column>
        <el-table-column prop="adviceName" label="通知人"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
      </el-table>

      <div class="pagination-body">
        <el-pagination
          background
          :current-page.sync="currentPage"
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
import Axios from 'axios'
export default {
  name: 'StateInspection',
  data() {
    return {
      loading: false,
      searchReleaseWay: '',
      searchReleaseTime: [],
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0 // 总条数
    }
  },

  mounted() {
    this.queryStateList()
  },
  methods: {
    queryStateList() {
      this.loading = true
      let url = '/gtw/EIMS-management/get-mes-review'
      let params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize
      }
      if (this.searchReleaseWay) {
        params.relName = this.searchReleaseWay
      }
      console.log(this.searchReleaseTime)
      if (this.searchReleaseTime) {
        if (this.searchReleaseTime.length > 0) {
          params.beginTime = this.searchReleaseTime[0]
          params.endTime = this.searchReleaseTime[1]
        }
      }
      // console.log('参数：', url, params)
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },

    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },

    // 查询
    querySearch() {
      this.currentPage = 1
      this.queryStateList()
    },

    handleSizeChange(val) {
      this.currentPage = 1
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryStateList()
    },

    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryStateList()
    }
  }
}
</script>
<style lang="scss">
.StateInspection {
  // background: transparent !important;
  // box-sizing: border-box;
  // padding: 0 !important;

  .headerTop {
    width: 100%;
    height: 70px;
    background: #ffffff;
    padding-left: 30px;
    display: flex;
    align-items: center;

    label {
      margin-right: 20px;
      font-size: 14px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #606d80;
    }

    .el-input {
      width: 256px;

      .el-input__inner {
        height: 36px !important;
        line-height: 36px;
      }
    }

    .item1 {
      margin-left: 40px;
    }
    .searchBtn {
      width: 69px;
      background: linear-gradient(90deg, #0093ff 0%, #056cf2 100%);
      border-radius: 2px;
      border: 0 none;
      font-size: 14px;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #ffffff;
      margin-left: 40px;
    }
  }

  .contentBody {
    margin-top: 10px;
    width: 100%;
    height: calc(100% - 96px);
    background: #ffffff;
    padding: 0px 20px 10px 20px;
    box-sizing: border-box;

    .content-top {
      width: 100%;
      height: 70px;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .top-title {
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #4e5a6e;
      }

      .el-button {
        width: 88px;
        height: 36px;
        line-height: 0px;
        background: linear-gradient(90deg, #0093ff 0%, #056cf2 100%);
        border-radius: 2px;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
        padding: 0;
        border: 0 none;
      }
    }

    .el-table {
      th.el-table__cell {
        background: #ffffff;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #606d80;
        text-align: center;
        height: 56px;
      }

      td.el-table__cell {
        text-align: center;
        height: 56px;
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #202d40;
        border-bottom: 1px solid #d3dae8;

        .operation {
          display: flex;
          width: 100%;
          align-items: center;
          font-size: 14px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #0093ff;

          span {
            cursor: pointer;
          }
          i {
            width: 1px;
            height: 16px;
            background: #d3dae8;
            margin: 0 20px;
          }
        }
      }
    }
    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
      background-color: #e4f4ff;
    }

    .pagination-body {
      margin-top: 30px;
    }
  }
}
</style>
