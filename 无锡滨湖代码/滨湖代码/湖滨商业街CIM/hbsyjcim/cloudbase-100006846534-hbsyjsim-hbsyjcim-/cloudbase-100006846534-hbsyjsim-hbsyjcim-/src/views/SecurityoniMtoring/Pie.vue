<template>
  <div class="Pie">
    <div id="myChart5" class="EC"></div>

    <div class="block">
      <div class="title">
        <i class="round" style="background-color: #3E3EDA;"></i>
        <span class="unit">积水</span>
        <span class="unit">88</span>
        <span class="unit">25.65%</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #5071FF;"></i>
        <span class="unit">违规撑雨</span>
        <span class="unit">6</span>
        <span class="unit">1.74%</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #00D5FF;"></i>
        <span class="unit">悬挂横幅</span>
        <span class="unit">13</span>
        <span class="unit">3.79%</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #1BDDD5;"></i>
        <span class="unit">机动车违停</span>
        <span class="unit">98</span>
        <span class="unit">28.57%</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #1BC078;"></i>
        <span class="unit">非机动车违停</span>
        <span class="unit">85</span>
        <span class="unit">24.78%</span>
      </div>

      <div class="title">
        <i class="round"></i>
        <span class="unit">机动车逆行</span>
        <span class="unit">53</span>
        <span class="unit">15.45%</span>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "SecurityoniMtoringPie",

  components: {},

  data() {
    return {};
  },

  methods: {
    Echarts() {
      let myChart5 = this.$echarts.init(document.getElementById("myChart5"));

      const chartData = [
        {
          value: 88,
          name: "积水",
        },

        {
          value: 6,
          name: "违规撑雨",
        },

        {
          value: 13,
          name: "悬挂横幅",
        },

        {
          value: 98,
          name: "机动车违停",
        },

        {
          value: 85,
          name: "非机动车违停",
        },

        {
          value: 53,
          name: "机动车逆行",
        },
      ];

      const colorList = ["#3E3EDA", "#5071FF", "#00D5FF", "#1BDDD5", "#1BC078", "#FFA076"];

      const sum = chartData.reduce((per, cur) => per + cur.value, 0);

      const gap = (1 * sum) / 100;

      const pieData1 = [];

      const gapData = {
        name: "",
        value: gap,
        itemStyle: {
          color: "transparent",
        },
      };

      for (let i = 0; i < chartData.length; i++) {
        // 第一圈数据
        pieData1.push({
          ...chartData[i],
          itemStyle: {
            borderRadius: 10,
          },
        });
        pieData1.push(gapData);
      }

      myChart5.setOption({
        title: {
          //文字
          text: "343",
          subtext: "事件总数",
          x: "46%",
          y: "33%",
          itemGap: 10,

          textStyle: {
            color: "#00D5FF",
            fontSize: 18,
            fontWeight: "bold",
          },

          subtextStyle: {
            color: "#ffffff",
            fontSize: 15,
            fontWeight: "lighter",
          },

          textAlign: "center",
        },

        tooltip: {
          //提示框
          show: true,
          backgroundColor: "rgba(0, 0, 0,.8)",

          textStyle: {
            color: "#fff",
          },
        },

        grid: {
          top: 30,
          right: 20,
          bottom: 10,
          left: 10,
        },

        color: colorList,

        series: [
          {
            name: "",
            type: "pie",
            roundCap: true,
            radius: ["82%", "95%"],
            center: ["50%", "50%"],

            label: {
              show: false,
            },

            labelLine: {
              show: false,
            },

            data: pieData1,
          },

          { //虚线圆
            type: 'gauge',
            zlevel: 2,
            splitNumber: 50,
            radius: '100%',
            center: ['50%', '50%'],
            startAngle: 90,
            endAngle: -269.9999,
            axisLine: {
                show: false,
            },
            axisTick: {
                show: false,
            },
            axisLabel: {
                show: false,
            },
            splitLine: {
                show: true,
                length: 2,
                lineStyle: {
                  width: 3,
                  color: '#00D5FF',
                },
            },
            pointer: {
                show: 0,
            },
            detail: {
                show: 0,
            },
          },

          { //背景圆
            type: "pie",
            center: ["50%", "50%"],
            radius: [0, "70%"],
            label: {
              show: false,
            },

            labelLine: {
              show: false,
            },

            itemStyle: {
              color: "rgba(0, 213, 255, 0.1)",
            },

            silent: true,
            data: [
              {
                value: 100,
                name: "",
              },
            ],
          },
        ],
      }); //图标控件自式响应式

      window.addEventListener("resize", () => {
        // console.log("改变");
        myChart5.resize();
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
.Pie {
  display: flex;
}
.EC {
  width: 150px;
  height: 150px;

  position: relative;
  top: 8px;
}

.block {
  margin-top: 8px;
  margin-left: 24px;
  height: 149px;
  line-height: 35px;

  .title {
    margin: 8px 0 8px 0;
    height: 15px;
    .round {
      display: inline-block;
      width: 8px;
      height: 8px;
      border-radius: 50%;
      margin-right: 8px;
      background-color: #3d94ff;
    }

    .unit {
      display: inline-block;
      font-size: 12px;
      height: 15px;
    }

    .unit:nth-child(2) {
      width: 80px;
    }
    .unit:nth-child(3) {
      margin-left: 30px;
      width: 40px;
    }

    .unit:nth-child(4) {
      margin-left: 30px;
    }
  }
  .title:last-child {
    margin-bottom: 0;
  }

}
</style>
