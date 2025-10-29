<template>
  <div class="ConsultationNotice">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="模糊查询">
        <el-input clearable v-model="searchForm.title" placeholder="请输入标题/发布人"></el-input>
      </el-form-item>
      <el-form-item label="发布时间">
        <el-date-picker
          v-model="searchForm.handleTime"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss"
          :default-time="['00:00:00', '23:59:59']"
        ></el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="状态">
        <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
          <el-option v-for="item in statusList" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" @click="onSubmit" size="mini">查询</el-button>
      </el-form-item>
    </el-form>
    <div class="znv-table-area">
      <div class="contentBody">
        <div class="content-top">
          <div class="content-left">
            <h4 style="color: #1c1c1c; font-size: 16px; margin: 0">查询结果</h4>
          </div>
          <div class="content-right">
            <el-button icon="el-icon-plus" type="primary" size="mini" @click="addPlan">新增</el-button>
            <el-button icon="el-icon-delete" type="danger" size="mini" @click="delPlan">删除</el-button>
            <!-- <el-upload
              class="btn2"
              ref="upload"
              action="/"
              :file-list="fileList"
              :show-file-list="false"
              :on-change="handleChange"
              :auto-upload="false"
              style="display: inline-block; margin: 0 10px"
            >
              <el-button type="primary" icon="el-icon-upload2" size="mini">导入</el-button>
            </el-upload>
            <a download="资讯管理导入模板" href="/template/资讯管理导入模板.xlsx" class="sel_btn">
              点击下载导入模板
              <i class="el-icon-download"></i>
            </a>-->
          </div>
        </div>
        <!-- v-loading="loading" -->
        <el-table
          ref="dangerTable"
          :data="tableData"
          style="width: 100%; margin-top: 10px"
          height="643"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column type="index" label="序号" width="50" :index="table_index"></el-table-column>
          <el-table-column prop="title" label="标题" show-overflow-tooltip></el-table-column>
          <el-table-column prop="publisher" label="发布人" show-overflow-tooltip></el-table-column>
          <el-table-column prop="releaseTime" label="发布时间" show-overflow-tooltip></el-table-column>
          <!-- <el-table-column label="状态">
            <template slot-scope="scope">
              <span v-if="scope.row.status == 0">暂存</span>
              <span v-else-if="scope.row.status == 1">已发布</span>
              <span v-else-if="scope.row.status == 2">已禁用</span>
            </template>
          </el-table-column>-->
          <el-table-column fixed="right" label="操作" width="420">
            <template slot-scope="scope">
              <el-button type="success" plain size="mini" @click="handleEdit(scope.row, 1)">详情</el-button>
              <el-button
                type="primary"
                plain
                size="mini"
                @click="handleEdit(scope.row, 0)"
                v-if="scope.row.status != 1"
              >编辑</el-button>
              <!-- <el-button type="text" size="medium" @click="handleRelease(scope.row)" v-if="scope.row.status == 0">发布</el-button>
              <el-button type="text" size="medium" @click="handleStop(scope.row)" v-if="scope.row.status == 1">禁用</el-button>-->
              <el-button
                type="info"
                plain
                size="mini"
                v-if="scope.row.isTop == '0'"
                @click="handleTopping(scope.row)"
              >置顶</el-button>
              <el-button type="warning" size="mini" @click>禁用</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <ConsultationNoticeAdd
      :dialogVisible.sync="showAdd"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    />
  </div>
</template>

<script>
import Axios from '@/utils/request'
// import znvTime from '../../../utils/znvTime'
import ConsultationNoticeAdd from './ConsultationNoticeAdd.vue'

