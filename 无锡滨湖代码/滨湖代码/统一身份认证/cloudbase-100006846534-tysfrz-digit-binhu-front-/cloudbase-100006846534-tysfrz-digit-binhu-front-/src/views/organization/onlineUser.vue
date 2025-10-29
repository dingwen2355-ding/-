<template>
  <el-row>
    <el-col :span="5">
      <basic-container>
        <div class="box" :style="{ height: height }" :key="randomKey1">
          <div class="treeBtn">
            <!-- <el-link v-if="isOpen" :underline="false" @click="openAll(true)">
              <i style="font-size: 20px" class="el-icon-caret-bottom"></i>
            </el-link>
            <el-link v-else :underline="false" @click="openAll(false)">
              <i class="el-icon-caret-top" style="font-size: 20px"></i>
            </el-link> -->
            <el-button
              v-if="isOpen"
              type="primary"
              icon="el-icon-download"
              size="mini"
              @click="openAll(true)"
            ></el-button>
            <el-button
              v-else
              type="primary"
              icon="el-icon-upload2"
              size="mini"
              @click="openAll(false)"
            ></el-button>
          </div>
          <el-scrollbar>
            <avue-tree
              :option="treeOption"
              :data="treeData"
              @node-click="nodeClick"
            />
          </el-scrollbar>
        </div>
      </basic-container>
    </el-col>
    <el-col :span="19">
      <basic-container ref="container">
        <avue-crud
          :option="option"
          :table-loading="loading"
          :data="data"
          :page.sync="page"
          ref="crud"
          @search-change="searchChange"
          @search-reset="searchReset"
          @current-change="currentChange"
          @size-change="sizeChange"
          @refresh-change="refreshChange"
          @on-load="onLoad"
        >
          <template slot="onLineSum" slot-scope="scope">
            <el-button
              type="primary"
              plain
              size="mini"
              @click="openDialog(scope.row)"
              >{{ scope.row.onLineSum }}个服务</el-button
            >
          </template>
          <template slot-scope="{ row, index }" slot="menu">
            <el-button type="success" plain size="mini" @click="openDialog(row)"
              >详 情</el-button
            >
            <el-button
              type="primary"
              plain
              size="mini"
              @click="handleOffline(row)"
              >踢下线</el-button
            >
            <el-button type="danger" size="mini" plain @click="handleZX(row)"
              >强制注销
            </el-button>
          </template>
        </avue-crud>

        <el-dialog
          title="详情"
          :visible.sync="dialogVisible"
          width="36%"
          append-to-body
        >
          <div class="title">在线列表</div>
          <!-- <div style="display: flex">
            <el-input
              placeholder="请输入内容"
              v-model="searchInput"
              size="small"
              style="margin-right: 50px"
              clearable
              @input="handleGetOnline"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="handleGetOnline"
              ></el-button>
            </el-input>
            <el-button type="info" size="small" @click="handleOffline({})"
              >踢下线</el-button
            >
          </div> -->
          <div class="dialog-table">
            <avue-crud
              :option="option2"
              :table-loading="loading2"
              :data="tableData"
              :page.sync="page2"
              ref="crud2"
              @current-change="currentChange2"
              @size-change="sizeChange2"
            >
              <template slot-scope="{ row, index }" slot="menu">
                <el-button
                  type="info"
                  plain
                  size="mini"
                  @click="handleOffline(row)"
                  >踢下线</el-button
                >
              </template>
            </avue-crud>
          </div>
          <!-- <span slot="footer" class="dialog-footer">
            <el-button size="small" type="primary" @click="handleSaveAuto"
              >保 存</el-button
            >
            <el-button size="small" @click="dialogVisible3 = false"
              >取 消</el-button
            >
          </span> -->
        </el-dialog>
      </basic-container>
    </el-col>
  </el-row>
</template>

<script>
import { getList, getDetail, getDatAPage } from "@/api/organization/onlineUser";
import { changeEnable } from "@/api/organization/user";
import { getBhDeptTree, changeOnline } from "@/api/organization/user";

