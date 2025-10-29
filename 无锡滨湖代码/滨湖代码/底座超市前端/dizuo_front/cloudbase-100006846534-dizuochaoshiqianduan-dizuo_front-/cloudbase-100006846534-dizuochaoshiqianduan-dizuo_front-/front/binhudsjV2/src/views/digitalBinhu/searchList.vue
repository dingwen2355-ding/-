<template>
    <!-- 应用市场-全部应用 -->
    <div>
        <!-- <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide swiper-slide1" v-for="(item, idx) in aTuiJianApp" :key="'aTuiJianApp' + idx"
                    @click="fGoToDetails(item)">
                    <img class="swiper-slide-img" :src="JSON.parse(item.appScreenshot)[0]" />
                </div>
            </div>
            <div class="swiper-pagination"></div>
        </div> -->


        <div class="box">
            <div class="box-con _flex">
                <div class="page-icon"></div>
                <span class="box-con-router" @click="$router.go(-1)">数字滨湖</span> >> {{ aRouter.name }}
            </div>
        </div>


        <div class="box">
            <div class="box-con _flexs">
                <div class="box-con-px">排序:
                    <div class="updata-time ">
                        更新时间
                        <div class="updata-time-icon">
                            <i class="el-icon-caret-top" :style="{ color: shangjiaTime == '1' ? '#266fe8' : '' }"
                                @click="fChangeShangJiaTime('1')"></i>
                            <i class="el-icon-caret-bottom" :style="{ color: shangjiaTime == '0' ? '#266fe8' : '' }"
                                @click="fChangeShangJiaTime('0')"></i>
                        </div>
                    </div>
                    <div class="updata-time ">
                        访问量
                        <div class="updata-time-icon">
                            <i class="el-icon-caret-top" :style="{ color: fangwenliang == '1' ? '#266fe8' : '' }"
                                @click="fGetAllAppList('1')"></i>
                            <i class="el-icon-caret-bottom" :style="{ color: fangwenliang == '0' ? '#266fe8' : '' }"
                                @click="fGetAllAppList('0')"></i>
                        </div>
                    </div>
                    <div class="updata-time ">
                        申请量
                        <div class="updata-time-icon">
                            <i class="el-icon-caret-top" :style="{ color: shenqingliang == '1' ? '#266fe8' : '' }"
                                @click="fGetAllAppList('1')"></i>
                            <i class="el-icon-caret-bottom" :style="{ color: shenqingliang == '0' ? '#266fe8' : '' }"
                                @click="fGetAllAppList('0')"></i>
                        </div>
                    </div>
                </div>
                <div class="line-count">
                    <el-input class="line-count-input" v-model="sMyAppKeyWord" placeholder="请输入关键字">
                        <el-button slot="append" plain icon="el-icon-search" size="mini"></el-button>
                    </el-input>
                    共{{ nTotal }}条结果
                </div>
            </div>
        </div>

        <div class="userinfo-box _flexs">
            <template v-if="aMyAppData && aMyAppData.length > 0">
                <comList-block :data="aMyAppData.slice((nCurrent - 1) * 9, (nCurrent) * 9)" :params="{ ...oParams }"
                    @fCliclDetails="fGoToDetails"></comList-block>

            </template>
            <template v-else>
                <el-empty description="暂无数据"></el-empty>
            </template>
        </div>
        <div class="userinfo-box _flex">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="nCurrent"
                :page-size="nNowPage" layout=" prev, pager, next, jumper" :total="nTotal">
            </el-pagination>
        </div>

    </div>
</template>

<script>
// import SubAppbanner from '@/views/appmarket/appbanner.vue';// 顶部banner图
import ComCondition from '../../components/page/comCondition.vue';// 筛选条件
import ComListBlock from '../../components/page/comListBlock.vue';// 列表组件

