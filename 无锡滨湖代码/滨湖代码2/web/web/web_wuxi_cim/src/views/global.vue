<script>
import TaskQueue from "@/utils/queue";
import MultiPartUpload from "@/utils/upload";
import ElementUI from "element-ui";
import { mapGetters } from "vuex";

const $ = require("jquery");
let runningQueue = new TaskQueue();
let stopQueue = new TaskQueue();
runningQueue.executeFun = function (task, rowid) {
  task.upload(rowid);
};
runningQueue.stopFun = function (task, running) {
  task._isrunning = running;
};
let panal = null;

function setCreateUploadPanel(arg) {
  panal = arg;
}

function createUploadPanel() {
  if (panal) panal();
}
let upToken = "";
let pri = '';

/* 创建上传 task, 启动任务
 * @file: 文件
 * @type: 文件类型
 * @attribute: 场景参数
 * @uploadtoken: 上传 token
 */
function addFile(file, type, attribute, uploadtoken, urls = null) {
  return new Promise((resolve, reject) => {
    upToken = uploadtoken;
    if (typeof attribute !== 'string') pri = attribute.pri;
    if (file) {
      const fileUp = new MultiPartUpload();
      let attributeObj, fileSuffix, fileRename;
      if (urls) {
        fileUp.url = urls
        attributeObj = typeof attribute == 'string' ? JSON.parse(attribute) : attribute;
        fileSuffix = attributeObj.suffix;
        fileRename = attributeObj.name + "." + fileSuffix
      }
      else {
        fileUp.url = [
          `${cim_server}service/uplaod/shp/process`,
          `${cim_server}service/server/upload`,
        ];
      }

      if (typeof attribute == 'string') {
        fileUp.param = [
          { type: type },
          {
            type: type,
            attribute: attribute,
          },
        ];
      } else {

        fileUp.param = [
          { type: type, size: attribute.raw.size, sdid: attribute.sdid, suffix: fileSuffix },
          {
            type: type,
            name: attribute.name,
            description: attribute.description,
            tags: attribute.tags,
            sdid: attribute.sdid,
            pri: attribute.pri,
            size: attribute.raw.size,
            suffix: fileSuffix,
            sourcetype: attribute.sourcetype,
            characterset: attribute.characterset,
            sourceformat: attribute.sourceformat
          },
        ];
      }

      /*---文件任务运行配置---*/
      fileUp.filetype = type;
      fileUp.autoSpeed = true;
      fileUp.limit = 1048576;
      fileUp.minLimit = 1024;
      fileUp.waitTime = 10000;
      fileUp.maxFile = 1099511627776; // 生产环境每个文件最大1TB
      //fileUp.maxFile = 536870912;// 测试环境每个文件最大512MB
      fileUp.paramFun = getParam;
      // 上传完毕回调函数, up: 上传task对象本身  deving? 非promise
      fileUp.finishFun = function (up) {
        if (up._ispause) {
          delete up._ispause;
          resolve("pause");
        }
        if (up.isFinish()) {
          runningQueue.removeTask(up);
          createUploadPanel();
        } else if (up._isdelete) {
          deleteUploadFile(up);
          delete up._isdelete;
        }
        resolve();
      };

      // 配置上传任务的文件信息，且判断文件大小未超限
      if (fileUp.setFile(file, fileRename)) {
        let listRun = runningQueue.getQueue();
        let listStop = stopQueue.getQueue();
        let add = true;
        // 监测任务是否在run和stop队列中存在，否则未新增任务
        let runTarget = listRun.find(
          (it) => it.fileid == fileUp.fileid && it.fileid != ""
        );
        // console.log("runTarget", runTarget);
        let stopTarget = listStop.find(
          (it) => it.fileid == fileUp.fileid && it.fileid != ""
        );
        // console.log("stopTarget", stopTarget);
        add = !runTarget && !stopTarget;
        // console.log("add", add);
        if (add) {
          ElementUI.Message.success("文件正在上传中，请到上传列表查看！");
          runningQueue.addTask(fileUp);
          createUploadPanel();
          runningQueue.execute(0);
        }
      } else {
        let bean = formatDocSize(fileUp.maxFile);
        ElementUI.Message.error(
          `上传文件超出"${bean.size.toFixed(2)}${bean.unit}`
        );
      }
    } else ElementUI.Message.error("没有选择文件!");
  });
}

