<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import leftRighttwo from "@/components/Layout/display/left-righttwo.vue";
let data = ref([
  {
    type: "新时代好少年",
    count: 12
  },
  {
    type: "身边好人",
    count: 6
  },
  {
    type: "最美人物",
    count: 22
  },
  {
    type: "岗位学雷锋标兵",
    count: 8
  }
]);
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/advancedCount").then(res => {
    const match = {
      xsdhsn: "新时代好少年",
      sbhr: "身边好人",
      zmrw: "最美人物",
      gwxlfbb: "岗位学雷锋标兵"
    };
    let counts = [];
    Object.keys(match).forEach(key => {
      counts.push({
        type: match[key],
        count: res.data.data[key]
      });
    });
    data.value = counts;
  });
});
</script>
<template>
  <div class="flex-row" style="height: 100%">
    <div
      v-for="(i, index) in data"
      :key="index"
      style="width: 20%; text-align: center"
    >
      <div class="big-value" style="font-size: 45px">{{ i.count }}</div>
      <img src="@/assets/img/gongdan.png" width="82" height="82" />
      <div class="mid-name">{{ i.type }}</div>
    </div>
  </div>
</template>
<style scoped lang="scss"></style>