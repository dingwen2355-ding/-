<template>
  <div class="panel">
    <div class="zmgjc" ref="wordCloudDom"></div>
  </div>
</template>
<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import axios from "axios";
let hotWord = [
  "敬业",
  "智慧",
  "民主",
  "有爱",
  "博爱",
  "爱护",
  "和谐",
  "尊师重道"
];
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
let wordArr = [],
  timer = null,
  resetTime = 10,
  ContainerSize = "";
let wordCloudDom = ref(null);
let maxVal = 100;
onMounted(() => {
  $axios_get(IP + "/screen/api/civilization/advancedKeyword")
    .then(res => {
      cancelAnimationFrame(timer);
      wordCloudDom.value.innerHTML = ""
      hotWord = res.data.data;
      maxVal = hotWord.reduce(
        (accumulator, currentValue) =>
          Math.max(accumulator, currentValue.count),
        0
      );
      init();
    });
});
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
  hotWord.forEach(value => {
    // 根据词云数量生成span数量设置字体颜色和大小
    const spanDom = document.createElement("span");
    spanDom.style.position = "relative";
    spanDom.style.display = "inline-block";
    spanDom.style.color = randomColor();
    spanDom.style.fontSize = Math.round(value.count / maxVal) * 20 + 20 + "px";
    spanDom.innerHTML = value.type;
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
    <style lang="scss" scoped>
.panel {
  .zmgjc {
    width: 100%;
    height: 172px;
  }
}
</style>