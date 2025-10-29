<template>
  <basic-container>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="已建应用" name="1"></el-tab-pane>
      <el-tab-pane label="企业产品" name="2"></el-tab-pane>
    </el-tabs>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :permission="permissionList"
      :before-open="beforeOpen"
      v-model="form"
      ref="crud"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="menuLeft">
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
          >删 除
        </el-button>
      </template>
      <template slot-scope="{ row }" slot="menu">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="$refs.crud.rowEdit(row, index)"
          >编 辑</el-button
        >
        <el-button
          type="success"
          plain
          size="mini"
          @click="$refs.crud.rowView(row, index)"
          >详 情</el-button
        >
        <!-- <el-button
          v-if="row.isEnable == 1"
          type="warning"
          plain
          size="mini"
          @click="handleStop(row)"
          >停 用</el-button
        > -->
        <el-button
          type="danger"
          plain
          size="mini"
          @click="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        >
      </template>
      <template slot="isEnable" slot-scope="scope">
        <span :style="{ color: scope.row.isEnable == 1 ? 'green' : 'red' }">{{
          scope.row.isEnableName
        }}</span>
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import {
  getList,
  getDetail,
  add,
  update,
  remove,
  changeEnable,
} from "@/api/application/category";
import { mapGetters } from "vuex";
const positiveIntegerRegex = /^[0-9]+$/;

export default {
  data() {
    const validatePass = (rule, value, callback) => {
      if (!positiveIntegerRegex.test(value)) {
        callback(new Error("请输入正整数进行排序"));
      } else {
        callback();
      }
    };
    return {
      activeName: "1",
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 10,
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
        index: true,
        viewBtn: false,
        editBtn: false,
        stripe: true,
        delBtn: false,
        selection: true,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        rowKey: "id",
        rowParentKey: "parentId",
        defaultExpandAll: true,
        searchLabelWidth: 72,
        menuWidth: 320,
        tree: true,
        column: [
          {
            label: "应用类别",
            prop: "name",
            search: true,
            span: 24,
            rules: [
              {
                required: true,
                message: "请输入应用类别",
                trigger: "blur",
              },
            ],
          },
          {
            label: "上级类别",
            prop: "parentId",
            hide: true,
            type: "tree",
            dicData: [],
            span: 24,
            props: {
              label: "name",
              value: "id",
            },
            align: "center",
            rules: [
              {
                required: true,
                message: "请选择上级类别",
                trigger: "blur",
              },
            ],
          },
          {
            label: "应用描述",
            prop: "description",
            span: 24,
            maxlength: 100,
            type: "textarea",
            minRows: 3,
            showWordLimit: true,
            align: "center",
          },
          // {
          //   label: "状态",
          //   prop: "isEnable",
          //   search: true,
          //   type: "radio",
          //   value: 1,
          //   span: 24,
          //   align: "center",
          //   dicData: [
          //     {
          //       label: "启用",
          //       value: 1,
          //     },
          //     {
          //       label: "停用",
          //       value: 0,
          //     },
          //   ],
          // },
          {
            label: "更新时间",
            prop: "updateTime",
            display: false,
            align: "center",
          },
          {
            label: "排序",
            prop: "sort",
            span: 24,
            align: "center",
            type: "number",
            rules: [
              { required: true, validator: validatePass, trigger: "change" },
            ],
          },
        ],
      },
      data: [],
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        //     addBtn: this.vaildData(
        //       this.permission.bhapplicationcategory_add,
        //       false
        //     ),
        //     viewBtn: this.vaildData(
        //       this.permission.bhapplicationcategory_view,
        //       false
        //     ),
        //     delBtn: this.vaildData(
        //       this.permission.bhapplicationcategory_delete,
        //       false
        //     ),
        //     editBtn: this.vaildData(
        //       this.permission.bhapplicationcategory_edit,
        //       false
        //     ),
      };
    },
    ids() {
      let ids = [];
      this.selectionList.forEach((ele) => {
        ids.push(ele.id);
      });
      return ids.join(",");
    },
  },
  methods: {
    handleClick() {
      this.onLoad();
    },
    handleStop(row) {
      changeEnable(row.id).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
        }
      });
    },
    rowSave(row, done, loading) {
      row.type = this.activeName;
      add(row).then(
        () => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          done();
        },
        (error) => {
          loading();
          window.console.log(error);
        }
      );
    },
    rowUpdate(row, index, done, loading) {
      update(row).then(
        () => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          done();
        },
        (error) => {
          loading();
          console.log(error);
        }
      );
    },
    rowDel(row) {
      this.$confirm("确定将选择数据删除?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return remove(row.id);
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        });
    },
    handleDelete() {
      if (this.selectionList.length === 0) {
        this.$message.warning("请选择至少一条数据");
        return;
      }
      this.$confirm("确定将选择数据删除?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return remove(this.ids);
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          this.$refs.crud.toggleSelection();
        });
    },
    beforeOpen(done, type) {
      if (["edit", "view"].includes(type)) {
        getDetail(this.form.id).then((res) => {
          this.form = res.data.data;
        });
      }
      done();
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
    selectionChange(list) {
      this.selectionList = list;
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      getList(this.activeName, Object.assign(params, this.query)).then(
        (res) => {
          this.data = res.data.data;
          var column = this.findObject(this.option.column, "parentId");
          column.dicData =
            res.data.data.length > 0
              ? [
                  {
                    name: "无",
                    id: "0",
                  },
                  ...res.data.data,
                ]
              : [
                  {
                    name: "无",
                    id: "0",
                  },
                ];
          this.loading = false;
          this.selectionClear();
        }
      );
    },
  },
};
</script>

<style>
</style>