function addBimFile(file, attribute, uploadtoken) {
  return new Promise((resolve, reject) => {
    let token = this.$store.getters.token;
    upToken = uploadtoken;
    if (file) {
      const fileUp = new MultiPartUpload();
      fileUp.url = [
        `${cim_main}freedo/cim/oss/file/process?token=${token}`,
        // `${cim_main}freedo/cim/oss/file?token=${token}`,
        `${cim_main}freedo/cim/oss/file`,
      ];
      fileUp.param = [
        {},
        {
          attribute: attribute,
        },
      ];

      /*---文件任务运行配置---*/
      fileUp.autoSpeed = true;
      fileUp.limit = 1048576;
      fileUp.minLimit = 1024;
      fileUp.waitTime = 10000;
      fileUp.maxFile = 1099511627776; // 生产环境每个文件最大1TB
      //fileUp.maxFile = 536870912;// 测试环境每个文件最大512MB
      fileUp.paramFun = getParam;
      // 上传完毕回调函数, up: 上传task对象本身  deving? 非promise
      fileUp.finishFun = function (up) {
        if (up._ispause) {
          delete up._ispause;
          resolve("pause");
        }
        if (up.isFinish()) {
          runningQueue.removeTask(up);
          createUploadPanel();
        } else if (up._isdelete) {
          deleteUploadFile(up);
          delete up._isdelete;
        }
        resolve();
      };

      // 配置上传任务的文件信息，且判断文件大小未超限
      if (fileUp.setFile(file)) {
        let listRun = runningQueue.getQueue();
        let listStop = stopQueue.getQueue();
        let add = true;

        // 监测任务是否在run和stop队列中存在，否则未新增任务
        let runTarget = listRun.find(
          (it) => it.fileid == fileUp.fileid && it.fileid != ""
        );
        let stopTarget = listStop.find(
          (it) => it.fileid == fileUp.fileid && it.fileid != ""
        );
        add = !runTarget && !stopTarget;

        if (add) {
          ElementUI.Message.success("文件正在上传中，请到上传列表查看！");
          runningQueue.addTask(fileUp);
          createUploadPanel();
          runningQueue.execute(0);
        }
      } else {
        let bean = formatDocSize(fileUp.maxFile);
        ElementUI.Message.error(
          `上传文件超出"${bean.size.toFixed(2)}${bean.unit}`
        );
      }
    } else ElementUI.Message.error("没有选择文件!");
  });
}

// ---deving:未调整---
function addShpFile(file, type, uploadtoken, options) {
  return new Promise((resolve, reject) => {
    upToken = uploadtoken;
    if (file) {
      const fileUp = new MultiPartUpload();
      fileUp.url = [
        `${cim_server}service/server/process`,
        `${cim_server}service/server/uploadshp`,
      ];
      fileUp.param = [
        { type: type },
        {
          type: type,
          attribute: "",
          returnTheGeom:1,
          returnProp:1,
        },
      ];
      fileUp.filetype = type;
      fileUp.autoSpeed = true;
      fileUp.limit = 1048576;
      fileUp.minLimit = 1024;
      fileUp.waitTime = 10000;
      fileUp.maxFile = 1099511627776; // 生产环境每个文件最大1TB
      //fileUp.maxFile = 536870912;// 测试环境每个文件最大512MB
      fileUp.paramFun = getParam;
      if (options && options.returnProp) {
        fileUp.returnProp = options.returnProp;
      }
      if (options && options.returnTheGeom) {
        fileUp.returnTheGeom = options.returnTheGeom;
      }

      fileUp.finishFun = function (up) {
        if (up._ispause) {
          delete up._ispause;
          resolve("pause");
        }
        if (up.isFinish()) {
          runningQueue.removeTask(up);
          createUploadPanel();
        } else if (up._isdelete) {
          deleteUploadFile(up);
          delete up._isdelete;
        }
        resolve(up.message);
      };
      if (fileUp.setFile(file)) {
        let list0 = runningQueue.getQueue();
        let list1 = stopQueue.getQueue();
        let add = true;
        for (let i = 0; i < list0.length; i++) {
          if (
            list0[i].name == fileUp.name &&
            list0[i].fileSize == fileUp.fileSize
          ) {
            add = false;
            break;
          }
        }
        for (let i = 0; i < list1.length; i++) {
          if (
            list1[i].name == fileUp.name &&
            list1[i].fileSize == fileUp.fileSize
          ) {
            add = false;
            break;
          }
        }
        if (add) {
          ElementUI.Message({
            type: "success",
            message: "文件正在上传中",
          });
          runningQueue.addTask(fileUp);
          createUploadPanel();
          runningQueue.execute(0);
        }
      } else {
        let bean = formatDocSize(fileUp.maxFile);
        ElementUI.Message({
          type: "error",
          message: "上传文件超出" + bean.size.toFixed(2) + bean.unit,
        });
      }
    } else {
      ElementUI.Message({
        type: "error",
        message: "没有选择文件",
      });
    }
  });
}

