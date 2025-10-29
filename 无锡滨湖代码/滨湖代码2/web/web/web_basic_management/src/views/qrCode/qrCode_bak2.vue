<template>
  <div class="qrCode">
    <van-collapse v-model="active">
      <van-collapse-item title="● 事件基础信息" name="1">
        <van-form ref="form">
          <van-field
            class="pt-page"
            name="eventName"
            label="事件名称"
            v-model="formData1.eventName"
            :rules="[{ required: true, message: '请填写事件名称' }]"
          ></van-field>
          <van-field
            class="pt-page"
            name="address"
            label="事件发生地址"
            rows="2"
            autosize
            type="textarea"
            v-model="formData1.address"
            :rules="[{ required: true, message: '请填写事件发生地址' }]"
          ></van-field>
          <van-field
            class="pt-page"
            name="desc"
            rows="2"
            autosize
            type="textarea"
            label="事件详细描述"
            v-model="formData1.desc"
            :rules="[{ required: true, message: '请填写事件详细描述' }]"
          ></van-field>
          <van-field v-model="formData1.eventTag" is-link readonly label="事件标签" @click="showPicker = true" />
          <van-popup v-model="showPicker" round position="bottom">
            <van-picker
              title="选择事件标签"
              show-toolbar
              :columns="eventTagList"
              value-key="tagName"
              @cancel="showPicker = false"
              @confirm="changeConfirm"
            />
          </van-popup>
          <van-field v-model="formData1.topicName" is-link readonly label="主题名称" @click="showtopicName = true" />
          <van-popup v-model="showtopicName" round position="bottom">
            <van-picker
              title="选择主题名称"
              show-toolbar
              :columns="topicNameList"
              value-key="topicName"
              @cancel="showtopicName = false"
              @confirm="changeshowtopicName"
            />
          </van-popup>
          <van-field v-model="formData1.eventType" is-link readonly label="事件类型" @click="showEventType = true" />
          <van-popup v-model="showEventType" round position="bottom">
            <van-picker
              title="选择事件类型"
              show-toolbar
              :columns="eventTypeList"
              value-key="typeName"
              @cancel="showEventType = false"
              @confirm="changeShowEventType"
            />
          </van-popup>
          <van-field v-model="formData1.level" is-link readonly label="事件级别" @click="showLevel = true" />
          <van-popup v-model="showLevel" round position="bottom">
            <van-picker
              title="选择事件级别"
              show-toolbar
              :columns="levelList"
              value-key="levelName"
              @cancel="showLevel = false"
              @confirm="changeShowLevel"
            />
          </van-popup>
          <van-field name="damageDegree" label="事件破坏程度" v-model="formData1.damageDegree"></van-field>
          <van-field :readonly="true" name="channel" label="事件上报渠道" v-model="formData1.channel"></van-field>
          <van-field
            name="reportPerson"
            class="pt-page"
            :rules="[{ required: true, message: '请填写上报人姓名' }]"
            label="上报人姓名"
            v-model="formData1.reportPerson"
          ></van-field>
          <van-field
            name="reportPhone"
            label="上报人电话"
            v-model="formData1.reportPhone"
            class="pt-page"
            :rules="[{ required: true, message: '请填写上报人电话' }]"
          ></van-field>
          <!-- <van-field v-model="formData1.reportSource" is-link readonly label="事件上报来源" @click="showReportSource = true" />
          <van-popup v-model="showReportSource" round position="bottom">
            <van-picker
              title="选择事件上报来源"
              show-toolbar
              :columns="reportSourceList"
              value-key="sourceName"
              @cancel="showReportSource = false"
              @confirm="changeShowReportSource"
            />
          </van-popup>
          <van-field v-model="formData1.infoSource" is-link readonly label="信息来源" @click="showInfoSource = true" />
          <van-popup v-model="showInfoSource" round position="bottom">
            <van-picker
              title="选择信息来源"
              show-toolbar
              :columns="infoSourceList"
              value-key="sourceName"
              @cancel="showInfoSource = false"
              @confirm="changeShowInfoSource"
            />
          </van-popup>-->
          <van-field
            class="pt-page"
            v-model="formData1.happenTime"
            is-link
            readonly
            label="事件发生时间"
            @click="showHappenTime = true"
            :rules="[{ required: true, message: '选择事件发生时间' }]"
          />
          <van-popup v-model="showHappenTime" round position="bottom">
            <van-datetime-picker
              label="事件发生时间"
              v-model="currentDate"
              type="datetime"
              :columns-order="['year', 'month', 'day','hour','minute']"
              :formatter="formatter"
              @cancel="showHappenTime = false"
              @confirm="changeShowHappenTime"
              @changeFn="onShift"
              title="选择事件发生时间"
            />
          </van-popup>
          <van-field v-model="formData1.syncTime" is-link readonly label="事件上报时间" @click="showSyncTime = true" />
          <van-popup v-model="showSyncTime" round position="bottom">
            <van-datetime-picker
              label="事件上报时间"
              v-model="currentDate"
              type="datetime"
              :columns-order="['year', 'month', 'day','hour','minute']"
              :formatter="formatter"
              @cancel="showSyncTime = false"
              @confirm="changeShowSyncTime"
              @changeFn="onShift"
              title="选择事件上报时间"
            />
          </van-popup>
          <van-field v-model="formData1.deadlineTime" is-link readonly label="处置截止时间" @click="showDeadlineTime = true" />
          <van-popup v-model="showDeadlineTime" round position="bottom">
            <van-datetime-picker
              label="处置截止时间"
              v-model="currentDate"
              type="datetime"
              :columns-order="['year', 'month', 'day','hour','minute']"
              :formatter="formatter"
              @cancel="showDeadlineTime = false"
              @confirm="changeShowDeadlineTime"
              @changeFn="onShift"
              title="选择处置截止时间"
            />
          </van-popup>
        </van-form>

        <!-- 附件 -->
        <div class="filesPhone">
          <span style="color:#646566">附件信息</span>
          <AttachmentUpload @success="siteImg" :title="title" />
        </div>
        <van-form>
          <van-field v-model="formData3.delivery" is-link readonly label="是否已处置" @click="showDelivery = true" />
          <van-popup v-model="showDelivery" round position="bottom">
            <van-picker
              title="选择是否已处置"
              show-toolbar
              :columns="disposalList"
              value-key="name"
              @cancel="showDelivery = false"
              @confirm="changeShowDelivery"
            />
          </van-popup>
        </van-form>
      </van-collapse-item>
      <van-collapse-item title="● 事件处置过程信息" name="2" v-if="formData3.delivery==='是'">
        <van-form ref="form2">
          <van-field
            v-model="formData2.dealStartTime"
            is-link
            readonly
            label="处置开始时间"
            @click="showDealStartTime = true"
            class="pt-page"
            :rules="[{ required: true, message: '选择事件发生时间' }]"
          />
          <van-popup v-model="showDealStartTime" round position="bottom">
            <van-datetime-picker
              label="处置开始时间"
              v-model="currentDate"
              type="datetime"
              :columns-order="['year', 'month', 'day','hour','minute']"
              :formatter="formatter"
              @cancel="showDealStartTime = false"
              @confirm="changeShowDealStartTime"
              @changeFn="onShift"
              title="选择处置开始时间"
            />
          </van-popup>
          <van-field
            v-model="formData2.dealEndTime"
            is-link
            readonly
            label="处置结束时间"
            @click="showDealEndTime = true"
            class="pt-page"
            :rules="[{ required: true, message: '选择处置结束时间' }]"
          />
          <van-popup v-model="showDealEndTime" round position="bottom">
            <van-datetime-picker
              label="处置结束时间"
              v-model="currentDate"
              type="datetime"
              :columns-order="['year', 'month', 'day','hour','minute']"
              :formatter="formatter"
              @cancel="showDealEndTime = false"
              @confirm="changeShowDealEndTime"
              @changeFn="onShift"
              title="选择处置结束时间"
            />
          </van-popup>
          <van-field
            name="desc"
            rows="2"
            autosize
            type="textarea"
            label="处置反馈"
            v-model="formData2.dealInfo"
            class="pt-page"
            :rules="[{ required: true, message: '请填写处置反馈' }]"
          ></van-field>
          <!-- <van-field name="dealPersonName" label="处置人姓名" v-model="formData2.dealPersonName"></van-field>
          <van-field name="dealPersonPhone" label="处置人电话" v-model="formData2.dealPersonPhone"></van-field>-->
        </van-form>
        <!-- 附件 -->
        <div class="filesPhone">
          <span style="color:#646566">附件信息</span>
          <AttachmentUpload @success="siteImg2" :title="title" />
        </div>
      </van-collapse-item>
    </van-collapse>
    <div class="footer">
      <van-button type="primary" block @click="handleOk">确认</van-button>
    </div>
  </div>
