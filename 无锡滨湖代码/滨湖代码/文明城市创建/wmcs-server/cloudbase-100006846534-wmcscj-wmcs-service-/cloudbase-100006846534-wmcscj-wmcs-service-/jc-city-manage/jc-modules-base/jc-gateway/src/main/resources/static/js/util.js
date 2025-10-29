
function getUrlHost() {
    let curWwwPath=window.document.location.href;
    let pathName=window.document.location.pathname;
    let pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8086
    return curWwwPath.substring(0,pos);
}

function getUrlParam(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    let r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) {
        if (unescape(r[2]).length > 0) {
            return unescape(r[2]);
        }
        return ''
    }
    return '' //返回参数值
}