<script setup>
import { ref, onMounted } from "vue";
import { EchartsLine } from "@/utils/jc-echarts/EchartsLine";
import axios from "axios";

let xData = [],
  seriesData = [];
onMounted(() => {
  $axios_get(IP + "/screen/api/assess/assessTrend").then(res => {
    Object.keys(res.data.data).forEach(key => {
      xData.push(key);
      seriesData.push(res.data.data[key]);
    });
    loadBJL();
  });
});
function loadBJL() {
  EchartsLine({
    autoplay: true,
    el: "zsqsfx",
    padding: ["15%", "3%", "3%", "3%"],
    legend: {
      show: false,
      data: ["指数趋势"]
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
        name: "指数趋势",
        smooth: true,
        data: seriesData
      }
    ]
  });

  // let option = {
  //     color: color,
  //     grid: {
  //         left: "3%",
  //         top: "3%",
  //         right: "3%",
  //         bottom: "3%",
  //         containLabel: true,
  //     },
  //     xAxis: [
  //         {
  //             type: "category",
  //             boundaryGap: false,
  //             axisLabel: {
  //                 formatter: "{value}月",
  //                 textStyle: {
  //                     color: "#fff",
  //                 },
  //             },
  //             axisLine: {
  //                 show: false,
  //                 lineStyle: {
  //                     color: "#D9D9D9",
  //                 },
  //             },
  //             data: xAxisData,
  //         },
  //     ],
  //     yAxis: [
  //         {
  //             type: "value",
  //             axisLabel: {
  //                 textStyle: {
  //                     color: "#fff",
  //                 },
  //             },
  //             nameTextStyle: {
  //                 color: "#fff",
  //                 fontSize: 12,
  //                 lineHeight: 40,
  //             },
  //             splitLine: {
  //                 lineStyle: {
  //                     color: "#E9E9E950",
  //                 },
  //             },
  //             axisLine: {
  //                 show: false,
  //             },
  //             axisTick: {
  //                 show: false,
  //             },
  //         },
  //     ],
  //     series: [
  //         {
  //             name: "2018",
  //             type: "line",
  //             smooth: true,
  //             // showSymbol: false,/
  //             symbolSize: 8,
  //             zlevel: 3,
  //             lineStyle: {
  //                 normal: {
  //                     color: color[0],
  //                     shadowBlur: 3,
  //                     shadowColor: hexToRgba(color[0], 0.5),
  //                     shadowOffsetY: 8,
  //                 },
  //             },
  //             areaStyle: {
  //                 normal: {
  //                     color: new echarts.graphic.LinearGradient(
  //                         0,
  //                         0,
  //                         0,
  //                         1,
  //                         [
  //                             {
  //                                 offset: 0,
  //                                 color: hexToRgba(color[0], 0.3),
  //                             },
  //                             {
  //                                 offset: 1,
  //                                 color: hexToRgba(color[0], 0.1),
  //                             },
  //                         ],
  //                         false
  //                     ),
  //                     shadowColor: hexToRgba(color[0], 0.1),
  //                     shadowBlur: 10,
  //                 },
  //             },
  //             data: yAxisData1,
  //         },
  //     ],
  // };
  // var chartDom = document.getElementById('zsqsfx');
  // var myChart = echarts.init(chartDom);
  // myChart.setOption(option);
}
</script>
<template>
  <div class="panel">
    <div id="zsqsfx"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #zsqsfx {
    width: 100%;
    height: 172px;
  }
}
</style>