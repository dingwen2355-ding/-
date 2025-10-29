const sysConfig = { // eslint-disable-line no-unused-vars
  map: {
    mapType: 'arcgisMap', // gaodeMap openlayerMap gaodePliceMap
    key: '66bb68fc7e755c2d8059eb0a8a5a8389',
    /**
     * 测绘院地图 http://map.smi.sh.cegn.cn/OneMapServer/rest/services/shmap_as3_19/MapServer
     * 互联网地图 https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer
     */
    mapUrl: 'http://map.smi.sh.cegn.cn/OneMapServer/rest/services/shmap_as3_19/MapServer',
    mapCsysType: 1 // 地图坐标系 csysType 坐标系类型，1表示WGS84,4表示GCJ02,3表示BD09(百度坐标系）
  },
  gkits: {
    ip: '218.58.131.116',
    port: '8089'
  }
}
