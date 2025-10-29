<template>
    <!-- 数字滨湖首页 -->
    <div style="margin-bottom: 50px;">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide swiper-slide1">
                    <img class="swiper-slide-img" :src="require('../../assets/image/new/banner.png')" />
                </div>
                <div class="swiper-slide swiper-slide1" v-for="(item, idx) in aTuiJianApp" :key="'aTuiJianApp' + idx">
                    <img class="swiper-slide-img" :src="JSON.parse(item.appScreenshot)[0]" />
                    <div class="swiper-slide-btn" @click="fGoToDetail(item)">立即使用</div>
                </div>
            </div>
            <!-- 如果需要分页器 -->
            <div class="swiper-pagination"></div>
        </div>
        <!-- 轮播图 -->
        <div class="home-banner">
            <div class="box _flex box-margin">
                <div class="all-data-item" v-for="(item, idx) in aAllData" :key="'aAllData' + idx">
                    <img :src="item.image" alt="" class="data-item-logo">
                    <div class="data-item">
                        <div class="data-item-name">{{ item.value }} <span class="data-item-unit"></span></div>
                        <div class="data-item-value">{{ item.name }}({{ item.unit }})</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 内容 -->
        <!-- 顶部数据总览 -->
        <div class="box _flex ">
            <div class="box—bg">
                <template v-for="(item, idx) in aAllDataV2">
                    <div class="all-datav2-item" :key="'aAllDataV2' + idx" v-if="item.show" @click="fGoToSearchList(item)"
                        :class="'_' + item.type">
                        <!-- <div class="datav2-item-logo"></div> -->
                        <div class="datav2-item-name">{{ item.name }}</div>
                        <div class="datav2-item-name">{{ item.value }}</div>
                    </div>
                </template>

            </div>
        </div>

        <!-- 数字底座 -->
        <div class=" box _flex">
            <com-block-t title="数字底座" position="center" class="box-block">
                <div class="box-block-con" v-if="false">
                    <!-- 左边 - 基础信息资源 -->
                    <div class="box-block-l box-jcxxzy block">
                        <div class="box-jcxxzy-tit">基础信息资源</div>
                        <!-- <div class="box-jcxxzy-scroll"></div> -->
                        <vue-seamless-scroll :data="aJcxxzyData" class="box-scroll-wrap " :class-option="defaultOption"
                            v-if="aJcxxzyData.length >= 1">
                            <div class="box-r-item" v-for="(item, idx) in aJcxxzyData" :key="'aJcxxzyData' + idx">
                                <div class="r-item-name text-ellipsis-1" :title="item.table_cn_name">{{ item.table_cn_name
                                }}</div>
                                <div class="r-item-level text-ellipsis-1" :title="item.system_name">名称：{{ item.system_name
                                }}</div>
                                <div class="r-item-source text-ellipsis-1" :title="item.dept_name">来源：{{ item.dept_name }}
                                </div>
                            </div>
                        </vue-seamless-scroll>
                    </div>
                    <!-- 右边 -->
                    <div class="box-block-r">
                        <div class="jcxxzy-item" v-for="(item, idx) in ajcxxzyCount" :key="'ajcxxzyCount' + idx">
                            <div class="jcxxzy-item-tit">{{ item.title }}</div>
                            <div class="jcxxzy-item-box">
                                <div class="item-box">
                                    <div>数据总量</div>
                                    <div class="item-box-val">{{ item.count }}</div>
                                </div>
                                <div class="item-box">
                                    <div>当日新增</div>
                                    <div class="item-box-val">{{ item.add }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-block-list">
                    <com-block-t title="平台能力" class="block-list" position="left" :noline="true">
                        <div class="block-list-item" :class="'_aPtnl' + idx" v-for="(item, idx) in aPtnl"
                            :key="'aPtnl' + idx" @click="fClickNumberBase(item, aPtnl)">
                            {{item.appName }}
                        </div>
                    </com-block-t>
                    <com-block-t title="组件服务" class="block-list" position="left" :noline="true">
                        <div class="block-list-item" :class="'_aZjfw' + idx" v-for="(item, idx) in aZjfw"
                            :key="'aZjfw' + idx" @click="fClickNumberBase(item)">
                            {{ item.appName }}
                        </div>
                    </com-block-t>
                    <com-block-t title="工具支持" class="block-list" position="left" :noline="true">
                        <div class="block-list-item" :class="'_aGjzc' + idx" v-for="(item, idx) in aGjzc"
                            :key="'aGjzc' + idx" @click="fClickNumberBase(item)">
                            {{ item.appName }}
                        </div>
                    </com-block-t>
                </div>
            </com-block-t>
        </div>
        <!-- 场景应用 -->
        <div class="box _flex">
            <com-block-t title="场景应用" position="center" class="box-block">
                <div class="cjyy-box">
                    <div class="cjyy-item" v-for="(item, idx) in aCjyy" :key="'aCjyy' + idx" @click="fGoToSearchList(item)">
                        <div class="cjyy-item-icon" :class="item.icon"></div>
                        <div class="cjyy-item-name">{{ item.name }}</div>
                        <div class="cjyy-item-details">{{ item.details }}</div>
                        <el-button type="primary" class="cjyy-item-btn">立即查看</el-button>
                    </div>
                </div>
            </com-block-t>
        </div>
        <!-- 应用活跃度排名  应用权限申请排名 -->
        <div class="box _flex">
            <com-block-t title="应用访问量排名" tip="截至当前日期统计排名" class="box-block-l" position="left">
                <com-block-b title="" class="box-block _bgcolor">
                    <div class=" zyflpm-chrt">
                        <div class="appmarket-box-item" v-for="(item, idx) in aHydpmData" :key="'aHydpmData' + idx">
                            <div class="box-item">
                                <div class="box-item-icon" :class="'_top' + (idx + 1)">{{ idx + 1 }}</div>
                                <div class="box-item-name">{{ item.name }}</div>
                            </div>
                            <div class="box-item _wid">
                                <div class="box-item-value">访问量：<span class="item-value">{{ item.hyd }}</span></div>
                            </div>
                        </div>
                    </div>
                </com-block-b>
            </com-block-t>
            <com-block-t title="应用权限申请排名" tip="截至当前日期统计排名" class="box-block-r" position="left">
                <com-block-b title="" class="box-block _bgcolor">
                    <div class=" zyflpm-chrt">
                        <comp-chart-jdt ref="dwzypm"></comp-chart-jdt>
                    </div>
                </com-block-b>
            </com-block-t>
        </div>
        <!-- 单位资源排名  热门数据资源 -->
        <div class="box _flex" v-if="false">
            <com-block-t title="单位资源排名" class="box-block-l" position="left">
                <div class="block zyflpm-chrt">
                    <comp-chart-jdt ref="dwzypm"></comp-chart-jdt>
                </div>
            </com-block-t>
            <com-block-t title="热门数据资源" class="box-block-r" position="left">
                <div class="block zyflpm-chrt">
                    <!-- <comp-chart-jdt ref="zyflpm"></comp-chart-jdt> -->
                    <sub-rmsjzy :data="aRmshzyData"></sub-rmsjzy>
                </div>
            </com-block-t>
        </div>
    </div>
</template>

<script>
import { URLS_USERINFO, URLS_SZBH, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js';
import vueSeamlessScroll from 'vue-seamless-scroll';// 滚动容器
import CompChartJdt from '@/components/echarts/chart-jdt.vue'; // 资源分类排名 
import ComBlockT from '@/components/page/comBlockT.vue'; // 块容器
import comBlockB from '@/components/page/comBlockB.vue'; // 块容器-边框

import ComListIndex from '@/components/page/comListIndex.vue'; // 首页数字滨湖的列表
import SubRmsjzy from '@/components/page/rmsjzy.vue'; // 热门数据资源
import axios from 'axios';
import fGetAppListAsync from '@/api/setAppList.js'
import Swiper from 'swiper'; // 注意引入的是Swiper
import 'swiper/css/swiper.min.css' // 注意这里的引入

export default {
    name: '',
    components: {
        vueSeamlessScroll,// 滚动容器
        CompChartJdt, // 资源分类排名 
        ComBlockT,// 块容器
        comBlockB,// 块容器
        ComListIndex, // 首页数字滨湖的列表
        SubRmsjzy,// 热门数据资源
    },
    props: [''],
    data() {
        return {
            oUserInfo: {},// 用户信息
            aAllAppData: [],// 所有应用
            nAllAppComUnitNum: 0, // 所有应用组件工具的数量
            // aBannerImage: [
            //     require('@/assets/image/banner/one.jpg'),
            //     require('@/assets/image/banner/two.jpg'),
            //     require('@/assets/image/banner/three.jpg'),
            //     require('@/assets/image/banner/four.jpg'),
            // ],
            aHydpmData: [],// 应用活跃度排名
            sBannerKeyWords: '', // 轮播图上输入框的关键字
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
                // { name: '数源系统数', unit: '个', value: '342', color: '#0e76ef', image: require('../../assets/image/home/V2/xzcs.png') },
                { name: '平台访问量', unit: '次', value: '', image: require('../../assets/image/home/V2/ptfwl.png') },
                { name: '用户注册数', unit: '次', value: '', color: '#ea9518', image: require('../../assets/image/home/V2/yhzcs.png') },
                // { name: '资源信息项', unit: '个', value: '245', color: '#1a5ffe', image: require('../../assets/image/home/V2/zyxxx.png') },
                { name: '应用服务', unit: '个', value: '', color: '#15c321', image: require('../../assets/image/home/V2/yyfw.png') },
            ],
            aAllDataV2: [
                { name: '应用推荐', value: '', type: 'yytj', module: '应用推荐', show: true, flag: 'banner' },
                { name: '最新上架', value: '', type: 'zxtj', module: '最新上架', show: true, flag: 'new' },
                { name: '平台能力', value: '', type: 'sqsj', module: '应用', show: true, flag: 'app_module' },
                { name: '组件服务', value: '', type: 'zjNum', module: '组件', show: true, flag: 'app_module' },
                { name: '工具支持', value: '', type: 'gjNum', module: '工具', show: true, flag: 'app_module' },
            ],
            aCjyy: [
                {
                    name: '数字经济', type: 'szjj', flag: 'app_type', icon: 'szjjing_home',
                    details: '包括但不限于工业互联网、智慧园区、智能制造、智慧农业、电子商务、智慧仓储物流、人工智能等方向。',
                },
                {
                    name: '数字治理', type: 'szzl', flag: 'app_type', icon: 'szzl_home',
                    details: '包括但不限于智慧城管、智慧停车、城市应急管理、智慧交通、公共安全、市场监管等方向。',
                },
                {
                    name: '数字政务', type: 'szzw', flag: 'app_type', icon: 'szzw_home',
                    details: '包括但不限于政务服务、党建信息资源融合共享、党务管理等方向。',
                },
                {
                    name: '数字生活', type: 'szsh', flag: 'app_type', icon: 'szsh_home',
                    details: '包括但不限于智慧教育、智慧医疗、智慧养老、智慧社区、智慧文旅、智慧生活等方向。',
                },
                {
                    name: '数字生态', type: 'szst', flag: 'app_type', icon: 'szst_home',
                    details: '包括但不限于数字素养、数据安全等方向。',
                },
                {
                    name: '数字基建', type: 'szjjian', flag: 'app_type', icon: 'szjjian_home',
                    details: '包括但不限于CIM、BIM、城市市政基础设施信息系统、5G新基建与传统基建融合发展等方向。',
                },
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
            aRmshzyData: [], // 热门数据资源
            oChangePassWord: {
                account: '',//账号
                oldPassword: '',// 旧密码
                newPassword: '',// 新密码
            },// 修改密码
            bShowAdminBtn: false, // 是否显示管理后台按钮
            // 单位资源排名
            oDwzypm: {
                name: [],
                value: [],
                valueMax: [],
            },
            // 平台能力
            aPtnl: [
                { name: '密码服务平台', type: 'mmfw', id: '', details: { logo: '', appScreenshot: JSON.stringify([require('../../assets/image/pass1.png')]), manageDeptName: '--', contacts: '--', telephone: '--', description: '根据平台的部署方式和实现业务功能，在满足总体性、完备性、经济性原则的基础上，通过使用云密码资源服务、密钥管理系统、SSLVPN服务、数据透明加解密系统、软件密码模块、动态令牌等，以及第三方CA服务、结合智能密码钥匙、国密浏览器来建立完善的密码应用安全保障体系。', } },
                { name: 'PAAS能力平台', type: 'passnl', id: '', details: { logo: '', appScreenshot: JSON.stringify([require('../../assets/image/pass1.png')]), manageDeptName: '--', contacts: '--', telephone: '--', description: '提供标准的PaaS 产品接入标准，可以将PaaS 产品接入到平台进行统一管理，支持容器化托管，提供统一管理集成度；具有接入统一管理数据库服务、统一管理中间件服务等能力，为整体云原生应用开发打下基础。本期PaaS平台包含tdsql，Tencent OS，ckafka，credis。', } },
                { name: '可视化大屏', type: 'kshpt', id: '1671340701423517697', details: {} },
            ],
            // 组件服务
            aZjfw: [
                { name: '视频汇集平台', type: 'sphj', id: '', details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '建设全区统一的视频汇聚平台，整合接入多来源的视频图像资源。制定视频资源接入标准，统一接频资源，根据各部门对视频资源的实际需求，分配相应的调用权限，规范共享授权管理，实现视频入城管、交通、应急等各委办局、乡镇（街道）已建视频监控资源，推动社会单位视频资源接入汇聚平台。统一对全区整合后的视资源集中管理、统一分配、统一控制，保证视频资源效能的最大化。', } },
                { name: '统一运维平台', type: 'tiyw', id: '1665646406557274114', details: {} },
                { name: '数据中台', type: 'sjzt', id: '1673222396863172609', details: {} },
            ],
            // 公开支撑
            aGjzc: [
                { name: 'AI平台', type: 'ai', id: '1665909999420870658', details: {} },
                { name: 'CIM平台', type: 'cim', id: '1671342421583085570', details: {} },
                { name: '低代码平台', type: 'ddm', id: '1685895694549831682', details: {} },
            ],
            bFlag: true,
            aSqData: [],
            aModel: [],
            aTuiJianApp: [],
            aAppModule: [],
            aAppType: [],
        }
    },
    computed: {
        defaultOption() {
            return {
                step: 0.3, // 数值越大速度滚动越快
                limitMoveNum: this.aJcxxzyData.length, // 开始无缝滚动的数据量 this.dataList.length
                hoverStop: true, // 是否开启鼠标悬停stop
                autoPlay: this.aJcxxzyData.length > 11 ? true : false,
                direction: 1, // 0向下 1向上 2向左 3向右
                openWatch: true, // 开启数据实时监控刷新dom
                singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
                singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
                waitTime: 2000 // 单步运动停止的时间(默认值1000ms)
            }
        }
    },
    watch: {},
    created() {
        this.fRequestApphyd();
        this.fGetApplyForStatistics()
        this.fRequestAllppV1();
    },
    mounted() {
        this.fRequestUserInfo();// 请求用户信息

        // this.$nextTick(() => {
        //     this.aJcxxzyData = this.fSetLen(this.aJcxxzyData)
        // })
        this.fSeiperInit()
       
    },
    beforeDestroy() {

    },
    methods: {

        fGoToDetail(item) {
            let oObj = this.$fSetData(item, ['appSecret'])
            localStorage.setItem('appDeatils', JSON.stringify(oObj))

            this.$router.push({
                path: "/appMarket/appDetails",
                // query: { data: JSON.stringify(oObj) }
            })
        },
        fSeiperInit() {
            new Swiper(".swiper-container", {
                loop: true,
                mousewheel: false,
                autoplay: true,
                keyboard: true,
                observer: true,
                observeParents: true,
                delay: 1000,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                },
            });
        },
        // 请求集合
        fRequest() {
           
            this.fRequestApphyd();// 请求应用活跃度
            this.fRequestBannerData();// 平台访问次数
            // this.fRequestApplicationApplyForList();//根据申请人查询应用申请
            this.fRequestAuthorityApplyForList();
            this.fGetModuleCouts();
            this.fGetTuiJianApp();
            this.fGetAppType();
            this.fGetAppModule();
            this.fRequestCount();
            this.fGetApplyForStatistics();// 请求应用申请量排名
            setTimeout(() => {
                this.fRequestAllpp();// 请求我的应用
                this.fRequestAllppV1();
            }, 500);
            this.$fRequest({ // 已读
                method: 'post',
                url: this.$ip.server_ocn + 'system/notice/list?pageNum=1&pageSize=999999',
                data: {
                    read: '0'
                },
            }).then(res1 => {
                localStorage.setItem('messageTotal', res1.total || 0)
                this.$EventBus.$emit('setTotal', res1.total || 0)
            })
        },

        // 请求应用申请量排名
        async fGetApplyForStatistics() {
            let res = await this.$fRequest({
                url: this.$ip.server_ocn + 'api/getApplyForRank'
            })
            // console.log('======>>>>>>请求应用申请量排名', res.data.sort(this.fSetDataSort('applyForNum', false)));
          res.data.forEach(item => {
            item.applyForNum = Number(item['value'])
          })
          let arr = res.data.sort(this.fSetDataSort('value', false))
          let name = []
          let value = []
          arr.forEach((item, idx) => {
            if (idx < 8) {
              name.push(item.name)
              value.push(item.applyForNum)
            }
          })
            this.$refs.dwzypm.fSetData(
                {
                    value,
                    name
                },
                {
                    numColor: '#FEFFB1',
                    nameColor: '#72F587',
                    color: {
                        star: '#6BF585',
                        end: '#FEFFB1',
                    }
                })
        },
        // 请求字典 应用模块
        fGetAppModule() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'system/dict/data/type/app_module'
            }).then(res => {
                this.aAppModule = res.data || []
            })
        },
        // 请求字典 应用分类
        fGetAppType() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'system/dict/data/type/app_type'
            }).then(res => {
                this.aAppType = res.data || []
            })
        },
        // 获取推荐的应用的轮播图
        async fGetTuiJianApp() {
            let res = await this.$fRequest({
                url: this.$ip.server_ocn + 'cmApp/getTuiJian',
            })
            this.aTuiJianApp = res.data.splice(0, 3) || [];
            this.$nextTick(() => {
                this.fSeiperInit();
            })
            // console.log(res, 2222)
        },
        fGetModuleCouts() {
            this.aAllData[2].value = 0
            this.$fRequest({
                url: this.$ip.server_ocn + 'cmApp/moduleCouts',
            }).then(res => {
                res.data.forEach(item => {
                  // if (item.appmodule == '应用' || item.appmodule == '工具' || item.appmodule == '组件') {
                  //   this.aAllData[2].value += item.counts
                  // }
                    this.aAllDataV2.forEach(data => {
                        if (item.appmodule == data.module) {
                            data.value = item.counts
                        }
                    })
                })
            })
        },
        // 请求已上架的应用
        fGetAllAppList(item) {
            this.$fRequest({
                method: 'post',
                url: this.$ip.server_ocn + 'cmApp/allAppList',
                data: {
                    // appModule: item.id,
                    // shangjiaTime: '0', // 上架时间
                }
            }).then(res => {
                this.aAllData[2].value = res.data.length
                let arr1 = []
                let arr2 = []
                let arr3 = []
                res.data.filter(x=> x.show ==="2").forEach(item => {
                    switch (item.appModule) {
                        case '1669301899511230465':  arr1.push(item)
                            break;
                        case '1669301825683091458':  arr2.push(item)
                            break;
                        case '1669301968486559746':  arr3.push(item)
                            break;
                    }
                })
                this.aZjfw = arr1.splice(0,3)
                this.aGjzc = arr2.splice(0,3);
                this.aPtnl = arr3.splice(0,3);

            })
        },
        fFalseReq(oUserInfo) {
            this.oUserInfo = oUserInfo
            this.$store.commit('fSetUserInfo', oUserInfo)
            this.oChangePassWord.account = this.oUserInfo.account;
            this.fCheckInitPassword();// 验证用户密码是否是初始密码
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
                    current: 1,// 当前页
                    size: 10,// 每页数量
                }
            }).then(res => {
                // console.log(res, '根据申请人查询应用申请')
                // this.aAllDataV2[2].value = res.data.total;
                // this.aSjApp = res.data.records;
            })
        },
        // 请求用户信息
        fRequestUserInfo() {
            // alert(111)
            if (localStorage.getItem('saber-userInfo')){
              localStorage.setItem('userInfo',localStorage.getItem('saber-userInfo'))
            }
            let oUserInfo = JSON.parse(localStorage.getItem('userInfo'))||null ;
            // console.log(oUserInfo)
            let oData = {};
            if (oUserInfo && oUserInfo.account) {
                // oData.account = oUserInfo.account
                // this.fFalseReq(oUserInfo);
                this.oUserInfo = oUserInfo
                this.$store.commit('fSetUserInfo', oUserInfo)
                this.oChangePassWord.account = this.oUserInfo.account;
                this.$EventBus.$emit('fGetUserBtn', oUserInfo);
                if (localStorage.getItem('code') && localStorage.getItem('code') === new URL(location.href).searchParams.get('code')) {
                    // 如果本地缓存和地址栏code相等，就不请求用户信息（原因：code只能使用一次）
                    this.fRequest();
                } else {
                    this.fRequestLogin();// 重新获取用户信息
                }
                return
            } else {
                // oData.code = this.$store.state.sCode;
                this.fRequestLogin();// 重新获取用户信息
            }
            // this.$router.go(0)
        },
        // 重新获取用户信息
        fRequestLogin() {
            let oData = {};
            oData.code = new URL(location.href).searchParams.get('code')
            // alert(this.$store.state.sCode)
            this.$fRequest({
                url: URLS_USERINFO.u_getCasUserInfo,
                data: { ...oData }
            }).then(res => {
                localStorage.setItem('code', new URL(location.href).searchParams.get('code'))
                console.log(res, '用户信息')
             if (res.msg == '授权码无效') {
               this.fLoginOut();
               return;
             }
                localStorage.setItem('userInfo', JSON.stringify(res.data.userInfo))
                // console.log(res);
                this.oUserInfo = res.data.userInfo
                this.$EventBus.$emit('fGetUserBtn', this.oUserInfo);

                this.$store.commit('fSetUserInfo', res.data.userInfo)
                this.oChangePassWord.account = this.oUserInfo.account;
                // this.fCheckInitPassword();// 验证用户密码是否是初始密码
                // this.fRequestAllpp();// 请求我的应用
                this.fRequest();
            })
        },
        // 根据申请人查询权限申请
        fRequestAuthorityApplyForList(len) {
            this.$fRequest({
                url: URLS_USERINFO.u_authorityApplyForList,
                data: {
                    applicant: this.oUserInfo.account,
                    // keyword: this.sMyAppKeyWord,
                    current: 1,// 当前页
                    size: 10,// 每页数量
                }
            }).then(res => {
                // console.log(res, '根据申请人查询权限申请')
                if (res.code == 200) {
                    this.aSqData = res.data.records;
                }
            })
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
                this.fRequest();
            })
        },
        // 申请上架的列表
        fGetSqsjList(replayStatus) {
            let params = null
            let obj = null;
            if (replayStatus != null && replayStatus != undefined) {
                obj = JSON.stringify({ "appType": "", "status": 1, "params": { "startDate": "", "endDate": "" } })
            } else {
                params = { 'own': 1 }
                obj = JSON.stringify({ params: params })
            }
            $.ajax({
                url: `https://2.21.138.89:11511/cmApp/replayList?pageNum=${1}&pageSize=${999}`,
                method: 'post',
                headers: {
                    'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    // "Content-Type": "application/json"
                },
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: obj
            }).then(res => {
                // debugger
                // console.log(res)
                this.aRecommend = res.data.records;
                this.aRecommendCopy = res.data.records;
                // this.aAllDataV2[2].value = this.aRecommendCopy.length;
            })
        },
        async fRequestAllpp() {
            const res = await fGetAppListAsync();
            this.fGetSqsjList(1);
            // console.log(res)
            // console.log(this.fGetLastWeekData(res))
            // this.aAllDataV2[1].value = this.fGetLastWeekData(res).length
            let aData = res.filter(item => item.sTypeName == '应用');
            let aAllData = res
            this.$store.commit('fSetAllAppDataRes', res.filter(item => item.sTypeName == '应用'));
            this.$store.commit('fSetComUtilsData', [...res.filter(item => item.sTypeName == '组件'), ...res.filter(item => item.sTypeName == '工具')]);
            // this.aAllDataV2[4].value = res.filter(item => item.sTypeName == '组件').length
            // this.aAllDataV2[5].value = res.filter(item => item.sTypeName == '工具').length


            // this.fSetDetails(aAllData, 'aPtnl');// 平台能力
            // this.fSetDetails(aAllData, 'aZjfw');// 组件服务
            // this.fSetDetails(aAllData, 'aGjzc');// 工具支撑

            // let aComData = res.filter(item => item.sTypeName.indexOf('应用') >= 0);// 获取所有组件
            // aComData.forEach(item => {
            //     for (const key in this.oCjyyNum) {
            //         if (key === item.title) {
            //             this.oCjyyNum[key] = item.children?.length || 0
            //         }
            //     }
            // })
            // setTimeout(() => {
            this.$nextTick(() => {
                // console.log('====>>>>', aData.filter(item => item.status == 0))
                this.aAllAppData = JSON.parse(JSON.stringify(aData.filter(item => item.status == 0)));
            })
            // }, 100);
            // this.aAllDataV2[0].value = this.aAllAppData.filter(item => item.recommendFlag == 0).length
            this.nAllAppComUnitNum = aAllData.length;
            // this.aAllDataV2[4].value = aAllData.length;
            this.$store.commit('fSetAllAppData', aData);// 请求全部应用
            this.$store.commit('fSetAllApp', aData);// 请求全部应用
            this.fRequestMyApp(aAllData.length);// 请求我的应用

        },
        // 获取最近一周的数据
        fGetLastWeekData(data) {
            let aData = []
            data.forEach(item => {
                if (new Date().getTime() - new Date(item.createTime).getTime() <= 604800000) {
                    aData.push(item)
                }
            });
            return aData
            return data.map(item => {
                // console.log(new Date().getDay())
                // console.log(new Date(item.createTime).getTime())
                if (new Date().getTime() - new Date(item.createTime).getTime() <= 604800000) {
                    return item
                }
            })
        },
        async fRequestModel() {
            let oUserInfo = JSON.parse(localStorage.getItem('userInfo')) || null;
            if (!oUserInfo) return
            let res = await this.$fRequest({
                url: this.$ip.server_ocn + 'system_server/api/addApplicationClassification'
            })
            this.aModel = res.data
            localStorage.setItem('aModel', JSON.stringify(this.aModel))
            this.aModel.forEach(item => {
                this.fGetAllAppList(item);// 请求已上架的应用
            })
        },
        fGetModelName(id, data, type) {
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
        },
        // 请求所有应用 / 工具 / 组件
        fRequestAllppV1() {
            axios.get('https://2.21.138.89:11511/cmApp/list', {
                params: {
                    pageNum: 1,
                    pageSize: 99999,
                }
            }).then(res => {
                // if (localStorage.getItem('aModel')) {
                //     this.aModel = JSON.parse(localStorage.getItem('aModel'))
                // } else {

                // }
                this.fRequestModel()
                res.data.data.records.forEach(item => {
                    item.sTypeName = this.fGetModelName(item.appModule, this.aModel)
                    item.sType = this.fGetModelName(item.appType, this.aModel, 'children')
                    item.isRecommend = item.recommendFlag; // 是否推荐
                    item.title = item.appName; // 应用名称
                    // item.deptName = item.appName; // 单位名称
                    item.url = item.appLink; // 应用地址
                    item.logo = item.appPic;// 应用图标
                    item.icon = item.appScreenshot ? JSON.parse(item.appScreenshot).join(',') : '';
                    item.userColumn = item.remark; // 应用简介
                    item.description = item.remark; // 应用简介
                    item.OCN = true; // OCN的应用
                })
                // this.fSetDetails(res.data.data.records, 'aPtnl');// 平台能力
                // this.fSetDetails(res.data.data.records, 'aZjfw');// 组件服务
                // this.fSetDetails(res.data.data.records, 'aGjzc');// 工具支撑
                this.aAllAppData = res.data.data.records.filter(item => item.status == 0)
                // this.aAllDataV2[0].value = this.aAllAppData.filter(item => item.isRecommend == '0').length
                // this.aAllDataV2[4].value = this.aAllAppData.filter(item => item.sTypeName == '组件').length
                // this.aAllDataV2[5].value = this.aAllAppData.filter(item => item.sTypeName == '工具').length
            })
        },
        // 数字底座跳转
        fClickNumberBase(data, arr) {
            let oObj = this.$fSetData(data, ['appSecret'])
            localStorage.setItem('appDeatils', JSON.stringify(oObj))
            this.$router.push({
                path: "/appMarket/appDetails",
            })
        },
        // 设置应用详情
        fSetDetails(data, sDataName) {
            data.forEach(item => {
                this[sDataName].forEach(name => {
                    // console.log(item.casAppId)
                    if (item.casAppId == name.id) {
                        name.details = { ...item, showBtn: true };
                    }
                })
            })
            this[sDataName] = JSON.parse(JSON.stringify(this[sDataName]))
        },
        // 请求底座超市总计登录数量
        fRequestBannerData() {
            this.$fRequest({
                url: URLS_SZDZ.u_baseSupermarket,
            }).then(res => {
                this.aAllData[0].value = res.data.totalNum;
                // this.aTopData[0].value = res.data[0].field;
                // this.aRmshzyData = res.data;
            })
        },
        //请求轮播图下数据
        fRequestCount(){
            this.$fRequest({
                url: URLS_SZBH.u_first_count,
            }).then(res=>{
                if(res.code == 200){
                    this.aAllData.map(item=>{
                        if(item.name == "用户注册数"){
                            item.value = res.data;
                        }
                    })
                }
            })
        },
        // 请求当前登录用户的应用
        fRequestMyApp(len) {
            this.$fRequest({
                url: URLS_USERINFO.u_userApplication,
                data: {
                    account: this.oUserInfo.account,
                    keyword: '',
                    current: 1,// 当前页
                    size: len + 50,// 每页数量
                }
            }).then(res => {
                // console.log(res.data[0].appList.records)
                // console.log(res.data[0].appList.records.sort(this.fSetDataSort('loginNumber', false)))
                this.$store.commit('fSetAllAppComUtil', res.data[0].appList.records)
                // 
                this.bShowAdminBtn = false;
                this.$EventBus.$emit('fShowAdminBtn', this.oUserInfo.adminFlag)
                window.localStorage.setItem('bShowAdminBtn', this.oUserInfo.adminFlag)
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
                this.$store.commit('fSetUserAppData', res.data[0].appList.records.sort(this.fSetDataSort('loginNumber', false)))
            })
        },
        // 请求应用活跃度
        fRequestApphyd() {
            const url = this.$ip.server_ocn + 'api/getAppUseRank'
            this.$fRequest({
                url: url
            }).then(res => {
              this.aHydpmData = [];
              res.data.forEach(item => {
                item.applyForNum = Number(item['number'])
              })
              let arr = res.data.sort(this.fSetDataSort('number', false))

              arr.forEach((item, idx) => {
                if (idx < 10) {
                this.aHydpmData.push({
                  name: item.name,
                  hyd: item.applyForNum,
                  //level: item.rankChange >= 0 ? 'top' : 'bot',
                  num: item.applyForNum
                })
                }
              })
                // res.data.forEach((item,index) => {
                //   if(index>9){
                //     return
                //   }
                //     this.aHydpmData.push({
                //         name: item.name,
                //         hyd: item.number,
                //         //level: item.rankChange >= 0 ? 'top' : 'bot',
                //         num: item.number
                //     },)
                // })
            })
        },

        // 退出登录
        fLoginOut() {
            window.location.replace('https://2.21.138.89:11511/system-web/logout');
            localStorage.removeItem('userInfo');
            localStorage.clear();
            sessionStorage.clear();
            window.close();
            return
            localStorage.removeItem('userInfo');
            localStorage.clear();
            sessionStorage.clear();
            // if (this.$router.history.current.indexOf) {

            // }
            this.$router.go(-this.$router.history.current.indexOf)
            // window.location.replace('https://2.21.138.89:11511/system-web/logout');
            // axios.get('https://2.21.138.89:11511/system-web/logout').then(res=>{
            //     window.location.replace('https://2.21.138.89:11511/api/login');
            // })
            window.location.replace('http://2.22.51.26:30002/logout?logoutUrl=https://2.21.138.89:11511/api/login');
            // window.open('http://2.21.137.200:8081/logout?logoutUrl=', '_self');
            // window.close();
            // window.open('https://2.21.138.89:11511/api/login')
        },
        // 查看详情
        fClickAppDetails(data) {
            // console.log(data)
            let oObj = this.$fSetData(data, ['appSecret'])
            localStorage.setItem('appDeatils', JSON.stringify(oObj))

            this.$router.push({
                path: "/appMarket/appDetails",
                // query: { data: JSON.stringify(oObj) }
            })
        },
        // 设置所有组件应用工具的集合
        fSetStoreAllApp(data) {
            let aData = []
            data?.children.forEach(item => {
                if (item.children && item.children.length > 0) {
                    aData.push(...item.children)
                }
            })
            return aData;
        },
        // 设置数组长度
        fSetLen(data) {
            // console.log(data)
            if (data.length < 12) {
                this.$nextTick(() => {
                    this.fSetLen([...data, ...data])
                })
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
            })
        },
        // 跳转页面
        fChangePage(type) {
            return
            // this.$store.commit('fSetAppMarket', type)
            // this.$store.commit('fSetNowPage', { type: 'appmarket', page: 'appmarket', component: 'sub-appmarket', id: 'vital1' });
            this.$router.push({
                path: "/appmarket",
                query: { type, id: 'vital1' }
            })
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
            }
        },
        // 去搜索页面
        fGoToSearchList(data) {
            console.log(data, '')
            switch (data.flag) {
                case 'app_module':
                    data.id = this.aAppModule.filter(item => item.dictLabel == data.name)[0].dictValue
                    this.$router.push({
                        path: "/appMarket",
                        query: { ...data }
                    })
                    break;
                case 'app_type':
                    data.id = this.aAppType.filter(item => item.dictLabel == data.name)[0].dictValue
                    this.$router.push({
                        path: "/appMarket",
                        query: { ...data }
                    })
                    break;
                case 'new':
                case 'banner':
                    this.$router.push({
                        path: "/digitalBinhu/searchList",
                        query: { ...data }
                    })
                    break;
                default:
                    break;
            }

            return
        },
    }
}
</script>

