<!-- Created by Y on 2018-06-27 -->
<template>
  <div class="organization" id="organization">
    <!--    <AreaTree titleText="重点单位" :optionsDisable="true" @node-click="handleNodeClick"></AreaTree>-->
    <!-- <section class="right-area"> -->
    <!-- <header class="r-title">
        <el-input placeholder="搜索" size="medium" v-model="srhData"></el-input>
        <div class="srhIcon" @click="searchConditions">
          <label class="el-icon-search"></label>
        </div>
        <el-button type="primary" @click="exportExcel">导 出</el-button>
    </header>-->
    <Table
      :listLoading="tableLoading"
      :tableData="tableData"
      :columnInfo="columnInfo"
      :total="total"
      :currentPage="currentPage"
      :pageSize="pageSize"
      @current-change="handleCurrentChange"
      @add-row="addNew"
      @del-row="delRow"
      @edit-row="handleEdit"
      @search-conditions="searchConditions"
      ref="table"
    ></Table>
    <!-- 弹窗 -->
    <div class="dialog">
      <!-- 增加弹窗 -->
      <el-dialog
        title="新增重点单位"
        :visible.sync="dialogVisible"
        :modal-append-to-body="false"
        @close="resetForm('device_new')"
        width="750px"
      >
        <el-row style="margin: 20px 0; text-align: center">
          <el-radio-group v-model="insertType">
            <el-radio :label="0">单个新增</el-radio>
            <el-radio :label="1">批量新增</el-radio>
          </el-radio-group>
        </el-row>
        <el-form v-if="!insertType" ref="device_new" :model="ruleForm" :rules="rules">
          <el-form-item label="所在区域" prop="precinctName" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.precinctName" placeholder="请输入区域名称" disabled="disabled"></el-input>
          </el-form-item>
          <!--            <el-form-item label="区域id" prop="precinctId" :label-width="formLabelWidth">-->
          <!--                <el-input v-model="ruleForm.precinctId" placeholder="请输入区域id"></el-input>-->
          <!--            </el-form-item>-->
          <el-form-item label="企业名称" prop="companyName" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.companyName" placeholder="请输入企业名称"></el-input>
          </el-form-item>
          <el-form-item label="企业地址" prop="companyAddress" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.companyAddress" placeholder="请输入企业地址"></el-input>
          </el-form-item>
          <el-form-item label="负责人" prop="contacts" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.contacts" placeholder="请输入负责人姓名"></el-input>
          </el-form-item>
          <el-form-item label="负责人电话" prop="phoneNumber1" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.phoneNumber1" placeholder="请输入负责人电话"></el-input>
          </el-form-item>
          <el-form-item label="安全干部" prop="cadres" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.cadres" placeholder="请输入安全干部姓名"></el-input>
          </el-form-item>
          <el-form-item label="安全干部电话" prop="phoneNumber2" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.phoneNumber2" placeholder="请输入安全干部电话"></el-input>
          </el-form-item>
          <el-form-item label="经度" prop="gpsx" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.gpsx" placeholder="请输入经度"></el-input>
          </el-form-item>
          <el-form-item label="纬度" prop="gpsy" :label-width="formLabelWidth">
            <el-input v-model="ruleForm.gpsy" placeholder="请输入纬度"></el-input>
          </el-form-item>
        </el-form>
        <el-upload
          v-if="insertType"
          class="upload-demo upload"
          drag
          action
          ref="upload"
          :limit="1"
          :on-change="changeExcel"
          :file-list="fileList"
          :on-preview="handlePreview"
          :on-remove="handleRemove1"
          :auto-upload="false"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            将文件拖到此处，或
            <em>点击上传</em>
          </div>
          <div slot="tip" class="el-upload__tip">
            只能上传excel文件，且不超过5MB
            <span style="color: darkred">且上传的文件内容必须符合模板规范</span>
            <!--            <a class="sel_btn" @click="downloadMu">-->
            <!--              点击下载模板-->
            <!--              <i class="el-icon-download"></i>-->
            <!--            </a>-->
            <a download="重点单位-模板" href="../../../static/template/重点单位-模板.xlsx" class="sel_btn">
              点击下载模板
              <i class="el-icon-download"></i>
            </a>
          </div>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="addNewDevice('device_new')">确定</el-button>
          <el-button type="primary" plain @click="resetForm('device_new')">取消</el-button>
        </div>
      </el-dialog>
      <!-- 编辑弹窗 -->
      <el-dialog
        title="编辑重点单位"
        :visible.sync="dialogedit"
        :modal-append-to-body="false"
        @close="resetForm('editor')"
        width="750px"
      >
        <el-form :model="editor" ref="editor" :rules="rules">
          <!--<el-form-item label="区域id" prop="precinctId" :label-width="formLabelWidth">
                    <el-input v-model="editor.precinctId" :disabled="true"></el-input>
          </el-form-item>-->
          <el-form-item label="所在区域" prop="precinctName" :label-width="formLabelWidth">
            <el-input v-model="editor.precinctName" :disabled="true"></el-input>
          </el-form-item>
          <!--<el-form-item label="企业id" prop="id" :label-width="formLabelWidth">
                    <el-input v-model="editor.id" :disabled="true"></el-input>
          </el-form-item>-->
          <el-form-item label="企业名称" prop="companyName" :label-width="formLabelWidth">
            <el-input v-model="editor.companyName"></el-input>
          </el-form-item>
          <el-form-item label="企业地址" prop="companyAddress" :label-width="formLabelWidth">
            <el-input v-model="editor.companyAddress"></el-input>
          </el-form-item>
          <el-form-item label="负责人" prop="contacts" :label-width="formLabelWidth">
            <el-input v-model="editor.contacts"></el-input>
          </el-form-item>
          <el-form-item label="负责人电话" prop="phoneNumber1" :label-width="formLabelWidth">
            <el-input v-model="editor.phoneNumber1"></el-input>
          </el-form-item>
          <el-form-item label="安全干部" prop="cadres" :label-width="formLabelWidth">
            <el-input v-model="editor.cadres"></el-input>
          </el-form-item>
          <el-form-item label="安全干部电话" prop="phoneNumber2" :label-width="formLabelWidth">
            <el-input v-model="editor.phoneNumber2"></el-input>
          </el-form-item>
          <el-form-item label="经度" prop="gpsx" :label-width="formLabelWidth">
            <el-input v-model="editor.gpsx" placeholder="请输入经度"></el-input>
          </el-form-item>
          <el-form-item label="纬度" prop="gpsy" :label-width="formLabelWidth">
            <el-input v-model="editor.gpsy" placeholder="请输入纬度"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="editForm('editor')">确定</el-button>
          <el-button type="primary" plain @click="resetForm('editor')">取消</el-button>
        </div>
      </el-dialog>
    </div>
    <!-- </section> -->
  </div>
