<template>
  <div :key="randomKey" style="height: 100%">
    <div v-if="!announceDetailShow">
      <el-row>
        <el-col :span="13">
          <basic-container class="module" style="position: relative">
            <div class="module-title">我的应用</div>
            <div class="for-right">
              <el-input
                placeholder="输入搜索关键词"
                size="small"
                v-model="searchIpt"
                v-if="searchShow"
                clearable
              >
                <el-button
                  size="small"
                  slot="append"
                  icon="el-icon-search"
                  @click="getUserApp"
                ></el-button>
              </el-input>
            </div>
            <div class="right-btn">
              <i class="el-icon-search" @click="searchShow = !searchShow"></i>
              <el-link
                :underline="false"
                style="margin-top: -5px; margin-left: 5px"
                v-if="!isAdmin"
                @click="toAll"
                >全部</el-link
              >
            </div>
            <div>
              <draggable
                v-model="AppData"
                animation="300"
                :options="{
                  sort: true,
                }"
                dragClass="pointer"
                class="dataCount"
                @update="draggableClick"
              >
                <div
                  v-for="item in AppData"
                  :key="item.name"
                  class="each-data"
                  @click="handleTo(item)"
                >
                  <div class="left"><img :src="item.icon" /></div>
                  <div class="right">
                    <span>{{ item.name }}</span>
                    <i class="el-icon-rank"></i>
                  </div>
                  <template v-if="item.isNew == 1">
                    <img src="img/bg/new.png" class="new-img" />
                  </template>
                </div>
              </draggable>
            </div>
          </basic-container>
        </el-col>
        <el-col :span="11">
          <basic-container class="module">
            <div class="module-title">当月访问最多排行</div>
            <el-table :data="tableData" style="margin-top: 10px" height="270">
              <el-table-column label="排名" width="80" align="center">
                <template slot-scope="{ $index }">
                  <span class="ranking" :class="$index < 3 ? 'active' : ''">
                    {{ $index + 1 }}
                  </span>
                </template>
              </el-table-column>
              <el-table-column
                prop="name"
                label="应用名称"
                align="center"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column prop="number" label="访问次数" align="center">
              </el-table-column>
              <el-table-column label="占比" align="center" width="150">
                <template slot-scope="{ row, $index }">
                  <el-progress
                    v-if="$index == 0"
                    :percentage="row.percent"
                    color="#f96163"
                  ></el-progress>
                  <el-progress
                    v-else-if="$index == 1"
                    :percentage="row.percent"
                    color="#fda853"
                  ></el-progress>
                  <el-progress
                    v-else-if="$index == 2"
                    :percentage="row.percent"
                    color="#51cecf"
                  ></el-progress>
                  <el-progress v-else :percentage="row.percent"></el-progress>
                </template>
              </el-table-column>
            </el-table>
          </basic-container>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="13">
          <basic-container class="module" style="position: relative">
            <div class="right-btn" @click="toAnnounceCenter">
              <el-link :underline="false">查看更多</el-link>
            </div>
            <el-tabs v-model="activeName" @tab-click="handleClick">
              <el-tab-pane label="通知公告" name="2"></el-tab-pane>
              <el-tab-pane label="服务指南" name="1"></el-tab-pane>
            </el-tabs>
            <div style="height: 350px">
              <el-table
                class="bottom-table"
                :data="notices"
                style="width: 100%"
                height="350"
                @row-click="clickRow"
              >
                <el-table-column
                  label="标题"
                  prop="title"
                  show-overflow-tooltip
                >
                </el-table-column>
                <el-table-column
                  label="来源"
                  prop="infoSource"
                  show-overflow-tooltip
                >
                </el-table-column>
                <el-table-column label="时间" prop="createTime" width="150">
                </el-table-column>
              </el-table>
            </div>
          </basic-container>
        </el-col>
        <el-col :span="11">
          <basic-container class="module">
            <div class="module-title">我的访问统计</div>
            <div class="time-choose">
              <el-radio-group
                v-model="radio"
                size="small"
                @input="handleRadioChange"
              >
                <el-radio-button :label="3">最近一周</el-radio-button>
                <el-radio-button :label="4">最近一月</el-radio-button>
              </el-radio-group>
            </div>
            <div class="chart_box">
              <e-chart :options="chartOption" class="charts" id="charts" />
            </div>
          </basic-container>
        </el-col>
      </el-row>
      <el-dialog
        title="初次密码修改"
        :visible.sync="dialogVisible"
        width="1020px"
        append-to-body
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
      >
        <!-- <el-dialog
        title="初次密码修改"
        :visible="true"
        width="750px"
        append-to-body
      > -->
        <el-steps :active="active" finish-status="success">
          <el-step title="初次登录"> </el-step>
          <el-step title="修改初始密码"></el-step>
          <el-step title="完成"></el-step>
        </el-steps>
        <div v-if="active == 1" style="padding: 30px 20px">
          <div style="margin: 20px 0; color: orange">
            您好，为了您的账号安全，请点击下一步修改初始密码。初始密码为Szbh@2024。
          </div>
          <div style="display: flex; align-items: center">
            <div style="width: 100px">登录账号：</div>
            <el-input
              placeholder="请输入内容"
              v-model="account"
              size="small"
              :disabled="true"
            >
            </el-input>
          </div>
        </div>
        <div v-else-if="active == 2" style="padding: 30px 20px">
          <el-form
            :model="ruleForm"
            status-icon
            :rules="rules"
            ref="ruleForm"
            label-width="110px"
          >
            <el-form-item label="原密码：" prop="oldPassword">
              <el-input
                v-model="ruleForm.oldPassword"
                clearable
                size="small"
              ></el-input>
            </el-form-item>
            <el-form-item label="新密码：" prop="newPassword">
              <el-input
                size="small"
                clearable
                autocomplete="off"
                v-model="ruleForm.newPassword"
                placeholder="请输入8位以上，至少包含大小写字母、数字、特殊符号（~!@#$%^&*）三种以上，不允许出现连续数字、字母，不允许包含账户名"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认新密码：" prop="checkPass">
              <el-input
                size="small"
                clearable
                v-model="ruleForm.checkPass"
                autocomplete="off"
                placeholder="请输入8位以上，至少包含大小写字母、数字、特殊符号（~!@#$%^&*）三种以上，不允许出现连续数字、字母，不允许包含账户名"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div v-else-if="active == 3" style="padding: 30px 20px">
          密码修改成功
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button
            style="margin-top: 12px"
            size="small"
            @click="back"
            v-if="active == 2"
            >上一步</el-button
          >
          <el-button style="margin-top: 12px" size="small" @click="next">{{
            btnText
          }}</el-button>
        </div>
      </el-dialog>
    </div>
    <basic-container v-else style="height: 100%">
      <announceDetail :row="row" @returnBack="returnBack"></announceDetail>
    </basic-container>
  </div>
