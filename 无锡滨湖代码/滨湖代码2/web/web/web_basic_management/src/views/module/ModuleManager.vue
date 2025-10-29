<template>
  <div id="module-page">
    <header style="padding: 20px 0">
      <el-button type="primary" icon="el-icon-plus" @click="showAdd">新增</el-button>
      <el-button type="danger" icon="el-icon-delete" @click.prevent="delRow()">删除</el-button>
    </header>
    <el-table
      :data="tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
      highlight-current-row
      v-loading="listLoading"
      @selection-change="handleSelectionChange"
      height="680"
      border
      stripe
    >
      <el-table-column type="selection" width="80"></el-table-column>
      <el-table-column prop="moduleName" label="菜单名称"></el-table-column>
      <el-table-column prop="description" label="菜单描述"></el-table-column>
      <el-table-column prop="id" label="菜单id"></el-table-column>
      <!-- <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="text" size="medium" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        </template>
      </el-table-column>-->
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
          :page-sizes="[10, 20, 50, 100]"
          layout=" prev, pager, next, sizes ,jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <div id="dialog">
      <!--编辑界面-->
      <el-dialog title="编辑" :visible.sync="editFormVisible" :modal-append-to-body="false" width="750px">
        <el-form ref="editForm" :model="editForm" :rules="editrules">
          <el-form-item label="模块名称" prop="moduleName" label-width="120px">
            <el-input v-model="editForm.moduleName"></el-input>
          </el-form-item>
          <el-form-item label="模块链接" prop="moduleUrl" label-width="120px">
            <el-input v-model="editForm.moduleUrl"></el-input>
          </el-form-item>
          <el-form-item label="模块描述" prop="description" label-width="120px">
            <el-input type="textarea" v-model="editForm.description"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitEdit('editForm')">确定</el-button>
          <el-button type="primary" plain @click.native="editFormVisible = false">取消</el-button>
        </div>
      </el-dialog>

      <!--新增界面-->
      <el-dialog title="模块信息" :visible.sync="addFormVisible" :modal-append-to-body="false" width="40%">
        <el-form ref="addForm" :model="addForm" :rules="addrules">
          <el-form-item label="模块名称" prop="moduleName" label-width="120px">
            <el-input v-model="addForm.moduleName" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="模块链接" prop="moduleUrl" label-width="120px">
            <el-input v-model="addForm.moduleUrl" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="模块描述" prop="description" label-width="120px">
            <el-input type="textarea" v-model="addForm.description" style="width: 90%"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitAdd('addForm')">确定</el-button>
          <el-button type="primary" plain @click.native="addFormVisible = false">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import axios from '@/utils/request.js'
import qs from 'qs'

export default {
  name: 'moduleManage',
  data() {
    let addmodulenameCheck = function (rule, value, callback) {
      callback()
    }
    let addmoduleUrlCheck = function (rule, value, callback) {
      callback()
    }
    let editmodulenameCheck = (rule, value, callback) => {
      callback()
    }
    let editmoduleUrlCheck = (rule, value, callback) => {
      callback()
    }
    return {
      menus: configs.menus,
      menuIds: JSON.parse(sessionStorage.getItem('menuIds')),
      tableData: [],
      addrules: {
        moduleName: [
          { required: true, message: '请输入模块名', trigger: 'blur' },
          {
            validator: addmodulenameCheck,
            message: '模块已存在',
            trigger: 'blur'
          }
        ],
        moduleUrl: [
          // { required: true, message: '请输入模块链接', trigger: 'blur' },
          {
            validator: addmoduleUrlCheck,
            message: '模块链接已存在',
            trigger: 'blur'
          }
        ]
      },
      editrules: {
        moduleName: [
          { required: true, message: '请输入模块名', trigger: 'blur' },
          {
            validator: editmodulenameCheck,
            message: '模块已存在',
            trigger: 'blur'
          }
        ],
        moduleUrl: [
          // { required: true, message: '请输入模块链接', trigger: 'blur' },
          {
            validator: editmoduleUrlCheck,
            message: '模块链接已存在',
            trigger: 'blur'
          }
        ]
      },
      addForm: {
        moduleName: '',
        description: '',
        moduleUrl: ''
      },
      //
      editForm: {
        moduleName: '',
        description: '1',
        moduleUrl: ''
      },
      //
      currentPage: 1,
      pageSize: 10,
      total: 0,
      listLoading: false,
      addFormVisible: false, // 修改密码界面是否显示
      editFormVisible: false, // 编辑界面是否显示
      editLoading: false,
      titles: ['备选项', '已选项'],
      selectionIds: []
    }
  },
  methods: {
    // 删除
    delRow(row) {
      if (this.selectionIds.length === 0) {
        this.$notify.info({
          title: '消息',
          message: '请先勾选删除项',
          duration: 1000
        })
        return
      }
      this.$confirm('此操作将删除模块, 是否继续?', '提示', {
        type: 'warning'
      }).then(() => {
        let ids = ''
        if (row === undefined) {
          ids = this.selectionIds.toString()
        } else {
          ids = row.id
        }
        axios.delete('/auth/module?id=' + ids).then((res) => {
          if (res.data.result === 0) {
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 1000
            })
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
      this.addForm = {
        moduleName: '',
        description: '',
        moduleUrl: ''
      }
    },
    // 选择框发生变化
    handleSelectionChange(rows) {
      this.selectionIds = []
      rows.forEach((row) => {
        this.selectionIds.push(row.id)
      })
    },
    query() {
      this.listLoading = true
      axios
        .get('/auth/module')
        .then((res) => {
          this.tableData = res.data.data
          this.listLoading = false
          this.total = this.tableData.length
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    handleCurrentChange(val) {
      this.currentPage = val
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    submitAdd(formName) {
      let param = qs.stringify({
        moduleName: this.addForm.moduleName,
        moduleUrl: this.addForm.moduleUrl,
        description: this.addForm.description
      })
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let url = '/auth/module?' + param
          axios.post(url).then((res) => {
            if (res.data.result === 0) {
              this.addFormVisible = false
              this.$notify({
                title: '成功',
                message: '新增成功',
                type: 'success',
                duration: 1000
              })
              this.query()
            }
          })
        }
      })
    },
    submitEdit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios
            .put(
              '/auth/module?id=' +
              this.editForm.id +
              '&moduleName=' +
              this.editForm.moduleName +
              '&moduleUrl=' +
              this.editForm.moduleUrl +
              '&description=' +
              this.editForm.description
            )
            .then((res) => {
              if (res.data.result === 0) {
                this.editFormVisible = false
                this.$notify({
                  title: '成功',
                  message: '编辑成功',
                  type: 'success',
                  duration: 1000
                })
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

<style lang="scss">
#module-page {
  width: 100%;
  padding: 20px;
  background: #f8f9ff;
}
</style>
