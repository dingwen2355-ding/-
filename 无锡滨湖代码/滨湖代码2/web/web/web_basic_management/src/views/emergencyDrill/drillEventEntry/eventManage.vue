<template>
  <div class="drillEventEntry">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item>
        <el-input
          clearable
          v-model="searchForm.eventTitle"
          style="width: 220px"
          placeholder="请输入事故标题"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="searchForm.typeName"
          style="width: 220px"
          placeholder="请选择事故类型"
          clearable
        >
          <el-option
            v-for="item in typeList"
            :value="item.dictName"
            :label="item.dictName"
            :key="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          filterable
          v-model="searchForm.sourceName"
          style="width: 220px"
          placeholder="请选择事故来源"
          clearable
        >
          <el-option
            v-for="item in sourceList"
            :value="item.dictName"
            :label="item.dictName"
            :key="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          filterable
          v-model="searchForm.communityName"
          style="width: 220px"
          placeholder="请选择所在社区"
          clearable
        >
          <el-option
            v-for="item in communityList"
            :value="item.precinctName"
            :label="item.precinctName"
            :key="item.precinctId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          filterable
          v-model="searchForm.streetName"
          style="width: 220px"
          placeholder="请选择所在街道"
          clearable
        >
          <el-option
            v-for="item in streetList"
            :value="item.precinctName"
            :label="item.precinctName"
            :key="item.precinctId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">查询</el-button>
        <el-button size="mini" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <!-- <el-button type="text" >查询结果</el-button> -->
          <h4 style="color: #1c1c1c; font-size: 16px; margin: 0">查询结果</h4>
        </div>
        <div class="content-right">
          <el-button
            icon="iconfont icon-button_xinjian"
            type="primary"
            size="mini"
            @click="addData"
          >新增记录</el-button>
          <el-button
            icon="iconfont icon-button_shanchu"
            type="danger"
            size="mini"
            @click="deleteData"
          >删除</el-button>
        </div>
      </div>
      <el-table
        :data="tableData"
        stripe
        ref="multipleTable"
        style="width: 100%"
        :height="tableHeight"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="eventTitle" label="事故标题" width="110"></el-table-column>
        <el-table-column prop="communityName" label="所在社区"></el-table-column>
        <el-table-column prop="streetName" label="所在街道" width="80"></el-table-column>
        <el-table-column prop="eventTime" label="事发时间"></el-table-column>
        <el-table-column prop="typeName" label="事故类型"></el-table-column>
        <el-table-column prop="occurAddress" label="事发详细地址"></el-table-column>
        <el-table-column prop="statusName" label="状态"></el-table-column>
        <el-table-column prop="sourceName" label="事故来源"></el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <!-- <el-button @click="handleEdit(scope.row, 0)" type="primary" plain size="mini">编辑</el-button> -->
            <el-button @click="handleEdit(scope.row, 1)" type="success" plain size="mini">详情</el-button>
            <!--            <el-button @click="handleDelete(scope.row)" type="text" size="medium" icon="el-icon-delete"></el-button>-->
          </template>
        </el-table-column>
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
    <add-dialog
      :dialogVisible.sync="showAdd"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    ></add-dialog>
    <verifyDialog
      :dialogVisible.sync="showVerify"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    ></verifyDialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import addDialog from './option/addEventDialog.vue'
