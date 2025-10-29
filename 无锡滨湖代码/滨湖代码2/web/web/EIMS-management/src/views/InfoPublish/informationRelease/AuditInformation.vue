<template>
  <div class="AuditInformation">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="待审核" name="first">
        <div class="headerTop">
          <label>模糊检索</label>
          <el-input v-model="inputVal" placeholder="请输入标题或内容" clearable></el-input>
          <el-button class="searchBtn" @click="querySearch()">查询</el-button>
        </div>
        <div class="contentBody">
          <el-table :data="tableData" class="tableInfo" border style="width: 100%" height="700" v-loading="loading">
            <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
            <el-table-column prop="mesName" label="标题"></el-table-column>
            <el-table-column prop="relPerson" label="发布人"></el-table-column>
            <el-table-column prop="relTime" label="创建时间"></el-table-column>
            <el-table-column prop="typeName" label="类型"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button @click="handleAudit(scope.row)" type="text" size="medium">审核</el-button>
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
      </el-tab-pane>
      <el-tab-pane label="已审核" name="second">
        <div class="headerTop">
          <label>模糊检索</label>
          <el-input v-model="inputAuditVal" placeholder="请输入标题或内容" clearable></el-input>
          <el-button class="searchBtn" @click="queryAuditSearch()">查询</el-button>
        </div>
        <div class="contentBody">
          <el-table
            :data="tableAuditData"
            class="tableInfo"
            border
            style="width: 100%"
            height="700"
            v-loading="loading"
          >
            <el-table-column type="index" label="序号" width="70" :index="table_audit_index"></el-table-column>
            <el-table-column prop="mesName" label="标题"></el-table-column>
            <el-table-column prop="relPerson" label="发布人"></el-table-column>
            <el-table-column prop="relTime" label="创建时间"></el-table-column>
            <el-table-column prop="processTime" label="处理时间"></el-table-column>
            <el-table-column prop="status" label="信息状态">
              <template slot-scope="scope">
                <span v-if="scope.row.status == '2'">通过</span>
                <span v-else-if="scope.row.status == '3'">退回</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button @click="handleView(scope.row)" type="text" size="medium">详情</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination-body">
            <el-pagination
              background
              :current-page.sync="currentAuditPage"
              @size-change="handleAuditSizeChange"
              @current-change="handleAuditCurrentChange"
              :page-size="pageAuditSize"
              :page-sizes="[10, 20, 30, 40]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalAudit"
            ></el-pagination>
          </div>
        </div>
      </el-tab-pane>
      <audit-info-dialog
        :dialogVisible.sync="showMyInfo"
        :formInfo="myInfoAuditFormInfo"
        @success="success"
        :isCode="isCode"
      ></audit-info-dialog>
    </el-tabs>
  </div>
</template>

<script>
import Axios from 'axios'
import auditInfoDialog from './optionComponents/auditInfoDialog.vue'
export default {
  components: { auditInfoDialog },
  name: 'AuditInformation',
  data() {
    return {
      activeName: 'first',
      loading: false,
      inputVal: '',
      inputAuditVal: '',
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      tableAuditData: [],
      currentAuditPage: 1, // 当前页数
      pageAuditSize: 10, // 每页条数
      totalAudit: 0, // 总条数
      showMyInfo: false,
      isCode: 1,
      myInfoAuditFormInfo: {}
    }
  },

  mounted() {
    this.queryTableList()
  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event)
      if (tab.name == 'first') {
        this.queryTableList()
      }
      if (tab.name == 'second') {
        this.queryAuditTableList()
      }
    },

    // 待审核
    queryTableList() {
      this.loading = true
      let params = {
        pageNum: this.currentPage, // 分页
        pageSize: this.pageSize,
        status: '1' // 信息状态：0：草稿 1：待审核 2：通过 3: 退回，可传多个用逗号分隔
      }
      if (this.inputVal) {
        params.text = this.inputVal
      }
      // console.log('获取我的信息列表--参数: ', params)
      let url = '/gtw/EIMS-management/get-mes-info'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 分页
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.querySearch()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.querySearch()
    },

    // 查询
    querySearch() {
      this.currentPage = 1
      this.queryTableList()
    },
    // 审核
    handleAudit(row) {
      // console.log('审核row', row)
      this.showMyInfo = true
      if (row.typeId && !Array.isArray(row.typeId)) {
        row.typeId = row.typeId.split(',')
      }
      this.myInfoAuditFormInfo = row
      this.isCode = 1
    },
    success() {
      this.currentPage = 1
      this.queryTableList()
    },

    // 已审核
    queryAuditTableList() {
      this.loading = true
      let params = {
        pageNum: this.currentAuditPage, // 分页
        pageSize: this.pageAuditSize,
        status: '2,3' // 信息状态：0：草稿 1：待审核 2：通过 3: 退回，可传多个用逗号分隔
      }
      if (this.inputAuditVal) {
        params.text = this.inputAuditVal
      }
      // console.log('获取我的信息列表--参数: ', params)
      let url = '/gtw/EIMS-management/get-mes-info'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableAuditData = res.data.data.list
        this.totalAudit = res.data.data.total
      })
    },
    table_audit_index(index) {
      return (this.currentAuditPage - 1) * this.pageAuditSize + index + 1
    },
    // 分页
    handleAuditSizeChange(val) {
      this.currentAuditPage = 1
      // console.log(`每页 ${val} 条`)
      this.pageAuditSize = val
      this.queryAuditTableList()
    },
    handleAuditCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentAuditPage = val
      this.queryAuditTableList()
    },

    // 查询
    queryAuditSearch() {
      this.currentAuditPage = 1
      this.queryAuditTableList()
    },

    // 详情
    handleView(row) {
      // console.log('详情row', row)
      this.showMyInfo = true
      if (row.typeId && !Array.isArray(row.typeId)) {
        row.typeId = row.typeId.split(',')
      }
      this.myInfoAuditFormInfo = row
      this.isCode = 2
    }
  }
}
</script>

<style lang="scss">
.AuditInformation {
  .headerTop {
    width: 100%;
    margin-bottom: 10px;
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
