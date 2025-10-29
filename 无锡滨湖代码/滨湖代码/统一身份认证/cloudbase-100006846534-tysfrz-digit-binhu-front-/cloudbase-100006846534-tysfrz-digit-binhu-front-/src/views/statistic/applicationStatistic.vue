<template>
  <div>
    <div class="top">
      <div class="top-left">
        <el-card class="box-card">
          <div class="left">
            <img src="/img/bg/zongjixiangmu.png" alt="" />
          </div>
          <div class="right">
            <div>
              <span style="font-size: 28px">{{ dataList[0].number }}</span>
              <span>个</span>
            </div>
            <div>
              <span>应用接入数</span>
              <span
                class="right-text"
                :style="{
                  color: Number(dataList[0].percent) >= 0 ? 'green' : 'red',
                }"
                >环比上月 {{ dataList[0].percent }}%</span
              >
            </div>
          </div>
        </el-card>
        <el-card class="box-card" style="margin-top: 20px">
          <div class="left"><img src="/img/bg/denglu.png" alt="" /></div>
          <div class="right">
            <div>
              <span style="font-size: 28px">{{ dataList[1].number }}</span>
              <span>次</span>
            </div>
            <div>
              <span>应用访问次数</span>
              <span
                class="right-text"
                :style="{
                  color: Number(dataList[1].percent) >= 0 ? 'green' : 'red',
                }"
                >环比上月 {{ dataList[1].percent }}%</span
              >
            </div>
          </div>
        </el-card>
      </div>
      <basic-container class="top-right">
        <div class="title">应用分类统计</div>
        <el-table :data="tableData" style="width: 100%" height="200">
          <el-table-column
            prop="name"
            label="应用分类"
            align="center"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column prop="appNumber" label="应用数" align="center">
          </el-table-column>
          <el-table-column
            prop="accessNumber"
            label="访问次数"
            align="center"
          ></el-table-column>
        </el-table>
      </basic-container>
    </div>
    <center :type="2"></center>
    <div class="bottom">
      <left-table type="应用"></left-table>
      <charts title="全平台应用访问统计"></charts>
    </div>
  </div>
</template>

<script>
import center from "./components/center";
import leftTable from "./components/leftTable.vue";
import charts from "./components/charts.vue";
import {
  getYOYRecord,
  getAppClassificationStatistics,
} from "@/api/statistic/statistic";
export default {
  components: {
    center,
    leftTable,
    charts,
  },
  data() {
    return {
      dataList: [],
      tableData: [],
    };
  },
  async created() {
    await getYOYRecord().then((res) => {
      res.data.data.forEach((item) => {
        if (item.name == "应用接入数量") {
          this.dataList.push(item);
        } else if (item.name == "应用访问次数") {
          this.dataList.push(item);
        }
      });
    });
    getAppClassificationStatistics().then((res) => {
      this.tableData = res.data.data;
    });
  },
};
</script>
<style lang="scss" scoped>
.title {
  font-weight: 600;
}
.top {
  display: flex;
  /deep/.basic-container {
    padding-top: 0;
    padding-bottom: 0;
  }
  .top-left {
    width: 500px;
    background: #fff;
    margin-left: 7px;
    border-radius: 5px;
    padding: 20px;
    font-size: 12px;
    /deep/.el-card__body {
      display: flex;
      width: 100%;
      height: 60px;
      .left {
        margin-right: 20px;
        img {
          height: 60px;
          width: 60px;
        }
      }
      .right {
        width: calc(100% - 120px);
        .right-text {
          float: right;
        }
      }
    }
  }
  .top-right {
    width: calc(100% - 500px);
  }
}
.bottom {
  display: flex;
  height: 430px;
  width: 100%;
  justify-content: space-between;
}
/deep/.el-table th.el-table__cell {
  background: #f5f8ff;
  color: rgb(19, 18, 18);
  font-weight: 550;
}
* {
  box-sizing: border-box;
}
</style>