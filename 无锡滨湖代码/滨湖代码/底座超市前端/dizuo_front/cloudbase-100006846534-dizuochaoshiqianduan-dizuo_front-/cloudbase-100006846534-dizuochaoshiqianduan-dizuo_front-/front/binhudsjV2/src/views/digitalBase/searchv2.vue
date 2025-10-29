<template>
    <!-- 搜索页 -->
    <div>
        <div class="numberbottom-banner">
            <div class="appmarket-banner-title">数字底座</div>
            <div class="appmarket-banner-detail">
                <div class="banner-detail">
                    数字底座涵盖智慧城市或城市数字化发展所需的基础网络、感知神经元、具有数据处置和智能能力的一系列平台，为政府提供可靠、安全、连续性的技术基础环境，
                    帮助政府实现数据的统一存储、分析、管理和应用，同时提高政府中的协同能力和决策效率。
                </div>
            </div>
        </div>
        <div class="search-box-wrap">
            <!-- <div class="search-box _bgcolor">
                <span class="search-box-router"
                    @click="fChangePage({ type: 'numberbottom', page: 'numberbottom', component: 'sub-numberbottom' })">数字底座</span>>>
                <span class="search-box-router _color">搜索结果</span>
            </div> -->
            <div class="search-box ">
                <div class="search-type">

                    <div class="search-type-item">
                        <div class="search-type-label">资源分类:</div>
                        <div class="search-type-wrap">
                            <div class="search-type-name" v-for="(item, idx) in aZyfl" :key="'aZyfl' + idx"
                                :class="sZyfl == item.value ? '_act' : ''" @click="fClickZyflType(item)">{{ item.name }}
                            </div>
                        </div>
                    </div>
                    <div class="search-type-item" v-if="false">
                        <div class="search-type-label">来源:</div>
                        <div class="search-type-wrap">
                            <div class="search-type-name" v-for="(item, idx) in aLy" :key="'aLy' + idx"
                                :class="sLy == item.value ? '_act' : ''" @click="fClickLyType(item)">{{ item.name }}</div>
                        </div>
                    </div>
                    <div class="search-type-item">
                        <div class="search-type-label">关键字:</div>
                        <el-input class="search-type-ipt" v-model="sKeyWords" placeholder="请输入关键字"></el-input>
                    </div>
                    <div class="search-type-item _flex">
                        <div class="type-item-btn _act" @click="fReuest()">检索</div>
                        <div class="type-item-btn" @click="fResect()">重置</div>
                    </div>
                </div>
            </div>
            <div class="search-box _pad">
                <div class="search-box-con">
                    <div class="search-box-px">
                    </div>
                    <div class="search-box-text">找到{{ nDataCount }}条结果</div>
                </div>
            </div>
            <div class="search-box _flexw">
                <template v-if="aSearchData && aSearchData.length > 0">
                    <div class="search-box-item block "
                        v-for="(item, idx) in aSearchData.slice((currentPage4 - 1) * 9, (currentPage4) * 9)"
                        :key="'aSearchData' + idx">
                        <div :class="[sPageType == 'zjNum' ? '_zjimg' : '_gjimg', 'image']"></div>
                        <div class="box-item-con">
                            <div class="box-item-tit text-ellipsis-1" :title="item.title">{{ item.title }}</div>
                            <div class="box-item-det">所属单位:{{ item.deptName }}</div>
                            <div class="box-item-bot">
                                <div class="item-bot-ly"></div>
                                <div class="item-bot-sj" @click="fGoToDetails(item)">跳转详情 ></div>
                            </div>
                        </div>
                    </div>
                </template>
                <template v-else>
                    <el-empty description="暂无数据"></el-empty>
                </template>
            </div>
            <div class="search-box _flexe">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="currentPage4" :page-size="9" layout=" prev, pager, next, jumper" :total="nDataCount">
                </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
