// import { createScripts } from './api/loadJsCss.js'
import ZnvSuperMap from './api/znvSuperMap.js'
import './css/map.css'
// 动态引用地图JS
/*
* mapConfig:
* 可以直接传如地图名称字符串，openlayerMap || gaodeMap || baiduMap || superMap
* 也可以传入对象，如
* superMap:
* {
*   mapName: 'superMap', // 地图类型名称
*   apiUrl: 'http://127.0.0.1:8882/superMap/libs/', // 地图api地址
*   mapServeUrl: 'http://127.0.0.1:8090/iserver/services/map-china400/rest/maps/China_4326' // 地图服务地址
* }
*
* */
class ZMapAPI {
  constructor(mapConfig) {
    return new Promise((resolve, reject) => {
      if (mapConfig === 'superMap' || mapConfig.mapName === 'superMap') {
        // let url1 = (mapConfig.apiUrl || 'http://10.45.157.223:8882/superMap/libs/') + 'SuperMap.Include.js'
        // let url2 = (mapConfig.apiUrl || 'http://10.45.157.223:8882/superMap/libs/') + 'SuperMap-8.1.1-14426.js'
        // createScripts([url1], function () {
        //   createScripts([url2], function () {
        //     let ZMap = new ZnvSuperMap(mapConfig.mapServeUrl)
        //     try {
        //       resolve(ZMap)
        //     } catch (e) {
        //       reject(e)
        //     }
        //   })
        // })
        let ZMap = new ZnvSuperMap(mapConfig.mapServeUrl)
        try {
          resolve(ZMap)
        } catch (e) {
          reject(e)
        }
      }
    })
  }
}

export default ZMapAPI
