const quotaRouter = {
    path: 'quota',
    name: 'quota',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 点位检查项
            path: 'checkItem',
            name: 'quotaCheckItem',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'quota-checkItemList',
                    component: () => import('@/views/quota/checkItem/list.vue')
                },
                {
                    path: '_add',
                    name: 'quota-checkItemAdd',
                    component: () => import('@/views/quota/checkItem/add.vue')
                },
                {
                    path: '_edit',
                    name: 'quota-checkItemEdit',
                    component: () => import('@/views/quota/checkItem/edit.vue')
                },
                {
                    path: '_info',
                    name: 'quota-checkItemInfo',
                    component: () => import('@/views/quota/checkItem/info.vue')
                }
            ]
        },
        {
            // 指标类型
            path: 'indicatorType',
            name: 'quotaIndicatorType',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'quota-indicatorTypeList',
                    component: () => import('@/views/quota/indicatorType/list.vue')
                },
                {
                    path: '_add',
                    name: 'quota-indicatorTypeAdd',
                    component: () => import('@/views/quota/indicatorType/add.vue')
                },
                {
                    path: '_edit',
                    name: 'quota-indicatorTypeEdit',
                    component: () => import('@/views/quota/indicatorType/edit.vue')
                },
                {
                    path: '_info',
                    name: 'quota-indicatorTypeInfo',
                    component: () => import('@/views/quota/indicatorType/info.vue')
                }
            ]
        },
        {
            // 指标管理
            path: 'indicator',
            name: 'quotaIndicator',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'quota-indicatorList',
                    component: () => import('@/views/quota/indicator/list.vue')
                },
                {
                    path: '_add',
                    name: 'quota-indicatorAdd',
                    component: () => import('@/views/quota/indicator/add.vue')
                },
                {
                    path: '_edit',
                    name: 'quota-indicatorEdit',
                    component: () => import('@/views/quota/indicator/edit.vue')
                },
                {
                    path: '_info',
                    name: 'quota-indicatorInfo',
                    component: () => import('@/views/quota/indicator/info.vue')
                }
            ]
        },
        {
            // 权重配置
            path: 'weight',
            name: 'quotaWeight',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'quotaWeightList',
                    component: () => import('@/views/quota/weight/list.vue')
                },
                {
                    path: '_add',
                    name: 'quotaWeightAdd',
                    component: () => import('@/views/quota/weight/add.vue')
                },
                {
                    path: '_edit',
                    name: 'quotaWeightEdit',
                    component: () => import('@/views/quota/weight/edit.vue')
                },
                {
                    path: '_info',
                    name: 'quotaWeightInfo',
                    component: () => import('@/views/quota/weight/info.vue')
                }
            ]
        }
    ]
}

export default quotaRouter
