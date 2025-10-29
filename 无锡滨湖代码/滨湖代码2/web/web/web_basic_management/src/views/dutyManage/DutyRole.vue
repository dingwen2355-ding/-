<template>
  <div class="page">
    <header style="padding:20px 0">
      <!--      <el-button type="primary" @click="showAdd" v-if="menuIds.includes(menus['config:add'])">新增</el-button>-->
      <el-button type="primary" @click="showAdd">新增</el-button>
      <el-button type="primary" @click.prevent="delRow()">删除</el-button>
    </header>
    <el-table
      :data="tableData.slice((currentPage-1)*pageSize, currentPage*pageSize)"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="handleSelectionChange"
      height="694"
      border
      stripe
    >
      <el-table-column type="selection" width="80"></el-table-column>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page.sync="currentPage"
      :page-size="pageSize"
      layout="total, prev, pager, next, jumper"
      :total="tableData.length"
    ></el-pagination>
    <div id="dialog">
      <!--新增界面-->
      <el-dialog
        title="模块信息"
        :visible.sync="addFormVisible"
        :modal-append-to-body="false"
        width="40%"
      >
        <el-form ref="addForm" :model="addForm" :rules="rules">
          <el-form-item label="角色名称" prop="roleName" label-width="120px" required>
            <el-input v-model="addForm.roleName" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitAdd('addForm')">确定</el-button>
          <el-button type="primary" plain @click.native="addFormVisible = false">取消</el-button>
        </div>
      </el-dialog>

      <!--编辑界面-->
      <el-dialog
        title="编辑"
        :visible.sync="editFormVisible"
        :modal-append-to-body="false"
        width="750px"
      >
        <el-form ref="editForm" :model="editForm" :rules="rules">
          <el-form-item label="角色名称" prop="roleName" label-width="120px">
            <el-input v-model="editForm.roleName" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitEdit('editForm')">确定</el-button>
          <el-button type="primary" plain @click.native="editFormVisible = false">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import axios from '@/utils/request'
import qs from 'qs'

export default {
  name: 'dutyManage',
  data() {
    return {
      menus: configs.menus,
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      tableData: [],
      rules: {
        roleName: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      },
      addForm: {
        roleName: ''
      },
      editForm: {
        roleName: ''
      },
      currentPage: 1,
      pageSize: 12,
      listLoading: false,
      addFormVisible: false,
      editFormVisible: false,
      editLoading: false,
      selectionIds: []
    }
  },
  methods: {
    // 删除
    delRow(row) {
      if (this.selectionIds.length === 0) {
        this.$message({
          type: 'info',
          showClose: true,
          message: '请先勾选删除项'
        })
        return
      }
      this.$confirm('此操作将删除选中项, 是否继续?', '提示', {
        type: 'warning'
      }).then(() => {
        let ids = ''
        if (row === undefined) {
          ids = this.selectionIds.toString()
        } else {
          ids = row.roleId
        }
        axios.post('/binhuapis/deleteDutyRole?ids=' + ids).then(res => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.query()
          }
        })
      })
    },
    // 显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true
      this.editForm = Object.assign({}, row)
    },
    // 显示新增界面
    showAdd() {
      this.addFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()
      })
    },
    // 选择框发生变化
    handleSelectionChange(rows) {
      this.selectionIds = []
      rows.forEach(row => {
        this.selectionIds.push(row.roleId)
      })
    },
    query() {
      this.listLoading = true
      axios
        .get('/binhuapis/queryDutyRole')
        .then(res => {
          this.tableData = res.data.data.data
          console.log('发', this.tableData)
          console.log('发222', this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize))
          this.listLoading = false
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    submitAdd(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const param = qs.stringify(this.addForm)
          let url = '/binhuapis/insertDutyRole?' + param
          axios.post(url).then(res => {
            if (res.data.code === 200 && res.data.message === 'ok') {
              this.addFormVisible = false
              this.$message.success('新增成功')
              this.query()
            } else {
              this.addFormVisible = false
              this.$message.warning(res.data.message)
            }
          })
        }
      })
    },
    submitEdit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.editForm.id = this.editForm.roleId
          const param = qs.stringify(this.editForm)
          let url = '/binhuapis/updateDutyRole?' + param
          axios.post(url).then(res => {
            if (res.data.code === 200) {
              this.editFormVisible = false
              this.$message.success('编辑成功')
              this.query()
            }
          })
        }
      })
    }
  },
  mounted() {
    this.query()
  }
}
</script>
