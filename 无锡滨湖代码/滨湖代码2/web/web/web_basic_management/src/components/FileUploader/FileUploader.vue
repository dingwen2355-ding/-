<template>
  <div class="file-uploader">
    <div
      v-show="showUpload"
      class="file-uploader-dropzone"
      :class="{ 'file-uploader-dropzone--hover': isDragging }"
      @dragover.prevent="handleDragOver"
      @dragleave.prevent="handleDragLeave"
      @drop.prevent="handleDrop"
    >
      <div class="upload-content-box">
        <input
          ref="fileInput"
          @change="handleFileChange"
          type="file"
          style="font-size: 14px; margin-left: 20px"
          id="uploader-input"
          :accept="fileAccept"
          multiple
        />
        <div>
          <slot>
            <i class="iconfont icon-shangchuanshipin" style="font-size: 26px; color: #0052d9"></i>
            <p style="color: #0052d9">上传视频</p>
            <p style="color: #5b6472">上传的文件格式为mp4/avr</p>
          </slot>
        </div>
      </div>
    </div>
    <ul>
      <li v-for="(file, index) in fileList" :key="index + file.name">
        <div class="file-item" v-if="enablePreviewFile(file.type)">
          <div class="file-item-preview" v-if="file.type.indexOf('image/') === 0">
            <el-image
              style="width: 210px; height: 140px"
              :src="file.previewUrl"
              :preview-src-list="file.previewUrl.split(',')"
            ></el-image>
          </div>
          <div class="file-item-preview" v-else>
            <video
              v-if="file.previewUrl"
              ref="videoPlayer"
              class="video"
              style="width: 210px; height: 140px; display: block"
              controls
              :src="file.previewUrl"
            />
          </div>
          <div class="file-item-progress">
            <el-progress
              :percentage="progressMap[file.name]"
              :stroke-width="14"
              :text-inside="true"
              :format="formatProgress"
              status="success"
            ></el-progress>
          </div>
          <div class="video-upload-failed" v-show="!showProgress && progressMap[file.name] !== 100">
            <el-button
              type="danger"
              icon="el-icon-refresh-right"
              circle
              class="file-item-delete"
              @click="handleUpload(file)"
            ></el-button>
          </div>
          <div class="file-item-info">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              circle
              class="file-item-delete"
              @click="handleDelete(file)"
            ></el-button>
          </div>
        </div>
        <div class="file-item" v-else>
          <div class="file-item-preview">
            <i class="el-icon-folder"></i>
          </div>
          <div class="file-item-info">
            <div class="file-item-name">{{ file.name }}</div>
            <div class="file-item-size">{{ formatSize(file.size) }}</div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import axios from '@/utils/request'
