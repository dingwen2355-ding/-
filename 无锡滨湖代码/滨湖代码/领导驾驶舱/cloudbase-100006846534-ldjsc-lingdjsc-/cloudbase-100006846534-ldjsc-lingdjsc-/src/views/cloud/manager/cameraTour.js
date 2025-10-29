import CameraTour from './cameraTour.class'

class CameraTourBIZ extends CameraTour {
    constructor() {
        super()

        this.cameraTourOa = []
        this.curOa = {
            id: '',
            name: '',
            time: 0,
            keyFrames: []
        }
    }
}

CameraTourBIZ.prototype.play_biz = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        that.curOa.id = 'camera' + Date.now()
        var newKeyFrames = []
        for (var i = 0; i < that.curOa.keyFrames.length; i++) {
            var cur = {
                index: that.curOa.keyFrames[i].index,
                time: that.curOa.keyFrames[i].time,
                location: that.curOa.keyFrames[i].location,
                rotation: that.curOa.keyFrames[i].rotation,
            }
            newKeyFrames.push(cur)
        }
        await that.add(new CameraTourData(that.curOa.id, that.curOa.name, newKeyFrames))
        await that.play(that.curOa.id)

        resolve()
    })
}

CameraTourBIZ.prototype.stop_biz = function () {
    let that = this
    return new Promise(async (resolve, reject) => {
        await that.stop(that.curOa.id)
        await that.delete(that.curOa.id)

        resolve()
    })
}

CameraTourBIZ.prototype.cancel = function () {
    let that = this
    return new Promise((resolve, reject) => {
        that.curOa.keyFrames = []
        that.curOa.time = 0
        resolve()
    })
}

export default CameraTourBIZ