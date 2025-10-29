<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      v-model="form"
      :before-open="beforeOpen"
      @row-update="rowUpdate"
      ref="crud"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot-scope="{ row, index }" slot="menu">
        <el-button
          type="primary"
          plain
          size="mini"
          v-if="row.reviewStatus == 0"
          @click="$refs.crud.rowEdit(row, index)"
          >审 核</el-button
        >
        <el-button
          type="success"
          plain
          size="mini"
          @click="$refs.crud.rowView(row, index)"
          >详 情</el-button
        >
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import {
  getAppsApplicationList,
  updateApps,
} from "@/api/application/application";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      form: {},
      loading: true,
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
      },
      option: {
        height: "auto",
        calcHeight: 30,
        tip: false,
        border: true,
        index: true,
        addBtn: false,
        selection: false,
        dialogClickModal: false,
        align: "center",
        stripe: true,
        editTitle: "审核",
        delBtn: false,
        columnBtn: false,
        menu: true,
        editBtn: false,
        dialogWidth: "35%",
        column: [
          {
            label: "应用名称",
            prop: "name",
            editDisplay: false,
            span: 24,
          },
          {
            label: "所属部门",
            prop: "deptName",
            span: 24,
            editDisplay: false,
          },
          {
            label: "申请时间",
            prop: "createTime",
            span: 24,
            editDisplay: false,
          },
          {
            label: "申请人",
            prop: "applicantName",
            span: 24,
            editDisplay: false,
          },
          {
            label: "审核意见",
            prop: "reviewOpinion",
            span: 24,
            type: "textarea",
            minRows: 3,
          },
          {
            label: "审核状态",
            prop: "reviewStatusName",
            editDisplay: false,
          },
          {
            label: "审核状态",
            prop: "reviewStatus",
            type: "select",
            hide: true,
            viewDisplay: false,
            span: 24,
            dicData: [
              {
                label: "通过",
                value: 1,
              },
              {
                label: "驳回",
                value: 2,
              },
            ],
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
    beforeOpen(done, type) {
      if (["edit"].includes(type)) {
        this.form = {
          id: this.form.id,
        };
      }
      done();
    },
    rowUpdate(row, index, done, loading) {
      let params = {
        id: row.id,
        reviewOpinion: row.reviewOpinion,
        reviewStatus: row.reviewStatus,
      };
      updateApps(params).then(
        () => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          done();
        },
        (error) => {
          window.console.log(error);
          loading();
        }
      );
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
      getAppsApplicationList(page.currentPage, page.pageSize).then((res) => {
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
