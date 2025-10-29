<template>
  <basic-container>
    <avue-form
      v-model="form"
      :option="option"
      @submit="submit"
      style="padding: 0 200px"
    >
      <template slot="logo">
        <el-upload
          class="avatar-uploader"
          action="/api/squirrel-szbh/file/uploadImg"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :headers="headers"
          name="multipartFile"
        >
          <img v-if="form.logo" :src="form.logo" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <div>图片上传格式为：jpg、png且大小不能超过2MB</div>
      </template>
    </avue-form>
  </basic-container>
</template>
<script>
import { getSysInfo, submitSysInfo } from "@/api/system/tenant";
import { headers } from "@/api/tool/datasource";

export default {
  data() {
    return {
      headers: headers(), // 自定义上传空间需要的权限
      form: {},
      option: {
        column: [
          {
            label: "动态配置",
            prop: "isConfig",
            type: "switch",
            span: 24,
            dicData: [
              {
                label: "",
                value: 2,
              },
              {
                label: "",
                value: 1,
              },
            ],
          },
          {
            label: "系统logo",
            prop: "logo",
            span: 24,
            formslot: true,
          },
          {
            label: "系统名称",
            prop: "sysName",
            span: 24,
          },
          {
            label: "版本编号",
            prop: "sysCode",
            span: 24,
          },
          {
            label: "更新时间",
            prop: "updateTime",
            span: 24,
          },
          {
            label: "更新描述",
            prop: "remark",
            span: 24,
          },
        ],
      },
    };
  },
  mounted() {
    getSysInfo().then((res) => {
      this.form = res.data.data;
    });
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.info(res);
      this.form.logo = res.data.fileUrl;
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
    submit(form, done) {
      console.info(form);
      submitSysInfo(form).then((res) => {
        if (res.data.success) {
          this.$message.success("保存成功！");
        }
      });
      done();
    },
  },
};
</script>

<style lang="scss" scoped></style>
