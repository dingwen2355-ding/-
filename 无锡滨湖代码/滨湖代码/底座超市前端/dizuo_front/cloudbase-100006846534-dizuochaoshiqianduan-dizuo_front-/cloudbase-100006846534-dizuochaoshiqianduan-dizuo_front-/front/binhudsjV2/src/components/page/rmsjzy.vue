<template>
    <!-- 热门数据资源 -->
    <div class="rmsjzy">
        <div class="rmsjzy-item" v-for="(item, idx) in data" :key="'data' + idx">
            <div :class="['_pm' + (idx + 1), 'rmsjzy-item-icon']">{{ idx + 1 }}</div>
            <div class="rmsjzy-item-box">
                <div class="item-box-tit">{{ item.table_cn_name || '--' }}</div>
                <!-- <div class="item-box-val text-ellipsis-2">所属部门：{{ item.dept_name || '--' }}</div> -->
                <div class="item-box-val text-ellipsis-2">数据来源：{{ item.system_name || '--' }}</div>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: '',
    components: {},
    props: ['data'],
    data() {
        return {

        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {

    },
    beforeDestroy() {

    },
    methods: {

    }
}
</script>
<style lang='less' scoped>

.rmsjzy-item {
    position: relative;
    display: flex;
    justify-content: space-around;
    height: 130px;
    padding: 35px 30px 0;

    &::after {
        content: '';
        position: absolute;
        bottom: 0;
        // left: 50%;
        // width: 50%;
        margin: 0 auto;
        width: 724px;
        height: 1px;
        background-color: #eaeaea;
    }
}

.rmsjzy-item-icon {
    height: 40px;
    width: 40px;
    line-height: 40px;
    text-align: center;
    font-size: 26px;
    color: #000;
    background: url('../../assets/image/numberbottom/pm.png') no-repeat center / 100%;

    &._pm1 {
        color: #fff;
        background-image: url('../../assets/image/numberbottom/pm1.png');
    }

    &._pm2 {
        color: #fff;
        background-image: url('../../assets/image/numberbottom/pm2.png');
    }

    &._pm3 {
        color: #fff;
        background-image: url('../../assets/image/numberbottom/pm3.png');
    }
}

.rmsjzy-item-box {
    width: 660px;
}

.item-box-tit {
    font-size: 26px;
    color: #191919;
    margin-bottom: 15px;
}

.item-box-val {
    font-size: 20px;
    color: #474747;
}
</style>