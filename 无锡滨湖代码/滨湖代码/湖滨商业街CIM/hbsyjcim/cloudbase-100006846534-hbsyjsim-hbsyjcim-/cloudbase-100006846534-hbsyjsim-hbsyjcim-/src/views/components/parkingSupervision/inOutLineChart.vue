<template>
  <div id="inOutLineChart"></div>
</template>
<script>
export default {
  props: {
    chartData: {
      type: Array,
      default: () => [],
    },
  },
  computed: {
    xAxisData() {
      var arr = this.chartData.map((item) => {
        return item.sj;
      });
      arr.sort((a, b) => a * 1 - b * 1);
      return Array.from(new Set(arr));
    },
    todayData() {
      return this.filterChartData("today");
    },
    yesterdayData() {
      return this.filterChartData("yesterday");
    },
  },
  watch: {
    chartData() {
      this.getInOutLineChart();
    },
  },
  mounted() {},
  methods: {
    filterChartData(key) {
      const result = Array.from(new Array(this.xAxisData.length));
      result.forEach((item, index) => {
        let targetCitem = this.chartData.find(
          (citem) => citem.sj === this.xAxisData[index] && citem.type == key
        );
        result[index] = targetCitem?.num || targetCitem?.num == 0 || null;
      });
      return result;
    },
    async getInOutLineChart() {
      const chartBox = await this.$echarts.init(
        document.getElementById("inOutLineChart")
      );

      const { todayData, yesterdayData } = this;
      // y轴刻度
      let max = Math.max(
        ...[Math.max(...todayData), Math.max(...yesterdayData)]
      );
      const option = {
        legend: {
          orient: "horizontal",
          x: "center",
          y: "bottom",
          data: ["今日", "昨日"],
          padding: 3,
          textStyle: {
            color: "#A0BDE0",
          },
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985",
            },
          },
        },
        grid: {
          top: "10%",
          left: "1%",
          right: "1%",
          bottom: "13%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: this.xAxisData,
          color: "#A0BDE0",
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: ["#A0BDE0"],
            },
          },
        },
        yAxis: {
          type: "value",
          min: 0, // 配置 Y 轴刻度最小值
          max: max + 20, // 配置 Y 轴刻度最大值
          splitNumber: max / 4, // 配置 Y 轴数值间隔
          interval: Math.ceil(max / 6), // 强制设置分隔
          color: "#A0BDE0",
          splitLine: {
            lineStyle: {
              type: "dotted",
              color: ["#A0BDE0"],
            },
          },
          axisLabel: {
            color: "#A0BDE0",
          },
        },

        series: [
          // 多组折线图数据
          {
            name: "今日",
            data: todayData,
            type: "line",
            showSymbol: false, // 圆点
            smooth: true, // 弧线
            areaStyle: {
              normal: {
                color: {
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0.1,
                      color: "rgba(0, 213, 255, 0.9)", // 0% 处的颜色
                    },
                    {
                      offset: 0.5,
                      color: "rgba(0, 213, 255, 0.6)", // 0% 处的颜色
                    },
                    {
                      offset: 0.7,
                      color: "rgba(0, 213, 255, 0.3)", // 0% 处的颜色
                    },
                    {
                      offset: 0.9,
                      color: "rgba(0, 213, 255, 0.1)", // 100% 处的颜色
                    },
                  ],
                  globalCoord: false, // 缺省为 false
                },
              },
            },
          },

          {
            name: "昨日",
            data: yesterdayData,
            type: "line",
            showSymbol: false, // 圆点
            smooth: true, // 弧线
            areaStyle: {
              normal: {
                color: {
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0.1,
                      color: "rgba(255, 178, 78, 0.9)", // 0% 处的颜色
                    },
                    {
                      offset: 0.5,
                      color: "rgba(255, 178, 78, 0.6)", // 0% 处的颜色
                    },
                    {
                      offset: 0.7,
                      color: "rgba(255, 178, 78, 0.3)", // 0% 处的颜色
                    },
                    {
                      offset: 0.9,
                      color: "rgba(255, 178, 78, 0.1)", // 100% 处的颜色
                    },
                  ],
                  globalCoord: false, // 缺省为 false
                },
              },
            },
          },
        ],
        color: ["#00D5FF", "#FFB24E"],
      };

      await chartBox.setOption(option);
    },
  },
};
</script>
<style lang="scss" scoped>
#inOutLineChart {
  width: 427px;
  height: 248px;
  margin-bottom: 24px;
}
</style>