<style lang='less' scoped>
.swiper-container {
    width: 100%;
    //height: 500px;

}

.swiper-pagination {
    margin-bottom: 50px;
}

/deep/.swiper-pagination-bullet {
    width: 30px;
    height: 8px;
    border-radius: 0%;
}

.swiper-wrapper,
.swiper-slide {
    position: relative;
    width: 100%;
    height: 100%;
    //background: #2490FC;
}

.swiper-slide-img {
    width: 100%;
    height: 100%;
    background-size: 100%;
}

.swiper-slide-btn {
    position: absolute;
    left: 190px;
    top: 370px;
    width: 160px;
    height: 50px;
    background-color: #1594f5;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
}

.box-margin {
    position: relative;
    // left: 0;
    // bottom: -180px;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin: -20px auto 0;
    padding: 0;
    width: 100%;
    height: 150px;
    background: #FFFFFF;
    box-shadow: 0px 4px 14px 0px rgba(59, 112, 192, 0.23);
    border-radius: 8px;
    z-index: 99;
}

.home-banner {
    position: relative;
    width: 80%;
    height: 151px;
    margin:0 auto;
    // background: url('../../assets/image/home/bg.png') no-repeat center / 100% 617px;
}

.block {
    width: 813px;
    min-height: 300px;
    padding: 20px 0;
}

