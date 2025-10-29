<template>
  <el-dialog
    title="水库"
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
          <span>水库名称：</span>
          <span>{{ detailInfo.resrevoirName }}</span>
        </li>
        <li>
          <span>县级：</span>
          <span>{{ detailInfo.area }}</span>
        </li>
        <li>
          <span>水库类型：</span>
          <span>{{ detailInfo.resrevoirType }}</span>
        </li>
        <li>
          <span>管理部门：</span>
          <span>{{ detailInfo.unit }}</span>
        </li>
        <li>
          <span>兴利库容：</span>
          <span>{{ detailInfo.irrigationCapacity }}</span>
        </li>
        <li>
          <span>总库容：</span>
          <span>{{ detailInfo.totalCapacity }}</span>
        </li>
        <li>
          <span>防洪库容：</span>
          <span>{{ detailInfo.floodCapacity }}</span>
        </li>
        <li>
          <span>供水能力：</span>
          <span>{{ detailInfo.waterSupply }}</span>
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
