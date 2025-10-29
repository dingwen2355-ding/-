import request from '@/utils/request'

// 应用类别
// 查询应用类别列表
export function cmAppTypeList(query) {
    return request({
        url: '/cmAppType/list',
        method: 'get',
        params: query
    })
}

// 新增应用类别
export function cmAppTypeSave(query) {
    return request({
        url: '/cmAppType/save',
        method: 'post',
        data: query
    })
}

// 应用类别详情
export function cmAppTypeDetails(query) {
    return request({
        url: '/cmAppType/getDetail/' + query.id,
        method: 'get',
        params: query
    })
}

// 删除应用类别
export function cmAppTypeDetele(query) {
    return request({
        url: '/cmAppType/delete/' + query.id,
        method: 'post',
        // data: query
    })
}

// 应用类别状态更新
export function cmAppTypeUpdateStatus(query) {
    return request({
        url: '/cmAppType/updateStatus',
        method: 'post',
        params: query
    })
}

// 应用列表
// 查询应用列表
export function cmAppList(query) {
    return request({
        url: '/cmApp/getAllAppListForAdmin',
        method: 'post',
        data: query
    })
}

// 认证
export function cmAppCertification(query) {
    return request({
        url: '/cmApp/certification',
        method: 'get',
        params: query
    })
}

// 应用列表详情
export function cmAppDetail(query) {
    return request({
        url: '/cmApp/getDetail/' + query.id,
        method: 'get',
        // params: query
    })
}

// 新增应用列表
export function cmAppSave(query) {
    return request({
        url: '/cmApp/save',
        method: 'post',
        data: query
    })
}

// 删除应用列表
export function cmAppDetele(query) {
    return request({
        url: '/cmApp/delete/' + query.id,
        method: 'post',
        // data: query
    })
}

// 应用列表下架
export function cmAppUpdateStatus(query) {
    return request({
        url: '/cmApp/updateStatus',
        method: 'post',
        data: query
    })
}

// 申请上架
// 申请上架列表
export function cmAppReplayList(query, data) {
    return request({
        url: `/cmApp/replayList?pageNum=${data.pageNum}&pageSize=${data.pageSize}`,
        method: 'post',
        data: {
            appType: query?.appType,// 应用类别
            appName: query.appName,// 应用类别
            status: query.status,// 是否上架
            startDate: query.startDate,//
            endDate: query.endDate,//
            params: {
                startDate: query.startDate,//
                endDate: query.endDate,//
            }
        }
    })
}

export function downloadResource(query, data) {
    return request({
        url: `/common/download/resource?resource=${query.resource}`,
        method: 'get',
        // data: {
        //     resource: query.resource,// 应用类别
        //     params: {
        //         resource: query.resource,//
        //     }
        // },
        responseType: 'blob',
        // responseType:'arraybuffer',
        // headers: {
        //     'Content-Type': 'multipart/form-data'
        // }
    })
}

export function getAllAbility(query) {
    return request({
        url: `/cmAbility/getAllAbility?current=${query.current}&size=${query.size}`,
        method: 'post',
        data: query
    })
}


// 应用类别状态更新
export function dictEdit(query) {
    return request({
        url: '/system/dict/data/edit',
        method: 'post',
        data: query
    })
}

// 应用列表详情
export function cmAbilityExamineSave(query) {
    return request({
        url: '/cmAbilityExamine/save',
        method: 'post',
        data: query
    })
}


export function cmMobanAllList(query) {
    return request({
        url: `/cmMoban/allList?current=${query.current}&size=${query.size}`,
        method: 'post',
        data: query
    })
}


export function cmMobanSave(query) {
    return request({
        url: `/cmMoban/save`,
        method: 'post',
        data: query
    })
}

export function cmMobanGet(query) {
    return request({
        url: `cmMoban/mobanUrl?type=${query.type}`,
        method: 'get',
        // params: query
    })
}

export function cmMobanDetele(query) {
    return request({
        url: '/cmMoban/delete/' + query.id,
        method: 'post',
        // data: query
    })
}

// export function cmMobanGet(query) {
//     return request({
//         url: `/cmAbility/getShouYeShenpiCount`,
//         method: 'get',
//     })
// }

// 首页顶部数据
export function cmAbilityGetShouYeShenpiCount(query) {
    return request({
        url: `/cmAbility/getShouYeShenpiCount`,
        method: 'get',
    })
}

// 首页入驻申请占比分类
export function cmAbilityGetRuZhufenleiZhanBi(query) {
    return request({
        //url: `/cmAbility/getRuZhufenleiZhanBi`,
        url: `/cmAbility/getRuZhuDeptZhanBi`,
        method: 'get',
    })
}

// 首页入驻申请来源分析
export function cmAbilityGetRuZhulaiyuanZhanBi(query) {
    return request({
        url: `/cmAbility/getRuZhulaiyuanZhanBi`,
        method: 'get',
    })
}

// 首页审批状态分析
export function apiGetHomePageAnalysis(query) {
    return request({
        url: `/api/getHomePageAnalysis`,
        method: 'get',
    })
}

// 申请量统计
export function apiGetShenqingliangtongji(query) {
    return request({
        url: `/cmAbility/getShenqingliangtongji`,
        method: 'get',
    })
}

// 审批统计
export function apigetShouYeShenpiCount(query) {
    return request({
        url: `/cmAbility/getShouYeShenpiCount`,
        method: 'get',
    })
}

// 申请量top8
export function apiGetApplyForStatistics(query) {
    return request({
        url: `/api/getApplyForRank`,
        method: 'get',
    })
}

// 访问量top8
export function apiGetAccessStatistics(query) {
    return request({
        url: `/api/getAppUseRank`,
        method: 'get',
    })
}

// 申请量一月趋势
export function apiGetApplyForEcharts(query) {
    return request({
        url: `/api/getApplyForEcharts`,
        method: 'get',
    })
}

// 访问量一月趋势
export function apiGetAccessEcharts(query) {
    return request({
        url: `/api/getAccessEcharts`,
        method: 'get',
    })
}

// 访问量统计
export function cmAbilityGetfangwenliangtongji(query) {
    return request({
        url: `/cmAbility/getfangwenliangtongji`,
        method: 'get',
    })
}



// 访问量统计-场景分类占比
export function apiGetfangwenChangjingZhanbi(query) {
    return request({
        url: `/cmAbility/getfangwenChangjingZhanbi`,
        method: 'post',
        data: query
    })
}

// 申请量统计-场景分类占比
export function apiGetshengqingChangjingZhanbi(query) {
    return request({
        url: `/cmAbility/getshengqingChangjingZhanbi`,
        method: 'post',
        data: query
    })
}


// 获取下载量top10
export function gettop10Download(query) {
    return request({
        url: `/cmMobanDownload/gettop10Download`,
        method: 'get',
    })
}

// cmAbility/getShenqingliangtongji
