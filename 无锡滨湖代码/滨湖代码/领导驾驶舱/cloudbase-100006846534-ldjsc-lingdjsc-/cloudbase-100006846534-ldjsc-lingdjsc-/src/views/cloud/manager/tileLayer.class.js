class TileLayer {
    constructor() { }

    /**
     * 添加一个或多个TileLayer对象
     * @param {object | array} data 数据结构，支持对象或数组，对于每一个对象支持以下属性：
     * id (string) 字符串类型的ID
     * groupId (string) 可选，Group分组
     * userData (string) 可选，用户自定义数据
     * fileName (string) 3dt文件路径
     * visible (boolean) 添加后是否可见，默认值：true 可见
     * releaseWhenHidden (boolean) 隐藏图层时是否释放资源，默认值：false 不释放
     * location (array) 位置坐标：[X,Y,Z]，取值示例，数组元素类型：(number)，取值范围：[任意数值]
     * rotation (array) 旋转：[Pitch,Yaw,Roll]，数组元素类型：(number)，取值范围：[任意数值]
     * scale (array) 缩放：[X,Y,Z]，数组元素类型：(number)，取值范围：[任意正整数]
     * @returns 可选的回调函数
     */
    add = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.add(data, () => {
                resolve()
            })
        })
    }
    /**
     * 根据ID获取TileLayer的详细信息
     * @param {string | array} ids 要获取的TileLayer对象ID或者ID数组（可以获取一个或者多个）
     * @returns 可选的回调函数
     */
    get = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.get(ids, (resp) => {
                resolve(resp)
            })
        })
    }
    /**
     * 显示TileLayer图层
     * @param {string | array} ids TileLayer对象的ID或者ID数组
     * @returns 可选的回调函数
     */
    show = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.show(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 隐藏TileLayer图层
     * @param {string | array} ids TileLayer对象的ID或者ID数组
     * @returns 可选的回调函数
     */
    hide = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.hide(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 删除一个或多个TileLayer对象
     * @param {string | array} ids 要删除的TileLayer对象的ID或者ID数组（可以删除一个或者多个）
     * @returns 可选的回调函数
     */
    delete = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.delete(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 自动定位到合适的观察距离
     * @param {string | array} ids TileLayer对象的ID或者ID数组
     * @param {number} distance 可选参数，观察点距离目标点（被拍摄物体）的距离，取值范围：[0.01~任意正数]，如果设置为0或者不设置，系统自动计算
     * @param {number} flyTime 可选参数，相机飞行的时间，取值范围：[0~任意正数]，单位：秒，默认值2秒
     * @param {array} rotation 可选参数，相机旋转的欧拉角：[Pitch,Yaw,Roll]，数组元素类型：(number)，取值范围：Pitch[-90~90] Yaw[-180~180] Roll[0]
     * @returns 可选的回调函数
     */
    focus = function (ids, distance, flyTime, rotation) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.focus(ids, distance, flyTime, rotation, () => {
                resolve()
            })
        })
    }
    /**
     * 根据挖洞多边形的坐标添加一个或多个挖洞操作，注意：同时只能对一个图层id进行挖洞操作
     * @param {object | array} data 对象或数组类型，如果是数组类型，对于每个数组元素，有以下属性：
     * id (string) 挖洞操作唯一标识符
     * tileLayerId (string) TileLayer对象的ID
     * coordinates (array) 挖洞多边形的坐标数组，二维数组，数组元素类型：挖洞多边形的坐标，取值示例
     * innerRings (array) 挖洞多边形的内环，三维数组，数组元素类型：挖洞多边形的坐标数组
     * isReverseCut (boolean) 挖洞多边形是否反转，默认值：false
     * @returns 可选的回调函数
     */
    addHole = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.addHole(data, () => {
                resolve()
            })
        })
    }
    /**
     * 删除一个挖洞操作
     * @param {string} id 唯一标识符
     * @param {string} tileLayerId TileLayer对象的ID
     * @returns 可选的回调函数
     */
    deleteHole = function (id, tileLayerId) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.deleteHole(id, tileLayerId, () => {
                resolve()
            })
        })
    }
    /**
     * 清空所有挖洞操作
     * @param {string | array} ids TileLayer对象的ID或数组
     * @returns 可选的回调函数
     */
    clearHole = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.clearHole(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 添加一个压平操作
     * @param {string} id 唯一标识符
     * @param {string} tileLayerId TileLayer对象的ID
     * @param {array} coordinates 压平多边形的坐标
     * @param {number} ententBufferSize 羽化范围，取值范围：[任意正浮点数]，单位：米
     * @returns 可选的回调函数
     */
    addModifier = function (id, tileLayerId, coordinates, ententBufferSize) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.addModifier(id, tileLayerId, coordinates, ententBufferSize, () => {
                resolve()
            })
        })
    }
    /**
     * 根据多边形的坐标添加一个或多个压平操作，注意：同时只能对一个图层id进行压平操作
     * @param {object | array} data 对象或数组类型，如果是数组类型，对于每个数组元素，有以下属性：
     * id (string) 压平操作唯一标识符
     * tileLayerId (string) TileLayer对象的ID
     * coordinates (array) 压平多边形的坐标数组，二维数组，数组元素类型：压平多边形的坐标，取值示例
     * innerRings (array) 压平多边形的内环，三维数组，数组元素类型：压平多边形的坐标数组
     * ententBufferSize (number) 羽化范围，取值范围：[任意正浮点数]，单位：米
     * @returns 可选的回调函数
     */
    addModifiers = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.addModifiers(data, () => {
                resolve()
            })
        })
    }
    /**
     * 删除一个压平操作
     * @param {string} id 唯一标识符
     * @param {string} tileLayerId TileLayer对象的ID
     * @returns 可选的回调函数
     */
    deleteModifier = function (id, tileLayerId) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.deleteModifier(id, tileLayerId, () => {
                resolve()
            })
        })
    }
    /**
     * 清空所有压平操作
     * @param {string | array} ids TileLayer对象的ID或数组
     * @returns 可选的回调函数
     */
    clearModifier = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.clearModifier(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 启用X光
     * @param {number | array} ids TileLayer的ID或者ID数组
     * @param {Color} color 颜色，支持四种格式
     * @returns 可选的回调函数
     */
    enableXRay = function (ids, color) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.enableXRay(ids, color, () => {
                resolve()
            })
        })
    }
    /**
     * 禁用X光
     * @param {number | array} ids TileLayer的ID或者ID数组
     * @returns 可选的回调函数
     */
    disableXRay = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.disableXRay(ids, () => {
                resolve()
            })
        })
    }
    /**
     * 高亮一个Actor
     * @param {string} id TileLayer的ID
     * @param {string} objectId Actor的ID
     * @returns 可选的回调函数
     */
    highlightActor = function (id, objectId) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.highlightActor(id, objectId, () => {
                resolve()
            })
        })
    }
    /**
     * 取消高亮一个Actor
     * @param {string} id TileLayer的ID
     * @param {string} objectId Actor的ID
     * @returns 可选的回调函数
     */
    stopHighlightActor = function (id, objectId) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.stopHighlightActor(id, objectId, () => {
                resolve()
            })
        })
    }
    /**
     * 高亮多个Actor
     * @param {object} data 数据对象，支持以下属性：
     * id (string) TileLayer的ID
     * objectIds (array) TileLayer里的Actor的ObjectID（单个或者数组）
     * @returns 可选的回调函数
     */
    highlightActors = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.highlightActors(data, () => {
                resolve()
            })
        })
    }
    /**
     * 停止高亮Actor
     * @param {object | array} data 数据结构，支持对象或数组，对于每一个对象支持以下属性：
     * id (string) TileLayer的ID
     * objectIds (array) TileLayer里的Actor的ObjectID（单个或者数组）
     * @returns 可选的回调函数
     */
    stopHighlightActors = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.stopHighlightActors(data, () => {
                resolve()
            })
        })
    }
    /**
     * 显示Actor
     * @param {string} id TileLayer的ID
     * @param {string} objectId Actor的ID
     * @returns 可选的回调函数
     */
    showActor = function (id, objectId) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.showActor(id, objectId, () => {
                resolve()
            })
        })
    }
    /**
     * 隐藏Actor
     * @param {string} id TileLayer的ID
     * @param {string} objectId Actor的ID
     * @returns 可选的回调函数
     */
    hideActor = function (id, objectId) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.hideActor(id, objectId, () => {
                resolve()
            })
        })
    }
    /**
     * 定位一个Actor
     * @param {string} id TileLayer的ID
     * @param {string} objectId Actor的ID
     * @param {number} distance 可选参数，观察点距离目标点（被拍摄物体）的距离，取值范围：[0.01~任意正数]，如果设置为0或者不设置，系统自动计算
     * @param {number} flyTime 可选参数，相机飞行的时间，取值范围：[0~任意正数]，单位：秒，默认值2秒
     * @param {array} rotation 可选参数，相机旋转的欧拉角：[Pitch,Yaw,Roll]，数组元素类型：(number)，取值范围：Pitch[-90~90] Yaw[-180~180] Roll[0]
     * @returns 可选的回调函数
     */
    focusActor = function (id, objectId, distance, flyTime, rotation) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.focusActor(id, objectId, distance, flyTime, rotation, () => {
                resolve()
            })
        })
    }
    /**
     * 显示一个或多个Actor
     * @param {object | array} data 数据结构，支持对象或数组，对于每一个对象支持以下属性：
     * id (string) TileLayer的ID
     * objectIds (array) TileLayer里的Actor的ObjectID（单个或者数组）
     * @returns 可选的回调函数
     */
    showActors = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.showActors(data, () => {
                resolve()
            })
        })
    }
    /**
     * 显示指定tilelayer的所有Actor
     * @param {string | array} tileLayerIds TileLayer的ID或ID数组 数据结构，支持字符串或数组
     * @returns 可选的回调函数
     */
    showAllActors = function (tileLayerIds) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.showAllActors(tileLayerIds, () => {
                resolve()
            })
        })
    }
    /**
     * 隐藏一个或多个Actor
     * @param {object | array} data 数据结构，支持对象或数组，对于每一个对象支持以下属性：
     * id (string) TileLayer的ID
     * objectIds (array) TileLayer里的Actor的ObjectID（单个或者数组）
     * @returns 可选的回调函数
     */
    hideActors = function (data) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.hideActors(data, () => {
                resolve()
            })
        })
    }
    /**
     * 获取指定TileLayer包含的所有Actor对象的ID
     * @param {string | array} ids TileLayer的ID或者ID数组
     * @returns 可选的回调函数
     */
    getObjectIDs = function (ids) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.getObjectIDs(ids, (resp) => {
                resolve(resp)
            })
        })
    }
    /**
     * 查询所有图层是否支持压平
     * @returns 可选的回调函数
     */
    getAllFlattenInfo = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.getAllFlattenInfo((resp) => {
                resolve(resp)
            })
        })
    }
    /**
     * 设置TileLayer的样式
     * @param {string | array} ids TileLayer的ID或ID数组
     * @param {number} style 样式， 0：默认；1：X光；2：纯色；3：水晶体；4：暗黑；5：科幻；6：扩散
     * @param {Color} color 颜色，默认值:Color.White，支持四种格式
     * @param {number} saturation 饱和度，仅在默认样式0下生效，取值范围：[0~2]，默认值：1.0
     * @param {number} brightness 亮度 ，仅在默认样式0下生效，取值范围：[0.1~10]，默认值：1.0
     * @param {number} contrast 对比度，仅在默认样式0下生效，取值范围：[0.2~5]，默认值：1.0
     * @param {number} contrastBase 对比度基准，仅在默认样式0下生效，取值范围：[0.036~0.9]，默认值：0.18
     * @returns 可选的回调函数
     */
    setStyle = function (ids, style, color, saturation, brightness, contrast, contrastBase) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.setStyle(ids, style, color, saturation, brightness, contrast, contrastBase, () => {
                resolve()
            })
        })
    }
    /**
     * 设置三维图层在播放导览时各视口的可见性 注意：此设置仅在播放导览时生效
     * @param {string} id TileLayer唯一标识符
     * @param {Viewport} vp 视口掩码（Viewport位运算）
     * @returns 
     */
    setViewportVisible = function (id, vp) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.setViewportVisible(id, vp, () => {
                resolve()
            })
        })
    }
    /**
     * 设置TileLayer是否参与碰撞检测
     * @param {string | array} ids TileLayer的ID或ID数组
     * @param {boolean} enabled 是否开启碰撞检测的总开关，如果此参数设置为false，则下面三个参数均会失效
     * @param {boolean} mouseInteract 是否开启鼠标交互，默认值：true 开启
     * @param {boolean} mouseFunction 是否开启鼠标相关的功能交互，包含鼠标拾取、分析工具、测量工具等，默认值：true 开启
     * @param {boolean} characterCollision 是否开启角色碰撞，默认值：true 开启角色碰撞
     * @returns 可选的回调函数
     */
    setCollision = function (ids, enabled, mouseInteract, mouseFunction, characterCollision) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.setCollision(ids, enabled, mouseInteract, mouseFunction, characterCollision, () => {
                resolve()
            })
        })
    }
    /**
     * 设置平移
     * @param {string} id TileLayer的ID
     * @param {array} newVal 新的位置坐标：[X,Y,Z]，取值示例，数组元素类型：(number)，取值范围：[任意数值]
     * @returns 可选的回调函数
     */
    setLocation = function (id, newVal) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.setLocation(id, newVal, () => {
                resolve()
            })
        })
    }
    /**
     * 设置旋转
     * @param {string} id TileLayer的ID
     * @param {array} newVal 新的旋转坐标：[Pitch,Yaw,Roll]，数组元素类型：(number)，取值范围：[任意数值]
     * @returns 可选的回调函数
     */
    setRotation = function (id, newVal) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.setRotation(id, newVal, () => {
                resolve()
            })
        })
    }
    /**
     * 设置缩放
     * @param {string} id TileLayer的ID
     * @param {array} newVal 新的缩放：[X,Y,Z]，数组元素类型：(number)，取值范围：[任意正整数]
     * @returns 可选的回调函数
     */
    setScale = function (id, newVal) {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.setScale(id, newVal, () => {
                resolve()
            })
        })
    }
    /**
     * 用于批量多次修改对象的属性
     * 在开始修改之前调用updateBegin，然后可以多次调用setXXX方法，最后调用updateEnd提交修改更新数据
       注意：
       updateBegin不是异步调用，不需要await，也没有回调函数参数
     * @returns 可选的回调函数
     */
    updateBegin = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.updateBegin(() => {
                resolve()
            })
        })
    }
    /**
     * 用于批量多次修改对象的属性，与updateBegin配套使用
       注意：
       updateEnd是异步调用，可以用回调函数也可以await
     * @returns 可选的回调函数
     */
    updateEnd = function () {
        return new Promise((resolve, reject) => {
            window.origAPI.tileLayer.updateEnd(() => {
                resolve()
            })
        })
    }
}

export default TileLayer