<template>
  <div class="LineChart">
    <div class="title">
      <i class="round"></i>
      <span class="unit">单位：宗</span>
    </div>

    <div id="myChart" class="EC"></div>
  </div>
</template>

<script>
export default {
  name: "SecurityoniMtoringLineChart",

  components: {},

  data() {
    return {};
  },

  methods: {
    async Echarts() {
      
      const chartBox = await this.$echarts.init(document.getElementById("myChart"));

      const option = {
        tooltip: {
          // 悬浮提示框设置
          backgroundColor: "rgba(0, 0, 0,.8)",
          textStyle: {
            color: "#fff",
          },

          show: true,

          trigger: "axis", // 移到内部点才有显示 item默认
          axisPointer: {
            //type: "cross",
            lineStyle: {
              //线的颜色
              color: "#1BDDD5",
              width: 1.5,
            },
          },
        },

        // 设置距离
        grid: {
          top: "10%",
          left: "1%",
          right: "1%",
          bottom: "13%",
          containLabel: true,
        },

        xAxis: {
          // X轴
          type: "category", // 类目轴

          data: ["2月", "3月", "4月", "5月", "6月", "7月"],

          boundaryGap: false,

          color: "#A0BDE0",

          axisLine: {
            // X轴线字颜色
            lineStyle: {
              color: "#A0BDE0",
            },
          },

          axisLabel: {
            // X轴线字体大小设置
            fontSize: 12,
          },
        },

        yAxis: {
          // Y轴
          type: "value", // 数字轴
          color: "#A0BDE0",

          splitLine: {
            //虚线设置
            lineStyle: {
              type: "dotted",
              color: "#A0BDE0",
            },
          },

          axisLine: {
            lineStyle: {
              // Y轴线字颜色
              color: "#A0BDE0",
            },
          },
        },

        series: [
          // 系列列表  X轴对应Y轴的数值在这!
          {
            name: "宗",

            type: "line", // 图表显示的类型
            showSymbol: false, // 圆点

            data: [426, 348, 416, 423, 580, 502],

            smooth: true, // 开启折线的平滑 弧线

            symbolSize: 10, // 标记的大小轴上的点

            areaStyle: {
              // 颜色设置
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

            lineStyle: {
              // 设置线条的颜色
              color: "#437df6",
            },
          },
        ],

      };

      await chartBox.setOption(option);

      window.addEventListener("resize", () => {
        chartBox.resize();
      });
    },
  },

  created() {},

  mounted() {
    this.Echarts();
  },
};
</script>

<style lang="scss" scoped>
.title {
  margin: 10px 0 8px 0;
  .round {
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    margin-right: 10px;
    background-color: #3d94ff;
  }

  .unit {
    font-size: 15px;
  }
}

.EC {
  width: 428px;
  height: 148px;
}
</style>
