import axios from "axios";
import proj4 from "proj4";
import $ from "jquery";
import * as turf from "@turf/turf";

export default class OLMap {
  constructor() {
    // 初始化mapconfig.js传入的参数
    this.option = window.mapConfig;
    // 初始化地图
    this.map = "";
    // 定义一个基础的坐标系，比如4326
    this.projection = new ol.proj.get(this.option.proj);
    // 初始化坐标系
    this.projDefs = this.option.projDefs;
  }
  /***********************************图层****************************** */
  // 25D
  getOMAP(option) {
    // {
    //   layerName: "omap",
    //   type: "OMAP",
    //   zooms: [0, 18],
    //   visible: false,
    //   url: "http://wx.gisocn.com:13204/guangrao/lean/",
    //   projection: "EPSG:4527"
    // },
    let tileGrid = new ol.tilegrid.TileGrid({
      origin: [-56255400.354765005, -56255400.354765005],
      resolutions: [
        107.29866095498084608,
        53.64933047749042304,
        26.82466523874521152,
        13.41233261937260576,
        6.70616630968630288,
        3.35308315484315144,
        1.67654157742157572,
        0.83827078871078786,
        0.41913539435539393
      ]
    });
    let source = new ol.source.TileImage({
      projection: new ol.proj.get(option.projection),
      tileGrid: tileGrid,
      tileUrlFunction: function(tileCoord, pixelRatio, projection) {
        let z = tileCoord[0];
        let x = tileCoord[1];
        let y = tileCoord[2];
        let t1 = parseInt(x / 10);
        let t2 = parseInt(y / 10);
        let t3 = x % 10;
        let t4 = y % 10;
        return (
          option.url +
          (z + 12) +
          "/" +
          t1 +
          "/" +
          t2 +
          "/" +
          t3 +
          "_" +
          t4 +
          ".PNG"
        );
      }
    });
    source.on("tileloaderror", function(e) {});
    let omapLayer = new ol.layer.Tile({
      source: source,
      zIndex: 9,
      opacity: 1
    });
    omapLayer.id = option.id;
    this.map.addLayer(omapLayer);
  }

  // 天地图矢量图层
  getTDTVEC(option) {
    let tdtvec = new ol.layer.Tile({
      projection: new ol.proj.get("EPSG:4326"),
      source: new ol.source.XYZ({
        urls: [
          "http://t0.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t1.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t2.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t3.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t4.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t5.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t6.tianditu.com/DataServer?T=vec_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2"
        ]
      }),
      visible: option.visible
    });
    tdtvec.id = "tdtvec";
    this.map.addLayer(tdtvec);
  }

  // 天地图适量图层标注
  getTDTCVA(option) {
    let tdtcva = new ol.layer.Tile({
      source: new ol.source.XYZ({
        urls: [
          "http://t0.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t1.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t2.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t3.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t4.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t5.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t6.tianditu.com/DataServer?T=cva_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2"
        ]
      }),
      visible: option.visible
    });
    tdtcva.id = "tdtcva";
    this.map.addLayer(tdtcva);
  }
  // 天地图矢量图层
  getTDTIMG(option) {
    let tdtvec = new ol.layer.Tile({
      projection: new ol.proj.get("EPSG:4326"),
      source: new ol.source.XYZ({
        urls: [
          "http://t0.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t1.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t2.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t3.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t4.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t5.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t6.tianditu.com/DataServer?T=img_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2"
        ]
      }),
      zIndex: 1,
      visible: option.visible
    });
    tdtvec.id = "tdtvec";
    this.map.addLayer(tdtvec);
  }

