<template>
  <div class="Dictionaries">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>字典管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- <el-form class="znv-el-form" ref="form" :inline="true" :model="search">
      <el-form-item label="字典名称" style="margin-bottom: 0">
        <el-input placeholder="请输入字典名称" clearable v-model="search.username"></el-input>
      </el-form-item>
      <el-form-item label="字典类型" style="margin-bottom: 0">
        <el-input placeholder="请输入字典类型" clearable v-model="search.operateDetail"></el-input>
      </el-form-item>
      <el-form-item label="字典状态" style="margin-bottom: 0">
        <el-select v-model="search.operateModule" filterable clearable placeholder="请选择字典状态">
          <el-option
            v-for="item in operateModules"
            :key="item.index"
            :label="item.operateModule"
            :value="item.operateModule"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" style="margin-bottom: 0">
        <el-date-picker
          v-model="search.beginTime"
          type="daterange"
          range-separator="-"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item style="margin-bottom: 0">
        <el-button type="primary" @click="querySerch()">查询</el-button>
      </el-form-item>
    </el-form>-->
    <!-- 表格 -->
    <div class="alll-table">
      <div class="box-card-left">
        <!-- <div class="title">组织树</div> -->
        <div
          class="item"
          :class="isActived === item.id ? 'active' : ''"
          v-for="item in tabList"
          :key="item.id"
          @click="handleDetail(item)"
        >{{ item.name }}</div>
      </div>
      <el-card class="box-card" style="margin-top: 20px; width: calc(100% - 180px)">
        <div v-if="isActived === 0">
          <div class="table">
            <el-table
              :data="logData0.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
              highlight-current-row
              v-loading="listLoading"
              ref="multipleTable"
              :height="tableHeight"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="sourceName" label="字典名称"></el-table-column>
              <el-table-column prop="sourceId" label="字典编码"></el-table-column>
            </el-table>
          </div>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <div class="total-container">
              <span>
                显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
                <span
                  class="total-number"
                >{{ logData0.length }}</span> 条记录
              </span>
            </div>
            <div class="block">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, sizes, jumper"
                :total="logData0.length"
              ></el-pagination>
            </div>
          </el-col>
        </div>
        <div v-if="isActived === 1">
          <div class="table">
            <el-table
              :data="logData1.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
              highlight-current-row
              v-loading="listLoading"
              ref="multipleTable"
              :height="tableHeight"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="sourceName" label="字典名称"></el-table-column>
              <el-table-column prop="sourceCode" label="字典编码"></el-table-column>
            </el-table>
          </div>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <div class="total-container">
              <span>
                显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
                <span
                  class="total-number"
                >{{ logData1.length }}</span> 条记录
              </span>
            </div>
            <div class="block">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, sizes, jumper"
                :total="logData1.length"
              ></el-pagination>
            </div>
          </el-col>
        </div>
        <div v-if="isActived === 2">
          <div class="table">
            <el-table
              :data="logData2.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
              highlight-current-row
              v-loading="listLoading"
              ref="multipleTable"
              :height="tableHeight"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="stateName" label="字典名称"></el-table-column>
              <el-table-column prop="stateId" label="字典编码"></el-table-column>
            </el-table>
          </div>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <div class="total-container">
              <span>
                显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
                <span
                  class="total-number"
                >{{ logData2.length }}</span> 条记录
              </span>
            </div>
            <div class="block">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, sizes, jumper"
                :total="logData2.length"
              ></el-pagination>
            </div>
          </el-col>
        </div>
        <div v-if="isActived === 3">
          <div class="table">
            <div class="tab-pane">
              <div
                class="tab-pane-item"
                v-for="item in logData3TabList"
                :key="item.id"
                :class="[logData3Active === item.id?'tab-pane-active':'']"
                @click="tabPaneClick(item.id)"
              >
                <span>{{ item.name }}</span>
              </div>
            </div>
            <el-table
              :data="logData3List.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
              highlight-current-row
              v-loading="listLoading"
              ref="multipleTable"
              :height="tableHeight"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="nodeName" label="字典名称"></el-table-column>
              <el-table-column prop="nodeId" label="字典编码"></el-table-column>
            </el-table>
          </div>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <div class="total-container">
              <span>
                显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
                <span
                  class="total-number"
                >{{ logData3List.length }}</span> 条记录
              </span>
            </div>
            <div class="block">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, sizes, jumper"
                :total="logData3List.length"
              ></el-pagination>
            </div>
          </el-col>
        </div>
        <div v-if="isActived === 4">
          <div class="table">
            <el-table
              :data="logData4.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
              highlight-current-row
              v-loading="listLoading"
              ref="multipleTable"
              :height="tableHeight"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="nodeName" label="字典名称"></el-table-column>
              <el-table-column prop="nodeId" label="字典编码"></el-table-column>
            </el-table>
          </div>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <div class="total-container">
              <span>
                显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
                <span
                  class="total-number"
                >{{ logData4.length }}</span> 条记录
              </span>
            </div>
            <div class="block">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, sizes, jumper"
                :total="logData4.length"
              ></el-pagination>
            </div>
          </el-col>
        </div>
        <div v-if="isActived === 5">
          <div class="table">
            <el-table
              :data="logData5.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
              highlight-current-row
              v-loading="listLoading"
              ref="multipleTable"
              :height="tableHeight"
              style="width: 100%"
            >
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="overdueName" label="字典名称"></el-table-column>
              <el-table-column prop="overdueId" label="字典编码"></el-table-column>
            </el-table>
          </div>
          <!--工具条-->
          <el-col :span="24" class="toolbar">
            <div class="total-container">
              <span>
                显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
                <span
                  class="total-number"
                >{{ logData5.length }}</span> 条记录
              </span>
            </div>
            <div class="block">
              <el-pagination
                @current-change="handleCurrentChange"
                @size-change="handleSizeChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, sizes, jumper"
                :total="logData5.length"
              ></el-pagination>
            </div>
          </el-col>
        </div>
      </el-card>
    </div>
    <DictionariesAdd :dialogVisible.sync="showAdd" :formInfo="formInfo" @success="handleSuccess" :isCode="isCode" />
  </div>
