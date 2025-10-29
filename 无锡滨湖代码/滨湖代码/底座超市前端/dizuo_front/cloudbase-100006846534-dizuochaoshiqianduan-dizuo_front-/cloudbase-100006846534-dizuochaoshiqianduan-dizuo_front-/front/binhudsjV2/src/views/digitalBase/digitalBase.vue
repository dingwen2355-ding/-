<template>
    <!-- 数字底座 -->
    <div>
        <!-- 顶部数据 -->
        <div class="home-banner">
            <!-- 轮播图搜索框 -->
            <div class="search-wrap">
                <div class="search-title">数字底座</div>
                <div class="appmarket-banner-detail">
                    <div class="banner-detail">
                        数字底座涵盖智慧城市或城市数字化发展所需的基础网络、感知神经元、具有数据处置和智能能力的一系列平台，为政府提供可靠、安全、连续性的技术基础环境，
                        帮助政府实现数据的统一存储、分析、管理和应用，同时提高政府中的协同能力和决策效率。
                    </div>
                </div>
                <!-- <el-input placeholder="请输入你要查找的内容" v-model="sBannerKeyWords" class="search-input">
                    <el-button type="primary" slot="append" icon="el-icon-search" @click="fClickSearch()">搜索</el-button>
                </el-input> -->
                <!-- <div class="search-keyword">关键字：人口 建筑 企业</div> -->
            </div>
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

        <!-- 顶部数据总览 -->
        <!-- <div class="box _flex">
            <div class="all-data-item" v-for="(item, idx) in aAllData" :key="'aAllData' + idx">
                <img :src="item.image" alt="" class="data-item-logo">
                <div class="data-item">
                    <div class="data-item-name">{{ item.name }} <span class="data-item-unit">({{ item.unit }})</span></div>
                    <div class="data-item-name" :style="{ color: item.color }">{{ item.value }}</div>
                </div>
            </div>
        </div> -->

        <!-- 资源分类 -->
        <!-- <div class=" box _flex">
            <com-block-t title="资源分类" position="center" more="resource" @fClickMore="fClickMore" class="box-block">
                <div class="numberbottom-sjzy">
                    <div class="numberbottom-sjzy-item" v-for="(item, idx) in aSjzyflData" :key="'aSjzyflData' + idx">
                        <img :src="item.image" :alt="item.name">
                        {{ item.name }}
                    </div>
                </div>
            </com-block-t>
        </div> -->

        <!-- 平台能力 -->
        <div class=" box _flex">
            <com-block-t title="平台能力" position="center" @fClickMore="fClickMore" class="box-block">
                <sub-bottom-block :data="oPtnl"></sub-bottom-block>
            </com-block-t>
        </div>


        <!-- 最新组件 -->
        <div class=" box _flex">
            <com-block-t title="最新组件" position="center" more="compoents" @fClickMore="fClickMore" class="box-block">
                <sub-bottom-block :data="oRmzysj"></sub-bottom-block>
            </com-block-t>
        </div>

        <!-- 最新工具 -->
        <div class=" box _flex">
            <com-block-t title="最新工具" position="center" more="unit" @fClickMore="fClickMore" class="box-block">
                <sub-bottom-block :data="oTool"></sub-bottom-block>
            </com-block-t>
        </div>

    </div>
</template>

<script>
import SubBottomBlock from '@/components/page/bottom-block.vue'; // 热门数据资源  工具
import ComBlockT from '@/components/page/comBlockT.vue'; // 块容器
import { URLS_USERINFO, URLS_SZDZ } from '@/api/url.js';
import fGetAppListAsync from '@/api/setAppList.js'

