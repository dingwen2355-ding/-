<!--
 * @FileDescription: 功能模板
 * @Author: yuanhaijun
 * @Date: 2023.02.09
 * @LastEditors: yuanhaijun
 * @LastEditTime: 2023.02.09
 -->
<template>
    <div>
        <!-- 功能菜单 bottom(switch) -->
        <div :class="['func-tpl', { btm0: !collapsed, switch: needSW }]" ref="funcTPL">
            <!-- 折叠 left -->
            <transition v-if="!needSW" name="el-zoom-in-left">
                <div :class="['collect', { retract: collapsed }]" @click="handleCollect">
                    <i class="el-icon-d-arrow-left"></i>
                </div>
            </transition>

            <!-- menu list -->
            <div v-show="!collapsed" class="nav_1">
                <!-- level_1 “图层树” -->
                <template v-for="(item, i) in menuList0">
                    <div v-if="item.show" :key="`0_${i}`" :class="['pry', { active: item.check }]">
                        <div @click="handleTree(item)">
                            <!-- bottom -->
                            <template v-if="needSW">
                                <svg-icon :icon-class="item.icon" class-name="icon svg-icon-20"></svg-icon>
                                <span> {{ item.name }} </span>
                            </template>
                            <!-- left -->
                            <el-tooltip v-else effect="dark" :content="item.name" placement="right">
                                <svg-icon :icon-class="item.icon" class-name="icon svg-icon-20"></svg-icon>
                            </el-tooltip>
                        </div>
                    </div>
                </template>

                <!-- configured -->
                <template v-for="(item, i) in menuList1">
                    <div v-if="item.show" :key="`1_${i}`" :class="['pry', { active: isCheck(1, item) }]">

                        <!-- level_1 -->
                        <div @click="handleMenu(item)">
                            <!-- bottom -->
                            <template v-if="needSW">
                                <svg-icon :icon-class="item.icon" class-name="icon svg-icon-20"></svg-icon>
                                <span> {{ item.name }} </span>
                            </template>
                            <!-- left -->
                            <el-tooltip v-else effect="dark" :content="item.name" placement="right">
                                <svg-icon :icon-class="item.icon" class-name="icon svg-icon-20"></svg-icon>
                            </el-tooltip>
                        </div>

                        <!-- level_2 -->
                        <template v-if="(item.children || []).length > 0 && isCheck(1, item)">
                            <!-- bottom -->
                            <div v-if="needSW" class="sec btm">
                                <template v-for="(jtem, j) in item.children">
                                    <span v-if="jtem.show" :key="`1_${i}_${j}`" :class="{ active: isCheck(2, jtem) }"
                                        @click="handleMenu(jtem)"> {{ jtem.name }} </span>
                                </template>
                            </div>
                            <!-- left -->
                            <template v-else>
                                <!-- currently selected secondary menu -->
                                <div v-if="isCheck('2_ACT', item.children)" class="sec sel" @click="closeFuncCMPT">
                                    <svg-icon :icon-class="curMenu.icon" class-name="icon"></svg-icon>
                                    <p> {{ curMenu.name }} </p>
                                </div>
                                <!-- all secondary menus -->
                                <div v-else class="sec l">
                                    <div class="title"> {{ item.name }} </div>
                                    <ul class="warp">
                                        <template v-for="(jtem, j) in item.children">
                                            <li v-if="jtem.show" :key="`1_${i}_${j}`" :class="{ active: isCheck(2, jtem) }"
                                                @click="handleMenu(jtem)">
                                                <svg-icon :icon-class="jtem.icon" class-name="icon svg-icon-15"></svg-icon>
                                                <p> {{ jtem.name }} </p>
                                            </li>
                                        </template>
                                    </ul>
                                </div>
                            </template>
                        </template>
                    </div>
                </template>

                <!-- other bottom -->
                <template v-if="needSW">
                    <!-- sxxscl -->
                    <div :class="['pry', { active: isCheck(1, menuList2[0]) }]">
                        <div @click="handleMenu(menuList2[0])">
                            <svg-icon :icon-class="menuList2[0].icon" class-name="icon svg-icon-20"></svg-icon>
                            <span> {{ menuList2[0].name }} </span>
                        </div>
                    </div>
                </template>
            </div>

            <!-- other left -->
            <template v-if="!needSW">
                <div v-show="!collapsed" class="nav_2">
                    <template v-for="(item, i) in menuList2">
                        <div v-if="item.show" :key="`2_${i}`" :class="['pry', { active: isCheck(1, item) }]">
                            <!-- level_1 -->
                            <div @click="handleMenu(item)">
                                <el-tooltip effect="dark" :content="item.name" placement="right">
                                    <svg-icon :icon-class="item.icon" class-name="icon svg-icon-20"></svg-icon>
                                </el-tooltip>
                            </div>
                            <!-- level_2 -->
                            <template v-if="(item.children || []).length > 0 && isCheck(1, item)">
                                <!-- currently selected secondary menu -->
                                <div v-if="isCheck('2_ACT', item.children)" class="sec sel" @click="closeFuncCMPT('full')">
                                    <svg-icon :icon-class="curMenu.icon" class-name="icon"></svg-icon>
                                    <p> {{ curMenu.name }} </p>
                                </div>
                                <!-- all secondary menus -->
                                <div v-else class="sec l">
                                    <div class="title"> {{ item.name }} </div>
                                    <ul class="warp">
                                        <template v-for="(jtem, j) in item.children">
                                            <li :key="`2_${i}_${j}`" :class="{ active: isCheck(2, jtem) }"
                                                @click="handleMenu(jtem)">
                                                <svg-icon :icon-class="jtem.icon" class-name="icon svg-icon-15"></svg-icon>
                                                <p> {{ jtem.name }} </p>
                                            </li>
                                        </template>
                                    </ul>
                                </div>
                            </template>
                        </div>
                    </template>
                </div>
            </template>

        </div>

        <!-- 切换业务模板 -->
        <SwitchBusinessTemplate />

        <!-- 图层树 -->
        <LayerTree ref="tree" v-show="!!menuList0[0] && menuList0[0].check" />

        <!-- 组件 -->
        <component v-if="curMenu && curMenu.fileLOC && funcCMPT" :is="funcCMPT" ref="func" :cmptData="curMenu.data || {}" />
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import { DTSTree } from "@/assets/js/funModule";
export default {
    components: {
        SwitchBusinessTemplate: () => import("./other/SwitchTemplate"), // 切换业务模板
        LayerTree: () => import("./layerTree/Tree"), // 图层树
    },
    name: "FunctionTemplate",
    data() {
        return {
            collapsed: false, // 已折叠
            curMenu: null,
            funcCMPT: null, // 组件
            switching: false, // 切换中（菜单）
            menuList0: [], // “图层树”
            menuList1: [], // 功能模板中配置的“图层树”之外的
            menuList2: [
                {
                    id: 201,
                    name: "刷新显示策略",
                    icon: "cloud-refresh",
                    fileLOC: "other/DisplayStrategy",
                    show: true,
                },
                {
                    id: 202,
                    name: "保存",
                    icon: "cloud-save",
                    fileLOC: "other/SaveScene",
                    show: true,
                },
                {
                    id: 203,
                    name: "设置",
                    icon: "cloud-setting",
                    show: true,
                    children: [
                        {
                            id: 20301,
                            name: "全屏",
                            icon: "cloud-setting-fullscreen",
                            pid: 203,
                            fileLOC: "other/FullScreen",
                            data: {
                                isFull: false,
                            }
                        },
                    ],
                },
            ], // 其他功能
        };
    },
    computed: {
        ...mapGetters(["sceneInfoData"]),
        // 是否需要切换业务模板
        needSW() {
            return this.sceneInfoData.needSW;
        },
        // 菜单是否选中
        isCheck() {
            return function (level, data) {
                if (!this.curMenu) return;

                switch (level) {
                    case 1:
                        return (
                            data.id === this.curMenu.id ||
                            data.id === this.curMenu.pid
                        );
                    case 2:
                        return data.id === this.curMenu.id;
                    case '2_ACT':
                        let node = data.find(i => i.id === this.curMenu.id);
                        return !!node;
                }
            };
        },
        loader() {
            return () => import(`./${this.curMenu.fileLOC}.vue`);
        },
    },
    mounted() {
        // 场景信息
        let media = this.sceneInfoData.media;
        if (media && media.attribute) {
            let model = media.attribute.funModel || [];
            this.initMNU(model);
        }
        // 入口文件存储
        window.Player = this;
    },
    methods: {
        // 折叠菜单
        handleCollect() {
            this.collapsed = !this.collapsed;

            this.closeLayerTree();
            this.closeFuncCMPT();
        },
        // 初始化功能菜单
        initMNU(model) {
            if (Object.prototype.toString.call(model) !== "[object Array]" || model.length == 0) return;

            // 图层树
            let node = model.find(i => i.id === 2);
            if (!!node) {
                let _D = DTSTree.find(i => i.id === 2);
                this.menuList0 = [{ ..._D, show: true, check: false }];
            }
            // 其他
            let nodes = [];
            model.forEach(i => {
                if (i.id === 2) return;
                let _D = DTSTree.find(it => it.id === i.id);
                if (!_D) return;
                let { children, ...attr } = _D;
                let node = { ...attr, children: [], show: true };
                (i.children || []).forEach(j => {
                    let __D = children.find(jt => jt.id === j.id);
                    if (!!__D) node.children.push({ ...__D, show: true });
                });
                nodes.push(node);
            });
            this.menuList1 = nodes;
        },
        // 加载子组件
        async loadComponent() {
            if (!!this.curMenu && !!this.curMenu.fileLOC) {
                // 动态添加组件
                await this.loader().then(() => {
                    this.funcCMPT = () => this.loader();
                });
            } else this.funcCMPT = null;

            let that = this;
            setTimeout(() => {
                that.switching = false;
            }, 6000);
        },
        // 点击一级菜单“图层树”
        handleTree(node) {
            this.$set(node, "check", !node.check);

            this.closeFuncCMPT();
        },
        // 左侧：关闭“图层树”
        closeLayerTree() {
            if (this.needSW) return;
            let node = this.menuList0[0];
            if (!!node && node.check) this.$set(node, "check", false);
        },
        // 左侧：关闭“功能组件”
        closeFuncCMPT(opr) {
            if (this.needSW) return;

            // 取消全屏
            if (opr === "full" && this.curMenu.id === 20301) {
                this.curMenu.data.destroy = true;
                this.$forceUpdate();
            }
            this.handleMenu({}, "tree");
        },
        // 切换功能菜单
        handleMenu(data, opr) {
            if (opr !== "tree") this.closeLayerTree();

            this.switching = true;
            if (
                !!this.curMenu &&
                (this.curMenu.id === data.id || this.curMenu.pid === data.id)
            ) {
                // 二级菜单取消选中（显示选框）
                if (data.hasOwnProperty("pid"))
                    this.curMenu = this.menuList1.find(
                        (item) => item.id === data.pid
                    );
                else this.curMenu = null;
            } else this.curMenu = data;

            this.loadComponent();
        },
        // 关闭子组件
        closeCMPT() {
            if (!this.switching) this.handleMenu(this.curMenu);
        },
    },
};
</script>

