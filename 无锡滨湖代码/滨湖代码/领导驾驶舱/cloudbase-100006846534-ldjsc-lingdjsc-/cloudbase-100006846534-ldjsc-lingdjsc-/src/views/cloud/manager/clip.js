import Tools from './tools.class'

class ClipBIZ extends Tools {
    constructor() {
        super()

        this.setting = {
            0: [
                {
                    type: "slider",
                    name: "高度",
                    key: "height",
                    value: 0,
                    min: -100,
                    max: 500,
                    step: 1,
                }
            ],
            1: [
                {
                    type: "slider",
                    name: "左负右正",
                    key: "length",
                    value: 0,
                    min: -10000,
                    max: 10000,
                    step: 1,
                }
            ],
            2: [
                {
                    type: "slider",
                    name: "长",
                    key: "x",
                    value: 20,
                    min: 0,
                    max: 10000,
                    step: 1,
                },
                {
                    type: "slider",
                    name: "宽",
                    key: "y",
                    value: 20,
                    min: 0,
                    max: 10000,
                    step: 1,
                },
                {
                    type: "slider",
                    name: "高",
                    key: "z",
                    value: 20,
                    min: 0,
                    max: 10000,
                    step: 1,
                },
                {
                    type: "radio",
                    name: "剖切方向",
                    value: 0,
                    key: "direction",
                    options: [
                        { label: "正向", value: 0 },
                        { label: "反向", value: 1 },
                    ],
                },
            ]
        }
        this.option = {
            setting: [],
            curOa: {},
            curType: -1
        };
        this.isEditing = false;
        this.Oa = [];
    }
}

ClipBIZ.prototype.getSetting = function () {
    return this.option
}

ClipBIZ.prototype.onEvent = function (data) {
    console.log('------------------------------------------------------------ClipBIZ.onEvent', data)
    console.log('------------------------------------------------------------', window.RTCPlayer.$refs.view)
    let that = this;
    return new Promise(async (resolve, reject) => {
        if (that.isEditing) {
            if (that.option.curType === 0) {
                that.option.curOa.rocationy = false;
                that.option.curOa.location = data.MouseClickPoint;
                that.option.setting[0].value = data.MouseClickPoint[2];
                that.option.curOa.rotation = [0, 0, 90];
                await that.stopPlaneClip();
                await that.startPlaneClip(that.option.curOa.location, [0, 0, -90], true);
                // TODO 230423
                if (window.RTCPlayer && window.RTCPlayer.$refs.view && !window.RTCPlayer.$refs.view.isShowClipSetting) {
                    window.RTCPlayer.$refs.view.isShowClipSetting = true;
                    // this.update();
                }
                if (window.modelPreview && !window.modelPreview.showCloudClipSetting) {
                    window.modelPreview.showCloudClipSetting = true;
                }
                resolve();
            } else if (that.option.curType === 1) {
                that.option.curOa.location = data.MouseClickPoint;
                that.option.setting[0].value = 0;
                that.option.curOa.rocationy = data.MouseClickPoint[1];
                that.option.curOa.rotation = [0, 0, 0];
                await that.stopPlaneClip();
                await that.startPlaneClip(that.option.curOa.location, [0, 0, 0], true);
                // TODO 230423
                if (window.RTCPlayer && window.RTCPlayer.$refs.view && !window.RTCPlayer.$refs.view.isShowClipSetting) {
                    window.RTCPlayer.$refs.view.isShowClipSetting = true;
                    // this.update();
                }
                if (window.modelPreview && !window.modelPreview.showCloudClipSetting) {
                    window.modelPreview.showCloudClipSetting = true;
                }
                resolve();
            } else if (that.option.curType === 2) {
                that.option.curOa.location = data.MouseClickPoint;
                that.option.curOa.rocationy = false;
                await that.stopVolumeClip();
                for (let i in that.option.setting) {
                    that.option.curOa[that.option.setting[i].key] = that.option.setting[i].value;
                }

                that.option.curOa.volumePoint = [
                    that.option.curOa.location[0] - that.option.curOa.x / 2,
                    that.option.curOa.location[1] - that.option.curOa.y / 2,
                    that.option.curOa.location[2] - that.option.curOa.z / 2,
                    that.option.curOa.location[0] + that.option.curOa.x / 2,
                    that.option.curOa.location[1] + that.option.curOa.y / 2,
                    that.option.curOa.location[2] + that.option.curOa.z / 2];
                await that.startVolumeClip(that.option.curOa.volumePoint, that.option.curOa.direction);
                // TODO 230423
                if (window.RTCPlayer && window.RTCPlayer.$refs.view && !window.RTCPlayer.$refs.view.isShowClipSetting) {
                    window.RTCPlayer.$refs.view.isShowClipSetting = true;
                    resolve();
                }
                if (window.modelPreview && !window.modelPreview.showCloudClipSetting) {
                    window.modelPreview.showCloudClipSetting = true;
                    resolve();
                }
            }
        } else {
            resolve();
        }
    })
}

/**
 * 开始
 * @param {*} type  0 横 1纵 2体
 */
ClipBIZ.prototype.start = function (type) {
    let that = this;
    return new Promise(async (resolve, reject) => {
        if (type !== undefined) {
            that.option.curType = type;
        }
        that.option.setting = that.setting[that.option.curType];
        if (type === 0) {
            await that.stopPlaneClip();
        } else if (type === 1) {
            await that.stopPlaneClip();
        } else if (type === 2) {
            await that.stopVolumeClip();
        }
        that.isEditing = true;
        resolve();
    })
}

/**
 * 更新绘制基础模型
 */
ClipBIZ.prototype.update = function () {
    let that = this;
    return new Promise(async (resolve, reject) => {
        if (that.option.curType === 0) {
            await that.stopPlaneClip();
            that.option.curOa.location[2] = that.option.curOa.height;
            await that.startPlaneClip(that.option.curOa.location, [0, 0, -90], true)
        } else if (that.option.curType === 1) {
            await that.stopPlaneClip()
            that.option.curOa.location[1] = that.option.curOa.length;
            await that.startPlaneClip(that.option.curOa.location, [0, 0, 0], true)
        } else if (that.option.curType === 2) {
            await that.stopVolumeClip()
            that.option.curOa.volumePoint = [
                that.option.curOa.location[0] - that.option.curOa.x / 2,
                that.option.curOa.location[1] - that.option.curOa.y / 2,
                that.option.curOa.location[2] - that.option.curOa.z / 2,
                that.option.curOa.location[0] + that.option.curOa.x / 2,
                that.option.curOa.location[1] + that.option.curOa.y / 2,
                that.option.curOa.location[2] + that.option.curOa.z / 2];
            await that.startVolumeClip(that.option.curOa.volumePoint, that.option.curOa.direction)
        }

        resolve();
    })
}

/**
 * 取消
 */
ClipBIZ.prototype.cancel = function () {
    let that = this;
    return new Promise(async (resolve, reject) => {
        if (that.isEditing) {
            await that.stopPlaneClip();
            await that.stopVolumeClip();
            that.isEditing = false;
        }
        resolve();
    })
}

export default ClipBIZ