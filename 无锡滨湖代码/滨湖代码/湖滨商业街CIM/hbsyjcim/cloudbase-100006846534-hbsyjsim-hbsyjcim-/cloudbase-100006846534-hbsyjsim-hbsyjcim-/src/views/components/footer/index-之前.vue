<template>
  <div class="footer">
    <ul>
      <li v-for="(item, i) in menuList" :key="i">
        <router-link v-if="item.routerLink" :to="{ path: item.routerLink, query: { time: time } }" >
          <div class="menu-item" @click="getTime">{{ item.label }}</div>
        </router-link>
        <div v-else class="menu-item">{{ item.label }}</div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'IocFooter',
  data() {
    return {
      // 导航菜单
      menuList: [
        {
          label: '智慧党建',
          routerLink: '/partyBuilding'
        },
        {
          label: '商户总览',
          routerLink: null
        },
        {
          label: '应急消防',
          routerLink: null
        },
        {
          label: '安防监控',
          routerLink: null
        },
        {
          label: '商圈事件',
          routerLink: null
        },
        {
          label: '停车监督',
          routerLink: null
        },
        {
          label: '商圈部件',
          routerLink: null
        }
      ],
      // 当前时间戳
      time: Date.now()
    }
  },
  methods: {
    getTime() {
      this.time = Date.now()
      
    }
  }

}
</script>

<style lang="scss" scoped>
.footer {
  position: fixed;
  left: 50%;
  bottom: 43px;
  transform: translateX(-50%);
  width: 904px;
  height: 51px;
  line-height: 51px;
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
  li {
    flex: 1;
    .menu-item {
      width: 80px;
      height: 32px;
      line-height: 32px;
      cursor: pointer;
      background-size: 100% 100%;
      background-image: url("../../../assets/footer/img_btn_menu_bg.png");
    }
    .router-link-active .menu-item, .menu-item:hover {
      background-image: url("../../../assets/footer/img_btn_menu_bg_active.png");
    }
  }
}
</style>