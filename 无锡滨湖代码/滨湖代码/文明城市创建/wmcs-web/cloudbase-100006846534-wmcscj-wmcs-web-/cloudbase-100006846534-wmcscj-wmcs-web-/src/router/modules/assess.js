const assessRouter = {
    path: 'assess',
    name: 'assess',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 月度评测
            path: 'month',
            name: 'monthAssess',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'monthAssessList',
                    component: () => import('@/views/assess/month/list.vue')
                }
            ]
        },
        {
            // 季度评测
            path: 'evaluating',
            name: 'evaluatingAssess',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'evaluatingAssessList',
                    component: () => import('@/views/assess/evaluating/list.vue')
                }
            ]
        },
        {
            // 年度评测
            path: 'year',
            name: 'yearAssess',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'yearAssessList',
                    component: () => import('@/views/assess/year/list.vue')
                }
            ]
        },
        {
            // 区指数
            path: 'regionIndex',
            name: 'regionIndexAssess',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'assess-regionIndex-list',
                    component: () => import('@/views/assess/regionIndex/list.vue')
                },
                {
                    path: '_add',
                    name: 'assess-regionIndex-add',
                    component: () => import('@/views/assess/regionIndex/add.vue')
                }
            ]
        },
        {
            // 文明程度指数
            path: 'civilizationLevel',
            name: 'civilizationLevelAssess',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'assess-civilizationLevel-list',
                    component: () => import('@/views/assess/civilizationLevel/list.vue')
                },
                {
                    path: '_add',
                    name: 'assess-civilizationLevel-add',
                    component: () => import('@/views/assess/civilizationLevel/add.vue')
                }
            ]
        }
    ]
}

export default assessRouter
