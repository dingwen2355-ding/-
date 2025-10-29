<script setup>
import { ref, onMounted } from "vue"
import axios from "axios";
let showZMZT = ref(false);
let detail = ref(null);
let data = ref([
  { name: "(最美校园)  江南大学", },
  { name: "(最美校园)  太湖高级中学", },
  { name: "(最美校园)  蠡园实验小学", },
  { name: "(最美校园)  雪浪仙河苑幼儿园", },
  { name: "(最美服务中心)  滨湖区民政局", },
  { name: "(最美服务中心)  行政审批局", },
])
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/advancedList", {
    params: {
      pageNum: 1, pageSize: 10
    }
  }).then(res => {
    data.value = res.data.data.records;
  })
})
function openDetail(row) {
  showZMZT.value = true;
  detail.value = row
}
</script>
<template>
  <vue3-seamless-scroll :list="data" class="scroll" :step="0.8" singleHeight="33">
    <div class="li" v-for="(i, index) in data" :key="index" @click="openDetail(i)">({{ i.recommendName }}){{ i.name }}
    </div>
  </vue3-seamless-scroll>
  <div class="zuimeirenwu" v-if="showZMZT">
    <div class="close" @click="showZMZT = false">×</div>
    <div class="title">
      <div class="titlebg">{{ detail.name }}</div>
    </div>
    <div class="content">
      <el-descriptions>
        <el-descriptions-item label="姓名：" :span="1">{{ detail.name
        }}</el-descriptions-item>
        <el-descriptions-item label="性别：" :span="1">{{ detail.sex== "0"?"男":"女"
        }}</el-descriptions-item>
        <el-descriptions-item label="类型：" :span="1">{{ detail.recommendName
        }}</el-descriptions-item>
        <el-descriptions-item label="荣誉类型：" :span="1">{{ detail.type
        }}</el-descriptions-item>
        <el-descriptions-item label="获选时间：" :span="1">{{ detail.selectDate
        }}</el-descriptions-item>
        <el-descriptions-item label="获选层级：" :span="1">{{ detail.level == "1"?"国家级":detail.level == "2"?"省级":"市级"
        }}</el-descriptions-item>
        <el-descriptions-item label="推荐来源：" :span="1">{{ detail.selectSource
        }}</el-descriptions-item>
        <el-descriptions-item label="所属板块：" :span="2">{{ detail.regionName
        }}</el-descriptions-item>
        <el-descriptions-item label="所在单位：" :span="2">{{ detail.company
        }}</el-descriptions-item>
        <el-descriptions-item label="单位职务：" :span="1">{{ detail.position
        }}</el-descriptions-item>
        <el-descriptions-item label="主要事迹：" :span="3">
          <div style="height: 300px;overflow-y: auto;" v-html="detail.selectDesc"></div>
        </el-descriptions-item>
        <!-- <el-descriptions-item label="照片：" :span="3">
          <div class="file">
            <img v-for="i, index in detail.files" :key="index" :src="i" />
          </div>
        </el-descriptions-item> -->
      </el-descriptions>
    </div>
  </div>
</template>
<style scoped>
::v-deep(.el-descriptions) {
  color: #fff !important;
}

::v-deep(.el-descriptions__body) {
  background-color: #ffffff00 !important;
}

::v-deep(.el-descriptions__label) {
  color: #00F0FF !important;
  font-size: 20px;
}

::v-deep(.el-descriptions__content) {
  color: #fff !important;
  font-size: 20px;
}
</style>
<style scoped lang="scss">
.scroll {
  overflow: hidden;
  height: 190px;
}

.li {
  width: 340px;
  height: 28px;
  background: #0165C250;
  position: relative;
  padding-left: 40px;
  line-height: 28px;
  color: #fff;
  font-size: 16px;
  margin-bottom: 5px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  cursor: pointer;
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

.zuimeirenwu {
  position: fixed;
  top: 180px;
  left: 50%;
  transform: translateX(-50%);
  width: 1136*0.8px;
  height: 650*0.9px;
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
    width: 1098*0.8px;
    height: 545*0.9px;


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
}
</style>