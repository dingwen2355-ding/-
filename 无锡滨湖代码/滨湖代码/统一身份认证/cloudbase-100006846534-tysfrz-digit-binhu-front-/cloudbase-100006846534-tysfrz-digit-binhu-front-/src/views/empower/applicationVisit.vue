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
import { getBhDeptTree } from "@/api/organization/user";
import { getAppCategoryTree } from "@/api/organization/user";
import { getUserAppPage } from "@/api/empower/empower";
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
        searchLabelWidth: 44,
        searchSpan: 4,
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
        stripe: true,
        column: [
          {
            label: "账号",
            prop: "account",
            search: true,
          },
          {
            label: "姓名",
            prop: "name",
            search: true,
          },
          {
            label: "部门",
            prop: "deptName",
          },
          {
            label: "部门",
            prop: "deptIds",
            search: true,
            type: "tree",
            hide: true,
            dicData: [],
            props: {
              label: "title",
              value: "id",
            },
          },
          {
            label: "职位",
            prop: "positionName",
          },
          {
            label: "是否可登录",
            prop: "isEnableLoginName",
          },
          {
            label: "应用",
            prop: "appName",
          },
          {
            label: "应用",
            prop: "appIds",
            search: true,
            hide: true,
            type: "tree",
            dicData: [],
            props: {
              label: "title",
              value: "id",
            },
          },
          {
            label: "创建时间",
            prop: "createTime",
          },
        ],
      },
      data: [],
    };
  },
  mounted() {
    getBhDeptTree().then((res) => {
      var column = this.findObject(this.option.column, "deptIds");
      column.dicData = res.data.data;
    });
    getAppCategoryTree("", 2).then((res) => {
      var column = this.findObject(this.option.column, "appIds");
      column.dicData = res.data.data;
    });
  },
  methods: {
    handleExport() {
      this.$confirm("是否导出表格数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let account = this.query.account == undefined ? "" : this.query.account;
        let name = this.query.name == undefined ? "" : this.query.name;
        let deptIds = this.query.deptIds == undefined ? "" : this.query.deptIds;
        let appIds = this.query.appIds == undefined ? "" : this.query.appIds;

        let str = `api/szbh-back/applicationauthority/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&account=${account}&name=${name}&deptIds=${deptIds}&appIds=${appIds}`;
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
      getUserAppPage(
        page.currentPage,
        page.pageSize,
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