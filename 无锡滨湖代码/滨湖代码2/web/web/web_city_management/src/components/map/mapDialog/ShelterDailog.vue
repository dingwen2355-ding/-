<template>
  <el-dialog
    title="避难场所"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1431rem"
    height="900rem"
    class="ShelterDailog  default-dialog-class large-screen"
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
          <span>名称：</span>
          <span>{{ detailInfo.shelterName }}</span>
        </li>
        <li>
          <span>地址：</span>
          <span>{{ detailInfo.shelterAddress }}</span>
        </li>
        <li>
          <span>面积：</span>
          <span>{{ detailInfo.acerage }}</span>
        </li>
        <li>
          <span>容纳避难人数（人）：</span>
          <span>{{ detailInfo.capacity }}</span>
        </li>
<!--        <li>
          <span>分管领导：</span>
          <span>{{ detailInfo.shelterPerson }}</span>
        </li>
        <li>
          <span>分管领导联系方式：</span>
          <span>{{ detailInfo.shelterPhone }}</span>
        </li>-->
        <li>
          <span>负责人：</span>
          <span>{{ detailInfo.shelterPerson }}</span>
        </li>
        <li>
          <span>负责人联系方式：</span>
          <span>{{ detailInfo.shelterPhone }}</span>
        </li>
      </ul>
    </div>
  </el-dialog>
</template>

<script>
// import { getCoordinatesDetails } from '@/api/info.js'

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
      detailInfo: {},
      nodata: ''
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
  .ShelterDailog {
    ::v-deep .el-dialog{
      width: 2912rem !important;
      height: 1304rem !important;
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
            font-size: 64rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            line-height: 64rem;
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
