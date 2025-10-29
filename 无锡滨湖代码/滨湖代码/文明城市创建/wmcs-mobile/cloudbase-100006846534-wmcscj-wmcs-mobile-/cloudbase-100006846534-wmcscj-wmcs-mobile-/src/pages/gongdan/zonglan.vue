<template>
    <view class="content">
        <div class="searchPanel">
            <uni-forms ref="form" :modelValue="page" label-position="left" label-width="120">
                <uni-forms-item label="问题描述" name="content">
                    <uni-easyinput v-model="page.content" placeholder="请输入问题描述" @change="search()"
                        style="width:80%;float: left;" />
                    <button size="mini" type="primary" style="width:20%;float: right;margin-top: 8upx;font-size:24upx"
                        @click="showMore = !showMore">更多</button>
                </uni-forms-item>
                <uni-forms-item label="点位类型" name="checkPointsTypeId" v-if="pointTypes.length" v-show="showMore">
                    <uni-data-select v-model="page.typeId" :localdata="pointTypes" @change="search()"></uni-data-select>
                </uni-forms-item>
                <!-- <uni-forms-item required label="事发点位" name="checkPointsId">
                    <uni-data-select @change="checkPointsIdChange()" v-model="formData.checkPointsId"
                        :localdata="points"></uni-data-select>
                </uni-forms-item> -->
                <uni-forms-item label="事件类型" name="eventType" v-if="pointTypes.length" v-show="showMore">
                    <uni-data-picker v-model="page.eventType" placeholder="请选择事件类型" popup-title="请选择事件类型"
                        :localdata="eventType" @change="search()" :map="{ text: 'name', value: 'id' }">
                    </uni-data-picker>
                </uni-forms-item>
            </uni-forms>
        </div>
        <div class="panel2" v-if="list.length > 0">
            <div class="item" v-for="i, index in list" :key="index" @click="toDetail(i)">
                <div class="top">
                    <image mode="aspectFit" v-if="i.attchpath" :src="$resource(i.attchpath)"></image>
                    <div v-else class="img">暂无附件</div>
                    <div class="text">
                        <div class="content">{{ i.content }}</div>
                        <div>
                            <div>上报时间：{{ i.reportTime }}</div>
                            <div v-if="isA && i.timelimit">处置时限：{{ i.timelimit }}</div>
                        </div>
                    </div>
                    <div class="status">
                        <uni-tag v-if="i.status == 20" :mark="true" type="primary" size="small" text="待分派"></uni-tag>
                        <uni-tag v-if="i.status == 30" :mark="true" type="success" size="small" text="待受理"></uni-tag>
                        <uni-tag v-if="i.status == 40" :mark="true" type="error" size="small" text="待处置"></uni-tag>
                        <uni-tag v-if="i.status == 50" :mark="true" type="warning" size="small" text="待核查"></uni-tag>
                        <uni-tag v-if="i.status == 60" :mark="true" type="info" size="small" text="已办结"></uni-tag>
                    </div>
                </div>
                <div class="bottom">
                    <uni-tag v-if="i.eventTypeOneName" class="tag" :inverted="false" :text="i.eventTypeOneName" size="small"
                        type="success" />
                    <uni-tag class="tag" :inverted="false" :text="i.eventTypeTwoName" size="small" type="primary" />
                    <uni-tag class="tag" :inverted="false" :text="i.eventTypeName" size="small" type="warning" />
                </div>
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
import uniSearchBar from '../../plugins/uni-search-bar/uni-search-bar.vue';
import uniDataPicker from "@/plugins/uni-data-picker/uni-data-picker.vue"
import uniForms from "@/plugins/uni-forms/uni-forms.vue"
import uniFormsItem from "@/plugins/uni-forms-item/uni-forms-item.vue"
import uniDataSelect from "@/plugins/uni-data-select/uni-data-select.vue"
import uniEasyinput from "@/plugins/uni-easyinput/uni-easyinput.vue"
export default {
    components: { uniTag, oEmpty, uniSearchBar, uniDataPicker, uniForms, uniFormsItem, uniDataSelect, uniEasyinput },
    data() {
        return {
            showMore: false,
            searchValue: "",
            page: {
                pageSize: 10,
                pageNum: 1,
                eventType: "",
                typeId: "",
                checkPoints: "",
                content: ""
            },
            eventType: [],
            pointTypes: [],
            total: 0,
            flag: false,
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
        this.getData();
    },
    onLoad(query) {
        this.$api.ListeventType().then(res => {
            this.eventType = res.data.records
        })
        this.$api.ListcheckPointsType().then(res => {
            this.pointTypes = [];
            res.data.records.forEach(element => {
                this.pointTypes.push({ value: element.id, text: element.name })
            });
        })
    },
    methods: {
        search() {
            
            this.page.pageNum = 1;
            this.list = []
            this.getData();
        },
        getStatus() { },
        toDetail(row) {
            row.scope = "all";
            let str = JSON.stringify(row)
            uni.navigateTo({
                url: "./../screen/shijianxiangqing?row=" + encodeURIComponent(str)
            });
        },
        getData(cb) {
            uni.showLoading({
                mask: true,
                title: "加载中"
            });
            this.$api.zonglan(this.page).then(r => {
                this.total = r.data.total;
                uni.hideLoading();
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
            }).catch(err => {
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

.searchPanel {
    background: #fff;
    padding: 0 25upx;
    position: fixed;
    z-index: 2;
    width: calc(100% - 50upx);
}

.content {
    font-family: alibaba55;

    .panel2 {
        width: 750rpx;
        margin: 10rpx 0rpx;
        padding-bottom: 30rpx;
        padding-top: 100upx;

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
