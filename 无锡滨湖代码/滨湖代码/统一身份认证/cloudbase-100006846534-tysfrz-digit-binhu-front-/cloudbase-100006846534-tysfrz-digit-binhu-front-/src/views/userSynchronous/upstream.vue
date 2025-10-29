<template>
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
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot-scope="{ row }" slot="menu">
        <el-button type="primary" plain size="mini" @click="openDialog(row)">
          手动同步
        </el-button>
        <el-button
          type="primary"
          plain
          size="mini"
          @click="openAutoDialog(row)"
        >
          自动同步设置
        </el-button>
        <el-button type="success" plain size="mini" @click="handleGo(row)">
          同步记录
        </el-button>
      </template>
    </avue-crud>
    <!-- 同步信息 -->
    <el-dialog
      title="同步信息"
      :visible.sync="dialogVisible"
      v-if="dialogVisible"
      width="1000px"
      class="myDialog"
      append-to-body
      :before-close="beforeClose"
      v-loading.fullscreen.lock="dialogLoading"
    >
      <div style="height: 500px; overflow: auto" v-if="deptSyn">
        <el-tree
          :data="dialogTreeData"
          ref="tree"
          :highlight-current="true"
          :check-on-click-node="true"
          show-checkbox
          node-key="id"
          @check="handleCheckChange"
          :props="{ value: 'id', label: 'title' }"
          default-expand-all
        >
        </el-tree>
      </div>
      <div v-else>
        <div class="dept-con2" style="height: 500px">
          <el-input
            placeholder="输入部门关键字进行过滤"
            v-model="filterText"
            size="small"
          >
          </el-input>
          <el-tree
            style="height: 460px; overflow-y: scroll"
            :data="dialogTreeData"
            ref="deptTree"
            :highlight-current="true"
            :check-on-click-node="true"
            show-checkbox
            node-key="id"
            @check="handleCheckChange2"
            :props="{ value: 'id', label: 'title' }"
            :filter-node-method="filterNode"
            default-expand-all
          >
          </el-tree>
        </div>
        <div
          v-loading="userLoading"
          class="user-con"
          v-if="checkTreeData.length > 0"
        >
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
              :label="item.userid"
              :key="item.userid"
              >{{ item.name }} / {{ item.position }}</el-checkbox
            >
          </el-checkbox-group>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="handleSave">
          同 步
        </el-button>
        <el-button size="small" @click="beforeClose">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 同步记录 -->
    <el-dialog
      title="同步记录"
      :visible.sync="dialogVisible2"
      width="43%"
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
    <!-- 自动同步设置弹窗 -->
    <el-dialog
      title="自动同步设置"
      :visible.sync="dialogVisible3"
      v-if="dialogVisible3"
      width="1000px"
      append-to-body
      :before-close="beforeClose3"
    >
      <el-form ref="form" :model="form3" label-width="80px">
        <el-form-item label="自动同步:">
          <el-switch v-model="form3.zdtb" size="small"></el-switch>
        </el-form-item>
        <el-form-item label="周期:">
          每
          <el-input-number
            v-model="form3.time"
            controls-position="right"
            :min="1"
            size="small"
          >
          </el-input-number>
          小时
        </el-form-item>
        <el-form-item label="数据选择:" v-show="false">
          <div style="height: 400px; overflow: scroll" v-if="deptSyn">
            <el-tree
              default-expand-all
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
          <div v-else>
            <!-- 左侧 -->
            <div class="dept-con2">
              <el-input
                placeholder="输入部门关键字进行过滤"
                v-model="filterText"
                size="small"
              >
              </el-input>
              <el-tree
                style="height: 360px; overflow-y: scroll"
                :data="dialogTreeData"
                ref="deptTree"
                :highlight-current="true"
                :check-on-click-node="true"
                show-checkbox
                node-key="id"
                @check="handleCheckChange2"
                :props="{ value: 'id', label: 'title' }"
                :filter-node-method="filterNode"
                :default-checked-keys="checkTreeData"
                default-expand-all
              >
              </el-tree>
            </div>
            <!-- 右侧 -->
            <div
              v-loading="userLoading"
              class="user-con"
              style="height: 400px"
              v-if="checkTreeData.length > 0"
            >
              <el-checkbox
                :indeterminate="isIndeterminate"
                v-model="checkAll"
                @change="handleCheckAllChange"
              >
                全选
              </el-checkbox>
              <el-checkbox-group
                v-model="checkedUsers"
                @change="handleCheckedUserChange"
              >
                <el-checkbox
                  v-for="item in userList"
                  :label="item.userid"
                  :key="item.userid"
                  >{{ item.name }} / {{ item.position }}</el-checkbox
                >
              </el-checkbox-group>
            </div>
          </div>
        </el-form-item>
      </el-form>
      <!-- <div style="color: #004fd8; margin-top: 20px">明日0点开始生效</div> -->
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="handleSaveAuto">
          保 存
        </el-button>
        <el-button size="small" @click="beforeClose3">取 消</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
