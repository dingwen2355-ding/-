<template>
  <div class="MyTasksAdd">
    <el-dialog
      :title="'任务详情'"
      :visible.sync="dialogVisible"
      width="1122px"
      :modal-append-to-body="false"
      :append-to-body="false"
      :before-close="handleClose"
    >
      <div class="info_body" v-loading="loading">
        <el-form :inline="true" ref="formLabel" :rules="rules" :model="form" label-width="110px">
          <el-form-item label="任务名称" prop="taskTitle">
            <el-input :readonly="true" clearable v-model="form.taskTitle" placeholder="请输入任务名称"></el-input>
          </el-form-item>
          <el-form-item label="任务节点" prop="nodeName">
            <el-input :readonly="true" clearable v-model="form.nodeName" placeholder="请输入任务节点"></el-input>
          </el-form-item>
          <el-form-item label="下发时间" style="margin-bottom: 0" prop="eventTime">
            <el-date-picker
              :readonly="true"
              type="datetime"
              placeholder="选择下发时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              key="basicTime"
              v-model="form.issueTime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="事件名称" prop="eventTitle">
            <el-input :readonly="true" clearable v-model="form.eventTitle" placeholder="请输入事件名称"></el-input>
          </el-form-item>
          <el-form-item label="任务内容" prop="taskContent">
            <el-input :readonly="true" clearable v-model="form.taskContent" placeholder="请输入任务内容"></el-input>
          </el-form-item>
          <el-form-item label="反馈状态" prop="status">
            <!-- <template>
                <span v-if="form.status == '0'" style="color: red">未反馈</span>
                <span v-if="form.status == '1'" style="color: green">已反馈</span>
            </template>-->
            <!-- <template>
                <slot slot="prepend" class="input-slot">
                  <span v-if="form.status == '0'" style="color: red">未反馈</span>
                  <span v-if="form.status == '1'" style="color: green">已反馈</span>
                </slot>
            </template>-->
            <span class="stateBorder">
              <span v-if="form.status == '0'" style="color: red">未反馈</span>
              <span v-if="form.status == '1'" style="color: green">已反馈</span>
            </span>
          </el-form-item>
          <el-form-item label="反馈部门" prop="department">
            <el-input :readonly="true" clearable v-model="form.department" placeholder="请输入反馈部门"></el-input>
          </el-form-item>
          <el-form-item label="反馈姓名" prop="person">
            <el-input :readonly="true" clearable v-model="form.person" placeholder="请输入反馈姓名"></el-input>
          </el-form-item>
          <el-form-item label="反馈时间" style="margin-bottom: 0" prop="dealTime">
            <el-date-picker
              :readonly="true"
              type="datetime"
              placeholder="选择反馈时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              key="basicTime"
              v-model="form.dealTime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="反馈内容" prop="feedback" style="display: flex; margin-right: 130px">
            <el-input
              :readonly="true"
              clearable
              v-model="form.feedback"
              type="textarea"
              :rows="2"
              placeholder="请输入反馈内容"
            ></el-input>
          </el-form-item>
          <el-form-item label="处置附件">
            <FileSegment
              :videos="form.fileVideo"
              :images="form.filePicture"
              :doucs="form.fileVoice"
            />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from '@/utils/request'
