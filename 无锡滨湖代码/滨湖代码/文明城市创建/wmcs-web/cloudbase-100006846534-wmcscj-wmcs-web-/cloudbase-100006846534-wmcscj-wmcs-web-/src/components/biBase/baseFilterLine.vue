<template>
    <!-- 用于看板 + 分类形式的组件外壳 -->
    <div class="base-filter-line last-no-border-bottom">
        <div v-if="!noLabel" class="f-title s-height">
            <i class="fa fa-angle-right margin-right-10" />
            {{ title }}
        </div>
        <div :id="'out-' + id" class="content-wapper" :class="{'less': useAuto && !more}">
            <div :id="'in-' + id" class="in-content-wapper">
                <slot />
            </div>
        </div>
        <slot name="search" />
        <div v-if="useAuto" class="more s-height">
            <div
                v-if="useAuto && !more && line > 1"
                class="more-text"
                @click="changeMore"
            >
                展开
                <i class="fa fa-angle-double-down margin-left-10" />
            </div>
            <div
                v-if="useAuto && more && line > 1"
                class="more-text"
                @click="changeMore"
            >
                收起
                <i class="fa fa-angle-double-up margin-left-10" />
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        noLabel: { type: Boolean, default: false },
        title: { type: String, default: '' },
        // 是否使用超出一行显示展开,默认是,如果为否,则始终展开
        useAuto: { type: Boolean, default: true },
        // 当使用useAuto时, 默认是否展开, 默认否
        autoMore: { type: Boolean, default: false }
    },
    data() {
        return {
            more: this.autoMore, // 是否展开
            line: 1, // 当前行数
            id: this.$get_uuid(),
            register_id: 0
        }
    },
    mounted() {
        if (this.useAuto) {
            this.register_id = setInterval(this.registerComputerLine, 20)
        }
    },
    destroyed() {
        clearInterval(this.register_id)
    },
    methods: {
        changeMore() {
            this.more = !this.more
        },
        registerComputerLine() {
            let one_height = 43 // 一行的高度就是43
            let in_height = 0
            try {
                in_height = document.getElementById('in-' + this.id).offsetHeight
            } catch (e) {
                // 这里有时候会报错,但是不知道为啥报错,放着看看:应该是重渲染没有触发destory,不影响正式环境
            }
            let num = ~~((in_height || one_height) / one_height)
            if (this.line != num) {
                this.line = num
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.base-filter-line{
    width: 100%;
    display: flex;
    flex-direction: row;
    padding-bottom: 10px;
    margin-bottom: 10px;
    border-bottom: 1px dashed #DCDFE6;
    .f-title{
        width: 120px;
        display: flex;
        flex-direction: row;
        align-items: center;
        color: #606266;
        font-size: 14px;
    }
    .content-wapper{
        flex:1;
        width: 0;
        overflow: hidden;
        height: fit-content;
        &.less{
            height: 43px !important;
        }
        .in-content-wapper{
            width: 100%;
            height: fit-content;
        }
    }
    .more{
        width: fit-content;margin-left:10px;
        display: flex;
        flex-direction: row;
        align-items: flex-end;
        .more-text{
            cursor: pointer;
            user-select: none;
            width: 100%;
            height: 44px;
            color: var(--td-brand-color);
            font-size: 14px;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
        }
    }
}
</style>
