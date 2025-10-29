<script setup>
import { ref, onMounted } from "vue";
import * as echarts from "echarts";
import TopBottom from "../display/top-bottom.vue";
import axios from "axios";
import pinia from "@/store/store";
import { useStore } from "@/store/index";

const store = useStore(pinia);

let datas = ref({
  score: 77.54,
  lastScore: 74.67
});
let curMonth = ref("2023-07");
let months = ref([]);
onMounted(() => {
  $axios_get(IP + "/screen/api/assess/getMonth").then(res => {
    months.value = res.data.data;
    curMonth.value = res.data.data[0];
    changeMonth();
  });
});
function getData() {
  $axios_get(IP + "/screen/api/assess/assessList", {
      params: {
        month: curMonth.value
      }
    })
    .then(res => {
      res.data.data.lastScore = res.data.data.lastScore || 0;
      datas.value = res.data.data;
    });
}
function changeMonth() {
  store.$patch({
    indexMonth: curMonth.value
  });
  getData();
}
</script>
<template>
  <div class="panel flex-row">
    <div class="left flex-row">
      <TopBottom class="item">
        <template #t>
          <span class="large-value">{{ datas.score }}</span>
        </template>
        <template #t-s> </template>
        <template #b>
          <div class="mid-name">良好</div>
        </template>
      </TopBottom>
      <div class="gap"></div>
      <TopBottom class="item">
        <template #t>
          <span class="large-value">{{
            (datas.score - datas.lastScore).toFixed(2)
          }}</span>
        </template>
        <template #t-s>
          <span class="small-value">{{
            datas.score - datas.lastScore > 0
              ? "↑"
              : datas.score - datas.lastScore < 0
              ? "↓"
              : ""
          }}</span>
        </template>
        <template #b>
          <div class="mid-name">较上月</div>
        </template>
      </TopBottom>
    </div>
  </div>

  <el-select class="month-select" v-model="curMonth" @change="changeMonth">
    <el-option
      v-for="(i, index) in months"
      :key="index"
      :label="i"
      :value="i"
    ></el-option>
  </el-select>
</template>
<style scoped lang="scss">
.panel {
  .left {
    width: 432px;
    height: 78px;
    background: url("@/assets/img/quxiantu.png") no-repeat;
    background-size: cover;
    background-position-y: -95px;
    padding-top: 90px;

    .item {
      width: 50%;
      height: 60px;
    }
  }

  #wenmindefen,
  .right {
    width: 182px;
    height: 182px;
    background: url("@/assets/img/pie.png") no-repeat;
    background-size: cover;
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