.appmarket-box-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 11%;
    margin-bottom: 10px;
    padding: 0 40px 0 20px;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
    background: #FFFFFF;
    border-radius: 3px;
}

.box-item {
    display: flex;
    align-items: center;
    color: rgba(0, 0, 0, 0.9);
    font-size: 18px;

    &._wid {
        width: calc(100% - 380px);
    }
}

.box-item-icon {
    width: 26px;
    height: 26px;
    margin-right: 17px;
    line-height: 26px;
    text-align: center;
    font-size: 16px;
    color: rgba(255, 255, 255, 0.9);
    border-radius: 7px;
    background: rgba(188, 188, 188, 0.39);

    &._top1 {
        background: #FC4124;
    }

    &._top2 {
        background: #2490FC;
    }

    &._top3 {
        background: #4FC44F;
    }
}

.box-item-name {
    width: 380px;
    color: rgba(0, 0, 0, 0.9);
    font-size: 18px;
}

.box-item-value {
    width: 170px;
    font-size:18px;
}

.item-value {
    color: #266FE8;
}

.search-wrap {
    display: flex;
    align-items: center;
    // justify-content: center;
    flex-direction: column;
    padding-top: 102px;
    position: absolute;
    top: 0%;
    left: 0%;
    height: 100%;
    width: 100%;
    z-index: 999;
    font-weight: bold;
}

