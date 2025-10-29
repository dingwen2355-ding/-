<template>
  <div class="my-plan">
    <!-- <el-tabs v-model="activeName" @tab-click="handleClick"> -->
      <!-- <el-tab-pane label="编辑预案" name="first">
        <edit-plan ref="editPlan"></edit-plan>
      </el-tab-pane>
      <el-tab-pane label="共享预案" name="second">
        <share-plan ref="sharePlan"></share-plan>
      </el-tab-pane> -->
      <!-- <el-tab-pane label="已发布预案" name="third"> -->
      <release-plan ref="releasePlan"></release-plan>
      <!-- </el-tab-pane> -->
    <!-- </el-tabs> -->
  </div>
</template>
<script>
// import editPlan from './optionPlan/editPlan.vue'
// import sharePlan from './optionPlan/sharePlan.vue'
import releasePlan from './optionPlan/releasePlan.vue'
export default {
  components: { releasePlan },
  name: 'myPlan',
  data() {
    return {
      activeName: 'third'
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log('点击', tab, event)
      if (tab.name == 'first') {
        this.$refs['editPlan'].queryMyPlanList()
        this.$refs['editPlan'].resetList()
      }
      if (tab.name == 'second') {
        this.$refs['sharePlan'].queryTableList()
        this.$refs['sharePlan'].getManageLevel()
        this.$refs['sharePlan'].queryPlanType()
      }
      if (tab.name == 'third') {
        this.$refs['releasePlan'].queryTableList()
        this.$refs['releasePlan'].getManageLevel()
        this.$refs['releasePlan'].queryPlanType()
      }
    }
  }
}
</script>
<style lang="scss">
.my-plan {
  height: 100%;
  width: 100%;
  background: #eff2f5 !important;
  padding: 0 !important;
  .el-tabs__header {
    width: 100%;
    height: 61px;
    background: #ffffff;
    padding: 13px;
  }
  .el-tabs__content {
    #pane-second,
    #pane-third {
      background: #ffffff;
      padding: 15px;
    }
  }
}
</style>
