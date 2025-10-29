
<template>
  <div class="panel">
    <!-- <el-select class="month-select" v-model="curMonth" @change="initPanel">
      <el-option v-for="i in 20" :key="i" :label="`第${i}期`" :value="i"></el-option>
    </el-select> -->
    <div class="wordCloud" ref="wordCloudDom"></div>
    <div class="shijianliebiao" v-if="showDCDW">
      <div class="close" @click="showDCDW = false">×</div>
      <div class="title">
        <div class="titlebg">{{ targetTitle }}事件</div>
      </div>
      <div class="content">
        <el-row :gutter="30">
          <el-col :span="8">
            <el-descriptions>
              <el-descriptions-item label="对应工单：" :span="3">
              </el-descriptions-item>
            </el-descriptions>
            <div class="tbody">
              <div :class="{ 'tr': true, 'active': shijianxiangqingForm.id == i.id }" v-for="i, index in gongdanlist"
                :key="index" @click="openSJXQ(i)">
                <div :style="{ color: i.status == '整改中' ? 'yellow' : i.status == '已整改' ? 'green' : 'red' }">{{
                  i.status }}</div>
                <div> {{ i.content }}</div>
              </div>
            </div>

          </el-col>
          <el-col :span="16" style="border-left: 3px solid #4BE9FF;">
            <el-row>
              <el-col :span="shijianxiangqingForm.nodes && shijianxiangqingForm.nodes.length > 0 ? 14 : 24">
                <el-descriptions>
                  <el-descriptions-item label="考核周期：" :span="3">{{ shijianxiangqingForm.cycle
                  }}</el-descriptions-item>
                  <el-descriptions-item label="状态：" :span="3">{{ shijianxiangqingForm.status
                  }}</el-descriptions-item>
                  <el-descriptions-item label="点位类型：" :span="3">{{ shijianxiangqingForm.typeName
                  }}</el-descriptions-item>
                  <el-descriptions-item label="事发点位：" :span="3">{{ shijianxiangqingForm.pointName
                  }}</el-descriptions-item>
                  <el-descriptions-item label="检查项目：" :span="3">{{ shijianxiangqingForm.checkItemName
                  }}</el-descriptions-item>
                  <el-descriptions-item label="责任单位：" :span="3">{{ shijianxiangqingForm.deptNames
                  }}</el-descriptions-item>
                  <el-descriptions-item label="扣分：">{{ shijianxiangqingForm.score }}</el-descriptions-item>
                  <el-descriptions-item label="事件类型：" :span="3">{{ shijianxiangqingForm.eventTypeName }}
                  </el-descriptions-item>
                  <el-descriptions-item label="所属区域：" :span="3">{{ shijianxiangqingForm.regionName
                  }}</el-descriptions-item>
                  <el-descriptions-item label="问题描述：" :span="3">{{ shijianxiangqingForm.content
                  }}</el-descriptions-item>
                  <el-descriptions-item label="附件：" :span="3">
                    <!-- <div class="file">
                                <image v-for="i, index in shijianxiangqingForm.files" :key="index" :src="i"
                                    @click="preview(index)"></image>
                            </div> -->
                  </el-descriptions-item>
                </el-descriptions>
              </el-col>
              <el-col :span="10" class="shijiantimeline"
                v-if="shijianxiangqingForm.nodes && shijianxiangqingForm.nodes.length > 0">
                <el-timeline>
                  <el-timeline-item v-for="i in shijianxiangqingForm.nodes" :timestamp="i.handletime" placement="top">
                    <div class="tips">{{ i.nodename }}</div>
                    <div class="tips">{{ i.nickName }}-{{ i.handleopinion }}</div>
                  </el-timeline-item>
                </el-timeline>
              </el-col>
            </el-row>
          </el-col>
        </el-row>

      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, onUnmounted, watch } from "vue";
import axios from "axios";
import { useStore } from "@/store/index";

