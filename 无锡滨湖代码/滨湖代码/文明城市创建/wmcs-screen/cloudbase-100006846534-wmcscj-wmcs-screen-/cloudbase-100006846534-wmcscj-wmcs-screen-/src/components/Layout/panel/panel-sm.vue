<script setup>
import { ref, onMounted, defineProps } from "vue"
const props = defineProps({
    title: String
})
</script>
<template>
    <div class="panel-s">
        <div class="title">{{ title }}</div>
        <div class="content">
            <slot></slot>
        </div>
    </div>
</template>
<style scoped lang="scss">
.panel-s {
    width: 480px;
    height: 250px;
    background: url("@/assets/img/panelbg-sm.png") no-repeat;
    background-size: cover;

    .title {
        width: 480px;
        height: 48px;
        line-height: 48px;
        text-align: center;
        background: url("@/assets/img/subtitlebg2.png") no-repeat;
        background-size: cover;
        font-size: 20px;
        color: #fff;
        letter-spacing: 3px;
    }

    .content {
        padding: 10px;
        margin: 10px;
        width: 452px;
        height: 169px;
    }
}
</style>