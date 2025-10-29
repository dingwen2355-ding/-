<template>
  <div>
    <basic-container>
      <avue-form
        :option="option"
        v-model="form"
        @tab-click="handleTabClick"
        @submit="handleSubmit"
      ></avue-form>
    </basic-container>
  </div>
</template>

<script>
// import option from "@/option/user/info";
import { getUserInfo, updateInfo, updatePassword } from "@/api/system/user";
import md5 from "js-md5";
import func from "@/util/func";
import { validatePassword } from "@/util/validate";
const regexText = "口令长度8位及以上，至少包含大小写字母、数字、特殊符号三种（~!@#$%^&*）及以上，不允许出现连续数字、字母，不允许包含账户名";

export default {
  data() {
    // const passwordRegex =
    //   /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~!@#$%^&*]).{8,}$/;

    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码!"));
      } else if (!validatePassword(value)) {
        callback(new Error(regexText));
      } else {
        if (this.form.newPassword1 !== "") {
          this.$refs.form.$refs.form.validateField("newPassword1");
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码!"));
      } else if (!validatePassword(value)) {
        callback(new Error(regexText));
      } else if (value !== this.form.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      index: 0,
      option: {
        tabs: true,
        tabsActive: 1,
        group: [
          {
            label: "个人信息",
            prop: "info",
            column: [
              {
                label: "头像",
                type: "upload",
                listType: "picture-img",
                propsHttp: {
                  fileName: "multipartFile",
                  res: "data",
                  url: "fileUrl",
                },
                dataType: "string",
                canvasOption: {
                  text: " ",
                  ratio: 0.1,
                },
                action: "/api/squirrel-szbh/file/uploadImg",
                tip: "只能上传jpg/png用户头像，且不超过500kb",
                span: 12,
                row: true,
                prop: "avatar",
              },
              {
                label: "姓名",
                span: 12,
                row: true,
                prop: "realName",
              },
              {
                label: "用户名",
                span: 12,
                row: true,
                prop: "name",
              },
              {
                label: "手机号",
                span: 12,
                row: true,
                prop: "phone",
              },
              {
                label: "邮箱",
                prop: "email",
                span: 12,
                row: true,
              },
            ],
          },
          {
            label: "修改密码",
            prop: "password",
            column: [
              {
                label: "原密码",
                span: 24,
                row: true,
                type: "password",
                prop: "oldPassword",
                rules: [
                  { required: true, message: "请输入原密码", trigger: "blur" },
                ],
              },
              {
                label: "新密码",
                span: 24,
                row: true,
                type: "password",
                prop: "newPassword",
                placeholder:
                  "口令长度8位及以上，至少包含大小写字母、数字、特殊符号三种（~!@#$%^&*）及以上，不允许出现连续数字、字母，不允许包含账户名",
                rules: [
                  { required: true, validator: validatePass, trigger: "blur" },
                ],
              },
              {
                label: "确认密码",
                span: 24,
                row: true,
                type: "password",
                prop: "newPassword1",
                placeholder:
                  "口令长度8位及以上，至少包含大小写字母、数字、特殊符号三种（~!@#$%^&*）及以上，不允许出现连续数字、字母，不允许包含账户名",
                rules: [
                  { required: true, validator: validatePass2, trigger: "blur" },
                ],
              },
            ],
          },
        ],
      },
      form: {},
    };
  },
  created() {
    this.handleWitch();
  },
  methods: {
    handleSubmit(form, done) {
      if (this.index === 0) {
        updateInfo(form).then(
          (res) => {
            if (res.data.success) {
              this.$message({
                type: "success",
                message: "修改信息成功!",
              });
            } else {
              this.$message({
                type: "error",
                message: res.data.msg,
              });
            }
            done();
          },
          (error) => {
            window.console.log(error);
            done();
          }
        );
      } else {
        updatePassword(
          md5(form.oldPassword),
          md5(form.newPassword),
          md5(form.newPassword1)
        ).then(
          (res) => {
            if (res.data.success) {
              this.$message({
                type: "success",
                message: "修改密码成功!",
              });
            } else {
              this.$message({
                type: "error",
                message: res.data.msg,
              });
            }
            done();
          },
          (error) => {
            window.console.log(error);
            done();
          }
        );
      }
    },
    handleWitch() {
      if (this.index === 0) {
        getUserInfo().then((res) => {
          const user = res.data.data;
          this.form = {
            id: user.id,
            avatar: user.avatar,
            name: user.name,
            realName: user.realName,
            phone: user.phone,
            email: user.email,
          };
        });
      }
    },
    handleTabClick(tabs) {
      this.index = func.toInt(tabs.index);
      this.handleWitch();
    },
  },
};
</script>

<style>
</style>
