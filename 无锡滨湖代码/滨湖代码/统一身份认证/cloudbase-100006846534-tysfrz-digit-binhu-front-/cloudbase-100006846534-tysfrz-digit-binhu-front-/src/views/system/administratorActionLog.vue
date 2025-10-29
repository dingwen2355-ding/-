<template>
  <div class="tabelContainer">
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
        @size-change="sizeChange"
        @refresh-change="refreshChange"
        @on-load="onLoad"
      >
        <template slot="operVerif" slot-scope="scope">
            <span
              :style="{ color: scope.row.operVerif == '是' ? 'red' : 'green' }"
            >{{ scope.row.operVerif }}</span>
        </template>
        <template slot="menuLeft">
          <!-- <el-button
            type="warning"
            size="small"
            plain
            icon="el-icon-download"
            @click="handleExport"
            >导 出</el-button
          > -->
        </template>
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import { getLogs } from "@/api/application/category";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 30,
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
        border: true,
        searchLabelWidth: 72,
        index: true,
        dialogClickModal: false,
        menu: false,
        addBtn: false,
        columnBtn: false,
        align: "center",
        searchShowBtn: false,
        stripe: true,
        column: [
          {
            label: "用户账号",
            prop: "username",
            search: true,
          },
          {
            label: "操作",
            prop: "operation",
            search: true,
          },
          {
            label: "操作时间",
            prop: "createDate",
          },
          {
            label: "用户地址",
            prop: "ip",
            search: true,
          },
          {
            label: "是否篡改",
            prop: "operVerif",
          }
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
      getLogs(
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
