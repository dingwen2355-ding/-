<template>
  <div>
    <basic-container class="top">
      <el-card class="box-card">
        <div class="left">
          <img src="/img/bg/zongjixiangmu.png" alt="" />
        </div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ dataList[0] }}</span>
            <span>个</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">总计用户</div>
        </div>
      </el-card>
      <el-card class="box-card">
        <div class="left"><img src="/img/bg/denglu.png" alt="" /></div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ dataList[1] }}</span>
            <span>个</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">活跃用户</div>
        </div>
      </el-card>
      <el-card class="box-card">
        <div class="left"><img src="/img/bg/tuisong.png" alt="" /></div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ dataList[2] }}</span>
            <span>个</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">今日新增用户</div>
        </div>
      </el-card>
    </basic-container>
    <center :type="1"></center>
    <div class="bottom">
      <left-table type="部门"></left-table>
      <charts title="登录次数月度分析 (近3月)"></charts>
    </div>
  </div>
</template>

<script>
import { getUserData } from "@/api/statistic/statistic";
import center from "./components/center.vue";
import leftTable from "./components/leftTable.vue";
import charts from "./components/charts.vue";
export default {
  components: {
    center,
    leftTable,
    charts,
  },
  data() {
    return {
      dataList: [],
    };
  },
  watch: {},
  async mounted() {
    await getUserData().then((res) => {
      res.data.data.forEach((item) => {
        if (item.name == "总计用户") {
          this.dataList.push(item.number);
        } else if (item.name == "今日登录") {
          this.dataList.push(item.number);
        } else {
          this.dataList.push(item.number);
        }
      });
    });
    this.getData();
  },
  methods: {
    search() {
      this.getData();
    },
    async getData() {},
  },
};
</script>

<style lang="scss" scoped>
.top {
  padding-bottom: 0;
  /deep/.el-card__body {
    display: flex;
    align-items: center;
    .box-card {
      width: 300px;
      margin-right: 15px;
    }
  }
}
.box-card /deep/.el-card__body {
  padding: 10px 20px !important;
  .left {
    margin-right: 20px;
    padding-top: 4px;
  }
}
.bottom {
  display: flex;
  height: 430px;
  width: 100%;
  justify-content: space-between;
}
* {
  box-sizing: border-box;
}
</style>