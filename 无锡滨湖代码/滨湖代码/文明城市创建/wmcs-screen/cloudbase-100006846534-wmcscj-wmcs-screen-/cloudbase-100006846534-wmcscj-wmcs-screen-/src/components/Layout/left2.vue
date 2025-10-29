<script setup>
import { ref, onMounted } from "vue"
import panelS from "@/components/Layout/panel/panel-s.vue"
import panelM from "@/components/Layout/panel/panel-m.vue"
import PanelLM from "@/components/Layout/panel/panel-lm.vue";
import PanelMm from "./panel/panel-mm.vue";
import PanelM from "./panel/panel-m.vue";
import PanelSm from "./panel/panel-sm.vue";
import zuimeizaitipingxuan from "./left2/zuimeizaitipingxuan.vue";
import quyuzuimeizaitiduibi from "./left2/quyuzuimeizaitiduibi.vue";
import zuimeiguanjianci from "./left2/zuimeiguanjianci.vue";
import zuimeizaiti from "./left2/zuimeizaiti.vue";
import zhiyuanfuwuchangci from "./left2/zhiyuanfuwuchangci.vue";
import zhiyuanfuwuhuodong from "./left2/zhiyuanfuwuhuodong.vue";

</script>
<template>
    <div class="left">
        <div class="toptitles">
            <div class="toptitle1">
                <div>文明实践</div>
            </div>
            <div class="toptitle2">
                <div>文明培育</div>
            </div>
        </div>
        <PanelM class="p1" title="先进典型">
            <zuimeizaitipingxuan></zuimeizaitipingxuan>
        </PanelM>
        <panelS class="p2" title="志愿者服务场次">
            <zhiyuanfuwuchangci></zhiyuanfuwuchangci>
        </panelS>
        <panelM class="p3" title="板块先进典型对比">
            <quyuzuimeizaitiduibi></quyuzuimeizaitiduibi>
        </panelM>
        <PanelLM class="p4" title="志愿服务活动">
            <zhiyuanfuwuhuodong></zhiyuanfuwuhuodong>
        </PanelLM>
        <panelS class="p5" title="最美关键词">
            <zuimeiguanjianci></zuimeiguanjianci>
        </panelS>
        <panelS class="p6" title="先进典型人物">
            <zuimeizaiti></zuimeizaiti>
        </panelS>
    </div>
</template>
<style scoped lang="scss">
.left {
    width: 1283px;
    height: 945px;
    position: absolute;
    top: 0;
    left: 0;
    background: url("@/assets/img/leftmask.png") no-repeat;
    background-size: cover;
}

.toptitles {
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    .toptitle1 {
        width: 410px;
        height: 48px;
        background: linear-gradient(to Right, #FFB96600, #FFB96699, #FFB96600);
        position: absolute;
        top: 76px;
        left: 20px;
        text-align: center;
        color: #fff;
        line-height: 48px;
        font-size: 28px;
        font-family: YouSheBiaoTiHei;
        letter-spacing: 10px;

        div {
            // margin-left: 401px;
            width: 410px;
            height: 48px;
            background: url("@/assets/img/toptitle.png") no-repeat;
            background-size: contain;
            background-position-y: 20px;
            background-position-x: -5px;
        }
    }

    .toptitle2 {
        width: 836px;
        height: 48px;
        background: linear-gradient(to Right, #FFB96600, #FFB96699, #FFB96600);
        position: absolute;
        top: 76px;
        left: 446px;
        text-align: center;
        color: #fff;
        line-height: 48px;
        font-size: 28px;
        font-family: YouSheBiaoTiHei;
        letter-spacing: 10px;

        div {
            margin-left: 213px;
            width: 410px;
            height: 48px;
            background: url("@/assets/img/toptitle.png") no-repeat;
            background-size: contain;
            background-position-y: 20px;
            background-position-x: -5px;
        }
    }
}


.p1 {
    position: absolute;
    top: 138px;
    left: 446px;
    z-index: 3;
}

.p2 {
    position: absolute;
    top: 138px;
    left: 20px;
    z-index: 3;
}

.p3 {
    position: absolute;
    top: 404px;
    left: 446px;
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
    top: 670px;
    left: 446px;
    z-index: 3;
}

.p6 {
    position: absolute;
    top: 670px;
    left: 872px;
    z-index: 3;
}
</style>