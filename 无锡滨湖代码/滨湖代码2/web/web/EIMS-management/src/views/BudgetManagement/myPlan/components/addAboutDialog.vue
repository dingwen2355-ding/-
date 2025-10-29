<template>
  <div v-if="dialogVisible">
    <el-dialog
      :title="isCode === 1 ? '查看相关资料' : '新增相关资料'"
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
          label-width="110px"
          style="margin-right: 35px"
          :disabled="isCode == 1"
        >
          <el-form-item label="名称" prop="fileName">
            <el-input clearable v-model="form.fileName" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input clearable type="textarea" v-model="form.remark" placeholder="请输入备注"></el-input>
          </el-form-item>
          <el-form-item label="上传文档" prop="fileUrl">
            <el-upload
              style="width: 100%"
              ref="upload"
              action="/"
              :multiple="false"
              :file-list="fileList"
              :show-file-list="true"
              :on-change="handleChange"
              :on-remove="handleRemove"
              :on-preview="handlePreview"
              :before-remove="beforeRemove"
              :auto-upload="false"
              accept=".xlsx,.docx,.pdf,.PDF,.jpg,.PNG"
            >
              <el-button slot="trigger" size="small" type="primary" :disabled="isCode == 1">上传文档</el-button>
              <div slot="tip" class="el-upload__tip">docx/pdf/xlsx/jpg/PNG文件，且不超过50MB</div>
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
import Axios from 'axios'
import znvTime from '../../../../utils/znvTime'

export default {
  name: 'AddAboutDialog',
  props: ['dialogVisible', 'formInfo', 'isCode', 'planId'],
  watch: {
    dialogVisible(v) {
      // console.log(v)
      this.dialogVisible = v
    },
    formInfo(v) {
      this.fileList = []
      if (v) {
        this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
        // console.log('this.form', this.form)
        // if (!v.fileName) {
        //   console.log('v.flieUrl', v.fileUrl)
        //   let flieUrl = v.fileUrl.split('/')[v.fileUrl.split('/').length - 1].split('-')[1]
        //   console.log('flieUrl', flieUrl)
        //   let obj = {
        //     url: v.fileUrl,
        //     raw: new File([v.fileUrl], flieUrl),
        //     name: flieUrl
        //   }
        //   this.fileList.push(obj)
        // } else {
        if (v.fileUrl) {
          // console.log('v.flieUrl', v.fileUrl)
          // let flieUrl = v.fileUrl.split('/')[v.fileUrl.split('/').length - 1].split('-')[1]
          let flieUrl = v.fileName
          // console.log('flieUrl', flieUrl)
          let obj = {
            url: v.fileUrl,
            raw: new File([v.fileUrl], flieUrl),
            name: flieUrl
          }
          this.fileList.push(obj)
        }
        // }
      }
    }
  },
  data() {
    return {
      rules: {
        // fileName: [{ required: true, message: '请输入名称', trigger: 'blur' }]
      },
      loading: false,
      form: {
        id: '',
        planId: this.planId,
        fileName: '', // 名字
        remark: '', // 备注
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrl: '' // 文档路径
      },
      newFormD: {},
      fileList: []
    }
  },
  methods: {
    reset() {
      this.loading = false
      this.form = {
        id: '',
        planId: this.planId,
        fileName: '', // 名字
        remark: '', // 备注
        createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:SS'), // 上传时间
        fileUrl: '' // 文档路径
      }
      this.$refs.upload.clearFiles()
    },
    // 取 消
    handleClose() {
      if (this.isCode !== 1) {
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
      let params = { addPlanDataBeans: [], updatePlanDataBeans: [] }
      this.newFormD = JSON.parse(JSON.stringify(this.form))
      // console.log('this.newFormD', this.newFormD)
      if (this.fileList) {
        for (let item of this.fileList) {
          // console.log('item', item)
          this.newFormD.fileName = item.name.substring(0, item.name.lastIndexOf('.'))
          this.newFormD.fileUrl = item.url
          params.addPlanDataBeans.push(this.newFormD)
        }
      }
      // console.log('params', params)
      let url = '/gtw/EIMS-management/save-plan-data'
      Axios.post(url, params).then((res) => {
        this.loading = false
        if (res.data.code == 200) {
          this.$message.success('新增成功！')
          this.$emit('success', this.planId)
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
      const fileType = ['xlsx', 'docx', 'pdf', 'PDF', 'jpg', 'PNG'].some((item) => item === types)
      if (!fileType) {
        this.$message('格式错误！请重新选择')
      }
      let existFile = fileList.slice(0, fileList.length - 1).find((f) => f.name === file.name)
      if (existFile) {
        this.$message.error('当前文件已经存在!')
        fileList.pop()
      }
      // console.log('fileList', fileList)
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
            // console.log('fileList-item', item)
            let formData = new FormData()
            formData.append('uploadFile', item.raw)
            // console.log('formData', formData)
            let url = '/gtw/EIMS-management/resourceAgreement/uploadFile'
            Axios.post(url, formData).then((res) => {
              this.loading = false
              if (res.data.code === 200) {
                this.fileList.push({
                  url: res.data.data,
                  name: file.name
                })
                // console.log('this.fileList', this.fileList)
                this.$message.success('上传成功！')
              } else {
                this.fileList.pop()
                // console.log('this.fileList', this.fileList)
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
      // console.log('file', file)
      if (file && file.status === 'success') {
        // console.log('this.fileList', this.fileList)
        let fileUrl = ''
        if (this.fileList && this.fileList.length > 0) {
          this.fileList.forEach((item) => {
            if (item.name === file.name) {
              fileUrl = item.url
            }
          })
          this.loading = true
          let url = '/gtw/EIMS-management/resourceAgreement/deleteFile?fileUrl=' + fileUrl
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
        // console.log('this.fileList', this.fileList)
      }
    },
    // 点击文件下载
    handlePreview(file) {
      // console.log('file', file)
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
