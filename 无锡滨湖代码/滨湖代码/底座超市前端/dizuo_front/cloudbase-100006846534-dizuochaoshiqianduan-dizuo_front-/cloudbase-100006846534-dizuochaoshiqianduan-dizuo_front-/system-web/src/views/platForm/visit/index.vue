<template>
  <div class="appwrap">
    <el-row class="header" v-if="oData">
      <el-col :span="5" class="bg one">
        <span>
          <p>总访问量</p>
          <p>{{ oData.all }}</p>
        </span>
      </el-col>
      <el-col :span="5" class="bg two">
        <span>
          <p>平台访问量</p>
          <p>{{ oData['平台'] }}</p>
        </span>
      </el-col>
      <el-col :span="5" class="bg three">
        <span>
          <p>组件访问量</p>
          <p>{{ oData['组件'] }}</p>
        </span>
      </el-col>
      <el-col :span="5" class="bg four">
        <span>
          <p>工具访问量</p>
          <p>{{ oData['工具'] }}</p>
        </span>
      </el-col>
    </el-row>
    <el-row class="middle">
      <el-col :span="6">
        应用分类:
        <el-select v-model="application" placeholder="请选择应用分类" @change="chooseapplication">
          <el-option v-for="item in dict.type.app_module" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
        <!-- <el-input
            v-model="sAppName"
            placeholder="请输入应用名称/APP ID"
            style="width: 70%"
          ></el-input> -->
      </el-col>
      <el-col :span="6">
        应用来源:
        <el-select v-model="source" placeholder="请选择应用来源" @change="choosesourse">
          <el-option v-for="item in dict.type.app_source" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-col>
      <!-- <el-col :span="8">
        统计时间:
        <el-date-picker value-format="yyyy-MM-dd" style="width: 70%" v-model="time" type="daterange" range-separator="-"
          start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-col> -->
      <el-col :span="12">
        <el-button type="primary" @click="search()">查询</el-button>
        <el-button @click="reset()">重置</el-button>
      </el-col>
    </el-row>
    <div class="echarts-top">
      <div class="block">
        <div class="block-tit">场景分类对比</div>
        <div class="block-box">
          <div class="echarts" ref="chart1"></div>
        </div>
      </div>
      <div class="block">
        <div class="block-tit">访问量top8</div>
        <div class="block-box">
          <div class="echarts" ref="chart2"></div>
        </div>
      </div>
    </div>
    <div class="echarts-bottom">
      <div class="block-bottom">
        <div class="block-tit">平台最近一个月申请量趋势分析</div>
        <div class="block-box">
          <div class="echarts" ref="chart3"></div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";
