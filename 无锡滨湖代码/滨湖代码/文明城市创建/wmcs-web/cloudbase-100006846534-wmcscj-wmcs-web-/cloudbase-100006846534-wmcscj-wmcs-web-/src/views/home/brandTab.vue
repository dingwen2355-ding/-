<template>
    <div class="brand-wapper">
        <!-- 首页 -->
        <!-- td风格 -->
        <t-tag
            v-if="firstMenu"
            theme="primary"
            :variant="firstMenu.link == activeMenu ? '' : 'light'"
            class="margin-right-5"
            @click="toPath(firstMenu.link)"
        >{{ firstMenu && firstMenu.name }}</t-tag>
        <t-tag
            v-for="(menu, index) in tagmenuList"
            :key="index"
            theme="primary"
            :variant="menu.link == activeMenu ? '' : 'light'"
            :closable="true"
            class="margin-right-5"
            @click="toPath(menu.link)"
            @close="pageClose(index)"
        >{{ menu.name }}</t-tag>
        <!-- element风格 -->
        <!-- <el-tag
            v-if="firstMenu"
            size="medium"
            :effect="firstMenu.link == activeMenu ? 'dark' : 'plain'"
            class="margin-right-5"
            @click="toPath(firstMenu.link)"
        >{{ firstMenu && firstMenu.name }}</el-tag>
        <el-tag
            v-for="(menu, index) in tagmenuList"
            :key="index"
            size="medium"
            :effect="menu.link == activeMenu ? 'dark' : 'plain'"
            :closable="true"
            class="margin-right-5"
            @click="toPath(menu.link)"
            @close="pageClose(index)"
        >{{ menu.name }}</el-tag> -->
    </div>
</template>
<script>
const sysConfig = require('@/config')
export default {
    data() {
        return {
            config: sysConfig,
            firstPagePath: '',
            stopPush: false, // 因为t-tag在close会同时触发click,导致关闭非本页面会切换页面,阻断一下
            tagmenuList: []
        }
    },
    computed: {
        firstMenu() {
            let mu = this.$store.state.firstMenu
            this.initMenu(mu)
            return mu
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
            // let fatherUrlArr = []
            function equalAll(arr, fatherArr) {
                arr.forEach(i => {
                    let farr = JSON.parse(JSON.stringify(fatherArr))
                    farr.push(i.link)
                    if (i['children']) {
                        equalAll(i.children, farr)
                    } else {
                        if (returnPath == i.link) {
                            maxUrl = i.link
                            farr.push(i.link)
                            // fatherUrlArr = farr
                        }
                    }
                })
            }
            equalAll(this.MenuList, [])
            if (maxUrl) {
                return maxUrl
            }
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
            // 再次获取展开列表
            equalAll(this.MenuList, [])
            if (maxUrl) {
                return maxUrl
            }
            return returnPath
        }
    },
    watch: {
        '$route': 'changePath'
    },
    methods: {
        initMenu(m) {
            // 初始化
            let _this = this
            if (m) {
                _this.firstPagePath = m.link
            }
            setTimeout(() => {
                // 延时一会, 确保当前的页面跳转
                console.log('初始化首页面包屑')
                _this.changePath()
            }, 200)
        },
        changePath() {
            let _this = this
            function getthisMenu(arr) {
                arr.forEach(i => {
                    if (i.link === activePath) {
                        nowMenu = i
                    } else {
                        if (i.children && i.children.length > 0) {
                            getthisMenu(i.children)
                        }
                    }
                })
            }
            let activePath = this.activeMenu
            let nowMenu = null
            getthisMenu(this.MenuList)
            if (nowMenu) {
                let nowAllPath = this.tagmenuList.map(i => { return i.link })
                if (nowAllPath.indexOf(nowMenu.link) < 0 && nowMenu.link !== _this.firstPagePath) {
                    this.tagmenuList.push(nowMenu)
                }
            }
        },
        toPath(path) {
            if (this.stopPush) {
                this.stopPush = false
                return
            }
            if (path != this.activeMenu) {
                this.$router.push(path)
            }
        },
        getNextPath(index) {
            let length = this.tagmenuList.length - 1
            let nextPath = this.firstMenu.link // 首页
            let nextIndex = index + 1
            let lastIndex = index - 1
            if (nextIndex <= length) {
                // 优先右边
                nextPath = this.tagmenuList[index + 1].link
                return nextPath
            }
            if (lastIndex >= 0) {
                // 然后左边
                nextPath = this.tagmenuList[index - 1].link
                return nextPath
            }
            return nextPath
        },
        pageClose(index) {
            this.stopPush = true
            let nowPath = this.tagmenuList[index].link
            if (nowPath == this.activeMenu) {
                // 关闭的是当前打开的
                console.log('关闭当前打开的')
                let nextPath = this.getNextPath(index)
                this.$router.push(nextPath)
            }
            this.tagmenuList.splice(index, 1)
        }
    }
}
</script>
<style scoped lang="scss">
.brand-wapper{
    position: absolute;
    left: 1px;right:0px;
    top:0px;
    height: 40px;
    background: #ffffff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;
    padding: 0 20px;
    overflow: auto;
    ::v-deep span{
        user-select: none;
        cursor: pointer;
    }
}
</style>
