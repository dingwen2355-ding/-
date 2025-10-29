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
        <div id="lineChart"></div>
        <div class="list">
            <div class="row1">
                <div class="left">
                    <img src="static/icons/1.png">
                    <span>工单总数</span>
                </div>
                <div class="right">
                    {{ formatNumber(sum) }}
                    <span>件</span>
                </div>
            </div>
            <div class="row" v-for="i, index in data">
                <div class="item" v-for="j in data[index]">
                    <img :src="j.icon">
                    <div class="right">
                        <div class="t">{{ j.name }}</div>
                        <div class="b">
                            {{ formatNumber(j.num) }}
                            <span>件</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
            sum: 7536,
            data: [
                [{
                    icon: require("static/icons/2.png"),
                    name: "按时办结",
                    num: 7020
                },
                {
                    icon: require("static/icons/3.png"),
                    name: "超时办结",
                    num: 0
                },],
                [{
                    icon: require("static/icons/4.png"),
                    name: "未办结",
                    num: 516 - 127
                },
                {
                    icon: require("static/icons/5.png"),
                    name: "延期工单",
                    num: 127
                },],
                // [{
                //     icon: require("static/icons/6.png"),
                //     name: "红牌警告",
                //     num: 0
                // },
                // {
                //     icon: require("static/icons/7.png"),
                //     name: "黄牌警告",
                //     num: 0
                // }],
            ]
        };
    },
    mounted() {
        this.$api.eventTrend({ regionName: this.areaIndex == 0 ? undefined : this.areaArray[this.areaIndex] }).then(res => {
            let xData = [];
            let yData2 = [];
            let yData1 = [];
            res.data.splice(0, 6).forEach(d => {
                xData.push(d.type)
                yData1.push(d.count)
                yData2.push(d.finishCount)
            })
            this.loadChart(xData,
                yData1,
                yData2)
        })

        this.$api.eventStatus({ regionName: this.areaIndex == 0 ? undefined : this.areaArray[this.areaIndex] }).then(res => {
            this.sum = res.data.totalCount
            this.data[0][0].num = res.data.finishCount
            this.data[1][0].num = res.data.todoCount
            this.data[1][1].num = res.data.extensionCount
        })
    },

    methods: {
        areaPickerChange(e) {
            this.areaIndex = e.detail.value
            this.$api.eventTrend({ regionName: this.areaIndex == 0 ? undefined : this.areaArray[this.areaIndex] }).then(res => {
                let xData = [];
                let yData2 = [];
                let yData1 = [];
                res.data.splice(0, 6).forEach(d => {
                    xData.push(d.type)
                    yData1.push(d.count)
                    yData2.push(d.finishCount)
                })
                this.loadChart(xData,
                    yData1,
                    yData2)
            })
            this.$api.eventStatus({ regionName: this.areaIndex == 0 ? undefined : this.areaArray[this.areaIndex] }).then(res => {
                this.sum = res.data.totalCount
                this.data[0][0].num = res.data.finishCount
                this.data[1][0].num = res.data.todoCount
                this.data[1][1].num = res.data.extensionCount
            })
        },
        goto(url) {
            uni.navigateTo({
                url: url
            });
        },
        loadChart(xdata, ydaya1, ydata2) {
            const colorList = ["#9E87FF", "#73DDFF", "#fe9a8b", "#F56948", "#9E87FF"];
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
                        data: xdata,
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
                        name: "工单数",
                        type: "line",
                        data: ydaya1,
                        symbolSize: 10,
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
                        name: "办结数",
                        type: "line",
                        data: ydata2,
                        symbolSize: 10,
                        symbol: "circle",
                        smooth: true,
                        yAxisIndex: 0,
                        showSymbol: true,
                        label: {
                            show: true,
                            position: "bottom",
                            textStyle: {
                                color: "#73DD39",
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
        // width: calc(49% - 40upx);
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
  