<template>
  <div class="SimilarThings module">
    <SmallTitle />
    <div v-if="list.length === 0 && !precinct" class="untitle">
      <img src="../../../../assets/global/暂无事件.png" alt="" />
    </div>
    <div class="content" v-else>
      <div class="dis-panel" v-for="(item, index) in list" :key="index">
        <div class="content-right">
          <div class="c-title">
            {{ item.event_title }}
          </div>
          <div class="c-tip">
            <i class="el-icon-time" style="font-size: 50rem; color: #b3e1fb"></i>
          <span class="tip-time">{{ item.happen_time }}</span> <span class="icon-place"></span>
          <span class="tip-place" :title="item.address">{{ item.address }}</span>
          </div>
          <div class="c-con">
            {{ item.desc }}
          </div>
        </div>
      </div>
    </div>
    <div class="tip1">
      <ul>
        <li
          v-for="(item, index) in typeName"
          :key="index"
          :class="{ active: index === activeIndex }"
          @click="handleClick(index)"
        >
          {{ item }}
        </li>
      </ul>
      <!-- <span v-for="item in typeName" :key="item"  @click="changeType">{{ item }}</span> -->
    </div>
    <!-- <div class="tip2" @click="openMore">更多>></div> -->
    <more-info-dialog :dialogVisibleFlag.sync="dialogVisibleFlag" :type="type"></more-info-dialog>
  </div>
</template>

<script>
import SmallTitle from '@/components/SmallTitle.vue'
import MoreInfoDialog from '@/components/MoreInfoDialog.vue'
import Axios from '@/utils/request'
export default {
  name: 'SimilarThings',
  components: { SmallTitle, MoreInfoDialog },
  data() {
    return {
      dialogVisibleFlag: false,
      precinct: this.$route.query.eventId,
      type: 1,
      // typeName: {
      //   1: '类似事件',
      //   2: '附近事件'
      // },
      typeName: ['类似事件', '附近事件'],
      list: [],
      activeIndex: 0
    }
  },
  mounted() {
    this.eventInfoListByType()
  },
  activated() {
    this.eventInfoListByType()
  },
  methods: {
    handleClick(index) {
      this.activeIndex = index
      if (this.activeIndex === 0) {
        this.type = 1
      } else {
        this.type = 2
      }
      this.list = []
      this.eventInfoListByType()
    },
    openMore() {
      this.dialogVisibleFlag = true
    },
    changeType() {
      if (this.type === 1) {
        this.type = 2
      } else {
        this.type = 1
      }
      this.eventInfoListByType()
    },
    eventInfoListByType() {
      let url = `/apis/Command/eventInfoListByType?type=${this.type}`
      Axios.get(url).then((res) => {
        if (res.data.code === 200) {
          let data = res.data.data.list
          this.list = data || []
        }
      })
      // if (sessionStorage.getItem('eventNaber') && this.type === 2) {
      //   this.list = JSON.parse(sessionStorage.getItem('eventNaber')).data
      // } else {
      //   let url = `/apis/Command/eventInfoListByType?type=${this.type}`
      //   Axios.get(url).then((res) => {
      //     if (res.data.code === 200) {
      //       let data = res.data.data.list
      //       this.list = data || []
      //       if (this.type === 2) {
      //         sessionStorage.setItem('eventNaber', JSON.stringify({ data }))
      //       }
      //     }
      //   })
      // }
    }
  }
}
</script>

<style lang="scss" scoped>
.SimilarThings {
  width: 1120rem;
  // height: 850rem;
  position: absolute;
  top: 2500rem;
  left: 1230rem;
  .untitle {
    width: 100%;
    height: 100%;
    margin-top: 60rem;
    text-align: center;
  }
  .content {
    margin-top: 5rem;
    height: 1112rem;
    overflow-y: scroll;
    border-bottom: 1rem solid #213b5a;
    .dis-panel:first-of-type {
      margin-top: 10rem;
    }
    .dis-panel {
      margin-top: 15rem;
      margin-bottom: 15rem;
      width: 100%;
      min-height: 115rem;
      height: auto;
      border: 1rem solid rgba(33, 59, 90, 1);
      background-color: rgba(33, 59, 90, 0.5);
      display: flex;
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

      .icon-left {
        margin-top: 6rem;
        width: 30rem;
        height: 30rem;
        background-image: url('../../../../assets/left/告警实心@2x.png');
        background-size: 100% 100%;
      }
      .content-right {
        width: calc(100% - 30rem);
        height: auto;
        .c-title {
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 500;
          margin-left: 20rem;
          color: rgba(238, 139, 141, 1);
          // line-height: 35rem;
        }
        .c-tip {
            margin: 20rem 0 30rem;
            padding-left: 38rem;
            display: flex;
            align-items: center;
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
          margin-left: 20rem;
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
          color: rgba(190, 229, 255, 1);
          // line-height: 24rem;
          opacity: 0.5;
          white-space: nowrap;
        }
        .tip-place {
          margin-left: 5rem;
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          color: rgba(190, 229, 255, 1);
          // line-height: 24rem;
          opacity: 0.5;
          width: 450rem;
          display: inline-block;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          // cursor: pointer;
        }
        }
        .c-con {
          font-size: 48rem;
          font-family: SourceHanSansCN;
          font-weight: 400;
          font-weight: 400;
          color: #FFFEFE;
          line-height: 84rem;
          padding-left: 30rem;
        }
      }
    }
  }
  .tip1 {
    position: absolute;
    top: 46rem;
    left: 120rem;
    width: 90rem;
    height: 20rem;
    font-size: 18rem;
    font-family: SourceHanSansCN;
    font-weight: 500;
    // color: #42a1e4;
    line-height: 48rem;
    ul {
        display: flex;
        width: 590rem;
        li {
          font-size: 56rem;
          font-family: SourceHanSansCN;
          font-weight: 500;
          color: #42a1e4;
          line-height: 30rem;
          margin: 0 10rem;
          cursor: pointer;
        }
        li.active {
          font-size: 60rem;
          font-family: SourceHanSansCN;
          font-weight: 500;
          color: #ffffff;
        }
      }
  }
  .tip2 {
    position: absolute;
    top: 0rem;
    right: 0rem;
    width: 70rem;
    height: 18rem;
    font-size: 16rem;
    font-family: SourceHanSansCN;
    font-weight: 400;
    color: #ffd56f;
    line-height: 24rem;
    cursor: pointer;
  }
}
</style>