</template>

<script>
import axios from '@/utils/request'
import DictionariesAdd from './DictionariesAdd.vue'
export default {
  name: 'operate-manager',
  components: { DictionariesAdd },
  data() {
    return {
      tableHeight: 0,
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
        { index: 1, operateModule: '所有' },
        { index: 2, operateModule: '正常' },
        { index: 3, operateModule: '停用' }
      ],
      listLoading: false,
      logData: [],
      currentRow: null,
      selectionIds: [],
      currentPage: 1,
      pageSize: 10, // 每页显示几条
      showAdd: false,
      formInfo: null,
      isCode: 0,
      tabList: [
        {
          id: 0,
          name: '事件信息来源'
        },
        {
          id: 1,
          name: '事件上报来源'
        },
        {
          id: 2,
          name: '事件状态'
        },
        {
          id: 3,
          name: '事件标签'
        },
        {
          id: 4,
          name: '流程节点'
        },
        {
          id: 5,
          name: '超期状态'
        }
      ],
      isActived: 0,
      logData0: [],
      logData1: [],
      logData2: [],
      logData3: [],
      logData4: [],
      logData5: [],
      logData3TabList: [
        { id: 1, name: '一级分类' },
        { id: 2, name: '二级分类' },
        { id: 3, name: '三级分类' }
      ],
      logData3Active: 1,
      logData3List: [],
      logData3Copy: []
    }
  },
  watch: {
    tableHeight() {
      window.onresize = () => (this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200)
    }
  },
  mounted() {
    // this.refreshData()
    this.handleDetail(this.tabList[0])
    this.$nextTick(function () {
      this.tableHeight = window.innerHeight - this.$refs['multipleTable'].$el.offsetTop - 200
    })
  },
  methods: {
    handleDetail(item) {
      this.isActived = item.id
      this.currentPage = 1
      if (item.id === 0) {
        this.getDataList0()
      } else if (item.id === 1) {
        this.getDataList1()
      } else if (item.id === 2) {
        this.getDataList2()
      } else if (item.id === 3) {
        this.getDataList3()
      } else if (item.id === 4) {
        this.getDataList4()
      } else if (item.id === 5) {
        this.getDataList5()
      }
    },
    getDataList0() {
      this.logData0 = []
      let url = '/binhuapis/event/queryDictEventInfoSource'
      axios
        .get(url)
        .then((res) => {
          if (res.data.code === 200) {
            this.logData0 = res.data.data
          }
        })
        .catch(() => { })
    },
    getDataList1() {
      this.logData1 = []
      let url = '/binhuapis/event/queryDictEventReportSource'
      axios
        .get(url)
        .then((res) => {
          if (res.data.code === 200) {
            this.logData1 = res.data.data
          }
        })
        .catch(() => { })
    },
    getDataList2() {
      this.logData2 = []
      let url = '/binhuapis/event/queryDictEventState'
      axios
        .get(url)
        .then((res) => {
          if (res.data.code === 200) {
            this.logData2 = res.data.data
          }
        })
        .catch(() => { })
    },
    tabPaneClick(id) {
      this.logData3List = JSON.parse(JSON.stringify(this.logData3.filter((e) => {
        return [id].includes(e.nodeTreeLevel)
      })))
      console.log(this.logData3)
      this.logData3Active = id
    },
    convertTreeData(data) {
      for (let i = 0; i < data.length; i++) {
        if (data[i].childrenList !== undefined) {
          const temp = data[i].childrenList
          delete data[i].childrenList
          data = data.concat(temp)
        }
      }
      return data
    },
    getDataList3() {
      this.logData3 = []
      let url = '/binhuapis/event/getEventTagTree'
      axios
        .get(url)
        .then((res) => {
          if (res.data.code === 200) {
            // console.log(this.convertTreeData(res.data.data), 'this.convertTreeData(res.data.data)')
            this.logData3 = JSON.parse(JSON.stringify(this.convertTreeData(res.data.data)))
            this.tabPaneClick(1)
          }
        })
        .catch(() => { })
    },
    getDataList4() {
      this.logData4 = []
      let url = '/binhuapis/event/queryDictFlowNode'
      axios
        .get(url)
        .then((res) => {
          if (res.data.code === 200) {
            this.logData4 = res.data.data
          }
        })
        .catch(() => { })
    },
    getDataList5() {
      this.logData5 = []
      let url = '/binhuapis/event/queryDictOverDue'
      axios
        .get(url)
        .then((res) => {
          if (res.data.code === 200) {
            this.logData5 = res.data.data
          }
        })
        .catch(() => { })
    },
    handleAdd() {
      this.showAdd = true
      this.formInfo = null
      this.isCode = 0
    },
    handleList(row) {
      // this.$router.push({
      //   name: 'dictionaryList',
      //   query: { id: row.id }
      // })
      this.$store.dispatch('view/setDictionCheck', false)
      console.log(this.$store.getters.dictionCheck, '===================')
    },
    handleSuccess() {
      this.currentPage = 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    handleSelectionChange(rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.id)
      })
    }
  }
}
</script>

