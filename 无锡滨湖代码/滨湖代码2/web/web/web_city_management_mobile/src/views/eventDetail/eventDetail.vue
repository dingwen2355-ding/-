<template>
  <div class="event-detail-page">
    <div class="event-title">{{ eventData.eventTitle }}</div>
    <div class="event-img" v-if="eventData.filePicture">
      <van-image :src="eventData.filePicture" fit="contain" alt="资讯图片" class="img" />
    </div>
    <div class="event-info">
      <div class="info-item">发生时间：{{ eventData.eventTime }}</div>
      <div class="info-item">事件类别：{{ eventData.typeName }}</div>
    </div>
    <section class="event-content">{{ eventData.summary }}</section>
    <attachment-overview
      v-if="eventData.filePicture || eventData.fileVideo || eventData.fileVoice"
      :imageList="eventData.filePicture ? [eventData.filePicture] : []"
      :videoList="eventData.fileVideo ? [eventData.fileVideo] : []"
      :audioList="eventData.fileVoice ? [eventData.fileVoice] : []"
    ></attachment-overview>
    <van-collapse v-model="activeStep" class="step-collapse">
      <!-- 核查中，核查反馈的处理 -->
      <van-collapse-item name="核查反馈填写" class="dispose-step-container" v-if="eventStatus === 135 && !!checkInfo">
        <template #title>
          <div class="step-title">核查反馈</div>
          <div class="step-info">
            <div class="step-icon issuing-step-icon">
              <i class="iconfont icon-chuzhixinxi_daihecha"></i>
            </div>
            <div class="step-summary-container">
              <div class="summary">{{ checkInfo.requirement }}</div>
              <div class="base-info">
                <span class="info">待核查</span>
                <span class="info">{{ checkInfo.disposeTime }}</span>
              </div>
            </div>
          </div>
        </template>
        <!-- 核查反馈的表单信息 -->
        <van-form class="step-content dispose-form" @submit="commitFeedback">
          <van-cell center title="是否属实" class="form-item">
            <template #right-icon>
              <van-switch
                v-model="feedbackForm.isTrue"
                active-value="是"
                inactive-value="否"
                active-color="#427DE5"
                inactive-color="#9a9a9a"
              />
            </template>
          </van-cell>
          <van-field
            v-model="feedbackForm.checkContent"
            class="form-item form-textarea"
            rows="8"
            autosize
            label=""
            type="textarea"
            :rules="rules.checkContent"
            placeholder="请填写核查内容"
          />
          <attachment-overview
            :fileList="feedbackForm.fileList"
            canDelete
            @delete="deleteFeedbackAttachment"
          ></attachment-overview>
          <div class="commit-container">
            <!-- 图片上传 -->
            <van-uploader
              :after-read="handleFeedbackUpload"
              accept="image/*"
              max-count="1"
              class="attachment-upload-btn"
            >
              <van-icon name="photo-o" />
            </van-uploader>
            <!-- 视频上传 -->
            <van-uploader
              :after-read="handleFeedbackUpload"
              accept="video/*"
              max-count="1"
              class="attachment-upload-btn"
            >
              <i class="iconfont icon-shangchuanshipin"></i>
            </van-uploader>
            <!-- 录音 -->
            <div class="attachment-upload-btn" @click="feedbackRecorderVisible = true">
              <i class="iconfont icon-yuyinwenjian"></i>
            </div>
            <!-- 文件上传 -->
            <van-uploader :after-read="handleFeedbackUpload" accept="*/*" max-count="1" class="attachment-upload-btn">
              <van-icon name="send-gift-o" />
            </van-uploader>
            <van-button native-type="submit" class="commit-btn">确定</van-button>
          </div>
        </van-form>
      </van-collapse-item>

      <!-- 待启动中，核查反馈信息的查看 -->
      <van-collapse-item name="核查反馈查看" class="dispose-step-container" v-else-if="checkInfo && feedbackInfo">
        <template #title>
          <div class="step-title">核查反馈</div>
          <div class="step-info">
            <div class="step-icon">
              <i class="iconfont icon-chuzhixinxi_yihecha"></i>
            </div>
            <div class="step-summary-container">
              <div class="summary">{{ checkInfo.requirement }}</div>
              <div class="base-info">
                <span class="info">已核查</span>
                <span class="info">{{ checkInfo.disposeTime }}</span>
              </div>
            </div>
          </div>
        </template>
        <!-- 核查反馈信息的查看 -->
        <div class="step-content dispose-info-container">
          <div class="info-item">
            <div class="info-label">是否属实</div>
            <div class="info-content">{{ feedbackInfo.isTrue }}</div>
          </div>
          <div class="info-item">
            <div class="info-label">核查内容</div>
            <div class="info-content">{{ feedbackInfo.checkContent }}</div>
          </div>
          <div class="info-item">
            <div class="info-label">核查人</div>
            <div class="info-content">{{ checkInfo.checkPersonName }}</div>
          </div>
          <div class="info-item">
            <div class="info-label">核查时间</div>
            <div class="info-content">{{ feedbackInfo.checkTime }}</div>
          </div>
          <div class="info-item line-content" v-if="feedbackInfo.fileUrls">
            <attachment-overview :fileList="feedbackInfo.fileUrls.split(',')"></attachment-overview>
          </div>
        </div>
      </van-collapse-item>

      <!-- 事件处理表单填写 -->
      <van-collapse-item name="事件处理填写" class="dispose-step-container" v-else-if="disposeTaskForm">
        <template #title>
          <div class="step-title">处置信息</div>
          <div class="step-info">
            <div class="step-icon issuing-step-icon">
              <i class="iconfont icon-chuzhixinxi_daifankui"></i>
            </div>
            <div class="step-summary-container">
              <div class="summary">{{ disposeTaskForm.taskTitle }}</div>
              <div class="summary">{{ disposeTaskForm.taskContent }}</div>
              <div class="base-info">
                <span class="info">未反馈</span>
                <span class="info">{{ disposeTaskForm.disposeTime }}</span>
              </div>
            </div>
          </div>
        </template>
        <!-- 任务处理的表单信息 -->
        <van-form class="step-content dispose-form" @submit="commitTaskDispose">
          <van-field
            v-model="disposeTaskForm.feedback"
            class="form-item form-textarea"
            rows="8"
            autosize
            label=""
            type="textarea"
            :rules="rules.feedback"
            placeholder="请填写反馈内容"
          />
          <attachment-overview
            v-if="disposeTaskForm.filePicture || disposeTaskForm.fileVideo || disposeTaskForm.fileVoice"
            :imageList="disposeTaskForm.filePicture ? [disposeTaskForm.filePicture] : []"
            :videoList="disposeTaskForm.fileVideo ? [disposeTaskForm.fileVideo] : []"
            :audioList="disposeTaskForm.fileVoice ? [disposeTaskForm.fileVoice] : []"
            canDelete
            @delete="deleteDisposeUpload"
          ></attachment-overview>
          <div class="commit-container">
            <!-- 图片上传 -->
            <van-uploader
              :after-read="disposeTaskImageUpload"
              accept="image/*"
              max-count="1"
              class="attachment-upload-btn"
            >
              <van-icon name="photo-o" />
            </van-uploader>
            <!-- 视频上传 -->
            <van-uploader
              :after-read="disposeTaskVideoUpload"
              accept="video/*"
              max-count="1"
              class="attachment-upload-btn"
            >
              <i class="iconfont icon-shangchuanshipin"></i>
            </van-uploader>
            <!-- 录音 -->
            <div class="attachment-upload-btn" @click="disposeRecorderVisible = true">
              <i class="iconfont icon-yuyinwenjian"></i>
            </div>
            <van-button native-type="submit" class="commit-btn">确定</van-button>
          </div>
        </van-form>
      </van-collapse-item>

      <!-- 已经处置完成的记录列表 -->
      <van-collapse-item
        v-for="task in finishTaskList"
        :key="task.id"
        :name="'处置信息-' + task.id"
        class="dispose-step-container"
      >
        <template #title>
          <div class="step-title" v-if="!disposeTaskForm">处置信息</div>
          <div class="step-info">
            <div class="step-icon">
              <i class="iconfont icon-chuzhixinxi_yifankui"></i>
            </div>
            <div class="step-summary-container">
              <div class="summary">{{ task.taskTitle }}</div>
              <div class="summary">{{ task.taskContent }}</div>
              <div class="base-info">
                <span class="info">已反馈</span>
                <span class="info">{{ task.disposeTime }}</span>
              </div>
            </div>
          </div>
        </template>
        <!-- 具体的反馈信息 -->
        <div class="task-dispose-info">
          <div class="dispose-person-info">
            <div class="person-head-img">
              <i class="iconfont icon-yidongduan_gerenzhongxin"></i>
            </div>
            <div class="person-info">
              <div class="person-name">{{ task.person }}</div>
              <div class="deal-time">{{ task.dealTime }}</div>
            </div>
          </div>
          <div class="task-dispose-content">{{ task.feedback }}</div>
          <attachment-overview
            v-if="task.filePicture || task.fileVideo || task.fileVoice"
            :imageList="task.filePicture ? [task.filePicture] : []"
            :videoList="task.fileVideo ? [task.fileVideo] : []"
            :audioList="task.fileVoice ? [task.fileVoice] : []"
          ></attachment-overview>
        </div>
      </van-collapse-item>
    </van-collapse>
    <recorder-collect-popup
      :visible.sync="feedbackRecorderVisible"
      @getRecorderFile="handleFeedbackRecorder"
    ></recorder-collect-popup>
    <recorder-collect-popup
      :visible.sync="disposeRecorderVisible"
      @getRecorderFile="disposeTaskAudioUpload"
    ></recorder-collect-popup>
  </div>
