<template>
    <div>
        <div class="numberbottom-box _flexd">
            <!-- <div class="numberbottom-box-tit">
                <div class=""></div>
                {{ data.title }}
                <div class="con-item-btn" @click="fGoToSearch(data.title)">更多>></div>
            </div> -->
            <div class="numberbottom-box-con">
                <template v-if="data.aRmsjzyData.length > 0">
                    <div class="box-con-item block" v-for="(item, idx) in fSetLoopData(data.aRmsjzyData)"
                        :key="'aRmsjzyData' + idx">
                        <!-- <div :class="[data.title == '最新组件' ? '_zjimg' : '_gjimg', 'image']"></div> -->
                        <div class="userinfo-item-con">
                            <div class="userinfo-item-title text-ellipsis-1" :title="item.title">{{ item.title }}</div>
                            <div class="userinfo-item-detail text-ellipsis-2" :title="item.description"> {{ item.description
                                ||
                                '暂无' }}
                            </div>
                            <hr>
                            <div class="userinfo-item-box">
                                <div class="item-box-unit">应用单位：{{ item.deptName }}</div>
                                <div class="appmarket-more" @click.stop="fGoToDetails(item)">查看详情 ></div>
                            </div>
                        </div>

                    </div>
                </template>
                <template v-else>
                    <el-empty description="暂无数据" style="margin: 0 auto;"></el-empty>
                </template>
            </div>
        </div>
        <!-- 数据资源分类 -->
        <!-- <div class="numberbottom-box _flexe">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="data.currentPage4" :page-sizes="[100, 200, 300, 400]" :page-size="100"
                layout=" prev, pager, next,sizes, jumper" :total="400">
            </el-pagination>
        </div> -->
    </div>
</template>

<script>

export default {
    name: '',
    components: {},
    props: ['data'],
    data() {
        return {
            sRmzyType: this.data.sRmzyType
        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {
        // console.log(this.data.sRmzyType)
    },
    beforeDestroy() {

    },
    methods: {
        fSetLoopData(data) {
            if (data.length > 9) {
                return data.slice(0, 9);
            } else {
                return data;
            }
        },
        // 去搜搜页面
        fGoToSearch(tit) {
            switch (tit) {
                case '最新组件':
                    this.$store.commit('fSetNowPage', { type: 'numberbottom', page: 'numberbottom', component: 'sub-numsearchv2', name: 'zjNum' })
                    break;
                case '最新工具':
                    this.$store.commit('fSetNowPage', { type: 'numberbottom', page: 'numberbottom', component: 'sub-numsearchv2', name: 'gjNum' })
                    break;
            }
            // this.$store.commit('fSetNowPage', { type: 'numberbottom', page: 'numberbottom', component: 'sub-numsearchv2', name })
        },
        // 去详情页面
        fGoToDetails(item) {
            let oObj = this.$fSetData(item, ['appSecret'])
            // console.log('')
            localStorage.setItem('appDeatils',JSON.stringify(oObj))
            if (this.data.sRmzyType == 'ptnl') {
                this.$router.push({
                    path: "/digitalBinhu/baseDetail",
                    // query: {data:JSON.stringify(oObj)}
                })
            } else {
                this.$router.push({
                    path: "/appMarket/appDetails",
                    // query: {data:JSON.stringify(oObj)}
                })
            }

            // this.$store.commit('fSetDetailsData', item);
            // this.$store.commit('fSetNowPage', { type: 'appmarket', page: 'appdetails', component: 'sub-appdetails', source: 'all',name:this.data.sRmzyType })
        },
        // 热门资源切换
        fChangeRmzyType(item) {
            this.sRmzyType = item.type;
        },

        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
        }
    }
}
</script>
<style lang='less' scoped>
.numberbottom-box {
    padding: 50px 30px 0;
    display: flex;
    align-items: center;
    justify-content: space-between;

    &._flexw {
        flex-wrap: wrap;
        justify-content: start;
    }

    &._flexd {
        align-items: start;
        flex-direction: column;
    }

    &._flexe {
        // align-items: end;
        justify-content: end;
    }

}

.numberbottom-box-tit {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    color: rgba(0, 0, 0, 0.9);
    font-size: 36px;
    margin-bottom: 30px;
}

.numberbottom-box-detail {
    font-size: 20px;
    color: #474747;
    margin-bottom: 10px;
}

.numberbottom-box-type {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    height: 90px;
    padding: 0 45px;
    margin-bottom: 35px;
    background: rgba(255, 255, 255, 0.39);
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
    border-radius: 60px;
}

.box-type-item {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 140px;
    height: 48px;
    border-radius: 60px;
    font-size: 20px;
    color: #000;
    background: none;
    cursor: pointer;
    user-select: none;

    &._act {
        color: #fff;
        background-color: #266FE8;
    }

    &:hover {
        opacity: .75;
    }
}

.numberbottom-box-con {
    display: flex;
    align-items: center;
    // justify-content: space-between;
    flex-wrap: wrap;
    width: 100%;
}

.box-con-item {
    display: flex;
    align-items: center;
    // justify-content: space-around;
    margin: 0 1%;
    width: 31%;
    margin-bottom: 15px;
    padding: 35px 30px;
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

.con-item-tit {
    font-size: 20px;
    color: #000;

    &._size {
        font-size: 26px;
        // margin-bottom: 20px;
    }
}

.con-item-det {
    width: 100%;
    margin: 15px 0 20px;
    font-size: 20px;
    color: #474747;
}

.con-item-btn {
    display: flex;
    justify-content: end;
    color: #4483eb;
    font-size: 14px;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }
}

.userinfo-item-con {
    width: 470px;
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
</style>