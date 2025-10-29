<template>
  <div>
    <el-dialog
      class="MyInformation"
      :title="title"
      :visible.sync="$store.getters.informationVisible"
      width="1700px"
      height="800px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-card class="box-card" style="margin-bottom: 20px">
          <!-- 标签页 -->

          <el-col :span="24" class="search-container">
            <el-form :inline="true" :model="searchForm" ref="form" label-width="100px">
              <el-form-item label="公告类型">
                <el-select v-model="searchForm.announcementType" filterable clearable placeholder="请选择公告类型">
                  <el-option
                    v-for="item in operateModules1"
                    :key="item.index"
                    :label="item.operateModule"
                    :value="item.operateModule"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="接收状态">
                <el-select v-model="searchForm.isAccept" filterable clearable placeholder="请选择公告类型">
                  <el-option
                    v-for="item in operateModules"
                    :key="item.index"
                    :label="item.operateModule"
                    :value="item.operateModule"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="公告内容">
                <el-input clearable v-model="searchForm.content" placeholder="请输入公告内容"></el-input>
              </el-form-item>

              <el-form-item label="事件发生时间">
                <el-date-picker
                  v-model="searchForm.time"
                  type="daterange"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
                <!-- :default-time="['00:00:00', '23:59:59']" -->
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="searchData">查询</el-button>
              </el-form-item>
            </el-form>
            <div class="mrgin-t">
              <el-button type="primary" @click="receive">接收</el-button>
            </div>
          </el-col>
          <!--列表-->
          <el-col :span="24" class="table-col">
            <el-table
              :data="tableData"
              highlight-current-row
              v-loading="listLoading"
              style="width: 100%"
              height="500px"
              @selection-change="handleSelectionChange"
              class="eventSink-table"
              :row-class-name="tableRowClassName"
            >
              <el-table-column type="selection" width="55" :selectable="checkSelectable"></el-table-column>
              <el-table-column type="index" label="序号" :index="table_index" width="65"></el-table-column>
              <el-table-column prop="alarmId" label="预警编号" width="190px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="announcementType" label="公告类型" width="90px" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.announcementType === '超期告警'" style="color: #d9001b">超期告警</span>
                  <span v-if="scope.row.announcementType === '催办告警'" style="color: #ff6600">催办告警</span>
                  <span v-if="scope.row.announcementType === '即将超期'" style="color: #f59a23">即将超期</span>
                </template>
              </el-table-column>
              <el-table-column prop="isAccept" label="接收状态" width="80px" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="scope.row.isAccept === '未接收'" style="color: #d9001b">{{ scope.row.isAccept }}</span>
                  <span v-if="scope.row.isAccept === '已接收'">{{ scope.row.isAccept }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="content" label="公告内容" width="300px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="eventId" label="事件编号" width="220px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="eventTitle" label="事件标题" width="80px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="startTime" label="事件发生时间" width="160px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="endTime" label="处理截止时间" width="180px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="personName" label="事件处置人" width="90px" show-overflow-tooltip></el-table-column>
              <el-table-column prop="phone" label="处置人电话" width="180px" show-overflow-tooltip></el-table-column>
              <el-table-column label="操作" width="150" fixed="right" align="right" header-align="left">
                <template slot-scope="scope">
                  <el-button
                    type="primary"
                    size="mini"
                    @click="handleEdit(scope.row, 1)"
                    v-if="scope.row.isAccept === '未接收'"
                  >接收</el-button>
                  <el-button type="success" size="mini" @click="handleEdit(scope.row, 0)">详情</el-button>
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
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                :page-sizes="[10, 20, 50, 100]"
                layout=" prev, pager,next, sizes, jumper"
                :total="total"
              ></el-pagination>
            </div>
          </el-col>
        </el-card>
      </div>
    </el-dialog>
    <InformationDetailsDialog :detailsDialog.sync="detailsDialog" :detailsDialogData.sync="detailsDialogData" />
    <el-dialog
      title="接收信息"
      :visible.sync="receiveDialog"
      width="600px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleCloseReceive"
      class="ReceiveInsert"
    >
      <div class="info_body">
        <el-form :inline="true" ref="form" :rules="rules" :model="form" label-width="110px">
          <!-- <el-form-item label="接收状态" prop="isAccept">
            <el-select v-model="form.isAccept" filterable clearable placeholder="请选择接收状态" disabled>
              <el-option
                v-for="item in operateModules"
                :key="item.index"
                :label="item.operateModule"
                :value="item.operateModule"
              ></el-option>
            </el-select>
          </el-form-item>-->

          <el-form-item label="备注">
            <el-input clearable v-model="form.remark" placeholder="请输入"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseReceive()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/request'
import InformationDetailsDialog from './informationDetailsDialog.vue'
export default {
  name: 'planSet',
  props: [],
  components: { InformationDetailsDialog },
  data() {
    return {
      title: '我的消息',
      tableData: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      listLoading: false,
      activeName: '全部',
      searchForm: {
        announcementType: '',
        content: '',
        isAccept: '',
        time: ''
      },
      multipleSelection: [],
      detailsDialog: false,
      detailsDialogData: {},
      receiveDialog: false,
      operateModules1: [
        { index: 1, operateModule: '超期告警' },
        { index: 2, operateModule: '催办告警' },
        { index: 3, operateModule: '即将超期' }
      ],
      operateModules: [
        { index: 1, operateModule: '已接收' },
        { index: 2, operateModule: '未接收' }
      ],
      form: {
        isAccept: '已接收',
        remark: '',
        alarmIds: ''
      },
      rules: {
        isAccept: [{ required: true, message: '请输入', trigger: 'blur' }]
      },
      selectionIds: []
    }
  },
  watch: {},
  mounted() {
    this.getInformation()
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 按条件控制多选是否置灰
    checkSelectable(row) {
      // row就是表格的每行数据， 我们判断审核状态是否不等于1。
      // 如果不等于1返回true 就是可勾选，如果等于1 返回false 多选置灰
      return row.isAccept !== '已接收'
    },
    getInformation() {
      this.listLoading = true
      let params = {
        pageStart: this.currentPage,
        pageLen: this.pageSize
      }
      if (this.searchForm.content) {
        params.content = this.searchForm.content
      }
      if (this.searchForm.isAccept) {
        params.isAccept = this.searchForm.isAccept
      }
      if (this.searchForm.announcementType) {
        params.announcementType = this.searchForm.announcementType
      }
      if (this.searchForm.time && this.searchForm.time.length > 1) {
        params.startTime = this.searchForm.time[0]
        params.endTime = this.searchForm.time[1]
      }
      let url = '/binhuapis/alarmInform/selAlarmInform'
      axios.get(url, { params }).then((r) => {
        if (r.data.code === 200) {
          this.tableData = r.data.data.list || []
          this.total = r.data.data.count || 0
          this.listLoading = false
        }
      })
    },
    // 操作
    handleEdit(row, index) {
      if (index === 0) {
        this.detailsDialog = true
        this.detailsDialogData = row
      } else if (index === 1) {
        this.receiveDialog = true
        this.form.alarmIds = row.alarmId
      }
    },
    handleCloseReceive() {
      this.receiveDialog = false
      this.getInformation()
      this.form = {
        isAccept: '',
        remark: '',
        alarmIds: ''
      }
      this.currentPage = 1
      this.receiveDialog = false
    },
    handleOk() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let url = ''
          url = '/binhuapis/alarmInform/update?alarmIds=' + this.form.alarmIds + '&remark=' + this.form.remark
          axios.post(url).then((r) => {
            if (r.data.code === 200) {
              this.$notify({
                title: '成功',
                message: '操作成功',
                type: 'success',
                duration: 1000
              })
              this.handleCloseReceive()
              this.$root.eventBus.$emit('alarmInformUpdate', true)
            } else {
              this.$notify.error({
                title: '错误',
                message: r.data.message,
                duration: 1000
              })
            }
          })
        } else {
          this.$notify({
            title: '警告',
            message: '请检查表单的数据合法性！！',
            type: 'warning',
            duration: 1000
          })
          return false
        }
      })
    },
    // 接收
    receive() {
      if (this.selectionIds.length === 0) {
        this.$notify({
          title: '警告',
          message: '请先勾选接收项',
          type: 'warning',
          duration: 1000
        })
      } else {
        let ids = this.selectionIds.join(',')
        this.deleteSome(ids)
      }
    },
    handleClose() {
      this.$store.dispatch('view/setInformationvisible', false)
    },
    handleSelectionChange(rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.alarmId)
      })
    },
    deleteSome(ids) {
      let url = ''
      url = '/binhuapis/alarmInform/update?alarmIds=' + ids + '&isAccept=' + '已接收'
      axios.post(url).then((r) => {
        if (r.data.code === 200) {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success',
            duration: 1000
          })
          this.getInformation()
          this.currentPage = 1
          this.$root.eventBus.$emit('alarmInformUpdate', true)
        } else {
          this.$notify.error({
            title: '错误',
            message: r.data.message,
            duration: 1000
          })
        }
      })
    },
    searchData() {
      this.currentPage = 1
      this.getInformation()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getInformation()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getInformation()
    },
    // 根据已读未读修改行样式
    tableRowClassName({ row, rowIndex }) {
      if (row.isAccept === '已接收') {
        return 'success-row'
      } else if (row.isAccept === '未接收') {
        return 'warning-row'
      }
      return 'warning-row'
    }
  }
}
</script>
<style lang="scss">
.MyInformation {
  .el-dialog {
    max-height: 850px;
    margin-top: 10vh !important;
  }
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
.ReceiveInsert {
  .el-form--inline {
    .el-select {
      display: block;
    }
    .el-form-item__content {
      width: 400px;
    }
    .whole-line {
      width: 100%;

      .el-form-item__content {
        width: 585px;
      }
    }
  }
}
</style>
