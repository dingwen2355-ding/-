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
import { GetEventTrends } from "@/utils/api/BusinessCircleEvent";
import { DataToData } from "@/utils/tool/DataToData";

export default {
  name: "BusinessCircleEventLineChart",

  components: {},

  data() {
    return {
      Data: [],
    };
  },

  methods: {
    async Echarts(newData) {
      const chartBox = await this.$echarts.init(
        document.getElementById("myChart")
      );

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

        legend: {
          orient: "horizontal",
          x: "center",
          y: "87%",
          data: ["上报数据", "处置数量"],
          padding: 4,
          textStyle: {
            color: "#A0BDE0",
          },
        },

        // 设置距离
        grid: {
          top: "8%",
          left: "1%",
          right: "1%",
          bottom: "13%",
          containLabel: true,
        },

        xAxis: {
          // X轴
          type: "category", // 类目轴

          data: newData.dateArray,

          //oundaryGap: false, // true是默认值 紧挨边缘 脱离0值

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
          min: newData.min,
          // min: 0,
          max: newData.max,

          splitNumber: 10, // 配置 Y 轴数值间隔
          interval: newData.interval, // 强制设置分隔
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
          {
            name: "上报数据",
            data: newData.zeroArray,
            type: "bar",
            barWidth: 5,
            showSymbol: false, // 圆点
            smooth: true, // 弧线
          },

          // 系列列表  X轴对应Y轴的数值在这!
          {
            name: "处置数量",

            type: "line", // 图表显示的类型
            showSymbol: false, // 圆点

            data: newData.oneArray,

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

                globalCoord: false,
              },
            },
          },
        ],

        color: ["#00D5FF", "#3d94ff"],
      };

      await chartBox.setOption(option);

      //图标控件自式响应式
      window.addEventListener("resize", () => {
        chartBox.resize();
      });
    },

    getEventTrends(timeNum = "2023-08") {
      var that = this;
      GetEventTrends({
        time: timeNum,
      }).then((res) => {
        console.log("-事件趋势-请求到接口了------");
        //0上报数量  1处置数量
        this.Data = res.data;

        var tool = new DataToData();
        var newData = tool.ToData(res.data);
        that.Echarts(newData);

        console.log(this.Data);
      });
    },



  },

  created() {
    this.getEventTrends();
  },

  mounted() {},
};
</script>

<style lang="scss" scoped>
.title {
  margin: 15px 0 8px 0;

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
