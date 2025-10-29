<script setup>
import { ref, onMounted, watch } from "vue";
import { EchartsLine } from "@/utils/jc-echarts/EchartsLine";
import axios from "axios";
import { useStore } from "@/store/index";

let store = useStore();
watch(
  () => store.areaName,
  (newVal, oldVal) => {
    regionName = newVal;
    initPanel();
  }
);

let xData = [],
  seriesData = [];
let regionName = "";
onMounted(() => {
  initPanel();
});
function initPanel() {
 $axios_get(IP + "/screen/api/count/eventTrend", {
      params: { regionName: regionName }
    })
    .then(res => {
      xData = [];
      seriesData = [];
      res.data.data.forEach(item => {
        xData.unshift(item.type);
        seriesData.unshift(item.count);
      });
      loadBJL();
    });
}
function loadBJL() {
  EchartsLine({
    autoplay: true,
    el: "gdqsfx",
    padding: ["15%", "3%", "3%", "3%"],
    legend: {
      show: false,
      data: ["上报总数"]
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
        name: "上报总数",
        data: seriesData
      }
    ]
  });
}
</script>
<template>
  <div class="panel">
    <div id="gdqsfx"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #gdqsfx {
    width: 100%;
    height: 172px;
  }
}
</style>