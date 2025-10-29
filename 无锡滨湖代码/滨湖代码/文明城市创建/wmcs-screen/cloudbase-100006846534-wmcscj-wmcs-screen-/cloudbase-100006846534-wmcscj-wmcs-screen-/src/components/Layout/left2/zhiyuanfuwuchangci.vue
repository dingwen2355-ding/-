<script setup>
import { ref, onMounted } from "vue"
import * as echarts from "echarts"
import axios from "axios";
import {
    colors, lineStyleColor, legendStyleSize, legendStyleColor, legendStyleWeight, lineStyleWidth, labelStyleColor, labelStyleSize, labelStyleSizeS
    , elineSymbolSize, elineSymbol, labelStyleWeight
} from "@/utils/jc-echarts/EchartsConfig"
onMounted(() => {
    loadChart()
})
function loadChart() {
    $axios_get(IP + "/screen/api/civilization/activityCount").then(res => {
        let data = res.data.data;
        let xData = [];
        let yData = [];
        data.forEach(e => {
            xData.push(e.type)
            yData.push(e.count)
        })
        var option = {
            grid: {
                left: "5%",
                right: "3%",
                bottom: "3%",
                top: "3%",
                containLabel: true,
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
                        align: "left",
                        padding: [0, 0, 0, -100],
                        textStyle: {
                            color: labelStyleColor,
                            fontSize: labelStyleSizeS,
                            fontWeight: labelStyleWeight
                        },
                        rich: {
                            a1: {
                                width: 28,
                                fontSize: labelStyleSize,
                                align: "center",
                                backgroundColor: {
                                    image: new URL("@/assets/img/top0.png", import.meta.url).href
                                }
                            },
                            a2: {
                                backgroundColor: {
                                    image: new URL("@/assets/img/top1.png", import.meta.url).href
                                },
                                fontSize: labelStyleSize,
                                width: 28,
                                align: "center"
                            },
                            a3: {
                                backgroundColor: {
                                    image: new URL("@/assets/img/top2.png", import.meta.url).href
                                },
                                fontSize: labelStyleSize,
                                width: 28,
                                align: "center"
                            },
                            a4: {
                                backgroundColor: {
                                    image: new URL("@/assets/img/top3.png", import.meta.url).href
                                },
                                fontSize: labelStyleSize,
                                width: 28,
                                align: "center"
                            }
                        },
                        formatter: function (params, i) {
                            let index = i + 1;
                            if (index == 1) {
                                return "{a1|" + index + "}" + " " + params;
                            } else if (index == 2) {
                                return "{a2|" + index + "}" + " " + params;
                            } else if (index == 3) {
                                return "{a3|" + index + "}" + " " + params;
                            } else {
                                return "{a4|" + index + "}" + " " + params;
                            }

                        }
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
                    data: xData,
                },
                {
                    type: "category",
                    inverse: true,
                    axisTick: "none",
                    axisLine: "none",
                    show: true,
                    axisLabel: {
                        textStyle: {
                            color: "#ffffff",
                            fontSize: 16,
                        },
                        formatter: function (value) {
                            if (value >= 10000) {
                                return (value / 10000).toLocaleString() + "万";
                            } else {
                                return value.toLocaleString();
                            }
                        },
                    },
                    data: yData,
                },
            ],
            series: [
                {
                    name: "场次",
                    type: "bar",
                    zlevel: 1,
                    itemStyle: {
                        normal: {
                            barBorderRadius: 30,
                            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                                {
                                    offset: 0,
                                    color: "rgb(57,89,255,1)",
                                },
                                {
                                    offset: 1,
                                    color: "rgb(46,200,207,1)",
                                },
                            ]),
                        },
                    },
                    barWidth: 10,
                    data: yData,
                },
                // {
                //     name: "背景",
                //     type: "bar",
                //     barWidth: 10,
                //     barGap: "-100%",
                //     data: [500, 500, 500, 500, 500, 500, 500],
                //     itemStyle: {
                //         normal: {
                //             color: "rgba(24,31,68,1)",
                //             barBorderRadius: 30,
                //         },
                //     },
                // },
            ],
        };
        var chartDom = document.getElementById('zyfwcc');
        var myChart = echarts.init(chartDom);
        myChart.setOption(option);
    })

}
</script>
<template>
    <div id="zyfwcc"></div>
</template>
<style scoped lang="scss">
#zyfwcc {
    width: 100%;
    height: 190px;
}
</style>