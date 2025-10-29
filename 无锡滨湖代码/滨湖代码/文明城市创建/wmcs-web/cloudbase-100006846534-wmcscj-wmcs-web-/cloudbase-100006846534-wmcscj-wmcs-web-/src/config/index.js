const devConfig = require('./verconfig/dev')
const testConfig = require('./verconfig/test')
const masterConfig = require('./verconfig/master')

let serverConfig = ''

if (process.env.VUE_APP_MODE == 'pro') {
    serverConfig = masterConfig
}

if (process.env.VUE_APP_MODE == 'test') {
    serverConfig = testConfig
}

if (process.env.VUE_APP_MODE == 'dev') {
    serverConfig = devConfig
}

export const api = serverConfig.api

// 如果项目内文件上传使用的是相对路径, 可以通过这个参数, 补全全路径, 在富文本上传图片内实现(尚未)
export const apiFileUrl = serverConfig.apiFileUrl || ''

// axios超时时间
export const timeout = 1000 * 100

// =========================================平台
// 在所有平台中: 本平台的code号码
export const platformCode = 'CRMBO'
// 版本号连接
export const verUrlCode = ''
// 使用说明链接
export const verUrlDesc = ''

// 本平台的标题
export const platformTitle = '滨湖区文明城市创建平台'
// 本平台的CopyRight
export const platCopyRight = 'CopyRight'

// =========================================菜单
// 一级菜单是否独立在顶部显示
export const firstMenuInTop = false

// 是否本地获取菜单
export const menuByLocal = serverConfig.menuByLocal

// 本地菜单列表
export const localMenuList = serverConfig.localMenuList

// 是否需要对菜单进行权限码过滤
export const useAccessCodeFilter = true

// 无路由菜单映射路由 [[path(菜单), router(页面)] ... ]
export const noRouterMenuRouter = serverConfig.noRouterMenuRouter || []

// ===================================================路由
// 可以在非登录状态下访问的路由白名单
export const routerWhiteList = serverConfig.routerWhiteList || ['404', '401', 'autologin']

// 登录状态下访问的白名单: 路由name, 或者path
export const loginRouterWhiteList = [
    'detail',
    'home'
]

// 约定好当路径内包含一下字符串时(二级页面), 刷新忽略判断, 避免跳回首页
export const noRefushRouterPath = ['/_info', '/_detail', '/_edit', '/_add', '/pa/', '/person/', '/outpage/', '/_handle', '/_extension', '/_appeal']

// 当从列表页跳转到详情,返回需要保留结果的(默认list->_info/_detail/_edit/_add自动缓存)
export const useKeepAliveArr = [
    // [from, to](routername)
]

// ====================================================登录
// 是否需要使用长token刷短token的机制
export const needRefreshToken = false

// 小token的截止过期时间倒数(秒)
export const tokenRecTime = 5 * 60

// 需要退出登录的错误码: 此设置不包含刷新token和登录本身, 这俩个错误码去登录页面和app.vue单独改
// 正确的登录码也没有配, 统一在network/request.js配
export const codeForReload = ['401', '10000', '403']
