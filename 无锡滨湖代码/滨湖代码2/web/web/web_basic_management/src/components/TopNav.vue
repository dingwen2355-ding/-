<!-- Created by Y on 2018-06-27 -->
<template>
  <div id="top-nav">
    <div class="title">
      <span class="title-icon"></span>
      {{ title }}
    </div>
    <div class="menuTop">
      <span
        class="menuItem"
        :class="[index === active ? 'active' : '']"
        v-for="(item, index) in menuList"
        v-show="item.isShow"
        :key="index"
        @click="selectHandel(item, index)"
      >
        <!--        <i :class="item.icon"></i>-->
        <span class="menuName">{{ item.name }}</span>
      </span>
    </div>

    <div class="user">
      <!-- <span class="back" title="返回基础门户"><i class="el-icon-back" @click="handleBack"></i></span> -->
      <!-- <span class="el-dropdown-link" @click="informationCilck" v-if="bellVisible">
        <el-badge :value="allNum" :max="99" class="item">
          <i class="el-icon-bell"></i>
        </el-badge>
      </span>-->
      <i class="iconfont icon-top_sousuo"></i>
      <i class="iconfont icon-top_xinxi" style="margin-left: 20px"></i>
      <i class="iconfont icon-top_geren" style="margin-left: 20px"></i>
      <span>{{ userName }}</span>

      <el-dropdown style="cursor: pointer">
        <span class="el-icon-arrow-down"></span>
        <el-dropdown-menu class="clearfix">
          <el-dropdown-item class="clearfix">
            <div class="text" @click="resetPassword">修改密码</div>
          </el-dropdown-item>
          <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <resetPwd :dialogPasswordVisible.sync="dialogPasswordVisible" />
    <!-- <InformationDialog :informationDialog.sync="informationDialog" /> -->
  </div>
</template>

<script>
import resetPwd from '../views/user/resetPwd.vue'
// import InformationDialog11 from '../views/information/informationDialog.vue'
import axios from '@/utils/request'
export default {
  name: 'TopNav',
  props: ['userName'],
  components: { resetPwd },
  // components: { InformationDialog: () => import('../views/information/informationDialog.vue') },
  data() {
    return {
      title: configs.title,
      menuList: JSON.parse(sessionStorage.getItem('menuList')),
      active: 0,
      informationDialog: false,
      allNum: 0,
      // bellVisible:
      //   JSON.parse(sessionStorage.getItem('menuList'))[2].menus[0].isShow ||
      //   JSON.parse(sessionStorage.getItem('menuList'))[2].menus[3].isShow,
      dialogPasswordVisible: false
    }
  },
  watch: {
    $route: {
      handler(v) {
        this.menuList.some((e, index) => {
          e.menus.some((menu) => {
            if (menu.menus) {
              let temp = menu.menus.filter((item) => item.path === this.$route.path)
              if (temp.length > 0) {
                this.active = index
                return true
              }
            } else {
              if (menu.path === this.$route.path) {
                this.active = index
                return true
              }
            }
            // menu.path === this.$route.path
          })
        })
      },
      immediate: true
    }
  },
  computed: {},
  mounted() {
    this.getInformation()
    this.$root.eventBus.$on('alarmInformUpdate', (point) => {
      if (point) {
        this.getInformation()
      }
    })
  },
  methods: {
    handleBack() {
      let url = window.location.hostname
      if (url === '10.133.252.22') {
        // 内网
        let linkUrl = 'http://10.133.252.22:8084/home?token='
        window.open(linkUrl + sessionStorage.getItem('token'))
      } else if (url === '172.16.7.11') {
        // 测试
        window.open('http://172.16.7.11:8037/home?token=' + sessionStorage.getItem('token'))
      } else {
        // 外网
        window.open('http://61.162.52.230:27260/home?token=' + sessionStorage.getItem('token'))
      }
    },
    resetPassword() {
      // if (sessionStorage.user === 'admin') {
      //   this.$message('admin无法在此更改密码')
      //   return
      // }
      this.dialogPasswordVisible = true
    },
    getInformation() {
      let url = '/binhuapis/alarmInform/selAlarmInform?isAccept=' + '未接收'
      axios.get(url).then((r) => {
        if (r.data.code === 200) {
          this.allNum = r.data.data.count || 0
        }
      })
    },
    informationCilck() {
      this.$store.dispatch('view/setInformationvisible', true)
    },
    logout() {
      if (configs.superControl) {
        this.$router.push('/')
        sessionStorage.clear()
      } else {
        localStorage.clear()
        sessionStorage.clear()
        // window.open("about:blank", "_self")
        // window.location.href = "about:blank"
        // window.close()
        console.log(window.opener);
        if (window.opener === null) {
          window.open("about:blank", "_self")
          window.location.href = "about:blank"
          window.close()
        } else {
          window.open('', '_self')
          window.close()
        }
        /*
        localStorage.clear()
        sessionStorage.clear()
        window.open('http://2.22.51.26:30002/logout?logoutUrl=', '_self')
        window.close()
        window.open('http://2.22.51.26:30002/oauth/authorize?client_id=Hhjvbg2U&client_secret=1383a5ddf71b635c3610fe4b26889a10d738d79b&redirect_uri=http://2.21.137.156:8084&response_type=code&scope=all')
        */
      }
    },
    selectHandel(item, index) {
      this.active = index
      if (item.menus[0].menus) {
        this.$router.push({ path: item.path, query: { state: item.menus[0].menus[0].name } })
      } else {
        this.$router.push({ path: item.path })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
#top-nav {
  font-family: Microsoft YaHei;
  font-weight: 400;
  color: #ffff;
  .topnav {
    width: 100%;
    height: 100%;
  }
  .iconfont {
    font-size: 20px;
  }
  .title-icon {
    width: 22px;
    height: 38px;
    background: url('../assets/common/logo@2x.png');
    display: inline-block;
    margin-right: 15px;
  }
  .title {
    width: 500px;
    float: left;
    font-size: 22px;
    font-family: Microsoft YaHei;
    font-weight: 600;
    color: #000332;
    line-height: 76px;
    margin-left: 36px;
    display: flex;
    align-items: center;
  }
  .menuTop {
    float: left;
    margin: 20px 10px 10px 0;

    border-radius: 5px;
    i {
      font-size: 20px;
    }
    .menuItem {
      cursor: pointer;
      margin-right: 10px;
      padding: 8px 10px;
      background: #ffffff;
      border-radius: 5px;
      .menuName {
        padding-left: 5px;
        font-size: 16px;
        font-family: Microsoft YaHei;
        font-weight: 600;
        color: #5d5d5d;
        line-height: 48px;
      }
    }
    .menuItem.active {
      background: #f2f3ff;
      .menuName {
        color: #0052d9;
      }
    }
  }
  .active {
    border: 1px solid #fff;
    background: rgba($color: #fff, $alpha: 0.5);
  }
  .user {
    float: right;
    margin-top: 20px;
    margin-right: 49px;
    font-size: 16px;
    display: flex;
    align-items: center;
    color: black;
    .back {
      margin-right: 10px;
      font-size: 20px;
      cursor: pointer;
    }
    .el-dropdown-link {
      margin-right: 15px;
      cursor: pointer;
    }
    .el-icon-s-custom {
      font-size: 20px;
      color: #000000;
    }
    .el-icon-arrow-down {
      font-size: 20px;
      color: #000000;
    }
    span {
      margin-left: 15px;
    }
    :deep(.el-dropdown-item) {
      color: black;
    }
  }
}
</style>
