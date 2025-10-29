<template>
  <div class="specification-detail-page">
    <div class="detail-info-container">
      <div class="info-item">
        <div class="info-label">文件名称</div>
        <div class="info-content">{{ detailData.fileName }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">编制单位</div>
        <div class="info-content">{{ detailData.preparationUnit }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">安全管理类型</div>
        <div class="info-content">{{ detailData.managementType }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">监管行业</div>
        <div class="info-content">{{ detailData.regulatoryIndustry }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">编制日期</div>
        <div class="info-content">{{ detailData.preparedDate }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">生效日期</div>
        <div class="info-content">{{ detailData.effectiveDate }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">文档类型</div>
        <div class="info-content">{{ detailData.documentType }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">内容摘要</div>
        <div class="info-content">{{ detailData.contentSummary }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">备注</div>
        <div class="info-content">{{ detailData.notes }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">附件</div>
        <attachment-overview
          class="info-content"
          v-if="detailData.annexUrl"
          :fileList="detailData.annexUrl.split(',')"
        ></attachment-overview>
      </div>
    </div>
  </div>
</template>

<script>
import AttachmentOverview from '@/components/AttachmentOverview'

export default {
  name: 'specificationDetail',
  components: { AttachmentOverview },
  data() {
    return {
      detailData: {}
    }
  },
  mounted() {
    this.detailData = this.$route.params.data
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.specification-detail-page {
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
    flex-basis: 160rem;
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
