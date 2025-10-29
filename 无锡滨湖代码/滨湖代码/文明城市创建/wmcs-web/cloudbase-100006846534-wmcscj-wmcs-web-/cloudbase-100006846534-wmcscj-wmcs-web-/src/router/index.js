// 基础
import Vue from "vue";
import VueRouter from "vue-router";
import Cookies from "js-cookie";
import Home from "../views/home/index.vue";
import Person from "../views/home/person.vue";
import personalRouter from "./modules/personal";
// 页面菜单路由
import dataRouter from "./modules/data";
import eventRouter from "./modules/event";
import quotaRouter from "./modules/quota";
import analysisRouter from "./modules/analysis";
import assessRouter from "./modules/assess";
import systemRouter from "./modules/system";
import statisRouter from "./modules/statis";
import synchRouter from "./modules/synch";
import disposalRouter from "./modules/disposal";
import specialTrackingRouter from "./modules/specialTracking";
import keyPointsRouter from './modules/keyPoints';
// 配置项加载
import {
    routerWhiteList,
    loginRouterWhiteList,
    noRefushRouterPath,
    useKeepAliveArr,
} from "@/config/index";
Vue.use(VueRouter);

const routes = [
    {
        path: "/",
        name: "homeorlogin",
        redirect: "/login",
    },
    {
        path: "/login",
        name: "login",
        component: () => import("../views/login/index.vue"),
    },
    {
        path: "/autologin",
        name: "autologin",
        component: () => import("../views/login/autologin.vue"),
    },
    {
        path: "/401",
        name: "401",
        component: () => import("../views/error/401.vue"),
    },
    {
        path: "/home",
        name: "home",
        component: Home,
        children: [
            dataRouter,
            eventRouter,
            quotaRouter,
            analysisRouter,
            assessRouter,
            systemRouter,
            statisRouter,
            synchRouter,
            disposalRouter,
            specialTrackingRouter,
            keyPointsRouter
        ],
    },
    {
        path: "/pa",
        name: "pa",
        component: Person,
        children: [
            // 个人中心
            personalRouter,
        ],
    },
    {
        path: "*",
        name: "404",
        component: () => import("../views/error/404.vue"),
    },
];

// 改history参考: https://www.jianshu.com/p/66278d0670ff
// nginx参考: https://segmentfault.com/q/1010000016609468/a-1020000016616673?sort=created
const router = new VueRouter({
    mode: "history", // 模式：hash | history | abstract
    routes,
});

router.beforeEach((to, from, next) => {
    let token = Cookies.get("token");
    let routers = router.app.$store.state.allMenuList || [];
    if (routers.length == 0) routers = false; // 纯粹为了兼容老版本的判断, 原先是从localStorage里获取的
    // 设置跳转数组,满足数组的跳转,会缓存
    let tokeepUrl = useKeepAliveArr || [];
    let from_keep = false;
    // 1 从list->_info/_detail/_edit/_add自动缓存
    let from_end_path = from.path.split("/").pop();
    let to_end_path = to.path.split("/").pop();
    if (
        from_end_path == "list" &&
        (to_end_path == "_info" ||
            to_end_path == "_detail" ||
            to_end_path == "_edit" ||
            to_end_path == "_add")
    ) {
        from_keep = true;
    }
    // 2 配置项内配置了独立的关系可缓存
    tokeepUrl.forEach((i) => {
        if (from.name == i[0] && to.name == i[1]) {
            from_keep = true;
        }
    });
    from.meta.keepAlive = from_keep;
    // 返回用的是刷新: 默认从详情返回是缓存,如果是提交后需要刷新,则添加_list_refush参数
    if (to.query._list_refush) {
        to.meta.keepAlive = false;
        delete to.query._list_refush;
        return next(to);
    }
    // if (to.name == 'detail') {
    //     from.meta.keepAlive = true
    // } else {
    //     from.meta.keepAlive = false
    // }
    let whiteList = routerWhiteList;
    // 白名单列表
    if (whiteList.indexOf(to.name) > -1) return next();
    // 未登录想登录--不能省,位置必须在前面
    if (token == undefined && to.path == "/login") return next();
    // 未登录想访问其他页面,先登录
    if (token == undefined && to.path != "/login")
        return next({ path: "/login" });
    // 已登录想访问登录页面,自动跳转到首页
    if (token && to.path == "/login") {
        if (!routers) return next({ path: "/home" });
        // 跳转到第一个菜单
        return next({ path: routers[0] });
    }
    if (!routers) return next();
    // 白名单验证
    if (routers.indexOf(to.path) > -1) return next();
    if (loginRouterWhiteList.indexOf(to.name) > -1) return next();
    if (loginRouterWhiteList.indexOf(to.path) > -1) return next();
    // 白名单字符串
    let whiteArr = noRefushRouterPath.filter((i) => {
        return to.path.indexOf(i) > -1;
    });
    if (whiteArr.length > 0) return next();
    return next(routers[0]);
});

export default router;
