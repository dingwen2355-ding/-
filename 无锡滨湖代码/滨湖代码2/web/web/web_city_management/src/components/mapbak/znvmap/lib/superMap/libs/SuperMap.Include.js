(function () {
  var r = new RegExp('(^|(.*?\\/))(SuperMap.Include\.js)(\\?|$)')
  var s = document.getElementsByTagName('script')
  var src; var m; var baseurl = ''
  for (var i = 0, len = s.length; i < len; i++) {
    src = s[i].getAttribute('src')
    if (src) {
      var m = src.match(r)
      if (m) {
        baseurl = m[1]
        break
      }
    }
  }
  // 加载类库资源文件
  function loadSMLibs () {
    /*createScripts([baseurl + 'SuperMap-8.1.1-14426.js'], function () {
      loadLocalization()
    })*/
    // createCss([baseurl + '../theme/default/style.css', baseurl + '../theme/default/google.css'])
  }
  // 引入汉化资源文件
  function loadLocalization () {
    var userLang
    // 针对不通浏览器做语言浏览器做判断
    if (navigator.userLanguage) {
      // 针对IE浏览器
      userLang = navigator.userLanguage
    } else if (navigator.languages) {
      // 针对Chrome
      userLang = navigator.languages[0]
    } else {
      // 其他
      userLang = navigator.language
    }
    if (userLang.indexOf('zh') > -1) {
      createScripts([baseurl + 'Lang/zh-CN.js'])
    } else {
      createScripts([baseurl + 'Lang/en.js'])
    }
  }

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

  function loadStyles (url) {
    var link = document.createElement('link')
    link.rel = 'stylesheet'
    link.type = 'text/css'
    link.href = url
    const head = document.getElementsByTagName('head')[0]
    head.appendChild(link)
  }

  function createScripts (urls, fn) {
    for (let i = 0; i < urls.length; i++) {
      let url = urls[i]
      loadJS(url, fn)
    }
  }

  function createCss (urls, fn) {
    for (let i = 0; i < urls.length; i++) {
      let url = urls[i]
      loadStyles(url, fn)
    }
  }
  loadSMLibs()
})()
