<template>
  <div class="lawsAndRegulations">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="法律名称:">
        <el-input clearable v-model="searchForm.lawName" style="width: 175px;" placeholder="请输入法律名称"></el-input>
      </el-form-item>
      <el-form-item label="事故类型:">
        <el-select
          filterable
          v-model="searchForm.lawClass"
          placeholder="请选择事故类型"
          popper-append-to-body
          clearable
          style="width: 175px;"
        >
          <el-option v-for="item in lawClassList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
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
        <el-table-column align="center" header-align="center" type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="lawName"
          label="法律名称"
          width="110"
        ></el-table-column>
        <el-table-column align="center" header-align="center" show-overflow-tooltip prop="lawClass" label="法律分类"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="illegalDescription"
          label="违法行为描述"
        ></el-table-column>
        <el-table-column align="center" header-align="center" show-overflow-tooltip prop="legalProvisions" label="法律规定"></el-table-column>
        <el-table-column align="center" header-align="center" show-overflow-tooltip prop="prescribedTerms" label="规定条款"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          show-overflow-tooltip
          prop="penaltyBasisClause"
          label="处罚依据条款"
        ></el-table-column>
        <el-table-column align="center" header-align="center" show-overflow-tooltip prop="accidentType" label="事故类型"></el-table-column>
        <el-table-column align="center" header-align="center" label="操作" width="220">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row, 1)" type="success" plain size="mini">详情</el-button>
            <el-button @click="handleEdit(scope.row, 0)" type="primary" plain size="mini">编辑</el-button>
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
    <AddLawsAndRegulations :dialogVisible.sync="showAdd" :formInfo="formInfo" :isCode="isCode" @success="success"></AddLawsAndRegulations>
  </div>
</template>
<script>
import request from '@/utils/request'
import AddLawsAndRegulations from './option/addLawsAndRegulations.vue'
import qs from 'qs'
export default {
  name: 'lawsAndRegulations',
  components: { AddLawsAndRegulations },
  data() {
    return {
      tableHeight: 0,
      searchForm: {
        lawName: '',
        lawClass: ''
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
      lawClassList: []
    }
  },
  watch: {
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200)
    }
  },
  mounted() {
    this.queryDataTable()
    this.getLawClassList()
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200
    })
  },
  methods: {
    getLawClassList() {
      request.get('/binhuapis/dict/query-condition?parentId=' + '102').then(res => {
        if (res.data.code === 200) {
          this.lawClassList = res.data.data.list
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
      let url = '/binhuapis/Law/queryLawList?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.lawName) {
        url = url + '&lawName=' + this.searchForm.lawName
      }
      if (this.searchForm.lawClass) {
        url = url + '&accidentType=' + this.searchForm.lawClass
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
        lawName: '',
        lawClass: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },
    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        lawName: '',
        lawClass: '',
        illegalDescription: '',
        legalProvisions: '',
        prescribedTerms: '',
        penaltyBasisClause: '',
        penaltyBasisName: '',
        penaltyBasis: '',
        standards: '',
        accidentType: ''
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
        this.$confirm('此操作将删除所勾选的法律法规, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/Law/deleteLaw?ids=${ids}` + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
          // console.log('删除--url', url)
          request.post(url, ids).then((r) => {
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
      this.$confirm('此操作将删除所选的法律法规, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/binhuapis/Law/deleteLaw?ids=' + row.id + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
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
      this.file = this.fileList[0]
      // console.log('导入文件file', this.file)
      const types = this.file.name.split('.')[1]
      // console.log('导入文件类型', types)
      const fileType = ['xlsx', 'xls'].some((item) => item === types)
      if (!fileType) {
        this.$message.warning('格式错误！请重新选择')
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      let url = '/binhuapis/siteManage/importSite'
      // console.log('导入url', url, 'formData', formData)
      let file = formData
      request.post(url, file).then((res) => {
        if (res.data.code === 200) {
          this.$message.success('导入成功')
          this.page = 1
          this.queryDataTable() // 调用查询接口
          this.$refs.upload.clearFiles()
        } else {
          this.$message({
            type: 'error',
            message: '导入失败' + res.data.message
          })
          this.$refs.upload.clearFiles()
        }
      })
      // .catch(() => {
      //   this.$message.error('导入失败')
      // })
    },

    // 导出
    exportData() {
      let param = {}
      if (this.multipleSelection.length > 0) {
        let ids = this.multipleSelection.map((item) => {
          return item.id
        })
        param.ids = ids.join(',')
      }
      if (this.searchForm.name) {
        param.name = this.searchForm.name
      }
      if (this.searchForm.type) {
        param.type = this.searchForm.type
      }
      if (this.searchForm.address) {
        param.address = this.searchForm.address
      }
      if (this.searchForm.MAXGALLERYFUL) {
        param.MAXGALLERYFUL = this.searchForm.MAXGALLERYFUL
      }
      if (this.searchForm.area) {
        param.acreage = this.searchForm.area
      }
      if (this.searchForm.streetName) {
        param.streetName = this.searchForm.streetName
      }
      if (this.searchForm.communityName) {
        param.community = this.searchForm.communityName
      }
      console.log(param)
      let url = '/binhuapis/siteManage/exportSite?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
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
.lawsAndRegulations {
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
