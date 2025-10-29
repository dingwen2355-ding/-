import * as CoordTransform from "@/utils/coordtransform"; //坐标变换工具
import request from "@/utils/request";
import { GetPoint,GetCityEventGps,GetCoverWarnningGps } from "@/utils/api/Map";


export class MarkerTool {
    constructor() {
        this.gg = __g; //重点
    }

    /**
     *  从商圈接口中读取数据
     * @param {*} imgType  图标类型
     * @param {*} zbx     坐标转换类型   如：wgs84  baidu
     * @param {*} distinction 坐标点点击后的识别前缀
     */
    async ShowCityEventGps(imgType = "城市", zbx="wgs84", distinction= "circle" ) {
        let that = this;
        this.getCity( imgType, zbx, distinction ).then((res) => {
            that.Show( res );
        });
    }

      /**
     *  井盖打点
     * @param {*} imgType  图标类型
     * @param {*} zbx     坐标转换类型   如：wgs84  baidu
     * @param {*} distinction 坐标点点击后的识别前缀
     */
    async ShowCoverWarnningGps(imgType = "井盖", zbx="wgs84", distinction= "warnning" ) {
        let that = this;
        this.GetCover( imgType, zbx, distinction ).then((res) => {
            that.Show( res );
        });
    }



    /**
     * 从接口中获取数据
     */
    getPointShow(type = "video", imgType = "普通", distinction = "video", specialData = [], isTextShow = false) {
        let that = this;
        this.getPoint( type, imgType, distinction ).then((res) => {
            console.log('------转后-----获取地图上的坐标点---------');
            console.log(res);
            console.log('--------------------');
            that.Show( res, specialData, isTextShow );
        });
    }

    /**
     * 数据并显示全部 安防监控
     */
    getPointAllShow() {
        let that = this;
        let resList = [];
        this.getPoint("video", "普通").then(( res1 ) => {
            resList = resList.concat(res1);
            this.getPoint("aivideo", "ai").then(( res2 ) => {
                resList = resList.concat( res2 );
                // that.getTxtData("./data/ai.txt").then(( res3 ) => {
                //     resList = resList.concat( res3 );
                    that.Show(resList);
                // });
            });
        });
    }

    /**
     * 显示
     * @param {*} res 坐标数据
     * @param {*} specialData 需要特别处理的数据
     * @param {*} isTextShow  是否显示名称
     * @returns 
     */
    Show(res,specialData = [],isTextShow = false) {
        let markerArr = [];
        let ids = [];
        res = this.SameCoordinates(res);
        // console.log('====================================');
        // console.log(res);
        // console.log('====================================');
        for (let i = 0; i < res.length; i++) {
            const e = res[i];
            let img;

            if (e.type == "ai") {
                img = require("@/assets/SecurityoniMtoring/ai.png");
            }

            if (e.type == "普通") {
                img = require("@/assets/SecurityoniMtoring/video.png");
            }

            // if (e.type == "鹰眼") {
            //     img = require("@/assets/SecurityoniMtoring/eagle.png");
            // }

            if (e.type == "党支部") {
                img = require("@/assets/icon_loudong_point.png");
            }

            if (e.type == "城市") {
                img = require("@/assets/BusinessCircleEvent/icon_map_point_07.png");
            }

            if (e.type == "井盖") {
                img = require("@/assets/BusinessCircleEvent/icon_map_point_16.png");
            }

            let coor = [];
            if (e.zbx == "wgs84") {
                coor = this.getCoord(
                    parseFloat(e.longitude),
                    parseFloat(e.latitude),
                )
            }

            if (e.zbx == "baidu") {
                coor = this.getBD09Coord(
                    parseFloat(e.longitude),
                    parseFloat(e.latitude)
                )
            }

            if (e.z != null && e.z != undefined){
                coor[2] = e.z;
            }
            // console.log("坐标点----",coor)

            let idName = e.distinction + i +"_" + e.id;
            if (e.type == "党支部"){
                idName =  e.distinction + i +"_" + e.name
            }

            //井盖数据传递
            let nameShow = JSON.stringify({
                name: e.name
            });
            if(e.item != null && e.item != undefined){
                nameShow = JSON.stringify({
                    name: e.name,
                    item: e.item,
                });
            }

            //坐标掉
            let marker = {
                id: idName,
                userData: nameShow,
                coordinate: coor, //[x,y,z]
                imagePath: img,
                fontSize: 20,
                displayMode: 2,
                range: [1, 80000],
                imageSize: [48, 48],
            };

            //特别数据处理
            for (let y = 0; y < specialData.length; y++) {
                if (specialData[y].name != null && specialData[y].name != undefined){
                    if (specialData[y].name == e.name){
                        marker.textOffset = specialData[y].textOffset;
                    }
                }
            }
            //是否显示名称
            if (isTextShow == true){
                marker.text = e.name;
            }

            markerArr.push(marker);
            ids.push("video" + i);
        }
        // 添加点位
        __g.marker.add(markerArr);
        // 聚焦点位
        __g.marker.focus(ids, 500);

        console.log("-----地图加载完成并显示------")
    }