import FileSegment from '@/components/Attachment/FileSegment'
export default {
  name: 'MyTasksAdd',
  props: ['dialogVisible', 'formInfo', 'isCode'],
  components: {
    FileSegment
  },
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      if (v) {
        this.addForm.video = ''
        this.videoShow = ''
        this.videoHidden = false
        this.form = Object.assign({}, JSON.parse(JSON.stringify(this.formInfo)))
        if (this.form.file) {
          this.files = this.form.file
          this.fileList = this.form.file.split(',').map((item) => {
            return {
              name: item.split('/').pop(),
              percentage: 0,
              raw: File,
              size: 6194,
              status: 'ready',
              uid: item.split('/').pop() + new Date().getTime(),
              url: item
            }
          })
        }
        if (this.isCode === 0 || this.isCode === 1) {
          this.videoHidden = true
          // this.form.fileVideo = this.formInfo.fileVideo
          if (this.formInfo.fileVideo) {
            this.form.fileVideo = this.formInfo.fileVideo.split(',')
          }
          // this.form.filePicture = this.formInfo.filePicture
          if (this.formInfo.filePicture) {
            this.form.filePicture = this.formInfo.filePicture.split(',')
          }
          // this.form.fileVoice = this.formInfo.fileVoice
          if (this.formInfo.fileVoice) {
            this.form.fileVoice = this.formInfo.fileVoice.split(',')
          }
          this.videoFileName = this.findName(this.formInfo.fileVideo)
          this.pictureFileName = this.findName(this.formInfo.filePicture)
          this.audioFileName = this.findName(this.formInfo.fileVoice)
          // this.videoShow = this.formInfo.fileVideo
          if (this.formInfo.fileVideo) {
            this.addForm.video = new window.File([this.formInfo.fileVideo], this.formInfo.fileVideo.split('/').pop(), {
              type: 'video/mp4'
            })
          }
          console.log(this.addForm.video, this.videoShow)
        }
      }
    }
  },
  data() {
    return {
      files: null,
      dialogVisible1: false,
      dialogImageUrl: '',
      fileList: [],
      rules: {},
      loading: false,
      kindList: [],
      typeList: [],
      sourceList: [],
      typeList1: [],
      streetList: [],
      communityList: [],
      videoShow: '',
      pictureShow: '',
      audioShow: '',
      addForm: {
        video: '',
        picture: '',
        audio: ''
      },
      form: {
        eventTitle: '',
        eventTime: '',
        summary: '',
        fileVideo: [],
        filePicture: [],
        fileVoice: []
      },
      gpsInfo: '',
      timeout: null,
      videoHidden: false,
      uploadProgress: 0,
      uploadPictureProgress: 0,
      uploadAudioProgress: 0,
      videoFileName: '',
      pictureFileName: '',
      audioFileName: ''
    }
  },
  computed: {
    sourceListSort() {
      if (this.isCode === 2) {
        return this.sourceList.filter((t) => t.dictName !== '突发事件')
      } else {
        return this.sourceList
      }
    }
  },
  filters: {
    picFilter(flie) {
      console.log(flie)
      if (
        flie.name.includes('.jpg') ||
        flie.name.includes('.png') ||
        flie.name.includes('.gif') ||
        flie.name.includes('.bmp') ||
        flie.name.includes('.jpeg') ||
        flie.name.includes('.JPG') ||
        flie.name.includes('.PNG') ||
        flie.name.includes('.GIF') ||
        flie.name.includes('.BMP') ||
        flie.name.includes('.JPEG')
      ) {
        flie.isShow = true
        return flie.url
      } else if (flie.name.includes('.docx')) {
        flie.isShow = false
        return require('../../assets/fileImg.png')
      } else if (flie.name.includes('.pptx')) {
        flie.isShow = false
        return require('../../assets/power.png')
      } else if (flie.name.includes('.xlsx')) {
        flie.isShow = false
        return require('../../assets/excel.png')
      } else if (flie.name.includes('.pdf') || flie.name.includes('.PDF')) {
        flie.isShow = false
        return require('../../assets/pdf.png')
      } else {
        flie.isShow = false
        return require('../../assets/other.png')
      }
    }
  },
  methods: {
    fileChange(e) {
      var files = []
      files = Array.from(e.target.files || e.dataTransfer.files)
      if (!files.length) return
      files.forEach((item) => {
        let name = item.name.toLowerCase()
        this.videoFileName = item.name
        // avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob
        if (!/\.(avr|mp4)$/.test(name)) {
          this.$message.warning('请上传视频')
          return
        }
        // 这里是file文件
        this.addForm.video = item
        const maxVideoSize = 1024 * 1024 * 50
        if (this.addForm.video.size > maxVideoSize) {
          this.$message.warning('视频不能大于50M')
          return
        }
        var reader = new FileReader()
        reader.readAsDataURL(item)
        this.videoHidden = false
        reader.onload = () => {
          // 这里是一段base64，用于视频回显用
          this.videoShow = reader.result
        }
        this.fileUpload(this.addForm.video)
      })
    },
    fileUpload(v) {
      // file/upload
      this.uploadProgress = 0
      let url = '/binhuapis/file/uploadMp4'
      this.$message.warning('视频正在上传中.......')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request({
        method: 'post',
        url,
        data: formData,
        onUploadProgress: (progressEvent) => {
          let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          this.uploadProgress = progress
          console.log('进度：', progress)
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.videoShuzu.push(res.data.data)
          this.$message.success('视频上传成功')
        }
      })
    },
    picChange(e) {
      var files = []
      files = Array.from(e.target.files || e.dataTransfer.files)
      if (!files.length) return
      files.forEach((item) => {
        let name = item.name.toLowerCase()
        this.pictureFileName = item.name
        // avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob
        if (!/\.(jpg|png)$/.test(name)) {
          this.$message.warning('请上传图片')
          return
        }
        // 这里是file文件
        this.addForm.picture = item
        const maxVideoSize = 1024 * 1024 * 10
        if (this.addForm.picture.size > maxVideoSize) {
          this.$message.warning('图片不能大于10M')
          return
        }
        var reader = new FileReader()
        reader.readAsDataURL(item)
        this.pictureHidden = false
        reader.onload = () => {
          // 这里是一段base64，用于视频回显用
          this.pictureShow = reader.result
        }
        this.picUpload(this.addForm.picture)
      })
    },
    picUpload(v) {
      this.uploadPictureProgress = 0
      let url = '/binhuapis/file/uploadImage'
      this.$message.warning('图片正在上传中.......')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request({
        method: 'post',
        url,
        data: formData,
        onUploadProgress: (progressEvent) => {
          let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          this.uploadPictureProgress = progress
          console.log('进度：', progress)
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.pictureShuzu.push(res.data.data)
          this.$message.success('图片上传成功')
        }
      })
    },
    audioChange(e) {
      var files = []
      files = Array.from(e.target.files || e.dataTransfer.files)
      if (!files.length) return
      files.forEach((item) => {
        let name = item.name.toLowerCase()
        this.audioFileName = item.name
        // avi|wmv|mpeg|mp4|mov|mkv|flv|f4v|m4v|rmvb|rm|3gp|dat|ts|mts|vob
        if (!/\.(mp3|wav)$/.test(name)) {
          this.$message.warning('请上传音频')
          return
        }
        // 这里是file文件
        this.addForm.audio = item
        const maxVideoSize = 1024 * 1024 * 50
        if (this.addForm.audio.size > maxVideoSize) {
          this.$message.warning('视频不能大于50M')
          return
        }
        var reader = new FileReader()
        reader.readAsDataURL(item)
        this.audioHidden = false
        reader.onload = () => {
          // 这里是一段base64，用于视频回显用
          this.audioShow = reader.result
        }
        this.audioUpload(this.addForm.audio)
      })
    },
    audioUpload(v) {
      this.uploadAudioProgress = 0
      let url = '/binhuapis/file/uploadMp3'
      this.$message.warning('音频正在上传中.......')
      let formData = new FormData()
      formData.append('file', v)
      formData.append('group', 'system')
      request({
        method: 'post',
        url,
        data: formData,
        onUploadProgress: (progressEvent) => {
          let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          this.uploadAudioProgress = progress
          console.log('进度：', progress)
        }
      }).then((res) => {
        if (res.data.code === 200) {
          this.audioShuzu.push(res.data.data)
          this.$message.success('音频上传成功')
        }
      })
    },
    findName(v) {
      let data = []
      if (v !== null) {
        if (v.indexOf(',') !== -1) {
          v.split(',').forEach((item) => {
            let da = item.split('/')
            let da2 = da[da.length - 1].split('-')
            da2.splice(0, 1)
            data.push(da2)
          })
        } else {
          let da = v.split('/')
          let da2 = da[da.length - 1].split('-')
          da2.splice(0, 1)
          data.push(da2)
          console.log(data)
        }
      }
      return data.toString()
    },
    handleClose() {
      this.fileList = []
      this.files = null
      // this.form = {}
      // this.form = {
      //   eventTitle: '',
      //   eventTime: '',
      //   summary: '',
      //   fileVideo: [],
      //   filePicture: [],
      //   fileVoice: []
      // }
      this.$emit('update:dialogVisible', false)
    },
    handleRemove(file) {
      this.$confirm('此操作将永久删除该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let fileUrl = ''
          if (this.fileList && this.fileList.length > 0) {
            console.log(this.fileList, 'this.fileListthis.fileList')
            this.fileList.forEach((item) => {
              if (item.name === file.name) {
                fileUrl = item.url
              } else {
                fileUrl = file.url
              }
            })
          } else {
            fileUrl = file.url
          }
          let url = '/binhuapis/file/delete?url=' + fileUrl
          request.get(url).then((res) => {
            this.files = null
            this.fileList = []
            this.$message.success('删除成功！')
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible1 = true
    },
    handleDownload(file) {
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
    },
    uploading(e) {
      let url = '/binhuapis/file/upload'
      let formData = new FormData()
      formData.append('file ', e.file)
      request
        .post(url, formData)
        .then((res) => {
          if (res.data.code === 200) {
            this.files = res.data.data
            let files = {
              name: e.file.name,
              url: res.data.data
            }
            this.fileList.push(files)
          }
        })
        .catch((e) => {
          this.$message.error(e.response.data.message)
        })
    },
    uploadExceed(file, fileList) {
      this.$message.error('只能上传一张图片!')
    }
  }
}
</script>
<style lang="scss">
.ExtraFilePreviewViewer {
  .previewActive {
    color: #3388ff;
  }
}
.MyTasksAdd {
  .stateBorder {
    display: inline-block;
    width: 200px;
    height: 32px;
    line-height: 32px;
    border-radius: 5px;
    border: 1px solid #dcdfe6;
    padding-left: 10px;
  }
  .el-input {
    line-height: 32px;
    width: 200px;
    box-sizing: border-box;
  }
  .el-input__icon {
    line-height: 32px;
  }
  .el-form--inline .el-form-item__content {
    flex-grow: 1;
    // height: 140px;
  }
  .upload_box {
    // overflow: scroll;
    box-sizing: border-box;
    width: 240px;
    height: 140px;
    margin-left: 50px;
    border: 1px solid rgb(220, 220, 220);
    position: relative;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    p {
      margin: 0;
      line-height: 25px;
      font-size: 14px;
    }
    #video_file {
      font-size: 14px;
      width: 100%;
      height: 100%;
      margin: 0 !important;
      opacity: 0;
      position: absolute;
      left: 0;
      top: 0;
      z-index: 1;
    }
  }
}
</style>
