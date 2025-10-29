<template>
  <div class="ReleaseLog">
    <div class="headerTop">
      <label>模糊检索</label>
      <el-input v-model="inputVal" placeholder="请输入标题或内容" clearable></el-input>
      <label style="margin-left: 20px">类型</label>
      <el-cascader filterable v-model="typeId" :options="options" placeholder="请选择类型" clearable></el-cascader>
      <label style="margin-left: 20px">发布时间</label>
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
      <el-table
        :data="tableData"
        class="tableInfo"
        border
        style="width: 100%"
        height="690"
        v-loading="loading"
        :expand-row-keys="expandList"
        :row-key="getRowKey"
        @expand-change="getListData"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-table
              border
              class="tableInfo"
              v-loading="loadingInner"
              :data="listData[props.row.id] ? listData[props.row.id] : []"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="200"></el-table-column>
              <el-table-column prop="personName" label="姓名" width="350"></el-table-column>
              <el-table-column prop="phoneNumber" label="联系方式" width="350"></el-table-column>
              <el-table-column label="状态" width="350">
                <template slot-scope="scope">
                  <span style="color: #85ce61" v-if="scope.row.statusId == '0'">{{ scope.row.status }}</span>
                  <span style="color: red" v-else>{{ scope.row.status }}</span>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="mesName" label="标题"></el-table-column>
        <el-table-column prop="relPerson" label="发布人"></el-table-column>
        <el-table-column prop="relTime" label="发布时间"></el-table-column>
        <el-table-column prop="typeName" label="类型"></el-table-column>
        <el-table-column label="成功数">
          <template slot-scope="scope">
            <span style="color: #85ce61">{{ scope.row.successCount }}</span>
          </template>
        </el-table-column>
        <el-table-column label="失败数">
          <template slot-scope="scope">
            <span style="color: red">{{ scope.row.failCount }}</span>
          </template>
        </el-table-column>
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
// import Axios from 'axios'
export default {
  name: 'ReleaseLog',
  data() {
    return {
      options: [],
      inputVal: '',
      searchReleaseTime: [],
      typeId: '',

      loading: false,
      loadingInner: false,
      expandList: [],
      tableData: [],
      listData: {},
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0 // 总条数
    }
  },
  mounted() {
    this.getTypeList()
    this.queryLogList()
  },
  methods: {
    // 获取类型
    getTypeList() {
      let url = '/gtw/EIMS-management/get-mes-ype-tree'
      Axios.get(url).then((res) => {
        this.options = res.data.data
      })
    },
    getRowKey(row) {
      return row.id
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryLogList()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryLogList()
    },
    queryLogList() {
      this.loading = true
      let params = {
        pageNum: this.currentPage, // 分页
        pageSize: this.pageSize
      }
      if (this.inputVal) {
        params.text = this.inputVal
      }
      if (this.typeId) {
        params.typeId = this.typeId.toString()
      }
      if (Array.isArray(this.searchReleaseTime)) {
        params.relBeginTime = this.searchReleaseTime[0]
        params.relEndTime = this.searchReleaseTime[1]
      }
      // // console.log('获取我的信息列表--参数: ', params)
      let url = '/gtw/EIMS-management/get-mes-rel-log'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    async getListData(row) {
      // console.log('获取子级信息row', row)
      this.loadingInner = true
      let url = '/gtw/EIMS-management/get-mes-rel-status?id=' + row.id
      Axios.get(url)
        .then((r) => {
          this.loadingInner = false
          this.listData[row.id] = r.data.data.list
          // console.log(r.data.data.list)
          return true
        })
        .catch(() => {
          return false
        })
    },
    // 查询
    querySearch() {
      this.currentPage = 1
      this.queryLogList()
    }
  }
}
</script>
<style lang="scss">
.ReleaseLog {
  .headerTop {
    width: 100%;
    margin-bottom: 20px;
    height: 140px;
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
    width: 100%;
    height: calc(100% - 160px);
    background: #ffffff;
    padding: 0px 20px 10px 20px;
    box-sizing: border-box;
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
