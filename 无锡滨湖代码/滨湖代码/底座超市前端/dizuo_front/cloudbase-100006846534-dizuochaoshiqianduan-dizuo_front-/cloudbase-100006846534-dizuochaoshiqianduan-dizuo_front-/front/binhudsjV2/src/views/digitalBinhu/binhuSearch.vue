<template>
    <!-- 首页检索页面 首页头部输入框 -->
    <div>
        <!-- 大图 -->
        <div class="banner">
            <div class="search-wrap">
                <div class="search-title">无锡市滨湖区综合应用服务平台</div>
                <div class="search-title _size">建立政企联动平台，赋能基层治理数字化、智慧化</div>
                <el-input placeholder="请输入你要查找的内容" v-model="sBannerKeyWords" class="search-input">
                    <el-button type="primary" slot="append" icon="el-icon-search" @click="fClickSearch()">搜索</el-button>
                </el-input>
                <!-- <div class="search-keyword">关键字：人口 建筑 企业</div> -->
            </div>
        </div>
        <!-- 页面层级 -->
        <div class="box">
            <div class="box-con _flex">
                <div class="page-icon"></div>
                首页 >> 检索
            </div>
        </div>
        <!-- 筛选条件 -->
        <div class="box">
            <com-condition class="condition" title="类别" :type="sClasses" :data="aClasses"
                @fChangeConditionType="fChangeClassesType"></com-condition>
            <!-- <com-condition class="condition" title="状态" v-if="sClasses !== 'sjzy'" :type="sStutas" :data="aStutas"
                @fChangeConditionType="fChangeStutasType"></com-condition> -->
            <com-condition class="condition" v-if="sClasses !== 'sjzy'" title="应用分类" :type="sYyfl" :data="aYyfl"
                @fChangeConditionType="fChangeYyflType"></com-condition>
            <!-- <com-condition class="condition" v-if="sClasses == 'yyfw'" title="应用单位" :type="sYydw" :data="aYydw"
                @fChangeConditionType="fChangeYydwType" :params="{ name: 'name', type: 'id' }"></com-condition> -->
        </div>

        <!--  -->
        <div class="box">
            <div class="box-con _flexs">
                <div class="">排序:</div>
                <div class="line-count">共{{ nTotal }}条结果</div>
            </div>
        </div>

        <!-- 应用列表 -->
        <div class="box">
            <template v-if="aShowData && aShowData.length > 0">
                <comList-block v-if="sClasses == 'sjzy' || sClasses == 'yyfw'" :type="sClasses" :data="aShowData"
                    :params="{ ...oParams }" @fCliclDetails="fCliclDetails"></comList-block>
                <comList-block v-else :type="sClasses" :data="aShowData.slice((nCurrent - 1) * 9, (nCurrent) * 9)"
                    :params="{ ...oParams }" @fCliclDetails="fCliclDetails"></comList-block>
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

import comCondition from '../../components/page/comCondition.vue';// 筛选条件
import comListBlock from '../../components/page/comListBlock.vue';// 列表组件

import { URLS_USERINFO, URLS_APPMARKET, URLS_SZDZ } from '@/api/url.js';


import fGetAppListAsync from '@/api/setAppList.js'

