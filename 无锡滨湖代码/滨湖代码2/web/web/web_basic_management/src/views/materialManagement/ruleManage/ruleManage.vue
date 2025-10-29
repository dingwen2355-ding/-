<template>
  <div class="ruleManage">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="预警类型:">
        <el-select filterable v-model="searchForm.wariningId" placeholder="请选择物资名称" clearable>
          <el-option v-for="item in wariningList" :value="item.id" :label="item.name" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="物资类型:">
        <el-select filterable v-model="searchForm.kind" placeholder="请选择物资类别" clearable>
          <el-option v-for="item in kindList" :value="item.dictName" :label="item.dictName" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <!-- <el-button icon="el-icon-delete" @click="deleteData">删除</el-button> -->
        </div>
        <div class="content-right">
          <el-button icon="el-icon-plus" @click="addData">新增记录</el-button>
        </div>
      </div>
      <!-- <el-table
                :data="tableData" border style="width: 100%" height="540"
                v-loading="loading" @selection-change="handleSelectionChange"
      >-->
      <el-table :data="tableData" border style="width: 100%" height="740" v-loading="loading">
        <!-- <el-table-column align='center' header-align='center'  align='center' header-align='center'  type="selection" width="55"> </el-table-column> -->
        <!-- <el-table-column align='center' header-align='center'  type="index" label="序号" width="70" :index="table_index"></el-table-column> -->
        <el-table-column align="center" header-align="center" prop="ruleName" label="预警名称"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="remind"
          label="预警提醒"
          show-overflow-tooltip
          width="400"
        ></el-table-column>
        <el-table-column align="center" header-align="center" label="触发阈值" width="100">
          <template slot-scope="scope">
            低于
            <span style="margin-left: 1px">{{ scope.row.threshold }}</span>
            <span v-if="scope.row.type == '1'">天</span>
            <span v-else-if="scope.row.type == '2'">个</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column align="center" header-align="center" label="预警类型">
          <template slot-scope="scope">
            <span v-if="scope.row.type == '1'">保质期预警</span>
            <span v-else-if="scope.row.type == '2'">库存数量预警</span>
            <span v-else>{{ scope.row.type }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" header-align="center" label="规则状态" width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.status == 1">可用</span>
            <span v-else-if="scope.row.status == 0">不可用</span>
            <span v-else>{{ scope.row.status }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" header-align="center" prop="reviser" label="修改人员" width="90"></el-table-column>
        <el-table-column align="center" header-align="center" prop="createTime" label="时间"></el-table-column>
        <el-table-column align="center" header-align="center" label="操作" width="90">
          <template slot-scope="scope">
            <el-button @click="handleEdit(scope.row, 1)" type="text" size="medium" icon="el-icon-view"></el-button>
            <el-button @click="handleEdit(scope.row, 0)" type="text" size="medium" icon="el-icon-edit-outline"></el-button>
            <el-button @click="handleDelete(scope.row)" type="text" size="medium" icon="el-icon-delete"></el-button>
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
    <addRuleDialog :dialogVisible.sync="showAdd" :formInfo="formInfo" :isCode="isCode" @success="success"></addRuleDialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import znvTime from '../../../utils/znvTime'
import addRuleDialog from './option/addRuleDialog.vue'
export default {
  name: 'ruleManage',
  components: { addRuleDialog },
  data() {
    return {
      wariningList: [
        { id: '1', name: '保质期预警' },
        { id: '2', name: '库存数量预警' }
      ],
      kindList: [],

      searchForm: {
        wariningId: '',
        kind: ''
      },

      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],

      showAdd: false,
      formInfo: {},
      isCode: 0 // 0 编辑 1 查看详情 2 新增
    }
  },
  mounted() {
    this.queryDataTable()
    this.getKindList()
  },
  methods: {
    getKindList() {
      let url = '/binhuapis/dict/query-condition?parentId=1'
      request.get(url).then(res => {
        this.kindList = res.data.data.list
      })
    },

    // table_index(index) {
    //     return (this.currentPage - 1) * this.pageSize + index + 1
    // },
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
      let url = '/binhuapis/resourceRule/query?page=' + this.currentPage + '&pageSize=' + this.pageSize
      if (this.searchForm.wariningId) {
        url = url + '&type=' + this.searchForm.wariningId
      }
      if (this.searchForm.kind) { // 类别
        url = url + '&stuffType=' + this.searchForm.kind
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
        wariningId: '',
        kind: ''
      }
      this.currentPage = 1
      this.queryDataTable()
    },
    // 新增
    addData() {
      this.showAdd = true
      this.formInfo = {
        ruleName: '',
        remind: '',
        threshold: '',
        type: '',
        stuffType: '',
        status: '0',
        reviser: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss')
      }
      this.isCode = 2
    },
    success() {
      this.currentPage = 1
      this.queryDataTable()
    },

    // // 选中的节点数据
    // handleSelectionChange(rows) {
    //   this.multipleSelection = rows
    //   // console.log('选中的节点数据: ', this.multipleSelection)
    // },

    // // 删除
    // deleteData() {
    //   if (this.multipleSelection.length > 0) {
    //     this.$confirm('此操作将删除所勾选的场所, 是否继续?', '提示', {
    //       confirmButtonText: '确定',
    //       cancelButtonText: '取消',
    //       type: 'warning'
    //     }).then(() => {
    //       let ids = this.multipleSelection.map((item) => {
    //         return item.id
    //       }).join(',')
    //       let url = `/binhuapis/siteManage/remove?id=${ids}`
    //       console.log('删除--url', url)
    //       request.delete(url, ids).then((r) => {
    //         if (r.data.code === 200) {
    //           this.$message.success('删除成功')
    //           this.currentPage = 1
    //           this.queryDataTable()
    //         } else {
    //           this.$message(r.data.message)
    //         }
    //       })
    //     }).catch(() => {
    //       this.$message({
    //         type: 'info',
    //         message: '已取消删除'
    //       })
    //     })
    //   } else {
    //     this.$message.warning('请勾选表格中的数据后再进行删除操作')
    //   }
    // },
    // 编辑/详情
    handleEdit(row, code) {
      this.showAdd = true
      if (code === 1) {
        // 1 -- 详情
        this.isCode = 1
      } else if (code === 0) {
        // 0 -- 编辑
        this.isCode = 0
      }
      if (row.stuffType) {
        row.stuffType = Number(row.stuffType)
      }
      this.formInfo = row
    },
    // 删除
    handleDelete(row) {
      this.$confirm('此操作将删除所选的规则, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/binhuapis/resourceRule/del?id=' + row.id
        // console.log('删除--url', url)
        request.get(url).then(res => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.currentPage = 1
            this.queryDataTable()
          } else {
            this.$message.error(res.data.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>
<style lang="scss">
.ruleManage {
  width: 100%;
  height: calc(100% - 10px);
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
