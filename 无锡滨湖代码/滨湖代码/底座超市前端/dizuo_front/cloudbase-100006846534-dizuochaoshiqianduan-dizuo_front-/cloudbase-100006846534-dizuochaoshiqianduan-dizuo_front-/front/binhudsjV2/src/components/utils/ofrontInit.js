/*
 * @Descripttion:基座方法应用处理
 * @version:
 * @Author: GM
 * @Date: 2021-06-08 09:29:47
 * @LastEditors: GM
 * @LastEditTime: 2022-02-21 11:19:43
 */
import "./ofront-sdk.js";
import axios from "axios";
import store from "@/store";
import message from "ant-design-vue/lib/message";

// 基座获取用户信息
async function getSysInfo() {
    const getSystemInfo = $ofront.require("getSystemInfo");
    const systemData = await getSystemInfo();
    return systemData;
}

// 获取基座信息
let ofrontInitFun = function() {
    // 本地运行环境,无基座情况下输入在线基座配置参数即可获取开发环境的下的基座参数。
    // store.commit("statechange", {
    //     ways: "token",
    //     data: 'access_token'
    // });
    // store.commit("statechange", {
    //     ways: "loginUserInfo",
    //     data: {
    //         extendsUser: {
    //             regionCode: "370685"
    //         }
    //     }
    // });
    if (process.env.NODE_ENV == "dev") {
        axios
            .post("http://10.10.29.141:7002/job/ocn/getAccessToken", {
                account: "admin",
                jobServerUrl: "http://10.10.29.141:7002/job/",
                password:
                    "io3c8Jj50tDmjfpILYiJ8DcrnoVs91i3SffmLK65TIbno7C0SBypBjgGYfKWiHEsYnnnBDCqfL9KOWZWR7nBRJW42QGK1wdnWQwt23gU2nbqyxRvtGOxiLYJhEqPrSKyONsFKOUoOyqh09i3I6DP6Az99liZn72oEfxWAc4pYrU=",
                serverUrl: "http://10.10.29.141:7002/system-api/",
                tenantId: "1",
                channel: "0"
            })
            .then(res => {
                console.log(res);
                const access_token = res.data.data.access_token;
                store.commit("statechange", {
                    ways: "token",
                    data: access_token
                });
                store.commit("statechange", {
                    ways: "loginUserInfo",
                    data: {
                        extendsUser: {
                            regionCode: "3706"
                        }
                    }
                });
                store.commit("statechange", {
                    ways: "getUserInfo",
                    data: {
                        "system": {
                            "home": {
                                "bg": "ae90e5f1-a448-49fb-93b6-4ebb77f62430",
                                "menulist": [
                                    {
                                        "menu": {
                                            "appId": null,
                                            "appPageId": null,
                                            "baseMenuLevel": 1,
                                            "baseMenuMark": "精治网格管理系统",
                                            "form": {
                                                "icon": "a9889e8e-e917-407f-8874-ad6f63c87d93",
                                                "name": ""
                                            },
                                            "icon": null,
                                            "isCatch": null,
                                            "isLast": false,
                                            "link": null,
                                            "markIntoTitle": "精治网格管理系统",
                                            "name": null,
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": false,
                                                "useRuoyi": false
                                            },
                                            "parentId": null,
                                            "prompt": "请填写有效连接",
                                            "skipType": "2",
                                            "templateId": null,
                                            "type": "5"
                                        },
                                        "name": "精治网格管理系统"
                                    },
                                    {
                                        "menu": {
                                            "name": null,
                                            "parentId": null,
                                            "isLast": false,
                                            "type": "5",
                                            "appId": null,
                                            "appPageId": null,
                                            "link": null,
                                            "prompt": "请填写有效连接",
                                            "templateId": null,
                                            "isCatch": null,
                                            "icon": null,
                                            "skipType": "2",
                                            "baseMenuLevel": 1,
                                            "baseMenuMark": "慧聚业务协同系统",
                                            "markIntoTitle": "慧聚业务协同系统",
                                            "form": {
                                                "icon": "f82f63a4-b756-4062-b2e0-428895307759",
                                                "name": ""
                                            },
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": false,
                                                "useRuoyi": false
                                            }
                                        },
                                        "name": "慧聚业务协同系统"
                                    },
                                    {
                                        "menu": {
                                            "appId": null,
                                            "appPageId": null,
                                            "baseMenuLevel": 1,
                                            "baseMenuMark": "社会稳定风险评估系统",
                                            "form": {
                                                "icon": "a95ddaad-0ff8-480c-80af-5e25cdb1273a",
                                                "name": ""
                                            },
                                            "icon": null,
                                            "isCatch": null,
                                            "isLast": false,
                                            "link": null,
                                            "markIntoTitle": "社会稳定风险评估系统",
                                            "name": null,
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": false,
                                                "useRuoyi": false
                                            },
                                            "parentId": null,
                                            "prompt": "请填写有效连接",
                                            "skipType": "2",
                                            "templateId": null,
                                            "type": "5"
                                        },
                                        "name": "社会稳定风险评估系统"
                                    },
                                    {
                                        "menu": {
                                            "appId": null,
                                            "appPageId": null,
                                            "baseMenuLevel": 1,
                                            "baseMenuMark": "平安指数管理系统",
                                            "form": {
                                                "icon": "2df0b85d-df08-4e3e-9b73-2c30efd4beeb",
                                                "name": ""
                                            },
                                            "icon": null,
                                            "isCatch": null,
                                            "isLast": false,
                                            "link": null,
                                            "markIntoTitle": "平安指数管理系统",
                                            "name": null,
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": false,
                                                "useRuoyi": false
                                            },
                                            "parentId": null,
                                            "prompt": "请填写有效连接",
                                            "skipType": "2",
                                            "templateId": null,
                                            "type": "5"
                                        },
                                        "name": "平安指数管理系统"
                                    },
                                    {
                                        "menu": {
                                            "name": null,
                                            "parentId": null,
                                            "isLast": false,
                                            "type": "2",
                                            "appId": null,
                                            "appPageId": null,
                                            "link": "http://wx.gisocn.com:11510/presentation?id=1c04f79c-8271-478e-9c0d-e1a9fd0e1767",
                                            "prompt": "请填写有效连接",
                                            "templateId": null,
                                            "isCatch": null,
                                            "icon": null,
                                            "skipType": "2",
                                            "baseMenuLevel": 0,
                                            "baseMenuMark": "基层多级社会治理指挥平台",
                                            "markIntoTitle": "基层多级社会治理指挥平台",
                                            "form": {
                                                "icon": "f300b6a8-30a9-4f48-9ce6-d23a9944419b",
                                                "name": ""
                                            },
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": false,
                                                "useRuoyi": false
                                            }
                                        },
                                        "name": "基层多级社会治理指挥平台"
                                    },
                                    {
                                        "menu": {
                                            "name": null,
                                            "parentId": null,
                                            "isLast": false,
                                            "type": "4",
                                            "appId": null,
                                            "appPageId": null,
                                            "link": null,
                                            "prompt": "请填写有效连接",
                                            "templateId": null,
                                            "isCatch": null,
                                            "icon": null,
                                            "skipType": "2",
                                            "baseMenuLevel": 0,
                                            "baseMenuMark": "统一运维管理系统",
                                            "markIntoTitle": "统一运维管理系统",
                                            "form": {
                                                "icon": "cf0ce157-e5ef-4cf8-9270-a6b531386f13",
                                                "name": ""
                                            },
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": false,
                                                "useRuoyi": true
                                            }
                                        },
                                        "name": "统一运维管理系统"
                                    }
                                ],
                                "opt": [],
                                "title": "28ce7942-0b84-4c4f-99b1-626d11639512"
                            },
                            "hosts": {
                                "ifsso": "2",
                                "jwt": "http://10.10.29.141:7002/job",
                                "sso": "",
                                "ssologin": "",
                                "userSystemFlag": 1
                            },
                            "isLoginIn": true,
                            "isRouyi": true,
                            "item": {
                                "name": "枣庄市社会治理一体化融合云平台",
                                "theme": "68f06472-104c-487f-8167-6b1d8d50c9ee",
                                "home": "3d059e56-0142-4037-a8f4-725ffed32d2e",
                                "menuId": "1552462032098381826",
                                "basicPlugin": "{\"theme\":{\"activeTextColor\":\"#1890FF\",\"backgroundColor\":\"#FFFFFF\",\"hasSwitchIcon\":true,\"hasSysMesAddress\":\"请输入\",\"hasSysMesIcon\":true,\"icon\":\"c9d51d16-0707-441a-9814-38ca85bede59\",\"textColor\":\"#1890FF\",\"themeMenu\":[{\"menu\":{\"appId\":null,\"appPageId\":null,\"baseMenuLevel\":0,\"form\":{},\"icon\":null,\"isCatch\":null,\"isLast\":false,\"link\":null,\"name\":null,\"ofrontapis\":{\"login\":false,\"logout\":true,\"useRuoyi\":false},\"parentId\":null,\"prompt\":\"请填写有效连接\",\"skipType\":\"2\",\"templateId\":null,\"type\":\"4\"},\"name\":\"退出系统\"}],\"title\":\"枣庄市社会治理一体化融合云平台\",\"titleBold\":\"700\",\"titleFontColor\":\"#1890FF\",\"titleFontSize\":\"20\",\"titleSpace\":\"1\"},\"home\":{\"bg\":\"ae90e5f1-a448-49fb-93b6-4ebb77f62430\",\"menulist\":[{\"menu\":{\"appId\":null,\"appPageId\":null,\"baseMenuLevel\":1,\"baseMenuMark\":\"精治网格管理系统\",\"form\":{\"icon\":\"a9889e8e-e917-407f-8874-ad6f63c87d93\",\"name\":\"\"},\"icon\":null,\"isCatch\":null,\"isLast\":false,\"link\":null,\"markIntoTitle\":\"精治网格管理系统\",\"name\":null,\"ofrontapis\":{\"login\":false,\"logout\":false,\"useRuoyi\":false},\"parentId\":null,\"prompt\":\"请填写有效连接\",\"skipType\":\"2\",\"templateId\":null,\"type\":\"5\"},\"name\":\"精治网格管理系统\"},{\"menu\":{\"name\":null,\"parentId\":null,\"isLast\":false,\"type\":\"5\",\"appId\":null,\"appPageId\":null,\"link\":null,\"prompt\":\"请填写有效连接\",\"templateId\":null,\"isCatch\":null,\"icon\":null,\"skipType\":\"2\",\"baseMenuLevel\":1,\"baseMenuMark\":\"慧聚业务协同系统\",\"markIntoTitle\":\"慧聚业务协同系统\",\"form\":{\"icon\":\"f82f63a4-b756-4062-b2e0-428895307759\",\"name\":\"\"},\"ofrontapis\":{\"login\":false,\"logout\":false,\"useRuoyi\":false}},\"name\":\"慧聚业务协同系统\"},{\"menu\":{\"appId\":null,\"appPageId\":null,\"baseMenuLevel\":1,\"baseMenuMark\":\"社会稳定风险评估系统\",\"form\":{\"icon\":\"a95ddaad-0ff8-480c-80af-5e25cdb1273a\",\"name\":\"\"},\"icon\":null,\"isCatch\":null,\"isLast\":false,\"link\":null,\"markIntoTitle\":\"社会稳定风险评估系统\",\"name\":null,\"ofrontapis\":{\"login\":false,\"logout\":false,\"useRuoyi\":false},\"parentId\":null,\"prompt\":\"请填写有效连接\",\"skipType\":\"2\",\"templateId\":null,\"type\":\"5\"},\"name\":\"社会稳定风险评估系统\"},{\"menu\":{\"appId\":null,\"appPageId\":null,\"baseMenuLevel\":1,\"baseMenuMark\":\"平安指数管理系统\",\"form\":{\"icon\":\"2df0b85d-df08-4e3e-9b73-2c30efd4beeb\",\"name\":\"\"},\"icon\":null,\"isCatch\":null,\"isLast\":false,\"link\":null,\"markIntoTitle\":\"平安指数管理系统\",\"name\":null,\"ofrontapis\":{\"login\":false,\"logout\":false,\"useRuoyi\":false},\"parentId\":null,\"prompt\":\"请填写有效连接\",\"skipType\":\"2\",\"templateId\":null,\"type\":\"5\"},\"name\":\"平安指数管理系统\"},{\"menu\":{\"name\":null,\"parentId\":null,\"isLast\":false,\"type\":\"2\",\"appId\":null,\"appPageId\":null,\"link\":\"http://wx.gisocn.com:11510/presentation?id=1c04f79c-8271-478e-9c0d-e1a9fd0e1767\",\"prompt\":\"请填写有效连接\",\"templateId\":null,\"isCatch\":null,\"icon\":null,\"skipType\":\"2\",\"baseMenuLevel\":0,\"baseMenuMark\":\"基层多级社会治理指挥平台\",\"markIntoTitle\":\"基层多级社会治理指挥平台\",\"form\":{\"icon\":\"f300b6a8-30a9-4f48-9ce6-d23a9944419b\",\"name\":\"\"},\"ofrontapis\":{\"login\":false,\"logout\":false,\"useRuoyi\":false}},\"name\":\"基层多级社会治理指挥平台\"},{\"menu\":{\"name\":null,\"parentId\":null,\"isLast\":false,\"type\":\"4\",\"appId\":null,\"appPageId\":null,\"link\":null,\"prompt\":\"请填写有效连接\",\"templateId\":null,\"isCatch\":null,\"icon\":null,\"skipType\":\"2\",\"baseMenuLevel\":0,\"baseMenuMark\":\"统一运维管理系统\",\"markIntoTitle\":\"统一运维管理系统\",\"form\":{\"icon\":\"cf0ce157-e5ef-4cf8-9270-a6b531386f13\",\"name\":\"\"},\"ofrontapis\":{\"login\":false,\"logout\":false,\"useRuoyi\":true}},\"name\":\"统一运维管理系统\"}],\"opt\":[],\"title\":\"28ce7942-0b84-4c4f-99b1-626d11639512\"},\"individualConfiguration\":true,\"hosts\":{\"ifsso\":\"2\",\"jwt\":\"http://10.10.29.141:7002/job\",\"sso\":\"\",\"ssologin\":\"\",\"userSystemFlag\":1},\"ruoyi\":{\"activerule\":\"/ruoyi\",\"backUrl\":\"http://10.10.29.141:7002/job\",\"id\":100001,\"route\":\"/ruoyi/index\",\"serverUrl\":\"http://10.10.29.141:7002/system-api\",\"type\":1,\"url\":\"http://10.10.29.141:7002/system-web/\"},\"watermark\":true,\"isRouyi\":true,\"isLoginIn\":true}",
                                "status": "1",
                                "cover": "c9d51d16-0707-441a-9814-38ca85bede59",
                                "hasMain": "",
                                "mainId": "zaozhuangshiwanggezhongqiceshi"
                            },
                            "ruoyi": {
                                "activerule": "/ruoyi",
                                "backUrl": "http://10.10.29.141:7002/job",
                                "id": 100001,
                                "route": "/ruoyi/index",
                                "serverUrl": "http://10.10.29.141:7002/system-api",
                                "type": 1,
                                "url": "http://10.10.29.141:7002/system-web/"
                            },
                            "theme": {
                                "activeTextColor": "#1890FF",
                                "backgroundColor": "#FFFFFF",
                                "hasSwitchIcon": true,
                                "hasSysMesAddress": "请输入",
                                "hasSysMesIcon": true,
                                "icon": "c9d51d16-0707-441a-9814-38ca85bede59",
                                "textColor": "#1890FF",
                                "themeMenu": [
                                    {
                                        "menu": {
                                            "appId": null,
                                            "appPageId": null,
                                            "baseMenuLevel": 0,
                                            "form": {},
                                            "icon": null,
                                            "isCatch": null,
                                            "isLast": false,
                                            "link": null,
                                            "name": null,
                                            "ofrontapis": {
                                                "login": false,
                                                "logout": true,
                                                "useRuoyi": false
                                            },
                                            "parentId": null,
                                            "prompt": "请填写有效连接",
                                            "skipType": "2",
                                            "templateId": null,
                                            "type": "4"
                                        },
                                        "name": "退出系统"
                                    }
                                ],
                                "title": "枣庄市社会治理一体化融合云平台",
                                "titleBold": "700",
                                "titleFontColor": "#1890FF",
                                "titleFontSize": "20",
                                "titleSpace": "1"
                            },
                            "watermark": true
                        },
                        "menu": [
                            {
                                "children": [],
                                "data": {
                                    "name": "统计分析",
                                    "parentId": "1552465656190947329",
                                    "isLastFlag": true,
                                    "isLast": 1,
                                    "type": "1",
                                    "appId": null,
                                    "appPageId": null,
                                    "pageId": "7ea5165bf61c3b3e97a838fae857ea73",
                                    "link": null,
                                    "templateId": null,
                                    "isCatch": "7",
                                    "icon": null,
                                    "children": [],
                                    "data": {
                                        "appId": "427d7fb8e155a8ed9593b0a84da6965c",
                                        "appPageId": "7ea5165bf61c3b3e97a838fae857ea73",
                                        "children": [],
                                        "createTime": null,
                                        "createUserId": null,
                                        "delFlag": 0,
                                        "icon": null,
                                        "id": "1552473166352306177",
                                        "isCatch": "7",
                                        "isLast": "1",
                                        "link": null,
                                        "name": "数据统计",
                                        "parentId": "1552465656190947329",
                                        "perms": null,
                                        "templateId": null,
                                        "type": "1",
                                        "updateTime": null,
                                        "updateUserId": null
                                    },
                                    "id": "1552473166352306177"
                                },
                                "icon": null,
                                "id": "1552473166352306177",
                                "isCatch": "7",
                                "isLast": 1,
                                "link": null,
                                "name": "统计分析",
                                "pageId": "7ea5165bf61c3b3e97a838fae857ea73",
                                "templateId": null,
                                "type": "1"
                            }
                        ]
                    }
                });
            });
        return;
    }
    getSysInfo().then(res => {
        const getUserInfo = $ofront.require("getUserInfo");
        getUserInfo()
            .then(user => {
                store.commit("statechange", {
                    ways: "token",
                    data: user.access_token
                });
                store.commit("statechange", {
                    ways: "loginUserInfo",
                    data: user
                });
                store.commit("statechange", {
                    ways: "getUserInfo",
                    data: res
                });
                console.log(
                    "🚀 ~ file: ofrontInit.js ~ line 32 ~ getSysInfo ~ store.getters.getUserInfo",
                    store.getters.get_getUserInfo
                );
            })
            .catch(err => {
                // 没有用户信息，会自动跳转单点登录
                err;
            });
    });
};

export default ofrontInitFun;