import { URLS_USERINFO, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js'
import fGetAppListAsync from '@/api/setAppList.js'

import Swiper from 'swiper'; // 注意引入的是Swiper
import 'swiper/css/swiper.min.css' // 注意这里的引入

export default {
    name: '',
    components: {
        // SubAppbanner,// 应用市场首页
        ComCondition,// 筛选条件
        ComListBlock,// 列表组件
    },
    props: [''],
    data() {
        return {
            aRouter: this.$route.query,
            // aBannerData: [
            //     { name: '应用数量', unit: '个', value: this.$store.state.aAllAppData.length },
            //     { name: '累计使用量', unit: '次', value: '24' },
            // ],
            fangwenliang: '0',
            shenqingliang: '0',
            sMyAppKeyWord: '',
            sBtnType: 'yjr',
            // 应用分类
            sYyfl: '',
            sYyflName: '数字政务',
            aYyfl: [
                { name: '全部', value: '', image: require('../../assets/image/home/appmarket/szzw_img.png') },
                { name: '数字政务', value: 'szzw', image: require('../../assets/image/home/appmarket/szzw_img.png') },
                { name: '数字经济', value: 'szjji', image: require('../../assets/image/home/appmarket/szjj_img.png') },
                { name: '数字生活', value: 'szsh', image: require('../../assets/image/home/appmarket/szsh_img.png') },
                { name: '数字生态', value: 'szst', image: require('../../assets/image/home/appmarket/szst_img.png') },
                { name: '数字治理', value: 'szzl', image: require('../../assets/image/home/appmarket/szzl_img.png') },
                { name: '数字基建', value: 'szjj', image: require('../../assets/image/home/appmarket/szjj1_img.png') },
            ],
            sImage: require('../../assets/image/home/appmarket/szzw_img.png'),
            // 应用单位
            aYydwType: [],
            sYydw: '',
            aYydw: [
                { name: '单位名称', value: 'szzw', act: false },
                { name: '单位名称', value: 'szsh', act: false },
                { name: '单位名称', value: 'szzl', act: false },
                { name: '单位名称', value: 'szst', act: false },
                { name: '单位名称', value: 'szjj', act: false },
                { name: '单位名称', value: 'szjc', act: false },
            ],
            aMyAppData: [],
            aMyAppDataCopy: [],
            nCurrent: 1, // 当前页
            nNowPage: 9,// 每页数量
            nTotal: 0,// 数据总数
            // 数据显示的自定义字段
            oParams: {
                title: 'appName', details: 'remark', unit: 'manageDeptName'
            },
            aAppSource: [], // 应用来源
            sAppSource: '',
            aAppType: [],// 应用分类
            sAppType: '',
            aAppModel: [],// 应用模块
            sAppModel: '',
            shangjiaTime: '0',
            aTuiJianApp: [],
        }
    },
    computed: {},
    watch: {
        $route: {
            handler(newVal) {
                // console.log()
                if (newVal.query) {
                    console.log(newVal.query)
                }
            },
            deep: true,
            immediate: true,
        },
    },
    created() {

    },
    mounted() {
        // this.fClichYyflType(this.aYyfl[0])
        // this.fRequestAppType();// 请求应用分类
        this.fGetAppSource();// 请求字典 应用来源
        this.fGetAppModule();// 请求字典 应用模块
        this.fGetAppType();// 请求字典 应用分类
        this.fGetAllAppList();// 请求已上架的应用
        this.fGetTuiJianApp();// 请求推荐轮播图
        this.fSeiperInit();
    },
    beforeDestroy() {

    },
    methods: {
        fSeiperInit() {
            new Swiper(".swiper-container", {
                loop: true,
                mousewheel: true,
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
        async fGetLastApp() {
            let res = await this.$fRequest({
                url: this.$ip.server_ocn + 'cmApp/getLastApp',
            })
            this.nTotal = JSON.parse(JSON.stringify(res.data)).length
            this.aMyAppData = JSON.parse(JSON.stringify(res.data));
            this.aMyAppDataCopy = JSON.parse(JSON.stringify(res.data));
            // console.log(res, 2222)
        },
        async fGetTuiJianApp() {
            let res = await this.$fRequest({
                url: this.$ip.server_ocn + 'cmApp/getTuiJian',
            })
            if (this.aRouter.flag == 'banner') {
                res.data.forEach(item => {
                    item.appTypeName = this.aAppModel.filter(data => data.dictValue == item.appType)[0]?.dictLabel || ''
                    item.appModuleName = this.aAppType.filter(data => data.dictValue == item.appModule)[0]?.dictLabel || ''
                    item.sourceName = this.aAppSource.filter(data => data.dictValue == item.source)[0]?.dictLabel || ''
                });
                // }
                this.nTotal = JSON.parse(JSON.stringify(res.data)).length
                this.aMyAppData = JSON.parse(JSON.stringify(res.data));
                this.aMyAppDataCopy = JSON.parse(JSON.stringify(res.data));
            }
            this.aTuiJianApp = JSON.parse(JSON.stringify(res.data.splice(0, 3))) || [];
            this.$nextTick(() => {
                this.fSeiperInit();
            })
            // console.log(res, 2222)
        },
        // 请求字典 应用模块
        fGetAppModule() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'system/dict/data/type/app_module'
            }).then(res => {
                this.aAppType = res.data || []
                this.aAppType.splice(0, 0, { dictLabel: '全部', dictValue: '' })
                this.sAppType = ''
            })
        },
        // 请求字典 应用分类
        fGetAppType() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'system/dict/data/type/app_type'
            }).then(res => {
                this.aAppModel = res.data || []
                this.aAppModel.splice(0, 0, { dictLabel: '全部', dictValue: '' })
                this.sAppModel = ''
            })
        },
        fChangeShangJiaTime() {
            switch (this.shangjiaTime) {
                case '1':
                    this.shangjiaTime = '0'
                    break;
                case '0':
                    this.shangjiaTime = '1'
                    break;
            }
            this.fGetAllAppList();// 请求已上架的应用
        },
        // 请求字典 应用来源
        fGetAppSource() {
            this.$fRequest({
                url: this.$ip.server_ocn + 'system/dict/data/type/app_source'
            }).then(res => {
                this.aAppSource = res.data || []
                this.aAppSource.splice(0, 0, { dictLabel: '全部', dictValue: '' })
            })
        },
        // 切换应用分类
        fClickAppSource(data) {
            this.sAppSource = data.dictValue
            this.fGetAllAppList();// 请求已上架的应用
        },
        // 请求已上架的应用
        fGetAllAppList() {
            console.log(this.aRouter)
            let data = {
                shangjiaTime: this.shangjiaTime, // 上架时间 
                source: '',
            }
            if (this.aRouter.flag == "app_module") {
                data.appModule = this.aRouter.id
                data.appType = ''
            } else if (this.aRouter.flag == "app_type") {
                data.appType = this.aRouter.id
                data.appModule = ''
            } else if (this.aRouter.flag == 'banner') {
                this.fGetTuiJianApp();
                return
            } else if (this.aRouter.flag == 'new') {
                this.fGetLastApp();

                return
            }
            this.$fRequest({
                method: 'post',
                url: this.$ip.server_ocn + 'cmApp/allAppList',
                data: {
                    ...data
                }
            }).then(res => {
                // if (this.sAppModel && this.sAppType) {
                res.data.forEach(item => {
                    item.appTypeName = this.aAppModel.filter(data => data.dictValue == item.appType)[0]?.dictLabel || ''
                    item.appModuleName = this.aAppType.filter(data => data.dictValue == item.appModule)[0]?.dictLabel || ''
                    item.sourceName = this.aAppSource.filter(data => data.dictValue == item.source)[0]?.dictLabel || ''
                });
                // }
                this.nTotal = res.data.length
                this.aMyAppData = res.data;
                this.aMyAppDataCopy = res.data;
            })
        },
        // 请求应用分类
        fRequestAppType() {
            this.$fRequest({
                url: URLS_APPMARKET.u_applicationClassification
            }).then(res => {
                console.log(res, '请求应用分类')
                let aData = res.data;
                res.data.forEach(item => {
                    this.aYyfl.forEach(data => {
                        if (data.name == item.name) {
                            data.value = item.id;
                        }
                    })
                })
                // this.aYyfl = aData.reduce((cur, next) => {
                //     obj[next.value] ? "" : obj[next.value] = true && cur.push(next);
                //     return cur;
                // }, []) //设置cur默认类型为数组，并且初始值为空的数组
                this.sYyfl = '';
                this.fRequestAppUnit();// 请求应用单位
            })
        },
        // 请求应用单位
        fRequestAppUnit() {
            this.$fRequest({
                url: URLS_APPMARKET.u_applicationDept
            }).then(res => {
                // console.log(res, '请求应用单位')
                this.aYydw = res.data;
                this.aYydw.splice(0, 0, { name: '全部', id: '' })
                this.sYydw = res.data[0].id;
                this.fRequestAppList();// 请求应用列表

            })
        },

        async fRequestAppList() {
            const res = await fGetAppListAsync();
            // console.log(res)
            this.aMyAppData = res;
            this.aMyAppDataCopy = res;
            this.nTotal = res.length
            this.fSearchMyApp()
        },
        fSearchMyApp() {
            this.aMyAppData = this.aMyAppDataCopy.filter(item => item.appName.indexOf(this.sMyAppKeyWord) >= 0)
            this.nTotal = this.aMyAppData.length
        },
        // 申请上架的列表
        fGetSqsjList() {
            this.aMyAppData = [];
            this.aMyAppDataCopy = [];
            $.ajax({
                url: `https://2.21.138.89:11511/cmApp/replayListAll?pageNum=${1}&pageSize=${999}`,
                method: 'post',
                headers: {
                    'Authorization': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    'ApiToken': JSON.parse(window.localStorage.getItem('userInfo')).apiToken,
                    // "Content-Type": "application/json"
                },
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify({ params: { 'own': 1 } })
            }).then(res => {
                res.data.records.forEach(item => {
                    item.logo = item.appScreenshot ? JSON.parse(item.appScreenshot)[0] : item.appPic
                    item.name = item.appName
                    item.title = item.appName
                    item.description = item.remark
                    item.manageDeptName = ''
                })
                // title: 'name', details: 'description', unit: 'manageDeptName'
                this.aMyAppData = res.data.records;
                this.aMyAppDataCopy = res.data.records;
                this.nTotal = res.data.total;

            })
        },
        // 类型切换
        fChangeBtnType(type) {
            this.sBtnType = type;
            this.nCurrent = 1;
            this.nNowPage = 9;
            this.nTotal = 0
            switch (type) {
                case 'yjr':
                    this.fRequestAppList();// 请求应用列表
                    break;
                case 'tjjr':
                    this.aMyAppData = [];
                    this.aMyAppDataCopy = [];
                    this.fGetSqsjList();// 申请上架的列表
                    break;
                default:
                    break;
            }
        },
        // 去详情页面
        fGoToDetails(item) {
            let oObj = this.$fSetData(item, ['appSecret'])
            localStorage.setItem('appDeatils', JSON.stringify(oObj))

            this.$router.push({
                path: "/appMarket/appDetails",
                // query: { data: JSON.stringify(oObj) }
            })
        },
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            this.nCurrent = val;
            // this.aMyAppData
            // this.fRequestAppList();// 请求应用列表
        }
    }
}
</script>
<style lang='less' scoped>
.swiper-container {
    width: 100%;
    height: 500px;

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
    width: 100%;
    height: 100%;
    background: #2490FC;
}

