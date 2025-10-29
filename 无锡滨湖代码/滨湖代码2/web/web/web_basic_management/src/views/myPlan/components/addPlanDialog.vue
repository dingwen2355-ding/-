<template>
  <div v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '编辑预案信息' : isCode === 1 ? '查看预案信息详情' : '新增预案信息'"
      :visible.sync="dialogVisible"
      width="800px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form
          ref="formLabel"
          :rules="rules"
          :model="form"
          style="margin-right: 35px"
          label-width="110px"
          :disabled="isCode == 1"
        >
          <el-form-item label="名称" prop="name">
            <el-input clearable v-model="form.name" placeholder="请输入预案名称"></el-input>
          </el-form-item>
          <el-form-item label="级别" prop="levelId" v-if="addShow">
            <el-select
              filterable
              clearable
              v-model="form.levelId"
              placeholder="请选择预案级别"
              style="width: 100%"
            >
              <el-option
                v-for="item in manageLevelList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="类型" prop="emergencyTypeId">
            <el-cascader
              style="width: 100%"
              filterable
              v-model="form.emergencyTypeId"
              :options="options"
              :props="props"
              placeholder="请选择类型"
              clearable
              @change="change"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="分类" prop="eventTypeId" v-if="addShow">
            <el-select
              filterable
              clearable
              v-model="form.eventTypeId"
              placeholder="请选择分类"
              style="width: 100%"
            >
              <el-option
                v-for="item in eventTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上传文档" prop="fileUrl">
            <el-upload
              style="width: 100%"
              ref="upload"
              action="/"
              :multiple="false"
              :file-list="fileList"
              :show-file-list="true"
              :on-progress="handleProgress"
              :on-change="handleChange"
              :on-remove="handleRemove"
              :on-preview="handlePreview"
              :before-remove="beforeRemove"
              :auto-upload="false"
              :on-exceed="showExceedTips"
              accept=".docx, .pdf, .PDF, .xlsx, .jpg, .PNG"
            >
              <el-button slot="trigger" size="small" type="primary">上传文档</el-button>
              <div slot="tip" class="el-upload__tip">只能上传docx/pdf/xlsx/jpg/PNG文件，且不超过50MB</div>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Axios from '@/utils/request'
import znvTime from '../../../utils/znvTime'