export default {
    name: '',
    components: {
        SubBottomBlock,// 热门数据资源
        ComBlockT,// 块容器
    },
    props: [''],
    data() {
        return {
            // banner 图上的数据
            aBannerData: [
                { name: '累计访问量', unit: '人次', value: '', key: 'totalNum' },
                { name: '今日访问量', unit: '人次', value: '', key: 'dayNum' },
            ],
            sBannerKeyWords: '',// 搜索框文字
            // 数据总览
            aAllData: [
                { name: '数据总量', unit: '条', value: '245634', color: '#0e76ef', image: require('../../assets/image/home/V2/sjzl.png') },
                { name: '有条件共享', unit: '条', value: '232', color: '#ea9518', image: require('../../assets/image/home/V2/ytjgx.png') },
                { name: '无条件共享', unit: '条', value: '31', color: '#ea9518', image: require('../../assets/image/home/V2/wtjgx.png') },
                { name: '不予共享', unit: '条', value: '3', color: '#1a5ffe', image: require('../../assets/image/home/V2/bygx.png') },
                { name: '数据申请', unit: '次', value: 45, color: '#15c321', image: require('../../assets/image/home/V2/sjsq.png') },
            ],
            // 数据资源分来
            aSjzyflData: [
                { name: '财政金融', type: 'jykj', image: require('../../assets/image/numberbottom/czjr.png') },
                { name: '城建住房', type: 'cjzf', image: require('../../assets/image/numberbottom/cjzf1.png') },
                { name: '地理空间', type: 'dlkj', image: require('../../assets/image/numberbottom/dlkj1.png') },
                { name: '法律服务', type: 'flfw', image: require('../../assets/image/numberbottom/flfw.png') },
                { name: '公共安全', type: 'ggaq', image: require('../../assets/image/numberbottom/ggaq1.png') },
                { name: '教育文化', type: 'jywh', image: require('../../assets/image/numberbottom/jywh.png') },
                { name: '社保就业', type: 'sbjy', image: require('../../assets/image/numberbottom/sbjy1.png') },
                { name: '社会企业', type: 'shqy', image: require('../../assets/image/numberbottom/shqy.png') },
                { name: '生活服务', type: 'shfw', image: require('../../assets/image/numberbottom/shfw.png') },
                { name: '生态环境', type: 'sthj', image: require('../../assets/image/numberbottom/sthj.png') },
                { name: '信用服务', type: 'xyfw', image: require('../../assets/image/numberbottom/xyfw1.png') },
                { name: '知识产权', type: 'zscq', image: require('../../assets/image/numberbottom/zscq.png') },
            ],
            // 平台能力
            oPtnl: {
                title: '平台能力',
                sRmzyType: 'ptnl',
                aRmsjzyData: [
                    {
                        name: '密码服务平台',
                        title: '密码服务平台',
                        description: '根据平台的部署方式和实现业务功能，在满足总体性、完备性、经济性原则的基础上，通过使用云密码资源服务、密钥管理系统、SSLVPN服务、数据透明加解密系统、软件密码模块、动态令牌等，以及第三方CA服务、结合智能密码钥匙、国密浏览器来建立完善的密码应用安全保障体系。',
                        deptName: '滨湖区技术单位',
                        details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '根据平台的部署方式和实现业务功能，在满足总体性、完备性、经济性原则的基础上，通过使用云密码资源服务、密钥管理系统、SSLVPN服务、数据透明加解密系统、软件密码模块、动态令牌等，以及第三方CA服务、结合智能密码钥匙、国密浏览器来建立完善的密码应用安全保障体系。', }
                    },
                    {
                        name: 'PAAS能力平台',
                        title: 'PAAS能力平台',
                        description: '提供标准的PaaS 产品接入标准，可以将PaaS 产品接入到平台进行统一管理，支持容器化托管，提供统一管理集成度；具有接入统一管理数据库服务、统一管理中间件服务等能力，为整体云原生应用开发打下基础。本期PaaS平台包含tdsql，Tencent OS，ckafka，credis。',
                        deptName: '滨湖区技术单位',
                        details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '提供标准的PaaS 产品接入标准，可以将PaaS 产品接入到平台进行统一管理，支持容器化托管，提供统一管理集成度；具有接入统一管理数据库服务、统一管理中间件服务等能力，为整体云原生应用开发打下基础。本期PaaS平台包含tdsql，Tencent OS，ckafka，credis。', }
                    },
                    {
                        name: 'Banber可视化定制工具',
                        title: 'Banber可视化定制工具',
                        description: '--',
                        deptName: '滨湖区技术单位',
                        id: '1671340701423517697',
                        details: { logo: '', manageDeptName: '--', contacts: '--', telephone: '--', description: '--', }
                    },
                    // {
                    //     name: '统一身份认证管理系统',
                    //     title: '统一身份认证管理系统',
                    //     "deptName": "滨湖区技术单位",
                    //     description: '滨湖区级统一身份认证体系，实现“一次登录，漫游全区”的沉浸式应用浏览体验，极大提高了公务人员的工作效率，也为全区应用系统统一接入、用户统一管理、统一身份认证提供了可行的综合解决方案。',
                    //     details: { "logo": "http://2.21.137.202:8080/img/civilization/20230626/统一身份认证_20230626A024.jpg", manageDeptName: '滨湖区技术单位', contacts: '何媛媛', telephone: '15161026370', description: '滨湖区级统一身份认证体系，实现“一次登录，漫游全区”的沉浸式应用浏览体验，极大提高了公务人员的工作效率，也为全区应用系统统一接入、用户统一管理、统一身份认证提供了可行的综合解决方案。', }
                    // },
                    // {
                    //     name: '安全运营中心',
                    //     title: '安全运营中心',
                    //     "deptName": "滨湖区技术单位",
                    //     "description": "在政务云基础安全服务的基础上，建设安全运营服务平台，包括态势大屏、安全数据基础分析、安全数据采集与处理、关联分析、APT分析、事件推理、威胁情报管理、集中资产管理、集中运维监控、事件与告警、漏洞管理、报表与知识库、通报预警、工单管理和全流量威胁检测等服务。",
                    //     details: { "logo": "http://2.21.137.202:8080/img/civilization/20230626/安全运营中心_20230626A031.jpg", manageDeptName: '滨湖区技术单位', contacts: '何媛媛', telephone: '15161026370', description: '在政务云基础安全服务的基础上，建设安全运营服务平台，包括态势大屏、安全数据基础分析、安全数据采集与处理、关联分析、APT分析、事件推理、威胁情报管理、集中资产管理、集中运维监控、事件与告警、漏洞管理、报表与知识库、通报预警、工单管理和全流量威胁检测等服务。', }
                    // },
                ],
            },
            // 热门资源数据
            oRmzysj: {
                title: '最新组件',
                sRmzyType: 'zjNum',
                // 热门资源数据
                aRmsjzyData: [],
            },
            // 热门资源数据
            oTool: {
                title: '最新工具',
                sRmzyType: 'gjNum',
                // 热门资源数据
                aRmsjzyData: [
                ],
            },
        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {
        this.fRequestAllpp();
    },
    beforeDestroy() {

    },
    methods: {
        async fRequestAllpp() {
            const res = await fGetAppListAsync();
            this.oRmzysj.aRmsjzyData = res.filter(item => item.sTypeName == '组件')
            this.oTool.aRmsjzyData = res.filter(item => item.sTypeName == '工具')
            this.oPtnl.aRmsjzyData.forEach(data => {
                data.details = {
                    ...data.details,
                    // description:
                    ...res.filter(item=>item.casAppId == data?.id)[0]
                }
            });
        },

        // 请求我的应用
        // fRequestAllpp() {
        //     this.$fRequest({
        //         url: URLS_USERINFO.u_applicationList,
        //     }).then(res => {
        //         // console.log(res)
        //         res.data.forEach(item => {
        //             if (item.description == '组件') {
        //                 this.oRmzysj.aRmsjzyData = [];
        //                 item.children.forEach(item => {
        //                     if (item.children && item.children.length > 0) {
        //                         this.oRmzysj.aRmsjzyData.push(...item.children);
        //                     }
        //                 })
        //             } else if (item.description == '工具') {
        //                 this.oTool.aRmsjzyData = [];
        //                 item.children.forEach(item => {
        //                     if (item.children && item.children.length > 0) {
        //                         this.oTool.aRmsjzyData.push(...item.children);
        //                     }
        //                 })
        //             };
        //         })
        //     })
        // },
        // 搜索框的点击按钮
        fClickSearch() {

            console.log(this.sBannerKeyWords.trim());
            this.$router.push({
                path: "/digitalBase/search",
                query: { type: 'sjzy', keyWord: this.sBannerKeyWords.trim() }
            })
        },
        // 点击更多
        fClickMore(type) {
            // console.log(type, '更多')
            switch (type) {
                case 'compoents':
                    // console.log('组件')
                    this.$router.push({
                        path: "/digitalBase/searchv2",
                        query: { type: 'zjNum' }
                    })
                    break;
                case 'unit':
                    // console.log('组件')
                    this.$router.push({
                        path: "/digitalBase/searchv2",
                        query: { type: 'gjNum' }
                    })
                    break;
                case 'resource':
                    // console.log('组件')
                    this.$router.push({
                        path: "/digitalBase/search",
                        query: { type: 'sjzy' }
                    })
                    break;

                default:
                    break;
            }
        }
    }
}
</script>
<style lang='less' scoped>
.box-margin {
    position: absolute;
    // left: 0;
    bottom: -200px;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    justify-content: space-around;
    margin: -20px auto 35px;
    padding: 0;
    width: 1440px;
    height: 181px;
    background: #FFFFFF;
    box-shadow: 0px 4px 14px 0px rgba(59, 112, 192, 0.23);
    border-radius: 8px;
}

