export function checkGPSXY(rule, value, callback) { // 经纬度校验
  var reg = new RegExp('^-?(((1?[0-7]?[0-9]?)|([0-9]?[0-9]?))(([.][0-9]{1,9})?)|180(([.][0]{1,9})?))$'); // 范围：-180.000000000~180.000000000;
  if (!(value === null || value === undefined || value === "")) {
    if (reg.test(value)) {
      callback();
    } else {
      callback(new Error("范围：-180.000000000~180.000000000"));
    }
  } else {
    callback();
  }
}
