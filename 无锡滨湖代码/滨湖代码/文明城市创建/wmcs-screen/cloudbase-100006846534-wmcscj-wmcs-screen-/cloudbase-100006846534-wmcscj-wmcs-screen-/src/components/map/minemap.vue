<script setup>
import { ref, onMounted, getCurrentInstance, watch, computed } from 'vue';
import axios from "axios"
import * as turf from "@turf/turf"
import "./minemap.scss"
import pinia from '@/store/store'
import { useStore } from "@/store/index"
import { ElMessage, ElNotification } from 'element-plus'
const store = useStore(pinia)
watch(() => store.heatmap, (newVal, oldVal) => {
    gdHeatmap()
})
watch(() => store.jrdb, (newVal, oldVal) => {
    jrdbchange()
})
watch(() => store.jrbj, (newVal, oldVal) => {
    jrbjchange()
})
watch(() => store.activeMenu, (newVal, oldVal) => {
    // 
})
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
let showSJXQ = ref(false)
let shijianxiangqingForm = ref(null)
// 周边监控半径
let jiankongRadius = ref(0.3)
let showCancelSearch = ref(false)
// 督察点位
let dcdw = ref(false);
let dcdwTypes = ref([]);
let dcdwList = ref([]);
let duchadianweiSearch = ref({
    pageNum: 1,
    pageSize: 10,
    typeId: "",
    name: ""
});
let dcdwRow = ref({})
let dcdwListTotal = ref(0)
let dcdwTab = ref("工单")

// 无人机
let wrj = ref(false);
// 单兵设备
let dbsb = ref(false);

let duchadianweiForm = ref({})
let showDCDW = ref(false)
let showDCDWList = ref(false)
let gongdanlist = ref([])
let xuncharenwulist = ref([])
let xunchayuanlist = ref([])
var map = ""


let showJQ = ref(false)
let jqForm = ref({})
let jqMarkers = ref([])

