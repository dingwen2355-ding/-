<template>
  <basic-container style="height: 100%">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="密码策略" name="1">
        <div class="tip">
          <i class="el-icon-info" />
          提示：口令长度8位及以上，至少包含大小写字母、数字、特殊符号三种（~!@#$%^&*）及以上，不允许出现连续数字、字母，不允许包含账户名
        </div>
        <el-form
          :model="form1"
          ref="form1"
          label-width="100px"
          class="demo-dynamic"
        >
          <!-- <el-form-item
            prop="secretStrength"
            label="密码强度:"
            :rules="[
              { required: true, message: '请选择密码强度', trigger: 'blur' },
            ]"
          >
            <el-select v-model="form1.secretStrength" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item
            prop="secretMaxLength"
            label="最大长度:"
            :rules="[
              { required: true, message: '请输入最大长度', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form1.secretMaxLength"
              :min="1"
            ></el-input-number>
          </el-form-item>
          <el-form-item
            prop="secretMinLength"
            label="最小长度:"
            :rules="[
              { required: true, message: '请输入最小长度', trigger: 'blur' },
            ]"
          >
            <el-input-number
              v-model="form1.secretMinLength"
              :min="1"
            ></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="submitForm('form1')">
              保存
            </el-button>
            <el-button @click="resetForm('form1')" size="small">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="登录配置" name="2">
        <el-form
          :model="form2"
          ref="form2"
          label-width="170px"
          class="demo-dynamic"
        >
          <el-form-item prop="isLoginLock" label="锁定设置:">
            <el-switch v-model="form2.isLoginLock"> </el-switch>
          </el-form-item>
          <el-form-item
            prop="secretErrorLockTime"
            label="密码错误锁定次数:"
            :rules="[
              {
                required: true,
                message: '请输入密码错误锁定次数',
                trigger: 'blur',
              },
            ]"
          >
            <el-input-number
              v-model="form2.secretErrorLockTime"
              :min="1"
            ></el-input-number>
          </el-form-item>
          <el-form-item
            prop="secretTryTimeRange"
            label="密码尝试时间范围:"
            :rules="[
              {
                required: true,
                message: '请输入密码尝试时间范围',
                trigger: 'blur',
              },
            ]"
          >
            <el-input-number
              v-model="form2.secretTryTimeRange"
              :min="1"
            ></el-input-number>
            <span style="margin-left: 10px">秒</span>
          </el-form-item>
          <el-form-item
            prop="secretUnlockTime"
            label="解锁时间:"
            :rules="[
              {
                required: true,
                message: '请输入解锁时间',
                trigger: 'blur',
              },
            ]"
          >
            <el-input-number
              v-model="form2.secretUnlockTime"
              :min="1"
            ></el-input-number>
            <span style="margin-left: 10px">秒</span>
          </el-form-item>
          <el-form-item prop="isCaptchaLock" label="验证码:">
            <el-switch v-model="form2.isCaptchaLock"> </el-switch>
          </el-form-item>
          <el-form-item
            prop="sercetErrorTimeCaptcha"
            label="密码错误次数:"
            :rules="[
              {
                required: true,
                message: '请输入密码错误次数',
                trigger: 'blur',
              },
            ]"
          >
            <el-input-number
              v-model="form2.sercetErrorTimeCaptcha"
              :min="1"
            ></el-input-number>
          </el-form-item>
          <el-form-item
            prop="captchaTryTimeRange"
            label="验证码自动刷新时间:"
            :rules="[
              {
                required: true,
                message: '请输入验证码自动刷新时间',
                trigger: 'blur',
              },
            ]"
          >
            <el-input-number
              v-model="form2.captchaTryTimeRange"
              :min="1"
            ></el-input-number>
            <span style="margin-left: 10px">秒</span>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="submitForm2('form2')"
              >保存</el-button
            >
            <el-button @click="resetForm('form2')" size="small">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </basic-container>
</template>

<script>
import { submit } from "@/api/system/user";
import { detail } from "@/api/user";
export default {
  data() {
    return {
      activeName: "1",
      form1: {},
      options: [
        {
          value: 3,
          label: "高",
        },
        {
          value: 2,
          label: "中",
        },
        {
          value: 1,
          label: "低",
        },
      ],
      form2: {},
    };
  },
  mounted() {
    detail().then((res) => {
      this.form1 = res.data.data;
    });
  },
  methods: {
    handleClick() {
      detail().then((res) => {
        this.form2 = res.data.data;
        if (this.form2.isLoginLock) {
          this.form2.isLoginLock = true;
        } else {
          this.form2.isLoginLock = false;
        }
        if (this.form2.isCaptchaLock) {
          this.form2.isCaptchaLock = true;
        } else {
          this.form2.isCaptchaLock = false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          submit(this.form1).then((res) => {
            if (res.data.success) {
              this.$message.success("操作成功！");
            }
          });
        }
      });
    },
    submitForm2(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = {
            ...this.form2,
            isCaptchaLock: this.form2.isCaptchaLock ? 1 : 0,
            isLoginLock: this.form2.isLoginLock ? 1 : 0,
          };
          submit(data).then((res) => {
            if (res.data.success) {
              this.$message.success("操作成功！");
            }
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.tip {
  font-size: 14px;
  margin-bottom: 10px;
  color: #666;
}
/deep/.basic-container__card {
  height: 92%;
}
</style>