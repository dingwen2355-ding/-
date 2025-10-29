<script setup>
import { ref, onMounted, getCurrentInstance, watch, computed } from 'vue';
import axios from "axios"
import * as turf from "@turf/turf"
import "./minemap.scss"
import pinia from '@/store/store'
import { useStore } from "@/store/index"
const store = useStore(pinia)
const { $getResource } = getCurrentInstance().appContext.config.globalProperties
const emit = defineEmits(['mapComplate'])
let hls = ref("")
const video = ref(null);
// pop弹窗
let mousemoveMarker = ref(null);
// 视频监控状态
let spjk = ref(false)
// 区划面数据存储
let polygons = ref([])
let areaName = ref("滨湖区")
// 选择的区划
let selectArea = ref("")
let showArea = ref(false)
// 事件详情表单内容
let showAGJD = ref(false)
let aiguojidixiangqing = ref(null)
let showCancelSearch = ref(false)
// 督察点位
let dcdw = ref(false);

let duchadianweiForm = ref({})
var map = ""
onMounted(() => {
    
})

// 处理json数据
function genJson(rows, xName, yName) {
    let json = {
        "type": "FeatureCollection",
        "features": []
    }
    rows.forEach(p => {
        if (p[xName] && p[yName]) {
            json.features.push({
                "type": "Feature",
                "properties": p,
                "geometry": { "type": "Point", "coordinates": [Number(p[xName]), Number(p[yName])] }
            })
        }
    })
    return json
}
</script>
<template>
    <iframe id="map3" src="http://2.21.137.224:8080/cim/"></iframe>
</template>
<style scoped>
#map3 {
    width: 60%;
    height: 945px;
    position: absolute;
    top: 0;
    left: 20%;
    background: url("@/assets/img/mapbg.png") no-repeat;
    background-size: cover;
}

::v-deep(.el-input__wrapper) {
    background-color: #053475eb !important;
    height: 35px;
    font-size: 16px;
}

::v-deep(.el-input__inner) {
    color: #fff !important;
}

::v-deep(.el-descriptions) {
    color: #fff !important;
}

::v-deep(.el-descriptions__body) {
    background-color: #ffffff00 !important;
}

::v-deep(.el-descriptions__label) {
    color: #00F0FF !important;
    font-size: 20px;
}

::v-deep(.el-descriptions__content) {
    color: #fff !important;
    font-size: 20px;
}

::v-deep(.el-timeline-item__timestamp) {
    color: #00F0FF !important;
    font-size: 20px;
}

::v-deep(input:-moz-placeholder) {
    color: #fff;
}

::v-deep(input:-ms-input-placeholder) {
    color: #fff;
}

::v-deep(input::-webkit-input-placeholder) {
    color: #fff;
}

::v-deep(.el-pager li) {
    color: #fff;
    background-color: #ffffff00 !important;
}

::v-deep(.el-pagination button) {
    color: #fff;
    background-color: #ffffff00 !important;
}
</style>
<style lang="scss">
.selectArea {
    position: absolute;
    top: 130px;
    left: 1300px;
    z-index: 999;
    width: 150px;
}

.controls {
    width: 127px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    position: absolute;
    top: 850px;
    left: 1300px;
    z-index: 9;

    .item {
        width: 147px;
        height: 68px;
        background: url("@/assets/img/mapbtnbg.png") no-repeat;
        background-size: contain;
        margin: 0 10px;
        cursor: pointer;

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

    .item:nth-child(1) {
        background: url("@/assets/img/mapbtn-area.png") no-repeat;
    }

    font-size: 20px;
}

.controls-r {
    position: absolute;
    top: 710px;
    left: 2400px;

    .item:nth-child(1) {
        background: url("@/assets/img/mapbtn-sxt.png") no-repeat;
    }

    .item:nth-child(2) {
        background: url("@/assets/img/mapbtn-wrj.png") no-repeat;
    }

    .item:nth-child(3) {
        background: url("@/assets/img/mapbtn-dbsb.png") no-repeat;
    }
}


#videos {
    position: absolute;
    top: 200px;
    left: 50%;
    transform: translateX(-50%);
    width: 738px;
    height: 394px;
    background: url("@/assets/img/panelbg-sm.png") no-repeat;
    background-size: cover;
    z-index: 999;
    display: none;

    video {
        margin: 15px;
        width: 708px;
        height: 364px;
    }

    .closeVideo {
        font-size: 48px;
        color: #4BE9FF;
        position: absolute;
        top: 00px;
        right: 30px;
        cursor: pointer;
        z-index: 999;
    }

}
</style>