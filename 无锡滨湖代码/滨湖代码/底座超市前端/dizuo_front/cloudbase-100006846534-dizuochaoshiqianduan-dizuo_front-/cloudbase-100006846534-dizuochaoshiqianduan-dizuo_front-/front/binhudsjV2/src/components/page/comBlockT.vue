<template>
    <!-- 容器组件 - 无边框的 -->
    <div class="wrap">
        <div class="top-wrap" :class="position">
            <div :style="{ width: position == 'left' ? '' : '136px' }">&nbsp;</div>
            <div class="wrap-top" :class="[position, tip ? 'flex' : '']">
                <div class="wrap-top-title">{{ title }}</div>
                <div class="tip" v-if="tip">
                    <svg t="1703402483937" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="4433" width="16" height="16">
                        <path
                            d="M512 64a448 448 0 1 0 0.064 896.064A448 448 0 0 0 512 64z m70.208 612.864c-33.792 50.752-68.224 89.92-126.08 89.92-39.488-6.464-55.744-34.816-47.168-63.616l74.432-246.528c1.856-6.08-1.152-12.48-6.656-14.464s-16.256 5.184-25.6 15.36l-44.928 54.144c-1.28-9.024-0.192-24.064-0.192-30.144 33.856-50.752 89.344-90.816 127.04-90.816 35.84 3.712 52.736 32.32 46.528 63.744l-74.944 247.744c-0.96 5.632 1.984 11.264 7.04 13.056 5.504 1.984 17.088-5.184 26.432-15.424l44.928-54.08c1.216 9.088-0.832 25.088-0.832 31.104z m-9.984-322.048c-28.48 0-51.52-20.672-51.52-51.264 0-30.528 23.104-51.264 51.52-51.264 28.48 0 51.52 20.736 51.52 51.264 0 30.592-23.04 51.264-51.52 51.264z"
                            fill="#8a8a8a" p-id="4434" data-spm-anchor-id="a313x.search_index.0.i0.757d3a81f35H2E"
                            class="selected"></path>
                    </svg>
                    {{ tip }}
                </div>
            </div>
            <div class="wrap-top-more" v-if="more" :style="{ width: position == 'left' ? '' : '136px' }"
                @click="fClickMore(more)">查看更多</div>
            <div v-else :style="{ width: position == 'left' ? '' : '136px' }"></div>
        </div>
        <el-divider width="100%" v-if="!noline"></el-divider>
        <slot></slot>
    </div>
</template>

<script>

export default {
    name: 'ComBlockT',
    components: {},
    props: ['title', 'position', 'more', 'noline', 'tip'],
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
        fClickMore(type) {
            this.$emit('fClickMore', type)
        },
    }
}
</script>
<style lang='less' scoped>
.top-wrap {
    display: flex;
    align-items: center;
    justify-content: space-between;

    &.left {
        background: url('../../assets/image/new/title_logo.png') no-repeat left;
        justify-content: start;
        padding-left: 38px;
    }

    &.center {
        text-align: center;
        background: url('../../assets/image/comments/tit_bg2.png') no-repeat center center;
        background-position: center center;
    }
}

.wrap-top {
    display: flex;
    align-items: center;
    height: 60px;

    // margin-bottom: 30px;
    &.flex {
        justify-content: space-between;
        width: 100%;
    }

    &.center {
        justify-content: center;
    }
}

.tip {
    display: flex;
    align-items: center;
    font-size: 16px;
    color: #aaaaaa;
}

.wrap-top-more {
    width: 136px;
    height: 46px;
    border-radius: 29px;
    box-shadow: 0px 0px 9px 0px rgba(47, 107, 255, 0.3);
    background: #FFFFFF;
    color: #19aff1;
    font-size: 18px;
    color: #2F6BFF;
    text-align: center;
    line-height: 46px;
    cursor: pointer;
    user-select: none;

    &:hover {
        opacity: .75;
    }
}

.wrap-top-icon {
    // display: flex;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-color: #5b87ea;
    overflow: hidden;
    margin: 0 20px
}

.wrap-top-title {
    margin-top: -5px;
    font-size: 30px;
    font-weight: bold;
    color: #1A1B1F;
}
</style>