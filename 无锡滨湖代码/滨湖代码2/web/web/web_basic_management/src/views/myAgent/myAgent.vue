<template>
  <div class="myAgent">
    <!-- 面包屑 -->

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>事件处置</el-breadcrumb-item>
      <el-breadcrumb-item>我的待办</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card" style="height: calc(100% - 40px)">
      <!-- 标签页 -->
      <el-col :span="24" class="search-container">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane :label="item.sourceId" :name="item.sourceName" v-for="item in tabList" :key="item.sourceName">
            <span slot="label">
              {{ item.sourceName }}
              <el-badge :value="item.count" />
            </span>
          </el-tab-pane>
        </el-tabs>
      </el-col>

      <el-col :span="24" class="search-container">
        <el-form :inline="true" :model="searchForm" ref="form" label-width="100px">
          <el-form-item label="事件标题">
            <el-input clearable v-model="searchForm.eventTitle" placeholder="请输入事件标题"></el-input>
          </el-form-item>
          <el-form-item label="状态">
            <el-select filterable v-model="searchForm.eventState" placeholder="请选择状态" clearable style="width: 220px">
              <el-option
                v-for="item in checkSourceList"
                :value="item.stateId"
                :label="item.stateName"
                :key="item.stateId"
              />
            </el-select>
          </el-form-item>
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
        <div class="mrgin-t">
          <el-button type="primary" @click="receive" :disabled="multipleSelection.length === 0">接收</el-button>
          <el-button type="danger" @click="reject" :disabled="multipleSelection.length === 0">吹哨处置</el-button>
        </div>
      </el-col>
      <!--列表-->
      <el-col :span="24" class="table-col">
        <el-table
          :data="tableData"
          highlight-current-row
          v-loading="listLoading"
          style="width: 100%"
          :height="tableHeight"
          ref="multipleTable"
          @select="handleSelectionChange"
          @select-all="handleSelectionChangeAll"
          class="eventSink-table"
          :row-class-name="tableRowClassName"
        >
          <el-table-column type="selection" width="55" :selectable="checkSelectable"></el-table-column>
          <el-table-column type="index" show-overflow-tooltip label="序号" :index="table_index" width="70"></el-table-column>
          <el-table-column prop="eventName" show-overflow-tooltip label="事件标题" width="150px"></el-table-column>
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
            width="110"
          ></el-table-column>
          <el-table-column prop="taskId" show-overflow-tooltip label="事件编号" width="150px"></el-table-column>
          <el-table-column prop="recordId" show-overflow-tooltip label="案卷编号" width="150px"></el-table-column>
          <el-table-column prop="happenTime" show-overflow-tooltip label="事件发生时间" width="180px"></el-table-column>
          <el-table-column prop="reportPerson" show-overflow-tooltip label="上报人" width="90"></el-table-column>
          <el-table-column prop="syncTime" show-overflow-tooltip label="导入时间" width="180px"></el-table-column>
          <el-table-column prop="deadlineTime" show-overflow-tooltip label="事件处置截止时间" width="180px"></el-table-column>
          <el-table-column label="操作" fixed="right" width="300" align="right" header-align="left">
            <template slot-scope="scope">
              <el-button type="info" size="mini" @click="handleEdit(scope.row, 1)" v-if="scope.row.js === '1'">接收</el-button>
              <el-button type="primary" size="mini" @click="handleEdit(scope.row, 5)" v-if="scope.row.pq === '1'">派遣</el-button>
              <el-button type="primary" size="mini" @click="handleEdit(scope.row, 3)" v-if="scope.row.cl === '1'">处理</el-button>
              <el-button type="warning" size="mini" @click="handleEdit(scope.row, 4)" v-if="scope.row.cx === '1'">撤销</el-button>
              <el-button type="danger" size="mini" @click="handleEdit(scope.row, 2)" v-if="scope.row.fwss === '1'">吹哨处置</el-button>
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
    <EventDetails
      :detailsDialog.sync="detailsDialog"
      :detailsDialogData.sync="detailsDialogData"
      :detailsDialogTitle.sync="detailsDialogTitle"
      @success="handleSuccess"
      @close="handleClose"
    />
    <DealDialog :dealDialog.sync="dealDialog" :dealDialogData.sync="dealDialogData" @success="handleSuccess" />
    <ReceiveDialog
      :receiveDialog.sync="receiveDialog"
      :receiveDialogData.sync="receiveDialogData"
      :receiveMessage.sync="receiveMessage"
      @success="handleSuccess"
      @close="handleClose"
    />
    <!-- 派遣 -->
    <EventToBeSendDialog
      :dialog.sync="dialog"
      :dialogData.sync="dialogData"
      @success="handleSuccess"
      @close="handleClose"
    />
    <!-- 撤销 -->
    <RevocationDialog
      :revocationDialog.sync="revocationDialog"
      :revocationDialogData.sync="revocationDialogData"
      @success="handleSuccess"
      @close="handleClose"
    />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import EventDetails from '../eventHasSent/eventDetails.vue'
import DealDialog from './dealDialog.vue'
import ReceiveDialog from './receiveDialog.vue'
import EventToBeSendDialog from '../eventToBeSent/eventToBeSentDialog'
import RevocationDialog from './revocationDialog.vue'