let store = useStore();
watch(
  () => store.areaName,
  (newVal, oldVal) => {
    regionName = newVal;
    initPanel();
  }
);
let showDCDW = ref(false);
let targetTitle = ref(true);
let shijianxiangqingForm = ref({});
let gongdanlist = ref([
  {
    "eventSource": 5,
    "id": "11265",
    "cycle": "第18期",
    "checkDate": "2023-07-04 16:59:00",
    "extensionFlag": 0,
    "planDate": "2024-07-19 00:00:00",
    "code": "",
    "eventTypeName": "乱扔垃圾",
    "content": "学校周边垃圾乱扔",
    "checkItemName": "9.学校周边无垃圾乱扔",
    "pointName": "无锡市育红小学（公益校区）",
    "typeName": "小学",
    "score": -2,
    "regionName": "荣巷街道",
    "latitude": 0,
    "longitude": 0,
    "deptNames": "区城管局,荣巷街道",
    "endTime": "2023-07-24 10:55:53",
    "status": "已整改"
  },
  {
    "eventSource": 5,
    "id": "6169",
    "cycle": "第11期",
    "checkDate": "2022-11-24 10:23:23",
    "extensionFlag": 0,
    "planDate": "2023-11-30 00:00:00",
    "code": "",
    "eventTypeName": "乱扔垃圾",
    "content": "乱扔杂物 ",
    "checkItemName": "9.学校周边无垃圾乱扔",
    "pointName": "无锡市育红小学（公益校区）",
    "typeName": "小学",
    "score": -1,
    "regionName": "荣巷街道",
    "latitude": 0,
    "longitude": 0,
    "deptNames": "区城管局,荣巷街道",
    "endTime": "2022-12-19 09:01:51",
    "status": "已整改"
  },
  {
    "eventSource": 5,
    "id": "3624",
    "cycle": "第7期",
    "checkDate": "2022-06-24 23:29:01",
    "extensionFlag": 0,
    "planDate": "2022-07-27 00:00:00",
    "code": "",
    "eventTypeName": "违章停放",
    "content": "车辆乱停放",
    "checkItemName": "23.机动车、非机动车有序规范停放、不占用绿化带、不影响行人通行，共享单车文明使用、规范停放（城市管理部门在人行道、非机动车道、机动车道旁统一划线，机动车、非机动车、共享单车在线内规范停放，且不影响正常交通，这种情况不算作违章停车）；",
    "pointName": "无锡市育红小学（公益校区）",
    "typeName": "中小学校及周边",
    "score": -0.5,
    "regionName": "荣巷街道",
    "latitude": 0,
    "longitude": 0,
    "deptNames": "荣巷街道,区城管局",
    "endTime": "2022-07-20 17:03:26",
    "status": "已整改"
  }]);
shijianxiangqingForm.value = gongdanlist.value[0]
let curMonth = ref(20);
let hotWord = [{ type: "乱扔垃圾", count: 1246 }];
let maxVal = 1246;
let color = [
  "#a18cd1",
  "#fad0c4",
  "#ff8177",
  "#fecfef",
  "#fda085",
  "#f5576c",
  "#fe9a8b",
  "#30cfd0",
  "#38f9d7"
];
function openSJXQ(element) {
  shijianxiangqingForm.value = element
}
let wordArr = [],
  timer = null,
  resetTime = 10,
  ContainerSize = "";
