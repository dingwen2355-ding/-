class SettingsPanel {
    constructor() { }

    /**
     * 设置地图样式
     * @param {MapMode} mode 地图样式，0：指南针；1：小地图；2：大地图
     * @param {object} options 地图模式相关的参数，目前支持的选项有下面这些（如果某个参数没有设置，会使用默认值）：
     * serviceType: 服务类型，0：MVT矢量切片； 1：WMTS(ArcGis) （默认值是0）
     * coordType: 坐标系类型，0：经纬度；1：本地（默认值是0）
     * mapPoint: 同名点，取值范围：[x,y]，（默认值是[0, 0]）
     * longitude: 经度，取值范围：[0~180]（默认值是0.0）
     * latitude: 纬度，取值范围：[0~90]（默认值是0.0）
     * cache: 缓存路径，字符串地址，（默认值是 ":memory:"）
     * style: 风格路径，字符串地址，（默认值是 "mapbox://styles/mapbox/streets-v10"）
     * groundHeight: 地面高度，取值范围：[0~任意数值]（默认值是0.0）
     * renderMode: 渲染模式，0：正常；1：透明；2：标注；3：贴地（默认值是0）
     * serverURL : WMTS风格路径，二维数组，元素说明：index[0]服务id，index[1]服务地址；取值示例：[['111', 'http://192.168.1.29:81'], ['222', 'http://192.168.1.29:82'], ['333', 'http://192.168.1.29:83']]
     * coordOrder: 坐标顺序，0: XY; 1: YX，默认值：0
     * maxLevel : WMTS服务最大显示层级，取值范围：[0~22]，默认值：10
     * @returns 可选的回调函数
     */
    setMapMode = function (mode, options) {
        return new Promise((resolve, reject) => {
            window.origAPI.settingsPanel.setMapMode(mode, options, () => {
                resolve()
            })
        })
    }
}

export default SettingsPanel