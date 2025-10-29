<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      :before-open="beforeOpen"
      v-model="form"
      ref="crud"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
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
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
          >删 除
        </el-button>
      </template>
      <template slot-scope="{ row, index }" slot="menu">
        <el-button
          type="primary"
          plain
          size="mini"
          @click="$refs.crud.rowEdit(row, index)"
          >编 辑</el-button
        >
        <el-button type="primary" plain size="mini" @click="handleAddUser(row)"
          >添加用户</el-button
        >
        <el-button type="success" plain size="mini" @click="openViewDialog(row)"
          >详 情</el-button
        >
        <el-button
          type="danger"
          plain
          size="mini"
          @click="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        >
      </template>
    </avue-crud>
    <el-dialog
      title="详情"
      :visible.sync="dialogVisible"
      width="40%"
      append-to-body
    >
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="用户组信息" name="1">
          <el-form ref="form" :model="formDialog" label-width="120px">
            <el-form-item
              label="用户组名称:"
              prop="name"
              :rules="{
                required: true,
                message: '请输入用户组名称',
                trigger: 'blur',
              }"
            >
              <el-input
                v-model="formDialog.name"
                size="small"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="备注:">
              <el-input
                type="textarea"
                v-model="formDialog.remark"
                maxlength="100"
                show-word-limit
                :rows="5"
                size="small"
                disabled
              ></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="用户列表" name="2">
          <div style="display: flex">
            <el-input
              placeholder="请输入 姓名"
              v-model="searchInput"
              size="small"
              style="margin-right: 30px"
              clearable
              @input="handleResetIpt"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="handleClick"
              ></el-button>
            </el-input>
            <!-- <el-button type="primary" size="mini">添加</el-button> -->
            <el-button type="danger" size="mini" @click="handleRemove"
              >移 除</el-button
            >
          </div>
          <div class="dialog-table">
            <avue-crud
              :option="option2"
              :table-loading="loading"
              :data="tableData"
              :page.sync="page2"
              ref="crud2"
              @selection-change="selectionChange2"
              @current-change="currentChange2"
              @size-change="sizeChange2"
            >
              <template slot-scope="{ row, index }" slot="menu">
                <el-button
                  type="danger"
                  plain
                  size="mini"
                  @click="handleRemoveOne(row)"
                  >移除用户</el-button
                >
              </template>
            </avue-crud>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <!-- 添加用户 -->
    <el-dialog
      title="添加用户"
      :visible.sync="dialogVisible2"
      width="50%"
      append-to-body
      :destroy-on-close="true"
      :before-close="beforeClose"
    >
      <div style="height: 500px; display: inline-block; width: 46%">
        <el-scrollbar>
          <el-input
            placeholder="输入部门关键字进行过滤"
            v-model="filterText"
            size="small"
          >
          </el-input>
          <el-tree
            :data="dialogTreeData"
            ref="deptTree"
            default-expand-all
            :highlight-current="true"
            :check-on-click-node="true"
            show-checkbox
            node-key="id"
            @check="handleCheckChange"
            :props="{ value: 'id', label: 'title' }"
            :filter-node-method="filterNode"
          >
          </el-tree>
        </el-scrollbar>
      </div>
      <div
        v-loading="userLoading"
        class="user-con"
        v-if="checkTreeData.length > 0"
      >
        <el-scrollbar>
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
            >全选</el-checkbox
          >
          <div style="margin: 15px 0"></div>
          <el-checkbox-group
            v-model="checkedUsers"
            @change="handleCheckedUserChange"
          >
            <el-checkbox
              v-for="item in userList"
              :label="item.id"
              :key="item.id"
              >{{ item.name }} / {{ item.majorPosition }}</el-checkbox
            >
          </el-checkbox-group>
        </el-scrollbar>
      </div>

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSave" size="small"
          >添 加</el-button
        >
        <el-button @click="beforeClose" size="small">取 消</el-button>
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
  getGroupUserPage,
  removeUser,
  submit,
  getUserList,
} from "@/api/organization/userGroup";
import { getUser, getBhDeptTree } from "@/api/organization/user";
import { mapGetters } from "vuex";
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
      selectionList: [],
      option: {
        height: "auto",
        calcHeight: 0,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        searchLabelWidth: 86,
        border: true,
        index: true,
        viewBtn: false,
        editBtn: false,
        delBtn: false,
        selection: true,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        align: "center",
        stripe: true,
        labelWidth: 120,
        menuWidth: 350,
        column: [
          {
            label: "用户组名称",
            prop: "name",
            search: true,
            span: 24,
            rules: [
              {
                required: true,
                message: "请输入用户组名称",
                trigger: "blur",
              },
            ],
          },
          {
            label: "备注",
            prop: "remark",
            span: 24,
            type: "textarea",
            maxlength: 100,
            showWordLimit: true,
          },
          {
            label: "创建时间",
            prop: "createTime",
            display: false,
          },
        ],
      },
      data: [],
      dialogVisible: false,
      activeName: "1",
      formDialog: {},
      searchInput: "",
      page2: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      tableData: [],
      selectionList2: [],
      option2: {
        height: "auto",
        calcHeight: 350,
        tip: false,
        searchShow: false,
        border: true,
        index: true,
        viewBtn: false,
        editBtn: false,
        delBtn: false,
        selection: true,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        stripe: true,
        column: [
          {
            label: "姓名",
            prop: "userName",
          },
          {
            label: "登录账号",
            prop: "userAccount",
          },
        ],
      },
      dialogVisible2: false,
      dialogTreeData: [],
      filterText: "",
      checkTreeData: [],
      checkAll: false,
      checkedUsers: [], // 保存被选中的人员
      userList: [], // 保存人员数据
      isIndeterminate: true,
      userLoading: true,
      tableId: "", // 表格id
      manageId: "", // 详情id
    };
  },
  watch: {
    filterText(val) {
      this.$refs.deptTree.filter(val);
    },
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permission.manageusergroup_add, false),
        viewBtn: this.vaildData(this.permission.manageusergroup_view, false),
        delBtn: this.vaildData(this.permission.manageusergroup_delete, false),
        editBtn: this.vaildData(this.permission.manageusergroup_edit, false),
      };
    },
    ids() {
      let ids = [];
      this.selectionList.forEach((ele) => {
        ids.push(ele.id);
      });
      return ids.join(",");
    },
    ids2() {
      let ids = [];
      this.selectionList2.forEach((ele) => {
        ids.push(ele.userId);
      });
      return ids.join(",");
    },
  },
  methods: {
    handleResetIpt() {
      if (this.searchInput == "") {
        this.handleClick();
      }
    },
    handleExport() {
      this.$confirm("是否导出表格数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let name = this.query.name == undefined ? "" : this.query.name;
        let str = `api/szbh-back/manageusergroup/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&name=${name}`;
        window.open(str);
      });
    },
    async handleSave() {
      let checkedUsers = {
        userIds: this.checkedUsers.toString(),
        bizId: this.tableId,
      };
      submit(checkedUsers).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
          this.beforeClose();
        }
      });
    },
    beforeClose() {
      this.checkAll = false;
      this.userList = [];
      this.checkedUsers = [];
      this.checkTreeData = [];
      this.dialogVisible2 = false;
    },
    filterNode(value, data, node) {
      if (!value) {
        node.expanded = false;
        return true;
      }
      return this.checkBelongToChooseNode(value, data, node);
    },
    //过滤时不过滤子节点
    checkBelongToChooseNode(value, data, node) {
      if (data.title.indexOf(value) !== -1) {
        return true;
      }
      const level = node.level;
      if (level === 1) {
        return false;
      }
      let parentData = node.parent;
      let index = 0;
      while (index < level - 1) {
        if (parentData.data.title.indexOf(value) != -1) {
          return true;
        }
        parentData = parentData.parent;
        index++;
      }
      return false;
    },
    handleCheckAllChange(val) {
      if (val) {
        this.checkedUsers = this.userList.map((item) => {
          return item.id; // 这个保存接口取的是人员的数据id，别的取的都是userid
        });
      } else {
        this.checkedUsers = [];
      }
      this.isIndeterminate = false;
    },
    handleCheckedUserChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.userList.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.userList.length;
    },
    handleCheckChange(data, checked, indeterminate) {
      // console.log(data, checked, indeterminate);
      this.checkTreeData = this.$refs.deptTree.getCheckedNodes();
      let checkTreeData = this.checkTreeData.map((item) => {
        return item.id;
      });
      checkTreeData = checkTreeData.toString();
      this.userLoading = true;
      getUser(checkTreeData).then((res) => {
        this.userList = res.data.data;
        this.userLoading = false;
      });
      getUserList(this.tableId).then((res) => {
        this.checkedUsers = res.data.data.map((item) => {
          return item.userId;
        });
      });
    },
    openViewDialog(row) {
      this.activeName = "1";
      this.searchInput = "";
      this.handleResetIpt();
      this.formDialog = row;
      this.dialogVisible = true;
      this.manageId = row.id;
    },
    handleClick() {
      if (this.activeName == 2) {
        this.loading = true;
        getGroupUserPage(
          this.page2.currentPage,
          this.page2.pageSize,
          this.formDialog.id,
          { userName: this.searchInput }
        ).then((res) => {
          const data = res.data.data;
          this.page2.total = data.total;
          this.tableData = data.records;
          this.loading = false;
          this.selectionClear();
        });
      }
    },
    handleRemove() {
      if (this.selectionList2.length === 0) {
        this.$message.warning("请选择至少一条数据");
        return;
      }
      removeUser(this.ids2, this.manageId).then(() => {
        this.handleClick();
        this.$message({
          type: "success",
          message: "操作成功!",
        });
      });
    },
    handleRemoveOne(row) {
      removeUser(row.userId, this.manageId).then(() => {
        this.handleClick();
        this.$message({
          type: "success",
          message: "操作成功!",
        });
      });
    },
    handleAddUser(row) {
      this.tableId = row.id;
      getBhDeptTree().then((res) => {
        this.dialogTreeData = res.data.data;
      });
      this.$nextTick(() => {
        this.dialogVisible2 = true;
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
    selectionChange2(list) {
      this.selectionList2 = list;
    },
    selectionClear2() {
      this.selectionList2 = [];
      this.$refs.crud2.toggleSelection();
    },
    currentChange2(currentPage) {
      this.page2.currentPage = currentPage;
      this.handleClick();
    },
    sizeChange2(pageSize) {
      this.page2.pageSize = pageSize;
      this.page2.currentPage = 1;
      this.handleClick();
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
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
      getList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
        this.selectionClear();
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog-table {
  margin-top: 10px;
  /deep/.avue-crud__menu {
    display: none;
  }
}
.user-con {
  height: 500px;
  display: inline-block;
  width: 50%;
  margin-left: 20px;
  /deep/.el-checkbox-group .el-checkbox {
    display: block;
    margin: 5px 0;
  }
}
</style>
