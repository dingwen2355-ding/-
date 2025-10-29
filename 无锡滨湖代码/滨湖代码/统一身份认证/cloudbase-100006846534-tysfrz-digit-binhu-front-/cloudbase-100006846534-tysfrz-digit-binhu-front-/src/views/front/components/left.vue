<template>
  <div class="left">
    <div class="btn1 btn" @click="changeImg(1)"></div>
    <div class="btn2 btn" @click="changeImg(2)"></div>
    <div class="btn3 btn" @click="changeImg(3)"></div>
  </div>
</template>
<script>
import bus from "./bus.js";
export default {
  data() {
    return {};
  },
  async mounted() {
    this.changeImg(sessionStorage.getItem("sideBar") || 1);
    let that = this;
    bus.$off("toAnnounce").$on("toAnnounce", (val) => {
      that.changeImg(val);
    });
  },
  methods: {
    resetImg() {
      let dom1 = document.getElementsByClassName("btn")[0];
      let dom2 = document.getElementsByClassName("btn")[1];
      let dom3 = document.getElementsByClassName("btn")[2];
      dom1.classList.add("btn1");
      dom2.classList.add("btn2");
      dom3.classList.add("btn3");
      dom1.classList.remove("img1");
      dom2.classList.remove("img2");
      dom3.classList.remove("img3");
    },
    changeImg(i) {
      sessionStorage.setItem("sideBar", i);
      this.$emit("getData", i);
      this.resetImg();
      let dom = document.getElementsByClassName("btn" + i)[0];
      dom.classList.remove("btn" + i);
      dom.classList.add("img" + i);
    },
  },
};
</script>
<style lang="scss" scoped>
.left {
  padding-top: 92px;
  div {
    width: 40px;
    height: 40px;
    cursor: pointer;
    // margin: 0 0 70px 25px;
    margin: 0 auto 50px;
    background-repeat: no-repeat;
    background-size: 100%;
  }
  .btn1 {
    background-image: url("/img/bg/gongzuotai1.png");
  }
  .img1 {
    background-image: url("/img/bg/gongzuotai2.png");
  }
  .btn2 {
    background-image: url("/img/bg/gonggao1.png");
  }
  .img2 {
    background-image: url("/img/bg/gonggao2.png");
  }
  .btn3 {
    background-image: url("/img/bg/quanxianshenqing1.png");
  }
  .img3 {
    background-image: url("/img/bg/quanxianshenqing2.png");
  }
}
</style>