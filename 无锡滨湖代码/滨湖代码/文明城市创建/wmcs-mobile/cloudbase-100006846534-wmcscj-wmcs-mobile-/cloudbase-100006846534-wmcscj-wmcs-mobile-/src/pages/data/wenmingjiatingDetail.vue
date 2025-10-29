<template>
    <view class="content">
        <div class="field">
            <div class="label">家庭名称</div>
            <div class="value">{{ row.name }}</div>
        </div>
        <div class="field">
            <div class="label">所属街道：</div>
            <div class="value">{{ row.regionName }}</div>
        </div>
        <div class="field">
            <div class="label">获评层级：</div>
            <div class="value">{{ getSelectLevel(row.level) }}</div>
        </div>
        <div class="field">
            <div class="label">评选季度：</div>
            <div class="value">{{ row.selectDate || '/' }}</div>
        </div>
        
        <div class="field">
            <div class="label">详细地址：</div>
            <div class="value">{{ row.address || '/' }}</div>
        </div>
        <div class="field">
            <div class="label">联系电话：</div>
            <div class="value">{{ row.recommendTel || '/' }}</div>
        </div>
        <div class="field">
            <div class="label">备注：</div>
            <div class="value" v-html="row.remark || '/'"></div>
        </div>
        <div class="field">
            <div class="label">事迹描述：</div>
            <div class="value" v-html="row.selectDesc || '/'"></div>
        </div>
        <div class="field">
            <div class="label">荣誉奖励：</div>
            <div class="value" v-html="row.honors || '/'"></div>
        </div>
        <div class="field">
            <div class="label">附件：</div>
            <div class="value">
                <div class="file">
                    <image v-for="i, index in files" :key="index" :src="i" @click="preview(index)"></image>
                </div>
            </div>
        </div>
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
            current: 0,
            files: []
        }
    },
    onLoad(query) {
        this.$api.familyDetail(query.id).then(res => {
            this.row = res.data
            this.files = [];
            this.row.iconList.map(re => {
                this.files.push(this.$resource(re.url))
            })
        })
    },
    methods: {
        getSelectLevel(val) {
            if(val == null) return '/'
            let dicts = JSON.parse(window.localStorage.getItem("dicts"));
            return dicts["data_civilization_select_level"].filter(d => d.value == val)[0].label
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
