import {
    colors, lineStyleColor, legendStyleSize, legendStyleColor, legendStyleWeight, lineStyleWidth, labelStyleColor, labelStyleSize
    , elineSymbolSize, elineSymbol, labelStyleWeight, labelStyleSizeM
} from "./EchartsConfig"
import * as echarts from "echarts"

export const EchartsPie = (params) => {

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
    //     series: [
    //         {
    //             name: "",
    //             left: "",
    //             radius: [],
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
            backgroundColor: "rgba(255,255,255,0.8)",
            textStyle: {
                color: colors[0],
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
            show: params.legend.show || true,
            icon: params.legend.icon || "roundRect",
            left: params.legend.left || "center",
            top: params.legend.top || "top",
            itemGap: params.legend.gap || 25,
            orient: params.legend.orient || "horizontal",
            textStyle: {
                color: legendStyleColor,
                fontSize: legendStyleSize,
                fontWeight: legendStyleWeight,
            }
        },
        series: []
    }
    params.series.forEach((element, index) => {
        option.series.push({
            name: element.name,
            type: "pie",
            left: element.left || "center",
            radius: element.radius || ["65%", "95%"],
            label: element.label,
            data: element.data,
            emphasis: {
                label: {
                    show: true,
                    fontSize: labelStyleSizeM,
                    color: legendStyleColor,
                    fontWeight: labelStyleWeight,
                    formatter: function (data) { // 设置圆饼图中间文字排版
                        return data.value + "\n" + data.name
                    }
                }
            },
        })
    });
    var chartDom = document.getElementById(params.el);
    var myChart = echarts.init(chartDom);
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
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
