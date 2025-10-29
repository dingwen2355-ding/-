<template>
  <div class="applicationCapabilityEvaluation">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="评估表名称:">
        <el-input clearable v-model="searchForm.name" style="width: 175px;" placeholder="请输入评估表名称"></el-input>
      </el-form-item>
      <el-form-item label="关联事件:">
        <el-input clearable v-model="searchForm.eventTitle" style="width: 175px;" placeholder="请输入关联事件"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-download" >导出</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <el-button icon="el-icon-delete" @click="deleteData">删除</el-button>
        </div>
        <div class="content-right">
          <el-button icon="el-icon-plus" @click="addData">新增记录</el-button>
        </div>
      </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        height="740"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="name" label="评估名称" width="110"></el-table-column>
        <el-table-column prop="eventTitle" label="关联事件"></el-table-column>
        <el-table-column prop="prepareScore" label="应急准备得分"></el-table-column>
        <el-table-column prop="warningScore" label="预测预警得分"></el-table-column>
        <el-table-column prop="criminalScore" label="事件接报得分"></el-table-column>
        <el-table-column prop="responseScore" label="事件响应得分"></el-table-column>
        <el-table-column prop="commandScore" label="应急指挥得分"></el-table-column>
        <el-table-column prop="afterScore" label="善后处置得分"></el-table-column>
        <el-table-column prop="totalScore" label="总得分"></el-table-column>
        <el-table-column prop="assessTime" label="评估时间"></el-table-column>
        <el-table-column label="操作" width="90">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium" icon="el-icon-view"></el-button>
            <el-button @click="handleEdit(scope.row, 0)" type="text" size="medium" icon="el-icon-edit-outline"></el-button>
            <!--                    <el-button @click="handleDelete(scope.row)" type="text" size="medium" icon="el-icon-delete"></el-button>-->
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
    <add-dialog :dialogVisible.sync="showAdd" :formInfo="formInfo" :isCode="isCode" @success="success"></add-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import addDialog from './option/addDialog.vue'
export default {
  components: { addDialog },
  name: 'applicationCapabilityEvaluation',
  data() {
    return {
      kindList: [
        { id: '工具', name: '工具' }
      ],
      searchForm: {
        name: '',
        eventTitle: ''
      },

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
  },
  methods: {
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
      let url = '/binhuapis/assess/query-condition?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.name) {
        url = url + '&name=' + this.searchForm.name
      }
      if (this.searchForm.eventTitle) {
        url = url + '&eventTitle=' + this.searchForm.eventTitle
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
        name: '',
        eventTitle: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },

    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        eventTitle: '',
        prepareScore: '',
        warningScore: '',
        criminalScore: '', // 数量
        responseScore: '', // 单位
        afterScore: '',
        commandScore: ''
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
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/assess/del-batch?ids=${ids}`
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
        }).catch(() => {
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
      }).then(() => {
        let url = '/binhuapis/assess/del?id=' + row.id
        // console.log('删除--url', url)
        request.get(url).then(res => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.currentPage = 1
            this.queryDataTable()
          } else {
            this.$message.error(res.data.message)
          }
        })
      }).catch(() => {
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
.applicationCapabilityEvaluation {
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
