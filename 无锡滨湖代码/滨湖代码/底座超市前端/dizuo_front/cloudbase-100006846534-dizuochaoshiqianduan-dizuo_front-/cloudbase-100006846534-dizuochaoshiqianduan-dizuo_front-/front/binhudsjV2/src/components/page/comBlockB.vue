<template>
    <!-- 容器组件 - 带边框的 -->
    <div class="block">
        <!-- <div class="wrap-top">
            <div class="wrap-top-icon"></div>
            <div class="wrap-top-title">{{ title }}</div>
        </div> -->
        <div class="top-wrap" :class="bgtype">
            <!-- <div class="top-wrap-null" v-if="type"></div> -->
            <div class="wrap-top">
                <div class="wrap-top-title">{{ title }}</div>
                <div class="wrap-top-num" v-if="num">{{ num }}</div>
            </div>
            <slot name="input"></slot>
        </div>
        <!-- <el-divider width="100%"></el-divider> -->
        <slot></slot>
    </div>
</template>

<script>

export default {
    name: 'ComBlockT',
    components: {},
    props: ['title', 'type', 'bgtype','num'],
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
.top-wrap {
    display: flex;
    align-items: center;
    justify-content: space-between;
    // padding: 20px 20px 0;
    background: url('../../assets/image/comments/tit_bg1.png') no-repeat center;
    background-size: 100% 100%;

    &.bg1 {
        background: url('../../assets/image/comments/tit_bg5.png') no-repeat center;
        background-size: 100% 100%;
    }

}

.top-wrap-null {
    width: 280px;
}

.block {
    border: 5px solid #f2f2f2;
    margin: 0 ;
}

.wrap-top {
    display: flex;
    align-items: center;
    height: 60px;
    padding-left: 38px;
}

.wrap-top-icon {
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-color: #5b87ea;
    overflow: hidden;
    margin: 0 20px
}

.wrap-top-title {
    font-size: 28px;
    color: #1A1B1F;
    font-weight: bold;
}
.wrap-top-num{
    margin-left: 30px;
    background-color: red;
    padding: 0 20px ;
    text-align: center;
    line-height: 40px;
    height: 40px;
    font-size: 20px;
    border-radius: 20px;
    color: #f2f2f2;
}
</style>