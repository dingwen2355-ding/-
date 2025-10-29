<template>
    <div class="base-go-back-wapper">
        <el-page-header :content="title" @back="base_go_back" />
    </div>
</template>
<script>
export default {
    props: {
        title: { type: String, default: '' }
    },
    methods: {
        base_go_back() {
            this.$router.back(-1)
        }
    }
}
</script>
<style lang="scss" scoped>
.base-go-back-wapper{
    padding: 10px;
    width: 100%;
    // margin-bottom: 10px;
    // background: #fff;
    color: var(--td-brand-color) !important;
    // color: #ffffff;
    border-bottom: 1px solid var(--td-brand-color) !important;
    ::v-deep.el-page-header__content{
        color: var(--td-brand-color) !important;
        // color: #ffffff;
        font-weight: bold;
    }
    ::v-deep.el-page-header{
        .el-page-header__left::after{
            background: var(--td-brand-color) !important;
        }

    }
}
</style>
