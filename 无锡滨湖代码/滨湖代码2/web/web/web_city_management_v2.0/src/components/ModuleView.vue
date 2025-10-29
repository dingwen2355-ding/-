<template>
  <div class="ModuleView">
    <div class="content-left"></div>
    <div class="content-left-m"></div>
    <div class="content-middle"></div>
    <div class="content-right-m"></div>
    <div class="content-right"></div>
    <div class="content">
      <div class="content-bg-halo"></div>
      <slot></slot>
    </div>
<!--    <div class="header" @click="clickHeader">
      <div class="title-bg"></div>
      <div class="title">
        <span class="name">{{moduleName}}</span>
        <span class="sub-name">{{subModuleName}}</span>
      </div>
    </div>-->
  </div>
</template>

<script>
export default {
  name: 'ModuleView',
  components: {},
  props: ['moduleName', 'subModuleName'],
  data () {
    return {}
  },
  mounted () {
  },
  methods: {
    clickHeader () {
      this.$emit('clickHeader', this.moduleName)
    }
  }
}
</script>

<style lang="scss">
.ModuleView {
  position: relative;
  width: 100%;
  height: 100%;
  padding: 7rem;

  .content-left {
    top: 0;
    left: 0;
    position: absolute;
    height: 100%;
    width: 96rem;
  }

  .content-left-m {
    top: 0;
    left: 96rem;
    position: absolute;
    height: 100%;
    width: calc(50% - 178rem);
  }

  .content-right {
    top: 0;
    right: 0;
    position: absolute;
    height: 100%;
    width: 96rem;
  }

  .content-right-m {
    top: 0;
    right: 96rem;
    position: absolute;
    height: 100%;
    width: calc(50% - 178rem);
  }

  .content-middle {
    top: 0;
    left: 50%;
    position: absolute;
    height: 100%;
    width: 164rem;
    margin-left: -82rem;
  }

  .header {
    position: absolute;
    width: 500rem;
    text-align: center;
    font-size: 30rem;
    color: #fff;
    top: -20rem;
    left: calc(50% - 250rem);
    height: 50rem;
    line-height: 50rem;
    font-weight: bold;
    z-index: 1;
    cursor: pointer;
    /*background-color: #14213a;*/

    .title {
      font-family: Microsoft YaHei;
      font-weight: bold;
      .name {
        color: rgba(255, 255, 255, 1);
        text-shadow: 0rem 1rem 1rem rgba(255, 255, 255, 0.7);
        background: linear-gradient(
          0deg,
          rgba(224, 241, 255, 1) 0.146484375%,
          rgba(0, 116, 206, 1) 99.0478515625%
        );
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
      }

      .sub-name {
        color: #ffde00;
      }
    }
    .title-bg {
      top: 14rem;
      left: 0;
      position: absolute;
      width: 100%;
      height: 100%;
      background-image: url("../assets/global/module-title-bg.png");
      background-size: 100% 100%;
    }
  }

  .content {
    position: relative;
    height: calc(100% - 60rem);
    margin-top: 30rem;
    padding: 15rem 20rem;
    font-size: 24rem;
    border-radius: 5rem;
    margin-left: 35rem;
    margin-right: 35rem;

    .content-bg-halo {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background: linear-gradient(
        0deg,
        rgba(5, 35, 69, 1) 0%,
        rgba(20, 36, 77, 1) 100%
      );
      opacity: 0.5;
      z-index: -1;
    }
  }
}
</style>
