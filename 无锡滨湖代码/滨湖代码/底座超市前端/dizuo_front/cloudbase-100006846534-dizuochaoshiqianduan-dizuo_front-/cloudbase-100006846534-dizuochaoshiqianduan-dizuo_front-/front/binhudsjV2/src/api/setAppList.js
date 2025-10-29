// 获取所有的应用
import axios from "axios";
import { fRequest } from './whatwgFetch.js';
import { URLS_USERINFO, URLS_SZBH, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js';
import { reject } from "lodash";
// let aAllData = []
let aModel = []
function fSetArray(data, obj, array) {
    let arr = array || []
    if (data.children) {
        data?.children.forEach(element => {
            if (element.nodeType == '1') {
                fSetArray(element, obj, arr)
            } else {
                arr.push({ ...element, ...obj })
            }
        });
    }
    return arr
}
// 请求蓝创的数据
const fGetLanList1 = fRequest({
    url: 'https://2.21.138.89:11511/system_server/api/addApplicationClassification',
});

async function fRequestModel() {
    let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
    if (!oUserInfo) return
    let res = await fRequest({
        url: 'https://2.21.138.89:11511/system_server/api/addApplicationClassification',
    })
    aModel = res.data
    localStorage.setItem('aModel', JSON.stringify(aModel))
    // console.log(aModel)
}
function fGetModelName(id, data, type) {
    let arr = []
    if (data && data.length > 0) {
        if (type) {
            data.forEach(item => {
                arr = item.children.map(item => {
                    if (item.id === id) {
                        return item.title
                    }
                })
            })
        } else {
            arr = data.map(item => {
                if (item.id === id) {
                    return item.title
                }
            })
        }

    }
    return arr.filter(item => item)[0]
};

// 请求若依的数据
const fGetRouyilist1 = fRequest({
    url: 'https://2.21.138.89:11511/cmApp/list?pageNum=1&pageSize=99999',
});

// export default  fRequest
export default async function fGetAppListAsync() {
    if (localStorage.getItem('aModel')) {
        aModel = JSON.parse(localStorage.getItem('aModel'))
    } else {
        fRequestModel()
    }

    let aAllData = []
    return new Promise((resolve, reject) => {
        axios.get('https://2.21.138.89:11511/cmApp/list', {
            params: {
                pageNum: 1,
                pageSize: 99999,
            }
        }).then(
            response => {
                // console.log(aModel)
                response.data.data.records.forEach(item => {
                    // 排除和蓝创重复的应用
                    // let obj = aAllData.find(obj => {
                    //     return obj.sTypeId === item.casAppId
                    // })
                    // if (obj == null || obj == undefined) {
                    item.sTypeName = fGetModelName(item.appModule, aModel)
                    item.sType = fGetModelName(item.appType, aModel, 'children')
                    item.isRecommend = item.recommendFlag; // 是否推荐
                    item.title = item.appName; // 应用名称
                    // item.deptName = item.appName; // 单位名称
                    item.url = item.appLink; // 应用地址
                    item.logo = item.appPic;// 应用图标
                    item.icon = item.appScreenshot ? JSON.parse(item.appScreenshot).join(',') : '';
                    item.userColumn = item.remark; // 应用简介
                    item.description = item.remark; // 应用简介
                    item.OCN = true; // OCN的应用
                    // }
                    // item.userColumn = item.remark
                })
                resolve(response.data.data.records.filter(data=>data.status == 0));
            },
            err => {
                reject(err);
            })
            .catch(error => {
                reject(error);
            });
    })
    // await Promise.all([fGetLanList1, fGetRouyilist1]).then(results => {
    //     if (localStorage.getItem('aModel')) {
    //         aModel = JSON.parse(localStorage.getItem('aModel'))
    //     } else {
    //         fRequestModel()
    //     }
    //     // console.log(results)
    //     // alert('请求了')
    //     // results[0].data.forEach(item => {
    //     //     if (item.children && item.children.length > 0) {
    //     //         item.children.forEach(aData => {
    //     //             if (aData.children && aData.children.length > 0) {
    //     //                 aData.children.forEach(obj => {
    //     //                     obj.sType = aData.title
    //     //                 })
    //     //             }

    //     //         })
    //     //     }
    //     // })
    //     // results[0].data.forEach(item => {
    //     //     if (item.children) {
    //     //         aAllData.push(...fSetArray(item, { sTypeId: item.id, sTypeName: item.title }))
    //     //     }
    //     // });
    //     // console.log("cmApp/list")
    //     aModel = results[0].data
    //     console.log(results[1])
    //     results[1].data.records.forEach(item => {
    //         // 排除和蓝创重复的应用
    //         // let obj = aAllData.find(obj => {
    //         //     return obj.sTypeId === item.casAppId
    //         // })
    //         // if (obj == null || obj == undefined) {
    //         // item.sTypeName = fGetModelName(item.appModule, aModel)
    //         // item.sType = fGetModelName(item.appType, aModel, 'children')
    //         // item.isRecommend = item.recommendFlag; // 是否推荐
    //         // item.title = item.appName; // 应用名称
    //         // item.deptName = item.appName; // 单位名称
    //         // item.url = item.appLink; // 应用地址
    //         // item.logo = item.appPic;// 应用图标
    //         // item.icon = item.appScreenshot ? JSON.parse(item.appScreenshot).join(',') : '';
    //         // item.userColumn = item.remark; // 应用简介
    //         // item.description = item.remark; // 应用简介
    //         // item.OCN = true; // OCN的应用
    //         // }
    //         // item.userColumn = item.remark
    //     })
    //     aAllData.push(...results[1].data.records)
    // })
    // return aAllData
}
