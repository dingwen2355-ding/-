const eventRouter = {
    path: 'event',
    name: 'event',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 阈值管理
            path: 'threshold',
            name: 'event-threshold',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: '_add',
                    name: 'event-threshold-add',
                    component: () => import('@/views/event/threshold/add.vue')
                },
                {
                    path: '_edit',
                    name: 'event-threshold-edit',
                    component: () => import('@/views/event/threshold/edit.vue')
                },
                {
                    path: '_info',
                    name: 'event-threshold-info',
                    component: () => import('@/views/event/threshold/info.vue')
                },
                {
                    path: 'list',
                    name: 'event-threshold-List',
                    component: () => import('@/views/event/threshold/list.vue')
                }
            ]
        },
        {
            // 时间配置
            path: 'timeconfig',
            name: 'event-timeconfig',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: '_add',
                    name: 'event-timeconfig-add',
                    component: () => import('@/views/event/timeconfig/add.vue')
                },
                {
                    path: '_edit',
                    name: 'event-timeconfig-edit',
                    component: () => import('@/views/event/timeconfig/edit.vue')
                },
                {
                    path: '_info',
                    name: 'event-timeconfig-info',
                    component: () => import('@/views/event/timeconfig/info.vue')
                },
                {
                    path: 'list',
                    name: 'event-timeconfig-List',
                    component: () => import('@/views/event/timeconfig/list.vue')
                }
            ]
        },
        {
            // 告警信息
            path: 'warning',
            name: 'event-warning',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'event-warning-List',
                    component: () => import('@/views/event/warning/list.vue')
                }
            ]
        },
        {
            // 现象工单
            path: 'appearance',
            name: 'event-appearance',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'event-appearance-List',
                    component: () => import('@/views/event/appearance/list.vue')
                },
                {
                    path: '_add',
                    name: 'event-appearance-add',
                    component: () => import('@/views/event/appearance/add.vue')
                }
            ]
        },
        {
            // 工单总览
            path: 'record',
            name: 'event-record',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'event-Record-List',
                    component: () => import('@/views/event/record/list.vue')
                },
                {
                    path: '_info',
                    name: 'event-Record-Info',
                    component: () => import('@/views/event/record/info.vue')
                },
                {
                    path: '_handle',
                    name: 'event-Record-Handle',
                    component: () => import('@/views/event/record/handle.vue')
                }
            ]
        },
        {
            // 办结审核
            path: 'finishConfirm',
            name: 'event-finishConfirm',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'event-finishConfirm-List',
                    component: () => import('@/views/event/finishConfirm/list.vue')
                }
            ]
        }
    ]
}

export default eventRouter
