<template>
  <k-dialog :dialog-title="dialogTitle" :dialog-visible="dialogVisible" @closeDialog="closeDialog">
    <template>
      <div class="AddLedDialog">
        <div class="form-container">
          <el-form ref="form" :model="form" label-width="260px">
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
                style="width: 100%;">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="批示内容">
              <el-input type="textarea" v-model="form.instructionContent"></el-input>
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
// import Axios from '@/utils/request'
import KDialog from '../../components/KDialog'
export default {
  name: 'AddLedDialog',
  components: { KDialog },
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '新增指示/要求',
      form: {
        leaderName: '',
        recordtime: '',
        status: true,
        instructionContent: ''
      }
    }
  },
  mounted() {
    this.$root.eventBus.$on('showAddLedDialog', (data) => {
      this.dialogVisible = true
    })
  },
  methods: {
    closeDialog: function (isRefresh) {
      this.dialogVisible = false
      // this.$root.eventBus.$emit('closeAddLedOrImplementationDialog', isRefresh)
    },
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
      // let list = [param]
      // let listStr = JSON.stringify(list)
      // let postParam = {
      //   eventId: this.$store.getters.eventId.id,
      //   lists: listStr,
      //   isAdd: 0
      // }
      this.$root.eventBus.$emit('showLeaders', param)
      this.closeDialog(true)
      this.$root.eventBus.$emit('refreshIframe', {})
    }
  }
}
</script>

<style lang="scss" scoped>
  .AddLedDialog {
    position: relative;
    width: 100%;
    height: 100%;
        .el-button {
  font-size: 40px;
  line-height: 80px;
  padding: 0 40px;
}
    .form-container {
      padding: 80px;
    }
  }
</style>
