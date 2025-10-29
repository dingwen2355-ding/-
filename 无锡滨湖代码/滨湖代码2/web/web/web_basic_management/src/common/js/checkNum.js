export function checkNum(rule, value, callback) { // 数字校验
  var reg = new RegExp('^[0-9]+([.]{1}[0-9]+){0,1}$')
  if (!(value === null || value === undefined || value === '')) {
    if (reg.test(value)) {
      callback()
    } else {
      callback(new Error('请输入为数字'))
    }
  } else {
    callback()
  }
}
