<template>
  <el-dialog
    :title="'领导指示/上级要求新增'"
    :visible.sync="dialogVisible"
    :modal="false"
    width="431rem"
    height="570rem"
    class="showLookDialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div>
      <div class="form-container" style="margin-top: 20rem">
        <el-form ref="form" :model="form" label-width="100rem">
          <el-form-item label="批示领导">
            <el-input v-model="form.leaderName"></el-input>
          </el-form-item>
          <el-form-item label="批示时间">
            <el-date-picker
              type="datetime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期"
              v-model="form.recordtime"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="批示内容">
            <el-input type="textarea" v-model="form.instructionContent"></el-input>
          </el-form-item>
          <el-form-item label="是否显示">
            <el-switch v-model="form.status"></el-switch>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提交</el-button>
            <el-button @click="dialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'
export default {
  props: ['dialogVisibleFlag', 'showLeadersList'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      if (v) {
        this.form = {
          leaderName: '',
          recordtime: '',
          status: true,
          instructionContent: ''
        }
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        leaderName: '',
        recordtime: '',
        status: true,
        instructionContent: ''
      }
    }
  },
  methods: {
    onSubmit() {
      if (!this.$store.getters.eventId.id) return
      if (!this.form.leaderName) {
        this.$message('请输入批示领导')
        return
      }
      if (!this.form.recordtime) {
        this.$message('请输入批示时间')
        return
      }
      if (!this.form.instructionContent) {
        this.$message('请输入批示内容')
        return
      }
      let param = {
        instructionContent: this.form.instructionContent,
        recordtime: this.form.recordtime,
        eventId: this.$store.getters.eventId.id,
        leaderName: this.form.leaderName,
        status: '0'
      }
      param.status = this.form.status ? '0' : '1'
      if (!this.$store.getters.eventId.id) return
      // let list = []
      // list.push(param)
      this.showLeadersList.push(param)
      let listStr = JSON.stringify(this.showLeadersList)
      let postParam = {
        eventId: this.$store.getters.eventId.id,
        lists: listStr,
        isAdd: 1
      }
      let url = '/apis/leaderInstruction/updateLeaderInstructionNew'
      Axios.post(url, postParam).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          this.$message({
            message: '更新成功！',
            type: 'success'
          })
          this.$root.Bus.$emit('updateRefresh', true)
          this.handleClose()
        } else {
          this.$message({
            message: '更新失败！',
            type: 'warning'
          })
        }
      })
    },
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss">
.showLookDialog {
  .el-dialog__title {
    font-size: 16rem;
  }
  .el-dialog__body {
    padding: 0 30rem 30rem;
    height: 400rem;
    overflow: hidden;
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
</style>
