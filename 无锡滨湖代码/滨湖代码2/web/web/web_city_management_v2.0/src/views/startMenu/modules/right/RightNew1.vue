<template>
  <div class="RightNew1 module">
    <SmallTitle :titleList="['重点旅游景区拥堵排名TOP10','重点区域人流']" size="big" @click="titleClick" />
    <div class="content">
      <!-- <div id="humanTrafficStatisticsEcharts"></div> -->
      <div class="bottom">
        <!-- 790rem tableData-->
        <el-table
          :data="tableData"
          class="large-screen1"
          height="1490rem"
          v-if="titleActiveIndex===0"
        >
          <el-table-column prop="index" align="center" label="排名" min-width="8%">
            <template slot-scope="scope">
              <span
                v-if="scope.row.index <= 3"
                :class="['ball', 'color-' + scope.row.index]"
              >{{ scope.row.index }}</span>
              <span v-else>{{ scope.row.index }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="road_name"
            label="名称"
            show-overflow-tooltip
            align="center"
            min-width="15%"
          ></el-table-column>
          <el-table-column prop="evaluation_blocked" label="拥堵指数" align="center" min-width="10%"></el-table-column>
          <el-table-column prop="evaluation_congested" label="缓行" align="center" min-width="10%"></el-table-column>
          <el-table-column prop="evaluation_expedite" label="畅通" align="center" min-width="10%"></el-table-column>
          <el-table-column
            prop="description"
            label="描述"
            show-overflow-tooltip
            align="center"
            min-width="20%"
          ></el-table-column>
          <!-- <el-table-column prop="reportTime" label="最新时间" align="center" min-width="20%"></el-table-column> -->
        </el-table>
        <div v-if="titleActiveIndex===1">
          <el-date-picker
            v-model="value1"
            type="datetimerange"
            popper-class="large-screen"
            class="large-screen"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            @change="changeDate"
            value-format="yyyy-MM-dd HH:00:00"
            style="height: 200rem; width: 2600rem;"
          ></el-date-picker>
          <el-table :data="tableData2" class="large-screen1" height="1320rem">
            <el-table-column prop="index" align="center" label="序号" min-width="8%">
              <template slot-scope="scope">
                <span
                  v-if="scope.row.index <= 3"
                  :class="['ball', 'color-' + scope.row.index]"
                >{{ scope.row.index }}</span>
                <span v-else>{{ scope.row.index }}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="address"
              label="地点"
              show-overflow-tooltip
              align="center"
              min-width="20%"
            ></el-table-column>
            <!-- <el-table-column
              prop="dailyCumulativePassengerFlow"
              label="日累计客流"
              show-overflow-tooltip
              align="center"
              min-width="20%"
            ></el-table-column>-->
            <el-table-column
              prop="realTimePersonnelEstimation"
              label="实时人数估算"
              show-overflow-tooltip
              align="center"
              min-width="20%"
            ></el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import axios from '@/utils/request';;
import * as echarts from 'echarts'
import moment from 'moment';

export default {
  name: 'RightNew1',
  components: { SmallTitle },
  props: ['info'],
  data() {
    return {
      humanTrafficStatisticsEcharts: null,
      tableData: [],
      option: {
        grid: {
          left: "1%",
          top: "20%",
          right: "3%",
          bottom: "5%",
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            padding: [-2, 0, 0, -1],
            fontSize: 6
          },
          axisLine: { show: false },
          axisTick: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: "#BBC9DE"
            }
          },
          boundaryGap: false,
          data: [
            moment().format('MM.DD'),
            moment().subtract(1, 'days').format('MM.DD'),
            moment().subtract(2, 'days').format('MM.DD'),
            moment().subtract(3, 'days').format('MM.DD'),
            moment().subtract(4, 'days').format('MM.DD'),
            moment().subtract(5, 'days').format('MM.DD'),
            moment().subtract(6, 'days').format('MM.DD'),
            moment().subtract(7, 'days').format('MM.DD'),
            moment().subtract(8, 'days').format('MM.DD'),
            moment().subtract(9, 'days').format('MM.DD')
          ]
        },
        yAxis: {
          name: "人流量统计：万人",
          nameGap: 7,
          nameTextStyle: {
            color: "#fff",
            fontSize: 8,
            fontWeight: "normal",
            padding: [10, 0, 0, 24]
          },
          type: 'value',
          axisLabel: {
            fontSize: 8
          },
          axisLine: {
            lineStyle: {
              color: '#3A6497'
            }
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(235,238,245, 0.1)'
            }
          },
          max: value => {
            if (value.max < 5) {
              return 5;
            } else if (value.max < 10) {
              return 10;
            }
            let max = value.max;
            let val = max / 5;
            const round = false;
            const exponent = Math.floor(Math.log(val) / Math.LN10);
            const exp10 = Math.pow(10, exponent);
            const f = val / exp10; // 1 <= f < 10
            let nf;
            if (round) {
              if (f < 1.5) {
                nf = 1;
              } else if (f < 2.5) {
                nf = 2;
              } else if (f < 4) {
                nf = 3;
              } else if (f < 7) {
                nf = 5;
              } else {
                nf = 10;
              }
            } else {
              if (f < 1) {
                nf = 1;
              } else if (f < 2) {
                nf = 2;
              } else if (f < 3) {
                nf = 3;
              } else if (f < 5) {
                nf = 5;
              } else {
                nf = 10;
              }
            }
            val = nf * exp10;
            return val * 5;
          }
        },
        series: [
          {
            data: [11, 12, 9, 14, 19, 13, 10, 12, 9, 14],
            type: 'line',
            symbol: 'circle',
            color: '#13D1DB',
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: "rgba(11,114,146, 1)"
                }, {
                  offset: 0.8,
                  color: "rgba(11,114,146, 0.2)",
                }], false),
                shadowColor: "rgba(11,114,146, 0.2)",
                shadowBlur: 10
              }
            }
          }
        ]
      },
      titleActiveIndex: 0,
      value1: [
        moment().clone().subtract(1, 'hours').format('yyyy-MM-DD HH:00:00'),
        moment().format('yyyy-MM-DD HH:00:00')
      ],
      pickerOptions: {
        disabledDate: (time) => {
          const hours = time.getHours();
          const minutes = time.getMinutes();
          const seconds = time.getSeconds();

          // 禁止选择任何时间，只允许选择小时
          return minutes !== 0 || seconds !== 0;
        }
      },
      tableData2: [
        {
          index: 1,
          id: '4151392041800135864',
          address: '无锡动物园',
          dailyCumulativePassengerFlow: '1089',
          realTimePersonnelEstimation: '382-421'
        },
        {
          index: 2,
          id: '4151392007148606284',
          address: '滨湖万达（商圈）',
          dailyCumulativePassengerFlow: '4788',
          realTimePersonnelEstimation: '2650-2930'
        },
        {
          index: 3,
          id: '4151368509581678218',
          address: '梅园景区',
          dailyCumulativePassengerFlow: '1693',
          realTimePersonnelEstimation: '855-933'
        },
        {
          index: 4,
          id: '4151367427115212271',
          address: '三国城水浒城景区',
          dailyCumulativePassengerFlow: '2084',
          realTimePersonnelEstimation: '1105-1209'
        },
        {
          index: 5,
          id: '4151367701877250025',
          address: '鼋头渚风景区',
          dailyCumulativePassengerFlow: '3153',
          realTimePersonnelEstimation: '1775-1962'
        },
        {
          index: 6,
          id: '4151363665125917808',
          address: '拈花湾景区',
          dailyCumulativePassengerFlow: '1303',
          realTimePersonnelEstimation: '577-648'
        },
        {
          index: 7,
          id: '4151363699296249935',
          address: '灵山大佛景区',
          dailyCumulativePassengerFlow: '2895',
          realTimePersonnelEstimation: '1853-2049'
        },
        {
          index: 8,
          id: '4151391251408924123',
          address: '滨湖万象汇',
          dailyCumulativePassengerFlow: '1839',
          realTimePersonnelEstimation: '1179-1303'
        },
        {
          index: 9,
          id: '4151392007568945142',
          address: '滨湖万达',
          dailyCumulativePassengerFlow: '12094',
          realTimePersonnelEstimation: '5392-5960'
        },
        {
          index: 10,
          id: '4151387952798741050',
          address: '融创文旅城',
          dailyCumulativePassengerFlow: '3854',
          realTimePersonnelEstimation: '1990-2198'
        },
        {
          index: 11,
          id: '4151368350683985397',
          address: '胡埭工业园',
          dailyCumulativePassengerFlow: '13993',
          realTimePersonnelEstimation: '6099-6742'
        },
        {
          index: 12,
          id: '4151391229706477001',
          address: '无锡国家集成电路设计中心',
          dailyCumulativePassengerFlow: '6064',
          realTimePersonnelEstimation: '2652-2932'
        },
        {
          index: 13,
          id: '4151391256147217146',
          address: '湖滨商业街',
          dailyCumulativePassengerFlow: '4051',
          realTimePersonnelEstimation: '2030-2247'
        }
      ]
    }
  },
  watch: {

  },
  mounted() {
    // this.getEcharts()
    this.queryRoadInfo()
    // 获取实时区域人流
    // this.timer = setInterval(() => {
    //   this.queryAreaFlow(this.value1)
    // }, 1000 * 5)
    setTimeout(() => {
      this.queryAreaFlow(this.value1)
    }, 1000 * 5);
  },
  methods: {
    changeDate(val) {
      // 判断选择的时间是否超过48小时
      if (val.length === 2) {
        const startDate = new Date(val[0]);
        const endDate = new Date(val[1]);
        const diffHours = Math.abs(endDate - startDate) / 36e5;
        // console.log(val, diffHours);
        if (diffHours > 48) {
          this.$message.error('选择的时间范围超过了48小时');
          this.value1 = [];
        } else {
          this.queryAreaFlow(val)
        }
      }
    },
    async queryAreaFlow(val) {
      // const dateObj = new Date('2024-03-18 15:07:20')
      // console.log(dateObj.getTime())
      // const currentTime = moment();
      // const lastHourTime = currentTime.clone().subtract(1, 'hours').startOf('hour');
      // const startTimestamp = lastHourTime.unix();
      // const endTimestamp = currentTime.startOf('hour').unix();
      const startTimestamp = moment(val[0]).unix();
      const endTimestamp = moment(val[1]).unix();
      // console.log(startTimestamp, endTimestamp);

      const key = "3N6BZ-SXHE5-UKXI3-IUVN3-II2FJ-2JFIO";

      for (const item of this.tableData2) {
        const params = {
          id: item.id,
          begin: startTimestamp,
          end: endTimestamp,
          interval: 60,
          people_type: "all",
          key: key
        };

        try {
          const res = await axios.post('/txmap/apismapqq/bigdata/realtime/v1.1/population', params);
          if (res.status === 200) {
            item.realTimePersonnelEstimation = res.data.data[0].value + '-' + res.data.data[res.data.data.length - 1].value;
          } else {
            // this.$message.error(res.data.message);
          }
        } catch (error) {
          console.error(error);
          // this.$message.error('An error occurred while fetching data');
        }
      }
    },
    titleClick(v) {
      console.log(v);
      this.titleActiveIndex = v.activeIndex
    },
    queryRoadInfo() {
      // flood  apis
      // let params = {
      //   id: '4151392 007148606284',
      //   begin: 1710115200,
      //   end: 1710118800,
      //   interval: 60,
      //   people_type: 'all',
      //   key: '3N6BZ-SXHE5-UKXI3-IUVN3-II2FJ-2JFIO'
      // }
      // axios.post('https://apis.map.qq.com//bigdata/realtime/v1.1/region', params).then(res => {
      //   if (res.data.code === 200) {
      //     console.log(res.data.data);
      //   } else {
      //     this.$message.error(res.data.message)
      //   }
      // })
      axios.get('/apis/gaoDeApi/queryRoadInfo').then(res => {
        if (res.data.code === 200) {
          this.tableData = res.data.data.map((item, index) => {
            item.index = index + 1
            return item
          })
          console.log(this.tableData);
        } else {
          this.$message.error(res.data.message)
        }
      })
    },
    getEcharts() {
      if (!this.humanTrafficStatisticsEcharts) {
        this.humanTrafficStatisticsEcharts = echarts.init(
          document.getElementById("humanTrafficStatisticsEcharts")
        )
      }
      this.humanTrafficStatisticsEcharts.setOption(this.option);
    }

  },
  beforeDestroy() {
  }
}
</script>

