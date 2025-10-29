<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
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
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="openDialog({}, 1)"
          >新 增</el-button
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
        <el-button type="primary" plain size="mini" @click="openDialog(row, 2)"
          >编 辑</el-button
        >
        <el-button type="success" plain size="mini" @click="openDialog(row, 3)"
          >详 情</el-button
        >
        <el-button type="success" plain size="mini" @click="handleGo(row)"
          >同步记录</el-button
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
    <!-- 同步记录 -->
    <el-dialog
      title="同步记录"
      :visible.sync="dialogVisible"
      width="40%"
      append-to-body
      :destroy-on-close="true"
      class="records-dialog"
    >
      <avue-crud
        :option="option2"
        :table-loading="loading2"
        :data="data2"
        :page.sync="page2"
        ref="crud2"
        @search-change="searchChange2"
        @search-reset="searchReset2"
        @current-change="currentChange2"
        @size-change="sizeChange2"
      ></avue-crud>
    </el-dialog>
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible2"
      width="43%"
      append-to-body
      :before-close="beforeClose"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="应用:" prop="appId">
          <el-select
            v-model="form.appId"
            placeholder="请选择 应用"
            size="small"
            :disabled="title == '详 情' || title == '编 辑'"
          >
            <el-option
              v-for="item in appList"
              :label="item.name"
              :value="item.id"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="同步数据:" prop="dataType">
          <el-select
            v-model="form.dataType"
            placeholder="请选择 同步数据"
            size="small"
            :disabled="title == '详 情' || title == '编 辑'"
            @change="handleChangeDatatype"
          >
            <el-option label="部门信息" value="部门信息"></el-option>
            <el-option label="人员信息" value="人员信息"></el-option>
          </el-select>
        </el-form-item>

        <div>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="数据权限" name="1">
              <div v-if="form.dataType">
                <div class="dept-con">
                  <el-tree
                    :key="randomKey"
                    default-expand-all
                    :data="dialogTreeData"
                    ref="tree"
                    :highlight-current="true"
                    :check-on-click-node="true"
                    show-checkbox
                    node-key="id"
                    @check="handleCheckChange"
                    :props="{ value: 'id', label: 'title' }"
                    :disabled="title == '详 情'"
                    :default-checked-keys="defaultChecked"
                  >
                  </el-tree>
                </div>
                <!-- <div
                  v-loading="userLoading"
                  class="user-con"
                  v-if="checkTreeData.length > 0 && form.dataType == '人员信息'"
                >
                  <el-checkbox
                    :disabled="title == '详 情'"
                    :indeterminate="isIndeterminate"
                    v-model="checkAll"
                    @change="handleCheckAllChange"
                    >全选</el-checkbox
                  >
                  <div style="margin: 15px 0"></div>
                  <el-checkbox-group
                    :disabled="title == '详 情'"
                    v-model="checkedUsers"
                    @change="handleCheckedUserChange"
                  >
                    <el-checkbox
                      v-for="item in userList"
                      :label="item.account"
                      :key="item.account"
                      >{{ item.name }} /
                      {{ item.majorPosition }}
                    </el-checkbox>
                  </el-checkbox-group>
                </div> -->
              </div>
            </el-tab-pane>
            <el-tab-pane label="字段权限" name="2">
              <div style="height: 400px; overflow: scroll">
                <el-checkbox
                  v-if="form.dataType"
                  :disabled="title == '详 情'"
                  :indeterminate="isIndeterminate2"
                  v-model="checkAll2"
                  @change="handleCheckAllChange2"
                  >全选</el-checkbox
                >
                <el-checkbox-group
                  v-if="form.dataType"
                  v-model="checkList"
                  :disabled="title == '详 情'"
                  style="padding-left: 10px"
                  @change="handleCheckedChange"
                >
                  <el-checkbox
                    style="display: block; margin: 5px 0"
                    v-for="item in zdList"
                    :key="item"
                    :label="item"
                  ></el-checkbox>
                </el-checkbox-group>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer" v-if="title !== '详 情'">
        <el-button size="small" type="primary" @click="handleSave"
          >保 存</el-button
        >
        <el-button size="small" @click="beforeClose">取 消</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
