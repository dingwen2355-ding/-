import CameraBIZ from './manager/camera'
import TileLayerBIZ from './manager/tileLayer'
import ToolsBIZ from './manager/tools'
import ViewshedBIZ from './manager/viewshed'
import SkylineBIZ from './manager/skyline'
import ClipBIZ from './manager/clip'
import ModifierBIZ from './manager/modifier'
import MiscBIZ from './manager/misc'
import WeatherBIZ from './manager/weather'
import BeamBIZ from './manager/beam'
import HighlightAreaBIZ from './manager/highlightArea'
import CameraTourBIZ from './manager/cameraTour'
import SettingsBIZ from './manager/settings'
import DrawHelper from './manager/drawhelper'
import Cartesian3 from './matrix/Cartesian3'
import Matrix3 from './matrix/Matrix3'
import Matrix4 from './matrix/Matrix4'
import Quaternion from './matrix/Quaternion'

import store from '@/services/store.provider'

import { Message } from 'element-ui'
import Axios from 'axios'


function onReady(cb) {
    // window.sealAPI._settings.setMainUIVisibility(false)
    globalObject.initClass()
    window.origAPI = __g
    // window.__g=undefined;
    window.origAPI.misc.setMainUIVisibility(false)
    window.origAPI.weather.setCloudDensity(0);
    window.origAPI.weather.setFogParam(0, 0, 0, 0);
    cb && cb()
}

function log(msg, noLineBreak) {
    console.log(msg + (noLineBreak ? '' : '\n'))
}

// 事件监听
function onEvent(data) {
    store.dispatch('SET_EVENTSEALAPI_ACTION', data)

    switch (data.eventtype) {
        case 'LeftMouseButtonClick':
            if (globalObject.EventListener.length > 0) {
                globalObject.EventListener.forEach(item => {
                    item(data)
                })
            }
            globalObject._clip.onEvent(data)
            globalObject._tools.onEvent(data)
            break
        case 'Measurement':
            globalObject._tools.onEvent(data)
            break
        case 'ViewShed':
            globalObject._viewshed.onEvent(data)
            break
    }
}


let globalObject = undefined
function SealAirCityAPI() {
    globalObject = this
}

SealAirCityAPI.prototype.initPlayer = function (instanceId, divId, ipAddress) {
    return new Promise((resolve, reject) => {
        let options = {
            'onReady': () => onReady(resolve(api)),
            'onApiVersion': undefined,
            'onEvent': onEvent,
            'onLog': log,
            'useColorLog': false // 仅用于SDK测试页面，二次开发请设置为false
        }
        let params = {
            'iid': instanceId, // 如果想连接指定的云渲染实例，可以指定这个参数
            'domId': divId,
            'apiOptions': options,
            'showMarker': true,
            'showStartupInfo': true,
        }
        let acp = new AirCityPlayer(ipAddress, params)
        window.AirCityPlayerACP = acp
        // 获取SealAirCityAPI接口
        const api = acp.getAPI()
    })
}
SealAirCityAPI.prototype.initClass = function () {
    this._tileLayer = new TileLayerBIZ()
    this._camera = new CameraBIZ()
    this._tools = new ToolsBIZ()
    this._viewshed = new ViewshedBIZ()
    this._skyline = new SkylineBIZ()
    this._clip = new ClipBIZ()
    this._misc = new MiscBIZ()
    this._modifier = new ModifierBIZ()
    this._weather = new WeatherBIZ()
    this._beam = new BeamBIZ()
    this._highlightArea = new HighlightAreaBIZ()
    this._cameraTour = new CameraTourBIZ()
    this._settings = new SettingsBIZ()

    this._drawHelper = new DrawHelper()
    this.EventListener = []
}


//添加左键单击事件
SealAirCityAPI.prototype.addLeftClick = function (callback) {
    this.EventListener.push(callback)
}

//移除左键单击事件
SealAirCityAPI.prototype.removeLeftClick = function (callback) {
    let index = this.EventListener.indexOf(callback)
    if (index > -1) {
        this.EventListener.splice(index, 1)
    }
}

/**
 * 获取id的值
 * @param {*} data '12' 、['23','23']、{id:'123'}、[{id:'123'},{id:'123'}]
 */
SealAirCityAPI.prototype.getIds = function (data) {
    let ids = null
    switch (Object.prototype.toString.call(data)) {
        case '[object String]':
        case '[object Number]':
            ids = data
            break
        case '[object Array]':
            ids = data.map(item => {
                if (Object.prototype.toString.call(item) === '[object Object]') {
                    if (!!item.id) return item.id
                    return null
                }
                return item
            }).filter(item => !!item)
            break
        case '[object Object]':
            ids = data.id
            break
    }
    return ids
}

