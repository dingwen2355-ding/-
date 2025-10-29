// let loadJsCss = {}

function loadJS (url, callback) {
  let script = document.createElement('script')
  let fn = callback || function () {
  }
  script.type = 'text/javascript'
  // IE
  if (script.readyState) {
    script.onreadystatechange = function () {
      if (script.readyState === 'loaded' || script.readyState === 'complete') {
        script.onreadystatechange = null
        fn()
      }
    }
  } else {
    // 其他浏览器
    script.onload = function () {
      fn()
    }
  }
  script.src = url
  document.getElementsByTagName('head')[0].appendChild(script)
}

function loadCss (url, callback) {
  let css = document.createElement('link')
  let fn = callback || function () {
  }
  css.rel = 'stylesheet'
  // IE
  if (css.readyState) {
    css.onreadystatechange = function () {
      if (css.readyState === 'loaded' || css.readyState === 'complete') {
        css.onreadystatechange = null
        fn()
      }
    }
  } else {
    // 其他浏览器
    css.onload = function () {
      fn()
    }
  }
  css.href = url
  document.getElementsByTagName('head')[0].appendChild(css)
}

function objectExtend () {
  var options
  var name
  var src
  var copy
  var copyIsArray
  var clone
  var target = arguments[0] || {} // 目标对象
  var i = 1
  var length = arguments.length
  var deep = false
  // 处理深度拷贝情况（第一个参数是boolean类型且为true）
  if (typeof target === 'boolean') {
    deep = target
    target = arguments[1] || {}
    // 跳过第一个参数（是否深度拷贝）和第二个参数（目标对象）
    i = 2
  }
  // 如果目标不是对象或函数，则初始化为空对象
  if (typeof target !== 'object' && !jQuery.isFunction(target)) {
    target = {}
  }
  // 如果只指定了一个参数，则使用jQuery自身作为目标对象
  if (length === i) {
    target = this
    --i
  }
  for (; i < length; i++) {
    // Only deal with non-null/undefined values
    if ((options = arguments[i]) != null) {
      // Extend the base object
      for (name in options) {
        src = target[name]
        copy = options[name]
        // Prevent never-ending loop
        if (target === copy) {
          continue
        }
        // 如果对象中包含了数组或者其他对象，则使用递归进行拷贝
        if (deep && copy && (jQuery.isPlainObject(copy) || (copyIsArray = jQuery.isArray(copy)))) {
          // 处理数组
          if (copyIsArray) {
            copyIsArray = false
            // 如果目标对象不存在该数组，则创建一个空数组；
            clone = src && jQuery.isArray(src) ? src : []
          } else {
            clone = src && jQuery.isPlainObject(src) ? src : {}
          }
          // 从不改变原始对象，只做拷贝
          target[name] = jQuery.extend(deep, clone, copy)
          // 不拷贝undefined值
        } else if (copy !== undefined) {
          target[name] = copy
        }
      }
    }
  }
  // 返回已经被修改的对象
  return target
}

/*
 *动态加载script
 */
function createScripts(urls, fn) {
  for (let i = 0; i < urls.length; i++) {
    let url = urls[i]
    loadJS(url, fn)
  }
}

/*
 *动态加载css
 */
function createCss(urls, fn) {
  for (let i = 0; i < urls.length; i++) {
    let url = urls[i]
    loadCss(url, fn)
  }
}

export {
  createScripts,
  createCss,
  objectExtend
}
