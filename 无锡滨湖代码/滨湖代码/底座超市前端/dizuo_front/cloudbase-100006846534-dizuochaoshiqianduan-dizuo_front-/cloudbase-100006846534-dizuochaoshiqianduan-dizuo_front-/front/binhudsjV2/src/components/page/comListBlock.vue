<template>
    <!-- 列表的外框 -->
    <div class="list-wrap">
        <div class="list" v-for="(item, idx) in data" :key="'data' + idx">
            <!-- <img v-if="item.icon" :src="item.icon" alt="" class="list-img" />
            <div v-else class="list-img _bg"></div> -->
            <template v-if="item.appScreenshot && JSON.parse(item.appScreenshot).length > 0 >= 0">
                <img :src="JSON.parse(item.appScreenshot)[0]" class="list-img" alt="">
            </template>
            <img :src="item.appScreenshot ? JSON.parse(item.appScreenshot)[0] : item.icon" v-else class="list-img" alt="">
            <div class="list-con">

                <div class="list-con-tit ">
                    <div class="text-ellipsis-1 con-tit">{{ item[params.title] || item.title }}</div>
                   
                </div>
                <div class="list-con-details _text text-ellipsis-2" v-if="type == 'sjzy'">数据来源:{{ item.system_name }}
                </div>
                <div class="list-con-details text-ellipsis-2" v-else>{{ item[params.details] || '暂无' }}</div>

                <div class="list-con-box" v-if="type == 'sjzy'">
                    <div class="con-box-unit">所属单位: {{ item[params.unit] }}</div>
                    <div class="con-box-unit">更新时间: {{ fSetDate(item.updated_at) }} </div>
                </div> 
                <div class=" list-con-box" v-else>
                    <div class="con-box-unit">入驻时间: {{ item.updateTime }}</div>
                    <div class="con-box-unit">访问量: {{ item.fangwenliang }}</div>
                    <div class="con-box-unit">申请量: {{ item.shengqingliang }}</div>
                </div>
                <div class=" list-con-box"  v-if="type !== 'sjzy'">
                    <!-- <div class="con-box-unit">更新时间: {{ item.updateTime }}</div> -->
                    <div class="list-con-btn" >
                        <div class="con-btn _bg1" v-if="item.appModuleName">{{ item.appModuleName }}</div>
                        <div class="con-btn _bg2" v-if="item.sourceName">{{ item.sourceName }}</div>
                        <div class="con-btn _bg3" v-if="item.appTypeName">{{ item.appTypeName }}</div>
                    </div>
                    <div class="con-box-godet" @click.stop="fGoToDetails(item)">查看详情 >></div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: '',
    components: {},
    props: {
        data: {
            typeof: Array,
        },
        type: {
            typeof: String,
            // default: 'sjzy'
        },
        params: {
            typeof: Object,
            default: () => {
                return {
                    title: 'title',
                    details: 'details',
                    unit: 'unit',
                }
            }

        },
    },
    data() {
        return {

        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {
        // console.log(this.data,this.params)
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
        // 点击查看详情
        fGoToDetails(data) {
            this.$emit('fCliclDetails', { ...data })
        },
    }
}
</script>
<style lang='less' scoped>
.list-wrap {
    width: 100%;
    display: flex;
    // justify-content: space-between;
    flex-wrap: wrap;
}

.list {
    width: 31%;
    height: 430px;
    border-radius: 5px;
    margin: 10px 11px;
    overflow: hidden;
    box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.03);
}

.list-img {
    height: 220px;
    width: 100%;

    &._bg {
        background: url('../../assets/image/appmarket/listicon.png') no-repeat center;
        background-size: cover;
    }
}

.list-con {
    padding: 40px 20px;
    font-size: 13px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #5C5E66;
}

.list-con-tit {
    display: flex;
    justify-content: space-between;
    margin-bottom: 12px;
    font-size: 18px;
    font-family: PingFangSC-Medium, PingFang SC;
    font-weight: 500;
    color: #000000;
}

.con-tit {
    width: 60%;
    font-weight: bold;
}

.list-con-details {
    // min-height: 48px;
    text-indent: 2rem;
    margin-bottom: 22px;

    &._text {
        text-indent: 0;
    }
}

.list-con-box {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;
}

.con-box-godet {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #2F6BFF;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }
}

.list-con-btn {
    display: flex;
    align-items: center;
    justify-content: end;
}

.con-btn {
    margin-right: 7px;
    color: #fff;
    padding: 5px;
    border-radius: 5px;
    overflow: hidden;
    &._bg1{
        background-color: #4ae0b4;
    }
    &._bg2{
        background-color: #fbde2e;
    }
    &._bg3{
        background-color: #757c92;
    }
}
</style>