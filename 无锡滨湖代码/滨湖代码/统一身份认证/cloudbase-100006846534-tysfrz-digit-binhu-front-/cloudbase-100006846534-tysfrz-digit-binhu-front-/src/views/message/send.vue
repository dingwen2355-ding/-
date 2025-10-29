<template>
  <basic-container>
    <avue-crud
      :option="option"
      :table-loading="loading"
      :data="data"
      :page.sync="page"
      :before-open="beforeOpen"
      v-model="form"
      ref="crud"
      @row-update="rowUpdate"
      @row-save="rowSave"
      @row-del="rowDel"
      @search-change="searchChange"
      @search-reset="searchReset"
      @current-change="currentChange"
      @size-change="sizeChange"
      @refresh-change="refreshChange"
      @on-load="onLoad"
    >
      <template slot="timeForm">
        <el-time-select
          arrow-control
          size="small"
          v-model="form.regularTime"
          :picker-options="{
            start: '00:00',
            step: '00:10',
            end: '23:59',
          }"
          placeholder="任意时间点"
        >
        </el-time-select>
      </template>
      <template slot="testMsgForm">
        <el-input v-model="form.testMsg" placeholder="请输入 内容"></el-input>
        <el-button type="primary" plain size="mini" @click="sendTestMsg"
          >发送到测试手机</el-button
        >
      </template>
      <template slot-scope="{ row, index }" slot="menu">
        <div v-if="row.isRegularTime == 1">
          <el-button
            type="primary"
            plain
            size="mini"
            @click="$refs.crud.rowEdit(row, index)"
            >编 辑</el-button
          >
          <el-button
            type="danger"
            plain
            size="mini"
            @click="$refs.crud.rowDel(row, index)"
            >删 除</el-button
          >
        </div>
        <div v-if="row.isRegularTime == 0">
          <el-button
            type="success"
            plain
            size="mini"
            @click="openViewDialog(row, index)"
            >明 细</el-button
          >
          <el-button type="warning" plain size="mini" @click="handleExport(row)"
            >导 出</el-button
          >
        </div>
      </template>
    </avue-crud>
    <el-dialog
      title="短信发送明细"
      :visible.sync="dialogVisible"
      width="36%"
      append-to-body
    >
      <avue-crud
        class="dialog-table"
        :option="option2"
        :table-loading="loading2"
        :data="data2"
        :page.sync="page2"
        ref="dialogCrud"
        @current-change="currentChange2"
        @size-change="sizeChange2"
      ></avue-crud>
    </el-dialog>
  </basic-container>
</template>

