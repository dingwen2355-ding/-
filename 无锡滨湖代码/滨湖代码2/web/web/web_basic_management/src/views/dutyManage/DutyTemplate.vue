<template>
  <div class="page">
    <header style="padding:20px 0">
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
      <el-table-column prop="dutyType" label="值班类型"></el-table-column>
      <el-table-column prop="moduleName" label="排班模板"></el-table-column>
      <el-table-column prop="isEndToday" label="是否跨天">
        <template slot-scope="scope">
          <span>{{ scope.row.isEndToday==='1'?'次日':'当日'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="时间段">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime+'-'+scope.row.endTime }}</span>
        </template>
      </el-table-column>
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
          <el-form-item label="值班类型" prop="dutyType" label-width="120px">
            <el-select v-model="addForm.dutyType" placeholder="值班类型">
              <el-option :label="v" :value="v" v-for="v in dutyType" :key="v"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排班模板" prop="moduleName" label-width="120px">
            <el-select v-model="addForm.moduleName" placeholder="排班模板">
              <el-option :label="v" :value="v" v-for="v in dutyTemplate" :key="v"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否跨天" prop="isEndToday" label-width="120px">
            <el-select v-model="addForm.isEndToday" placeholder="是否跨天">
              <el-option label="次日" value="1"></el-option>
              <el-option label="当日" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime" label-width="120px">
            <el-time-picker v-model="addForm.startTime" value-format="HH:mm:ss"></el-time-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime" label-width="120px">
            <el-time-picker v-model="addForm.endTime" value-format="HH:mm:ss"></el-time-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitAdd('addForm')">确定</el-button>
          <el-button type="primary" plain @click.native="closeAddForm">取消</el-button>
        </div>
      </el-dialog>

      <!--编辑界面-->
      <el-dialog
        title="编辑"
        :visible.sync="editFormVisible"
        :modal-append-to-body="false"
        width="750px"
      >
        <el-form ref="editForm" :model="editForm" :rules="rules2">
          <el-form-item label="值班类型" prop="dutyType" label-width="120px">
            <el-select v-model="editForm.dutyType" placeholder="值班类型" disabled>
              <el-option :label="v" :value="v" v-for="v in dutyType" :key="v"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排班模板" prop="moduleName" label-width="120px">
            <el-select v-model="editForm.moduleName" placeholder="排班模板" disabled>
              <el-option :label="v" :value="v" v-for="v in dutyTemplate" :key="v"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否跨天" prop="isEndToday" label-width="120px">
            <el-select v-model="editForm.isEndToday" placeholder="是否跨天">
              <el-option label="次日" value="1"></el-option>
              <el-option label="当日" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="开始时间" prop="startTime" label-width="120px">
            <el-time-picker v-model="editForm.startTime" value-format="HH:mm:ss"></el-time-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime" label-width="120px">
            <el-time-picker v-model="editForm.endTime" value-format="HH:mm:ss"></el-time-picker>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitEdit('editForm')">确定</el-button>
          <el-button type="primary" plain @click.native="closeEditForm">取消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import axios from '@/utils/request'
import moment from 'moment'

export default {
  name: 'dutyTemplate',
  data() {
    let validateTimeAdd = (rule, value, callback) => {
      if (this.addForm.isEndToday === '0') {
        if (this.addForm.startTime > this.addForm.endTime) {
          callback(new Error('开始时间不能大于结束时间'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    let validateTimeEdit = (rule, value, callback) => {
      if (this.editForm.isEndToday === '0') {
        if (this.editForm.startTime > this.editForm.endTime) {
          callback(new Error('开始时间不能大于结束时间'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    return {
      menus: configs.menus,
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      tableData: [],
      rules: {
        dutyType: [{ required: true, message: '请输入此项', trigger: 'blur' }],
        moduleName: [
          { required: true, message: '请输入此项', trigger: 'blur' }
        ],
        isEndToday: [
          { required: true, message: '请输入此项', trigger: 'blur' }
        ],
        startTime: [
          { required: true, message: '请输入此项', trigger: 'blur' },
          { validator: validateTimeAdd, trigger: 'blur' }
        ],
        endTime: [
          { required: true, message: '请输入此项', trigger: 'blur' },
          { validator: validateTimeAdd, trigger: 'blur' }
        ]
      },
      rules2: {
        dutyType: [{ required: true, message: '请输入此项', trigger: 'blur' }],
        moduleName: [
          { required: true, message: '请输入此项', trigger: 'blur' }
        ],
        isEndToday: [
          { required: true, message: '请输入此项', trigger: 'blur' }
        ],
        startTime: [{ required: true, message: '请输入此项', trigger: 'blur' }, { validator: validateTimeEdit, trigger: 'blur' }],
        endTime: [{ required: true, message: '请输入此项', trigger: 'blur' }, { validator: validateTimeEdit, trigger: 'blur' }]
      },
      addForm: {
        dutyType: '',
        endTime: moment().format('HH:mm:00'),
        isEndToday: '',
        moduleName: '',
        startTime: moment().format('HH:mm:00')
      },
      editForm: {
        dutyType: '',
        endTime: moment().format('HH:mm:00'),
        isEndToday: '',
        moduleName: '',
        startTime: moment().format('HH:mm:00')
      },
      dutyType: ['工作日', '双休日', '法定节假日'],
      dutyTemplate: ['白班', '夜班', '全日班', '早班', '中班', '晚班', '其他'],
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
          ids = row.moduleId
        }
        axios.post('/binhuapis/deleteDutyModule?ids=' + ids).then(res => {
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
    closeAddForm() {
      this.addFormVisible = false
      if (this.$refs['addForm']) {
        this.$refs['addForm'].clearValidate()
        this.$refs['addForm'].resetFields()
      }
    },
    closeEditForm() {
      this.editFormVisible = false
      if (this.$refs['editForm']) {
        this.$refs['editForm'].clearValidate()
        this.$refs['editForm'].resetFields()
      }
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
        this.selectionIds.push(row.moduleId)
      })
    },
    query() {
      this.listLoading = true
      axios
        .get('/binhuapis/queryDutyModule')
        .then(res => {
          if (res.data.code === 200) {
            this.tableData = res.data.data.data
          }
          this.listLoading = false
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    submitAdd(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let url = '/binhuapis/insertDutyModule'
          axios.post(url, this.addForm).then(res => {
            if (res.data.code === 200) {
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
          axios.post('/binhuapis/updateDutyModule', this.editForm).then(res => {
            if (res.data.code === 200) {
              this.editFormVisible = false
              this.$message.success('编辑成功')
              this.query()
            } else {
              this.addFormVisible = false
              this.$message.warning(res.data.message)
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
