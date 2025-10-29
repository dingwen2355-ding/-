<script setup>
import { ref, onMounted } from "vue";
import { EchartsLine } from "@/utils/jc-echarts/EchartsLine";
import axios from "axios";

let xData = [],
  seriesData1 = [],
  seriesData2 = [];
onMounted(() => {
  $axios_get(IP + "/screen/api/count/eventEnd").then(res => {
    const result = res.data.data;
    Object.keys(result).forEach(key => {
      xData.unshift(key);
      seriesData1.unshift(result[key]["人工办结"]);
      seriesData2.unshift(result[key]["自动办结"]);
    });
    loadBJL();
  });
});
function loadBJL() {
  EchartsLine({
    autoplay: true,
    el: "gdbjfx",
    padding: ["18%", "3%", "3%", "3%"],
    legend: {
      show: true,
      data: ["人工办结", "自动办结"]
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
        name: "人工办结",
        data: seriesData1
      },
      {
        name: "自动办结",
        data: seriesData2
      }
    ]
  });
}
</script>
<template>
  <div class="panel">
    <div id="gdbjfx"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #gdbjfx {
    width: 100%;
    height: 172px;
  }
}
</style>