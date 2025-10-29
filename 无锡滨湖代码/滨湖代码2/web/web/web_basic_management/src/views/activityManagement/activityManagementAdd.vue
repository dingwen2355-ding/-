<template>
  <div class="ConsultationNoticeAdd" v-if="dialogVisible">
    <el-dialog
      :title="isCode === 0 ? '修改资讯信息' : isCode === 1 ? '查看资讯详情' : '新增资讯信息'"
      :visible.sync="dialogVisible"
      width="1250px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form
          :inline="true"
          ref="formLabel"
          :rules="rules"
          :model="form"
          label-width="122px"
          :disabled="isCode == 1"
        >
          <el-form-item label="标题" prop="title" class="whole-line">
            <el-input clearable v-model="form.title" placeholder="请输入标题"></el-input>
          </el-form-item>
          <el-form-item label="发布人" prop="publisher">
            <el-input clearable v-model="form.publisher" placeholder="请输入发布人"></el-input>
          </el-form-item>
          <el-form-item label="发布时间" prop="releaseTime">
            <el-date-picker
              style="width: 100%"
              v-model="form.releaseTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="请选择发布时间"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="描述" prop="remark" class="content-line">
            <auto-editor
              :disabled="isCode == 1"
              v-model="form.remark"
              :style="isCollapse ? 'width: 1085px' : 'width: 940px'"
            ></auto-editor>
          </el-form-item>
          <el-form-item label="附件" prop="files">
            <!-- <AttachmentUploadNew @success="siteImg" :title="title" :imgUrl="form.annexUrl" /> -->
            <el-upload
              class="attachmentUpload"
              action="#"
              list-type="picture-card"
              :http-request="uploading"
              :file-list="files"
              accept=".jpg, .png"
              ref="upload"
              :limit="3"
              :before-upload="beforeAvatarUpload"
            >
              <i class="el-icon-plus"></i>
              <div slot="file" slot-scope="{ file }">
                <img
                  class="el-upload-list__item-thumbnail"
                  style="width: 100%; height: 50%; margin-top: 5px; object-fit: contain; object-position: 50% 20%"
                  :src="file.url"
                />
                <div style="margin-top: -10px">{{ file.name }}</div>
                <span class="el-upload-list__item-actions">
                  <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span class="el-upload-list__item-delete" @click="handleDownload(file)">
                    <i class="el-icon-download"></i>
                  </span>
                  <span v-if="isCode !== 1" class="el-upload-list__item-delete" @click="handleRemove(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>
          </el-form-item>
          <el-form-item label="是否置顶" prop="isTop" class="content-line">
            <el-select v-model="form.isTop" placeholder="请选择状态" clearable>
              <el-option v-for="item in statusList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="禁用轮播" class="content-line" v-if="form.isTop == 1">
            <el-select v-model="form.isTurn" placeholder="请选择状态" clearable v>
              <el-option v-for="item in turnList" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <!-- 附件查看 -->
      <el-dialog :visible.sync="fileDialogVisible" :append-to-body="true">
        <div class="imgItem">
          <img class="img img-object" :src="dialogImageUrl" alt />
        </div>
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk" v-if="isCode !== 1">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from '@/utils/request'
import AutoEditor from '../../components/AutoEditor/AutoEditor.vue'
export default {
  name: 'activityManagementAdd',
  components: { AutoEditor },
  props: ['dialogVisible', 'formInfo', 'isCode'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        this.title = this.isCode == 0 ? '修改' : this.isCode === 1 ? '详情' : '新增'
        // if (this.form.annexUrl) {
        //   this.fileList = this.form.annexUrl
        // }
        if (this.form.annexUrl === '') {
          this.files = []
        } else {
          let data = this.form.annexUrl.split(',')
          data.some((e) => {
            let fileName = e.split('/')
            let files = {
              name: fileName[fileName.length - 1],
              url: e
            }
            this.files.push(files)
          })
        }
      }
    }
  },
  data() {
    var validateFiles = (rule, value, callback) => {
      let files = this.$refs.upload.uploadFiles

      if (!files.length) {
        callback(new Error('文件不能为空'))
      }

      callback()
    }
    return {
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        publisher: [{ required: true, message: '请输入发布人', trigger: 'blur' }],
        releaseTime: [{ required: true, message: '请选择发布时间', trigger: 'blur' }],
        remark: [{ required: true, message: '请输入描述信息', trigger: 'blur' }],
        isTop: [{ required: true, message: '请选择', trigger: 'change' }],
        files: [{ required: true, validator: validateFiles }]
      },
      loading: false,
      form: {
        title: '',
        publisher: '',
        releaseTime: '',
        remark: '',
        isTop: '0',
        isTurn: '0'
        // files: []
      },
      isCollapse: false,
      // fileList: '',
      statusList: [
        {
          id: '0',
          name: '否'
        },
        {
          id: '1',
          name: '是'
        }
      ],
      turnList: [
        {
          id: '0',
          name: '否'
        },
        {
          id: '1',
          name: '是'
        }
      ],
      isChange: false,
      files: [],
      dialogImageUrl: '',
      fileDialogVisible: false,
      fileList: []
    }
  },
  mounted() {},
  methods: {
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
        this.reset()
        this.$emit('update:dialogVisible', false)
      }
    },
    reset() {
      this.loading = false
      this.form = {}
      this.files = []
    },
    handleOk() {
      this.$refs.formLabel.validate((valid) => {
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
      let newForm = {
        title: this.form.title,
        publisher: this.form.publisher,
        releaseTime: this.form.releaseTime,
        remark: this.form.remark,
        isTop: this.form.isTop,
        isTurn: this.form.isTurn,
        annexUrl: this.form.annexUrl
      }
      let url = ''
      let formData = new FormData()
      if (this.formInfo && this.formInfo.id) {
        newForm.id = this.formInfo.id
        url = '/binhuapis/informationManage/edit'
        if (this.files.length === 0 && this.isChange) {
          this.form.annexUrl = ''
        } else {
          for (var i = 0; i < this.files.length; i++) {
            // formData.append('files', this.files[i].raw)
          }
        }
      } else {
        url = '/binhuapis/informationManage/add'
        for (var i = 0; i < this.files.length; i++) {
          // formData.append('files', this.files[i].raw)
        }
      }

      formData.append('informationBean', JSON.stringify(newForm))

      // if (this.formInfo && this.formInfo.id) {
      // } else {
      // }
      Axios.post(url, formData).then((r) => {
        this.loading = false
        if (r.data.code === 200) {
          if (this.formInfo && this.formInfo.id) {
            this.$message.success('修改成功！')
          } else {
            this.$message.success('新增成功！')
          }
          this.$emit('success')
          this.reset()
          this.$emit('update:dialogVisible', false)
        } else {
          this.$message.error(r.data.message)
        }
      })
    },
    siteImg(r) {
      this.isChange = true
      this.files = r
    },
    // 附件
    uploading(e) {
      this.files = this.$refs.upload.uploadFiles
      console.log(this.files)
    },
    handleRemove(file) {
      this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let arr = this.$refs.upload.uploadFiles
          let index = arr.indexOf(file)
          // this.formInfo.main.attachFiles.splice(index, 1)
          arr.splice(index, 1)
          this.files.splice(index, 1)
          this.$message.success('删除成功！')
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file.url
      this.fileDialogVisible = true
    },
    beforeAvatarUpload(file) {
      console.log(file)
      // 1MB=1024*1024(1MB=1024KB 1KB=1024MB)
      const is20M = file.size / 1024 / 1024 < 20
      // 限制文件上传大小
      if (!is20M) {
        this.$notify.error({
          title: '错误',
          message: '上传文件大小不能超过 20MB!',
          duration: 1000
        })
        return false
      }
      return true
    },
    handleDownload(file) {
      console.log(file)
      let url2 = file.url.replace(/\\/g, '/')
      let xhr = new XMLHttpRequest()
      xhr.open('GET', url2, true)
      xhr.responseType = 'blob'
      xhr.onload = () => {
        if (xhr.status === 200) {
          this.saveAs(xhr.response, file.name)
        }
      }
      xhr.send()
    },
    saveAs(data, name) {
      let urlObject = window.URL || window.webkitURL || window
      // eslint-disable-next-line camelcase
      let export_blob = new Blob([data])
      // eslint-disable-next-line camelcase
      let save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a')
      save_link.href = urlObject.createObjectURL(export_blob)
      save_link.download = name
      save_link.click()
    }
  }
}
</script>

<style scoped lang="scss">
.ConsultationNoticeAdd {
  .info_body {
    // height: 685px;
  }
  .whole-line {
    width: 100%;
    .el-form-item__content {
      width: calc(100% - 191px);
    }
  }
  .content-line {
    width: 100%;

    .el-form-item__content {
      width: calc(100% - 191px);
    }
  }
  .fileList {
    margin-left: 50px;
    font-size: 14px;
    position: relative;
    height: 180px;
    width: 90%;
    overflow-x: hidden;
    overflow-y: scroll;
    .attachmentUpload {
      position: absolute;
      top: 0;
      left: 50px;
    }
  }
}
</style>
