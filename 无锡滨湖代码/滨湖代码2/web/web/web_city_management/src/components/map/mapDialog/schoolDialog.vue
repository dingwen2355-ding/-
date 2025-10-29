<template>
  <el-dialog
    title="学校"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1300rem"
    height="900rem"
    class="HospitalDialog"
    :before-close="handleClose"
  >
    <div class="box-1">
      <ul>
        <li>
          <span>名称：</span>
          <span>{{ detailInfo.XXMC }}</span>
        </li>
        <li>
          <span>所属居委：</span>
          <span>{{ detailInfo.CJ }}</span>
        </li>
        <li>
          <span>地址：</span>
          <span>{{ detailInfo.JGDZ }}</span>
        </li>
        <li>
          <span>负责人：</span>
          <span>{{ detailInfo.XZFZRXM }}</span>
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
  .HospitalDialog {
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