let wordCloudDom = ref(null);
onMounted(() => {
  initPanel();
});
let regionName = "";
function initPanel() {
  $axios_get(IP + "/screen/api/count/highType", {
      params: { regionName: regionName }
    })
    .then(res => {
      cancelAnimationFrame(timer);
      wordCloudDom.value.innerHTML = "";
      hotWord = res.data.data;
      maxVal = hotWord.reduce(
        (accumulator, currentValue) =>
          Math.max(accumulator, currentValue.count),
        0
      );
      init();
    });
}
onUnmounted(() => {
  // 组件销毁，关闭定时执行
  cancelAnimationFrame(timer);
});
function init() {
  dealSpan();
  initWordPos();
  render();
}
function dealSpan() {
  const wordArrTemp = [];
  hotWord.forEach(item => {
    // 根据词云数量生成span数量设置字体颜色和大小
    const spanDom = document.createElement("span");
    spanDom.style.position = "absolute";
    spanDom.style.display = "inline-block";
    spanDom.style.color = randomColor();
    spanDom.style.fontSize = Math.round(item.count / maxVal) * 20 + 20 + "px";
    spanDom.style.cursor = "pointer";
    spanDom.innerHTML = item.type;
    spanDom.local = {
      position: {
        // 位置
        x: 0,
        y: 0
      },
      direction: {
        // 方向 正数往右 负数往左
        x: 1,
        y: 1
      },
      velocity: {
        // 每次位移初速度
        x: -0.5 + Math.random(),
        y: -0.5 + Math.random()
      }
    };
    wordCloudDom.value.appendChild(spanDom);
    wordArrTemp.push(spanDom);
    spanDom.addEventListener("click", (e) => {
      showDCDW.value = true;
      targetTitle.value = e.target.innerText
    })
  });
  wordArr = wordArrTemp;
}
function randomColor() {
  // 获取随机颜色
  var colorIndex = Math.floor(color.length * Math.random());
  return color[colorIndex];
}
function randomNumber(lowerInteger, upperInteger) {
  // 获得一个包含最小值和最大值之间的随机数。
  const choices = upperInteger - lowerInteger + 1;
  return Math.floor(Math.random() * choices + lowerInteger);
}
function render() {
  if (resetTime < 100) {
    resetTime = resetTime + 1;
    timer = requestAnimationFrame(render.bind(this));
    resetTime = 0;
  }
  wordFly();
}
function wordFly() {
  wordArr.forEach(value => {
    // 设置运动方向 大于边界或者小于边界的时候换方向
    if (
      value.local.realPos.minx + value.local.position.x <
      ContainerSize.leftPos.x ||
      value.local.realPos.maxx + value.local.position.x >
      ContainerSize.rightPos.x
    )
      value.local.direction.x = -value.local.direction.x;
    if (
      value.local.realPos.miny + value.local.position.y <
      ContainerSize.leftPos.y ||
      value.local.realPos.maxy + value.local.position.y >
      ContainerSize.rightPos.y
    )
      value.local.direction.y = -value.local.direction.y;
    value.local.position.x += value.local.velocity.x * value.local.direction.x;
    value.local.position.y += value.local.velocity.y * value.local.direction.y;
    // 给每个词云加动画过渡
    value.style.transform =
      "translateX(" +
      value.local.position.x +
      "px) translateY(" +
      value.local.position.y +
      "px)";
  });
}
function initWordPos() {
  // 计算每个词的真实位置和容器的位置
  wordArr.forEach(value => {
    value.local.realPos = {
      minx: value.offsetLeft,
      maxx: value.offsetLeft + value.offsetWidth,
      miny: value.offsetTop,
      maxy: value.offsetTop + value.offsetHeight
    };
  });
  ContainerSize = getContainerSize();
}
function getContainerSize() {
  // 判断容器大小控制词云位置
  return {
    leftPos: {
      // 容器左侧的位置和顶部位置
      x: wordCloudDom.value.offsetLeft,
      y: wordCloudDom.value.offsetTop
    },
    rightPos: {
      // 容器右侧的位置和底部位置
      x: wordCloudDom.value.offsetLeft + wordCloudDom.value.offsetWidth,
      y: wordCloudDom.value.offsetTop + wordCloudDom.value.offsetHeight
    }
  };
}
</script>
<style scoped>
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
<style lang="scss" scoped>
.panel {
  .wordCloud {
    width: 100%;
    height: 160px;
  }
}

.shijianliebiao {
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
    width: 1098px;
    height: 545px;

    .tbody {
      height: 350px;
      overflow: auto;

      .active {
        background-color: #4BE9FF50;
      }

      .tr {
        width: 100%;
        height: 40px;
        line-height: 40px;
        cursor: pointer;
        border-bottom: 1px solid #ffffff50;
        color: #fff;
        font-size: 20px;

        div:nth-child(1) {
          width: 30%;
          float: left;
          color: #4BE9FF;
        }

        div:nth-child(2) {
          width: 70%;
          text-overflow: ellipsis;
          overflow: hidden;
          white-space: nowrap;
          float: right;
        }
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
</style>