import {
  apiGetfangwenChangjingZhanbi,//
  cmAbilityGetfangwenliangtongji,//
  apiGetAccessStatistics,// 申请量top8
  apiGetAccessEcharts, apiGetApplyForStatistics,// 申请量一个月
} from "@/api/management/management";
export default {
  name: "",
  dicts: ["app_module", "app_source"],
  components: {},
  data() {
    return {
      requestParams: {
        "appName": "",
        "appModule": "",
        "appType": "",
        "source": "",
        "recommendFlag": "",
        "status": ""
      },
      application: "",
      source: "",
      time: "",
      oData: null,
      chart1: null,
      chart2: null,
    };
  },
  mounted() {
    this.fGetCjfl();
    this.fGetSqltj();
    this.getSqlTop8()
    this.getSqlMonth()
    this.$nextTick(() => {
      this.initChart();
    });
  },
  methods: {
    // 申请量一个月
    async getSqlMonth() {
      const res = await apiGetAccessEcharts();
      console.log('申请量一个月', res)
      this.chart3 = echarts.init(this.$refs.chart3);
      // var scaleData3 = {
      //   xData: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
      //   data: [220, 182, 191, 134, 150, 120, 110, 125, 235, 137, 432, 375],
      // };
      this.setChart3("chart3", {
        xData: res.data.x,
        data: res.data.y,
      });

    },
    // 数组排序
    fSetDataSort(attr, rev) {
      //第二个参数没有传递 默认升序排列
      if (rev == undefined) {
        rev = 1;
      } else {
        rev = (rev) ? 1 : -1;
      }
      return function (a, b) {
        a = a[attr];
        b = b[attr];
        if (a < b) {
          return rev * -1;
        }
        if (a > b) {
          return rev * 1;
        }
        return 0;
      }
    },
    async getSqlTop8() {
      const res = await apiGetAccessStatistics();
      res.data.forEach(item => {
        item.applyForNum = Number(item['number'])
      })
      let arr = res.data.sort(this.fSetDataSort('number', false))
      let name = []
      let value = []
      arr.forEach((item, idx) => {
        if (idx < 8) {
          name.push(item.name)
          value.push(item.applyForNum)
        }
      })
      this.chart2 = echarts.init(this.$refs.chart2);
      this.setChart2("chart2", { name, value });

    },
    async fGetCjfl() {
      const res = await apiGetfangwenChangjingZhanbi(this.requestParams);
      // console.log(res, '场景分类对比')
      let data = []
      for (const key in res.data) {
        data.push({
          name: key,
          value: res.data[key]
        })
      }
      this.chart1 = echarts.init(this.$refs.chart1);
      this.setChart1("chart1", data);


    },
    async fGetSqltj() {
      const res = await cmAbilityGetfangwenliangtongji();
      console.log(res.data, '顶部')
      this.oData = res.data
      this.oData.all = Number(res.data['平台']) + Number(res.data['工具']) + Number(res.data['组件'])
      console.log(res, '申请量统计')
    },
    chooseapplication(e) {
      // console.log(e)
      this.requestParams.appModule = e
    },
    choosesourse(e) {
      // console.log(e)
      this.requestParams.source = e
    },
    search() {
      this.fGetCjfl();
      this.fGetSqltj();
      this.getSqlTop8()
      this.getSqlMonth()
    },
    reset() {
      this.requestParams = {
        "appName": "",
        "appModule": "",
        "appType": "",
        "source": "",
        "recommendFlag": "",
        "status": ""
      }
      this.source=""
      this.application=""
      this.search()
    },
    initChart() {
      this.chart1 = echarts.init(this.$refs.chart1);
      this.chart2 = echarts.init(this.$refs.chart2);
      this.chart3 = echarts.init(this.$refs.chart3);
      var scaleData = [
        {
          name: "数字政务",
          value: 1230,
        },
        {
          name: "数字经济",
          value: 325,
        },
        {
          name: "数字生态",
          value: 635,
        },
        {
          name: "数字治理",
          value: 1242,
        },
        {
          name: "数字生活",
          value: 3254,
        },
        {
          name: "数字基建",
          value: 574,
        },
      ];
      var scaleData2 = {
        name: [
          "应用1",
          "应用2",
          "应用3",
          "应用4",
          "应用5",
          "应用6",
          "应用7",
          "应用8",
        ],
        value: [3000, 2890, 1309, 1273, 1073, 980, 782, 642],
      };
      var scaleData3 = {
        xData: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
        data: [220, 182, 191, 134, 150, 120, 110, 125, 235, 137, 432, 375],
      };
      this.setChart1("chart1", scaleData);
      //this.setChart2("chart2", scaleData2);
      this.setChart3("chart3", scaleData3);
    },
    setChart1(ref, scaleData) {
      var series = []
      var name = []
      var color = ['#2ee48b', '#3d7ef8', '#07bae2', '#f46262', '#877cfb', '#fad93b']
      var borderColor = ['#dff9ec', '#e2e8fd', '#cdf1f9', '#fde0e0', '#e7e5fe', '#fef7d8']
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
          }
        })
      })
      this[ref].setOption({
        // title: {
        //   text: num,
        //   textStyle: {
        //     color: '#000',
        //     fontSize: 30,
        //     // align: 'center'
        //   },
        //   x: 'center',
        //   y: 'center',
        // },
        legend: {
          orient: 'vertical',
          top: "center",
          right: "5%",
          data: name,
          textStyle: {
            color: "#676a6c",
            fontSize: 16
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b} : {c} ({d}%)'
        },
        series: [{
          // name: title,
          type: 'pie',
          radius: ['30%', '60%'],
          center: ['30%', '50%'],
          roseType: 'radius',
          label: {
            normal: {
              position: 'inner',
              formatter: '{c}',
              textStyle: {
                color: '#fff',
                fontWeight: 'bold',
                fontSize: 13
              }
            }
          },
          labelLine: {
            show: false,
            smooth: false
          },

          data: series
        }]
      })
      // var rich = {
      //   white: {
      //     color: "#000",
      //     align: "center",
      //     padding: [3, 0],
      //   },
      // };
      // var placeHolderStyle = {
      //   normal: {
      //     label: {
      //       show: false,
      //     },
      //     labelLine: {
      //       show: false,
      //     },
      //     // color: 'rgba(0, 0, 0, 0)',
      //     // borderColor: 'rgba(0, 0, 0, 0)',
      //     borderWidth: 1,
      //   },
      // };
      // var data = [];
      // var color = [
      //   "#00ffff",
      //   "#00cfff",
      //   "#006ced",
      //   "#ffe000",
      //   "#ffa800",
      //   "#ff5b00",
      //   "#ff3000",
      // ];
      // for (var i = 0; i < scaleData.length; i++) {
      //   data.push({
      //     value: scaleData[i].value,
      //     name: scaleData[i].name,
      //     itemStyle: {
      //       normal: {
      //         borderWidth: 4,
      //       },
      //     },
      //   });
      // }
      // var seriesObj = [
      //   {
      //     name: "场景分类",
      //     type: "pie",
      //     clockWise: false,
      //     radius: [70, 135],
      //     hoverAnimation: false,
      //     // minAngle:15,
      //     itemStyle: {
      //       normal: {
      //         label: {
      //           show: true,
      //           position: "outside",
      //           color: "#000",
      //           alignTo:'edge',
      //           formatter: function (params) {
      //             var percent = 0;
      //             var total = 0;
      //             for (var i = 0; i < scaleData.length; i++) {
      //               total += scaleData[i].value;
      //             }
      //             percent = params.value ? ((params.value / total) * 100).toFixed(0) : 0;
      //             if (params.name !== "") {
      //               return (
      //                 params.name +
      //                 "\n{white|" +
      //                 params.value +
      //                 "," +
      //                 percent +
      //                 "%}"
      //               );
      //             } else {
      //               return "";
      //             }
      //           },
      //           rich: rich,
      //         },
      //         labelLine: {
      //           length: 30,
      //           length2: 60,
      //           show: true,
      //           color: "#00ffff",
      //         },
      //       },
      //     },
      //     data: data,
      //   },
      // ];
      // this[ref].setOption({
      //   tooltip: {
      //     show: true,
      //   },
      //   legend: {
      //     show: false,
      //   },
      //   toolbox: {
      //     show: false,
      //   },
      //   series: seriesObj,
      // });
    },
    setChart2(ref, scaleData) {
      var normal = {
        show: true,
        label: {
          "show": true,
          "textStyle": {
            "color": "#676a6c",
            // fontFamily:"PingFang SC"
          },
          "position": [0,'-18px'],
          formatter(p) {
            return '{a|' + p.name + '}';
          },
          rich: {
            a: {
              // padding: [0, 0, 0, 20], // 设置内边距为上下4px、左右8px
              fontSize: 14,
            }
          }
        },
        color: function (params) {
          // #73d13d
          if (params.dataIndex == 1) {
            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
              offset: 0,
              color: '#73d13d'
            }, {
              offset: 1,
              color: '#73d13d'
            }])
          } else if (params.dataIndex == 0) {
            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
              offset: 0,
              color: '#13c2c2'
            }, {
              offset: 1,
              color: '#13c2c2'
            }])
          }
          else {
            return new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
              offset: 0,
              color: '#2f6bff'
            }, {
              offset: 1,
              color: '#2f6bff'
            }])
          }
        },
        barBorderRadius: 70,
        borderWidth: 0,
        borderColor: '#333',
      }
      this[ref].setOption({
        // backgroundColor: "#003366",
        grid: {
          left: "2%",
          right: "2%",
          bottom: "-6%",
          top: "4%",
          containLabel: true,
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "none",
          },
          formatter: function (params) {
            return params[0].name + " : " + params[0].value;
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
                fontSize: '16',
                color: '#000'
              },
              padding:[-14,0,0,0],
              formatter(value, idx) {
                // return idx+1
                // if(value.length>3){
                //   value = value.slice(0,3)+"..."
                // }else{
                //   value = value
                // }
                if (idx == 0) {
                  return '{one|' + (idx+1) + '}'
                } else if (idx == 1) {
                  return '{two|' + (idx+1) + '}'
                }
                else if (idx == 2) {
                  return '{three|' + (idx+1) + '}'
                }
                else {
                  return '{other|' + (idx+1) + '}'
                }
              },
              rich: {
                one: {
                  color: "#fb6650",
                  fontSize: '16',
                  textShadow: '2px 2px red',
                },
                two: {
                  color: "#4ea5fd",
                  fontSize: '16',
                  textShadow: '2px 2px red',
                },
                three: {
                  color: "#70cf72",
                  fontSize: '16',
                  textShadow: '2px 2px red',
                },
                other: {
                  color: "#000",
                  fontSize: '16',
                  textShadow: '2px 2px red',
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
            data: scaleData.name,
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
            data: scaleData.value,
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
            // barGap: '-100%',
            data: scaleData.value,
            // barBorderRadius: 30,
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
          //
          //         }
          //     },
          // },
        ],
      });
    },
    setChart3(ref, scaleData) {
      this[ref].setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
          formatter: function (params) {
            let str = "";
            str = params[0].name + "日: " + params[0].value + "次";
            return str;
          },
        },
        legend: {
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
              formatter: "{value}日",
              textStyle: {
                color: "#333",
              },
            },
            data: scaleData.xData,
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "访问量（次）",
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
            name: "",
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
                      color: "rgba(16,97,204, 0.6)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(17,235,210, 0.6)",
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
                color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                  {
                    offset: 0,
                    color: "rgba(16,97,204,1)",
                  },
                  {
                    offset: 1,
                    color: "rgba(17,235,210,1)",
                  },
                ]),
              },
              emphasis: {
                color: "rgb(0,196,132)",
                borderColor: "rgba(0,196,132,0.2)",
                extraCssText: "box-shadow: 8px 8px 8px rgba(0, 0, 0, 1);",
                borderWidth: 10,
              },
            },
            data: scaleData.data,
          },
        ],
      });
    },
  },

};
</script>
<style lang="scss" scoped>
.appwrap {
  height: 100%;
  width: 100%;
  padding: 30px;
  color:#676a6c
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.bg {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 0 0 200px;
  height: 120px;
  color: #fff;
  font-size: 20px;

  img {
    width: 70px;
    height: 70px;
  }
}

.one {
  background: url('../../../assets/images/fangwen_1.png') no-repeat center / 100% 100%;
}

.two {
  background: url('../../../assets/images/fangwen_2.png') no-repeat center / 100% 100%;
}

.three {
  background: url('../../../assets/images/fangwen_3.png') no-repeat center / 100% 100%;
}

.four {
  background: url('../../../assets/images/fangwen_4.png') no-repeat center / 100% 100%;
}

.middle {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 30px;
  padding-top: 20px;
  border-top: 10px solid #f2f2f2;
}

.echarts-top {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.block {
  width: 48%;
  height: 400px;
  box-shadow: 0px 10px 15px #c9d4d0;
  margin-bottom: 20px;
}

.block-tit {
  height: 50px;
  font-size: 20px;
  font-weight: bold;
  line-height: 50px;
  text-indent: 2rem;
  background: url('../../../assets/images/tit_bg4.png') no-repeat center / 100% 100%;
}

.block-box {
  width: 100%;
  height: calc(400px - 40px);
  padding: 10px;
  box-sizing: border-box;
}

.echarts {
  width: 100%;
  height: 100%;
}

.block-bottom {
  width: 100%;
  height: 400px;
  box-shadow: 0px 10px 15px #c9d4d0;
  margin-bottom: 20px;

  .block-tit {
    height: 50px;
    font-size: 20px;
    font-weight: bold;
    line-height: 50px;
    text-indent: 2rem;
    background: url('../../../assets/images/tit_bg4.png') no-repeat center / 100% 100%;
  }

  .block-box {
    width: 100%;
    height: calc(400px - 40px);
    padding: 10px;
    box-sizing: border-box;
  }

  .echarts {
    width: 100%;
    height: 100%;
  }
}
</style>
