<template>
  <div class="drillReport">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="演练名称:">
        <el-input clearable v-model="searchForm.drillName" style="width: 175px;" placeholder="请输入演练名称"></el-input>
      </el-form-item>
      <el-form-item label="所属分类:">
        <el-input clearable v-model="searchForm.category" style="width: 170px;" placeholder="请输入所属分类"></el-input>
      </el-form-item>
      <el-form-item label="演练类型:">
        <el-input clearable v-model="searchForm.drillTypeName" style="width: 170px;" placeholder="请输入演练类型"></el-input>
      </el-form-item>
      <el-form-item label="主办单位:">
        <el-input clearable v-model="searchForm.organizer" style="width: 160px;" placeholder="请输入主办单位"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <el-button icon="el-icon-delete" @click="deleteData">删除</el-button>
          <el-button icon="el-icon-upload2" @click="exportData">导出</el-button>
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
        ref="multipleTable"
        @select="handleSelectionChange"
        @select-all="handleSelectionChangeAll"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="drillName" label="演练名称"></el-table-column>
        <el-table-column prop="drillTime" label="时间"></el-table-column>
        <el-table-column prop="address" label="地点"></el-table-column>
        <el-table-column prop="category" label="所属分类"></el-table-column>
        <el-table-column prop="drillForm" label="演练形式"></el-table-column>
        <el-table-column prop="drillTypeName" label="演练类型"></el-table-column>
        <el-table-column prop="leader" label="出席领导"></el-table-column>
        <el-table-column prop="organizer" label="主办单位"></el-table-column>
        <el-table-column prop="actUnit" label="主要参演单位"></el-table-column>
        <el-table-column prop="actNum" label="参演人数"></el-table-column>
        <el-table-column prop="team" label="出动队伍"></el-table-column>
        <el-table-column prop="equip" label="出动装备"></el-table-column>
        <el-table-column prop="drillSubject" label="主要演练科目"></el-table-column>
        <el-table-column prop="basis" label="参考依据"></el-table-column>
        <el-table-column prop="problem" label="存在问题"></el-table-column>
        <el-table-column prop="results" label="演练成果"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
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
import qs from 'qs'
export default {
  components: { addDialog },
  name: 'drillReport',
  data() {
    return {
      kindList: [
        { id: '工具', name: '工具' }
      ],
      searchForm: {
        name: '',
        kind: '',
        address: '',
        manageName: '',
        drillName: '',
        category: '',
        drillTypeName: '',
        organizer: ''
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
      let url = '/binhuapis/drillReport/query-condition?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.drillName) {
        url = url + '&drillName=' + this.searchForm.drillName
      }
      if (this.searchForm.category) {
        url = url + '&category=' + this.searchForm.category
      }
      if (this.searchForm.drillTypeName) {
        url = url + '&drillTypeName=' + this.searchForm.drillTypeName
      }
      if (this.searchForm.organizer) {
        url = url + '&organizer=' + this.searchForm.organizer
      }
      let userId = localStorage.getItem('userId')
      if (userId) {
        url = url + '&userId=' + userId
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
        name: '',
        kind: '',
        address: '',
        manageName: '',
        drillName: '',
        category: '',
        drillTypeName: '',
        organizer: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },

    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        id: '',
        drillName: '',
        drillTime: '',
        category: '',
        address: '',
        results: '', // 数量
        drillForm: '', // 单位
        drillTypeName: '',
        leader: '',
        organizer: '', // 所属场所Id
        actUnit: '', // 所属场所Id
        actNum: '',
        team: '',
        equip: '',
        drillSubject: '',
        basis: '',
        problem: '',
        remark: '' // 备注
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
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/drillReport/del-batch?ids=${ids}`
          // console.log('删除--url', url)
          request.get(url).then((r) => {
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
        let url = '/binhuapis/drillReport/del?id=' + row.id
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
      let url = '/binhuapis/drillReport/export?'
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
.drillReport {
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
