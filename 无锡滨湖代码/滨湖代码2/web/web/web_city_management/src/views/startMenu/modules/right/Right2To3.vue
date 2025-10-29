<template>
  <div class="Right2To3 module">
    <div class="content">
<!--      <div class="leader">-->
<!--        <img src="../../../../assets/global/title.png" alt="" />-->
<!--        <ul>-->
<!--          <li-->
<!--            v-for="(item, index) in title"-->
<!--            :key="index"-->
<!--            :class="{ active: index === activeIndex }"-->
<!--            @click="handleClick(index)"-->
<!--          >-->
<!--            {{ item }}-->
<!--          </li>-->
<!--        </ul>-->
        <!-- <div style="margin-bottom: 65rem">
          <span class="more">更多>></span>
          <img class="add1" style="width: 98rem; height: 88rem" src="../../../../assets/global/新增.png" alt="" />
        </div> -->
<!--      </div>-->
      <div style="display: flex;">
        <div class="left">
          <span class="label">事件类型: </span>
          <el-select v-model="value" class="large-screen event-type-select" placeholder="请选择" style="margin-top: 18rem;margin-bottom: 46rem" @change="handleSelect" clearable>
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"> </el-option>
          </el-select>
          <div style="display: flex">
            <div class="info_title">
              相关搜索结果<span> {{ totalSize }} </span>条
            </div>
            <div class="lookmore" @click="showMore">更多>></div>
          </div>
          <ul style="height: 1315rem; overflow: auto;margin-top: 48rem">
            <li
              style="cursor: pointer"
              v-for="(item, index) in showTable"
              :key="index"
              :title="item.fmcontent"
              @click="showDetail(item)"
            >
              <span>{{ index + 1 }}</span>
              {{ item.fmcontent }}
            </li>
          </ul>
        </div>
        <div class="right">
          <div class="medio" style="position: relative;top: 100rem">
            <div id="medio15"></div>
          </div>
        </div>
      </div>
    </div>
    <lookMoreDialog :dialogVisibleFlag.sync="showMoreVisible" :infomation="options" :showValue="value" />
    <eventDetailDialog :dialogVisibleFlag.sync="showEventVisible" :infomation="showEventData" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
