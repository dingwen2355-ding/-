<template>
  <div class="ioc-title">
    <span>{{ title }}</span>
    <i v-if="closeVisible" class="close" @click="closeWin"></i>
  </div>
</template>

<script>
export default {
  name: 'IocTitle',
  props: {
    title: {
      type: String,
      default: '标题'
    },
    closeVisible: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {

    }
  },
  methods: {
    // 关闭窗口
    closeWin() {
      this.$emit('change')
    }
  }
}
</script>

<style lang="scss" scoped>
.ioc-title {
  width: 427px;
  height: 37px;
  line-height: 37px;
  position: relative;
  background-image: url("../../assets/img_slider_title_bg.png");
  background-size: 100% 100%;
  span {
    margin-left: 8px;
    color: #00d5ff;
    font-size: 18px;
    font-weight: 600;
  }
  .close {
    display: inline-block;
    width: 22px;
    height: 22px;
    background-image: url("../../assets/icon_btn_close.png");
    background-size: 100% 100%;
    position: absolute;
    right: 0;
    top: 8px;
    cursor: pointer;
  }
}
</style>