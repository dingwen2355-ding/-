<template>
  <div class="event-report-page">
    <div class="page-title">事件上报</div>
    <van-form class="report-form" @submit="reportForm">
      <van-field
        v-model="form.eventTitle"
        rows="1"
        autosize
        name="事件名称"
        label="事件名称"
        type="textarea"
        :rules="rules.eventTitle"
        placeholder="请填写事件名称"
      />
      <van-field
        readonly
        clickable
        name="picker"
        :value="form.typeName"
        label="事件类别"
        :rules="rules.typeId"
        placeholder="请选择事件类别"
        @click="typePickerVisible = true"
      />
      <van-popup v-model="typePickerVisible" position="bottom">
        <van-picker
          show-toolbar
          :columns="eventTypes"
          value-key="dictName"
          item-height="60px"
          @cancel="typePickerVisible = false"
          @confirm="handleEventTypeSelect"
        />
      </van-popup>
      <van-field
        readonly
        clickable
        name="picker"
        :value="form.eventTime"
        label="事发时间"
        :rules="rules.eventTime"
        placeholder="请选择事发时间"
        @click="timePickerVisible = true"
      />
      <van-popup v-model="timePickerVisible" position="bottom">
        <van-datetime-picker
          v-model="defaultEventTime"
          type="datetime"
          item-height="60px"
          @cancel="timePickerVisible = false"
          @confirm="handleEventTimeSelect"
        />
      </van-popup>
      <van-field
        v-model="form.summary"
        rows="3"
        autosize
        label="事件描述"
        type="textarea"
        :rules="rules.summary"
        placeholder="请填写事件描述"
      />
      <van-field
        v-model="form.occurAddress"
        rows="1"
        autosize
        label="事发地点"
        type="textarea"
        :rules="rules.occurAddress"
        placeholder="请填写事发地点"
      />
      <van-field
        readonly
        clickable
        name="picker"
        :value="form.gps"
        label="经纬度"
        :rules="rules.gps"
        placeholder="请选择经纬度"
        @click="locationPopupVisible = true"
      />
      <van-field label="附件" class="upload-field">
        <template #input>
          <!-- 图片上传 -->
          <van-uploader
            v-model="pictureUpload"
            :after-read="handleImageUpload"
            accept="image/*"
            max-count="1"
            class="upload-item"
            @delete="form.filePicture = ''"
          >
            <div class="upload-inner">
              <van-icon name="photo-o" class="upload-icon" />
            </div>
          </van-uploader>
          <!-- 视频上传 -->
          <van-uploader
            v-model="videoUpload"
            :after-read="handleVideoUpload"
            accept="video/*"
            max-count="1"
            class="upload-item"
            @delete="form.fileVideo = ''"
          >
            <div class="upload-inner">
              <i class="upload-icon iconfont icon-shangchuanshipin"></i>
            </div>
          </van-uploader>
          <!-- 录音 -->
          <van-uploader
            ref="recorderUpload"
            v-model="audioUpload"
            :after-read="handleAudioUpload"
            max-count="1"
            disabled
            class="upload-item audio-uploader"
            @click.native="recorderPopupVisible = true"
            @delete="form.fileVoice = ''"
          >
            <div class="upload-inner">
              <i class="upload-icon iconfont icon-yuyinwenjian"></i>
            </div>
          </van-uploader>
        </template>
      </van-field>
      <div class="bottom-confirm-container">
        <van-button round type="warning" @click="cancelReport">取消</van-button>
        <van-button round type="info" native-type="submit">提交</van-button>
      </div>
    </van-form>
    <recorder-collect-popup
      :visible.sync="recorderPopupVisible"
      @getRecorderFile="saveRecorder"
    ></recorder-collect-popup>
    <choose-location-popup
      :visible.sync="locationPopupVisible"
      :location="form.gps.split(',')"
      @change="saveLocation"
    ></choose-location-popup>
  </div>
</template>

