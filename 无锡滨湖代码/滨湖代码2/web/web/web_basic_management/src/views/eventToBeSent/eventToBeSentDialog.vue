<template>
  <div v-if="dialogVisible">
    <el-dialog
      class="eventToBeSentDialog"
      :title="title"
      :visible.sync="dialogVisible"
      width="1550px"
      :modal-append-to-body="false"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <div class="info_body">
        <el-collapse v-model="active">
          <el-collapse-item title="● 派发信息" name="1">
            <el-form
              :inline="true"
              ref="formData1"
              :rules="rules1"
              class="storage-form"
              :model="formData1"
              label-width="125px"
              style="margin-top: 10px"
            >
              <el-form-item label="处置部门" prop="dealDeptId">
                <el-cascader
                  clearable
                  filterable
                  ref="cascader"
                  @change="dealDeptIdChange"
                  :options="dealDeptList"
                  :props="dealDeptProps"
                  :show-all-levels="false"
                  v-model="formData1.dealDeptId"
                  placeholder="请选择处置部门"
                ></el-cascader>
              </el-form-item>
              <el-form-item label="处置人" prop="dealPersonId">
                <el-select
                  @change="dealPersonIdChange"
                  filterable
                  v-model="formData1.dealPersonId"
                  placeholder="请选择处置人"
                  clearable
                >
                  <el-option
                    v-for="item in dealPersonList"
                    :value="item.userId"
                    :label="item.trueName"
                    :key="item.userId"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="处置人电话" prop="phone">
                <el-input clearable v-model="formData1.phone"></el-input>
              </el-form-item>
              <el-form-item label="事件标签" prop="eventTag" v-if="reportSource!=='政务热线'">
                <!-- <el-select filterable v-model="formData1.eventTag" placeholder="请选择事件标签" clearable>
                  <el-option v-for="item in eventTagList" :value="item.tagId" :label="item.tagName" :key="item.tagId" />
                </el-select>-->
                <el-cascader
                  :options="eventTagList"
                  :props="optionProps"
                  v-model="formData1.eventTag"
                  clearable
                  :filterable="true"
                  placeholder="请选择事件标签"
                ></el-cascader>
              </el-form-item>
              <el-form-item label="处置开始时间" prop="dealStartTime">
                <el-date-picker
                  v-model="formData1.dealStartTime"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="处置截止时间" prop="dealEndTime" v-if="reportSource!=='政务热线'">
                <el-date-picker
                  v-model="formData1.dealEndTime"
                  type="datetime"
                  placeholder="选择时间"
                  :picker-options="pickerOptions"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="下派处置意见" prop="distributeInfo">
                <el-input type="textarea" :rows="2" v-model="formData1.distributeInfo" style="width: 640px"></el-input>
              </el-form-item>
            </el-form>
          </el-collapse-item>

          <el-collapse-item title="● 事件基础信息" name="2" v-if="title==='待派遣'">
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
                <el-input clearable v-model="formData2.taskId" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="案卷编号" prop="recordId">
                <el-input clearable v-model="formData2.recordId" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件名称" prop="eventName">
                <el-input clearable v-model="formData2.eventName" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件状态" prop="eventState">
                <span class="stateBorder">
                  <span :style="cerrorColor(formData2.eventStateId)">{{ formData2.eventState }}</span>
                  <span
                    :class="[
                 formData2.overdueStateId === '30' ? 'overdueStatRed' : '',
                  formData2.overdueStateId === '20' ? 'overdueStatyellow' : ''
                ]"
                    v-if="formData2.overdueStateId !== '10' && formData2.overdueStateId !== null&& formData2.overdueStateId !== ''"
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
              <el-form-item label="所属社区" prop="gridName">
                <el-input clearable v-model="formData2.gridName" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件详细描述" prop="desc">
                <el-input type="textarea" :rows="2" v-model="formData2.desc" style="width: 1260px" :readonly="true"></el-input>
              </el-form-item>
              <!-- <el-form-item label="事件标签" prop="eventTag">
                <el-input clearable v-model="formData2.eventTag" :readonly="true"></el-input>
              </el-form-item>-->
              <el-form-item label="主题名称" prop="topicName">
                <el-input clearable v-model="formData2.topicName" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件类型" prop="eventType">
                <el-input clearable v-model="formData2.eventType" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件级别" prop="level" style="margin-right: 200px">
                <el-input clearable v-model="formData2.level" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件破坏程度" prop="damageDegree">
                <el-input clearable v-model="formData2.damageDegree" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件上报渠道" prop="channel">
                <el-input clearable v-model="formData2.channel" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="信息来源" prop="infoSource" style="margin-right: 200px">
                <el-input clearable v-model="formData2.infoSource" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="上报人姓名" prop="reportPerson">
                <el-input clearable v-model="formData2.reportPerson" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="上报人电话" prop="reportPhone">
                <el-input clearable v-model="formData2.reportPhone" :readonly="true"></el-input>
              </el-form-item>
              <el-form-item label="事件上报来源" prop="reportSource" style="margin-right: 200px">
                <el-input clearable v-model="formData2.reportSource" :readonly="true"></el-input>
              </el-form-item>

              <el-form-item label="事件发生时间" prop="happenTime">
                <el-date-picker
                  v-model="formData2.happenTime"
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
              <el-form-item label="处理截止时间" prop="deadlineTime">
                <el-date-picker
                  v-model="formData2.deadlineTime"
                  type="datetime"
                  placeholder="选择时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :readonly="!deadlineTimeShow"
                ></el-date-picker>
                <span v-if="roleId==='1' ||roleId==='10'">
                  <el-button
                    type="primary"
                    size="mini"
                    @click="deadlineTimeShow=!deadlineTimeShow"
                    style="margin-left:5px"
                    v-if="!deadlineTimeShow"
                  >修改</el-button>
                  <el-button type="primary" @click="deadlineTimeChange" size="mini" style="margin-left:5px" v-else>确定</el-button>
                </span>
              </el-form-item>
              <el-form-item label="事件结案时间" prop="endTime" v-if="title === '事件详情'">
                <el-date-picker
                  v-model="formData2.endTime"
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
              <span v-if="formData2.files !== null">事件附件</span>
              <AttachmentUpload :imgUrl="formData2.files" :title="'事件详情'" />
            </div>
          </el-collapse-item>
        </el-collapse>
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
import qs from 'qs'
import moment from 'moment'
import { HOLIDAY } from '@/utils/time'
export default {
  name: 'eventToBeSentDialog',
  props: ['dialog', 'dialogData', 'dialogTitle'],
  components: { AttachmentUpload },
  data() {
    var validDealDeptId = (rule, value, callback) => {
      if (this.formData1.dealDeptId.length === 0) {
        callback(new Error('请选择处置部门'))
      } else {
        callback()
      }
    }

    return {
      dialogVisible: false,
      tableData: [],
      title: '待派遣',
      active: ['1', '2'],
      formData1: {
        dealDeptId: [],
        dealPersonId: '',
        checkerName: '',
        dealStartTime: moment().format('yyyy-MM-DD HH:mm:ss'),
        dealEndTime: '',
        distributeInfo: '',
        eventTag: ''
      },
      rules1: {
        dealDeptId: [{ required: true, validator: validDealDeptId, trigger: 'chamge' }],
        dealStartTime: [{ required: true, message: '请选择处置开始时间', trigger: 'blur' }],
        eventTag: [{ required: true, message: '请选择事件标签', trigger: 'blur' }],
        dealEndTime: [{ required: true, message: '请选择处置截止时间', trigger: 'blur' }]
      },
      formData2: {},
      rules2: {},
      dealDeptList: [],
      eventTagList: [], // 事件标签列表
      optionProps: {
        value: 'nodeId',
        label: 'nodeName',
        children: 'childrenList',
        expandTrigger: 'hover',
        checkStrictly: true
      },
      dealDeptProps: {
        value: 'id',
        label: 'name',
        children: 'children',
        checkStrictly: true,
        expandTrigger: 'click'
      },
      dealPersonList: [],
      disabledTooltip: false,
      disable: true,
      deadlineTimeShow: false, // 处置截止时间修改
      roleId: sessionStorage.getItem('roleId'),
      isChange: false,
      reportSource: ''
    }
  },
  computed: {
    pickerOptions() {
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
    },
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
    dialog(v) {
      this.dialogVisible = v
      if (v) {
        // this.formData2 = JSON.parse(JSON.stringify(this.detailsDialogData))
        // this.getDetail() // 获取详情
        // this.getDelateData() // 获取延期信息和整改复查信息
        this.getDealPersonId()
      }
    },
    dialogTitle(v) {
      if (v) {
        this.title = v
      }
    },
    dialogData(v) {
      this.formData2 = JSON.parse(JSON.stringify(v))
      if (this.formData2.length > 1) {
        this.reportSource = this.formData2[0].reportSource
      } else {
        this.reportSource = this.formData2.reportSource
      }
      console.log(this.formData2)
      if (this.title === '待派遣') {
        console.log(this.eventTagList, 'this.eventTagList')
        this.formData1.eventTag = this.formData2.eventTag === null || this.formData2.eventTag === undefined ? '' : JSON.parse(JSON.stringify(this.formData2.eventTagId))
        if (this.formData2.deadlineTime === null || this.formData2.deadlineTime === undefined || this.formData2.deadlineTime === '') {
          this.getDealEndTime()
        } else {
          this.formData1.dealEndTime = JSON.parse(JSON.stringify(this.formData2.deadlineTime))
        }
      }
    }
  },
  mounted() {
    this.getDealDeptList()
    this.getEventTagList()
  },
  methods: {
    // 修改处置截止时间
    deadlineTimeChange() {
      let param = {
        deadlineTime: this.formData2.deadlineTime,
        eventId: this.formData2.eventId
      }
      axios.post('/binhuapis/event/updateDeadlineTime', qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          this.deadlineTimeShow = false
          this.$notify({
            title: '成功',
            message: '修改处置截止时间成功',
            type: 'success',
            duration: 1000
          })
          this.isChange = true
        } else {
          this.$notify({
            title: '警告',
            message: '获取截止时间失败，' + res.data.message,
            type: 'warning',
            duration: 1000
          })
        }
      })
    },
    getDealEndTime() {
      let param = {
        time: this.formData1.dealStartTime
      }
      if (this.formData2.eventType) {
        if (this.formData2.eventType === '建筑垃圾') {
          param.days = '4'
        } else {
          param.days = '2'
        }
      } else {
        param.days = '2'
      }
      axios.get('/binhuapis/event/getDefaultEventDeadlineTime?' + qs.stringify(param)).then((res) => {
        if (res.data.code === 200) {
          this.formData1.dealEndTime = res.data.data
        } else {
          this.$notify({
            title: '警告',
            message: '获取截止时间失败，' + res.data.message,
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
    dealPersonIdChange(v) {
      console.log(this.dealPersonList)
      let data = this.dealPersonList.filter((e) => {
        if (e.userId === v) return e
      })
      if (data.length !== 0) {
        this.formData1.phone = data[0].phone
        // this.formData1.dealDeptId[0] = '3702030250001100000'
        // this.formData1.dealDeptId[1] = '3702030250001100101'
        this.formData1.dealDeptId = data[0].departmentId
      } else {
        this.formData1.phone = ''
      }
      this.$forceUpdate()
      console.log(this.formData1)
    },
    dealDeptIdChange() {
      this.$refs.cascader.toggleDropDownVisible(false)
      console.log(this.formData1)
      if (this.formData1.dealDeptId === null || this.formData1.dealDeptId === '') {
        this.formData1.phone = ''
        this.formData1.dealPersonId = ''
      }
      this.getDealPersonId()
    },
    // 获取处置人信息
    getDealPersonId() {
      let dealDeptId = this.formData1.dealDeptId || ''
      if (this.formData1.dealDeptId.length === 0) {
        axios.get('/auth/user?precinctId=' + dealDeptId + '&roleId=' + '40,50,60').then((res) => {
          this.dealPersonList = res.data.data
        })
      } else {
        let dealDeptId1 = ''
        if (dealDeptId.length === 1) {
          dealDeptId1 = dealDeptId[0]
        } else {
          dealDeptId1 = dealDeptId[1]
        }
        axios.get('/auth/user?precinctId=' + dealDeptId1).then((res) => {
          this.dealPersonList = res.data.data
        })
      }
    },
    //  获取处置部门
    getDealDeptList() {
      axios
        .get(
          '/auth/v2/device/queryPrecinctTree?precinctId=' +
          sessionStorage.getItem('streetId') +
          '&userId=' +
          sessionStorage.getItem('userId')
        )
        .then((res) => {
          if (res.data.code === 200) {
            this.dealDeptList = this.getTreeData(res.data.data[0].children)
            // this.dealDeptList = res.data.data[0].children
          } else {
            this.$notify({
              title: '警告',
              message: '获取处置部门失败，' + res.data.message,
              type: 'warning',
              duration: 1000
            })
          }
        })
    },
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
    // 确定按钮
    handleOk() {
      console.log(this.formData1.eventTag)
      this.$refs['formData1'].validate((valid) => {
        if (valid) {
          let dealDeptId = ''
          console.log(typeof this.formData1.dealDeptId, 'typeof')
          if (typeof this.formData1.dealDeptId === 'object') {
            if (this.formData1.dealDeptId.length === 1) {
              dealDeptId = this.formData1.dealDeptId[0]
            } else {
              dealDeptId = this.formData1.dealDeptId[1]
            }
          } else {
            dealDeptId = this.formData1.dealDeptId
          }

          console.log(dealDeptId, 'dealDeptId')
          let params = {}
          let url = '/binhuapis/event/dispatch'
          if (this.title === '批量派遣') {
            let eventIds = ''
            let taskIds = ''
            this.formData2.some((e) => {
              eventIds += e.eventId + ','
              taskIds += e.taskId + ','
            })
            let eventTag = ''
            if (typeof this.formData1.eventTag === 'object') {
              if (this.formData1.eventTag) {
                if (this.formData1.eventTag.length === 1) {
                  eventTag = this.formData1.eventTag[0]
                } else if (this.formData1.eventTag.length === 2) {
                  eventTag = this.formData1.eventTag[1]
                } else {
                  eventTag = this.formData1.eventTag[2]
                }
              }
            } else {
              eventTag = this.formData1.eventTag
            }
            eventIds = eventIds.substring(0, eventIds.lastIndexOf(','))
            taskIds = taskIds.substring(0, taskIds.lastIndexOf(','))
            params = {
              eventIds: eventIds,
              dealDeptId: dealDeptId,
              dealPersonId: this.formData1.dealPersonId,
              dealStartTime: this.formData1.dealStartTime,
              // dealEndTime: this.formData1.dealEndTime,
              distributeInfo: this.formData1.distributeInfo,
              // eventTagId: eventTag,
              taskId: taskIds
            }
            if (this.reportSource !== '政务热线') {
              params.dealEndTime = this.formData1.dealEndTime
              params.eventTagId = eventTag
            }
            console.log(eventTag)
            axios.post(url, qs.stringify(params)).then((r) => {
              if (r.data.code === 200) {
                this.$notify({
                  title: '成功',
                  message: '派遣成功',
                  type: 'success',
                  duration: 1000
                })
                this.$emit('update:multipleSelection', [])
                this.$emit('success')
                this.handleClose()
              } else {
                this.$notify({
                  title: '警告',
                  message: '派遣失败，' + r.data.message,
                  type: 'warning',
                  duration: 1000
                })
              }
            })
          } else {
            let eventTag = ''
            if (typeof this.formData1.eventTag === 'object') {
              if (this.formData1.eventTag) {
                if (this.formData1.eventTag.length === 1) {
                  eventTag = this.formData1.eventTag[0]
                } else if (this.formData1.eventTag.length === 2) {
                  eventTag = this.formData1.eventTag[1]
                } else {
                  eventTag = this.formData1.eventTag[2]
                }
              }
            } else {
              eventTag = this.formData1.eventTag
            }
            params = {
              eventIds: this.formData2.eventId,
              dealDeptId: dealDeptId,
              dealPersonId: this.formData1.dealPersonId,
              dealStartTime: this.formData1.dealStartTime,
              dealEndTime: this.formData1.dealEndTime,
              distributeInfo: this.formData1.distributeInfo,
              eventTagId: eventTag,
              taskId: this.formData2.taskId
            }

            axios.post(url, qs.stringify(params)).then((r) => {
              if (r.data.code === 200) {
                this.$notify({
                  title: '成功',
                  message: '派遣成功',
                  type: 'success',
                  duration: 1000
                })
                this.$emit('success')
                this.handleClose()
              } else {
                this.$notify({
                  title: '警告',
                  message: '派遣失败，' + r.data.message,
                  type: 'warning',
                  duration: 1000
                })
              }
            })
          }
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
    },
    handleClose() {
      this.formData1 = {
        dealDeptId: '',
        dealPersonId: '',
        checkerName: '',
        dealStartTime: moment().format('yyyy-MM-DD HH:mm:ss'),
        dealEndTime: '',
        distributeInfo: '',
        eventTag: ''
      }
      this.formData2 = {}
      this.disabledTooltip = false
      this.disable = true
      this.dealPersonList = []
      this.deadlineTimeShow = false
      // 刷新
      if (this.isChange) {
        this.$emit('refresh')
      }
      this.isChange = false
      this.$emit('update:dialogData', {})
      this.$emit('update:dialog', false)
      this.$emit('close')
    },
    siteImg(r) {
      this.formData1.files = r
    }
  }
}
</script>
<style lang="scss" scoped>
.eventToBeSentDialog {
  .files {
    margin-left: 40px;
  }
  .info_body {
    overflow: auto;
    overflow-x: hidden;
    height: 600px;
    .stateBorder {
      display: inline-block;
      width: 220px;
      border-radius: 5px;
      border: 1px solid #dcdfe6;
      padding-left: 10px;
    }
  }
}
</style>
