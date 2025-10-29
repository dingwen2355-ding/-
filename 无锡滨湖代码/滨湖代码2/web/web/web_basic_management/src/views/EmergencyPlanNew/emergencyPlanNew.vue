<template>
  <div class="emergency-plan">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="国家级" name="first">
        <CityLevel ref="first" />
      </el-tab-pane>
      <el-tab-pane label="省级" name="second">
        <CityLevel ref="second" />
      </el-tab-pane>
      <el-tab-pane label="市级" name="three">
        <CityLevel ref="three" />
      </el-tab-pane>
      <el-tab-pane label="区县级" name="four">
        <TownshipLevel ref="four" />
      </el-tab-pane>
      <el-tab-pane label="乡镇级" name="five">
        <TownshipLevel ref="five" />
      </el-tab-pane>
      <el-tab-pane label="基层单位级" name="six">
        <TownshipLevel ref="six" />
      </el-tab-pane>
      <!-- <el-tab-pane label="企业级" name="seven">
        <EnterpriseLevel ref="seven" />
      </el-tab-pane>-->
    </el-tabs>
  </div>
</template>
<script>
import Axios from '@/utils/request'
// import znvTime from '../../utils/znvTime'
import CityLevel from '@/views/EmergencyPlanNew/optionPlan/cityLevel'
import TownshipLevel from '@/views/EmergencyPlanNew/optionPlan/townshipLevel'
export default {
  components: {
    CityLevel,
    TownshipLevel
  },
  name: 'emergencyPlanNew',
  data() {
    return {
      activeName: 'first',
      tabLevel: '985',
      manageLevelList: []
    }
  },
  mounted() {
    this.getManageLevel()
  },
  methods: {
    // 管理级别
    getManageLevel() {
      let url = '/binhuapis/getLevel'
      Axios.get(url).then((r) => {
        this.manageLevelList = r.data.data
      })
    },
    handleClick(tab, event) {
      // console.log(tab, event)
      if (tab.name !== 'seven') {
        this.$refs[tab.name].tabLevel = this.manageLevelList.filter((v) => v.label === tab.label)[0].value
      }
      this.$refs[tab.name].queryTableList()
      // console.log(this.manageLevelList.filter((v) => v.name === tab.label)[0]);
      // if (tab.name == 'first' || tab.name == 'second' || tab.name == 'three') {
      //   console.log(this.tabLevel, 'tabLevel')
      //   // this.queryAuditTableList()
      // } else if (tab.name == 'four' || tab.name == 'five' || tab.name == 'six') {
      //   this.$refs['townshipLevel'].tabLevel = this.tabLevel
      //   this.$refs['townshipLevel'].queryTableList()
      // }
    }
  }
}
</script>
<style scoped>
</style>
