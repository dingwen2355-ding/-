<template>
  <el-dialog
    title="指令详情"
    :visible.sync="dialogVisible"
    :modal="false"
    height="570rem"
    :class="showBig ? 'videoMeetingDialog' : 'videoMeetingDialog2'"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div>
      <div style="position: absolute;top: 20rem;right: 65rem;cursor: pointer" @click="tick" v-if="showBig">全屏</div>
      <div style="position: absolute;top: 20rem;right: 65rem;cursor: pointer" @click="tick" v-else>缩小</div>
      <div class="form-container">
        <iframe id="iframe" :src="form.url" frameborder="no" scrolling="no" allowtransparency="true"></iframe>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: ['dialogVisibleFlag', 'videoMeetingData'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.showBig = true
        this.form = this.videoMeetingData
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      showBig: true,
      form: {}
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      window.parent.postMessage('全屏关闭', '*')
      this.$emit('update:dialogVisibleFlag', false)
    },
    tick() {
      if (this.showBig === true) {
        window.parent.postMessage('全屏打开', '*')
      } else {
        window.parent.postMessage('全屏关闭', '*')
      }
      this.showBig = !this.showBig
    }
  }
}
</script>

<style lang="scss">
.videoMeetingDialog {
  .el-dialog__title {
    font-size: 16rem;
  }
  .form-container {
    margin-top: 20rem;
    height: 500rem;
    overflow: scroll;
  }
  .el-dialog__body {
    padding: 0 30rem 30rem;
    height: 90%;
    overflow: hidden;
    #iframe {
      width: 100%;
      height: 80vh;
    }
    .el-textarea__inner {
      background-color: #0d2c4c;
      border: 1rem solid #42678d;
      font-size: 16rem;
      color: #01caeb;
      min-height: 110rem !important;
    }
    .el-form-item__content {
      .el-button:first-of-type {
        width: 70rem;
        height: 32rem;
        line-height: 7rem;
        border-radius: 2rem;
        background: #0d365f;
        box-sizing: border-box;
        border: 1rem solid #02a7fc;
        color: #b7e6ff;
      }
      .el-button:last-of-type {
        width: 70rem;
        height: 34rem;
        line-height: 7rem;
        border-radius: 2rem;
        background: #082239;
        box-sizing: border-box;
        border: 1rem solid #0d365f;
        color: #9ab1cb;
      }
    }
  }
  .box-2 {
    ul {
      display: flex;
      float: right;
      li {
        width: 252rem;
        span {
          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          &:nth-of-type(1) {
            color: #035561;
          }
        }
      }
    }

    .time-box {
      display: flex;
      padding: 25rem 0rem 0rem 55rem;
      .time-item {
        width: 76rem;
        // height: 28rem;
        background: rgba(61, 248, 248, 0.2);
        border: 2rem solid #035561;
        font-size: 16rem;
        line-height: 28rem;
        text-align: center;
        cursor: pointer;
      }
      .active {
        border: 2rem solid #00ffff;
        // background: linear-gradient(180deg, #28d9ff 0%, rgba(40, 217, 255, 0.2) 100%);
        background-image: radial-gradient(rgba(8, 36, 36, 0.2) 40%, rgba(89, 230, 240, 0.39));
        color: #00ffff;
      }
    }
  }
  #echart {
    width: 100%;
    height: 400rem;
  }
  .el-table th {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00ffff;
    background: #1c2331;
    height: 50rem;
  }
  .el-table td {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
    height: 67rem;
    background: #171d2b;
  }
  .el-table th.is-leaf,
  .el-table th.el-table__cell.is-leaf {
    border: 0 !important;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #171d2b !important;
  }
  .el-table,
  .el-table__expanded-cell {
    background-color: #1c2331;
  }
  .el-table th.is-leaf {
    border-top: 1rem solid #28feff;
    border-bottom: 1rem solid #28feff;
  }
  .el-table td {
    border-bottom: 1rem solid #28feff;
  }
  .el-table::before {
    height: 0;
  }
  .el-table__body-wrapper {
    height: 470rem !important;
    overflow: auto;
  }
  .historyList .el-input__inner {
    font-size: 14rem;
    color: #fff;
  }
  .historyList .el-select .el-input__inner {
    width: 140rem;
    color: #fff;
  }
  .el-form--inline .el-form-item__label {
    font-size: 16rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00efff;
  }
  .el-range-editor .el-range-input {
    background: #0c223e;
    color: #fff;
    margin-left: 8rem;
  }
  .el-date-editor .el-range-separator {
    color: #fff;
  }
  .footer {
    margin-top: 27rem;
    text-align: center;
    .el-pagination__jump {
      color: #ffffff;
    }
  }
}
.videoMeetingDialog2 {
   .el-dialog {
    margin-top: 1vh !important;
    width: 99%;
    height: 97%;
    margin-bottom: 0rem;
  }
  .el-dialog__title {
    font-size: 16rem;
  }
  .el-dialog__body {
    padding: 0 30rem 30rem;
    height: 95%;
    overflow: hidden;
    #iframe {
      width: 100%;
      height: 88vh;
    }
    .el-textarea__inner {
      background-color: #0d2c4c;
      border: 1rem solid #42678d;
      font-size: 16rem;
      color: #01caeb;
      min-height: 110rem !important;
    }
    .el-form-item__content {
      .el-button:first-of-type {
        width: 70rem;
        height: 32rem;
        line-height: 7rem;
        border-radius: 2rem;
        background: #0d365f;
        box-sizing: border-box;
        border: 1rem solid #02a7fc;
        color: #b7e6ff;
      }
      .el-button:last-of-type {
        width: 70rem;
        height: 34rem;
        line-height: 7rem;
        border-radius: 2rem;
        background: #082239;
        box-sizing: border-box;
        border: 1rem solid #0d365f;
        color: #9ab1cb;
      }
    }
  }
  .box-2 {
    ul {
      display: flex;
      float: right;
      li {
        width: 252rem;
        span {
          font-size: 16rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          &:nth-of-type(1) {
            color: #035561;
          }
        }
      }
    }

    .time-box {
      display: flex;
      padding: 25rem 0rem 0rem 55rem;
      .time-item {
        width: 76rem;
        // height: 28rem;
        background: rgba(61, 248, 248, 0.2);
        border: 2rem solid #035561;
        font-size: 16rem;
        line-height: 28rem;
        text-align: center;
        cursor: pointer;
      }
      .active {
        border: 2rem solid #00ffff;
        // background: linear-gradient(180deg, #28d9ff 0%, rgba(40, 217, 255, 0.2) 100%);
        background-image: radial-gradient(rgba(8, 36, 36, 0.2) 40%, rgba(89, 230, 240, 0.39));
        color: #00ffff;
      }
    }
  }
  #echart {
    width: 100%;
    height: 400rem;
  }
  .el-table th {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00ffff;
    background: #1c2331;
    height: 50rem;
  }
  .el-table td {
    font-size: 14rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #ffffff;
    height: 67rem;
    background: #171d2b;
  }
  .el-table--enable-row-hover .el-table__body tr:hover > td {
    background-color: #171d2b !important;
  }
  .el-table,
  .el-table__expanded-cell {
    background-color: #1c2331;
  }
  .el-table th.is-leaf {
    border-top: 1rem solid #28feff;
    border-bottom: 1rem solid #28feff;
  }
  .el-table td {
    border-bottom: 1rem solid #28feff;
  }
  .el-table::before {
    height: 0;
  }
  .el-table__body-wrapper {
    height: 470rem !important;
    overflow: auto;
  }
  .historyList .el-input__inner {
    font-size: 14rem;
    color: #fff;
  }
  .historyList .el-select .el-input__inner {
    width: 140rem;
    color: #fff;
  }
  .el-form--inline .el-form-item__label {
    font-size: 16rem;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #00efff;
  }
  .el-range-editor .el-range-input {
    background: #0c223e;
    color: #fff;
    margin-left: 8rem;
  }
  .el-date-editor .el-range-separator {
    color: #fff;
  }
  .footer {
    margin-top: 27rem;
    text-align: center;
    .el-pagination__jump {
      color: #ffffff;
    }
  }
}
</style>
