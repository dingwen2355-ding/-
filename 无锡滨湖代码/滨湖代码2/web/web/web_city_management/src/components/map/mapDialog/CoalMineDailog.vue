<template>
  <el-dialog
    title="煤矿企业"
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
          <span>煤矿名称：</span>
          <span>{{ detailInfo.name }}</span>
        </li>
        <li>
          <span>县市区：</span>
          <span>{{ detailInfo.area }}</span>
        </li>
        <li>
          <span>海拔：</span>
          <span>{{ detailInfo.seaLevel }}</span>
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
