<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card
          :loading="loading"
          title="待处理报警"
          :total="this.alarmStatistic.unprocessedCount | NumberFormat"
          :show-content="false"
          :show-footer="false"
          @click="onJumpToAlarm"
        >
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card
          :loading="loading"
          title="数据分析告警"
          :total="dataTotal | NumberFormat"
          :show-content="false"
          :show-footer="false"
          @click="onJumpToDataAlarm"
        >
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
        </chart-card>
      </a-col>
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card
          :loading="loading"
          title="图像分析预警"
          :total="imageTotal | NumberFormat"
          :show-content="false"
          :show-footer="false"
          @click="onJumpToImageAlarm"
        >
          <a-tooltip title="指标说明" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
        </chart-card>
      </a-col>
    </a-row>

    <a-row>
      <a-col :span="24">
        <a-card :loading="loading" :bordered="false" title="今日人流" :style="{ marginTop: '14px' }">
          <a-row>
            <a-col :xxl="8" :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
              <access-list
                :list="accessList"
                :height="260"
                :total-today="accessTotalToday"
                :total-yesterday="accessTotalYesterday"
              />
            </a-col>
            <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
              <e-chart
                class="chart"
                :loading="flowChartLoading"
                :loading-options="loadingOptions"
                :autoresize="true"
                :option="flowBarOption"
              />
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>

    <a-row>
      <a-col :span="24">
        <a-card
          :loading="loading"
          :body-style="{ padding: '0 0 20px 0' }"
          :bordered="false"
          title="设备统计"
          :style="{ marginTop: '14px' }"
        >
          <a-space direction="vertical" size="middle" style="width: 100%">
            <a-row>
              <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
                <div class="device-statistic">
                  <div class="title-container">
                    <div class="title">接入设备概况</div>
                    <a-button icon="more" type="link" @click="onJumpToSystemList">更多详情</a-button>
                  </div>
                  <a-row :gutter="[16, 16]">
                    <a-col :span="12">
                      <a-card hoverable>
                        <a-statistic
                          title="接入系统"
                          :value="deviceStatistic.systemTypeCount"
                          :precision="0"
                          :value-style="{ color: '#3f8600' }"
                        >
                        </a-statistic>
                      </a-card>
                    </a-col>
                    <a-col :span="12">
                      <a-card hoverable>
                        <a-statistic
                          title="设备类型"
                          :value="deviceStatistic.deviceTypeCount"
                          :precision="0"
                          :value-style="{ color: '#cf1322' }"
                        >
                        </a-statistic>
                      </a-card>
                    </a-col>
                  </a-row>
                  <a-row :gutter="[16, 16]">
                    <a-col :span="12">
                      <a-card hoverable>
                        <a-statistic
                          title="接入设备数量"
                          :value="deviceStatistic.deviceCount"
                          :precision="0"
                          :value-style="{ color: '#3f8600' }"
                        >
                        </a-statistic>
                      </a-card>
                    </a-col>
                    <a-col :span="12">
                      <a-card hoverable>
                        <a-statistic
                          title="在线设备数量"
                          :value="deviceStatistic.onlineDeviceCount"
                          :precision="0"
                          :value-style="{ color: '#cf1322' }"
                        >
                        </a-statistic>
                      </a-card>
                    </a-col>
                  </a-row>
                </div>
              </a-col>
              <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
                <div class="device-statistic">
                  <div class="title-container">
                    <div class="title">设备在线情况</div>
                    <a-button icon="more" type="link" @click="onJumpToDeviceList(null)">更多详情</a-button>
                  </div>
                  <a-row :gutter="[16, 16]">
                    <a-col :span="8">
                      <a-card hoverable @click="onJumpToDeviceList('1')">
                        <a-statistic
                          title="安防系统"
                          :value="deviceStatistic.deviceOnlineMap.securityProtection.onlineCount"
                          :precision="0"
                          :value-style="{ color: '#3f8600' }"
                        >
                          <template #suffix>
                            <span> / {{ deviceStatistic.deviceOnlineMap.securityProtection.totalCount }}</span>
                          </template>
                        </a-statistic>
                      </a-card>
                    </a-col>
                    <a-col :span="8">
                      <a-card hoverable @click="onJumpToDeviceList('4')">
                        <a-statistic
                          title="消防系统"
                          :value="deviceStatistic.deviceOnlineMap.fireFighting.onlineCount"
                          :precision="0"
                          :value-style="{ color: '#cf1322' }"
                        >
                          <template #suffix>
                            <span> / {{ deviceStatistic.deviceOnlineMap.fireFighting.totalCount }}</span>
                          </template>
                        </a-statistic>
                      </a-card>
                    </a-col>
                    <a-col :span="8">
                      <a-card hoverable @click="onJumpToDeviceList('7')">
                        <a-statistic
                          title="环境监测"
                          :value="deviceStatistic.deviceOnlineMap.environmentalMonitoring.onlineCount"
                          :precision="0"
                          :value-style="{ color: '#cf1322' }"
                        >
                          <template #suffix>
                            <span> / {{ deviceStatistic.deviceOnlineMap.environmentalMonitoring.totalCount }}</span>
                          </template>
                        </a-statistic>
                      </a-card>
                    </a-col>
                  </a-row>
                  <a-row :gutter="[16, 16]">
                    <a-col :span="12">
                      <a-card hoverable @click="onJumpToDeviceList('9')">
                        <a-statistic
                          title="能耗系统"
                          :value="deviceStatistic.deviceOnlineMap.dissipativeSystem.onlineCount"
                          :precision="0"
                          :value-style="{ color: '#3f8600' }"
                        >
                          <template #suffix>
                            <span> / {{ deviceStatistic.deviceOnlineMap.dissipativeSystem.totalCount }}</span>
                          </template>
                        </a-statistic>
                      </a-card>
                    </a-col>
                    <a-col :span="12">
                      <a-card hoverable>
                        <a-statistic title="智能家居" :value="93" :precision="0" :value-style="{ color: '#cf1322' }">
                          <template #suffix>
                            <span> / 100</span>
                          </template>
                        </a-statistic>
                      </a-card>
                    </a-col>
                  </a-row>
                </div>
              </a-col>
            </a-row>
            <a-row>
              <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
                <e-chart
                  class="chart"
                  :loading="deviceChartLoading"
                  :loading-options="loadingOptions"
                  :autoresize="true"
                  :option="deviceFailureBarOption"
                />
              </a-col>
              <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
                <e-chart
                  class="chart"
                  :loading="deviceChartLoading"
                  :loading-options="loadingOptions"
                  :autoresize="true"
                  :option="deviceOnlineRankBarOption"
                />
              </a-col>
            </a-row>
          </a-space>
        </a-card>
      </a-col>
    </a-row>

    <a-row>
      <a-col :span="24">
        <a-card
          :loading="loading"
          :body-style="{ padding: '0 0 20px 0' }"
          :bordered="false"
          title="告警统计"
          :style="{ marginTop: '14px' }"
        >
          <a-row>
            <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
              <e-chart
                class="chart"
                :loading="alarmChartLoading"
                :loading-options="loadingOptions"
                :autoresize="true"
                :option="lineOption"
              />
            </a-col>
            <a-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24">
              <e-chart
                class="chart"
                :loading="alarmChartLoading"
                :loading-options="loadingOptions"
                :autoresize="true"
                :option="pieOption"
              />
            </a-col>
          </a-row>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import ChartCard from '@/components/ChartCard'
