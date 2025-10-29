<script setup>
import { ref, onMounted } from "vue";
import * as echarts from "echarts";
import {
  labelStyleColor,
  labelStyleSize,
  labelStyleSizeS,
  labelStyleWeight
} from "@/utils/jc-echarts/EchartsConfig";
import axios from "axios";

let curMonth = ref("");
let months = ref([]);
onMounted(() => {
  $axios_get(IP + "/screen/api/assess/getCycle").then(res => {
    months.value = res.data.data;
    curMonth.value = res.data.data[0];
    getData();
  });
});
let curChart = ref("板块");
let xData = [],
  seriesData1 = [],
  seriesData2 = [];
function loadBJL() {
  let seriesData3 = xData.map((item, index) => {
    return Math.round((seriesData2[index] / seriesData1[index]) * 100);
  });

  let option = {
    tooltip: {
      trigger: "axis",
      axisPointer: {
        lineStyle: {
          color: "#ddd"
        }
      },
      backgroundColor: "rgba(17,95,182,0.8)",
      textStyle: {
        color: "#fff"
      }
    },
    grid: {
      left: "3%",
      right: "3%",
      top: "20%",
      bottom: "3%",
      containLabel: true
    },
    legend: {
      left: "center",
      top: "top",
      itemGap: 25,
      textStyle: {
        color: "#fff",
        fontSize: 12
      }
    },
    xAxis: {
      type: "category",
      //axisTick 坐标轴刻度相关设置
      axisTick: {
        show: false
      },
      //axixLine 坐标轴轴线相关设置
      axisLine: {
        lineStyle: {
          color: "#FFFFFF"
        }
      },
      data: xData,
      //axisLabel 坐标轴刻度标签的相关设置
      axisLabel: {
        // show: true,
        color: "#fff",
        fontSize: 12
      }
    },
    yAxis: [
      {
        name: "数量",
        type: "value",
        axisLabel: {
          show: true,
          color: "#fff",
          fontSize: 12,
          formatter: "{value}"
        },
        splitLine: {
          show: false
        },
        axisLine: {
          lineStyle: {
            color: "#D9D9D9"
          }
        },
        axisTick: {
          // show: false
        }
      },
      {
        name: "百分比（%）",
        position: "right",
        max: 100,
        axisLabel: {
          show: true,
          color: "#fff",
          fontSize: 16
        },
        splitLine: {
          show: false
        },
        axisLine: {
          lineStyle: {
            color: "#D9D9D9"
          }
        },
        axisTick: {
          // show: false
        }
      }
    ],
    series: [
      {
        name: "工单总数",
        //type决定图表类型
        type: "bar",
        barWidth: "16%",
        data: seriesData1,
        itemStyle: {
          color: function (val) {
            return new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: "#0085FF"
              },
              {
                offset: 1,
                color: "#ffffff00"
              }
            ]);
          }
        },
        label: {
          show: true,
          position: "inside",
          color: labelStyleColor,
          fontSize: labelStyleSizeS,
          fontWeight: labelStyleWeight
        }
      },
      {
        name: "办结总数",
        //type决定图表类型
        type: "bar",
        barWidth: "16%",
        data: seriesData2,
        itemStyle: {
          color: function (val) {
            return new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {
                offset: 0,
                color: "#30E6FF"
              },
              {
                offset: 1,
                color: "#ffffff00"
              }
            ]);
          }
        },
        label: {
          show: true,
          position: "inside",
          offset: [0, 10],
          color: labelStyleColor,
          fontSize: labelStyleSizeS,
          fontWeight: labelStyleWeight
        }
      },
      {
        name: "办结率",
        //type决定图表类型
        type: "line",
        yAxisIndex: 1,
        data: seriesData3,
        label: {
          show: true,
          position: "top",
          color: labelStyleColor,
          fontSize: labelStyleSizeS,
          fontWeight: labelStyleWeight
        }
      }
    ]
  };
  var chartDom = document.getElementById("quzgfx");
  echarts.dispose(chartDom);
  var myChart = echarts.init(chartDom);
  myChart.setOption(option);
}
function getData() {
  $axios_get(IP + "/screen/api/count/highRegion", {
      params: {
        type: curChart.value == "板块" ? 1 : 2,
        cycle: curMonth.value
      }
    })
    .then(result => {
      let res = result.data.data;
      xData = [];
      seriesData1 = [];
      seriesData2 = [];
      Object.keys(res).forEach(key => {
        xData.push(key);
        seriesData1.push(res[key]["工单总数"]);
        seriesData2.push(res[key]["办结总数"]);
      });
      loadBJL();
    });
}
</script>
<template>
  <div class="panel">
    <div class="switch-quzgfx">
      <span
        :class="{ active: curChart == '板块' }"
        @click="
          curChart = '板块';
          getData();
        "
      >
        板块
      </span>
      <span
        :class="{ active: curChart == '职能部门' }"
        @click="
          curChart = '职能部门';
          getData();
        "
      >
        职能部门
      </span>
    </div>
    <div id="quzgfx"></div>
    <el-select class="month-select" v-model="curMonth" @change="getData">
      <el-option
        v-for="(i, index) in months"
        :key="index"
        :label="i"
        :value="i"
      ></el-option>
    </el-select>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #quzgfx {
    width: 100%;
    height: 172px;
  }
  .switch-quzgfx {
    text-align: center;
    color: #fff;
    margin: -15px 0 10px 0;
    span {
      padding: 5px 35px;
    }
    .active {
      background: #256ddc;
    }
  }
}
.month-select {
  position: absolute;
  right: 20px;
  top: 50px;
  width: 120px;
  background: #063478;
  color: #fff;
  ::v-deep(.el-input__wrapper) {
    background-color: #053475eb !important;
    height: 35px;
    font-size: 16px;
  }

  ::v-deep(.el-input__inner) {
    color: #fff !important;
  }
}
</style>