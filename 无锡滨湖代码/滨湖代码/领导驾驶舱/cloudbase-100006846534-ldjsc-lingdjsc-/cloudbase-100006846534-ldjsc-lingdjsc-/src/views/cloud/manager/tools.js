import Tools from './tools.class'

class ToolsBIZ extends Tools {
    constructor() {
        super()

        // 测量默认参数
        this.toolsSetting = {
            mode: {
                1: MeasurementMode.Coordinate,
                2: MeasurementMode.Linear,
                3: MeasurementMode.Horizontal,
                4: MeasurementMode.Vertical,
                5: MeasurementMode.MultiPoint,
                6: MeasurementMode.Volume
            },
            options: {
                'lineSize': 3.0,
                'pointSize': 8.0,
                'textColor': Color.Yellow,
                'pointColor': [0, 0, 1, 0.3],
                'lineColor': Color.Red,
                'areaColor': [0, 1, 0, 0.3],
                'showCoordinateText': false
            }
        }
        // 是否进入测量状态
        this.isMeasuring = false
        this.isClip = false
    }
}

ToolsBIZ.prototype.getIsClip = function () {
    return this.isClip
}

/**
 * TileLayer多边形剖切
 */
ToolsBIZ.prototype.startPolygonClip_biz = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.isClip = true

        await that.startPolygonClip()
        resolve()
    })
}
/**
 * 停止多边形剖切
 */
ToolsBIZ.prototype.stopClip_biz = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.isClip = false

        await that.stopClip()
        resolve()
    })
}
/**
 * 设置测量模式及相关参数
 */
ToolsBIZ.prototype.setMeasurement_biz = function (type, OPTS) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let options = {
            'lineSize': 3.0,
            'pointSize': 8.0,
            'textColor': Color.Yellow,
            'pointColor': [0, 0, 1, 0.3],
            'lineColor': Color.Red,
            'areaColor': [0, 1, 0, 0.3],
            'showCoordinateText': false
        }
        let measurementMode = that.toolsSetting.mode[type] || that.toolsSetting.mode[1]
        Object.assign(options, OPTS)

        if (!that.isMeasuring) await that.startMeasurement_biz()
        await that.setMeasurement(measurementMode, options)
        resolve()
    })
}
/**
 * 开始测量，用户可以在三维场景中点击鼠标进行测量
 */
ToolsBIZ.prototype.startMeasurement_biz = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.isMeasuring = true

        await that.startMeasurement()
        resolve()
    })
}
/**
 * 停止测量
 */
ToolsBIZ.prototype.stopMeasurement_biz = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.isMeasuring = false

        await that.stopMeasurement()
        resolve()
    })
}
/**
 * TODO 待优化 事件监听
 */
ToolsBIZ.prototype.onEvent = function (data) {
    // TODO 230423
    console.log('------------------------------------------------------------ToolsBIZ.onEvent', data)
    return false
}

ToolsBIZ.prototype.clear = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.stopClip_biz()
    })
}

export default ToolsBIZ