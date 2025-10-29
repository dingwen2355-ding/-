//坐标变换工具
import * as CoordTransform from "@/utils/coordtransform";
import request from "@/utils/request";

export class FenceTool {
    constructor() {
        //电子坐标颜色
        this.colorArr = [
            "#FF384A", //所有
            "#0072ff", //右
            "#5c00f2", //左
            "#1BDDD5", //中
          ]; 
    }

    /**
     * 显示围栏
     * @param {*} txtUrl 保存围栏数据的txt文件路径
     * @returns 
     */
     Show( txtUrl="./data/polyline.txt" ) {
        __g.polyline.clear(); //清除所有的电子围栏
        let that = this;
        this.getTxtDataAndChangeArray(txtUrl).then((res) => {
            //数组坐标添加电子围栏坐标
            res = that.ArrayAddFenceCoordinate(res);
  
            //加载电子围栏
            let arr = [];
            for (let i = 0; i < res.length; i++) {
                let oneData = that.FenceCoordinateData( res[i],  i);
                arr.push(oneData) //添加围栏
            }
            //显示电子围栏
            __g.polyline.add(arr);
        });
    }

  
    /**
     * 清除电子围栏
     */
    static Clear() {
        __g.polyline.clear();
    }

    /**
     * 获取txt文件数据并转为数组格式
     * @param {*} url 
     * @returns 
     */
    async getTxtDataAndChangeArray(url) {
        let formatData = [];
        await request.get(url).then((res) => {
            let listData = res.split("\r\n");
            for (let i = 0; i < listData.length; i++) {
                let arr = listData[i].split("：");
                if (arr[0] == "") {
                    continue;
                }
                let  myArray = JSON.parse(arr[1]);
                formatData.push(myArray);
            }
        });
        return formatData;
    }

    /**
     * 数组坐标添加电子围栏坐标
     * @param {*} data 
     * @returns 
     */
    ArrayAddFenceCoordinate(data) {
        let that = this;
        //循环添加元素
        for (let i = 0; i < data.length; i++) {
            for (let y = 0; y < data[i].length; y++) {
                //转为 wgp8 的坐标
                let itemCoord = that.getCoord(    
                    parseFloat(data[i][y][0]),
                    parseFloat(data[i][y][1])
                )
                data[i][y][0] = itemCoord[0];
                data[i][y][1] = itemCoord[1];
                data[i][y][2] = 25; //添加地图高度25
            }
        }
        return data;
    }
  
    /**
     * 坐标平移转换
     * @param {*} lng 
     * @param {*} lat 
     * @returns 
     */ 
    getCoord(lng, lat) {
        const points = CoordTransform.TransformByEPSG(4326, 4549, lng, lat);
        return points;
    }

      /**
       * 电子围栏需要用的数据
       * coordinate = 坐标点
       * @param {*} coordinate 
       * @param {*} colorString 
       * @param {*} i 
       * @returns 
       */
      FenceCoordinateData(coordinate, i) {
        let p1 = {
          id: "polylineFL" + i,
          coordinates:coordinate,
          coordinateType: 0, //坐标系类型，取值范围：0为Projection类型，1为WGS84类型，2为火星坐标系(GCJ02)，3为百度坐标系(BD09)，默认值：0
          range: [1, 10000], //可视范围：[近裁距离, 远裁距离]，取值范围: [任意负值, 任意正值]
          flowRate: 0.7,
          shape: 0,
          color: this.colorArr[i], //边框颜色
          thickness: 2, //边框厚度
          intensity: 6, //亮度
          style: 2, //单色 请参照API开发文档选取枚举  0~5
          depthTest: true, //是否做深度检测 开启后会被地形高度遮挡
        };
        return p1;
      }
}