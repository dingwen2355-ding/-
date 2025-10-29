<template>
  <k-dialog :dialog-title="dialogTitle" :dialog-visible="dialogVisible" @closeDialog="closeDialog">
    <template>
      <div class="AddRiskDialog">
        <div class="form-container">
          <el-form ref="form" :model="form" label-width="260px">
            <el-form-item label="风险类型">
              <el-input v-model="form.riskType"></el-input>
            </el-form-item>
            <el-form-item label="发现时间">
              <el-date-picker
                type="datetime"
                format="yyyy-MM-dd HH:mm:ss"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期"
                v-model="form.foundTime"
                style="width: 100%;">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="风险预警">
              <el-input type="textarea" v-model="form.riskInsight"></el-input>
            </el-form-item>
            <el-form-item label="处置建议">
              <el-input type="textarea" v-model="form.dealSuggest"></el-input>
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
  name: 'AddRiskDialog',
  components: { KDialog },
  data() {
    return {
      dialogVisible: false,
      dialogTitle: '新增风险预警',
      form: {
        riskType: '',
        foundTime: '',
        riskInsight: '',
        dealSuggest: '',
        status: true
      }
    }
  },
  mounted() {
    this.$root.eventBus.$on('showAddRiskDialog', (data) => {
      this.dialogVisible = true
    })
  },
  methods: {
    closeDialog: function (isRefresh) {
      this.dialogVisible = false
      this.$root.eventBus.$emit('closeAddRiskDialog', isRefresh)
    },
    onSubmit() {
      if (!this.$store.getters.eventId.id) return
      if (!this.form.riskType) {
        this.$message('请输入风险类型')
        return
      }
      if (!this.form.riskInsight) {
        this.$message('请输入风险预警')
        return
      }
      if (!this.form.dealSuggest) {
        this.$message('请输入处置建议')
        return
      }
      let param = {
        deal_suggest: this.form.dealSuggest,
        risk_insight: this.form.riskInsight,
        event_id: this.$store.getters.eventId.id,
        found_time: this.form.foundTime,
        risk_type: this.form.riskType,
        status: '0'
      }
      param.status = this.form.status ? '0' : '1'
      let list = [param]
      let listStr = JSON.stringify(list)
      // let url = '/apis/risk/updateRiskInsightInfo?eventId=' + this.$store.getters.eventId.id + '&lists=' + listStr + '&isAdd=0'
      let url = '/apis/risk/updateRiskInsightInfo'
      let obj = {
        eventId: this.$store.getters.eventId.id,
        lists: listStr,
        isAdd: 0
      }
      Axios.post(url, obj).then((res) => {
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
  .AddRiskDialog {
    position: relative;
    width: 100%;
    height: 100%;
    .form-container {
      padding: 80px;
    }
  }
</style>
