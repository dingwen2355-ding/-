<template>
  <div class="warnInfo">
    <div class="addWarnInfo">
      <el-button
        type="primary"
        size="small"
        plain
        icon="el-icon-circle-plus-outline"
        @click="addWarnInfoBtn()"
        v-if="isCode !== 1"
      >新增预警信息</el-button>
      <el-table
        :data="form.warnBeans.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)"
        border
        style="width: 100%; height: 400px; overflow: auto"
        class="tableInfo"
      >
        <el-table-column type="index" label="序号" width="70" :index="table_index"></el-table-column>
        <el-table-column prop="warningTitle" label="预警标题" width="200"></el-table-column>
        <el-table-column label="预警类别" width="200">
          <template slot-scope="scope">
            {{
            scope.row.warningType2 !== ''
            ? scope.row.warningType1 + '/' + scope.row.warningType2
            : scope.row.warningType1
            }}
          </template>
        </el-table-column>
        <el-table-column prop="releaseTime" label="预警时间" width="180"></el-table-column>
        <el-table-column prop="warningContent" label="预警内容"></el-table-column>
        <el-table-column label="操作" width="210" v-if="isCode != 1">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewWarnInfo(scope.row, scope.$index)">详情</el-button>
            <el-button type="text" size="small" @click="delWarnInfo(scope.row, scope.$index)">删除</el-button>
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
        :total="form.warnBeans.length"
      ></el-pagination>
    </div>
    <div v-if="addWarnInfoDialog">
      <el-dialog
        title="新增预警信息"
        :visible.sync="addWarnInfoDialog"
        width="700px"
        :modal-append-to-body="false"
        :append-to-body="false"
        :before-close="handleClose"
        :close-on-click-modal="false"
      >
        <div class="info_body" v-loading="loading">
          <div style="display: flex; margin-top: 5px">
            <el-input clearable placeholder="请输入搜索内容" v-model="search"></el-input>
            <el-button icon="el-icon-search" @click="rightSearch" style="margin-left: 10px"></el-button>
          </div>
          <div class="mainContent" style="overflow: hidden; height: 698px">
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
                  >{{ data.warningTitle }}</div>
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
    <alarm-check-dialog :dialogFormVisible.sync="showWarnInfoViewDialog" :formInfo="checkForm" :isCode="1" />
  </div>
</template>
<script>
import AlarmCheckDialog from '../../../../alarmNews/alarmCheckDialog.vue'
import Axios from 'axios'

export default {
  name: 'warnInfo',
  props: ['form', 'isCode'],
  components: { AlarmCheckDialog },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      warnRowInfo: null,
      //   rules: {
      //   },
      addWarnInfoDialog: false,
      showWarnInfoViewDialog: false,
      loading: false,
      search: '',
      checkAll: false,
      defaultProps: {
        id: 'id',
        label: 'warningTitle'
      },
      dataList: [],
      checkForm: {},
      choseTotal: 0,
      chooseList: []
    }
  },
  watch: {
    form(v) {
      console.log(v, 'form-1111111111111111')
    }
  },
  mounted() {
    this.queryWarningList()
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
    viewWarnInfo(row, index) {
      // console.log('warnInfo--index', index)
      this.warnRowInfo = (this.currentPage - 1) * this.pageSize + index
      // console.log('warnInfo--this.warnRowInfo--index', this.warnRowInfo)
      // console.log('warnInfo--row: ', row)
      this.showWarnInfoViewDialog = true
      row.deal = '1'
      this.checkForm = row
    },
    // 添加
    addWarnInfoBtn() {
      this.addWarnInfoDialog = true
    },
    // 删除
    delWarnInfo(row, index) {
      // console.log('warnInfo--index', index)
      this.warnRowInfo = (this.currentPage - 1) * this.pageSize + index
      // console.log('warnInfo--this.warnRowInfo--index', this.warnRowInfo)
      // console.log('warnInfo--row: ', row)
      this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.form.warnBeans.splice(this.warnRowInfo, 1)
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
    queryWarningList() {
      console.log(this.form)
      this.rightLoading = true
      let url = '/gtw/EIMS-management/commandWarning/getWarning'
      if (this.search) {
        url = url + '?title=' + this.search
      }
      Axios.get(url).then((res) => {
        this.rightLoading = false
        this.dataList = res.data.data
        this.choseTotal = this.dataList.length
      })
    },
    rightSearch() {
      this.queryWarningList()
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
        this.chooseList.push(data)
        // console.log('chooseList: ', this.chooseList)
      } else {
        this.chooseList.forEach((item, index) => {
          // console.log('chooseList: ', item)
          if (item.id == data.id && item.warningTitle == data.warningTitle) {
            // console.log('取消选择的item: ', item)
            // console.log('取消选择的item-index: ', index)
            this.chooseList.splice(index, 1)
            // console.log('chooseList: ', this.chooseList)
          }
        })
      }
    },
    // 关闭添加队员的信息前的操作
    handleClose(done) {
      this.addWarnInfoDialog = false
    },
    // 确定
    submitBtn() {
      // console.log('chooseList: ', this.chooseList)
      this.form.warnBeans = this.form.warnBeans.concat(this.chooseList)
      this.addWarnInfoDialog = false
    }
  }
}
</script>

<style lang="scss" scoped>
.warnInfo {
  .addWarnInfo {
    .tableInfo {
      margin-top: 10px;
    }
  }
}
</style>
