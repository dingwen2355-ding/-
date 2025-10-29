<template>
    <!-- 最新推荐 -->
    <div>
        <div class="home-banner">
            <!-- 轮播图搜索框 -->
            <div class="search-wrap">
                <div class="search-title">无锡市滨湖区综合应用服务平台</div>
                <el-input placeholder="请输入你要查找的内容" v-model="sKeyWords" class="search-input">
                    <el-button type="primary" slot="append" icon="el-icon-search" @click="fReuest()">搜索</el-button>
                </el-input>
                <!-- <div class="search-keyword">关键字：人口 建筑 企业</div> -->
            </div>
        </div>
        <div class="box">
            <div class="box-con _flex">
                <div class="page-icon"></div>
                首页 >> 最新推荐
            </div>
        </div>
        <div class="search-box-wrap">
            <div class="search-box _flexw">
                <template v-if="aRecommend && aRecommend.length > 0">
                    <comList-block :data="aRecommend.slice((currentPage4 - 1) * 9, (currentPage4) * 9)"
                        :params="{ ...oParams }" @fCliclDetails="fGoToDetails" v-if="aRecommend && aRecommend.length > 0"></comList-block>
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
import { URLS_USERINFO } from '@/api/url.js'
import comListBlock from '../../components/page/comListBlock.vue';// 列表组件

import fGetAppListAsync from '@/api/setAppList.js'

export default {
    name: '',
    components: {
        comListBlock,// 列表组件
    },
    props: [''],
    data() {
        return {
            aAllAppData: [],// 所有的应用
            aRecommend: [],// 推荐的应用
            aRecommendCopy: [],// 推荐的应用副本
            sKeyWords: '',// 关键字
            currentPage4: 1,// 页码
            // 数据总数
            nDataCount: 0,
            oParams: {
                title: 'title', details: 'description', unit: 'deptName'
            },
        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {
        this.fRequestAllpp();// 请求所有的组件/应用/工具
    },
    beforeDestroy() {

    },
    methods: {
        // 重置按钮
        fResect() {
            this.sKeyWords = '';
            this.currentPage4 = 1;
            this.aRecommend = this.aRecommendCopy;
            // this.fClickZyflType({ name: '全部', value: 'all' },);
        },
        fReuest() {
            // console.log(111)
            this.currentPage4 = 1;
            if (this.sKeyWords) {
                this.aRecommend = this.aRecommendCopy.filter(item => item.title.indexOf(this.sKeyWords) >= 0);
            } else {
                this.aRecommend = this.aRecommendCopy;
            }
            this.nDataCount = this.aRecommend?.length || 0;
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
        async fRequestAllpp() {
            const res = await fGetAppListAsync();
            // res.filter(item=>item.sTypeName == '组件')
            let aData = res.filter(item => item.sTypeName.indexOf('应用') >= 0);
            // console.log(aData)
            // aData.forEach(element => {
            //     console.log(JSON.parse(element.appScreenshot))
            // });
            this.aAllAppData = aData;// 全部的应用
            // 推荐的应用
            this.aRecommend = this.fGetLastWeekData(this.fMsgSort(aData)) ;
            this.aRecommendCopy = this.fGetLastWeekData(this.fMsgSort(aData));
            // console.log(this.fGetLastWeekData(this.fMsgSort(aData)))
            // this.aRecommend = aData
            // this.aRecommendCopy = aData
            this.nDataCount = aData.length
            // console.log(this.aAllAppData)

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
                console.log(item.createTime,new Date().getTime() - new Date(item.createTime).getTime())
                if (new Date().getTime() - new Date(item.createTime).getTime() <= 604800000) {
                    console.log(item)
                    return item
                }
            })
        },
        // 请求所有的组件/应用/工具
        // fRequestAllpp() {
        //     this.$fRequest({
        //         url: URLS_USERINFO.u_applicationList,
        //     }).then(res => {
        //         // console.log(res, '请求所有的组件/应用/工具')
        //         let aData = [];
        //         let aComData = res.data.filter(item => item.title.indexOf('应用') >= 0)[0];// 获取所有组件
        //         aComData.children.forEach(item => {
        //             if (item.children && item.children.length > 0) {
        //                 aData.push(...item.children)
        //             }
        //         })
        //         this.aAllAppData = aData;// 全部的应用
        //         // 推荐的应用
        //         this.aRecommend = this.fMsgSort(aData);
        //         this.aRecommendCopy = this.fMsgSort(aData);
        //         this.nDataCount = aData.length
        //         // console.log(this.aAllAppData)
        //         console.log(this.aRecommend)
        //     })
        // },
        //传入一个需要排序的数组
        fMsgSort(obj) {
            obj.sort((a, b) => {
                let t1 = new Date(Date.parse(a.createTime.replace(/-/g, "/")))
                let t2 = new Date(Date.parse(b.createTime.replace(/-/g, "/")))
                return t2.getTime() - t1.getTime()
            })
            return obj
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
.home-banner {
    position: relative;
    width: 100%;
    height: 617px;
    // margin-bottom: 100px;
    background: url('../../assets/image/home/bg.png') no-repeat center / 100% 617px;
}


.search-wrap {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
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