import lookMoreDialog from './lookMoreDialog.vue'
import eventDetailDialog from './eventDetail.vue'
import axios from '@/utils/request'
export default {
  name: 'LeftDuty',
  components: { lookMoreDialog, eventDetailDialog },
  data() {
    return {
      searchInput: '',
      title: ['12345'],
      activeIndex: 0,
      showMoreVisible: false,
      showEventVisible: false,
      showEventData: {},
      options: [],
      value: '',
      option1: {
        color: ['#00E5FF', '#0082FF', '#FF5E9B', '#73FBFD', '#A6B2BB', '#CCE198', '#8C97CB'],
        tooltip: {
          trigger: 'item'
        },
        graphic: {
          elements: [
            {
              type: 'image',
              z: 3,
              style: {
                image: require('../../../../assets/global/环形图bg.png'),
                width: this.computeWidth(635),
                height: this.computeWidth(550)
              },
              left: '28%',
              top: '5%'
            }
          ]
        },
        legend: {
          orient: 'vertical',
          // top: 'middle',
          bottom: this.computeWidth(40),
          left: this.computeWidth(60),
          itemWidth: this.computeWidth(28),
          itemHeight: this.computeWidth(28),
          itemGap: this.computeWidth(50),
          // icon: 'circle',
          formatter: (name) => {
            let data = this.option1.series[0].data
            let target
            let value
            // let value
            let sums = this.sum(data)
            for (let i = 0, l = data.length; i < l; i++) {
              if (data[i].name === name) {
                value = data[i].value
                // value = data[i].value
                if (sums !== 0) {
                  if (data[i].value !== 0) {
                    target =
                      ((data[i].value / sums) * 100 < 10
                        ? '' + Math.round((data[i].value / sums) * 100) + '%'
                        : Math.round((data[i].value / sums) * 100) + '%')
                  } else {
                    target =
                      ((data[i].value / sums) * 100 < 10
                        ? '' + Math.round((data[i].value / sums) * 100) + '%'
                        : Math.round(data[i].value / sums) * 100 + '%')
                  }
                } else {
                  target = '  ' + 0 + '%'
                }
              }
            }
            // if (name.length > 6) {
            //   name = name.substr(0, 6) + '...'
            // }
            let arr = ['{a|' + name + '}' + '{c|' + value + '}' + '{b|' + target + '}']
            return arr.join('')
          },
          textStyle: {
            color: 'auto',
            rich: {
              a: {
                fontSize: this.computeWidth(48),
                width: this.computeWidth(400)
              },
              c: {
                fontSize: this.computeWidth(48),
                width: this.computeWidth(200),
                align: 'right',
                //padding: [0, this.computeWidth(25), 0, this.computeWidth(300)]
              },
              b: {
                fontSize: this.computeWidth(48),
                //padding: [this.computeWidth(5), this.computeWidth(-100), 0, 0],
                verticalAlign: 'top'
                // align: 'right'
              }
            }
          }
        },
        series: [
          {
            name: '',
            type: 'pie',
            radius: ['32%', '45%'],
            center: ['56%', '26.2%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: true,
                position: 'center',
                color: '#4c4a4a',
                formatter: (name) => {
                  let data = this.option1.series[0].data[0].total
                  let arr = '{total|' + data + '}' + '\n\r\n\r\n\r\n\r\n\r' + '{active|总数}'
                  return arr
                },
                rich: {
                  total: {
                    fontSize: this.computeWidth(72),
                    fontFamily: '微软雅黑',
                    color: '#FFFFFF',
                    lineHeight: this.computeWidth(60)
                  },
                  active: {
                    fontFamily: '微软雅黑',
                    fontSize: this.computeWidth(65),
                    color: '#717C85',
                    lineHeight: this.computeWidth(60)
                  }
                }
              }
            },
            emphasis: {
              label: {
                show: false,
                fontSize: this.computeWidth(48),
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: []
          }
        ]
      },
      totalSize: 18245,
      totalSize1: 0,
      showTable: [{
        fmcontent: '安全隐患早已存在，还好救援，并未造成......'
      }, {
        fmcontent: '工厂火灾，消防栓无水'
      }, {
        fmcontent: '安全隐患早已存在，还好救援，并未造成......'
      }, {
        fmcontent: '工厂火灾，消防栓无水'
      }, {
        fmcontent: '安全隐患早已存在，还好救援，并未造成......'
      }, {
        fmcontent: '工厂火灾，消防栓无水'
      }, {
        fmcontent: '工厂火灾，消防栓无水'
      }, {
        fmcontent: '安全隐患早已存在，还好救援，并未造成......'
      }, {
        fmcontent: '工厂火灾，消防栓无水'
      }],
      timer: null,
      clientHeight:'',
      clientWidth:''
    }
  },
  mounted() {
    this.$nextTick(() => {
      // this.get12345Event()
      this.init()
    })
    // this.timer = setInterval(() => {
    //   this.init()
    // }, 1000 * 147)
  },
  methods: {
    computeWidth(width){
      return width/15360*(document.documentElement.clientWidth || document.body.clientWidth)
    },
    handleSelect() {
      this.get12345Event('1')
    },
    showDetail(val) {
      this.showEventData = val
      this.showEventVisible = true
    },
    get12345Event(val) {
      let url = '/apis/warningInfo/get12345EventList?pageNum=1&pageSize=5'
      if (this.value) {
        url += '&type=' + this.value
      }
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let row = res.data.data
          this.showTable = row.content
          if (val && this.value) {
            this.totalSize = row.totalSize || 0
          } else {
            this.totalSize = this.totalSize1
          }
        }
      })
    },
    init() {
      let url = '/community/dapeng/event/getSyncEventCount'
      axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let row  = {}
          res.data.data.forEach(t=>{
            if(t.typeName === '总计'){
              this.totalSize = t.num
            }else{
              row[t.typeName]=t.num
            }
          })
          this.totalSize1 = JSON.parse(JSON.stringify(this.totalSize))
          let data = []
          for (let key in row) {
            let obj = {
              count: row[key],
              mediaType: key,
              total: this.totalSize
            }
            data.push(obj)
          }
          this.options = data.map((item) => {
            return {
              value: item.mediaType,
              label: item.mediaType
            }
          })
          data.sort(function (a, b) {
            return parseInt(a.count) < parseInt(b.count) ? 1 : -1
          })
          this.option1.series[0].data = data.map((v) => {
            return {
              value: parseInt(v.count),
              name: v.mediaType,
              total: v.total
            }
          })
          this.$nextTick(() => {
            this.initChart1()
          })
        }
      })
    },
    showMore() {
      this.showMoreVisible = true
    },
    handleSearch() {
      console.log(this.searchInput)
    },
    initChart1() {
      let myChart = echarts.init(document.getElementById('medio15'))
      myChart.setOption(this.option1)
    },
    sum(arr) {
      var s = 0
      for (var i = arr.length - 1; i >= 0; i--) {
        s += arr[i].value
      }
      return s
    },
    beforeDestroy() {
      this.timer && clearInterval(this.timer)
    }
  }
}
</script>

