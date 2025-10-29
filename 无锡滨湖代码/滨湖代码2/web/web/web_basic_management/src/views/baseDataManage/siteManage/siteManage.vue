<template>
  <div class="siteManage">
    <el-form :inline="true" :model="searchForm" ref="form">
      <!-- <el-form-item label="所属办事处:">
        <el-select
          filterable
          v-model="searchForm.streetName"
          placeholder="请选择所属办事处"
          popper-append-to-body
          clearable
          style="width: 175px;"
        >
          <el-option
            v-for="item in streetList"
            :value="item.precinctName"
            :label="item.precinctName"
            :key="item.precinctId"
          ></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item label="所属社区:">
        <el-select
          filterable
          v-model="searchForm.communityName"
          placeholder="请选择所属社区"
          popper-append-to-body
          clearable
          style="width: 175px;"
        >
          <el-option
            v-for="item in communityList"
            :value="item.precinctName"
            :label="item.precinctName"
            :key="item.precinctId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="避难场所名称:">
        <el-input clearable v-model="searchForm.name" style="width: 175px;" placeholder="请输入避难场所名称"></el-input>
      </el-form-item>
      <el-form-item label="容纳人数:">
        <el-input
          clearable
          v-model="searchForm.MAXGALLERYFUL"
          style="width: 140px;"
          placeholder="请输入容纳人数:"
        ></el-input>
      </el-form-item>
      <el-form-item label="面积:">
        <!-- <el-input clearable v-model="searchForm.area" type="number" min="0" placeholder="请输入面积"></el-input> -->
        <el-input clearable v-model="searchForm.area" style="width: 140px;" placeholder="请输入面积"></el-input>
      </el-form-item>
      <el-form-item label="类别:">
        <el-select
          filterable
          v-model="searchForm.type"
          style="width: 140px;"
          placeholder="请输入类型"
          clearable
        >
          <el-option v-for="item in typeList" :value="item.id" :label="item.name" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" type="primary" size="small" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" size="small" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <el-button icon="el-icon-download" size="small" @click="templateDown">模板下载</el-button>
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
            <el-button
              icon="el-icon-upload2"
              style="margin-left: 10px"
              type="primary"
              size="small"
            >导入</el-button>
          </el-upload>
          <el-button
            icon="el-icon-download"
            style="margin-left: 10px"
            type="success"
            size="small"
            @click="exportData"
          >导出</el-button>
          <el-button icon="el-icon-delete" type="danger" size="small" @click="deleteData">删除</el-button>
        </div>
        <div class="content-right">
          <el-button icon="el-icon-plus" size="small" type="primary" @click="addData">新增记录</el-button>
          <!-- <el-button type="text" icon="el-icon-search" @click="onSubmit"></el-button>
                    <el-button type="text" icon="el-icon-refresh" @click="resetSearch"></el-button>
          <el-button type="text" icon="el-icon-s-unfold"></el-button>-->
        </div>
      </div>
      <el-table
        :data="tableData"
        border
        fit
        style="width: 100%"
        height="740"
        v-loading="loading"
        ref="multipleTable"
        @select="handleSelectionChange"
        @select-all="handleSelectionChangeAll"
      >
        <el-table-column align="center" header-align="center" type="selection" width="55"></el-table-column>
        <!-- <el-table-column align='center' header-align='center'  type="index" label="序号" width="70" :index="table_index"></el-table-column> -->
        <el-table-column
          align="center"
          header-align="center"
          prop="name"
          label="避难场所名称"
          width="110"
        ></el-table-column>
        <el-table-column align="center" header-align="center" prop="type" label="类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="category" label="类别"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="deviceId"
          width="200"
          label="设备ID"
        ></el-table-column>
        <el-table-column align="center" header-align="center" label="设备状态">
          <template slot-scope="scope">
            <span v-if="scope.row.deviceStatus=='online'">在线</span>
            <span v-if="scope.row.deviceStatus=='offline'">离线</span>
          </template>
        </el-table-column>
        <!--                <el-table-column align='center' header-align='center'  prop="roomCount" label="房间数量" width="80"></el-table-column>-->
        <!-- <el-table-column align="center" header-align="center" prop="streetName" label="所属办事处"></el-table-column> -->
        <el-table-column align="center" header-align="center" prop="community" label="所属社区"></el-table-column>
        <el-table-column align="center" header-align="center" prop="acreage">
          <template slot="header">
            面积 m
            <sup>2</sup>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="maxGalleryful"
          label="最大容纳人数"
          width="110"
        ></el-table-column>
        <!-- <el-table-column align="center" header-align="center" prop="alreadyGalleryful" label="当前人数" width="110"></el-table-column> -->
        <el-table-column align="center" header-align="center" label="状态">
          <template slot-scope="scope">
            <span>{{stateList[scope.row.state]}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" header-align="center" prop="address" label="地址"></el-table-column>
        <el-table-column align="center" header-align="center" prop="person" label="管理人员"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="phone"
          label="管理员手机"
          width="120"
        ></el-table-column>
        <el-table-column align="center" header-align="center" label="操作" width="90">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit(scope.row, 1)"
              type="text"
              size="medium"
              icon="el-icon-view"
            ></el-button>
            <el-button
              @click="handleEdit(scope.row, 0)"
              type="text"
              size="medium"
              icon="el-icon-edit-outline"
            ></el-button>
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
    <AddSiteManage
      :dialogVisible.sync="showAdd"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    ></AddSiteManage>
  </div>
</template>
<script>
import request from '@/utils/request'
import AddSiteManage from './option/addSiteManage.vue'
import qs from 'qs'
export default {
  name: 'siteManage',
  components: { AddSiteManage },
  data() {
    return {
      kindList: [
        { id: '公园', name: '公园' },
        { id: '学校', name: '学校' }
      ],
      typeList: [
        { id: '室外场所', name: '室外场所' },
        { id: '室内场所', name: '室内场所' }
      ],
      stateList: {
        1: '开放',
        2: '有人',
        3: '告警',
        4: '未开放',
        5: '暂未使用'
      },
      searchForm: {
        streetName: '',
        communityName: '',
        name: '',
        kind: '',
        type: '',
        MAXGALLERYFUL: '',
        area: ''
      },

      loading: false,
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      multipleSelection: [],
      fileList: [],
      file: {},
      streetList: [],
      communityList: [],
      showAdd: false,
      formInfo: {},
      isCode: 0 // 0 编辑 1 查看详情 2 新增
    }
  },
  mounted() {
    this.queryDataTable()
    this.getSiteList3()
    this.getSiteList4()
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
    templateDown() {
      const link = document.createElement('a')
      link.href = '/mock/避难场所导入模板.xlsx'
      link.setAttribute('download', '避难场所导入模板.xlsx')
      document.body.appendChild(link)
      link.click()
    },
    getSiteList3() {
      let url = '/auth/v2/precinct/queryPrecinctByKind?precinctKind=110'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.communityList = data
        }
      })
    },
    getSiteList4() {
      let url = '/auth/v2/precinct/queryPrecinctByKind?precinctKind=110'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data || []
          this.streetList = data
        }
      })
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
      let url = '/binhuapis/siteManage/query?pageNum=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.name) {
        url = url + '&name=' + this.searchForm.name
      }
      if (this.searchForm.type) {
        url = url + '&type=' + this.searchForm.type
      }
      if (this.searchForm.address) {
        url = url + '&address=' + this.searchForm.address
      }
      if (this.searchForm.MAXGALLERYFUL) {
        url = url + '&MAXGALLERYFUL=' + this.searchForm.MAXGALLERYFUL
      }
      if (this.searchForm.area) {
        url = url + '&acreage=' + this.searchForm.area
      }
      if (this.searchForm.streetName) {
        url = url + '&streetName=' + this.searchForm.streetName
      }
      if (this.searchForm.communityName) {
        url = url + '&community=' + this.searchForm.communityName
      }
      request.get(url).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list.list
        this.total = res.data.data.list.total
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
        type: '',
        address: '',
        area: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },
    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        name: '',
        type: '',
        category: '',
        roomCount: '',
        responsibility: '',
        person: '',
        phone: '',
        community: '',
        address: '',
        acreage: '',
        state: '',
        maxGalleryful: '' // 最大容纳人数
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
        this.$confirm('此操作将删除所勾选的场所, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/siteManage/remove?id=${ids}`
          // console.log('删除--url', url)
          request.delete(url, ids).then((r) => {
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
      this.$confirm('此操作将删除所选的场所, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/binhuapis/siteManage/remove?id=' + row.id
        // console.log('删除--url', url)
        request.delete(url).then(res => {
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
.siteManage {
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
