<template>
  <div class="history-event-detail">
    <div class="detail-info-container">
      <div class="info-item">
        <div class="info-label">事件名称</div>
        <div class="info-content">{{ eventData.accidentTitle }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">事故地点</div>
        <div class="info-content">{{ eventData.accidentAddress }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">事故案例类型</div>
        <div class="info-content">{{ eventData.accidentCasesType }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">事故类型</div>
        <div class="info-content">{{ eventData.accidentType }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">发生日期</div>
        <div class="info-content">{{ eventData.occurrenceDate }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">监管行业</div>
        <div class="info-content">{{ eventData.regulatoryIndustry }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">事故模型</div>
        <div class="info-content">{{ eventData.accidentModel }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">轻伤人数</div>
        <div class="info-content">{{ eventData.minorNum }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">重伤人数</div>
        <div class="info-content">{{ eventData.seriousNum }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">死亡人数</div>
        <div class="info-content">{{ eventData.deathNum }}</div>
      </div>
      <div class="info-item">
        <div class="info-label">经济损失</div>
        <div class="info-content">{{ eventData.economicLoss }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">事故原因</div>
        <div class="info-content">{{ eventData.accidentCause }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">预防措施</div>
        <div class="info-content">{{ eventData.preventiveMeasure }}</div>
      </div>
      <div class="info-item line-content">
        <div class="info-label">附件</div>
        <attachment-overview
          class="info-content"
          v-if="eventData.annexUrl"
          :fileList="eventData.annexUrl.split(',')"
        ></attachment-overview>
      </div>
    </div>
  </div>
</template>

<script>
import AttachmentOverview from '@/components/AttachmentOverview'

export default {
  name: 'historyEventDetail',
  components: { AttachmentOverview },
  data() {
    return {
      eventData: {}
    }
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.eventData = to.params.data
    })
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.history-event-detail {
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
