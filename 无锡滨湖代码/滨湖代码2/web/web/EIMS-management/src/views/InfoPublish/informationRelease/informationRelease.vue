<template>
  <div class="informationRelease">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="我的信息" name="first">
        <div class="headerTop">
          <label>模糊检索</label>
          <el-input v-model="infoForm.inputVal" placeholder="请输入标题或内容" clearable></el-input>
          <label style="margin-left: 20px">类型</label>
          <el-cascader filterable v-model="infoForm.typeId" :options="options" placeholder="请选择类型" clearable></el-cascader>
          <label style="margin-left: 20px">发布时间</label>
          <el-date-picker
            v-model="infoForm.searchReleaseTime"
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
          <el-table :data="tableData" class="tableInfo" border style="width: 100%" height="700" v-loading="loading">
            <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
            <el-table-column prop="mesName" label="标题"></el-table-column>
            <el-table-column prop="relPerson" label="发布人"></el-table-column>
            <el-table-column prop="relTime" label="发布时间"></el-table-column>
            <el-table-column prop="typeName" label="类型"></el-table-column>
            <el-table-column prop="remark" label="内容"></el-table-column>
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
      <el-tab-pane label="我的草稿" name="second">
        <div class="headerTop">
          <label>模糊检索</label>
          <el-input v-model="inputDraftVal" placeholder="请输入标题或内容" clearable></el-input>
          <el-button class="searchBtn" @click="queryDraftSearch()">查询</el-button>
        </div>
        <div class="contentBody">
          <div style="margin-bottom: 20px; float: right">
            <el-button type="primary" @click="addDraftInfo()">新增</el-button>
          </div>
          <el-table
            :data="tableDraftData"
            class="tableInfo"
            border
            style="width: 100%"
            height="640"
            v-loading="loading"
          >
            <el-table-column type="index" label="序号" width="70" :index="table_draft_index"></el-table-column>
            <el-table-column prop="mesName" label="标题"></el-table-column>
            <el-table-column prop="relTime" label="创建时间"></el-table-column>
            <el-table-column prop="typeName" label="类型"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button @click="handleEdit(scope.row)" type="text" size="medium">编辑</el-button>
                <el-button @click="handleSumbit(scope.row)" type="text" size="medium">提交</el-button>
                <el-button @click="handleDelete(scope.row)" type="text" size="medium">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination-body">
            <el-pagination
              background
              :current-page.sync="currentDraftPage"
              @size-change="handleDraftSizeChange"
              @current-change="handleDraftCurrentChange"
              :page-size="pageDraftSize"
              :page-sizes="[10, 20, 30, 40]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalDraft"
            ></el-pagination>
          </div>
        </div>
        <add-my-info-dialog
          :dialogVisible.sync="addMyInfoShow"
          :formInfo="myFormInfo"
          :isCode="isCode"
          @success="success"
        ></add-my-info-dialog>
      </el-tab-pane>
      <el-tab-pane label="我的流程" name="third">
        <div class="headerTop">
          <label>模糊检索</label>
          <el-input v-model="inputProcessVal" placeholder="请输入标题或状态" clearable></el-input>
          <el-button class="searchBtn" @click="queryProcessSearch()">查询</el-button>
        </div>
        <div class="contentBody">
          <el-table
            :data="tableProcessData"
            class="tableInfo"
            border
            style="width: 100%"
            height="700"
            v-loading="loading"
          >
            <el-table-column type="index" label="序号" width="70" :index="table_process_index"></el-table-column>
            <el-table-column prop="mesName" label="标题"></el-table-column>
            <el-table-column prop="typeName" label="类型"></el-table-column>
            <el-table-column prop="relTime" label="创建时间"></el-table-column>
            <el-table-column prop="process" label="状态"></el-table-column>
            <el-table-column label="当前处理人">
              <template slot-scope="scope">
                <span v-if="scope.row.status === '1'">{{ scope.row.createName }}</span>
                <span v-else>{{ scope.row.auditorName }}</span>
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
              :current-page.sync="currentProcessPage"
              @size-change="handleProcessSizeChange"
              @current-change="handleProcessCurrentChange"
              :page-size="pageProcessSize"
              :page-sizes="[10, 20, 30, 40]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalProcess"
            ></el-pagination>
          </div>
        </div>
        <my-process-view-dialog :dialogVisible.sync="showProcessView" :formInfo="myProcessFormInfo"></my-process-view-dialog>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Axios from 'axios'
