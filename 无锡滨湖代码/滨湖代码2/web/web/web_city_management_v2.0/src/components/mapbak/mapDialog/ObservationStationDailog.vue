<template>
  <el-dialog
    title="水雨情监测站"
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
          <span>站名：</span>
          <span>{{ detailInfo.stationName }}</span>
        </li>
        <li>
          <span>站点类型：</span>
          <span>{{ detailInfo.stationType }}</span>
        </li>
        <li>
          <span>地址：</span>
          <span>{{ detailInfo.address }}</span>
        </li>
        <li>
          <span>水系：</span>
          <span>{{ detailInfo.drainage }}</span>
        </li>
        <li>
          <span>河名：</span>
          <span>{{ detailInfo.riverName }}</span>
        </li>
        <li>
          <span>高程系统：</span>
          <span>{{ detailInfo.heightSystems }}</span>
        </li>
        <li>
          <span>集水面积：</span>
          <span>{{ detailInfo.collectWaterArea }}</span>
        </li>
        <li>
          <span>河口距离：</span>
          <span>{{ detailInfo.distance }}</span>
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
