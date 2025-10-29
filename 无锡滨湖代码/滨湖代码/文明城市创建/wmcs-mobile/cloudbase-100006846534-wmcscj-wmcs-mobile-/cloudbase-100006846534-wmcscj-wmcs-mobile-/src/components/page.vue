<template>
    <view class="content">
        <div class="panel2">
            <div class="item" v-for="i, index in list" :key="index" @click="toDetail(i)">
            </div>
        </div>
    </view>
</template>

<script>
export default {
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
    onLoad() {
        this.getData()
    },
    methods: {
        toDetail(row) {
            let str = JSON.stringify(row)
            uni.navigateTo({
                url: "./../list/detail?row=" + encodeURIComponent(str)
            });
        },
        getData(cb) {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            this.$api.ListExamineNews(this.page)
                .then(r => {
                    uni.hideLoading();
                    if (this.list.length < r.total) {
                        this.total = r.total;
                        if (this.total <= this.pageSize) {
                            this.flag = false; // 关闭触底加载
                        } else {
                            this.flag = true; // 开启触底加载
                        }
                        if (r.rows.length) {
                            this.list = this.list.concat(r.rows);
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
.content {
    font-family: alibaba55;

    .panel2 {
        width: 700rpx;
        margin: 10rpx 25rpx;
        padding-bottom: 30rpx;

        .item {
            height: 100%;
            width: 100%;
            display: flex;
            flex-direction: row;
            justify-content: space-around;
            margin-bottom: 20rpx;

            img {
                width: 214rpx;
                height: 150rpx;
                border-radius: 15rpx;
            }

            .text {
                width: 450rpx;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                color: #A1A1A1;
                font-size: 24rpx;

                div:nth-child(1) {
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
        }
    }
}
</style>
