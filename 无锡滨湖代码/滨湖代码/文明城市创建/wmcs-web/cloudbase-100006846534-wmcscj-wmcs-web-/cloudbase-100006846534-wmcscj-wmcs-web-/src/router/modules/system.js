const systemRouter = {
    path: 'system',
    name: 'system',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 街道管理
            path: 'region',
            name: 'system-region',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'regionList',
                    component: () => import('@/views/system/region/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'regionAdd',
                    component: () => import('@/views/system/region/add.vue')
                },
                {
                    path: '_edit',
                    name: 'regionEdit',
                    component: () => import('@/views/system/region/edit.vue')
                },
                {
                    path: '_info',
                    name: 'regionInfo',
                    component: () => import('@/views/system/region/info.vue')
                }
            ]
        },
        {
            // 单位管理
            path: 'dept',
            name: 'system-dept',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'deptList',
                    component: () => import('@/views/system/dept/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'deptAdd',
                    component: () => import('@/views/system/dept/add.vue')
                },
                {
                    path: '_edit',
                    name: 'deptEdit',
                    component: () => import('@/views/system/dept/edit.vue')
                },
                {
                    path: '_info',
                    name: 'deptInfo',
                    component: () => import('@/views/system/dept/info.vue')
                }
            ]
        },
        {
            // 人员管理
            path: 'employee',
            name: 'system-employee',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'employeeList',
                    component: () => import('@/views/system/employee/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'employeeAdd',
                    component: () => import('@/views/system/employee/add.vue')
                },
                {
                    path: '_edit',
                    name: 'employeeEdit',
                    component: () => import('@/views/system/employee/edit.vue')
                },
                {
                    path: '_info',
                    name: 'employeeInfo',
                    component: () => import('@/views/system/employee/info.vue')
                }
            ]
        }
    ]
}

export default systemRouter
