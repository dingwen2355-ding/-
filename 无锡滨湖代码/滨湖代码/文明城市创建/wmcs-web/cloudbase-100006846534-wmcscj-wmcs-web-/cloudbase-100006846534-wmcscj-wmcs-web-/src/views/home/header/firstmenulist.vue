<template>
    <div class="first-menu-wapper-el">
        <el-scrollbar style="height:100%; width:100%;">
            <div class="f-in-menu-wapper">
                <div
                    v-for="(menu, index) in menuList"
                    :key="index"
                    class="m-item"
                    :class="{'select': active == menu.link}"
                    @click="changeFirstMenu(menu)"
                >
                    <span class="txt">{{ menu.name || '' }}</span>
                </div>
            </div>
        </el-scrollbar>
    </div>
</template>
<script>
const config = require('@/config/index')
export default {
    data() {
        return {
            config: config,
            nowFirst: { id: -1 }
        }
    },
    computed: {
        menuList() {
            if (config.firstMenuInTop) {
                return this.$store.state.menuInfo.data ? this.$store.state.menuInfo.data : []
            }
            return []
        },
        active() {
            let matched_path = this.$route.matched.map(i => { return i.path })
            let f_path = this.menuList.find(i => {
                return matched_path.indexOf(i.link) > -1
            })
            if (f_path) {
                this.$store.commit('setSecondMenu', f_path['children'] || [])
                return f_path.link
            } else {
                this.$store.commit('setSecondMenu', [])
            }
            return '-nopath'
        }
    },
    methods: {
        changeFirstMenu(item) {
            // if (item.id == this.nowFirst['id']) return
            if (item.link == this.active) return
            // 是否有二级菜单
            if (item['children']) {
                this.$store.commit('setSecondMenu', item['children'] || [])
                this.checkToPath()
            } else {
                this.$router.push(item.link)
                this.$store.commit('setSecondMenu', item['children'] || [])
            }
            this.nowFirst = item
        },
        get_next_first(arr) {
            if (!arr || arr.length == 0) {
                return ''
            }
            if (arr[0]['children']) {
                return this.get_next_first(arr[0].children)
            } else {
                return arr[0].link
            }
        },
        checkToPath() {
            let now_url = this.get_next_first(this.$store.state.nowSecondMenuInfo)
            if (now_url) {
                this.$router.push(now_url)
            }
        }
    }
}
</script>
<style lang="scss">
.first-menu-wapper-el{
    width: 100%;
    height: 100%;
    .f-in-menu-wapper{
        height: 100%;
        display: flex;
        flex-direction: row;
        justify-items: center;
        padding-bottom: 20px;
    }
    .m-item{
        margin-top: 20px;
        min-width: fit-content;
        height: 40px;
        font-size: 16px;
        display: flex;
        justify-items: center;
        align-items: center;
        color: #303133;
        font-weight: bold;
        .txt{
            padding: 10px;
            cursor: pointer;
        }
        &:hover{
            color: #d7d7d7;
        }
        &.select{
            color: #fff;
            .txt{
                border-bottom: 2px solid #fff;
            }
        }
    }
    .el-scrollbar__bar.is-vertical{
        display: none;
    }
    .el-scrollbar__bar{
        opacity: 1 !important;
    }
    .el-scrollbar__thumb{
        background: #fff;
    }
    .el-scrollbar__wrap{
        overflow-y: hidden;
        overflow-x: auto;
        /* 多出来的20px是横向滚动条默认的样式 */
        height: calc(100% + 20px);
        .el-scrollbar__view{
            height: 100%;
        }
    }
}
</style>

