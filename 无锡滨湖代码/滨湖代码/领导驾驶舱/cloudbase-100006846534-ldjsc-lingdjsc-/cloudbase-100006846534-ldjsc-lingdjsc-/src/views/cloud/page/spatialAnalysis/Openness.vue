<template>
  <div class="cloud-func" v-show="opennessOpen">
    <div class="func-title">
      开敞度分析
      <span @click.stop="handleClose">
        <i class="el-icon-close"></i>
      </span>
    </div>
    <div class="func-warp">
      <div class="warp-list legend">
        <div><span>可见区域</span><label class="visibleColor"></label></div>
        <div><span>不可见区域</span><label class="invisibleColor"></label></div>
      </div>
      <div class="warp-list">
        <div class="list-title">展示半径（米）</div>
        <div class="list">
          <el-slider :min="5" :max="1000" :step="5" v-model="openness.radius" @change="changeValue" :show-input="false"
            :show-input-controls="false"></el-slider>
          <el-input-number v-model="openness.radius" controls-position="right" :min="5" :max="1000" :step="1"
            @change="changeValue"></el-input-number>
        </div>
      </div>
      <div class="warp-list">
        <div class="list-title">透明度</div>
        <div class="list">
          <el-slider :min="0.1" :max="1" :step="0.1" v-model="openness.opacity" @change="changeValue" :show-input="false"
            :show-input-controls="false"></el-slider>
          <el-input-number v-model="openness.opacity" controls-position="right" :min="0.1" :max="1" :step="0.1"
            @change="changeValue"></el-input-number>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Openness",
  data() {
    return {
      opennessOpen: false,
      openness: {
        radius: 100, //展示半径，单位：米，取值范围：[0.01~任意正数]，默认值：500
        opacity: 0.5, //透明度，取值范围：[0~1]，默认值：0.5
        // 颜色
        visibleColor: Color.Red, //可见区域的颜色，默认值：红色
        invisibleColor: Color.Green, //不可见区域的颜色，默认值：绿色
      },
    };
  },
  watch: {
    "$store.getters.eventSealAPI": {
      indeterminate: true,
      deep: true,
      handler(val) {
        if (val.eventtype == "ViewDome") {
          this.opennessOpen = true;
        }
      }
    },
  },
  mounted() {
    window.sealAPI._tools.startViewDomeAnalysis(this.openness);
    this.$message.success("开敞度分析: 选择分析中心点！");
  },
  methods: {
    // 开敞度参数调节
    changeValue(e) {
      window.sealAPI._tools.startViewDomeAnalysis(this.openness);
    },
    handleClose() {
      if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
    },
  },
  beforeDestroy() {
    window.sealAPI._tools.stopViewDomeAnalysis();
    this.handleClose();
  },
};
</script>

<style lang="scss" scoped>
@import "~@/views/cloud/page/css/func3.scss";

.warp-list {
  &:not(:first-child) {
    margin-top: 6px;
  }

  &.legend {
    margin-bottom: 16px;

    div {
      display: flex;
      align-items: center;

      label {
        display: inline-block;
        margin-left: 10px;
        width: 16px;
        height: 16px;
        border-radius: 50%;

        &.visibleColor {
          background: rgba(0, 210, 0, 0.8);
        }

        &.invisibleColor {
          background: red;
        }
      }

      &:not(:first-child) {
        margin-top: 10px;
      }
    }

    span {
      margin-right: 6px;
    }
  }

  .list {
    margin: 0 !important;
  }

  /deep/.el-slider {
    width: 82%;
    margin-right: 20px;
  }
}
</style>