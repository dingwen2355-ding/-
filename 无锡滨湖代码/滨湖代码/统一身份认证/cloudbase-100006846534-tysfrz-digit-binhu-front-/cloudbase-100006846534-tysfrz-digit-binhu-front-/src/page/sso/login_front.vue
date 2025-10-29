<template>
  <div></div>
</template>

<script>
import {mapGetters} from "vuex";
import {info} from "@/api/system/tenant";
import {getCaptcha} from "@/api/user";
import {getTopUrl} from "@/util/util";
import {validatenull} from "@/util/validate";

export default {
  name: "userlogin",
  data() {
    return {
      tenantMode: this.website.tenantMode,
      loginForm: {
        //租户ID
        tenantId: "000000",
        //部门ID
        deptId: "",
        //角色ID
        roleId: "",
        //用户名
        username: "",
        //密码
        password: "",
        //账号类型
        type: "account",
        //验证码的值
        code: "",
        //验证码的索引
        key: "",
        //预加载白色背景
        image:
          "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
        grantType: "ssoToken"
      },
      loginRules: {
        tenantId: [
          {required: false, message: "请输入租户ID", trigger: "blur"},
        ],
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 1, message: "密码长度最少为6位", trigger: "blur"},
        ],
      },
      passwordType: "password",
      userBox: false,
      userForm: {
        deptId: "",
        roleId: "",
      },
      userOption: {
        labelWidth: 70,
        submitBtn: true,
        emptyBtn: false,
        submitText: "登录",
        column: [
          {
            label: "部门",
            prop: "deptId",
            type: "select",
            props: {
              label: "deptName",
              value: "id",
            },
            dicUrl: "/api/blade-system/dept/select",
            span: 24,
            display: false,
            rules: [
              {
                required: true,
                message: "请选择部门",
                trigger: "blur",
              },
            ],
          },
          {
            label: "角色",
            prop: "roleId",
            type: "select",
            props: {
              label: "roleName",
              value: "id",
            },
            dicUrl: "/api/blade-system/role/select",
            span: 24,
            display: false,
            rules: [
              {
                required: true,
                message: "请选择角色",
                trigger: "blur",
              },
            ],
          },
        ],
      },
      isSavePwd: false, // 记住密码
    };
  },
  created() {
    this.getTenant();
  },

  mounted() {
    let ssoToken = this.getQueryString("data")
    window.$platformType = "front"
    //使用username传递token信息
    this.loginForm.username = ssoToken;
    this.handleLogin();
  },
  watch: {
    "loginForm.deptId"() {
      const column = this.findObject(this.userOption.column, "deptId");
      if (this.loginForm.deptId.includes(",")) {
        column.dicUrl = `/api/blade-system/dept/select?deptId=${this.loginForm.deptId}`;
        column.display = true;
      } else {
        column.dicUrl = "";
      }
    },
    "loginForm.roleId"() {
      const column = this.findObject(this.userOption.column, "roleId");
      if (this.loginForm.roleId.includes(",")) {
        column.dicUrl = `/api/blade-system/role/select?roleId=${this.loginForm.roleId}`;
        column.display = true;
      } else {
        column.dicUrl = "";
      }
    },
  },
  computed: {
    ...mapGetters(["tagWel", "userInfo", "menu"]),
  },
  props: [],
  methods: {
    //设置cookie
    setCookie(name, value, day) {
      let date = new Date();
      date.setDate(date.getDate() + day);
      document.cookie = `${name}=${value};expires=${date}`;
    },
    //获取cookie
    getCookie(name) {
      let reg = RegExp(name + "=([^;]+)");
      let arr = document.cookie.match(reg);
      return arr ? arr[1] : null;
    },
    //删除cookie
    delCookie(name) {
      this.setCookie(name, null, -1);
    },
    refreshCode() {
      if (this.website.captchaMode) {
        getCaptcha().then((res) => {
          const data = res.data;
          this.loginForm.key = data.key;
          this.loginForm.image = data.image;
        });
      }
    },
    showPassword() {
      this.passwordType === ""
        ? (this.passwordType = "password")
        : (this.passwordType = "");
    },
    submitLogin(form, done) {
      this.handleLogin();
      done();
    },

    handleLogin(item = {}) {
      this.$store.dispatch("LoginByUsername", this.loginForm).then(async () => {
        await this.$store.dispatch("GetMenu", item.id).then((data) => {
          if (data.length !== 0) {
            this.$router.$avueRouter.formatRoutes(data, true);
          }
        });
        this.$store.commit("SET_CURRENT_MENU", this.menu);
        if (this.website.switchMode) {
          const deptId = this.userInfo.dept_id;
          const roleId = this.userInfo.role_id;
          if (deptId.includes(",") || roleId.includes(",")) {
            this.loginForm.deptId = deptId;
            this.loginForm.roleId = roleId;
            this.userBox = true;
            this.$store.dispatch("LogOut").then(() => {

            });
          }
        }
        this.$router.push({path: "/front"});
        /*if (this.userInfo.account == "admin") {
          this.$router.push({path: "/"});
        } else {
          this.$router.push({path: "/front"});
        }*/
      })
        .catch(() => {

        });
    },

    openSysMenu(item = {}) {
      if (item.name === undefined || item.name === "" || item.name === "首页") {
        return;
      }
      this.$store.commit("SET_TAG_LIST", []); // 在top-menu中去掉首页
      if (!validatenull(item) && item.name !== "首页") {
        let itemActive = {};
        if (!item.children.length) {
          itemActive = item;
        } else {
          this.findChild([item], (data) => {
            itemActive = data;
          });
        }
        this.$router.push({
          path: this.$router.$avueRouter.getPath(
            {
              name: itemActive.label || itemActive.name,
              src: itemActive.path,
            },
            itemActive.meta
          ),
        });
      }
    },
    findChild(array, callback) {
      var data = array[0];
      if (data.children.length > 0) {
        this.findChild(data.children, callback);
      } else {
        callback(data);
      }
    },
    getTenant() {
      let domain = getTopUrl();
      // 临时指定域名，方便测试
      //domain = "https://bladex.vip";
      info(domain).then((res) => {
        const data = res.data;
        if (data.success && data.data.tenantId) {
          this.tenantMode = false;
          this.loginForm.tenantId = data.data.tenantId;
          this.$parent.$refs.login.style.backgroundImage = `url(${data.data.backgroundUrl})`;
        }
      });
    },

    getQueryString(name) {
      return window.location.hash.split("?")[1]; //获取url中"?"
    }
  },
};
</script>

<style lang="scss" scoped>
.save-pwd {
  display: flex;
  justify-content: space-between;
}
</style>
