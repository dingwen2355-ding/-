<template>
  <div class="contanier">
    <div class="title">访问最多{{ type }}排名</div>
    <el-table :data="tableData" style="width: 100%" height="300">
      <el-table-column label="排名" align="center" type="index" width="50">
      </el-table-column>
      <el-table-column prop="name" :label="type + '名称'" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="number" label="访问次数"> </el-table-column>
      <el-table-column label="占比">
        <template slot-scope="{ row }">
          <el-progress :percentage="row.percent"></el-progress>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getDeptRank, getAppRecordRank } from "@/api/statistic/statistic";
export default {
  props: {
    type: {
      type: String,
      default: "部门",
    },
  },
  data() {
    return {
      tableData: [],
      title: "",
    };
  },
  mounted() {
    if (this.type == "部门") {
      getDeptRank().then((res) => {
        this.tableData = res.data.data;
      });
    } else if (this.type == "应用") {
      getAppRecordRank().then((res) => {
        this.tableData = res.data.data;
      });
    }
  },
};
</script>
<style lang="scss" scoped>
.contanier {
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  width: 49%;
  margin: 0 5px 10px 6px;
}
.title {
  font-weight: 600;
}
/deep/.el-table th.el-table__cell {
  background: #f5f8ff;
  color: rgb(19, 18, 18);
  font-weight: 550;
}
/deep/.el-progress-bar {
  width: 90%;
}
* {
  box-sizing: border-box;
}
</style>
