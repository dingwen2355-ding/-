import Tools from './tools.class'

class SkylineBIZ extends Tools {
    constructor() {
        super()

        this.option = {
            setting: [
                {
                    type: 'radio',
                    name: '显示轮廓线',
                    value: true,
                    key: 'showOutline',
                    options: [
                        { label: '是', value: true },
                        { label: '否', value: false },
                    ],
                },
                {
                    type: 'slider',
                    name: '轮廓宽度',
                    value: 1,
                    key: 'outlineThickness',
                    min: 0.1,
                    max: 10,
                    step: 0.1,
                },
                {
                    type: 'color',
                    name: '轮廓颜色',
                    value: '#00FF00',
                    opacity: 1,
                    key: 'outlineColor',
                },
                {
                    type: 'radio',
                    name: '使用场景颜色',
                    value: false,
                    key: 'useSceneColor',
                    options: [
                        { label: '是', value: true },
                        { label: '否', value: false },
                    ],
                },
                {
                    type: 'color',
                    name: '场景颜色',
                    value: '#000000',
                    opacity: 1,
                    key: 'sceneColor',
                },
                {
                    type: 'radio',
                    name: '显示天际线窗口',
                    value: true,
                    key: 'showSkyline',
                    options: [
                        { label: '是', value: true },
                        { label: '否', value: false },
                    ],
                },
                {
                    type: 'array',
                    name: '天际线窗口',
                    value: [400, 200],
                    key: 'windowSize',
                    min: 50,
                    max: 2000,
                    step: 1,
                },
                {
                    type: 'color',
                    name: '天际颜色',
                    value: '#00FF00',
                    opacity: 1,
                    key: 'skylineColor',
                },
                {
                    type: 'color',
                    name: '窗口颜色',
                    value: '#000000',
                    opacity: 0.75,
                    key: 'backgroundColor',
                },
                {
                    type: 'slider',
                    name: '视点高度',
                    value: 0,
                    key: 'height',
                    min: 0,
                    max: 10000,
                    step: 1,
                },
            ],
            curOa: {},
            type: '天际线'
        }
        this.isEditing = false
        this.Oa = []
    }
}

SkylineBIZ.prototype.getSetting = function () {
    return this.option
}

/**
 * 开始
 */
SkylineBIZ.prototype.start = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        for (let i in that.option.setting) {
            that.option.curOa[that.option.setting[i].key] = that.option.setting[i].value
        }
        if (that.isEditing) await that.cancel()
        await that.startSkylineAnalysis(that.option.curOa)
        that.isEditing = true
        resolve()
    })
}

/**
 * 更新绘制基础模型
 */
SkylineBIZ.prototype.update = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.startSkylineAnalysis(that.option.curOa)
        resolve()
    })
}

/**
 * 取消
 */
SkylineBIZ.prototype.cancel = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        if (that.isEditing) {
            await that.stopSkylineAnalysis()
            that.isEditing = false
        }
        resolve()
    })
}

SkylineBIZ.prototype.finish = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.cancel()
    })
}

export default SkylineBIZ