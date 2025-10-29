<template>
  <div class="app-container home">
    <div class="header" v-if="oTopData">
      <div class="header-item _one">
        <!-- <div class="header-item-icon all"></div> -->
        <div class="header-item-box">
          <div class="item-box-name">审批总数</div>
          <div class="" v-if="oTopData">{{ oTopData.all }}</div>
        </div>
      </div>
      <div class="header-item _two">
        <!-- <div class="header-item-icon shenqing"></div> -->
        <div class="header-item-box">
          <div class="item-box-name">申请使用数</div>
          <div class="">{{ oTopData.shenqingshiyong }}</div>
        </div>
      </div>
      <div
        class="header-item _three _change"
        @click="fChangePage('/shenpi/ruzhu')"
      >
        <!-- <div class="header-item-icon ruzhu"></div> -->
        <div class="header-item-box">
          <div class="item-box-name">入驻申请数</div>
          <div class="">{{ oTopData.ruzhu }}</div>
        </div>
      </div>
      <div
        class="header-item _four _change"
        @click="fChangePage('/shenpi/nenglishenpi')"
      >
        <!-- <div class="header-item-icon nengli"></div> -->
        <div class="header-item-box">
          <div class="item-box-name">能力申请数</div>
          <div class="">{{ oTopData.nengli }}</div>
        </div>
      </div>
    </div>
    <!--  -->
    <div class="content">
      <div class="block">
        <div class="block-tit">审核状态分析</div>
        <div class="block-box">
          <div class="echarts" ref="chart1" id="chart1"></div>
        </div>
      </div>
      <div class="block">
        <div class="block-tit">入驻申请分类占比</div>
        <div class="block-box">
          <div class="echarts" ref="chart2" id="chart2"></div>
        </div>
      </div>
      <div class="block">
        <div class="block-tit">入驻申请来源分析</div>
        <div class="block-box">
          <div class="echarts" ref="chart3" id="chart3"></div>
        </div>
      </div>
      <div class="block _Width">
        <div class="block-tit">应用访问量申请量趋势对比(近一个月)</div>
        <div class="block-box">
          <div class="echarts" ref="chart5" id="chart5"></div>
        </div>
      </div>
      <div class="block">
        <div class="block-tit">应用附件下载排行</div>
        <div class="block-box">
          <div class="echarts" ref="chart4" id="chart4"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  cmAbilityGetShouYeShenpiCount, // 首页顶部数据
  cmAbilityGetRuZhufenleiZhanBi, // 首页入驻申请占比分类
  cmAbilityGetRuZhulaiyuanZhanBi, // 首页入驻申请来源分析
  apiGetHomePageAnalysis, // 首页审批状态分析
  gettop10Download, // 下载量top10
  apiGetApplyForEcharts, // 申请量一个月趋势
  apiGetAccessEcharts, // 访问量一月趋势
} from "@/api/management/management"
import * as echarts from "echarts"

