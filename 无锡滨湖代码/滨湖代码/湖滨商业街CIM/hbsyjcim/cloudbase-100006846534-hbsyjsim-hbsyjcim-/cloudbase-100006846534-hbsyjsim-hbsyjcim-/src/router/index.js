import Vue from "vue";

import VueRouter from "vue-router";

import Layout from "@/views/layout.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    component: Layout,

    children: [
      {
        path: "/partyBuilding",

        components: {
          left: () => import("@/views/components/partyBuilding/left.vue"),
          right: () => import("@/views/components/partyBuilding/right.vue"),
        },
      },
      {
        path: "/businessOverview",

        components: {
          left: () => import("@/views/components/businessOverview/left.vue"),
          right: () => import("@/views/components/businessOverview/right.vue"),
        },
      },
      {
        path: "/fireFighting",
        components: {
          left: () => import("@/views/components/fireFighting/left.vue"),
          right: () => import("@/views/components/fireFighting/right.vue"),
        },
      },
      {
        path: "/parkingSupervision",
        components: {
          left: () => import("@/views/components/parkingSupervision/left.vue"),
          right: () =>
            import("@/views/components/parkingSupervision/right.vue"),
        },
      },
      {
        path: "/businessDistrictComponents",
        components: {
          left: () =>
            import("@/views/components/businessDistrictComponents/left.vue"),
          right: () =>
            import("@/views/components/businessDistrictComponents/right.vue"),
        },
      },
      {
        path: "/pipeNetwork",
        components: {
          left: () => import("@/views/components/pipeNetwork/index.vue"),
        },
      },
    ],
  },

  // 安防监控
  {
    path: "/SecurityoniMtoring",
    component: Layout,

    children: [
      {
        path: "/SecurityoniMtoring",

        components: {
          left: () => import("@/views/SecurityoniMtoring/left.vue"),
          right: () => import("@/views/SecurityoniMtoring/right.vue"),
        },
      },
    ],
  },

  // 商圈事件
  {
    path: "/BusinessCircleEvent",
    component: Layout,

    children: [
      {
        path: "/BusinessCircleEvent",

        components: {
          left: () => import("@/views/BusinessCircleEvent/left.vue"),
          right: () => import("@/views/BusinessCircleEvent/right.vue"),
        },
      },
    ],
  },
];

const router = new VueRouter({
  routes,
});

export default router;
