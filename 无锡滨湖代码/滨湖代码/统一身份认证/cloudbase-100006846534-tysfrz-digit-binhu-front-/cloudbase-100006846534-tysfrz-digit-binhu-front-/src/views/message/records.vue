<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      v-model="form"
      ref="crud"
      @search-change="searchChange"
      @search-reset="searchReset"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="menuLeft">
        <!-- <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          plain
          v-if="permission.bhsmssend_delete"
          @click="handleDelete"
          >删 除
        </el-button> -->
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import { getMsgList } from "@/api/message/message";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
      },
      selectionList: [],
      option: {
        height: "auto",
        calcHeight: 30,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        searchLabelWidth: 72,
        searchSpan: 4,
        border: true,
        index: true,
        addBtn: false,
        selection: false,
        dialogClickModal: false,
        align: "center",
        stripe: true,
        columnBtn: false,
        menu: false,
        column: [
          {
            label: "搜索关键词",
            prop: "param",
            search: true,
            hide: true,
            searchLabelWidth: 86,
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
            label: "应用",
            prop: "appName",
          },
          {
            label: "发送内容",
            prop: "content",
          },
          // {
          //   label: "用户姓名",
          //   prop: "noticePerson",
          // },
          {
            label: "手机号",
            prop: "mobile",
          },
          // {
          //   label: "事件类型",
          //   prop: "eventType",
          // },
          {
            label: "接收状态",
            prop: "result",
            search: true,
            type: "radio",
            dicData: [
              {
                label: "成功",
                value: 1,
              },
              {
                label: "失败",
                value: 0,
              },
            ],
          },
          {
            label: "发送时间",
            prop: "noticeTime",
          },
        ],
      },
      data: [],
    };
  },
  computed: {
    ...mapGetters(["permission"]),
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
      getMsgList(
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

<style>
</style>
