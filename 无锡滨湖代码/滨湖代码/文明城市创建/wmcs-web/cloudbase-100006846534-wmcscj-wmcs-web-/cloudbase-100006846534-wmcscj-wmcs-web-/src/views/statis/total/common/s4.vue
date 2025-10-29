<template>
    <baseDiyChart :chartData="data1" :upDataFuc="upDataFucLeft" />
</template>
<script>
export default {
    data() {
        return {
            data1: [],
            option1: {
                // color: ['#6395f9', '#62daab', '#12908c'],
                title: {
                    textAlign: 'center',
                    top: 'middle'
                },
                legend: {
                    orient: 'vertical',
                    top: 'center',
                    right: 'right',
                    icon: 'circle'
                },
                series: [
                    {
                        type: 'pie',
                        center: ['40%', '50%'],
                        radius: ['0%', '60%'],
                        // hoverAnimation: false, // 是否开启hover扇形区域放大缩小
                        // avoidLabelOverlap: false,
                        data: []
                    }
                ]
            }
        }
    },
    mounted() {
        this.reSearch()
    },
    methods: {
        reSearch(data) {
            this.data1 = [
                { name: '河埒街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '荣巷街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '蠡湖街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '蠡湖街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '华庄街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '太湖街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '雪浪街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '马山街道', value: Math.round(Math.random() * 100) + 200 },
                { name: '胡埭镇', value: Math.round(Math.random() * 100) + 200 }
            ]
        },
        upDataFucLeft() {
            this.option1.series[0].data = this.data1
            return this.option1
        }
    }
}
</script>