</template>

<script>
import moment from 'moment'
import AttachmentOverview from '@/components/AttachmentOverview'
import RecorderCollectPopup from '@/components/RecorderCollectPopup'
import axios from '@/utils/request'
export default {
  name: 'eventDetail',
  components: { AttachmentOverview, RecorderCollectPopup },
  data() {
    return {
      eventData: {},
      eventStatus: '', // 135 核查中；41 待启动；42：进行中；43 已结束
      activeStep: [],
      checkInfo: null,
      rules: {
        checkContent: [{ required: true, message: '请填写核查内容' }],
        feedback: [{ required: true, message: '请填写反馈内容' }]
      },
      feedbackForm: {
        isTrue: '否',
        checkContent: '',
        fileList: []
      },
      feedbackInfo: null,
      disposeTaskForm: null,
      disposeRecorderVisible: false,
      feedbackRecorderVisible: false,
      finishTaskList: []
    }
  },
  mounted() {
    this.eventData = this.$route.params.eventData
    this.eventStatus = this.eventData.status
    // 查询核查信息
    if ([135, 41, 43].includes(this.eventStatus)) {
      this.queryCheckInfo()
    }
    // 查询处置任务的信息
    if ([42, 43].includes(this.eventStatus)) {
      this.queryTaskInfo()
    }
  },
  methods: {
    queryCheckInfo() {
      axios.get(`/binhuapis/eventCheck/query-check?eventId=${this.eventData.id}`).then((res) => {
        if (res.data.code === 200 && res.data.data && res.data.data.list && res.data.data.list[0]) {
          this.checkInfo = res.data.data.list[0]
          if (this.eventStatus === 41 || this.eventStatus === 43) {
            // 待启动
            this.queryFeedbackInfo()
          }
        }
      })
    },
    queryFeedbackInfo() {
      axios.get(`/binhuapis/eventCheck/query-feedback?checkId=${this.checkInfo.id}`).then((res) => {
        if (res.data.code === 200 && res.data.data && res.data.data.list && res.data.data.list[0]) {
          this.feedbackInfo = res.data.data.list[0]
        }
      })
    },
    queryTaskInfo() {
      axios.get(`/binhuapis/dapeng/eventTask/query-event-task?eventId=${this.eventData.id}`).then((res) => {
        if (res.data.code === 200 && res.data.data && res.data.data.list && res.data.data.list[0]) {
          let handleTask
          const finishTasks = []
          res.data.data.list[0].eventNodeList.reduce((list, node) => {
            node.eventTaskList.forEach((task) => {
              if (task.status === '1') {
                // 已反馈的任务
                finishTasks.unshift(task)
              } else if (task.personId === sessionStorage.getItem('userId')) {
                handleTask = task
              }
            })
          }, [])
          this.disposeTaskForm = handleTask
          this.finishTaskList = finishTasks.sort((a, b) => (b.disposeTime > a.disposeTime ? 1 : -1))
        }
      })
    },
    handleFeedbackUpload(file) {
      this.handleFileRead(file, (url) => {
        this.feedbackForm.fileList.push(url)
      })
    },
    handleFeedbackRecorder(audio) {
      this.handleFileRead({ file: audio }, (url) => {
        this.feedbackForm.fileList.push(url)
      })
    },
    deleteFeedbackAttachment(fileUrl) {
      let fileIndex = this.feedbackForm.fileList.findIndex((url) => url === fileUrl)
      if (fileIndex > -1) {
        this.feedbackForm.fileList.splice(fileIndex, 1)
      }
    },
    disposeTaskImageUpload(image) {
      this.handleFileRead(image, (url) => {
        this.disposeTaskForm.filePicture = url
      })
    },
    disposeTaskVideoUpload(video) {
      this.handleFileRead(video, (url) => {
        this.disposeTaskForm.fileVideo = url
      })
    },
    disposeTaskAudioUpload(audio) {
      this.handleFileRead({ file: audio }, (url) => {
        this.disposeTaskForm.fileVoice = url
      })
    },
    deleteDisposeUpload(fileUrl) {
      if (this.disposeTaskForm.filePicture === fileUrl) {
        this.disposeTaskForm.filePicture = ''
      } else if (this.disposeTaskForm.fileVideo === fileUrl) {
        this.disposeTaskForm.fileVideo = ''
      } else if (this.disposeTaskForm.fileVoice === fileUrl) {
        this.disposeTaskForm.fileVoice = ''
      }
    },
    handleFileRead(file, successCallback) {
      const formData = new FormData()
      formData.append('file', file.file)
      formData.append('group', 'system')
      axios
        .post('/binhuapis/file/upload', formData)
        .then((res) => {
          if (res.data.code === 200) {
            successCallback && successCallback(res.data.data)
          } else {
            this.$notify({
              type: 'danger',
              message: res.data.message
            })
          }
        })
        .catch((err) => {
          console.error(err)
          this.$notify({
            type: 'danger',
            message: '上传失败'
          })
        })
    },
    commitFeedback() {
      this.$dialog
        .confirm({
          title: '核查反馈',
          message: '您确定要提交反馈信息？'
        })
        .then(() => {
          const commitForm = {
            checkId: this.checkInfo.id,
            isTrue: this.feedbackForm.isTrue,
            checkContent: this.feedbackForm.checkContent,
            checkTime: moment().format('YYYY-MM-DD HH:mm:ss'),
            fileUrls: this.feedbackForm.fileList.join(',')
          }
          this.$toast({
            type: 'loading',
            message: '提交中...',
            forbidClick: true,
            duration: 0
          })
          axios
            .post('/binhuapis/eventCheck/add-feedback', commitForm)
            .then((res) => {
              this.$toast.clear()
              if (res.data.code === 200) {
                this.$notify({
                  type: 'success',
                  message: '提交成功'
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
                message: '提交失败'
              })
            })
        })
    },
    commitTaskDispose() {
      this.$dialog
        .confirm({
          title: '事件处置',
          message: '您确定要提交处置反馈？'
        })
        .then(() => {
          const commitForm = { ...this.disposeTaskForm }
          commitForm.dealTime = moment().format('YYYY-MM-DD HH:mm:ss')
          commitForm.status = '1'
          this.$toast({
            type: 'loading',
            message: '提交中...',
            forbidClick: true,
            duration: 0
          })
          axios
            .post('/binhuapis/dapeng/eventTask/edit', commitForm)
            .then((res) => {
              this.$toast.clear()
              if (res.data.code === 200) {
                this.$notify({
                  type: 'success',
                  message: '提交成功'
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
                message: '提交失败'
              })
            })
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.event-detail-page {
  padding: 28rem;
}
.event-title {
  font-size: 33rem;
  color: #010101;
  line-height: 50rem;
  font-weight: 600;
}
.event-img {
  margin-top: 60rem;
  .img {
    width: 100%;
  }
}
.event-info,
.event-content {
  color: #000000;
  font-size: 28rem;
  line-height: 50rem;
  white-space: pre-wrap;
}
.event-info {
  margin-top: 40rem;
  padding-left: 2em;
}
.event-content {
  text-indent: 2em;
  padding-bottom: 40rem;
  border-bottom: 1px solid #ececec;
  /deep/ {
    img {
      width: 100%;
      margin: 20rem 0;
    }
  }
}
.step-collapse {
  /deep/ .van-cell__right-icon {
    position: absolute;
    bottom: 20rem;
    right: 20rem;
    color: #919191;
  }
}
.dispose-step-container {
  margin-top: 40rem;
  padding-bottom: 40rem;
  border-bottom: 1px solid #ececec;
  .step-title {
    color: #427de5;
    font-size: 28rem;
    line-height: 42rem;
    font-weight: 600;
    &::before {
      content: '';
      display: inline-block;
      vertical-align: top;
      width: 11rem;
      height: 42rem;
      margin-right: 16rem;
      background: #427de5;
      box-shadow: 0 7rem 15rem 0 rgba(66, 125, 229, 0.4);
      border-radius: 6rem;
    }
  }
  .step-info {
    margin-top: 40rem;
    display: flex;
    justify-content: space-between;
    .step-icon {
      width: 40rem;
      color: #00c212;
      &.issuing-step-icon {
        color: #ff1010;
      }
      .iconfont {
        font-size: 36rem;
        line-height: 50rem;
      }
    }
    .step-summary-container {
      margin-left: 15rem;
      flex: 1;
    }
    .summary {
      color: #000;
      font-size: 28rem;
      line-height: 50rem;
      white-space: pre-wrap;
    }
    .base-info {
      margin-top: 40rem;
      color: #919191;
      font-size: 25rem;
      line-height: 40rem;
    }
    .info + .info {
      margin-left: 36rem;
    }
  }
  .step-content {
    margin-top: 40rem;
  }
}
.dispose-form {
  .form-item + .form-item {
    margin-top: 30rem;
  }
  .form-textarea /deep/ .van-field__control {
    padding: 10rem;
    background: #f6f6f6;
    border: 1px solid #ececec;
    border-radius: 7rem;
  }
  .commit-container {
    margin-top: 20rem;
    line-height: 40rem;
    .attachment-upload-btn {
      display: inline-block;
      vertical-align: top;
      width: 70rem;
      height: 70rem;
      text-align: center;
      + .attachment-upload-btn {
        margin-left: 40rem;
      }
      /deep/ .van-uploader__wrapper {
        display: inline-block;
        width: 100%;
        height: 100%;
      }
      .iconfont,
      .van-icon {
        color: #919191;
        font-size: 40rem;
        line-height: 70rem;
      }
    }
  }
  .commit-btn {
    float: right;
    color: #919191;
    font-size: 28rem;
    border: none;
    background: transparent;
  }
}
.dispose-info-container {
  .info-item {
    display: flex;
    justify-content: space-between;
    color: #000000;
    font-size: 25rem;
    line-height: 40rem;
    + .info-item {
      margin-top: 40rem;
    }
  }
  .info-label {
    flex-basis: 140rem;
    flex-shrink: 0;
    color: #919191;
  }
  .info-content {
    margin-left: 56rem;
    text-align: right;
    white-space: pre-wrap;
  }
  .info-item.line-content {
    display: block;
    .info-label,
    .info-content {
      width: 100%;
    }
    .info-content {
      margin-left: 0;
      margin-top: 20rem;
      text-align: left;
      white-space: pre-wrap;
    }
  }
}
.task-dispose-info {
  margin-top: 40rem;
  .dispose-person-info {
    display: flex;
    justify-content: space-between;
  }
  .person-head-img {
    flex-shrink: 0;
    flex-basis: 63rem;
    width: 63rem;
    height: 63rem;
    color: #fff;
    line-height: 61rem;
    text-align: center;
    background: #919191;
    border: 1rem solid #ececec;
    opacity: 0.5;
    border-radius: 50%;
    .iconfont {
      font-size: 36rem;
    }
  }
  .person-info {
    margin-left: 38rem;
    flex: 1;
  }
  .person-name {
    flex: 1;
    color: #000;
    font-size: 28rem;
    line-height: 36rem;
  }
  .deal-time {
    color: #919191;
    font-size: 21rem;
    line-height: 27rem;
  }
  .task-dispose-content {
    margin-top: 30rem;
    color: #000;
    font-size: 28rem;
    line-height: 36rem;
    white-space: pre-wrap;
  }
}
</style>