export default {
  name: 'InformationManagement',
  components: { ConsultationNoticeAdd },
  data() {
    return {
      statusList: [
        { id: '0', name: '暂存' },
        { id: '1', name: '已发布' },
        { id: '2', name: '已禁用' }
      ],
      searchForm: {
        title: '',
        handleTime: '',
        // status: ''
      },

      fileList: [],

      loading: false,
      tableData: [],
      page: 1,
      pageSize: 10,
      total: 0,
      multipleSelection: [],

      showAdd: false,
      formInfo: {},
      isCode: 0
    }
  },
  mounted() {
    this.queryTableData()
  },
  methods: {
    table_index(index) {
      return (this.page - 1) * this.pageSize + index + 1
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.queryTableData()
    },

    handleCurrentChange(val) {
      this.page = val
      this.queryTableData()
    },

    queryTableData() {
      this.loading = true
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize
      }
      if (this.searchForm.title) {
        params.text = this.searchForm.title
      }
      // if (this.searchForm.status) {
      //   params.status = this.searchForm.status
      // }
      if (Array.isArray(this.searchForm.handleTime)) {
        params.beginTime = this.searchForm.handleTime[0]
        params.endTime = this.searchForm.handleTime[1]
      }
      let url = '/binhuapis/informationManage/query'
      Axios.post(url, params).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },

    // 查询
    onSubmit() {
      this.page = 1
      this.queryTableData()
    },

    // 新增
    addPlan() {
      this.showAdd = true
      this.formInfo = {
        title: '',
        publisher: '',
        // releaseTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'),
        remark: ''
      }
      this.isCode = 2
    },
    success() {
      this.page = 1
      this.queryTableData()
    },
    // 选中的节点数据
    handleSelectionChange(rows) {
      this.multipleSelection = rows
      // console.log('选中的节点数据: ', this.multipleSelection)
    },
    // 删除
    delPlan() {
      let ids = []
      if (this.multipleSelection.length > 0) {
        this.multipleSelection.forEach((item, index) => {
          ids.push(item.id)
        })
        // console.log('选中的id: ', ids)
        let url = '/binhuapis/informationManage/del'
        this.$confirm('此操作将删除选中的资讯相关信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            Axios.post(url, ids)
              .then((res) => {
                if (res.data.code === 200) {
                  this.$message.success('删除成功')
                  this.page = 1
                  this.queryTableData()
                } else {
                  this.$message(res.data.message)
                }
              })
              .catch(() => {
                this.$message.error('删除失败')
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

    // 导入
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
      let url = '/binhuapis/informationManage/import'
      // console.log('导入url', url, 'formData', formData)
      let file = formData
      Axios.post(url, file)
        .then((res) => {
          if (res.data.code === 200) {
            this.$message.success('导入成功')
            this.page = 1
            this.queryTableData() // 调用查询接口
            this.$refs.upload.clearFiles()
          } else {
            this.$message({
              type: 'error',
              message: res.data.message
            })
            this.$refs.upload.clearFiles()
          }
        })
        .catch(() => {
          this.$message.error('导入失败')
        })
    },

    // 详情/编辑
    handleEdit(row, code) {
      // console.log('详情/编辑-- row: ', row)
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
    // 置顶
    handleTopping(row) {
      let url = '/binhuapis/informationManage/updateIsTop?id=' + row.id
      this.$confirm('请确定，是否置顶？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          Axios.post(url)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('置顶成功')
                this.page = 1
                this.queryTableData()
              } else {
                this.$message(res.data.message)
              }
            })
            .catch(() => {
              this.$message.error('置顶失败')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消置顶'
          })
        })
    },
    // 发布
    handleRelease(row) {
      //   console.log('发布-- row: ', row)
      let params = {
        id: row.id,
        status: '1'
      }
      let url = '/auth/informationManage/edit'
      this.$confirm('请确定编辑完毕，是否发布？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('发布成功')
                this.page = 1
                this.queryTableData()
              } else {
                this.$message(res.data.message)
              }
            })
            .catch(() => {
              this.$message.error('发布失败')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
    },

    // 禁用
    handleStop(row) {
      // console.log('禁用--row: ', row)
      let params = {
        id: row.id,
        status: '2'
      }
      let url = '/auth/informationManage/edit'
      this.$confirm('此操作会导致资讯禁用，是否禁用？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          Axios.post(url, params)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success('禁用成功')
                this.page = 1
                this.queryTableData()
              } else {
                this.$message(res.data.message)
              }
            })
            .catch(() => {
              this.$message.error('禁用失败')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消发布'
          })
        })
    }
  }
}
</script>

<style scoped lang="scss">
.ConsultationNotice {
  width: 100%;
  height: calc(100% - 10px);
  -webkit-box-sizing: border-box;
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
