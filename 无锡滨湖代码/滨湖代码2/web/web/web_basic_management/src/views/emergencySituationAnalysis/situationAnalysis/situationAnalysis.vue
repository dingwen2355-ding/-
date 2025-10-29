<template>
  <div class="situationAnalysis">
    <el-form label-width="100px" style="display: flex; justify-content: space-between; align-items: center">
      <div style="display: flex; align-items: center">
        <el-form-item label="日期：" style="margin-bottom: 0px">
          <el-date-picker
            v-model="valueTime"
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item  style="margin-bottom: 0px">
          <el-button icon="el-icon-download" >导出</el-button>
        </el-form-item>
        <!-- <el-form-item label="模板选择：" style="margin-bottom: 0px">
          <el-select v-model="template" placeholder="请选择模板"> </el-select>
        </el-form-item> -->
      </div>
      <!-- <div style="width: 100px; display: flex">
        <el-link icon="el-icon-upload2">导出</el-link>
      </div> -->
    </el-form>
    <div class="container-02">
      <div class="left-con container-child" style="width: 40%; position: relative">
        <div class="con-title"><span class="title-back"></span>突发事件总体统计</div>
        <div
          style="
            position: absolute;
            top: 10px;
            width: 30%;
            right: 68px;
            font-size: 15px;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #7f8892;
            line-height: 30px;
          "
        >
          对比条件：
        </div>
        <el-select
          v-model="valueYear"
          placeholder="请选择"
          style="position: absolute; top: 10px; width: 30%; right: 0"
          clearable
        >
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
        <div class="con-main">
          <div id="chartTotal"></div>
        </div>
      </div>
      <div class="left-con container-child" style="width: 60%">
        <div class="con-title" style="width: 100%; position: relative">
          <span class="title-back"></span>突发事件分类统计<el-button
            v-if="!showEvent"
            @click="showEvent = !showEvent"
            style="position: absolute; top: 10px; right: 0"
            >返回</el-button
          >
        </div>
        <div class="con-main" style="display: flex; flex-wrap: wrap; padding-top: 10px" v-show="showEvent">
          <div
            v-for="(item, index) in classicList"
            :style="{
              flexShrink: item[Object.keys(item)[0]].length === 0 ? 1 : 0,
              flexGrow: item[Object.keys(item)[0]].length === 0 ? 0 : 1
            }"
            :key="index"
            class="chartTotal1"
          ></div>
        </div>
        <div class="con-main" v-show="!showEvent">
          <div id="chartTotal2"></div>
        </div>
      </div>
    </div>
    <div class="container-02">
      <div class="left-con container-child">
        <div class="con-title"><span class="title-back"></span>突发事件季度分布统计</div>
        <div class="con-main" style="padding-top: 10px">
          <div id="chartTotal3"></div>
        </div>
      </div>
      <div class="left-con container-child">
        <div class="con-title"><span class="title-back"></span>突发事件级别统计</div>
        <div class="con-main" style="padding-top: 10px">
          <div id="chartTotal4"></div>
        </div>
      </div>
      <div class="left-con container-child">
        <div class="con-title" style="cursor: pointer"><span class="title-back"></span>突发事件辖区分布</div>
        <div class="con-main" style="padding-top: 10px">
          <div id="chartTotal5"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts'