export default {
  name: "Index",
  dicts: ["app_module", "sys_user_sex", "app_source", "app_type"],
  data() {
    return {
      // 版本号
      version: "3.8.6",
      oTopData: null,
      chart1: null,
      chart2: null,
      chart3: null,
      chart4: null,
      chart5: null,
    }
  },
  mounted() {
    // console.log(this.dict.type.app_module)

    this.$nextTick(() => {
      // this.initChart()
    })
    this.fGetReuquestTop()
    this.fGetRequestGetHomePageAnalysis()
    this.fGetRequestGetRuZhufenleiZhanBi()
    this.fGetRequestGetRuZhulaiyuanZhanBi()
    this.getXzlTop10()
    this.fGetLineEcharts()
  },
  methods: {
    fGetLineEcharts() {
      let shenqing = apiGetApplyForEcharts() // 申请量一个月趋势
      let fangwen = apiGetAccessEcharts() // 访问量一月趋势
      Promise.all([shenqing, fangwen]).then(([res1, res2]) => {
        console.log(res1, res2)
        let data = {
          name: [],
        }
        res1.data.x.map((item) => {
          data.name.push(item + "日")
        })
        data.value1 = res1.data.y
        data.value2 = res2.data.y
        console.log("折线图", data)
        this.$nextTick(() => {
          this.chart5 = echarts.init(this.$refs.chart5)
          this.setChart5("chart5", data, "审批状态分析")
        })
      })
    },
    fChangePage(url) {
      this.$router.push(url)
    },
    // 下载量top10
    async getXzlTop10() {
      const res = await gettop10Download()
      console.log("下载量top10", res)
      let name = []
      let value = []
      res.data.forEach((item) => {
        name.push(item.app_name)
        value.push(item.counts)
      })
      this.$nextTick(() => {
        this.chart4 = echarts.init(this.$refs.chart4)
        console.log(this.$refs.chart4)
        this.setChart2("chart4", { name, value })
      })
    },
    async fGetReuquestTop() {
      const res = await cmAbilityGetShouYeShenpiCount()
      // console.log(res)
      this.oTopData = res.data[0]
      this.oTopData.all =
        res.data[0].shenqingshiyong + res.data[0].ruzhu + res.data[0].nengli
      this.oTopData.shenqingbfb = (
        (this.oTopData.shenqingshiyong / this.oTopData.all) *
        100
      ).toFixed(2)
      this.oTopData.ruzhubfb = (
        (this.oTopData.ruzhu / this.oTopData.all) *
        100
      ).toFixed(2)
      this.oTopData.nenglifb = (
        (this.oTopData.nengli / this.oTopData.all) *
        100
      ).toFixed(2)
      console.log(this.oTopData)
    },
    async fGetRequestGetHomePageAnalysis() {
      const res = await apiGetHomePageAnalysis()
      // console.log('首页审批状态分析', res)
      let data = []
      res.data.echarts.forEach((item) => {
        data.push({
          name: item.name,
          value: item.num,
        })
      })
      this.$nextTick(() => {
        this.chart1 = echarts.init(this.$refs.chart1)
        this.setChart1("chart1", data, "审批状态分析")
      })
    },
    async fGetRequestGetRuZhufenleiZhanBi() {
      const res = await cmAbilityGetRuZhufenleiZhanBi()
      let data = []
      // this.dict.type.app_module.forEach(item => {
      console.log(res.data, "000")
      res.data.forEach((item) => {
        //if (item.value == obj.module) {
        data.push({
          value: item.counts,
          name: item.name,
        })
        //}
      })
      // })
      this.$nextTick(() => {
        this.chart2 = echarts.init(this.$refs.chart2)
        this.setChart1("chart2", data, "入驻申请分类占比")
      })
    },
    async fGetRequestGetRuZhulaiyuanZhanBi() {
      const res = await cmAbilityGetRuZhulaiyuanZhanBi()
      let data = []
      console.log(this.dict.type.app_source)
      this.dict.type.app_source.forEach((item) => {
        res.data.forEach((obj) => {
          if (item.value == obj.source) {
            console.log(obj)
            data.push({
              value: obj.counts,
              name: item.label,
            })
          }
        })
      })
      console.log(res.data)
      let aNull = res.data.filter((item) => item.source == "无") || []
      if (aNull.length > 0) {
        data.push({
          value: res.data.filter((item) => item.source == "无")[0]?.counts,
          name: "其他",
        })
      }

      this.$nextTick(() => {
        this.chart3 = echarts.init(this.$refs.chart3)
        this.setChart1("chart3", data, "入驻申请来源分析")
      })
    },
    initChart() {},
    setChart1(ref, scaleData, title) {
      console.log(scaleData, "11111")
      var series = []
      var name = []
      var color = ["#2ee48b", "#3d7ef8", "#07bae2", "#f46262"]
      var borderColor = ["#dff9ec", "#e2e8fd", "#cdf1f9", "#fde0e0"]
      var num = 0
      scaleData.forEach((item, idx) => {
        name.push(item.name)
        num += Number(item.value)
        series.push({
          value: item.value,
          name: item.name,
          itemStyle: {
            color: color[idx],
            borderColor: borderColor[idx],
            borderWidth: 5,
          },
        })
      })
      this[ref].setOption({
        title: {
          text: num,
          textStyle: {
            color: "#000",
            fontSize: 18,
            // align: 'center'
          },
          x: "26.5%",
          y: "center",
        },
        legend: {
          orient: "vertical",
          top: "center",
          right: "5%",
          data: name,
          textStyle: {
            color: "#000",
            fontSize: 16,
          },
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)",
        },
        series: [
          {
            name: title,
            type: "pie",
            radius: ["30%", "60%"],
            center: ["30%", "50%"],
            roseType: "radius",
            label: {
              normal: {
                position: "inner",
                formatter: "{c}",
                textStyle: {
                  color: "#fff",
                  fontWeight: "bold",
                  fontSize: 13,
                },
              },
            },
            labelLine: {
              show: false,
              smooth: false,
            },

            data: series,
          },
        ],
      })
    },
    setChart2(ref, aData) {
      // console.log(ref, scaleData)
      var normal = {
        show: true,
        label: {
          show: true,
          textStyle: {
            color: "#676a6c",
            // fontFamily:"PingFang SC"
          },
          position: [0, "-15px"],
          formatter(p) {
            return "{a|" + p.name + "}"
          },
          rich: {
            a: {
              // padding: [0, 0, 0, 20], // 设置内边距为上下4px、左右8px
              fontSize: 14,
            },
          },
        },
        color: function (params) {
          // #73d13d
          if (params.dataIndex == 1) {
            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "#73d13d",
              },
              {
                offset: 1,
                color: "#73d13d",
              },
            ])
          } else if (params.dataIndex == 0) {
            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "#13c2c2",
              },
              {
                offset: 1,
                color: "#13c2c2",
              },
            ])
          } else {
            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "#2f6bff",
              },
              {
                offset: 1,
                color: "#2f6bff",
              },
            ])
          }
        },
        barBorderRadius: 70,
        borderWidth: 0,
        borderColor: "#333",
      }
      this[ref].setOption({
        // backgroundColor: "#003366",
        grid: {
          left: "2%",
          right: "2%",
          bottom: "-9%",
          top: "3%",
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none",
          },
          formatter: function (params) {
            return params[0].name + " : " + params[0].value
          },
        },
        xAxis: {
          show: false,
          type: "value",
        },
        yAxis: [
          {
            type: "category",
            inverse: true,

            axisLabel: {
              show: true,
              textStyle: {
                fontSize: "16",
                color: "#000",
              },
              padding: [-14, 0, 0, 0],
              formatter(value, idx) {
                // console.log(value)
                // if(value.length>3){
                //   value = value.slice(0,3)+"..."
                // }else{
                //   value = value
                // }
                // return idx+1
                if (idx == 0) {
                  return "{one|" + (idx + 1) + "}"
                } else if (idx == 1) {
                  return "{two|" + (idx + 1) + "}"
                } else if (idx == 2) {
                  return "{three|" + (idx + 1) + "}"
                } else {
                  return "{other|" + (idx + 1) + "}"
                }
              },
              rich: {
                one: {
                  color: "#fb6650",
                  fontSize: "16",
                  textShadow: "2px 2px red",
                },
                two: {
                  color: "#4ea5fd",
                  fontSize: "16",
                  textShadow: "2px 2px red",
                },
                three: {
                  color: "#70cf72",
                  fontSize: "16",
                  textShadow: "2px 2px red",
                },
                other: {
                  color: "#000",
                  fontSize: "16",
                  textShadow: "2px 2px red",
                },
              },
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },
            data: aData.name,
          },
          {
            type: "category",
            inverse: true,
            axisTick: "none",
            axisLine: "none",
            show: true,
            axisLabel: {
              textStyle: {
                color: "#000",
                fontSize: "14",
              },
            },
            data: aData.value,
          },
        ],
        series: [
          {
            name: "值",
            type: "bar",
            zlevel: 1,
            itemStyle: {
              normal,
            },

            barWidth: 12,
            barGap: 40,
            data: aData.value,
          },
          // {
          //     name: '背景',
          //     type: 'bar',
          //     barWidth: 20,
          //     barGap: '-100%',
          //     data: salvProMax,
          //     itemStyle: {
          //         normal: {
          //             color: 'rgba(24,31,68,1)',
          //             barBorderRadius: 30,
          //         }
          //     },
          // },
        ],
      })
    },
    setChart5(ref, scaleData) {
      this[ref].setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
          // formatter: function (params) {
          //   let str = "";
          //   str = params[0].name + "日: " + params[0].value + "次";
          //   return str;
          // },
        },
        legend: {
          data: ["申请量", "访问量"],
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          right: "4%",
          textStyle: {
            fontSize: 12,
            color: "#292f39",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: "#57617B",
              },
            },
            axisLabel: {
              formatter: "{value}",
              textStyle: {
                color: "#333",
              },
            },
            data: scaleData.name,
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "申请量",
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#57617B",
              },
            },
            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 14,
              },
            },
            splitLine: {
              lineStyle: {
                color: "#57617B",
              },
            },
          },
          {
            type: "value",
            name: "访问量",
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#57617B",
              },
            },
            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 14,
              },
            },
            splitLine: {
              lineStyle: {
                color: "#57617B",
              },
            },
          },
        ],
        series: [
          {
            name: "申请量",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 3,
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  1,
                  0,
                  [
                    {
                      offset: 0,
                      color: "rgba(80, 119, 239, 0.6)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(155, 176, 239, 0.6)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "#5077EF",
              },
              emphasis: {
                color: "rgb(0,196,132)",
                borderColor: "rgba(0,196,132,0.2)",
                extraCssText: "box-shadow: 8px 8px 8px rgba(0, 0, 0, 1);",
                borderWidth: 10,
              },
            },
            data: scaleData.value1,
          },
          {
            name: "访问量",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 3,
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  1,
                  0,
                  [
                    {
                      offset: 0,
                      color: "rgba(244, 98, 98, 0.6)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(244, 168, 168, 0.6)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "#F46262",
              },
              emphasis: {
                color: "rgb(0,196,132)",
                borderColor: "rgba(0,196,132,0.2)",
                extraCssText: "box-shadow: 8px 8px 8px rgba(0, 0, 0, 1);",
                borderWidth: 10,
              },
            },
            data: scaleData.value2,
          },
        ],
      })
    },
    goTarget(href) {
      window.open(href, "_blank")
    },
  },
}
</script>

