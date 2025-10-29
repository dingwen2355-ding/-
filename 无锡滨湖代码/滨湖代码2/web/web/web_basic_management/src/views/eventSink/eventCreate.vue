<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="eventCreate"
      :title="title"
      :visible.sync="dialogVisible"
      width="1550px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-form
          :inline="true"
          ref="formData2"
          :rules="rules2"
          class="storage-form"
          :model="formData2"
          label-width="120px"
          style="margin-top: 10px"
          :disabled="title === '派遣'"
        >
          <el-form-item label="事件名称" prop="eventName">
            <el-input clearable v-model="formData2.eventName"></el-input>
          </el-form-item>
          <el-form-item label="事件编号" prop="taskId">
            <el-input clearable v-model="formData2.taskId"></el-input>
          </el-form-item>
          <el-form-item label="案卷编号" prop="recordId">
            <el-input clearable v-model="formData2.recordId"></el-input>
          </el-form-item>
          <el-form-item label="事件状态" prop="eventState">
            <el-input clearable v-model="formData2.eventState" :disabled="title === '创建事件'"></el-input>
          </el-form-item>
          <el-form-item label="事件位置描述" prop="address">
            <el-input type="textarea" :rows="2" v-model="formData2.address" style="width: 220px"></el-input>
          </el-form-item>
          <el-form-item label="经纬度坐标" prop="gps">
            <el-input clearable v-model="formData2.gps" style="width: 562px" :readonly="true">
              <el-button slot="append" icon="el-icon-location-outline" @click="getGps"></el-button>
            </el-input>
          </el-form-item>
          <!-- <el-form-item label="纬度坐标" prop="gpsy">
            <el-input clearable v-model="formData2.gpsy" style="width: 212px">
              <el-button slot="append" icon="el-icon-location-outline" @click="getGps"></el-button>
            </el-input>
          </el-form-item>-->
          <el-form-item label="所属社区" prop="gridId">
            <el-select filterable v-model="formData2.gridId" placeholder="请选择所属社区" clearable>
              <el-option
                v-for="item in gridNameList"
                :value="item.precinctId"
                :label="item.precinctName"
                :key="item.precinctId"
              />
            </el-select>
            <!-- <el-cascader
              :options="gridNameList"
              :props="dealDeptProps"
              v-model="formData2.gridId"
              placeholder="请选择所属社区"
            ></el-cascader>-->
            <!-- <el-select filterable v-model="formData2.gridId " placeholder="请选择所属社区" clearable>
              <el-option
                v-for="item in gridNameList"
                :value="item.precinctId"
                :label="item.precinctName"
                :key="item.precinctId"
              />
            </el-select>-->
          </el-form-item>
          <el-form-item label="事件详细描述" prop="desc">
            <el-input type="textarea" :rows="2" v-model="formData2.desc" style="width: 1260px"></el-input>
            <!-- <el-input clearable v-model="formData2.desc" style="width:300px"></el-input> -->
          </el-form-item>
          <el-form-item label="事件标签" prop="eventTag">
            <!-- <el-select filterable v-model="formData2.eventTag" placeholder="请选择事件标签" clearable>
              <el-option v-for="item in eventTagList" :value="item.tagId" :label="item.tagName" :key="item.tagId" />
            </el-select>-->
            <el-cascader
              :options="eventTagList"
              :props="optionProps"
              v-model="formData2.eventTag"
              clearable
              :filterable="true"
              placeholder="请选择事件标签"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="主题名称" prop="topicName">
            <el-select filterable v-model="formData2.topicName" placeholder="请选择主题名称" clearable>
              <el-option
                v-for="item in topicNameList"
                :value="item.topicName"
                :label="item.topicName"
                :key="item.topicName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件类型" prop="eventType" style="margin-right: 200px">
            <!--eventTypeList  -->
            <el-select
              filterable
              v-model="formData2.eventType"
              placeholder="请选择事件类型"
              clearable
              allow-create
              default-first-option
            >
              <el-option
                v-for="item in eventTypeList"
                :value="item.typeName"
                :label="item.typeName"
                :key="item.typeName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件级别" prop="level">
            <el-select filterable v-model="formData2.level" placeholder="请选择事件级别" clearable>
              <el-option
                v-for="item in levelList"
                :value="item.levelName"
                :label="item.levelName"
                :key="item.levelName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="事件破坏程度" prop="damageDegree">
            <el-input clearable v-model="formData2.damageDegree"></el-input>
          </el-form-item>
          <el-form-item label="事件上报渠道" prop="channel" style="margin-right: 200px">
            <el-input clearable v-model="formData2.channel"></el-input>
          </el-form-item>
          <el-form-item label="上报人姓名" prop="reportPerson">
            <el-input clearable v-model="formData2.reportPerson"></el-input>
          </el-form-item>
          <el-form-item label="上报人电话" prop="reportPhone">
            <el-input clearable v-model="formData2.reportPhone"></el-input>
          </el-form-item>
          <el-form-item label="事件上报来源" prop="reportSource">
            <el-select filterable v-model="formData2.reportSource" placeholder="请选择事件上报来源" clearable>
              <el-option
                v-for="item in reportSourceList"
                :value="item.sourceId"
                :label="item.sourceName"
                :key="item.sourceId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="信息来源" prop="infoSource">
            <el-select filterable v-model="formData2.infoSource" placeholder="请选择信息来源" clearable>
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
              v-model="formData2.happenTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="事件上报时间" prop="syncTime">
            <el-date-picker
              v-model="formData2.syncTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="处置截止时间" prop="deadlineTime">
            <el-date-picker
              v-model="formData2.deadlineTime"
              type="datetime"
              placeholder="选择时间"
              :picker-options="pickerOptions"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="事件结案时间" prop="endTime" v-if="title === '事件详情'">
            <el-date-picker
              v-model="formData2.endTime"
              type="datetime"
              placeholder="选择时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            ></el-date-picker>
          </el-form-item>
        </el-form>
        <!-- 附件 -->
        <div class="files">
          <span>事件附件</span>
          <AttachmentUpload @success="siteImg" :title="title" />
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose()">取 消</el-button>
        <el-button type="primary" @click="handleOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import AttachmentUpload from '../../components/attachmentUpload.vue'
