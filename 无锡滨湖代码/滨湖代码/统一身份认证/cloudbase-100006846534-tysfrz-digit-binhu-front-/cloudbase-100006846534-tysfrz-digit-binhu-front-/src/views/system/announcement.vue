<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :permission="permissionList"
      :before-open="beforeOpen"
      v-model="form"
      :page.sync="page"
      ref="crud"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @current-change="currentChange"
      @size-change="sizeChange"
      @selection-change="selectionChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="menuLeft">
        <el-button
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
          >删 除
        </el-button>
      </template>
      <template slot-scope="{ row }" slot="menu">
        <el-button
          type="primary"
          plain
          size="mini"
          v-if="row.type == 0"
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
          type="warning"
          plain
          size="mini"
          v-if="row.type == 0"
          @click="handleAnnounce(row)"
          >立即发布</el-button
        >
        <el-button
          type="danger"
          plain
          size="mini"
          @click="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        >
      </template>
      <template slot="fjForm">
        <el-upload
          class="upload-demo"
          ref="upload"
          action="/api/squirrel-szbh/file/upload"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :on-remove="handleRemove"
          :limit="3"
          :file-list="fileList"
          :headers="headers"
          name="multipartFile"
        >
          <el-button
            slot="trigger"
            size="small"
            type="primary"
            :disabled="type == 'view'"
            >附件上传</el-button
          >
          <div slot="tip" class="el-upload__tip">
            支持格式：.rar .zip .doc .docx .pdf .xlsx，单个文件不能超过20MB
          </div>
        </el-upload>
      </template>
      <template slot="contentForm">
        <Toolbar
          style="border-bottom: 1px solid #ccc"
          :editor="editor"
          :defaultConfig="toolbarConfig"
          :mode="mode"
        />
        <Editor
          style="height: 300px; overflow-y: hidden"
          v-model="form.content"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onCreated="onCreated"
          @onChange="onChange"
        />
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import {
  getList,
  getDetail,
  remove,
  submit,
  update,
} from "@/api/system/announcement.js";
import { headers } from "@/api/tool/datasource";

import { Editor, Toolbar } from "@wangeditor/editor-for-vue";

