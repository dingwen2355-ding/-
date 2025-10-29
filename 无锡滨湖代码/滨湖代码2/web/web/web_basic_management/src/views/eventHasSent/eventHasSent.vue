<template>
  <div class="eventHasSent">
    <!-- 面包屑 -->

    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>事件派遣</el-breadcrumb-item>
      <el-breadcrumb-item>事件已派遣</el-breadcrumb-item>
    </el-breadcrumb>-->

    <el-card class="box-card" style="height: calc(100% - 40px)">
      <el-col :span="24" class="search-container">
        <el-form :inline="true" :model="searchForm" ref="form" label-width="100px">
          <el-form-item label="事件标题">
            <el-input clearable v-model="searchForm.eventTitle" placeholder="请输入事件标题"></el-input>
          </el-form-item>
          <!-- <el-form-item label="状态">
            <el-select filterable v-model="searchForm.eventState" placeholder="请选择状态" clearable style="width: 220px">
              <el-option
                v-for="item in checkSourceList"
                :value="item.stateId"
                :label="item.stateName"
                :key="item.stateId"
              />
            </el-select>
          </el-form-item>-->
          <el-form-item label="事件编号">
            <el-input clearable v-model="searchForm.taskId" placeholder="请输入事件编号"></el-input>
          </el-form-item>

          <el-form-item label="发生时间">
            <el-date-picker
              v-model="searchForm.time"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              :default-time="['00:00:00', '23:59:59']"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchDataCilck" style="flot: right">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <!--列表-->
      <el-col :span="24" class="table-col">
        <el-table
          :data="tableData"
          highlight-current-row
          v-loading="listLoading"
          style="width: 100%"
          ref="table"
          :height="tableHeight"
          class="eventSink-table"
          :row-class-name="tableRowClassName"
        >
          <el-table-column type="index" show-overflow-tooltip label="序号" :index="table_index" width="70"></el-table-column>
          <el-table-column prop="eventName" show-overflow-tooltip label="事件标题" width="150px"></el-table-column>
          <!-- <el-table-column prop="isCommunity" show-overflow-tooltip label="是否区级网格事件" width="135">
            <template slot-scope="scope">
              <span v-if="scope.row.isCommunity === '1'">是</span>
            </template>
          </el-table-column>-->
          <el-table-column prop="eventState" show-overflow-tooltip label="状态" width="120px">
            <template slot-scope="scope">
              <span :style="cerrorColor(scope.row.eventStateId)">{{ scope.row.eventState }}</span>
              <span
                :class="[
                  scope.row.overdueStateId === '30' ? 'overdueStatRed' : '',
                  scope.row.overdueStateId === '20' ? 'overdueStatyellow' : ''
                ]"
                v-if="scope.row.overdueStateId !== '10' && scope.row.overdueStateId !== null && scope.row.overdueStateId !== ''"
              >({{ scope.row.overdueState }})</span>
            </template>
          </el-table-column>
          <el-table-column prop="eventTag" show-overflow-tooltip label="事件标签" width="240"></el-table-column>
          <el-table-column
            prop="reportSource"
            :key="Math.random()"
            v-if="activeName==='全部'"
            show-overflow-tooltip
            label="事件上报来源"
            width="110px"
          ></el-table-column>
          <el-table-column prop="taskId" show-overflow-tooltip label="事件编号" width="150px"></el-table-column>
          <el-table-column prop="recordId" show-overflow-tooltip label="案卷编号" width="150px"></el-table-column>
          <el-table-column prop="happenTime" show-overflow-tooltip label="事件发生时间" width="180px"></el-table-column>
          <el-table-column prop="reportPerson" show-overflow-tooltip label="上报人" width="90"></el-table-column>
          <el-table-column prop="syncTime" show-overflow-tooltip label="导入时间" width="180px"></el-table-column>
          <el-table-column prop="deadlineTime" show-overflow-tooltip label="事件处置截止时间" width="180px"></el-table-column>
          <el-table-column label="操作" width="150" fixed="right" align="right" header-align="left">
            <template slot-scope="scope">
              <el-button type="warning" size="mini" @click="handleEdit(scope.row, 1)" v-if="scope.row.zh === '1'">追回</el-button>
              <el-button type="success" size="mini" @click="handleEdit(scope.row, 0)" v-if="scope.row.xq === '1'">详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span
              class="total-number"
            >{{ total }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :page-sizes="[10, 20, 50, 100]"
            layout=" prev, pager,next, sizes, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </el-col>
    </el-card>
    <EventDetails :detailsDialog.sync="detailsDialog" :detailsDialogData.sync="detailsDialogData" @close="handleClose" />
    <TakeBackDialog
      :takeBackDialog.sync="takeBackDialog"
      :takeBackDialogData.sync="takeBackDialogData"
      :takeBackDialogTitle.sync="takeBackDialogTitle"
      @success="handleSuccess"
      @close="handleClose"
    />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import EventDetails from './eventDetails.vue'
import TakeBackDialog from '../takeBack/takeBackDialog.vue'
export default {
  name: 'eventHasSent',
  components: { EventDetails, TakeBackDialog },
  data() {
    return {
      name: '',
      cardNo: '',
      timeList: '',
      tableData: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      listLoading: false,
      reportSourceName: '全部',
      reportSourceId: '',
      tabList: [],
      checkSourceList: [
        {
          stateName: '待接收',
          stateId: '40'
        },
        {
          stateName: '处置中',
          stateId: '50'
        },
        {
          stateName: '已处置',
          stateId: '60'
        }
      ],
      activeName: '全部',
      searchForm: {},
      detailsDialog: false,
      detailsDialogData: [],
      tableHeight: 0,
      // 追回
      takeBackDialog: false,
      takeBackDialogData: [],
      takeBackDialogTitle: '',
      eventState: '' // 事件状态
    }
  },
  computed: {
    cerrorColor() {
      return (v) => {
        let color = ''
        switch (v) {
          case '10': // 报废
            color = { color: '#B0B0B0' }
            break
          case '20': //  已反馈
            color = { color: '#231FB3' }
            break
          case '30': // 待派遣
            color = { color: '#0FB4F0' }
            break
          case '40': // 待接收
            color = { color: '#0005BD' }
            break
          case '50': // 处置中
            color = { color: '#7030A0' }
            break
          case '60': // 已处置
            color = { color: '#1FA159' }
            break
          default:
            color = { color: '#000000' }
            break
        }
        return color
      }
    }
  },
  watch: {
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['table'].$el.offsetTop - 200)
    },
    total(v) {
      // if (v > 0) {
      //   this.exportDataDisabled = false
      // } else {
      //   this.exportDataDisabled = true
      // }
    },
    $route: {
      handler(v) {
        let state = ''
        switch (v.query.state) {
          case '待接收':
            state = '40'
            break
          case '处置中':
            state = '50'
            break
          case '已处置':
            state = '60'
            break
          default:
            state = ''
            break
        }
        this.eventState = state
        this.searchData()
      },
      immediate: true
    }
  },
  mounted() {
    this.searchData()
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['table'].$el.offsetTop - 200
    })
  },
  methods: {
    handleClose() {
      this.$nextTick(() => {
        this.$refs.table.doLayout()
      })
    },
    // 序号
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleEdit(row, index) {
      if (index === 0) {
        this.detailsDialog = true
        this.detailsDialogData = row
        this.handleReading(row)
      } else if (index === 1) {
        // 排他操作
        let url1 = '/binhuapis/event/checkEventState'
        let params1 = {
          eventId: row.eventId,
          eventState: row.eventStateId
        }
        axios.post(url1, qs.stringify(params1)).then((r) => {
          if (r.data.code === 200) {
            this.takeBackDialog = true
            this.takeBackDialogData = row
            this.takeBackDialogTitle = '坐席追回'
          } else {
            this.$notify({
              title: '警告',
              message: '该事件状态有更新，请刷新页面或重新查询',
              type: 'warning'
            })
          }
        })
      }
    },
    // 根据tabList 的name进行筛选
    tabListsScreen() {
      let param = {
        eventState: this.eventState
      }
      // if (this.searchForm.eventState) {
      //   param.eventState = this.searchForm.eventState
      // }
      axios.get('/binhuapis/event/queryEventStatistics?' + qs.stringify(param)).then((res) => {
        this.tabList = res.data.data
      })
    },
    // 标签页修改
    handleClick(v) {
      this.currentPage = 1
      this.reportSourceName = v.name
      this.reportSourceId = v.label
      this.searchData()
    },
    searchDataCilck() {
      this.currentPage = 1
      this.searchData()
    },
    searchData() {
      this.tabListsScreen()
      let param = {
        pageStart: this.currentPage,
        pageLen: this.pageSize,
        button: '1',
        eventState: this.eventState
      }
      if (this.searchForm.eventTitle) {
        param.eventName = this.searchForm.eventTitle.trim()
      }
      // if (this.searchForm.eventState) {
      //   param.eventState = this.searchForm.eventState
      // }
      if (this.searchForm.taskId) {
        param.taskId = this.searchForm.taskId.trim()
      }
      if (this.reportSourceName !== '全部') {
        param.reportSource = this.reportSourceId
      }
      if (this.searchForm.time) {
        if (this.searchForm.time.length === 2) {
          param.beginTime = this.searchForm.time[0]
          param.endTime = this.searchForm.time[1]
        }
      }
      this.listLoading = true
      axios.get('/binhuapis/event/queryEventInfoList?' + qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          this.tableData = res.data.data.list
          this.total = parseInt(res.data.data.count)
          this.$nextTick(() => {
            this.$refs.table.doLayout()
          })
          this.listLoading = false
        } else {
          this.tableData = []
          this.listLoading = false
          this.$notify({
            title: '警告',
            message: '查询失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    handleSuccess() {
      this.currentPage = 1
      this.searchData()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.searchData()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.searchData()
    },
    // 已读
    handleReading(row) {
      console.log(row, 'rowrow')
      let params1 = {
        userId: sessionStorage.getItem('userId'),
        eventIdList: [row.eventId]
      }
      let url = '/binhuapis/event/updateEventReadUserIds'
      axios.post(url, params1).then((res) => {
        if (res.data.code === 200) {
          this.searchData()
        } else {
          this.$notify.error({
            title: '错误',
            message: res.data.message,
            duration: 1000
          })
        }
      })
    },
    // 根据已读未读修改行样式
    tableRowClassName({ row, rowIndex }) {
      if (row.isRead === 1) {
        return 'success-row'
      } else if (row.isRead === 0) {
        return 'warning-row'
      }
      return 'warning-row'
    }
  }
}
</script>
<style lang="scss">
.eventHasSent {
  width: 100%;
  height: 100%;
  .eventSink-table {
    .warning-row {
      font-weight: 700 !important;
    }
    .success-row {
      font-weight: 400 !important;
    }
  }
}
</style>
