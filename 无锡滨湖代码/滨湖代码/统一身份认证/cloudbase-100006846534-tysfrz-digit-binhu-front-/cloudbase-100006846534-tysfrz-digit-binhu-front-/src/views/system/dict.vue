<template>
  <basic-container>
    <avue-crud
      :option="optionParent"
      :table-loading="loading"
      :data="dataParent"
      :page="pageParent"
      ref="crud"
      v-model="formParent"
      :permission="permissionList"
      :before-open="beforeOpen"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoadParent"
    >
      <template slot="menuLeft">
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDeleteChild"
          >删 除
        </el-button>
      </template>
      <template slot-scope="{ row, index }" slot="menu">
        <el-button
          type="primary"
          plain
          size="mini"
          @click.stop="handleRowClick(row, 'edit')"
          >编 辑</el-button
        >
        <el-button
          type="danger"
          plain
          size="mini"
          @click.stop="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        >
        <el-button
          type="success"
          size="mini"
          plain
          @click.stop="handleRowClick(row, 'view')"
          >字典详情
        </el-button>
      </template>
      <template slot-scope="{ row }" slot="code">
        <el-tag @click="handleRowClick(row, 'view')" style="cursor: pointer">{{
          row.code
        }}</el-tag>
      </template>
      <template slot-scope="{ row }" slot="isSealed">
        <el-tag>{{ row.isSealed === 0 ? "否" : "是" }}</el-tag>
      </template>
    </avue-crud>
    <el-dialog
      :title="`[${dictValue}]字典`"
      append-to-body
      :visible.sync="box"
      width="1000px"
    >
      <avue-crud
        :option="optionChild"
        :table-loading="loadingChild"
        :data="dataChild"
        ref="crudChild"
        v-model="formChild"
        :permission="permissionList"
        :before-open="beforeOpenChild"
        :before-close="beforeCloseChild"
        @row-del="rowDelChild"
        @row-update="rowUpdateChild"
        @row-save="rowSaveChild"
        @search-change="searchChangeChild"
        @search-reset="searchResetChild"
        @selection-change="selectionChangeChild"
        @current-change="currentChangeChild"
        @size-change="sizeChangeChild"
        @refresh-change="refreshChangeChild"
        @on-load="onLoadChild"
      >
        <template slot="menuLeft" v-if="type == 'edit'">
          <el-button
            type="danger"
            size="small"
            icon="el-icon-delete"
            v-if="permission.dict_delete"
            @click="handleDeleteChild"
            >删 除
          </el-button>
        </template>
        <template slot-scope="{ row, index }" slot="menu">
          <el-button
            type="primary"
            plain
            size="mini"
            @click="$refs.crudChild.rowEdit(row, index)"
            >编 辑</el-button
          >
          <el-button
            type="success"
            plain
            size="mini"
            @click.stop="handleAdd(row, index)"
            >新增子项
          </el-button>
        </template>
        <template slot-scope="{ row }" slot="isSealed">
          <el-tag>{{ row.isSealed === 0 ? "否" : "是" }}</el-tag>
        </template>
      </avue-crud>
    </el-dialog>
  </basic-container>
</template>

