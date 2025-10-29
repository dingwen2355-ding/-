<template>
  <div class="eventQuery">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="事故标题:">
        <el-input clearable v-model="searchForm.eventTitle" style="width: 175px" placeholder="请输入事故标题"></el-input>
      </el-form-item>
      <el-form-item label="事故类型:">
        <el-select v-model="searchForm.typeName" placeholder="请输入事故类型" clearable>
          <el-option v-for="item in typeList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事故来源:">
        <el-select filterable v-model="searchForm.sourceName" placeholder="请选择事故来源" clearable>
          <el-option v-for="item in sourceList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在区:">
        <el-input clearable v-model="searchForm.communityName" style="width: 160px" placeholder="请输入所在区"></el-input>
      </el-form-item>
      <el-form-item label="所在街道:">
        <el-input clearable v-model="searchForm.streetName" style="width: 160px" placeholder="请输入所在街道"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <!-- <el-button icon="el-icon-download" @click="inputData">导入</el-button> -->
          <el-button icon="el-icon-upload2" @click="exportData">导出</el-button>
          <!--<el-button icon="el-icon-delete" @click="deleteData">删除</el-button>-->
        </div>
        <div class="content-right">
          <!--<el-button icon="el-icon-plus" @click="addData">新增记录</el-button>-->
          <!-- <el-button type="text" icon="el-icon-search" @click="onSubmit"></el-button>
                    <el-button type="text" icon="el-icon-refresh" @click="resetSearch"></el-button>
          <el-button type="text" icon="el-icon-s-unfold"></el-button>-->
        </div>
      </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        height="740"
        v-loading="loading"
        ref="multipleTable"
        @select="handleSelectionChange"
        @select-all="handleSelectionChangeAll"
      >
        <el-table-column align="center" header-align="center" type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column label="事故标题" width="180">
          <template slot-scope="scope">
            <el-link type="primary" @click="handleEdit(scope.row, 1)">{{ scope.row.eventTitle }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="communityName" label="所在区"></el-table-column>
        <el-table-column prop="streetName" label="所在街道" width="80"></el-table-column>
        <el-table-column prop="eventTime" label="事发时间"></el-table-column>
        <el-table-column prop="typeName" label="事故类型"></el-table-column>
        <el-table-column prop="occurAddress" label="事发详细地址"></el-table-column>
        <el-table-column prop="summary" label="事故概况"></el-table-column>
        <el-table-column prop="sourceName" label="事故来源"></el-table-column>
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
    <add-dialog :dialogVisible.sync="showAdd" :formInfo="formInfo" :isCode="isCode" @success="success"></add-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import addDialog from './option/addDialog.vue'
import qs from 'qs'
export default {
  components: { addDialog },
  name: 'eventQuery',
  data() {
    return {
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
      isCode: 0 // 0 编辑 2 新增
    }
  },
  mounted() {
    this.queryDataTable()
    this.getTypeList()
    this.getSourceList()
  },
  methods: {
    // 回显
    showTag() {
      let handleSelectData = this.multipleSelection || []
      console.log(handleSelectData, 'handleSelectData')
      for (let i = 0; i < this.tableData.length; i++) {
        for (let j = 0; j < handleSelectData.length; j++) {
          if (this.tableData[i].id === handleSelectData[j].id) {
            this.$nextTick().then(() => {
              this.$refs.multipleTable.toggleRowSelection(this.tableData[i], true)
            })
          }
        }
      }
    },
    // 单选
    handleSelectionChange(val, row) {
      if (val.includes(row)) {
        this.multipleSelection.push(row)
      } else {
        let index = -1
        this.multipleSelection.some((e, i) => {
          if (e.id === row.id) {
            index = i
            return true
          }
        })
        this.multipleSelection.splice(index, 1)
      }
    },
    // 全选
    handleSelectionChangeAll(val) {
      if (val.length === 0) {
        this.tableData.forEach((t) => {
          let index = -1
          this.multipleSelection.some((e, i) => {
            if (e.id === t.id) {
              index = i
              return true
            }
          })
          this.multipleSelection.splice(index, 1)
        })
      } else {
        val.forEach((e) => {
          let ids = this.multipleSelection.map((m) => m.id)
          if (!ids.includes(e.id)) {
            this.multipleSelection.push(e)
          }
        })
      }
    },
    // 来源
    getSourceList() {
      let url = '/binhuapis/dict/dictTree?parentId=9'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.sourceList = data.filter(item => item.dictName === '突发事件' || item.dictName === '演练事件')
        }
      })
    },
    getTypeList() {
      let url = '/binhuapis/dict/dictTree?parentId=1'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.typeList = data
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
      let url = '/binhuapis/event/query-condition?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.eventTitle) {
        url = url + '&eventTitle=' + this.searchForm.eventTitle
      }
      if (this.searchForm.typeName) {
        url = url + '&typeName=' + this.searchForm.typeName
      }
      if (this.searchForm.sourceName) {
        url = url + '&sourceName=' + this.searchForm.sourceName
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
        this.showTag()
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

    // // 选中的节点数据
    // handleSelectionChange(rows) {
    //   this.multipleSelection = rows
    //   // console.log('选中的节点数据: ', this.multipleSelection)
    // },

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
            let url = `/binhuapis/event/del-batch?id=${ids}`
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
      this.showAdd = true
      if (code === 1) {
        // 1 -- 详情
        this.isCode = 1
      } else if (code === 0) {
        // 0 -- 编辑
        this.isCode = 0
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
          let url = '/binhuapis/event/del?id=' + row.id
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
    },

    // // 导入
    // inputData() {},

    // // 导出
    exportData() {
      let param = {}
      if (this.multipleSelection.length > 0) {
        let ids = this.multipleSelection.map((item) => {
          return item.id
        })
        param.ids = ids.join(',')
      }
      let url = '/binhuapis/event/export?'
      // console.log('导出url', url, 'param', param)
      request.get(url + qs.stringify(param))
        .then((res) => {
          if (res.data.code === 200) {
            let fileUrl = res.data.data
            window.open(fileUrl, '_bank')
            this.$message.success('导出成功')
            this.multipleSelection = []
          } else {
            this.$message({
              type: 'error',
              message: res.data.message
            })
          }
        })
        .catch(() => {
          this.$message.error('导出失败')
        })
    }
  }
}
</script>
<style lang="scss">
.eventQuery {
  width: 100%;
  height: calc(100% - 10px);
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
