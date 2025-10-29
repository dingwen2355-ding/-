<template>
  <div class="qrCode">
    <el-collapse v-model="active">
      <el-collapse-item title="● 事件基础信息" name="1">
        <el-form
          :inline="true"
          ref="formData1"
          :rules="rules1"
          class="storage-form"
          :model="formData1"
          label-width="1.3rem"
          style="margin-top: 0.2rem"
          :disabled="title === '派遣'"
        >
          <el-form-item label="事件名称" prop="eventName">
            <el-input clearable v-model="formData1.eventName"></el-input>
          </el-form-item>

          <el-form-item label="事件发生地址" prop="address">
            <el-input type="textarea" :rows="2" v-model="formData1.address" style="width:2rem"></el-input>
          </el-form-item>

          <el-form-item label="事件详细描述" prop="desc">
            <el-input type="textarea" :rows="2" v-model="formData1.desc" style="width:2rem"></el-input>
          </el-form-item>
          <el-form-item label="事件标签" prop="eventTag">
            <el-select filterable v-model="formData1.eventTag" placeholder="请选择事件标签" clearable>
              <el-option v-for="item in eventTagList" :value="item.tagId" :label="item.tagName" :key="item.tagId" />
            </el-select>
          </el-form-item>
          <el-form-item label="主题名称" prop="topicName">
            <el-select filterable v-model="formData1.topicName" placeholder="请选择主题名称" clearable>
              <el-option
                v-for="item in topicNameList"
                :value="item.topicName"
                :label="item.topicName"
                :key="item.topicName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件类型" prop="eventType">
            <el-select filterable v-model="formData1.eventType" placeholder="请选择事件类型" clearable>
              <el-option
                v-for="item in eventTypeList"
                :value="item.typeName"
                :label="item.typeName"
                :key="item.typeName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件级别" prop="level">
            <el-select filterable v-model="formData1.level" placeholder="请选择事件级别" clearable>
              <el-option
                v-for="item in levelList"
                :value="item.levelName"
                :label="item.levelName"
                :key="item.levelName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件破坏程度" prop="damageDegree">
            <el-input clearable v-model="formData1.damageDegree"></el-input>
          </el-form-item>
          <el-form-item label="事件上报渠道" prop="channel">
            <el-input v-model="formData1.channel" :readonly="true" style="width:2rem"></el-input>
          </el-form-item>
          <el-form-item label="上报人姓名" prop="reportPerson">
            <el-input clearable v-model="formData1.reportPerson"></el-input>
          </el-form-item>
          <el-form-item label="上报人电话" prop="reportPhone">
            <el-input clearable v-model="formData1.reportPhone"></el-input>
          </el-form-item>
          <el-form-item label="事件上报来源" prop="reportSource">
            <el-select filterable v-model="formData1.reportSource" placeholder="请选择事件上报来源" clearable>
              <el-option
                v-for="item in reportSourceList"
                :value="item.sourceId"
                :label="item.sourceName"
                :key="item.sourceId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="信息来源" prop="infoSource">
            <el-select filterable v-model="formData1.infoSource" placeholder="请选择信息来源" clearable>
              <el-option
                v-for="item in infoSourceList"
                :value="item.sourceId"
                :label="item.sourceName"
                :key="item.sourceId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件发生时间" prop="happenTime">
            <el-date-picker
              v-model="formData1.happenTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="事件上报时间" prop="syncTime">
            <el-date-picker
              v-model="formData1.syncTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="处置截止时间" prop="deadlineTime">
            <el-date-picker
              v-model="formData1.deadlineTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
        </el-form>
        <!-- 附件 -->
        <div class="filesPhone">
          <span>附件信息</span>
          <AttachmentUpload @success="siteImg" :title="title" />
        </div>
        <el-form
          :inline="true"
          ref="formData3"
          :rules="rules3"
          class="storage-form"
          :model="formData3"
          label-width="1.3rem"
          style="margin-top: 0.2rem"
        >
          <el-form-item label="是否已处置">
            <el-switch v-model="formData3.delivery" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
          </el-form-item>
        </el-form>
      </el-collapse-item>
      <el-collapse-item title="● 事件处置过程信息" name="2" v-if="formData3.delivery">
        <el-form
          :inline="true"
          ref="formData2"
          :rules="rules2"
          class="storage-form"
          :model="formData2"
          label-width="1.3rem"
          style="margin-top: 0.2rem"
          :disabled="title === '详情'"
        >
          <el-form-item label="处置开始时间" prop="dealStartTime">
            <el-date-picker
              v-model="formData2.dealStartTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="处置截止时间" prop="dealEndTime">
            <el-date-picker
              v-model="formData2.dealEndTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="处置人姓名" prop="dealPersonName">
            <el-input clearable v-model="formData2.dealPersonName"></el-input>
          </el-form-item>
          <el-form-item label="处置人电话" prop="dealPersonPhone">
            <el-input clearable v-model="formData2.dealPersonPhone"></el-input>
          </el-form-item>
          <el-form-item label="处置反馈" prop="dealInfo">
            <el-input type="textarea" :rows="2" v-model="formData2.dealInfo" style="width:2rem"></el-input>
          </el-form-item>
        </el-form>
        <!-- 附件 -->
        <div class="filesPhone">
          <span>附件信息</span>
          <AttachmentUpload @success="siteImg2" :title="title" />
        </div>
      </el-collapse-item>
    </el-collapse>

    <div slot="footer" class="footer">
      <!-- <el-button @click="handleClose()">取 消</el-button> -->
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </div>
  </div>
