// 请求的前缀
import IP from "./ipconfig";

export const URL_INDEX = {
    u_index: '',
};

// 数字滨湖
export const URLS_SZBH = {
    u_first_page_hui_ji_mu_lu_count: '/dbApi/first_page_hui_ji_mu_lu_count', // 首页--汇集目录总数
    u_first_page_she_ji_dept_count: '/dbApi/first_page_she_ji_dept_count', // 首页--涉及部门总数
    u_first_page_fu_gai_count: '/dbApi/first_page_fu_gai_count', // 首页--覆盖系统总数
    u_first_page_data_apply_count: '/dbApi/first_page_data_apply_count', // 首页--数据资源申请量
    u_first_page_data_read_count: '/dbApi/first_page_data_read_count', // 首页--数据资源浏览量
    // u_first_page_data_read_count: '/dbApi/first_page_data_read_count', // 首页--数据资源浏览量
    u_first_page_datasource: '/dbApi/first_page_datasource', // 首页--3000个数据资源
    u_first_page_call_count: '/dbApi/first_page_call_count', // 首页--20万累计调用
    u_fisrt_count_sourceShareCount: '/dbApi/fisrt_count_sourceShareCount', // 首页--数据共享
    u_fisrt_count_sourceDownCount: '/dbApi/fisrt_count_sourceDownCount', // 首页--下载次数
    u_fisrt_count_sourceCount: '/dbApi/fisrt_count_sourceCount', // 首页--资源信息项
    u_first_count: "/cmAbility/getUserTotal" // 首页--轮播图下面三个
}

// 数字底座
export const URLS_SZDZ = {
    u_digital_base_hot_source: '/dbApi/digital_base_hot_source', // 数据资源：按照数据资源浏览量，取前5个
    u_digital_base_hot_source_sort: '/dbApi/digital_base_hot_source_sort', // 部门资源排名：按照部门资源排名，取前5个
    u_digital_base_keyWordsSearch: '/dbApi/digital_base_keyWordsSearch', // 关键字资源检索
    u_digital_base_data_count: '/dbApi/digital_base_data_count', // 数据资源
    u_baseSupermarket: '/api/baseSupermarket', // 获取底座超市总计数量
    u_appliationCategoryNum: '/api/appliationCategoryNum', // 应用，工具，组件数量
}

// 应用市场
export const URLS_APPMARKET = {
    u_applicationActivation: '/api/applicationActivation', // 获取应用活跃度
    u_applicationClassification: '/api/applicationClassification', // 获取应用分类
    u_applicationDept: '/api/applicationDept', // 获取应用单位
    u_applicationByClassificationAndDept: '/api/applicationByClassificationAndDept', // 根据应用分类应用单位查询应用

}

// 个人中心
export const URLS_USERINFO = {
    u_userApplication: '/api/userApplication', // 获取用户的应用
    u_applicationList: '/api/applicationList', // 获取所有的应用
    u_checkInitPassword: '/api/checkInitPassword', // 验证用户密码是否是初始密码
    u_changePassword: '/api/changePassword', // 修改密码
    u_getCasUserInfo: '/api/getCasUserInfo', // 获取用户账户
    u_smsNotice: '/api/smsNotice', // 获取短信通知记录
    u_logout: '/logout', // 退出登录
    u_addUserAuthority: '/api/addUserAuthority', // 权限申请
    u_applicationApplyForList: '/api/applicationApplyForList', // 根据申请人查询应用申请
    u_authorityApplyForList: '/api/authorityApplyForList', // 根据申请人查询权限申请
    u_getbUserInfo: '/api/getbUserInfo', // 获取用户信息
}


// 个人中心
export const URLS_APPAPPLY = {
    u_entProductList: '/api/entProductList', // 获取所有企业产品的信息
    u_addApplicationClassification: '/api/addApplicationClassification', // 新增应用所选的应用分类
    u_addApplicationDept: '/api/addApplicationDept', // 新增应用所选的的应用部门
    u_uploadFile: '/api/uploadFile', // 上传图片
    u_addApplication: '/api/addApplication', // 新增应用
}


