<template>
  <div class="NotificationManager">
    <div class="informationSearch">
      <el-form :inline="true" :model="searchForm" ref="form" label-width="80px">
        <el-form-item label="消息名称">
          <el-input
            clearable
            v-model="searchForm.messageTitle"
            placeholder="请输入消息名称"
            style="width: 160px"
          ></el-input>
        </el-form-item>
        <el-form-item label="消息内容">
          <el-input
            clearable
            v-model="searchForm.messageContent"
            placeholder="请输入消息内容"
            style="width: 160px"
          ></el-input>
        </el-form-item>
        <!-- <el-form-item label="所属科室">
          <el-select
            filterable
            v-model="searchForm.precinctName"
            placeholder="请选择所属科室"
            clearable
            style="width: 220px"
          >
            <el-option
              v-for="item in queryPrecinctList"
              :value="item.precinctName"
              :label="item.precinctName"
              :key="item.precinctName"
            />
          </el-select>
        </el-form-item>-->
        <el-form-item>
          <el-button type="primary" style="margin-left: 40px" size="mini" @click="queryClick">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleEdit({}, 2)" size="mini">新增</el-button>
        </el-form-item>
      </el-form>
      <el-button style="position:relative;left: 807px;top: 50px" type="primary" size="mini">合并</el-button>
    </div>
    <div class="content">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="未读消息" name="first">
          <el-table :data="tableData" v-loading="listLoading" style="width: 100%" height="650">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column type="index" label="序号" :index="table_index" min-width="7%"></el-table-column>
            <el-table-column
              prop="messageContent"
              label="消息详情"
              min-width="60%"
              show-overflow-tooltip
            ></el-table-column>
            <!-- <el-table-column prop="messageType" label="状态" min-width="10%" show-overflow-tooltip></el-table-column> -->
            <el-table-column label="发送科室（状态）" min-width="25%" show-overflow-tooltip>
              <template slot-scope="scope">
                <div>
                  <span>{{ scope.row.sendPrecinctName }}</span>&nbsp;
                  <span
                    :class="scope.row.messageType === '2' ? 'green' : 'red'"
                  >({{ scope.row.messageType === '2' ? '已读' : '未读' }})</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="sendTime" label="发送时间" min-width="20%" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" min-width="15%">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="handleEdit(scope.row, 0)">详情</el-button>
                <el-button type="primary" size="mini" @click="readCilck(scope.row)">已读</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              layout="total, prev, pager, next, jumper"
              :total="total"
            ></el-pagination>
          </div>
        </el-tab-pane>
        <el-tab-pane label="已读消息" name="second">
          <el-table :data="tableData" v-loading="listLoading" style="width: 100%" height="650">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column type="index" label="序号" :index="table_index" min-width="7%"></el-table-column>
            <el-table-column
              prop="messageContent"
              label="消息详情"
              min-width="60%"
              show-overflow-tooltip
            ></el-table-column>
            <!-- <el-table-column prop="messageType" label="状态" min-width="10%" show-overflow-tooltip></el-table-column> -->
            <el-table-column label="发送科室（状态）" min-width="25%" show-overflow-tooltip>
              <template slot-scope="scope">
                <div>
                  <span>{{ scope.row.sendPrecinctName }}</span>&nbsp;
                  <span
                    :class="scope.row.messageType === '2' ? 'green' : 'red'"
                  >({{ scope.row.messageType === '2' ? '已读' : '未读' }})</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="sendTime" label="发送时间" min-width="20%" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" min-width="15%">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="handleEdit(scope.row, 4)">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              layout="total, prev, pager, next, jumper"
              :total="total"
            ></el-pagination>
          </div>
        </el-tab-pane>
        <el-tab-pane label="已发消息" name="third">
          <el-table :data="tableData" v-loading="listLoading" style="width: 100%" height="650">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column type="index" label="序号" :index="table_index" min-width="7%"></el-table-column>
            <el-table-column prop="messageTitle" label="消息标题" min-width="20%" show-overflow-tooltip></el-table-column>
            <el-table-column
              prop="messageContent"
              label="消息详情"
              min-width="60%"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column prop="sendTime" label="发送时间" min-width="20%" show-overflow-tooltip></el-table-column>
            <el-table-column label="操作" min-width="15%">
              <template slot-scope="scope">
                <el-button type="success" size="mini" @click="handleEdit(scope.row, 3)">详情</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pagination">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="pageSize"
              layout="total, prev, pager, next, jumper"
              :total="total"
            ></el-pagination>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <NotificationManagerAdd
      :detailsDialog.sync="detailsDialog"
      :detailsDialogData.sync="detailsDialogData"
      :dialogTitle.sync="dialogTitle"
      @success="handleSuccess"
    />
  </div>
</template>

