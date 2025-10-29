<template>
  <div>
    <div class="title">
      <i class="round"></i>
      <span class="unit">单位：宗</span>
    </div>

    <div class="Pie">
      <div id="myChart5" class="EC"></div>

      <div class="block">
        <div class="title" v-for="(item, index) in Data" :key="index">
          <i class="round" :style="color[index]"></i>
          <span class="unit">{{ item.name }}</span>
          <span class="unit">{{ item.count }}</span>
          <span class="unit">{{ item.persent }}%</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { GetEventGroup } from "@/utils/api/BusinessCircleEvent";

export default {
  name: "BusinessCircleEventPie",

  components: {},

  data() {
    return {
      myChart5: null,
      Data: [],
      Total: 0,
      color: [
        "background-color: #00d5ff",
        "background-color: #1bddd5",
        "background-color: #ffa076",
        "background-color: #1bddd5",
        "background-color: #3d94ff",
      ],
    };
  },

  methods: {
    Echarts(textNum, cDataNum) {
      if (!this.myChart5) {
        this.myChart5 = this.$echarts.init(document.getElementById("myChart5"));
      }
      var chartData = [];
      for (var i = 0; i < cDataNum.length; ++i) {
        console.log(cDataNum[i]);
        let item = {
          value: cDataNum[i].count,
          name: cDataNum[i].name,
        };
        chartData.push(item);
      }

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

      this.myChart5.setOption({
        title: {
          //文字
          text: textNum, //总数  --------------------------------
          subtext: "工单总数",
          x: "46%",
          y: "33%",
          itemGap: 10,

          textStyle: {
            color: "#00D5FF",
            fontSize: 16,
            fontWeight: "bold",
          },

          subtextStyle: {
            color: "#ffffff",
            fontSize: 14,
            fontWeight: "lighter",
          },

          textAlign: "center",
        },

        tooltip: {
          //提示框
          show: true,
          backgroundColor: "rgba(0, 0, 0,.8)",
          position: ["50%", "50%"],

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
            radius: ["75%", "85%"],
            center: ["50%", "50%"],

            label: {
              show: false,
            },

            labelLine: {
              show: false,
            },

            data: pieData1,
          },

          {
            //虚线圆
            type: "gauge",
            zlevel: 2,
            splitNumber: 40,
            radius: "90%",
            center: ["50%", "50%"],
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
                color: "#00D5FF",
              },
            },
            pointer: {
              show: 0,
            },
            detail: {
              show: 0,
            },
          },

          {
            //背景圆
            type: "pie",
            center: ["50%", "50%"],
            radius: [0, "60%"],
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
        this.myChart5.resize();
      });
    },

    getEventGroup(timeDate = "2023-08") {
      let that = this;
      GetEventGroup({
        time: timeDate,
      }).then((res) => {
        that.Data = res.data.data;
        that.Total = res.data.total;
        //总数
        that.Echarts(this.Total, res.data.data);
      });
    },
  },

  created() {},

  mounted() {
    //this.$nextTick(() => {});
  },
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
.Pie {
  display: flex;
}
.EC {
  width: 150px;
  height: 150px;
  position: relative;
  top: -5px;
}

.block {
  margin-left: 24px;

  .title {
    margin: 10px 0 8px 0;
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
      width: 65px;
    }
    .unit:nth-child(3) {
      margin-left: 30px;
      width: 50px;
      text-align: right;
    }

    .unit:nth-child(4) {
      margin-left: 35px;
      text-align: right;
      width: 50px;
    }
  }
  .title:last-child {
    margin-bottom: 0;
  }
}
</style>
