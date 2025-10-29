<script>
import axios from "axios"
export default {
    props: ["lng", "lat", "disabled"],
    data() {
        return {
            map: "",
            marker: ""
        }
    },
    methods: {
        // 处理json数据
        genJson(rows, xName, yName) {
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
    },
    mounted() {
        setTimeout(() => {


            if (process.env.NODE_ENV == "development") {
                minemap.domainUrl = '//minedata.com.cn';
                minemap.dataDomainUrl = '//minedata.com.cn';
                minemap.serverDomainUrl = '//minedata.com.cn';
                minemap.spriteUrl = '//minedata.com.cn/minemapapi/v2.1.0/sprite/sprite';
                minemap.serviceUrl = '//minedata.com.cn/service/';
                minemap.key = '16be596e00c44c86bb1569cb53424dc9';
                minemap.solution = 12877;
            } else {
                minemap.domainUrl = '//2.22.131.158:21009';
                minemap.dataDomainUrl = '//2.22.131.158:21009';
                minemap.serverDomainUrl = '//2.22.131.158:21009';
                minemap.spriteUrl = '//2.22.131.158:21009/minemapapi/v2.1.0/sprite/sprite';
                minemap.serviceUrl = '//2.22.131.158:21009/service/';
                minemap.key = 'cc6114fa3a7c48c3b97a3ac16741152d';
                minemap.solution = 12877;
            }

            this.map = new minemap.Map({
                container: 'map2',
                style: process.env.NODE_ENV == "development" ? '//minedata.com.cn/service/solu/style/id/12877' : '//2.22.131.158:21009/service/solu/style/id/12877',/* style的值为自定义配图后发布的地图样式URL，或者为自定义的JSON格式的样式对象 */
                center: [120.18487, 31.500612],
                zoom: 10,
                pitch: 0,
                maxZoom: 18,
                minZoom: 9,
                logoControl: false,
                projection: process.env.NODE_ENV == "development" ? 'MERCATOR' : 'LATLON',
            });
            var el = document.createElement('div');
            el.id = 'jqPoint';
            var content = document.createElement('div');
            content.classList.add("jqPop")
            el.appendChild(content)
            // 自定义DOM样式 或者通过css类设置
            if (this.lng && this.lat) {
                this.marker = new minemap.Marker(el, { offset: [-16, -32] }).setLngLat([Number(this.lng), Number(this.lat)]).addTo(this.map);
                this.map.setCenter([Number(this.lng), Number(this.lat)])
            }


            this.map.on('click', (e) => {
                if (!this.disabled) {
                    if (this.marker) {
                        this.marker.remove()
                    }
                    this.marker = new minemap.Marker(el, { offset: [-16, -32] }).setLngLat([e.lngLat.lng, e.lngLat.lat]).addTo(this.map);
                    this.$emit("update:lng", e.lngLat.lng)
                    this.$emit("update:lat", e.lngLat.lat)
                }
            })
        }, 1000)
    },
    watch: {
        lng() {
            if (this.marker) {
                this.marker.remove()
            }
            var el = document.createElement('div');
            el.id = 'jqPoint';
            var content = document.createElement('div');
            content.classList.add("jqPop")
            el.appendChild(content)
            this.marker = new minemap.Marker(el, { offset: [0, 0] }).setLngLat([Number(this.lng), Number(this.lat)]).addTo(this.map);
            this.map.setCenter([Number(this.lng), Number(this.lat)])
        },
        lat() {
            if (this.marker) {
                this.marker.remove()
            }
            var el = document.createElement('div');
            el.id = 'jqPoint';
            var content = document.createElement('div');
            content.classList.add("jqPop")
            el.appendChild(content)
            this.marker = new minemap.Marker(el, { offset: [0, 0] }).setLngLat([Number(this.lng), Number(this.lat)]).addTo(this.map);
            this.map.setCenter([Number(this.lng), Number(this.lat)])
        }
    }
}
</script>
<template>
    <div id="map2"></div>
</template>
<style lang="scss">
#map2 {
    width: 100%;
    height: 100%;
}

#jqPoint {
    width: 32px;
    height: 32px;
}

.jqPop {
    width: 32px;
    height: 32px;
    background: url("./../../assets/images/point.png");
    background-size: contain;
}
</style>