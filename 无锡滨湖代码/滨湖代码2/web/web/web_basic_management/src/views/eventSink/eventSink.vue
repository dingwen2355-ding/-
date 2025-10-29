<template>
  <div class="eventSink">
    <!-- 面包屑 -->

    <!-- <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>事件接入</el-breadcrumb-item>
      <el-breadcrumb-item>事件接收</el-breadcrumb-item>
    </el-breadcrumb>-->

    <el-card
      class="box-card"
      style="height: calc(100% - 40px);display:none;"
      v-loading="loading"
      element-loading-text="正在导出"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
    >
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
        <div class="mrgin-t">
          <div>
            <el-popover placement="bottom" class="popoper-button" width="200" trigger="manual" v-model="visible">
              <div class="down-button">
                <a class="sel_btn" href="javascript:void(0)" @click="download(0)">
                  12345事件模板
                  <i class="el-icon-download"></i>
                </a>
              </div>
              <!-- <div class="down-button">
                <a class="sel_btn" download="网格事件模板" href="javascript:void(0);">
                  网格事件模板
                  .
                  <i class="el-icon-download"></i>
                </a>
              </div>-->
              <div class="down-button">
                <a class="sel_btn" href="javascript:void(0)" @click="download(1)">
                  自登记事件模板
                  <i class="el-icon-download"></i>
                </a>
              </div>
              <el-button slot="reference" type="primary" @click="templateDown">模板下载</el-button>
            </el-popover>
            <el-popover
              placement="bottom"
              class="popoper-button"
              width="200"
              trigger="manual"
              content="这是一段内容,这是一段内容,这是一段内容,这是一段内容。"
              v-model="visible1"
            >
              <el-upload
                class="btn2"
                ref="upload12345"
                action="/"
                :file-list="fileList"
                :show-file-list="false"
                :on-change="handleChange"
                :auto-upload="false"
                style="display: inline-block"
              >
                <el-button type="primary" icon="el-icon-upload2">12345事件导入</el-button>
              </el-upload>
              <!-- <el-upload
                class="btn2"
                ref="uploadNet"
                action="/"
                :file-list="fileList1"
                :show-file-list="false"
                :on-change="handleChange1"
                :auto-upload="false"
                disabled
                style="display: inline-block"
              >
                <el-button type="primary" icon="el-icon-upload2" disabled>网格事件导入</el-button>
              </el-upload>-->
              <el-upload
                class="btn2"
                ref="uploadEvent"
                action="/"
                :file-list="fileList2"
                :show-file-list="false"
                :on-change="handleChange2"
                :auto-upload="false"
                style="display: inline-block"
              >
                <el-button type="primary" icon="el-icon-upload2">自登记事件导入</el-button>
              </el-upload>
              <el-button slot="reference" type="primary" @click="templateSearch">批量导入</el-button>
            </el-popover>
            <el-button
              type="primary"
              @click="exportData"
              :loading="exportDataLoading"
              :disabled="exportDataDisabled"
            >批量导出</el-button>
          </div>
          <!-- <el-button type="primary" @click="searchData">查询</el-button> -->
          <div>
            <el-button type="primary" class="createBtn" @click="createEvent">创建事件</el-button>
          </div>
        </div>
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
          :row-class-name="tableRowClassName"
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
          <el-table-column label="操作" width="100" fixed="right">
            <template slot-scope="scope">
              <!-- <el-button type="warning" size="mini" @click="auditCilck(scope.row)" :disabled="scope.row.isDisabled">审核</el-button> -->
              <el-button type="success" size="mini" @click="handleEdit(scope.row)" v-if="scope.row.xq === '1'">详情</el-button>
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
      @close="handleClose"
      @refresh="handleRefresh"
    />
    <EventCreate :dialog.sync="dialog" :dialogData.sync="dialogData" @success="handleSuccess" @close="handleClose" />
  </div>
</template>

