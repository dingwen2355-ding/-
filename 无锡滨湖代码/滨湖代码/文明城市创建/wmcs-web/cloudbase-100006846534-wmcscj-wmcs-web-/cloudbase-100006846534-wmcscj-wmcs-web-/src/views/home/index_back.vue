<template>
    <div class="body-in">
        <BiSideBar :isCollapse="isCollapse" class="subNav" :class="{'collapse': isCollapse}" />
        <div class="flex-1 w-0 h-100 show-flex-c">
            <BiHeader @collapse="collapse" />
            <div class="flex-1 h-0 w-100" style="padding: 10px;">
                <el-scrollbar ref="biMainScroll" style="height:100%; width:100%; background: #ffffff;" class="no-x-scoll">
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
export default {
    components: {
        BiHeader,
        BiSideBar
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
.body-in{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    .subNav {
        width: 230px;
        height: 100%;
        transition: width 0.3s;
        background-color: $thSideBarBack;
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
