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
    if (import.meta.env.MODE == "dev") {
        minemap.domainUrl = '//minedata.com.cn';
        minemap.dataDomainUrl = '//minedata.com.cn';
        minemap.serverDomainUrl = '//minedata.com.cn';
        minemap.spriteUrl = '//minedata.com.cn/minemapapi/v2.1.0/sprite/sprite';
        minemap.serviceUrl = '//minedata.com.cn/service/';
        minemap.key = '16be596e00c44c86bb1569cb53424dc9';
        minemap.solution = 12878;
    } else {
        minemap.domainUrl = '//2.22.131.158:21009';
        minemap.dataDomainUrl = '//2.22.131.158:21009';
        minemap.serverDomainUrl = '//2.22.131.158:21009';
        minemap.spriteUrl = '//2.22.131.158:21009/minemapapi/v2.1.0/sprite/sprite';
        minemap.serviceUrl = '//2.22.131.158:21009/service/';
        minemap.key = 'cc6114fa3a7c48c3b97a3ac16741152d';
        minemap.solution = 222530;
    }

    map = new minemap.Map({
        container: 'map2',
        style: import.meta.env.MODE == "dev" ? '//minedata.com.cn/service/solu/style/id/12878' : '//2.22.131.158:21009/service/solu/style/id/222530',/* style的值为自定义配图后发布的地图样式URL，或者为自定义的JSON格式的样式对象 */
        center: [120.18487, 31.500612],
        zoom: 11,
        pitch: 0,
        maxZoom: 18,
        minZoom: 11,
        logoControl: false,
        projection: import.meta.env.MODE == "dev" ? 'MERCATOR' : 'LATLON',
    });
    map.on('load', function () {
        // 增加自定义数据源、自定义图层
        createAreaLayer();
    });
    function createAreaLayer() {
        axios.get("./json/polygon.json").then(res => {
            let rows = res.data.RECORDS;
            polygons.value = res.data.RECORDS;
            var jsonData = {
                "type": "FeatureCollection",
                "features": []
            };
            rows.forEach((element, index) => {
                jsonData.features.push({
                    "type": "Feature",
                    "geometry": element.geometry
                })
                // // 自定义点标记的内部DOM元素
                var el = document.createElement('div');
                el.id = 'areaPoint' + index;
                var content = `<div class="mapPop">
                        <div class="text1">
                            ${element.name}
                        </div>
                        </div>`
                el.appendChild($.parseHTML(content)[0])
                // 自定义DOM样式 或者通过css类设置
                var _marker = new minemap.Marker(el, { offset: [-55, -20] }).setLngLat([Number(element.x), Number(element.y)]).addTo(map);
            });
            map.addSource("binhuSource", {
                "type": "geojson",
                "data": jsonData
            });

            map.addLayer({
                "id": "binhufillLayer",
                "type": "fill",
                "source": "binhuSource",
                "layout": {
                    "visibility": "visible",
                },
                "paint": {
                    "fill-color": "#0075FF",
                    "fill-opacity": 0.1,
                },
            });
            map.addLayer({
                "id": "binhulineLayer",
                "type": "line",
                "source": "binhuSource",
                "layout": {
                    "visibility": "visible",
                },
                "paint": {
                    "line-color": {
                        stops: [
                            [5, "#fff"],
                            [8, "#fff"],
                            [20, "#0075FF"],
                        ],
                        base: 1,
                    },
                    "line-width": {
                        stops: [
                            [5, 0.7],
                            [6, 0.8],
                            [7, 1.6],
                            [9, 1.6],
                            [20, 8],
                        ],
                        base: 1,
                    },
                },
            });
        })
    }
    let images = [{ name: "aiguo", url: "./image/aiguo.png" },
    ];
    images.forEach(img => {
        map.loadImage(img.url, function (error, image) {
            if (error) throw error;
            map.addImage(img.name, image);
        })
    })
    setTimeout(() => {
        loadAGJD()
    }, 3000)
    var el = document.createElement('div');
    el.id = 'mousemove-marker';
    var content = `<div class="text"></div>`
    el.appendChild($.parseHTML(content)[0])
    let v = window.innerWidth / 3840;
    // 自定义DOM样式 或者通过css类设置
    mousemoveMarker.value = new minemap.Marker(el, { offset: [24 * v, 0] }).setLngLat([0, 0]).addTo(map);
    map.on("mousemove", (e) => {
        let layers = ["aiguojidi-layer"];
        var features = map.queryRenderedFeatures(e.point, { layers: layers });
        var feature = features.length > 0 ? features[0] : null
        if (feature) {
            if (feature.layer.id == "aiguojidi-layer") {
                mousemoveMarker.value.setLngLat(e.lngLat);
                $("#mousemove-marker .text").text(feature.properties.name)
            }
        } else {
            $("#mousemove-marker .text").text("")
            mousemoveMarker.value.setLngLat([0, 0]);
        }

    })
    map.on('click', function (e) {
        var features = map.queryRenderedFeatures(e.point);
        if (features.length > 0) {
            features.forEach(feature => {
                if (feature.layer.id == "aiguojidi-layer") {
                    aiguojidixiangqing.value = feature.properties
                    showAGJD.value = true;
                    aiguojidixiangqing.value.files = []
                    let files = aiguojidixiangqing.value.pics.split(",");
                    files.map(re => {
                        aiguojidixiangqing.value.files.push($getResource(re))
                    })
                }
            })
        }
    })
})
function areaChange(val) {
    if (selectArea.value == val.id) {
        selectArea.value = undefined
        areaName.value = "滨湖区"
        showArea.value = false
        store.$patch({
            areaName: undefined
        })
    } else {
        selectArea.value = val.id
        areaName.value = val.name
        store.$patch({
            areaName: areaName.value
        })
    }

    if (selectArea.value) {
        if (map.getLayer("qulineLayer")) {
            map.removeLayer("qulineLayer");
        }
        if (map.getSource("quSource")) {
            map.removeSource("quSource");
        }
        if (map.getLayer("spya-layer")) {
            map.removeLayer("spya-layer");
        }
        if (map.getSource("spya-source")) {
            map.removeSource("spya-source");
        }
        let row = polygons.value.filter(e => e.id == selectArea.value)[0];
        map.setCenter([Number(row.x), Number(row.y)]); //设置地图层级
        map.setZoom(12)
        var jsonData = {
            "type": "FeatureCollection",
            "features": []
        };
        jsonData.features.push({
            "type": "Feature",
            "geometry": row.geometry
        })
        map.addSource("quSource", {
            "type": "geojson",
            "data": jsonData
        });

        map.addLayer({
            "id": "qulineLayer",
            "type": "line",
            "source": "quSource",
            "layout": {
                "visibility": "visible",
            },
            "paint": {
                "line-color": {
                    stops: [
                        [5, "#00F0FF"],
                        [8, "#00F0FF"],
                        [20, "#00F0FF"],
                    ],
                    base: 1,
                },
                "line-width": {
                    stops: [
                        [5, 0.7],
                        [6, 0.8],
                        [7, 1.6],
                        [9, 1.6],
                        [20, 8],
                    ],
                    base: 1,
                },
            },
        });


    } else {
        map.setCenter([120.18487, 31.500612]); //设置地图层级
        map.setZoom(11)
        if (map.getLayer("qulineLayer")) {
            map.removeLayer("qulineLayer");
        }
        if (map.getSource("quSource")) {
            map.removeSource("quSource");
        }
    }

}