import axios from '@/utils/request'
import moment from 'moment'
import { HOLIDAY } from '@/utils/time'
export default {
  name: 'eventCreat',
  props: ['dialog', 'dialogData'],
  components: { AttachmentUpload },
  data() {
    return {
      dialogVisible: false,
      tableData: [],
      title: '创建事件',
      formData2: {
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
        reportSource: '', // 事件上报来源
        channel: '', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        gps: '',
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: moment().format('yyyy-MM-DD HH:mm:ss'), // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '', // 上报人电话
        infoSource: '2', // 信息来源
        files: []
      },
      rules2: {
        eventName: [{ required: true, message: '请输入事件名称', trigger: 'blur' }],
        desc: [{ required: true, message: '请输入事件详情', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址详情', trigger: 'blur' }],
        happenTime: [{ required: true, message: '请选择事件发生时间', trigger: 'blur' }]
      },
      eventTagList: [], // 事件标签列表
      optionProps: {
        value: 'nodeId',
        label: 'nodeName',
        children: 'childrenList',
        expandTrigger: 'hover',
        checkStrictly: true
      },
      levelList: [
        { levelName: '紧急', levelId: '1' },
        { levelName: '一般', levelId: '2' }
      ],
      reportSourceList: [], // 上报来源
      infoSourceList: [], // 事件信息来源
      gridNameList: [],
      dealDeptProps: {
        value: 'id',
        label: 'name',
        children: 'children',
        checkStrictly: true,
        expandTrigger: 'click',
        emitPath: true
      },
      topicNameList: [],
      eventTypeList: []
    }
  },
  watch: {
    dialog(v) {
      this.dialogVisible = v
      if (v) {
        // this.formData2 = JSON.parse(JSON.stringify(this.detailsDialogData))
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
      }
    }
  },
  mounted() {
    this.getEventTagList()
    this.getReportSourceList()
    this.getInfoSourceList()
    this.getGridNameList()
    this.getTopicNameList()
    this.getEventTypeList()
    this.$root.eventBus.$on('clickMap', e => {
      if (e[0] === undefined) {
        this.$notify({
          title: '警告',
          message: '请选取坐标',
          type: 'warning',
          duration: 1000
        })
        this.formData2.gpsx = ''
        this.formData2.gpsy = ''
        this.formData2.gps = ''
      } else {
        this.formData2.gpsx = e[0]
        this.formData2.gpsy = e[1]
        this.formData2.gps = e[0] + ',' + e[1]
      }
    })
  },
  computed: {
    pickerOptions() {
      console.log(HOLIDAY, '++++++++++')
      return {
        // 法定节假日日期不可选
        disabledDate: time => {
          for (var i = 0; i < HOLIDAY.length; i++) {
            if (time.getTime() === new Date(HOLIDAY[i]).getTime()) {
              return true
            }
          }
          if (time.getDay() === 0 || time.getDay() === 6) {
            // 返回日期中表示周几的数值（0 表示周日，6 表示周六）
            return '#'
          }
        },
        // 修改法定节假日期的样式
        cellClassName: time => {
          for (var i = 0; i < HOLIDAY.length; i++) {
            if (time.getTime() === new Date(HOLIDAY[i]).getTime()) {
              return 'x-hd'
            }
          }
        }
      }
    },
    openDialogVisible: {
      get() {
        return this.DialogVisible
      },
      set(val) {
        console.log(this.formAdd, 'set')
        this.$emit('update:DialogVisible', val) // openDialogVisible改变的时候通知父组件
      }
    }
  },
  methods: {
    getGps() {
      if (this.formData2.gpsx && this.formData2.gpsy) {
        this.$root.eventBus.$emit('clickMap', [this.formData2.gpsx, this.formData2.gpsy])
      } else {
        this.$root.eventBus.$emit('clickMap', [])
      }
    },
    // 获取事件类型
    getEventTypeList() {
      axios.get('/binhuapis/event/queryDictEventType').then((res) => {
        if (res.data.code === 200) {
          this.eventTypeList = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取事件类型失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 获取主题名称
    getTopicNameList() {
      axios.get('/binhuapis/event/queryDictEventTopic').then((res) => {
        if (res.data.code === 200) {
          this.topicNameList = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取主题名称失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 获取网格列表
    getGridNameList() {
      axios
        .get(
          '/auth/v2/precinct/queryPrecinctByKind?precinctKind=' + '110'
          // sessionStorage.getItem('streetId') +
          // '&userId=' +
          // sessionStorage.getItem('userId')
        )
        .then((res) => {
          if (res.data.code === 200) {
            this.gridNameList = res.data.data
            // this.dealDeptList = res.data.data[0].children
          } else {
            this.$notify({
              title: '警告',
              message: '获取网格列表失败，' + res.data.message,
              type: 'warning',
              duration: 1000
            })
          }
        })
      // axios.get('/auth/v2/precinct/queryPrecinctByKind?precinctKind=' + '120').then((res) => {
      //   if (res.data.code === 200) {
      //     this.gridNameList = res.data.data
      //   }
      // })
    },
    // getTreeData(data) {
    //   // 循环遍历json数据
    //   for (var i = 0; i < data.length; i++) {
    //     if (data[i].children.length < 1) {
    //       // children若为空数组，则将children设为undefined
    //       data[i].children = undefined
    //     }
    //   }
    //   return data
    // },
    // 获取事件信息来源
    getInfoSourceList() {
      axios.get('/binhuapis/event/queryDictEventInfoSource').then((res) => {
        if (res.data.code === 200) {
          this.infoSourceList = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取事件信息来源失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 获取上报来源
    getReportSourceList() {
      axios.get('/binhuapis/event/queryDictEventReportSource').then((res) => {
        if (res.data.code === 200) {
          this.reportSourceList = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取上报来源失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 获取事件标签
    getEventTagList() {
      axios.get('/binhuapis/event/getEventTagTree').then((res) => {
        if (res.data.code === 200) {
          this.eventTagList = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取事件标签失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    // 确定按钮
    handleOk() {
      this.$refs['formData2'].validate((valid) => {
        if (valid) {
          let eventTag = ''
          if (this.formData2.eventTag) {
            if (this.formData2.eventTag.length === 1) {
              eventTag = this.formData2.eventTag[0]
            } else if (this.formData2.eventTag.length === 2) {
              eventTag = this.formData2.eventTag[1]
            } else {
              eventTag = this.formData2.eventTag[2]
            }
          }

          let params = {
            taskId: this.formData2.taskId,
            recordId: this.formData2.recordId,
            eventName: this.formData2.eventName,
            eventState: '30',
            address: this.formData2.address,
            gpsx: this.formData2.gpsx,
            gpsy: this.formData2.gpsy,
            gridId: this.formData2.gridId,
            desc: this.formData2.desc,
            eventTag: eventTag,
            topicName: this.formData2.topicName,
            eventType: this.formData2.eventType,
            level: this.formData2.level,
            damageDegree: this.formData2.damageDegree,
            channel: this.formData2.channel,
            reportPerson: this.formData2.reportPerson,
            reportPhone: this.formData2.reportPhone,
            reportSource: this.formData2.reportSource,
            infoSource: this.formData2.infoSource,
            happenTime: this.formData2.happenTime,
            syncTime: this.formData2.syncTime,
            deadlineTime: this.formData2.deadlineTime
          }
          let formData = new FormData()
          formData.append('eventInfo', JSON.stringify(params))
          // let fileList = []
          for (var i = 0; i < this.formData2.files.length; i++) {
            // fileList.push()
            formData.append('files', this.formData2.files[i].raw)
          }
          // console.log(formData.eventInfo)
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
              this.$emit('close')
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
      this.formData2 = {
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
        reportSource: '', // 事件上报来源
        channel: '', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        gps: '',
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: moment().format('yyyy-MM-DD HH:mm:ss'), // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '', // 上报人电话
        infoSource: '2', // 信息来源
        files: []
      }
      this.$emit('update:dialog', false)
      this.$emit('close')
    },
    siteImg(r) {
      this.formData2.files = r
    }
  }
}
</script>
<style lang="scss" scoped>
.eventCreate {
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 620px;

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
  .files {
    margin-left: 40px;
  }
}
</style>
