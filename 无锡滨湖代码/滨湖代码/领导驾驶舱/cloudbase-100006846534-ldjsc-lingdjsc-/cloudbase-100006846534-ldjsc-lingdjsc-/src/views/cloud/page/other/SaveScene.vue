<!--
 * @FileDescription: 场景预览 - 场景保存
 * @Author: yuanhaijun
 * @Date: 2023.04.23
 * @LastEditors: yuanhaijun
 * @LastEditTime: 2023.04.23
 -->
<template></template>

<script>
export default {
    props: {
        cmptData: {
            type: Object,
            default: {},
        },
    },
    name: 'SaveScene',
    data() {
        return {
        };
    },
    async mounted() {
        await this.saveScene();
        window.Player.curMenu = null;
    },
    methods: {
        // 保存场景
        async saveScene() {
            let checkIds = window.Player.$refs.tree.getSelectedTreeIds();
            await window.RTCPlayer.saveProject(checkIds);
            window.sealAPI._settings.setMousePickMask(0);
        },
        handleClose() {
            if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
        }
    },
    async beforeDestroy() {
        await this.handleClose();
    }
}
</script>

<style lang="scss" scoped></style>