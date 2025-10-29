<!-- 党员年龄构成 -->
<template>
  <div class="Pie">
    <div id="myPie" class="EC"></div>

    <div class="block">
      <div class="title">
        <i class="round" style="background-color: #00D5FF;"></i>
        <span class="unit">18-29岁</span>
        <span class="unit">{{ AgeData[0].count }}</span>
        <span class="unit">{{ percentage(AgeData[0].persent) }}</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #1BDDD5;"></i>
        <span class="unit">30-39岁</span>
        <span class="unit">{{ AgeData[1].count }}</span>
        <span class="unit">{{ percentage(AgeData[1].persent) }}</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #FFA076;"></i>
        <span class="unit">40-49岁</span>
        <span class="unit">{{ AgeData[2].count }}</span>
        <span class="unit">{{ percentage(AgeData[2].persent) }}</span>
      </div>

      <div class="title">
        <i class="round" style="background-color: #1BDDD5;"></i>
        <span class="unit">50-59岁</span>
        <span class="unit">{{ AgeData[3].count }}</span>
        <span class="unit">{{ percentage(AgeData[3].persent) }}</span>
      </div>

      <div class="title">
        <i class="round"></i>
        <span class="unit">60岁以上</span>
        <span class="unit">{{ AgeData[4].count }}</span>
        <span class="unit">{{ percentage(AgeData[4].persent) }}</span>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "partyBuildingPie",

  components: {},

  data() {
    return {
      MyPie: null,
      AgeData: [
        { count: 0 },
        { count: 0 },
        { count: 0 },
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
      if (!this.MyPie) {
        this.MyPie = this.$echarts.init(document.getElementById("myPie"));
      }

      const chartData = [
        {
          value: this.AgeData[0].count,
          name: "18-29岁",
        },

        {
          value: this.AgeData[1].count,
          name: "30-39岁",
        },

        {
          value: this.AgeData[2].count,
          name: "40-49岁",
        },

        {
          value: this.AgeData[3].count,
          name: "50-59岁",
        },

        {
          value: this.AgeData[4].count,
          name: "60岁以上",
        },
      ];

      const colorList = ["#00D5FF", "#1BDDD5", "#FFA076", "#1BDDD5", "#3D94FF"];

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

      this.MyPie.setOption({
        title: {
          //文字
          text: this.TotalCount,
          subtext: "在职人数",
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
        this.MyPie.resize();
      });
    },
  },

  created() {},

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