export default {
  components: { Editor, Toolbar },
  data() {
    var validateContent = (rule, value, callback) => {
      if (this.content === "") {
        callback(new Error("请输入公告内容"));
      } else {
        callback();
      }
    };
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
        calcHeight: 30,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        searchLabelWidth: 72,
        border: true,
        index: true,
        viewBtn: false,
        editBtn: false,
        stripe: true,
        delBtn: false,
        selection: true,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        menuWidth: 320,
        align: "center",
        column: [
          {
            label: "上架时间",
            prop: "date1",
            type: "daterange",
            display: false,
            searchRange: true,
            hide: true,
            search: true,
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
            pickerOptions: {
              firstDayOfWeek: 1,
            },
          },
          {
            label: "下架时间",
            prop: "date2",
            type: "daterange",
            display: false,
            searchRange: true,
            hide: true,
            search: true,
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
            pickerOptions: {
              firstDayOfWeek: 1,
            },
          },
          {
            label: "公告标题",
            prop: "title",
            search: true,
            span: 24,
            rules: [
              {
                required: true,
                message: "请输入公告标题",
                trigger: "blur",
              },
            ],
          },
          {
            label: "公告类型",
            prop: "noticeType",
            type: "select",
            search: true,
            span: 24,
            dicData: [
              {
                label: "服务指南",
                value: 1,
              },
              {
                label: "通知公告",
                value: 2,
              },
            ],
            rules: [
              {
                required: true,
                message: "请选择公告类型",
                trigger: "blur",
              },
            ],
          },
          {
            label: "发布账号",
            prop: "account",
            display: false,
          },
          {
            label: "信息来源",
            prop: "infoSource",
            span: 24,
          },
          {
            label: "发布状态",
            prop: "type",
            type: "select",
            dicData: [
              {
                label: "已发布",
                value: 1,
              },
              {
                label: "待发布",
                value: 0,
              },
            ],
            display: false,
            search: true,
          },
          {
            label: "发布时间",
            prop: "releaseTime",
            type: "date",
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
            span: 24,
            rules: [
              {
                required: true,
                message: "请选择发布时间",
                trigger: "blur",
              },
            ],
            blur: ({ value }) => {
              if (value && this.form.offShelfTime) {
                if (value > this.form.offShelfTime) {
                  this.form.releaseTime = this.form.offShelfTime;
                }
              }
            },
          },
          {
            label: "下架时间",
            prop: "offShelfTime",
            type: "date",
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
            span: 24,
            hide: true,
            blur: ({ value }) => {
              if (value && this.form.releaseTime) {
                if (value < this.form.releaseTime) {
                  this.form.offShelfTime = this.form.releaseTime;
                }
              }
            },
          },
          {
            label: "是否置顶",
            prop: "isTop",
            type: "switch",
            search: true,
            span: 24,
            value: 0,
            dicData: [
              {
                label: "否",
                value: 0,
              },
              {
                label: "是",
                value: 1,
              },
            ],
          },
          {
            label: "附件",
            prop: "fj",
            formslot: true,
            hide: true,
            span: 24,
          },
          {
            label: "公告内容",
            prop: "content",
            formslot: true,
            hide: true,
            span: 24,
            rules: [
              {
                required: true,
                validator: validateContent,
                trigger: "blur",
              },
            ],
          },
        ],
      },
      data: [],
      fileList: [],
      headers: headers(), // 自定义上传空间需要的权限
      type: "", //弹窗类型
      editor: null,
      toolbarConfig: {},
      editorConfig: { placeholder: "请输入 公告内容" },
      mode: "simple", // or 'default'
      content: "",
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
  beforeDestroy() {
    const editor = this.editor;
    if (editor == null) return;
    editor.destroy(); // 组件销毁时，及时销毁编辑器
  },
  methods: {
    onChange(val) {
      //   console.log(val.getText(), 111);
      this.content = val.getText();
    },
    onCreated(editor) {
      this.editor = Object.seal(editor); // 一定要用 Object.seal() ，否则会报错
    },
    handleRemove(file, fileList) {
      if (fileList.length > 0) {
        this.fileList = fileList.map((item) => {
          if (item.response == undefined) {
            return item;
          } else {
            return item.response.data;
          }
        });
      } else {
        this.fileList = [];
      }
    },
    handleAvatarSuccess(res, file) {
      let obj = {
        ...res,
        id: res.data.id,
        name: res.data.fileName,
        url: res.data.fileUrl,
      };
      this.fileList.push(obj);
    },
    beforeAvatarUpload(file) {
      var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      const extension = testmsg === "rar";
      const extension2 = testmsg === "zip";
      const extension3 = testmsg === "doc";
      const extension4 = testmsg === "docx";
      const extension5 = testmsg === "pdf";
      const extension6 = testmsg === "xlsx";
      if (
        !extension &&
        !extension2 &&
        !extension3 &&
        !extension4 &&
        !extension5 &&
        !extension6
      ) {
        this.$message({
          message: "请检查上传文件格式！",
          type: "warning",
        });
      }
      const isLt2M = file.size / 1024 / 1024 < 20;
      if (!isLt2M) {
        this.$message.error("上传附件大小不能超过 20MB!");
      }
      return (
        (extension ||
          extension2 ||
          extension3 ||
          extension4 ||
          extension5 ||
          extension6) &&
        isLt2M
      );
    },
    handleAnnounce(row) {
      this.$confirm("是否确定发布公告?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return update({ id: row.id });
        })
        .then(() => {
          this.onLoad(this.page);
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        });
    },
    rowSave(row, done, loading) {
      if (this.fileList.length > 0) {
        let idArr = this.fileList.map((item) => {
          return item.id;
        });
        row.files = idArr.toString();
      } else {
        row.files = "";
      }
      submit(row).then(
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
      if (this.fileList.length > 0) {
        let idArr = this.fileList.map((item) => {
          return item.id;
        });
        row.files = idArr.toString();
      } else {
        row.files = "";
      }
      submit(row).then(
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
      this.$confirm("是否确定删除公告?", {
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
    currentChange(currentPage) {
      this.page.currentPage = currentPage;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
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
      this.type = type;
      this.fileList = [];
      this.form.content = "";
      if (["edit", "view"].includes(type)) {
        getDetail(this.form.id).then((res) => {
          this.form = res.data.data;
          if (this.form.fileList.length > 0) {
            this.fileList = this.form.fileList.map((item) => {
              return {
                ...item,
                id: item.id,
                name: item.fileName,
                url: item.fileUrl,
              };
            });
          }
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
    selectionClear() {
      this.selectionList = [];
      this.$refs.crud.toggleSelection();
    },
    refreshChange() {
      this.onLoad(this.page, this.query);
    },
    onLoad(page, params = {}) {
      this.loading = true;
      if (this.query.date1) {
        this.query.start = this.query.date1[0];
        this.query.end = this.query.date1[1];
      }
      if (this.query.date2) {
        this.query.downStart = this.query.date2[0];
        this.query.downEnd = this.query.date2[1];
      }
      getList(
        this.page.currentPage,
        this.page.pageSize,
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

<style src="@wangeditor/editor/dist/css/style.css"></style>