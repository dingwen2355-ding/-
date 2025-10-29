<template>
  <div style="height: 100vh">
    <div class="search">
      <uni-search-bar bgColor="#fff" @confirm="search" :focus="true" v-model="searchValue"></uni-search-bar>
    </div>
    <div id="map"></div>
    <div class="switch">
      只查看我的：
      <switch checked @change="switch1Change" />
    </div>
  </div>
</template>

<script>
import { initMap, zoomToRange } from "../../pages/olmap/init";
import uniSearchBar from '../../plugins/uni-search-bar/uni-search-bar.vue';
export default {
  components: { uniSearchBar },
  props: [],
  data() {
    return {
      searchValue: "",
      map: "",
      pointLayer: {},
      source: {},
      pointLayer1: {},
      source1: {},
    };
  },
  onLoad() {
  },
  mounted() {
    this.map = initMap();
    this.createLayer();
    this.createLayer1();
    this.addFeatrue([
      {
        ZuoBiao: "120.27944231531366, 31.529188353814106"
      },
      {
        ZuoBiao: "120.2806246768887, 31.529278559276378"
      },
    ])

  },
  methods: {
    search() {
      this.source1.clear()
      this.$api.checkPoints({ pageNum: 1, pageSize: 9999, name: this.searchValue.value }).then(res => {
        let row = res.data.records;
        let data = [];
        row.forEach(r => {
          if (r.longitude) {
            data.push({ ZuoBiao: r.longitude + "," + r.latitude })
          }
        })
        this.addFeatrue1(data)
      })
    },
    switch1Change(e) {
      if (e.detail.value) {
        this.source1.clear()
      } else {
        this.$api.checkPoints({ pageNum: 1, pageSize: 9999, name: this.searchValue.value }).then(res => {
          let row = res.data.records;
          let data = [];
          row.forEach(r => {
            if (r.longitude) {
              data.push({ ZuoBiao: r.longitude + "," + r.latitude })
            }
          })
          this.addFeatrue1(data)
        })
        // this.addFeatrue1([
        //   {
        //     ZuoBiao: "120.27958821281572, 31.526828359566338"
        //   },
        //   {
        //     ZuoBiao: "120.27803603506983, 31.530418500960536"
        //   },
        //   {
        //     ZuoBiao: "120.28169989640448, 31.53206704942268"
        //   },
        //   {
        //     ZuoBiao: "120.28221188760655, 31.525965781665647"
        //   },
        // ])
      }
    },
    createLayer() {
      this.source = new ol.source.Vector();
      this.pointLayer = new ol.layer.Vector({
        zIndex: "11",
        source: this.source,
        style: new ol.style.Style({
          image: new ol.style.Icon({
            crossOrigin: "anonymous",
            src: require("static/icons/men_a.png"),
            anchor: [0.5, 1],
            size: new ol.size.toSize([40, 40])
          })
        })
      });
      this.map.addLayer(this.pointLayer);
    },
    createLayer1() {
      this.source1 = new ol.source.Vector();
      this.pointLayer1 = new ol.layer.Vector({
        zIndex: "11",
        source: this.source1,
        style: new ol.style.Style({
          image: new ol.style.Icon({
            crossOrigin: "anonymous",
            src: require("static/icons/men.png"),
            anchor: [0.5, 1],
            size: new ol.size.toSize([40, 40])
          })
        })
      });
      this.map.addLayer(this.pointLayer1);
    },
    addFeatrue(data) {
      this.source.clear(true);
      let geojson = {
        type: "FeatureCollection",
        features: []
      };
      data.forEach(d => {
        geojson.features.push({
          type: "Feature",
          properties: d,
          geometry: {
            type: "Point",
            coordinates: ol.proj.transform(
              [
                Number(d.ZuoBiao.split(",")[0]),
                Number(d.ZuoBiao.split(",")[1])
              ],
              "EPSG:4326",
              "EPSG:3857"
            )
          }
        });
      });
      zoomToRange(geojson, this.map);
      let GeoJSONformat = new ol.format.GeoJSON();
      let features = GeoJSONformat.readFeatures(geojson);
      this.source.addFeatures(features);
    },
    addFeatrue1(data) {
      this.source1.clear(true);
      let geojson = {
        type: "FeatureCollection",
        features: []
      };
      data.forEach(d => {
        geojson.features.push({
          type: "Feature",
          properties: d,
          geometry: {
            type: "Point",
            coordinates: ol.proj.transform(
              [
                Number(d.ZuoBiao.split(",")[0]),
                Number(d.ZuoBiao.split(",")[1])
              ],
              "EPSG:4326",
              "EPSG:3857"
            )
          }
        });
      });
      zoomToRange(geojson, this.map);
      let GeoJSONformat = new ol.format.GeoJSON();
      let features = GeoJSONformat.readFeatures(geojson);
      this.source1.addFeatures(features);
    },
  },
  watch: {}
};
</script>

<style scoped lang="less">
#map {
  width: 100%;
  height: 100%;
}

.switch {
  position: absolute;
  top: 80upx;
  right: 10upx;
  color: #007aff;
}
</style>