<script>
import {
  sendSms,
  detail,
  add,
  update,
  remove,
  testSend,
} from "@/api/message/message";
import { getLoginUserPhone } from "@/api/message/send";
import { getToken } from "@/util/auth";
export default {
  data() {
    var validateMobile = (rule, value, callback) => {
      if (!/^(1[3-9]\d{9})(,(1[3-9]\d{9}))*$/.test(value)) {
        callback(new Error("请输入正确手机号并以英文逗号隔开"));
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
      option: {
        height: "auto",
        calcHeight: 10,
        tip: false,
        searchShow: true,
        searchMenuSpan: 6,
        border: true,
        index: true,
        menu: true,
        viewBtn: false,
        addBtn: true,
        delBtn: false,
        editBtn: false,
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        align: "center",
        stripe: true,
        dialogWidth: "35%",
        searchLabelWidth: 72,
        column: [
          {
            label: "短信标题",
            prop: "title",
            search: true,
            span: 24,
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
          },
          {
            label: "接收方式",
            prop: "receiveWay",
            type: "select",
            hide: true,
            clearable: false,
            dicData: [
              {
                label: "发送至已登录用户手机号",
                value: 1,
              },
              {
                label: "自定义接收号码",
                value: 2,
              },
            ],
            rules: [
              {
                required: true,
                message: "请选择接收方式",
                trigger: "blur",
              },
            ],
            change: ({ value }) => {
              if (value === 1) {
                this.getPhone();
              } else {
                this.form.mobile = "";
              }
            },
          },
          {
            label: "接收号码",
            prop: "mobile",
            span: 24,
            type: "textarea",
            placeholder: "请输入正确手机号并以英文逗号隔开",
            hide: true,
            rules: [
              {
                required: true,
                validator: validateMobile,
                trigger: "blur",
              },
            ],
          },
          {
            label: "短信类型",
            prop: "isRegularTime",
            search: true,
            type: "select",
            value: 1,
            dicData: [
              {
                label: "定时",
                value: 1,
              },
              {
                label: "即时",
                value: 0,
              },
            ],
            change: ({ value, column }) => {
              var time = this.findObject(this.option.column, "time");
              time.display = value == 1 ? true : false;
            },
            rules: [
              {
                required: true,
                message: "请选择短信类型",
                trigger: "change",
              },
            ],
          },
          {
            label: "",
            prop: "time",
            display: false,
            hide: true,
            formslot: true,
            rules: [
              {
                required: true,
                message: "请选择时间",
                trigger: "change",
              },
            ],
          },
          {
            label: "发送内容",
            prop: "content",
            span: 24,
            type: "textarea",
            rules: [
              {
                required: true,
                message: "请输入发送内容",
                trigger: "blur",
              },
            ],
          },
          {
            label: "定时发送时间",
            prop: "regularTime",
            display: false,
          },
          {
            label: "即时发送时间",
            prop: "createTime",
            display: false,
            hide: false,
          },
          {
            label: "发送状态",
            prop: "sendStatus",
            search: true,
            type: "select",
            display: false,
            dicData: [
              {
                label: "待发送",
                value: 0,
              },
              {
                label: "已发送",
                value: 1,
              },
            ],
          },
          {
            label: "号码总数",
            prop: "mobileSum",
            display: false,
          },
          {
            label: "发送成功次数",
            prop: "succeedMobileSum",
            display: false,
          },
          // {
          //   label: "操作员",
          //   prop: "userName",
          //   display: false,
          // },
          {
            label: "测试手机号",
            prop: "testPhone",
            span: 24,
            hide: true,
            // rules: [
            //   {
            //     validator: validateMobile,
            //     trigger: "blur",
            //   },
            // ],
          },
          {
            label: "测试短信",
            prop: "testMsg",
            hide: true,
            formslot: true,
            span: 24,
          },
        ],
      },
      data: [],
      dialogVisible: false,
      loading2: true,
      page2: {
        pageSize: 10,
        currentPage: 1,
        total: 0,
      },
      option2: {
        height: "auto",
        calcHeight: 150,
        tip: false,
        searchShow: false,
        searchMenuSpan: 6,
        border: true,
        index: true,
        menu: false,
        viewBtn: false,
        addBtn: false,
        delBtn: false,
        editBtn: false,
        selection: false,
        dialogClickModal: false,
        columnBtn: false,
        refreshBtn: false,
        align: "center",
        stripe: true,
        column: [
          {
            label: "手机号码",
            prop: "mobile",
          },
          {
            label: "号码来源",
            prop: "hmly",
          },
          {
            label: "用户名称",
            prop: "noticePersonName",
          },

          {
            label: "发送状态",
            prop: "resultName",
          },
        ],
      },
      data2: [],
      tableId: "",
    };
  },

  methods: {
    getPhone() {
      getLoginUserPhone().then((res) => {
        const data = res.data.data;
        this.form.mobile = data;
      });
    },
    sendTestMsg() {
      testSend(this.form.testMsg, this.form.testPhone).then((res) => {});
    },
    handleExport(row) {
      this.$confirm("是否导出报表数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        let str = `api/szbh-back/smssendsetting/downLoad?${
          this.website.tokenHeader
        }=${getToken()}&id=${row.id}`;
        window.open(str);
      });
    },
    openViewDialog(row) {
      this.tableId = row.id;
      this.getDetailTable();
      this.dialogVisible = true;
    },
    currentChange2(currentPage) {
      this.page2.currentPage = currentPage;
      this.getDetailTable();
    },
    sizeChange2(pageSize) {
      this.page2.pageSize = pageSize;
      this.page2.currentPage = 1;
      this.getDetailTable();
    },
    getDetailTable() {
      this.loading2 = true;
      this.data2 = [];
      detail(this.page2.currentPage, this.page2.pageSize, this.tableId).then(
        (res) => {
          const data = res.data.data;
          if (data.records.length > 0) {
            data.records.forEach((item) => {
              let obj = {
                ...item,
                resultName: item.result == 0 ? "失败" : "成功",
                noticePersonName:
                  item.noticePerson == "" ? "-" : item.noticePerson,
                hmly: item.noticePerson == "" ? "手动添加" : "系统用户",
              };
              this.data2.push(obj);
            });
          }
          this.page2.total = data.total;

          this.loading2 = false;
        }
      );
    },
    beforeOpen(done, type) {
      // this.type = type;
      // if (["edit", "view"].includes(type)) {
      //   getDetail(this.form.id).then((res) => {
      //     this.form = res.data.data;
      //   });
      // }
      done();
    },
    rowSave(row, done, loading) {
      // console.log(row);
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
      row.createTime = row.createTime == "-" ? "" : row.createTime;
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
      sendSms(
        page.currentPage,
        page.pageSize,
        Object.assign(params, this.query)
      ).then((res) => {
        const data = res.data.data;
        this.page.total = data.total;
        if (data.records.length > 0) {
          data.records.forEach((item) => {
            let obj = {
              ...item,
              regularTime: item.isRegularTime == 1 ? item.regularTime : "-",
              createTime: item.isRegularTime == 0 ? item.createTime : "-",
            };
            this.data.push(obj);
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
.dialog-table {
  /deep/.avue-crud__menu {
    display: none;
  }
}
</style>