<style lang="scss">
.Dictionaries {
  width: 100%;
  height: 100%;
  background: #f8f9ff;
  overflow: auto;
  .alll-table {
    width: 100%;
    display: flex;
    height: calc(100% - 20px);
    .box-card-left {
      width: 160px;
      background-color: #fff;
      padding: 20px;
      height: calc(100% - 20px);
      overflow: auto;
      margin-right: 20px;
      margin-top: 20px;
      box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
      border: 1px solid #ebeef5;
      color: #303133;
      .title {
      }
      .item {
        font-size: 14px;
        cursor: pointer;
        width: 100%;
        height: 50px;
        line-height: 50px;
        padding: 0 10px;
      }
      .item:hover {
        background-color: #ecf5ff;
      }
      .active {
        background-color: #ecf5ff;
        color: #409eff;
      }
    }
  }
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
  // 409EFF
  .tab-pane {
    display: flex;
    .tab-pane-item {
      padding: 0 20px;
      height: 40px;
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      line-height: 40px;
      display: inline-block;
      list-style: none;
      font-size: 14px;
      font-weight: 500;
      border-bottom: 2px solid #e4e7ed;
      cursor: pointer;
    }
    .tab-pane-item:hover {
      color: #409eff;
      border-bottom: 2px solid #409eff;
    }
    .tab-pane-active {
      color: #409eff;
      border-bottom: 2px solid #409eff;
    }
  }
}
</style>