.home-banner {
    position: relative;
    width: 100%;
    height: 617px;
    margin-bottom: 180px;
    background: url('../../assets/image/home/szdzbg.png') no-repeat center / 100% 617px;
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

.search-keyword {
    color: #FFFFFF;
    font-size: 18px;
}

.search-input {
    // display: flex;
    width: 50%;
    height: 60px;
    font-size: 30px;
    margin: 30px 0 10px;
}

/deep/.el-input__inner {
    height: 60px;
}

/deep/.el-button {
    height: 60px;
    background-color: #26acf7 !important;
    color: #FFFFFF !important;
}

.banner-image {
    width: 100%;
    height: 617px;
    background: no-repeat center / 100% 617px;
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
    height: 60px;
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
        width: 300px;
    }
}

.numberbottom-banner {
    width: 100%;
    height: 617px;
    padding: 109px 0 0 100px;
    margin-bottom: 50px;
    margin-top: -65px;
    background: url('../../assets/image/appmarket/banner.png') no-repeat center 100%;
}

.appmarket-banner-title {
    margin-bottom: 28px;
    color: #000000;
    font-weight: 500;
    font-size: 60px;
}

.appmarket-banner-detail {
    font-size: 18px;
    color: #000000;
}

.banner-detail {
    width: 50%;
    margin: 0 auto 0;
    color: #FFFFFF;
    font-size: 20px;
    font-weight: 500;
    text-indent: 2rem;
}

