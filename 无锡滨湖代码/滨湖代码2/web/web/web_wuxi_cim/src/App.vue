
<template>
    <div id="app">
        <transition>
            <router-view />
        </transition>
    </div>
</template>

<script>
import { get } from "@/utils/fetch";
function destroyPlayer() {
    if (__p) __p.destroy();
}
function test_ac_destroy() {
    if (__g) __g.destroy();
    destroyPlayer();
}
export default {
    name: "app",
    data() {
        return {
            _beforeUnload_time: null,
            _gap_time: null,
            navsFlat: [],
        };
    },
    mounted() {
        window.addEventListener("beforeunload", (e) => this.beforeunloadHandler(e));
        window.addEventListener("unload", (e) => this.unloadHandler(e));
        this.getSysMappingData()
    },
    methods: {
        // 查询服务映射信息
        getSysMappingData() {
            if (Object.prototype.toString.call(this.$store.getters.serviceMapping.innerMapping) === "[object Boolean]") return;

            get(`${cim_main}serverMapping/list`).then((res) => {
                let data = res.message[0] || {};
                data.innerMapping = !isLocalDev && location.href.includes(data.systemInUrl);
                this.$store.dispatch("SET_MAPPING_ACTION", data);
            })
        },
        beforeunloadHandler() {
            this._beforeUnload_time = new Date().getTime();
        },
        unloadHandler(e) {
            this._gap_time = new Date().getTime() - this._beforeUnload_time;
            //判断是窗口关闭还是刷新
            if (this._gap_time <= 5) test_ac_destroy();
        },
    },
};
</script>

<style lang="scss">
@import "./assets/css/reset.styl.scss";
</style>
                