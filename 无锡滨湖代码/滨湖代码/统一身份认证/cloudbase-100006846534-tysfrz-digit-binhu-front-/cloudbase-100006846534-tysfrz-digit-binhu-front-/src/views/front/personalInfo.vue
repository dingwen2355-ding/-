<template>
  <basic-container>
    <el-tabs tab-position="left" v-model="activeName" style="height: 700px">
      <el-tab-pane label="用户管理" name="1">
        <div style="color: orange; margin-left: 40px">
          注：若想修改其他个人信息，请联系管理员。
        </div>
        <el-form
          ref="form"
          style="width: 600px; padding: 20px"
          :model="form"
          :rules="rules"
          label-width="100px"
        >
          <el-form-item label="登录账号:" prop="account">
            <el-input v-model="form.account" disabled></el-input>
          </el-form-item>
          <el-form-item label="姓名:" prop="name">
            <el-input v-model="form.name" disabled></el-input>
          </el-form-item>
          <el-form-item label="职务:">
            <el-input v-model="form.majorPosition" disabled></el-input>
          </el-form-item>
          <el-form-item label="主岗部门:" prop="majorDept">
            <el-input v-model="form.majorBhDeptName" disabled></el-input>
          </el-form-item>
          <el-form-item label="手机号码:">
            <el-input v-model="form.majorMobile"></el-input>
          </el-form-item>
          <el-form-item label="修改头像:">
            <el-upload
              class="avatar-uploader"
              accept=".jpg,.png,.jpeg,.gif"
              action="/api/squirrel-szbh/file/uploadImg"
              :headers="headers"
              :show-file-list="false"
              :file-list="fileList"
              :before-upload="beforeAvatarUpload"
              :on-success="handleAvatarSuccess"
              name="multipartFile"
            >
              <img v-if="form.avatar" :src="form.avatar" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div>图片上传格式为：jpg、png且大小不能超过2MB</div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="submitForm">
              保存
            </el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="修改密码" name="2">
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          label-width="110px"
          style="width: 670px; padding: 20px"
        >
          <el-form-item
            label="原密码："
            prop="oldPassword"
            class="special-form-item"
          >
            <el-input v-model="ruleForm.oldPassword" clearable></el-input>
          </el-form-item>
          <el-form-item
            label="新密码："
            prop="newPassword"
            class="special-form-item"
          >
            <el-input
              clearable
              autocomplete="off"
              :placeholder="placeholder"
              v-model="ruleForm.newPassword"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="确认新密码："
            prop="checkPass"
            class="special-form-item"
          >
            <el-input
              clearable
              v-model="ruleForm.checkPass"
              :placeholder="placeholder"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="submitPswForm">
              保存
            </el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </basic-container>
</template>

<script>
import { getToken } from "@/util/auth";
import {
  getAccountDetail,
  update,
  updatePassword,
  getPasswordBit,
} from "@/api/front/front";
import { validatePassword } from "@/util/validate";

export default {
  data() {
    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~!@#$%^&*]).{8,}$/;
    const regexText =
      "口令长度8位及以上，至少包含大小写字母、数字、特殊符号三种（~!@#$%^&*）及以上，不允许出现连续数字、字母，不允许包含账户名!";
    const validatePass = (rule, value, callback) => {
      if (!value || value === "") {
        callback(new Error("请输入密码"));
      } else if (!validatePassword(value)) {
        callback(new Error(regexText));
      }
      // else if (value.length < this.min) {
      //   callback(new Error("密码长度不够"));
      // }
      else {
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (!value || value === "") {
        callback(new Error("请再次输入密码"));
      } else if (!validatePassword(value)) {
        callback(new Error(regexText));
      } else if (value != this.ruleForm.newPassword) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
      }
    };
    const phoneRegex = /^1[3-9]\d{9}$/;
    return {
      activeName: "1",
      form: {},
      ruleForm: {},
      rules: {
        account: [
          { required: true, message: "请输入登录账号", trigger: "blur" },
        ],
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        majorDept: [
          { required: true, message: "请输入主岗部门", trigger: "blur" },
        ],
        oldPassword: [
          { required: true, message: "请输入原密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, validator: validatePass, trigger: "blur" },
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],
      },
      headers: {},
      fileList: [],
      min: 0,
      max: 0,
      placeholder: "",
    };
  },
  mounted() {
    this.headers = { "Blade-Auth": getToken() }; // 自定义上传空间需要的权限
    getAccountDetail().then((res) => {
      // console.log(res.data.data);
      this.form = res.data.data;
    });
    getPasswordBit().then((res) => {
      this.min = res.data.data.minLength;
      this.max = res.data.data.maxLength;
      // this.placeholder = `密码为${this.min}位数以上，包含数字、字母及符号`;
      this.placeholder = `密码为8位以上，至少包含大小写字母、数字、特殊符号（~!@#$%^&*）三种以上`;
    });
  },
  methods: {
    submitForm() {
      update({
        id: this.form.id,
        majorMobile: this.form.majorMobile,
        avatar: this.form.avatar,
      }).then((res) => {
        this.$message.success("操作成功！");
      });
    },
    submitPswForm() {
      this.$refs.ruleForm.validate(async (valid) => {
        if (valid) {
          await updatePassword(this.ruleForm).then((res) => {
            this.$message.success("操作成功");
          });
        }
      });
    },
    beforeAvatarUpload(file) {
      var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      const extension = testmsg === "jpg";
      const extension2 = testmsg === "png";
      if (!extension && !extension2) {
        this.$message({
          message: "上传文件只能是 jpg、png!",
          type: "warning",
        });
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return (extension || extension2) && isLt2M;
    },
    handleAvatarSuccess(res, file) {
      // console.log(res);
      // console.log(file);
      this.fileList = [
        {
          name: file.response.data.fileName,
          url: file.response.data.fileUrl,
        },
      ];
      this.form.avatar = file.response.data.fileUrl;
      // console.log(this.fileList);
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
/deep/ .el-form-item.special-form-item {
  margin-bottom: 35px;
}
</style>