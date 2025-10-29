<template>
  <div class="Right2To4 module">
    <div v-show="setMore">
      <div class="monitor-search-input">
        <el-input placeholder="请输入搜索关键词" v-model="searchInput" clearable />
        <div class="search_button" @click="handleSearch">搜索</div>
      </div>
      <div class="info_title">
        相关搜索结果<span> {{ total }} </span>条
      </div>
      <div class="info_tab">
        <div class="listItem" v-for="(item, index) in showTable" :key="index" @click="handleDetail(item)">
          <img
            style="width: 14rem; height: 12rem; margin-top: 15rem; margin-right: 11rem"
            src="../../../../assets/right/微博.png"
            alt=""
          />
          <span>{{ item.title }}</span>
        </div>
      </div>
    </div>
    <div class="divMore" v-show="!setMore">
      <div class="left">
        <div class="title">媒体来源占比</div>
        <div class="medio">
          <div id="trendChartLeft"></div>
        </div>
      </div>
      <div class="right">
        <div class="title">倾向性占比</div>
        <div class="medio">
          <div id="trendChartRight"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import SmallTitle from '@/components/SmallTitle.vue'
import * as echarts from 'echarts'
import axios from '@/utils/request'
export default {
  name: 'LeftDuty',
  components: {},
  data() {
    return {
      setMore: false,
      searchInput: '',
      limitNum: 100,
      total: 0,
      showTable: [],
      option1: {
        color: ['#00E5FF', '#0082FF', '#FF5E9B', '#EDB800', '#4DE81F'],
        tooltip: {
          trigger: 'item'
        },
        legend: {
          // orient: 'vertical',
          // top: 'middle',
          bottom: 50,
          // right: 5,
          left: 20,
          itemGap: 10,
          icon: 'circle',
          formatter: (name) => {
            let data = this.option1.series[0].data
            let target
            let sums = this.sum(data)
            for (let i = 0, l = data.length; i < l; i++) {
              if (data[i].name === name) {
                // value = data[i].value
                if (sums !== 0) {
                  if (data[i].value !== 0) {
                    target = Math.round((data[i].value / sums) * 100) < 10 ? '  ' + Math.round((data[i].value / sums) * 100) + '%' : Math.round((data[i].value / sums) * 100) + '%'
                  } else {
                    target = '  ' + 0 + '%'
                  }
                } else {
                  target = '  ' + 0 + '%'
                }
              }
            }
            // if (name.length > 6) {
            //   name = name.substr(0, 6) + '...'
            // }
            let arr = ['{a|' + name + '}' + '{b|' + (target !== '100%' ? ('  ' + target) : target) + '}']
            return arr.join('')
          },
          textStyle: {
            rich: {
              a: {
                fontSize: 14,
                color: '#FFFFFF',
                width: 60
              },
              b: {
                fontSize: 14,
                color: '#FFFFFF',
                verticalAlign: 'top',
                padding: [0, 0, 0, 5]
                // align: 'right'
              }
            }
          }
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: ['30%', '50%'],
            center: ['50%', '25%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false
              }
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {
                value: 0,
                name: '短视频'
              },
              {
                value: 0,
                name: '网媒'
              },
              {
                value: 0,
                name: '论坛'
              },
              {
                value: 0,
                name: 'APP'
              },
              {
                value: 0,
                name: '微信'
              }
            ]
          }
        ]
      },
      option2: {
        color: ['#00E5FF', '#0082FF', '#EDB800'],
        tooltip: {
          trigger: 'item'
        },
        legend: {
          // orient: 'vertical',
          // top: 'middle',
          bottom: 60,
          // right: 1,
          itemGap: 10,
          icon: 'circle',
          formatter: (name) => {
            let data = this.option2.series[0].data
            let target
            let sums = this.sum(data)
            for (let i = 0, l = data.length; i < l; i++) {
              if (data[i].name === name) {
                if (sums !== 0) {
                  if (data[i].value !== 0) {
                    target = ((data[i].value / sums) * 100).toFixed(2) + '%'
                  } else {
                    target = (data[i].value / sums) * 100 + '%'
                  }
                } else {
                  target = 0 + '%'
                }
              }
            }
            let arr = ['{a|' + name + '}' + '{b|' + target + '}']
            return arr.join('')
          },
          textStyle: {
            rich: {
              a: {
                fontSize: 14,
                color: '#FFFFFF',
                width: 30
              },
              b: {
                fontSize: 14,
                color: '#FFFFFF',
                verticalAlign: 'top',
                padding: [0, 0, 0, 5]
                // align: 'right'
              }
            }
          }
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: ['30%', '50%'],
            center: ['50%', '25%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false
              }
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '40',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {
                value: 0,
                name: '正面'
              },
              {
                value: 0,
                name: '负面'
              },
              {
                value: 0,
                name: '中性'
              }
            ]
          }
        ]
      }
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart1()
      this.initChart2()
    })
    this.$root.Bus.$on('showMore', (data) => {
      if (data === false) {
        this.setMore = false
      } else {
        this.setMore = true
      }
    })
  },
  watch: {
    '$store.getters.eventInfo': {
      handler(v) {
        this.goToView(v)
      },
      deep: true
    }
  },
  methods: {
    handleDetail(v) {
      if (v.url) {
        let strWindowFeatures = `width=1920,height=1080,left=${
          window.outerWidth / 4
        },menubar=yes,location=yes,resizable=yes,scrollbars=true,status=true`
        window.open(v.url, 'newWindow', strWindowFeatures)
      } else {
        this.$message({
          message: '暂无链接',
          type: 'warning'
        })
      }
    },
    handleSearch() {
      this.getTableDataList()
      this.getMediaSourcesList()
      this.getInclinationList()
    },
    goToView(v) {
      // let input = v ? (v.eventType ? (v.area ? (v.eventType + ' ' + v.area) : v.eventType) : (v.area ? v.area : '')) : ''
      // let all = v && v.list ? v.list.join(' ') : ''
      // this.searchInput = input + ' ' + all
      this.searchInput = v.eventType && v ? v.eventType.replace('类', '') : ''
      this.handleSearch()
    },
    getTableDataList() {
      let url = '/apis/publicOpinion/searchAll?keywords=' + this.searchInput + '&limitNum=' + this.limitNum
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          this.showTable = res.data.data.records || []
          this.total = res.data.data.total || 0
        }
      })
    },
    getMediaSourcesList() {
      let url = '/apis/publicOpinion/mediaTypeStatistics?keywords=' + this.searchInput
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let arr = res.data.data || []
          let arr1 = arr.sort(this.compare('count')).slice(0, 5)
          this.option1.series[0].data = arr1.map((v) => {
            return {
              value: parseInt(v.count),
              name: v.mediaType
            }
          })
          this.$nextTick(() => {
            this.initChart1()
          })
        }
      })
    },
    compare(property) {
      // property:根据什么属性排序
      return function (a, b) {
        var value1 = a[property]
        var value2 = b[property]
        return value2 - value1 // 升序排序
      }
    },
    getInclinationList() {
      let url = '/apis/publicOpinion/attitudeStatistics?keywords=' + this.searchInput
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let arr = res.data.data || []
          arr.forEach((v) => {
            if (v.type === '1') {
              v.name = '正面'
            } else if (v.type === '2') {
              v.name = '负面'
            } else if (v.type === '3') {
              v.name = '中性'
            }
          })
          this.option2.series[0].data = arr.map((v) => {
            return {
              value: parseInt(v.count),
              name: v.name
            }
          })
          this.$nextTick(() => {
            this.initChart2()
          })
        }
      })
    },
    initChart1() {
      let myChart = echarts.init(document.getElementById('trendChartLeft'))
      myChart.setOption(this.option1)
    },
    initChart2() {
      let myChart = echarts.init(document.getElementById('trendChartRight'))
      myChart.setOption(this.option2)
    },
    sum(arr) {
      var s = 0
      for (var i = arr.length - 1; i >= 0; i--) {
        s += arr[i].value
      }
      return s
    }
  }
}
</script>