.search-title {
    color: #FFFFFF;
    font-size: 40px;
}

/deep/.el-input__inner {
    height: 60px;
}

/deep/.el-button {
    height: 50px;
    background-color: #26acf7 !important;
    color: #FFFFFF !important;
}

.all-data-item {
    display: flex;
    align-items: center;
    justify-content: space-around;
    width: 17%;
    height: 100px;
    // background-color: #f6fbff;
}

.data-item-logo {
    width: 52px;
    height: 52px;
    margin-right: 16px;
    background-size: cover;
}

.data-item {
    width: 180px;
}

.data-item-name {
    // margin-bottom: 10px;
    font-size: 30px;
    color: #000;
    font-weight: bold;
    font-family: DINAlternate-Bold, DINAlternate;
}

.data-item-value {
    font-size: 16px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #999999;
}

.data-item-unit {
    color: #5c5d5e;
    font-size: 16px;
    font-weight: 500;
}

.box—bg {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 130px;
    padding: 30px 0;
    // margin-bottom: 30px;
    // background: linear-gradient(to right, #ceddf6, #fcfffe, #b1eff8);
}

.all-datav2-item {
    display:flex;
    flex-direction: column;
    padding: 30px 0 0 10px;
    height: 140px;
    width: 15%;
    color: #fff;
    cursor: pointer;
    user-select: none;
    background: no-repeat center / 110%;

    &._gjNum {
        background-image: url('../../assets/image/home/gjNum.png');
    }

    &._sjgx1 {
        background-image: url('../../assets/image/home/sjgx1.png');
    }

    &._sqsj {
        background-image: url('../../assets/image/home/sqsj.png');
    }

    &._yytj {
        background-image: url('../../assets/image/home/yytj.png');
    }

    &._zjNum {
        background-image: url('../../assets/image/home/zjNum.png');
    }

    &._zxtj {
        background-image: url('../../assets/image/home/zxtj.png');
    }
}

