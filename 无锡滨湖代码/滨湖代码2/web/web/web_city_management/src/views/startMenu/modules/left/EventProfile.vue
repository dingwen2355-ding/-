<template>
  <div class="EventProfile module">
    <SmallTitle :titleList="['事件简介']" size="big" />
    <img class="button1" src="../../../../assets/global/设置.png" alt="" @click="lineDialogVisible = true" />
    <!-- <div class="button1" style="cursor: pointer" @click="openDialog"></div> -->
    <!-- <div v-if="!info.desc && !info.eventTitle && !precinct" class="untitle">
      <img src="../../../../assets/global/暂无事件.png" alt="" />
    </div> -->
    <div class="content">
      <div class="con-line-1" v-if="info.eventTitle">
        <span class="icon-a"></span>
        <span class="tip1" :title="info.eventTitle">{{ info.eventTitle }}</span>
        <div class="showChange">
          <span class="tip-c">处置中</span>
        </div>
      </div>
      <div class="con-line-2">
        <div>
          <span class="card" v-if="info.area">#{{ info.area }}</span>
          <span class="card" v-if="type">#{{ type }}</span>
          <span class="card" v-for="item in list" :key="item">#{{ item }}</span>
        </div>
      </div>
      <div class="c-content">
        <div class="con-line-4" v-if="info.happen_time">
          <i class="el-icon-time" style="font-size: 50rem; color: #b3e1fb"></i
          ><span class="tip-time">发生时间: {{ info.happen_time }}</span> <span class="icon-place"></span
          ><span class="tip-place" :title="info.address">事发地点: {{ info.address }}</span>
        </div>
        <div class="con-line-3">
          <span style="display: inline-block; overflow: auto">{{ info.desc }}</span>
          <!-- <div class="noImg" v-if="info.desc">
          <img src="../../../../assets/global/暂无事件.png" alt="" />
          <img src="../../../../assets/global/暂无事件.png" alt="" />
          <img src="../../../../assets/global/暂无事件.png" alt="" />
         </div> -->
        </div>
      </div>
    </div>
    <el-dialog
      :visible.sync="showvideoVisible"
      :modal="false"
      class="videoDialogSJGL"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showvideoVisible = false"
        style="width: 4060rem; height: 3326rem"
        src="../../../../assets/global/事件管理@2x.png"
        alt=""
      />
    </el-dialog>
    <LineDialog :dialogVisibleFlag.sync="lineDialogVisible" />
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import LineDialog from '@/components/LineDialog.vue'
export default {
  name: 'EventProfile',
  components: { SmallTitle, LineDialog },
  props: ['info'],
  data() {
    return {
      value: '',
      precinct: this.$route.query.eventId,
      type: '',
      list: [],
      lineDialogVisible: false,
      showvideoVisible: false
    }
  },
  watch: {
    '$store.getters.eventInfo': {
      handler(v) {
        this.type = v ? v.eventType : ''
      },
      deep: true
    },
    info: {
      handler(v) {
        this.list = v.eventTags ? JSON.parse(v.eventTags) : []
      },
      deep: true
    }
  },
  mounted() {
    var oC = document.getElementById('c1')
    if (!oC) return
    var ctx = oC.getContext('2d')
    ctx.fillStyle = '#FFFFFF'
    ctx.fillRect(20, 20, 150, 100)
  },
  methods: {
    handleClose() {},
    openDialog() {
      this.lineDialogVisible = true
    }
    // address() {
    //   this.$root.Bus.$emit('clear-map1')
    // }
  }
}
</script>
<style lang="scss">
.videoDialogSJGL {
  z-index: 10000 !important;
  ::v-deep .el-dialog {
    .el-dialog__header {
      display: none;
    }
  }
}
</style>
<style lang="scss" scoped>
.EventProfile {
  width: 2268rem;
  // height: 420rem;
  position: absolute;
  top: 530rem;
  left: 80rem;
  .button1 {
    width: 96rem;
    height: 96rem;
    position: absolute;
    right: 0rem;
    top: 3rem;
  }
  .untitle {
    width: 100%;
    height: 100%;
    line-height: 379rem;
    text-align: center;
  }
  .content {
    display: flex;
    flex-direction: column;
    .con-line-1 {
      margin-top: 60rem;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      display: flex;
      .showChange {
        width: 180rem;
        height: 70rem;
        background: url('../../../../assets/global/376.png') no-repeat;
        background-size: cover;
        // line-height: 24rem;
        text-align: center;
        .tip-c {
          font-size: 52rem;
          color: #30ff7e;
          background: linear-gradient(10deg, #52ff93 30%, #ffffff 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
        }
      }
      .tip1 {
        font-size: 64rem;
        font-family: SourceHanSansCN;
        font-weight: bold;
        color: #ffffff;
        // line-height: 24rem;
        margin-left: 10rem;
        width: 1555rem;
        overflow: hidden;
        text-overflow: ellipsis;
        margin-right: 400rem;
      }
      .icon-a {
        width: 136rem;
        height: 78rem;
        display: inline-block;
        background-image: url('../../../../assets/left/事件@2x.png');
        background-size: contain;
        background-position: 100% 100%;
        background-repeat: no-repeat;
      }
    }
    .con-line-2 {
      margin-top: 54rem;
      display: flex;
      flex-direction: row;
      flex-wrap: nowrap;
      justify-content: space-between;
      align-items: center;
      div {
        .card {
          width: auto;
          background: rgba(190, 229, 255, 0.1);
          border: 2rem solid rgba(190, 229, 255, 0.4);
          border-radius: 8rem;
          margin-right: 24rem;
          padding: 10rem 30rem;
          font-size: 46rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #bee5ff;
          display: inline-block;
        }
      }
    }
    .c-content {
      // margin-top: 14rem;
      width: 100%;
      height: 830rem;
      padding: 0 8rem;
      // border: 1rem solid rgba(0, 145, 255, 0.5);
      // background-image: url('../../../../assets/global/值班体系.png');
      // background-repeat: no-repeat;
      // background-size: 100% 100%;
      border: 1rem solid;
      border-image: linear-gradient(to bottom, rgba(70, 129, 249, 0), rgba(70, 129, 249, 0.5)) 1;
      // background-color: rgba(26, 38, 66, 1);
      .con-line-3 {
        margin-top: 30rem;
        font-size: 48rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #ececec;
        line-height: 84rem;
        opacity: 0.8;
        height: 80%;
        overflow: auto;
        padding: 0 40rem;
        .noImg {
          display: flex;
          width: 484rem;
          justify-content: space-around;
          margin-top: 15rem;
          img {
            width: 156rem;
            height: 102rem;
            border: 1rem solid #73fdff;
            margin-right: 5rem;
          }
        }
      }
      .con-line-4 {
        margin-top: 50rem;
        display: flex;
        align-items: center;
        flex-wrap: nowrap;
        .icon-time {
          width: 52rem;
          height: 52rem;
          display: inline-block;
          margin-top: -5rem;
          color: #77b1e2;
          // background: url('../../../../assets/left/217时间@2x.png') no-repeat;
          // background-size: 100% 100%;
        }
        .icon-place {
          margin-left: 110rem;
          width: 52rem;
          height: 52rem;
          display: inline-block;
          background: url('../../../../assets/left/_定位小@2x.png') no-repeat;
          background-size: 100% 100%;
          cursor: pointer;
        }
        .tip-time {
          margin-left: 3rem;
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #ffffff;
          // line-height: 24rem;
          opacity: 0.5;
          white-space: nowrap;
        }
        .tip-place {
          margin-left: 5rem;
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #ffffff;
          // line-height: 24rem;
          opacity: 0.5;
          width: 1300rem;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          // cursor: pointer;
        }
      }
    }
  }
}
</style>
