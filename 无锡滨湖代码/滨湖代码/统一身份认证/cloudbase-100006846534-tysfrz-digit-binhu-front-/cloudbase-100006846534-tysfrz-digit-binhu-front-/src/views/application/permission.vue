<template>
  <basic-container>
    <el-tabs v-model="activeName">
      <el-tab-pane label="权限申请" name="1"> </el-tab-pane>
      <el-tab-pane label="应用授权" name="2"> </el-tab-pane>
    </el-tabs>
    <template v-if="activeName === '1'">
      <avue-crud
        :option="option"
        :table-loading="loading"
        :data="data"
        :page.sync="page"
        v-model="form"
        :before-open="beforeOpen"
        @row-update="rowUpdate"
        ref="crud"
        @current-change="currentChange"
        @size-change="sizeChange"
        @refresh-change="refreshChange"
        @on-load="onLoad"
        @selection-change="selectionChange"
      >
        <template slot="menuLeft">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-delete"
            @click="audit"
            >批量审核
          </el-button>
        </template>
        <template slot-scope="{ row, index }" slot="menu">
          <el-button
            type="primary"
            plain
            size="mini"
            v-if="row.reviewStatus == 0"
            @click="$refs.crud.rowEdit(row, index)"
            >审 核</el-button
          >
          <el-button
            type="success"
            plain
            size="mini"
            @click="$refs.crud.rowView(row, index)"
            >详 情</el-button
          >
        </template>
      </avue-crud>
      <el-dialog
        title="审核"
        append-to-body
        :visible.sync="visible"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :show-close="false"
        width="35%"
        top="35vh"
      >
        <el-form
          :model="dialogForm"
          ref="dialogForm"
          size="small"
          :rules="rules"
          label-width="120px"
        >
          <el-form-item label="审核意见：" prop="reviewOpinion">
            <el-input
              type="textarea"
              :rows="4"
              v-model="dialogForm.reviewOpinion"
              placeholder="请输入审核意见"
            ></el-input>
          </el-form-item>
          <el-form-item label="审核状态：" prop="reviewStatus">
            <el-select
              v-model="dialogForm.reviewStatus"
              placeholder="请选择审核状态"
              style="width: 100%"
            >
              <el-option :value="1" label="通过"></el-option>
              <el-option :value="2" label="驳回"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            size="small"
            icon="el-icon-circle-check"
            :loading="submitLoading"
            @click="submitAudit"
          >
            修 改
          </el-button>
          <el-button
            size="small"
            icon="el-icon-circle-close"
            @click="visible = false"
          >
            取 消
          </el-button>
        </span> </el-dialog
      >Ï
    </template>
    <template v-else-if="activeName === '2'">
      <Application />
    </template>
  </basic-container>
</template>

<script>
import {
  getPermissionApplicationList,
  updatePermission,
  submitList,
} from "@/api/application/application";
import { mapGetters } from "vuex";
import Application from "./components/application.vue";

export default {
  components: { Application },
  data() {
    return {
      form: {},
      loading: true,
      page: {
        pageSize: 20,
        currentPage: 1,
        total: 0,
      },
      option: {
        height: "auto",
        calcHeight: 30,
        tip: false,
        border: true,
        index: true,
        addBtn: false,
        selection: true,
        selectable: (row, index) => {
          return row.reviewStatus == 0;
        },
        dialogClickModal: false,
        align: "center",
        editTitle: "审核",
        stripe: true,
        delBtn: false,
        columnBtn: false,
        menu: true,
        editBtn: false,
        dialogWidth: "35%",
        column: [
          {
            label: "应用名称",
            prop: "appName",
            editDisplay: false,
            span: 24,
          },
          {
            label: "申请账号",
            prop: "account",
            span: 24,
            editDisplay: false,
          },
          {
            label: "所属部门",
            prop: "deptName",
            span: 24,
            editDisplay: false,
          },
          {
            label: "申请时间",
            prop: "createTime",
            span: 24,
            editDisplay: false,
          },
          {
            label: "申请理由",
            prop: "reason",
            span: 24,
            editDisplay: false,
          },
          {
            label: "申请人",
            prop: "applicantName",
            span: 24,
            editDisplay: false,
          },
          {
            label: "审核意见",
            prop: "reviewOpinion",
            span: 24,
            type: "textarea",
            minRows: 3,
            rules: [
              {
                required: true,
                message: "请输入审核意见",
                trigger: "blur",
              },
            ],
          },
          {
            label: "审核状态",
            prop: "reviewStatusName",
            editDisplay: false,
          },
          {
            label: "审核状态",
            prop: "reviewStatus",
            type: "select",
            hide: true,
            viewDisplay: false,
            span: 24,
            dicData: [
              {
                label: "通过",
                value: 1,
              },
              {
                label: "驳回",
                value: 2,
              },
            ],
            rules: [
              {
                required: true,
                message: "请选择审核状态",
                trigger: "blur",
              },
            ],
          },
        ],
      },
      data: [],
      selectionList: [],
      visible: false,
      dialogForm: {
        reviewOpinion: "",
        reviewStatus: "", // 1-通过 2-驳回
      },
      rules: {
        reviewOpinion: [
          { required: true, message: "请输入审核意见", trigger: "blur" },
        ],
        reviewStatus: [
          { required: true, message: "请选择审核状态", trigger: "blur" },
        ],
      },
      submitLoading: false,
      activeName: "1",
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    ids() {
      let ids = [];
      this.selectionList.forEach((ele) => {
        ids.push(ele.id);
      });
      return ids.join(",");
    },
  },
  methods: {
    beforeOpen(done, type) {
      if (["edit"].includes(type)) {
        this.form = {
          id: this.form.id,
        };
      }
      done();
    },
    rowUpdate(row, index, done, loading) {
      let params = {
        id: row.id,
        reviewOpinion: row.reviewOpinion,
        reviewStatus: row.reviewStatus,
      };
      updatePermission(params).then(
        () => {
          this.onLoad(this.page);
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
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    selectionChange(list) {
      this.selectionList = list;
    },
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },
    onLoad(page, params = {}) {
      this.loading = true;
      getPermissionApplicationList(page.currentPage, page.pageSize).then(
        (res) => {
          const data = res.data.data;
          this.page.total = data.total;
          this.data = data.records;
          this.loading = false;
          this.selectionClear();
        }
      );
    },
    audit() {
      if (!this.ids) {
        this.$message.warning("请选择至少一条数据");
        return;
      }
      this.visible = true;
      this.dialogForm = {
        reviewOpinion: "",
        reviewStatus: "",
      };
    },
    submitAudit() {
      this.$refs["dialogForm"].validate((valid) => {
        if (valid) {
          this.submitLoading = true;
          submitList({ ids: this.ids, ...this.dialogForm })
            .then(() => {
              this.$message.success("操作成功");
              this.onLoad(this.page, this.query);
              setTimeout(() => {
                this.submitLoading = false;
                this.visible = false;
              }, 500);
            })
            .catch(() => {
              setTimeout(() => {
                this.submitLoading = false;
                this.visible = false;
              }, 500);
            });
        }
      });
    },
  },
};
</script>

<style>
</style>
