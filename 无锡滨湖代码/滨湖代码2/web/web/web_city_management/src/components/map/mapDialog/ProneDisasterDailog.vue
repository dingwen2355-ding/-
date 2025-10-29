<template>
  <el-dialog
    title="易灾点"
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
          <span>名称：</span>
          <span>{{ detailInfo.name }}</span>
        </li>
        <li>
          <span>地址：</span>
          <span>{{ detailInfo.area }}{{ detailInfo.township }}{{ detailInfo.village }}</span>
        </li>
        <li>
          <span>规模：</span>
          <span>{{ detailInfo.scale }}</span>
        </li>
        <li>
          <span>险情级别：</span>
          <span>{{ detailInfo.dangerLevel }}</span>
        </li>
        <li>
          <span>监测员姓名：</span>
          <span>{{ detailInfo.monitorUsername }}</span>
        </li>
        <li>
          <span>>监测员电话：</span>
          <span>{{ detailInfo.monitorPhone }}</span>
        </li>
        <li>
          <span>防灾责任人姓名：</span>
          <span>{{ detailInfo.preventUsername }}</span>
        </li>
        <li>
          <span>防灾责任人电话：</span>
          <span>{{ detailInfo.preventPhone }}</span>
        </li>
        <li>
          <span>监测责任人姓名：</span>
          <span>{{ detailInfo.monitorResponsibleUsername }}</span>
        </li>
        <li>
          <span>监测责任人电话：</span>
          <span>{{ detailInfo.monitorResponsiblePhone }}</span>
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
