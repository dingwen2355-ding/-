// 安防监控
import request from "@/utils/tool/request";

// 视频监控概览 监控类别 1普通监控 2ai监控 3鹰眼接口
export let GetVideonum = function( data ){
    return request({
        url: '/binhu/getVideonum',
        data,
        method: 'post'
    });
};

// AI事件 1当月AI事件总数 2今日ai事件数
export let GetAinum = function( data ){
    return request({
        url: '/binhu/getAinum',
        data,
        method: 'post'
    });
};

// 获取区分视频个数
export let GetAreaVideonum = function( data ){
    return request({
        url: '/binhu/getAreaVideonum',
        data,
        method: 'post'
    });
};

// 获取设备信息 视频
export let GetSecurityVideo = function( data ){
    return request({
        url: '/binhu/getSecurityVideo',
        data,
        method: 'post'
    });
};

// 获取设备信息 视频流HLS
export let GetVideoUrl = function( data ){
    return request({
        url: '/dahua/getVideoUrl',
        data,
        method: 'post'
    });
};




