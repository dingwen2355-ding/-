const $ = require('jquery');
// 文件上传 task 类
MultiPartUpload = function () {
  let _this = this;
  let file = null; // 文件对象
  let state = true; // 状态？
  let finish = true; // 完成
  let timer = null; // 上传进度定时器
  let startTime = 0;
  let finishSize = 0;
  let senderror = false; // 上传错误
  let formData = null;
  let timestamp = 0;
  let fileid = "";

  this.url = null; // 请求的url数组: ["0、获取文件已上传的大小","1、上传地址"] 
  this.size = 0; // 文件大小
  this.start = 0; // 上传起始位置
  this.end = 0; // 上传终止位置
  this.limit = 1024 * 1024; // 每次上传的文件块大小
  this.minLimit = 1024 * 100; // 最小每次上传的文件块大小
  this.maxLimit = 1024 * 1024 * 100; // 最大每次上传的文件块大小
  this.maxFile = 524288000; // 允许上传的最大文件大小，默认200mb
  this.param = [{}, {}]; // 自定义参数
  this.autoSpeed = false; // 是否自动计算每次上传的文件块大小
  this.successFun = null; // 上传成功的回调函数
  this.finishFun = null; // 上传完毕的回调函数
  this.executeTime = 0; // 每次上传的执行时间（毫秒）
  this.waitTime = 1000; // 每次上传的等待时间，如果不在这个时间的正负10%范围内，系统自动计算上传的文件块大小
  this.running = false; // 运行

  this.filename = ''; // 文件名
  this.fileRename = "";
  this.fileid = ''; // 文件 id
  this.stype = 1; // 文件上传状态 1: 正在上传 10: 暂停
  this.returnProp = 0;
  this.returnTheGeom = 0;

  function _init() {
    _this.start = 0;
    _this.end = _this.limit;
  }
  this.getStartTime = function () {
    return startTime;
  }
  this.getFinishSize = function () {
    return finishSize;
  }

  /* 设置上传文件信息
   * @f: 文件对象 (包含文件名，大小，文件数据等信息)
   * @return: 上传文件大小是否超限
   */
  this.setFile = function (f, fileRename = "") {
    file = f;
    this.fileRename = fileRename;
    this.filename = this.fileRename ? this.fileRename : file.name;
    this.size = file.size;
    this.stype = 1;
    return !(this.maxFile < this.size);
  };
  this.isFinish = function () {
    return this.size <= this.end;
  };
  this.isStop = function () {
    return finish;
  };
  function getParam() {
    if (_this.paramFun) return _this.paramFun();
    else return null;
  }

  /*---上传进度---*/
  this.upload = function (rowid) {
    this.running = true;
    startTime = Date.now();
    finishSize = 0;
    let param = getParam();
    if (param && param[0]) {
      for (let key in param[0]) {
        this.param[0][key] = param[0][key];
      }
    }
    if (file && finish) {
      finish = false;
      _init();
      let data = {
        filename: this.fileRename ? this.fileRename : file.name,
        size: this.size,
      };
      if (rowid) data.fileid = rowid; // 续传 id

      if (this.param[0]) {
        for (let key in _this.param[0]) {
          data[key] = this.param[0][key];
        }
      }
      $.ajax({
        url: this.url[0],
        type: 'POST',
        data: data,
        dataType: "json",
        success: function (responseText) {
          let res = responseText.body.message;
          if (typeof res == 'object') {
            _this.fileid = res.fileId;
            _this.start = parseInt(res.index);
          } else _this.start = parseInt(res);

          if (_this.start >= _this.size) _this.start = _this.size - 1;

          if (_this.start < _this.size) {
            _upload();
            timer = window.setInterval(function () {
              _upload();
            }, 100);
          } else {
            finish = true;
            if (_this.finishFun) _this.finishFun(_this);
          }
        },
        error: function (responseText) {
          finish = true;
        }
      });
    }
  };
  this.stop = function () {
    finish = true;
  };

  /*---上传文件---*/
  function _upload() {
    if (finish && state) {
      this.running = false;
      window.clearTimeout(timer);
      timer = null;
      if (_this.finishFun) _this.finishFun(_this);
    } else if (state) {
      state = false;
      let param = getParam();
      if (param && param[1]) {
        for (let key in param[1]) {
          _this.param[1][key] = param[1][key];
        }
      }
      if (!senderror) {
        if (_this.limit > _this.maxLimit) _this.limit = _this.maxLimit;
        if (_this.limit < _this.minLimit) _this.limit = _this.minLimit;

        _this.end = Math.min(_this.start + _this.limit, _this.size);
        formData = new FormData();
        if (_this.start == 0 && _this.end == _this.size) formData.append('file', file);
        else formData.append('file', file.slice(_this.start, _this.end));
        formData.append('filename', _this.fileRename ? _this.fileRename : file.name);
        formData.append('start', _this.start);
        formData.append('end', _this.end);
        formData.append('size', _this.size);
        formData.append("fileid", fileid || _this.fileid);
        if (_this.param[1]) {
          for (let key in _this.param[1]) {
            formData.append(key, _this.param[1][key]);
          }
        }
      } else {
        if (_this.param[1]) {
          for (let key in _this.param[1]) {
            formData.set(key, _this.param[1][key]);
          }
        }
      }
      timestamp = Date.now();
      if (_this.returnProp) formData.append('returnProp', _this.returnProp);// 控制属性和type返回 1: 返回数据 0:返回[]
      if (_this.returnTheGeom) formData.append('returnTheGeom', _this.returnTheGeom);// 控制属性和type返回 1: 返回数据 0:返回[]
      let type = formData.get("type")
      if (type == "shp") formData.set('type', 21) //修正 type 参数
      $.ajax({
        url: _this.url[1],
        type: 'POST',
        data: formData,
        dataType: "json",
        processData: false,
        contentType: false,
        success: function (responseText) {
          if (typeof responseText.body.message == 'object' || responseText.body.message == '1') {
            senderror = false;
            finishSize += _this.limit;
            _this.message = responseText.body.message;
            console.log("res.responseText", responseText.body)
            _this.executeTime = Date.now() - timestamp;
            if (_this.successFun) _this.successFun(_this);
            if (_this.autoSpeed) {
              if (_this.executeTime < _this.waitTime * 0.9 || _this.executeTime > _this.waitTime * 1.1) {
                _this.limit = parseInt(_this.limit * _this.waitTime / _this.executeTime);
              }
            }
            if (_this.size <= _this.end) finish = true;
            else _this.start = _this.end;
          } else {
            console.log("err.responseText", responseText);
            senderror = true
          };
          state = true;
        },
        error: function (responseText) {
          state = true;
        }
      });
    } else {
      //console.log('没空');
    }
  }
};
module.exports = MultiPartUpload


