// 用户api
import { http, t_http } from '../network/request'
import {
    api, menuByLocal, localMenuList
    // platformCode
} from '../config'
import Cookies from 'js-cookie'
// import md5 from 'js-md5'
import { Base64 } from 'js-base64'

// 登录
export function login(params) {
    // 密码传输时加密
    params['password'] = Base64.encode(params['password'])
    return t_http({
        method: 'post',
        url: api + '/system/login',
        data: params
    })
}

// 获取用户信息
export function me() {
    return http({
        method: 'get',
        url: api + '/system/getInfo'
    })
}

// 获取菜单--un
export function menu() {
    // 后台没有处理,把children为null的都删掉
    function rechild(arr) {
        arr.forEach(a => {
            delete a['appGroup']
            if (a.children && a.children.length > 0) {
                rechild(a.children)
            } else {
                delete a['children']
            }
        })
    }
    // 适配key
    // function replaceKey(arr, patharr) {
    //     let fatherArr = JSON.parse(JSON.stringify(patharr))
    //     arr.forEach(a => {
    //         a.icon = a.icon || ''
    //         fatherArr.push(a.path)
    //         a.link = '/' + fatherArr.join('/')
    //         // 子菜单
    //         if (a.children && a.children.length > 0) {
    //             replaceKey(a.children, fatherArr)
    //         }
    //     })
    // }
    return new Promise((resolve, reject) => {
        // 本地列表
        if (menuByLocal) {
            let localMenu = localMenuList
            rechild(localMenu)
            setTimeout(() => {
                resolve(localMenu)
            }, 500)
        } else {
            // 在线获取列表
            http({
                method: 'get',
                url: api + '/system/menu/getRouters'
                // params: { app: platformCode }
            })
            .then(res => {
                let data = res.data
                if (data) {
                    // data = data.filter(m => { return m.appGroup === platformCode })
                    // 去掉children为空的key
                    rechild(data.menuList)
                    // 适配前端参数: 添加字段分开维护link和paeh,本系统只需要用到link,不做适配处理
                    // replaceKey(data, [])
                    resolve(data)
                }
            })
        }
    })
}

// 获取权限列表码
export function getAccessCodeList() {
    // 由于测试服务器这个接口经常挂, 并且这个接口是和菜单一起返回的, 不想改底层
    // 为了解决本地菜单出不来, 这里跑个竞赛, 默认3秒不出结果就当失败了,不等了
    return new Promise((resolve, reject) => {
        resolve([])
        // Promise.race([
        //     new Promise((r1, j1) => {
        //         if (useAccessCodeFilter) {
        //             http({
        //                 method: 'get',
        //                 // params: { app: platformCode }
        //                 url: api + '/ems/v1/emp/perms'
        //             }).then((res) => {
        //                 let data = res.data.map((perm) => {
        //                     return perm.fuction
        //                 })
        //                 r1(data)
        //             })
        //         } else {
        //             r1(false)
        //         }
        //     }),
        //     new Promise((r2, j2) => {
        //         setTimeout(() => {
        //             r2(useAccessCodeFilter ? [] : false)
        //         }, 3000)
        //     })
        // ])
        // .then(data => {
        //     resolve(data)
        // })
    })
}

// 退出登录
export function logout() {
    return new Promise((res, rej) => {
        Cookies.remove('token')
        Cookies.remove('refreshToken')
        // location.href = 'http://2.22.51.26:30002/';
        window.close();
        location.href = 'http://2.22.51.26:30002/';
        res()

        // http({
        //     method: 'get',
        //     url: api + '/system/login/logout'
        // })
        // .then((data) => {
        //     // 这里退出统一重新刷新页面, 清空vuex内的值, 搜索条件不在用token做标识
        //     Cookies.remove('token')
        //     Cookies.remove('refreshToken')
        //     // location.reload()
        //     res(data)
        // })
        // .catch((err) => {
        //     console.log(err)
        //     Cookies.remove('token')
        //     Cookies.remove('refreshToken')
        //     // location.reload()
        // })
    })
}

// 自动登录
export function authorization() {
    return t_http({
        method: 'get',
        url: api + `/system/login/authorization`
    })
}

// 自动登录
export function tohome(token) {
    let loginUrl = `/system/login/tohome?code=${token}`;
    console.log('------------' + loginUrl)
    return t_http({
        method: 'post',
        url: api + loginUrl
    })
}