.datav2-item-name {
    font-size: 20px;
    font-family: PingFangSC-Medium, PingFang SC;
    text-shadow: 0px 1px 0px rgba(7, 7, 7, 0.4);
}

.box-block {
    width: 100%;
}

._bgcolor {
    padding: 0 !important;
}

.box-block-con {
    display: flex;
    // align-items: center;
    justify-content: space-between;
}

.box-block-list {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.block-list {
    width: 32%;
    height: 700px;
    // min-width: 400px;
}

.block-list-item {
    width: 100%;
    height: 180px;
    margin-bottom: 20px;
    padding: 27px 0 0 54px;
    font-size: 24px;
    color: #FFFFFF;
    font-family: AlibabaPuHuiTiH;
    text-shadow: 0px 0px 14px #2555E8;
    background: no-repeat center center / 105% 220px;
    overflow: hidden;
    background-size: 105% 240px;
    overflow: hidden;
    border-radius: 10px;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._aPtnl0 {
        background-image: url('../../assets/image/home/base/mmfw.png');
    }

    &._aPtnl1 {
        background-image: url('../../assets/image/home/base/passnl.png');
    }

    &._aPtnl2 {
        background-image: url('../../assets/image/home/base/kshpt.png');
    }

    &._aZjfw0 {
        background-image: url('../../assets/image/home/base/sphj.png');
    }

    &._aZjfw1 {
        background-image: url('../../assets/image/home/base/tiyw.png');
    }

    &._aZjfw2 {
        background-image: url('../../assets/image/home/base/sjzt.png');
    }

    &._aGjzc0 {
        background-image: url('../../assets/image/home/base/ai.png');
    }

    &._aGjzc1 {
        background-image: url('../../assets/image/home/base/cim.png');
    }

    &._aGjzc2 {
        background-image: url('../../assets/image/home/base/ddm.png');
    }
}

.box-block-l,
.box-block-r {
    width: 49.5%;
}

.box-jcxxzy {
    padding: 0;
    height: 750px;
    overflow: hidden;
}

.box-jcxxzy-tit {
    height: 40px;
    line-height: 40px;
    font-size: 25px;
    text-align: center;
    color: #252525;
    background: #f2f2f2;
}

.box-scroll-wrap {
    height: 700px;
    overflow: hidden !important;
}

.box-r-item {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 59px;
    width: 813px;
    padding: 0 69px 0;
    margin-bottom: 5px;
    border-radius: 3px;
    border: 1px solid rgba(112, 112, 112, 0.5608);
    background: rgba(255, 255, 255, 0.39);
    font-size: 20px;
    font-weight: 300;
    color: rgba(0, 0, 0, 0.9);

    &::after {
        content: '';
        position: absolute;
        top: 25px;
        left: 26px;
        height: 10px;
        width: 10px;
        background: #1594f5;
    }
}

.r-item-name {
    width: 200px;
    font-weight: 500;
}

.r-item-level {
    width: 200px;
}

.r-item-source {
    width: 250px;
}


.jcxxzy-item {
    height: 240px;
    width: 100%;
    background-color: #5a85b8;
    margin-bottom: 12px;
}

.jcxxzy-item-tit {
    text-align: center;
    font-size: 28px;
    font-weight: bold;
    color: #fff;
    line-height: 130px;
}

.jcxxzy-item-box {
    display: flex;
    align-items: center;
    justify-content: space-around;
    color: #e4e8f0;
    font-size: 25px;
}

.item-box-val {
    text-align: center;
    font-weight: bold;
    font-size: 35px;
    color: #b9defd;
}

.zyflpm-chrt {
    height: 560px;
}

.cjyy-box {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    justify-content: space-around;
}

.cjyy-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    width: 32%;
    height: 380px;
    background: #fff;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.15);
    margin-bottom: 20px;
    box-sizing: border-box;
    padding: 10px 30px;
    font-size: 14px;
}

.cjyy-item-icon {
    width: 100px;
    height: 100px;
    background-color: #1594f5;
    border-radius: 50%;

    &.szjjian_home {
        background: url('../../assets/image/new/szjjian_home.png') no-repeat center / 100px 100px;
    }

    &.szjjing_home {
        background: url('../../assets/image/new/szjjing_home.png') no-repeat center / 100px 100px;
    }

    &.szsh_home {
        background: url('../../assets/image/new/szsh_home.png') no-repeat center / 100px 100px;
    }

    &.szst_home {
        background: url('../../assets/image/new/szst_home.png') no-repeat center / 100px 100px;
    }

    &.szzl_home {
        background: url('../../assets/image/new/szzl_home.png') no-repeat center / 100px 100px;
    }

    &.szzw_home {
        background: url('../../assets/image/new/szzw_home.png') no-repeat center / 100px 100px;
    }
}

.cjyy-item-name {
    font-size: 26px;
    font-weight: bold;
}

.cjyy-item-btn {
    font-size: 16px;
    width: 100px;
    display:flex;
    justify-content: center;
    align-items: center;
}
</style>