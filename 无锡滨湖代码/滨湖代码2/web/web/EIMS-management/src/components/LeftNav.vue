<template>
  <div :class="{ leftRetract: isCollapse }" class="left-nav">
    <el-menu
      :collapse="isCollapse"
      :default-active="menuActive"
      :router="true"
      active-text-color="#0093FF"
      text-color="#4E5A6E"
      @select="selectHandel"
    >
      <template v-for="item in menuList">
        <template v-if="item.menus">
          <el-submenu :index="item.name" :key="item.name" v-show="item.isShow">
            <template slot="title">
              <i class="iconfont" :class="item.icon"></i>
              <span slot="title">{{ item.name }}</span>
            </template>
            <el-menu-item
              v-for="subItem in item.menus"
              :index="subItem.path"
              :key="subItem.name"
              v-show="subItem.isShow"
            >
              <i class="iconfont" :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.path" :key="item.name" v-show="item.isShow">
            <i class="iconfont" :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
    <div class="icon-container">
      <i :class="[!isCollapse ? 'el-icon-s-fold' : 'el-icon-s-unfold']" @click="switchCollapse"></i>
    </div>
  </div>
</template>
<script>
export default {
  name: 'left-nav',
  data() {
    return {
      isAdmin: localStorage.getItem('user') === 'admin',
      menuList: JSON.parse(localStorage.getItem('menuList')),
      isCollapse: false,
      menuActive: '',
      roleId: JSON.parse(localStorage.getItem('roleId'))
    }
  },
  methods: {
    switchCollapse() {
      this.isCollapse = !this.isCollapse
      this.$emit('changeNav', this.isCollapse)
      this.$store.dispatch('view/setIsCollapse', this.isCollapse)
    },
    selectHandel(v) {
      this.findNode(this.menuList, (e) => {
        if (e.path === v) {
          this.$root.eventBus.$emit('openTag', e)
        }
      })
    },
    findNode(tree, func) {
      for (const node of tree) {
        if (func(node)) return node
        if (node.menus) {
          const res = this.findNode(node.menus, func)
          if (res) return res
        }
      }
      return null
    }
  },
  watch: {
    $route: {
      handler(newRouter) {
        this.menuActive = newRouter.path
        // console.log('this.menuActive', this.menuActive)
      },
      immediate: true
    }
  },
  mounted() {
    this.$emit('changeNav', this.isCollapse)
    setTimeout(() => {
      this.selectHandel(this.menuActive)
    }, 100)

    this.$root.eventBus.$on('openRoute', (data) => {
      this.$router.push('/' + data)
      this.selectHandel('/' + data)
    })
  }
}
</script>
<style lang="scss" scoped>
.left-nav {
  width: 100%;
  height: 100%;
  overflow: hidden;

  .el-menu {
    width: 100%;
    height: calc(100% - 40px);
    overflow: auto;
  }

  .iconfont {
    font-size: 20px;
  }

  .icon-container {
    font-size: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    height: 40px;
    border-top: 1px solid #d8d8d8;
    position: absolute;
    width: 100%;
    bottom: 0;
  }
}
</style>
