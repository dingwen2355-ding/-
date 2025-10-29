<template>
  <div class="NoticeAnnouncement">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>通知公告</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form class="znv-el-form" ref="form" :inline="true" :model="search">
      <el-form-item label="公告标题" style="margin-bottom: 0">
        <el-input placeholder="请输入公告标题" clearable v-model="search.announcementTitle"></el-input>
      </el-form-item>
      <el-form-item label="操作人员" style="margin-bottom: 0">
        <el-input placeholder="请输入操作人员" clearable v-model="search.creator"></el-input>
      </el-form-item>
      <el-form-item label="公告类型" style="margin-bottom: 0">
        <el-select v-model="search.announcementType" filterable clearable placeholder="请选择公告类型">
          <el-option
            v-for="item in operateModules"
            :key="item.index"
            :label="item.operateModule"
            :value="item.index"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" @click="querySerch()">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-card class="box-card" style="margin-top: 20px; height: calc(100% - 140px)">
      <div class="table">
        <header>
          <span>
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete(1, {})">删除</el-button>
          </span>
        </header>
        <el-table
          :data="logData"
          highlight-current-row
          v-loading="listLoading"
          height="550px"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column type="index" label="序号" :index="table_index" width="50"></el-table-column>
          <el-table-column
            prop="announcementTitle"
            label="公告标题"
            width="400"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column prop="announcementType" label="公告类型" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.announcementType === '1'">通知</span>
              <span v-else-if="scope.row.announcementType === '2'">公告</span>
            </template>
          </el-table-column>
          <el-table-column prop="announcementState" label="状态" show-overflow-tooltip>
            <template slot-scope="scope">
              <span v-if="scope.row.announcementState === '1'">正常</span>
              <span v-else-if="scope.row.announcementState === '2'">关闭</span>
            </template>
          </el-table-column>
          <el-table-column prop="creator" label="创建者" show-overflow-tooltip></el-table-column>
          <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip></el-table-column>
          <el-table-column fixed="right" label="操作" width="150">
            <template slot-scope="scope">
              <el-button type="text" size="medium" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="text" size="medium" @click="deleteSome(scope.row.noticeId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span class="total-number">{{ total }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, sizes, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </el-col>
    </el-card>
    <NoticeAnnouncementAdd
      :dialogVisible.sync="showAdd"
      :formInfo="formInfo"
      @success="handleSuccess"
      :isCode="isCode"
    />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import NoticeAnnouncementAdd from './NoticeAnnouncementAdd.vue'
export default {
  name: 'NoticeAnnouncement',
  components: { NoticeAnnouncementAdd },
  data() {
    return {
      pickerOptionsStart: {
        disabledDate: (time) => {
          if (this.search.endTime) {
            return time.getTime() > new Date(this.search.endTime).getTime()
          }
        }
      },
      pickerOptionsEnd: {
        disabledDate: (time) => {
          if (this.search.beginTime) {
            return time.getTime() <= new Date(this.search.beginTime).getTime() - 8.64e7
          }
        }
      },
      search: {
        announcementTitle: '',
        announcementType: '',
        creator: ''
      },
      operateModules: [
        { index: 1, operateModule: '通知' },
        { index: 2, operateModule: '公告' }
      ],
      listLoading: false,
      logData: [],
      currentRow: null,
      selectionIds: [],
      currentPage: 1,
      pageSize: 10, // 每页显示几条
      showAdd: false,
      formInfo: null,
      isCode: 0,
      total: 0
    }
  },
  mounted() {
    this.refreshData()
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleAdd() {
      this.showAdd = true
      this.formInfo = null
      this.isCode = 0
    },
    handleEdit(row) {
      this.showAdd = true
      this.formInfo = JSON.parse(JSON.stringify(row))
      this.isCode = 1
    },
    handleSuccess() {
      this.pageStart = 1
      this.refreshData()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.refreshData()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.refreshData()
    },
    querySerch() {
      this.currentPage = 1
      this.refreshData()
    },
    refreshData() {
      this.listLoading = true
      this.logData = []
      let param = {
        pageNum: this.currentPage,
        pageSize: this.pageSize
      }
      if (this.search.announcementTitle) {
        param.announcementTitle = this.search.announcementTitle
      }
      if (this.search.creator) {
        param.creator = this.search.creator
      }
      if (this.search.announcementType) {
        param.announcementType = this.search.announcementType
      }

      let url = '/auth/queryNoticeAnnouncement?' + qs.stringify(param)
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.listLoading = false
          this.logData = res.data.data.list
          this.total = res.data.data.total
        }
      })
    },
    handleSelectionChange(rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.noticeId)
      })
    },
    handleDelete() {
      if (this.selectionIds.length === 0) {
        this.$notify.info({
          title: '消息',
          message: '请先勾选删除项',
          duration: 1000
        })
      } else {
        let ids = this.selectionIds.join(',')
        this.deleteSome(ids)
      }
    },
    deleteSome(ids) {
      this.$confirm('此操作将删除所选信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/auth/deleteNoticeAnnouncement?noticeId=' + ids
          axios.delete(url).then((res) => {
            if (res.data.remark === 'success' && res.data.result === 0) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 1000
              })
              this.currentPage = 1
              this.refreshData()
            } else {
              this.$notify.info({
                title: '消息',
                message: res.data.message,
                duration: 1000
              })
            }
          })
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消删除',
            duration: 1000
          })
        })
    }
  }
}
</script>

<style lang="scss">
.NoticeAnnouncement {
  width: 100%;
  height: 100%;
  background: #f8f9ff;
  overflow: auto;
  /*定义搜索栏*/
  .search-container {
    margin-bottom: 10px;
    overflow: hidden;
    .el-input,
    .el-option,
    .el-select {
      width: 288px;
      display: inline-block;
    }
    .el-button {
      display: inline-block;
    }
  }
  .table {
    margin-top: 10px;
  }
  .search-container .el-button {
    margin-top: 10px;
  }
  .search-container .label {
    display: inline-block;
    width: 80px;
    font-size: 14px;
  }
}
</style>
