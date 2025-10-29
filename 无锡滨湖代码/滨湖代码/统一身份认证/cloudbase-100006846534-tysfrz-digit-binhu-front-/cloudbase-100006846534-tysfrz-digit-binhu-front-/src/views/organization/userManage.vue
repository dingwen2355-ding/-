<template>
  <el-row>
    <el-col :span="5">
      <basic-container>
        <div class="box" :style="{ height: height }" :key="randomKey1">
          <div class="treeBtn">
            <!-- <el-link v-if="isOpen" :underline="false" @click="openAll(true)">
              <i style="font-size: 20px" class="el-icon-caret-bottom"></i>
            </el-link>
            <el-link :underline="false" v-else @click="openAll(false)">
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
          :permission="permissionList"
          :before-open="beforeOpen"
          :row-key="getRowKey"
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
              type="primary"
              icon="el-icon-plus"
              size="small"
              @click="$refs.crud.rowAdd()"
              >新增用户</el-button
            >
            <el-button
              type="info"
              size="small"
              plain
              v-if="permission.user_reset"
              icon="el-icon-refresh"
              @click="handleReset"
              >密码重置
            </el-button>

            <el-button
              type="danger"
              size="small"
              icon="el-icon-delete"
              @click="handleDelete"
              >删 除
            </el-button>
            <el-button
              type="warning"
              size="small"
              plain
              icon="el-icon-download"
              @click="handleExport"
              >导 出</el-button
            >
            <el-button
              type="success"
              size="small"
              plain
              icon="el-icon-upload2"
              @click="handleImport"
              >导入
            </el-button>
            <!-- <el-upload
              class="upload-demo"
              action="/squirrel-szbh/bhuser/import"
              :headers="headers"
              :file-list="fileList"
              :before-upload="beforeUpload"
              :show-file-list="false"
              :on-success="handleSuccess"
              :on-error="handleError"
              name="multipartFile"
              :data="importData"
            >
              <el-button
                type="success"
                size="small"
                plain
                icon="el-icon-upload"
                @click="handleImport"
                >导 入</el-button
              >
            </el-upload> -->
          </template>
          <template slot="avatarForm">
            <el-upload
              class="avatar-uploader"
              action="/api/squirrel-szbh/file/uploadImg"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              :disabled="viewFlag"
              :headers="headers"
              name="multipartFile"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            <div>图片上传格式为：jpg、png且大小不能超过2MB</div>
          </template>

          <template slot="isEnable" slot-scope="scope">
            <span
              :style="{ color: scope.row.isEnable == 1 ? 'green' : 'red' }"
              >{{ scope.row.isEnableName }}</span
            >
          </template>
          <template slot="majorMobile" slot-scope="scope">
            <span
              :style="{ color: scope.row.majorMobile == '手机号码篡改' ? 'red' : 'black' }"
              >{{ scope.row.majorMobile }}</span
            >
          </template>
          <template slot="isOnline" slot-scope="scope">
            <span
              :style="{ color: scope.row.isOnline == 1 ? 'green' : 'red' }"
              >{{ scope.row.isOnlineName }}</span
            >
          </template>
          <template slot="isAuthorized" slot-scope="scope">
            <span
              :style="{ color: scope.row.isAuthorized == 1 ? 'green' : 'red' }"
              >{{ scope.row.isAuthorizedName }}</span
            >
          </template>
          <template slot-scope="{ row, index }" slot="menu">
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
            <el-dropdown size="small" style="margin-left: 10px">
              <el-button type="primary" plain size="mini">
                更 多<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <!-- <router-link tag="li" to="/organization/loginLog">
                    登录日志
                  </router-link> -->
                  <div @click="toLoginLog(row)">登录日志</div>
                </el-dropdown-item>
                <el-dropdown-item
                  @click.native="handleStop(row)"
                  v-if="row.isEnable == 1"
                >
                  停用
                </el-dropdown-item>
                <el-dropdown-item @click.native="$refs.crud.rowDel(row, index)">
                  删除
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
          <template slot="morePositionForm">
            <div style="display: flex" v-if="!viewFlag">
              <avue-input-tree
                :key="randomKey"
                style="width: 30%"
                leaf-only
                ref="tree"
                v-model="form.otherDeptTemp"
                placeholder="请选择部门"
                :dic="deptDic"
                :props="{ label: 'title', value: 'id' }"
                @change="handleChangeMoreDept"
              ></avue-input-tree>
              <el-input
                style="width: 400px"
                v-model="form.otherPositionTemp"
                placeholder="请输入职务"
                size="small"
              ></el-input>
              <i
                class="el-icon-plus"
                style="margin: 7px 0 0 8px"
                @click="toShowMorePosition"
              ></i>
            </div>
            <div>
              <el-input
                size="small"
                v-for="(item, index) in morePositionInput"
                :key="index"
                v-model="item.positionData"
                disabled
              >
                <template slot="append" v-if="!viewFlag">
                  <i
                    class="el-icon-delete"
                    @click="toDeleteMorePosition(item, index)"
                  ></i>
                </template>
              </el-input>
            </div>
          </template>
          <template slot="accountForm">
            <el-input
              v-model="form.account"
              placeholder="请输入 登录账号"
              size="small"
            ></el-input>
            <div style="color: #004fd8">初始密码为Szbh@2024</div>
          </template>
          <template slot="yysqForm">
            <div style="height: 300px; padding-top: 3px">
              <el-scrollbar>
                <el-tree
                  :data="authorizeTreeData"
                  default-expand-all
                  ref="authorizeTree"
                  :highlight-current="true"
                  :check-on-click-node="true"
                  show-checkbox
                  node-key="id"
                  :props="{ value: 'id', label: 'title' }"
                  :default-checked-keys="defaultChecked"
                >
                </el-tree>
              </el-scrollbar>
            </div>
          </template>
        </avue-crud>
        <el-dialog
          title="导 入"
          append-to-body
          :visible.sync="excelBox"
          width="555px"
        >
          <avue-form
            :option="excelOption"
            v-model="excelForm"
            :upload-after="uploadAfter"
            :upload-before="beforeUpload"
          >
            <template slot="excelTemplate">
              <el-button type="primary" @click="handleTemplate">
                点击下载
              </el-button>
            </template>
          </avue-form>
        </el-dialog>
      </basic-container>
    </el-col>
  </el-row>
