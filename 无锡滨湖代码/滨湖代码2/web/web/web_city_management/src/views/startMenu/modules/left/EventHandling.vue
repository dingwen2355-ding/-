<template>
  <div class="EventHandling module">
    <SmallTitle :titleList="['事件预案']" />
    <div class="content">
      <div class="con-line-5">
        <span class="icon-c"></span>
        <span class="info">预案等级:</span>
        <el-input v-model="emergeEvent.levelName" :disabled="true"></el-input>
      </div>
      <div class="con-line-6">
        <span class="icon-c"></span>
        <span class="info">预案类型:</span>
        <el-input v-model="emergeEvent.typeName" :disabled="true"></el-input>
      </div>
      <div class="con-line-6">
        <span class="icon-c"></span>
        <span class="info">预案名称:</span>
        <el-input v-model="emergeEvent.planName" :disabled="true"></el-input>
      </div>
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
      }
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
      window.open(url, '_blank')
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
  width: 1120rem;
  // height: 850rem;
  position: absolute;
  top: 2500rem;
  left: 80rem;
  .content {
    display: flex;
    flex-direction: column;
    .con-line-5 {
      margin-top: 48rem;
      display: flex;
      flex-wrap: nowrap;
      align-items: center;
      .icon-c {
        width: 60rem;
        height: 45rem;
        display: inline-block;
        background-image: url('../../../../assets/left/排名标签@2x.png');
        background-repeat: no-repeat;
        margin-top: 5rem;
        background-size: contain;
      }
      .info {
        // margin-left: 13rem;
        width: 280rem;
        font-size: 48rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #6fd1ff;
      }
      ::v-deep .el-input__inner {
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        color: #4d98cd;
        height: 84rem;
        background: RGBA(16, 38, 60, 1);
      }
    }
    .con-line-6 {
      margin-top: 40rem;
      display: flex;
      flex-wrap: nowrap;
      align-items: center;
      .icon-c {
        width: 60rem;
        height: 45rem;
        display: inline-block;
        background-image: url('../../../../assets/left/分类@2x.png');
        background-repeat: no-repeat;
        margin-top: 5rem;
        background-size: contain;
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
        width: 280rem;
        font-size: 48rem;
        font-family: SourceHanSansCN;
        font-weight: 400;
        color: #6fd1ff;
      }
      ::v-deep .el-input__inner {
        border: 1rem solid rgba(0, 145, 255, 0.5);
        border-radius: 2rem;
        color: #4d98cd;
        height: 84rem;
        background: RGBA(16, 38, 60, 1);
      }
    }
    .c-content {
      margin-top: 40rem;
      width: 100%;
      height: 700rem;
      overflow: auto;
      border: 1rem solid;
      border-image: linear-gradient(to bottom, rgba(70, 129, 249, 0), rgba(70, 129, 249, 0.5)) 1;
      background-color: linear-gradient(0deg, rgba(21, 59, 102, 0.5), rgba(26, 34, 52, 0));
      .con-line-7 {
        margin-top: 20rem;
        display: flex;
        align-items: center;
        flex-wrap: nowrap;
        // justify-content: space-between;
        width: 100%;
        padding: 0 20rem;
        margin-bottom: 5rem;
        .icon-c {
          width: 43rem;
          height: 40rem;
          display: inline-block;
          background-image: url('../../../../assets/left/查看@2x.png');
          background-size: contain;
          background-position: 10% 100%;
          background-repeat: no-repeat;
          margin-right: 5rem;
        }
        .tip {
          margin-left: 5rem;
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #6fd1ff;
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
          font-size: 44rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: #ffd56f;
          width: 55rem;
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
