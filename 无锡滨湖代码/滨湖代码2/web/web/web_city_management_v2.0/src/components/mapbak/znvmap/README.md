## Install
```shell
npm install znvmap -S
```

## Quick Start
``` shell
<!--第一种写法：直接传入地图类型名称字符串，如'openlayerMap’-->
 <template>
   <div class="znvmap" id="map-container"></div>
 </template>
 import ZMapAPI from 'znvmap'
 // openlayerMap:openlayer地图
 // superMap: 超图
 // gaodeMap: 高德地图
 // baiduMap: 百度地图
 // arcGisMap: arcgis地图
 new ZMapAPI('openlayerMap').then(
   ZMap => {
      let map = new ZMap.Map('map-container',{
        zoom: 11,
        center: {lng:118.6589, lat:32.1474}
      })
   }
 );
 /*
 * 第二种写法：直接传入对象，如
 * superMap:
 * {
 *   mapName: 'superMap', // 地图类型名称
 *   apiUrl: 'http://10.45.157.223:8882/superMap/libs/', // 地图api地址
 *   mapServeUrl: 'http://10.45.157.223:8090/iserver/services/map-china400/rest/maps/China_4326' // 地图服务地址
 * }
 * */
  <template>
    <div class="znvmap" id="map-container"></div>
  </template>
  import ZMapAPI from 'znvmap'
  new ZMapAPI({
    mapName: 'superMap',
    apiUrl: 'http://127.0.0.1:8882/superMap/libs/',
    mapServeUrl: 'http://127.0.0.1:8090/iserver/services/map-china400/rest/maps/China_4326/'
  }).then(
    ZMap => {
        let map = new ZMap.Map('map-container',{
          zoom: 11,
          center: {lng:118.6589, lat:32.1474}
        })
    }
  );
 ```
 
## 地图技术支持
  http://10.45.157.223:8011/MapDemo
