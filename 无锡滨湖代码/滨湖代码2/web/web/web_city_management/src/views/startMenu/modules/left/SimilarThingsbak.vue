<template>
  <div class="SimilarThings module">
    <SmallTitle :titleList="['应急物资']" />
    <div class="content">
      <div class="con-line-1">
        <span class="info">范围搜索:</span>
        <el-input v-model="level" type="number" onkeypress="return(/^[0-9]*$/.test(String.fromCharCode(event.keyCode)))"></el-input>
        <span class="info">公里内</span>
      </div>
      <div class="medio">
        <div id="medio16"></div>
      </div>
      <div class="con-line-2">
        <ul>
          <li
            v-for="(item, index) in source"
            :key="item"
            :class="{ active: activeIndex === index }"
            @click="change(index)"
          >
            {{ item }}
          </li>
        </ul>
        <div  v-for="(itm, ind) in source" v-show="activeIndex === ind"
             :key="ind" style="overflow: auto;height: 880rem">
          <div class="listContent" v-for="(item, index) in sourceObjectList[itm]" :key="index">
            <p class="title" >{{ item.title }}</p>
            <p class="address">
              <span class="icon-place" ></span><span class="tip-place">{{ item.address }}</span>
              <span class="way">距离：{{ item.way }}km</span>
            </p>
          </div>
        </div>
      </div>
    </div>
    <showLeadersDialog :dialogVisibleFlag.sync="showLeadersVisible" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
import SmallTitle from '@/components/SmallTitle.vue'
import Axios from '@/utils/request'
import showLeadersDialog from '../dialog/showLeadersDialog.vue'
export default {
  name: 'SimilarThings',
  components: { SmallTitle, showLeadersDialog },
  data() {
    return {
      title: ['领导指示', '指令'],
      activeIndex: 0,
      level: '',
      source: ['救援队伍', '救援专家', '避难场所', '物资装备'],
      sourceList: [
        {
          title: '公交集团道路运输应急转运队',
          address: '江苏省无锡市滨湖区商会大厦裙楼4楼',
          way: '2.48km'
        },
        {
          title: '应急管理中队',
          address: '江苏省无锡市滨湖区观山路199号市民中心8号楼',
          way: '2.48km'
        },
      ],
      sourceObjectList:{
        '救援队伍':[],
        '救援专家':[],
        '避难场所':[],
        '物资装备':[]
      },
      leadershipList: [],
      showLeadersVisible: false,
      showLookVisible: false,
      showLeadersList: [],
      videoVisible: false,
      timer: null,
      timer1: null,
      showViewVisible: false,
      videoMeetingVisible: false,
      showViewlist: false,
      videoMeetingData: {},
      options: [],
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
                width: 510,
                height: 460
              },
              left: '2%',
              top: '15%'
            }
          ]
        },
        legend: {
          orient: 'vertical',
          // top: 'middle',
          bottom: 5,
          left: 600,
          itemWidth: 30,
          itemHeight: 30,
          itemGap: 30,
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
                fontSize: 48,
                width: 100
              },
              c: {
                fontSize: 48,
                width: 200,
                align: 'right',
                padding: [0, 25, 0, 150]
              },
              b: {
                fontSize: 48,
                padding: [5, -100, 0, 0],
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
            radius: ['56%', '80%'],
            center: ['20.4%', '58%'],
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
                    fontSize: 72,
                    fontFamily: '微软雅黑',
                    color: '#FFFFFF',
                    lineHeight: 60
                  },
                  active: {
                    fontFamily: '微软雅黑',
                    fontSize: 65,
                    color: '#717C85',
                    lineHeight: 60
                  }
                }
              }
            },
            emphasis: {
              label: {
                show: false,
                fontSize: '48',
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
      myChart:null
    }
  },
  mounted() {
    // this.getLeaderShip()
    // this.drawBill()
    // this.timer = setInterval(() => {
    //   this.getLeaderShip()
    // }, 1000 * 10)
    // this.$root.Bus.$on('drawBill', (data) => {
    //   if (data) {
    //     this.drawBill()
    //   }
    // })
    this.$nextTick(() => {
      this.init()
    })
    this.$root.Bus.$on('eventInfos', (data) => {
      this.sourceObjectList.救援队伍 = []
      data['救援队伍'].forEach(t=>{
        this.sourceObjectList.救援队伍.push({
          title: t.TEAM_NAME,
          address: t.ADDRESS,
          way: t.distance.toFixed(2)
        })
      })
      this.sourceObjectList.救援专家 = []
      data['救援专家'].forEach(t=>{
        this.sourceObjectList.救援专家.push({
          title: t.title,
          address: t.address,
          way: t.distance.toFixed(2)
        })
      })
      this.sourceObjectList.避难场所 = []
      data['避难场所'].forEach(t=>{
        this.sourceObjectList.避难场所.push({
          title: t.shelterName,
          address: t.shelterAddress,
          way: t.distance.toFixed(2)
        })
      })
      this.sourceObjectList.物资装备 = []
      data['物资装备'].forEach(t=>{
        this.sourceObjectList.物资装备.push({
          title: t.regionName,
          address: t.address,
          way: t.distance.toFixed(2)
        })
      })
      this.updateChart()
    })
  },
  watch:{
    level(val){
      this.$root.Bus.$emit('eventRadiu', val)
    }
  },
  methods: {
    change(v) {
      this.activeIndex = v
    },
    updateChart(){
      // let url = '/apis/warningInfo/getInfo'
      // axios.get(url).then((res) => {
      //   if (res.data.code === 200) {
      let row = {
        物资装备: this.sourceObjectList.物资装备.length,
        救援队伍: this.sourceObjectList.救援队伍.length,
        救援专家: this.sourceObjectList.救援专家.length,
        避难场所: this.sourceObjectList.避难场所.length,
      }
      this.totalSize = this.sourceObjectList.物资装备.length + this.sourceObjectList.救援队伍.length + this.sourceObjectList.救援专家.length + this.sourceObjectList.避难场所.length
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

      this.myChart.setOption(this.option1,true,false)
    },
    init() {
      // let url = '/apis/warningInfo/getInfo'
      // axios.get(url).then((res) => {
      //   if (res.data.code === 200) {
      let row = {
        物资装备: this.sourceObjectList.物资装备.length,
        救援队伍: this.sourceObjectList.救援队伍.length,
        救援专家: this.sourceObjectList.救援专家.length,
        避难场所: this.sourceObjectList.避难场所.length,
      }
      this.totalSize = this.sourceObjectList.物资装备.length + this.sourceObjectList.救援队伍.length + this.sourceObjectList.救援专家.length + this.sourceObjectList.避难场所.length
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
      //   }
      // })
    },
    initChart1() {
      this.myChart = echarts.init(document.getElementById('medio16'))
      this.myChart.setOption(this.option1)
    },
    sum(arr) {
      var s = 0
      for (var i = arr.length - 1; i >= 0; i--) {
        s += arr[i].value
      }
      return s
    },
    click(e) {
      console.log(e)
    },
    handleClick(index) {
      this.activeIndex = index
    },
    drawBill() {
      let url = '/apis/eventJoint/queryTitle'
      Axios.get(url).then((res) => {
        let data = res.data
        if (data.code === 200) {
          let r = data.data.list
          this.page = r
        } else {
          this.$message({
            message: data.message,
            type: 'warning'
          })
        }
      })
    },
    // 组织部门
    departments() {
      let url = '/apis/eventJoint/getDepartmentAll'
      Axios.get(url).then((res) => {
        let data = res.data
        // eslint-disable-next-line eqeqeq
        if (data.code == 200) {
          let r = data.data
          this.options = r
        } else {
          this.$message({
            message: data.message,
            type: 'warning'
          })
        }
      })
    },
    // 领导指示 / 要求
    getLeaderShip() {
      let url = '/apis/leaderInstruction/instructionContent'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data
          if (data.leaderInstruction && data.leaderInstruction.length > 0) {
            this.showLeadersList = data.leaderInstruction
            let leaderTrue = data.leaderInstruction.filter((item) => Number(item.status) !== 1)
            this.leadershipList = leaderTrue
          } else {
            this.leadershipList = []
          }
        }
      })
    },
    show() {
      if (this.activeIndex === 0) {
        this.showLookVisible = true
      } else {
        this.showViewVisible = true
      }
    },
    more() {
      if (this.activeIndex === 0) {
        this.showLeadersVisible = true
      } else {
        this.showViewlist = true
      }
    },
    handleVideo() {
      this.videoVisible = true
      this.clearOprator()
    }
  }
}
</script>

