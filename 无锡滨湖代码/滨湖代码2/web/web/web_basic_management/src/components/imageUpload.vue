<template>
  <!-- 图片附件上传组件 -->
  <div class="imageUpload">
    <el-upload
      action
      :http-request="submit"
      ref="upload"
      list-type="picture-card"
      :on-remove="handleRemove"
      :on-change="beforeAvatarUpload"
      :file-list="planForm.imgList"
      :on-success="finish"
      :auto-upload="true"
      :disabled="imageDisabled"
    >
      <i slot="default" class="el-icon-plus"></i>
      <div slot="file" slot-scope="{ file }">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt />
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <i class="el-icon-zoom-in"></i>
          </span>
          <span class="el-upload-list__item-delete" @click="handleDownload(file)">
            <i class="el-icon-download"></i>
          </span>
          <span class="el-upload-list__item-delete" @click="handleRemove(file)">
            <i class="el-icon-delete"></i>
          </span>
        </span>
      </div>
      <div
        style="margin-left: 0px"
        slot="tip"
        class="el-upload__tip"
        v-if="title !== '事件详情'"
      >只能上传jpg/png文件</div>
    </el-upload>

    <div v-if="dialogImageVisible">
      <el-dialog
        :visible.sync="dialogImageVisible"
        :append-to-body="false"
        :modal-append-to-body="false"
        height="500"
      >
        <img width="100%" :src="dialogImageUrl" alt />
      </el-dialog>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  props: ['imgUrl', 'title'],
  data() {
    return {
      dialogImageVisible: false,
      dialogImageUrl: '',
      planForm: {
        imgType: '1',
        imgList: []
      },
      imgUrList: '',
      imageDisabled: false
    }
  },
  watch: {
    title(v) {
      if (v === '事件详情') {
        console.log(v)
        this.imageDisabled = true
      }
    },
    imgUrl(v) {
      this.imgUrList = v
      // this.planForm.imgList = []
      // console.log(v)
      // let data = v.split(',')

      // let obj = {
      //   name: '',
      //   url: ''
      // }
      // console.log(obj);
      // this.planForm.imgList.push(obj)
    }
  },
  mounted() {
    if (this.title) {
      if (this.title === '事件详情') {
        this.imageDisabled = true
      }
    }
    if (this.imgUrl) {
      let data = this.imgUrl.split(',')
      let obj = {
        name: '',
        url: ''
      }
      data.some((e) => {
        obj.url = e
        this.planForm.imgList.push(obj)
      })
      console.log(this.planForm.imgList)
    } else {
      this.planForm.imgList = []
    }
  },
  methods: {
    beforeAvatarUpload(file, fileList) {
      this.changeImg = '1'
      const isJPG = file.raw.type === 'image/jpeg'
      const isPNG = file.raw.type === 'image/png'
      if (!isJPG && !isPNG) {
        this.$notify.error({
          title: '错误',
          message: '只能上传JPG/PNG格式!',
          duration: 1000
        })
        this.planForm.imgList = []
        return false
      } else {
        return true
      }
    },
    uploadExceed(file, fileList) {
      this.$notify.error({
        title: '错误',
        message: '只能上传一张图片!',
        duration: 1000
      })
    },
    handleRemove(file, fileList) {
      this.planForm.imgList = []
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
    submit(r) {
      console.log(this.planForm.imgList)
      let file = this.$refs.upload.uploadFiles[0].raw
      let data = new FormData()
      data.append('uploadFile', file)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      let url = '/community/superviseCompany/uploadFile'
      axios
        .post(url, data, config)
        .then((r) => {
          if (r.status === 200) {
            this.$emit('success', r.data)
            this.$notify({
              title: '成功',
              message: '提交成功',
              type: 'success',
              duration: 1000
            })
          }
        })
        .catch(function (error) {
          this.editing = ''
          console.log(error)
        })
    },
    finish() { },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogImageVisible = true
      console.log(file)
    }
  }
}
</script>

<style>
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
</style>
