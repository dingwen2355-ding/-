class HighlightArea {
    constructor() { }

    /**
     * 添加一个或多个HighlightArea对象
     * @param {object | array} data 对象或者数组，对于每一个对象支持以下属性：
     * id (string) 字符串类型的ID
     * groupId (string) 可选，Group分组
     * userData (string) 可选，用户自定义数据
     * coordinates (array) 多边形坐标数组（二维数组）
     * color (Color) 多边形高亮颜色，支持四种格式
     * heightRange (array) 高亮染色区域高度范围：[min,max]，数组元素取值范围：[任意浮点数]，取值说明：Z坐标的区间，只有Z值这这个区间的模型才会被染色
     * intensity (number) 高亮颜色的强度，取值范围：[0~1000]
     * depthTest (boolean) 是否做深度检测，默认为true（DepthTest=true会被遮挡，false的话不会被遮挡）
     * @returns 可选的回调函数
     */
    add = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.add(data, () => {
                resolve()
            })
        })
    }
    /**
     * 显示HighlightArea
     * @param {string | array} ids HighlightArea对象的ID或者ID数组
     * @returns 可选的回调函数
     */
    show = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.show(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 隐藏HighlightArea
     * @param {string | array} ids HighlightArea对象的ID或者ID数组
     * @returns 可选的回调函数
     */
    hide = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.hide(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 删除一个或多个HighlightArea对象
     * @param {string | array} ids 要删除的HighlightArea对象的ID或者ID数组（可以删除一个或者多个）
     * @returns 可选的回调函数
     */
    delete = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.delete(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 自动定位到合适的观察距离
     * @param {string | array} ids HighlightArea对象的ID或者ID数组
     * @param {number} distance 可选参数，观察点距离目标点（被拍摄物体）的距离，取值范围：[0.01~任意正数]，如果设置为0或者不设置，系统自动计算
     * @param {number} flyTime 可选参数，相机飞行的时间，取值范围：[0~任意正数]，单位：秒，默认值2秒
     * @param {array} rotation 可选参数，相机旋转的欧拉角：[Pitch,Yaw,Roll]，数组元素类型：(number)，取值范围：Pitch[-90~90] Yaw[-180~180] Roll[0]
     * @returns 可选的回调函数
     */
    focus = function (ids, distance, flyTime, rotation) {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.focus(ids, distance, flyTime, rotation, () => {
                resolve()
            })
        })
    }
    /**
     * 删除场景中所有的HighlightArea
     * @returns 可选的回调函数
     */
    clear = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.clear(() => {
                resolve()
            })
        })
    }
    /**
     * 修改一个或多个HighlightArea对象
     * @param {object | array} data 对象或者数组，数据结构请参考add方法
     * @returns 可选的回调函数
     */
    update = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.highlightArea.update(data, () => {
                resolve()
            })
        })
    }
}

export default HighlightArea