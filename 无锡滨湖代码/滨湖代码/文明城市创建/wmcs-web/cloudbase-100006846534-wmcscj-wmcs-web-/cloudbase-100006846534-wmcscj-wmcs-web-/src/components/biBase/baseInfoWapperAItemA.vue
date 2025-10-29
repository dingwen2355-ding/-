<!-- 展示某个详情的展示看板样式A itemA 一行3列 -->
<template>
    <div class="base-info-wapper-a-item-a">
        <div class="biwaia-lab">
            <slot name="lab" />:
        </div>
        <div class="biwaia-v-con">
            <slot name="con" />
        </div>
    </div>
</template>

<style lang="scss" scoped>
.base-info-wapper-a-item-a{
    width: 32%;
    min-width: 400px;
    display: flex;
    margin-right:20px;
    margin-bottom: 20px;
    font-size: 14px;
    .biwaia-lab{
        color: #606266;
    }
    .biwaia-v-con{
        padding-left:10px;
        flex:1;
        word-break: break-all;
        color: #909399;
    }
}
</style>