    /**
     * 清除
     */
    Clear() {
        __g.marker.clear();
    }


    //相同坐标数据处理
    SameCoordinates(data){
        const processedCoordinates = new Map();
        data.forEach((item) => {
            const coordinateKey = `${item.longitude}-${item.latitude}`;
            if (processedCoordinates.has(coordinateKey)) {
                const count = processedCoordinates.get(coordinateKey);
                item.z = count * 10;
                processedCoordinates.set(coordinateKey, count + 1);
            } else {
                processedCoordinates.set(coordinateKey, 1);
            }
        });
        return data;
    }

    /**
     * 请求商圈接口 城市
     */
    async getCity(imgType = "城市", zbx="wgs84" ,  distinction= "circle") {
        let formatData = [];
        await GetCityEventGps({}).then((res) => {
            console.log("---------商圈事件-城市-获取点位-请求到接口了------");
            console.log(res);
            if( res.code == 200 ) {
              //console.log(res.data);
              let listData = res.data;
              for (let i = 0; i < listData.length; i++) {
                  let arr = listData[i].jingweidu.split(",");
                  let data = {
                      id: listData[i].id,
                      name: listData[i].locationdescription,
                      longitude: arr[0],
                      latitude: arr[1],
                      type: imgType,
                      zbx: zbx,
                      distinction: distinction,
                      item: listData[i],
                  };
                  formatData.push(data);
              }
            }
        });
        return formatData;
    }

     /**
     * 请求商圈接口 井盖
     */
      async GetCover(imgType = "井盖", zbx="wgs84", distinction= "warnning") {
        let formatData = [];
        await GetCoverWarnningGps({}).then((res) => {
            console.log("---------商圈事件-井盖-获取点位-请求到接口了------");
            console.log(res);
            if( res.code == 200 ) {
              let listData = res.data;
              for (let i = 0; i < listData.length; i++) {
                  let data = {
                      id: listData[i].id,
                      name: listData[i].alter_context,
                      longitude: listData[i].lng, 
                      latitude: listData[i].lat, 
                      type: imgType,
                      zbx: zbx,
                      distinction: distinction,
                      item: listData[i],
                  };
                  formatData.push(data);
              }
            }
        });
        return formatData;
    }


    /*
     * 请求接口 监控 党支部
     */
    async getPoint( type = "video", imgType = "普通",  distinction= "video" ) {
        let formatData = [];
        await GetPoint({
            type: type,
        }).then((res) => {
            console.log('------请求到的接口-----获取地图上的坐标点---------');
            console.log(res.data);
            console.log('--------------------');
            if (res.code == 200) {
                let listData = res.data;
                for (let i = 0; i < listData.length; i++) {
                    if (listData[i].lat == '0E-12' || listData[i].lng == '0E-12') {
                        continue;
                    }
                    let data = {
                        id: listData[i].id,
                        name: listData[i].name,
                        longitude: listData[i].lng,
                        latitude: listData[i].lat,
                        type: imgType,
                        zbx: listData[i].zbx,
                        distinction: distinction,
                    };
                    formatData.push(data);
                }
            }
        });
        return formatData;
    }

    /**
     * 从txt中读取数据： ("从txt文件中获取数据")
     * @param {*} url 
     * @returns 
     */
    async getTxtData(url) {
        let formatData = [];
        await request.get(url).then((res) => {
            let listData = res.split("\r\n");
            for (let i = 0; i < listData.length; i++) {
                let arr = listData[i].split(",");
                if (arr[0] == "") {
                    continue;
                }
                let data = {
                    id: i,
                    name: arr[0],
                    longitude: arr[1],
                    latitude: arr[2],
                    type: arr[5],
                    zbx: "wgs84",
                    distinction: "video",
                };
                formatData.push(data);
            }
        });
        return formatData;
    }


    /**
     *  wgs84  坐标转换 
     * @param {*} lng 
     * @param {*} lat 
     * @returns 
     */
    getCoord(lng, lat) {
        const points = CoordTransform.TransformByEPSG(4326, 4549, lng, lat);
        return points;
    }


    /**
     * baidu  百度坐标系换工程坐标
     * @param {*} lng 经度
     * @param {*} lat 纬度
     * @returns 
     */
    getBD09Coord(lng, lat) {
        // 百度转谷歌、高德
        const a = CoordTransform.BD09ToGCJ02(lng, lat)
        // 谷歌、高德 转84
        const point = CoordTransform.GCJ02ToWGS84(a[0], a[1])
        // 84转工程系
        const points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1])
        return points
    }

    /**
     * google 谷歌、高德换工程坐标
     * @param {*} lng 经度
     * @param {*} lat 纬度
     * @returns 
     */
    getGCJ02Coord(lng, lat) {
        // 谷歌、高德 转84
        const point = CoordTransform.GCJ02ToWGS84(lng, lat)
        // 84转工程系
        const points = CoordTransform.TransformByEPSG(4326, 4549, point[0], point[1])
        return points
    }

}