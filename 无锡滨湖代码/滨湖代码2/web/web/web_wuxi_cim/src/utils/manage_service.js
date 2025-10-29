var __isOnlyStatus = false;

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

function getValue(domId) {
	return document.getElementById(domId).value;
}

function getChecked(domId) {
	return document.getElementById(domId).checked;
}


var __ws;
var __index = 0;
var __bConnected = false;

//保存登录成功以后服务器返回的授权信息
var __authorization;

export function connect(config, connectEvent = null, checkInstanceEvent = null, previewEvent = null) {
	if (!(config && config.ws)) return;

	let strURL = `ws://${config.ws}/manager`;
	__ws = new WebSocket(strURL);

	// __ws.onopen = function () {
	//     __bConnected = true;
	//     if (__isOnlyStatus)
	//     msg_GetStatus();
	// }


	__ws.onopen = function () {
		__bConnected = true;
		var o = {
			command: ManageCommand.Login,
			userName: "admin",
			password: "admin"
		}
		__ws.send(JSON.stringify(o));
	}

	__ws.onmessage = function (event) {
		let str = event.data;
		let o = JSON.parse(event.data);

		//检查登录是否过期
		if (o.result == ManageResult.PermissionDenied) {
			alert('登录失败或者登录信息已过期，请重新登录！')
			return;
		}

		switch (o.command) {
			case ManageCommand.Login: {
				let strResult = '服务器返回信息：\n' + str;
				if (o.result == ManageResult.OK) {
					__authorization = o.authorization;  //保存

					strResult += '\n登录成功!';

					if (connectEvent) connectEvent()

					if (__isOnlyStatus)
						msg_GetStatus();
				}
				else
					strResult += '\n登录失败!';
			} break;

			case ManageCommand.GetStatus: break;
			case ManageCommand.GetPorts: break;
			case ManageCommand.GetInstanceList: break;
			case ManageCommand.GetInstanceInformation:
				if (checkInstanceEvent) checkInstanceEvent(str)
				break;
			case ManageCommand.GetOneFreeInstance: break;
			case ManageCommand.GetOneFreeInstanceRunning: break;
			case ManageCommand.SetInstanceParams:
				console.info("工程切换完成", str);
				if (previewEvent) previewEvent(str)
				break;
			case ManageCommand.StopInstance: alert(str); break;
			case ManageCommand.UnlockInstance: alert(str); break;
			case ManageCommand.GetProjectList:
				break;
			case ManageCommand.KickPlayer:
				console.info("踢人完成", str);
				if (previewEvent) previewEvent(str)
				break;
		}

	}

	__ws.onclose = function () {
		__ws = null;
		__bConnected = false;
		// connect(config, connectEvent, checkInstanceEvent, previewEvent);
	}

	__ws.onerror = function (event) {
		__ws = null;
	};
}


// window.onload = connect;




function checkConnection() {
	if (!__bConnected) {
		alert('WebSocket尚未连接！');
		return false;
	}
	return true;
}

function sendData(o) {
	if (!checkConnection())
		return;

	//在发送之前需要附加上授权信息以进行权限验证
	o.authorization = __authorization;

	__ws.send(JSON.stringify(o));
}


//Message: 登录
export function msg_Login(userName = "admin", password = "admin") {
	var o = {
		command: ManageCommand.Login,
		userName: userName,
		password: password
	}
	sendData(o);
}


//Message: 获取端口信息
function msg_GetPorts() {
	sendData({
		command: ManageCommand.GetPorts
	});
}

//Message: 获取实例列表
function msg_GetInstanceList() {

	let bIncludeInstDetails = getChecked('e_IncludeInstInfo');
	let bIncludeConnectionDetails = getChecked('e_IncludeConnInfo');

	var o = {
		command: ManageCommand.GetInstanceList,
		details: bIncludeInstDetails,
		connections: bIncludeConnectionDetails
	}
	sendData(o);
}


//Message: 获取指定实例的详细信息
export function msg_GetInstanceInformation(iid) {
	var o = {
		command: ManageCommand.GetInstanceInformation,
		id: iid
	}
	sendData(o);
}

//Message: 踢出用户
export function msg_KickPlayer(IID) {
	var o = {
		command: ManageCommand.KickPlayer,
		id: IID
	}
	sendData(o);
}

//Message: 获取一个空闲的实例信息（正在运行或者尚未启动）
function msg_GetOneFreeInstance() {
	var o = {
		command: ManageCommand.GetOneFreeInstance
	}
	sendData(o);
}


//Message: 获取一个空闲的实例信息（正在运行的实例）
function msg_GetOneFreeInstanceRunning() {
	var o = {
		command: ManageCommand.GetOneFreeInstanceRunning
	}
	sendData(o);
}


//Message: 设置实例运行参数并启动
export function msg_SetInstanceParams(iid, path) {

	var o = {
		command: ManageCommand.SetInstanceParams,
		quiet: true,     //传递null，生成 JSON字符串的时候就不会有quiet属性
		async: false,                     //async参数是可选的，如果设置为true，那么立即返回结果，如果设置为false或者没有此参数，会等待实例启动结果，然后再返回
		startup: true,  //是否启动实例 
		staticInstance: {
			adjustResolution: true,
			id: iid,
			limitOneClient: true,
			locked: true,
			pauseWhenIdle: true,
			project: path,
			// websocketPort: "0",
		}
	}

	//单独处理工程文件
	// let project = getValue('e_Project');
	// let projectId = parseInt(project);
	// if (isNaN(projectId))
	//     o.staticInstance.project = project;
	// else
	//     o.staticInstance.projectId = projectId;

	sendData(o);
}


//Message: 取消锁定
function msg_UnlockInstance() {

	var o = {
		command: ManageCommand.UnlockInstance,
		id: getValue('e_IID_UnlockInstance')
	}
	sendData(o);
}


//Message: 停止实例运行
function msg_StopInstance() {

	var o = {
		command: ManageCommand.StopInstance,
		id: getValue('e_IID_StopInstance')
	}
	sendData(o);
}


//Message: 获取工程列表
function msg_GetProjectList() {

	var o = {
		command: ManageCommand.GetProjectList,
	}
	sendData(o);
}



function msg_GetStatus() {

	let o = {
		command: ManageCommand.GetStatus
	}
	sendData(o);

	__index = 0;
}