<script setup>
import { ref, onMounted } from "vue"
import panelS from "@/components/Layout/panel/panel-s.vue"
import panelM from "@/components/Layout/panel/panel-m.vue"
import Gongdanzonglanfenxi from "./left1/gongdanzonglanfenxi.vue";
import Gongdanlaiyuanfenxi from "./left1/gongdanlaiyuanfenxi.vue";
import Gaofagongdanleixing from "./left1/gaofagongdanleixing.vue";
import Gongdanqushifenxi from "./left1/gongdanqushifenxi.vue";
import Gongdanbanjiefenxi from "./left1/gongdanbanjiefenxi.vue";
import Gaojinggaofadianwei from "./left1/gaojinggaofadianwei.vue";
import Gequyugongdanhuanbifenxi from "./left1/gequyugongdanhuanbifenxi.vue";
import Yuedugaofaquyu from "./left1/yuedugaofaquyu.vue";

</script>
<template>
    <div class="left">
        <div class="toptitle">
            <div>工单分析</div>
        </div>
        <panelS class="p1" title="工单总览分析">
            <Gongdanzonglanfenxi></Gongdanzonglanfenxi>
        </panelS>
        <panelS class="p2" title="工单来源分析">
            <Gongdanlaiyuanfenxi></Gongdanlaiyuanfenxi>
        </panelS>
        <panelS class="p3" title="高发工单类型">
            <Gaofagongdanleixing></Gaofagongdanleixing>
        </panelS>
        <panelS class="p4" title="近12期工单趋势分析">
            <Gongdanqushifenxi></Gongdanqushifenxi>
        </panelS>
        <panelS class="p5" title="工单办结分析">
            <Gongdanbanjiefenxi></Gongdanbanjiefenxi>
        </panelS>
        <panelS class="p6" title="高发点位分析">
            <Gaojinggaofadianwei></Gaojinggaofadianwei>
        </panelS>
        <panelM class="p7" title="责任单位工单同环比分析">
            <Gequyugongdanhuanbifenxi></Gequyugongdanhuanbifenxi>
        </panelM>
        <panelS class="p8" title="高发责任单位分析">
            <Yuedugaofaquyu></Yuedugaofaquyu>
        </panelS>
    </div>
</template>
<style scoped lang="scss">
.left {
    width: 1283px;
    height: 945px;
    position: absolute;
    z-index: 999;
    top: 0;
    left: 0;
    background: url("@/assets/img/leftmask.png") no-repeat;
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
    left: 20px;
    z-index: 3;
}

.p2 {
    position: absolute;
    top: 138px;
    left: 446px;
    z-index: 3;
}

.p3 {
    position: absolute;
    top: 138px;
    left: 872px;
    z-index: 3;
}

.p4 {
    position: absolute;
    top: 404px;
    left: 20px;
    z-index: 3;
}

.p5 {
    position: absolute;
    top: 404px;
    left: 446px;
    z-index: 3;
}

.p6 {
    position: absolute;
    top: 404px;
    left: 872px;
    z-index: 3;
}

.p7 {
    position: absolute;
    top: 670px;
    left: 20px;
    z-index: 3;
}

.p8 {
    position: absolute;
    top: 670px;
    left: 872px;
    z-index: 3;
}
</style>