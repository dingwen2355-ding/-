// 部分参数为自定义组件自用的参数,不是echarts的参数
// 1 yNumType: 1 为单条数据, 2 为多条数据,两种情况传入的参数格式有差异
// 各页面的报表配置汇总
// 默认的颜色配置
let colorArr = ['#21c8aa', '#7466bc', '#12908c']

let normal_con = {
    color: colorArr,
    title: { text: '', subtext: '', left: 'left' },
    tooltip: { trigger: 'item' },
    legend: { orient: 'horizontal', bottom: '0px', show: true },
    series: [
        {
            name: '',
            type: 'pie',
            radius: '60%',
            data: [],
            label: { normal: { show: true, formatter: '{b}: {c}({d}%)' }},
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
}

export const chartConfig = {
    normal_con
}
