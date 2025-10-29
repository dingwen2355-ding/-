<template>
    <view class="content">
        <div class="top">
            <div class="menu">
                <div class="title">{{ title }}</div>
                <UniSegmentedControl class="swicth" :current="current" :values="items" @clickItem="onClickItem"
                    styleType="text" activeColor="#ffffff"></UniSegmentedControl>
            </div>
            <div class="icons">
                <uni-icons type="tune" size="36" color="#fff" @click="logout()"></uni-icons>
            </div>
        </div>
        <div class="view">
            <Zonglan v-if="current == 0"></Zonglan>
            <zerenzhuti v-if="current == 1"></zerenzhuti>
            <Leixing v-if="current == 2"></Leixing>
            <Qushi v-if="current == 3"></Qushi>
        </div>
        <!-- <div class="bottom">
            <div class="item active" @click="goto('./../screen/home')">
                <img src="static/images/b-1-a.png" alt="">
                <div>分析总览</div>
            </div>
            <div class="item" @click="goto('./../screen/jixiaokaohe')">
                <img src="static/images/b-2.png" alt="">
                <div>绩效考评</div>
            </div>
            <div class="item" @click="goto('./../screen/gongdan')">
                <img src="static/images/b-3.png" alt="">
                <div>工单总览</div>
            </div>
        </div> -->
    </view>
</template>
  
<script>
import Leixing from './leixing.vue';
import Zonglan from './zonglan.vue';
import zerenzhuti from './zerenzhuti.vue';
import Qushi from './qushi.vue';
import UniSegmentedControl from '@/plugins/uni-segmented-control/uni-segmented-control.vue';
import uniIcons from "../../plugins/uni-icons/uni-icons.vue"
export default {
    components: { UniSegmentedControl, Zonglan, zerenzhuti, Leixing, Qushi, uniIcons },
    data() {
        return {
            title: "分析总览",
            items: ['总览', '责任主体', '类型', '分析'],
            current: 0
        };
    },
    onLoad() { },
    methods: {
        logout() {
            this.goto("./../gongdan/workDesk")
        },
        goto(url) {
            uni.navigateTo({
                url: url
            });
        },
        onClickItem(e) {
            if (this.current != e.currentIndex) {
                this.current = e.currentIndex;
            }
        }
    }
};
</script>

<style lang="less">
/deep/ .segmented-control__text {
    font-size: 18px;
    font-weight: 700;
    color: #fff !important;
}

/deep/ .segmented-control__item--text {
    border-bottom-style: solid;
    border-bottom-width: 6px;
    padding: 2px 0;
}

page {
    background: #f4f5fd;
}

.top {
    width: 750upx;
    height: 213upx;
    background-color: #4770bf;
    position: fixed;
    top: 0;
    left: 0;

    .icons {
        position: absolute;
        top: 50upx;
        right: 40upx;
        z-index: 9999;
    }

    .menu {
        width: 750upx;
        height: 100%;
        background: url("static/images/menu.png") no-repeat;
        background-size: cover;
        background-position: -10upx -80upx;

        .title {
            text-align: center;
            line-height: 140upx;
            font-size: 42upx;
            color: #fff;
            letter-spacing: 15upx;
            font-weight: 900;
        }

        .swicth {
            margin-top: 0upx;
        }
    }

}

.view {
    position: absolute;
    top: 213upx;
    left: 0;
    height: calc(100vh - 213upx - 100upx);
    overflow: auto;

}
.bottom {
    position: fixed;
    bottom: 0;
    left: 0;
    height: 100upx;
    width: 100%;
    display: flex;
    flex-direction: row;
    border-top: 1px solid #00000020;

    .item {
        width: 33.3%;
        line-height: 50upx;
        text-align: center;
        background-color: #fff;
        border-right: 1px solid #ffffff50;
        color: #000;
        display: flex;
        flex-direction: column;
        font-size: 14upx;
        align-items: center;
        border-right: 1px solid #00000020;
        justify-content: space-around;
        font-size: 24upx;
        img {
            width: 48upx;
            height: 48upx;
        }
    }

    .item:nth-child(3) {
        border-right: none
    }

    .active {
        color: #2194f3;
    }
}

</style>
  