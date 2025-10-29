<template>
  <el-dialog
    append-to-body
    :title="title"
    :visible.sync="dialogVisible"
    @closed="closeDialog"
    width="60%"
  >
    <el-steps
      :active="active"
      finish-status="success"
      style="margin-bottom: 20px"
    >
      <el-step title="选择应用"></el-step>
      <el-step
        :title="
          activeName == 1
            ? '选择组织架构'
            : activeName == 2
            ? '选择用户组'
            : '选择用户'
        "
      ></el-step>
      <el-step title="完成"></el-step>
    </el-steps>
    <avue-crud
      v-show="active == 0"
      :option="option"
      :data="data"
      @selection-change="selectionChange"
      @search-change="searchChange"
      @search-reset="searchReset"
      ref="crud"
    ></avue-crud>
    <div class="input_container">
      <el-input
        v-if="!this.dialogType && active == 1"
        placeholder="输入关键字进行过滤"
        v-model="filterText"
        size="small"
      >
      </el-input>
    </div>
    <el-scrollbar v-show="active == 1" style="height: 300px">
      <el-tree
        :data="dialogTreeData"
        ref="deptTree"
        show-checkbox
        node-key="id"
        check-on-click-node
        :expand-on-click-node="false"
        highlight-current
        @node-click="nodeClick"
        :props="{
          value: 'id',
          label: 'title',
        }"
        :filter-node-method="filterNode"
        :default-checked-keys="form.defaultChecked"
        default-expand-all
      >
      </el-tree>
    </el-scrollbar>
    <div v-show="active == 1 && activeName == 2">
      适用部门职位：
      <el-radio-group
        :disabled="dialogType == 'view'"
        v-model="form.userGroupPostType"
      >
        <el-radio :label="1">仅主岗</el-radio>
        <el-radio :label="2">全部</el-radio>
      </el-radio-group>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeDialog" size="small">取 消</el-button>
      <el-button
        type="primary"
        size="small"
        v-if="active == 0"
        @click="active = active + 1"
        >下一步</el-button
      >
      <template v-if="active == 1">
        <el-button @click="active = active - 1" size="small">上一步</el-button>
        <el-button
          type="primary"
          size="small"
          v-if="!dialogType"
          @click="submitData"
          >完成
        </el-button>
        <el-button type="primary" size="small" v-else @click="closeDialog">
          确定
        </el-button>
      </template>
    </div>
  </el-dialog>
</template>
<script>
import { updateOrAdd, getDetail } from "@/api/empower/empower";
import { getAllList } from "@/api/application/management";
import { getGroupList } from "@/api/organization/userGroup";
import { getUserList, getBhDeptTree } from "@/api/organization/user";

