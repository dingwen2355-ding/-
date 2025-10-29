TaskQueue = function () {
  var _this = this;
  this.running = false;
  this.executeFun = null;
  this.stopFun = null;
  var queue = [];
  var runningIndex = -1;
  this.getQueue = function () {
    return queue;
  };
  this.addTask = function (obj) {
    queue.push(obj);
    return this;
  };
  this.removeTask = function (obj, startIndex) {
    if (startIndex == null || startIndex == undefined) startIndex = 0;
    let obj1 = null;
    let index = -2;
    let ls = [];
    for (let i = 0; i < queue.length; i++) {
      if (queue[i] != obj) ls.push(queue[i]);
      else {
        obj1 = obj;
        index = i;
      }
    }
    queue = ls;
    if (obj1 && runningIndex == index) {
      if (this.stopFun) this.stopFun(obj1, true);
      this.running = false;
      this.execute(startIndex);
    } else if (obj1 && this.stopFun) this.stopFun(obj1, false);
    return obj1;
  };
  this.removeTaskAt = function (fileid, startIndex = 0) {
    let index = queue.findIndex(it => it.fileid == fileid);
    let obj = null;
    if (index >= 0 && index < queue.length) {
      obj = this.removeTask(queue[index], startIndex);
    }
    return obj;
  };
  this.execute = function (index, rowid) {
    // let taskTarget = queue[index];
    // if (taskTarget && this.executeFun && (taskTarget._ispause || this.running) && index >= 0 && index < queue.length) {
    if (queue.length && this.executeFun && this.running == false && index >= 0 && index < queue.length) {
      this.running = true;
      this.executeFun(queue[index], rowid);
      runningIndex = index;
    }
  };
  this.getRunIndex = function () {
    return runningIndex;
  };
};

module.exports = TaskQueue