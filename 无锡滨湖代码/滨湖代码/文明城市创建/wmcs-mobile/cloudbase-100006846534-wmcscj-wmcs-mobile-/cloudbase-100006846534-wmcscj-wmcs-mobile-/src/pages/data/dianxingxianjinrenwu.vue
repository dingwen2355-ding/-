<template>
    <view class="content">
        <UniSegmentedControl class="swicth" :current="current" :values="items" @clickItem="onClickItem" styleType="text"
            activeColor="#4770bf"></UniSegmentedControl>
        <div class="searchPanel">
            <uni-forms ref="form" :modelValue="page" label-position="left" label-width="120">
                <uni-forms-item label="关键字" name="selectName">
                    <uni-easyinput v-model="page.selectName" placeholder="请输入姓名" @change="search()" />
                </uni-forms-item>
                <div
                    style="width:100%;display: flex;flex-direction: row;justify-content: flex-start;align-items: center;">
                    <uni-forms-item label="获评层级" name="level" v-show="showMore" style="width: 30%;">
                        <uni-data-select style="width: 30%;" v-model="page.level" :localdata="levels" @change="search()"
                            :clear="true"></uni-data-select>
                    </uni-forms-item>
                    <uni-forms-item label="评选季度" name="selectName" v-show="showMore" style="width: 50%; margin-left: 145rpx;">
                        <uni-easyinput v-model="page.selectDate" placeholder="请输入评选季度" @change="search()" />
                    </uni-forms-item>
                </div>
                <uni-forms-item label="所属街道" name="regionCode" v-show="showMore">
                    <uni-data-picker v-model="page.regionCode" placeholder="所属街道" popup-title="所属街道"
                        :localdata="regionTree" @change="search()" :map="{ text: 'name', value: 'id' }">
                    </uni-data-picker>
                </uni-forms-item>
            </uni-forms>
            <p class="total">共<span>{{ total }}</span>条</p>
        </div>
        
        <div class="panel2" v-if="list.length > 0">
            <div class="item" v-for="i, index in list" :key="index" @click="toDetail(i)">
                <div class="top">
                    <image mode="aspectFit" v-if="i.iconList.length > 0" :src="$resource(i.iconList[0].url)"></image>
                    <div v-else class="img">暂无附件</div>
                    <div class="text">
                        <div class="content">{{ i.name }}</div>
                        <div>
                            <div>类型：{{ getSelectType(i.recommendType) }}</div>
                            <div>所属街道：{{ i.regionName }}</div>
                            <div>评选季度：{{ i.selectDate || '/' }}</div>
                            <div>获评层级：{{ getSelectLevel(i.level) }}</div>
                        </div>
                    </div>
                </div>
                <!-- <div class="bottom">
                    <uni-tag class="tag" :inverted="false" :text="i.eventTypeOneName" size="small" type="success" />
                    <uni-tag class="tag" :inverted="false" :text="i.eventTypeTwoName" size="small" type="primary" />
                    <uni-tag class="tag" :inverted="false" :text="i.eventTypeName" size="small" type="warning" />
                </div> -->
            </div>
        </div>
        <div class="panel2" v-else>
            <oEmpty bg="#ffffff00"></oEmpty>
        </div>
    </view>
</template>

