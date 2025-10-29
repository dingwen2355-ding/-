const mapConfig = {
  useGeoServer: true, //这个配置决定你用不用geoServer
  mapId: "omap", // 地图dom
  proj: "EPSG:4326",
  view: {
    center: [117.25751, 34.7959],
    zoom: 11,
    zooms: [5, 18],
    projection: "EPSG:4326"
  },
  projDefs: [
    {
      name: "EPSG:4527",
      text:
        "+proj=tmerc +lat_0=0 +lon_0=117 +k=1 +x_0=39500000 +y_0=0 +ellps=GRS80 +units=m +no_defs"
    },
    {
      name: "EPSG:32650",
      text: "+proj=utm +zone=50 +datum=WGS84 +units=m +no_defs"
    },
    {
      name: "EPSG:32651",
      text: "+proj=utm +zone=51 +datum=WGS84 +units=m +no_defs "
    }
  ],
  layers: [
    {
      type: "TDTIMG",
      layers: [
        {
          layerName: "TDTIMG",
          type: "TDTIMG",
          visible: false
        },
        {
          layerName: "TDTIMG_cia",
          type: "TDTCIA",
          visible: false
        }
      ]
    },
    {
      type: "TDTVEC",
      layers: [
        {
          layerName: "TDTVEC",
          type: "TDTVEC",
          visible: true
        },
        {
          layerName: "TDTVEC_cva",
          type: "TDTCVA",
          visible: true
        }
      ]
    }
  ],
  regionWMSOption: {
    layer: "ocn:grids",
    format: "image/png",
    version: "1.1.0",
    url: "http://wx.gisocn.com:13233/geoserver/ocn/wms"
  },
  regionWFSOption: {
    layer: "ocn:grids",
    format: "json",
    version: "1.1.0",
    url: "http://wx.gisocn.com:13233/geoserver/wfs"
  }
};
window.mapConfig = mapConfig;
