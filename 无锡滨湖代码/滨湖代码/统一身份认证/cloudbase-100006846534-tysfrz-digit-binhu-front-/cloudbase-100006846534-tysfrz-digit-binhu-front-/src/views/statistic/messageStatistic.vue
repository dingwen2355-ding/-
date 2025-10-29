<template>
  <div>
    <basic-container class="top">
      <el-card class="box-card">
        <div class="left">
          <img src="/img/bg/zongjixiangmu.png" alt="" />
        </div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ dataList[0].number }}</span>
            <span>条</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">
            <span>短信总计</span>
            <span
              class="right-text"
              :style="{
                color: Number(dataList[0].percent) >= 0 ? 'green' : 'red',
              }"
              >环比上月 {{ dataList[0].percent }}%
            </span>
          </div>
        </div>
      </el-card>
      <el-card class="box-card">
        <div class="left"><img src="/img/bg/denglu.png" alt="" /></div>
        <div class="right">
          <div>
            <span style="font-size: 26px">{{ dataList[1].number }}</span>
            <span>条</span>
          </div>
          <div style="margin-top: 2px; font-size: 12px">今日发送</div>
        </div>
      </el-card>
    </basic-container>
    <center :type="3"></center>
    <div class="bottom">
      <charts title="短信发送分析 (近3月)"></charts>
      <charts title="全平台应用短信发送统计"></charts>
    </div>
  </div>
</template>

<script>
import { getSMSStatistics } from "@/api/statistic/statistic";
import center from "./components/center";
import charts from "./components/charts.vue";
export default {
  components: {
    center,
    charts,
  },
  data() {
    return {
      dataList: [],
    };
  },
  mounted() {
    getSMSStatistics().then((res) => {
      res.data.data.forEach((item) => {
        if (item.name == "短信总计") {
          this.dataList.push(item);
        } else if (item.name == "今日发送") {
          this.dataList.push(item);
        }
      });
    });
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
      width: 350px;
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
  .right {
    width: calc(100% - 100px);
    .right-text {
      float: right;
    }
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