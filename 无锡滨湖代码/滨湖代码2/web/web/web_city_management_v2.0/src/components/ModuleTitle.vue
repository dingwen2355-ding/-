<template>
  <div class="module-title" :style="{'width': width + 'rem'}" @click="clickHeader">
    <div class="module-title-all-bg"></div>
    <div class="module-title-bg"></div>
    <div class="module-title-cnt" :style="{'font-size': fontSize + 'rem'}">{{title}} <i v-if="isArrow" class="el-icon-arrow-down"></i></div>
  </div>
</template>

<script>
export default {
  name: 'ModuleTitle',
  props: ['title', 'width', 'fontSize', 'isArrow'],
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
.module-title {
  cursor: pointer;
  position: relative;
  display: inline-block;
  top: -12rem;

  &:before {
    content: " ";
    display: block;
  }
  &:after {
    content: " ";
    display: block;
    clear: both;
  }

  .module-title-all-bg {
    position: absolute;
    width: 100%;
    height: 100%;
  }

  .module-title-bg {
    position: absolute;
    left: 0;
    top: 0;
    width: 233rem;
    height: 44rem;
    // background-image: url('../assets/global/son_module_title_bg.png');
    // background-size: 100% 100%;
  }
  .module-title-cnt {
    font-size: 20rem;
    background:linear-gradient(0deg,rgba(23,239,160,1) 0%, rgba(0,204,234,1) 100%);
    -webkit-background-clip: text; /*必需加前缀 -webkit- 才支持这个text值 */
    -webkit-text-fill-color: transparent; /*text-fill-color会覆盖color所定义的字体颜色： */
    margin-left: 12rem;
    display: inline-block;
    height: 44rem;
    line-height: 44rem;
    font-weight: bold;
    position: relative;
  }
}
</style>
