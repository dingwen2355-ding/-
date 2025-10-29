<template>
  <el-dialog
    title="发送短信"
    :visible.sync="dialogVisible"
    :modal="false"
    width="550rem"
    class="messageconferencing-dialog"
    :append-to-body="true"
    :before-close="handleClose"
  >
    <div class="container">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100rem">
        <el-form-item label="号码" prop="messageTheme">
          <el-input placeholder="请输入号码" v-model="ruleForm.messageTheme"></el-input>
        </el-form-item>
        <el-form-item label="短信" prop="messageCnt">
          <el-input type="textarea" placeholder="请输入短信" resize="none" v-model="ruleForm.messageCnt" disabled></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="footer">
      <div @click="handleClose">取 消</div>
      <div @click="handleOk">发 送</div>
    </div>
  </el-dialog>
</template>

<script>
import Axios from '@/utils/request'

export default {
  props: ['dialogMessageVisible', 'infomation'],
  watch: {
    dialogMessageVisible(v) {
      if (v) {
        this.dialogVisible = v
        console.log(this.infomation, this.$store.getters.eventInfo)
        let phone = this.infomation.phone ? (this.infomation.phone.substring(3, 0)) + '****' + (this.infomation.phone.substring(7)) : ''
        this.ruleForm.messageTheme = phone
        let message = this.$store.getters.eventInfo.obj
        this.info = this.$store.getters.eventInfo.obj
        this.ruleForm.messageCnt = '您收到一条' + message.level + '突发事件，' + '请立即前往' + message.address + '查看。' + message.eventTitle + '，请立即处理。'
      }
    }
  },
  data() {
    return {
      dialogVisible: false,
      ruleForm: {
        messageTheme: '',
        messageCnt: ''
      },
      info: {},
      rules: {
        messageTheme: [{ required: true, message: '请输入号码', trigger: 'blur' }],
        messageCnt: [{ required: true, message: '请输入短信', trigger: 'blur' }]
      }
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.ruleForm.messageTheme = ''
      this.ruleForm.messageCnt = ''
      this.$refs.ruleForm.resetFields()
      this.$emit('update:dialogMessageVisible', false)
    },
    handleOk() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let url = '/apis/SMS/send?phones=' + (this.ruleForm.messageTheme.indexOf('**') !== -1 ? this.infomation.phone : this.ruleForm.messageTheme) +
          '&code=' + (this.info.level + '突发事件') + '&appName=' + this.info.address + '&action=' + this.info.eventTitle
          Axios.get(url).then((res) => {
            if (res.data.code === 200) {
              this.$message.success('发送短信成功')
              this.handleClose()
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
      //   this.$confirm('是否确认发送?', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(() => {
      //     this.send()
      //     this.handleClose()
      //   })
    }
  }
}
</script>

<style lang='scss'>
.messageconferencing-dialog {
  .el-dialog__title {
    font-size: 20rem;
  }
  .el-form-item__error {
    font-size: 16rem;
  }
  .el-dialog__body {
    height: 210rem !important;
    .el-textarea,
    .el-input {
      width: 93%;
    }
  }
  .el-textarea.is-disabled .el-textarea__inner {
    background: rgba(45, 121, 199, 0.2);
    border: 1rem solid #42678d;
  }
  .container {
    display: flex;
    margin-left: -26rem;
    .el-form-item {
      width: 556rem;
      margin-bottom: 25rem;
    }
    .head {
      width: 170rem;
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        width: 130rem;
        height: 130rem;
      }
    }
    .list {
      flex: 1;
      .item {
        margin-top: 10rem;
        font-size: 16rem;
        display: flex;
        line-height: 28rem;
        padding: 0 10rem;
        border-bottom: 1rem solid #066469;
        justify-content: space-between;
        // .left {
        // }
        .right {
          color: #00ffff;
        }
      }
    }
  }
  .el-textarea__inner {
    background: rgba(45, 121, 199, 0.2);
    border: 1rem solid #42678d;
    border-radius: 4rem;
    color: #00deff;
    font-size: 22rem;
    font-weight: 600;
    height: 180rem;
  }
  .el-dialog__footer {
    padding: 110rem 0rem 20rem;
    margin-left: -20rem;
  }
  .footer {
    display: flex;
    margin-left: 193rem;
    div {
      width: 95rem;
      height: 45rem;
      background: rgba(4, 12, 28, 0.4);
      border: 1rem solid #3df8f8;
      border-radius: 4rem;
      font-size: 22rem;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #3df8f8;
      line-height: 45rem;
      text-align: center;
      margin-right: 10rem;
      cursor: pointer;
      &:hover {
        background: rgba(61, 248, 248, 0.4);
      }
    }
  }
}
</style>
