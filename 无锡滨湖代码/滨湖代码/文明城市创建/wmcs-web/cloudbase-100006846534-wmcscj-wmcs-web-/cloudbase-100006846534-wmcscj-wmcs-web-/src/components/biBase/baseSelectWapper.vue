<template>
    <!-- 对于上下高select选择组件,不知道哪里的样式影响,右边的箭头和删除经常偏下,套一层调整样式 -->
    <div class="base-select-wapper">
        <div class="base-select-in-wapper" :class="{'use-w-100': w100}">
            <slot />
        </div>
    </div>
</template>
<script>
export default {
    props: {
        w100: {
            type: Boolean,
            default: false
        }
    }
}
</script>
<style lang="scss" scoped>
.base-select-wapper{
    min-height: 44px;
    line-height: 44px;
    display: flex;
    flex-direction: row;
    align-items: center;
    width: 100%;
    .base-select-in-wapper{
        line-height: 0;
        ::v-deep.el-cascader__tags{
            align-items: center;
        }
        &.use-w-100{
            width: 100%;
            .el-cascader{
                width: 100%;
            }
        }
    }
}
</style>