<script>
import {
  getParentList,
  getChildList,
  remove,
  update,
  add,
  getDict,
  getDictTree,
} from "@/api/system/dict";
import { optionParent, optionChild1, optionChild2 } from "@/option/system/dict";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      type: "",
      dictValue: "暂无",
      parentId: -1,
      formParent: {},
      formChild: {},
      selectionList: [],
      query: {},
      box: false,
      loading: true,
      loadingChild: true,
      pageParent: {
        pageSize: 10,
        pageSizes: [10, 30, 50, 100, 200],
        currentPage: 1,
        total: 0,
      },
      pageChild: {
        pageSize: 10,
        pageSizes: [10, 30, 50, 100, 200],
        currentPage: 1,
        total: 0,
      },
      dataParent: [],
      dataChild: [],
      optionParent: optionParent,
      // selectionListChild: [],
      optionChild: optionChild1,
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.dict_add, false),
        delBtn: this.vaildData(this.permission.dict_delete, false),
        editBtn: this.vaildData(this.permission.dict_edit, false),
        viewBtn: false,
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
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      getDictTree().then((res) => {
        const column = this.findObject(this.optionChild.column, "parentId");
        column.dicData = res.data.data;
      });
    },
    handleAdd(row) {
      this.formChild.dictValue = "";
      this.formChild.dictKey = "";
      this.formChild.sort = 0;
      this.formChild.isSealed = 0;
      this.formChild.remark = "";
      this.formChild.parentId = row.id;
      this.$refs.crudChild.rowAdd();
    },
    rowSave(row, done, loading) {
      const form = {
        ...row,
        dictKey: -1,
      };
      add(form).then(
        () => {
          this.onLoadParent(this.pageParent);
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
    rowUpdate(row, index, done, loading) {
      update(row).then(
        () => {
          this.onLoadParent(this.pageParent);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          this.onLoadChild(this.pageChild);
          done();
        },
        (error) => {
          window.console.log(error);
          loading();
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
          this.onLoadParent(this.pageParent);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        });
    },
    handleRowClick(row, type) {
      this.type = type;
      this.optionChild = type == "view" ? optionChild2 : optionChild1;
      this.query = {};
      this.parentId = row.id;
      this.dictValue = row.dictValue;
      const code = this.findObject(this.optionChild.column, "code");
      code.value = row.code;
      const parentId = this.findObject(this.optionChild.column, "parentId");
      parentId.value = row.id;
      this.onLoadChild(this.pageChild);
      this.$nextTick(() => {
        this.box = true;
      });
    },
    searchReset() {
      this.query = {};
      this.onLoadParent(this.pageParent);
    },
    searchChange(params, done) {
      this.query = params;
      this.pageParent.currentPage = 1;
      this.onLoadParent(this.pageParent, params);
      done();
    },
    selectionChange(list) {
      this.selectionList = list;
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
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
          this.onLoadParent(this.pageParent);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          this.$refs.crud.toggleSelection();
        });
    },
    beforeOpen(done, type) {
      if (["edit", "view"].includes(type)) {
        getDict(this.formParent.id).then((res) => {
          this.formParent = res.data.data;
        });
      }
      done();
    },
    currentChange(currentPage) {
      this.pageParent.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.pageParent.pageSize = pageSize;
    },
    refreshChange() {
      this.onLoadParent(this.pageParent, this.query);
    },
    rowSaveChild(row, done, loading) {
      add(row).then(
        () => {
          this.onLoadChild(this.pageChild);
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
    rowUpdateChild(row, index, done, loading) {
      update(row).then(
        () => {
          this.onLoadChild(this.pageChild);
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
    rowDelChild(row) {
      this.$confirm("确定将选择数据删除?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return remove(row.id);
        })
        .then(() => {
          this.onLoadChild(this.pageChild);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        });
    },
    searchResetChild() {
      this.query = {};
      this.onLoadChild(this.pageChild);
    },
    searchChangeChild(params, done) {
      this.query = params;
      this.pageChild.currentPage = 1;
      this.onLoadChild(this.pageChild, params);
      done();
    },
    selectionChangeChild(list) {
      this.selectionList = list;
    },
    selectionClearChild() {
      this.selectionList = [];
      this.$refs.crudChild.toggleSelection();
    },
    handleDeleteChild() {
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
          this.onLoadChild(this.pageChild);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          this.$refs.crudChild.toggleSelection();
        });
    },
    beforeOpenChild(done, type) {
      if (["add", "edit"].includes(type)) {
        this.initData();
      }
      if (["edit", "view"].includes(type)) {
        getDict(this.formChild.id).then((res) => {
          this.formChild = res.data.data;
        });
      }
      done();
    },
    beforeCloseChild(done) {
      this.$refs.crudChild.value.parentId = this.parentId;
      this.$refs.crudChild.option.column.filter((item) => {
        if (item.prop === "parentId") {
          item.value = this.parentId;
        }
      });
      done();
    },
    currentChangeChild(currentPage) {
      this.pageChild.currentPage = currentPage;
    },
    sizeChangeChild(pageSize) {
      this.pageChild.pageSize = pageSize;
    },
    refreshChangeChild() {
      this.onLoadChild(this.pageChild, this.query);
    },
    onLoadParent(page, params = {}) {
      this.loading = true;
      getParentList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.pageParent.total = data.total;
        this.dataParent = data.records;
        this.loading = false;
        this.selectionClear();
      });
    },
    onLoadChild(page, params = {}) {
      this.loadingChild = true;
      getChildList(
        page.currentPage,
        page.pageSize,
        this.parentId,
        Object.assign(params, this.query)
      ).then((res) => {
        this.dataChild = res.data.data;
        this.loadingChild = false;
        this.selectionClear();
      });
    },
  },
};
</script>

