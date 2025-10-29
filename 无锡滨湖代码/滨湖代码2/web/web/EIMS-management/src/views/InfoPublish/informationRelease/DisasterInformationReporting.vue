<template>
  <div class="DisasterInformationReporting">
    <div class="headerTop">
      <label>模糊检索</label>
      <el-input v-model="inputVal" placeholder="请输入标题或内容" clearable></el-input>

      <label style="margin-left: 20px">报送时间</label>
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
        <el-button type="primary" icon="el-icon-plus" @click="addData()">新增</el-button>
      </div>
      <el-table :data="tableData" class="tableInfo" border style="width: 100%" height="700" v-loading="loading">
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="disName" label="标题"></el-table-column>
        <el-table-column prop="typeName" label="类型"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <div style="color: red" v-if="scope.row.status === '0'">未报送</div>
            <div style="color: #85ce61" v-else>已报送</div>
          </template>
        </el-table-column>
        <el-table-column prop="repPrecinctName" label="报送科室"></el-table-column>
        <el-table-column prop="repPersonName" label="报送人"></el-table-column>
        <el-table-column prop="repTime" label="报送时间"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <div v-if="scope.row.edit == 1 || scope.row.status === '1'">
              <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium">详情</el-button>
            </div>
            <div v-else>
              <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium">详情</el-button>
              <el-button @click="handleEdit(scope.row, 0)" type="text" size="medium">编辑</el-button>
              <el-button @click="handleSubmit(scope.row)" type="text" size="medium">报送</el-button>
              <el-button @click="handleDelete(scope.row)" type="text" size="medium">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        :current-page.sync="currentPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :page-sizes="[10, 20, 30, 40]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </div>
    <add-disaster-report-dialog
      :dialogVisible.sync="showDisasterDialog"
      :formInfo="disasterFormInfo"
      @success="success"
    ></add-disaster-report-dialog>
  </div>
</template>

<script>
import addDisasterReportDialog from './optionComponents/DisasterRepotOption/addDisasterReportDialog.vue'
import Axios from 'axios'
import znvTime from '../../../utils/znvTime'

export default {
  components: { addDisasterReportDialog },
  name: 'DisasterInformationReporting',
  data() {
    return {
      inputVal: '',
      searchReleaseTime: [],

      tableData: [],
      loading: false,
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      userName: '', // 报送人
      streetName: '', // 报送科室
      showDisasterDialog: false,
      disasterFormInfo: {}
    }
  },
  mounted() {
    this.queryReportingList()
    // this.userName = localStorage.getItem('user')
    // this.streetName = localStorage.getItem('streetName')
    // console.log('this.streetName::', this.streetName)
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleSizeChange(val) {
      this.currentPage = 1
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryReportingList()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryReportingList()
    },
    queryReportingList() {
      let params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        repBeginTime: '',
        repEndTime: '',
        text: '',
        userId: localStorage.getItem('userId')
      }
      if (this.inputVal) {
        params.text = this.inputVal
      }
      if (Array.isArray(this.searchReleaseTime)) {
        params.repBeginTime = this.searchReleaseTime[0]
        params.repEndTime = this.searchReleaseTime[1]
      }
      let url = '/gtw/EIMS-management/get-disaster'
      Axios.post(url, params).then((res) => {
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    // 查询
    querySearch() {
      this.currentPage = 1
      this.queryReportingList()
    },
    // 新增
    addData() {
      this.showDisasterDialog = true
      this.disasterFormInfo = {
        disName: '',
        typeId: '',
        submitItem: [],
        repPersonName: '',
        repTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'),
        remark: '',
        eventBeans: [],
        warnBeans: [],
        disasterDataBeans: []
      }
      this.$root.eventBus.$emit('isCode', 2)
    },
    // 详情/编辑
    handleEdit(row, code) {
      // console.log('编辑row: ', row)
      let url = '/gtw/EIMS-management/get-disaster-data?id=' + row.id
      Axios.get(url).then((res) => {
        let typeId = []
        if (res.data.data.typeId && !Array.isArray(res.data.data.typeId)) {
          typeId = res.data.data.typeId.split(',')
        }
        this.disasterFormInfo = {
          id: res.data.data.id,
          disName: res.data.data.disName,
          typeId: typeId,
          submitItem: res.data.data.repPrecinctId,
          repPersonName: res.data.data.repPersonName,
          repTime: res.data.data.repTime,
          remark: res.data.data.remark,
          disasterDataBeans: res.data.data.disasterDataBeans,
          eventBeans: res.data.data.eventBeans,
          warnBeans: res.data.data.warnBeans
        }
        this.$root.eventBus.$emit('isCode', code)
        this.showDisasterDialog = true
      })
    },
    success() {
      this.currentPage = 1
      this.queryReportingList()
    },
    // 报送
    handleSubmit(row) {
      // console.log('报送row: ', row)
      this.$confirm('此操作将报送该灾情, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/rep-disaster?id=' + row.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('报送成功')
              this.currentPage = 1
              this.queryReportingList()
            } else {
              this.$message.error(res.data.message)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消报送'
          })
        })
    },
    // 删除
    handleDelete(row) {
      // console.log('删除row: ', row)
      this.$confirm('此操作将删除该信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/del-disaster?id=' + row.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.currentPage = 1
              this.disasterFormInfo = {
                disName: '',
                typeId: '',
                submitItem: [],
                repPersonName: '',
                repTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'),
                remark: '',
                eventBeans: [],
                warnBeans: [],
                disasterDataBeans: []
              }
              this.queryReportingList()
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
.DisasterInformationReporting {
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
    height: calc(100% - 80px);
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
  }
}
</style>