<script>
import moment from 'moment'
import RecorderCollectPopup from '@/components/RecorderCollectPopup'
import ChooseLocationPopup from '@/components/ChooseLocationPopup'
import axios from '@/utils/request'
export default {
  name: 'eventReport',
  components: { RecorderCollectPopup, ChooseLocationPopup },
  data() {
    return {
      pictureUpload: [],
      videoUpload: [],
      audioUpload: [],
      form: {
        eventTitle: '',
        typeId: '',
        typeName: '',
        eventTime: '',
        summary: '',
        occurAddress: '',
        gps: '',
        filePicture: '',
        fileVideo: '',
        fileVoice: ''
      },
      rules: {
        eventTitle: [{ required: true, message: '请填写事件名称' }],
        typeId: [{ required: true, message: '请选择事件类别' }],
        eventTime: [{ required: true, message: '请选择事发时间' }],
        summary: [{ required: true, message: '请填写事件描述' }],
        occurAddress: [{ required: true, message: '请填写事件名称' }],
        gps: [
          { required: true, message: '请填写经纬度' },
          { pattern: /^-?\d+(\.\d+)?,-?\d+(\.\d+)?$/, message: '请输入正确格式的经纬度' }
        ]
      },
      typePickerVisible: false,
      eventTypes: [],
      defaultEventTime: new Date(),
      timePickerVisible: false,
      recorderPopupVisible: false,
      locationPopupVisible: false
    }
  },
  mounted() {
    this.getTypes()
  },
  methods: {
    handleImageUpload(image) {
      this.handleFileRead('filePicture', image)
    },
    handleVideoUpload(video) {
      this.handleFileRead('fileVideo', video)
    },
    handleAudioUpload(video) {
      this.handleFileRead('fileVoice', video)
    },
    saveRecorder(file) {
      this.$refs.recorderUpload.readFile(file)
    },
    saveLocation(location) {
      this.form.gps = location.join(',')
    },
    handleFileRead(attr, file) {
      file.status = 'uploading'
      file.message = '上传中...'
      const formData = new FormData()
      formData.append('file', file.file)
      formData.append('group', 'system')
      axios
        .post('/binhuapis/file/upload', formData)
        .then((res) => {
          if (res.data.code === 200) {
            this.form[attr] = res.data.data
            file.status = 'done'
            file.message = ''
          } else {
            file.status = 'failed'
            file.message = '上传失败'
          }
        })
        .catch((err) => {
          console.error(err)
          file.status = 'failed'
          file.message = '上传失败'
        })
    },
    cancelReport() {
      this.$router.go(-1)
    },
    reportForm() {
      this.$dialog
        .confirm({
          title: '事件上报',
          message: '您确定要上报填写的事件吗？'
        })
        .then(() => {
          const lonLat = this.form.gps.split(',')
          const reportForm = {
            eventTitle: this.form.eventTitle,
            typeId: this.form.typeId,
            eventTime: this.form.eventTime,
            summary: this.form.summary,
            occurAddress: this.form.occurAddress,
            lon: lonLat[0],
            lat: lonLat[1],
            status: 40,
            filePicture: this.form.filePicture,
            fileVideo: this.form.fileVideo,
            fileVoice: this.form.fileVoice
          }
          this.$toast({
            type: 'loading',
            message: '上报中...',
            forbidClick: true,
            duration: 0
          })
          axios
            .post('/binhuapis/dapeng/event/add', reportForm)
            .then((res) => {
              this.$toast.clear()
              if (res.data.code === 200) {
                this.$notify({
                  type: 'success',
                  message: '上报成功'
                })
                this.$router.go(-1)
              } else {
                this.$notify({
                  type: 'danger',
                  message: res.data.message
                })
              }
            })
            .catch((err) => {
              console.error(err)
              this.$toast.clear()
              this.$notify({
                type: 'danger',
                message: '上报失败'
              })
            })
        })
    },
    getTypes() {
      axios.get('/binhuapis/dict/dictTree?parentId=1').then((res) => {
        this.eventTypes = res.data.data || []
      })
    },
    handleEventTypeSelect(eventType) {
      this.form.typeId = eventType.id
      this.form.typeName = eventType.dictName
      this.typePickerVisible = false
    },
    handleEventTimeSelect(eventTime) {
      this.form.eventTime = moment(eventTime).format('YYYY-MM-DD HH:mm:ss')
      this.timePickerVisible = false
    }
  }
}
</script>

<style lang="scss" scoped>
.event-report-page {
  padding: 40rem 28rem 0 28rem;
}
.page-title {
  color: #010101;
  font-size: 33rem;
  font-weight: 600;
  line-height: 50rem;
  text-align: center;
}
.report-form {
  margin-top: 40rem;
  /deep/ .van-field {
    & + .van-field {
      margin-top: 20rem;
    }
  }
}
.upload-field {
  display: flex;
  justify-content: space-between;
  .upload-item {
    flex: 1;
    + .upload-item {
      margin-left: 30rem;
    }
  }
  .audio-uploader /deep/ {
    .van-uploader__wrapper--disabled {
      opacity: 1;
    }
    .van-uploader__input {
      display: none;
    }
  }
  /deep/ .van-uploader__input-wrapper {
    width: 100%;
  }
  .upload-inner,
  /deep/ .van-uploader__preview {
    width: 100%;
    margin: 0;
    background: #f7f8fa;
    border-radius: 3rem;
    position: relative;
    &::before {
      content: '';
      display: block;
      padding-top: 100%;
    }
  }
  /deep/ .van-uploader__preview-image {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  /deep/ .van-uploader__file {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    .van-icon {
      font-size: 50rem;
    }
    .van-uploader__file-name {
      margin-top: 10rem;
      padding: 0 10rem;
      font-size: 20rem;
      line-height: 30rem;
    }
  }
  .upload-icon {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #dcdee0;
    // 覆盖van-icon设置的默认size
    font-size: 50rem !important;
  }
  /deep/ .van-uploader__preview-delete {
    width: 30rem;
    height: 30rem;
    border-radius: 0 0 0 30rem;
  }
}
.bottom-confirm-container {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 33rem 30rem 55rem 30rem;
  background: #fff;
  display: flex;
  justify-content: space-between;
  .van-button {
    flex: 1;
    + .van-button {
      margin-left: 40rem;
    }
  }
}
</style>
