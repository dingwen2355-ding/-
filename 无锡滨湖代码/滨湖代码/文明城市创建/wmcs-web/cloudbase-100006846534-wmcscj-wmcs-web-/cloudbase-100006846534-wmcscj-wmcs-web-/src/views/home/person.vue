<template>
    <!-- <div class="body-in">
        <div class="w-100" style="border-bottom: 1px solid #ececec;">
            <zrHeader @collapse="collapse" />
        </div>
        <div class="flex-1 h-0 show-flex-r">
            <div class="main">
                <div class="flex-1 h-0" style="background: #ffffff;">
                    <router-view />
                </div>
            </div>
        </div>
    </div> -->
    <div class="home-body-pa">
        <div class="w-100" style="border-bottom: 1px solid #ececec;">
            <zrHeader @collapse="collapse" />
        </div>
        <div class="flex-1 h-0 show-flex-r">
            <div class="main">
                <div class="flex-1 h-0" style="background: #ffffff;">
                    <router-view />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import zrHeader from './header'
export default {
    components: {
        zrHeader
    },
    data() {
        return {
            sTop: 0
        }
    },
    watch: {
        $route(to, from) {
            // 重置复位页面的滚动条
            // this.$refs.biMainScroll.wrap.scrollTop = 0
        }
    },
    methods: {
        collapse(i) {
            // 菜单横向收起事件
            this.isCollapse = !i
        }
    }
}
</script>

<style lang='scss'>
.home-body-pa{
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    overflow: hidden;
    .main {
        background: #eceef5;
        height: 100%;
        flex:1;
        width: 0;
        padding: 10px 10px 0px 10px;
        transition: left 0.3s;
        display: flex;
        flex-direction: column;
        .copy-right{
            height: 25px;
            line-height: 25px;
            font-size: 12px;
            width: 100%;
            text-align: center;
            color: #C0C4CC;
        }
    }
}
</style>
