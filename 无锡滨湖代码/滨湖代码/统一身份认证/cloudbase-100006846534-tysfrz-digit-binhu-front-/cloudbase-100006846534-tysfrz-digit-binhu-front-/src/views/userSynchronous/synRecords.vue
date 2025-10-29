<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      ref="crud"
      @search-change="searchChange"
      @search-reset="searchReset"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="menuLeft">
        <el-button
          type="warning"
          size="small"
          plain
          icon="el-icon-download"
          @click="handleExport"
          >导 出</el-button
        >
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import { getSynList } from "@/api/userSynchronous/userSynchronous";
import { getToken } from "@/util/auth";
export default {
  data() {
    return {
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      option: {
        height: "auto",
        calcHeight: 10,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        menu: false,
        viewBtn: false,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        searchLabelWidth: 118,
        stripe: true,
        column: [
          {
            label: "数据源/应用名称",
            prop: "appName",
            search: true,
          },
          {
            label: "开始时间",
            prop: "start",
            type: "date",
            display: false,
            hide: true,
            search: true,
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
            pickerOptions: {
              firstDayOfWeek: 1,
            },
          },
          {
            label: "结束时间",
            prop: "end",
            type: "date",
            display: false,
            hide: true,
            search: true,
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
            pickerOptions: {
              firstDayOfWeek: 1,
            },
          },
          {
            label: "数据类型",
            prop: "dataType",
          },
          {
            label: "同步类型",
            prop: "upOrDown",
          },
          {
            label: "同步结果",
            prop: "result",
          },
          {
            label: "异常原因",
            prop: "cause",
          },
          {
            label: "同步时间",
            prop: "createTime",
          },
        ],
      },
      data: [],
    };
  },
  methods: {
    handleExport() {
      this.$confirm("是否导出表格数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let appName = this.query.appName == undefined ? "" : this.query.appName;
        let start = this.query.start == undefined ? "" : this.query.start;
        let end = this.query.end == undefined ? "" : this.query.end;
        let str = `api/szbh-back/syncrecord/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&appName=${appName}&start=${start}&end=${end}`;
        window.open(str);
      });
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
    // selectionChange(list) {
    //   this.selectionList = list;
    // },
    // selectionClear() {
    //   this.selectionList = [];
    //   this.$refs.crud.toggleSelection();
    // },
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      getSynList(
        page.currentPage,
        page.pageSize,
        "",
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
        // this.selectionClear();
      });
    },
  },
};
</script>