import Layout from '@/page/index/'

export default [{
  path: '/login',
  name: '登录页',
  component: () =>
    import( /* webpackChunkName: "page" */ '@/page/login/index'),
  meta: {
    keepAlive: true,
    isTab: false,
    isAuth: false
  }
},{
  path: '/backendIndex',
  name: '后端登录页',
  component: () =>
    import( /* webpackChunkName: "page" */ '@/page/login/backend_index'),
  meta: {
    keepAlive: true,
    isTab: false,
    isAuth: false
  }
},
  {
    path: '/lock',
    name: '锁屏页',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/page/lock/index'),
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/sso/login',
    name: '单点登录后端',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/page/sso/login'),
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/sso/loginFront',
    name: '单点登录后端',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/page/sso/login_front'),
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/404',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/components/error-page/404'),
    name: '404',
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }

  },
  {
    path: '/403',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/components/error-page/403'),
    name: '403',
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/500',
    component: () =>
      import( /* webpackChunkName: "page" */ '@/components/error-page/500'),
    name: '500',
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: false
    }
  },
  {
    path: '/',
    name: '主页',
    redirect: '/front'
  },
  {
    path: '/myiframe',
    component: Layout,
    redirect: '/myiframe',
    children: [{
      path: ":routerPath",
      name: 'iframe',
      component: () =>
        import( /* webpackChunkName: "page" */ '@/components/iframe/main'),
      props: true
    }]
  },
  {
    path: '/front',
    name: '前台',
    component: () => import('@/views/front/index.vue'),
    meta: {
      keepAlive: true,
      isTab: false,
      isAuth: true
    }
  },
  {
    path: '*',
    redirect: '/404'
  },
]
