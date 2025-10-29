import { initBaiduProj } from "./baidu.js";

export function initBDLayer(map) {
  initBaiduProj();
  let bmercResolutions = new Array(19);
  for (let i = 0; i < 19; ++i) {
    bmercResolutions[i] = Math.pow(2, 18 - i);
  }
  var tilegrid = new ol.tilegrid.TileGrid({
    origin: [0, 0],
    resolutions: bmercResolutions
  });
  var baidu_source = new ol.source.TileImage({
    projection: "baidu",
    tileGrid: tilegrid,
    tileUrlFunction: function(tileCoord, pixelRatio, proj) {
      if (!tileCoord) {
        return "";
      }
      var z = tileCoord[0];
      var x = tileCoord[1];
      var y = tileCoord[2];
      if (x < 0) {
        x = "M" + -x;
      }
      if (y < 0) {
        y = "M" + -y;
      }
      return (
        "http://maponline2.bdimg.com/tile/?qt=vtile&x=" +
        x +
        "&y=" +
        y +
        "&z=" +
        z +
        "&styles=pl&scaler=1&udt=20210506&from=jsapi3_0"
      );
    }
  });
  //百度地图
  var baidu_layer = new ol.layer.Tile({
    name: "bdmap_25D_heatmap",
    category: "BaseMap",
    source: baidu_source,
    visible: true
  });
  map.addLayer(baidu_layer);
  return baidu_layer;
}
