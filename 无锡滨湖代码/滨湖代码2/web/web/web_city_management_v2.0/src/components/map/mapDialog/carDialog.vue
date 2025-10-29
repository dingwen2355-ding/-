<template>
  <el-dialog
    :title="infomation.clickType"
    :visible.sync="dialogVisible"
    :modal="false"
    width="1300rem"
    height="650rem"
    class="fireDailog"
    :before-close="handleClose"
  >
    <div class="box-1">
      <ul>
        <li>
          <span>车牌：</span>
          <span>{{ infomation.license }}</span>
        </li>
        <li>
          <span>所在地址：</span>
          <span>{{ infomation.address }}</span>
        </li>
        <li>
          <span>所属部门：</span>
          <span>{{ infomation.dept }}</span>
        </li>
        <li>
          <span>时间：</span>
          <span>{{ infomation.time }}</span>
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

<style lang="scss">
.fireDailog {
  .box-1 {
    ul {
      display: flex;
      flex-wrap: wrap;
      li {
        width: 50%;
        margin-bottom: 24rem;
        display: flex;
        padding-right: 20rem;
        span {
          font-size: 24rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          line-height: 40rem;
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
