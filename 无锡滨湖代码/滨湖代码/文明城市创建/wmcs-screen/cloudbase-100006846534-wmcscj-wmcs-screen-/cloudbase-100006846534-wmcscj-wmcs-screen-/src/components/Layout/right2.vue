<script setup>
import { ref, onMounted } from "vue"
import PanelS from "@/components/Layout/panel/panel-s.vue"
import PanelLm from "@/components/Layout/panel/panel-lm.vue";
import wenmingjiating from  "./right2/wenmingjiating.vue";
import wenmingdanwei from "./right2/wenmingdanwei.vue";
import wenmingxiaoyuan from "./right2/wenmingxiaoyuan.vue";
import wenmingzhenjie from "./right2/wenmingzhenjie.vue";
import wenmingdanweileibie from "./right2/wenmingdanweileibie.vue";
import wenmingxiaoyuanleibie from "./right2/wenmingxiaoyuanleibie.vue";

</script>
<template>
    <div class="left">
        <div class="toptitle">
            <div>文明培育</div>
        </div>
        <PanelS class="p1" title="文明单位类别">
            <wenmingdanweileibie></wenmingdanweileibie>
        </PanelS>
        <PanelS class="p3" title="文明校园类别">
            <wenmingxiaoyuanleibie></wenmingxiaoyuanleibie>
        </PanelS>
        <PanelLm class="p6" title="文明单位">
            <wenmingdanwei></wenmingdanwei>
        </PanelLm>
        <PanelLm class="p2" title="文明校园">
            <wenmingxiaoyuan></wenmingxiaoyuan>
        </PanelLm>
        <PanelS class="p5" title="文明村镇">
            <wenmingzhenjie></wenmingzhenjie>
        </PanelS>
        <PanelLm class="p4" title="文明家庭">
            <wenmingjiating></wenmingjiating>
        </PanelLm>



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
        background-position-x: -5px;
    }
}

.p1 {
    position: absolute;
    top: 138px;
    right: 20px;
    z-index: 3;
}
.p2 {
    position: absolute;
    top: 404px;
    right: 20px;
    z-index: 3;
}

.p3 {
    position: absolute;
    top: 138px;
    right: 446px;
    z-index: 3;
}
.p4 {
    position: absolute;
    top: 404px;
    right: 446px;
    z-index: 3;
}

.p5 {
    position: absolute;
    top: 138px;
    right: 872px;
    z-index: 3;
}
.p6 {
    position: absolute;
    top: 404px;
    right: 872px;
    z-index: 3;
}

</style>