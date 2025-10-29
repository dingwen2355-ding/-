<!-- 展示某个详情的展示看板样式A -->
<template>
    <div class="base-info-wapper-a">
        <slot />
    </div>
</template>
<style lang="scss" scoped>
.base-info-wapper-a{
    width: 100%;
    padding: 20px;
    padding-bottom: 10px;
    border: 1px solid #EBEEF5;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-top:5px solid var(--td-brand-color);
}
</style>