<style lang="scss" >
.RightNew1 {
  width: 2840rem;
  // height: 850rem;
  position: absolute;
  top: 0;
  left: 0;

  .content {
    width: 2840rem;
    height: 1610rem;
    padding: 52rem 88rem;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    display: flex;
    overflow-x: scroll;
    flex-direction: column;
    #humanTrafficStatisticsEcharts {
      width: 100%;
      height: 700rem;
    }
    .bottom {
      .ball {
        display: inline-block;
        width: 100rem;
        height: 100rem;
        border-radius: 50%;
        color: #fff;
        text-align: center;
        line-height: 100rem;
      }
      .color-1 {
        background-color: #ec8a2b;
      }
      .color-2 {
        background-color: #dbb005;
      }
      .color-3 {
        background-color: #52b0dc;
      }
    }
  }
  .el-table th.el-table__cell > .cell {
    padding: 30rem 0 !important;
  }
  .el-table .el-table__cell {
    padding: 30rem 0 !important;
  }
  .el-table .cell {
    padding: 0 !important;
  }
  .el-date-editor .el-range-input {
    font-size: 85rem;
  }
  .el-date-editor .el-range-separator {
    width: 8%;
    line-height: 110rem;
    font-size: 100rem;
    color: #f7f5fb;
  }
  .el-date-editor .el-range__close-icon {
    line-height: 110rem;
  }
}
</style>
