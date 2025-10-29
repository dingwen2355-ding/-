<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="receiveDialog"
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
          v-if="receive === '接收'"
        >
          <!-- <el-form-item label="接收状态" prop="nodeId">
            <el-input clearable v-model="formData1.nodeId" style="width:340px" :readonly="true"></el-input>
          </el-form-item>-->

          <el-form-item label="处理备注" prop="dealInfo">
            <el-input type="textarea" :rows="2" v-model="formData1.dealInfo" style="width: 340px"></el-input>
          </el-form-item>
        </el-form>
        <el-form
          :inline="true"
          ref="formData2"
          :rules="rules2"
          class="storage-form"
          :model="formData2"
          label-width="110px"
          style="margin-top: 10px"
          :disabled="title === '详情'"
          v-else
        >
          <!-- <el-form-item label="接收状态" prop="nodeId">
            <el-input clearable v-model="formData2.nodeId" style="width:340px" :readonly="true"></el-input>
          </el-form-item>-->

          <el-form-item label="处理备注" prop="dealInfo">
            <el-input type="textarea" :rows="2" v-model="formData2.dealInfo" style="width: 340px"></el-input>
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
import qs from 'qs'
export default {
  name: 'receiveDialog',
  props: ['receiveDialog', 'receiveDialogData', 'receiveMessage'],
  components: {},
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      title: '接收信息',
      formData1: {
        nodeId: '接收',
        dealInfo: '已接收，会尽快处理'
      },
      rules1: {},
      formData2: {
        nodeId: '吹哨处置',
        dealInfo: ''
      },
      rules2: {
        dealInfo: [{ required: true, message: '请输入处理备注', trigger: 'blur' }]
      },
      receive: ''
    }
  },
  watch: {
    receiveDialog(v) {
      this.dialogVisible = v
      if (v) {
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
      }
    },
    receiveDialogData(v) {
      this.tableData = JSON.parse(JSON.stringify(v))
    },
    receiveMessage(v) {
      this.receive = v
      if (v === '接收') {
        this.title = v
      } else {
        this.title = '吹哨处置'
      }
    }
  },
  methods: {
    // 确定按钮
    handleOk() {
      let eventIds = ''
      let taskId = ''
      let type = Object.prototype.toString.call(this.tableData)
      if (type === '[object Object]') {
        eventIds = this.tableData.eventId
        taskId = this.tableData.taskId
      } else {
        this.tableData.some((e) => {
          eventIds += e.eventId + ','
          taskId += e.taskId + ','
        })
        eventIds = eventIds.substring(0, eventIds.lastIndexOf(','))
        taskId = taskId.substring(0, taskId.lastIndexOf(','))
      }
      // console.log(this.receive)
      if (this.receive === '吹哨处置(派遣)') {
        this.$refs['formData2'].validate((valid) => {
          if (valid) {
            let params = {
              eventIds: eventIds,
              isWhistle: '1',
              distributeInfo: this.formData2.dealInfo,
              taskId: taskId
            }
            let url = '/binhuapis/event/dispatch'
            axios.post(url, qs.stringify(params)).then((r) => {
              if (r.data.code === 200) {
                this.$notify({
                  title: '成功',
                  message: '吹哨处置成功',
                  type: 'success',
                  duration: 1000
                })
                this.$emit('success')
                this.handleClose()
              } else {
                this.$notify({
                  title: '警告',
                  message: '吹哨处置失败，' + r.data.message,
                  type: 'warning',
                  duration: 1000
                })
              }
            })
          } else {
            this.$notify({
              title: '警告',
              message: '检查表单输入内容的合法性！',
              type: 'warning',
              duration: 1000
            })
            // console.log('error submit!!')
            return false
          }
        })
      } else if (this.receive === '吹哨处置(处置)') {
        this.$refs['formData2'].validate((valid) => {
          if (valid) {
            let params = {
              eventIds: eventIds,
              nodeId: '10',
              dealInfo: this.formData2.dealInfo,
              taskId: taskId
            }
            let url = '/binhuapis/event/confirm'
            axios.post(url, qs.stringify(params)).then((r) => {
              if (r.data.code === 200) {
                this.$notify({
                  title: '成功',
                  message: '吹哨处置成功',
                  type: 'success',
                  duration: 1000
                })
                this.$emit('success')
                this.handleClose()
              } else {
                this.$notify({
                  title: '警告',
                  message: '吹哨处置失败，' + r.data.message,
                  type: 'warning',
                  duration: 1000
                })
              }
            })
          } else {
            this.$notify({
              title: '警告',
              message: '检查表单输入内容的合法性！',
              type: 'warning',
              duration: 1000
            })
            // console.log('error submit!!')
            return false
          }
        })
      } else {
        let params = {
          eventIds: eventIds,
          nodeId: '50',
          dealInfo: this.formData1.dealInfo,
          taskId: taskId
        }
        let url = '/binhuapis/event/confirm'
        axios.post(url, qs.stringify(params)).then((r) => {
          if (r.data.code === 200) {
            // console.log(r)
            this.$notify({
              title: '成功',
              message: '接收成功',
              type: 'success',
              duration: 1000
            })
            this.$emit('success')
            this.handleClose()
          } else {
            this.$notify({
              title: '警告',
              message: '接收失败，' + r.data.message,
              type: 'warning',
              duration: 1000
            })
          }
        })
      }
    },
    handleClose() {
      this.formData1 = {
        nodeId: '接收',
        dealInfo: '已接收，会尽快处理'
      }
      this.formData2 = {
        nodeId: '吹哨处置',
        dealInfo: ''
      }
      this.$emit('update:receiveDialogData', {})
      this.$emit('update:receiveMessage', '')
      this.$emit('update:receiveDialog', false)
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss" scoped>
.receiveDialog {
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
