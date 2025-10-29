<template>
  <k-dialog :dialog-title="dialogTitle" :dialog-visible="dialogVisible" @closeDialog="closeDialog">
    <template>
      <div class="AddImplementationDialog">
        <div class="form-container">
          <el-form ref="form" :model="form" label-width="260px">
            <el-form-item label="上报委办局">
              <el-input v-model="form.unitName"></el-input>
            </el-form-item>
            <el-form-item label="上报时间">
              <el-date-picker
                type="datetime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期"
                v-model="form.recordtime"
                style="width: 100%;">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="落实情况">
              <el-input type="textarea" v-model="form.reportContent"></el-input>
            </el-form-item>
            <el-form-item label="是否显示">
              <el-switch v-model="form.status"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">立即提交</el-button>
              <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </template>
  </k-dialog>
</template>

<script>
import Axios from '@/utils/request'
import KDialog from '../../components/KDialog'
export default {
  name: 'AddImplementationDialog',
  components: { KDialog },
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '新增落实情况',
      form: {
        unitName: '',
        recordtime: '',
        status: true,
        reportContent: ''
      }
    }
  },
  mounted() {
    this.$root.eventBus.$on('showAddImplementationDialog', (data) => {
      this.dialogVisible = true
    })
  },
  methods: {
    closeDialog: function (isRefresh) {
      this.dialogVisible = false
      this.$root.eventBus.$emit('closeAddLedOrImplementationDialog', isRefresh)
    },
    onSubmit() {
      if (!this.$store.getters.eventId.id) return
      // if (!this.form.unitName) {
      //   this.$message('请输入上报委办局')
      //   return
      // }
      if (!this.form.recordtime) {
        this.$message('请输入落实时间')
        return
      }
      if (!this.form.reportContent) {
        this.$message('请输入落实内容')
        return
      }
      let param = {
        reportContent: this.form.reportContent,
        recordtime: this.form.recordtime,
        eventId: this.$store.getters.eventId.id,
        unitName: this.form.unitName,
        status: '0'
      }
      param.status = this.form.status ? '0' : '1'
      let list = [param]
      let listStr = JSON.stringify(list)
      let postParam = {
        eventId: this.$store.getters.eventId.id,
        lists: listStr,
        isAdd: 0
      }
      let url = '/apis/leaderInstruction/updateLeaderInstructionImplementNew'
      Axios.post(url, postParam).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          this.$message({
            message: '添加成功！',
            type: 'success'
          })
          this.closeDialog(true)
          this.$root.eventBus.$emit('refreshIframe', {})
        } else {
          this.$message({
            message: '添加失败！',
            type: 'warning'
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .AddImplementationDialog {
    position: relative;
    width: 100%;
    height: 100%;
    .form-container {
      padding: 80px;
    }
  }
</style>
