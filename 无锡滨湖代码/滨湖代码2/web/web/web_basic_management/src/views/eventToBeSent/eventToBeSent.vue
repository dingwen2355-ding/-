<template>
  <div class="eventToBeSent">
    <!-- 面包屑 -->

    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>事件派遣</el-breadcrumb-item>
      <el-breadcrumb-item>事件待派遣</el-breadcrumb-item>
    </el-breadcrumb>-->

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
        <div class="mrgin-t">
          <el-button
            type="primary"
            @click="send"
            :disabled="multipleSelection.length === 0"
            v-if="activeName!=='全部'"
          >批量派遣</el-button>
        </div>
      </el-col>
      <!--列表 -->
      <el-col :span="24" class="table-col">
        <el-table
          :data="tableData"
          highlight-current-row
          v-loading="listLoading"
          style="width: 100%"
          ref="multipleTable"
          @select="handleSelectionChange"
          @select-all="handleSelectionChangeAll"
          :height="tableHeight"
          class="eventSink-table"
          :row-class-name="tableRowClassName"
        >
          <el-table-column
            v-if="activeName!=='全部'"
            type="selection"
            width="55"
            :selectable="checkSelectable"
            :key="Math.random()"
          ></el-table-column>
          <el-table-column
            type="index"
            :key="Math.random()"
            show-overflow-tooltip
            label="序号"
            :index="table_index"
            width="70"
          ></el-table-column>
          <el-table-column prop="eventName" :key="Math.random()" show-overflow-tooltip label="事件标题" width="150px"></el-table-column>
          <!-- <el-table-column prop="isCommunity" show-overflow-tooltip label="是否区级网格事件" width="135">
            <template slot-scope="scope">
              <span v-if="scope.row.isCommunity === '1'">是</span>
            </template>
          </el-table-column>-->
          <el-table-column prop="eventState" :key="Math.random()" show-overflow-tooltip label="状态" width="120px">
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
          <el-table-column prop="eventTag" :key="Math.random()" show-overflow-tooltip label="事件标签" width="240"></el-table-column>
          <el-table-column
            prop="reportSource"
            :key="Math.random()"
            v-if="activeName==='全部'"
            show-overflow-tooltip
            label="事件上报来源"
            width="110px"
          ></el-table-column>
          <el-table-column prop="taskId" :key="Math.random()" show-overflow-tooltip label="事件编号" width="150px"></el-table-column>
          <el-table-column prop="recordId" :key="Math.random()" show-overflow-tooltip label="案卷编号" width="150px"></el-table-column>
          <el-table-column prop="happenTime" :key="Math.random()" show-overflow-tooltip label="事件发生时间" width="180px"></el-table-column>
          <el-table-column prop="reportPerson" :key="Math.random()" show-overflow-tooltip label="上报人" width="90"></el-table-column>
          <el-table-column prop="syncTime" :key="Math.random()" show-overflow-tooltip label="导入时间" width="180px"></el-table-column>
          <el-table-column
            prop="deadlineTime"
            :key="Math.random()"
            show-overflow-tooltip
            label="事件处置截止时间"
            width="180px"
          ></el-table-column>
          <el-table-column label="操作" fixed="right" width="400" align="right" header-align="left">
            <template slot-scope="scope">
              <!-- <el-button type="primary" size="mini" @click="handleEdit(scope.row, 1)" v-if="scope.row.zt === '1'">痛点难点</el-button> -->
              <el-button type="primary" size="mini" @click="handleEdit(scope.row, 1)" v-if="scope.row.pq === '1' ">派遣</el-button>
              <!-- && currentTime < scope.row.deadlineTime -->
              <el-button type="danger" size="mini" @click="handleEdit(scope.row, 2)" v-if="scope.row.fwss === '1'">吹哨处置</el-button>
              <button
                class="command"
                @click="handleEdit(scope.row,3)"
                v-if="scope.row.zt === '1' && scope.row.isPainSpot==='0' "
              >痛点难点</button>
              <el-button type="info" size="mini" @click="handleRest(scope.row)" v-if="scope.row.bf === '1'">报废</el-button>
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
    <EventToBeSendDialog
      :dialog.sync="dialog"
      :dialogData.sync="dialogData"
      :dialogTitle.sync="dialogTitle"
      @success="handleSuccess"
      @close="handleClose"
      @refresh="handleRefresh"
    />
    <EventDetails
      :detailsDialog.sync="detailsDialog"
      :detailsDialogData.sync="detailsDialogData"
      :detailsDialogTitle.sync="detailsDialogTitle"
      @success="handleSuccess"
      @close="handleClose"
      @refresh="handleRefresh"
    />
    <ReceiveDialog
      :receiveDialog.sync="receiveDialog"
      :receiveDialogData.sync="receiveDialogData"
      :receiveMessage.sync="receiveMessage"
      @success="handleSuccess"
      @close="handleClose"
    />
    <scrapDia
      :receiveDialog.sync="scrapDiaVisisve"
      :receiveDialogData.sync="scrapDiaData"
      @success="handleSuccess"
      @close="handleClose"
    />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import EventToBeSendDialog from './eventToBeSentDialog'
