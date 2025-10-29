/*
 * @Descripttion: ofrontsdk，利用postmesage方式，解决和基座之间的跨域通信
 * @version:
 * @Author: 飞飞同学
 * @Date: 2021-05-25 17:04:06
 * @LastEditors: GM
 * @LastEditTime: 2021-05-26 14:47:07
 */
export default class OFront {
  constructor() {
    this.isIframe = false;
    if (window.frames.length != parent.frames.length) {
      this.isIframe = true;
      this.watchMessage();
    }
    this.callbacks = {};
    this.listenBacks = {};
    this.messageData = undefined;
  }

  watchMessage() {
    window.addEventListener("message", e => {
      try {
        let data = JSON.parse(e.data);
        if (data.listenName && this.listenBacks[data.listenName]) {
          for (let cbid in this.listenBacks[data.listenName]) {
            this.listenBacks[data.listenName][cbid](data.res);
          }
        }
        if (data.cbid && this.callbacks[data.cbid]) {
          this.callbacks[data.cbid](data.params);
        }
        if (data && data.cmd) {
          this.messageData = data;
          // this.receive(data);
        }
      } catch (error) {
        error;
      }
    });
  }

  require(name) {
    return x => {
      return new Promise((resolve, reject) => {
        const cbid = Date.now() + "" + Math.round(Math.random() * 100000);
        this.callbacks[cbid] = params => {
          if (params.reslove) {
            resolve(params.res);
          } else {
            reject(params.res);
          }
        };
        window.parent.postMessage(
          JSON.stringify({
            method: name,
            cbid: cbid,
            x: x
          }),
          "*"
        );
      });
    };
  }

  receive(data) {
    switch (data.cmd) {
      case "handleDot":
        console.log(data);
        break;
      default:
        break;
    }
  }

  /**
   * 新增监听事件
   */
  addEventListener(name, fun) {
    if (!name) {
      return Promise.reject("请注册有效的监听事件");
    }
    const getEventNames = $ofront.require("getEventNames");
    getEventNames().then(names => {
      const flag = names.filter(item => item.name === name);
      if (!flag) {
        reject("请注册有效的监听事件");
      } else {
        const cbid = Date.now() + "" + Math.round(Math.random() * 100000);
        if (!this.listenBacks[name]) {
          this.listenBacks[name] = {};
        }
        this.listenBacks[name][cbid] = fun;
      }
    });
  }
}
if (!window.$ofront) {
  window.$ofront = new OFront();
}
