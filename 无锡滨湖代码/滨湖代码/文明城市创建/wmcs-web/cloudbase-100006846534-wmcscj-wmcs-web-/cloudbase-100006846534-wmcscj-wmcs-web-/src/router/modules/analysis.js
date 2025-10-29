const analysisRouter = {
    path: 'analysis',
    name: 'analysis',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 历史数据
            path: 'event',
            name: 'analysis-event',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'analysis-eventList',
                    component: () => import('@/views/analysis/event/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_info',
                    name: 'disposal-eventInfo',
                    component: () => import('@/views/analysis/event/info.vue')
                }
            ]
        },
        {
            // 事件总浏览
            path: 'all',
            name: 'analysis-eventAll',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'analysis-eventAllList',
                    component: () => import('@/views/analysis/all/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_info',
                    name: 'analysis-eventAllInfo',
                    component: () => import('@/views/analysis/all/info.vue')
                }
            ]
        },
        {
            // 统计分析
            path: 'statistic',
            name: 'analysis-statistic',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'analysis-statisticList',
                    component: () => import('@/views/analysis/statistic/list.vue')
                }
            ]
        },
        {
            // 趋势分析
            path: 'trend',
            name: 'analysis-trend',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'analysis-trendList',
                    component: () => import('@/views/analysis/trend/list.vue')
                }
            ]
        },
        {
            // 单位分析
            path: 'deptunit',
            name: 'analysis-deptunit',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'analysis-deptunitList',
                    component: () => import('@/views/analysis/deptunit/list.vue')
                }
            ]
        }
    ]
}

export default analysisRouter
