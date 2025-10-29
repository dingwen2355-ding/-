<template>
  <div class="protectiveTarget">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="防护名称:">
        <el-input
          clearable
          v-model="searchForm.protectionName"
          style="width: 175px;"
          placeholder="请输入防护名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="防护类型:">
        <el-select
          filterable
          v-model="searchForm.protectionType"
          placeholder="请选择防护类型"
          popper-append-to-body
          clearable
          style="width: 175px;"
        >
          <el-option
            v-for="item in protectionTypeList"
            :value="item.dictName"
            :label="item.dictName"
            :key="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button type="primary" size="mini" @click="onSubmit">查询</el-button>
        <el-upload
          class="btn2"
          ref="upload"
          action="/"
          size="mini"
          :file-list="fileList"
          :show-file-list="false"
          :on-change="handleChange"
          :auto-upload="false"
          style="display: inline-block; margin-left: 10px"
        >
          <el-button type="primary" size="mini" icon="el-icon-upload2">导入</el-button>
        </el-upload>
        <a download="防护目标模板" style="font-size: 14px" href="template/防护目标模板.xlsx" class="sel_btn">
          点击下载导入模板
          <i class="el-icon-download"></i>
        </a>
        <el-button
          type="success"
          size="mini"
          icon="el-icon-download"
          style="margin-left: 10px"
          @click="exportData"
        >导出</el-button>
        <el-button size="mini" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <h4 style="color: #1c1c1c; font-size: 16px; margin: 0">查询结果</h4>
          <!-- <el-button icon="el-icon-download" @click="templateDown">模板下载</el-button>
          <el-upload
            class="btn2"
            ref="upload"
            action="/"
            :file-list="fileList"
            :show-file-list="false"
            :on-change="handleChange"
            :auto-upload="false"
            style="display: inline-block"
            accept=".xlsx, .xls"
          >
            <el-button icon="el-icon-download">导入</el-button>
          </el-upload>
          <el-button icon="el-icon-upload2" @click="exportData">导出</el-button>-->
        </div>
        <div class="content-right">
          <el-button icon="el-icon-plus" type="primary" size="mini" @click="addData">新增记录</el-button>
          <el-button
            icon="el-icon-delete"
            type="danger"
            size="mini"
            @click="deleteData"
            :disabled="multipleSelection.length === 0"
          >删除</el-button>
          <!-- <el-button type="text" icon="el-icon-search" @click="onSubmit"></el-button>
                    <el-button type="text" icon="el-icon-refresh" @click="resetSearch"></el-button>
          <el-button type="text" icon="el-icon-s-unfold"></el-button>-->
        </div>
      </div>
      <el-table
        :data="tableData"
        highlight-current-row
        style="width: 100%"
        :height="tableHeight"
        v-loading="loading"
        ref="multipleTable"
        @select="handleSelectionChange"
        @select-all="handleSelectionChangeAll"
      >
        <el-table-column align="center" header-align="center" type="selection" width="55"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          type="index"
          label="序号"
          width="70"
          :index="table_index"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="protectionName"
          label="防护名称"
          width="110"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="protectionType"
          label="防护类型"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="destinationAddress"
          label="目标地址"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="managerName"
          label="管理人姓名"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="managerPhone"
          label="联系电话"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="lastInspection"
          label="最近巡查时间"
        ></el-table-column>
        <el-table-column align="center" header-align="center" label="操作" width="220">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row, 0)" type="primary" plain size="mini">编辑</el-button>
            <el-button @click="handleEdit(scope.row, 1)" type="success" plain size="mini">详情</el-button>
            <el-button @click="handleDelete(scope.row)" type="danger" plain size="mini">删除</el-button>
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
    <AddProtectiveTarget
      :dialogVisible.sync="showAdd"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    ></AddProtectiveTarget>
  </div>
