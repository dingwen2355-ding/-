<script setup>
import { ref, onMounted } from "vue"
import { useStore } from "@/store/index"
import left1 from "@/components/Layout/left1.vue";
import left2 from "@/components/Layout/left2.vue";
import right1 from "@/components/Layout/right1.vue";
import minemap from "@/components/map/minemap.vue"
import Right2 from "@/components/Layout/right2.vue";
import topvue from "@/components/Layout/top.vue";
import Minemap_2 from "@/components/map/minemap_2.vue";
import minemap_cim from "@/components/map/minemap_cim.vue";
let store = useStore()
let showimg = ref(false)
let activeMenu = ref(1)
let map3Mode = ref(false)
// 监听store数据修改
let sub = store.$subscribe((params, state) => {
    // console.log("params", params);
    // console.log("state", state);
})
function close() {
    store.$patch({
        showPop: false
    })
}
function clickMenu(val) {
    activeMenu.value = val
    store.$patch({
        activeMenu: val
    })
}
function back() {
    window.location.href = "http://2.21.137.206:11510/presentation?id=44cd5979-c8cc-4745-81f9-7d766fb6cf95"
}
</script>
<template>
    <div class="bg"></div>
    <div class="theader">
        <span>滨湖区文明城市创建</span>
    </div>
    <!-- <div class="back" @click="back()">
        <div>返回</div>
    </div> -->
    <div class="leftBtn">
        <div class="item" @click="clickMenu(1)">
            <div>首页概览</div>
        </div>
    </div>
    <div class="rightBtn">
        <div class="item" @click="clickMenu(2)">
            <div>培育成效</div>
        </div>
    </div>
    <div class="mapmode">
        <div class="text" @click="map3Mode=!map3Mode" :style="{ color: map3Mode ? '#00F0FF' : '#fff' }">三维地图</div>
    </div>
    <minemap v-if="!map3Mode && activeMenu == 1"></minemap>
    <minemap_2 v-if="!map3Mode && activeMenu == 2"></Minemap_2>
    <minemap_cim v-if="map3Mode"></minemap_cim>
    <topvue v-if="activeMenu == 1"></topvue>
    <left1 v-if="activeMenu == 1"></left1>
    <left2 v-if="activeMenu == 2"></left2>
    <right1 v-if="activeMenu == 1"></right1>
    <Right2 v-if="activeMenu == 2"> </Right2>
</template>
<style scoped lang="scss">
.home {
    width: 3840px;
    height: 945px;
    overflow: hidden;
}

.mapmode {
    position: absolute;
    top: 780px;
    left: 1280px;
    width: 147px;
    height: 68px;
    background: url("@/assets/img/mapbtn-area.png") no-repeat;
    background-size: contain;
    margin: 0 10px;
    cursor: pointer;
    z-index: 9;

    .text {
        width: 70px;
        margin-left: 57px;
        height: 68px;
        line-height: 68px;
        font-size: 16px;
        color: #fff;
        text-align: center;
    }

    .ul {
        width: 800px;
        display: flex;
        flex-direction: row;
        position: relative;
        top: -46px;
        left: 140px;

        .li {
            width: 90px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            background: #000000c9;
            color: #fff;
            margin: 0 5px;
            font-size: 16px;
        }
    }

}

.bg {
    width: 100%;
    height: 95px;
    background-size: cover;
    position: absolute;
    top: 0;
    left: 0;
    pointer-events: none;
    z-index: 1;
    background: linear-gradient(#292929, #000, #00000000);
}

.theader {
    width: 3840px;
    height: 153px;
    position: absolute;
    left: 0px;
    top: 0px;
    z-index: 2;
    background: url("@/assets/img/headbg.png") no-repeat;
    background-size: 100%;
    line-height: 95px;
    font-size: 56px;
    font-family: YouSheBiaoTiHei;
    text-align: center;
    pointer-events: none;

    span {
        letter-spacing: 10px;
        color: #E9EEF2;
    }
}

.back {
    position: fixed;
    z-index: 99;
    top: 10px;
    right: 40px;
    width: 140px;
    height: 40px;
    line-height: 40px;
    background: linear-gradient(to Bottom, #FFB966, rgba(255, 229, 198, 0.5));
    cursor: pointer;
    text-align: center;
    font-size: 20px;
    color: #fff;
    transform: skewX(-25deg);
    border-radius: 8px;

    div {
        transform: skewX(25deg)
    }
}

.leftBtn {
    width: 500px;
    height: 40px;
    position: absolute;
    top: 60px;
    left: 25%;
    z-index: 99;
    display: flex;
    justify-content: flex-end;

    .item {
        width: 140px;
        height: 40px;
        line-height: 40px;
        background: linear-gradient(to Bottom, #FFB966, rgba(255, 229, 198, 0.5));
        cursor: pointer;
        text-align: center;
        font-size: 20px;
        color: #fff;
        transform: skewX(45deg);
        border-radius: 8px;

        div {
            transform: skewX(-45deg)
        }
    }
}

.rightBtn {
    width: 500px;
    height: 40px;
    position: absolute;
    top: 60px;
    right: 25%;
    z-index: 99;
    display: flex;
    justify-content: flex-start;

    .item {
        width: 140px;
        height: 40px;
        line-height: 40px;
        background: linear-gradient(to Bottom, #FFB966, rgba(255, 229, 198, 0.5));
        cursor: pointer;
        text-align: center;
        font-size: 20px;
        color: #fff;
        transform: skewX(-45deg);
        border-radius: 8px;

        div {
            transform: skewX(45deg)
        }
    }
}

.rightControl {
    height: 50px;
    width: 15px;
    line-height: 50px;
    background-color: #1f98ff90;
    color: #fff;
    position: absolute;
    right: 500px;
    top: 45%;
    cursor: pointer;
    text-align: center;
}

.rightnone {
    right: 0;
}

@keyframes moveL {
    0% {
        transform: translateX(-100%);
        scale: 0;
    }

    100% {
        transform: translateX(0%);
        scale: 1;
    }
}

@keyframes moveR {
    0% {
        transform: translateX(100%);
        scale: 0;
    }

    100% {
        transform: translateX(0%);
        scale: 1;
    }
}
</style>