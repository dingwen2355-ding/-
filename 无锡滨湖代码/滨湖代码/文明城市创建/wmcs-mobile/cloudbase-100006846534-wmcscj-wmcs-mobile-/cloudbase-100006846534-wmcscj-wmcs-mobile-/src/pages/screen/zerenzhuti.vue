<template>
    <view class="content">
        <div class="pick">
            <div class="item">
                <span>考核周期：</span>
                <picker @change="cyclePickerChange" :value="cycleIndex" :range="cycleArray">
                    <view>{{ cycleArray[cycleIndex] }}</view>
                </picker>
                <uni-icons type="bottom" size="24" color="#00000050"></uni-icons>
            </div>
        </div>
        <div class="tbg">总数/办结数分析</div>
        <div id="lineChart"></div>
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
            cycleArray: [],
            cycleIndex: 0,
            typeArray: ["板块", "职能部门"],
            typeIndex: 0,
        };
    },
    mounted() {
        this.getCycles()
        // this.loadChart2(["马山街道", "雪浪街道", "蠡园街道", "胡埭镇", "河埒街道", "荣巷街道", "蠡湖街道"], [0, 0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0, 0, 0])
    },

    methods: {
        goto(url) {
            uni.navigateTo({
                url: url
            });
        },
        cyclePickerChange(e) {
            this.cycleIndex = e.detail.value;
            this.getData()
        },
        typePickerChange(e) {
            this.typeIndex = e.detail.value
            this.getData()
        },
        getData() {
            let xData = ["马山街道", "雪浪街道", "蠡园街道", "胡埭镇", "河埒街道", "荣巷街道", "蠡湖街道"];
            let yData1 = [], yData2 = [];
            this.$api.highRegion({ type: 1, cycle: this.cycleArray[this.cycleIndex] }).then(res2 => {
                for (let i = 0; i < xData.length; i++) {
                    const element = xData[i];
                    yData1.push(res2.data[element]["工单总数"])
                    yData2.push(res2.data[element]["办结总数"])
                }
                this.loadChart(xData, yData1, yData2)
            })
            this.$api.highRegion({ type: 2, cycle: this.cycleArray[this.cycleIndex] }).then(res2 => {
                let xData2 = [], yData12 = [], yData22 = [];
                for (const key in res2.data) {
                    if (Object.hasOwnProperty.call(res2.data, key)) {
                        const element = res2.data[key];
                        xData2.push(key)
                        yData12.push(element["工单总数"])
                        yData22.push(element["办结总数"])
                    }
                }
                this.loadChart2(xData2, yData12, yData22)
            })
        },
        getCycles() {
            this.$api.getAssessCycleList().then(res => {
                this.cycleArray = [];
                res.data.records.forEach(element => {
                    this.cycleArray.push(element.name)
                });
                this.getData()
            })
        },
        loadChart(xData, yData1, yData2) {
            var borderData = [];
            var legend = ["总数", "办结数"];
            var colorArr = [
                {
                    start: "rgba(24, 231, 255,",
                    end: "rgba(24, 146, 255, 1)",
                },
                {
                    start: "RGBA(46, 217, 228,",
                    end: "RGBA(48, 230, 255, 1)",
                },
            ];
            var normalColor = "rgba(0,0,0,0.5)";
            let seriesData = [];
            var borderHeight = 4;
            xData.forEach((element) => {
                borderData.push(borderHeight);
            });
            [yData1, yData2].forEach((item, index) => {
                var obj1 = {};
                obj1 = {
                    name: legend[index],
                    type: "bar",
                    stack: legend[index],
                    data: item,
                    barWidth: "25%",
                    itemStyle: {
                        normal: {
                            color: {
                                type: "linear",
                                x: 0,
                                y: 0,
                                x2: 0,
                                y2: 1,
                                colorStops: [
                                    {
                                        offset: 0,
                                        color: colorArr[index].start + "0.7)",
                                    },
                                    {
                                        offset: 0.5,
                                        color: colorArr[index].start + "0.3)",
                                    },
                                    {
                                        offset: 1,
                                        color: colorArr[index].end,
                                    },
                                ],
                                globalCoord: false,
                            },
                        },
                    },
                };
                seriesData.push(obj1);
            });

            let option = {
                grid: {
                    top: "15%",
                    left: "5%",
                    bottom: "5%",
                    containLabel: true
                },
                legend: {
                    icon: "rect",
                    left: "center",
                    itemGap: 20,
                    textStyle: {
                        color: "#000",
                    },
                    data: legend,
                },
                tooltip: {
                    trigger: "axis",
                    formatter: function (params) {
                        var str = "";
                        for (var i = 0; i < params.length; i++) {
                            if (params[i].seriesName !== "") {
                                str +=
                                    params[i].name +
                                    ":" +
                                    params[i].seriesName +
                                    params[i].value +
                                    "<br/>";
                            }
                        }
                        return str;
                    },
                },
                xAxis: [
                    {
                        type: "category",
                        data: xData,
                        axisLabel: {
                            rotate: "30",
                            textStyle: {
                                color: normalColor,
                                fontSize: 12,
                            },
                        },
                        axisLine: {
                            lineStyle: {
                                color: "#DCE2E8",
                            },
                        },
                        axisTick: {
                            show: false,
                        },
                        splitLine: {
                            show: false,
                        },
                    },
                ],
                yAxis: [
                    {
                        type: "value",
                        nameTextStyle: {
                            color: normalColor,
                            fontSize: 12,
                        },
                        // "min": 0,
                        // "max": 50,
                        axisLabel: {
                            formatter: "{value}",
                            textStyle: {
                                color: normalColor,
                                fontSize: 12,
                            },
                        },
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: "#DCE2E8",
                            },
                        },
                        axisTick: {
                            show: false,
                        },
                        splitLine: {
                            show: false,
                            lineStyle: {
                                type: "dashed",
                                color: normalColor,
                            },
                        },
                    },
                ],
                series: seriesData,
            };
            let myChart = echarts.init(document.querySelector("#lineChart"));
            myChart.setOption(option)
        },
        loadChart2(xData, yData1, yData2) {
            var borderData = [];
            var legend = ["总数", "办结数"];
            var colorArr = [
                {
                    start: "rgba(24, 231, 255,",
                    end: "rgba(24, 146, 255, 1)",
                },
                {
                    start: "RGBA(46, 217, 228,",
                    end: "RGBA(48, 230, 255, 1)",
                },
            ];
            var normalColor = "rgba(0,0,0,0.5)";
            let seriesData = [];
            var borderHeight = 4;
            xData.forEach((element) => {
                borderData.push(borderHeight);
            });
            [yData1, yData2].forEach((item, index) => {
                var obj1 = {};
                obj1 = {
                    name: legend[index],
                    type: "bar",
                    stack: legend[index],
                    data: item,
                    barWidth: "25%",
                    itemStyle: {
                        normal: {
                            color: {
                                type: "linear",
                                x: 0,
                                y: 0,
                                x2: 0,
                                y2: 1,
                                colorStops: [
                                    {
                                        offset: 0,
                                        color: colorArr[index].start + "0.7)",
                                    },
                                    {
                                        offset: 0.5,
                                        color: colorArr[index].start + "0.3)",
                                    },
                                    {
                                        offset: 1,
                                        color: colorArr[index].end,
                                    },
                                ],
                                globalCoord: false,
                            },
                        },
                    },
                };
                seriesData.push(obj1);
            });

            let option = {
                grid: {
                    top: "15%",
                    left: "5%",
                    bottom: "5%",
                    containLabel: true
                },
                legend: {
                    icon: "rect",
                    left: "center",
                    itemGap: 20,
                    textStyle: {
                        color: "#000",
                    },
                    data: legend,
                },
                tooltip: {
                    trigger: "axis",
                    formatter: function (params) {
                        var str = "";
                        for (var i = 0; i < params.length; i++) {
                            if (params[i].seriesName !== "") {
                                str +=
                                    params[i].name +
                                    ":" +
                                    params[i].seriesName +
                                    params[i].value +
                                    "<br/>";
                            }
                        }
                        return str;
                    },
                },
                xAxis: [
                    {
                        type: "category",
                        data: xData,
                        axisLabel: {
                            rotate: "30",
                            textStyle: {
                                color: normalColor,
                                fontSize: 12,
                            },
                        },
                        axisLine: {
                            lineStyle: {
                                color: "#DCE2E8",
                            },
                        },
                        axisTick: {
                            show: false,
                        },
                        splitLine: {
                            show: false,
                        },
                    },
                ],
                yAxis: [
                    {
                        type: "value",
                        nameTextStyle: {
                            color: normalColor,
                            fontSize: 12,
                        },
                        // "min": 0,
                        // "max": 50,
                        axisLabel: {
                            formatter: "{value}",
                            textStyle: {
                                color: normalColor,
                                fontSize: 12,
                            },
                        },
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: "#DCE2E8",
                            },
                        },
                        axisTick: {
                            show: false,
                        },
                        splitLine: {
                            show: false,
                            lineStyle: {
                                type: "dashed",
                                color: normalColor,
                            },
                        },
                    },
                ],
                series: seriesData,
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
  
<style lang="less" scoped>
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
    height: 500upx;
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
  