<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="informationDetailsDialog"
      :title="title"
      :visible.sync="dialogVisible"
      width="1550px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-collapse v-model="active">
          <el-collapse-item title="● 告警信息" name="1">
            <el-form
              :inline="true"
              ref="formData1"
              :rules="rules1"
              class="storage-form"
              :model="formData1"
              label-width="125px"
              style="margin-top: 10px"
            >
              <el-form-item label="预警编号" prop="alarmId">
                <el-input clearable v-model="formData1.alarmId" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="预警类型" prop="announcementType">
                <el-input clearable v-model="formData1.announcementType" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="接收状态" prop="isAccept">
                <el-input clearable v-model="formData1.isAccept" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="预警内容" prop="content">
                <el-input clearable v-model="formData1.content" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="推送部门" prop="department">
                <el-input clearable v-model="formData1.department" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="推送人" prop="pushPersonName">
                <el-input clearable v-model="formData1.pushPersonName" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="推送人电话" prop="pushPhone">
                <el-input clearable v-model="formData1.pushPhone" :readonly="true"></el-input>
              </el-form-item>

              <el-form-item label="创建时间" prop="create_time">
                <el-date-picker
                  v-model="formData1.create_time"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :readonly="true"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="创建人" prop="creator">
                <el-input clearable v-model="formData1.creator" :readonly="true"></el-input>
              </el-form-item>
            </el-form>
          </el-collapse-item>

          <el-collapse-item title="● 事件基础信息" name="2">
            <el-form
              :inline="true"
              ref="formData2"
              :rules="rules2"
              class="storage-form"
              :model="formData2"
              label-width="110px"
              style="margin-top: 10px"
              :disabled="title === '派遣'"
            >
              <el-form-item label="事件编号" prop="taskId">
                <el-input clearable v-model="formData2.eventId" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="案卷编号" prop="recordId">
                <el-input clearable v-model="formData2.recordId" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件名称" prop="eventName">
                <el-input clearable v-model="formData2.eventTitle" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件状态" prop="eventState">
                <span class="stateBorder">
                  <span :style="cerrorColor(formData2.eventState)">{{ formData2.eventState }}</span>
                  <span
                    :class="[
                 formData2.overdueStateId === '30' ? 'overdueStatRed' : '',
                  formData2.overdueStateId === '20' ? 'overdueStatyellow' : ''
                ]"
                    v-if="formData2.overdueStateId !== '10' &&formData2.overdueStateId !== null && formData2.overdueStateId !== null"
                  >({{ formData2.overdueState }})</span>
                </span>
              </el-form-item>
              <el-form-item label="事件位置描述" prop="address">
                <el-input type="textarea" :rows="2" v-model="formData2.address" style="width: 220px" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="经度坐标" prop="gpsx">
                <el-input clearable v-model="formData2.gpsx" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="纬度坐标" prop="gpsy">
                <el-input clearable v-model="formData2.gpsy" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="所属社区" prop="precinctName">
                <el-input clearable v-model="formData2.precinctName" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件详细描述" prop="eventDesc">
                <el-input
                  type="textarea"
                  :rows="2"
                  v-model="formData2.eventDesc"
                  style="width: 1260px"
                  :readonly="true"
                ></el-input>
              </el-form-item>
              <el-form-item label="事件标签" prop="eventTag">
                <el-input clearable v-model="formData2.eventTag" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="主题名称" prop="topicName">
                <el-input clearable v-model="formData2.topicName" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件类型" prop="eventType" style="margin-right: 200px">
                <el-input clearable v-model="formData2.eventType" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件级别" prop="level">
                <el-input clearable v-model="formData2.level" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件破坏程度" prop="damageDegree">
                <el-input clearable v-model="formData2.damageDegree" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件上报渠道" prop="channel" style="margin-right: 200px">
                <el-input clearable v-model="formData2.channel" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="上报人姓名" prop="reportPerson">
                <el-input clearable v-model="formData2.reportPerson" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="上报人电话" prop="reportPhone">
                <el-input clearable v-model="formData2.reportPhone" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件上报来源" prop="reportSource">
                <el-input clearable v-model="formData2.reportSource" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="信息来源" prop="infoSource">
                <el-input clearable v-model="formData2.infoSource" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件发生时间" prop="startTime">
                <el-date-picker
                  v-model="formData2.startTime"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :readonly="true"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="事件上报时间" prop="syncTime">
                <el-date-picker
                  v-model="formData2.syncTime"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :readonly="true"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="处理截止时间" prop="endTime">
                <el-date-picker
                  v-model="formData2.endTime"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :readonly="true"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="事件结案时间" prop="eventEndTime" v-if="title === '事件详情'">
                <el-date-picker
                  v-model="formData2.eventEndTime"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :readonly="true"
                ></el-date-picker>
              </el-form-item>
            </el-form>
            <!-- 附件 -->
            <div class="files">
              <span v-if="formData1.files">事件附件</span>
              <AttachmentUpload @success="siteImg" :imgUrl="formData1.files" title="事件详情" />
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <!-- <el-button type="primary" @click="handleOk">确 定</el-button> -->
      </div>
    </el-dialog>
  </div>
