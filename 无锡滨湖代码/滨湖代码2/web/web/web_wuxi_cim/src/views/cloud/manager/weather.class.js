class Weather {
    constructor() { }

    /**
     * 获取日期时间
     * @returns 可选的回调函数
     */
    getDateTime = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.getDateTime((resp) => {
                resolve(resp)
            })
        })
    }
    /**
     * 设置日期时间
     * @param {number} year 年
     * @param {number} month 月
     * @param {number} day 日
     * @param {number} hour 时
     * @param {number} minute 分
     * @param {boolean} daynightLoop 是否日夜循环 如果为true 则三分钟模拟循环一天
     * @returns 可选的回调函数
     */
    setDateTime = function (year, month, day, hour, minute, daynightLoop) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setDateTime(year, month, day, hour, minute, daynightLoop, () => {
                resolve()
            })
        })
    }
    /**
     * 获取天气相关的参数
     * @returns 可选的回调函数
     */
    getParams = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.getParams((resp) => {
                resolve(resp)
            })
        })
    }
    /**
     * 设置云层的密度
     * @param {number} cloudDensity 云层密度，取值范围：[0~1.0]
     * @returns 可选的回调函数
     */
    setCloudDensity = function (cloudDensity) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setCloudDensity(cloudDensity, () => {
                resolve()
            })
        })
    }
    /**
     * 设置云层的高度
     * @param {number} cloudHeight 云层高度，取值范围：[0~20]km，单位：公里
     * @returns 可选的回调函数
     */
    setCloudHeight = function (cloudHeight) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setCloudHeight(cloudHeight, () => {
                resolve()
            })
        })
    }
    /**
     * 设置云层的厚度
     * @param {number} cloudThickness 云层厚度，取值范围：[0~20]
     * @returns 可选的回调函数
     */
    setCloudThickness = function (cloudThickness) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setCloudThickness(cloudThickness, () => {
                resolve()
            })
        })
    }
    /**
     * 设置雨效。注意：开启雨效前需先设置云层的厚度和密度
     * @param {number} strength 强度（必须大于0，才会有下雨效果），取值范围:[0~1.0]
     * @param {number} speed 速度，取值范围:[0~10]
     * @param {number} raindropSize 雨滴大小，取值范围:[0~5]
     * @returns 可选的回调函数
     */
    setRainParam = function (strength, speed, raindropSize) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setRainParam(strength, speed, raindropSize, () => {
                resolve()
            })
        })
    }
    /**
     * 设置雪效。注意：开启雪效前需先设置云层的厚度和密度
     * @param {number} strength 强度（必须大于0，才会有下雪效果），取值范围:[0~1.0]
     * @param {number} speed 速度，取值范围:[0~10]
     * @param {number} snowflakeSize 雪花大小，取值范围:[0~25]
     * @returns 可选的回调函数
     */
    setSnowParam = function (strength, speed, snowflakeSize) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setSnowParam(strength, speed, snowflakeSize, () => {
                resolve()
            })
        })
    }
    /**
     * 设置雾效参数
     * @param {number} fogDensity 整体雾浓度，取值范围:[0~1.0]
     * @param {number} fogGroundDensity 地面雾浓度，取值范围:[0~1.0]
     * @param {number} fogGroundHeight 地面雾高度，取值范围:[0~10]（单位KM）
     * @param {number} fogRange 雾化范围，取值范围:[0~1.0]
     * @returns 可选的回调函数
     */
    setFogParam = function (fogDensity, fogGroundDensity, fogGroundHeight, fogRange) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setFogParam(fogDensity, fogGroundDensity, fogGroundHeight, fogRange, () => {
                resolve()
            })
        })
    }
    /**
     * 关闭雨雪效果
     * @returns 可选的回调函数
     */
    disableRainSnow = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.disableRainSnow(() => {
                resolve()
            })
        })
    }
    /**
     * 设置是否进入黑暗模式
     * @param {boolean} bDark 布尔值
     * @returns 可选的回调函数
     */
    setDarkMode = function (bDark) {
        return new Promise((resolve, reject) => {
            window.origAPI.weather.setDarkMode(bDark, () => {
                resolve()
            })
        })
    }

}

export default Weather