<template>
  <div class="statisticalAnalysis">
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>事件监管</el-breadcrumb-item>
      <el-breadcrumb-item>统计分析</el-breadcrumb-item>
    </el-breadcrumb>
    <!--    <el-select v-model="list[0]" class="position" placeholder="请选择">-->
    <!--      <el-option-->
    <!--        v-for="item in selectList"-->
    <!--        :key="item.value"-->
    <!--        :label="item.label"-->
    <!--        :value="item.value">-->
    <!--      </el-option>-->
    <!--    </el-select>-->
    <el-card class="statisticalSearch">
      <el-form :inline="true" :model="searchForm" ref="form" label-width="100px">
        <el-form-item label="查询时间">
          <el-date-picker
            v-model="searchForm.time"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
            :default-time="['00:00:00', '23:59:59']"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="init" style="flot: right">查询</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <div class="all_data">
      <el-card class="statisticalBody" style="height: calc(100% - 70px)">
        <span class="btn_list">
          <div
            class="item"
            :class="isActived === item.id ? 'isActived' : ''"
            v-for="item in btnList"
            :key="item.id"
            @click="handleDetail(item)"
          >{{ item.name }}</div>
        </span>
        <div id="incidentHandling"></div>
        <div id="regionalSources"></div>
        <div id="reportingSource"></div>
        <div id="lastWhat"></div>
      </el-card>
      <el-card class="statisticalTable">
        <el-form ref="form" :inline="true" :model="search">
          <el-form-item label="姓名" style="margin-bottom: 0">
            <el-input placeholder="请输入姓名" clearable v-model="search.name"></el-input>
          </el-form-item>
          <el-form-item label="所属社区/网格" style="margin-bottom: 0">
            <el-cascader
              :options="gridList"
              clearable
              :props="dealDeptProps"
              v-model="search.precinctId"
              placeholder="请选择所属社区/网格"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="时间" style="margin-bottom: 0">
            <el-date-picker
              v-model="search.time"
              type="datetimerange"
              value-format="yyyy-MM-dd HH:mm:ss"
              range-separator="-"
              :default-time="['00:00:00', '23:59:59']"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item style="margin-bottom: 0">
            <el-button type="primary" @click="querySerch()">查询</el-button>
          </el-form-item>
        </el-form>
        <div class="table_list">
          <div class="table">
            <el-table :data="logData" highlight-current-row v-loading="listLoading" style="width: 100%" height="700px">
              <el-table-column type="index" label="序号" :index="table_index" width="65"></el-table-column>
              <el-table-column prop="name" label="姓名" show-overflow-tooltip></el-table-column>
              <el-table-column prop="communityName" label="所属社区" show-overflow-tooltip></el-table-column>
              <el-table-column prop="gridName" label="所属网格" width="180" show-overflow-tooltip></el-table-column>
              <el-table-column prop="timePeriod" label="所属时段" show-overflow-tooltip></el-table-column>
              <el-table-column prop="time" label="巡查时间" width="180" show-overflow-tooltip></el-table-column>
              <el-table-column prop="shangbao" label="上报" show-overflow-tooltip></el-table-column>
              <el-table-column prop="daiban" label="待办" show-overflow-tooltip></el-table-column>
              <el-table-column prop="chaoqi" label="超期未办" show-overflow-tooltip></el-table-column>
              <el-table-column prop="yichuhzi" label="已处置" show-overflow-tooltip></el-table-column>
              <el-table-column prop="baofei" label="报废" show-overflow-tooltip></el-table-column>
              <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                  <el-button type="text" size="medium" @click="handlEdit(scope.row)">查看轨迹</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <el-col :span="24" class="toolbar">
            <div class="block">
              <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="total"
              ></el-pagination>
            </div>
          </el-col>
        </div>
      </el-card>
    </div>
    <!-- <MapView /> -->
  </div>
</template>

