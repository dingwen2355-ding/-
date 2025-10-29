<template>
  <div id="trafficLineChart"></div>
</template>
<script>
export default {
  props: {
    chartData: {
      type: Array,
      default: () => [],
    },
  },
  watch: {
    chartData() {
      this.getTrafficLineChart();
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
    lData() {
      return this.filterChartData("bd");
    },
    fData() {
      return this.filterChartData("wd");
    },
  },
  methods: {
    filterChartData(key) {
      const result = Array.from(new Array(this.xAxisData.length));
      result.forEach((item, index) => {
        let targetCitem = this.chartData.find(
          (citem) => citem.sj === this.xAxisData[index] && citem.type == key
        );
        result[index] = targetCitem?.num || null;
      });
      return result;
    },
    async getTrafficLineChart() {
      const chartBox = await this.$echarts.init(
        document.getElementById("trafficLineChart")
      );

      let { lData, fData } = this;
      // y轴刻度
      let max = Math.max(...[Math.max(...lData), Math.max(...fData)]);
      const option = {
        title: {
          text: "单位：车次",
          x: "left",
          y: "top",
          textStyle: {
            color: "rgb(160, 189, 224)",
            fontFamily: "PingFang SC",
            fontSize: 14,
            fontWeight: "400",
            lineHeight: 15,
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
        legend: {
          orient: "horizontal",
          x: "center",
          y: "bottom",
          data: ["本地车牌", "外地车牌"],
          padding: 3,
          textStyle: {
            color: "#A0BDE0",
          },
        },
        grid: {
          top: "18%",
          left: "1%",
          right: "1%",
          bottom: "13%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          data: this.xAxisData,
          color: "#A0BDE0",
          boundaryGap: false, // 不留白 从0开始
          axisLine: {
            lineStyle: {
              color: ["#A0BDE0"],
            },
          },
        },
        yAxis: {
          type: "value",
          min: 0, // 配置 Y 轴刻度最小值
          max: max + 30, // 配置 Y 轴刻度最大值
          splitNumber: Math.ceil(max / 6), // 配置 Y 轴数值间隔
          interval: Math.ceil(max / 5), // 强制设置分隔
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
            name: "本地车牌",
            data: lData,
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
                      color: "rgba(61, 148, 255, 0.9)", // 0% 处的颜色
                    },
                    {
                      offset: 0.5,
                      color: "rgba(61, 148, 255, 0.6)", // 0% 处的颜色
                    },
                    {
                      offset: 0.7,
                      color: "rgba(61, 148, 255, 0.3)", // 0% 处的颜色
                    },
                    {
                      offset: 0.9,
                      color: "rgba(61, 148, 255, 0.1)", // 100% 处的颜色
                    },
                  ],
                  globalCoord: false, // 缺省为 false
                },
              },
            },
          },

          {
            name: "外地车牌",
            data: fData,
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
                      color: "rgba(27, 192, 120, 0.9)", // 0% 处的颜色
                    },
                    {
                      offset: 0.5,
                      color: "rgba(27, 192, 120, 0.6)", // 0% 处的颜色
                    },
                    {
                      offset: 0.7,
                      color: "rgba(27, 192, 120, 0.3)", // 0% 处的颜色
                    },
                    {
                      offset: 0.9,
                      color: "rgba(27, 192, 120, 0.1)", // 100% 处的颜色
                    },
                  ],
                  globalCoord: false, // 缺省为 false
                },
              },
            },
          },
        ],
        color: ["#3D94FF", "#1BC078"],
      };

      await chartBox.setOption(option);
    },
  },
};
</script>
<style lang="scss" scoped>
#trafficLineChart {
  width: 427px;
  height: 248px;
  margin-top: 10px;
  margin-bottom: 24px;
}
</style>
