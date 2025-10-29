<script setup>
import { ref, onMounted } from "vue";
import { EchartsBarRow } from "@/utils/jc-echarts/EchartsBarRow";
import axios from "axios";
let xData = [];
let seriesData = [];
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/schoolCount").then(res => {
    Object.keys(res.data.data).forEach(key => {
      xData.push(key);
      seriesData.push(res.data.data[key]);
    });
    loadBJL();
  });
});
function loadBJL(params) {
  EchartsBarRow({
    autoplay: true,
    el: "wmxy",
    padding: ["15%", "5%", "3%", "3%"],
    legend: {
      show: false
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
        name: "文明校园",
        data: seriesData,
        label: {
          show: true
        }
      }
    ]
  });
}
</script>
<template>
  <div class="panel">
    <div id="wmxy"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #wmxy {
    width: 100%;
    height: 172px;
  }
}
</style>