</template>

<script>
import {
  getList,
  getDetail,
  add,
  update,
  remove,
  getBhDeptTree,
  changePassword,
  changeEnable,
  getUserAppCategoryTree,
  getDeptList,
} from "@/api/organization/user";
import { getToken } from "@/util/auth";
import { mapGetters } from "vuex";
import { headers } from "@/api/tool/datasource";
export default {
  data() {
    return {
      randomKey1: 0,
      height: "",
      headers: headers(), // 自定义上传空间需要的权限
      filterText: "",
      dialogTreeData: [],
      form: { otherDeptTemp: "", otherPositionTemp: "" },
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
        calcHeight: 70,
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
        reserveSelection: true,
        dialogClickModal: false,
        dialogWidth: "35%",
        columnBtn: false,
        align: "center",
        labelWidth: 120,
        menuWidth: 260,
        stripe: true,
        column: [
          {
            label: "姓名/账户",
            prop: "code",
            display: false,
            hide: true,
            search: true,
            // searchLabelWidth: 80,
          },

          {
            label: "姓名",
            prop: "name",
            display: false,
          },
          {
            label: "登录账号",
            prop: "account",
            display: false,
          },
          {
            label: "主岗部门",
            prop: "bhDeptName",
            display: false,
          },
          {
            label: "职务",
            prop: "majorPosition",
            display: false,
            width: 150,
          },
          {
            label: "账号状态",
            prop: "isEnable",
            search: true,
            display: false,
            type: "radio",
            dicData: [
              {
                label: "启用",
                value: 1,
              },
              {
                label: "停用",
                value: 0,
              },
            ],
          },
          {
            label: "最后登录开始时间",
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
            label: "最后登录结束时间",
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
            label: "手机",
            prop: "majorMobile",
            display: false,
            width: 100,
          },
          {
            label: "在线状态",
            prop: "isOnline",
            search: true,
            display: false,
            type: "radio",
            dicData: [
              {
                label: "在线",
                value: 1,
              },
              {
                label: "离线",
                value: 0,
              },
            ],
            // searchLabelWidth: 72,
          },
          // {
          //   label: "配置状态",
          //   prop: "isAuthorized",
          //   search: true,
          //   display: false,
          //   type: "radio",
          //   dicData: [
          //     {
          //       label: "已配置",
          //       value: 1,
          //     },
          //     {
          //       label: "未配置",
          //       value: 0,
          //     },
          //   ],
          // },
          {
            label: "最后登录",
            prop: "lastLoginTime",
            display: false,
          },
        ],
        group: [
          {
            label: "基 础 信 息",
            prop: "jcxx",
            column: [
              {
                label: "用户头像",
                prop: "avatar",
                formslot: true,
                span: 24,
              },
              {
                label: "登录账号",
                prop: "account",
                span: 24,
                formslot: true,
                rules: [
                  {
                    required: true,
                    message: "请输入登录账号",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "姓名",
                prop: "name",
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请输入姓名",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "性别",
                prop: "gender",
                span: 24,
                type: "radio",
                value: 1,
                dicData: [
                  {
                    label: "男",
                    value: 1,
                  },
                  {
                    label: "女",
                    value: 2,
                  },
                ],
              },
              {
                label: "主岗部门",
                prop: "majorDept",
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
                    message: "请选择主岗部门",
                    trigger: "click",
                  },
                ],
              },
              {
                label: "职务",
                prop: "majorPosition",
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请输入职务",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "手机号码",
                prop: "majorMobile",
                span: 24,
                rules: [
                  {
                    required: true,
                    message: "请输入手机号码",
                    trigger: "blur",
                  },
                ],
              },
              {
                label: "短信通知",
                prop: "isSendSms",
                span: 24,
                type: "switch",
                dicData: [
                  {
                    label: "关",
                    value: 0,
                  },
                  {
                    label: "开",
                    value: 1,
                  },
                ],
                value: 1,
              },
              {
                label: "账号状态",
                prop: "isEnable",
                type: "radio",
                span: 24,
                value: 1,
                dicData: [
                  {
                    label: "启用",
                    value: 1,
                  },
                  {
                    label: "停用",
                    value: 0,
                  },
                ],
              },
              {
                label: "更多部门/职务",
                prop: "morePosition",
                span: 24,
                formslot: true,
              },
            ],
          },
          {
            label: "详 细 信 息",
            prop: "xxxx",
            column: [
              {
                label: "别名",
                prop: "englishName",
                span: 24,
              },
              {
                label: "邮箱",
                prop: "email",
                span: 24,
              },
              {
                label: "电话",
                prop: "telephone",
                span: 24,
              },
              // {
              //   label: "是否上级",
              //   prop: "isLeaderInDept",
              //   type: "radio",
              //   span: 24,
              //   dicData: [
              //     {
              //       label: "上级",
              //       value: 1,
              //     },
              //     {
              //       label: "非上级",
              //       value: 0,
              //     },
              //   ],
              // },
              {
                label: "对外简称",
                prop: "externalCorpName",
                span: 24,
              },
            ],
          },
          {
            label: "用 户 授 权",
            prop: "yhsq",
            display: false,
            column: [
              {
                label: "应用授权",
                prop: "yysq",
                span: 24,
                formslot: true,
              },
            ],
          },
        ],
      },
      data: [],
      treeData: [],
      treeOption: {
        nodeKey: "id",
        addBtn: false,
        defaultExpandAll: false,
        menu: false,
        size: "small",
        props: {
          labelText: "标题",
          label: "title",
          value: "id",
          children: "children",
        },
      },
      treeDeptId: "",
      imageUrl: "",
      viewFlag: false,
      authorizeTreeData: [],
      deptDic: [],
      defaultChecked: [], //保存授权弹窗回显的应用数据
      morePositionInput: [], //保存添加的更多部门和职务
      randomKey: 0,
      importData: { deptId: "" },
      excelBox: false,
      excelForm: {},
      excelOption: {
        submitBtn: false,
        emptyBtn: false,
        column: [
          {
            label: "模板上传",
            prop: "excelFile",
            type: "upload",
            data: { deptId: "" },
            drag: true,
            loadText: "模板上传中，请稍等",
            showFileList: false,
            span: 24,
            propsHttp: {
              fileName: "multipartFile",
              res: "data",
            },
            tip: "请上传 .xls,.xlsx 标准格式文件",
            action: "/api/squirrel-szbh/bhuser/import",
          },
          {
            label: "模板下载",
            prop: "excelTemplate",
            formslot: true,
            span: 24,
          },
        ],
      },
      isOpen: true,
    };
  },
  computed: {
    ...mapGetters(["permission"]),
    permissionList() {
      return {
        // addBtn: this.vaildData(this.permission.bhuser_add, false),
        // viewBtn: this.vaildData(this.permission.bhuser_view, false),
        // delBtn: this.vaildData(this.permission.bhuser_delete, false),
        // editBtn: this.vaildData(this.permission.bhuser_edit, false),
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
  watch: {
    filterText(val) {
      this.$refs.deptTree.filter(val);
    },
  },
  mounted() {
    getBhDeptTree().then((res) => {
      this.treeData = res.data.data;
      var column = this.findObject(this.option.group[0].column, "majorDept");
      column.dicData = res.data.data;
      this.deptDic = res.data.data;
    });
  },
  methods: {
    toLoginLog(e) {
      window.localStorage.setItem("loginLogAccount", e.account);
      this.$router.push({
        path: "/organization/loginLog",
        // query: {
        //   extra: 1,
        // },
      });
    },
    openAll(data) {
      this.$set(this.treeOption, "defaultExpandAll", data);
      this.isOpen = !data;
      this.randomKey1 = Math.random();
    },
    // 模板下载
    handleTemplate() {
      let str = `api/squirrel-szbh/bhuser/downTemplate?${
        this.website.tokenHeader
      }=${getToken()}`;
      window.open(str);
    },

    // 导入成功后
    uploadAfter(res, done, loading) {
      done();
      this.excelBox = false;
      this.onLoad(this.page, this.query);
    },

    beforeUpload(file, done, loading) {
      var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
      const extension = testmsg === "xls";
      const extension2 = testmsg === "xlsx";
      if (!extension && !extension2) {
        this.$message({
          message: "请上传 .xls,.xlsx 标准格式文件!",
          type: "warning",
        });
        loading();
      } else {
        done();
      }
    },
    handleImport() {
      if (this.treeDeptId == "") {
        this.$message.warning("请先选择部门！");
      } else {
        this.excelBox = true;
        this.excelOption.column[0].data = { deptId: this.treeDeptId };
        console.log(this.excelOption.column[0]);
      }
    },
    handleChangeMoreDept() {
      if (!this.form.majorDept) {
        if (this.form.otherDeptTemp) {
          this.$message.warning("请先选择主岗部门！");
          this.$set(this.form, "otherDeptTemp", "");
          this.randomKey = Math.random();
        }
      } else {
        if (this.form.majorDept == this.form.otherDeptTemp) {
          this.$message.warning("请选择不同于主岗部门的数据！");
          this.$set(this.form, "otherDeptTemp", "");
          this.randomKey = Math.random();
        }
      }
    },
    toShowMorePosition() {
      let treeData = this.$refs.tree.node[0]; // 新增、编辑选中的部门树数据
      // console.log(treeData);
      this.morePositionInput.push({
        positionData:
          treeData.title + " / " + (this.form.otherPositionTemp || ""), // 弹窗表单里显示的
        ...this.form,
        ...treeData,
      });
      this.form.otherDeptTemp = "";
      this.form.otherPositionTemp = "";
      this.randomKey = Math.random();
    },
    toDeleteMorePosition(data, index) {
      this.morePositionInput.splice(index, 1);
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

    // 封装获取部门职务及对应的授权数据的方法
    async handleGetDeptAppData(id) {
      // 应用树
      await getUserAppCategoryTree("", 2).then((res) => {
        let authorizeTreeData = res.data.data;
        this.addDisabled(authorizeTreeData);
        this.authorizeTreeData = authorizeTreeData;
      });
      await getDeptList(id).then((res) => {
        const { data } = res.data;
        if (data.length == 0) {
          this.flag = false;
        } else {
          this.flag = true;
          this.defaultChecked = data.map((item) => {
            return item.id;
          });
        }
      });
    },
    addDisabled(list) {
      for (let i = 0, len = list.length; i < len; i++) {
        //list[i].key中的key是树的唯一标识。
        list[i].disabled = true;
        if (list[i].children && list[i].children.length) {
          this.addDisabled(list[i].children);
        }
      }
    },

    // 停用事件
    handleStop(row) {
      changeEnable(row.id).then((res) => {
        if (res.data.success) {
          this.$message.success("操作成功！");
          this.onLoad(this.page, this.query);
        }
      });
    },
    handleReset() {
      if (this.selectionList.length === 0) {
        this.$message.warning("请选择至少一条数据");
        return;
      }
      this.$confirm("确定将选择账号密码重置为Szbh@2024?", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return changePassword(this.ids);
        })
        .then(() => {
          this.$message({
            type: "success",
            message: "操作成功!",
          });
          this.$refs.crud.toggleSelection();
        });
    },

    handleExport() {
      this.$confirm("是否导出表格数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let ids = this.selectionList.length > 0 ? this.ids : "";
        let str = `api/squirrel-szbh/bhuser/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&bhDeptId=${this.treeDeptId}&code=${
          this.query.code
        }&end=${this.query.end}&start=${this.query.start}&isAuthorized=${
          this.query.isAuthorized
        }&isEnable=${this.query.isEnable}&isOnline=${
          this.query.isOnline
        }&ids=${ids}`;
        window.open(str);
        this.$refs.crud.toggleSelection();
      });
    },
    handleAvatarSuccess(res, file) {
      // console.log(res.data.fileUrl);
      this.imageUrl = res.data.fileUrl;
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
    nodeClick(data) {
      console.log(data, "data");
      this.treeDeptId = data.id;
      this.page.currentPage = 1;
      this.onLoad(this.page);
    },
    rowSave(row, done, loading) {
      // console.log(this.morePositionInput, 11);
      row.avatar = this.imageUrl;
      if (this.morePositionInput.length > 0) {
        let deptArr = this.morePositionInput.map((item) => {
          return item.id;
        });
        let positonArr = this.morePositionInput.map((item) => {
          return item.otherPositionTemp;
        });
        row.otherDept = deptArr.toString();
        row.otherPosition = positonArr.toString();
      } else {
        row.otherDept = "";
        row.otherPosition = "";
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
      // console.log(this.morePositionInput, 11);
      // console.log(this.imageUrl, 22);
      row.avatar = this.imageUrl;
      if (this.morePositionInput.length > 0) {
        let deptArr = this.morePositionInput.map((item) => {
          return item.id;
        });
        let positonArr = this.morePositionInput.map((item) => {
          return item.otherPositionTemp;
        });
        row.otherDept = deptArr.toString();
        row.otherPosition = positonArr.toString();
      } else {
        row.otherDept = "";
        row.otherPosition = "";
      }
      row.id = this.form.id;
      // console.log(row, 22);
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
      this.defaultChecked = [];
      this.morePositionInput = [];
      this.viewFlag = type == "view" ? true : false;
      this.imageUrl = "";
      if (type == "view") {
        this.$set(this.option.group[2], "display", true);
        this.handleGetDeptAppData(this.form.id);
      } else {
        this.$set(this.option.group[2], "display", false);
      }
      if (["edit", "view"].includes(type)) {
        getDetail(this.form.id).then((res) => {
          this.form = res.data.data;
          this.imageUrl = this.form.avatar;
          if (res.data.data.deptPositions.length > 0) {
            res.data.data.deptPositions.forEach((item) => {
              this.morePositionInput.push({
                id: item.deptId,
                otherPositionTemp: item.position,
                positionData: item.deptName + " / " + item.position,
              });
            });
          }
        });
      }
      done();
    },
    searchReset() {
      getBhDeptTree().then((res) => {
        this.treeData = res.data.data;
        var column = this.findObject(this.option.group[0].column, "majorDept");
        column.dicData = res.data.data;
        this.deptDic = res.data.data;
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
    selectionChange(list) {
      this.selectionList = list;
    },
    getRowKey(row) {
      return row.id;
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
        this.treeDeptId,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
        this.height = `${this.$refs.container.$el.clientHeight - 50}px`;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.box {
  /deep/.avue-tree__filter {
    position: fixed;
    z-index: 99;
    width: 11%;
    margin-bottom: 0;
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

/deep/.el-radio-button__inner {
  border-left: 1px solid #dcdfe6;
}

* {
  box-sizing: border-box;
}
</style>
<style lang='scss'>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #0e54d6;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 150px;
  height: 150px;
  display: block;
}
.avue-crud {
  .el-dropdown-link {
    cursor: pointer;
    color: #0e54d6;
    font-size: 12px;
  }
  .el-icon-arrow-down {
    font-size: 12px;
  }
}
</style>
