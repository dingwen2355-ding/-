<template>
    <div class="sidebar">
        <!-- 添加一个用于计算滚动条的div -->
        <div
            id="compute-scroll-width-out"
            style="height: 100px;overflow-y: scroll;width:100px;position: fixed;left: -100000px;top:-100000px;"
        >
            <div class="compute-scroll-width-in" />
        </div>
        <div class="el-sco-wapper">
            <el-scrollbar class="no-x-scoll sidebar-left">
                <!-- 动画要手动关闭,它和外层的动画会有冲突,表现抽搐 -->
                <el-menu :collapse="isCollapse" :unique-opened="true" :collapse-transition="false" :default-active="activeMenu" :background-color="variables.menuBg" mode="vertical">
                    <SidebarItem v-for="menu in MenuList" :key="menu.id" :item="menu" :class="{'bold': menu.id == 1}" />
                </el-menu>
            </el-scrollbar>
        </div>
        <div class="version">
            <div v-if="config.verUrlCode">
                <el-link class="margin-top--2" type="info" :href="config.verUrlCode" target="_blank">版本: v{{ versionNow }}</el-link>
            </div>
            <div v-else>版本: v{{ versionNow }}</div>
            <el-link v-if="config.verUrlDesc" class="margin-top--2" type="primary" :href="config.verUrlDesc" target="_blank">更新说明</el-link>
        </div>
    </div>
</template>

