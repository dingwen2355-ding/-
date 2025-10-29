<template>
  <div class="footer">
    <ul>
      <li v-for="(item, i) in menuList" :key="i">
        <router-link
          v-if="item.routerLink"
          :to="{ path: item.routerLink, query: { time: time } }"
        >
          <div class="menu-item" @click="getTime">{{ item.label }}</div>
        </router-link>
        <div v-else class="menu-item">
          {{ item.label }}
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "IocFooter",

  data() {
    return {
      // 导航菜单
      menuList: [
        {
          label: "智慧党建",
          routerLink: "/partyBuilding",
        },

        {
          label: "商户总览",
          routerLink: "/businessOverview",
        },

        {
          label: "应急消防",
          routerLink: "/fireFighting",
        },

        {
          label: "安防监控",
          routerLink: "/SecurityoniMtoring",
        },

        {
          label: "商圈事件",
          routerLink: "/BusinessCircleEvent",
        },

        {
          label: "人车监管",
          routerLink: "/parkingSupervision",
        },

        {
          label: "商圈部件",
          routerLink: "/businessDistrictComponents",
        },
        {
          label: "地下管网",
          routerLink: "/pipeNetwork",
        },
      ],

      // 当前时间戳
      time: Date.now(),
    };
  },

  methods: {
    getTime() {
      this.time = Date.now();
    },
  },
};
</script>

<style lang="scss" scoped>
.footer {
  position: fixed;
  left: 50%;
  // bottom: 43px;
  bottom: 0.13rem;
  transform: translateX(-50%);
  // width: 904px;
  width: 8.93rem;
  // height: 51px;
  height: 0.51rem;
  line-height: 0.51rem;
  background-image: url("../../../assets/footer/img_menubox_bg@2x.png");
  background-size: 100% 100%;
  background-position: top center;
  background-repeat: no-repeat;
  text-align: center;
}

ul {
  display: flex;
  width: 648px;
  margin: 10px auto;

  // width: 648px;
  width: 6.48rem;
  // margin: 10px auto;
  margin: 0.1rem auto;

  li {
    flex: 1;

    .menu-item {
      // width: 80px;
      // width: .8rem;
      // height: 32px;
      height: 0.32rem;
      line-height: 0.32rem;
      cursor: pointer;
      background-size: 100% 100%;
      background-image: url("../../../assets/footer/img_btn_menu_bg.png");
      margin-right: 0.16rem;
      font-size: 0.15rem;
    }

    .router-link-active .menu-item,
    .menu-item:hover {
      background-image: url("../../../assets/footer/img_btn_menu_bg_active.png");
    }
  }
}
</style>