<script>
import NotificationManagerAdd from './NotificationManagerAdd.vue'
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'NotificationManager',
  props: [],
  components: { NotificationManagerAdd },
  data() {
    return {
      title: '消息管理',
      tableData: [],
      total: 0,
      currentPage: 1,
      pageSize: 10,
      searchForm: {
        messageTitle: '',
        messageContent: '',
        precinctName: ''
      },
      activeName: 'first',
      listLoading: false,
      detailsDialog: '',
      detailsDialogData: {},
      dialogTitle: '',
      queryPrecinctList: [],
      tabName: '未读消息'
    }
  },
  watch: {},
  mounted() {
    this.getQueryPrecinctList()
    this.getInformation()
  },
  beforeDestroy() {
    this.$root.eventBus.$off('messageUpdate')
  },
  methods: {
    getQueryPrecinctList() {
      axios.get('/binhuapis/queryPrecinct').then((res) => {
        if (res.data.code === 200) {
          this.queryPrecinctList = res.data.data
        }
      })
    },
    // tab栏切换
    handleClick(tab, event) {
      this.searchForm = {
        messageTitle: '',
        messageContent: '',
        precinctName: ''
      }
      if (tab.label === '未读消息') {
        this.tabName = '未读消息'
      } else if (tab.label === '已读消息') {
        this.tabName = '已读消息'
      } else if (tab.label === '已发消息') {
        this.tabName = '已发消息'
      }
      this.currentPage = 1
      this.getInformation()
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 已读
    readCilck(v) {
      this.$confirm('是否将此消息置为已读?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let param = {
            Id: v.id,
            messageType: '2'
          }
          axios.post('/binhuapis/updateMessageType?' + qs.stringify(param)).then((res) => {
            console.log(res)
            if (res.data.code === 200) {
              this.$root.eventBus.$emit('messageUpdate', true)
              this.getInformation()
              this.$notify({
                title: '成功',
                message: '已成功将此消息置为已读',
                type: 'success',
                duration: 1000
              })
            } else {
              this.$notify({
                title: '警告',
                message: '错误，' + res.data.message,
                type: 'warning',
                duration: 1000
              })
            }
          })
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消',
            duration: 1000
          })
        })
    },
    // 操作按钮
    handleEdit(row, index) {
      if (index === 0) {
        this.detailsDialog = true
        this.detailsDialogData = row
        this.dialogTitle = '未读消息详情'
      } else if (index === 2) {
        this.detailsDialog = true
        this.dialogTitle = '新增消息'
      } else if (index === 3) {
        this.detailsDialog = true
        this.detailsDialogData = row
        this.dialogTitle = '已发消息详情'
      } else if (index === 4) {
        this.detailsDialog = true
        this.detailsDialogData = row
        console.log(row, '}}}}}}}}}}}}}}}')
        this.dialogTitle = '已读消息详情'
      }
    },
    queryClick() {
      this.currentPage = 1
      this.getInformation()
    },
    getInformation() {
      if (this.tabName === '已发消息') {
        let param = {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          sendPrecinctName: sessionStorage.getItem('departmentName')
        }
        this.listLoading = true
        axios.get('/binhuapis/queryMessageInfo?' + qs.stringify(param)).then((res) => {
          if (res.data.code === 200) {
            if (res.data.data) {
              this.tableData = res.data.data.list
              this.total = parseInt(res.data.data.total)
            } else {
              this.tableData = []
              this.total = 0
            }
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
      } else {
        let param = {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          receivePerson: sessionStorage.getItem('user')
        }
        if (this.searchForm.messageTitle) {
          param.messageTitle = this.searchForm.messageTitle.trim()
        }
        if (this.searchForm.messageContent) {
          param.messageContent = this.searchForm.messageContent
        }
        if (this.tabName === '未读消息') {
          if (this.searchForm.precinctName) {
            param.sendPrecinctName = this.searchForm.precinctName
            param.receivePrecinctName = sessionStorage.getItem('departmentName')
          } else {
            param.sendPrecinctName = ''
            param.receivePrecinctName = sessionStorage.getItem('departmentName')
          }
          param.messageType = '1'
        } else if (this.tabName === '已读消息') {
          if (this.searchForm.precinctName) {
            param.sendPrecinctName = this.searchForm.precinctName
            param.receivePrecinctName = sessionStorage.getItem('departmentName')
          } else {
            param.sendPrecinctName = ''
            param.receivePrecinctName = sessionStorage.getItem('departmentName')
          }
          param.messageType = '2'
        } else if (this.tabName === '已发消息') {
          if (this.searchForm.precinctName) {
            param.sendPrecinctName = sessionStorage.getItem('departmentName')
            param.receivePrecinctName = this.searchForm.precinctName
          } else {
            param.sendPrecinctName = sessionStorage.getItem('departmentName')
            param.receivePrecinctName = ''
          }
        }
        this.listLoading = true
        axios.get('/binhuapis/queryMessage?' + qs.stringify(param)).then((res) => {
          if (res.data.code === 200) {
            if (res.data.data) {
              this.tableData = res.data.data.list
              this.total = parseInt(res.data.data.total)
            } else {
              this.tableData = []
              this.total = 0
            }
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
      }
    },
    handleClose() {
      this.$store.dispatch('view/setInformationvisible', false)
    },
    handleSuccess() {
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
    }
  }
}
</script>
<style lang="scss">
.NotificationManager {
  width: 100%;
  height: calc(100% - 10px);
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  padding: 20px;
  background-color: #fff;

  .red {
    color: #d9001b;
  }
  .green {
    color: #1fa159;
  }
  .informationSearch {
    margin-top: 7px;
    padding-top: 20px;
    display: flex;
    height: 10%;
    align-items: center;
    // background: #10355d;
  }
}
</style>
