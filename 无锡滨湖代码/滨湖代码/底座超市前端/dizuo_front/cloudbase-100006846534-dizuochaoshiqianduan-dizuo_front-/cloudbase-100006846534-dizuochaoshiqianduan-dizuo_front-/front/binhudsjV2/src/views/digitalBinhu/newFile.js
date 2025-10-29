import { URLS_USERINFO, URLS_SZBH, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js';
import fGetAppListAsync from '@/api/setAppList.js';

export default (await import('vue')).defineComponent({
name: '',
components: {
vueSeamlessScroll,
CompChartJdt,
ComBlockT,
ComListIndex,
SubRmsjzy, // 热门数据资源
},
props: [''],
data() {
return {
oUserInfo: {},
aAllAppData: [],
nAllAppComUnitNum: 0,
aBannerImage: [
require('@/assets/image/banner/one.jpg'),
require('@/assets/image/banner/two.jpg'),
require('@/assets/image/banner/three.jpg'),
require('@/assets/image/banner/four.jpg'),
],
aHydpmData: [],
sBannerKeyWords: '',

//  场景应用的数量
oCjyyNum: {
'数字政务': 0,
'数字治理': 0,
'数字经济': 0,
'数字生活': 0,
'数字生态': 0,
'数字基建': 0,
},
// 数据总览
aAllData: [
{ name: '数源系统数', unit: '个', value: '342', color: '#0e76ef', image: require('../../assets/image/home/V2/xzcs.png') },
{ name: '平台访问量', unit: '次', value: '200000', image: require('../../assets/image/home/V2/ptfwl.png') },
{ name: '用户注册数', unit: '次', value: '700', color: '#ea9518', image: require('../../assets/image/home/V2/yhzcs.png') },
{ name: '资源信息项', unit: '个', value: '245', color: '#1a5ffe', image: require('../../assets/image/home/V2/zyxxx.png') },
{ name: '应用服务', unit: '个', value: 0, color: '#15c321', image: require('../../assets/image/home/V2/yyfw.png') },
],
aAllDataV2: [
{ name: '应用推荐', value: '30', type: 'yytj', show: true, },
{ name: '最新推荐', value: '10', type: 'zxtj', show: true, },
{ name: '申请上架', value: '10', type: 'sqsj', show: true, },
{ name: '数据共享', value: '234', type: 'sjgx1', show: false, },
{ name: '组件服务', value: '8', type: 'zjNum', show: true, },
{ name: '工具支持', value: '20', type: 'gjNum', show: true, },
],
// 基础信息资源
aJcxxzyData: [
{ dept_name: '数据资源浏览量', count: '', level: '公开', source: '数据中台', },
{ dept_name: '数据资源申请量', count: '', level: '公开', source: '数据中台', },
{ dept_name: '覆盖系统总数', count: '', level: '公开', source: '数据中台', },
{ dept_name: '涉及部门总数', count: '', level: '公开', source: '数据中台', },
{ dept_name: '汇集目录总数', count: '', level: '公开', source: '数据中台', },
],
ajcxxzyCount: [
{ title: '基础信息资源', count: '1590', add: '+23' },
{ title: '已汇聚资源', count: '1028', add: '+522' },
{ title: '数源组织', count: '34', add: '+4' },
],
aRmshzyData: [],
oChangePassWord: {
account: '',
oldPassword: '',
newPassword: '', // 新密码
},
bShowAdminBtn: false,

// 单位资源排名
oDwzypm: {
name: [],
value: [],
valueMax: [],
},
// 平台能力
aPtnl: [
{ name: '密码服务平台', type: 'mmfw', id: '', details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '根据平台的部署方式和实现业务功能，在满足总体性、完备性、经济性原则的基础上，通过使用云密码资源服务、密钥管理系统、SSLVPN服务、数据透明加解密系统、软件密码模块、动态令牌等，以及第三方CA服务、结合智能密码钥匙、国密浏览器来建立完善的密码应用安全保障体系。', } },
{ name: 'PAAS能力平台', type: 'passnl', id: '', details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '提供标准的PaaS 产品接入标准，可以将PaaS 产品接入到平台进行统一管理，支持容器化托管，提供统一管理集成度；具有接入统一管理数据库服务、统一管理中间件服务等能力，为整体云原生应用开发打下基础。本期PaaS平台包含tdsql，Tencent OS，ckafka，credis。', } },
{ name: '可视化大屏', type: 'kshpt', id: '167134070142351769', },
],
// 组件服务
aZjfw: [
{ name: '视频汇集平台', type: 'sphj', id: '', details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '建设全区统一的视频汇聚平台，整合接入多来源的视频图像资源。制定视频资源接入标准，统一接频资源，根据各部门对视频资源的实际需求，分配相应的调用权限，规范共享授权管理，实现视频入城管、交通、应急等各委办局、乡镇（街道）已建视频监控资源，推动社会单位视频资源接入汇聚平台。统一对全区整合后的视资源集中管理、统一分配、统一控制，保证视频资源效能的最大化。', } },
{ name: '统一运维平台', type: 'tiyw', id: '1665646406557274114', },
{ name: '数据中台', type: 'sjzt', id: '1673222396863172609', },
],
// 公开支撑
aGjzc: [
{ name: 'AI平台', type: 'ai', id: '1665909999420870658', },
{ name: 'CIM平台', type: 'cim', id: '1671342421583085570', },
{ name: '低代码平台', type: 'ddm', id: '1671340254377820161', },
],
};
},
computed: {
defaultOption() {
return {
step: 0.3,
limitMoveNum: this.aJcxxzyData.length,
hoverStop: true,
autoPlay: this.aJcxxzyData.length > 11 ? true : false,
direction: 1,
openWatch: true,
singleHeight: 0,
singleWidth: 0,
waitTime: 2000 // 单步运动停止的时间(默认值1000ms)
};
}
},
watch: {},
created() {
},
mounted() {
this.fRequestUserInfo(); // 请求用户信息




// this.fRequestHydpmData();
// this.$nextTick(() => {
//     this.aJcxxzyData = this.fSetLen(this.aJcxxzyData)
// })
},
beforeDestroy() {
},
methods: {
// 请求集合
fRequest() {
// this.fRequestSjzylll();// 请求数据资源浏览量
// this.fRequestSjzysql();// 请求数据资源申请量
// this.fRequestFgxtzs();// 请求覆盖系统总数
// this.fRequestSjbmzs();// 请求涉及部门总数
// this.fRequestHjmuzs();// 请求汇集目录总数
this.fRequestApphyd(); // 请求应用活跃度
this.fRequestRmsjzyData(); // 请求热门数据资源
this.fRequestBmzypm(); // 请求部门资源排名
this.fRequestZyjsData(); // 资源检索
this.fRequestBannerData(); // 平台访问次数
this.fRequestSjgx(); //请求数据共享
this.fRequestDownCount(); //请求下载次数
this.fRequestSourceCount(); //请求资源信息项
this.fRequestApplicationApplyForList(); //根据申请人查询应用申请
},
fFalseReq(oUserInfo) {
this.oUserInfo = oUserInfo;
this.$store.commit('fSetUserInfo', oUserInfo);
this.oChangePassWord.account = this.oUserInfo.account;
this.fCheckInitPassword(); // 验证用户密码是否是初始密码
this.$EventBus.$emit('fGetUserBtn', oUserInfo);
// this.fRequestAllpp();// 请求我的应用
// this.fRequest();
},
// 根据申请人查询应用申请
fRequestApplicationApplyForList() {
this.$fRequest({
url: URLS_USERINFO.u_applicationApplyForList,
data: {
applicant: JSON.parse(window.localStorage.getItem('userInfo')).account,
// keyword: this.sMyAppKeyWord,
current: 1,
size: 10, // 每页数量
}
}).then(res => {
// console.log(res, '根据申请人查询应用申请')
this.aAllDataV2[2].value = res.data.total;
// this.aSjApp = res.data.records;
});
},
// 请求用户信息
fRequestUserInfo() {
// alert(111)
let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
// console.log(oUserInfo)
let oData = {};
if (oUserInfo && oUserInfo.account) {
// oData.account = oUserInfo.account
this.fFalseReq(oUserInfo);
return;
} else {
oData.code = this.$store.state.sCode;
// alert(this.$store.state.sCode)
this.$fRequest({
url: URLS_USERINFO.u_getCasUserInfo,
data: { ...oData }
}).then(res => {
// console.log(res, '用户信息')
if (res.msg == '授权码无效') {
this.fLoginOut();
return;
}
localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo));
// console.log(res);
this.oUserInfo = res.data.userInfo;
this.$EventBus.$emit('fGetUserBtn', this.oUserInfo);

this.$store.commit('fSetUserInfo', res.data.userInfo);
this.oChangePassWord.account = this.oUserInfo.account;
this.fCheckInitPassword(); // 验证用户密码是否是初始密码


// this.fRequestAllpp();// 请求我的应用
// this.fRequest();
});
}
// this.$router.go(0)
},
// 验证用户密码是否是初始密码
fCheckInitPassword() {
this.$fRequest({
url: URLS_USERINFO.u_checkInitPassword,
data: {
account: this.oUserInfo.account
}
}).then(res => {
// console.log(res,'验证用户密码是否是初始密码')
// alert(222)
// if (res.message.indexOf('token') >= 0) {
//     this.fLoginOut();
//     return;
// }
// if (res.data == 0) { // 是初始密码，未修改过
//     this.$message({
//         message: '当前密码为初始密码，请先修改密码！',
//         type: 'warning'
//     });
//     this.$EventBus.$emit('fShowChangePasswordlayer', true);
//     // this.bChangePasswordlayer = true;
// }
this.fRequestAllpp(); // 请求我的应用
this.fRequest();
});
},
async fRequestAllpp() {
const res = await fGetAppListAsync();
let aData = res.filter(item => item.sTypeName == '应用');
let aAllData = res;
this.$store.commit('fSetAllAppDataRes', res.filter(item => item.sTypeName == '应用'));
this.$store.commit('fSetComUtilsData', [...res.filter(item => item.sTypeName == '组件'), ...res.filter(item => item.sTypeName == '工具')]);
this.aAllDataV2[4].value = res.filter(item => item.sTypeName == '组件').length;
this.aAllDataV2[5].value = res.filter(item => item.sTypeName == '工具').length;

this.fSetDetails(aAllData, 'aPtnl'); // 平台能力
this.fSetDetails(aAllData, 'aZjfw'); // 组件服务
this.fSetDetails(aAllData, 'aGjzc'); // 工具支撑









// let aComData = res.filter(item => item.sTypeName.indexOf('应用') >= 0);// 获取所有组件
// aComData.forEach(item => {
//     for (const key in this.oCjyyNum) {
//         if (key === item.title) {
//             this.oCjyyNum[key] = item.children?.length || 0
//         }
//     }
// })
this.aAllAppData = aData;
this.aAllDataV2[0].value = this.aAllAppData.filter(item => item.isRecommend == '1').length;
this.nAllAppComUnitNum = aAllData.length;
this.aAllData[4].value = aAllData.length;
this.$store.commit('fSetAllAppData', aData); // 请求全部应用
this.$store.commit('fSetAllApp', aData); // 请求全部应用
this.fRequestMyApp(aAllData.length); // 请求我的应用

},
// 请求所有应用 / 工具 / 组件
// fRequestAllpp() {
//     this.$fRequest({
//         url: URLS_USERINFO.u_applicationList,
//     }).then(res => {
//         // console.log(111, res)
//         let aData = []
//         let aAllData = []
//         this.$store.commit('fSetAllAppDataRes', res.data[2].children);
//         this.$store.commit('fSetComUtilsData', [res.data[0], res.data[1]]);
//         // 0 工具  1 组件  2 应用
//         // console.log(res.data[0])
//         this.aAllDataV2[4].value = this.fSetStoreAllApp(res.data[1]).length
//         this.aAllDataV2[5].value = this.fSetStoreAllApp(res.data[0]).length
//         res.data.forEach(item => {
//             if (item.children) {
//                 aAllData.push(...this.fSetStoreAllApp(item))
//             }
//         })
//         // this.$store.commit('fSetAllAppComUtil',aAllData) // 
//         this.fSetDetails(aAllData, 'aPtnl');// 平台能力
//         this.fSetDetails(aAllData, 'aZjfw');// 组件服务
//         this.fSetDetails(aAllData, 'aGjzc');// 工具支撑
//         let aComData = res.data.filter(item => item.title.indexOf('应用') >= 0)[0];// 获取所有组件
//         aComData.children.forEach(item => {
//             if (item.children && item.children.length > 0) {
//                 aData.push(...item.children)
//             }
//             for (const key in this.oCjyyNum) {
//                 if (key === item.title) {
//                     this.oCjyyNum[key] = item.children?.length || 0
//                 }
//             }
//         })
//         // console.log(this.oCjyyNum)
//         // console.log(this.aAllAppData)
//         this.aAllAppData = aData;
//         // console.log(this.aAllAppData.filter(item => item.isRecommend == '1'), 1111111)
//         this.aAllDataV2[0].value = this.aAllAppData.filter(item => item.isRecommend == '1').length
//         this.nAllAppComUnitNum = aAllData.length;
//         this.aAllData[4].value = aAllData.length;
//         this.$store.commit('fSetAllAppData', aData);// 请求全部应用
//         this.$store.commit('fSetAllApp', aData);// 请求全部应用
//         this.fRequestMyApp(aAllData.length);// 请求我的应用
//         // this.fRequestAllApp(aAllData.length);// 请求全部应用 - 无分类
//     })
// },
// 数字底座跳转
fClickNumberBase(data) {
let oObj = this.$fSetData(data, ['appSecret']);
this.$router.push({
path: "/digitalBinhu/baseDetail",
query: { ...oObj, }
});
console.log(data, '跳转详情');
},
// 设置应用详情
fSetDetails(data, sDataName) {
data.forEach(item => {
this[sDataName].forEach(name => {
if (item.id == name.id) {
name.details = { ...name, ...item, showBtn: true };
// console.log(111, name)
}
});
});
// console.log(this[sDataName])
},
// 请求底座超市总计登录数量
fRequestBannerData() {
this.$fRequest({
url: URLS_SZDZ.u_baseSupermarket,
}).then(res => {
this.aAllData[1].value = res.data.totalNum;
// this.aTopData[0].value = res.data[0].field;
// this.aRmshzyData = res.data;
});
},
// 请求关键字资源检索
fRequestZyjsData() {
$.ajax({
url: URLS_SZDZ.u_digital_base_keyWordsSearch,
method: 'post',
data: {
keyWord: '',
pageSize: '100',
pageNo: 1 // 页码
}
}).then(res => {
// console.log(res, '请求关键字资源检索')
this.aJcxxzyData = res.data[0];
this.ajcxxzyCount[0].count = res.data[1][0].total;
// alert(111)
});
},
// 请求当前登录用户的应用
fRequestMyApp(len) {
this.$fRequest({
url: URLS_USERINFO.u_userApplication,
data: {
account: this.oUserInfo.account,
keyword: '',
current: 1,
size: len + 50, // 每页数量
}
}).then(res => {
// console.log(res.data[0].appList.records)
// console.log(res.data[0].appList.records.sort(this.fSetDataSort('loginNumber', false)))
this.$store.commit('fSetAllAppComUtil', res.data[0].appList.records);
// 
this.bShowAdminBtn = false;
this.$EventBus.$emit('fShowAdminBtn', this.oUserInfo.adminFlag);
window.localStorage.setItem('bShowAdminBtn', this.oUserInfo.adminFlag);
// setTimeout(() => {
//     this.$nextTick(() => {
//         console.log(this.$store.state.oUserInfo.adminFlag)
//         res.data[0].appList.records.forEach(item => {
//             if (item.appId === 'ZmVLiSl0') {
//                 this.$store.state.oUserInfo.adminFlag = true;
//                 window.localStorage.setItem('bShowAdminBtn', this.$store.state.oUserInfo.adminFlag)
//                 this.$EventBus.$emit('fShowAdminBtn', this.$store.state.oUserInfo.adminFlag)
//                 console.log('存在-显示后台管理')
//             }
//         })
//     })
// }, 100);
this.$store.commit('fSetUserAppData', res.data[0].appList.records.sort(this.fSetDataSort('loginNumber', false)));
});
},
// 请求应用活跃度
fRequestApphyd() {
this.$fRequest({
url: URLS_APPMARKET.u_applicationActivation,
data: {
type: '1',
month: '',
}
}).then(res => {
// console.log(res)
// if (res.message.indexOf('token') >= 0) {
//     this.timer = setTimeout(() => {
//         this.fRequestApphyd();
//     }, 100);
//     return;
// }
this.aHydpmData = [];
res.data.forEach(item => {
this.aHydpmData.push({
name: item.name,
hyd: item.recordNum,
level: item.rankChange >= 0 ? 'top' : 'bot',
num: item.rankChange
});
});
});
},
// 请求热门数据资源
fRequestRmsjzyData() {
$.ajax({
url: URLS_SZDZ.u_digital_base_hot_source,
}).then(res => {
// console.log(res)
this.aRmshzyData = res.data;
});
// this.$fRequest({
//     url: URLS_SZDZ.u_digital_base_hot_source,
// }).then(res => {
//     // console.log(res, '请求热门数据资源')
//     this.aRmshzyData = res.data;
// })
},
// 请求数据共享
fRequestSjgx() {
$.ajax({
url: URLS_SZBH.u_fisrt_count_sourceShareCount,
}).then(res => {
this.aAllDataV2[3].value = Number(res.data[0].total) + Number(res.data[1].total);
// console.log(res, '请求数据共享')
});
},
// 请求下载次数
fRequestDownCount() {
$.ajax({
url: URLS_SZBH.u_fisrt_count_sourceDownCount,
}).then(res => {
this.aAllData[0].value = res.data[0].value;
// console.log(res, '请求下载次数')
});
},
// 请求资源信息项
fRequestSourceCount() {
$.ajax({
url: URLS_SZBH.u_fisrt_count_sourceCount,
}).then(res => {
this.aAllData[3].value = res.data[0].field;
// console.log(res, '请求资源信息项')
});
},
// 请求部门资源排名
fRequestBmzypm() {
$.ajax({
url: URLS_SZDZ.u_digital_base_hot_source_sort,
}).then(res => {
// console.log(res, '请求部门资源排名')
this.oDwzypm.name = [];
this.oDwzypm.value = [];
res.data.forEach((item, idx) => {
this.oDwzypm.name.push(item.dept_name);
this.oDwzypm.value.push(item.count);
});
console.log(this.oDwzypm);
this.$refs.dwzypm.fSetData(this.oDwzypm, { color: false }); //图表





// this.$refs.zyflpm.fSetData({
//     name: ['社保就业', '财政金融', '城建住房', '生活服务', '生态环境',],
//     value: [10000, 9830, 8749, 7492, 7422]
// }, { color: true });//图表
});
},
// 请求数据资源浏览量
fRequestSjzylll() {
this.$fRequest({
url: URLS_SZBH.u_first_page_data_read_count,
}).then(res => {
// console.log(res.data, '请求数据资源浏览量')
this.aJcxxzyData[0].count = res.data[0].count;
// this.aJcxxzyData = [];
});
},
// 请求数据资源申请量
fRequestSjzysql() {
this.$fRequest({
url: URLS_SZBH.u_first_page_data_apply_count,
}).then(res => {
// console.log(res.data, '请求数据资源申请量')
this.aJcxxzyData[1].count = res.data[0].count;
});
},
// 请求覆盖系统总数
fRequestFgxtzs() {
this.$fRequest({
url: URLS_SZBH.u_first_page_fu_gai_count,
}).then(res => {
// console.log(res.data, '请求覆盖系统总数')
this.aJcxxzyData[2].count = res.data[0].value;
});
},
// 请求涉及部门总数
fRequestSjbmzs() {
this.$fRequest({
url: URLS_SZBH.u_first_page_she_ji_dept_count,
}).then(res => {
// console.log(res.data, '请求涉及部门总数')
this.aJcxxzyData[3].count = res.data[0].sum;
});
},
// 请求汇集目录总数
fRequestHjmuzs() {
this.$fRequest({
url: URLS_SZBH.u_first_page_hui_ji_mu_lu_count,
}).then(res => {
// console.log(res.data, '请求汇集目录总数')
this.aJcxxzyData[4].count = res.data[0].sum;
});
},
// 退出登录
fLoginOut() {
localStorage.removeItem('userInfo');
localStorage.clear();
sessionStorage.clear();
// this.$fRequest({
//     url: 'http://2.21.137.200:8081/logout',
//     // url: URLS_USERINFO.u_logout,
// }).then(res => {
//     console.log(res, '退出登录')
// })
// return
// let backlen = window.history.length - 1;
// window.history.go(-backlen);
this.$router.go(-this.$router.history.current.indexOf);
window.location.replace('http://2.22.51.26:30002/logout?logoutUrl=https://2.21.138.89:11511/api/login');
// window.open('http://2.21.137.200:8081/logout?logoutUrl=', '_self');
// window.close();
// window.open('https://2.21.138.89:11511/api/login')
},
// 查看详情
fClickAppDetails(data) {
// console.log(data)
let oObj = this.$fSetData(data, ['appSecret']);
this.$router.push({
path: "/appMarket/appDetails",
query: { ...oObj }
});
},
// 设置所有组件应用工具的集合
fSetStoreAllApp(data) {
let aData = [];
data?.children.forEach(item => {
if (item.children && item.children.length > 0) {
aData.push(...item.children);
}
});
return aData;
},
// 设置数组长度
fSetLen(data) {
// console.log(data)
if (data.length < 12) {
this.$nextTick(() => {
this.fSetLen([...data, ...data]);
});
} else {
return data;
}
},
// 轮播图输入框搜索按钮
fClickSearch() {
// console.log(this.sBannerKeyWords);
this.$router.push({
path: "/digitalBinhu/binhuSearch",
query: { name: this.sBannerKeyWords.trim() }
});
},
// 请求活跃度排名
fRequestHydpmData() {
this.$fRequest({
url: URLS_APPMARKET.u_applicationActivation,
data: {
type: '1',
month: '',
}
}).then(res => {
// console.log(res)
});
},
// 跳转页面
fChangePage(type) {
// this.$store.commit('fSetAppMarket', type)
// this.$store.commit('fSetNowPage', { type: 'appmarket', page: 'appmarket', component: 'sub-appmarket', id: 'vital1' });
this.$router.push({
path: "/appmarket",
query: { type, id: 'vital1' }
});
},
// 数组排序
fSetDataSort(attr, rev) {
//第二个参数没有传递 默认升序排列
if (rev == undefined) {
rev = 1;
} else {
rev = (rev) ? 1 : -1;
}
return function (a, b) {
a = a[attr];
b = b[attr];
if (a < b) {
return rev * -1;
}
if (a > b) {
return rev * 1;
}
return 0;
};
},
// 去搜索页面
fGoToSearchList(data) {
// console.log(data)
switch (data.type) {
case 'gjNum':
case 'zjNum':
// 组件和工具
this.$router.push({
path: "/digitalBinhu/searchList",
query: { ...data }
});
break;
case 'yytj':
console.log('应用推荐');
this.$router.push({
path: "/digitalBinhu/appRecommend",
query: { ...data }
});
// appRecommend
break;
case 'zxtj':
console.log('最新推荐');
this.$router.push({
path: "/digitalBinhu/newRecommend",
query: { ...data }
});
break;
case 'sqsj':
console.log('申请上架');
// 
this.$router.push({
path: "/digitalBinhu/appGronding",
query: { ...data }
});
// this.$message({
//     message: '正在开发中！',
// });
break;
case 'sjgx1':
console.log('数据共享');
this.$message({
message: '正在开发中！',
});
break;
default:
break;
}
},
}
});
