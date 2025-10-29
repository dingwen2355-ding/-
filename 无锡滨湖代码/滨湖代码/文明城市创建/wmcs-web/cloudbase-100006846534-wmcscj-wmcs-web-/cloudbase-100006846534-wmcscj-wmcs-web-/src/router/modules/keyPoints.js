const keyPointsRouter = {
    path: 'keyPoints',
    name: 'keyPoints',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 高发
            path: 'gf',
            name: 'keyPointsgf',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'keyPointsgf-list',
                    component: () => import('@/views/keyPoints/gf/list.vue')
                }
            ]
        },
        {
            // 集中
            path: 'jz',
            name: 'keyPointsgf',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'keyPointsjz-list',
                    component: () => import('@/views/keyPoints/jz/list.vue')
                }
            ]
        },
        {
            // 顽症
            path: 'wz',
            name: 'keyPointsgf',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'keyPointswz-list',
                    component: () => import('@/views/keyPoints/wz/list.vue')
                }
            ]
        }
    ]
}

export default keyPointsRouter
