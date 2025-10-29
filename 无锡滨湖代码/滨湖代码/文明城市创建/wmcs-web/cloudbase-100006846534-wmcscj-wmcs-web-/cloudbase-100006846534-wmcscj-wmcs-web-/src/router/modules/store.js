const storeRouter = {
    path: 'warehouse',
    name: 'warehouse',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            path: 'list',
            name: 'warehouse-list',
            component: () => import('@/views/store/warehouse/list.vue')
        }
    ]
}

export default storeRouter
