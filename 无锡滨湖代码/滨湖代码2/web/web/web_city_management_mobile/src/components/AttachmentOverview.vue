<template>
  <div class="attachment-overview">
    <div class="attachment-list">
      <div v-for="item in attachmentList" :key="item.id" class="attachment-item">
        <van-image
          v-if="item.type === 'image'"
          :src="item.url"
          fit="cover"
          alt="资讯图片"
          class="attachment-img"
          @click.native="showBigImage(item.url)"
        ></van-image>
        <div class="attachment-player" v-else-if="item.type === 'video'" @click="showVideo(item)">
          <van-icon name="play-circle" class="player-icon" />
        </div>
        <div class="attachment-player" v-else-if="item.type === 'audio'" @click="playAudio(item)">
          <van-icon name="stop-circle" class="player-icon" v-if="isAudioPlaying" />
          <van-icon name="volume" class="player-icon" v-else />
        </div>
        <div class="attachment-download" v-else @click="downloadAttachment(item)">
          <div class="download-inner">
            <van-icon name="description" class="attachment-icon" />
            <div class="attachment-file">{{ item.name }}</div>
          </div>
        </div>
        <div class="delete-btn" v-if="canDelete" @click.stop="deleteAttachment(item)">
          <i class="iconfont icon-close delete-icon"></i>
        </div>
      </div>
    </div>
    <van-overlay :show="playVideoVisible" @click="playVideoVisible = false">
      <div class="play-wrapper" @click.stop>
        <vue-core-video-player :src="playUrl" autoplay v-if="playVideoVisible"></vue-core-video-player>
      </div>
      <div class="player-close-btn" @click="playVideoVisible = false">
        <van-icon name="clear" class="close-icon" />
      </div>
    </van-overlay>
  </div>
</template>

<script>
import { ImagePreview } from 'vant'
import { Howl } from 'howler'

const imageFormat = ['jpg', 'png', 'jpeg', 'gif', 'bmp', 'JPG', 'PNG', 'JPEG', 'GIF', 'BMP']
const videoFormat = ['mp4', 'webm', 'MP4', 'WEBM']
const audioFormat = ['mp3', 'wav', 'ogg', 'aac', 'MP3', 'WAV', 'OGG', 'AAC']

