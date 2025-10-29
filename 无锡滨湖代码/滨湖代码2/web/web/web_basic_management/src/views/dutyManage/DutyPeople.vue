<template>
  <div class="page" id="dutyPeople">
    <header style="padding:20px 0">
      <el-button type="primary" @click="showAdd">新增</el-button>
      <el-button type="primary" @click.prevent="delRow()">删除</el-button>
      <el-input class="item" v-model="srhCond.name" placeholder="请输入姓名"></el-input>
      <el-input class="item" v-model="srhCond.phone" placeholder="请输入电话"></el-input>
      <el-button class="item" type="primary" @click.prevent="search()">搜索</el-button>
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
      <el-table-column prop="name" label="人员名称"></el-table-column>
      <el-table-column prop="dept" label="部门"></el-table-column>
      <el-table-column prop="occupation" label="职位"></el-table-column>
      <el-table-column prop="phone" label="联系电话"></el-table-column>
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
        title="值班人员新增"
        :visible.sync="addFormVisible"
        :modal-append-to-body="false"
        width="40%"
      >
        <el-row style="margin: 20px 0px;text-align: center;">
          <el-radio-group v-model="insertType">
            <el-radio :label="0">单个新增</el-radio>
            <el-radio :label="1">批量新增</el-radio>
          </el-radio-group>
        </el-row>
        <el-form v-if="!insertType" ref="addForm" :model="addForm" :rules="rules">
          <el-form-item label="姓名" prop="name" label-width="120px" required>
            <el-input v-model="addForm.name" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="部门" prop="dept" label-width="120px">
            <!--            <el-input v-model="addForm.dept" style="width: 90%"></el-input>-->
            <el-select v-model="addForm.dept" style="width: 90%">
              <el-option
                v-for="(item,index) in deptList"
                :key="index"
                :label="item.precinctName"
                :value="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="职位" prop="occupation" label-width="120px">
            <el-input v-model="addForm.occupation" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone" label-width="120px">
            <el-input v-model.number="addForm.phone" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="照片" prop="picture" label-width="120px">
            <el-upload
              class="avatar-uploader"
              action="/binhuapis/file/uploadImage"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
        <el-upload
          v-if="insertType"
          class="upload-demo upload"
          drag
          action
          ref="upload"
          :limit="1"
          :file-list="fileList"
          :headers="{
            Authorization:token
          }"
          style="margin-left: 180px"
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
          <div slot="tip" class="el-upload__tip" style="margin-left: -55px">
            只能上传excel文件，且不超过5MB
            <span style="color: darkred;">且上传的文件内容必须符合模板规范</span>
            <a class="sel_btn" download="值班人员导入模板" href="template/值班人员导入.xlsx">
              点击下载模板
              <i class="el-icon-download"></i>
            </a>
          </div>
        </el-upload>
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
          <el-form-item label="姓名" prop="name" label-width="120px">
            <el-input v-model="editForm.name" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="部门" prop="dept" label-width="120px">
            <!--            <el-input v-model="editForm.dept" style="width: 90%"></el-input>-->
            <el-select v-model="editForm.dept" style="width: 90%">
              <el-option
                v-for="(item,index) in deptList"
                :key="index"
                :label="item.precinctName"
                :value="item.precinctId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="职位" prop="occupation" label-width="120px">
            <el-input v-model="editForm.occupation" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="phone" label-width="120px">
            <el-input v-model.number="editForm.phone" style="width: 90%"></el-input>
          </el-form-item>
          <el-form-item label="照片" prop="picture" label-width="120px">
            <el-upload
              class="avatar-uploader"
              action="/binhuapis/file/uploadImage"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
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
    var phoneCheck = function (rule, value, callback) {
      var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/ // 手机号码
      var isMob = /^0?1[3|4|5|8][0-9]\d{8}$/ // 座机格式
      if (!(value === null || value === undefined || value === '')) {
        if (isMob.test(value) || isPhone.test(value)) {
          callback()
        } else {
          callback(new Error('非有效电话号码！'))
        }
      } else {
        callback()
      }
    }
    return {
      imageUrl: '',
      deptList: [],
      menus: configs.menus,
      menuIds: JSON.parse(localStorage.getItem('menuIds')),
      tableData: [],
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        dept: [{ required: true, message: '请输入部门', trigger: 'blur' }],
        occupation: [{ required: true, message: '请输入职位', trigger: 'blur' }],
        picture: [{ required: true, message: '请上传人员照片', trigger: 'blur' }],
        phone: [
          {
            required: true,
            message: '请输入合法电话号码',
            validator: phoneCheck,
            trigger: 'blur'
          }
        ]
      },
      addForm: {
        dept: '',
        name: '',
        occupation: '',
        phone: '',
        picture: ''
      },
      editForm: {
        dept: '',
        name: '',
        occupation: '',
        phone: '',
        picture: ''
      },
      srhCond: {
        name: '',
        phone: ''
      },
      currentPage: 1,
      pageSize: 12,
      listLoading: false,
      addFormVisible: false,
      editFormVisible: false,
      editLoading: false,
      selectionIds: [],
      insertType: 0,
      fileList: [],
      files: '',
      token: localStorage.getItem('token')
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = res.data
      this.addForm.picture = res.data
      this.editForm.picture = res.data
      // this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const picFormat = ['image/jpeg', 'image/png', 'image/jpg']
      const isJPG = picFormat.indexOf(file.type) !== -1
      const isLt20M = file.size / 1024 / 1024 < 20

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG、png 格式!')
      }
      if (!isLt20M) {
        this.$message.error('上传头像图片大小不能超过 20MB!')
      }
      return isJPG && isLt20M
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
      this.$confirm('此操作将删除选中项, 是否继续?', '提示', {
        type: 'warning'
      }).then(() => {
        let ids = ''
        if (row === undefined) {
          ids = this.selectionIds.toString()
        } else {
          ids = row.id
        }
        axios.post('/binhuapis/deleteDutyPerson?ids=' + ids).then(res => {
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
      console.log('洒洒水', row)
      this.imageUrl = row.picture
      this.editForm = Object.assign({}, row)
    },
    // 显示新增界面
    showAdd() {
      this.insertType = 0
      this.addFormVisible = true
      this.$nextTick(() => {
        this.$refs['addForm'].resetFields()
      })
    },
    // 选择框发生变化
    handleSelectionChange(rows) {
      this.selectionIds = []
      rows.forEach(row => {
        this.selectionIds.push(row.id)
      })
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
    },
    query() {
      this.listLoading = true
      axios
        .get('/binhuapis/queryDutyPerson')
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
      if (this.insertType) {
        let formdata = new FormData()
        let url = '/binhuapis/insertDutyPersonByExcel?userId=' + localStorage.getItem('userId')
        formdata.append('file', this.files)
        console.log(this.files)
        axios.post(url, formdata)
          .then(res => {
            if (res.data.code == 200) {
              this.addFormVisible = false
              this.query()
              this.$message.success('上传成功')
              this.fileList = []
            } else {
              this.addFormVisible = false
              this.$message.error(res.data.message)
            }
          })
          .catch(function (error) {
            this.addFormVisible = false
            this.$message.warning(error)
          })
        return false
      }
      this.$refs[formName].validate(valid => {
        if (valid) {
          let url = '/binhuapis/insertDutyPerson'
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
          axios.post('/binhuapis/updateDutyPerson', this.editForm).then(res => {
            if (res.data.code === 200) {
              this.editFormVisible = false
              this.$message.success('编辑成功')
              this.query()
            }
          })
        }
      })
    },
    search() {
      let url = '/binhuapis/queryDutyPerson?'
      let param = qs.stringify(this.srhCond)
      url += param
      axios
        .get(url)
        .then(res => {
          this.tableData = res.data.data
          this.listLoading = false
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    queryDept() {
      let url = '/auth/v2/precinct/queryPrecinct?upPrecinctId=373&precinctKind=100'
      axios.get(url).then(res => {
        if (res.data.code === 200) {
          this.deptList = res.data.data
        } else {
          this.$message.error('部门数据查询失败！')
        }
      })
    }
  },
  mounted() {
    this.query()
    this.queryDept()
  }
}
</script>
<style lang="scss">
#dutyPeople {
  .item {
    width: 120px;
    margin-left: 50px;
  }
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
