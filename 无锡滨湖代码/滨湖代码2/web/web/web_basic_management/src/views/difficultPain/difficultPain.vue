<template>
  <div class="difficultPain">
    <!-- 面包屑 -->

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>痛点难点</el-breadcrumb-item>
      <el-breadcrumb-item>痛点难点事件</el-breadcrumb-item>
    </el-breadcrumb>

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
              start-placeholder="开始日期"
              end-placeholder="结束日期"
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
          ref="multipleTable"
          :height="tableHeight"
          class="eventSink-table"
        >
          <el-table-column type="index" label="序号" :index="table_index" width="70"></el-table-column>
          <el-table-column prop="eventName" show-overflow-tooltip label="事件标题" width="140px"></el-table-column>
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
          <!-- <el-table-column prop="address" show-overflow-tooltip label="事件发生地" width="250px"></el-table-column> -->
          <el-table-column prop="happenTime" show-overflow-tooltip label="事件发生时间" width="180px"></el-table-column>
          <el-table-column prop="reportPerson" label="上报人" width="90"></el-table-column>
          <el-table-column prop="syncTime" show-overflow-tooltip label="导入时间" width="180px"></el-table-column>
          <el-table-column prop="deadlineTime" show-overflow-tooltip label="事件处置截止时间" width="180px"></el-table-column>
          <el-table-column label="操作" width="150" fixed="right">
            <template slot-scope="scope">
              <el-button type="success" size="mini" @click="handleEdit(scope.row)">详情</el-button>
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
    <EventDetails :detailsDialog.sync="detailsDialog" :detailsDialogData.sync="detailsDialogData" />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import EventDetails from '../eventSink/eventDetails'
import { Button } from 'element-ui'
export default {
  name: 'difficultPain',
  components: { EventDetails },
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
      exportDataLoading: false,
      exportDataDisabled: true,
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
        },
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
      searchForm: {
        eventTitle: '',
        eventState: '',
        taskId: '',
        time: ''
      },
      multipleSelection: [],
      detailsDialog: false,
      detailsDialogData: [],
      dialog: false,
      dialogData: [],
      visible: false,
      visible1: false,
      fileList: [],
      file: null,
      fileList1: [],
      file1: null,
      fileList2: [],
      file2: null,
      loading: false,
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
    handleEdit(row) {
      this.detailsDialog = true
      this.detailsDialogData = row
    },
    // 序号
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // // 根据tabList 的name进行筛选
    // tabListsScreen() {
    //   let param = {
    //     eventState: this.eventState
    //   }
    //   axios.get('/binhuapis/event/queryEventStatistics?' + qs.stringify(param)).then((res) => {
    //     this.tabList = res.data.data
    //   })
    // },
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
      // this.tabListsScreen()
      let param = {
        pageStart: this.currentPage,
        pageLen: this.pageSize,
        // eventState: this.eventState
        // eventState: '30'
        isPainSpot: '1',
        button: '5'
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
          // this.showTag()
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
    },
    handleSizeChange(val) {
      this.pageSize = val
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
.difficultPain {
  width: 100%;
  height: 100%;
  .mrgin-t {
    display: flex;
    justify-content: space-between;

    .popoper-button {
      margin-right: 10px;
    }
  }
  // .createBtn {
  //   float: right;
  // }
  .eventSink-table {
    .warning-row {
      font-weight: 700 !important;
    }
    .success-row {
      font-weight: 400 !important;
    }
  }
  // .custom_table ::v-deep .el-table__fixed-right {
  //   height: 100% !important;
  // }
  .difficultPainDialog {
    .img {
      width: 90%;
      height: 320px;
      margin: 0 12px;
      /* border: black 1px solid;
  background-color: darkgrey; */
    }
    /* 图片自适应的样式 */
    .img-object {
      object-fit: contain;
      object-position: 50% 20%;
    }
  }
}
.custom_table ::v-deep .el-table__fixed-right,
.custom_table::v-deep .el-table__fixed {
  height: 100% !important;
}
</style>
