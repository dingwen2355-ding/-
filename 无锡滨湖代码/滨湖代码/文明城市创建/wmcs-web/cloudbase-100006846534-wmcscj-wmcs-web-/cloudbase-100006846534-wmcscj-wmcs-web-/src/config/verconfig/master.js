// 正式环境的部署配置
const menuList = require('../menuList')

export const api = '/prod-api'// 网关

export const apiFileUrl = 'http://2.21.137.224:8080/img/wmcstext/'

// 是否本地获取菜单
export const menuByLocal = false

export const localMenuList = menuList.menuList
