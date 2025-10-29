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
          type="danger"
          size="small"
          icon="el-icon-delete"
          @click="handleDelete"
          >删 除
        </el-button>
      </template>
      <template slot="logoForm">
        <el-upload
          class="avatar-uploader"
          action="/api/squirrel-szbh/file/upload"
          list-type="picture-card"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :on-remove="handleRemove"
          :disabled="type == 'view'"
          :limit="5"
          :file-list="fileList"
          :headers="headers"
          name="multipartFile"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <div>图片上传格式为：jpg、png且大小不能超过2MB</div>
      </template>
      <template slot="iconForm">
        <el-upload
          class="avatar-uploader"
          action="/api/squirrel-szbh/file/upload"
          list-type="picture-card"
          :on-success="handleAvatarSuccess2"
          :before-upload="beforeAvatarUpload"
          :on-remove="handleRemove2"
          :disabled="type == 'view'"
          :limit="1"
          :file-list="fileList2"
          :headers="headers"
          name="multipartFile"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <div>图片上传格式为：jpg、png且大小不能超过2MB！仅支持上传一张图片</div>
      </template>
      <template slot="clscForm">
        <upload
          @success="uploadSuccess"
          :fileList.sync="fileList"
          :showfile="true"
          :isSingle="false"
          :limit="5"
          :dialogType="type"
          :isImg="false"
          :tips="'最多上传5个文件！'"
        />
      </template>
      <template slot-scope="{ row }" slot="menu">
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
          v-if="row.isGround != 0 && row.type == 1"
          type="warning"
          plain
          size="mini"
          @click="handleStop(row)"
          >下 架</el-button
        >
        <el-button
          type="danger"
          plain
          size="mini"
          @click="$refs.crud.rowDel(row, index)"
          >删 除</el-button
        >
      </template>
      <template slot="isGround" slot-scope="scope">
        <span :style="{ color: scope.row.isGround == 1 ? 'green' : 'red' }">{{
          scope.row.isGroundName
        }}</span>
      </template>
      <template slot="isRecommend" slot-scope="scope">
        <span
          :style="{ color: scope.row.isRecommend == 1 ? 'green' : 'red' }"
          >{{ scope.row.isRecommendName }}</span
        >
      </template>
      <template slot="appSecretForm">
        <!-- <div style="display: flex"> -->
        <el-input
          v-model="form.appSecret"
          placeholder=""
          size="small"
          disabled
        ></el-input>
        <!-- <div style="margin-left: 10px">
            <el-button
              type="primary"
              size="small"
              icon="el-icon-refresh"
              @click="handleRefreshSecret"
              >刷新秘钥</el-button
            >
          </div>
        </div> -->
      </template>
    </avue-crud>
  </basic-container>
</template>

<script>
import {
  getList,
  getDetail,
  add,
  update,
  remove,
  getSecret,
  changeGround,
} from "@/api/application/management";
import { getAppCategoryTree } from "@/api/application/category";
import { getBhDeptTree } from "@/api/organization/user";
import upload from "@/components/upload/uploadBDR";
import { mapGetters } from "vuex";
import { headers } from "@/api/tool/datasource";

