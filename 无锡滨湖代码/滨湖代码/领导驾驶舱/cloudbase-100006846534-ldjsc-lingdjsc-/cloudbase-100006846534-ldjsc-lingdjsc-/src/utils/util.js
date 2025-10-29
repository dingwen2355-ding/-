export const formatSeconds = (value) => {
  let result = parseInt(value)
  let h = Math.floor(result / 3600) < 10 ? '0' + Math.floor(result / 3600) : Math.floor(result / 3600);
  let m = Math.floor((result / 60 % 60)) < 10 ? '0' + Math.floor((result / 60 % 60)) : Math.floor((result / 60 % 60));
  let s = Math.floor((result % 60)) < 10 ? '0' + Math.floor((result % 60)) : Math.floor((result % 60));
  let res = '';
  if (h !== '00') res += `${h}小时`;
  if (m !== '00') res += `${m}分`;
  res += `${s}秒`;
  return res;
}
/**
 * 时间格式转换
 * @param {*} time 需要转换的时间
 * @param {*} fmt 需要转换的格式 如 yyyy-MM-dd、yyyy-MM-dd HH:mm:ss
 * @returns 
 */
export const formatTime = (time, fmt) => {
  if (!time) return '';
  else {
    let date = new Date(time);
    let o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'H+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds(),
      'q+': Math.floor((date.getMonth() + 3) / 3),
      S: date.getMilliseconds(),
    };
    if (/(y+)/.test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        (date.getFullYear() + '').substr(4 - RegExp.$1.length)
      );
    for (let k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          RegExp.$1.length === 1
            ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length)
        );
      }
    }
    return fmt;
  }
}

export const bytesToSize = (bytes) => {
  if (bytes === 0) return '0 B';
  var k = 1024,
    sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
    i = Math.floor(Math.log(bytes) / Math.log(k));
  return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

/**
 * 动态加载JS
 * @param {*} src 
 * @param {*} id 
 * @returns 
 */
export const loadJs = (src, id) => {
  return new Promise((resolve, reject) => {
    let elem = document.getElementById(id)
    if (!!elem) {
      resolve();
    } else {
      let script = document.createElement("script");
      script.id = id;
      script.type = "text/javascript";
      script.onload = () => {
        resolve();
      };
      script.onerror = () => {
        reject();
      };
      script.src = src;
      document.getElementsByTagName("body")[0].appendChild(script);
    }
  });
}
/**
 *  数组降维
 * @param {*} arr  原始数据
 * @param {*} tempArr   目标数组
 * @param {*} AN  child or children
 * @param {*} saveChild  是否将child 或children 保存到数组中
 * @returns 
 */
export const deepTree = (arr, tempArr, AN = "children", saveChild = false) => {
  const result = tempArr || []
  arr.forEach(item => {
    if (Object.prototype.toString.call(item[AN]) === "[object Array]") {
      const { childrens,children, child, ...obj } = item
      if (saveChild) {
        result.push(item)
      } else {
        result.push(obj)
      }
      if (AN === "children") {
        deepTree(children, result, AN, saveChild)
      } else if (AN === "child") {
        deepTree(child, result, AN, saveChild)
      }else if (AN === "childrens") {
        deepTree(childrens, result, AN, saveChild)
      }
      return
    }
    result.push(item)
  })
  return result
}
/**
 * 树形结构转数组
 * @param {*} arr 
 * @param {*} child 
 */
export const tree2Array = (arr, child = "children", resultArr = []) => {
  // let result = [];
  if (Object.prototype.toString.call(arr) === "[object Array]") {
    arr.forEach(item => {
      if (Object.prototype.toString.call(item[child]) === "[object Array]") {
        tree2Array(item[child], child, resultArr);
      }
      resultArr.push(item);
    })
  } else {
    resultArr.push(arr);
    if (Object.prototype.toString.call(arr[child]) === "[object Array]") {
      tree2Array(arr[child], child, resultArr);
    }
  }
}

/**
 * 树结构 - 广度遍历
 * @param {*} node  tree => list
 * @param {*} key 默认为 'children' 
 * @returns 
 */
export const breadthData = (node, key = 'children') => {
  if (node instanceof Array) {
    let nodeArr = []
    for (let i = 0; i < node.length; i++) {
      nodeArr = nodeArr.concat(breadthData(node[i], key))
    }
    return nodeArr
  }
  let nodes = []
  if (node) {
    let queue = []
    queue.unshift(node)
    while (queue.length != 0) {
      let item = queue.shift()
      nodes.push(item)
      let childList = item[key] || []
      for (let index = 0; index < childList.length; index++) {
        queue.push(childList[index])
      }
    }
  }
  return nodes
}

/**
* 树结构 - 深度遍历
* @param {*} node  
* @param {*} key 默认为 'children' 
* @returns 
*/
export const deepData = (node, key = 'children') => {
  if (node instanceof Array) {
    let nodeArr = []
    for (let i = 0; i < node.length; i++) {
      nodeArr = nodeArr.concat(deepData(node[i], key))
    }
    return nodeArr
  }
  let nodes = []
  if (node) {
    let stack = []
    stack.push(node)
    while (stack.length != 0) {
      let item = stack.pop()
      nodes.push(item)
      let childList = item[key] || []
      for (let index = 0; index < childList.length; index++) {
        stack.push(childList[index])
      }
    }
  }
  return nodes
}

/**
 * 下载文件
 * @param {*} url  `${cim_main}fusion/downloadExcel?tableName=${this.tableName}&token=${this.$store.state.login.account.token}`
 * @param {*} fileName  测试文件.xls
 * @param {*} type  application/pdf
 */
export const downloadFile = (url, fileName) => {
  var oReq = new XMLHttpRequest();
  oReq.open("GET", url, true);
  oReq.responseType = "blob";
  oReq.onload = function () {
    let blob = new Blob([oReq.response]);
    let link = document.createElement("a");
    let url = URL.createObjectURL(blob);
    link.download = fileName;
    link.href = url;
    link.click();
    URL.revokeObjectURL(url);
  };
  oReq.send();
}

/**
 * 判断是否是数字
 * @param {} value 
 */
export const isNumeric = (value) => {
  return !isNaN(parseFloat(value)) && isFinite(value);
}

/**
 * 限制命名：字母、数字、中文，且不能以数字开头
 */
export const attrNameRegExp = (str) => {
  const reg = /^(?![0-9])[a-zA-Z0-9\u4e00-\u9fa5]+$/
  return reg.test(str);
}

/**
 * 限制命名：字母、数字、下划线，且不能以数字开头
 */
export const attrNameRegExp2 = (str) => {
  const reg = /^(?![0-9])[a-zA-Z0-9_]+$/
  return reg.test(str);
}