</template>

<script>
import {
  getUserApp,
  getNoticeList,
  getPasswordType,
  updatePassword,
  getUserRecord,
  submitOrder,
} from "@/api/front/front";
import { getAppRecordRank } from "@/api/home";
import { getUserRole, getSsoLogoutUri } from "@/api/common";
import draggable from "vuedraggable";
import bus from "@/views/front/components/bus.js";
import announceDetail from "./announceDetail.vue";
import { validatePassword } from "@/util/validate";

export default {
  components: { draggable, announceDetail },
  props: {
    randomKey: {
      type: Number,
      default: 0,
    },
  },
  watch: {
    randomKey(val) {
      this.announceDetailShow = false;
    },
  },
  data() {
    const passwordRegex =
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~!@#$%^&*]).{8,}$/;
    const regexText =
      "请输入8位以上，至少包含大小写字母、数字、特殊符号（~!@#$%^&*）三种以上，不允许出现连续数字、字母，不允许包含账户名";
    const validatePass = (rule, value, callback) => {
      if (!value || value === "") {
        callback(new Error("请输入密码"));
      } else if (!validatePassword(value)) {
        callback(new Error(regexText));
      } else {
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (!value || value === "") {
        callback(new Error("请再次输入密码"));
      } else if (!validatePassword(value)) {
        callback(new Error(regexText));
      } else if (value != this.ruleForm.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      announceDetailShow: false,
      row: {},
      searchIpt: "",
      activeName: "2",
      AppData: [],
      isAdmin: false,
      notices: [],
      dialogVisible: false,
      active: 1,
      btnText: "下一步",
      account: "",
      ruleForm: {},
      rules: {
        oldPassword: [
          { required: true, message: "请输入原密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, validator: validatePass, trigger: "blur" },
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: "blur" },
        ],
      },
      tableData: [],
      radio: 3,
      chartOption: {},
      searchShow: false,
    };
  },
  mounted() {
    this.account = JSON.parse(
      localStorage.getItem("saber-userInfo")
    ).content.account;
    this.getUserApp();
    getUserRole().then((res) => {
      // 0是管理员 1是普通用户
      this.isAdmin = res.data.data == 0 ? true : false;
      if (!this.isAdmin) {
        getPasswordType().then((res) => {
          this.dialogVisible = res.data.data == 0 ? true : false;
        });
      }
    });
    getAppRecordRank().then((res) => {
      this.tableData = res.data.data;
    });
    this.getNoticeList();
    this.getEchartsData();

    this.resizefun = () => {
      this.$echarts.init(document.getElementById("charts")).resize();
    };
    window.addEventListener("resize", this.resizefun);
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.resizefun);
    this.resizefun = null;
  },
  methods: {
    handleTo(data) {
      // console.log(data);
      let webUri = data.webUri;
      if (webUri == "http://2.21.137.206:11511/binhudsj") {
          webUri = "http://2.21.137.206:11511/api/login";
      } else if (webUri == "https://2.21.138.89:11511/binhudsj") {
          webUri = "https://2.21.138.89:11511/api/login";
      }
      //页面有操作设置平台过期时间
      this.$store.dispatch("UpdateAppExpiryTime");
      window.open(webUri);
    },
    clickRow(row, column, event) {
      // console.log(row, column, event);
      this.row = row;
      this.announceDetailShow = true;
    },
    getUserApp() {
      getUserApp({ name: this.searchIpt }).then((res) => {
        this.AppData = res.data.data;
      });
    },
    toAll() {
      this.$emit("toAll", true);
    },
    toAnnounceCenter() {
      bus.$emit("toAnnounce", 2);
    },
    draggableClick() {
      this.AppData.forEach((item, index) => {
        item.appOrder = index + 1;
      });
      submitOrder(this.AppData).then((res) => {
        this.$message.success("保存成功");
      });
    },
    getEchartsData() {
      this.chartOption = {
        title: {
          show: true,
          text: "总计",
          left: "34%",
          top: "42%",
          subtext: "0",
          textAlign: "center",
          textStyle: {
            fontSize: 14,
            fontStyle: "normal",
            fontWeight: "normal",
          },
          subtextStyle: {
            fontSize: 20,
            color: "black",
            fontStyle: "normal",
            fontWeight: "bolder",
          },
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          type: "scroll",
          top: "20%",
          left: "66%",
          orient: "vertical",
        },
        series: [
          {
            name: "",
            type: "pie",
            radius: ["40%", "60%"],
            center: ["35%", "50%"],
            avoidLabelOverlap: false,
            labelLine: {
              show: false,
              length: 5,
              length2: 0,
            },
            label: {
              show: false,
              position: "center",
            },
            data: [],
          },
        ],
      };
      getUserRecord(this.radio).then((res) => {
        this.chartOption.series[0].data = res.data.data;
        let total = 0;
        res.data.data.forEach((item) => {
          total += item.value;
          this.chartOption.title.subtext = total;
        });
        this.chartOption.legend.formatter = function (name) {
          //图例后添加数值
          let data = res.data.data;
          let tarValue;
          for (let i = 0; i < data.length; i++) {
            if (data[i].name === name) {
              tarValue = data[i].value;
            }
          }
          return name + ": " + tarValue;
        };
      });
    },
    handleRadioChange() {
      this.getEchartsData();
    },
    handleClick() {
      this.getNoticeList();
    },
    getNoticeList() {
      getNoticeList({ noticeType: this.activeName }).then((res) => {
        this.notices = res.data.data.records;
      });
    },
    next() {
      if (this.active == 2) {
        this.$refs.ruleForm.validate(async (valid) => {
          if (valid) {
            await updatePassword(this.ruleForm).then((res) => {
              this.active++;
              if (this.active > 2) {
                this.btnText = "重新登录";
              }
            });
          }
        });
      } else {
        this.active++;
        if (this.active > 2) {
          this.btnText = "重新登录";
        }
      }
      if (this.active == 4) {
        this.$store.dispatch("LogOut").then(() => {
          window.sessionStorage.removeItem("sideBar");
          getSsoLogoutUri().then((res) => {
            window.location.href = res.data.data;
          });
        });
      }
    },
    back() {
      this.active--;
    },
    returnBack() {
      this.announceDetailShow = false;
    },
  },
};
</script>

<style lang="scss" scoped>
/deep/.el-table::before {
  height: 0;
}
.for-right {
  width: 22%;
  position: absolute;
  right: 100px;
  top: 18px;
}
.right-btn {
  position: absolute;
  right: 30px;
  top: 25px;
  z-index: 99;
}
/deep/.el-tabs__active-bar {
  background-color: #0070b9;
}
/deep/.el-tabs__item {
  font-weight: 600;
  font-size: 16px;
  letter-spacing: 2px;
}
.module-title {
  font-weight: 600;
  letter-spacing: 2px;
}
/deep/.el-table th.el-table__cell {
  background: #0070b9;
  color: #fff;
  font-weight: 600;
}
.ranking {
  display: inline-block;
  color: #fff;
  background: #ccc;
  font-weight: 700;
  height: 24px;
  width: 24px;
  text-align: center;
  line-height: 24px;
  border-radius: 3px;
}
.ranking.active {
  background: #ffa82f;
}
/deep/.el-progress__text {
  margin-left: 20px;
}
.dataCount {
  display: flex;
  flex-wrap: wrap;
  height: 260px;
  overflow: scroll;
  width: 100%;
  margin-top: 20px;
  .each-data {
    border: solid 1px #dcdcdc;
    border-radius: 5px;
    width: 430px;
    height: 64px;
    width: calc(33% - 15px);
    margin: 0 15px 15px 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #fff;
    position: relative;
    .left {
      width: 64px;
      height: 64px;
      overflow: hidden;
      img {
        width: 64px;
        height: 64px;
        border-radius: 5px;
        float: left;
      }
    }
    .right {
      flex: 1;
      padding: 0 10px;
      font-size: 14px;
      display: flex;
      align-items: center;
      span {
        font-size: 14px;
        flex: 1;
        margin-right: 10px;
        overflow: hidden;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
      }
      i {
        font-size: 20px;
        color: #ccc;
      }
    }
    .new-img {
      position: absolute;
      top: 2px;
      right: 2px;
    }
  }
  /* 在屏幕宽度小于1400px时，一行显示两个元素 */
  @media (max-width: 1400px) {
    .each-data {
      width: calc(50% - 15px);
    }
  }
  /* 在屏幕宽度小于950px时，一行显示一个元素 */
  @media (max-width: 950px) {
    .each-data {
      width: 100%;
    }
  }
}
/deep/.el-radio-button__orig-radio:checked + .el-radio-button__inner {
  background-color: #0070b9;
  border-color: #0070b9;
}
/deep/.el-tabs__item.is-active {
  color: #0070b9;
}
.time-choose {
  float: right;
  margin-top: -25px;
}
.bottom-table {
  /deep/.el-table__header-wrapper {
    display: none !important;
  }
}
// /deep/.el-card__body {
//   overflow: scroll;
// }
.chart_box {
  height: 360px;
  margin-top: 20px;
  .charts {
    height: 350px !important;
  }
}
* {
  box-sizing: border-box;
}
</style>
