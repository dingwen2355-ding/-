<template>
  <div class="main-page">
    <section class="page-view" :style="{ 'padding-bottom': isShowMenu ? '160rem' : '0' }">
      <router-view></router-view>
    </section>
    <div class="bottom-tab-bar" v-show="isShowMenu">
      <div
        v-for="item in menuList"
        :key="item.name"
        :class="{ 'bar-item': true, active: item.name === activeMenu, prominent: item.prominent }"
        @click="toMenuPage(item)"
      >
        <div :class="'bar-icon iconfont ' + item.icon"></div>
        <div>{{ item.name }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'mainPage',
  data() {
    return {
      isShowMenu: false,
      menuList: [
        { name: '资讯', icon: 'icon-bottom_zixun', route: '/consultationNotice' },
        { name: '事件', icon: 'icon-bottom_shijian', route: '/eventOverview' },
        { name: '上报', icon: 'icon-bottom_shangbao', prominent: true, route: '/eventReport' },
        { name: '通知', icon: 'icon-bottom_tongzhi', route: '/notificationOverview' },
        { name: '查询', icon: 'icon-bottom_chaxun', route: '/classicCaseQuery' }
      ],
      activeMenu: ''
    }
  },
  watch: {
    $route: {
      immediate: true,
      handler(to) {
        this.isShowMenu = to.meta.showBottomMenu
        if (to.meta.showBottomMenu) {
          this.activeMenu = to.meta.menuName
        }
      }
    }
  },
  methods: {
    toMenuPage(menu) {
      this.activeMenu = menu.name
      this.$router.push(menu.route)
    }
  }
}
</script>

<style lang="scss" scoped>
.bottom-tab-bar {
  position: fixed;
  left: 0;
  bottom: 0;
  display: flex;
  justify-content: space-around;
  width: 100%;
  padding: 10rem 0 50rem 0;
  background: #fff;
  .bar-icon {
    font-size: 36rem;
    line-height: 50rem;
    margin-bottom: 8rem;
  }
  .bar-item {
    width: 80rem;
    color: #575757;
    font-size: 18rem;
    line-height: 30rem;
    text-align: center;
    cursor: pointer;
    transition: color 0.3s;
    &.active {
      color: #427de5;
    }
    &.prominent {
      margin-top: -20rem;
      .bar-icon {
        display: inline-block;
        width: 69rem;
        height: 69rem;
        color: #fff;
        line-height: 69rem;
        background: #427de5;
        border-radius: 50%;
      }
    }
  }
}
</style>
