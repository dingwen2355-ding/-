<template>
    <!-- 搜索页 -->
    <div>
        <div class="appmarket-banner">
            <div class="search-wrap">
                <el-input placeholder="请输入你要查找的内容" v-model="sKeyWords" class="search-input">
                    <el-button type="primary" slot="append" icon="el-icon-search" @click="fReuest()">搜索</el-button>
                </el-input>
            </div>
        </div>

        <div class="box">
            <div class="box-con _flex">
                <div class="page-icon"></div>
                数字底座 >> 数据资源
            </div>
        </div>

        <div class="box">
            <com-condition class="condition" title="资源分类" :type="sZyfl" :data="aZyfl" @fChangeConditionType="fClickZyflType"
                :params="{ name: 'name', type: 'value' }"></com-condition>
            <com-condition class="condition" title="来源" :type="sLy" :data="aLy" @fChangeConditionType="fClickLyType"
                :params="{ name: 'name', type: 'value' }"></com-condition>
        </div>

        <div class="box">
            <div class="box-con _flexs">
                <div class="box-con-px">排序:
                    <div class="updata-time">更新时间</div>
                </div>
                <div class="line-count">共{{ nDataCount }}条结果</div>
            </div>
        </div>


        <div class="box">
            <div class="search-box">
                <div class="search-box-item block" v-for="(item, idx) in aSearchData" :key="'aSearchData' + idx">
                    <div class="box-item-tit">{{ item.table_cn_name }}</div>
                    <div class="box-item-bot">
                        <div class="item-bot-ly">数据来源:<span class="box-px-color">{{ item.system_name }}</span></div>
                        <div class="box-item-det">所属单位:{{ item.dept_name }}</div>
                        <div class="item-bot-sj">更新时间:{{ fSetDate(item.updated_at) }}</div>
                    </div>
                </div>
            </div>
            <div class="search-box _flexe">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="currentPage4" :page-size="5" layout=" prev, pager, next, jumper" :total="nDataCount">
                </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
import ComCondition from '../../components/page/comCondition.vue';// 筛选条件
import { URLS_USERINFO, URLS_SZDZ } from '@/api/url.js';

export default {
    name: '',
    components: {
        ComCondition,// 筛选条件
    },
    props: [''],
    data() {
        return {
            // 类别
            sLb: 'all',
            aLb: [
                { name: '全部', value: 'all' },
                { name: '数据资源', value: 'sjzy' },
                { name: '组件服务', value: 'zjfw' },
                { name: '工具', value: 'gj' },
            ],
            // 资源分类
            sZyfl: 'all',
            aZyfl: [
                { name: '全部', value: 'all' },
                { name: '财政金融', value: 'czjr' },
                { name: '城建住房', value: 'cjzf' },
                { name: '地理空间', value: 'dlkj' },
                { name: '法律服务', value: 'flfw' },
                { name: '公共安全', value: 'ggaq' },
                { name: '教育文化', value: 'jywh' },
                { name: '社保就业', value: 'sbjy' },
                { name: '社会企业', value: 'shqy' },
                { name: '生活服务', value: 'shfw' },
                { name: '生态环境', value: 'sthj' },
                { name: '信用服务', value: 'xyfw' },
                { name: '知识产权', value: 'zscq' },
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
            sKeyWords: this.$route.query.keyWord || '',
            // 搜索结果
            aSearchData: [

            ],
            currentPage4: 1,
            // 数据总数
            nDataCount: 0,

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
                console.log()
                if (!newVal) return;
                this.$nextTick(() => {
                    if (newVal == 'sjzy') { // 数据资源
                        this.fRequestZyjsData();// 请求关键字资源检索
                    } else if (newVal == 'zjNum') { // 组件服务
                        this.fRequestAllpp(1);
                    } else if (newVal == 'gjNum') { // 工具
                        this.fRequestAllpp(0);
                    }
                })
            },
            deep: true,
            immediate: true,
        },
    },
    created() {

    },
    mounted() {
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
        // 重置按钮
        fResect() {
            this.sKeyWords = '';
            this.sLb = 'all';
            this.sZyfl = 'all';
            this.sLy = 'all';
            this.sDate = 'all';
            this.currentPage4 = 1;
            this.fRequestZyjsData(); // 请求关键字资源检索
        },
        fReuest() {
            this.currentPage4 = 1;
            this.fRequestZyjsData();
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
        // 请求组件和工具
        fRequestAllpp(type) {
            this.$fRequest({
                url: URLS_USERINFO.u_applicationList,
            }).then(res => {
                console.log(res.data[type])

            })
        },
        // 路由切换
        fChangePage(data) {
            this.$store.commit('fSetNowPage', { ...data })
        },
        // 类别的切换
        fClickLbType(item) {
            this.sLb = item.value;
        },
        // 资源分类的切换
        fClickZyflType(item) {
            this.sZyfl = item.value;
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
            this.fRequestZyjsData();// 请求关键字资源检索
            // console.log(`当前页: ${val}`);
        }
    }
}
</script>
<style lang='less' scoped>
.appmarket-banner {
    position: relative;
    width: 100%;
    height: 617px;
    // padding: 109px 0 0 100px;
    // margin-bottom: 50px;
    // margin-top: -65px;
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

.box-con {
    display: flex;
    align-items: center;

    &._flex {
        align-items: center;
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
    width: 620px;
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
    // background: #fff;
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
        justify-content: space-between;
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
    flex-direction: column;
    justify-content: space-around;
    width: 100%;
    height: 150px;
    padding: 0 35px;
    margin-bottom: 30px;
    border: 1px solid rgba(112, 112, 112, 0.4314);
    border-radius: 3px;
    font-size: 20px;
    color: rgba(0, 0, 0, 0.9);
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

.condition {
    margin-bottom: 20px;
}

.box-con {
    display: flex;
    align-items: center;

    &._flex {
        align-items: center;
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
    color: #155DFF;
    border-bottom: 1px solid #155dff;
}

.line-count {
    font-size: 16px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #5C5E66;
}

/deep/.el-empty {
    margin: 0 auto;
}
</style>