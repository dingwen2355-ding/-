<template>
  <div class="FormSettings">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>表单设置</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 表格 -->
    <el-card class="box-card" style="margin-top: 20px; height: calc(100% - 40px)">
      <div class="table">
        <header>
          <span>
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete(1, {})">删除</el-button>
          </span>
        </header>
        <el-table
          :data="logData"
          highlight-current-row
          v-loading="listLoading"
          @selection-change="handleSelectionChange"
          height="700px"
          style="width: 100%"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column type="index" label="序号" :index="table_index" width="50"></el-table-column>
          <el-table-column prop="name" label="系统名称" show-overflow-tooltip></el-table-column>
          <el-table-column prop="address" label="系统地址" show-overflow-tooltip></el-table-column>
          <el-table-column prop="params" label="参数键名值" show-overflow-tooltip></el-table-column>
          <!-- <el-table-column prop="param2" label="参数值"></el-table-column> -->
          <el-table-column prop="source" label="系统来源" show-overflow-tooltip></el-table-column>
          <el-table-column prop="image" label="图片" show-overflow-tooltip></el-table-column>
          <el-table-column prop="remark" label="备注" show-overflow-tooltip></el-table-column>
          <el-table-column fixed="right" label="操作" width="150">
            <template slot-scope="scope">
              <el-button type="text" size="medium" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="text" size="medium" @click="deleteSome(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!--工具条-->
      <el-col :span="24" class="toolbar">
        <div class="total-container">
          <span>
            显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
            <span class="total-number">{{ logData.length }}</span> 条记录
          </span>
        </div>
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager,next, sizes, jumper"
            :total="total"
          ></el-pagination>
        </div>
      </el-col>
    </el-card>
    <FormSettingsAdd :dialogVisible.sync="showAdd" :formInfo="formInfo" @success="handleSuccess" :isCode="isCode" />
  </div>
</template>

<script>
import axios from '@/utils/request'
import FormSettingsAdd from './FormSettingsAdd.vue'
export default {
  name: 'log-manager',
  components: { FormSettingsAdd },
  data() {
    return {
      showAdd: false,
      formInfo: null,
      isCode: 0,
      listLoading: false,
      logData: [],
      currentRow: null,
      selectionIds: [],
      currentPage: 1,
      total: 0,
      pageSize: 10 // 每页显示几条
    }
  },
  mounted() {
    this.refreshData()
  },
  methods: {
    handleAdd() {
      this.showAdd = true
      this.formInfo = null
      this.isCode = 0
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.refreshData()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.refreshData()
    },
    handleSuccess() {
      this.currentPage = 1
      this.formInfo = null
      this.refreshData()
    },
    refreshData() {
      let params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize
      }
      var url = '/auth/third-platform/query-by-page'
      this.listLoading = true
      axios
        .get(url, { params })
        .then((res) => {
          if (res.data.code === 200) {
            this.listLoading = false
            this.logData = res.data.data.list || []
            // let arr = res.data.data.list || []
            // this.logData = arr.map((v) => {
            //   return {
            //     name: v.name,
            //     address: v.address,
            //     params: v.params,
            //     source: v.source,
            //     image: v.image,
            //     remark: v.remark,
            //     id: v.id,
            //     createTime: v.createTime,
            //     updateTime: v.updateTime
            //   }
            // })
            this.total = res.data.data.total
          }
        })
        .catch(() => {})
    },
    handleSelectionChange: function (rows) {
      var self = this
      this.selectionIds.splice(0)
      rows.forEach(function (row) {
        self.selectionIds.push(row.id)
      })
    },
    handleEdit(row) {
      this.showAdd = true
      this.formInfo = JSON.parse(JSON.stringify(row))
      this.isCode = 1
    },
    handleDelete() {
      if (this.selectionIds.length === 0) {
        this.$notify.info({
          title: '消息',
          message: '请先勾选删除项'
        })
      } else {
        let ids = this.selectionIds.join(',')
        this.deleteSome(ids)
      }
    },
    deleteSome(ids) {
      this.$confirm('此操作将删除所选信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let url = '/auth/third-platform/delete?ids=' + ids
          axios.delete(url).then((res) => {
            if (res.data.code === 200) {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 1000
              })
              this.currentPage = 1
              this.refreshData()
            } else {
              this.$notify.error({
                title: '错误',
                message: res.data.message,
                duration: 1000
              })
            }
          })
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消删除',
            duration: 1000
          })
        })
    }
  }
}
</script>

<style lang="scss">
.FormSettings {
  width: 100%;
  height: 100%;
  background: #f8f9ff;
}
</style>
