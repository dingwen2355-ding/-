<script setup>
import { ref, onMounted, defineProps } from "vue"
const props = defineProps({
    title: String
})
</script>
<template>
    <div class="panel-s">
        <div class="title">
            <div class="titlebg">{{ title }}</div>
        </div>
        <div class="content">
            <slot></slot>
        </div>
    </div>
</template>
<style scoped lang="scss">
.panel-s {
    width: 836px;
    height: 250px;
    background: url("@/assets/img/panelbg-m.png") no-repeat;
    background-size: cover;

    .title {
        width: 100%;
        height: 48px;
        background: url("@/assets/img/title-m.png") no-repeat;
        background-size: cover;

        .titlebg {
            margin-left: 238px;
            width: 360px;
            height: 48px;
            line-height: 48px;
            text-align: center;
            background: url("@/assets/img/titleline.png") no-repeat;
            background-size: 360px 13px;
            background-position-y: 18px;
            font-size: 20px;
            color: #fff;
        }

    }

    .content {
        padding: 10px;
        margin: 10px;
        width: 808px;
        height: 169px;
    }
}
</style>