<script>
import { getVersion } from '@/config/version/index'
import SidebarItem from './sidebarItem'
import variables from '@/assets/style/js.scss'
// import { menu } from '@/api/user'
const sysConfig = require('@/config')
export default {
    components: { SidebarItem },
    props: {
        isCollapse: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            compute_scroll_task_id: 0,
            config: sysConfig,
            // MenuList: [],
            defaultMenuId: '',
            listArr: [],
            versionNow: getVersion()
        }
    },
    computed: {
        variables() {
            return variables
        },
        MenuList() {
            if (!this.config.firstMenuInTop) {
                return this.$store.state.menuInfo.data ? this.$store.state.menuInfo.data : []
            } else {
                return this.$store.state.nowSecondMenuInfo
            }
        },
        activeMenu() {
            if (!this.$store.state.menuInfo.data) return
            // 配置指定没有路由的页面指向固定的路由
            let returnPath = this.$route.path
            // 配置方式
            if (sysConfig.noRouterMenuRouter) {
                sysConfig.noRouterMenuRouter.forEach((item) => {
                    if (this.$route.path.indexOf(item[0]) > -1) {
                        returnPath = item[1]
                    }
                })
            }
            // 配置大于约定
            if (returnPath != this.$route.path) return returnPath
            // 约定方式
            // 先找有没有完全匹配的
            let maxUrl = ''
            function equalAll(arr) {
                arr.forEach(i => {
                    if (i['children']) {
                        equalAll(i.children)
                    } else {
                        if (returnPath == i.link) maxUrl = i.link
                    }
                })
            }
            equalAll(this.MenuList)
            if (maxUrl) return maxUrl
            // 再找有没有上级匹配的
            function get_next_menu_list(arr, menu) {
                menu.forEach(i => {
                    if (i.link.indexOf('/') > -1) {
                        arr.push(i.link)
                    }
                    if (i.children) {
                        get_next_menu_list(arr, i.children)
                    }
                })
            }
            let allMenuList = []
            // 获取所有带路径的菜单(只有带路径的菜单才可以高亮)
            get_next_menu_list(allMenuList, this.$store.state.menuInfo.data)
            let now_path_arr = this.$route.path.split('/')
            now_path_arr.pop()
            let now_path_high = now_path_arr.join('/')
            allMenuList.forEach((item) => {
                let last_path_arr = item.split('/')
                last_path_arr.pop()
                let last_path = last_path_arr.join('/')
                if (now_path_high == last_path) {
                    returnPath = item
                }
            })
            return returnPath
        }
    },
    mounted() {
        // 计算滚动条真实宽度并让菜单栏右侧隐藏滚动条: 主要解决缩小页面会显示滚动条......
        this.compute_scroll_task_id = setInterval(() => {
            let c_d = document.getElementById('compute-scroll-width-out')
            let scroll_width = c_d.offsetWidth - c_d.clientWidth + 10
            let menu_scroll = document.querySelector('.sidebar-left .el-scrollbar__wrap')
            menu_scroll.setAttribute('style', `margin-right:-${scroll_width}px !important`)
        }, 20)
    },
    destroyed() {
        clearInterval(this.compute_scroll_task_id)
    },
    methods: {
        get_next_first(arr) {
            if (!arr || arr.length == 0) {
                return ''
            }
            if (arr[0]['children']) {
                return this.get_next_first(arr[0].children)
            } else {
                return arr[0].link
            }
        }
    }
}
</script>
<style lang="scss">
.sidebar {
    user-select: none;
    display: flex;
    flex-direction: column;
    .el-sco-wapper{
        flex:1;
        height: 0;
    }
    .sidebar-left{
        height: calc(100% - 10px);
        width:100%;
    }
    .version{
        margin-bottom: -10px;
        overflow: hidden;
        text-align: center;
        line-height: 20px;
        height: 40px;
        // @include h-lh(30px);
        color: $thGTInfo;
        ::v-deep .el-link--inner{
            text-decoration: underline;
        }
    }
    // min-height: 100%;
    // padding-bottom: 40px;
}
// 强制重新设置,使菜单折叠后,图标相对居中
.el-menu--collapse {
    .el-tooltip {
        padding-left: 18px !important;
    }
    .el-submenu__title {
        padding-left: 18px !important;
    }
}
.bold {
    span {
        font-weight: bold
    }
}
// 左侧菜单
.el-menu-item {
    @include h-lh(35px);
    i {
        color: $thSideBarMIBack !important;
    }
    i,
    span {
        font-size: 15px !important;
    }
    &.submenu-title-noDropdown{
        // 一级菜单 无下拉子项
        @include h-lh(40px);
        span {
            font-weight: bold;
        }
    }
    color: $thSideBarMText !important;
    &:hover {
        background-color: $thSideBarMHover !important;
        i {
            color: $thSideBarMHoverText !important;
        }
        span {
            color: $thSideBarMHoverText !important;
        }
    }
    &.is-active {
        background-color: $thSideBarMActiveBack !important;
        i {
            color: $thSideBarMActiveText !important;
        }
        span {
            color: $thSideBarMActiveText !important;
        }
    }
}

// 下拉菜单
.el-submenu__title {
    @include h-lh(40px);
    background-color: $thSideBarMFBack !important;
    color: $thSideBarMText !important;
    i {
        color: $thSideBarMIBack !important;
    }
    span {
        font-weight: bold;
        color: $thSideBarMText !important;
    }
    i,
    span {
        font-size: 15px;
    }
    color: $thSideBarMText !important;
    &:hover {
        background-color: $thSideBarMHover !important;
        i {
            color: $thSideBarMHoverText !important;
        }
        span {
            color: $thSideBarMHoverText !important;
        }
    }
}

// 子集菜单背景
.sidebar-munu-list {
    // margin-bottom: 8px;
    .nest-menu {
        margin-bottom: 0;
        .el-menu-item {
            @include h-lh(35px);
            background-color: $thSideBarChBack !important;
            &:hover {
                background-color: $thSideBarMHover !important;
                i {
                    color: $thSideBarMHoverText !important;
                }
                span {
                    color: $thSideBarMHoverText !important;
                }
            }
            &.is-active {
                background-color: $thSideBarMActiveBack !important;
                i {
                    color: $thSideBarMActiveText !important;
                }
                span {
                    color: $thSideBarMActiveText !important;
                }
            }
        }
    }
}

// 默认选中的样式
</style>
