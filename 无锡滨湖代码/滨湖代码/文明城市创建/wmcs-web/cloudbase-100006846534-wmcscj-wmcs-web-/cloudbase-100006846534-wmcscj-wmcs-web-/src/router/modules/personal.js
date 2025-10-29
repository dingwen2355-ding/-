// personalRouter
const personalRouter = {
    path: 'personal',
    name: 'personal',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            path: 'center',
            name: 'personal-personal',
            component: () => import('@/views/personal/center/index.vue')
        },
        {
            path: 'config',
            name: 'personal-powerconfig',
            component: () => import('@/views/personal/powerconfig/index.vue')
        }
    ]
}

export default personalRouter
