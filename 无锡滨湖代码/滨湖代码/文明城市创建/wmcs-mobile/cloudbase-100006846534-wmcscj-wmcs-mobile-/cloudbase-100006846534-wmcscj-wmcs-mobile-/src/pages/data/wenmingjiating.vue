<template>
    <view class="content">
        <div class="searchPanel">
            <uni-forms ref="form" :modelValue="page" label-position="left" label-width="120">
                <uni-forms-item label="关键字" name="selectName">
                    <uni-easyinput v-model="page.selectName" placeholder="请输入名称或地址" @change="search()"/>
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
                    <uni-data-picker v-model="page.regionCode" placeholder="所属街道" popup-title="所属街道" :localdata="regionTree"
                        @change="search()" :map="{ text: 'name', value: 'id' }">
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
                            <div>所属街道：{{ i.regionName }}</div>
                            <div>家庭地址：{{ i.address || '/' }}</div>
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
import uniTag from "@/plugins/uni-tag/uni-tag.vue"
import oEmpty from "@/plugins/o-empty/o-empty.vue"
import uniForms from "@/plugins/uni-forms/uni-forms.vue"
import uniFormsItem from "@/plugins/uni-forms-item/uni-forms-item.vue"
import uniDataSelect from "@/plugins/uni-data-select/uni-data-select.vue"
import uniEasyinput from "@/plugins/uni-easyinput/uni-easyinput.vue"
import customTreeSelect from "@/plugins/custom-tree-select/custom-tree-select.vue"
import uniDataPicker from "@/plugins/uni-data-picker/uni-data-picker.vue"
export default {
    components: { uniTag, oEmpty, uniForms, uniFormsItem, uniEasyinput, uniDataSelect, customTreeSelect, uniDataPicker },
    data() {
        return {
            page: {
                pageSize: 10,
                pageNum: 1,
                selectName: "",
                regionCode: ""
            },
            total: 0,
            flag: false,
            showMore: true,
            regionTree: [],
            levels: [],
            list: [

            ],
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
        getSelectLevel(val) {
            if(val == null) return '/'
            let dicts = JSON.parse(window.localStorage.getItem("dicts"));
            return dicts["data_civilization_select_level"].filter(d => d.value == val)[0].label
        },
        search() {
            
            this.page.pageNum = 1;
            this.list = []
            this.getData();
        },
        toDetail(row) {
            // row.scope = "all";
            // if (this.isA) {
            //     row.optTag = "hecha"
            //     let str = JSON.stringify(row)
            uni.navigateTo({
                url: "./../data/wenmingjiatingDetail?id=" + row.id
            });
            // } else {
            //     let str = JSON.stringify(row)
            //     uni.navigateTo({
            //         url: "./../gongdan/shijianxiangqing?row=" + encodeURIComponent(str)
            //     });
            // }
        },
        getData(cb) {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            this.$api.familyPage(this.page).then(r => {
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

    .searchPanel {
        background: #fff;
        padding: 0 25upx;
        position: fixed;
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
        padding-top: 380rpx;

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
