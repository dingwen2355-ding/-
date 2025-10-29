<template>
  <div class="emergencyResponse">
    <el-form ref="form" label-width="80px" :disabled="isCode==1">
      <el-form-item label="终止条件">
        <el-input type="textarea" v-model="viewData.planStopGroups.groupName"></el-input>
      </el-form-item>
      <el-form-item label="终止程序">
<!--        <el-input type="textarea" v-model="viewData.planStopGroups.extendRemark"></el-input>-->
        <auto-editor v-model="viewData.planStopGroups.remark"></auto-editor>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import AutoEditor from '@/components/AutoEditor/AutoEditor'
export default {
  components: { AutoEditor },
  name: 'terminationConditions',
  props: ['viewData', 'isCode'],
  data() {
    return {
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
