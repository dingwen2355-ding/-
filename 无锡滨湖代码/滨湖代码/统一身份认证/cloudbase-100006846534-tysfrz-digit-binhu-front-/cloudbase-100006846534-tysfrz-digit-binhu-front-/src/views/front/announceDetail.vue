<template>
  <div style="height: 100%">
    <el-page-header @click.native="returnBack" content="公告详情">
    </el-page-header>
    <h3 class="announce-title">{{ detailInfo.title }}</h3>
    <div class="announce-center">
      <div style="margin-left: 12px">信息来源：{{ detailInfo.infoSource }}</div>
      <div style="width: 240px">发布时间：{{ detailInfo.createTime }}</div>
    </div>
    <el-divider></el-divider>
    <div class="announce-content" v-html="detailInfo.content"></div>
    <div style="margin: 50px 0 0 20px">
      附件下载：<span v-if="detailInfo.fileList.length > 0">
        <el-link
          :underline="false"
          v-for="item in detailInfo.fileList"
          :key="item.id"
          @click="handleDownload(item)"
          >{{ item.fileName }}&nbsp;&nbsp;&nbsp;
        </el-link>
      </span>
      <el-link v-else disabled>暂无</el-link>
    </div>
  </div>
</template>

<script>
import { detail } from "@/api/front/front";
export default {
  props: {
    row: {
      type: Object,
      default: {},
    },
  },
  data() {
    return {
      detailInfo: { fileList: [] },
    };
  },
  mounted() {
    // console.log(this.row, "row");
    detail(this.row.id).then((res) => {
      this.detailInfo = { ...res.data.data };
    });
  },
  methods: {
    handleDownload(data) {
      window.open(data.fileUrl);
    },
    returnBack() {
      this.$emit("returnBack");
    },
  },
};
</script>
<style scoped>
.announce-title {
  text-align: center;
  letter-spacing: 2px;
}
.announce-center {
  display: flex;
  justify-content: space-between;
}
.announce-content {
  padding: 0 20px;
}
</style>