function getParam() {
  let param = [
    { token: upToken }, // 这要get新token
    { token: upToken }, // 这要get新token
    { pri: pri },
  ];

  console.log("查看param-->", param);
  return param;
}

function start(row, isContinue = false) {
  return new Promise((resolve, reject) => {
    let task = stopQueue.removeTaskAt(row.fileid);
    console.log("start.task---", task);
    if (task) {
      if (task._ispause) task._ispause = false; // !暂停
      task.stype = 1;
      runningQueue.addTask(task);
      createUploadPanel();
      if (isContinue) runningQueue.execute(0, row.fileid || row.id);
      else runningQueue.execute(0);
      resolve();
    } else {
      ElementUI.Message({
        type: "error",
        message: "没有可开始上传的文件, 请重新选择文件上传！",
      });
      reject(false);
    }
  });
}

function stop(row) {
  return new Promise((resolve, reject) => {
    let task = runningQueue.removeTaskAt(row.fileid);
    console.log("stop.task---", task);
    if (task) {
      if (task._isrunning) {
        // if (task.running) {
        task._ispause = true; // 暂停
        task.stype = 10;
        task.stop();
      }
      delete task._isrunning;
      stopQueue.addTask(task);
      createUploadPanel();
      resolve();
    }
    // else {
    //   ElementUI.Message({
    //     type: "error",
    //     message: "没有可停止上传的文件",
    //   });
    // }
  });
}

function deleteRunFile(fileId) {
  return new Promise((resolve, reject) => {
    let task = runningQueue.removeTaskAt(fileId);
    if (task) {
      // 删除没有正在运行的，不走finish方法
      if (!task._isrunning) deleteUploadFile(task);
      else {
        task._isdelete = true; // 给任务标识删除
        task.stop(); // 停止任务
      }
      createUploadPanel();
      delete task._isrunning;
      resolve();
    }
    // else {
    //   ElementUI.Message({
    //     type: "error",
    //     message: "没有可删除的文件",
    //   });
    // }
  });
}

function deleteStopFile(fileId) {
  return new Promise((resolve, reject) => {
    let task = stopQueue.removeTaskAt(fileId);
    if (task) {
      createUploadPanel();
      deleteUploadFile(task);
      resolve();
    }
    // else {
    //   ElementUI.Message({
    //     type: "error",
    //     message: "没有可删除的文件",
    //   });
    // }
  });
}

function deleteUploadFile(up) {
  let token = JSON.parse(sessionStorage.getItem("vuex"));
  $.ajax({
    url: `${cim_server}service/server/delete`,
    type: "POST",
    data: {
      filename: up.name,
      size: up.fileSize,
      // 这要get新token
      token:
        token && token.updataToken ? token.updataToken.uploadtoken : upToken,
    },
    dataType: "json",
    success: function (responseText) {
      console.log("deleteUploadFile.success", responseText);
    },
    error: function (responseText) {
      console.log("deleteUploadFile.error", responseText);
    },
  });
}

export default {
  computed: {
    ...mapGetters(["token", "uploadtoken"]),
  },
  runningQueue,
  stopQueue,
  setCreateUploadPanel,
  createUploadPanel,
  addFile,
  addBimFile,
  addShpFile,
  getParam,
  start,
  stop,
  deleteRunFile,
  deleteStopFile,
  deleteUploadFile,
};
</script>