import {
  getDownPage,
  getDownRecordPage,
  submit,
  remove,
  getDetail,
} from "@/api/userSynchronous/userSynchronous";
import {
  getAppCategoryTree,
  getUsers,
  getBhDeptTree,
} from "@/api/organization/user";
import { getAllList } from "@/api/application/management";
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
        calcHeight: 10,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        searchLabelWidth: 72,
        border: true,
        index: true,
        viewBtn: false,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        selection: true,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        menuWidth: 350,
        stripe: true,
        column: [
          {
            label: "应用名称",
            prop: "appName",
            search: true,
          },
          {
            label: "数据类型",
            prop: "dataType",
          },
          {
            label: "创建时间",
            prop: "createTime",
          },
          // {
          //   label: "同步状态",
          //   prop: "result",
          // },
        ],
      },
      data: [],
      dialogVisible: false,
      option2: {
        height: "auto",
        calcHeight: 150,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: false,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        menu: false,
        refreshBtn: false,
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        stripe: true,
        column: [
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
      loading2: true,
      query2: {},
      data2: [],
      page2: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      type: 1,
      title: "",
      dialogVisible2: false,
      dialogType: 1,
      // appDic: [],
      tableId: "",
      activeName: "1",
      checkList: [], // 保存选中的字段权限
      zdList: [],
      dialogTreeData: [],
      checkTreeData: [], // 部门树的选中值
      checkAll: false,
      checkAll2: false, //字段权限
      checkedUsers: [], // 保存被选中的人员
      userList: [], // 保存人员数据
      isIndeterminate: true,
      isIndeterminate2: true, //字段权限
      userLoading: true,
      defaultChecked: [], //保存授权弹窗回显的应用数据
      randomKey: 0, // 强制刷新树
      appList: [],
    };
  },
  computed: {
    ids() {
      let ids = [];
      this.selectionList.forEach((ele) => {
        ids.push(ele.id);
      });
      return ids.join(",");
    },
  },
  methods: {
    beforeClose() {
      this.activeName = "1";
      this.defaultChecked = [];
      this.checkList = [];
      this.dialogVisible2 = false;
    },
    // 字段权限全选
    handleCheckAllChange2(val) {
      if (val) {
        this.checkList = this.zdList.map((item) => {
          return item;
        });
      } else {
        this.checkList = [];
      }
      this.isIndeterminate2 = false;
    },
    handleCheckedChange(value) {
      let checkedCount = value.length;
      this.checkAll2 = checkedCount === this.zdList.length;
      this.isIndeterminate2 =
        checkedCount > 0 && checkedCount < this.zdList.length;
    },
    // 人员全选
    handleCheckedUserChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.userList.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.userList.length;
    },
    handleCheckAllChange(val) {
      if (val) {
        this.checkedUsers = this.userList.map((item) => {
          return item.account;
        });
      } else {
        this.checkedUsers = [];
      }
      this.isIndeterminate = false;
    },
    handleCheckChange(data, checked, indeterminate) {
      this.checkTreeData = this.$refs.tree.getCheckedNodes();
      //console.info(this.checkTreeData);
      // console.log(this.checkTreeData, 33);
      if (this.form.dataType == "人员信息") {
        this.userLoading = true;
        let checkTreeData = this.checkTreeData.map((item) => {
          return item.id;
        });
        checkTreeData = checkTreeData.toString();
        getUsers({ ids: checkTreeData }).then((res) => {
          this.userList = res.data.data;
          this.userLoading = false;
        });
      }
    },
    handleChangeDatatype(val) {
      this.checkTreeData = [];
      this.randomKey = Math.random();
      this.checkList = [];
      if (val == "部门信息") {
        this.zdList = [
          "部门名称",
          "部门类型",
          "成员上限",
          "部门详情",
          "单位全称",
          "单位简称",
          "单位简介",
          "单位网站",
          "单位地址",
          "单位电话",
          "信用代码",
        ];
      } else {
        this.zdList = [
          "用户名称",
          "用户账号",
          "密码",
          "所属主部门",
          "主部门内是否为上级",
          "主部门职位",
          "主部门手机",
          "是否短信通知",
          "是否启用",
          "其他所属部门",
          "其他部门内是否为上级",
          "其他部门职位",
          "性别",
          "用户头像",
          "别名",
          "邮箱",
          "电话",
          "对外简称",
        ];
      }
    },
    handleSave() {
      // if (this.checkTreeData.length == 0 || this.checkList.length == 0) {
      //   this.$message.warning("请完善信息！");
      //   return;
      // }
      if (this.checkTreeData.length == 0) {
        this.$message.warning("请完善信息！");
        return;
      }
      let checkTreeData = this.checkTreeData.map((item) => {
        if (item.id == undefined) {
          return item;
        } else {
          return item.id;
        }
      });
      this.form.syncDept = checkTreeData.toString();
      this.form.syncUser =
        this.checkedUsers.length > 0 ? this.checkedUsers.toString() : "";
      this.form.syncField = this.checkList.toString();
      // console.log(this.form);

      submit(this.form).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
          this.beforeClose();
        }
      });
    },
    handleClick() {
      // this.checkTreeData = [];
      // console.log(this.form, 11);
      if (this.form.dataType == "人员信息") {
        this.zdList = [
          "用户名称",
          "用户账号",
          "密码",
          "所属主部门",
          "主部门内是否为上级",
          "主部门职位",
          "主部门手机",
          "是否短信通知",
          "是否启用",
          "其他所属部门",
          "其他部门内是否为上级",
          "其他部门职位",
          "性别",
          "用户头像",
          "别名",
          "邮箱",
          "电话",
          "对外简称",
        ];
      } else {
        this.zdList = [
          "部门名称",
          "部门类型",
          "成员上限",
          "部门详情",
          "单位全称",
          "单位简称",
          "单位简介",
          "单位网站",
          "单位地址",
          "单位电话",
          "信用代码",
        ];
      }
      if (this.activeName == "2") {
        this.checkAll2 = this.checkList.length === this.zdList.length;
        this.isIndeterminate2 =
          this.checkList.length > 0 &&
          this.checkList.length < this.zdList.length;
      }
    },
    openDialog(row, type) {
      this.dialogType = type;
      this.title = type == 1 ? "新 增" : type == 2 ? "编 辑" : "详 情";
      // 应用的下拉框
      getAllList().then((res) => {
        this.appList = res.data.data;
      });
      getBhDeptTree().then((res) => {
        this.dialogTreeData = res.data.data;
      });
      if (type != 1) {
        getDetail(row.id).then((res) => {
          this.form = res.data.data;
          if (res.data.data.syncDept) {
            this.defaultChecked = res.data.data.syncDept.split(",");
            this.checkTreeData = this.defaultChecked;
            if (this.form.dataType == "人员信息") {
              this.userLoading = true;
              getUsers({ ids: res.data.data.syncDept }).then((res) => {
                this.userList = res.data.data;
                this.checkedUsers = this.form.syncUser
                  ? this.form.syncUser.split(",")
                  : [];
                this.checkAll =
                  this.checkedUsers.length === this.userList.length;
                this.isIndeterminate =
                  this.checkedUsers.length > 0 &&
                  this.checkedUsers.length < this.userList.length;
                this.userLoading = false;
              });
            }
          }
          this.checkList = this.form.syncField
            ? this.form.syncField.split(",")
            : [];
        });
      } else {
        this.defaultChecked = [];
        this.form = {};
      }
      this.$nextTick(() => {
        this.dialogVisible2 = true;
      });
    },
    handleGo(row) {
      this.type = row.dataType;
      this.tableId = row.id;
      this.onLoad2(this.page2, this.query2);
      this.dialogVisible = true;
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
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange2(currentPage) {
      this.page2.currentPage = currentPage;
      this.onLoad2(this.page2);
    },
    sizeChange2(pageSize) {
      this.page2.pageSize = pageSize;
      this.page2.currentPage = 1;
      this.onLoad2(this.page2);
    },
    searchReset2() {
      this.query2 = {};
      this.onLoad2(this.page2);
    },
    searchChange2(params, done) {
      this.query2 = params;
      this.page2.currentPage = 1;
      this.onLoad2(this.page2, params);
      done();
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      getDownPage(
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
    onLoad2(page, params = {}) {
      this.loading2 = true;
      getDownRecordPage(page.currentPage, page.pageSize, this.tableId).then(
        (res) => {
          const data = res.data.data;
          this.page2.total = data.total;
          this.data2 = data.records;
          this.loading2 = false;
        }
      );
    },
  },
};
</script>

<style lang="scss" scoped>
.dept-con {
  height: 400px;
  display: inline-block;
  overflow: scroll;
  width: 49%;
}
.user-con {
  height: 400px;
  display: inline-block;
  margin-left: 16px;
  width: 48%;
  overflow: scroll;
  /deep/.el-checkbox-group .el-checkbox {
    display: block;
    margin: 5px 0;
  }
}
.records-dialog {
  /deep/.avue-crud__menu {
    display: none;
  }
}
</style>
