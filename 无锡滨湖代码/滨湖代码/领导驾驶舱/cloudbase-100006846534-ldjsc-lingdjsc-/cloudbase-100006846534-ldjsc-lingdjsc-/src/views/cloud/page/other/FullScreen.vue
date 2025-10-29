<!--
 * @FileDescription: 场景预览 - 全屏
 * @Author: yuanhaijun
 * @Date: 2023.04.19
 * @LastEditors: yuanhaijun
 * @LastEditTime: 2023.04.19
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
    name: 'FullScreen',
    data() {
        return {

        };
    },
    mounted() {
        let self = this;
        window.onresize = function () {
            let full = Math.abs(
                window.screen.height - window.document.documentElement.clientHeight
            ) <= 17;
            let node = self.getNode();
            if (!!node) {
                node.name = `${full ? '取消' : ''}全屏`;
                node.data.isFull = full;
                self.$forceUpdate();
            }
            self.handleClose();
        };

        if (this.cmptData.isFull) this.quitFullScreen();
        else this.enterFullScreen();
    },
    methods: {
        getNode() {
            let node = window.Player.menuList2.find(i => i.id === 203);
            if (!!node) {
                return (node.children || []).find(i => i.id === 20301);
            }
            return;
        },
        // 进入全屏
        enterFullScreen() {
            let element = document.documentElement;
            if (element.requestFullscreen) element.requestFullscreen();
            else if (element.webkitRequestFullScreen)
                element.webkitRequestFullScreen();
            else if (element.mozRequestFullScreen) element.mozRequestFullScreen();
            else if (element.msRequestFullscreen) element.msRequestFullscreen(); // IE11
        },
        // 退出全屏
        quitFullScreen() {
            if (document.exitFullscreen) {
                document.exitFullscreen();
            } else if (document.webkitCancelFullScreen) {
                document.webkitCancelFullScreen();
            } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
            } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
            }
        },
        handleClose() {
            if (this.$parent.hasOwnProperty("funcCMPT")) this.$parent.closeCMPT();
        }
    },
    async beforeDestroy() {
        if (this.cmptData.destroy) {
            let node = this.getNode();
            if (!!node) {
                node.name = `全屏`;
                node.data.isFull = false;
                node.data.destroy = null;
                this.$forceUpdate();
            }
            this.quitFullScreen();
        }
        await this.handleClose();
    }
}
</script>

<style lang="scss" scoped></style>