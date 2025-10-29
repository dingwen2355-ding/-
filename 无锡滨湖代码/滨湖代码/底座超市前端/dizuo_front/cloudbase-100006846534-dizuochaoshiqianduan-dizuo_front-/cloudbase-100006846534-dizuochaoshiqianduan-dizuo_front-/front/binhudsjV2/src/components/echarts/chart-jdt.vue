<template>
    <div class="echarts"></div>
</template>
<script>
import { fInitEcharts, oChartColorConf } from "../utils/fInitCharts.js";
import * as echarts from 'echarts'
const fGaugeOption = function (aData, oData) {
    // console.log(aData)
    var normal = {
        barBorderRadius: 30,
        show: true,
        label: {
          "show": true,
          "textStyle": {
            "color": "#676a6c",
            // fontFamily:"PingFang SC"
          },
          "position": [0,'-14px'],
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
        color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [{
            offset: 0,
            color: 'rgb(57,89,255,1)'
        }, {
            offset: 1,
            color: 'rgb(46,200,207,1)'
        }]),
    }
    if (oData.color) {
        normal = {
            show: true,
            label: {
          "show": true,
          "textStyle": {
            "color": "#676a6c",
            // fontFamily:"PingFang SC"
          },
          "position": [0,'-14px'],
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
    }
    return {
        // backgroundColor: "#003366",
        grid: {
          left: "2%",
          right: "2%",
          bottom: "-2%",
          top: "1%",
          containLabel: true,
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'none'
            },
            formatter: function (params) {
                return params[0].name + ' : ' + params[0].value
            }
        },
        xAxis: {
            show: false,
            type: 'value'
        },
        yAxis: [{
            type: 'category',
            inverse: true,
            axisLabel: {
                show: true,
                padding:[-20,0,0,0],
                textStyle: {
                    fontSize: '18',
                    color: '#000'
                },
                formatter(value, idx) {
                    // console.log(value, idx)
                    // return idx+1
                    if (idx == 0) {
                        return '{one|' + (idx + 1) + '}'
                    } else if (idx == 1) {
                        return '{two|' + (idx + 1) + '}'
                    }
                    else if (idx == 2) {
                        return '{three|' + (idx + 1) + '}'
                    }
                    else {
                        return '{other|' + (idx + 1) + '}'
                    }
                },
                rich: {
                    one: {
                        color: "#fb6650",
                        fontSize: '24',
                        textShadow: '2px 2px red',
                    },
                    two: {
                        color: "#4ea5fd",
                        fontSize: '24',
                        textShadow: '2px 2px red',
                    },
                    three: {
                        color: "#70cf72",
                        fontSize: '24',
                        textShadow: '2px 2px red',
                    },
                    other: {
                        color: "#000",
                        fontSize: '24',
                        textShadow: '2px 2px red',
                    },
                },
            },
            splitLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            data: aData.name
        }, {
            type: 'category',
            inverse: true,
            axisTick: 'none',
            axisLine: 'none',
            show: true,
            axisLabel: {
                textStyle: {
                    color: '#000',
                    fontSize: '18'
                },
            },
            data: aData.value
        }],
        series: [{
            name: '值',
            type: 'bar',
            zlevel: 1,
            itemStyle: {
                normal,
            },
            barWidth: 16,
            data: aData.value
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
        ]
    };
};

export default {
    // 组件参数 接收来自父组件的数据
    props: {
        opts: Object
    },
    // 局部注册的组件
    components: {},
    // 组件状态值
    data() {
        return {
            oChartApi: null
        };
    },
    mounted() {
      console.info("opts:...")
        this.oChartApi = fInitEcharts($(this.$el), {
            ...this.opts,
            listenResize: false,
            optionFactory: fGaugeOption //生成绘图option的自定义方法
        });
    },
    beforeDestroy() {
        this.oChartApi.fDestroy();
    },
    // 组件方法
    methods: {
        fSetData(aData, oData) {
            this.oChartApi.fSetData(aData, oData);
        }
    }
};
</script> 

<style scoped lang='less'>
.echarts {
    width: 100%;
    height: 100%;
    padding: 30px 30px 0;
}
</style>
