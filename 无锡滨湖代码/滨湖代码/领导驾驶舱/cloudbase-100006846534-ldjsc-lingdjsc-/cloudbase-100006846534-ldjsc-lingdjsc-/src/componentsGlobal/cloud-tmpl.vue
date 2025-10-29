<template>
  <transition class="cloud-tmpl_root">
    <div class="cloud-tmpl" ref="cloudTmpl" :style="position" v-show="show">
      <div class="cloud-title">
        <div class="title-box" :class="{ moveHead: draggable }" @mousedown="mousedown" @mousemove="mousemove"
          @mouseup="mouseup" @mouseleave="mouseup">
          <p>{{ title }} </p>
          <i class="el-icon-loading" v-show="loading"></i>
        </div>
        <div class="close-btn" v-show="showClose">
          <i class="el-icon-close" @click.stop="closeEvent"></i>
        </div>
      </div>
      <div class="cloud-head">
        <!-- 头部插槽 -->
        <slot name="head"></slot>
      </div>
      <div class="cloud-content" :style="scrollSize">
        <el-scrollbar style="height: 100%">
          <div class="cloud-content-warp">
            <!-- 正文插槽-滚动体 -->
            <slot name="content"></slot>
          </div>
        </el-scrollbar>
      </div>
      <div class="cloud-foot">
        <!-- 底部插槽 -->
        <slot name="foot"></slot>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  // 全局-场景功能弹窗
  name: "CloudTmpl",
  props: {
    title: {
      type: String,
      default: 'TitleTitleTitleTitle',
    },
    top: {
      type: Number,
      default: 200,
    },
    right: {
      type: Number,
      default: 60,
    },
    width: {
      type: Number,
      default: 300,
    },
    scrollHeight: { // 滚动体高度
      type: Number,
      default: 50,
    },
    loading: {
      type: Boolean,
      default: false,
    },
		showClose: {
			type: Boolean,
      default: true,
		},
    draggable: {
      type: Boolean, // {tips} 拖拽性能待优化
      default: false,
    }
  },
  data() {
    return {
      cloudTmpl: null,
      show: true, //是否显示
      topPx: 200, //top:y
      rightPx: 100, //left:x
      leftOffset: 0, //鼠标距离移动窗口左侧偏移量
      topOffset: 0, //鼠标距离移动窗口顶部偏移量
      clientWidth: 0,
      clientHeight: 0,
      isMove: false, //是否移动标识
    }
  },
  computed: {
    position() {
      return `width:${this.width}px;top:${this.topPx}px;right:${this.rightPx}px;`
    },
    scrollSize() {
      return `height:${this.scrollHeight}vh;`
    },
  },
  watch: {
    top(val) {
      this.topPx = val;
    }
  },
  created() {
    this.topPx = this.top || 200;
    this.rightPx = this.right || 60;
    this.draggable && this.winSizeSet();
  },
  mounted() {
    this.cloudTmpl = this.$refs.cloudTmpl;
  },
  methods: {
    winSizeSet() {
      this.clientWidth = document.body.clientWidth;
      this.clientHeight = document.body.clientHeight;

      const that = this;
      function windowResize() {
        that.clientWidth = document.body.clientWidth;
        that.clientHeight = document.body.clientHeight;
      }
      window.addEventListener("resize", windowResize);
    },
    closeEvent(params) {
      // this.rightPx = 80;
      this.topPx = this.top || 200;
      this.rightPx = this.right || 80;
      // this.topPx = 200;
      this.show = !this.show;
      if (!this.show) this.$emit('close', params)
    },
    /* --- 鼠标按下事件 --- */
    mousedown(event) {
      if (!this.draggable) return;
      this.leftOffset = event.offsetX
      this.topOffset = event.offsetY
      this.isMove = true
    },
    /* --- 鼠标抬起 --- */
    mouseup() {
      this.isMove = false;
    },
    /* --- 鼠标移动 --- */
    mousemove(event) {
      if (!this.isMove) return;
      const rectObject = this.cloudTmpl.getBoundingClientRect();
      /*
       rectObject.top：元素上边到视窗上边的距离;
       rectObject.right：元素右边到视窗左边的距离;
       rectObject.bottom：元素下边到视窗上边的距离;
       rectObject.left：元素左边到视窗左边的距离;
       rectObject.width：是元素自身的宽
       rectObject.height 是元素自身的高
     */
      let topPx = event.clientY - this.topOffset;
      if (topPx > 0 && topPx < this.clientHeight - rectObject.height) this.cloudTmpl.style.top = topPx + "px";

      let rightPx = this.clientWidth - event.clientX + this.leftOffset - rectObject.width;
      if (rightPx > 0 && rightPx < this.clientWidth - rectObject.width) this.cloudTmpl.style.right = rightPx + "px";
    },
  }
}
</script>

