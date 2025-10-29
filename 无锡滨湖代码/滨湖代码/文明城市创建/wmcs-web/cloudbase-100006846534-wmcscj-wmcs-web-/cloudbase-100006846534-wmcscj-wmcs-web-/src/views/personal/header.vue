<template>
    <div class="p-back-header">
        <div class="back" @click="backUrl">
            <i class="el-icon-d-arrow-left" />返回
        </div>
        <div class="p-center">{{ title }}</div>
    </div>
</template>
<script>
export default {
    props: {
        title: {
            type: String,
            default: ''
        }
    },
    methods: {
        backUrl() {
            this.$router.go(-1)
        }
    }
}
</script>
<style lang="scss" scoped>
.p-back-header{
    border-bottom: 1px dashed #DCDFE6;
    padding: 5px 0 10px 10px;
    margin-top: 5px;
    display: flex;
    flex-direction: row;
    .back{
        cursor: pointer;
        color: var(--td-brand-color);
        margin-right: 20px;
        &:hover{
            color: var(--td-brand-color-hover);
        }
    }
    .p-center{
        font-size: 16px;
        color: #606266;
        font-weight: bold;
    }
}
</style>
