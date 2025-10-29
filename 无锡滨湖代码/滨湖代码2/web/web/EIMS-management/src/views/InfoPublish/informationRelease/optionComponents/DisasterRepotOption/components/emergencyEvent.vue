<template>
  <div class="emergencyEvent">
    <div class="addEventInfo">
      <el-button
        type="primary"
        size="small"
        plain
        icon="el-icon-circle-plus-outline"
        @click="addEventInfoBtn()"
        v-if="isCode !== 1"
      >新增突发事件</el-button>
      <el-table
        :data="form.eventBeans.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)"
        border
        style="width: 100%; height: 400px; overflow: auto"
        class="tableInfo"
      >
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="title" label="事件标题" width="200"></el-table-column>
        <el-table-column label="事件类型" width="200">
          <template slot-scope="scope">
            <span>{{ scope.row.type }}/{{ scope.row.type2 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="happenTime" label="事发时间" width="160"></el-table-column>
        <el-table-column prop="address" label="事发地点" width="200"></el-table-column>
        <el-table-column label="操作" width="210" v-if="isCode != 1">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewEventInfo(scope.row, scope.$index)">详情</el-button>
            <el-button type="text" size="small" @click="delEventInfo(scope.row, scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        :current-page.sync="currentPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :page-sizes="[10, 30, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="form.eventBeans.length"
      ></el-pagination>
    </div>
    <div v-if="addEventInfoDialog">
      <el-dialog
        title="新增突发事件"
        :visible.sync="addEventInfoDialog"
        width="700px"
        :modal-append-to-body="false"
        :append-to-body="false"
        :before-close="handleClose"
        :close-on-click-modal="false"
      >
        <div class="info_body">
          <div style="display: flex; margin-top: 5px">
            <el-input clearable placeholder="请输入搜索内容" v-model="search"></el-input>
            <el-button icon="el-icon-search" @click="rightSearch" style="margin-left: 10px"></el-button>
          </div>
          <div class="mainContent" style="overflow: hidden;height: 698px">
            <div style="margin-left: 10px; margin-bottom: 5px">
              <el-checkbox v-model="checkAll" @change="checkAllChange">全部选择</el-checkbox>
            </div>
            <div style="height: 100%; overflow: scroll">
              <el-tree
                :data="dataList"
                ref="rightForm"
                node-key="id"
                :props="defaultProps"
                :expand-on-click-node="true"
                show-checkbox
                @check-change="handleCheckChange"
                v-loading="rightLoading"
              >
                <div
                  style="width: 328px; height: 100%; display: flex; justify-content: space-between"
                  slot-scope="{ data }"
                >
                  <div
                    style="
                      font-size: 14px;
                      width: 228px;
                      white-space: nowrap;
                      overflow: hidden;
                      text-overflow: ellipsis;
                    "
                  >{{ data.title }}</div>
                </div>
              </el-tree>
            </div>
          </div>
          <span style="margin-top: 5px; float: right; font-size: 14px; color: #606266">共 {{ choseTotal }} 条数据</span>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="handleClose()">取 消</el-button>
          <el-button type="primary" @click="submitBtn()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <EmergencyManagementView :dialogVisible.sync="showEventInfoViewDialog" :eventId="eventId" />
  </div>
</template>
<script>
import EmergencyManagementView from '../../../../emergencyManagement/EmergencyManagementView.vue'
import Axios from 'axios'
export default {
  name: 'emergencyEvent',
  props: ['form', 'isCode'],
  components: { EmergencyManagementView },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      eventRowInfo: null,
      //   rules: {
      //   },
      addEventInfoDialog: false,
      showEventInfoViewDialog: false,
      search: '',
      checkAll: false,
      defaultProps: {
        id: 'id',
        label: 'title'
      },
      dataList: [],
      rightLoading: false,
      choseTotal: 0,
      chooseList: [],
      eventId: ''
    }
  },
  watch: {
    form(v) {
      // console.log(v, 'form-1111111111111111')
    }
  },
  mounted() {
    this.queryEventList()
  },
  methods: {
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    // 分页
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    // 查看
    viewEventInfo(row, index) {
      // console.log('eventInfo--index', index)
      this.eventRowInfo = (this.currentPage - 1) * this.pageSize + index
      // console.log('eventInfo--this.eventRowInfo--index', this.eventRowInfo)
      // console.log('eventInfo--row: ', row)
      this.showEventInfoViewDialog = true
      this.eventId = row.eventId
    },
    // 添加
    addEventInfoBtn() {
      this.addEventInfoDialog = true
    },
    // 删除
    delEventInfo(row, index) {
      // console.log('eventInfo--index', index)
      this.eventRowInfo = (this.currentPage - 1) * this.pageSize + index
      // console.log('eventRowInfo--this.eventRowInfo--index', this.eventRowInfo)
      // console.log('eventInfo--row: ', row)
      this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.form.eventBeans.splice(this.eventRowInfo, 1)
          this.currentPage = 1
          this.$message.success('删除成功！')
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    queryEventList() {
      this.rightLoading = true
      let url = '/gtw/EIMS-management/event/queryEventList'
      if (this.search) {
        url = url + '?text=' + this.search
      }
      Axios.get(url).then((res) => {
        this.rightLoading = false
        this.dataList = res.data.data.list
        this.choseTotal = res.data.data.total
      })
    },
    rightSearch() {
      this.queryEventList()
    },
    checkAllChange() {
      // console.log('已选列表checkAll: ', this.checkAll)
      this.chooseList = []
      if (this.checkAll) {
        // 全选
        // console.log('全选 已选列表checkAll: ', this.checkAll)
        this.$refs['rightForm'].setCheckedNodes(this.dataList)
        this.chooseAll = true
      } else {
        // 取消选中
        // console.log('取消选中 已选列表checkAll: ', this.checkAll)
        this.$refs['rightForm'].setCheckedKeys([])
        this.chooseList = []
        this.chooseAll = false
      }
    },
    handleCheckChange(data, checked, indeterminate) {
      // // console.log('handleCheckChange: ', data, checked, indeterminate)
      if (checked) {
        data.eventId = data.id
        this.chooseList.push(data)
        // console.log('chooseList: ', this.chooseList)
      } else {
        this.chooseList.forEach((item, index) => {
          // console.log('chooseList: ', item)
          if (item.id == data.id && item.title == data.title) {
            // console.log('取消选择的item: ', item)
            // console.log('取消选择的item-index: ', index)
            this.chooseList.splice(index, 1)
            // console.log('chooseList: ', this.chooseList)
          }
        })
      }
    },
    // 关闭添加前的操作
    handleClose(done) {
      this.addEventInfoDialog = false
    },
    // 确定
    submitBtn() {
      // console.log('chooseList: ', this.chooseList)
      this.form.eventBeans = this.form.eventBeans.concat(this.chooseList)
      this.addEventInfoDialog = false
    }
  }
}
</script>

<style lang="scss" scoped>
.emergencyEvent {
  .addEventInfo {
    .tableInfo {
      margin-top: 10px;
    }
  }
}
</style>
