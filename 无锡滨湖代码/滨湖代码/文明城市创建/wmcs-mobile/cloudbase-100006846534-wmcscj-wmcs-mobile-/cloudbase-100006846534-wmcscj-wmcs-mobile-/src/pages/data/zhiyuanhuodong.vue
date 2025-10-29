<template>
    <view class="content">
        <div class="panel2" v-if="list.length > 0">
            <div class="item" v-for="i, index in list" :key="index" @click="toDetail(i)">
                <div class="top">
                    <image mode="aspectFit" v-if="i.attchpath" :src="$resource(i.icon)"></image>
                    <div v-else class="img">暂无附件</div>
                    <div class="text">
                        <div class="content">{{ i.name }}</div>
                        <div>
                            <div>地址：{{ i.address }}</div>
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
export default {
    components: { uniTag, oEmpty },
    data() {
        return {
            page: {
                pageSize: 10,
                pageNum: 1
            },
            total: 0,
            flag: false,
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
    },
    methods: {
        toDetail(row) {
            // row.scope = "all";
            // if (this.isA) {
            //     row.optTag = "hecha"
            //     let str = JSON.stringify(row)
            uni.navigateTo({
                url: "./../data/zhiyuanhuodongDetail?id=" + row.id
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
            this.$api.volunteerActivityPage(this.page).then(r => {
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

.content {
    font-family: alibaba55;

    .panel2 {
        width: 750rpx;
        margin: 10rpx 0rpx;
        padding-bottom: 30rpx;

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
