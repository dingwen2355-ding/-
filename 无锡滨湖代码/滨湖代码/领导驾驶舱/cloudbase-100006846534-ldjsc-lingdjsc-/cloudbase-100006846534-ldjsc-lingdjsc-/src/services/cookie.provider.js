'use strict'
const cookieProvider = {}

/* @usage : 同一域名下有个数（50-53 个）大小（4095-4097 字节）限制
            cookieProvider.get('keyname')  */
cookieProvider.get = function (key) {
  if (document.cookie.length > 0) {
    var cookieBegin = document.cookie.indexOf(key + '=')
    if (cookieBegin !== -1) {
      cookieBegin = cookieBegin + key.length + 1
      let cookieEnd = document.cookie.indexOf(';', cookieBegin)
      if (cookieEnd === -1) cookieEnd = document.cookie.length
      return unescape(document.cookie.substring(cookieBegin, cookieEnd))
    }
  }
  return ''
}

cookieProvider.set = function (key, value, days = 365) {
  let expires = new Date()
  expires.setDate(expires.getDate() + days)
  document.cookie = key + '=' + escape(value) + ((days == null || days === 0) ? '' : ';expires=' + expires.toGMTString())
}

cookieProvider.delete = function (key) {
  var expires = new Date()
  expires.setTime(expires.getTime() - 1)
  var keyValue = this.get(key)
  if (keyValue != null) {
    document.cookie = key + '=' + keyValue + ';expires=' + expires.toGMTString()
  }
}

export default cookieProvider