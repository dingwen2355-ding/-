<template>
  <van-nav-bar :title="title" @click-left="onClickLeft" fixed class="TopBar" :left-arrow="leftArrowShow">
    <template #right>
      <slot name="right"></slot>
    </template>
  </van-nav-bar>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: ''
    },
    leftArrowShow: {
      type: Boolean,
      default: true
    }
  },
  data() {
  },
  mounted() { },
  methods: {
    onClickLeft() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="scss">
.TopBar {
  z-index: 999;
  background-color: #fff;
  font-family: 'PingFangSC-Medium';
  height: 92rem;

  .van-nav-bar__left,
  .van-nav-bar__right {
    padding: 0 32rem;
    font-size: 28rem;
    .van-icon {
      color: #131824;
      font-weight: 800;
    }
    .van-nav-bar__arrow {
      min-width: 1em;
      margin-right: 8rem;
      font-size: 36rem;
    }
  }

  .van-nav-bar__text,
  .van-nav-bar__title {
    color: #131824;
    font-weight: 500;
    font-size: 34rem;
  }
}
</style>
