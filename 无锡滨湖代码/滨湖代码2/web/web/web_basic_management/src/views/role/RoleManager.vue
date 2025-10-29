<template>
  <div class="role-page">
    <!-- <header style="padding:20px 0">
      <el-button type="primary" @click="addRow">新增</el-button>
      <el-button
        type="primary"
        @click.prevent="delRow"
      >删除
      </el-button>
    </header>-->
    <el-table
      :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="handleSelectionChange"
      height="750"
      border
      stripe
    >
      <!-- <el-table-column type="selection" width="80">
      </el-table-column>-->
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="level" label="角色等级"></el-table-column>
      <el-table-column prop="roleLimit" label="权限">
        <template slot-scope="scope">{{ scope.row.modules | getPermission }}</template>
      </el-table-column>
      <el-table-column prop="description" label="角色描述"></el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="medium"
            @click="editRow(scope.$index, scope.row)"
            v-if="scope.row.roleId != '1' && parseInt(scope.row.level) >= currentLevel"
          >编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="toolbar">
      <div class="total-container">
        <span>
          显示 {{ (currentPage - 1) * pageSize + 1 }} - {{ currentPage * pageSize }} 条记录， 共
          <span
            class="total-number"
          >{{ total }}</span> 条记录
        </span>
      </div>
      <div class="block">
        <el-pagination
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          layout="prev, pager, next, sizes, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <div id="dialog">
      <el-dialog title="角色信息" :visible.sync="dialogFormVisible" :modal-append-to-body="false" width="50%">
        <el-form :model="ruleForm" ref="ruleForm" :rules="rules" label-width="120px">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="ruleForm.roleName" auto-complete="off" :disabled="upDateOrInsert === 'update'"></el-input>
          </el-form-item>
          <el-form-item label="角色等级" prop="level">
            <el-input v-model="ruleForm.level"></el-input>
          </el-form-item>
          <el-form-item label="角色描述" prop="description">
            <el-input v-model="ruleForm.description"></el-input>
          </el-form-item>
          <el-form-item label="权限">
            <template>
              <el-transfer
                :titles="titles"
                v-model="ruleForm.moduleIds"
                :data="modulesList"
                :props="{
                  label: 'moduleName',
                  key: 'id'
                }"
              ></el-transfer>
            </template>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="save('ruleForm')">确定</el-button>
          <el-button type="primary" @click.native="dialogFormVisible = false">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/request.js'
import qs from 'qs'

export default {
  name: 'roleManage',
  data() {
    const roleNameCheck = function (rule, value, callback) {
      var url = '/auth/role/verifyName?roleName=' + value
      axios.get(url).then(function (res) {
        if (res.data.code !== 200) {
          callback(new Error())
        } else {
          callback()
        }
      })
    }
    return {
      currentLevel: parseInt(JSON.parse(sessionStorage.getItem('level'))),
      menus: configs.menus,
      menuIds: JSON.parse(sessionStorage.getItem('menuIds')),
      tableData: [],
      dialogFormVisible: false,
      upDateOrInsert: null,
      modulesList: [],
      ruleForm: {
        roleId: '',
        roleName: '',
        description: '',
        moduleIds: [], // 选中的权限
        level: ''
      },
      rules: {
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' },
          { validator: roleNameCheck, message: '该角色已存在', trigger: 'blur' }
        ],
        level: [{ required: true, message: '请输入角色等级', trigger: 'blur' }]
      },
      selectionIds: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      listLoading: false,
      addFormVisible: false, // 修改密码界面是否显示
      editFormVisible: false, // 编辑界面是否显示
      editLoading: false,
      titles: ['备选项', '已选项'],
      modulesListBak: []
    }
  },
  filters: {
    getPermission(modules) {
      let data = []
      modules.forEach((v, i) => {
        data.push(v.moduleName)
      })
      return data.join()
    }
  },
  methods: {
    delRow: function () {
      if (this.selectionIds.length === 0) {
        this.$notify.info({
          title: '消息',
          message: '请先勾选删除项'
        })
        return
      }
      this.$confirm('此操作将删除角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let url = '/auth/role/delete?roleIds=' + this.selectionIds.join(',')
        axios.delete(url).then((res) => {
          if (res.data.result === 0) {
            self.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              onClose: function () {
                self.dialogFormVisible = false
              },
              duration: 1000
            })
          } else {
            self.$notify.error({
              title: '错误',
              message: '删除失败',
              onClose: function () {
                self.dialogFormVisible = false
              },
              duration: 1000
            })
          }
          this.query()
        })
      })
    },
    // 显示编辑界面
    editRow: function (index, row) {
      this.dialogFormVisible = true
      this.upDateOrInsert = 'update'
      this.ruleForm.roleName = row.roleName
      this.ruleForm.roleId = row.roleId
      this.ruleForm.level = row.level
      this.ruleForm.description = row.description
      let data = []
      row.modules.forEach((v, i) => {
        data.push(v.id)
      })
      this.ruleForm.moduleIds = data
      if (this.$refs.ruleForm) {
        this.$refs.ruleForm.clearValidate()
      }
      if (row.roleId === '1') {
        this.modulesList = JSON.parse(JSON.stringify(this.modulesListBak))
      } else {
        this.modulesList = JSON.parse(JSON.stringify(this.modulesListBak))
        this.modulesList = this.modulesList.filter((item) => {
          return item.id !== '98' && item.id !== '99' && item.id !== '100' && item.id !== '101'
        })
      }
    },
    // 显示新增界面
    addRow() {
      this.dialogFormVisible = true
      this.upDateOrInsert = 'insert'
      if (this.$refs.ruleForm) {
        this.$refs.ruleForm.resetFields()
      }
    },
    save(formName) {
      let params = Object.assign({}, this.ruleForm)
      for (let key in params) {
        if (!params[key]) {
          delete !params[key]
        }
      }
      params.moduleIds = params.moduleIds.join()
      if (this.upDateOrInsert === 'update') {
        let url = '/auth/role/update?' + qs.stringify(params)
        axios.put(url).then((res) => {
          if (res.data.result === 0) {
            this.dialogFormVisible = false

            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 1000
            })
          } else {
            this.$notify.error({
              title: '错误',
              message: '编辑失败',
              duration: 1000
            })
          }
          this.query()
        })
      } else if (this.upDateOrInsert === 'insert') {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let url = '/auth/role/insert?' + qs.stringify(params)
            axios.post(url).then((res) => {
              if (res.data.result === 0) {
                this.dialogFormVisible = false
                this.$notify({
                  title: '成功',
                  message: '修改成功',
                  type: 'success',
                  duration: 1000
                })
              } else {
                this.$notify.error({
                  title: '错误',
                  message: '新增失败',
                  duration: 1000
                })
                this.dialogFormVisible = false
              }
              this.query()
            })
          }
        })
      }
    },
    // 选择框发生变化
    handleSelectionChange(rows) {
      this.selectionIds = []
      rows.forEach((v) => {
        this.selectionIds.push(v.roleId)
      })
    },
    query() {
      this.listLoading = true
      axios.get('/auth/role/query').then((res) => {
        this.listLoading = false
        this.tableData = res.data.data
        this.total = this.tableData.length
      })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    queryModules() {
      axios.get('/auth/module').then((res) => {
        let data = res.data.data
        this.modulesList = data
        this.modulesListBak = JSON.parse(JSON.stringify(data))
      })
    }
  },
  mounted() {
    this.query()
    this.queryModules()
  }
}
</script>

<style lang="scss">
.role-page {
  width: 100%;
  padding: 20px;
  background: #f8f9ff;
}
</style>
