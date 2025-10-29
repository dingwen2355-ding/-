import {
    colors, lineStyleColor,lineStyleColor90, legendStyleSize, legendStyleColor, legendStyleWeight, lineStyleWidth, labelStyleColor, labelStyleSize
    , elineSymbolSize, elineSymbol, labelStyleWeight
} from "./EchartsConfig"
import * as echarts from "echarts"

export const EchartsBarRow = (params) => {

    // params = {
    //     el: "",
    //     padding: ["3%", "3%", "3%", "3%"],
    //     legend: {
    //         show: true,
    //         left: "center", // 'left', 'center', 'right'
    //         top: "top", // 'top','middle','bottom'
    //         gap: 25,
    //         icon: "roundRect", // 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
    //         data: [],
    //     },
    //     x: {
    //         show: true,
    //         name: "",
    //         min: 0,
    //         max: 0,
    //         rotate: "0",
    //         data: []
    //     },
    //     y: {
    //         show: true,
    //         name: "",
    //         min: 0,
    //         max: 0,
    //         rotate: "0"
    //     },
    //     series: [
    //         {
    //             name: "",
    //             barWidth: "20%",
    //             label: {
    //                 show: false,
    //             },
    //             data: []
    //         }
    //     ]
    // }

    let option = {
        color: colors,
        tooltip: {
            trigger: "axis",
            axisPointer: {
                lineStyle: {
                    color: "#ddd",
                },
            },
            backgroundColor: "rgba(17,95,182,0.8)",
            textStyle: {
                color: "#fff",
            },
            extraCssText: "box-shadow: 0 0 3px rgba(0,0,0,0.3)",
        },
        grid: {
            top: params.padding[0],
            right: params.padding[1],
            bottom: params.padding[2],
            left: params.padding[3],
            containLabel: true,
        },
        legend: {
            show: params.legend.show,
            icon: params.legend.icon || "roundRect",
            left: params.legend.left || "center",
            top: params.legend.top || "top",
            data: params.legend.data,
            itemGap: params.legend.gap || 25,
            textStyle: {
                color: legendStyleColor,
                fontSize: legendStyleSize,
                fontWeight: legendStyleWeight,
            }
        },
        xAxis: {
            show: params.x.show,
            type: "category",
            name: params.x.name,
            min: params.x.min,
            max: params.x.max,
            axisLine: {
                show: true,
                lineStyle: {
                    color: lineStyleColor90,
                    width: lineStyleWidth,
                }
            },
            axisTick: { show: false },
            axisLabel: {
                show: true,
                rotate: params.x.rotate || 0,
                color: labelStyleColor,
                fontSize: labelStyleSize,
                rotate: params.x.rotate || 0,
            },
            splitLine: {
                show: false,
                lineStyle: {
                    color: lineStyleColor,
                    width: lineStyleWidth,
                }
            },
            data: params.x.data
        },
        yAxis: {
            show: params.y.show,
            type: "value",
            name: params.y.name,
            min: params.y.min,
            max: params.y.max,
            axisLine: {
                show: true,
                lineStyle: {
                    color: lineStyleColor,
                    width: lineStyleWidth,
                }
            },
            axisTick: { show: false },
            axisLabel: {
                show: true,
                rotate: params.y.rotate,
                color: labelStyleColor,
                fontSize: labelStyleSize,
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: lineStyleColor,
                    width: lineStyleWidth,
                }
            }
        },
        series: []
    }
    params.series.forEach((element, index) => {
        option.series.push({
            name: element.name,
            type: "bar",
            barWidth: element.barWidth,
            lineStyle: {
                normal: {
                    color: "#0085FF", // 线条颜色
                },
                borderColor: "#f0f",
            },
            label: {
                show: element.label.show,
                position: "top",
                color: labelStyleColor,
                fontSize: labelStyleSize,
                fontWeight: labelStyleWeight
            },
            itemStyle: {
                //线性渐变，前4个参数分别是x0,y0,x2,y2(范围0~1);相当于图形包围盒中的百分比。如果最后一个参数是‘true’，则该四个值是绝对像素位置。
                color: element.normalColor ? colors[index] : new echarts.graphic.LinearGradient(0, 0, 0, 1,
                    [
                        {
                            offset: 0,
                            color: colors[index],
                        },
                        {
                            offset: 1,
                            color: "rgba(0,0,0, 0)",
                        },
                    ]
                ),
            },
            data: element.data,
        })
    });
    var chartDom = document.getElementById(params.el);
    echarts.dispose(chartDom);
    var myChart = echarts.init(chartDom);
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    myChart.on("click",function (params) {
        console.log(params);
    })
    window.addEventListener("resize", () => {
        myChart.resize();
    });
    if (params.autoplay) {
        handleChartLoop(option, myChart)
    }
    return myChart;
}
// 饼图自动轮播
function handleChartLoop(option, myChart) {
    if (!myChart) {
        return
    }
    let currentIndex = -1 // 当前高亮图形在饼图数据中的下标
    let changePieInterval = setInterval(selectPie, 3000) // 设置自动切换高亮图形的定时器

    // 取消所有高亮并高亮当前图形
    function highlightPie() {
        // 遍历饼图数据，取消所有图形的高亮效果
        for (var idx in option.series[0].data) {
            myChart.dispatchAction({
                type: 'downplay',
                seriesIndex: 0,
                dataIndex: idx
            })
        }
        // 高亮当前图形
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
    }

    // 用户鼠标悬浮到某一图形时，停止自动切换并高亮鼠标悬浮的图形
    myChart.on('mouseover', (params) => {
        clearInterval(changePieInterval)
        currentIndex = params.dataIndex
        highlightPie()
    })

    // 用户鼠标移出时，重新开始自动切换
    myChart.on('mouseout', (params) => {
        if (changePieInterval) {
            clearInterval(changePieInterval)
        }
        changePieInterval = setInterval(selectPie, 3000)
    })

    // 高亮效果切换到下一个图形
    function selectPie() {
        var dataLen = option.series[0].data.length
        currentIndex = (currentIndex + 1) % dataLen
        highlightPie()
    }
}
