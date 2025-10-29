<template>
  <basic-container style="height: 800px">
    <el-page-header
      style="width: 180px"
      @click.native="returnBack"
      content="搜索应用"
    >
    </el-page-header>
    <div class="search-tool">
      <div class="search-ipt">
        <el-input
          size="small"
          placeholder="请输入应用名称"
          prefix-icon="el-icon-search"
          v-model="inputAppName"
        >
        </el-input>
      </div>
      <div style="margin-left: 16px">
        <el-button
          icon="el-icon-search"
          type="primary"
          size="small"
          @click="onLoad"
          >搜 索</el-button
        >
        <el-button icon="el-icon-delete" size="small" @click="toReset"
          >清 空</el-button
        >
      </div>
    </div>
    <div style="display: flex; align-items: baseline; margin-top: 20px">
      <div style="width: 90px">应用分类：</div>
      <el-radio-group v-model="checkList1" style="width: 90%" @input="onLoad">
        <el-radio
          :label="item.id"
          v-for="item in categoryList"
          :key="item.id"
          >{{ item.name }}</el-radio
        >
      </el-radio-group>
    </div>
    <div style="display: flex; align-items: baseline; margin-top: 20px">
      <div style="width: 90px">应用单位：</div>
      <el-radio-group v-model="checkList2" style="width: 90%" @input="onLoad">
        <el-radio
          :label="item.deptId"
          v-for="item in deptList"
          :key="item.deptId"
          >{{ item.name }}</el-radio
        >
      </el-radio-group>
    </div>
    <div style="display: flex; align-items: baseline; margin: 20px 0">
      <div style="width: 90px">授权状态：</div>
      <el-radio-group v-model="checkList3" style="width: 90%" @input="onLoad">
        <el-radio :label="item.id" v-for="item in stateList" :key="item.id">{{
          item.name
        }}</el-radio>
      </el-radio-group>
    </div>
    <el-divider></el-divider>
    <div v-loading="loading">
      <div class="dataCount">
        <el-card class="box-card" v-for="item in data" :key="item.name">
          <div class="each-data">
            <div class="left">
              <!-- <el-image :src="item.icon" style="width: 100%; height: 100px"> -->
              <el-image :src="item.icon" fit="contain">
                <div slot="error" class="image-slot">
                  <i
                    class="el-icon-picture-outline"
                    style="font-size: 22px"
                  ></i>
                </div>
              </el-image>
            </div>
            <div class="right">
              <div style="font-weight: 600">{{ item.name }}</div>
              <div
                style="
                  width: 100%;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                  overflow: hidden;
                  color: #999;
                  margin: 10px 0 15px 0;
                "
                :title="item.description"
              >
                {{ item.description || "-" }}
              </div>
              <div>应用单位：{{ item.manageDeptName }}</div>
              <div class="btn-con">
                <el-link
                  v-if="item.isAuthorization == 2"
                  type="primary"
                  :underline="false"
                  @click="toApp(item)"
                  >访问应用 ></el-link
                >
                <el-link
                  v-else-if="item.isAuthorization == 1"
                  type="primary"
                  :underline="false"
                  >审核中</el-link
                >
                <el-link
                  v-else
                  type="primary"
                  :underline="false"
                  @click="opendialog(item)"
                  >权限申请</el-link
                >
              </div>
            </div>
          </div>
        </el-card>
      </div>
      <el-pagination
        v-if="data.length > 0"
        class="page"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.currentPage"
        :page-size="page.pageSize"
        background
        layout="total, prev, pager, next, jumper"
        :total="page.total"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="权限申请"
      :visible.sync="dialogVisible"
      width="30%"
      append-to-body
      @closed="closed"
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="130px">
        <el-form-item label="申请理由" prop="reason">
          <el-input type="textarea" v-model="form.reason"></el-input>
        </el-form-item>
        <el-form-item label="申请人手机号" prop="applicantPhone">
          <el-input v-model="form.applicantPhone"></el-input>
        </el-form-item>
        <el-form-item label="申请人名称" prop="applicantName">
          <el-input v-model="form.applicantName"></el-input>
        </el-form-item>
        <el-form-item label="申请人单位名称" prop="deptName">
          <el-input v-model="form.deptName"></el-input>
        </el-form-item>
        <el-form-item label="单位类型" prop="applicantDeptType">
          <el-select
            v-model="form.applicantDeptType"
            placeholder="请选择单位类型"
          >
            <el-option
              v-for="item in deptTypeOptions"
              :key="item.dictKey"
              :label="item.dictValue"
              :value="item.dictKey"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="handleSave"
          >保 存</el-button
        >
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </basic-container>
</template>

