<template>
  <div class="planMaterials">
    <el-table
      :data="viewData.planDataBeans"
      style="width: 100%">
      <el-table-column
        prop="fileName"
        label="名称">
      </el-table-column>
      <el-table-column
        prop="remark"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="上传时间">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="deleteMaterials(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'planMaterials',
  props: ['formInfo', 'viewData', 'isCode'],
  data() {
    return {
    }
  },
  mounted() {

  },
  methods: {
    deleteMaterials(row) {
      this.viewData.planDataBeans = this.viewData.planDataBeans.filter(t => row.id !== t.id)
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
