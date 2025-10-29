<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import * as echarts from "echarts";
import "echarts-wordcloud";

let interval;
onUnmounted(() => {
  interval && clearInterval(interval);
});
onMounted(() => {
  loadBJL("板块");
});

let curChart = ref("板块");
function loadBJL(params) {
  curChart.value = params;

  let colors = ["#00F0FF80", "#FBD36D80", "#00A3FF80", "#fc845280"];
  let res,
    minVal = 0,
    maxVal = 0;
  if (params == "板块") {
    res = [
      { id: 1, name: "河埒街道", value: 1414 },
      { id: 2, name: "雪浪街道", value: 1207 },
      { id: 3, name: "荣巷街道", value: 1198 },
      { id: 4, name: "蠡湖街道", value: 989 },
      { id: 5, name: "蠡园街道", value: 966 }
    ];
    minVal = 882;
    maxVal = 1414;
  } else {
    res = [
      { id: 1, name: "区住房城乡建设局", value: 3534 },
      { id: 2, name: "区城管局", value: 1639 },
      { id: 3, name: "滨湖交警大队", value: 457 },
      { id: 4, name: "区工业和信息化局", value: 440 },
      { id: 5, name: "区市场监管局", value: 331 },
      { id: 6, name: "区公安分局", value: 205 },
      { id: 7, name: "区商务局", value: 51 }
    ];
    minVal = 51;
    maxVal = 3534;
  }
  let data = res.map((item, i) => {
    item.itemStyle = { color: colors[i % 4] };
    item.symbolSize =
      Math.round((item.value - minVal) / (maxVal - minVal)) * 25 + 30;
    return item;
  });
  let seriesData = res.map((item, i) => {
    item.itemStyle = { color: colors[i % 4] };
    item.symbolSize =
      Math.round((item.value - minVal) / (maxVal - minVal)) * 25 + 30;
    return item;
  });

  var option = {
    series: [
      {
        type: "graph",
        layout: "force",
        animation: false,
        data: seriesData,
        force: {
          // initLayout: 'circular'
          // gravity: 0
          repulsion: 100,
          edgeLength: 10
        },
        label: {
          show: true,
          color: "#fff",
          formatter: "{b}",
          fontWeight: "1000"
        },
        edges: []
      }
    ]
  };
  echarts.dispose(document.getElementById("ydgfqy"));
  let myChart = echarts.init(document.getElementById("ydgfqy"));
  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);

  let index = 0;
  interval && clearInterval(interval);
  interval = setInterval(function () {
    let temp = seriesData.shift();
    temp.id = Math.random() * 10;
    seriesData.push(temp);

    myChart.setOption({
      series: [
        {
          roam: true,
          data: seriesData
        }
      ]
    });
    index++;
  }, 1000);
}
</script>
<template>
  <div class="panel">
    <div class="switch-ydgfqy">
      <span :class="{ active: curChart == '板块' }" @click="loadBJL('板块')">
        板块
      </span>
      <span :class="{ active: curChart == '职能部门' }" @click="loadBJL('职能部门')">
        职能部门
      </span>
    </div>
    <div id="ydgfqy"></div>
  </div>
</template>
<style scoped lang="scss">
.panel {
  #ydgfqy {
    width: 100%;
    height: 172px;
  }

  .switch-ydgfqy {
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