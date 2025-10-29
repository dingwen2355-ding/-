$(function () {
    $(".box").click(function () {
        console.log($(this).attr("code"))
        if ($(this).attr("code") == "ditu") {
            window.open("http://pro.jchgis.cn:9911/overview-web?token=" +
                window.sessionStorage.getItem("token") + "&jobUrl=" + window.localStorage.getItem("jobServerUrl") + "&pageId=164992217313161498")
        } else {
            window.location.href = window.localStorage.getItem("jobServerUrl") + "/html/main.html"
        }
    })
})

