<template>
  <div style="height: 100vh">
    <div id="map"></div>
  </div>
</template>

<script>
import { initMap, zoomToRange } from "../../pages/olmap/init";

export default {
  components: {},
  props: [],
  data() {
    return {
      map: "",
      pointLayer: {},
      source: {},
      travelData: null,
      chooseLayer: {}
    };
  },
  onLoad() {
  },
  mounted() {
    this.map = initMap();
    this.createLayer();
    this.addFeatrue([
      {
        ZuoBiao: "120.28017873663885, 31.527949543160503"
      }
    ])
  },
  methods: {
    resetFeatureStyle(feature) {
      let features = this.source.getFeatures();
      features.forEach(f => {
        let icon = require("static/icons/ren_a.png");
        let type = f.values_.poiType.split(",")[1];
        let poitree = this.poitreeList.filter(p => p.poiCode == type);

        if (feature == f) {
          if (poitree.length > 0) {
            if (poitree[0].poiMapicona) {
              icon = this.baseUrl + poitree[0].poiMapicona;
            }
          }
        } else {
          if (poitree.length > 0) {
            if (poitree[0].poiMapicon) {
              icon = this.baseUrl + poitree[0].poiMapicon;
            }
          }
        }
        f.setStyle(ff => {
          return new ol.style.Style({
            image: new ol.style.Icon({
              crossOrigin: "anonymous",
              src: icon,
              anchor: [0.5, 1],
              size: new ol.size.toSize([40, 40])
            })
          });
        });
      });
    },
    createLayer() {
      this.source = new ol.source.Vector();
      this.pointLayer = new ol.layer.Vector({
        zIndex: "11",
        source: this.source,
        style: new ol.style.Style({
          image: new ol.style.Icon({
            crossOrigin: "anonymous",
            src: require("static/icons/ren_a.png"),
            anchor: [0.5, 1],
            size: new ol.size.toSize([40, 40])
          })
        })
      });
      this.map.addLayer(this.pointLayer);
      this.pointLayer.name = "pointLayer";
      this.pointLayer.clickEvent = this.map.on("singleclick", clickEvent);
      let _this = this;
      function clickEvent(e) {
        console.log(ol.proj.transform(
          e.coordinate,
          "EPSG:3857",
          "EPSG:4326"
        ));
        let feature = "";
        let pixel = e.map.getEventPixel(e.originalEvent);
        feature = e.map.forEachFeatureAtPixel(
          pixel,
          function (feature, l) {
            return feature;
          },
          {
            layerFilter: function (layer) {
              return layer.name == "pointLayer";
            }
          }
        );
        if (typeof feature != "undefined") {
          // uni.$emit("openDeatil", feature.getProperties());

        }
      }
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
      features.forEach(f => {
        f.setStyle(() => {
          let icon = require("static/icons/ren_a.png");
          return new ol.style.Style({
            image: new ol.style.Icon({
              crossOrigin: "anonymous",
              src: icon,
              anchor: [0.5, 1],
              size: new ol.size.toSize([40, 40])
            })
          });
        });
      });
      this.source.addFeatures(features);
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
</style>
