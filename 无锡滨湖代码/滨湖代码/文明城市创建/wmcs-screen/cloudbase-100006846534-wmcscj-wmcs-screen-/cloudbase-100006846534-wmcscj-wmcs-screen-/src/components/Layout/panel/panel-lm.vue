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
    height: 528px;
    background: url("@/assets/img/panelbg-l.png") no-repeat;
    background-size: 100% 100%;

    .title {
        width: 410px;
        height: 48px;
        line-height: 48px;
        text-align: center;
        background: url("@/assets/img/subtitlebg.png") no-repeat;
        background-size: cover;
        font-size: 20px;
        color: #fff;
        letter-spacing: 5px;
    }

    .content {
        padding: 10px;
        margin: 10px;
        width: 382px;
        height: 438px;
    }
}
</style>