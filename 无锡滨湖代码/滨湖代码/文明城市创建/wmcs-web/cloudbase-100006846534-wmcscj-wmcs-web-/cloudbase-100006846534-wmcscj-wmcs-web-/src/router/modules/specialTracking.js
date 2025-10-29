const specialTrackingRouter = {
    path: 'specialTracking',
    name: 'specialTracking',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 重点问题
            path: 'mainEventList',
            name: 'spectracmainEventList',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'spectracmainEventList-list',
                    component: () => import('@/views/specialTracking/mainEventList/list.vue')
                }
            ]
        },
        {
            // 重点点位
            path: 'mainPointsList',
            name: 'spectracmainPointsList',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'spectracmainPointsList-list',
                    component: () => import('@/views/specialTracking/mainPointsList/list.vue')
                }
            ]
        },
        {
            // 专项监测
            path: 'specialMonitoring',
            name: 'specialMonitoringList',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'specialMonitoringList-list',
                    component: () => import('@/views/specialTracking/specialMonitoring/list.vue')
                }
            ]
        }
    ]
}

export default specialTrackingRouter