.appmarket-banner {
    position: relative;
    width: 100%;
    height: 617px;
    // padding: 109px 0 0 100px;
    // margin-bottom: 50px;
    // margin-top: -65px;
    background: url('../../assets/image/home/yyscbg.png') no-repeat center / 100% 617px;

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

.appmarket-banner-detail {
    font-size: 18px;
    color: #000000;
}

.banner-detail {
    width: 70%;
    margin: 0 auto 0;
    color: #FFFFFF;
    font-size: 20px;
    font-weight: 500;
    text-indent: 2rem;
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

.appmarket-box {
    padding: 50px 130px 0;

    &._flex {
        display: flex;
        justify-content: space-between;
    }

    &._flexc {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    &._flexw {
        display: flex;
        align-items: center;
        justify-content: space-between;
        flex-wrap: wrap;
    }
}

.box-btn-wrap {
    display: flex;
    align-items: center;
    width: 544px;
    height: 58px;
    margin: 0 auto;
    border-radius: 29px;
    overflow: hidden;
    background: #ecf0f9;
    box-shadow: 0 0 30px rgba(0, 0, 0, 0.15);
}


.appmarket-box-btn {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    // width: 136px;
    height: 58px;
    font-size: 18px;
    // font-weight: bold;
    color: #5C5E66;
    border-radius: 29px;
    background: #ecf0f9;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._act {
        color: #FFFFFF;
        background: linear-gradient(140deg, #5CA4FF 0%, #2F6BFF 100%, #2F6BFF 100%);
    }
}

.condition {
    margin-bottom: 20px;
}



.userinfo-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    padding: 30px 130px 0;
    margin-bottom: 26px;

    &._flexs {
        justify-content: start;
    }

    &._flex {
        justify-content: end;

        /deep/.el-input__inner {
            height: auto;
            font-size: 12px;
            background: rgba(255, 255, 255, 0.39);
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
        }
    }
}

.userinfo-box-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 535px;
    height: 230px;
    margin-bottom: 16px;
    margin-right: 10px;

    padding: 25px 23px 0 25px;
}

.userinfo-item-image {
    width: 114px;
    height: 114px;
    background: url('../../assets/image/appmarket/image.png') no-repeat center / 114px;
}

.userinfo-item-con {
    width: 360px;
}

.userinfo-item-title {
    font-size: 26px;
    font-weight: 500;
    color: #000000;
}

.userinfo-item-detail {
    height: 60px;
    margin: 15px 0;
    font-size: 20px;
    font-weight: 300;
    color: #000000;
}

.userinfo-item-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
}

