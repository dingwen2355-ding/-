<template>
  <div class="materialUse">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="物资名称:">
        <el-input clearable v-model="searchForm.materialName" style="width: 160px;" placeholder="请输入物资名称"></el-input>
      </el-form-item>
      <el-form-item label="出入库类型:">
        <el-select filterable v-model="searchForm.type" style="width: 160px;" placeholder="请选择出入库类型" clearable>
          <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="物资来源:">
        <el-input clearable v-model="searchForm.source" style="width: 160px;" placeholder="请输入物资来源"></el-input>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" size="small" type="primary" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" size="small" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <el-button icon="el-icon-download" type="success" size="small" @click="exportData">导出</el-button>
        </div>
        <div class="content-right">
        </div>
      </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        height="800"
        v-loading="loading"
        ref="multipleTable"
        @select="handleSelectionChange"
        @select-all="handleSelectionChangeAll"
      >
        <el-table-column align="center" header-align="center" type="selection" width="55"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffName" label="物资名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="typeName" label="物资类型" width="90"></el-table-column>
        <el-table-column align="center" header-align="center" prop="num" label="数量" width="110"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffUnit" label="单位" width="110"></el-table-column>
        <el-table-column align="center" header-align="center" label="出入库类型" width="110">
          <template slot-scope="scope">
            <span v-if="scope.row.type == '1'">入库</span>
            <span v-else-if="scope.row.type == '2'">出库</span>
            <span v-else>{{ scope.row.type }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column align='center' header-align='center'  prop="" label="库存数量"></el-table-column> -->
        <el-table-column align="center" header-align="center" prop="note" label="备注"></el-table-column>
        <el-table-column align="center" header-align="center" prop="info" label="物资来源/领用人员" width="150"></el-table-column>
        <el-table-column align="center" header-align="center" prop="phone" label="电话" width="120"></el-table-column>
        <!-- <el-table-column align='center' header-align='center'  label="操作" width="90">
                <template slot-scope="scope">
                    <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium" icon="el-icon-view"></el-button>
                    <el-button @click="handleEdit(scope.row, 0)" type="text" size="medium" icon="el-icon-edit-outline"></el-button>
                    <el-button @click="handleDelete(scope.row)" type="text" size="medium" icon="el-icon-delete"></el-button>
                </template>
        </el-table-column>-->
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
  </div>
</template>
<script>
import request from '@/utils/request'
import qs from 'qs'
export default {
  name: 'materialUse',
  data() {
    return {
      typeList: [
        { id: '1', name: '入库' },
        { id: '2', name: '出库' }
      ],
      searchForm: {
        materialName: '', // 物资名称
        type: '', // 出入库类型
        source: '' // 物资来源
      },
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],

      showAdd: false,
      formInfo: {},
      isCode: 0 // 0 编辑 1 查看详情 2 新增
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
    // table_index(index) {
    //     return (this.currentPage - 1) * this.pageSize + index + 1
    // },
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
      let url = '/binhuapis/resourceOperation/query?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.materialName) { // 物资名称
        url = url + '&stuffName=' + this.searchForm.materialName
      }
      if (this.searchForm.type) { // 类别
        url = url + '&type=' + this.searchForm.type
      }
      if (this.searchForm.source) { // 来源
        url = url + '&info=' + this.searchForm.source
      }
      if (this.searchForm.name) { // 避难场所名称
        url = url + '&siteName=' + this.searchForm.name
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
        materialName: '', // 物资名称
        type: '', // 物资型号
        kind: '', // 物资类别
        name: '' // 场所名称
      }
      this.currentPage = 1
      this.queryDataTable()
    },

    // 新增
    addData() {

    },

    // 选中的节点数据
    // handleSelectionChange(rows) {
    //   this.multipleSelection = rows
    //   // console.log('选中的节点数据: ', this.multipleSelection)
    // },

    // 删除
    deleteData() {
      if (this.multipleSelection.length > 0) {
        this.$confirm('此操作将删除所勾选的物资使用信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/resourceOperation/del-batch?id=${ids}`
          console.log('删除--url', url)
          // request.delete(url, ids).then((r) => {
          // if (r.data.code === 200) {
          //     this.$message.success('删除成功')
          // this.currentPage = 1
          // this.queryDataTable()
          // } else {
          //     this.$message(r.data.message)
          // }
          // })
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

    },

    // 删除
    handleDelete(row) {
      this.$confirm('此操作将删除所勾选的物资使用信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/binhuapis/resourceOperation/del?id=' + row.id
        console.log('删除--url', url)
        // request.delete(url).then(res => {
        //     if (res.data.code == 200) {
        //         this.$message.success('删除成功')
        //         this.currentPage = 1
        //         this.queryDataTable()
        //     } else {
        //         this.$message.error(res.data.message)
        //     }
        // })
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
      let url = '/binhuapis/resourceOperation/export?'
      // console.log('导出url', url, 'param', param)
      request.get(url + qs.stringify(param))
        .then((res) => {
          if (res.data.code === 200) {
            let fileUrl = res.data.data
            window.open(fileUrl, '_bank')
            this.$message.success('导出成功')
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
.materialUse {
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
