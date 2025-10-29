<template>
  <el-dialog
    title="医疗机构"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1431rem"
    height="900rem"
    class="CoalMineDailog"
    :before-close="handleClose"
  >
    <div class="box-1">
      <ul>
        <li>
          <span>机构名称：</span>
          <span>{{ detailInfo.institutionName }}</span>
        </li>
        <li>
          <span>详细地址：</span>
          <span>{{ detailInfo.address }}</span>
        </li>
        <li>
          <span>单位级别：</span>
          <span>{{ detailInfo.unitLevel }}</span>
        </li>
        <li>
          <span>病床数：</span>
          <span>{{ detailInfo.bedNum }}</span>
        </li>
        <li>
          <span>医生数：</span>
          <span>{{ detailInfo.doctorNum }}</span>
        </li>
        <li>
          <span>护士数：</span>
          <span>{{ detailInfo.nurseNum }}</span>
        </li>
        <li>
          <span>联系人：</span>
          <span>{{ detailInfo.legalPersonName }}</span>
        </li>
        <li>
          <span>联系人电话：</span>
          <span>{{ detailInfo.phone }}</span>
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

<style lang="scss">
  .CoalMineDailog {
    .box-1 {
      ul {
        display: flex;
        flex-wrap: wrap;

        li {
          width: 50%;
          margin-bottom: 24rem;

          span {
            font-size: 24rem;
            font-family: Microsoft YaHei;
            font-weight: 400;
            line-height: 40rem;
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