.item-box-unit {
    color: #000000;
}

.appmarket-more {
    font-size: 14px;
    color: #266FE8;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._cen {
        // margin-bottom: 50px;
        text-align: center;
    }
}

.box-con {
    display: flex;
    align-items: center;

    &._flex {
        align-items: center;
        font-size: 16px;
        font-weight: 500;
        color: #1A1B1F;
    }

    &._flexs {
        // align-items: center;
        justify-content: space-between;
        padding: 0 25px;
        // margin-bottom: 25px;
        background: #F3F3F3;
        height: 56px;
    }
}

.page-icon {
    width: 13px;
    height: 16px;
    margin-right: 20px;
    background: url('../../assets/image/appmarket/position.png') no-repeat center / 100%;
}

.box-con-router {
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }
}

.box-con-px {
    display: flex;
    align-items: center;
}

.updata-time {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 56px;
    padding: 0 20px;

    // background: linear-gradient(180deg, rgba(21, 93, 255, 0) 0%, #155DFF 50%);
    &._act {
        border-bottom: 1px solid #155dff;
        color: #155DFF;

        &:hover {
            opacity: .5;
            cursor: pointer;
        }
    }

}

.line-count {
    display: flex;
    align-items: center;
    width: 400px;
    font-size: 16px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #5C5E66;
}

.line-count-input {
    width: 250px;
    height: 40px;
    margin-right: 50px;

    /deep/.el-button {
        height: 40px;
    }
}

/deep/.el-input__inner {
    height: 40px;
}

/deep/.el-empty {
    margin: 0 auto;
}

.updata-time-icon {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    height: 100%;
    margin-left: 15px;
    margin-top: -10px;
    font-size: 25px;
    padding: 10px 0;
}

/deep/.el-icon-caret-top,
/deep/.el-icon-caret-bottom {
    display: inline-block;
    width: 15px;
    height: 15px;
}
</style>