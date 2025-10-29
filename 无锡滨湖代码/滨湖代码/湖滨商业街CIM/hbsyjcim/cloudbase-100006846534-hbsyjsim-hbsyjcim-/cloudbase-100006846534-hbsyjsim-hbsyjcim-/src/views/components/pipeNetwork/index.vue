<template>
  <div class="pipe-network-box" @click="handleClick">
    <div :class="active === 1 ? 'active-item item' : 'item'">全部</div>
    <div :class="active === 2 ? 'active-item item' : 'item'">污水</div>
    <div :class="active === 3 ? 'active-item item' : 'item'">雨水</div>
  </div>
</template>

<script>
import eventBus from "@/utils/eventBus";
export default {
  name: "pipeNetwork",
  data() {
    return {
      active: 1,
    };
  },
  watch: {
    active: {
      handler(val) {
        let sewage = "0D3ADE1841DE0AC76D8285BE202D00EF"; // 污水
        let rain = "CB47EC6748EB1C2D78E387B55E083F67"; // 雨水
        let arr = [];
        if (val === 1) {
          arr = [sewage, rain];
        }
        if (val === 2) {
          // 污水
          __g.infoTree.hide(rain);
          arr = [sewage];
        }
        if (val === 3) {
          // 雨水
          __g.infoTree.hide(sewage);

          arr = [rain];
        }

        __g.infoTree.show(arr).then((res) => {
          console.log(res);
        });
      },
      immediate: true,
    },
  },
  async created() {
    // 清除marker
    await __g.marker.clear();
    // __g.infoTree.hide([
    //   "58E5125D41C77BE9B6ED34B7890EBDF0",
    //   "05F6CB864CF5DE76B7D92D8493E8B094",
    // ]);
  },
  mounted() {
    // 设置为黑夜
    eventBus.$emit("handlePipeShow", true);
    // 隐藏商业街上建筑
    __g.infoTree.hide([
      "58E5125D41C77BE9B6ED34B7890EBDF0",
      "05F6CB864CF5DE76B7D92D8493E8B094",
      "E03EA99845A1CC2B2600AD94563A8A55",
    ]);
    // 显示管网
    __g.infoTree.show("78B9456E4461B6C1CAFE548CD9D489C9");
  },
  destroyed() {
    let hour = new Date().getHours();
    if (hour > 6 && hour < 18) {
      console.log("白天");
      // 切换为白天
      eventBus.$emit("handlePipeShow", false);
    }
    // 隐藏地下网管
    __g.infoTree.hide("78B9456E4461B6C1CAFE548CD9D489C9");
    // 显示建筑
    __g.infoTree.show([
      "58E5125D41C77BE9B6ED34B7890EBDF0",
      "05F6CB864CF5DE76B7D92D8493E8B094",
      "E03EA99845A1CC2B2600AD94563A8A55",
    ]);
  },
  methods: {
    handleClick(e) {
      const { innerText } = e.target;
      if (innerText === "全部") this.active = 1;
      if (innerText === "污水") this.active = 2;
      if (innerText === "雨水") this.active = 3;
    },
  },
};
</script>

<style lang="scss" scoped>
.pipe-network-box {
  .item {
    width: 126px;
    height: 40px;
    background: rgba(26, 33, 35, 0.8);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .active-item {
    background: linear-gradient(0deg, #1a2123cc 0%, #1a2123cc 100%), #ffffff03;
    background-image: url("../../../assets/header/img_head_model_box_sun_bg.png");
    background-repeat: no-repeat;
  }
}
</style>
