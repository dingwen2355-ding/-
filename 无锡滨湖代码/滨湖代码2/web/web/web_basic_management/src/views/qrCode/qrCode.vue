<template>
  <div class="qrCode">
    <van-nav-bar title="事件上报" />

    <div class="mapView" v-show="dialogVisible">
      <div class="input-item-lnglat" v-show="false">
        <div class="input-item-prepend">
          <span class="input-item-text">经纬度</span>
        </div>
        <input id="lnglat" type="text" disabled />
      </div>
      <div class="input-item-address">
        <div class="input-item-prepend">
          <span class="input-item-text">地址</span>
        </div>
        <textarea id="address" />
      </div>
      <!-- <div id="myPageTop">
        <table>
          <tr>
            <td>
              <label>请输入关键字：</label>
            </td>
          </tr>
          <tr>
            <td>
              <input id="tipinput" />
            </td>
          </tr>
        </table>
      </div>-->
      <!-- <div id="panel" @click="panel"></div> -->
      <div class="mapPath" v-if="pathList.length !== 0">
        <div
          v-for="item in pathList"
          :key="item.name"
          @click="mapPathCilck(item)"
          :class="item.name === activeName ? 'active' : ''"
        >{{ item.name }}</div>
      </div>
      <van-button class="searchbtn" size="small" type="default" @click="search">搜索</van-button>
      <van-button class="confirmbtn" size="small" type="default" @click="confirm">确定</van-button>
      <div id="container"></div>
    </div>
    <div v-show="!dialogVisible">
      <van-collapse v-model="active">
        <van-collapse-item title="● 事件基础信息" name="1">
          <van-form ref="form">
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
            <van-field
              class="pt-page"
              name="address"
              label="事件发生地址"
              rows="2"
              autosize
              type="textarea"
              v-model="formData1.address"
              :rules="[{ required: true, message: '请填写事件发生地址' }]"
            >
              <template #button>
                <van-button size="small" type="default" @click="mapShow">
                  <van-icon name="location-o" />
                </van-button>
              </template>
            </van-field>
            <!-- <van-field v-model="formData1.gpsx" center clearable label="精度坐标" placeholder="请选择精度坐标">
        <template #button>
          <van-button size="small" type="default" @click="mapShow">
            <van-icon name="location-o" />
          </van-button>
        </template>
      </van-field>

      <van-field v-model="formData1.gpsy" center clearable label="纬度坐标" placeholder="请选择纬度坐标">
        <template #button>
          <van-button size="small" type="default" @click="mapShow">
            <van-icon name="location-o" />
          </van-button>
        </template>
            </van-field>-->
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

            <!-- 附件 -->
          </van-form>
          <div class="filesPhone">
            <span style="color: #646566">事件附件</span>
            <AttachmentUpload @success="siteImg" :title="title" />
          </div>
          <van-cell center title="是否已处置">
            <template #right-icon>
              <van-switch v-model="formData3.delivery" size="24" active-color="#13ce66" inactive-color="#ff4949" />
            </template>
          </van-cell>
        </van-collapse-item>
        <van-collapse-item title="● 事件处置过程信息" name="2" v-if="formData3.delivery">
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
                :columns-order="['year', 'month', 'day', 'hour', 'minute']"
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
                :columns-order="['year', 'month', 'day', 'hour', 'minute']"
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
            <span style="color: #646566">处置附件</span>
            <AttachmentUpload @success="siteImg2" :title="title" />
          </div>
        </van-collapse-item>
      </van-collapse>

      <div class="footer">
        <van-button type="primary" block @click="handleOk">确认</van-button>
      </div>
    </div>

    <!-- <div class="info">
      <h4 id="status"></h4>
      <p id="result"></p>
    </div>
    <div class="input-item">
      <div class="input-item-prepend">
        <span class="input-item-text">经纬度</span>
      </div>
      <input id="lnglat" type="text" value="116.39,39.9" />
    </div>
    <div class="input-item">
      <div class="input-item-prepend">
        <span class="input-item-text">地址</span>
      </div>
      <input id="address" type="text" disabled />
    </div>-->
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
      activeName: '',
      pathList: [],
      dialogVisible: false,
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
      formData3: { delivery: '' },
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
      changeDate: new Date(),
      alarmMarker: null
    }
  },
  computed: {},
  watch: {},
  mounted() {
    window.document.title = '事件上报'
    // this.getEventTagList()
    // this.getReportSourceList()
    // this.getInfoSourceList()
    // this.getGridNameList()
    // this.getTopicNameList()
    // this.getEventTypeList()
    // this.$root.eventBus.$on('clickMap', (e) => {
    //   // this.mapVisible = true
    //   this.formData1.gpsx = e[0]
    //   this.formData1.gpsy = e[1]
    // })
    // this.timeFormat(new Date())
  },
  methods: {
    mapPathCilck(v) {
      this.activeName = v.name
      document.getElementById('address').value = v.name
      this.init(3)
    },
    search() {
      this.init(2)
      // 关键字查询
      // placeSearch.search(document.getElementById('address').valuefunction (status, result){
      //   console.log(status)
      // })
    },
    confirm() {
      var lnglat = document.getElementById('lnglat').value.split(',')
      var address = document.getElementById('address').value
      console.log(lnglat)
      if (lnglat[0] !== '') {
        this.formData1.gpsx = lnglat[0]
        this.formData1.gpsy = lnglat[1]
        this.formData1.address = address
        this.dialogVisible = false
        console.log(lnglat)
      } else {
        Toast('请选择地址')
      }
    },
    mapShow() {
      this.dialogVisible = true
      this.init(1)
    },
    init(i) {
      var tat = this
      let AMap = window.AMap
      var map = new AMap.Map('container', {
        resizeEnable: true
      })
      // 青岛：370200
      var geocoder = new AMap.Geocoder({ city: '青岛', radius: 500 })
      if (i === 1) {
        var geolocation = new AMap.Geolocation({
          enableHighAccuracy: true, // 是否使用高精度定位，默认:true
          timeout: 10000, // 超过10秒后停止定位，默认：5s
          position: 'RB', // 定位按钮的停靠位置
          offset: [10, 20], // 定位按钮与设置的停靠位置的偏移量，默认：[10, 20]
          zoomToAccuracy: true // 定位成功后是否自动调整地图视野到定位点
        })
        map.addControl(geolocation)
        geolocation.getCurrentPosition(function (status, result) {
          if (status === 'complete') {
            tat.onComplete(result, map, marker, geocoder)
          } else {
            Toast('定位失败!')
            onError(result)
          }
        })
      } else if (i === 2) {
        var placeSearch = new AMap.PlaceSearch({
          pageSize: 20, // 单页显示结果条数
          pageIndex: 1, // 页码
          city: '青岛',
          citylimit: true, // 是否强制限制在设置的城市内搜索
          map: map, // 展现结果的地图实例
          // panel: "panel", // 结果列表将在此容器中进行展示。
          autoFitView: true // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
        })
        placeSearch.search(document.getElementById('address').value, function (status, result) {
          console.log(status, 11)
          if (status === 'no_data') {
            tat.pathList = []
            Toast('请输入关键词搜索')
          } else {
            tat.pathList = result.poiList.pois
          }
          console.log(tat.pathList)
        })
      } else if (i === 3) {
        console.log(document.getElementById('address').value)
        var address = document.getElementById('address').value
        geocoder.getLocation(address, function (status, result) {
          if (status === 'complete' && result.geocodes.length) {
            var lnglat = result.geocodes[0].location
            console.log(lnglat, 22)
            document.getElementById('lnglat').value = lnglat
            marker.setPosition(lnglat)
            map.add(marker)
            map.setFitView(marker)
            // tat.init(2)
          } else {
            Toast('根据地址查询位置失败')
          }
        })
      }
      var marker = new AMap.Marker()
      map.on('click', function (e) {
        document.getElementById('lnglat').value = e.lnglat
        // var lnglat = document.getElementById('lnglat').value.split(',')
        tat.regeoCode(map, marker, geocoder)
      })
      // 解析定位错误信息
      function onError(data) {
        Toast('定位失败')
        // document.getElementById('status').innerHTML = '定位失败'
        // document.getElementById('result').innerHTML = '失败原因排查信息:' + data.message + '</br>浏览器返回信息：' + data.originMessage
      }
    },
    regeoCode(map, marker, geocoder) {
      var lnglat = document.getElementById('lnglat').value.split(',')
      map.add(marker)
      marker.setPosition(lnglat)

      geocoder.getAddress(lnglat, function (status, result) {
        if (status === 'complete' && result.regeocode) {
          var address = result.regeocode.formattedAddress
          document.getElementById('address').value = address
          // document.getElementById('tipinput').value = address
        } else {
          Toast('根据经纬度查询地址失败')
        }
      })
    },
    // 解析定位结果
    onComplete(data, map, marker, geocoder) {
      // document.getElementById('status').innerHTML = '定位成功'
      var str = []
      str.push(data.position)
      // str.push('定位类别：' + data.location_type)
      // if (data.accuracy) {
      //   str.push('精度：' + data.accuracy + ' 米')
      // }// 如为IP精确定位结果则没有精度信息
      // str.push('是否经过偏移：' + (data.isConverted ? '是' : '否'))
      // document.getElementById('result').innerHTML = str.join('<br>')
      document.getElementById('lnglat').value = data.position
      this.regeoCode(map, marker, geocoder)
      // this.toMap()
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
    timeFormat(time) {
      // 时间格式化 2019-09-08
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
    // 确定按钮
    handleOk() {
      if (!this.formData3.delivery) {
        this.$refs.form
          .validate()
          .then(() => {
            let params = {
              eventName: this.formData1.address + '发生事件',
              address: this.formData1.address,
              desc: this.formData1.desc,
              gpsx: this.formData1.gpsx,
              gpsy: this.formData1.gpsy,
              // eventTag: this.formData1.eventTag === '' ? '' : this.eventTagList.filter((v) => v.tagName === this.formData1.eventTag)[0].tagId,
              // topicName: this.formData1.topicName,
              // eventType: this.formData1.eventType,
              // level: this.formData1.level,
              // damageDegree: this.formData1.damageDegree,
              channel: this.formData1.channel,
              reportPerson: this.formData1.reportPerson,
              reportPhone: this.formData1.reportPhone,
              // reportSource: this.reportSourceList.filter((v) => v.sourceName === this.formData1.reportSource)[0].sourceId,
              // infoSource: this.infoSourceList.filter((v) => v.sourceName === this.formData1.infoSource)[0].sourceId,
              happenTime: moment().format('yyyy-MM-DD HH:mm:ss'),
              syncTime: moment().format('yyyy-MM-DD HH:mm:ss')
              // deadlineTime: this.formData1.deadlineTime
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
          })
          .catch(() => {
            Toast('检查表单输入内容的合法性！')
          })
      } else {
        this.$refs.form
          .validate()
          .then(() => {
            this.$refs.form2
              .validate()
              .then(() => {
                let params = {
                  eventName: this.formData1.address + '发生事件',
                  address: this.formData1.address,
                  desc: this.formData1.desc,
                  gpsx: this.formData1.gpsx,
                  gpsy: this.formData1.gpsy,
                  // eventTag: this.formData1.eventTag === '' ? '' : this.eventTagList.filter((v) => v.tagName === this.formData1.eventTag)[0].tagId,
                  // topicName: this.formData1.topicName,
                  // eventType: this.formData1.eventType,
                  // level: this.formData1.level,
                  // damageDegree: this.formData1.damageDegree,
                  channel: this.formData1.channel,
                  reportPerson: this.formData1.reportPerson,
                  reportPhone: this.formData1.reportPhone,
                  // reportSource: this.reportSourceList.filter((v) => v.sourceName === this.formData1.reportSource)[0].sourceId,
                  // infoSource: this.infoSourceList.filter((v) => v.sourceName === this.formData1.infoSource)[0].sourceId,
                  happenTime: moment().format('yyyy-MM-DD HH:mm:ss'),
                  syncTime: moment().format('yyyy-MM-DD HH:mm:ss')
                  // deadlineTime: this.formData1.deadlineTime
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
              })
              .catch(() => {
                Toast('检查表单输入内容的合法性！')
              })
          })
          .catch(() => {
            Toast('检查表单输入内容的合法性！')
          })
      }
    },
    // 多个表单验证
    checkForm(formName) {
      return new Promise((resolve, reject) => {
        this.$refs.formName.validate((valid) => {
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
  background: #ffffff;
  padding: 0 0.1rem 0 0.1rem;

  .mapView {
    height: 100%;
    width: 100%;
    position: relative;
    .input-item-lnglat {
      position: absolute;
      top: 0.1rem;
      z-index: 99999;
    }
    .input-item-address {
      position: absolute;
      top: 0.1rem;
      z-index: 99999;
    }
    .searchbtn {
      position: absolute;
      width: 0.6rem;
      top: 0.31rem;
      right: 0.2rem;
      z-index: 99999;
    }
    .confirmbtn {
      position: absolute;
      width: 0.6rem;
      top: 0.71rem;
      right: 0.2rem;
      z-index: 99999;
    }
    #container {
      height: 100%;
      width: 100%;
    }
    .mapPath {
      display: flex;
      flex-direction: column;
      position: absolute;
      background-color: white;
      max-height: 30%;
      overflow-y: auto;
      top: 1.05rem;
      width: 2.5rem;
      height: 1rem;
      z-index: 99999;
      .active {
        background: #9cdcfe;
      }
    }
    #panel {
      background-color: white;
      max-height: 30%;
      overflow-y: auto;

      width: 10rem;
      z-index: 99999;
    }
    #address {
      width: 100%;
      height: 0.7rem;
    }
  }

  .info {
    width: 26rem;
  }
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
    height: 0.4rem;

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
