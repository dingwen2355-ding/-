<template>
  <div class="KDialog" v-if="dialogVisible">
    <div class="k-dialog-bg" @click="closeDialog"></div>
    <div class="k-dialog-warp">
      <div class="k-dialog-head">
        <div class="k-dialog-close" @click="closeDialog"><i class="el-icon-close"></i></div>
        <div class="title"><i class="iconfont iconzhgl_title"></i> {{dialogTitle}} <i class="iconfont iconzhgl_title"></i></div>
      </div>
      <div class="k-dialog-content">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'KDialog',
  components: {},
  props: ['dialogTitle', 'dialogVisible'],
  data() {
    return {}
  },
  mounted() {
  },
  methods: {
    closeDialog: function () {
      this.$emit('closeDialog', false)
    }
  }
}
</script>

<style lang="scss" scoped>
  .KDialog {
    position: fixed;
    width: 100%;
    height: 100%;
    left: 0;
    top: 0;
    z-index: 111;
    .k-dialog-bg {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background-color: #000;
      opacity: 0.5;
    }
    .k-dialog-warp {
      width:2740px;
      // height:1664px;
      background:rgba(255,255,255,1);
      border-radius:10px;
      position: absolute;
      left: 50%;
      top: 50%;
      margin-left: -1370px;
      margin-top: -715px;
      .k-dialog-head {
        position: relative;
        height: 204px;
        padding: 62px 86px;
        border-bottom: 4px solid #DAE1E5;

        .title {
          font-size:48px;
          line-height:80px;
          text-align: center;
          position: relative;
          margin: 0 230px;
          font-weight: 400;
          color: #333333;

          i {
            background:linear-gradient(-90deg,rgba(0,215,254,1) 0%,rgba(0,150,255,1) 100%);
            -webkit-background-clip: text; /*必需加前缀 -webkit- 才支持这个text值 */
            -webkit-text-fill-color: transparent; /*text-fill-color会覆盖color所定义的字体颜色： */
            margin: 0 20px;
          }
        }

        .k-dialog-close {
          float: right;
          cursor: pointer;
          font-size: 48px;
          color: #B6B6B6;
        }
      }
      .k-dialog-content {
        position: relative;
        height: calc(100% - 204px);
        overflow-x: hidden;
        overflow-y: auto;
      }
    }
  }
</style>
