<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
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
      <template slot="usernameForm">
        <el-input
          v-model="form.username"
          placeholder="请输入 登录账号"
          size="small"
        ></el-input>
        <div style="color: #004fd8">初始密码为Szbh@2024</div>
      </template>
      <template slot="avatarForm">
        <img :src="imageUrl" class="avatar" />
      </template>
      <template slot-scope="{ row, index }" slot="menu">
        <el-button
          type="success"
          plain
          size="mini"
          @click="$refs.crud.rowView(row, index)"
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
  </basic-container>
</template>

<script>
import { getList, getDetail, remove } from "@/api/organization/loginLog";
import { mapGetters } from "vuex";
import { getBhDeptTree, getAppCategoryTree } from "@/api/organization/user";
import { getToken } from "@/util/auth";
import { getAllList } from "@/api/application/management";

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
        stripe: true,
        dialogWidth: "35%",
        column: [
          {
            label: "用户头像",
            prop: "avatar",
            formslot: true,
            hide: true,
            span: 24,
          },
          {
            label: "登录账号",
            prop: "userAccount",
            search: true,
            searchValue: "",
            formslot: true,
            span: 24,
            rules: [
              {
                required: true,
                message: "请输入用户名",
                trigger: "blur",
              },
            ],
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
            searchLabelWidth: 150,
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
            searchLabelWidth: 150,
          },
          {
            label: "姓名",
            prop: "userName",
            span: 24,
            hide: true,
            rules: [
              {
                required: true,
                message: "请输入姓名",
                trigger: "blur",
              },
            ],
          },
          {
            label: "部门",
            prop: "bhDeptName",
            span: 24,
          },
          // {
          //   label: "部门",
          //   prop: "deptIds",
          //   search: true,
          //   display: false,
          //   hide: true,
          //   type: "tree",
          //   dicData: [],
          //   props: {
          //     label: "title",
          //     value: "id",
          //   },
          //   span: 24,
          //   rules: [
          //     {
          //       required: true,
          //       message: "请选择部门",
          //       trigger: "blur",
          //     },
          //   ],
          // },
          {
            label: "登录应用",
            prop: "appName",
          },
          {
            label: "登录应用",
            prop: "appIds",
            search: true,
            hide: true,
            display: false,
            type: "select",
            dicData: [],
            props: {
              label: "name",
              value: "id",
            },
          },
          {
            label: "职务",
            prop: "positionName",
            hide: true,
            span: 24,
          },
          {
            label: "登录Token",
            prop: "token",
            type: "textarea",
            overHidden: true,
            hide: true,
            span: 24,
          },
          {
            label: "登录IP",
            prop: "ip",
            span: 24,
          },
          {
            label: "登录时间",
            prop: "createTime",
            span: 24,
          },
          // {
          //   label: "登录地",
          //   prop: "dld",
          //   span: 24,
          // },
          {
            label: "登录客户端",
            span: 24,
            prop: "dlkhd",
            hide: true,
          },
          // {
          //   label: "设备信息",
          //   prop: "device",
          //   display: false,
          // },
          {
            label: "所属系统",
            prop: "yhtx1",
            hide: true,
            span: 24,
          },
          {
            label: "授权地址",
            prop: "yhtx3",
            hide: true,
            span: 24,
          },
          // {
          //   label: "创建时间",
          //   prop: "createTime",
          //   hide: true,
          //   span: 24,
          // },
        ],
      },
      treeDeptId: "",
      imageUrl: "",
      data: [],
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        // addBtn: this.vaildData(this.permission.loginrecord_add, false),
        // viewBtn: this.vaildData(this.permission.loginrecord_view, false),
        // delBtn: this.vaildData(this.permission.loginrecord_delete, false),
        // editBtn: this.vaildData(this.permission.loginrecord_edit, false),
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
    // console.log(this.$route.query);
    getBhDeptTree().then((res) => {
      var column = this.findObject(this.option.column, "deptIds");
      column.dicData = res.data.data;
    });
    getAllList().then((res) => {
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
        let userAccount =
          this.query.userAccount == undefined ? "" : this.query.userAccount;
        let start = this.query.start == undefined ? "" : this.query.start;
        let end = this.query.end == undefined ? "" : this.query.end;
        let deptIds = this.query.deptIds == undefined ? "" : this.query.deptIds;
        let appIds = this.query.appIds == undefined ? "" : this.query.appIds;
        let str = `api/szbh-back/appuserecord/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&userAccount=${userAccount}&start=${start}&end=${end}&deptIds=${deptIds}&appIds=${appIds}`;
        window.open(str);
      });
    },
    nodeClick(data) {
      this.treeDeptId = data.id;
      this.page.currentPage = 1;
      this.onLoad(this.page);
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
        this.imageUrl = this.form.avatar;
        // getDetail(this.form.id).then((res) => {
        //   this.form = res.data.data;
        // });
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
      const loginAccount = window.localStorage.getItem("loginLogAccount");
      if (loginAccount) {
        const column = this.findObject(this.option.column, "userAccount");
        column.searchValue = loginAccount;
        this.query.userAccount = loginAccount;
        console.log(this.option.column);
      }
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
  beforeDestroy() {
    window.localStorage.removeItem("loginLogAccount");
  },
};
</script>

<style>
</style>