</template>
<script>
import request from '@/utils/request'
import xlsx from 'xlsx'
import AddProtectiveTarget from './option/addProtectiveTarget.vue'
export default {
  name: 'protectiveTarget',
  components: { AddProtectiveTarget },
  data() {
    return {
      tableHeight: 0,
      searchForm: {
        protectionType: '',
        protectionName: ''
      },
      loading: false,
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      multipleSelection: [],
      fileList: [],
      file: {},
      showAdd: false,
      formInfo: {},
      isCode: 0, // 0 编辑 1 查看详情 2 新增
      protectionTypeList: []
    }
  },
  watch: {
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200)
    }
  },
  mounted() {
    this.queryDataTable()
    this.getProtectionTypeList()
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200
    })
  },
  methods: {
    // 导出
    exportData() {
      if (this.multipleSelection.length > 0) {
        let tempTable = []
        this.multipleSelection.forEach((item, index) => {
          tempTable.push({
            序号: index + 1,
            防护名称: item.protectionName,
            防护类型: item.protectionType,
            最近巡查时间: item.lastInspection,
            目标地址: item.destinationAddress,
            管理员姓名: item.managerName,
            管理员电话: item.managerPhone
          })
        })
        let ws = xlsx.utils.json_to_sheet(tempTable)
        let wb = xlsx.utils.book_new()
        // 设置列宽
        ws['!cols'] = [{ wch: 8 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 }]
        // 设置行高
        ws['!rows'] = [{ hpx: 25 }]
        this.multipleSelection.forEach(() => {
          ws['!rows'].push({ hpx: 25 })
        })
        xlsx.utils.book_append_sheet(wb, ws, 'Sheet1')
        xlsx.writeFile(wb, '防护目标.xlsx')
      } else {
        let url = '/binhuapis/Protection/queryProtectionList?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
        if (this.searchForm.protectionName) {
          url = url + '&protectionName=' + this.searchForm.protectionName
        }
        if (this.searchForm.protectionType) {
          url = url + '&protectionType=' + this.searchForm.protectionType
        }
        request.get(url).then((res) => {
          this.loading = false
          if (res.data.code === 200) {
            let allData = res.data.data.list
            let tempTable = []
            allData.forEach((item, index) => {
              tempTable.push({
                序号: index + 1,
                防护名称: item.protectionName,
                防护类型: item.protectionType,
                最近巡查时间: item.lastInspection,
                目标地址: item.destinationAddress,
                管理员姓名: item.managerName,
                管理员电话: item.managerPhone
              })
            })
            let ws = xlsx.utils.json_to_sheet(tempTable)
            let wb = xlsx.utils.book_new()
            // 设置列宽
            ws['!cols'] = [{ wch: 8 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 }, { wch: 25 }]
            // 设置行高
            ws['!rows'] = [{ hpx: 25 }]
            allData.forEach(() => {
              ws['!rows'].push({ hpx: 25 })
            })
            xlsx.utils.book_append_sheet(wb, ws, 'Sheet1')
            xlsx.writeFile(wb, '防护目标.xlsx')
          }
        })
      }
    },
    getProtectionTypeList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '65').then(res => {
        if (res.data.code === 200) {
          this.protectionTypeList = res.data.data.list
        }
      })
    },
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
    templateDown() {
      const link = document.createElement('a')
      link.href = '/mock/避难场所导入模板.xlsx'
      link.setAttribute('download', '避难场所导入模板.xlsx')
      document.body.appendChild(link)
      link.click()
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
      let url = '/binhuapis/Protection/queryProtectionList?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.protectionName) {
        url = url + '&protectionName=' + this.searchForm.protectionName
      }
      if (this.searchForm.protectionType) {
        url = url + '&protectionType=' + this.searchForm.protectionType
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
        protectionType: '',
        protectionName: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },
    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        protectionName: '',
        protectionType: '',
        lastInspection: '',
        destinationAddress: '',
        managerName: '',
        managerPhone: ''
      }
      this.isCode = 2
    },
    success() {
      this.currentPage = 1
      this.queryDataTable()
    },
    // 删除
    deleteData() {
      if (this.multipleSelection.length > 0) {
        this.$confirm('此操作将删除所勾选防护目标, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/Protection/deleteByPrimaryKey?ids=${ids}`
          // console.log('删除--url', url)
          request.post(url).then((r) => {
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
      this.$confirm('此操作将删除所选防护目标, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/binhuapis/Protection/deleteByPrimaryKey?ids=' + row.id
        // console.log('删除--url', url)
        request.post(url).then(res => {
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
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
      this.importExcel()
    },

    importExcel() {
      let url = '/binhuapis/Protection/importExcel'
      this.file = this.fileList[0]
      // this.$refs.upload.clearFiles()
      const types = this.file.name.split('.')[1]
      const fileType = ['xlsx', 'xlc', 'xlm', 'xls', 'xlt', 'xlw', 'csv'].some((item) => item === types)
      if (!fileType) {
        this.$message.warning('格式错误！请重新选择')
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      formData.append('type', 4)
      request
        .post(url, formData)
        .then((res) => {
          if (res.data.code === 201 || res.data.code === 200) {
            this.$message.success('导入成功')
            this.pageStart = 1
            this.queryDataTable()
            this.$refs.upload.clearFiles()
          } else {
            this.$message({
              type: 'error',
              message: res.data.message
            })
            this.$refs.upload.clearFiles()
          }
        })
        .catch((err) => {
          console.log(err)
          this.$message.error('导入失败')
        })
    }
  }
}
</script>
<style lang="scss">
.protectiveTarget {
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
