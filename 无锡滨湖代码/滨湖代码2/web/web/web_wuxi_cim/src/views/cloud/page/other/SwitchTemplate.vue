<!--
 * @FileDescription: 切换业务模板
 * @Author: yuanhaijun
 * @Date: 2023.04.19
 * @LastEditors: yuanhaijun
 * @LastEditTime: 2023.04.19
 -->
<template>
    <div>
        <!-- 模板标题 && 操作：切换业务模板、保存、原始视角 -->
        <template v-if="sceneInfo.needSW">
            <div class="tplTitle">
                <h1>{{ sceneInfo.media.attribute.bizTMPL | filterTitle }}</h1>
            </div>
            <ul class="right-visual-angle biz">
                <li @click="switchBusinessTemplate()">
                    <img src="@/assets/images/cloud/page/cutover.png" />
                </li>
                <li @click="saveScene()">
                    <img src="@/assets/images/cloud/page/save.png" />
                </li>
                <li @click="initVisualAngle()">
                    <img src="@/assets/images/cloud/page/perspective.png" />
                </li>
            </ul>
        </template>

        <!-- 原始视角 -->
        <div v-else class="right-visual-angle def" @click="initVisualAngle()">
            <img src="@/assets/images/cloud/page/reduction.png" alt="" />
        </div>
    </div>
</template>

<script>
export default {
    name: 'SwitchBusinessTemplate',
    data() {
        return {}
    },
    computed: {
        sceneInfo() {
            return this.$store.getters.sceneInfoData;
        },
    },
    filters: {
        filterTitle(val) {
            return val === "city" ? "XX智慧城市" : "XX智慧社区";
        },
    },
    mounted() {
    },
    methods: {
        // 切换业务模板
        switchBusinessTemplate() {
            let media = this.sceneInfo.media;
            if (media && media.attribute) {
                let bizTMPL = media.attribute.bizTMPL;
                if (!!bizTMPL && bizTMPL !== "default")
                    window.RTCPlayer.handleSwitchBusinessTmpl(window.RTCPlayer.bizTMPL, "switch");
            }
        },
        // 保存场景
        async saveScene() {
            let checkIds = window.Player.$refs.tree.getSelectedTreeIds();
            window.RTCPlayer.saveProject(checkIds);
            window.sealAPI._settings.setMousePickMask(0);
        },
        // 初始视角
        initVisualAngle() {
            let ids = window.Player.$refs.tree.checkTreeIds;
            let tileLayerId = ids.length > 0 ? ids[0] : null;
            if (tileLayerId)
                window.sealAPI._tileLayer.focus_biz(tileLayerId); // 自动定位到第一个默认勾选的节点key
            else
                window.sealAPI._camera.set(
                    window.RTCPlayer.x,
                    window.RTCPlayer.y,
                    window.RTCPlayer.z,
                    window.RTCPlayer.pitch,
                    window.RTCPlayer.yaw,
                    0
                );
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

<style lang="scss" scoped>
/* 模板标题 */
.tplTitle {
    position: fixed;
    color: #fff;
    width: 100vw;
    background: url("~@/assets/images/cloud/page/title-bg.png") center center no-repeat;
    background-size: 97% 100%;
    height: 52px;
    margin-top: 26px;
    display: flex;
    justify-content: center;
    align-items: center;

    h1 {
        font-size: 24px;
    }
}

/* 操作 */
.right-visual-angle {
    position: fixed;
    display: flex;
    justify-content: center;

    &.def {
        top: 38px;
        right: 33px;
        width: 32px;
        height: 32px;
        background: rgba(38, 38, 41, 0.8);
        box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
            0px 0px 0px 0px rgba(0, 0, 0, 0.5);
        border-radius: 8px;
        align-items: center;
        cursor: pointer;

        img {
            width: 60%;
        }
    }

    &.biz {
        right: 100px;
        top: 26px;
        padding: 6px 20px;
        min-width: 102px;
        background: url("~@/assets/images/cloud/page/opr-bg.png") center center no-repeat;
        background-size: contain;

        li {
            cursor: pointer;
            padding: 1px;
            border: 1px solid transparent;
            font-size: 0;

            img {
                width: 24px;
            }

            &:hover {
                overflow: hidden;
                background: rgba(94, 117, 241, 0.05);
                box-shadow: 0px 1px 9px 0px #48ebff;
                border-radius: 5px;
                opacity: 0.79;
                border-image: linear-gradient(180deg,
                        rgba(134, 244, 253, 1),
                        rgba(107, 237, 251, 0),
                        rgba(78, 230, 249, 1)) 1 1;
            }

            &:not(:last-child) {
                margin-right: 16px;
            }
        }
    }

}
</style>