export default {
  components: { upload },
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      headers: headers(), // 自定义上传空间需要的权限
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
        searchSpan: 6,
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
        menuWidth: 320,
        stripe: true,
        column: [
          {
            label: "应用名称/App ID",
            prop: "code",
            display: false,
            hide: true,
            search: true,
            searchLabelWidth: 120,
          },
          {
            label: "应用名称",
            prop: "name",
            display: false,
          },
          {
            label: "App ID",
            prop: "appId",
            display: false,
          },
          {
            label: "应用类型",
            prop: "type",
            search: true,
            display: false,
            hide: true,
            type: "select",
            dicData: [
              { label: "已建应用", value: 1 },
              { label: "企业产品", value: 2 },
            ],
            change: ({ value }) => {
              let categoryName = this.findObject(
                this.option.column,
                "categoryName"
              );
              if (value) {
                getAppCategoryTree({ type: value }).then((res) => {
                  categoryName.dicData = res.data.data;
                });
              } else {
                categoryName.dicData = [];
              }
            },
          },
          {
            label: "应用类别",
            prop: "categoryName",
            search: true,
            display: false,
            type: "tree",
            dicData: [],
            span: 24,
            props: {
              label: "title",
              value: "id",
            },
          },
          {
            label: "应用描述",
            prop: "description",
            display: false,
          },
          {
            label: "是否上架",
            search: true,
            display: false,
            prop: "isGround",
            type: "switch",
            dicData: [
              {
                label: "是",
                value: 1,
              },
              {
                label: "否",
                value: 0,
              },
            ],
          },
          // {
          //   label: "是否推荐",
          //   prop: "isRecommend",
          //   type: "switch",
          //   dicData: [
          //     {
          //       label: "是",
          //       value: 1,
          //     },
          //     {
          //       label: "否",
          //       value: 0,
          //     },
          //   ],
          //   search: true,
          //   searchLabelWidth: 72,
          //   display: false,
          // },
        ],
        group: [
          {
            label: "基 础 信 息",
            prop: "jcxx",
            column: [
              {
                label: "应用名称",
                prop: "name",
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请输入应用名称",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "应用类型",
                prop: "type",
                hide: true,
                type: "select",
                dicData: [
                  { label: "已建应用", value: 1 },
                  { label: "企业产品", value: 2 },
                ],
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请选择应用类型",
                    trigger: "blur",
                  },
                ],
                change: ({ value }) => {
                  const deptId = this.findObject(this.option.group, "deptId"); // 所属部门
                  const entId = this.findObject(this.option.group, "entId"); // 所属公司
                  const url = this.findObject(this.option.group, "url"); // 应用URL
                  const logo = this.findObject(this.option.group, "logo"); //系统截图
                  const clsc = this.findObject(this.option.group, "clsc"); //材料上传
                  const categoryId = this.findObject(
                    this.option.group,
                    "categoryId"
                  ); //应用类别
                  if (value) {
                    this.form.categoryId = "";
                    getAppCategoryTree({ type: value }).then((res) => {
                      categoryId.dicData = res.data.data;
                    });
                    if (value == 2) {
                      this.$set(this.option.group[1], "display", false);
                      this.$set(this.option.group[2], "display", false);
                      deptId.display = false;
                      entId.display = true;
                      url.display = false;
                      logo.display = false;
                      clsc.display = true;
                    } else {
                      this.$set(this.option.group[1], "display", true);
                      this.$set(this.option.group[2], "display", true);
                      deptId.display = true;
                      entId.display = false;
                      url.display = true;
                      logo.display = true;
                      clsc.display = false;
                    }
                  }
                },
              },
              {
                label: "应用类别",
                prop: "categoryId",
                hide: true,
                type: "tree",
                dicData: [],
                span: 24,
                props: {
                  label: "title",
                  value: "id",
                },
                rules: [
                  {
                    required: true,
                    message: "请选择应用类别",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "应用描述",
                prop: "description",
                type: "textarea",
                span: 24,
                maxlength: 1000,
                minRows: 6,
                showWordLimit: true,
              },
              {
                label: "系统logo",
                prop: "icon",
                span: 24,
                formslot: true,
                rules: [
                  {
                    required: false,
                    message: "请上传系统logo",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "系统截图",
                prop: "logo",
                span: 24,
                formslot: true,
                rules: [
                  {
                    required: false,
                    message: "请上传系统截图",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "材料上传",
                prop: "clsc",
                span: 24,
                display: false,
                formslot: true,
              },
              {
                label: "所属部门",
                prop: "deptId",
                span: 24,
                type: "tree",
                dicData: [],
                props: {
                  label: "title",
                  value: "id",
                },
                rules: [
                  {
                    required: true,
                    message: "请选择所属部门",
                    trigger: "click",
                  },
                ],
              },
              {
                label: "所属公司",
                prop: "entId",
                span: 24,
                type: "select",
                display: false,
                dicUrl: "/api/szbh-back/bhent/getList",
                props: {
                  label: "entName",
                  value: "id",
                },
                rules: [
                  {
                    required: true,
                    message: "请选择所属公司",
                    trigger: "click",
                  },
                ],
              },
              {
                label: "应用URL",
                prop: "url",
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请输入应用URL",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "联系人",
                prop: "contacts",
                span: 24,
              },
              {
                label: "电话",
                prop: "telephone",
                span: 24,
              },
            ],
          },
          {
            label: "端 点 信 息",
            prop: "ddxx",
            // display: true,
            column: [
              {
                label: "App ID",
                prop: "appId",
                span: 24,
              },
              {
                label: "App Secret",
                prop: "appSecret",
                span: 24,
                formslot: true,
              },
            ],
          },
          {
            label: "应 用 配 置",
            prop: "yypz",
            column: [
              {
                label: "是否上架",
                prop: "isGround",
                type: "switch",
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
                value: 1,
              },
              // {
              //   label: "是否推荐",
              //   prop: "isRecommend",
              //   type: "switch",
              //   dicData: [
              //     {
              //       label: "否",
              //       value: 0,
              //     },
              //     {
              //       label: "是",
              //       value: 1,
              //     },
              //   ],
              //   value: 1,
              // },
              {
                label: "短信推送",
                prop: "isSendSms",
                type: "switch",
                span: 24,
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
                value: 1,
              },
              {
                label: "协议类型",
                prop: "protocol",
                type: "radio",
                span: 24,
                dicData: [
                  {
                    label: "OIDC",
                    value: "OIDC",
                  },
                  {
                    label: "OAuth 2.0",
                    value: "OAuth 2.0",
                  },
                ],
                rules: [
                  {
                    required: true,
                    message: "请选择协议类型",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "用户信息",
                prop: "userColumn",
                type: "checkbox",
                span: 24,
                dicData: [
                  {
                    label: "用户名称",
                    value: "用户名称",
                  },
                  {
                    label: "用户账号",
                    value: "用户账号",
                  },
                  {
                    label: "密码",
                    value: "密码",
                  },
                  {
                    label: "所属主部门",
                    value: "所属主部门",
                  },
                  {
                    label: "主部门内是否为上级",
                    value: "主部门内是否为上级",
                  },
                  {
                    label: "主部门职位",
                    value: "主部门职位",
                  },
                  {
                    label: "主部门手机",
                    value: "主部门手机",
                  },
                  {
                    label: "是否短信通知",
                    value: "是否短信通知",
                  },
                  {
                    label: "是否启用",
                    value: "是否启用",
                  },
                  {
                    label: "其他所属部门",
                    value: "其他所属部门",
                  },
                  {
                    label: "其他部门内是否为上级",
                    value: "其他部门内是否为上级",
                  },
                  {
                    label: "其他部门职位",
                    value: "其他部门职位",
                  },
                  {
                    label: "性别",
                    value: "性别",
                  },
                  {
                    label: "用户头像",
                    value: "用户头像",
                  },
                  {
                    label: "别名",
                    value: "别名",
                  },
                  {
                    label: "邮箱",
                    value: "邮箱",
                  },
                  {
                    label: "电话",
                    value: "电话",
                  },
                  {
                    label: "对外简称",
                    value: "对外简称",
                  },
                ],
              },
            ],
          },
        ],
      },
      data: [],
      fileList: [],
      type: "", // 弹窗类型
      fileList2: [],
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        //   addBtn: this.vaildData(
        //     this.permission.bhapplicationmanagement_add,
        //     false
        //   ),
        //   viewBtn: this.vaildData(
        //     this.permission.bhapplicationmanagement_view,
        //     false
        //   ),
        //   delBtn: this.vaildData(
        //     this.permission.bhapplicationmanagement_delete,
        //     false
        //   ),
        //   editBtn: this.vaildData(
        //     this.permission.bhapplicationmanagement_edit,
        //     false
        //   ),
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
    // getAppCategoryTree().then((res) => {
    //   var column1 = this.findObject(this.option.column, "categoryName");
    //   column1.dicData = res.data.data;
    // });
    getBhDeptTree().then((res) => {
      this.treeData = res.data.data;
      var column = this.findObject(this.option.group[0].column, "deptId");
      column.dicData =
        res.data.data.length > 0
          ? res.data.data
          : [
              {
                title: "无",
                id: "0",
              },
            ];
    });
  },
  methods: {
    // 刷新密钥
    // handleRefreshSecret() {
    //   getSecret().then((res) => {
    //     this.form.appSecret = res.data.data;
    //   });
    // },
    uploadSuccess(data) {
      this.fileList = data;
    },
    handleStop(row) {
      changeGround(row.id).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
        }
      });
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
      const extension = testmsg === "jpg";
      const extension2 = testmsg === "png";
      if (!extension && !extension2) {
        this.$message({
          message: "上传文件只能是 jpg、png!",
          type: "warning",
        });
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return (extension || extension2) && isLt2M;
    },
    handleRemove2(file, fileList) {
      if (fileList.length > 0) {
        this.fileList2 = fileList.map((item) => {
          if (item.response == undefined) {
            return item;
          } else {
            return item.response.data;
          }
        });
      } else {
        this.fileList2 = [];
      }
    },
    handleAvatarSuccess2(res, file) {
      let obj = {
        ...res,
        id: res.data.id,
        name: res.data.fileName,
        url: res.data.fileUrl,
      };
      this.fileList2.push(obj);
    },

    rowSave(row, done, loading) {
      row.userColumn = row.userColumn.toString();
      // console.log(this.fileList);
      // 系统截图
      if (this.fileList.length > 0) {
        let logoarr = this.fileList.map((item) => {
          return item.url;
        });
        let logoarr2 = this.fileList.map((item) => {
          return item.id;
        });
        row.logoId = logoarr2.toString();
        row.logo = logoarr.toString();
      } else {
        row.logoId = "";
        row.logo = "";
      }
      // 系统logo
      if (this.fileList2.length > 0) {
        let iconarr = this.fileList2.map((item) => {
          return item.url;
        });
        let iconarr2 = this.fileList2.map((item) => {
          return item.id;
        });
        row.iconId = iconarr2.toString();
        row.icon = iconarr.toString();
      } else {
        row.iconId = "";
        row.icon = "";
      }
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
      // console.log(this.fileList);
      // 系统截图
      if (this.fileList.length > 0) {
        let logoarr = this.fileList.map((item) => {
          return item.url;
        });
        let logoarr2 = this.fileList.map((item) => {
          return item.id;
        });
        row.logoId = logoarr2.toString();
        row.logo = logoarr.toString();
      } else {
        row.logo = "";
        row.logoId = "";
      }
      // 系统logo
      if (this.fileList2.length > 0) {
        let iconarr = this.fileList2.map((item) => {
          return item.url;
        });
        let iconarr2 = this.fileList2.map((item) => {
          return item.id;
        });
        row.iconId = iconarr2.toString();
        row.icon = iconarr.toString();
      } else {
        row.iconId = "";
        row.icon = "";
      }
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
      this.type = type;
      this.fileList = [];
      this.fileList2 = [];
      if (["edit", "view"].includes(type)) {
        getDetail(this.form.id).then((res) => {
          this.form = res.data.data;
          this.form.entId = String(this.form.entId);
          if (this.form.iconFiles.length > 0) {
            this.form.iconFiles.forEach((item) => {
              let obj = {
                ...item,
                name: item.fileName,
                url: item.fileUrl,
              };
              this.fileList2.push(obj);
            });
          }
          if (this.form.files.length > 0) {
            this.form.files.forEach((item) => {
              let obj = {
                ...item,
                name: item.fileName,
                url: item.fileUrl,
              };
              this.fileList.push(obj);
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
      this.data = [];
      this.query.categoryId = this.query.categoryName;
      delete this.query.categoryName;
      getList(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        if (data.records.length > 0) {
          data.records.map((item) => {
            this.data.push({
              ...item,
              appId: item.appId || "-",
            });
          });
        }
        this.loading = false;
        this.selectionClear();
      });
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/.el-icon-zoom-in:before {
  content: " ";
}
</style>