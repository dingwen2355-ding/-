<script setup>
import { ref, onMounted, watch } from "vue";
import * as echarts from "echarts";
import {
  colors,
  lineStyleColor,
  legendStyleSize,
  legendStyleColor,
  legendStyleWeight,
  lineStyleWidth,
  labelStyleColor,
  labelStyleSize,
  elineSymbolSize,
  elineSymbol,
  labelStyleWeight
} from "@/utils/jc-echarts/EchartsConfig";
import axios from "axios";
import { useStore } from "@/store/index";

let store = useStore();

watch(
  () => store.indexMonth,
  (newVal, oldVal) => {
    getData();
  }
);

let datas = [];
onMounted(() => {
  getData();
});
function getData() {
  $axios_get(IP + "/screen/api/assess/assessList", {
      params: { month: store.indexMonth }
    }).then(res => {
    datas = res.data.data.list.map(item => {
      return {
        name: item.regionName,
        value: item.score
      };
    });
    loadChart();
  });
}
function loadChart() {
  let maxArr = new Array(datas.length).fill(100);
  var option = {
    grid: {
      left: "3%",
      right: "3%",
      bottom: "3%",
      top: "3%",
      containLabel: true
    },
    xAxis: {
      show: false,
      type: "value"
    },
    splitLine: {
      show: false
    },
    yAxis: [
      {
        type: "category",
        inverse: true,
        axisLabel: {
          show: true,
          align: "left",
          padding: [0, 0, 0, -100],
          textStyle: {
            color: labelStyleColor,
            fontSize: labelStyleSize,
            fontWeight: labelStyleWeight
          },
          rich: {
            a1: {
              width: 32,
              height: 28,
              align: "center",
              backgroundColor: {
                image: new URL("@/assets/img/top0.png", import.meta.url).href
              }
            },
            a2: {
              backgroundColor: {
                image: new URL("@/assets/img/top1.png", import.meta.url).href
              },
              width: 32,
              height: 28,
              align: "center"
            },
            a3: {
              backgroundColor: {
                image: new URL("@/assets/img/top2.png", import.meta.url).href
              },
              fontSize: labelStyleSize,
              width: 32,
              height: 28,
              align: "center"
            },
            a4: {
              backgroundColor: {
                image: new URL("@/assets/img/top3.png", import.meta.url).href
              },
              fontSize: labelStyleSize,
              width: 32,
              height: 28,
              align: "center"
            }
          },
          formatter: function (params) {
            let index = datas.map(item => item.name).indexOf(params);
            index = index + 1;
            if (index == 1) {
              return "{a1|" + index + "}" + " " + params;
            } else if (index == 2) {
              return "{a2|" + index + "}" + " " + params;
            } else if (index == 3) {
              return "{a3|" + index + "}" + " " + params;
            } else {
              return "{a4|" + index + "}" + " " + params;
            }
          }
        },
        splitLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLine: {
          show: false
        },
        data: datas.map(item => item.name)
      },
      {
        type: "category",
        inverse: true,
        axisTick: "none",
        axisLine: "none",
        show: true,
        axisLabel: {
          textStyle: {
            color: "#ffffff",
            fontSize: labelStyleSize
          },
          formatter: function (value) {
            if (value >= 10000) {
              return (value / 10000).toLocaleString() + "万";
            } else {
              return value.toLocaleString();
            }
          }
        },
        data: datas.map(item => item.value)
      }
    ],
    series: [
      {
        name: "文明程度指数",
        type: "bar",
        zlevel: 1,
        itemStyle: {
          normal: {
            barBorderRadius: 30,
            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "rgb(57,89,255,1)"
              },
              {
                offset: 1,
                color: "rgb(46,200,207,1)"
              }
            ])
          }
        },
        barWidth: 10,
        data: datas.map(item => item.value)
      },
      {
        name: "背景",
        type: "bar",
        barWidth: 10,
        barGap: "-100%",
        data: maxArr,
        itemStyle: {
          normal: {
            color: "rgba(24,31,68,1)",
            barBorderRadius: 30
          }
        }
      }
    ]
  };
  echarts.dispose(document.getElementById("chuangyoupaiming"));
  var chartDom = document.getElementById("chuangyoupaiming");
  var myChart = echarts.init(chartDom);
  myChart.setOption(option);
}
</script>
<template>
  <div id="chuangyoupaiming"></div>
</template>
<style scoped lang="scss">
#chuangyoupaiming {
  width: 100%;
  height: 430px;
}
</style>