<script>
import UniSegmentedControl from '../../plugins/uni-segmented-control/uni-segmented-control.vue'
import uniTag from "@/plugins/uni-tag/uni-tag.vue"
import oEmpty from "@/plugins/o-empty/o-empty.vue"
import uniForms from "@/plugins/uni-forms/uni-forms.vue"
import uniFormsItem from "@/plugins/uni-forms-item/uni-forms-item.vue"
import uniDataSelect from "@/plugins/uni-data-select/uni-data-select.vue"
import uniEasyinput from "@/plugins/uni-easyinput/uni-easyinput.vue"
import customTreeSelect from "@/plugins/custom-tree-select/custom-tree-select.vue"
import uniDataPicker from "@/plugins/uni-data-picker/uni-data-picker.vue"
export default {
    components: { UniSegmentedControl, uniTag, oEmpty, uniForms, uniFormsItem, uniEasyinput, uniDataSelect, customTreeSelect, uniDataPicker },
    data() {
        return {
            items: ["全部", "新时代好少年", "身边好人", "最美人物", "岗位学雷锋标兵"],
            current: 0,
            showMore: true,
            page: {
                pageSize: 10,
                pageNum: 1,
                selectName: "",
                selectDate: "",
                level: "",
                regionCode: "",
                recommendType: 0
            },
            total: 0,
            regionTree: [],
            levels: [],
            dates: [
                { text: "2016", value: "2016" },
                { text: "2017", value: "2017" },
                { text: "2018", value: "2018" },
                { text: "2019", value: "2019" },
                { text: "2020", value: "2020" },
                { text: "2021", value: "2021" },
                { text: "2022", value: "2022" },
                { text: "2023", value: "2023" },
                { text: "2024", value: "2024" },
            ],
            list: [],
            isA: true
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
        this.row = JSON.parse(decodeURIComponent(query.row))
        let dicts = JSON.parse(window.localStorage.getItem("dicts"));
        this.levels = dicts["data_civilization_select_level"].map(r => {
            return { text: r.label, value: r.value }
        })
        this.$api.RegionTree({}).then(res => {
            res.data.records[0].children.forEach(r => {
                r.children = []
            })
            this.regionTree = res.data.records
        })
    },
    methods: {
        onClickItem(e) {
            if (this.current != e.currentIndex) {
                this.current = e.currentIndex;
            }
            this.page.recommendType = this.current
            this.page.pageNum = 1;
            this.list = []
            this.getData();
        },
        getSelectLevel(val) {
            let dicts = JSON.parse(window.localStorage.getItem("dicts"));
            return dicts["data_civilization_select_level"].filter(d => d.value == val)[0].label
        },
        getSelectType(val) {
            let dicts = JSON.parse(window.localStorage.getItem("dicts"));
            return dicts["data_recommend_type"].filter(d => d.value == val)[0].label
        },
        toDetail(row) {
            // row.scope = "all";
            // if (this.isA) {
            //     row.optTag = "hecha"
            //     let str = JSON.stringify(row)
            uni.navigateTo({
                url: "./../data/dianxingxianjinrenwuDetail?id=" + row.id
            });
            // } else {
            //     let str = JSON.stringify(row)
            //     uni.navigateTo({
            //         url: "./../gongdan/shijianxiangqing?row=" + encodeURIComponent(str)
            //     });
            // }
        },
        search() {

            this.page.pageNum = 1;
            this.list = []
            this.getData();
        },
        getData(cb) {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            this.$api.advancedPage(this.page).then(r => {
                uni.hideLoading();
                this.total = r.data.total;
                if (this.list.length < r.data.total) {
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

.content {
    font-family: alibaba55;

    .swicth {
        position: fixed;
        top: 360upx;
        left: 0;
        width: 100%;
        background: #fff;
        z-index: 1;

        ::v-deep .segmented-control__item {
            flex: none;
        }

        ::v-deep .segmented-control__item:nth-child(1) {
            width: 10% !important;
        }

        ::v-deep .segmented-control__item:nth-child(2) {
            width: 25% !important;
        }

        ::v-deep .segmented-control__item:nth-child(3) {
            width: 18% !important;
        }

        ::v-deep .segmented-control__item:nth-child(4) {
            width: 18% !important;
        }

        ::v-deep .segmented-control__item:nth-child(5) {
            width: 29% !important;
        }
    }

    .searchPanel {
        background: #fff;
        padding: 0 25upx;
        position: fixed;
        top: 0rpx;
        z-index: 2;
        width: calc(100% - 50upx);
        border-bottom: 1px solid #0a50e7;
        margin-top: -5px;
        padding-top: 30rpx;

        .total {
            text-align: center;
            color: #00000050;
            padding: 0 15rpx;
            margin-top: -35rpx;

            span {
                color: #0a50e7;
                margin: 0 10rpx;
            }
        }
    }

    .panel2 {
        width: 750rpx;
        margin: 10rpx 0rpx;
        padding-bottom: 30rpx;
        padding-top: 460rpx;

        .item {
            height: 100%;
            width: 100%;
            background-color: #fff;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
            margin-bottom: 20upx;
            position: relative;

            .top {
                height: 100%;
                width: 100%;
                display: flex;
                flex-direction: row;
                justify-content: space-around;
                background-color: #fff;
                padding: 20upx 0;
                border-bottom: 1upx solid #3d3b3e46;

                image {
                    width: 214rpx;
                    height: 150rpx;
                    border-radius: 15rpx;
                }

                .img {
                    width: 214rpx;
                    height: 150rpx;
                    border-radius: 15rpx;
                    background: #a1a1a141;
                    color: #3d3b3e88;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    font-size: 20upx;
                }

                .text {
                    width: 450rpx;
                    display: flex;
                    flex-direction: column;
                    justify-content: space-between;
                    color: #A1A1A1;
                    font-size: 24rpx;

                    .content {
                        display: -webkit-box;
                        overflow: hidden;
                        text-overflow: ellipsis;
                        word-wrap: break-word;
                        white-space: normal !important;
                        -webkit-line-clamp: 2;
                        -webkit-box-orient: vertical;
                        color: #3D3B3E;
                        font-size: 28rpx;
                    }
                }

                .status {
                    position: absolute;
                    bottom: 80upx;
                    right: 20upx;
                }
            }

            .bottom {
                min-height: 40upx;
                padding: 10upx 20upx;
                display: flex;
                align-items: center;

                .tag {
                    margin-right: 10upx;
                }
            }

        }
    }
}
</style>
