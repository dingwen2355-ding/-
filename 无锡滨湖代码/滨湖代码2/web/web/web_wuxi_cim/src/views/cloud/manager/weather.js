import Weather from './weather.class'

class WeatherBIZ extends Weather {
    constructor() {
        super()

        this.hour = null
        this.isDark = null
        this.param = {
            '雨': {
                open: false,
                data: [
                    { name: '强度', value: 0.3, min: 0, max: 1, step: 0.01 },
                    { name: '速度', value: 0.4, min: 0, max: 1, step: 0.01 },
                    { name: '雨滴大小', value: 0.5, min: 0, max: 1, step: 0.01 },
                ],
            },
            '雪': {
                open: false,
                data: [
                    { name: '强度', value: 0.3, min: 0, max: 1, step: 0.01 },
                    { name: '速度', value: 0.4, min: 0, max: 1, step: 0.01 },
                    { name: '雪花大小', value: 0.5, min: 0, max: 1, step: 0.01 },
                ],
            },
            '雾': {
                open: false,
                data: [
                    { name: '整体雾浓度', value: 0.5, min: 0, max: 1, step: 0.01 },
                    { name: '地面雾浓度', value: 0.05, min: 0, max: 1, step: 0.01 },
                    { name: '地面雾高度', value: 1, min: 0, max: 10, step: 0.1 },
                ],
            },
            '云': {
                open: false,
                data: [
                    { name: '云层密度', value: 0.5, min: 0, max: 1, step: 0.01 },
                    { name: '云层高度', value: 0.5, min: 0, max: 20, step: 0.01 },
                    { name: '云层厚度', value: 0.5, min: 0, max: 20, step: 0.01 },
                ],
            },
            '太阳': {
                open: false,
                data: [
                    { name: '辐射强度', value: 0.7, min: 0, max: 10, step: 0.01 },
                    { name: '色温值', value: 8500, min: 1700, max: 12000, step: 1 },
                    { name: '阴影质量', value: 2, min: 1, max: 5, step: 0.01 },
                    { name: '阴影可见距离', value: 2000, min: 0, max: 5000, step: 1 },
                ],
            },
            '月亮': {
                open: false,
                data: [
                    { name: '辐射强度', value: 30, min: 0, max: 30, step: 0.01 },
                    { name: '环境光强度', value: 0.3, min: 0, max: 10, step: 0.01 },
                ],
            },
            '阴影': {
                open: false,
                data: [],
            },
        }
        this.initPARAM()
    }
}

/**
 * 获取小时
 */
WeatherBIZ.prototype.getHour = function () {
    return this.hour
}

/**
 * 设置黑暗模式
 * @param {boolean} bDark 布尔值
 */
WeatherBIZ.prototype.setDarkMode_biz = function (bDark) {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.setDarkMode(bDark)
        that.isDark = bDark
        sessionStorage.setItem('bDark', bDark)
        resolve()
    })
}
/**
 * 获取是否为黑暗模式
 */
WeatherBIZ.prototype.getDarkMode = function () {
    let bDark = sessionStorage.getItem('bDark')
    this.isDark = bDark
    return this.isDark
}

/**
 * 设置早、中、晚、夜晚
 * @param {*} hour 小时 [1~24]
 */
WeatherBIZ.prototype.setDateByHour = function (hour) {
    let that = this
    return new Promise(async (resolve, reject) => {
        let _DT = that.getDateTime()
        await that.setDateTime(_DT.year, _DT.month, _DT.day, hour || _DT.hour, _DT.minute, false)
        that.hour = hour
        resolve()
    })
}

/**
 * 初始化天气相关的参数
 */
WeatherBIZ.prototype.initPARAM = function () {
    let that = this
    // setTimeout(
    async () => {
        // 日期时间
        let _DT = await that.getDateTime()
        that.hour = _DT.hour
        // 天气参数
        let res = await that.getParams()
        that.isDark = res.darkMode === 1 // 是否黑暗模式，0关闭 1打开
        sessionStorage.setItem('bDark', that.isDark)
        that.param['云'].data[0].value = res.cloudDensity
        that.param['云'].data[1].value = res.cloudHeight
        that.param['云'].data[2].value = res.cloudThickness
    }
    // , 1000)
}

/**
 * 获取初始配置数据
 */
WeatherBIZ.prototype.getPARAM = function (attr) {
    if (!!attr) return this.param[attr]

    return this.param
}


export default WeatherBIZ