<template>
  <div class="full-screen-container">
    <div class="contanier">
      <left class="leftLink" @getData="getData"></left>
      <div class="module_container">
        <div class="front-title">
          <img src="/img/bg/logo.png" alt="" />
          <div style="font-weight: 500; font-size: 26px; margin-left: 8px">
            {{
              websitConfig.sysName ? websitConfig.sysName : website.indexTitle
            }}
          </div>
          <div class="top-bar__right">
            <el-dropdown>
              <div class="el-dropdown-link">
                <img
                  v-if="userInfo.avatar"
                  class="top-bar__img"
                  :src="userInfo.avatar"
                />
                <img v-else class="top-bar__img" src="/img/bg/touxiang.png" />
                {{ userInfo.user_name }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </div>
              <el-dropdown-menu
                style="width: 80px; padding: 10px 0"
                slot="dropdown"
              >
                <el-dropdown-item
                  style="text-align: center"
                  @click.native="handleOpenPersonal"
                  >个人中心
                </el-dropdown-item>
                <el-dropdown-item
                  style="text-align: center"
                  v-if="isAdmin"
                  @click.native="handleGoBack"
                  >后台管理
                </el-dropdown-item>
                <el-dropdown-item
                  @click.native="logout"
                  divided
                  style="text-align: center"
                  >{{ $t("navbar.logOut") }}
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>
        <div class="bottom">
          <right1
            v-if="
              sideBar == 1 && !searchShow && !infoShow && !announceDetailShow
            "
            @toAll="toAll"
            :randomKey="randomKey"
          ></right1>
          <right2
            v-if="
              sideBar == 2 && !searchShow && !infoShow && !announceDetailShow
            "
            :randomKey="randomKey"
          ></right2>
          <right3
            v-if="
              sideBar == 3 && !searchShow && !infoShow && !announceDetailShow
            "
          ></right3>
          <searchApp
            v-if="searchShow && !infoShow"
            @returnBack="returnBack"
          ></searchApp>
          <personalInfo v-if="infoShow && !searchShow"></personalInfo>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserRole, getSsoLogoutUri } from "@/api/common";
import { mapGetters } from "vuex";
import left from "./components/left.vue";
import right1 from "./platform.vue";
import right2 from "./announceCenter.vue";
import right3 from "./permission.vue";
import searchApp from "./searchApp.vue";
import personalInfo from "./personalInfo.vue";

export default {
  components: {
    left,
    right1,
    right2,
    right3,
    searchApp,
    personalInfo,
  },
  computed: {
    ...mapGetters(["website", "websitConfig", "userInfo"]),
  },
  data() {
    return {
      sideBar: 1,
      isAdmin: false,
      searchShow: false,
      infoShow: false,
      randomKey: 0,
    };
  },
  mounted() {
    window.$platformType = "front"
    document.title = "前台-滨湖区统一身份认证";
    getUserRole().then((res) => {
      // 0是管理员 1是普通用户
      this.isAdmin = res.data.data == 0 ? true : false;
    });
  },
  methods: {
    returnBack() {
      this.searchShow = false;
    },
    handleOpenPersonal() {
      this.searchShow = false;
      this.infoShow = true;
    },
    // 跳转到后台
    handleGoBack() {
      window.$platformType = "back"
      this.$router.push({ path: "/wel" });
    },
    getData(data) {
      this.searchShow = false;
      this.infoShow = false;
      this.sideBar = data;
      this.randomKey = Math.random();
    },
    toAll(data) {
      this.searchShow = data;
    },
    logout() {
      this.$confirm(this.$t("logoutTip"), this.$t("tip"), {
        confirmButtonText: this.$t("submitText"),
        cancelButtonText: this.$t("cancelText"),
        type: "warning",
      }).then(() => {
        this.$store.dispatch("LogOut").then(() => {
          window.sessionStorage.removeItem("sideBar");
          // this.$router.push({ path: "/login" });
          getSsoLogoutUri().then((res) => {
            window.location.href = res.data.data;
          });
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.full-screen-container {
  background: #0070b9;
  height: 100%;
  width: 100%;
  .contanier {
    width: 100%;
    height: 100%;
    display: flex;
    .leftLink {
      width: 90px;
    }
    .module_container {
      background: #f2f7ff;
      height: 100%;
      width: calc(100% - 100px);
      border-radius: 40px;
      .front-title {
        display: flex;
        align-items: center;
        padding: 10px 20px;

        /deep/ .el-dropdown {
          padding: 0 7px 0 20px;
          color: #333;
          /deep/el-dropdown-item {
            width: 200px;
          }
        }
      }
      .bottom {
        height: calc(100% - 100px);
        padding: 0 20px;
        overflow: scroll;
        /deep/.basic-container__card {
          height: 100%;
          /deep/.el-card__body {
            height: 100%;
          }
        }
      }
    }
  }
}
</style>
