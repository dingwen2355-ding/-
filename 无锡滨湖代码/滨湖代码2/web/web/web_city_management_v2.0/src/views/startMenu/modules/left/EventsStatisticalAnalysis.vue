<template>
  <div class="EventSatistical module">
    <div class="moduleHeader" style="cursor: default">事件统计分析</div>
    <div class="content">
      <div class="annualDisasters">
        <span class="content_title">年度灾害统计</span>
        <div id="annualDisastersChart" style="height: 600rem"></div>
      </div>
      <div class="information_2">
        <span class="content_title">重大节庆活动保障统计</span>
        <div id="columnBarChart" style="height: 600rem"></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/request';
import moment from 'moment'
import * as echarts from 'echarts'
export default {
  name: 'EventSatistical',
  data() {
    return {
      annualDisasters: 0,
      dataList: [
        {
          value: 55,
          percent: '55%'
        },
        {
          value: 14,
          percent: '14%'
        },
        {
          value: 12,
          percent: '12%'
        },
        {
          value: 7,
          percent: '5.8%'
        },
        {
          value: 55,
          percent: '55%'
        }
      ],
      colors: ['#3C96F5', '#F3D662', '#EC8A2B', '#16F0F3', '#3C96F5']
    }
  },
  mounted() {
    this.query()
    this.initBarChart('columnBarChart', this.dataList)
  },
  methods: {
    initPieChart(id, data) {
      let chartDom = document.getElementById(id)
      let myChart = echarts.init(chartDom)
      let option

      option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: 'center',
          right: 'right',
          textStyle: {
            color: '#fff',
            rich: {
              title: {
                fontSize: '60rem',
                color: '#fff'
              },
              natrue: {
                fontSize: '60rem',
                color: '#3A77B6'
              },
              event: {
                fontSize: '60rem',
                color: '#F3D662'
              },
              social: {
                fontSize: '60rem',
                color: '#EC8A2B'
              },
              public: {
                fontSize: '60rem',
                color: '#73FBFD'
              }
            }
          },
          itemWidth: 8,
          itemHeight: 8,
          formatter: function (name) {
            const [item] = data.filter((item) => item.name === name)
            if (name === '自然灾害') {
              return `{title|${name}} {natrue|${item.value}} {natrue|${item.percent}%}`
            } else if (name === '事故灾害') {
              return `{title|${name}} {event|${item.value}} {event|${item.percent}%}`
            } else if (name === '社会安全') {
              return `{title|${name}} {social|${item.value}} {social|${item.percent}%}`
            } else {
              return `{title|${name}} {public|${item.value}} {public|${item.percent}%}`
            }
          }
        },
        title: {
          text: `{title|${this.annualDisasters}}`,
          textStyle: {
            color: '#fff',
            // fontSize: 16,
            rich: {
              title: {
                fontSize: '64rem',
                color: '#fff'
              }
            }
          },
          textAlign: 'center',
          subtext: `{subtext|${'总数'}}`,
          subtextStyle: {
            color: '#727D86',
            rich: {
              subtext: {
                fontSize: '60rem',
                color: 'RGBA(255,255,255,0.6)'
              }
            }
          },
          top: 'middle',
          left: '24%'
        },
        color: ['#4A8DD3', '#F3D662', '#EC8A2B', '#73FBFD', 'rgba(60, 150, 245, 0.7)'],
        series: [
          {
            name: '年度灾害统计',
            type: 'pie',
            clockwise: false,
            center: ['25%', '50%'],
            radius: ['70%', '80%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            labelLine: {
              show: false
            },
            data
          }
        ]
      }

      option && myChart.setOption(option)
    },
    initBarChart(id, data) {
      let chartDom = document.getElementById(id)
      let myChart = echarts.init(chartDom)
      let option
      const sourceList = data.reverse()
      let borderRadius = 12
      let colors = ['#3C96F5', '#F3D662', '#EC8A2B', '#16F0F3', '#3C96F5'].reverse()
      let projectList = ['重大节庆', '运动活动', '民族活动', '商业汇演', '其他'].reverse()
      let yAxisLeft = projectList.map((text) => {
        const ydata = {
          value: `{leftText|${text}}`,
          textStyle: {
            rich: {
              leftText: {
                fontSize: '56rem',
                color: '#fff'
              }
            }
          }
        }
        return ydata
      })
      let yAxisData = sourceList.map((item, index) => {
        const color = colors[index]
        const ydata = {
          value: `{rightText|${item.value} ${item.percent}}`,
          textStyle: {
            rich: {
              rightText: {
                fontSize: '66rem',
                color
              }
            }
          }
        }
        return ydata
      })
      let seriesData = sourceList.map((item, index) => {
        const color = colors[index]
        const sdata = {
          value: item.value,
          itemStyle: { color, borderRadius }
        }
        return sdata
      })
      option = {
        grid: {
          top: 5,
          left: '20%',
          right: '20%',
          bottom: 5
        },
        xAxis: {
          type: 'value',
          show: false
        },
        yAxis: [
          {
            type: 'category',
            axisLine: {
              show: false
            },
            axisLabel: {
              interval: 0
            },
            axisTick: {
              show: false
            },
            data: yAxisLeft
          },
          {
            type: 'category',
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              interval: 0
            },
            data: yAxisData
          }
        ],
        series: [
          {
            name: '重大节庆活动保障统计',
            type: 'bar',
            barWidth: '22%',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)',
              borderRadius
            },
            data: seriesData
          }
        ]
      }

      option && myChart.setOption(option)
    },
    query() {
      let url =
        '/community/eventSituation/burstEventParentType?beginTime=' +
        moment().format('YYYY-01-01') +
        '&endTime=' +
        moment().format('YYYY-MM-DD')
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let [{ num }] = res.data.data.filter((item) => item.type === '总计')
          this.annualDisasters = num
          const data = res.data.data
            .filter((item) => item.type !== '总计')
            .map((item, index) => {
              const obj = {}
              obj.value = item.num
              obj.selected = index === 0
              obj.name = item.type
              obj.percent = (item.num / num).toFixed(2)
              return obj
            })
          this.initPieChart('annualDisastersChart', data)
        } else {
          this.$message.error('值班信息查询失败！')
        }
      })
    }
    // queryEventList() {
    //   let url = '/apis/Command/eventInfoList'
    //   axios.get(url).then((res) => {
    //     if (res.data.code === 200) {
    //       this.ToDoTableData = res.data.data
    //     } else {
    //       this.$message.error('事件查询失败！')
    //     }
    //   })
    // }
  }
}
</script>
<style lang="scss" scoped>
.EventSatistical {
  width: 2840rem;
  height: 932rem;
  position: absolute;
  top: 1896rem;
  left: 128rem;
  z-index: 1001;
  .moduleHeader {
    height: 200rem;
    line-height: 200rem;
    background: url('../../../../assets/SmallTitle/module_head@2x.png') no-repeat;
    background-size: 100%;
    text-indent: 196rem;
    font-size: 96rem;
    font-family: 'YouShe';
    font-weight: 500;
    color: #ffffff;
  }
  .content {
    padding-top: 60rem;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    height: 732rem;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;
    .content_title {
      height: 76rem;
      font-size: 64rem;
      font-family: Source Han Sans CN;
      font-weight: 400;
      color: #00f6ff;
      line-height: 76rem;
      background: url('../../../../assets/left/二级标题@2x.png') no-repeat;
      background-size: 108rem 76rem;
      background-position: 76rem center;
      text-indent: 212rem;
      display: block;
    }
    .annualDisasters {
      width: 48%;
      #annualDisastersChart {
        background: url('../../../../assets/global/环形图bg.png') no-repeat;
        background-size: 664rem 563rem;
        background-position: 3% center;
      }
    }
    .information_2 {
      position: relative;
      width: 48%;
    }
  }
}
</style>
