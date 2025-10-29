<template>
  <basic-container style="height: 99%" :key="randomKey">
    <div v-if="!announceDetailShow">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="全部" name=" "></el-tab-pane>
        <el-tab-pane label="通知公告" name="2"></el-tab-pane>
        <el-tab-pane label="服务指南" name="1"></el-tab-pane>
      </el-tabs>
      <avue-crud
        :option="option"
        :table-loading="loading"
        :data="data"
        :page.sync="page"
        ref="crud"
        @search-change="searchChange"
        @search-reset="searchReset"
        @refresh-change="refreshChange"
        @on-load="onLoad"
      >
        <template slot-scope="{ row, index }" slot="menu">
          <el-button type="text" size="samll" @click="openDetail(row, index)"
            >查看详情</el-button
          >
        </template>
      </avue-crud>
    </div>
    <div v-else style="height: 100%">
      <announceDetail :row="row" @returnBack="returnBack"></announceDetail>
    </div>
  </basic-container>
</template>
<script>
import { getNoticeList } from "@/api/front/front";
import announceDetail from "./announceDetail.vue";
export default {
  components: {
    announceDetail,
  },
  data() {
    return {
      activeName: " ",
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      option: {
        height: "auto",
        calcHeight: 0,
        tip: false,
        searchLabelWidth: 44,
        searchMenuSpan: 6,
        border: true,
        index: true,
        addBtn: false,
        menu: true,
        viewBtn: false,
        editBtn: false,
        stripe: true,
        delBtn: false,
        searchShowBtn: false,
        refreshBtn: false,
        selection: false,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        align: "center",
        column: [
          { label: "标题", prop: "title", search: true },
          { label: "公告类别", prop: "noticeTypeName" },
          { label: "来源", prop: "infoSource" },
          { label: "发布时间", prop: "createTime" },
        ],
      },
      data: [],
      announceDetailShow: false,
      row: {},
    };
  },
  props: {
    randomKey: {
      type: Number,
      default: 0,
    },
  },
  watch: {
    randomKey(val) {
      this.announceDetailShow = false;
    },
  },
  methods: {
    openDetail(row, i) {
      // console.log(row);
      this.row = row;
      this.announceDetailShow = true;
    },
    handleClick() {
      this.onLoad();
    },
    searchReset() {
      this.query = {};
      this.onLoad(this.page);
    },
    searchChange(params, done) {
      this.query = params;
      this.page.currentPage = 1;
      this.onLoad(this.page, params);
      done();
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      this.data = [];
      let param = {
        current: this.page.currentPage,
        size: this.page.pageSize,
        title: this.query.title == undefined ? "" : this.query.title,
        noticeType: this.activeName,
      };
      getNoticeList(param).then((res) => {
        const data = res.data.data;
        if (data.records.length > 0) {
          data.records.map((item) => {
            this.data.push({
              ...item,
              noticeTypeName: item.noticeType == 1 ? "服务指南" : "通知公告",
            });
          });
        }
        this.page.total = data.total;
        this.loading = false;
      });
    },
    returnBack() {
      this.announceDetailShow = false;
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.avue-crud__menu {
  display: none;
}
/deep/.el-button--primary {
  background-color: #0f71b7;
  border-color: #0f71b7;
}
/deep/.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #0f71b7 !important;
}
/deep/.el-table th.el-table__cell {
  background: #0f71b7;
  color: #fff;
}
</style>