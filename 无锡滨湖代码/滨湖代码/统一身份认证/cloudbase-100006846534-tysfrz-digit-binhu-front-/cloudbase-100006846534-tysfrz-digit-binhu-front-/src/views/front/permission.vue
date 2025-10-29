<template>
  <basic-container style="height: 99%">
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
    </avue-crud>
  </basic-container>
</template>
<script>
import { getUserApplyFor } from "@/api/front/front";
export default {
  data() {
    return {
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
        searchShow: true,
        searchMenuSpan: 6,
        searchLabelWidth: 72,
        border: true,
        index: true,
        addBtn: false,
        menu: false,
        viewBtn: false,
        searchShowBtn: false,
        refreshBtn: false,
        editBtn: false,
        stripe: true,
        delBtn: false,
        selection: false,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        align: "center",
        column: [
          { label: "应用名称", prop: "appName", search: true },
          { label: "所属部门", prop: "deptName" },
          { label: "申请时间", prop: "createTime" },
          { label: "申请理由", prop: "reason" },
          {
            label: "审核状态",
            prop: "reviewStatus",
            slot: true,
            type: "select",
            search: true,
            dicData: [
              { label: "待审核", value: 0 },
              { label: "通过", value: 1 },
              { label: "驳回", value: 2 },
            ],
          },
          { label: "审核意见", prop: "reviewOpinion" },
        ],
      },
      data: [],
    };
  },
  methods: {
    searchReset() {
      this.query = {};
      this.onLoad(this.page);
    },
    searchChange(params, done) {
      this.query = params;
      this.page.currentPage = 1;
      this.onLoad();
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
        appName: this.query.appName == undefined ? "" : this.query.appName,
        reviewStatus:
          this.query.reviewStatus == undefined ? "" : this.query.reviewStatus,
      };
      getUserApplyFor(param).then((res) => {
        const data = res.data.data;
        this.data = data.records;
        this.page.total = data.total;
        this.loading = false;
      });
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