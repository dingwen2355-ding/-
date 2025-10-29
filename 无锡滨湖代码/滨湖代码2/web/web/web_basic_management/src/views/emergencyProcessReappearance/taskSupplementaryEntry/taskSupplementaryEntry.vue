<template>
  <div class="taskSupplementaryEntry" style="height: calc(100% - 40px)">
    <div class="top-container">
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="事故标题：">
          <el-input v-model="form.eventTitle" placeholder="事故标题"></el-input>
        </el-form-item>
        <el-form-item label="事故类型：">
          <el-select v-model="form.eventTypeId" placeholder="请选择事故类型">
            <el-option v-for="item in eventTypeArr" :key="item.id" :label="item.dictName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="事故来源：">
          <el-select v-model="form.sourceId" placeholder="请选择事故来源">
            <el-option v-for="item in sourcesArr" :key="item.id" :label="item.dictName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属区划：">
          <el-input v-model="form.communityName" placeholder="所属区划"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" @click="onSubmit">查询</el-button>
          <el-button type="primary" size="mini"  @click="reSet">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="tools-container">
      <el-link icon="el-icon-s-tools" :underline="false" @click="manageNodeShow">节点管理</el-link>
    </div>
    <div class="main-container">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="scope">
            <zCollapse v-model="scope.row.collapseOpen" accordion style="margin-left: 50px">
              <zCollapseItem
                :title="item.nodeName"
                v-for="(item,index) in scope.row.eventNodeList"
                :key="index"
                :name="index"
              >
                <template v-slot:header>
                  <span @click.stop>
                    <el-switch
                      active-value="0"
                      inactive-value="1"
                      v-model="item.isHidden"
                      active-text
                      @change="changeHidden(item)"
                    ></el-switch>
                  </span>
                  <el-button
                    icon="el-icon-sort-down"
                    class="sort-btn-diy"
                    :disabled="index===scope.row.eventNodeList.length - 1"
                    @click.stop="doSort('down',scope.row,item,index)"
                  ></el-button>
                  <el-button
                    icon="el-icon-sort-up"
                    class="sort-btn-diy"
                    :disabled="index===0"
                    @click.stop="doSort('up',scope.row,item,index)"
                  ></el-button>
                  <el-button icon="el-icon-plus" class="tools-btn" @click.stop="addTask(item)"></el-button>
                  <!--增加任务-->
                  <el-button icon="el-icon-delete" class="tools-btn" @click.stop="deleteNode(item)"></el-button>
                  <!--删除节点-->
                </template>
                <template v-slot:main>
                  <el-table row-class-name="rowClass" :data="item.eventTaskList" style="width: 100%">
                    <el-table-column prop="taskTitle" label="任务标题" width="180"></el-table-column>
                    <el-table-column label="事件名称" width="180">
                      <template slot-scope="scope">{{scope.row.eventTitle}}</template>
                    </el-table-column>
                    <el-table-column label="节点名称" width="180">
                      <template>{{item.nodeName}}</template>
                    </el-table-column>
                    <el-table-column prop="issueTime" label="下发时间" width="180"></el-table-column>
                    <el-table-column prop="department" label="处置部门" width="180"></el-table-column>
                    <el-table-column prop="person" label="处置收人" width="180"></el-table-column>
                    <el-table-column label="处置状态" width="180">
                      <template slot-scope="sco">
                        <span>{{statusMap[sco.row.status]}}</span>
                      </template>
                    </el-table-column>
                    <el-table-column prop="dealTime" label="处置时间" width="180"></el-table-column>
                    <el-table-column prop="feedback" label="处置反馈内容" width="180"></el-table-column>
                    <el-table-column label="反馈附件" width="180">
                      <template slot-scope="sco">
                        <span v-if="sco.row.file && /^.+(\.mp4|\.MP4)$/.test(sco.row.file)">
                          <el-link target="_blank" :href="sco.row.file" type="primary">视频</el-link>
                        </span>
                        <span v-else-if="sco.row.file">
                          <el-link target="_blank" :href="sco.row.file" type="primary">附件</el-link>
                        </span>
                        <span v-else></span>
                      </template>
                    </el-table-column>
                    <el-table-column label="操作">
                      <template slot-scope="sco">
                        <i
                          class="el-icon-document"
                          @click.stop="detailTask(sco.row)"
                          style="font-size: 16px;color: #7F8892;margin-right: 24px;"
                        ></i>
                        <i
                          class="el-icon-edit-outline"
                          @click.stop="editTask(sco.row,item)"
                          style="font-size: 16px;color: #7F8892;margin-right: 24px;"
                        ></i>
                        <i
                          class="el-icon-delete"
                          @click.stop="deleteTask(sco.row,item)"
                          style="font-size: 16px;color: #7F8892;margin-right: 24px;"
                        ></i>
                      </template>
                    </el-table-column>
                  </el-table>
                </template>
              </zCollapseItem>
            </zCollapse>
          </template>
        </el-table-column>
        <el-table-column label="事故标题" prop="eventTitle"></el-table-column>
        <el-table-column label="所在区" prop="communityName"></el-table-column>
        <el-table-column label="所在街道" prop="streetName"></el-table-column>
        <el-table-column label="事发时间" prop="eventTime"></el-table-column>
        <el-table-column label="事发类型" prop="typeName"></el-table-column>
        <el-table-column label="事发详细地址" prop="occurAddress"></el-table-column>
        <el-table-column label="事发概况" prop="summary"></el-table-column>
        <el-table-column label="事故来源" prop="sourceName"></el-table-column>
        <el-table-column label="操作" prop="desc" width="130">
          <template slot-scope="scope">
            <el-button icon="el-icon-plus"  size="mini" @click="addNodeShow(scope.row)">增加节点</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="bottom-container">
      <el-pagination
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
        :current-page.sync="form.page"
        hide-on-single-page
        :page-size="form.pageSize"
        :page-sizes="[10, 20, 30, 40]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
    <addNode :dialogVisible.sync="addNodeDialogVisible" :formInfo="addNodeForm"></addNode>
    <addTask :dialogVisible.sync="addTaskDialogVisible" :formInfo="addTaskForm" :isCode="taskIsCode"></addTask>
    <manage-node :dialogVisible.sync="manageNodeDialogVisible"></manage-node>
  </div>
