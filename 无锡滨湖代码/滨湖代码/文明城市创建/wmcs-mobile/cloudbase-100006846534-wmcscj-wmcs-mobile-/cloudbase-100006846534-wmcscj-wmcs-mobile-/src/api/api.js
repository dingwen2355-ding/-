import request from "./request"
import { Base64 } from 'js-base64'
// const base_url = '/api/prod-api'
// window.upload_base_url = '/api/prod-api/file/upload'
const base_url = 'http://2.21.137.224:8080/prod-api'
window.upload_base_url = 'http://2.21.137.224:8080/prod-api/file/upload'
// const base_url = 'http://192.168.1.7:9000'
// window.upload_base_url = 'http://106.14.27.198:9900/file/upload'
// const base_url = 'http://gyhl.wellscene.cn:20000/prod-api'
// window.upload_base_url = 'http://106.14.27.198:9900/file/upload'
export default {
    GetDict() {
        return request(base_url + "/system/common/dict", "GET")
    },
    // 获取openid登录
    Login(params) {
        params.password = Base64.encode(params['password'])
        return request(base_url + "/system/login", "POST", params)
    },
    Auth(params) {
        return request(base_url + "/system/login/wechat", "GET", params)
    },
    jianquan(params) {
        return request(base_url + "/system/login/signature", "GET", params)
    },
    getRouters(params) {
        return request(base_url + "/system/menu/getRouters", "GET", params)
    },
    // 获取用户信息
    GetUserInfo() {
        return request(base_url + "/system/getInfo", "GET")
    },
    // 更新用户信息
    UpdateUserInfo(data) {
        return request(base_url + "/wechat/updateUser", "POST", data)
    },
    RegionTree(data) {
        return request(base_url + "/system/dept/region", "POST", data)
    },
    eventReport(data) {
        return request(base_url + "/workflow/start/report", "POST", data)
    },
    checkPoints(data) {
        return request(base_url + "/screen/api/index/checkPoints", "GET", data)
    },
    // 点位类型下拉
    ListcheckPointsType() {
        return request(base_url + "/civilization/checkPointsType/page", "POST", {
            pageNum: 1,
            pageSize: 1000
        })
    },
    // 事发点位下拉
    ListcheckPoints(data) {
        return request(base_url + "/civilization/checkPoints/list", "POST", data)
    },
    // 事件类型下拉
    ListeventType() {
        return request(base_url + "/civilization/eventType/list", "POST", {
            pageNum: 1,
            pageSize: 1000
        })
    },
    // 检查项目下拉
    ListcheckItem(id) {
        return request(base_url + "/civilization/checkItem/page", "POST", {
            pageNum: 1,
            pageSize: 1000,
            typeId: id
        })
    },
    // Zerendanwei下拉
    ListDept() {
        return request(base_url + "/system/dept/list", "POST", {})
    },
    eventReport(data) {
        return request(base_url + "/workflow/start/report", "POST", data)
    },
    myReport(data) {
        return request(base_url + "/workflow/search/myReport", "POST", data)
    },
    myReportDetail(id, instanceid, scope, taskId) {
        return request(base_url + `/workflow/info/detail/${id}/${instanceid}?scope=${scope}&taskId=${taskId}`, "GET")
    },
    myReportDetail2(id) {
        return request(base_url + "/workflow/info/event/" + id, "GET")
    },
    allocate(data) {
        return request(base_url + `/workflow/search/allocate`, "POST", data)
    },
    allocated(data) {
        return request(base_url + `/workflow/search/allocated`, "POST", data)
    },
    handle(data) {
        return request(base_url + `/workflow/search/handle`, "POST", data)
    },
    appealsave(data) {
        return request(base_url + `/workflow/appeal/save`, "POST", data)
    },
    handled(data) {
        return request(base_url + `/workflow/search/handled`, "POST", data)
    },
    zonglan(data) {
        return request(base_url + `/workflow/search/page`, "POST", data)
    },
    check(data) {
        return request(base_url + `/workflow/search/check`, "POST", data)
    },
    finish(data) {
        return request(base_url + `/workflow/search/finished`, "POST", data)
    },
    extension(data) {
        return request(base_url + `/workflow/extension/page`, "POST", data)
    },
    extensionSave(data) {
        return request(base_url + `/workflow/extension/save`, "POST", data)
    },
    editEvent(data) {
        return request(base_url + `/workflow/info/edit`, "POST", data)
    },
    submitEvent(data) {
        return request(base_url + `/workflow/submit/submit`, "POST", data)
    },
    getUsersByNodeId(data) {
        return request(base_url + `/workflow/search/getUsersByNodeId`, "POST", data)
    },
    getDeptsByNodeId(data) {
        return request(base_url + `/workflow/search/getDeptsByNodeId`, "POST", data)
    },
    getAssessCycleList(data) {
        return request(base_url + `/civilization/assessCycle/page`, "POST", {
            pageNum: 1,
            pageSize: 1000
        })
    },
    citizen(data) {
        return request(base_url + `/workflow/search/citizen`, "POST", data)
    },
    eventPage(data) {
        return request(base_url + `/screen/api/wechat/workEvent`, "GET", data)
    },
    highType(data) {
        return request(base_url + `/screen/api/count/highType`, "GET", data)
    },
    eventRatio(data) {
        return request(base_url + `/screen/api/count/eventRatio`, "GET", data)
    },
    eventTrend(data) {
        return request(base_url + `/screen/api/count/eventTrend`, "GET", data)
    },
    eventEnd(data) {
        return request(base_url + `/screen/api/count/eventEnd`, "GET", data)
    },
    eventStatus(data) {
        return request(base_url + `/screen/api/count/eventStatus`, "GET", data)
    },
    highRegion(data) {
        return request(base_url + `/screen/api/count/highRegion`, "GET", data)
    },
    pointsAvg(data) {
        return request(base_url + `/screen/api/wechat/pointsAvg`, "GET", data)
    },
    eventTypeAvg(data) {
        return request(base_url + `/screen/api/wechat/eventTypeAvg`, "GET", data)
    },
    getMonth(data) {
        return request(base_url + `/screen/api/assess/getMonth`, "GET", data)
    },
    eventInfo(data) {
        return request(base_url + `/screen/api/index/eventInfo`, "GET", data)
    },
    evaluating(data) {
        return request(base_url + `/civilization/assess/evaluating`, "post", data)
    },
    evaluatingmonth(data) {
        return request(base_url + `/civilization/assess/month`, "POST", data)
    },
    evaluatingyear(data) {
        return request(base_url + `/civilization/assess/year`, "POST", data)
    },
    checkPointsTypePage(data) {
        return request(base_url + "/civilization/checkPointsType/page", "POST", data)
    },
    townPage(data) {
        return request(base_url + "/civilization/civilizationTown/page", "POST", data)
    },
    townDetail(id) {
        return request(base_url + "/civilization/civilizationTown/" + id, "POST")
    },
    companyPage(data) {
        return request(base_url + "/civilization/civilizationCompany/page", "POST", data)
    },
    companyDetail(id) {
        return request(base_url + "/civilization/civilizationCompany/" + id, "POST")
    },
    schoolPage(data) {
        return request(base_url + "/civilization/civilizationSchool/page", "POST", data)
    },
    schoolDetail(id) {
        return request(base_url + "/civilization/civilizationSchool/" + id, "POST")
    },
    familyPage(data) {
        return request(base_url + "/civilization/civilizationFamily/page", "POST", data)
    },
    familyDetail(id) {
        return request(base_url + "/civilization/civilizationFamily/" + id, "POST")
    },
    advancedPage(data) {
        return request(base_url + "/civilization/civilizationAdvanced/page", "POST", data)
    },
    advancedDetail(id) {
        return request(base_url + "/civilization/civilizationAdvanced/" + id, "POST")
    },
    volunteerPage(data) {
        return request(base_url + "/civilization/volunteer/page", "POST", data)
    },
    volunteerDetail(id) {
        return request(base_url + "/civilization/volunteer/" + id, "POST")
    },
    volunteerActivityPage(data) {
        return request(base_url + "/civilization/volunteerActivity/page", "POST", data)
    },
    volunteerActivityDetail(id) {
        return request(base_url + "/civilization/volunteerActivity/" + id, "POST")
    },
}