<template>
    <div class="echarts"></div>
</template>
<script>
import { fInitEcharts, oChartColorConf } from "@/utils/fInitCharts.js";
import * as echarts from 'echarts'
const fGaugeOption = function (aData, oData) {
    // console.log(aData,111)
    let name = ['社保就业', '财政金融', '城建住房', '生活服务', '生态环境', '信用服务', '公共安全', '教育文化']
    let colorArray = []
    let value = [10000, 9830, 8749, 7492, 7422, 6492, 6318, 3819]
    name.forEach((item, idx) => {
        if (idx < 3) {
            colorArray.push({
                top: '#d54023',
                bottom: '#efb53e'
            })
        } else {
            colorArray.push({
                top: '#6dedd0',
                bottom: '#419cf2'
            })
        }
    })
    // console.log(colorArray)
    // alert(111)
    return {
        // backgroundColor: "#003366",
        grid: {
            left: '2%',
            right: '2%',
            bottom: '2%',
            top: '2%',
            containLabel: true
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
                textStyle: {
                    fontSize: '18',
                    color: '#000'
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
            data: name
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
            data: value
        }],
        series: [{
            name: '值',
            type: 'bar',
            zlevel: 1,
            itemStyle: {
                normal: {
                    show: true,
                    color: function (params) {
                        let num = colorArray.length;
                        return {
                            type: 'linear',
                            colorStops: [{
                                offset: 0,
                                color: colorArray[params.dataIndex % num].bottom
                            }, {
                                offset: 1,
                                color: colorArray[params.dataIndex % num].top
                            }, {
                                offset: 0,
                                color: colorArray[params.dataIndex % num].bottom
                            }, {
                                offset: 1,
                                color: colorArray[params.dataIndex % num].top
                            }, {
                                offset: 0,
                                color: colorArray[params.dataIndex % num].bottom
                            }, {
                                offset: 1,
                                color: colorArray[params.dataIndex % num].top
                            }, {
                                offset: 0,
                                color: colorArray[params.dataIndex % num].bottom
                            }, {
                                offset: 1,
                                color: colorArray[params.dataIndex % num].top
                            }, {
                                offset: 0,
                                color: colorArray[params.dataIndex % num].bottom
                            }, {
                                offset: 1,
                                color: colorArray[params.dataIndex % num].top
                            }, {
                                offset: 0,
                                color: colorArray[params.dataIndex % num].bottom
                            }, {
                                offset: 1,
                                color: colorArray[params.dataIndex % num].top
                            }],
                            //globalCoord: false
                        }
                    },
                    barBorderRadius: 70,
                    borderWidth: 0,
                    borderColor: '#333',
                }
            },
            barWidth: 20,
            data: value
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
        this.oChartApi = fInitEcharts($(this.$el), {
            ...this.opts,
            listenResize: false,
            optionFactory: fGaugeOption() //生成绘图option的自定义方法
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
