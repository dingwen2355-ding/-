<template>
    <view class="content">
        <div class="pick">
            <div class="item">
                <span>区域：</span>
                <picker @change="areaPickerChange" :value="areaIndex" :range="areaArray">
                    <view>{{ areaArray[areaIndex] }}</view>
                </picker>
                <uni-icons type="bottom" size="24" color="#00000050"></uni-icons>
            </div>
        </div>
        <div class="tbg">办结分析</div>
        <div id="lineChart"></div>

        <div class="tbg">同环比分析</div>
        <div class="pick">
            <div class="item">
                <span>时间：</span>
                <picker @change="timePickerChange" :value="timeIndex" :range="timeArray">
                    <view>{{ timeArray[timeIndex] }}</view>
                </picker>
                <uni-icons type="bottom" size="24" color="#00000050"></uni-icons>
            </div>
        </div>
        <div id="lineChart2"></div>
    </view>
</template>
  
<script>
import * as echarts from "echarts"
import uniIcons from "../../plugins/uni-icons/uni-icons.vue";
export default {
    components: { uniIcons },
    data() {
        return {
            areaArray: ["滨湖区", "马山街道", "雪浪街道", "蠡园街道", "胡埭镇", "河埒街道", "荣巷街道", "蠡湖街道"],
            areaIndex: 0,
            timeArray: ["2023-07", "2023-06"],
            timeIndex: 0,
            tb: 0,
            hb: 0,
            chart2: {}
        };
    },
    mounted() {
        this.loadChart()
        this.$api.getMonth().then(res => {
            this.timeArray = res.data
            this.$api.eventRatio({ month: this.timeArray[this.timeIndex] }).then(res => {
                this.chart2 = res.data
                let tbsum = 0, hbsum = 0
                for (const key in this.chart2) {
                    if (Object.hasOwnProperty.call(this.chart2, key)) {
                        const element = this.chart2[key];
                        tbsum += element["同比"]
                        hbsum += element["环比"]
                    }
                }
                this.tb = Number(tbsum / 7).toFixed(2)
                this.hb = Number(hbsum / 7).toFixed(2)
                this.loadChart2(this.tb, this.hb);
            })
        })

    },

    methods: {
        areaPickerChange(e) {
            this.areaIndex = e.detail.value
            this.loadChart()
            if (this.areaIndex == 0) {
                this.loadChart2(this.tb, this.hb);
            } else {
                let elemet = this.chart2[this.areaArray[this.areaIndex]]
                this.loadChart2(elemet["同比"], elemet["环比"]);
            }

        },
        timePickerChange(e) {
            this.timeIndex = e.detail.value
            this.$api.eventRatio({ month: this.timeArray[this.timeIndex] }).then(res => {
                this.chart2 = res.data
                let tbsum = 0, hbsum = 0
                for (const key in this.chart2) {
                    if (Object.hasOwnProperty.call(this.chart2, key)) {
                        const element = this.chart2[key];
                        tbsum += element["同比"]
                        hbsum += element["环比"]
                    }
                }
                this.tb = Number(tbsum / 7).toFixed(2)
                this.hb = Number(hbsum / 7).toFixed(2)
                this.areaPickerChange({ detail: { value: this.areaIndex } })
            })
        },
        goto(url) {
            uni.navigateTo({
                url: url
            });
        },
        loadChart() {
            this.$api.eventEnd({ regionName: this.areaIndex == 0 ? undefined : this.areaArray[this.areaIndex] }).then(res => {
                let xData = [];
                let yData2 = [];
                let yData1 = [];
                let list = res.data;
                let index = 0
                for (const key in list) {
                    if (index <= 5) {
                        if (Object.hasOwnProperty.call(list, key)) {
                            const element = list[key];
                            xData.push(key)
                            yData1.push(element["人工办结"])
                            yData2.push(element["自动办结"])
                        }
                    }
                    index++
                }

                const colorList = ["#9E87FF", "#73DD39"];
                let option = {
                    legend: {
                        icon: "rect",
                        left: "center",
                        itemGap: 20,
                        textStyle: {
                            color: "#000",
                        },
                    },
                    tooltip: {
                        trigger: "axis",
                        axisPointer: {
                            label: {
                                show: true,
                                backgroundColor: "#fff",
                                color: "#556677",
                                borderColor: "rgba(0,0,0,0)",
                                shadowColor: "rgba(0,0,0,0)",
                                shadowOffsetY: 0,
                            },
                            lineStyle: {
                                width: 0,
                            },
                        },
                        backgroundColor: "#fff",
                        textStyle: {
                            color: "#5c6c7c",
                        },
                        padding: [10, 10],
                        extraCssText: "box-shadow: 1px 0 2px 0 rgba(163,163,163,0.5)",
                    },
                    grid: {
                        top: "15%",
                        left: "5%",
                        bottom: "5%",
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: "category",
                            data: xData,
                            axisLine: {
                                lineStyle: {
                                    color: "#DCE2E8",
                                },
                            },
                            axisTick: {
                                show: false,
                            },
                            axisLabel: {
                                interval: 0,
                                textStyle: {
                                    color: "#556677",
                                },
                                // 默认x轴字体大小
                                fontSize: 12,
                                // margin:文字到x轴的距离
                                margin: 20,
                            },
                            axisPointer: {
                                label: {
                                    // padding: [11, 5, 7],
                                    padding: [0, 0, 10, 0],
                                    /*
                              除了padding[0]建议必须是0之外，其他三项可随意设置
                              
                              和CSSpadding相同，[上，右，下，左]
                              
                              如果需要下边线超出文字，设左右padding即可，注：左右padding最好相同
                              
                              padding[2]的10:
                              
                              10 = 文字距下边线的距离 + 下边线的宽度
                                          
                              如：UI图中文字距下边线距离为7 下边线宽度为2
                              
                              则padding: [0, 0, 9, 0]
                                          
                                          */
                                    // 这里的margin和axisLabel的margin要一致!
                                    margin: 15,
                                    // 移入时的字体大小
                                    fontSize: 12,
                                    backgroundColor: {
                                        type: "linear",
                                        x: 0,
                                        y: 0,
                                        x2: 0,
                                        y2: 1,
                                        colorStops: [
                                            {
                                                offset: 0,
                                                color: "#fff", // 0% 处的颜色
                                            },
                                            {
                                                // offset: 0.9,
                                                offset: 0.86,
                                                /*
                                0.86 = （文字 + 文字距下边线的距离）/（文字 + 文字距下边线的距离 + 下边线的宽度）
                                                        
                                                        */
                                                color: "#fff", // 0% 处的颜色
                                            },
                                            {
                                                offset: 0.86,
                                                color: "#33c0cd", // 0% 处的颜色
                                            },
                                            {
                                                offset: 1,
                                                color: "#33c0cd", // 100% 处的颜色
                                            },
                                        ],
                                        global: false, // 缺省为 false
                                    },
                                },
                            },
                            boundaryGap: false,
                        },
                    ],
                    yAxis: [
                        {
                            type: "value",
                            axisTick: {
                                show: false,
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    color: "#DCE2E8",
                                },
                            },
                            axisLabel: {
                                textStyle: {
                                    color: "#556677",
                                },
                            },
                            splitLine: {
                                show: false,
                            },
                        },
                    ],
                    series: [
                        {
                            name: "人工办结",
                            type: "line",
                            data: yData1,
                            symbolSize: 5,
                            symbol: "circle",
                            smooth: true,
                            yAxisIndex: 0,
                            showSymbol: true,
                            label: {
                                show: true,
                                position: "top",
                                textStyle: {
                                    color: "#9E87FF",
                                },
                            },
                            lineStyle: {
                                width: 5,
                                color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                                    {
                                        offset: 0,
                                        color: "#9effff",
                                    },
                                    {
                                        offset: 1,
                                        color: "#9E87FF",
                                    },
                                ]),
                                shadowColor: "rgba(158,135,255, 0.3)",
                                shadowBlur: 10,
                                shadowOffsetY: 20,
                            },
                            itemStyle: {
                                normal: {
                                    color: colorList[0],
                                    borderColor: colorList[0],
                                },
                            },
                        },
                        {
                            name: "自动办结",
                            type: "line",
                            data: yData2,
                            symbolSize: 1,
                            symbol: "circle",
                            smooth: true,
                            yAxisIndex: 0,
                            showSymbol: true,
                            label: {
                                show: true,
                                position: "bottom",
                                textStyle: {
                                    color: "#73DDFF",
                                },
                            },
                            lineStyle: {
                                width: 5,
                                color: new echarts.graphic.LinearGradient(1, 1, 0, 0, [
                                    {
                                        offset: 0,
                                        color: "#73DD39",
                                    },
                                    {
                                        offset: 1,
                                        color: "#73DDFF",
                                    },
                                ]),
                                shadowColor: "rgba(115,221,255, 0.3)",
                                shadowBlur: 10,
                                shadowOffsetY: 20,
                            },
                            itemStyle: {
                                normal: {
                                    color: colorList[1],
                                    borderColor: colorList[1],
                                },
                            },
                        },
                    ],
                };
                let myChart = echarts.init(document.querySelector("#lineChart"));
                myChart.setOption(option)
            })
        },
        loadChart2(tb, hb) {
            var highlight = "#4770bf";

            var demoData = [
                {
                    name: "同比",
                    value: tb,
                    unit: "%",
                    pos: ["25%", "55%"],
                    range: [-100, 100],
                },
                {
                    name: "环比",
                    value: hb,
                    unit: "%",
                    pos: ["75%", "55%"],
                    range: [-100, 100],
                },
            ];

            let option = {
                series: (function () {
                    var result = [];

                    demoData.forEach(function (item) {
                        result.push(
                            // 外围刻度
                            {
                                type: "gauge",
                                center: item.pos,
                                radius: "80%",
                                splitNumber: item.splitNum || 10,
                                min: item.range[0],
                                max: item.range[1],
                                startAngle: 225,
                                endAngle: -45,
                                axisLine: {
                                    show: true,
                                    lineStyle: {
                                        width: 2,
                                        shadowBlur: 0,
                                        color: [[1, highlight]],
                                    },
                                },
                                axisTick: {
                                    show: true,
                                    lineStyle: {
                                        color: highlight,
                                        width: 1,
                                    },
                                    length: -5,
                                    splitNumber: 10,
                                },
                                splitLine: {
                                    show: true,
                                    length: -14,
                                    lineStyle: {
                                        color: highlight,
                                    },
                                },
                                axisLabel: {
                                    distance: -20,
                                    textStyle: {
                                        color: highlight,
                                        fontSize: "14",
                                    },
                                },
                                pointer: {
                                    show: 0,
                                },
                                detail: {
                                    show: 0,
                                },
                            },

                            // 内侧指针、数值显示
                            {
                                name: item.name,
                                type: "gauge",
                                center: item.pos,
                                radius: "50%",
                                startAngle: 225,
                                endAngle: -45,
                                min: item.range[0],
                                max: item.range[1],
                                axisLine: {
                                    show: true,
                                    lineStyle: {
                                        width: 16,
                                        color: [[1, "rgba(255,255,255,.1)"]],
                                    },
                                },
                                axisTick: {
                                    show: 0,
                                },
                                splitLine: {
                                    show: 0,
                                },
                                axisLabel: {
                                    show: 0,
                                },
                                pointer: {
                                    show: true,
                                    length: "105%",
                                },
                                detail: {
                                    show: true,
                                    offsetCenter: [0, "100%"],
                                    textStyle: {
                                        fontSize: 20,
                                        color: "#556677",
                                    },
                                    formatter: [
                                        "{value} " + (item.unit || ""),
                                        "{name|" + item.name + "}",
                                    ].join("\n"),
                                    rich: {
                                        name: {
                                            fontSize: 14,
                                            lineHeight: 30,
                                            color: "#556677",
                                        },
                                    },
                                },
                                itemStyle: {
                                    normal: {
                                        color: highlight,
                                    },
                                },
                                data: [
                                    {
                                        value: item.value,
                                    },
                                ],
                            }
                        );
                    });

                    return result;
                })(),
            };
            let myChart = echarts.init(document.querySelector("#lineChart2"));
            myChart.setOption(option)
        },
        formatNumber(val) {
            const valTemp = val.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
            return valTemp
        }
    }
};
</script>
  
