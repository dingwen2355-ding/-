<template>
  <div class="emergency-plan-tags">
    <div style="text-align: left; height: 47px; margin-left: 50px" v-if="showRightContent" v-show="showName">
      {{ planName }}
    </div>
    <el-tabs :tab-position="tabPosition" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="前期准备" name="first" style="height: 100%">
        <slot name="prepare" v-if="showRightContent"></slot>
      </el-tab-pane>
      <el-tab-pane label="监测预警" name="second" :disabled="status == 1" style="height: 100%">
        <slot name="monitoringWarning" v-if="showRightContent"></slot>
      </el-tab-pane>
      <el-tab-pane label="应急响应" name="third" :disabled="status == 1" style="height: 100%">
        <slot name="emergencyResponse" v-if="showRightContent"></slot>
      </el-tab-pane>
      <el-tab-pane label="应急保障" name="fourth" :disabled="status == 1" style="height: 100%">
        <slot name="emergencySafe" v-if="showRightContent"></slot>
      </el-tab-pane>
      <el-tab-pane label="应急响应终止" name="fifth" :disabled="status == 1" style="height: 100%">
        <slot name="stopEmergencyResponse" v-if="showRightContent"></slot>
      </el-tab-pane>
      <el-tab-pane label="奖励与责任追究" name="sixth" :disabled="status == 1" style="height: 100%">
        <slot name="rewardsDuty" v-if="showRightContent"></slot>
      </el-tab-pane>
      <el-tab-pane label="相关资料" name="seventh" style="height: 100%">
        <slot name="about" v-if="showRightContent"></slot>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
export default {
  name: 'emergencyPlanTags',
  props: ['status', 'planName', 'showRightContent', 'showName'],
  data() {
    return {
      activeName: 'first',
      tabPosition: 'left'
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
<style lang="scss">
.emergency-plan-tags {
  height: 100%;
  .el-tabs--left {
    height: calc(100% - 60px);
    .el-tabs__content {
      overflow: hidden;
      position: relative;
      height: 100%;
    }
    // .el-tabs__item {
    //   margin-bottom: 20px;
    // }
  }
}
</style>