export default {
  props: {
    title: {
      type: String,
      default: "新增组织架构授权",
    },
    activeName: {
      type: String,
      default: "1",
    },
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    detailId: {
      type: String,
      default: "",
    },
    dialogType: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      active: 0,
      ids: "",
      dialogTreeData: [],
      checkTreeData: [],
      filterText: "",
      form: { userGroupPostType: "", defaultChecked: [] },
      query: {},
      data: [],
      option: {
        height: 300,
        selection: true,
        searchMenuSpan: 6,
        tip: false,
        border: true,
        menu: false,
        addBtn: false,
        columnBtn: false,
        searchShowBtn: false,
        refreshBtn: false,
        align: "center",
        stripe: true,
        column: [
          {
            label: "关键字",
            prop: "key",
            hide: true,
            search: this.dialogType == "view" ? false : true,
          },
          {
            label: "应用名称",
            prop: "name",
          },
          {
            label: "应用ID",
            prop: "id",
          },
        ],
      },
      apiMap: {
        1: getBhDeptTree,
        2: getGroupList,
        3: getUserList,
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.deptTree.filter(val);
    },
  },
  created() {
    if (this.dialogType == "view") {
      this.$set(this.option, "selectable", (row, index) => {
        return index === -1;
      });
    }
  },
  async mounted() {
    if (this.detailId) {
      await getDetail(this.detailId).then((res) => {
        this.form = res.data.data;
        if (this.form.bizId) {
          this.form.defaultChecked = this.form.bizId.split(",");
        }
      });
    }
    this.onLoad();
    this.apiMap[this.activeName]().then((res) => {
      let data = res.data.data;
      this.deepEach(data);
      this.dialogTreeData = data;
    });
  },
  methods: {
    deepEach(arr) {
      arr.forEach((item) => {
        // if (this.dialogType == "view") {
        if (this.detailId) {
          // item.disabled = true;
        }
        item.title = item.title ? item.title : item.name;
        if (item.children) {
          this.deepEach(item.children); // 递归
        }
      });
    },
    submitData() {
      //let checkTreeData = this.$refs.deptTree.getCheckedKeys(true, false);
      let checkedData = this.$refs.deptTree.getCheckedKeys();
      let halfCheckedData = this.$refs.deptTree.getHalfCheckedKeys();
      let checkTreeData =  checkedData.filter(item => !halfCheckedData.includes(item)).toString();
      // let checkTreeData = this.$refs.deptTree.getCheckedKeys(false, false);
      //checkTreeData = checkTreeData.toString();
      // console.log(checkTreeData, "checkTreeData");
      if (!this.ids) {
        this.$message.error("请选择应用！");
        return false;
      }
      if (!checkTreeData) {
        if (this.activeName == 1) {
          this.$message.error("请选择组织架构！");
        } else if (this.activeName == 2) {
          this.$message.error("请选择用户组！");
        } else if (this.activeName == 3) {
          this.$message.error("请选择用户！");
        }
        return false;
      }
      if (this.activeName == 2 && !this.form.userGroupPostType) {
        this.$message.error("请选择适用部门职位！");
        return false;
      }
      let obj = {
        applicationId: this.ids,
        bizId: checkTreeData,
        type: this.activeName,
        userGroupPostType: this.form.userGroupPostType,
      };
      if (this.detailId) {
        obj.id = this.detailId;
      }
      updateOrAdd(obj).then((res) => {
        if (res.data.success) {
          this.$message.success("保存成功！");
        }
        this.closeDialog();
        this.$emit("success");
      });
    },
    nodeClick(data, node) {
      // console.log(node, 11);
      this.parentNodeState = false;
      this.currentNodeState = node.checked;
      this.childNodesChange(node);
      let nodes = node.parent.childNodes;
      for (let i = 0; i < nodes.length; i++) {
        if (nodes[i].checked == true) {
          this.parentNodeState = true;
          break;
        }
      }
      this.parentNodesChange(node);
    },
    childNodesChange(node) {
      let len = node.childNodes.length;
      if (len > 1) {
        for (let i = 0; i < len; i++) {
          node.childNodes[i].checked = this.currentNodeState;
          this.childNodesChange(node.childNodes[i]);
        }
      }
    },
    parentNodesChange(node) {
      if (node.parent) {
        for (let key in node) {
          if (key == "parent") {
            node[key].checked = this.parentNodeState;
            this.parentNodesChange(node[key]);
          }
        }
      }
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
    searchReset() {
      this.query = {};
      this.parentId = 0;
      this.onLoad();
    },
    searchChange(params, done) {
      this.query = params;
      this.onLoad();
      done();
    },
    selectionChange(list) {
      this.selectionList = list;
      let ids = [];
      list.forEach((ele) => {
        ids.push(ele.id);
      });
      this.ids = ids.join(",");
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },
    onLoad() {
      getAllList(Object.assign({}, this.query)).then((res) => {
        this.data = res.data.data;
        if (this.form.applicationId) {
          let arr = this.form.applicationId.split(",");
          this.$nextTick(() => {
            this.data.forEach((row) => {
              arr.forEach((item) => {
                if (item == row.id) {
                  this.$refs.crud.toggleRowSelection(row, true);
                }
              });
            });
          });
        }

        this.loading = false;
        this.selectionClear();
      });
    },
    closeDialog() {
      this.$emit("update:dialogVisible", false);
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ .avue-crud__menu {
  display: none;
}
/deep/ .el-tree-node__label {
  position: relative;
}
/deep/ .el-tree-node__label:before {
  content: "";
  width: 20px;
  height: 20px;
  display: block;
  position: absolute;
  top: 0px;
  left: -24px;
  z-index: 999;
}
/deep/ .el-checkbox__inner {
  top: 0;
}
.input_container {
  margin-bottom: 6px;
}
</style>