</template>

<script>
import request from '@/utils/request'
import zCollapse from '@/components/Z-Collapse/collapse/src/collapse'
import zCollapseItem from '@/components/Z-Collapse/collapse/src/collapse-item.vue'
import addNode from './modules/addNode'
import manageNode from './modules/manageNode'
import addTask from './modules/addTask'
export default {
  name: 'taskSupplementaryEntry',
  data() {
    return {
      form: {
        eventTitle: '',
        eventTypeId: '',
        sourceId: '',
        streetName: '',
        communityName: '',
        page: 1,
        pageSize: 10
      },
      total: 0,
      eventTypeArr: [],
      sourcesArr: [],
      tableData: [],
      addNodeDialogVisible: false,
      addNodeForm: {},
      manageNodeDialogVisible: false,
      manageNodeForm: {},
      addTaskDialogVisible: false,
      addTaskForm: {},
      taskIsCode: 2, // 0 编辑 1 查看详情 2 新增
      statusMap: {
        0: '未处置',
        1: '已处置'
      }
    }
  },
  components: {
    zCollapse: zCollapse,
    zCollapseItem: zCollapseItem,
    addNode: addNode,
    addTask: addTask,
    manageNode: manageNode
  },
  mounted() {
    this.getDictTree(1).then(res => {
      this.eventTypeArr = res.data.data
    })
    this.getDictTree(9).then(res => {
      this.sourcesArr = res.data.data
    })
    this.onSubmit()
  },
  methods: {
    doSort(type, ScopeRow, row, index) {
      if (type === 'up') {
        if (index === 0) {
          return
        }
        let moveItem = ScopeRow.eventNodeList[index]
        ScopeRow.eventNodeList.splice(index, 1)
        ScopeRow.eventNodeList.splice(index - 1, 0, moveItem)
      }

      if (type === 'down') {
        if (index === ScopeRow.length - 1) {
          return
        }
        let moveItem = ScopeRow.eventNodeList[index]
        ScopeRow.eventNodeList.splice(index, 1)
        ScopeRow.eventNodeList.splice(index + 1, 0, moveItem)
      }
      let params = ScopeRow.eventNodeList.map((t, i) => {
        return {
          id: t.id,
          order: i + 1
        }
      })
      let url = '/binhuapis/dapeng/eventNodeContact/edit-order'
      request.post(url, params).then(res => {
        if (res.data.code === 200) {
        } else {
          this.$message.error('操作失败')
        }
      })
    },
    sortEventNodeList(list) {
      const o = list
      return o.sort(function (a, b) {
        if (a.order > b.order) {
          return 1
        } else {
          return -1
        }
      })
    },
    onSubmit() {
      this.queryEventTask().then(res => {
        this.tableData = res.data.data.list.map(t => {
          t.collapseOpen = []
          t.eventNodeList = this.sortEventNodeList(t.eventNodeList)
          return t
        })
        this.total = res.data.data.total
      })
    },
    refreshData(type, eventId, nodeId) { // type: 可更新节点数据和任务数据  type:node & task
      this.queryEventTask().then(res => {
        if (type === 'node') {
          let tempTableData = res.data.data.list.map(t => {
            t.collapseOpen = []
            t.eventNodeList = this.sortEventNodeList(t.eventNodeList)
            return t
          })
          this.tableData.forEach(t => {
            if (t.id === eventId) {
              t.eventNodeList = tempTableData.filter(s => s.id === eventId)[0].eventNodeList
            }
          })
          this.total = res.data.data.total
        }
        if (type === 'task') {
          let tempTableData = res.data.data.list.map(t => {
            t.collapseOpen = []
            t.eventNodeList = this.sortEventNodeList(t.eventNodeList)
            return t
          })
          let eventTaskList = tempTableData.filter(t => t.id === eventId)[0].eventNodeList.filter(t => t.nodeId === nodeId).eventTaskList
          this.tableData.forEach(t => {
            if (t.id === eventId) {
              t.eventNodeList.forEach(s => {
                s.eventTaskList = eventTaskList
              })
            }
          })
          this.total = res.data.data.total
        }
      })
    },
    handleCurrentChange(val) {
      this.form.page = val
      this.onSubmit()
    },
    handleSizeChange(val) {
      this.form.pageSize = val
      this.onSubmit()
    },
    getDictTree(id) {
      let url = `/binhuapis/dict/dictTree?parentId=${id}`
      return request.get(url)
    },
    addNodeShow(row) {
      this.addNodeDialogVisible = true
      this.addNodeForm = {
        eventId: row.id,
        isHidden: '0',
        nodeId: null,
        order: row.eventNodeList.length
      }
    },
    manageNodeShow() {
      this.manageNodeDialogVisible = true
    },
    pushDeleteNode(row) {
      request.get(`/binhuapis/dapeng/eventNodeContact/del?id=${row.id}`).then(res => {
        this.refreshData('node', row.eventId)
        if (res.data.code === 200) {
          this.$message.success('删除成功!')
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    deleteNode(row) {
      this.$confirm('确定要删除节点么？', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '关闭窗口',
        type: 'warning'
      })
        .then(() => {
          this.pushDeleteNode(row)
        })
        .catch((e) => {
        })
    },
    addTask(item) {
      this.taskIsCode = 2
      this.addTaskDialogVisible = true
      this.addTaskForm = {
        contactId: item.id,
        eventId: item.eventId,
        dealTime: '',
        department: '', // 手填
        feedback: '',
        file: '', //
        issueTime: '',
        person: '',
        status: '',
        taskTitle: ''
      }
    },
    editTask(row, item) {
      this.taskIsCode = 0
      this.addTaskForm = row
      row.eventId = item.eventId
      this.addTaskDialogVisible = true
    },
    deleteTask(row, item) {
      this.$confirm('确定要删除任务么？', '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '关闭窗口',
        type: 'warning'
      })
        .then(() => {
          this.pushDeleteTask(row, item)
        })
        .catch((e) => {
        })
    },
    pushDeleteTask(row, item) {
      let url = `/binhuapis/dapeng/eventTask/del?id=${row.id}` + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      request.get(url).then(res => {
        this.refreshData('node', item.eventId, item.id)
        if (res.data.code === 200) {
          this.$message.success('删除成功!')
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    detailTask(row) {
      this.taskIsCode = 1
      this.addTaskForm = row
      this.addTaskDialogVisible = true
    },
    queryEventTask() {
      let params = this.form
      let url = `/binhuapis/dapeng/eventTask/query-event-task?eventTitle=${params.eventTitle}&eventTypeId=${params.eventTypeId}&sourceId=${params.sourceId}&streetName=${params.streetName}&communityName=${params.communityName}&page=${params.page}&pageSize=${params.pageSize}&status=42,43`
      return request.get(url)
    },
    changeHidden(item) {
      let params = {
        id: item.id,
        isHidden: item.isHidden
      }
      let url = '/binhuapis/dapeng/eventNodeContact/edit'
      request.post(url, params).then(res => {

      })
    },
    reSet() {
      this.form = {
        eventTitle: '',
        eventTypeId: '',
        sourceId: '',
        streetName: '',
        communityName: '',
        page: 1,
        pageSize: 10
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.taskSupplementaryEntry {
  background: #ffffff;
  border-radius: 10px;
  padding: 10px;
  .top-container {
  }
  .tools-container {
    width: 100%;
    height: 45px;
    display: flex;
    flex-direction: row-reverse;
  }
  .main-container {
    ::v-deep .el-table {
      border-top: 1px solid #e9edf7;
      .el-table__header-wrapper {
        .el-table__header {
          thead {
            tr {
              background: #f9fbfd;
              th {
                background: transparent;
              }
            }
          }
        }
      }
      .el-table__body-wrapper {
        height: 606px;
        overflow-y: auto;
        .el-table__body {
          tbody {
            .el-table__row {
              background: #f9fbfd;
            }
          }
        }
      }
    }
  }

  .tools-btn {
    width: 20px;
    border: 0;
    padding: 0 20px;
  }

  .sort-btn-diy {
    width: 15px;
    border: 0;
    padding: 0 20px;
  }
  .rowClass {
    background: #f9fbfd !important;
  }
}
</style>