.appmarket-banner-value {
    // display: flex;
    width: 620px;
}

.banner-value-item {
    display: flex;
    align-items: end;
    margin-right: 80px;
    line-height: 50px;
    font-size: 50px;
    font-weight: 600;
    color: #266FE8;

    & div {
        overflow: hidden !important;
    }
}

.value-item-unit {
    margin: 0 14px 0 7px;
    line-height: 22px;
    color: #000000;
    font-size: 22px;
}

.value-item-name {
    line-height: 35px;
    color: #000000;
    font-size: 35px;
}

.search-input {
    height: 50px;
    font-size: 25px;
    margin: 30px 0 10px;
}

.all-data-item {
    display: flex;
    align-items: center;
    justify-content: space-around;
    width: 17%;
    height: 100px;
    margin-bottom: 30px;
    // background-color: #f6fbff;
}

.data-item-logo {
    width: 64px;
    height: 64px;
    margin-right: 16px;
    background-size: cover;
}

.data-item {
    width: 180px;
}

.data-item-name {
    // margin-bottom: 10px;
    font-size: 36px;
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

.box-block {
    width: 100%;
}

.numberbottom-sjzy {
    display: flex;
    flex-wrap: wrap;
    justify-content: start;
}

.numberbottom-sjzy-item {
    display: flex;
    align-items: center;
    justify-content: space-around;
    flex-direction: column;
    padding: 20px 0;
    margin: 0 16px 32px;
    width: 240px;
    height: 240px;
    font-size: 24px;
    background: rgba(255, 255, 255, 0.39);
    border-radius: 3px;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
}
</style>