<template>
  <div class="contanier">
    <div class="title">{{ title }}</div>
    <e-chart
      :options="chartOptions"
      style="width: 100%; height: 300px"
    ></e-chart>
  </div>
</template>

<script>
import {
  getAccessStatistics,
  getAppRecordRank,
  getRecentlyEchars,
  getAllSendEchars,
} from "@/api/statistic/statistic";
export default {
  data() {
    return {
      chartOptions: {
        tooltip: {
          trigger: "item",
        },
        legend: {
          type: "scroll",
          top: 50,
          left: "70%",
          orient: "vertical",
        },
        series: [
          {
            name: "",
            type: "pie",
            radius: ["40%", "60%"],
            center: ["36%", "50%"],
            avoidLabelOverlap: false,
            data: [],
            labelLine: {
              show: true,
            },
            label: {
              show: true,
            },
          },
        ],
      },
    };
  },
  props: {
    title: {
      type: String,
      default: "登录人数月度分析 (近3月)",
    },
  },
  mounted() {
    if (this.title == "登录次数月度分析 (近3月)") {
      getAccessStatistics().then((res) => {
        res.data.data.map((item) => {
          this.chartOptions.series[0].data.push({
            name: item.name,
            value: item.number,
          });
        });
      });
    } else if (this.title == "全平台应用访问统计") {
      getAppRecordRank().then((res) => {
        if (res.data.data.length > 0) {
          res.data.data.map((item) => {
            this.chartOptions.series[0].data.push({
              name: item.name,
              value: item.number,
            });
          });
          let that = this;
          this.chartOptions.series[0].labelLine.show = false;
          this.chartOptions.series[0].label.show = false;
          this.chartOptions.legend.formatter = function (name) {
            //图例后添加数值
            let data = that.chartOptions.series[0].data;
            let tarValue;
            for (let i = 0; i < data.length; i++) {
              if (data[i].name === name) {
                tarValue = data[i].value;
              }
            }
            return name + ": " + tarValue;
          };
        }
      });
    } else if (this.title == "短信发送分析 (近3月)") {
      getRecentlyEchars().then((res) => {
        res.data.data.map((item) => {
          this.chartOptions.series[0].data.push({
            name: item.name,
            value: item.number,
          });
        });
      });
    } else if (this.title == "全平台应用短信发送统计") {
      getAllSendEchars().then((res) => {
        res.data.data.map((item) => {
          this.chartOptions.series[0].data.push({
            name: item.name,
            value: item.number,
          });
        });
        let that = this;
        this.chartOptions.series[0].labelLine.show = false;
        this.chartOptions.series[0].label.show = false;
        this.chartOptions.legend.formatter = function (name) {
          //图例后添加数值
          let data = that.chartOptions.series[0].data;
          let tarValue;
          for (let i = 0; i < data.length; i++) {
            if (data[i].name === name) {
              tarValue = data[i].value;
            }
          }
          return name + ": " + tarValue;
        };
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
* {
  box-sizing: border-box;
}
</style>