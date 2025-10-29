<script setup>
import { ref, onMounted } from "vue"
</script>
<template>
    <div class="left-righttwo">
        <div class="left">
            <slot name="l"></slot>
        </div>
        <div class="right">
            <div>
                <slot name="r-t"></slot>
                <span>
                    <slot name="r-t-s"></slot>
                </span>
            </div>
            <div>
                <slot name="r-b"></slot> <span>
                    <slot name="r-b-s"></slot>
                </span>
            </div>
        </div>
    </div>
</template>
<style scoped lang="scss">
.left-righttwo {
    width: 95%;
    display: flex;
    flex-direction: row;
    justify-content: space-around;

    align-items: center;

    .left {
    }

    .right {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: center;

        div:nth-child(1) {
            width: 100%;
            font-size: 20px;
            font-weight: blod;
            color: #fff;
            text-align: center;

            span {
                font-size: 12px;
            }
        }

        div:nth-child(2) {
            width: 100%;
            font-size: 16px;
            color: #fff;
            text-align: center;

            span {
                font-size: 12px;
            }
        }
    }
}
</style>