</template>

<script>
import AttachmentUpload from '../../components/attachmentUpload.vue'
import axios from '@/utils/request'
import moment from 'moment'
import '../../plugins/vant.js'
import 'vant/lib/index.css'
export default {
  name: 'qrCode',
  components: { AttachmentUpload },
  data() {
    return {
      active: ['1', '2'],
      showPicker: false,
      dialogVisible: false,
      tableData: [],
      title: '创建事件',
      formData1: {
        gridId: [], // 所属社区
        taskId: '', // 事件编号
        recordId: '', // 案卷编号
        eventName: '', // 事件名称
        address: '', // 事件位置描述
        happenTime: '', // 事件发生时间
        damageDegree: '', // 事件破坏程度
        topicName: '', // 主题名称
        desc: '', // 事件详细描述
        eventState: '待派遣', // 事件状态
        overdueState: '', // 超期状态
        eventTag: '', // 事件标签
        eventType: '', // 事件类型
        level: '一般', // 事件级别
        reportSource: '0', // 事件上报来源
        channel: 'APP填报', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: moment().format('yyyy-MM-DD HH:mm:ss'), // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '', // 上报人电话
        infoSource: '2', // 信息来源
        files: []
      },
      rules1: {
        eventName: [{ required: true, message: '请输入事件名称', trigger: 'blur' }],
        desc: [{ required: true, message: '请输入事件详情', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址详情', trigger: 'blur' }],
        happenTime: [{ required: true, message: '请选择事件发生时间', trigger: 'blur' }]
      },
      formData2: {
        dealStartTime: '', // 处置开始时间
        dealEndTime: '', // 处置截止时间
        dealPersonName: sessionStorage.getItem('user') || '', // 处置人姓名
        dealPersonPhone: sessionStorage.getItem('phone') || '', // 处置人电话
        dealInfo: '已按要求处置完毕', // 处置反馈
        files: []
      },
      rules2: {
        dealStartTime: [{ required: true, message: '请选择处置开始时间', trigger: 'blur' }],
        dealEndTime: [{ required: true, message: '请选择处置截止时间', trigger: 'blur' }],
        dealInfo: [{ required: true, message: '请输入处置反馈', trigger: 'blur' }]
      },
      formData3: { delivery: '' },
      rules3: {},
      eventTagList: [], // 事件标签列表
      levelList: [
        { levelName: '紧急', levelId: '1' },
        { levelName: '一般', levelId: '2' }
      ],
      reportSourceList: [], // 上报来源
      infoSourceList: [], // 事件信息来源
      // gridNameList: [], // 属所组织
      // dealDeptProps: {
      //   value: 'id',
      //   label: 'name',
      //   children: 'children',
      //   checkStrictly: true,
      //   expandTrigger: 'click',
      //   emitPath: true
      // },
      topicNameList: [],
      eventTypeList: [],
      mapVisible: true
    }
  },
  computed: {

  },
  watch: {

  },
  mounted() {
    this.getEventTagList()
    this.getReportSourceList()
    this.getInfoSourceList()
    // this.getGridNameList()
    this.getTopicNameList()
    this.getEventTypeList()
    this.$root.eventBus.$on('clickMap', (e) => {
      // this.mapVisible = true
      this.formData1.gpsx = e[0]
      this.formData1.gpsy = e[1]
    })
  },
  methods: {
    getGps() {
      this.mapVisible = false
      if (this.formData1.gpsx && this.formData1.gpsy) {
        this.$root.eventBus.$emit('clickMap', [this.formData1.gpsx, this.formData1.gpsy])
      } else {
        this.$root.eventBus.$emit('clickMap', [])
      }
    },
    // 获取事件类型
    getEventTypeList() {
      axios.get('/binhuapis/event/queryDictEventType').then((res) => {
        if (res.data.code === 200) {
          this.eventTypeList = res.data.data
        }
      })
    },
    // 获取主题名称
    getTopicNameList() {
      axios.get('/binhuapis/event/queryDictEventTopic').then((res) => {
        if (res.data.code === 200) {
          this.topicNameList = res.data.data
        }
      })
    },
    // 获取网格列表
    // getGridNameList() {
    //   axios
    //     .get(
    //       '/auth/v2/device/queryPrecinctTree?precinctId=' + '3702030250000000000' + '&userId=' + '57'

    //     )
    //     .then((res) => {
    //       if (res.data.code === 200) {
    //         this.gridNameList = this.getTreeData(res.data.data[0].children)
    //         // this.dealDeptList = res.data.data[0].children
    //       }
    //     })
    //   // axios.get('/auth/v2/precinct/queryPrecinctByKind?precinctKind=' + '120').then((res) => {
    //   //   if (res.data.code === 200) {
    //   //     this.gridNameList = res.data.data
    //   //   }
    //   // })
    // },
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined
        }
      }
      return data
    },
    // 获取事件信息来源
    getInfoSourceList() {
      axios.get('/binhuapis/event/queryDictEventInfoSource').then((res) => {
        if (res.data.code === 200) {
          this.infoSourceList = res.data.data
        }
      })
    },
    // 获取上报来源
    getReportSourceList() {
      axios.get('/binhuapis/event/queryDictEventReportSource').then((res) => {
        if (res.data.code === 200) {
          this.reportSourceList = res.data.data
        }
      })
    },
    // 获取事件标签
    getEventTagList() {
      axios.get('/binhuapis/event/queryDictEventTag').then((res) => {
        if (res.data.code === 200) {
          this.eventTagList = res.data.data
        }
      })
    },
    // 确定按钮
    handleOk() {
      this.$refs['formData1'].validate((valid) => {
        if (valid) {
          // let dealDeptId = ''
          // if (this.formData1.gridId.length === 1) {
          //   dealDeptId = this.formData1.gridId[0]
          // } else {
          //   dealDeptId = this.formData1.gridId[1]
          // }
          let params = {
            taskId: this.formData1.taskId,
            recordId: this.formData1.recordId,
            eventName: this.formData1.eventName,
            eventState: '30',
            address: this.formData1.address,
            gpsx: this.formData1.gpsx,
            gpsy: this.formData1.gpsy,
            // gridId: dealDeptId,
            desc: this.formData1.desc,
            eventTag: this.formData1.eventTag,
            topicName: this.formData1.topicName,
            eventType: this.formData1.eventType,
            level: this.formData1.level,
            damageDegree: this.formData1.damageDegree,
            channel: this.formData1.channel,
            reportPerson: this.formData1.reportPerson,
            reportPhone: this.formData1.reportPhone,
            reportSource: this.formData1.reportSource,
            infoSource: this.formData1.infoSource,
            happenTime: this.formData1.happenTime,
            syncTime: this.formData1.syncTime,
            deadlineTime: this.formData1.deadlineTime,
            endTime: this.formData1.endTime
          }
          let formData = new FormData()
          formData.append('eventInfo', JSON.stringify(params))
          // let fileList = []
          for (var i = 0; i < this.formData1.files.length; i++) {
            // fileList.push()
            formData.append('files', this.formData1.files[i].raw)
          }
          let url = '/binhuapis/event/saveEventInfo'
          axios.post(url, formData).then((r) => {
            if (r.data.code === 200) {
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 1000
              })
              this.$emit('success')
              this.handleClose()
            } else {
              this.$notify({
                title: '警告',
                message: '保存失败，' + r.data.message,
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
          return false
        }
      })
    },
    handleClose() {
      this.formData1 = {
        gridId: [], // 所属社区
        taskId: '', // 事件编号
        recordId: '', // 案卷编号
        eventName: '', // 事件名称
        address: '', // 事件位置描述
        happenTime: '', // 事件发生时间
        damageDegree: '', // 事件破坏程度
        topicName: '', // 主题名称
        desc: '', // 事件详细描述
        eventState: '待派遣', // 事件状态
        overdueState: '', // 超期状态
        eventTag: '', // 事件标签
        eventType: '', // 事件类型
        level: '一般', // 事件级别
        reportSource: '0', // 事件上报来源
        channel: '', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: moment().format('yyyy-MM-DD HH:mm:ss'), // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '', // 上报人电话
        infoSource: '2', // 信息来源
        files: []
      }
    },
    siteImg(r) {
      this.formData1.files = r
    }
  }
}
</script>
<style lang="scss">
.qrCode {
  width: 100%;
  height: 100%;
  overflow: auto;
  overflow-x: hidden;
  .filesPhone {
    margin-left: 0.5rem;
  }
  .footer {
    display: flex;
    justify-content: flex-end;
    // margin-right: 0.1rem;
    padding: 0.2rem 0.2rem 0.3rem 0;
    background: #ffffff;
  }
  .el-input__inner {
    width: 2rem;
  }
}
</style>
