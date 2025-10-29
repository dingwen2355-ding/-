<template>
  <!-- 附件上传组件 -->
  <div class="attachmentUploadNew">
    <el-upload
      ref="upload"
      action="#"
      list-type="picture-card"
      :file-list="fileList"
      :http-request="finish"
      :before-upload="beforeAvatarUpload"
      :disabled="attachFilesDisabled"
      :on-change="handleIntroduceUploadHide"
      :class="objClass"
    >
      <i class="el-icon-plus"></i>
      <div slot="file" slot-scope="{ file }" :title="file.name">
        <img class="el-upload-list__item-thumbnail" :src="file | picFilter" alt />
        <span class="el-upload-list__item-actions">
          <span
            class="el-upload-list__item-preview"
            @click="handlePictureCardPreview(file)"
            v-if="file.name.includes('.jpg') ||
          file.name.includes('.png') ||
          file.name.includes('.gif') ||
          file.name.includes('.bmp') ||
          file.name.includes('.jpeg') ||
          file.name.includes('.JPG') ||
          file.name.includes('.PNG') ||
          file.name.includes('.GIF') ||
          file.name.includes('.BMP') ||
          file.name.includes('.JPEG')"
          >
            <i class="el-icon-zoom-in"></i>
          </span>
          <span class="el-upload-list__item-delete" @click="handleDownload(file)">
            <i class="el-icon-download"></i>
          </span>
          <span
            class="el-upload-list__item-delete"
            @click="handleRemove(file)"
            v-if="!attachFilesDisabled"
          >
            <i class="el-icon-delete"></i>
          </span>
        </span>
      </div>
    </el-upload>

    <div v-if="dialogImageVisible">
      <el-dialog :visible.sync="dialogImageVisible" width="850px" :append-to-body="true">
        <div class="imgItem">
          <!-- <el-button
            style="height:45px"
            :disabled="fileList.length===1"
            icon="el-icon-arrow-left"
            circle
            @click="imgLeft"
          ></el-button>-->
          <img class="img img-object" :src="dialogImageUrl" alt />
          <!-- <el-button
            style="height:45px"
            :disabled="fileList.length===1"
            icon="el-icon-arrow-right"
            circle
            @click="imgRight"
          ></el-button>-->
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
// import axios from '@/utils/request'
export default {
  props: ['imgUrl', 'title', 'type'],
  data() {
    return {
      dialogImageVisible: false,
      fileList: [],
      attachFilesDisabled: false,
      dialogImageUrl: [],
      objClass: {
        upLoadShow: true,
        upLoadHide: false
      }
    }
  },
  filters: {
    picFilter(files) {
      console.log(files)
      if (files.name) {
        if (
          files.name.includes('.jpg') ||
          files.name.includes('.png') ||
          files.name.includes('.gif') ||
          files.name.includes('.bmp') ||
          files.name.includes('.jpeg') ||
          files.name.includes('.JPG') ||
          files.name.includes('.PNG') ||
          files.name.includes('.GIF') ||
          files.name.includes('.BMP') ||
          files.name.includes('.JPEG')
        ) {
          return files.url
        } else if (files.name.includes('.docx') || files.name.includes('.doc')) {
          return require('../assets/fileImg.png')
        } else if (files.name.includes('.pptx')) {
          return require('../assets/power.png')
        } else if (files.name.includes('.xlsx')) {
          return require('../assets/excel.png')
        } else if (files.name.includes('.pdf') || files.name.includes('.PDF')) {
          return require('../assets/pdf.png')
        } else {
          return require('../assets/other.png')
        }
      }
    }
  },
  watch: {
    title: {
      handler(v) {
        this.fileList = []
        console.log(this.objClass, v, 'this.objClass')
        if (v === '详情') {
          this.attachFilesDisabled = true
          this.objClass = {
            upLoadShow: false,
            upLoadHide: true
          }
        } else {
          this.attachFilesDisabled = false
          this.objClass = {
            upLoadShow: true,
            upLoadHide: false
          }
        }
      },
      immediate: true
    },
    imgUrl: {
      handler(v) {
        console.log(v)
        this.fileList = []
        if (v !== null && v !== undefined && v.length !== 0) {
          let data = v.split(',')
          data.some((e) => {
            e.split('/')
            let obj = {
              name: e.split('/')[e.split('/').length - 1],
              url: e
            }
            this.fileList.push(obj)
            this.objClass = {
              upLoadShow: false,
              upLoadHide: true
            }
          })
        }
      },
      immediate: true
    }
  },
  mounted() { },
  methods: {
    handleIntroduceUploadHide(file, fileList) {
      if (fileList.length >= 1) {
        this.objClass = {
          upLoadShow: false,
          upLoadHide: true
        }
      }
    },
    imgRight() {
      let inx = -1
      console.log(this.fileList, 'this.fileList')
      this.fileList.some((e, index) => {
        if (e.url === this.dialogImageUrl) {
          inx = index
          return true
        }
      })
      if (inx === this.fileList.length - 1 && this.fileList.length !== 1) {
        this.dialogImageUrl = this.fileList[0].url
      } else {
        this.dialogImageUrl = this.fileList[inx + 1].url
      }
    },
    imgLeft() {
      let inx = -1
      this.fileList.some((e, index) => {
        if (e.url === this.dialogImageUrl) {
          inx = index
          return true
        }
      })
      if (inx === 0 && this.fileList.length !== 1) {
        this.dialogImageUrl = this.fileList[this.fileList.length - 1].url
      } else {
        this.dialogImageUrl = this.fileList[inx - 1].url
      }
    },
    finish(v) {
      this.$emit('success', this.$refs.upload.uploadFiles)
    },
    handleRemove(file) {
      this.$confirm('是否删除该附件?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          let index = this.$refs.upload.uploadFiles.findIndex((e) => e.uid === file.uid)
          this.$refs.upload.uploadFiles.splice(index, 1)
          this.objClass = {
            upLoadShow: true,
            upLoadHide: false
          }
        })
        .catch(() => {
          this.$notify.info({
            title: '消息',
            message: '已取消删除',
            duration: 1000
          })
        })
    },
    handleDownload(file) {
      console.log(file)
      let url2 = file.url.replace(/\\/g, '/')
      file.name = file.raw ? file.raw.name : file.name
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
    handlePictureCardPreview(file) {
      console.log(this.fileList, '----')
      this.dialogImageUrl = file.url
      this.dialogImageVisible = true
    },
    beforeAvatarUpload(file) {
      console.log(file, 'file')
      if (this.type === '标准规范' || this.type === '历史事件') {
        // const isJPG = file.type === 'image/jpg'
        // const isPng = file.type === 'image/png'
        // const isJpeg = file.type === 'image/jpeg'
        const ispdf = file.type === 'application/pdf'
        const isdocx = file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
        const isdoc = file.type === 'application/msword'
        // 1MB=1024*1024(1MB=1024KB 1KB=1024MB)
        // const is20M = file.size / 1024 / 1024 < 20
        // 限制文件上传类型
        // if (!isJPG && !isPng && !isJpeg) {
        //   this.$notify.error({
        //     title: '错误',
        //     message: '上传图片只能是 png,jpg,jpeg 格式!',
        //     duration: 1000
        //   })
        //   return false
        // }
        if (!ispdf && !isdoc && !isdocx) {
          this.$notify.error({
            title: '错误',
            message: '上传文件只能是 pdf,docx,doc 格式!',
            duration: 1000
          })
          let index = this.$refs.upload.uploadFiles.findIndex((e) => e.uid === file.uid)
          this.$refs.upload.uploadFiles.splice(index, 1)
          this.objClass = {
            upLoadShow: true,
            upLoadHide: false
          }
          return false
        }
        // // 限制文件上传大小
        // if (!is20M) {
        //   this.$notify.error({
        //     title: '错误',
        //     message: '上传图片大小不能超过 10MB!',
        //     duration: 1000
        //   })
        //   return false
        // }
        // return true
      }

    }
  }
}
</script>

<style>
.upLoadHide .el-upload {
  display: none;
}
.el-upload--picture-card {
  width: 100px;
  height: 100px;
  line-height: 100px;
}
.el-upload-list--picture-card .el-upload-list__item {
  width: 100px;
  height: 100px;
  line-height: 100px;
}
/* .attachmentUpload {
  margin-left: 74px;
} */
.imgItem {
  display: flex;
  justify-content: center;
  align-items: center;
}
.img {
  width: 90%;
  height: 500px;
  margin: 0 12px;
  /* border: black 1px solid;
  background-color: darkgrey; */
}
/* 图片自适应的样式 */
.img-object {
  object-fit: contain;
  object-position: 50% 20%;
}
</style>