export default {
    name: '',
    components: {
        comCondition,// 筛选条件
        comListBlock,// 列表组件
    },
    props: [''],
    data() {
        return {
            sBannerKeyWords: this.$route.query.name,
            // 类别的数组
            sClasses: 'all',
            aClasses: [
                // { name: '数据资源', type: 'sjzy' },
                { name: '全部', type: 'all' },
                { name: '组件服务', type: 'zjfw' },
                { name: '工具服务', type: 'gjfw' },
                { name: '应用服务', type: 'yyfw' },
            ],
            // 状态的数组
            sStutas: 'ysj',
            aStutas: [
                { name: '已上架', type: 'ysj' },
                { name: '待上架', type: 'dsj' },
            ],
            // 的数组应用分类
            sYyfl: 'all',
            aYyfl: [
                { name: '全部', type: '', value: 'all' },
                { name: '数字政务', type: 'szzw', value: 'szzw' },
                { name: '数字经济', type: 'szjji', value: 'szjji' },
                { name: '数字生活', type: 'szsh', value: 'szsh' },
                { name: '数字生态', type: 'szst', value: 'szst' },
                { name: '数字治理', type: 'szzl', value: 'szzl' },
                { name: '数字基建', type: 'szjj', value: 'szjj' },
            ],
            // 应用单位
            sYydw: '',
            aYydw: [
                { name: '单位名称', value: 'szzw', act: false },
                { name: '单位名称', value: 'szsh', act: false },
                { name: '单位名称', value: 'szzl', act: false },
                { name: '单位名称', value: 'szst', act: false },
                { name: '单位名称', value: 'szjj', act: false },
                { name: '单位名称', value: 'szjc', act: false },
            ],

            nCurrent: 1, // 当前页
            nNowPage: 9,// 每页数量
            nTotal: 0,// 数据总数

            // 页面显示的数据
            aShowData: [],
            // 数据显示的自定义字段
            oParams: {
                // title: 'name', details: 'description', unit: 'manageDeptName'
            },

            aAllData: [],
            aAllDataCopy: [],
        }
    },
    computed: {},
    watch: {
        sClasses: {
            handler(newVal) {
                // console.log(newVal);
                this.nCurrent = 1;
                this.nNowPage = 9;
                this.nTotal = 0;
                this.aShowData = [];
                this.nTotal = newVal.length
                this.fSetRequest(newVal);

            },
            deep: true,
            immediate: true,
        },
        aShowData: {
            handler(newVal) {
                console.log(newVal)
            },
            deep: true,
            immediate: true,
        }
    },
    created() {

    },
    mounted() {
        this.fRequestAppType();// 请求应用分类
        this.sBannerKeyWords = this.$route.query.name
        console.log(this.sBannerKeyWords)
    },
    beforeDestroy() {

    },
    methods: {
        fSetRequest(type) {
            switch (type) {
                case 'sjzy':
                    // 请求数据资源
                    // console.log('数据资源')
                    this.oParams = { title: 'table_cn_name', details: 'description', unit: 'dept_name' };
                    this.fRequestZyjsData();
                    break;
                case 'zjfw':
                    // 请求组件服务
                    // console.log('组件服务')
                    this.oParams = { title: 'title', details: 'description', unit: 'deptName' };
                    this.fRequestAllpp();
                    break;
                case 'gjfw':
                    // 请求工具服务
                    // console.log('工具服务')
                    this.oParams = { title: 'title', details: 'description', unit: 'deptName' };
                    this.fRequestAllpp();
                    break;
                case 'yyfw':
                    // 请求应用服务
                    this.oParams = { title: 'title', details: 'description', unit: 'deptName' };
                    this.fRequestAllpp();
                    break;
                case 'all':
                    // 请求应用服务
                    this.oParams = { title: 'title', details: 'description', unit: 'deptName' };
                    this.fRequestAllpp();
                    break;
                default:
                    break;
            }
        },
        async fRequestAllpp() {
            const res = await fGetAppListAsync();
            // console.log(res)
            switch (this.sClasses) {
                case 'all':
                    this.aAllData = res
                    break;
                case 'zjfw':
                    this.aAllData = res.filter(item => item.sTypeName == '组件')
                    break;
                case 'gjfw':
                    this.aAllData = res.filter(item => item.sTypeName == '工具')
                    break;
                case 'yyfw':
                    this.aAllData = res.filter(item => item.sTypeName == '应用')
                    break;

            }

            this.fClickZyflType({ name: '全部', value: 'all' },);
            // this.fClickSearch()

        },
        // 请求组件和工具
        // fRequestAllpp() {
        //     this.$fRequest({
        //         url: URLS_USERINFO.u_applicationList,
        //     }).then(res => {
        //         res.data.forEach(item => {
        //             if (item.description == '组件' && this.sClasses == 'zjfw') {
        //                 this.aAllData = item.children;
        //             } else if (item.description == '工具' && this.sClasses == 'gjfw') {
        //                 this.aAllData = item.children;
        //             }
        //         })
        //         this.fClickZyflType({ name: '全部', value: 'all' },);
        //     })
        // },
        // 资源分类的切换
        fClickZyflType(item) {
            // console.log(item, this.aAllData)
            this.aShowData = [];
            // this.sZyfl = item.value;
            if (item.value == 'all') {
                this.aShowData = this.aAllData
                this.aAllDataCopy = this.aAllData;

                // this.aAllData.forEach(data => {
                //     if (data.children && data.children.length > 0) {
                //         this.aShowData.push(...data.children);
                //     }
                // })
            } else {
                // console.log(111)
                this.aShowData = this.aAllData.filter(data => data.sType === item.name);
                this.aAllDataCopy = this.aAllData.filter(data => data.sType === item.name);

            }
            this.aShowData = this.aShowData.slice((this.nCurrent - 1) * 9, (this.nCurrent) * 9)
            this.nTotal = this.aAllDataCopy?.length || 0;
            this.fClickSearch()
            // console.log(this.aShowData)
            // console.log(this.aShowData)
        },
        // 请求关键字资源检索
        fRequestZyjsData() {
            $.ajax({
                url: URLS_SZDZ.u_digital_base_keyWordsSearch,
                method: 'post',
                data: {
                    keyWord: this.sBannerKeyWords,
                    pageSize: this.nNowPage,// 每页数量
                    pageNo: this.nCurrent, // 页码
                }
            }).then(res => {
                // console.log(res, '请求关键字资源检索')
                this.aShowData = res.data[0];
                this.nTotal = res.data[1][0].total;
            })
        },
        // 请求应用分类
        fRequestAppType() {
            this.$fRequest({
                url: URLS_APPMARKET.u_applicationClassification
            }).then(res => {
                // console.log(res, '请求应用分类')
                let aData = res.data;
                res.data.forEach(item => {
                    this.aYyfl.forEach(data => {
                        if (data.name == item.name) {
                            data.type = item.id;
                        }
                    })
                })
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
                // this.fRequestAppList();// 请求应用列表

            })
        },
        // 请求应用列表
        fRequestAppList() {
            this.$fRequest({
                url: URLS_APPMARKET.u_applicationByClassificationAndDept,
                data: {
                    applicationClassificationId: this.sYyfl,// 应用分类id
                    deptId: this.sYydw,// 应用单位id
                    keywords: this.sBannerKeyWords,// 关键字
                    current: this.nCurrent,// 当前页
                    size: this.nNowPage,// 每页数量
                }
            }).then(res => {
                // console.log(res, '请求应用列表')
                this.aShowData = res.data.records;
                this.nTotal = res.data.total;
            })
        },
        // 输入框的搜索按钮
        fClickSearch() {
            if (this.sClasses == 'zjfw' || this.sClasses == 'gjfw' || this.sClasses == 'yyfw' || this.sClasses == 'all') {
                console.log(111)
                this.nCurrent = 1;
                if (this.sBannerKeyWords) {
                    this.$nextTick(() => {
                        this.aShowData = this.aAllDataCopy.filter(item => item.title.indexOf(this.sBannerKeyWords) >= 0);
                    })
                    console.log('过滤', this.aShowData)
                } else {
                    this.aShowData = this.aAllDataCopy;
                }
                this.nTotal = this.aShowData?.length || 0;
            } else {
                this.fSetRequest(this.sClasses);
            }
        },
        // 类别的切换
        fChangeClassesType(item) {
            if (this.sClasses === item.type) return;
            this.sClasses = item.type;
            this.sYyfl = '';
        },
        // 状态的切换
        fChangeStutasType(item) {
            if (this.sStutas === item.type) return;
            this.sStutas = item.type;
        },
        // 应用分类的切换
        fChangeYyflType(item) {
            if (this.sYyfl === item.type) return;
            if (this.sClasses == 'zjfw' || this.sClasses == 'gjfw' || this.sClasses == 'yyfw' || this.sClasses == 'all') {
                this.fClickZyflType(item);
            }
            this.sYyfl = item.type;
        },
        // 应用分类的切换
        fChangeYydwType(item) {
            if (this.sYydw === item.id) return;
            this.sYydw = item.id;
        },
        // 去详情页面
        fCliclDetails(item) {
            let oObj = this.$fSetData(item, ['appSecret'])
            localStorage.setItem('appDeatils',JSON.stringify(oObj))

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
            this.fSetRequest(this.sClasses);
            // this.fRequestAppList();// 请求应用列表
        }
    }

}
</script>
<style lang='less' scoped>
.box-con {
    display: flex;
    align-items: center;

    &._flex {
        align-items: center;
    }

    &._flexs {
        // align-items: center;
        justify-content: space-between;
        padding: 10px 25px;
        margin-bottom: 25px;
        background: #F3F3F3;
        height: 56px;
    }
}

.banner {
    position: relative;
    width: 100%;
    height: 617px;
    // margin-bottom: 100px;
    background: url('../../assets/image/home/bg.png') no-repeat center / 100% 617px;
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

    &._size {
        font-size: 20px;
        // opacity: 0.6;
    }
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
    height: 60px !important;
    background-image: linear-gradient(127deg, #5CA4FF 0%, #2F6BFF 100%, #2F6BFF 100%) !important;
    color: #FFFFFF !important;
}

.page-icon {
    width: 13px;
    height: 16px;
    margin-right: 20px;
    background: url('../../assets/image/appmarket/position.png') no-repeat center / 100%;

}

.condition {
    margin-bottom: 20px;
}

.line-count {
    font-size: 16px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #5C5E66;
}

.userinfo-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    padding: 0 130px 0;
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
</style>