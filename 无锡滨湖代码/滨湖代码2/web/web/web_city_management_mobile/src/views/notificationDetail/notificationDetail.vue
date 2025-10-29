<template>
  <div class="notification-detail">
    <div class="detail-info-container">
      <div class="info-item">
        <div class="info-label">事件标题</div>
        <div class="info-content">{{ detailData.messageTitle }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">发布时间</div>
        <div class="info-content">{{ detailData.sendTime }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">类别</div>
        <div class="info-content">{{ detailData.messageCategory }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">消息级别</div>
        <div class="info-content">{{ detailData.messageLevel }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">消息内容</div>
        <div class="info-content">{{ detailData.messageContent }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">附件</div>
        <attachment-overview
          class="info-content"
          v-if="detailData.imgUrl"
          :fileList="detailData.imgUrl.split(',')"
        ></attachment-overview>
      </div>
    </div>
  </div>
</template>

<script>
import AttachmentOverview from '@/components/AttachmentOverview'
import axios from '@/utils/request'

export default {
  name: 'notificationDetail',
  components: { AttachmentOverview },
  data() {
    return {
      detailData: {}
    }
  },
  mounted() {
    this.detailData = this.$route.params.data
    this.handleReadNotification()
  },
  methods: {
    handleReadNotification() {
      // 自动设置消息为已读状态
      const params = {
        Id: this.detailData.id,
        messageType: '2'
      }
      axios({
        method: 'put',
        url: '/binhuapis/updateMessageType',
        params
      }).then((res) => {
        if (res.data.code !== 200) {
          console.error('该消息已读失败')
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.notification-detail {
  padding: 40rem 28rem;
}
.detail-info-container {
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
</style>
