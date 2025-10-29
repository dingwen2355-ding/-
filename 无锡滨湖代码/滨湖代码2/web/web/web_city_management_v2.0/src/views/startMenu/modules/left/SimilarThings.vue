<template>
  <div class="SimilarThings module">
    <SmallTitle :titleList="['物资力量']" size="big" />
    <div class="content">
      <div class="lf-con">
        <div class="con-line-1">
          <span class="info"></span>
          <el-input
            v-model="level"
            type="number"
            onkeypress="return(/^[0-9]*$/.test(String.fromCharCode(event.keyCode)))"
            @change="changeLevel"
          ></el-input>
          <span class="info">公里内</span>
        </div>
        <div class="medio">
          <div id="medio16"></div>
        </div>
      </div>
      <div class="rt-con">
        <div class="con-line-2">
          <ul>
            <li
              v-for="(item, index) in source"
              :key="item"
              :class="{ active: activeIndex === index }"
              @click="activeIndex = index"
            >{{ item }}</li>
          </ul>
          <div
            v-for="(itm, ind) in source"
            v-show="activeIndex === ind"
            :key="ind"
            style="overflow: auto;height: 880rem;cursor: pointer"
          >
            <div
              class="listContent"
              v-for="(item, index) in sourceObjectList[itm]"
              :key="index"
              @click="clickPoint(item)"
            >
              <p class="title">{{ item.title }}</p>
              <p class="address">
                <span class="icon-place"></span>
                <span class="tip-place">{{ item.address }}</span>
                <span class="way">距离：{{ item.way }}km</span>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import SmallTitle from '@/components/SmallTitle.vue'
import { getEmergencyCoordinates, getEmergencySupplies, queryRescue, getEmergencyExpert } from '@/api/info'
import ZnvMap from '@/components/map/znvMap'

