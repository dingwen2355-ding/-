<script setup>
import { ref, onMounted, watch } from "vue";
import * as echarts from "echarts";
import { EchartsPie } from "@/utils/jc-echarts/EchartsPie";
import { useStore } from "@/store/index";
import axios from "axios";

let store = useStore();
watch(
  () => store.areaName,
  (newVal, oldVal) => {
    console.log(newVal);
  }
);
let data = ref([
  {
    value: 0,
    name: "日常巡查"
  }
]);
onMounted(() => {
  $axios_get(IP + "/screen/api/count/eventSource").then(res => {
    const match = {
      reportCount: "日常巡查",
      aiCount: "AI智能分析",
      specialCount: "专班",
      cityCount: "市级下发"
    };
    let seriesData = [];
    Object.keys(match).forEach(key => {
      seriesData.push({ name: match[key], value: res.data.data[key] });
    });
    data.value = seriesData;
    loadBJL();
  });
});

function loadBJL(params) {
  EchartsPie({
    autoplay: true,
    el: "gdlyfx",
    padding: ["3%", "3%", "3%", "3%"],
    legend: {
      show: true,
      left: "60%", // 'left', 'center', 'right'
      top: "middle", // 'top','middle','bottom'
      gap: 25,
      icon: "roundRect" // 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
    },
    series: [
      {
        name: "工单来源",
        left: "-40%",
        radius: ["65%", "75%"],
        label: {
          show: false,
          position: "center"
        },
        data: data.value
      }
    ]
  });
}
</script>
<template>
  <div class="panel">
    <img src="@/assets/img/animate_circle.png" alt="" />
    <div id="gdlyfx"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #gdlyfx {
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