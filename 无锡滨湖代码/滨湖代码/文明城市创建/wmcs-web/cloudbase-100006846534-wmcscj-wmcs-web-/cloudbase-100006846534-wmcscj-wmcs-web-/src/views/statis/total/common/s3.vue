<template>
    <baseDiyChart :chartData="data1" :upDataFuc="upDataFucLeft" />
</template>
<script>
export default {
    data() {
        return {
            v_loading: false,
            dataOrigin: {},
            data1: [],
            option1: {
                color: ['#6395f9', '#62daab', '#12908c'],
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['2023-06-04', '2023-06-05', '2023-06-06', '2023-06-07', '2023-06-06', '2023-06-07', '2023-06-08']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [150, 230, 224, 218, 135, 147, 260],
                        type: 'line',
                        areaStyle: {}
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
                { 'name': '2023-01-01', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-02', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-03', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-04', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-05', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-06', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-07', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-08', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-09', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-10', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-11', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-12', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-13', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-14', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-15', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-16', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-17', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-18', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-19', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-20', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-21', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-22', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-23', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-24', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-25', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-26', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-27', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-28', 'value': Math.round(Math.random() * 100) + 10 },
                { 'name': '2023-01-29', 'value': Math.round(Math.random() * 100) + 10 }
            ]
        },
        upDataFucLeft() {
            this.option1.series[0].data = this.data1
            return this.option1
        }
    }
}
</script>
