<template>
  <el-dialog
    title="应急专家"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1300rem"
    height="650rem"
    class="specialDailog default-dialog-class large-screen"
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
          <span>姓名：</span>
          <span>{{ infomation.name }}</span>
        </li>
        <li>
          <span>性别：</span>
          <span>{{ infomation.sex === '0'?'男':'女' }}</span>
        </li>
        <li>
          <span>出生年月：</span>
          <span>{{ infomation.birthday }}</span>
        </li>
        <li>
          <span>工作单位：</span>
          <span>{{ infomation.company }}</span>
        </li>
        <li>
          <span>职称：</span>
          <span>{{ infomation.title }}</span>
        </li>
        <li>
          <span>从事专业：</span>
          <span>{{ infomation.experience }}</span>
        </li>
        <li>
          <span>联系电话：</span>
          <span>{{ infomation.phone }}</span>
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
    }
  },
  data() {
    return {
      dialogVisible: false,
      level: '',
      district: '',
      detailedAddress: '',
      latitude: '',
      contact: '',
      name: '',
      keepLiablePerson: '',
      contactPhone: '',
      keepLiablePersonPhone: '',
      longitude: ''
    }
  },
  methods: {
    handleClose() {
      this.dialogVisible = false
      this.$emit('update:dialogVisibleFlag', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.specialDailog {
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
        margin-left: 10%;
        margin-bottom: 24rem;
        display: flex;
        padding-right: 20rem;
        span {
          font-size: 64rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          line-height: 64rem;
          color: #fff;
          &:nth-of-type(1) {
            color: #3df8f8;
            white-space: nowrap;
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
