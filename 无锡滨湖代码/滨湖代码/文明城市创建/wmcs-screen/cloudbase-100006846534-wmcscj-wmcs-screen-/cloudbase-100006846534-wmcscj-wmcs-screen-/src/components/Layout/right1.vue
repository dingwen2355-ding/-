<script setup>
import { ref, onMounted } from "vue"
import panelSm from "@/components/Layout/panel/panel-sm.vue"
import panelMm from "@/components/Layout/panel/panel-mm.vue"
import panelMs from "@/components/Layout/panel/panel-ms.vue"
import panelL from "@/components/Layout/panel/panel-l.vue"
import Wenmingchengshizhishu from "./right1/wenmingchengshizhishu.vue";
import Wenmingchengshizhihuzhangdie from "./right1/wenmingchengshizhihuzhangdie.vue";
import Wenmingchengshichuangyoupaiming from "./right1/wenmingchengshichuangyoupaiming.vue";
import Zhishuqushifenxi from "./right1/zhishuqushifenxi.vue";
import Quyuzhenggaifenxi from "./right1/quyuzhenggaifenxi.vue";
</script>
<template>
    <div class="left">
        <div class="toptitle">
            <div>指数分析</div>
        </div>
        <panelMs class="p1" title="区文明程度指数（月度自评）">
            <Wenmingchengshizhishu></Wenmingchengshizhishu>
        </panelMs>
        <panelMs class="p2" title="区文明程度指数涨跌">
            <Wenmingchengshizhihuzhangdie></Wenmingchengshizhihuzhangdie>
        </panelMs>
        <panelMm class="p3" title="指数趋势分析">
            <Zhishuqushifenxi></Zhishuqushifenxi>
        </panelMm>
        <panelL class="p4" title="镇（开发区、街道）文明程度指数排名">
            <Wenmingchengshichuangyoupaiming></Wenmingchengshichuangyoupaiming>
        </panelL>
        <panelMm class="p5" title="责任单位整改分析">
            <Quyuzhenggaifenxi></Quyuzhenggaifenxi>
        </panelMm>
    </div>
</template>
<style scoped lang="scss">
.left {
    width: 1283px;
    height: 945px;
    position: absolute;
    top: 0;
    right: 0;
    background: url("@/assets/img/rightmask.png") no-repeat;
    background-size: cover;
}

.toptitle {
    width: 1264px;
    height: 48px;
    background: url("@/assets/img/toptitlebg.png") no-repeat;
    background-size: cover;
    position: absolute;
    top: 76px;
    right: 20px;
    text-align: center;
    color: #fff;
    line-height: 48px;
    font-size: 28px;
    font-family: YouSheBiaoTiHei;
    letter-spacing: 10px;

    div {
        margin-left: 401px;
        width: 462px;
        height: 48px;
        background: url("@/assets/img/toptitle.png") no-repeat;
        background-size: contain;
        background-position-y: 20px;
    }
}

.p1 {
    position: absolute;
    top: 138px;
    right: 665px;
    z-index: 3;
}

.p2 {
    position: absolute;
    top: 138px;
    right: 20px;
    z-index: 3;
}

.p3 {
    position: absolute;
    top: 404px;
    right: 20px;
    z-index: 3;
}

.p4 {
    position: absolute;
    top: 404px;
    right: 808px;
    z-index: 3;
}

.p5 {
    position: absolute;
    top: 670px;
    right: 20px;
    z-index: 3;
}
</style>