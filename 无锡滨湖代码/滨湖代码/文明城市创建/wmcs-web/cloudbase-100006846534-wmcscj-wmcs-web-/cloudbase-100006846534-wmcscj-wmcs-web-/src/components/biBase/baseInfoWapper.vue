<template>
    <!-- 产品特别喜欢的蓝框已选 -->
    <div class="base-info-wapper">
        <i class="el-icon-info icon" />
        <slot />
    </div>
</template>
<style lang="scss" scoped>
.base-info-wapper{
    padding: 8px 40px 8px 10px;
    border: 1px solid #bae7ff;
    background: #e6f7ff;
    border-radius: 5px;
    width: fit-content;
    font-size: 13px;
    display: flex;
    flex-direction: row;
    align-items: center;
    color: #606266;
    .icon{
        font-size: 14px;
        color: #409EFF;
        margin-right: 10px;
    }
    .blod{
        margin-left: 5px;
        font-weight: bold;
        color: #606266;
    }
}
</style>
