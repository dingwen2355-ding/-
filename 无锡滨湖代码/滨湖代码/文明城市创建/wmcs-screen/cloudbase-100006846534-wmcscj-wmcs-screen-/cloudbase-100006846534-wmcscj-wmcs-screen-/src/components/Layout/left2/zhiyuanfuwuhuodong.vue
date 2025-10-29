<script setup>
import { ref, onMounted, getCurrentInstance } from "vue";
import axios from "axios";
const { $getAssets, $getResource } =
  getCurrentInstance().appContext.config.globalProperties;
let data = ref([]);
let showZYHD = ref(false);
let detail = ref(null);
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/activityList").then(res => {
    data.value = res.data.data.records;
  });
});
function openDetail(row) {
  showZYHD.value = true;
  detail.value = row;
}
</script>
<template>
  <vue3-seamless-scroll
    class="list"
    :hover-stop="true"
    :list="data"
    hover="true"
    step="0.3"
    v-if="data.length > 0"
  >
    <div class="item" v-for="(i, index) in data" :key="index" @click="openDetail(i)">
      <div class="img">
        <img :src="$getResource(i.iconImg)" alt="" />
      </div>
      <div class="title">{{ i.regionName }}</div>
      <div class="content">{{ i.name }}</div>
    </div>
  </vue3-seamless-scroll>
  <div class="detail-zyhd" v-if="showZYHD">
    <div class="close" @click="showZYHD = false">×</div>
    <div class="title">
      <div class="titlebg">{{ detail.name }}</div>
    </div>
    <div class="content">
        <div v-html="detail.details"></div>
    </div>
  </div>
</template>
<style scoped lang="scss">
.list {
  height: 422px;
  overflow-y: auto;
  .item {
    margin-bottom: 20px;
    .img {
      width: 100%;
      height: 200px;
      background-color: rgb(167, 167, 167);

      img {
        width: 100%;
        height: 200px;
      }
    }

    .title {
      width: 100%;
      font-size: $font-size-big;
      color: $color3;
      text-align: center;
    }

    .content {
      width: 100%;
      font-size: $font-size-small;
      color: #fff;
    }
  }
}
.detail-zyhd {
  position: fixed;
  top: 180px;
  left: 50%;
  transform: translateX(-50%);
  width: 1136px;
  height: 650px;
  background: #053475eb;
  z-index: 9999;

  .title {
    width: 100%;
    height: 48px;
    background: url("@/assets/img/title-m.png") no-repeat;
    background-size: cover;

    .titlebg {
      margin-left: 50%;
      transform: translateX(-50%);
      width: 100%;
      height: 48px;
      line-height: 48px;
      text-align: center;
      font-size: 20px;
      color: #fff;
    }
  }

  .content {
    padding: 10px;
    margin: 10px;
    height: 545px;
    color: #fff !important;
    overflow-y: auto;

    .logo {
      width: 100px;
      height: 100px;
      position: absolute;
      top: 60px;
      right: 80px;

      img {
        width: 100px;
        height: 100px;
      }
    }
  }

  .close {
    position: absolute;
    z-index: 10;
    top: 0px;
    right: 20px;
    font-size: 36px;
    color: aqua;
    cursor: pointer;
  }

  .file {
    width: 100%;

    img {
      width: 30%;
      height: 180px;
      margin-right: 2%;
    }
  }
}
</style>