<style lang="scss">
.Right2To3 {
  width: 100%;
  height: 246rem;
  position: absolute;
  top: 1800rem;
  .content {
    .leader {
      width: 100%;
      height: 72rem;
      display: flex;
      img {
        width: 105rem;
      }
      ul {
        display: flex;
        width: 590rem;
        li {
          font-size: 60rem;
          font-family: Microsoft YaHei;
          font-weight: 600;
          color: #ffffff;
          line-height: 70rem;
          background: linear-gradient(0deg, #13a1f1 0%, #ffffff 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          margin: 0 25rem;
          cursor: pointer;
        }
      }
      span {
        font-size: 16rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ffd56f;
        margin-top: 6rem;
        cursor: pointer;
      }
      .more {
        font-size: 48rem;
        font-family: Source Han Sans CN;
        font-weight: 400;
        color: #ffd56f;
        position: absolute;
        right: 8%;
        top: -6rem;
      }
    }
    .left {
      width: 50%;
      .label {
        font-size: 48rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #6fd1ff;
      }
      .el-select {
        width: 910rem;
        height: 85rem;
        .el-input__inner {
          height: 85rem;
        }
      }
      .info_title {
        font-size: 45rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ffffff;
        line-height: 48rem;
        padding-left: 0;
        width: 65%;
      }
      .lookmore {
        font-size: 45rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ffd56f;
        line-height: 46rem;
        margin-left: 35rem;
      }
      ul {
        li {
          width: 1125rem;
          height: 132rem;
          background: #092f52;
          margin-bottom: 10rem;
          font-size: 48rem;
          line-height: 135rem;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          span {
            width: 88rem;
            height: 100rem;
            line-height: 100rem;
            display: inline-block;
            border: 1rem solid RGBA(61, 91, 155, 1);
            text-align: center;
            background: #1A2F52;
            color: RGBA(0, 144, 255, 1);
            font-size: 45rem;
            margin-left: 5rem;
            margin-right: 18rem;
          }
        }
      }
    }
    .right {
      width: 50%;
      .medio {
        width: 100%;
        height: 1363rem;
      }
      #medio15 {
        width: 100%;
        height: 100%;
      }
    }
    .monitor-search-input {
      height: 40rem;
      width: 100%;
      padding: 0 18rem 0 20rem;
      margin-top: 13rem;
      margin-bottom: 10rem;
      display: flex;
      .el-input {
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
      font-size: 16rem;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #00ffff;
      padding-left: 18rem;
      span {
        color: #ffde00;
        font-size: 56rem;
        vertical-align: bottom;
      }
    }
    .lookmore {
      font-size: 16rem;
      font-family: SourceHanSansCN;
      font-weight: 400;
      color: #ffd56f;
      cursor: pointer;
      // position: absolute;
      // right: 8rem;
      // bottom: 23rem;
    }
    .info_tab {
      width: 100%;
      padding: 0 1rem 0 3rem;
      height: 285rem;
      overflow: auto;
      position: relative;
      .listItem {
        display: flex;
        // height: 57rem;
        padding-left: 12rem;
        cursor: pointer;
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
      .listItem:nth-of-type(3) .title {
        margin-left: 86rem !important;
      }
      .no_data {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
      }
    }
  }
  .event-type-select{
    .el-input__inner{
      width: 910rem;
    }
  }
}
</style>