export default {
  name: 'AttachmentOverview',
  props: {
    canDelete: Boolean,
    // 不区分列表内组件方法进行区分，内容是各个文件的url
    fileList: Array,
    // 区分文件类型的列表，如果传了fileList就不传下面的了，同理相反也一样
    imageList: Array,
    videoList: Array,
    audioList: Array,
    otherList: Array
  },
  data() {
    return {
      attachmentList: [],
      playVideoVisible: false,
      playUrl: '',
      audioPlayObj: null,
      isAudioPlaying: false
    }
  },
  watch: {
    fileList(list) {
      this.handleFileAnalysis(list)
    },
    imageList(list) {
      this.handleTypeFileAnalysis('image', list)
    },
    videoList(list) {
      this.handleTypeFileAnalysis('video', list)
    },
    audioList(list) {
      this.handleTypeFileAnalysis('audio', list)
    },
    otherList(list) {
      this.handleTypeFileAnalysis('file', list)
    }
  },
  mounted() {
    if (this.fileList) {
      this.handleFileAnalysis(this.fileList)
    } else {
      if (this.imageList) {
        this.handleTypeFileAnalysis('image', this.imageList)
      }
      if (this.videoList) {
        this.handleTypeFileAnalysis('video', this.videoList)
      }
      if (this.audioList) {
        this.handleTypeFileAnalysis('audio', this.audioList)
      }
      if (this.otherList) {
        this.handleTypeFileAnalysis('file', this.otherList)
      }
    }
  },
  methods: {
    handleFileAnalysis(list) {
      if (list && list.length > 0) {
        this.attachmentList = list.map((fileUrl) => {
          const format = fileUrl.slice(fileUrl.lastIndexOf('.') + 1)
          if (imageFormat.includes(format)) {
            return {
              id: Math.round(Math.random() * 1e10),
              type: 'image',
              url: fileUrl
            }
          } else if (videoFormat.includes(format)) {
            return {
              id: Math.round(Math.random() * 1e10),
              type: 'video',
              url: fileUrl
            }
          } else if (audioFormat.includes(format)) {
            return {
              id: Math.round(Math.random() * 1e10),
              type: 'audio',
              url: fileUrl
            }
          } else {
            const fileName = fileUrl.slice(fileUrl.lastIndexOf('/') + 1)
            return {
              id: Math.round(Math.random() * 1e10),
              type: 'file',
              url: fileUrl,
              name: fileName.slice(fileName.indexOf('-') + 1)
            }
          }
        })
      } else {
        this.attachmentList = []
      }
    },
    handleTypeFileAnalysis(type, list) {
      this.attachmentList = this.attachmentList.filter((item) => item.type !== type)
      if (list && list.length > 0) {
        list.forEach((url) => {
          const fileName = url.slice(url.lastIndexOf('/') + 1)
          this.attachmentList.push({
            type: type,
            url,
            name: fileName.slice(fileName.indexOf('-') + 1)
          })
        })
      }
    },
    showBigImage(url) {
      ImagePreview([url])
    },
    showVideo(item) {
      this.playUrl = item.url
      this.playVideoVisible = true
    },
    playAudio(item) {
      if (this.isAudioPlaying) {
        this.audioPlayObj.stop()
        this.isAudioPlaying = false
        this.audioPlayObj = null
      } else {
        this.audioPlayObj = new Howl({
          src: [item.url],
          autoplay: false,
          loop: false,
          onend: () => {
            this.isAudioPlaying = false
          }
        })
        this.audioPlayObj.play()
        this.isAudioPlaying = true
      }
    },
    downloadAttachment(file) {
      let url2 = file.url.replace(/\\/g, '/')
      file.name = file.raw ? file.raw.name : file.name
      let xhr = new XMLHttpRequest()
      xhr.open('GET', url2, true)
      xhr.responseType = 'blob'
      xhr.onload = () => {
        if (xhr.status === 200) {
          let urlObject = window.URL || window.webkitURL || window
          // eslint-disable-next-line camelcase
          let export_blob = new Blob([xhr.response])
          // eslint-disable-next-line camelcase
          let save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a')
          save_link.href = urlObject.createObjectURL(export_blob)
          save_link.download = file.name
          save_link.click()
        }
      }
      xhr.send()
    },
    deleteAttachment(attachment) {
      this.$emit('delete', attachment.url)
    }
  }
}
</script>

<style lang="scss" scoped>
.attachment-overview {
  width: 100%;
  margin-top: 20rem;
}
.attachment-list {
  display: flex;
  flex-wrap: wrap;
  .attachment-item {
    flex-basis: 30%;
    border-radius: 3rem;
    position: relative;
    &::before {
      content: '';
      display: block;
      padding-top: 100%;
    }
    + .attachment-item {
      margin-left: 3.3%;
    }
    &:nth-child(n + 4) {
      margin-top: 3.3%;
    }
    &:nth-child(3n - 2) {
      margin-left: 0;
    }
    .delete-btn {
      position: absolute;
      top: 0;
      right: 0;
      width: 40rem;
      height: 40rem;
      padding-left: 15rem;
      background: #000;
      border-radius: 0 0 0 100rem;
      .delete-icon {
        color: #fff;
        font-size: 20rem;
        line-height: 40rem;
      }
    }
  }
  .attachment-img,
  .attachment-player,
  .attachment-download {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    cursor: pointer;
    border: 1rem solid #ececec;
  }
  .attachment-player {
    background: #000;
    .player-icon {
      position: absolute;
      top: calc(50% - 30rem);
      left: calc(50% - 30rem);
      color: #fff;
      font-size: 60rem;
      line-height: 1;
    }
  }
  .download-inner {
    position: absolute;
    top: 50%;
    left: 50%;
    max-width: 100%;
    transform: translate(-50%, -50%);
    color: #333;
    text-align: center;
    .attachment-icon {
      color: #427de5;
      font-size: 60rem;
      line-height: 60rem;
    }
    .attachment-file {
      width: 95%;
      margin: 0 auto;
      margin-top: 20rem;
      font-size: 28rem;
      line-height: 30rem;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  }
}
.player-close-btn {
  position: absolute;
  top: 20rem;
  right: 20rem;
  .close-icon {
    color: #fff;
    font-size: 60rem;
    line-height: 1;
  }
}
.play-wrapper {
  position: absolute;
  top: 50%;
  left: 0;
  width: 100%;
  transform: translateY(-50%);
}
</style>
