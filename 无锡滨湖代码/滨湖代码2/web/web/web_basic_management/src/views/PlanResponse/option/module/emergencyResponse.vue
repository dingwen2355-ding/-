<template>
  <div class="emergencyResponse">
    <el-form ref="form" label-width="80px" :disabled="isCode == 1">
      <el-form-item label="级别">
        <el-select v-model="viewData.planResponseGroups.levelId" style="width: 220px" placeholder="请选择响应级别" clearable>
          <el-option v-for="(item,key) in levelTypes" :value="item.value" :label="item.label" :key="key"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启动条件">
        <el-input type="textarea" v-model="viewData.planResponseGroups.groupName"></el-input>
      </el-form-item>
      <el-form-item label="响应条件">
<!--        <el-input type="textarea" v-model="viewData.planResponseGroups.extendRemark"></el-input>-->
        <auto-editor v-model="viewData.planResponseGroups.remark"></auto-editor>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from '@/utils/request'
import AutoEditor from '@/components/AutoEditor/AutoEditor'
export default {
  name: 'emergencyResponse',
  components: { AutoEditor },
  props: ['viewData', 'isCode'],
  data() {
    return {
      levelTypes: []
    }
  },
  watch: {
  },
  mounted() {
    this.getLevelType()
  },
  methods: {
    getLevelType() {
      let url = '/binhuapis/get-level-type'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          this.levelTypes = res.data.data
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
