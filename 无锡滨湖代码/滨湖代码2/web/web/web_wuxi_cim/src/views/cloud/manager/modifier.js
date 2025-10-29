import TileLayer from './tileLayer.class'

class ModifierBIZ extends TileLayer {
    constructor() {
        super()
    }
}

/**
 * 添加odline
 * @param {object | array} data 要添加的对象数据（可以是单个对象或者对象数组）
 */
ModifierBIZ.prototype.add = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        if (!!data) await that.addModifier(data.id, data.tileLayerId, data.coordinates, data.ententBufferSize)
        resolve()
    })
}

ModifierBIZ.prototype.show = function (data) {
    this.add(data)
}

ModifierBIZ.prototype.hide = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.delete()
        resolve()
    })
}

ModifierBIZ.prototype.delete = function (data) {
    let that = this
    return new Promise(async (resolve, reject) => {
        if (!!data) await that.deleteModifier(data.id, data.tileLayerId)
        resolve()
    })
}

ModifierBIZ.prototype.clear = function (ids) {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.clearModifier(ids)
        resolve()
    })
}

export default ModifierBIZ