// 加载爱国基地数据
function loadAGJD() {
    axios.get("./json/aiguojidi.json").then(res => {
        let rows = res.data;
        let geojson = genJson(rows, "longitude", "latitude");
        map.addSource("aiguojidi-source", {
            type: "geojson",
            data: geojson
        });
        map.addLayer({
            id: "aiguojidi-layer",
            type: "symbol",
            source: "aiguojidi-source",
            layout: {
                "icon-image": "aiguo",
                "icon-size": 0.5,
                "icon-anchor": "bottom"
            }
        });
    })
}

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
    <div id="map2"></div>
    <div id="videos">
        <div class="closeVideo">×</div>
        <video id="video" controls muted autoplay></video>
    </div>

    <div class="controls">
        <div class="item" @click="showArea = !showArea">
            <div class="text">{{ areaName }}</div>
            <div class="ul" v-if="showArea">
                <div class="li" v-for="item, index in polygons" :key="item.id" @click.stop="areaChange(item)"
                    :style="{ color: item.id == selectArea ? '#00F0FF' : '#fff' }">{{
                        item.name }}
                </div>
            </div>
        </div>
        <!-- <div class="item" @click="dcdwchange()">
            <div class="text" :style="{ color: dcdw ? '#00F0FF' : '#fff' }">督察点位</div>
        </div> -->
    </div>
    <div class="aiguojidixiangqing" v-if="showAGJD">
        <div class="close" @click="showAGJD = false">×</div>
        <div class="title">
            <div class="titlebg">{{ aiguojidixiangqing.name }}</div>
        </div>
        <div class="content">
            <div class="logo">
                <img :key="index" :src="$getResource(aiguojidixiangqing.logo)" />
            </div>
            <el-descriptions>
                <el-descriptions-item label="名称：" :span="3">{{ aiguojidixiangqing.name
                }}</el-descriptions-item>
                <el-descriptions-item label="地址：" :span="3">{{ aiguojidixiangqing.address
                }}</el-descriptions-item>
                <el-descriptions-item label="成立日期：" :span="3">{{ aiguojidixiangqing.time
                }}</el-descriptions-item>
                <el-descriptions-item label="简介：" :span="3">{{ aiguojidixiangqing.description
                }}</el-descriptions-item>
                <el-descriptions-item label="照片：" :span="3">
                    <div class="file">
                        <img v-for="i, index in aiguojidixiangqing.files" :key="index" :src="i" />
                    </div>
                </el-descriptions-item>
            </el-descriptions>
        </div>
    </div>
</template>
<style scoped>
#map2 {
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
<style scoped lang="scss">
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
        background-size: contain;
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