<script>
import {
  getUserDept,
  getUserApplicationCategory,
  applicationByClassificationAndDept,
  submit,
  getDeptType,
  getUserInfo,
} from "@/api/front/front";
export default {
  data() {
    return {
      inputAppName: "",
      checkList1: "",
      categoryList: [],
      deptList: [],
      checkList2: "",
      checkList3: "",
      stateList: [
        { id: 0, name: "未授权" },
        { id: 1, name: "审核中" },
        { id: 2, name: "已授权" },
      ],
      data: [],
      page: {
        pageSize: 9,
        currentPage: 1,
        total: 0,
      },
      loading: false,
      dialogVisible: false,
      form: {
        reason: "",
        applicantPhone: "",
        applicantName: "",
        deptName: "",
        applicantDeptType: "",
      },
      rules: {
        applicantDeptType: [
          { required: true, message: "请选择单位类型", trigger: "change" },
        ],
        reason: [
          { required: true, message: "请填写申请理由", trigger: "blur" },
        ],
        applicantPhone: [
          { required: true, message: "请填写申请人手机号", trigger: "blur" },
        ],
        applicantName: [
          { required: true, message: "请填写申请人名称", trigger: "blur" },
        ],
        deptName: [
          { required: true, message: "请填写申请人单位名称", trigger: "blur" },
        ],
      },
      deptTypeOptions: [],
    };
  },
  mounted() {
    getUserApplicationCategory().then((res) => {
      this.categoryList = res.data.data;
    });
    getUserDept().then((res) => {
      this.deptList = res.data.data;
    });
    getDeptType().then((res) => {
      this.deptTypeOptions = res.data.data;
    });
    this.onLoad();
  },
  methods: {
    toReset() {
      this.inputAppName = "";
      this.checkList1 = "";
      this.checkList2 = "";
      this.checkList3 = "";
      this.onLoad();
    },
    toApp(data) {
      // this.$router.push({ path: "/webUri" });
      let webUri = data.webUri;
      if (webUri == "http://2.21.137.206:11511/binhudsj") {
        webUri = "http://2.21.137.206:11511/api/login";
      }
      window.open(webUri);
    },
    closed() {
      this.$refs['form'].resetFields();
    },
    handleSave() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.form.reviewStatus = 1;
          this.form.account = this.form.applicant = JSON.parse(
            localStorage.getItem("saber-userInfo")
          ).content.account;
          this.form.applicationManagementId = this.form.id;
          delete this.form.id;
          submit(this.form).then((res) => {
            if (res.data.success) {
              this.$message.success("操作成功！");
              this.dialogVisible = false;
              this.onLoad();
            }
          });
        } else {
          return false;
        }
      });
    },
    opendialog(row) {
      getUserInfo().then((res) => {
        let data = res.data.data;
        this.form = { ...row, ...this.form };
        console.log(this.form);
        this.form.applicantPhone = data.phone;
        this.form.applicantName = data.userName;
        this.form.deptName = data.deptName;
      });
      this.dialogVisible = true;
    },
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.page.currentPage = 1;
      this.onLoad();
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.onLoad();
    },
    onLoad() {
      this.loading = true;
      let params = {
        applicationClassificationId: this.checkList1,
        deptId: this.checkList2,
        isAuthorization: this.checkList3,
        current: this.page.currentPage,
        size: this.page.pageSize,
        appName: this.inputAppName,
      };
      applicationByClassificationAndDept(params).then((res) => {
        const { data } = res.data;
        this.page.total = data.total;
        this.data = data.records;
        this.loading = false;
      });
    },
    returnBack() {
      this.$emit("returnBack");
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.el-card__body {
  position: relative;
}
/deep/.el-radio {
  margin-bottom: 10px;
}
.dataCount {
  // display: flex;
  // flex-wrap: wrap;
  // width: 100%;
  // margin-top: 20px;
  // padding: 10px 15px 0 32px;
  display: flex;
  // justify-content: space-between;
  flex-wrap: wrap;
  max-height: 450px;
  padding-bottom: 10px;
  overflow-y: scroll;
  .box-card {
    // padding: 15px;
    // width: 32.5%;
    // box-sizing: border-box;
    // margin: 0 10px 20px 0px;
    height: 130px;
    flex: 0 0 31%;
    margin-left: calc(2% / 2);
    margin-right: calc(2% / 2);
    margin-bottom: calc(2% / 2);
    padding: 15px;
    // &:nth-child(3n) {
    //   margin-right: 0;
    // }
    /deep/.el-card__body {
      padding: 0;
    }
    .each-data {
      font-weight: 400;
      font-size: 14px;
      display: flex;
      align-items: center;
      justify-content: space-between;

      .left {
        width: 20%;
      }
      .right {
        width: 76%;
        position: relative;
        .btn-con {
          position: absolute;
          right: 4%;
          top: 0px;
        }
      }
    }
  }
  /* 在屏幕宽度小于1400px时，一行显示两个元素 */
  @media (max-width: 1400px) {
    .box-card {
      height: 130px;
      flex: 0 0 45%;
      margin-left: calc(2% / 2);
      margin-right: calc(2% / 2);
      margin-bottom: calc(2% / 2);
      padding: 15px;
    }
  }
  /* 在屏幕宽度小于950px时，一行显示一个元素 */
  @media (max-width: 950px) {
    .box-card {
      height: 130px;
      flex: 0 0 95%;
      margin-left: calc(2% / 2);
      margin-right: calc(2% / 2);
      margin-bottom: calc(2% / 2);
      padding: 15px;
    }
  }
}

.search-tool {
  display: flex;
  position: absolute;
  top: 16px;
  left: 220px;

  .el-button--primary {
    background-color: #0f71b7;
    border-color: #0f71b7;
  }
  .el-button {
    height: 32px;
  }
  /deep/.el-input__inner:focus {
    border-color: #0f71b7;
  }
}
/deep/.el-radio__input.is-checked .el-radio__inner {
  background-color: #0f71b7;
  border-color: #0f71b7;
}
/deep/.el-radio__input.is-checked + .el-radio__label {
  color: #0f71b7;
}
/deep/.el-link.el-link--primary {
  color: #0f71b7;
}
/deep/.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #0f71b7;
}
.page {
  // position: absolute;
  // right: 50px;
  // top: 730px;
  text-align: right;
}
* {
  box-sizing: border-box;
}
</style>