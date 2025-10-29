<template>
    <div class="body-in">
        <div class="w-100" style="border-bottom: 1px solid #ececec;">
            <BiHeader @collapse="collapse" />
        </div>
        <div class="flex-1 h-0 show-flex-r">
            <BiSideBar :isCollapse="isCollapse" class="subNav" :class="{'collapse': isCollapse}" />
            <div class="flex-1 w-0 h-100 main" style="background: #eeeeee;padding: 20px;padding-top:60px;position:relative;">
                <brandTab />
                <el-scrollbar ref="biMainScroll" style="height:100%; width:100%;" class="no-x-scoll">
                    <router-view />
                </el-scrollbar>
            </div>
        </div>
    </div>
</template>

<script>
import { searchFilterStoreMixins } from '@/utils/mixins'
import BiHeader from './header'
import BiSideBar from './sidebar'
import brandTab from './brandTab'
export default {
    components: {
        BiHeader,
        BiSideBar,
        brandTab
    },
    mixins: [searchFilterStoreMixins],
    data() {
        return {
            isCollapse: false,
            sTop: 0,
            searchFilterInId: null
        }
    },
    watch: {
        $route(to, from) {
            // 重置复位页面的滚动条
            this.$refs.biMainScroll.wrap.scrollTop = 0
        }
    },
    created() {
        // 搜索条件组件的轮询缓存
        // this.searchFilterInId = setInterval(this.InverSearchFilter, 600)
    },
    destroyed() {
        // 清理搜索轮询事件,减少重复
        clearInterval(this.searchFilterInId)
    },
    methods: {
        collapse(i) {
            // 菜单横向收起事件
            this.isCollapse = !i
            // this.$diyWindowResize()
        }
    }
}
</script>

<style lang='scss'>
// 路由过渡动画
// .fade-enter-active,
// .fade-leave-active {
//   transition: opacity 3s ease;
// }

// .fade-enter-from,
// .fade-leave-to {
//   opacity: 0;
// }
.body-in{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    .subNav {
        width: 230px;
        height: 100%;
        transition: width 0.3s;
        background-color: #ffffff;
        padding-top: 8px;
        padding-bottom: 20px;
        &.collapse {
            width: 60px;
            transition: width 0.3s;
        }
    }
    .main {
        height: 100%;
        flex:1;
        width: 0;
        padding: 10px 10px 0 10px;
        // transition: left 0.3s;
        font-family: Roboto;
        // z-index: -1;
        &.collapses {
            // left: 70px;
            // transition: left 0.3s;
        }
        .el-scrollbar__view{
            height: 100%;
        }
    }
}
</style>
