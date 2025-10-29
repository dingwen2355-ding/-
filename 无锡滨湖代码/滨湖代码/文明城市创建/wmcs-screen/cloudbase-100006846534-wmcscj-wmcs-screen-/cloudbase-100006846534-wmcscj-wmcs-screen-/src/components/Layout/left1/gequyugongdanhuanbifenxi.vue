<script setup>
import { ref, onMounted } from "vue";
import * as echarts from "echarts";
import { EchartsBarRow } from "@/utils/jc-echarts/EchartsBarRow";
import axios from "axios";

let curMonth = ref("");
let months = ref([]);
onMounted(() => {
  months.value = getLastSixMon();
  curMonth.value = months.value[0];
  getData();
});
function getLastSixMon() {
  let data = new Date(); //获取年
  let year = data.getFullYear(); //获取月
  let mon = data.getMonth() + 1;
  let arry = new Array();
  for (let i = 0; i < 6; i++) {
    if (mon <= 0) {
      year = year - 1;
      mon = mon + 12;
    }
    if (mon < 10) {
      mon = "0" + mon;
    }
    arry[i] = year + "-" + mon;
    mon = mon - 1;
  }
  return arry;
}
let xData = [],
  seriesData1 = [],
  seriesData2 = [];
let curChart = ref("板块");
function loadBJL() {
  let chart = EchartsBarRow({
    autoplay: true,
    el: "gqygdhbfx",
    padding: ["15%", "3%", "1%", "0"],
    legend: {
      show: true,
      data: ["同比", "环比"]
    },
    x: {
      show: true,
      data: xData
    },
    y: {
      show: true
    },
    series: [
      {
        name: "同比",
        barWidth: "20%",
        normalColor: true,
        label: {
          show: false
        },
        data: seriesData1
      },
      {
        name: "环比",
        barWidth: "20%",
        normalColor: true,
        label: {
          show: false
        },
        data: seriesData2
      }
    ]
  });
  chart.on("click", e => {
    console.log(e);
  });
  // var option = {
  //     grid: {
  //         left: "3%",
  //         top: "18%",
  //         right: "3%",
  //         bottom: "3%",
  //         containLabel: true,
  //     },
  //     color: ["#0085FF", "#1DF1FF"],
  //     legend: {
  //         icon: "roundRect",
  //         data: ["同比", "环比"],
  //         textStyle: {
  //             color: "#fff",
  //             fontSize: 16,
  //             fontWeight: "700"
  //         },
  //     },
  //     xAxis: [
  //         {
  //             type: "category",
  //             nameLocation: "center",
  //             nameGap: 35,
  //             axisLabel: {
  //                 textStyle: {
  //                     color: "#fff",
  //                     fontSize: 16,
  //                 },
  //             },
  //             data: [
  //                 "河埒街道", "荣巷街道", "蠡湖街道", "蠡园街道", "马山街道", "雪浪街道", "胡埭镇",
  //             ],
  //             axisLine: {
  //                 lineStyle: {
  //                     width: 2,
  //                     color: "rgba(63,92,122,1)",
  //                 },
  //             },
  //             axisTick: {
  //                 show: false,
  //             },
  //         },
  //     ],
  //     yAxis: [
  //         {
  //             type: "value",
  //             axisLabel: {
  //                 textStyle: {
  //                     color: "#fff",
  //                     fontSize: 16,
  //                 },
  //             },
  //             axisTick: {
  //                 show: false,
  //             },
  //             axisLine: {
  //                 lineStyle: {
  //                     width: 2,
  //                     color: "rgba(160,160,160,0.3)",
  //                 },
  //             },
  //             splitLine: {
  //                 lineStyle: {
  //                     color: "rgba(160,160,160,0.3)",
  //                 },
  //             },
  //         },
  //     ],
  //     series: [
  //         {
  //             type: "bar",
  //             barWidth: "20%",
  //             itemStyle: {
  //                 color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
  //                     {
  //                         offset: 0,
  //                         color: "#0085FF",
  //                     },
  //                     {
  //                         offset: 1,
  //                         color: "#ffffff00",
  //                     },
  //                 ])
  //             },
  //             label: {
  //                 normal: {
  //                     show: false,
  //                 },
  //             },
  //             data: [
  //                 20, 15, 12, 12, 16, 15, 15,
  //             ],
  //         },
  //         {
  //             type: "bar",
  //             barWidth: "20%",
  //             itemStyle: {
  //                 color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
  //                     {
  //                         offset: 0,
  //                         color: "#ffffff00",
  //                     },
  //                     {
  //                         offset: 1,
  //                         color: "#30E6FF",
  //                     },
  //                 ])
  //             },
  //             label: {
  //                 normal: {
  //                     show: false,
  //                 },
  //             },
  //             data: [
  //                 -20, -20, -20, -20, -20, -20, - 20,
  //             ],
  //         },
  //     ],
  // };

  // var chartDom = document.getElementById('gqygdhbfx');
  // var myChart = echarts.init(chartDom);
  // myChart.setOption(option);
}
function getData() {
  $axios_get(IP + "/screen/api/count/eventRatio", {
      params: { month: curMonth.value, type: curChart.value == "板块" ? 1 : 2 }
    })
    .then(result => {
      let res = result.data.data;
      xData = [];
      seriesData1 = [];
      seriesData2 = [];
      Object.keys(res).forEach(key => {
        xData.push(key);
        seriesData1.push(res[key]["同比"]);
        seriesData2.push(res[key]["环比"]);
      });
      loadBJL();
    });
}
</script>
<template>
  <div class="panel">
    <el-select class="month-select" v-model="curMonth" @change="getData">
      <el-option
        v-for="(i, index) in months"
        :key="index"
        :label="i"
        :value="i"
      ></el-option>
    </el-select>
    <div class="switch-gqygdhbfx">
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
    <div id="gqygdhbfx"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #gqygdhbfx {
    width: 100%;
    height: 172px;
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

  .switch-gqygdhbfx {
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
</style>