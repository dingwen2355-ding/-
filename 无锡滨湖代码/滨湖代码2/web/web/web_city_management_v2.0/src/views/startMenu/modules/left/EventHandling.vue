<template>
  <div class="EventHandling module">
    <div style="position: relative">
      <SmallTitle :titleList="['事件预案']" size="big" />
      <div class="headForm">
        <div class="con-line-5">
          <span class="icon-c"></span>
          <span class="info">预案等级：</span>
          <el-input v-model="emergeEvent.levelName" :disabled="true"></el-input>
        </div>
        <div class="con-line-6">
          <span class="icon-c"></span>
          <span class="info">预案名称：</span>
          <el-input v-model="emergeEvent.planName" :disabled="true"></el-input>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="c-content">
        <div class="con-line-7" v-for="(item, index) in emergeEvent.similarPlan" :key="index">
          <span
            class="tip"
            @click="emergList(item)"
            :style="{ cursor: item && item.importPlanId ? 'pointer' : 'default' }"
            :title="item.planName"
            >{{ item.planName }}</span
          >
          <div class="btn" @click="openView(item.fileUrl)">
            <!-- @click="showImportPosition = true"-->
            <div class="icon-c"></div>
            查看
          </div>
        </div>
      </div>
    </div>
    <el-dialog
      :visible.sync="showVisible"
      :modal="false"
      class="videoDialogZDSJYJYA"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showVisible = false"
        style="width: 4060rem; height: 3326rem"
        src="../../../../assets/global/重大事件应急预案_指挥体系@2x (2).png"
        alt=""
      />
      <span
        style="
          position: absolute;
          top: 300rem;
          left: 200rem;
          display: inline-block;
          width: 500rem;
          height: 200rem;
          background-color: transparent;
        "
        @click.prevent="
          showVisible = true
          showVisible2 = false
        "
      ></span>
      <span
        style="
          position: absolute;
          top: 300rem;
          left: 700rem;
          display: inline-block;
          width: 500rem;
          height: 200rem;
          background-color: transparent;
        "
        @click.prevent="
          showVisible2 = true
          showVisible = false
        "
      ></span>
    </el-dialog>
    <el-dialog
      :visible.sync="showVisible2"
      :modal="false"
      class="videoDialogZDSJYJYA"
      :append-to-body="true"
      :before-close="handleClose"
    >
      <img
        @click="showVisible2 = false"
        style="width: 4060rem; height: 3326rem"
        src="../../../../assets/global/重大事件应急预案_指挥体系@2x.png"
        alt=""
      />
      <span
        style="
          position: absolute;
          top: 300rem;
          left: 200rem;
          display: inline-block;
          width: 500rem;
          height: 200rem;
          background-color: transparent;
        "
        @click.prevent="
          showVisible = true
          showVisible2 = false
        "
      ></span>
      <span
        style="
          position: absolute;
          top: 300rem;
          left: 700rem;
          display: inline-block;
          width: 500rem;
          height: 200rem;
          background-color: transparent;
        "
        @click.prevent="
          showVisible2 = true
          showVisible = false
        "
      ></span>
    </el-dialog>
    <EmergeDialog :dialogVisibleFlag.sync="showEmergeVisible" :infomation="infomation" />
    <importPosition :dialogVisibleFlag.sync="showImportPosition" :infomation="infomation" />
    <el-dialog :visible.sync="dialogVisible" width="60%" custom-class="meetFrameDialog" :append-to-body="true">
      <p slot="title" style="color: #fff; font-size: 60rem">预案详情</p>
      <iframe :src="planUrl" style="width: 100%; height: 4000rem"></iframe>
    </el-dialog>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import Axios from '@/utils/request'
