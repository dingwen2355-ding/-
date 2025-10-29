<template>
  <div :class="{leftRetract:isCollapse}" class="leftnav">
    <!--    <div @click="switchCollapse" class="switchCollapse">
      <i :class="isCollapse? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
    </div>-->
    <el-menu :collapse="isCollapse" :default-active="menuActive" :router="true">
      <template v-for="item in subMenuList">
        <template v-if="item.menus">
          <el-submenu :index="item.name" :key="item.name" v-show="item.isShow">
            <template slot="title">
              <i class="iconfont icon-saber_wenjianjia"></i>
              <!--:class="item.icon"-->
              <span slot="title">{{ item.name }}</span>
            </template>
            <el-menu-item
              v-for="subItem in item.menus"
              :index="subItem.path + subItem.name"
              :key="subItem.name"
              :route="{ path: subItem.path, query: { state: subItem.name }}"
            >
              <!--              <i class="iconfont el-icon-folder" ></i>-->
              <!--:class="subItem.icon"-->
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.path" :key="item.name" v-show="item.isShow">
            <i class="iconfont el-icon-folder"></i>
            <!--:class="item.icon"-->
            <span slot="title">{{ item.name }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>
<script>
export default {
  name: 'LeftNav',
  data() {
    return {
      isCollapse: false,
      menuActive: '',
      subMenuList: []
    }
  },
  watch: {
    $route: {
      handler(v) {
        let menuList = JSON.parse(sessionStorage.getItem('menuList'))
        menuList.some((e, index) => {
          e.menus.some(menu => {
            if (menu.menus) {
              let temp = menu.menus.filter(item => item.path === this.$route.path)
              if (temp.length > 0) {
                this.subMenuList = e.menus
                return true
              }
            } else {
              if (menu.path === this.$route.path) {
                this.subMenuList = e.menus
                return true
              }
            }
          })
          // let temp = e.menus.filter(menu => {
          //   if (menu.menus) {
          //     if (menu.menus[0].path === this.$route.path) {
          //       return menu
          //     }
          //   } else {
          //     if (menu.path === this.$route.path) {
          //       return menu
          //     }
          //   }
          // })
          // if (temp.length > 0) {
          //   this.subMenuList = e.menus
          //   return true
          // }
        })
        if (v.fullPath === v.path) {
          this.menuActive = v.path
        } else {
          this.menuActive = v.path + v.query.state
        }
      },
      immediate: true
    }
  },
  mounted() {
  },
  methods: {
    switchCollapse() {
      this.isCollapse = !this.isCollapse
      this.$emit('changeNav', this.isCollapse)
    }
  }
}
</script>
<style lang="scss" scoped>
.leftnav {
  width: 100%;
  height: 100%;
  overflow-y: auto;
  overflow-x: hidden;
  background: #ffffff;
  :deep(.el-menu) {
    border-right: none;
    .el-submenu {
      .el-submenu__title {
        .iconfont {
          color: #000000;
        }
        .el-submenu__icon-arrow {
          color: #000000;
        }
        span {
          font-size: 16px;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #1c1c1c;
        }
      }
    }
    .el-menu-item {
      width: calc(100% - 16px);
      line-height: 48px;
      margin-left: 8px;
      margin-right: 8px;
      span {
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #5d5d5d;
      }
    }
    .el-menu-item.is-active {
      line-height: 48px;
      background: #0052d9;
      border-radius: 5px;
      span {
        color: #ffffff;
      }
    }
    .iconfont {
      margin-right: 15px;
    }
  }
  .switchCollapse {
    height: 40px;
    font-size: 24px;
    color: #000;
    position: relative;
    text-align: center;
    line-height: 40px;
    cursor: pointer;
  }
}
</style>
