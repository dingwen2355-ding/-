<template>
  <div id="deviceTypePieChart"></div>
</template>
<script>
export default {
  props: ["deviceData"],
  data() {
    return {
      chartBox: null,
    };
  },
  watch: {
    deviceData(val) {
      console.log(val);
      this.initDiveceChart();
    },
  },
  mounted() {
    // this.initDiveceChart();
  },
  methods: {
    async initDiveceChart() {
      if (!this.chartBox) {
        var chartBox = await this.$echarts.init(
          document.getElementById("deviceTypePieChart")
        );
        this.chartBox = chartBox;
      }
      const { total, data } = this.deviceData;

      const chartData = [
        {
          value: data[1]?.count || 0,
          name: "在线",
          itemStyle: { color: "#00D5FF" },
        },
        {
          value: data[0]?.count || 0,
          name: "离线",
          itemStyle: { color: "#FFBC94" },
        },
      ];
      const option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        legend: {
          orient: "vertical", // 朝向
          icon: "circle", // 图形
          x: "center",
          y: "bottom",
          bottom: "0",
          itemHeight: 8,
          data: ["在线", "离线"],
          formatter: (name) => {
            let value, proportion;
            for (let i = 0; i < data.length; i++) {
              if (data[i].name === name) {
                value = `${data[i].count}`;
                proportion = Math.round((value / total) * 10000) / 100 + "%";
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
            radius: ["38%", "60%"],
            center: ["50%", "40%"], // 位置
            data: chartData,
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
            center: ["50%", "40%"], // 需和type: 'pie'中的center一致
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
              formatter: ["{total|" + total + "} {unit|个}"].join("\n"),
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
                value: this.deviceData,
                name: "设备总数",
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
#deviceTypePieChart {
  width: 428px;
  height: 290px;
}
</style>
