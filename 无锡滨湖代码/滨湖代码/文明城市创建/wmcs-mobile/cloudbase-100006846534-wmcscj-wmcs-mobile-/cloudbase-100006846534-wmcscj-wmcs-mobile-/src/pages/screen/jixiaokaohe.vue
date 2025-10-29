<template>
    <view class="content">
        <div class="top">
            <div class="menu">
                <div class="title">绩效考评</div>
                <div class="search">
                    <uni-search-bar bgColor="#fff" @confirm="search" :focus="true" v-model="searchValue"></uni-search-bar>
                </div>
            </div>
        </div>
        <div class="pick">
            <div class="item">
                <span>范围：</span>
                <picker @change="timePickerChange" :value="timeIndex" :range="timeArray">
                    <view class="uni-input">{{ timeArray[timeIndex] }}</view>
                </picker>
                <uni-icons type="bottom" size="24" color="#ffffff50"></uni-icons>
            </div>
            <!-- <div class="item">
                <span>状态：</span>
                <picker @change="statusPickerChange" :value="statusIndex" :range="statusArray">
                    <view class="uni-input">{{ statusArray[statusIndex] }}</view>
                </picker>
                <uni-icons type="bottom" size="24" color="#ffffff50"></uni-icons>
            </div> -->
        </div>
        <div class="view">
            <div id="lineChart">
                <div class="th">
                    <div class="td" style="width:30%">单位名称</div>
                    <div class="td">发生次数</div>
                    <div class="td">整改个数</div>
                    <div class="td">总扣分数</div>
                    <div class="td">完成率</div>
                </div>
                <div class="tr" v-for="i, index in list" @click="toDetail(i)" :key="index">
                    <div class="td" style="width:30%">{{ i.deptName }}</div>
                    <div class="td">{{ i.count }}</div>
                    <div class="td">{{ i.handleCount }}</div>
                    <div class="td">{{ i.scoreTotal }}</div>
                    <div class="td">{{ i.handleAvg }}%</div>
                </div>
            </div>
        </div>
        <!-- <div class="bottom">
            <div class="item" @click="goto('./../screen/home')">
                <img src="static/images/b-1.png" alt="">
                <div>分析总览</div>
            </div>
            <div class="item active" @click="goto('./../screen/jixiaokaohe')">
                <img src="static/images/b-2-a.png" alt="">
                <div>绩效考评</div>
            </div>
            <div class="item" @click="goto('./../screen/gongdan')">
                <img src="static/images/b-3.png" alt="">
                <div>工单总览</div>
            </div>
        </div> -->
    </view>
</template>

<script>
import uniIcons from '../../plugins/uni-icons/uni-icons.vue';
import uniSearchBar from '../../plugins/uni-search-bar/uni-search-bar.vue';
import uniTag from "@/plugins/uni-tag/uni-tag.vue"
export default {
    components: { uniTag, uniIcons, uniSearchBar },
    data() {
        return {
            searchValue: "",
            statusIndex: 0,
            statusArray: ["全部", "待受理", "待核查", "已办结"],
            timeArray: ["月度", "季度", "年度"],
            timeIndex: 0,
            page: {
                pageSize: 20,
                pageNum: 1,
                deptName: ""
            },
            total: 0,
            flag: false,
            list: [
            ],
        }
    },
    onPullDownRefresh() {
        this.page.pageNum = 1;
        this.flag = true;
        this.list = [];
        this.getData(() => {
            uni.stopPullDownRefresh();
        });
    },
    onReachBottom() {
        if (this.flag) {
            this.page.pageNum++;
            this.getData();
        }
    },
    onShow() {
        this.page.pageNum = 1;
        this.flag = true;
        this.list = [];
        this.getData()
    },
    onLoad(query) {
    },
    methods: {
        goto(url) {
            uni.navigateTo({
                url: url
            });
        },
        search() {
            this.page.pageNum = 1;
            this.page.deptName = this.searchValue.value;
            this.flag = true;
            this.list = [];
            this.getData()
        },
        timePickerChange(e) {
            this.timeIndex = e.detail.value
            this.search()
        },
        statusPickerChange(e) {
            this.statusIndex = e.detail.value
        },
        getStatus() {

        },
        toDetail(row) {
            // let str = JSON.stringify(row)
            // uni.navigateTo({
            //     url: "./../gongdan/shijianxiangqing?row=" + encodeURIComponent(str)
            // });
        },
        getData(cb) {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });

            let api = "";
            if (this.timeIndex == 0) {
                api = this.$api.evaluatingmonth(this.page)
            } else if (this.timeIndex == 1) {
                api = this.$api.evaluating(this.page)
            } else if (this.timeIndex == 2) {
                api = this.$api.evaluatingyear(this.page)
            }
            api.then(r => {
                uni.hideLoading();
                if (this.list.length < r.data.total) {
                    this.total = r.data.total;
                    if (this.total <= this.pageSize) {
                        this.flag = false; // 关闭触底加载
                    } else {
                        this.flag = true; // 开启触底加载
                    }
                    if (r.data.records.length) {
                        this.list = this.list.concat(r.data.records);
                    } else {
                        if (this.page.pageNum == 1) {
                            this.list = [];
                        }
                        this.flag = false; // 关闭触底加载
                    }
                } else {
                    this.flag = false; // 关闭触底加载
                }
                cb && cb();
            })
                .catch(err => {
                    cb && cb();
                });
        },
    }
}
</script>

<style lang="scss" scoped>
page {
    background: #f4f5fd;
}

.top {
    width: 750upx;
    height: 213upx;
    background-color: #4770bf;
    position: fixed;
    top: 0;
    left: 0;

    .icons {
        position: absolute;
        top: 20upx;
        right: 40upx;
        z-index: 9999;
    }

    .menu {
        width: 750upx;
        height: 100%;
        background: url("static/images/menu.png") no-repeat;
        background-size: cover;
        background-position: -10upx -80upx;

        .title {
            text-align: center;
            line-height: 140upx;
            font-size: 42upx;
            color: #fff;
            letter-spacing: 15upx;
            font-weight: 900;
        }

    }

    .search {
        margin-top: 0upx;
        height: 80upx;
        background-color: #4770bf;
    }

}

.pick {
    position: absolute;
    top: 253upx;
    left: 0;
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


.view {
    position: absolute;
    top: 343upx;
    left: 0;

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
                width: 20%;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }

            .td:nth-child(1) {
                width: 20%;
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

            .td {
                width: 30%;
                color: #4770bf;
                position: relative;
                border-bottom: 1upx solid #0000001a;
                display: flex;
                align-items: center;
                justify-content: center;
                //overflow: hidden;
                //text-overflow: ellipsis;
                //white-space: nowrap;

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

            .td:nth-child(1) {
                width: 10%;
            }
        }
    }
}

.bottom {
    position: fixed;
    bottom: 0;
    left: 0;
    height: 100upx;
    width: 100%;
    display: flex;
    flex-direction: row;
    border-top: 1px solid #00000020;

    .item {
        width: 33.3%;
        line-height: 50upx;
        text-align: center;
        background-color: #fff;
        border-right: 1px solid #ffffff50;
        color: #000;
        display: flex;
        flex-direction: column;
        font-size: 14upx;
        align-items: center;
        border-right: 1px solid #00000020;
        justify-content: space-around;
        font-size: 24upx;

        img {
            width: 48upx;
            height: 48upx;
        }
    }

    .item:nth-child(3) {
        border-right: none
    }

    .active {
        color: #2194f3;
    }
}
</style>