export default {
  name: 'AddPlanDialog',
  props: ['dialogVisible', 'formInfo', 'isCode', 'addShow'],
  watch: {
    dialogVisible(v) {
      console.log(v)
      this.dialogVisible = v
      this.fileList = []
      let obj = {}
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      console.log('this.form', this.form)
      if (this.formInfo.fileData) {
        console.log('fileData', this.formInfo.fileData)
        for (let item of this.formInfo.fileData) {
          console.log('文档 item: ', item)
          obj = {
            url: item.fileUrl,
            raw: new File([item], item.fileName),
            name: item.fileName
          }
          this.fileList.push(obj)
        }
      }
      // if (this.formInfo.fileUrls) {
      //   console.log('flieUrl', this.formInfo.fileUrls)
      //   for (let item of this.formInfo.fileUrls) {
      //     console.log('文档路径item: ', item)
      //     flieUrl = item.split('/')[item.split('/').length - 1].split('-')[1]
      //     console.log('flieUrl', flieUrl)
      //     obj = {
      //       url: item,
      //       raw: new File([item], flieUrl),
      //       name: flieUrl
      //     }
      //     this.fileList.push(obj)
      //   }
      // }
    }
  },
  data() {
    return {
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        levelId: [{ required: true, message: '请选择级别', trigger: 'blur' }],
        eventTypeId: [{ required: true, message: '请选择类型', trigger: 'blur' }],
        emergencyTypeId: [{ required: true, message: '请选择分类', trigger: 'blur' }]
      },
      loading: false,
      manageLevelList: [], // 管理级别
      eventTypeList: [],
      options: [],
      props: {},
      form: {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrls: [] // 上传文档
      },
      newFormD: {},
      fileList: []
    }
  },
  mounted() {
    this.getManageLevel() // 管理级别
    this.queryPlanType()
    this.queryEventType()
  },
  methods: {
    showExceedTips(files, fileList) {
      this.$message.warning('请最多上传1个文件。')
    },
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/menu/getStorageLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data.data
      })
    },
    change(value) {
      console.log(value)
    },
    // 类型获取
    queryPlanType() {
      let url = '/binhuapis/get-type-tree'
      Axios.get(url).then((r) => {
        console.log('类型信息：', r.data.data)
        this.options = r.data.data
      })
    },
    // 分类获取
    queryEventType() {
      let url = '/binhuapis/get-event-type'
      Axios.get(url).then((r) => {
        console.log('分类获取', r.data.data)
        this.eventTypeList = r.data.data
      })
    },
    // 保存上传文件
    saveUploadFile() {
      let url = '/binhuapis/save-plan-data'
      let params = {
        addPlanDataBeans: [],
        updatePlanDataBeans: [],
        delIds: []
      }
      let addFiles = {}
      for (let item of this.fileList) {
        addFiles = {
          planId: this.form.id,
          fileUrl: item.url,
          fileName: item.name.substring(0, item.name.lastIndexOf('.')),
          createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD-HH-MM-SS')
        }
        params.addPlanDataBeans.push(addFiles)
      }
      Axios.post(url, params).then((res) => {
        if (res.data.code == 200) {
          this.$message({
            type: 'success',
            message: '保存成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: '保存失败'
          })
        }
      })
    },
    reset() {
      this.loading = false
      this.form = {
        id: '',
        name: '', // 名字
        levelId: '', // 级别
        levelName: '',
        eventTypeId: '', // 类型
        eventTypeName: '',
        emergencyTypeId: '', // 分类
        emergencyTypeName: '',
        status: '0', // 状态
        shared: '0', // 是否共享
        createId: localStorage.getItem('userId'),
        createName: localStorage.getItem('user'),
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrls: [] // 上传文档
      }
      this.$refs.upload.clearFiles()
    },
    // 取 消
    handleClose() {
      if (this.isCode != 1) {
        this.$confirm('尚未保存，是否在离开页面前保存？', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '提交保存',
          cancelButtonText: '关闭窗口',
          type: 'warning'
        })
          .then(() => {
            this.handleOk()
          })
          .catch((e) => {
            if (e === 'cancel') {
              this.reset()
              this.$emit('update:dialogVisible', false)
            }
          })
      } else {
        this.$emit('update:dialogVisible', false)
      }
    },
    // 确 定
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      this.newFormD = JSON.parse(JSON.stringify(this.form))
      console.log('this.newFormD', this.newFormD)
      if (Array.isArray(this.newFormD.emergencyTypeId)) {
        this.newFormD.emergencyTypeId = this.newFormD.emergencyTypeId.join(',')
      }
      this.newFormD.planDataBeans = []
      let aboutData = {}
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/binhuapis/update-plan'
        for (let item of this.fileList) {
          console.log('item', item)
          aboutData = {
            planId: this.form.id,
            fileName: item.name.substring(0, item.name.lastIndexOf('.')),
            fileUrl: item.url,
            createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:ss')
          }
          this.newFormD.planDataBeans.push(aboutData)
        }
      } else {
        url = '/binhuapis/add-plan'
        for (let item of this.fileList) {
          console.log('item', item)
          aboutData = {
            fileUrl: item.url,
            fileName: item.name.substring(0, item.name.lastIndexOf('.')),
            createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:ss')
          }
          this.newFormD.planDataBeans.push(aboutData)
        }
      }
      console.log('this.newFormD', this.newFormD)
      Axios.post(url, this.newFormD).then((res) => {
        this.loading = false
        if (res.data.code == 200) {
          if (this.formInfo && this.formInfo.id) {
            this.$message.success('修改成功！')
          } else {
            this.$message.success('新增成功！')
          }
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    // 文件上传相关操作
    handleChange(file, fileList) {
      const types = file.name.split('.')[file.name.split('.').length - 1]
      const fileType = ['docx', 'pdf', 'PDF', 'xlsx', 'jpg', 'PNG'].some((item) => item === types)
      if (!fileType) {
        this.$message('格式错误！请重新选择')
      }
      let existFile = fileList.slice(0, fileList.length - 1).find((f) => f.name === file.name)
      if (existFile) {
        this.$message.error('当前文件已经存在!')
        fileList.pop()
      }
      console.log('fileList', fileList)
      if (file.size / 1024 / 1024 > 50) {
        this.$message({
          message: '上传文件大小不能超过 50MB!',
          type: 'error'
        })
        fileList.pop()
      } else {
        this.loading = true
        for (let item of fileList) {
          if (item.url == undefined) {
            console.log('fileList-item', item)
            let formData = new FormData()
            formData.append('uploadFile', item.raw)
            console.log('formData', formData)
            let url = '/binhuapis/resourceAgreement/uploadFile'
            Axios.post(url, formData).then((res) => {
              this.loading = false
              if (res.data.code === 200) {
                this.fileList.push({
                  url: res.data.data,
                  name: file.name
                })
                console.log('this.fileList', this.fileList)
                this.$message.success('上传成功！')
              } else {
                this.fileList.pop()
                console.log('this.fileList', this.fileList)
                this.$message.error('上传失败！')
              }
            })
          }
        }
      }
    },
    beforeRemove(file, fileList) {
      // 已上传文件删除前提醒确认
      if (this.isCode !== 1) {
        return this.$confirm('此操作将删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
      } else {
        this.$message.warning('查看时无法删除')
        return false
      }
    },
    handleRemove(file, fileList) {
      console.log('file', file)
      if (file && file.status === 'success') {
        console.log('this.fileList', this.fileList)
        let fileUrl = ''
        if (this.fileList && this.fileList.length > 0) {
          this.fileList.forEach((item) => {
            if (item.name === file.name) {
              fileUrl = item.url
            }
          })
          this.loading = true
          let url = '/binhuapis/resourceAgreement/deleteFile?fileUrl=' + fileUrl
          Axios.post(url).then((res) => {
            this.loading = false
            if (res.data.code === 200) {
              this.$message.success('删除成功！')
              this.fileList = fileList
            } else {
              this.$message.error('删除失败！')
              this.fileList = fileList
            }
          })
        }
        console.log('this.fileList', this.fileList)
      }
    },
    // 点击文件下载
    handlePreview(file) {
      console.log('file', file)
      let url = file.url
      window.open(url, '_bank')
    }
  }
}
</script>
<style scoped>
.el-upload__tip {
  font-size: 14px;
  color: #5daf34;
  margin-top: 0px;
}
</style>
