<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import pinia from "@/store/store";
import { useStore } from "@/store/index";
const store = useStore(pinia);
const formatNumber = val => {
  const valTemp = val.toString().replace(/(\d)(?=(?:\d{3})+$)/g, "$1,");
  return valTemp;
};
let data = ref({
  todoCount: 0,
  finishCount: 0
});
let bottom = ref({
  "eventCount": 7798,
  "pointsTypeCount": 36,
  "pointsCount": 882,
  "eventPointsCount": 882
});
onMounted(() => {
  $axios_get(IP + "/screen/api/index/today").then(res => {
    data.value = res.data.data;
  });
  $axios_get(IP + "/screen/api/index/dataCount").then(res => {
    bottom.value = res.data.data;
  });
});
function changeJRDB() {
  store.$patch({
    jrdb: !store.jrdb
  });
}
function changeJRBJ() {
  store.$patch({
    jrbj: !store.jrbj
  });
}
</script>
<template>
  <div class="top">
    <div class="item" @click="changeJRDB()">
      <img src="@/assets/img/daiban.png" alt="" />
      <div class="text" :style="{ color: store.jrdb ? '#00F0FF' : '#fff' }">
        今日待办事件<span>{{ formatNumber(data.todoCount) }}</span>件
      </div>
    </div>
    <div class="item" @click="changeJRBJ()">
      <img src="@/assets/img/banjie.png" alt="" />
      <div class="text" :style="{ color: store.jrbj ? '#00F0FF' : '#fff' }">
        今日办结事件<span>{{ formatNumber(data.finishCount) }}</span>件
      </div>
    </div>
  </div>
  <!-- <div class="bottom">
    <div class="item">
      <div class="text">点位数<span>{{ bottom.pointsCount }}</span>个</div>
    </div>
    <div class="item">
      <div class="text">问题点位数<span>{{ bottom.pointsTypeCount }}</span>个</div>
    </div>
    <div class="item">
      <div class="text">问题数<span>{{ bottom.eventCount }}</span>个</div>
    </div>
    <div class="item">
      <div class="text">问题发生率<span>{{ Number(bottom.pointsTypeCount / bottom.pointsCount * 100).toFixed(2) }}</span>%
      </div>
    </div>
  </div> -->
</template>
<style scoped lang="scss">
.top {
  position: absolute;
  top: 117px;
  width: 1100px;
  left: 50%;
  transform: translateX(-44%);
  z-index: 9;
  display: flex;
  flex-direction: row;

  .item {
    width: 500px;
    height: 85px;
    background: url("@/assets/img/titbg.png") no-repeat;
    background-size: 419px 78px;
    background-position: 80px 4px;
    display: flex;
    flex-direction: row;
    cursor: pointer;

    img {
      width: 80px;
      height: 85px;
    }

    .text {
      width: 420px;
      height: 80px;
      line-height: 80px;
      font-size: 20px;
      color: #fff;
      text-align: center;

      span {
        font-size: 28px;
        font-weight: blod;
        margin: 0 10px;
      }
    }
  }
}

.bottom {
  position: absolute;
  top: 890px;
  width: 900px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: space-around;

  .item {
    width: 200px;
    height: 40px;
    line-height: 40px;
    background: url("@/assets/img/titbg.png") no-repeat;
    background-size: 100% 100%;
  }

  .text {
    font-size: 16px;
    color: #fff;
    text-align: center;

    span {
      font-size: 22px;
      font-weight: blod;
      margin: 0 10px;
    }
  }
}
</style>