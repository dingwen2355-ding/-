<template>
  <van-popup v-model="popupVisible" position="bottom" @close="closePopup">
    <div class="van-picker__toolbar">
      <button type="button" class="van-picker__cancel" @click="closePopup">取消</button>
      <button type="button" class="van-picker__confirm" @click="saveRecorder">确认</button>
    </div>
    <div class="recorder-container">
      <div class="recorder-center-btn" v-if="isRecording" @click="handleRecorderStop">
        <van-icon name="stop-circle-o" class="center-btn-icon" />
        <div class="center-btn-text">点击停止录音</div>
      </div>
      <div class="recorder-center-btn" v-else @click="handleRecorderStart">
        <van-icon name="play-circle-o" class="center-btn-icon" />
        <div class="center-btn-text">点击开始录音</div>
      </div>
    </div>
  </van-popup>
</template>

<script>
import Recorder from 'js-audio-recorder'

export default {
  name: 'RecorderCollectPopup',
  props: {
    visible: Boolean
  },
  data() {
    return {
      popupVisible: this.visible,
      isRecording: false,
      recorderObj: null,
      recorderFile: null
    }
  },
  watch: {
    visible(visible) {
      this.popupVisible = visible
    }
  },
  methods: {
    closePopup() {
      this.isRecording = false
      this.recorderObj = null
      this.recorderFile = null
      this.$emit('update:visible', false)
      this.$emit('close')
    },
    handleRecorderStart() {
      Recorder.getPermission().then(
        () => {
          this.recorderObj = new Recorder()
          this.recorderObj.start().then(
            () => {
              this.isRecording = true
            },
            (error) => {
              // 出错了
              console.error(error)
              this.$notify({
                type: 'danger',
                message: '录音开启失败'
              })
            }
          )
        },
        (error) => {
          console.error(error)
          this.$notify({
            type: 'warning',
            message: '录音功能依赖录音权限，请给与录音权限后再尝试'
          })
        }
      )
    },
    handleRecorderStop() {
      if (this.recorderObj) {
        const wavBlob = this.recorderObj.getWAVBlob()
        this.recorderFile = new File([wavBlob], 'recording.wav', { type: 'audio/wav' })
      }
      this.isRecording = false
    },
    saveRecorder() {
      if (this.recorderFile) {
        this.$emit('getRecorderFile', this.recorderFile)
        this.recorderObj.destroy().then(() => {
          this.recorderObj = null
          this.recorderFile = null
        })
        this.closePopup()
      } else {
        this.$notify({
          type: 'warning',
          message: '请先进行录音'
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.recorder-container {
  width: 100%;
  height: 300rem;
  position: relative;
  .recorder-center-btn {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #427de5;
    text-align: center;
    cursor: pointer;
  }
  .center-btn-icon {
    font-size: 60rem;
    line-height: 60rem;
  }
  .center-btn-text {
    margin-top: 10rem;
    font-size: 28rem;
    line-height: 40rem;
  }
}
</style>