<style lang="scss">
.Right2To4 {
  width: 100%;
  position: absolute;
  top: 315rem;
  padding: 0 10rem;
  display: flex;
  margin-top: 12rem;
  .divMore {
    display: flex;
    width: 100%;
    height: 185rem;
  }
  .monitor-search-input {
    height: 40rem;
    width: 100%;
    padding: 0 18rem 0 20rem;
    margin-top: 13rem;
    margin-bottom: 10rem;
    display: flex;
    .el-input {
      width: 550rem;
      background: rgba(0, 148, 255, 0);
      border: 1rem solid rgba(0, 0, 0, 0.4);
    }
    .search_button {
      width: 80rem;
      height: 40rem;
      border: 1rem solid #0094ff;
      margin-left: 20rem;
      border-radius: 2rem;
      line-height: 40rem;
      text-align: center;
      font-size: 16rem;
      font-family: Microsoft YaHei;
      font-weight: 500;
      color: #93d0ff;
      cursor: pointer;
    }
  }
  .info_title {
    width: 100%;
    height: 16rem;
    line-height: 25rem;
    font-size: 14rem;
    font-family: Microsoft YaHei;
    color: #ffffff;
    padding-left: 18rem;
    span {
      color: #ffde00;
      font-size: 16rem;
      vertical-align: bottom;
    }
  }
  .lookmore {
    font-size: 16rem;
    font-family: SourceHanSansCN;
    font-weight: 400;
    color: #ffd56f;
    position: absolute;
    right: 8rem;
    bottom: 23rem;
  }
  .info_tab {
    width: 100%;
    padding: 0 1rem 0 3rem;
    height: 170rem;
    overflow: auto;
    position: relative;
    margin-top: 15rem;
    .listItem {
      display: flex;
      // height: 57rem;
      padding-left: 12rem;
      cursor: pointer;
      width: 680rem;
      height: 37rem;
      line-height: 37rem;
      background: #092f52;
      margin-bottom: 5rem;
      span {
        display: inline-block;
        text-align: left;
        font-size: 16rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #9ab1cb;
        // line-height: 48rem;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    .no_data {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
  .left {
    width: 340rem;
    height: 258rem;
    border: 1rem solid rgba(38, 59, 86, 1);
    .title {
      font-size: 14rem;
      font-family: SourceHanSansCN;
      font-weight: 500;
      color: #ffffff;
      line-height: 48rem;
      margin-left: 30rem;
      background: linear-gradient(0deg, #13a1f1 0%, #ffffff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
    .medio {
      width: 100%;
      height: 100%;
      overflow: auto;
    }
    #trendChartLeft {
      width: 100%;
      height: 100%;
      overflow: auto;
    }
  }
  .right {
    width: 340rem;
    height: 258rem;
    border: 1rem solid rgba(38, 59, 86, 1);
    margin-left: 14rem;
    .title {
      font-size: 14rem;
      font-family: SourceHanSansCN;
      font-weight: 500;
      color: #ffffff;
      line-height: 48rem;
      margin-left: 30rem;
      background: linear-gradient(0deg, #13a1f1 0%, #ffffff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
    .medio {
      width: 100%;
      height: 100%;
    }
    #trendChartRight {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
