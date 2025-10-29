import TileLayer from './tileLayer.class'

class TileLayerBIZ extends TileLayer {
    constructor() {
        super()

        this.showStyle = '' // 显示风格
        this.isInit = false
    }
}

TileLayerBIZ.prototype.initTreeData_3dt = function () {
    let that = this
    let dataList = window.tree ? window.tree.treeData.media3dt : []
    let count = dataList.length
    if (count && !that.isInit) {
        that.isInit = true
        let styleType = {
            '默认': 0,
            'X光': 1,
            '纯色': 2,
            '水晶体': 3,
            '暗黑': 4,
        }
        that.updateBegin()
        for (let i = 0; i < count; i++) {
            let option = dataList[i]
            if (styleType[option.displayStyle] && that.showStyle == 'theDefault') {
                that.setStyle(option.id, styleType[option.displayStyle], option.originalColor)
            }
        }
        that.updateEnd()
    }
}
/**
 * 根据ID获取TileLayer的详细信息
 */
TileLayerBIZ.prototype.get_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        let result= await that.get(ids)
        resolve(result)
    })
}
/**
 * 显示TileLayer图层
 */
TileLayerBIZ.prototype.show_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.show(ids)
        resolve()
    })
}
/**
 * 隐藏TileLayer图层
 */
TileLayerBIZ.prototype.hide_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.hide(ids)
        resolve()
    })
}
/**
 * 自动定位到合适的观察距离
 */
TileLayerBIZ.prototype.focus_biz = function (data, distance, flyTime, rotation) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.focus(ids, distance, flyTime, rotation)
        resolve()
    })
}
/**
 * 删除一个或多个TileLayer对象
 */
TileLayerBIZ.prototype.delete_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.delete(ids)
        resolve()
    })
}
/**
 * 清空所有挖洞操作
 */
TileLayerBIZ.prototype.clearHole_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.clearHole(ids)
        resolve()
    })
}
/**
 * 清空所有压平操作
 */
TileLayerBIZ.prototype.clearModifier_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.clearModifier(ids)
        resolve()
    })
}
/**
 * TileLayer的样式
 */
TileLayerBIZ.prototype.setStyle_biz = function (data, style = 0, color = [1, 0, 1, 0.0381]) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.setStyle(ids, Number(style), color)
        resolve()
    })
}
/**
 * 设置TileLayer是否参与碰撞检测
 */
TileLayerBIZ.prototype.setCollision_biz = function (data, enabled, mouseInteract, mouseFunction, characterCollision) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.setCollision(ids, enabled, mouseInteract, mouseFunction, characterCollision)
        resolve()
    })
}

/**
 * 启用X光
 */
TileLayerBIZ.prototype.enableXRay_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.enableXRay(ids)
        resolve()
    })
}

/**
 * 禁用X光
 */
TileLayerBIZ.prototype.disableXRay_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.disableXRay(ids)
        resolve()
    })
}

export default TileLayerBIZ