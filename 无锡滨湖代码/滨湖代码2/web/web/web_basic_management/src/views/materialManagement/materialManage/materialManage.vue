<template>
  <div class="materialManage">
    <el-form :inline="true" :model="searchForm" ref="form">
      <el-form-item label="物资名称:">
        <el-input clearable v-model="searchForm.materialId" placeholder="请输入物资名称"></el-input>
      </el-form-item>
      <el-form-item label="救援类型:">
        <el-select
          filterable
          v-model="searchForm.kind"
          style="width: 175px;"
          placeholder="请选择救援类型"
          clearable
        >
          <el-option v-for="(item,key) in helpTypes" :key="key" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="float: right">
        <el-button icon="el-icon-search" type="primary" size="small" @click="onSubmit">查询</el-button>
        <el-button icon="el-icon-refresh-left" size="small" @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="contentBody">
      <div class="content-top">
        <div class="content-left">
          <el-button icon="el-icon-delete" type="danger" size="small" @click="deleteData">删除</el-button>
        </div>
        <div class="content-right">
          <el-button icon="el-icon-plus" size="small" type="primary" @click="addData">入库</el-button>
        </div>
      </div>
      <!--lazy-->
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        height="760"
        row-key="id"
        ref="table"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column align="center" header-align="center" type="selection" width="55"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffName" label="物资名称"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffType" label="物资类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="helpTypeName" label="救援类型"></el-table-column>
        <el-table-column align="center" header-align="center" prop="stuffNum" label="数量" width="60"></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="stuffUnit"
          label="单位"
          width="60"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="address"
          label="仓库名称"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="dutyPerson"
          label="负责人员"
          width="90"
        ></el-table-column>
        <el-table-column
          align="center"
          header-align="center"
          prop="dutyPhone"
          label="负责人电话"
          width="120"
        ></el-table-column>
        <el-table-column align="center" header-align="center" label="操作" width="200">
          <template slot-scope="scope">
            <el-button
              @click="handleEdit(scope.row, 1)"
              type="text"
              size="medium"
              icon="el-icon-view"
            ></el-button>
            <el-button
              @click="handleEdit(scope.row, 0)"
              type="text"
              size="medium"
              icon="el-icon-edit-outline"
            ></el-button>
            <el-button @click="handleOperate(scope.row,2)" type="text" size="medium">出库</el-button>
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
      <add-material-manage
        :dialogVisible.sync="showAdd"
        :formInfo="formInfo"
        :isCode="isCode"
        @success="success"
      ></add-material-manage>
      <operate-stuff-out
        :dialogVisible.sync="showOperate"
        :formInfo="operateForm"
        @success="success"
      ></operate-stuff-out>
    </div>
  </div>
