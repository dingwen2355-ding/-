<template>
  <div class="consultation-notice-detail">
    <div class="notice-title">{{ noticeData.title }}</div>
    <div class="notice-info">
      <span class="info-item">{{ noticeData.publisher }}</span>
      <span class="info-item">{{ noticeData.releaseTime }}</span>
    </div>
    <section class="notice-content" v-html="noticeData.remark"></section>
    <attachment-overview :fileList="noticeData.annexUrl.split(',')" v-if="noticeData.annexUrl"></attachment-overview>
  </div>
</template>

<script>
import AttachmentOverview from '@/components/AttachmentOverview'

export default {
  name: 'consultationNoticeDetail',
  components: { AttachmentOverview },
  data() {
    return {
      noticeData: {}
    }
  },
  mounted() {
    this.noticeData = this.$route.params.noticeData
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.consultation-notice-detail {
  padding: 28rem;
}
.notice-title {
  color: #010101;
  font-size: 33rem;
  font-weight: 600;
  line-height: 50rem;
  text-align: center;
}
.notice-info {
  color: #919191;
  font-size: 25rem;
  line-height: 30rem;
  margin-top: 40rem;
  .info-item {
    margin-right: 26rem;
  }
}
.notice-content {
  margin-top: 40rem;
  color: #000000;
  font-size: 28rem;
  line-height: 50rem;
  white-space: pre-wrap;
  /deep/ {
    img {
      width: 100%;
    }
  }
}
</style>
