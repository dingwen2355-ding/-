<template>
  <!-- 附件上传组件 -->
  <div class="attachmentUpload">
    <el-upload
      ref="upload"
      action="#"
      accept="image/png, image/gif, image/jpg, image/jpeg"
      list-type="picture-card"
      :file-list="fileList"
      :http-request="finish"
      :before-upload="beforeAvatarUpload"
      :disabled="attachFilesDisabled"
      :class="objClass"
    >
      <i class="el-icon-plus"></i>
      <div slot="file" slot-scope="{ file }">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt />
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <i class="el-icon-zoom-in"></i>
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
      <div
        style="margin-left: 0px; font-size:14px"
        slot="tip"
        class="el-upload__tip"
        v-if="title !== '事件详情'"
      >只能上传jpg/png文件,且不超过20MB</div>
    </el-upload>

    <div v-if="dialogImageVisible">
      <el-dialog :visible.sync="dialogImageVisible" width="1550px" :append-to-body="true">
        <div class="imgItem">
          <el-button
            style="height:45px"
            :disabled="fileList.length===1"
            icon="el-icon-arrow-left"
            circle
            @click="imgLeft"
          ></el-button>
          <img class="img img-object" :src="dialogImageUrl" alt />
          <el-button
            style="height:45px"
            :disabled="fileList.length===1"
            icon="el-icon-arrow-right"
            circle
            @click="imgRight"
          ></el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
// import axios from '@/utils/request'
export default {
  props: ['imgUrl', 'title'],
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
  watch: {
    title: {
      handler(v) {
        this.fileList = []
        if (v === '事件详情') {
          this.attachFilesDisabled = true
          this.objClass = {
            upLoadShow: false,
            upLoadHide: true
          }
        }
      },
      immediate: true
    },
    imgUrl: {
      handler(v) {
        this.fileList = []
        if (v !== null && v !== undefined && v.length !== 0) {
          let data = v.split(',')
          data.some((e) => {
            let obj = {
              name: '',
              url: e
            }
            this.fileList.push(obj)
          })
        }
      },
      immediate: true
    }
  },
  filters: {},
  mounted() { },
  methods: {
    imgRight() {
      let inx = -1
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
      if (this.title === '居民上报') {
        let index = this.$refs.upload.uploadFiles.findIndex((e) => e.uid === file.uid)
        this.$refs.upload.uploadFiles.splice(index, 1)
      } else {
        this.$confirm('是否删除该附件?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            let index = this.$refs.upload.uploadFiles.findIndex((e) => e.uid === file.uid)
            this.$refs.upload.uploadFiles.splice(index, 1)
          })
          .catch(() => {
            this.$notify.info({
              title: '消息',
              message: '已取消删除',
              duration: 1000
            })
          })
      }
    },
    handlePictureCardPreview(file) {
      console.log(this.fileList, '----')
      this.dialogImageUrl = file.url
      this.dialogImageVisible = true
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpg'
      const isPng = file.type === 'image/png'
      const isJpeg = file.type === 'image/jpeg'
      // 1MB=1024*1024(1MB=1024KB 1KB=1024MB)
      const is20M = file.size / 1024 / 1024 < 20
      // 限制文件上传类型
      if (!isJPG && !isPng && !isJpeg) {
        this.$notify.error({
          title: '错误',
          message: '上传图片只能是 png,jpg,jpeg 格式!',
          duration: 1000
        })
        return false
      }
      // 限制文件上传大小
      if (!is20M) {
        this.$notify.error({
          title: '错误',
          message: '上传图片大小不能超过 10MB!',
          duration: 1000
        })
        return false
      }
      return true
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
  height: 700px;
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