  // 天地图影像图层标注
  getTDTCIA(option) {
    let tdtcva = new ol.layer.Tile({
      source: new ol.source.XYZ({
        urls: [
          "http://t0.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t1.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t2.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t3.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t4.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t5.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2",
          "http://t6.tianditu.com/DataServer?T=cia_w&x={x}&y={y}&l={z}&tk=aef1277d283beb91a608f2971d735fb2"
        ]
      }),
      zIndex: 2,
      visible: option.visible
    });
    tdtcva.id = "tdtcva";
    this.map.addLayer(tdtcva);
  }
  //添加wms图层
  addWMSLayer(option) {
    // {
    //   id: "wx_xianWMS",
    //   layers: "wx:wx_xian",
    //   type: "WMS",
    //   format: "image/png",
    //   version: "1.1.0",
    //   url: "http://49.234.237.247:5433/geoserver/wx/wms",
    //   wmsType: "",
    //   zooms: [0, 12],
    //   visible: false,
    //   zIndex: 9
    // },
    let wmsLayer = new ol.layer.Tile({
      source: new ol.source.TileWMS({
        url: option.url,
        crossOrigin: "anonymous",
        serverType: "geoserver",
        params: {
          LAYERS: option.layers,
          FORMAT: option.format || "image/png",
          TRANSPARENT: "true",
          VERSION: "1.1.0"
        }
      }),
      zIndex: option.zIndex || 9,
      maxZoom: option.zooms[1] || 18,
      minZoom: option.zooms[0] || 0,
      visible: option.visible || true
    });
    wmsLayer.id = option.id;
    this.map.addLayer(wmsLayer);
  }
  // 添加TMS图层
  addTMSLayer(option) {
    // {
    //   id: "bazhouTMS",
    //   // layers: "wx:wx_xian",
    //   layers: "guangrao:hotspot",
    //   type: "TMS",
    //   format: "@geojson",
    //   version: "1.0.0",
    //   proj: "EPSG:4326",
    //   url: "http://wx.gisocn.com:13233/geoserver/gwc/service/tms/",
    //   wmsType: "",
    //   zooms: [0, 18],
    //   visible: false,
    //   zIndex: 19
    // }
    let self = this;
    let source = new ol.source.VectorTile({
      format: new ol.format.GeoJSON(),
      projection: "EPSG:4326",
      tileUrlFunction: function(tileCoord) {
        return (
          option.url +
          option.version +
          "/" +
          option.layers +
          "/" +
          option.proj +
          option.format +
          "/" +
          (tileCoord[0] - 1) +
          "/" +
          tileCoord[1] +
          "/" +
          (Math.pow(2, tileCoord[0] - 1) + tileCoord[2]) +
          ".geojson"
        );
      },
      wrapX: true
    });
    let tmsLayer = new ol.layer.VectorTile({
      source: source,
      zIndex: 9,
      opacity: 0.5,
      style: function(feature) {
        let selected = !!self.tmsSelection[feature.id_];
        return new ol.style.Style({
          stroke: new ol.style.Stroke({
            color: selected ? "yellow" : "rgba(200,20,20,0)",
            width: selected ? 2 : 1
          }),
          fill: new ol.style.Fill({
            color: selected ? "rgba(200,20,20,0.9)" : "rgba(20,20,20,0)"
          })
        });
      }
    });
    tmsLayer.id = option.id;
    this.map.addLayer(tmsLayer);
  }

