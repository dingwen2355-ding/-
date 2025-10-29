// 智慧党建
import request from "@/utils/tool/request";

// 统计概括
export let GetPartyCompanyTypeCountData = function( data ){
    return request({
        url: '/binhu/getPartyCompanyTypeCountData',
        data,
        method: 'post'
    });
};

// 党员商铺
export let GetPartyMerchantList = function( data ){
    return request({
        url: '/binhu/getPartyMerchantList',
        data,
        method: 'post'
    });
};

// 党建数据
export let GetPartyMemberStatistic = function( data ){
    return request({
        url: '/binhu/getPartyMemberStatistic',
        data,
        method: 'post'
    });
};

// 资源可见
export let GetResourceVisualization = function( data ){
    return request({
        url: '/binhu/getResourceVisualization',
        data,
        method: 'post'
    });
};

// 今日客流
export let GetPersonnum = function( data ){
    return request({
        url: '/binhu/getPersonnum',
        data,
        method: 'post'
    });
};

// 获取点位信息的弹窗
export let GetPartyOrgDetail = function( data ){
    return request({
        url: '/binhu/getPartyOrgDetail',
        data,
        method: 'post'
    });
};