import { URLS_USERINFO, URLS_SZDZ } from '@/api/url.js';
import fGetAppListAsync from '@/api/setAppList.js'
export default {
    name: '',
    components: {},
    props: [''],
    data() {
        return {

            // 资源分类
            sZyfl: 'all',
            aZyfl: [
                { name: '全部', value: 'all' },
                { name: '数字政务', value: 'szzw' },
                { name: '数字经济', value: 'szjji' },
                { name: '数字生活', value: 'szsh' },
                { name: '数字生态', value: 'szst' },
                { name: '数字治理', value: 'szzl' },
                { name: '数字基建', value: 'szjj' },
            ],
            // 来源
            sLy: 'all',
            aLy: [
                { name: '全部', value: 'all' },
                { name: '无锡市滨湖区教育局', value: 'sjzy' },
                { name: '无锡市滨湖区公共资源交易中心', value: 'zjfw' },
                { name: '无锡市滨湖区教育局', value: 'zwfw' },
                { name: '无锡市公安局滨湖分局', value: 'sfrz' },
                { name: '无锡市滨湖区行政审批局', value: 'xxtz' },
            ],
            // 日期
            sDate: 'all',
            aDate: [
                { name: '全部', value: 'all' },
                { name: '数据资源', value: 'sjzy' },
                { name: '组件服务', value: 'zjfw' },
                { name: '工具', value: 'gj' },
            ],
            // 关键字
            sKeyWords: '',
            // 搜索结果
            aSearchData: [],
            currentPage4: 1,
            // 数据总数
            nDataCount: 0,
            sPageType: '',
            aAllData: [],
            aAllDataCopy: [],
            aComUtilsData: [],

        }
    },
    computed: {
        sNowPageName() {
            return this.$route.query.type || '';
        },
    },
    watch: {
        sNowPageName: {
            handler(newVal) {
                if (!newVal) return;
                this.$nextTick(() => {
                    this.sPageType = newVal;
                    this.aSearchData = [];
                    this.aAllData = [];
                    // this.aComUtilsData.forEach(item => {
                    //     if (newVal == 'zjNum') { // 组件服务
                    //         if (item.title == '组件') {
                    //             this.aAllData = item.children;
                    //             item.children.forEach(item => {
                    //                 if (item.children && item.children.length > 0) {
                    //                     this.aSearchData.push(...item.children);
                    //                 }
                    //             })
                    //         }
                    //     } else if (newVal == 'gjNum') { // 工具
                    //         if (item.title == '工具') {
                    //             this.aAllData = item.children;
                    //             item.children.forEach(item => {
                    //                 if (item.children && item.children.length > 0) {
                    //                     this.aSearchData.push(...item.children);
                    //                 }
                    //             })
                    //         }
                    //     }
                    //     this.nDataCount = this.aSearchData.length;
                    // })
                    // this.aAllDataCopy = this.aSearchData;
                })
            },
            deep: true,
            immediate: true,
        },
    },
    created() {

    },
    mounted() {
        this.fRequestAllpp();// 请求所有应用
    },
    beforeDestroy() {

    },
    methods: {

        // 设置时间
        fSetDate(num) {
            let sStr = '';
            if (num) {
                let date = new Date(num);
                let Year = date.getFullYear();
                let Moth = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
                let Day = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
                let Hour = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours());
                let Minute = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
                let Sechond = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
                sStr = Year + '-' + Moth + '-' + Day + '   ' + Hour + ':' + Minute + ':' + Sechond;
            } else {
                sStr = '--';
            }
            return sStr;

        },
        // 去详情页面
        fGoToDetails(item) {
            // console.log(item)
            let oObj = this.$fSetData(item, ['appSecret'])
            localStorage.setItem('appDeatils',JSON.stringify(oObj))

            this.$router.push({
                path: "/appMarket/appDetails",
                // query: {data:JSON.stringify(oObj)}
            })
        },
        // 重置按钮
        fResect() {
            this.sKeyWords = '';
            this.sZyfl = 'all';
            this.sLy = 'all';
            this.sDate = 'all';
            this.currentPage4 = 1;
            this.aSearchData = this.aAllDataCopy;
            this.fClickZyflType({ name: '全部', value: 'all' },);
        },
        fReuest() {
            this.currentPage4 = 1;
            if (this.sKeyWords) {
                this.aSearchData = this.aAllDataCopy.filter(item => item.title.indexOf(this.sKeyWords) >= 0);
            } else {
                this.aSearchData = this.aAllDataCopy;
            }
            this.nDataCount = this.aSearchData?.length || 0;
        },
        // 请求关键字资源检索
        fRequestZyjsData() {
            $.ajax({
                url: URLS_SZDZ.u_digital_base_keyWordsSearch,
                method: 'post',
                data: {
                    keyWord: this.sKeyWords,
                    pageSize: '5',// 每页数量
                    pageNo: this.currentPage4, // 页码
                }
            }).then(res => {
                // console.log(res, '请求关键字资源检索')
                this.aSearchData = res.data[0];
                this.nDataCount = res.data[1][0].total;
            })
        },
        async fRequestAllpp() {
            const res = await fGetAppListAsync();
            console.log(res)
            switch (this.sPageType) {
                case 'zjNum':
                    this.aAllData = res.filter(item => item.sTypeName == '组件')
                    break;
                case 'gjNum':
                    this.aAllData = res.filter(item => item.sTypeName == '工具')
                    break;
            }
            this.fClickZyflType({ name: '全部', value: 'all' },);
            console.log(this.aAllData)
        },
        // 请求组件和工具
        // fRequestAllpp(type) {
        //     this.$fRequest({
        //         url: URLS_USERINFO.u_applicationList,
        //     }).then(res => {
        //         res.data.forEach(item => {
        //             if (item.description == '组件' && this.sPageType == 'zjNum') {
        //                 this.aAllData = item.children;
        //             } else if (item.description == '工具' && this.sPageType == 'gjNum') {
        //                 this.aAllData = item.children;
        //             }
        //         })
        //         this.fClickZyflType({ name: '全部', value: 'all' },);
        //     })
        // },
        // 路由切换
        fChangePage(data) {
            this.$store.commit('fSetNowPage', { ...data })
        },
        // 资源分类的切换
        fClickZyflType(item) {
            // console.log(item, this.aAllData)
            this.aSearchData = [];
            this.sZyfl = item.value;
            if (item.value == 'all') {
                this.aSearchData = this.aAllData
                // this.aAllData.forEach(data => {
                //     if (data.children && data.children.length > 0) {
                //         this.aSearchData.push(...data.children);
                //     }
                // })
            } else {
                this.aSearchData = this.aAllData.filter(data => data.sType === item.name);
            }
            this.aAllDataCopy = this.aSearchData;
            this.nDataCount = this.aSearchData?.length || 0;
        },
        // 来源的切换
        fClickLyType(item) {
            this.sLy = item.value;
        },
        // 日期的切换
        fClickDateType(item) {
            this.sDate = item.value;
        },
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage4 = val;
            // this.fRequestZyjsData();// 请求关键字资源检索
            // console.log(`当前页: ${val}`);
        }
    }
}
</script>
<style lang='less' scoped>
.numberbottom-banner {
    width: 100%;
    height: 617px;
    padding: 109px 0 0 0;
    color: #fff;
    display: flex;
    align-items: center;
    flex-direction: column;
    margin-bottom: 50px;
    margin-top: -65px;
    background: url('../../assets/image/home/szdzbg.png') no-repeat center / 100% 617px;
}