import request from '@/utils/request'
import moment from 'moment'
export default {
  name: 'situationAnalysis',
  components: {},
  data() {
    return {
      showMap: false,
      valueYear: '2',
      template: '',
      valueTime: [new Date(new Date().getFullYear() + '-01-01 00:00:00'), new Date()],
      disList: [
        {
          hurtCount: '',
          deathCount: '',
          percent: 0,
          num: 0
        }
      ],
      options: [
        {
          label: '环比',
          value: '2'
        },
        {
          label: '同比',
          value: '1'
        }
      ],
      showEvent: true,
      chartOneOption: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        title: {
          text: '突发事件分类统计图',
          left: 'center',
          textStyle: {
            color: '#7F8892',
            fontFamily: 'Microsoft YaHei',
            fontSize: 16,
            fontWeight: 400
          }
        },
        grid: {
          // 让图表占满容器
          top: '15%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisPointer: {
              type: 'shadow'
            },
            axisTick: false,
            axisLine: {
              lineStyle: {
                color: '#9FABCC',
                opacity: '0.3'
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '人',
            min: 0,
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed'
              }
            }
          },
          {
            type: 'value',
            name: '%',
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed'
              }
            }
          }
        ],
        series: [
          {
            name: '总人数',
            type: 'bar',
            barWidth: '15px',
            data: [],
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(1, 1, 1, 0, [
                  { offset: 0, color: '#3D87E0' },
                  { offset: 1, color: '#5BCEFF' }
                ]),
                label: {
                  show: true, // 开启显示
                  position: 'top', // 在上方显示
                  textStyle: {
                    // 数值样式
                    color: '#9FABCC',
                    fontSize: 12
                  }
                }
              }
            }
          },
          {
            name: '死亡人数',
            type: 'bar',
            barWidth: '15px',
            data: [],
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(1, 1, 1, 0, [
                  { offset: 0, color: '#52D1D1' },
                  { offset: 1, color: '#52D1D1' }
                ]),
                label: {
                  show: true, // 开启显示
                  position: 'top', // 在上方显示
                  textStyle: {
                    // 数值样式
                    color: '#9FABCC',
                    fontSize: 12
                  }
                }
              }
            }
          },
          {
            name: '受伤人数',
            type: 'bar',
            barWidth: '15px',
            data: [],
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(1, 1, 1, 0, [
                  { offset: 0, color: '#41A24F' },
                  { offset: 1, color: '#66EE86 ' }
                ]),
                label: {
                  show: true, // 开启显示
                  position: 'top', // 在上方显示
                  textStyle: {
                    // 数值样式
                    color: '#9FABCC',
                    fontSize: 12
                  }
                }
              }
            }
          },
          {
            name: '总占比率',
            type: 'line',
            yAxisIndex: 1,
            data: [],
            itemStyle: {
              normal: {
                color: '#FFCA38'
              }
            }
          },
          {
            name: '死亡占比率',
            type: 'line',
            yAxisIndex: 1,
            data: [],
            itemStyle: {
              normal: {
                color: '#B573F4'
              }
            }
          },
          {
            name: '受伤占比率',
            type: 'line',
            yAxisIndex: 1,
            data: [],
            itemStyle: {
              normal: {
                color: '3C90EB'
              }
            }
          }
        ]
      },
      chartOneOption1: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        legend: {
          data: ['当前数据', '同/环比数据'],
          orient: 'horizontal',
          top: 6,
          textStyle: {
            color: '#7F8892',
            fontFamily: 'Microsoft YaHei',
            fontSize: 10,
            fontWeight: 400
          }
        },
        grid: {
          // 让图表占满容器
          top: '15%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisPointer: {
              type: 'shadow'
            },
            axisTick: false,
            axisLine: {
              lineStyle: {
                color: '#9FABCC',
                opacity: '0.3'
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '人',
            min: 0,
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed'
              }
            }
          },
          {
            type: 'value',
            name: '%',
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed'
              }
            }
          }
        ],
        series: [
          {
            name: '当前数据',
            type: 'bar',
            barWidth: '40px',
            data: [],
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(1, 1, 1, 0, [
                  { offset: 0, color: '#3D87E0' },
                  { offset: 1, color: '#5BCEFF' }
                ]),
                label: {
                  show: true, // 开启显示
                  position: 'top', // 在上方显示
                  textStyle: {
                    // 数值样式
                    color: '#5BCEFF',
                    fontSize: 20
                  }
                }
              }
            }
          },
          {
            name: '同/环比数据',
            type: 'line',
            yAxisIndex: 1,
            data: [],
            itemStyle: {
              normal: {
                color: '#FFCA38'
              }
            }
          }
        ]
      },
      chartOneOption2: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        title: {
          text: '突发事件季度分布统计图',
          left: 'center',
          textStyle: {
            color: '#7F8892',
            fontFamily: 'Microsoft YaHei',
            fontSize: 16,
            fontWeight: 400
          }
        },
        grid: {
          // 让图表占满容器
          top: '15%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisPointer: {
              type: 'shadow'
            },
            axisTick: false,
            axisLine: {
              lineStyle: {
                color: '#9FABCC',
                opacity: '0.3'
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '',
            min: 0,
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed'
              }
            }
          }
        ],
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: '44px',
            data: [],
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(1, 1, 1, 0, [
                  { offset: 0, color: '#3D87E0' },
                  { offset: 1, color: '#5BCEFF' }
                ]),
                label: {
                  show: true, // 开启显示
                  position: 'top', // 在上方显示
                  textStyle: {
                    // 数值样式
                    color: '#9FABCC',
                    fontSize: 20
                  }
                }
              }
            }
          }
        ]
      },
      chartOneOption3: {
        title: {
          text: '突发事件级别统计图',
          left: 'center',
          textStyle: {
            color: '#7F8892',
            fontFamily: 'Microsoft YaHei',
            fontSize: 16,
            fontWeight: 400
          }
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          // orient: 'vertical',
          top: '75%',
          left: 'right',
          icon: 'circle',
          textStyle: {
            color: '#7F8892',
            fontFamily: 'Microsoft YaHei',
            fontSize: 10,
            fontWeight: 400
          }
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [],
            label: {
              normal: {
                show: true,
                formatter: '{title|{b}}\n\n{name|{d}%}',
                rich: {
                  title: {
                    color: '#9FABCC',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: 16,
                    fontWeight: 400
                  },
                  name: {
                    color: '#9FABCC',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: 18,
                    fontWeight: 400
                  }
                }
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      chartOneOption4: {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        title: {
          text: '突发事件辖区分布图',
          left: 'center',
          textStyle: {
            color: '#7F8892',
            fontFamily: 'Microsoft YaHei',
            fontSize: 16,
            fontWeight: 400
          }
        },
        grid: {
          // 让图表占满容器
          top: '15%',
          left: '5%',
          right: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: [],
            axisPointer: {
              type: 'shadow'
            },
            axisTick: false,
            axisLine: {
              lineStyle: {
                color: '#9FABCC',
                opacity: '0.3'
              }
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '',
            min: 0,
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: true,
              lineStyle: {
                type: 'dashed'
              }
            }
          }
        ],
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: '44px',
            data: [],
            label: {
              show: true,
              position: 'top',
              color: '#9FABCC',
              fontFamily: 'DIN Alternate',
              fontSize: 20,
              fontWeight: 'bold'
            },
            itemStyle: {
              normal: {
                color: function (params) {
                  let colorList = [
                    ['#9738E8', '#CC9BFF'],
                    ['#3D87E0', '#5BCEFF'],
                    ['#20BDA9', '#6AEFDD'],
                    ['#41A24F', '#66EE86'],
                    ['#D8B40B', '#FFE27D'],
                    ['#D67900', '#FFBC8F'],
                    ['#E43179', '#FF8CBA'],
                    ['#BCBCBC', '#E8E8E8']
                  ]
                  return new echarts.graphic.LinearGradient(
                    1,
                    1,
                    1,
                    0, // 渐变色在下面修改，这里是透明度
                    [
                      {
                        offset: 0,
                        color: colorList[params.dataIndex][0]
                      },
                      {
                        offset: 1,
                        color: colorList[params.dataIndex][1]
                      }
                    ]
                  )
                }
              }
            }
          }
        ]
      },
      classicList: [],
      eventNew: [],
      tableData: [],
      tableDataOutInput: [],
      sortList: [],
      option: {
        color: ['#157ef5'], // 设置柱状图的颜色
        textStyle: {
          color: '#828282'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '0%',
          top: '5%',
          containLabel: true
        },
        xAxis: {
          show: false
        },
        yAxis: {
          type: 'category',
          data: [],
          axisTick: { show: false },
          axisLine: {
            lineStyle: {
              color: 'transparent'
            }
          },
          inside: true,
          textStyle: {
            color: '#000'
          }
        },
        series: [
          {
            type: 'bar',
            itemStyle: {
              color: '#f1f1f1', // 定义柱形的背景色
              borderRadius: [10, 10, 10, 10] // 定义背景柱形的圆角
            },
            barGap: '-100%',
            data: [],
            animation: false, // 关闭动画效果
            barWidth: '10px' // 设置柱形宽度
          },
          {
            type: 'bar',
            data: [],
            barWidth: '10px',
            barGap: '-100%', // 设置柱形重合的重要步骤
            itemStyle: {
              borderRadius: [10, 10, 10, 10], // 定义柱形的圆角
              color: function (params) {
                var colorList = ['#3C90EB', '#B573F4', '#F9B341', '#19DB6A', '#52D1D1', '#4EAAFF', '#BC65FF', '#88ABD1']
                return colorList[params.dataIndex]
              }
            }
          }
        ]
      },
      colorList: ['#F95D74', '#FFCA38', '#19DB6A', '#52D1D1', '#4EAAFF', '#BC65FF', '#88ABD1'],
      chartOne: null,
      chartTwo: null,
      chartBox: null,
      value1: '',
      input: ''
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.queryDataTable()
      this.loadChartOne(this.valueYear)
      this.queryCityData()
      this.loadChartTwo()
      this.getChartData()
    })

    window.onresize = () => {
      this.chartOne.resize()
      this.chartTwo.resize()
      this.getChartData()
    }
  },
  watch: {
    valueYear: {
      handler(v) {
        this.showEvent = true
        this.loadChartOne(v)
        this.queryDataTable()
      }
    },
    valueTime: {
      handler(v) {
        this.valueYear = '2'
        this.showEvent = true
        this.loadChartOne(this.valueYear)
        this.queryDataTable()
        this.loadChartTwo()
        this.queryCityData()
      }
    }
  },
  methods: {
    // 突发事件分类统计
    queryDataTable() {
      let url = '/binhuapis/eventSituation/burstEventTypeCountAll'
      if (this.valueTime && this.valueTime.length > 0) {
        url +=
          '?beginTime=' +
          moment(this.valueTime[0]).format('yyyy-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(this.valueTime[1]).format('yyyy-MM-DD HH:mm:ss')
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          this.classicList = data
          this.$nextTick(() => {
            let arr = []
            this.classicList.forEach((item, index) => {
              let obj = {}
              for (let i in item) {
                obj.title = i
                let sum = 0
                item[i].map((items) => {
                  sum += items.totalCount
                })
                obj.sum = sum
                obj.data = item[i].map((r) => {
                  return {
                    name: r.typeName,
                    value: r.totalCount
                  }
                })
              }
              arr.push(obj)
            })
            let chartDom = document.getElementsByClassName('chartTotal1')
            for (let i = 0; i < chartDom.length; i++) {
              let myChart = echarts.init(chartDom[i])
              myChart.setOption({
                tooltip: {
                  trigger: 'item'
                },
                title: {
                  text: arr[i].title,
                  left: 'center',
                  textStyle: {
                    color: '#7F8892',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: 16,
                    fontWeight: 400
                  }
                },
                legend: {
                  orient: 'vertical',
                  top: '15%',
                  left: 'right',
                  formatter: function (name) {
                    return name.length > 5 ? name.substr(0, 5) + '...' : name
                  },
                  textStyle: {
                    color: '#9FABCC',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: 10,
                    fontWeight: 400
                  }
                },
                series: [
                  {
                    type: 'pie',
                    radius: ['40%', '70%'],
                    center: ['35%', '50%'],
                    labelLine: {
                      show: false
                    },
                    label: {
                      show: false,
                      position: 'center',
                      normal: {
                        show: true,
                        position: 'center',
                        formatter: '{total|' + arr[i].sum + '}',
                        rich: {
                          total: {
                            fontSize: 20,
                            fontFamily: '微软雅黑',
                            color: '#5BCEFF'
                          }
                        }
                      },
                      emphasis: {
                        // 中间文字显示
                        show: true
                      }
                    },
                    data: arr[i].data
                  }
                ]
              })
            }
          })
          // this.$nextTick(() => {
          //   let chartDom = document.getElementById('myChart1')
          //   this.chartOne = echarts.init(chartDom)
          //   this.chartOne.setOption(this.option)
          // })
          // this.eventOption
        }
      })
    },
    // 突发事件季度分布统计
    getChartData() {
      let url = '/binhuapis/eventSituation/quartAnalysis'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          this.chartOneOption2.xAxis[0].data = data.map((item) => item.q + '季度')
          this.chartOneOption2.series[0].data = data.map((item) => item.qCount)
          this.$nextTick(() => {
            let chartDom = document.getElementById('chartTotal3')
            let myCHrat = echarts.init(chartDom)
            myCHrat.setOption(this.chartOneOption2)
          })
        }
      })
    },
    // 突发事件辖区分布
    queryCityData() {
      let url = '/binhuapis/eventSituation/eventStreetCount'
      if (this.valueTime && this.valueTime.length > 0) {
        url +=
          '?beginTime=' +
          moment(this.valueTime[0]).format('yyyy-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(this.valueTime[1]).format('yyyy-MM-DD HH:mm:ss')
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          this.chartOneOption4.xAxis[0].data = data.map((item) => item.streetName)
          this.chartOneOption4.series[0].data = data.map((item) => item.streetCount)
          this.$nextTick(() => {
            let chartDom = document.getElementById('chartTotal5')
            let myCHrat = echarts.init(chartDom)
            myCHrat.setOption(this.chartOneOption4)
          })
        }
      })
    },
    // 突发事件总体统计
    loadChartOne(v) {
      let url = '/binhuapis/eventSituation/burstEventTotalCount'
      if (v) {
        let arr = [new Date(new Date().getFullYear() + '-01-01 00:00:00'), new Date()]
        url +=
          '?type=' +
          v +
          '&beginTime=' +
          moment(arr[0]).format('yyyy-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(arr[1]).format('yyyy-MM-DD HH:mm:ss')
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          this.chartOneOption.xAxis[0].data = data.map((item) => item.typeName)
          this.chartOneOption.series[0].data = data.map((item) => item.totalCount)
          this.chartOneOption.series[1].data = data.map((item) => item.deathCount)
          this.chartOneOption.series[2].data = data.map((item) => item.hurtCount)
          this.chartOneOption.series[3].data = data.map((item) => item.totalCountRing.split('%')[0])
          this.chartOneOption.series[4].data = data.map((item) => item.deathCountRing.split('%')[0])
          this.chartOneOption.series[5].data = data.map((item) => item.hurtCountRing.split('%')[0])
          this.$nextTick(() => {
            if (this.chartOne != null && this.chartOne !== '' && this.chartOne !== undefined) {
              this.chartOne.dispose()
            }
            let chartDom = document.getElementById('chartTotal')
            this.chartOne = echarts.init(chartDom)
            this.chartOne.setOption(this.chartOneOption)
            this.chartOne.getZr().on('click', (param) => {
              console.log(param)
              const pointInPixel = [param.offsetX, param.offsetY]
              let pointInGrid = this.chartOne.convertFromPixel({ seriesIndex: 0 }, pointInPixel)
              let xIndex = pointInGrid[0]
              let op = this.chartOne.getOption()
              let time = op.xAxis[0].data[xIndex]
              this.showOtherList(time, v)
            })
          })
        }
      })
    },
    showOtherList(v, r) {
      this.showEvent = false
      let url = '/binhuapis/eventSituation/burstEventTypeCount?type=' + v
      if (r) {
        let arr = [new Date(new Date().getFullYear() + '-01-01 00:00:00'), new Date()]
        url +=
          '&beginTime=' +
          moment(arr[0]).format('yyyy-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(arr[1]).format('yyyy-MM-DD HH:mm:ss')
      } else {
        if (this.valueTime && this.valueTime.length > 0) {
          url +=
            '&beginTime=' +
            moment(this.valueTime[0]).format('yyyy-MM-DD HH:mm:ss') +
            '&endTime=' +
            moment(this.valueTime[1]).format('yyyy-MM-DD HH:mm:ss')
        }
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          this.chartOneOption1.xAxis[0].data = data.map((item) => item.typeName)
          this.chartOneOption1.series[0].data = data.map((item) => item.totalCount)
          this.chartOneOption1.series[1].data = data.map((item) => item.totalCountRing.split('%')[0])
          this.$nextTick(() => {
            if (this.chartBox != null && this.chartBox !== '' && this.chartBox !== undefined) {
              this.chartBox.dispose()
            }
            let chartDom = document.getElementById('chartTotal2')
            this.chartBox = echarts.init(chartDom)
            this.chartBox.setOption(this.chartOneOption1)
          })
        }
      })
    },
    // 突发事件级别统计
    loadChartTwo() {
      let url = '/binhuapis/eventSituation/eventLevelCount'
      if (this.valueTime && this.valueTime.length > 0) {
        url +=
          '?beginTime=' +
          moment(this.valueTime[0]).format('yyyy-MM-DD HH:mm:ss') +
          '&endTime=' +
          moment(this.valueTime[1]).format('yyyy-MM-DD HH:mm:ss')
      }
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list || []
          this.chartOneOption3.series[0].data = data.map((item) => {
            return {
              value: item.levelCount,
              name: item.levelName
            }
          })
          this.$nextTick(() => {
            let chartDom = document.getElementById('chartTotal4')
            let chartTwo = echarts.init(chartDom)
            chartTwo.setOption(this.chartOneOption3)
          })
        }
      })
    },
    // 最新事件信息/事件热力图分布
    queryNewTable() {
      let url = '/binhuapis/leadKanBan/eventList'
      request.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data
          this.eventNew = data.list || []
        }
      })
    },
    sortNumArr(num) {
      if (!num) {
        return []
      }
      if (Object.prototype.toString.call(num) !== '[object String]') {
        num = num + ''
      }
      let strArr = []
      for (let i of num) {
        strArr.push(i)
      }
      return strArr
    }
  }
}
</script>
<style lang="scss" scoped>
.situationAnalysis {
  background: #f7f8fb;
  width: 100%;
  // height: 100%;
  ::v-deep .el-date-editor .el-range__icon,
  ::v-deep .el-date-editor .el-range-separator,
  ::v-deep .el-date-editor .el-range__close-icon,
  ::v-deep .el-select .el-input .el-select__caret {
    line-height: 32px;
  }
  .container-01 {
    // height: 130px;
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;
    .disp-item {
      .item-title {
        height: 43px;
        font-size: 18px;
        font-family: PingFang SC;
        font-weight: 500;
        color: #7f8892;
        display: flex;
        align-items: center;
        justify-content: center;
      }
      .item-dis-num {
        height: 65px;
        .dis-num {
          display: inline-block;
          width: 41px;
          height: 63px;
          margin-right: 6px;
        }
      }
    }
  }
  .container-02 {
    height: 68%;
    display: flex;
    justify-content: space-between;
    flex-wrap: nowrap;
    .left-con {
      width: 50%;
      margin-right: 7px;
      .con-main {
        width: 100%;
        height: 410px;
        background: #ffffff;
        border: 1px solid #e9edf7;
        border-radius: 8px;
        .conTain {
          list-style: none;
          padding-left: 0;
          margin-top: 10px;
          height: 390px;
          overflow: auto;
          li {
            display: flex;
            padding-left: 10px;
            img {
              width: 40px;
              height: 32px;
              margin-top: 23px;
              margin-right: 15px;
            }
            .eventDiv {
              display: flex;
              font-size: 14px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #9fabcc;
              span {
                color: #7f8892;
              }
            }
          }
          li:nth-of-type(2n-1) {
            background: #f1f6ff;
          }
        }
        #myChart1 {
          width: 100%;
          height: 100%;
        }
        .chartTotal1 {
          width: 32%;
          height: 50%;
        }
        #chartOne,
        #chartTotal,
        #chartTotal1,
        #chartTotal2,
        #chartTotal3,
        #chartTotal4,
        #chartTotal5 {
          width: 100%;
          height: 100%;
        }
        #chartTwo {
          width: 100%;
          height: 100%;
        }
        .container-center {
          display: flex;
          margin-left: 10px;
          position: relative;
          .circle1 {
            width: 111px;
            height: 111px;
            border: 1px solid #d5dbec;
            border-radius: 50%;
            z-index: 2;
            .circle2 {
              width: 93px;
              height: 92px;
              border: 6px solid #4ecaff;
              border-radius: 50%;
              margin: 8px auto;
              p:first-of-type {
                font-size: 40px;
                font-family: DIN;
                font-weight: bold;
                color: #20bcff;
                margin-top: 0px;
                margin-left: 10px;
                margin-bottom: -18px;
                span {
                  font-size: 16px;
                  font-family: PingFang SC;
                  font-weight: 500;
                  color: #20bcff;
                }
              }
              p:last-of-type {
                font-size: 14px;
                font-family: Microsoft YaHei;
                font-weight: 400;
                color: #9fabcc;
                margin-left: 12px;
              }
            }
          }
          .con-right {
            width: 90%;
            height: 127px;
            border-image: linear-gradient(0deg, #edf2ff, #d5dbec) 10 10;
            background: linear-gradient(90deg, #ffffff 0%, #ecf2f6 100%);
            border-radius: 0px 64px 64px 0px;
            position: absolute;
            top: -9px;
            left: 35px;
            .deathNum {
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #7f8892;
              margin-left: 85px;
              margin-top: 10px;
              span {
                margin-left: 15%;
                font-weight: bold;
                color: #fea100;
              }
            }
            .deathNum1 {
              font-size: 16px;
              font-family: Microsoft YaHei;
              font-weight: 400;
              color: #7f8892;
              margin-left: 85px;
              margin-top: 10px;
              span {
                margin-left: 15%;
                font-weight: bold;
                color: #19dd6b;
              }
            }
            ::v-deep .el-progress {
              margin-left: 80px;
            }
            ::v-deep .el-progress .el-progress__text {
              display: none;
            }
          }
          .con-main1 {
            position: absolute;
            width: 97%;
            top: 130px;
            ::v-deep .el-table__body-wrapper {
              overflow-x: hidden;
              height: 98px;
              overflow-y: auto;
            }
            ::v-deep .el-table .el-table__cell {
              padding: 4px 0;
            }
          }
        }
      }
    }
    .right-con {
      position: relative;
      width: calc(50% - 57px);
      height: 100%;
      .con-main {
        height: 100%;
        #chartTwo {
          width: 100%;
          height: 100%;
        }
      }
      .con-absolute {
        position: absolute;
        bottom: 20px;
        right: 100px;
        .c-s-label {
          font-size: 17px;
          font-family: Microsoft YaHei UI;
          font-weight: 400;
          color: #7f8892;
          line-height: 25px;
          .c-s-unit {
            font-size: 21px;
            font-family: Microsoft YaHei UI;
            font-weight: 400;
            color: rgba(30, 138, 252, 1);
            line-height: 25px;
          }
        }
      }
    }
  }
  .container-03 {
    margin-top: 27px;
    height: 31%;
    width: 100%;
    display: flex;
    flex-wrap: nowrap;
    .left-con {
      width: calc(80% - 46px);
      margin-right: 46px;
      height: 100%;
      .con-main {
        width: 100%;
        height: calc(100% - 51px);
        #chartOne {
          width: 100%;
          //height: 274px;
          height: 100%;
        }
      }
    }
    .right-con {
      width: 20%;
      height: 100%;
      .con-main {
        height: calc(100% - 81px);
        ul {
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          height: 100%;
          li {
            display: flex;
            flex-direction: row;
            flex-wrap: nowrap;
            align-items: center;
            justify-content: space-between;
            .circle-num {
              width: 24px;
              height: 24px;
              border-radius: 50%;
              display: inline-block;
              display: flex;
              align-items: center;
              justify-content: center;

              font-size: 17px;
              font-family: DIN;
              font-weight: bold;
              color: #ffffff;
              line-height: 32px;
            }
            .c-title {
              font-size: 17px;
              font-family: Microsoft YaHei UI;
              font-weight: 400;
              color: #7f8892;
              line-height: 32px;
              margin-left: 29px;
            }
            .c-num {
              font-size: 20px;
              font-family: Microsoft YaHei UI;
              font-weight: bold;
              color: #7f8892;
              line-height: 32px;
              margin-left: 29px;
            }
            .c-unit {
              font-size: 17px;
              font-family: Microsoft YaHei UI;
              font-weight: 400;
              color: #7f8892;
              line-height: 32px;
              margin-left: 3px;
            }
          }
        }
      }
    }
  }
  .container-04 {
    height: 24%;
    .con-main {
      height: calc(100% - 46px);
      overflow: scroll;
    }
  }
  .container-child {
    font-size: 22px;
    font-family: Microsoft YaHei;
    font-weight: bold;
    color: #7f8892;
    .top-menu {
      display: flex;
      justify-content: space-between;
      .con-menu {
        display: flex;
        align-items: center;
        ::v-deep .el-button {
          border: 1px solid #e9edf7;
          border-radius: 8px;
          height: 42px;
        }
        ::v-deep .el-button.litter-button {
          width: 40px;
        }
        ::v-deep .con-input {
          width: 189px;
          .el-input__inner {
            width: 189px;
            height: 42px;
            background: #ffffff;
            border: 1px solid #e9edf7;
            border-radius: 8px;
          }
        }
        ::v-deep .con-date.el-date-editor {
          height: 42px;
          background: #ffffff;
          border: 1px solid #e9edf7;
          border-radius: 8px;
          width: 251px !important;
        }
      }
    }
    .con-title {
      height: 51px;
      font-size: 18px;
      display: flex;
      flex-wrap: nowrap;
      align-items: center;
      .title-back {
        width: 5px;
        height: 20px;
        background: #1e8afc;
        display: inline-block;
        margin-right: 10px;
      }
    }
  }
}
</style>
