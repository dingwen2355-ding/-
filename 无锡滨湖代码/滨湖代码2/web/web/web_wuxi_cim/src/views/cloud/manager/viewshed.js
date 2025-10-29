import Tools from './tools.class'

class ViewshedBIZ extends Tools {
    constructor() {
        super()

        this.option = {
            coordinate: [0, 0, 0],
            fov_h: 60,
            fov_v: 30,
            height: 0,
            visibleColor: '#FF0000',
            invisibleColor: '#00FF00',

            setting: [
                //线
                // { type: 'input', name: '可视域名称', value: '' },
                {
                    type: 'slider',
                    name: '水平视角（度）',
                    key: 'fov_h',
                    value: 60,
                    min: 1,
                    max: 150,
                    step: 1,
                },
                {
                    type: 'slider',
                    name: '垂直视角（度）',
                    value: 30,
                    key: 'fov_v',
                    min: 1,
                    max: 150,
                    step: 1,
                },
                {
                    type: 'slider',
                    name: '视点高度（米）',
                    value: 0,
                    key: 'height',
                    min: 0,
                    max: 1000,
                    step: 1,
                },
                {
                    type: 'color',
                    name: '可见区域',
                    value: '#FF0000',
                    opacity: 1,
                    key: 'visibleColor',
                },
                {
                    type: 'color',
                    name: '不可见区域',
                    value: '#00FF00',
                    opacity: 1,
                    key: 'invisibleColor',
                },
            ],
            curOa: {},
            type: '可视域'
        }
        this.isEditing = false
        this.Oa = []
    }
}

ViewshedBIZ.prototype.getSetting = function () {
    return this.option
}

ViewshedBIZ.prototype.onEvent = function (data) {
    let that = this
    return new Promise((resolve, reject) => {
        if (that.isEditing) {
            that.option.setting[2].value = data.position[2]
        }
    })
}

/**
 * 开始
 */
ViewshedBIZ.prototype.start = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        return new Promise(async (resolve, reject) => {
            if (that.isEditing) await that.cancel()

            await that.startViewshedAnalysis(that.option)
            that.isEditing = true
            resolve()
        })
    })
}

/**
 * 更新绘制基础模型
 */
ViewshedBIZ.prototype.update = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.startViewshedAnalysis(that.option.curOa)
        resolve()
    })
}

/**
 * 取消
 */
ViewshedBIZ.prototype.cancel = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        if (that.isEditing) {
            await that.stopViewshedAnalysis()
            that.isEditing = false
        }
        resolve()
    })
}

ViewshedBIZ.prototype.finish = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.cancel()
    })
}

export default ViewshedBIZ