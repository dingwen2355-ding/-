import HighlightArea from './highlightArea.class'

class HighlightAreaBIZ extends HighlightArea {
    constructor() {
        super()
    }
}

/**
 * 显示HighlightArea
 */
HighlightAreaBIZ.prototype.show_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.show(ids)
        resolve()
    })
}
/**
 * 隐藏HighlightArea
 */
HighlightAreaBIZ.prototype.hide_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.hide(ids)
        resolve()
    })
}
/**
 * 删除一个或多个HighlightArea对象
 */
HighlightAreaBIZ.prototype.delete_biz = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.delete(ids)
        resolve()
    })
}
/**
 * 自动定位到合适的观察距离
 */
HighlightAreaBIZ.prototype.focus_biz = function (data, distance, flyTime, rotation) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let ids = window.sealAPI.getIds(data)
        if (!ids) resolve()

        await that.focus(ids, distance, flyTime, rotation)
        resolve()
    })
}

export default HighlightAreaBIZ