<style lang="scss" scoped>
.func-tpl {
    position: fixed;
    z-index: 999;

    /* 默认模板 左侧left */
    &:not(.switch) {
        left: 0;
        top: 0;
        background: rgba($color: #262629, $alpha: 0.8);
        box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
            0px 0px 0px 0px rgba(0, 0, 0, 0.5);

        &.btm0 {
            bottom: 0;
        }

        /* 折叠菜单 */
        .collect {
            padding: 0 4px;
            box-sizing: border-box;
            width: 100%;
            color: #8d8d8d;
            font-size: 16px;
            cursor: pointer;

            &.retract {
                i {
                    transform: rotate(180deg);
                }
            }
        }

        /* 菜单 */
        .nav_1,
        .nav_2 {

            // level_1
            .pry {
                position: relative;

                &>div:first-child {
                    position: relative;
                    padding: 14px 24px;
                    cursor: pointer;
                }

                &.active {
                    &>div:first-child {
                        background: rgba($color: #aed6ff, $alpha: 0.2);

                        &::before {
                            content: "";
                            position: absolute;
                            left: 0;
                            top: 0;
                            bottom: 0;
                            width: 4px;
                            background: linear-gradient(180deg, #9dda79 0%, #017aff 100%);
                        }
                    }
                }
            }

            // level_2
            .sec {
                position: absolute;
                left: 100%;
                top: 0;
                margin-left: 1px;

                /* 左侧：二级 */
                &.l {
                    width: 238px;
                    margin-top: -2px;
                    background: rgba(38, 38, 41, 0.8);
                    box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
                        0px 0px 0px 0px rgba(0, 0, 0, 0.5), 0px 2px 5px 0px rgba(38, 38, 44, 0.5);
                    border-radius: 15px;
                    padding: 16px;
                    box-sizing: border-box;

                    .title {
                        font-size: 16px;
                        font-weight: 400;
                        color: #ffffff;
                    }

                    .warp {
                        display: grid;
                        grid-template-columns: repeat(3, 33.33%);

                        li {
                            cursor: pointer;
                            margin-top: 10px;
                            width: 58px;
                            height: 58px;
                            background: #4d515a;
                            border: 1px solid #4d515a;
                            box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
                                0px 0px 0px 0px rgba(0, 0, 0, 0.5);
                            border-radius: 8px;
                            display: inline-flex;
                            flex-direction: column;
                            justify-content: center;
                            align-items: center;

                            &:not(:nth-child(3n)) {
                                margin-right: 16px;
                            }

                            img {
                                width: 24px;
                                height: 24px;
                            }

                            p {
                                text-align: center;
                                font-size: 12px;
                                font-weight: 400;
                                color: rgba(255, 255, 255, 0.8);
                            }

                            &.active {
                                border: 1px solid #0078d4;
                                background: #535d84;
                            }
                        }
                    }
                }

                /* 二级 选中的菜单 */
                &.sel {
                    width: 86px;
                    background: url("~@/assets/images/cloud/page/state.png") center no-repeat;
                    background-size: 100% 100%;
                    padding: 11px 0 11px 20px;
                    box-sizing: border-box;
                    color: rgba(255, 255, 255, 0.8);
                    font-size: 12px;
                    text-align: center;
                    cursor: pointer;

                    .icon {
                        font-size: 22px;
                        margin-bottom: 3px;
                    }

                    img {
                        width: 24px;
                        max-height: 24px;
                    }
                }
            }
        }

        /* 功能菜单 */
        .nav_1 .pry {

            &:nth-last-child(3),
            &:nth-last-child(1) {
                &::after {
                    content: "";
                    position: absolute;
                    width: 60%;
                    left: 50%;
                    transform: translateX(-50%);
                    height: 1px;
                    background: #5d6171;
                    border-radius: 1px;
                }
            }
        }

        /* 其他菜单 */
        .nav_2 {
            position: absolute;
            bottom: 0;

            .pry::before {
                content: "";
                position: absolute;
                width: 60%;
                left: 50%;
                transform: translateX(-50%);
                height: 1px;
                background: #5d6171;
                border-radius: 1px;
            }

            .sec.l {
                top: auto;
                bottom: 0;
                margin-top: auto;
            }
        }
    }



    /* 需要切换模板 底部bottom */
    &.switch {
        bottom: 28px;
        left: calc(50% + 69px);
        transform: translateX(-50%);
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 8px 12px;
        border-radius: 12px;
        background: linear-gradient(180deg,
                rgba(21, 25, 37, 0.85) 0%,
                rgba(26, 31, 46, 0.9) 100%);
        box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
            0px 0px 0px 0px rgba(0, 0, 0, 0.5);

        .nav_1 {
            display: flex;
        }

        // level_1
        .pry {
            position: relative;

            // 图层树
            &:first-child {
                position: absolute;
                right: calc(100% + 24px);

                &::before {
                    content: "";
                    position: absolute;
                    width: calc(100% + 24px);
                    height: calc(100% + 16px);
                    left: -12px;
                    top: -8px;
                    display: block;
                    border-radius: 12px;
                    background: linear-gradient(180deg,
                            rgba(21, 25, 37, 0.85) 0%,
                            rgba(26, 31, 46, 0.9) 100%);
                    box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
                        0px 0px 0px 0px rgba(0, 0, 0, 0.5);
                }
            }

            // 选中
            &.active {
                &>div:not(.sec) {
                    background: linear-gradient(180deg,
                            rgba(80, 109, 155, 0.53) 0%,
                            rgba(44, 60, 80, 0.9) 100%);
                    box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
                        0px 0px 0px 0px rgba(0, 0, 0, 0.5),
                        0px 11px 17px -11px rgba(7, 8, 15, 0.95);
                    filter: blur(0px);

                    span {
                        color: #48ebff;
                    }
                }

                .sec {
                    display: flex !important;
                }
            }

            &>div:not(.sec) {
                position: relative;
                min-width: 50px;
                padding: 10px;
                border-radius: 12px;
                cursor: pointer;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }

            img {
                width: 30px;
                height: 30px;
            }

            span {
                font-size: 12px;
                font-weight: 400;
                color: rgba(255, 255, 255, 0.8);
                margin-top: 2px;
            }

            .svg-icon {
                font-size: 16px;
            }
        }

        // level_2
        .sec {
            position: absolute;
            bottom: calc(100% + 16px);
            left: 50%;
            transform: translateX(-50%);
            // display: flex;
            align-items: center;
            justify-content: center;
            display: none;

            background: linear-gradient(180deg,
                    rgba(21, 25, 37, 0.7) 0%,
                    rgba(15, 18, 26, 0.8) 100%);
            box-shadow: 0px 0px 0px 0px rgba(255, 255, 255, 0.15),
                0px 0px 0px 0px rgba(0, 0, 0, 0.5);
            border-radius: 7px;
            padding: 10px 10px;
            box-sizing: border-box;

            // &.wrap {
            //     flex-flow: row wrap;
            //     min-width: 1400%;
            // }

            span {
                display: flex;
                justify-content: center;
                align-items: center;
                white-space: nowrap;
                margin: 0 2px;
                padding: 5px 15px;
                cursor: pointer;
                font-size: 12px;
                font-weight: 400;
                color: rgba(255, 255, 255, 0.8);

                &.active {
                    background-image: url("~@/assets/images/cloud/page/sec-menu-bg.png");
                    background-size: 100% 100%;
                }
            }
        }
    }

}
</style>
