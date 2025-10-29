<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      ref="crud"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @selection-change="selectionChange"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
    >
      <template slot="menuLeft">
        <!-- <el-button
          type="primary"
          size="small"
          icon="el-icon-plus"
          @click="handleAdd()"
        >
          新 增
        </el-button> -->
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
        >
          删 除
        </el-button>
      </template>
      <template slot-scope="{ row, index }" slot="menu">
        <el-button type="primary" plain size="mini" @click="handleAdd(row.id)"
          >编 辑</el-button
        >
        <el-button
          type="success"
          plain
          size="mini"
          @click="handleAdd(row.id, 'view')"
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
    <appdialog
      v-if="dialogVisible"
      :activeName="activeName"
      :detailId="detailId"
      :dialogType="dialogType"
      :dialogVisible.sync="dialogVisible"
      @success="onLoad()"
    ></appdialog>
  </basic-container>
</template>

<script>
import { getList, remove } from "@/api/empower/empower";
import appdialog from "./appdialog.vue";
export default {
  components: {
    appdialog,
  },
  data() {
    return {
      dialogVisible: false,
      detailId: "",
      dialogType: "",
      activeName: "3",
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
        border: true,
        index: true,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        stripe: true,
        selection: true,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        searchLabelWidth: 72,
        menuWidth: 260,
        column: [],
      },
      data: [],
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
  mounted() {
    this.onLoad();
  },
  methods: {
    handleAdd(id, type) {
      if (id) {
        this.detailId = id;
      } else {
        this.detailId = "";
      }
      this.dialogType = type;
      this.dialogVisible = true;
    },
    getData() {
      this.page = {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      };
      this.$refs.crud.searchReset();
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
          this.data = [];
          this.parentId = 0;
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
    onLoad() {
      this.loading = true;
      if (this.activeName == 1) {
        this.option.column = [
          {
            label: "应用名称",
            prop: "appNames",
            search: true,
          },
          {
            label: "应用数量",
            prop: "appSum",
          },
          {
            label: "组织名称",
            prop: "deptNames",
            search: true,
          },
          {
            label: "组织数量",
            prop: "deptSums",
          },
          {
            label: "创建时间",
            prop: "createTime",
          },
        ];
      } else if (this.activeName == 2) {
        this.option.column = [
          {
            label: "应用名称",
            prop: "appNames",
            search: true,
          },
          {
            label: "应用数量",
            prop: "appSum",
          },
          {
            label: "用户组名称",
            prop: "deptNames",
            search: true,
            searchLabelWidth: 90,
          },
          {
            label: "用户组数量",
            prop: "deptSums",
          },
          {
            label: "创建时间",
            prop: "createTime",
          },
        ];
      } else if (this.activeName == 3) {
        this.option.column = [
          {
            label: "应用名称",
            prop: "appNames",
            search: true,
          },
          {
            label: "应用数量",
            prop: "appSum",
          },
          {
            label: "用户名称",
            prop: "deptNames",
            search: true,
          },
          {
            label: "用户数量",
            prop: "deptSums",
          },
          {
            label: "创建时间",
            prop: "createTime",
          },
        ];
      }
      getList(
        this.page.currentPage,
        this.page.pageSize,
        // this.activeName,
        4,
        this.query
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
        this.selectionClear();
      });
    },
    selectionChange(list) {
      this.selectionList = list;
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },
    searchReset() {
      this.query = {};
      this.onLoad();
    },
    searchChange(params, done) {
      this.query = params;
      this.page.currentPage = 1;
      this.onLoad();
      done();
    },
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
      this.onLoad();
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
      this.page.currentPage = 1;
      this.onLoad();
    },
    refreshChange() {
      this.onLoad();
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.avue-view {
  height: 100% !important;
  /deep/.basic-container__card {
    height: 100%;
  }
}
</style>
