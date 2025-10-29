<script setup>
import { ref, onMounted, watch } from "vue";
import leftRighttwo from "@/components/Layout/display/left-righttwo.vue";
import topBottom from "@/components/Layout/display/top-bottom.vue";
import axios from "axios";
import { useStore } from "@/store/index";

let store = useStore();

watch(
  () => store.indexMonth,
  (newVal, oldVal) => {
    curMonth = newVal;
    getData();
  }
);

let curMonth = "";
let datas = ref({
  riseRegion: "蠡湖街道",
  riseScore: 0,
  fallRegion: "马山街道",
  fallScore: 0
});
onMounted(() => {
  getData();
});
function getData() {
  $axios_get(IP + "/screen/api/assess/assessChange", {
      params: { month: curMonth }
    })
    .then(res => {
      datas.value = res.data.data;
    });
}
</script>
<template>
  <div class="panel flex-row">
    <topBottom class="el">
      <template #t>
        <leftRighttwo>
          <template #l>
            <img src="@/assets/img/up.png" width="82" height="82" />
          </template>
          <template #r-t>
            <div class="mid-name">本月最大涨幅</div>
          </template>
          <template #r-b>
            <div class="font-b-r">{{ datas.riseRegion }}</div>
          </template>
        </leftRighttwo>
      </template>
      <template #b>
        <topBottom>
          <template #t>
            <span class="font-b-r">{{ datas.riseScore }}</span>
          </template>
          <template #t-s>{{ datas.riseScore == 0 ? "" : "↑" }}</template>
          <template #b class="mid-name">较上月</template>
        </topBottom>
      </template>
    </topBottom>
    <div class="gap"></div>
    <topBottom class="el">
      <template #t>
        <leftRighttwo>
          <template #l>
            <img src="@/assets/img/down.png" width="82" height="82" />
          </template>
          <template #r-t>
            <div class="mid-name">本月最大跌幅</div>
          </template>
          <template #r-b>
            <div class="font-b-g">{{ datas.fallRegion }}</div>
          </template>
        </leftRighttwo>
      </template>
      <template #b>
        <topBottom>
          <template #t>
            <span class="font-b-g">{{ datas.fallScore }}</span>
          </template>
          <template #t-s>{{ datas.fallScore == 0 ? "" : "↓" }}</template>
          <template #b class="mid-name">较上月</template>
        </topBottom>
      </template>
    </topBottom>
  </div>
</template>
<style scoped lang="scss">
.panel {
  height: 100%;
}

.el {
  width: 50%;
  height: 100%;
}

.name {
  color: #fff;
  font-size: 16px;
}

.name2 {
  color: #fff;
  font-size: 20px;
}

.value {
  color: #00f0ff;
  font-size: 24px;
}

.value2 {
  color: #00f0ff;
  font-size: 36px;
}
</style>