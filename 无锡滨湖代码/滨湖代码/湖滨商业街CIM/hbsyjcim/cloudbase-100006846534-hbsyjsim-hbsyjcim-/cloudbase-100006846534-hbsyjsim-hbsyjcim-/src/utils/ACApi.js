import * as CoordTransform from "@/utils/coordtransform";
export class ACApi {
  constructor() {}
  /**
   * 百度坐标系换工程坐标
   * @param {*} lng 经度
   * @param {*} lat 纬度
   * @returns
   */
  getBD09Coord(lng, lat) {
    // 百度转谷歌、高德
    const a = CoordTransform.BD09ToGCJ02(lng, lat);
    // 谷歌、高德 转84
    const point = CoordTransform.GCJ02ToWGS84(a[0], a[1]);
    // 84转工程系
    const points = CoordTransform.TransformByEPSG(
      4326,
      4549,
      point[0],
      point[1]
    );
    return points;
  }
  /**
   * 谷歌、高德换工程坐标
   * @param {*} lng 经度
   * @param {*} lat 纬度
   * @returns
   */
  getGCJ02Coord(lng, lat) {
    // 谷歌、高德 转84
    const point = CoordTransform.GCJ02ToWGS84(lng, lat);
    // 84转工程系
    const points = CoordTransform.TransformByEPSG(
      4326,
      4549,
      point[0],
      point[1]
    );
    return points;
  }
  /**
   * 添加Polyline对象
   * @param {*} polylineList Polyline对象数组
   * @param {*} color 线条颜色 例:rgb(238, 75, 43)
   */
  async addPolyline(polylineList, color) {
    const arr = [];
    polylineList.map((v, i) => {
      arr.push({
        id: "polyline" + i, //  字符串类型的ID
        coordinates: v.coordinates,
        coordinateType: 0, //坐标系类型，取值范围：0为Projection类型，1为WGS84类型，2为火星坐标系(GCJ02)，3为百度坐标系(BD09)，默认值：0
        range: [1, 10000], //可视范围：[近裁距离, 远裁距离]，取值范围: [任意负值, 任意正值]
        //color: [0, 0, 1, 0.8],//多边形的填充颜色
        flowRate: 0.7,
        shape: 0,
        color: color || "rgb(238, 75, 43)", //边框颜色
        thickness: 2, //边框厚度
        intensity: 6, //亮度
        style: PolylineStyle.DottedCircle, //单色 请参照API开发文档选取枚举
        ///PolygonStyle: 5,//单色 请参照API开发文档选取枚举]
        //PolylineStyle: 2,
        depthTest: true, //是否做深度检测 开启后会被地形高度遮挡
      });
    });

    // 添加一个或多个Polyline对象
    await __g.polyline.add(arr);
  }
}
