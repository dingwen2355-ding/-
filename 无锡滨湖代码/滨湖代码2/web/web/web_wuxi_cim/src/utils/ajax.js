export const Ajax_get = (url, callback) => {
    const ajax = new XMLHttpRequest();
    ajax.open('get', url);
    ajax.send();
    ajax.onreadystatechange = function() {
        if (ajax.readyState == 4 && ajax.status == 200) {
            if (callback) callback(ajax.responseText)
            else console.log(ajax.responseText);
        }
    }
}


const $ = require("jquery");
export const Ajax_upload = (url, data, callback) => {
    
    let { fileList, type } = data;
    const formData = new FormData();
    formData.append("type", type);
    fileList.forEach((item) => {
        formData.append("fileInfo[]", item);
    })
    $.ajax({
        url: url,
        type: "POST",
        data: formData,
        contentType: false, //必须false才会自动加上正确的Content-Type
        processData: false, //必须false才会避开jQuery对formdata的默认处理
        success: function(data, status) {
            if (callback) {
                window[callback](data)
            } else console.log(data);
        }
    });
}