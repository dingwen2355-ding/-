import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
export default new Vuex.Store({
    state: {
        token: '',
        searchFilter: {
            token: ''
        },
        // 用于实现队列的数组
        searchArrFirst: [], // 已经被页面注册了需要立即执行的列表-更新搜索项
        // 队列-end
        windowSizeChangeNum: 0, // 窗口改变:宽+高
        windowHeight: 0,
        windowWidth: 0,
        userInfo: {}, // 缓存用户信息: 以token为标识过期信息
        menuInfo: {}, // 缓存菜单信息: 以token为标识过期信息
        nowSecondMenuInfo: [], // 当前第二级菜单数组
        firstMenu: null, // 首页
        allMenuList: [], // 当前所有可被访问的菜单数组(没有children的linkPath)
        accessCodeList: [], // 当前权限码列表
        pubConfig: {} // 远端公共参数
    },
    mutations: {
        setToken(state, payload) {
            state.token = payload
        },
        addSearchFilter(state, filterName) {
            state.searchArrFirst.push(filterName)
        },
        storeSearchFilter(state, obj) {
            // 数仓现在是单接口制,一个条件一个接口,不合并
            Vue.set(state.searchFilter, obj.key, obj.data)
            // Object.keys(obj).forEach((key) => { Vue.set(state.searchFilter, key, obj[key]) })
        },
        setWindowChange(state, obj) {
            state.windowHeight = obj.height
            state.windowWidth = obj.width
            state.windowSizeChangeNum = obj.width + obj.height
        },
        // 缓存用户信息
        setUserInfo(state, user) {
            state.userInfo = user
            // 把权限码单独设置
            // if (user.data.perms) {
            //     state.accessCodeList = user.data.perms
            // }
        },
        // 缓存菜单信息
        setMenuCode(state, menuCode) {
            function filterMenuByCode(arr, code) {
                return arr
            }
            // 这个方法从获取菜单来, 同时维护3组vuex数据
            // 权限组 false 或者数组
            state.accessCodeList = menuCode.data.access_code
            // 二级菜单在真实切换时维护
            // 整个菜单树, 全路径数组, 权限code
            let menuList = JSON.parse(JSON.stringify(menuCode.data.menu_list))
            // 如果需要进行权限过滤,则菜单过滤一遍
            if (menuCode.data.access_code) {
                menuList = filterMenuByCode(menuList, menuCode.data.access_code)
            }
            // 整个菜单树
            state.menuInfo = {
                token: menuCode.token,
                data: menuList
            }
            // 全路径数组
            let allList = []
            function getList(arr, allL) {
                arr.forEach((item) => {
                    if (item.link && item.link.includes('/') && !item['children']) {
                        if (allList.length == 0) {
                            // 首页
                            console.log('缓存首页')
                            state.firstMenu = item
                        }
                        allL.push(item.link)
                    }
                    if (item.children) {
                        getList(item.children, allL)
                    }
                })
            }
            getList(JSON.parse(JSON.stringify(menuList)), allList)
            state.allMenuList = allList
        },
        setSecondMenu(state, secondMenu) {
            state.nowSecondMenuInfo = secondMenu
        },
        upNewToken(state, token) {
            `
                后台现在有一个机制: token有效期2小时, 登录时返回2个TOKEN 每次有接口请求时, 需要判断token剩余时间
                如果token剩余不到5分钟, 则需要重新刷新token(用户延时, token变新的token, 但是用户不变)
                这里前端原先的设计缓存时, 以token判断缓存是否有效, 如果切换用户时需要重新刷新缓存
                为了兼容这俩个设计, 当新的token获取时, 除了更新cookie外, 另外调用此处的方法, 把缓存内的token全部刷新为新token
                理论上可行
            `
            state.token = token
            // 用户信息
            state.userInfo['token'] && (state.userInfo['token'] = token)
            // 菜单
            state.menuInfo['token'] && (state.menuInfo['token'] = token)
            // 已经缓存的搜索条件逐条更新token
            let filters = state.searchFilter
            filters.token && (filters.token = token)
            // for (let key in filters) {
            //     if (key == 'token') {
            //         continue
            //     }
            //     filters[key].token = token
            // }
        },
        upPubConfig(state, config) {
            state.pubConfig = config
        }
    }
})