<style lang="scss" scoped>
/deep/ .el-scrollbar__wrap {
  overflow-x: hidden;
}

.cloud-tmpl {
  position: fixed;
  z-index: 66666;
  // width: 300px;
  // right: 60px;
  // top: 50%;
  // transform: translateY(-50%);
  background: rgba(46, 52, 65, 0.8);
  box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
    0px 0px 0px 0px rgba(0, 0, 0, 0.5), 0px 2px 5px 0px rgba(38, 38, 44, 0.5);
  border-radius: 16px;
  color: #fff;
  pointer-events: auto;
  // transition: height 300ms ease-in-out;

  .cloud-title {
    height: 56px;
    line-height: 56px;
    font-size: 22px;
    color: #ffffff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    background: rgba(46, 52, 65, 0.8);
    border-top-left-radius: 15px;
    border-top-right-radius: 15px;

    .title-box {
      padding: 0 20px;
      display: inline-flex;
      align-items: center;

      p {
        padding-right: 10px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }

    .close-btn {
      padding: 0 20px;

      i {
        font-size: 8px;
        cursor: pointer;
        display: inline-block;
        width: 22px;
        height: 22px;
        background: rgba(0, 0, 0, 0.5);
        box-shadow: 0px 0px 5px 0px rgba(0, 0, 0, 0.5);
        border: 1px solid #ffffff;
        border-radius: 50%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }

  .cloud-head {
    padding: 16px 16px 0 16px;
  }

  .cloud-content {
    box-sizing: border-box;
    // transition: height 300ms ease-in-out;

    .cloud-content-warp {
      // height: 50vh;
      padding: 0 16px;
    }
  }

  .cloud-foot {
    padding: 0 16px 16px 16px;
  }
}

.moveHead {
  cursor: move;
}

.v-enter,
.v-leave-to {
  opacity: 0;
  transform: scale(0.5);
}

.content {
  padding: 10px;
}

.v-enter-active,
.v-leave-active {
  transition: all 0.3s ease;
}

// el-input 样式重绘
/deep/ .el-input {
  .el-input__inner {
    background: transparent;
    height: 32px;
    background: #494d52;
    border-radius: 4px;
    border: 1px solid #646b6f;
    font-size: 14px;
    color: #ffffff;
  }
}

// el-radio-group 样式重绘
/deep/ .el-radio-group {
  .el-radio__label {
    color: #ccc;
  }

  .el-radio {
    .is-checked {
      .el-radio__label {
        color: #409eff !important;
      }
    }
  }
}

// el-select 样式重绘
/deep/ .el-select {
  .el-input__inner {
    background: transparent;
    color: #409EFF;
  }
}

// el-input-number 样式重绘
/deep/ .el-input-number {
  .el-input__inner {
    background: transparent;
    color: #ccc;
    border: solid 1px #666;
  }

  .el-input-number__increase,
  .el-input-number__decrease {
    background: rgba(46, 52, 65, 0.8);
    border: 0;
    line-height: 14px;

    i {
      color: #eee;
    }
  }

  .el-input-number__increase {
    margin-top: 4px;
  }

  .el-input-number__decrease {
    margin-bottom: 4px;
  }
}

// /*定义滚动条高宽及背景 高宽分别对应横竖滚动条的尺寸*/
// ::-webkit-scrollbar {
//   width: 5px;
//   background-color: rgba(14, 73, 121, 0.2) !important;
// }

// /*定义了滚动条滑块的样式*/
// ::-webkit-scrollbar-thumb {
//   border-radius: 0;
//   border-width: 1.5px;
//   background-clip: padding-box;
//   background: #ededed;
// }
</style>