import request from '@/utils/request'
export default {
  props: {
    accepts: {
      type: Array,
      default: () => {
        return ['audio/*', 'video/*', 'image/*']
      }
    },
    value: {
      type: Array,
      default: () => {
        return []
      }
    },
    max: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      fileList: [],
      uploadedFiles: [],
      isDragging: false,
      showProgress: false,
      uploadProgress: 0,
      progressMap: {}
    }
  },
  computed: {
    acceptRegExp() {
      const acceptTypes = Array.from(this.accepts)
      const acceptRegExp = new RegExp(`(${acceptTypes.join('|').replace('*', '.*')})`)
      return acceptRegExp
    },
    fileAccept() {
      return this.accepts.join(',')
    },
    showUpload() {
      return (
        this.uploadedFiles.length < this.max
        // &&
        // this.fileList.length < this.max &&
        // this.uploadedFiles.length + this.fileList.length < this.max
      )
    }
  },
  methods: {
    enablePreviewFile(type) {
      return type.indexOf('video/') === 0 || type.indexOf('audio/') === 0 || type.indexOf('image/') === 0
    },
    handleFileChange(event) {
      const files = event.target.files
      if (files.length > this.max) {
        alert(`最多只能上传${this.max}个文件`)
        this.$refs.fileInput.value = ''
        return
      }
      this.addFiles(files)
      // if (this.fileList.some((file) => file.type.indexOf('video/') === 0)) {
      this.handleUpload()
      // }
    },
    handleDragOver(event) {
      event.preventDefault()
      this.isDragging = true
    },
    handleDragLeave(event) {
      event.preventDefault()
      this.isDragging = false
    },
    handleDrop(event) {
      event.preventDefault()
      this.isDragging = false
      const files = event.dataTransfer.files
      if (files.length > this.max) {
        alert(`最多只能上传${this.max}个文件`)
        this.$refs.fileInput.value = ''
        return
      }
      this.addFiles(files)
      // if (this.fileList.some((file) => file.type.indexOf('video/') === 0)) {
      this.handleUpload()
      // }
    },
    addFiles(files) {
      for (let i = 0; i < files.length; i++) {
        const file = files[i]
        if (
          file.type.indexOf('video/') === 0 ||
          file.type.indexOf('audio/') === 0 ||
          file.type.indexOf('image/') === 0
        ) {
          file.previewUrl = URL.createObjectURL(file)
          this.fileList.push(file)
        } else {
          this.fileList.push({
            name: file.name,
            size: file.size,
            type: file.type
          })
        }
      }
    },
    async handleUpload() {
      const formData = new FormData()
      formData.append('group', 'system')
      this.$refs.fileInput.value = '' // 清空 value 值
      let url = '/binhuapis/file/upload'
      this.showProgress = true
      const filesNeedUpload = this.fileList.filter(
        (file) => !this.progressMap[file.name] || this.progressMap[file.name] < 100
      )
      try {
        const { uploadedCount, failedCount } = await filesNeedUpload.reduce(async (acc, file) => {
          const { formData, uploadedCount, failedCount } = await acc
          formData.set('file', file)
          try {
            const response = await request({
              method: 'post',
              url,
              data: formData,
              onUploadProgress: (progressEvent) => {
                let progress = ((progressEvent.loaded / progressEvent.total) * 100) | 0
                // console.log(`Uploading ${file.name}: ${progress}%`)
                this.$set(this.progressMap, file.name, progress)
              }
            })
            if (response.data.code === 200) {
              this.uploadedFiles.push(response.data.data)
              return {
                formData,
                uploadedCount: uploadedCount + 1,
                failedCount
              }
            } else {
              throw new Error(`上传文件失败: ${response.data.message}`)
            }
          } catch (error) {
            // console.error(`上传文件失败: ${error}`)
            return {
              formData,
              uploadedCount,
              failedCount: failedCount + 1
            }
          }
        }, Promise.resolve({ formData, uploadedCount: 0, failedCount: 0 }))

        if (failedCount === 0) {
          this.$message.success('所有文件上传成功')
        } else {
          this.$message.success(`${uploadedCount} 个文件上传成功`)
          this.$message.error(`${failedCount} 个文件上传失败`)
        }
        // this.fileList = this.fileList.filter(
        //   (file) => !this.progressMap[file.name] || this.progressMap[file.name] < 100
        // )
        // this.progressMap = {}
        this.showProgress = false
        this.$emit('input', this.uploadedFiles)
      } catch (error) {
        this.showProgress = false
        this.$message.error(`上传文件失败: ${error}`)
        this.$emit('input', [])
      }
    },
    handleDelete(file) {
      if (file.previewUrl) {
        URL.revokeObjectURL(file.previewUrl)
      }
      if (file.path) {
        axios
          .delete(`/api/upload/${file.id}`)
          .then((response) => {
            this.uploadedFiles = this.uploadedFiles.filter((video) => video.id !== file.id)
          })
          .catch((error) => {
            console.log(error)
          })
      } else {
        this.fileList = this.fileList.filter((item) => item !== file)
      }
    },
    formatSize(size) {
      if (size < 1024) {
        return size + ' B'
      } else if (size < 1024 * 1024) {
        return (size / 1024).toFixed(2) + ' KB'
      } else {
        return (size / 1024 / 1024).toFixed(2) + ' MB'
      }
    },
    formatProgress(percentage) {
      return `${percentage}%`
    }
  },
  beforeDestroy() {
    this.$refs.fileInput.value = ''
  }
}
</script>
<style lang="scss">
$primary-color: #409eff;
$secondary-color: #66b1ff;
$border-color: #ccc;
$background-color: #f5f5f5;
$text-color: #fff;

.file-uploader {
  display: flex;
  gap: 20px;
  align-items: center;

  .file-uploader-dropzone {
    display: flex;
    order: 1;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border: 2px dashed $border-color;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    .upload-content-box {
      box-sizing: border-box;
      width: 210px;
      height: 140px;
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
      #uploader-input {
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
    &:hover {
      background-color: $background-color;
    }

    &--hover {
      background-color: $background-color;
    }
  }

  ul {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;

    .file-item {
      position: relative;
      width: 210px;
      height: 140px;
      border: 1px solid $border-color;
      border-radius: 5px;
      overflow: hidden;
      cursor: pointer;

      .file-item-preview {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: $background-color;

        video,
        i {
          max-width: 100%;
          max-height: 100%;
        }
      }

      .file-item-info {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 30px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
        padding: 5px;
        background-color: rgba(0, 0, 0, 0.5);
        color: $text-color;
        font-size: 10px;
        opacity: 0;
        transition: opacity 0.3s ease;
      }
      .file-item-progress {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
      }
      .video-upload-failed {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(0, 0, 0, 0.5);
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
      }
      &:hover .file-item-info {
        opacity: 1;
      }

      .file-item-name {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .file-item-delete {
        border: none;
        color: $text-color;
        cursor: pointer;
        transition: opacity 0.3s ease;

        &:hover {
          opacity: 0.7;
        }
      }
    }
  }
}
</style>
