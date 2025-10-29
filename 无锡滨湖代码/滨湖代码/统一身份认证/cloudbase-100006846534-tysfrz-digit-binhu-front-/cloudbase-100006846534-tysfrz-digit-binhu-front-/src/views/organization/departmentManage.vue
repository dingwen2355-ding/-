<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
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
      @tree-load="treeLoad"
    >
      <template slot="menuLeft">
        <!-- <el-button
          type="primary"
          size="small"
          icon="el-icon-plus"
          @click="openDialog"
          >同步政务微信</el-button
        > -->
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="$refs.crud.rowAdd()"
          >新建部门</el-button
        >
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
          >删 除
        </el-button>
        <el-button
          type="warning"
          size="small"
          plain
          icon="el-icon-download"
          @click="handleExport"
          >导 出</el-button
        >
      </template>
      <template slot-scope="{ row, index }" slot="menu">
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
        <el-button
          v-if="row.isEnable == 1"
          type="warning"
          plain
          size="mini"
          @click="handleStop(row)"
          >停 用</el-button
        ><el-button
          v-else
          type="primary"
          plain
          size="mini"
          @click="handleStop(row)"
          >启 用</el-button
        >
        <el-button
          type="danger"
          plain
          size="mini"
          @click="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        >
      </template>
      <template slot="isEnableName" slot-scope="scope">
        <span :style="{ color: scope.row.isEnable == 1 ? 'green' : 'red' }">{{
          scope.row.isEnableName
        }}</span>
      </template>
    </avue-crud>
    <el-dialog
      title="同步信息"
      :visible.sync="dialogVisible"
      width="40%"
      append-to-body
      :destroy-on-close="true"
    >
      <div style="height: 500px">
        <el-scrollbar>
          <div>
            <el-tree
              :data="dialogTreeData"
              ref="tree"
              :highlight-current="true"
              :check-on-click-node="true"
              show-checkbox
              node-key="id"
              @check="handleCheckChange"
              :props="{ value: 'id', label: 'title' }"
            >
            </el-tree>
          </div>
        </el-scrollbar>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="handleSave"
          >同 步</el-button
        >
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
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
  getBhDeptTreeSync,
  syn,
  syncDept,
  getList2,
} from "@/api/organization/department";
import { getBhDeptTree } from "@/api/organization/user";
import { getToken } from "@/util/auth";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      formTree: {},
      form: {},
      query: {},
      loading: true,
      parentId: "1",
      selectionList: [],
      page: {
        pageSize: 20,
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
        addBtn: false,
        viewBtn: false,
        editBtn: false,
        delBtn: false,
        selection: true,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        stripe: true,
        // defaultExpandAll: true,
        // sortable: true, // 拖拽排序
        rowKey: "id",
        // rowParentKey: "parentId",
        menuWidth: 320,
        lazy: true,
        tree: true,
        column: [
          {
            label: "部门名称",
            prop: "name",
            search: true,
            display: false,
          },
          {
            label: "状态",
            prop: "isEnableName",
            display: false,
            align: "center",
          },
          {
            label: "状态",
            prop: "isEnable",
            display: false,
            hide: true,
            search: true,
            type: "radio",

            dicData: [
              {
                label: "启用",
                value: 1,
              },
              {
                label: "停用",
                value: 0,
              },
            ],
          },
          {
            label: "类型",
            prop: "type",
            search: true,
            display: false,
            align: "center",
            type: "select",
            dataType: "number",
            dicUrl: "/api/blade-system/dict/dictionary?code=dept_type",
            props: {
              label: "dictValue",
              value: "dictKey",
            },
          },
          {
            label: "部门描述",
            prop: "description",
            display: false,
            overHidden: true,
            align: "center",
          },
          {
            label: "更新时间",
            prop: "updateTime",
            display: false,
            align: "center",
          },
          // {
          //   label: "排序",
          //   prop: "sort",
          //   display: false,
          //   align: "center",
          // },
        ],
        group: [
          {
            label: "基 础 信 息",
            prop: "jcxx",
            column: [
              {
                label: "部门名称",
                prop: "name",
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请输入部门名称",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "上级部门",
                prop: "parentId",
                type: "tree",
                dicData: [],
                span: 24,
                props: {
                  label: "title",
                  value: "id",
                },
                rules: [
                  {
                    required: false,
                    message: "请选择上级部门",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "部门类型",
                prop: "type",
                type: "select",
                span: 24,
                dataType: "number",
                dicUrl: "/api/blade-system/dict/dictionary?code=dept_type",
                props: {
                  label: "dictValue",
                  value: "dictKey",
                },
              },
              {
                label: "成员上限",
                prop: "partyUserLimit",
                span: 24,
                blur: ({ value, column }) => {
                  if (!/(^[1-9]\d*$)/.test(value)) {
                    this.$set(this.form, "partyUserLimit", 0);
                  }
                },
              },
              {
                label: "状态",
                prop: "isEnable",
                type: "radio",
                span: 24,
                value: 1,
                dicData: [
                  {
                    label: "启用",
                    value: 1,
                  },
                  {
                    label: "停用",
                    value: 0,
                  },
                ],
              },
            ],
          },
          {
            label: "详 细 信 息",
            prop: "xxxx",
            column: [
              {
                label: "部门详情",
                prop: "description",
                type: "textarea",
                span: 24,
                maxlength: 100,
                minRows: 3,
                showWordLimit: true,
              },
              {
                label: "单位全称",
                prop: "fullName",
                span: 24,
              },
              {
                label: "单位简称",
                prop: "shortName",
                span: 24,
              },
              {
                label: "单位简介",
                prop: "briefIntroduction",
                span: 24,
              },
              {
                label: "单位网站",
                prop: "domainName",
                span: 24,
              },
              {
                label: "单位地址",
                prop: "address",
                span: 24,
              },
              {
                label: "单位电话",
                prop: "telephone",
                span: 24,
              },
              {
                label: "信用代码",
                prop: "organizationCode",
                span: 24,
              },
            ],
          },
        ],
      },
      data: [],
      dialogVisible: false,
      dialogTreeData: [],
      checkTreeData: [],
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.bhdept_add, false),
        viewBtn: this.vaildData(this.permission.bhdept_view, false),
        delBtn: this.vaildData(this.permission.bhdept_delete, false),
        editBtn: this.vaildData(this.permission.bhdept_edit, false),
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
    getBhDeptTree().then((res) => {
      var column = this.findObject(this.option.group[0].column, "parentId");
      column.dicData = res.data.data;
    });
  },
  methods: {
    handleCheckChange(data, checked, indeterminate) {
      this.checkTreeData = this.$refs.tree.getCheckedNodes(false, true);
    },
    async handleSave() {
      let checkTreeData = this.checkTreeData.map((item) => {
        return item.id;
      });
      checkTreeData = checkTreeData.toString();
      await syncDept();
      syn(checkTreeData).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
          this.dialogVisible = false;
        }
      });
    },
    openDialog() {
      getBhDeptTreeSync().then((res) => {
        this.dialogTreeData = res.data.data;
      });
      this.$nextTick(() => {
        this.dialogVisible = true;
      });
    },
    handleStop(row) {
      changeEnable(row.id).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.$refs.crud.refreshTable();
          this.onLoad(this.page, this.query);
        }
      });
    },
    handleExport() {
      this.$confirm("是否导出表格数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let isEnable =
          this.query.isEnable == undefined ? "" : this.query.isEnable;
        let name = this.query.name == undefined ? "" : this.query.name;
        let type = this.query.type == undefined ? "" : this.query.type;
        let str = `api/squirrel-szbh/bhdept/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&isEnable=${isEnable}&name=${name}&type=${type}&ids=${
          this.ids
        }`;
        window.open(str);
      });
    },
    rowSave(row, done, loading) {
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
          this.$refs.crud.refreshTable();
          this.$refs.crud.toggleSelection();
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
    rowDel(row, index, done) {
      this.$confirm("确定将选择数据删除?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return remove(row.id);
        })
        .then(() => {
          // this.$refs.crud.refreshTable();
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          // 数据回调进行刷新
          done(row);
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
          // 刷新表格数据并重载
          // this.data = [];
          this.parentId = "1";
          this.$refs.crud.refreshTable();
          this.$refs.crud.toggleSelection();
          // 表格数据重载
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
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
      this.parentId = "1";
      // this.$refs.crud.refreshTable();
      this.$refs.crud.toggleSelection();
      this.onLoad(this.page);
    },
    searchChange(params, done) {
      this.query = params;
      this.page.currentPage = 1;
      this.data = [];
      this.parentId = "";
      // this.$refs.crud.refreshTable();
      this.$refs.crud.toggleSelection();
      // this.onLoad(this.page, params);
      this.onLoad2(this.page, params);
      done();
    },
    selectionChange(list) {
      this.selectionList = list;
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.refreshTable();
      this.$refs.crud.toggleSelection();
    },
    refreshChange() {
      this.onLoad2(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      getList(Object.assign(params, this.query), this.parentId).then((res) => {
        this.data = res.data.data;
        this.loading = false;
        this.selectionClear();
        this.$nextTick(() => {
          this.$refs.crud.doLayout();
        });
      });
    },
    onLoad2(page, params = {}) {
      this.loading = true;
      getList(Object.assign(params, this.query), this.parentId).then((res) => {
        this.data = res.data.data;
        this.loading = false;
        this.selectionClear();
        this.$nextTick(() => {
          this.$refs.crud.doLayout();
        });
      });
    },
    treeLoad(tree, treeNode, resolve) {
      // console.log(tree, treeNode, resolve);
      const parentId = tree.id;
      getList({ ...this.query }, parentId).then((res) => {
        resolve(res.data.data);
      });
    },
  },
};
</script>

<style lang='scss'>
// 按原型图修改表单分组标题的样式
.el-dialog__body .avue-form .el-form .avue-group .avue-group__header {
  border-left: 4px solid #004fd8;
  height: 18px;
  .avue-group__title {
    padding-left: 10px;
  }
}
.avue-group .el-collapse-item__header {
  border: none;
}
</style>
