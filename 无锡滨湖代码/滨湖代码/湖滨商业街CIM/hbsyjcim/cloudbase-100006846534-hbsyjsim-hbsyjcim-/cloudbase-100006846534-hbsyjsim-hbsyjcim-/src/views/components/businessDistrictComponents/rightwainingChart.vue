<template>
  <div id="warnChart"></div>
</template>
<script>
export default {
  props: {
    chartData: {
      type: Number,
      default: 100,
    },
  },
  data() {
    return {
      chartBox: null,
    };
  },
  watch: {
    chartData() {
      this.initWarnningChart();
    },
  },
  mounted() {
    this.initWarnningChart();
  },
  methods: {
    async initWarnningChart() {
      if (!this.chartBox) {
        var chartBox = await this.$echarts.init(
          document.getElementById("warnChart")
        );
        this.chartBox = chartBox;
      }
      const data = [
        {
          value: this.chartData,
          name: "普通",
          itemStyle: { color: "#00D5FF" },
        },
        { value: 0, name: "紧急", itemStyle: { color: "#FFBC94" } },
      ];
      const option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          orient: "vertical", // 朝向
          icon: "circle", // 图形
          x: "50%",
          y: "center",
          bottom: "0",
          itemHeight: 8,
          data: ["普通", "紧急"],
          formatter: (name) => {
            let value, proportion;
            for (let i = 0; i < data.length; i++) {
              if (data[i].name === name) {
                value = `${data[i].value}`;
                proportion =
                  Math.round((value / this.chartData) * 10000) / 100 + "%";
              }
            }
            const arr = [
              `{name|${name}}`,
              `{count|${value}}`,
              `{proportion|${proportion}}`,
            ];
            return arr.join("");
          },
          textStyle: {
            rich: {
              name: {
                fontSize: 15,
                width: 50,
                color: "#fff",
              },
              count: {
                fontSize: 18,
                width: 50,
                color: "#fff",
              },
              proportion: {
                color: "#00D5FF",
                fontSize: 18,
              },
            },
          },
        },
        series: [
          {
            name: "占比率",
            type: "pie",
            radius: ["48%", "70%"],
            center: ["30%", "50%"], // 位置
            data: data,
            itemStyle: {
              normal: {
                label: {
                  show: false,
                },
                labelLine: {
                  show: false,
                },
              },
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
          {
            // 配置中间文字样式
            z: 100,
            type: "gauge",
            radius: "-50%",
            center: ["30%", "50%"], // 需和type: 'pie'中的center一致
            pointer: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            detail: {
              // 详情
              offsetCenter: [-1, 15],
              fontSize: 18,
              color: "#3D94FF",
              formatter: ["{total|" + this.chartData + "} {unit|个}"].join(
                "\n"
              ),
              rich: {
                total: {
                  color: "#3D94FF",
                  fontSize: 18,
                },
                unit: {
                  color: "#A0BDE0",
                  fontSize: 15,
                },
              },
            },
            data: [
              {
                value: "263",
                name: "报警总数",
                title: {
                  show: true,
                  fontSize: 15,
                  color: "#fff",
                  offsetCenter: [0, "20%"],
                },
              },
            ],
          },
        ],
      };
      await this.chartBox.setOption(option);
    },
  },
};
</script>

<style lang="scss" scoped>
#warnChart {
  height: 188px;
  width: 426px;
}
</style>