import EmergeDialog from '../dialog/EmergeDialog.vue'
import importPosition from '../dialog/importPosition.vue'
export default {
  name: 'EventHandling',
  components: { SmallTitle, EmergeDialog, importPosition },
  props: ['info'],
  data() {
    return {
      showVisible: false,
      showVisible2: false,
      showImportPosition: false,
      pdfList: [],
      type: '',
      showEmergeVisible: false,
      infomation: {},
      emergeEvent: {
        levelName: '国家级',
        planName: '测试预案-国家级',
        similarPlan: [],
        typeName: ''
      },
      dialogVisible: false,
      planUrl: null
    }
  },
  watch: {
    '$store.getters.eventInfo': {
      handler(v) {
        console.log(v)
        this.type = v ? v.eventType : ''
      },
      deep: true
    }
  },
  mounted() {
    this.getPdfList()
  },
  activated() {
    this.getPdfList()
  },
  methods: {
    handleClose() {},
    emergList(v) {
      if (v && v.importPlanId) {
        this.infomation = v
        this.showEmergeVisible = true
      }
    },
    openView(url) {
      this.planUrl = url
      this.dialogVisible = true
      // window.open(url, '_blank')
    },
    // 应急预案
    getPdfList() {
      let url = '/apis/emergencyPlan/queryEmergencyInfo'
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data
          this.emergeEvent = data
          this.$store.dispatch('view/setEventPlanId', data.id)
          // this.pdfList = data || []
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.videoDialogZDSJYJYA {
  z-index: 10000 !important;
  ::v-deep .el-dialog {
    position: relative;
    .el-dialog__header {
      display: none;
    }
  }
}
</style>
<style lang="scss" scoped>
.EventHandling {
  width: 2840rem;
  position: absolute;
  top: 2152rem;
  left: 128rem;
  .headForm {
    position: absolute;
    right: 48rem;
    top: 32rem;
    display: flex;
    // width: 70%;
    height: 200rem;
    .con-line-5 {
      // margin-top: 48rem;
      display: flex;
      flex-wrap: nowrap;
      align-items: center;
      .icon-c {
        width: 60rem;
        height: 64rem;
        display: inline-block;
        background-image: url('../../../../assets/left/排名标签@2x.png');
        background-repeat: no-repeat;
        margin-top: 5rem;
        background-size: contain;
        margin-right: 38rem;
      }
      .info {
        // margin-left: 13rem;
        // width: 400rem;
        font-size: 60rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #6fd1ff;
      }
      .el-input{
        width: auto;
      }
      ::v-deep .el-input__inner {
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        font-size: 60rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
        height: 104rem;
        width: 420rem;
        background: none;
      }
    }
    .con-line-6 {
      margin-left: 72rem;
      display: flex;
      flex-wrap: nowrap;
      align-items: center;
      .icon-c {
        width: 64rem;
        height: 64rem;
        display: inline-block;
        background-image: url('../../../../assets/left/分类@2x.png');
        background-repeat: no-repeat;
        // margin-top: 35rem;
        background-size: contain;
        margin-right: 38rem;
      }
      .icon-d {
        background-image: url('../../../../assets/left/定位@2x.png');
        width: 20rem;
        height: 20rem;
        display: inline-block;
        background-repeat: no-repeat;
      }
      .info {
        // margin-left: 13rem;
        // width: 380rem;
        font-size: 60rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #6fd1ff;
      }
      .el-input{
        width: auto;
      }
      ::v-deep .el-input__inner {
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        font-size: 60rem;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #ffffff;
        height: 104rem;
        width: 730rem;
        background: none;
      }
    }
  }
  .content {
    // height: 700rem;
    display: flex;
    flex-direction: column;
    background: rgba(3, 16, 50, 0.5);
    border: 8rem solid #002e7b;
    border-radius: 8rem;
    padding: 32rem;

    .c-content {
      margin-top: 40rem;
      width: 100%;
      height: 364rem;
      overflow: auto;
      display: flex;
      flex-wrap: wrap;
      //border: 1rem solid;
      //border-image: linear-gradient(to bottom, rgba(70, 129, 249, 0), rgba(70, 129, 249, 0.5)) 1;
      // background-color: linear-gradient(0deg, rgba(21, 59, 102, 0.5), rgba(26, 34, 52, 0));
      .con-line-7 {
        margin-top: 20rem;
        display: flex;
        align-items: center;
        flex-wrap: nowrap;
        justify-content: space-between;
        width: 1308rem;
        padding: 0 20rem;
        margin-bottom: 5rem;
        .icon-c {
          width: 68rem;
          height: 56rem;
          display: inline-block;
          background-image: url('../../../../assets/left/eyes@2x.png');
          background-size: contain;
          background-position: 10% 100%;
          background-repeat: no-repeat;
          margin-right: 5rem;
        }
        .tip {
          margin-left: 5rem;
          font-size: 56rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #fff; // #6fd1ff;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          width: 890rem;
          cursor: pointer;
          margin-right: 20rem;
        }
        .dot {
          margin-left: 13rem;
          font-size: 44rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #ffffff;
          opacity: 0.2;
        }
        .btn {
          margin-right: 20rem;
          font-size: 56rem;
          font-family: Microsoft YaHei;
          font-weight: 400;
          color: #14a1ff;
          width: 192rem;
          cursor: pointer;
          white-space: nowrap;
        }
      }
      &::-webkit-scrollbar {
        // 滚动条整体
        display: block !important;
      }
    }
    .con-line-8 {
      margin-top: 16rem;
    }
    .con-line-9 {
      margin-top: 16rem;
    }
  }
}
</style>
