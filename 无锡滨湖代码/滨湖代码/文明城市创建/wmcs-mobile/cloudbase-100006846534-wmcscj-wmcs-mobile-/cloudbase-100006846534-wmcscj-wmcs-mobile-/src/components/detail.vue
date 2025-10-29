<template>
    <view class="content">
        <div class="readTitle">{{ row.name }}</div>
        <div class="tRow">
            <div>来源部门：<span class="value">{{ row.deptName || '滦州市' }}</span> </div>
            <div>发布时间：<span class="value">{{ row.createTime }}</span></div>
        </div>
        <div class="form ql-editor" v-html="row.content">

        </div>
    </view>
</template>

<script>
export default {
    data() {
        return {
            row: {}
        }
    },
    onLoad(query) {
        this.row = JSON.parse(decodeURIComponent(query.row))
    },
    methods: {
        toDetail() {
            uni.navigateTo({
                url: "./../banshizhinan/detail"
            });
        },
        goback() {
            uni.navigateBack()
        },
    }
}
</script>

<style lang="scss" scoped>
.content {
    font-family: alibaba55;

    .readTitle {
        text-align: center;
        font-size: 36rpx;
        font-weight: 900;
    }

    .form {
        width: 700rpx;
        margin: 25rpx;
    }

    .value {
        font-size: 28rpx;
        text-indent: 2em;
    }

    .tRow {
        width: 700rpx;
        margin: 25rpx;
        display: flex;
        flex-direction: row;
        justify-content: space-around;

        .value {
            color: #166DC4;
        }
    }
}
</style>
