<template>
    <view class="content">
        <UniSegmentedControl class="swicth" :current="current" :values="items" @clickItem="onClickItem" styleType="button"
            activeColor="#4770bf"></UniSegmentedControl>
        <template v-if="current == 0">
            <div class="field">
                <div class="label">考核周期：</div>
                <div class="value">{{ info.cycle }}</div>
            </div>
            <div class="field">
                <div class="label">点位类型：</div>
                <div class="value">{{ info.checkPointsType }}</div>
            </div>
            <div class="field">
                <div class="label">事发点位：</div>
                <div class="value">{{ info.checkPoints  }}</div>
            </div>
            <div class="field">
                <div class="label">检查项目：</div>
                <div class="value">{{ info.checkItemName || '/' }}</div>
            </div>
            <div class="field">
                <div class="label">状态：</div>
                <div class="value">
                    <uni-tag v-if="info.status == 20" :mark="true" type="primary" size="small" text="待分派"></uni-tag>
                    <uni-tag v-if="info.status == 30" :mark="true" type="success" size="small" text="待受理"></uni-tag>
                    <uni-tag v-if="info.status == 40" :mark="true" type="error" size="small" text="待处置"></uni-tag>
                    <uni-tag v-if="info.status == 50" :mark="true" type="warning" size="small" text="待核查"></uni-tag>
                    <uni-tag v-if="info.status == 60 || info.status == 1" :mark="true" type="info" size="small" text="已办结"></uni-tag>
                    <uni-tag v-if="info.status == 0" :mark="true" type="primary" size="small" text="整改中"></uni-tag>
                </div>
            </div>
            <div class="field">
                <div class="label">责任单位：</div>
                <div class="value">{{ info.deptNames || '/' }}</div>
            </div>
            <div class="field">
                <div class="label">扣分：</div>
                <div class="value">{{ info.score || '/' }}</div>
            </div>
            <div class="field">
                <div class="label">事件类型：</div>
                <div class="value">{{ info.eventTypeOneName }}~{{ info.eventTypeTwoName }}~{{ info.eventTypeName }}</div>
            </div>
            <!-- <div class="field">
                <div class="label">地址描述：</div>
                <div class="value">{{ info.address }}</div>
            </div> -->
            <div class="field">
                <div class="label">问题描述：</div>
                <div class="value">{{ info.content }}</div>
            </div>
            <div class="field" v-if="info.eventSource != 5">
                <div class="label">附件：</div>
                <div class="value">
                    <div class="file">
                        <image v-for="i, index in files" :key="index" :src="i" @click="preview(index)"></image>
                    </div>
                </div>
            </div>

        </template>
        <template v-if="current == 1">
            <timeline v-if="info.nodeHistoryList != null">
                <timelineItem v-for="i in info.nodeHistoryList" :leftTime='i.createTime' :key="i.id">
                    <view class="tripItem">
                        <view class="title">{{ i.nodename }}</view>
                        <view class="tips">{{ i.handler }}：{{ i.handleopinion }}</view>
                        <div class="files" v-if="i.nodefiles">
                            <image v-for="j, index in i.nodefiles" :key="index" :src="j" @click="previewJ(j)"></image>
                        </div>
                    </view>
                </timelineItem>
            </timeline>
        </template>
    </view>
</template>

<script>
import UniSegmentedControl from '../../plugins/uni-segmented-control/uni-segmented-control.vue'
import timeline from '../../plugins/chenbin-timeline/timeLine.vue'
import timelineItem from '../../plugins/chenbin-timeline/timelineItem.vue'
import uniTag from "@/plugins/uni-tag/uni-tag.vue"
export default {
    components: {
        UniSegmentedControl, timeline,
        timelineItem, uniTag
    },
    data() {
        return {
            row: {},
            info: {},
            items: ["事件详情", "事件历程"],
            current: 0,
            files: []
        }
    },
    onLoad(query) {
        this.row = JSON.parse(decodeURIComponent(query.row))
        if(this.row.eventSource == 5) {
            this.items = ['事件详情']
        }
        this.$api.myReportDetail2(this.row.id).then(res => {
            this.info = res.data.eventReport
            this.info.nodeHistoryList = res.data.nodeHistoryList
            if (res.data.docfiles != null) {
                this.files = [];
                res.data.docfiles.map(re => {
                    this.files.push(this.$resource(re))
                })
            }

            if (this.info.nodeHistoryList != null) {
                this.info.nodeHistoryList.forEach(f => {
                    if (f.attchpath) {
                        let nodefiles = []
                        let jfiles = f.attchpath.split(",");
                        jfiles.map(re => {
                            nodefiles.push(this.$resource(re))
                        })
                        f.nodefiles = nodefiles;
                    }
                })
            }

            // console.log(this.info.nodeHistoryList);
        })
    },
    methods: {
        onClickItem(e) {
            if (this.current != e.currentIndex) {
                this.current = e.currentIndex;
            }
        },
        goback() {
            uni.navigateBack()
        },
        preview(index) {
            uni.previewImage({
                current: index,//当前所点击预览的图片地址
                urls: this.files,//这就是当前行图片数据，注意一定要是数组格式
                indicator: 'number',
                loop: true
            });
        },
        previewJ(file) {
            uni.previewImage({
                current: 0,//当前所点击预览的图片地址
                urls: [file],//这就是当前行图片数据，注意一定要是数组格式
                indicator: 'number',
                loop: true
            });
        }
    }
}
</script>

<style lang="scss" scoped>
.content {
    padding: 10upx 0;
    font-family: alibaba55;

    .swicth {
        width: 700upx;
        margin: 0 25upx;
    }

    .field {
        width: 700rpx;
        margin: 25rpx;
        display: flex;
        flex-direction: row;
        justify-content: space-around;

        .label {
            width: 25%;
        }

        .value {
            width: 75%;
            color: #4770bf;

            .file {
                width: 100%;

                image {
                    width: 30%;
                    height: 180upx;
                    margin-right: 2%;
                    margin-bottom: 20upx;
                }
            }
        }
    }

    .tripItem {
        min-height: 140rpx;
        padding: 20rpx 30rpx;
        box-sizing: border-box;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.08);
        border-radius: 10px;
        margin-bottom: 30rpx;

        .title {
            font-size: 28rpx;
            font-family: PingFangSC-Medium, PingFang SC;
            font-weight: 500;
            color: rgba(51, 51, 51, 1);
        }

        .tips {
            font-size: 22rpx;
            font-family: PingFangSC-Regular, PingFang SC;
            font-weight: 400;
            color: rgba(153, 153, 153, 1);
            margin-top: 20rpx;

        }

        .files {
            width: 400upx;

            image {
                width: 25%;
                height: 100upx;
                margin-bottom: 10upx;
                margin-right: 2%;
            }
        }
    }
}
</style>
