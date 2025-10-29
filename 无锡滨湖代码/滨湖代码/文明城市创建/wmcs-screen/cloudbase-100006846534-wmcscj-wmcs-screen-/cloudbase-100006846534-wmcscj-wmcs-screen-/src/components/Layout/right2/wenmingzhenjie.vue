<script setup>
import { ref, onMounted } from "vue"
import axios from "axios";
let data=ref([])
onMounted(()=>{
    axios.get(IP+"/screen/api/civilization/townList").then(res=>{
        data.value = res.data.data.records;
    })
})
</script>
<template>
    <div class="wmzj flex-row">
        <div v-for="(item,index) in data" :key="index" class="item flex-row">
            <div></div>
            <div>{{item.name}}</div>
        </div>
    </div>
</template>
<style scoped lang="scss">
.wmzj {
    flex-wrap: wrap;

    .item {
        height: 60px;
        width: 25%;
        justify-content: center;
        color: #fff;
        font-size: $font-size-middle;

        div:nth-child(1) {
            width: 48px;
            height: 48px;
            background: url("@/assets/img/top3.png")no-repeat;
            background-size: contain;
            text-align: center;
            line-height: 50px;
        }
    }

    .item:nth-child(1) {
        width: 100%;

        div:nth-child(1) {
            width: 48px;
            height: 48px;
            background: url("@/assets/img/top0.png")no-repeat;
            background-size: contain;
            text-align: center;
            line-height: 50px;
        }
    }

    .item:nth-child(2) {
        width: 33%;

        div:nth-child(1) {
            width: 48px;
            height: 48px;
            background: url("@/assets/img/top1.png")no-repeat;
            background-size: contain;
            text-align: center;
            line-height: 50px;
        }
    }

    .item:nth-child(3) {
        width: 33%;

        div:nth-child(1) {
            width: 48px;
            height: 48px;
            background: url("@/assets/img/top2.png")no-repeat;
            background-size: contain;
            text-align: center;
            line-height: 50px;
        }
    }

    .item:nth-child(4) {
        width: 33%;
    }

    .item:nth-child(5) {
        width: 33%;
    }

    .item:nth-child(6) {
        width: 33%;
    }

    .item:nth-child(7) {
        width: 33%;
    }
}
</style>