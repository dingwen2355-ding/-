/*
 * @Descripttion: webscoket通讯
 * @version:
 * @Author: GM
 * @Date: 2021-05-13 20:40:56
 * @LastEditors: GM
 * @LastEditTime: 2021-09-02 13:51:40
 */
import store from "@/store";
import moment from "moment";
let socket = null;
let lockReconnet = false; //避免重复连接
let isReconnet = false; //是否真正建立连接
let wsUrl = undefined;
let globalCallback = null,
  sendData = null; //把要发送给socket的数据和处理socket返回数据的回调保存起来
let heartsNum = 3; //心跳次数
let timeout = 30 * 1000; //30秒一次心跳
let timeoutObj = null; //心跳倒计时对象
let serverTimeoutObj = null; //心跳倒计时超时
let timeoutnum = null; //断开 重连倒计时

let createSocket = url => {
  //创建socket
  try {
    if ("WebSocket" in window) {
      socket = new WebSocket(url);
      if (!socket) return;
    } else if ("MozWebSocket" in window) {
      socket = new MozWebSocket(url);
      if (!socket) return;
    }
    initSocket();
  } catch (e) {
    reconnet(url);
  }
};

let initSocket = () => {
  //初始化websocket
  socket.onopen = () => {
    console.log("socket连接成功");
    //开启心跳
    start();
    if (isReconnet) {
      //执行全局回调函数
      console.log("websocket重新连接了");
      isReconnet = false;
    }
  };

  socket.onmessage = ev => {
    if (ev && ev.data != "pong") {
      console.log("接受服务任务消息：");
      console.log(ev);
      // 列表数据处理
      let taskList = store.getters.get_taskList;
      let message = JSON.parse(ev.data);
      if (taskList) {
        taskList.forEach((val, idx, arr) => {
          if (message.taskId == val.taskId) {
            val.completePercent = message.completePercent;
            // 进度条过度效果
            if (val.completePercent != 100) {
              val.processing = true;
            }
            val.message = message.message;
            if (message.endTime) {
              val.endTime = message.endTime;
            }
          }
        });
      }
      // 如果收到的message是有结束时间的和错误消息的话就提示出来,发送到消息。
      if (message.endTime) {
        let tnn = +store.getters.get_taskNewsNum + 1;
        store.commit("statechange", {
          ways: "taskFulfil",
          data: message
        });
        store.commit("statechange", {
          ways: "taskNewsNum",
          data: tnn
        });
        // 添加基座角标
        $ofront.require("isDot")({ taskDot: true });
      }
    } else {
      console.log("接受服务消息：" + ev.data);
    }
    //收到服务器信息，心跳重置
    heartsNum = 3;
    reset();
  };

  socket.onerror = a => {
    console.log("websocket服务出错了---onerror", a);
  };

  socket.onclose = e => {
    if (e.code != 1000) {
      console.log("websocket服务关闭了---onclose, code != 1000 情况", e);
      reconnet(wsUrl);
    } else {
      console.log("websocket服务关闭了---onclose", e);
    }
  };
};

let reconnet = url => {
  //重新连接websock函数
  if (lockReconnet) return false;
  isReconnet = true;
  lockReconnet = true;
  setTimeout(() => {
    createSocket(url);
    lockReconnet = false;
  }, 2000);
};

let start = () => {
  //开启心跳
  timeoutObj && clearTimeout(timeoutObj);
  serverTimeoutObj && clearTimeout(serverTimeoutObj);
  timeoutObj = setTimeout(function() {
    //这里发送一个心跳，后端收到后，返回一个心跳消息，
    if (socket.readyState == 1) {
      //如果连接正常
      socket.send("ping");
    } else {
      //否则重连
      reconnet();
    }
    serverTimeoutObj = setTimeout(function() {
      console.log("第" + (3 - heartsNum) + "次循环心跳检测");
      heartsNum--;
      start();
      if (heartsNum == 0) {
        console.log("超时关闭");
        //超时关闭
        socket.close();
        serverTimeoutObj && clearTimeout(serverTimeoutObj);
        timeoutObj && clearTimeout(timeoutObj);
        store.commit("statechange", {
          ways: "taskError",
          data: { reason: "通讯服务连接超时" }
        });
      }
    }, timeout);
  }, timeout);
};

let reset = () => {
  //重置心跳
  //清除时间
  clearTimeout(timeoutObj);
  clearTimeout(serverTimeoutObj);
  //重启心跳
  start();
};

// 等待获取token
let createSocketWait = setInterval(() => {
  if (store.getters.get_token) {
    wsUrl = process.env.VUE_APP_webSocket + store.getters.get_token;
    createSocket(wsUrl);
    clearInterval(createSocketWait);
  }
}, 100);