/**
 * 检查cloud api 中返回的内容是否正常
 * @param {*} result  
 * result 为0  和 resultMessage 为OK 为正常，返回其他值有问题。
 * {
    'command':	398,
    'result':	0,
    'resultMessage':	'OK',
    'data':	[]
    } 

    系统内置的错误代码
    APIErrorCode = {
          OK: 0,正常 
            InvalidParameters: 1,  参数错误 
            InternalError: 2,内部错误（环境尚未准备好、空指针等） 
            ResourceNotFound: 3,数据不存在 
            AcpProjWKTNotSet: 4,工程未设置坐标系
            CoordinateConversionFailed: 5,坐标转换失败
            IDExists: 6,指定ID的对象已经存在了 
            InvalidRequestType: 7,无效的请求类型 
            InvalidRequestString: 8,无效的请求（包含了InvalidRequestType）
            NoCommand: 9,CommandFactory没有创建对应的Command 
            DataTypeNotSupport: 10, 不支持这种类型的数据（比如压平操作只能是OSGB和Terrain） 
            InvalidOperation: 11,无效的操作 
            CodeMax: 65535
    }
 */

SealAirCityAPI.prototype.kickPlayerByws = function (cloudUrl, masterInstanceId) {
    // TODO wxh
    return new Promise((resolve, reject) => {
        //管理命令
        const ManageCommand = {
            Login: -1,                          //登录，只有登录成功以后，下面的命令才能调用
            GetPorts: 0,                        //获取端口号、版本号
            GetStatus: 1,                       //实时获取运行状态
            GetInstanceList: 100,               //获取实例列表
            GetInstanceInformation: 101,        //获取指定实例的详细信息
            GetOneFreeInstance: 102,            //获取一个空闲的实例信息（正在运行或者尚未启动）
            GetOneFreeInstanceRunning: 103,	    //获取一个空闲的实例信息（正在运行的实例）
            SetInstanceParams: 104,             //设置实例运行参数并启动    
            StopInstance: 105,				    //停止实例运行
            UnlockInstance: 106,                //取消锁定
            KickPlayer: 200,                    //踢出
            GetProjectList: 201,                //获取工程列表
        }
        //管理命令的执行结果
        const ManageResult = {
            OK: 0,
            PermissionDenied: 1,            //没有权限
            NoFreeInstance: 2,				//没有可用的实例
            InstanceNotFound: 3,		    //没有找到指定的实例
            InstanceNotRunning: 4,		    //指定的实例没有在运行
            InvalidParameters: 5,           //参数无效
            ManagerNotFound: 6,             //没有找到实例所属的管理工具
            PlayerNotFound: 7,              //没有找到指定的Player    

            StartInstance_ExeNotExist: 800,             //可执行文件不存在
            StartInstance_ProjectPathNotExist: 801,	    //工程文件不存在
            StartInstance_NoChange: 802,                //没有改变
            StartInstance_ProcessStartFailed: 803,      //进程启动失败
            StartInstance_Locked: 804,                  //无法修改已锁定的实例
        }
        let resultInfo = [
            {
                code: 0,
                info: 'OK'
            },
            {
                code: 1,
                info: '没有权限'
            },
            {
                code: 2,
                info: '没有可用的实例'
            },
            {
                code: 3,
                info: '没有找到指定的实例'
            },
            {
                code: 4,
                info: '指定的实例没有在运行'
            },
            {
                code: 5,
                info: '参数无效'
            },
            {
                code: 6,
                info: '没有找到实例所属的管理工具'
            },
            {
                code: 7,
                info: '没有找到指定的Player'
            },
            {
                code: 800,
                info: '可执行文件不存在'
            },
            {
                code: 801,
                info: '工程文件不存在'
            },
            {
                code: 802,
                info: '没有改变'
            },
            {
                code: 803,
                info: '进程启动失败'
            },
            {
                code: 804,
                info: '无法修改已锁定的实例'
            },
        ]
        let __ws
        let strURL = `ws://${cloudUrl}/manager`
        __ws = new WebSocket(strURL)
        __ws.onopen = function () {
            let o = {
                command: ManageCommand.Login,
                password: '666666'
            }
            __ws.send(JSON.stringify(o))
        }
        __ws.onmessage = function (event) {
            let o = JSON.parse(event.data)
            switch (o.command) {
                case ManageCommand.Login: {
                    if (o.result == ManageResult.OK) {
                        let obj = {
                            command: ManageCommand.KickPlayer,
                            id: masterInstanceId,
                            playerId: -1,
                            authorization: o.authorization
                        }
                        __ws.send(JSON.stringify(obj))
                    }
                } break
                case ManageCommand.KickPlayer: {
                    __ws.close()
                    if (o.result == ManageResult.OK || o.result == ManageResult.PlayerNotFound) { // 踢人成功,但是当刷新页面的时候，实例会被断开，这时再踢人，返回的是“没有找到指定的Player”
                        resolve({
                            code: 0,
                            info: 'OK'
                        })
                    } else {
                        let info = resultInfo.find(o => o.code == o.result)
                        reject(info)
                    }
                } break
            }
        }
        __ws.onclose = function () {
            __ws = null
        }
        __ws.onerror = function (event) {
            __ws = null
        }
    })
}