</template>
<script>
import axios from '@/utils/request.js'
import qs from 'qs'
import AreaTree from '../../components/AreaTree'
import Table from '../../components/Table'
// import XLSX from 'xlsx'
export default {
  name: 'Organization',
  components: { AreaTree, Table },
  data() {
    const validateGpsX = (rule, value, callback) => {
      let reg = /^[0-9,.-]*$/
      if (!reg.test(value)) {
        return callback(new Error('请输入数字值'))
      } else {
        if (value < -180 || value > 180) {
          return callback(new Error('gpsX的范围是[-180~180]'))
        } else {
          callback()
        }
      }
    }
    const validateGpsY = (rule, value, callback) => {
      let reg = /^[0-9,.-]*$/
      if (!reg.test(value)) {
        return callback(new Error('请输入数字值'))
      } else {
        if (value < -85 || value > 85) {
          return callback(new Error('gpsY的范围是[-85~85]'))
        } else {
          callback()
        }
      }
    }
    const riverTel = (rule, value, callback) => {
      if (value) {
        // let reg = /^[1][3,4,5,7,8][0-9]{9}$/
        let reg = /^\d+$/
        if (!reg.test(value)) {
          return callback(new Error('请输入正确的电话号码'))
        } else {
          callback()
        }
      } else {
        callback()
      }
    }
    let checkSpecificKey = (rule, value, callback) => {
      let regEn = /[`~!@#$%^&*()_+<>?:"{},.\\/;'[\]]/im
      let regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im
      if (regCn.test(value) || regEn.test(value)) {
        callback(new Error('包含特殊字符'))
      } else {
        callback()
      }
    }
    return {
      // 模块权限
      menus: configs.menus,
      menuIds: JSON.parse(sessionStorage.getItem('menuIds')),
      precinctId_v: '', // 用于判断是否已选地址
      insertType: 0, // 判断是单个还是批量新增
      fileList: [],
      fileName: '',
      files: '',
      index: '', // 要编辑的下标
      tableLoading: false, // 控制加载动画
      currentPage: 1, // 当前的页数
      pageSize: 14, // 每页的条数
      srhData: '', // 搜索的内容
      dialogVisible: false, // 控制增加弹窗显示
      dialogedit: false, // 控制编辑弹窗显示
      formLabelWidth: '110px',
      precinctName: '', // 地区参数
      allData: [], // 所有数据
      storage: [], // 存储的所有数据
      // 表头
      columnInfo: [
        {
          prop: 'companyName',
          label: '企业名称',
          width: ''
        },
        {
          prop: 'companyAddress',
          label: '企业地址',
          width: ''
        },
        {
          prop: 'contacts',
          label: '负责人',
          width: ''
        },
        {
          prop: 'phoneNumber1',
          label: '负责人电话',
          width: ''
        },
        {
          prop: 'cadres',
          label: '安全干部',
          width: ''
        },
        {
          prop: 'phoneNumber2',
          label: '安全干部电话',
          width: ''
        }
      ],
      // 当前页-表的数据
      tableData: [],
      // 表的长度
      total: 0,
      // 要增加的数据
      ruleForm: {
        precinctName: '',
        precinctId: '',
        companyName: '',
        companyAddress: '',
        contacts: '',
        phoneNumber1: '',
        cadres: '',
        phoneNumber2: '',
        gpsy: '',
        gpsx: ''
      },
      rules: {
        gpsx: [{ validator: validateGpsX, trigger: 'blur' }],
        gpsy: [{ validator: validateGpsY, trigger: 'blur' }],
        phoneNumber1: [
          { required: true, message: '负责人电话不能为空', trigger: 'blur' },
          { validator: riverTel, trigger: 'blur' }
        ],
        phoneNumber2: [{ validator: riverTel, trigger: 'blur' }],
        precinctName: [
          { required: true, message: '所在区域不能为空', trigger: 'blur' },
          { validator: checkSpecificKey }
        ],
        companyName: [
          { required: true, message: '企业名称不能为空', trigger: 'blur' },
          { validator: checkSpecificKey }
        ],
        companyAddress: [
          { required: true, message: '企业地址不能为空', trigger: 'blur' },
          { validator: checkSpecificKey }
        ],
        contacts: [{ required: true, message: '负责人不能为空', trigger: 'blur' }, { validator: checkSpecificKey }],
        cadres: [{ required: true, message: '安全干部不能为空', trigger: 'blur' }, { validator: checkSpecificKey }]
      },
      // 要编辑的数据
      editor: {
        id: '',
        precinctName: '',
        precinctId: '',
        companyName: '',
        companyAddress: '',
        contacts: '',
        phoneNumber1: '',
        cadres: '',
        phoneNumber2: '',
        gpsy: '',
        gpsx: ''
      }
    }
  },
  methods: {
    // 中间的地址选择
    handleNodeClick(data) {
      this.precinctName = data.name
      this.precinctId_v = data.id
      this.ruleForm.precinctName = data.name
      this.ruleForm.precinctId = data.id
      this.searchAllData()
    },
    // 表格导出
    exportExcel() {
      let _this = this
      let url = '/community/excel/exportCompany?precinctName=' + sessionStorage.getItem('streetName')
      axios
        .get(url, { responseType: 'arraybuffer' })
        .then(function (res) {
          var content = res
          var blob = new Blob([content.data], {
            type: 'application/vnd.ms-excel;charset=utf-8'
          })
          var fileName = '重点单位' + '.xls'
          if ('download' in document.createElement('a')) {
            // 非IE下载
            var elink = document.createElement('a')
            elink.download = fileName
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink)
          } else {
            // IE10+下载
            navigator.msSaveBlob(blob, fileName)
          }
          _this.dialogVisible = false
        })
        .catch(function (error) {
          console.log(error)
          _this.$notify({
            title: '警告',
            message: error,
            type: 'warning',
            duration: 1000
          })
        })
    },
    // 下载表格模板
    downloadMu() {
      let _this = this
      let url = '/community/excel01/exportCompany'
      axios
        .get(url, { responseType: 'arraybuffer' })
        .then(function (res) {
          var content = res
          var blob = new Blob([content.data], {
            type: 'application/vnd.ms-excel;charset=utf-8'
          })
          var fileName = '重点单位-模板' + '.xls'
          if ('download' in document.createElement('a')) {
            // 非IE下载
            var elink = document.createElement('a')
            elink.download = fileName
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink)
          } else {
            // IE10+下载
            navigator.msSaveBlob(blob, fileName)
          }
          _this.dialogVisible = false
        })
        .catch(function (error) {
          console.log(error)
          _this.$notify({
            title: '警告',
            message: error,
            type: 'warning',
            duration: 1000
          })
        })
    },
    // 按地区的所有数据
    searchAllData() {
      this.tableLoading = true
      axios
        .get('/community/street/Information/queryCompany?precinctName=' + sessionStorage.getItem('streetName'))
        .then((res) => {
          let tableData = []
          this.total = res.data.length // 数据条数
          res.data.forEach((e) => {
            const obj = {}
            if (!e.id) {
              obj.id = ''
            } else {
              obj.id = e.id
            }
            if (!e.precinctName) {
              obj.precinctName = ''
            } else {
              obj.precinctName = e.precinctName
            }
            if (!e.precinctId) {
              obj.precinctId = ''
            } else {
              obj.precinctId = e.precinctId
            }
            if (!e.companyName) {
              obj.companyName = ''
            } else {
              obj.companyName = e.companyName
            }
            if (!e.companyAddress) {
              obj.companyAddress = ''
            } else {
              obj.companyAddress = e.companyAddress
            }
            if (!e.contacts) {
              obj.contacts = ''
            } else {
              obj.contacts = e.contacts
            }
            if (!e.phoneNumber1) {
              obj.phoneNumber1 = ''
            } else {
              obj.phoneNumber1 = e.phoneNumber1
            }
            if (!e.phoneNumber2) {
              obj.phoneNumber2 = ''
            } else {
              obj.phoneNumber2 = e.phoneNumber2
            }
            if (!e.cadres) {
              obj.cadres = ''
            } else {
              obj.cadres = e.cadres
            }
            if (!e.gpsy) {
              obj.gpsy = ''
            } else {
              obj.gpsy = e.gpsy
            }
            if (!e.gpsx) {
              obj.gpsx = ''
            } else {
              obj.gpsx = e.gpsx
            }
            tableData.push(obj)
          })
          this.allData = tableData
          this.storage = tableData
          if (this.srhData) {
            this.searchConditions()
          } else {
            this.tableData = tableData.slice(
              this.currentPage * this.pageSize - this.pageSize,
              this.currentPage * this.pageSize
            )
          }
          this.tableLoading = false
        })
    },
    // 查询-过滤
    searchConditions() {
      this.allData = this.storage
      let search = this.$refs.table.srhData
      let searchData = ''
      if (search) {
        searchData = this.allData.filter(function (item) {
          return Object.keys(item).some(function (key) {
            if (key != 'id' && key != 'precinctId' && key != 'precinctName' && key != 'gpsx' && key != 'gpsy') {
              return String(item[key]).toLowerCase().indexOf(search) > -1
            }
          })
        })
        console.log(searchData)
        this.total = searchData.length
        this.currentPage = 1 // 默认到首页
        this.allData = searchData
        this.tableData = searchData.slice(
          this.currentPage * this.pageSize - this.pageSize,
          this.currentPage * this.pageSize
        )
      } else {
        this.searchAllData()
      }
    },
    // 接收子组件的-编辑按钮的事件
    handleEdit(index, row) {
      this.dialogedit = true
      console.log(index, row)
      this.editor.id = row.id
      this.editor.precinctName = row.precinctName
      this.editor.precinctId = row.precinctId
      this.editor.companyName = row.companyName
      this.editor.companyAddress = row.companyAddress
      this.editor.contacts = row.contacts
      this.editor.phoneNumber1 = row.phoneNumber1
      this.editor.cadres = row.cadres
      this.editor.phoneNumber2 = row.phoneNumber2
      this.editor.gpsy = row.gpsy
      this.editor.gpsx = row.gpsx
      this.index = index
    },
    // 编辑-确定
    editForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = this.$refs[formName].model
          this.dialogedit = false
          let param = qs.stringify(data)
          axios
            .post('/community/street/Information/updateCompany', param)
            .then((res) => {
              this.searchAllData() // 重置表格数据
              this.$notify({
                title: '成功',
                message: '编辑成功',
                type: 'success',
                duration: 1000
              })
            })
            .catch(function (error) {
              this.$notify({
                title: '警告',
                message: error,
                type: 'warning',
                duration: 1000
              })
            })
        } else {
          return false
        }
      })
    },
    // 接收子组件的-翻页按钮的事件
    handleCurrentChange(val) {
      this.currentPage = val
      console.log(val)
      console.log(this.allData)
      this.tableData = this.allData.slice(
        this.currentPage * this.pageSize - this.pageSize,
        this.currentPage * this.pageSize
      )
    },
    // 接受子组件的-增加按钮的事件
    addNew(val) {
      if (this.precinctId_v === '') {
        this.$notify({
          title: '警告',
          message: '还没选择地区，无法添加重点单位',
          type: 'warning',
          duration: 1000
        })
      } else {
        this.dialogVisible = true
      }
    },
    // 接受子组件的-删除按钮的事件
    delRow(val) {
      console.log(val)
      if (val.length > 0) {
        this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let delArry = []
            val.forEach((e) => {
              const obj = {}
              obj.id = e.id
              delArry.push(obj)
            })
            console.log(delArry)
            axios
              .post('/community/street/Information/deleteCompany', delArry)
              .then((res) => {
                console.log(res.data)
                this.$notify({
                  title: '成功',
                  message: '删除成功!',
                  type: 'success',
                  duration: 1000
                })
                this.currentPage = 1 // 默认到首页
                // 重新渲染表格
                this.searchAllData()
              })
              .catch(function (error) {
                this.$notify({
                  title: '警告',
                  message: error,
                  type: 'warning',
                  duration: 1000
                })
              })
          })
          .catch(function (error) {
            console.log(error)
          })
      } else {
        this.$notify({
          title: '警告',
          message: '请先选择要删除的数据',
          type: 'warning',
          duration: 1000
        })
      }
    },
    // 增加-弹窗-取消
    resetForm(formName) {
      this.dialogVisible = false
      this.dialogedit = false
      if (this.insertType) {
        this.fileList = []
      } else {
        this.$refs[formName].resetFields()
      }
    },
    // 增加-弹窗-确定
    addNewDevice(formName) {
      let _this = this
      if (this.insertType) {
        console.log(this.files)
        console.log(this.fileList)
        let formdata = new FormData()
        formdata.append('file', this.files)
        formdata.append('precinctId', this.precinctId)
        axios
          .post('/community/ImportExcel/importCompany', formdata)
          .then((res) => {
            if (res.data.code === 200 && res.data.message === 'ok') {
              this.dialogVisible = false
              this.searchAllData() // 重置表格数据
              _this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 1000
              })
              this.fileList = []
            } else {
              _this.$notify({
                title: '警告',
                message: res.data.message,
                type: 'warning',
                duration: 1000
              })
              this.dialogVisible = false
              this.fileList = []
            }
          })
          .catch(function (error) {
            _this.$notify({
              title: '警告',
              message: error,
              type: 'warning',
              duration: 1000
            })
          })
        return false
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)
          let param = qs.stringify(this.ruleForm)
          axios
            .post('/community/street/Information/addCompany', param)
            .then((res) => {
              this.dialogVisible = false
              this.searchAllData() // 重置表格数据
              this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 1000
              })
              this.$refs[formName].resetFields()
            })
            .catch(function (error) {
              this.$notify({
                title: '警告',
                message: error,
                type: 'warning',
                duration: 1000
              })
            })
        } else {
          return false
        }
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
    changeExcel(file, fileList) {
      console.log('文件:', file.name)
      const extension = file.name.split('.')[1] === 'xls'
      const extension2 = file.name.split('.')[1] === 'xlsx'
      const isLt2M = file.size / 1024 / 1024 < 5
      if (!extension && !extension2) {
        this.$notify({
          title: '警告',
          message: '上传模板只能是 xls、xlsx格式!',
          type: 'warning',
          duration: 1000
        })
        this.files = ''
        this.fileList = []
        return false
      }
      if (!isLt2M) {
        this.$notify({
          title: '警告',
          message: '上传模板大小不能超过 5MB!',
          type: 'warning',
          duration: 1000
        })
        this.files = ''
        this.fileList = []
        return false
      }
      this.files = file.raw
      this.fileList = fileList[0].raw
    }
  },
  mounted() {
    this.precinctId = sessionStorage.getItem('streetId')
    this.precinctId_v = sessionStorage.getItem('streetId')
    this.precinctName = sessionStorage.getItem('streetName')
    this.ruleForm.precinctId = sessionStorage.getItem('streetId')
    this.ruleForm.precinctName = sessionStorage.getItem('streetName')
    this.searchAllData()
  }
}
</script>
<style lang="scss" scoped>
.organization {
  width: 100%;
  height: 100%;
  background: #f8f9ff;

  .right-area {
    flex-grow: 1;
    width: 62.2rem;
    padding-left: 1.4rem;
    height: 100%;

    .r-title {
      font-size: 0;

      .srhIcon {
        background: #6187d8;
        width: 2.5rem;
        display: inline-block;
        font-size: 0.7rem;
        line-height: 1.7rem;
        text-align: center;
        color: #fff;
        margin-right: 12px;
        cursor: pointer;

        .el-icon-search {
          cursor: pointer;
        }

        &:hover {
          background: #66b1ff;
          border-color: #66b1ff;
        }

        &:active {
          outline: 0;
          background: #6187d8;
        }
      }

      .el-button--primary {
        background: #6187d8;
        // width: 2.5rem;
        display: inline-block;
        font-size: 0.7rem;
        line-height: 1.7rem;
        text-align: center;
        color: #fff;
        padding-top: 0;
        padding-bottom: 0;
      }

      .el-input {
        width: 12.25rem;
      }
    }

    .upload {
      text-align: center;
      margin-top: 4rem;
      margin-bottom: 4rem;
    }

    .sel_btn {
      height: 21px;
      line-height: 21px;
      padding: 0 11px;
      background: #02bafa;
      border: 1px #26bbdb solid;
      border-radius: 3px;
      /*color: #fff;*/
      display: inline-block;
      text-decoration: none;
      font-size: 12px;
      outline: none;
      cursor: pointer;
    }
  }
}
</style>
