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
      <template slot-scope="{ row, index }" slot="menu">
        <!-- <el-button
          type="success"
          plain
          size="mini"
          @click="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        > -->
      </template>
      <template slot="menuLeft">
        <!-- <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
          >删 除
        </el-button> -->
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import { getLoginList } from "@/api/application/category";
export default {
  data() {
    return {
      query: {},
      loading: true,
      page: {
        pageSize: 30,
        currentPage: 1,
        total: 0,
      },
      option: {
        height: "auto",
        calcHeight: 30,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        searchLabelWidth: 72,
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
            label: "登录账号",
            prop: "username",
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
            label: "登录IP",
            prop: "ip",
          },
          {
            label: "是否篡改",
            prop: "operVerif",
          },
          // {
          //   label: "登录地",
          //   prop: "name",
          // },
          // {
          //   label: "设备信息",
          //   prop: "createTime",
          // },
          // {
          //   label: "本次登录Token",
          //   prop: "token",
          //   overHidden: true,
          // },
          // { label: "操作说明", prop: "token" },
          {
            label: "登录时间",
            prop: "createDate",
          },
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
      this.onLoad(this.page, params);
      done();
    },
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
      getLoginList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
      });
    },
  },
};
</script>