  // 添加矢量图层
  addVectorLayer(option) {
    let source = new ol.source.Vector();
    let vectorLayer = new ol.layer.Vector({
      source: source,
      style: option.olStyle || this.createStyle(option.style || null),
      zIndex: option.zIndex || 10,
      minResolution: self.map
        .getView()
        .getResolutionForZoom(option.showLevel ? option.showLevel[1] : 18),
      maxResolution: self.map
        .getView()
        .getResolutionForZoom(option.showLevel ? option.showLevel[0] : 0)
    });
    vectorLayer.id = option.id;
    if (option.events && option.events.length > 0) {
      for (let i = 0; i < option.events.length; i++) {
        const element = option.events[i];
        this.events.push(element);
      }
    }
    this.map.addLayer(vectorLayer);
    return vectorLayer;
  }
  // 添加抽稀的矢量图层
  addVectorLayerANDcluster(option) {
    if (option.data == null) {
      console.log("如果进行抽稀，则需要先将数据插入。");
      return;
    }
    let GeoJSONformat = new ol.format.GeoJSON();
    let features = GeoJSONformat.readFeatures(option.data);
    let f = [];
    for (let index = 0; index < features.length; index++) {
      const element = features[index];
      element.data = option.data.features[index].properties;
      f.push(element);
    }
    let source = new ol.source.Vector({
      features: f
    });
    let clusterSource = new ol.source.Cluster({
      distance: parseInt(70, 10),
      source: source
    });
    let vectorLayerANDcluster = new ol.layer.Vector({
      source: clusterSource,
      style: this.createStyle(option.style || null),
      zIndex: option.zIndex || 10
    });
    if (option.events && option.events.length > 0) {
      for (let i = 0; i < option.events.length; i++) {
        const element = option.events[i];
        this.events.push(element);
      }
    }
    vectorLayerANDcluster.id = option.id;
    this.map.addLayer(vectorLayerANDcluster);
  }
  // 热力图图层
  getHeatMAP(option) {
    let heatMap = new ol.layer.Heatmap({
      source: new ol.source.Vector(),
      gradient: ["#00f", "#0ff", "#0f0", "#ff0", "#f00"],
      blur: parseInt(15, 10),
      radius: parseInt(8, 10),
      zIndex: 1999
    });
    heatMap.id = option.id;
    this.map.addLayer(heatMap);
  }
  /**
   * 添加图层
   * @param {} option
   */
  addLayers(option) {
    if (option.type == "vector") {
      this.addVectorLayer(option);
    } else if (option.type == "vectorANDcluster") {
      this.addVectorLayerANDcluster(option);
    } else if (option.type == "heatmap") {
      this.getHeatMAP(option);
    } else {
      console.log("没 有配置这个类型的矢量图层，请前往map.js自行配置");
    }
  }
  /***********************************        数据             **********************************/
  // 通过geojson类型的数据给图层添加features
  addFeaturesByGeojson(layer, json) {
    let GeoJSONformat = new ol.format.GeoJSON();
    let features = GeoJSONformat.readFeatures(json);
    for (let index = 0; index < features.length; index++) {
      const element = features[index];
      element.data = element.values_;
    }
    layer.getSource().addFeatures(features);
    return features;
  }
  /*************************************     地图初载方法      ******************************************************** */
  // 加载layers
  initLayer(layers) {
    for (let index = 0; index < layers.length; index++) {
      const element = layers[index];
      switch (element.type) {
        case "TDTVEC":
          this.getTDTVEC(element.layers[0]);
          this.getTDTCVA(element.layers[1]);
          break;
        case "TDTIMG":
          this.getTDTIMG(element.layers[0]);
          this.getTDTCIA(element.layers[1]);
          break;
        default:
          break;
      }
    }
  }
  /**
   * 利用proj4注册并转化坐标系
   */
  transformByProj4() {
    if (this.projDefs.length > 0) {
      for (let i = 0; i < this.projDefs.length; i++) {
        proj4.defs(this.projDefs[i].name, this.projDefs[i].text);
      }
      ol.proj.proj4.register(proj4);
    }
  }
  zoomToExtent(extent, div) {
    var xResolution =
      Math.abs(extent[0] - extent[2]) / $("#" + this.option.mapId).width();
    var yResolution =
      Math.abs(extent[1] - extent[3]) / $("#" + this.option.mapId).height();
    var resolution =
      (xResolution > yResolution ? xResolution : yResolution) * 1.8;
    if (
      resolution <
      this.map.getView().getResolutionForZoom(this.option.view.zooms[1])
    ) {
      resolution = this.map.getView().getResolutionForZoom(18);
    }
    this.map.getView().setResolution(resolution);

    this.map
      .getView()
      .setCenter([(extent[0] + extent[2]) / 2, (extent[1] + extent[3]) / 2]);
  }
  /**
   * 初始init方法
   */
  initMap() {
    const self = this;
    let option = this.option;
    // 转换坐标系
    this.transformByProj4();
    let view = new ol.View({
      center: option.view.center,
      zoom: option.view.zoom || 10,
      maxZoom: option.view.zooms[1] || 18,
      minZoom: option.view.zooms[0] || 0,
      projection: new ol.proj.get(option.view.projection)
    });
    this.map = new ol.Map({
      // target指向map container的id
      target: option.mapId,
      // view指定地图的中心、分辨率、旋转
      view: view,
      // 地图控件
      controls: [],
      extent: [73, 4.15, 135, 54]
    });
    this.initLayer(option.layers);
  }
}
