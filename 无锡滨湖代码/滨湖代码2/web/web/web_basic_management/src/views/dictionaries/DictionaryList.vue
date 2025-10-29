<template>
  <div class="DictionaryList">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>字典管理</el-breadcrumb-item>
      <el-breadcrumb-item>字典列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form class="znv-el-form" ref="form" :inline="true" :model="search">
      <el-form-item label="字典名称" style="margin-bottom: 0">
        <el-input placeholder="请输入字典名称" clearable v-model="search.username"></el-input>
      </el-form-item>
      <el-form-item label="字典标签" style="margin-bottom: 0">
        <el-input placeholder="请输入字典标签" clearable v-model="search.operateDetail"></el-input>
      </el-form-item>
      <el-form-item label="数据状态" style="margin-bottom: 0">
        <el-select v-model="search.operateModule" filterable clearable placeholder="请选择数据状态">
          <el-option
            v-for="item in operateModules"
            :key="item.index"
            :label="item.operateModule"
            :value="item.operateModule"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" @click="querySerch()">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-card class="box-card" style="margin-top: 20px">
      <div class="table">
        <header>
          <span>
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete(1, {})">删除</el-button>
            <el-button type="success" icon="el-icon-download">导出</el-button>
            <el-button type="primary" @click="handleBack">返回</el-button>
          </span>
        </header>
        <el-table
          :data="logData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
          highlight-current-row
          v-loading="listLoading"
          height="580px"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="username" label="用户名" width="100"></el-table-column>
          <el-table-column prop="clientIp" label="主机地址" width="180"></el-table-column>
          <el-table-column prop="operateTime" label="操作时间" width="300"></el-table-column>
          <el-table-column prop="operateModule" label="操作模块" width="100"></el-table-column>
          <el-table-column prop="operateFunction" label="操作功能" width="150"></el-table-column>
          <el-table-column prop="operateType" label="操作类型" width="100"></el-table-column>
          <el-table-column prop="operateDetail" label="操作内容" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateResult" label="结果" width="100"></el-table-column>
          <el-table-column fixed="right" label="操作" width="120">
            <template slot-scope="scope">
              <el-button type="text" size="medium" @click="redact(scope.row)">编辑</el-button>
              <el-button type="text" size="medium" @click="delete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span
              class="total-number"
            >{{ logData.length }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, sizes, jumper"
            :total="logData.length"
          ></el-pagination>
        </div>
      </el-col>
    </el-card>
    <DictionaryListAdd :dialogVisible.sync="showAdd" :formInfo="formInfo" @success="handleSuccess" :isCode="isCode" />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import DictionaryListAdd from './DictionaryListAdd.vue'
export default {
  name: 'operate-manager',
  components: { DictionaryListAdd },
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
        username: '',
        operateModule: '',
        operateDetail: ''
      },
      operateModules: [
        { index: 1, operateModule: '所有' },
        { index: 2, operateModule: '正常' },
        { index: 3, operateModule: '停用' }
      ],
      listLoading: false,
      logData: [],
      currentRow: null,
      selectionIds: [],
      currentPage: 1,
      pageSize: 10, // 每页显示几条
      showAdd: false,
      formInfo: null,
      isCode: 0
    }
  },
  mounted() {
    this.refreshData()
  },
  methods: {
    // 编辑
    redact() { },
    // 删除
    delete() { },
    handleAdd() {
      this.showAdd = true
      this.formInfo = null
      this.isCode = 0
    },
    handleBack() {
      this.$store.dispatch('view/setDictionCheck', true)
    },
    handleSuccess() {
      this.pageStart = 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    refreshData() {
      var self = this
      let param = {
        username: self.username,
        beginTime: self.beginTime,
        endTime: self.endTime,
        operateModule: self.operateModule,
        operateDetail: self.operateDetail
      }
      if (self.search.username) {
        param.username = this.search.username
      }
      if (self.search.beginTime) {
        param.beginTime = this.search.beginTime
      }
      if (self.search.endTime) {
        param.endTime = this.search.endTime
      }
      if (self.search.operateModule) {
        param.operateModule = this.search.operateModule
      }
      if (self.search.operateDetail) {
        param.operateDetail = this.search.operateDetail
      }

      let url = '/community/queryOperateLog?' + qs.stringify(param)
      self.listLoading = true
      axios.get(url).then(function (res) {
        self.listLoading = false
        if (res.data.code === 200) {
          self.logData = res.data.data
        }
      })
    },
    handleSelectionChange(rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.id)
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
          let url = '/community/hiddenLevel/deleteFirstLevel?id=' + ids
          axios.post(url).then((res) => {
            if (res.data.code === 200) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 1000
              })
              this.pageStart = 1
              this.refreshData()
            } else {
              this.$notify.error({
                title: '错误',
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
.DictionaryList {
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
