<template>
  <div class="page" id="dutyManage">
    <header style="padding:20px 0">
      <el-button type="primary" @click="showAdd">新增</el-button>
      <el-button type="primary" @click="batchAddFormVisible = !batchAddFormVisible">批量新增</el-button>
      <!--<el-button
        type="primary"
        @click.prevent="delRow()"
      >删除</el-button>-->
    </header>
    <el-table
      :data="
        tableData.slice((currentPage - 1) * pageSize, currentPage * pageSize)
      "
      highlight-current-row
      v-loading="listLoading"
      @selection-change="handleSelectionChange"
      height="694"
      border
      stripe
    >
      <el-table-column type="selection" width="80"></el-table-column>
      <el-table-column prop="personName" label="人员名称"></el-table-column>
      <el-table-column prop="dutyType" label="值班类型"></el-table-column>
      <el-table-column prop="moduleName" label="值班模板"></el-table-column>
      <el-table-column prop="dept" label="部门"></el-table-column>
      <el-table-column prop="occupation" label="职位"></el-table-column>
      <el-table-column prop="roleName" label="值班角色"></el-table-column>
      <el-table-column prop="phone" label="联系电话"></el-table-column>
      <el-table-column prop="dutyDate" label="值班日期"></el-table-column>
      <el-table-column label="时间段">
        <template slot-scope="scope">
          <span>{{ scope.row.startTime + "-" + scope.row.endTime }}</span>
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
    <!--新增修改界面-->
    <el-dialog
      :title="isInsert ? '新增人员排班' : '编辑人员排班'"
      :visible.sync="formVisible"
      :modal-append-to-body="false"
      width="60%"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="排班类型" prop="dutyType">
          <el-select v-model="form.dutyType" placeholder="排班类型" :disabled="isInsert === false">
            <el-option
              v-for="item in dutyTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排班模板" prop="dutyModule">
          <el-select v-model="form.dutyModule" placeholder="排班模板" :disabled="isInsert === false">
            <el-option
              v-for="item in dutyModuleOptions"
              :key="item.moduleId"
              :label="item.moduleName"
              :value="item.moduleId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排班日期" prop="dutyDate">
          <el-date-picker
            v-model="form.dutyDate"
            value-format="yyyy-MM-dd"
            type="date"
            :picker-options="pickerOptions"
            placeholder="选择日期"
            @change="dateChange"
            :disabled="isInsert === false"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="人员列表" prop="personList">
          <el-transfer
            v-model="form.personCheckedList"
            filterable
            :titles="['人员列表', '选中人员']"
            :format="{
              noChecked: '${total}',
              hasChecked: '${checked}/${total}'
            }"
            :props="{
              key: 'id',
              label: 'name'
            }"
            @change="peopleChange"
            :data="personList"
          >
            <div slot-scope="{ option }">
              <!--              <span class="item item3" :title="option.index">{{option.index}}</span>-->
              <span class="item item1" :title="option.name">
                {{
                option.name
                }}
              </span>
              <span class="item item2" :title="option.precinctName">
                {{
                option.precinctName
                }}
              </span>
              <span class="item item3" :title="option.phone">
                {{
                option.phone
                }}
              </span>
              <span class="item item4">
                <el-select
                  :id="forId(option.index)"
                  :class="forId(option.index)"
                  v-if="isChildtype"
                  v-model="peopleChooseList[option.id]"
                  placeholder="值班角色"
                  @change="changeRole($event, option)"
                >
                  <el-option
                    v-for="item in roleList"
                    :key="item.roleId"
                    :label="item.roleName"
                    :value="item.roleId"
                  ></el-option>
                </el-select>
              </span>
            </div>
          </el-transfer>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submit('form')">确定</el-button>
        <el-button type="primary" plain @click.native="formVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--    批量新增-->
    <el-dialog
      title="批量新增"
      :visible.sync="batchAddFormVisible"
      :modal-append-to-body="false"
      width="30%"
    >
      <el-upload
        class="upload-demo upload"
        drag
        action
        ref="upload"
        :limit="1"
        :file-list="fileList"
        :headers="{
          Authorization: token
        }"
        style="margin-left: 80px"
        :on-preview="handlePreview"
        :on-remove="handleRemove1"
        :on-success="uploadSuccess"
        :auto-upload="false"
        :on-change="changeExcel"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div slot="tip" class="el-upload__tip" style="margin-left: -50px">
          只能上传excel文件，且不超过5MB
          <span style="color: darkred">且上传的文件内容必须符合模板规范</span>
          <a class="sel_btn" download="人员排班信息导入模板" href="template/人员排班信息导入.xlsx">
            点击下载模板
            <i class="el-icon-download"></i>
          </a>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchAdd('addForm')">确定</el-button>
        <el-button type="primary" plain @click.native="closeBatchAddForm">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import axios from '@/utils/request'