</template>
<script>
import request from '@/utils/request'
import AddMaterialManage from './option/addMaterialManage'
import operateStuffOut from './option/operateStuffOut'
export default {
  name: 'materialManage',
  components: { operateStuffOut, AddMaterialManage },
  data() {
    return {
      siteList: [],
      kindList: [],
      searchForm: {
        materialId: '', // 物资名称
        kind: '', // 物资类别
        siteId: '' // 场所名称
      },
      loading: false,
      tableData: [],
      currentPage: 1, // 当前页数
      pageSize: 10, // 每页条数
      total: 0, // 总条数
      multipleSelection: [],

      showAdd: false,
      formInfo: {},
      isCode: 0, // 0 编辑 1 查看详情 2 新增

      showOperate: false,
      operateForm: {},
      helpTypes: []
    }
  },
  mounted() {
    this.queryDataTable()
    this.getSiteList()
    this.getKindList()
    this.getHelpType()
  },
  methods: {
    // 救援类别 // 物资类别
    getHelpType() {
      let url = '/binhuapis/menu/getHelpType'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.helpTypes = res.data.data.data
        }
      })
    },
    viewTool(row) {
      this.$refs.table.toggleRowExpansion(row)
    },
    success() {
      this.currentPage = 1
      this.tableData = []
      this.queryDataTable()
    },
    getSiteList() {
      let url = '/binhuapis/siteManage/query'
      request.get(url).then(res => {
        this.siteList = res.data.data.list
      })
    },
    getKindList() {
      let url = '/binhuapis/dict/query-condition?parentId=1'
      request.get(url).then(res => {
        this.kindList = res.data.data.list
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.queryDataTable()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.queryDataTable()
    },
    queryDataTable() {
      this.loading = true
      let url = '/binhuapis/resourceStuff/query?page=' + this.currentPage + '&pageSize=' + this.pageSize + '&userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName")
      if (this.searchForm.materialId) { // 物资名称
        url = url + '&stuffName=' + this.searchForm.materialId
      }
      if (this.searchForm.kind) { // 类别
        url = url + '&helpType=' + this.searchForm.kind
      }
      request.get(url).then((res) => {
        this.loading = false
        this.tableData = res.data.data.list.map(t => {
          t.children = []
          t.hasChildren = true
          return t
        })
        this.total = res.data.data.total
      })
    },
    handleOperate(row, type) {
      this.operateForm = {
        rsId: row.id,
        type: '',
        num: '',
        info: '',
        note: '',
        createTime: '',
        phone: '',
        idNumber: '',
        stuffName: row.stuffName,
        address: row.address,
        stuffNum: row.stuffNum,
        stuffType: row.stuffType,
        dutyPerson: row.dutyPerson,
        dutyPhone: row.dutyPhone,
        regionName: row.regionName
      }
      this.showOperate = true
    },
    // 查询
    onSubmit() {
      this.currentPage = 1
      this.queryDataTable()
    },
    // 重置
    resetSearch() {
      this.searchForm = {
        materialId: '', // 物资名称
        kind: '', // 物资类别
        siteId: '' // 场所名称
      }
      this.currentPage = 1
      this.queryDataTable()
    },

    // 新增
    addData() {
      this.showAdd = true
      this.isCode = 2
      this.formInfo = {
        stuffName: '',
        stuffModel: '',
        typeName: '',
        stuffNum: '',
        stuffUnit: '',
        siteName: '',
        siteUnit: '',
        community: '',
        siteAddress: '',
        sitePerson: '',
        sitePhone: '',
        batch: '',
        overdueDate: '',
        stuffId: '',
        siteId: ''
      }
    },

    // 选中的节点数据
    handleSelectionChange(rows) {
      this.multipleSelection = rows
    },

    // 删除
    deleteData() {
      if (this.multipleSelection.length > 0) {
        this.$confirm('此操作将删除所勾选的物资信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = this.multipleSelection.map((item) => {
            return item.id
          }).join(',')
          let url = `/binhuapis/resourceStuff/del-batch?ids=${ids}`
          console.log('删除--url', url)
          request.get(url, ids).then((r) => {
            if (r.data.code === 200) {
              this.$message.success('删除成功')
              this.currentPage = 1
              this.queryDataTable()
            } else {
              this.$message(r.data.message)
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      } else {
        this.$message.warning('请勾选表格中的数据后再进行删除操作')
      }
    },

    // 编辑/详情
    handleEdit(row, code) {
      this.showAdd = true
      this.isCode = code
      this.formInfo = row
    },

    // 删除
    handleDelete(row) {
      this.$confirm('此操作将删除所勾选的物资信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/binhuapis/resourceStuff/del?id=' + row.id
        console.log('删除--url', url)
        request.get(url).then(res => {
          if (res.data.code == 200) {
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
.materialManage {
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
  .el-table {
    .el-table__body-wrapper {
      .el-table__row {
        .el-table__cell {
          .cell {
            .el-table__expand-icon {
              display: none;
            }
          }
        }
      }
      .el-table__row--level-1 {
        background: rgba(75, 105, 174, 0.2);
      }
    }
  }
}
</style>
