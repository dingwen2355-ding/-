<template>
  <el-form
    class="login-form"
    status-icon
    :rules="loginRules"
    ref="loginForm"
    :model="loginForm"
    label-width="0"
  >
    <el-form-item v-if="tenantMode" prop="tenantId">
      <el-input
        size="small"
        @keyup.enter.native="handleLogin"
        v-model="loginForm.tenantId"
        auto-complete="off"
        :placeholder="$t('login.tenantId')"
      >
        <i slot="prefix" class="icon-quanxian" />
      </el-input>
    </el-form-item>
    <el-form-item prop="username">
      <el-input
        size="small"
        @keyup.enter.native="handleLogin"
        v-model="loginForm.username"
        auto-complete="off"
        :placeholder="$t('login.username')"
      >
        <i slot="prefix" class="icon-yonghu" />
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        size="small"
        @keyup.enter.native="handleLogin"
        :type="passwordType"
        v-model="loginForm.password"
        auto-complete="off"
        :placeholder="$t('login.password')"
      >
        <i
          class="el-icon-view el-input__icon"
          slot="suffix"
          @click="showPassword"
        />
        <i slot="prefix" class="icon-mima" />
      </el-input>
    </el-form-item>
    <el-form-item
      v-if="this.website.captchaMode && captchaInfo.captchaShow"
      prop="code"
    >
      <el-row :span="24">
        <el-col :span="16">
          <el-input
            size="small"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.code"
            auto-complete="off"
            :placeholder="$t('login.code')"
          >
            <i slot="prefix" class="icon-yanzhengma" />
          </el-input>
        </el-col>
        <el-col :span="8">
          <div class="login-code">
            <img
              :src="loginForm.image"
              class="login-code-img"
              @click="handleRefresh"
            />
          </div>
        </el-col>
      </el-row>
    </el-form-item>
    <div class="save-pwd">
      <div>
        <el-checkbox v-model="isSavePwd">
          <span>记住密码</span>
        </el-checkbox>
      </div>
      <div>
        <el-tooltip
          class="item"
          effect="dark"
          content="请联系客服重置登录密码 电话：0510-85126279"
          placement="top"
        >
          <div style="font-size: 13px; color: #004fd8">忘记密码？</div>
        </el-tooltip>
      </div>
    </div>
    <el-form-item>
      <el-button
        type="primary"
        size="small"
        @click.native.prevent="handleLogin"
        class="login-submit"
        >{{ $t("login.submit") }}
      </el-button>
    </el-form-item>
    <el-dialog
      title="用户信息选择"
      append-to-body
      :visible.sync="userBox"
      width="350px"
    >
      <avue-form
        :option="userOption"
        v-model="userForm"
        @submit="submitLogin"
      />
    </el-dialog>
  </el-form>
</template>

<script>
import { mapGetters } from "vuex";
import { info } from "@/api/system/tenant";
import { getCaptcha, detail, getAccountDetail } from "@/api/user";
import { getTopUrl } from "@/util/util";
import { validatenull } from "@/util/validate";

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
        grantType: "captcha",
      },
      loginRules: {
        tenantId: [
          { required: false, message: "请输入租户ID", trigger: "blur" },
        ],
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 1, message: "密码长度最少为6位", trigger: "blur" },
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
      timer: 0,
      captchaInfo: {
        captchaShow: false,
        captchaTime: 0,
      },
      isCaptchaLock: false,
      interval: null,
    };
  },
  created() {
    this.getTenant();
    this.$once("hook:deactivated", () => {
      clearInterval(this.interval);
      this.interval = null;
    });
  },
  mounted() {
    // console.log(sessionStorage.getItem("captchaInfo"), "captchaInfo");
    this.captchaInfo = JSON.parse(
      sessionStorage.getItem("captchaInfo"),
      "captchaInfo"
    );
    if (this.captchaInfo.captchaShow) {
      this.handleRefresh();
    }
    let username = this.getCookie("username");
    let password = this.getCookie("password");
    if (username && password) {
      // 如果cookie中有信息则勾选记住密码
      this.isSavePwd = true;
      this.loginForm.username = username;
      this.loginForm.password = password;
    } else {
      // 否则不勾选
      this.isSavePwd = false;
    }
    detail().then((res) => {
      this.isCaptchaLock = res.data.data.isCaptchaLock == 1 ? true : false;
    });
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
    handleRefresh() {
      this.refreshCode();
      clearInterval(this.interval);
      this.timer = this.captchaInfo.captchaTime;
      this.getCaptchaTryTime();
    },
    getCaptchaTryTime() {
      let taht = this;
      this.interval = setInterval(() => {
        taht.timer--;
        console.log(taht.timer);
        if (taht.timer == 0) {
          this.refreshCode();
          taht.timer = taht.captchaInfo.captchaTime;
        }
      }, 1000);
    },
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
      if (form.deptId !== "") {
        this.loginForm.deptId = form.deptId;
      }
      if (form.roleId !== "") {
        this.loginForm.roleId = form.roleId;
      }
      this.handleLogin();
      done();
    },
    async handleLogin(item = {}) {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          const loading = this.$loading({
            lock: true,
            text: "登录中,请稍后。。。",
            spinner: "el-icon-loading",
          });
          if (this.isCaptchaLock) {
            getAccountDetail(this.loginForm.username).then((res) => {
              if (res.data.data.secretErrorResidueTimeCaptcha == 0) {
                this.captchaInfo = {
                  captchaShow: true,
                  captchaTime: res.data.data.time,
                };
                this.$emit("getChildData", this.captchaInfo);
                this.timer = this.captchaInfo.captchaTime;
                if (res.data.data.time == 0) {
                  this.refreshCode();
                } else {
                  this.getCaptchaTryTime();
                }
              } else {
                this.captchaInfo = {
                  captchaShow: false,
                  captchaTime: res.data.data.time,
                };
                this.$emit("getChildData", this.captchaInfo);
              }
            });
          }
          this.$store
            .dispatch("LoginByUsername", this.loginForm)
            .then(async () => {
              if (this.isSavePwd) {
                this.setCookie("tenantId", this.loginForm.tenantId, 7);
                this.setCookie("username", this.loginForm.username, 7);
                this.setCookie("password", this.loginForm.password, 7);
              } else {
                this.delCookie("tenantId");
                this.delCookie("username");
                this.delCookie("password");
              }
              if (this.website.switchMode) {
                const deptId = this.userInfo.dept_id;
                const roleId = this.userInfo.role_id;
                if (deptId.includes(",") || roleId.includes(",")) {
                  this.loginForm.deptId = deptId;
                  this.loginForm.roleId = roleId;
                  this.userBox = true;
                  this.$store.dispatch("LogOut").then(() => {
                    loading.close();
                  });
                }
              }

              await this.$store.dispatch("GetMenu", item.id).then((data) => {
                if (data.length !== 0) {
                  this.$router.$avueRouter.formatRoutes(data, true);
                }
              });
              this.$store.commit("SET_CURRENT_MENU", this.menu);

              this.openSysMenu(this.menu[0]);
              loading.close();
            })
            .catch(() => {
              loading.close();
              this.handleUsernameBlur();
            });
        }
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
  },
};
</script>

<style lang="scss" scoped>
.save-pwd {
  display: flex;
  justify-content: space-between;
}
</style>
