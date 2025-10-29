<template>
  <div class="ReleaseWay">
    <div class="headerTop">
      <label>模糊检索</label>
      <el-input v-model="inputVal" placeholder="请输入名称/接入协议" clearable></el-input>
      <el-button class="searchBtn" @click="querySearch()">查询</el-button>
    </div>

    <div class="contentBody">
      <div class="content-top">
        <div class="top-title">查询表格</div>
        <el-button type="primary" icon="el-icon-plus" @click="addData()">新增</el-button>
      </div>

      <el-table :data="tableData" border style="width: 100%" height="700" v-loading="loading">
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="relName" label="名称"></el-table-column>
        <el-table-column prop="typeName" label="接入协议"></el-table-column>
        <el-table-column prop="createName" label="创建人"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
            <el-button @click="handledelete(scope.row)" type="text" size="medium">删除</el-button>
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
    <add-release-way-dialog :dialogVisible.sync="showAdd" :formInfo="wayFormInfo" :isCode="isCode" @success="success"></add-release-way-dialog>
  </div>
</template>

<script>
import Axios from 'axios'
import addReleaseWayDialog from './optionComponents/addReleaseWayDialog.vue'
export default {
  name: 'ReleaseWay',
  components: { addReleaseWayDialog },
  data() {
    return {
      loading: false,
      inputVal: '',
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      showAdd: false,
      wayFormInfo: {},
      isCode: 0 // 0 编辑 2 新增
    }
  },

  mounted() {
    this.queryWayList()
  },
  methods: {
    querySearch() {
      this.currentPage = 1
      this.queryWayList()
    },
    // 查询
    queryWayList() {
      this.loading = true
      let url = '/gtw/EIMS-management/get-mes-rel?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.inputVal) {
        url = url + '&text=' + this.inputVal
      }
      Axios.get(url).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },

    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 新增
    addData() {
      this.showAdd = true
      this.wayFormInfo = {
        id: '',
        relName: '',
        typeId: '',
        createName: '',
        remark: ''
      }
      this.isCode = 2
    },

    // 编辑
    handleEdit(row) {
      // console.log('编辑row: ', row)
      this.isCode = 0
      this.showAdd = true
      this.wayFormInfo = row
    },

    success() {
      this.currentPage = 1
      this.queryWayList()
    },

    // 删除
    handledelete(row) {
      // console.log('删除row: ', row)
      this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/del-mes-rel?id=' + row.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.currentPage = 1
              this.inputVal = ''
              this.queryWayList()
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
    },

    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.currentPage = 1
      this.pageSize = val
      this.queryWayList()
    },

    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryWayList()
    }
  }
}
</script>
<style lang="scss">
.ReleaseWay {
  // background: transparent !important;
  // box-sizing: border-box;
  // padding: 0 !important;

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
