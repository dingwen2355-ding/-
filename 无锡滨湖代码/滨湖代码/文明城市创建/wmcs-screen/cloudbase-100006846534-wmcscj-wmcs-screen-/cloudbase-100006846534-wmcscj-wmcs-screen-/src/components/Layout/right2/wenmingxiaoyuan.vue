<script setup>
import { ref, onMounted, getCurrentInstance } from "vue";
import axios from "axios";
const { $getAssets, $getResource } =
  getCurrentInstance().appContext.config.globalProperties;
let data = ref([]);
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/schoolList", {
      params: {
        pageNum: 1,
        pageSize: 10
      }
    })
    .then(res => {
      data.value = res.data.data.records;
    });
});
let showWMXY = ref(false);
let detail = ref(null);
function openDetail(row) {
  showWMXY.value = true;
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
        <img
          :src="
            !!i.iconImg
              ? $getResource(i.iconImg)
              : './image/testImage/wmxy1.jpg'
          "
          alt=""
        />
      </div>
      <div class="title">{{ i.regionName }}</div>
      <div class="content">{{ i.name }}</div>
    </div>
  </vue3-seamless-scroll>
  <div class="detail-wmxy" v-if="showWMXY">
    <div class="close" @click="showWMXY = false">×</div>
    <div class="title">
      <div class="titlebg">{{ detail.name }}</div>
    </div>
    <div class="content">
      <el-descriptions>
        <el-descriptions-item label="名称：" :span="1">{{
          detail.name
        }}</el-descriptions-item>
        <el-descriptions-item label="地址：" :span="2">{{
          detail.address
        }}</el-descriptions-item>
        <el-descriptions-item label="联系人：" :span="1">{{
          detail.contact
        }}</el-descriptions-item>
        <el-descriptions-item label="联系电话：" :span="2">{{
          detail.contactTel
        }}</el-descriptions-item>
        <el-descriptions-item label="荣誉：" :span="3">
          <div
            v-html="detail.honors"
          ></div>
        </el-descriptions-item>
        <el-descriptions-item label="照片：" :span="3" v-if="!!detail.attachmentUrl">
          <div class="file">
            <img
              v-for="(i, index) in detail.attachmentUrl"
              :key="index"
              :src="$getResource(i)"
              v-show="i.indexOf('docx')<0&&i.indexOf('doc')<0"
            />
          </div>
        </el-descriptions-item>
        <el-descriptions-item label="描述：" :span="3">
          <div
            v-html="detail.selectDesc"
          ></div>
        </el-descriptions-item>
      </el-descriptions>
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
      text-align: center;
      width: 100%;
      font-size: $font-size-small;
      color: #fff;
    }
  }
}
.detail-wmxy {
  position: fixed;
  top: 180px;
  left: 50%;
  transform: translateX(-50%);
  width: 1136 * 0.8px;
  height: 650 * 0.9px;
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
      width: 360px;
      height: 48px;
      line-height: 48px;
      text-align: center;
      background: url("@/assets/img/titleline.png") no-repeat;
      background-size: 360px 13px;
      background-position-y: 18px;
      font-size: 20px;
      color: #fff;
    }
  }

  .content {
    padding: 10px;
    margin: 10px;
    width: 1098 * 0.8px;
    height: 545 * 0.9px;
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
  ::v-deep(.el-descriptions) {
    color: #fff !important;
  }

  ::v-deep(.el-descriptions__body) {
    background-color: #ffffff00 !important;
  }

  ::v-deep(.el-descriptions__label) {
    color: #00f0ff !important;
    font-size: 20px;
  }

  ::v-deep(.el-descriptions__content) {
    color: #fff !important;
    font-size: 20px;
  }
}
</style>