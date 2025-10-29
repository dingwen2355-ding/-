const synchRouter = {
    path: 'synch',
    name: 'synch',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 账号同步
            path: 'unityUser',
            name: 'synch-unityUser',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'unityUserList',
                    component: () => import('@/views/system/unityUser/list.vue'),
                    meta: { keepAlive: true }
                }
            ]
        }
    ]
}

export default synchRouter
