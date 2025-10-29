<template>
    <view class="content">
        <div class="top">
            <div class="menu">
                <div class="title">工单详情</div>
            </div>
        </div>
        <div class="view">
            <div class="form">
                <div>
                    <span>事件类型：</span>
                    <span>市政环境工程</span>
                </div>
                <div>
                    <span>事件类型：</span>
                    <span>市政环境工程</span>
                </div>
                <div>
                    <span>事件类型：</span>
                    <span>市政环境工程</span>
                </div>
                <div>
                    <span>事件类型：</span>
                    <span>市政环境工程</span>
                </div>
                <div>
                    <span>事件类型：</span>
                    <span>市政环境工程</span>
                </div>
            </div>
            <div class="tbg">实地测评</div>
            <div class="row">
                <div class="left">存在问题：</div>
                <div class="right">
                    <div>外立面有污染</div>
                    <div class="imgs">
                        <div class="img"></div>
                        <div class="img"></div>
                        <div class="img"></div>
                        <div class="img"></div>
                    </div>
                </div>
            </div>
            <div class="tbg">整改情况</div>
            <div class="row">
                <div class="left">评价：</div>
                <div class="right">
                    <div>外立面有污染</div>
                    <div class="imgs">
                        <div class="img"></div>
                        <div class="img"></div>
                        <div class="img"></div>
                        <div class="img"></div>
                    </div>
                </div>
            </div>
        </div>
    </view>
</template>

<script>
import uniIcons from '../../plugins/uni-icons/uni-icons.vue';
import uniSearchBar from '../../plugins/uni-search-bar/uni-search-bar.vue';
export default {
    components: { uniIcons, uniSearchBar },
    data() {
        return {
            searchValue: "",
            page: {
                pageSize: 10,
                pageNum: 1
            },
            total: 0,
            flag: false,
            index: 0,
            array: ["本月", "本季", "本年"],
            array1: ["办结"],
            array2: ["按时"],
            array3: ["红牌"],
            list: [
                {
                    name: "河埒口",
                    status: "已办结",
                    cs: true,
                },
                {
                    name: "河埒口",
                    status: "待处置",
                    cs: false,
                },

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
        // this.getData()
    },
    methods: {
        search() {

        },
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
}

.view {
    width: 100%;
    position: absolute;
    top: 146upx;
    left: 0;
}

.form {
    background: #4770bf;
    color: #fff;
    padding: 30upx;
    width: 700upx;
    div{
        height: 60upx;
        line-height: 60upx;
    }
}
.row{
    width: 690upx;
    padding: 5upx 30upx;
    display: flex;
    flex-direction: row;
    .left{
        width: 30%;
        color: #000;
    }
    .right{
        width: 70%;
        color: #4770bf;
        .imgs{
            width: 100%;
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            .img{
                width: 130upx;
                height: 130upx;
                background: #81818125;
                margin: 10upx;
            }
        }
    }
}
.tbg {
    width: 100%;
    height: 125upx;
    background: url("static/images/tbg.png") no-repeat;
    background-size: 640upx 25upx;
    background-position: 55upx 55upx;
    text-align: center;
    line-height: 125upx;
    font-size: 42upx;
    color: #4770bf;
    font-weight: 700;
}
</style>
