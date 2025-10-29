<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="dealDialog"
      :title="title"
      :visible.sync="dialogVisible"
      width="700px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-form
          :inline="true"
          ref="formData1"
          :rules="rules1"
          class="storage-form"
          :model="formData1"
          label-width="110px"
          style="margin-top: 10px"
          :disabled="title === '详情'"
        >
          <el-form-item label="告警状态" prop="eventState">
            <el-input disabled clearable v-model="formData1.eventState" style="width: 340px"></el-input>
          </el-form-item>
          <el-form-item label="告警内容" prop="remark">
            <el-input clearable v-model="formData1.remark" style="width: 340px"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/utils/request'
export default {
  name: 'dealDialog',
  props: ['dealDialog', 'dealDialogData'],
  components: {},
  data() {
    return {
      dialogVisible: false,
      tableData: {},
      title: '事件催办',
      active: '1',
      formData1: {
        eventState: '催办告警',
        remark: ''
      },
      rules1: {}
    }
  },
  watch: {
    dealDialog(v) {
      this.dialogVisible = v
      if (v) {
        this.tableData = JSON.parse(JSON.stringify(this.dealDialogData))
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
      }
    },
    dealDialogData(v) { }
  },
  methods: {
    // 确定按钮
    handleOk() {
      let params = [
        {
          event_id: this.tableData.eventId,
          reportSource: this.tableData.reportSource,
          grid_name: this.tableData.gridName,
          deal_person_name: this.tableData.dealPersonName,
          infoSource: this.tableData.infoSource,
          deadline_time: this.tableData.deadlineTime,
          deal_dept_id: this.tableData.dealDeptId,
          deal_person_id: this.tableData.dealPersonId,
          remark: this.formData1.remark,
          userId: JSON.parse(sessionStorage.getItem('userId'))
        }
      ]
      let url = ''
      url = '/binhuapis/alarmInform/installAlarmInform'
      axios.post(url, params).then((r) => {
        if (r.data.code === 200) {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success',
            duration: 1000
          })
          this.$emit('success')
          this.$emit('update:dealDialog', false)
          this.formData1 = {
            eventState: '催办告警',
            remark: ''
          }
        } else {
          this.$notify.error({
            title: '错误',
            message: r.data.message,
            duration: 1000
          })
        }
      })
    },
    handleClose() {
      this.formData1 = {
        eventState: '催办告警',
        remark: ''
      }
      this.$emit('update:dealDialogData', {})
      this.$emit('update:dealDialog', false)
    }
  }
}
</script>
<style lang="scss" scoped>
.dealDialog {
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 150px;
    display: flex;
    .leftCard {
      width: 10%;
      height: 100%;
      .steps {
        height: 500px;
      }
    }
    .rightCard {
      overflow: auto;
      overflow-x: hidden;
      width: 97%;
      height: 100%;
      margin-left: 10px;
    }
  }
}
</style>