export default {
  name: 'SimilarThings',
  components: { SmallTitle },
  data() {
    const _this = this
    return {
      clientWidth: '',
      activeIndex: 0,
      level: 1,
      source: ['救援队伍', '救援专家', '避难场所', '物资装备'],
      sourceObjectList: {
        救援队伍: [],
        救援专家: [],
        避难场所: [],
        物资装备: []
      },
      option1: {
        color: ['#3C96F5', '#F3D662', '#EC8A2B', '#16F0F3', '#3C96F5'],
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
                width: _this.computeWidth(610),
                height: _this.computeWidth(560)
              },
              left: '19%',
              top: '9%'
            }
          ]
        },
        legend: {
          orient: 'vertical',
          // top: 'middle',
          bottom: _this.computeWidth(10),
          left: _this.computeWidth(104),
          itemWidth: _this.computeWidth(30),
          itemHeight: _this.computeWidth(30),
          itemGap: _this.computeWidth(30),
          // icon: 'circle',
          formatter: name => {
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
                      (data[i].value / sums) * 100 < 10
                        ? '' + Math.round((data[i].value / sums) * 100) + '%'
                        : Math.round((data[i].value / sums) * 100) + '%'
                  } else {
                    target =
                      (data[i].value / sums) * 100 < 10
                        ? '' + Math.round((data[i].value / sums) * 100) + '%'
                        : Math.round(data[i].value / sums) * 100 + '%'
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
                fontSize: '48rem',
                width: _this.computeWidth(100)
              },
              c: {
                fontSize: '48rem',
                width: _this.computeWidth(200),
                align: 'right',
                padding: [0, _this.computeWidth(25), 0, _this.computeWidth(150)]
              },
              b: {
                fontSize: '48rem',
                padding: [_this.computeWidth(5), _this.computeWidth(-100), 0, 0],
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
            radius: ['43%', '50%'],
            center: ['50%', '35%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: true,
                position: 'center',
                color: '#4c4a4a',
                formatter: name => {
                  let data = this.option1.series[0].data[0].total
                  let arr = '{total|' + data + '}' + '\n\r\n\r\n\r' + '{active|总数}'
                  return arr
                },
                rich: {
                  total: {
                    fontSize: '72rem',
                    fontFamily: '微软雅黑',
                    color: '#FFFFFF',
                    lineHeight: _this.computeWidth(60)
                  },
                  active: {
                    fontFamily: '微软雅黑',
                    fontSize: '65rem',
                    color: 'RGBA(255,255,255,0.6)',
                    lineHeight: _this.computeWidth(60)
                  }
                }
              }
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '48rem',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: []
          }
        ]
      }
    }
  },
  watch: {
    sourceObjectList: {
      handler() {
        let row = {
          物资装备: this.sourceObjectList.物资装备.length,
          救援队伍: this.sourceObjectList.救援队伍.length,
          救援专家: this.sourceObjectList.救援专家.length,
          避难场所: this.sourceObjectList.避难场所.length
        }
        this.totalSize =
          this.sourceObjectList.物资装备.length +
          this.sourceObjectList.救援队伍.length +
          this.sourceObjectList.救援专家.length +
          this.sourceObjectList.避难场所.length
        let data = []
        for (let key in row) {
          let obj = {
            count: row[key],
            mediaType: key,
            total: this.totalSize
          }
          data.push(obj)
        }
        data.sort(function (a, b) {
          return parseInt(a.count) < parseInt(b.count) ? 1 : -1
        })
        this.option1.series[0].data = data.map(v => {
          return {
            value: parseInt(v.count),
            name: v.mediaType,
            total: v.total
          }
        })
        this.$nextTick(() => {
          if (this.myChart) {
            this.myChart.setOption(this.option1)
          } else {
            this.myChart = echarts.init(document.getElementById('medio16'))
            this.myChart.setOption(this.option1)
          }
        })
      },
      immediate: true
    }
  },
  mounted() {
    setTimeout(() => {
      this.changeLevel()
    }, 4000)
  },
  methods: {
    clickPoint(item) {
      ZnvMap.markPoint([item.id])
    },
    computeWidth(width) {
      return width / 15360 * (document.documentElement.clientWidth || document.body.clientWidth)
    },
    changeLevel() {
      if (this.level && this.level > 0) {
        // 重新绘制半径 查询半径之内的资源
        this.loadEventCircle(this.level * 1000)
        setTimeout(() => {
          this.circleSourceObject()
        }, 1000)
      } else {
        // 没有半径 查询事件本身资源
        this.eventSourceObject()
      }
    },
    loadEventCircle(radiu) {
      const _this = this
      let data = ''
      let event = this.$store.getters.eventInfo
      if (event && event.gpsx && event.gpsy) {
        data = [event.gpsx, event.gpsy]
      } else {
        data = [120.310846, 31.58658]
      }
      ZnvMap.loadEventCircle({
        id: 'event_circle',
        center: data,
        radiu: radiu,
        height: _this.computeWidth(20)
      })
    },
    circleSourceObject() {
      this.sourceObjectList = {
        救援队伍: [],
        救援专家: [],
        避难场所: [],
        物资装备: []
      }
      let markerList = this.$store.getters.circleContainPoints
      markerList.forEach(e => {
        let t = e.opts.extData
        if (t.type === 'firestation') {
          this.sourceObjectList.救援队伍.push({
            title: t.TEAM_NAME,
            address: t.ADDRESS,
            way: t.distance.toFixed(2),
            id: '应急救援队伍_' + t.ID
          })
        } else if (t.type === 'specialist') {
          this.sourceObjectList.救援专家.push({
            title: t.title,
            address: t.address,
            way: t.distance.toFixed(2),
            id: '应急专家_' + t.id
          })
        } else if (t.type === 'shelter') {
          this.sourceObjectList.避难场所.push({
            title: t.shelterName,
            address: t.shelterAddress,
            way: t.distance.toFixed(2),
            id: '避难场所_' + t.id
          })
        } else if (t.type === 'fxbox') {
          this.sourceObjectList.物资装备.push({
            title: t.regionName,
            address: t.address,
            way: t.distance.toFixed(2),
            id: '应急物资_' + t.id
          })
        }
      })
    },
    eventSourceObject() {
      // 应急救援队伍
      let p1 = queryRescue('0')
      // 应急专家
      let p2 = getEmergencyExpert('0')
      // 避难场所
      let p3 = getEmergencyCoordinates('0')
      // 应急物资仓库
      let p4 = getEmergencySupplies('0')
      Promise.all([p1, p2, p3, p4]).then(res => {
        this.sourceObjectList = {
          救援队伍: res[0].data.data.teamList.map(t => {
            return {
              title: t.TEAM_NAME,
              address: t.ADDRESS,
              way: t.distance.toFixed(2),
              id: '应急救援队伍_' + t.ID
            }
          }),
          救援专家: res[1].data.data.data.map(t => {
            return {
              title: t.title,
              address: t.address,
              way: t.distance.toFixed(2),
              id: '应急专家_' + t.id
            }
          }),
          避难场所: res[2].data.data.data.map(t => {
            return {
              title: t.shelterName,
              address: t.shelterAddress,
              way: t.distance.toFixed(2),
              id: '避难场所_' + t.id
            }
          }),
          物资装备: res[3].data.data.list.map(t => {
            return {
              title: t.regionName,
              address: t.address,
              way: t.distance.toFixed(2),
              id: '应急物资_' + t.id
            }
          })
        }
        console.log(this.sourceObjectList)
      })
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

<style lang="scss" scoped>
.SimilarThings {
  width: 2840rem;
  // height: 850rem;
  position: absolute;
  top: 1856rem;
  left: 0rem;
  .content {
    margin: 0rem 0;
    padding-top: 15rem;
    position: relative;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;
    .lf-con {
      .medio {
        width: 992rem;
        height: 1072rem;
        #medio16 {
          width: 100%;
          height: 100%;
        }
      }
      .con-line-1 {
        margin-top: 48rem;
        display: flex;
        flex-wrap: nowrap;
        align-items: center;
        padding-left: 52rem;
        .icon-c {
          width: 60rem;
          height: 45rem;
          display: inline-block;
          background-image: url('../../../../assets/left/排名标签@2x.png');
          background-repeat: no-repeat;
          margin-top: 5rem;
          background-size: contain;
        }
        .info {
          // margin-left: 13rem;
          width: 280rem;
          font-size: 60rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #6fd1ff;
        }
        ::v-deep .el-input__inner {
          border: 1rem solid rgba(0, 145, 255, 0.5);
          border-radius: 2rem;
          color: #4d98cd;
          height: 105rem;
          text-align: center;
          width: 180rem;
          background: RGBA(16, 38, 60, 1);
          padding: 0 15rem;
        }
        .el-input {
          width: 20%;
          margin-left: -22rem;
        }
      }
    }
    .rt-con {
      .con-line-2 {
        margin-top: 80rem;
        ul {
          display: flex;
          margin-bottom: 36rem;
          li {
            width: 444rem;
            height: 108rem;
            text-align: center;
            font-size: 64rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            color: #ffffff;
            line-height: 108rem;
            opacity: 0.5;
            padding: 0 0 0 47rem;
            cursor: pointer;
            &.active {
              opacity: 1;
              background-image: url('../../../../assets/left/tab.png');
              background-size: 100% 100%;
            }
          }
        }

        .listContent {
          width: 100%;
          height: 252rem;
          font-size: 60rem;
          padding: 30rem 60rem;
          .title {
            margin-bottom: 20rem;
          }
          .address {
            color: #bee5ff;
            display: flex;
            align-items: center;
            .icon-place {
              margin-right: 10rem;
              width: 52rem;
              height: 52rem;
              display: inline-block;
              background: url('../../../../assets/left/_定位小@2x.png') no-repeat;
              background-size: 100% 100%;
              cursor: pointer;
              vertical-align: middle;
            }
            .tip-place {
              width: 1200rem;
              white-space: nowrap;
              text-overflow: ellipsis;
              overflow: hidden;
              display: inline-block;
            }
            .way {
              float: right;
            }
          }
        }
        .listContent:nth-of-type(2n-1) {
          background: #1a2f52;
        }
        .listContent:hover {
          background: rgba(2, 66, 146, 1);
        }
      }
    }
  }
}
</style>
