<script setup>
import { ref, onMounted } from "vue"
</script>
<template>
    <div class="top-bottom">
        <div>
            <slot name="t"></slot>
            <span>
                <slot name="t-s"></slot>
            </span>
        </div>
        <div>
            <slot name="b"></slot>
            <span>
                <slot name="b-s"></slot>
            </span>
        </div>
    </div>
</template>
<style scoped lang="scss">
.top-bottom {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;

    div:nth-child(1) {
        width: 100%;
        color: #00F0FF;
        font-size: 20px;
        text-align: center;

        span {
            color: #00F0FF;
            font-size: 12px;
        }
    }

    div:nth-child(2) {
        width: 100%;
        color: #fff;
        font-size: 16px;
        text-align: center;

        span {
            color: #fff;
            font-size: 12px;
        }
    }
}
</style>