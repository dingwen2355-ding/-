$(function () {
    /* 调用login页面，携带参数redirectUrl */

    $.ajax({
        url: "./config.json",
        dataType: "json",
        success: function (res) {
            var result = {}
            if (window.location.href.indexOf("localhost") != -1) {
                result = res.dev
            } else if (window.location.href.indexOf("wxgis.cn") != -1) {
                result = res.test
            } else {
                result = res.prod
            }
            window.result = result;

            window.localStorage.removeItem("redirectUrl");
            window.localStorage.setItem("redirectUrl", result.jobUrl + "/html/nav.html");

            window.localStorage.removeItem("serverUrl");
            window.localStorage.setItem("serverUrl", result.serverUrl);

            window.localStorage.removeItem("jobServerUrl");
            window.localStorage.setItem("jobServerUrl", result.jobUrl);
        }
    })

})

function sysLogin() {
    let redirectUrl = window.localStorage.getItem("redirectUrl");
    $.ajax({
        url: '/auth/jc/getAccessToken',
        contentType: 'application/json;charset=UTF-8',
        type: 'post',
        data: loginBody(redirectUrl),
        dataType: 'json',
        success: function (res) {
            if (res.code == "200") {
                let jwt_token = res.data.access_token;
                window.sessionStorage.setItem("token", jwt_token)
                window.location.href = decodeURIComponent(redirectUrl);
            } else {
                alert(res.msg)
            }
        }
    })
}

function loginBody(redirectUrl) {
    let obj = new Object();
    obj.account = $("#account").val()
    obj.password = encrypt($("#password").val());
    obj.serverUrl = window.localStorage.getItem("serverUrl");
    obj.jobServerUrl = window.localStorage.getItem("jobServerUrl");
    obj.channel = 0;
    return JSON.stringify(obj);
}

function encrypt(text) {
    var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpsfRSFf22HSHeoooXzfdEIDCzLPBq3t+TvcsI/vQ/DoLgpdqGilN5z+YRmchayNKPVUVJYpb4zZQjFLxNfICe6G7tjOlLufrITSMaOXweOQc+UTJ8DFteFhbKT/iwj/aYNgDG/XPbxUB4kJYWcUbniOr5wh55/3Gtnz6k1niAgwIDAQAB";
    var crypt = new JSEncrypt();
    crypt.setKey(publicKey);
    return crypt.encrypt(text);
}
