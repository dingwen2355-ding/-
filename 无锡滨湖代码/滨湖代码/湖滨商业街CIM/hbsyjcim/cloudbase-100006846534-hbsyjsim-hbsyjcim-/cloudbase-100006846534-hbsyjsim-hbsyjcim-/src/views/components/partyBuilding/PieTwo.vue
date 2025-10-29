<!-- 党员性别比例 -->
<template>
  <div class="Pie">
    <div id="myPieTwo" class="EC"></div>

    <div class="block">
      <div class="title">
        <i class="round" style="background-color: #3D94FF;"></i>
        <span class="unit">男</span>
        <span class="unit">{{ SexData[1].count }}</span>
        <span class="unit">{{ percentage(SexData[1].persent) }}</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #FFA076;"></i>
        <span class="unit">女</span>
        <span class="unit">{{ SexData[0].count }}</span>
        <span class="unit">{{ percentage(SexData[0].persent) }}</span>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "partyBuildingPieTwo",

  components: {},

  data() {
    return {
      MyPieTwo: null,
      SexData: [
        { count: 0 },
        { count: 0 }
      ],
      TotalCount: 0, // 总数
    };
  },

  methods: {
    percentage(persent) {
      return this.Percentages(persent)
    },

    Echarts() {
      if (!this.MyPieTwo) {
        this.MyPieTwo = this.$echarts.init(document.getElementById("myPieTwo"));
      }

      const chartData = [
        {
          value: this.SexData[1].count,
          name: "男",
        },

        {
          value: this.SexData[0].count,
          name: "女",
        },
      ];

      const colorList = ["#3D94FF", "#FFA076"];

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

      this.MyPieTwo.setOption({
        title: {
          //文字
          text: this.TotalCount,
          subtext: "在职人员",
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
          position: ['50%', '50%'],
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
      }); 

      //图标控件自式响应式
      window.addEventListener("resize", () => {
        // console.log("改变");
        this.MyPieTwo.resize();
      });
    },
  },

  created() {
  },

  mounted() {},
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
  left: 16px;
}

.block {
  margin-top: 4px;
  margin-left: 25px;
  height: 150px;
  line-height: 100px;

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
      width: 70px;
    }
    .unit:nth-child(3) {
      margin-left: 30px;
      width: 40px;
      text-align: right;
    }

    .unit:nth-child(4) {
      width: 50px;
      text-align: right;
      margin-left: 30px;
    }
  }
  .title:last-child {
    margin-bottom: 0;
  }

}
</style>
