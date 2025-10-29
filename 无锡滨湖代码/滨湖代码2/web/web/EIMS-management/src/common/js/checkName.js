export function checkName(rule, value, callback) { // 名称特殊字符校验
  let specificReg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_-]");
  if (!(value === null || value === undefined || value === "")) {
    if (specificReg.test(value)) {
      callback(new Error("不能包含特殊字符"));
    } else {
      callback();
    }
  }
}