</template>
<script>
import AttachmentUpload from '../../components/attachmentUpload.vue'
export default {
  name: 'planSet',
  props: ['detailsDialog', 'detailsDialogData'],
  components: { AttachmentUpload },
  data() {
    return {
      dialogVisible: false,
      tableData: {},
      title: '告警详情',
      active: ['1', '2'],
      formData1: {},
      rules1: {},
      formData2: {},
      rules2: {}
    }
  },
  computed: {
    cerrorColor() {
      return (v) => {
        let color = ''
        switch (v) {
          case '10': // 报废
            color = { color: '#B0B0B0' }
            break
          case '20': //  已反馈
            color = { color: '#231FB3' }
            break
          case '30': // 待派遣
            color = { color: '#0FB4F0' }
            break
          case '40': // 待接收
            color = { color: '#0005BD' }
            break
          case '50': // 处置中
            color = { color: '#7030A0' }
            break
          case '60': // 已处置
            color = { color: '#1FA159' }
            break
          default:
            color = { color: '#000000' }
            break
        }
        return color
      }
    }
  },
  watch: {
    detailsDialog(v) {
      this.dialogVisible = v
      if (v) {
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
        console.log(this.detailsDialogData, '----------------------------')
        this.formData1 = JSON.parse(JSON.stringify(this.detailsDialogData))
        this.formData2 = JSON.parse(JSON.stringify(this.detailsDialogData))
      }
    },
    detailsDialogData(v) { }
  },
  methods: {
    // 确定按钮
    handleOk() { },
    handleClose() {
      this.$emit('update:detailsDialog', false)
    },
    siteImg(r) {
      this.formData1.sitePicUrl = r
    }
  }
}
</script>
<style lang="scss" scoped>
.informationDetailsDialog {
  .files {
    margin-left: 40px;
  }
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 550px;
    .stateBorder {
      display: inline-block;
      width: 220px;
      border-radius: 5px;
      border: 1px solid #dcdfe6;
      padding-left: 10px;
    }
    .information {
      width: 100%;
      height: 48px;
      line-height: 48px;
      font-size: 18px;
      color: rgb(114, 118, 123);
      font-weight: 500;
      border-bottom: 1px solid #ebeef5;
      cursor: pointer;
    }

    .informationOne {
      border-top: 1px solid #ebeef5;
    }

    .inform_base {
      width: 100%;
      padding: 10px;
      border-bottom: 1px solid #ebeef5;
    }

    // /deep/ .el-collapse-item__arrow {
    //   display: none;
    // }

    // /deep/ .el-collapse-item__header {
    //   font-size: 18px;
    //   color: rgb(114, 118, 123);
    //   font-weight: 500;
    // }
  }
}
</style>
