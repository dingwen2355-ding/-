import { Message } from 'element-ui';

var __that = undefined;
var __globalObject = undefined;
function drawhelper(globalObject) {
    this.isEditing = false;
    this.drawType = 0;
    __globalObject = globalObject;
    __that = this;
    this.resolve=undefined;
}
/**
 * 绘制
 * @param {*} type 1 线 2 曲线 3 面 4 体 5 光流 6 ODline 7 热力图 8 高亮区 9 辐射区 10地形开挖
 */
drawhelper.prototype.drawParam = function (type) {
    return new Promise((resolve, reject) => {
        this.resolve=resolve;
        if (type) {
            __that.drawType = type;
        } else {
            type = __that.drawType;
        }
        if (type === 1) {
            __that.setParam(0, 0, 0);
        } else if (type === 2) {
            __that.setParam(1, 0, 0);
        } else if (type === 3) {
            __that.setParam(0, 1, 1);
        } else if (type === 4) {
            __that.setParam(0, 1, 1);
        } else if (type === 5) {
            __that.setParam(0, 0, 0);
        } else if (type === 6) {
            __that.setParam(0, 0, 0);
        } else if (type === 7 && !__that.isEditing) { //热力图

        } else if (type === 8) {
            __that.setParam(0, 1, 1);
        } else if (type === 9) {
            __that.isEditing = true;
        } else if (type === 10) {
            window.sealAPI._tools.stopClip_biz().then(() => {
                __that.setParam(0, 1, 1);
            });
        }
    })
}

/**
 * 设置绘制参数
 * @param {*} lineType number	线类型，0：直线，1：曲线，默认值是0
 * @param {*} buildType number	绘制类型，0：画多点线段， 1：画多边形， 默认值是0 
 * @param {*} drawType number	绘制显示类型，0：线 1：平面， 默认值是0
 * @param {*} color any	颜色
 * @param {*} drawThickness number	默认值10，当DrawType为线时设置无效
 * @returns 
 */
drawhelper.prototype.setParam = function (lineType, buildType, drawType, _color, _drawThickness) {
    return new Promise((resolve, reject) => {
        let color2 = _color || Color.Red;      //绘制颜色
        window.origAPI.editHelper.setParam(lineType, buildType, color2, () => {
            if (__that.isEditing) {
                resolve();
            } else {
                __that.start().then(() => {
                    addDblclick();
                    resolve();
                })
            }
        });
    })
}

function addDblclick() {
    window.origAPI.player.container.querySelector('video').addEventListener('dblclick', listenFinishDraw, false);
}
function removeDblclick() {
    window.origAPI.player.container.querySelector('video').removeEventListener('dblclick', listenFinishDraw, false);
}

function listenFinishDraw(event) {
    __that.finish().then((res) => {
        if (res.success === 0) {
            Message.warning('请先选择绘制区域！');
        }else{
        }
    });
}
/**
 * 进入绘制模式
 * @returns 
 */
drawhelper.prototype.start = function () {
    return new Promise((resolve, reject) => {
        window.origAPI.editHelper.start(() => {
            __that.isEditing = true;
            resolve();
        });
    })
}

/**
 * 取消编辑
 * @returns 
 */
drawhelper.prototype.cancel = function () {
    return new Promise((resolve, reject) => {
        if (__that.isEditing) {
            window.origAPI.editHelper.cancel(() => {
                __that.isEditing = false;
                removeDblclick();
                resolve();
            });
        }
    })
}

/**
 * 绘制结束。调用此方法会结束当前的绘制，并在回调函数中返回绘制类型和坐标点，然后根据这些坐标点再创建相关的几何图形
 * @param {*} withOffset boolean 是否计算工程中心偏移，默认值是true
 * @returns 
 */
drawhelper.prototype.finish = function (withOffset = true) {
    return new Promise((resolve, reject) => {
        window.origAPI.editHelper.finish(withOffset, (response) => {
            __that.isEditing = false;
            //去除最后由于双击绘制的重复点
            deplicate(response.coordinates);
            if (response.coordinates.length > 1) {
                removeDblclick();
                if(__that.resolve){
                    __that.resolve(response);
                    __that.resolve=undefined;
                }
            } else {
                __that.drawParam().then(() => {
                    resolve({ success: 0 })
                })
            }
        });

    })
}

//去除最后由于双击绘制的重复点
function deplicate(array) {
    if (array.length > 1 && array[array.length - 1][0] == array[array.length - 2][0]
        && array[array.length - 1][1] == array[array.length - 2][1]
        && array[array.length - 1][2] == array[array.length - 2][2]) {
        array.pop();
    }
}


/**
 * 用于刷新清除
 * @returns 
 */
drawhelper.prototype.clear = function () {
    return new Promise((resolve, reject) => {
        window.origAPI.editHelper.cancel(() => {
            resolve();
        });
    })
}

export default drawhelper;