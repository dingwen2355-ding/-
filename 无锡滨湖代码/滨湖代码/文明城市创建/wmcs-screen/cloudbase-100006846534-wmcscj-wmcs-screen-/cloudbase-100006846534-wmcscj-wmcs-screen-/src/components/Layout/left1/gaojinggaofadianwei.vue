<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

let data = ref([{ type: "（马山）峰影苑" }, { type: "（马山）乐山市场" }]);

let curMonth = ref(20);
let curChart = ref("达标率");

onMounted(() => {
  getData();
});
function getData() {
  $axios_get(IP + "/screen/api/count/highPoint").then(res => {
    data.value = res.data.data;
  });
}
</script>
<template>
  <!-- <el-select class="month-select" v-model="curMonth" @change="getData">
    <el-option
      v-for="i in 20"
      :key="i"
      :label="`第${i}期`"
      :value="i"
    ></el-option>
  </el-select> -->
  <!-- <div class="tab-switch">
    <span
      :class="{ active: curChart == '达标率' }"
      @click="
        curChart = '达标率';
        getData();
      "
    >
      达标率
    </span>
    <span
      :class="{ active: curChart == '覆盖率' }"
      @click="
        curChart = '覆盖率';
        getData();
      "
    >
      覆盖率
    </span>
  </div> -->
  <vue3-seamless-scroll
    :list="data"
    class="scroll"
    :step="0.8"
    singleHeight="33"
  >
    <div class="li" v-for="(i, index) in data" :key="index">
      {{ i.type }}
      <span style="float: right; margin-right: 20px">{{ i.count }}</span>
    </div>
  </vue3-seamless-scroll>
</template>
<style scoped lang="scss">
.scroll {
  overflow: hidden;
  margin-top: 10px;
  height: 180px;
}
.li {
  width: 340px;
  height: 28px;
  background: #0165c250;
  position: relative;
  padding-left: 40px;
  line-height: 28px;
  color: #fff;
  font-size: 16px;
  margin-bottom: 5px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.li:after {
  width: 27px;
  height: 22px;
  content: "";
  position: absolute;
  top: 6px;
  left: 5px;
  background: url("@/assets/img/li.png") no-repeat;
  background-size: cover;
}
.month-select {
  position: absolute;
  right: 0;
  top: 45px;
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

.tab-switch {
  text-align: center;
  color: #fff;
  margin: -15px 0 10px 0;

  span {
    padding: 5px 10px;
  }

  .active {
    background: #256ddc;
  }
}
</style>