import EventDetails from '../eventHasSent/eventDetails.vue'
import ReceiveDialog from '../myAgent/receiveDialog.vue'
import scrapDia from './scrapDia.vue'
import moment from 'moment'
export default {
  name: 'eventToBeSent',
  components: { EventToBeSendDialog, EventDetails, ReceiveDialog, scrapDia },
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
          stateName: '报废',
          stateId: '10'
        },
        {
          stateName: '已反馈',
          stateId: '20'
        },
        {
          stateName: '待派遣',
          stateId: '30'
        }
      ],
      activeName: '全部',
      searchForm: {},
      dialog: false,
      dialogData: [],
      dialogTitle: '',
      detailsDialog: false,
      detailsDialogData: [],
      // 接收
      receiveDialog: false,
      receiveDialogData: [],
      receiveMessage: '',
      detailsDialogTitle: '',
      scrapDiaVisisve: false,
      scrapDiaData: null,
      multipleSelection: [],
      eventState: '', // 事件状态
      currentTime: moment().format('yyyy-MM-DD HH:mm:ss')
    }
  },
  beforeUpdate() {
    // 切换表头，让某些表头显示和隐藏。这个时候切换时，解决表头会出现抖动的现象
    this.$nextTick(() => {
      // 在数据加载完，重新渲染表格
      this.$refs.multipleTable.doLayout()
    })
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
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200)
    },
    $route: {
      handler(v) {
        let state = ''
        switch (v.query.state) {
          case '报废': // 报废
            state = '10'
            break
          case '已反馈': //  已反馈
            state = '20'
            break
          case '待派遣': // 待派遣
            state = '30'
            break
          case '退回待派遣':
            state = '35'
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
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200
    })
  },
  methods: {
    // 刷新
    handleRefresh() {
      this.searchDataCilck()
    },
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
      // && this.currentTime < row.deadlineTime
      return row.pq === '1'
    },
    send() {
      this.dialog = true
      this.dialogData = this.multipleSelection
      this.dialogTitle = '批量派遣'
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
    handleRest(row) {
      this.scrapDiaVisisve = true
      this.scrapDiaData = row
      // let url1 = '/binhuapis/event/dump'
      // let params1 = {
      //   eventIds: row.eventId
      // }
      // axios.post(url1, qs.stringify(params1)).then((r) => {
      //   if (r.data.code === 200) {
      //   } else {
      //     this.$notify({
      //       title: '警告',
      //       message: '该事件状态有更新，请刷新页面或重新查询',
      //       type: 'warning'
      //     })
      //   }
      // })
    },
    handleEdit(row, index) {
      if (index === 0) {
        // 详情不做排他操作
        this.detailsDialog = true
        this.detailsDialogData = row
        this.detailsDialogTitle = '事件待派遣'
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
            if (index === 1) {
              this.dialog = true
              this.dialogData = row
              this.dialogTitle = '待派遣'
            } else if (index === 2) {
              this.receiveMessage = '吹哨处置(派遣)'
              this.receiveDialog = true
              this.receiveDialogData = row
            } else if (index === 3) {
              this.$confirm('是否将此事件转为痛点难点事件?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              })
                .then(() => {
                  let param = {
                    eventIds: row.eventId
                  }
                  axios.post('/binhuapis/event/painSpot', qs.stringify(param)).then((res) => {
                    if (res.data.code === 200) {
                      this.$notify({
                        title: '成功',
                        message: '转为痛点难点成功',
                        type: 'success',
                        duration: 1000
                      })
                      this.searchDataCilck()
                    }
                  })
                  // console.log(this.tableData)
                  // this.$forceUpdate()
                })
                .catch(() => {
                  this.$notify.info({
                    title: '消息',
                    message: '已取消',
                    duration: 1000
                  })
                })
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
      this.multipleSelection = []
      this.$nextTick(() => {
        // 在数据加载完，重新渲染表格
        this.$refs.multipleTable.doLayout()
      })
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
        // isPainSpot: '0',
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
      this.multipleSelection = []
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
.eventToBeSent {
  width: 100%;
  height: 100%;
  .eventSink-table {
    .warning-row {
      font-weight: 700 !important;
    }
    .success-row {
      font-weight: 400 !important;
    }
    .command {
      margin: 0 10px;
      display: inline-block;
      line-height: 1;
      white-space: nowrap;
      cursor: pointer;
      background: #fff;
      border: 1px solid #dcdfe6;
      -webkit-appearance: none;
      text-align: center;
      box-sizing: border-box;
      outline: 0;
      transition: 0.1s;
      font-weight: 500;
      padding: 7px 15px;
      font-size: 12px;
      border-radius: 3px;
      color: #fff;
      background-color: #bd1efc;
      border-color: #bd1efc;
    }
    .command:hover {
      background: #bd1efcad;
      border-color: #bd1efcad;
    }
  }
}
</style>