import AccessList from './modules/AccessList'
import { getAction } from '@/api/manage'
import moment from 'moment'

export default {
  name: 'IndexChart',
  components: {
    ChartCard,
    AccessList,
  },
  data() {
    return {
      loading: true,
      accessList: [],
      accessTotalToday: 0,
      accessTotalYesterday: 0,
      imageTotal: 0,
      dataTotal: 0,
      flowBarOption: {
        title: {
          text: '近三日人员流量统计',
          textStyle: {
            fontSize: 16,
          },
          left: 'center',
          // top: 6,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        grid: {
          // left: '3%',
          // right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        color: ['rgb(38,163,255)'],
        xAxis: {
          type: 'category',
          data: ['27楼', '28楼'],
        },
        yAxis: {
          type: 'value',
          name: '单位（人次）',
          nameTextStyle: {
            color: '#aaa',
            nameLocation: 'start',
          },
        },
        series: [
          {
            data: [0, 0],
            type: 'bar',
            itemStyle: {
              //上方显示数值
              normal: {
                label: {
                  show: true,
                  position: 'top',
                  textStyle: {
                    color: 'black',
                    fontSize: 16,
                  },
                },
              },
            },
          },
        ],
      },
      deviceFailureBarOption: {
        title: {
          text: '设备离线数量统计',
          textStyle: {
            fontSize: 16,
          },
          left: 'center',
          // top: 6,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        grid: {
          // left: '3%',
          // right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        // legend: {
        //   top: 14,
        //   right: 10,
        // },
        color: ['rgb(255,100,68)', 'rgb(0,210,178)', 'rgb(255,129,187)', 'rgb(255,204,82)'],
        xAxis: {
          type: 'category',
          data: [],
          axisLabel: {
            rotate: 45,
          },
        },
        yAxis: {
          type: 'value',
          name: '单位（次）',
          nameTextStyle: {
            color: '#aaa',
            nameLocation: 'start',
          },
        },
        series: [
          {
            data: [],
            type: 'bar',
            colorBy: 'data',
            itemStyle: {
              //上方显示数值
              normal: {
                label: {
                  show: true,
                  position: 'top',
                  textStyle: {
                    color: 'black',
                    fontSize: 16,
                  },
                },
              },
            },
          },
        ],
      },
      deviceOnlineRankBarOption: {
        title: {
          text: '设备在线数量排行',
          textStyle: {
            fontSize: 16,
          },
          left: 'center',
          // top: 6,
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        grid: {
          // left: '3%',
          // right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        // legend: {
        //   top: 14,
        //   right: 10,
        // },
        color: ['rgb(255,100,68)', 'rgb(0,210,178)', 'rgb(255,129,187)', 'rgb(255,204,82)'],
        xAxis: {
          type: 'value',
        },
        yAxis: {
          type: 'category',
          data: ['门禁', '摄像机', '烟感', '燃气探测'],
        },
        series: [
          {
            data: [120, 200, 150, 80],
            type: 'bar',
            colorBy: 'data',
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  position: 'right',
                  textStyle: {
                    color: 'black',
                    fontSize: 16,
                  },
                },
              },
            },
          },
        ],
      },
      lineOption: {
        // title: {
        //   text: '维修工单统计',
        //   textStyle: {
        //     fontSize: 16,
        //   },
        //   left: 10,
        //   top: 14,
        // },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow',
          },
        },
        legend: {
          top: 14,
          itemGap: 20,
        },
        grid: {
          // left: '3%',
          // right: '4%',
          bottom: '3%',
          containLabel: true,
        },
        xAxis: {
          type: 'category',
          data: [],
          axisLabel: {
            rotate: 45,
          },
        },
        yAxis: {
          type: 'value',
          // max: 100,
          // axisLabel: {
          //   formatter: '{value}%',
          // },
        },
        series: [
          {
            name: '环境检测',
            type: 'line',
            data: [],
            barWidth: 18,
            // itemStyle: {
            //   normal: {
            //     color: 'rgba(113,17,29)',
            //   },
            // },
          },
          {
            name: '安防系统',
            type: 'line',
            data: [],
            barWidth: 18,
            // itemStyle: {
            //   normal: {
            //     color: 'rgba(157,91,95)',
            //   },
            // },
          },
        ],
      },
      pieOption: {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          // right: '20%',
          // y: 'center',
          // orient: 'vertical',
          // itemGap: 20,
          top: '4%',
          left: 'center',
        },
        series: [
          {
            name: '告警处理情况',
            type: 'pie',
            color: ['rgb(255,100,68)', 'rgb(0,210,178)'],
            center: ['50%', '54%'],
            radius: ['40%', '70%'],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
            data: [],
            itemStyle: {
              normal: {
                label: {
                  show: true,
                  formatter: '{d}%', //只要百分比
                },
                labelLine: { show: true },
              },
            },
          },
        ],
      },
      flowChartLoading: true,
      deviceChartLoading: true,
      alarmChartLoading: true,
      loadingOptions: {
        text: '数据正在加载...',
        textStyle: { fontSize: 30, color: '#333' },
        // color: 'rgba(113,17,29)',
        effectOption: { backgroundColor: 'rgba(0, 0, 0, 0)' },
      },
      deviceStatistic: {
        systemTypeCount: 0,
        deviceTypeCount: 0,
        deviceCount: 0,
        onlineDeviceCount: 0,
        deviceOnlineMap: {
          dissipativeSystem: {
            totalCount: 0,
            onlineCount: 0,
          },
          securityProtection: {
            totalCount: 0,
            onlineCount: 0,
          },
          fireFighting: {
            totalCount: 0,
            onlineCount: 0,
          },
          environmentalMonitoring: {
            totalCount: 0,
            onlineCount: 0,
          },
        },
      },
      alarmStatistic: {
        totalCount: 0,
        processedCount: 0,
        unprocessedCount: 0,
      },
    }
  },
  created() {
    
    this.initCardNumber()
    this.initFlowStatistic()
    this.initDeviceStatisticInfo()
    this.initAlarmStatisticInfo()
    this.initTodayAccessList()
  },
  methods: {
    moment,
    onJumpToSystemList() {
      this.$router.push({
        path: '/equipment/system',
      })
    },
    onJumpToDeviceList(type) {
      this.$router.push({
        name: 'equipment-device-list',
        params: {
          systemType: type,
        },
      })
    },
    initDeviceStatisticInfo() {
      getAction('/device/statistic').then((res) => {
        if (res.success) {
          this.deviceStatistic = res.result
          this.deviceFailureBarOption.xAxis.data = this.deviceStatistic.deviceOfflineVOList.map((device) => device.name)
          this.deviceFailureBarOption.series[0].data = this.deviceStatistic.deviceOfflineVOList.map(
            (device) => device.offlineCount
          )
          this.deviceOnlineRankBarOption.yAxis.data = this.deviceStatistic.deviceOnlineVOList.map(
            (device) => device.name
          )
          this.deviceOnlineRankBarOption.series[0].data = this.deviceStatistic.deviceOnlineVOList.map(
            (device) => device.onlineCount
          )
          this.deviceChartLoading = false
        }
      })
    },
    initAlarmStatisticInfo() {
      getAction('/alarm/statistic').then((res) => {
        if (res.success) {
          this.alarmStatistic = res.result
          this.pieOption.series[0].data = [
            { value: this.alarmStatistic.unprocessedCount, name: '待处理' },
            { value: this.alarmStatistic.processedCount, name: '已处理' },
          ]
          this.lineOption.xAxis.data = this.alarmStatistic.countList.map((alarm) => alarm.datetime)
          this.lineOption.series[0].data = this.alarmStatistic.countList.map((vo) => vo.environment)
          this.lineOption.series[1].data = this.alarmStatistic.countList.map((vo) => vo.alarm)
          this.alarmChartLoading = false
        }
      })
    },
    initFlowStatistic() {
      getAction('/alarm/doorRecord/flow/statistic').then((res) => {
        if (res.success) {
          this.flowStatistic = res.result
          this.flowBarOption.xAxis.data = this.flowStatistic.map((flow) => flow.position)
          this.flowBarOption.series[0].data = this.flowStatistic.map((flow) => flow.total)
          this.flowChartLoading = false
        }
      })
    },
    onJumpToAlarm() {
      this.$router.push({
        path: '/sirens/MyUnifiedAlarmHandling',
      })
    },
    onJumpToDataAlarm() {
      this.$router.push({
        path: '/security/alarm/data/analysis',
      })
    },
    onJumpToImageAlarm() {
      this.$router.push({
        path: '/security/alarm/image/analysis',
      })
    },
    async initTodayAccessList() {
      const startTime = moment(moment().startOf('day').valueOf()).format('YYYY-MM-DD HH:mm:ss')
      const endTime = moment(moment().endOf('day').valueOf()).format('YYYY-MM-DD HH:mm:ss')
      const startTimeY = moment(moment().add(-1, 'days').startOf('day').valueOf()).format('YYYY-MM-DD HH:mm:ss')
      const endTimeY = moment(moment().add(-1, 'days').endOf('day').valueOf()).format('YYYY-MM-DD HH:mm:ss')
      const res = await getAction(`/alarm/doorRecord/list`, {
        isdoor: '1',
        photo: '!no-picuri',
        happenTime_begin: startTime,
        happenTime_end: endTime,
      })
      if (res.success) {
        this.accessList = res.result.records
        this.accessTotalToday = res.result.total
      }
      const yesterday = await getAction(`/alarm/doorRecord/list`, {
        isdoor: '1',
        photo: '!no-picuri',
        happenTime_begin: startTimeY,
        happenTime_end: endTimeY,
      })
      if (yesterday.success) {
        this.accessTotalYesterday = yesterday.result.total
      }
    },
    async initCardNumber() {
      const res = await getAction(`/alarm/doorRecord/list`, {
        isdoor: 'null',
      })
      if (res.success) {
        this.imageTotal = res.result.total
      }
      const data = await getAction(`/alarm/dataAnalysis/getTodayCrowdGatheringAlarmNum`)
      if (data.success) {
        this.dataTotal = data.result
      }
    },
  },
  mounted(){
    setTimeout(() => {
      this.loading = !this.loading
    }, 1000)
  }
}
</script>

<style lang="less" scoped>
.chart {
  width: 100%;
  height: 360px;
}

.device-statistic {
  padding: 0 48px;

  .title-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;

    /deep/ .ant-btn {
      padding: 0 !important;
    }
  }
  .title {
    font-weight: bold;
  }
}
</style>
