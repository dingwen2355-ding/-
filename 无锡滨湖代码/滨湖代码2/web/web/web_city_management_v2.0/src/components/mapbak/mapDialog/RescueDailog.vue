<template>
  <el-dialog
    title="应急救援队伍"
    :visible.sync="dialogVisible"
    append-to-body
    :modal="false"
    width="1431rem"
    height="900rem"
    class="CoalMineDailog  default-dialog-class large-screen"
    :before-close="handleClose"
  >
    <div class="dd-title-back">
      <div class="dd-title-back-s"></div>
    </div>
    <div class="dd-back-area top-right-area"></div>
    <div class="dd-back-angle top-left-angle"></div>
    <div class="dd-back-angle top-right-angle"></div>
    <div class="dd-back-angle bottom-left-angle"></div>
    <div class="dd-back-angle bottom-right-angle"></div>
    <div class="box-1">
      <ul>
        <li>
          <span>应急救援队伍名称：</span>
          <span>{{ detailInfo.TEAM_NAME }}</span>
        </li>
        <li>
          <span>所属单位：</span>
          <span>{{ detailInfo.BELONG_UNIT }}</span>
        </li>
        <li>
          <span>主攻方向：</span>
          <span>{{ detailInfo.SPECIALITIES }}</span>
        </li>
        <li>
          <span>现有人数：</span>
          <span>{{ detailInfo.member_number }}</span>
        </li>
        <li>
          <span>联系人：</span>
          <span>{{ detailInfo.MANAGER }}</span>
        </li>
        <li>
          <span>联系人手机：</span>
          <span>{{ detailInfo.MANAGER_PHONE }}</span>
        </li>
        <li>
          <span>固定电话：</span>
          <span>{{ detailInfo.DUTY_PHONE }}</span>
        </li>
      </ul>
    </div>
  </el-dialog>
</template>

<script>
export default {
  components: {},
  props: ['dialogVisibleFlag', 'infomation'],
  watch: {
    dialogVisibleFlag(v) {
      this.dialogVisible = v
      this.detailInfo = this.infomation
    }
  },
  data() {
    return {
      dialogVisible: false,
      detailInfo: {}
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.detailInfo = {}
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss" scoped>
  .CoalMineDailog {
    ::v-deep .el-dialog{
      margin-top: 25vh !important;
      width: 1600rem !important;
      height: 1280rem !important;
    }
    .box-1 {
      ul {
        display: flex;
        flex-wrap: wrap;

        li {
          width: 100%;
          margin-bottom: 24rem;
          margin-left: 10%;

          span {
            font-size: 80rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            line-height: 80rem;
            color: #fff;

            &:nth-of-type(1) {
              color: #3df8f8;
            }

            &:nth-of-type(2) {
              color: #ffffff;
            }
          }
        }
      }
    }
  }
</style>
