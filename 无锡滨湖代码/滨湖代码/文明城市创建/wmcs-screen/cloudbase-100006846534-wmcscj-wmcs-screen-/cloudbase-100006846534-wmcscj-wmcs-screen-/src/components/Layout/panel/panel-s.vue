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
    width: 410px;
    height: 250px;
    background: url("@/assets/img/panelbg.png") no-repeat;
    background-size: cover;

    .title {
        width: 100%;
        line-height: 46px;
        text-align: center;
        background: url("@/assets/img/subtitlebg.png") no-repeat;
        background-size: cover;
        font-size: 20px;
        color: #fff;
        letter-spacing: 2px;
    }

    .content {
        padding: 10px;
        margin: 10px;
        width: 382px;
        height: 169px;
    }
}
</style>