export default {
  data() {
    return {
      height: "",
      treeData: [],
      treeOption: {
        nodeKey: "id",
        addBtn: false,
        menu: false,
        size: "small",
        defaultExpandAll: false,
        props: {
          labelText: "标题",
          label: "title",
          value: "id",
          children: "children",
        },
      },
      dialogVisible: false,
      query: {},
      loading: false,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      selectionList: [],
      option: {
        height: "auto",
        calcHeight: 60,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        // searchSpan: 8,
        border: true,
        index: true,
        addBtn: false,
        delBtn: false,
        viewBtn: false,
        editBtn: false,
        selection: true,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        align: "center",
        labelWidth: 120,
        menuWidth: 290,
        searchLabelWidth: 72,
        stripe: true,
        column: [
          { label: "人员姓名", prop: "userName", search: true },
          { label: "账号信息", prop: "userAccount", search: true },
          { label: "登录数量", prop: "onLineSum", slot: true },
        ],
      },
      data: [],
      searchInput: "",
      page2: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      tableData: [],
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
        menu: false,
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        stripe: true,
        column: [
          {
            label: "Token凭证",
            prop: "token",
            overHidden: true,
          },
          {
            label: "登录设备",
            prop: "device",
          },
          {
            label: "登录ip",
            prop: "ip",
          },
        ],
      },
      treeDeptId: "",
      loading2: false,
      userAccount: "",
      isOpen: true,
    };
  },
  computed: {
    ids() {
      let ids = [];
      this.selectionList.forEach((ele) => {
        ids.push(ele.userId);
      });
      return ids.join(",");
    },
  },
  mounted() {
    getBhDeptTree().then((res) => {
      this.treeData = res.data.data;
    });
  },
  methods: {
    openAll(data) {
      this.$set(this.treeOption, "defaultExpandAll", data);
      this.isOpen = !data;
      this.randomKey1 = Math.random();
    },
    openDialog(row) {
      this.userAccount = row.userAccount;
      this.page2 = {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      };
      this.getData();
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.$refs.crud2.doLayout();
      });
    },
    getData() {
      this.loading2 = true;
      getDatAPage(
        this.page2.currentPage,
        this.page2.pageSize,
        this.userAccount
      ).then((res) => {
        const data = res.data.data;
        this.page2.total = data.total;
        this.tableData = data.records;
        this.loading2 = false;
      });
    },
    handleZX(row) {
      this.$confirm("是否确定将该账号强制注销?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          changeEnable(row.userId).then((res) => {
            if (res.data.success) {
              this.$message.success("操作成功！");
              this.onLoad(this.page, this.query);
            }
          });
        })
        .then(() => {
          this.onLoad(this.page);
          // this.$message({
          //   type: "success",
          //   message: "操作成功!",
          // });
          this.$refs.crud.toggleSelection();
        });
    },
    handleOffline(row) {
      let id = "";
      if (Object.keys(row).length == 0) {
        if (this.selectionList.length === 0) {
          this.$message.warning("请选择至少一条数据");
          return;
        } else {
          id = this.ids;
        }
      } else {
        id = row.userId;
      }
      this.$confirm("确定将该用户的所有客户端踢下线?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return changeOnline(id);
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          if (this.dialogVisible) {
            this.dialogVisible = false;
          }
          this.$refs.crud.toggleSelection();
        });
    },
    nodeClick(data) {
      this.treeDeptId = data.id;
      this.page.currentPage = 1;
      this.onLoad(this.page, this.query);
    },
    searchReset() {
      getBhDeptTree().then((res) => {
        this.treeData = res.data.data;
      });
      this.treeDeptId = "";
      this.query = {};
      this.onLoad(this.page);
    },
    searchChange(params, done) {
      this.query = params;
      this.page.currentPage = 1;
      this.onLoad(this.page, params);
      done();
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
      this.height = `${this.$refs.container.$el.clientHeight - 70}px`;
    },
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange2(currentPage) {
      this.page2.currentPage = currentPage;
      this.getData();
    },
    sizeChange2(pageSize) {
      this.page2.pageSize = pageSize;
      this.page2.currentPage = 1;
      this.getData();
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      getList(
        page.currentPage,
        page.pageSize,
        this.treeDeptId,
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
.box {
  /deep/.avue-tree__filter {
    position: fixed;
    margin-bottom: 0;
    z-index: 99;
    width: 11%;
  }
  /deep/.el-tree {
    margin-top: 25px;
  }
  .treeBtn {
    position: absolute;
    left: 15%;
    top: 21px;
    z-index: 100;
    /deep/.el-button--mini {
      font-size: 15px;
    }
  }
}
/deep/.el-col {
  margin-bottom: 0;
}
.title {
  border-left: 6px solid #51a5fc;
  padding-left: 8px;
}
.dialog-table {
  margin-top: 10px;
  /deep/.avue-crud__menu {
    display: none;
  }
}
* {
  box-sizing: border-box;
}
</style>