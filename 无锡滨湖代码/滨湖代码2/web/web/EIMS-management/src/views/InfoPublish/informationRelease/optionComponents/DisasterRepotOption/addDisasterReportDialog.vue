<template>
  <div class="addDisasterReportDialog" v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '修改灾情信息' : isCode === 1 ? '查看灾情信息详情' : '录入灾情信息'"
      :visible.sync="dialogVisible"
      width="1050px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-collapse v-model="activeName" accordion>
          <el-collapse-item title="● 基本信息" name="1">
            <basic-info :form.sync="form" :isCode.sync="isCode" ref="basicInfo" @choosedKey="choosedKey"></basic-info>
          </el-collapse-item>
          <el-collapse-item title="● 突发事件" name="2">
            <emergencyEvent :form.sync="form" :isCode.sync="isCode"></emergencyEvent>
          </el-collapse-item>
          <el-collapse-item title="● 预警信息" name="3">
            <warn-info :form.sync="form" :isCode.sync="isCode"></warn-info>
          </el-collapse-item>
          <div>
            <el-collapse-item title="● 附件" name="4">
              <el-upload
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
                :disabled="isCode == 1"
                accept=".doc, .docx, .pdf, .PDF"
              >
                <el-button slot="trigger" size="small" type="primary" :disabled="isCode == 1">上传文档</el-button>
                <div slot="tip" class="el-upload__tip">只能上传docx/pdf/xlsx/jpg/PNG文件，且不超过50MB</div>
              </el-upload>
            </el-collapse-item>
          </div>
        </el-collapse>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import emergencyEvent from './components/emergencyEvent.vue'
