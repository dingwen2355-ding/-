<template>
  <el-dialog
    title="实例预约使用时间"
    :visible.sync="dialogVisible"
    width="28%"
    custom-class="d_form"
    :before-close="backConfirm"
  >
    <h1>您已成功预约可视预览</h1>
    <div style="text-align: center">{{ example }}</div>
    <div class="detail">
      <p>{{ reserveInfo.ruletime }}</p>
      <p>{{ formatDate(reserveInfo.ruledate) }}</p>
      <p>请在该时间段内操作</p>
    </div>
    <span slot="footer">
      <el-button size="small" @click="handleCancelReserve">取消预约</el-button>
      <el-button size="small" @click="handleReschedule">重新预约</el-button>
      <el-button size="small" type="primary" @click="backConfirm"
        >关 闭</el-button
      >
    </span>
  </el-dialog>
</template>
<script>
import { get, generalPostEvent } from "@/utils/fetch";
import moment from "moment";
export default {
  props: ["reserveInfo"],
  data() {
    return {
      dialogVisible: true,
      example: "",
    };
  },
  created() {
    this.getReserveInfo();
  },
  methods: {
    // 提示：取消预约
    handleCancelReserve() {
      let str = `取消${this.formatDate(this.reserveInfo.ruledate)} ${
        this.reserveInfo.ruletime
      }时间段的预约`;
      const h = this.$createElement;
      this.$msgbox({
        title: "取消预约",
        message: h("div", null, [
          h("p", { style: "color: #65708A;" }, str),
          h(
            "p",
            { style: "color: #FD3B31; margin-top: 10px;" },
            "1、取消后该时间段将不预览"
          ),
        ]),
        showCancelButton: true,
        confirmButtonText: "确认",
        cancelButtonText: "取消",
      })
        .then(() => {
          this.cancelReserve();
          this.backConfirm();
        })
        .catch((err) => {});
    },
    // 取消预约
    async cancelReserve() {
      await generalPostEvent(`${cim_loginServer}freedo/user/setuserrule`, {
        instanceid: this.reserveInfo.instanceid,
        ruledate: this.reserveInfo.ruledate,
      }).then((res) => {
        if (res.message === "1") {
          this.$message.success("已取消");
          localStorage.removeItem("CIM_account_appoint");
        }
      });
    },
    async handleReschedule() {
      this.$parent.ruletime = [];
      this.$parent.reserveFlag = true;
      await this.cancelReserve();
      this.handleClickClose(true);
    },
    formatDate(date) {
      return moment(date).format("YYYY年MM月DD日");
    },
    getReserveInfo() {
      get(`${cim_main}service/instance/list`).then((res) => {
        let list = res.message.filter(
          (item) => item.id === this.reserveInfo.instanceid
        );
        this.example = list.length ? list[0].name.replace("\n", "") : "";
      });
    },
    backConfirm() {
      this.handleClickClose();
    },
    handleClickClose(val = false, obj) {
      this.$emit("handleClickButton", val, obj);
    },
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/css/reset-dialog.scss";
.d_form {
  h1 {
    font-size: 32px;
    font-weight: 500;
    color: #000000;
    text-align: center;
  }
}
.detail {
  background: #e2ecfb;
  border-radius: 6px;
  width: 80%;
  margin: 30px auto 60px auto;
  padding: 46px 0 54px 0;
  & > p {
    font-weight: 500;
    color: #000000;
    text-align: center;
    &:nth-child(1) {
      font-size: 20px;
    }
    &:nth-child(2) {
      font-size: 22px;
      padding: 10px 0;
    }
    &:nth-child(3) {
      font-size: 14px;
    }
  }
}
</style>