import znvTime from '../../../utils/znvTime'
import AddMyInfoDialog from './optionComponents/MyInfoOption/addMyInfoDialog.vue'
import myProcessViewDialog from './optionComponents/myProcessViewDialog.vue'
export default {
  components: { AddMyInfoDialog, myProcessViewDialog },
  name: 'informationRelease',
  data() {
    return {
      activeName: 'first',
      options: [],
      loading: false,

      // 我的信息
      infoForm: {
        inputVal: '',
        searchReleaseTime: [],
        typeId: ''
      },
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数

      // 我的草稿
      inputDraftVal: '',
      tableDraftData: [],
      currentDraftPage: 1, // 当前页数
      pageDraftSize: 10, // 每页条数
      totalDraft: 0, // 总条数
      addMyInfoShow: false,
      myFormInfo: {},
      isCode: 0,
      // 我的流程
      inputProcessVal: '',
      tableProcessData: [],
      currentProcessPage: 1, // 当前页数
      pageProcessSize: 10, // 每页条数
      totalProcess: 0, // 总条数
      showProcessView: false,
      myProcessFormInfo: {}
    }
  },

  mounted() {
    this.queryMyInfoList()
    this.getTypeList()
  },
  methods: {
    handleClick(tab, event) {
      // console.log(tab, event)
      if (tab.name == 'first') {
        this.queryMyInfoList()
      }
      if (tab.name == 'second') {
        this.queryMyDraftList()
      }
      if (tab.name == 'third') {
        this.queryMyProcessList()
      }
    },
    // 获取类型
    getTypeList() {
      let url = '/gtw/EIMS-management/get-mes-ype-tree'
      Axios.get(url).then((res) => {
        this.options = res.data.data
      })
    },

    // 我的信息
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleSizeChange(val) {
      this.currentPage = 1
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryMyInfoList()
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryMyInfoList()
    },
    // 获取我的信息列表
    queryMyInfoList() {
      this.loading = true
      let params = {
        pageNum: this.currentPage, // 分页
        pageSize: this.pageSize
      }
      if (this.infoForm.inputVal) {
        params.text = this.infoForm.inputVal
      }
      if (this.infoForm.typeId) {
        params.typeId = this.infoForm.typeId.toString()
      }
      if (Array.isArray(this.infoForm.searchReleaseTime)) {
        params.relBeginTime = this.infoForm.searchReleaseTime[0]
        params.relEndTime = this.infoForm.searchReleaseTime[1]
      }
      // console.log('获取我的信息列表--参数: ', params)
      let url = '/gtw/EIMS-management/get-mes-info'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    // 查询
    querySearch() {
      this.currentPage = 1
      this.queryMyInfoList()
    },

    // 我的草稿
    table_draft_index(index) {
      return (this.currentDraftPage - 1) * this.pageDraftSize + index + 1
    },
    handleDraftSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.currentDraftPage = 1
      this.pageDraftSize = val
      this.queryMyDraftList()
    },
    handleDraftCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentDraftPage = val
      this.queryMyDraftList()
    },
    // 获取我的草稿列表
    queryMyDraftList() {
      this.loading = true
      let params = {
        pageNum: this.currentDraftPage, // 分页
        pageSize: this.pageDraftSize,
        status: '0' // 信息状态：0：草稿 1：待审核 2：通过 3: 退回，可传多个用逗号分隔
      }
      if (this.inputDraftVal) {
        params.text = this.inputDraftVal
      }
      // console.log('获取我的信息列表--参数: ', params)
      let url = '/gtw/EIMS-management/get-mes-info'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableDraftData = res.data.data.list
        this.totalDraft = res.data.data.total
      })
    },
    // 查询
    queryDraftSearch() {
      this.currentDraftPage = 1
      this.queryMyDraftList()
    },
    // 新增
    addDraftInfo() {
      this.addMyInfoShow = true
      this.myFormInfo = {
        id: '',
        mesName: '',
        typeId: '',
        jsonObject: {},
        // relId: '',
        relObjectName: [],
        relPerson: '',
        relTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'),
        remark: ''
      }
      this.isCode = 2
    },
    success() {
      this.currentDraftPage = 1
      this.queryMyDraftList()
    },
    // 编辑
    handleEdit(row) {
      // console.log('编辑row', row)
      this.addMyInfoShow = true
      this.isCode = 0
      if (row.typeId && !Array.isArray(row.typeId)) {
        row.typeId = row.typeId.split(',')
      }
      this.myFormInfo = row
      // console.log('编辑this.myFormInfo', this.myFormInfo)
    },
    // 提交
    handleSumbit(row) {
      // console.log('提交row', row)
      this.$confirm('此操作将提交我的草稿, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management/audit-mes-info'
          let params = {
            id: row.id, // 信息id
            status: '1' // 提交：1
          }
          // console.log('参数', params, url)
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('提交成功')
                this.currentDraftPage = 1
                this.queryMyDraftList()
              } else {
                this.$message.error('提交失败')
              }
            })
            .catch(() => {
              this.$message.error('提交失败')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          })
        })
    },
    // 删除
    handleDelete(row) {
      // console.log('删除row', row)
      this.$confirm('此操作将删除我的草稿, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/gtw/EIMS-management//del-mes-info?id=' + row.id
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.currentDraftPage = 1
              this.queryMyDraftList()
            } else {
              this.$message.error('删除失败')
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

    // 我的流程
    table_process_index(index) {
      return (this.currentProcessPage - 1) * this.pageProcessSize + index + 1
    },
    handleProcessSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.currentProcessPage = 1
      this.pageProcessSize = val
      this.queryMyProcessList()
    },
    handleProcessCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentProcessPage = val
      this.queryMyProcessList()
    },
    // 获取我的流程列表
    queryMyProcessList() {
      this.loading = true
      let params = {
        text: '', // 标题或者状态  状态分：流程中和结束
        pageNum: this.currentProcessPage, // 分页
        pageSize: this.pageProcessSize // 分页
      }
      if (this.inputProcessVal) {
        params.text = this.inputProcessVal
      }
      let url = '/gtw/EIMS-management/get-mes-info-process'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableProcessData = res.data.data.list
        this.totalProcess = res.data.data.total
      })
    },
    // 查询
    queryProcessSearch() {
      this.currentProcessPage = 1
      this.queryMyProcessList()
    },
    // 详情
    handleView(row) {
      // console.log('详情row', row)
      this.showProcessView = true
      if (row.typeId && !Array.isArray(row.typeId)) {
        row.typeId = row.typeId.split(',')
      }
      this.myProcessFormInfo = row
    }
  }
}
</script>

<style lang="scss">
.informationRelease {
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
