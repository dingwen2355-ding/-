<template>
  <div class="law-detail-page">
    <div class="law-title">{{ lawData.lawName }}</div>
    <div class="law-base-info">
      <span class="type-tag" v-if="lawData.lawClass">{{ lawData.lawClass }}</span>
    </div>
    <div class="law-content-container">
      <div class="law-node">
        <div class="node-title">法规分类</div>
        <div class="node-content">{{ lawData.lawClass }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">违法行为描述</div>
        <div class="node-content">{{ lawData.illegalDescription }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">法律规定</div>
        <div class="node-content">{{ lawData.legalProvisions }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">规定条款</div>
        <div class="node-content">{{ lawData.prescribedTerms }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">处罚依据条款</div>
        <div class="node-content">{{ lawData.penaltyBasisClause }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">处罚依据名称</div>
        <div class="node-content">{{ lawData.penaltyBasisName }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">处罚依据</div>
        <div class="node-content">{{ lawData.penaltyBasis }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">实施标准</div>
        <div class="node-content">{{ lawData.standards }}</div>
      </div>
      <div class="law-node">
        <div class="node-title">事故类型</div>
        <div class="node-content">{{ lawData.accidentType }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'lawDetail',
  data() {
    return {
      lawData: {}
    }
  },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      vm.lawData = to.params.data
    })
  },
  methods: {}
}
</script>

<style lang="scss" scoped>
.law-detail-page {
  width: 100%;
  padding: 30rem;
}
.law-title {
  color: #010101;
  font-size: 33rem;
  font-weight: 600;
  line-height: 50rem;
}
.law-base-info {
  margin-top: 30rem;
  .type-tag {
    display: inline-block;
    padding: 0 14rem;
    height: 36rem;
    color: #6a9b00;
    font-size: 21rem;
    line-height: 34rem;
    background: rgba(105, 154, 0, 0.1);
    border: 1rem solid #699a00;
    border-radius: 7rem;
  }
}
.law-content-container {
  margin-top: 40rem;
  .law-node {
    margin-top: 10rem;
  }
  .node-title {
    color: #427de5;
    font-size: 25rem;
    line-height: 35rem;
    margin-left: -17rem;
    &::before {
      content: '';
      display: inline-block;
      vertical-align: top;
      width: 35rem;
      height: 35rem;
      background: #fff;
      border-radius: 50%;
      border: 11rem solid #427de5;
      margin-right: 20rem;
    }
  }
  .node-content {
    margin-top: 10rem;
    padding-left: 36rem;
    padding-bottom: 40rem;
    color: #000;
    font-size: 28rem;
    line-height: 50rem;
    white-space: pre-wrap;
    border-left: 1rem dashed #427de5;
  }
}
</style>
