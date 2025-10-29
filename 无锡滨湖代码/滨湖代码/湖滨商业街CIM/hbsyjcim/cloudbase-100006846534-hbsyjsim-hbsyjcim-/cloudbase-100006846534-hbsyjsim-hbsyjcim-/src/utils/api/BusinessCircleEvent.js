// 商圈事件
import request from "@/utils/tool/request";

// 工单概览 宗
export let GetEventnum = function( data ){
    return request({
        url: '/binhu/getEventnum',
        data,
        method: 'post'
    });
};

// 事件列表
export let GetEventList = function( data ){
    return request({
        url: '/binhu/getEventList',
        data,
        method: 'post'
    });
};

// 事件趋势 0上报数量  1处置数量
export let GetEventTrends = function( data ){
    return request({
        url: '/binhu/getEventTrends',
        data,
        method: 'post'
    });
};

// 事件分类
export let GetEventGroup = function( data ){
    return request({
        url: '/binhu/getEventGroup',
        data,
        method: 'post'
    });
};

// 事件处置时长
export let GetEventDisposal = function( data ){
    return request({
        url: '/binhu/getEventDisposal',
        data,
        method: 'post'
    });
};

// 事件高发类型
export let GetEventGflx = function( data ){
    return request({
        url: '/binhu/getEventGflx',
        data,
        method: 'post'
    });
};



