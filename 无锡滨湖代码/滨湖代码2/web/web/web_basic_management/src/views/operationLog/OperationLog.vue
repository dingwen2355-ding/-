<template>
  <div class="OperationLog">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>操作日志</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form class="znv-el-form" ref="form" :inline="true" :model="search">
      <el-form-item label="用户名" style="margin-bottom: 0">
        <el-input placeholder="请输入用户名" clearable v-model="search.username"></el-input>
      </el-form-item>
      <el-form-item label="开始时间" style="margin-bottom: 0">
        <el-date-picker
          type="datetime"
          placeholder="选择开始时间"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="pickerOptionsStart"
          v-model="search.beginTime"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" style="margin-bottom: 0">
        <el-date-picker
          type="datetime"
          placeholder="选择结束时间"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="pickerOptionsEnd"
          v-model="search.endTime"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="操作模块" style="margin-bottom: 0">
        <el-select v-model="search.operateModule" filterable clearable placeholder="请选择操作模块">
          <el-option
            v-for="item in operateModules"
            :key="item.index"
            :label="item.operateModule"
            :value="item.operateModule"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作内容" style="margin-bottom: 0">
        <el-input placeholder="请输入操作内容" clearable v-model="search.operateDetail"></el-input>
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" @click="querySerch()">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-card class="box-card" style="margin-top: 20px; height: calc(100% - 140px)">
      <div class="table">
        <el-table :data="logData" highlight-current-row v-loading="listLoading" style="width: 100%" height="540px">
          <el-table-column type="index" label="序号" :index="table_index" width="65"></el-table-column>
          <el-table-column prop="username" label="用户名" width="100" show-overflow-tooltip></el-table-column>
          <el-table-column prop="clientIp" label="主机地址" width="180" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateTime" label="操作时间" width="300" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateModule" label="操作模块" width="100" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateFunction" label="操作功能" width="150" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateType" label="操作类型" width="100" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateDetail" label="操作内容" show-overflow-tooltip></el-table-column>
          <el-table-column prop="operateResult" label="结果" width="100" show-overflow-tooltip></el-table-column>
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
            layout="prev, pager,next, sizes, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </el-col>
    </el-card>
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'operate-manager',
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
        beginTime: '',
        endTime: '',
        operateModule: '',
        operateDetail: ''
      },
      operateModules: [
        { index: 1, operateModule: '值班信息' },
        { index: 2, operateModule: '基本信息' },
        { index: 3, operateModule: '一键轮巡' },
        { index: 4, operateModule: '管理事项' },
        { index: 5, operateModule: '地图' },
        { index: 6, operateModule: '监督管理' },
        { index: 7, operateModule: '管理闭环' },
        { index: 8, operateModule: '专项管理' }
      ],
      listLoading: false,
      logData: [],
      currentRow: null,
      selectionIds: [],
      total: 0,
      currentPage: 1,
      pageSize: 10 // 每页显示几条
    }
  },
  mounted() {
    this.refreshData()
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    querySerch() {
      this.currentPage = 1
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

    refreshData() {
      this.listLoading = true
      let param = {
        pageNum: this.currentPage,
        pageSize: this.pageSize
      }
      if (this.search.username) {
        param.username = this.search.username
      }
      if (this.search.beginTime) {
        param.beginTime = this.search.beginTime
      }
      if (this.search.endTime) {
        param.endTime = this.search.endTime
      }
      if (this.search.operateModule) {
        param.operateModule = this.search.operateModule
      }
      if (this.search.operateDetail) {
        param.operateDetail = this.search.operateDetail
      }
      let url = '/auth/queryOperateLog?' + qs.stringify(param)
      axios.get(url).then((res) => {
        this.listLoading = false
        if (res.data.code === 200) {
          this.logData = res.data.data.list || []
          this.total = res.data.data.total
        }
      })
    }
  }
}
</script>

<style lang="scss">
@import '../../style/common';
.OperationLog {
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