<script>
import axios from '@/utils/request'
import qs from 'qs'
import EventDetails from './eventDetails'
import EventCreate from './eventCreate'
export default {
  name: 'eventSink',
  components: { EventDetails, EventCreate },
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
    total(v) {
      if (v > 0) {
        this.exportDataDisabled = false
      } else {
        this.exportDataDisabled = true
      }
    },
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200)
    },
    $route: {
      handler(v) {
        let state = ''
        switch (v.query.state) {
          case '报废':
            state = '10'
            break
          case '已反馈':
            state = '20'
            break
          case '待派遣':
            state = '30'
            break
          case '退回待派遣':
            state = '35'
            break
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
    // this.searchData()
    // this.$nextTick(function () {
    //   this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200
    // })
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
    download(state) {
      let geturl = ''
      let fileName = ''
      if (state === 0) {
        geturl = 'template/12345工单事件模板.xls'
        fileName = '12345工单事件模板.xls'
      } else if (state === 1) {
        geturl = 'template/自登记事件模板.xlsx'
        fileName = '自登记事件模板.xlsx'
      }
      axios.get(geturl, {
        responseType: 'blob'
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        let fname = fileName
        link.href = url
        link.setAttribute('download', fname)
        document.body.appendChild(link)
        link.click()
      }).catch(error => {
        console.log('error:' + JSON.stringify(error))
      })
    },
    // 审核
    auditCilck(v) {
      this.$confirm('是否确认通过审核此事件?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          v.isDisabled = true
          this.$notify({
            title: '成功',
            message: '审核成功',
            type: 'success',
            duration: 1000
          })
          console.log(this.tableData)
          this.$forceUpdate()
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消审核',
            duration: 1000
          })
        })
    },
    // 导入
    handleChange(file, fileList) {
      this.file = file
      this.fileList = fileList
      this.importExcel()
    },
    importExcel() {
      this.file = this.fileList[0]
      const types = this.file.name.split('.')[this.file.name.split('.').length - 1]
      const fileType = ['xlsx', 'xls'].some((item) => item === types)
      if (!fileType) {
        this.$notify({
          title: '警告',
          message: '格式错误！请重新选择',
          type: 'warning',
          duration: 1000
        })
        return
      }
      let formData = new FormData()
      formData.append('file', this.file.raw)
      formData.append('reportSourceId', 2)
      let url = '/binhuapis/event/importEventInfoList'
      // console.log('导入url', url, 'formData', formData)
      let file = formData
      axios
        .post(url, file)
        .then((res) => {
          if (res.data.code === 200) {
            this.$notify({
              title: '成功',
              message: '导入成功',
              type: 'success',
              duration: 1000
            })
            this.$refs.upload12345.clearFiles()
            this.searchData()
            this.visible1 = !this.visible1
          } else {
            this.$notify.error({
              title: '错误',
              message: res.data.message,
              duration: 1000
            })
            this.$refs.upload12345.clearFiles()
          }
        })
        .catch((err) => {
          console.log(err)
          this.$notify.error({
            title: '错误',
            message: '导入失败',
            duration: 1000
          })
        })
    },
    // 导入
    handleChange1(file, fileList) {
      this.file1 = file
      this.fileList1 = fileList
      this.importExcel1()
    },
    importExcel1() {
      this.file1 = this.fileList[0]
      const types = this.file1.name.split('.')[this.file1.name.split('.').length - 1]
      const fileType = ['xlsx', 'xls'].some((item) => item === types)
      if (!fileType) {
        this.$notify({
          title: '警告',
          message: '格式错误！请重新选择',
          type: 'warning',
          duration: 1000
        })
        return
      }
      let formData = new FormData()
      formData.append('file', this.file1.raw)
      formData.append('reportSourceId', 2)
      let url = '/binhuapis/event/importEventInfoList'
      // console.log('导入url', url, 'formData', formData)
      let file = formData
      axios
        .post(url, file)
        .then((res) => {
          if (res.data.code === 200) {
            this.$notify({
              title: '成功',
              message: '导入成功',
              type: 'success',
              duration: 1000
            })
            this.$refs.uploadNet.clearFiles()
            this.searchData()
            this.visible1 = !this.visible1
          } else {
            this.$notify.error({
              title: '错误',
              message: res.data.message,
              duration: 1000
            })
            this.$refs.uploadNet.clearFiles()
          }
        })
        .catch((err) => {
          console.log(err)
          this.$notify.error({
            title: '错误',
            message: '导入失败',
            duration: 1000
          })
        })
    },
    // 导入
    handleChange2(file, fileList) {
      this.file2 = file
      this.fileList2 = fileList
      this.importExcel2()
    },
    importExcel2() {
      this.file2 = this.fileList2[0]
      const types = this.file2.name.split('.')[this.file2.name.split('.').length - 1]
      const fileType = ['xlsx', 'xls'].some((item) => item === types)
      if (!fileType) {
        this.$notify({
          title: '警告',
          message: '格式错误！请重新选择',
          type: 'warning',
          duration: 1000
        })
        return
      }
      let formData = new FormData()
      formData.append('file', this.file2.raw)
      formData.append('reportSourceId', 2)
      let url = '/binhuapis/event/importEventByExcel'
      // console.log('导入url', url, 'formData', formData)
      let file = formData
      axios
        .post(url, file)
        .then((res) => {
          if (res.data.code === 200) {
            this.$notify({
              title: '成功',
              message: '导入成功',
              type: 'success',
              duration: 1000
            })
            this.$refs.uploadEvent.clearFiles()
            this.searchData()
            this.visible1 = !this.visible1
          } else {
            this.$notify.error({
              title: '错误',
              message: res.data.message,
              duration: 1000
            })
            this.$refs.uploadEvent.clearFiles()
          }
        })
        .catch((err) => {
          console.log(err)
          this.$notify.error({
            title: '错误',
            message: '导入失败',
            duration: 1000
          })
        })
    },
    // 序号
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 创建事件
    createEvent() {
      this.dialog = true
    },
    handleEdit(row) {
      this.detailsDialog = true
      this.detailsDialogData = row
      this.handleReading(row)
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
    // 根据tabList 的name进行筛选
    tabListsScreen() {
      let param = {
        eventState: this.eventState
      }
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
    templateDown() {
      this.visible = !this.visible
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
      // const link = document.createElement('a')
      // link.href = '/static/hesuan.xls'
      // link.setAttribute('download', '核酸查询模板.xls')
      // document.body.appendChild(link)
      // link.click()
    },
    templateSearch() {
      this.visible1 = !this.visible1
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
      // this.$refs.upload.click()
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
        eventState: this.eventState
        // eventState: '30'
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
          this.tableData = res.data.data.list.map(e => {
            e.isDisabled = false
            return e
          })
          this.$nextTick(() => {
            this.$refs.multipleTable.doLayout()
          })
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
      this.searchData()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.searchData()
    },
    exportData() {
      this.loading = true
      let param = {
        pageStart: 1,
        pageLen: 100000,
        eventState: this.eventState
      }
      if (this.searchForm.eventTitle) {
        param.eventName = this.searchForm.eventTitle.trim()
      }
      // if (this.searchForm.eventState) {
      //   param.eventState = this.searchForm.eventState
      // }
      if (this.searchForm.address) {
        param.address = this.searchForm.address.trim()
      }
      if (this.reportSourceName !== '全部') {
        param.reportSource = this.reportSourceId
      }
      if (this.searchForm.time && this.searchForm.time.length === 2) {
        param.beginTime = this.searchForm.time[0]
        param.endTime = this.searchForm.time[1]
      }
      let url = '/binhuapis/event/exportEventInfoList?'
      axios({
        method: 'get',
        url: url + qs.stringify(param),
        responseType: 'blob',
        headers: {
          'Content-Type': 'application/json'
        }
        // getResponse: true
      })
        .then((res) => {
          if (res.status === 200) {
            const link = document.createElement('a')
            let blob = new Blob([res.data], { type: 'application/x-excel' })
            link.style.display = 'none'
            link.href = URL.createObjectURL(blob)
            // 从响应头获取文件名
            const filename = res.headers?.['content-disposition'].split('attachment;filename=')[1]
            link.download = decodeURI(filename)
            document.body.appendChild(link)
            link.click()
            document.body.removeChild(link)
            this.$notify({
              title: '成功',
              message: '导出成功',
              type: 'success',
              duration: 1000
            })
            this.loading = false
          } else {
            this.loading = false
            this.$message({
              type: 'error',
              message: '导出失败'
            })
          }
        })
        .catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '导出失败',
            duration: 1000
          })
        })
      this.$nextTick(() => {
        this.$refs.multipleTable.doLayout()
      })
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
.eventSink {
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
}
.custom_table ::v-deep .el-table__fixed-right,
.custom_table::v-deep .el-table__fixed {
  height: 100% !important;
}
</style>
