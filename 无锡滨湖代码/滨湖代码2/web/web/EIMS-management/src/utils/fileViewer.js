var context = {
  // 查看器的源，当前示例为在线，本地测试请改为 http://localhost:8900
  // origin: 'http://' + '172.16.7.11' + ':8087',
  origin: 'http://' + location.hostname + ':8087',
  // 目标frame
  frame: null,
  // 文件url
  url: ''
}
function appendFrame(src) {
  if (context.frame) {
    document.body.removeChild(context.frame)
  }
  // 构建frame
  var frame = (context.frame = document.createElement('iframe'))
  frame.src = src
  frame.className = 'file-frame'
  return document.body.appendChild(frame)
}

function loadFromUrl(url) {
  // 拼接iframe请求url
  var src = context.origin + '?url=' + encodeURIComponent(url)
  // 拼接frame
  appendFrame(src)
}

export default loadFromUrl
