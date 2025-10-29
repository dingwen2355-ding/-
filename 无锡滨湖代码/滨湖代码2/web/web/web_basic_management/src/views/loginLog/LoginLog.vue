<template>
  <div class="log-page">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>日志管理</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin-top: 20px;margin-bottom: -20px">
      <el-form :inline="true">
        <el-form-item label="用户名">
          <div style="display: inline">
            <el-input placeholder="请输入用户名" v-model="userName" clearable></el-input>
          </div>
        </el-form-item>
        <el-form-item label="时间">
          <el-date-picker
            clearable
            v-model="dateRangeTime"
            type="datetimerange"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="至"
            :default-time="['00:00:00', '23:59:59']"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="日志类型">
          <el-select v-model="logType" clearable>
            <el-option
              v-for="item in logKinds"
              :label="item.operator"
              :key="item.operator"
              :value="item.operator"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="searchLog" icon="el-icon-search">查询</el-button>
          <el-button type="success" size="mini" @click="exportExcel" :loading="loading" icon="el-icon-download">导出</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-card class="box-card" style="margin-top: 20px; height: calc(100% - 120px)">
      <div class="table">
        <el-table
          :data="logData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
          highlight-current-row
          v-loading="listLoading"
          @selection-change="handleSelectionChange"
          height="600px"
          style="width: 100%"
        >
          <!-- <el-table-column type="selection" width="50"></el-table-column> -->
          <el-table-column prop="operUserId" label="用户ID" width="80"></el-table-column>
          <el-table-column prop="operUserName" label="用户名" width="200"></el-table-column>
          <el-table-column prop="ip" label="用户ip" width="180"></el-table-column>

          <el-table-column prop="operatedUserId" label="操作对象ID" width="120"></el-table-column>
          <el-table-column prop="operatedUserName" label="操作对象用户名" width="150"></el-table-column>
          <el-table-column prop="operator" label="操作类型" width="200"></el-table-column>

          <el-table-column prop="detail" label="操作内容" width="200"></el-table-column>
          <el-table-column prop="time" label="操作时间" width="200"></el-table-column>
<!--          <el-table-column prop="result" label="结果">-->
<!--            <template slot-scope="scope">{{ result2string(scope.row.result) }}</template>-->
<!--          </el-table-column>-->
          <el-table-column prop="result" label="结果"></el-table-column>
        </el-table>
      </div>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span class="total-number">{{ logData.length }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :page-sizes="[11, 30, 60, 100]"
            layout="prev, pager,next, sizes, jumper"
            :total="logData.length"
          ></el-pagination>
        </div>
      </el-col>
    </el-card>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'log-manager',
  data() {
    return {
      listLoading: false,
      logData: [],
      logType: '',
      userName: '',
      dateRangeTime: [],
      logKinds: [],
      currentRow: null,
      loading: false,
      selectionIds: [],
      currentPage: 1,
      pageSize: 11 // 每页显示几条
    }
  },
  mounted() {
    this.refreshData()
    this.queryLogKinds()
  },
  methods: {
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    refreshData() {
      var self = this
      var url = '/auth/log'
      self.listLoading = true
      axios.get(url).then(function (res) {
        self.listLoading = false
        self.logData = res.data.data
      })
    },
    handleSelectionChange: function (rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.id)
      })
    },
    handleCurrentChangePage: function (val) {
      this.currentPage = val
      this.tableData = this.logData.slice((val - 1) * this.pageSize, val * this.pageSize)
    },
    result2string: function (result) {
      if (result === 0) {
        return '成功'
      } else if (result === 1) {
        return '失败'
      }
    },
    searchLog() {
      var self = this
      self.listLoading = true
      let url = `/auth/log`
      if (this.userName) {
        url += `?operUserName=${this.userName}`
      }
      console.log('s222', this.dateRangeTime)
      if (this.dateRangeTime !== null && this.dateRangeTime.length !== 0) {
        if (url.indexOf('?') === -1) {
          url += `?startTime=${this.dateRangeTime[0]}&endTime=${this.dateRangeTime[1]}`
        } else {
          url += `&startTime=${this.dateRangeTime[0]}&endTime=${this.dateRangeTime[1]}`
        }
      }
      if (this.logType) {
        if (url.indexOf('?') === -1) {
          url += `?operator=${this.logType}`
        } else {
          url += `&operator=${this.logType}`
        }
      }
      axios.get(url).then(function (res) {
        self.listLoading = false
        self.logData = res.data.data
      })
    },
    exportExcel() {
      this.loading = true
      let url = `/auth/exportLogExcel`
      if (this.userName) {
        url += `?operUserName=${this.userName}`
      }
      if (this.dateRangeTime !== null && this.dateRangeTime.length !== 0) {
        if (url.indexOf('?') === -1) {
          url += `?startTime=${this.dateRangeTime[0]}&endTime=${this.dateRangeTime[1]}`
        } else {
          url += `&startTime=${this.dateRangeTime[0]}&endTime=${this.dateRangeTime[1]}`
        }
      }
      if (this.logType) {
        if (url.indexOf('?') === -1) {
          url += `?operator=${this.logType}`
        } else {
          url += `&operator=${this.logType}`
        }
      }
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          window.open(res.data.data, '_bank')
          this.loading = false
        }
      })
    },
    queryLogKinds() {
      let url = `/auth/getLogKinds`
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.logKinds = res.data.data
        }
      })
    }
  }
}
</script>

<style lang="scss">
.log-page {
  width: 100%;
  height: 100%;
  background: #f8f9ff;
}
</style>