.appmarket-banner-title {
    margin-bottom: 28px;
    color: #fff;
    font-weight: 500;
    font-size: 60px;
}

.appmarket-banner-detail {
    font-size: 18px;
    color: #fff;
}

.banner-detail {
    width: 1020px;
    margin: 28px 0 40px;
    font-size: 24px;
    font-weight: 500;
    text-indent: 2rem;
}

.search-box-wrap {
    padding: 92px 130px 0;
}

.search-box {
    padding: 20px 50px;
    background: #fff;
    border-radius: 3px;

    &._flex {
        display: flex;
        justify-content: space-between;
    }

    &._flexe {
        display: flex;
        justify-content: end;
    }


    &._flexc {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    &._flexw {
        display: flex;
        align-items: center;
        // justify-content: space-between;
        flex-wrap: wrap;
    }

    &._bgcolor {
        height: 65px;
        background-color: #fcfcfc;
        // box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
        // border-radius: 3px;
    }

    &._pad {
        padding-bottom: 30px;
    }
}

.search-box-con {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 59px;
    padding: 0 30px;
    // margin-bottom: 25px;
    font-size: 20px;
    color: rgba(0, 0, 0, 0.9);
    // background-color: #f7f7f7;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
    border-radius: 3px;
}

.search-box-item {
    display: flex;
    // flex-direction: column;
    align-items: center;
    justify-content: space-around;
    width: 32%;
    height: 200px;
    padding: 0 35px;
    margin-bottom: 15px;
    margin-right: 15px;
    border: 1px solid rgba(112, 112, 112, 0.4314);
    border-radius: 3px;
    font-size: 20px;
    color: rgba(0, 0, 0, 0.9);
}

.box-item-con {
    width: calc(100% - 160px);
}

.image {
    width: 120px;
    height: 120px;

    &._zjimg {
        background: url('../../assets/image//numberbottom/zjimg.jpg') no-repeat center / cover;
    }

    &._gjimg {
        background: url('../../assets/image//numberbottom/gjimg.jpg') no-repeat center / cover;
    }
}

.search-box-router {
    cursor: pointer;
    user-select: none;
    color: #000000;

    &:hover {
        opacity: .75;
    }

    &._color {
        color: #266FE8;

    }
}

.search-type {
    padding-bottom: 10px;
}

.search-type-item {
    display: flex;
    align-items: center;
    margin-bottom: 25px;
    font-size: 16px;

    &._flex {
        justify-content: center;
    }
}

.search-type-wrap {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
}

.search-type-label {
    width: 100px;
}

.search-type-name {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 34px;
    margin-right: 10px;
    background-color: #fff;
    border-radius: 17px;
    padding: 0 15px;
    margin-bottom: 10px;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._act {
        background: #266FE8;
        color: #fff;
    }
}

.type-item-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 108px;
    height: 52px;
    margin-left: 20px;
    font-size: 20px;
    border-radius: 26px;
    padding: 0 15px;
    border: 1px solid #dfdfdf;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }

    &._act {
        margin-right: 20px;
        background: #266FE8;
        color: #fff;
    }
}

.box-px-color {
    padding-left: 20px;
    color: #266FE8;
}

.box-item-tit {
    font-size: 26px;
    color: #000;
}

.box-item-bot {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.box-item-det {

    height: 60px;
}

.item-bot-sj {
    color: #266FE8;
    cursor: pointer;
    user-select: none;
}

/deep/.el-empty {
    margin: 0 auto;
}
</style>