</template>

<script>
import AttachmentUpload from '../../components/attachmentUpload.vue'
import axios from '@/utils/request'
import moment from 'moment'
import '../../plugins/vant.js'
import 'vant/lib/index.css'
import { Toast } from 'vant'

export default {
  name: 'qrCode',
  components: { AttachmentUpload },
  data() {
    return {
      active: ['1', '2'],
      showPicker: false,
      showtopicName: false,
      showEventType: false,
      showLevel: false,
      showReportSource: false,
      showInfoSource: false,
      showHappenTime: false,
      showSyncTime: false,
      showDeadlineTime: false,
      showDealStartTime: false,
      showDealEndTime: false,
      showDelivery: false,
      tableData: [],
      title: '居民上报',
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
        // reportSource: '自登记', // 事件上报来源
        channel: 'APP填报', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: moment().format('yyyy-MM-DD HH:mm:ss'), // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '', // 上报人电话
        // infoSource: '自登记', // 信息来源
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

        dealInfo: '已按要求处置完毕', // 处置反馈
        files: []
      },
      rules2: {
        dealStartTime: [{ required: true, message: '请选择处置开始时间', trigger: 'blur' }],
        dealEndTime: [{ required: true, message: '请选择处置截止时间', trigger: 'blur' }],
        dealInfo: [{ required: true, message: '请输入处置反馈', trigger: 'blur' }]
      },
      formData3: { delivery: '否' },
      rules3: {},
      eventTagList: [], // 事件标签列表
      levelList: [
        { levelName: '紧急', levelId: '1' },
        { levelName: '一般', levelId: '2' }
      ],
      disposalList: [{ name: '是' }, { name: '否' }],
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
      mapVisible: true,
      currentDate: new Date(),
      changeDate: new Date()
    }
  },
  computed: {

  },
  watch: {

  },
  mounted() {
    window.document.title = '事件上报'
    this.getEventTagList()
    // this.getReportSourceList()
    // this.getInfoSourceList()
    // this.getGridNameList()
    this.getTopicNameList()
    this.getEventTypeList()
    this.$root.eventBus.$on('clickMap', (e) => {
      // this.mapVisible = true
      this.formData1.gpsx = e[0]
      this.formData1.gpsy = e[1]
    })
    this.timeFormat(new Date())
  },
  methods: {
    // 处置结束时间
    changeShowDelivery(val) {
      this.formData3.delivery = val.name
      this.showDelivery = false
    },
    // 处置结束时间
    changeShowDealEndTime() {
      this.formData2.dealEndTime = this.timeFormat(this.currentDate)
      this.showDealEndTime = false
    },
    // 处置开始时间
    changeShowDealStartTime() {
      this.formData2.dealStartTime = this.timeFormat(this.currentDate)
      this.showDealStartTime = false
    },
    // 处置截止时间
    changeShowDeadlineTime() {
      this.formData1.deadlineTime = this.timeFormat(this.currentDate)
      this.showDeadlineTime = false
    },
    // 事件上报时间
    changeShowSyncTime() {
      this.formData1.syncTime = this.timeFormat(this.currentDate)
      this.showSyncTime = false
    },
    // 事件发生时间
    formatter(type, val) {
      if (type === 'year') {
        return val + '年'
      }
      if (type === 'month') {
        return val + '月'
      }
      if (type === 'day') {
        return val + '日'
      }
      if (type === 'hour') {
        return val + '时'
      }
      if (type === 'minute') {
        return val + '分'
      }
      return val
    },
    onShift() {
      this.changeDate = this.currentDate // Tue Sep 08 2020 00:00:00 GMT+0800 (中国标准时间)
    },
    timeFormat(time) { // 时间格式化 2019-09-08
      let year = time.getFullYear()
      let month = time.getMonth() + 1
      let day = time.getDate()
      let hour = time.getHours()
      let minute = time.getMinutes()
      if (month < 10) {
        month = '0' + month
      }
      if (day < 10) {
        day = '0' + day
      }
      if (hour < 10) {
        hour = '0' + hour
      }
      if (minute < 10) {
        minute = '0' + minute
      }
      return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + '00'
    },
    changeShowHappenTime(val) {
      this.formData1.happenTime = this.timeFormat(this.currentDate)
      this.showHappenTime = false
    },
    // // 信息来源
    // changeShowInfoSource(val) {
    //   this.formData1.infoSource = val.sourceName
    //   this.showInfoSource = false
    // },
    // // 事件上报来源
    // changeShowReportSource(val) {
    //   this.formData1.reportSource = val.sourceName
    //   this.showReportSource = false
    // },
    // 事件类型
    changeShowLevel(val) {
      this.formData1.level = val.levelName
      this.showLevel = false
    },
    // 事件类型
    changeShowEventType(val) {
      this.formData1.eventType = val.typeName
      this.showEventType = false
    },
    // 主题名称
    changeshowtopicName(val) {
      this.formData1.topicName = val.topicName
      this.showtopicName = false
    },
    // 事件标签
    changeConfirm(val) {
      this.formData1.eventTag = val.tagName // 传值
      this.showPicker = false
    },

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
    // // 获取事件信息来源
    // getInfoSourceList() {
    //   axios.get('/binhuapis/event/queryDictEventInfoSource').then((res) => {
    //     if (res.data.code === 200) {
    //       this.infoSourceList = res.data.data
    //     }
    //   })
    // },
    // // 获取上报来源
    // getReportSourceList() {
    //   axios.get('/binhuapis/event/queryDictEventReportSource').then((res) => {
    //     if (res.data.code === 200) {
    //       this.reportSourceList = res.data.data
    //     }
    //   })
    // },
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
      if (this.formData3.delivery === '否') {
        this.$refs.form.validate().then(() => {
          let params = {
            eventName: this.formData1.eventName,
            address: this.formData1.address,
            desc: this.formData1.desc,
            eventTag: this.formData1.eventTag === '' ? '' : this.eventTagList.filter((v) => v.tagName === this.formData1.eventTag)[0].tagId,
            topicName: this.formData1.topicName,
            eventType: this.formData1.eventType,
            level: this.formData1.level,
            damageDegree: this.formData1.damageDegree,
            channel: this.formData1.channel,
            reportPerson: this.formData1.reportPerson,
            reportPhone: this.formData1.reportPhone,
            // reportSource: this.reportSourceList.filter((v) => v.sourceName === this.formData1.reportSource)[0].sourceId,
            // infoSource: this.infoSourceList.filter((v) => v.sourceName === this.formData1.infoSource)[0].sourceId,
            happenTime: this.formData1.happenTime,
            syncTime: this.formData1.syncTime,
            deadlineTime: this.formData1.deadlineTime
          }
          let formData = new FormData()
          formData.append('eventInfo', JSON.stringify(params))
          // let fileList = []
          for (let i = 0; i < this.formData1.files.length; i++) {
            // fileList.push()
            formData.append('files', this.formData1.files[i].raw)
          }
          let url = '/binhuapis/event/uploadEventByResident'
          axios.post(url, formData).then((r) => {
            if (r.data.code === 200) {
              Toast('提交成功')
              this.handleClose()
              window.location.reload()
            } else {
              Toast('提交失败' + r.data.message)
            }
          })
        }).catch(() => {
          Toast('检查表单输入内容的合法性！')
        })
      } else {
        this.$refs.form.validate().then(() => {
          this.$refs.form2.validate().then(() => {
            let params = {
              eventName: this.formData1.eventName,
              address: this.formData1.address,
              desc: this.formData1.desc,
              eventTag: this.formData1.eventTag === '' ? '' : this.eventTagList.filter((v) => v.tagName === this.formData1.eventTag)[0].tagId,
              topicName: this.formData1.topicName,
              eventType: this.formData1.eventType,
              level: this.formData1.level,
              damageDegree: this.formData1.damageDegree,
              channel: this.formData1.channel,
              reportPerson: this.formData1.reportPerson,
              reportPhone: this.formData1.reportPhone,
              // reportSource: this.reportSourceList.filter((v) => v.sourceName === this.formData1.reportSource)[0].sourceId,
              // infoSource: this.infoSourceList.filter((v) => v.sourceName === this.formData1.infoSource)[0].sourceId,
              happenTime: this.formData1.happenTime,
              syncTime: this.formData1.syncTime,
              deadlineTime: this.formData1.deadlineTime
            }
            let formData = new FormData()
            formData.append('eventInfo', JSON.stringify(params))
            formData.append('dealStartTime', this.formData2.dealStartTime)
            formData.append('dealEndTime', this.formData2.dealEndTime)
            formData.append('dealInfo', this.formData2.dealInfo)
            for (let i = 0; i < this.formData1.files.length; i++) {
              formData.append('eventFiles', this.formData1.files[i].raw)
            }
            for (let i = 0; i < this.formData2.files.length; i++) {
              formData.append('flowFiles', this.formData2.files[i].raw)
            }
            let url = '/binhuapis/event/uploadEndEventByResident'

            axios.post(url, formData).then((r) => {
              if (r.data.code === 200) {
                Toast('提交成功')
                this.handleClose()
                window.location.reload()
              } else {
                Toast('提交失败' + r.data.message)
              }
            })
          }).catch(() => {
            Toast('检查表单输入内容的合法性！')
          })
        }).catch(() => {
          Toast('检查表单输入内容的合法性！')
        })
      }
    },
    // 多个表单验证
    checkForm(formName) {
      return new Promise((resolve, reject) => {
        this.$refs.formName.validate(valid => {
          if (valid) {
            resolve()
          } else {

          }
        })
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
        // reportSource: '自登记', // 事件上报来源
        channel: 'APP填报', // 事件上报渠道
        gridName: '', // 所属社区
        gpsx: '', // 经度坐标
        gpsy: '', // 纬度坐标
        deadlineTime: '', // 处理截止时间
        endTime: '', // 事件结案时间
        syncTime: moment().format('yyyy-MM-DD HH:mm:ss'), // 事件上报时间
        reportPerson: '', // 上报人姓名
        reportPhone: '', // 上报人电话
        // infoSource: '自登记', // 信息来源
        files: []
      }
      this.formData2 = {
        dealStartTime: '', // 处置开始时间
        dealEndTime: '', // 处置截止时间

        dealInfo: '已按要求处置完毕', // 处置反馈
        files: []
      }
    },
    siteImg(r) {
      this.formData1.files = r
    },
    siteImg2(r) {
      this.formData2.files = r
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
    margin-left: 0.15rem;
  }
  .pt-page {
    padding-left: 0.06rem;
  }
  .pt-page:before {
    content: '*';
    font-size: 20px;
    color: red;
  }
  .van-collapse-item__title {
    background: #efefef;
    border-bottom: 1px solid #e4eaf0;
    border-top: 1px solid #e4eaf0;
  }
  .footer {
    padding-top: 0.1rem;
    padding-bottom: 0.2rem;
    background: #ffffff;
  }
}
</style>
