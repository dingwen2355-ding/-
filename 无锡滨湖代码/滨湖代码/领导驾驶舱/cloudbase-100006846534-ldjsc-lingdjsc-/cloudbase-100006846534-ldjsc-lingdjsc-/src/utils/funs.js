/**
 * 函数防抖(debounce) 
 * --> 在事件被触发n秒后再执行回调，如果在这n秒内又被触发，则重新计时。
 * @param Function fn 延时调用函数
 * @param Number delay 延迟多长时间
 * @param Boolean immediate 
 * @return Function 延迟执行的方法
 */
function debounce(func, delay = 300, immediate = false) {
    let timer = null;
    return function() {
        if (timer) {
            clearTimeout(timer);
        }
        if (immediate && !timer) {
            func.apply(this, arguments);
        }
        timer = setTimeout(() => {
            func.apply(this, arguments);
        }, delay);
    };
}

function fileSizsTransform(bytes) {
    // if (isNaN(size)) return '0MB';
    // return `${Math.round(Number(size) / 1024 / 1024 * 100) / 100}MB`;
    if (bytes === 0) return '0 B';
    var k = 1024,
      sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
      i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

/**
 * 函数节流(throttle) 
 * --> 规定在一个单位时间内，只能触发一次函数。如果这个单位时间内触发多次函数，只有一次生效。
 * @param Function fn 延时调用函数
 * @param Number delay 延迟多长时间
 * @return Function 延迟执行的方法
 */
function throttle(fn, delay = 300) {
    let timer = null;
    return function() {
        clearTimeout(timer);
        timer = setTimeout(function() {
            fn();
        }, delay);
    }
};

/**
 * 获取客户端IP地址
 */
function getCusIpAdress() {
    var data;
    let xmlHttpRequest;
    if (window.ActiveXObject) {
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    } else if (window.XMLHttpRequest) {
        xmlHttpRequest = new XMLHttpRequest();
    }
    xmlHttpRequest.onreadystatechange = function() {
        if (xmlHttpRequest.readyState == 4) {
            if (xmlHttpRequest.status == 200) data = xmlHttpRequest.responseText;
            else console.error("error:HTTP状态码为:" + xmlHttpRequest.status)
        }
    };
    xmlHttpRequest.open("get", IpAdressUrl, false);
    xmlHttpRequest.send(null);
    var datalist = data.split("\n");
    var patt = [/[0-9]+.[0-9]+.[0-9]+.[0-9]+/, /来自/, []];
    for (var i in datalist) {
        if (patt[0].test(datalist[i]) && patt[1].test(datalist[i])) {
            patt[2].push(patt[0].exec(datalist[i])[0]);
            patt[2].push(datalist[i].substr(patt[1].exec(datalist[i]).index + 3));
        }
    }
    console.log('Got IP! :', patt[2]);
    return patt[2];
}


export { debounce, throttle, fileSizsTransform, getCusIpAdress }