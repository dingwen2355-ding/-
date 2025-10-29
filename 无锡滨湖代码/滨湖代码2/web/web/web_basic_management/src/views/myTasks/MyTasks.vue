<template>
  <div class="MyTasks">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="任务名称">
        <el-input
          clearable
          v-model="searchForm.taskTitle"
          style="width: 220px"
          placeholder="请输入任务名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="事件名称">
        <el-input
          clearable
          v-model="searchForm.eventTitle"
          style="width: 220px"
          placeholder="请输入事件名称"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="mini" @click="onSubmit">查询</el-button>
        <el-button size="mini" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <h4 style="color: #1c1c1c; font-size: 16px; margin: 0">查询结果</h4>
        </div>
        <!-- <div class="content-right">
          <el-button icon="iconfont icon-button_xinjian" type="primary" size="mini" @click="addData"
            >新增记录</el-button
          >
          <el-button icon="iconfont icon-button_shanchu" type="danger" size="mini" @click="deleteData">删除</el-button>
        </div>-->
      </div>
      <el-table
        :data="tableData"
        stripe
        ref="multipleTable"
        style="width: 100%"
        :height="tableHeight"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="taskTitle" label="任务名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="eventTitle" label="事件名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="nodeName" label="节点名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="taskContent" label="任务内容" width="250" show-overflow-tooltip></el-table-column>
        <el-table-column prop="status" label="反馈状态" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.status == '0'" style="color: red">未反馈</span>
            <span v-if="scope.row.status == '1'" style="color: green">已反馈</span>
          </template>
        </el-table-column>
        <el-table-column prop="issueTime" label="任务下派时间" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row, 1)" type="text" size="mini">查看</el-button>
            <el-button @click="handleEdit(scope.row, 2)" type="text" size="mini">响应</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-body">
        <el-pagination
          background
          :current-page="currentPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size="pageSize"
          :page-sizes="[10, 20, 30, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <add-dialog
      :dialogVisible.sync="showAdd1"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    ></add-dialog>
    <MyTasksAdd
      :dialogVisible.sync="showAdd"
      :formInfo="formInfo"
      :isCode="isCode"
      @success="success"
    />
  </div>
</template>
<script>
import request from '@/utils/request'
import addDialog from './addEventDialog.vue'
import MyTasksAdd from './MyTasksAdd.vue'
export default {
  components: { addDialog, MyTasksAdd },
  name: 'MyTasks',
  data() {
    return {
      tableHeight: 0,
      kindList: [{ id: '工具', name: '工具' }],
      searchForm: {
        taskTitle: '',
        eventTitle: ''
      },
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],
      showAdd: false,
      showAdd1: false,
      formInfo: {},
      isCode: 0 // 0 编辑 2 新增
    }
  },
  mounted() {
    this.queryDataTable()
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 150
    })
  },
  watch: {
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 150)
    }
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`)
      this.pageSize = val
      this.queryDataTable()
    },

    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`)
      this.currentPage = val
      this.queryDataTable()
    },
    queryDataTable() {
      this.loading = true
      let url = '/binhuapis/dapeng/eventTask/query-condition?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (sessionStorage.getItem('userId')) {
        url = url + '&personId=' + sessionStorage.getItem('userId')
      }
      if (this.searchForm.taskTitle) {
        url = url + '&taskTitle=' + this.searchForm.taskTitle
      }
      if (this.searchForm.eventTitle) {
        url = url + '&eventTitle=' + this.searchForm.eventTitle
      }
      request.get(url).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list
        this.total = res.data.data.total
      })
    },
    // 查询
    onSubmit() {
      this.currentPage = 1
      this.queryDataTable()
    },
    // 重置
    resetSearch() {
      this.searchForm = {
        taskTitle: '',
        eventTitle: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },

    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        taskTitle: '',
        typeName: '',
        streetName: '',
        communityName: '', // 数量
        levelName: '', // 单位
        eventTime: '',
        smallTypeName: '',
        impactLevel: '', // 所属场所Id
        monitorUnit: '', // 所属场所Id
        occurAddress: '',
        minorNum: '',
        seriousNum: '',
        deathNum: '',
        economicLoss: '', // 备注
        isConclusion: '',
        summary: '',
        isCommand: '',
        eventNodeList: '',
        lon: '',
        lat: '',
        file: ''
      }
      this.isCode = 2
    },

    success() {
      this.currentPage = 1
      this.queryDataTable()
    },

    // 选中的节点数据
    handleSelectionChange(rows) {
      this.multipleSelection = rows
      // console.log('选中的节点数据: ', this.multipleSelection)
    },

    // 编辑/详情
    handleEdit(row, code) {
      if (code === 1) {
        // 1 -- 详情
        this.isCode = 1
        this.showAdd = true
      } else if (code === 2) {
        // 0 -- 编辑
        this.isCode = 2
        this.showAdd1 = true
      }
      this.formInfo = {
        taskTitle: '',
        typeName: '',
        streetName: '',
        communityName: '', // 数量
        levelName: '', // 单位
        eventTime: '',
        smallTypeName: '',
        impactLevel: '', // 所属场所Id
        monitorUnit: '', // 所属场所Id
        occurAddress: '',
        minorNum: '',
        seriousNum: '',
        deathNum: '',
        economicLoss: '', // 备注
        isConclusion: '',
        summary: '',
        isCommand: '',
        eventNodeList: '',
        lon: '',
        lat: '',
        file: ''
      }
      this.formInfo = row
    }
  }
}
</script>
<style lang="scss">
.MyTasks {
  width: 100%;
  height: calc(100% - 10px);
  box-sizing: border-box;
  padding: 20px;
  background-color: #fff;
  .content-top {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    .content-right {
      margin-right: 20px;
    }
  }
}
</style>
