import proj4 from "proj4";
import * as turf from "@turf/turf";
import $ from "jquery";
export function initMap() {
  var map = new ol.Map({
    target: "map",
    view: new ol.View({
      center: ol.proj.transform(
        [120.28017873663885, 31.527949543160503],
        "EPSG:4326",
        "EPSG:3857"
      ),
      zoom: 16,
      maxZoom: 19,
      minZoom: 10,
      projection: new ol.proj.get("EPSG:3857"),
      enableRotation: false,
      // extent: [
      //   13385854.234827878, 3696794.4643414295, 13390180.092897393,
      //   3698440.249106695
      // ]
    }),
    controls: [
      new ol.control.Zoom({
        className: "z-zoom"
      })
    ]
  });
  proj4.defs("EPSG:32650", "+proj=utm +zone=50 +datum=WGS84 +units=m +no_defs");
  proj4.defs("EPSG:32651", "+proj=utm +zone=51 +datum=WGS84 +units=m +no_defs");
  ol.proj.proj4.register(proj4);
  map._baseLayers = []; // 基础图层，底图
  map._pluginLayers = []; // 业务图层：聚合，热力图
  addLayer(map);

  return map;
}
export const overviewMap = new ol.control.OverviewMap({
  collapsed: false,
  collapsible: false,
  className: "z-overviewmap"
});
export function zoomToRange(geojson, map) {
  var bbox = turf.bbox(geojson);
  zoomToExtent(bbox, map);
}
export function zoomToViewer(geojson, map) {
  var bbox = turf.center(geojson);
  let xy1 = ol.proj.transform(
    bbox.geometry.coordinates,
    "EPSG:3857",
    "EPSG:4326"
  );
  viewer.flyToBounds(xy1, {
    heading: 0,
    pitch: -35,
    range: 1000
  });
}

function zoomToExtent(extent, map) {
  var xResolution = Math.abs(extent[0] - extent[2]) / $("#map").width();
  var yResolution = Math.abs(extent[1] - extent[3]) / $("#map").height();
  var resolution =
    (xResolution > yResolution ? xResolution : yResolution) * 1.8;
  if (resolution < map.getView().getResolutionForZoom(18)) {
    resolution = map.getView().getResolutionForZoom(18);
  }
  map.getView().setResolution(resolution);
  map
    .getView()
    .setCenter([(extent[0] + extent[2]) / 2, (extent[1] + extent[3]) / 2]);
}

function addLayer(mapIns) {
  // let tdt_img = new ol.layer.Tile({
  //   name: "tdt_img",
  //   category: "BaseMap",
  //   source: new ol.source.XYZ({
  //     url: window.ONIP + "/zyimg/{z}/{x}/{y}.png",
  //     projection: "EPSG:3857"
  //   }),
  //   // minResolution: mapIns.getView().getResolutionForZoom(18),
  //   visible: false
  // });
  // let tdt_cia = new ol.layer.Tile({
  //   name: "tdt_img_cia",
  //   category: "BaseMap",
  //   source: new ol.source.XYZ({
  //     url:
  //       "http://t" +
  //       Math.round(Math.random() * 7) +
  //       ".tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=8a3ea07ccd07bbbb5ea79c641e8313a8",
  //     projection: "EPSG:3857"
  //   }),
  //   visible: false
  // });
  let tdt_vec = new ol.layer.Tile({
    name: "tdt_vec",
    category: "BaseMap",
    source: new ol.source.XYZ({
      url:
        "http://t" +
        Math.round(Math.random() * 7) +
        ".tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=8a3ea07ccd07bbbb5ea79c641e8313a8",
      projection: "EPSG:3857"
    }),
    // minResolution: mapIns.getView().getResolutionForZoom(18),
    visible: true
  });
  let tdt_cva = new ol.layer.Tile({
    name: "tdt_vec_cva",
    category: "BaseMap",
    source: new ol.source.XYZ({
      url:
        "http://t" +
        Math.round(Math.random() * 7) +
        ".tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=8a3ea07ccd07bbbb5ea79c641e8313a8",
      projection: "EPSG:3857"
    }),
    visible: true
  });
  // mapIns.addLayer(tdt_img);
  // mapIns.addLayer(wxzy_img);
  // mapIns.addLayer(tdt_cia);
  mapIns.addLayer(tdt_vec);
  // mapIns.addLayer(wxzy_vec);
  mapIns.addLayer(tdt_cva);
  // mapIns.addLayer(baidu_layer);
  // mapIns.addLayer(gaode_layer);
  
  mapIns._baseLayers.push(
    // tdt_img,
    // wxzy_img,
    // tdt_cia,
    tdt_vec,
    // wxzy_vec,
    tdt_cva
    // baidu_layer,
    // gaode_layer
  );
}
export function switchBaseLayer() {}