<style lang="less">
.content {
    width: 750upx;
}

.pick {
    width: 100%;
    line-height: 80upx;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    background-color: #fff;
    margin-bottom: 20upx;

    .item {
        padding: 0 20upx;
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-around;
        border-bottom: 1px solid #00000009;
    }

    .item:nth-child(odd) {
        border-right: 1px solid #00000009;
    }
}

#lineChart {
    width: 750upx;
    height: 500upx;
}

#lineChart2 {
    width: 750upx;
    height: 300upx;
}

.row1 {
    width: 690upx;
    padding: 0 30upx;
    height: 144upx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;

    .left {
        width: 50%;
        display: flex;
        justify-content: space-around;
        align-items: center;
        font-size: 32upx;
        font-weight: 700;
        letter-spacing: 8upx;

        img {
            width: 100upx;
            height: 100upx;
        }
    }

    .right {
        color: #2192FB;
        font-size: 62upx;
        font-weight: 700;

        span {
            font-size: 28upx;
            margin-left: 15upx;

        }
    }
}

.row {
    width: 690upx;
    padding: 0 30upx;
    height: 144upx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;
    margin-top: 20upx;

    .item {
        width: 50%;
        display: flex;
        justify-content: space-around;
        align-items: center;

        img {
            width: 100upx;
            height: 100upx;
        }

        .right {
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-direction: column;
            font-size: 32upx;
            font-weight: 700;

            .t {
                font-size: 32upx;
                font-weight: 700;
                letter-spacing: 8upx;
            }

            .b {
                color: #2192FB;
                font-size: 42upx;
                font-weight: 700;

                span {
                    font-size: 28upx;
                    margin-left: 15upx;
                }
            }
        }
    }
}
</style>
  