export default {
  name: 'dutyManage',
  data() {
    return {
      isChildtype: false,
      menus: configs.menus,
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      batchAddFormVisible: false,
      tableData: [],
      pickerOptions: {
        // 限制收货时间不让选择今天以前的
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        }
      },
      rules: {
        dutyType: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        dutyModule: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        dutyDate: [{ required: true, message: '请输入名称', trigger: 'blur' }]
        // personList: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      },
      form: {
        dutyDate: '',
        dutyModule: '',
        dutyType: '',
        personList: [],
        // 这个不用提交接口
        personCheckedList: []
        // 'personList': [
        //   {
        //     'personId': '',
        //     'roleId': ''
        //   }
        // ]
      },
      personCheckedList: [],
      roleCheckedList: {},
      peopleChooseList: {},
      dutyTypeOptions: [
        {
          value: '工作日',
          label: '工作日'
        },
        {
          value: '双休日',
          label: '双休日'
        },
        {
          value: '法定节假日',
          label: '法定节假日'
        }
      ],
      dutyModuleOptions: [],
      personList: [],
      roleList: [],
      currentPage: 1,
      pageSize: 12,
      listLoading: false,
      formVisible: false,
      isInsert: true,
      selectionIds: [],
      token: localStorage.getItem('token'),
      fileList: [],
      files: ''
    }
  },
  watch: {
    peopleChooseList: {
      handler(v) {
        console.log(v)
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    forId(index) {
      return 'dutyManage' + index
    },
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
      this.$confirm('此操作将删除模块, 是否继续?', '提示', {
        type: 'warning'
      }).then(() => {
        let ids = ''
        if (row === undefined) {
          ids = this.selectionIds.toString()
        } else {
          ids = row.id
        }
        axios.post('/binhuapis/deleteDutyRole?ids=' + ids).then(res => {
          if (res.data.result === 0) {
            this.$message.success('删除成功')
            this.query()
          }
        })
      })
    },
    // 显示编辑界面
    handleEdit: function (index, row) {
      this.isInsert = false
      this.isChildtype = true
      this.form = Object.assign({}, this.form, row)
      this.form.personCheckedList = []

      // this.form.personCheckedList.push(row.personId)
      // this.roleCheckedList[row.personId] = row.roleId
      // this.peopleChooseList[row.personId] = Number(row.roleId)
      this.isChildtype = false
      this.isChildtype = true
      this.formVisible = true
      this.changeDutyType()
      this.queryOnDutyPeople(row)
      this.form.dutyModule = row.moduleId
    },
    queryOnDutyPeople(row) {
      let url =
        '/binhuapis/queryDutyPersonByDate?dutyDate=' +
        row.dutyDate +
        '&dutyType=' +
        row.dutyType +
        '&moduleId=' +
        row.moduleId
      axios.get(url).then(r => {
        let data = r.data.data.data
        // this.formVisible = true
        console.log('first', this.peopleChooseList)
        for (let key in this.peopleChooseList) {
          this.peopleChooseList[key] = ''
        }
        data.forEach(v => {
          this.form.personCheckedList.push(v.personId)
          if (v.roleId !== '') {
            this.roleCheckedList[v.personId] = v.roleId
            this.peopleChooseList[v.personId] = Number(v.roleId)
          }
        })
        console.log('second', this.peopleChooseList)
      })
    },
    // 显示新增界面
    showAdd() {
      this.isInsert = true
      this.formVisible = true
      this.$nextTick(() => {
        this.$refs['form'].resetFields()
        this.form.dutyType = this.dutyTypeOptions[0].value
        this.roleCheckedList = {}
        this.peopleChooseList = {}
        this.changeDutyType()
        this.form.personCheckedList = []
        this.isChildtype = true
      })
    },
    // 选择框发生变化
    handleSelectionChange(rows) {
      this.selectionIds = []
      rows.forEach(row => {
        this.selectionIds.push(row.id)
      })
    },
    query() {
      this.listLoading = true
      axios
        .get('/binhuapis/queryDutyInfo?userId=' + sessionStorage.getItem("userId") + '&userName=' + sessionStorage.getItem("userName"))
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
    submit(formName) {
      if (this.form.personCheckedList.length === 0) {
        this.form.personList = []
        if (this.isInsert === true) {
          this.$message.warning('请选择值班人员')
          return
        } else {
          this.roleCheckedList = {}
        }
      } else {
        this.form.personList = []
        for (let v of this.form.personCheckedList) {
          if (!this.roleCheckedList[v]) {
            this.$message.warning('请选择值班角色')
            return
          } else {
            let tmp = {
              personId: v,
              roleId: this.roleCheckedList[v]
            }
            this.form.personList.push(tmp)
          }
        }
      }
      this.$refs[formName].validate(valid => {
        if (valid) {
          let url = '/binhuapis/updateDutyInfo'
          axios.post(url, this.form).then(res => {
            if (res.data.code === 200) {
              this.formVisible = false
              this.$message.success('提交成功')
              this.query()
            }
          })
        }
      })
    },
    submitBatchAdd() {
      let formdata = new FormData()
      formdata.append('file', this.files)
      console.log(this.files)
      axios
        .post(
          '/binhuapis/insertDutyInfoByExcel?userId=' +
          localStorage.getItem('userId'),
          formdata
        )
        .then(res => {
          if (res.data.code == 200) {
            this.batchAddFormVisible = false
            this.query()
            this.$message.success('上传成功')
            this.fileList = []
            this.files = ''
          } else {
            this.batchAddFormVisible = false
            this.$message.error(res.data.message)
            this.fileList = []
            this.files = ''
          }
        })
        .catch(function (error) {
          this.batchAddFormVisible = false
          this.$message.warning(error)
          this.fileList = []
          this.files = ''
        })
    },
    closeBatchAddForm() {
      this.batchAddFormVisible = false
      this.fileList = []
      this.files = ''
    },
    changeDutyType() {
      let url = '/binhuapis/queryDutyModule?dutyType=' + this.form.dutyType
      this.form.dutyModule = ''
      axios.get(url).then(res => {
        this.dutyModuleOptions = res.data.data.data
      })
    },
    changeDutyModule() {
      this.form.dutyDate = ''
    },
    dateChange() {
      this.roleCheckedList = {}
      this.peopleChooseList = {}
      this.form.personCheckedList = []
      if (
        this.form.dutyType !== '' &&
        this.form.dutyModule !== '' &&
        this.form.dutyDate !== ''
      ) {
        let url =
          '/binhuapis/queryDutyPersonByDate?dutyDate=' +
          this.form.dutyDate +
          '&dutyType=' +
          this.form.dutyType +
          '&moduleId=' +
          this.form.dutyModule
        axios.get(url).then(r => {
          let data = r.data.data
          // this.formVisible = true
          for (let key in this.peopleChooseList) {
            this.peopleChooseList[key] = ''
          }
          data.forEach(v => {
            this.form.personCheckedList.push(v.personId)
            if (v.roleId !== '') {
              this.roleCheckedList[v.personId] = v.roleId
              this.peopleChooseList[v.personId] = Number(v.roleId)
            }
          })
        })
      } else {
        this.$message.warning('请输入前三项')
      }
    },
    queryPeople() {
      let url = '/binhuapis/queryDutyPerson'
      axios.get(url).then(res => {
        this.personList = res.data.data.data
        this.personList.forEach((v, i) => {
          // v.index = i
          this.peopleChooseList[v.id] = ' '
        })
      })
    },
    queryRole() {
      let url = '/binhuapis/queryDutyRole'
      axios.get(url).then(res => {
        this.roleList = res.data.data.data
      })
    },
    peopleChange() {
      if (
        this.form.dutyDate === '' ||
        this.form.dutyModule === '' ||
        this.form.dutyType === ''
      ) {
        this.$message.warning('请优先选择排班类型，排班模板和排班日期')
      }
    },
    changeRole(v, option) {
      this.roleCheckedList[option.id] = v
      // this.peopleChooseList[option.index] = Number(v)
      // this.peopleChooseList[option.index] = v
      this.isChildtype = false
      this.isChildtype = true
    },
    // 点击文件列表中已上传的文件时的钩子
    handlePreview(file) {
      console.log('点击预览：' + file.name)
      this.files = file
    },
    // 文件列表移除文件时的钩子
    handleRemove1(file, fileList) {
      console.log('移除文件：' + file.name, fileList)
      // 执行服务器删除操作
    },
    // 文件上传成功时的钩子
    uploadSuccess(response, file, fileList) {
      console.log(response)
      this.dialogFormVisible = false
      // 重置表格
    },
    changeExcel(file, fileList) {
      const extension = file.name.split('.')[1] === 'xls'
      const extension2 = file.name.split('.')[1] === 'xlsx'
      const isLt2M = file.size / 1024 / 1024 < 5
      if (!extension && !extension2) {
        this.$message.warning('上传模板只能是 xls、xlsx格式!')
        this.files = ''
        this.fileList = []
        return false
      }
      if (!isLt2M) {
        this.$message.warning('上传模板大小不能超过 5MB!')
        this.files = ''
        this.fileList = []
        return false
      }
      this.files = file.raw
      this.fileList = fileList[0].raw
    }
  },
  mounted() {
    this.query()
    this.queryPeople()
    this.queryRole()
  }
}
</script>
<style lang="scss">
#dutyManage {
  .item {
    display: inline-block;
    overflow: hidden;
  }

  .item1 {
    width: 60px;
  }

  .item2 {
    width: 80px;
  }

  .item3 {
    width: 100px;
  }

  .item4 {
    width: 120px;
  }

  .el-transfer {
  }

  .el-transfer-panel:first-child {
    width: 300px;

    .el-select {
      display: none;
    }
  }

  .el-transfer-panel:last-child {
    width: 450px;
  }
}
</style>
