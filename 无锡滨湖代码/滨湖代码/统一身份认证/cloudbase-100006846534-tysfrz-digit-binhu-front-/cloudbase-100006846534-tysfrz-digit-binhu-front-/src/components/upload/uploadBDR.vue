<template>
  <el-upload
    class="upload-single"
    :headers="headers"
    action="/api/squirrel-szbh/file/upload"
    :show-file-list="showfile"
    :limit="limit"
    :file-list="fileList"
    :on-success="PicSuccess"
    :beforeUpload="BeforePicUpload"
    :on-error="PicError"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    name="multipartFile"
  >
    <el-button :disabled="dialogType == 'view'" size="small" type="primary"
      >点击上传</el-button
    >
    <span v-if="tips" slot="tip" class="el-upload__tip">{{ tips }}</span>
  </el-upload>
</template>

<script>
import { getToken } from "@/util/auth";
import { deepClone } from "@/util/util";
export default {
  props: {
    isImg: {
      //是否限制只上传图片文件
      type: Boolean,
      default: true,
    },
    tips: {
      type: String,
      default: "",
    },
    limit: {
      type: Number,
      default: 1,
    },
    isSingle: {
      //是否上传单个
      type: Boolean,
      default: true,
    },
    fileList: {
      type: Array,
      default: () => [],
    },
    showfile: {
      type: Boolean,
      default: false,
    },
    dialogType: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      headers: { "Blade-Auth": getToken() }, // 自定义上传空间需要的权限
    };
  },
  methods: {
    handleRemove(file, fileList) {
      let arr = fileList.map((item) => {
        return item.id;
      });
      this.$emit("update:fileIds", arr.join(","));
      this.$emit("update:fileList", fileList);
      this.$emit("success", fileList);
    },
    handlePreview(file) {
      this.$emit("filePreview", file);
    },
    // 证件照上传成功后 执行的函数
    PicSuccess(res, file, fileList) {
      if (this.isSingle) {
        res.data.name = res.data.fileName;
        this.$emit("update:fileList", [res.data]);
        this.$emit("update:fileIds", res.data.id);
        this.$emit("success", res.data);
      } else {
        let newFileList = deepClone(fileList);
        let arr = [];
        for (let i = 0; i < newFileList.length; i++) {
          if (newFileList[i].response) {
            newFileList[i].id = newFileList[i].response.data.id;
            newFileList[i].fileName = newFileList[i].response.data.fileName;
          }
          arr.push(newFileList[i].id);
          newFileList[i].name = newFileList[i].fileName;
        }
        this.$emit("update:fileList", newFileList);
        this.$emit("update:fileIds", arr.join(","));
        this.$emit("success", newFileList);
      }
    },
    BeforePicUpload(file) {
      if (this.isImg) {
        //是否限制只上传图片文件
        var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
        // console.info(testmsg);
        const extension = testmsg === "jpg";
        const extension2 = testmsg === "png";
        const extension3 = testmsg === "PNG";
        if (!extension && !extension2 && !extension3) {
          this.$message({
            message: "上传文件只能是 jpg、png!",
            type: "warning",
          });
        }
        return extension || extension2 || extension3;
      } else {
        const isLt50M = file.size / 1024 / 1024 < 50;
        if (!isLt50M) {
          this.$message({
            message: "上传文件大小不能超过 50MB!",
            type: "warning",
          });
        }
        return isLt50M;
      }
    },
    PicError() {
      this.$message({
        message: "文件上传失败",
        type: "error",
      });
    },
  },
};
</script>

<style lang="scss">
.upload-single {
  margin-bottom: 5px;
  .el-upload__tip {
    margin-left: 10px;
  }
}
</style>