import { getUpSyn, getSynList } from "@/api/userSynchronous/userSynchronous";
import { getBhDeptTreeSync, syn } from "@/api/organization/department";
import {
  getUserSyn,
  syn as userSyn,
  userSynSubmit,
  getUserSynDetail,
} from "@/api/organization/user";
export default {
  data() {
    return {
      dialogLoading: false,
      form: {},
      query: {},
      loading: true,
      page: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      option: {
        height: "auto",
        calcHeight: 10,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        viewBtn: false,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        searchLabelWidth: 86,
        menuWidth: 350,
        stripe: true,
        column: [
          {
            label: "数据源名称",
            prop: "dataSource",
            search: true,
          },
          {
            label: "数据类型",
            prop: "dataTypeName",
          },
          {
            label: "自动同步",
            prop: "isEnableName",
          },
          {
            label: "最新同步时间",
            prop: "createTime",
          },
          {
            label: "同步状态",
            prop: "isSynName",
          },
        ],
      },
      data: [],
      deptSyn: true,
      dialogTreeData: [],
      dialogVisible: false,
      checkTreeData: [],
      filterText: "",
      userLoading: true,
      checkAll: false,
      checkedUsers: [], // 保存被选中的人员
      userList: [], // 保存人员数据
      isIndeterminate: true,
      dialogVisible2: false,
      dialogVisible3: false,
      form3: { zdtb: true },
      type: 1,
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
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        refreshBtn: false,
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
        pageSize: 30,
        currentPage: 1,
        total: 0,
      },
      fullscreenLoading: false,
    };
  },
  watch: {
    filterText(val) {
      this.$refs.deptTree.filter(val);
    },
  },
  methods: {
    handleSaveAuto() {
      this.form3.isEnable = this.form3.zdtb ? 1 : 0;
      this.form3.type = this.deptSyn ? 2 : 1;
      // console.log(this.checkTreeData, 111);
      // console.log(this.form3, 22);
      // console.log(this.checkedUsers, 333);
      if (!this.deptSyn) {
        let checkTreeData = this.checkTreeData.map((item) => {
          return item.id;
        });
        // this.form3.deptId = checkTreeData.toString();
        // // 部门同步
        // this.form3.synIds = this.checkedUsers.toString();
      } else {
        // 人员同步
        let checkTreeData = this.checkTreeData.map((item) => {
          return item.id;
        });
        // this.form3.synIds = checkTreeData.toString();
      }

      userSynSubmit(this.form3).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
          this.beforeClose3();
        }
      });
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
    // 部门同步
    handleCheckChange(data, checked, indeterminate) {
      this.checkTreeData = this.$refs.tree.getCheckedNodes(false, true);
      // console.log(this.checkTreeData, 33);
    },
    // 人员同步
    handleCheckChange2(data, checked, indeterminate) {
      this.checkTreeData = this.$refs.deptTree.getCheckedNodes();
      // console.log(this.$refs.deptTree);
      let checkTreeData = this.checkTreeData.map((item) => {
        return item.id;
      });
      checkTreeData = checkTreeData.toString();
      this.userLoading = true;
      this.checkAll = false;
      getUserSyn(checkTreeData).then((res) => {
        this.userList = res.data.data;
        this.userLoading = false;
      });
    },
    handleCheckedUserChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.userList.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.userList.length;
    },
    handleCheckAllChange(val) {
      if (val) {
        this.checkedUsers = this.userList.map((item) => {
          return item.userid;
        });
      } else {
        this.checkedUsers = [];
      }
      this.isIndeterminate = false;
    },
    openDialog(row) {
      // getBhDeptTreeSync().then((res) => {
      //   this.dialogTreeData = res.data.data;
      // });
      // this.deptSyn = row.dataType == 1 ? false : true;
      // this.$nextTick(() => {
      //   this.dialogVisible = true;
      // });

      // this.fullscreenLoading = true;
      const loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0)",
      });
      if (row.dataType === 1) {
        userSyn().then((res) => {
          if (res.data.success) {
            this.$message.success("操作成功！");
            setTimeout(() => {
              // this.fullscreenLoading = false;
              loading.close();
              this.dialogLoading = false;
              this.onLoad(this.page, this.query);
              this.beforeClose();
            }, 500);
          }
        });
      } else {
        syn().then((res) => {
          if (res.data.success) {
            this.$message.success("操作成功！");
            setTimeout(() => {
              // this.fullscreenLoading = false;
              loading.close();
              this.dialogLoading = false;
              this.onLoad(this.page, this.query);
              this.beforeClose();
            }, 500);
          }
        });
      }
    },
    openAutoDialog(row) {
      getBhDeptTreeSync().then((res) => {
        this.dialogTreeData = res.data.data;
      });
      this.deptSyn = row.dataType == 1 ? false : true;
      getUserSynDetail(row.dataType).then((res) => {
        const { data } = res.data;
        if (data.deptId && data.synIds) {
          this.checkTreeData = data.deptId.split(",");
          this.checkedUsers = data.synIds.split(",");
          getUserSyn(data.deptId).then((rsp) => {
            this.userList = rsp.data.data;
            this.userLoading = false;
            if (this.checkedUsers.length === this.userList.length) {
              this.checkAll = true;
              this.isIndeterminate = false;
            } else {
              this.checkAll = false;
              this.isIndeterminate = true;
            }
          });
        }
        this.form3 = data;
        let zdtb = data.isEnable == 1 ? true : false;
        this.$set(this.form3, "zdtb", zdtb);
      });
      this.$nextTick(() => {
        this.dialogVisible3 = true;
      });
    },
    // 保存同步
    async handleSave() {
      if (this.checkTreeData.length == 0) {
        this.$message.warning("请选择部门");
        return;
      }
      if (this.deptSyn) {
        let checkTreeData = this.checkTreeData.map((item) => {
          return item.id;
        });
        checkTreeData = checkTreeData.toString();
        this.dialogLoading = true;
        syn(checkTreeData).then((res) => {
          if (res.data.success) {
            this.$message.success("操作成功！");
            setTimeout(() => {
              this.dialogLoading = false;
              this.onLoad(this.page, this.query);
              this.beforeClose();
            }, 500);
          }
        });
      } else {
        if (this.checkedUsers.length == 0) {
          this.$message.warning("请选择人员");
          return;
        }
        let checkedUsers = { account: this.checkedUsers.toString() };
        this.dialogLoading = true;
        userSyn(checkedUsers).then((res) => {
          if (res.data.success) {
            this.$message.success("操作成功！");
            setTimeout(() => {
              this.dialogLoading = false;
              this.onLoad(this.page, this.query);
              this.beforeClose();
            }, 500);
          }
        });
      }
    },
    beforeClose() {
      if (!this.deptSyn) {
        this.checkAll = false;
        this.userList = [];
        this.checkedUsers = [];
      }
      this.checkTreeData = [];
      this.dialogVisible = false;
      // this.deptSyn = true;
    },
    beforeClose3() {
      if (!this.deptSyn) {
        this.checkAll = false;
        this.userList = [];
        this.checkedUsers = [];
      }
      this.checkTreeData = [];
      this.dialogVisible3 = false;
    },
    handleGo(row) {
      this.type = row.dataTypeName;
      this.onLoad2(this.page2, this.query2);
      this.dialogVisible2 = true;
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
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
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
    onLoad(page, params = {}) {
      this.loading = true;
      getUpSyn(
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
    onLoad2(page, params = {}) {
      this.loading2 = true;
      getSynList(
        page.currentPage,
        page.pageSize,
        this.type,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page2.total = data.total;
        this.data2 = data.records;
        this.loading2 = false;
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.dept-con {
  height: 500px;
  display: inline-block;
  width: 46%;
  overflow: scroll;
}
.dept-con2 {
  display: inline-block;
  width: 46%;
  height: 400px;
}
.user-con {
  height: 500px;
  display: inline-block;
  width: 50%;
  margin-left: 20px;
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
/deep/ .el-loading-mask {
  background-color: rgba(0, 0, 0, 0.5) !important;
}
</style>