<style scoped lang="scss">
.home {
  box-sizing: border-box;
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 30px;
}

.header-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 0 0 200px;
  width: 22%;
  height: 150px;
  color: #fff;
  font-size: 30px;
  font-weight: bold;
}

._one {
  background: url("../assets/images/index_1.png") no-repeat center / 100% 100%;
}

._two {
  background: url("../assets/images/index_2.png") no-repeat center / 100% 100%;
}

._three {
  background: url("../assets/images/index_3.png") no-repeat center / 100% 100%;
}

._four {
  background: url("../assets/images/index_4.png") no-repeat center / 100% 100%;
}

._change {
  cursor: pointer;
}

.header-item-icon {
  width: 70px;
  height: 70px;
  // background-color: orangered;
}

.item-box-name {
  // margin-bottom: 20px;
  font-size: 20px;
  font-weight: 500;
}

.content {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
}

.block {
  width: 32%;
  height: 400px;
  box-shadow: 0px 10px 15px #c9d4d0;
  margin-bottom: 20px;
}

._Width {
  width: 65% !important;
}

.block-tit {
  height: 50px;
  font-size: 20px;
  font-weight: bold;
  line-height: 50px;
  text-indent: 2rem;
  background: url("../assets/images/tit_bg4.png") no-repeat center / 100% 100%;
}

.block-box {
  width: 100%;
  height: calc(400px - 50px);
  padding: 10px;
  box-sizing: border-box;
}

.echarts {
  width: 100%;
  height: 100%;
}
</style>