onMounted(() => {
    if (store.loadmap) {
        loadMap()
    }
    window.onload = () => {
        store.$patch({
            loadmap: true
        })
        loadMap()
        setTimeout(() => {
            loadJQ()
        }, 2000)
        setInterval(() => {
            loadJQ()
        }, 30000)

    }
})
function loadJQ() {
    // $axios_get(IP + "/screen/api/index/aiEventList").then(res => {
    $axios_get(IP + "/screen/api/index/aiEventList", { params: { status: 20 } }).then(res => {
        // res.data.data.forEach(r => {
        //     ElNotification({
        //         title: r.eventTypeName,
        //         dangerouslyUseHTMLString: true,
        //         type: 'error',
        //         message: `<p>点位：${r.pointName}</p>
        //     <p>责任单位：${r.deptNames}</p>
        //     <p>坐标：${r.longitude},${r.latitude}</p>
        //     <p>周期：${r.cycle}</p>
        //     `,
        //     })
        // })
        if (jqMarkers.value.length > 0) {
            jqMarkers.value.forEach(m => {
                m.remove();
            })
            jqMarkers.value = []
        }
        let rows = res.data.data
        rows.forEach((element, index) => {
            // // 自定义点标记的内部DOM元素
            var el = document.createElement('div');
            el.id = 'jqPoint' + index;
            var content = `<div class="jqPop"></div>`
            el.appendChild($.parseHTML(content)[0])
            // 自定义DOM样式 或者通过css类设置
            var _marker = new minemap.Marker(el, { offset: [-40, -40] }).setLngLat([Number(element.longitude), Number(element.latitude)]).addTo(map);
            jqMarkers.value.push(_marker)
            el.addEventListener("click", () => {
                jqForm.value = element
                jqForm.value.nodes = []
                showJQ.value = true;
                console.log(element);
                element.properties = { channelId: element.channelId }
                setTimeout(() => {
                    let video = document.getElementById('video2');
                    $(".closeVideo").click(function () {
                        video.pause()
                        hls.destroy();
                        hls = null;
                    })
                    $.ajax({
                        url: IP + "/civilization/video/hls",
                        type: "POST",
                        data: {
                            channelId: element.properties.channelId
                        },
                        success: function (res1) {
                            if (Hls.isSupported()) {
                                hls = new Hls();
                                hls.loadSource(res1.data.url);
                                hls.attachMedia(video);
                                hls.on(Hls.Events.MANIFEST_PARSED, () => {
                                    console.log(video);
                                    console.log('加载成功');
                                    video.play();
                                });
                                hls.on(Hls.Events.ERROR, (event, data) => {
                                    console.log(event, data);
                                    // 监听出错事件
                                    console.log('加载失败');
                                });
                            }
                        }
                    })
                }, 500)

            })
        });
    })
}
function loadMap() {
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
        container: 'map',
        // style: '//2.22.131.158:21009/service/solu/style/id/222530',/* style的值为自定义配图后发布的地图样式URL，或者为自定义的JSON格式的样式对象 */
        style: import.meta.env.MODE == "dev" ? '//minedata.com.cn/service/solu/style/id/12878' : '//2.22.131.158:21009/service/solu/style/id/222530',/* style的值为自定义配图后发布的地图样式URL，或者为自定义的JSON格式的样式对象 */
        center: [120.18487, 31.500612],
        zoom: 11,
        pitch: 0,
        maxZoom: 18,
        minZoom: 11,
        logoControl: false,
        // projection: 'LATLON',
        projection: import.meta.env.MODE == "dev" ? 'MERCATOR' : 'LATLON',
    });
    map.on('load', function () {
        // 增加自定义数据源、自定义图层
        createAreaLayer();
    });
    loadDCDWType()
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
    let images = [{ name: "1_jk", url: "./image/1_jk.png" },
    { name: "shexiangtou", url: "./image/shexiangtou.png" },
    { name: "1PNG", url: "./image/1.png" },
    { name: "banjie", url: "./image/banjie.png" },
    { name: "daiban", url: "./image/daiban.png" },
    { name: "ducha", url: "./image/ducha.png" },
    { name: "duchaa", url: "./image/duchaa.png" },
    { name: "jq", url: "./image/jq.png" },
    ];
    images.forEach(img => {
        map.loadImage(img.url, function (error, image) {
            if (error) throw error;
            map.addImage(img.name, image);
        })
    })
    var el = document.createElement('div');
    el.id = 'mousemove-marker';
    var content = `<div class="text"></div>`
    el.appendChild($.parseHTML(content)[0])
    let v = window.innerWidth / 3840;
    // 自定义DOM样式 或者通过css类设置
    mousemoveMarker.value = new minemap.Marker(el, { offset: [24 * v, 0] }).setLngLat([0, 0]).addTo(map);
    map.on("mousemove", (e) => {
        let layers = [];
        if (spjk.value) {
            layers.push("unclustered-points")
        }
        if (showCancelSearch.value) {
            layers.push("searchVideo-layer")
        }
        if (areaName.value != "滨湖区") {
            layers.push("spya-layer")
        }
        if (dcdw.value) {
            layers.push("duchadianwei-layer")
        }
        if (store.jrdb) {
            layers.push("jinridaiban-layer")
        }
        if (store.jrbj) {
            layers.push("jinribanjie-layer")
        }
        var features = map.queryRenderedFeatures(e.point, { layers: layers });
        var feature = features.length > 0 ? features[0] : null
        if (feature) {
            if (feature.layer.id == "unclustered-points" || feature.layer.id == "searchVideo-layer" || feature.layer.id == "spya-layer") {
                mousemoveMarker.value.setLngLat(e.lngLat);
                $("#mousemove-marker .text").text(feature.properties.name)
            } else if (feature.layer.id == "duchadianwei-layer") {
                mousemoveMarker.value.setLngLat(e.lngLat);
                $("#mousemove-marker .text").text(feature.properties.name)
            } else if (feature.layer.id == "jinridaiban-layer" || feature.layer.id == "jinribanjie-layer") {
                mousemoveMarker.value.setLngLat(e.lngLat);
                $("#mousemove-marker .text").text(feature.properties.pointName)
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
                if (feature.layer.id == "unclustered-points") {
                    openVideo(feature)
                    console.log(feature.properties);
                } else if (feature.layer.id == "searchVideo-layer") {
                    openVideo(feature)
                    console.log(feature.properties);
                } else if (feature.layer.id == "spya-layer") {
                    openVideo(feature)
                    console.log(feature.properties);
                } else if (feature.layer.id == "duchadianwei-layer") {
                    duchadianweiForm.value = feature.properties;
                    getGDByName()
                    console.log(feature.properties);
                } else if (feature.layer.id == "jinridaiban-layer") {
                    $axios_get(IP + "/screen/api/index/eventInfo?eventId=" + feature.properties.id + "&eventSource=" + feature.properties.eventSource).then(res => {
                        shijianxiangqingForm.value = res.data.data
                        showSJXQ.value = true;
                        // shijianxiangqingForm.value.files = []
                        // let files = shijianxiangqingForm.value.attchpath.split(",");
                        // files.map(re => {
                        //     shijianxiangqingForm.value.files.push($getResource(re))
                        // })
                        // shijianxiangqingForm.nodeHistoryList = res.data.nodeHistoryList
                    })
                }
            })
        }
    })
}
// 获取督察点位类型
function loadDCDWType() {
    $axios_get(IP + "/screen/api/index/checkPointsType", { params: { pageSize: 999, pageNum: 1 } }).then(res => {
        dcdwTypes.value = res.data.data.records;
    })
}
function loadJK() {
    $axios_get(IP + "/screen/api/index/deviceChannel").then(res => {
        let rows = res.data.data;
        var jsonData = {
            "type": "FeatureCollection",
            "features": []
        };
        rows.forEach((element, index) => {
            jsonData.features.push({
                "type": "Feature",
                "properties": element,
                "geometry": { "type": "Point", "coordinates": [Number(element.gpsX), Number(element.gpsY)] }
            })
        })
        map.addSource("data-point", {
            type: "geojson",
            data: jsonData,
            cluster: true,
            clusterMaxZoom: 15, /* 最大聚合层级 */
            clusterRadius: 50 /* 聚合半径 */
        });
        map.addLayer({
            "id": "unclustered-points",
            "type": "symbol",
            "source": "data-point",
            "filter": ["!has", "point_count"],
            "layout": {
                "icon-image": "1_jk"
            }
        });


        //添加聚合图层
        var outerColors = [[1000, 'rgba(253, 156, 115, 0.6)'], [100, 'rgba(241, 211, 87, 0.6)'], [0, 'rgba(181, 226, 140, 0.6)']];
        outerColors.forEach(function (color, i) {
            map.addLayer({
                "id": "point-outer-cluster-" + i,
                "type": "circle",
                "source": "data-point",
                "paint": {
                    "circle-color": color[1],
                    "circle-radius": 20
                },
                "filter": i === 0 ?
                    [">=", "point_count", color[0]] :
                    ["all", [">=", "point_count", color[0]], ["<", "point_count", outerColors[i - 1][0]]]
            });
        });
        var innerColors = [[1000, 'rgba(241, 128, 23, 0.6)'], [100, 'rgba(240, 194, 12, 0.6)'], [0, 'rgba(110, 204, 57, 0.6)']];

        innerColors.forEach(function (color, i) {
            map.addLayer({
                "id": "point-inner-cluster-" + i,
                "type": "circle",
                "source": "data-point",
                "paint": {
                    "circle-color": color[1],
                    "circle-radius": 15
                },
                "filter": i === 0 ?
                    [">=", "point_count", color[0]] :
                    ["all", [">=", "point_count", color[0]], ["<", "point_count", innerColors[i - 1][0]]]
            });
        });
        //添加数量图层
        map.addLayer({
            "id": "cluster-count",
            "type": "symbol",
            "source": "data-point",
            "layout": {
                "text-field": "{point_count}",
                "text-size": 10
            },
            "paint": {
                "text-color": "rgba(0,0,0,.75)"
            },
            "filter": ["has", "point_count"]
        });
    })
}
function spjkchange() {
    spjk.value = !spjk.value
    if (spjk.value) {
        loadJK()
    } else {
        let layers = [
            "unclustered-points", "point-inner-cluster-0", "point-inner-cluster-1", "point-inner-cluster-2", "cluster-count",
            "point-outer-cluster-0", "point-outer-cluster-1", "point-outer-cluster-2",
        ]
        let sources = [
            "data-point"
        ]
        layers.forEach(e => {
            if (map.getLayer(e)) {
                map.removeLayer(e);
            }
        })
        sources.forEach(e => {
            if (map.getSource(e)) {
                map.removeSource(e);
            }
        })

    }
}
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
        $axios_get(IP + "/screen/api/index/regionDevice?regionName=" + areaName.value).then(res => {
            let rows = res.data.data;
            let json = genJson(rows, "gpsX", "gpsY")
            map.addSource("spya-source", {
                type: "geojson",
                data: json
            });
            map.addLayer({
                "id": "spya-layer",
                "type": "symbol",
                "source": "spya-source",
                "layout": {
                    "icon-image": "1_jk"
                }
            });
        })


    } else {
        map.setCenter([120.18487, 31.500612]); //设置地图层级
        map.setZoom(11)
        if (map.getLayer("qulineLayer")) {
            map.removeLayer("qulineLayer");
        }
        if (map.getLayer("spya-layer")) {
            map.removeLayer("spya-layer");
        }
        if (map.getSource("quSource")) {
            map.removeSource("quSource");
        }
        if (map.getSource("spya-source")) {
            map.removeSource("spya-source");
        }
    }

}
// 今日待办状态切换
function jrdbchange() {
    if (store.jrdb) {
        loadJRDB()
    } else {
        if (map.getLayer("jinridaiban-layer")) {
            map.removeLayer("jinridaiban-layer");
        }
        if (map.getSource("jinridaiban-source")) {
            map.removeSource("jinridaiban-source");
        }
    }
}
// 今日待办撒点
function loadJRDB() {
    $axios_get(IP + "/screen/api/index/eventByStatus", {
        params: { todayStatus: 1 }, headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(res => {
        let rows = res.data.data.records
        let geojson = genJson(rows, "longitude", "latitude");
        map.addSource("jinridaiban-source", {
            type: "geojson",
            data: geojson
        });
        map.addLayer({
            id: "jinridaiban-layer",
            type: "symbol",
            source: "jinridaiban-source",
            layout: {
                "icon-image": "daiban",
                "icon-size": 0.5,
                "icon-anchor": "bottom"
            }
        });
    })

}
// 今日办结状态切换
function jrbjchange() {
    if (store.jrbj) {
        loadJRBJ()
    } else {
        if (map.getLayer("jinribanjie-layer")) {
            map.removeLayer("jinribanjie-layer");
        }
        if (map.getSource("jinribanjie-source")) {
            map.removeSource("jinribanjie-source");
        }
    }
}
// 今日办结撒点
function loadJRBJ() {
    $axios_get(IP + "/screen/api/index/eventByStatus", {
        params: { todayStatus: 2 }, headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(res => {
        let rows = res.data.data.records
        let geojson = genJson(rows, "longitude", "latitude");
        map.addSource("jinribanjie-source", {
            type: "geojson",
            data: geojson
        });
        map.addLayer({
            id: "jinribanjie-layer",
            type: "symbol",
            source: "jinribanjie-source",
            layout: {
                "icon-image": "banjie",
                "icon-size": 0.5,
                "icon-anchor": "bottom"
            }
        });
    })
}
// 检索周边视频监控
function searchVideoByRadius(flag, x, y) {
    showCancelSearch.value = flag
    if (flag) {
        if (showDCDW.value) {
            showDCDW.value = false
        }
        if (showSJXQ.value) {
            showSJXQ.value = false
        }
        var center = [Number(x), Number(y)];
        var options = { steps: 64, units: 'kilometers' };
        var cirlceData = turf.circle(center, jiankongRadius.value, options);
        map.addSource("searchVideoSource", {
            "type": "geojson",
            "data": cirlceData
        });

        map.addLayer({
            "id": "searchVideoLayer",
            "type": "fill",
            "source": "searchVideoSource",
            "layout": {},
            "paint": {
                "fill-color": "#FFD700",
                "fill-opacity": 0.4
            }
        });
        map.setCenter(center)
        map.setZoom(16.5)
        let vidoejson = {
            "type": "FeatureCollection",
            "features": []
        }
        $axios_get(IP + "/screen/api/index/deviceChannel").then(res => {
            let rows = res.data.data;
            rows.forEach(p => {
                var pt = turf.point([Number(p.gpsX), Number(p.gpsY)]);
                let flag = turf.booleanPointInPolygon(pt, cirlceData);
                if (flag) {
                    vidoejson.features.push({
                        "type": "Feature",
                        "properties": p,
                        "geometry": { "type": "Point", "coordinates": [Number(p.gpsX), Number(p.gpsY)] }
                    })
                }
            })
            console.log(vidoejson);
            map.addSource("searchVideo-source", {
                type: "geojson",
                data: vidoejson
            });
            map.addLayer({
                "id": "searchVideo-layer",
                "type": "symbol",
                "source": "searchVideo-source",
                "layout": {
                    "icon-image": "1_jk"
                }
            });
        })

    } else {
        map.setCenter([120.18487, 31.500612]); //设置地图层级
        map.setZoom(11)
        if (map.getLayer("searchVideoLayer")) {
            map.removeLayer("searchVideoLayer");
        }
        if (map.getSource("searchVideoSource")) {
            map.removeSource("searchVideoSource");
        }
        if (map.getLayer("searchVideo-layer")) {
            map.removeLayer("searchVideo-layer");
        }
        if (map.getSource("searchVideo-source")) {
            map.removeSource("searchVideo-source");
        }
    }

}
// 工单heatmap
function gdHeatmap() {
    if (store.heatmap) {
        $axios_get(IP + "/screen/api/index/eventHeatMap").then(res => {
            let rows = res.data.data;
            var jsonData = {
                "type": "FeatureCollection",
                "features": []
            };
            rows.forEach((element, index) => {
                jsonData.features.push({
                    "type": "Feature",
                    "properties": element,
                    "geometry": { "type": "Point", "coordinates": [Number(element.longitude), Number(element.latitude)] }
                })
            })
            //添加数据源,该热力图效果，数据源类型支持geojson或vector两种形式
            map.addSource("heatmapSource", {
                type: "geojson",
                data: jsonData
            });
            map.addLayer({
                "id": "heatmapLayer",
                "type": "heatmap",
                "source": "heatmapSource",
                "layout": {
                    "visibility": "visible"
                },
                "paint": {
                    // 一个热力图数据点的模糊范围，单位是像素，默认值30；要求：值大于等于1，可根据zoom level进行插值设置
                    "heatmap-radius": 30,
                    //一个热力图单个数据点的热力程度，默认值为1；要求：值大于等于0，支持使用property中某个的热力值
                    "heatmap-weight": {
                        "property": "mag",
                        "stops": [[0, 0], [10, 1]]
                    },
                    // 用于统一控制热力值的强度，默认值1；要求：值大于等于0，可根据zoom level进行插值设置
                    "heatmap-intensity": 1,
                    // 表示热力图颜色阶梯，阶梯的值域范围为0-1，默认值为["interpolate",["linear"],["heatmap-density"],0,"rgba(0, 0, 255, 0)",0.1,"royalblue",0.3,"cyan",0.5,"lime",0.7,"yellow",1,"red"]
                    "heatmap-color": [
                        "interpolate",
                        ["linear"],
                        ["heatmap-density"],
                        0, "rgba(0, 0, 255, 0)", 0.1, "royalblue", 0.3, "cyan", 0.5, "lime", 0.7, "yellow", 1, "red"
                    ],
                    // 表示热力图的不透明度，默认值1；值域范围0-1，可根据zoom level进行插值设置
                    "heatmap-opacity": 1,
                }
            });
        })

    } else {
        if (map.getLayer("heatmapLayer")) {
            map.removeLayer("heatmapLayer");
        }
        if (map.getSource("heatmapSource")) {
            map.removeSource("heatmapSource");
        }
    }

}
// 督察点位状态切换
function dcdwchange() {
    dcdw.value = !dcdw.value
    if (dcdw.value) {
        loadDCDW()
    } else {
        if (map.getSource("duchadianwei-source")) {
            map.removeSource("duchadianwei-source");
        }
        if (map.getLayer("duchadianwei-layer")) {
            map.removeLayer("duchadianwei-layer");
        }
    }
}
// 督察点位上图
function dcdwdataChange() {
    duchadianweiSearch.value.pageNum = 1;
    loadDCDW()
}
function changeDCDWListPage() {
    loadDCDW()
}
function loadDCDW() {
    if (map.getSource("duchadianwei-source")) {
        map.removeSource("duchadianwei-source");
    }
    if (map.getLayer("duchadianwei-layer")) {
        map.removeLayer("duchadianwei-layer");
    }
    if (duchadianweiSearch.value.typeId || duchadianweiSearch.value.name) {
        dcdw.value = true
        $axios_get(IP + "/screen/api/index/checkPoints", {
            params: duchadianweiSearch.value
        }).then(res => {
            let rows = res.data.data.records;
            rows.forEach(r => {
                if (r.markCount == 0) {
                    r.iconname = "ducha"
                } else {
                    r.iconname = "duchaa"
                }
            })
            console.log(rows);
            dcdwList.value = res.data.data.records;
            dcdwListTotal.value = res.data.data.total;
            let geojson = genJson(rows, "longitude", "latitude");
            map.addSource("duchadianwei-source", {
                type: "geojson",
                data: geojson
            });
            map.addLayer({
                id: "duchadianwei-layer",
                type: "symbol",
                source: "duchadianwei-source",
                layout: {
                    "icon-image": ['get', 'iconname'],
                    "icon-size": 0.5,
                    "icon-anchor": "bottom"
                }
            });
        })
    } else {
        dcdw.value = false
        dcdwList.value = []
        map.setCenter([120.18487, 31.500612]); //设置地图层级
        map.setZoom(11)
    }

}
function toDCDW(row) {
    dcdwRow.value = row
    if (row.latitude) {
        map.setCenter([Number(row.longitude), Number(row.latitude)]); //设置地图层级
        map.setZoom(17)
    } else {
        duchadianweiForm.value = row;
        getGDByName()
        map.setCenter([120.18487, 31.500612]); //设置地图层级
        map.setZoom(11)
    }

}
function getGDByName() {
    $axios_get(IP + "/screen/api/index/eventByPoint", {
        params: { pointName: duchadianweiForm._value.name }, headers: {
            'Content-Type': 'multipart/form-data'
        }
    }).then(res => {
        gongdanlist.value = res.data.data.records
        showDCDWList.value = true
        if (gongdanlist.value.length > 0) {
            let element = gongdanlist.value[0]
            openSJXQ(element)
        }
    })
    $axios_get(IP + "/screen/api/index/checkTask", {
        params: { pointsTypeId: duchadianweiForm._value.typeId, pointsId: duchadianweiForm._value.id }
    }).then(res => {
        xuncharenwulist.value = res.data.data
    })
    $axios_get(IP + "/screen/api/index/checkUser", {
        params: { pointsId: duchadianweiForm._value.id }
    }).then(res => {
        xunchayuanlist.value = res.data.data
    })
}
function openSJXQ(element) {
    $axios_get(IP + "/screen/api/index/eventInfo?eventId=" + element.id + "&eventSource=" + element.eventSource).then(res => {
        shijianxiangqingForm.value = res.data.data
        showDCDW.value = true;
    })
}
// 打开视频监控
function openVideo(feature, flag) {
    $("#videos").css("display", "block")
    let video = document.getElementById('video');
    $(".closeVideo").click(function () {
        $("#videos").css("display", "none")
        video.pause()
        hls.destroy();
        hls = null;
    })
    $.ajax({
        url: IP + "/civilization/video/hls",
        type: "POST",
        data: {
            channelId: feature.properties.channelId
        },
        success: function (res1) {
            if (Hls.isSupported()) {
                hls = new Hls();
                hls.loadSource(res1.data.url);
                hls.attachMedia(video);
                hls.on(Hls.Events.MANIFEST_PARSED, () => {
                    console.log(video);
                    console.log('加载成功');
                    video.play();
                });
                hls.on(Hls.Events.ERROR, (event, data) => {
                    console.log(event, data);
                    // 监听出错事件
                    console.log('加载失败');
                });
            }
        }
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
function markEvent(i) {
    axios.post(IP + "/screen/api/index/mark", {
        params: { pointsId: duchadianweiForm._value.id, name: i.name, phone: i.phone }
    }).then(res => {
        ElMessage({
            message: '操作成功',
            type: 'success',
        })
        showDCDW.value = false;
        dcdwdataChange()
    })
}
</script>
<template>
    <div id="map"></div>
    <div id="videos">
        <div class="closeVideo">×</div>
        <video id="video" controls muted autoplay></video>
    </div>

    <div class="controls">
        <div class="item" @click="showArea = !showArea">
            <div class="text">{{ areaName }}</div>
            <div class="ul" v-if="showArea">
                <div class="li" v-for="item in polygons" :key="item.id" @click.stop="areaChange(item)"
                    :style="{ color: item.id == selectArea ? '#00F0FF' : '#fff' }">{{
                        item.name }}
                </div>
            </div>
        </div>
        <!-- <div class="item" @click="dcdwchange()">
            <div class="text" :style="{ color: dcdw ? '#00F0FF' : '#fff' }">督察点位</div>
        </div> -->
    </div>
    <div class="controls controls-r">
        <div class="item" @click="spjkchange()">
            <div class="text" :style="{ color: spjk ? '#00F0FF' : '#fff' }">视频监控</div>
        </div>
        <div class="item">
            <div class="text" :style="{ color: wrj ? '#00F0FF' : '#fff' }">无人机</div>
        </div>
        <div class="item">
            <div class="text" :style="{ color: dbsb ? '#00F0FF' : '#fff' }">单兵设备</div>
        </div>
    </div>
    <div class="shijianxiangqing" v-if="showSJXQ">
        <div class="close" @click="showSJXQ = false">×</div>
        <div class="title">
            <div class="titlebg">工单详情</div>
        </div>
        <div class="content">
            <el-row>
                <el-col :span="shijianxiangqingForm.nodes.length ? 16 : 24" style="border-right: 1px solid #ffffff50;">
                    <el-descriptions>
                        <el-descriptions-item label="考核周期：" :span="3">{{ shijianxiangqingForm.cycle
                        }}</el-descriptions-item>
                        <el-descriptions-item label="状态：" :span="3">{{ shijianxiangqingForm.status
                        }}</el-descriptions-item>
                        <el-descriptions-item label="点位类型：" :span="3">{{ shijianxiangqingForm.typeName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="事发点位：" :span="3">{{ shijianxiangqingForm.pointName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="检查项目：" :span="3">{{ shijianxiangqingForm.checkItemName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="责任单位：" :span="2">{{ shijianxiangqingForm.deptNames
                        }}</el-descriptions-item>
                        <el-descriptions-item label="扣分：">{{ shijianxiangqingForm.score }}</el-descriptions-item>
                        <el-descriptions-item label="事件类型：" :span="3">{{ shijianxiangqingForm.eventTypeName }}
                        </el-descriptions-item>
                        <el-descriptions-item label="所属区域：" :span="3">{{ shijianxiangqingForm.regionName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="问题描述：" :span="3">{{ shijianxiangqingForm.content
                        }}</el-descriptions-item>
                        <el-descriptions-item label="附件：" :span="3">
                            <div class="file" v-if="shijianxiangqingForm.files">
                                <el-image v-for="i, index in shijianxiangqingForm.files" :key="index"
                                    style="width: 100px; height: 100px" :preview-src-list="[$getResource(i)]" fit="fill"
                                    :src="$getResource(i)"></el-image>
                            </div>
                        </el-descriptions-item>
                    </el-descriptions>
                    <div class="bottoms">
                        <el-input-number v-model="jiankongRadius"></el-input-number>
                        <el-button
                            @click="searchVideoByRadius(true, shijianxiangqingForm.longitude, shijianxiangqingForm.latitude)"
                            type="success" style="margin-left: 2%;">检索周边视频监控（半径：单位km）</el-button>
                    </div>
                </el-col>
                <el-col :span="8" class="shijiantimeline" v-if="shijianxiangqingForm.nodes.length">
                    <el-timeline>
                        <el-timeline-item v-for="i, index in shijianxiangqingForm.nodes" :timestamp="i.handletime"
                            placement="top" :key="index">
                            <div class="tips">{{ i.nodename }}</div>
                            <div class="tips">{{ i.nickName }}-{{ i.handleopinion }}</div>
                        </el-timeline-item>
                    </el-timeline>
                </el-col>
            </el-row>
        </div>
    </div>
    <div class="jqForm" v-if="showJQ">
        <div class="close closeVideo" @click="showJQ = false">×</div>
        <div class="title">
            <div class="titlebg">告警详情</div>
        </div>
        <div class="content">
            <el-row>
                <el-col :span="24">
                    <el-descriptions>
                        <el-descriptions-item label="考核周期：" :span="3">{{ jqForm.cycle
                        }}</el-descriptions-item>
                        <el-descriptions-item label="点位类型：" :span="3">{{ jqForm.typeName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="事发点位：" :span="3">{{ jqForm.pointName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="责任单位：" :span="3">{{ jqForm.deptNames
                        }}</el-descriptions-item>
                        <el-descriptions-item label="事件类型：" :span="3">{{ jqForm.eventTypeName }}
                        </el-descriptions-item>
                        <el-descriptions-item label="所属区域：" :span="3">{{ jqForm.regionName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="告警设备名称：" :span="3">{{ jqForm.deviceName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="告警详情：" :span="3">{{ jqForm.content
                        }}</el-descriptions-item>
                        <el-descriptions-item label="附件：" :span="3">
                            <div class="file">
                                <el-image :preview-src-list="[$getResource(i)]" v-for="i, index in jqForm.files"
                                    :key="index" fit="contain" :src="$getResource(i)"></el-image>
                            </div>
                        </el-descriptions-item>
                    </el-descriptions>
                </el-col>
            </el-row>
        </div>
        <video class="video" id="video2" controls muted autoplay></video>
    </div>
    <div class="duchadianweixiangqing" v-if="showDCDW">
        <div class="close" @click="showDCDW = false">×</div>
        <div class="title">
            <div class="titlebg">督察点位详情</div>
        </div>
        <div class="content">
            <el-row :gutter="30">
                <el-col :span="8">
                    <el-descriptions>
                        <el-descriptions-item label="点位名称：" :span="3">{{ duchadianweiForm.name
                        }}</el-descriptions-item>
                        <el-descriptions-item label="点位类型：" :span="3">{{ duchadianweiForm.typeName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="所属街道：" :span="3">{{ duchadianweiForm.regionName
                        }}</el-descriptions-item>
                        <el-descriptions-item label="负责人：" :span="3"></el-descriptions-item>
                        <el-descriptions-item label="达标标准：" :span="3">无明显垃圾</el-descriptions-item>
                        <el-descriptions-item :span="3">
                            <el-tabs v-model="dcdwTab">
                                <el-tab-pane name="工单" label="工单"></el-tab-pane>
                                <el-tab-pane name="巡查员" label="巡查员"></el-tab-pane>
                                <el-tab-pane name="巡查任务" label="巡查任务"></el-tab-pane>
                            </el-tabs>
                        </el-descriptions-item>
                    </el-descriptions>
                    <div class="tbody" v-if="dcdwTab == '工单'">
                        <div :class="{ 'tr': true, 'active': shijianxiangqingForm.id == i.id }"
                            v-for="i, index in gongdanlist" :key="index" @click="openSJXQ(i)">
                            <div :style="{ color: i.status == '整改中' ? 'yellow' : i.status == '已整改' ? 'green' : 'red' }">{{
                                i.status }}</div>
                            <div> {{ i.content }}</div>
                        </div>
                    </div>
                    <div class="tbody" v-if="dcdwTab == '巡查任务'">
                        <div :class="{ 'tr': true }" v-for="i, index in xuncharenwulist" :key="index">
                            <div>{{ i.pointsTypeName }}</div>
                            <div> {{ i.pointsName }}</div>
                        </div>
                    </div>
                    <div class="tbody" v-if="dcdwTab == '巡查员'">
                        <div :class="{ 'tr': true }" v-for="i, index in xunchayuanlist" :key="index">
                            <div>{{ i.name }}</div>
                            <div> {{ i.phone }}</div>
                        </div>
                    </div>

                </el-col>
                <el-col :span="16" style="border-left: 3px solid #4BE9FF;">
                    <template v-if="dcdwTab == '工单'">
                        <el-row>
                            <el-col :span="shijianxiangqingForm.nodes.length > 0 ? 14 : 24">
                                <el-descriptions>
                                    <el-descriptions-item label="考核周期：" :span="3">{{ shijianxiangqingForm.cycle
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="状态：" :span="3">{{ shijianxiangqingForm.status
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="点位类型：" :span="3">{{ shijianxiangqingForm.typeName
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="事发点位：" :span="3">{{ shijianxiangqingForm.pointName
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="检查项目：" :span="3">{{ shijianxiangqingForm.checkItemName
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="责任单位：" :span="3">{{ shijianxiangqingForm.deptNames
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="扣分：">{{ shijianxiangqingForm.score
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="事件类型：" :span="3">{{ shijianxiangqingForm.eventTypeName }}
                                    </el-descriptions-item>
                                    <el-descriptions-item label="所属区域：" :span="3">{{ shijianxiangqingForm.regionName
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="问题描述：" :span="3">{{ shijianxiangqingForm.content
                                    }}</el-descriptions-item>
                                    <el-descriptions-item label="附件：" :span="3">
                                        <div class="file" v-if="shijianxiangqingForm.files">
                                            <el-image :preview-src-list="[$getResource(i)]"
                                                v-for="i, index in shijianxiangqingForm.files" :key="index" fit="contain"
                                                :src="$getResource(i)"></el-image>
                                        </div>
                                    </el-descriptions-item>
                                </el-descriptions>
                            </el-col>
                            <el-col :span="10" class="shijiantimeline" v-if="shijianxiangqingForm.nodes.length > 0">
                                <el-timeline>
                                    <el-timeline-item v-for="i, index in shijianxiangqingForm.nodes"
                                        :timestamp="i.handletime" placement="top" :key="index">
                                        <div class="tips">{{ i.nodename }}</div>
                                        <div class="tips">{{ i.nickName }}-{{ i.handleopinion }}</div>
                                    </el-timeline-item>
                                </el-timeline>
                            </el-col>
                        </el-row>
                        <div class="bottoms">
                            <el-input-number v-model="jiankongRadius"></el-input-number>
                            <el-button
                                @click="searchVideoByRadius(true, duchadianweiForm.longitude, duchadianweiForm.latitude)"
                                type="success" style="margin-left: 2%;">检索周边视频监控（半径：单位km）</el-button>
                        </div>
                    </template>
                    <template v-if="dcdwTab == '巡查任务'">
                        <el-row>
                            <el-col :span="24">
                                <el-descriptions v-for="i, index in xuncharenwulist" :key="index">
                                    <el-descriptions-item label="任务内容：" :span="3">{{ i.checkItem }}
                                    </el-descriptions-item>
                                </el-descriptions>
                            </el-col>
                        </el-row>
                    </template>
                    <template v-if="dcdwTab == '巡查员'">
                        <el-row>
                            <el-col :span="24">
                                <el-descriptions v-for="i, index in xunchayuanlist" :key="index">
                                    <el-descriptions-item label="巡查员姓名：" :span="2">{{ i.name }}
                                    </el-descriptions-item>
                                    <el-descriptions-item label="巡查员联系方式：" :span="1">{{ i.phone }}
                                    </el-descriptions-item>
                                    <el-descriptions-item :span="3"><el-button type="success"
                                            @click="markEvent(i)">标记信号</el-button>
                                    </el-descriptions-item>
                                </el-descriptions>
                            </el-col>
                        </el-row>
                    </template>
                </el-col>
            </el-row>
        </div>

    </div>


    <div class="gongdanliebiao">
        <div class="searchInput">
            <el-row :gutter="10">
                <el-col :span="8">
                    <el-select size="large" v-model="duchadianweiSearch.typeId" placeholder="全部" @change="dcdwdataChange()"
                        clearable>
                        <el-option v-for="i, index in dcdwTypes" :key="index" :label="i.name" :value="i.id"></el-option>
                    </el-select></el-col>
                <el-col :span="16">
                    <el-input size="large" clearable v-model="duchadianweiSearch.name"
                        @change="dcdwdataChange()"></el-input>
                </el-col>
            </el-row>
        </div>
        <div class="content" v-if="dcdwList.length > 0">
            <div class="tr">
                <!-- <div>序号</div> -->
                <div>名称</div>
                <div>状态</div>
                <div>定位</div>
            </div>
            <div :class="{ tr: true, active: dcdwRow.id == i.id }" v-for="i, index in dcdwList" :key="index"
                @click="toDCDW(i)">
                <!-- <div>{{ index + 1 }}</div> -->
                <div>{{ i.name }}</div>
                <div v-if="index == 3" style="color: yellow;">待巡查</div>
                <div v-else-if="index == 5" style="color: blue;">不合格待处理</div>
                <div v-else-if="index == 9" style="color: orange;">不合格处理中</div>
                <div v-else style="color: green;">合格</div>
                <div>
                    <el-icon v-if="i.latitude" color="red">
                        <LocationFilled />
                    </el-icon>
                    <el-icon v-else>
                        <LocationFilled />
                    </el-icon>
                </div>
            </div>
            <div class="pageclass">
                <el-pagination v-model:current-page="duchadianweiSearch.pageNum" layout="prev, pager, next"
                    :total="dcdwListTotal" @current-change="changeDCDWListPage()" />
            </div>
        </div>
    </div>
    <el-button class="cancelSearch" v-if="showCancelSearch" @click="searchVideoByRadius(false)"
        type="danger">关闭检索</el-button>
</template>
<style scoped>
#map {
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

::v-deep(.el-tabs__item) {
    color: #fff;
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
        background-image: url("@/assets/img/mapbtn-area.png");
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
        background-size: contain;
    }

    .item:nth-child(2) {
        background: url("@/assets/img/mapbtn-wrj.png") no-repeat;
        background-size: contain;
    }

    .item:nth-child(3) {
        background: url("@/assets/img/mapbtn-dbsb.png") no-repeat;
        background-size: contain;
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
    z-index: 99999;
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

.el-popper.is-light {
    background: #053475eb !important;
    border-color: #053475eb !important;
}

.el-popper.is-light .el-popper__arrow::before {
    background: #053475eb !important;
    border-color: #053475eb !important;
}

.el-select-dropdown__item.hover,
.el-select-dropdown__item:hover {
    background: #07469deb !important;
}

.el-select-dropdown__item {
    color: #fff !important;
}
</style>