import verifyDialog from './option/verifyDialog.vue'
import { regionPrecinctMixin } from './precinctRegionMixins.js'
export default {
  components: { addDialog, verifyDialog },
  name: 'drillEventEntry',
  mixins: [regionPrecinctMixin],
  data() {
    return {
      tableHeight: 0,
      kindList: [{ id: '工具', name: '工具' }],
      searchForm: {
        eventTitle: '',
        typeName: '',
        sourceName: '',
        communityName: '',
        streetName: ''
      },
      typeList: [],
      sourceList: [],
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],
      showAdd: false,
      formInfo: {},
      isCode: 0, // 0 编辑 2 新增
      showVerify: false
      // precinctList: [],
      // communityList: [],
      // streetList: []
    }
  },
  watch: {
    // 'searchForm.streetName'(val) {
    //   if (!val) return
    //   const [street] = this.precinctList.filter((item) => {
    //     return item.precinctName === this.searchForm.streetName
    //   })
    //   this.communityList = this.precinctList.filter((item) => item.up_precinct_id === street.precinctId)
    // },
    // 'searchForm.communityName'(val) {
    //   if (!val) return
    //   const [community] = this.precinctList.filter((item) => {
    //     return item.precinctName === this.searchForm.communityName
    //   })
    //   const [street] = this.precinctList.filter((item) => item.precinctId === community.up_precinct_id)
    //   this.searchForm.streetName = street.precinctName
    // },
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 150)
    },
    sourceList(val) {
      this.queryDataTable()
    }
  },
  mounted() {
    this.getPrecinctList()
    this.getTypeList()
    this.getSourceList()
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 150
    })
  },
  methods: {
    // precinctKind：100-区 110-街道 120-社区 ;upPrecinctId： 上级id
    // getPrecinctList() {
    //   let url = '/binhuapis/regional/precinct'
    //   request.get(url).then((res) => {
    //     if (res.data.code === 200) {
    //       let data = res.data.data || []
    //       this.precinctList = data
    //       this.communityList = this.precinctList.filter((item) => item.precinctKind === 120)
    //       this.streetList = this.precinctList.filter((item) => item.precinctKind === 110)
    //     }
    //   })
    // },
    // 来源
    getSourceList() {
      let url = '/binhuapis/dict/dictTree?parentId=9'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.sourceList = data.filter((item) => item.dictName !== '值班值守')
        }
      })
    },
    getTypeList() {
      let url = '/binhuapis/dict/dictTree?parentId=1'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          let sortModel = { 自然灾害: 1, 事故灾难: 2, 社会安全: 3, 公共卫生: 4, 其他: 5 }
          this.typeList = data
          this.typeList = this.typeList.sort((a, b) => {
            return sortModel[a.typeName] - sortModel[b.typeName]
          })
        }
      })
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
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
      let url = '/binhuapis/dapeng/event/query-condition?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.eventTitle) {
        url = url + '&eventTitle=' + this.searchForm.eventTitle
      }
      if (this.searchForm.typeName) {
        url = url + '&typeName=' + this.searchForm.typeName
      }
      if (this.searchForm.sourceName) {
        url = url + '&sourceName=' + this.searchForm.sourceName
      } else {
        const sourceids = this.sourceList.map((item) => item.id).join(',')
        url = url + '&sourceId=' + sourceids
      }
      if (this.searchForm.streetName) {
        url = url + '&streetName=' + this.searchForm.streetName
      }
      if (this.searchForm.communityName) {
        url = url + '&communityName=' + this.searchForm.communityName
      }
      request.get(url).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
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
        eventTitle: '',
        typeName: '',
        sourceName: '',
        communityName: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },

    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        eventTitle: '',
        typeName: '',
        streetName: '',
        communityName: '', // 数量
        levelName: '', // 单位
        eventTime: '',
        smallTypeName: '',
        impactLevel: '', // 所属场所Id
        monitorUnit: '', // 所属场所Id
        occurAddress: '',
        minorNum: '',
        seriousNum: '',
        deathNum: '',
        economicLoss: '', // 备注
        isConclusion: '',
        summary: '',
        isCommand: '',
        eventNodeList: '',
        lon: '',
        lat: '',
        file: ''
      }
      this.isCode = 2
    },

    success() {
      this.currentPage = 1
      this.queryDataTable()
    },

    // 选中的节点数据
    handleSelectionChange(rows) {
      this.multipleSelection = rows
      // console.log('选中的节点数据: ', this.multipleSelection)
    },

    // 删除
    deleteData() {
      if (this.multipleSelection.length > 0) {
        this.$confirm('此操作将删除所勾选的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let ids = this.multipleSelection
              .map((item) => {
                return item.id
              })
              .join(',')
            let url = `/binhuapis/dapeng/event/del-batch?ids=${ids}` + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
            // console.log('删除--url', url)
            request.get(url, ids).then((r) => {
              if (r.data.code === 200) {
                this.$message.success('删除成功')
                this.currentPage = 1
                this.queryDataTable()
              } else {
                this.$message(r.data.message)
              }
            })
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      } else {
        this.$message.warning('请勾选表格中的数据后再进行删除操作')
      }
    },

    // 编辑/详情
    handleEdit(row, code) {
      if (code === 1) {
        // 1 -- 详情
        this.isCode = 1
        this.showAdd = true
      } else if (code === 0) {
        // 0 -- 编辑
        this.isCode = 0
        this.showVerify = true
      }
      this.formInfo = row
    },

    // 删除
    handleDelete(row) {
      this.$confirm('此操作将删除所勾选的数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/binhuapis/dapeng/event/del?id=' + row.id
          // console.log('删除--url', url)
          request.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('删除成功')
              this.currentPage = 1
              this.queryDataTable()
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

    // // 导入
    // inputData() {},

    // // 导出
    // exportData() {}
  }
}
</script>
<style lang="scss">
.drillEventEntry {
  width: 100%;
  height: calc(100% - 10px);
  box-sizing: border-box;
  padding: 20px;
  background-color: #fff;
  .content-top {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    .content-right {
      margin-right: 20px;
    }
  }
}
</style>
