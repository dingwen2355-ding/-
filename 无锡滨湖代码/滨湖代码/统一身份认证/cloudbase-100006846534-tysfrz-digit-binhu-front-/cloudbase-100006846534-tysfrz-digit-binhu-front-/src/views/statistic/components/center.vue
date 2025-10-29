<template>
  <basic-container>
    <div class="title">
      <div class="title-text">{{ title }}</div>
      <div>
        <el-radio-group v-model="radio" size="small" @input="handleDateType">
          <el-radio-button label="1">本周</el-radio-button>
          <el-radio-button label="2">本月</el-radio-button>
          <el-radio-button label="3">今年</el-radio-button>
        </el-radio-group>
        <el-date-picker
          style="margin-left: 10px"
          size="small"
          v-model="dateTime"
          type="daterange"
          range-separator="至"
          value-format="yyyy-MM-dd"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleChooseDate"
        >
        </el-date-picker>
      </div>
    </div>
    <e-chart
      :options="chartOption"
      style="width: 100%; height: 260px"
    ></e-chart>
  </basic-container>
</template>

<script>
import {
  getEcharsList,
  getRecordEchars,
  getSmsEchars,
} from "@/api/statistic/statistic";
export default {
  props: {
    type: {
      type: Number,
      default: 1,
    },
  },
  data() {
    return {
      title: "",
      chartOption: {},
      radio: "1",
      dateTime: [],
    };
  },
  mounted() {
    this.title =
      this.type == 1
        ? "活跃趋势"
        : this.type == 2
        ? "访问趋势"
        : "短信发送趋势";
    this.getData();
  },
  methods: {
    handleChooseDate() {
      this.radio = "";
      this.getData();
    },
    handleDateType() {
      this.dateTime = [];
      this.getData();
    },
    async getData() {
      this.loading = true;
      let xArr = [];
      let yData = [];
      let params = {};
      let yName = "";
      if (this.type == 1) {
        await getEcharsList({
          type: this.radio,
          start: this.dateTime[0],
          end: this.dateTime[1],
        }).then((res) => {
          const data = res.data.data;
          xArr = data.times;
          yData.push({
            name: "新增用户",
            type: "line",
            emphasis: {
              focus: "series",
            },
            smooth: true,
            data: data.createUserSum,
          });
          yData.push({
            name: "活跃用户",
            type: "line",
            emphasis: {
              focus: "series",
            },
            smooth: true,
            data: data.activeUserSum,
          });
          yData.push({
            name: "登录次数",
            type: "line",
            emphasis: {
              focus: "series",
            },
            smooth: true,
            data: data.onLineSum,
          });
        });
        yName = "数量";
      } else if (this.type == 2) {
        yName = "应用访问次数";
        params = {
          type: this.radio,
          start: this.dateTime[0],
          end: this.dateTime[1],
        };
        await getRecordEchars(params).then((res) => {
          const data = res.data.data;
          if (data.length > 0) {
            data.forEach((item) => {
              if (item.name != "time") {
                yData.push({
                  name: item.name,
                  type: "line",
                  emphasis: {
                    focus: "series",
                  },
                  smooth: true,
                  data: item.x,
                });
              } else {
                xArr = item.x;
              }
            });
          }
        });
      } else if (this.type == 3) {
        yName = "短信数量";
        params = {
          type: this.radio,
          start: this.dateTime[0],
          end: this.dateTime[1],
        };
        await getSmsEchars(params).then((res) => {
          const data = res.data.data;
          if (data.length > 0) {
            data.forEach((item) => {
              if (item.name != "time") {
                yData.push({
                  name: item.name,
                  type: "line",
                  emphasis: {
                    focus: "series",
                  },
                  smooth: true,
                  data: item.x,
                });
              } else {
                xArr = item.x;
              }
            });
          }
        });
      }
      this.chartOption = {
        tooltip: {
          trigger: "axis",
          confine: true,
          appendToBody: true,
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985",
            },
          },
        },
        legend: {
          type: "scroll",
        },
        grid: {
          left: "3%",
          right: "3%",
          bottom: "3%",
          top: 50,
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: xArr,
          name: "日期",
          nameTextStyle: {
            color: "#333",
          },
          axisTick: {
            alignWithLabel: true,
          },
          axisLine: {
            lineStyle: {
              color: "#ccc",
              type: "dashed",
            },
          },
        },
        yAxis: {
          type: "value",
          name: yName,
          nameTextStyle: {
            color: "#333",
          },
          minInterval: 1,
          axisLine: {
            lineStyle: {
              color: "#ccc",
              type: "dotted",
            },
          },
          splitLine: {
            lineStyle: {
              color: "#ccc",
              type: "dashed",
            },
          },
          axisTick: {
            show: false,
          },
        },
        series: yData,
      };
    },
  },
};
</script>

<style lang="scss" scoped>
.title {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.title-text {
  font-weight: 600;
}
* {
  box-sizing: border-box;
}
</style>