SealAirCityAPI.prototype.setLocation = function (id, location) {
    return new Promise((resolve, reject) => {
        window.origAPI.customObject.setLocation(id, location).then(() => {
            resolve()
        })
    })
}

SealAirCityAPI.prototype.clear = function () {
    return new Promise((resolve, reject) => {
        resolve()
    })
}

export default SealAirCityAPI


/**
 * 树结构 - 广度遍历
 * @param {*} node  tree => list
 * @param {*} key 默认为 'children' 
 * @returns 
 */
function breadthData(node, key = 'children') {
    if (node instanceof Array) {
        let nodeArr = []
        for (let i = 0; i < node.length; i++) {
            nodeArr = nodeArr.concat(breadthData(node[i], key))
        }
        return nodeArr
    }
    let nodes = []
    if (node) {
        let queue = []
        queue.unshift(node)
        while (queue.length != 0) {
            let item = queue.shift()
            nodes.push(item)
            let childList = item[key] || []
            for (let index = 0; index < childList.length; index++) {
                queue.push(childList[index])
            }
        }
    }
    return nodes
}

/**
 * 树结构 - 深度遍历
 * @param {*} node  
 * @param {*} key 默认为 'children' 
 * @returns 
 */
function deepData(node, key = 'children') {
    if (node instanceof Array) {
        let nodeArr = []
        for (let i = 0; i < node.length; i++) {
            nodeArr = nodeArr.concat(deepData(node[i], key))
        }
        return nodeArr
    }
    let nodes = []
    if (node) {
        let stack = []
        stack.push(node)
        while (stack.length != 0) {
            let item = stack.pop()
            nodes.push(item)
            let childList = item[key] || []
            for (let index = 0; index < childList.length; index++) {
                stack.push(childList[index])
            }
        }
    }
    return nodes
}

function ResizeBox(box, ratio) {
    let newBox = []
    let min = new Cartesian3(box[0], box[1], 0)
    let max = new Cartesian3(box[3], box[4], 0)
    let center = Cartesian3.add(min, max, new Cartesian3())
    center = Cartesian3.multiplyByScalar(center, 0.5, new Cartesian3())
    let dir = Cartesian3.subtract(max, center, new Cartesian3())
    let newDir = Cartesian3.multiplyByScalar(dir, ratio, new Cartesian3())
    let newMax = Cartesian3.add(center, newDir, new Cartesian3())

    dir = Cartesian3.subtract(min, center, new Cartesian3())
    newDir = Cartesian3.multiplyByScalar(dir, ratio, new Cartesian3())
    let newMin = Cartesian3.add(center, newDir, new Cartesian3())

    newBox = [newMin.x, newMin.y, box[2], newMax.x, newMax.y, box[5]]
    return newBox
}

// 在目标点处以某个角度和距离来观察模型,pitch为倾角
function calCameraPosition(target, distance, pitch, yaw) {
    let zAxis = new Cartesian3(0, 0, 1)
    let yAXis = new Cartesian3(0, 1, 0)

    let moveVec3 = Cartesian3.multiplyByScalar(zAxis, distance, new Cartesian3())
    let originCamera = Cartesian3.add(target, moveVec3, new Cartesian3())

    let rotationY = Quaternion.fromAxisAngle(yAXis, -pitch / 180.0 * Math.PI)
    let rotationMY = Matrix3.fromQuaternion(rotationY)
    let negateT = Cartesian3.negate(target, new Cartesian3())
    let IT = Matrix4.fromTranslation(negateT, new Matrix4())
    let T = Matrix4.fromTranslation(target, new Matrix4())

    let TRY = Matrix4.multiplyByMatrix3(T, rotationMY, new Matrix4())
    let TRYIT = Matrix4.multiply(TRY, IT, new Matrix4())
    let newCameraY = Matrix4.multiplyByPoint(TRYIT, originCamera, new Cartesian3())

    let rotationZ = Quaternion.fromAxisAngle(zAxis, -yaw / 180.0 * Math.PI)
    let rotationMZ = Matrix3.fromQuaternion(rotationZ)
    let TRZ = Matrix4.multiplyByMatrix3(T, rotationMZ, new Matrix4())
    let TRZIT = Matrix4.multiply(TRZ, IT, new Matrix4())
    let newCameraZ = Matrix4.multiplyByPoint(TRZIT, newCameraY, new Cartesian3())

    return {
        'x': newCameraZ.x,
        'y': newCameraZ.y,
        'z': newCameraZ.z,
        'pitch': -(90 - pitch),
        'yaw': yaw,
    }
}

let __fn = null
function getMatchServerConfig(host, fn) {

    if ('WebSocket' in window) {

        let url = `ws://${host}`
        __fn = fn

        let ws = new WebSocket(url)
        ws.onopen = function () {
            this.send('{"command":0}')
        }
        ws.onmessage = function (event) {
            let o = JSON.parse(event.data)
            __fn(o)
        }
        ws.onclose = function () {
        }
        ws.onerror = function (event) {
        }
    }
    else {
        this.log('Not Support WebSocket!')
    }
}

export {
    breadthData, deepData, ResizeBox
}