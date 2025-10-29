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
            <div class="item">
                <span>考核周期：</span>
                <picker @change="cyclePickerChange" :value="cycleIndex" :range="cycleArray">
                    <view>{{ cycleArray[cycleIndex] }}</view>
                </picker>
                <uni-icons type="bottom" size="24" color="#00000050"></uni-icons>
            </div>
        </div>
        <div class="tbg" style="margin-top: 80upx;">点位分析</div>
        <div id="lineChart">
            <div class="th">
                <div class="td">序号</div>
                <div class="td">点位名称</div>
                <div class="td">整改率</div>
                <div class="td">工单数</div>
            </div>
            <div class="tr" v-for="i, index in table" :key="i.id">
                <div class="td" v-if="index >= 3">{{ index + 1 }}</div>
                <div class="td" v-else>
                    <span class="pos">{{ index + 1 }}</span>
                    <img :src="'static/images/top' + index + '.png'" alt="" srcset="">
                </div>
                <div class="td">{{ i.pointName }}</div>
                <div class="td">{{ Number(i.avgCount * 100).toFixed(2) + "%" }}</div>
                <div class="td">{{ i.count }}</div>
            </div>
        </div>
        <div class="tbg">类别分析</div>
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
            areaArray: ["马山街道", "雪浪街道", "蠡园街道", "胡埭镇", "河埒街道", "荣巷街道", "蠡湖街道"],
            areaIndex: 0,
            table: [
            ]
        };
    },
    mounted() {
        this.getCycles()
    },

    methods: {
        goto(url) {
            uni.navigateTo({
                url: url
            });
        },
        cyclePickerChange(e) {
            this.cycleIndex = e.detail.value
            this.loadChart2()
            this.loadTable()
        },
        areaPickerChange(e) {
            this.areaIndex = e.detail.value
            this.loadChart2()
            this.loadTable()
        },
        getCycles() {
            this.$api.getAssessCycleList().then(res => {
                this.cycleArray = [];
                res.data.records.forEach(element => {
                    this.cycleArray.push(element.name)
                });
                this.loadChart2()
                this.loadTable()
            })
        },
        loadTable() {
            this.$api.pointsAvg({ regionName: this.areaArray[this.areaIndex], cycle: this.cycleArray[this.cycleIndex] }).then(res => {
                this.table = res.data.slice(0, 6)
            })
        },
        loadChart2() {
            this.$api.eventTypeAvg({ regionName: this.areaArray[this.areaIndex], cycle: this.cycleArray[this.cycleIndex] }).then(res => {
                let data = [];
                // let sum = 0;
                // res.data.forEach(d => {
                //     sum += d.count
                // })
                res.data.forEach(d => {
                    data.push({
                        value: Number(d.avg * 100).toFixed(2),
                        name: d.eventType
                    })
                })
                for (var n in data) {
                    data[n]["name"] = data[n]["name"] + " \n" + data[n]["value"] + "%";
                }

                let option = {
                    tooltip: {
                        trigger: "item",
                        formatter: "{b}",
                    },
                    series: [
                        {
                            type: "treemap",
                            width: "90%",
                            height: "85%",
                            top: "5%",
                            roam: true, //是否开启拖拽漫游（移动和缩放）
                            nodeClick: false, //点击节点后的行为,false无反应
                            breadcrumb: {
                                show: false,
                            },
                            label: {
                                //描述了每个矩形中，文本标签的样式。
                                normal: {
                                    show: true,
                                    position: ["0%", "20%"],
                                },
                            },
                            itemStyle: {
                                normal: {
                                    show: true,
                                    textStyle: {
                                        color: "#fff",
                                        fontSize: 16,
                                    },
                                    borderWidth: 1,
                                    borderColor: "#fff",
                                },

                                emphasis: {
                                    label: {
                                        show: true,
                                    },
                                },
                            },
                            data: data,
                        },
                    ],
                };
                let myChart = echarts.init(document.querySelector("#lineChart2"));
                myChart.setOption(option)
            })

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
    position: fixed;
    top: 210upx;
    z-index: 99;

    .item {
        padding: 0 20upx;
        width: calc(49% - 40upx);
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

    .th {
        height: 90upx;
        text-align: center;
        font-weight: 700;
        font-size: 30upx;
        display: flex;
        flex-direction: row;
        background-color: #fff;
        line-height: 90upx;

        .td {
            width: 25%;
        }
    }

    .tr {
        min-height: 90upx;
        text-align: center;
        font-weight: 700;
        font-size: 30upx;
        display: flex;
        flex-direction: row;
        background-color: #fff;
        line-height: 90upx;

        .td {
            width: 25%;
            color: #4770bf;
            position: relative;
            border-bottom: 1upx solid #0000001a;

            img {
                width: 66upx;
                height: 66upx;
                margin-top: 10upx;
            }

            .pos {
                position: absolute;
                left: 85upx;
                color: #fff;
            }
        }
        .td:nth-child(2){
            font-size: 24upx;
        }
    }
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
  