export default {
  name: 'myAgent',
  components: { EventDetails, DealDialog, ReceiveDialog, EventToBeSendDialog, RevocationDialog },
  data() {
    return {
      tableHeight: 0,
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
        }
      ],
      activeName: '全部',
      searchForm: {},
      multipleSelection: [],
      //  详情
      detailsDialog: false,
      detailsDialogData: [],
      // 处理
      dealDialog: false,
      dealDialogData: [],
      // 接收
      receiveDialog: false,
      receiveDialogData: [],
      receiveMessage: '',
      detailsDialogTitle: '',
      // 派遣
      dialog: false,
      dialogData: [],
      // 撤销
      revocationDialog: false,
      revocationDialogData: []
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
    total(v) {
      // if (v > 0) {
      //   this.exportDataDisabled = false
      // } else {
      //   this.exportDataDisabled = true
      // }
    },
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200)
    }
  },
  mounted() {
    this.searchData()
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200
    })
  },
  methods: {
    handleClose() {
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
    },
    // 序号
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    checkSelectable(row) {
      // row就是表格的每行数据， 我们判断审核状态是否不等于1。
      // 如果不等于1返回true 就是可勾选，如果等于1 返回false 多选置灰
      return row.js === '1'
    },
    // 操作
    handleEdit(row, index) {
      if (index === 0) {
        this.detailsDialog = true
        this.detailsDialogData = row
        this.detailsDialogTitle = '我的待办'
        this.handleReading(row)
      } else {
        // 排他操作
        let url1 = '/binhuapis/event/checkEventState'
        let params1 = {
          eventId: row.eventId,
          eventState: row.eventStateId
        }
        axios.post(url1, qs.stringify(params1)).then((r) => {
          if (r.data.code === 200) {
            if (index === 3) {
              this.dealDialog = true
              this.dealDialogData = row
            } else if (index === 1) {
              this.receiveMessage = '接收'
              this.receiveDialog = true
              this.receiveDialogData = row
            } else if (index === 2) {
              this.receiveMessage = '吹哨处置(处置)'
              this.receiveDialog = true
              this.receiveDialogData = row
            } else if (index === 5) {
              // 派遣
              this.dialog = true
              this.dialogData = row
            } else if (index === 4) {
              // 撤销
              this.revocationDialog = true
              this.revocationDialogData = row
            }
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
    // 接收
    receive() {
      let eventIds = ''
      if (this.multipleSelection.length === 0) {
        this.$notify({
          title: '警告',
          message: '请先勾选接收项',
          type: 'warning',
          duration: 1000
        })
      } else {
        this.multipleSelection.some((e) => {
          eventIds += e.eventId + ','
        })
        eventIds = eventIds.substring(0, eventIds.lastIndexOf(','))
        // 排他操作
        let url1 = '/binhuapis/event/checkEventState'
        let params1 = {
          eventId: eventIds,
          eventState: '40'
        }
        axios.post(url1, qs.stringify(params1)).then((r) => {
          if (r.data.code === 200) {
            this.receiveMessage = '接收'
            this.receiveDialog = true
            this.receiveDialogData = this.multipleSelection
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
    // 吹哨处置
    reject() {
      let eventIds = ''
      if (this.multipleSelection.length === 0) {
        this.$notify({
          title: '警告',
          message: '请先勾选接收项',
          type: 'warning',
          duration: 1000
        })
      } else {
        this.multipleSelection.some((e) => {
          eventIds += e.eventId + ','
        })
        eventIds = eventIds.substring(0, eventIds.lastIndexOf(','))
        // 排他操作
        let url1 = '/binhuapis/event/checkEventState'
        let params1 = {
          eventId: eventIds,
          eventState: '40'
        }
        axios.post(url1, qs.stringify(params1)).then((r) => {
          if (r.data.code === 200) {
            this.receiveMessage = '吹哨处置(处置)'
            this.receiveDialog = true
            this.receiveDialogData = this.multipleSelection
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
    showTag() {
      let handleSelectData = this.multipleSelection || []
      for (let i = 0; i < this.tableData.length; i++) {
        for (let j = 0; j < handleSelectData.length; j++) {
          if (this.tableData[i].eventId === handleSelectData[j].eventId) {
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
          if (e.eventId === row.eventId) {
            index = i
            return true
          }
        })
        this.multipleSelection.splice(index, 1)
      }
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
    },
    // 全选
    handleSelectionChangeAll(val) {
      if (val.length === 0) {
        this.tableData.forEach((t) => {
          let index = -1
          this.multipleSelection.some((e, i) => {
            if (e.eventId === t.eventId) {
              index = i
              return true
            }
          })
          this.multipleSelection.splice(index, 1)
        })
      } else {
        val.forEach((e) => {
          let ids = this.multipleSelection.map((m) => m.eventId)
          if (!ids.includes(e.eventId)) {
            this.multipleSelection.push(e)
          }
        })
      }
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
    },
    // 根据tabList 的name进行筛选
    tabListsScreen() {
      let param = {
        eventState: '40,50'
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
        button: '2',
        eventState: '40,50'
      }
      if (this.searchForm.eventTitle) {
        param.eventName = this.searchForm.eventTitle.trim()
      }
      if (this.searchForm.eventState) {
        param.eventState = this.searchForm.eventState
      }
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
          this.showTag()
          this.$nextTick(() => {
            this.$refs.multipleTable.doLayout()
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
      this.multipleSelection = []
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
.myAgent {
  width: 100%;
  height: 100%;
  .mrgin-t {
    display: flex;
    justify-content: flex-end;
  }
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
