const statisRouter = {
    path: 'statis',
    name: 'statis',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            path: 'total',
            name: 'statis-total',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'index',
                    name: 'statisTotalIndex',
                    component: () => import('@/views/statis/total/index.vue')
                }
            ]
        }
    ]
}

export default statisRouter