<style lang="scss" scoped>
.SimilarThings {
  width: 1390rem;
  // height: 850rem;
  position: absolute;
  top: 1910rem;
  left: 2490rem;
  .content {
    margin: 15rem 0;
    position: relative;
    .medio {
      width: 100%;
      height: 500rem;
      #medio16 {
        width: 100%;
        height: 100%;
      }
    }
    .con-line-2 {
      margin-top: 80rem;
      ul {
        display: flex;
        margin-bottom: 36rem;
        li {
          width: 239rem;
          height: 82rem;
          text-align: center;
          font-size: 48rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #ffffff;
          line-height: 80rem;
          opacity: 0.5;
          padding: 0 0 0 47rem;
          cursor: pointer;
          &.active {
            opacity: 1;
            background-image: url('../../../../assets/left/未选中.png');
            background-size: 100% 100%;
          }
        }
      }

      .listContent {
        width: 88%;
        height: 202rem;
        font-size: 48rem;
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
          .tip-place{
            width: 700rem;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;display: inline-block;
          }
          .way {
            float: right;
          }
        }
      }
      .listContent:nth-of-type(2n-1) {
        background: #1a2f52;
      }
    }
    .con-line-1 {
      margin-top: 48rem;
      display: flex;
      flex-wrap: nowrap;
      align-items: center;
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
        font-size: 48rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #6fd1ff;
      }
      ::v-deep .el-input__inner {
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        color: #4d98cd;
        height: 84rem;
        text-align: center;
        width: 180rem;
        background: RGBA(16, 38, 60, 1);
      }
      .el-input {
        width: 14%;
        margin-left: -42rem;
      }
    }
  }
}
</style>
