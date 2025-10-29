<script setup>
import { ref, onMounted } from "vue";
import * as echarts from "echarts";
import { EchartsPie } from "@/utils/jc-echarts/EchartsPie";
import axios from "axios";
let seriesData = [];
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/companyCount").then(res => {
    Object.keys(res.data.data).forEach(key => {
      seriesData.push({
        name: key,
        value: res.data.data[key]
      });
    });
    loadBJL();
  });
});
function loadBJL() {
  EchartsPie({
    autoplay: true,
    el: "wmdw",
    padding: ["3%", "3%", "3%", "3%"],
    legend: {
      show: true,
      left: "56%", // 'left', 'center', 'right'
      top: "middle", // 'top','middle','bottom'
      orient: "vertical",
      gap: 10,
      icon: "roundRect" // 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
    },
    series: [
      {
        name: "文明单位",
        left: "-40%",
        radius: ["65%", "79%"],
        label: {
          show: false,
          position: "center"
        },
        data: seriesData
      }
    ]
  });
}
</script>
<template>
  <div class="panel">
    <img src="@/assets/img/animate_circle.png" alt="" />
    <div id="wmdw"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #wmdw {
    width: 100%;
    height: 172px;
  }
  img {
    position: absolute;
    top: 56px;
    left: 40px;
    width: 190px;
  }
}
</style>