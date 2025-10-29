<template>
  <div class="page-header-index-wide">

    <a-row>
      <a-col :span="24">
        <a-card
          :loading="loading"
          :body-style="{ padding: '0 0 20px 0' }"
          :bordered="false"
          title="人口统计"
          :style="{ marginTop: '14px' }"
        >
          <a-row>
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
import { getAction } from '@/api/manage'
import moment from 'moment'

export default {
  name: 'PeopleStatistic',
  components: {
    ChartCard,
  },
  data() {
    return {
      loading: true,
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
      alarmChartLoading: true,
      loadingOptions: {
        text: '数据正在加载...',
        textStyle: { fontSize: 30, color: '#333' },
        // color: 'rgba(113,17,29)',
        effectOption: { backgroundColor: 'rgba(0, 0, 0, 0)' },
      },
    }
  },
  created() {
    setTimeout(() => {
      this.loading = !this.loading
    }, 1000)
    this.initAlarmStatisticInfo()
  },
  methods: {
    moment,
    initAlarmStatisticInfo() {
      getAction('/alarm/statistic').then((res) => {
        if (res.success) {
          this.alarmStatistic = res.result
          this.pieOption.series[0].data = [
            { value: this.alarmStatistic.unprocessedCount, name: '待处理' },
            { value: this.alarmStatistic.processedCount, name: '已处理' },
          ]
          this.alarmChartLoading = false
        }
      })
    },
  },
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