<script>
// import MapView from './MapView.vue'
import axios from '@/utils/request'
import qs from 'qs'
export default {
  name: 'statisticalAnalysis',
  // components: { MapView },
  data() {
    return {
      isActived: 0,
      btnList: [
        {
          name: '社区',
          id: 0
        },
        {
          name: '科室',
          id: 1
        }
      ],
      searchForm: {
        time: []
      },
      selectList: [
        {
          label: '自登记',
          value: 0
        },
        {
          label: '网格事件',
          value: 1
        },
        {
          label: '12345事件',
          value: 2
        },
        {
          label: '智慧城管',
          value: 3
        },
        {
          label: '党群e家',
          value: 4
        },
        {
          label: '百姓上报事件',
          value: 5
        },
        {
          label: 'AI智能发现',
          value: 6
        }
      ],
      list: [6],
      disposalAnalysis: [],
      regionalSourceAnalysis: [],
      reportSourceAnalysis: [],
      option: {
        title: {
          text: '事件处置分析',
          left: 'center',
          top: '3%'
        },
        tooltip: {
          trigger: 'item',
          extraCssText: 'width:auto;height:auto;background-color:rgba(0,0,0,0.4);color:#fff',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          itemHeight: 10,
          itemWidth: 10,
          itemGap: 5,
          type: 'scroll',
          orient: 'vertical',
          right: '20%',
          top: 'center'
        },
        series: [
          {
            name: '事件处置分析',
            type: 'pie',
            radius: [25, 120],
            center: ['30%', '50%'],
            roseType: 'radius',
            itemStyle: {
              borderRadius: 5
            },
            label: {
              show: false
            },
            data: []
          }
        ]
      },
      option1: {
        xAxis: {
          type: 'category',
          data: [],
          splitLine: {
            show: false
          },
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            interval: 0
          }
        },
        grid: {
          left: '10%',
          right: '8%',
          top: '15%',
          bottom: '10%'
        },
        yAxis: {
          type: 'value'
        },
        title: {
          text: '区域来源分析',
          left: 'center',
          top: '3%'
        },
        tooltip: {
          trigger: 'item',
          extraCssText: 'width:auto;height:auto;background-color:rgba(0,0,0,0.4);color:#fff',
          formatter: '{a} <br/>{b} : {c}'
        },
        series: [
          {
            name: '区域来源分析',
            data: [],
            type: 'bar',
            barWidth: 40,
            itemStyle: {
              normal: {
                color: function (params) {
                  // build a color map as your need.
                  let colorList = [
                    '#B5C334',
                    '#FCCE10',
                    '#E87C25',
                    '#27727B',
                    '#FE8463',
                    '#9BCA63',
                    '#FAD860',
                    '#F3A43B',
                    '#60C0DD',
                    '#D7504B',
                    '#C6E579',
                    '#F4E001',
                    '#F0805A',
                    '#26C0C0'
                  ]
                  return colorList[params.dataIndex]
                }
              }
            }
          }
        ]
      },
      option2: {
        title: {
          text: '上报来源统计分析',
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          extraCssText: 'width:auto;height:auto;background-color:rgba(0,0,0,0.4);color:#fff'
        },
        legend: {
          right: '20%',
          top: 'center',
          orient: 'vertical'
        },
        series: [
          {
            name: '上报来源统计分析',
            type: 'pie',
            radius: '55%',
            center: ['30%', '50%'],
            data: []
          }
        ]
      },
      incidentHandling: null,
      regionalSources: null,
      reportingSource: null,
      lastWhat: null,
      logData: [],
      listLoading: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      search: {
        name: '',
        precinctId: [],
        time: ''
      },
      communityList: [],
      gridList: [],
      formInfo: {},
      showAdd: false,
      dealDeptProps: {
        value: 'communityId',
        label: 'communityName',
        children: 'gridList',
        checkStrictly: true,
        expandTrigger: 'click',
        emitPath: true
      }
    }
  },
  computed: {},
  watch: {},
  mounted() {
    this.$nextTick(() => {
      this.init()
      if (this.isActived === 0) {
        this.getList1()
      } else {
        this.getList2()
      }
    })
    // this.getcommunityData()
    this.gridData()
    this.getTableDta()
  },
  methods: {
    handleDetail(val) {
      this.isActived = val.id
      if (val.name === '社区') {
        this.getList1()
      } else {
        this.getList2()
      }
    },
    getList1() {
      let param = ''
      if (this.searchForm.time) {
        if (this.searchForm.time.length > 0) {
          param = 'startTime=' + this.searchForm.time[0] + '&endTime=' + this.searchForm.time[1]
        }
      }
      axios.get('/binhuapis/event/statistical/disposalAnalysis?' + param).then((res) => {
        if (res.data.code === 200) {
          this.disposalAnalysis = res.data.data
          this.option.series[0].data = this.disposalAnalysis.map((item) => {
            return {
              value: item['已办'],
              name: item['部门名称']
            }
          })
          this.option.tooltip.formatter = (params) => {
            const marker = params.marker
            let details = {}
            this.disposalAnalysis.forEach((item) => {
              if (item['已办'] === params.data.value && item['部门名称'] === params.data.name) {
                details = item
              }
            })
            return (
              marker +
              details['部门名称'] +
              '<br/>' +
              ' 已办： ' +
              details['已办'] +
              ' 待办：' +
              details['待办'] +
              ' 超期未办：' +
              details['超期未办'] +
              ' 报废：' +
              details['报废'] +
              ' 已反馈：' +
              details['已反馈'] +
              '<br/>' +
              ' 待派遣：' +
              details['待派遣'] +
              ' 退回待派遣：' +
              details['退回待派遣'] +
              ' 处置率：' +
              details['处置率']
            )
          }
          if (
            // 判断是否存在echarts实例化对象,如果存在则销毁
            this.incidentHandling !== null &&
            this.incidentHandling !== '' &&
            this.incidentHandling !== undefined
          ) {
            this.incidentHandling.dispose()
          }
          this.incidentHandling = this.$echarts.init(document.getElementById('incidentHandling'))
          this.incidentHandling.setOption(this.option)
          window.addEventListener('resize', function () {
            this.incidentHandling.resize()
          })
        }
      })
    },
    getList2() {
      let param = ''
      if (this.searchForm.time) {
        if (this.searchForm.time.length > 0) {
          param = 'startTime=' + this.searchForm.time[0] + '&endTime=' + this.searchForm.time[1]
        }
      }
      axios.get('/binhuapis/event/statistical/disposalAnalysis2?' + param).then((res) => {
        if (res.data.code === 200) {
          this.disposalAnalysis = res.data.data
          this.option.series[0].data = this.disposalAnalysis.map((item) => {
            return {
              value: item['已办'],
              name: item['部门名称']
            }
          })
          this.option.tooltip.formatter = (params) => {
            const marker = params.marker
            let details = {}
            this.disposalAnalysis.forEach((item) => {
              if (item['已办'] === params.data.value && item['部门名称'] === params.data.name) {
                details = item
              }
            })
            return (
              marker +
              details['部门名称'] +
              '<br/>' +
              ' 已办： ' +
              details['已办'] +
              ' 待办：' +
              details['待办'] +
              ' 超期未办：' +
              details['超期未办'] +
              ' 报废：' +
              details['报废'] +
              ' 已反馈：' +
              details['已反馈'] +
              '<br/>' +
              ' 待派遣：' +
              details['待派遣'] +
              ' 退回待派遣：' +
              details['退回待派遣'] +
              ' 处置率：' +
              details['处置率']
            )
          }
          if (
            // 判断是否存在echarts实例化对象,如果存在则销毁
            this.incidentHandling !== null &&
            this.incidentHandling !== '' &&
            this.incidentHandling !== undefined
          ) {
            this.incidentHandling.dispose()
          }
          this.incidentHandling = this.$echarts.init(document.getElementById('incidentHandling'))
          this.incidentHandling.setOption(this.option)
          window.addEventListener('resize', function () {
            this.incidentHandling.resize()
          })
        }
      })
    },
    getList() {
      let param = ''
      if (this.searchForm.time) {
        if (this.searchForm.time.length > 0) {
          param = 'startTime=' + this.searchForm.time[0] + '&endTime=' + this.searchForm.time[1]
        }
      }
      // axios.get('/binhuapis/event/statistical/disposalAnalysis?' + param).then((res) => {
      //   if (res.data.code === 200) {
      //     this.disposalAnalysis = res.data.data
      //     this.option.series[0].data = this.disposalAnalysis.map((item) => {
      //       return {
      //         value: item['已办'],
      //         name: item['部门名称']
      //       }
      //     })
      //     this.option.tooltip.formatter = (params) => {
      //       const marker = params.marker
      //       let details = {}
      //       this.disposalAnalysis.forEach((item) => {
      //         if (item['已办'] === params.data.value && item['部门名称'] === params.data.name) {
      //           details = item
      //         }
      //       })
      //       return (
      //         marker +
      //         details['部门名称'] +
      //         '<br/>' +
      //         ' 已办： ' +
      //         details['已办'] +
      //         ' 待办：' +
      //         details['待办'] +
      //         ' 超期未办：' +
      //         details['超期未办'] +
      //         ' 报废：' +
      //         details['报废'] +
      //         ' 已反馈：' +
      //         details['已反馈'] +
      //         '<br/>' +
      //         ' 待派遣：' +
      //         details['待派遣'] +
      //         ' 退回待派遣：' +
      //         details['退回待派遣'] +
      //         ' 处置率：' +
      //         details['处置率']
      //       )
      //     }
      //     if (
      //       //判断是否存在echarts实例化对象,如果存在则销毁
      //       this.incidentHandling != null &&
      //       this.incidentHandling != '' &&
      //       this.incidentHandling != undefined
      //     ) {
      //       this.incidentHandling.dispose()
      //     }
      //     this.incidentHandling = this.$echarts.init(document.getElementById('incidentHandling'))
      //     this.incidentHandling.setOption(this.option)
      //     window.addEventListener('resize', function () {
      //       this.incidentHandling.resize()
      //     })
      //   }
      // })
      axios.get('/binhuapis/event/statistical/regionalSourceAnalysis?' + param).then((res) => {
        if (res.data.code === 200) {
          this.regionalSourceAnalysis = res.data.data
          this.option1.series[0].data = this.regionalSourceAnalysis.map((item) => {
            return {
              value: item['数量'],
              name: item['区域']
            }
          })
          this.option1.xAxis.data = this.regionalSourceAnalysis.map((item) => {
            return item['区域']
          })
          this.option1.tooltip.formatter = (params) => {
            const marker = params.marker
            let details = {}
            this.regionalSourceAnalysis.forEach((item) => {
              if (item['数量'] === params.data.value && item['区域'] === params.data.name) {
                details = item
              }
            })
            return marker + details['区域'] + '<br/>' + ' 数量： ' + details['数量'] + ' 占比：' + details['占比']
          }
          if (
            // 判断是否存在echarts实例化对象,如果存在则销毁
            this.regionalSources !== null &&
            this.regionalSources !== '' &&
            this.regionalSources !== undefined
          ) {
            this.regionalSources.dispose()
          }
          this.regionalSources = this.$echarts.init(document.getElementById('regionalSources'))
          this.regionalSources.setOption(this.option1)
          window.addEventListener('resize', function () {
            this.regionalSources.resize()
          })
        }
      })
      axios.get('/binhuapis/event/statistical/reportSourceAnalysis?' + param).then((res) => {
        if (res.data.code === 200) {
          this.reportSourceAnalysis = res.data.data
          this.option2.series[0].data = this.reportSourceAnalysis.map((item) => {
            return {
              value: item['数量'],
              name: item['来源']
            }
          })
          this.option2.tooltip.formatter = (params) => {
            const marker = params.marker
            let details = {}
            this.reportSourceAnalysis.forEach((item) => {
              if (item['数量'] === params.data.value && item['来源'] === params.data.name) {
                details = item
              }
            })
            return marker + details['来源'] + '<br/>' + ' 数量： ' + details['数量'] + ' 占比：' + details['占比']
          }
          if (
            // 判断是否存在echarts实例化对象,如果存在则销毁
            this.reportingSource !== null &&
            this.reportingSource !== '' &&
            this.reportingSource !== undefined
          ) {
            this.reportingSource.dispose()
          }
          this.reportingSource = this.$echarts.init(document.getElementById('reportingSource'))
          this.reportingSource.setOption(this.option2)
          window.addEventListener('resize', function () {
            this.reportingSource.resize()
          })
        }
      })
      axios.post('/binhuapis/event/statistical/customSourceAnalysis?' + param, this.list).then((res) => {
        let list = JSON.parse(JSON.stringify(this.option1))
        if (res.data.code === 200) {
          list.title.text = '热点事项分析——市容市貌'
          list.series[0].name = '热点事项分析——市容市貌'
          list.series[0].itemStyle = {
            normal: {
              color: function (params) {
                // build a color map as your need.
                let colorList = [
                  '#FE8463',
                  '#9BCA63',
                  '#FAD860',
                  '#F3A43B',
                  '#60C0DD',
                  '#B5C334',
                  '#FCCE10',
                  '#E87C25',
                  '#27727B',
                  '#D7504B',
                  '#C6E579',
                  '#F4E001',
                  '#F0805A',
                  '#26C0C0'
                ]
                return colorList[params.dataIndex]
              }
            }
          }
          list.series[0].data = res.data.data.map((item) => {
            return {
              value: item.count,
              name: item.eventName
            }
          })
          list.xAxis.data = res.data.data.map((item) => {
            return item.eventName
          })
          list.tooltip.formatter = (params) => {
            const marker = params.marker
            let details = {}
            res.data.data.forEach((item) => {
              if (item.eventName === params.data.name && item.count === params.data.value) {
                details = item
              }
            })
            return marker + details.eventName + ' 数量： ' + details.count
          }
          if (
            // 判断是否存在echarts实例化对象,如果存在则销毁
            this.lastWhat !== null &&
            this.lastWhat !== '' &&
            this.lastWhat !== undefined
          ) {
            this.lastWhat.dispose()
          }
          this.lastWhat = this.$echarts.init(document.getElementById('lastWhat'))
          this.lastWhat.setOption(list)
          window.addEventListener('resize', function () {
            this.lastWhat.resize()
          })
        }
      })
    },
    init() {
      this.getList()
      if (this.isActived === 0) {
        this.getList1()
      } else {
        this.getList2()
      }
    },
    gridData() {
      let url = '/binhuapis/clock/queryCommunityTree'
      axios.get(url).then((res) => {
        this.gridList = res.data.data
      })
    },
    getTableDta() {
      // this.listLoading = true
      let param = {
        pageStart: this.currentPage,
        pageSize: this.pageSize
      }
      if (this.search.time && this.search.time.length > 1) {
        param.beginTime = this.search.time[0]
        param.endTime = this.search.time[1]
      }
      if (this.search.precinctId && this.search.precinctId.length > 0 && this.search.precinctId.length < 2) {
        param.communityId = this.search.precinctId[0]
      }
      if (this.search.precinctId && this.search.precinctId.length > 1) {
        param.gridId = this.search.precinctId[1]
      }
      if (this.search.name) {
        param.name = this.search.name
      }
      let url = '/binhuapis/clock/queryClockUserStatisticsList?' + qs.stringify(param)
      axios.get(url).then((res) => {
        this.listLoading = false
        if (res.data.code === 200) {
          this.logData = res.data.data.list || []
          this.total = res.data.data.total
        }
      })
    },
    handlEdit(row) {
      console.log(row)
      this.$root.eventBus.$emit('clickMapPline', { time: this.search.time, rows: row })
      // this.showAdd = true
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getTableDta()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getTableDta()
    },
    table_index(index) {
      return (this.currentPage - 1) * this.pageSize + index + 1
    },
    querySerch() {
      this.getTableDta()
    },
    handleSuccess() { }
  }
}
</script>
<style lang="scss">
.statisticalAnalysis {
  width: 100%;
  height: 100%;
  .all_data {
    width: 100%;
    height: 770px;
    // overflow: auto;
    margin-top: 10px;
  }
  .position {
    position: absolute;
    right: 25px;
    top: 10px;
  }
  .statisticalSearch {
    margin-top: 5px;
    height: calc(8%);
    .el-card__body {
      padding: 10px 0 0 0;
    }
  }

  .statisticalBody {
    position: relative;
    .btn_list {
      display: flex;
      position: absolute;
      top: 10px;
      left: 535px;
      z-index: 999;
      height: 35px;
      width: 160px;
      cursor: pointer;
      .item {
        margin-right: 5px;
        text-align: center;
        line-height: 35px;
        width: 80px;
        height: 35px;
        background: rgba(49, 170, 255, 0.5);
        color: #fff;
      }
      .isActived {
        background: rgba(49, 170, 255, 1);
      }
    }
    .el-card__body {
      padding: 0;
      display: flex;
      height: 100%;
      width: 100%;
      flex-wrap: wrap;

      div {
        width: calc(100% / 2);
        height: calc(100% / 2);
      }
    }
  }
  .statisticalTable {
    margin-top: 10px;
    .table_list {
      margin-top: 10px;
      width: 100%;
      height: 500px;
    }
  }
}
</style>