import basicInfo from './components/basicInfo.vue'
import warnInfo from './components/warnInfo.vue'
import znvTime from '../../../../../utils/znvTime'
import Axios from 'axios'
export default {
  components: { basicInfo, emergencyEvent, warnInfo },
  name: 'addDisasterReportDialog',
  props: ['dialogVisible', 'formInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.fileList = []
      let obj = {}
      this.form = Object.assign(this.form, JSON.parse(JSON.stringify(this.formInfo)))
      console.log('this.form', this.form)
      if (this.formInfo.disasterDataBeans) {
        // console.log('fileData', this.formInfo.disasterDataBeans)
        for (let item of this.formInfo.disasterDataBeans) {
          // console.log('文档 item: ', item)
          obj = {
            url: item.fileUrl,
            raw: new File([item], item.fileName),
            name: item.fileName
          }
          this.fileList.push(obj)
        }
      }
    }
    // formInfo(v) {

    //   if (v) {
    //     this.form = Object.assign(this.form, JSON.parse(JSON.stringify(v)))
    //     console.log('this.form', this.form)
    //   }
    // }
  },
  mounted() {
    this.$root.eventBus.$on('isCode', (data) => {
      this.isCode = data
    })
    // this.$root.eventBus.$on('choosedKeys-ids', (data) => {
    //   this.form.submitItem = data
    //   console.log('this.form.submitItem', this.form.submitItem)
    // })
  },
  data() {
    return {
      activeName: '1',
      loading: false,
      form: {
        disName: '',
        typeId: '',
        repPrecinctId: '',
        submitItem: [],
        repTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'),
        remark: '',
        eventBeans: [],
        warnBeans: [],
        disasterDataBeans: []
      },
      isCode: 2,
      newFormD: {}
    }
  },
  methods: {
    choosedKey(ids) {
      this.form.submitItem = ids
      // console.log('this.form.submitItem: ', this.form.submitItem)
    },
    handleClose(done) {
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
    reset() {
      this.loading = false
      this.form = {
        disName: '',
        typeId: '',
        repPersonName: '',
        submitItem: [],
        repTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD hh:mm:ss'),
        remark: '',
        eventBeans: [],
        warnBeans: [],
        disasterDataBeans: []
      }
      this.activeName = '1'
    },
    handleOk() {
      console.log(this.form, 'form.remark');
      this.$refs.basicInfo.$refs['form'].validate((valid) => {
        if (valid) {
          this.submitAll()
        } else {
          this.$message.warning('检查表单输入内容的合法性！')
          // console.log('error submit!!')
          return false
        }
      })
    },
    submitAll() {
      this.loading = true
      // this.newFormD = JSON.parse(JSON.stringify(this.form))
      this.newFormD.disName = this.form.disName
      this.newFormD.typeId = this.form.typeId
      this.newFormD.remark = this.form.remark
      this.newFormD.eventIds = []
      this.newFormD.warnIds = []
      if (this.form.eventBeans.length > 0) {
        for (let item of this.form.eventBeans) {
          this.newFormD.eventIds.push(item.eventId)
        }
      }
      if (this.form.warnBeans.length > 0) {
        for (let item of this.form.warnBeans) {
          this.newFormD.warnIds.push(item.id)
        }
      }
      this.newFormD.createId = localStorage.getItem('userId')
      if (Array.isArray(this.newFormD.typeId)) {
        this.newFormD.typeId = this.newFormD.typeId.join(',')
      }
      if (Array.isArray(this.form.submitItem)) {
        this.newFormD.repPrecinctId = this.form.submitItem.join(',')
      }
      if (this.form.repPersonName) {
        this.newFormD.repPersonName = this.form.repPersonName
      }
      this.newFormD.disasterDataBeans = []
      let aboutData = {}
      for (let item of this.fileList) {
        // console.log('item', item)
        aboutData = {
          disId: this.formInfo.id,
          fileName: item.name.substring(0, item.name.lastIndexOf('.')),
          fileUrl: item.url,
          createTime: znvTime.getCurrentTimeByFormat('YYYY-MM-DD HH:mm:ss')
        }
        this.newFormD.disasterDataBeans.push(aboutData)
      }
      let url = ''
      if (this.formInfo && this.formInfo.id) {
        url = '/gtw/EIMS-management/edit-disaster'
        this.newFormD.id = this.formInfo.id
        // console.log('this.newFormD', this.newFormD, url)
      } else {
        url = '/gtw/EIMS-management/add-disaster'
        // console.log('this.newFormD', this.newFormD, url)
      }
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
    // async handleChange(file, fileList) {
    handleChange(file, fileList) {
      const types = file.name.split('.')[file.name.split('.').length - 1]
      const fileType = ['docx', 'doc', 'pdf', 'PDF', 'xlsx', 'jpg', 'PNG'].some((item) => item === types)
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
            // await this.upSubmit(url, formData)
          }
        }
      }
    },
    // async upSubmit(url, formData) {
    //   Axios.post(url, formData).then((res) => {
    //     this.loading = false
    //     if (res.data.code === 200) {
    //       this.fileList.push({
    //         url: res.data.data,
    //         name: file.name
    //       })
    //       console.log('this.fileList', this.fileList)
    //       this.$message.success('上传成功！')
    //     } else {
    //       this.fileList.pop()
    //       console.log('this.fileList', this.fileList)
    //       this.$message.error('上传失败！')
    //     }
    //   })
    // },
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
<style lang="scss">
.addDisasterReportDialog {
  .el-dialog__body {
    height: 835px;
    // overflow: auto;
  }
  .info_body {
    // .el-collapse-item:is-active {
    //   height: 450px;
    // }
    .information {
      width: 100%;
      height: 48px;
      line-height: 48px;
      font-size: 18px;
      color: rgb(114, 118, 123);
      font-weight: 500;
      border-bottom: 1px solid #ebeef5;
      cursor: pointer;
    }
    .informationOne {
      border-top: 1px solid #ebeef5;
    }
    .inform_base {
      width: 100%;
      padding: 10px;
      border-bottom: 1px solid #ebeef5;
    }
    .el-collapse-item__arrow {
      display: none;
    }
    .el-collapse-item__header {
      font-size: 18px;
      color: rgb(114, 118, 123);
      font-weight: 500;
    }
  }
  .el-upload__tip {
    font-size: 14px;
    color: #5daf34;
    margin-top: 0px;
  }
}
</style>
