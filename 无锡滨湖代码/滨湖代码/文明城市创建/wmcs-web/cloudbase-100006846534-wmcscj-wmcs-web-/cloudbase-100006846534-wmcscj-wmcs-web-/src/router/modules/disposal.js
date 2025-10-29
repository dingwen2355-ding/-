const disposalRouter = {
    path: 'disposal',
    name: 'disposal',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 任务类型上报
            path: 'taskpublish',
            name: 'disposal-taskpublish',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-taskpublishList',
                    component: () => import('@/views/disposal/taskpublish/list.vue')
                },
                {
                    path: '_add',
                    name: 'disposal-taskpublishAdd',
                    component: () => import('@/views/disposal/taskpublish/add.vue')
                },
                {
                    path: '_edit',
                    name: 'disposal-taskpublishEdit',
                    component: () => import('@/views/disposal/taskpublish/edit.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-taskpublishInfo',
                    component: () => import('@/views/disposal/taskpublish/info.vue')
                }
            ]
        },
        {
            // 专班巡查
            path: 'specialReport',
            name: 'disposal-special',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-specialReportList',
                    component: () => import('@/views/disposal/specialReport/list.vue')
                },
                {
                    path: '_add',
                    name: 'disposal-specialReportAdd',
                    component: () => import('@/views/disposal/specialReport/add.vue')
                },
                {
                    path: '_edit',
                    name: 'disposal-specialReportEdit',
                    component: () => import('@/views/disposal/specialReport/edit.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-specialReportInfo',
                    component: () => import('@/views/disposal/specialReport/info.vue')
                }
            ]
        },
        {
            // 事件上报
            path: 'eventReport',
            name: 'disposal-eventReport',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-eventReportList',
                    component: () => import('@/views/disposal/eventReport/list.vue')
                },
                {
                    path: '_add',
                    name: 'disposal-eventReportAdd',
                    component: () => import('@/views/disposal/eventReport/add.vue')
                },
                {
                    path: '_edit',
                    name: 'disposal-eventReportEdit',
                    component: () => import('@/views/disposal/eventReport/edit.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-eventReportInfo',
                    component: () => import('@/views/disposal/eventReport/info.vue')
                }
            ]
        },
        {
            // 待分派
            path: 'eventGive',
            name: 'disposal-eventGive',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-eventGiveList',
                    component: () => import('@/views/disposal/eventGive/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-eventGiveInfo',
                    component: () => import('@/views/disposal/eventGive/info.vue')
                }
            ]
        },
        {
            // 待受理
            path: 'allocate',
            name: 'disposal-allocate',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-allocateList',
                    component: () => import('@/views/disposal/allocate/list.vue')
                },
                {
                    path: '_handle',
                    name: 'disposal-allocateHandle',
                    component: () => import('@/views/disposal/allocate/handle.vue')
                },
                {
                    path: '_extension',
                    name: 'disposal-allocateExtension',
                    component: () => import('@/views/disposal/allocate/extension.vue')
                },
                {
                    path: '_appeal',
                    name: 'disposal-allocateAppeal',
                    component: () => import('@/views/disposal/allocate/appeal.vue')
                }
            ]
        },
        {
            // 已分拨
            path: 'allocated',
            name: 'disposal-allocated',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-allocatedList',
                    component: () => import('@/views/disposal/allocated/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-allocatedInfo',
                    component: () => import('@/views/disposal/allocated/info.vue')
                }
            ]
        },
        {
            // 待处置
            path: 'handle',
            name: 'disposal-handle',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-handleList',
                    component: () => import('@/views/disposal/handle/list.vue')
                },
                {
                    path: '_handle',
                    name: 'disposal-handleHandle',
                    component: () => import('@/views/disposal/handle/handle.vue')
                }
            ]
        },
        {
            // 已处置
            path: 'handled',
            name: 'disposal-handled',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-handledList',
                    component: () => import('@/views/disposal/handled/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-handledInfo',
                    component: () => import('@/views/disposal/handled/info.vue')
                }
            ]
        },
        {
            // 待核查
            path: 'check',
            name: 'disposal-check',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-checkList',
                    component: () => import('@/views/disposal/check/list.vue')
                },
                {
                    path: '_handle',
                    name: 'disposal-checkHandle',
                    component: () => import('@/views/disposal/check/handle.vue')
                }
            ]
        },
        {
            // 已办结
            path: 'finished',
            name: 'disposal-finished',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-finishedList',
                    component: () => import('@/views/disposal/finished/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-finishedInfo',
                    component: () => import('@/views/disposal/finished/info.vue')
                }
            ]
        },
        {
            // 延期管理
            path: 'extension',
            name: 'disposal-extension',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-extensionList',
                    component: () => import('@/views/disposal/extension/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-extensionInfo',
                    component: () => import('@/views/disposal/extension/info.vue')
                }
            ]
        },
        {
            // 申诉管理
            path: 'appeal',
            name: 'disposal-appeal',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-appealList',
                    component: () => import('@/views/disposal/appeal/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-extensionInfo',
                    component: () => import('@/views/disposal/appeal/info.vue')
                }
            ]
        },
        {
            // 转12345工单
            path: 'citizen',
            name: 'disposal-citizen',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-citizenList',
                    component: () => import('@/views/disposal/citizen/list.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-citizenInfo',
                    component: () => import('@/views/disposal/citizen/info.vue')
                }
            ]
        },
        {
            // 事项类型
            path: 'eventType',
            name: 'disposal-eventType',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-eventTypeList',
                    component: () => import('@/views/disposal/eventType/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'disposal-eventTypeAdd',
                    component: () => import('@/views/disposal/eventType/add.vue')
                },
                {
                    path: '_edit',
                    name: 'disposal-eventTypeEdit',
                    component: () => import('@/views/disposal/eventType/edit.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-eventTypeInfo',
                    component: () => import('@/views/disposal/eventType/info.vue')
                }
            ]
        },
        {
            // 考核周期
            path: 'assessCycle',
            name: 'disposal-assessCycle',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-assessCycle-list',
                    component: () => import('@/views/disposal/assessCycle/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'disposal-assessCycle-add',
                    component: () => import('@/views/disposal/assessCycle/add.vue')
                },
                {
                    path: '_edit',
                    name: 'disposal-assessCycle-edit',
                    component: () => import('@/views/disposal/assessCycle/edit.vue')
                },
                {
                    path: '_info',
                    name: 'disposal-assessCycle-info',
                    component: () => import('@/views/disposal/assessCycle/info.vue')
                }
            ]
        },
        {
            // 消息通知
            path: 'notice',
            name: 'disposal-notice',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'disposal-notice-list',
                    component: () => import('@/views/disposal/notice/list.vue'),
                    meta: { keepAlive: true }
                }
            ]
        }
    ]
}

export default disposalRouter
