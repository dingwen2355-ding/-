<!--
 * @FileDescription: 场景预览 - 黑暗模式
 * @Author: yuanhaijun
 * @Date: 2023.03.14
 * @LastEditors: yuanhaijun
 * @LastEditTime: 2023.03.14
 -->
<template>
    <cloud-tmpl title='黑暗模式' :scrollHeight='10' @close='handleClose'>
        <template v-slot:content>
            <div class="warp-switch">
                开启暗黑模式
                <el-switch v-model="isDark" active-color="#13ce66" inactive-color="#979797" @change="handleChange">
                </el-switch>
            </div>
        </template>
    </cloud-tmpl>
</template>

<script>
export default {
    name: 'DarkMode',
    data() {
        return {
            isDark: window.sealAPI._weather.isDark
        };
    },
    mounted() {
    },
    methods: {
        handleChange() {
            window.sealAPI._weather.setDarkMode_biz(this.isDark);
        },
        handleClose() {
            if (this.$parent.hasOwnProperty('funcCMPT')) this.$parent.closeCMPT();
        }
    },
    async beforeDestroy() {
        await this.handleClose();
    }
}
</script>

<style lang="scss" scoped>
.warp-switch {
    display: flex;
    align-items: center;
    height: 70px;

    .el-switch {
        margin-left: 6px;
    }
}
</style>