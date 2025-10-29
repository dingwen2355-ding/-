<script setup>
import { ref, onMounted } from "vue"
import { EchartsBarRow } from "@/utils/jc-echarts/EchartsBarRow";
import axios from "axios";
onMounted(() => {
    loadBJL()
})
function loadBJL(params) {
    $axios_get(IP + "/screen/api/civilization/advancedCountByRegion").then(res => {
        let data = res.data.data;
        console.log(data);
        let xData=[],yData1=[],yData2=[],yData3=[],yData4=[]
        for (const key in data) {
            if (Object.hasOwnProperty.call(data, key)) {
                const element = data[key];
                xData.push(key)
                yData1.push(element.zmrw)
                yData2.push(element.sbhr)
                yData3.push(element.xsdhsn)
                yData4.push(element.gwxlfbb)
            }
        }
        EchartsBarRow({
            autoplay: true,
            el: "qyzmzt",
            padding: ["18%", "3%", "3%", "3%"],
            legend: {
                show: true,
                data: ["最美人物", "身边好人", "新时代好少年", "岗位学雷锋标兵"],
            },
            x: {
                show: true,
                data: xData 
            },
            y: {
                show: true,
            },
            series: [
                {
                    name: "最美人物",
                    barWidth: "16%",
                    label: {
                        show: false,
                    },
                    data: yData1
                },
                {
                    name: "身边好人",
                    barWidth: "16%",
                    label: {
                        show: false,
                    },
                    data: yData2
                },
                {
                    name: "新时代好少年",
                    barWidth: "16%",
                    label: {
                        show: false,
                    },
                    data: yData3
                },
                {
                    name: "岗位学雷锋标兵",
                    barWidth: "16%",
                    label: {
                        show: false,
                    },
                    data: yData4
                }
            ]
        })
    })


}
</script>
<template>
    <div class="panel">
        <div id="qyzmzt"></div>
    </div>
</template>
<style scoped lang="scss">
.panel {
    #qyzmzt {
        width: 100%;
        height: 172px;
    }
}
</style>