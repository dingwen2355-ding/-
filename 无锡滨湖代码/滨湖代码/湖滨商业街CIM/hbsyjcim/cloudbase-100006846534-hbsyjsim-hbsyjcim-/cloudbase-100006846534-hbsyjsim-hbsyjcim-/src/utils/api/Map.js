// 获取点位信息
import request from "@/utils/tool/request";

/* 参数说明：video 监控 
            park 停车场 
            csbj 城市部件 
            shop 商铺
            dzb 党支部
*/ 
export let GetPoint = function( data ){
    return request({
        url: '/map/getPoint',
        data,
        method: 'post'
    });
};

// 商圈事件的地图坐标点 数据返回的经纬度是天地图 用 wgs84 的 getCoord方法转
// 城市的坐标点
export let GetCityEventGps = function( data ){
    return request({
        url: '/map/getCityEventGps',
        data,
        method: 'post'
    });
};

// 井蓋的坐标点
export let GetCoverWarnningGps = function( data ){
    return request({
        url: '/map/getCoverWarnningGps',
        data,
        method: 'post'
    });
};