<script setup>
import { ref, onMounted, defineProps } from "vue"
const props = defineProps({
    title: String
})
</script>
<template>
    <div class="panel-ms">
        <div class="title">
            <div class="titlebg">{{ title }}</div>
        </div>
        <div class="content">
            <slot></slot>
        </div>
    </div>
</template>
<style scoped lang="scss">
.panel-ms {
    width: 623px;
    height: 250px;
    background: url("@/assets/img/panelbg-mm.png") no-repeat;
    background-size: cover;

    .title {
        width: 100%;
        height: 48px;
        background: url("@/assets/img/title-mm.png") no-repeat;
        background-size: cover;

        .titlebg {
            margin-left: 97px;
            width: 430px;
            height: 48px;
            line-height: 48px;
            text-align: center;
            background: url("@/assets/img/titleline.png") no-repeat;
            background-size: 430px 13px;
            background-position-y: 18px;
            font-size: 20px;
            color: #fff;
        }
    }

    .content {
        padding: 10px;
        margin: 10px;
        width: 623px;
        height: 169px;
    }
}
</style>